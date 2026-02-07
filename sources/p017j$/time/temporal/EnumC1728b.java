package p017j$.time.temporal;

import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.Duration;

/* renamed from: j$.time.temporal.b */
/* loaded from: classes2.dex */
public enum EnumC1728b implements InterfaceC1746t {
    NANOS("Nanos"),
    MICROS("Micros"),
    MILLIS("Millis"),
    SECONDS("Seconds"),
    MINUTES("Minutes"),
    HOURS("Hours"),
    HALF_DAYS("HalfDays"),
    DAYS("Days"),
    WEEKS("Weeks"),
    MONTHS("Months"),
    YEARS("Years"),
    DECADES("Decades"),
    CENTURIES("Centuries"),
    MILLENNIA("Millennia"),
    ERAS("Eras"),
    FOREVER("Forever");


    /* renamed from: a */
    public final String f673a;

    static {
        Duration.m549k(1L);
        Duration.m549k(1000L);
        Duration.m549k(1000000L);
        Duration.ofSeconds(1L);
        Duration.ofSeconds(60L);
        Duration.ofSeconds(3600L);
        Duration.ofSeconds(43200L);
        Duration.ofSeconds(86400L);
        Duration.ofSeconds(604800L);
        Duration.ofSeconds(2629746L);
        Duration.ofSeconds(31556952L);
        Duration.ofSeconds(315569520L);
        Duration.ofSeconds(3155695200L);
        Duration.ofSeconds(31556952000L);
        Duration.ofSeconds(31556952000000000L);
        Duration.m548j(AbstractC1636a.m494M(Long.MAX_VALUE, AbstractC1636a.m499R(999999999L, 1000000000L)), (int) AbstractC1636a.m498Q(999999999L, 1000000000L));
    }

    EnumC1728b(String str) {
        this.f673a = str;
    }

    @Override // p017j$.time.temporal.InterfaceC1746t
    /* renamed from: i */
    public final InterfaceC1739m mo808i(InterfaceC1739m interfaceC1739m, long j) {
        return interfaceC1739m.mo557d(j, this);
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f673a;
    }
}
