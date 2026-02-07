package p017j$.util;

import java.util.Comparator;
import java.util.function.Consumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.h0 */
/* loaded from: classes2.dex */
public final class C1833h0 implements Spliterator {

    /* renamed from: a */
    public final Object[] f918a;

    /* renamed from: b */
    public int f919b;

    /* renamed from: c */
    public final int f920c;

    /* renamed from: d */
    public final int f921d;

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return Spliterator.CC.$default$getExactSizeIfKnown(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    public C1833h0(Object[] objArr, int i, int i2, int i3) {
        this.f918a = objArr;
        this.f919b = i;
        this.f920c = i2;
        this.f921d = i3 | 16448;
    }

    @Override // p017j$.util.Spliterator
    public final Spliterator trySplit() {
        int i = this.f919b;
        int i2 = (this.f920c + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        this.f919b = i2;
        return new C1833h0(this.f918a, i, i2, this.f921d);
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        int i;
        consumer.getClass();
        Object[] objArr = this.f918a;
        int length = objArr.length;
        int i2 = this.f920c;
        if (length < i2 || (i = this.f919b) < 0) {
            return;
        }
        this.f919b = i2;
        if (i < i2) {
            do {
                consumer.accept(objArr[i]);
                i++;
            } while (i < i2);
        }
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        consumer.getClass();
        int i = this.f919b;
        if (i < 0 || i >= this.f920c) {
            return false;
        }
        this.f919b = i + 1;
        consumer.accept(this.f918a[i]);
        return true;
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return this.f920c - this.f919b;
    }

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return this.f921d;
    }

    @Override // p017j$.util.Spliterator
    public final Comparator getComparator() {
        if (Spliterator.CC.$default$hasCharacteristics(this, 4)) {
            return null;
        }
        throw new IllegalStateException();
    }
}
