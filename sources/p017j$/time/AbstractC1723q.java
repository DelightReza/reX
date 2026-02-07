package p017j$.time;

import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;

/* renamed from: j$.time.q */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC1723q {

    /* renamed from: a */
    public static final /* synthetic */ int[] f661a;

    /* renamed from: b */
    public static final /* synthetic */ int[] f662b;

    static {
        int[] iArr = new int[EnumC1728b.values().length];
        f662b = iArr;
        try {
            iArr[EnumC1728b.YEARS.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f662b[EnumC1728b.DECADES.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f662b[EnumC1728b.CENTURIES.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f662b[EnumC1728b.MILLENNIA.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f662b[EnumC1728b.ERAS.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        int[] iArr2 = new int[EnumC1727a.values().length];
        f661a = iArr2;
        try {
            iArr2[EnumC1727a.YEAR_OF_ERA.ordinal()] = 1;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f661a[EnumC1727a.YEAR.ordinal()] = 2;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f661a[EnumC1727a.ERA.ordinal()] = 3;
        } catch (NoSuchFieldError unused8) {
        }
    }
}
