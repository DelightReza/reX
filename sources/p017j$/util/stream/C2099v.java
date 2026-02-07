package p017j$.util.stream;

import java.util.function.DoubleConsumer;
import p017j$.time.C1726t;
import p017j$.util.C1763C;
import p017j$.util.InterfaceC1779T;
import p017j$.util.Objects;

/* renamed from: j$.util.stream.v */
/* loaded from: classes2.dex */
public final class C2099v extends AbstractC2024g2 {

    /* renamed from: b */
    public boolean f1349b;

    /* renamed from: c */
    public final C1763C f1350c;

    /* renamed from: d */
    public final /* synthetic */ C2104w f1351d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2099v(C2104w c2104w, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1351d = c2104w;
        InterfaceC2062n2 interfaceC2062n22 = this.f1249a;
        Objects.requireNonNull(interfaceC2062n22);
        this.f1350c = new C1763C(interfaceC2062n22, 1);
    }

    @Override // p017j$.util.stream.AbstractC2024g2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1249a.mo931h(-1L);
    }

    @Override // p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        InterfaceC1871D interfaceC1871D = (InterfaceC1871D) ((C1726t) this.f1351d.f1360t).apply(d);
        if (interfaceC1871D != null) {
            try {
                boolean z = this.f1349b;
                C1763C c1763c = this.f1350c;
                if (!z) {
                    interfaceC1871D.sequential().forEach(c1763c);
                } else {
                    InterfaceC1779T interfaceC1779TSpliterator = interfaceC1871D.sequential().spliterator();
                    while (!this.f1249a.mo932m() && interfaceC1779TSpliterator.tryAdvance((DoubleConsumer) c1763c)) {
                    }
                }
            } catch (Throwable th) {
                try {
                    interfaceC1871D.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (interfaceC1871D != null) {
            interfaceC1871D.close();
        }
    }

    @Override // p017j$.util.stream.AbstractC2024g2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        this.f1349b = true;
        return this.f1249a.mo932m();
    }
}
