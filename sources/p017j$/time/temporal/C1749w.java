package p017j$.time.temporal;

import java.util.Map;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1640b;
import p017j$.time.chrono.InterfaceC1652b;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.format.C1683D;
import p017j$.time.format.EnumC1684E;

/* renamed from: j$.time.temporal.w */
/* loaded from: classes2.dex */
public final class C1749w implements InterfaceC1744r {

    /* renamed from: f */
    public static final C1748v f701f = C1748v.m818f(1, 7);

    /* renamed from: g */
    public static final C1748v f702g = C1748v.m819g(0, 4, 6);

    /* renamed from: h */
    public static final C1748v f703h = C1748v.m819g(0, 52, 54);

    /* renamed from: i */
    public static final C1748v f704i = C1748v.m819g(1, 52, 53);

    /* renamed from: a */
    public final String f705a;

    /* renamed from: b */
    public final C1750x f706b;

    /* renamed from: c */
    public final InterfaceC1746t f707c;

    /* renamed from: d */
    public final InterfaceC1746t f708d;

    /* renamed from: e */
    public final C1748v f709e;

    @Override // p017j$.time.temporal.InterfaceC1744r
    public final boolean isDateBased() {
        return true;
    }

    /* renamed from: e */
    public final InterfaceC1652b m829e(InterfaceC1661k interfaceC1661k, int i, int i2, int i3) {
        InterfaceC1652b interfaceC1652bMo652I = interfaceC1661k.mo652I(i, 1, 1);
        int iM832h = m832h(1, m826b(interfaceC1652bMo652I));
        int i4 = i3 - 1;
        return interfaceC1652bMo652I.mo557d(((Math.min(i2, m825a(iM832h, interfaceC1652bMo652I.mo571M() + this.f706b.f713b) - 1) - 1) * 7) + i4 + (-iM832h), (InterfaceC1746t) EnumC1728b.DAYS);
    }

