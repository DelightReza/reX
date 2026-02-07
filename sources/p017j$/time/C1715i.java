package p017j$.time;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.mvel2.MVEL;
import org.telegram.messenger.MediaDataController;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
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

/* renamed from: j$.time.i */
/* loaded from: classes2.dex */
public final class C1715i implements InterfaceC1739m, InterfaceC1741o, Comparable, Serializable {

    /* renamed from: e */
    public static final C1715i f640e;

    /* renamed from: f */
    public static final C1715i f641f;

    /* renamed from: g */
    public static final C1715i f642g;

    /* renamed from: h */
    public static final C1715i[] f643h = new C1715i[24];
    private static final long serialVersionUID = 6414437269572265201L;

    /* renamed from: a */
    public final byte f644a;

    /* renamed from: b */
    public final byte f645b;

    /* renamed from: c */
    public final byte f646c;

    /* renamed from: d */
    public final int f647d;

    static {
        int i = 0;
        while (true) {
            C1715i[] c1715iArr = f643h;
            if (i < c1715iArr.length) {
                c1715iArr[i] = new C1715i(i, 0, 0, 0);
                i++;
            } else {
                C1715i c1715i = c1715iArr[0];
                f642g = c1715i;
                C1715i c1715i2 = c1715iArr[12];
                f640e = c1715i;
                f641f = new C1715i(23, 59, 59, 999999999);
                return;
            }
        }
    }

    /* renamed from: U */
    public static C1715i m771U(int i, int i2, int i3, int i4) {
        EnumC1727a.HOUR_OF_DAY.m800D(i);
        EnumC1727a.MINUTE_OF_HOUR.m800D(i2);
        EnumC1727a.SECOND_OF_MINUTE.m800D(i3);
        EnumC1727a.NANO_OF_SECOND.m800D(i4);
        return m769R(i, i2, i3, i4);
    }

    /* renamed from: V */
    public static C1715i m772V(long j) {
        EnumC1727a.NANO_OF_DAY.m800D(j);
        int i = (int) (j / 3600000000000L);
        long j2 = j - (i * 3600000000000L);
        int i2 = (int) (j2 / 60000000000L);
        long j3 = j2 - (i2 * 60000000000L);
        int i3 = (int) (j3 / 1000000000);
        return m769R(i, i2, i3, (int) (j3 - (i3 * 1000000000)));
    }

    /* renamed from: S */
    public static C1715i m770S(InterfaceC1740n interfaceC1740n) {
        Objects.requireNonNull(interfaceC1740n, "temporal");
        C1715i c1715i = (C1715i) interfaceC1740n.mo547t(AbstractC1745s.f696g);
        if (c1715i != null) {
            return c1715i;
        }
        throw new C1640b("Unable to obtain LocalTime from TemporalAccessor: " + interfaceC1740n + " of type " + interfaceC1740n.getClass().getName());
    }

    /* renamed from: R */
    public static C1715i m769R(int i, int i2, int i3, int i4) {
        if ((i2 | i3 | i4) == 0) {
            return f643h[i];
        }
        return new C1715i(i, i2, i3, i4);
    }

