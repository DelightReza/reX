package p017j$.time.temporal;

import java.util.Map;
import org.mvel2.asm.Opcodes;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1640b;
import p017j$.time.DayOfWeek;
import p017j$.time.LocalDate;
import p017j$.time.chrono.C1668r;
import p017j$.time.format.C1683D;
import p017j$.time.format.EnumC1684E;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.time.temporal.h */
/* loaded from: classes2.dex */
public abstract class EnumC1734h implements InterfaceC1744r {
    public static final EnumC1734h DAY_OF_QUARTER;
    public static final EnumC1734h QUARTER_OF_YEAR;
    public static final EnumC1734h WEEK_BASED_YEAR;
    public static final EnumC1734h WEEK_OF_WEEK_BASED_YEAR;

    /* renamed from: a */
    public static final int[] f675a;

    /* renamed from: b */
    public static final /* synthetic */ EnumC1734h[] f676b;

    @Override // p017j$.time.temporal.InterfaceC1744r
    public final boolean isDateBased() {
        return true;
    }

    /* renamed from: k */
    public /* synthetic */ InterfaceC1740n mo804k(Map map, C1683D c1683d, EnumC1684E enumC1684E) {
        return null;
    }

    public static EnumC1734h valueOf(String str) {
        return (EnumC1734h) Enum.valueOf(EnumC1734h.class, str);
    }

    public static EnumC1734h[] values() {
        return (EnumC1734h[]) f676b.clone();
    }

