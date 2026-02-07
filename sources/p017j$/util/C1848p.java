package p017j$.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;

/* renamed from: j$.util.p */
/* loaded from: classes2.dex */
public class C1848p extends C1844n implements List, List {
    private static final long serialVersionUID = -283967356065247728L;

    /* renamed from: b */
    public final List f945b;

    public C1848p(List list) {
        super(list);
        this.f945b = list;
    }

    @Override // java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        return obj == this || this.f945b.equals(obj);
    }

    @Override // java.util.Collection, java.util.List
    public final int hashCode() {
        return this.f945b.hashCode();
    }

    @Override // java.util.List
    public final Object get(int i) {
        return this.f945b.get(i);
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final Object remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        return this.f945b.indexOf(obj);
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        return this.f945b.lastIndexOf(obj);
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, p017j$.util.List
    public final void replaceAll(UnaryOperator unaryOperator) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, p017j$.util.List
    public final void sort(Comparator comparator) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return new C1846o(this, 0);
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        return new C1846o(this, i);
    }

    @Override // java.util.List
    public List subList(int i, int i2) {
        return new C1848p(this.f945b.subList(i, i2));
    }

    private Object readResolve() {
        List list = this.f945b;
        return list instanceof RandomAccess ? new C2125u(list) : this;
    }
}
