package p017j$.util.stream;

/* renamed from: j$.util.stream.s */
/* loaded from: classes2.dex */
public final class C2084s extends AbstractC2119z {

    /* renamed from: s */
    public final /* synthetic */ int f1334s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C2084s(AbstractC1985a abstractC1985a, int i, int i2) {
        super(abstractC1985a, i);
        this.f1334s = i2;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        switch (this.f1334s) {
            case 0:
                return new C2074q(this, interfaceC2062n2, 1);
            case 1:
                return interfaceC2062n2;
            case 2:
                return new C2074q(this, interfaceC2062n2, 4);
            case 3:
                return new C1970X(1, interfaceC2062n2);
            case 4:
                return new C1960V(this, interfaceC2062n2, 4);
            case 5:
                return new C2010e0(interfaceC2062n2);
            default:
                return new C2004d0(this, interfaceC2062n2, 3);
        }
    }
}
