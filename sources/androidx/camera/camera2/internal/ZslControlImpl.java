package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageWriter;
import android.util.Size;
import android.view.Surface;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.internal.compat.quirk.DeviceQuirks;
import androidx.camera.camera2.internal.compat.quirk.ZslDisablerQuirk;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Logger;
import androidx.camera.core.MetadataImageReader;
import androidx.camera.core.SafeCloseImageReaderProxy;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.utils.CompareSizesByArea;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.compat.ImageWriterCompat;
import androidx.camera.core.internal.utils.RingBuffer;
import androidx.camera.core.internal.utils.ZslRingBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import p017j$.util.Objects;

/* loaded from: classes3.dex */
final class ZslControlImpl implements ZslControl {
    private final CameraCharacteristicsCompat mCameraCharacteristicsCompat;
    private final Executor mExecutor;
    final ZslRingBuffer mImageRingBuffer;
    private boolean mIsPrivateReprocessingSupported;
    private DeferrableSurface mReprocessingImageDeferrableSurface;
    SafeCloseImageReaderProxy mReprocessingImageReader;
    ImageWriterHolder mReprocessingImageWriterHolder;
    private boolean mShouldZslDisabledByQuirks;
    private boolean mIsZslDisabledByUseCaseConfig = false;
    private boolean mIsZslDisabledByFlashMode = false;

    ZslControlImpl(CameraCharacteristicsCompat cameraCharacteristicsCompat, Executor executor) {
        this.mIsPrivateReprocessingSupported = false;
        this.mShouldZslDisabledByQuirks = false;
        this.mCameraCharacteristicsCompat = cameraCharacteristicsCompat;
        this.mExecutor = executor;
        this.mIsPrivateReprocessingSupported = ZslUtil.isCapabilitySupported(cameraCharacteristicsCompat, 4);
        this.mShouldZslDisabledByQuirks = DeviceQuirks.get(ZslDisablerQuirk.class) != null;
        this.mImageRingBuffer = new ZslRingBuffer(3, new RingBuffer.OnRemoveCallback() { // from class: androidx.camera.camera2.internal.ZslControlImpl$$ExternalSyntheticLambda2
            @Override // androidx.camera.core.internal.utils.RingBuffer.OnRemoveCallback
            public final void onRemove(Object obj) {
                ((ImageProxy) obj).close();
            }
        });
    }

    @Override // androidx.camera.camera2.internal.ZslControl
    public void setZslDisabledByUserCaseConfig(boolean z) {
        if (this.mIsZslDisabledByUseCaseConfig != z && z) {
            clearRingBuffer();
        }
        this.mIsZslDisabledByUseCaseConfig = z;
    }

    @Override // androidx.camera.camera2.internal.ZslControl
    public void setZslDisabledByFlashMode(boolean z) {
        this.mIsZslDisabledByFlashMode = z;
    }

