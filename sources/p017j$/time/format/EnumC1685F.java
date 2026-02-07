package p017j$.time.format;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.time.format.F */
/* loaded from: classes2.dex */
public final class EnumC1685F {
    public static final EnumC1685F ALWAYS;
    public static final EnumC1685F EXCEEDS_PAD;
    public static final EnumC1685F NEVER;
    public static final EnumC1685F NORMAL;
    public static final EnumC1685F NOT_NEGATIVE;

    /* renamed from: a */
    public static final /* synthetic */ EnumC1685F[] f555a;

    public static EnumC1685F valueOf(String str) {
        return (EnumC1685F) Enum.valueOf(EnumC1685F.class, str);
    }

    public static EnumC1685F[] values() {
        return (EnumC1685F[]) f555a.clone();
    }

    static {
        EnumC1685F enumC1685F = new EnumC1685F("NORMAL", 0);
        NORMAL = enumC1685F;
        EnumC1685F enumC1685F2 = new EnumC1685F("ALWAYS", 1);
        ALWAYS = enumC1685F2;
        EnumC1685F enumC1685F3 = new EnumC1685F("NEVER", 2);
        NEVER = enumC1685F3;
        EnumC1685F enumC1685F4 = new EnumC1685F("NOT_NEGATIVE", 3);
        NOT_NEGATIVE = enumC1685F4;
        EnumC1685F enumC1685F5 = new EnumC1685F("EXCEEDS_PAD", 4);
        EXCEEDS_PAD = enumC1685F5;
        f555a = new EnumC1685F[]{enumC1685F, enumC1685F2, enumC1685F3, enumC1685F4, enumC1685F5};
    }
}
