package p017j$.time.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import p017j$.time.C1678e;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.temporal.AbstractC1736j;
import p017j$.time.temporal.AbstractC1738l;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1734h;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.util.Objects;

/* renamed from: j$.time.format.u */
/* loaded from: classes2.dex */
public final class C1707u {

    /* renamed from: h */
    public static final C1678e f616h = new C1678e(1);

    /* renamed from: i */
    public static final Map f617i;

    /* renamed from: a */
    public C1707u f618a;

    /* renamed from: b */
    public final C1707u f619b;

    /* renamed from: c */
    public final List f620c;

    /* renamed from: d */
    public final boolean f621d;

    /* renamed from: e */
    public int f622e;

    /* renamed from: f */
    public char f623f;

    /* renamed from: g */
    public int f624g;

    static {
        HashMap map = new HashMap();
        f617i = map;
        map.put('G', EnumC1727a.ERA);
        map.put('y', EnumC1727a.YEAR_OF_ERA);
        map.put('u', EnumC1727a.YEAR);
        EnumC1734h enumC1734h = AbstractC1736j.f679a;
        map.put('Q', enumC1734h);
        map.put('q', enumC1734h);
        EnumC1727a enumC1727a = EnumC1727a.MONTH_OF_YEAR;
        map.put('M', enumC1727a);
        map.put('L', enumC1727a);
        map.put('D', EnumC1727a.DAY_OF_YEAR);
        map.put('d', EnumC1727a.DAY_OF_MONTH);
        map.put('F', EnumC1727a.ALIGNED_DAY_OF_WEEK_IN_MONTH);
        EnumC1727a enumC1727a2 = EnumC1727a.DAY_OF_WEEK;
        map.put('E', enumC1727a2);
        map.put('c', enumC1727a2);
        map.put('e', enumC1727a2);
        map.put('a', EnumC1727a.AMPM_OF_DAY);
        map.put('H', EnumC1727a.HOUR_OF_DAY);
        map.put('k', EnumC1727a.CLOCK_HOUR_OF_DAY);
        map.put('K', EnumC1727a.HOUR_OF_AMPM);
        map.put('h', EnumC1727a.CLOCK_HOUR_OF_AMPM);
        map.put('m', EnumC1727a.MINUTE_OF_HOUR);
        map.put('s', EnumC1727a.SECOND_OF_MINUTE);
        EnumC1727a enumC1727a3 = EnumC1727a.NANO_OF_SECOND;
        map.put('S', enumC1727a3);
        map.put('A', EnumC1727a.MILLI_OF_DAY);
        map.put('n', enumC1727a3);
        map.put('N', EnumC1727a.NANO_OF_DAY);
        map.put('g', AbstractC1738l.f687a);
    }

    public C1707u() {
        this.f618a = this;
        this.f620c = new ArrayList();
        this.f624g = -1;
        this.f619b = null;
        this.f621d = false;
    }

    public C1707u(C1707u c1707u) {
        this.f618a = this;
        this.f620c = new ArrayList();
        this.f624g = -1;
        this.f619b = c1707u;
        this.f621d = true;
    }

    /* renamed from: k */
    public final void m753k(InterfaceC1744r interfaceC1744r) {
        Objects.requireNonNull(interfaceC1744r, "field");
        m752j(new C1695i(interfaceC1744r, 1, 19, EnumC1685F.NORMAL));
    }

    /* renamed from: l */
    public final void m754l(InterfaceC1744r interfaceC1744r, int i) {
        Objects.requireNonNull(interfaceC1744r, "field");
        if (i < 1 || i > 19) {
            throw new IllegalArgumentException("The width must be from 1 to 19 inclusive but was " + i);
        }
        m752j(new C1695i(interfaceC1744r, i, i, EnumC1685F.NOT_NEGATIVE));
    }

    /* renamed from: m */
    public final void m755m(InterfaceC1744r interfaceC1744r, int i, int i2, EnumC1685F enumC1685F) {
        if (i == i2 && enumC1685F == EnumC1685F.NOT_NEGATIVE) {
            m754l(interfaceC1744r, i2);
            return;
        }
        Objects.requireNonNull(interfaceC1744r, "field");
        Objects.requireNonNull(enumC1685F, "signStyle");
        if (i < 1 || i > 19) {
            throw new IllegalArgumentException("The minimum width must be from 1 to 19 inclusive but was " + i);
        }
        if (i2 < 1 || i2 > 19) {
            throw new IllegalArgumentException("The maximum width must be from 1 to 19 inclusive but was " + i2);
        }
        if (i2 < i) {
            throw new IllegalArgumentException("The maximum width must exceed or equal the minimum width but " + i2 + " < " + i);
        }
        m752j(new C1695i(interfaceC1744r, i, i2, enumC1685F));
    }

