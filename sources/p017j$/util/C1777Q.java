package p017j$.util;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.Q */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1777Q implements InterfaceC1779T {

    /* renamed from: a */
    public final /* synthetic */ Spliterator.OfDouble f783a;

    public /* synthetic */ C1777Q(Spliterator.OfDouble ofDouble) {
        this.f783a = ofDouble;
    }

    /* renamed from: a */
    public static /* synthetic */ InterfaceC1779T m857a(Spliterator.OfDouble ofDouble) {
        if (ofDouble == null) {
            return null;
        }
        return ofDouble instanceof C1778S ? ((C1778S) ofDouble).f784a : new C1777Q(ofDouble);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ int characteristics() {
        return this.f783a.characteristics();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        Spliterator.OfDouble ofDouble = this.f783a;
        if (obj instanceof C1777Q) {
            obj = ((C1777Q) obj).f783a;
        }
        return ofDouble.equals(obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long estimateSize() {
        return this.f783a.estimateSize();
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final /* synthetic */ void forEachRemaining(Object obj) {
        this.f783a.forEachRemaining((Spliterator.OfDouble) obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.f783a.forEachRemaining((Consumer<? super Double>) consumer);
    }

    @Override // p017j$.util.InterfaceC1779T
    public final /* synthetic */ void forEachRemaining(DoubleConsumer doubleConsumer) {
        this.f783a.forEachRemaining(doubleConsumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return this.f783a.getComparator();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return this.f783a.getExactSizeIfKnown();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return this.f783a.hasCharacteristics(i);
    }

    public final /* synthetic */ int hashCode() {
        return this.f783a.hashCode();
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final /* synthetic */ boolean tryAdvance(Object obj) {
        return this.f783a.tryAdvance((Spliterator.OfDouble) obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return this.f783a.tryAdvance((Consumer<? super Double>) consumer);
    }

    @Override // p017j$.util.InterfaceC1779T
    public final /* synthetic */ boolean tryAdvance(DoubleConsumer doubleConsumer) {
        return this.f783a.tryAdvance(doubleConsumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Spliterator trySplit() {
        return C1791c0.m867a(this.f783a.trySplit());
    }

    @Override // p017j$.util.InterfaceC1779T, p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* synthetic */ InterfaceC1779T trySplit() {
        return m857a(this.f783a.trySplit());
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* synthetic */ InterfaceC1789b0 trySplit() {
        return C1785Z.m864a(this.f783a.trySplit());
    }
}
