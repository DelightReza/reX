package p017j$.util.function;

import java.util.function.IntPredicate;
import p017j$.time.C1726t;
import p017j$.util.Objects;

/* renamed from: j$.util.function.IntPredicate$-CC, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class IntPredicate$CC {
    public static IntPredicate $default$and(IntPredicate intPredicate, IntPredicate intPredicate2) {
        Objects.requireNonNull(intPredicate2);
        return new C1826e(intPredicate, intPredicate2, 1);
    }

    public static IntPredicate $default$negate(IntPredicate intPredicate) {
        return new C1726t(2, intPredicate);
    }

    public static IntPredicate $default$or(IntPredicate intPredicate, IntPredicate intPredicate2) {
        Objects.requireNonNull(intPredicate2);
        return new C1826e(intPredicate, intPredicate2, 0);
    }
}
