package p017j$.util.stream;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import p017j$.time.C1678e;
import p017j$.time.C1726t;
import p017j$.util.Map;
import p017j$.util.function.BiConsumer$CC;

/* loaded from: classes2.dex */
public final class Collectors {

    /* renamed from: a */
    public static final Set f978a;

    /* renamed from: b */
    public static final Set f979b;

    /* renamed from: c */
    public static final Set f980c;

    static {
        EnumC2021g enumC2021g = EnumC2021g.CONCURRENT;
        EnumC2021g enumC2021g2 = EnumC2021g.UNORDERED;
        EnumC2021g enumC2021g3 = EnumC2021g.IDENTITY_FINISH;
        Collections.unmodifiableSet(EnumSet.of(enumC2021g, enumC2021g2, enumC2021g3));
        Collections.unmodifiableSet(EnumSet.of(enumC2021g, enumC2021g2));
        f978a = Collections.unmodifiableSet(EnumSet.of(enumC2021g3));
        f979b = Collections.unmodifiableSet(EnumSet.of(enumC2021g2, enumC2021g3));
        f980c = Collections.EMPTY_SET;
        Collections.unmodifiableSet(EnumSet.of(enumC2021g2));
    }

    public static <T, C extends Collection<T>> Collector<T, ?, C> toCollection(Supplier<C> supplier) {
        return new C2049l(supplier, new C1678e(13), new C1678e(14), f978a);
    }

    public static <T> Collector<T, ?, List<T>> toList() {
        return new C2049l(new C1678e(15), new C1678e(16), new C1678e(21), f978a);
    }

    public static <T> Collector<T, ?, Set<T>> toSet() {
        return new C2049l(new C1678e(18), new C1678e(19), new C1678e(26), f979b);
    }

    public static Collector<CharSequence, ?, String> joining() {
        return new C2049l(new C1678e(27), new C1678e(28), new C1678e(29), new C2044k(0), f980c);
    }

    public static Collector<CharSequence, ?, String> joining(CharSequence charSequence) {
        return new C2049l(new C1726t(6, charSequence), new C1678e(23), new C1678e(24), new C1678e(25), f980c);
    }

    /* renamed from: a */
    public static void m942a(double[] dArr, double d) {
        double d2 = d - dArr[1];
        double d3 = dArr[0];
        double d4 = d3 + d2;
        dArr[1] = (d4 - d3) - d2;
        dArr[0] = d4;
    }

    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(final Function<? super T, ? extends K> function, final Function<? super T, ? extends U> function2, final BinaryOperator<U> binaryOperator, Supplier<M> supplier) {
        return new C2049l(supplier, new BiConsumer() { // from class: j$.util.stream.j
            public final /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer$CC.$default$andThen(this, biConsumer);
            }

            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Set set = Collectors.f978a;
                Map.EL.m855a((java.util.Map) obj, function.apply(obj2), function2.apply(obj2), binaryOperator);
            }
        }, new C1726t(7, binaryOperator), f978a);
    }
}
