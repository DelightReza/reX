package p017j$.util.stream;

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicReference;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.b */
/* loaded from: classes2.dex */
public abstract class AbstractC1991b extends AbstractC2003d {

    /* renamed from: h */
    public final AtomicReference f1181h;

    /* renamed from: i */
    public volatile boolean f1182i;

    /* renamed from: h */
    public abstract Object mo974h();

    public AbstractC1991b(AbstractC2106w1 abstractC2106w1, Spliterator spliterator) {
        super(abstractC2106w1, spliterator);
        this.f1181h = new AtomicReference(null);
    }

    public AbstractC1991b(AbstractC1991b abstractC1991b, Spliterator spliterator) {
        super(abstractC1991b, spliterator);
        this.f1181h = abstractC1991b.f1181h;
    }

    @Override // p017j$.util.stream.AbstractC2003d, java.util.concurrent.CountedCompleter
    public final void compute() {
        Object objMo974h;
        Spliterator spliteratorTrySplit;
        Spliterator spliterator = this.f1219b;
        long jEstimateSize = spliterator.estimateSize();
        long jM1033e = this.f1220c;
        if (jM1033e == 0) {
            jM1033e = AbstractC2003d.m1033e(jEstimateSize);
            this.f1220c = jM1033e;
        }
        AtomicReference atomicReference = this.f1181h;
        boolean z = false;
        AbstractC1991b abstractC1991b = this;
        while (true) {
            objMo974h = atomicReference.get();
            if (objMo974h != null) {
                break;
            }
            boolean z2 = abstractC1991b.f1182i;
            if (!z2) {
                CountedCompleter<?> completer = abstractC1991b.getCompleter();
                while (true) {
                    AbstractC1991b abstractC1991b2 = (AbstractC1991b) ((AbstractC2003d) completer);
                    if (z2 || abstractC1991b2 == null) {
                        break;
                    }
                    z2 = abstractC1991b2.f1182i;
                    completer = abstractC1991b2.getCompleter();
                }
            }
            if (z2) {
                objMo974h = abstractC1991b.mo974h();
                break;
            }
            if (jEstimateSize <= jM1033e || (spliteratorTrySplit = spliterator.trySplit()) == null) {
                break;
            }
            AbstractC1991b abstractC1991b3 = (AbstractC1991b) abstractC1991b.mo973c(spliteratorTrySplit);
            abstractC1991b.f1221d = abstractC1991b3;
            AbstractC1991b abstractC1991b4 = (AbstractC1991b) abstractC1991b.mo973c(spliterator);
            abstractC1991b.f1222e = abstractC1991b4;
            abstractC1991b.setPendingCount(1);
            if (z) {
                spliterator = spliteratorTrySplit;
                abstractC1991b = abstractC1991b3;
                abstractC1991b3 = abstractC1991b4;
            } else {
                abstractC1991b = abstractC1991b4;
            }
            z = !z;
            abstractC1991b3.fork();
            jEstimateSize = spliterator.estimateSize();
        }
        objMo974h = abstractC1991b.mo972a();
        abstractC1991b.mo1023d(objMo974h);
        abstractC1991b.tryComplete();
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: d */
    public final void mo1023d(Object obj) {
        if (!m1034b()) {
            this.f1223f = obj;
        } else if (obj != null) {
            AtomicReference atomicReference = this.f1181h;
            while (!atomicReference.compareAndSet(null, obj) && atomicReference.get() == null) {
            }
        }
    }

    @Override // p017j$.util.stream.AbstractC2003d, java.util.concurrent.CountedCompleter, java.util.concurrent.ForkJoinTask
    public final Object getRawResult() {
        return m1025i();
    }

    /* renamed from: i */
    public final Object m1025i() {
        if (m1034b()) {
            Object obj = this.f1181h.get();
            return obj == null ? mo974h() : obj;
        }
        return this.f1223f;
    }

    /* renamed from: f */
    public void mo1022f() {
        this.f1182i = true;
    }

    /* renamed from: g */
    public final void m1024g() {
        AbstractC1991b abstractC1991b = this;
        for (AbstractC1991b abstractC1991b2 = (AbstractC1991b) ((AbstractC2003d) getCompleter()); abstractC1991b2 != null; abstractC1991b2 = (AbstractC1991b) ((AbstractC2003d) abstractC1991b2.getCompleter())) {
            if (abstractC1991b2.f1221d == abstractC1991b) {
                AbstractC1991b abstractC1991b3 = (AbstractC1991b) abstractC1991b2.f1222e;
                if (!abstractC1991b3.f1182i) {
                    abstractC1991b3.mo1022f();
                }
            }
            abstractC1991b = abstractC1991b2;
        }
    }
}
