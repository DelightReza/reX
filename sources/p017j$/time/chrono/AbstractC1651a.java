package p017j$.time.chrono;

import java.util.Locale;
import java.util.Map;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1640b;
import p017j$.time.C1678e;
import p017j$.time.C1715i;
import p017j$.time.DayOfWeek;
import p017j$.time.LocalDateTime;
import p017j$.time.format.EnumC1684E;
import p017j$.time.temporal.C1742p;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;
import p017j$.util.concurrent.ConcurrentHashMap;

/* renamed from: j$.time.chrono.a */
/* loaded from: classes2.dex */
public abstract class AbstractC1651a implements InterfaceC1661k {

    /* renamed from: a */
    public static final ConcurrentHashMap f478a = new ConcurrentHashMap();

    /* renamed from: b */
    public static final ConcurrentHashMap f479b = new ConcurrentHashMap();

    /* renamed from: j */
    public abstract /* synthetic */ InterfaceC1652b mo657j();

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return getId().compareTo(((InterfaceC1661k) obj).getId());
    }

    static {
        new Locale("ja", "JP", "JP");
    }

    /* renamed from: k */
    public static InterfaceC1661k m668k(InterfaceC1661k interfaceC1661k, String str) {
        String strMo658l;
        InterfaceC1661k interfaceC1661k2 = (InterfaceC1661k) f478a.putIfAbsent(str, interfaceC1661k);
        if (interfaceC1661k2 == null && (strMo658l = interfaceC1661k.mo658l()) != null) {
            f479b.putIfAbsent(strMo658l, interfaceC1661k);
        }
        return interfaceC1661k2;
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: K */
    public InterfaceC1652b mo653K(Map map, EnumC1684E enumC1684E) {
        EnumC1727a enumC1727a = EnumC1727a.EPOCH_DAY;
        if (map.containsKey(enumC1727a)) {
            return mo656h(((Long) map.remove(enumC1727a)).longValue());
        }
        mo672t(map, enumC1684E);
        InterfaceC1652b interfaceC1652bMo671D = mo671D(map, enumC1684E);
        if (interfaceC1652bMo671D != null) {
            return interfaceC1652bMo671D;
        }
        EnumC1727a enumC1727a2 = EnumC1727a.YEAR;
        if (!map.containsKey(enumC1727a2)) {
            return null;
        }
        EnumC1727a enumC1727a3 = EnumC1727a.MONTH_OF_YEAR;
        if (map.containsKey(enumC1727a3)) {
            if (map.containsKey(EnumC1727a.DAY_OF_MONTH)) {
                return mo673y(map, enumC1684E);
            }
            EnumC1727a enumC1727a4 = EnumC1727a.ALIGNED_WEEK_OF_MONTH;
            if (map.containsKey(enumC1727a4)) {
                EnumC1727a enumC1727a5 = EnumC1727a.ALIGNED_DAY_OF_WEEK_IN_MONTH;
                if (map.containsKey(enumC1727a5)) {
                    int iM820a = mo660q(enumC1727a2).m820a(((Long) map.remove(enumC1727a2)).longValue(), enumC1727a2);
                    if (enumC1684E == EnumC1684E.LENIENT) {
                        long jM501T = AbstractC1636a.m501T(((Long) map.remove(enumC1727a3)).longValue(), 1L);
                        return mo652I(iM820a, 1, 1).mo557d(jM501T, (InterfaceC1746t) EnumC1728b.MONTHS).mo557d(AbstractC1636a.m501T(((Long) map.remove(enumC1727a4)).longValue(), 1L), (InterfaceC1746t) EnumC1728b.WEEKS).mo557d(AbstractC1636a.m501T(((Long) map.remove(enumC1727a5)).longValue(), 1L), (InterfaceC1746t) EnumC1728b.DAYS);
                    }
                    int iM820a2 = mo660q(enumC1727a3).m820a(((Long) map.remove(enumC1727a3)).longValue(), enumC1727a3);
                    int iM820a3 = mo660q(enumC1727a4).m820a(((Long) map.remove(enumC1727a4)).longValue(), enumC1727a4);
                    InterfaceC1652b interfaceC1652bMo557d = mo652I(iM820a, iM820a2, 1).mo557d((mo660q(enumC1727a5).m820a(((Long) map.remove(enumC1727a5)).longValue(), enumC1727a5) - 1) + ((iM820a3 - 1) * 7), (InterfaceC1746t) EnumC1728b.DAYS);
                    if (enumC1684E != EnumC1684E.STRICT || interfaceC1652bMo557d.mo544i(enumC1727a3) == iM820a2) {
                        return interfaceC1652bMo557d;
                    }
                    throw new C1640b("Strict mode rejected resolved date as it is in a different month");
                }
                EnumC1727a enumC1727a6 = EnumC1727a.DAY_OF_WEEK;
                if (map.containsKey(enumC1727a6)) {
                    int iM820a4 = mo660q(enumC1727a2).m820a(((Long) map.remove(enumC1727a2)).longValue(), enumC1727a2);
                    if (enumC1684E == EnumC1684E.LENIENT) {
                        return m669n(mo652I(iM820a4, 1, 1), AbstractC1636a.m501T(((Long) map.remove(enumC1727a3)).longValue(), 1L), AbstractC1636a.m501T(((Long) map.remove(enumC1727a4)).longValue(), 1L), AbstractC1636a.m501T(((Long) map.remove(enumC1727a6)).longValue(), 1L));
                    }
                    int iM820a5 = mo660q(enumC1727a3).m820a(((Long) map.remove(enumC1727a3)).longValue(), enumC1727a3);
                    InterfaceC1652b interfaceC1652bMo591x = mo652I(iM820a4, iM820a5, 1).mo557d((mo660q(enumC1727a4).m820a(((Long) map.remove(enumC1727a4)).longValue(), enumC1727a4) - 1) * 7, (InterfaceC1746t) EnumC1728b.DAYS).mo591x(new C1742p(DayOfWeek.m541Q(mo660q(enumC1727a6).m820a(((Long) map.remove(enumC1727a6)).longValue(), enumC1727a6)).getValue(), 0));
                    if (enumC1684E != EnumC1684E.STRICT || interfaceC1652bMo591x.mo544i(enumC1727a3) == iM820a5) {
                        return interfaceC1652bMo591x;
                    }
                    throw new C1640b("Strict mode rejected resolved date as it is in a different month");
                }
            }
        }
        EnumC1727a enumC1727a7 = EnumC1727a.DAY_OF_YEAR;
        if (map.containsKey(enumC1727a7)) {
            int iM820a6 = mo660q(enumC1727a2).m820a(((Long) map.remove(enumC1727a2)).longValue(), enumC1727a2);
            if (enumC1684E != EnumC1684E.LENIENT) {
                return mo659m(iM820a6, mo660q(enumC1727a7).m820a(((Long) map.remove(enumC1727a7)).longValue(), enumC1727a7));
            }
            return mo659m(iM820a6, 1).mo557d(AbstractC1636a.m501T(((Long) map.remove(enumC1727a7)).longValue(), 1L), (InterfaceC1746t) EnumC1728b.DAYS);
        }
        EnumC1727a enumC1727a8 = EnumC1727a.ALIGNED_WEEK_OF_YEAR;
        if (!map.containsKey(enumC1727a8)) {
            return null;
        }
        EnumC1727a enumC1727a9 = EnumC1727a.ALIGNED_DAY_OF_WEEK_IN_YEAR;
        if (map.containsKey(enumC1727a9)) {
            int iM820a7 = mo660q(enumC1727a2).m820a(((Long) map.remove(enumC1727a2)).longValue(), enumC1727a2);
            if (enumC1684E == EnumC1684E.LENIENT) {
                return mo659m(iM820a7, 1).mo557d(AbstractC1636a.m501T(((Long) map.remove(enumC1727a8)).longValue(), 1L), (InterfaceC1746t) EnumC1728b.WEEKS).mo557d(AbstractC1636a.m501T(((Long) map.remove(enumC1727a9)).longValue(), 1L), (InterfaceC1746t) EnumC1728b.DAYS);
            }
            int iM820a8 = mo660q(enumC1727a8).m820a(((Long) map.remove(enumC1727a8)).longValue(), enumC1727a8);
            InterfaceC1652b interfaceC1652bMo557d2 = mo659m(iM820a7, 1).mo557d((mo660q(enumC1727a9).m820a(((Long) map.remove(enumC1727a9)).longValue(), enumC1727a9) - 1) + ((iM820a8 - 1) * 7), (InterfaceC1746t) EnumC1728b.DAYS);
            if (enumC1684E != EnumC1684E.STRICT || interfaceC1652bMo557d2.mo544i(enumC1727a2) == iM820a7) {
                return interfaceC1652bMo557d2;
            }
            throw new C1640b("Strict mode rejected resolved date as it is in a different year");
        }
        EnumC1727a enumC1727a10 = EnumC1727a.DAY_OF_WEEK;
        if (!map.containsKey(enumC1727a10)) {
            return null;
        }
        int iM820a9 = mo660q(enumC1727a2).m820a(((Long) map.remove(enumC1727a2)).longValue(), enumC1727a2);
        if (enumC1684E == EnumC1684E.LENIENT) {
            return m669n(mo659m(iM820a9, 1), 0L, AbstractC1636a.m501T(((Long) map.remove(enumC1727a8)).longValue(), 1L), AbstractC1636a.m501T(((Long) map.remove(enumC1727a10)).longValue(), 1L));
        }
        InterfaceC1652b interfaceC1652bMo591x2 = mo659m(iM820a9, 1).mo557d((mo660q(enumC1727a8).m820a(((Long) map.remove(enumC1727a8)).longValue(), enumC1727a8) - 1) * 7, (InterfaceC1746t) EnumC1728b.DAYS).mo591x(new C1742p(DayOfWeek.m541Q(mo660q(enumC1727a10).m820a(((Long) map.remove(enumC1727a10)).longValue(), enumC1727a10)).getValue(), 0));
        if (enumC1684E != EnumC1684E.STRICT || interfaceC1652bMo591x2.mo544i(enumC1727a2) == iM820a9) {
            return interfaceC1652bMo591x2;
        }
        throw new C1640b("Strict mode rejected resolved date as it is in a different year");
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: B */
    public ChronoLocalDateTime mo670B(LocalDateTime localDateTime) {
        try {
            return mo651A(localDateTime).mo568F(C1715i.m770S(localDateTime));
        } catch (C1640b e) {
            throw new C1640b("Unable to obtain ChronoLocalDateTime from TemporalAccessor: " + LocalDateTime.class, e);
        }
    }

    /* renamed from: t */
    public void mo672t(Map map, EnumC1684E enumC1684E) {
        EnumC1727a enumC1727a = EnumC1727a.PROLEPTIC_MONTH;
        Long l = (Long) map.remove(enumC1727a);
        if (l != null) {
            if (enumC1684E != EnumC1684E.LENIENT) {
                enumC1727a.m800D(l.longValue());
            }
            InterfaceC1652b interfaceC1652bMo556c = mo657j().mo556c(1L, (InterfaceC1744r) EnumC1727a.DAY_OF_MONTH).mo556c(l.longValue(), (InterfaceC1744r) enumC1727a);
            m667i(map, EnumC1727a.MONTH_OF_YEAR, interfaceC1652bMo556c.mo544i(r0));
            m667i(map, EnumC1727a.YEAR, interfaceC1652bMo556c.mo544i(r0));
        }
    }

    /* renamed from: D */
    public InterfaceC1652b mo671D(Map map, EnumC1684E enumC1684E) {
        int iM493L;
        EnumC1727a enumC1727a = EnumC1727a.YEAR_OF_ERA;
        Long l = (Long) map.remove(enumC1727a);
        if (l != null) {
            Long l2 = (Long) map.remove(EnumC1727a.ERA);
            if (enumC1684E != EnumC1684E.LENIENT) {
                iM493L = mo660q(enumC1727a).m820a(l.longValue(), enumC1727a);
            } else {
                iM493L = AbstractC1636a.m493L(l.longValue());
            }
            if (l2 != null) {
                m667i(map, EnumC1727a.YEAR, mo663v(mo662u(mo660q(r2).m820a(l2.longValue(), r2)), iM493L));
                return null;
            }
            EnumC1727a enumC1727a2 = EnumC1727a.YEAR;
            if (map.containsKey(enumC1727a2)) {
                m667i(map, enumC1727a2, mo663v(mo659m(mo660q(enumC1727a2).m820a(((Long) map.get(enumC1727a2)).longValue(), enumC1727a2), 1).mo569G(), iM493L));
                return null;
            }
            if (enumC1684E == EnumC1684E.STRICT) {
                map.put(enumC1727a, l);
                return null;
            }
            if (mo661s().isEmpty()) {
                m667i(map, enumC1727a2, iM493L);
                return null;
            }
            m667i(map, enumC1727a2, mo663v((InterfaceC1662l) r9.get(r9.size() - 1), iM493L));
            return null;
        }
        EnumC1727a enumC1727a3 = EnumC1727a.ERA;
        if (!map.containsKey(enumC1727a3)) {
            return null;
        }
        mo660q(enumC1727a3).m821b(((Long) map.get(enumC1727a3)).longValue(), enumC1727a3);
        return null;
    }

    /* renamed from: y */
    public InterfaceC1652b mo673y(Map map, EnumC1684E enumC1684E) {
        EnumC1727a enumC1727a = EnumC1727a.YEAR;
        int iM820a = mo660q(enumC1727a).m820a(((Long) map.remove(enumC1727a)).longValue(), enumC1727a);
        if (enumC1684E == EnumC1684E.LENIENT) {
            long jM501T = AbstractC1636a.m501T(((Long) map.remove(EnumC1727a.MONTH_OF_YEAR)).longValue(), 1L);
            return mo652I(iM820a, 1, 1).mo557d(jM501T, (InterfaceC1746t) EnumC1728b.MONTHS).mo557d(AbstractC1636a.m501T(((Long) map.remove(EnumC1727a.DAY_OF_MONTH)).longValue(), 1L), (InterfaceC1746t) EnumC1728b.DAYS);
        }
        EnumC1727a enumC1727a2 = EnumC1727a.MONTH_OF_YEAR;
        int iM820a2 = mo660q(enumC1727a2).m820a(((Long) map.remove(enumC1727a2)).longValue(), enumC1727a2);
        EnumC1727a enumC1727a3 = EnumC1727a.DAY_OF_MONTH;
        int iM820a3 = mo660q(enumC1727a3).m820a(((Long) map.remove(enumC1727a3)).longValue(), enumC1727a3);
        if (enumC1684E != EnumC1684E.SMART) {
            return mo652I(iM820a, iM820a2, iM820a3);
        }
        try {
            return mo652I(iM820a, iM820a2, iM820a3);
        } catch (C1640b unused) {
            return mo652I(iM820a, iM820a2, 1).mo591x(new C1678e(2));
        }
    }

    /* renamed from: n */
    public static InterfaceC1652b m669n(InterfaceC1652b interfaceC1652b, long j, long j2, long j3) {
        long j4;
        InterfaceC1652b interfaceC1652bMo557d = interfaceC1652b.mo557d(j, (InterfaceC1746t) EnumC1728b.MONTHS);
        EnumC1728b enumC1728b = EnumC1728b.WEEKS;
        InterfaceC1652b interfaceC1652bMo557d2 = interfaceC1652bMo557d.mo557d(j2, (InterfaceC1746t) enumC1728b);
        if (j3 > 7) {
            long j5 = j3 - 1;
            interfaceC1652bMo557d2 = interfaceC1652bMo557d2.mo557d(j5 / 7, (InterfaceC1746t) enumC1728b);
            j4 = j5 % 7;
        } else {
            if (j3 < 1) {
                interfaceC1652bMo557d2 = interfaceC1652bMo557d2.mo557d(AbstractC1636a.m501T(j3, 7L) / 7, (InterfaceC1746t) enumC1728b);
                j4 = (j3 + 6) % 7;
            }
            return interfaceC1652bMo557d2.mo591x(new C1742p(DayOfWeek.m541Q((int) j3).getValue(), 0));
        }
        j3 = j4 + 1;
        return interfaceC1652bMo557d2.mo591x(new C1742p(DayOfWeek.m541Q((int) j3).getValue(), 0));
    }

    /* renamed from: i */
    public static void m667i(Map map, EnumC1727a enumC1727a, long j) {
        Long l = (Long) map.get(enumC1727a);
        if (l != null && l.longValue() != j) {
            throw new C1640b("Conflict found: " + enumC1727a + " " + l + " differs from " + enumC1727a + " " + j);
        }
        map.put(enumC1727a, Long.valueOf(j));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AbstractC1651a) && getId().compareTo(((AbstractC1651a) obj).getId()) == 0;
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    public final int hashCode() {
        return getClass().hashCode() ^ getId().hashCode();
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    public final String toString() {
        return getId();
    }
}
