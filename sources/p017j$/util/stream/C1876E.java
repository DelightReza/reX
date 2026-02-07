package p017j$.util.stream;

import java.util.function.Predicate;
import java.util.function.Supplier;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.E */
/* loaded from: classes2.dex */
public final class C1876E implements InterfaceC1910K3 {

    /* renamed from: a */
    public final int f990a;

    /* renamed from: b */
    public final Object f991b;

    /* renamed from: c */
    public final Predicate f992c;

    /* renamed from: d */
    public final Supplier f993d;

    public C1876E(boolean z, EnumC2001c3 enumC2001c3, Object obj, Predicate predicate, Supplier supplier) {
        this.f990a = (z ? 0 : EnumC1995b3.f1200r) | EnumC1995b3.f1203u;
        this.f991b = obj;
        this.f992c = predicate;
        this.f993d = supplier;
    }

    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: s */
    public final int mo904s() {
        return this.f990a;
    }

    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: f */
    public final Object mo902f(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        InterfaceC1915L3 interfaceC1915L3 = (InterfaceC1915L3) this.f993d.get();
        abstractC1985a.mo1017t0(spliterator, interfaceC1915L3);
        Object obj = interfaceC1915L3.get();
        return obj != null ? obj : this.f991b;
    }

    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: i */
    public final Object mo903i(AbstractC2106w1 abstractC2106w1, Spliterator spliterator) {
        AbstractC1985a abstractC1985a = (AbstractC1985a) abstractC2106w1;
        return new C1906K(this, EnumC1995b3.ORDERED.m1030n(abstractC1985a.f1167m), abstractC1985a, spliterator).invoke();
    }
}
