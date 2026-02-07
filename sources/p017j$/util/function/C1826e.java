package p017j$.util.function;

import java.util.function.IntPredicate;

/* renamed from: j$.util.function.e */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1826e implements IntPredicate {

    /* renamed from: a */
    public final /* synthetic */ int f902a;

    /* renamed from: b */
    public final /* synthetic */ IntPredicate f903b;

    /* renamed from: c */
    public final /* synthetic */ IntPredicate f904c;

    public /* synthetic */ C1826e(IntPredicate intPredicate, IntPredicate intPredicate2, int i) {
        this.f902a = i;
        this.f903b = intPredicate;
        this.f904c = intPredicate2;
    }

    public final /* synthetic */ IntPredicate and(IntPredicate intPredicate) {
        switch (this.f902a) {
        }
        return IntPredicate$CC.$default$and(this, intPredicate);
    }

    public final /* synthetic */ IntPredicate negate() {
        switch (this.f902a) {
        }
        return IntPredicate$CC.$default$negate(this);
    }

    /* renamed from: or */
    public final /* synthetic */ IntPredicate m908or(IntPredicate intPredicate) {
        switch (this.f902a) {
        }
        return IntPredicate$CC.$default$or(this, intPredicate);
    }

    @Override // java.util.function.IntPredicate
    public final boolean test(int i) {
        switch (this.f902a) {
            case 0:
                return this.f903b.test(i) || this.f904c.test(i);
            default:
                return this.f903b.test(i) && this.f904c.test(i);
        }
    }
}
