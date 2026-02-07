package p017j$.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import p017j$.util.Collection;
import p017j$.util.Spliterator;
import p017j$.util.stream.Stream;

/* renamed from: j$.util.n */
/* loaded from: classes2.dex */
public class C1844n implements Collection, Serializable, Collection {
    private static final long serialVersionUID = 1820017752578914078L;

    /* renamed from: a */
    public final Collection f934a;

    @Override // java.util.Collection
    public final /* synthetic */ Stream parallelStream() {
        return Stream.Wrapper.convert(parallelStream());
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Spliterator spliterator() {
        return Spliterator.Wrapper.convert(spliterator());
    }

    @Override // java.util.Collection
    public final /* synthetic */ java.util.stream.Stream stream() {
        return Stream.Wrapper.convert(stream());
    }

    @Override // java.util.Collection, p017j$.util.Collection
    public final /* synthetic */ Object[] toArray(IntFunction intFunction) {
        return toArray((Object[]) intFunction.apply(0));
    }

    public C1844n(Collection collection) {
        collection.getClass();
        this.f934a = collection;
    }

    @Override // java.util.Collection
    public final int size() {
        return this.f934a.size();
    }

    @Override // java.util.Collection
    public final boolean isEmpty() {
        return this.f934a.isEmpty();
    }

    @Override // java.util.Collection
    public boolean contains(Object obj) {
        return this.f934a.contains(obj);
    }

    @Override // java.util.Collection
    public Object[] toArray() {
        return this.f934a.toArray();
    }

    @Override // java.util.Collection
    public Object[] toArray(Object[] objArr) {
        return this.f934a.toArray(objArr);
    }

    public final String toString() {
        return this.f934a.toString();
    }

    @Override // java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return new C1842m(this);
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public boolean containsAll(Collection collection) {
        return this.f934a.containsAll(collection);
    }

    @Override // java.util.Collection
    public final boolean addAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean removeAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean retainAll(Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.lang.Iterable, p017j$.util.Collection, p017j$.lang.InterfaceC1637a
    public void forEach(Consumer consumer) {
        Collection.EL.m852a(this.f934a, consumer);
    }

    @Override // java.util.Collection, p017j$.util.Collection
    public final boolean removeIf(Predicate predicate) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection, java.lang.Iterable, p017j$.util.Collection
    public Spliterator spliterator() {
        return Collection.EL.spliterator(this.f934a);
    }

    @Override // java.util.Collection, p017j$.util.Collection
    public p017j$.util.stream.Stream stream() {
        return Collection.EL.stream(this.f934a);
    }

    @Override // java.util.Collection, p017j$.util.Collection
    public p017j$.util.stream.Stream parallelStream() {
        return Collection.EL.parallelStream(this.f934a);
    }
}
