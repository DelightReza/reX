package p017j$.util.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import p017j$.time.C1726t;
import p017j$.util.C1817d0;
import p017j$.util.Objects;
import p017j$.util.Optional;
import p017j$.util.Spliterator;
import p017j$.util.concurrent.C1810s;
import p017j$.util.function.C1822a;

/* renamed from: j$.util.stream.f2 */
/* loaded from: classes2.dex */
public abstract class AbstractC2018f2 extends AbstractC1985a implements Stream {
    @Override // p017j$.util.stream.Stream
    public final Stream sorted() {
        return new C1909K2(this);
    }

    @Override // p017j$.util.stream.Stream
    public final Stream distinct() {
        return new C2064o(this, EnumC1995b3.f1195m | EnumC1995b3.f1202t);
    }

    @Override // p017j$.util.stream.Stream
    public final Optional min(Comparator comparator) {
        Objects.requireNonNull(comparator);
        return reduce(new C1822a(comparator, 1));
    }

    @Override // p017j$.util.stream.Stream
    public final Optional findAny() {
        return (Optional) m1020w0(C1896I.f1022d);
    }

    @Override // p017j$.util.stream.Stream
    public final Optional findFirst() {
        return (Optional) m1020w0(C1896I.f1021c);
    }

    @Override // p017j$.util.stream.Stream
    public final Stream sorted(Comparator comparator) {
        return new C1909K2(this, comparator);
    }

    @Override // p017j$.util.stream.Stream
    public final Object reduce(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
        Objects.requireNonNull(biFunction);
        Objects.requireNonNull(binaryOperator);
        return m1020w0(new C1863B1(EnumC2001c3.REFERENCE, binaryOperator, biFunction, obj, 2));
    }

    @Override // p017j$.util.stream.Stream
    public final Object reduce(Object obj, BinaryOperator binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        Objects.requireNonNull(binaryOperator);
        return m1020w0(new C1863B1(EnumC2001c3.REFERENCE, binaryOperator, binaryOperator, obj, 2));
    }

    public void forEach(Consumer consumer) {
        Objects.requireNonNull(consumer);
        m1020w0(new C1931P(consumer, false));
    }

    public void forEachOrdered(Consumer consumer) {
        Objects.requireNonNull(consumer);
        m1020w0(new C1931P(consumer, true));
    }

