package p017j$.util;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.k0 */
/* loaded from: classes2.dex */
public final class C1839k0 extends AbstractC1636a implements Spliterator.OfInt {
    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m514j(this, consumer);
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
        return AbstractC1636a.m528x(this, consumer);
    }

    @Override // p017j$.com.android.tools.p018r8.AbstractC1636a, p017j$.util.InterfaceC1779T, p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* bridge */ /* synthetic */ Spliterator.OfInt trySplit() {
        return null;
    }

    @Override // p017j$.com.android.tools.p018r8.AbstractC1636a, p017j$.util.InterfaceC1779T, p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* bridge */ /* synthetic */ InterfaceC1789b0 trySplit() {
        return null;
    }

    @Override // j$.util.Spliterator.OfInt
    public final boolean tryAdvance(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return false;
    }

    @Override // j$.util.Spliterator.OfInt
    public final void forEachRemaining(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
    }
}
