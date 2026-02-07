package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
final class zzbe implements zzbf {
    private final zzg zza;
    private final String zzb;

    public zzbe(zzg zzgVar, String str) {
        this.zza = zzgVar;
        this.zzb = str;
    }

    @Override // com.google.android.gms.internal.measurement.zzbf
    public final zzg zza(zzap zzapVar) {
        zzg zzgVarZza = this.zza.zza();
        zzgVarZza.zze(this.zzb, zzapVar);
        return zzgVarZza;
    }
}
