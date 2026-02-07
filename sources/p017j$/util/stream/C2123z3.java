package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.z3 */
/* loaded from: classes2.dex */
public final class C2123z3 extends AbstractC1860A3 implements InterfaceC1784Y, LongConsumer {

    /* renamed from: f */
    public long f1392f;

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m515k(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m529y(this, consumer);
    }

    @Override // p017j$.util.stream.AbstractC1875D3
    /* renamed from: b */
    public final Spliterator mo940b(Spliterator spliterator) {
        return new C2123z3((InterfaceC1784Y) spliterator, this);
    }

    @Override // p017j$.util.stream.AbstractC1860A3
    /* renamed from: d */
    public final void mo935d(Object obj) {
        ((LongConsumer) obj).accept(this.f1392f);
    }

    @Override // java.util.function.LongConsumer
    public final void accept(long j) {
        this.f1392f = j;
    }

    @Override // p017j$.util.stream.AbstractC1860A3
    /* renamed from: e */
    public final AbstractC2031h3 mo936e(int i) {
        return new C2025g3(i);
    }
}
