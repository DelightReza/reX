package p017j$.util.stream;

import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.O */
/* loaded from: classes2.dex */
public final class C1926O extends AbstractC1936Q implements InterfaceC2057m2 {

    /* renamed from: b */
    public final LongConsumer f1077b;

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo988u((Long) obj);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // java.util.function.Supplier
    public final /* bridge */ /* synthetic */ Object get() {
        return null;
    }

    @Override // p017j$.util.stream.InterfaceC2057m2
    /* renamed from: u */
    public final /* synthetic */ void mo988u(Long l) {
        AbstractC2106w1.m1072E(this, l);
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

    public C1926O(LongConsumer longConsumer, boolean z) {
        super(z);
        this.f1077b = longConsumer;
    }

    @Override // p017j$.util.stream.AbstractC1936Q, p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        this.f1077b.accept(j);
    }
}
