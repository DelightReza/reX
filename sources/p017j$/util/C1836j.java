package p017j$.util;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.util.Map;
import p017j$.util.concurrent.C1810s;
import p017j$.util.concurrent.InterfaceC1811t;

/* renamed from: j$.util.j */
/* loaded from: classes2.dex */
public final class C1836j implements Map, Serializable, Map {
    private static final long serialVersionUID = 1978198479659022715L;

    /* renamed from: a */
    public final Map f927a;

    /* renamed from: b */
    public final C1836j f928b = this;

    /* renamed from: c */
    public transient C1840l f929c;

    /* renamed from: d */
    public transient C1840l f930d;

    /* renamed from: e */
    public transient C1832h f931e;

    public C1836j(Map map) {
        this.f927a = (Map) Objects.requireNonNull(map);
    }

    @Override // java.util.Map
    public final int size() {
        int size;
        synchronized (this.f928b) {
            size = this.f927a.size();
        }
        return size;
    }

    @Override // java.util.Map
    public final boolean isEmpty() {
        boolean zIsEmpty;
        synchronized (this.f928b) {
            zIsEmpty = this.f927a.isEmpty();
        }
        return zIsEmpty;
    }

    @Override // java.util.Map
    public final boolean containsKey(Object obj) {
        boolean zContainsKey;
        synchronized (this.f928b) {
            zContainsKey = this.f927a.containsKey(obj);
        }
        return zContainsKey;
    }

    @Override // java.util.Map
    public final boolean containsValue(Object obj) {
        boolean zContainsValue;
        synchronized (this.f928b) {
            zContainsValue = this.f927a.containsValue(obj);
        }
        return zContainsValue;
    }

    @Override // java.util.Map
    public final Object get(Object obj) {
        Object obj2;
        synchronized (this.f928b) {
            obj2 = this.f927a.get(obj);
        }
        return obj2;
    }

    @Override // java.util.Map
    public final Object put(Object obj, Object obj2) {
        Object objPut;
        synchronized (this.f928b) {
            objPut = this.f927a.put(obj, obj2);
        }
        return objPut;
    }

    @Override // java.util.Map
    public final Object remove(Object obj) {
        Object objRemove;
        synchronized (this.f928b) {
            objRemove = this.f927a.remove(obj);
        }
        return objRemove;
    }

    @Override // java.util.Map
    public final void putAll(Map map) {
        synchronized (this.f928b) {
            this.f927a.putAll(map);
        }
    }

    @Override // java.util.Map
    public final void clear() {
        synchronized (this.f928b) {
            this.f927a.clear();
        }
    }

    @Override // java.util.Map
    public final Set keySet() {
        C1840l c1840l;
        synchronized (this.f928b) {
            try {
                if (this.f929c == null) {
                    this.f929c = new C1840l(this.f927a.keySet(), this.f928b);
                }
                c1840l = this.f929c;
            } catch (Throwable th) {
                throw th;
            }
        }
        return c1840l;
    }

    @Override // java.util.Map
    public final Set entrySet() {
        C1840l c1840l;
        synchronized (this.f928b) {
            try {
                if (this.f930d == null) {
                    this.f930d = new C1840l(this.f927a.entrySet(), this.f928b);
                }
                c1840l = this.f930d;
            } catch (Throwable th) {
                throw th;
            }
        }
        return c1840l;
    }

    @Override // java.util.Map
    public final Collection values() {
        C1832h c1832h;
        synchronized (this.f928b) {
            try {
                if (this.f931e == null) {
                    this.f931e = new C1832h(this.f927a.values(), this.f928b);
                }
                c1832h = this.f931e;
            } catch (Throwable th) {
                throw th;
            }
        }
        return c1832h;
    }

    @Override // java.util.Map
    public final boolean equals(Object obj) {
        boolean zEquals;
        if (this == obj) {
            return true;
        }
        synchronized (this.f928b) {
            zEquals = this.f927a.equals(obj);
        }
        return zEquals;
    }

    @Override // java.util.Map
    public final int hashCode() {
        int iHashCode;
        synchronized (this.f928b) {
            iHashCode = this.f927a.hashCode();
        }
        return iHashCode;
    }

