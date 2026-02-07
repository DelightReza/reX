package com.google.android.gms.measurement.internal;

import android.util.Log;

/* loaded from: classes4.dex */
final class zzhh implements zzen {
    final /* synthetic */ zzgd zza;

    zzhh(zzhi zzhiVar, zzgd zzgdVar) {
        this.zza = zzgdVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzen
    public final boolean zza() {
        return this.zza.zzL() && Log.isLoggable(this.zza.zzaA().zzr(), 3);
    }
}
