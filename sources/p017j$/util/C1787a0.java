package p017j$.util;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.a0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1787a0 implements Spliterator.OfPrimitive {

    /* renamed from: a */
    public final /* synthetic */ InterfaceC1789b0 f798a;

    public /* synthetic */ C1787a0(InterfaceC1789b0 interfaceC1789b0) {
        this.f798a = interfaceC1789b0;
    }

    /* renamed from: a */
    public static /* synthetic */ Spliterator.OfPrimitive m866a(InterfaceC1789b0 interfaceC1789b0) {
        if (interfaceC1789b0 == null) {
            return null;
        }
        return interfaceC1789b0 instanceof C1785Z ? ((C1785Z) interfaceC1789b0).f794a : interfaceC1789b0 instanceof InterfaceC1779T ? C1778S.m858a((InterfaceC1779T) interfaceC1789b0) : interfaceC1789b0 instanceof Spliterator.OfInt ? C1781V.m861a((Spliterator.OfInt) interfaceC1789b0) : interfaceC1789b0 instanceof InterfaceC1784Y ? C1783X.m863a((InterfaceC1784Y) interfaceC1789b0) : new C1787a0(interfaceC1789b0);
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ int characteristics() {
        return this.f798a.characteristics();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        InterfaceC1789b0 interfaceC1789b0 = this.f798a;
        if (obj instanceof C1787a0) {
            obj = ((C1787a0) obj).f798a;
        }
        return interfaceC1789b0.equals(obj);
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ long estimateSize() {
        return this.f798a.estimateSize();
    }

    @Override // java.util.Spliterator.OfPrimitive
    public final /* synthetic */ void forEachRemaining(Object obj) {
        this.f798a.forEachRemaining(obj);
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.f798a.forEachRemaining(consumer);
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return this.f798a.getComparator();
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return this.f798a.getExactSizeIfKnown();
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return this.f798a.hasCharacteristics(i);
    }

    public final /* synthetic */ int hashCode() {
        return this.f798a.hashCode();
    }

    @Override // java.util.Spliterator.OfPrimitive
    public final /* synthetic */ boolean tryAdvance(Object obj) {
        return this.f798a.tryAdvance(obj);
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return this.f798a.tryAdvance(consumer);
    }

    @Override // java.util.Spliterator.OfPrimitive, java.util.Spliterator
    public final /* synthetic */ Spliterator.OfPrimitive trySplit() {
        return m866a(this.f798a.trySplit());
    }

    @Override // java.util.Spliterator.OfPrimitive, java.util.Spliterator
    public final /* synthetic */ java.util.Spliterator trySplit() {
        return Spliterator.Wrapper.convert(this.f798a.trySplit());
    }
}
