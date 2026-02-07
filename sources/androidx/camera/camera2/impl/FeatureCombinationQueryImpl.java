package androidx.camera.camera2.impl;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.DynamicRangeProfiles;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.media.ImageReader;
import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Size;
import android.view.SurfaceHolder;
import androidx.camera.camera2.internal.CameraUnavailableExceptionHelper;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.camera2.internal.compat.params.DynamicRangeConversions;
import androidx.camera.camera2.internal.compat.params.DynamicRangesCompat;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.Logger;
import androidx.camera.core.featuregroup.impl.FeatureCombinationQuery;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.featurecombinationquery.CameraDeviceSetupCompat;
import androidx.camera.featurecombinationquery.CameraDeviceSetupCompatFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.mvel2.asm.Opcodes;

/* loaded from: classes3.dex */
public final class FeatureCombinationQueryImpl implements FeatureCombinationQuery {
    public static final Companion Companion = new Companion(null);
    private static final FeatureCombinationQueryImpl$Companion$NO_OP_CALLBACK$1 NO_OP_CALLBACK = new CameraCaptureSession.StateCallback() { // from class: androidx.camera.camera2.impl.FeatureCombinationQueryImpl$Companion$NO_OP_CALLBACK$1
        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigureFailed(CameraCaptureSession p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
        }

        @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
        public void onConfigured(CameraCaptureSession p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
        }
    };
    private final Lazy cameraCharacteristics$delegate;
    private final Lazy cameraDeviceSetup$delegate;
    private final Lazy cameraDeviceSetupCompat$delegate;
    private final String cameraId;
    private final CameraManagerCompat cameraManagerCompat;
    private final Context context;
    private final Lazy dynamicRangeProfiles$delegate;
    private final Lazy isDeferredSurfaceSupported$delegate;

