package p017j$.util;

import p017j$.util.stream.AbstractC1890G3;
import p017j$.util.stream.IntStream;
import p017j$.util.stream.Stream;

/* loaded from: classes2.dex */
public final /* synthetic */ class DesugarArrays {
    /* renamed from: a */
    public static C1833h0 m854a(Object[] objArr, int i, int i2) {
        Spliterators.m859a(((Object[]) Objects.requireNonNull(objArr)).length, i, i2);
        return new C1833h0(objArr, i, i2, 1040);
    }

    public static <T> Stream<T> stream(T[] tArr) {
        return AbstractC1890G3.m961b(m854a(tArr, 0, tArr.length), false);
    }

    public static IntStream stream(int[] iArr) {
        return AbstractC1890G3.m960a(Spliterators.spliterator(iArr, 0, iArr.length, 1040));
    }
}
