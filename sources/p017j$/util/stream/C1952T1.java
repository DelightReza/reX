package p017j$.util.stream;

import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.stream.T1 */
/* loaded from: classes2.dex */
public final class C1952T1 extends AbstractC1972X1 implements InterfaceC2047k2 {
    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo934v((Double) obj);
    }

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2047k2
    /* renamed from: v */
    public final /* synthetic */ void mo934v(Double d) {
        AbstractC2106w1.m1068A(this, d);
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

    @Override // p017j$.util.stream.AbstractC1972X1, p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        this.f1138b++;
    }
}
