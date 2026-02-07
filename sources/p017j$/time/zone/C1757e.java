package p017j$.time.zone;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p017j$.time.C1715i;
import p017j$.time.DayOfWeek;
import p017j$.time.EnumC1717k;
import p017j$.time.ZoneOffset;
import p017j$.time.temporal.EnumC1727a;
import p017j$.util.Objects;

/* renamed from: j$.time.zone.e */
/* loaded from: classes2.dex */
public final class C1757e implements Serializable {
    private static final long serialVersionUID = 6889046316657758795L;

    /* renamed from: a */
    public final EnumC1717k f743a;

    /* renamed from: b */
    public final byte f744b;

    /* renamed from: c */
    public final DayOfWeek f745c;

    /* renamed from: d */
    public final C1715i f746d;

    /* renamed from: e */
    public final boolean f747e;

    /* renamed from: f */
    public final EnumC1756d f748f;

    /* renamed from: g */
    public final ZoneOffset f749g;

    /* renamed from: h */
    public final ZoneOffset f750h;

    /* renamed from: i */
    public final ZoneOffset f751i;

    public C1757e(EnumC1717k enumC1717k, int i, DayOfWeek dayOfWeek, C1715i c1715i, boolean z, EnumC1756d enumC1756d, ZoneOffset zoneOffset, ZoneOffset zoneOffset2, ZoneOffset zoneOffset3) {
        this.f743a = enumC1717k;
        this.f744b = (byte) i;
        this.f745c = dayOfWeek;
        this.f746d = c1715i;
        this.f747e = z;
        this.f748f = enumC1756d;
        this.f749g = zoneOffset;
        this.f750h = zoneOffset2;
        this.f751i = zoneOffset3;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C1753a((byte) 3, this);
    }

    /* renamed from: b */
    public final void m849b(DataOutput dataOutput) {
        byte b;
        int iM782d0 = this.f747e ? 86400 : this.f746d.m782d0();
        int totalSeconds = this.f749g.getTotalSeconds();
        int totalSeconds2 = this.f750h.getTotalSeconds() - totalSeconds;
        int totalSeconds3 = this.f751i.getTotalSeconds() - totalSeconds;
        if (iM782d0 % 3600 == 0) {
            b = this.f747e ? (byte) 24 : this.f746d.f644a;
        } else {
            b = 31;
        }
        int i = totalSeconds % 900 == 0 ? (totalSeconds / 900) + 128 : 255;
        int i2 = (totalSeconds2 == 0 || totalSeconds2 == 1800 || totalSeconds2 == 3600) ? totalSeconds2 / 1800 : 3;
        int i3 = (totalSeconds3 == 0 || totalSeconds3 == 1800 || totalSeconds3 == 3600) ? totalSeconds3 / 1800 : 3;
        DayOfWeek dayOfWeek = this.f745c;
        dataOutput.writeInt((this.f743a.getValue() << 28) + ((this.f744b + 32) << 22) + ((dayOfWeek == null ? 0 : dayOfWeek.getValue()) << 19) + (b << 14) + (this.f748f.ordinal() << 12) + (i << 4) + (i2 << 2) + i3);
        if (b == 31) {
            dataOutput.writeInt(iM782d0);
        }
        if (i == 255) {
            dataOutput.writeInt(totalSeconds);
        }
        if (i2 == 3) {
            dataOutput.writeInt(this.f750h.getTotalSeconds());
        }
        if (i3 == 3) {
            dataOutput.writeInt(this.f751i.getTotalSeconds());
        }
    }

