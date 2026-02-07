package p017j$.util;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Spliterator;

/* renamed from: j$.util.j0 */
/* loaded from: classes2.dex */
public final class C1837j0 extends AbstractC1636a implements InterfaceC1779T {
    @Override // p017j$.util.Spliterator
    public final /* synthetic */ void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m513i(this, consumer);
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
        return AbstractC1636a.m527w(this, consumer);
    }

    @Override // p017j$.com.android.tools.p018r8.AbstractC1636a, p017j$.util.InterfaceC1779T, p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* bridge */ /* synthetic */ InterfaceC1779T trySplit() {
        return null;
    }

    @Override // p017j$.com.android.tools.p018r8.AbstractC1636a, p017j$.util.InterfaceC1779T, p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    public final /* bridge */ /* synthetic */ InterfaceC1789b0 trySplit() {
        return null;
    }

    @Override // p017j$.util.InterfaceC1779T
    public final boolean tryAdvance(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        return false;
    }

    @Override // p017j$.util.InterfaceC1779T
    public final void forEachRemaining(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
    }
}
