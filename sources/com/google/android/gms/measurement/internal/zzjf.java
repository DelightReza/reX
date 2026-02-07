package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes4.dex */
final class zzjf implements Runnable {
    final /* synthetic */ zzq zza;
    final /* synthetic */ com.google.android.gms.internal.measurement.zzcf zzb;
    final /* synthetic */ zzjz zzc;

    zzjf(zzjz zzjzVar, zzq zzqVar, com.google.android.gms.internal.measurement.zzcf zzcfVar) {
        this.zzc = zzjzVar;
        this.zza = zzqVar;
        this.zzb = zzcfVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzgd zzgdVar;
        String strZzd = null;
        try {
            try {
                if (this.zzc.zzt.zzm().zzc().zzj(zzha.ANALYTICS_STORAGE)) {
                    zzjz zzjzVar = this.zzc;
                    zzej zzejVar = zzjzVar.zzb;
                    if (zzejVar == null) {
                        zzjzVar.zzt.zzaA().zzd().zza("Failed to get app instance id");
                        zzgdVar = this.zzc.zzt;
                    } else {
                        Preconditions.checkNotNull(this.zza);
                        strZzd = zzejVar.zzd(this.zza);
                        if (strZzd != null) {
                            this.zzc.zzt.zzq().zzO(strZzd);
                            this.zzc.zzt.zzm().zze.zzb(strZzd);
                        }
                        this.zzc.zzQ();
                        zzgdVar = this.zzc.zzt;
                    }
                } else {
                    this.zzc.zzt.zzaA().zzl().zza("Analytics storage consent denied; will not get app instance id");
                    this.zzc.zzt.zzq().zzO(null);
                    this.zzc.zzt.zzm().zze.zzb(null);
                    zzgdVar = this.zzc.zzt;
                }
            } catch (RemoteException e) {
                this.zzc.zzt.zzaA().zzd().zzb("Failed to get app instance id", e);
                zzgdVar = this.zzc.zzt;
            }
            zzgdVar.zzv().zzW(this.zzb, strZzd);
        } catch (Throwable th) {
            this.zzc.zzt.zzv().zzW(this.zzb, null);
            throw th;
        }
    }
}
