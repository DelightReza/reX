package p017j$.time.format;

/* renamed from: j$.time.format.l */
/* loaded from: classes2.dex */
public final class C1698l extends C1699m {
    @Override // p017j$.time.format.C1699m
    /* renamed from: d */
    public final C1699m mo736d(String str, String str2, C1699m c1699m) {
        return new C1698l(str, str2, c1699m);
    }

    @Override // p017j$.time.format.C1699m
    /* renamed from: b */
    public final boolean mo735b(char c, char c2) {
        return C1708v.m760b(c, c2);
    }

    @Override // p017j$.time.format.C1699m
    /* renamed from: e */
    public final boolean mo737e(CharSequence charSequence, int i, int i2) {
        int length = this.f588a.length();
        if (length > i2 - i) {
            return false;
        }
        int i3 = 0;
        while (true) {
            int i4 = length - 1;
            if (length <= 0) {
                return true;
            }
            int i5 = i3 + 1;
            int i6 = i + 1;
            if (!C1708v.m760b(this.f588a.charAt(i3), charSequence.charAt(i))) {
                return false;
            }
            i = i6;
            length = i4;
            i3 = i5;
        }
    }
}
