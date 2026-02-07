package p017j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import p017j$.time.AbstractC1641c;
import p017j$.time.C1640b;
import p017j$.time.C1678e;
import p017j$.time.C1715i;
import p017j$.time.LocalDate;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1743q;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.time.temporal.InterfaceC1746t;

/* renamed from: j$.time.chrono.w */
/* loaded from: classes2.dex */
public final class C1673w extends AbstractC1654d {

    /* renamed from: d */
    public static final LocalDate f518d = LocalDate.m566of(1873, 1, 1);
    private static final long serialVersionUID = -305327627230580483L;

    /* renamed from: a */
    public final transient LocalDate f519a;

    /* renamed from: b */
    public final transient C1674x f520b;

    /* renamed from: c */
    public final transient int f521c;

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: F */
    public final ChronoLocalDateTime mo568F(C1715i c1715i) {
        return new C1656f(this, c1715i);
    }

    public C1673w(LocalDate localDate) {
        if (localDate.m578X(f518d)) {
            throw new C1640b("JapaneseDate before Meiji 6 is not supported");
        }
        C1674x c1674xM701h = C1674x.m701h(localDate);
        this.f520b = c1674xM701h;
        this.f521c = (localDate.getYear() - c1674xM701h.f525b.getYear()) + 1;
        this.f519a = localDate;
    }

    public C1673w(C1674x c1674x, int i, LocalDate localDate) {
        if (localDate.m578X(f518d)) {
            throw new C1640b("JapaneseDate before Meiji 6 is not supported");
        }
        this.f520b = c1674x;
        this.f521c = i;
        this.f519a = localDate;
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: a */
    public final InterfaceC1661k mo581a() {
        return C1671u.f516c;
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    public final int hashCode() {
        C1671u.f516c.getClass();
        return this.f519a.hashCode() ^ (-688086063);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: G */
    public final InterfaceC1662l mo569G() {
        return this.f520b;
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: M */
    public final int mo571M() {
        int iMo571M;
        C1674x c1674xM703l = this.f520b.m703l();
        if (c1674xM703l != null && c1674xM703l.f525b.getYear() == this.f519a.getYear()) {
            iMo571M = c1674xM703l.f525b.m576V() - 1;
        } else {
            iMo571M = this.f519a.mo571M();
        }
        return this.f521c == 1 ? iMo571M - (this.f520b.f525b.m576V() - 1) : iMo571M;
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b, p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.ALIGNED_DAY_OF_WEEK_IN_MONTH || interfaceC1744r == EnumC1727a.ALIGNED_DAY_OF_WEEK_IN_YEAR || interfaceC1744r == EnumC1727a.ALIGNED_WEEK_OF_MONTH || interfaceC1744r == EnumC1727a.ALIGNED_WEEK_OF_YEAR) {
            return false;
        }
        if (interfaceC1744r instanceof EnumC1727a) {
            return ((EnumC1727a) interfaceC1744r).isDateBased();
        }
        return interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return interfaceC1744r.mo803j(this);
        }
        if (!mo543e(interfaceC1744r)) {
            throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
        EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
        int i = AbstractC1672v.f517a[enumC1727a.ordinal()];
        if (i == 1) {
            return C1748v.m818f(1L, this.f519a.m579Y());
        }
        if (i == 2) {
            return C1748v.m818f(1L, mo571M());
        }
        if (i != 3) {
            return C1671u.f516c.mo660q(enumC1727a);
        }
        int year = this.f520b.f525b.getYear();
        return this.f520b.m703l() != null ? C1748v.m818f(1L, (r0.f525b.getYear() - year) + 1) : C1748v.m818f(1L, 999999999 - year);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return interfaceC1744r.mo806t(this);
        }
        switch (AbstractC1672v.f517a[((EnumC1727a) interfaceC1744r).ordinal()]) {
            case 2:
                return this.f521c == 1 ? (this.f519a.m576V() - this.f520b.f525b.m576V()) + 1 : this.f519a.m576V();
            case 3:
                return this.f521c;
            case 4:
            case 5:
            case 6:
            case 7:
                throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
            case 8:
                return this.f520b.f524a;
            default:
                return this.f519a.mo542D(interfaceC1744r);
        }
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: V, reason: merged with bridge method [inline-methods] */
    public final C1673w mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
            if (mo542D(enumC1727a) == j) {
                return this;
            }
            int[] iArr = AbstractC1672v.f517a;
            int i = iArr[enumC1727a.ordinal()];
            if (i == 3 || i == 8 || i == 9) {
                C1671u c1671u = C1671u.f516c;
                int iM820a = c1671u.mo660q(enumC1727a).m820a(j, enumC1727a);
                int i2 = iArr[enumC1727a.ordinal()];
                if (i2 == 3) {
                    return m700X(this.f519a.m588k0(c1671u.mo663v(this.f520b, iM820a)));
                }
                if (i2 == 8) {
                    return m700X(this.f519a.m588k0(c1671u.mo663v(C1674x.m702m(iM820a), this.f521c)));
                }
                if (i2 == 9) {
                    return m700X(this.f519a.m588k0(iM820a));
                }
            }
            return m700X(this.f519a.mo556c(j, interfaceC1744r));
        }
        return (C1673w) super.mo556c(j, interfaceC1744r);
    }

    /* renamed from: W */
    public final C1673w m699W(C1678e c1678e) {
        return (C1673w) super.mo591x(c1678e);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        return (C1673w) super.mo591x(localDate);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: x */
    public final InterfaceC1652b mo591x(InterfaceC1741o interfaceC1741o) {
        return (C1673w) super.mo591x(interfaceC1741o);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: J */
    public final InterfaceC1652b mo570J(InterfaceC1743q interfaceC1743q) {
        return (C1673w) super.mo570J(interfaceC1743q);
    }

    @Override // p017j$.time.chrono.AbstractC1654d
    /* renamed from: T */
    public final InterfaceC1652b mo647T(long j) {
        return m700X(this.f519a.m585g0(j));
    }

    @Override // p017j$.time.chrono.AbstractC1654d
    /* renamed from: S */
    public final InterfaceC1652b mo646S(long j) {
        return m700X(this.f519a.m583e0(j));
    }

    @Override // p017j$.time.chrono.AbstractC1654d
    /* renamed from: R */
    public final InterfaceC1652b mo645R(long j) {
        return m700X(this.f519a.plusDays(j));
    }

    /* renamed from: U */
    public final C1673w m697U(long j, EnumC1728b enumC1728b) {
        return (C1673w) super.mo557d(j, (InterfaceC1746t) enumC1728b);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: d */
    public final InterfaceC1652b mo557d(long j, InterfaceC1746t interfaceC1746t) {
        return (C1673w) super.mo557d(j, interfaceC1746t);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: d */
    public final InterfaceC1739m mo557d(long j, InterfaceC1746t interfaceC1746t) {
        return (C1673w) super.mo557d(j, interfaceC1746t);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: r */
    public final InterfaceC1652b mo559y(long j, InterfaceC1746t interfaceC1746t) {
        return (C1673w) super.mo559y(j, interfaceC1746t);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return (C1673w) super.mo559y(j, enumC1728b);
    }

    /* renamed from: X */
    public final C1673w m700X(LocalDate localDate) {
        return localDate.equals(this.f519a) ? this : new C1673w(localDate);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: E */
    public final long mo567E() {
        return this.f519a.mo567E();
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C1673w) {
            return this.f519a.equals(((C1673w) obj).f519a);
        }
        return false;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C1645D((byte) 4, this);
    }
}
