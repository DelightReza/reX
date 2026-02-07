package p017j$.util.stream;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* renamed from: j$.util.stream.n */
/* loaded from: classes2.dex */
public final class C2059n extends AbstractC2042j2 {

    /* renamed from: b */
    public final /* synthetic */ int f1295b;

    /* renamed from: c */
    public Object f1296c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C2059n(AbstractC1985a abstractC1985a, InterfaceC2062n2 interfaceC2062n2, int i) {
        super(interfaceC2062n2);
        this.f1295b = i;
        this.f1296c = abstractC1985a;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C2059n(InterfaceC2062n2 interfaceC2062n2) {
        super(interfaceC2062n2);
        this.f1295b = 0;
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    public void end() {
        switch (this.f1295b) {
            case 0:
                this.f1296c = null;
                this.f1274a.end();
                break;
            default:
                super.end();
                break;
        }
    }

    @Override // p017j$.util.stream.AbstractC2042j2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public void mo931h(long j) {
        switch (this.f1295b) {
            case 0:
                this.f1296c = new HashSet();
                this.f1274a.mo931h(-1L);
                break;
            case 1:
            default:
                super.mo931h(j);
                break;
            case 2:
                this.f1274a.mo931h(-1L);
                break;
        }
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.f1295b) {
            case 0:
                if (!((Set) this.f1296c).contains(obj)) {
                    ((Set) this.f1296c).add(obj);
                    this.f1274a.accept((InterfaceC2062n2) obj);
                    break;
                }
                break;
            case 1:
                ((Consumer) ((C2079r) this.f1296c).f1324t).accept(obj);
                this.f1274a.accept((InterfaceC2062n2) obj);
                break;
            case 2:
                if (((Predicate) ((C2079r) this.f1296c).f1324t).test(obj)) {
                    this.f1274a.accept((InterfaceC2062n2) obj);
                    break;
                }
                break;
            case 3:
                this.f1274a.accept((InterfaceC2062n2) ((Function) ((C2079r) this.f1296c).f1324t).apply(obj));
                break;
            case 4:
                this.f1274a.accept(((ToIntFunction) ((C1965W) this.f1296c).f1136t).applyAsInt(obj));
                break;
            case 5:
                this.f1274a.accept(((ToLongFunction) ((C2022g0) this.f1296c).f1248t).applyAsLong(obj));
                break;
            default:
                this.f1274a.accept(((ToDoubleFunction) ((C2104w) this.f1296c).f1360t).applyAsDouble(obj));
                break;
        }
    }
}
