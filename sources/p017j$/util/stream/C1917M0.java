package p017j$.util.stream;

import java.util.concurrent.CountedCompleter;
import java.util.function.BinaryOperator;
import java.util.function.LongFunction;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.M0 */
/* loaded from: classes2.dex */
public class C1917M0 extends AbstractC2003d {

    /* renamed from: h */
    public final AbstractC2106w1 f1061h;

    /* renamed from: i */
    public final LongFunction f1062i;

    /* renamed from: j */
    public final BinaryOperator f1063j;

    @Override // p017j$.util.stream.AbstractC2003d, java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        AbstractC2003d abstractC2003d = this.f1221d;
        if (abstractC2003d != null) {
            this.f1223f = (InterfaceC1887G0) this.f1063j.apply((InterfaceC1887G0) ((C1917M0) abstractC2003d).f1223f, (InterfaceC1887G0) ((C1917M0) this.f1222e).f1223f);
        }
        super.onCompletion(countedCompleter);
    }

    public C1917M0(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, LongFunction longFunction, BinaryOperator binaryOperator) {
        super(abstractC2106w1, spliterator);
        this.f1061h = abstractC2106w1;
        this.f1062i = longFunction;
        this.f1063j = binaryOperator;
    }

    public C1917M0(C1917M0 c1917m0, Spliterator spliterator) {
        super(c1917m0, spliterator);
        this.f1061h = c1917m0.f1061h;
        this.f1062i = c1917m0.f1062i;
        this.f1063j = c1917m0.f1063j;
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: c */
    public AbstractC2003d mo973c(Spliterator spliterator) {
        return new C1917M0(this, spliterator);
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: f, reason: merged with bridge method [inline-methods] */
    public final InterfaceC1887G0 mo972a() {
        InterfaceC2115y0 interfaceC2115y0 = (InterfaceC2115y0) this.f1062i.apply(this.f1061h.mo1016e0(this.f1219b));
        this.f1061h.mo1017t0(this.f1219b, interfaceC2115y0);
        return interfaceC2115y0.build();
    }
}
