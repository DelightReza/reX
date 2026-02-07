package p017j$.util;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import p017j$.util.Collection;
import p017j$.util.Spliterator;
import p017j$.util.stream.Stream;

/* renamed from: j$.util.h */
/* loaded from: classes2.dex */
public class C1832h implements Collection, Serializable, Collection {
    private static final long serialVersionUID = 3053995032091335093L;

    /* renamed from: a */
    public final Collection f916a;

    /* renamed from: b */
    public final Object f917b;

    @Override // java.util.Collection, p017j$.util.Collection
    public final /* synthetic */ Object[] toArray(IntFunction intFunction) {
        return toArray((Object[]) intFunction.apply(0));
    }

    public C1832h(Collection collection) {
        this.f916a = (Collection) Objects.requireNonNull(collection);
        this.f917b = this;
    }

    public C1832h(Collection collection, Object obj) {
        this.f916a = (Collection) Objects.requireNonNull(collection);
        this.f917b = Objects.requireNonNull(obj);
    }

    @Override // java.util.Collection
    public final int size() {
        int size;
        synchronized (this.f917b) {
            size = this.f916a.size();
        }
        return size;
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        boolean zIsEmpty;
        synchronized (this.f917b) {
            zIsEmpty = this.f916a.isEmpty();
        }
        return zIsEmpty;
    }

    @Override // java.util.Collection
    public final boolean contains(Object obj) {
        boolean zContains;
        synchronized (this.f917b) {
            zContains = this.f916a.contains(obj);
        }
        return zContains;
    }

    @Override // java.util.Collection
    public final Object[] toArray() {
        Object[] array;
        synchronized (this.f917b) {
            array = this.f916a.toArray();
        }
        return array;
    }

    @Override // java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        Object[] array;
        synchronized (this.f917b) {
            array = this.f916a.toArray(objArr);
        }
        return array;
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return this.f916a.iterator();
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        boolean zAdd;
        synchronized (this.f917b) {
            zAdd = this.f916a.add(obj);
        }
        return zAdd;
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        boolean zRemove;
        synchronized (this.f917b) {
            zRemove = this.f916a.remove(obj);
        }
        return zRemove;
    }

    @Override // java.util.Collection
    public final boolean containsAll(Collection collection) {
        boolean zContainsAll;
        synchronized (this.f917b) {
            zContainsAll = this.f916a.containsAll(collection);
        }
        return zContainsAll;
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        boolean zAddAll;
        synchronized (this.f917b) {
            zAddAll = this.f916a.addAll(collection);
        }
        return zAddAll;
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        boolean zRemoveAll;
        synchronized (this.f917b) {
            zRemoveAll = this.f916a.removeAll(collection);
        }
        return zRemoveAll;
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        boolean zRetainAll;
        synchronized (this.f917b) {
            zRetainAll = this.f916a.retainAll(collection);
        }
        return zRetainAll;
    }

    @Override // java.util.Collection
    public final void clear() {
        synchronized (this.f917b) {
            this.f916a.clear();
        }
    }

    public final String toString() {
        String string;
        synchronized (this.f917b) {
            string = this.f916a.toString();
        }
        return string;
    }

    @Override // java.lang.Iterable, p017j$.util.Collection, p017j$.lang.InterfaceC1637a
    public final void forEach(Consumer consumer) {
        synchronized (this.f917b) {
            Collection.EL.m852a(this.f916a, consumer);
        }
    }

    @Override // java.util.Collection, p017j$.util.Collection
    public final boolean removeIf(Predicate predicate) {
        boolean zRemoveIf;
        synchronized (this.f917b) {
            zRemoveIf = Collection.EL.removeIf(this.f916a, predicate);
        }
        return zRemoveIf;
    }

    @Override // java.util.Collection, java.lang.Iterable, p017j$.util.Collection
    public final Spliterator spliterator() {
        return Collection.EL.spliterator(this.f916a);
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final Spliterator spliterator() {
        return Spliterator.Wrapper.convert(Collection.EL.spliterator(this.f916a));
    }

    @Override // java.util.Collection, p017j$.util.Collection
    public final Stream stream() {
        return Collection.EL.stream(this.f916a);
    }

    @Override // java.util.Collection
    public final java.util.stream.Stream stream() {
        return Stream.Wrapper.convert(Collection.EL.stream(this.f916a));
    }

    @Override // java.util.Collection, p017j$.util.Collection
    public final Stream parallelStream() {
        return Collection.EL.parallelStream(this.f916a);
    }

    @Override // java.util.Collection
    public final java.util.stream.Stream parallelStream() {
        return Stream.Wrapper.convert(Collection.EL.parallelStream(this.f916a));
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        synchronized (this.f917b) {
            objectOutputStream.defaultWriteObject();
        }
    }
}
