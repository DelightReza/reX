package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
final class zzit implements Runnable {
    final /* synthetic */ Bundle zza;
    final /* synthetic */ zzir zzb;
    final /* synthetic */ zzir zzc;
    final /* synthetic */ long zzd;
    final /* synthetic */ zziz zze;

    zzit(zziz zzizVar, Bundle bundle, zzir zzirVar, zzir zzirVar2, long j) {
        this.zze = zzizVar;
        this.zza = bundle;
        this.zzb = zzirVar;
        this.zzc = zzirVar2;
        this.zzd = j;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zziz.zzp(this.zze, this.zza, this.zzb, this.zzc, this.zzd);
    }
}
