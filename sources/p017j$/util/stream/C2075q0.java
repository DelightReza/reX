package p017j$.util.stream;

import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.stream.q0 */
/* loaded from: classes2.dex */
public final class C2075q0 extends AbstractC2085s0 implements InterfaceC2057m2 {
    @Override // java.util.function.Consumer
    public final /* bridge */ /* synthetic */ void accept(Object obj) {
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

    @Override // p017j$.util.stream.AbstractC2085s0, p017j$.util.stream.InterfaceC2062n2
    public final void accept(long j) {
        if (this.f1335a) {
            return;
        }
        LongPredicate longPredicate = null;
        longPredicate.test(j);
        throw null;
    }
}
