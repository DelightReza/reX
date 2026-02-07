package p017j$.util.stream;

import java.util.concurrent.CountedCompleter;
import java.util.function.IntFunction;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.Z3 */
/* loaded from: classes2.dex */
public final class C1984Z3 extends AbstractC2003d {

    /* renamed from: h */
    public final AbstractC1985a f1157h;

    /* renamed from: i */
    public final IntFunction f1158i;

    /* renamed from: j */
    public final boolean f1159j;

    /* renamed from: k */
    public long f1160k;

    /* renamed from: l */
    public long f1161l;

    @Override // p017j$.util.stream.AbstractC2003d, java.util.concurrent.CountedCompleter
    public final void onCompletion(CountedCompleter countedCompleter) {
        AbstractC2003d abstractC2003d = this.f1221d;
        if (abstractC2003d != null) {
            if (this.f1159j) {
                C1984Z3 c1984z3 = (C1984Z3) abstractC2003d;
                long j = c1984z3.f1161l;
                this.f1161l = j;
                if (j == c1984z3.f1160k) {
                    this.f1161l = j + ((C1984Z3) this.f1222e).f1161l;
                }
            }
            C1984Z3 c1984z32 = (C1984Z3) abstractC2003d;
            long j2 = c1984z32.f1160k;
            C1984Z3 c1984z33 = (C1984Z3) this.f1222e;
            this.f1160k = j2 + c1984z33.f1160k;
            InterfaceC1887G0 interfaceC1887G0M1092Y = c1984z32.f1160k == 0 ? (InterfaceC1887G0) c1984z33.f1223f : c1984z33.f1160k == 0 ? (InterfaceC1887G0) c1984z32.f1223f : AbstractC2106w1.m1092Y(this.f1157h.mo916A0(), (InterfaceC1887G0) ((C1984Z3) this.f1221d).f1223f, (InterfaceC1887G0) ((C1984Z3) this.f1222e).f1223f);
            if (m1034b() && this.f1159j) {
                interfaceC1887G0M1092Y = interfaceC1887G0M1092Y.mo956e(this.f1161l, interfaceC1887G0M1092Y.count(), this.f1158i);
            }
            this.f1223f = interfaceC1887G0M1092Y;
        }
        super.onCompletion(countedCompleter);
    }

    public C1984Z3(AbstractC1985a abstractC1985a, AbstractC2106w1 abstractC2106w1, Spliterator spliterator, IntFunction intFunction) {
        super(abstractC2106w1, spliterator);
        this.f1157h = abstractC1985a;
        this.f1158i = intFunction;
        this.f1159j = EnumC1995b3.ORDERED.m1030n(((AbstractC1985a) abstractC2106w1).f1167m);
    }

    public C1984Z3(C1984Z3 c1984z3, Spliterator spliterator) {
        super(c1984z3, spliterator);
        this.f1157h = c1984z3.f1157h;
        this.f1158i = c1984z3.f1158i;
        this.f1159j = c1984z3.f1159j;
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: c */
    public final AbstractC2003d mo973c(Spliterator spliterator) {
        return new C1984Z3(this, spliterator);
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x001c  */
    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object mo972a() {
        /*
            r5 = this;
            boolean r0 = r5.m1034b()
            if (r0 != 0) goto L1c
            boolean r1 = r5.f1159j
            if (r1 == 0) goto L1c
            j$.util.stream.b3 r1 = p017j$.util.stream.EnumC1995b3.SIZED
            j$.util.stream.a r2 = r5.f1157h
            int r3 = r2.f1164j
            int r1 = r1.f1209e
            r3 = r3 & r1
            if (r3 != r1) goto L1c
            j$.util.Spliterator r1 = r5.f1219b
            long r1 = r2.mo1016e0(r1)
            goto L1e
        L1c:
            r1 = -1
        L1e:
            j$.util.stream.w1 r3 = r5.f1218a
            java.util.function.IntFunction r4 = r5.f1158i
            j$.util.stream.y0 r1 = r3.mo925q0(r1, r4)
            j$.util.stream.a r2 = r5.f1157h
            j$.util.stream.X3 r2 = (p017j$.util.stream.InterfaceC1974X3) r2
            boolean r3 = r5.f1159j
            if (r3 == 0) goto L32
            if (r0 != 0) goto L32
            r0 = 1
            goto L33
        L32:
            r0 = 0
        L33:
            j$.util.stream.Y3 r0 = r2.mo965h(r1, r0)
            j$.util.stream.w1 r2 = r5.f1218a
            j$.util.Spliterator r3 = r5.f1219b
            r2.mo1017t0(r3, r0)
            j$.util.stream.G0 r1 = r1.build()
            long r2 = r1.count()
            r5.f1160k = r2
            long r2 = r0.mo989n()
            r5.f1161l = r2
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.stream.C1984Z3.mo972a():java.lang.Object");
    }
}
