package p017j$.util;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.n0 */
/* loaded from: classes2.dex */
public final class C1845n0 implements Spliterator.OfInt {

    /* renamed from: a */
    public final int[] f935a;

    /* renamed from: b */
    public int f936b;

    /* renamed from: c */
    public final int f937c;

    /* renamed from: d */
    public final int f938d;

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m514j(this, consumer);
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

    public C1845n0(int[] iArr, int i, int i2, int i3) {
        this.f935a = iArr;
        this.f936b = i;
        this.f937c = i2;
        this.f938d = i3 | 16448;
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final Spliterator.OfInt trySplit() {
        int i = this.f936b;
        int i2 = (this.f937c + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        this.f936b = i2;
        return new C1845n0(this.f935a, i, i2, this.f938d);
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(IntConsumer intConsumer) {
        int i;
        intConsumer.getClass();
        int[] iArr = this.f935a;
        int length = iArr.length;
        int i2 = this.f937c;
        if (length < i2 || (i = this.f936b) < 0) {
            return;
        }
        this.f936b = i2;
        if (i < i2) {
            do {
                intConsumer.accept(iArr[i]);
                i++;
            } while (i < i2);
        }
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(IntConsumer intConsumer) {
        intConsumer.getClass();
        int i = this.f936b;
        if (i < 0 || i >= this.f937c) {
            return false;
        }
        this.f936b = i + 1;
        intConsumer.accept(this.f935a[i]);
        return true;
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return this.f937c - this.f936b;
    }

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return this.f938d;
    }

    @Override // p017j$.util.Spliterator
    public final Comparator getComparator() {
        if (Spliterator.CC.$default$hasCharacteristics(this, 4)) {
            return null;
        }
        throw new IllegalStateException();
    }
}
