package p017j$.util.stream;

import java.util.Comparator;
import java.util.function.Consumer;
import p017j$.util.C1833h0;
import p017j$.util.DesugarArrays;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.Q2 */
/* loaded from: classes2.dex */
public final class C1939Q2 implements Spliterator {

    /* renamed from: a */
    public int f1099a;

    /* renamed from: b */
    public final int f1100b;

    /* renamed from: c */
    public int f1101c;

    /* renamed from: d */
    public final int f1102d;

    /* renamed from: e */
    public Object[] f1103e;

    /* renamed from: f */
    public final /* synthetic */ C1983Z2 f1104f;

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

    public C1939Q2(C1983Z2 c1983z2, int i, int i2, int i3, int i4) {
        this.f1104f = c1983z2;
        this.f1099a = i;
        this.f1100b = i2;
        this.f1101c = i3;
        this.f1102d = i4;
        Object[][] objArr = c1983z2.f1156f;
        this.f1103e = objArr == null ? c1983z2.f1155e : objArr[i];
    }

    @Override // p017j$.util.Spliterator
    public final long estimateSize() {
        int i = this.f1099a;
        int i2 = this.f1102d;
        int i3 = this.f1100b;
        if (i == i3) {
            return i2 - this.f1101c;
        }
        long[] jArr = this.f1104f.f1215d;
        return ((jArr[i3] + i2) - jArr[i]) - this.f1101c;
    }

    @Override // p017j$.util.Spliterator
    public final boolean tryAdvance(Consumer consumer) {
        Objects.requireNonNull(consumer);
        int i = this.f1099a;
        int i2 = this.f1100b;
        if (i >= i2 && (i != i2 || this.f1101c >= this.f1102d)) {
            return false;
        }
        Object[] objArr = this.f1103e;
        int i3 = this.f1101c;
        this.f1101c = i3 + 1;
        consumer.accept(objArr[i3]);
        if (this.f1101c == this.f1103e.length) {
            this.f1101c = 0;
            int i4 = this.f1099a + 1;
            this.f1099a = i4;
            Object[][] objArr2 = this.f1104f.f1156f;
            if (objArr2 != null && i4 <= i2) {
                this.f1103e = objArr2[i4];
            }
        }
        return true;
    }

    @Override // p017j$.util.Spliterator
    public final void forEachRemaining(Consumer consumer) {
        C1983Z2 c1983z2;
        Objects.requireNonNull(consumer);
        int i = this.f1099a;
        int i2 = this.f1102d;
        int i3 = this.f1100b;
        if (i < i3 || (i == i3 && this.f1101c < i2)) {
            int i4 = this.f1101c;
            while (true) {
                c1983z2 = this.f1104f;
                if (i >= i3) {
                    break;
                }
                Object[] objArr = c1983z2.f1156f[i];
                while (i4 < objArr.length) {
                    consumer.accept(objArr[i4]);
                    i4++;
                }
                i++;
                i4 = 0;
            }
            Object[] objArr2 = this.f1099a == i3 ? this.f1103e : c1983z2.f1156f[i3];
            while (i4 < i2) {
                consumer.accept(objArr2[i4]);
                i4++;
            }
            this.f1099a = i3;
            this.f1101c = i2;
        }
    }

    @Override // p017j$.util.Spliterator
    public final Spliterator trySplit() {
        int i = this.f1099a;
        int i2 = this.f1100b;
        if (i < i2) {
            int i3 = i2 - 1;
            int i4 = this.f1101c;
            C1983Z2 c1983z2 = this.f1104f;
            C1939Q2 c1939q2 = new C1939Q2(c1983z2, i, i3, i4, c1983z2.f1156f[i3].length);
            this.f1099a = i2;
            this.f1101c = 0;
            this.f1103e = c1983z2.f1156f[i2];
            return c1939q2;
        }
        if (i != i2) {
            return null;
        }
        int i5 = this.f1101c;
        int i6 = (this.f1102d - i5) / 2;
        if (i6 == 0) {
            return null;
        }
        C1833h0 c1833h0M854a = DesugarArrays.m854a(this.f1103e, i5, i5 + i6);
        this.f1101c += i6;
        return c1833h0M854a;
    }
}