    /* renamed from: j */
    public final void m752j(C1695i c1695i) {
        C1695i c1695iMo724d;
        C1707u c1707u = this.f618a;
        int i = c1707u.f624g;
        if (i < 0) {
            c1707u.f624g = m745c(c1695i);
            return;
        }
        C1695i c1695i2 = (C1695i) ((ArrayList) c1707u.f620c).get(i);
        int i2 = c1695i.f575b;
        int i3 = c1695i.f576c;
        if (i2 == i3 && c1695i.f577d == EnumC1685F.NOT_NEGATIVE) {
            c1695iMo724d = c1695i2.mo725e(i3);
            m745c(c1695i.mo724d());
            this.f618a.f624g = i;
        } else {
            c1695iMo724d = c1695i2.mo724d();
            this.f618a.f624g = m745c(c1695i);
        }
        ((ArrayList) this.f618a.f620c).set(i, c1695iMo724d);
    }

    /* renamed from: b */
    public final void m744b(EnumC1727a enumC1727a, int i, int i2, boolean z) {
        if (i == i2 && !z) {
            m752j(new C1692f(enumC1727a, i, i2, z));
        } else {
            m745c(new C1692f(enumC1727a, i, i2, z));
        }
    }

    /* renamed from: i */
    public final void m751i(InterfaceC1744r interfaceC1744r, TextStyle textStyle) {
        Objects.requireNonNull(interfaceC1744r, "field");
        Objects.requireNonNull(textStyle, "textStyle");
        m745c(new C1703q(interfaceC1744r, textStyle, C1681B.f538c));
    }

    /* renamed from: h */
    public final void m750h(EnumC1727a enumC1727a, Map map) {
        Objects.requireNonNull(enumC1727a, "field");
        Objects.requireNonNull(map, "textLookup");
        LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        TextStyle textStyle = TextStyle.FULL;
        m745c(new C1703q(enumC1727a, textStyle, new C1687a(new C1680A(Collections.singletonMap(textStyle, linkedHashMap)))));
    }

    /* renamed from: g */
    public final void m749g(String str, String str2) {
        m745c(new C1696j(str, str2));
    }

    /* renamed from: f */
    public final void m748f(TextStyle textStyle) {
        Objects.requireNonNull(textStyle, "style");
        if (textStyle != TextStyle.FULL && textStyle != TextStyle.SHORT) {
            throw new IllegalArgumentException("Style must be either full or short");
        }
        m745c(new C1694h(0, textStyle));
    }

    /* renamed from: d */
    public final void m746d(char c) {
        m745c(new C1689c(c));
    }

    /* renamed from: e */
    public final void m747e(String str) {
        Objects.requireNonNull(str, "literal");
        if (str.isEmpty()) {
            return;
        }
        if (str.length() == 1) {
            m745c(new C1689c(str.charAt(0)));
        } else {
            m745c(new C1694h(1, str));
        }
    }

    /* renamed from: a */
    public final void m743a(DateTimeFormatter dateTimeFormatter) {
        Objects.requireNonNull(dateTimeFormatter, "formatter");
        C1690d c1690d = dateTimeFormatter.f549a;
        if (c1690d.f569b) {
            c1690d = new C1690d(c1690d.f568a, false);
        }
        m745c(c1690d);
    }

    /* renamed from: o */
    public final void m757o() {
        C1707u c1707u = this.f618a;
        c1707u.f624g = -1;
        this.f618a = new C1707u(c1707u);
    }

    /* renamed from: n */
    public final void m756n() {
        C1707u c1707u = this.f618a;
        if (c1707u.f619b == null) {
            throw new IllegalStateException("Cannot call optionalEnd() as there was no previous call to optionalStart()");
        }
        if (((ArrayList) c1707u.f620c).size() > 0) {
            C1707u c1707u2 = this.f618a;
            C1690d c1690d = new C1690d(c1707u2.f620c, c1707u2.f621d);
            this.f618a = this.f618a.f619b;
            m745c(c1690d);
            return;
        }
        this.f618a = this.f618a.f619b;
    }

    /* renamed from: c */
    public final int m745c(InterfaceC1691e interfaceC1691e) {
        Objects.requireNonNull(interfaceC1691e, "pp");
        C1707u c1707u = this.f618a;
        int i = c1707u.f622e;
        if (i > 0) {
            if (interfaceC1691e != null) {
                interfaceC1691e = new C1697k(interfaceC1691e, i, c1707u.f623f);
            }
            c1707u.f622e = 0;
            c1707u.f623f = (char) 0;
        }
        ((ArrayList) c1707u.f620c).add(interfaceC1691e);
        this.f618a.f624g = -1;
        return ((ArrayList) r5.f620c).size() - 1;
    }

    /* renamed from: p */
    public final DateTimeFormatter m758p(EnumC1684E enumC1684E, InterfaceC1661k interfaceC1661k) {
        return m759q(Locale.getDefault(), enumC1684E, interfaceC1661k);
    }

    /* renamed from: q */
    public final DateTimeFormatter m759q(Locale locale, EnumC1684E enumC1684E, InterfaceC1661k interfaceC1661k) {
        Objects.requireNonNull(locale, "locale");
        while (this.f618a.f619b != null) {
            m756n();
        }
        C1690d c1690d = new C1690d(this.f620c, false);
        C1682C c1682c = C1682C.f539a;
        return new DateTimeFormatter(c1690d, locale, enumC1684E, interfaceC1661k);
    }
}
