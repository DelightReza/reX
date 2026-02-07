package p017j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.chrono.ChronoLocalDateTime;
import p017j$.time.chrono.ChronoZonedDateTime;
import p017j$.time.chrono.InterfaceC1652b;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.format.DateTimeFormatter;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;
import p017j$.util.Objects;

/* loaded from: classes2.dex */
public final class LocalDateTime implements InterfaceC1739m, InterfaceC1741o, ChronoLocalDateTime<LocalDate>, Serializable {

    /* renamed from: c */
    public static final LocalDateTime f441c = m593T(LocalDate.f436d, C1715i.f640e);

    /* renamed from: d */
    public static final LocalDateTime f442d = m593T(LocalDate.f437e, C1715i.f641f);
    private static final long serialVersionUID = 6207766400415563566L;

    /* renamed from: a */
    public final LocalDate f443a;

    /* renamed from: b */
    public final C1715i f444b;

    @Override // p017j$.time.chrono.ChronoLocalDateTime
    /* renamed from: a */
    public final InterfaceC1661k mo603a() {
        return ((LocalDate) mo606f()).mo581a();
    }

    @Override // p017j$.time.chrono.ChronoLocalDateTime
    /* renamed from: z */
    public final ChronoZonedDateTime mo607z(ZoneId zoneId) {
        return ZonedDateTime.m630Q(this, zoneId, null);
    }

    /* renamed from: T */
    public static LocalDateTime m593T(LocalDate localDate, C1715i c1715i) {
        Objects.requireNonNull(localDate, "date");
        Objects.requireNonNull(c1715i, "time");
        return new LocalDateTime(localDate, c1715i);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return interfaceC1739m.mo556c(((LocalDate) mo606f()).mo567E(), EnumC1727a.EPOCH_DAY).mo556c(mo605b().m781c0(), EnumC1727a.NANO_OF_DAY);
    }

    /* renamed from: U */
    public static LocalDateTime m594U(long j, int i, ZoneOffset zoneOffset) {
        Objects.requireNonNull(zoneOffset, "offset");
        long j2 = i;
        EnumC1727a.NANO_OF_SECOND.m800D(j2);
        return new LocalDateTime(LocalDate.m563b0(AbstractC1636a.m499R(j + zoneOffset.getTotalSeconds(), 86400)), C1715i.m772V((((int) AbstractC1636a.m498Q(r5, r7)) * 1000000000) + j2));
    }

    /* renamed from: R */
    public static LocalDateTime m592R(InterfaceC1740n interfaceC1740n) {
        if (interfaceC1740n instanceof LocalDateTime) {
            return (LocalDateTime) interfaceC1740n;
        }
        if (!(interfaceC1740n instanceof ZonedDateTime)) {
            if (interfaceC1740n instanceof OffsetDateTime) {
                return ((OffsetDateTime) interfaceC1740n).toLocalDateTime();
            }
            try {
                return new LocalDateTime(LocalDate.m561S(interfaceC1740n), C1715i.m770S(interfaceC1740n));
            } catch (C1640b e) {
                throw new C1640b("Unable to obtain LocalDateTime from TemporalAccessor: " + interfaceC1740n + " of type " + interfaceC1740n.getClass().getName(), e);
            }
        }
        return ((ZonedDateTime) interfaceC1740n).f462a;
    }

    @Override // p017j$.time.chrono.ChronoLocalDateTime
    public final Instant toInstant(ZoneOffset zoneOffset) {
        return Instant.m553S(AbstractC1636a.m525u(this, zoneOffset), mo605b().f647d);
    }

    public LocalDateTime(LocalDate localDate, C1715i c1715i) {
        this.f443a = localDate;
        this.f444b = c1715i;
    }

