package com.google.common.collect;

import com.exteragram.messenger.plugins.PluginsConstants;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMapEntrySet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import p017j$.util.Objects;
import p017j$.util.function.Consumer$CC;

/* loaded from: classes4.dex */
final class JdkBackedImmutableMap extends ImmutableMap {
    private final transient Map delegateMap;
    private final transient ImmutableList entries;

    @Override // com.google.common.collect.ImmutableMap
    boolean isPartialView() {
        return false;
    }

    static ImmutableMap create(int i, Map.Entry[] entryArr, boolean z) {
        HashMap mapNewHashMapWithExpectedSize = Maps.newHashMapWithExpectedSize(i);
        HashMap map = null;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            Map.Entry entry = entryArr[i3];
            Objects.requireNonNull(entry);
            ImmutableMapEntry immutableMapEntryMakeImmutable = RegularImmutableMap.makeImmutable(entry);
            entryArr[i3] = immutableMapEntryMakeImmutable;
            Object key = immutableMapEntryMakeImmutable.getKey();
            Object value = entryArr[i3].getValue();
            Object objPut = mapNewHashMapWithExpectedSize.put(key, value);
            if (objPut != null) {
                if (z) {
                    throw ImmutableMap.conflictException(PluginsConstants.Settings.KEY, entryArr[i3], entryArr[i3].getKey() + "=" + objPut);
                }
                if (map == null) {
                    map = new HashMap();
                }
                map.put(key, value);
                i2++;
            }
        }
        if (map != null) {
            Map.Entry[] entryArr2 = new Map.Entry[i - i2];
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                Map.Entry entry2 = entryArr[i5];
                Objects.requireNonNull(entry2);
                Map.Entry entry3 = entry2;
                Object key2 = entry3.getKey();
                if (map.containsKey(key2)) {
                    Object obj = map.get(key2);
                    if (obj != null) {
                        ImmutableMapEntry immutableMapEntry = new ImmutableMapEntry(key2, obj);
                        map.put(key2, null);
                        entry3 = immutableMapEntry;
                        entryArr2[i4] = entry3;
                        i4++;
                    }
                } else {
                    entryArr2[i4] = entry3;
                    i4++;
                }
            }
            entryArr = entryArr2;
        }
        return new JdkBackedImmutableMap(mapNewHashMapWithExpectedSize, ImmutableList.asImmutableList(entryArr, i));
    }

    JdkBackedImmutableMap(Map map, ImmutableList immutableList) {
        this.delegateMap = map;
        this.entries = immutableList;
    }

    @Override // java.util.Map
    public int size() {
        return this.entries.size();
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map
    public Object get(Object obj) {
        return this.delegateMap.get(obj);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet createEntrySet() {
        return new ImmutableMapEntrySet.RegularEntrySet(this, this.entries);
    }

    @Override // com.google.common.collect.ImmutableMap, java.util.Map, p017j$.util.Map
    public void forEach(final BiConsumer biConsumer) {
        Preconditions.checkNotNull(biConsumer);
        this.entries.forEach(new Consumer() { // from class: com.google.common.collect.JdkBackedImmutableMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            /* renamed from: accept */
            public final void m971v(Object obj) {
                Map.Entry entry = (Map.Entry) obj;
                biConsumer.accept(entry.getKey(), entry.getValue());
            }

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }
        });
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableSet createKeySet() {
        return new ImmutableMapKeySet(this);
    }

    @Override // com.google.common.collect.ImmutableMap
    ImmutableCollection createValues() {
        return new ImmutableMapValues(this);
    }
}
