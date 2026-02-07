package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
final class zzlu extends zzlw {
    /* synthetic */ zzlu(zzlt zzltVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.measurement.zzlw
    final void zza(Object obj, long j) {
        ((zzli) zznu.zzf(obj, j)).zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzlw
    final void zzb(Object obj, Object obj2, long j) {
        zzli zzliVarZzd = (zzli) zznu.zzf(obj, j);
        zzli zzliVar = (zzli) zznu.zzf(obj2, j);
        int size = zzliVarZzd.size();
        int size2 = zzliVar.size();
        if (size > 0 && size2 > 0) {
            if (!zzliVarZzd.zzc()) {
                zzliVarZzd = zzliVarZzd.zzd(size2 + size);
            }
            zzliVarZzd.addAll(zzliVar);
        }
        if (size > 0) {
            zzliVar = zzliVarZzd;
        }
        zznu.zzs(obj, j, zzliVar);
    }
}
