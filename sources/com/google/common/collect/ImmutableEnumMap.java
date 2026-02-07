package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;
import p017j$.util.Map;
import p017j$.util.Set;
import p017j$.util.Spliterator;

/* loaded from: classes4.dex */
final class ImmutableEnumMap extends ImmutableMap.IteratorBasedImmutableMap {
    private final transient EnumMap delegate;

    @Override // com.google.common.collect.ImmutableMap
    boolean isPartialView() {
        return false;
    }

    static ImmutableMap asImmutable(EnumMap enumMap) {
        int size = enumMap.size();
        if (size == 0) {
            return ImmutableMap.m441of();
        }
        if (size == 1) {
            return ImmutableMap.m442of(r2.getKey(), ((Map.Entry) Iterables.getOnlyElement(enumMap.entrySet())).getValue());
        }
        return new ImmutableEnumMap(enumMap);
    }

    private ImmutableEnumMap(EnumMap enumMap) {
        this.delegate = enumMap;
        Preconditions.checkArgument(!enumMap.isEmpty());
    }

    @Override // com.google.common.collect.ImmutableMap
    UnmodifiableIterator keyIterator() {
        return Iterators.unmodifiableIterator(this.delegate.keySet().iterator());
    }

    @Override // com.google.common.collect.ImmutableMap
    Spliterator keySpliterator() {
        return Set.EL.spliterator(this.delegate.keySet());
    }

    @Override // java.util.Map
    public int size() {
        return this.delegate.size();
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public boolean containsKey(Object obj) {
        return this.delegate.containsKey(obj);
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public Object get(Object obj) {
        return this.delegate.get(obj);
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ImmutableEnumMap) {
            obj = ((ImmutableEnumMap) obj).delegate;
        }
        return this.delegate.equals(obj);
    }

    @Override // com.google.common.collect.ImmutableMap.IteratorBasedImmutableMap
    UnmodifiableIterator entryIterator() {
        return Maps.unmodifiableEntryIterator(this.delegate.entrySet().iterator());
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map, p017j$.util.Map
    public void forEach(BiConsumer biConsumer) {
        Map.EL.forEach(this.delegate, biConsumer);
    }
}
