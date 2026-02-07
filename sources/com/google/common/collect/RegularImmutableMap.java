package com.google.common.collect;

import com.exteragram.messenger.plugins.PluginsConstants;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMapEntry;
import com.google.common.collect.ImmutableMapEntrySet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import p017j$.util.Objects;

/* loaded from: classes.dex */
final class RegularImmutableMap extends ImmutableMap {
    static final ImmutableMap EMPTY = new RegularImmutableMap(ImmutableMap.EMPTY_ENTRY_ARRAY, null, 0);
    final transient Map.Entry[] entries;
    private final transient int mask;
    private final transient ImmutableMapEntry[] table;

    @Override // com.google.common.collect.ImmutableMap
    boolean isPartialView() {
        return false;
    }

    static ImmutableMap fromEntries(Map.Entry... entryArr) {
        return fromEntryArray(entryArr.length, entryArr, true);
    }

    static ImmutableMap fromEntryArray(int i, Map.Entry[] entryArr, boolean z) {
        Preconditions.checkPositionIndex(i, entryArr.length);
        if (i == 0) {
            return EMPTY;
        }
        try {
            return fromEntryArrayCheckingBucketOverflow(i, entryArr, z);
        } catch (BucketOverflowException unused) {
            return JdkBackedImmutableMap.create(i, entryArr, z);
        }
    }

