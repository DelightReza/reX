package p017j$.util;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.f0 */
/* loaded from: classes2.dex */
public final class C1821f0 implements InterfaceC1774N, LongConsumer, InterfaceC2129y {

    /* renamed from: a */
    public boolean f890a = false;

    /* renamed from: b */
    public long f891b;

    /* renamed from: c */
    public final /* synthetic */ InterfaceC1784Y f892c;

    public final /* synthetic */ LongConsumer andThen(LongConsumer longConsumer) {
        return AbstractC1636a.m507c(this, longConsumer);
    }

    @Override // p017j$.util.InterfaceC1775O
    public final void forEachRemaining(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        while (hasNext()) {
            longConsumer.accept(nextLong());
        }
    }

    @Override // java.util.Iterator
    public final Long next() {
        if (AbstractC1855s0.f959a) {
            AbstractC1855s0.m914a(C1821f0.class, "{0} calling PrimitiveIterator.OfLong.nextLong()");
            throw null;
        }
        return Long.valueOf(nextLong());
    }

    @Override // p017j$.util.InterfaceC1774N, java.util.Iterator, p017j$.util.InterfaceC2129y
    public final void forEachRemaining(Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            forEachRemaining((LongConsumer) consumer);
            return;
        }
        Objects.requireNonNull(consumer);
        if (AbstractC1855s0.f959a) {
            AbstractC1855s0.m914a(C1821f0.class, "{0} calling PrimitiveIterator.OfLong.forEachRemainingLong(action::accept)");
            throw null;
        }
        Objects.requireNonNull(consumer);
        forEachRemaining((LongConsumer) new C1771K(consumer, 0));
    }

    public C1821f0(InterfaceC1784Y interfaceC1784Y) {
        this.f892c = interfaceC1784Y;
    }

    @Override // java.util.function.LongConsumer
    public final void accept(long j) {
        this.f890a = true;
        this.f891b = j;
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        if (!this.f890a) {
            this.f892c.tryAdvance((LongConsumer) this);
        }
        return this.f890a;
    }

    @Override // p017j$.util.InterfaceC1774N
    public final long nextLong() {
        if (!this.f890a && !hasNext()) {
            throw new NoSuchElementException();
        }
        this.f890a = false;
        return this.f891b;
    }
}