    @Override // androidx.camera.camera2.internal.ZslControl
    public void addZslConfig(SessionConfig.Builder builder) {
        cleanup();
        if (this.mIsZslDisabledByUseCaseConfig) {
            builder.setTemplateType(1);
            return;
        }
        if (this.mShouldZslDisabledByQuirks) {
            builder.setTemplateType(1);
            return;
        }
        Map mapCreateReprocessingInputSizeMap = createReprocessingInputSizeMap(this.mCameraCharacteristicsCompat);
        if (!this.mIsPrivateReprocessingSupported || mapCreateReprocessingInputSizeMap.isEmpty() || !mapCreateReprocessingInputSizeMap.containsKey(34) || !isJpegValidOutputForInputFormat(this.mCameraCharacteristicsCompat, 34)) {
            builder.setTemplateType(1);
            return;
        }
        Size size = (Size) mapCreateReprocessingInputSizeMap.get(34);
        MetadataImageReader metadataImageReader = new MetadataImageReader(size.getWidth(), size.getHeight(), 34, 9);
        final SafeCloseImageReaderProxy safeCloseImageReaderProxy = new SafeCloseImageReaderProxy(metadataImageReader);
        Surface surface = safeCloseImageReaderProxy.getSurface();
        Objects.requireNonNull(surface);
        ImmediateSurface immediateSurface = new ImmediateSurface(surface, new Size(safeCloseImageReaderProxy.getWidth(), safeCloseImageReaderProxy.getHeight()), 34);
        final ImageWriterHolder imageWriterHolder = new ImageWriterHolder(this.mExecutor);
        this.mReprocessingImageReader = safeCloseImageReaderProxy;
        this.mReprocessingImageDeferrableSurface = immediateSurface;
        this.mReprocessingImageWriterHolder = imageWriterHolder;
        safeCloseImageReaderProxy.setOnImageAvailableListener(new ImageReaderProxy.OnImageAvailableListener() { // from class: androidx.camera.camera2.internal.ZslControlImpl$$ExternalSyntheticLambda3
            @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
            public final void onImageAvailable(ImageReaderProxy imageReaderProxy) {
                ZslControlImpl.$r8$lambda$H8j22iFMqdgVTolvmAT0Ne0XADI(this.f$0, imageReaderProxy);
            }
        }, CameraXExecutors.ioExecutor());
        immediateSurface.getTerminationFuture().addListener(new Runnable() { // from class: androidx.camera.camera2.internal.ZslControlImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                ZslControlImpl.m1420$r8$lambda$Zp9uz5XoCIrSvBEWZnZAQVQ_Yg(safeCloseImageReaderProxy, imageWriterHolder);
            }
        }, this.mExecutor);
        builder.addSurface(immediateSurface);
        builder.addCameraCaptureCallback(metadataImageReader.getCameraCaptureCallback());
        builder.addSessionStateCallback(new CameraCaptureSession.StateCallback() { // from class: androidx.camera.camera2.internal.ZslControlImpl.1
            @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
            public void onConfigureFailed(CameraCaptureSession cameraCaptureSession) {
            }

            @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
            public void onConfigured(CameraCaptureSession cameraCaptureSession) {
                Surface inputSurface = cameraCaptureSession.getInputSurface();
                if (inputSurface != null) {
                    imageWriterHolder.onImageWriterCreated(ImageWriterCompat.newInstance(inputSurface, 1));
                }
            }
        });
        ZslControlImpl$$ExternalSyntheticApiModelOutline1.m32m();
        builder.setInputConfiguration(ZslControlImpl$$ExternalSyntheticApiModelOutline0.m31m(safeCloseImageReaderProxy.getWidth(), safeCloseImageReaderProxy.getHeight(), safeCloseImageReaderProxy.getImageFormat()));
    }

    public static /* synthetic */ void $r8$lambda$H8j22iFMqdgVTolvmAT0Ne0XADI(ZslControlImpl zslControlImpl, ImageReaderProxy imageReaderProxy) {
        zslControlImpl.getClass();
        try {
            ImageProxy imageProxyAcquireLatestImage = imageReaderProxy.acquireLatestImage();
            if (imageProxyAcquireLatestImage != null) {
                zslControlImpl.mImageRingBuffer.enqueue(imageProxyAcquireLatestImage);
            }
        } catch (IllegalStateException e) {
            Logger.m45e("ZslControlImpl", "Failed to acquire latest image IllegalStateException = " + e.getMessage());
        }
    }

    /* renamed from: $r8$lambda$Zp9uz-5XoCIrSvBEWZnZAQVQ_Yg, reason: not valid java name */
    public static /* synthetic */ void m1420$r8$lambda$Zp9uz5XoCIrSvBEWZnZAQVQ_Yg(SafeCloseImageReaderProxy safeCloseImageReaderProxy, ImageWriterHolder imageWriterHolder) {
        safeCloseImageReaderProxy.safeClose();
        imageWriterHolder.release();
    }

    @Override // androidx.camera.camera2.internal.ZslControl
    public void clearZslConfig() {
        cleanup();
    }

    private void cleanup() {
        SafeCloseImageReaderProxy safeCloseImageReaderProxy = this.mReprocessingImageReader;
        if (safeCloseImageReaderProxy != null) {
            safeCloseImageReaderProxy.clearOnImageAvailableListener();
            this.mReprocessingImageReader = null;
        }
        ImageWriterHolder imageWriterHolder = this.mReprocessingImageWriterHolder;
        if (imageWriterHolder != null) {
            imageWriterHolder.deactivate();
            this.mReprocessingImageWriterHolder = null;
        }
        clearRingBuffer();
        DeferrableSurface deferrableSurface = this.mReprocessingImageDeferrableSurface;
        if (deferrableSurface != null) {
            deferrableSurface.close();
            this.mReprocessingImageDeferrableSurface = null;
        }
    }

    private void clearRingBuffer() {
        ZslRingBuffer zslRingBuffer = this.mImageRingBuffer;
        while (!zslRingBuffer.isEmpty()) {
            ((ImageProxy) zslRingBuffer.dequeue()).close();
        }
    }

    private Map createReprocessingInputSizeMap(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        StreamConfigurationMap streamConfigurationMap;
        try {
            streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristicsCompat.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        } catch (AssertionError e) {
            Logger.m45e("ZslControlImpl", "Failed to retrieve StreamConfigurationMap, error = " + e.getMessage());
            streamConfigurationMap = null;
        }
        if (streamConfigurationMap == null || streamConfigurationMap.getInputFormats() == null) {
            return new HashMap();
        }
        HashMap map = new HashMap();
        for (int i : streamConfigurationMap.getInputFormats()) {
            Size[] inputSizes = streamConfigurationMap.getInputSizes(i);
            if (inputSizes != null) {
                Arrays.sort(inputSizes, new CompareSizesByArea(true));
                map.put(Integer.valueOf(i), inputSizes[0]);
            }
        }
        return map;
    }

    private boolean isJpegValidOutputForInputFormat(CameraCharacteristicsCompat cameraCharacteristicsCompat, int i) {
        int[] validOutputFormatsForInput;
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristicsCompat.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap == null || (validOutputFormatsForInput = streamConfigurationMap.getValidOutputFormatsForInput(i)) == null) {
            return false;
        }
        for (int i2 : validOutputFormatsForInput) {
            if (i2 == 256) {
                return true;
            }
        }
        return false;
    }

    static class ImageWriterHolder {
        private final Executor mExecutor;
        private ImageWriter mImageWriter;
        private final AtomicBoolean mIsOpened = new AtomicBoolean(true);

        ImageWriterHolder(Executor executor) {
            this.mExecutor = executor;
        }

        public void onImageWriterCreated(ImageWriter imageWriter) {
            if (this.mIsOpened.get()) {
                if (this.mImageWriter != null) {
                    Logger.m48w("ZslControlImpl", "ImageWriter already existed in the ImageWriter holder. Closing the previous one.");
                    this.mImageWriter.close();
                }
                this.mImageWriter = imageWriter;
            }
        }

        public void deactivate() {
            this.mIsOpened.set(false);
        }

        public void release() {
            deactivate();
            ImageWriter imageWriter = this.mImageWriter;
            if (imageWriter != null) {
                imageWriter.close();
            }
        }
    }
}
