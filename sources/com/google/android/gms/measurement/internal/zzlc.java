package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;

/* loaded from: classes4.dex */
final class zzlc implements zzlo {
    final /* synthetic */ zzlh zza;

    zzlc(zzlh zzlhVar) {
        this.zza = zzlhVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzlo
    public final void zza(String str, String str2, Bundle bundle) {
        if (!TextUtils.isEmpty(str)) {
            this.zza.zzaB().zzp(new zzlb(this, str, "_err", bundle));
            return;
        }
        zzlh zzlhVar = this.zza;
        if (zzlhVar.zzn != null) {
            zzlhVar.zzn.zzaA().zzd().zzb("AppId not known when logging event", "_err");
        }
    }
}
