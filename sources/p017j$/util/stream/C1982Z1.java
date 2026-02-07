package p017j$.util.stream;

import java.util.function.DoubleConsumer;
import java.util.function.LongConsumer;
import p017j$.time.C1726t;
import p017j$.util.C1763C;
import p017j$.util.C1771K;
import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Objects;

/* renamed from: j$.util.stream.Z1 */
/* loaded from: classes2.dex */
public final class C1982Z1 extends AbstractC2042j2 {

    /* renamed from: b */
    public final /* synthetic */ int f1151b = 0;

    /* renamed from: c */
    public boolean f1152c;

    /* renamed from: d */
    public final Object f1153d;

    /* renamed from: e */
    public final /* synthetic */ AbstractC1985a f1154e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1982Z1(C2104w c2104w, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1154e = c2104w;
        InterfaceC2062n2 interfaceC2062n22 = this.f1274a;
        Objects.requireNonNull(interfaceC2062n22);
        this.f1153d = new C1763C(interfaceC2062n22, 1);
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        switch (this.f1151b) {
            case 0:
                this.f1274a.mo931h(-1L);
                break;
            default:
                this.f1274a.mo931h(-1L);
                break;
        }
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        switch (this.f1151b) {
            case 0:
                C1771K c1771k = (C1771K) this.f1153d;
                LongStream longStream = (LongStream) ((C1726t) ((C2022g0) this.f1154e).f1248t).apply((C1726t) obj);
                if (longStream != null) {
                    try {
                        if (!this.f1152c) {
                            longStream.sequential().forEach(c1771k);
                        } else {
                            InterfaceC1784Y interfaceC1784YSpliterator = longStream.sequential().spliterator();
                            while (!this.f1274a.mo932m() && interfaceC1784YSpliterator.tryAdvance((LongConsumer) c1771k)) {
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
                    return;
                }
                return;
            default:
                C1763C c1763c = (C1763C) this.f1153d;
                InterfaceC1871D interfaceC1871D = (InterfaceC1871D) ((C1726t) ((C2104w) this.f1154e).f1360t).apply((C1726t) obj);
                if (interfaceC1871D != null) {
                    try {
                        if (!this.f1152c) {
                            interfaceC1871D.sequential().forEach(c1763c);
                        } else {
                            InterfaceC1779T interfaceC1779TSpliterator = interfaceC1871D.sequential().spliterator();
                            while (!this.f1274a.mo932m() && interfaceC1779TSpliterator.tryAdvance((DoubleConsumer) c1763c)) {
                            }
                        }
                    } catch (Throwable th3) {
                        try {
                            interfaceC1871D.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                        throw th3;
                    }
                }
                if (interfaceC1871D != null) {
                    interfaceC1871D.close();
                    return;
                }
                return;
        }
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public final boolean mo932m() {
        switch (this.f1151b) {
            case 0:
                this.f1152c = true;
                break;
            default:
                this.f1152c = true;
                break;
        }
        return this.f1274a.mo932m();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1982Z1(C2022g0 c2022g0, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1154e = c2022g0;
        InterfaceC2062n2 interfaceC2062n22 = this.f1274a;
        Objects.requireNonNull(interfaceC2062n22);
        this.f1153d = new C1771K(interfaceC2062n22, 1);
    }
}
