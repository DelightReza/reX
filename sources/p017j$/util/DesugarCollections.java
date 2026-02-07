package p017j$.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

/* loaded from: classes2.dex */
public class DesugarCollections {
    public static <T> Collection<T> unmodifiableCollection(Collection<? extends T> collection) {
        return new C1844n(collection);
    }

    public static <T> Set<T> unmodifiableSet(Set<? extends T> set) {
        return new C2126v(set);
    }

    public static <T> List<T> unmodifiableList(List<? extends T> list) {
        if (!(list instanceof RandomAccess)) {
            return new C1848p(list);
        }
        return new C2125u(list);
    }

    public static <K, V> Map<K, V> unmodifiableMap(Map<? extends K, ? extends V> map) {
        return new C2124t(map);
    }

    public static <T> Set<T> synchronizedSet(Set<T> set) {
        return new C1840l(set);
    }

    public static <T> List<T> synchronizedList(List<T> list) {
        if (!(list instanceof RandomAccess)) {
            return new C1834i(list);
        }
        return new C1838k(list);
    }

    public static <K, V> Map<K, V> synchronizedMap(Map<K, V> map) {
        return new C1836j(map);
    }
}
