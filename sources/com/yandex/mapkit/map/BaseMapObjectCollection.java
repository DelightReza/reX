package com.yandex.mapkit.map;

/* loaded from: classes4.dex */
public interface BaseMapObjectCollection extends MapObject {
    void addListener(MapObjectCollectionListener mapObjectCollectionListener);

    void clear();

    void remove(MapObject mapObject);

    void removeListener(MapObjectCollectionListener mapObjectCollectionListener);

    void traverse(MapObjectVisitor mapObjectVisitor);
}
