package p017j$.util.concurrent;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import p017j$.util.Collection;
import p017j$.util.Spliterator;
import p017j$.util.stream.Stream;

/* renamed from: j$.util.concurrent.r */
/* loaded from: classes2.dex */
public final class C1809r extends AbstractC1793b implements Collection {
    private static final long serialVersionUID = 2249069246763182397L;

    @Override // java.util.Collection, p017j$.util.Collection
    public final /* synthetic */ Stream parallelStream() {
        return Collection.CC.$default$parallelStream(this);
    }

    @Override // java.util.Collection
    public final /* synthetic */ java.util.stream.Stream parallelStream() {
        return Stream.Wrapper.convert(Collection.CC.$default$parallelStream(this));
    }

    @Override // java.util.Collection, java.lang.Iterable
    public final /* synthetic */ Spliterator spliterator() {
        return Spliterator.Wrapper.convert(spliterator());
    }

    @Override // java.util.Collection, p017j$.util.Collection
    public final /* synthetic */ Stream stream() {
        return Collection.CC.$default$stream(this);
    }

    @Override // java.util.Collection
    public final /* synthetic */ java.util.stream.Stream stream() {
        return Stream.Wrapper.convert(Collection.CC.$default$stream(this));
    }

    @Override // java.util.Collection, p017j$.util.Collection
    public final /* synthetic */ Object[] toArray(IntFunction intFunction) {
        return toArray((Object[]) intFunction.apply(0));
    }

    @Override // p017j$.util.concurrent.AbstractC1793b, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.f827a.containsValue(obj);
    }

    @Override // p017j$.util.concurrent.AbstractC1793b, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        AbstractC1792a abstractC1792a;
        if (obj == null) {
            return false;
        }
        Object it = iterator();
        do {
            abstractC1792a = (AbstractC1792a) it;
            if (!abstractC1792a.hasNext()) {
                return false;
            }
        } while (!obj.equals(((C1799h) it).next()));
        abstractC1792a.remove();
        return true;
    }

    @Override // p017j$.util.concurrent.AbstractC1793b, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        ConcurrentHashMap concurrentHashMap = this.f827a;
        C1802k[] c1802kArr = concurrentHashMap.f811a;
        int length = c1802kArr == null ? 0 : c1802kArr.length;
        return new C1799h(c1802kArr, length, length, concurrentHashMap, 1);
    }

    @Override // java.util.Collection
    public final boolean add(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Collection
    public final boolean addAll(java.util.Collection collection) {
        throw new UnsupportedOperationException();
    }

    @Override // p017j$.util.concurrent.AbstractC1793b, java.util.Collection
    public final boolean removeAll(java.util.Collection collection) {
        collection.getClass();
        Object it = iterator();
        boolean z = false;
        while (true) {
            AbstractC1792a abstractC1792a = (AbstractC1792a) it;
            if (!abstractC1792a.hasNext()) {
                return z;
            }
            if (collection.contains(((C1799h) it).next())) {
                abstractC1792a.remove();
                z = true;
            }
        }
    }

    @Override // java.util.Collection, p017j$.util.Collection
    public final boolean removeIf(Predicate predicate) {
        ConcurrentHashMap concurrentHashMap = this.f827a;
        predicate.getClass();
        C1802k[] c1802kArr = concurrentHashMap.f811a;
        boolean z = false;
        if (c1802kArr != null) {
            C1806o c1806o = new C1806o(c1802kArr, c1802kArr.length, 0, c1802kArr.length);
            while (true) {
                C1802k c1802kM892a = c1806o.m892a();
                if (c1802kM892a == null) {
                    break;
                }
                Object obj = c1802kM892a.f838b;
                Object obj2 = c1802kM892a.f839c;
                if (predicate.test(obj2) && concurrentHashMap.m879g(obj, null, obj2) != null) {
                    z = true;
                }
            }
        }
        return z;
    }

    @Override // java.util.Collection, java.lang.Iterable, p017j$.util.Collection
    public final p017j$.util.Spliterator spliterator() {
        ConcurrentHashMap concurrentHashMap = this.f827a;
        long jM880j = concurrentHashMap.m880j();
        C1802k[] c1802kArr = concurrentHashMap.f811a;
        int length = c1802kArr == null ? 0 : c1802kArr.length;
        return new C1800i(c1802kArr, length, 0, length, jM880j < 0 ? 0L : jM880j, 1);
    }

    @Override // java.lang.Iterable, p017j$.util.Collection, p017j$.lang.InterfaceC1637a
    public final void forEach(Consumer consumer) {
        consumer.getClass();
        C1802k[] c1802kArr = this.f827a.f811a;
        if (c1802kArr == null) {
            return;
        }
        C1806o c1806o = new C1806o(c1802kArr, c1802kArr.length, 0, c1802kArr.length);
        while (true) {
            C1802k c1802kM892a = c1806o.m892a();
            if (c1802kM892a == null) {
                return;
            } else {
                consumer.m971v(c1802kM892a.f839c);
            }
        }
    }
}
