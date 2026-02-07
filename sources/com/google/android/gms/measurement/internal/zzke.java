package com.google.android.gms.measurement.internal;

/* loaded from: classes4.dex */
final class zzke implements Runnable {
    final /* synthetic */ zzlh zza;
    final /* synthetic */ Runnable zzb;

    zzke(zzkg zzkgVar, zzlh zzlhVar, Runnable runnable) {
        this.zza = zzlhVar;
        this.zzb = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zza.zzA();
        this.zza.zzz(this.zzb);
        this.zza.zzX();
    }
}
