package p017j$.util.stream;

import java.util.function.IntConsumer;
import p017j$.util.Spliterator;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.N */
/* loaded from: classes2.dex */
public final class C1921N extends AbstractC1936Q implements InterfaceC2052l2 {

    /* renamed from: b */
    public final IntConsumer f1069b;

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo975l((Integer) obj);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // java.util.function.Supplier
    public final /* bridge */ /* synthetic */ Object get() {
        return null;
    }

    @Override // p017j$.util.stream.InterfaceC2052l2
    /* renamed from: l */
    public final /* synthetic */ void mo975l(Integer num) {
        AbstractC2106w1.m1070C(this, num);
    }

    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: f */
    public final Object mo902f(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        abstractC1985a.mo1017t0(spliterator, this);
        return null;
    }

    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: i */
    public final /* bridge */ /* synthetic */ Object mo903i(AbstractC2106w1 abstractC2106w1, Spliterator spliterator) {
        m990a(abstractC2106w1, spliterator);
        return null;
    }

    public C1921N(IntConsumer intConsumer, boolean z) {
        super(z);
        this.f1069b = intConsumer;
    }

    @Override // p017j$.util.stream.AbstractC1936Q, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        this.f1069b.accept(i);
    }
}
