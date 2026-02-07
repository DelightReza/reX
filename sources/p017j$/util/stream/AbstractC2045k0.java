package p017j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import p017j$.time.C1678e;
import p017j$.time.C1726t;
import p017j$.util.C1761A;
import p017j$.util.C1762B;
import p017j$.util.C1771K;
import p017j$.util.C1821f0;
import p017j$.util.C2130z;
import p017j$.util.InterfaceC1774N;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.k0 */
/* loaded from: classes2.dex */
public abstract class AbstractC2045k0 extends AbstractC1985a implements LongStream {
    @Override // p017j$.util.stream.LongStream
    public final C1762B findAny() {
        return (C1762B) m1020w0(C1891H.f1012d);
    }

    @Override // p017j$.util.stream.LongStream
    public final C1762B findFirst() {
        return (C1762B) m1020w0(C1891H.f1011c);
    }

    @Override // p017j$.util.stream.LongStream
    public final LongStream sorted() {
        return new C1904J2(this, EnumC1995b3.f1199q | EnumC1995b3.f1197o, 0);
    }

    public void forEach(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        m1020w0(new C1926O(longConsumer, false));
    }

    public void forEachOrdered(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        m1020w0(new C1926O(longConsumer, true));
    }

    /* renamed from: I0 */
    public static InterfaceC1784Y m1046I0(Spliterator spliterator) {
        if (spliterator instanceof InterfaceC1784Y) {
            return (InterfaceC1784Y) spliterator;
        }
        if (AbstractC1920M3.f1068a) {
            AbstractC1920M3.m987a(AbstractC1985a.class, "using LongStream.adapt(Spliterator<Long> s)");
            throw null;
        }
        throw new UnsupportedOperationException("LongStream.adapt(Spliterator<Long> s)");
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: A0 */
    public final EnumC2001c3 mo916A0() {
        return EnumC2001c3.LONG_VALUE;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: y0 */
    public final InterfaceC1887G0 mo929y0(AbstractC1985a abstractC1985a, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return AbstractC2106w1.m1091X(abstractC1985a, spliterator, z);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: H0 */
    public final Spliterator mo917H0(AbstractC1985a abstractC1985a, Supplier supplier, boolean z) {
        return new C2078q3(abstractC1985a, supplier, z);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: z0 */
    public final boolean mo930z0(Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2) {
        LongConsumer c1771k;
        boolean zMo932m;
        InterfaceC1784Y interfaceC1784YM1046I0 = m1046I0(spliterator);
        if (interfaceC2062n2 instanceof LongConsumer) {
            c1771k = (LongConsumer) interfaceC2062n2;
        } else {
            if (AbstractC1920M3.f1068a) {
                AbstractC1920M3.m987a(AbstractC1985a.class, "using LongStream.adapt(Sink<Long> s)");
                throw null;
            }
            Objects.requireNonNull(interfaceC2062n2);
            c1771k = new C1771K(interfaceC2062n2, 1);
        }
        do {
            zMo932m = interfaceC2062n2.mo932m();
            if (zMo932m) {
                break;
            }
        } while (interfaceC1784YM1046I0.tryAdvance(c1771k));
        return zMo932m;
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: q0 */
    public final InterfaceC2115y0 mo925q0(long j, IntFunction intFunction) {
        return AbstractC2106w1.m1102m0(j);
    }

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final InterfaceC1774N iterator() {
        InterfaceC1784Y interfaceC1784YSpliterator = spliterator();
        Objects.requireNonNull(interfaceC1784YSpliterator);
        return new C1821f0(interfaceC1784YSpliterator);
    }

    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final InterfaceC1784Y spliterator() {
        return m1046I0(super.spliterator());
    }

    @Override // p017j$.util.stream.LongStream
    public final InterfaceC1871D asDoubleStream() {
        return new C2084s(this, EnumC1995b3.f1196n, 5);
    }

    @Override // p017j$.util.stream.LongStream
    public final Stream boxed() {
        return new C2079r(this, 0, new C1955U(12), 2);
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: e */
    public final LongStream mo980e() {
        Objects.requireNonNull(null);
        return new C2094u(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, 3);
    }

    @Override // p017j$.util.stream.LongStream
    public final Stream mapToObj(LongFunction longFunction) {
        Objects.requireNonNull(longFunction);
        return new C2079r(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, longFunction, 2);
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: x */
    public final IntStream mo985x() {
        Objects.requireNonNull(null);
        return new C2089t(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, 2);
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: j */
    public final InterfaceC1871D mo981j() {
        Objects.requireNonNull(null);
        return new C2084s(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, 6);
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: a */
    public final LongStream mo976a(C1726t c1726t) {
        Objects.requireNonNull(c1726t);
        return new C2022g0(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n | EnumC1995b3.f1202t, c1726t, 0);
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: c */
    public final LongStream mo978c() {
        Objects.requireNonNull(null);
        return new C2094u(this, EnumC1995b3.f1202t, 5);
    }

    @Override // p017j$.util.stream.LongStream
    public final LongStream peek(LongConsumer longConsumer) {
        Objects.requireNonNull(longConsumer);
        return new C2022g0(this, longConsumer);
    }

    @Override // p017j$.util.stream.LongStream
    public final LongStream limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return AbstractC2122z2.m1116g(this, 0L, j);
    }

    @Override // p017j$.util.stream.LongStream
    public final LongStream skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : AbstractC2122z2.m1116g(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: b */
    public final LongStream mo977b() {
        int i = AbstractC2038i4.f1264a;
        Objects.requireNonNull(null);
        return new C1904J2(this, AbstractC2038i4.f1264a, 1);
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: d */
    public final LongStream mo979d() {
        int i = AbstractC2038i4.f1264a;
        Objects.requireNonNull(null);
        return new C1904J2(this, AbstractC2038i4.f1265b, 2);
    }

    @Override // p017j$.util.stream.LongStream
    public final LongStream distinct() {
        return ((AbstractC2018f2) boxed()).distinct().mapToLong(new C1955U(9));
    }

    @Override // p017j$.util.stream.LongStream
    public final long sum() {
        return reduce(0L, new C1955U(17));
    }

    @Override // p017j$.util.stream.LongStream
    public final C1762B min() {
        return reduce(new C1955U(8));
    }

    @Override // p017j$.util.stream.LongStream
    public final C1762B max() {
        return reduce(new C1955U(16));
    }

    @Override // p017j$.util.stream.LongStream
    public final C1761A average() {
        long j = ((long[]) collect(new C1955U(13), new C1955U(14), new C1955U(15)))[0];
        return j > 0 ? new C1761A(r0[1] / j) : C1761A.f757c;
    }

    @Override // p017j$.util.stream.LongStream
    public final long reduce(long j, LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return ((Long) m1020w0(new C2111x1(EnumC2001c3.LONG_VALUE, longBinaryOperator, j))).longValue();
    }

    @Override // p017j$.util.stream.LongStream
    public final C2130z summaryStatistics() {
        return (C2130z) collect(new C1678e(20), new C1955U(7), new C1955U(10));
    }

    @Override // p017j$.util.stream.LongStream
    public final Object collect(Supplier supplier, ObjLongConsumer objLongConsumer, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        C2069p c2069p = new C2069p(biConsumer, 2);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objLongConsumer);
        Objects.requireNonNull(c2069p);
        return m1020w0(new C1863B1(EnumC2001c3.LONG_VALUE, c2069p, objLongConsumer, supplier, 0));
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: v */
    public final boolean mo984v() {
        return ((Boolean) m1020w0(AbstractC2106w1.m1105p0(EnumC2090t0.ANY))).booleanValue();
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: m */
    public final boolean mo982m() {
        return ((Boolean) m1020w0(AbstractC2106w1.m1105p0(EnumC2090t0.ALL))).booleanValue();
    }

    @Override // p017j$.util.stream.LongStream
    public final C1762B reduce(LongBinaryOperator longBinaryOperator) {
        Objects.requireNonNull(longBinaryOperator);
        return (C1762B) m1020w0(new C2121z1(EnumC2001c3.LONG_VALUE, longBinaryOperator, 0));
    }

    @Override // p017j$.util.stream.LongStream
    /* renamed from: p */
    public final boolean mo983p() {
        return ((Boolean) m1020w0(AbstractC2106w1.m1105p0(EnumC2090t0.NONE))).booleanValue();
    }

    @Override // p017j$.util.stream.LongStream
    public final long[] toArray() {
        return (long[]) AbstractC2106w1.m1098i0((InterfaceC1877E0) m1021x0(new C1955U(11))).mo951b();
    }

    @Override // p017j$.util.stream.LongStream
    public final long count() {
        return ((Long) m1020w0(new C1873D1(0))).longValue();
    }
}
