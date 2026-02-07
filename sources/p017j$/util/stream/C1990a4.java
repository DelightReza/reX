package p017j$.util.stream;

import java.util.function.IntFunction;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.a4 */
/* loaded from: classes2.dex */
public final class C1990a4 extends AbstractC1991b {

    /* renamed from: j */
    public final AbstractC1985a f1175j;

    /* renamed from: k */
    public final IntFunction f1176k;

    /* renamed from: l */
    public final boolean f1177l;

    /* renamed from: m */
    public long f1178m;

    /* renamed from: n */
    public boolean f1179n;

    /* renamed from: o */
    public volatile boolean f1180o;

    @Override // p017j$.util.stream.AbstractC1991b
    /* renamed from: f */
    public final void mo1022f() {
        this.f1182i = true;
        if (this.f1177l && this.f1180o) {
            mo1023d(AbstractC2106w1.m1094c0(this.f1175j.mo916A0()));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0041  */
    @Override // p017j$.util.stream.AbstractC2003d, java.util.concurrent.CountedCompleter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void onCompletion(java.util.concurrent.CountedCompleter r9) {
        /*
            r8 = this;
            j$.util.stream.d r0 = r8.f1221d
            if (r0 != 0) goto L6
            goto L8b
        L6:
            j$.util.stream.a4 r0 = (p017j$.util.stream.C1990a4) r0
            boolean r0 = r0.f1179n
            j$.util.stream.d r1 = r8.f1222e
            j$.util.stream.a4 r1 = (p017j$.util.stream.C1990a4) r1
            boolean r1 = r1.f1179n
            r0 = r0 | r1
            r8.f1179n = r0
            boolean r0 = r8.f1177l
            r1 = 0
            if (r0 == 0) goto L2a
            boolean r0 = r8.f1182i
            if (r0 == 0) goto L2a
            r8.f1178m = r1
            j$.util.stream.a r0 = r8.f1175j
            j$.util.stream.c3 r0 = r0.mo916A0()
            j$.util.stream.Z0 r0 = p017j$.util.stream.AbstractC2106w1.m1094c0(r0)
            goto L88
        L2a:
            boolean r0 = r8.f1177l
            if (r0 == 0) goto L41
            j$.util.stream.d r0 = r8.f1221d
            j$.util.stream.a4 r0 = (p017j$.util.stream.C1990a4) r0
            boolean r3 = r0.f1179n
            if (r3 == 0) goto L41
            long r1 = r0.f1178m
            r8.f1178m = r1
            java.lang.Object r0 = r0.m1025i()
            j$.util.stream.G0 r0 = (p017j$.util.stream.InterfaceC1887G0) r0
            goto L88
        L41:
            j$.util.stream.d r0 = r8.f1221d
            j$.util.stream.a4 r0 = (p017j$.util.stream.C1990a4) r0
            long r3 = r0.f1178m
            j$.util.stream.d r5 = r8.f1222e
            j$.util.stream.a4 r5 = (p017j$.util.stream.C1990a4) r5
            long r6 = r5.f1178m
            long r3 = r3 + r6
            r8.f1178m = r3
            long r3 = r0.f1178m
            int r6 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r6 != 0) goto L5d
            java.lang.Object r0 = r5.m1025i()
            j$.util.stream.G0 r0 = (p017j$.util.stream.InterfaceC1887G0) r0
            goto L88
        L5d:
            long r3 = r5.f1178m
            int r5 = (r3 > r1 ? 1 : (r3 == r1 ? 0 : -1))
            if (r5 != 0) goto L6a
            java.lang.Object r0 = r0.m1025i()
            j$.util.stream.G0 r0 = (p017j$.util.stream.InterfaceC1887G0) r0
            goto L88
        L6a:
            j$.util.stream.a r0 = r8.f1175j
            j$.util.stream.c3 r0 = r0.mo916A0()
            j$.util.stream.d r1 = r8.f1221d
            j$.util.stream.a4 r1 = (p017j$.util.stream.C1990a4) r1
            java.lang.Object r1 = r1.m1025i()
            j$.util.stream.G0 r1 = (p017j$.util.stream.InterfaceC1887G0) r1
            j$.util.stream.d r2 = r8.f1222e
            j$.util.stream.a4 r2 = (p017j$.util.stream.C1990a4) r2
            java.lang.Object r2 = r2.m1025i()
            j$.util.stream.G0 r2 = (p017j$.util.stream.InterfaceC1887G0) r2
            j$.util.stream.I0 r0 = p017j$.util.stream.AbstractC2106w1.m1092Y(r0, r1, r2)
        L88:
            r8.mo1023d(r0)
        L8b:
            r0 = 1
            r8.f1180o = r0
            super.onCompletion(r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.stream.C1990a4.onCompletion(java.util.concurrent.CountedCompleter):void");
    }

    public C1990a4(AbstractC1985a abstractC1985a, AbstractC2106w1 abstractC2106w1, Spliterator spliterator, IntFunction intFunction) {
        super(abstractC2106w1, spliterator);
        this.f1175j = abstractC1985a;
        this.f1176k = intFunction;
        this.f1177l = EnumC1995b3.ORDERED.m1030n(((AbstractC1985a) abstractC2106w1).f1167m);
    }

    public C1990a4(C1990a4 c1990a4, Spliterator spliterator) {
        super(c1990a4, spliterator);
        this.f1175j = c1990a4.f1175j;
        this.f1176k = c1990a4.f1176k;
        this.f1177l = c1990a4.f1177l;
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: c */
    public final AbstractC2003d mo973c(Spliterator spliterator) {
        return new C1990a4(this, spliterator);
    }

    @Override // p017j$.util.stream.AbstractC1991b
    /* renamed from: h */
    public final Object mo974h() {
        return AbstractC2106w1.m1094c0(this.f1175j.mo916A0());
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: a */
    public final Object mo972a() {
        InterfaceC2115y0 interfaceC2115y0Mo925q0 = this.f1218a.mo925q0(-1L, this.f1176k);
        InterfaceC2062n2 interfaceC2062n2Mo964E0 = this.f1175j.mo964E0(((AbstractC1985a) this.f1218a).f1167m, interfaceC2115y0Mo925q0);
        AbstractC2106w1 abstractC2106w1 = this.f1218a;
        boolean zMo1014a0 = abstractC2106w1.mo1014a0(this.f1219b, abstractC2106w1.mo1018u0(interfaceC2062n2Mo964E0));
        this.f1179n = zMo1014a0;
        if (zMo1014a0) {
            m1024g();
        }
        InterfaceC1887G0 interfaceC1887G0Build = interfaceC2115y0Mo925q0.build();
        this.f1178m = interfaceC1887G0Build.count();
        return interfaceC1887G0Build;
    }
}
