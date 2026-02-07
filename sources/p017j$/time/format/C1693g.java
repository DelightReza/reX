package p017j$.time.format;

import de.robv.android.xposed.callbacks.XCallback;
import java.util.Locale;
import org.mvel2.asm.signature.SignatureVisitor;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1715i;
import p017j$.time.LocalDate;
import p017j$.time.LocalDateTime;
import p017j$.time.ZoneOffset;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1740n;

/* renamed from: j$.time.format.g */
/* loaded from: classes2.dex */
public final class C1693g implements InterfaceC1691e {
    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    public final boolean mo721i(C1711y c1711y, StringBuilder sb) {
        Long lM767a = c1711y.m767a(EnumC1727a.INSTANT_SECONDS);
        InterfaceC1740n interfaceC1740n = c1711y.f634a;
        EnumC1727a enumC1727a = EnumC1727a.NANO_OF_SECOND;
        Long lValueOf = interfaceC1740n.mo543e(enumC1727a) ? Long.valueOf(interfaceC1740n.mo542D(enumC1727a)) : null;
        int i = 0;
        if (lM767a == null) {
            return false;
        }
        long jLongValue = lM767a.longValue();
        int iM820a = enumC1727a.f671b.m820a(lValueOf != null ? lValueOf.longValue() : 0L, enumC1727a);
        if (jLongValue >= -62167219200L) {
            long j = jLongValue - 253402300800L;
            long jM499R = 1 + AbstractC1636a.m499R(j, 315569520000L);
            LocalDateTime localDateTimeM594U = LocalDateTime.m594U(AbstractC1636a.m498Q(j, 315569520000L) - 62167219200L, 0, ZoneOffset.UTC);
            if (jM499R > 0) {
                sb.append(SignatureVisitor.EXTENDS);
                sb.append(jM499R);
            }
            sb.append(localDateTimeM594U);
            if (localDateTimeM594U.f444b.f646c == 0) {
                sb.append(":00");
            }
        } else {
            long j2 = jLongValue + 62167219200L;
            long j3 = j2 / 315569520000L;
            long j4 = j2 % 315569520000L;
            LocalDateTime localDateTimeM594U2 = LocalDateTime.m594U(j4 - 62167219200L, 0, ZoneOffset.UTC);
            int length = sb.length();
            sb.append(localDateTimeM594U2);
            if (localDateTimeM594U2.f444b.f646c == 0) {
                sb.append(":00");
            }
            if (j3 < 0) {
                if (localDateTimeM594U2.f443a.getYear() == -10000) {
                    sb.replace(length, length + 2, Long.toString(j3 - 1));
                } else if (j4 == 0) {
                    sb.insert(length, j3);
                } else {
                    sb.insert(length + 1, Math.abs(j3));
                }
            }
        }
        if (iM820a > 0) {
            sb.append('.');
            int i2 = 100000000;
            while (true) {
                if (iM820a <= 0 && i % 3 == 0 && i >= -2) {
                    break;
                }
                int i3 = iM820a / i2;
                sb.append((char) (i3 + 48));
                iM820a -= i3 * i2;
                i2 /= 10;
                i++;
            }
        }
        sb.append('Z');
        return true;
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    public final int mo722j(C1708v c1708v, CharSequence charSequence, int i) {
        C1707u c1707u = new C1707u();
        c1707u.m743a(DateTimeFormatter.ISO_LOCAL_DATE);
        c1707u.m746d('T');
        EnumC1727a enumC1727a = EnumC1727a.HOUR_OF_DAY;
        c1707u.m754l(enumC1727a, 2);
        c1707u.m746d(':');
        EnumC1727a enumC1727a2 = EnumC1727a.MINUTE_OF_HOUR;
        c1707u.m754l(enumC1727a2, 2);
        c1707u.m746d(':');
        EnumC1727a enumC1727a3 = EnumC1727a.SECOND_OF_MINUTE;
        c1707u.m754l(enumC1727a3, 2);
        EnumC1727a enumC1727a4 = EnumC1727a.NANO_OF_SECOND;
        int i2 = 1;
        c1707u.m744b(enumC1727a4, 0, 9, true);
        c1707u.m746d('Z');
        C1690d c1690d = c1707u.m759q(Locale.getDefault(), EnumC1684E.SMART, null).f549a;
        if (c1690d.f569b) {
            c1690d = new C1690d(c1690d.f568a, false);
        }
        C1708v c1708v2 = new C1708v(c1708v.f625a);
        c1708v2.f626b = c1708v.f626b;
        c1708v2.f627c = c1708v.f627c;
        int iMo722j = c1690d.mo722j(c1708v2, charSequence, i);
        if (iMo722j < 0) {
            return iMo722j;
        }
        long jLongValue = c1708v2.m763d(EnumC1727a.YEAR).longValue();
        int iIntValue = c1708v2.m763d(EnumC1727a.MONTH_OF_YEAR).intValue();
        int iIntValue2 = c1708v2.m763d(EnumC1727a.DAY_OF_MONTH).intValue();
        int iIntValue3 = c1708v2.m763d(enumC1727a).intValue();
        int iIntValue4 = c1708v2.m763d(enumC1727a2).intValue();
        Long lM763d = c1708v2.m763d(enumC1727a3);
        Long lM763d2 = c1708v2.m763d(enumC1727a4);
        int iIntValue5 = lM763d != null ? lM763d.intValue() : 0;
        int iIntValue6 = lM763d2 != null ? lM763d2.intValue() : 0;
        if (iIntValue3 == 24 && iIntValue4 == 0 && iIntValue5 == 0 && iIntValue6 == 0) {
            iIntValue3 = 0;
        } else {
            if (iIntValue3 == 23 && iIntValue4 == 59 && iIntValue5 == 60) {
                c1708v.m762c().f543d = true;
                iIntValue5 = 59;
            }
            i2 = 0;
        }
        int i3 = ((int) jLongValue) % XCallback.PRIORITY_HIGHEST;
        try {
            LocalDateTime localDateTime = LocalDateTime.f441c;
            LocalDate localDateM566of = LocalDate.m566of(i3, iIntValue, iIntValue2);
            C1715i c1715iM771U = C1715i.m771U(iIntValue3, iIntValue4, iIntValue5, 0);
            return c1708v.m765f(enumC1727a4, iIntValue6, i, c1708v.m765f(EnumC1727a.INSTANT_SECONDS, AbstractC1636a.m525u(new LocalDateTime(localDateM566of, c1715iM771U).m602Z(localDateM566of.plusDays(i2), c1715iM771U), ZoneOffset.UTC) + AbstractC1636a.m500S(jLongValue / 10000, 315569520000L), i, iMo722j));
        } catch (RuntimeException unused) {
            return ~i;
        }
    }

    public final String toString() {
        return "Instant()";
    }
}
