package com.yandex.mapkit.location;

/* loaded from: classes4.dex */
public interface LocationManager {
    void requestSingleUpdate(LocationListener locationListener);

    void resume();

    void subscribeForLocationUpdates(SubscriptionSettings subscriptionSettings, LocationListener locationListener);

    void suspend();

    void unsubscribe(LocationListener locationListener);
}
