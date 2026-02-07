package p017j$.util;

import java.util.Comparator;
import java.util.function.Consumer;
import p017j$.time.C1726t;

/* renamed from: j$.util.r */
/* loaded from: classes2.dex */
public final class C1852r implements Spliterator {

    /* renamed from: a */
    public final Spliterator f957a;

    public C1852r(Spliterator spliterator) {
        this.f957a = spliterator;
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        Objects.requireNonNull(consumer);
        return this.f957a.tryAdvance(new C1726t(1, consumer));
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        Objects.requireNonNull(consumer);
        this.f957a.forEachRemaining(new C1726t(1, consumer));
    }

    @Override // p017j$.util.Spliterator
    public final Spliterator trySplit() {
        Spliterator spliteratorTrySplit = this.f957a.trySplit();
        if (spliteratorTrySplit == null) {
            return null;
        }
        return new C1852r(spliteratorTrySplit);
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return this.f957a.estimateSize();
    }

    @Override // p017j$.util.Spliterator
    public final long getExactSizeIfKnown() {
        return this.f957a.getExactSizeIfKnown();
    }

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return this.f957a.characteristics();
    }

    @Override // p017j$.util.Spliterator
    public final boolean hasCharacteristics(int i) {
        return this.f957a.hasCharacteristics(i);
    }

    @Override // p017j$.util.Spliterator
    public final Comparator getComparator() {
        return this.f957a.getComparator();
    }
}
