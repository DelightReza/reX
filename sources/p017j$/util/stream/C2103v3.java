package p017j$.util.stream;

import java.util.Comparator;
import java.util.function.Consumer;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.v3 */
/* loaded from: classes2.dex */
public final class C2103v3 extends AbstractC2108w3 implements Spliterator {
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

    public C2103v3(Spliterator spliterator, long j, long j2) {
        super(spliterator, j, j2, 0L, Math.min(spliterator.estimateSize(), j2));
    }

    @Override // p017j$.util.stream.AbstractC2108w3
    /* renamed from: a */
    public final Spliterator mo1063a(Spliterator spliterator, long j, long j2, long j3, long j4) {
        return new C2103v3(spliterator, j, j2, j3, j4);
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        long j;
        Objects.requireNonNull(consumer);
        long j2 = this.f1374e;
        long j3 = this.f1370a;
        if (j3 >= j2) {
            return false;
        }
        while (true) {
            j = this.f1373d;
            if (j3 <= j) {
                break;
            }
            this.f1372c.tryAdvance(new C2097u2(1));
            this.f1373d++;
        }
        if (j >= this.f1374e) {
            return false;
        }
        this.f1373d = j + 1;
        return this.f1372c.tryAdvance(consumer);
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        Objects.requireNonNull(consumer);
        long j = this.f1374e;
        long j2 = this.f1370a;
        if (j2 >= j) {
            return;
        }
        long j3 = this.f1373d;
        if (j3 >= j) {
            return;
        }
        if (j3 >= j2 && this.f1372c.estimateSize() + j3 <= this.f1371b) {
            this.f1372c.forEachRemaining(consumer);
            this.f1373d = this.f1374e;
            return;
        }
        while (j2 > this.f1373d) {
            this.f1372c.tryAdvance(new C2097u2(2));
            this.f1373d++;
        }
        while (this.f1373d < this.f1374e) {
            this.f1372c.tryAdvance(consumer);
            this.f1373d++;
        }
    }
}
