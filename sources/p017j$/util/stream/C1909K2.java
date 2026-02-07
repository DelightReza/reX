package p017j$.util.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntFunction;
import p017j$.util.Comparator;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.K2 */
/* loaded from: classes2.dex */
public final class C1909K2 extends AbstractC2006d2 {

    /* renamed from: s */
    public final boolean f1052s;

    /* renamed from: t */
    public final Comparator f1053t;

    public C1909K2(AbstractC2018f2 abstractC2018f2) {
        super(abstractC2018f2, EnumC1995b3.f1199q | EnumC1995b3.f1197o);
        this.f1052s = true;
        this.f1053t = Comparator.CC.naturalOrder();
    }

    public C1909K2(AbstractC2018f2 abstractC2018f2, java.util.Comparator comparator) {
        super(abstractC2018f2, EnumC1995b3.f1199q | EnumC1995b3.f1198p);
        this.f1052s = false;
        this.f1053t = (java.util.Comparator) Objects.requireNonNull(comparator);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        Objects.requireNonNull(interfaceC2062n2);
        if (EnumC1995b3.SORTED.m1030n(i) && this.f1052s) {
            return interfaceC2062n2;
        }
        if (EnumC1995b3.SIZED.m1030n(i)) {
            return new C1934P2(interfaceC2062n2, this.f1053t);
        }
        return new C1914L2(interfaceC2062n2, this.f1053t);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: B0 */
    public final InterfaceC1887G0 mo962B0(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, IntFunction intFunction) {
        if (EnumC1995b3.SORTED.m1030n(((AbstractC1985a) abstractC2106w1).f1167m) && this.f1052s) {
            return abstractC2106w1.mo1015d0(spliterator, false, intFunction);
        }
        Object[] objArrMo958g = abstractC2106w1.mo1015d0(spliterator, true, intFunction).mo958g(intFunction);
        Arrays.sort(objArrMo958g, this.f1053t);
        return new C1902J0(objArrMo958g);
    }
}
