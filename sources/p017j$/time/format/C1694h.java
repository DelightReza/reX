package p017j$.time.format;

import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.temporal.EnumC1727a;

/* renamed from: j$.time.format.h */
/* loaded from: classes2.dex */
public final class C1694h implements InterfaceC1691e {

    /* renamed from: a */
    public final /* synthetic */ int f571a;

    /* renamed from: b */
    public final Object f572b;

    public /* synthetic */ C1694h(int i, Object obj) {
        this.f571a = i;
        this.f572b = obj;
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    public final boolean mo721i(C1711y c1711y, StringBuilder sb) {
        switch (this.f571a) {
            case 0:
                Long lM767a = c1711y.m767a(EnumC1727a.OFFSET_SECONDS);
                if (lM767a != null) {
                    sb.append("GMT");
                    int iM493L = AbstractC1636a.m493L(lM767a.longValue());
                    if (iM493L != 0) {
                        int iAbs = Math.abs((iM493L / 3600) % 100);
                        int iAbs2 = Math.abs((iM493L / 60) % 60);
                        int iAbs3 = Math.abs(iM493L % 60);
                        sb.append(iM493L < 0 ? "-" : "+");
                        if (((TextStyle) this.f572b) == TextStyle.FULL) {
                            m726a(sb, iAbs);
                            sb.append(':');
                            m726a(sb, iAbs2);
                            if (iAbs3 != 0) {
                                sb.append(':');
                                m726a(sb, iAbs3);
                                break;
                            }
                        } else {
                            if (iAbs >= 10) {
                                sb.append((char) ((iAbs / 10) + 48));
                            }
                            sb.append((char) ((iAbs % 10) + 48));
                            if (iAbs2 != 0 || iAbs3 != 0) {
                                sb.append(':');
                                m726a(sb, iAbs2);
                                if (iAbs3 != 0) {
                                    sb.append(':');
                                    m726a(sb, iAbs3);
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            default:
                sb.append((String) this.f572b);
                break;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x0124 A[PHI: r6 r14
      0x0124: PHI (r6v3 int) = (r6v2 int), (r6v4 int), (r6v4 int), (r6v4 int), (r6v4 int), (r6v4 int), (r6v4 int) binds: [B:50:0x00ce, B:55:0x00de, B:57:0x00e4, B:58:0x00e6, B:60:0x00ec, B:62:0x00f8, B:63:0x00fa] A[DONT_GENERATE, DONT_INLINE]
      0x0124: PHI (r14v5 int) = (r14v4 int), (r14v7 int), (r14v7 int), (r14v7 int), (r14v7 int), (r14v7 int), (r14v7 int) binds: [B:50:0x00ce, B:55:0x00de, B:57:0x00e4, B:58:0x00e6, B:60:0x00ec, B:62:0x00f8, B:63:0x00fa] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final int mo722j(p017j$.time.format.C1708v r12, java.lang.CharSequence r13, int r14) {
        /*
            Method dump skipped, instructions count: 332
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.time.format.C1694h.mo722j(j$.time.format.v, java.lang.CharSequence, int):int");
    }

    public final String toString() {
        switch (this.f571a) {
            case 0:
                return "LocalizedOffset(" + ((TextStyle) this.f572b) + ")";
            default:
                return "'" + ((String) this.f572b).replace("'", "''") + "'";
        }
    }

    /* renamed from: a */
    public static void m726a(StringBuilder sb, int i) {
        sb.append((char) ((i / 10) + 48));
        sb.append((char) ((i % 10) + 48));
    }

    /* renamed from: b */
    public static int m727b(CharSequence charSequence, int i) {
        char cCharAt = charSequence.charAt(i);
        if (cCharAt < '0' || cCharAt > '9') {
            return -1;
        }
        return cCharAt - '0';
    }
}
