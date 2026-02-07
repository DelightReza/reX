package p017j$.util.stream;

import java.util.concurrent.CountedCompleter;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.Y1 */
/* loaded from: classes2.dex */
public final class C1977Y1 extends AbstractC2003d {

    /* renamed from: h */
    public final AbstractC2106w1 f1148h;

    @Override // p017j$.util.stream.AbstractC2003d, java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        AbstractC2003d abstractC2003d = this.f1221d;
        if (abstractC2003d != null) {
            InterfaceC1942R1 interfaceC1942R1 = (InterfaceC1942R1) ((C1977Y1) abstractC2003d).f1223f;
            interfaceC1942R1.mo933q((InterfaceC1942R1) ((C1977Y1) this.f1222e).f1223f);
            this.f1223f = interfaceC1942R1;
        }
        super.onCompletion(countedCompleter);
    }

    public C1977Y1(AbstractC2106w1 abstractC2106w1, AbstractC2106w1 abstractC2106w12, Spliterator spliterator) {
        super(abstractC2106w12, spliterator);
        this.f1148h = abstractC2106w1;
    }

    public C1977Y1(C1977Y1 c1977y1, Spliterator spliterator) {
        super(c1977y1, spliterator);
        this.f1148h = c1977y1.f1148h;
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: c */
    public final AbstractC2003d mo973c(Spliterator spliterator) {
        return new C1977Y1(this, spliterator);
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: a */
    public final Object mo972a() {
        AbstractC2106w1 abstractC2106w1 = this.f1218a;
        InterfaceC1942R1 interfaceC1942R1Mo939s0 = this.f1148h.mo939s0();
        abstractC2106w1.mo1017t0(this.f1219b, interfaceC1942R1Mo939s0);
        return interfaceC1942R1Mo939s0;
    }
}
