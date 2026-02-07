package p017j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Arrays;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.AbstractC1641c;
import p017j$.time.C1640b;
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

/* renamed from: j$.time.chrono.p */
/* loaded from: classes2.dex */
public final class C1666p extends AbstractC1654d {
    private static final long serialVersionUID = -5207853542612002020L;

    /* renamed from: a */
    public final transient C1664n f506a;

    /* renamed from: b */
    public final transient int f507b;

    /* renamed from: c */
    public final transient int f508c;

    /* renamed from: d */
    public final transient int f509d;

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: F */
    public final ChronoLocalDateTime mo568F(C1715i c1715i) {
        return new C1656f(this, c1715i);
    }

    public C1666p(C1664n c1664n, int i, int i2, int i3) {
        c1664n.m689T(i, i2, i3);
        this.f506a = c1664n;
        this.f507b = i;
        this.f508c = i2;
        this.f509d = i3;
    }

    public C1666p(C1664n c1664n, long j) {
        int i = (int) j;
        c1664n.m686Q();
        if (i < c1664n.f498e || i >= c1664n.f499f) {
            throw new C1640b("Hijrah date out of range");
        }
        int iBinarySearch = Arrays.binarySearch(c1664n.f497d, i);
        iBinarySearch = iBinarySearch < 0 ? (-iBinarySearch) - 2 : iBinarySearch;
        int[] iArr = {c1664n.m688S(iBinarySearch), ((c1664n.f500g + iBinarySearch) % 12) + 1, (i - c1664n.f497d[iBinarySearch]) + 1};
        this.f506a = c1664n;
        this.f507b = iArr[0];
        this.f508c = iArr[1];
        this.f509d = iArr[2];
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: a */
    public final InterfaceC1661k mo581a() {
        return this.f506a;
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: G */
    public final InterfaceC1662l mo569G() {
        return EnumC1667q.f510AH;
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: M */
    public final int mo571M() {
        return this.f506a.m691W(this.f507b, 12);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return interfaceC1744r.mo803j(this);
        }
        if (!AbstractC1636a.m519o(this, interfaceC1744r)) {
            throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
        EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
        int i = AbstractC1665o.f505a[enumC1727a.ordinal()];
        return i != 1 ? i != 2 ? i != 3 ? this.f506a.mo660q(enumC1727a) : C1748v.m818f(1L, 5L) : C1748v.m818f(1L, mo571M()) : C1748v.m818f(1L, this.f506a.m690U(this.f507b, this.f508c));
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return interfaceC1744r.mo806t(this);
        }
        switch (AbstractC1665o.f505a[((EnumC1727a) interfaceC1744r).ordinal()]) {
            case 1:
                return this.f509d;
            case 2:
                return m692U();
            case 3:
                return ((this.f509d - 1) / 7) + 1;
            case 4:
                return ((int) AbstractC1636a.m498Q(mo567E() + 3, 7)) + 1;
            case 5:
                return ((this.f509d - 1) % 7) + 1;
            case 6:
                return ((m692U() - 1) % 7) + 1;
            case 7:
                return mo567E();
            case 8:
                return ((m692U() - 1) / 7) + 1;
            case 9:
                return this.f508c;
            case 10:
                return ((this.f507b * 12) + this.f508c) - 1;
            case 11:
                return this.f507b;
            case 12:
                return this.f507b;
            case 13:
                return this.f507b <= 1 ? 0 : 1;
            default:
                throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: Y, reason: merged with bridge method [inline-methods] */
    public final C1666p mo556c(long j, InterfaceC1744r interfaceC1744r) {
        if (!(interfaceC1744r instanceof EnumC1727a)) {
            return (C1666p) super.mo556c(j, interfaceC1744r);
        }
        EnumC1727a enumC1727a = (EnumC1727a) interfaceC1744r;
        this.f506a.mo660q(enumC1727a).m821b(j, enumC1727a);
        int i = (int) j;
        switch (AbstractC1665o.f505a[enumC1727a.ordinal()]) {
            case 1:
                return m695X(this.f507b, this.f508c, i);
            case 2:
                return mo645R(Math.min(i, mo571M()) - m692U());
            case 3:
                return mo645R((j - mo542D(EnumC1727a.ALIGNED_WEEK_OF_MONTH)) * 7);
            case 4:
                return mo645R(j - (((int) AbstractC1636a.m498Q(mo567E() + 3, 7)) + 1));
            case 5:
                return mo645R(j - mo542D(EnumC1727a.ALIGNED_DAY_OF_WEEK_IN_MONTH));
            case 6:
                return mo645R(j - mo542D(EnumC1727a.ALIGNED_DAY_OF_WEEK_IN_YEAR));
            case 7:
                return new C1666p(this.f506a, j);
            case 8:
                return mo645R((j - mo542D(EnumC1727a.ALIGNED_WEEK_OF_YEAR)) * 7);
            case 9:
                return m695X(this.f507b, i, this.f509d);
            case 10:
                return mo646S(j - (((this.f507b * 12) + this.f508c) - 1));
            case 11:
                if (this.f507b < 1) {
                    i = 1 - i;
                }
                return m695X(i, this.f508c, this.f509d);
            case 12:
                return m695X(i, this.f508c, this.f509d);
            case 13:
                return m695X(1 - this.f507b, this.f508c, this.f509d);
            default:
                throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
    }

    /* renamed from: X */
    public final C1666p m695X(int i, int i2, int i3) {
        int iM690U = this.f506a.m690U(i, i2);
        if (i3 > iM690U) {
            i3 = iM690U;
        }
        return new C1666p(this.f506a, i, i2, i3);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        return (C1666p) super.mo591x(localDate);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: x */
    public final InterfaceC1652b mo591x(InterfaceC1741o interfaceC1741o) {
        return (C1666p) super.mo591x(interfaceC1741o);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: J */
    public final InterfaceC1652b mo570J(InterfaceC1743q interfaceC1743q) {
        return (C1666p) super.mo570J(interfaceC1743q);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: E */
    public final long mo567E() {
        return this.f506a.m689T(this.f507b, this.f508c, this.f509d);
    }

    /* renamed from: U */
    public final int m692U() {
        return this.f506a.m691W(this.f507b, this.f508c - 1) + this.f509d;
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: p */
    public final boolean mo589p() {
        return this.f506a.mo655O(this.f507b);
    }

    @Override // p017j$.time.chrono.AbstractC1654d
    /* renamed from: T */
    public final InterfaceC1652b mo647T(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = this.f507b + ((int) j);
        int i = (int) j2;
        if (j2 == i) {
            return m695X(i, this.f508c, this.f509d);
        }
        throw new ArithmeticException();
    }

    @Override // p017j$.time.chrono.AbstractC1654d
    /* renamed from: W, reason: merged with bridge method [inline-methods] */
    public final C1666p mo646S(long j) {
        if (j == 0) {
            return this;
        }
        long j2 = (this.f507b * 12) + (this.f508c - 1) + j;
        C1664n c1664n = this.f506a;
        long jM499R = AbstractC1636a.m499R(j2, 12L);
        if (jM499R >= c1664n.m688S(0) && jM499R <= c1664n.m688S(c1664n.f497d.length - 1) - 1) {
            return m695X((int) jM499R, ((int) AbstractC1636a.m498Q(j2, 12L)) + 1, this.f509d);
        }
        throw new C1640b("Invalid Hijrah year: " + jM499R);
    }

    @Override // p017j$.time.chrono.AbstractC1654d
    /* renamed from: V, reason: merged with bridge method [inline-methods] */
    public final C1666p mo645R(long j) {
        return new C1666p(this.f506a, mo567E() + j);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: d */
    public final InterfaceC1652b mo557d(long j, InterfaceC1746t interfaceC1746t) {
        return (C1666p) super.mo557d(j, interfaceC1746t);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: d */
    public final InterfaceC1739m mo557d(long j, InterfaceC1746t interfaceC1746t) {
        return (C1666p) super.mo557d(j, interfaceC1746t);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: r */
    public final InterfaceC1652b mo559y(long j, InterfaceC1746t interfaceC1746t) {
        return (C1666p) super.mo559y(j, interfaceC1746t);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return (C1666p) super.mo559y(j, enumC1728b);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C1666p) {
            C1666p c1666p = (C1666p) obj;
            if (this.f507b == c1666p.f507b && this.f508c == c1666p.f508c && this.f509d == c1666p.f509d && this.f506a.equals(c1666p.f506a)) {
                return true;
            }
        }
        return false;
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    public final int hashCode() {
        int i = this.f507b;
        int i2 = this.f508c;
        int i3 = this.f509d;
        this.f506a.getClass();
        return (((i << 11) + (i2 << 6)) + i3) ^ ((i & (-2048)) ^ 2100100019);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C1645D((byte) 6, this);
    }
}
