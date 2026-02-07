package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
public final class zzqz implements zzqy {
    public static final zzib zza;
    public static final zzib zzb;
    public static final zzib zzc;
    public static final zzib zzd;
    public static final zzib zze;
    public static final zzib zzf;

    static {
        zzhy zzhyVarZza = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzhyVarZza.zzf("measurement.client.sessions.background_sessions_enabled", true);
        zzb = zzhyVarZza.zzf("measurement.client.sessions.enable_fix_background_engagement", false);
        zzc = zzhyVarZza.zzf("measurement.client.sessions.immediate_start_enabled_foreground", true);
        zzd = zzhyVarZza.zzf("measurement.client.sessions.remove_expired_session_properties_enabled", true);
        zze = zzhyVarZza.zzf("measurement.client.sessions.session_id_enabled", true);
        zzf = zzhyVarZza.zzd("measurement.id.client.sessions.enable_fix_background_engagement", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzqy
    public final boolean zza() {
        return ((Boolean) zzb.zzb()).booleanValue();
    }
}
