package p017j$.util;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.U */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1780U implements Spliterator.OfInt {

    /* renamed from: a */
    public final /* synthetic */ Spliterator.OfInt f790a;

    public /* synthetic */ C1780U(Spliterator.OfInt ofInt) {
        this.f790a = ofInt;
    }

    /* renamed from: a */
    public static /* synthetic */ Spliterator.OfInt m860a(Spliterator.OfInt ofInt) {
        if (ofInt == null) {
            return null;
        }
        return ofInt instanceof C1781V ? ((C1781V) ofInt).f791a : new C1780U(ofInt);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ int characteristics() {
        return this.f790a.characteristics();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        Spliterator.OfInt ofInt = this.f790a;
        if (obj instanceof C1780U) {
            obj = ((C1780U) obj).f790a;
        }
        return ofInt.equals(obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long estimateSize() {
        return this.f790a.estimateSize();
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final /* synthetic */ void forEachRemaining(Object obj) {
        this.f790a.forEachRemaining((Spliterator.OfInt) obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.f790a.forEachRemaining((Consumer<? super Integer>) consumer);
    }

    @Override // j$.util.Spliterator.OfInt
    public final /* synthetic */ void forEachRemaining(IntConsumer intConsumer) {
        this.f790a.forEachRemaining(intConsumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return this.f790a.getComparator();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return this.f790a.getExactSizeIfKnown();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return this.f790a.hasCharacteristics(i);
    }

    public final /* synthetic */ int hashCode() {
        return this.f790a.hashCode();
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final /* synthetic */ boolean tryAdvance(Object obj) {
        return this.f790a.tryAdvance((Spliterator.OfInt) obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return this.f790a.tryAdvance((Consumer<? super Integer>) consumer);
    }

    @Override // j$.util.Spliterator.OfInt
    public final /* synthetic */ boolean tryAdvance(IntConsumer intConsumer) {
        return this.f790a.tryAdvance(intConsumer);
    }

    @Override // j$.util.Spliterator.OfInt, p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* synthetic */ Spliterator.OfInt trySplit() {
        return m860a(this.f790a.trySplit());
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Spliterator trySplit() {
        return C1791c0.m867a(this.f790a.trySplit());
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* synthetic */ InterfaceC1789b0 trySplit() {
        return C1785Z.m864a(this.f790a.trySplit());
    }
}
