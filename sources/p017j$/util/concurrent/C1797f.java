package p017j$.util.concurrent;

import java.util.Comparator;
import java.util.function.Consumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.concurrent.f */
/* loaded from: classes2.dex */
public final class C1797f extends C1806o implements Spliterator {

    /* renamed from: i */
    public final ConcurrentHashMap f828i;

    /* renamed from: j */
    public long f829j;

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return 4353;
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

    public C1797f(C1802k[] c1802kArr, int i, int i2, int i3, long j, ConcurrentHashMap concurrentHashMap) {
        super(c1802kArr, i, i2, i3);
        this.f828i = concurrentHashMap;
        this.f829j = j;
    }

    @Override // p017j$.util.Spliterator
    public final Spliterator trySplit() {
        int i = this.f850f;
        int i2 = this.f851g;
        int i3 = (i + i2) >>> 1;
        if (i3 <= i) {
            return null;
        }
        C1802k[] c1802kArr = this.f845a;
        this.f851g = i3;
        long j = this.f829j >>> 1;
        this.f829j = j;
        return new C1797f(c1802kArr, this.f852h, i3, i2, j, this.f828i);
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        consumer.getClass();
        while (true) {
            C1802k c1802kM892a = m892a();
            if (c1802kM892a == null) {
                return;
            } else {
                consumer.m971v(new C1801j(c1802kM892a.f838b, c1802kM892a.f839c, this.f828i));
            }
        }
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        consumer.getClass();
        C1802k c1802kM892a = m892a();
        if (c1802kM892a == null) {
            return false;
        }
        consumer.m971v(new C1801j(c1802kM892a.f838b, c1802kM892a.f839c, this.f828i));
        return true;
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return this.f829j;
    }
}
