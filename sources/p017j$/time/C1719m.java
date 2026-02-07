package p017j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Locale;
import org.mvel2.MVEL;
import org.mvel2.asm.signature.SignatureVisitor;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.chrono.C1668r;
import p017j$.time.format.C1707u;
import p017j$.time.format.EnumC1684E;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1744r;

/* renamed from: j$.time.m */
/* loaded from: classes2.dex */
public final class C1719m implements InterfaceC1740n, InterfaceC1741o, Comparable, Serializable {

    /* renamed from: c */
    public static final /* synthetic */ int f652c = 0;
    private static final long serialVersionUID = -939150713474957432L;

    /* renamed from: a */
    public final int f653a;

    /* renamed from: b */
    public final int f654b;

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        C1719m c1719m = (C1719m) obj;
        int i = this.f653a - c1719m.f653a;
        return i == 0 ? this.f654b - c1719m.f654b : i;
    }

    static {
        C1707u c1707u = new C1707u();
        c1707u.m747e("--");
        c1707u.m754l(EnumC1727a.MONTH_OF_YEAR, 2);
        c1707u.m746d(SignatureVisitor.SUPER);
        c1707u.m754l(EnumC1727a.DAY_OF_MONTH, 2);
        c1707u.m759q(Locale.getDefault(), EnumC1684E.SMART, null);
    }

    public C1719m(int i, int i2) {
        this.f653a = i;
        this.f654b = i2;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return interfaceC1744r instanceof EnumC1727a ? interfaceC1744r == EnumC1727a.MONTH_OF_YEAR || interfaceC1744r == EnumC1727a.DAY_OF_MONTH : interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.MONTH_OF_YEAR) {
            return interfaceC1744r.mo805n();
        }
        if (interfaceC1744r != EnumC1727a.DAY_OF_MONTH) {
            return AbstractC1745s.m816d(this, interfaceC1744r);
        }
        EnumC1717k enumC1717kM786T = EnumC1717k.m786T(this.f653a);
        enumC1717kM786T.getClass();
        int i = AbstractC1716j.f648a[enumC1717kM786T.ordinal()];
        return C1748v.m819g(1L, i != 1 ? (i == 2 || i == 3 || i == 4 || i == 5) ? 30 : 31 : 28, EnumC1717k.m786T(this.f653a).m789S());
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
        int i2 = AbstractC1718l.f651a[((EnumC1727a) interfaceC1744r).ordinal()];
        if (i2 == 1) {
            i = this.f654b;
        } else {
            if (i2 != 2) {
                throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
            }
            i = this.f653a;
        }
        return i;
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f691b) {
            return C1668r.f512c;
        }
        return AbstractC1745s.m815c(this, c1678e);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        if (!AbstractC1636a.m492K(interfaceC1739m).equals(C1668r.f512c)) {
            throw new C1640b("Adjustment only supported on ISO date-time");
        }
        InterfaceC1739m interfaceC1739mMo556c = interfaceC1739m.mo556c(this.f653a, EnumC1727a.MONTH_OF_YEAR);
        EnumC1727a enumC1727a = EnumC1727a.DAY_OF_MONTH;
        return interfaceC1739mMo556c.mo556c(Math.min(interfaceC1739mMo556c.mo545k(enumC1727a).f700d, this.f654b), enumC1727a);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof C1719m) {
            C1719m c1719m = (C1719m) obj;
            if (this.f653a == c1719m.f653a && this.f654b == c1719m.f654b) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return (this.f653a << 6) + this.f654b;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(10);
        sb.append("--");
        sb.append(this.f653a < 10 ? MVEL.VERSION_SUB : "");
        sb.append(this.f653a);
        sb.append(this.f654b < 10 ? "-0" : "-");
        sb.append(this.f654b);
        return sb.toString();
    }

    private Object writeReplace() {
        return new C1722p((byte) 13, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
