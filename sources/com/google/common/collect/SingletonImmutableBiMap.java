package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
final class SingletonImmutableBiMap extends ImmutableBiMap {
    private final transient ImmutableBiMap inverse;
    private transient ImmutableBiMap lazyInverse;
    final transient Object singleKey;
    final transient Object singleValue;

    @Override // com.google.common.collect.ImmutableMap
    boolean isPartialView() {
        return false;
    }

    @Override // java.util.Map
    public int size() {
        return 1;
    }

    SingletonImmutableBiMap(Object obj, Object obj2) {
        CollectPreconditions.checkEntryNotNull(obj, obj2);
        this.singleKey = obj;
        this.singleValue = obj2;
        this.inverse = null;
    }

    private SingletonImmutableBiMap(Object obj, Object obj2, ImmutableBiMap immutableBiMap) {
        this.singleKey = obj;
        this.singleValue = obj2;
        this.inverse = immutableBiMap;
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public Object get(Object obj) {
        if (this.singleKey.equals(obj)) {
            return this.singleValue;
        }
        return null;
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map, p017j$.util.Map
    public void forEach(BiConsumer biConsumer) {
        ((BiConsumer) Preconditions.checkNotNull(biConsumer)).accept(this.singleKey, this.singleValue);
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public boolean containsKey(Object obj) {
        return this.singleKey.equals(obj);
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public boolean containsValue(Object obj) {
        return this.singleValue.equals(obj);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet createEntrySet() {
        return ImmutableSet.m444of((Object) Maps.immutableEntry(this.singleKey, this.singleValue));
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet createKeySet() {
        return ImmutableSet.m444of(this.singleKey);
    }

    @Override // com.google.common.collect.ImmutableBiMap
    public ImmutableBiMap inverse() {
        ImmutableBiMap immutableBiMap = this.inverse;
        if (immutableBiMap != null) {
            return immutableBiMap;
        }
        ImmutableBiMap immutableBiMap2 = this.lazyInverse;
        if (immutableBiMap2 != null) {
            return immutableBiMap2;
        }
        SingletonImmutableBiMap singletonImmutableBiMap = new SingletonImmutableBiMap(this.singleValue, this.singleKey, this);
        this.lazyInverse = singletonImmutableBiMap;
        return singletonImmutableBiMap;
    }
}
