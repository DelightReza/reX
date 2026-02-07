package p017j$.util;

import java.util.ListIterator;
import java.util.function.Consumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.o */
/* loaded from: classes2.dex */
public final class C1846o implements ListIterator, InterfaceC2129y {

    /* renamed from: a */
    public final ListIterator f939a;

    public C1846o(C1848p c1848p, int i) {
        this.f939a = c1848p.f945b.listIterator(i);
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final boolean hasNext() {
        return this.f939a.hasNext();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final Object next() {
        return this.f939a.next();
    }

    @Override // java.util.ListIterator
    public final boolean hasPrevious() {
        return this.f939a.hasPrevious();
    }

    @Override // java.util.ListIterator
    public final Object previous() {
        return this.f939a.previous();
    }

    @Override // java.util.ListIterator
    public final int nextIndex() {
        return this.f939a.nextIndex();
    }

    @Override // java.util.ListIterator
    public final int previousIndex() {
        return this.f939a.previousIndex();
    }

    @Override // java.util.ListIterator, java.util.Iterator
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final void set(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.ListIterator
    public final void add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Iterator, p017j$.util.InterfaceC2129y
    public final void forEachRemaining(Consumer consumer) {
        AbstractC1636a.m491J(this.f939a, consumer);
    }
}
