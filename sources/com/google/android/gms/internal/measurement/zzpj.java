package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
public final class zzpj implements zzpi {
    public static final zzib zza;
    public static final zzib zzb;
    public static final zzib zzc;

    static {
        zzhy zzhyVarZza = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zza();
        zza = zzhyVarZza.zzf("measurement.client.sessions.check_on_reset_and_enable2", true);
        zzb = zzhyVarZza.zzf("measurement.client.sessions.check_on_startup", true);
        zzc = zzhyVarZza.zzf("measurement.client.sessions.start_session_before_view_screen", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzpi
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzpi
    public final boolean zzb() {
        return ((Boolean) zza.zzb()).booleanValue();
    }
}
