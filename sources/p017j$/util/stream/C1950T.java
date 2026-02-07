package p017j$.util.stream;

import java.util.concurrent.CountedCompleter;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.T */
/* loaded from: classes2.dex */
public final class C1950T extends CountedCompleter {

    /* renamed from: a */
    public Spliterator f1124a;

    /* renamed from: b */
    public final InterfaceC2062n2 f1125b;

    /* renamed from: c */
    public final AbstractC2106w1 f1126c;

    /* renamed from: d */
    public long f1127d;

    public C1950T(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2) {
        super(null);
        this.f1125b = interfaceC2062n2;
        this.f1126c = abstractC2106w1;
        this.f1124a = spliterator;
        this.f1127d = 0L;
    }

    public C1950T(C1950T c1950t, Spliterator spliterator) {
        super(c1950t);
        this.f1124a = spliterator;
        this.f1125b = c1950t.f1125b;
        this.f1127d = c1950t.f1127d;
        this.f1126c = c1950t.f1126c;
    }

    @Override // java.util.concurrent.CountedCompleter
    public final void compute() {
        Spliterator spliteratorTrySplit;
        Spliterator spliterator = this.f1124a;
        long jEstimateSize = spliterator.estimateSize();
        long jM1033e = this.f1127d;
        if (jM1033e == 0) {
            jM1033e = AbstractC2003d.m1033e(jEstimateSize);
            this.f1127d = jM1033e;
        }
        boolean zM1030n = EnumC1995b3.SHORT_CIRCUIT.m1030n(((AbstractC1985a) this.f1126c).f1167m);
        InterfaceC2062n2 interfaceC2062n2 = this.f1125b;
        boolean z = false;
        C1950T c1950t = this;
        while (true) {
            if (zM1030n && interfaceC2062n2.mo932m()) {
                break;
            }
            if (jEstimateSize <= jM1033e || (spliteratorTrySplit = spliterator.trySplit()) == null) {
                break;
            }
            C1950T c1950t2 = new C1950T(c1950t, spliteratorTrySplit);
            c1950t.addToPendingCount(1);
            if (z) {
                spliterator = spliteratorTrySplit;
            } else {
                C1950T c1950t3 = c1950t;
                c1950t = c1950t2;
                c1950t2 = c1950t3;
            }
            z = !z;
            c1950t.fork();
            c1950t = c1950t2;
            jEstimateSize = spliterator.estimateSize();
        }
        c1950t.f1126c.mo1013Z(spliterator, interfaceC2062n2);
        c1950t.f1124a = null;
        c1950t.propagateCompletion();
    }
}
