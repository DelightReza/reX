package p017j$.time.format;

import org.mvel2.asm.signature.SignatureVisitor;
import p017j$.time.C1640b;
import p017j$.time.temporal.InterfaceC1744r;

/* renamed from: j$.time.format.i */
/* loaded from: classes2.dex */
public class C1695i implements InterfaceC1691e {

    /* renamed from: f */
    public static final long[] f573f = {0, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000, 10000000000L};

    /* renamed from: a */
    public final InterfaceC1744r f574a;

    /* renamed from: b */
    public final int f575b;

    /* renamed from: c */
    public final int f576c;

    /* renamed from: d */
    public final EnumC1685F f577d;

    /* renamed from: e */
    public final int f578e;

    /* renamed from: a */
    public long mo728a(C1711y c1711y, long j) {
        return j;
    }

    public C1695i(InterfaceC1744r interfaceC1744r, int i, int i2, EnumC1685F enumC1685F) {
        this.f574a = interfaceC1744r;
        this.f575b = i;
        this.f576c = i2;
        this.f577d = enumC1685F;
        this.f578e = 0;
    }

    public C1695i(InterfaceC1744r interfaceC1744r, int i, int i2, EnumC1685F enumC1685F, int i3) {
        this.f574a = interfaceC1744r;
        this.f575b = i;
        this.f576c = i2;
        this.f577d = enumC1685F;
        this.f578e = i3;
    }

    /* renamed from: d */
    public C1695i mo724d() {
        if (this.f578e == -1) {
            return this;
        }
        return new C1695i(this.f574a, this.f575b, this.f576c, this.f577d, -1);
    }

    /* renamed from: e */
    public C1695i mo725e(int i) {
        return new C1695i(this.f574a, this.f575b, this.f576c, this.f577d, this.f578e + i);
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    public boolean mo721i(C1711y c1711y, StringBuilder sb) {
        InterfaceC1744r interfaceC1744r = this.f574a;
        Long lM767a = c1711y.m767a(interfaceC1744r);
        if (lM767a == null) {
            return false;
        }
        long jMo728a = mo728a(c1711y, lM767a.longValue());
        C1682C c1682c = c1711y.f635b.f551c;
        String string = jMo728a == Long.MIN_VALUE ? "9223372036854775808" : Long.toString(Math.abs(jMo728a));
        int length = string.length();
        int i = this.f576c;
        if (length > i) {
            throw new C1640b("Field " + interfaceC1744r + " cannot be printed as the value " + jMo728a + " exceeds the maximum print width of " + i);
        }
        c1682c.getClass();
        int i2 = this.f575b;
        EnumC1685F enumC1685F = this.f577d;
        if (jMo728a >= 0) {
            int i3 = AbstractC1688b.f566a[enumC1685F.ordinal()];
            if (i3 != 1) {
                if (i3 == 2) {
                    sb.append(SignatureVisitor.EXTENDS);
                }
            } else if (i2 < 19 && jMo728a >= f573f[i2]) {
                sb.append(SignatureVisitor.EXTENDS);
            }
        } else {
            int i4 = AbstractC1688b.f566a[enumC1685F.ordinal()];
            if (i4 == 1 || i4 == 2 || i4 == 3) {
                sb.append(SignatureVisitor.SUPER);
            } else if (i4 == 4) {
                throw new C1640b("Field " + interfaceC1744r + " cannot be printed as the value " + jMo728a + " cannot be negative according to the SignStyle");
            }
        }
        for (int i5 = 0; i5 < i2 - string.length(); i5++) {
            sb.append('0');
        }
        sb.append(string);
        return true;
    }

    /* renamed from: b */
    public boolean mo723b(C1708v c1708v) {
        int i = this.f578e;
        if (i != -1) {
            return i > 0 && this.f575b == this.f576c && this.f577d == EnumC1685F.NOT_NEGATIVE;
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:0x0173, code lost:
    
        if (r6 <= r10) goto L99;
     */
    /* JADX WARN: Removed duplicated region for block: B:120:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0197  */
    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public int mo722j(p017j$.time.format.C1708v r27, java.lang.CharSequence r28, int r29) {
        /*
            Method dump skipped, instructions count: 414
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.time.format.C1695i.mo722j(j$.time.format.v, java.lang.CharSequence, int):int");
    }

    /* renamed from: c */
    public int mo729c(C1708v c1708v, long j, int i, int i2) {
        return c1708v.m765f(this.f574a, j, i, i2);
    }

    public String toString() {
        int i = this.f576c;
        InterfaceC1744r interfaceC1744r = this.f574a;
        EnumC1685F enumC1685F = this.f577d;
        int i2 = this.f575b;
        if (i2 == 1 && i == 19 && enumC1685F == EnumC1685F.NORMAL) {
            return "Value(" + interfaceC1744r + ")";
        }
        if (i2 == i && enumC1685F == EnumC1685F.NOT_NEGATIVE) {
            return "Value(" + interfaceC1744r + "," + i2 + ")";
        }
        return "Value(" + interfaceC1744r + "," + i2 + "," + i + "," + enumC1685F + ")";
    }
}