    static {
        EnumC1734h enumC1734h = new EnumC1734h() { // from class: j$.time.temporal.d
            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: n */
            public final C1748v mo805n() {
                return C1748v.m819g(1L, 90L, 92L);
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: i */
            public final boolean mo802i(InterfaceC1740n interfaceC1740n) {
                if (!interfaceC1740n.mo543e(EnumC1727a.DAY_OF_YEAR) || !interfaceC1740n.mo543e(EnumC1727a.MONTH_OF_YEAR) || !interfaceC1740n.mo543e(EnumC1727a.YEAR)) {
                    return false;
                }
                EnumC1734h enumC1734h2 = AbstractC1736j.f679a;
                return AbstractC1636a.m492K(interfaceC1740n).equals(C1668r.f512c);
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: j */
            public final C1748v mo803j(InterfaceC1740n interfaceC1740n) {
                if (!mo802i(interfaceC1740n)) {
                    throw new C1747u("Unsupported field: DayOfQuarter");
                }
                long jMo542D = interfaceC1740n.mo542D(EnumC1734h.QUARTER_OF_YEAR);
                if (jMo542D == 1) {
                    return C1668r.f512c.mo655O(interfaceC1740n.mo542D(EnumC1727a.YEAR)) ? C1748v.m818f(1L, 91L) : C1748v.m818f(1L, 90L);
                }
                if (jMo542D == 2) {
                    return C1748v.m818f(1L, 91L);
                }
                if (jMo542D == 3 || jMo542D == 4) {
                    return C1748v.m818f(1L, 92L);
                }
                return mo805n();
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: t */
            public final long mo806t(InterfaceC1740n interfaceC1740n) {
                if (!mo802i(interfaceC1740n)) {
                    throw new C1747u("Unsupported field: DayOfQuarter");
                }
                return interfaceC1740n.mo544i(EnumC1727a.DAY_OF_YEAR) - EnumC1734h.f675a[((interfaceC1740n.mo544i(EnumC1727a.MONTH_OF_YEAR) - 1) / 3) + (C1668r.f512c.mo655O(interfaceC1740n.mo542D(EnumC1727a.YEAR)) ? 4 : 0)];
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: y */
            public final InterfaceC1739m mo807y(InterfaceC1739m interfaceC1739m, long j) {
                long jMo806t = mo806t(interfaceC1739m);
                mo805n().m821b(j, this);
                EnumC1727a enumC1727a = EnumC1727a.DAY_OF_YEAR;
                return interfaceC1739m.mo556c((j - jMo806t) + interfaceC1739m.mo542D(enumC1727a), enumC1727a);
            }

            @Override // p017j$.time.temporal.EnumC1734h, p017j$.time.temporal.InterfaceC1744r
            /* renamed from: k */
            public final InterfaceC1740n mo804k(Map map, C1683D c1683d, EnumC1684E enumC1684E) {
                LocalDate localDateM583e0;
                long jM501T;
                EnumC1727a enumC1727a = EnumC1727a.YEAR;
                Long l = (Long) map.get(enumC1727a);
                InterfaceC1744r interfaceC1744r = EnumC1734h.QUARTER_OF_YEAR;
                Long l2 = (Long) map.get(interfaceC1744r);
                if (l == null || l2 == null) {
                    return null;
                }
                int iM820a = enumC1727a.f671b.m820a(l.longValue(), enumC1727a);
                long jLongValue = ((Long) map.get(EnumC1734h.DAY_OF_QUARTER)).longValue();
                EnumC1734h enumC1734h2 = AbstractC1736j.f679a;
                if (!AbstractC1636a.m492K(c1683d).equals(C1668r.f512c)) {
                    throw new C1640b("Resolve requires IsoChronology");
                }
                if (enumC1684E == EnumC1684E.LENIENT) {
                    localDateM583e0 = LocalDate.m566of(iM820a, 1, 1).m583e0(AbstractC1636a.m500S(AbstractC1636a.m501T(l2.longValue(), 1L), 3));
                    jM501T = AbstractC1636a.m501T(jLongValue, 1L);
                } else {
                    LocalDate localDateM566of = LocalDate.m566of(iM820a, ((interfaceC1744r.mo805n().m820a(l2.longValue(), interfaceC1744r) - 1) * 3) + 1, 1);
                    if (jLongValue < 1 || jLongValue > 90) {
                        if (enumC1684E == EnumC1684E.STRICT) {
                            mo803j(localDateM566of).m821b(jLongValue, this);
                        } else {
                            mo805n().m821b(jLongValue, this);
                        }
                    }
                    localDateM583e0 = localDateM566of;
                    jM501T = jLongValue - 1;
                }
                map.remove(this);
                map.remove(enumC1727a);
                map.remove(interfaceC1744r);
                return localDateM583e0.plusDays(jM501T);
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "DayOfQuarter";
            }
        };
        DAY_OF_QUARTER = enumC1734h;
        EnumC1734h enumC1734h2 = new EnumC1734h() { // from class: j$.time.temporal.e
            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: n */
            public final C1748v mo805n() {
                return C1748v.m818f(1L, 4L);
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: i */
            public final boolean mo802i(InterfaceC1740n interfaceC1740n) {
                if (!interfaceC1740n.mo543e(EnumC1727a.MONTH_OF_YEAR)) {
                    return false;
                }
                EnumC1734h enumC1734h3 = AbstractC1736j.f679a;
                return AbstractC1636a.m492K(interfaceC1740n).equals(C1668r.f512c);
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: t */
            public final long mo806t(InterfaceC1740n interfaceC1740n) {
                if (!mo802i(interfaceC1740n)) {
                    throw new C1747u("Unsupported field: QuarterOfYear");
                }
                return (interfaceC1740n.mo542D(EnumC1727a.MONTH_OF_YEAR) + 2) / 3;
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: j */
            public final C1748v mo803j(InterfaceC1740n interfaceC1740n) {
                if (!mo802i(interfaceC1740n)) {
                    throw new C1747u("Unsupported field: QuarterOfYear");
                }
                return mo805n();
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: y */
            public final InterfaceC1739m mo807y(InterfaceC1739m interfaceC1739m, long j) {
                long jMo806t = mo806t(interfaceC1739m);
                mo805n().m821b(j, this);
                EnumC1727a enumC1727a = EnumC1727a.MONTH_OF_YEAR;
                return interfaceC1739m.mo556c(((j - jMo806t) * 3) + interfaceC1739m.mo542D(enumC1727a), enumC1727a);
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "QuarterOfYear";
            }
        };
        QUARTER_OF_YEAR = enumC1734h2;
        EnumC1734h enumC1734h3 = new EnumC1734h() { // from class: j$.time.temporal.f
            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: n */
            public final C1748v mo805n() {
                return C1748v.m819g(1L, 52L, 53L);
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: i */
            public final boolean mo802i(InterfaceC1740n interfaceC1740n) {
                if (!interfaceC1740n.mo543e(EnumC1727a.EPOCH_DAY)) {
                    return false;
                }
                EnumC1734h enumC1734h4 = AbstractC1736j.f679a;
                return AbstractC1636a.m492K(interfaceC1740n).equals(C1668r.f512c);
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: j */
            public final C1748v mo803j(InterfaceC1740n interfaceC1740n) {
                if (mo802i(interfaceC1740n)) {
                    return EnumC1734h.m812S(LocalDate.m561S(interfaceC1740n));
                }
                throw new C1747u("Unsupported field: WeekOfWeekBasedYear");
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: t */
            public final long mo806t(InterfaceC1740n interfaceC1740n) {
                if (!mo802i(interfaceC1740n)) {
                    throw new C1747u("Unsupported field: WeekOfWeekBasedYear");
                }
                return EnumC1734h.m809D(LocalDate.m561S(interfaceC1740n));
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: y */
            public final InterfaceC1739m mo807y(InterfaceC1739m interfaceC1739m, long j) {
                mo805n().m821b(j, this);
                return interfaceC1739m.mo557d(AbstractC1636a.m501T(j, mo806t(interfaceC1739m)), EnumC1728b.WEEKS);
            }

            @Override // p017j$.time.temporal.EnumC1734h, p017j$.time.temporal.InterfaceC1744r
            /* renamed from: k */
            public final InterfaceC1740n mo804k(Map map, C1683D c1683d, EnumC1684E enumC1684E) {
                LocalDate localDateMo556c;
                long j;
                long j2;
                InterfaceC1744r interfaceC1744r = EnumC1734h.WEEK_BASED_YEAR;
                Long l = (Long) map.get(interfaceC1744r);
                EnumC1727a enumC1727a = EnumC1727a.DAY_OF_WEEK;
                Long l2 = (Long) map.get(enumC1727a);
                if (l == null || l2 == null) {
                    return null;
                }
                int iM820a = interfaceC1744r.mo805n().m820a(l.longValue(), interfaceC1744r);
                long jLongValue = ((Long) map.get(EnumC1734h.WEEK_OF_WEEK_BASED_YEAR)).longValue();
                EnumC1734h enumC1734h4 = AbstractC1736j.f679a;
                if (!AbstractC1636a.m492K(c1683d).equals(C1668r.f512c)) {
                    throw new C1640b("Resolve requires IsoChronology");
                }
                LocalDate localDateM566of = LocalDate.m566of(iM820a, 1, 4);
                if (enumC1684E == EnumC1684E.LENIENT) {
                    long jLongValue2 = l2.longValue();
                    if (jLongValue2 > 7) {
                        long j3 = jLongValue2 - 1;
                        j = 1;
                        localDateM566of = localDateM566of.m584f0(j3 / 7);
                        j2 = j3 % 7;
                    } else {
                        j = 1;
                        if (jLongValue2 < 1) {
                            localDateM566of = localDateM566of.m584f0(AbstractC1636a.m501T(jLongValue2, 7L) / 7);
                            j2 = (jLongValue2 + 6) % 7;
                        }
                        localDateMo556c = localDateM566of.m584f0(AbstractC1636a.m501T(jLongValue, j)).mo556c(jLongValue2, enumC1727a);
                    }
                    jLongValue2 = j2 + j;
                    localDateMo556c = localDateM566of.m584f0(AbstractC1636a.m501T(jLongValue, j)).mo556c(jLongValue2, enumC1727a);
                } else {
                    int iM820a2 = enumC1727a.f671b.m820a(l2.longValue(), enumC1727a);
                    if (jLongValue < 1 || jLongValue > 52) {
                        if (enumC1684E == EnumC1684E.STRICT) {
                            EnumC1734h.m812S(localDateM566of).m821b(jLongValue, this);
                        } else {
                            mo805n().m821b(jLongValue, this);
                        }
                    }
                    localDateMo556c = localDateM566of.m584f0(jLongValue - 1).mo556c(iM820a2, enumC1727a);
                }
                map.remove(this);
                map.remove(interfaceC1744r);
                map.remove(enumC1727a);
                return localDateMo556c;
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "WeekOfWeekBasedYear";
            }
        };
        WEEK_OF_WEEK_BASED_YEAR = enumC1734h3;
        EnumC1734h enumC1734h4 = new EnumC1734h() { // from class: j$.time.temporal.g
            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: n */
            public final C1748v mo805n() {
                return EnumC1727a.YEAR.f671b;
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: i */
            public final boolean mo802i(InterfaceC1740n interfaceC1740n) {
                if (!interfaceC1740n.mo543e(EnumC1727a.EPOCH_DAY)) {
                    return false;
                }
                EnumC1734h enumC1734h5 = AbstractC1736j.f679a;
                return AbstractC1636a.m492K(interfaceC1740n).equals(C1668r.f512c);
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: t */
            public final long mo806t(InterfaceC1740n interfaceC1740n) {
                if (mo802i(interfaceC1740n)) {
                    return EnumC1734h.m810Q(LocalDate.m561S(interfaceC1740n));
                }
                throw new C1747u("Unsupported field: WeekBasedYear");
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: j */
            public final C1748v mo803j(InterfaceC1740n interfaceC1740n) {
                if (!mo802i(interfaceC1740n)) {
                    throw new C1747u("Unsupported field: WeekBasedYear");
                }
                return mo805n();
            }

            @Override // p017j$.time.temporal.InterfaceC1744r
            /* renamed from: y */
            public final InterfaceC1739m mo807y(InterfaceC1739m interfaceC1739m, long j) {
                if (!mo802i(interfaceC1739m)) {
                    throw new C1747u("Unsupported field: WeekBasedYear");
                }
                int iM820a = EnumC1727a.YEAR.f671b.m820a(j, EnumC1734h.WEEK_BASED_YEAR);
                LocalDate localDateM561S = LocalDate.m561S(interfaceC1739m);
                int iMo544i = localDateM561S.mo544i(EnumC1727a.DAY_OF_WEEK);
                int iM809D = EnumC1734h.m809D(localDateM561S);
                if (iM809D == 53 && EnumC1734h.m811R(iM820a) == 52) {
                    iM809D = 52;
                }
                return interfaceC1739m.mo591x(LocalDate.m566of(iM820a, 1, 4).plusDays(((iM809D - 1) * 7) + (iMo544i - r6.mo544i(r0))));
            }

            @Override // java.lang.Enum
            public final String toString() {
                return "WeekBasedYear";
            }
        };
        WEEK_BASED_YEAR = enumC1734h4;
        f676b = new EnumC1734h[]{enumC1734h, enumC1734h2, enumC1734h3, enumC1734h4};
        f675a = new int[]{0, 90, Opcodes.PUTFIELD, 273, 0, 91, Opcodes.INVOKEVIRTUAL, 274};
    }

    /* renamed from: S */
    public static C1748v m812S(LocalDate localDate) {
        return C1748v.m818f(1L, m811R(m810Q(localDate)));
    }

    /* renamed from: R */
    public static int m811R(int i) {
        LocalDate localDateM566of = LocalDate.m566of(i, 1, 1);
        if (localDateM566of.m575U() != DayOfWeek.THURSDAY) {
            return (localDateM566of.m575U() == DayOfWeek.WEDNESDAY && localDateM566of.mo589p()) ? 53 : 52;
        }
        return 53;
    }

    /* renamed from: D */
    public static int m809D(LocalDate localDate) {
        int iOrdinal = localDate.m575U().ordinal();
        int iM576V = localDate.m576V() - 1;
        int i = (3 - iOrdinal) + iM576V;
        int i2 = i - ((i / 7) * 7);
        int i3 = i2 - 3;
        if (i3 < -3) {
            i3 = i2 + 4;
        }
        if (iM576V >= i3) {
            int i4 = ((iM576V - i3) / 7) + 1;
            if (i4 != 53 || i3 == -3 || (i3 == -2 && localDate.mo589p())) {
                return i4;
            }
            return 1;
        }
        if (localDate.m576V() != 180) {
            localDate = LocalDate.m564c0(localDate.f438a, Opcodes.GETFIELD);
        }
        return (int) m812S(localDate.m585g0(-1L)).f700d;
    }

    /* renamed from: Q */
    public static int m810Q(LocalDate localDate) {
        int year = localDate.getYear();
        int iM576V = localDate.m576V();
        if (iM576V <= 3) {
            return iM576V - localDate.m575U().ordinal() < -2 ? year - 1 : year;
        }
        if (iM576V >= 363) {
            return ((iM576V - 363) - (localDate.mo589p() ? 1 : 0)) - localDate.m575U().ordinal() >= 0 ? year + 1 : year;
        }
        return year;
    }
}
