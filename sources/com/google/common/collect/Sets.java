package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import p017j$.lang.Iterable$CC;
import p017j$.util.Collection;
import p017j$.util.Objects;
import p017j$.util.Set;
import p017j$.util.Spliterator;
import p017j$.util.stream.Stream;

/* loaded from: classes4.dex */
public abstract class Sets {

    /* renamed from: com.google.common.collect.Sets$1 */
    abstract class AbstractC12701 extends SetView {
    }

    static abstract class ImprovedAbstractSet extends AbstractSet {
        ImprovedAbstractSet() {
        }

        @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean removeAll(Collection collection) {
            return Sets.removeAllImpl(this, collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public boolean retainAll(Collection collection) {
            return super.retainAll((Collection) Preconditions.checkNotNull(collection));
        }
    }

    public static HashSet newHashSet() {
        return new HashSet();
    }

    public static HashSet newHashSet(Object... objArr) {
        HashSet hashSetNewHashSetWithExpectedSize = newHashSetWithExpectedSize(objArr.length);
        Collections.addAll(hashSetNewHashSetWithExpectedSize, objArr);
        return hashSetNewHashSetWithExpectedSize;
    }

    public static HashSet newHashSetWithExpectedSize(int i) {
        return new HashSet(Maps.capacity(i));
    }

    public static Set newIdentityHashSet() {
        return Collections.newSetFromMap(Maps.newIdentityHashMap());
    }

    /* loaded from: classes.dex */
    public static abstract class SetView extends AbstractSet implements p017j$.util.Set {
        @Override // java.lang.Iterable, p017j$.util.Collection, p017j$.lang.InterfaceC1637a
        public /* synthetic */ void forEach(Consumer consumer) {
            Iterable$CC.$default$forEach(this, consumer);
        }

        @Override // java.util.Collection, p017j$.util.Collection
        public /* synthetic */ Stream parallelStream() {
            return Collection.CC.$default$parallelStream(this);
        }

        @Override // java.util.Collection
        public /* synthetic */ java.util.stream.Stream parallelStream() {
            return Stream.Wrapper.convert(parallelStream());
        }

        @Override // java.util.Collection, java.lang.Iterable, java.util.Set, p017j$.util.Set, p017j$.util.Collection
        public /* synthetic */ Spliterator spliterator() {
            return Set.CC.$default$spliterator(this);
        }

        @Override // java.util.Collection, java.lang.Iterable, java.util.Set
        public /* synthetic */ java.util.Spliterator spliterator() {
            return Spliterator.Wrapper.convert(spliterator());
        }

        @Override // java.util.Collection, p017j$.util.Collection
        public /* synthetic */ Stream stream() {
            return Collection.CC.$default$stream(this);
        }

        @Override // java.util.Collection
        public /* synthetic */ java.util.stream.Stream stream() {
            return Stream.Wrapper.convert(stream());
        }

        @Override // java.util.Collection, p017j$.util.Collection
        public /* synthetic */ Object[] toArray(IntFunction intFunction) {
            return toArray((Object[]) intFunction.apply(0));
        }

        /* synthetic */ SetView(AbstractC12701 abstractC12701) {
            this();
        }

        private SetView() {
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean add(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean remove(Object obj) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean addAll(java.util.Collection collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractSet, java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean removeAll(java.util.Collection collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.Collection, p017j$.util.Collection
        public final boolean removeIf(Predicate predicate) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final boolean retainAll(java.util.Collection collection) {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public final void clear() {
            throw new UnsupportedOperationException();
        }
    }

    public static SetView intersection(final java.util.Set set, final java.util.Set set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView() { // from class: com.google.common.collect.Sets.2
            @Override // com.google.common.collect.Sets.SetView, java.util.Collection
            public /* synthetic */ java.util.stream.Stream parallelStream() {
                return Stream.Wrapper.convert(parallelStream());
            }

            @Override // com.google.common.collect.Sets.SetView, java.util.Collection
            public /* synthetic */ java.util.stream.Stream stream() {
                return Stream.Wrapper.convert(stream());
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(null);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public UnmodifiableIterator iterator() {
                return new AbstractIterator() { // from class: com.google.common.collect.Sets.2.1
                    final Iterator itr;

                    {
                        this.itr = set.iterator();
                    }

                    @Override // com.google.common.collect.AbstractIterator
                    protected Object computeNext() {
                        while (this.itr.hasNext()) {
                            Object next = this.itr.next();
                            if (set2.contains(next)) {
                                return next;
                            }
                        }
                        return endOfData();
                    }
                };
            }

            @Override // com.google.common.collect.Sets.SetView, java.util.Collection, p017j$.util.Collection
            public Stream stream() {
                Stream stream = Collection.EL.stream(set);
                java.util.Set set3 = set2;
                Objects.requireNonNull(set3);
                return stream.filter(new Sets$2$$ExternalSyntheticLambda0(set3));
            }

            @Override // com.google.common.collect.Sets.SetView, java.util.Collection, p017j$.util.Collection
            public Stream parallelStream() {
                Stream streamParallelStream = Collection.EL.parallelStream(set);
                java.util.Set set3 = set2;
                Objects.requireNonNull(set3);
                return streamParallelStream.filter(new Sets$2$$ExternalSyntheticLambda0(set3));
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                Iterator it = set.iterator();
                int i = 0;
                while (it.hasNext()) {
                    if (set2.contains(it.next())) {
                        i++;
                    }
                }
                return i;
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean isEmpty() {
                return Collections.disjoint(set2, set);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean contains(Object obj) {
                return set.contains(obj) && set2.contains(obj);
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean containsAll(java.util.Collection collection) {
                return set.containsAll(collection) && set2.containsAll(collection);
            }
        };
    }

    public static java.util.Set filter(java.util.Set set, com.google.common.base.Predicate predicate) {
        if (set instanceof SortedSet) {
            return filter((SortedSet) set, predicate);
        }
        if (set instanceof FilteredSet) {
            FilteredSet filteredSet = (FilteredSet) set;
            return new FilteredSet((java.util.Set) filteredSet.unfiltered, Predicates.and(filteredSet.predicate, predicate));
        }
        return new FilteredSet((java.util.Set) Preconditions.checkNotNull(set), (com.google.common.base.Predicate) Preconditions.checkNotNull(predicate));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static SortedSet filter(SortedSet sortedSet, com.google.common.base.Predicate predicate) {
        if (sortedSet instanceof FilteredSet) {
            FilteredSet filteredSet = (FilteredSet) sortedSet;
            return new FilteredSortedSet((SortedSet) filteredSet.unfiltered, Predicates.and(filteredSet.predicate, predicate));
        }
        return new FilteredSortedSet((SortedSet) Preconditions.checkNotNull(sortedSet), (com.google.common.base.Predicate) Preconditions.checkNotNull(predicate));
    }

    private static class FilteredSet extends Collections2.FilteredCollection implements java.util.Set, p017j$.util.Set {
        FilteredSet(java.util.Set set, com.google.common.base.Predicate predicate) {
            super(set, predicate);
        }

        @Override // java.util.Collection, java.util.Set
        public boolean equals(Object obj) {
            return Sets.equalsImpl(this, obj);
        }

        @Override // java.util.Collection, java.util.Set
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }

    private static class FilteredSortedSet extends FilteredSet implements SortedSet, p017j$.util.SortedSet {
        FilteredSortedSet(SortedSet sortedSet, com.google.common.base.Predicate predicate) {
            super(sortedSet, predicate);
        }

        @Override // java.util.SortedSet
        public Comparator comparator() {
            return ((SortedSet) this.unfiltered).comparator();
        }

        @Override // java.util.SortedSet
        public SortedSet subSet(Object obj, Object obj2) {
            return new FilteredSortedSet(((SortedSet) this.unfiltered).subSet(obj, obj2), this.predicate);
        }

        @Override // java.util.SortedSet
        public SortedSet headSet(Object obj) {
            return new FilteredSortedSet(((SortedSet) this.unfiltered).headSet(obj), this.predicate);
        }

        @Override // java.util.SortedSet
        public SortedSet tailSet(Object obj) {
            return new FilteredSortedSet(((SortedSet) this.unfiltered).tailSet(obj), this.predicate);
        }

        @Override // java.util.SortedSet
        public Object first() {
            return Iterators.find(this.unfiltered.iterator(), this.predicate);
        }

        @Override // java.util.SortedSet
        public Object last() {
            SortedSet sortedSetHeadSet = (SortedSet) this.unfiltered;
            while (true) {
                Object objLast = sortedSetHeadSet.last();
                if (this.predicate.apply(objLast)) {
                    return objLast;
                }
                sortedSetHeadSet = sortedSetHeadSet.headSet(objLast);
            }
        }
    }

    static int hashCodeImpl(java.util.Set set) {
        Iterator it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            Object next = it.next();
            i = ~(~(i + (next != null ? next.hashCode() : 0)));
        }
        return i;
    }

    static boolean equalsImpl(java.util.Set set, Object obj) {
        if (set == obj) {
            return true;
        }
        if (obj instanceof java.util.Set) {
            java.util.Set set2 = (java.util.Set) obj;
            try {
                if (set.size() == set2.size()) {
                    if (set.containsAll(set2)) {
                        return true;
                    }
                }
            } catch (ClassCastException | NullPointerException unused) {
            }
        }
        return false;
    }

    static boolean removeAllImpl(java.util.Set set, Iterator it) {
        boolean zRemove = false;
        while (it.hasNext()) {
            zRemove |= set.remove(it.next());
        }
        return zRemove;
    }

    static boolean removeAllImpl(java.util.Set set, java.util.Collection collection) {
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            collection = ((Multiset) collection).elementSet();
        }
        if ((collection instanceof java.util.Set) && collection.size() > set.size()) {
            return Iterators.removeAll(set.iterator(), collection);
        }
        return removeAllImpl(set, collection.iterator());
    }
}
