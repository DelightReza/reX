package p017j$.util.stream;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.util.stream.g */
/* loaded from: classes2.dex */
public final class EnumC2021g {
    public static final EnumC2021g CONCURRENT;
    public static final EnumC2021g IDENTITY_FINISH;
    public static final EnumC2021g UNORDERED;

    /* renamed from: a */
    public static final /* synthetic */ EnumC2021g[] f1246a;

    public static EnumC2021g valueOf(String str) {
        return (EnumC2021g) Enum.valueOf(EnumC2021g.class, str);
    }

    public static EnumC2021g[] values() {
        return (EnumC2021g[]) f1246a.clone();
    }

    static {
        EnumC2021g enumC2021g = new EnumC2021g("CONCURRENT", 0);
        CONCURRENT = enumC2021g;
        EnumC2021g enumC2021g2 = new EnumC2021g("UNORDERED", 1);
        UNORDERED = enumC2021g2;
        EnumC2021g enumC2021g3 = new EnumC2021g("IDENTITY_FINISH", 2);
        IDENTITY_FINISH = enumC2021g3;
        f1246a = new EnumC2021g[]{enumC2021g, enumC2021g2, enumC2021g3};
    }
}
