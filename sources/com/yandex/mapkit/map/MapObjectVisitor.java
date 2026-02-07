package com.yandex.mapkit.map;

/* loaded from: classes4.dex */
public interface MapObjectVisitor {
    void onCircleVisited(CircleMapObject circleMapObject);

    void onClusterizedCollectionVisitEnd(ClusterizedPlacemarkCollection clusterizedPlacemarkCollection);

    boolean onClusterizedCollectionVisitStart(ClusterizedPlacemarkCollection clusterizedPlacemarkCollection);

    void onCollectionVisitEnd(MapObjectCollection mapObjectCollection);

    boolean onCollectionVisitStart(MapObjectCollection mapObjectCollection);

    void onPlacemarkVisited(PlacemarkMapObject placemarkMapObject);

    void onPolygonVisited(PolygonMapObject polygonMapObject);

    void onPolylineVisited(PolylineMapObject polylineMapObject);
}
