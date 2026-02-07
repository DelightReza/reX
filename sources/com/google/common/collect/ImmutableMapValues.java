package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import p017j$.util.Map;
import p017j$.util.Spliterator;
import p017j$.util.function.BiConsumer$CC;
import p017j$.util.function.Function$CC;

/* loaded from: classes4.dex */
final class ImmutableMapValues extends ImmutableCollection {
    private final ImmutableMap map;

    @Override // com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return true;
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable
    public /* synthetic */ Spliterator spliterator() {
        return Spliterator.Wrapper.convert(spliterator());
    }

    ImmutableMapValues(ImmutableMap immutableMap) {
        this.map = immutableMap;
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public int size() {
        return this.map.size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable
    public UnmodifiableIterator iterator() {
        return new UnmodifiableIterator() { // from class: com.google.common.collect.ImmutableMapValues.1
            final UnmodifiableIterator entryItr;

            {
                this.entryItr = ImmutableMapValues.this.map.entrySet().iterator();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.entryItr.hasNext();
            }

            @Override // java.util.Iterator
            public Object next() {
                return ((Map.Entry) this.entryItr.next()).getValue();
            }
        };
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable, p017j$.util.Collection
    public p017j$.util.Spliterator spliterator() {
        return CollectSpliterators.map(this.map.entrySet().spliterator(), new Function() { // from class: com.google.common.collect.ImmutableMapValues$$ExternalSyntheticLambda1
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((Map.Entry) obj).getValue();
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        });
    }

    @Override // com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean contains(Object obj) {
        return obj != null && Iterators.contains(iterator(), obj);
    }

    @Override // com.google.common.collect.ImmutableCollection
    public ImmutableList asList() {
        final ImmutableList immutableListAsList = this.map.entrySet().asList();
        return new ImmutableAsList() { // from class: com.google.common.collect.ImmutableMapValues.2
            @Override // java.util.List
            public Object get(int i) {
                return ((Map.Entry) immutableListAsList.get(i)).getValue();
            }

            @Override // com.google.common.collect.ImmutableAsList
            ImmutableCollection delegateCollection() {
                return ImmutableMapValues.this;
            }
        };
    }

    @Override // com.google.common.collect.ImmutableCollection, java.lang.Iterable, p017j$.util.Collection, p017j$.lang.InterfaceC1637a
    public void forEach(final Consumer consumer) {
        Preconditions.checkNotNull(consumer);
        Map.EL.forEach(this.map, new BiConsumer() { // from class: com.google.common.collect.ImmutableMapValues$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                consumer.m971v(obj2);
            }

            public /* synthetic */ BiConsumer andThen(BiConsumer biConsumer) {
                return BiConsumer$CC.$default$andThen(this, biConsumer);
            }
        });
    }
}
