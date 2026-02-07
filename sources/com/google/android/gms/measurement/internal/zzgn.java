package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes4.dex */
final class zzgn implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ zzgv zzb;

    zzgn(zzgv zzgvVar, zzq zzqVar) {
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
        zzhb zzhbVarZzc = zzhb.zzc(zzqVar.zzv, 100);
        zzhb zzhbVarZzq = zzlhVar.zzq(zzqVar.zza);
        zzlhVar.zzaA().zzj().zzc("Setting consent, package, consent", zzqVar.zza, zzhbVarZzc);
        zzlhVar.zzV(zzqVar.zza, zzhbVarZzc);
        if (zzhbVarZzc.zzm(zzhbVarZzq)) {
            zzlhVar.zzQ(zzqVar);
        }
    }
}
