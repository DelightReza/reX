package p017j$.util;

import java.util.List;
import java.util.RandomAccess;

/* renamed from: j$.util.k */
/* loaded from: classes2.dex */
public final class C1838k extends C1834i implements RandomAccess {
    private static final long serialVersionUID = 1530674583602358482L;

    @Override // p017j$.util.C1834i, java.util.List
    public final List subList(int i, int i2) {
        C1838k c1838k;
        synchronized (this.f917b) {
            c1838k = new C1838k(this.f922c.subList(i, i2), this.f917b);
        }
        return c1838k;
    }

    private Object writeReplace() {
        return new C1834i(this.f922c);
    }
}
