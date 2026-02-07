package p017j$.util.stream;

import java.util.function.IntConsumer;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.U1 */
/* loaded from: classes2.dex */
public final class C1957U1 extends AbstractC1972X1 implements InterfaceC2052l2 {
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

    @Override // p017j$.util.stream.AbstractC1947S1, java.util.function.Supplier
    public final Object get() {
        return Long.valueOf(this.f1138b);
    }

    @Override // p017j$.util.stream.InterfaceC1942R1
    /* renamed from: q */
    public final void mo933q(InterfaceC1942R1 interfaceC1942R1) {
        this.f1138b += ((AbstractC1972X1) interfaceC1942R1).f1138b;
    }

    @Override // p017j$.util.stream.AbstractC1972X1, p017j$.util.stream.InterfaceC2062n2
    public final void accept(int i) {
        this.f1138b++;
    }
}
