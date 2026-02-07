package p017j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1678e;
import p017j$.time.C1715i;
import p017j$.time.Duration;
import p017j$.time.Instant;
import p017j$.time.LocalDate;
import p017j$.time.LocalDateTime;
import p017j$.time.ZoneId;
import p017j$.time.ZoneOffset;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;
import p017j$.time.zone.C1754b;
import p017j$.time.zone.ZoneRules;
import p017j$.util.Objects;

/* renamed from: j$.time.chrono.j */
/* loaded from: classes2.dex */
public final class C1660j implements ChronoZonedDateTime, Serializable {
    private static final long serialVersionUID = -5261813987200935591L;

    /* renamed from: a */
    public final transient C1656f f491a;

    /* renamed from: b */
    public final transient ZoneOffset f492b;

    /* renamed from: c */
    public final transient ZoneId f493c;

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: P */
    public final /* synthetic */ long mo633P() {
        return AbstractC1636a.m526v(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final /* synthetic */ int mo544i(InterfaceC1744r interfaceC1744r) {
        return AbstractC1636a.m516l(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final /* synthetic */ Object mo547t(C1678e c1678e) {
        return AbstractC1636a.m523s(this, c1678e);
    }

    @Override // java.lang.Comparable
    public final /* synthetic */ int compareTo(ChronoZonedDateTime<?> chronoZonedDateTime) {
        return AbstractC1636a.m510f(this, chronoZonedDateTime);
    }

    /* renamed from: Q */
    public static C1660j m681Q(ZoneId zoneId, ZoneOffset zoneOffset, C1656f c1656f) {
        Objects.requireNonNull(c1656f, "localDateTime");
        Objects.requireNonNull(zoneId, "zone");
        if (zoneId instanceof ZoneOffset) {
            return new C1660j(zoneId, (ZoneOffset) zoneId, c1656f);
        }
        ZoneRules rules = zoneId.getRules();
        LocalDateTime localDateTimeM592R = LocalDateTime.m592R(c1656f);
        List listM841f = rules.m841f(localDateTimeM592R);
        if (listM841f.size() == 1) {
            zoneOffset = (ZoneOffset) listM841f.get(0);
        } else if (listM841f.size() == 0) {
            C1754b c1754bM840e = rules.m840e(localDateTimeM592R);
            c1656f = c1656f.m677S(c1656f.f482a, 0L, 0L, Duration.ofSeconds(c1754bM840e.f740d.getTotalSeconds() - c1754bM840e.f739c.getTotalSeconds()).f431a, 0L);
            zoneOffset = c1754bM840e.f740d;
        } else {
            if (zoneOffset == null || !listM841f.contains(zoneOffset)) {
                zoneOffset = (ZoneOffset) listM841f.get(0);
            }
            c1656f = c1656f;
        }
        Objects.requireNonNull(zoneOffset, "offset");
        return new C1660j(zoneId, zoneOffset, c1656f);
    }

    /* renamed from: R */
    public static C1660j m682R(InterfaceC1661k interfaceC1661k, Instant instant, ZoneId zoneId) {
        ZoneOffset offset = zoneId.getRules().getOffset(instant);
        Objects.requireNonNull(offset, "offset");
        return new C1660j(zoneId, offset, (C1656f) interfaceC1661k.mo670B(LocalDateTime.m594U(instant.f434a, instant.f435b, offset)));
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            if (interfaceC1744r != EnumC1727a.INSTANT_SECONDS && interfaceC1744r != EnumC1727a.OFFSET_SECONDS) {
                return ((C1656f) mo641o()).mo545k(interfaceC1744r);
            }
            return ((EnumC1727a) interfaceC1744r).f671b;
        }
        return interfaceC1744r.mo803j(this);
    }

    /* renamed from: n */
    public static C1660j m683n(InterfaceC1661k interfaceC1661k, InterfaceC1739m interfaceC1739m) {
        C1660j c1660j = (C1660j) interfaceC1739m;
        if (interfaceC1661k.equals(c1660j.mo637a())) {
            return c1660j;
        }
        throw new ClassCastException("Chronology mismatch, required: " + interfaceC1661k.getId() + ", actual: " + c1660j.mo637a().getId());
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            int i = AbstractC1658h.f489a[((EnumC1727a) interfaceC1744r).ordinal()];
            if (i == 1) {
                return mo633P();
            }
            if (i == 2) {
                return mo640g().getTotalSeconds();
            }
            return ((C1656f) mo641o()).mo542D(interfaceC1744r);
        }
        return interfaceC1744r.mo806t(this);
    }

    public C1660j(ZoneId zoneId, ZoneOffset zoneOffset, C1656f c1656f) {
        this.f491a = (C1656f) Objects.requireNonNull(c1656f, "dateTime");
        this.f492b = (ZoneOffset) Objects.requireNonNull(zoneOffset, "offset");
        this.f493c = (ZoneId) Objects.requireNonNull(zoneId, "zone");
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: g */
    public final ZoneOffset mo640g() {
        return this.f492b;
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: f */
    public final InterfaceC1652b mo639f() {
        return ((C1656f) mo641o()).mo606f();
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: b */
    public final C1715i mo638b() {
        return ((C1656f) mo641o()).mo605b();
    }

    public final int hashCode() {
        return (this.f491a.hashCode() ^ this.f492b.f460b) ^ Integer.rotateLeft(this.f493c.hashCode(), 3);
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: o */
    public final ChronoLocalDateTime mo641o() {
        return this.f491a;
    }

    public final String toString() {
        String str = this.f491a.toString() + this.f492b.f461c;
        ZoneOffset zoneOffset = this.f492b;
        ZoneId zoneId = this.f493c;
        if (zoneOffset == zoneId) {
            return str;
        }
        return str + "[" + zoneId.toString() + "]";
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: C */
    public final ZoneId mo632C() {
        return this.f493c;
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: a */
    public final InterfaceC1661k mo637a() {
        return mo639f().mo581a();
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    /* renamed from: w */
    public final ChronoZonedDateTime mo642w(ZoneId zoneId) {
        return m681Q(zoneId, this.f492b, this.f491a);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            return true;
        }
        return interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: c */
    public final InterfaceC1739m mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return m683n(mo637a(), interfaceC1744r.mo807y(this, j));
        }
        EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
        int i = AbstractC1659i.f490a[enumC1727a.ordinal()];
        if (i == 1) {
            return mo557d(j - AbstractC1636a.m526v(this), EnumC1728b.SECONDS);
        }
        if (i != 2) {
            return m681Q(this.f493c, this.f492b, this.f491a.mo556c(j, interfaceC1744r));
        }
        ZoneOffset zoneOffsetM626W = ZoneOffset.m626W(enumC1727a.f671b.m820a(j, enumC1727a));
        C1656f c1656f = this.f491a;
        c1656f.getClass();
        return m682R(mo637a(), Instant.m553S(c1656f.m678T(zoneOffsetM626W), c1656f.mo605b().f647d), this.f493c);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: S, reason: merged with bridge method [inline-methods] */
    public final C1660j mo557d(long j, InterfaceC1746t interfaceC1746t) {
        if (interfaceC1746t instanceof EnumC1728b) {
            return m683n(mo637a(), this.f491a.mo557d(j, interfaceC1746t).mo546n(this));
        }
        return m683n(mo637a(), interfaceC1746t.mo808i(this, j));
    }

    private Object writeReplace() {
        return new C1645D((byte) 3, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ChronoZonedDateTime) && AbstractC1636a.m510f(this, (ChronoZonedDateTime) obj) == 0;
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        return m683n(mo637a(), localDate.mo546n(this));
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return m683n(mo637a(), AbstractC1745s.m814b(this, j, enumC1728b));
    }

    @Override // p017j$.time.chrono.ChronoZonedDateTime
    public final Instant toInstant() {
        return Instant.m553S(mo633P(), mo638b().f647d);
    }
}
