package p017j$.util.stream;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import p017j$.util.Spliterator;
import p017j$.util.concurrent.C1810s;

/* renamed from: j$.util.stream.u0 */
/* loaded from: classes2.dex */
public final class C2095u0 extends AbstractC1991b {

    /* renamed from: j */
    public final C1810s f1347j;

    public C2095u0(C1810s c1810s, AbstractC1985a abstractC1985a, Spliterator spliterator) {
        super(abstractC1985a, spliterator);
        this.f1347j = c1810s;
    }

    public C2095u0(C2095u0 c2095u0, Spliterator spliterator) {
        super(c2095u0, spliterator);
        this.f1347j = c2095u0.f1347j;
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: c */
    public final AbstractC2003d mo973c(Spliterator spliterator) {
        return new C2095u0(this, spliterator);
    }

    @Override // p017j$.util.stream.AbstractC2003d
    /* renamed from: a */
    public final Object mo972a() {
        AbstractC2106w1 abstractC2106w1 = this.f1218a;
        AbstractC2085s0 abstractC2085s0 = (AbstractC2085s0) ((Supplier) this.f1347j.f865c).get();
        abstractC2106w1.mo1017t0(this.f1219b, abstractC2085s0);
        boolean z = abstractC2085s0.f1336b;
        if (z == ((EnumC2090t0) this.f1347j.f864b).f1343b) {
            Boolean boolValueOf = Boolean.valueOf(z);
            AtomicReference atomicReference = this.f1181h;
            while (!atomicReference.compareAndSet(null, boolValueOf) && atomicReference.get() == null) {
            }
        }
        return null;
    }

    @Override // p017j$.util.stream.AbstractC1991b
    /* renamed from: h */
    public final Object mo974h() {
        return Boolean.valueOf(!((EnumC2090t0) this.f1347j.f864b).f1343b);
    }
}
