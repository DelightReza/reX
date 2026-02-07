package p017j$.util.concurrent;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.InterfaceC1779T;
import p017j$.util.Spliterator;

/* renamed from: j$.util.concurrent.v */
/* loaded from: classes2.dex */
public final class C1813v implements InterfaceC1779T {

    /* renamed from: a */
    public long f866a;

    /* renamed from: b */
    public final long f867b;

    /* renamed from: c */
    public final double f868c;

    /* renamed from: d */
    public final double f869d;

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return 17728;
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m513i(this, consumer);
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
        return AbstractC1636a.m527w(this, consumer);
    }

    public C1813v(long j, long j2, double d, double d2) {
        this.f866a = j;
        this.f867b = j2;
        this.f868c = d;
        this.f869d = d2;
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public final C1813v trySplit() {
        long j = this.f866a;
        long j2 = (this.f867b + j) >>> 1;
        if (j2 <= j) {
            return null;
        }
        this.f866a = j2;
        return new C1813v(j, j2, this.f868c, this.f869d);
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return this.f867b - this.f866a;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(DoubleConsumer doubleConsumer) {
        doubleConsumer.getClass();
        long j = this.f866a;
        if (j >= this.f867b) {
            return false;
        }
        doubleConsumer.accept(ThreadLocalRandom.current().m887a(this.f868c, this.f869d));
        this.f866a = j + 1;
        return true;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(DoubleConsumer doubleConsumer) {
        doubleConsumer.getClass();
        long j = this.f866a;
        long j2 = this.f867b;
        if (j < j2) {
            this.f866a = j2;
            ThreadLocalRandom threadLocalRandomCurrent = ThreadLocalRandom.current();
            do {
                doubleConsumer.accept(threadLocalRandomCurrent.m887a(this.f868c, this.f869d));
                j++;
            } while (j < j2);
        }
    }
}
