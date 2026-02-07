package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
public final class zzpy implements zzpx {
    public static final zzib zza;
    public static final zzib zzb;
    public static final zzib zzc;

    static {
        zzhy zzhyVarZza = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zza();
        zza = zzhyVarZza.zzd("measurement.id.lifecycle.app_in_background_parameter", 0L);
        zzb = zzhyVarZza.zzf("measurement.lifecycle.app_backgrounded_tracking", true);
        zzc = zzhyVarZza.zzf("measurement.lifecycle.app_in_background_parameter", false);
    }

    @Override // com.google.android.gms.internal.measurement.zzpx
    public final boolean zza() {
        return ((Boolean) zzc.zzb()).booleanValue();
    }
}
