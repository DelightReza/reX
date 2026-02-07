package p017j$.util.stream;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.Supplier;
import p017j$.time.C1678e;
import p017j$.time.C1726t;
import p017j$.util.C1761A;
import p017j$.util.C1763C;
import p017j$.util.C1831g0;
import p017j$.util.C2127w;
import p017j$.util.InterfaceC1766F;
import p017j$.util.InterfaceC1779T;
import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.A */
/* loaded from: classes2.dex */
public abstract class AbstractC1856A extends AbstractC1985a implements InterfaceC1871D {
    @Override // p017j$.util.stream.InterfaceC1871D
    public final C1761A findAny() {
        return (C1761A) m1020w0(C1881F.f1001d);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final C1761A findFirst() {
        return (C1761A) m1020w0(C1881F.f1000c);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final InterfaceC1871D sorted() {
        return new C1894H2(this, EnumC1995b3.f1199q | EnumC1995b3.f1197o, 0);
    }

    /* renamed from: I0 */
    public static InterfaceC1779T m915I0(Spliterator spliterator) {
        if (spliterator instanceof InterfaceC1779T) {
            return (InterfaceC1779T) spliterator;
        }
        if (AbstractC1920M3.f1068a) {
            AbstractC1920M3.m987a(AbstractC1985a.class, "using DoubleStream.adapt(Spliterator<Double> s)");
            throw null;
        }
        throw new UnsupportedOperationException("DoubleStream.adapt(Spliterator<Double> s)");
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public void forEach(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        m1020w0(new C1916M(doubleConsumer, false));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public void forEachOrdered(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        m1020w0(new C1916M(doubleConsumer, true));
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: A0 */
    public final EnumC2001c3 mo916A0() {
        return EnumC2001c3.DOUBLE_VALUE;
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: y0 */
    public final InterfaceC1887G0 mo929y0(AbstractC1985a abstractC1985a, Spliterator spliterator, boolean z, IntFunction intFunction) {
        return AbstractC2106w1.m1089V(abstractC1985a, spliterator, z);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: H0 */
    public final Spliterator mo917H0(AbstractC1985a abstractC1985a, Supplier supplier, boolean z) {
        return new C2058m3(abstractC1985a, supplier, z);
    }

    @Override // p017j$.util.stream.AbstractC1985a
    /* renamed from: z0 */
    public final boolean mo930z0(Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2) {
        DoubleConsumer c1763c;
        boolean zMo932m;
        InterfaceC1779T interfaceC1779TM915I0 = m915I0(spliterator);
        if (interfaceC2062n2 instanceof DoubleConsumer) {
            c1763c = (DoubleConsumer) interfaceC2062n2;
        } else {
            if (AbstractC1920M3.f1068a) {
                AbstractC1920M3.m987a(AbstractC1985a.class, "using DoubleStream.adapt(Sink<Double> s)");
                throw null;
            }
            Objects.requireNonNull(interfaceC2062n2);
            c1763c = new C1763C(interfaceC2062n2, 1);
        }
        do {
            zMo932m = interfaceC2062n2.mo932m();
            if (zMo932m) {
                break;
            }
        } while (interfaceC1779TM915I0.tryAdvance(c1763c));
        return zMo932m;
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: q0 */
    public final InterfaceC2115y0 mo925q0(long j, IntFunction intFunction) {
        return AbstractC2106w1.m1093b0(j);
    }

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final InterfaceC1766F iterator() {
        InterfaceC1779T interfaceC1779TSpliterator = spliterator();
        Objects.requireNonNull(interfaceC1779TSpliterator);
        return new C1831g0(interfaceC1779TSpliterator);
    }

    @Override // p017j$.util.stream.AbstractC1985a, p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final InterfaceC1779T spliterator() {
        return m915I0(super.spliterator());
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final Stream boxed() {
        return new C2079r(this, 0, new C2044k(11), 0);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: e */
    public final InterfaceC1871D mo922e() {
        Objects.requireNonNull(null);
        return new C2084s(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, 0);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final Stream mapToObj(DoubleFunction doubleFunction) {
        Objects.requireNonNull(doubleFunction);
        return new C2079r(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, doubleFunction, 0);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: w */
    public final IntStream mo927w() {
        Objects.requireNonNull(null);
        return new C2089t(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, 0);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: r */
    public final LongStream mo926r() {
        Objects.requireNonNull(null);
        return new C2094u(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n, 0);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: a */
    public final InterfaceC1871D mo918a(C1726t c1726t) {
        Objects.requireNonNull(c1726t);
        return new C2104w(this, EnumC1995b3.f1198p | EnumC1995b3.f1196n | EnumC1995b3.f1202t, c1726t, 0);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: c */
    public final InterfaceC1871D mo920c() {
        Objects.requireNonNull(null);
        return new C2084s(this, EnumC1995b3.f1202t, 2);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final InterfaceC1871D peek(DoubleConsumer doubleConsumer) {
        Objects.requireNonNull(doubleConsumer);
        return new C2104w(this, doubleConsumer);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final InterfaceC1871D limit(long j) {
        if (j < 0) {
            throw new IllegalArgumentException(Long.toString(j));
        }
        return AbstractC2122z2.m1114e(this, 0L, j);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final InterfaceC1871D skip(long j) {
        if (j >= 0) {
            return j == 0 ? this : AbstractC2122z2.m1114e(this, j, -1L);
        }
        throw new IllegalArgumentException(Long.toString(j));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: b */
    public final InterfaceC1871D mo919b() {
        int i = AbstractC2038i4.f1264a;
        Objects.requireNonNull(null);
        return new C1894H2(this, AbstractC2038i4.f1264a, 1);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: d */
    public final InterfaceC1871D mo921d() {
        int i = AbstractC2038i4.f1264a;
        Objects.requireNonNull(null);
        return new C1894H2(this, AbstractC2038i4.f1265b, 2);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final InterfaceC1871D distinct() {
        return ((AbstractC2018f2) boxed()).distinct().mapToDouble(new C2044k(12));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final double sum() {
        double[] dArr = (double[]) collect(new C2044k(15), new C2044k(16), new C2044k(4));
        Set set = Collectors.f978a;
        double d = dArr[0] + dArr[1];
        double d2 = dArr[dArr.length - 1];
        return (Double.isNaN(d) && Double.isInfinite(d2)) ? d2 : d;
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final C1761A min() {
        return reduce(new C2044k(5));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final C1761A max() {
        return reduce(new C2044k(14));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final C1761A average() {
        double[] dArr = (double[]) collect(new C2044k(6), new C2044k(7), new C2044k(8));
        if (dArr[2] <= 0.0d) {
            return C1761A.f757c;
        }
        Set set = Collectors.f978a;
        double d = dArr[0] + dArr[1];
        double d2 = dArr[dArr.length - 1];
        if (Double.isNaN(d) && Double.isInfinite(d2)) {
            d = d2;
        }
        return new C1761A(d / dArr[2]);
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final C2127w summaryStatistics() {
        return (C2127w) collect(new C1678e(12), new C2044k(9), new C2044k(10));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final Object collect(Supplier supplier, ObjDoubleConsumer objDoubleConsumer, BiConsumer biConsumer) {
        Objects.requireNonNull(biConsumer);
        C2069p c2069p = new C2069p(biConsumer, 0);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(objDoubleConsumer);
        Objects.requireNonNull(c2069p);
        return m1020w0(new C1863B1(EnumC2001c3.DOUBLE_VALUE, c2069p, objDoubleConsumer, supplier, 1));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: l */
    public final boolean mo923l() {
        return ((Boolean) m1020w0(AbstractC2106w1.m1103n0(EnumC2090t0.ANY))).booleanValue();
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: q */
    public final boolean mo924q() {
        return ((Boolean) m1020w0(AbstractC2106w1.m1103n0(EnumC2090t0.ALL))).booleanValue();
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    /* renamed from: y */
    public final boolean mo928y() {
        return ((Boolean) m1020w0(AbstractC2106w1.m1103n0(EnumC2090t0.NONE))).booleanValue();
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final double[] toArray() {
        return (double[]) AbstractC2106w1.m1096g0((InterfaceC1857A0) m1021x0(new C2044k(13))).mo951b();
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final double reduce(double d, DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return ((Double) m1020w0(new C1883F1(EnumC2001c3.DOUBLE_VALUE, doubleBinaryOperator, d))).doubleValue();
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final C1761A reduce(DoubleBinaryOperator doubleBinaryOperator) {
        Objects.requireNonNull(doubleBinaryOperator);
        return (C1761A) m1020w0(new C2121z1(EnumC2001c3.DOUBLE_VALUE, doubleBinaryOperator, 1));
    }

    @Override // p017j$.util.stream.InterfaceC1871D
    public final long count() {
        return ((Long) m1020w0(new C1873D1(1))).longValue();
    }
}
