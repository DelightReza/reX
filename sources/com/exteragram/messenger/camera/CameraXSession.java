package com.exteragram.messenger.camera;

import android.content.Context;
import android.view.WindowManager;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.MeteringPointFactory;
import androidx.camera.core.Preview;
import androidx.camera.core.ZoomState;
import androidx.camera.extensions.ExtensionsManager;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.VideoCapture;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.Utilities;

/* loaded from: classes3.dex */
public class CameraXSession {
    private Camera camera;
    private CameraControl cameraControl;
    private CameraSelector cameraSelector;
    private boolean isFrontface;
    private boolean isInitiated = false;
    private final CameraLifecycle lifecycle;
    private final MeteringPointFactory meteringPointFactory;
    private Preview previewUseCase;
    private ProcessCameraProvider provider;
    private final Preview.SurfaceProvider surfaceProvider;
    private VideoCapture vCapture;
    private static final Map stabilizationCache = new HashMap();
    private static final Map fpsRangesCache = new HashMap();

    public static class CameraLifecycle implements LifecycleOwner {
        private final LifecycleRegistry lifecycleRegistry;

        public CameraLifecycle() {
            LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
            this.lifecycleRegistry = lifecycleRegistry;
            lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        }

        public void start() {
            try {
                this.lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
            } catch (IllegalStateException e) {
                FileLog.m1160e(e);
            }
        }

        public void stop() {
            try {
                this.lifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
            } catch (IllegalStateException e) {
                FileLog.m1160e(e);
            }
        }

        @Override // androidx.lifecycle.LifecycleOwner
        public Lifecycle getLifecycle() {
            return this.lifecycleRegistry;
        }
    }

    public CameraXSession(CameraLifecycle cameraLifecycle, MeteringPointFactory meteringPointFactory, Preview.SurfaceProvider surfaceProvider) {
        this.lifecycle = cameraLifecycle;
        this.meteringPointFactory = meteringPointFactory;
        this.surfaceProvider = surfaceProvider;
    }

    public boolean isInitied() {
        return this.isInitiated;
    }

