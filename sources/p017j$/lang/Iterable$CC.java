package p017j$.lang;

import java.util.Iterator;
import java.util.function.Consumer;
import p017j$.util.Objects;

/* renamed from: j$.lang.Iterable$-CC, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class Iterable$CC {
    public static void $default$forEach(Iterable iterable, Consumer consumer) {
        Objects.requireNonNull(consumer);
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            consumer.accept(it.next());
        }
    }
}
