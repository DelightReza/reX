package p017j$.util.stream;

/* renamed from: j$.util.stream.t */
/* loaded from: classes2.dex */
public final class C2089t extends AbstractC1992b0 {

    /* renamed from: s */
    public final /* synthetic */ int f1340s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C2089t(AbstractC1985a abstractC1985a, int i, int i2) {
        super(abstractC1985a, i);
        this.f1340s = i2;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        switch (this.f1340s) {
            case 0:
                return new C2074q(this, interfaceC2062n2, 2);
            case 1:
                return interfaceC2062n2;
            default:
                return new C2004d0(this, interfaceC2062n2, 2);
        }
    }
}
