package p017j$.util;

import java.util.Arrays;

/* renamed from: j$.util.q0 */
/* loaded from: classes2.dex */
public final class C1851q0 {

    /* renamed from: a */
    public final String f951a;

    /* renamed from: b */
    public final String f952b;

    /* renamed from: c */
    public final String f953c;

    /* renamed from: d */
    public String[] f954d;

    /* renamed from: e */
    public int f955e;

    /* renamed from: f */
    public int f956f;

    public C1851q0(CharSequence charSequence) {
        Objects.requireNonNull("", "The prefix must not be null");
        Objects.requireNonNull(charSequence, "The delimiter must not be null");
        Objects.requireNonNull("", "The suffix must not be null");
        this.f951a = "";
        this.f952b = charSequence.toString();
        this.f953c = "";
    }

    /* renamed from: c */
    public static int m911c(String str, char[] cArr, int i) {
        int length = str.length();
        str.getChars(0, length, cArr, i);
        return length;
    }

    public final String toString() {
        String[] strArr = this.f954d;
        int i = this.f955e;
        String str = this.f951a;
        int length = str.length();
        String str2 = this.f953c;
        int length2 = str2.length() + length;
        if (length2 == 0) {
            m913b();
            return i == 0 ? "" : strArr[0];
        }
        char[] cArr = new char[this.f956f + length2];
        int iM911c = m911c(str, cArr, 0);
        if (i > 0) {
            int iM911c2 = m911c(strArr[0], cArr, iM911c) + iM911c;
            for (int i2 = 1; i2 < i; i2++) {
                int iM911c3 = m911c(this.f952b, cArr, iM911c2) + iM911c2;
                iM911c2 = m911c(strArr[i2], cArr, iM911c3) + iM911c3;
            }
            iM911c = iM911c2;
        }
        m911c(str2, cArr, iM911c);
        return new String(cArr);
    }

    /* renamed from: a */
    public final void m912a(CharSequence charSequence) {
        String strValueOf = String.valueOf(charSequence);
        String[] strArr = this.f954d;
        if (strArr == null) {
            this.f954d = new String[8];
        } else {
            int i = this.f955e;
            if (i == strArr.length) {
                this.f954d = (String[]) Arrays.copyOf(strArr, i * 2);
            }
            this.f956f = this.f952b.length() + this.f956f;
        }
        this.f956f = strValueOf.length() + this.f956f;
        String[] strArr2 = this.f954d;
        int i2 = this.f955e;
        this.f955e = i2 + 1;
        strArr2[i2] = strValueOf;
    }

    /* renamed from: b */
    public final void m913b() {
        String[] strArr;
        if (this.f955e > 1) {
            char[] cArr = new char[this.f956f];
            int iM911c = m911c(this.f954d[0], cArr, 0);
            int i = 1;
            do {
                int iM911c2 = m911c(this.f952b, cArr, iM911c) + iM911c;
                iM911c = m911c(this.f954d[i], cArr, iM911c2) + iM911c2;
                strArr = this.f954d;
                strArr[i] = null;
                i++;
            } while (i < this.f955e);
            this.f955e = 1;
            strArr[0] = new String(cArr);
        }
    }
}
