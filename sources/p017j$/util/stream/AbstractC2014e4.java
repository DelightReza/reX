package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.stream.e4 */
/* loaded from: classes2.dex */
public abstract class AbstractC2014e4 extends AbstractC2032h4 implements IntConsumer, Spliterator.OfInt {

    /* renamed from: e */
    public final IntPredicate f1237e;

    /* renamed from: f */
    public int f1238f;

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // p017j$.util.stream.AbstractC2032h4, p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m514j(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m528x(this, consumer);
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(IntConsumer intConsumer) {
        while (tryAdvance(intConsumer)) {
        }
    }

    public AbstractC2014e4(Spliterator.OfInt ofInt, IntPredicate intPredicate) {
        super(ofInt);
        this.f1237e = intPredicate;
    }

    public AbstractC2014e4(Spliterator.OfInt ofInt, AbstractC2014e4 abstractC2014e4) {
        super(ofInt, abstractC2014e4);
        this.f1237e = abstractC2014e4.f1237e;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        this.f1260d = (this.f1260d + 1) & 63;
        this.f1238f = i;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public /* bridge */ /* synthetic */ boolean tryAdvance(Object obj) {
        return tryAdvance((IntConsumer) obj);
    }
}
