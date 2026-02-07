package p017j$.util.stream;

import java.util.Arrays;
import java.util.function.IntFunction;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.J2 */
/* loaded from: classes2.dex */
public final class C1904J2 extends AbstractC2034i0 implements InterfaceC1974X3 {

    /* renamed from: s */
    public final /* synthetic */ int f1043s;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1904J2(AbstractC1985a abstractC1985a, int i, int i2) {
        super(abstractC1985a, i);
        this.f1043s = i2;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: C0 */
    public Spliterator mo963C0(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        switch (this.f1043s) {
            case 1:
                return EnumC1995b3.ORDERED.m1030n(abstractC1985a.f1167m) ? mo962B0(abstractC1985a, spliterator, new C2097u2(5)).spliterator() : new C2020f4((InterfaceC1784Y) abstractC1985a.mo1019v0(spliterator), 1);
            case 2:
                return EnumC1995b3.ORDERED.m1030n(abstractC1985a.f1167m) ? mo962B0(abstractC1985a, spliterator, new C2097u2(6)).spliterator() : new C2020f4((InterfaceC1784Y) abstractC1985a.mo1019v0(spliterator), 0);
            default:
                return super.mo963C0(abstractC1985a, spliterator);
        }
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: B0 */
    public final InterfaceC1887G0 mo962B0(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, IntFunction intFunction) {
        switch (this.f1043s) {
            case 0:
                if (EnumC1995b3.SORTED.m1030n(((AbstractC1985a) abstractC2106w1).f1167m)) {
                    return abstractC2106w1.mo1015d0(spliterator, false, intFunction);
                }
                long[] jArr = (long[]) ((InterfaceC1877E0) abstractC2106w1.mo1015d0(spliterator, true, intFunction)).mo951b();
                Arrays.sort(jArr);
                return new C2046k1(jArr);
            case 1:
                return (InterfaceC1887G0) new C1990a4(this, abstractC2106w1, spliterator, intFunction).invoke();
            default:
                return (InterfaceC1887G0) new C1984Z3(this, abstractC2106w1, spliterator, intFunction).invoke();
        }
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        switch (this.f1043s) {
            case 0:
                Objects.requireNonNull(interfaceC2062n2);
                if (EnumC1995b3.SORTED.m1030n(i)) {
                    return interfaceC2062n2;
                }
                return EnumC1995b3.SIZED.m1030n(i) ? new C1929O2(interfaceC2062n2) : new C1889G2(interfaceC2062n2);
            case 1:
                return new C1954T3(this, interfaceC2062n2);
            default:
                return new C1959U3(this, interfaceC2062n2, false);
        }
    }

    @Override // p017j$.util.stream.InterfaceC1974X3
    /* renamed from: h */
    public InterfaceC1979Y3 mo965h(InterfaceC2115y0 interfaceC2115y0, boolean z) {
        return new C1959U3(this, interfaceC2115y0, z);
    }
}
