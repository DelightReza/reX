package com.google.common.collect;

import com.google.common.base.Predicate;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import p017j$.util.Collection;

/* loaded from: classes.dex */
public abstract class Iterables {
    public static boolean removeIf(Iterable iterable, Predicate predicate) {
        if (iterable instanceof Collection) {
            return Collection.EL.removeIf((java.util.Collection) iterable, predicate);
        }
        return Iterators.removeIf(iterable.iterator(), predicate);
    }

    public static Object getOnlyElement(Iterable iterable) {
        return Iterators.getOnlyElement(iterable.iterator());
    }

    static Object[] toArray(Iterable iterable, Object[] objArr) {
        return castOrCopyToCollection(iterable).toArray(objArr);
    }

    private static java.util.Collection castOrCopyToCollection(Iterable iterable) {
        if (iterable instanceof java.util.Collection) {
            return (java.util.Collection) iterable;
        }
        return Lists.newArrayList(iterable.iterator());
    }

    public static boolean any(Iterable iterable, Predicate predicate) {
        return Iterators.any(iterable.iterator(), predicate);
    }

    public static Object getFirst(Iterable iterable, Object obj) {
        return Iterators.getNext(iterable.iterator(), obj);
    }

    public static Object getLast(Iterable iterable) {
        if (iterable instanceof List) {
            List list = (List) iterable;
            if (list.isEmpty()) {
                throw new NoSuchElementException();
            }
            return getLastInNonemptyList(list);
        }
        return Iterators.getLast(iterable.iterator());
    }

    public static Object getLast(Iterable iterable, Object obj) {
        if (iterable instanceof java.util.Collection) {
            if (((java.util.Collection) iterable).isEmpty()) {
                return obj;
            }
            if (iterable instanceof List) {
                return getLastInNonemptyList(Lists.cast(iterable));
            }
        }
        return Iterators.getLast(iterable.iterator(), obj);
    }

    private static Object getLastInNonemptyList(List list) {
        return list.get(list.size() - 1);
    }
}
