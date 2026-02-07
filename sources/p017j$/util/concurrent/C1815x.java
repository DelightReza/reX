package p017j$.util.concurrent;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Spliterator;

/* renamed from: j$.util.concurrent.x */
/* loaded from: classes2.dex */
public final class C1815x implements InterfaceC1784Y {

    /* renamed from: a */
    public long f874a;

    /* renamed from: b */
    public final long f875b;

    /* renamed from: c */
    public final long f876c;

    /* renamed from: d */
    public final long f877d;

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return 17728;
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m515k(this, consumer);
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
        return AbstractC1636a.m529y(this, consumer);
    }

    public C1815x(long j, long j2, long j3, long j4) {
        this.f874a = j;
        this.f875b = j2;
        this.f876c = j3;
        this.f877d = j4;
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C1815x trySplit() {
        long j = this.f874a;
        long j2 = (this.f875b + j) >>> 1;
        if (j2 <= j) {
            return null;
        }
        this.f874a = j2;
        return new C1815x(j, j2, this.f876c, this.f877d);
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return this.f875b - this.f874a;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(LongConsumer longConsumer) {
        longConsumer.getClass();
        long j = this.f874a;
        if (j >= this.f875b) {
            return false;
        }
        longConsumer.accept(ThreadLocalRandom.current().m889c(this.f876c, this.f877d));
        this.f874a = j + 1;
        return true;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(LongConsumer longConsumer) {
        longConsumer.getClass();
        long j = this.f874a;
        long j2 = this.f875b;
        if (j < j2) {
            this.f874a = j2;
            ThreadLocalRandom threadLocalRandomCurrent = ThreadLocalRandom.current();
            do {
                longConsumer.accept(threadLocalRandomCurrent.m889c(this.f876c, this.f877d));
                j++;
            } while (j < j2);
        }
    }
}
