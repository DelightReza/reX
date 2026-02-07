package p017j$.util;

import java.util.function.Consumer;
import java.util.function.LongConsumer;

/* renamed from: j$.util.N */
/* loaded from: classes2.dex */
public interface InterfaceC1774N extends InterfaceC1775O {
    @Override // java.util.Iterator, p017j$.util.InterfaceC2129y
    void forEachRemaining(Consumer consumer);

    void forEachRemaining(LongConsumer longConsumer);

    @Override // java.util.Iterator
    Long next();

    long nextLong();
}
