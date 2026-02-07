package p017j$.util.function;

import java.util.function.IntUnaryOperator;

/* renamed from: j$.util.function.f */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1827f implements IntUnaryOperator {

    /* renamed from: a */
    public final /* synthetic */ int f905a;

    /* renamed from: b */
    public final /* synthetic */ IntUnaryOperator f906b;

    /* renamed from: c */
    public final /* synthetic */ IntUnaryOperator f907c;

    public /* synthetic */ C1827f(IntUnaryOperator intUnaryOperator, IntUnaryOperator intUnaryOperator2, int i) {
        this.f905a = i;
        this.f906b = intUnaryOperator;
        this.f907c = intUnaryOperator2;
    }

    public final /* synthetic */ IntUnaryOperator andThen(IntUnaryOperator intUnaryOperator) {
        switch (this.f905a) {
        }
        return IntUnaryOperator$CC.$default$andThen(this, intUnaryOperator);
    }

    public final /* synthetic */ IntUnaryOperator compose(IntUnaryOperator intUnaryOperator) {
        switch (this.f905a) {
        }
        return IntUnaryOperator$CC.$default$compose(this, intUnaryOperator);
    }

    @Override // java.util.function.IntUnaryOperator
    public final int applyAsInt(int i) {
        switch (this.f905a) {
            case 0:
                return this.f907c.applyAsInt(this.f906b.applyAsInt(i));
            default:
                return this.f906b.applyAsInt(this.f907c.applyAsInt(i));
        }
    }
}
