package p017j$.util.stream;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.Predicate;
import java.util.stream.Collector;
import p017j$.time.C1726t;
import p017j$.util.C1830g;
import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Objects;
import p017j$.util.Spliterator;
import p017j$.util.concurrent.C1810s;

/* renamed from: j$.util.stream.w1 */
/* loaded from: classes2.dex */
public abstract class AbstractC2106w1 implements InterfaceC1910K3 {

    /* renamed from: a */
    public static final C1976Y0 f1361a = new C1976Y0();

    /* renamed from: b */
    public static final C1966W0 f1362b = new C1966W0();

    /* renamed from: c */
    public static final C1971X0 f1363c = new C1971X0();

    /* renamed from: d */
    public static final C1961V0 f1364d = new C1961V0();

    /* renamed from: e */
    public static final int[] f1365e = new int[0];

    /* renamed from: f */
    public static final long[] f1366f = new long[0];

    /* renamed from: g */
    public static final double[] f1367g = new double[0];

    /* renamed from: Z */
    public abstract void mo1013Z(Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2);

    /* renamed from: a0 */
    public abstract boolean mo1014a0(Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2);

    /* renamed from: d0 */
    public abstract InterfaceC1887G0 mo1015d0(Spliterator spliterator, boolean z, IntFunction intFunction);

    /* renamed from: e0 */
    public abstract long mo1016e0(Spliterator spliterator);

    /* renamed from: q0 */
    public abstract InterfaceC2115y0 mo925q0(long j, IntFunction intFunction);

    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: s */
    public /* synthetic */ int mo904s() {
        return 0;
    }

    /* renamed from: s0 */
    public abstract InterfaceC1942R1 mo939s0();

    /* renamed from: t0 */
    public abstract InterfaceC2062n2 mo1017t0(Spliterator spliterator, InterfaceC2062n2 interfaceC2062n2);

    /* renamed from: u0 */
    public abstract InterfaceC2062n2 mo1018u0(InterfaceC2062n2 interfaceC2062n2);

    /* renamed from: v0 */
    public abstract Spliterator mo1019v0(Spliterator spliterator);

    /* renamed from: k0 */
    public static C1726t m1100k0(Function function) {
        C1726t c1726t = new C1726t(9);
        c1726t.f668b = function;
        return c1726t;
    }

    /* renamed from: j0 */
    public static Set m1099j0(Set set) {
        if (set == null || set.isEmpty()) {
            return set;
        }
        HashSet hashSet = new HashSet();
        Object next = set.iterator().next();
        if (next instanceof EnumC2021g) {
            Iterator it = set.iterator();
            while (it.hasNext()) {
                try {
                    EnumC2021g enumC2021g = (EnumC2021g) it.next();
                    hashSet.add(enumC2021g == null ? null : enumC2021g == EnumC2021g.CONCURRENT ? Collector.Characteristics.CONCURRENT : enumC2021g == EnumC2021g.UNORDERED ? Collector.Characteristics.UNORDERED : Collector.Characteristics.IDENTITY_FINISH);
                } catch (ClassCastException e) {
                    C1830g.m910a(e, "java.util.stream.Collector.Characteristics");
                    throw null;
                }
            }
        } else {
            if (!(next instanceof Collector.Characteristics)) {
                C1830g.m910a(next.getClass(), "java.util.stream.Collector.Characteristics");
                throw null;
            }
            Iterator it2 = set.iterator();
            while (it2.hasNext()) {
                try {
                    Collector.Characteristics characteristics = (Collector.Characteristics) it2.next();
                    hashSet.add(characteristics == null ? null : characteristics == Collector.Characteristics.CONCURRENT ? EnumC2021g.CONCURRENT : characteristics == Collector.Characteristics.UNORDERED ? EnumC2021g.UNORDERED : EnumC2021g.IDENTITY_FINISH);
                } catch (ClassCastException e2) {
                    C1830g.m910a(e2, "java.util.stream.Collector.Characteristics");
                    throw null;
                }
            }
        }
        return hashSet;
    }

