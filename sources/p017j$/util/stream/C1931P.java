package p017j$.util.stream;

import java.util.function.Consumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.P */
/* loaded from: classes2.dex */
public final class C1931P extends AbstractC1936Q {

    /* renamed from: b */
    public final Consumer f1087b;

    @Override // java.util.function.Supplier
    public final /* bridge */ /* synthetic */ Object get() {
        return null;
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

    public C1931P(Consumer consumer, boolean z) {
        super(z);
        this.f1087b = consumer;
    }

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final void m971v(Object obj) {
        this.f1087b.m971v(obj);
    }
}
