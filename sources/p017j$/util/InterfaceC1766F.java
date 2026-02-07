package p017j$.util;

import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

/* renamed from: j$.util.F */
/* loaded from: classes2.dex */
public interface InterfaceC1766F extends InterfaceC1775O {
    @Override // java.util.Iterator, p017j$.util.InterfaceC2129y
    void forEachRemaining(Consumer consumer);

    void forEachRemaining(DoubleConsumer doubleConsumer);

    @Override // java.util.Iterator
    Double next();

    double nextDouble();
}
