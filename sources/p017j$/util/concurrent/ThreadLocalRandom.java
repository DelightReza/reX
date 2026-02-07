package p017j$.util.concurrent;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.security.AccessController;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.telegram.tgnet.ConnectionsManager;
import p017j$.util.C1853r0;
import p017j$.util.stream.AbstractC1890G3;
import p017j$.util.stream.C1866C;
import p017j$.util.stream.C2028h0;
import p017j$.util.stream.C2055m0;
import p017j$.util.stream.C2109x;
import p017j$.util.stream.EnumC1995b3;
import p017j$.util.stream.IntStream;

/* loaded from: classes2.dex */
public class ThreadLocalRandom extends Random {
    private static final long serialVersionUID = -5851777807851030925L;

    /* renamed from: a */
    public long f822a;

    /* renamed from: b */
    public int f823b;

    /* renamed from: c */
    public final boolean f824c;
    private static final ObjectStreamField[] serialPersistentFields = {new ObjectStreamField("rnd", Long.TYPE), new ObjectStreamField("initialized", Boolean.TYPE)};

    /* renamed from: d */
    public static final ThreadLocal f818d = new ThreadLocal();

    /* renamed from: e */
    public static final AtomicInteger f819e = new AtomicInteger();

    /* renamed from: f */
    public static final C1812u f820f = new C1812u();

    /* renamed from: g */
    public static final AtomicLong f821g = new AtomicLong(m886f(System.currentTimeMillis()) ^ m886f(System.nanoTime()));

    public /* synthetic */ ThreadLocalRandom(int i) {
        this();
    }

    /* renamed from: e */
    public static int m885e(long j) {
        long j2 = (j ^ (j >>> 33)) * (-49064778989728563L);
        return (int) (((j2 ^ (j2 >>> 33)) * (-4265267296055464877L)) >>> 32);
    }

    /* renamed from: f */
    public static long m886f(long j) {
        long j2 = (j ^ (j >>> 33)) * (-49064778989728563L);
        long j3 = (j2 ^ (j2 >>> 33)) * (-4265267296055464877L);
        return j3 ^ (j3 >>> 33);
    }

    private ThreadLocalRandom() {
        this.f824c = true;
    }

    /* renamed from: d */
    public static final void m884d() {
        int iAddAndGet = f819e.addAndGet(-1640531527);
        if (iAddAndGet == 0) {
            iAddAndGet = 1;
        }
        long jM886f = m886f(f821g.getAndAdd(-4942790177534073029L));
        ThreadLocalRandom threadLocalRandom = (ThreadLocalRandom) f820f.get();
        threadLocalRandom.f822a = jM886f;
        threadLocalRandom.f823b = iAddAndGet;
    }

    public static ThreadLocalRandom current() {
        ThreadLocalRandom threadLocalRandom = (ThreadLocalRandom) f820f.get();
        if (threadLocalRandom.f823b == 0) {
            m884d();
        }
        return threadLocalRandom;
    }

    @Override // java.util.Random
    public final void setSeed(long j) {
        if (this.f824c) {
            throw new UnsupportedOperationException();
        }
    }

    /* renamed from: g */
    public final long m890g() {
        long j = this.f822a - 7046029254386353131L;
        this.f822a = j;
        return j;
    }

    @Override // java.util.Random
    public final int next(int i) {
        return nextInt() >>> (32 - i);
    }

    /* renamed from: c */
    public final long m889c(long j, long j2) {
        long jM886f = m886f(m890g());
        if (j >= j2) {
            return jM886f;
        }
        long j3 = j2 - j;
        long j4 = j3 - 1;
        if ((j3 & j4) == 0) {
            return (jM886f & j4) + j;
        }
        if (j3 > 0) {
            while (true) {
                long j5 = jM886f >>> 1;
                long j6 = j5 + j4;
                long j7 = j5 % j3;
                if (j6 - j7 >= 0) {
                    return j7 + j;
                }
                jM886f = m886f(m890g());
            }
        } else {
            while (true) {
                if (jM886f >= j && jM886f < j2) {
                    return jM886f;
                }
                jM886f = m886f(m890g());
            }
        }
    }

