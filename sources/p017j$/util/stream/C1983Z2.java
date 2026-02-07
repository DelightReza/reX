package p017j$.util.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import p017j$.lang.InterfaceC1637a;
import p017j$.time.C1726t;
import p017j$.util.C1817d0;
import p017j$.util.Objects;
import p017j$.util.Spliterator;
import p017j$.util.function.Consumer$CC;

/* renamed from: j$.util.stream.Z2 */
/* loaded from: classes2.dex */
public class C1983Z2 extends AbstractC1997c implements Consumer, Iterable, InterfaceC1637a {

    /* renamed from: e */
    public Object[] f1155e = new Object[1 << 4];

    /* renamed from: f */
    public Object[][] f1156f;

    public final /* synthetic */ Consumer andThen(Consumer consumer) {
        return Consumer$CC.$default$andThen(this, consumer);
    }

    @Override // java.lang.Iterable
    public final /* synthetic */ Spliterator spliterator() {
        return Spliterator.Wrapper.convert(spliterator());
    }

    /* renamed from: j */
    public final void m1010j(long j) {
        long length;
        int i = this.f1214c;
        if (i == 0) {
            length = this.f1155e.length;
        } else {
            length = this.f1215d[i] + this.f1156f[i].length;
        }
        if (j > length) {
            if (this.f1156f == null) {
                Object[][] objArr = new Object[8][];
                this.f1156f = objArr;
                this.f1215d = new long[8];
                objArr[0] = this.f1155e;
            }
            int i2 = i + 1;
            while (j > length) {
                Object[][] objArr2 = this.f1156f;
                if (i2 >= objArr2.length) {
                    int length2 = objArr2.length * 2;
                    this.f1156f = (Object[][]) Arrays.copyOf(objArr2, length2);
                    this.f1215d = Arrays.copyOf(this.f1215d, length2);
                }
                int iMin = this.f1212a;
                if (i2 != 0 && i2 != 1) {
                    iMin = Math.min((iMin + i2) - 1, 30);
                }
                int i3 = 1 << iMin;
                this.f1156f[i2] = new Object[i3];
                long[] jArr = this.f1215d;
                jArr[i2] = jArr[i2 - 1] + r5[r7].length;
                length += i3;
                i2++;
            }
        }
    }

    @Override // p017j$.util.stream.AbstractC1997c
    public final void clear() {
        Object[][] objArr = this.f1156f;
        if (objArr != null) {
            this.f1155e = objArr[0];
            int i = 0;
            while (true) {
                Object[] objArr2 = this.f1155e;
                if (i >= objArr2.length) {
                    break;
                }
                objArr2[i] = null;
                i++;
            }
            this.f1156f = null;
            this.f1215d = null;
        } else {
            for (int i2 = 0; i2 < this.f1213b; i2++) {
                this.f1155e[i2] = null;
            }
        }
        this.f1213b = 0;
        this.f1214c = 0;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        p017j$.util.Spliterator spliterator = spliterator();
        Objects.requireNonNull(spliterator);
        return new C1817d0(spliterator);
    }

    @Override // java.lang.Iterable, p017j$.lang.InterfaceC1637a
    public void forEach(Consumer consumer) {
        for (int i = 0; i < this.f1214c; i++) {
            for (Object obj : this.f1156f[i]) {
                consumer.accept(obj);
            }
        }
        for (int i2 = 0; i2 < this.f1213b; i2++) {
            consumer.accept(this.f1155e[i2]);
        }
    }

    @Override // java.util.function.Consumer
    public void accept(Object obj) {
        long length;
        int i = this.f1213b;
        Object[] objArr = this.f1155e;
        if (i == objArr.length) {
            if (this.f1156f == null) {
                Object[][] objArr2 = new Object[8][];
                this.f1156f = objArr2;
                this.f1215d = new long[8];
                objArr2[0] = objArr;
            }
            int i2 = this.f1214c;
            int i3 = i2 + 1;
            Object[][] objArr3 = this.f1156f;
            if (i3 >= objArr3.length || objArr3[i3] == null) {
                if (i2 == 0) {
                    length = objArr.length;
                } else {
                    length = objArr3[i2].length + this.f1215d[i2];
                }
                m1010j(length + 1);
            }
            this.f1213b = 0;
            int i4 = this.f1214c + 1;
            this.f1214c = i4;
            this.f1155e = this.f1156f[i4];
        }
        Object[] objArr4 = this.f1155e;
        int i5 = this.f1213b;
        this.f1213b = i5 + 1;
        objArr4[i5] = obj;
    }

    public final String toString() {
        ArrayList arrayList = new ArrayList();
        Objects.requireNonNull(arrayList);
        forEach(new C1726t(12, arrayList));
        return "SpinedBuffer:" + arrayList.toString();
    }

    @Override // java.lang.Iterable
    public p017j$.util.Spliterator spliterator() {
        return new C1939Q2(this, 0, this.f1214c, 0, this.f1213b);
    }
}
