package com.google.android.gms.measurement.internal;

/* loaded from: classes4.dex */
final class zzjx implements Runnable {
    final /* synthetic */ zzjy zza;

    zzjx(zzjy zzjyVar) {
        this.zza = zzjyVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zza.zza.zzb = null;
        this.zza.zza.zzP();
    }
}
