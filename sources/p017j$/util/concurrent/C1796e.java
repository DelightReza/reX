package p017j$.util.concurrent;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import p017j$.util.Collection;
import p017j$.util.Spliterator;
import p017j$.util.stream.Stream;

/* renamed from: j$.util.concurrent.e */
/* loaded from: classes2.dex */
public final class C1796e extends AbstractC1793b implements Set, p017j$.util.Set {
    private static final long serialVersionUID = 2249069246763182397L;

    @Override // java.util.Collection, p017j$.util.Collection
    public final /* synthetic */ Stream parallelStream() {
        return Collection.CC.$default$parallelStream(this);
    }

    @Override // java.util.Collection
    public final /* synthetic */ java.util.stream.Stream parallelStream() {
        return Stream.Wrapper.convert(Collection.CC.$default$parallelStream(this));
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
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

    @Override // java.util.Collection, java.util.Set
    public final boolean add(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        return this.f827a.m878f(entry.getKey(), entry.getValue(), false) == null;
    }

    @Override // p017j$.util.concurrent.AbstractC1793b, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        Map.Entry entry;
        Object key;
        Object obj2;
        Object value;
        if (!(obj instanceof Map.Entry) || (key = (entry = (Map.Entry) obj).getKey()) == null || (obj2 = this.f827a.get(key)) == null || (value = entry.getValue()) == null) {
            return false;
        }
        return value == obj2 || value.equals(obj2);
    }

    @Override // p017j$.util.concurrent.AbstractC1793b, java.util.Collection, java.util.Set
    public final boolean remove(Object obj) {
        Map.Entry entry;
        Object key;
        Object value;
        return (obj instanceof Map.Entry) && (key = (entry = (Map.Entry) obj).getKey()) != null && (value = entry.getValue()) != null && this.f827a.remove(key, value);
    }

    @Override // p017j$.util.concurrent.AbstractC1793b, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        ConcurrentHashMap concurrentHashMap = this.f827a;
        C1802k[] c1802kArr = concurrentHashMap.f811a;
        int length = c1802kArr == null ? 0 : c1802kArr.length;
        return new C1795d(c1802kArr, length, length, concurrentHashMap);
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean addAll(java.util.Collection collection) {
        Iterator it = collection.iterator();
        boolean z = false;
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            if (this.f827a.m878f(entry.getKey(), entry.getValue(), false) == null) {
                z = true;
            }
        }
        return z;
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
                if (predicate.test(new AbstractMap.SimpleImmutableEntry(obj, obj2)) && concurrentHashMap.m879g(obj, null, obj2) != null) {
                    z = true;
                }
            }
        }
        return z;
    }

    @Override // java.util.Collection, java.util.Set
    public final int hashCode() {
        C1802k[] c1802kArr = this.f827a.f811a;
        int iHashCode = 0;
        if (c1802kArr != null) {
            C1806o c1806o = new C1806o(c1802kArr, c1802kArr.length, 0, c1802kArr.length);
            while (true) {
                C1802k c1802kM892a = c1806o.m892a();
                if (c1802kM892a == null) {
                    break;
                }
                iHashCode += c1802kM892a.hashCode();
            }
        }
        return iHashCode;
    }

    @Override // java.util.Collection, java.util.Set
    public final boolean equals(Object obj) {
        if (!(obj instanceof Set)) {
            return false;
        }
        Set set = (Set) obj;
        if (set != this) {
            return containsAll(set) && set.containsAll(this);
        }
        return true;
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set, p017j$.util.Set, p017j$.util.Collection
    public final p017j$.util.Spliterator spliterator() {
        ConcurrentHashMap concurrentHashMap = this.f827a;
        long jM880j = concurrentHashMap.m880j();
        C1802k[] c1802kArr = concurrentHashMap.f811a;
        int length = c1802kArr == null ? 0 : c1802kArr.length;
        return new C1797f(c1802kArr, length, 0, length, jM880j >= 0 ? jM880j : 0L, concurrentHashMap);
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
                consumer.m971v(new C1801j(c1802kM892a.f838b, c1802kM892a.f839c, this.f827a));
            }
        }
    }
}
