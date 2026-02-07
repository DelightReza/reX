package p017j$.util.stream;

import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.OptionalDouble;
import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.C1761A;
import p017j$.util.C1768H;
import p017j$.util.C1769I;
import p017j$.util.C1780U;
import p017j$.util.C1781V;
import p017j$.util.C1791c0;
import p017j$.util.C2128x;
import p017j$.util.InterfaceC1770J;
import p017j$.util.OptionalInt;
import p017j$.util.Spliterator;
import p017j$.util.Spliterators;
import p017j$.util.stream.Stream;

/* loaded from: classes2.dex */
public interface IntStream extends BaseStream<Integer, IntStream> {
    boolean allMatch(IntPredicate intPredicate);

    boolean anyMatch(IntPredicate intPredicate);

    InterfaceC1871D asDoubleStream();

    LongStream asLongStream();

    C1761A average();

    Stream boxed();

    Object collect(Supplier supplier, ObjIntConsumer objIntConsumer, BiConsumer biConsumer);

    long count();

    IntStream distinct();

    IntStream dropWhile(IntPredicate intPredicate);

    IntStream filter(IntPredicate intPredicate);

    OptionalInt findAny();

    OptionalInt findFirst();

    void forEach(IntConsumer intConsumer);

    void forEachOrdered(IntConsumer intConsumer);

    /* renamed from: g */
    InterfaceC1871D mo966g();

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    InterfaceC1770J iterator();

    /* renamed from: k */
    LongStream mo967k();

    IntStream limit(long j);

    IntStream map(IntUnaryOperator intUnaryOperator);

    <U> Stream<U> mapToObj(IntFunction<? extends U> intFunction);

    OptionalInt max();

    OptionalInt min();

    boolean noneMatch(IntPredicate intPredicate);

    /* renamed from: o */
    IntStream mo968o(C1911L c1911l);

    @Override // p017j$.util.stream.BaseStream
    IntStream parallel();

    IntStream peek(IntConsumer intConsumer);

    int reduce(int i, IntBinaryOperator intBinaryOperator);

    OptionalInt reduce(IntBinaryOperator intBinaryOperator);

    @Override // p017j$.util.stream.BaseStream
    IntStream sequential();

    IntStream skip(long j);

    IntStream sorted();

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    Spliterator.OfInt spliterator();

    int sum();

    C2128x summaryStatistics();

    IntStream takeWhile(IntPredicate intPredicate);

    int[] toArray();

