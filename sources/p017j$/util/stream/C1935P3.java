package p017j$.util.stream;

/* renamed from: j$.util.stream.P3 */
/* loaded from: classes2.dex */
public final class C1935P3 extends AbstractC2030h2 {

    /* renamed from: b */
    public boolean f1093b;

    /* renamed from: c */
    public final /* synthetic */ C1940Q3 f1094c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1935P3(C1940Q3 c1940q3, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1094c = c1940q3;
        this.f1093b = true;
    }

    @Override // p017j$.util.stream.AbstractC2030h2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1255a.mo931h(-1L);
    }

    @Override // p017j$.util.stream.InterfaceC2052l2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        if (this.f1093b) {
            boolean zTest = this.f1094c.f1105s.test(i);
            this.f1093b = zTest;
            if (zTest) {
                this.f1255a.accept(i);
            }
        }
    }

    @Override // p017j$.util.stream.AbstractC2030h2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        return !this.f1093b || this.f1255a.mo932m();
    }
}
