package p017j$.util.stream;

import java.util.function.IntConsumer;
import p017j$.util.OptionalInt;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.G */
/* loaded from: classes2.dex */
public final class C1886G extends AbstractC1901J implements InterfaceC2052l2 {

    /* renamed from: c */
    public static final C1876E f1005c;

    /* renamed from: d */
    public static final C1876E f1006d;

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // p017j$.util.stream.AbstractC1901J, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        m971v(Integer.valueOf(i));
    }

    @Override // java.util.function.Supplier
    public final Object get() {
        if (this.f1036a) {
            return new OptionalInt(((Integer) this.f1037b).intValue());
        }
        return null;
    }

    static {
        EnumC2001c3 enumC2001c3 = EnumC2001c3.INT_VALUE;
        C2044k c2044k = new C2044k(19);
        C2044k c2044k2 = new C2044k(20);
        OptionalInt optionalInt = OptionalInt.f779c;
        f1005c = new C1876E(true, enumC2001c3, optionalInt, c2044k, c2044k2);
        f1006d = new C1876E(false, enumC2001c3, optionalInt, new C2044k(19), new C2044k(20));
    }
}
