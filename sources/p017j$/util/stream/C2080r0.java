package p017j$.util.stream;

import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.stream.r0 */
/* loaded from: classes2.dex */
public final class C2080r0 extends AbstractC2085s0 implements InterfaceC2047k2 {
    @Override // java.util.function.Consumer
    public final /* bridge */ /* synthetic */ void accept(Object obj) {
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

    @Override // p017j$.util.stream.AbstractC2085s0, p017j$.util.stream.InterfaceC2062n2, p017j$.util.stream.InterfaceC2047k2, java.util.function.DoubleConsumer
    public final void accept(double d) {
        if (this.f1335a) {
            return;
        }
        DoublePredicate doublePredicate = null;
        doublePredicate.test(d);
        throw null;
    }
}
