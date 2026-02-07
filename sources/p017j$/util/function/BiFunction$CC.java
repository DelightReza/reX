package p017j$.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import p017j$.util.Objects;
import p017j$.util.concurrent.C1810s;

/* renamed from: j$.util.function.BiFunction$-CC, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class BiFunction$CC {
    public static BiFunction $default$andThen(BiFunction biFunction, Function function) {
        Objects.requireNonNull(function);
        return new C1810s(biFunction, function);
    }
}
