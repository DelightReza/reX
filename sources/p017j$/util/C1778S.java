package p017j$.util;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.S */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1778S implements Spliterator.OfDouble {

    /* renamed from: a */
    public final /* synthetic */ InterfaceC1779T f784a;

    public /* synthetic */ C1778S(InterfaceC1779T interfaceC1779T) {
        this.f784a = interfaceC1779T;
    }

    /* renamed from: a */
    public static /* synthetic */ Spliterator.OfDouble m858a(InterfaceC1779T interfaceC1779T) {
        if (interfaceC1779T == null) {
            return null;
        }
        return interfaceC1779T instanceof C1777Q ? ((C1777Q) interfaceC1779T).f783a : new C1778S(interfaceC1779T);
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ int characteristics() {
        return this.f784a.characteristics();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        InterfaceC1779T interfaceC1779T = this.f784a;
        if (obj instanceof C1778S) {
            obj = ((C1778S) obj).f784a;
        }
        return interfaceC1779T.equals(obj);
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ long estimateSize() {
        return this.f784a.estimateSize();
    }

    @Override // java.util.Spliterator.OfPrimitive
    public final /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
        this.f784a.forEachRemaining((Object) doubleConsumer);
    }

    @Override // java.util.Spliterator.OfDouble, java.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.f784a.forEachRemaining(consumer);
    }

    @Override // java.util.Spliterator.OfDouble
    /* renamed from: forEachRemaining, reason: avoid collision after fix types in other method */
    public final /* synthetic */ void forEachRemaining2(DoubleConsumer doubleConsumer) {
        this.f784a.forEachRemaining(doubleConsumer);
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return this.f784a.getComparator();
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return this.f784a.getExactSizeIfKnown();
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return this.f784a.hasCharacteristics(i);
    }

    public final /* synthetic */ int hashCode() {
        return this.f784a.hashCode();
    }

    @Override // java.util.Spliterator.OfPrimitive
    public final /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
        return this.f784a.tryAdvance((Object) doubleConsumer);
    }

    @Override // java.util.Spliterator.OfDouble, java.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return this.f784a.tryAdvance(consumer);
    }

    @Override // java.util.Spliterator.OfDouble
    /* renamed from: tryAdvance, reason: avoid collision after fix types in other method */
    public final /* synthetic */ boolean tryAdvance2(DoubleConsumer doubleConsumer) {
        return this.f784a.tryAdvance(doubleConsumer);
    }

    @Override // java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
    public final /* synthetic */ Spliterator.OfDouble trySplit() {
        return m858a(this.f784a.trySplit());
    }

    @Override // java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
    public final /* synthetic */ Spliterator.OfPrimitive trySplit() {
        return C1787a0.m866a(this.f784a.trySplit());
    }

    @Override // java.util.Spliterator.OfDouble, java.util.Spliterator.OfPrimitive, java.util.Spliterator
    public final /* synthetic */ Spliterator trySplit() {
        return Spliterator.Wrapper.convert(this.f784a.trySplit());
    }
}
