package p017j$.util;

import java.util.PrimitiveIterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* renamed from: j$.util.H */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1768H implements InterfaceC1770J, InterfaceC2129y {

    /* renamed from: a */
    public final /* synthetic */ PrimitiveIterator.OfInt f771a;

    public /* synthetic */ C1768H(PrimitiveIterator.OfInt ofInt) {
        this.f771a = ofInt;
    }

    public final /* synthetic */ boolean equals(Object obj) {
        PrimitiveIterator.OfInt ofInt = this.f771a;
        if (obj instanceof C1768H) {
            obj = ((C1768H) obj).f771a;
        }
        return ofInt.equals(obj);
    }

    @Override // p017j$.util.InterfaceC1775O
    public final /* synthetic */ void forEachRemaining(Object obj) {
        this.f771a.forEachRemaining((PrimitiveIterator.OfInt) obj);
    }

    @Override // p017j$.util.InterfaceC1770J, java.util.Iterator, p017j$.util.InterfaceC2129y
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.f771a.forEachRemaining((Consumer<? super Integer>) consumer);
    }

    @Override // p017j$.util.InterfaceC1770J
    public final /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
        this.f771a.forEachRemaining(intConsumer);
    }

    @Override // java.util.Iterator
    public final /* synthetic */ boolean hasNext() {
        return this.f771a.hasNext();
    }

    public final /* synthetic */ int hashCode() {
        return this.f771a.hashCode();
    }

    @Override // p017j$.util.InterfaceC1770J, java.util.Iterator
    public final /* synthetic */ Integer next() {
        return this.f771a.next();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ Object next() {
        return this.f771a.next();
    }

    @Override // p017j$.util.InterfaceC1770J
    public final /* synthetic */ int nextInt() {
        return this.f771a.nextInt();
    }

    @Override // java.util.Iterator
    public final /* synthetic */ void remove() {
        this.f771a.remove();
    }
}
