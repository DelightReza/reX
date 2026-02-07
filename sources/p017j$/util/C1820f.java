package p017j$.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import p017j$.util.Comparator;

/* renamed from: j$.util.f */
/* loaded from: classes2.dex */
public final class C1820f implements Comparator, Serializable, Comparator {
    private static final long serialVersionUID = -7569533591570686392L;

    /* renamed from: a */
    public final boolean f888a;

    /* renamed from: b */
    public final Comparator f889b;

    @Override // java.util.Comparator, p017j$.util.Comparator
    public final /* synthetic */ Comparator thenComparing(Function function) {
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

    public C1820f(boolean z, java.util.Comparator comparator) {
        this.f888a = z;
        this.f889b = comparator;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        if (obj == null) {
            if (obj2 == null) {
                return 0;
            }
            return this.f888a ? -1 : 1;
        }
        if (obj2 == null) {
            return this.f888a ? 1 : -1;
        }
        java.util.Comparator comparator = this.f889b;
        if (comparator == null) {
            return 0;
        }
        return comparator.compare(obj, obj2);
    }

    @Override // java.util.Comparator, p017j$.util.Comparator
    public final java.util.Comparator thenComparing(java.util.Comparator comparator) {
        Objects.requireNonNull(comparator);
        boolean z = this.f888a;
        java.util.Comparator comparator2 = this.f889b;
        if (comparator2 != null) {
            comparator = Comparator.EL.m853a(comparator2, comparator);
        }
        return new C1820f(z, comparator);
    }

    @Override // java.util.Comparator, p017j$.util.Comparator
    public final java.util.Comparator reversed() {
        boolean z = !this.f888a;
        java.util.Comparator comparator = this.f889b;
        return new C1820f(z, comparator == null ? null : Comparator.EL.reversed(comparator));
    }
}
