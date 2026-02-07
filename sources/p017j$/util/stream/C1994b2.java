package p017j$.util.stream;

import java.util.function.IntConsumer;
import p017j$.time.C1726t;
import p017j$.util.C1767G;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.b2 */
/* loaded from: classes2.dex */
public final class C1994b2 extends AbstractC2042j2 {

    /* renamed from: b */
    public boolean f1185b;

    /* renamed from: c */
    public final C1767G f1186c;

    /* renamed from: d */
    public final /* synthetic */ C1965W f1187d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1994b2(C1965W c1965w, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1187d = c1965w;
        InterfaceC2062n2 interfaceC2062n22 = this.f1274a;
        Objects.requireNonNull(interfaceC2062n22);
        this.f1186c = new C1767G(interfaceC2062n22, 1);
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1274a.mo931h(-1L);
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        IntStream intStream = (IntStream) ((C1726t) this.f1187d.f1136t).apply((C1726t) obj);
        if (intStream != null) {
            try {
                boolean z = this.f1185b;
                C1767G c1767g = this.f1186c;
                if (!z) {
                    intStream.sequential().forEach(c1767g);
                } else {
                    Spliterator.OfInt ofIntSpliterator = intStream.sequential().spliterator();
                    while (!this.f1274a.mo932m() && ofIntSpliterator.tryAdvance((IntConsumer) c1767g)) {
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

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        this.f1185b = true;
        return this.f1274a.mo932m();
    }
}
