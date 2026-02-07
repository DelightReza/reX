package p017j$.util.stream;

import java.util.function.DoublePredicate;

/* renamed from: j$.util.stream.V3 */
/* loaded from: classes2.dex */
public final class C1964V3 extends AbstractC2024g2 {

    /* renamed from: b */
    public final boolean f1134b;

    public C1964V3(C1894H2 c1894h2, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1134b = true;
    }

    @Override // p017j$.util.stream.AbstractC2024g2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1249a.mo931h(-1L);
    }

    @Override // p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        if (this.f1134b) {
            DoublePredicate doublePredicate = null;
            doublePredicate.test(d);
            throw null;
        }
    }

    @Override // p017j$.util.stream.AbstractC2024g2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        return !this.f1134b || this.f1249a.mo932m();
    }
}
