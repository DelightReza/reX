package p017j$.time.temporal;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import p017j$.time.C1640b;

/* renamed from: j$.time.temporal.v */
/* loaded from: classes2.dex */
public final class C1748v implements Serializable {
    private static final long serialVersionUID = -7317881728594519368L;

    /* renamed from: a */
    public final long f697a;

    /* renamed from: b */
    public final long f698b;

    /* renamed from: c */
    public final long f699c;

    /* renamed from: d */
    public final long f700d;

    /* renamed from: f */
    public static C1748v m818f(long j, long j2) {
        if (j > j2) {
            throw new IllegalArgumentException("Minimum value must be less than maximum value");
        }
        return new C1748v(j, j, j2, j2);
    }

    /* renamed from: g */
    public static C1748v m819g(long j, long j2, long j3) {
        if (j > 1) {
            throw new IllegalArgumentException("Smallest minimum value must be less than largest minimum value");
        }
        if (j2 > j3) {
            throw new IllegalArgumentException("Smallest maximum value must be less than largest maximum value");
        }
        if (1 > j3) {
            throw new IllegalArgumentException("Minimum value must be less than maximum value");
        }
        return new C1748v(j, 1L, j2, j3);
    }

    public C1748v(long j, long j2, long j3, long j4) {
        this.f697a = j;
        this.f698b = j2;
        this.f699c = j3;
        this.f700d = j4;
    }

    /* renamed from: d */
    public final boolean m823d() {
        return this.f697a >= -2147483648L && this.f700d <= 2147483647L;
    }

    /* renamed from: e */
    public final boolean m824e(long j) {
        return j >= this.f697a && j <= this.f700d;
    }

    /* renamed from: a */
    public final int m820a(long j, InterfaceC1744r interfaceC1744r) {
        if (m823d() && m824e(j)) {
            return (int) j;
        }
        throw new C1640b(m822c(j, interfaceC1744r));
    }

    /* renamed from: b */
    public final void m821b(long j, InterfaceC1744r interfaceC1744r) {
        if (!m824e(j)) {
            throw new C1640b(m822c(j, interfaceC1744r));
        }
    }

    /* renamed from: c */
    public final String m822c(long j, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r != null) {
            return "Invalid value for " + interfaceC1744r + " (valid values " + this + "): " + j;
        }
        return "Invalid value (valid values " + this + "): " + j;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        long j = this.f697a;
        long j2 = this.f698b;
        if (j > j2) {
            throw new InvalidObjectException("Smallest minimum value must be less than largest minimum value");
        }
        long j3 = this.f699c;
        long j4 = this.f700d;
        if (j3 > j4) {
            throw new InvalidObjectException("Smallest maximum value must be less than largest maximum value");
        }
        if (j2 > j4) {
            throw new InvalidObjectException("Minimum value must be less than maximum value");
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C1748v) {
            C1748v c1748v = (C1748v) obj;
            if (this.f697a == c1748v.f697a && this.f698b == c1748v.f698b && this.f699c == c1748v.f699c && this.f700d == c1748v.f700d) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        long j = this.f697a;
        long j2 = this.f698b;
        long j3 = j + (j2 << 16) + (j2 >> 48);
        long j4 = this.f699c;
        long j5 = j3 + (j4 << 32) + (j4 >> 32);
        long j6 = this.f700d;
        long j7 = j5 + (j6 << 48) + (j6 >> 16);
        return (int) (j7 ^ (j7 >>> 32));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.f697a);
        if (this.f697a != this.f698b) {
            sb.append('/');
            sb.append(this.f698b);
        }
        sb.append(" - ");
        sb.append(this.f699c);
        if (this.f699c != this.f700d) {
            sb.append('/');
            sb.append(this.f700d);
        }
        return sb.toString();
    }
}
