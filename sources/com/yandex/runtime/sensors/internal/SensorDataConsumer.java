package com.yandex.runtime.sensors.internal;

import android.hardware.SensorEvent;

/* loaded from: classes4.dex */
public interface SensorDataConsumer {
    void consume(SensorEvent sensorEvent);

    void sensorUnavailable();
}
