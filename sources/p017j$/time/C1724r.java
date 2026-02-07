package p017j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Locale;
import org.telegram.messenger.MediaDataController;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.chrono.C1668r;
import p017j$.time.format.C1707u;
import p017j$.time.format.EnumC1684E;
import p017j$.time.format.EnumC1685F;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;

/* renamed from: j$.time.r */
/* loaded from: classes2.dex */
public final class C1724r implements InterfaceC1739m, InterfaceC1741o, Comparable, Serializable {

    /* renamed from: b */
    public static final /* synthetic */ int f663b = 0;
    private static final long serialVersionUID = -23038383694477807L;

    /* renamed from: a */
    public final int f664a;

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return this.f664a - ((C1724r) obj).f664a;
    }

    static {
        C1707u c1707u = new C1707u();
        c1707u.m755m(EnumC1727a.YEAR, 4, 10, EnumC1685F.EXCEEDS_PAD);
        c1707u.m759q(Locale.getDefault(), EnumC1684E.SMART, null);
    }

    /* renamed from: Q */
    public static C1724r m793Q(int i) {
        EnumC1727a.YEAR.m800D(i);
        return new C1724r(i);
    }

    public C1724r(int i) {
        this.f664a = i;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return interfaceC1744r instanceof EnumC1727a ? interfaceC1744r == EnumC1727a.YEAR || interfaceC1744r == EnumC1727a.YEAR_OF_ERA || interfaceC1744r == EnumC1727a.ERA : interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.YEAR_OF_ERA) {
            return C1748v.m818f(1L, this.f664a <= 0 ? 1000000000L : 999999999L);
        }
        return AbstractC1745s.m816d(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        return mo545k(interfaceC1744r).m820a(mo542D(interfaceC1744r), interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return interfaceC1744r.mo806t(this);
        }
        int i = AbstractC1723q.f661a[((EnumC1727a) interfaceC1744r).ordinal()];
        if (i == 1) {
            int i2 = this.f664a;
            if (i2 < 1) {
                i2 = 1 - i2;
            }
            return i2;
        }
        if (i == 2) {
            return this.f664a;
        }
        if (i == 3) {
            return this.f664a < 1 ? 0 : 1;
        }
        throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        localDate.getClass();
        return (C1724r) AbstractC1636a.m505a(localDate, this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: T, reason: merged with bridge method [inline-methods] */
    public final C1724r mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return (C1724r) interfaceC1744r.mo807y(this, j);
        }
        EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
        enumC1727a.m800D(j);
        int i = AbstractC1723q.f661a[enumC1727a.ordinal()];
        if (i == 1) {
            if (this.f664a < 1) {
                j = 1 - j;
            }
            return m793Q((int) j);
        }
        if (i == 2) {
            return m793Q((int) j);
        }
        if (i == 3) {
            return mo542D(EnumC1727a.ERA) == j ? this : m793Q(1 - this.f664a);
        }
        throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: R, reason: merged with bridge method [inline-methods] */
    public final C1724r mo557d(long j, InterfaceC1746t interfaceC1746t) {
        if (!(interfaceC1746t instanceof EnumC1728b)) {
            return (C1724r) interfaceC1746t.mo808i(this, j);
        }
        int i = AbstractC1723q.f662b[((EnumC1728b) interfaceC1746t).ordinal()];
        if (i == 1) {
            return m795S(j);
        }
        if (i == 2) {
            return m795S(AbstractC1636a.m500S(j, 10));
        }
        if (i == 3) {
            return m795S(AbstractC1636a.m500S(j, 100));
        }
        if (i == 4) {
            return m795S(AbstractC1636a.m500S(j, MediaDataController.MAX_STYLE_RUNS_COUNT));
        }
        if (i == 5) {
            EnumC1727a enumC1727a = EnumC1727a.ERA;
            return mo556c(AbstractC1636a.m494M(mo542D(enumC1727a), j), enumC1727a);
        }
        throw new C1747u("Unsupported unit: " + interfaceC1746t);
    }

    /* renamed from: S */
    public final C1724r m795S(long j) {
        if (j == 0) {
            return this;
        }
        EnumC1727a enumC1727a = EnumC1727a.YEAR;
        return m793Q(enumC1727a.f671b.m820a(this.f664a + j, enumC1727a));
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return j == Long.MIN_VALUE ? mo557d(Long.MAX_VALUE, enumC1728b).mo557d(1L, enumC1728b) : mo557d(-j, enumC1728b);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f691b) {
            return C1668r.f512c;
        }
        if (c1678e == AbstractC1745s.f692c) {
            return EnumC1728b.YEARS;
        }
        return AbstractC1745s.m815c(this, c1678e);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        if (!AbstractC1636a.m492K(interfaceC1739m).equals(C1668r.f512c)) {
            throw new C1640b("Adjustment only supported on ISO date-time");
        }
        return interfaceC1739m.mo556c(this.f664a, EnumC1727a.YEAR);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof C1724r) && this.f664a == ((C1724r) obj).f664a;
    }

    public final int hashCode() {
        return this.f664a;
    }

    public final String toString() {
        return Integer.toString(this.f664a);
    }

    private Object writeReplace() {
        return new C1722p((byte) 11, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
