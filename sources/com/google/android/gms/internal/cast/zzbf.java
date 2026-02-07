package com.google.android.gms.internal.cast;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.media.session.MediaSessionCompat;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.MediaTransferReceiver;
import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
public final class zzbf extends zzal {
    private static final Logger zza = new Logger("MediaRouterProxy");
    private final MediaRouter zzb;
    private final CastOptions zzc;
    private final Map zzd = new HashMap();
    private zzbn zze;
    private boolean zzf;

    public zzbf(Context context, MediaRouter mediaRouter, final CastOptions castOptions, com.google.android.gms.cast.internal.zzn zznVar) {
        this.zzb = mediaRouter;
        this.zzc = castOptions;
        if (Build.VERSION.SDK_INT <= 32) {
            zza.m301i("Don't need to set MediaRouterParams for Android S v2 or below", new Object[0]);
            return;
        }
        zza.m297d("Set up MediaRouterParams based on module flag and CastOptions for Android T or above", new Object[0]);
        this.zze = new zzbn(castOptions);
        Intent intent = new Intent(context, (Class<?>) MediaTransferReceiver.class);
        intent.setPackage(context.getPackageName());
        boolean zIsEmpty = context.getPackageManager().queryBroadcastReceivers(intent, 0).isEmpty();
        this.zzf = !zIsEmpty;
        if (!zIsEmpty) {
            zzo.zzd(zzml.CAST_OUTPUT_SWITCHER_ENABLED);
        }
        zznVar.zza(new String[]{"com.google.android.gms.cast.FLAG_OUTPUT_SWITCHER_ENABLED"}).addOnCompleteListener(new OnCompleteListener() { // from class: com.google.android.gms.internal.cast.zzbc
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                this.zza.zzp(castOptions, task);
            }
        });
    }

    private final void zzt(MediaRouteSelector mediaRouteSelector, int i) {
        Set set = (Set) this.zzd.get(mediaRouteSelector);
        if (set == null) {
            return;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            this.zzb.addCallback(mediaRouteSelector, (MediaRouter.Callback) it.next(), i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: zzu, reason: merged with bridge method [inline-methods] */
    public final void zzq(MediaRouteSelector mediaRouteSelector) {
        Set set = (Set) this.zzd.get(mediaRouteSelector);
        if (set == null) {
            return;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            this.zzb.removeCallback((MediaRouter.Callback) it.next());
        }
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final Bundle zzb(String str) {
        for (MediaRouter.RouteInfo routeInfo : this.zzb.getRoutes()) {
            if (routeInfo.getId().equals(str)) {
                return routeInfo.getExtras();
            }
        }
        return null;
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final String zzc() {
        return this.zzb.getSelectedRoute().getId();
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final void zzd(Bundle bundle, final int i) {
        final MediaRouteSelector mediaRouteSelectorFromBundle = MediaRouteSelector.fromBundle(bundle);
        if (mediaRouteSelectorFromBundle == null) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            zzt(mediaRouteSelectorFromBundle, i);
        } else {
            new zzed(Looper.getMainLooper()).post(new Runnable() { // from class: com.google.android.gms.internal.cast.zzbe
                @Override // java.lang.Runnable
                public final void run() {
                    this.zza.zzo(mediaRouteSelectorFromBundle, i);
                }
            });
        }
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final void zze(Bundle bundle, zzao zzaoVar) {
        MediaRouteSelector mediaRouteSelectorFromBundle = MediaRouteSelector.fromBundle(bundle);
        if (mediaRouteSelectorFromBundle == null) {
            return;
        }
        if (!this.zzd.containsKey(mediaRouteSelectorFromBundle)) {
            this.zzd.put(mediaRouteSelectorFromBundle, new HashSet());
        }
        ((Set) this.zzd.get(mediaRouteSelectorFromBundle)).add(new zzat(zzaoVar));
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final void zzf() {
        Iterator it = this.zzd.values().iterator();
        while (it.hasNext()) {
            Iterator it2 = ((Set) it.next()).iterator();
            while (it2.hasNext()) {
                this.zzb.removeCallback((MediaRouter.Callback) it2.next());
            }
        }
        this.zzd.clear();
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final void zzg(Bundle bundle) {
        final MediaRouteSelector mediaRouteSelectorFromBundle = MediaRouteSelector.fromBundle(bundle);
        if (mediaRouteSelectorFromBundle == null) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            zzq(mediaRouteSelectorFromBundle);
        } else {
            new zzed(Looper.getMainLooper()).post(new Runnable() { // from class: com.google.android.gms.internal.cast.zzbd
                @Override // java.lang.Runnable
                public final void run() {
                    this.zza.zzq(mediaRouteSelectorFromBundle);
                }
            });
        }
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final void zzh() {
        MediaRouter mediaRouter = this.zzb;
        mediaRouter.selectRoute(mediaRouter.getDefaultRoute());
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final void zzi(String str) {
        zza.m297d("select route with routeId = %s", str);
        for (MediaRouter.RouteInfo routeInfo : this.zzb.getRoutes()) {
            if (routeInfo.getId().equals(str)) {
                zza.m297d("media route is found and selected", new Object[0]);
                this.zzb.selectRoute(routeInfo);
                return;
            }
        }
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final void zzj(int i) {
        this.zzb.unselect(i);
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final boolean zzk() {
        MediaRouter.RouteInfo bluetoothRoute = this.zzb.getBluetoothRoute();
        return bluetoothRoute != null && this.zzb.getSelectedRoute().getId().equals(bluetoothRoute.getId());
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final boolean zzl() {
        MediaRouter.RouteInfo defaultRoute = this.zzb.getDefaultRoute();
        return defaultRoute != null && this.zzb.getSelectedRoute().getId().equals(defaultRoute.getId());
    }

    @Override // com.google.android.gms.internal.cast.zzam
    public final boolean zzm(Bundle bundle, int i) {
        MediaRouteSelector mediaRouteSelectorFromBundle = MediaRouteSelector.fromBundle(bundle);
        if (mediaRouteSelectorFromBundle == null) {
            return false;
        }
        return this.zzb.isRouteAvailable(mediaRouteSelectorFromBundle, i);
    }

    public final zzbn zzn() {
        return this.zze;
    }

    final /* synthetic */ void zzo(MediaRouteSelector mediaRouteSelector, int i) {
        synchronized (this.zzd) {
            zzt(mediaRouteSelector, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final /* synthetic */ void zzp(com.google.android.gms.cast.framework.CastOptions r9, com.google.android.gms.tasks.Task r10) {
        /*
            r8 = this;
            boolean r0 = r10.isSuccessful()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L34
            java.lang.Object r10 = r10.getResult()
            android.os.Bundle r10 = (android.os.Bundle) r10
            java.lang.String r0 = "com.google.android.gms.cast.FLAG_OUTPUT_SWITCHER_ENABLED"
            if (r10 == 0) goto L1a
            boolean r3 = r10.containsKey(r0)
            if (r3 == 0) goto L1a
            r3 = 1
            goto L1b
        L1a:
            r3 = 0
        L1b:
            com.google.android.gms.cast.internal.Logger r4 = com.google.android.gms.internal.cast.zzbf.zza
            if (r2 == r3) goto L22
            java.lang.String r5 = "not existed"
            goto L24
        L22:
            java.lang.String r5 = "existed"
        L24:
            java.lang.Object[] r6 = new java.lang.Object[r2]
            r6[r1] = r5
            java.lang.String r5 = "The module-to-client output switcher flag %s"
            r4.m297d(r5, r6)
            if (r3 == 0) goto L34
            boolean r10 = r10.getBoolean(r0)
            goto L35
        L34:
            r10 = 1
        L35:
            com.google.android.gms.cast.internal.Logger r0 = com.google.android.gms.internal.cast.zzbf.zza
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r10)
            boolean r4 = r9.zzh()
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
            r5 = 2
            java.lang.Object[] r6 = new java.lang.Object[r5]
            r6[r1] = r3
            r6[r2] = r4
            java.lang.String r3 = "Set up output switcher flags: %b (from module), %b (from CastOptions)"
            r0.m301i(r3, r6)
            if (r10 == 0) goto L59
            boolean r9 = r9.zzh()
            if (r9 == 0) goto L59
            r9 = 1
            goto L5a
        L59:
            r9 = 0
        L5a:
            androidx.mediarouter.media.MediaRouter r10 = r8.zzb
            if (r10 == 0) goto Lbf
            com.google.android.gms.cast.framework.CastOptions r3 = r8.zzc
            if (r3 != 0) goto L63
            goto Lbf
        L63:
            boolean r4 = r3.zzf()
            boolean r3 = r3.zze()
            androidx.mediarouter.media.MediaRouterParams$Builder r6 = new androidx.mediarouter.media.MediaRouterParams$Builder
            r6.<init>()
            androidx.mediarouter.media.MediaRouterParams$Builder r6 = r6.setMediaTransferReceiverEnabled(r9)
            androidx.mediarouter.media.MediaRouterParams$Builder r6 = r6.setTransferToLocalEnabled(r4)
            androidx.mediarouter.media.MediaRouterParams$Builder r6 = r6.setOutputSwitcherEnabled(r3)
            androidx.mediarouter.media.MediaRouterParams r6 = r6.build()
            r10.setRouterParams(r6)
            boolean r10 = r8.zzf
            java.lang.Boolean r10 = java.lang.Boolean.valueOf(r10)
            java.lang.Boolean r9 = java.lang.Boolean.valueOf(r9)
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r4)
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            r7 = 4
            java.lang.Object[] r7 = new java.lang.Object[r7]
            r7[r1] = r10
            r7[r2] = r9
            r7[r5] = r6
            r9 = 3
            r7[r9] = r3
            java.lang.String r9 = "media transfer = %b, session transfer = %b, transfer to local = %b, in-app output switcher = %b"
            r0.m301i(r9, r7)
            if (r4 == 0) goto Lbf
            androidx.mediarouter.media.MediaRouter r9 = r8.zzb
            com.google.android.gms.internal.cast.zzbb r10 = new com.google.android.gms.internal.cast.zzbb
            com.google.android.gms.internal.cast.zzbn r0 = r8.zze
            java.lang.Object r0 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)
            com.google.android.gms.internal.cast.zzbn r0 = (com.google.android.gms.internal.cast.zzbn) r0
            r10.<init>(r0)
            r9.setOnPrepareTransferListener(r10)
            com.google.android.gms.internal.cast.zzml r9 = com.google.android.gms.internal.cast.zzml.CAST_TRANSFER_TO_LOCAL_ENABLED
            com.google.android.gms.internal.cast.zzo.zzd(r9)
        Lbf:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzbf.zzp(com.google.android.gms.cast.framework.CastOptions, com.google.android.gms.tasks.Task):void");
    }

    public final void zzr(MediaSessionCompat mediaSessionCompat) {
        this.zzb.setMediaSessionCompat(mediaSessionCompat);
    }

    public final boolean zzs() {
        return this.zzf;
    }
}
