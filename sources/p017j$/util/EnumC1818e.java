package p017j$.util;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import p017j$.util.Comparator;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: j$.util.e */
/* loaded from: classes2.dex */
public final class EnumC1818e implements Comparator, Comparator {
    public static final EnumC1818e INSTANCE;

    /* renamed from: a */
    public static final /* synthetic */ EnumC1818e[] f884a;

    @Override // java.util.Comparator, p017j$.util.Comparator
    public final /* synthetic */ Comparator thenComparing(Comparator comparator) {
        return Comparator.CC.$default$thenComparing(this, comparator);
    }

    @Override // java.util.Comparator, p017j$.util.Comparator
    public final /* synthetic */ java.util.Comparator thenComparing(Function function) {
        return Comparator.EL.m853a(this, Comparator.CC.comparing(function));
    }

    @Override // java.util.Comparator, p017j$.util.Comparator
    public final /* synthetic */ java.util.Comparator thenComparing(Function function, java.util.Comparator comparator) {
        return Comparator.EL.m853a(this, Comparator.CC.comparing(function, comparator));
    }

    @Override // java.util.Comparator, p017j$.util.Comparator
    public final /* synthetic */ java.util.Comparator thenComparingDouble(ToDoubleFunction toDoubleFunction) {
        return Comparator.EL.m853a(this, Comparator.CC.comparingDouble(toDoubleFunction));
    }

    @Override // java.util.Comparator, p017j$.util.Comparator
    public final /* synthetic */ java.util.Comparator thenComparingInt(ToIntFunction toIntFunction) {
        return Comparator.CC.$default$thenComparingInt(this, toIntFunction);
    }

    @Override // java.util.Comparator, p017j$.util.Comparator
    public final /* synthetic */ java.util.Comparator thenComparingLong(ToLongFunction toLongFunction) {
        return Comparator.EL.m853a(this, Comparator.CC.comparingLong(toLongFunction));
    }

    public static EnumC1818e valueOf(String str) {
        return (EnumC1818e) Enum.valueOf(EnumC1818e.class, str);
    }

    public static EnumC1818e[] values() {
        return (EnumC1818e[]) f884a.clone();
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return ((Comparable) obj).compareTo((Comparable) obj2);
    }

    static {
        EnumC1818e enumC1818e = new EnumC1818e("INSTANCE", 0);
        INSTANCE = enumC1818e;
        f884a = new EnumC1818e[]{enumC1818e};
    }

    @Override // java.util.Comparator, p017j$.util.Comparator
    public final java.util.Comparator reversed() {
        return Comparator.CC.reverseOrder();
    }
}
