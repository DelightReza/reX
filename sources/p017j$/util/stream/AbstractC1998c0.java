package p017j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import p017j$.time.C1678e;
import p017j$.util.C1761A;
import p017j$.util.C1767G;
import p017j$.util.C1819e0;
import p017j$.util.C2128x;
import p017j$.util.InterfaceC1770J;
import p017j$.util.Objects;
import p017j$.util.OptionalInt;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.c0 */
/* loaded from: classes2.dex */
public abstract class AbstractC1998c0 extends AbstractC1985a implements IntStream {
    @Override // p017j$.util.stream.IntStream
    public final OptionalInt findAny() {
        return (OptionalInt) m1020w0(C1886G.f1006d);
    }

    @Override // p017j$.util.stream.IntStream
    public final OptionalInt findFirst() {
        return (OptionalInt) m1020w0(C1886G.f1005c);
    }

    @Override // p017j$.util.stream.IntStream
    public final IntStream sorted() {
        return new C1899I2(this, EnumC1995b3.f1199q | EnumC1995b3.f1197o);
    }

    public void forEach(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        m1020w0(new C1921N(intConsumer, false));
    }

    public void forEachOrdered(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        m1020w0(new C1921N(intConsumer, true));
    }

    /* renamed from: I0 */
    public static Spliterator.OfInt m1032I0(Spliterator spliterator) {
        if (spliterator instanceof Spliterator.OfInt) {
            return (Spliterator.OfInt) spliterator;
        }
        if (AbstractC1920M3.f1068a) {
            AbstractC1920M3.m987a(AbstractC1985a.class, "using IntStream.adapt(Spliterator<Integer> s)");
            throw null;
        }
        throw new UnsupportedOperationException("IntStream.adapt(Spliterator<Integer> s)");
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: A0 */
    public final EnumC2001c3 mo916A0() {
        return EnumC2001c3.INT_VALUE;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: y0 */
    public final InterfaceC1887G0 mo929y0(AbstractC1985a abstractC1985a, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return AbstractC2106w1.m1090W(abstractC1985a, spliterator, z);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: H0 */
    public final Spliterator mo917H0(AbstractC1985a abstractC1985a, Supplier supplier, boolean z) {
        return new C2068o3(abstractC1985a, supplier, z);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: z0 */
    public final boolean mo930z0(Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2) {
        IntConsumer c1767g;
        boolean zMo932m;
        Spliterator.OfInt ofIntM1032I0 = m1032I0(spliterator);
        if (interfaceC2062n2 instanceof IntConsumer) {
            c1767g = (IntConsumer) interfaceC2062n2;
        } else {
            if (AbstractC1920M3.f1068a) {
                AbstractC1920M3.m987a(AbstractC1985a.class, "using IntStream.adapt(Sink<Integer> s)");
                throw null;
            }
            Objects.requireNonNull(interfaceC2062n2);
            c1767g = new C1767G(interfaceC2062n2, 1);
        }
        do {
            zMo932m = interfaceC2062n2.mo932m();
            if (zMo932m) {
                break;
            }
        } while (ofIntM1032I0.tryAdvance(c1767g));
        return zMo932m;
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: q0 */
    public final InterfaceC2115y0 mo925q0(long j, IntFunction intFunction) {
        return AbstractC2106w1.m1101l0(j);
    }

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final InterfaceC1770J iterator() {
        Spliterator.OfInt ofIntSpliterator = spliterator();
        Objects.requireNonNull(ofIntSpliterator);
        return new C1819e0(ofIntSpliterator);
    }

    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final Spliterator.OfInt spliterator() {
        return m1032I0(super.spliterator());
    }

    @Override // p017j$.util.stream.IntStream
    public final LongStream asLongStream() {
        return new C2094u(this, 0, 1);
    }

    @Override // p017j$.util.stream.IntStream
    public final InterfaceC1871D asDoubleStream() {
        return new C2084s(this, 0, 3);
    }

    @Override // p017j$.util.stream.IntStream
    public final Stream boxed() {
        return new C2079r(this, 0, new C2044k(28), 1);
    }

    @Override // p017j$.util.stream.IntStream
    public final IntStream map(IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        return new C1965W(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, intUnaryOperator, 1);
    }

    @Override // p017j$.util.stream.IntStream
    public final Stream mapToObj(IntFunction intFunction) {
        Objects.requireNonNull(intFunction);
        return new C2079r(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, intFunction, 1);
    }

    @Override // p017j$.util.stream.IntStream
    /* renamed from: k */
    public final LongStream mo967k() {
        Objects.requireNonNull(null);
        return new C2094u(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, 2);
    }

    @Override // p017j$.util.stream.IntStream
    /* renamed from: g */
    public final InterfaceC1871D mo966g() {
        Objects.requireNonNull(null);
        return new C2084s(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, 4);
    }

    @Override // p017j$.util.stream.IntStream
    public final int reduce(int i, IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return ((Integer) m1020w0(new C1918M1(EnumC2001c3.INT_VALUE, intBinaryOperator, i))).intValue();
    }

    @Override // p017j$.util.stream.IntStream
    /* renamed from: o */
    public final IntStream mo968o(C1911L c1911l) {
        Objects.requireNonNull(c1911l);
        return new C1965W(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n | EnumC1995b3.f1202t, c1911l, 2);
    }

    @Override // p017j$.util.stream.IntStream
    public final OptionalInt reduce(IntBinaryOperator intBinaryOperator) {
        Objects.requireNonNull(intBinaryOperator);
        return (OptionalInt) m1020w0(new C2121z1(EnumC2001c3.INT_VALUE, intBinaryOperator, 3));
    }

    @Override // p017j$.util.stream.IntStream
    public final IntStream filter(IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        return new C1965W(this, EnumC1995b3.f1202t, intPredicate, 3);
    }

    @Override // p017j$.util.stream.IntStream
    public final IntStream peek(IntConsumer intConsumer) {
        Objects.requireNonNull(intConsumer);
        return new C1965W(this, intConsumer);
    }

    @Override // p017j$.util.stream.IntStream
    public final IntStream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return AbstractC2122z2.m1115f(this, 0L, j);
    }

    @Override // p017j$.util.stream.IntStream
    public final IntStream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : AbstractC2122z2.m1115f(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // p017j$.util.stream.IntStream
    public final IntStream takeWhile(IntPredicate intPredicate) {
        int i = AbstractC2038i4.f1264a;
        Objects.requireNonNull(intPredicate);
        return new C1940Q3(this, AbstractC2038i4.f1264a, intPredicate);
    }

    @Override // p017j$.util.stream.IntStream
    public final IntStream dropWhile(IntPredicate intPredicate) {
        int i = AbstractC2038i4.f1264a;
        Objects.requireNonNull(intPredicate);
        return new C1949S3(this, AbstractC2038i4.f1265b, intPredicate);
    }

    @Override // p017j$.util.stream.IntStream
    public final long count() {
        return ((Long) m1020w0(new C1873D1(3))).longValue();
    }

    @Override // p017j$.util.stream.IntStream
    public final IntStream distinct() {
        return ((AbstractC2018f2) boxed()).distinct().mapToInt(new C2044k(27));
    }

    @Override // p017j$.util.stream.IntStream
    public final int sum() {
        return reduce(0, new C1955U(2));
    }

    @Override // p017j$.util.stream.IntStream
    public final OptionalInt min() {
        return reduce(new C2044k(29));
    }

    @Override // p017j$.util.stream.IntStream
    public final OptionalInt max() {
        return reduce(new C1955U(3));
    }

    @Override // p017j$.util.stream.IntStream
    public final C1761A average() {
        long j = ((long[]) collect(new C1955U(4), new C1955U(5), new C1955U(6)))[0];
        return j > 0 ? new C1761A(r0[1] / j) : C1761A.f757c;
    }

    @Override // p017j$.util.stream.IntStream
    public final C2128x summaryStatistics() {
        return (C2128x) collect(new C1678e(17), new C1955U(0), new C1955U(1));
    }

    @Override // p017j$.util.stream.IntStream
    public final Object collect(Supplier supplier, ObjIntConsumer objIntConsumer, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        C2069p c2069p = new C2069p(biConsumer, 1);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objIntConsumer);
        Objects.requireNonNull(c2069p);
        return m1020w0(new C1863B1(EnumC2001c3.INT_VALUE, c2069p, objIntConsumer, supplier, 4));
    }

    @Override // p017j$.util.stream.IntStream
    public final boolean anyMatch(IntPredicate intPredicate) {
        return ((Boolean) m1020w0(AbstractC2106w1.m1104o0(EnumC2090t0.ANY, intPredicate))).booleanValue();
    }

    @Override // p017j$.util.stream.IntStream
    public final boolean allMatch(IntPredicate intPredicate) {
        return ((Boolean) m1020w0(AbstractC2106w1.m1104o0(EnumC2090t0.ALL, intPredicate))).booleanValue();
    }

    @Override // p017j$.util.stream.IntStream
    public final boolean noneMatch(IntPredicate intPredicate) {
        return ((Boolean) m1020w0(AbstractC2106w1.m1104o0(EnumC2090t0.NONE, intPredicate))).booleanValue();
    }

    @Override // p017j$.util.stream.IntStream
    public final int[] toArray() {
        return (int[]) AbstractC2106w1.m1097h0((InterfaceC1867C0) m1021x0(new C2044k(26))).mo951b();
    }
}
