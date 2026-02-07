package p017j$.util.stream;

import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.S3 */
/* loaded from: classes2.dex */
public final class C1949S3 extends AbstractC1986a0 implements InterfaceC1974X3 {

    /* renamed from: s */
    public final /* synthetic */ IntPredicate f1121s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1949S3(AbstractC1998c0 abstractC1998c0, int i, IntPredicate intPredicate) {
        super(abstractC1998c0, i);
        this.f1121s = intPredicate;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: C0 */
    public final Spliterator mo963C0(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        return EnumC1995b3.ORDERED.m1030n(abstractC1985a.f1167m) ? mo962B0(abstractC1985a, spliterator, new C2097u2(4)).spliterator() : new C2002c4((Spliterator.OfInt) abstractC1985a.mo1019v0(spliterator), this.f1121s);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: B0 */
    public final InterfaceC1887G0 mo962B0(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, IntFunction intFunction) {
        return (InterfaceC1887G0) new C1984Z3(this, abstractC2106w1, spliterator, intFunction).invoke();
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        return new C1944R3(this, interfaceC2062n2, false);
    }

    @Override // p017j$.util.stream.InterfaceC1974X3
    /* renamed from: h */
    public final InterfaceC1979Y3 mo965h(InterfaceC2115y0 interfaceC2115y0, boolean z) {
        return new C1944R3(this, interfaceC2115y0, z);
    }
}
