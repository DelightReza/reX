package p017j$.util;

import java.util.function.LongConsumer;

/* renamed from: j$.util.Y */
/* loaded from: classes2.dex */
public interface InterfaceC1784Y extends InterfaceC1789b0 {
    void forEachRemaining(LongConsumer longConsumer);

    boolean tryAdvance(LongConsumer longConsumer);

    @Override // p017j$.util.InterfaceC1789b0, p017j$.util.Spliterator
    InterfaceC1784Y trySplit();
}
