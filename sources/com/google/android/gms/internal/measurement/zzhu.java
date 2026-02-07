package com.google.android.gms.internal.measurement;

import android.util.Log;

/* loaded from: classes4.dex */
final class zzhu extends zzib {
    zzhu(zzhy zzhyVar, String str, Long l, boolean z) {
        super(zzhyVar, str, l, true, null);
    }

    @Override // com.google.android.gms.internal.measurement.zzib
    final /* synthetic */ Object zza(Object obj) {
        try {
            return Long.valueOf(Long.parseLong((String) obj));
        } catch (NumberFormatException unused) {
            Log.e("PhenotypeFlag", "Invalid long value for " + this.zzb + ": " + ((String) obj));
            return null;
        }
    }
}
