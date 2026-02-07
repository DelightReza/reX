package p017j$.util.stream;

import java.util.function.IntFunction;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.t2 */
/* loaded from: classes2.dex */
public final class C2092t2 extends AbstractC2034i0 {

    /* renamed from: s */
    public final /* synthetic */ long f1344s;

    /* renamed from: t */
    public final /* synthetic */ long f1345t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2092t2(AbstractC2045k0 abstractC2045k0, int i, long j, long j2) {
        super(abstractC2045k0, i);
        this.f1344s = j;
        this.f1345t = j2;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: C0 */
    public final Spliterator mo963C0(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        long jMo1016e0 = abstractC1985a.mo1016e0(spliterator);
        if (jMo1016e0 > 0 && spliterator.hasCharacteristics(16384)) {
            InterfaceC1784Y interfaceC1784Y = (InterfaceC1784Y) abstractC1985a.mo1019v0(spliterator);
            long j = this.f1344s;
            return new C2093t3(interfaceC1784Y, j, AbstractC2122z2.m1112c(j, this.f1345t));
        }
        if (EnumC1995b3.ORDERED.m1030n(abstractC1985a.f1167m)) {
            return ((InterfaceC1887G0) new C2117y2(this, abstractC1985a, spliterator, new C1955U(29), this.f1344s, this.f1345t).invoke()).spliterator();
        }
        InterfaceC1784Y interfaceC1784Y2 = (InterfaceC1784Y) abstractC1985a.mo1019v0(spliterator);
        long j2 = this.f1344s;
        long j3 = this.f1345t;
        if (j2 <= jMo1016e0) {
            long jMin = jMo1016e0 - j2;
            if (j3 >= 0) {
                jMin = Math.min(j3, jMin);
            }
            j3 = jMin;
            j2 = 0;
        }
        return new C2123z3(interfaceC1784Y2, j2, j3);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: B0 */
    public final InterfaceC1887G0 mo962B0(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, IntFunction intFunction) {
        long jMin;
        long j;
        long jMo1016e0 = abstractC2106w1.mo1016e0(spliterator);
        if (jMo1016e0 > 0 && spliterator.hasCharacteristics(16384)) {
            AbstractC1985a abstractC1985a = (AbstractC1985a) abstractC2106w1;
            while (abstractC1985a.f1166l > 0) {
                abstractC1985a = abstractC1985a.f1163i;
            }
            return AbstractC2106w1.m1091X(abstractC2106w1, AbstractC2122z2.m1111b(abstractC1985a.mo916A0(), spliterator, this.f1344s, this.f1345t), true);
        }
        if (!EnumC1995b3.ORDERED.m1030n(((AbstractC1985a) abstractC2106w1).f1167m)) {
            InterfaceC1784Y interfaceC1784Y = (InterfaceC1784Y) abstractC2106w1.mo1019v0(spliterator);
            long j2 = this.f1344s;
            long j3 = this.f1345t;
            if (j2 <= jMo1016e0) {
                long j4 = jMo1016e0 - j2;
                jMin = j3 >= 0 ? Math.min(j3, j4) : j4;
                j = 0;
            } else {
                jMin = j3;
                j = j2;
            }
            return AbstractC2106w1.m1091X(this, new C2123z3(interfaceC1784Y, j, jMin), true);
        }
        return (InterfaceC1887G0) new C2117y2(this, abstractC2106w1, spliterator, intFunction, this.f1344s, this.f1345t).invoke();
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        return new C2087s2(this, interfaceC2062n2);
    }
}
