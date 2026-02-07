package p017j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
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

/* renamed from: j$.time.chrono.z */
/* loaded from: classes2.dex */
public final class C1676z extends AbstractC1651a implements Serializable {

    /* renamed from: c */
    public static final C1676z f528c = new C1676z();
    private static final long serialVersionUID = 1039765215346859963L;

    @Override // p017j$.time.chrono.InterfaceC1661k
    public final String getId() {
        return "Minguo";
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: u */
    public final InterfaceC1662l mo662u(int i) {
        if (i == 0) {
            return EnumC1644C.BEFORE_ROC;
        }
        if (i == 1) {
            return EnumC1644C.ROC;
        }
        throw new C1640b("Invalid era: " + i);
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: l */
    public final String mo658l() {
        return "roc";
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: I */
    public final InterfaceC1652b mo652I(int i, int i2, int i3) {
        return new C1643B(LocalDate.m566of(i + 1911, i2, i3));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: m */
    public final InterfaceC1652b mo659m(int i, int i2) {
        return new C1643B(LocalDate.m564c0(i + 1911, i2));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: h */
    public final InterfaceC1652b mo656h(long j) {
        return new C1643B(LocalDate.m563b0(j));
    }

    @Override // p017j$.time.chrono.AbstractC1651a
    /* renamed from: j */
    public final InterfaceC1652b mo657j() {
        return new C1643B(LocalDate.m561S(LocalDate.m562a0(AbstractC1636a.m504W())));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: A */
    public final InterfaceC1652b mo651A(InterfaceC1740n interfaceC1740n) {
        if (interfaceC1740n instanceof C1643B) {
            return (C1643B) interfaceC1740n;
        }
        return new C1643B(LocalDate.m561S(interfaceC1740n));
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: O */
    public final boolean mo655O(long j) {
        return C1668r.f512c.mo655O(j + 1911);
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: v */
    public final int mo663v(InterfaceC1662l interfaceC1662l, int i) {
        if (interfaceC1662l instanceof EnumC1644C) {
            return interfaceC1662l == EnumC1644C.ROC ? i : 1 - i;
        }
        throw new ClassCastException("Era must be MinguoEra");
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: s */
    public final List mo661s() {
        return AbstractC1636a.m495N(EnumC1644C.values());
    }

    @Override // p017j$.time.chrono.InterfaceC1661k
    /* renamed from: q */
    public final C1748v mo660q(EnumC1727a enumC1727a) {
        int i = AbstractC1675y.f527a[enumC1727a.ordinal()];
        if (i == 1) {
            C1748v c1748v = EnumC1727a.PROLEPTIC_MONTH.f671b;
            return C1748v.m818f(c1748v.f697a - 22932, c1748v.f700d - 22932);
        }
        if (i == 2) {
            C1748v c1748v2 = EnumC1727a.YEAR.f671b;
            return C1748v.m819g(1L, c1748v2.f700d - 1911, (-c1748v2.f697a) + 1912);
        }
        if (i != 3) {
            return enumC1727a.f671b;
        }
        C1748v c1748v3 = EnumC1727a.YEAR.f671b;
        return C1748v.m818f(c1748v3.f697a - 1911, c1748v3.f700d - 1911);
    }

    @Override // p017j$.time.chrono.AbstractC1651a, p017j$.time.chrono.InterfaceC1661k
    /* renamed from: K */
    public final InterfaceC1652b mo653K(Map map, EnumC1684E enumC1684E) {
        return (C1643B) super.mo653K(map, enumC1684E);
    }

    private C1676z() {
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
