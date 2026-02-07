package p017j$.util;

import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* renamed from: j$.util.J */
/* loaded from: classes2.dex */
public interface InterfaceC1770J extends InterfaceC1775O {
    @Override // java.util.Iterator, p017j$.util.InterfaceC2129y
    void forEachRemaining(Consumer consumer);

    void forEachRemaining(IntConsumer intConsumer);

    @Override // java.util.Iterator
    Integer next();

    int nextInt();
}
