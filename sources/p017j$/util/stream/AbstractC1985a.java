package p017j$.util.stream;

import java.util.function.IntFunction;
import java.util.function.Supplier;
import p017j$.time.C1678e;
import p017j$.time.C1726t;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.a */
/* loaded from: classes2.dex */
public abstract class AbstractC1985a extends AbstractC2106w1 implements BaseStream {

    /* renamed from: h */
    public final AbstractC1985a f1162h;

    /* renamed from: i */
    public final AbstractC1985a f1163i;

    /* renamed from: j */
    public final int f1164j;

    /* renamed from: k */
    public final AbstractC1985a f1165k;

    /* renamed from: l */
    public int f1166l;

    /* renamed from: m */
    public int f1167m;

    /* renamed from: n */
    public Spliterator f1168n;

    /* renamed from: o */
    public boolean f1169o;

    /* renamed from: p */
    public final boolean f1170p;

    /* renamed from: q */
    public Runnable f1171q;

    /* renamed from: r */
    public boolean f1172r;

    /* renamed from: A0 */
    public abstract EnumC2001c3 mo916A0();

    /* renamed from: D0 */
    public abstract boolean mo1007D0();

    /* renamed from: E0 */
    public abstract InterfaceC2062n2 mo964E0(int i, InterfaceC2062n2 interfaceC2062n2);

    /* renamed from: H0 */
    public abstract Spliterator mo917H0(AbstractC1985a abstractC1985a, Supplier supplier, boolean z);

    /* renamed from: y0 */
    public abstract InterfaceC1887G0 mo929y0(AbstractC1985a abstractC1985a, Spliterator spliterator, boolean z, IntFunction intFunction);

    /* renamed from: z0 */
    public abstract boolean mo930z0(Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2);

    public AbstractC1985a(Spliterator spliterator, int i, boolean z) {
        this.f1163i = null;
        this.f1168n = spliterator;
        this.f1162h = this;
        int i2 = EnumC1995b3.f1189g & i;
        this.f1164j = i2;
        this.f1167m = (~(i2 << 1)) & EnumC1995b3.f1194l;
        this.f1166l = 0;
        this.f1172r = z;
    }

