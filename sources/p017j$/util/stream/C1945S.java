package p017j$.util.stream;

import java.util.concurrent.CountedCompleter;
import p017j$.util.Spliterator;
import p017j$.util.concurrent.ConcurrentHashMap;

/* renamed from: j$.util.stream.S */
/* loaded from: classes2.dex */
public final class C1945S extends CountedCompleter {

    /* renamed from: a */
    public final AbstractC2106w1 f1111a;

    /* renamed from: b */
    public Spliterator f1112b;

    /* renamed from: c */
    public final long f1113c;

    /* renamed from: d */
    public final ConcurrentHashMap f1114d;

    /* renamed from: e */
    public final AbstractC1936Q f1115e;

    /* renamed from: f */
    public final C1945S f1116f;

    /* renamed from: g */
    public InterfaceC1887G0 f1117g;

    public C1945S(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, AbstractC1936Q abstractC1936Q) {
        super(null);
        this.f1111a = abstractC2106w1;
        this.f1112b = spliterator;
        this.f1113c = AbstractC2003d.m1033e(spliterator.estimateSize());
        this.f1114d = new ConcurrentHashMap(Math.max(16, AbstractC2003d.f1217g << 1));
        this.f1115e = abstractC1936Q;
        this.f1116f = null;
    }

    public C1945S(C1945S c1945s, Spliterator spliterator, C1945S c1945s2) {
        super(c1945s);
        this.f1111a = c1945s.f1111a;
        this.f1112b = spliterator;
        this.f1113c = c1945s.f1113c;
        this.f1114d = c1945s.f1114d;
        this.f1115e = c1945s.f1115e;
        this.f1116f = c1945s2;
    }

    @Override // java.util.concurrent.CountedCompleter
    public final void compute() {
        Spliterator spliteratorTrySplit;
        Spliterator spliterator = this.f1112b;
        long j = this.f1113c;
        boolean z = false;
        C1945S c1945s = this;
        while (spliterator.estimateSize() > j && (spliteratorTrySplit = spliterator.trySplit()) != null) {
            C1945S c1945s2 = new C1945S(c1945s, spliteratorTrySplit, c1945s.f1116f);
            C1945S c1945s3 = new C1945S(c1945s, spliterator, c1945s2);
            c1945s.addToPendingCount(1);
            c1945s3.addToPendingCount(1);
            c1945s.f1114d.put(c1945s2, c1945s3);
            if (c1945s.f1116f != null) {
                c1945s2.addToPendingCount(1);
                if (c1945s.f1114d.replace(c1945s.f1116f, c1945s, c1945s2)) {
                    c1945s.addToPendingCount(-1);
                } else {
                    c1945s2.addToPendingCount(-1);
                }
            }
            if (z) {
                spliterator = spliteratorTrySplit;
                c1945s = c1945s2;
                c1945s2 = c1945s3;
            } else {
                c1945s = c1945s3;
            }
            z = !z;
            c1945s2.fork();
        }
        if (c1945s.getPendingCount() > 0) {
            C2044k c2044k = new C2044k(25);
            AbstractC2106w1 abstractC2106w1 = c1945s.f1111a;
            InterfaceC2115y0 interfaceC2115y0Mo925q0 = abstractC2106w1.mo925q0(abstractC2106w1.mo1016e0(spliterator), c2044k);
            c1945s.f1111a.mo1017t0(spliterator, interfaceC2115y0Mo925q0);
            c1945s.f1117g = interfaceC2115y0Mo925q0.build();
            c1945s.f1112b = null;
        }
        c1945s.tryComplete();
    }

    @Override // java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        InterfaceC1887G0 interfaceC1887G0 = this.f1117g;
        if (interfaceC1887G0 != null) {
            interfaceC1887G0.forEach(this.f1115e);
            this.f1117g = null;
        } else {
            Spliterator spliterator = this.f1112b;
            if (spliterator != null) {
                this.f1111a.mo1017t0(spliterator, this.f1115e);
                this.f1112b = null;
            }
        }
        C1945S c1945s = (C1945S) this.f1114d.remove(this);
        if (c1945s != null) {
            c1945s.tryComplete();
        }
    }
}