    /* renamed from: b */
    public final int m888b(int i, int i2) {
        int iM885e = m885e(m890g());
        if (i >= i2) {
            return iM885e;
        }
        int i3 = i2 - i;
        int i4 = i3 - 1;
        if ((i3 & i4) == 0) {
            return (iM885e & i4) + i;
        }
        if (i3 > 0) {
            int iM885e2 = iM885e >>> 1;
            while (true) {
                int i5 = iM885e2 + i4;
                int i6 = iM885e2 % i3;
                if (i5 - i6 >= 0) {
                    return i6 + i;
                }
                iM885e2 = m885e(m890g()) >>> 1;
            }
        } else {
            while (true) {
                if (iM885e >= i && iM885e < i2) {
                    return iM885e;
                }
                iM885e = m885e(m890g());
            }
        }
    }

    /* renamed from: a */
    public final double m887a(double d, double d2) {
        double dNextLong = (nextLong() >>> 11) * 1.1102230246251565E-16d;
        if (d >= d2) {
            return dNextLong;
        }
        double d3 = ((d2 - d) * dNextLong) + d;
        return d3 >= d2 ? Double.longBitsToDouble(Double.doubleToLongBits(d2) - 1) : d3;
    }

    @Override // java.util.Random
    public final int nextInt() {
        return m885e(m890g());
    }

