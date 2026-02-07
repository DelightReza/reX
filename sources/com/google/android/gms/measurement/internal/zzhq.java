package com.google.android.gms.measurement.internal;

/* loaded from: classes4.dex */
final class zzhq implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ Object zzc;
    final /* synthetic */ long zzd;
    final /* synthetic */ zzik zze;

    zzhq(zzik zzikVar, String str, String str2, Object obj, long j) {
        this.zze = zzikVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = obj;
        this.zzd = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zze.zzY(this.zza, this.zzb, this.zzc, this.zzd);
    }
}
