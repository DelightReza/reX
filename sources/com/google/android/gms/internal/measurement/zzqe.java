package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
public final class zzqe implements zzqd {
    public static final zzib zza;
    public static final zzib zzb;
    public static final zzib zzc;
    public static final zzib zzd;
    public static final zzib zze;

    static {
        zzhy zzhyVarZza = new zzhy(zzhq.zza("com.google.android.gms.measurement")).zza();
        zza = zzhyVarZza.zzf("measurement.test.boolean_flag", false);
        zzb = zzhyVarZza.zzc("measurement.test.double_flag", -3.0d);
        zzc = zzhyVarZza.zzd("measurement.test.int_flag", -2L);
        zzd = zzhyVarZza.zzd("measurement.test.long_flag", -1L);
        zze = zzhyVarZza.zze("measurement.test.string_flag", "---");
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final double zza() {
        return ((Double) zzb.zzb()).doubleValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzb() {
        return ((Long) zzc.zzb()).longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final long zzc() {
        return ((Long) zzd.zzb()).longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final String zzd() {
        return (String) zze.zzb();
    }

    @Override // com.google.android.gms.internal.measurement.zzqd
    public final boolean zze() {
        return ((Boolean) zza.zzb()).booleanValue();
    }
}
