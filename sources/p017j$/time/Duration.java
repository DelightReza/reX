package p017j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.math.BigInteger;
import org.telegram.messenger.MediaDataController;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1743q;

/* loaded from: classes2.dex */
public final class Duration implements InterfaceC1743q, Comparable<Duration>, Serializable {

    /* renamed from: c */
    public static final Duration f430c = new Duration(0, 0);
    private static final long serialVersionUID = 3078945930695997490L;

    /* renamed from: a */
    public final long f431a;

    /* renamed from: b */
    public final int f432b;

    @Override // java.lang.Comparable
    public final int compareTo(Duration duration) {
        Duration duration2 = duration;
        int iCompare = Long.compare(this.f431a, duration2.f431a);
        return iCompare != 0 ? iCompare : this.f432b - duration2.f432b;
    }

    static {
        BigInteger.valueOf(1000000000L);
    }

    public static Duration ofMinutes(long j) {
        return m548j(AbstractC1636a.m500S(j, 60), 0);
    }

    public static Duration ofSeconds(long j) {
        return m548j(j, 0);
    }

    /* renamed from: k */
    public static Duration m549k(long j) {
        long j2 = j / 1000000000;
        int i = (int) (j % 1000000000);
        if (i < 0) {
            i = (int) (i + 1000000000);
            j2--;
        }
        return m548j(j2, i);
    }

    /* renamed from: j */
    public static Duration m548j(long j, int i) {
        if ((i | j) == 0) {
            return f430c;
        }
        return new Duration(j, i);
    }

    public Duration(long j, int i) {
        this.f431a = j;
        this.f432b = i;
    }

    @Override // p017j$.time.temporal.InterfaceC1743q
    /* renamed from: i */
    public final InterfaceC1739m mo550i(InterfaceC1739m interfaceC1739m) {
        long j = this.f431a;
        if (j != 0) {
            interfaceC1739m = interfaceC1739m.mo557d(j, EnumC1728b.SECONDS);
        }
        int i = this.f432b;
        return i != 0 ? interfaceC1739m.mo557d(i, EnumC1728b.NANOS) : interfaceC1739m;
    }

    public long toMillis() {
        long j = this.f431a;
        long j2 = this.f432b;
        if (j < 0) {
            j++;
            j2 -= 1000000000;
        }
        return AbstractC1636a.m494M(AbstractC1636a.m500S(j, MediaDataController.MAX_STYLE_RUNS_COUNT), j2 / 1000000);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Duration) {
            Duration duration = (Duration) obj;
            if (this.f431a == duration.f431a && this.f432b == duration.f432b) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        long j = this.f431a;
        return (this.f432b * 51) + ((int) (j ^ (j >>> 32)));
    }

    public final String toString() {
        if (this == f430c) {
            return "PT0S";
        }
        long j = this.f431a;
        if (j < 0 && this.f432b > 0) {
            j++;
        }
        long j2 = j / 3600;
        int i = (int) ((j % 3600) / 60);
        int i2 = (int) (j % 60);
        StringBuilder sb = new StringBuilder(24);
        sb.append("PT");
        if (j2 != 0) {
            sb.append(j2);
            sb.append('H');
        }
        if (i != 0) {
            sb.append(i);
            sb.append('M');
        }
        if (i2 == 0 && this.f432b == 0 && sb.length() > 2) {
            return sb.toString();
        }
        if (this.f431a < 0 && this.f432b > 0 && i2 == 0) {
            sb.append("-0");
        } else {
            sb.append(i2);
        }
        if (this.f432b > 0) {
            int length = sb.length();
            if (this.f431a < 0) {
                sb.append(2000000000 - this.f432b);
            } else {
                sb.append(this.f432b + 1000000000);
            }
            while (sb.charAt(sb.length() - 1) == '0') {
                sb.setLength(sb.length() - 1);
            }
            sb.setCharAt(length, '.');
        }
        sb.append('S');
        return sb.toString();
    }

    private Object writeReplace() {
        return new C1722p((byte) 1, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
