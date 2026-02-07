package androidx.camera.camera2.interop;

import android.hardware.camera2.CameraCharacteristics;
import android.util.Pair;
import androidx.camera.camera2.internal.Camera2CameraInfoImpl;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.impl.AdapterCameraInfo;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.core.util.Preconditions;
import java.util.List;

/* loaded from: classes3.dex */
public final class Camera2CameraInfo {
    private Camera2CameraInfoImpl mCamera2CameraInfoImpl;
    private List mExtensionsSpecificChars;

    public Camera2CameraInfo(Camera2CameraInfoImpl camera2CameraInfoImpl) {
        this.mCamera2CameraInfoImpl = camera2CameraInfoImpl;
    }

    public static Camera2CameraInfo from(CameraInfo cameraInfo) {
        CameraInfoInternal implementation = ((CameraInfoInternal) cameraInfo).getImplementation();
        Preconditions.checkArgument(implementation instanceof Camera2CameraInfoImpl, "CameraInfo doesn't contain Camera2 implementation.");
        Camera2CameraInfo camera2CameraInfo = ((Camera2CameraInfoImpl) implementation).getCamera2CameraInfo();
        if (cameraInfo instanceof AdapterCameraInfo) {
            ((AdapterCameraInfo) cameraInfo).getSessionProcessor();
        }
        return camera2CameraInfo;
    }

    public String getCameraId() {
        return this.mCamera2CameraInfoImpl.getCameraId();
    }

    public Object getCameraCharacteristic(CameraCharacteristics.Key key) {
        List<Pair> list = this.mExtensionsSpecificChars;
        if (list != null) {
            for (Pair pair : list) {
                if (((CameraCharacteristics.Key) pair.first).equals(key)) {
                    return pair.second;
                }
            }
        }
        return this.mCamera2CameraInfoImpl.getCameraCharacteristicsCompat().get(key);
    }
}