    /* renamed from: a */
    public static C1757e m848a(DataInput dataInput) {
        EnumC1756d enumC1756d;
        C1715i c1715iM769R;
        int i = dataInput.readInt();
        EnumC1717k enumC1717kM786T = EnumC1717k.m786T(i >>> 28);
        int i2 = ((264241152 & i) >>> 22) - 32;
        int i3 = (3670016 & i) >>> 19;
        DayOfWeek dayOfWeekM541Q = i3 == 0 ? null : DayOfWeek.m541Q(i3);
        int i4 = (507904 & i) >>> 14;
        EnumC1756d enumC1756d2 = EnumC1756d.values()[(i & 12288) >>> 12];
        int i5 = (i & 4080) >>> 4;
        int i6 = (i & 12) >>> 2;
        int i7 = i & 3;
        if (i4 == 31) {
            long j = dataInput.readInt();
            C1715i c1715i = C1715i.f640e;
            EnumC1727a.SECOND_OF_DAY.m800D(j);
            int i8 = (int) (j / 3600);
            enumC1756d = enumC1756d2;
            long j2 = j - (i8 * 3600);
            c1715iM769R = C1715i.m769R(i8, (int) (j2 / 60), (int) (j2 - (r8 * 60)), 0);
        } else {
            enumC1756d = enumC1756d2;
            int i9 = i4 % 24;
            C1715i c1715i2 = C1715i.f640e;
            EnumC1727a.HOUR_OF_DAY.m800D(i9);
            c1715iM769R = C1715i.f643h[i9];
        }
        ZoneOffset zoneOffsetM626W = ZoneOffset.m626W(i5 == 255 ? dataInput.readInt() : (i5 - 128) * 900);
        ZoneOffset zoneOffsetM626W2 = ZoneOffset.m626W(i6 == 3 ? dataInput.readInt() : (i6 * 1800) + zoneOffsetM626W.getTotalSeconds());
        ZoneOffset zoneOffsetM626W3 = ZoneOffset.m626W(i7 == 3 ? dataInput.readInt() : (i7 * 1800) + zoneOffsetM626W.getTotalSeconds());
        boolean z = i4 == 24;
        Objects.requireNonNull(enumC1717kM786T, "month");
        Objects.requireNonNull(c1715iM769R, "time");
        Objects.requireNonNull(enumC1756d, "timeDefnition");
        Objects.requireNonNull(zoneOffsetM626W, "standardOffset");
        Objects.requireNonNull(zoneOffsetM626W2, "offsetBefore");
        Objects.requireNonNull(zoneOffsetM626W3, "offsetAfter");
        if (i2 < -28 || i2 > 31 || i2 == 0) {
            throw new IllegalArgumentException("Day of month indicator must be between -28 and 31 inclusive excluding zero");
        }
        if (z && !c1715iM769R.equals(C1715i.f642g)) {
            throw new IllegalArgumentException("Time must be midnight when end of day flag is true");
        }
        if (c1715iM769R.f647d != 0) {
            throw new IllegalArgumentException("Time's nano-of-second must be zero");
        }
        return new C1757e(enumC1717kM786T, i2, dayOfWeekM541Q, c1715iM769R, z, enumC1756d, zoneOffsetM626W, zoneOffsetM626W2, zoneOffsetM626W3);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C1757e) {
            C1757e c1757e = (C1757e) obj;
            if (this.f743a == c1757e.f743a && this.f744b == c1757e.f744b && this.f745c == c1757e.f745c && this.f748f == c1757e.f748f && this.f746d.equals(c1757e.f746d) && this.f747e == c1757e.f747e && this.f749g.equals(c1757e.f749g) && this.f750h.equals(c1757e.f750h) && this.f751i.equals(c1757e.f751i)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iM782d0 = ((this.f746d.m782d0() + (this.f747e ? 1 : 0)) << 15) + (this.f743a.ordinal() << 11) + ((this.f744b + 32) << 5);
        DayOfWeek dayOfWeek = this.f745c;
        return ((this.f749g.f460b ^ (this.f748f.ordinal() + (iM782d0 + ((dayOfWeek == null ? 7 : dayOfWeek.ordinal()) << 2)))) ^ this.f750h.f460b) ^ this.f751i.f460b;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TransitionRule[");
        sb.append(this.f751i.f460b - this.f750h.f460b > 0 ? "Gap " : "Overlap ");
        sb.append(this.f750h);
        sb.append(" to ");
        sb.append(this.f751i);
        sb.append(", ");
        DayOfWeek dayOfWeek = this.f745c;
        if (dayOfWeek != null) {
            byte b = this.f744b;
            if (b == -1) {
                sb.append(dayOfWeek.name());
                sb.append(" on or before last day of ");
                sb.append(this.f743a.name());
            } else if (b < 0) {
                sb.append(dayOfWeek.name());
                sb.append(" on or before last day minus ");
                sb.append((-this.f744b) - 1);
                sb.append(" of ");
                sb.append(this.f743a.name());
            } else {
                sb.append(dayOfWeek.name());
                sb.append(" on or after ");
                sb.append(this.f743a.name());
                sb.append(' ');
                sb.append((int) this.f744b);
            }
        } else {
            sb.append(this.f743a.name());
            sb.append(' ');
            sb.append((int) this.f744b);
        }
        sb.append(" at ");
        sb.append(this.f747e ? "24:00" : this.f746d.toString());
        sb.append(" ");
        sb.append(this.f748f);
        sb.append(", standard offset ");
        sb.append(this.f749g);
        sb.append(']');
        return sb.toString();
    }
}
