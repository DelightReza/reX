package com.yandex.mapkit.indoor;

/* loaded from: classes4.dex */
public interface IndoorStateListener {
    void onActiveLevelChanged(String str);

    void onActivePlanFocused(IndoorPlan indoorPlan);

    void onActivePlanLeft();
}