    @Override // p017j$.util.stream.Stream
    public final Optional max(Comparator comparator) {
        Objects.requireNonNull(comparator);
        return reduce(new C1822a(comparator, 0));
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: A0 */
    public final EnumC2001c3 mo916A0() {
        return EnumC2001c3.REFERENCE;
    }

    @Override // p017j$.util.stream.Stream
    public final Optional reduce(BinaryOperator binaryOperator) {
        Objects.requireNonNull(binaryOperator);
        return (Optional) m1020w0(new C2121z1(EnumC2001c3.REFERENCE, binaryOperator, 2));
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: y0 */
    public final InterfaceC1887G0 mo929y0(AbstractC1985a abstractC1985a, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return AbstractC2106w1.m1088U(abstractC1985a, spliterator, z, intFunction);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: H0 */
    public final Spliterator mo917H0(AbstractC1985a abstractC1985a, Supplier supplier, boolean z) {
        return new C1885F3(abstractC1985a, supplier, z);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: z0 */
    public final boolean mo930z0(Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2) {
        boolean zMo932m;
        do {
            zMo932m = interfaceC2062n2.mo932m();
            if (zMo932m) {
                break;
            }
        } while (spliterator.tryAdvance(interfaceC2062n2));
        return zMo932m;
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: q0 */
    public final InterfaceC2115y0 mo925q0(long j, IntFunction intFunction) {
        return AbstractC2106w1.m1087T(j, intFunction);
    }

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final Iterator iterator() {
        Spliterator spliterator = spliterator();
        Objects.requireNonNull(spliterator);
        return new C1817d0(spliterator);
    }

    @Override // p017j$.util.stream.Stream
    public final Stream filter(Predicate predicate) {
        Objects.requireNonNull(predicate);
        return new C2079r(this, EnumC1995b3.f1202t, predicate, 4);
    }

    @Override // p017j$.util.stream.Stream
    public final Stream map(Function function) {
        Objects.requireNonNull(function);
        return new C2079r(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, function, 5);
    }

    @Override // p017j$.util.stream.Stream
    public final IntStream mapToInt(ToIntFunction toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        return new C1965W(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, toIntFunction, 4);
    }

    @Override // p017j$.util.stream.Stream
    public final Object collect(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(biConsumer);
        Objects.requireNonNull(biConsumer2);
        return m1020w0(new C1863B1(EnumC2001c3.REFERENCE, biConsumer2, biConsumer, supplier, 3));
    }

    @Override // p017j$.util.stream.Stream
    public final LongStream mapToLong(ToLongFunction toLongFunction) {
        Objects.requireNonNull(toLongFunction);
        return new C2022g0(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, toLongFunction, 3);
    }

    @Override // p017j$.util.stream.Stream
    public final InterfaceC1871D mapToDouble(ToDoubleFunction toDoubleFunction) {
        Objects.requireNonNull(toDoubleFunction);
        return new C2104w(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, toDoubleFunction, 2);
    }

    @Override // p017j$.util.stream.Stream
    public final long count() {
        return ((Long) m1020w0(new C1873D1(2))).longValue();
    }

    @Override // p017j$.util.stream.Stream
    /* renamed from: a */
    public final Stream mo998a(C1726t c1726t) {
        Objects.requireNonNull(c1726t);
        return new C2079r(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n | EnumC1995b3.f1202t, c1726t, 6);
    }

    @Override // p017j$.util.stream.Stream
    /* renamed from: t */
    public final IntStream mo1000t(C1726t c1726t) {
        Objects.requireNonNull(c1726t);
        return new C1965W(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n | EnumC1995b3.f1202t, c1726t, 5);
    }

    @Override // p017j$.util.stream.Stream
    /* renamed from: u */
    public final InterfaceC1871D mo1001u(C1726t c1726t) {
        Objects.requireNonNull(c1726t);
        return new C2104w(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n | EnumC1995b3.f1202t, c1726t, 3);
    }

    @Override // p017j$.util.stream.Stream
    public final Object collect(Collector collector) {
        Collector collector2;
        Object objM1020w0;
        if (!this.f1162h.f1172r || !collector.characteristics().contains(EnumC2021g.CONCURRENT) || (EnumC1995b3.ORDERED.m1030n(this.f1167m) && !collector.characteristics().contains(EnumC2021g.UNORDERED))) {
            Supplier supplier = ((Collector) Objects.requireNonNull(collector)).supplier();
            collector2 = collector;
            objM1020w0 = m1020w0(new C1898I1(EnumC2001c3.REFERENCE, collector.combiner(), collector.accumulator(), supplier, collector2));
        } else {
            objM1020w0 = collector.supplier().get();
            forEach(new C1810s(8, collector.accumulator(), objM1020w0));
            collector2 = collector;
        }
        return collector2.characteristics().contains(EnumC2021g.IDENTITY_FINISH) ? objM1020w0 : collector2.finisher().apply(objM1020w0);
    }

    @Override // p017j$.util.stream.Stream
    /* renamed from: n */
    public final LongStream mo999n(C1726t c1726t) {
        Objects.requireNonNull(c1726t);
        return new C2022g0(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n | EnumC1995b3.f1202t, c1726t, 2);
    }

    @Override // p017j$.util.stream.Stream
    public final Stream peek(Consumer consumer) {
        Objects.requireNonNull(consumer);
        return new C2079r(this, consumer);
    }

    @Override // p017j$.util.stream.Stream
    public final Stream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return AbstractC2122z2.m1117h(this, 0L, j);
    }

    @Override // p017j$.util.stream.Stream
    public final Stream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : AbstractC2122z2.m1117h(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // p017j$.util.stream.Stream
    public final Stream takeWhile(Predicate predicate) {
        int i = AbstractC2038i4.f1264a;
        Objects.requireNonNull(predicate);
        return new C1925N3(this, AbstractC2038i4.f1264a, predicate, 0);
    }

    @Override // p017j$.util.stream.Stream
    public final Stream dropWhile(Predicate predicate) {
        int i = AbstractC2038i4.f1264a;
        Objects.requireNonNull(predicate);
        return new C1925N3(this, AbstractC2038i4.f1265b, predicate, 1);
    }

    @Override // p017j$.util.stream.Stream
    public final Object[] toArray(IntFunction intFunction) {
        return AbstractC2106w1.m1095f0(m1021x0(intFunction), intFunction).mo958g(intFunction);
    }

    @Override // p017j$.util.stream.Stream
    public final Object[] toArray() {
        return toArray(new C1955U(27));
    }

    @Override // p017j$.util.stream.Stream
    public final boolean anyMatch(Predicate predicate) {
        return ((Boolean) m1020w0(AbstractC2106w1.m1106r0(EnumC2090t0.ANY, predicate))).booleanValue();
    }

    @Override // p017j$.util.stream.Stream
    public final boolean allMatch(Predicate predicate) {
        return ((Boolean) m1020w0(AbstractC2106w1.m1106r0(EnumC2090t0.ALL, predicate))).booleanValue();
    }

    @Override // p017j$.util.stream.Stream
    public final boolean noneMatch(Predicate predicate) {
        return ((Boolean) m1020w0(AbstractC2106w1.m1106r0(EnumC2090t0.NONE, predicate))).booleanValue();
    }

    @Override // p017j$.util.stream.Stream
    public final List toList() {
        return Collections.unmodifiableList(new ArrayList(Arrays.asList(toArray())));
    }
}
