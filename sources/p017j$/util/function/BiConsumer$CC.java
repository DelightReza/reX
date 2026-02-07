package p017j$.util.function;

import java.util.function.BiConsumer;
import p017j$.util.Objects;
import p017j$.util.concurrent.C1810s;

/* renamed from: j$.util.function.BiConsumer$-CC, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class BiConsumer$CC {
    public static BiConsumer $default$andThen(BiConsumer biConsumer, BiConsumer biConsumer2) {
        Objects.requireNonNull(biConsumer2);
        return new C1810s(1, biConsumer, biConsumer2);
    }
}
