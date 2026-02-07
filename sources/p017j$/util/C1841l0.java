package p017j$.util;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.l0 */
/* loaded from: classes2.dex */
public final class C1841l0 extends AbstractC1636a implements InterfaceC1784Y {
    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m515k(this, consumer);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ Comparator getComparator() {
        return Spliterator.CC.$default$getComparator(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ long getExactSizeIfKnown() {
        return Spliterator.CC.$default$getExactSizeIfKnown(this);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean hasCharacteristics(int i) {
        return Spliterator.CC.$default$hasCharacteristics(this, i);
    }

    @Override // p017j$.util.Spliterator
    public final /* synthetic */ boolean tryAdvance(Consumer consumer) {
        return AbstractC1636a.m529y(this, consumer);
    }

    @Override // p017j$.com.android.tools.p018r8.AbstractC1636a, p017j$.util.InterfaceC1779T, p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* bridge */ /* synthetic */ InterfaceC1784Y trySplit() {
        return null;
    }

    @Override // p017j$.com.android.tools.p018r8.AbstractC1636a, p017j$.util.InterfaceC1779T, p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* bridge */ /* synthetic */ InterfaceC1789b0 trySplit() {
        return null;
    }

    @Override // p017j$.util.InterfaceC1784Y
    public final boolean tryAdvance(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        return false;
    }

    @Override // p017j$.util.InterfaceC1784Y
    public final void forEachRemaining(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
    }
}
