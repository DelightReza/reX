package p017j$.util;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* renamed from: j$.util.L */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1772L implements InterfaceC1774N, InterfaceC2129y {

    /* renamed from: a */
    public final /* synthetic */ PrimitiveIterator.OfLong f775a;

    public /* synthetic */ C1772L(PrimitiveIterator.OfLong ofLong) {
        this.f775a = ofLong;
    }

    public final /* synthetic */ boolean equals(Object obj) {
        PrimitiveIterator.OfLong ofLong = this.f775a;
        if (obj instanceof C1772L) {
            obj = ((C1772L) obj).f775a;
        }
        return ofLong.equals(obj);
    }

    @Override // p017j$.util.InterfaceC1775O
    public final /* synthetic */ void forEachRemaining(Object obj) {
        this.f775a.forEachRemaining((PrimitiveIterator.OfLong) obj);
    }

    @Override // p017j$.util.InterfaceC1774N, java.util.Iterator, p017j$.util.InterfaceC2129y
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.f775a.forEachRemaining((Consumer<? super Long>) consumer);
    }

    @Override // p017j$.util.InterfaceC1774N
    public final /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
        this.f775a.forEachRemaining(longConsumer);
    }

    @Override // java.util.Iterator
    public final /* synthetic */ boolean hasNext() {
        return this.f775a.hasNext();
    }

    public final /* synthetic */ int hashCode() {
        return this.f775a.hashCode();
    }

    @Override // p017j$.util.InterfaceC1774N, java.util.Iterator
    public final /* synthetic */ Long next() {
        return this.f775a.next();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        return this.f775a.next();
    }

    @Override // p017j$.util.InterfaceC1774N
    public final /* synthetic */ long nextLong() {
        return this.f775a.nextLong();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ void remove() {
        this.f775a.remove();
    }
}
