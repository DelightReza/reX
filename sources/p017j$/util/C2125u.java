package p017j$.util;

import java.util.List;
import java.util.RandomAccess;

/* renamed from: j$.util.u */
/* loaded from: classes2.dex */
public final class C2125u extends C1848p implements RandomAccess {
    private static final long serialVersionUID = -2542308836966382001L;

    @Override // p017j$.util.C1848p, java.util.List
    public final List subList(int i, int i2) {
        return new C2125u(this.f945b.subList(i, i2));
    }

    private Object writeReplace() {
        return new C1848p(this.f945b);
    }
}