    /* renamed from: Z */
    public final LocalDateTime m602Z(LocalDate localDate, C1715i c1715i) {
        return (this.f443a == localDate && this.f444b == c1715i) ? this : new LocalDateTime(localDate, c1715i);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return interfaceC1744r != null && interfaceC1744r.mo802i(this);
        }
        EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
        return enumC1727a.isDateBased() || enumC1727a.m801Q();
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            if (((EnumC1727a) interfaceC1744r).m801Q()) {
                C1715i c1715i = this.f444b;
                c1715i.getClass();
                return AbstractC1745s.m816d(c1715i, interfaceC1744r);
            }
            return this.f443a.mo545k(interfaceC1744r);
        }
        return interfaceC1744r.mo803j(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            return ((EnumC1727a) interfaceC1744r).m801Q() ? this.f444b.mo544i(interfaceC1744r) : this.f443a.mo544i(interfaceC1744r);
        }
        return AbstractC1745s.m813a(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            return ((EnumC1727a) interfaceC1744r).m801Q() ? this.f444b.mo542D(interfaceC1744r) : this.f443a.mo542D(interfaceC1744r);
        }
        return interfaceC1744r.mo806t(this);
    }

    @Override // p017j$.time.chrono.ChronoLocalDateTime
    /* renamed from: f */
    public final InterfaceC1652b mo606f() {
        return this.f443a;
    }

    @Override // p017j$.time.chrono.ChronoLocalDateTime
    /* renamed from: b */
    public final C1715i mo605b() {
        return this.f444b;
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: a0, reason: merged with bridge method [inline-methods] */
    public final LocalDateTime mo591x(InterfaceC1741o interfaceC1741o) {
        if (interfaceC1741o instanceof LocalDate) {
            return m602Z((LocalDate) interfaceC1741o, this.f444b);
        }
        if (interfaceC1741o instanceof C1715i) {
            return m602Z(this.f443a, (C1715i) interfaceC1741o);
        }
        if (interfaceC1741o instanceof LocalDateTime) {
            return (LocalDateTime) interfaceC1741o;
        }
        return (LocalDateTime) interfaceC1741o.mo546n(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: Y, reason: merged with bridge method [inline-methods] */
    public final LocalDateTime mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            if (((EnumC1727a) interfaceC1744r).m801Q()) {
                return m602Z(this.f443a, this.f444b.mo556c(j, interfaceC1744r));
            }
            return m602Z(this.f443a.mo556c(j, interfaceC1744r), this.f444b);
        }
        return (LocalDateTime) interfaceC1744r.mo807y(this, j);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: V, reason: merged with bridge method [inline-methods] */
    public final LocalDateTime mo557d(long j, InterfaceC1746t interfaceC1746t) {
        if (!(interfaceC1746t instanceof EnumC1728b)) {
            return (LocalDateTime) interfaceC1746t.mo808i(this, j);
        }
        switch (AbstractC1713g.f637a[((EnumC1728b) interfaceC1746t).ordinal()]) {
            case 1:
                return m600X(this.f443a, 0L, 0L, 0L, j);
            case 2:
                LocalDateTime localDateTimeM602Z = m602Z(this.f443a.plusDays(j / 86400000000L), this.f444b);
                return localDateTimeM602Z.m600X(localDateTimeM602Z.f443a, 0L, 0L, 0L, (j % 86400000000L) * 1000);
            case 3:
                LocalDateTime localDateTimeM602Z2 = m602Z(this.f443a.plusDays(j / 86400000), this.f444b);
                return localDateTimeM602Z2.m600X(localDateTimeM602Z2.f443a, 0L, 0L, 0L, (j % 86400000) * 1000000);
            case 4:
                return m599W(j);
            case 5:
                return m600X(this.f443a, 0L, j, 0L, 0L);
            case 6:
                return m600X(this.f443a, j, 0L, 0L, 0L);
            case 7:
                LocalDateTime localDateTimeM602Z3 = m602Z(this.f443a.plusDays(j / 256), this.f444b);
                return localDateTimeM602Z3.m600X(localDateTimeM602Z3.f443a, (j % 256) * 12, 0L, 0L, 0L);
            default:
                return m602Z(this.f443a.mo557d(j, interfaceC1746t), this.f444b);
        }
    }

    /* renamed from: W */
    public final LocalDateTime m599W(long j) {
        return m600X(this.f443a, 0L, 0L, j, 0L);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return j == Long.MIN_VALUE ? mo557d(Long.MAX_VALUE, enumC1728b).mo557d(1L, enumC1728b) : mo557d(-j, enumC1728b);
    }

    /* renamed from: X */
    public final LocalDateTime m600X(LocalDate localDate, long j, long j2, long j3, long j4) {
        if ((j | j2 | j3 | j4) == 0) {
            return m602Z(localDate, this.f444b);
        }
        long j5 = 1;
        long jM781c0 = this.f444b.m781c0();
        long j6 = ((((j % 24) * 3600000000000L) + ((j2 % 1440) * 60000000000L) + ((j3 % 86400) * 1000000000) + (j4 % 86400000000000L)) * j5) + jM781c0;
        long jM499R = AbstractC1636a.m499R(j6, 86400000000000L) + (((j / 24) + (j2 / 1440) + (j3 / 86400) + (j4 / 86400000000000L)) * j5);
        long jM498Q = AbstractC1636a.m498Q(j6, 86400000000000L);
        return m602Z(localDate.plusDays(jM499R), jM498Q == jM781c0 ? this.f444b : C1715i.m772V(jM498Q));
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f695f) {
            return this.f443a;
        }
        return AbstractC1636a.m522r(this, c1678e);
    }

    public String format(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        return dateTimeFormatter.m719a(this);
    }

    @Override // java.lang.Comparable
    /* renamed from: H, reason: merged with bridge method [inline-methods] */
    public final int compareTo(ChronoLocalDateTime chronoLocalDateTime) {
        if (chronoLocalDateTime instanceof LocalDateTime) {
            return m596Q((LocalDateTime) chronoLocalDateTime);
        }
        return AbstractC1636a.m509e(this, chronoLocalDateTime);
    }

    /* renamed from: Q */
    public final int m596Q(LocalDateTime localDateTime) {
        int iM573Q = this.f443a.m573Q(localDateTime.f443a);
        return iM573Q == 0 ? this.f444b.compareTo(localDateTime.f444b) : iM573Q;
    }

    /* renamed from: S */
    public final boolean m597S(ChronoLocalDateTime chronoLocalDateTime) {
        if (chronoLocalDateTime instanceof LocalDateTime) {
            return m596Q((LocalDateTime) chronoLocalDateTime) < 0;
        }
        long jMo567E = this.f443a.mo567E();
        long jMo567E2 = chronoLocalDateTime.mo606f().mo567E();
        if (jMo567E >= jMo567E2) {
            return jMo567E == jMo567E2 && this.f444b.m781c0() < chronoLocalDateTime.mo605b().m781c0();
        }
        return true;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) obj;
            if (this.f443a.equals(localDateTime.f443a) && this.f444b.equals(localDateTime.f444b)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.f443a.hashCode() ^ this.f444b.hashCode();
    }

    public final String toString() {
        return this.f443a.toString() + "T" + this.f444b.toString();
    }

    private Object writeReplace() {
        return new C1722p((byte) 5, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
