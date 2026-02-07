package p017j$.util.stream;

/* renamed from: j$.util.stream.v2 */
/* loaded from: classes2.dex */
public final class C2102v2 extends AbstractC2024g2 {

    /* renamed from: b */
    public long f1356b;

    /* renamed from: c */
    public long f1357c;

    /* renamed from: d */
    public final /* synthetic */ C2107w2 f1358d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2102v2(C2107w2 c2107w2, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1358d = c2107w2;
        this.f1356b = c2107w2.f1368s;
        long j = c2107w2.f1369t;
        this.f1357c = j < 0 ? Long.MAX_VALUE : j;
    }

    @Override // p017j$.util.stream.AbstractC2024g2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1249a.mo931h(AbstractC2122z2.m1110a(j, this.f1358d.f1368s, this.f1357c));
    }

    @Override // p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        long j = this.f1356b;
        if (j == 0) {
            long j2 = this.f1357c;
            if (j2 > 0) {
                this.f1357c = j2 - 1;
                this.f1249a.accept(d);
                return;
            }
            return;
        }
        this.f1356b = j - 1;
    }

    @Override // p017j$.util.stream.AbstractC2024g2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        return this.f1357c == 0 || this.f1249a.mo932m();
    }
}
