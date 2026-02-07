package p017j$.time;

import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.EnumC1728b;

/* renamed from: j$.time.d */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class AbstractC1677d {

    /* renamed from: a */
    public static final /* synthetic */ int[] f529a;

    /* renamed from: b */
    public static final /* synthetic */ int[] f530b;

    static {
        int[] iArr = new int[EnumC1728b.values().length];
        f530b = iArr;
        try {
            iArr[EnumC1728b.NANOS.ordinal()] = 1;
        } catch (NoSuchFieldError unused) {
        }
        try {
            f530b[EnumC1728b.MICROS.ordinal()] = 2;
        } catch (NoSuchFieldError unused2) {
        }
        try {
            f530b[EnumC1728b.MILLIS.ordinal()] = 3;
        } catch (NoSuchFieldError unused3) {
        }
        try {
            f530b[EnumC1728b.SECONDS.ordinal()] = 4;
        } catch (NoSuchFieldError unused4) {
        }
        try {
            f530b[EnumC1728b.MINUTES.ordinal()] = 5;
        } catch (NoSuchFieldError unused5) {
        }
        try {
            f530b[EnumC1728b.HOURS.ordinal()] = 6;
        } catch (NoSuchFieldError unused6) {
        }
        try {
            f530b[EnumC1728b.HALF_DAYS.ordinal()] = 7;
        } catch (NoSuchFieldError unused7) {
        }
        try {
            f530b[EnumC1728b.DAYS.ordinal()] = 8;
        } catch (NoSuchFieldError unused8) {
        }
        int[] iArr2 = new int[EnumC1727a.values().length];
        f529a = iArr2;
        try {
            iArr2[EnumC1727a.NANO_OF_SECOND.ordinal()] = 1;
        } catch (NoSuchFieldError unused9) {
        }
        try {
            f529a[EnumC1727a.MICRO_OF_SECOND.ordinal()] = 2;
        } catch (NoSuchFieldError unused10) {
        }
        try {
            f529a[EnumC1727a.MILLI_OF_SECOND.ordinal()] = 3;
        } catch (NoSuchFieldError unused11) {
        }
        try {
            f529a[EnumC1727a.INSTANT_SECONDS.ordinal()] = 4;
        } catch (NoSuchFieldError unused12) {
        }
    }
}
