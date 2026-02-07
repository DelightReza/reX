package p017j$.util.stream;

import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.stream.V1 */
/* loaded from: classes2.dex */
public final class C1962V1 extends AbstractC1972X1 implements InterfaceC2057m2 {
    @Override // java.util.function.Consumer
    /* renamed from: accept */
    public final /* bridge */ /* synthetic */ void m971v(Object obj) {
        mo988u((Long) obj);
    }

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC2057m2
    /* renamed from: u */
    public final /* synthetic */ void mo988u(Long l) {
        AbstractC2106w1.m1072E(this, l);
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
    public final void accept(long j) {
        this.f1138b++;
    }
}
