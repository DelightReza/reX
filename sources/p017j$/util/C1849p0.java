package p017j$.util;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.p0 */
/* loaded from: classes2.dex */
public final class C1849p0 implements InterfaceC1784Y {

    /* renamed from: a */
    public final long[] f946a;

    /* renamed from: b */
    public int f947b;

    /* renamed from: c */
    public final int f948c;

    /* renamed from: d */
    public final int f949d;

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m515k(this, consumer);
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
        return AbstractC1636a.m529y(this, consumer);
    }

    public C1849p0(long[] jArr, int i, int i2, int i3) {
        this.f946a = jArr;
        this.f947b = i;
        this.f948c = i2;
        this.f949d = i3 | 16448;
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final InterfaceC1784Y trySplit() {
        int i = this.f947b;
        int i2 = (this.f948c + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        this.f947b = i2;
        return new C1849p0(this.f946a, i, i2, this.f949d);
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(LongConsumer longConsumer) {
        int i;
        longConsumer.getClass();
        long[] jArr = this.f946a;
        int length = jArr.length;
        int i2 = this.f948c;
        if (length < i2 || (i = this.f947b) < 0) {
            return;
        }
        this.f947b = i2;
        if (i < i2) {
            do {
                longConsumer.accept(jArr[i]);
                i++;
            } while (i < i2);
        }
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(LongConsumer longConsumer) {
        longConsumer.getClass();
        int i = this.f947b;
        if (i < 0 || i >= this.f948c) {
            return false;
        }
        this.f947b = i + 1;
        longConsumer.accept(this.f946a[i]);
        return true;
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return this.f948c - this.f947b;
    }

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return this.f949d;
    }

    @Override // p017j$.util.Spliterator
    public final Comparator getComparator() {
        if (Spliterator.CC.$default$hasCharacteristics(this, 4)) {
            return null;
        }
        throw new IllegalStateException();
    }
}
