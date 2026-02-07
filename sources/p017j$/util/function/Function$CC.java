package p017j$.util.function;

import java.util.function.Function;
import p017j$.time.C1678e;
import p017j$.util.Objects;

/* renamed from: j$.util.function.Function$-CC, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class Function$CC {
    public static Function $default$compose(Function function, Function function2) {
        Objects.requireNonNull(function2);
        return new C1824c(function, function2, 1);
    }

    public static Function $default$andThen(Function function, Function function2) {
        Objects.requireNonNull(function2);
        return new C1824c(function, function2, 0);
    }

    public static <T> Function<T, T> identity() {
        return new C1678e(10);
    }
}
