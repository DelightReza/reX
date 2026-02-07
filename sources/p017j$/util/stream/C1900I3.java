package p017j$.util.stream;

import java.util.Comparator;
import java.util.function.Consumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.I3 */
/* loaded from: classes2.dex */
public final class C1900I3 implements Spliterator {

    /* renamed from: a */
    public final Spliterator f1030a;

    /* renamed from: b */
    public final Spliterator f1031b;

    /* renamed from: c */
    public boolean f1032c = true;

    /* renamed from: d */
    public final boolean f1033d;

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return Spliterator.CC.$default$getExactSizeIfKnown(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    public C1900I3(Spliterator spliterator, Spliterator spliterator2) {
        this.f1030a = spliterator;
        this.f1031b = spliterator2;
        this.f1033d = spliterator2.estimateSize() + spliterator.estimateSize() < 0;
    }

    @Override // p017j$.util.Spliterator
    public final Spliterator trySplit() {
        Spliterator spliteratorTrySplit = this.f1032c ? this.f1030a : this.f1031b.trySplit();
        this.f1032c = false;
        return spliteratorTrySplit;
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        boolean z = this.f1032c;
        Spliterator spliterator = this.f1031b;
        if (z) {
            boolean zTryAdvance = this.f1030a.tryAdvance(consumer);
            if (zTryAdvance) {
                return zTryAdvance;
            }
            this.f1032c = false;
            return spliterator.tryAdvance(consumer);
        }
        return spliterator.tryAdvance(consumer);
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        if (this.f1032c) {
            this.f1030a.forEachRemaining(consumer);
        }
        this.f1031b.forEachRemaining(consumer);
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        boolean z = this.f1032c;
        Spliterator spliterator = this.f1031b;
        if (z) {
            long jEstimateSize = spliterator.estimateSize() + this.f1030a.estimateSize();
            if (jEstimateSize >= 0) {
                return jEstimateSize;
            }
            return Long.MAX_VALUE;
        }
        return spliterator.estimateSize();
    }

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        boolean z = this.f1032c;
        Spliterator spliterator = this.f1031b;
        if (z) {
            return this.f1030a.characteristics() & spliterator.characteristics() & (~((this.f1033d ? 16448 : 0) | 5));
        }
        return spliterator.characteristics();
    }

    @Override // p017j$.util.Spliterator
    public final Comparator getComparator() {
        if (this.f1032c) {
            throw new IllegalStateException();
        }
        return this.f1031b.getComparator();
    }
}
