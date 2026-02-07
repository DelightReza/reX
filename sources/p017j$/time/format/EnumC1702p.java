package p017j$.time.format;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.time.format.p */
/* loaded from: classes2.dex */
public final class EnumC1702p implements InterfaceC1691e {
    public static final EnumC1702p INSENSITIVE;
    public static final EnumC1702p LENIENT;
    public static final EnumC1702p SENSITIVE;
    public static final EnumC1702p STRICT;

    /* renamed from: a */
    public static final /* synthetic */ EnumC1702p[] f600a;

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    public final boolean mo721i(C1711y c1711y, StringBuilder sb) {
        return true;
    }

    public static EnumC1702p valueOf(String str) {
        return (EnumC1702p) Enum.valueOf(EnumC1702p.class, str);
    }

    public static EnumC1702p[] values() {
        return (EnumC1702p[]) f600a.clone();
    }

    static {
        EnumC1702p enumC1702p = new EnumC1702p("SENSITIVE", 0);
        SENSITIVE = enumC1702p;
        EnumC1702p enumC1702p2 = new EnumC1702p("INSENSITIVE", 1);
        INSENSITIVE = enumC1702p2;
        EnumC1702p enumC1702p3 = new EnumC1702p("STRICT", 2);
        STRICT = enumC1702p3;
        EnumC1702p enumC1702p4 = new EnumC1702p("LENIENT", 3);
        LENIENT = enumC1702p4;
        f600a = new EnumC1702p[]{enumC1702p, enumC1702p2, enumC1702p3, enumC1702p4};
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    public final int mo722j(C1708v c1708v, CharSequence charSequence, int i) {
        int iOrdinal = ordinal();
        if (iOrdinal == 0) {
            c1708v.f626b = true;
            return i;
        }
        if (iOrdinal == 1) {
            c1708v.f626b = false;
            return i;
        }
        if (iOrdinal == 2) {
            c1708v.f627c = true;
            return i;
        }
        if (iOrdinal != 3) {
            return i;
        }
        c1708v.f627c = false;
        return i;
    }

    @Override // java.lang.Enum
    public final String toString() {
        int iOrdinal = ordinal();
        if (iOrdinal == 0) {
            return "ParseCaseSensitive(true)";
        }
        if (iOrdinal == 1) {
            return "ParseCaseSensitive(false)";
        }
        if (iOrdinal == 2) {
            return "ParseStrict(true)";
        }
        if (iOrdinal == 3) {
            return "ParseStrict(false)";
        }
        throw new IllegalStateException("Unreachable");
    }
}
