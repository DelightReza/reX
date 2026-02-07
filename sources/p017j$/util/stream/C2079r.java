package p017j$.util.stream;

import java.util.function.Consumer;

/* renamed from: j$.util.stream.r */
/* loaded from: classes2.dex */
public final class C2079r extends AbstractC2012e2 {

    /* renamed from: s */
    public final /* synthetic */ int f1323s;

    /* renamed from: t */
    public final /* synthetic */ Object f1324t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C2079r(AbstractC1985a abstractC1985a, int i, Object obj, int i2) {
        super(abstractC1985a, i);
        this.f1323s = i2;
        this.f1324t = obj;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        switch (this.f1323s) {
            case 0:
                return new C2074q(this, interfaceC2062n2, 0);
            case 1:
                return new C1960V(this, interfaceC2062n2, 0);
            case 2:
                return new C2004d0(this, interfaceC2062n2, 0);
            case 3:
                return new C2059n(this, interfaceC2062n2, 1);
            case 4:
                return new C2059n(this, interfaceC2062n2, 2);
            case 5:
                return new C2059n(this, interfaceC2062n2, 3);
            default:
                return new C2054m(this, interfaceC2062n2);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2079r(AbstractC2018f2 abstractC2018f2, Consumer consumer) {
        super(abstractC2018f2, 0);
        this.f1323s = 3;
        this.f1324t = consumer;
    }
}
