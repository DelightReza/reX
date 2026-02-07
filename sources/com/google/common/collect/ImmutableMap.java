package com.google.common.collect;

import com.google.common.collect.ImmutableCollection;
import java.io.Serializable;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import p017j$.util.Map;
import p017j$.util.Objects;
import p017j$.util.Spliterator;
import p017j$.util.function.Function$CC;

/* loaded from: classes.dex */
public abstract class ImmutableMap implements Map, Serializable, p017j$.util.Map {
    static final Map.Entry[] EMPTY_ENTRY_ARRAY = new Map.Entry[0];
    private transient ImmutableSet entrySet;
    private transient ImmutableSet keySet;
    private transient ImmutableCollection values;

    abstract ImmutableSet createEntrySet();

    abstract ImmutableSet createKeySet();

    abstract ImmutableCollection createValues();

    @Override // java.util.Map, p017j$.util.Map
    public /* synthetic */ void forEach(BiConsumer biConsumer) {
        Map.CC.$default$forEach(this, biConsumer);
    }

    @Override // java.util.Map
    public abstract Object get(Object obj);

    boolean isHashCodeFast() {
        return false;
    }

    abstract boolean isPartialView();

    /* renamed from: of */
    public static ImmutableMap m441of() {
        return RegularImmutableMap.EMPTY;
    }

    /* renamed from: of */
    public static ImmutableMap m442of(Object obj, Object obj2) {
        return ImmutableBiMap.m436of(obj, obj2);
    }

    static Map.Entry entryOf(Object obj, Object obj2) {
        return new ImmutableMapEntry(obj, obj2);
    }

    public static Builder builder() {
        return new Builder();
    }

    static void checkNoConflict(boolean z, String str, Object obj, Object obj2) {
        if (!z) {
            throw conflictException(str, obj, obj2);
        }
    }

    static IllegalArgumentException conflictException(String str, Object obj, Object obj2) {
        return new IllegalArgumentException("Multiple entries with same " + str + ": " + obj + " and " + obj2);
    }

    /* loaded from: classes4.dex */
    public static class Builder {
        Map.Entry[] entries;
        boolean entriesUsed;
        int size;
        Comparator valueComparator;

        public Builder() {
            this(4);
        }

        Builder(int i) {
            this.entries = new Map.Entry[i];
            this.size = 0;
            this.entriesUsed = false;
        }

        private void ensureCapacity(int i) {
            Map.Entry[] entryArr = this.entries;
            if (i > entryArr.length) {
                this.entries = (Map.Entry[]) Arrays.copyOf(entryArr, ImmutableCollection.Builder.expandedCapacity(entryArr.length, i));
                this.entriesUsed = false;
            }
        }

        public Builder put(Object obj, Object obj2) {
            ensureCapacity(this.size + 1);
            Map.Entry entryEntryOf = ImmutableMap.entryOf(obj, obj2);
            Map.Entry[] entryArr = this.entries;
            int i = this.size;
            this.size = i + 1;
            entryArr[i] = entryEntryOf;
            return this;
        }

        private ImmutableMap build(boolean z) {
            Map.Entry[] entryArr;
            int length = this.size;
            if (length == 0) {
                return ImmutableMap.m441of();
            }
            if (length == 1) {
                Map.Entry entry = this.entries[0];
                Objects.requireNonNull(entry);
                Map.Entry entry2 = entry;
                return ImmutableMap.m442of(entry2.getKey(), entry2.getValue());
            }
            if (this.valueComparator == null) {
                entryArr = this.entries;
            } else {
                if (this.entriesUsed) {
                    this.entries = (Map.Entry[]) Arrays.copyOf(this.entries, length);
                }
                Map.Entry[] entryArr2 = this.entries;
                if (!z) {
                    Map.Entry[] entryArrLastEntryForEachKey = lastEntryForEachKey(entryArr2, this.size);
                    entryArr2 = entryArrLastEntryForEachKey;
                    length = entryArrLastEntryForEachKey.length;
                }
                Arrays.sort(entryArr2, 0, length, Ordering.from(this.valueComparator).onResultOf(Maps.valueFunction()));
                entryArr = entryArr2;
            }
            this.entriesUsed = true;
            return RegularImmutableMap.fromEntryArray(length, entryArr, z);
        }

        public ImmutableMap buildOrThrow() {
            return build(true);
        }

        private static Map.Entry[] lastEntryForEachKey(Map.Entry[] entryArr, int i) {
            HashSet hashSet = new HashSet();
            BitSet bitSet = new BitSet();
            for (int i2 = i - 1; i2 >= 0; i2--) {
                if (!hashSet.add(entryArr[i2].getKey())) {
                    bitSet.set(i2);
                }
            }
            if (bitSet.isEmpty()) {
                return entryArr;
            }
            Map.Entry[] entryArr2 = new Map.Entry[i - bitSet.cardinality()];
            int i3 = 0;
            for (int i4 = 0; i4 < i; i4++) {
                if (!bitSet.get(i4)) {
                    entryArr2[i3] = entryArr[i4];
                    i3++;
                }
            }
            return entryArr2;
        }
    }

