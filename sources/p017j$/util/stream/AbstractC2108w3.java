package p017j$.util.stream;

import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.w3 */
/* loaded from: classes2.dex */
public abstract class AbstractC2108w3 {

    /* renamed from: a */
    public final long f1370a;

    /* renamed from: b */
    public final long f1371b;

    /* renamed from: c */
    public Spliterator f1372c;

    /* renamed from: d */
    public long f1373d;

    /* renamed from: e */
    public long f1374e;

    /* renamed from: a */
    public abstract Spliterator mo1063a(Spliterator spliterator, long j, long j2, long j3, long j4);

    public AbstractC2108w3(Spliterator spliterator, long j, long j2, long j3, long j4) {
        this.f1372c = spliterator;
        this.f1370a = j;
        this.f1371b = j2;
        this.f1373d = j3;
        this.f1374e = j4;
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public final Spliterator m2956trySplit() {
        long j = this.f1374e;
        if (this.f1370a >= j || this.f1373d >= j) {
            return null;
        }
        while (true) {
            Spliterator spliteratorTrySplit = this.f1372c.trySplit();
            if (spliteratorTrySplit == null) {
                return null;
            }
            long jEstimateSize = spliteratorTrySplit.estimateSize() + this.f1373d;
            long jMin = Math.min(jEstimateSize, this.f1371b);
            long j2 = this.f1370a;
            if (j2 >= jMin) {
                this.f1373d = jMin;
            } else {
                long j3 = this.f1371b;
                if (jMin >= j3) {
                    this.f1372c = spliteratorTrySplit;
                    this.f1374e = jMin;
                } else {
                    long j4 = this.f1373d;
                    if (j4 >= j2 && jEstimateSize <= j3) {
                        this.f1373d = jMin;
                        return spliteratorTrySplit;
                    }
                    this.f1373d = jMin;
                    return mo1063a(spliteratorTrySplit, j2, j3, j4, jMin);
                }
            }
        }
    }

    public final long estimateSize() {
        long j = this.f1374e;
        long j2 = this.f1370a;
        if (j2 < j) {
            return j - Math.max(j2, this.f1373d);
        }
        return 0L;
    }

    public final int characteristics() {
        return this.f1372c.characteristics();
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ InterfaceC1789b0 m2959trySplit() {
        return (InterfaceC1789b0) m2956trySplit();
    }

    public /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
        return (Spliterator.OfInt) m2956trySplit();
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ InterfaceC1784Y m2958trySplit() {
        return (InterfaceC1784Y) m2956trySplit();
    }

    /* renamed from: trySplit, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ InterfaceC1779T m2957trySplit() {
        return (InterfaceC1779T) m2956trySplit();
    }
}
