package p017j$.util;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.D */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1764D implements InterfaceC1766F, InterfaceC2129y {

    /* renamed from: a */
    public final /* synthetic */ PrimitiveIterator.OfDouble f767a;

    public /* synthetic */ C1764D(PrimitiveIterator.OfDouble ofDouble) {
        this.f767a = ofDouble;
    }

    public final /* synthetic */ boolean equals(Object obj) {
        PrimitiveIterator.OfDouble ofDouble = this.f767a;
        if (obj instanceof C1764D) {
            obj = ((C1764D) obj).f767a;
        }
        return ofDouble.equals(obj);
    }

    @Override // p017j$.util.InterfaceC1775O
    public final /* synthetic */ void forEachRemaining(Object obj) {
        this.f767a.forEachRemaining((PrimitiveIterator.OfDouble) obj);
    }

    @Override // p017j$.util.InterfaceC1766F, java.util.Iterator, p017j$.util.InterfaceC2129y
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.f767a.forEachRemaining((Consumer<? super Double>) consumer);
    }

    @Override // p017j$.util.InterfaceC1766F
    public final /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
        this.f767a.forEachRemaining(doubleConsumer);
    }

    @Override // java.util.Iterator
    public final /* synthetic */ boolean hasNext() {
        return this.f767a.hasNext();
    }

    public final /* synthetic */ int hashCode() {
        return this.f767a.hashCode();
    }

    @Override // p017j$.util.InterfaceC1766F, java.util.Iterator
    public final /* synthetic */ Double next() {
        return this.f767a.next();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        return this.f767a.next();
    }

    @Override // p017j$.util.InterfaceC1766F
    public final /* synthetic */ double nextDouble() {
        return this.f767a.nextDouble();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ void remove() {
        this.f767a.remove();
    }
}
