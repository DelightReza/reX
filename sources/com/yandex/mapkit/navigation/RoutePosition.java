package com.yandex.mapkit.navigation;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.geometry.PolylinePosition;

/* loaded from: classes4.dex */
public interface RoutePosition {
    RoutePosition advance(double d);

    Double distanceTo(RoutePosition routePosition);

    double distanceToFinish();

    boolean equals(RoutePosition routePosition);

    Point getPoint();

    double heading();

    boolean onRoute(String str);

    PolylinePosition positionOnRoute(String str);

    boolean precedes(RoutePosition routePosition);

    boolean precedesOrEquals(RoutePosition routePosition);

    double timeToFinish();
}
