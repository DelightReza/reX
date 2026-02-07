package p017j$.util.stream;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;

/* renamed from: j$.util.stream.q */
/* loaded from: classes2.dex */
public final class C2074q extends AbstractC2024g2 {

    /* renamed from: b */
    public final /* synthetic */ int f1317b;

    /* renamed from: c */
    public final /* synthetic */ AbstractC1985a f1318c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C2074q(AbstractC1985a abstractC1985a, InterfaceC2062n2 interfaceC2062n2, int i) {
        super(interfaceC2062n2);
        this.f1317b = i;
        this.f1318c = abstractC1985a;
    }

    @Override // p017j$.util.stream.AbstractC2024g2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public void mo931h(long j) {
        switch (this.f1317b) {
            case 4:
                this.f1249a.mo931h(-1L);
                break;
            default:
                super.mo931h(j);
                break;
        }
    }

    @Override // p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        switch (this.f1317b) {
            case 0:
                this.f1249a.accept((InterfaceC2062n2) ((DoubleFunction) ((C2079r) this.f1318c).f1324t).apply(d));
                return;
            case 1:
                ((C2084s) this.f1318c).getClass();
                DoubleUnaryOperator doubleUnaryOperator = null;
                doubleUnaryOperator.applyAsDouble(d);
                throw null;
            case 2:
                ((C2089t) this.f1318c).getClass();
                DoubleToIntFunction doubleToIntFunction = null;
                doubleToIntFunction.applyAsInt(d);
                throw null;
            case 3:
                ((C2094u) this.f1318c).getClass();
                DoubleToLongFunction doubleToLongFunction = null;
                doubleToLongFunction.applyAsLong(d);
                throw null;
            case 4:
                ((C2084s) this.f1318c).getClass();
                DoublePredicate doublePredicate = null;
                doublePredicate.test(d);
                throw null;
            default:
                ((DoubleConsumer) ((C2104w) this.f1318c).f1360t).accept(d);
                this.f1249a.accept(d);
                return;
        }
    }
}
