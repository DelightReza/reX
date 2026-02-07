package com.yandex.mapkit.map;

import com.yandex.mapkit.ConflictResolutionMode;

/* loaded from: classes4.dex */
public interface RootMapObjectCollection extends MapObjectCollection {
    ConflictResolutionMode getConflictResolutionMode();

    void setConflictResolutionMode(ConflictResolutionMode conflictResolutionMode);
}
