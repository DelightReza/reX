package p017j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.chrono.ChronoLocalDateTime;
import p017j$.time.chrono.ChronoZonedDateTime;
import p017j$.time.chrono.InterfaceC1652b;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;
import p017j$.time.zone.C1754b;
import p017j$.time.zone.ZoneRules;
import p017j$.util.Objects;

/* loaded from: classes2.dex */
public final class ZonedDateTime implements InterfaceC1739m, ChronoZonedDateTime<LocalDate>, Serializable {
    private static final long serialVersionUID = -6260982410461394882L;

    /* renamed from: a */
    public final LocalDateTime f462a;

    /* renamed from: b */
    public final ZoneOffset f463b;

    /* renamed from: c */
    public final ZoneId f464c;

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: P */
    public final /* synthetic */ long mo633P() {
        return AbstractC1636a.m526v(this);
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(ChronoZonedDateTime<?> chronoZonedDateTime) {
        return AbstractC1636a.m510f(this, chronoZonedDateTime);
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: a */
    public final InterfaceC1661k mo637a() {
        return ((LocalDate) mo639f()).mo581a();
    }

    /* renamed from: Q */
    public static ZonedDateTime m630Q(LocalDateTime localDateTime, ZoneId zoneId, ZoneOffset zoneOffset) {
        Objects.requireNonNull(localDateTime, "localDateTime");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId instanceof ZoneOffset) {
            return new ZonedDateTime(localDateTime, zoneId, (ZoneOffset) zoneId);
        }
        ZoneRules rules = zoneId.getRules();
        List listM841f = rules.m841f(localDateTime);
        if (listM841f.size() == 1) {
            zoneOffset = (ZoneOffset) listM841f.get(0);
        } else if (listM841f.size() == 0) {
            C1754b c1754bM840e = rules.m840e(localDateTime);
            localDateTime = localDateTime.m599W(Duration.ofSeconds(c1754bM840e.f740d.getTotalSeconds() - c1754bM840e.f739c.getTotalSeconds()).f431a);
            zoneOffset = c1754bM840e.f740d;
        } else if (zoneOffset == null || !listM841f.contains(zoneOffset)) {
            zoneOffset = (ZoneOffset) Objects.requireNonNull((ZoneOffset) listM841f.get(0), "offset");
        }
        return new ZonedDateTime(localDateTime, zoneId, zoneOffset);
    }

    /* renamed from: n */
    public static ZonedDateTime m631n(long j, int i, ZoneId zoneId) {
        ZoneOffset offset = zoneId.getRules().getOffset(Instant.m553S(j, i));
        return new ZonedDateTime(LocalDateTime.m594U(j, i, offset), zoneId, offset);
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    public final Instant toInstant() {
        return Instant.m553S(mo633P(), mo638b().f647d);
    }

    public ZonedDateTime(LocalDateTime localDateTime, ZoneId zoneId, ZoneOffset zoneOffset) {
        this.f462a = localDateTime;
        this.f463b = zoneOffset;
        this.f464c = zoneId;
    }

    /* renamed from: S */
    public final ZonedDateTime m635S(LocalDateTime localDateTime) {
        return m630Q(localDateTime, this.f464c, this.f463b);
    }

    /* renamed from: T */
    public final ZonedDateTime m636T(ZoneOffset zoneOffset) {
        return (zoneOffset.equals(this.f463b) || !this.f464c.getRules().m841f(this.f462a).contains(zoneOffset)) ? this : new ZonedDateTime(this.f462a, this.f464c, zoneOffset);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            return true;
        }
        return interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            if (interfaceC1744r == EnumC1727a.INSTANT_SECONDS || interfaceC1744r == EnumC1727a.OFFSET_SECONDS) {
                return ((EnumC1727a) interfaceC1744r).f671b;
            }
            return this.f462a.mo545k(interfaceC1744r);
        }
        return interfaceC1744r.mo803j(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            int i = AbstractC1752v.f721a[((EnumC1727a) interfaceC1744r).ordinal()];
            if (i == 1) {
                throw new C1747u("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
            }
            if (i == 2) {
                return this.f463b.getTotalSeconds();
            }
            return this.f462a.mo544i(interfaceC1744r);
        }
        return AbstractC1636a.m516l(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return interfaceC1744r.mo806t(this);
        }
        int i = AbstractC1752v.f721a[((EnumC1727a) interfaceC1744r).ordinal()];
        return i != 1 ? i != 2 ? this.f462a.mo542D(interfaceC1744r) : this.f463b.getTotalSeconds() : AbstractC1636a.m526v(this);
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: g */
    public final ZoneOffset mo640g() {
        return this.f463b;
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: C */
    public final ZoneId mo632C() {
        return this.f464c;
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: w */
    public final ChronoZonedDateTime mo642w(ZoneId zoneId) {
        Objects.requireNonNull(zoneId, "zone");
        return this.f464c.equals(zoneId) ? this : m630Q(this.f462a, zoneId, this.f463b);
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: o */
    public final ChronoLocalDateTime mo641o() {
        return this.f462a;
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: f */
    public final InterfaceC1652b mo639f() {
        return this.f462a.f443a;
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: b */
    public final C1715i mo638b() {
        return this.f462a.f444b;
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        if (AbstractC1641c.m644b(localDate)) {
            return m635S(LocalDateTime.m593T(localDate, this.f462a.f444b));
        }
        return (ZonedDateTime) localDate.mo546n(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: c */
    public final InterfaceC1739m mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
            int i = AbstractC1752v.f721a[enumC1727a.ordinal()];
            if (i == 1) {
                return m631n(j, this.f462a.f444b.f647d, this.f464c);
            }
            if (i == 2) {
                return m636T(ZoneOffset.m626W(enumC1727a.f671b.m820a(j, enumC1727a)));
            }
            return m635S(this.f462a.mo556c(j, interfaceC1744r));
        }
        return (ZonedDateTime) interfaceC1744r.mo807y(this, j);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: R, reason: merged with bridge method [inline-methods] */
    public final ZonedDateTime mo557d(long j, InterfaceC1746t interfaceC1746t) {
        if (!(interfaceC1746t instanceof EnumC1728b)) {
            return (ZonedDateTime) interfaceC1746t.mo808i(this, j);
        }
        EnumC1728b enumC1728b = (EnumC1728b) interfaceC1746t;
        if (enumC1728b.compareTo(EnumC1728b.DAYS) >= 0 && enumC1728b != EnumC1728b.FOREVER) {
            return m635S(this.f462a.mo557d(j, interfaceC1746t));
        }
        LocalDateTime localDateTimeMo557d = this.f462a.mo557d(j, interfaceC1746t);
        ZoneOffset zoneOffset = this.f463b;
        ZoneId zoneId = this.f464c;
        Objects.requireNonNull(localDateTimeMo557d, "localDateTime");
        Objects.requireNonNull(zoneOffset, "offset");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId.getRules().m841f(localDateTimeMo557d).contains(zoneOffset)) {
            return new ZonedDateTime(localDateTimeMo557d, zoneId, zoneOffset);
        }
        localDateTimeMo557d.getClass();
        return m631n(AbstractC1636a.m525u(localDateTimeMo557d, zoneOffset), localDateTimeMo557d.f444b.f647d, zoneId);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return j == Long.MIN_VALUE ? mo557d(Long.MAX_VALUE, enumC1728b).mo557d(1L, enumC1728b) : mo557d(-j, enumC1728b);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f695f) {
            return this.f462a.f443a;
        }
        return AbstractC1636a.m523s(this, c1678e);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ZonedDateTime) {
            ZonedDateTime zonedDateTime = (ZonedDateTime) obj;
            if (this.f462a.equals(zonedDateTime.f462a) && this.f463b.equals(zonedDateTime.f463b) && this.f464c.equals(zonedDateTime.f464c)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (this.f462a.hashCode() ^ this.f463b.f460b) ^ Integer.rotateLeft(this.f464c.hashCode(), 3);
    }

    public final String toString() {
        String str = this.f462a.toString() + this.f463b.f461c;
        ZoneOffset zoneOffset = this.f463b;
        ZoneId zoneId = this.f464c;
        if (zoneOffset == zoneId) {
            return str;
        }
        return str + "[" + zoneId.toString() + "]";
    }

    private Object writeReplace() {
        return new C1722p((byte) 6, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
