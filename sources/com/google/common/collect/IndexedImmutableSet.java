package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import p017j$.util.Spliterator;

/* loaded from: classes4.dex */
abstract class IndexedImmutableSet extends ImmutableSet.CachingAsList {
    abstract Object get(int i);

    @Override // com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable
    public /* synthetic */ Spliterator spliterator() {
        return Spliterator.Wrapper.convert(spliterator());
    }

    IndexedImmutableSet() {
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public UnmodifiableIterator iterator() {
        return asList().iterator();
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable, p017j$.util.Collection
    public p017j$.util.Spliterator spliterator() {
        return CollectSpliterators.indexed(size(), 1297, new IntFunction() { // from class: com.google.common.collect.IndexedImmutableSet$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return this.f$0.get(i);
            }
        });
    }

    @Override // com.google.common.collect.ImmutableCollection, java.lang.Iterable, p017j$.util.Collection, p017j$.lang.InterfaceC1637a
    public void forEach(Consumer consumer) {
        Preconditions.checkNotNull(consumer);
        int size = size();
        for (int i = 0; i < size; i++) {
            consumer.accept(get(i));
        }
    }

    @Override // com.google.common.collect.ImmutableCollection
    int copyIntoArray(Object[] objArr, int i) {
        return asList().copyIntoArray(objArr, i);
    }

    @Override // com.google.common.collect.ImmutableSet.CachingAsList
    ImmutableList createAsList() {
        return new ImmutableAsList() { // from class: com.google.common.collect.IndexedImmutableSet.1
            @Override // java.util.List
            public Object get(int i) {
                return IndexedImmutableSet.this.get(i);
            }

            @Override // com.google.common.collect.ImmutableAsList, com.google.common.collect.ImmutableCollection
            boolean isPartialView() {
                return IndexedImmutableSet.this.isPartialView();
            }

            @Override // com.google.common.collect.ImmutableAsList, java.util.AbstractCollection, java.util.Collection, java.util.List
            public int size() {
                return IndexedImmutableSet.this.size();
            }

            @Override // com.google.common.collect.ImmutableAsList
            ImmutableCollection delegateCollection() {
                return IndexedImmutableSet.this;
            }
        };
    }
}
