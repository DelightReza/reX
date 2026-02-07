package p017j$.util.stream;

import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1726t;
import p017j$.util.C1761A;
import p017j$.util.C1764D;
import p017j$.util.C1765E;
import p017j$.util.C1777Q;
import p017j$.util.C1791c0;
import p017j$.util.C2127w;
import p017j$.util.InterfaceC1766F;
import p017j$.util.InterfaceC1779T;
import p017j$.util.Spliterator;
import p017j$.util.stream.IntStream;
import p017j$.util.stream.Stream;

/* renamed from: j$.util.stream.B */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1861B implements InterfaceC1871D {

    /* renamed from: a */
    public final /* synthetic */ DoubleStream f964a;

    public /* synthetic */ C1861B(DoubleStream doubleStream) {
        this.f964a = doubleStream;
    }

    /* renamed from: f */
    public static /* synthetic */ InterfaceC1871D m937f(DoubleStream doubleStream) {
        if (doubleStream == null) {
            return null;
        }
        return doubleStream instanceof C1866C ? ((C1866C) doubleStream).f972a : new C1861B(doubleStream);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ C1761A average() {
        return AbstractC1636a.m484C(this.f964a.average());
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: b */
    public final /* synthetic */ InterfaceC1871D mo919b() {
        return m937f(this.f964a.takeWhile(null));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ Stream boxed() {
        return Stream.VivifiedWrapper.convert(this.f964a.boxed());
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: c */
    public final /* synthetic */ InterfaceC1871D mo920c() {
        return m937f(this.f964a.filter(null));
    }

    @Override // p017j$.util.stream.BaseStream, java.lang.AutoCloseable
    public final /* synthetic */ void close() {
        this.f964a.close();
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer) {
        return this.f964a.collect(supplier, objDoubleConsumer, biConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ long count() {
        return this.f964a.count();
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: d */
    public final /* synthetic */ InterfaceC1871D mo921d() {
        return m937f(this.f964a.dropWhile(null));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ InterfaceC1871D distinct() {
        return m937f(this.f964a.distinct());
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: e */
    public final /* synthetic */ InterfaceC1871D mo922e() {
        return m937f(this.f964a.map(null));
    }

    public final /* synthetic */ boolean equals(Object obj) {
        DoubleStream doubleStream = this.f964a;
        if (obj instanceof C1861B) {
            obj = ((C1861B) obj).f964a;
        }
        return doubleStream.equals(obj);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ C1761A findAny() {
        return AbstractC1636a.m484C(this.f964a.findAny());
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ C1761A findFirst() {
        return AbstractC1636a.m484C(this.f964a.findFirst());
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ void forEach(DoubleConsumer doubleConsumer) {
        this.f964a.forEach(doubleConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ void forEachOrdered(DoubleConsumer doubleConsumer) {
        this.f964a.forEachOrdered(doubleConsumer);
    }

    public final /* synthetic */ int hashCode() {
        return this.f964a.hashCode();
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ boolean isParallel() {
        return this.f964a.isParallel();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.PrimitiveIterator$OfDouble] */
    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ InterfaceC1766F iterator() {
        ?? it = this.f964a.iterator();
        if (it == 0) {
            return null;
        }
        return it instanceof C1765E ? ((C1765E) it).f768a : new C1764D(it);
    }

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ Iterator iterator() {
        return this.f964a.iterator();
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: l */
    public final /* synthetic */ boolean mo923l() {
        return this.f964a.anyMatch(null);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ InterfaceC1871D limit(long j) {
        return m937f(this.f964a.limit(j));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ Stream mapToObj(DoubleFunction doubleFunction) {
        return Stream.VivifiedWrapper.convert(this.f964a.mapToObj(doubleFunction));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ C1761A max() {
        return AbstractC1636a.m484C(this.f964a.max());
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ C1761A min() {
        return AbstractC1636a.m484C(this.f964a.min());
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream onClose(Runnable runnable) {
        return C2009e.m1038f(this.f964a.onClose(runnable));
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream parallel() {
        return C2009e.m1038f(this.f964a.parallel());
    }

    @Override // p017j$.util.stream.InterfaceC1871D, p017j$.util.stream.BaseStream
    public final /* synthetic */ InterfaceC1871D parallel() {
        return m937f(this.f964a.parallel());
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ InterfaceC1871D peek(DoubleConsumer doubleConsumer) {
        return m937f(this.f964a.peek(doubleConsumer));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: q */
    public final /* synthetic */ boolean mo924q() {
        return this.f964a.allMatch(null);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: r */
    public final /* synthetic */ LongStream mo926r() {
        return C2050l0.m1047f(this.f964a.mapToLong(null));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
        return this.f964a.reduce(d, doubleBinaryOperator);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ C1761A reduce(DoubleBinaryOperator doubleBinaryOperator) {
        return AbstractC1636a.m484C(this.f964a.reduce(doubleBinaryOperator));
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream sequential() {
        return C2009e.m1038f(this.f964a.sequential());
    }

    @Override // p017j$.util.stream.InterfaceC1871D, p017j$.util.stream.BaseStream
    public final /* synthetic */ InterfaceC1871D sequential() {
        return m937f(this.f964a.sequential());
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ InterfaceC1871D skip(long j) {
        return m937f(this.f964a.skip(j));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ InterfaceC1871D sorted() {
        return m937f(this.f964a.sorted());
    }

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ Spliterator spliterator() {
        return C1791c0.m867a(this.f964a.spliterator());
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [java.util.Spliterator$OfDouble] */
    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ InterfaceC1779T spliterator() {
        return C1777Q.m857a(this.f964a.spliterator());
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ double sum() {
        return this.f964a.sum();
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ double[] toArray() {
        return this.f964a.toArray();
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream unordered() {
        return C2009e.m1038f(this.f964a.unordered());
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: w */
    public final /* synthetic */ IntStream mo927w() {
        return IntStream.VivifiedWrapper.convert(this.f964a.mapToInt(null));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: y */
    public final /* synthetic */ boolean mo928y() {
        return this.f964a.noneMatch(null);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final C2127w summaryStatistics() {
        this.f964a.summaryStatistics();
        throw new Error("Java 8+ API desugaring (library desugaring) cannot convert from java.util.DoubleSummaryStatistics");
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: a */
    public final InterfaceC1871D mo918a(C1726t c1726t) {
        DoubleStream doubleStream = this.f964a;
        C1726t c1726t2 = new C1726t(8);
        c1726t2.f668b = c1726t;
        return m937f(doubleStream.flatMap(c1726t2));
    }
}
