package com.google.android.gms.internal.measurement;

import sun.misc.Unsafe;

/* loaded from: classes4.dex */
final class zzns extends zznt {
    zzns(Unsafe unsafe) {
        super(unsafe);
    }

    @Override // com.google.android.gms.internal.measurement.zznt
    public final double zza(Object obj, long j) {
        return Double.longBitsToDouble(this.zza.getLong(obj, j));
    }

    @Override // com.google.android.gms.internal.measurement.zznt
    public final float zzb(Object obj, long j) {
        return Float.intBitsToFloat(this.zza.getInt(obj, j));
    }

    /* JADX WARN: Failed to inline method: com.google.android.gms.internal.measurement.zznu.zzi(java.lang.Object, long, boolean):void */
    /* JADX WARN: Failed to inline method: com.google.android.gms.internal.measurement.zznu.zzj(java.lang.Object, long, boolean):void */
    /* JADX WARN: Unknown register number '(r5v0 'z' boolean)' in method call: com.google.android.gms.internal.measurement.zznu.zzi(java.lang.Object, long, boolean):void */
    /* JADX WARN: Unknown register number '(r5v0 'z' boolean)' in method call: com.google.android.gms.internal.measurement.zznu.zzj(java.lang.Object, long, boolean):void */
    @Override // com.google.android.gms.internal.measurement.zznt
    public final void zzc(Object obj, long j, boolean z) {
        if (zznu.zzb) {
            zznu.zzi(obj, j, z);
        } else {
            zznu.zzj(obj, j, z);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zznt
    public final void zzd(Object obj, long j, byte b) {
        if (zznu.zzb) {
            zznu.zzD(obj, j, b);
        } else {
            zznu.zzE(obj, j, b);
        }
    }

    @Override // com.google.android.gms.internal.measurement.zznt
    public final void zze(Object obj, long j, double d) {
        this.zza.putLong(obj, j, Double.doubleToLongBits(d));
    }

    @Override // com.google.android.gms.internal.measurement.zznt
    public final void zzf(Object obj, long j, float f) {
        this.zza.putInt(obj, j, Float.floatToIntBits(f));
    }

    @Override // com.google.android.gms.internal.measurement.zznt
    public final boolean zzg(Object obj, long j) {
        return zznu.zzb ? zznu.zzt(obj, j) : zznu.zzu(obj, j);
    }
}
