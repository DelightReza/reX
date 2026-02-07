package p017j$.util.stream;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.J3 */
/* loaded from: classes2.dex */
public final class C1905J3 implements Spliterator.OfInt {

    /* renamed from: a */
    public int f1044a;

    /* renamed from: b */
    public final int f1045b;

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return 17749;
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m514j(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final Comparator getComparator() {
        return null;
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return Spliterator.CC.$default$getExactSizeIfKnown(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m528x(this, consumer);
    }

    public C1905J3(int i, int i2) {
        this.f1044a = i;
        this.f1045b = i2;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        int i = this.f1044a;
        if (i >= this.f1045b) {
            return false;
        }
        this.f1044a = i + 1;
        intConsumer.accept(i);
        return true;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        int i = this.f1045b;
        this.f1044a = i;
        for (int i2 = this.f1044a; i2 < i; i2++) {
            intConsumer.accept(i2);
        }
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return (this.f1045b - this.f1044a) + 0;
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final Spliterator.OfInt trySplit() {
        long jEstimateSize = estimateSize();
        if (jEstimateSize <= 1) {
            return null;
        }
        int i = this.f1044a;
        int i2 = ((int) (jEstimateSize / (jEstimateSize < 16777216 ? 2 : 8))) + i;
        this.f1044a = i2;
        return new C1905J3(i, i2);
    }
}
