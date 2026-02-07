package p017j$.util.stream;

import java.util.function.LongBinaryOperator;

/* renamed from: j$.util.stream.x1 */
/* loaded from: classes2.dex */
public final class C2111x1 extends AbstractC2106w1 {

    /* renamed from: h */
    public final /* synthetic */ LongBinaryOperator f1375h;

    /* renamed from: i */
    public final /* synthetic */ long f1376i;

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: s0 */
    public final InterfaceC1942R1 mo939s0() {
        return new C1933P1(this.f1376i, this.f1375h);
    }

    public C2111x1(EnumC2001c3 enumC2001c3, LongBinaryOperator longBinaryOperator, long j) {
        this.f1375h = longBinaryOperator;
        this.f1376i = j;
    }
}
