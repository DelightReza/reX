package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
public final class zzrc implements zzrb {
    public static final zzib zza;
    public static final zzib zzb;

    static {
        zzhy zzhyVarZza = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzhyVarZza.zzf("measurement.sfmc.client", true);
        zzb = zzhyVarZza.zzf("measurement.sfmc.service", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzrb
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzrb
    public final boolean zzb() {
        return ((Boolean) zza.zzb()).booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzrb
    public final boolean zzc() {
        return ((Boolean) zzb.zzb()).booleanValue();
    }
}
