package p017j$.time.format;

import java.text.ParsePosition;

/* renamed from: j$.time.format.m */
/* loaded from: classes2.dex */
public class C1699m {

    /* renamed from: a */
    public String f588a;

    /* renamed from: b */
    public String f589b;

    /* renamed from: c */
    public final char f590c;

    /* renamed from: d */
    public C1699m f591d;

    /* renamed from: e */
    public C1699m f592e;

    /* renamed from: b */
    public boolean mo735b(char c, char c2) {
        return c == c2;
    }

    public C1699m(String str, String str2, C1699m c1699m) {
        this.f588a = str;
        this.f589b = str2;
        this.f591d = c1699m;
        if (str.isEmpty()) {
            this.f590c = (char) 65535;
        } else {
            this.f590c = this.f588a.charAt(0);
        }
    }

    /* renamed from: c */
    public final String m739c(CharSequence charSequence, ParsePosition parsePosition) {
        int index = parsePosition.getIndex();
        int length = charSequence.length();
        if (!mo737e(charSequence, index, length)) {
            return null;
        }
        int length2 = this.f588a.length() + index;
        C1699m c1699m = this.f591d;
        if (c1699m != null && length2 != length) {
            while (true) {
                if (mo735b(c1699m.f590c, charSequence.charAt(length2))) {
                    parsePosition.setIndex(length2);
                    String strM739c = c1699m.m739c(charSequence, parsePosition);
                    if (strM739c != null) {
                        return strM739c;
                    }
                } else {
                    c1699m = c1699m.f592e;
                    if (c1699m == null) {
                        break;
                    }
                }
            }
        }
        parsePosition.setIndex(length2);
        return this.f589b;
    }

    /* renamed from: d */
    public C1699m mo736d(String str, String str2, C1699m c1699m) {
        return new C1699m(str, str2, c1699m);
    }

    /* renamed from: e */
    public boolean mo737e(CharSequence charSequence, int i, int i2) {
        if (charSequence instanceof String) {
            return ((String) charSequence).startsWith(this.f588a, i);
        }
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
            if (!mo735b(this.f588a.charAt(i3), charSequence.charAt(i))) {
                return false;
            }
            i = i6;
            length = i4;
            i3 = i5;
        }
    }

    /* renamed from: a */
    public final boolean m738a(String str, String str2) {
        int i = 0;
        while (i < str.length() && i < this.f588a.length() && mo735b(str.charAt(i), this.f588a.charAt(i))) {
            i++;
        }
        if (i == this.f588a.length()) {
            if (i < str.length()) {
                String strSubstring = str.substring(i);
                for (C1699m c1699m = this.f591d; c1699m != null; c1699m = c1699m.f592e) {
                    if (mo735b(c1699m.f590c, strSubstring.charAt(0))) {
                        return c1699m.m738a(strSubstring, str2);
                    }
                }
                C1699m c1699mMo736d = mo736d(strSubstring, str2, null);
                c1699mMo736d.f592e = this.f591d;
                this.f591d = c1699mMo736d;
                return true;
            }
            this.f589b = str2;
            return true;
        }
        C1699m c1699mMo736d2 = mo736d(this.f588a.substring(i), this.f589b, this.f591d);
        this.f588a = str.substring(0, i);
        this.f591d = c1699mMo736d2;
        if (i < str.length()) {
            this.f591d.f592e = mo736d(str.substring(i), str2, null);
            this.f589b = null;
            return true;
        }
        this.f589b = str2;
        return true;
    }
}
