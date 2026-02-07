package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.SortedSet;
import org.telegram.tgnet.TLObject;
import p017j$.util.Objects;

/* loaded from: classes4.dex */
public abstract class ImmutableSet extends ImmutableCollection implements Set, p017j$.util.Set {
    boolean isHashCodeFast() {
        return false;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public abstract UnmodifiableIterator iterator();

    /* renamed from: of */
    public static ImmutableSet m443of() {
        return RegularImmutableSet.EMPTY;
    }

    /* renamed from: of */
    public static ImmutableSet m444of(Object obj) {
        return new SingletonImmutableSet(obj);
    }

    /* renamed from: of */
    public static ImmutableSet m445of(Object obj, Object obj2) {
        return construct(2, 2, obj, obj2);
    }

    /* renamed from: of */
    public static ImmutableSet m446of(Object obj, Object obj2, Object obj3) {
        return construct(3, 3, obj, obj2, obj3);
    }

    /* renamed from: of */
    public static ImmutableSet m447of(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        return construct(5, 5, obj, obj2, obj3, obj4, obj5);
    }

    private static ImmutableSet constructUnknownDuplication(int i, Object... objArr) {
        return construct(i, Math.max(4, IntMath.sqrt(i, RoundingMode.CEILING)), objArr);
    }

    private static ImmutableSet construct(int i, int i2, Object... objArr) {
        if (i == 0) {
            return m443of();
        }
        if (i == 1) {
            return m444of(objArr[0]);
        }
        SetBuilderImpl regularSetBuilderImpl = new RegularSetBuilderImpl(i2);
        for (int i3 = 0; i3 < i; i3++) {
            regularSetBuilderImpl = regularSetBuilderImpl.add(Preconditions.checkNotNull(objArr[i3]));
        }
        return regularSetBuilderImpl.review().build();
    }

    public static ImmutableSet copyOf(Collection collection) {
        if ((collection instanceof ImmutableSet) && !(collection instanceof SortedSet)) {
            ImmutableSet immutableSet = (ImmutableSet) collection;
            if (!immutableSet.isPartialView()) {
                return immutableSet;
            }
        } else if (collection instanceof EnumSet) {
            return copyOfEnumSet((EnumSet) collection);
        }
        Object[] array = collection.toArray();
        if (collection instanceof Set) {
            return construct(array.length, array.length, array);
        }
        return constructUnknownDuplication(array.length, array);
    }

    public static ImmutableSet copyOf(Object[] objArr) {
        int length = objArr.length;
        if (length == 0) {
            return m443of();
        }
        if (length == 1) {
            return m444of(objArr[0]);
        }
        return constructUnknownDuplication(objArr.length, (Object[]) objArr.clone());
    }

    private static ImmutableSet copyOfEnumSet(EnumSet enumSet) {
        return ImmutableEnumSet.asImmutable(EnumSet.copyOf(enumSet));
    }

    ImmutableSet() {
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if ((obj instanceof ImmutableSet) && isHashCodeFast() && ((ImmutableSet) obj).isHashCodeFast() && hashCode() != obj.hashCode()) {
            return false;
        }
        return Sets.equalsImpl(this, obj);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    static abstract class CachingAsList extends ImmutableSet {
        private transient ImmutableList asList;

        CachingAsList() {
        }

        @Override // com.google.common.collect.ImmutableCollection
        public ImmutableList asList() {
            ImmutableList immutableList = this.asList;
            if (immutableList != null) {
                return immutableList;
            }
            ImmutableList immutableListCreateAsList = createAsList();
            this.asList = immutableListCreateAsList;
            return immutableListCreateAsList;
        }

        ImmutableList createAsList() {
            return new RegularImmutableAsList(this, toArray());
        }
    }

    private static abstract class SetBuilderImpl {
        Object[] dedupedElements;
        int distinct;

        abstract SetBuilderImpl add(Object obj);

        abstract ImmutableSet build();

        SetBuilderImpl review() {
            return this;
        }

        SetBuilderImpl(int i) {
            this.dedupedElements = new Object[i];
            this.distinct = 0;
        }

        SetBuilderImpl(SetBuilderImpl setBuilderImpl) {
            Object[] objArr = setBuilderImpl.dedupedElements;
            this.dedupedElements = Arrays.copyOf(objArr, objArr.length);
            this.distinct = setBuilderImpl.distinct;
        }

        private void ensureCapacity(int i) {
            Object[] objArr = this.dedupedElements;
            if (i > objArr.length) {
                this.dedupedElements = Arrays.copyOf(this.dedupedElements, ImmutableCollection.Builder.expandedCapacity(objArr.length, i));
            }
        }

        final void addDedupedElement(Object obj) {
            ensureCapacity(this.distinct + 1);
            Object[] objArr = this.dedupedElements;
            int i = this.distinct;
            this.distinct = i + 1;
            objArr[i] = obj;
        }
    }

    static int chooseTableSize(int i) {
        int iMax = Math.max(i, 2);
        if (iMax < 751619276) {
            int iHighestOneBit = Integer.highestOneBit(iMax - 1) << 1;
            while (iHighestOneBit * 0.7d < iMax) {
                iHighestOneBit <<= 1;
            }
            return iHighestOneBit;
        }
        Preconditions.checkArgument(iMax < 1073741824, "collection too large");
        return TLObject.FLAG_30;
    }

    private static final class RegularSetBuilderImpl extends SetBuilderImpl {
        private int expandTableThreshold;
        private int hashCode;
        private Object[] hashTable;
        private int maxRunBeforeFallback;

        RegularSetBuilderImpl(int i) {
            super(i);
            this.hashTable = null;
            this.maxRunBeforeFallback = 0;
            this.expandTableThreshold = 0;
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        SetBuilderImpl add(Object obj) {
            Preconditions.checkNotNull(obj);
            if (this.hashTable == null) {
                if (this.distinct == 0) {
                    addDedupedElement(obj);
                    return this;
                }
                ensureTableCapacity(this.dedupedElements.length);
                this.distinct--;
                return insertInHashTable(this.dedupedElements[0]).add(obj);
            }
            return insertInHashTable(obj);
        }

        private SetBuilderImpl insertInHashTable(Object obj) {
            Objects.requireNonNull(this.hashTable);
            int iHashCode = obj.hashCode();
            int iSmear = Hashing.smear(iHashCode);
            int length = this.hashTable.length - 1;
            for (int i = iSmear; i - iSmear < this.maxRunBeforeFallback; i++) {
                int i2 = i & length;
                Object obj2 = this.hashTable[i2];
                if (obj2 == null) {
                    addDedupedElement(obj);
                    this.hashTable[i2] = obj;
                    this.hashCode += iHashCode;
                    ensureTableCapacity(this.distinct);
                    return this;
                }
                if (obj2.equals(obj)) {
                    return this;
                }
            }
            return new JdkBackedSetBuilderImpl(this).add(obj);
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        SetBuilderImpl review() {
            if (this.hashTable != null) {
                int iChooseTableSize = ImmutableSet.chooseTableSize(this.distinct);
                if (iChooseTableSize * 2 < this.hashTable.length) {
                    this.hashTable = rebuildHashTable(iChooseTableSize, this.dedupedElements, this.distinct);
                    this.maxRunBeforeFallback = maxRunBeforeFallback(iChooseTableSize);
                    this.expandTableThreshold = (int) (iChooseTableSize * 0.7d);
                }
                if (hashFloodingDetected(this.hashTable)) {
                    return new JdkBackedSetBuilderImpl(this);
                }
            }
            return this;
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        ImmutableSet build() {
            int i = this.distinct;
            if (i == 0) {
                return ImmutableSet.m443of();
            }
            if (i == 1) {
                Object obj = this.dedupedElements[0];
                Objects.requireNonNull(obj);
                return ImmutableSet.m444of(obj);
            }
            Object[] objArrCopyOf = this.dedupedElements;
            if (i != objArrCopyOf.length) {
                objArrCopyOf = Arrays.copyOf(objArrCopyOf, i);
            }
            int i2 = this.hashCode;
            Object[] objArr = this.hashTable;
            Objects.requireNonNull(objArr);
            return new RegularImmutableSet(objArrCopyOf, i2, objArr, this.hashTable.length - 1);
        }

        static Object[] rebuildHashTable(int i, Object[] objArr, int i2) {
            int i3;
            Object[] objArr2 = new Object[i];
            int i4 = i - 1;
            for (int i5 = 0; i5 < i2; i5++) {
                Object obj = objArr[i5];
                Objects.requireNonNull(obj);
                int iSmear = Hashing.smear(obj.hashCode());
                while (true) {
                    i3 = iSmear & i4;
                    if (objArr2[i3] == null) {
                        break;
                    }
                    iSmear++;
                }
                objArr2[i3] = obj;
            }
            return objArr2;
        }

        void ensureTableCapacity(int i) {
            int length;
            Object[] objArr = this.hashTable;
            if (objArr == null) {
                length = ImmutableSet.chooseTableSize(i);
                this.hashTable = new Object[length];
            } else {
                if (i <= this.expandTableThreshold || objArr.length >= 1073741824) {
                    return;
                }
                length = objArr.length * 2;
                this.hashTable = rebuildHashTable(length, this.dedupedElements, this.distinct);
            }
            this.maxRunBeforeFallback = maxRunBeforeFallback(length);
            this.expandTableThreshold = (int) (length * 0.7d);
        }

        static boolean hashFloodingDetected(Object[] objArr) {
            int iMaxRunBeforeFallback = maxRunBeforeFallback(objArr.length);
            int length = objArr.length - 1;
            int i = 0;
            int i2 = 0;
            while (i < objArr.length) {
                if (i != i2 || objArr[i] != null) {
                    int i3 = i + iMaxRunBeforeFallback;
                    for (int i4 = i3 - 1; i4 >= i2; i4--) {
                        if (objArr[i4 & length] == null) {
                            i2 = i3;
                            i = i4 + 1;
                        }
                    }
                    return true;
                }
                i2 = i + iMaxRunBeforeFallback;
                if (objArr[(i2 - 1) & length] != null) {
                    i2 = i + 1;
                }
                i = i2;
            }
            return false;
        }

        static int maxRunBeforeFallback(int i) {
            return IntMath.log2(i, RoundingMode.UNNECESSARY) * 13;
        }
    }

    private static final class JdkBackedSetBuilderImpl extends SetBuilderImpl {
        private final Set delegate;

        JdkBackedSetBuilderImpl(SetBuilderImpl setBuilderImpl) {
            super(setBuilderImpl);
            this.delegate = Sets.newHashSetWithExpectedSize(this.distinct);
            for (int i = 0; i < this.distinct; i++) {
                Set set = this.delegate;
                Object obj = this.dedupedElements[i];
                Objects.requireNonNull(obj);
                set.add(obj);
            }
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        SetBuilderImpl add(Object obj) {
            Preconditions.checkNotNull(obj);
            if (this.delegate.add(obj)) {
                addDedupedElement(obj);
            }
            return this;
        }

        @Override // com.google.common.collect.ImmutableSet.SetBuilderImpl
        ImmutableSet build() {
            int i = this.distinct;
            if (i == 0) {
                return ImmutableSet.m443of();
            }
            if (i == 1) {
                Object obj = this.dedupedElements[0];
                Objects.requireNonNull(obj);
                return ImmutableSet.m444of(obj);
            }
            return new JdkBackedImmutableSet(this.delegate, ImmutableList.asImmutableList(this.dedupedElements, this.distinct));
        }
    }
}
