package androidx.camera.camera2.internal;

import android.content.Context;
import android.util.Log;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.camera2.internal.concurrent.Camera2CameraCoordinator;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.CameraXConfig;
import androidx.camera.core.InitializationException;
import androidx.camera.core.Logger;
import androidx.camera.core.concurrent.CameraCoordinator;
import androidx.camera.core.impl.CameraFactory;
import androidx.camera.core.impl.CameraInternal;
import androidx.camera.core.impl.CameraStateRegistry;
import androidx.camera.core.impl.CameraThreadConfig;
import androidx.camera.core.internal.StreamSpecsCalculator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.mvel2.MVEL;

/* loaded from: classes3.dex */
public final class Camera2CameraFactory implements CameraFactory {
    private final CameraSelector mAvailableCamerasSelector;
    private final CameraCoordinator mCameraCoordinator;
    private final CameraManagerCompat mCameraManager;
    private final long mCameraOpenRetryMaxTimeoutInMs;
    private final Camera2PresenceSource mCameraPresenceObservable;
    private final CameraStateRegistry mCameraStateRegistry;
    private final CameraXConfig mCameraXConfig;
    private final Context mContext;
    private final DisplayInfoManager mDisplayInfoManager;
    private final StreamSpecsCalculator mStreamSpecsCalculator;
    private final CameraThreadConfig mThreadConfig;
    private final Map mCameraInfos = new HashMap();
    private final Object mLock = new Object();
    private List mAvailableCameraIds = new ArrayList();

    public Camera2CameraFactory(Context context, CameraThreadConfig cameraThreadConfig, CameraSelector cameraSelector, long j, CameraXConfig cameraXConfig, StreamSpecsCalculator streamSpecsCalculator) throws InitializationException {
        this.mContext = context;
        this.mThreadConfig = cameraThreadConfig;
        CameraManagerCompat cameraManagerCompatFrom = CameraManagerCompat.from(context, cameraThreadConfig.getSchedulerHandler());
        this.mCameraManager = cameraManagerCompatFrom;
        this.mDisplayInfoManager = DisplayInfoManager.getInstance(context);
        Camera2CameraCoordinator camera2CameraCoordinator = new Camera2CameraCoordinator(cameraManagerCompatFrom);
        this.mCameraCoordinator = camera2CameraCoordinator;
        CameraStateRegistry cameraStateRegistry = new CameraStateRegistry(camera2CameraCoordinator, 1);
        this.mCameraStateRegistry = cameraStateRegistry;
        camera2CameraCoordinator.addListener(cameraStateRegistry);
        this.mCameraOpenRetryMaxTimeoutInMs = j;
        this.mStreamSpecsCalculator = streamSpecsCalculator;
        this.mCameraXConfig = cameraXConfig;
        this.mAvailableCamerasSelector = cameraSelector;
        this.mCameraPresenceObservable = new Camera2PresenceSource(cameraManagerCompatFrom, cameraThreadConfig.getCameraExecutor());
        try {
            onCameraIdsUpdated(Arrays.asList(cameraManagerCompatFrom.getCameraIdList()));
        } catch (CameraAccessExceptionCompat e) {
            throw new InitializationException(CameraUnavailableExceptionHelper.createFrom(e));
        }
    }

    public void onCameraIdsUpdated(List list) throws InitializationException {
        try {
            List backwardCompatibleCameraIds = getBackwardCompatibleCameraIds(CameraSelectionOptimizer.getSelectedAvailableCameraIds(this, this.mAvailableCamerasSelector, new ArrayList(list)));
            synchronized (this.mLock) {
                try {
                    if (this.mAvailableCameraIds.equals(backwardCompatibleCameraIds)) {
                        return;
                    }
                    Logger.m43d("Camera2CameraFactory", "Updated available camera list: " + this.mAvailableCameraIds + " -> " + backwardCompatibleCameraIds);
                    this.mAvailableCameraIds = backwardCompatibleCameraIds;
                } catch (Throwable th) {
                    throw th;
                }
            }
        } catch (InitializationException e) {
            Log.e("Camera2CameraFactory", "Unable to get backward compatible camera ids", e);
            throw e;
        }
    }

    @Override // androidx.camera.core.impl.CameraFactory
    public CameraInternal getCamera(String str) {
        synchronized (this.mLock) {
            if (!this.mAvailableCameraIds.contains(str)) {
                throw new IllegalArgumentException("The given camera id is not on the available camera id list.");
            }
        }
        return new Camera2CameraImpl(this.mContext, this.mCameraManager, str, getCameraInfo(str), this.mCameraCoordinator, this.mCameraStateRegistry, this.mThreadConfig.getCameraExecutor(), this.mThreadConfig.getSchedulerHandler(), this.mDisplayInfoManager, this.mCameraOpenRetryMaxTimeoutInMs, this.mCameraXConfig);
    }

    Camera2CameraInfoImpl getCameraInfo(String str) throws CameraUnavailableException {
        try {
            Camera2CameraInfoImpl camera2CameraInfoImpl = (Camera2CameraInfoImpl) this.mCameraInfos.get(str);
            if (camera2CameraInfoImpl != null) {
                return camera2CameraInfoImpl;
            }
            Camera2CameraInfoImpl camera2CameraInfoImpl2 = new Camera2CameraInfoImpl(str, this.mCameraManager, this.mStreamSpecsCalculator);
            this.mCameraInfos.put(str, camera2CameraInfoImpl2);
            return camera2CameraInfoImpl2;
        } catch (CameraAccessExceptionCompat e) {
            throw CameraUnavailableExceptionHelper.createFrom(e);
        }
    }

    @Override // androidx.camera.core.impl.CameraFactory
    public Set getAvailableCameraIds() {
        LinkedHashSet linkedHashSet;
        synchronized (this.mLock) {
            linkedHashSet = new LinkedHashSet(this.mAvailableCameraIds);
        }
        return linkedHashSet;
    }

    @Override // androidx.camera.core.impl.CameraFactory
    public CameraCoordinator getCameraCoordinator() {
        return this.mCameraCoordinator;
    }

    @Override // androidx.camera.core.impl.CameraFactory
    public CameraManagerCompat getCameraManager() {
        return this.mCameraManager;
    }

    @Override // androidx.camera.core.impl.CameraFactory
    public void shutdown() {
        this.mCameraCoordinator.shutdown();
        this.mCameraPresenceObservable.stopMonitoring();
    }

    private List getBackwardCompatibleCameraIds(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str.equals(MVEL.VERSION_SUB) || str.equals("1")) {
                arrayList.add(str);
            } else if (CameraIdUtil.isBackwardCompatible(this.mCameraManager, str)) {
                arrayList.add(str);
            } else {
                Logger.m43d("Camera2CameraFactory", "Camera " + str + " is filtered out because its capabilities do not contain REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE.");
            }
        }
        return arrayList;
    }
}
