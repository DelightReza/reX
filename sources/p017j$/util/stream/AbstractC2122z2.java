package p017j$.util.stream;

import p017j$.util.InterfaceC1779T;
import p017j$.util.InterfaceC1784Y;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.z2 */
/* loaded from: classes2.dex */
public abstract class AbstractC2122z2 {
    /* renamed from: c */
    public static long m1112c(long j, long j2) {
        long j3 = j2 >= 0 ? j + j2 : Long.MAX_VALUE;
        if (j3 >= 0) {
            return j3;
        }
        return Long.MAX_VALUE;
    }

    /* renamed from: a */
    public static long m1110a(long j, long j2, long j3) {
        if (j >= 0) {
            return Math.max(-1L, Math.min(j - j2, j3));
        }
        return -1L;
    }

    /* renamed from: b */
    public static Spliterator m1111b(EnumC2001c3 enumC2001c3, Spliterator spliterator, long j, long j2) {
        long jM1112c = m1112c(j, j2);
        int i = AbstractC2112x2.f1377a[enumC2001c3.ordinal()];
        if (i == 1) {
            return new C2103v3(spliterator, j, jM1112c);
        }
        if (i == 2) {
            return new C2088s3((Spliterator.OfInt) spliterator, j, jM1112c);
        }
        if (i == 3) {
            return new C2093t3((InterfaceC1784Y) spliterator, j, jM1112c);
        }
        if (i != 4) {
            throw new IllegalStateException("Unknown shape " + enumC2001c3);
        }
        return new C2083r3((InterfaceC1779T) spliterator, j, jM1112c);
    }

    /* renamed from: h */
    public static C2072p2 m1117h(AbstractC2018f2 abstractC2018f2, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new C2072p2(abstractC2018f2, m1113d(j2), j, j2);
    }

    /* renamed from: f */
    public static C2082r2 m1115f(AbstractC1998c0 abstractC1998c0, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new C2082r2(abstractC1998c0, m1113d(j2), j, j2);
    }

    /* renamed from: g */
    public static C2092t2 m1116g(AbstractC2045k0 abstractC2045k0, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new C2092t2(abstractC2045k0, m1113d(j2), j, j2);
    }

    /* renamed from: e */
    public static C2107w2 m1114e(AbstractC1856A abstractC1856A, long j, long j2) {
        if (j < 0) {
            throw new IllegalArgumentException("Skip must be non-negative: " + j);
        }
        return new C2107w2(abstractC1856A, m1113d(j2), j, j2);
    }

    /* renamed from: d */
    public static int m1113d(long j) {
        return (j != -1 ? EnumC1995b3.f1203u : 0) | EnumC1995b3.f1202t;
    }
}
