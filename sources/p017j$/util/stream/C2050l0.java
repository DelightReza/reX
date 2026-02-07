package p017j$.util.stream;

import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.stream.LongStream;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1726t;
import p017j$.util.C1761A;
import p017j$.util.C1762B;
import p017j$.util.C1772L;
import p017j$.util.C1773M;
import p017j$.util.C1782W;
import p017j$.util.C1791c0;
import p017j$.util.C2130z;
import p017j$.util.InterfaceC1774N;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Spliterator;
import p017j$.util.stream.IntStream;
import p017j$.util.stream.Stream;

/* renamed from: j$.util.stream.l0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2050l0 implements LongStream {

    /* renamed from: a */
    public final /* synthetic */ LongStream f1288a;

    public /* synthetic */ C2050l0(LongStream longStream) {
        this.f1288a = longStream;
    }

    /* renamed from: f */
    public static /* synthetic */ LongStream m1047f(LongStream longStream) {
        if (longStream == null) {
            return null;
        }
        return longStream instanceof C2055m0 ? ((C2055m0) longStream).f1294a : new C2050l0(longStream);
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ InterfaceC1871D asDoubleStream() {
        return C1861B.m937f(this.f1288a.asDoubleStream());
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ C1761A average() {
        return AbstractC1636a.m484C(this.f1288a.average());
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: b */
    public final /* synthetic */ LongStream mo977b() {
        return m1047f(this.f1288a.takeWhile(null));
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ Stream boxed() {
        return Stream.VivifiedWrapper.convert(this.f1288a.boxed());
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: c */
    public final /* synthetic */ LongStream mo978c() {
        return m1047f(this.f1288a.filter(null));
    }

    @Override // p017j$.util.stream.BaseStream, java.lang.AutoCloseable
    public final /* synthetic */ void close() {
        this.f1288a.close();
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer) {
        return this.f1288a.collect(supplier, objLongConsumer, biConsumer);
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ long count() {
        return this.f1288a.count();
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: d */
    public final /* synthetic */ LongStream mo979d() {
        return m1047f(this.f1288a.dropWhile(null));
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ LongStream distinct() {
        return m1047f(this.f1288a.distinct());
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: e */
    public final /* synthetic */ LongStream mo980e() {
        return m1047f(this.f1288a.map(null));
    }

    public final /* synthetic */ boolean equals(Object obj) {
        LongStream longStream = this.f1288a;
        if (obj instanceof C2050l0) {
            obj = ((C2050l0) obj).f1288a;
        }
        return longStream.equals(obj);
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ C1762B findAny() {
        return AbstractC1636a.m486E(this.f1288a.findAny());
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ C1762B findFirst() {
        return AbstractC1636a.m486E(this.f1288a.findFirst());
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ void forEach(LongConsumer longConsumer) {
        this.f1288a.forEach(longConsumer);
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ void forEachOrdered(LongConsumer longConsumer) {
        this.f1288a.forEachOrdered(longConsumer);
    }

    public final /* synthetic */ int hashCode() {
        return this.f1288a.hashCode();
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ boolean isParallel() {
        return this.f1288a.isParallel();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.PrimitiveIterator$OfLong] */
    @Override // p017j$.util.stream.LongStream, p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ InterfaceC1774N iterator() {
        ?? it = this.f1288a.iterator();
        if (it == 0) {
            return null;
        }
        return it instanceof C1773M ? ((C1773M) it).f776a : new C1772L(it);
    }

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ Iterator iterator() {
        return this.f1288a.iterator();
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: j */
    public final /* synthetic */ InterfaceC1871D mo981j() {
        return C1861B.m937f(this.f1288a.mapToDouble(null));
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ LongStream limit(long j) {
        return m1047f(this.f1288a.limit(j));
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: m */
    public final /* synthetic */ boolean mo982m() {
        return this.f1288a.allMatch(null);
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ Stream mapToObj(LongFunction longFunction) {
        return Stream.VivifiedWrapper.convert(this.f1288a.mapToObj(longFunction));
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ C1762B max() {
        return AbstractC1636a.m486E(this.f1288a.max());
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ C1762B min() {
        return AbstractC1636a.m486E(this.f1288a.min());
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream onClose(Runnable runnable) {
        return C2009e.m1038f(this.f1288a.onClose(runnable));
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: p */
    public final /* synthetic */ boolean mo983p() {
        return this.f1288a.noneMatch(null);
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream parallel() {
        return C2009e.m1038f(this.f1288a.parallel());
    }

    @Override // p017j$.util.stream.LongStream, p017j$.util.stream.BaseStream
    public final /* synthetic */ LongStream parallel() {
        return m1047f(this.f1288a.parallel());
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ LongStream peek(LongConsumer longConsumer) {
        return m1047f(this.f1288a.peek(longConsumer));
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ long reduce(long j, LongBinaryOperator longBinaryOperator) {
        return this.f1288a.reduce(j, longBinaryOperator);
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ C1762B reduce(LongBinaryOperator longBinaryOperator) {
        return AbstractC1636a.m486E(this.f1288a.reduce(longBinaryOperator));
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream sequential() {
        return C2009e.m1038f(this.f1288a.sequential());
    }

    @Override // p017j$.util.stream.LongStream, p017j$.util.stream.BaseStream
    public final /* synthetic */ LongStream sequential() {
        return m1047f(this.f1288a.sequential());
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ LongStream skip(long j) {
        return m1047f(this.f1288a.skip(j));
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ LongStream sorted() {
        return m1047f(this.f1288a.sorted());
    }

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ Spliterator spliterator() {
        return C1791c0.m867a(this.f1288a.spliterator());
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Spliterator$OfLong] */
    @Override // p017j$.util.stream.LongStream, p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ InterfaceC1784Y spliterator() {
        return C1782W.m862a(this.f1288a.spliterator());
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ long sum() {
        return this.f1288a.sum();
    }

    @Override // p017j$.util.stream.LongStream
    public final /* synthetic */ long[] toArray() {
        return this.f1288a.toArray();
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream unordered() {
        return C2009e.m1038f(this.f1288a.unordered());
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: v */
    public final /* synthetic */ boolean mo984v() {
        return this.f1288a.anyMatch(null);
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: x */
    public final /* synthetic */ IntStream mo985x() {
        return IntStream.VivifiedWrapper.convert(this.f1288a.mapToInt(null));
    }

    @Override // p017j$.util.stream.LongStream
    public final C2130z summaryStatistics() {
        this.f1288a.summaryStatistics();
        throw new Error("Java 8+ API desugaring (library desugaring) cannot convert from java.util.LongSummaryStatistics");
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: a */
    public final LongStream mo976a(C1726t c1726t) {
        LongStream longStream = this.f1288a;
        C1726t c1726t2 = new C1726t(10);
        c1726t2.f668b = c1726t;
        return m1047f(longStream.flatMap(c1726t2));
    }
}
