package com.google.android.gms.measurement.internal;

/* loaded from: classes4.dex */
final class zzix implements Runnable {
    final /* synthetic */ zzir zza;
    final /* synthetic */ long zzb;
    final /* synthetic */ zziz zzc;

    zzix(zziz zzizVar, zzir zzirVar, long j) {
        this.zzc = zzizVar;
        this.zza = zzirVar;
        this.zzb = j;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zzc.zzB(this.zza, false, this.zzb);
        zziz zzizVar = this.zzc;
        zzizVar.zza = null;
        zzizVar.zzt.zzt().zzG(null);
    }
}
