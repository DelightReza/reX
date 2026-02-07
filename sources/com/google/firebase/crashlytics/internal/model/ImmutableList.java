package com.google.firebase.crashlytics.internal.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import p017j$.util.DesugarCollections;

/* loaded from: classes4.dex */
public final class ImmutableList implements List, RandomAccess {
    private final List immutableList;

    public static ImmutableList from(Object... objArr) {
        return new ImmutableList(Arrays.asList(objArr));
    }

    public static ImmutableList from(List list) {
        return new ImmutableList(list);
    }

    private ImmutableList(List list) {
        this.immutableList = DesugarCollections.unmodifiableList(list);
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.immutableList.size();
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.immutableList.isEmpty();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        return this.immutableList.contains(obj);
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator iterator() {
        return this.immutableList.iterator();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        return this.immutableList.toArray();
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray(Object[] objArr) {
        return this.immutableList.toArray(objArr);
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(Object obj) {
        return this.immutableList.add(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        return this.immutableList.remove(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection collection) {
        return this.immutableList.containsAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection collection) {
        return this.immutableList.addAll(collection);
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection collection) {
        return this.immutableList.addAll(i, collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection collection) {
        return this.immutableList.removeAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection collection) {
        return this.immutableList.retainAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        this.immutableList.clear();
    }

    @Override // java.util.List, java.util.Collection
    public boolean equals(Object obj) {
        return this.immutableList.equals(obj);
    }

    @Override // java.util.List, java.util.Collection
    public int hashCode() {
        return this.immutableList.hashCode();
    }

    @Override // java.util.List
    public Object get(int i) {
        return this.immutableList.get(i);
    }

    @Override // java.util.List
    public Object set(int i, Object obj) {
        return this.immutableList.set(i, obj);
    }

    @Override // java.util.List
    public void add(int i, Object obj) {
        this.immutableList.add(i, obj);
    }

    @Override // java.util.List
    public Object remove(int i) {
        return this.immutableList.remove(i);
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        return this.immutableList.indexOf(obj);
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        return this.immutableList.lastIndexOf(obj);
    }

    @Override // java.util.List
    public ListIterator listIterator() {
        return this.immutableList.listIterator();
    }

    @Override // java.util.List
    public ListIterator listIterator(int i) {
        return this.immutableList.listIterator(i);
    }

    @Override // java.util.List
    public List subList(int i, int i2) {
        return this.immutableList.subList(i, i2);
    }
}
