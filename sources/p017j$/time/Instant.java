package p017j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.telegram.messenger.MediaDataController;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.format.DateTimeFormatter;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
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
public final class Instant implements InterfaceC1739m, InterfaceC1741o, Comparable<Instant>, Serializable {

    /* renamed from: c */
    public static final Instant f433c = new Instant(0, 0);
    private static final long serialVersionUID = -665713676816604388L;

    /* renamed from: a */
    public final long f434a;

    /* renamed from: b */
    public final int f435b;

    public static Instant now() {
        C1639a.f465b.getClass();
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = MediaDataController.MAX_STYLE_RUNS_COUNT;
        return m551Q(AbstractC1636a.m499R(jCurrentTimeMillis, j), ((int) AbstractC1636a.m498Q(jCurrentTimeMillis, j)) * 1000000);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Instant instant) {
        Instant instant2 = instant;
        int iCompare = Long.compare(this.f434a, instant2.f434a);
        return iCompare != 0 ? iCompare : this.f435b - instant2.f435b;
    }

    static {
        m553S(-31557014167219200L, 0L);
        m553S(31556889864403199L, 999999999L);
    }

    /* renamed from: S */
    public static Instant m553S(long j, long j2) {
        return m551Q(AbstractC1636a.m494M(j, AbstractC1636a.m499R(j2, 1000000000L)), (int) AbstractC1636a.m498Q(j2, 1000000000L));
    }

    /* renamed from: R */
    public static Instant m552R(InterfaceC1740n interfaceC1740n) {
        if (interfaceC1740n instanceof Instant) {
            return (Instant) interfaceC1740n;
        }
        Objects.requireNonNull(interfaceC1740n, "temporal");
        try {
            return m553S(interfaceC1740n.mo542D(EnumC1727a.INSTANT_SECONDS), interfaceC1740n.mo544i(EnumC1727a.NANO_OF_SECOND));
        } catch (C1640b e) {
            throw new C1640b("Unable to obtain Instant from TemporalAccessor: " + interfaceC1740n + " of type " + interfaceC1740n.getClass().getName(), e);
        }
    }

    /* renamed from: Q */
    public static Instant m551Q(long j, int i) {
        if ((i | j) == 0) {
            return f433c;
        }
        if (j < -31557014167219200L || j > 31556889864403199L) {
            throw new C1640b("Instant exceeds minimum or maximum instant");
        }
        return new Instant(j, i);
    }

