package androidx.camera.camera2.internal;

import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;

/* loaded from: classes3.dex */
class ExposureStateImpl {
    private final CameraCharacteristicsCompat mCameraCharacteristics;
    private int mExposureCompensation;
    private final Object mLock = new Object();

    ExposureStateImpl(CameraCharacteristicsCompat cameraCharacteristicsCompat, int i) {
        this.mCameraCharacteristics = cameraCharacteristicsCompat;
        this.mExposureCompensation = i;
    }

    public int getExposureCompensationIndex() {
        int i;
        synchronized (this.mLock) {
            i = this.mExposureCompensation;
        }
        return i;
    }

    void setExposureCompensationIndex(int i) {
        synchronized (this.mLock) {
            this.mExposureCompensation = i;
        }
    }
}
