package p017j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.AbstractC1641c;
import p017j$.time.C1678e;
import p017j$.time.C1715i;
import p017j$.time.Instant;
import p017j$.time.LocalDate;
import p017j$.time.ZoneId;
import p017j$.time.ZoneOffset;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;
import p017j$.util.Objects;

/* renamed from: j$.time.chrono.f */
/* loaded from: classes2.dex */
public final class C1656f implements ChronoLocalDateTime, InterfaceC1739m, InterfaceC1741o, Serializable {
    private static final long serialVersionUID = 4556003607393004514L;

    /* renamed from: a */
    public final transient InterfaceC1652b f482a;

    /* renamed from: b */
    public final transient C1715i f483b;

    @Override // java.lang.Comparable
    /* renamed from: H */
    public final /* synthetic */ int compareTo(ChronoLocalDateTime chronoLocalDateTime) {
        return AbstractC1636a.m509e(this, chronoLocalDateTime);
    }

    /* renamed from: T */
    public final /* synthetic */ long m678T(ZoneOffset zoneOffset) {
        return AbstractC1636a.m525u(this, zoneOffset);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final /* synthetic */ Object mo547t(C1678e c1678e) {
        return AbstractC1636a.m522r(this, c1678e);
    }

    /* renamed from: Q */
    public static C1656f m675Q(InterfaceC1661k interfaceC1661k, InterfaceC1739m interfaceC1739m) {
        C1656f c1656f = (C1656f) interfaceC1739m;
        if (interfaceC1661k.equals(c1656f.f482a.mo581a())) {
            return c1656f;
        }
        throw new ClassCastException("Chronology mismatch, required: " + interfaceC1661k.getId() + ", actual: " + c1656f.f482a.mo581a().getId());
    }

    public C1656f(InterfaceC1652b interfaceC1652b, C1715i c1715i) {
        Objects.requireNonNull(interfaceC1652b, "date");
        Objects.requireNonNull(c1715i, "time");
        this.f482a = interfaceC1652b;
        this.f483b = c1715i;
    }

    /* renamed from: V */
    public final C1656f m680V(InterfaceC1739m interfaceC1739m, C1715i c1715i) {
        InterfaceC1652b interfaceC1652b = this.f482a;
        return (interfaceC1652b == interfaceC1739m && this.f483b == c1715i) ? this : new C1656f(AbstractC1654d.m674Q(interfaceC1652b.mo581a(), interfaceC1739m), c1715i);
    }

    @Override // p017j$.time.chrono.ChronoLocalDateTime
    /* renamed from: a */
    public final InterfaceC1661k mo603a() {
        return this.f482a.mo581a();
    }

    @Override // p017j$.time.chrono.ChronoLocalDateTime
    /* renamed from: f */
    public final InterfaceC1652b mo606f() {
        return this.f482a;
    }

    public final int hashCode() {
        return this.f482a.hashCode() ^ this.f483b.hashCode();
    }

    public final String toString() {
        return this.f482a.toString() + "T" + this.f483b.toString();
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return m675Q(this.f482a.mo581a(), AbstractC1745s.m814b(this, j, enumC1728b));
    }

    @Override // p017j$.time.chrono.ChronoLocalDateTime
    /* renamed from: b */
    public final C1715i mo605b() {
        return this.f483b;
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
            if (!((EnumC1727a) interfaceC1744r).m801Q()) {
                return this.f482a.mo545k(interfaceC1744r);
            }
            C1715i c1715i = this.f483b;
            c1715i.getClass();
            return AbstractC1745s.m816d(c1715i, interfaceC1744r);
        }
        return interfaceC1744r.mo803j(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            return ((EnumC1727a) interfaceC1744r).m801Q() ? this.f483b.mo544i(interfaceC1744r) : this.f482a.mo544i(interfaceC1744r);
        }
        return mo545k(interfaceC1744r).m820a(mo542D(interfaceC1744r), interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            return ((EnumC1727a) interfaceC1744r).m801Q() ? this.f483b.mo542D(interfaceC1744r) : this.f482a.mo542D(interfaceC1744r);
        }
        return interfaceC1744r.mo806t(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        if (AbstractC1641c.m644b(localDate)) {
            return m680V(localDate, this.f483b);
        }
        InterfaceC1661k interfaceC1661kMo581a = this.f482a.mo581a();
        localDate.getClass();
        return m675Q(interfaceC1661kMo581a, (C1656f) AbstractC1636a.m505a(localDate, this));
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: U, reason: merged with bridge method [inline-methods] */
    public final C1656f mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            if (((EnumC1727a) interfaceC1744r).m801Q()) {
                return m680V(this.f482a, this.f483b.mo556c(j, interfaceC1744r));
            }
            return m680V(this.f482a.mo556c(j, interfaceC1744r), this.f483b);
        }
        return m675Q(this.f482a.mo581a(), interfaceC1744r.mo807y(this, j));
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: R, reason: merged with bridge method [inline-methods] */
    public final C1656f mo557d(long j, InterfaceC1746t interfaceC1746t) {
        if (!(interfaceC1746t instanceof EnumC1728b)) {
            return m675Q(this.f482a.mo581a(), interfaceC1746t.mo808i(this, j));
        }
        switch (AbstractC1655e.f481a[((EnumC1728b) interfaceC1746t).ordinal()]) {
            case 1:
                return m677S(this.f482a, 0L, 0L, 0L, j);
            case 2:
                C1656f c1656fM680V = m680V(this.f482a.mo557d(j / 86400000000L, (InterfaceC1746t) EnumC1728b.DAYS), this.f483b);
                return c1656fM680V.m677S(c1656fM680V.f482a, 0L, 0L, 0L, (j % 86400000000L) * 1000);
            case 3:
                C1656f c1656fM680V2 = m680V(this.f482a.mo557d(j / 86400000, (InterfaceC1746t) EnumC1728b.DAYS), this.f483b);
                return c1656fM680V2.m677S(c1656fM680V2.f482a, 0L, 0L, 0L, (j % 86400000) * 1000000);
            case 4:
                return m677S(this.f482a, 0L, 0L, j, 0L);
            case 5:
                return m677S(this.f482a, 0L, j, 0L, 0L);
            case 6:
                return m677S(this.f482a, j, 0L, 0L, 0L);
            case 7:
                C1656f c1656fM680V3 = m680V(this.f482a.mo557d(j / 256, (InterfaceC1746t) EnumC1728b.DAYS), this.f483b);
                return c1656fM680V3.m677S(c1656fM680V3.f482a, (j % 256) * 12, 0L, 0L, 0L);
            default:
                return m680V(this.f482a.mo557d(j, interfaceC1746t), this.f483b);
        }
    }

    /* renamed from: S */
    public final C1656f m677S(InterfaceC1652b interfaceC1652b, long j, long j2, long j3, long j4) {
        if ((j | j2 | j3 | j4) == 0) {
            return m680V(interfaceC1652b, this.f483b);
        }
        long j5 = j / 24;
        long j6 = ((j % 24) * 3600000000000L) + ((j2 % 1440) * 60000000000L) + ((j3 % 86400) * 1000000000) + (j4 % 86400000000000L);
        long jM781c0 = this.f483b.m781c0();
        long j7 = j6 + jM781c0;
        long jM499R = AbstractC1636a.m499R(j7, 86400000000000L) + j5 + (j2 / 1440) + (j3 / 86400) + (j4 / 86400000000000L);
        long jM498Q = AbstractC1636a.m498Q(j7, 86400000000000L);
        return m680V(interfaceC1652b.mo557d(jM499R, (InterfaceC1746t) EnumC1728b.DAYS), jM498Q == jM781c0 ? this.f483b : C1715i.m772V(jM498Q));
    }

    @Override // p017j$.time.chrono.ChronoLocalDateTime
    /* renamed from: z */
    public final ChronoZonedDateTime mo607z(ZoneId zoneId) {
        return C1660j.m681Q(zoneId, null, this);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return interfaceC1739m.mo556c(mo606f().mo567E(), EnumC1727a.EPOCH_DAY).mo556c(mo605b().m781c0(), EnumC1727a.NANO_OF_DAY);
    }

    private Object writeReplace() {
        return new C1645D((byte) 2, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChronoLocalDateTime) && AbstractC1636a.m509e(this, (ChronoLocalDateTime) obj) == 0;
    }

    @Override // p017j$.time.chrono.ChronoLocalDateTime
    public final Instant toInstant(ZoneOffset zoneOffset) {
        return Instant.m553S(m678T(zoneOffset), mo605b().f647d);
    }
}
