package p017j$.util.stream;

import p017j$.time.C1726t;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.m */
/* loaded from: classes2.dex */
public final class C2054m extends AbstractC2042j2 {

    /* renamed from: b */
    public final /* synthetic */ int f1291b = 2;

    /* renamed from: c */
    public boolean f1292c;

    /* renamed from: d */
    public Object f1293d;

    public /* synthetic */ C2054m(InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2054m(C1925N3 c1925n3, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1293d = c1925n3;
        this.f1292c = true;
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public final void mo931h(long j) {
        switch (this.f1291b) {
            case 0:
                this.f1292c = false;
                this.f1293d = null;
                this.f1274a.mo931h(-1L);
                break;
            case 1:
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
        switch (this.f1291b) {
            case 0:
                InterfaceC2062n2 interfaceC2062n2 = this.f1274a;
                if (obj == null) {
                    if (this.f1292c) {
                        return;
                    }
                    this.f1292c = true;
                    this.f1293d = null;
                    interfaceC2062n2.m971v((InterfaceC2062n2) null);
                    return;
                }
                Object obj2 = this.f1293d;
                if (obj2 == null || !obj.equals(obj2)) {
                    this.f1293d = obj;
                    interfaceC2062n2.m971v((InterfaceC2062n2) obj);
                    return;
                }
                return;
            case 1:
                Stream stream = (Stream) ((C1726t) ((C2079r) this.f1293d).f1324t).apply((C1726t) obj);
                if (stream != null) {
                    try {
                        boolean z = this.f1292c;
                        InterfaceC2062n2 interfaceC2062n22 = this.f1274a;
                        if (!z) {
                            ((Stream) stream.sequential()).forEach(interfaceC2062n22);
                        } else {
                            Spliterator spliterator = ((Stream) stream.sequential()).spliterator();
                            while (!interfaceC2062n22.mo932m() && spliterator.tryAdvance(interfaceC2062n22)) {
                            }
                        }
                    } catch (Throwable th) {
                        try {
                            stream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                if (stream != null) {
                    stream.close();
                    return;
                }
                return;
            default:
                if (this.f1292c) {
                    boolean zTest = ((C1925N3) this.f1293d).f1076t.test(obj);
                    this.f1292c = zTest;
                    if (zTest) {
                        this.f1274a.m971v((InterfaceC2062n2) obj);
                        return;
                    }
                    return;
                }
                return;
        }
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: m */
    public boolean mo932m() {
        switch (this.f1291b) {
            case 1:
                this.f1292c = true;
                return this.f1274a.mo932m();
            case 2:
                return !this.f1292c || this.f1274a.mo932m();
            default:
                return super.mo932m();
        }
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    public void end() {
        switch (this.f1291b) {
            case 0:
                this.f1292c = false;
                this.f1293d = null;
                this.f1274a.end();
                break;
            default:
                super.end();
                break;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2054m(C2079r c2079r, InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1293d = c2079r;
    }
}
