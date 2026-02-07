package com.google.android.gms.internal.measurement;

import java.io.Serializable;

/* loaded from: classes4.dex */
public abstract class zzii implements Serializable {
    zzii() {
    }

    public static zzii zzc() {
        return zzie.zza;
    }

    public static zzii zzd(Object obj) {
        return new zzik(obj);
    }

    public abstract Object zza();

    public abstract boolean zzb();
}
