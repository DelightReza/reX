package p017j$.util;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import p017j$.util.Spliterator;

/* renamed from: j$.util.c0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1791c0 implements Spliterator {

    /* renamed from: a */
    public final /* synthetic */ Spliterator f801a;

    public /* synthetic */ C1791c0(Spliterator spliterator) {
        this.f801a = spliterator;
    }

    /* renamed from: a */
    public static /* synthetic */ Spliterator m867a(Spliterator spliterator) {
        if (spliterator == null) {
            return null;
        }
        return spliterator instanceof Spliterator.Wrapper ? Spliterator.this : spliterator instanceof Spliterator.OfPrimitive ? C1785Z.m864a((Spliterator.OfPrimitive) spliterator) : new C1791c0(spliterator);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ int characteristics() {
        return this.f801a.characteristics();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        java.util.Spliterator spliterator = this.f801a;
        if (obj instanceof C1791c0) {
            obj = ((C1791c0) obj).f801a;
        }
        return spliterator.equals(obj);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long estimateSize() {
        return this.f801a.estimateSize();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        this.f801a.forEachRemaining(consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return this.f801a.getComparator();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return this.f801a.getExactSizeIfKnown();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return this.f801a.hasCharacteristics(i);
    }

    public final /* synthetic */ int hashCode() {
        return this.f801a.hashCode();
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return this.f801a.tryAdvance(consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Spliterator trySplit() {
        return m867a(this.f801a.trySplit());
    }
}
