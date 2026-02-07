package p017j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1639a;
import p017j$.time.C1640b;
import p017j$.time.C1724r;
import p017j$.time.EnumC1717k;
import p017j$.time.Instant;
import p017j$.time.LocalDate;
import p017j$.time.LocalDateTime;
import p017j$.time.ZoneId;
import p017j$.time.ZonedDateTime;
import p017j$.time.format.EnumC1684E;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.util.Objects;

/* renamed from: j$.time.chrono.r */
/* loaded from: classes2.dex */
public final class C1668r extends AbstractC1651a implements Serializable {

    /* renamed from: c */
    public static final C1668r f512c = new C1668r();
    private static final long serialVersionUID = -1440403870442975015L;

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: u */
    public final InterfaceC1662l mo662u(int i) {
        if (i == 0) {
            return EnumC1669s.BCE;
        }
        if (i == 1) {
            return EnumC1669s.f513CE;
        }
        throw new C1640b("Invalid era: " + i);
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    public final String getId() {
        return "ISO";
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: l */
    public final String mo658l() {
        return "iso8601";
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: I */
    public final InterfaceC1652b mo652I(int i, int i2, int i3) {
        return LocalDate.m566of(i, i2, i3);
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: m */
    public final InterfaceC1652b mo659m(int i, int i2) {
        return LocalDate.m564c0(i, i2);
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: h */
    public final InterfaceC1652b mo656h(long j) {
        return LocalDate.m563b0(j);
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: A */
    public final InterfaceC1652b mo651A(InterfaceC1740n interfaceC1740n) {
        return LocalDate.m561S(interfaceC1740n);
    }

    private C1668r() {
    }

    @Override // p017j$.time.chrono.AbstractC1651a, p017j$.time.chrono.InterfaceC1661k
    /* renamed from: B */
    public final ChronoLocalDateTime mo670B(LocalDateTime localDateTime) {
        return LocalDateTime.m592R(localDateTime);
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: L */
    public final ChronoZonedDateTime mo654L(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        return ZonedDateTime.m631n(instant.f434a, instant.f435b, zoneId);
    }

    @Override // p017j$.time.chrono.AbstractC1651a
    /* renamed from: j */
    public final InterfaceC1652b mo657j() {
        C1639a c1639aM504W = AbstractC1636a.m504W();
        Objects.requireNonNull(c1639aM504W, "clock");
        return LocalDate.m561S(LocalDate.m562a0(c1639aM504W));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: O */
    public final boolean mo655O(long j) {
        if ((3 & j) == 0) {
            return j % 100 != 0 || j % 400 == 0;
        }
        return false;
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: v */
    public final int mo663v(InterfaceC1662l interfaceC1662l, int i) {
        if (interfaceC1662l instanceof EnumC1669s) {
            return interfaceC1662l == EnumC1669s.f513CE ? i : 1 - i;
        }
        throw new ClassCastException("Era must be IsoEra");
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: s */
    public final List mo661s() {
        return AbstractC1636a.m495N(EnumC1669s.values());
    }

    @Override // p017j$.time.chrono.AbstractC1651a, p017j$.time.chrono.InterfaceC1661k
    /* renamed from: K */
    public final InterfaceC1652b mo653K(Map map, EnumC1684E enumC1684E) {
        return (LocalDate) super.mo653K(map, enumC1684E);
    }

    @Override // p017j$.time.chrono.AbstractC1651a
    /* renamed from: t */
    public final void mo672t(Map map, EnumC1684E enumC1684E) {
        EnumC1727a enumC1727a = EnumC1727a.PROLEPTIC_MONTH;
        Long l = (Long) map.remove(enumC1727a);
        if (l != null) {
            if (enumC1684E != EnumC1684E.LENIENT) {
                enumC1727a.m800D(l.longValue());
            }
            AbstractC1651a.m667i(map, EnumC1727a.MONTH_OF_YEAR, ((int) AbstractC1636a.m498Q(l.longValue(), r4)) + 1);
            AbstractC1651a.m667i(map, EnumC1727a.YEAR, AbstractC1636a.m499R(l.longValue(), 12));
        }
    }

    @Override // p017j$.time.chrono.AbstractC1651a
    /* renamed from: D */
    public final InterfaceC1652b mo671D(Map map, EnumC1684E enumC1684E) {
        EnumC1727a enumC1727a = EnumC1727a.YEAR_OF_ERA;
        Long l = (Long) map.remove(enumC1727a);
        if (l != null) {
            if (enumC1684E != EnumC1684E.LENIENT) {
                enumC1727a.m800D(l.longValue());
            }
            Long l2 = (Long) map.remove(EnumC1727a.ERA);
            if (l2 != null) {
                if (l2.longValue() == 1) {
                    AbstractC1651a.m667i(map, EnumC1727a.YEAR, l.longValue());
                    return null;
                }
                if (l2.longValue() == 0) {
                    AbstractC1651a.m667i(map, EnumC1727a.YEAR, AbstractC1636a.m501T(1L, l.longValue()));
                    return null;
                }
                throw new C1640b("Invalid value for era: " + l2);
            }
            EnumC1727a enumC1727a2 = EnumC1727a.YEAR;
            Long l3 = (Long) map.get(enumC1727a2);
            if (enumC1684E != EnumC1684E.STRICT) {
                AbstractC1651a.m667i(map, enumC1727a2, (l3 == null || l3.longValue() > 0) ? l.longValue() : AbstractC1636a.m501T(1L, l.longValue()));
                return null;
            }
            if (l3 != null) {
                long jLongValue = l3.longValue();
                long jLongValue2 = l.longValue();
                if (jLongValue <= 0) {
                    jLongValue2 = AbstractC1636a.m501T(1L, jLongValue2);
                }
                AbstractC1651a.m667i(map, enumC1727a2, jLongValue2);
                return null;
            }
            map.put(enumC1727a, l);
            return null;
        }
        EnumC1727a enumC1727a3 = EnumC1727a.ERA;
        if (!map.containsKey(enumC1727a3)) {
            return null;
        }
        enumC1727a3.m800D(((Long) map.get(enumC1727a3)).longValue());
        return null;
    }

    @Override // p017j$.time.chrono.AbstractC1651a
    /* renamed from: y */
    public final InterfaceC1652b mo673y(Map map, EnumC1684E enumC1684E) {
        EnumC1727a enumC1727a = EnumC1727a.YEAR;
        int iM820a = enumC1727a.f671b.m820a(((Long) map.remove(enumC1727a)).longValue(), enumC1727a);
        boolean z = true;
        if (enumC1684E == EnumC1684E.LENIENT) {
            return LocalDate.m566of(iM820a, 1, 1).m583e0(AbstractC1636a.m501T(((Long) map.remove(EnumC1727a.MONTH_OF_YEAR)).longValue(), 1L)).plusDays(AbstractC1636a.m501T(((Long) map.remove(EnumC1727a.DAY_OF_MONTH)).longValue(), 1L));
        }
        EnumC1727a enumC1727a2 = EnumC1727a.MONTH_OF_YEAR;
        int iM820a2 = enumC1727a2.f671b.m820a(((Long) map.remove(enumC1727a2)).longValue(), enumC1727a2);
        EnumC1727a enumC1727a3 = EnumC1727a.DAY_OF_MONTH;
        int iM820a3 = enumC1727a3.f671b.m820a(((Long) map.remove(enumC1727a3)).longValue(), enumC1727a3);
        if (enumC1684E == EnumC1684E.SMART) {
            if (iM820a2 == 4 || iM820a2 == 6 || iM820a2 == 9 || iM820a2 == 11) {
                iM820a3 = Math.min(iM820a3, 30);
            } else if (iM820a2 == 2) {
                EnumC1717k enumC1717k = EnumC1717k.FEBRUARY;
                long j = iM820a;
                int i = C1724r.f663b;
                if ((3 & j) != 0 || (j % 100 == 0 && j % 400 != 0)) {
                    z = false;
                }
                iM820a3 = Math.min(iM820a3, enumC1717k.m788R(z));
            }
        }
        return LocalDate.m566of(iM820a, iM820a2, iM820a3);
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: q */
    public final C1748v mo660q(EnumC1727a enumC1727a) {
        return enumC1727a.f671b;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public Object writeReplace() {
        return new C1645D((byte) 1, this);
    }
}