    public final String toString() {
        String string;
        synchronized (this.f928b) {
            string = this.f927a.toString();
        }
        return string;
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object getOrDefault(Object obj, Object obj2) {
        Object orDefault;
        synchronized (this.f928b) {
            orDefault = Map.EL.getOrDefault(this.f927a, obj, obj2);
        }
        return orDefault;
    }

    @Override // java.util.Map, p017j$.util.Map
    public final void forEach(BiConsumer biConsumer) {
        synchronized (this.f928b) {
            Map.EL.forEach(this.f927a, biConsumer);
        }
    }

    @Override // java.util.Map, p017j$.util.Map
    public final void replaceAll(BiFunction biFunction) {
        synchronized (this.f928b) {
            java.util.Map map = this.f927a;
            if (map instanceof Map) {
                ((Map) map).replaceAll(biFunction);
            } else if (map instanceof ConcurrentMap) {
                ConcurrentMap concurrentMap = (ConcurrentMap) map;
                Objects.requireNonNull(biFunction);
                C1810s c1810s = new C1810s(0, concurrentMap, biFunction);
                if (concurrentMap instanceof InterfaceC1811t) {
                    ((InterfaceC1811t) concurrentMap).forEach(c1810s);
                } else {
                    AbstractC1636a.m512h(concurrentMap, c1810s);
                }
            } else {
                Map.CC.$default$replaceAll(map, biFunction);
            }
        }
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object putIfAbsent(Object obj, Object obj2) {
        Object objPutIfAbsent;
        synchronized (this.f928b) {
            objPutIfAbsent = Map.EL.putIfAbsent(this.f927a, obj, obj2);
        }
        return objPutIfAbsent;
    }

    @Override // java.util.Map, p017j$.util.Map
    public final boolean remove(Object obj, Object obj2) {
        boolean zRemove;
        synchronized (this.f928b) {
            java.util.Map map = this.f927a;
            zRemove = map instanceof Map ? ((Map) map).remove(obj, obj2) : Map.CC.$default$remove(map, obj, obj2);
        }
        return zRemove;
    }

    @Override // java.util.Map, p017j$.util.Map
    public final boolean replace(Object obj, Object obj2, Object obj3) {
        boolean zReplace;
        synchronized (this.f928b) {
            java.util.Map map = this.f927a;
            zReplace = map instanceof Map ? ((Map) map).replace(obj, obj2, obj3) : Map.CC.$default$replace(map, obj, obj2, obj3);
        }
        return zReplace;
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object replace(Object obj, Object obj2) {
        Object objReplace;
        synchronized (this.f928b) {
            java.util.Map map = this.f927a;
            objReplace = map instanceof Map ? ((Map) map).replace(obj, obj2) : Map.CC.$default$replace(map, obj, obj2);
        }
        return objReplace;
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object computeIfAbsent(Object obj, Function function) {
        Object objComputeIfAbsent;
        synchronized (this.f928b) {
            objComputeIfAbsent = Map.EL.computeIfAbsent(this.f927a, obj, function);
        }
        return objComputeIfAbsent;
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object computeIfPresent(Object obj, BiFunction biFunction) {
        Object obj$default$computeIfPresent;
        Object objApply;
        synchronized (this.f928b) {
            java.util.Map map = this.f927a;
            if (map instanceof Map) {
                obj$default$computeIfPresent = ((Map) map).computeIfPresent(obj, biFunction);
            } else if (map instanceof ConcurrentMap) {
                ConcurrentMap concurrentMap = (ConcurrentMap) map;
                Objects.requireNonNull(biFunction);
                while (true) {
                    Object obj2 = concurrentMap.get(obj);
                    if (obj2 == null) {
                        obj$default$computeIfPresent = null;
                        break;
                    }
                    objApply = biFunction.apply(obj, obj2);
                    if (objApply == null) {
                        if (concurrentMap.remove(obj, obj2)) {
                            break;
                        }
                    } else if (concurrentMap.replace(obj, obj2, objApply)) {
                        break;
                    }
                }
                obj$default$computeIfPresent = objApply;
            } else {
                obj$default$computeIfPresent = Map.CC.$default$computeIfPresent(map, obj, biFunction);
            }
        }
        return obj$default$computeIfPresent;
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object compute(Object obj, BiFunction biFunction) {
        Object objCompute;
        synchronized (this.f928b) {
            objCompute = Map.EL.compute(this.f927a, obj, biFunction);
        }
        return objCompute;
    }

    @Override // java.util.Map, p017j$.util.Map
    public final Object merge(Object obj, Object obj2, BiFunction biFunction) {
        Object objM855a;
        synchronized (this.f928b) {
            objM855a = Map.EL.m855a(this.f927a, obj, obj2, biFunction);
        }
        return objM855a;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) {
        synchronized (this.f928b) {
            objectOutputStream.defaultWriteObject();
        }
    }
}
