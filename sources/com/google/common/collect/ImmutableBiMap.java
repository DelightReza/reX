package com.google.common.collect;

import java.util.Map;

/* loaded from: classes4.dex */
public abstract class ImmutableBiMap extends ImmutableBiMapFauxverideShim implements Map, p017j$.util.Map {
    public abstract ImmutableBiMap inverse();

    /* renamed from: of */
    public static ImmutableBiMap m436of(Object obj, Object obj2) {
        return new SingletonImmutableBiMap(obj, obj2);
    }

    ImmutableBiMap() {
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public ImmutableSet values() {
        return inverse().keySet();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.google.common.collect.ImmutableMap
    public final ImmutableSet createValues() {
        throw new AssertionError("should never be called");
    }
}
