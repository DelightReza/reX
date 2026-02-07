package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.y3 */
/* loaded from: classes2.dex */
public final class C2118y3 extends AbstractC1860A3 implements Spliterator.OfInt, IntConsumer {

    /* renamed from: f */
    public int f1388f;

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m514j(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m528x(this, consumer);
    }

    @Override // p017j$.util.stream.AbstractC1875D3
    /* renamed from: b */
    public final Spliterator mo940b(Spliterator spliterator) {
        return new C2118y3((Spliterator.OfInt) spliterator, this);
    }

    @Override // p017j$.util.stream.AbstractC1860A3
    /* renamed from: d */
    public final void mo935d(Object obj) {
        ((IntConsumer) obj).accept(this.f1388f);
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        this.f1388f = i;
    }

    @Override // p017j$.util.stream.AbstractC1860A3
    /* renamed from: e */
    public final AbstractC2031h3 mo936e(int i) {
        return new C2019f3(i);
    }
}