    public static ImmutableMap copyOf(java.util.Map map) {
        if ((map instanceof ImmutableMap) && !(map instanceof SortedMap)) {
            ImmutableMap immutableMap = (ImmutableMap) map;
            if (!immutableMap.isPartialView()) {
                return immutableMap;
            }
        } else if (map instanceof EnumMap) {
            return copyOfEnumMap((EnumMap) map);
        }
        return copyOf(map.entrySet());
    }

    public static ImmutableMap copyOf(Iterable iterable) {
        Map.Entry[] entryArr = (Map.Entry[]) Iterables.toArray(iterable, EMPTY_ENTRY_ARRAY);
        int length = entryArr.length;
        if (length == 0) {
            return m441of();
        }
        if (length == 1) {
            Map.Entry entry = entryArr[0];
            Objects.requireNonNull(entry);
            Map.Entry entry2 = entry;
            return m442of(entry2.getKey(), entry2.getValue());
        }
        return RegularImmutableMap.fromEntries(entryArr);
    }

    private static ImmutableMap copyOfEnumMap(EnumMap enumMap) {
        EnumMap enumMap2 = new EnumMap(enumMap);
        for (Map.Entry entry : enumMap2.entrySet()) {
            CollectPreconditions.checkEntryNotNull(entry.getKey(), entry.getValue());
        }
        return ImmutableEnumMap.asImmutable(enumMap2);
    }

    /* loaded from: classes4.dex */
    static abstract class IteratorBasedImmutableMap extends ImmutableMap {
        abstract UnmodifiableIterator entryIterator();

        IteratorBasedImmutableMap() {
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public /* bridge */ /* synthetic */ Set entrySet() {
            return super.entrySet();
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public /* bridge */ /* synthetic */ Set keySet() {
            return super.keySet();
        }

        @Override // com.google.common.collect.ImmutableMap, java.util.Map
        public /* bridge */ /* synthetic */ Collection values() {
            return super.values();
        }

        @Override // com.google.common.collect.ImmutableMap
        ImmutableSet createKeySet() {
            return new ImmutableMapKeySet(this);
        }

        @Override // com.google.common.collect.ImmutableMap
        ImmutableSet createEntrySet() {
            return new ImmutableMapEntrySet() { // from class: com.google.common.collect.ImmutableMap.IteratorBasedImmutableMap.1EntrySetImpl
                @Override // com.google.common.collect.ImmutableMapEntrySet
                ImmutableMap map() {
                    return IteratorBasedImmutableMap.this;
                }

                @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
                public UnmodifiableIterator iterator() {
                    return IteratorBasedImmutableMap.this.entryIterator();
                }
            };
        }

        @Override // com.google.common.collect.ImmutableMap
        ImmutableCollection createValues() {
            return new ImmutableMapValues(this);
        }
    }

    ImmutableMap() {
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object putIfAbsent(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final boolean replace(Object obj, Object obj2, Object obj3) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object replace(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object computeIfAbsent(Object obj, Function function) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object computeIfPresent(Object obj, BiFunction biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object compute(Object obj, BiFunction biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object merge(Object obj, Object obj2, BiFunction biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final void putAll(java.util.Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final void replaceAll(BiFunction biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final boolean remove(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override // java.util.Map
    public boolean containsKey(Object obj) {
        return get(obj) != null;
    }

    @Override // java.util.Map
    public boolean containsValue(Object obj) {
        return values().contains(obj);
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object getOrDefault(Object obj, Object obj2) {
        Object obj3 = get(obj);
        return obj3 != null ? obj3 : obj2;
    }

    @Override // java.util.Map
    public ImmutableSet entrySet() {
        ImmutableSet immutableSet = this.entrySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet immutableSetCreateEntrySet = createEntrySet();
        this.entrySet = immutableSetCreateEntrySet;
        return immutableSetCreateEntrySet;
    }

    @Override // java.util.Map
    public ImmutableSet keySet() {
        ImmutableSet immutableSet = this.keySet;
        if (immutableSet != null) {
            return immutableSet;
        }
        ImmutableSet immutableSetCreateKeySet = createKeySet();
        this.keySet = immutableSetCreateKeySet;
        return immutableSetCreateKeySet;
    }

    UnmodifiableIterator keyIterator() {
        final UnmodifiableIterator it = entrySet().iterator();
        return new UnmodifiableIterator(this) { // from class: com.google.common.collect.ImmutableMap.1
            @Override // java.util.Iterator
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override // java.util.Iterator
            public Object next() {
                return ((Map.Entry) it.next()).getKey();
            }
        };
    }

    Spliterator keySpliterator() {
        return CollectSpliterators.map(entrySet().spliterator(), new Function() { // from class: com.google.common.collect.ImmutableMap$$ExternalSyntheticLambda0
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Map.Entry) obj).getKey();
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        });
    }

    @Override // java.util.Map
    public ImmutableCollection values() {
        ImmutableCollection immutableCollection = this.values;
        if (immutableCollection != null) {
            return immutableCollection;
        }
        ImmutableCollection immutableCollectionCreateValues = createValues();
        this.values = immutableCollectionCreateValues;
        return immutableCollectionCreateValues;
    }

    @Override // java.util.Map
    public boolean equals(Object obj) {
        return Maps.equalsImpl(this, obj);
    }

    @Override // java.util.Map
    public int hashCode() {
        return Sets.hashCodeImpl(entrySet());
    }

    public String toString() {
        return Maps.toStringImpl(this);
    }
}
