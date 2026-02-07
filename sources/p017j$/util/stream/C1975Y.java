package p017j$.util.stream;

import java.util.function.IntConsumer;
import p017j$.util.C1767G;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.Y */
/* loaded from: classes2.dex */
public final class C1975Y extends AbstractC2030h2 {

    /* renamed from: b */
    public boolean f1145b;

    /* renamed from: c */
    public final C1767G f1146c;

    /* renamed from: d */
    public final /* synthetic */ C1965W f1147d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1975Y(C1965W c1965w, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1147d = c1965w;
        InterfaceC2062n2 interfaceC2062n22 = this.f1255a;
        Objects.requireNonNull(interfaceC2062n22);
        this.f1146c = new C1767G(interfaceC2062n22, 1);
    }

    @Override // p017j$.util.stream.AbstractC2030h2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1255a.mo931h(-1L);
    }

    @Override // p017j$.util.stream.InterfaceC2052l2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        IntStream intStream = (IntStream) ((C1911L) this.f1147d.f1136t).apply(i);
        if (intStream != null) {
            try {
                boolean z = this.f1145b;
                C1767G c1767g = this.f1146c;
                if (!z) {
                    intStream.sequential().forEach(c1767g);
                } else {
                    Spliterator.OfInt ofIntSpliterator = intStream.sequential().spliterator();
                    while (!this.f1255a.mo932m() && ofIntSpliterator.tryAdvance((IntConsumer) c1767g)) {
                    }
                }
            } catch (Throwable th) {
                try {
                    intStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (intStream != null) {
            intStream.close();
        }
    }

    @Override // p017j$.util.stream.AbstractC2030h2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        this.f1145b = true;
        return this.f1255a.mo932m();
    }
}
