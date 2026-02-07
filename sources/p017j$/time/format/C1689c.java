package p017j$.time.format;

/* renamed from: j$.time.format.c */
/* loaded from: classes2.dex */
public final class C1689c implements InterfaceC1691e {

    /* renamed from: a */
    public final char f567a;

    public C1689c(char c) {
        this.f567a = c;
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    public final boolean mo721i(C1711y c1711y, StringBuilder sb) {
        sb.append(this.f567a);
        return true;
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    public final int mo722j(C1708v c1708v, CharSequence charSequence, int i) {
        if (i == charSequence.length()) {
            return ~i;
        }
        char cCharAt = charSequence.charAt(i);
        char c = this.f567a;
        return (cCharAt == c || (!c1708v.f626b && (Character.toUpperCase(cCharAt) == Character.toUpperCase(c) || Character.toLowerCase(cCharAt) == Character.toLowerCase(c)))) ? i + 1 : ~i;
    }

    public final String toString() {
        char c = this.f567a;
        if (c == '\'') {
            return "''";
        }
        return "'" + c + "'";
    }
}
