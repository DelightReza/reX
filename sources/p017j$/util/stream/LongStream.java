package p017j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import p017j$.time.C1726t;
import p017j$.util.C1761A;
import p017j$.util.C1762B;
import p017j$.util.C2130z;
import p017j$.util.InterfaceC1774N;
import p017j$.util.InterfaceC1784Y;

/* loaded from: classes2.dex */
public interface LongStream extends BaseStream<Long, LongStream> {
    /* renamed from: a */
    LongStream mo976a(C1726t c1726t);

    InterfaceC1871D asDoubleStream();

    C1761A average();

    /* renamed from: b */
    LongStream mo977b();

    Stream boxed();

    /* renamed from: c */
    LongStream mo978c();

    Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer);

    long count();

    /* renamed from: d */
    LongStream mo979d();

    LongStream distinct();

    /* renamed from: e */
    LongStream mo980e();

    C1762B findAny();

    C1762B findFirst();

    void forEach(LongConsumer longConsumer);

    void forEachOrdered(LongConsumer longConsumer);

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    InterfaceC1774N iterator();

    /* renamed from: j */
    InterfaceC1871D mo981j();

    LongStream limit(long j);

    /* renamed from: m */
    boolean mo982m();

    <U> Stream<U> mapToObj(LongFunction<? extends U> longFunction);

    C1762B max();

    C1762B min();

    /* renamed from: p */
    boolean mo983p();

    @Override // p017j$.util.stream.BaseStream
    LongStream parallel();

    LongStream peek(LongConsumer longConsumer);

    long reduce(long j, LongBinaryOperator longBinaryOperator);

    C1762B reduce(LongBinaryOperator longBinaryOperator);

    @Override // p017j$.util.stream.BaseStream
    LongStream sequential();

    LongStream skip(long j);

    LongStream sorted();

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    InterfaceC1784Y spliterator();

    long sum();

    C2130z summaryStatistics();

    long[] toArray();

    /* renamed from: v */
    boolean mo984v();

    /* renamed from: x */
    IntStream mo985x();
}
