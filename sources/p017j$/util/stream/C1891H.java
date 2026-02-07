package p017j$.util.stream;

import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.C1762B;

/* renamed from: j$.util.stream.H */
/* loaded from: classes2.dex */
public final class C1891H extends AbstractC1901J implements InterfaceC2057m2 {

    /* renamed from: c */
    public static final C1876E f1011c;

    /* renamed from: d */
    public static final C1876E f1012d;

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // p017j$.util.stream.AbstractC1901J, p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        m971v(Long.valueOf(j));
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        if (this.f1036a) {
            return new C1762B(((Long) this.f1037b).longValue());
        }
        return null;
    }

    static {
        EnumC2001c3 enumC2001c3 = EnumC2001c3.LONG_VALUE;
        C2044k c2044k = new C2044k(21);
        C2044k c2044k2 = new C2044k(22);
        C1762B c1762b = C1762B.f760c;
        f1011c = new C1876E(true, enumC2001c3, c1762b, c2044k, c2044k2);
        f1012d = new C1876E(false, enumC2001c3, c1762b, new C2044k(21), new C2044k(22));
    }
}
