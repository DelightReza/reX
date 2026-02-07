package p017j$.util;

/* loaded from: classes2.dex */
public final class OptionalInt {

    /* renamed from: c */
    public static final OptionalInt f779c = new OptionalInt();

    /* renamed from: a */
    public final boolean f780a;

    /* renamed from: b */
    public final int f781b;

    public OptionalInt() {
        this.f780a = false;
        this.f781b = 0;
    }

    public OptionalInt(int i) {
        this.f780a = true;
        this.f781b = i;
    }

    public int orElse(int i) {
        return this.f780a ? this.f781b : i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof OptionalInt)) {
            return false;
        }
        OptionalInt optionalInt = (OptionalInt) obj;
        boolean z = optionalInt.f780a;
        boolean z2 = this.f780a;
        return (z2 && z) ? this.f781b == optionalInt.f781b : z2 == z;
    }

    public final int hashCode() {
        if (this.f780a) {
            return this.f781b;
        }
        return 0;
    }

    public final String toString() {
        if (this.f780a) {
            return "OptionalInt[" + this.f781b + "]";
        }
        return "OptionalInt.empty";
    }
}
