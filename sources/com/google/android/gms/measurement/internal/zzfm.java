package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes4.dex */
public final class zzfm {
    private final zza zza;

    public interface zza {
        void doStartService(Context context, Intent intent);
    }

    public zzfm(zza zzaVar) {
        Preconditions.checkNotNull(zzaVar);
        this.zza = zzaVar;
    }

    public final void zza(Context context, Intent intent) {
        zzgd zzgdVarZzp = zzgd.zzp(context, null, null);
        zzet zzetVarZzaA = zzgdVarZzp.zzaA();
        if (intent == null) {
            zzetVarZzaA.zzk().zza("Receiver called with null intent");
            return;
        }
        zzgdVarZzp.zzay();
        String action = intent.getAction();
        zzetVarZzaA.zzj().zzb("Local receiver got", action);
        if (!"com.google.android.gms.measurement.UPLOAD".equals(action)) {
            if ("com.android.vending.INSTALL_REFERRER".equals(action)) {
                zzetVarZzaA.zzk().zza("Install Referrer Broadcasts are deprecated");
            }
        } else {
            Intent className = new Intent().setClassName(context, "com.google.android.gms.measurement.AppMeasurementService");
            className.setAction("com.google.android.gms.measurement.UPLOAD");
            zzetVarZzaA.zzj().zza("Starting wakeful intent.");
            this.zza.doStartService(context, className);
        }
    }
}
