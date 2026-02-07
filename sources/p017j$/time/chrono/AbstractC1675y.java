package p017j$.time.chrono;

import p017j$.time.temporal.EnumC1727a;

/* renamed from: j$.time.chrono.y */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC1675y {

    /* renamed from: a */
    public static final /* synthetic */ int[] f527a;

    static {
        int[] iArr = new int[EnumC1727a.values().length];
        f527a = iArr;
        try {
            iArr[EnumC1727a.PROLEPTIC_MONTH.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f527a[EnumC1727a.YEAR_OF_ERA.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f527a[EnumC1727a.YEAR.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
    }
}
