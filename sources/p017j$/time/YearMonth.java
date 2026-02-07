package p017j$.time;

import de.robv.android.xposed.callbacks.XCallback;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Locale;
import org.mvel2.asm.signature.SignatureVisitor;
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

/* loaded from: classes2.dex */
public final class YearMonth implements InterfaceC1739m, InterfaceC1741o, Comparable<YearMonth>, Serializable {

    /* renamed from: c */
    public static final /* synthetic */ int f452c = 0;
    private static final long serialVersionUID = 4183400860270640070L;

    /* renamed from: a */
    public final int f453a;

    /* renamed from: b */
    public final int f454b;

    @Override // java.lang.Comparable
    public final int compareTo(YearMonth yearMonth) {
        YearMonth yearMonth2 = yearMonth;
        int i = this.f453a - yearMonth2.f453a;
        return i == 0 ? this.f454b - yearMonth2.f454b : i;
    }

    static {
        C1707u c1707u = new C1707u();
        c1707u.m755m(EnumC1727a.YEAR, 4, 10, EnumC1685F.EXCEEDS_PAD);
        c1707u.m746d(SignatureVisitor.SUPER);
        c1707u.m754l(EnumC1727a.MONTH_OF_YEAR, 2);
        c1707u.m759q(Locale.getDefault(), EnumC1684E.SMART, null);
    }

    /* renamed from: of */
    public static YearMonth m612of(int i, int i2) {
        EnumC1727a.YEAR.m800D(i);
        EnumC1727a.MONTH_OF_YEAR.m800D(i2);
        return new YearMonth(i, i2);
    }

    public YearMonth(int i, int i2) {
        this.f453a = i;
        this.f454b = i2;
    }

