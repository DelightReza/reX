package p017j$.util.concurrent;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* renamed from: j$.util.concurrent.h */
/* loaded from: classes2.dex */
public final class C1799h extends AbstractC1792a implements Iterator, Enumeration {

    /* renamed from: k */
    public final /* synthetic */ int f831k;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1799h(C1802k[] c1802kArr, int i, int i2, ConcurrentHashMap concurrentHashMap, int i3) {
        super(c1802kArr, i, i2, concurrentHashMap);
        this.f831k = i3;
    }

    @Override // java.util.Iterator
    public final Object next() {
        switch (this.f831k) {
            case 0:
                C1802k c1802k = this.f846b;
                if (c1802k == null) {
                    throw new NoSuchElementException();
                }
                Object obj = c1802k.f838b;
                this.f826j = c1802k;
                m892a();
                return obj;
            default:
                C1802k c1802k2 = this.f846b;
                if (c1802k2 == null) {
                    throw new NoSuchElementException();
                }
                Object obj2 = c1802k2.f839c;
                this.f826j = c1802k2;
                m892a();
                return obj2;
        }
    }

    @Override // java.util.Enumeration
    public final Object nextElement() {
        switch (this.f831k) {
        }
        return next();
    }
}
