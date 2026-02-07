package com.yandex.mapkit.offline_cache;

import com.yandex.runtime.Error;

/* loaded from: classes4.dex */
public interface DataMoveListener {
    void onDataMoveCompleted();

    void onDataMoveError(Error error);

    void onDataMoveProgress(int i);
}
