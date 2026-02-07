package p017j$.time;

import java.util.Locale;
import p017j$.time.format.C1707u;
import p017j$.time.format.EnumC1684E;
import p017j$.time.format.TextStyle;
import p017j$.time.temporal.AbstractC1745s;
import p017j$.time.temporal.C1747u;
import p017j$.time.temporal.C1748v;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;
import p017j$.time.temporal.InterfaceC1739m;
import p017j$.time.temporal.InterfaceC1740n;
import p017j$.time.temporal.InterfaceC1741o;
import p017j$.time.temporal.InterfaceC1744r;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class DayOfWeek implements InterfaceC1740n, InterfaceC1741o {
    public static final DayOfWeek FRIDAY;
    public static final DayOfWeek MONDAY;
    public static final DayOfWeek SATURDAY;
    public static final DayOfWeek SUNDAY;
    public static final DayOfWeek THURSDAY;
    public static final DayOfWeek TUESDAY;
    public static final DayOfWeek WEDNESDAY;

    /* renamed from: a */
    public static final DayOfWeek[] f428a;

    /* renamed from: b */
    public static final /* synthetic */ DayOfWeek[] f429b;

    public static DayOfWeek valueOf(String str) {
        return (DayOfWeek) Enum.valueOf(DayOfWeek.class, str);
    }

    public static DayOfWeek[] values() {
        return (DayOfWeek[]) f429b.clone();
    }

    static {
        DayOfWeek dayOfWeek = new DayOfWeek("MONDAY", 0);
        MONDAY = dayOfWeek;
        DayOfWeek dayOfWeek2 = new DayOfWeek("TUESDAY", 1);
        TUESDAY = dayOfWeek2;
        DayOfWeek dayOfWeek3 = new DayOfWeek("WEDNESDAY", 2);
        WEDNESDAY = dayOfWeek3;
        DayOfWeek dayOfWeek4 = new DayOfWeek("THURSDAY", 3);
        THURSDAY = dayOfWeek4;
        DayOfWeek dayOfWeek5 = new DayOfWeek("FRIDAY", 4);
        FRIDAY = dayOfWeek5;
        DayOfWeek dayOfWeek6 = new DayOfWeek("SATURDAY", 5);
        SATURDAY = dayOfWeek6;
        DayOfWeek dayOfWeek7 = new DayOfWeek("SUNDAY", 6);
        SUNDAY = dayOfWeek7;
        f429b = new DayOfWeek[]{dayOfWeek, dayOfWeek2, dayOfWeek3, dayOfWeek4, dayOfWeek5, dayOfWeek6, dayOfWeek7};
        f428a = values();
    }

    /* renamed from: Q */
    public static DayOfWeek m541Q(int i) {
        if (i < 1 || i > 7) {
            throw new C1640b("Invalid value for DayOfWeek: " + i);
        }
        return f428a[i - 1];
    }

    public final int getValue() {
        return ordinal() + 1;
    }

    public String getDisplayName(TextStyle textStyle, Locale locale) {
        C1707u c1707u = new C1707u();
        c1707u.m751i(EnumC1727a.DAY_OF_WEEK, textStyle);
        return c1707u.m759q(locale, EnumC1684E.SMART, null).m719a(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: e */
    public final boolean mo543e(InterfaceC1744r interfaceC1744r) {
        return interfaceC1744r instanceof EnumC1727a ? interfaceC1744r == EnumC1727a.DAY_OF_WEEK : interfaceC1744r != null && interfaceC1744r.mo802i(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: k */
    public final C1748v mo545k(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.DAY_OF_WEEK) {
            return interfaceC1744r.mo805n();
        }
        return AbstractC1745s.m816d(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: i */
    public final int mo544i(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.DAY_OF_WEEK) {
            return getValue();
        }
        return AbstractC1745s.m813a(this, interfaceC1744r);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: D */
    public final long mo542D(InterfaceC1744r interfaceC1744r) {
        if (interfaceC1744r == EnumC1727a.DAY_OF_WEEK) {
            return getValue();
        }
        if (interfaceC1744r instanceof EnumC1727a) {
            throw new C1747u(AbstractC1641c.m643a("Unsupported field: ", interfaceC1744r));
        }
        return interfaceC1744r.mo806t(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1740n
    /* renamed from: t */
    public final Object mo547t(C1678e c1678e) {
        if (c1678e == AbstractC1745s.f692c) {
            return EnumC1728b.DAYS;
        }
        return AbstractC1745s.m815c(this, c1678e);
    }

    @Override // p017j$.time.temporal.InterfaceC1741o
    /* renamed from: n */
    public final InterfaceC1739m mo546n(InterfaceC1739m interfaceC1739m) {
        return interfaceC1739m.mo556c(getValue(), EnumC1727a.DAY_OF_WEEK);
    }
}
