package p017j$.time;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.regex.Pattern;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.chrono.C1668r;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1743q;
import p017j$.util.Objects;

/* loaded from: classes2.dex */
public final class Period implements InterfaceC1743q, Serializable {

    /* renamed from: d */
    public static final Period f448d = new Period(0, 0, 0);
    private static final long serialVersionUID = -3587258372562876L;

    /* renamed from: a */
    public final int f449a;

    /* renamed from: b */
    public final int f450b;

    /* renamed from: c */
    public final int f451c;

    static {
        Pattern.compile("([-+]?)P(?:([-+]?[0-9]+)Y)?(?:([-+]?[0-9]+)M)?(?:([-+]?[0-9]+)W)?(?:([-+]?[0-9]+)D)?", 2);
        AbstractC1636a.m495N(new Object[]{EnumC1728b.YEARS, EnumC1728b.MONTHS, EnumC1728b.DAYS});
    }

    public static Period between(LocalDate localDate, LocalDate localDate2) {
        localDate.getClass();
        LocalDate localDateM561S = LocalDate.m561S(localDate2);
        long jM577W = localDateM561S.m577W() - localDate.m577W();
        int iM579Y = localDateM561S.f440c - localDate.f440c;
        if (jM577W > 0 && iM579Y < 0) {
            jM577W--;
            iM579Y = (int) (localDateM561S.mo567E() - localDate.m583e0(jM577W).mo567E());
        } else if (jM577W < 0 && iM579Y > 0) {
            jM577W++;
            iM579Y -= localDateM561S.m579Y();
        }
        return m611a(AbstractC1636a.m493L(jM577W / 12), (int) (jM577W % 12), iM579Y);
    }

    /* renamed from: a */
    public static Period m611a(int i, int i2, int i3) {
        if ((i | i2 | i3) == 0) {
            return f448d;
        }
        return new Period(i, i2, i3);
    }

    public Period(int i, int i2, int i3) {
        this.f449a = i;
        this.f450b = i2;
        this.f451c = i3;
    }

    public int getYears() {
        return this.f449a;
    }

    @Override // p017j$.time.temporal.InterfaceC1743q
    /* renamed from: i */
    public final InterfaceC1739m mo550i(InterfaceC1739m interfaceC1739m) {
        Objects.requireNonNull(interfaceC1739m, "temporal");
        InterfaceC1661k interfaceC1661k = (InterfaceC1661k) interfaceC1739m.mo547t(AbstractC1745s.f691b);
        if (interfaceC1661k == null || C1668r.f512c.equals(interfaceC1661k)) {
            int i = this.f450b;
            if (i != 0) {
                long j = (this.f449a * 12) + i;
                if (j != 0) {
                    interfaceC1739m = interfaceC1739m.mo557d(j, EnumC1728b.MONTHS);
                }
            } else {
                int i2 = this.f449a;
                if (i2 != 0) {
                    interfaceC1739m = interfaceC1739m.mo557d(i2, EnumC1728b.YEARS);
                }
            }
            int i3 = this.f451c;
            return i3 != 0 ? interfaceC1739m.mo557d(i3, EnumC1728b.DAYS) : interfaceC1739m;
        }
        throw new C1640b("Chronology mismatch, expected: ISO, actual: " + interfaceC1661k.getId());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Period) {
            Period period = (Period) obj;
            if (this.f449a == period.f449a && this.f450b == period.f450b && this.f451c == period.f451c) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Integer.rotateLeft(this.f451c, 16) + Integer.rotateLeft(this.f450b, 8) + this.f449a;
    }

    public final String toString() {
        if (this == f448d) {
            return "P0D";
        }
        StringBuilder sb = new StringBuilder("P");
        int i = this.f449a;
        if (i != 0) {
            sb.append(i);
            sb.append('Y');
        }
        int i2 = this.f450b;
        if (i2 != 0) {
            sb.append(i2);
            sb.append('M');
        }
        int i3 = this.f451c;
        if (i3 != 0) {
            sb.append(i3);
            sb.append('D');
        }
        return sb.toString();
    }

    private Object writeReplace() {
        return new C1722p((byte) 14, this);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Deserialization via serialization delegate");
    }
}
