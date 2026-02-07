package p017j$.util.stream;

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
import java.util.stream.BaseStream;
import java.util.stream.Collector;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1726t;
import p017j$.util.C1791c0;
import p017j$.util.Objects;
import p017j$.util.Optional;
import p017j$.util.Spliterator;
import p017j$.util.stream.IntStream;

/* loaded from: classes2.dex */
public interface Stream<T> extends BaseStream<T, Stream<T>> {

    public final /* synthetic */ class VivifiedWrapper implements Stream {

        /* renamed from: a */
        public final /* synthetic */ java.util.stream.Stream f1122a;

        public /* synthetic */ VivifiedWrapper(java.util.stream.Stream stream) {
            this.f1122a = stream;
        }

        public static /* synthetic */ Stream convert(java.util.stream.Stream stream) {
            if (stream == null) {
                return null;
            }
            return stream instanceof Wrapper ? Stream.this : new VivifiedWrapper(stream);
        }

        @Override // p017j$.util.stream.Stream
        /* renamed from: a */
        public final /* synthetic */ Stream mo998a(C1726t c1726t) {
            return convert(this.f1122a.flatMap(AbstractC2106w1.m1100k0(c1726t)));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ boolean allMatch(Predicate predicate) {
            return this.f1122a.allMatch(predicate);
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ boolean anyMatch(Predicate predicate) {
            return this.f1122a.anyMatch(predicate);
        }

        @Override // p017j$.util.stream.BaseStream, java.lang.AutoCloseable
        public final /* synthetic */ void close() {
            this.f1122a.close();
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Object collect(Collector collector) {
            return this.f1122a.collect(collector == null ? null : collector instanceof C2027h ? ((C2027h) collector).f1254a : new C2033i(collector));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Object collect(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
            return this.f1122a.collect(supplier, biConsumer, biConsumer2);
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ long count() {
            return this.f1122a.count();
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Stream distinct() {
            return convert(this.f1122a.distinct());
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Stream dropWhile(Predicate predicate) {
            return convert(this.f1122a.dropWhile(predicate));
        }

        public final /* synthetic */ boolean equals(Object obj) {
            java.util.stream.Stream stream = this.f1122a;
            if (obj instanceof VivifiedWrapper) {
                obj = ((VivifiedWrapper) obj).f1122a;
            }
            return stream.equals(obj);
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Stream filter(Predicate predicate) {
            return convert(this.f1122a.filter(predicate));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Optional findAny() {
            return AbstractC1636a.m483B(this.f1122a.findAny());
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Optional findFirst() {
            return AbstractC1636a.m483B(this.f1122a.findFirst());
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ void forEach(Consumer consumer) {
            this.f1122a.forEach(consumer);
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ void forEachOrdered(Consumer consumer) {
            this.f1122a.forEachOrdered(consumer);
        }

        public final /* synthetic */ int hashCode() {
            return this.f1122a.hashCode();
        }

        @Override // p017j$.util.stream.BaseStream
        public final /* synthetic */ boolean isParallel() {
            return this.f1122a.isParallel();
        }

        @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
        public final /* synthetic */ Iterator iterator() {
            return this.f1122a.iterator();
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Stream limit(long j) {
            return convert(this.f1122a.limit(j));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Stream map(Function function) {
            return convert(this.f1122a.map(function));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ InterfaceC1871D mapToDouble(ToDoubleFunction toDoubleFunction) {
            return C1861B.m937f(this.f1122a.mapToDouble(toDoubleFunction));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ IntStream mapToInt(ToIntFunction toIntFunction) {
            return IntStream.VivifiedWrapper.convert(this.f1122a.mapToInt(toIntFunction));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ LongStream mapToLong(ToLongFunction toLongFunction) {
            return C2050l0.m1047f(this.f1122a.mapToLong(toLongFunction));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Optional max(Comparator comparator) {
            return AbstractC1636a.m483B(this.f1122a.max(comparator));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Optional min(Comparator comparator) {
            return AbstractC1636a.m483B(this.f1122a.min(comparator));
        }

        @Override // p017j$.util.stream.Stream
        /* renamed from: n */
        public final /* synthetic */ LongStream mo999n(C1726t c1726t) {
            return C2050l0.m1047f(this.f1122a.flatMapToLong(AbstractC2106w1.m1100k0(c1726t)));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ boolean noneMatch(Predicate predicate) {
            return this.f1122a.noneMatch(predicate);
        }

        @Override // p017j$.util.stream.BaseStream
        public final /* synthetic */ BaseStream onClose(Runnable runnable) {
            return C2009e.m1038f(this.f1122a.onClose(runnable));
        }

        @Override // p017j$.util.stream.BaseStream
        public final /* synthetic */ BaseStream parallel() {
            return C2009e.m1038f(this.f1122a.parallel());
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Stream peek(Consumer consumer) {
            return convert(this.f1122a.peek(consumer));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Optional reduce(BinaryOperator binaryOperator) {
            return AbstractC1636a.m483B(this.f1122a.reduce(binaryOperator));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Object reduce(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
            return this.f1122a.reduce(obj, biFunction, binaryOperator);
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Object reduce(Object obj, BinaryOperator binaryOperator) {
            return this.f1122a.reduce(obj, binaryOperator);
        }

        @Override // p017j$.util.stream.BaseStream
        public final /* synthetic */ BaseStream sequential() {
            return C2009e.m1038f(this.f1122a.sequential());
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Stream skip(long j) {
            return convert(this.f1122a.skip(j));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Stream sorted() {
            return convert(this.f1122a.sorted());
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Stream sorted(Comparator comparator) {
            return convert(this.f1122a.sorted(comparator));
        }

        @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
        public final /* synthetic */ Spliterator spliterator() {
            return C1791c0.m867a(this.f1122a.spliterator());
        }

        @Override // p017j$.util.stream.Stream
        /* renamed from: t */
        public final /* synthetic */ IntStream mo1000t(C1726t c1726t) {
            return IntStream.VivifiedWrapper.convert(this.f1122a.flatMapToInt(AbstractC2106w1.m1100k0(c1726t)));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Stream takeWhile(Predicate predicate) {
            return convert(this.f1122a.takeWhile(predicate));
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Object[] toArray() {
            return this.f1122a.toArray();
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ Object[] toArray(IntFunction intFunction) {
            return this.f1122a.toArray(intFunction);
        }

        @Override // p017j$.util.stream.Stream
        public final /* synthetic */ List toList() {
            return this.f1122a.toList();
        }

        @Override // p017j$.util.stream.Stream
        /* renamed from: u */
        public final /* synthetic */ InterfaceC1871D mo1001u(C1726t c1726t) {
            return C1861B.m937f(this.f1122a.flatMapToDouble(AbstractC2106w1.m1100k0(c1726t)));
        }

        @Override // p017j$.util.stream.BaseStream
        public final /* synthetic */ BaseStream unordered() {
            return C2009e.m1038f(this.f1122a.unordered());
        }
    }

    public final /* synthetic */ class Wrapper implements java.util.stream.Stream {
        public /* synthetic */ Wrapper() {
        }

        public static /* synthetic */ java.util.stream.Stream convert(Stream stream) {
            if (stream == null) {
                return null;
            }
            return stream instanceof VivifiedWrapper ? ((VivifiedWrapper) stream).f1122a : new Wrapper();
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ boolean allMatch(Predicate predicate) {
            return Stream.this.allMatch(predicate);
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ boolean anyMatch(Predicate predicate) {
            return Stream.this.anyMatch(predicate);
        }

        @Override // java.util.stream.BaseStream, java.lang.AutoCloseable
        public final /* synthetic */ void close() {
            Stream.this.close();
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ Object collect(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2) {
            return Stream.this.collect(supplier, biConsumer, biConsumer2);
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ Object collect(Collector collector) {
            return Stream.this.collect(collector == null ? null : collector instanceof C2033i ? ((C2033i) collector).f1261a : new C2027h(collector));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ long count() {
            return Stream.this.count();
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.Stream distinct() {
            return convert(Stream.this.distinct());
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.Stream dropWhile(Predicate predicate) {
            return convert(Stream.this.dropWhile(predicate));
        }

        public final /* synthetic */ boolean equals(Object obj) {
            Stream stream = Stream.this;
            if (obj instanceof Wrapper) {
                obj = Stream.this;
            }
            return stream.equals(obj);
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.Stream filter(Predicate predicate) {
            return convert(Stream.this.filter(predicate));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.Optional findAny() {
            return AbstractC1636a.m487F(Stream.this.findAny());
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.Optional findFirst() {
            return AbstractC1636a.m487F(Stream.this.findFirst());
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.Stream flatMap(Function function) {
            return convert(Stream.this.mo998a(AbstractC2106w1.m1100k0(function)));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ DoubleStream flatMapToDouble(Function function) {
            return C1866C.m941f(Stream.this.mo1001u(AbstractC2106w1.m1100k0(function)));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.IntStream flatMapToInt(Function function) {
            return IntStream.Wrapper.convert(Stream.this.mo1000t(AbstractC2106w1.m1100k0(function)));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ LongStream flatMapToLong(Function function) {
            return C2055m0.m1052f(Stream.this.mo999n(AbstractC2106w1.m1100k0(function)));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ void forEach(Consumer consumer) {
            Stream.this.forEach(consumer);
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ void forEachOrdered(Consumer consumer) {
            Stream.this.forEachOrdered(consumer);
        }

        public final /* synthetic */ int hashCode() {
            return Stream.this.hashCode();
        }

        @Override // java.util.stream.BaseStream
        public final /* synthetic */ boolean isParallel() {
            return Stream.this.isParallel();
        }

        @Override // java.util.stream.BaseStream
        public final /* synthetic */ Iterator iterator() {
            return Stream.this.iterator();
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.Stream limit(long j) {
            return convert(Stream.this.limit(j));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.Stream map(Function function) {
            return convert(Stream.this.map(function));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ DoubleStream mapToDouble(ToDoubleFunction toDoubleFunction) {
            return C1866C.m941f(Stream.this.mapToDouble(toDoubleFunction));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.IntStream mapToInt(ToIntFunction toIntFunction) {
            return IntStream.Wrapper.convert(Stream.this.mapToInt(toIntFunction));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ LongStream mapToLong(ToLongFunction toLongFunction) {
            return C2055m0.m1052f(Stream.this.mapToLong(toLongFunction));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.Optional max(Comparator comparator) {
            return AbstractC1636a.m487F(Stream.this.max(comparator));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.Optional min(Comparator comparator) {
            return AbstractC1636a.m487F(Stream.this.min(comparator));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ boolean noneMatch(Predicate predicate) {
            return Stream.this.noneMatch(predicate);
        }

        @Override // java.util.stream.BaseStream
        public final /* synthetic */ BaseStream onClose(Runnable runnable) {
            return C2015f.m1040f(Stream.this.onClose(runnable));
        }

        @Override // java.util.stream.BaseStream
        public final /* synthetic */ BaseStream parallel() {
            return C2015f.m1040f(Stream.this.parallel());
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.Stream peek(Consumer consumer) {
            return convert(Stream.this.peek(consumer));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ Object reduce(Object obj, BiFunction biFunction, BinaryOperator binaryOperator) {
            return Stream.this.reduce(obj, biFunction, binaryOperator);
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ Object reduce(Object obj, BinaryOperator binaryOperator) {
            return Stream.this.reduce(obj, binaryOperator);
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.Optional reduce(BinaryOperator binaryOperator) {
            return AbstractC1636a.m487F(Stream.this.reduce(binaryOperator));
        }

        @Override // java.util.stream.BaseStream
        public final /* synthetic */ BaseStream sequential() {
            return C2015f.m1040f(Stream.this.sequential());
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.Stream skip(long j) {
            return convert(Stream.this.skip(j));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.Stream sorted() {
            return convert(Stream.this.sorted());
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.Stream sorted(Comparator comparator) {
            return convert(Stream.this.sorted(comparator));
        }

        @Override // java.util.stream.BaseStream
        public final /* synthetic */ java.util.Spliterator spliterator() {
            return Spliterator.Wrapper.convert(Stream.this.spliterator());
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ java.util.stream.Stream takeWhile(Predicate predicate) {
            return convert(Stream.this.takeWhile(predicate));
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ Object[] toArray() {
            return Stream.this.toArray();
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ Object[] toArray(IntFunction intFunction) {
            return Stream.this.toArray(intFunction);
        }

        @Override // java.util.stream.Stream
        public final /* synthetic */ List toList() {
            return Stream.this.toList();
        }

        @Override // java.util.stream.BaseStream
        public final /* synthetic */ BaseStream unordered() {
            return C2015f.m1040f(Stream.this.unordered());
        }
    }

    /* renamed from: a */
    Stream mo998a(C1726t c1726t);

    boolean allMatch(Predicate<? super T> predicate);

    boolean anyMatch(Predicate<? super T> predicate);

    <R, A> R collect(Collector<? super T, A, R> collector);

    Object collect(Supplier supplier, BiConsumer biConsumer, BiConsumer biConsumer2);

    long count();

    Stream<T> distinct();

    Stream dropWhile(Predicate predicate);

    Stream<T> filter(Predicate<? super T> predicate);

    Optional findAny();

    Optional<T> findFirst();

    void forEach(Consumer<? super T> consumer);

    void forEachOrdered(Consumer consumer);

    Stream<T> limit(long j);

    <R> Stream<R> map(Function<? super T, ? extends R> function);

    InterfaceC1871D mapToDouble(ToDoubleFunction toDoubleFunction);

    IntStream mapToInt(ToIntFunction<? super T> toIntFunction);

    LongStream mapToLong(ToLongFunction<? super T> toLongFunction);

    Optional max(Comparator comparator);

    Optional min(Comparator comparator);

    /* renamed from: n */
    LongStream mo999n(C1726t c1726t);

    boolean noneMatch(Predicate<? super T> predicate);

    Stream peek(Consumer consumer);

    Optional reduce(BinaryOperator binaryOperator);

    Object reduce(Object obj, BiFunction biFunction, BinaryOperator binaryOperator);

    Object reduce(Object obj, BinaryOperator binaryOperator);

    Stream skip(long j);

    Stream<T> sorted();

    Stream<T> sorted(Comparator<? super T> comparator);

    /* renamed from: t */
    IntStream mo1000t(C1726t c1726t);

    Stream takeWhile(Predicate predicate);

    Object[] toArray();

    <A> A[] toArray(IntFunction<A[]> intFunction);

    List toList();

    /* renamed from: u */
    InterfaceC1871D mo1001u(C1726t c1726t);

    /* renamed from: j$.util.stream.Stream$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static <T> Stream<T> concat(Stream<? extends T> stream, Stream<? extends T> stream2) {
            Objects.requireNonNull(stream);
            Objects.requireNonNull(stream2);
            C2000c2 c2000c2M961b = AbstractC1890G3.m961b(new C1900I3(stream.spliterator(), stream2.spliterator()), stream.isParallel() || stream2.isParallel());
            c2000c2M961b.onClose(new RunnableC1895H3(1, stream, stream2));
            return c2000c2M961b;
        }
    }
}
