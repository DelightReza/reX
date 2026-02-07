package p017j$.time.chrono;

import p017j$.time.temporal.EnumC1727a;

/* renamed from: j$.time.chrono.A */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC1642A {

    /* renamed from: a */
    public static final /* synthetic */ int[] f467a;

    static {
        int[] iArr = new int[EnumC1727a.values().length];
        f467a = iArr;
        try {
            iArr[EnumC1727a.DAY_OF_MONTH.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f467a[EnumC1727a.DAY_OF_YEAR.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f467a[EnumC1727a.ALIGNED_WEEK_OF_MONTH.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f467a[EnumC1727a.YEAR_OF_ERA.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f467a[EnumC1727a.PROLEPTIC_MONTH.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f467a[EnumC1727a.YEAR.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f467a[EnumC1727a.ERA.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
    }
}
