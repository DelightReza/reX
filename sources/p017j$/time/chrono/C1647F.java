package p017j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1640b;
import p017j$.time.Instant;
import p017j$.time.LocalDate;
import p017j$.time.ZoneId;
import p017j$.time.format.EnumC1684E;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1740n;

/* renamed from: j$.time.chrono.F */
/* loaded from: classes2.dex */
public final class C1647F extends AbstractC1651a implements Serializable {

    /* renamed from: c */
    public static final C1647F f473c = new C1647F();
    private static final long serialVersionUID = 2775954514031616474L;

    static {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        map.put("en", new String[]{"BB", "BE"});
        map.put("th", new String[]{"BB", "BE"});
        map2.put("en", new String[]{"B.B.", "B.E."});
        map2.put("th", new String[]{"พ.ศ.", "ปีก่อนคริสต์กาลที่"});
        map3.put("en", new String[]{"Before Buddhist", "Budhhist Era"});
        map3.put("th", new String[]{"พุทธศักราช", "ปีก่อนคริสต์กาลที่"});
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: u */
    public final InterfaceC1662l mo662u(int i) {
        if (i == 0) {
            return EnumC1650I.BEFORE_BE;
        }
        if (i == 1) {
            return EnumC1650I.f476BE;
        }
        throw new C1640b("Invalid era: " + i);
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    public final String getId() {
        return "ThaiBuddhist";
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: l */
    public final String mo658l() {
        return "buddhist";
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: I */
    public final InterfaceC1652b mo652I(int i, int i2, int i3) {
        return new C1649H(LocalDate.m566of(i - 543, i2, i3));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: m */
    public final InterfaceC1652b mo659m(int i, int i2) {
        return new C1649H(LocalDate.m564c0(i - 543, i2));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: h */
    public final InterfaceC1652b mo656h(long j) {
        return new C1649H(LocalDate.m563b0(j));
    }

    @Override // p017j$.time.chrono.AbstractC1651a
    /* renamed from: j */
    public final InterfaceC1652b mo657j() {
        return new C1649H(LocalDate.m561S(LocalDate.m562a0(AbstractC1636a.m504W())));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: A */
    public final InterfaceC1652b mo651A(InterfaceC1740n interfaceC1740n) {
        if (interfaceC1740n instanceof C1649H) {
            return (C1649H) interfaceC1740n;
        }
        return new C1649H(LocalDate.m561S(interfaceC1740n));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: O */
    public final boolean mo655O(long j) {
        return C1668r.f512c.mo655O(j - 543);
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: v */
    public final int mo663v(InterfaceC1662l interfaceC1662l, int i) {
        if (interfaceC1662l instanceof EnumC1650I) {
            return interfaceC1662l == EnumC1650I.f476BE ? i : 1 - i;
        }
        throw new ClassCastException("Era must be BuddhistEra");
    }

    private C1647F() {
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: s */
    public final List mo661s() {
        return AbstractC1636a.m495N(EnumC1650I.values());
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: q */
    public final C1748v mo660q(EnumC1727a enumC1727a) {
        int i = AbstractC1646E.f472a[enumC1727a.ordinal()];
        if (i == 1) {
            C1748v c1748v = EnumC1727a.PROLEPTIC_MONTH.f671b;
            return C1748v.m818f(c1748v.f697a + 6516, c1748v.f700d + 6516);
        }
        if (i == 2) {
            C1748v c1748v2 = EnumC1727a.YEAR.f671b;
            return C1748v.m819g(1L, (-(c1748v2.f697a + 543)) + 1, c1748v2.f700d + 543);
        }
        if (i != 3) {
            return enumC1727a.f671b;
        }
        C1748v c1748v3 = EnumC1727a.YEAR.f671b;
        return C1748v.m818f(c1748v3.f697a + 543, c1748v3.f700d + 543);
    }

    @Override // p017j$.time.chrono.AbstractC1651a, p017j$.time.chrono.InterfaceC1661k
    /* renamed from: K */
    public final InterfaceC1652b mo653K(Map map, EnumC1684E enumC1684E) {
        return (C1649H) super.mo653K(map, enumC1684E);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: L */
    public final ChronoZonedDateTime mo654L(Instant instant, ZoneId zoneId) {
        return C1660j.m682R(this, instant, zoneId);
    }

    public Object writeReplace() {
        return new C1645D((byte) 1, this);
    }
}
