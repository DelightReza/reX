package p017j$.util.stream;

import java.util.function.DoubleBinaryOperator;

/* renamed from: j$.util.stream.F1 */
/* loaded from: classes2.dex */
public final class C1883F1 extends AbstractC2106w1 {

    /* renamed from: h */
    public final /* synthetic */ DoubleBinaryOperator f1002h;

    /* renamed from: i */
    public final /* synthetic */ double f1003i;

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: s0 */
    public final InterfaceC1942R1 mo939s0() {
        return new C1858A1(this.f1003i, this.f1002h);
    }

    public C1883F1(EnumC2001c3 enumC2001c3, DoubleBinaryOperator doubleBinaryOperator, double d) {
        this.f1002h = doubleBinaryOperator;
        this.f1003i = d;
    }
}
