package com.google.android.gms.measurement.internal;

import java.util.List;

/* loaded from: classes4.dex */
final class zzfs implements com.google.android.gms.internal.measurement.zzr {
    final /* synthetic */ zzfu zza;

    zzfs(zzfu zzfuVar) {
        this.zza = zzfuVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzr
    public final void zza(int i, String str, List list, boolean z, boolean z2) {
        int i2 = i - 1;
        zzer zzerVarZzi = i2 != 0 ? i2 != 1 ? i2 != 3 ? i2 != 4 ? this.zza.zzt.zzaA().zzi() : z ? this.zza.zzt.zzaA().zzm() : !z2 ? this.zza.zzt.zzaA().zzl() : this.zza.zzt.zzaA().zzk() : this.zza.zzt.zzaA().zzj() : z ? this.zza.zzt.zzaA().zzh() : !z2 ? this.zza.zzt.zzaA().zze() : this.zza.zzt.zzaA().zzd() : this.zza.zzt.zzaA().zzc();
        int size = list.size();
        if (size == 1) {
            zzerVarZzi.zzb(str, list.get(0));
            return;
        }
        if (size == 2) {
            zzerVarZzi.zzc(str, list.get(0), list.get(1));
        } else if (size != 3) {
            zzerVarZzi.zza(str);
        } else {
            zzerVarZzi.zzd(str, list.get(0), list.get(1), list.get(2));
        }
    }
}
