package com.google.android.gms.measurement.api;

import android.os.Bundle;
import com.google.android.gms.internal.measurement.zzef;
import com.google.android.gms.measurement.internal.zzhg;
import java.util.Map;

/* loaded from: classes.dex */
public class AppMeasurementSdk {
    private final zzef zza;

    public interface OnEventListener extends zzhg {
    }

    public AppMeasurementSdk(zzef zzefVar) {
        this.zza = zzefVar;
    }

    public Map getUserProperties(String str, String str2, boolean z) {
        return this.zza.zzr(str, str2, z);
    }

    public void logEvent(String str, String str2, Bundle bundle) {
        this.zza.zzz(str, str2, bundle);
    }

    public void registerOnMeasurementEventListener(OnEventListener onEventListener) {
        this.zza.zzC(onEventListener);
    }

    public void setUserProperty(String str, String str2, Object obj) {
        this.zza.zzO(str, str2, obj, true);
    }
}
