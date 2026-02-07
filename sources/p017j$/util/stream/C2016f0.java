package p017j$.util.stream;

import java.util.function.LongConsumer;
import p017j$.time.C1726t;
import p017j$.util.C1771K;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Objects;

/* renamed from: j$.util.stream.f0 */
/* loaded from: classes2.dex */
public final class C2016f0 extends AbstractC2036i2 {

    /* renamed from: b */
    public boolean f1240b;

    /* renamed from: c */
    public final C1771K f1241c;

    /* renamed from: d */
    public final /* synthetic */ C2022g0 f1242d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2016f0(C2022g0 c2022g0, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1242d = c2022g0;
        InterfaceC2062n2 interfaceC2062n22 = this.f1262a;
        Objects.requireNonNull(interfaceC2062n22);
        this.f1241c = new C1771K(interfaceC2062n22, 1);
    }

    @Override // p017j$.util.stream.AbstractC2036i2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        this.f1262a.mo931h(-1L);
    }

    @Override // p017j$.util.stream.InterfaceC2057m2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        LongStream longStream = (LongStream) ((C1726t) this.f1242d.f1248t).apply(j);
        if (longStream != null) {
            try {
                boolean z = this.f1240b;
                C1771K c1771k = this.f1241c;
                if (!z) {
                    longStream.sequential().forEach(c1771k);
                } else {
                    InterfaceC1784Y interfaceC1784YSpliterator = longStream.sequential().spliterator();
                    while (!this.f1262a.mo932m() && interfaceC1784YSpliterator.tryAdvance((LongConsumer) c1771k)) {
                    }
                }
            } catch (Throwable th) {
                try {
                    longStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        }
        if (longStream != null) {
            longStream.close();
        }
    }

    @Override // p017j$.util.stream.AbstractC2036i2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        this.f1240b = true;
        return this.f1262a.mo932m();
    }
}
