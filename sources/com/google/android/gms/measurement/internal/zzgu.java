package com.google.android.gms.measurement.internal;

/* loaded from: classes4.dex */
final class zzgu implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ long zzd;
    final /* synthetic */ zzgv zze;

    zzgu(zzgv zzgvVar, String str, String str2, String str3, long j) {
        this.zze = zzgvVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        String str = this.zza;
        if (str == null) {
            this.zze.zza.zzR(this.zzb, null);
        } else {
            this.zze.zza.zzR(this.zzb, new zzir(this.zzc, str, this.zzd));
        }
    }
}
