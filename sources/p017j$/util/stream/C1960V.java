package p017j$.util.stream;

import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;

/* renamed from: j$.util.stream.V */
/* loaded from: classes2.dex */
public final class C1960V extends AbstractC2030h2 {

    /* renamed from: b */
    public final /* synthetic */ int f1131b;

    /* renamed from: c */
    public final /* synthetic */ AbstractC1985a f1132c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1960V(AbstractC1985a abstractC1985a, InterfaceC2062n2 interfaceC2062n2, int i) {
        super(interfaceC2062n2);
        this.f1131b = i;
        this.f1132c = abstractC1985a;
    }

    @Override // p017j$.util.stream.AbstractC2030h2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public void mo931h(long j) {
        switch (this.f1131b) {
            case 5:
                this.f1255a.mo931h(-1L);
                break;
            default:
                super.mo931h(j);
                break;
        }
    }

    @Override // p017j$.util.stream.InterfaceC2052l2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        switch (this.f1131b) {
            case 0:
                this.f1255a.accept((InterfaceC2062n2) ((IntFunction) ((C2079r) this.f1132c).f1324t).apply(i));
                return;
            case 1:
                ((IntConsumer) ((C1965W) this.f1132c).f1136t).accept(i);
                this.f1255a.accept(i);
                return;
            case 2:
                this.f1255a.accept(((IntUnaryOperator) ((C1965W) this.f1132c).f1136t).applyAsInt(i));
                return;
            case 3:
                ((C2094u) this.f1132c).getClass();
                IntToLongFunction intToLongFunction = null;
                intToLongFunction.applyAsLong(i);
                throw null;
            case 4:
                ((C2084s) this.f1132c).getClass();
                IntToDoubleFunction intToDoubleFunction = null;
                intToDoubleFunction.applyAsDouble(i);
                throw null;
            default:
                if (((IntPredicate) ((C1965W) this.f1132c).f1136t).test(i)) {
                    this.f1255a.accept(i);
                    return;
                }
                return;
        }
    }
}
