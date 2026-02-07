package com.google.android.gms.internal.cast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.Event;
import com.google.android.datatransport.Transformer;
import com.google.android.datatransport.Transport;
import com.google.android.datatransport.cct.CCTDestination;
import com.google.android.datatransport.runtime.TransportRuntime;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.internal.Logger;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes4.dex */
public final class zzg {
    Transport zzb;
    private final Context zzd;
    private final com.google.android.gms.cast.internal.zzn zze;
    private final SessionManager zzf;
    private final zzbn zzg;
    private final zzaf zzh;
    private Long zzj;
    private final ExecutorService zzk;
    private static final Logger zzc = new Logger("ClientCastAnalytics");
    public static final boolean zza = true;
    private int zzl = 1;
    private final String zzi = UUID.randomUUID().toString();

    private zzg(Context context, com.google.android.gms.cast.internal.zzn zznVar, SessionManager sessionManager, zzbn zzbnVar, zzaf zzafVar) {
        this.zzd = context;
        this.zze = zznVar;
        this.zzf = sessionManager;
        this.zzg = zzbnVar;
        this.zzh = zzafVar;
        zzec.zza();
        this.zzk = Executors.unconfigurableExecutorService(Executors.newCachedThreadPool());
    }

    public static zzg zza(Context context, com.google.android.gms.cast.internal.zzn zznVar, SessionManager sessionManager, zzbn zzbnVar, zzaf zzafVar) {
        return new zzg(context, zznVar, sessionManager, zzbnVar, zzafVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final /* synthetic */ void zzb(java.lang.String r12, int r13, android.content.SharedPreferences r14, android.os.Bundle r15) {
        /*
            r11 = this;
            com.google.android.gms.cast.framework.SessionManager r0 = r11.zzf
            com.google.android.gms.common.internal.Preconditions.checkNotNull(r0)
            com.google.android.gms.internal.cast.zzbn r0 = r11.zzg
            com.google.android.gms.cast.framework.SessionManager r1 = r11.zzf
            r2 = 3
            java.lang.Class<com.google.android.gms.cast.framework.CastSession> r3 = com.google.android.gms.cast.framework.CastSession.class
            r4 = 2
            if (r13 == r2) goto L12
            if (r13 != r4) goto L2b
            r13 = 2
        L12:
            com.google.android.gms.internal.cast.zzaf r2 = r11.zzh
            com.google.android.gms.internal.cast.zzv r5 = new com.google.android.gms.internal.cast.zzv
            r5.<init>(r11, r2, r12)
            com.google.android.gms.internal.cast.zzt r2 = new com.google.android.gms.internal.cast.zzt
            r2.<init>(r5)
            r1.addSessionManagerListener(r2, r3)
            if (r0 == 0) goto L2b
            com.google.android.gms.internal.cast.zzu r2 = new com.google.android.gms.internal.cast.zzu
            r2.<init>(r5)
            r0.zzm(r2)
        L2b:
            r2 = 1
            if (r13 == r2) goto L30
            if (r13 != r4) goto L4d
        L30:
            com.google.android.gms.internal.cast.zzaf r8 = r11.zzh
            com.google.android.gms.internal.cast.zzk r5 = new com.google.android.gms.internal.cast.zzk
            r7 = r11
            r10 = r12
            r6 = r14
            r9 = r15
            r5.<init>(r6, r7, r8, r9, r10)
            com.google.android.gms.internal.cast.zzi r12 = new com.google.android.gms.internal.cast.zzi
            r12.<init>(r5)
            r1.addSessionManagerListener(r12, r3)
            if (r0 == 0) goto L4d
            com.google.android.gms.internal.cast.zzj r12 = new com.google.android.gms.internal.cast.zzj
            r12.<init>(r5)
            r0.zzm(r12)
        L4d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzg.zzb(java.lang.String, int, android.content.SharedPreferences, android.os.Bundle):void");
    }

    public final void zzc(Bundle bundle) {
        final int i = bundle.containsKey("com.google.android.gms.cast.FLAG_CLIENT_SESSION_ANALYTICS_MODE") ? bundle.getInt("com.google.android.gms.cast.FLAG_CLIENT_SESSION_ANALYTICS_MODE", 0) : (bundle.containsKey("com.google.android.gms.cast.FLAG_CLIENT_SESSION_ANALYTICS_ENABLED") && bundle.getBoolean("com.google.android.gms.cast.FLAG_CLIENT_SESSION_ANALYTICS_ENABLED", false)) ? 1 : 0;
        boolean z = bundle.getBoolean("com.google.android.gms.cast.FLAG_CLIENT_FEATURE_USAGE_ANALYTICS_ENABLED", false);
        if (i == 0) {
            if (!z) {
                return;
            }
            i = 0;
            z = true;
        }
        final String packageName = this.zzd.getPackageName();
        String str = String.format(Locale.ROOT, "%s.%s", packageName, "client_cast_analytics_data");
        this.zzl = bundle.getLong("com.google.android.gms.cast.FLAG_FIRELOG_UPLOAD_MODE") == 0 ? 1 : 2;
        TransportRuntime.initialize(this.zzd);
        this.zzb = TransportRuntime.getInstance().newFactory(CCTDestination.INSTANCE).getTransport("CAST_SENDER_SDK", zzno.class, Encoding.m249of("proto"), new Transformer() { // from class: com.google.android.gms.internal.cast.zze
            @Override // com.google.android.datatransport.Transformer
            public final Object apply(Object obj) {
                zzno zznoVar = (zzno) obj;
                try {
                    int iZzu = zznoVar.zzu();
                    byte[] bArr = new byte[iZzu];
                    zztc zztcVarZzz = zztc.zzz(bArr, 0, iZzu);
                    zznoVar.zzJ(zztcVarZzz);
                    zztcVarZzz.zzA();
                    return bArr;
                } catch (IOException e) {
                    throw new RuntimeException("Serializing " + zznoVar.getClass().getName() + " to a byte array threw an IOException (should never happen).", e);
                }
            }
        });
        if (bundle.containsKey("com.google.android.gms.cast.FLAG_ANALYTICS_LOGGING_BUCKET_SIZE")) {
            this.zzj = Long.valueOf(bundle.getLong("com.google.android.gms.cast.FLAG_ANALYTICS_LOGGING_BUCKET_SIZE"));
        }
        final SharedPreferences sharedPreferences = this.zzd.getApplicationContext().getSharedPreferences(str, 0);
        if (i != 0) {
            final com.google.android.gms.cast.internal.zzn zznVar = this.zze;
            final String[] strArr = {"com.google.android.gms.cast.DICTIONARY_CAST_STATUS_CODES_TO_APP_SESSION_ERROR", "com.google.android.gms.cast.DICTIONARY_CAST_STATUS_CODES_TO_APP_SESSION_CHANGE_REASON"};
            zznVar.doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.cast.internal.zze
                @Override // com.google.android.gms.common.api.internal.RemoteCall
                public final void accept(Object obj, Object obj2) {
                    ((zzaj) ((zzo) obj).getService()).zzh(new zzl(zznVar, (TaskCompletionSource) obj2), strArr);
                }
            }).setFeatures(com.google.android.gms.cast.zzax.zzg).setAutoResolveMissingFeatures(false).setMethodKey(8426).build()).addOnSuccessListener(new OnSuccessListener() { // from class: com.google.android.gms.internal.cast.zzd
                @Override // com.google.android.gms.tasks.OnSuccessListener
                public final void onSuccess(Object obj) {
                    this.zza.zzb(packageName, i, sharedPreferences, (Bundle) obj);
                }
            });
        }
        if (z) {
            Preconditions.checkNotNull(sharedPreferences);
            zzo.zza(sharedPreferences, this, packageName).zze();
            zzo.zzd(zzml.CAST_CONTEXT);
        }
    }

    final /* synthetic */ void zzd(zzno zznoVar, int i) {
        zznn zznnVarZzd = zzno.zzd(zznoVar);
        zznnVarZzd.zzk(this.zzi);
        zznnVarZzd.zzf(this.zzi);
        Long l = this.zzj;
        if (l != null) {
            zznnVarZzd.zzi((int) l.longValue());
        }
        zzno zznoVar2 = (zzno) zznnVarZzd.zzq();
        int i2 = this.zzl;
        int i3 = i2 - 1;
        Event eventOfTelemetry = null;
        if (i2 == 0) {
            throw null;
        }
        if (i3 == 0) {
            eventOfTelemetry = Event.ofTelemetry(i - 1, zznoVar2);
        } else if (i3 == 1) {
            eventOfTelemetry = Event.ofData(i - 1, zznoVar2);
        }
        zzc.m297d("analytics event: %s", eventOfTelemetry);
        Preconditions.checkNotNull(eventOfTelemetry);
        Transport transport = this.zzb;
        if (transport != null) {
            transport.send(eventOfTelemetry);
        }
    }

    public final void zze(final zzno zznoVar, final int i) {
        this.zzk.execute(new Runnable() { // from class: com.google.android.gms.internal.cast.zzf
            @Override // java.lang.Runnable
            public final void run() {
                this.zza.zzd(zznoVar, i);
            }
        });
    }
}