    public C1715i(int i, int i2, int i3, int i4) {
        this.f644a = (byte) i;
        this.f645b = (byte) i2;
        this.f646c = (byte) i3;
        this.f647d = i4;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            return ((EnumC1727a) interfaceC1744r).m801Q();
        }
        return interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m816d(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            return m775T(interfaceC1744r);
        }
        return AbstractC1745s.m813a(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            if (interfaceC1744r == EnumC1727a.NANO_OF_DAY) {
                return m781c0();
            }
            if (interfaceC1744r == EnumC1727a.MICRO_OF_DAY) {
                return m781c0() / 1000;
            }
            return m775T(interfaceC1744r);
        }
        return interfaceC1744r.mo806t(this);
    }

    /* renamed from: T */
    public final int m775T(InterfaceC1744r interfaceC1744r) {
        switch (AbstractC1714h.f638a[((EnumC1727a) interfaceC1744r).ordinal()]) {
            case 1:
                return this.f647d;
            case 2:
                throw new C1747u("Invalid field 'NanoOfDay' for get() method, use getLong() instead");
            case 3:
                return this.f647d / MediaDataController.MAX_STYLE_RUNS_COUNT;
            case 4:
                throw new C1747u("Invalid field 'MicroOfDay' for get() method, use getLong() instead");
            case 5:
                return this.f647d / 1000000;
            case 6:
                return (int) (m781c0() / 1000000);
            case 7:
                return this.f646c;
            case 8:
                return m782d0();
            case 9:
                return this.f645b;
            case 10:
                return (this.f644a * 60) + this.f645b;
            case 11:
                return this.f644a % 12;
            case 12:
                int i = this.f644a % 12;
                if (i % 12 == 0) {
                    return 12;
                }
                return i;
            case 13:
                return this.f644a;
            case 14:
                byte b = this.f644a;
                if (b == 0) {
                    return 24;
                }
                return b;
            case 15:
                return this.f644a / 12;
            default:
                throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        localDate.getClass();
        return (C1715i) AbstractC1636a.m505a(localDate, this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: e0, reason: merged with bridge method [inline-methods] */
    public final C1715i mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return (C1715i) interfaceC1744r.mo807y(this, j);
        }
        EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
        enumC1727a.m800D(j);
        switch (AbstractC1714h.f638a[enumC1727a.ordinal()]) {
            case 1:
                return m784f0((int) j);
            case 2:
                return m772V(j);
            case 3:
                return m784f0(((int) j) * MediaDataController.MAX_STYLE_RUNS_COUNT);
            case 4:
                return m772V(j * 1000);
            case 5:
                return m784f0(((int) j) * 1000000);
            case 6:
                return m772V(j * 1000000);
            case 7:
                int i = (int) j;
                if (this.f646c != i) {
                    EnumC1727a.SECOND_OF_MINUTE.m800D(i);
                    return m769R(this.f644a, this.f645b, i, this.f647d);
                }
                return this;
            case 8:
                return m780a0(j - m782d0());
            case 9:
                int i2 = (int) j;
                if (this.f645b != i2) {
                    EnumC1727a.MINUTE_OF_HOUR.m800D(i2);
                    return m769R(this.f644a, i2, this.f646c, this.f647d);
                }
                return this;
            case 10:
                return m778Y(j - ((this.f644a * 60) + this.f645b));
            case 11:
                return m777X(j - (this.f644a % 12));
            case 12:
                if (j == 12) {
                    j = 0;
                }
                return m777X(j - (this.f644a % 12));
            case 13:
                int i3 = (int) j;
                if (this.f644a != i3) {
                    EnumC1727a.HOUR_OF_DAY.m800D(i3);
                    return m769R(i3, this.f645b, this.f646c, this.f647d);
                }
                return this;
            case 14:
                if (j == 24) {
                    j = 0;
                }
                int i4 = (int) j;
                if (this.f644a != i4) {
                    EnumC1727a.HOUR_OF_DAY.m800D(i4);
                    return m769R(i4, this.f645b, this.f646c, this.f647d);
                }
                return this;
            case 15:
                return m777X((j - (this.f644a / 12)) * 12);
            default:
                throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
    }

    /* renamed from: f0 */
    public final C1715i m784f0(int i) {
        if (this.f647d == i) {
            return this;
        }
        EnumC1727a.NANO_OF_SECOND.m800D(i);
        return m769R(this.f644a, this.f645b, this.f646c, i);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: W, reason: merged with bridge method [inline-methods] */
    public final C1715i mo557d(long j, InterfaceC1746t interfaceC1746t) {
        if (interfaceC1746t instanceof EnumC1728b) {
            switch (AbstractC1714h.f639b[((EnumC1728b) interfaceC1746t).ordinal()]) {
                case 1:
                    return m779Z(j);
                case 2:
                    return m779Z((j % 86400000000L) * 1000);
                case 3:
                    return m779Z((j % 86400000) * 1000000);
                case 4:
                    return m780a0(j);
                case 5:
                    return m778Y(j);
                case 6:
                    return m777X(j);
                case 7:
                    return m777X((j % 2) * 12);
                default:
                    throw new C1747u("Unsupported unit: " + interfaceC1746t);
            }
        }
        return (C1715i) interfaceC1746t.mo808i(this, j);
    }

    /* renamed from: X */
    public final C1715i m777X(long j) {
        return j == 0 ? this : m769R(((((int) (j % 24)) + this.f644a) + 24) % 24, this.f645b, this.f646c, this.f647d);
    }

    /* renamed from: Y */
    public final C1715i m778Y(long j) {
        if (j != 0) {
            int i = (this.f644a * 60) + this.f645b;
            int i2 = ((((int) (j % 1440)) + i) + 1440) % 1440;
            if (i != i2) {
                return m769R(i2 / 60, i2 % 60, this.f646c, this.f647d);
            }
        }
        return this;
    }

    /* renamed from: a0 */
    public final C1715i m780a0(long j) {
        if (j != 0) {
            int i = (this.f645b * 60) + (this.f644a * 3600) + this.f646c;
            int i2 = ((((int) (j % 86400)) + i) + 86400) % 86400;
            if (i != i2) {
                return m769R(i2 / 3600, (i2 / 60) % 60, i2 % 60, this.f647d);
            }
        }
        return this;
    }

    /* renamed from: Z */
    public final C1715i m779Z(long j) {
        if (j != 0) {
            long jM781c0 = m781c0();
            long j2 = (((j % 86400000000000L) + jM781c0) + 86400000000000L) % 86400000000000L;
            if (jM781c0 != j2) {
                return m769R((int) (j2 / 3600000000000L), (int) ((j2 / 60000000000L) % 60), (int) ((j2 / 1000000000) % 60), (int) (j2 % 1000000000));
            }
        }
        return this;
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return j == Long.MIN_VALUE ? mo557d(Long.MAX_VALUE, enumC1728b).mo557d(1L, enumC1728b) : mo557d(-j, enumC1728b);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f691b || c1678e == AbstractC1745s.f690a || c1678e == AbstractC1745s.f694e || c1678e == AbstractC1745s.f693d) {
            return null;
        }
        if (c1678e == AbstractC1745s.f696g) {
            return this;
        }
        if (c1678e == AbstractC1745s.f695f) {
            return null;
        }
        if (c1678e == AbstractC1745s.f692c) {
            return EnumC1728b.NANOS;
        }
        return c1678e.m704g(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return interfaceC1739m.mo556c(m781c0(), EnumC1727a.NANO_OF_DAY);
    }

    /* renamed from: d0 */
    public final int m782d0() {
        return (this.f645b * 60) + (this.f644a * 3600) + this.f646c;
    }

    /* renamed from: c0 */
    public final long m781c0() {
        return (this.f646c * 1000000000) + (this.f645b * 60000000000L) + (this.f644a * 3600000000000L) + this.f647d;
    }

    @Override // java.lang.Comparable
    /* renamed from: Q, reason: merged with bridge method [inline-methods] */
    public final int compareTo(C1715i c1715i) {
        int iCompare = Integer.compare(this.f644a, c1715i.f644a);
        return (iCompare == 0 && (iCompare = Integer.compare(this.f645b, c1715i.f645b)) == 0 && (iCompare = Integer.compare(this.f646c, c1715i.f646c)) == 0) ? Integer.compare(this.f647d, c1715i.f647d) : iCompare;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C1715i) {
            C1715i c1715i = (C1715i) obj;
            if (this.f644a == c1715i.f644a && this.f645b == c1715i.f645b && this.f646c == c1715i.f646c && this.f647d == c1715i.f647d) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        long jM781c0 = m781c0();
        return (int) (jM781c0 ^ (jM781c0 >>> 32));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(18);
        byte b = this.f644a;
        byte b2 = this.f645b;
        byte b3 = this.f646c;
        int i = this.f647d;
        sb.append(b < 10 ? MVEL.VERSION_SUB : "");
        sb.append((int) b);
        sb.append(b2 < 10 ? ":0" : ":");
        sb.append((int) b2);
        if (b3 > 0 || i > 0) {
            sb.append(b3 < 10 ? ":0" : ":");
            sb.append((int) b3);
            if (i > 0) {
                sb.append('.');
                if (i % 1000000 == 0) {
                    sb.append(Integer.toString((i / 1000000) + MediaDataController.MAX_STYLE_RUNS_COUNT).substring(1));
                } else if (i % MediaDataController.MAX_STYLE_RUNS_COUNT == 0) {
                    sb.append(Integer.toString((i / MediaDataController.MAX_STYLE_RUNS_COUNT) + 1000000).substring(1));
                } else {
                    sb.append(Integer.toString(i + 1000000000).substring(1));
                }
            }
        }
        return sb.toString();
    }

    private Object writeReplace() {
        return new C1722p((byte) 4, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    /* renamed from: g0 */
    public final void m785g0(DataOutput dataOutput) throws IOException {
        if (this.f647d == 0) {
            if (this.f646c == 0) {
                if (this.f645b == 0) {
                    dataOutput.writeByte(~this.f644a);
                    return;
                } else {
                    dataOutput.writeByte(this.f644a);
                    dataOutput.writeByte(~this.f645b);
                    return;
                }
            }
            dataOutput.writeByte(this.f644a);
            dataOutput.writeByte(this.f645b);
            dataOutput.writeByte(~this.f646c);
            return;
        }
        dataOutput.writeByte(this.f644a);
        dataOutput.writeByte(this.f645b);
        dataOutput.writeByte(this.f646c);
        dataOutput.writeInt(this.f647d);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v4, types: [int] */
    /* renamed from: b0 */
    public static C1715i m773b0(DataInput dataInput) throws IOException {
        int i;
        int i2;
        int i3 = dataInput.readByte();
        byte b = 0;
        if (i3 >= 0) {
            byte b2 = dataInput.readByte();
            if (b2 < 0) {
                ?? r4 = ~b2;
                i = 0;
                b = r4;
                i2 = 0;
            } else {
                byte b3 = dataInput.readByte();
                if (b3 < 0) {
                    i2 = ~b3;
                    b = b2;
                } else {
                    i = dataInput.readInt();
                    b = b2;
                    i2 = b3;
                }
            }
            return m771U(i3, b, i2, i);
        }
        i3 = ~i3;
        i2 = 0;
        i = 0;
        return m771U(i3, b, i2, i);
    }
}