    /* renamed from: U */
    public final YearMonth m617U(int i, int i2) {
        return (this.f453a == i && this.f454b == i2) ? this : new YearMonth(i, i2);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return interfaceC1744r instanceof EnumC1727a ? interfaceC1744r == EnumC1727a.YEAR || interfaceC1744r == EnumC1727a.MONTH_OF_YEAR || interfaceC1744r == EnumC1727a.PROLEPTIC_MONTH || interfaceC1744r == EnumC1727a.YEAR_OF_ERA || interfaceC1744r == EnumC1727a.ERA : interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.YEAR_OF_ERA) {
            return C1748v.m818f(1L, this.f453a <= 0 ? 1000000000L : 999999999L);
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
        int i;
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return interfaceC1744r.mo806t(this);
        }
        int i2 = AbstractC1725s.f665a[((EnumC1727a) interfaceC1744r).ordinal()];
        if (i2 == 1) {
            i = this.f454b;
        } else {
            if (i2 == 2) {
                return m613Q();
            }
            if (i2 == 3) {
                int i3 = this.f453a;
                if (i3 < 1) {
                    i3 = 1 - i3;
                }
                return i3;
            }
            if (i2 != 4) {
                if (i2 == 5) {
                    return this.f453a < 1 ? 0 : 1;
                }
                throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
            }
            i = this.f453a;
        }
        return i;
    }

    /* renamed from: Q */
    public final long m613Q() {
        return ((this.f453a * 12) + this.f454b) - 1;
    }

    public int lengthOfMonth() {
        return EnumC1717k.m786T(this.f454b).m788R(C1668r.f512c.mo655O(this.f453a));
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        localDate.getClass();
        return (YearMonth) AbstractC1636a.m505a(localDate, this);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: V, reason: merged with bridge method [inline-methods] */
    public final YearMonth mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return (YearMonth) interfaceC1744r.mo807y(this, j);
        }
        EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
        enumC1727a.m800D(j);
        int i = AbstractC1725s.f665a[enumC1727a.ordinal()];
        if (i == 1) {
            int i2 = (int) j;
            EnumC1727a.MONTH_OF_YEAR.m800D(i2);
            return m617U(this.f453a, i2);
        }
        if (i == 2) {
            return m615S(j - m613Q());
        }
        if (i == 3) {
            if (this.f453a < 1) {
                j = 1 - j;
            }
            int i3 = (int) j;
            EnumC1727a.YEAR.m800D(i3);
            return m617U(i3, this.f454b);
        }
        if (i == 4) {
            int i4 = (int) j;
            EnumC1727a.YEAR.m800D(i4);
            return m617U(i4, this.f454b);
        }
        if (i != 5) {
            throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
        if (mo542D(EnumC1727a.ERA) == j) {
            return this;
        }
        int i5 = 1 - this.f453a;
        EnumC1727a.YEAR.m800D(i5);
        return m617U(i5, this.f454b);
    }

    @Override // p017j$.time.temporal.InterfaceC1739m
    /* renamed from: R, reason: merged with bridge method [inline-methods] */
    public final YearMonth mo557d(long j, InterfaceC1746t interfaceC1746t) {
        if (!(interfaceC1746t instanceof EnumC1728b)) {
            return (YearMonth) interfaceC1746t.mo808i(this, j);
        }
        switch (AbstractC1725s.f666b[((EnumC1728b) interfaceC1746t).ordinal()]) {
            case 1:
                return m615S(j);
            case 2:
                return m616T(j);
            case 3:
                return m616T(AbstractC1636a.m500S(j, 10));
            case 4:
                return m616T(AbstractC1636a.m500S(j, 100));
            case 5:
                return m616T(AbstractC1636a.m500S(j, MediaDataController.MAX_STYLE_RUNS_COUNT));
            case 6:
                EnumC1727a enumC1727a = EnumC1727a.ERA;
                return mo556c(AbstractC1636a.m494M(mo542D(enumC1727a), j), enumC1727a);
            default:
                throw new C1747u("Unsupported unit: " + interfaceC1746t);
        }
    }

    /* renamed from: T */
    public final YearMonth m616T(long j) {
        if (j == 0) {
            return this;
        }
        EnumC1727a enumC1727a = EnumC1727a.YEAR;
        return m617U(enumC1727a.f671b.m820a(this.f453a + j, enumC1727a), this.f454b);
    }

    /* renamed from: S */
    public final YearMonth m615S(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (this.f453a * 12) + (this.f454b - 1) + j;
        EnumC1727a enumC1727a = EnumC1727a.YEAR;
        long j3 = 12;
        return m617U(enumC1727a.f671b.m820a(AbstractC1636a.m499R(j2, j3), enumC1727a), ((int) AbstractC1636a.m498Q(j2, j3)) + 1);
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
            return EnumC1728b.MONTHS;
        }
        return AbstractC1745s.m815c(this, c1678e);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        if (!AbstractC1636a.m492K(interfaceC1739m).equals(C1668r.f512c)) {
            throw new C1640b("Adjustment only supported on ISO date-time");
        }
        return interfaceC1739m.mo556c(m613Q(), EnumC1727a.PROLEPTIC_MONTH);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof YearMonth) {
            YearMonth yearMonth = (YearMonth) obj;
            if (this.f453a == yearMonth.f453a && this.f454b == yearMonth.f454b) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.f453a ^ (this.f454b << 27);
    }

    public final String toString() {
        int iAbs = Math.abs(this.f453a);
        StringBuilder sb = new StringBuilder(9);
        if (iAbs < 1000) {
            int i = this.f453a;
            if (i < 0) {
                sb.append(i + XCallback.PRIORITY_LOWEST);
                sb.deleteCharAt(1);
            } else {
                sb.append(i + XCallback.PRIORITY_HIGHEST);
                sb.deleteCharAt(0);
            }
        } else {
            sb.append(this.f453a);
        }
        sb.append(this.f454b < 10 ? "-0" : "-");
        sb.append(this.f454b);
        return sb.toString();
    }

    private Object writeReplace() {
        return new C1722p((byte) 12, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
