package com.yandex.mapkit.internal;

import com.yandex.mapkit.GeoObjectSession;
import com.yandex.runtime.NativeObject;

/* loaded from: classes4.dex */
public class GeoObjectSessionBinding implements GeoObjectSession {
    private final NativeObject nativeObject;

    @Override // com.yandex.mapkit.GeoObjectSession
    public native void cancel();

    @Override // com.yandex.mapkit.GeoObjectSession
    public native void retry(GeoObjectSession.GeoObjectListener geoObjectListener);

    protected GeoObjectSessionBinding(NativeObject nativeObject) {
        this.nativeObject = nativeObject;
    }
}
