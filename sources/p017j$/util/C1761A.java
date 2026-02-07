package p017j$.util;

/* renamed from: j$.util.A */
/* loaded from: classes2.dex */
public final class C1761A {

    /* renamed from: c */
    public static final C1761A f757c = new C1761A();

    /* renamed from: a */
    public final boolean f758a;

    /* renamed from: b */
    public final double f759b;

    public C1761A() {
        this.f758a = false;
        this.f759b = Double.NaN;
    }

    public C1761A(double d) {
        this.f758a = true;
        this.f759b = d;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1761A)) {
            return false;
        }
        C1761A c1761a = (C1761A) obj;
        boolean z = c1761a.f758a;
        boolean z2 = this.f758a;
        return (z2 && z) ? Double.compare(this.f759b, c1761a.f759b) == 0 : z2 == z;
    }

    public final int hashCode() {
        if (!this.f758a) {
            return 0;
        }
        long jDoubleToLongBits = Double.doubleToLongBits(this.f759b);
        return (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
    }

    public final String toString() {
        if (this.f758a) {
            return "OptionalDouble[" + this.f759b + "]";
        }
        return "OptionalDouble.empty";
    }
}
