package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
public final class zzqw implements zzqv {
    public static final zzib zza;
    public static final zzib zzb;
    public static final zzib zzc;
    public static final zzib zzd;

    static {
        zzhy zzhyVarZza = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzhyVarZza.zzf("measurement.collection.enable_session_stitching_token.client.dev", true);
        zzb = zzhyVarZza.zzf("measurement.collection.enable_session_stitching_token.first_open_fix", true);
        zzc = zzhyVarZza.zzf("measurement.session_stitching_token_enabled", false);
        zzd = zzhyVarZza.zzf("measurement.link_sst_to_sid", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzqv
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzqv
    public final boolean zzb() {
        return ((Boolean) zza.zzb()).booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqv
    public final boolean zzc() {
        return ((Boolean) zzb.zzb()).booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqv
    public final boolean zzd() {
        return ((Boolean) zzc.zzb()).booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqv
    public final boolean zze() {
        return ((Boolean) zzd.zzb()).booleanValue();
    }
}