    public Instant(long j, int i) {
        this.f434a = j;
        this.f435b = i;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return interfaceC1744r instanceof EnumC1727a ? interfaceC1744r == EnumC1727a.INSTANT_SECONDS || interfaceC1744r == EnumC1727a.NANO_OF_SECOND || interfaceC1744r == EnumC1727a.MICRO_OF_SECOND || interfaceC1744r == EnumC1727a.MILLI_OF_SECOND : interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m816d(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return AbstractC1745s.m816d(this, interfaceC1744r).m820a(interfaceC1744r.mo806t(this), interfaceC1744r);
        }
        int i = AbstractC1677d.f529a[((EnumC1727a) interfaceC1744r).ordinal()];
        if (i == 1) {
            return this.f435b;
        }
        if (i == 2) {
            return this.f435b / MediaDataController.MAX_STYLE_RUNS_COUNT;
        }
        if (i == 3) {
            return this.f435b / 1000000;
        }
        if (i == 4) {
            EnumC1727a enumC1727a = EnumC1727a.INSTANT_SECONDS;
            enumC1727a.f671b.m820a(this.f434a, enumC1727a);
        }
        throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        int i;
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return interfaceC1744r.mo806t(this);
        }
        int i2 = AbstractC1677d.f529a[((EnumC1727a) interfaceC1744r).ordinal()];
        if (i2 == 1) {
            i = this.f435b;
        } else if (i2 == 2) {
            i = this.f435b / MediaDataController.MAX_STYLE_RUNS_COUNT;
        } else {
            if (i2 != 3) {
                if (i2 == 4) {
                    return this.f434a;
                }
                throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
            }
            i = this.f435b / 1000000;
        }
        return i;
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        localDate.getClass();
        return (Instant) AbstractC1636a.m505a(localDate, this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: c */
    public final InterfaceC1739m mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return (Instant) interfaceC1744r.mo807y(this, j);
        }
        EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
        enumC1727a.m800D(j);
        int i = AbstractC1677d.f529a[enumC1727a.ordinal()];
        if (i != 1) {
            if (i == 2) {
                int i2 = ((int) j) * MediaDataController.MAX_STYLE_RUNS_COUNT;
                if (i2 != this.f435b) {
                    return m551Q(this.f434a, i2);
                }
            } else if (i == 3) {
                int i3 = ((int) j) * 1000000;
                if (i3 != this.f435b) {
                    return m551Q(this.f434a, i3);
                }
            } else {
                if (i != 4) {
                    throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
                }
                if (j != this.f434a) {
                    return m551Q(j, this.f435b);
                }
            }
        } else if (j != this.f435b) {
            return m551Q(this.f434a, (int) j);
        }
        return this;
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: U, reason: merged with bridge method [inline-methods] */
    public final Instant mo557d(long j, InterfaceC1746t interfaceC1746t) {
        if (!(interfaceC1746t instanceof EnumC1728b)) {
            return (Instant) interfaceC1746t.mo808i(this, j);
        }
        switch (AbstractC1677d.f530b[((EnumC1728b) interfaceC1746t).ordinal()]) {
            case 1:
                return m554T(0L, j);
            case 2:
                return m554T(j / 1000000, (j % 1000000) * 1000);
            case 3:
                return m554T(j / 1000, (j % 1000) * 1000000);
            case 4:
                return plusSeconds(j);
            case 5:
                return plusSeconds(AbstractC1636a.m500S(j, 60));
            case 6:
                return plusSeconds(AbstractC1636a.m500S(j, 3600));
            case 7:
                return plusSeconds(AbstractC1636a.m500S(j, 43200));
            case 8:
                return plusSeconds(AbstractC1636a.m500S(j, 86400));
            default:
                throw new C1747u("Unsupported unit: " + interfaceC1746t);
        }
    }

    public Instant plusSeconds(long j) {
        return m554T(j, 0L);
    }

    /* renamed from: T */
    public final Instant m554T(long j, long j2) {
        if ((j | j2) == 0) {
            return this;
        }
        return m553S(AbstractC1636a.m494M(AbstractC1636a.m494M(this.f434a, j), j2 / 1000000000), this.f435b + (j2 % 1000000000));
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return j == Long.MIN_VALUE ? mo557d(Long.MAX_VALUE, enumC1728b).mo557d(1L, enumC1728b) : mo557d(-j, enumC1728b);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f692c) {
            return EnumC1728b.NANOS;
        }
        if (c1678e == AbstractC1745s.f691b || c1678e == AbstractC1745s.f690a || c1678e == AbstractC1745s.f694e || c1678e == AbstractC1745s.f693d || c1678e == AbstractC1745s.f695f || c1678e == AbstractC1745s.f696g) {
            return null;
        }
        return c1678e.m704g(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return interfaceC1739m.mo556c(this.f434a, EnumC1727a.INSTANT_SECONDS).mo556c(this.f435b, EnumC1727a.NANO_OF_SECOND);
    }

    public OffsetDateTime atOffset(ZoneOffset zoneOffset) {
        return OffsetDateTime.m608Q(this, zoneOffset);
    }

    public long toEpochMilli() {
        long j = this.f434a;
        return (j >= 0 || this.f435b <= 0) ? AbstractC1636a.m494M(AbstractC1636a.m500S(j, MediaDataController.MAX_STYLE_RUNS_COUNT), this.f435b / 1000000) : AbstractC1636a.m494M(AbstractC1636a.m500S(j + 1, MediaDataController.MAX_STYLE_RUNS_COUNT), (this.f435b / 1000000) - MediaDataController.MAX_STYLE_RUNS_COUNT);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Instant) {
            Instant instant = (Instant) obj;
            if (this.f434a == instant.f434a && this.f435b == instant.f435b) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        long j = this.f434a;
        return (this.f435b * 51) + ((int) (j ^ (j >>> 32)));
    }

    public String toString() {
        return DateTimeFormatter.f548f.m719a(this);
    }

    private Object writeReplace() {
        return new C1722p((byte) 2, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
