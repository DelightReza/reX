package p017j$.time.temporal;

import java.util.Map;
import p017j$.time.format.C1683D;
import p017j$.time.format.EnumC1684E;

/* renamed from: j$.time.temporal.a */
/* loaded from: classes2.dex */
public enum EnumC1727a implements InterfaceC1744r {
    NANO_OF_SECOND("NanoOfSecond", C1748v.m818f(0, 999999999)),
    NANO_OF_DAY("NanoOfDay", C1748v.m818f(0, 86399999999999L)),
    MICRO_OF_SECOND("MicroOfSecond", C1748v.m818f(0, 999999)),
    MICRO_OF_DAY("MicroOfDay", C1748v.m818f(0, 86399999999L)),
    MILLI_OF_SECOND("MilliOfSecond", C1748v.m818f(0, 999)),
    MILLI_OF_DAY("MilliOfDay", C1748v.m818f(0, 86399999)),
    SECOND_OF_MINUTE("SecondOfMinute", C1748v.m818f(0, 59), 0),
    SECOND_OF_DAY("SecondOfDay", C1748v.m818f(0, 86399)),
    MINUTE_OF_HOUR("MinuteOfHour", C1748v.m818f(0, 59), 0),
    MINUTE_OF_DAY("MinuteOfDay", C1748v.m818f(0, 1439)),
    HOUR_OF_AMPM("HourOfAmPm", C1748v.m818f(0, 11)),
    CLOCK_HOUR_OF_AMPM("ClockHourOfAmPm", C1748v.m818f(1, 12)),
    HOUR_OF_DAY("HourOfDay", C1748v.m818f(0, 23), 0),
    CLOCK_HOUR_OF_DAY("ClockHourOfDay", C1748v.m818f(1, 24)),
    AMPM_OF_DAY("AmPmOfDay", C1748v.m818f(0, 1), 0),
    DAY_OF_WEEK("DayOfWeek", C1748v.m818f(1, 7), 0),
    ALIGNED_DAY_OF_WEEK_IN_MONTH("AlignedDayOfWeekInMonth", C1748v.m818f(1, 7)),
    ALIGNED_DAY_OF_WEEK_IN_YEAR("AlignedDayOfWeekInYear", C1748v.m818f(1, 7)),
    DAY_OF_MONTH("DayOfMonth", C1748v.m819g(1, 28, 31), 0),
    DAY_OF_YEAR("DayOfYear", C1748v.m819g(1, 365, 366)),
    EPOCH_DAY("EpochDay", C1748v.m818f(-365243219162L, 365241780471L)),
    ALIGNED_WEEK_OF_MONTH("AlignedWeekOfMonth", C1748v.m819g(1, 4, 5)),
    ALIGNED_WEEK_OF_YEAR("AlignedWeekOfYear", C1748v.m818f(1, 53)),
    MONTH_OF_YEAR("MonthOfYear", C1748v.m818f(1, 12), 0),
    PROLEPTIC_MONTH("ProlepticMonth", C1748v.m818f(-11999999988L, 11999999999L)),
    YEAR_OF_ERA("YearOfEra", C1748v.m819g(1, 999999999, 1000000000)),
    YEAR("Year", C1748v.m818f(-999999999, 999999999), 0),
    ERA("Era", C1748v.m818f(0, 1), 0),
    INSTANT_SECONDS("InstantSeconds", C1748v.m818f(Long.MIN_VALUE, Long.MAX_VALUE)),
    OFFSET_SECONDS("OffsetSeconds", C1748v.m818f(-64800, 64800));


    /* renamed from: a */
    public final String f670a;

    /* renamed from: b */
    public final C1748v f671b;

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: k */
    public final /* synthetic */ InterfaceC1740n mo804k(Map map, C1683D c1683d, EnumC1684E enumC1684E) {
        return null;
    }

    static {
        EnumC1728b enumC1728b = EnumC1728b.NANOS;
    }

    EnumC1727a(String str, C1748v c1748v) {
        this.f670a = str;
        this.f671b = c1748v;
    }

    EnumC1727a(String str, C1748v c1748v, int i) {
        this.f670a = str;
        this.f671b = c1748v;
    }

    /* renamed from: D */
    public final void m800D(long j) {
        this.f671b.m821b(j, this);
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: n */
    public final C1748v mo805n() {
        return this.f671b;
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    public final boolean isDateBased() {
        return ordinal() >= DAY_OF_WEEK.ordinal() && ordinal() <= ERA.ordinal();
    }

    /* renamed from: Q */
    public final boolean m801Q() {
        return ordinal() < DAY_OF_WEEK.ordinal();
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: i */
    public final boolean mo802i(InterfaceC1740n interfaceC1740n) {
        return interfaceC1740n.mo543e(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: j */
    public final C1748v mo803j(InterfaceC1740n interfaceC1740n) {
        return interfaceC1740n.mo545k(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: t */
    public final long mo806t(InterfaceC1740n interfaceC1740n) {
        return interfaceC1740n.mo542D(this);
    }

    @Override // p017j$.time.temporal.InterfaceC1744r
    /* renamed from: y */
    public final InterfaceC1739m mo807y(InterfaceC1739m interfaceC1739m, long j) {
        return interfaceC1739m.mo556c(j, this);
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f670a;
    }
}
