package p017j$.util.stream;

import p017j$.util.Objects;
import p017j$.util.Spliterator;

/* renamed from: j$.util.stream.G3 */
/* loaded from: classes2.dex */
public abstract class AbstractC1890G3 {
    /* renamed from: b */
    public static C2000c2 m961b(Spliterator spliterator, boolean z) {
        Objects.requireNonNull(spliterator);
        return new C2000c2(spliterator, EnumC1995b3.m1028k(spliterator), z);
    }

    /* renamed from: a */
    public static C1980Z m960a(Spliterator.OfInt ofInt) {
        return new C1980Z(ofInt, EnumC1995b3.m1028k(ofInt), false);
    }
}
