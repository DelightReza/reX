package p017j$.util.concurrent;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* renamed from: j$.util.concurrent.d */
/* loaded from: classes2.dex */
public final class C1795d extends AbstractC1792a implements Iterator {
    @Override // java.util.Iterator
    public final Object next() {
        C1802k c1802k = this.f846b;
        if (c1802k == null) {
            throw new NoSuchElementException();
        }
        Object obj = c1802k.f838b;
        Object obj2 = c1802k.f839c;
        this.f826j = c1802k;
        m892a();
        return new C1801j(obj, obj2, this.f825i);
    }
}
