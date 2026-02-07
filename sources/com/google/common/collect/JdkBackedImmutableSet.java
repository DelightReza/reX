package com.google.common.collect;

import java.util.Set;

/* loaded from: classes4.dex */
final class JdkBackedImmutableSet extends IndexedImmutableSet {
    private final Set delegate;
    private final ImmutableList delegateList;

    @Override // com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return false;
    }

    JdkBackedImmutableSet(Set set, ImmutableList immutableList) {
        this.delegate = set;
        this.delegateList = immutableList;
    }

    @Override // com.google.common.collect.IndexedImmutableSet
    Object get(int i) {
        return this.delegateList.get(i);
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        return this.delegate.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.delegateList.size();
    }
}
