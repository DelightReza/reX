package p017j$.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Consumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.o0 */
/* loaded from: classes2.dex */
public class C1847o0 implements Spliterator {

    /* renamed from: a */
    public final Collection f940a;

    /* renamed from: b */
    public Iterator f941b = null;

    /* renamed from: c */
    public final int f942c;

    /* renamed from: d */
    public long f943d;

    /* renamed from: e */
    public int f944e;

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return Spliterator.CC.$default$getExactSizeIfKnown(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    public C1847o0(Collection collection, int i) {
        this.f940a = collection;
        this.f942c = (i & 4096) == 0 ? i | 16448 : i;
    }

    @Override // p017j$.util.Spliterator
    public final Spliterator trySplit() {
        long size;
        Iterator it = this.f941b;
        if (it == null) {
            it = this.f940a.iterator();
            this.f941b = it;
            size = this.f940a.size();
            this.f943d = size;
        } else {
            size = this.f943d;
        }
        if (size <= 1 || !it.hasNext()) {
            return null;
        }
        int i = this.f944e + 1024;
        if (i > size) {
            i = (int) size;
        }
        if (i > 33554432) {
            i = 33554432;
        }
        Object[] objArr = new Object[i];
        int i2 = 0;
        do {
            objArr[i2] = it.next();
            i2++;
            if (i2 >= i) {
                break;
            }
        } while (it.hasNext());
        this.f944e = i2;
        long j = this.f943d;
        if (j != Long.MAX_VALUE) {
            this.f943d = j - i2;
        }
        return new C1833h0(objArr, 0, i2, this.f942c);
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        consumer.getClass();
        Iterator it = this.f941b;
        if (it == null) {
            it = this.f940a.iterator();
            this.f941b = it;
            this.f943d = this.f940a.size();
        }
        AbstractC1636a.m491J(it, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        consumer.getClass();
        if (this.f941b == null) {
            this.f941b = this.f940a.iterator();
            this.f943d = this.f940a.size();
        }
        if (!this.f941b.hasNext()) {
            return false;
        }
        consumer.accept(this.f941b.next());
        return true;
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        if (this.f941b == null) {
            this.f941b = this.f940a.iterator();
            long size = this.f940a.size();
            this.f943d = size;
            return size;
        }
        return this.f943d;
    }

    @Override // p017j$.util.Spliterator
    public final int characteristics() {
        return this.f942c;
    }

    @Override // p017j$.util.Spliterator
    public Comparator getComparator() {
        if (Spliterator.CC.$default$hasCharacteristics(this, 4)) {
            return null;
        }
        throw new IllegalStateException();
    }
}
