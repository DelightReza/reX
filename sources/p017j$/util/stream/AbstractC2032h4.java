package p017j$.util.stream;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.h4 */
/* loaded from: classes2.dex */
public abstract class AbstractC2032h4 implements Spliterator {

    /* renamed from: a */
    public final Spliterator f1257a;

    /* renamed from: b */
    public final AtomicBoolean f1258b;

    /* renamed from: c */
    public boolean f1259c;

    /* renamed from: d */
    public int f1260d;

    /* renamed from: b */
    public abstract Spliterator mo1031b(Spliterator spliterator);

    @Override // p017j$.util.Spliterator
    public /* synthetic */ void forEachRemaining(Consumer consumer) {
        Spliterator.CC.$default$forEachRemaining(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final long getExactSizeIfKnown() {
        return -1L;
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    public AbstractC2032h4(Spliterator spliterator) {
        this.f1259c = true;
        this.f1257a = spliterator;
        this.f1258b = new AtomicBoolean();
    }

    public AbstractC2032h4(Spliterator spliterator, AbstractC2032h4 abstractC2032h4) {
        this.f1259c = true;
        this.f1257a = spliterator;
        abstractC2032h4.getClass();
        this.f1258b = abstractC2032h4.f1258b;
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return this.f1257a.estimateSize();
    }

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return this.f1257a.characteristics() & (-16449);
    }

    @Override // p017j$.util.Spliterator
    public final Comparator getComparator() {
        return this.f1257a.getComparator();
    }

    @Override // p017j$.util.Spliterator
    public Spliterator trySplit() {
        Spliterator spliteratorTrySplit = this.f1257a.trySplit();
        if (spliteratorTrySplit != null) {
            return mo1031b(spliteratorTrySplit);
        }
        return null;
    }

    /* renamed from: a */
    public final boolean m1041a() {
        return (this.f1260d == 0 && this.f1258b.get()) ? false : true;
    }

    @Override // p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
        return (Spliterator.OfInt) trySplit();
    }

    @Override // p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ InterfaceC1784Y trySplit() {
        return (InterfaceC1784Y) trySplit();
    }

    @Override // p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ InterfaceC1779T trySplit() {
        return (InterfaceC1779T) trySplit();
    }

    @Override // p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ InterfaceC1789b0 trySplit() {
        return (InterfaceC1789b0) trySplit();
    }
}
