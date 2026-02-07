package androidx.camera.core.impl;

import android.content.Context;
import android.util.Size;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public interface CameraDeviceSurfaceManager {

    public interface Provider {
        CameraDeviceSurfaceManager newInstance(Context context, Object obj, Set set);
    }

    SurfaceStreamSpecQueryResult getSuggestedStreamSpecs(int i, String str, List list, Map map, boolean z, boolean z2, boolean z3, boolean z4);

    SurfaceConfig transformSurfaceConfig(int i, String str, int i2, Size size, StreamUseCase streamUseCase);
}
