package androidx.camera.core.concurrent;

import androidx.camera.core.impl.CameraRepository;

/* loaded from: classes3.dex */
public interface CameraCoordinator {

    public interface ConcurrentCameraModeListener {
        void onCameraOperatingModeUpdated(int i, int i2);
    }

    void addListener(ConcurrentCameraModeListener concurrentCameraModeListener);

    int getCameraOperatingMode();

    String getPairedConcurrentCameraId(String str);

    void init(CameraRepository cameraRepository);

    void setCameraOperatingMode(int i);

    void shutdown();

    /* renamed from: androidx.camera.core.concurrent.CameraCoordinator$-CC, reason: invalid class name */
    public abstract /* synthetic */ class CC {
        public static void $default$init(CameraCoordinator cameraCoordinator, CameraRepository cameraRepository) {
        }
    }
}
