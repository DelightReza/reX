package p017j$.time.format;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.AbstractC1641c;
import p017j$.time.C1640b;
import p017j$.time.C1678e;
import p017j$.time.C1715i;
import p017j$.time.Instant;
import p017j$.time.LocalDate;
import p017j$.time.Period;
import p017j$.time.ZoneId;
import p017j$.time.ZoneOffset;
import p017j$.time.chrono.InterfaceC1652b;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.util.Objects;

/* renamed from: j$.time.format.D */
/* loaded from: classes2.dex */
public final class C1683D implements InterfaceC1740n {

    /* renamed from: b */
    public ZoneId f541b;

    /* renamed from: c */
    public InterfaceC1661k f542c;

    /* renamed from: d */
    public boolean f543d;

    /* renamed from: e */
    public EnumC1684E f544e;

    /* renamed from: f */
    public InterfaceC1652b f545f;

    /* renamed from: g */
    public C1715i f546g;

    /* renamed from: a */
    public final Map f540a = new HashMap();

    /* renamed from: h */
    public Period f547h = Period.f448d;

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final /* synthetic */ int mo544i(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m813a(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final /* synthetic */ C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m816d(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        if (((HashMap) this.f540a).containsKey(interfaceC1744r)) {
            return true;
        }
        InterfaceC1652b interfaceC1652b = this.f545f;
        if (interfaceC1652b != null && interfaceC1652b.mo543e(interfaceC1744r)) {
            return true;
        }
        C1715i c1715i = this.f546g;
        if (c1715i == null || !c1715i.mo543e(interfaceC1744r)) {
            return (interfaceC1744r == null || (interfaceC1744r instanceof EnumC1727a) || !interfaceC1744r.mo802i(this)) ? false : true;
        }
        return true;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        Objects.requireNonNull(interfaceC1744r, "field");
        Long l = (Long) ((HashMap) this.f540a).get(interfaceC1744r);
        if (l != null) {
            return l.longValue();
        }
        InterfaceC1652b interfaceC1652b = this.f545f;
        if (interfaceC1652b != null && interfaceC1652b.mo543e(interfaceC1744r)) {
            return this.f545f.mo542D(interfaceC1744r);
        }
        C1715i c1715i = this.f546g;
        if (c1715i != null && c1715i.mo543e(interfaceC1744r)) {
            return this.f546g.mo542D(interfaceC1744r);
        }
        if (interfaceC1744r instanceof EnumC1727a) {
            throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
        return interfaceC1744r.mo806t(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f690a) {
            return this.f541b;
        }
        if (c1678e == AbstractC1745s.f691b) {
            return this.f542c;
        }
        if (c1678e == AbstractC1745s.f695f) {
            InterfaceC1652b interfaceC1652b = this.f545f;
            if (interfaceC1652b != null) {
                return LocalDate.m561S(interfaceC1652b);
            }
            return null;
        }
        if (c1678e == AbstractC1745s.f696g) {
            return this.f546g;
        }
        if (c1678e == AbstractC1745s.f693d) {
            Long l = (Long) ((HashMap) this.f540a).get(EnumC1727a.OFFSET_SECONDS);
            if (l != null) {
                return ZoneOffset.m626W(l.intValue());
            }
            ZoneId zoneId = this.f541b;
            return zoneId instanceof ZoneOffset ? zoneId : c1678e.m704g(this);
        }
        if (c1678e == AbstractC1745s.f694e) {
            return c1678e.m704g(this);
        }
        if (c1678e == AbstractC1745s.f692c) {
            return null;
        }
        return c1678e.m704g(this);
    }

    /* renamed from: v */
    public final void m718v(InterfaceC1744r interfaceC1744r, EnumC1727a enumC1727a, Long l) {
        Long l2 = (Long) ((HashMap) this.f540a).put(enumC1727a, l);
        if (l2 == null || l2.longValue() == l.longValue()) {
            return;
        }
        throw new C1640b("Conflict found: " + enumC1727a + " " + l2 + " differs from " + enumC1727a + " " + l + " while resolving  " + interfaceC1744r);
    }

    /* renamed from: l */
    public final void m712l() {
        if (((HashMap) this.f540a).containsKey(EnumC1727a.INSTANT_SECONDS)) {
            ZoneId zoneId = this.f541b;
            if (zoneId != null) {
                m713m(zoneId);
                return;
            }
            Long l = (Long) ((HashMap) this.f540a).get(EnumC1727a.OFFSET_SECONDS);
            if (l != null) {
                m713m(ZoneOffset.m626W(l.intValue()));
            }
        }
    }

    /* renamed from: m */
    public final void m713m(ZoneId zoneId) {
        Map map = this.f540a;
        EnumC1727a enumC1727a = EnumC1727a.INSTANT_SECONDS;
        m717u(this.f542c.mo654L(Instant.m551Q(((Long) ((HashMap) map).remove(enumC1727a)).longValue(), 0), zoneId).mo639f());
        m718v(enumC1727a, EnumC1727a.SECOND_OF_DAY, Long.valueOf(r5.mo638b().m782d0()));
    }

    /* renamed from: u */
    public final void m717u(InterfaceC1652b interfaceC1652b) {
        InterfaceC1652b interfaceC1652b2 = this.f545f;
        if (interfaceC1652b2 != null) {
            if (interfaceC1652b == null || interfaceC1652b2.equals(interfaceC1652b)) {
                return;
            }
            throw new C1640b("Conflict found: Fields resolved to two different dates: " + this.f545f + " " + interfaceC1652b);
        }
        if (interfaceC1652b != null) {
            if (!this.f542c.equals(interfaceC1652b.mo581a())) {
                throw new C1640b("ChronoLocalDate must use the effective parsed chronology: " + this.f542c);
            }
            this.f545f = interfaceC1652b;
        }
    }

    /* renamed from: q */
    public final void m715q() {
        Map map = this.f540a;
        EnumC1727a enumC1727a = EnumC1727a.CLOCK_HOUR_OF_DAY;
        if (((HashMap) map).containsKey(enumC1727a)) {
            long jLongValue = ((Long) ((HashMap) this.f540a).remove(enumC1727a)).longValue();
            EnumC1684E enumC1684E = this.f544e;
            if (enumC1684E == EnumC1684E.STRICT || (enumC1684E == EnumC1684E.SMART && jLongValue != 0)) {
                enumC1727a.m800D(jLongValue);
            }
            EnumC1727a enumC1727a2 = EnumC1727a.HOUR_OF_DAY;
            if (jLongValue == 24) {
                jLongValue = 0;
            }
            m718v(enumC1727a, enumC1727a2, Long.valueOf(jLongValue));
        }
        Map map2 = this.f540a;
        EnumC1727a enumC1727a3 = EnumC1727a.CLOCK_HOUR_OF_AMPM;
        if (((HashMap) map2).containsKey(enumC1727a3)) {
            long jLongValue2 = ((Long) ((HashMap) this.f540a).remove(enumC1727a3)).longValue();
            EnumC1684E enumC1684E2 = this.f544e;
            if (enumC1684E2 == EnumC1684E.STRICT || (enumC1684E2 == EnumC1684E.SMART && jLongValue2 != 0)) {
                enumC1727a3.m800D(jLongValue2);
            }
            m718v(enumC1727a3, EnumC1727a.HOUR_OF_AMPM, Long.valueOf(jLongValue2 != 12 ? jLongValue2 : 0L));
        }
        Map map3 = this.f540a;
        EnumC1727a enumC1727a4 = EnumC1727a.AMPM_OF_DAY;
        if (((HashMap) map3).containsKey(enumC1727a4)) {
            Map map4 = this.f540a;
            EnumC1727a enumC1727a5 = EnumC1727a.HOUR_OF_AMPM;
            if (((HashMap) map4).containsKey(enumC1727a5)) {
                long jLongValue3 = ((Long) ((HashMap) this.f540a).remove(enumC1727a4)).longValue();
                long jLongValue4 = ((Long) ((HashMap) this.f540a).remove(enumC1727a5)).longValue();
                if (this.f544e == EnumC1684E.LENIENT) {
                    m718v(enumC1727a4, EnumC1727a.HOUR_OF_DAY, Long.valueOf(AbstractC1636a.m494M(AbstractC1636a.m500S(jLongValue3, 12), jLongValue4)));
                } else {
                    enumC1727a4.m800D(jLongValue3);
                    enumC1727a5.m800D(jLongValue3);
                    m718v(enumC1727a4, EnumC1727a.HOUR_OF_DAY, Long.valueOf((jLongValue3 * 12) + jLongValue4));
                }
            }
        }
        Map map5 = this.f540a;
        EnumC1727a enumC1727a6 = EnumC1727a.NANO_OF_DAY;
        if (((HashMap) map5).containsKey(enumC1727a6)) {
            long jLongValue5 = ((Long) ((HashMap) this.f540a).remove(enumC1727a6)).longValue();
            if (this.f544e != EnumC1684E.LENIENT) {
                enumC1727a6.m800D(jLongValue5);
            }
            m718v(enumC1727a6, EnumC1727a.HOUR_OF_DAY, Long.valueOf(jLongValue5 / 3600000000000L));
            m718v(enumC1727a6, EnumC1727a.MINUTE_OF_HOUR, Long.valueOf((jLongValue5 / 60000000000L) % 60));
            m718v(enumC1727a6, EnumC1727a.SECOND_OF_MINUTE, Long.valueOf((jLongValue5 / 1000000000) % 60));
            m718v(enumC1727a6, EnumC1727a.NANO_OF_SECOND, Long.valueOf(jLongValue5 % 1000000000));
        }
        Map map6 = this.f540a;
        EnumC1727a enumC1727a7 = EnumC1727a.MICRO_OF_DAY;
        if (((HashMap) map6).containsKey(enumC1727a7)) {
            long jLongValue6 = ((Long) ((HashMap) this.f540a).remove(enumC1727a7)).longValue();
            if (this.f544e != EnumC1684E.LENIENT) {
                enumC1727a7.m800D(jLongValue6);
            }
            m718v(enumC1727a7, EnumC1727a.SECOND_OF_DAY, Long.valueOf(jLongValue6 / 1000000));
            m718v(enumC1727a7, EnumC1727a.MICRO_OF_SECOND, Long.valueOf(jLongValue6 % 1000000));
        }
        Map map7 = this.f540a;
        EnumC1727a enumC1727a8 = EnumC1727a.MILLI_OF_DAY;
        if (((HashMap) map7).containsKey(enumC1727a8)) {
            long jLongValue7 = ((Long) ((HashMap) this.f540a).remove(enumC1727a8)).longValue();
            if (this.f544e != EnumC1684E.LENIENT) {
                enumC1727a8.m800D(jLongValue7);
            }
            m718v(enumC1727a8, EnumC1727a.SECOND_OF_DAY, Long.valueOf(jLongValue7 / 1000));
            m718v(enumC1727a8, EnumC1727a.MILLI_OF_SECOND, Long.valueOf(jLongValue7 % 1000));
        }
        Map map8 = this.f540a;
        EnumC1727a enumC1727a9 = EnumC1727a.SECOND_OF_DAY;
        if (((HashMap) map8).containsKey(enumC1727a9)) {
            long jLongValue8 = ((Long) ((HashMap) this.f540a).remove(enumC1727a9)).longValue();
            if (this.f544e != EnumC1684E.LENIENT) {
                enumC1727a9.m800D(jLongValue8);
            }
            m718v(enumC1727a9, EnumC1727a.HOUR_OF_DAY, Long.valueOf(jLongValue8 / 3600));
            m718v(enumC1727a9, EnumC1727a.MINUTE_OF_HOUR, Long.valueOf((jLongValue8 / 60) % 60));
            m718v(enumC1727a9, EnumC1727a.SECOND_OF_MINUTE, Long.valueOf(jLongValue8 % 60));
        }
        Map map9 = this.f540a;
        EnumC1727a enumC1727a10 = EnumC1727a.MINUTE_OF_DAY;
        if (((HashMap) map9).containsKey(enumC1727a10)) {
            long jLongValue9 = ((Long) ((HashMap) this.f540a).remove(enumC1727a10)).longValue();
            if (this.f544e != EnumC1684E.LENIENT) {
                enumC1727a10.m800D(jLongValue9);
            }
            m718v(enumC1727a10, EnumC1727a.HOUR_OF_DAY, Long.valueOf(jLongValue9 / 60));
            m718v(enumC1727a10, EnumC1727a.MINUTE_OF_HOUR, Long.valueOf(jLongValue9 % 60));
        }
        Map map10 = this.f540a;
        EnumC1727a enumC1727a11 = EnumC1727a.NANO_OF_SECOND;
        if (((HashMap) map10).containsKey(enumC1727a11)) {
            long jLongValue10 = ((Long) ((HashMap) this.f540a).get(enumC1727a11)).longValue();
            EnumC1684E enumC1684E3 = this.f544e;
            EnumC1684E enumC1684E4 = EnumC1684E.LENIENT;
            if (enumC1684E3 != enumC1684E4) {
                enumC1727a11.m800D(jLongValue10);
            }
            Map map11 = this.f540a;
            EnumC1727a enumC1727a12 = EnumC1727a.MICRO_OF_SECOND;
            if (((HashMap) map11).containsKey(enumC1727a12)) {
                long jLongValue11 = ((Long) ((HashMap) this.f540a).remove(enumC1727a12)).longValue();
                if (this.f544e != enumC1684E4) {
                    enumC1727a12.m800D(jLongValue11);
                }
                jLongValue10 = (jLongValue10 % 1000) + (jLongValue11 * 1000);
                m718v(enumC1727a12, enumC1727a11, Long.valueOf(jLongValue10));
            }
            Map map12 = this.f540a;
            EnumC1727a enumC1727a13 = EnumC1727a.MILLI_OF_SECOND;
            if (((HashMap) map12).containsKey(enumC1727a13)) {
                long jLongValue12 = ((Long) ((HashMap) this.f540a).remove(enumC1727a13)).longValue();
                if (this.f544e != enumC1684E4) {
                    enumC1727a13.m800D(jLongValue12);
                }
                m718v(enumC1727a13, enumC1727a11, Long.valueOf((jLongValue10 % 1000000) + (jLongValue12 * 1000000)));
            }
        }
        Map map13 = this.f540a;
        EnumC1727a enumC1727a14 = EnumC1727a.HOUR_OF_DAY;
        if (((HashMap) map13).containsKey(enumC1727a14)) {
            Map map14 = this.f540a;
            EnumC1727a enumC1727a15 = EnumC1727a.MINUTE_OF_HOUR;
            if (((HashMap) map14).containsKey(enumC1727a15)) {
                Map map15 = this.f540a;
                EnumC1727a enumC1727a16 = EnumC1727a.SECOND_OF_MINUTE;
                if (((HashMap) map15).containsKey(enumC1727a16) && ((HashMap) this.f540a).containsKey(enumC1727a11)) {
                    m714n(((Long) ((HashMap) this.f540a).remove(enumC1727a14)).longValue(), ((Long) ((HashMap) this.f540a).remove(enumC1727a15)).longValue(), ((Long) ((HashMap) this.f540a).remove(enumC1727a16)).longValue(), ((Long) ((HashMap) this.f540a).remove(enumC1727a11)).longValue());
                }
            }
        }
    }

    /* renamed from: n */
    public final void m714n(long j, long j2, long j3, long j4) {
        if (this.f544e == EnumC1684E.LENIENT) {
            long jM494M = AbstractC1636a.m494M(AbstractC1636a.m494M(AbstractC1636a.m494M(AbstractC1636a.m500S(j, 3600000000000L), AbstractC1636a.m500S(j2, 60000000000L)), AbstractC1636a.m500S(j3, 1000000000L)), j4);
            m716s(C1715i.m772V(AbstractC1636a.m498Q(jM494M, 86400000000000L)), Period.m611a(0, 0, (int) AbstractC1636a.m499R(jM494M, 86400000000000L)));
            return;
        }
        EnumC1727a enumC1727a = EnumC1727a.MINUTE_OF_HOUR;
        int iM820a = enumC1727a.f671b.m820a(j2, enumC1727a);
        EnumC1727a enumC1727a2 = EnumC1727a.NANO_OF_SECOND;
        int iM820a2 = enumC1727a2.f671b.m820a(j4, enumC1727a2);
        if (this.f544e == EnumC1684E.SMART && j == 24 && iM820a == 0 && j3 == 0 && iM820a2 == 0) {
            m716s(C1715i.f642g, Period.m611a(0, 0, 1));
            return;
        }
        EnumC1727a enumC1727a3 = EnumC1727a.HOUR_OF_DAY;
        int iM820a3 = enumC1727a3.f671b.m820a(j, enumC1727a3);
        EnumC1727a enumC1727a4 = EnumC1727a.SECOND_OF_MINUTE;
        m716s(C1715i.m771U(iM820a3, iM820a, enumC1727a4.f671b.m820a(j3, enumC1727a4), iM820a2), Period.f448d);
    }

    /* renamed from: s */
    public final void m716s(C1715i c1715i, Period period) {
        C1715i c1715i2 = this.f546g;
        if (c1715i2 != null) {
            if (!c1715i2.equals(c1715i)) {
                throw new C1640b("Conflict found: Fields resolved to different times: " + this.f546g + " " + c1715i);
            }
            Period period2 = this.f547h;
            period2.getClass();
            Period period3 = Period.f448d;
            if (period2 != period3 && period != period3 && !this.f547h.equals(period)) {
                throw new C1640b("Conflict found: Fields resolved to different excess periods: " + this.f547h + " " + period);
            }
            this.f547h = period;
            return;
        }
        this.f546g = c1715i;
        this.f547h = period;
    }

    /* renamed from: h */
    public final void m711h(InterfaceC1740n interfaceC1740n) {
        Iterator it = ((HashMap) this.f540a).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            InterfaceC1744r interfaceC1744r = (InterfaceC1744r) entry.getKey();
            if (interfaceC1740n.mo543e(interfaceC1744r)) {
                try {
                    long jMo542D = interfaceC1740n.mo542D(interfaceC1744r);
                    long jLongValue = ((Long) entry.getValue()).longValue();
                    if (jMo542D != jLongValue) {
                        throw new C1640b("Conflict found: Field " + interfaceC1744r + " " + jMo542D + " differs from " + interfaceC1744r + " " + jLongValue + " derived from " + interfaceC1740n);
                    }
                    it.remove();
                } catch (RuntimeException unused) {
                }
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(64);
        sb.append(this.f540a);
        sb.append(',');
        sb.append(this.f542c);
        if (this.f541b != null) {
            sb.append(',');
            sb.append(this.f541b);
        }
        if (this.f545f != null || this.f546g != null) {
            sb.append(" resolved to ");
            InterfaceC1652b interfaceC1652b = this.f545f;
            if (interfaceC1652b != null) {
                sb.append(interfaceC1652b);
                if (this.f546g != null) {
                    sb.append('T');
                    sb.append(this.f546g);
                }
            } else {
                sb.append(this.f546g);
            }
        }
        return sb.toString();
    }
}
