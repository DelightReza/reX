package com.exteragram.messenger.badges;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import p017j$.util.DesugarCollections;
import p017j$.util.Objects;

/* loaded from: classes.dex */
public abstract /* synthetic */ class CachedRemoteSet$$ExternalSyntheticBackport0 {
    /* renamed from: m */
    public static /* synthetic */ Set m190m(Collection collection) {
        HashSet hashSet = new HashSet(collection.size());
        for (Object obj : collection) {
            Objects.requireNonNull(obj);
            hashSet.add(obj);
        }
        return DesugarCollections.unmodifiableSet(hashSet);
    }
}
