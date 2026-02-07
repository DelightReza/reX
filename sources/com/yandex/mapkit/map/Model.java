package com.yandex.mapkit.map;

import com.yandex.runtime.DataProviderWithId;

/* loaded from: classes4.dex */
public interface Model {
    ModelStyle getModelStyle();

    boolean isValid();

    void setData(DataProviderWithId dataProviderWithId, Callback callback);

    void setModelStyle(ModelStyle modelStyle);
}
