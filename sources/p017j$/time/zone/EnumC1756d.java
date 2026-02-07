package p017j$.time.zone;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.time.zone.d */
/* loaded from: classes2.dex */
public final class EnumC1756d {
    public static final EnumC1756d STANDARD;
    public static final EnumC1756d UTC;
    public static final EnumC1756d WALL;

    /* renamed from: a */
    public static final /* synthetic */ EnumC1756d[] f742a;

    public static EnumC1756d valueOf(String str) {
        return (EnumC1756d) Enum.valueOf(EnumC1756d.class, str);
    }

    public static EnumC1756d[] values() {
        return (EnumC1756d[]) f742a.clone();
    }

    static {
        EnumC1756d enumC1756d = new EnumC1756d("UTC", 0);
        UTC = enumC1756d;
        EnumC1756d enumC1756d2 = new EnumC1756d("WALL", 1);
        WALL = enumC1756d2;
        EnumC1756d enumC1756d3 = new EnumC1756d("STANDARD", 2);
        STANDARD = enumC1756d3;
        f742a = new EnumC1756d[]{enumC1756d, enumC1756d2, enumC1756d3};
    }
}