    public FeatureCombinationQueryImpl(Context context, String cameraId, CameraManagerCompat cameraManagerCompat) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(cameraId, "cameraId");
        Intrinsics.checkNotNullParameter(cameraManagerCompat, "cameraManagerCompat");
        this.context = context;
        this.cameraId = cameraId;
        this.cameraManagerCompat = cameraManagerCompat;
        this.cameraDeviceSetupCompat$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.FeatureCombinationQueryImpl$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FeatureCombinationQueryImpl.cameraDeviceSetupCompat_delegate$lambda$0(this.f$0);
            }
        });
        this.cameraDeviceSetup$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.FeatureCombinationQueryImpl$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FeatureCombinationQueryImpl.cameraDeviceSetup_delegate$lambda$1(this.f$0);
            }
        });
        this.cameraCharacteristics$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.FeatureCombinationQueryImpl$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FeatureCombinationQueryImpl.cameraCharacteristics_delegate$lambda$2(this.f$0);
            }
        });
        this.dynamicRangeProfiles$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.FeatureCombinationQueryImpl$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FeatureCombinationQueryImpl.dynamicRangeProfiles_delegate$lambda$3(this.f$0);
            }
        });
        this.isDeferredSurfaceSupported$delegate = LazyKt.lazy(new Function0() { // from class: androidx.camera.camera2.impl.FeatureCombinationQueryImpl$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(FeatureCombinationQueryImpl.isDeferredSurfaceSupported_delegate$lambda$4(this.f$0));
            }
        });
    }

    private final CameraDeviceSetupCompat getCameraDeviceSetupCompat() {
        return (CameraDeviceSetupCompat) this.cameraDeviceSetupCompat$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CameraDeviceSetupCompat cameraDeviceSetupCompat_delegate$lambda$0(FeatureCombinationQueryImpl featureCombinationQueryImpl) {
        return new CameraDeviceSetupCompatFactory(featureCombinationQueryImpl.context).getCameraDeviceSetupCompat(featureCombinationQueryImpl.cameraId);
    }

    private final CameraDevice.CameraDeviceSetup getCameraDeviceSetup() {
        return FeatureCombinationQueryImpl$$ExternalSyntheticApiModelOutline7.m14m(this.cameraDeviceSetup$delegate.getValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CameraDevice.CameraDeviceSetup cameraDeviceSetup_delegate$lambda$1(FeatureCombinationQueryImpl featureCombinationQueryImpl) {
        if (featureCombinationQueryImpl.cameraManagerCompat.unwrap().isCameraDeviceSetupSupported(featureCombinationQueryImpl.cameraId)) {
            return featureCombinationQueryImpl.cameraManagerCompat.unwrap().getCameraDeviceSetup(featureCombinationQueryImpl.cameraId);
        }
        return null;
    }

    private final CameraCharacteristicsCompat getCameraCharacteristics() {
        return (CameraCharacteristicsCompat) this.cameraCharacteristics$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final CameraCharacteristicsCompat cameraCharacteristics_delegate$lambda$2(FeatureCombinationQueryImpl featureCombinationQueryImpl) throws CameraUnavailableException {
        try {
            CameraCharacteristicsCompat cameraCharacteristicsCompat = featureCombinationQueryImpl.cameraManagerCompat.getCameraCharacteristicsCompat(featureCombinationQueryImpl.cameraId);
            Intrinsics.checkNotNull(cameraCharacteristicsCompat);
            return cameraCharacteristicsCompat;
        } catch (CameraAccessExceptionCompat e) {
            throw CameraUnavailableExceptionHelper.createFrom(e);
        }
    }

    private final DynamicRangeProfiles getDynamicRangeProfiles() {
        return FeatureCombinationQueryImpl$$ExternalSyntheticApiModelOutline6.m13m(this.dynamicRangeProfiles$delegate.getValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final DynamicRangeProfiles dynamicRangeProfiles_delegate$lambda$3(FeatureCombinationQueryImpl featureCombinationQueryImpl) {
        return DynamicRangesCompat.fromCameraCharacteristics(featureCombinationQueryImpl.getCameraCharacteristics()).toDynamicRangeProfiles();
    }

    private final boolean isDeferredSurfaceSupported() {
        return ((Boolean) this.isDeferredSurfaceSupported$delegate.getValue()).booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean isDeferredSurfaceSupported_delegate$lambda$4(FeatureCombinationQueryImpl featureCombinationQueryImpl) {
        return Intrinsics.areEqual(featureCombinationQueryImpl.hasPlayServicesDependency(), Boolean.FALSE);
    }

    @Override // androidx.camera.core.featuregroup.impl.FeatureCombinationQuery
    public boolean isSupported(SessionConfig sessionConfig) throws Exception {
        Intrinsics.checkNotNullParameter(sessionConfig, "sessionConfig");
        List listCreateOutputConfigurations = createOutputConfigurations(sessionConfig);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listCreateOutputConfigurations, 10));
        Iterator it = listCreateOutputConfigurations.iterator();
        while (it.hasNext()) {
            arrayList.add(((CloseableOutputConfiguration) it.next()).getValue());
        }
        SessionConfiguration camera2SessionConfiguration = getCamera2SessionConfiguration(arrayList, sessionConfig);
        if (camera2SessionConfiguration == null) {
            return false;
        }
        int supported = getCameraDeviceSetupCompat().isSessionConfigurationSupported(camera2SessionConfiguration).getSupported();
        Logger.m43d("FeatureCombinationQuery", "isSupported: supported = " + supported + " for session config with " + toLogString(sessionConfig));
        boolean z = supported == 1;
        Iterator it2 = listCreateOutputConfigurations.iterator();
        while (it2.hasNext()) {
            AbstractC0111xb15b98f9.m15m((AutoCloseable) it2.next());
        }
        return z;
    }

    private final List createOutputConfigurations(SessionConfig sessionConfig) {
        CloseableOutputConfiguration concreteOutputConfiguration;
        List outputConfigs = sessionConfig.getOutputConfigs();
        Intrinsics.checkNotNullExpressionValue(outputConfigs, "getOutputConfigs(...)");
        List<SessionConfig.OutputConfig> list = outputConfigs;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (SessionConfig.OutputConfig outputConfig : list) {
            if (isDeferredSurfaceSupported()) {
                Intrinsics.checkNotNull(outputConfig);
                concreteOutputConfiguration = toDeferredOutputConfiguration(outputConfig);
            } else {
                Intrinsics.checkNotNull(outputConfig);
                concreteOutputConfiguration = toConcreteOutputConfiguration(outputConfig);
            }
            if (outputConfig.getSurface().getContainerClass() != null) {
                applyDynamicRange(concreteOutputConfiguration.getValue(), outputConfig);
            }
            arrayList.add(concreteOutputConfiguration);
        }
        return arrayList;
    }

    private final CloseableOutputConfiguration toConcreteOutputConfiguration(SessionConfig.OutputConfig outputConfig) {
        long j;
        Class containerClass = outputConfig.getSurface().getContainerClass();
        if (Intrinsics.areEqual(containerClass, MediaCodec.class)) {
            j = 65536;
        } else if (Intrinsics.areEqual(containerClass, SurfaceHolder.class)) {
            j = 2048;
        } else {
            j = Intrinsics.areEqual(containerClass, SurfaceTexture.class) ? 256L : 0L;
        }
        long j2 = j;
        Logger.m43d("FeatureCombinationQuery", "toConcreteOutputConfiguration: surface containerClass = " + outputConfig.getSurface().getContainerClass() + ", usageFlag = " + j2);
        ImageReader imageReaderNewInstance = ImageReader.newInstance(outputConfig.getSurface().getPrescribedSize().getWidth(), outputConfig.getSurface().getPrescribedSize().getHeight(), outputConfig.getSurface().getPrescribedStreamFormat(), 1, j2);
        Intrinsics.checkNotNullExpressionValue(imageReaderNewInstance, "newInstance(...)");
        FeatureCombinationQueryImpl$$ExternalSyntheticApiModelOutline1.m8m();
        return new CloseableOutputConfiguration(FeatureCombinationQueryImpl$$ExternalSyntheticApiModelOutline0.m7m(imageReaderNewInstance.getSurface()), imageReaderNewInstance);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private final CloseableOutputConfiguration toDeferredOutputConfiguration(SessionConfig.OutputConfig outputConfig) {
        OutputConfiguration outputConfigurationM10m;
        Class containerClass = outputConfig.getSurface().getContainerClass();
        Logger.m43d("FeatureCombinationQuery", "toDeferredOutputConfiguration: surface containerClass = " + outputConfig.getSurface().getContainerClass());
        if (containerClass != null) {
            FeatureCombinationQueryImpl$$ExternalSyntheticApiModelOutline1.m8m();
            Size prescribedSize = outputConfig.getSurface().getPrescribedSize();
            if (prescribedSize == null) {
                throw new IllegalArgumentException("Required value was null.");
            }
            outputConfigurationM10m = FeatureCombinationQueryImpl$$ExternalSyntheticApiModelOutline2.m9m(prescribedSize, containerClass);
        } else {
            FeatureCombinationQueryImpl$$ExternalSyntheticApiModelOutline1.m8m();
            outputConfigurationM10m = FeatureCombinationQueryImpl$$ExternalSyntheticApiModelOutline3.m10m(outputConfig.getSurface().getPrescribedStreamFormat(), outputConfig.getSurface().getPrescribedSize());
        }
        return new CloseableOutputConfiguration(outputConfigurationM10m, null, 2, 0 == true ? 1 : 0);
    }

    private final void applyDynamicRange(OutputConfiguration outputConfiguration, SessionConfig.OutputConfig outputConfig) {
        DynamicRangeProfiles dynamicRangeProfiles = getDynamicRangeProfiles();
        if (dynamicRangeProfiles == null) {
            return;
        }
        Long lDynamicRangeToFirstSupportedProfile = DynamicRangeConversions.dynamicRangeToFirstSupportedProfile(outputConfig.getDynamicRange(), dynamicRangeProfiles);
        if (lDynamicRangeToFirstSupportedProfile != null) {
            outputConfiguration.setDynamicRangeProfile(lDynamicRangeToFirstSupportedProfile.longValue());
            return;
        }
        throw new IllegalArgumentException("Required value was null.");
    }

    private final SessionConfiguration getCamera2SessionConfiguration(List list, SessionConfig sessionConfig) {
        FeatureCombinationQueryImpl$$ExternalSyntheticApiModelOutline5.m12m();
        SessionConfiguration sessionConfigurationM11m = FeatureCombinationQueryImpl$$ExternalSyntheticApiModelOutline4.m11m(0, list, CameraXExecutors.directExecutor(), NO_OP_CALLBACK);
        CameraDevice.CameraDeviceSetup cameraDeviceSetup = getCameraDeviceSetup();
        if (cameraDeviceSetup == null) {
            return null;
        }
        CaptureRequest.Builder builderCreateCaptureRequest = cameraDeviceSetup.createCaptureRequest(sessionConfig.getTemplateType());
        builderCreateCaptureRequest.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, sessionConfig.getExpectedFrameRateRange());
        if (sessionConfig.getRepeatingCaptureConfig().getPreviewStabilizationMode() == 2) {
            builderCreateCaptureRequest.set(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, 2);
        }
        sessionConfigurationM11m.setSessionParameters(builderCreateCaptureRequest.build());
        return sessionConfigurationM11m;
    }

    private final String toLogString(SessionConfig sessionConfig) {
        StringBuilder sb = new StringBuilder();
        sb.append("sessionParameters=[");
        sb.append("fpsRange=" + sessionConfig.getExpectedFrameRateRange());
        sb.append(", previewStabilizationMode=" + sessionConfig.getRepeatingCaptureConfig().getPreviewStabilizationMode());
        sb.append("], ");
        sb.append("outputConfigurations=[");
        List outputConfigs = sessionConfig.getOutputConfigs();
        Intrinsics.checkNotNullExpressionValue(outputConfigs, "getOutputConfigs(...)");
        int i = 0;
        for (Object obj : outputConfigs) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            SessionConfig.OutputConfig outputConfig = (SessionConfig.OutputConfig) obj;
            if (i != 0) {
                sb.append(",");
            }
            sb.append("{format=" + outputConfig.getSurface().getPrescribedStreamFormat() + ", size=" + outputConfig.getSurface().getPrescribedSize() + ", dynamicRange=" + outputConfig.getDynamicRange() + ", class=" + outputConfig.getSurface().getContainerClass() + '}');
            i = i2;
        }
        sb.append("]");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private final Boolean hasPlayServicesDependency() {
        try {
            ServiceInfo[] serviceInfoArr = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), Opcodes.IINC).services;
            if (serviceInfoArr == null) {
                return Boolean.FALSE;
            }
            Iterator it = ArrayIteratorKt.iterator(serviceInfoArr);
            while (it.hasNext()) {
                Bundle bundle = ((ServiceInfo) it.next()).metaData;
                if (bundle != null && bundle.getString("androidx.camera.featurecombinationquery.PLAY_SERVICES_IMPL_PROVIDER_KEY") != null) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    private static final class CloseableOutputConfiguration implements AutoCloseable {
        private final ImageReader backingImageReader;
        private final OutputConfiguration value;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CloseableOutputConfiguration)) {
                return false;
            }
            CloseableOutputConfiguration closeableOutputConfiguration = (CloseableOutputConfiguration) obj;
            return Intrinsics.areEqual(this.value, closeableOutputConfiguration.value) && Intrinsics.areEqual(this.backingImageReader, closeableOutputConfiguration.backingImageReader);
        }

        public int hashCode() {
            int iHashCode = this.value.hashCode() * 31;
            ImageReader imageReader = this.backingImageReader;
            return iHashCode + (imageReader == null ? 0 : imageReader.hashCode());
        }

        public String toString() {
            return "CloseableOutputConfiguration(value=" + this.value + ", backingImageReader=" + this.backingImageReader + ')';
        }

        public CloseableOutputConfiguration(OutputConfiguration value, ImageReader imageReader) {
            Intrinsics.checkNotNullParameter(value, "value");
            this.value = value;
            this.backingImageReader = imageReader;
        }

        public /* synthetic */ CloseableOutputConfiguration(OutputConfiguration outputConfiguration, ImageReader imageReader, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(outputConfiguration, (i & 2) != 0 ? null : imageReader);
        }

        public final OutputConfiguration getValue() {
            return this.value;
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            ImageReader imageReader = this.backingImageReader;
            if (imageReader != null) {
                imageReader.close();
            }
        }
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
