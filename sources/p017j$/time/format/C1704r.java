package p017j$.time.format;

import java.util.Calendar;
import java.util.Locale;
import p017j$.time.DayOfWeek;
import p017j$.time.temporal.C1749w;
import p017j$.time.temporal.C1750x;
import p017j$.util.Objects;
import p017j$.util.concurrent.ConcurrentHashMap;

/* renamed from: j$.time.format.r */
/* loaded from: classes2.dex */
public final class C1704r extends C1695i {

    /* renamed from: g */
    public final char f605g;

    /* renamed from: h */
    public final int f606h;

    @Override // p017j$.time.format.C1695i, p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    public final int mo722j(C1708v c1708v, CharSequence charSequence, int i) {
        return m740f(c1708v.f625a.f550b).mo722j(c1708v, charSequence, i);
    }

    @Override // p017j$.time.format.C1695i, p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    public final boolean mo721i(C1711y c1711y, StringBuilder sb) {
        return m740f(c1711y.f635b.f550b).mo721i(c1711y, sb);
    }

    public C1704r(char c, int i, int i2, int i3, int i4) {
        super(null, i2, i3, EnumC1685F.NOT_NEGATIVE, i4);
        this.f605g = c;
        this.f606h = i;
    }

    @Override // p017j$.time.format.C1695i
    /* renamed from: d */
    public final C1695i mo724d() {
        if (this.f578e == -1) {
            return this;
        }
        return new C1704r(this.f605g, this.f606h, this.f575b, this.f576c, -1);
    }

    @Override // p017j$.time.format.C1695i
    /* renamed from: e */
    public final C1695i mo725e(int i) {
        return new C1704r(this.f605g, this.f606h, this.f575b, this.f576c, this.f578e + i);
    }

    /* renamed from: f */
    public final C1695i m740f(Locale locale) {
        C1749w c1749w;
        ConcurrentHashMap concurrentHashMap = C1750x.f710g;
        Objects.requireNonNull(locale, "locale");
        C1750x c1750xM833a = C1750x.m833a(DayOfWeek.f428a[((((int) ((r7.getFirstDayOfWeek() - 1) % 7)) + 7) + DayOfWeek.SUNDAY.ordinal()) % 7], Calendar.getInstance(new Locale(locale.getLanguage(), locale.getCountry())).getMinimalDaysInFirstWeek());
        char c = this.f605g;
        if (c == 'W') {
            c1749w = c1750xM833a.f715d;
        } else {
            if (c == 'Y') {
                C1749w c1749w2 = c1750xM833a.f717f;
                int i = this.f606h;
                if (i == 2) {
                    return new C1701o(c1749w2, 2, 2, C1701o.f598h, this.f578e);
                }
                return new C1695i(c1749w2, i, 19, i < 4 ? EnumC1685F.NORMAL : EnumC1685F.EXCEEDS_PAD, this.f578e);
            }
            if (c == 'c' || c == 'e') {
                c1749w = c1750xM833a.f714c;
            } else {
                if (c != 'w') {
                    throw new IllegalStateException("unreachable");
                }
                c1749w = c1750xM833a.f716e;
            }
        }
        return new C1695i(c1749w, this.f575b, this.f576c, EnumC1685F.NOT_NEGATIVE, this.f578e);
    }

    @Override // p017j$.time.format.C1695i
    public final String toString() {
        StringBuilder sb = new StringBuilder(30);
        sb.append("Localized(");
        int i = this.f606h;
        char c = this.f605g;
        if (c != 'Y') {
            if (c == 'W') {
                sb.append("WeekOfMonth");
            } else if (c == 'c' || c == 'e') {
                sb.append("DayOfWeek");
            } else if (c == 'w') {
                sb.append("WeekOfWeekBasedYear");
            }
            sb.append(",");
            sb.append(i);
        } else if (i == 1) {
            sb.append("WeekBasedYear");
        } else if (i == 2) {
            sb.append("ReducedValue(WeekBasedYear,2,2,2000-01-01)");
        } else {
            sb.append("WeekBasedYear,");
            sb.append(i);
            sb.append(",19,");
            sb.append(i < 4 ? EnumC1685F.NORMAL : EnumC1685F.EXCEEDS_PAD);
        }
        sb.append(")");
        return sb.toString();
    }
}
