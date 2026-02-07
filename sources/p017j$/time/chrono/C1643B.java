package p017j$.time.chrono;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.AbstractC1641c;
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
import p017j$.util.Objects;

/* renamed from: j$.time.chrono.B */
/* loaded from: classes2.dex */
public final class C1643B extends AbstractC1654d {
    private static final long serialVersionUID = 1300372329181994526L;

    /* renamed from: a */
    public final transient LocalDate f468a;

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: F */
    public final ChronoLocalDateTime mo568F(C1715i c1715i) {
        return new C1656f(this, c1715i);
    }

    public C1643B(LocalDate localDate) {
        Objects.requireNonNull(localDate, "isoDate");
        this.f468a = localDate;
    }

    @Override // p017j$.time.chrono.InterfaceC1652b
    /* renamed from: a */
    public final InterfaceC1661k mo581a() {
        return C1676z.f528c;
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    public final int hashCode() {
        C1676z.f528c.getClass();
        return this.f468a.hashCode() ^ (-1990173233);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: G */
    public final InterfaceC1662l mo569G() {
        return m648U() >= 1 ? EnumC1644C.ROC : EnumC1644C.BEFORE_ROC;
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
        int i = AbstractC1642A.f467a[enumC1727a.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            return this.f468a.mo545k(interfaceC1744r);
        }
        if (i != 4) {
            return C1676z.f528c.mo660q(enumC1727a);
        }
        C1748v c1748v = EnumC1727a.YEAR.f671b;
        return C1748v.m818f(1L, m648U() <= 0 ? (-c1748v.f697a) + 1912 : c1748v.f700d - 1911);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r instanceof EnumC1727a) {
            int i = AbstractC1642A.f467a[((EnumC1727a) interfaceC1744r).ordinal()];
            if (i == 4) {
                int iM648U = m648U();
                if (iM648U < 1) {
                    iM648U = 1 - iM648U;
                }
                return iM648U;
            }
            if (i == 5) {
                return ((m648U() * 12) + this.f468a.f439b) - 1;
            }
            if (i == 6) {
                return m648U();
            }
            if (i != 7) {
                return this.f468a.mo542D(interfaceC1744r);
            }
            return m648U() < 1 ? 0 : 1;
        }
        return interfaceC1744r.mo806t(this);
    }

    /* renamed from: U */
    public final int m648U() {
        return this.f468a.getYear() - 1911;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x004a  */
    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: V, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final p017j$.time.chrono.C1643B mo556c(long r8, p017j$.time.temporal.InterfaceC1744r r10) {
        /*
            r7 = this;
            boolean r0 = r10 instanceof p017j$.time.temporal.EnumC1727a
            if (r0 == 0) goto La0
            r0 = r10
            j$.time.temporal.a r0 = (p017j$.time.temporal.EnumC1727a) r0
            long r1 = r7.mo542D(r0)
            int r3 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r3 != 0) goto L10
            return r7
        L10:
            int[] r1 = p017j$.time.chrono.AbstractC1642A.f467a
            int r2 = r0.ordinal()
            r2 = r1[r2]
            r3 = 7
            r4 = 6
            r5 = 4
            if (r2 == r5) goto L4a
            r6 = 5
            if (r2 == r6) goto L25
            if (r2 == r4) goto L4a
            if (r2 == r3) goto L4a
            goto L60
        L25:
            j$.time.chrono.z r10 = p017j$.time.chrono.C1676z.f528c
            j$.time.temporal.v r10 = r10.mo660q(r0)
            r10.m821b(r8, r0)
            int r10 = r7.m648U()
            long r0 = (long) r10
            r2 = 12
            long r0 = r0 * r2
            j$.time.LocalDate r10 = r7.f468a
            short r2 = r10.f439b
            long r2 = (long) r2
            long r0 = r0 + r2
            r2 = 1
            long r0 = r0 - r2
            long r8 = r8 - r0
            j$.time.LocalDate r8 = r10.m583e0(r8)
            j$.time.chrono.B r8 = r7.m650W(r8)
            return r8
        L4a:
            j$.time.chrono.z r2 = p017j$.time.chrono.C1676z.f528c
            j$.time.temporal.v r2 = r2.mo660q(r0)
            int r2 = r2.m820a(r8, r0)
            int r0 = r0.ordinal()
            r0 = r1[r0]
            if (r0 == r5) goto L89
            if (r0 == r4) goto L7c
            if (r0 == r3) goto L6b
        L60:
            j$.time.LocalDate r0 = r7.f468a
            j$.time.LocalDate r8 = r0.mo556c(r8, r10)
            j$.time.chrono.B r8 = r7.m650W(r8)
            return r8
        L6b:
            j$.time.LocalDate r8 = r7.f468a
            int r9 = r7.m648U()
            int r9 = 1912 - r9
            j$.time.LocalDate r8 = r8.m588k0(r9)
            j$.time.chrono.B r8 = r7.m650W(r8)
            return r8
        L7c:
            j$.time.LocalDate r8 = r7.f468a
            int r2 = r2 + 1911
            j$.time.LocalDate r8 = r8.m588k0(r2)
            j$.time.chrono.B r8 = r7.m650W(r8)
            return r8
        L89:
            j$.time.LocalDate r8 = r7.f468a
            int r9 = r7.m648U()
            r10 = 1
            if (r9 < r10) goto L95
            int r2 = r2 + 1911
            goto L97
        L95:
            int r2 = 1912 - r2
        L97:
            j$.time.LocalDate r8 = r8.m588k0(r2)
            j$.time.chrono.B r8 = r7.m650W(r8)
            return r8
        La0:
            j$.time.chrono.b r8 = super.mo556c(r8, r10)
            j$.time.chrono.B r8 = (p017j$.time.chrono.C1643B) r8
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.time.chrono.C1643B.mo556c(long, j$.time.temporal.r):j$.time.chrono.B");
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: j */
    public final InterfaceC1739m mo591x(LocalDate localDate) {
        return (C1643B) super.mo591x(localDate);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: x */
    public final InterfaceC1652b mo591x(InterfaceC1741o interfaceC1741o) {
        return (C1643B) super.mo591x(interfaceC1741o);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: J */
    public final InterfaceC1652b mo570J(InterfaceC1743q interfaceC1743q) {
        return (C1643B) super.mo570J(interfaceC1743q);
    }

    @Override // p017j$.time.chrono.AbstractC1654d
    /* renamed from: T */
    public final InterfaceC1652b mo647T(long j) {
        return m650W(this.f468a.m585g0(j));
    }

    @Override // p017j$.time.chrono.AbstractC1654d
    /* renamed from: S */
    public final InterfaceC1652b mo646S(long j) {
        return m650W(this.f468a.m583e0(j));
    }

    @Override // p017j$.time.chrono.AbstractC1654d
    /* renamed from: R */
    public final InterfaceC1652b mo645R(long j) {
        return m650W(this.f468a.plusDays(j));
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: d */
    public final InterfaceC1652b mo557d(long j, InterfaceC1746t interfaceC1746t) {
        return (C1643B) super.mo557d(j, interfaceC1746t);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: d */
    public final InterfaceC1739m mo557d(long j, InterfaceC1746t interfaceC1746t) {
        return (C1643B) super.mo557d(j, interfaceC1746t);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: r */
    public final InterfaceC1652b mo559y(long j, InterfaceC1746t interfaceC1746t) {
        return (C1643B) super.mo559y(j, interfaceC1746t);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.temporal.InterfaceC1739m
    /* renamed from: y */
    public final InterfaceC1739m mo559y(long j, EnumC1728b enumC1728b) {
        return (C1643B) super.mo559y(j, enumC1728b);
    }

    /* renamed from: W */
    public final C1643B m650W(LocalDate localDate) {
        return localDate.equals(this.f468a) ? this : new C1643B(localDate);
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    /* renamed from: E */
    public final long mo567E() {
        return this.f468a.mo567E();
    }

    @Override // p017j$.time.chrono.AbstractC1654d, p017j$.time.chrono.InterfaceC1652b
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C1643B) {
            return this.f468a.equals(((C1643B) obj).f468a);
        }
        return false;
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }

    private Object writeReplace() {
        return new C1645D((byte) 7, this);
    }
}
