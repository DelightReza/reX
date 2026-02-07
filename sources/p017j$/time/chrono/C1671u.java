package p017j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1640b;
import p017j$.time.C1678e;
import p017j$.time.Instant;
import p017j$.time.LocalDate;
import p017j$.time.ZoneId;
import p017j$.time.format.EnumC1684E;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.util.Objects;

/* renamed from: j$.time.chrono.u */
/* loaded from: classes2.dex */
public final class C1671u extends AbstractC1651a implements Serializable {

    /* renamed from: c */
    public static final C1671u f516c = new C1671u();
    private static final long serialVersionUID = 459996390165777884L;

    @Override // p017j$.time.chrono.InterfaceC1661k
    public final String getId() {
        return "Japanese";
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: l */
    public final String mo658l() {
        return "japanese";
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: I */
    public final InterfaceC1652b mo652I(int i, int i2, int i3) {
        return new C1673w(LocalDate.m566of(i, i2, i3));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: m */
    public final InterfaceC1652b mo659m(int i, int i2) {
        return new C1673w(LocalDate.m564c0(i, i2));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: h */
    public final InterfaceC1652b mo656h(long j) {
        return new C1673w(LocalDate.m563b0(j));
    }

    @Override // p017j$.time.chrono.AbstractC1651a
    /* renamed from: j */
    public final InterfaceC1652b mo657j() {
        return new C1673w(LocalDate.m561S(LocalDate.m562a0(AbstractC1636a.m504W())));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: A */
    public final InterfaceC1652b mo651A(InterfaceC1740n interfaceC1740n) {
        if (interfaceC1740n instanceof C1673w) {
            return (C1673w) interfaceC1740n;
        }
        return new C1673w(LocalDate.m561S(interfaceC1740n));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: s */
    public final List mo661s() {
        C1674x[] c1674xArr = C1674x.f523e;
        return AbstractC1636a.m495N((C1674x[]) Arrays.copyOf(c1674xArr, c1674xArr.length));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: O */
    public final boolean mo655O(long j) {
        return C1668r.f512c.mo655O(j);
    }

    private C1671u() {
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: v */
    public final int mo663v(InterfaceC1662l interfaceC1662l, int i) {
        if (!(interfaceC1662l instanceof C1674x)) {
            throw new ClassCastException("Era must be JapaneseEra");
        }
        C1674x c1674x = (C1674x) interfaceC1662l;
        int year = (c1674x.f525b.getYear() + i) - 1;
        if (i != 1 && (year < -999999999 || year > 999999999 || year < c1674x.f525b.getYear() || interfaceC1662l != C1674x.m701h(LocalDate.m566of(year, 1, 1)))) {
            throw new C1640b("Invalid yearOfEra value");
        }
        return year;
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: u */
    public final InterfaceC1662l mo662u(int i) {
        return C1674x.m702m(i);
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: q */
    public final C1748v mo660q(EnumC1727a enumC1727a) {
        switch (AbstractC1670t.f515a[enumC1727a.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
                throw new C1747u("Unsupported field: " + enumC1727a);
            case 5:
                C1674x[] c1674xArr = C1674x.f523e;
                int year = c1674xArr[c1674xArr.length - 1].f525b.getYear();
                int year2 = 1000000000 - c1674xArr[c1674xArr.length - 1].f525b.getYear();
                int year3 = c1674xArr[0].f525b.getYear();
                int i = 1;
                while (true) {
                    C1674x[] c1674xArr2 = C1674x.f523e;
                    if (i >= c1674xArr2.length) {
                        return C1748v.m819g(1L, year2, 999999999 - year);
                    }
                    C1674x c1674x = c1674xArr2[i];
                    year2 = Math.min(year2, (c1674x.f525b.getYear() - year3) + 1);
                    year3 = c1674x.f525b.getYear();
                    i++;
                }
            case 6:
                C1674x c1674x2 = C1674x.f522d;
                long j = EnumC1727a.DAY_OF_YEAR.f671b.f699c;
                long jMin = j;
                for (C1674x c1674x3 : C1674x.f523e) {
                    long jMin2 = Math.min(jMin, (c1674x3.f525b.mo571M() - c1674x3.f525b.m576V()) + 1);
                    jMin = c1674x3.m703l() != null ? Math.min(jMin2, c1674x3.m703l().f525b.m576V() - 1) : jMin2;
                }
                return C1748v.m819g(1L, jMin, EnumC1727a.DAY_OF_YEAR.f671b.f700d);
            case 7:
                return C1748v.m818f(C1673w.f518d.getYear(), 999999999L);
            case 8:
                long j2 = C1674x.f522d.f524a;
                C1674x[] c1674xArr3 = C1674x.f523e;
                return C1748v.m818f(j2, c1674xArr3[c1674xArr3.length - 1].f524a);
            default:
                return enumC1727a.f671b;
        }
    }

    @Override // p017j$.time.chrono.AbstractC1651a, p017j$.time.chrono.InterfaceC1661k
    /* renamed from: K */
    public final InterfaceC1652b mo653K(Map map, EnumC1684E enumC1684E) {
        return (C1673w) super.mo653K(map, enumC1684E);
    }

    @Override // p017j$.time.chrono.AbstractC1651a
    /* renamed from: D */
    public final InterfaceC1652b mo671D(Map map, EnumC1684E enumC1684E) {
        C1673w c1673wM699W;
        EnumC1727a enumC1727a = EnumC1727a.ERA;
        Long l = (Long) map.get(enumC1727a);
        C1674x c1674xM702m = l != null ? C1674x.m702m(mo660q(enumC1727a).m820a(l.longValue(), enumC1727a)) : null;
        EnumC1727a enumC1727a2 = EnumC1727a.YEAR_OF_ERA;
        Long l2 = (Long) map.get(enumC1727a2);
        int iM820a = l2 != null ? mo660q(enumC1727a2).m820a(l2.longValue(), enumC1727a2) : 0;
        if (c1674xM702m == null && l2 != null && !map.containsKey(EnumC1727a.YEAR) && enumC1684E != EnumC1684E.STRICT) {
            C1674x[] c1674xArr = C1674x.f523e;
            c1674xM702m = ((C1674x[]) Arrays.copyOf(c1674xArr, c1674xArr.length))[((C1674x[]) Arrays.copyOf(c1674xArr, c1674xArr.length)).length - 1];
        }
        if (l2 != null && c1674xM702m != null) {
            EnumC1727a enumC1727a3 = EnumC1727a.MONTH_OF_YEAR;
            if (map.containsKey(enumC1727a3)) {
                EnumC1727a enumC1727a4 = EnumC1727a.DAY_OF_MONTH;
                if (map.containsKey(enumC1727a4)) {
                    map.remove(enumC1727a);
                    map.remove(enumC1727a2);
                    if (enumC1684E == EnumC1684E.LENIENT) {
                        return new C1673w(LocalDate.m566of((c1674xM702m.f525b.getYear() + iM820a) - 1, 1, 1)).m697U(AbstractC1636a.m501T(((Long) map.remove(enumC1727a3)).longValue(), 1L), EnumC1728b.MONTHS).m697U(AbstractC1636a.m501T(((Long) map.remove(enumC1727a4)).longValue(), 1L), EnumC1728b.DAYS);
                    }
                    int iM820a2 = mo660q(enumC1727a3).m820a(((Long) map.remove(enumC1727a3)).longValue(), enumC1727a3);
                    int iM820a3 = mo660q(enumC1727a4).m820a(((Long) map.remove(enumC1727a4)).longValue(), enumC1727a4);
                    if (enumC1684E != EnumC1684E.SMART) {
                        LocalDate localDate = C1673w.f518d;
                        Objects.requireNonNull(c1674xM702m, "era");
                        LocalDate localDateM566of = LocalDate.m566of((c1674xM702m.f525b.getYear() + iM820a) - 1, iM820a2, iM820a3);
                        if (localDateM566of.m578X(c1674xM702m.f525b) || c1674xM702m != C1674x.m701h(localDateM566of)) {
                            throw new C1640b("year, month, and day not valid for Era");
                        }
                        return new C1673w(c1674xM702m, iM820a, localDateM566of);
                    }
                    if (iM820a < 1) {
                        throw new C1640b("Invalid YearOfEra: " + iM820a);
                    }
                    int year = (c1674xM702m.f525b.getYear() + iM820a) - 1;
                    try {
                        c1673wM699W = new C1673w(LocalDate.m566of(year, iM820a2, iM820a3));
                    } catch (C1640b unused) {
                        c1673wM699W = new C1673w(LocalDate.m566of(year, iM820a2, 1)).m699W(new C1678e(2));
                    }
                    if (c1673wM699W.f520b == c1674xM702m || AbstractC1745s.m813a(c1673wM699W, EnumC1727a.YEAR_OF_ERA) <= 1 || iM820a <= 1) {
                        return c1673wM699W;
                    }
                    throw new C1640b("Invalid YearOfEra for Era: " + c1674xM702m + " " + iM820a);
                }
            }
            EnumC1727a enumC1727a5 = EnumC1727a.DAY_OF_YEAR;
            if (map.containsKey(enumC1727a5)) {
                map.remove(enumC1727a);
                map.remove(enumC1727a2);
                if (enumC1684E == EnumC1684E.LENIENT) {
                    return new C1673w(LocalDate.m564c0((c1674xM702m.f525b.getYear() + iM820a) - 1, 1)).m697U(AbstractC1636a.m501T(((Long) map.remove(enumC1727a5)).longValue(), 1L), EnumC1728b.DAYS);
                }
                int iM820a4 = mo660q(enumC1727a5).m820a(((Long) map.remove(enumC1727a5)).longValue(), enumC1727a5);
                LocalDate localDate2 = C1673w.f518d;
                Objects.requireNonNull(c1674xM702m, "era");
                LocalDate localDateM564c0 = iM820a == 1 ? LocalDate.m564c0(c1674xM702m.f525b.getYear(), (c1674xM702m.f525b.m576V() + iM820a4) - 1) : LocalDate.m564c0((c1674xM702m.f525b.getYear() + iM820a) - 1, iM820a4);
                if (localDateM564c0.m578X(c1674xM702m.f525b) || c1674xM702m != C1674x.m701h(localDateM564c0)) {
                    throw new C1640b("Invalid parameters");
                }
                return new C1673w(c1674xM702m, iM820a, localDateM564c0);
            }
        }
        return null;
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: L */
    public final ChronoZonedDateTime mo654L(Instant instant, ZoneId zoneId) {
        return C1660j.m682R(this, instant, zoneId);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    public Object writeReplace() {
        return new C1645D((byte) 1, this);
    }
}
