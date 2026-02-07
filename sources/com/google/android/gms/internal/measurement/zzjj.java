package com.google.android.gms.internal.measurement;

/* loaded from: classes4.dex */
public abstract class zzjj implements zzmh {
    public abstract zzjj zzaw(byte[] bArr, int i, int i2);

    public abstract zzjj zzax(byte[] bArr, int i, int i2, zzkn zzknVar);

    @Override // com.google.android.gms.internal.measurement.zzmh
    public final /* synthetic */ zzmh zzay(byte[] bArr) {
        return zzaw(bArr, 0, bArr.length);
    }

    @Override // com.google.android.gms.internal.measurement.zzmh
    public final /* synthetic */ zzmh zzaz(byte[] bArr, zzkn zzknVar) {
        return zzax(bArr, 0, bArr.length, zzknVar);
    }
}
