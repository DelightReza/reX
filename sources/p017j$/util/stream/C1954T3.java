package p017j$.util.stream;

import java.util.function.LongPredicate;

/* renamed from: j$.util.stream.T3 */
/* loaded from: classes2.dex */
public final class C1954T3 extends AbstractC2036i2 {

    /* renamed from: b */
    public final boolean f1129b;

    public C1954T3(C1904J2 c1904j2, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1129b = true;
    }

    @Override // p017j$.util.stream.AbstractC2036i2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1262a.mo931h(-1L);
    }

    @Override // p017j$.util.stream.InterfaceC2057m2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        if (this.f1129b) {
            LongPredicate longPredicate = null;
            longPredicate.test(j);
            throw null;
        }
    }

    @Override // p017j$.util.stream.AbstractC2036i2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        return !this.f1129b || this.f1262a.mo932m();
    }
}