    public final /* synthetic */ class Wrapper implements java.util.stream.IntStream {
        public /* synthetic */ Wrapper() {
        }

        public static /* synthetic */ java.util.stream.IntStream convert(IntStream intStream) {
            if (intStream == null) {
                return null;
            }
            return intStream instanceof VivifiedWrapper ? ((VivifiedWrapper) intStream).f1034a : intStream.new Wrapper();
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ boolean allMatch(IntPredicate intPredicate) {
            return IntStream.this.allMatch(intPredicate);
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ boolean anyMatch(IntPredicate intPredicate) {
            return IntStream.this.anyMatch(intPredicate);
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ DoubleStream asDoubleStream() {
            return C1866C.m941f(IntStream.this.asDoubleStream());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ LongStream asLongStream() {
            return C2055m0.m1052f(IntStream.this.asLongStream());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ OptionalDouble average() {
            return AbstractC1636a.m488G(IntStream.this.average());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ Stream boxed() {
            return Stream.Wrapper.convert(IntStream.this.boxed());
        }

        @Override // java.util.stream.BaseStream, java.lang.AutoCloseable
        public final /* synthetic */ void close() {
            IntStream.this.close();
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ Object collect(Supplier supplier, ObjIntConsumer objIntConsumer, BiConsumer biConsumer) {
            return IntStream.this.collect(supplier, objIntConsumer, biConsumer);
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ long count() {
            return IntStream.this.count();
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.stream.IntStream distinct() {
            return convert(IntStream.this.distinct());
        }

        public final /* synthetic */ java.util.stream.IntStream dropWhile(IntPredicate intPredicate) {
            return convert(IntStream.this.dropWhile(intPredicate));
        }

        public final /* synthetic */ boolean equals(Object obj) {
            IntStream intStream = IntStream.this;
            if (obj instanceof Wrapper) {
                obj = IntStream.this;
            }
            return intStream.equals(obj);
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.stream.IntStream filter(IntPredicate intPredicate) {
            return convert(IntStream.this.filter(intPredicate));
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.OptionalInt findAny() {
            return AbstractC1636a.m489H(IntStream.this.findAny());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.OptionalInt findFirst() {
            return AbstractC1636a.m489H(IntStream.this.findFirst());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ void forEach(IntConsumer intConsumer) {
            IntStream.this.forEach(intConsumer);
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ void forEachOrdered(IntConsumer intConsumer) {
            IntStream.this.forEachOrdered(intConsumer);
        }

        public final /* synthetic */ int hashCode() {
            return IntStream.this.hashCode();
        }

        @Override // java.util.stream.BaseStream
        public final /* synthetic */ boolean isParallel() {
            return IntStream.this.isParallel();
        }

        @Override // java.util.stream.IntStream, java.util.stream.BaseStream
        public final /* synthetic */ Iterator<Integer> iterator() {
            return IntStream.this.iterator();
        }

        @Override // java.util.stream.IntStream, java.util.stream.BaseStream
        /* renamed from: iterator, reason: avoid collision after fix types in other method */
        public final /* synthetic */ Iterator<Integer> iterator2() {
            InterfaceC1770J it = IntStream.this.iterator();
            if (it == null) {
                return null;
            }
            return it instanceof C1768H ? ((C1768H) it).f771a : new C1769I(it);
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.stream.IntStream limit(long j) {
            return convert(IntStream.this.limit(j));
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.stream.IntStream map(IntUnaryOperator intUnaryOperator) {
            return convert(IntStream.this.map(intUnaryOperator));
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ DoubleStream mapToDouble(IntToDoubleFunction intToDoubleFunction) {
            return C1866C.m941f(IntStream.this.mo966g());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ LongStream mapToLong(IntToLongFunction intToLongFunction) {
            return C2055m0.m1052f(IntStream.this.mo967k());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.stream.Stream mapToObj(IntFunction intFunction) {
            return Stream.Wrapper.convert(IntStream.this.mapToObj(intFunction));
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.OptionalInt max() {
            return AbstractC1636a.m489H(IntStream.this.max());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.OptionalInt min() {
            return AbstractC1636a.m489H(IntStream.this.min());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ boolean noneMatch(IntPredicate intPredicate) {
            return IntStream.this.noneMatch(intPredicate);
        }

        @Override // java.util.stream.BaseStream
        public final /* synthetic */ BaseStream onClose(Runnable runnable) {
            return C2015f.m1040f(IntStream.this.onClose(runnable));
        }

        @Override // java.util.stream.IntStream, java.util.stream.BaseStream
        public final /* synthetic */ BaseStream parallel() {
            return C2015f.m1040f(IntStream.this.parallel());
        }

        @Override // java.util.stream.IntStream, java.util.stream.BaseStream
        public final /* synthetic */ java.util.stream.IntStream parallel() {
            return convert(IntStream.this.parallel());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.stream.IntStream peek(IntConsumer intConsumer) {
            return convert(IntStream.this.peek(intConsumer));
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ int reduce(int i, IntBinaryOperator intBinaryOperator) {
            return IntStream.this.reduce(i, intBinaryOperator);
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.OptionalInt reduce(IntBinaryOperator intBinaryOperator) {
            return AbstractC1636a.m489H(IntStream.this.reduce(intBinaryOperator));
        }

        @Override // java.util.stream.IntStream, java.util.stream.BaseStream
        public final /* synthetic */ BaseStream sequential() {
            return C2015f.m1040f(IntStream.this.sequential());
        }

        @Override // java.util.stream.IntStream, java.util.stream.BaseStream
        public final /* synthetic */ java.util.stream.IntStream sequential() {
            return convert(IntStream.this.sequential());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.stream.IntStream skip(long j) {
            return convert(IntStream.this.skip(j));
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ java.util.stream.IntStream sorted() {
            return convert(IntStream.this.sorted());
        }

        @Override // java.util.stream.IntStream, java.util.stream.BaseStream
        public final /* synthetic */ java.util.Spliterator<Integer> spliterator() {
            return C1781V.m861a(IntStream.this.spliterator());
        }

        @Override // java.util.stream.IntStream, java.util.stream.BaseStream
        /* renamed from: spliterator, reason: avoid collision after fix types in other method */
        public final /* synthetic */ java.util.Spliterator<Integer> spliterator2() {
            return Spliterator.Wrapper.convert(IntStream.this.spliterator());
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ int sum() {
            return IntStream.this.sum();
        }

        public final /* synthetic */ java.util.stream.IntStream takeWhile(IntPredicate intPredicate) {
            return convert(IntStream.this.takeWhile(intPredicate));
        }

        @Override // java.util.stream.IntStream
        public final /* synthetic */ int[] toArray() {
            return IntStream.this.toArray();
        }

        @Override // java.util.stream.BaseStream
        public final /* synthetic */ BaseStream unordered() {
            return C2015f.m1040f(IntStream.this.unordered());
        }

        @Override // java.util.stream.IntStream
        public final IntSummaryStatistics summaryStatistics() {
            IntStream.this.summaryStatistics();
            throw new Error("Java 8+ API desugaring (library desugaring) cannot convert to java.util.IntSummaryStatistics");
        }

        @Override // java.util.stream.IntStream
        public final java.util.stream.IntStream flatMap(IntFunction intFunction) {
            IntStream intStream = IntStream.this;
            C1911L c1911l = new C1911L();
            c1911l.f1054a = intFunction;
            return convert(intStream.mo968o(c1911l));
        }
    }

    public final /* synthetic */ class VivifiedWrapper implements IntStream {

        /* renamed from: a */
        public final /* synthetic */ java.util.stream.IntStream f1034a;

        public /* synthetic */ VivifiedWrapper(java.util.stream.IntStream intStream) {
            this.f1034a = intStream;
        }

        public static /* synthetic */ IntStream convert(java.util.stream.IntStream intStream) {
            if (intStream == null) {
                return null;
            }
            return intStream instanceof Wrapper ? IntStream.this : new VivifiedWrapper(intStream);
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ boolean allMatch(IntPredicate intPredicate) {
            return this.f1034a.allMatch(intPredicate);
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ boolean anyMatch(IntPredicate intPredicate) {
            return this.f1034a.anyMatch(intPredicate);
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ InterfaceC1871D asDoubleStream() {
            return C1861B.m937f(this.f1034a.asDoubleStream());
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ LongStream asLongStream() {
            return C2050l0.m1047f(this.f1034a.asLongStream());
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ C1761A average() {
            return AbstractC1636a.m484C(this.f1034a.average());
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ Stream boxed() {
            return Stream.VivifiedWrapper.convert(this.f1034a.boxed());
        }

        @Override // p017j$.util.stream.BaseStream, java.lang.AutoCloseable
        public final /* synthetic */ void close() {
            this.f1034a.close();
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ Object collect(Supplier supplier, ObjIntConsumer objIntConsumer, BiConsumer biConsumer) {
            return this.f1034a.collect(supplier, objIntConsumer, biConsumer);
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ long count() {
            return this.f1034a.count();
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ IntStream distinct() {
            return convert(this.f1034a.distinct());
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ IntStream dropWhile(IntPredicate intPredicate) {
            return convert(this.f1034a.dropWhile(intPredicate));
        }

        public final /* synthetic */ boolean equals(Object obj) {
            java.util.stream.IntStream intStream = this.f1034a;
            if (obj instanceof VivifiedWrapper) {
                obj = ((VivifiedWrapper) obj).f1034a;
            }
            return intStream.equals(obj);
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ IntStream filter(IntPredicate intPredicate) {
            return convert(this.f1034a.filter(intPredicate));
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ OptionalInt findAny() {
            return AbstractC1636a.m485D(this.f1034a.findAny());
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ OptionalInt findFirst() {
            return AbstractC1636a.m485D(this.f1034a.findFirst());
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ void forEach(IntConsumer intConsumer) {
            this.f1034a.forEach(intConsumer);
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ void forEachOrdered(IntConsumer intConsumer) {
            this.f1034a.forEachOrdered(intConsumer);
        }

        @Override // p017j$.util.stream.IntStream
        /* renamed from: g */
        public final /* synthetic */ InterfaceC1871D mo966g() {
            return C1861B.m937f(this.f1034a.mapToDouble(null));
        }

        public final /* synthetic */ int hashCode() {
            return this.f1034a.hashCode();
        }

        @Override // p017j$.util.stream.BaseStream
        public final /* synthetic */ boolean isParallel() {
            return this.f1034a.isParallel();
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [java.util.PrimitiveIterator$OfInt] */
        @Override // p017j$.util.stream.IntStream, p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
        public final /* synthetic */ InterfaceC1770J iterator() {
            ?? it = this.f1034a.iterator();
            if (it == 0) {
                return null;
            }
            return it instanceof C1769I ? ((C1769I) it).f772a : new C1768H(it);
        }

        @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
        public final /* synthetic */ Iterator iterator() {
            return this.f1034a.iterator();
        }

        @Override // p017j$.util.stream.IntStream
        /* renamed from: k */
        public final /* synthetic */ LongStream mo967k() {
            return C2050l0.m1047f(this.f1034a.mapToLong(null));
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ IntStream limit(long j) {
            return convert(this.f1034a.limit(j));
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ IntStream map(IntUnaryOperator intUnaryOperator) {
            return convert(this.f1034a.map(intUnaryOperator));
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ Stream mapToObj(IntFunction intFunction) {
            return Stream.VivifiedWrapper.convert(this.f1034a.mapToObj(intFunction));
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ OptionalInt max() {
            return AbstractC1636a.m485D(this.f1034a.max());
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ OptionalInt min() {
            return AbstractC1636a.m485D(this.f1034a.min());
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ boolean noneMatch(IntPredicate intPredicate) {
            return this.f1034a.noneMatch(intPredicate);
        }

        @Override // p017j$.util.stream.BaseStream
        public final /* synthetic */ BaseStream onClose(Runnable runnable) {
            return C2009e.m1038f(this.f1034a.onClose(runnable));
        }

        @Override // p017j$.util.stream.BaseStream
        public final /* synthetic */ BaseStream parallel() {
            return C2009e.m1038f(this.f1034a.parallel());
        }

        @Override // p017j$.util.stream.IntStream, p017j$.util.stream.BaseStream
        public final /* synthetic */ IntStream parallel() {
            return convert(this.f1034a.parallel());
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ IntStream peek(IntConsumer intConsumer) {
            return convert(this.f1034a.peek(intConsumer));
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ int reduce(int i, IntBinaryOperator intBinaryOperator) {
            return this.f1034a.reduce(i, intBinaryOperator);
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ OptionalInt reduce(IntBinaryOperator intBinaryOperator) {
            return AbstractC1636a.m485D(this.f1034a.reduce(intBinaryOperator));
        }

        @Override // p017j$.util.stream.BaseStream
        public final /* synthetic */ BaseStream sequential() {
            return C2009e.m1038f(this.f1034a.sequential());
        }

        @Override // p017j$.util.stream.IntStream, p017j$.util.stream.BaseStream
        public final /* synthetic */ IntStream sequential() {
            return convert(this.f1034a.sequential());
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ IntStream skip(long j) {
            return convert(this.f1034a.skip(j));
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ IntStream sorted() {
            return convert(this.f1034a.sorted());
        }

        /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Spliterator$OfInt] */
        @Override // p017j$.util.stream.IntStream, p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
        public final /* synthetic */ Spliterator.OfInt spliterator() {
            return C1780U.m860a(this.f1034a.spliterator());
        }

        @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
        public final /* synthetic */ Spliterator spliterator() {
            return C1791c0.m867a(this.f1034a.spliterator());
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ int sum() {
            return this.f1034a.sum();
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ IntStream takeWhile(IntPredicate intPredicate) {
            return convert(this.f1034a.takeWhile(intPredicate));
        }

        @Override // p017j$.util.stream.IntStream
        public final /* synthetic */ int[] toArray() {
            return this.f1034a.toArray();
        }

        @Override // p017j$.util.stream.BaseStream
        public final /* synthetic */ BaseStream unordered() {
            return C2009e.m1038f(this.f1034a.unordered());
        }

        @Override // p017j$.util.stream.IntStream
        public final C2128x summaryStatistics() {
            this.f1034a.summaryStatistics();
            throw new Error("Java 8+ API desugaring (library desugaring) cannot convert from java.util.IntSummaryStatistics");
        }

        @Override // p017j$.util.stream.IntStream
        /* renamed from: o */
        public final IntStream mo968o(C1911L c1911l) {
            java.util.stream.IntStream intStream = this.f1034a;
            C1911L c1911l2 = new C1911L();
            c1911l2.f1054a = c1911l;
            return convert(intStream.flatMap(c1911l2));
        }
    }

    /* renamed from: j$.util.stream.IntStream$-CC, reason: invalid class name */
    public final /* synthetic */ class CC {
        public static IntStream range(int i, int i2) {
            if (i >= i2) {
                return AbstractC1890G3.m960a(Spliterators.f787b);
            }
            return AbstractC1890G3.m960a(new C1905J3(i, i2));
        }
    }
}
