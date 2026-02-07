package p017j$.time.format;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import p017j$.time.AbstractC1641c;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.util.Objects;

/* renamed from: j$.time.format.f */
/* loaded from: classes2.dex */
public final class C1692f extends C1695i {

    /* renamed from: g */
    public final boolean f570g;

    @Override // p017j$.time.format.C1695i
    /* renamed from: b */
    public final boolean mo723b(C1708v c1708v) {
        return c1708v.f627c && this.f575b == this.f576c && !this.f570g;
    }

    @Override // p017j$.time.format.C1695i, p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    public final int mo722j(C1708v c1708v, CharSequence charSequence, int i) {
        boolean z = c1708v.f627c;
        DateTimeFormatter dateTimeFormatter = c1708v.f625a;
        int i2 = (z || mo723b(c1708v)) ? this.f575b : 0;
        int i3 = (c1708v.f627c || mo723b(c1708v)) ? this.f576c : 9;
        int length = charSequence.length();
        if (i != length) {
            if (this.f570g) {
                char cCharAt = charSequence.charAt(i);
                dateTimeFormatter.f551c.getClass();
                if (cCharAt == '.') {
                    i++;
                } else if (i2 > 0) {
                    return ~i;
                }
            }
            int i4 = i;
            int i5 = i2 + i4;
            if (i5 > length) {
                return ~i4;
            }
            int iMin = Math.min(i3 + i4, length);
            int i6 = i4;
            int i7 = 0;
            while (true) {
                if (i6 >= iMin) {
                    break;
                }
                int i8 = i6 + 1;
                char cCharAt2 = charSequence.charAt(i6);
                dateTimeFormatter.f551c.getClass();
                int i9 = cCharAt2 - '0';
                if (i9 < 0 || i9 > 9) {
                    i9 = -1;
                }
                if (i9 >= 0) {
                    i7 = (i7 * 10) + i9;
                    i6 = i8;
                } else if (i8 < i5) {
                    return ~i4;
                }
            }
            BigDecimal bigDecimalMovePointLeft = new BigDecimal(i7).movePointLeft(i6 - i4);
            C1748v c1748vMo805n = this.f574a.mo805n();
            BigDecimal bigDecimalValueOf = BigDecimal.valueOf(c1748vMo805n.f697a);
            return c1708v.m765f(this.f574a, bigDecimalMovePointLeft.multiply(BigDecimal.valueOf(c1748vMo805n.f700d).subtract(bigDecimalValueOf).add(BigDecimal.ONE)).setScale(0, RoundingMode.FLOOR).add(bigDecimalValueOf).longValueExact(), i4, i6);
        }
        if (i2 > 0) {
            return ~i;
        }
        return i;
    }

    public C1692f(InterfaceC1744r interfaceC1744r, int i, int i2, boolean z) {
        this(interfaceC1744r, i, i2, z, 0);
        Objects.requireNonNull(interfaceC1744r, "field");
        C1748v c1748vMo805n = interfaceC1744r.mo805n();
        if (c1748vMo805n.f697a != c1748vMo805n.f698b || c1748vMo805n.f699c != c1748vMo805n.f700d) {
            throw new IllegalArgumentException(AbstractC1641c.m643a("Field must have a fixed set of values: ", interfaceC1744r));
        }
        if (i < 0 || i > 9) {
            throw new IllegalArgumentException("Minimum width must be from 0 to 9 inclusive but was " + i);
        }
        if (i2 < 1 || i2 > 9) {
            throw new IllegalArgumentException("Maximum width must be from 1 to 9 inclusive but was " + i2);
        }
        if (i2 >= i) {
            return;
        }
        throw new IllegalArgumentException("Maximum width must exceed or equal the minimum width but " + i2 + " < " + i);
    }

    public C1692f(InterfaceC1744r interfaceC1744r, int i, int i2, boolean z, int i3) {
        super(interfaceC1744r, i, i2, EnumC1685F.NOT_NEGATIVE, i3);
        this.f570g = z;
    }

    @Override // p017j$.time.format.C1695i
    /* renamed from: d */
    public final C1695i mo724d() {
        if (this.f578e == -1) {
            return this;
        }
        return new C1692f(this.f574a, this.f575b, this.f576c, this.f570g, -1);
    }

    @Override // p017j$.time.format.C1695i
    /* renamed from: e */
    public final C1695i mo725e(int i) {
        return new C1692f(this.f574a, this.f575b, this.f576c, this.f570g, this.f578e + i);
    }

    @Override // p017j$.time.format.C1695i, p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    public final boolean mo721i(C1711y c1711y, StringBuilder sb) {
        InterfaceC1744r interfaceC1744r = this.f574a;
        Long lM767a = c1711y.m767a(interfaceC1744r);
        if (lM767a == null) {
            return false;
        }
        C1682C c1682c = c1711y.f635b.f551c;
        long jLongValue = lM767a.longValue();
        C1748v c1748vMo805n = interfaceC1744r.mo805n();
        c1748vMo805n.m821b(jLongValue, interfaceC1744r);
        BigDecimal bigDecimalValueOf = BigDecimal.valueOf(c1748vMo805n.f697a);
        BigDecimal bigDecimalAdd = BigDecimal.valueOf(c1748vMo805n.f700d).subtract(bigDecimalValueOf).add(BigDecimal.ONE);
        BigDecimal bigDecimalSubtract = BigDecimal.valueOf(jLongValue).subtract(bigDecimalValueOf);
        RoundingMode roundingMode = RoundingMode.FLOOR;
        BigDecimal bigDecimalDivide = bigDecimalSubtract.divide(bigDecimalAdd, 9, roundingMode);
        BigDecimal bigDecimal = BigDecimal.ZERO;
        if (bigDecimalDivide.compareTo(bigDecimal) != 0) {
            bigDecimal = bigDecimalDivide.signum() == 0 ? new BigDecimal(BigInteger.ZERO, 0) : bigDecimalDivide.stripTrailingZeros();
        }
        int iScale = bigDecimal.scale();
        boolean z = this.f570g;
        int i = this.f575b;
        if (iScale != 0) {
            String strSubstring = bigDecimal.setScale(Math.min(Math.max(bigDecimal.scale(), i), this.f576c), roundingMode).toPlainString().substring(2);
            c1682c.getClass();
            if (z) {
                sb.append('.');
            }
            sb.append(strSubstring);
            return true;
        }
        if (i > 0) {
            if (z) {
                c1682c.getClass();
                sb.append('.');
            }
            for (int i2 = 0; i2 < i; i2++) {
                c1682c.getClass();
                sb.append('0');
            }
        }
        return true;
    }

    @Override // p017j$.time.format.C1695i
    public final String toString() {
        return "Fraction(" + this.f574a + "," + this.f575b + "," + this.f576c + (this.f570g ? ",DecimalPoint" : "") + ")";
    }
}
