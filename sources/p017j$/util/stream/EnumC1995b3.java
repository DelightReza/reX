package p017j$.util.stream;

import java.util.EnumMap;
import java.util.Map;
import p017j$.time.C1726t;
import p017j$.util.Map;
import p017j$.util.Spliterator;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'DISTINCT' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:395)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:324)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* renamed from: j$.util.stream.b3 */
/* loaded from: classes2.dex */
public final class EnumC1995b3 {
    public static final EnumC1995b3 DISTINCT;
    public static final EnumC1995b3 ORDERED;
    public static final EnumC1995b3 SHORT_CIRCUIT;
    public static final EnumC1995b3 SIZED;
    public static final EnumC1995b3 SORTED;

    /* renamed from: f */
    public static final int f1188f;

    /* renamed from: g */
    public static final int f1189g;

    /* renamed from: h */
    public static final int f1190h;

    /* renamed from: i */
    public static final int f1191i;

    /* renamed from: j */
    public static final int f1192j;

    /* renamed from: k */
    public static final int f1193k;

    /* renamed from: l */
    public static final int f1194l;

    /* renamed from: m */
    public static final int f1195m;

    /* renamed from: n */
    public static final int f1196n;

    /* renamed from: o */
    public static final int f1197o;

    /* renamed from: p */
    public static final int f1198p;

    /* renamed from: q */
    public static final int f1199q;

    /* renamed from: r */
    public static final int f1200r;

    /* renamed from: s */
    public static final int f1201s;

    /* renamed from: t */
    public static final int f1202t;

    /* renamed from: u */
    public static final int f1203u;

    /* renamed from: v */
    public static final /* synthetic */ EnumC1995b3[] f1204v;

    /* renamed from: a */
    public final Map f1205a;

    /* renamed from: b */
    public final int f1206b;

    /* renamed from: c */
    public final int f1207c;

    /* renamed from: d */
    public final int f1208d;

    /* renamed from: e */
    public final int f1209e;

    public static EnumC1995b3 valueOf(String str) {
        return (EnumC1995b3) Enum.valueOf(EnumC1995b3.class, str);
    }

    public static EnumC1995b3[] values() {
        return (EnumC1995b3[]) f1204v.clone();
    }

