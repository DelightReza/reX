package p017j$.util.stream;

import java.util.Arrays;
import p017j$.lang.InterfaceC1637a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.Y2 */
/* loaded from: classes2.dex */
public abstract class AbstractC1978Y2 extends AbstractC1997c implements Iterable, InterfaceC1637a {

    /* renamed from: e */
    public Object f1149e;

    /* renamed from: f */
    public Object[] f1150f;

    /* renamed from: j */
    public abstract void mo994j(Object obj, int i, int i2, Object obj2);

    /* renamed from: k */
    public abstract int mo995k(Object obj);

    public abstract Object newArray(int i);

    /* renamed from: r */
    public abstract Object[] mo996r();

    public abstract Spliterator spliterator();

    @Override // java.lang.Iterable
    public final /* synthetic */ java.util.Spliterator spliterator() {
        return Spliterator.Wrapper.convert(spliterator());
    }

    public AbstractC1978Y2(int i) {
        super(i);
        this.f1149e = newArray(1 << this.f1212a);
    }

    public AbstractC1978Y2() {
        this.f1149e = newArray(16);
    }

    /* renamed from: p */
    public final void m1005p(long j) {
        long jMo995k;
        int i = this.f1214c;
        if (i == 0) {
            jMo995k = mo995k(this.f1149e);
        } else {
            jMo995k = mo995k(this.f1150f[i]) + this.f1215d[i];
        }
        if (j > jMo995k) {
            if (this.f1150f == null) {
                Object[] objArrMo996r = mo996r();
                this.f1150f = objArrMo996r;
                this.f1215d = new long[8];
                objArrMo996r[0] = this.f1149e;
            }
            int i2 = this.f1214c + 1;
            while (j > jMo995k) {
                Object[] objArr = this.f1150f;
                if (i2 >= objArr.length) {
                    int length = objArr.length * 2;
                    this.f1150f = Arrays.copyOf(objArr, length);
                    this.f1215d = Arrays.copyOf(this.f1215d, length);
                }
                int iMin = this.f1212a;
                if (i2 != 0 && i2 != 1) {
                    iMin = Math.min((iMin + i2) - 1, 30);
                }
                int i3 = 1 << iMin;
                this.f1150f[i2] = newArray(i3);
                long[] jArr = this.f1215d;
                jArr[i2] = jArr[i2 - 1] + mo995k(this.f1150f[r6]);
                jMo995k += i3;
                i2++;
            }
        }
    }

    /* renamed from: o */
    public final int m1004o(long j) {
        if (this.f1214c == 0) {
            if (j < this.f1213b) {
                return 0;
            }
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        if (j >= count()) {
            throw new IndexOutOfBoundsException(Long.toString(j));
        }
        for (int i = 0; i <= this.f1214c; i++) {
            if (j < this.f1215d[i] + mo995k(this.f1150f[i])) {
                return i;
            }
        }
        throw new IndexOutOfBoundsException(Long.toString(j));
    }

    /* renamed from: c */
    public void mo952c(int i, Object obj) {
        long j = i;
        long jCount = count() + j;
        if (jCount > mo995k(obj) || jCount < j) {
            throw new IndexOutOfBoundsException("does not fit");
        }
        if (this.f1214c == 0) {
            System.arraycopy(this.f1149e, 0, obj, i, this.f1213b);
            return;
        }
        for (int i2 = 0; i2 < this.f1214c; i2++) {
            Object obj2 = this.f1150f[i2];
            System.arraycopy(obj2, 0, obj, i, mo995k(obj2));
            i += mo995k(this.f1150f[i2]);
        }
        int i3 = this.f1213b;
        if (i3 > 0) {
            System.arraycopy(this.f1149e, 0, obj, i, i3);
        }
    }

    /* renamed from: b */
    public Object mo951b() {
        long jCount = count();
        if (jCount >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object objNewArray = newArray((int) jCount);
        mo952c(0, objNewArray);
        return objNewArray;
    }

    /* renamed from: s */
    public final void m1006s() {
        long jMo995k;
        if (this.f1213b == mo995k(this.f1149e)) {
            if (this.f1150f == null) {
                Object[] objArrMo996r = mo996r();
                this.f1150f = objArrMo996r;
                this.f1215d = new long[8];
                objArrMo996r[0] = this.f1149e;
            }
            int i = this.f1214c;
            int i2 = i + 1;
            Object[] objArr = this.f1150f;
            if (i2 >= objArr.length || objArr[i2] == null) {
                if (i == 0) {
                    jMo995k = mo995k(this.f1149e);
                } else {
                    jMo995k = mo995k(objArr[i]) + this.f1215d[i];
                }
                m1005p(jMo995k + 1);
            }
            this.f1213b = 0;
            int i3 = this.f1214c + 1;
            this.f1214c = i3;
            this.f1149e = this.f1150f[i3];
        }
    }

    @Override // p017j$.util.stream.AbstractC1997c
    public final void clear() {
        Object[] objArr = this.f1150f;
        if (objArr != null) {
            this.f1149e = objArr[0];
            this.f1150f = null;
            this.f1215d = null;
        }
        this.f1213b = 0;
        this.f1214c = 0;
    }

    /* renamed from: d */
    public void mo953d(Object obj) {
        for (int i = 0; i < this.f1214c; i++) {
            Object obj2 = this.f1150f[i];
            mo994j(obj2, 0, mo995k(obj2), obj);
        }
        mo994j(this.f1149e, 0, this.f1213b, obj);
    }
}
