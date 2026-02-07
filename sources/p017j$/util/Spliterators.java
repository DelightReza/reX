package p017j$.util;

import java.util.Collection;
import p017j$.util.Spliterator;

/* loaded from: classes2.dex */
public final class Spliterators {

    /* renamed from: a */
    public static final C1843m0 f786a = new C1843m0();

    /* renamed from: b */
    public static final C1839k0 f787b = new C1839k0();

    /* renamed from: c */
    public static final C1841l0 f788c = new C1841l0();

    /* renamed from: d */
    public static final C1837j0 f789d = new C1837j0();

    public static <T> Spliterator<T> spliterator(Object[] objArr, int i) {
        Object[] objArr2 = (Object[]) Objects.requireNonNull(objArr);
        return new C1833h0(objArr2, 0, objArr2.length, i);
    }

    public static Spliterator.OfInt spliterator(int[] iArr, int i, int i2, int i3) {
        m859a(((int[]) Objects.requireNonNull(iArr)).length, i, i2);
        return new C1845n0(iArr, i, i2, i3);
    }

    /* renamed from: a */
    public static void m859a(int i, int i2, int i3) {
        if (i2 <= i3) {
            if (i2 < 0) {
                throw new ArrayIndexOutOfBoundsException(i2);
            }
            if (i3 > i) {
                throw new ArrayIndexOutOfBoundsException(i3);
            }
            return;
        }
        throw new ArrayIndexOutOfBoundsException("origin(" + i2 + ") > fence(" + i3 + ")");
    }

    public static <T> Spliterator<T> spliterator(Collection<? extends T> collection, int i) {
        return new C1847o0((Collection) Objects.requireNonNull(collection), i);
    }
}
