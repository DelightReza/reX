package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1726t;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.q3 */
/* loaded from: classes2.dex */
public final class C2078q3 extends AbstractC2007d3 implements InterfaceC1784Y {
    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m515k(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m529y(this, consumer);
    }

    @Override // p017j$.util.stream.AbstractC2007d3
    /* renamed from: e */
    public final AbstractC2007d3 mo955e(Spliterator spliterator) {
        return new C2078q3(this.f1227b, spliterator, this.f1226a);
    }

    @Override // p017j$.util.stream.AbstractC2007d3
    /* renamed from: d */
    public final void mo954d() {
        C1968W2 c1968w2 = new C1968W2();
        this.f1233h = c1968w2;
        Objects.requireNonNull(c1968w2);
        this.f1230e = this.f1227b.mo1018u0(new C2073p3(c1968w2, 1));
        this.f1231f = new C1726t(16, this);
    }

    @Override // p017j$.util.stream.AbstractC2007d3, p017j$.util.Spliterator
    public final Spliterator trySplit() {
        return (InterfaceC1784Y) super.trySplit();
    }

    @Override // p017j$.util.stream.AbstractC2007d3, p017j$.util.Spliterator
    public final InterfaceC1784Y trySplit() {
        return (InterfaceC1784Y) super.trySplit();
    }

    @Override // p017j$.util.stream.AbstractC2007d3, p017j$.util.Spliterator
    public final InterfaceC1789b0 trySplit() {
        return (InterfaceC1784Y) super.trySplit();
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(LongConsumer longConsumer) {
        long j;
        Objects.requireNonNull(longConsumer);
        boolean zM1035a = m1035a();
        if (zM1035a) {
            C1968W2 c1968w2 = (C1968W2) this.f1233h;
            long j2 = this.f1232g;
            int iM1004o = c1968w2.m1004o(j2);
            if (c1968w2.f1214c == 0 && iM1004o == 0) {
                j = ((long[]) c1968w2.f1149e)[(int) j2];
            } else {
                j = ((long[][]) c1968w2.f1150f)[iM1004o][(int) (j2 - c1968w2.f1215d[iM1004o])];
            }
            longConsumer.accept(j);
        }
        return zM1035a;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(LongConsumer longConsumer) {
        if (this.f1233h == null && !this.f1234i) {
            Objects.requireNonNull(longConsumer);
            m1037c();
            Objects.requireNonNull(longConsumer);
            C2073p3 c2073p3 = new C2073p3(longConsumer, 0);
            this.f1227b.mo1017t0(this.f1229d, c2073p3);
            this.f1234i = true;
            return;
        }
        while (tryAdvance(longConsumer)) {
        }
    }
}
