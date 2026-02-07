package p017j$.time.format;

import java.util.ArrayList;
import java.util.HashMap;
import p017j$.time.ZoneId;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.util.Objects;

/* renamed from: j$.time.format.v */
/* loaded from: classes2.dex */
public final class C1708v {

    /* renamed from: a */
    public final DateTimeFormatter f625a;

    /* renamed from: b */
    public boolean f626b = true;

    /* renamed from: c */
    public boolean f627c = true;

    /* renamed from: d */
    public final ArrayList f628d;

    /* renamed from: e */
    public ArrayList f629e;

    public C1708v(DateTimeFormatter dateTimeFormatter) {
        ArrayList arrayList = new ArrayList();
        this.f628d = arrayList;
        this.f629e = null;
        this.f625a = dateTimeFormatter;
        arrayList.add(new C1683D());
    }

    /* renamed from: a */
    public final boolean m761a(char c, char c2) {
        if (this.f626b) {
            return c == c2;
        }
        return m760b(c, c2);
    }

    /* renamed from: g */
    public final boolean m766g(CharSequence charSequence, int i, CharSequence charSequence2, int i2, int i3) {
        if (i + i3 <= charSequence.length() && i2 + i3 <= charSequence2.length()) {
            if (this.f626b) {
                for (int i4 = 0; i4 < i3; i4++) {
                    if (charSequence.charAt(i + i4) == charSequence2.charAt(i2 + i4)) {
                    }
                }
                return true;
            }
            for (int i5 = 0; i5 < i3; i5++) {
                char cCharAt = charSequence.charAt(i + i5);
                char cCharAt2 = charSequence2.charAt(i2 + i5);
                if (cCharAt == cCharAt2 || Character.toUpperCase(cCharAt) == Character.toUpperCase(cCharAt2) || Character.toLowerCase(cCharAt) == Character.toLowerCase(cCharAt2)) {
                }
            }
            return true;
        }
        return false;
    }

    /* renamed from: b */
    public static boolean m760b(char c, char c2) {
        return c == c2 || Character.toUpperCase(c) == Character.toUpperCase(c2) || Character.toLowerCase(c) == Character.toLowerCase(c2);
    }

    /* renamed from: c */
    public final C1683D m762c() {
        return (C1683D) this.f628d.get(r0.size() - 1);
    }

    /* renamed from: d */
    public final Long m763d(EnumC1727a enumC1727a) {
        return (Long) ((HashMap) m762c().f540a).get(enumC1727a);
    }

    /* renamed from: f */
    public final int m765f(InterfaceC1744r interfaceC1744r, long j, int i, int i2) {
        Objects.requireNonNull(interfaceC1744r, "field");
        Long l = (Long) ((HashMap) m762c().f540a).put(interfaceC1744r, Long.valueOf(j));
        return (l == null || l.longValue() == j) ? i2 : ~i;
    }

    /* renamed from: e */
    public final void m764e(ZoneId zoneId) {
        Objects.requireNonNull(zoneId, "zone");
        m762c().f541b = zoneId;
    }

    public final String toString() {
        return m762c().toString();
    }
}
