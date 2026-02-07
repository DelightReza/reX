package com.exteragram.messenger.backup;

import java.util.HashSet;
import java.util.Set;
import p017j$.util.DesugarCollections;
import p017j$.util.Objects;

/* loaded from: classes.dex */
public abstract /* synthetic */ class PreferencesUtils$$ExternalSyntheticBackport1 {
    /* renamed from: m */
    public static /* synthetic */ Set m189m(Object[] objArr) {
        HashSet hashSet = new HashSet(objArr.length);
        for (Object obj : objArr) {
            Objects.requireNonNull(obj);
            if (!hashSet.add(obj)) {
                throw new IllegalArgumentException("duplicate element: " + obj);
            }
        }
        return DesugarCollections.unmodifiableSet(hashSet);
    }
}
