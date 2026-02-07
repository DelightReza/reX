package p017j$.time;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import org.mvel2.MVEL;
import org.mvel2.asm.Opcodes;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.zone.ZoneRules;
import p017j$.util.Objects;
import p017j$.util.concurrent.ConcurrentHashMap;

/* loaded from: classes2.dex */
public final class ZoneOffset extends ZoneId implements InterfaceC1740n, InterfaceC1741o, Comparable<ZoneOffset>, Serializable {
    private static final long serialVersionUID = 2357656521762053153L;

    /* renamed from: b */
    public final int f460b;

    /* renamed from: c */
    public final transient String f461c;

    /* renamed from: d */
    public static final ConcurrentHashMap f456d = new ConcurrentHashMap(16, 0.75f, 4);

    /* renamed from: e */
    public static final ConcurrentHashMap f457e = new ConcurrentHashMap(16, 0.75f, 4);
    public static final ZoneOffset UTC = m626W(0);

    /* renamed from: f */
    public static final ZoneOffset f458f = m626W(-64800);

    /* renamed from: g */
    public static final ZoneOffset f459g = m626W(64800);

    @Override // java.lang.Comparable
    public final int compareTo(ZoneOffset zoneOffset) {
        return zoneOffset.f460b - this.f460b;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:28:0x008f A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a8  */
    /* renamed from: U */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static p017j$.time.ZoneOffset m624U(java.lang.String r7) {
        /*
            java.lang.String r0 = "offsetId"
            p017j$.util.Objects.requireNonNull(r7, r0)
            j$.util.concurrent.ConcurrentHashMap r0 = p017j$.time.ZoneOffset.f457e
            java.lang.Object r0 = r0.get(r7)
            j$.time.ZoneOffset r0 = (p017j$.time.ZoneOffset) r0
            if (r0 == 0) goto L10
            return r0
        L10:
            int r0 = r7.length()
            r1 = 2
            r2 = 1
            r3 = 0
            if (r0 == r1) goto L63
            r1 = 3
            if (r0 == r1) goto L7f
            r4 = 5
            if (r0 == r4) goto L5a
            r5 = 6
            r6 = 4
            if (r0 == r5) goto L50
            r5 = 7
            if (r0 == r5) goto L43
            r1 = 9
            if (r0 != r1) goto L37
            int r0 = m627X(r7, r2, r3)
            int r1 = m627X(r7, r6, r2)
            int r2 = m627X(r7, r5, r2)
            goto L85
        L37:
            j$.time.b r0 = new j$.time.b
            java.lang.String r1 = "Invalid ID for ZoneOffset, invalid format: "
            java.lang.String r7 = r1.concat(r7)
            r0.<init>(r7)
            throw r0
        L43:
            int r0 = m627X(r7, r2, r3)
            int r1 = m627X(r7, r1, r3)
            int r2 = m627X(r7, r4, r3)
            goto L85
        L50:
            int r0 = m627X(r7, r2, r3)
            int r1 = m627X(r7, r6, r2)
        L58:
            r2 = 0
            goto L85
        L5a:
            int r0 = m627X(r7, r2, r3)
            int r1 = m627X(r7, r1, r3)
            goto L58
        L63:
            char r0 = r7.charAt(r3)
            char r7 = r7.charAt(r2)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r0 = "0"
            r1.append(r0)
            r1.append(r7)
            java.lang.String r7 = r1.toString()
        L7f:
            int r0 = m627X(r7, r2, r3)
            r1 = 0
            goto L58
        L85:
            char r3 = r7.charAt(r3)
            r4 = 43
            r5 = 45
            if (r3 == r4) goto L9e
            if (r3 != r5) goto L92
            goto L9e
        L92:
            j$.time.b r0 = new j$.time.b
            java.lang.String r1 = "Invalid ID for ZoneOffset, plus/minus not found when expected: "
            java.lang.String r7 = r1.concat(r7)
            r0.<init>(r7)
            throw r0
        L9e:
            if (r3 != r5) goto La8
            int r7 = -r0
            int r0 = -r1
            int r1 = -r2
            j$.time.ZoneOffset r7 = m625V(r7, r0, r1)
            return r7
        La8:
            j$.time.ZoneOffset r7 = m625V(r0, r1, r2)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.time.ZoneOffset.m624U(java.lang.String):j$.time.ZoneOffset");
    }

    @Override // p017j$.time.ZoneId
    public final ZoneRules getRules() {
        Objects.requireNonNull(this, "offset");
        return new ZoneRules(this);
    }

    /* renamed from: X */
    public static int m627X(CharSequence charSequence, int i, boolean z) {
        if (z) {
            String str = (String) charSequence;
            if (str.charAt(i - 1) != ':') {
                throw new C1640b("Invalid ID for ZoneOffset, colon not found when expected: " + ((Object) str));
            }
        }
        String str2 = (String) charSequence;
        char cCharAt = str2.charAt(i);
        char cCharAt2 = str2.charAt(i + 1);
        if (cCharAt >= '0' && cCharAt <= '9' && cCharAt2 >= '0' && cCharAt2 <= '9') {
            return (cCharAt2 - '0') + ((cCharAt - '0') * 10);
        }
        throw new C1640b("Invalid ID for ZoneOffset, non numeric characters found: " + ((Object) str2));
    }

    /* renamed from: V */
    public static ZoneOffset m625V(int i, int i2, int i3) {
        if (i < -18 || i > 18) {
            throw new C1640b("Zone offset hours not in valid range: value " + i + " is not in the range -18 to 18");
        }
        if (i > 0) {
            if (i2 < 0 || i3 < 0) {
                throw new C1640b("Zone offset minutes and seconds must be positive because hours is positive");
            }
        } else if (i < 0) {
            if (i2 > 0 || i3 > 0) {
                throw new C1640b("Zone offset minutes and seconds must be negative because hours is negative");
            }
        } else if ((i2 > 0 && i3 < 0) || (i2 < 0 && i3 > 0)) {
            throw new C1640b("Zone offset minutes and seconds must have the same sign");
        }
        if (i2 < -59 || i2 > 59) {
            throw new C1640b("Zone offset minutes not in valid range: value " + i2 + " is not in the range -59 to 59");
        }
        if (i3 < -59 || i3 > 59) {
            throw new C1640b("Zone offset seconds not in valid range: value " + i3 + " is not in the range -59 to 59");
        }
        if (Math.abs(i) == 18 && (i2 | i3) != 0) {
            throw new C1640b("Zone offset not in valid range: -18:00 to +18:00");
        }
        return m626W((i2 * 60) + (i * 3600) + i3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: W */
    public static ZoneOffset m626W(int i) {
        if (i < -64800 || i > 64800) {
            throw new C1640b("Zone offset not in valid range: -18:00 to +18:00");
        }
        if (i % 900 == 0) {
            Integer numValueOf = Integer.valueOf(i);
            ConcurrentHashMap concurrentHashMap = f456d;
            ZoneOffset zoneOffset = (ZoneOffset) concurrentHashMap.get(numValueOf);
            if (zoneOffset != null) {
                return zoneOffset;
            }
            concurrentHashMap.putIfAbsent(numValueOf, new ZoneOffset(i));
            ZoneOffset zoneOffset2 = (ZoneOffset) concurrentHashMap.get(numValueOf);
            f457e.putIfAbsent(zoneOffset2.f461c, zoneOffset2);
            return zoneOffset2;
        }
        return new ZoneOffset(i);
    }

    public ZoneOffset(int i) {
        String string;
        this.f460b = i;
        if (i == 0) {
            string = "Z";
        } else {
            int iAbs = Math.abs(i);
            StringBuilder sb = new StringBuilder();
            int i2 = iAbs / 3600;
            int i3 = (iAbs / 60) % 60;
            sb.append(i < 0 ? "-" : "+");
            sb.append(i2 < 10 ? MVEL.VERSION_SUB : "");
            sb.append(i2);
            sb.append(i3 < 10 ? ":0" : ":");
            sb.append(i3);
            int i4 = iAbs % 60;
            if (i4 != 0) {
                sb.append(i4 < 10 ? ":0" : ":");
                sb.append(i4);
            }
            string = sb.toString();
        }
        this.f461c = string;
    }

    public int getTotalSeconds() {
        return this.f460b;
    }

    @Override // p017j$.time.ZoneId
    public final String getId() {
        return this.f461c;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return interfaceC1744r instanceof EnumC1727a ? interfaceC1744r == EnumC1727a.OFFSET_SECONDS : interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m816d(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.OFFSET_SECONDS) {
            return this.f460b;
        }
        if (interfaceC1744r instanceof EnumC1727a) {
            throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
        return AbstractC1745s.m816d(this, interfaceC1744r).m820a(mo542D(interfaceC1744r), interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.OFFSET_SECONDS) {
            return this.f460b;
        }
        if (interfaceC1744r instanceof EnumC1727a) {
            throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
        return interfaceC1744r.mo806t(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        return (c1678e == AbstractC1745s.f693d || c1678e == AbstractC1745s.f694e) ? this : AbstractC1745s.m815c(this, c1678e);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return interfaceC1739m.mo556c(this.f460b, EnumC1727a.OFFSET_SECONDS);
    }

    @Override // p017j$.time.ZoneId
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ZoneOffset) && this.f460b == ((ZoneOffset) obj).f460b;
    }

    @Override // p017j$.time.ZoneId
    public final int hashCode() {
        return this.f460b;
    }

    @Override // p017j$.time.ZoneId
    public final String toString() {
        return this.f461c;
    }

    private Object writeReplace() {
        return new C1722p((byte) 8, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    @Override // p017j$.time.ZoneId
    /* renamed from: T */
    public final void mo623T(DataOutput dataOutput) throws IOException {
        dataOutput.writeByte(8);
        m629Z(dataOutput);
    }

    /* renamed from: Z */
    public final void m629Z(DataOutput dataOutput) throws IOException {
        int i = this.f460b;
        int i2 = i % 900 == 0 ? i / 900 : Opcodes.LAND;
        dataOutput.writeByte(i2);
        if (i2 == 127) {
            dataOutput.writeInt(i);
        }
    }

    /* renamed from: Y */
    public static ZoneOffset m628Y(DataInput dataInput) throws IOException {
        byte b = dataInput.readByte();
        return b == 127 ? m626W(dataInput.readInt()) : m626W(b * 900);
    }
}
