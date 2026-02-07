package p017j$.util.stream;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.util.stream.a3 */
/* loaded from: classes2.dex */
public final class EnumC1989a3 {

    /* renamed from: OP */
    public static final EnumC1989a3 f1173OP;
    public static final EnumC1989a3 SPLITERATOR;
    public static final EnumC1989a3 STREAM;
    public static final EnumC1989a3 TERMINAL_OP;
    public static final EnumC1989a3 UPSTREAM_TERMINAL_OP;

    /* renamed from: a */
    public static final /* synthetic */ EnumC1989a3[] f1174a;

    public static EnumC1989a3 valueOf(String str) {
        return (EnumC1989a3) Enum.valueOf(EnumC1989a3.class, str);
    }

    public static EnumC1989a3[] values() {
        return (EnumC1989a3[]) f1174a.clone();
    }

    static {
        EnumC1989a3 enumC1989a3 = new EnumC1989a3("SPLITERATOR", 0);
        SPLITERATOR = enumC1989a3;
        EnumC1989a3 enumC1989a32 = new EnumC1989a3("STREAM", 1);
        STREAM = enumC1989a32;
        EnumC1989a3 enumC1989a33 = new EnumC1989a3("OP", 2);
        f1173OP = enumC1989a33;
        EnumC1989a3 enumC1989a34 = new EnumC1989a3("TERMINAL_OP", 3);
        TERMINAL_OP = enumC1989a34;
        EnumC1989a3 enumC1989a35 = new EnumC1989a3("UPSTREAM_TERMINAL_OP", 4);
        UPSTREAM_TERMINAL_OP = enumC1989a35;
        f1174a = new EnumC1989a3[]{enumC1989a3, enumC1989a32, enumC1989a33, enumC1989a34, enumC1989a35};
    }
}
