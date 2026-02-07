package p017j$.util.function;

import java.util.function.Predicate;

/* renamed from: j$.util.function.h */
/* loaded from: classes2.dex */
public final /* synthetic */ class C1829h implements Predicate {

    /* renamed from: a */
    public final /* synthetic */ int f910a;

    /* renamed from: b */
    public final /* synthetic */ Predicate f911b;

    /* renamed from: c */
    public final /* synthetic */ Predicate f912c;

    public /* synthetic */ C1829h(Predicate predicate, Predicate predicate2, int i) {
        this.f910a = i;
        this.f911b = predicate;
        this.f912c = predicate2;
    }

    public final /* synthetic */ Predicate and(Predicate predicate) {
        switch (this.f910a) {
        }
        return Predicate$CC.$default$and(this, predicate);
    }

    public final /* synthetic */ Predicate negate() {
        switch (this.f910a) {
        }
        return Predicate$CC.$default$negate(this);
    }

    /* renamed from: or */
    public final /* synthetic */ Predicate m909or(Predicate predicate) {
        switch (this.f910a) {
        }
        return Predicate$CC.$default$or(this, predicate);
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        switch (this.f910a) {
            case 0:
                return this.f911b.test(obj) && this.f912c.test(obj);
            default:
                return this.f911b.test(obj) || this.f912c.test(obj);
        }
    }
}
