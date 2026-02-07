package com.yandex.mapkit.indoor;

import java.util.List;

/* loaded from: classes4.dex */
public interface IndoorPlan {
    String getActiveLevelId();

    List<IndoorLevel> getLevels();

    void setActiveLevelId(String str);
}
