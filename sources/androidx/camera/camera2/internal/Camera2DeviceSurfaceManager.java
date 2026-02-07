package androidx.camera.camera2.internal;

import android.content.Context;
import android.media.CamcorderProfile;
import android.os.Build;
import android.util.Size;
import androidx.camera.camera2.impl.FeatureCombinationQueryImpl;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.core.featuregroup.impl.FeatureCombinationQuery;
import androidx.camera.core.impl.CameraDeviceSurfaceManager;
import androidx.camera.core.impl.StreamUseCase;
import androidx.camera.core.impl.SurfaceConfig;
import androidx.camera.core.impl.SurfaceStreamSpecQueryResult;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public final class Camera2DeviceSurfaceManager implements CameraDeviceSurfaceManager {
    private final CamcorderProfileHelper mCamcorderProfileHelper;
    private final Map mCameraSupportedSurfaceCombinationMap;

    public Camera2DeviceSurfaceManager(Context context, Object obj, Set set) {
        this(context, new CamcorderProfileHelper() { // from class: androidx.camera.camera2.internal.Camera2DeviceSurfaceManager.1
            @Override // androidx.camera.camera2.internal.CamcorderProfileHelper
            public boolean hasProfile(int i, int i2) {
                return CamcorderProfile.hasProfile(i, i2);
            }

            @Override // androidx.camera.camera2.internal.CamcorderProfileHelper
            public CamcorderProfile get(int i, int i2) {
                return CamcorderProfile.get(i, i2);
            }
        }, obj, set);
    }

    Camera2DeviceSurfaceManager(Context context, CamcorderProfileHelper camcorderProfileHelper, Object obj, Set set) {
        CameraManagerCompat cameraManagerCompatFrom;
        this.mCameraSupportedSurfaceCombinationMap = new HashMap();
        Preconditions.checkNotNull(camcorderProfileHelper);
        this.mCamcorderProfileHelper = camcorderProfileHelper;
        if (obj instanceof CameraManagerCompat) {
            cameraManagerCompatFrom = (CameraManagerCompat) obj;
        } else {
            cameraManagerCompatFrom = CameraManagerCompat.from(context);
        }
        init(context, cameraManagerCompatFrom, set);
    }

    private void init(Context context, CameraManagerCompat cameraManagerCompat, Set set) {
        Preconditions.checkNotNull(context);
        FeatureCombinationQuery featureCombinationQueryImpl = FeatureCombinationQuery.NO_OP_FEATURE_COMBINATION_QUERY;
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (Build.VERSION.SDK_INT >= 35) {
                featureCombinationQueryImpl = new FeatureCombinationQueryImpl(context, str, cameraManagerCompat);
            }
            FeatureCombinationQuery featureCombinationQuery = featureCombinationQueryImpl;
            this.mCameraSupportedSurfaceCombinationMap.put(str, new SupportedSurfaceCombination(context, str, cameraManagerCompat, this.mCamcorderProfileHelper, featureCombinationQuery));
            featureCombinationQueryImpl = featureCombinationQuery;
        }
    }

    @Override // androidx.camera.core.impl.CameraDeviceSurfaceManager
    public SurfaceConfig transformSurfaceConfig(int i, String str, int i2, Size size, StreamUseCase streamUseCase) {
        SupportedSurfaceCombination supportedSurfaceCombination = (SupportedSurfaceCombination) this.mCameraSupportedSurfaceCombinationMap.get(str);
        Preconditions.checkArgument(supportedSurfaceCombination != null, "No such camera id in supported combination list: " + str);
        return supportedSurfaceCombination.transformSurfaceConfig(i, i2, size, streamUseCase);
    }

    @Override // androidx.camera.core.impl.CameraDeviceSurfaceManager
    public SurfaceStreamSpecQueryResult getSuggestedStreamSpecs(int i, String str, List list, Map map, boolean z, boolean z2, boolean z3, boolean z4) {
        Preconditions.checkArgument(!map.isEmpty(), "No new use cases to be bound.");
        SupportedSurfaceCombination supportedSurfaceCombination = (SupportedSurfaceCombination) this.mCameraSupportedSurfaceCombinationMap.get(str);
        Preconditions.checkArgument(supportedSurfaceCombination != null, "No such camera id in supported combination list: " + str);
        return supportedSurfaceCombination.getSuggestedStreamSpecifications(i, list, map, z, z2, z3, z4);
    }
}
