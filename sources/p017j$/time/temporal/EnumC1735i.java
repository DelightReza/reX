package p017j$.time.temporal;

import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.Duration;

/* renamed from: j$.time.temporal.i */
/* loaded from: classes2.dex */
public enum EnumC1735i implements InterfaceC1746t {
    WEEK_BASED_YEARS("WeekBasedYears"),
    QUARTER_YEARS("QuarterYears");


    /* renamed from: a */
    public final String f678a;

    static {
        Duration.ofSeconds(31556952L);
        Duration.ofSeconds(7889238L);
    }

    EnumC1735i(String str) {
        this.f678a = str;
    }

    @Override // p017j$.time.temporal.InterfaceC1746t
    /* renamed from: i */
    public final InterfaceC1739m mo808i(InterfaceC1739m interfaceC1739m, long j) {
        int i = AbstractC1729c.f674a[ordinal()];
        if (i == 1) {
            return interfaceC1739m.mo556c(AbstractC1636a.m494M(interfaceC1739m.mo544i(r0), j), AbstractC1736j.f681c);
        }
        if (i == 2) {
            return interfaceC1739m.mo557d(j / 4, EnumC1728b.YEARS).mo557d((j % 4) * 3, EnumC1728b.MONTHS);
        }
        throw new IllegalStateException("Unreachable");
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f678a;
    }
}
