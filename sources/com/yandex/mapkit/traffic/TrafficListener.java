package com.yandex.mapkit.traffic;

/* loaded from: classes4.dex */
public interface TrafficListener {
    void onTrafficChanged(TrafficLevel trafficLevel);

    void onTrafficExpired();

    void onTrafficLoading();
}
