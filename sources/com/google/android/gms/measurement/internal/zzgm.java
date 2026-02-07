package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes4.dex */
final class zzgm implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ zzgv zzb;

    zzgm(zzgv zzgvVar, zzq zzqVar) {
        this.zzb = zzgvVar;
        this.zza = zzqVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzb.zza.zzA();
        zzlh zzlhVar = this.zzb.zza;
        zzq zzqVar = this.zza;
        zzlhVar.zzaB().zzg();
        zzlhVar.zzB();
        Preconditions.checkNotEmpty(zzqVar.zza);
        zzlhVar.zzd(zzqVar);
    }
}
