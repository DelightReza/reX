package p017j$.util.concurrent;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.concurrent.w */
/* loaded from: classes2.dex */
public final class C1814w implements Spliterator.OfInt {

    /* renamed from: a */
    public long f870a;

    /* renamed from: b */
    public final long f871b;

    /* renamed from: c */
    public final int f872c;

    /* renamed from: d */
    public final int f873d;

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return 17728;
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m514j(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return Spliterator.CC.$default$getComparator(this);
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

    public C1814w(long j, long j2, int i, int i2) {
        this.f870a = j;
        this.f871b = j2;
        this.f872c = i;
        this.f873d = i2;
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C1814w trySplit() {
        long j = this.f870a;
        long j2 = (this.f871b + j) >>> 1;
        if (j2 <= j) {
            return null;
        }
        this.f870a = j2;
        return new C1814w(j, j2, this.f872c, this.f873d);
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return this.f871b - this.f870a;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(IntConsumer intConsumer) {
        intConsumer.getClass();
        long j = this.f870a;
        if (j >= this.f871b) {
            return false;
        }
        intConsumer.accept(ThreadLocalRandom.current().m888b(this.f872c, this.f873d));
        this.f870a = j + 1;
        return true;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(IntConsumer intConsumer) {
        intConsumer.getClass();
        long j = this.f870a;
        long j2 = this.f871b;
        if (j < j2) {
            this.f870a = j2;
            ThreadLocalRandom threadLocalRandomCurrent = ThreadLocalRandom.current();
            do {
                intConsumer.accept(threadLocalRandomCurrent.m888b(this.f872c, this.f873d));
                j++;
            } while (j < j2);
        }
    }
}
