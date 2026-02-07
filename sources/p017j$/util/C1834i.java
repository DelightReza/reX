package p017j$.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;
import p017j$.util.List;

/* renamed from: j$.util.i */
/* loaded from: classes2.dex */
public class C1834i extends C1832h implements List, List {
    private static final long serialVersionUID = -7754090372962971524L;

    /* renamed from: c */
    public final List f922c;

    public C1834i(List list) {
        super(list);
        this.f922c = list;
    }

    public C1834i(List list, Object obj) {
        super(list, obj);
        this.f922c = list;
    }

    @Override // java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        boolean zEquals;
        if (this == obj) {
            return true;
        }
        synchronized (this.f917b) {
            zEquals = this.f922c.equals(obj);
        }
        return zEquals;
    }

    @Override // java.util.Collection, java.util.List
    public final int hashCode() {
        int iHashCode;
        synchronized (this.f917b) {
            iHashCode = this.f922c.hashCode();
        }
        return iHashCode;
    }

    @Override // java.util.List
    public final Object get(int i) {
        Object obj;
        synchronized (this.f917b) {
            obj = this.f922c.get(i);
        }
        return obj;
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        Object obj2;
        synchronized (this.f917b) {
            obj2 = this.f922c.set(i, obj);
        }
        return obj2;
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        synchronized (this.f917b) {
            this.f922c.add(i, obj);
        }
    }

    @Override // java.util.List
    public final Object remove(int i) {
        Object objRemove;
        synchronized (this.f917b) {
            objRemove = this.f922c.remove(i);
        }
        return objRemove;
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        int iIndexOf;
        synchronized (this.f917b) {
            iIndexOf = this.f922c.indexOf(obj);
        }
        return iIndexOf;
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        int iLastIndexOf;
        synchronized (this.f917b) {
            iLastIndexOf = this.f922c.lastIndexOf(obj);
        }
        return iLastIndexOf;
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        boolean zAddAll;
        synchronized (this.f917b) {
            zAddAll = this.f922c.addAll(i, collection);
        }
        return zAddAll;
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return this.f922c.listIterator();
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        return this.f922c.listIterator(i);
    }

    @Override // java.util.List
    public List subList(int i, int i2) {
        C1834i c1834i;
        synchronized (this.f917b) {
            c1834i = new C1834i(this.f922c.subList(i, i2), this.f917b);
        }
        return c1834i;
    }

    @Override // java.util.List, p017j$.util.List
    public final void replaceAll(UnaryOperator unaryOperator) {
        synchronized (this.f917b) {
            List list = this.f922c;
            if (list instanceof List) {
                ((List) list).replaceAll(unaryOperator);
            } else {
                List.CC.$default$replaceAll(list, unaryOperator);
            }
        }
    }

    @Override // java.util.List, p017j$.util.List
    public final void sort(Comparator comparator) {
        synchronized (this.f917b) {
            List.EL.sort(this.f922c, comparator);
        }
    }

    private Object readResolve() {
        java.util.List list = this.f922c;
        return list instanceof RandomAccess ? new C1838k(list) : this;
    }
}
