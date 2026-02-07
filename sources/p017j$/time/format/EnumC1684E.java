package p017j$.time.format;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.time.format.E */
/* loaded from: classes2.dex */
public final class EnumC1684E {
    public static final EnumC1684E LENIENT;
    public static final EnumC1684E SMART;
    public static final EnumC1684E STRICT;

    /* renamed from: a */
    public static final /* synthetic */ EnumC1684E[] f554a;

    public static EnumC1684E valueOf(String str) {
        return (EnumC1684E) Enum.valueOf(EnumC1684E.class, str);
    }

    public static EnumC1684E[] values() {
        return (EnumC1684E[]) f554a.clone();
    }

    static {
        EnumC1684E enumC1684E = new EnumC1684E("STRICT", 0);
        STRICT = enumC1684E;
        EnumC1684E enumC1684E2 = new EnumC1684E("SMART", 1);
        SMART = enumC1684E2;
        EnumC1684E enumC1684E3 = new EnumC1684E("LENIENT", 2);
        LENIENT = enumC1684E3;
        f554a = new EnumC1684E[]{enumC1684E, enumC1684E2, enumC1684E3};
    }
}
