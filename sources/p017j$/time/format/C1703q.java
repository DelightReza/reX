package p017j$.time.format;

import java.util.Iterator;
import java.util.Map;
import p017j$.time.chrono.C1668r;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.chrono.InterfaceC1662l;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1744r;

/* renamed from: j$.time.format.q */
/* loaded from: classes2.dex */
public final class C1703q implements InterfaceC1691e {

    /* renamed from: a */
    public final InterfaceC1744r f601a;

    /* renamed from: b */
    public final TextStyle f602b;

    /* renamed from: c */
    public final C1681B f603c;

    /* renamed from: d */
    public volatile C1695i f604d;

    public C1703q(InterfaceC1744r interfaceC1744r, TextStyle textStyle, C1681B c1681b) {
        this.f601a = interfaceC1744r;
        this.f602b = textStyle;
        this.f603c = c1681b;
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    public final boolean mo721i(C1711y c1711y, StringBuilder sb) {
        String strMo708c;
        Long lM767a = c1711y.m767a(this.f601a);
        DateTimeFormatter dateTimeFormatter = c1711y.f635b;
        if (lM767a == null) {
            return false;
        }
        InterfaceC1661k interfaceC1661k = (InterfaceC1661k) c1711y.f634a.mo547t(AbstractC1745s.f691b);
        if (interfaceC1661k == null || interfaceC1661k == C1668r.f512c) {
            strMo708c = this.f603c.mo708c(this.f601a, lM767a.longValue(), this.f602b, dateTimeFormatter.f550b);
        } else {
            strMo708c = this.f603c.mo707b(interfaceC1661k, this.f601a, lM767a.longValue(), this.f602b, dateTimeFormatter.f550b);
        }
        if (strMo708c != null) {
            sb.append(strMo708c);
            return true;
        }
        if (this.f604d == null) {
            this.f604d = new C1695i(this.f601a, 1, 19, EnumC1685F.NORMAL);
        }
        return this.f604d.mo721i(c1711y, sb);
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    public final int mo722j(C1708v c1708v, CharSequence charSequence, int i) {
        Iterator itMo710e;
        C1681B c1681b = this.f603c;
        InterfaceC1744r interfaceC1744r = this.f601a;
        int length = charSequence.length();
        if (i >= 0 && i <= length) {
            boolean z = c1708v.f627c;
            DateTimeFormatter dateTimeFormatter = c1708v.f625a;
            TextStyle textStyle = z ? this.f602b : null;
            InterfaceC1661k interfaceC1661k = c1708v.m762c().f542c;
            if (interfaceC1661k == null && (interfaceC1661k = c1708v.f625a.f553e) == null) {
                interfaceC1661k = C1668r.f512c;
            }
            InterfaceC1661k interfaceC1661k2 = interfaceC1661k;
            if (interfaceC1661k2 == null || interfaceC1661k2 == C1668r.f512c) {
                itMo710e = c1681b.mo710e(interfaceC1744r, textStyle, dateTimeFormatter.f550b);
            } else {
                itMo710e = c1681b.mo709d(interfaceC1661k2, interfaceC1744r, textStyle, dateTimeFormatter.f550b);
            }
            Iterator it = itMo710e;
            if (it != null) {
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    String str = (String) entry.getKey();
                    if (c1708v.m766g(str, 0, charSequence, i, str.length())) {
                        return c1708v.m765f(this.f601a, ((Long) entry.getValue()).longValue(), i, str.length() + i);
                    }
                }
                if (interfaceC1744r == EnumC1727a.ERA && !c1708v.f627c) {
                    Iterator it2 = interfaceC1661k2.mo661s().iterator();
                    while (it2.hasNext()) {
                        String string = ((InterfaceC1662l) it2.next()).toString();
                        if (c1708v.m766g(string, 0, charSequence, i, string.length())) {
                            return c1708v.m765f(this.f601a, r7.getValue(), i, string.length() + i);
                        }
                    }
                }
                if (c1708v.f627c) {
                    return ~i;
                }
            }
            if (this.f604d == null) {
                this.f604d = new C1695i(this.f601a, 1, 19, EnumC1685F.NORMAL);
            }
            return this.f604d.mo722j(c1708v, charSequence, i);
        }
        throw new IndexOutOfBoundsException();
    }

    public final String toString() {
        TextStyle textStyle = TextStyle.FULL;
        InterfaceC1744r interfaceC1744r = this.f601a;
        TextStyle textStyle2 = this.f602b;
        if (textStyle2 == textStyle) {
            return "Text(" + interfaceC1744r + ")";
        }
        return "Text(" + interfaceC1744r + "," + textStyle2 + ")";
    }
}
