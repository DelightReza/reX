package p017j$.util.stream;

import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.M */
/* loaded from: classes2.dex */
public final class C1916M extends AbstractC1936Q implements InterfaceC2047k2 {

    /* renamed from: b */
    public final DoubleConsumer f1060b;

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo934v((Double) obj);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // java.util.function.Supplier
    public final /* bridge */ /* synthetic */ Object get() {
        return null;
    }

    @Override // p017j$.util.stream.InterfaceC2047k2
    /* renamed from: v */
    public final /* synthetic */ void mo934v(Double d) {
        AbstractC2106w1.m1068A(this, d);
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

    public C1916M(DoubleConsumer doubleConsumer, boolean z) {
        super(z);
        this.f1060b = doubleConsumer;
    }

    @Override // p017j$.util.stream.AbstractC1936Q, p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        this.f1060b.accept(d);
    }
}
