package p017j$.util.stream;

import java.util.concurrent.CountedCompleter;
import java.util.function.IntFunction;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.y2 */
/* loaded from: classes2.dex */
public final class C2117y2 extends AbstractC1991b {

    /* renamed from: j */
    public final AbstractC1985a f1382j;

    /* renamed from: k */
    public final IntFunction f1383k;

    /* renamed from: l */
    public final long f1384l;

    /* renamed from: m */
    public final long f1385m;

    /* renamed from: n */
    public long f1386n;

    /* renamed from: o */
    public volatile boolean f1387o;

    @Override // p017j$.util.stream.AbstractC1991b
    /* renamed from: f */
    public final void mo1022f() {
        this.f1182i = true;
        if (this.f1387o) {
            mo1023d(AbstractC2106w1.m1094c0(this.f1382j.mo916A0()));
        }
    }

    @Override // p017j$.util.stream.AbstractC2003d, java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        C2117y2 c2117y2;
        InterfaceC1887G0 interfaceC1887G0M1094c0;
        AbstractC2003d abstractC2003d = this.f1221d;
        if (abstractC2003d != null) {
            this.f1386n = ((C2117y2) abstractC2003d).f1386n + ((C2117y2) this.f1222e).f1386n;
            if (this.f1182i) {
                this.f1386n = 0L;
                interfaceC1887G0M1094c0 = AbstractC2106w1.m1094c0(this.f1382j.mo916A0());
            } else {
                interfaceC1887G0M1094c0 = this.f1386n == 0 ? AbstractC2106w1.m1094c0(this.f1382j.mo916A0()) : ((C2117y2) this.f1221d).f1386n == 0 ? (InterfaceC1887G0) ((C2117y2) this.f1222e).m1025i() : AbstractC2106w1.m1092Y(this.f1382j.mo916A0(), (InterfaceC1887G0) ((C2117y2) this.f1221d).m1025i(), (InterfaceC1887G0) ((C2117y2) this.f1222e).m1025i());
            }
            InterfaceC1887G0 interfaceC1887G0Mo956e = interfaceC1887G0M1094c0;
            if (m1034b()) {
                interfaceC1887G0Mo956e = interfaceC1887G0Mo956e.mo956e(this.f1384l, this.f1385m >= 0 ? Math.min(interfaceC1887G0Mo956e.count(), this.f1384l + this.f1385m) : this.f1386n, this.f1383k);
            }
            mo1023d(interfaceC1887G0Mo956e);
            this.f1387o = true;
        }
        if (this.f1385m >= 0 && !m1034b()) {
            long j = this.f1384l + this.f1385m;
            long jM1108j = this.f1387o ? this.f1386n : m1108j(j);
            if (jM1108j >= j) {
                m1024g();
            } else {
                C2117y2 c2117y22 = (C2117y2) ((AbstractC2003d) getCompleter());
                C2117y2 c2117y23 = this;
                while (true) {
                    if (c2117y22 == null) {
                        if (jM1108j >= j) {
                            break;
                        }
                    } else {
                        if (c2117y23 == c2117y22.f1222e && (c2117y2 = (C2117y2) c2117y22.f1221d) != null) {
                            long jM1108j2 = c2117y2.m1108j(j) + jM1108j;
                            if (jM1108j2 >= j) {
                                break;
                            } else {
                                jM1108j = jM1108j2;
                            }
                        }
                        c2117y23 = c2117y22;
                        c2117y22 = (C2117y2) ((AbstractC2003d) c2117y22.getCompleter());
                    }
                }
                m1024g();
            }
        }
        super.onCompletion(countedCompleter);
    }

    public C2117y2(AbstractC1985a abstractC1985a, AbstractC2106w1 abstractC2106w1, Spliterator spliterator, IntFunction intFunction, long j, long j2) {
        super(abstractC2106w1, spliterator);
        this.f1382j = abstractC1985a;
        this.f1383k = intFunction;
        this.f1384l = j;
        this.f1385m = j2;
    }

    public C2117y2(C2117y2 c2117y2, Spliterator spliterator) {
        super(c2117y2, spliterator);
        this.f1382j = c2117y2.f1382j;
        this.f1383k = c2117y2.f1383k;
        this.f1384l = c2117y2.f1384l;
        this.f1385m = c2117y2.f1385m;
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: c */
    public final AbstractC2003d mo973c(Spliterator spliterator) {
        return new C2117y2(this, spliterator);
    }

    @Override // p017j$.util.stream.AbstractC1991b
    /* renamed from: h */
    public final Object mo974h() {
        return AbstractC2106w1.m1094c0(this.f1382j.mo916A0());
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: a */
    public final Object mo972a() {
        if (m1034b()) {
            EnumC1995b3 enumC1995b3 = EnumC1995b3.SIZED;
            AbstractC1985a abstractC1985a = this.f1382j;
            int i = abstractC1985a.f1164j;
            int i2 = enumC1995b3.f1209e;
            InterfaceC2115y0 interfaceC2115y0Mo925q0 = this.f1382j.mo925q0((i & i2) == i2 ? abstractC1985a.mo1016e0(this.f1219b) : -1L, this.f1383k);
            InterfaceC2062n2 interfaceC2062n2Mo964E0 = this.f1382j.mo964E0(((AbstractC1985a) this.f1218a).f1167m, interfaceC2115y0Mo925q0);
            AbstractC2106w1 abstractC2106w1 = this.f1218a;
            abstractC2106w1.mo1014a0(this.f1219b, abstractC2106w1.mo1018u0(interfaceC2062n2Mo964E0));
            return interfaceC2115y0Mo925q0.build();
        }
        InterfaceC2115y0 interfaceC2115y0Mo925q02 = this.f1382j.mo925q0(-1L, this.f1383k);
        if (this.f1384l == 0) {
            InterfaceC2062n2 interfaceC2062n2Mo964E02 = this.f1382j.mo964E0(((AbstractC1985a) this.f1218a).f1167m, interfaceC2115y0Mo925q02);
            AbstractC2106w1 abstractC2106w12 = this.f1218a;
            abstractC2106w12.mo1014a0(this.f1219b, abstractC2106w12.mo1018u0(interfaceC2062n2Mo964E02));
        } else {
            this.f1218a.mo1017t0(this.f1219b, interfaceC2115y0Mo925q02);
        }
        InterfaceC1887G0 interfaceC1887G0Build = interfaceC2115y0Mo925q02.build();
        this.f1386n = interfaceC1887G0Build.count();
        this.f1387o = true;
        this.f1219b = null;
        return interfaceC1887G0Build;
    }

    /* renamed from: j */
    public final long m1108j(long j) {
        if (this.f1387o) {
            return this.f1386n;
        }
        C2117y2 c2117y2 = (C2117y2) this.f1221d;
        C2117y2 c2117y22 = (C2117y2) this.f1222e;
        if (c2117y2 == null || c2117y22 == null) {
            return this.f1386n;
        }
        long jM1108j = c2117y2.m1108j(j);
        return jM1108j >= j ? jM1108j : c2117y22.m1108j(j) + jM1108j;
    }
}
