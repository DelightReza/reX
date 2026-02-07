package com.google.android.gms.vision;

/* loaded from: classes4.dex */
public abstract class Detector {
    private final Object zza = new Object();

    public void release() {
        synchronized (this.zza) {
        }
    }
}
