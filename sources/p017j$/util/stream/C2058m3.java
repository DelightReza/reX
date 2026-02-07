package p017j$.util.stream;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1726t;
import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1789b0;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.m3 */
/* loaded from: classes2.dex */
public final class C2058m3 extends AbstractC2007d3 implements InterfaceC1779T {
    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m513i(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m527w(this, consumer);
    }

    @Override // p017j$.util.stream.AbstractC2007d3
    /* renamed from: e */
    public final AbstractC2007d3 mo955e(Spliterator spliterator) {
        return new C2058m3(this.f1227b, spliterator, this.f1226a);
    }

    @Override // p017j$.util.stream.AbstractC2007d3
    /* renamed from: d */
    public final void mo954d() {
        C1948S2 c1948s2 = new C1948S2();
        this.f1233h = c1948s2;
        Objects.requireNonNull(c1948s2);
        this.f1230e = this.f1227b.mo1018u0(new C2053l3(c1948s2, 1));
        this.f1231f = new C1726t(14, this);
    }

    @Override // p017j$.util.stream.AbstractC2007d3, p017j$.util.Spliterator
    public final Spliterator trySplit() {
        return (InterfaceC1779T) super.trySplit();
    }

    @Override // p017j$.util.stream.AbstractC2007d3, p017j$.util.Spliterator
    public final InterfaceC1779T trySplit() {
        return (InterfaceC1779T) super.trySplit();
    }

    @Override // p017j$.util.stream.AbstractC2007d3, p017j$.util.Spliterator
    public final InterfaceC1789b0 trySplit() {
        return (InterfaceC1779T) super.trySplit();
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final boolean tryAdvance(DoubleConsumer doubleConsumer) {
        double d;
        Objects.requireNonNull(doubleConsumer);
        boolean zM1035a = m1035a();
        if (zM1035a) {
            C1948S2 c1948s2 = (C1948S2) this.f1233h;
            long j = this.f1232g;
            int iM1004o = c1948s2.m1004o(j);
            if (c1948s2.f1214c == 0 && iM1004o == 0) {
                d = ((double[]) c1948s2.f1149e)[(int) j];
            } else {
                d = ((double[][]) c1948s2.f1150f)[iM1004o][(int) (j - c1948s2.f1215d[iM1004o])];
            }
            doubleConsumer.accept(d);
        }
        return zM1035a;
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final void forEachRemaining(DoubleConsumer doubleConsumer) {
        if (this.f1233h == null && !this.f1234i) {
            Objects.requireNonNull(doubleConsumer);
            m1037c();
            Objects.requireNonNull(doubleConsumer);
            C2053l3 c2053l3 = new C2053l3(doubleConsumer, 0);
            this.f1227b.mo1017t0(this.f1229d, c2053l3);
            this.f1234i = true;
            return;
        }
        while (tryAdvance(doubleConsumer)) {
        }
    }
}
