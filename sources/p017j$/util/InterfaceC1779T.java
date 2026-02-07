package p017j$.util;

import java.util.function.DoubleConsumer;

/* renamed from: j$.util.T */
/* loaded from: classes2.dex */
public interface InterfaceC1779T extends InterfaceC1789b0 {
    void forEachRemaining(DoubleConsumer doubleConsumer);

    boolean tryAdvance(DoubleConsumer doubleConsumer);

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    InterfaceC1779T trySplit();
}
