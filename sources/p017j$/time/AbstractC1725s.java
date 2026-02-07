package p017j$.time;

import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;

/* renamed from: j$.time.s */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC1725s {

    /* renamed from: a */
    public static final /* synthetic */ int[] f665a;

    /* renamed from: b */
    public static final /* synthetic */ int[] f666b;

    static {
        int[] iArr = new int[EnumC1728b.values().length];
        f666b = iArr;
        try {
            iArr[EnumC1728b.MONTHS.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f666b[EnumC1728b.YEARS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f666b[EnumC1728b.DECADES.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f666b[EnumC1728b.CENTURIES.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f666b[EnumC1728b.MILLENNIA.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f666b[EnumC1728b.ERAS.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        int[] iArr2 = new int[EnumC1727a.values().length];
        f665a = iArr2;
        try {
            iArr2[EnumC1727a.MONTH_OF_YEAR.ordinal()] = 1;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f665a[EnumC1727a.PROLEPTIC_MONTH.ordinal()] = 2;
        } catch (NoSuchFieldError unused8) {
        }
        try {
            f665a[EnumC1727a.YEAR_OF_ERA.ordinal()] = 3;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            f665a[EnumC1727a.YEAR.ordinal()] = 4;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            f665a[EnumC1727a.ERA.ordinal()] = 5;
        } catch (NoSuchFieldError unused11) {
        }
    }
}
