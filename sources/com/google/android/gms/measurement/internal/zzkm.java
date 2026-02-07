package com.google.android.gms.measurement.internal;

/* loaded from: classes4.dex */
final class zzkm extends zzan {
    final /* synthetic */ zzkn zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzkm(zzkn zzknVar, zzgy zzgyVar) {
        super(zzgyVar);
        this.zza = zzknVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzan
    public final void zzc() {
        zzkn zzknVar = this.zza;
        zzknVar.zzc.zzg();
        zzknVar.zzd(false, false, zzknVar.zzc.zzt.zzax().elapsedRealtime());
        zzknVar.zzc.zzt.zzd().zzf(zzknVar.zzc.zzt.zzax().elapsedRealtime());
    }
}
