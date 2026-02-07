package p017j$.util.function;

import java.util.function.Consumer;
import p017j$.util.Objects;
import p017j$.util.concurrent.C1810s;

/* renamed from: j$.util.function.Consumer$-CC, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class Consumer$CC {
    public static Consumer $default$andThen(Consumer consumer, Consumer consumer2) {
        Objects.requireNonNull(consumer2);
        return new C1810s(3, consumer, consumer2);
    }
}
