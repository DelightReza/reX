package p017j$.util.stream;

/* renamed from: j$.util.stream.c */
/* loaded from: classes2.dex */
public abstract class AbstractC1997c {

    /* renamed from: a */
    public final int f1212a;

    /* renamed from: b */
    public int f1213b;

    /* renamed from: c */
    public int f1214c;

    /* renamed from: d */
    public long[] f1215d;

    public abstract void clear();

    public AbstractC1997c() {
        this.f1212a = 4;
    }

    public AbstractC1997c(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + i);
        }
        this.f1212a = Math.max(4, 32 - Integer.numberOfLeadingZeros(i - 1));
    }

    public final long count() {
        int i = this.f1214c;
        if (i == 0) {
            return this.f1213b;
        }
        return this.f1215d[i] + this.f1213b;
    }
}
