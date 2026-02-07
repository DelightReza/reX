package p017j$.util.stream;

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.ForkJoinPool;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.d */
/* loaded from: classes2.dex */
public abstract class AbstractC2003d extends CountedCompleter {

    /* renamed from: g */
    public static final int f1217g = ForkJoinPool.getCommonPoolParallelism() << 2;

    /* renamed from: a */
    public final AbstractC2106w1 f1218a;

    /* renamed from: b */
    public Spliterator f1219b;

    /* renamed from: c */
    public long f1220c;

    /* renamed from: d */
    public AbstractC2003d f1221d;

    /* renamed from: e */
    public AbstractC2003d f1222e;

    /* renamed from: f */
    public Object f1223f;

    /* renamed from: a */
    public abstract Object mo972a();

    /* renamed from: c */
    public abstract AbstractC2003d mo973c(Spliterator spliterator);

    public AbstractC2003d(AbstractC2106w1 abstractC2106w1, Spliterator spliterator) {
        super(null);
        this.f1218a = abstractC2106w1;
        this.f1219b = spliterator;
        this.f1220c = 0L;
    }

    public AbstractC2003d(AbstractC2003d abstractC2003d, Spliterator spliterator) {
        super(abstractC2003d);
        this.f1219b = spliterator;
        this.f1218a = abstractC2003d.f1218a;
        this.f1220c = abstractC2003d.f1220c;
    }

    /* renamed from: e */
    public static long m1033e(long j) {
        long j2 = j / f1217g;
        if (j2 > 0) {
            return j2;
        }
        return 1L;
    }

    @Override // java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    public Object getRawResult() {
        return this.f1223f;
    }

    @Override // java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    public final void setRawResult(Object obj) {
        if (obj != null) {
            throw new IllegalStateException();
        }
    }

    /* renamed from: d */
    public void mo1023d(Object obj) {
        this.f1223f = obj;
    }

    /* renamed from: b */
    public final boolean m1034b() {
        return ((AbstractC2003d) getCompleter()) == null;
    }

    @Override // java.util.concurrent.CountedCompleter
    public void compute() {
        Spliterator spliteratorTrySplit;
        Spliterator spliterator = this.f1219b;
        long jEstimateSize = spliterator.estimateSize();
        long jM1033e = this.f1220c;
        if (jM1033e == 0) {
            jM1033e = m1033e(jEstimateSize);
            this.f1220c = jM1033e;
        }
        boolean z = false;
        AbstractC2003d abstractC2003d = this;
        while (jEstimateSize > jM1033e && (spliteratorTrySplit = spliterator.trySplit()) != null) {
            AbstractC2003d abstractC2003dMo973c = abstractC2003d.mo973c(spliteratorTrySplit);
            abstractC2003d.f1221d = abstractC2003dMo973c;
            AbstractC2003d abstractC2003dMo973c2 = abstractC2003d.mo973c(spliterator);
            abstractC2003d.f1222e = abstractC2003dMo973c2;
            abstractC2003d.setPendingCount(1);
            if (z) {
                spliterator = spliteratorTrySplit;
                abstractC2003d = abstractC2003dMo973c;
                abstractC2003dMo973c = abstractC2003dMo973c2;
            } else {
                abstractC2003d = abstractC2003dMo973c2;
            }
            z = !z;
            abstractC2003dMo973c.fork();
            jEstimateSize = spliterator.estimateSize();
        }
        abstractC2003d.mo1023d(abstractC2003d.mo972a());
        abstractC2003d.tryComplete();
    }

    @Override // java.util.concurrent.CountedCompleter
    public void onCompletion(CountedCompleter countedCompleter) {
        this.f1219b = null;
        this.f1222e = null;
        this.f1221d = null;
    }
}