    /* renamed from: r0 */
    public static C1810s m1106r0(EnumC2090t0 enumC2090t0, Predicate predicate) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(enumC2090t0);
        return new C1810s(EnumC2001c3.REFERENCE, enumC2090t0, new C1810s(6, enumC2090t0, predicate));
    }

    /* renamed from: c0 */
    public static AbstractC1981Z0 m1094c0(EnumC2001c3 enumC2001c3) {
        int i = AbstractC1892H0.f1013a[enumC2001c3.ordinal()];
        if (i == 1) {
            return f1361a;
        }
        if (i == 2) {
            return f1362b;
        }
        if (i == 3) {
            return f1363c;
        }
        if (i == 4) {
            return f1364d;
        }
        throw new IllegalStateException("Unknown shape " + enumC2001c3);
    }

    /* renamed from: o0 */
    public static C1810s m1104o0(EnumC2090t0 enumC2090t0, IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        Objects.requireNonNull(enumC2090t0);
        return new C1810s(EnumC2001c3.INT_VALUE, enumC2090t0, new C1810s(5, enumC2090t0, intPredicate));
    }

    /* renamed from: S */
    public static InterfaceC1887G0 m1086S(InterfaceC1887G0 interfaceC1887G0, long j, long j2, IntFunction intFunction) {
        if (j == 0 && j2 == interfaceC1887G0.count()) {
            return interfaceC1887G0;
        }
        Spliterator spliterator = interfaceC1887G0.spliterator();
        long j3 = j2 - j;
        InterfaceC2115y0 interfaceC2115y0M1087T = m1087T(j3, intFunction);
        interfaceC2115y0M1087T.mo931h(j3);
        for (int i = 0; i < j && spliterator.tryAdvance(new C1955U(18)); i++) {
        }
        if (j2 == interfaceC1887G0.count()) {
            spliterator.forEachRemaining(interfaceC2115y0M1087T);
        } else {
            for (int i2 = 0; i2 < j3 && spliterator.tryAdvance(interfaceC2115y0M1087T); i2++) {
            }
        }
        interfaceC2115y0M1087T.end();
        return interfaceC2115y0M1087T.build();
    }

    /* renamed from: Y */
    public static AbstractC1897I0 m1092Y(EnumC2001c3 enumC2001c3, InterfaceC1887G0 interfaceC1887G0, InterfaceC1887G0 interfaceC1887G02) {
        int i = AbstractC1892H0.f1013a[enumC2001c3.ordinal()];
        if (i == 1) {
            return new C1941R0(interfaceC1887G0, interfaceC1887G02);
        }
        if (i == 2) {
            return new C1927O0((InterfaceC1867C0) interfaceC1887G0, (InterfaceC1867C0) interfaceC1887G02);
        }
        if (i == 3) {
            return new C1932P0((InterfaceC1877E0) interfaceC1887G0, (InterfaceC1877E0) interfaceC1887G02);
        }
        if (i != 4) {
            throw new IllegalStateException("Unknown shape " + enumC2001c3);
        }
        return new C1922N0((InterfaceC1857A0) interfaceC1887G0, (InterfaceC1857A0) interfaceC1887G02);
    }

    /* renamed from: p0 */
    public static C1810s m1105p0(EnumC2090t0 enumC2090t0) {
        Objects.requireNonNull(null);
        Objects.requireNonNull(enumC2090t0);
        return new C1810s(EnumC2001c3.LONG_VALUE, enumC2090t0, new C2060n0(enumC2090t0, 0));
    }

    /* renamed from: G */
    public static void m1074G() {
        throw new IllegalStateException("called wrong accept method");
    }

    /* renamed from: n0 */
    public static C1810s m1103n0(EnumC2090t0 enumC2090t0) {
        Objects.requireNonNull(null);
        Objects.requireNonNull(enumC2090t0);
        return new C1810s(EnumC2001c3.DOUBLE_VALUE, enumC2090t0, new C2060n0(enumC2090t0, 1));
    }

    /* renamed from: H */
    public static void m1075H() {
        throw new IllegalStateException("called wrong accept method");
    }

    /* renamed from: T */
    public static InterfaceC2115y0 m1087T(long j, IntFunction intFunction) {
        if (j >= 0 && j < 2147483639) {
            return new C1987a1(j, intFunction);
        }
        return new C2086s1();
    }

    /* renamed from: z */
    public static void m1107z() {
        throw new IllegalStateException("called wrong accept method");
    }

    /* renamed from: C */
    public static void m1070C(InterfaceC2052l2 interfaceC2052l2, Integer num) {
        if (AbstractC1920M3.f1068a) {
            AbstractC1920M3.m987a(interfaceC2052l2.getClass(), "{0} calling Sink.OfInt.accept(Integer)");
            throw null;
        }
        interfaceC2052l2.accept(num.intValue());
    }

    /* renamed from: E */
    public static void m1072E(InterfaceC2057m2 interfaceC2057m2, Long l) {
        if (AbstractC1920M3.f1068a) {
            AbstractC1920M3.m987a(interfaceC2057m2.getClass(), "{0} calling Sink.OfLong.accept(Long)");
            throw null;
        }
        interfaceC2057m2.accept(l.longValue());
    }

    /* renamed from: l0 */
    public static InterfaceC2105w0 m1101l0(long j) {
        if (j < 0 || j >= 2147483639) {
            return new C2005d1();
        }
        return new C1999c1(j);
    }

    /* renamed from: A */
    public static void m1068A(InterfaceC2047k2 interfaceC2047k2, Double d) {
        if (AbstractC1920M3.f1068a) {
            AbstractC1920M3.m987a(interfaceC2047k2.getClass(), "{0} calling Sink.OfDouble.accept(Double)");
            throw null;
        }
        interfaceC2047k2.accept(d.doubleValue());
    }

    /* renamed from: m0 */
    public static InterfaceC2110x0 m1102m0(long j) {
        if (j < 0 || j >= 2147483639) {
            return new C2056m1();
        }
        return new C2051l1(j);
    }

    /* renamed from: I */
    public static Object[] m1076I(InterfaceC1882F0 interfaceC1882F0, IntFunction intFunction) {
        if (AbstractC1920M3.f1068a) {
            AbstractC1920M3.m987a(interfaceC1882F0.getClass(), "{0} calling Node.OfPrimitive.asArray");
            throw null;
        }
        if (interfaceC1882F0.count() >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) intFunction.apply((int) interfaceC1882F0.count());
        interfaceC1882F0.mo957f(objArr, 0);
        return objArr;
    }

    /* renamed from: b0 */
    public static InterfaceC2100v0 m1093b0(long j) {
        if (j < 0 || j >= 2147483639) {
            return new C1956U0();
        }
        return new C1951T0(j);
    }

    /* renamed from: U */
    public static InterfaceC1887G0 m1088U(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, boolean z, IntFunction intFunction) {
        long jMo1016e0 = abstractC2106w1.mo1016e0(spliterator);
        if (jMo1016e0 < 0 || !spliterator.hasCharacteristics(16384)) {
            C1911L c1911l = new C1911L();
            c1911l.f1054a = intFunction;
            InterfaceC1887G0 interfaceC1887G0 = (InterfaceC1887G0) new C1912L0(abstractC2106w1, spliterator, c1911l, new C1955U(26), 3).invoke();
            return z ? m1095f0(interfaceC1887G0, intFunction) : interfaceC1887G0;
        }
        if (jMo1016e0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) intFunction.apply((int) jMo1016e0);
        new C2076q1(spliterator, abstractC2106w1, objArr).invoke();
        return new C1902J0(objArr);
    }

    /* renamed from: N */
    public static void m1081N(InterfaceC1867C0 interfaceC1867C0, Consumer consumer) {
        if (consumer instanceof IntConsumer) {
            interfaceC1867C0.mo953d((IntConsumer) consumer);
        } else {
            if (AbstractC1920M3.f1068a) {
                AbstractC1920M3.m987a(interfaceC1867C0.getClass(), "{0} calling Node.OfInt.forEachRemaining(Consumer)");
                throw null;
            }
            ((Spliterator.OfInt) interfaceC1867C0.spliterator()).forEachRemaining(consumer);
        }
    }

    /* renamed from: K */
    public static void m1078K(InterfaceC1867C0 interfaceC1867C0, Integer[] numArr, int i) {
        if (AbstractC1920M3.f1068a) {
            AbstractC1920M3.m987a(interfaceC1867C0.getClass(), "{0} calling Node.OfInt.copyInto(Integer[], int)");
            throw null;
        }
        int[] iArr = (int[]) interfaceC1867C0.mo951b();
        for (int i2 = 0; i2 < iArr.length; i2++) {
            numArr[i + i2] = Integer.valueOf(iArr[i2]);
        }
    }

    /* renamed from: Q */
    public static InterfaceC1867C0 m1084Q(InterfaceC1867C0 interfaceC1867C0, long j, long j2) {
        if (j == 0 && j2 == interfaceC1867C0.count()) {
            return interfaceC1867C0;
        }
        long j3 = j2 - j;
        Spliterator.OfInt ofInt = (Spliterator.OfInt) interfaceC1867C0.spliterator();
        InterfaceC2105w0 interfaceC2105w0M1101l0 = m1101l0(j3);
        interfaceC2105w0M1101l0.mo931h(j3);
        for (int i = 0; i < j && ofInt.tryAdvance((IntConsumer) new C1862B0(0)); i++) {
        }
        if (j2 == interfaceC1867C0.count()) {
            ofInt.forEachRemaining((IntConsumer) interfaceC2105w0M1101l0);
        } else {
            for (int i2 = 0; i2 < j3 && ofInt.tryAdvance((IntConsumer) interfaceC2105w0M1101l0); i2++) {
            }
        }
        interfaceC2105w0M1101l0.end();
        return interfaceC2105w0M1101l0.build();
    }

    /* renamed from: W */
    public static InterfaceC1867C0 m1090W(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, boolean z) {
        long jMo1016e0 = abstractC2106w1.mo1016e0(spliterator);
        if (jMo1016e0 < 0 || !spliterator.hasCharacteristics(16384)) {
            InterfaceC1867C0 interfaceC1867C0 = (InterfaceC1867C0) new C1912L0(abstractC2106w1, spliterator, new C1955U(22), new C1955U(23), 1).invoke();
            return z ? m1097h0(interfaceC1867C0) : interfaceC1867C0;
        }
        if (jMo1016e0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        int[] iArr = new int[(int) jMo1016e0];
        new C2066o1(spliterator, abstractC2106w1, iArr).invoke();
        return new C1993b1(iArr);
    }

    /* renamed from: X */
    public static InterfaceC1877E0 m1091X(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, boolean z) {
        long jMo1016e0 = abstractC2106w1.mo1016e0(spliterator);
        if (jMo1016e0 < 0 || !spliterator.hasCharacteristics(16384)) {
            InterfaceC1877E0 interfaceC1877E0 = (InterfaceC1877E0) new C1912L0(abstractC2106w1, spliterator, new C1955U(24), new C1955U(25), 2).invoke();
            return z ? m1098i0(interfaceC1877E0) : interfaceC1877E0;
        }
        if (jMo1016e0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        long[] jArr = new long[(int) jMo1016e0];
        new C2071p1(spliterator, abstractC2106w1, jArr).invoke();
        return new C2046k1(jArr);
    }

    /* renamed from: O */
    public static void m1082O(InterfaceC1877E0 interfaceC1877E0, Consumer consumer) {
        if (consumer instanceof LongConsumer) {
            interfaceC1877E0.mo953d((LongConsumer) consumer);
        } else {
            if (AbstractC1920M3.f1068a) {
                AbstractC1920M3.m987a(interfaceC1877E0.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                throw null;
            }
            ((InterfaceC1784Y) interfaceC1877E0.spliterator()).forEachRemaining(consumer);
        }
    }

    /* renamed from: L */
    public static void m1079L(InterfaceC1877E0 interfaceC1877E0, Long[] lArr, int i) {
        if (AbstractC1920M3.f1068a) {
            AbstractC1920M3.m987a(interfaceC1877E0.getClass(), "{0} calling Node.OfInt.copyInto(Long[], int)");
            throw null;
        }
        long[] jArr = (long[]) interfaceC1877E0.mo951b();
        for (int i2 = 0; i2 < jArr.length; i2++) {
            lArr[i + i2] = Long.valueOf(jArr[i2]);
        }
    }

    /* renamed from: R */
    public static InterfaceC1877E0 m1085R(InterfaceC1877E0 interfaceC1877E0, long j, long j2) {
        if (j == 0 && j2 == interfaceC1877E0.count()) {
            return interfaceC1877E0;
        }
        long j3 = j2 - j;
        InterfaceC1784Y interfaceC1784Y = (InterfaceC1784Y) interfaceC1877E0.spliterator();
        InterfaceC2110x0 interfaceC2110x0M1102m0 = m1102m0(j3);
        interfaceC2110x0M1102m0.mo931h(j3);
        for (int i = 0; i < j && interfaceC1784Y.tryAdvance((LongConsumer) new C1872D0(0)); i++) {
        }
        if (j2 == interfaceC1877E0.count()) {
            interfaceC1784Y.forEachRemaining((LongConsumer) interfaceC2110x0M1102m0);
        } else {
            for (int i2 = 0; i2 < j3 && interfaceC1784Y.tryAdvance((LongConsumer) interfaceC2110x0M1102m0); i2++) {
            }
        }
        interfaceC2110x0M1102m0.end();
        return interfaceC2110x0M1102m0.build();
    }

    /* renamed from: V */
    public static InterfaceC1857A0 m1089V(AbstractC2106w1 abstractC2106w1, Spliterator spliterator, boolean z) {
        long jMo1016e0 = abstractC2106w1.mo1016e0(spliterator);
        if (jMo1016e0 < 0 || !spliterator.hasCharacteristics(16384)) {
            InterfaceC1857A0 interfaceC1857A0 = (InterfaceC1857A0) new C1912L0(abstractC2106w1, spliterator, new C1955U(20), new C1955U(21), 0).invoke();
            return z ? m1096g0(interfaceC1857A0) : interfaceC1857A0;
        }
        if (jMo1016e0 >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        double[] dArr = new double[(int) jMo1016e0];
        new C2061n1(spliterator, abstractC2106w1, dArr).invoke();
        return new C1946S0(dArr);
    }

    /* renamed from: f0 */
    public static InterfaceC1887G0 m1095f0(InterfaceC1887G0 interfaceC1887G0, IntFunction intFunction) {
        if (interfaceC1887G0.mo959i() <= 0) {
            return interfaceC1887G0;
        }
        long jCount = interfaceC1887G0.count();
        if (jCount >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        Object[] objArr = (Object[]) intFunction.apply((int) jCount);
        new C2101v1(interfaceC1887G0, objArr, 1).invoke();
        return new C1902J0(objArr);
    }

    /* renamed from: M */
    public static void m1080M(InterfaceC1857A0 interfaceC1857A0, Consumer consumer) {
        if (consumer instanceof DoubleConsumer) {
            interfaceC1857A0.mo953d((DoubleConsumer) consumer);
        } else {
            if (AbstractC1920M3.f1068a) {
                AbstractC1920M3.m987a(interfaceC1857A0.getClass(), "{0} calling Node.OfLong.forEachRemaining(Consumer)");
                throw null;
            }
            ((InterfaceC1779T) interfaceC1857A0.spliterator()).forEachRemaining(consumer);
        }
    }

    /* renamed from: h0 */
    public static InterfaceC1867C0 m1097h0(InterfaceC1867C0 interfaceC1867C0) {
        if (interfaceC1867C0.mo959i() <= 0) {
            return interfaceC1867C0;
        }
        long jCount = interfaceC1867C0.count();
        if (jCount >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        int[] iArr = new int[(int) jCount];
        new C2096u1(interfaceC1867C0, iArr, 0).invoke();
        return new C1993b1(iArr);
    }

    /* renamed from: J */
    public static void m1077J(InterfaceC1857A0 interfaceC1857A0, Double[] dArr, int i) {
        if (AbstractC1920M3.f1068a) {
            AbstractC1920M3.m987a(interfaceC1857A0.getClass(), "{0} calling Node.OfDouble.copyInto(Double[], int)");
            throw null;
        }
        double[] dArr2 = (double[]) interfaceC1857A0.mo951b();
        for (int i2 = 0; i2 < dArr2.length; i2++) {
            dArr[i + i2] = Double.valueOf(dArr2[i2]);
        }
    }

    /* renamed from: P */
    public static InterfaceC1857A0 m1083P(InterfaceC1857A0 interfaceC1857A0, long j, long j2) {
        if (j == 0 && j2 == interfaceC1857A0.count()) {
            return interfaceC1857A0;
        }
        long j3 = j2 - j;
        InterfaceC1779T interfaceC1779T = (InterfaceC1779T) interfaceC1857A0.spliterator();
        InterfaceC2100v0 interfaceC2100v0M1093b0 = m1093b0(j3);
        interfaceC2100v0M1093b0.mo931h(j3);
        for (int i = 0; i < j && interfaceC1779T.tryAdvance((DoubleConsumer) new C2120z0(0)); i++) {
        }
        if (j2 == interfaceC1857A0.count()) {
            interfaceC1779T.forEachRemaining((DoubleConsumer) interfaceC2100v0M1093b0);
        } else {
            for (int i2 = 0; i2 < j3 && interfaceC1779T.tryAdvance((DoubleConsumer) interfaceC2100v0M1093b0); i2++) {
            }
        }
        interfaceC2100v0M1093b0.end();
        return interfaceC2100v0M1093b0.build();
    }

    /* renamed from: i0 */
    public static InterfaceC1877E0 m1098i0(InterfaceC1877E0 interfaceC1877E0) {
        if (interfaceC1877E0.mo959i() <= 0) {
            return interfaceC1877E0;
        }
        long jCount = interfaceC1877E0.count();
        if (jCount >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        long[] jArr = new long[(int) jCount];
        new C2091t1(interfaceC1877E0, jArr, 0).invoke();
        return new C2046k1(jArr);
    }

    /* renamed from: g0 */
    public static InterfaceC1857A0 m1096g0(InterfaceC1857A0 interfaceC1857A0) {
        if (interfaceC1857A0.mo959i() <= 0) {
            return interfaceC1857A0;
        }
        long jCount = interfaceC1857A0.count();
        if (jCount >= 2147483639) {
            throw new IllegalArgumentException("Stream size exceeds max array size");
        }
        double[] dArr = new double[(int) jCount];
        new C2091t1(interfaceC1857A0, dArr, 0).invoke();
        return new C1946S0(dArr);
    }

    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: f */
    public Object mo902f(AbstractC1985a abstractC1985a, Spliterator spliterator) {
        InterfaceC1942R1 interfaceC1942R1Mo939s0 = mo939s0();
        abstractC1985a.mo1017t0(spliterator, interfaceC1942R1Mo939s0);
        return interfaceC1942R1Mo939s0.get();
    }

    @Override // p017j$.util.stream.InterfaceC1910K3
    /* renamed from: i */
    public Object mo903i(AbstractC2106w1 abstractC2106w1, Spliterator spliterator) {
        return ((InterfaceC1942R1) new C1977Y1(this, abstractC2106w1, spliterator).invoke()).get();
    }
}
