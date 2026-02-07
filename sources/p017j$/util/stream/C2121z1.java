package p017j$.util.stream;

import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;

/* renamed from: j$.util.stream.z1 */
/* loaded from: classes2.dex */
public final class C2121z1 extends AbstractC2106w1 {

    /* renamed from: h */
    public final /* synthetic */ int f1390h;

    /* renamed from: i */
    public final /* synthetic */ Object f1391i;

    public /* synthetic */ C2121z1(EnumC2001c3 enumC2001c3, Object obj, int i) {
        this.f1390h = i;
        this.f1391i = obj;
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: s0 */
    public final InterfaceC1942R1 mo939s0() {
        switch (this.f1390h) {
            case 0:
                return new C1938Q1((LongBinaryOperator) this.f1391i);
            case 1:
                return new C1868C1((DoubleBinaryOperator) this.f1391i);
            case 2:
                return new C1893H1((BinaryOperator) this.f1391i);
            default:
                return new C1923N1((IntBinaryOperator) this.f1391i);
        }
    }
}
