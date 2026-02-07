package p017j$.util.function;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/* renamed from: j$.util.function.a */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1822a implements BinaryOperator {

    /* renamed from: a */
    public final /* synthetic */ int f893a;

    /* renamed from: b */
    public final /* synthetic */ Comparator f894b;

    public /* synthetic */ C1822a(Comparator comparator, int i) {
        this.f893a = i;
        this.f894b = comparator;
    }

    public final /* synthetic */ BiFunction andThen(Function function) {
        switch (this.f893a) {
        }
        return BiFunction$CC.$default$andThen(this, function);
    }

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        switch (this.f893a) {
            case 0:
                if (this.f894b.compare(obj, obj2) < 0) {
                    break;
                }
                break;
            default:
                if (this.f894b.compare(obj, obj2) > 0) {
                    break;
                }
                break;
        }
        return obj2;
    }
}
