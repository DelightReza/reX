package p017j$.time.chrono;

import java.io.Serializable;
import org.telegram.messenger.MediaDataController;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.AbstractC1641c;
import p017j$.time.C1678e;
import p017j$.time.C1715i;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1743q;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;

/* renamed from: j$.time.chrono.d */
/* loaded from: classes2.dex */
public abstract class AbstractC1654d implements InterfaceC1652b, InterfaceC1739m, InterfaceC1741o, Serializable {
    private static final long serialVersionUID = 6282433883239719096L;

    @Override // java.lang.Comparable
    /* renamed from: N */
    public final /* synthetic */ int compareTo(InterfaceC1652b interfaceC1652b) {
        return AbstractC1636a.m508d(this, interfaceC1652b);
    }

    /* renamed from: R */
    public abstract InterfaceC1652b mo645R(long j);

    /* renamed from: S */
    public abstract InterfaceC1652b mo646S(long j);

    /* renamed from: T */
    public abstract InterfaceC1652b mo647T(long j);

    @Override // p017j$.time.chrono.InterfaceC1652b, p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public /* synthetic */ boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return AbstractC1636a.m519o(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final /* synthetic */ int mo544i(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m813a(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public /* synthetic */ C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        return AbstractC1745s.m816d(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final /* synthetic */ InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return AbstractC1636a.m505a(this, interfaceC1739m);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final /* synthetic */ Object mo547t(C1678e c1678e) {
        return AbstractC1636a.m521q(this, c1678e);
    }

    /* renamed from: Q */
    public static InterfaceC1652b m674Q(InterfaceC1661k interfaceC1661k, InterfaceC1739m interfaceC1739m) {
        InterfaceC1652b interfaceC1652b = (InterfaceC1652b) interfaceC1739m;
        if (interfaceC1661k.equals(interfaceC1652b.mo581a())) {
            return interfaceC1652b;
        }
        throw new ClassCastException("Chronology mismatch, expected: " + interfaceC1661k.getId() + ", actual: " + interfaceC1652b.mo581a().getId());
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: F */
    public ChronoLocalDateTime mo568F(C1715i c1715i) {
        return new C1656f(this, c1715i);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: d */
    public InterfaceC1652b mo557d(long j, InterfaceC1746t interfaceC1746t) {
        boolean z = interfaceC1746t instanceof EnumC1728b;
        if (!z) {
            if (!z) {
                return m674Q(mo581a(), interfaceC1746t.mo808i(this, j));
            }
            throw new C1747u("Unsupported unit: " + interfaceC1746t);
        }
        switch (AbstractC1653c.f480a[((EnumC1728b) interfaceC1746t).ordinal()]) {
            case 1:
                return mo645R(j);
            case 2:
                return mo645R(AbstractC1636a.m500S(j, 7));
            case 3:
                return mo646S(j);
            case 4:
                return mo647T(j);
            case 5:
                return mo647T(AbstractC1636a.m500S(j, 10));
            case 6:
                return mo647T(AbstractC1636a.m500S(j, 100));
            case 7:
                return mo647T(AbstractC1636a.m500S(j, MediaDataController.MAX_STYLE_RUNS_COUNT));
            case 8:
                EnumC1727a enumC1727a = EnumC1727a.ERA;
                return mo556c(AbstractC1636a.m494M(mo542D(enumC1727a), j), (InterfaceC1744r) enumC1727a);
            default:
                throw new C1747u("Unsupported unit: " + interfaceC1746t);
        }
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: G */
    public InterfaceC1662l mo569G() {
        return mo581a().mo662u(AbstractC1745s.m813a(this, EnumC1727a.ERA));
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: p */
    public boolean mo589p() {
        return mo581a().mo655O(mo542D(EnumC1727a.YEAR));
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: M */
    public int mo571M() {
        return mo589p() ? 366 : 365;
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof InterfaceC1652b) && AbstractC1636a.m508d(this, (InterfaceC1652b) obj) == 0;
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    public int hashCode() {
        long jMo567E = mo567E();
        return mo581a().hashCode() ^ ((int) (jMo567E ^ (jMo567E >>> 32)));
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: x */
    public InterfaceC1652b mo591x(InterfaceC1741o interfaceC1741o) {
        return m674Q(mo581a(), interfaceC1741o.mo546n(this));
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    public final String toString() {
        long jMo542D = mo542D(EnumC1727a.YEAR_OF_ERA);
        long jMo542D2 = mo542D(EnumC1727a.MONTH_OF_YEAR);
        long jMo542D3 = mo542D(EnumC1727a.DAY_OF_MONTH);
        StringBuilder sb = new StringBuilder(30);
        sb.append(mo581a().toString());
        sb.append(" ");
        sb.append(mo569G());
        sb.append(" ");
        sb.append(jMo542D);
        sb.append(jMo542D2 < 10 ? "-0" : "-");
        sb.append(jMo542D2);
        sb.append(jMo542D3 < 10 ? "-0" : "-");
        sb.append(jMo542D3);
        return sb.toString();
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: c */
    public InterfaceC1652b mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
        return m674Q(mo581a(), interfaceC1744r.mo807y(this, j));
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: J */
    public InterfaceC1652b mo570J(InterfaceC1743q interfaceC1743q) {
        return m674Q(mo581a(), interfaceC1743q.mo550i(this));
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: r */
    public InterfaceC1652b mo559y(long j, InterfaceC1746t interfaceC1746t) {
        return m674Q(mo581a(), AbstractC1745s.m814b(this, j, interfaceC1746t));
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: E */
    public long mo567E() {
        return mo542D(EnumC1727a.EPOCH_DAY);
    }
}
