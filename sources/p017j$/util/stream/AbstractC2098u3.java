package p017j$.util.stream;

import java.util.Comparator;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.u3 */
/* loaded from: classes2.dex */
public abstract class AbstractC2098u3 extends AbstractC2108w3 implements InterfaceC1789b0 {
    /* renamed from: b */
    public abstract Object mo1064b();

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

    public AbstractC2098u3(InterfaceC1789b0 interfaceC1789b0, long j, long j2) {
        super(interfaceC1789b0, j, j2, 0L, Math.min(interfaceC1789b0.estimateSize(), j2));
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(Object obj) {
        long j;
        Objects.requireNonNull(obj);
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
            ((InterfaceC1789b0) this.f1372c).tryAdvance(mo1064b());
            this.f1373d++;
        }
        if (j >= this.f1374e) {
            return false;
        }
        this.f1373d = j + 1;
        return ((InterfaceC1789b0) this.f1372c).tryAdvance(obj);
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(Object obj) {
        Objects.requireNonNull(obj);
        long j = this.f1374e;
        long j2 = this.f1370a;
        if (j2 >= j) {
            return;
        }
        long j3 = this.f1373d;
        if (j3 >= j) {
            return;
        }
        if (j3 >= j2 && ((InterfaceC1789b0) this.f1372c).estimateSize() + j3 <= this.f1371b) {
            ((InterfaceC1789b0) this.f1372c).forEachRemaining(obj);
            this.f1373d = this.f1374e;
            return;
        }
        while (j2 > this.f1373d) {
            ((InterfaceC1789b0) this.f1372c).tryAdvance(mo1064b());
            this.f1373d++;
        }
        while (this.f1373d < this.f1374e) {
            ((InterfaceC1789b0) this.f1372c).tryAdvance(obj);
            this.f1373d++;
        }
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
        forEachRemaining((Object) intConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
        return tryAdvance((Object) intConsumer);
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
        forEachRemaining((Object) longConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
        return tryAdvance((Object) longConsumer);
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
        forEachRemaining((Object) doubleConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
        return tryAdvance((Object) doubleConsumer);
    }
}
