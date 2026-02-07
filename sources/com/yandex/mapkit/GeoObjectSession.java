package com.yandex.mapkit;

import com.yandex.runtime.Error;

/* loaded from: classes4.dex */
public interface GeoObjectSession {

    public interface GeoObjectListener {
        void onGeoObjectError(Error error);

        void onGeoObjectResult(GeoObject geoObject);
    }

    void cancel();

    void retry(GeoObjectListener geoObjectListener);
}
