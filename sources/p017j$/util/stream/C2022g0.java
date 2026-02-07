package p017j$.util.stream;

import java.util.function.LongConsumer;

/* renamed from: j$.util.stream.g0 */
/* loaded from: classes2.dex */
public final class C2022g0 extends AbstractC2040j0 {

    /* renamed from: s */
    public final /* synthetic */ int f1247s;

    /* renamed from: t */
    public final /* synthetic */ Object f1248t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C2022g0(AbstractC1985a abstractC1985a, int i, Object obj, int i2) {
        super(abstractC1985a, i);
        this.f1247s = i2;
        this.f1248t = obj;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        switch (this.f1247s) {
            case 0:
                return new C2016f0(this, interfaceC2062n2);
            case 1:
                return new C2004d0(this, interfaceC2062n2, 5);
            case 2:
                return new C1982Z1(this, interfaceC2062n2);
            default:
                return new C2059n(this, interfaceC2062n2, 5);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2022g0(AbstractC2045k0 abstractC2045k0, LongConsumer longConsumer) {
        super(abstractC2045k0, 0);
        this.f1247s = 1;
        this.f1248t = longConsumer;
    }
}