    static {
        EnumC1989a3 enumC1989a3 = EnumC1989a3.SPLITERATOR;
        C1726t c1726tM1029t = m1029t(enumC1989a3);
        EnumC1989a3 enumC1989a32 = EnumC1989a3.STREAM;
        c1726tM1029t.m799s(enumC1989a32);
        EnumC1989a3 enumC1989a33 = EnumC1989a3.f1173OP;
        ((EnumMap) ((Map) c1726tM1029t.f668b)).put((EnumMap) enumC1989a33, (EnumC1989a3) 3);
        EnumC1995b3 enumC1995b3 = new EnumC1995b3("DISTINCT", 0, 0, c1726tM1029t);
        DISTINCT = enumC1995b3;
        C1726t c1726tM1029t2 = m1029t(enumC1989a3);
        c1726tM1029t2.m799s(enumC1989a32);
        ((EnumMap) ((Map) c1726tM1029t2.f668b)).put((EnumMap) enumC1989a33, (EnumC1989a3) 3);
        EnumC1995b3 enumC1995b32 = new EnumC1995b3("SORTED", 1, 1, c1726tM1029t2);
        SORTED = enumC1995b32;
        C1726t c1726tM1029t3 = m1029t(enumC1989a3);
        c1726tM1029t3.m799s(enumC1989a32);
        ((EnumMap) ((Map) c1726tM1029t3.f668b)).put((EnumMap) enumC1989a33, (EnumC1989a3) 3);
        EnumC1989a3 enumC1989a34 = EnumC1989a3.TERMINAL_OP;
        ((EnumMap) ((Map) c1726tM1029t3.f668b)).put((EnumMap) enumC1989a34, (EnumC1989a3) 2);
        EnumC1989a3 enumC1989a35 = EnumC1989a3.UPSTREAM_TERMINAL_OP;
        ((EnumMap) ((Map) c1726tM1029t3.f668b)).put((EnumMap) enumC1989a35, (EnumC1989a3) 2);
        EnumC1995b3 enumC1995b33 = new EnumC1995b3("ORDERED", 2, 2, c1726tM1029t3);
        ORDERED = enumC1995b33;
        C1726t c1726tM1029t4 = m1029t(enumC1989a3);
        c1726tM1029t4.m799s(enumC1989a32);
        ((EnumMap) ((Map) c1726tM1029t4.f668b)).put((EnumMap) enumC1989a33, (EnumC1989a3) 2);
        EnumC1995b3 enumC1995b34 = new EnumC1995b3("SIZED", 3, 3, c1726tM1029t4);
        SIZED = enumC1995b34;
        C1726t c1726tM1029t5 = m1029t(enumC1989a33);
        c1726tM1029t5.m799s(enumC1989a34);
        int i = 0;
        EnumC1995b3 enumC1995b35 = new EnumC1995b3("SHORT_CIRCUIT", 4, 12, c1726tM1029t5);
        SHORT_CIRCUIT = enumC1995b35;
        f1204v = new EnumC1995b3[]{enumC1995b3, enumC1995b32, enumC1995b33, enumC1995b34, enumC1995b35};
        f1188f = m1027j(enumC1989a3);
        f1189g = m1027j(enumC1989a32);
        f1190h = m1027j(enumC1989a33);
        m1027j(enumC1989a34);
        m1027j(enumC1989a35);
        for (EnumC1995b3 enumC1995b36 : values()) {
            i |= enumC1995b36.f1209e;
        }
        f1191i = i;
        int i2 = f1189g;
        f1192j = i2;
        int i3 = i2 << 1;
        f1193k = i3;
        f1194l = i2 | i3;
        EnumC1995b3 enumC1995b37 = DISTINCT;
        f1195m = enumC1995b37.f1207c;
        f1196n = enumC1995b37.f1208d;
        EnumC1995b3 enumC1995b38 = SORTED;
        f1197o = enumC1995b38.f1207c;
        f1198p = enumC1995b38.f1208d;
        EnumC1995b3 enumC1995b39 = ORDERED;
        f1199q = enumC1995b39.f1207c;
        f1200r = enumC1995b39.f1208d;
        EnumC1995b3 enumC1995b310 = SIZED;
        f1201s = enumC1995b310.f1207c;
        f1202t = enumC1995b310.f1208d;
        f1203u = SHORT_CIRCUIT.f1207c;
    }

    /* renamed from: t */
    public static C1726t m1029t(EnumC1989a3 enumC1989a3) {
        C1726t c1726t = new C1726t(13, new EnumMap(EnumC1989a3.class));
        c1726t.m799s(enumC1989a3);
        return c1726t;
    }

    public EnumC1995b3(String str, int i, int i2, C1726t c1726t) {
        for (EnumC1989a3 enumC1989a3 : EnumC1989a3.values()) {
            Map.EL.putIfAbsent((java.util.Map) c1726t.f668b, enumC1989a3, 0);
        }
        this.f1205a = (java.util.Map) c1726t.f668b;
        int i3 = i2 * 2;
        this.f1206b = i3;
        this.f1207c = 1 << i3;
        this.f1208d = 2 << i3;
        this.f1209e = 3 << i3;
    }

    /* renamed from: n */
    public final boolean m1030n(int i) {
        return (i & this.f1209e) == this.f1207c;
    }

    /* renamed from: j */
    public static int m1027j(EnumC1989a3 enumC1989a3) {
        int iIntValue = 0;
        for (EnumC1995b3 enumC1995b3 : values()) {
            iIntValue |= ((Integer) enumC1995b3.f1205a.get(enumC1989a3)).intValue() << enumC1995b3.f1206b;
        }
        return iIntValue;
    }

    /* renamed from: i */
    public static int m1026i(int i, int i2) {
        int i3;
        if (i == 0) {
            i3 = f1191i;
        } else {
            i3 = ~(((f1192j & i) << 1) | i | ((f1193k & i) >> 1));
        }
        return i | (i2 & i3);
    }

    /* renamed from: k */
    public static int m1028k(Spliterator spliterator) {
        int iCharacteristics = spliterator.characteristics();
        int i = iCharacteristics & 4;
        int i2 = f1188f;
        return (i == 0 || spliterator.getComparator() == null) ? iCharacteristics & i2 : iCharacteristics & i2 & (-5);
    }
}
