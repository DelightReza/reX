package p017j$.time.format;

import java.text.ParsePosition;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;
import p017j$.time.C1640b;
import p017j$.time.C1678e;
import p017j$.time.ZoneId;
import p017j$.time.ZoneOffset;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.zone.C1760h;

/* renamed from: j$.time.format.s */
/* loaded from: classes2.dex */
public class C1705s implements InterfaceC1691e {

    /* renamed from: c */
    public static volatile Map.Entry f607c;

    /* renamed from: d */
    public static volatile Map.Entry f608d;

    /* renamed from: a */
    public final C1678e f609a;

    /* renamed from: b */
    public final String f610b;

    /* renamed from: a */
    public C1699m mo742a(C1708v c1708v) {
        Set<String> set = C1760h.f755d;
        int size = set.size();
        Map.Entry simpleImmutableEntry = c1708v.f626b ? f607c : f608d;
        if (simpleImmutableEntry == null || ((Integer) simpleImmutableEntry.getKey()).intValue() != size) {
            synchronized (this) {
                try {
                    simpleImmutableEntry = c1708v.f626b ? f607c : f608d;
                    if (simpleImmutableEntry == null || ((Integer) simpleImmutableEntry.getKey()).intValue() != size) {
                        Integer numValueOf = Integer.valueOf(size);
                        C1699m c1699m = c1708v.f626b ? new C1699m("", null, null) : new C1698l("", null, null);
                        for (String str : set) {
                            c1699m.m738a(str, str);
                        }
                        simpleImmutableEntry = new AbstractMap.SimpleImmutableEntry(numValueOf, c1699m);
                        if (c1708v.f626b) {
                            f607c = simpleImmutableEntry;
                        } else {
                            f608d = simpleImmutableEntry;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return (C1699m) simpleImmutableEntry.getValue();
    }

    public C1705s(C1678e c1678e, String str) {
        this.f609a = c1678e;
        this.f610b = str;
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    public boolean mo721i(C1711y c1711y, StringBuilder sb) {
        ZoneId zoneId = (ZoneId) c1711y.m768b(this.f609a);
        if (zoneId == null) {
            return false;
        }
        sb.append(zoneId.getId());
        return true;
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    public final int mo722j(C1708v c1708v, CharSequence charSequence, int i) {
        int i2;
        int length = charSequence.length();
        if (i > length) {
            throw new IndexOutOfBoundsException();
        }
        if (i == length) {
            return ~i;
        }
        char cCharAt = charSequence.charAt(i);
        if (cCharAt == '+' || cCharAt == '-') {
            return m741b(c1708v, charSequence, i, i, C1696j.f580e);
        }
        int i3 = i + 2;
        if (length >= i3) {
            char cCharAt2 = charSequence.charAt(i + 1);
            if (c1708v.m761a(cCharAt, 'U') && c1708v.m761a(cCharAt2, 'T')) {
                int i4 = i + 3;
                if (length >= i4 && c1708v.m761a(charSequence.charAt(i3), 'C')) {
                    return m741b(c1708v, charSequence, i, i4, C1696j.f581f);
                }
                return m741b(c1708v, charSequence, i, i3, C1696j.f581f);
            }
            if (c1708v.m761a(cCharAt, 'G') && length >= (i2 = i + 3) && c1708v.m761a(cCharAt2, 'M') && c1708v.m761a(charSequence.charAt(i3), 'T')) {
                int i5 = i + 4;
                if (length >= i5 && c1708v.m761a(charSequence.charAt(i2), '0')) {
                    c1708v.m764e(ZoneId.m622of("GMT0"));
                    return i5;
                }
                return m741b(c1708v, charSequence, i, i2, C1696j.f581f);
            }
        }
        C1699m c1699mMo742a = mo742a(c1708v);
        ParsePosition parsePosition = new ParsePosition(i);
        String strM739c = c1699mMo742a.m739c(charSequence, parsePosition);
        if (strM739c == null) {
            if (!c1708v.m761a(cCharAt, 'Z')) {
                return ~i;
            }
            c1708v.m764e(ZoneOffset.UTC);
            return i + 1;
        }
        c1708v.m764e(ZoneId.m622of(strM739c));
        return parsePosition.getIndex();
    }

    /* renamed from: b */
    public static int m741b(C1708v c1708v, CharSequence charSequence, int i, int i2, C1696j c1696j) {
        String upperCase = charSequence.subSequence(i, i2).toString().toUpperCase();
        if (i2 >= charSequence.length()) {
            c1708v.m764e(ZoneId.m622of(upperCase));
            return i2;
        }
        if (charSequence.charAt(i2) != '0' && !c1708v.m761a(charSequence.charAt(i2), 'Z')) {
            C1708v c1708v2 = new C1708v(c1708v.f625a);
            c1708v2.f626b = c1708v.f626b;
            c1708v2.f627c = c1708v.f627c;
            int iMo722j = c1696j.mo722j(c1708v2, charSequence, i2);
            try {
                if (iMo722j < 0) {
                    if (c1696j == C1696j.f580e) {
                        return ~i;
                    }
                    c1708v.m764e(ZoneId.m622of(upperCase));
                    return i2;
                }
                c1708v.m764e(ZoneId.m620R(upperCase, ZoneOffset.m626W((int) c1708v2.m763d(EnumC1727a.OFFSET_SECONDS).longValue())));
                return iMo722j;
            } catch (C1640b unused) {
                return ~i;
            }
        }
        c1708v.m764e(ZoneId.m622of(upperCase));
        return i2;
    }

    public final String toString() {
        return this.f610b;
    }
}
