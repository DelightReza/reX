package p017j$.time.format;

import java.lang.ref.SoftReference;
import java.text.DateFormatSymbols;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.zone.C1760h;
import p017j$.util.Objects;
import p017j$.util.concurrent.ConcurrentHashMap;

/* renamed from: j$.time.format.t */
/* loaded from: classes2.dex */
public final class C1706t extends C1705s {

    /* renamed from: i */
    public static final ConcurrentHashMap f611i = new ConcurrentHashMap();

    /* renamed from: e */
    public final TextStyle f612e;

    /* renamed from: f */
    public final boolean f613f;

    /* renamed from: g */
    public final Map f614g;

    /* renamed from: h */
    public final Map f615h;

    public C1706t(TextStyle textStyle, boolean z) {
        super(AbstractC1745s.f694e, "ZoneText(" + textStyle + ")");
        this.f614g = new HashMap();
        this.f615h = new HashMap();
        this.f612e = (TextStyle) Objects.requireNonNull(textStyle, "textStyle");
        this.f613f = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:19:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00f5  */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    @Override // p017j$.time.format.C1705s, p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean mo721i(p017j$.time.format.C1711y r14, java.lang.StringBuilder r15) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.time.format.C1706t.mo721i(j$.time.format.y, java.lang.StringBuilder):boolean");
    }

    @Override // p017j$.time.format.C1705s
    /* renamed from: a */
    public final C1699m mo742a(C1708v c1708v) {
        C1699m c1699m;
        if (this.f612e == TextStyle.NARROW) {
            return super.mo742a(c1708v);
        }
        Locale locale = c1708v.f625a.f550b;
        boolean z = c1708v.f626b;
        Set set = C1760h.f755d;
        int size = set.size();
        Map map = z ? this.f614g : this.f615h;
        Map.Entry entry = (Map.Entry) map.get(locale);
        if (entry != null && ((Integer) entry.getKey()).intValue() == size && (c1699m = (C1699m) ((SoftReference) entry.getValue()).get()) != null) {
            return c1699m;
        }
        C1699m c1699m2 = c1708v.f626b ? new C1699m("", null, null) : new C1698l("", null, null);
        for (String[] strArr : DateFormatSymbols.getInstance(locale).getZoneStrings()) {
            String str = strArr[0];
            if (set.contains(str)) {
                c1699m2.m738a(str, str);
                HashMap map2 = (HashMap) AbstractC1686G.f559d;
                String str2 = (String) map2.get(str);
                if (str2 == null) {
                    HashMap map3 = (HashMap) AbstractC1686G.f562g;
                    if (map3.containsKey(str)) {
                        str = (String) map3.get(str);
                        str2 = (String) map2.get(str);
                    }
                }
                if (str2 != null) {
                    Map map4 = (Map) ((HashMap) AbstractC1686G.f561f).get(str2);
                    str = (map4 == null || !map4.containsKey(locale.getCountry())) ? (String) ((HashMap) AbstractC1686G.f560e).get(str2) : (String) map4.get(locale.getCountry());
                }
                HashMap map5 = (HashMap) AbstractC1686G.f562g;
                if (map5.containsKey(str)) {
                    str = (String) map5.get(str);
                }
                for (int i = this.f612e == TextStyle.FULL ? 1 : 2; i < strArr.length; i += 2) {
                    c1699m2.m738a(strArr[i], str);
                }
            }
        }
        map.put(locale, new AbstractMap.SimpleImmutableEntry(Integer.valueOf(size), new SoftReference(c1699m2)));
        return c1699m2;
    }
}
