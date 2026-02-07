package p017j$.util;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.util.Spliterator;
import p017j$.util.function.IntConsumer$CC;

/* renamed from: j$.util.e0 */
/* loaded from: classes2.dex */
public final class C1819e0 implements InterfaceC1770J, IntConsumer, InterfaceC2129y {

    /* renamed from: a */
    public boolean f885a = false;

    /* renamed from: b */
    public int f886b;

    /* renamed from: c */
    public final /* synthetic */ Spliterator.OfInt f887c;

    public final /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
        return IntConsumer$CC.$default$andThen(this, intConsumer);
    }

    @Override // p017j$.util.InterfaceC1775O
    public final void forEachRemaining(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        while (hasNext()) {
            intConsumer.accept(nextInt());
        }
    }

    @Override // java.util.Iterator
    public final Integer next() {
        if (AbstractC1855s0.f959a) {
            AbstractC1855s0.m914a(C1819e0.class, "{0} calling PrimitiveIterator.OfInt.nextInt()");
            throw null;
        }
        return Integer.valueOf(nextInt());
    }

    @Override // p017j$.util.InterfaceC1770J, java.util.Iterator, p017j$.util.InterfaceC2129y
    public final void forEachRemaining(Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            forEachRemaining((IntConsumer) consumer);
            return;
        }
        Objects.requireNonNull(consumer);
        if (AbstractC1855s0.f959a) {
            AbstractC1855s0.m914a(C1819e0.class, "{0} calling PrimitiveIterator.OfInt.forEachRemainingInt(action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        forEachRemaining((IntConsumer) new C1767G(consumer, 0));
    }

    public C1819e0(Spliterator.OfInt ofInt) {
        this.f887c = ofInt;
    }

    @Override // java.util.function.IntConsumer
    public final void accept(int i) {
        this.f885a = true;
        this.f886b = i;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (!this.f885a) {
            this.f887c.tryAdvance((IntConsumer) this);
        }
        return this.f885a;
    }

    @Override // p017j$.util.InterfaceC1770J
    public final int nextInt() {
        if (!this.f885a && !hasNext()) {
            throw new NoSuchElementException();
        }
        this.f885a = false;
        return this.f886b;
    }
}
