package kotlinx.serialization.json.internal;

import java.util.Map;
import p017j$.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public abstract class CreateMapForCacheKt {
    public static final Map createMapForCache(int i) {
        return new ConcurrentHashMap(i);
    }
}
