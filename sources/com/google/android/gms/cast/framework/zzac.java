package com.google.android.gms.cast.framework;

import android.os.RemoteException;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.dynamic.IObjectWrapper;

/* loaded from: classes4.dex */
public final class zzac {
    private static final Logger zza = new Logger("DiscoveryManager");
    private final zzaq zzb;

    zzac(zzaq zzaqVar) {
        this.zzb = zzaqVar;
    }

    public final IObjectWrapper zza() {
        try {
            return this.zzb.zze();
        } catch (RemoteException e) {
            zza.m298d(e, "Unable to call %s on %s.", "getWrappedThis", zzaq.class.getSimpleName());
            return null;
        }
    }
}
