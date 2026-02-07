package p017j$.util.stream;

import p017j$.util.Optional;

/* renamed from: j$.util.stream.I */
/* loaded from: classes2.dex */
public final class C1896I extends AbstractC1901J {

    /* renamed from: c */
    public static final C1876E f1021c;

    /* renamed from: d */
    public static final C1876E f1022d;

    @Override // java.util.function.Supplier
    public final Object get() {
        if (this.f1036a) {
            return Optional.m856of(this.f1037b);
        }
        return null;
    }

    static {
        EnumC2001c3 enumC2001c3 = EnumC2001c3.REFERENCE;
        f1021c = new C1876E(true, enumC2001c3, Optional.empty(), new C2044k(23), new C2044k(24));
        f1022d = new C1876E(false, enumC2001c3, Optional.empty(), new C2044k(23), new C2044k(24));
    }
}
