package p017j$.util.stream;

import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.C1761A;

/* renamed from: j$.util.stream.F */
/* loaded from: classes2.dex */
public final class C1881F extends AbstractC1901J implements InterfaceC2047k2 {

    /* renamed from: c */
    public static final C1876E f1000c;

    /* renamed from: d */
    public static final C1876E f1001d;

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // p017j$.util.stream.AbstractC1901J, p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        m971v(Double.valueOf(d));
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        if (this.f1036a) {
            return new C1761A(((Double) this.f1037b).doubleValue());
        }
        return null;
    }

    static {
        EnumC2001c3 enumC2001c3 = EnumC2001c3.DOUBLE_VALUE;
        C2044k c2044k = new C2044k(17);
        C2044k c2044k2 = new C2044k(18);
        C1761A c1761a = C1761A.f757c;
        f1000c = new C1876E(true, enumC2001c3, c1761a, c2044k, c2044k2);
        f1001d = new C1876E(false, enumC2001c3, c1761a, new C2044k(17), new C2044k(18));
    }
}
