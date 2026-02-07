package p017j$.util.stream;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.util.stream.C3 */
/* loaded from: classes2.dex */
public final class EnumC1870C3 {
    public static final EnumC1870C3 MAYBE_MORE;
    public static final EnumC1870C3 NO_MORE;
    public static final EnumC1870C3 UNLIMITED;

    /* renamed from: a */
    public static final /* synthetic */ EnumC1870C3[] f977a;

    static {
        EnumC1870C3 enumC1870C3 = new EnumC1870C3("NO_MORE", 0);
        NO_MORE = enumC1870C3;
        EnumC1870C3 enumC1870C32 = new EnumC1870C3("MAYBE_MORE", 1);
        MAYBE_MORE = enumC1870C32;
        EnumC1870C3 enumC1870C33 = new EnumC1870C3("UNLIMITED", 2);
        UNLIMITED = enumC1870C33;
        f977a = new EnumC1870C3[]{enumC1870C3, enumC1870C32, enumC1870C33};
    }

    public static EnumC1870C3 valueOf(String str) {
        return (EnumC1870C3) Enum.valueOf(EnumC1870C3.class, str);
    }

    public static EnumC1870C3[] values() {
        return (EnumC1870C3[]) f977a.clone();
    }
}
