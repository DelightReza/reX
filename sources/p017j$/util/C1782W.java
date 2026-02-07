package p017j$.util;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* renamed from: j$.util.W */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1782W implements InterfaceC1784Y {

    /* renamed from: a */
    public final /* synthetic */ Spliterator.OfLong f792a;

    public /* synthetic */ C1782W(Spliterator.OfLong ofLong) {
        this.f792a = ofLong;
    }

    /* renamed from: a */
    public static /* synthetic */ InterfaceC1784Y m862a(Spliterator.OfLong ofLong) {
        if (ofLong == null) {
            return null;
        }
        return ofLong instanceof C1783X ? ((C1783X) ofLong).f793a : new C1782W(ofLong);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ int characteristics() {
        return this.f792a.characteristics();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        Spliterator.OfLong ofLong = this.f792a;
        if (obj instanceof C1782W) {
            obj = ((C1782W) obj).f792a;
        }
        return ofLong.equals(obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long estimateSize() {
        return this.f792a.estimateSize();
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final /* synthetic */ void forEachRemaining(Object obj) {
        this.f792a.forEachRemaining((Spliterator.OfLong) obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.f792a.forEachRemaining((Consumer<? super Long>) consumer);
    }

    @Override // p017j$.util.InterfaceC1784Y
    public final /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
        this.f792a.forEachRemaining(longConsumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return this.f792a.getComparator();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return this.f792a.getExactSizeIfKnown();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return this.f792a.hasCharacteristics(i);
    }

    public final /* synthetic */ int hashCode() {
        return this.f792a.hashCode();
    }

    @Override // p017j$.util.InterfaceC1789b0
    public final /* synthetic */ boolean tryAdvance(Object obj) {
        return this.f792a.tryAdvance((Spliterator.OfLong) obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return this.f792a.tryAdvance((Consumer<? super Long>) consumer);
    }

    @Override // p017j$.util.InterfaceC1784Y
    public final /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
        return this.f792a.tryAdvance(longConsumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Spliterator trySplit() {
        return C1791c0.m867a(this.f792a.trySplit());
    }

    @Override // p017j$.util.InterfaceC1784Y, p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* synthetic */ InterfaceC1784Y trySplit() {
        return m862a(this.f792a.trySplit());
    }

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* synthetic */ InterfaceC1789b0 trySplit() {
        return C1785Z.m864a(this.f792a.trySplit());
    }
}
