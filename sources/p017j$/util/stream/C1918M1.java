package p017j$.util.stream;

import java.util.function.IntBinaryOperator;

/* renamed from: j$.util.stream.M1 */
/* loaded from: classes2.dex */
public final class C1918M1 extends AbstractC2106w1 {

    /* renamed from: h */
    public final /* synthetic */ IntBinaryOperator f1064h;

    /* renamed from: i */
    public final /* synthetic */ int f1065i;

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: s0 */
    public final InterfaceC1942R1 mo939s0() {
        return new C1913L1(this.f1065i, this.f1064h);
    }

    public C1918M1(EnumC2001c3 enumC2001c3, IntBinaryOperator intBinaryOperator, int i) {
        this.f1064h = intBinaryOperator;
        this.f1065i = i;
    }
}
