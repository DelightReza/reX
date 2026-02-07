package p017j$.util;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.i0 */
/* loaded from: classes2.dex */
public final class C1835i0 implements InterfaceC1779T {

    /* renamed from: a */
    public final double[] f923a;

    /* renamed from: b */
    public int f924b;

    /* renamed from: c */
    public final int f925c;

    /* renamed from: d */
    public final int f926d;

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m513i(this, consumer);
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
        return AbstractC1636a.m527w(this, consumer);
    }

    public C1835i0(double[] dArr, int i, int i2, int i3) {
        this.f923a = dArr;
        this.f924b = i;
        this.f925c = i2;
        this.f926d = i3 | 16448;
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final InterfaceC1779T trySplit() {
        int i = this.f924b;
        int i2 = (this.f925c + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        this.f924b = i2;
        return new C1835i0(this.f923a, i, i2, this.f926d);
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(DoubleConsumer doubleConsumer) {
        int i;
        doubleConsumer.getClass();
        double[] dArr = this.f923a;
        int length = dArr.length;
        int i2 = this.f925c;
        if (length < i2 || (i = this.f924b) < 0) {
            return;
        }
        this.f924b = i2;
        if (i < i2) {
            do {
                doubleConsumer.accept(dArr[i]);
                i++;
            } while (i < i2);
        }
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(DoubleConsumer doubleConsumer) {
        doubleConsumer.getClass();
        int i = this.f924b;
        if (i < 0 || i >= this.f925c) {
            return false;
        }
        this.f924b = i + 1;
        doubleConsumer.accept(this.f923a[i]);
        return true;
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return this.f925c - this.f924b;
    }

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return this.f926d;
    }

    @Override // p017j$.util.Spliterator
    public final Comparator getComparator() {
        if (Spliterator.CC.$default$hasCharacteristics(this, 4)) {
            return null;
        }
        throw new IllegalStateException();
    }
}
