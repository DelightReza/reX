package p017j$.util.stream;

import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;

/* renamed from: j$.util.stream.d0 */
/* loaded from: classes2.dex */
public final class C2004d0 extends AbstractC2036i2 {

    /* renamed from: b */
    public final /* synthetic */ int f1224b;

    /* renamed from: c */
    public final /* synthetic */ AbstractC1985a f1225c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C2004d0(AbstractC1985a abstractC1985a, InterfaceC2062n2 interfaceC2062n2, int i) {
        super(interfaceC2062n2);
        this.f1224b = i;
        this.f1225c = abstractC1985a;
    }

    @Override // p017j$.util.stream.AbstractC2036i2, p017j$.util.stream.InterfaceC2062n2
    /* renamed from: h */
    public void mo931h(long j) {
        switch (this.f1224b) {
            case 4:
                this.f1262a.mo931h(-1L);
                break;
            default:
                super.mo931h(j);
                break;
        }
    }

    @Override // p017j$.util.stream.InterfaceC2057m2, p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        switch (this.f1224b) {
            case 0:
                this.f1262a.accept((InterfaceC2062n2) ((LongFunction) ((C2079r) this.f1225c).f1324t).apply(j));
                return;
            case 1:
                ((C2094u) this.f1225c).getClass();
                LongUnaryOperator longUnaryOperator = null;
                longUnaryOperator.applyAsLong(j);
                throw null;
            case 2:
                ((C2089t) this.f1225c).getClass();
                LongToIntFunction longToIntFunction = null;
                longToIntFunction.applyAsInt(j);
                throw null;
            case 3:
                ((C2084s) this.f1225c).getClass();
                LongToDoubleFunction longToDoubleFunction = null;
                longToDoubleFunction.applyAsDouble(j);
                throw null;
            case 4:
                ((C2094u) this.f1225c).getClass();
                LongPredicate longPredicate = null;
                longPredicate.test(j);
                throw null;
            default:
                ((LongConsumer) ((C2022g0) this.f1225c).f1248t).accept(j);
                this.f1262a.accept(j);
                return;
        }
    }
}
