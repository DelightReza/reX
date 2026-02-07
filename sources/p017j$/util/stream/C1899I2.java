package p017j$.util.stream;

import java.util.Arrays;
import java.util.function.IntFunction;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.I2 */
/* loaded from: classes2.dex */
public final class C1899I2 extends AbstractC1986a0 {
    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        Objects.requireNonNull(interfaceC2062n2);
        return EnumC1995b3.SORTED.m1030n(i) ? interfaceC2062n2 : EnumC1995b3.SIZED.m1030n(i) ? new C1924N2(interfaceC2062n2) : new C1884F2(interfaceC2062n2);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: B0 */
    public final InterfaceC1887G0 mo962B0(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, IntFunction intFunction) {
        if (EnumC1995b3.SORTED.m1030n(((AbstractC1985a) abstractC2106w1).f1167m)) {
            return abstractC2106w1.mo1015d0(spliterator, false, intFunction);
        }
        int[] iArr = (int[]) ((InterfaceC1867C0) abstractC2106w1.mo1015d0(spliterator, true, intFunction)).mo951b();
        Arrays.sort(iArr);
        return new C1993b1(iArr);
    }
}
