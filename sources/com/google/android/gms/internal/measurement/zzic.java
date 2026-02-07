package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.StrictMode;
import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.collection.ArrayMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class zzic implements zzhk {
    private static final Map zza = new ArrayMap();

    static zzic zza(Context context, String str, Runnable runnable) {
        if (zzhb.zzb()) {
            throw null;
        }
        synchronized (zzic.class) {
            WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(zza.get(null));
            StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = StrictMode.allowThreadDiskReads();
            try {
                throw null;
            } finally {
            }
        }
    }

    static synchronized void zzc() {
        Map map = zza;
        Iterator it = map.values().iterator();
        if (it.hasNext()) {
            WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(it.next());
            throw null;
        }
        map.clear();
    }
}
