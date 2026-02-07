package p017j$.util.stream;

import java.util.function.IntConsumer;

/* renamed from: j$.util.stream.W */
/* loaded from: classes2.dex */
public final class C1965W extends AbstractC1992b0 {

    /* renamed from: s */
    public final /* synthetic */ int f1135s;

    /* renamed from: t */
    public final /* synthetic */ Object f1136t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1965W(AbstractC1985a abstractC1985a, int i, Object obj, int i2) {
        super(abstractC1985a, i);
        this.f1135s = i2;
        this.f1136t = obj;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        switch (this.f1135s) {
            case 0:
                return new C1960V(this, interfaceC2062n2, 1);
            case 1:
                return new C1960V(this, interfaceC2062n2, 2);
            case 2:
                return new C1975Y(this, interfaceC2062n2);
            case 3:
                return new C1960V(this, interfaceC2062n2, 5);
            case 4:
                return new C2059n(this, interfaceC2062n2, 4);
            default:
                return new C1994b2(this, interfaceC2062n2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1965W(AbstractC1998c0 abstractC1998c0, IntConsumer intConsumer) {
        super(abstractC1998c0, 0);
        this.f1135s = 0;
        this.f1136t = intConsumer;
    }
}
