package p017j$.util.stream;

import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.p0 */
/* loaded from: classes2.dex */
public final class C2070p0 extends AbstractC2085s0 implements InterfaceC2052l2 {

    /* renamed from: c */
    public final /* synthetic */ EnumC2090t0 f1310c;

    /* renamed from: d */
    public final /* synthetic */ IntPredicate f1311d;

    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo975l((Integer) obj);
    }

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2052l2
    /* renamed from: l */
    public final /* synthetic */ void mo975l(Integer num) {
        AbstractC2106w1.m1070C(this, num);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C2070p0(EnumC2090t0 enumC2090t0, IntPredicate intPredicate) {
        super(enumC2090t0);
        this.f1310c = enumC2090t0;
        this.f1311d = intPredicate;
    }

    @Override // p017j$.util.stream.AbstractC2085s0, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        if (this.f1335a) {
            return;
        }
        boolean zTest = this.f1311d.test(i);
        EnumC2090t0 enumC2090t0 = this.f1310c;
        if (zTest == enumC2090t0.f1342a) {
            this.f1335a = true;
            this.f1336b = enumC2090t0.f1343b;
        }
    }
}
