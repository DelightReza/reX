package com.google.android.gms.internal.measurement;

import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import java.util.Collections;
import java.util.Map;

/* loaded from: classes4.dex */
public final class zzkn {
    static final zzkn zza = new zzkn(true);
    private static volatile zzkn zzd;
    private final Map zze = Collections.EMPTY_MAP;

    public final zzkz zzb(zzmi zzmiVar, int i) {
        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(this.zze.get(new zzkm(zzmiVar, i)));
        return null;
    }

    zzkn(boolean z) {
    }

    public static zzkn zza() {
        zzkn zzknVar = zzd;
        if (zzknVar != null) {
            return zzknVar;
        }
        synchronized (zzkn.class) {
            try {
                zzkn zzknVar2 = zzd;
                if (zzknVar2 != null) {
                    return zzknVar2;
                }
                zzkn zzknVarZzb = zzkv.zzb(zzkn.class);
                zzd = zzknVarZzb;
                return zzknVarZzb;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
