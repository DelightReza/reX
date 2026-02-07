package p017j$.time.format;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import org.mvel2.asm.signature.SignatureVisitor;
import p017j$.time.C1640b;
import p017j$.time.C1678e;
import p017j$.time.chrono.C1668r;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.temporal.AbstractC1736j;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.util.Objects;

/* loaded from: classes2.dex */
public final class DateTimeFormatter {
    public static final DateTimeFormatter ISO_LOCAL_DATE;

    /* renamed from: f */
    public static final DateTimeFormatter f548f;

    /* renamed from: a */
    public final C1690d f549a;

    /* renamed from: b */
    public final Locale f550b;

    /* renamed from: c */
    public final C1682C f551c;

    /* renamed from: d */
    public final EnumC1684E f552d;

    /* renamed from: e */
    public final InterfaceC1661k f553e;

    /* JADX WARN: Removed duplicated region for block: B:119:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x026f  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0294  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x048a  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x04a3 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static p017j$.time.format.DateTimeFormatter ofPattern(java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 1318
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.time.format.DateTimeFormatter.ofPattern(java.lang.String):j$.time.format.DateTimeFormatter");
    }

    static {
        C1707u c1707u = new C1707u();
        EnumC1727a enumC1727a = EnumC1727a.YEAR;
        EnumC1685F enumC1685F = EnumC1685F.EXCEEDS_PAD;
        c1707u.m755m(enumC1727a, 4, 10, enumC1685F);
        c1707u.m746d(SignatureVisitor.SUPER);
        EnumC1727a enumC1727a2 = EnumC1727a.MONTH_OF_YEAR;
        c1707u.m754l(enumC1727a2, 2);
        c1707u.m746d(SignatureVisitor.SUPER);
        EnumC1727a enumC1727a3 = EnumC1727a.DAY_OF_MONTH;
        c1707u.m754l(enumC1727a3, 2);
        EnumC1684E enumC1684E = EnumC1684E.STRICT;
        C1668r c1668r = C1668r.f512c;
        DateTimeFormatter dateTimeFormatterM758p = c1707u.m758p(enumC1684E, c1668r);
        ISO_LOCAL_DATE = dateTimeFormatterM758p;
        C1707u c1707u2 = new C1707u();
        EnumC1702p enumC1702p = EnumC1702p.INSENSITIVE;
        c1707u2.m745c(enumC1702p);
        c1707u2.m743a(dateTimeFormatterM758p);
        C1696j c1696j = C1696j.f580e;
        c1707u2.m745c(c1696j);
        c1707u2.m758p(enumC1684E, c1668r);
        C1707u c1707u3 = new C1707u();
        c1707u3.m745c(enumC1702p);
        c1707u3.m743a(dateTimeFormatterM758p);
        c1707u3.m757o();
        c1707u3.m745c(c1696j);
        c1707u3.m758p(enumC1684E, c1668r);
        C1707u c1707u4 = new C1707u();
        EnumC1727a enumC1727a4 = EnumC1727a.HOUR_OF_DAY;
        c1707u4.m754l(enumC1727a4, 2);
        c1707u4.m746d(':');
        EnumC1727a enumC1727a5 = EnumC1727a.MINUTE_OF_HOUR;
        c1707u4.m754l(enumC1727a5, 2);
        c1707u4.m757o();
        c1707u4.m746d(':');
        EnumC1727a enumC1727a6 = EnumC1727a.SECOND_OF_MINUTE;
        c1707u4.m754l(enumC1727a6, 2);
        c1707u4.m757o();
        c1707u4.m744b(EnumC1727a.NANO_OF_SECOND, 0, 9, true);
        DateTimeFormatter dateTimeFormatterM758p2 = c1707u4.m758p(enumC1684E, null);
        C1707u c1707u5 = new C1707u();
        c1707u5.m745c(enumC1702p);
        c1707u5.m743a(dateTimeFormatterM758p2);
        c1707u5.m745c(c1696j);
        c1707u5.m758p(enumC1684E, null);
        C1707u c1707u6 = new C1707u();
        c1707u6.m745c(enumC1702p);
        c1707u6.m743a(dateTimeFormatterM758p2);
        c1707u6.m757o();
        c1707u6.m745c(c1696j);
        c1707u6.m758p(enumC1684E, null);
        C1707u c1707u7 = new C1707u();
        c1707u7.m745c(enumC1702p);
        c1707u7.m743a(dateTimeFormatterM758p);
        c1707u7.m746d('T');
        c1707u7.m743a(dateTimeFormatterM758p2);
        DateTimeFormatter dateTimeFormatterM758p3 = c1707u7.m758p(enumC1684E, c1668r);
        C1707u c1707u8 = new C1707u();
        c1707u8.m745c(enumC1702p);
        c1707u8.m743a(dateTimeFormatterM758p3);
        EnumC1702p enumC1702p2 = EnumC1702p.LENIENT;
        c1707u8.m745c(enumC1702p2);
        c1707u8.m745c(c1696j);
        EnumC1702p enumC1702p3 = EnumC1702p.STRICT;
        c1707u8.m745c(enumC1702p3);
        DateTimeFormatter dateTimeFormatterM758p4 = c1707u8.m758p(enumC1684E, c1668r);
        C1707u c1707u9 = new C1707u();
        c1707u9.m743a(dateTimeFormatterM758p4);
        c1707u9.m757o();
        c1707u9.m746d('[');
        EnumC1702p enumC1702p4 = EnumC1702p.SENSITIVE;
        c1707u9.m745c(enumC1702p4);
        C1678e c1678e = C1707u.f616h;
        c1707u9.m745c(new C1705s(c1678e, "ZoneRegionId()"));
        c1707u9.m746d(']');
        c1707u9.m758p(enumC1684E, c1668r);
        C1707u c1707u10 = new C1707u();
        c1707u10.m743a(dateTimeFormatterM758p3);
        c1707u10.m757o();
        c1707u10.m745c(c1696j);
        c1707u10.m757o();
        c1707u10.m746d('[');
        c1707u10.m745c(enumC1702p4);
        c1707u10.m745c(new C1705s(c1678e, "ZoneRegionId()"));
        c1707u10.m746d(']');
        c1707u10.m758p(enumC1684E, c1668r);
        C1707u c1707u11 = new C1707u();
        c1707u11.m745c(enumC1702p);
        c1707u11.m755m(enumC1727a, 4, 10, enumC1685F);
        c1707u11.m746d(SignatureVisitor.SUPER);
        c1707u11.m754l(EnumC1727a.DAY_OF_YEAR, 3);
        c1707u11.m757o();
        c1707u11.m745c(c1696j);
        c1707u11.m758p(enumC1684E, c1668r);
        C1707u c1707u12 = new C1707u();
        c1707u12.m745c(enumC1702p);
        c1707u12.m755m(AbstractC1736j.f681c, 4, 10, enumC1685F);
        c1707u12.m747e("-W");
        c1707u12.m754l(AbstractC1736j.f680b, 2);
        c1707u12.m746d(SignatureVisitor.SUPER);
        EnumC1727a enumC1727a7 = EnumC1727a.DAY_OF_WEEK;
        c1707u12.m754l(enumC1727a7, 1);
        c1707u12.m757o();
        c1707u12.m745c(c1696j);
        c1707u12.m758p(enumC1684E, c1668r);
        C1707u c1707u13 = new C1707u();
        c1707u13.m745c(enumC1702p);
        c1707u13.m745c(new C1693g());
        f548f = c1707u13.m758p(enumC1684E, null);
        C1707u c1707u14 = new C1707u();
        c1707u14.m745c(enumC1702p);
        c1707u14.m754l(enumC1727a, 4);
        c1707u14.m754l(enumC1727a2, 2);
        c1707u14.m754l(enumC1727a3, 2);
        c1707u14.m757o();
        c1707u14.m745c(enumC1702p2);
        c1707u14.m749g("+HHMMss", "Z");
        c1707u14.m745c(enumC1702p3);
        c1707u14.m758p(enumC1684E, c1668r);
        HashMap map = new HashMap();
        map.put(1L, "Mon");
        map.put(2L, "Tue");
        map.put(3L, "Wed");
        map.put(4L, "Thu");
        map.put(5L, "Fri");
        map.put(6L, "Sat");
        map.put(7L, "Sun");
        HashMap map2 = new HashMap();
        map2.put(1L, "Jan");
        map2.put(2L, "Feb");
        map2.put(3L, "Mar");
        map2.put(4L, "Apr");
        map2.put(5L, "May");
        map2.put(6L, "Jun");
        map2.put(7L, "Jul");
        map2.put(8L, "Aug");
        map2.put(9L, "Sep");
        map2.put(10L, "Oct");
        map2.put(11L, "Nov");
        map2.put(12L, "Dec");
        C1707u c1707u15 = new C1707u();
        c1707u15.m745c(enumC1702p);
        c1707u15.m745c(enumC1702p2);
        c1707u15.m757o();
        c1707u15.m750h(enumC1727a7, map);
        c1707u15.m747e(", ");
        c1707u15.m756n();
        c1707u15.m755m(enumC1727a3, 1, 2, EnumC1685F.NOT_NEGATIVE);
        c1707u15.m746d(' ');
        c1707u15.m750h(enumC1727a2, map2);
        c1707u15.m746d(' ');
        c1707u15.m754l(enumC1727a, 4);
        c1707u15.m746d(' ');
        c1707u15.m754l(enumC1727a4, 2);
        c1707u15.m746d(':');
        c1707u15.m754l(enumC1727a5, 2);
        c1707u15.m757o();
        c1707u15.m746d(':');
        c1707u15.m754l(enumC1727a6, 2);
        c1707u15.m756n();
        c1707u15.m746d(' ');
        c1707u15.m749g("+HHMM", "GMT");
        c1707u15.m758p(EnumC1684E.SMART, c1668r);
    }

    public DateTimeFormatter(C1690d c1690d, Locale locale, EnumC1684E enumC1684E, InterfaceC1661k interfaceC1661k) {
        C1682C c1682c = C1682C.f539a;
        this.f549a = (C1690d) Objects.requireNonNull(c1690d, "printerParser");
        this.f550b = (Locale) Objects.requireNonNull(locale, "locale");
        this.f551c = (C1682C) Objects.requireNonNull(c1682c, "decimalStyle");
        this.f552d = (EnumC1684E) Objects.requireNonNull(enumC1684E, "resolverStyle");
        this.f553e = interfaceC1661k;
    }

    /* renamed from: a */
    public final String m719a(InterfaceC1740n interfaceC1740n) {
        StringBuilder sb = new StringBuilder(32);
        C1690d c1690d = this.f549a;
        Objects.requireNonNull(interfaceC1740n, "temporal");
        Objects.requireNonNull(sb, "appendable");
        try {
            c1690d.mo721i(new C1711y(interfaceC1740n, this), sb);
            return sb.toString();
        } catch (IOException e) {
            throw new C1640b(e.getMessage(), e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:132:0x0330  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0275  */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final p017j$.time.format.C1683D m720b(java.lang.CharSequence r27) {
        /*
            Method dump skipped, instructions count: 1106
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.time.format.DateTimeFormatter.m720b(java.lang.CharSequence):j$.time.format.D");
    }

    public final String toString() {
        String string = this.f549a.toString();
        return string.startsWith("[") ? string : string.substring(1, string.length() - 1);
    }
}
