package p017j$.util.stream;

import java.util.function.DoubleConsumer;

/* renamed from: j$.util.stream.w */
/* loaded from: classes2.dex */
public final class C2104w extends AbstractC2119z {

    /* renamed from: s */
    public final /* synthetic */ int f1359s;

    /* renamed from: t */
    public final /* synthetic */ Object f1360t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C2104w(AbstractC1985a abstractC1985a, int i, Object obj, int i2) {
        super(abstractC1985a, i);
        this.f1359s = i2;
        this.f1360t = obj;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2104w(AbstractC1856A abstractC1856A, DoubleConsumer doubleConsumer) {
        super(abstractC1856A, 0);
        this.f1359s = 1;
        this.f1360t = doubleConsumer;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        switch (this.f1359s) {
            case 0:
                return new C2099v(this, interfaceC2062n2);
            case 1:
                return new C2074q(this, interfaceC2062n2, 5);
            case 2:
                return new C2059n(this, interfaceC2062n2, 6);
            default:
                return new C1982Z1(this, interfaceC2062n2);
        }
    }
}
