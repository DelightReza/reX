package com.google.android.gms.measurement.internal;

import java.util.Map;

/* loaded from: classes4.dex */
final class zzky implements zzev {
    final /* synthetic */ String zza;
    final /* synthetic */ zzlh zzb;

    zzky(zzlh zzlhVar, String str) {
        this.zzb = zzlhVar;
        this.zza = str;
    }

    @Override // com.google.android.gms.measurement.internal.zzev
    public final void zza(String str, int i, Throwable th, byte[] bArr, Map map) {
        this.zzb.zzK(i, th, bArr, this.zza);
    }
}
