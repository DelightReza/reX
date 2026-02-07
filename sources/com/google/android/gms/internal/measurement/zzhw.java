package com.google.android.gms.internal.measurement;

import android.util.Log;

/* loaded from: classes4.dex */
final class zzhw extends zzib {
    zzhw(zzhy zzhyVar, String str, Double d, boolean z) {
        super(zzhyVar, "measurement.test.double_flag", d, true, null);
    }

    @Override // com.google.android.gms.internal.measurement.zzib
    final /* synthetic */ Object zza(Object obj) {
        try {
            return Double.valueOf(Double.parseDouble((String) obj));
        } catch (NumberFormatException unused) {
            Log.e("PhenotypeFlag", "Invalid double value for " + this.zzb + ": " + ((String) obj));
            return null;
        }
    }
}
