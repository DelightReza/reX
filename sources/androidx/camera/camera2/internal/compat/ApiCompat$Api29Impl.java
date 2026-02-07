package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraManager;

/* loaded from: classes3.dex */
public abstract class ApiCompat$Api29Impl {
    public static void onCameraAccessPrioritiesChanged(CameraManager.AvailabilityCallback availabilityCallback) {
        availabilityCallback.onCameraAccessPrioritiesChanged();
    }
}
