package com.google.android.gms.internal.measurement;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes4.dex */
final class zzcn extends zzdu {
    final /* synthetic */ Bundle zza;
    final /* synthetic */ zzef zzb;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcn(zzef zzefVar, Bundle bundle) {
        super(zzefVar, true);
        this.zzb = zzefVar;
        this.zza = bundle;
    }

    @Override // com.google.android.gms.internal.measurement.zzdu
    final void zza() {
        ((zzcc) Preconditions.checkNotNull(this.zzb.zzj)).setConditionalUserProperty(this.zza, this.zzh);
    }
}
