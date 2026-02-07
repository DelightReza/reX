package p017j$.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* renamed from: j$.util.c */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1790c implements Comparator, Serializable {

    /* renamed from: a */
    public final /* synthetic */ int f799a;

    /* renamed from: b */
    public final /* synthetic */ Object f800b;

    public /* synthetic */ C1790c(int i, Object obj) {
        this.f799a = i;
        this.f800b = obj;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.f799a) {
            case 0:
                ToIntFunction toIntFunction = (ToIntFunction) this.f800b;
                return Integer.compare(toIntFunction.applyAsInt(obj), toIntFunction.applyAsInt(obj2));
            case 1:
                ToDoubleFunction toDoubleFunction = (ToDoubleFunction) this.f800b;
                return Double.compare(toDoubleFunction.applyAsDouble(obj), toDoubleFunction.applyAsDouble(obj2));
            case 2:
                Function function = (Function) this.f800b;
                return ((Comparable) function.apply(obj)).compareTo(function.apply(obj2));
            default:
                ToLongFunction toLongFunction = (ToLongFunction) this.f800b;
                return Long.compare(toLongFunction.applyAsLong(obj), toLongFunction.applyAsLong(obj2));
        }
    }
}
