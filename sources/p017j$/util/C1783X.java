package p017j$.util;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.X */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1783X implements Spliterator.OfLong {

    /* renamed from: a */
    public final /* synthetic */ InterfaceC1784Y f793a;

    public /* synthetic */ C1783X(InterfaceC1784Y interfaceC1784Y) {
        this.f793a = interfaceC1784Y;
    }

    /* renamed from: a */
    public static /* synthetic */ Spliterator.OfLong m863a(InterfaceC1784Y interfaceC1784Y) {
        if (interfaceC1784Y == null) {
            return null;
        }
        return interfaceC1784Y instanceof C1782W ? ((C1782W) interfaceC1784Y).f792a : new C1783X(interfaceC1784Y);
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ int characteristics() {
        return this.f793a.characteristics();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        InterfaceC1784Y interfaceC1784Y = this.f793a;
        if (obj instanceof C1783X) {
            obj = ((C1783X) obj).f793a;
        }
        return interfaceC1784Y.equals(obj);
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ long estimateSize() {
        return this.f793a.estimateSize();
    }

    @Override // java.util.Spliterator.OfPrimitive
    public final /* synthetic */ void forEachRemaining(LongConsumer longConsumer) {
        this.f793a.forEachRemaining((Object) longConsumer);
    }

    @Override // java.util.Spliterator.OfLong, java.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.f793a.forEachRemaining(consumer);
    }

    @Override // java.util.Spliterator.OfLong
    /* renamed from: forEachRemaining, reason: avoid collision after fix types in other method */
    public final /* synthetic */ void forEachRemaining2(LongConsumer longConsumer) {
        this.f793a.forEachRemaining(longConsumer);
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return this.f793a.getComparator();
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return this.f793a.getExactSizeIfKnown();
    }

    @Override // java.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return this.f793a.hasCharacteristics(i);
    }

    public final /* synthetic */ int hashCode() {
        return this.f793a.hashCode();
    }

    @Override // java.util.Spliterator.OfPrimitive
    public final /* synthetic */ boolean tryAdvance(LongConsumer longConsumer) {
        return this.f793a.tryAdvance((Object) longConsumer);
    }

    @Override // java.util.Spliterator.OfLong, java.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return this.f793a.tryAdvance(consumer);
    }

    @Override // java.util.Spliterator.OfLong
    /* renamed from: tryAdvance, reason: avoid collision after fix types in other method */
    public final /* synthetic */ boolean tryAdvance2(LongConsumer longConsumer) {
        return this.f793a.tryAdvance(longConsumer);
    }

    @Override // java.util.Spliterator.OfLong, java.util.Spliterator.OfPrimitive, java.util.Spliterator
    public final /* synthetic */ Spliterator.OfLong trySplit() {
        return m863a(this.f793a.trySplit());
    }

    @Override // java.util.Spliterator.OfLong, java.util.Spliterator.OfPrimitive, java.util.Spliterator
    public final /* synthetic */ Spliterator.OfPrimitive trySplit() {
        return C1787a0.m866a(this.f793a.trySplit());
    }

    @Override // java.util.Spliterator.OfLong, java.util.Spliterator.OfPrimitive, java.util.Spliterator
    public final /* synthetic */ Spliterator trySplit() {
        return Spliterator.Wrapper.convert(this.f793a.trySplit());
    }
}
