package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import p017j$.lang.Iterable$EL;
import p017j$.util.Collection;
import p017j$.util.Objects;
import p017j$.util.Spliterator;
import p017j$.util.function.Consumer$CC;
import p017j$.util.function.Predicate$CC;
import p017j$.util.stream.Stream;

/* loaded from: classes4.dex */
public abstract class Collections2 {
    static boolean safeContains(Collection collection, Object obj) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.contains(obj);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

    /* loaded from: classes.dex */
    static class FilteredCollection extends AbstractCollection implements p017j$.util.Collection {
        final Predicate predicate;
        final Collection unfiltered;

        @Override // java.util.Collection, p017j$.util.Collection
        public /* synthetic */ Stream parallelStream() {
            return Collection.CC.$default$parallelStream(this);
        }

        @Override // java.util.Collection
        public /* synthetic */ java.util.stream.Stream parallelStream() {
            return Stream.Wrapper.convert(parallelStream());
        }

        @Override // java.util.Collection, java.lang.Iterable
        public /* synthetic */ Spliterator spliterator() {
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

        FilteredCollection(java.util.Collection collection, Predicate predicate) {
            this.unfiltered = collection;
            this.predicate = predicate;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean add(Object obj) {
            Preconditions.checkArgument(this.predicate.apply(obj));
            return this.unfiltered.add(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean addAll(java.util.Collection collection) {
            Iterator it = collection.iterator();
            while (it.hasNext()) {
                Preconditions.checkArgument(this.predicate.apply(it.next()));
            }
            return this.unfiltered.addAll(collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public void clear() {
            Iterables.removeIf(this.unfiltered, this.predicate);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean contains(Object obj) {
            if (Collections2.safeContains(this.unfiltered, obj)) {
                return this.predicate.apply(obj);
            }
            return false;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean containsAll(java.util.Collection collection) {
            return Collections2.containsAllImpl(this, collection);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean isEmpty() {
            return !Iterables.any(this.unfiltered, this.predicate);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
        public Iterator iterator() {
            return Iterators.filter(this.unfiltered.iterator(), this.predicate);
        }

        @Override // java.util.Collection, java.lang.Iterable, p017j$.util.Collection
        public p017j$.util.Spliterator spliterator() {
            return CollectSpliterators.filter(Collection.EL.spliterator(this.unfiltered), this.predicate);
        }

        @Override // java.lang.Iterable, p017j$.util.Collection, p017j$.lang.InterfaceC1637a
        public void forEach(final Consumer consumer) {
            Preconditions.checkNotNull(consumer);
            Iterable$EL.forEach(this.unfiltered, new Consumer() { // from class: com.google.common.collect.Collections2$FilteredCollection$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                /* renamed from: accept */
                public final void m971v(Object obj) {
                    Collections2.FilteredCollection.m2721$r8$lambda$KdHSErvdvqiUIyx9i6Ctzd8nGU(this.f$0, consumer, obj);
                }

                public /* synthetic */ Consumer andThen(Consumer consumer2) {
                    return Consumer$CC.$default$andThen(this, consumer2);
                }
            });
        }

        /* renamed from: $r8$lambda$KdHSErvdvqiUI-yx9i6Ctzd8nGU, reason: not valid java name */
        public static /* synthetic */ void m2721$r8$lambda$KdHSErvdvqiUIyx9i6Ctzd8nGU(FilteredCollection filteredCollection, Consumer consumer, Object obj) {
            if (filteredCollection.predicate.test(obj)) {
                consumer.m971v(obj);
            }
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean remove(Object obj) {
            return contains(obj) && this.unfiltered.remove(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean removeAll(final java.util.Collection collection) {
            Objects.requireNonNull(collection);
            return removeIf(new java.util.function.Predicate() { // from class: com.google.common.collect.Collections2$FilteredCollection$$ExternalSyntheticLambda0
                public /* synthetic */ java.util.function.Predicate and(java.util.function.Predicate predicate) {
                    return Predicate$CC.$default$and(this, predicate);
                }

                public /* synthetic */ java.util.function.Predicate negate() {
                    return Predicate$CC.$default$negate(this);
                }

                /* renamed from: or */
                public /* synthetic */ java.util.function.Predicate m433or(java.util.function.Predicate predicate) {
                    return Predicate$CC.$default$or(this, predicate);
                }

                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return collection.contains(obj);
                }
            });
        }

        public static /* synthetic */ boolean $r8$lambda$mHSDE7M_UCPqdJt5BUAEQGWilN8(java.util.Collection collection, Object obj) {
            return !collection.contains(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public boolean retainAll(final java.util.Collection collection) {
            return removeIf(new java.util.function.Predicate() { // from class: com.google.common.collect.Collections2$FilteredCollection$$ExternalSyntheticLambda2
                public /* synthetic */ java.util.function.Predicate and(java.util.function.Predicate predicate) {
                    return Predicate$CC.$default$and(this, predicate);
                }

                public /* synthetic */ java.util.function.Predicate negate() {
                    return Predicate$CC.$default$negate(this);
                }

                /* renamed from: or */
                public /* synthetic */ java.util.function.Predicate m435or(java.util.function.Predicate predicate) {
                    return Predicate$CC.$default$or(this, predicate);
                }

                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Collections2.FilteredCollection.$r8$lambda$mHSDE7M_UCPqdJt5BUAEQGWilN8(collection, obj);
                }
            });
        }

        @Override // java.util.Collection, p017j$.util.Collection
        public boolean removeIf(final java.util.function.Predicate predicate) {
            Preconditions.checkNotNull(predicate);
            return Collection.EL.removeIf(this.unfiltered, new java.util.function.Predicate() { // from class: com.google.common.collect.Collections2$FilteredCollection$$ExternalSyntheticLambda1
                public /* synthetic */ java.util.function.Predicate and(java.util.function.Predicate predicate2) {
                    return Predicate$CC.$default$and(this, predicate2);
                }

                public /* synthetic */ java.util.function.Predicate negate() {
                    return Predicate$CC.$default$negate(this);
                }

                /* renamed from: or */
                public /* synthetic */ java.util.function.Predicate m434or(java.util.function.Predicate predicate2) {
                    return Predicate$CC.$default$or(this, predicate2);
                }

                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Collections2.FilteredCollection.$r8$lambda$XcikizKVdHbtQuywrRF3RSKWCKc(this.f$0, predicate, obj);
                }
            });
        }

        public static /* synthetic */ boolean $r8$lambda$XcikizKVdHbtQuywrRF3RSKWCKc(FilteredCollection filteredCollection, java.util.function.Predicate predicate, Object obj) {
            return filteredCollection.predicate.apply(obj) && predicate.test(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public int size() {
            Iterator it = this.unfiltered.iterator();
            int i = 0;
            while (it.hasNext()) {
                if (this.predicate.apply(it.next())) {
                    i++;
                }
            }
            return i;
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray() {
            return Lists.newArrayList(iterator()).toArray();
        }

        @Override // java.util.AbstractCollection, java.util.Collection
        public Object[] toArray(Object[] objArr) {
            return Lists.newArrayList(iterator()).toArray(objArr);
        }
    }

    static boolean containsAllImpl(java.util.Collection collection, java.util.Collection collection2) {
        Iterator it = collection2.iterator();
        while (it.hasNext()) {
            if (!collection.contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    static StringBuilder newStringBuilderForCollection(int i) {
        CollectPreconditions.checkNonnegative(i, "size");
        return new StringBuilder((int) Math.min(i * 8, 1073741824L));
    }
}
