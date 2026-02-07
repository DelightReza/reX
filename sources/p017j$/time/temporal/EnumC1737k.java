package p017j$.time.temporal;

import java.util.Map;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1640b;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.format.C1683D;
import p017j$.time.format.EnumC1684E;

/* renamed from: j$.time.temporal.k */
/* loaded from: classes2.dex */
public enum EnumC1737k implements InterfaceC1744r {
    JULIAN_DAY("JulianDay", 2440588),
    MODIFIED_JULIAN_DAY("ModifiedJulianDay", 40587),
    RATA_DIE("RataDie", 719163);

    private static final long serialVersionUID = -7501623920830201812L;

    /* renamed from: a */
    public final transient String f684a;

    /* renamed from: b */
    public final transient C1748v f685b;

    /* renamed from: c */
    public final transient long f686c;

    @Override // p017j$.time.temporal.InterfaceC1744r
    public final boolean isDateBased() {
        return true;
    }

    static {
        EnumC1728b enumC1728b = EnumC1728b.NANOS;
    }

    EnumC1737k(String str, long j) {
        this.f684a = str;
        this.f685b = C1748v.m818f((-365243219162L) + j, 365241780471L + j);
        this.f686c = j;
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: n */
    public final C1748v mo805n() {
        return this.f685b;
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: y */
    public final InterfaceC1739m mo807y(InterfaceC1739m interfaceC1739m, long j) {
        if (!this.f685b.m824e(j)) {
            throw new C1640b("Invalid value: " + this.f684a + " " + j);
        }
        return interfaceC1739m.mo556c(AbstractC1636a.m501T(j, this.f686c), EnumC1727a.EPOCH_DAY);
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: i */
    public final boolean mo802i(InterfaceC1740n interfaceC1740n) {
        return interfaceC1740n.mo543e(EnumC1727a.EPOCH_DAY);
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: j */
    public final C1748v mo803j(InterfaceC1740n interfaceC1740n) {
        if (interfaceC1740n.mo543e(EnumC1727a.EPOCH_DAY)) {
            return this.f685b;
        }
        throw new C1640b("Unsupported field: " + this);
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: t */
    public final long mo806t(InterfaceC1740n interfaceC1740n) {
        return interfaceC1740n.mo542D(EnumC1727a.EPOCH_DAY) + this.f686c;
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: k */
    public final InterfaceC1740n mo804k(Map map, C1683D c1683d, EnumC1684E enumC1684E) {
        long jLongValue = ((Long) map.remove(this)).longValue();
        InterfaceC1661k interfaceC1661kM492K = AbstractC1636a.m492K(c1683d);
        EnumC1684E enumC1684E2 = EnumC1684E.LENIENT;
        long j = this.f686c;
        if (enumC1684E == enumC1684E2) {
            return interfaceC1661kM492K.mo656h(AbstractC1636a.m501T(jLongValue, j));
        }
        this.f685b.m821b(jLongValue, this);
        return interfaceC1661kM492K.mo656h(jLongValue - j);
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f684a;
    }
}
