package p017j$.util.stream;

/* renamed from: j$.util.stream.q2 */
/* loaded from: classes2.dex */
public final class C2077q2 extends AbstractC2030h2 {

    /* renamed from: b */
    public long f1320b;

    /* renamed from: c */
    public long f1321c;

    /* renamed from: d */
    public final /* synthetic */ C2082r2 f1322d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2077q2(C2082r2 c2082r2, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1322d = c2082r2;
        this.f1320b = c2082r2.f1332s;
        long j = c2082r2.f1333t;
        this.f1321c = j < 0 ? Long.MAX_VALUE : j;
    }

    @Override // p017j$.util.stream.AbstractC2030h2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1255a.mo931h(AbstractC2122z2.m1110a(j, this.f1322d.f1332s, this.f1321c));
    }

    @Override // p017j$.util.stream.InterfaceC2052l2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        long j = this.f1320b;
        if (j == 0) {
            long j2 = this.f1321c;
            if (j2 > 0) {
                this.f1321c = j2 - 1;
                this.f1255a.accept(i);
                return;
            }
            return;
        }
        this.f1320b = j - 1;
    }

    @Override // p017j$.util.stream.AbstractC2030h2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        return this.f1321c == 0 || this.f1255a.mo932m();
    }
}
