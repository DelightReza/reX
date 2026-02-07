package p017j$.util.stream;

/* renamed from: j$.util.stream.s2 */
/* loaded from: classes2.dex */
public final class C2087s2 extends AbstractC2036i2 {

    /* renamed from: b */
    public long f1337b;

    /* renamed from: c */
    public long f1338c;

    /* renamed from: d */
    public final /* synthetic */ C2092t2 f1339d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2087s2(C2092t2 c2092t2, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1339d = c2092t2;
        this.f1337b = c2092t2.f1344s;
        long j = c2092t2.f1345t;
        this.f1338c = j < 0 ? Long.MAX_VALUE : j;
    }

    @Override // p017j$.util.stream.AbstractC2036i2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1262a.mo931h(AbstractC2122z2.m1110a(j, this.f1339d.f1344s, this.f1338c));
    }

    @Override // p017j$.util.stream.InterfaceC2057m2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        long j2 = this.f1337b;
        if (j2 == 0) {
            long j3 = this.f1338c;
            if (j3 > 0) {
                this.f1338c = j3 - 1;
                this.f1262a.accept(j);
                return;
            }
            return;
        }
        this.f1337b = j2 - 1;
    }

    @Override // p017j$.util.stream.AbstractC2036i2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        return this.f1338c == 0 || this.f1262a.mo932m();
    }
}
