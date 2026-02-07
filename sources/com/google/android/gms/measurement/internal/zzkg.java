package com.google.android.gms.measurement.internal;

import android.app.job.JobParameters;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes4.dex */
public final class zzkg {
    private final Context zza;

    public zzkg(Context context) {
        Preconditions.checkNotNull(context);
        this.zza = context;
    }

    private final zzet zzk() {
        return zzgd.zzp(this.zza, null, null).zzaA();
    }

    public final int zza(final Intent intent, int i, final int i2) {
        zzgd zzgdVarZzp = zzgd.zzp(this.zza, null, null);
        final zzet zzetVarZzaA = zzgdVarZzp.zzaA();
        if (intent == null) {
            zzetVarZzaA.zzk().zza("AppMeasurementService started with null intent");
            return 2;
        }
        String action = intent.getAction();
        zzgdVarZzp.zzay();
        zzetVarZzaA.zzj().zzc("Local AppMeasurementService called. startId, action", Integer.valueOf(i2), action);
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            zzh(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzkd
                @Override // java.lang.Runnable
                public final void run() {
                    this.zza.zzc(i2, zzetVarZzaA, intent);
                }
            });
        }
        return 2;
    }

    public final IBinder zzb(Intent intent) {
        if (intent == null) {
            zzk().zzd().zza("onBind called with null intent");
            return null;
        }
        String action = intent.getAction();
        if ("com.google.android.gms.measurement.START".equals(action)) {
            return new zzgv(zzlh.zzt(this.zza), null);
        }
        zzk().zzk().zzb("onBind received unknown action", action);
        return null;
    }

    final /* synthetic */ void zzc(int i, zzet zzetVar, Intent intent) {
        if (((zzkf) this.zza).zzc(i)) {
            zzetVar.zzj().zzb("Local AppMeasurementService processed last upload request. StartId", Integer.valueOf(i));
            zzk().zzj().zza("Completed wakeful intent.");
            ((zzkf) this.zza).zza(intent);
        }
    }

    final /* synthetic */ void zzd(zzet zzetVar, JobParameters jobParameters) {
        zzetVar.zzj().zza("AppMeasurementJobService processed last upload request.");
        ((zzkf) this.zza).zzb(jobParameters, false);
    }

    public final void zze() {
        zzgd zzgdVarZzp = zzgd.zzp(this.zza, null, null);
        zzet zzetVarZzaA = zzgdVarZzp.zzaA();
        zzgdVarZzp.zzay();
        zzetVarZzaA.zzj().zza("Local AppMeasurementService is starting up");
    }

    public final void zzf() {
        zzgd zzgdVarZzp = zzgd.zzp(this.zza, null, null);
        zzet zzetVarZzaA = zzgdVarZzp.zzaA();
        zzgdVarZzp.zzay();
        zzetVarZzaA.zzj().zza("Local AppMeasurementService is shutting down");
    }

    public final void zzg(Intent intent) {
        if (intent == null) {
            zzk().zzd().zza("onRebind called with null intent");
        } else {
            zzk().zzj().zzb("onRebind called. action", intent.getAction());
        }
    }

    public final void zzh(Runnable runnable) {
        zzlh zzlhVarZzt = zzlh.zzt(this.zza);
        zzlhVarZzt.zzaB().zzp(new zzke(this, zzlhVarZzt, runnable));
    }

    public final boolean zzi(final JobParameters jobParameters) {
        zzgd zzgdVarZzp = zzgd.zzp(this.zza, null, null);
        final zzet zzetVarZzaA = zzgdVarZzp.zzaA();
        String string = jobParameters.getExtras().getString("action");
        zzgdVarZzp.zzay();
        zzetVarZzaA.zzj().zzb("Local AppMeasurementJobService called. action", string);
        if (!"com.google.android.gms.measurement.UPLOAD".equals(string)) {
            return true;
        }
        zzh(new Runnable() { // from class: com.google.android.gms.measurement.internal.zzkc
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzd(zzetVarZzaA, jobParameters);
            }
        });
        return true;
    }

    public final boolean zzj(Intent intent) {
        if (intent == null) {
            zzk().zzd().zza("onUnbind called with null intent");
            return true;
        }
        zzk().zzj().zzb("onUnbind called for intent. action", intent.getAction());
        return true;
    }
}
