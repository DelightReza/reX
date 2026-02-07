package p017j$.util.stream;

import java.util.Comparator;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.X2 */
/* loaded from: classes2.dex */
public abstract class AbstractC1973X2 implements InterfaceC1789b0 {

    /* renamed from: a */
    public int f1139a;

    /* renamed from: b */
    public final int f1140b;

    /* renamed from: c */
    public int f1141c;

    /* renamed from: d */
    public final int f1142d;

    /* renamed from: e */
    public Object f1143e;

    /* renamed from: f */
    public final /* synthetic */ AbstractC1978Y2 f1144f;

    /* renamed from: a */
    public abstract void mo991a(int i, Object obj, Object obj2);

    /* renamed from: b */
    public abstract InterfaceC1789b0 mo992b(Object obj, int i, int i2);

    /* renamed from: c */
    public abstract InterfaceC1789b0 mo993c(int i, int i2, int i3, int i4);

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

    public AbstractC1973X2(AbstractC1978Y2 abstractC1978Y2, int i, int i2, int i3, int i4) {
        this.f1144f = abstractC1978Y2;
        this.f1139a = i;
        this.f1140b = i2;
        this.f1141c = i3;
        this.f1142d = i4;
        Object[] objArr = abstractC1978Y2.f1150f;
        this.f1143e = objArr == null ? abstractC1978Y2.f1149e : objArr[i];
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        int i = this.f1139a;
        int i2 = this.f1142d;
        int i3 = this.f1140b;
        if (i == i3) {
            return i2 - this.f1141c;
        }
        long[] jArr = this.f1144f.f1215d;
        return ((jArr[i3] + i2) - jArr[i]) - this.f1141c;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(Object obj) {
        Objects.requireNonNull(obj);
        int i = this.f1139a;
        int i2 = this.f1140b;
        if (i >= i2 && (i != i2 || this.f1141c >= this.f1142d)) {
            return false;
        }
        Object obj2 = this.f1143e;
        int i3 = this.f1141c;
        this.f1141c = i3 + 1;
        mo991a(i3, obj2, obj);
        int i4 = this.f1141c;
        Object obj3 = this.f1143e;
        AbstractC1978Y2 abstractC1978Y2 = this.f1144f;
        if (i4 == abstractC1978Y2.mo995k(obj3)) {
            this.f1141c = 0;
            int i5 = this.f1139a + 1;
            this.f1139a = i5;
            Object[] objArr = abstractC1978Y2.f1150f;
            if (objArr != null && i5 <= i2) {
                this.f1143e = objArr[i5];
            }
        }
        return true;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(Object obj) {
        AbstractC1978Y2 abstractC1978Y2;
        Objects.requireNonNull(obj);
        int i = this.f1139a;
        int i2 = this.f1142d;
        int i3 = this.f1140b;
        if (i < i3 || (i == i3 && this.f1141c < i2)) {
            int i4 = this.f1141c;
            while (true) {
                abstractC1978Y2 = this.f1144f;
                if (i >= i3) {
                    break;
                }
                Object obj2 = abstractC1978Y2.f1150f[i];
                abstractC1978Y2.mo994j(obj2, i4, abstractC1978Y2.mo995k(obj2), obj);
                i++;
                i4 = 0;
            }
            abstractC1978Y2.mo994j(this.f1139a == i3 ? this.f1143e : abstractC1978Y2.f1150f[i3], i4, i2, obj);
            this.f1139a = i3;
            this.f1141c = i2;
        }
    }

    @Override // p017j$.util.Spliterator
    public final InterfaceC1789b0 trySplit() {
        int i = this.f1139a;
        int i2 = this.f1140b;
        if (i < i2) {
            int i3 = i2 - 1;
            int i4 = this.f1141c;
            AbstractC1978Y2 abstractC1978Y2 = this.f1144f;
            InterfaceC1789b0 interfaceC1789b0Mo993c = mo993c(i, i3, i4, abstractC1978Y2.mo995k(abstractC1978Y2.f1150f[i3]));
            this.f1139a = i2;
            this.f1141c = 0;
            this.f1143e = abstractC1978Y2.f1150f[i2];
            return interfaceC1789b0Mo993c;
        }
        if (i != i2) {
            return null;
        }
        int i5 = this.f1141c;
        int i6 = (this.f1142d - i5) / 2;
        if (i6 == 0) {
            return null;
        }
        InterfaceC1789b0 interfaceC1789b0Mo992b = mo992b(this.f1143e, i5, i6);
        this.f1141c += i6;
        return interfaceC1789b0Mo992b;
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
        forEachRemaining((Object) intConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
        return tryAdvance((Object) intConsumer);
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
        return (Spliterator.OfInt) trySplit();
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
        forEachRemaining((Object) longConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
        return tryAdvance((Object) longConsumer);
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ InterfaceC1784Y trySplit() {
        return (InterfaceC1784Y) trySplit();
    }

    public /* bridge */ /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
        forEachRemaining((Object) doubleConsumer);
    }

    public /* bridge */ /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
        return tryAdvance((Object) doubleConsumer);
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public /* bridge */ /* synthetic */ InterfaceC1779T trySplit() {
        return (InterfaceC1779T) trySplit();
    }
}
