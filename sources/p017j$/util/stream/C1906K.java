package p017j$.util.stream;

import java.util.concurrent.CountedCompleter;
import java.util.concurrent.atomic.AtomicReference;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.K */
/* loaded from: classes2.dex */
public final class C1906K extends AbstractC1991b {

    /* renamed from: j */
    public final C1876E f1046j;

    /* renamed from: k */
    public final boolean f1047k;

    public C1906K(C1876E c1876e, boolean z, AbstractC1985a abstractC1985a, Spliterator spliterator) {
        super(abstractC1985a, spliterator);
        this.f1047k = z;
        this.f1046j = c1876e;
    }

    public C1906K(C1906K c1906k, Spliterator spliterator) {
        super(c1906k, spliterator);
        this.f1047k = c1906k.f1047k;
        this.f1046j = c1906k.f1046j;
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: c */
    public final AbstractC2003d mo973c(Spliterator spliterator) {
        return new C1906K(this, spliterator);
    }

    @Override // p017j$.util.stream.AbstractC1991b
    /* renamed from: h */
    public final Object mo974h() {
        return this.f1046j.f991b;
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: a */
    public final Object mo972a() {
        AbstractC2106w1 abstractC2106w1 = this.f1218a;
        InterfaceC1915L3 interfaceC1915L3 = (InterfaceC1915L3) this.f1046j.f993d.get();
        abstractC2106w1.mo1017t0(this.f1219b, interfaceC1915L3);
        Object obj = interfaceC1915L3.get();
        if (this.f1047k) {
            if (obj != null) {
                AbstractC2003d abstractC2003d = this;
                while (abstractC2003d != null) {
                    AbstractC2003d abstractC2003d2 = (AbstractC2003d) abstractC2003d.getCompleter();
                    if (abstractC2003d2 != null && abstractC2003d2.f1221d != abstractC2003d) {
                        m1024g();
                        return obj;
                    }
                    abstractC2003d = abstractC2003d2;
                }
                AtomicReference atomicReference = this.f1181h;
                while (!atomicReference.compareAndSet(null, obj) && atomicReference.get() == null) {
                }
                return obj;
            }
        } else if (obj != null) {
            AtomicReference atomicReference2 = this.f1181h;
            while (!atomicReference2.compareAndSet(null, obj) && atomicReference2.get() == null) {
            }
        }
        return null;
    }

    @Override // p017j$.util.stream.AbstractC2003d, java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        if (this.f1047k) {
            C1906K c1906k = (C1906K) this.f1221d;
            C1906K c1906k2 = null;
            while (true) {
                if (c1906k != c1906k2) {
                    Object objM1025i = c1906k.m1025i();
                    if (objM1025i != null && this.f1046j.f992c.test(objM1025i)) {
                        mo1023d(objM1025i);
                        AbstractC2003d abstractC2003d = this;
                        while (true) {
                            if (abstractC2003d != null) {
                                AbstractC2003d abstractC2003d2 = (AbstractC2003d) abstractC2003d.getCompleter();
                                if (abstractC2003d2 != null && abstractC2003d2.f1221d != abstractC2003d) {
                                    m1024g();
                                    break;
                                }
                                abstractC2003d = abstractC2003d2;
                            } else {
                                AtomicReference atomicReference = this.f1181h;
                                while (!atomicReference.compareAndSet(null, objM1025i) && atomicReference.get() == null) {
                                }
                            }
                        }
                    } else {
                        c1906k2 = c1906k;
                        c1906k = (C1906K) this.f1222e;
                    }
                } else {
                    break;
                }
            }
        }
        super.onCompletion(countedCompleter);
    }
}
