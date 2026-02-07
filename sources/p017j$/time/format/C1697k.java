package p017j$.time.format;

import p017j$.time.C1640b;

/* renamed from: j$.time.format.k */
/* loaded from: classes2.dex */
public final class C1697k implements InterfaceC1691e {

    /* renamed from: a */
    public final InterfaceC1691e f585a;

    /* renamed from: b */
    public final int f586b;

    /* renamed from: c */
    public final char f587c;

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    public final int mo722j(C1708v c1708v, CharSequence charSequence, int i) {
        boolean z = c1708v.f627c;
        if (i > charSequence.length()) {
            throw new IndexOutOfBoundsException();
        }
        if (i == charSequence.length()) {
            return ~i;
        }
        int length = this.f586b + i;
        if (length > charSequence.length()) {
            if (z) {
                return ~i;
            }
            length = charSequence.length();
        }
        int i2 = i;
        while (i2 < length && c1708v.m761a(charSequence.charAt(i2), this.f587c)) {
            i2++;
        }
        int iMo722j = this.f585a.mo722j(c1708v, charSequence.subSequence(0, length), i2);
        return (iMo722j == length || !z) ? iMo722j : ~(i + i2);
    }

    public C1697k(InterfaceC1691e interfaceC1691e, int i, char c) {
        this.f585a = interfaceC1691e;
        this.f586b = i;
        this.f587c = c;
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    public final boolean mo721i(C1711y c1711y, StringBuilder sb) {
        int length = sb.length();
        if (!this.f585a.mo721i(c1711y, sb)) {
            return false;
        }
        int length2 = sb.length() - length;
        int i = this.f586b;
        if (length2 <= i) {
            for (int i2 = 0; i2 < i - length2; i2++) {
                sb.insert(length, this.f587c);
            }
            return true;
        }
        throw new C1640b("Cannot print as output of " + length2 + " characters exceeds pad width of " + i);
    }

    public final String toString() {
        String str;
        char c = this.f587c;
        if (c == ' ') {
            str = ")";
        } else {
            str = ",'" + c + "')";
        }
        return "Pad(" + this.f585a + "," + this.f586b + str;
    }
}
