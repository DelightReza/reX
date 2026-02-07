package p017j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import p017j$.util.function.BiFunction$CC;

/* renamed from: j$.util.stream.p */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2069p implements BinaryOperator {

    /* renamed from: a */
    public final /* synthetic */ int f1308a;

    /* renamed from: b */
    public final /* synthetic */ BiConsumer f1309b;

    public /* synthetic */ C2069p(BiConsumer biConsumer, int i) {
        this.f1308a = i;
        this.f1309b = biConsumer;
    }

    public final /* synthetic */ BiFunction andThen(Function function) {
        switch (this.f1308a) {
        }
        return BiFunction$CC.$default$andThen(this, function);
    }

    @Override // java.util.function.BiFunction
    public final Object apply(Object obj, Object obj2) {
        switch (this.f1308a) {
            case 0:
                this.f1309b.accept(obj, obj2);
                break;
            case 1:
                this.f1309b.accept(obj, obj2);
                break;
            default:
                this.f1309b.accept(obj, obj2);
                break;
        }
        return obj;
    }
}
