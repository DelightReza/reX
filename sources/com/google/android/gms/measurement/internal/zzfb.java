package com.google.android.gms.measurement.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes4.dex */
final class zzfb extends BroadcastReceiver {
    private final zzlh zzb;
    private boolean zzc;
    private boolean zzd;

    zzfb(zzlh zzlhVar) {
        Preconditions.checkNotNull(zzlhVar);
        this.zzb = zzlhVar;
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        this.zzb.zzB();
        String action = intent.getAction();
        this.zzb.zzaA().zzj().zzb("NetworkBroadcastReceiver received action", action);
        if (!"android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            this.zzb.zzaA().zzk().zzb("NetworkBroadcastReceiver received unknown action", action);
            return;
        }
        boolean zZza = this.zzb.zzj().zza();
        if (this.zzd != zZza) {
            this.zzd = zZza;
            this.zzb.zzaB().zzp(new zzfa(this, zZza));
        }
    }

    public final void zzb() {
        this.zzb.zzB();
        this.zzb.zzaB().zzg();
        if (this.zzc) {
            return;
        }
        this.zzb.zzaw().registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.zzd = this.zzb.zzj().zza();
        this.zzb.zzaA().zzj().zzb("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.zzd));
        this.zzc = true;
    }

    public final void zzc() {
        this.zzb.zzB();
        this.zzb.zzaB().zzg();
        this.zzb.zzaB().zzg();
        if (this.zzc) {
            this.zzb.zzaA().zzj().zza("Unregistering connectivity change receiver");
            this.zzc = false;
            this.zzd = false;
            try {
                this.zzb.zzaw().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                this.zzb.zzaA().zzd().zzb("Failed to unregister the network broadcast receiver", e);
            }
        }
    }
}
