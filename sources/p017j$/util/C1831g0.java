package p017j$.util;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.g0 */
/* loaded from: classes2.dex */
public final class C1831g0 implements InterfaceC1766F, DoubleConsumer, InterfaceC2129y {

    /* renamed from: a */
    public boolean f913a = false;

    /* renamed from: b */
    public double f914b;

    /* renamed from: c */
    public final /* synthetic */ InterfaceC1779T f915c;

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // p017j$.util.InterfaceC1775O
    public final void forEachRemaining(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        while (hasNext()) {
            doubleConsumer.accept(nextDouble());
        }
    }

    @Override // java.util.Iterator
    public final Double next() {
        if (AbstractC1855s0.f959a) {
            AbstractC1855s0.m914a(C1831g0.class, "{0} calling PrimitiveIterator.OfDouble.nextLong()");
            throw null;
        }
        return Double.valueOf(nextDouble());
    }

    @Override // p017j$.util.InterfaceC1766F, java.util.Iterator, p017j$.util.InterfaceC2129y
    public final void forEachRemaining(Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            forEachRemaining((DoubleConsumer) consumer);
            return;
        }
        Objects.requireNonNull(consumer);
        if (AbstractC1855s0.f959a) {
            AbstractC1855s0.m914a(C1831g0.class, "{0} calling PrimitiveIterator.OfDouble.forEachRemainingDouble(action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        forEachRemaining((DoubleConsumer) new C1763C(consumer, 0));
    }

    public C1831g0(InterfaceC1779T interfaceC1779T) {
        this.f915c = interfaceC1779T;
    }

    @Override // java.util.function.DoubleConsumer
    public final void accept(double d) {
        this.f913a = true;
        this.f914b = d;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (!this.f913a) {
            this.f915c.tryAdvance((DoubleConsumer) this);
        }
        return this.f913a;
    }

    @Override // p017j$.util.InterfaceC1766F
    public final double nextDouble() {
        if (!this.f913a && !hasNext()) {
            throw new NoSuchElementException();
        }
        this.f913a = false;
        return this.f914b;
    }
}
