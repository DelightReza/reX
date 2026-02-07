package com.google.common.collect;

import com.google.common.collect.ImmutableSet;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;
import p017j$.util.Spliterator;

/* loaded from: classes4.dex */
abstract class ImmutableMapEntrySet extends ImmutableSet.CachingAsList {
    abstract ImmutableMap map();

    static final class RegularEntrySet extends ImmutableMapEntrySet {
        private final transient ImmutableList entries;
        private final transient ImmutableMap map;

        @Override // com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable
        public /* synthetic */ Spliterator spliterator() {
            return Spliterator.Wrapper.convert(spliterator());
        }

        RegularEntrySet(ImmutableMap immutableMap, Map.Entry[] entryArr) {
            this(immutableMap, ImmutableList.asImmutableList(entryArr));
        }

        RegularEntrySet(ImmutableMap immutableMap, ImmutableList immutableList) {
            this.map = immutableMap;
            this.entries = immutableList;
        }

        @Override // com.google.common.collect.ImmutableMapEntrySet
        ImmutableMap map() {
            return this.map;
        }

        @Override // com.google.common.collect.ImmutableCollection
        int copyIntoArray(Object[] objArr, int i) {
            return this.entries.copyIntoArray(objArr, i);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
        public UnmodifiableIterator iterator() {
            return this.entries.iterator();
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable, p017j$.util.Collection
        public p017j$.util.Spliterator spliterator() {
            return this.entries.spliterator();
        }

        @Override // com.google.common.collect.ImmutableCollection, java.lang.Iterable, p017j$.util.Collection, p017j$.lang.InterfaceC1637a
        public void forEach(Consumer consumer) {
            this.entries.forEach(consumer);
        }

        @Override // com.google.common.collect.ImmutableSet.CachingAsList
        ImmutableList createAsList() {
            return new RegularImmutableAsList(this, this.entries);
        }
    }

    ImmutableMapEntrySet() {
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return map().size();
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        if (obj instanceof Map.Entry) {
            Map.Entry entry = (Map.Entry) obj;
            Object obj2 = map().get(entry.getKey());
            if (obj2 != null && obj2.equals(entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return map().isPartialView();
    }

    @Override // com.google.common.collect.ImmutableSet
    boolean isHashCodeFast() {
        return map().isHashCodeFast();
    }

    @Override // com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return map().hashCode();
    }
}
