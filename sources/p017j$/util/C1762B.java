package p017j$.util;

/* renamed from: j$.util.B */
/* loaded from: classes2.dex */
public final class C1762B {

    /* renamed from: c */
    public static final C1762B f760c = new C1762B();

    /* renamed from: a */
    public final boolean f761a;

    /* renamed from: b */
    public final long f762b;

    public C1762B() {
        this.f761a = false;
        this.f762b = 0L;
    }

    public C1762B(long j) {
        this.f761a = true;
        this.f762b = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C1762B)) {
            return false;
        }
        C1762B c1762b = (C1762B) obj;
        boolean z = c1762b.f761a;
        boolean z2 = this.f761a;
        return (z2 && z) ? this.f762b == c1762b.f762b : z2 == z;
    }

    public final int hashCode() {
        if (!this.f761a) {
            return 0;
        }
        long j = this.f762b;
        return (int) (j ^ (j >>> 32));
    }

    public final String toString() {
        if (this.f761a) {
            return "OptionalLong[" + this.f762b + "]";
        }
        return "OptionalLong.empty";
    }
}
