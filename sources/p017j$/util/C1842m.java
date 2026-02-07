package p017j$.util;

import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.C1726t;

/* renamed from: j$.util.m */
/* loaded from: classes2.dex */
public final class C1842m implements Iterator, InterfaceC2129y {

    /* renamed from: a */
    public final /* synthetic */ int f932a = 0;

    /* renamed from: b */
    public final Iterator f933b;

    public C1842m(C1844n c1844n) {
        this.f933b = c1844n.f934a.iterator();
    }

    public C1842m(C1854s c1854s) {
        this.f933b = c1854s.f934a.iterator();
    }

    @Override // java.util.Iterator
    public final boolean hasNext() {
        switch (this.f932a) {
        }
        return this.f933b.hasNext();
    }

    @Override // java.util.Iterator
    public final Object next() {
        switch (this.f932a) {
            case 0:
                return this.f933b.next();
            default:
                return new C1850q((Map.Entry) this.f933b.next());
        }
    }

    @Override // java.util.Iterator
    public final void remove() {
        switch (this.f932a) {
            case 0:
                throw new UnsupportedOperationException();
            default:
                throw new UnsupportedOperationException();
        }
    }

    @Override // java.util.Iterator, p017j$.util.InterfaceC2129y
    public final void forEachRemaining(Consumer consumer) {
        switch (this.f932a) {
            case 0:
                AbstractC1636a.m491J(this.f933b, consumer);
                break;
            default:
                AbstractC1636a.m491J(this.f933b, new C1726t(1, consumer));
                break;
        }
    }
}
