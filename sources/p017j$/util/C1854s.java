package p017j$.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import p017j$.time.C1726t;
import p017j$.util.Collection;
import p017j$.util.stream.AbstractC1890G3;
import p017j$.util.stream.Stream;

/* renamed from: j$.util.s */
/* loaded from: classes2.dex */
public final class C1854s extends C2126v {
    private static final long serialVersionUID = 7854390611657943733L;

    @Override // p017j$.util.C1844n, java.lang.Iterable, p017j$.util.Collection, p017j$.lang.InterfaceC1637a
    public final void forEach(Consumer consumer) {
        Objects.requireNonNull(consumer);
        Collection.EL.m852a(this.f934a, new C1726t(1, consumer));
    }

    @Override // p017j$.util.C1844n, java.util.Collection, java.lang.Iterable, p017j$.util.Collection
    public final Spliterator spliterator() {
        return new C1852r(Collection.EL.spliterator(this.f934a));
    }

    @Override // p017j$.util.C1844n, java.util.Collection, p017j$.util.Collection
    public final Stream stream() {
        return AbstractC1890G3.m961b(spliterator(), false);
    }

    @Override // p017j$.util.C1844n, java.util.Collection, p017j$.util.Collection
    public final Stream parallelStream() {
        return AbstractC1890G3.m961b(spliterator(), true);
    }

    @Override // p017j$.util.C1844n, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return new C1842m(this);
    }

    @Override // p017j$.util.C1844n, java.util.Collection
    public final Object[] toArray() {
        Object[] array = this.f934a.toArray();
        for (int i = 0; i < array.length; i++) {
            array[i] = new C1850q((Map.Entry) array[i]);
        }
        return array;
    }

    @Override // p017j$.util.C1844n, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        Object[] array = this.f934a.toArray(objArr.length == 0 ? objArr : Arrays.copyOf(objArr, 0));
        for (int i = 0; i < array.length; i++) {
            array[i] = new C1850q((Map.Entry) array[i]);
        }
        if (array.length > objArr.length) {
            return array;
        }
        System.arraycopy(array, 0, objArr, 0, array.length);
        if (objArr.length > array.length) {
            objArr[array.length] = null;
        }
        return objArr;
    }

    @Override // p017j$.util.C1844n, java.util.Collection
    public final boolean contains(Object obj) {
        if (obj instanceof Map.Entry) {
            return this.f934a.contains(new C1850q((Map.Entry) obj));
        }
        return false;
    }

    @Override // p017j$.util.C1844n, java.util.Collection
    public final boolean containsAll(java.util.Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    @Override // p017j$.util.C2126v, java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (set.size() != this.f934a.size()) {
            return false;
        }
        return containsAll(set);
    }
}
