package p017j$.util;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;

/* renamed from: j$.util.Z */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1785Z implements InterfaceC1789b0 {

    /* renamed from: a */
    public final /* synthetic */ Spliterator.OfPrimitive f794a;

    public /* synthetic */ C1785Z(Spliterator.OfPrimitive ofPrimitive) {
        this.f794a = ofPrimitive;
    }

    /* renamed from: a */
    public static /* synthetic */ InterfaceC1789b0 m864a(Spliterator.OfPrimitive ofPrimitive) {
        if (ofPrimitive == null) {
            return null;
        }
        return ofPrimitive instanceof C1787a0 ? ((C1787a0) ofPrimitive).f798a : ofPrimitive instanceof Spliterator.OfDouble ? C1777Q.m857a((Spliterator.OfDouble) ofPrimitive) : ofPrimitive instanceof Spliterator.OfInt ? C1780U.m860a((Spliterator.OfInt) ofPrimitive) : ofPrimitive instanceof Spliterator.OfLong ? C1782W.m862a((Spliterator.OfLong) ofPrimitive) : new C1785Z(ofPrimitive);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ int characteristics() {
        return this.f794a.characteristics();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        Spliterator.OfPrimitive ofPrimitive = this.f794a;
        if (obj instanceof C1785Z) {
            obj = ((C1785Z) obj).f794a;
        }
        return ofPrimitive.equals(obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long estimateSize() {
        return this.f794a.estimateSize();
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final /* synthetic */ void forEachRemaining(Object obj) {
        this.f794a.forEachRemaining((Spliterator.OfPrimitive) obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.f794a.forEachRemaining(consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return this.f794a.getComparator();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return this.f794a.getExactSizeIfKnown();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return this.f794a.hasCharacteristics(i);
    }

    public final /* synthetic */ int hashCode() {
        return this.f794a.hashCode();
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final /* synthetic */ boolean tryAdvance(Object obj) {
        return this.f794a.tryAdvance((Spliterator.OfPrimitive) obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return this.f794a.tryAdvance(consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Spliterator trySplit() {
        return C1791c0.m867a(this.f794a.trySplit());
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* synthetic */ InterfaceC1789b0 trySplit() {
        return m864a(this.f794a.trySplit());
    }
}
