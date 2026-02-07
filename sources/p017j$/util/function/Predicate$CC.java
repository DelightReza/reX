package p017j$.util.function;

import java.util.function.Predicate;
import p017j$.time.C1726t;
import p017j$.util.Objects;

/* renamed from: j$.util.function.Predicate$-CC, reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class Predicate$CC {
    public static Predicate $default$and(Predicate predicate, Predicate predicate2) {
        Objects.requireNonNull(predicate2);
        return new C1829h(predicate, predicate2, 0);
    }

    public static Predicate $default$negate(Predicate predicate) {
        return new C1726t(3, predicate);
    }

    public static Predicate $default$or(Predicate predicate, Predicate predicate2) {
        Objects.requireNonNull(predicate2);
        return new C1829h(predicate, predicate2, 1);
    }
}
