package p017j$.util.function;

import java.util.function.Function;

/* renamed from: j$.util.function.c */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1824c implements Function {

    /* renamed from: a */
    public final /* synthetic */ int f897a;

    /* renamed from: b */
    public final /* synthetic */ Function f898b;

    /* renamed from: c */
    public final /* synthetic */ Function f899c;

    public /* synthetic */ C1824c(Function function, Function function2, int i) {
        this.f897a = i;
        this.f898b = function;
        this.f899c = function2;
    }

    public final /* synthetic */ Function andThen(Function function) {
        switch (this.f897a) {
        }
        return Function$CC.$default$andThen(this, function);
    }

    public final /* synthetic */ Function compose(Function function) {
        switch (this.f897a) {
        }
        return Function$CC.$default$compose(this, function);
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.f897a) {
            case 0:
                return this.f899c.apply(this.f898b.apply(obj));
            default:
                return this.f898b.apply(this.f899c.apply(obj));
        }
    }
}
