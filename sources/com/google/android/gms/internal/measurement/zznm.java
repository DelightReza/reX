package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
final class zznm extends zznk {
    zznm() {
    }

    @Override // com.google.android.gms.internal.measurement.zznk
    final /* synthetic */ int zza(Object obj) {
        return ((zznl) obj).zza();
    }

    @Override // com.google.android.gms.internal.measurement.zznk
    final /* synthetic */ int zzb(Object obj) {
        return ((zznl) obj).zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zznk
    final /* bridge */ /* synthetic */ Object zzc(Object obj) {
        zzlb zzlbVar = (zzlb) obj;
        zznl zznlVar = zzlbVar.zzc;
        if (zznlVar != zznl.zzc()) {
            return zznlVar;
        }
        zznl zznlVarZzf = zznl.zzf();
        zzlbVar.zzc = zznlVarZzf;
        return zznlVarZzf;
    }

    @Override // com.google.android.gms.internal.measurement.zznk
    final /* synthetic */ Object zzd(Object obj) {
        return ((zzlb) obj).zzc;
    }

    @Override // com.google.android.gms.internal.measurement.zznk
    final /* bridge */ /* synthetic */ Object zze(Object obj, Object obj2) {
        if (!zznl.zzc().equals(obj2)) {
            if (zznl.zzc().equals(obj)) {
                return zznl.zze((zznl) obj, (zznl) obj2);
            }
            ((zznl) obj).zzd((zznl) obj2);
        }
        return obj;
    }

    @Override // com.google.android.gms.internal.measurement.zznk
    final /* bridge */ /* synthetic */ void zzf(Object obj, int i, long j) {
        ((zznl) obj).zzj(i << 3, Long.valueOf(j));
    }

    @Override // com.google.android.gms.internal.measurement.zznk
    final void zzg(Object obj) {
        ((zzlb) obj).zzc.zzh();
    }

    @Override // com.google.android.gms.internal.measurement.zznk
    final /* synthetic */ void zzh(Object obj, Object obj2) {
        ((zzlb) obj).zzc = (zznl) obj2;
    }

    @Override // com.google.android.gms.internal.measurement.zznk
    final /* synthetic */ void zzi(Object obj, zzoc zzocVar) {
        ((zznl) obj).zzk(zzocVar);
    }
}
