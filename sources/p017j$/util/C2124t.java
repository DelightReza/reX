package p017j$.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import p017j$.util.Map;

/* renamed from: j$.util.t */
/* loaded from: classes2.dex */
public final class C2124t implements Map, Serializable, Map {
    private static final long serialVersionUID = -1034234728574286014L;

    /* renamed from: a */
    public final Map f1393a;

    /* renamed from: b */
    public transient Set f1394b;

    /* renamed from: c */
    public transient C1854s f1395c;

    /* renamed from: d */
    public transient Collection f1396d;

    public C2124t(Map map) {
        map.getClass();
        this.f1393a = map;
    }

    @Override // java.util.Map
    public final int size() {
        return this.f1393a.size();
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        return this.f1393a.isEmpty();
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        return this.f1393a.containsKey(obj);
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        return this.f1393a.containsValue(obj);
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        return this.f1393a.get(obj);
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map
    public final Set keySet() {
        if (this.f1394b == null) {
            this.f1394b = DesugarCollections.unmodifiableSet(this.f1393a.keySet());
        }
        return this.f1394b;
    }

    @Override // java.util.Map
    public final Set entrySet() {
        if (this.f1395c == null) {
            this.f1395c = new C1854s(this.f1393a.entrySet());
        }
        return this.f1395c;
    }

    @Override // java.util.Map
    public final Collection values() {
        if (this.f1396d == null) {
            this.f1396d = DesugarCollections.unmodifiableCollection(this.f1393a.values());
        }
        return this.f1396d;
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        return obj == this || this.f1393a.equals(obj);
    }

    @Override // java.util.Map
    public final int hashCode() {
        return this.f1393a.hashCode();
    }

    public final String toString() {
        return this.f1393a.toString();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object getOrDefault(Object obj, Object obj2) {
        return Map.EL.getOrDefault(this.f1393a, obj, obj2);
    }

    @Override // java.util.Map, p017j$.util.Map
    public final void forEach(BiConsumer biConsumer) {
        Map.EL.forEach(this.f1393a, biConsumer);
    }

    @Override // java.util.Map, p017j$.util.Map
    public final void replaceAll(BiFunction biFunction) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object putIfAbsent(Object obj, Object obj2) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.Map, p017j$.util.Map
    public final boolean remove(Object obj, Object obj2) {
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
}
