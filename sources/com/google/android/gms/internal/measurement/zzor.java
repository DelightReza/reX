package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
public final class zzor implements zzoq {
    public static final zzib zza;
    public static final zzib zzb;
    public static final zzib zzc;

    static {
        zzhy zzhyVarZza = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzhyVarZza.zzf("measurement.collection.event_safelist", true);
        zzb = zzhyVarZza.zzf("measurement.service.store_null_safelist", true);
        zzc = zzhyVarZza.zzf("measurement.service.store_safelist", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzoq
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzoq
    public final boolean zzb() {
        return ((Boolean) zzb.zzb()).booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzoq
    public final boolean zzc() {
        return ((Boolean) zzc.zzb()).booleanValue();
    }
}
