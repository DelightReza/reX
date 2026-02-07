package p017j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

/* renamed from: j$.util.stream.I1 */
/* loaded from: classes2.dex */
public final class C1898I1 extends AbstractC2106w1 {

    /* renamed from: h */
    public final /* synthetic */ BinaryOperator f1026h;

    /* renamed from: i */
    public final /* synthetic */ BiConsumer f1027i;

    /* renamed from: j */
    public final /* synthetic */ Supplier f1028j;

    /* renamed from: k */
    public final /* synthetic */ Collector f1029k;

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: s0 */
    public final InterfaceC1942R1 mo939s0() {
        return new C1903J1(this.f1028j, this.f1027i, this.f1026h);
    }

    @Override // p017j$.util.stream.AbstractC2106w1, p017j$.util.stream.InterfaceC1910K3
    /* renamed from: s */
    public final int mo904s() {
        if (this.f1029k.characteristics().contains(EnumC2021g.UNORDERED)) {
            return EnumC1995b3.f1200r;
        }
        return 0;
    }

    public C1898I1(EnumC2001c3 enumC2001c3, BinaryOperator binaryOperator, BiConsumer biConsumer, Supplier supplier, Collector collector) {
        this.f1026h = binaryOperator;
        this.f1027i = biConsumer;
        this.f1028j = supplier;
        this.f1029k = collector;
    }
}
