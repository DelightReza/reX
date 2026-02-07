package com.google.android.gms.cast.framework.media;

import com.google.android.gms.cast.framework.media.RemoteMediaClient;
import com.google.android.gms.common.api.Status;
import java.util.Iterator;

/* loaded from: classes4.dex */
final class zzbi implements com.google.android.gms.cast.internal.zzas {
    final /* synthetic */ zzbk zza;

    zzbi(zzbk zzbkVar) {
        this.zza = zzbkVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004d A[LOOP:0: B:21:0x0047->B:23:0x004d, LOOP_END] */
    @Override // com.google.android.gms.cast.internal.zzas
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void zza(java.lang.String r13, long r14, int r16, java.lang.Object r17, long r18, long r20) {
        /*
            r12 = this;
            com.google.android.gms.cast.framework.media.zzbk r0 = r12.zza     // Catch: java.lang.IllegalStateException -> L2c
            com.google.android.gms.cast.framework.media.zzbl r1 = new com.google.android.gms.cast.framework.media.zzbl     // Catch: java.lang.IllegalStateException -> L2c
            com.google.android.gms.common.api.Status r2 = new com.google.android.gms.common.api.Status     // Catch: java.lang.IllegalStateException -> L2c
            r7 = r16
            r2.<init>(r7)     // Catch: java.lang.IllegalStateException -> L1c
            r3 = 1
            r4 = r17
            boolean r5 = r4 instanceof com.google.android.gms.cast.internal.zzap
            r6 = 0
            if (r3 == r5) goto L14
            r4 = r6
        L14:
            if (r4 == 0) goto L1e
            r3 = r4
            com.google.android.gms.cast.internal.zzap r3 = (com.google.android.gms.cast.internal.zzap) r3     // Catch: java.lang.IllegalStateException -> L1c
            org.json.JSONObject r3 = r3.zza     // Catch: java.lang.IllegalStateException -> L1c
            goto L1f
        L1c:
            r0 = move-exception
            goto L2f
        L1e:
            r3 = r6
        L1f:
            if (r4 == 0) goto L25
            com.google.android.gms.cast.internal.zzap r4 = (com.google.android.gms.cast.internal.zzap) r4     // Catch: java.lang.IllegalStateException -> L1c
            com.google.android.gms.cast.MediaError r6 = r4.zzb     // Catch: java.lang.IllegalStateException -> L1c
        L25:
            r1.<init>(r2, r3, r6)     // Catch: java.lang.IllegalStateException -> L1c
            r0.setResult(r1)     // Catch: java.lang.IllegalStateException -> L1c
            goto L3b
        L2c:
            r0 = move-exception
            r7 = r16
        L2f:
            com.google.android.gms.cast.internal.Logger r1 = com.google.android.gms.cast.framework.media.RemoteMediaClient.zzd()
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = "Result already set when calling onRequestCompleted"
            r1.m300e(r0, r3, r2)
        L3b:
            com.google.android.gms.cast.framework.media.zzbk r0 = r12.zza
            com.google.android.gms.cast.framework.media.RemoteMediaClient r0 = r0.zzg
            java.util.List r0 = com.google.android.gms.cast.framework.media.RemoteMediaClient.zzm(r0)
            java.util.Iterator r0 = r0.iterator()
        L47:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L60
            java.lang.Object r1 = r0.next()
            r3 = r1
            com.google.android.gms.cast.framework.media.RemoteMediaClient$Callback r3 = (com.google.android.gms.cast.framework.media.RemoteMediaClient.Callback) r3
            r4 = r13
            r5 = r14
            r8 = r18
            r10 = r20
            r3.zza(r4, r5, r7, r8, r10)
            r7 = r16
            goto L47
        L60:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.cast.framework.media.zzbi.zza(java.lang.String, long, int, java.lang.Object, long, long):void");
    }

    @Override // com.google.android.gms.cast.internal.zzas
    public final void zzb(String str, long j, long j2, long j3) {
        try {
            zzbk zzbkVar = this.zza;
            zzbkVar.setResult(new zzbj(zzbkVar, new Status(2103)));
        } catch (IllegalStateException e) {
            RemoteMediaClient.zza.m300e(e, "Result already set when calling onRequestReplaced", new Object[0]);
        }
        Iterator it = this.zza.zzg.zzj.iterator();
        while (it.hasNext()) {
            ((RemoteMediaClient.Callback) it.next()).zza(str, j, 2103, j2, j3);
        }
    }
}
