package p017j$.util.stream;

import java.util.function.IntFunction;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.p2 */
/* loaded from: classes2.dex */
public final class C2072p2 extends AbstractC2006d2 {

    /* renamed from: s */
    public final /* synthetic */ long f1313s;

    /* renamed from: t */
    public final /* synthetic */ long f1314t;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2072p2(AbstractC2018f2 abstractC2018f2, int i, long j, long j2) {
        super(abstractC2018f2, i);
        this.f1313s = j;
        this.f1314t = j2;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: C0 */
    public final Spliterator mo963C0(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        long jMo1016e0 = abstractC1985a.mo1016e0(spliterator);
        if (jMo1016e0 > 0 && spliterator.hasCharacteristics(16384)) {
            Spliterator spliteratorMo1019v0 = abstractC1985a.mo1019v0(spliterator);
            long j = this.f1313s;
            return new C2103v3(spliteratorMo1019v0, j, AbstractC2122z2.m1112c(j, this.f1314t));
        }
        if (EnumC1995b3.ORDERED.m1030n(abstractC1985a.f1167m)) {
            return ((InterfaceC1887G0) new C2117y2(this, abstractC1985a, spliterator, new C1955U(19), this.f1313s, this.f1314t).invoke()).spliterator();
        }
        Spliterator spliteratorMo1019v02 = abstractC1985a.mo1019v0(spliterator);
        long j2 = this.f1313s;
        long j3 = this.f1314t;
        if (j2 <= jMo1016e0) {
            long jMin = jMo1016e0 - j2;
            if (j3 >= 0) {
                jMin = Math.min(j3, jMin);
            }
            j3 = jMin;
            j2 = 0;
        }
        return new C1865B3(spliteratorMo1019v02, j2, j3);
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
            return AbstractC2106w1.m1088U(abstractC2106w1, AbstractC2122z2.m1111b(abstractC1985a.mo916A0(), spliterator, this.f1313s, this.f1314t), true, intFunction);
        }
        if (!EnumC1995b3.ORDERED.m1030n(((AbstractC1985a) abstractC2106w1).f1167m)) {
            Spliterator spliteratorMo1019v0 = abstractC2106w1.mo1019v0(spliterator);
            long j2 = this.f1313s;
            long j3 = this.f1314t;
            if (j2 <= jMo1016e0) {
                long j4 = jMo1016e0 - j2;
                jMin = j3 >= 0 ? Math.min(j3, j4) : j4;
                j = 0;
            } else {
                jMin = j3;
                j = j2;
            }
            return AbstractC2106w1.m1088U(this, new C1865B3(spliteratorMo1019v0, j, jMin), true, intFunction);
        }
        return (InterfaceC1887G0) new C2117y2(this, abstractC2106w1, spliterator, intFunction, this.f1313s, this.f1314t).invoke();
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: E0 */
    public final InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2) {
        return new C2067o2(this, interfaceC2062n2);
    }
}
