package p017j$.util;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.function.Consumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.a */
/* loaded from: classes2.dex */
public final class C1786a implements Spliterator {

    /* renamed from: a */
    public final List f795a;

    /* renamed from: b */
    public int f796b;

    /* renamed from: c */
    public int f797c;

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return 16464;
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

    public C1786a(List list) {
        this.f795a = list;
        this.f796b = 0;
        this.f797c = -1;
    }

    public C1786a(C1786a c1786a, int i, int i2) {
        this.f795a = c1786a.f795a;
        this.f796b = i;
        this.f797c = i2;
    }

    /* renamed from: a */
    public final int m865a() {
        List list = this.f795a;
        int i = this.f797c;
        if (i >= 0) {
            return i;
        }
        int size = list.size();
        this.f797c = size;
        return size;
    }

    @Override // p017j$.util.Spliterator
    public final Spliterator trySplit() {
        int iM865a = m865a();
        int i = this.f796b;
        int i2 = (iM865a + i) >>> 1;
        if (i >= i2) {
            return null;
        }
        this.f796b = i2;
        return new C1786a(this, i, i2);
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        consumer.getClass();
        int iM865a = m865a();
        int i = this.f796b;
        if (i >= iM865a) {
            return false;
        }
        this.f796b = i + 1;
        try {
            consumer.accept(this.f795a.get(i));
            return true;
        } catch (IndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        Objects.requireNonNull(consumer);
        List list = this.f795a;
        int iM865a = m865a();
        this.f796b = iM865a;
        for (int i = this.f796b; i < iM865a; i++) {
            try {
                consumer.accept(list.get(i));
            } catch (IndexOutOfBoundsException unused) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        return m865a() - this.f796b;
    }
}