    public C1749w(String str, C1750x c1750x, InterfaceC1746t interfaceC1746t, InterfaceC1746t interfaceC1746t2, C1748v c1748v) {
        this.f705a = str;
        this.f706b = c1750x;
        this.f707c = interfaceC1746t;
        this.f708d = interfaceC1746t2;
        this.f709e = c1748v;
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: t */
    public final long mo806t(InterfaceC1740n interfaceC1740n) {
        int iM827c;
        EnumC1728b enumC1728b = EnumC1728b.WEEKS;
        InterfaceC1746t interfaceC1746t = this.f708d;
        if (interfaceC1746t == enumC1728b) {
            iM827c = m826b(interfaceC1740n);
        } else if (interfaceC1746t != EnumC1728b.MONTHS) {
            if (interfaceC1746t != EnumC1728b.YEARS) {
                if (interfaceC1746t == C1750x.f711h) {
                    iM827c = m828d(interfaceC1740n);
                } else if (interfaceC1746t == EnumC1728b.FOREVER) {
                    iM827c = m827c(interfaceC1740n);
                } else {
                    throw new IllegalStateException("unreachable, rangeUnit: " + interfaceC1746t + ", this: " + this);
                }
            } else {
                int iM826b = m826b(interfaceC1740n);
                int iMo544i = interfaceC1740n.mo544i(EnumC1727a.DAY_OF_YEAR);
                iM827c = m825a(m832h(iMo544i, iM826b), iMo544i);
            }
        } else {
            int iM826b2 = m826b(interfaceC1740n);
            int iMo544i2 = interfaceC1740n.mo544i(EnumC1727a.DAY_OF_MONTH);
            iM827c = m825a(m832h(iMo544i2, iM826b2), iMo544i2);
        }
        return iM827c;
    }

    /* renamed from: b */
    public final int m826b(InterfaceC1740n interfaceC1740n) {
        return AbstractC1745s.m817e(interfaceC1740n.mo544i(EnumC1727a.DAY_OF_WEEK) - this.f706b.f712a.getValue()) + 1;
    }

    /* renamed from: c */
    public final int m827c(InterfaceC1740n interfaceC1740n) {
        int iM826b = m826b(interfaceC1740n);
        int iMo544i = interfaceC1740n.mo544i(EnumC1727a.YEAR);
        EnumC1727a enumC1727a = EnumC1727a.DAY_OF_YEAR;
        int iMo544i2 = interfaceC1740n.mo544i(enumC1727a);
        int iM832h = m832h(iMo544i2, iM826b);
        int iM825a = m825a(iM832h, iMo544i2);
        return iM825a == 0 ? iMo544i - 1 : iM825a >= m825a(iM832h, ((int) interfaceC1740n.mo545k(enumC1727a).f700d) + this.f706b.f713b) ? iMo544i + 1 : iMo544i;
    }

    /* renamed from: d */
    public final int m828d(InterfaceC1740n interfaceC1740n) {
        int iM825a;
        int iM826b = m826b(interfaceC1740n);
        EnumC1727a enumC1727a = EnumC1727a.DAY_OF_YEAR;
        int iMo544i = interfaceC1740n.mo544i(enumC1727a);
        int iM832h = m832h(iMo544i, iM826b);
        int iM825a2 = m825a(iM832h, iMo544i);
        if (iM825a2 == 0) {
            return m828d(AbstractC1636a.m492K(interfaceC1740n).mo651A(interfaceC1740n).mo559y(iMo544i, EnumC1728b.DAYS));
        }
        return (iM825a2 <= 50 || iM825a2 < (iM825a = m825a(iM832h, ((int) interfaceC1740n.mo545k(enumC1727a).f700d) + this.f706b.f713b))) ? iM825a2 : (iM825a2 - iM825a) + 1;
    }

    /* renamed from: h */
    public final int m832h(int i, int i2) {
        int iM817e = AbstractC1745s.m817e(i - i2);
        return iM817e + 1 > this.f706b.f713b ? 7 - iM817e : -iM817e;
    }

    /* renamed from: a */
    public static int m825a(int i, int i2) {
        return ((i2 - 1) + (i + 7)) / 7;
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: y */
    public final InterfaceC1739m mo807y(InterfaceC1739m interfaceC1739m, long j) {
        if (this.f709e.m820a(j, this) == interfaceC1739m.mo544i(this)) {
            return interfaceC1739m;
        }
        if (this.f708d != EnumC1728b.FOREVER) {
            return interfaceC1739m.mo557d(r0 - r1, this.f707c);
        }
        C1750x c1750x = this.f706b;
        return m829e(AbstractC1636a.m492K(interfaceC1739m), (int) j, interfaceC1739m.mo544i(c1750x.f716e), interfaceC1739m.mo544i(c1750x.f714c));
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: k */
    public final InterfaceC1740n mo804k(Map map, C1683D c1683d, EnumC1684E enumC1684E) {
        InterfaceC1652b interfaceC1652bMo557d;
        InterfaceC1652b interfaceC1652bMo557d2;
        EnumC1727a enumC1727a;
        InterfaceC1652b interfaceC1652bMo557d3;
        long jLongValue = ((Long) map.get(this)).longValue();
        int iM493L = AbstractC1636a.m493L(jLongValue);
        EnumC1728b enumC1728b = EnumC1728b.WEEKS;
        C1748v c1748v = this.f709e;
        C1750x c1750x = this.f706b;
        InterfaceC1746t interfaceC1746t = this.f708d;
        if (interfaceC1746t == enumC1728b) {
            long jM817e = AbstractC1745s.m817e((c1748v.m820a(jLongValue, this) - 1) + (c1750x.f712a.getValue() - 1)) + 1;
            map.remove(this);
            map.put(EnumC1727a.DAY_OF_WEEK, Long.valueOf(jM817e));
            return null;
        }
        EnumC1727a enumC1727a2 = EnumC1727a.DAY_OF_WEEK;
        if (!map.containsKey(enumC1727a2)) {
            return null;
        }
        int iM817e = AbstractC1745s.m817e(enumC1727a2.f671b.m820a(((Long) map.get(enumC1727a2)).longValue(), enumC1727a2) - c1750x.f712a.getValue()) + 1;
        InterfaceC1661k interfaceC1661kM492K = AbstractC1636a.m492K(c1683d);
        EnumC1727a enumC1727a3 = EnumC1727a.YEAR;
        if (!map.containsKey(enumC1727a3)) {
            if ((interfaceC1746t != C1750x.f711h && interfaceC1746t != EnumC1728b.FOREVER) || !map.containsKey(c1750x.f717f) || !map.containsKey(c1750x.f716e)) {
                return null;
            }
            C1749w c1749w = c1750x.f717f;
            int iM820a = c1749w.f709e.m820a(((Long) map.get(c1749w)).longValue(), c1750x.f717f);
            if (enumC1684E == EnumC1684E.LENIENT) {
                interfaceC1652bMo557d = m829e(interfaceC1661kM492K, iM820a, 1, iM817e).mo557d(AbstractC1636a.m501T(((Long) map.get(c1750x.f716e)).longValue(), 1L), (InterfaceC1746t) enumC1728b);
            } else {
                C1749w c1749w2 = c1750x.f716e;
                InterfaceC1652b interfaceC1652bM829e = m829e(interfaceC1661kM492K, iM820a, c1749w2.f709e.m820a(((Long) map.get(c1749w2)).longValue(), c1750x.f716e), iM817e);
                if (enumC1684E == EnumC1684E.STRICT && m827c(interfaceC1652bM829e) != iM820a) {
                    throw new C1640b("Strict mode rejected resolved date as it is in a different week-based-year");
                }
                interfaceC1652bMo557d = interfaceC1652bM829e;
            }
            map.remove(this);
            map.remove(c1750x.f717f);
            map.remove(c1750x.f716e);
            map.remove(enumC1727a2);
            return interfaceC1652bMo557d;
        }
        int iM820a2 = enumC1727a3.f671b.m820a(((Long) map.get(enumC1727a3)).longValue(), enumC1727a3);
        EnumC1728b enumC1728b2 = EnumC1728b.MONTHS;
        if (interfaceC1746t == enumC1728b2) {
            EnumC1727a enumC1727a4 = EnumC1727a.MONTH_OF_YEAR;
            if (map.containsKey(enumC1727a4)) {
                long jLongValue2 = ((Long) map.get(enumC1727a4)).longValue();
                long j = iM493L;
                if (enumC1684E == EnumC1684E.LENIENT) {
                    InterfaceC1652b interfaceC1652bMo557d4 = interfaceC1661kM492K.mo652I(iM820a2, 1, 1).mo557d(AbstractC1636a.m501T(jLongValue2, 1L), (InterfaceC1746t) enumC1728b2);
                    int iM826b = m826b(interfaceC1652bMo557d4);
                    int iMo544i = interfaceC1652bMo557d4.mo544i(EnumC1727a.DAY_OF_MONTH);
                    interfaceC1652bMo557d3 = interfaceC1652bMo557d4.mo557d(AbstractC1636a.m494M(AbstractC1636a.m500S(AbstractC1636a.m501T(j, m825a(m832h(iMo544i, iM826b), iMo544i)), 7), iM817e - m826b(interfaceC1652bMo557d4)), (InterfaceC1746t) EnumC1728b.DAYS);
                    enumC1727a = enumC1727a4;
                } else {
                    enumC1727a = enumC1727a4;
                    InterfaceC1652b interfaceC1652bMo652I = interfaceC1661kM492K.mo652I(iM820a2, enumC1727a.f671b.m820a(jLongValue2, enumC1727a), 1);
                    long jM820a = c1748v.m820a(j, this);
                    int iM826b2 = m826b(interfaceC1652bMo652I);
                    int iMo544i2 = interfaceC1652bMo652I.mo544i(EnumC1727a.DAY_OF_MONTH);
                    InterfaceC1652b interfaceC1652bMo557d5 = interfaceC1652bMo652I.mo557d((((int) (jM820a - m825a(m832h(iMo544i2, iM826b2), iMo544i2))) * 7) + (iM817e - m826b(interfaceC1652bMo652I)), (InterfaceC1746t) EnumC1728b.DAYS);
                    if (enumC1684E == EnumC1684E.STRICT && interfaceC1652bMo557d5.mo542D(enumC1727a) != jLongValue2) {
                        throw new C1640b("Strict mode rejected resolved date as it is in a different month");
                    }
                    interfaceC1652bMo557d3 = interfaceC1652bMo557d5;
                }
                map.remove(this);
                map.remove(enumC1727a3);
                map.remove(enumC1727a);
                map.remove(enumC1727a2);
                return interfaceC1652bMo557d3;
            }
        }
        if (interfaceC1746t != EnumC1728b.YEARS) {
            return null;
        }
        long j2 = iM493L;
        InterfaceC1652b interfaceC1652bMo652I2 = interfaceC1661kM492K.mo652I(iM820a2, 1, 1);
        if (enumC1684E == EnumC1684E.LENIENT) {
            int iM826b3 = m826b(interfaceC1652bMo652I2);
            int iMo544i3 = interfaceC1652bMo652I2.mo544i(EnumC1727a.DAY_OF_YEAR);
            interfaceC1652bMo557d2 = interfaceC1652bMo652I2.mo557d(AbstractC1636a.m494M(AbstractC1636a.m500S(AbstractC1636a.m501T(j2, m825a(m832h(iMo544i3, iM826b3), iMo544i3)), 7), iM817e - m826b(interfaceC1652bMo652I2)), (InterfaceC1746t) EnumC1728b.DAYS);
        } else {
            long jM820a2 = c1748v.m820a(j2, this);
            int iM826b4 = m826b(interfaceC1652bMo652I2);
            int iMo544i4 = interfaceC1652bMo652I2.mo544i(EnumC1727a.DAY_OF_YEAR);
            InterfaceC1652b interfaceC1652bMo557d6 = interfaceC1652bMo652I2.mo557d((((int) (jM820a2 - m825a(m832h(iMo544i4, iM826b4), iMo544i4))) * 7) + (iM817e - m826b(interfaceC1652bMo652I2)), (InterfaceC1746t) EnumC1728b.DAYS);
            if (enumC1684E == EnumC1684E.STRICT && interfaceC1652bMo557d6.mo542D(enumC1727a3) != iM820a2) {
                throw new C1640b("Strict mode rejected resolved date as it is in a different year");
            }
            interfaceC1652bMo557d2 = interfaceC1652bMo557d6;
        }
        map.remove(this);
        map.remove(enumC1727a3);
        map.remove(enumC1727a2);
        return interfaceC1652bMo557d2;
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: n */
    public final C1748v mo805n() {
        return this.f709e;
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: i */
    public final boolean mo802i(InterfaceC1740n interfaceC1740n) {
        if (!interfaceC1740n.mo543e(EnumC1727a.DAY_OF_WEEK)) {
            return false;
        }
        EnumC1728b enumC1728b = EnumC1728b.WEEKS;
        InterfaceC1746t interfaceC1746t = this.f708d;
        if (interfaceC1746t == enumC1728b) {
            return true;
        }
        if (interfaceC1746t == EnumC1728b.MONTHS) {
            return interfaceC1740n.mo543e(EnumC1727a.DAY_OF_MONTH);
        }
        if (interfaceC1746t == EnumC1728b.YEARS) {
            return interfaceC1740n.mo543e(EnumC1727a.DAY_OF_YEAR);
        }
        if (interfaceC1746t == C1750x.f711h) {
            return interfaceC1740n.mo543e(EnumC1727a.DAY_OF_YEAR);
        }
        if (interfaceC1746t == EnumC1728b.FOREVER) {
            return interfaceC1740n.mo543e(EnumC1727a.YEAR);
        }
        return false;
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: j */
    public final C1748v mo803j(InterfaceC1740n interfaceC1740n) {
        EnumC1728b enumC1728b = EnumC1728b.WEEKS;
        InterfaceC1746t interfaceC1746t = this.f708d;
        if (interfaceC1746t == enumC1728b) {
            return this.f709e;
        }
        if (interfaceC1746t == EnumC1728b.MONTHS) {
            return m830f(interfaceC1740n, EnumC1727a.DAY_OF_MONTH);
        }
        if (interfaceC1746t == EnumC1728b.YEARS) {
            return m830f(interfaceC1740n, EnumC1727a.DAY_OF_YEAR);
        }
        if (interfaceC1746t == C1750x.f711h) {
            return m831g(interfaceC1740n);
        }
        if (interfaceC1746t == EnumC1728b.FOREVER) {
            return EnumC1727a.YEAR.f671b;
        }
        throw new IllegalStateException("unreachable, rangeUnit: " + interfaceC1746t + ", this: " + this);
    }

    /* renamed from: f */
    public final C1748v m830f(InterfaceC1740n interfaceC1740n, EnumC1727a enumC1727a) {
        int iM832h = m832h(interfaceC1740n.mo544i(enumC1727a), m826b(interfaceC1740n));
        C1748v c1748vMo545k = interfaceC1740n.mo545k(enumC1727a);
        return C1748v.m818f(m825a(iM832h, (int) c1748vMo545k.f697a), m825a(iM832h, (int) c1748vMo545k.f700d));
    }

    /* renamed from: g */
    public final C1748v m831g(InterfaceC1740n interfaceC1740n) {
        EnumC1727a enumC1727a = EnumC1727a.DAY_OF_YEAR;
        if (!interfaceC1740n.mo543e(enumC1727a)) {
            return f703h;
        }
        int iM826b = m826b(interfaceC1740n);
        int iMo544i = interfaceC1740n.mo544i(enumC1727a);
        int iM832h = m832h(iMo544i, iM826b);
        int iM825a = m825a(iM832h, iMo544i);
        if (iM825a != 0) {
            if (iM825a >= m825a(iM832h, this.f706b.f713b + ((int) interfaceC1740n.mo545k(enumC1727a).f700d))) {
                return m831g(AbstractC1636a.m492K(interfaceC1740n).mo651A(interfaceC1740n).mo557d((r0 - iMo544i) + 8, (InterfaceC1746t) EnumC1728b.DAYS));
            }
            return C1748v.m818f(1L, r1 - 1);
        }
        return m831g(AbstractC1636a.m492K(interfaceC1740n).mo651A(interfaceC1740n).mo559y(iMo544i + 7, EnumC1728b.DAYS));
    }

    public final String toString() {
        return this.f705a + "[" + this.f706b.toString() + "]";
    }
}