    public void initCamera(final Context context, boolean z, final Runnable runnable) {
        this.isFrontface = z;
        final ListenableFuture processCameraProvider = ProcessCameraProvider.getInstance(context);
        processCameraProvider.addListener(new Runnable() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$initCamera$1(processCameraProvider, context, runnable);
            }
        }, ContextCompat.getMainExecutor(context));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCamera$1(ListenableFuture listenableFuture, Context context, final Runnable runnable) {
        try {
            ProcessCameraProvider processCameraProvider = (ProcessCameraProvider) listenableFuture.get();
            this.provider = processCameraProvider;
            ExtensionsManager.getInstanceAsync(context, processCameraProvider).addListener(new Runnable() { // from class: com.exteragram.messenger.camera.CameraXSession$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$initCamera$0(runnable);
                }
            }, ContextCompat.getMainExecutor(context));
        } catch (InterruptedException | ExecutionException e) {
            FileLog.m1160e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initCamera$0(Runnable runnable) {
        if (this.lifecycle.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        prepareUseCases();
        bindUseCases();
        this.lifecycle.start();
        runnable.run();
        this.isInitiated = true;
    }

    public void switchCamera() {
        this.isFrontface = !this.isFrontface;
        prepareUseCases();
        bindUseCases();
    }

    public void closeCamera() {
        ProcessCameraProvider processCameraProvider = this.provider;
        if (processCameraProvider != null) {
            processCameraProvider.unbindAll();
        }
        this.lifecycle.stop();
    }

    public boolean isFlashAvailable() {
        Camera camera = this.camera;
        if (camera == null) {
            return false;
        }
        return camera.getCameraInfo().hasFlashUnit();
    }

    public void setTorchEnabled(boolean z) {
        if (isFlashAvailable()) {
            this.cameraControl.enableTorch(z);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x010a A[Catch: Exception -> 0x00c8, TRY_LEAVE, TryCatch #1 {Exception -> 0x00c8, blocks: (B:34:0x00a7, B:36:0x00ab, B:38:0x00af, B:40:0x00b5, B:42:0x00bf, B:46:0x00e4, B:48:0x00f7, B:50:0x00fb, B:56:0x010a, B:53:0x0104, B:45:0x00ca), top: B:63:0x00a7 }] */
    /* JADX WARN: Type inference failed for: r1v8, types: [androidx.camera.camera2.interop.Camera2Interop$Extender] */
    /* JADX WARN: Type inference failed for: r1v9, types: [androidx.camera.camera2.interop.Camera2Interop$Extender] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void prepareUseCases() {
        /*
            Method dump skipped, instructions count: 306
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.camera.CameraXSession.prepareUseCases():void");
    }

    public void bindUseCases() {
        if (this.provider == null || this.lifecycle.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        this.provider.unbindAll();
        try {
            CameraSelector cameraSelector = this.cameraSelector;
            if (cameraSelector != null && this.provider.hasCamera(cameraSelector)) {
                VideoCapture videoCapture = this.vCapture;
                if (videoCapture != null) {
                    try {
                        this.camera = this.provider.bindToLifecycle(this.lifecycle, this.cameraSelector, this.previewUseCase, videoCapture);
                    } catch (Exception e) {
                        FileLog.m1160e(e);
                        this.camera = this.provider.bindToLifecycle(this.lifecycle, this.cameraSelector, this.previewUseCase);
                    }
                } else {
                    this.camera = this.provider.bindToLifecycle(this.lifecycle, this.cameraSelector, this.previewUseCase);
                }
                CameraControl cameraControl = this.camera.getCameraControl();
                this.cameraControl = cameraControl;
                cameraControl.setZoomRatio(1.0f);
                return;
            }
            this.isInitiated = false;
        } catch (Exception e2) {
            FileLog.m1160e(e2);
            this.isInitiated = false;
        }
    }

    public float getLinearZoom() {
        ZoomState zoomState;
        Camera camera = this.camera;
        if (camera == null || (zoomState = (ZoomState) camera.getCameraInfo().getZoomState().getValue()) == null) {
            return 0.0f;
        }
        return zoomState.getLinearZoom();
    }

    public void setZoom(float f) {
        Camera camera;
        ZoomState zoomState;
        if (this.cameraControl == null || (camera = this.camera) == null || (zoomState = (ZoomState) camera.getCameraInfo().getZoomState().getValue()) == null) {
            return;
        }
        this.cameraControl.setZoomRatio(Utilities.clamp(zoomState.getZoomRatio() * f, zoomState.getMaxZoomRatio(), zoomState.getMinZoomRatio()));
    }

    public float getZoomRatio() {
        ZoomState zoomState;
        Camera camera = this.camera;
        if (camera == null || (zoomState = (ZoomState) camera.getCameraInfo().getZoomState().getValue()) == null) {
            return 1.0f;
        }
        return zoomState.getZoomRatio();
    }

    public void setZoomRatio(float f) {
        CameraControl cameraControl = this.cameraControl;
        if (cameraControl == null) {
            return;
        }
        cameraControl.setZoomRatio(f);
    }

    public float getMinZoomRatio() {
        ZoomState zoomState;
        Camera camera = this.camera;
        if (camera == null || (zoomState = (ZoomState) camera.getCameraInfo().getZoomState().getValue()) == null) {
            return 1.0f;
        }
        return zoomState.getMinZoomRatio();
    }

    public float getMaxZoomRatio() {
        ZoomState zoomState;
        Camera camera = this.camera;
        if (camera == null || (zoomState = (ZoomState) camera.getCameraInfo().getZoomState().getValue()) == null) {
            return 1.0f;
        }
        return zoomState.getMaxZoomRatio();
    }

    public void focusToPoint(float f, float f2) {
        if (this.cameraControl == null) {
            return;
        }
        this.cameraControl.startFocusAndMetering(new FocusMeteringAction.Builder(this.meteringPointFactory.createPoint(f, f2), 7).build());
    }

    public int getDisplayOrientation() {
        int rotation = ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
        if (rotation == 0) {
            return 0;
        }
        if (rotation != 1) {
            return rotation != 2 ? rotation != 3 ? 0 : 270 : Opcodes.GETFIELD;
        }
        return 90;
    }
}
