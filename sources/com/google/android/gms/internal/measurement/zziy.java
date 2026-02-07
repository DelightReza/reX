package com.google.android.gms.internal.measurement;

/* loaded from: classes.dex */
final class zziy extends zzis {
    private final zzja zza;

    zziy(zzja zzjaVar, int i) {
        super(zzjaVar.size(), i);
        this.zza = zzjaVar;
    }

    @Override // com.google.android.gms.internal.measurement.zzis
    protected final Object zza(int i) {
        return this.zza.get(i);
    }
}
