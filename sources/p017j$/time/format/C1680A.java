package p017j$.time.format;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import p017j$.util.concurrent.ConcurrentHashMap;

/* renamed from: j$.time.format.A */
/* loaded from: classes2.dex */
public final class C1680A {

    /* renamed from: a */
    public final Map f534a;

    /* renamed from: b */
    public final Map f535b;

    public C1680A(Map map) {
        this.f534a = map;
        HashMap map2 = new HashMap();
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : map.entrySet()) {
            HashMap map3 = new HashMap();
            for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                String str = (String) entry2.getValue();
                String str2 = (String) entry2.getValue();
                Long l = (Long) entry2.getKey();
                ConcurrentHashMap concurrentHashMap = C1681B.f536a;
                map3.put(str, new AbstractMap.SimpleImmutableEntry(str2, l));
            }
            ArrayList arrayList2 = new ArrayList(map3.values());
            Collections.sort(arrayList2, C1681B.f537b);
            map2.put((TextStyle) entry.getKey(), arrayList2);
            arrayList.addAll(arrayList2);
            map2.put(null, arrayList);
        }
        Collections.sort(arrayList, C1681B.f537b);
        this.f535b = map2;
    }

    /* renamed from: a */
    public final String m705a(long j, TextStyle textStyle) {
        Map map = (Map) this.f534a.get(textStyle);
        if (map != null) {
            return (String) map.get(Long.valueOf(j));
        }
        return null;
    }
}
