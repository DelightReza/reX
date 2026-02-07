package p017j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.chrono.C1668r;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;
import p017j$.util.Objects;

/* loaded from: classes2.dex */
public final class OffsetDateTime implements InterfaceC1739m, InterfaceC1741o, Comparable<OffsetDateTime>, Serializable {

    /* renamed from: c */
    public static final /* synthetic */ int f445c = 0;
    private static final long serialVersionUID = 2287754244819255394L;

    /* renamed from: a */
    public final LocalDateTime f446a;

    /* renamed from: b */
    public final ZoneOffset f447b;

    @Override // java.lang.Comparable
    public final int compareTo(OffsetDateTime offsetDateTime) {
        int iCompare;
        OffsetDateTime offsetDateTime2 = offsetDateTime;
        if (this.f447b.equals(offsetDateTime2.f447b)) {
            iCompare = toLocalDateTime().compareTo(offsetDateTime2.toLocalDateTime());
        } else {
            LocalDateTime localDateTime = this.f446a;
            ZoneOffset zoneOffset = this.f447b;
            localDateTime.getClass();
            long jM525u = AbstractC1636a.m525u(localDateTime, zoneOffset);
            LocalDateTime localDateTime2 = offsetDateTime2.f446a;
            ZoneOffset zoneOffset2 = offsetDateTime2.f447b;
            localDateTime2.getClass();
            iCompare = Long.compare(jM525u, AbstractC1636a.m525u(localDateTime2, zoneOffset2));
            if (iCompare == 0) {
                iCompare = this.f446a.f444b.f647d - offsetDateTime2.f446a.f444b.f647d;
            }
        }
        return iCompare == 0 ? toLocalDateTime().compareTo(offsetDateTime2.toLocalDateTime()) : iCompare;
    }

    static {
        LocalDateTime localDateTime = LocalDateTime.f441c;
        ZoneOffset zoneOffset = ZoneOffset.f459g;
        localDateTime.getClass();
        new OffsetDateTime(localDateTime, zoneOffset);
        LocalDateTime localDateTime2 = LocalDateTime.f442d;
        ZoneOffset zoneOffset2 = ZoneOffset.f458f;
        localDateTime2.getClass();
        new OffsetDateTime(localDateTime2, zoneOffset2);
    }

    /* renamed from: Q */
    public static OffsetDateTime m608Q(Instant instant, ZoneId zoneId) {
        Objects.requireNonNull(instant, "instant");
        Objects.requireNonNull(zoneId, "zone");
        ZoneOffset offset = zoneId.getRules().getOffset(instant);
        return new OffsetDateTime(LocalDateTime.m594U(instant.f434a, instant.f435b, offset), offset);
    }

    public OffsetDateTime(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        this.f446a = (LocalDateTime) Objects.requireNonNull(localDateTime, "dateTime");
        this.f447b = (ZoneOffset) Objects.requireNonNull(zoneOffset, "offset");
    }

    /* renamed from: S */
    public final OffsetDateTime m610S(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        return (this.f446a == localDateTime && this.f447b.equals(zoneOffset)) ? this : new OffsetDateTime(localDateTime, zoneOffset);
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
            if (interfaceC1744r != EnumC1727a.INSTANT_SECONDS && interfaceC1744r != EnumC1727a.OFFSET_SECONDS) {
                return this.f446a.mo545k(interfaceC1744r);
            }
            return ((EnumC1727a) interfaceC1744r).f671b;
        }
        return interfaceC1744r.mo803j(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            int i = AbstractC1720n.f655a[((EnumC1727a) interfaceC1744r).ordinal()];
            if (i == 1) {
                throw new C1747u("Invalid field 'InstantSeconds' for get() method, use getLong() instead");
            }
            if (i == 2) {
                return this.f447b.getTotalSeconds();
            }
            return this.f446a.mo544i(interfaceC1744r);
        }
        return AbstractC1745s.m813a(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return interfaceC1744r.mo806t(this);
        }
        int i = AbstractC1720n.f655a[((EnumC1727a) interfaceC1744r).ordinal()];
        if (i != 1) {
            return i != 2 ? this.f446a.mo542D(interfaceC1744r) : this.f447b.getTotalSeconds();
        }
        LocalDateTime localDateTime = this.f446a;
        ZoneOffset zoneOffset = this.f447b;
        localDateTime.getClass();
        return AbstractC1636a.m525u(localDateTime, zoneOffset);
    }

    public LocalDateTime toLocalDateTime() {
        return this.f446a;
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        if (AbstractC1641c.m644b(localDate)) {
            return m610S(this.f446a.mo591x(localDate), this.f447b);
        }
        localDate.getClass();
        return (OffsetDateTime) AbstractC1636a.m505a(localDate, this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: c */
    public final InterfaceC1739m mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
            int i = AbstractC1720n.f655a[enumC1727a.ordinal()];
            if (i == 1) {
                return m608Q(Instant.m553S(j, this.f446a.f444b.f647d), this.f447b);
            }
            if (i == 2) {
                return m610S(this.f446a, ZoneOffset.m626W(enumC1727a.f671b.m820a(j, enumC1727a)));
            }
            return m610S(this.f446a.mo556c(j, interfaceC1744r), this.f447b);
        }
        return (OffsetDateTime) interfaceC1744r.mo807y(this, j);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: R, reason: merged with bridge method [inline-methods] */
    public final OffsetDateTime mo557d(long j, InterfaceC1746t interfaceC1746t) {
        if (interfaceC1746t instanceof EnumC1728b) {
            return m610S(this.f446a.mo557d(j, interfaceC1746t), this.f447b);
        }
        return (OffsetDateTime) interfaceC1746t.mo808i(this, j);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return j == Long.MIN_VALUE ? mo557d(Long.MAX_VALUE, enumC1728b).mo557d(1L, enumC1728b) : mo557d(-j, enumC1728b);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f693d || c1678e == AbstractC1745s.f694e) {
            return this.f447b;
        }
        if (c1678e == AbstractC1745s.f690a) {
            return null;
        }
        if (c1678e == AbstractC1745s.f695f) {
            return this.f446a.f443a;
        }
        if (c1678e == AbstractC1745s.f696g) {
            return this.f446a.f444b;
        }
        if (c1678e == AbstractC1745s.f691b) {
            return C1668r.f512c;
        }
        if (c1678e == AbstractC1745s.f692c) {
            return EnumC1728b.NANOS;
        }
        return c1678e.m704g(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return interfaceC1739m.mo556c(this.f446a.f443a.mo567E(), EnumC1727a.EPOCH_DAY).mo556c(this.f446a.f444b.m781c0(), EnumC1727a.NANO_OF_DAY).mo556c(this.f447b.getTotalSeconds(), EnumC1727a.OFFSET_SECONDS);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof OffsetDateTime) {
            OffsetDateTime offsetDateTime = (OffsetDateTime) obj;
            if (this.f446a.equals(offsetDateTime.f446a) && this.f447b.equals(offsetDateTime.f447b)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.f446a.hashCode() ^ this.f447b.f460b;
    }

    public final String toString() {
        return this.f446a.toString() + this.f447b.f461c;
    }

    private Object writeReplace() {
        return new C1722p((byte) 10, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
