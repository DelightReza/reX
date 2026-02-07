package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
final class zzlz implements zzmg {
    private final zzmg[] zza;

    zzlz(zzmg... zzmgVarArr) {
        this.zza = zzmgVarArr;
    }

    @Override // com.google.android.gms.internal.measurement.zzmg
    public final zzmf zzb(Class cls) {
        zzmg[] zzmgVarArr = this.zza;
        for (int i = 0; i < 2; i++) {
            zzmg zzmgVar = zzmgVarArr[i];
            if (zzmgVar.zzc(cls)) {
                return zzmgVar.zzb(cls);
            }
        }
        throw new UnsupportedOperationException("No factory is available for message type: ".concat(cls.getName()));
    }

    @Override // com.google.android.gms.internal.measurement.zzmg
    public final boolean zzc(Class cls) {
        zzmg[] zzmgVarArr = this.zza;
        for (int i = 0; i < 2; i++) {
            if (zzmgVarArr[i].zzc(cls)) {
                return true;
            }
        }
        return false;
    }
}