    private static ImmutableMap fromEntryArrayCheckingBucketOverflow(int i, Map.Entry[] entryArr, boolean z) throws BucketOverflowException {
        Map.Entry[] entryArrCreateEntryArray = i == entryArr.length ? entryArr : ImmutableMapEntry.createEntryArray(i);
        int iClosedTableSize = Hashing.closedTableSize(i, 1.2d);
        ImmutableMapEntry[] immutableMapEntryArrCreateEntryArray = ImmutableMapEntry.createEntryArray(iClosedTableSize);
        int i2 = iClosedTableSize - 1;
        IdentityHashMap identityHashMap = null;
        int i3 = 0;
        for (int i4 = i - 1; i4 >= 0; i4--) {
            Map.Entry entry = entryArr[i4];
            Objects.requireNonNull(entry);
            Map.Entry entry2 = entry;
            Object key = entry2.getKey();
            Object value = entry2.getValue();
            CollectPreconditions.checkEntryNotNull(key, value);
            int iSmear = Hashing.smear(key.hashCode()) & i2;
            ImmutableMapEntry immutableMapEntry = immutableMapEntryArrCreateEntryArray[iSmear];
            ImmutableMapEntry immutableMapEntryCheckNoConflictInKeyBucket = checkNoConflictInKeyBucket(key, value, immutableMapEntry, z);
            if (immutableMapEntryCheckNoConflictInKeyBucket == null) {
                if (immutableMapEntry == null) {
                    immutableMapEntryCheckNoConflictInKeyBucket = makeImmutable(entry2, key, value);
                } else {
                    immutableMapEntryCheckNoConflictInKeyBucket = new ImmutableMapEntry.NonTerminalImmutableMapEntry(key, value, immutableMapEntry);
                }
                immutableMapEntryArrCreateEntryArray[iSmear] = immutableMapEntryCheckNoConflictInKeyBucket;
            } else {
                if (identityHashMap == null) {
                    identityHashMap = new IdentityHashMap();
                }
                identityHashMap.put(immutableMapEntryCheckNoConflictInKeyBucket, Boolean.TRUE);
                i3++;
                if (entryArrCreateEntryArray == entryArr) {
                    entryArrCreateEntryArray = (Map.Entry[]) entryArrCreateEntryArray.clone();
                }
            }
            entryArrCreateEntryArray[i4] = immutableMapEntryCheckNoConflictInKeyBucket;
        }
        if (identityHashMap != null) {
            entryArrCreateEntryArray = removeDuplicates(entryArrCreateEntryArray, i, i - i3, identityHashMap);
            if (Hashing.closedTableSize(entryArrCreateEntryArray.length, 1.2d) != iClosedTableSize) {
                return fromEntryArrayCheckingBucketOverflow(entryArrCreateEntryArray.length, entryArrCreateEntryArray, true);
            }
        }
        return new RegularImmutableMap(entryArrCreateEntryArray, immutableMapEntryArrCreateEntryArray, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    static Map.Entry[] removeDuplicates(Map.Entry[] entryArr, int i, int i2, IdentityHashMap identityHashMap) {
        ImmutableMapEntry[] immutableMapEntryArrCreateEntryArray = ImmutableMapEntry.createEntryArray(i2);
        int i3 = 0;
        for (int i4 = 0; i4 < i; i4++) {
            Map.Entry entry = entryArr[i4];
            Boolean bool = (Boolean) identityHashMap.get(entry);
            if (bool != null) {
                if (bool.booleanValue()) {
                    identityHashMap.put(entry, Boolean.FALSE);
                    immutableMapEntryArrCreateEntryArray[i3] = entry;
                    i3++;
                }
            } else {
                immutableMapEntryArrCreateEntryArray[i3] = entry;
                i3++;
            }
        }
        return immutableMapEntryArrCreateEntryArray;
    }

    static ImmutableMapEntry makeImmutable(Map.Entry entry, Object obj, Object obj2) {
        return ((entry instanceof ImmutableMapEntry) && ((ImmutableMapEntry) entry).isReusable()) ? (ImmutableMapEntry) entry : new ImmutableMapEntry(obj, obj2);
    }

    static ImmutableMapEntry makeImmutable(Map.Entry entry) {
        return makeImmutable(entry, entry.getKey(), entry.getValue());
    }

    private RegularImmutableMap(Map.Entry[] entryArr, ImmutableMapEntry[] immutableMapEntryArr, int i) {
        this.entries = entryArr;
        this.table = immutableMapEntryArr;
        this.mask = i;
    }

    static ImmutableMapEntry checkNoConflictInKeyBucket(Object obj, Object obj2, ImmutableMapEntry immutableMapEntry, boolean z) throws BucketOverflowException {
        int i = 0;
        while (immutableMapEntry != null) {
            if (immutableMapEntry.getKey().equals(obj)) {
                if (!z) {
                    return immutableMapEntry;
                }
                ImmutableMap.checkNoConflict(false, PluginsConstants.Settings.KEY, immutableMapEntry, obj + "=" + obj2);
            }
            i++;
            if (i <= 8) {
                immutableMapEntry = immutableMapEntry.getNextInKeyBucket();
            } else {
                throw new BucketOverflowException();
            }
        }
        return null;
    }

    /* loaded from: classes4.dex */
    static class BucketOverflowException extends Exception {
        BucketOverflowException() {
        }
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public Object get(Object obj) {
        return get(obj, this.table, this.mask);
    }

    static Object get(Object obj, ImmutableMapEntry[] immutableMapEntryArr, int i) {
        if (obj != null && immutableMapEntryArr != null) {
            for (ImmutableMapEntry nextInKeyBucket = immutableMapEntryArr[i & Hashing.smear(obj.hashCode())]; nextInKeyBucket != null; nextInKeyBucket = nextInKeyBucket.getNextInKeyBucket()) {
                if (obj.equals(nextInKeyBucket.getKey())) {
                    return nextInKeyBucket.getValue();
                }
            }
        }
        return null;
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map, p017j$.util.Map
    public void forEach(BiConsumer biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        for (Map.Entry entry : this.entries) {
            biConsumer.accept(entry.getKey(), entry.getValue());
        }
    }

    @Override // java.util.Map
    public int size() {
        return this.entries.length;
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet createEntrySet() {
        return new ImmutableMapEntrySet.RegularEntrySet(this, this.entries);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet createKeySet() {
        return new KeySet(this);
    }

    /* loaded from: classes4.dex */
    private static final class KeySet extends IndexedImmutableSet {
        private final RegularImmutableMap map;

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return true;
        }

        KeySet(RegularImmutableMap regularImmutableMap) {
            this.map = regularImmutableMap;
        }

        @Override // com.google.common.collect.IndexedImmutableSet
        Object get(int i) {
            return this.map.entries[i].getKey();
        }

        @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.List
        public boolean contains(Object obj) {
            return this.map.containsKey(obj);
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
        public int size() {
            return this.map.size();
        }
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableCollection createValues() {
        return new Values(this);
    }

    /* loaded from: classes4.dex */
    private static final class Values extends ImmutableList {
        final RegularImmutableMap map;

        @Override // com.google.common.collect.ImmutableCollection
        boolean isPartialView() {
            return true;
        }

        Values(RegularImmutableMap regularImmutableMap) {
            this.map = regularImmutableMap;
        }

        @Override // java.util.List
        public Object get(int i) {
            return this.map.entries[i].getValue();
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.map.size();
        }
    }
}
