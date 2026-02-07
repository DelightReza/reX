package p017j$.time.format;

import org.mvel2.MVEL;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1640b;
import p017j$.time.temporal.EnumC1727a;
import p017j$.util.Objects;

/* renamed from: j$.time.format.j */
/* loaded from: classes2.dex */
public final class C1696j implements InterfaceC1691e {

    /* renamed from: d */
    public static final String[] f579d = {"+HH", "+HHmm", "+HH:mm", "+HHMM", "+HH:MM", "+HHMMss", "+HH:MM:ss", "+HHMMSS", "+HH:MM:SS", "+HHmmss", "+HH:mm:ss", "+H", "+Hmm", "+H:mm", "+HMM", "+H:MM", "+HMMss", "+H:MM:ss", "+HMMSS", "+H:MM:SS", "+Hmmss", "+H:mm:ss"};

    /* renamed from: e */
    public static final C1696j f580e = new C1696j("+HH:MM:ss", "Z");

    /* renamed from: f */
    public static final C1696j f581f = new C1696j("+HH:MM:ss", MVEL.VERSION_SUB);

    /* renamed from: a */
    public final String f582a;

    /* renamed from: b */
    public final int f583b;

    /* renamed from: c */
    public final int f584c;

    public C1696j(String str, String str2) {
        Objects.requireNonNull(str, "pattern");
        Objects.requireNonNull(str2, "noOffsetText");
        for (int i = 0; i < 22; i++) {
            if (f579d[i].equals(str)) {
                this.f583b = i;
                this.f584c = i % 11;
                this.f582a = str2;
                return;
            }
        }
        throw new IllegalArgumentException("Invalid zone offset pattern: " + str);
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    public final boolean mo721i(C1711y c1711y, StringBuilder sb) {
        Long lM767a = c1711y.m767a(EnumC1727a.OFFSET_SECONDS);
        boolean z = false;
        if (lM767a == null) {
            return false;
        }
        int iM493L = AbstractC1636a.m493L(lM767a.longValue());
        String str = this.f582a;
        if (iM493L == 0) {
            sb.append(str);
            return true;
        }
        int iAbs = Math.abs((iM493L / 3600) % 100);
        int iAbs2 = Math.abs((iM493L / 60) % 60);
        int iAbs3 = Math.abs(iM493L % 60);
        int length = sb.length();
        sb.append(iM493L < 0 ? "-" : "+");
        if (this.f583b < 11 || iAbs >= 10) {
            m730a(false, iAbs, sb);
        } else {
            sb.append((char) (iAbs + 48));
        }
        int i = this.f584c;
        if ((i >= 3 && i <= 8) || ((i >= 9 && iAbs3 > 0) || (i >= 1 && iAbs2 > 0))) {
            m730a(i > 0 && i % 2 == 0, iAbs2, sb);
            iAbs += iAbs2;
            if (i == 7 || i == 8 || (i >= 5 && iAbs3 > 0)) {
                if (i > 0 && i % 2 == 0) {
                    z = true;
                }
                m730a(z, iAbs3, sb);
                iAbs += iAbs3;
            }
        }
        if (iAbs == 0) {
            sb.setLength(length);
            sb.append(str);
        }
        return true;
    }

    /* renamed from: a */
    public static void m730a(boolean z, int i, StringBuilder sb) {
        sb.append(z ? ":" : "");
        sb.append((char) ((i / 10) + 48));
        sb.append((char) ((i % 10) + 48));
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    public final int mo722j(C1708v c1708v, CharSequence charSequence, int i) {
        CharSequence charSequence2;
        int i2;
        int i3;
        int i4;
        int i5;
        int length = charSequence.length();
        int length2 = this.f582a.length();
        if (length2 == 0) {
            if (i == length) {
                return c1708v.m765f(EnumC1727a.OFFSET_SECONDS, 0L, i, i);
            }
            charSequence2 = charSequence;
        } else {
            if (i == length) {
                return ~i;
            }
            charSequence2 = charSequence;
            if (c1708v.m766g(charSequence2, i, this.f582a, 0, length2)) {
                return c1708v.m765f(EnumC1727a.OFFSET_SECONDS, 0L, i, i + length2);
            }
        }
        char cCharAt = charSequence.charAt(i);
        if (cCharAt == '+' || cCharAt == '-') {
            int i6 = cCharAt == '-' ? -1 : 1;
            int i7 = this.f584c;
            boolean z = i7 > 0 && i7 % 2 == 0;
            int i8 = this.f583b;
            boolean z2 = i8 < 11;
            int[] iArr = new int[4];
            iArr[0] = i + 1;
            if (!c1708v.f627c) {
                if (z2) {
                    if (z || (i8 == 0 && length > (i5 = i + 3) && charSequence2.charAt(i5) == ':')) {
                        i8 = 10;
                        z = true;
                    } else {
                        i8 = 9;
                    }
                } else if (z || (i8 == 11 && length > (i4 = i + 3) && (charSequence2.charAt(i + 2) == ':' || charSequence2.charAt(i4) == ':'))) {
                    i8 = 21;
                    z = true;
                } else {
                    i8 = 20;
                }
            }
            switch (i8) {
                case 0:
                case 11:
                    m732c(charSequence2, z2, iArr);
                    break;
                case 1:
                case 2:
                case 13:
                    m732c(charSequence2, z2, iArr);
                    m733d(charSequence2, z, false, iArr);
                    break;
                case 3:
                case 4:
                case 15:
                    m732c(charSequence2, z2, iArr);
                    m733d(charSequence2, z, true, iArr);
                    break;
                case 5:
                case 6:
                case 17:
                    m732c(charSequence2, z2, iArr);
                    m733d(charSequence2, z, true, iArr);
                    m731b(charSequence2, z, 3, iArr);
                    break;
                case 7:
                case 8:
                case 19:
                    m732c(charSequence2, z2, iArr);
                    m733d(charSequence2, z, true, iArr);
                    if (!m731b(charSequence2, z, 3, iArr)) {
                        iArr[0] = ~iArr[0];
                        break;
                    }
                    break;
                case 9:
                case 10:
                case 21:
                    m732c(charSequence2, z2, iArr);
                    if (m731b(charSequence2, z, 2, iArr)) {
                        m731b(charSequence2, z, 3, iArr);
                        break;
                    }
                    break;
                case 12:
                    m734e(charSequence2, 1, 4, iArr);
                    break;
                case 14:
                    m734e(charSequence2, 3, 4, iArr);
                    break;
                case 16:
                    m734e(charSequence2, 3, 6, iArr);
                    break;
                case 18:
                    m734e(charSequence2, 5, 6, iArr);
                    break;
                case 20:
                    m734e(charSequence2, 1, 6, iArr);
                    break;
            }
            int i9 = iArr[0];
            if (i9 > 0) {
                int i10 = iArr[1];
                if (i10 > 23 || (i2 = iArr[2]) > 59 || (i3 = iArr[3]) > 59) {
                    throw new C1640b("Value out of range: Hour[0-23], Minute[0-59], Second[0-59]");
                }
                return c1708v.m765f(EnumC1727a.OFFSET_SECONDS, ((i2 * 60) + (i10 * 3600) + i3) * i6, i, i9);
            }
        }
        return length2 == 0 ? c1708v.m765f(EnumC1727a.OFFSET_SECONDS, 0L, i, i) : ~i;
    }

    /* renamed from: c */
    public static void m732c(CharSequence charSequence, boolean z, int[] iArr) {
        if (z) {
            if (m731b(charSequence, false, 1, iArr)) {
                return;
            }
            iArr[0] = ~iArr[0];
            return;
        }
        m734e(charSequence, 1, 2, iArr);
    }

    /* renamed from: d */
    public static void m733d(CharSequence charSequence, boolean z, boolean z2, int[] iArr) {
        if (m731b(charSequence, z, 2, iArr) || !z2) {
            return;
        }
        iArr[0] = ~iArr[0];
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0026  */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean m731b(java.lang.CharSequence r5, boolean r6, int r7, int[] r8) {
        /*
            r0 = 0
            r1 = r8[r0]
            r2 = 1
            if (r1 >= 0) goto L7
            return r2
        L7:
            if (r6 == 0) goto L1d
            if (r7 == r2) goto L1d
            int r6 = r1 + 1
            int r3 = r5.length()
            if (r6 > r3) goto L4e
            char r1 = r5.charAt(r1)
            r3 = 58
            if (r1 == r3) goto L1c
            goto L4e
        L1c:
            r1 = r6
        L1d:
            int r6 = r1 + 2
            int r3 = r5.length()
            if (r6 <= r3) goto L26
            goto L4e
        L26:
            int r3 = r1 + 1
            char r1 = r5.charAt(r1)
            char r5 = r5.charAt(r3)
            r3 = 48
            if (r1 < r3) goto L4e
            r4 = 57
            if (r1 > r4) goto L4e
            if (r5 < r3) goto L4e
            if (r5 <= r4) goto L3d
            goto L4e
        L3d:
            int r1 = r1 - r3
            int r1 = r1 * 10
            int r5 = r5 - r3
            int r5 = r5 + r1
            if (r5 < 0) goto L4e
            r1 = 59
            if (r5 <= r1) goto L49
            goto L4e
        L49:
            r8[r7] = r5
            r8[r0] = r6
            return r2
        L4e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.time.format.C1696j.m731b(java.lang.CharSequence, boolean, int, int[]):boolean");
    }

    /* renamed from: e */
    public static void m734e(CharSequence charSequence, int i, int i2, int[] iArr) {
        int i3;
        char cCharAt;
        int i4 = iArr[0];
        char[] cArr = new char[i2];
        int i5 = 0;
        int i6 = 0;
        while (i5 < i2 && (i3 = i4 + 1) <= charSequence.length() && (cCharAt = charSequence.charAt(i4)) >= '0' && cCharAt <= '9') {
            cArr[i5] = cCharAt;
            i6++;
            i5++;
            i4 = i3;
        }
        if (i6 < i) {
            iArr[0] = ~iArr[0];
            return;
        }
        switch (i6) {
            case 1:
                iArr[1] = cArr[0] - '0';
                break;
            case 2:
                iArr[1] = (cArr[1] - '0') + ((cArr[0] - '0') * 10);
                break;
            case 3:
                iArr[1] = cArr[0] - '0';
                iArr[2] = (cArr[2] - '0') + ((cArr[1] - '0') * 10);
                break;
            case 4:
                iArr[1] = (cArr[1] - '0') + ((cArr[0] - '0') * 10);
                iArr[2] = (cArr[3] - '0') + ((cArr[2] - '0') * 10);
                break;
            case 5:
                iArr[1] = cArr[0] - '0';
                iArr[2] = (cArr[2] - '0') + ((cArr[1] - '0') * 10);
                iArr[3] = (cArr[4] - '0') + ((cArr[3] - '0') * 10);
                break;
            case 6:
                iArr[1] = (cArr[1] - '0') + ((cArr[0] - '0') * 10);
                iArr[2] = (cArr[3] - '0') + ((cArr[2] - '0') * 10);
                iArr[3] = (cArr[5] - '0') + ((cArr[4] - '0') * 10);
                break;
        }
        iArr[0] = i4;
    }

    public final String toString() {
        String strReplace = this.f582a.replace("'", "''");
        return "Offset(" + f579d[this.f583b] + ",'" + strReplace + "')";
    }
}
