package p017j$.util.stream;

import java.util.function.IntFunction;
import java.util.function.Predicate;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.N3 */
/* loaded from: classes2.dex */
public final class C1925N3 extends AbstractC2006d2 implements InterfaceC1974X3 {

    /* renamed from: s */
    public final /* synthetic */ int f1075s;

    /* renamed from: t */
    public final /* synthetic */ Predicate f1076t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1925N3(AbstractC2018f2 abstractC2018f2, int i, Predicate predicate, int i2) {
        super(abstractC2018f2, i);
        this.f1075s = i2;
        this.f1076t = predicate;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: C0 */
    public final Spliterator mo963C0(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        switch (this.f1075s) {
            case 0:
                return EnumC1995b3.ORDERED.m1030n(abstractC1985a.f1167m) ? mo962B0(abstractC1985a, spliterator, new C1955U(19)).spliterator() : new C2026g4(abstractC1985a.mo1019v0(spliterator), this.f1076t, 1);
            default:
                return EnumC1995b3.ORDERED.m1030n(abstractC1985a.f1167m) ? mo962B0(abstractC1985a, spliterator, new C1955U(19)).spliterator() : new C2026g4(abstractC1985a.mo1019v0(spliterator), this.f1076t, 0);
        }
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: B0 */
    public final InterfaceC1887G0 mo962B0(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, IntFunction intFunction) {
        switch (this.f1075s) {
            case 0:
                return (InterfaceC1887G0) new C1990a4(this, abstractC2106w1, spliterator, intFunction).invoke();
            default:
                return (InterfaceC1887G0) new C1984Z3(this, abstractC2106w1, spliterator, intFunction).invoke();
        }
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        switch (this.f1075s) {
            case 0:
                return new C2054m(this, interfaceC2062n2);
            default:
                return new C1930O3(this, interfaceC2062n2, false);
        }
    }

    @Override // p017j$.util.stream.InterfaceC1974X3
    /* renamed from: h */
    public InterfaceC1979Y3 mo965h(InterfaceC2115y0 interfaceC2115y0, boolean z) {
        return new C1930O3(this, interfaceC2115y0, z);
    }
}
