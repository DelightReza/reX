package com.google.common.collect;

import java.util.Set;
import java.util.function.Predicate;
import p017j$.util.function.Predicate$CC;

/* loaded from: classes.dex */
public final /* synthetic */ class Sets$2$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ Set f$0;

    public /* synthetic */ Sets$2$$ExternalSyntheticLambda0(Set set) {
        this.f$0 = set;
    }

    public /* synthetic */ Predicate and(Predicate predicate) {
        return Predicate$CC.$default$and(this, predicate);
    }

    public /* synthetic */ Predicate negate() {
        return Predicate$CC.$default$negate(this);
    }

    /* renamed from: or */
    public /* synthetic */ Predicate m448or(Predicate predicate) {
        return Predicate$CC.$default$or(this, predicate);
    }

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        return this.f$0.contains(obj);
    }
}
