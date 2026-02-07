package p017j$.util.stream;

/* renamed from: j$.util.stream.o2 */
/* loaded from: classes2.dex */
public final class C2067o2 extends AbstractC2042j2 {

    /* renamed from: b */
    public long f1305b;

    /* renamed from: c */
    public long f1306c;

    /* renamed from: d */
    public final /* synthetic */ C2072p2 f1307d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2067o2(C2072p2 c2072p2, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1307d = c2072p2;
        this.f1305b = c2072p2.f1313s;
        long j = c2072p2.f1314t;
        this.f1306c = j < 0 ? Long.MAX_VALUE : j;
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1274a.mo931h(AbstractC2122z2.m1110a(j, this.f1307d.f1313s, this.f1306c));
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        long j = this.f1305b;
        if (j == 0) {
            long j2 = this.f1306c;
            if (j2 > 0) {
                this.f1306c = j2 - 1;
                this.f1274a.m971v((InterfaceC2062n2) obj);
                return;
            }
            return;
        }
        this.f1305b = j - 1;
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        return this.f1306c == 0 || this.f1274a.mo932m();
    }
}