    public AbstractC1985a(AbstractC1985a abstractC1985a, int i) {
        if (abstractC1985a.f1169o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        abstractC1985a.f1169o = true;
        abstractC1985a.f1165k = this;
        this.f1163i = abstractC1985a;
        this.f1164j = EnumC1995b3.f1190h & i;
        this.f1167m = EnumC1995b3.m1026i(i, abstractC1985a.f1167m);
        AbstractC1985a abstractC1985a2 = abstractC1985a.f1162h;
        this.f1162h = abstractC1985a2;
        if (mo1007D0()) {
            abstractC1985a2.f1170p = true;
        }
        this.f1166l = abstractC1985a.f1166l + 1;
    }

    /* renamed from: w0 */
    public final Object m1020w0(InterfaceC1910K3 interfaceC1910K3) {
        if (this.f1169o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.f1169o = true;
        if (this.f1162h.f1172r) {
            return interfaceC1910K3.mo903i(this, m1011F0(interfaceC1910K3.mo904s()));
        }
        return interfaceC1910K3.mo902f(this, m1011F0(interfaceC1910K3.mo904s()));
    }

    /* renamed from: x0 */
    public final InterfaceC1887G0 m1021x0(IntFunction intFunction) {
        if (this.f1169o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.f1169o = true;
        if (this.f1162h.f1172r && this.f1163i != null && mo1007D0()) {
            this.f1166l = 0;
            AbstractC1985a abstractC1985a = this.f1163i;
            return mo962B0(abstractC1985a, abstractC1985a.m1011F0(0), intFunction);
        }
        return mo1015d0(m1011F0(0), true, intFunction);
    }

    /* renamed from: G0 */
    public final Spliterator m1012G0() {
        AbstractC1985a abstractC1985a = this.f1162h;
        if (this != abstractC1985a) {
            throw new IllegalStateException();
        }
        if (this.f1169o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.f1169o = true;
        Spliterator spliterator = abstractC1985a.f1168n;
        if (spliterator != null) {
            abstractC1985a.f1168n = null;
            return spliterator;
        }
        throw new IllegalStateException("source already consumed or closed");
    }

    public final BaseStream sequential() {
        this.f1162h.f1172r = false;
        return this;
    }

    public final BaseStream parallel() {
        this.f1162h.f1172r = true;
        return this;
    }

    @Override // p017j$.util.stream.BaseStream, java.lang.AutoCloseable
    public final void close() {
        this.f1169o = true;
        this.f1168n = null;
        AbstractC1985a abstractC1985a = this.f1162h;
        Runnable runnable = abstractC1985a.f1171q;
        if (runnable != null) {
            abstractC1985a.f1171q = null;
            runnable.run();
        }
    }

    @Override // p017j$.util.stream.BaseStream
    public final BaseStream onClose(Runnable runnable) {
        if (this.f1169o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        Objects.requireNonNull(runnable);
        AbstractC1985a abstractC1985a = this.f1162h;
        Runnable runnable2 = abstractC1985a.f1171q;
        if (runnable2 != null) {
            runnable = new RunnableC1895H3(0, runnable2, runnable);
        }
        abstractC1985a.f1171q = runnable;
        return this;
    }

    public Spliterator spliterator() {
        if (this.f1169o) {
            throw new IllegalStateException("stream has already been operated upon or closed");
        }
        this.f1169o = true;
        AbstractC1985a abstractC1985a = this.f1162h;
        if (this == abstractC1985a) {
            Spliterator spliterator = abstractC1985a.f1168n;
            if (spliterator != null) {
                abstractC1985a.f1168n = null;
                return spliterator;
            }
            throw new IllegalStateException("source already consumed or closed");
        }
        return mo917H0(this, new C1726t(4, this), abstractC1985a.f1172r);
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: d0 */
    public final InterfaceC1887G0 mo1015d0(Spliterator spliterator, boolean z, IntFunction intFunction) {
        if (this.f1162h.f1172r) {
            return mo929y0(this, spliterator, z, intFunction);
        }
        InterfaceC2115y0 interfaceC2115y0Mo925q0 = mo925q0(mo1016e0(spliterator), intFunction);
        mo1017t0(spliterator, interfaceC2115y0Mo925q0);
        return interfaceC2115y0Mo925q0.build();
    }

    @Override // p017j$.util.stream.BaseStream
    public final boolean isParallel() {
        return this.f1162h.f1172r;
    }

    /* renamed from: F0 */
    public final Spliterator m1011F0(int i) {
        int i2;
        int i3;
        AbstractC1985a abstractC1985a = this.f1162h;
        Spliterator spliteratorMo963C0 = abstractC1985a.f1168n;
        if (spliteratorMo963C0 != null) {
            abstractC1985a.f1168n = null;
            if (abstractC1985a.f1172r && abstractC1985a.f1170p) {
                AbstractC1985a abstractC1985a2 = abstractC1985a.f1165k;
                int i4 = 1;
                while (abstractC1985a != this) {
                    int i5 = abstractC1985a2.f1164j;
                    if (abstractC1985a2.mo1007D0()) {
                        if (EnumC1995b3.SHORT_CIRCUIT.m1030n(i5)) {
                            i5 &= ~EnumC1995b3.f1203u;
                        }
                        spliteratorMo963C0 = abstractC1985a2.mo963C0(abstractC1985a, spliteratorMo963C0);
                        if (spliteratorMo963C0.hasCharacteristics(64)) {
                            i2 = (~EnumC1995b3.f1202t) & i5;
                            i3 = EnumC1995b3.f1201s;
                        } else {
                            i2 = (~EnumC1995b3.f1201s) & i5;
                            i3 = EnumC1995b3.f1202t;
                        }
                        i5 = i2 | i3;
                        i4 = 0;
                    }
                    int i6 = i4 + 1;
                    abstractC1985a2.f1166l = i4;
                    abstractC1985a2.f1167m = EnumC1995b3.m1026i(i5, abstractC1985a.f1167m);
                    AbstractC1985a abstractC1985a3 = abstractC1985a2;
                    abstractC1985a2 = abstractC1985a2.f1165k;
                    abstractC1985a = abstractC1985a3;
                    i4 = i6;
                }
            }
            if (i != 0) {
                this.f1167m = EnumC1995b3.m1026i(i, this.f1167m);
            }
            return spliteratorMo963C0;
        }
        throw new IllegalStateException("source already consumed or closed");
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: e0 */
    public final long mo1016e0(Spliterator spliterator) {
        if (EnumC1995b3.SIZED.m1030n(this.f1167m)) {
            return spliterator.getExactSizeIfKnown();
        }
        return -1L;
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: t0 */
    public final InterfaceC2062n2 mo1017t0(Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2) {
        mo1013Z(spliterator, mo1018u0((InterfaceC2062n2) Objects.requireNonNull(interfaceC2062n2)));
        return interfaceC2062n2;
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: Z */
    public final void mo1013Z(Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2) {
        Objects.requireNonNull(interfaceC2062n2);
        if (!EnumC1995b3.SHORT_CIRCUIT.m1030n(this.f1167m)) {
            interfaceC2062n2.mo931h(spliterator.getExactSizeIfKnown());
            spliterator.forEachRemaining(interfaceC2062n2);
            interfaceC2062n2.end();
            return;
        }
        mo1014a0(spliterator, interfaceC2062n2);
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: a0 */
    public final boolean mo1014a0(Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2) {
        AbstractC1985a abstractC1985a = this;
        while (abstractC1985a.f1166l > 0) {
            abstractC1985a = abstractC1985a.f1163i;
        }
        interfaceC2062n2.mo931h(spliterator.getExactSizeIfKnown());
        boolean zMo930z0 = abstractC1985a.mo930z0(spliterator, interfaceC2062n2);
        interfaceC2062n2.end();
        return zMo930z0;
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: u0 */
    public final InterfaceC2062n2 mo1018u0(InterfaceC2062n2 interfaceC2062n2) {
        Objects.requireNonNull(interfaceC2062n2);
        for (AbstractC1985a abstractC1985a = this; abstractC1985a.f1166l > 0; abstractC1985a = abstractC1985a.f1163i) {
            interfaceC2062n2 = abstractC1985a.mo964E0(abstractC1985a.f1163i.f1167m, interfaceC2062n2);
        }
        return interfaceC2062n2;
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: v0 */
    public final Spliterator mo1019v0(Spliterator spliterator) {
        return this.f1166l == 0 ? spliterator : mo917H0(this, new C1726t(5, spliterator), this.f1162h.f1172r);
    }

    /* renamed from: B0 */
    public InterfaceC1887G0 mo962B0(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, IntFunction intFunction) {
        throw new UnsupportedOperationException("Parallel evaluation is not supported");
    }

    /* renamed from: C0 */
    public Spliterator mo963C0(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        return mo962B0(abstractC1985a, spliterator, new C1678e(11)).spliterator();
    }
}
