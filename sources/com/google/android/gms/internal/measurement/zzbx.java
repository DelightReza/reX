package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
public abstract class zzbx {
    private static final zzbu zza;
    private static volatile zzbu zzb;

    static {
        zzbw zzbwVar = new zzbw(null);
        zza = zzbwVar;
        zzb = zzbwVar;
    }

    public static zzbu zza() {
        return zzb;
    }
}