    @Override // java.util.Random
    public final int nextInt(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("bound must be positive");
        }
        int iM885e = m885e(m890g());
        int i2 = i - 1;
        if ((i & i2) == 0) {
            return iM885e & i2;
        }
        while (true) {
            int i3 = iM885e >>> 1;
            int i4 = i3 + i2;
            int i5 = i3 % i;
            if (i4 - i5 >= 0) {
                return i5;
            }
            iM885e = m885e(m890g());
        }
    }

    public final int nextInt(int i, int i2) {
        if (i >= i2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return m888b(i, i2);
    }

    @Override // java.util.Random
    public final long nextLong() {
        return m886f(m890g());
    }

    public final long nextLong(long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("bound must be positive");
        }
        long jM886f = m886f(m890g());
        long j2 = j - 1;
        if ((j & j2) == 0) {
            return jM886f & j2;
        }
        while (true) {
            long j3 = jM886f >>> 1;
            long j4 = j3 + j2;
            long j5 = j3 % j;
            if (j4 - j5 >= 0) {
                return j5;
            }
            jM886f = m886f(m890g());
        }
    }

    public long nextLong(long j, long j2) {
        if (j >= j2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return m889c(j, j2);
    }

    @Override // java.util.Random
    public final double nextDouble() {
        return (m886f(m890g()) >>> 11) * 1.1102230246251565E-16d;
    }

    public final double nextDouble(double d) {
        if (d <= 0.0d) {
            throw new IllegalArgumentException("bound must be positive");
        }
        double dM886f = (m886f(m890g()) >>> 11) * 1.1102230246251565E-16d * d;
        return dM886f < d ? dM886f : Double.longBitsToDouble(Double.doubleToLongBits(d) - 1);
    }

    public final double nextDouble(double d, double d2) {
        if (d >= d2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        return m887a(d, d2);
    }

    @Override // java.util.Random
    public final boolean nextBoolean() {
        return m885e(m890g()) < 0;
    }

    @Override // java.util.Random
    public final float nextFloat() {
        return (m885e(m890g()) >>> 8) * 5.9604645E-8f;
    }

    @Override // java.util.Random
    public final double nextGaussian() {
        ThreadLocal threadLocal = f818d;
        Double d = (Double) threadLocal.get();
        if (d != null) {
            threadLocal.set(null);
            return d.doubleValue();
        }
        while (true) {
            double dNextDouble = (nextDouble() * 2.0d) - 1.0d;
            double dNextDouble2 = (nextDouble() * 2.0d) - 1.0d;
            double d2 = (dNextDouble2 * dNextDouble2) + (dNextDouble * dNextDouble);
            if (d2 < 1.0d && d2 != 0.0d) {
                double dSqrt = StrictMath.sqrt((StrictMath.log(d2) * (-2.0d)) / d2);
                f818d.set(Double.valueOf(dNextDouble2 * dSqrt));
                return dNextDouble * dSqrt;
            }
        }
    }

    @Override // java.util.Random
    public final IntStream ints(long j) {
        if (j >= 0) {
            return IntStream.Wrapper.convert(AbstractC1890G3.m960a(new C1814w(0L, j, ConnectionsManager.DEFAULT_DATACENTER_ID, 0)));
        }
        throw new IllegalArgumentException("size must be non-negative");
    }

    @Override // java.util.Random
    public final java.util.stream.IntStream ints() {
        return IntStream.Wrapper.convert(AbstractC1890G3.m960a(new C1814w(0L, Long.MAX_VALUE, ConnectionsManager.DEFAULT_DATACENTER_ID, 0)));
    }

    @Override // java.util.Random
    public final java.util.stream.IntStream ints(long j, int i, int i2) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        if (i < i2) {
            return IntStream.Wrapper.convert(AbstractC1890G3.m960a(new C1814w(0L, j, i, i2)));
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public final java.util.stream.IntStream ints(int i, int i2) {
        if (i < i2) {
            return IntStream.Wrapper.convert(AbstractC1890G3.m960a(new C1814w(0L, Long.MAX_VALUE, i, i2)));
        }
        throw new IllegalArgumentException("bound must be greater than origin");
    }

    @Override // java.util.Random
    public final LongStream longs(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        C1815x c1815x = new C1815x(0L, j, Long.MAX_VALUE, 0L);
        return C2055m0.m1052f(new C2028h0(c1815x, EnumC1995b3.m1028k(c1815x), false));
    }

    @Override // java.util.Random
    public final LongStream longs() {
        C1815x c1815x = new C1815x(0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L);
        return C2055m0.m1052f(new C2028h0(c1815x, EnumC1995b3.m1028k(c1815x), false));
    }

    @Override // java.util.Random
    public final LongStream longs(long j, long j2, long j3) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        if (j2 >= j3) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        C1815x c1815x = new C1815x(0L, j, j2, j3);
        return C2055m0.m1052f(new C2028h0(c1815x, EnumC1995b3.m1028k(c1815x), false));
    }

    @Override // java.util.Random
    public final LongStream longs(long j, long j2) {
        if (j >= j2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        C1815x c1815x = new C1815x(0L, Long.MAX_VALUE, j, j2);
        return C2055m0.m1052f(new C2028h0(c1815x, EnumC1995b3.m1028k(c1815x), false));
    }

    @Override // java.util.Random
    public final DoubleStream doubles(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        C1813v c1813v = new C1813v(0L, j, Double.MAX_VALUE, 0.0d);
        return C1866C.m941f(new C2109x(c1813v, EnumC1995b3.m1028k(c1813v), false));
    }

    @Override // java.util.Random
    public final DoubleStream doubles() {
        C1813v c1813v = new C1813v(0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0d);
        return C1866C.m941f(new C2109x(c1813v, EnumC1995b3.m1028k(c1813v), false));
    }

    @Override // java.util.Random
    public final DoubleStream doubles(long j, double d, double d2) {
        if (j < 0) {
            throw new IllegalArgumentException("size must be non-negative");
        }
        if (d >= d2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        C1813v c1813v = new C1813v(0L, j, d, d2);
        return C1866C.m941f(new C2109x(c1813v, EnumC1995b3.m1028k(c1813v), false));
    }

    @Override // java.util.Random
    public final DoubleStream doubles(double d, double d2) {
        if (d >= d2) {
            throw new IllegalArgumentException("bound must be greater than origin");
        }
        C1813v c1813v = new C1813v(0L, Long.MAX_VALUE, d, d2);
        return C1866C.m941f(new C2109x(c1813v, EnumC1995b3.m1028k(c1813v), false));
    }

    static {
        if (((Boolean) AccessController.doPrivileged(new C1853r0(1))).booleanValue()) {
            byte[] seed = SecureRandom.getSeed(8);
            long j = seed[0] & 255;
            for (int i = 1; i < 8; i++) {
                j = (j << 8) | (seed[i] & 255);
            }
            f821g.set(j);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ObjectOutputStream.PutField putFieldPutFields = objectOutputStream.putFields();
        putFieldPutFields.put("rnd", this.f822a);
        putFieldPutFields.put("initialized", true);
        objectOutputStream.writeFields();
    }

    private Object readResolve() {
        return current();
    }
}
