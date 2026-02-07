package p017j$.util.stream;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.util.stream.c3 */
/* loaded from: classes2.dex */
public final class EnumC2001c3 {
    public static final EnumC2001c3 DOUBLE_VALUE;
    public static final EnumC2001c3 INT_VALUE;
    public static final EnumC2001c3 LONG_VALUE;
    public static final EnumC2001c3 REFERENCE;

    /* renamed from: a */
    public static final /* synthetic */ EnumC2001c3[] f1216a;

    public static EnumC2001c3 valueOf(String str) {
        return (EnumC2001c3) Enum.valueOf(EnumC2001c3.class, str);
    }

    public static EnumC2001c3[] values() {
        return (EnumC2001c3[]) f1216a.clone();
    }

    static {
        EnumC2001c3 enumC2001c3 = new EnumC2001c3("REFERENCE", 0);
        REFERENCE = enumC2001c3;
        EnumC2001c3 enumC2001c32 = new EnumC2001c3("INT_VALUE", 1);
        INT_VALUE = enumC2001c32;
        EnumC2001c3 enumC2001c33 = new EnumC2001c3("LONG_VALUE", 2);
        LONG_VALUE = enumC2001c33;
        EnumC2001c3 enumC2001c34 = new EnumC2001c3("DOUBLE_VALUE", 3);
        DOUBLE_VALUE = enumC2001c34;
        f1216a = new EnumC2001c3[]{enumC2001c3, enumC2001c32, enumC2001c33, enumC2001c34};
    }
}
