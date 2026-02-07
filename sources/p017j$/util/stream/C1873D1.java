package p017j$.util.stream;

import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.D1 */
/* loaded from: classes2.dex */
public final class C1873D1 extends AbstractC2106w1 {

    /* renamed from: h */
    public final /* synthetic */ int f982h;

    public /* synthetic */ C1873D1(int i) {
        this.f982h = i;
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: s0 */
    public final InterfaceC1942R1 mo939s0() {
        switch (this.f982h) {
            case 0:
                return new C1962V1();
            case 1:
                return new C1952T1();
            case 2:
                return new C1967W1();
            default:
                return new C1957U1();
        }
    }

    @Override // p017j$.util.stream.AbstractC2106w1, p017j$.util.stream.InterfaceC1910K3
    /* renamed from: f */
    public final Object mo902f(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        switch (this.f982h) {
            case 0:
                if (!EnumC1995b3.SIZED.m1030n(abstractC1985a.f1167m)) {
                    break;
                } else {
                    break;
                }
            case 1:
                if (!EnumC1995b3.SIZED.m1030n(abstractC1985a.f1167m)) {
                    break;
                } else {
                    break;
                }
            case 2:
                if (!EnumC1995b3.SIZED.m1030n(abstractC1985a.f1167m)) {
                    break;
                } else {
                    break;
                }
            default:
                if (!EnumC1995b3.SIZED.m1030n(abstractC1985a.f1167m)) {
                    break;
                } else {
                    break;
                }
        }
        return (Long) super.mo902f(abstractC1985a, spliterator);
    }

    @Override // p017j$.util.stream.AbstractC2106w1, p017j$.util.stream.InterfaceC1910K3
    /* renamed from: i */
    public final Object mo903i(AbstractC2106w1 abstractC2106w1, Spliterator spliterator) {
        switch (this.f982h) {
            case 0:
                if (!EnumC1995b3.SIZED.m1030n(((AbstractC1985a) abstractC2106w1).f1167m)) {
                    break;
                } else {
                    break;
                }
            case 1:
                if (!EnumC1995b3.SIZED.m1030n(((AbstractC1985a) abstractC2106w1).f1167m)) {
                    break;
                } else {
                    break;
                }
            case 2:
                if (!EnumC1995b3.SIZED.m1030n(((AbstractC1985a) abstractC2106w1).f1167m)) {
                    break;
                } else {
                    break;
                }
            default:
                if (!EnumC1995b3.SIZED.m1030n(((AbstractC1985a) abstractC2106w1).f1167m)) {
                    break;
                } else {
                    break;
                }
        }
        return (Long) super.mo903i(abstractC2106w1, spliterator);
    }

    @Override // p017j$.util.stream.AbstractC2106w1, p017j$.util.stream.InterfaceC1910K3
    /* renamed from: s */
    public final int mo904s() {
        switch (this.f982h) {
        }
        return EnumC1995b3.f1200r;
    }
}
