package p017j$.lang;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import p017j$.util.Objects;

/* renamed from: j$.lang.Iterable$-EL, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class Iterable$EL {
    public static void forEach(Iterable iterable, Consumer consumer) {
        if (iterable instanceof InterfaceC1637a) {
            ((InterfaceC1637a) iterable).forEach(consumer);
            return;
        }
        if (!(iterable instanceof Collection)) {
            Iterable$CC.$default$forEach(iterable, consumer);
            return;
        }
        Objects.requireNonNull(consumer);
        Iterator it = ((Collection) iterable).iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }
}
