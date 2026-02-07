package com.google.android.gms.internal.cast;

import android.os.Bundle;
import com.google.android.gms.cast.internal.Logger;
import java.math.BigInteger;
import java.util.Map;

/* loaded from: classes4.dex */
public final class zzm {
    private static final Logger zza = new Logger("ApplicationAnalyticsUtils");
    private static final String zzb = "21.4.0";
    private final String zzc;
    private final Map zzd;
    private final Map zze;

    public zzm(Bundle bundle, String str) {
        this.zzc = str;
        this.zzd = zzah.zza(bundle, "com.google.android.gms.cast.DICTIONARY_CAST_STATUS_CODES_TO_APP_SESSION_ERROR");
        this.zze = zzah.zza(bundle, "com.google.android.gms.cast.DICTIONARY_CAST_STATUS_CODES_TO_APP_SESSION_CHANGE_REASON");
    }

    private final zznn zzh(zzl zzlVar) {
        long jLongValue;
        zznn zznnVarZzc = zzno.zzc();
        zznnVarZzc.zzm(zzlVar.zzd);
        int i = zzlVar.zze;
        zzlVar.zze = i + 1;
        zznnVarZzc.zzh(i);
        String str = zzlVar.zzc;
        if (str != null) {
            zznnVarZzc.zzj(str);
        }
        String str2 = zzlVar.zzh;
        if (str2 != null) {
            zznnVarZzc.zzg(str2);
        }
        zznd zzndVarZza = zzne.zza();
        zzndVarZza.zzb(zzb);
        zzndVarZza.zza(this.zzc);
        zznnVarZzc.zzb((zzne) zzndVarZza.zzq());
        zznf zznfVarZza = zzng.zza();
        if (zzlVar.zzb != null) {
            zzoj zzojVarZza = zzok.zza();
            zzojVarZza.zza(zzlVar.zzb);
            zznfVarZza.zza((zzok) zzojVarZza.zzq());
        }
        zznfVarZza.zzf(false);
        String str3 = zzlVar.zzf;
        if (str3 != null) {
            try {
                String strReplace = str3.replace("-", "");
                jLongValue = new BigInteger(strReplace.substring(0, Math.min(16, strReplace.length())), 16).longValue();
            } catch (NumberFormatException e) {
                zza.m303w(e, "receiverSessionId %s is not valid for hash", str3);
                jLongValue = 0;
            }
            zznfVarZza.zzh(jLongValue);
        }
        zznfVarZza.zzb(zzlVar.zzg);
        zznfVarZza.zzd(zzlVar.zzd());
        zznfVarZza.zze(zzlVar.zzi);
        zznnVarZzc.zzd(zznfVarZza);
        return zznnVarZzc;
    }

    private static void zzi(zznn zznnVar, boolean z) {
        zznf zznfVarZzc = zzng.zzc(zznnVar.zza());
        zznfVarZzc.zzf(z);
        zznnVar.zzd(zznfVarZzc);
    }

    public final zzno zza(zzl zzlVar) {
        return (zzno) zzh(zzlVar).zzq();
    }

    public final zzno zzb(zzl zzlVar, boolean z) {
        zznn zznnVarZzh = zzh(zzlVar);
        zzi(zznnVarZzh, z);
        return (zzno) zznnVarZzh.zzq();
    }

    public final zzno zzc(zzl zzlVar) {
        zznn zznnVarZzh = zzh(zzlVar);
        zznf zznfVarZzc = zzng.zzc(zznnVarZzh.zza());
        zznfVarZzc.zzg(10);
        zznnVarZzh.zze((zzng) zznfVarZzc.zzq());
        zzi(zznnVarZzh, true);
        return (zzno) zznnVarZzh.zzq();
    }

    public final zzno zzd(zzl zzlVar) {
        zznn zznnVarZzh = zzh(zzlVar);
        if (zzlVar.zzj == 1) {
            zznf zznfVarZzc = zzng.zzc(zznnVarZzh.zza());
            zznfVarZzc.zzg(17);
            zznnVarZzh.zze((zzng) zznfVarZzc.zzq());
        }
        return (zzno) zznnVarZzh.zzq();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final com.google.android.gms.internal.cast.zzno zze(com.google.android.gms.internal.cast.zzl r4, int r5) {
        /*
            r3 = this;
            com.google.android.gms.internal.cast.zznn r4 = r3.zzh(r4)
            com.google.android.gms.internal.cast.zzng r0 = r4.zza()
            com.google.android.gms.internal.cast.zznf r0 = com.google.android.gms.internal.cast.zzng.zzc(r0)
            java.util.Map r1 = r3.zze
            if (r1 == 0) goto L2e
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
            boolean r1 = r1.containsKey(r2)
            if (r1 != 0) goto L1b
            goto L2e
        L1b:
            java.util.Map r1 = r3.zze
            java.lang.Object r1 = r1.get(r2)
            java.lang.Integer r1 = (java.lang.Integer) r1
            java.lang.Object r1 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            int r1 = r1.intValue()
            goto L30
        L2e:
            int r1 = r5 + 10000
        L30:
            r0.zzg(r1)
            java.util.Map r1 = r3.zzd
            if (r1 == 0) goto L55
            java.lang.Integer r2 = java.lang.Integer.valueOf(r5)
            boolean r1 = r1.containsKey(r2)
            if (r1 != 0) goto L42
            goto L55
        L42:
            java.util.Map r5 = r3.zzd
            java.lang.Object r5 = r5.get(r2)
            java.lang.Integer r5 = (java.lang.Integer) r5
            java.lang.Object r5 = com.google.android.gms.common.internal.Preconditions.checkNotNull(r5)
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            goto L57
        L55:
            int r5 = r5 + 10000
        L57:
            r0.zzc(r5)
            com.google.android.gms.internal.cast.zztp r5 = r0.zzq()
            com.google.android.gms.internal.cast.zzng r5 = (com.google.android.gms.internal.cast.zzng) r5
            r4.zze(r5)
            com.google.android.gms.internal.cast.zztp r4 = r4.zzq()
            com.google.android.gms.internal.cast.zzno r4 = (com.google.android.gms.internal.cast.zzno) r4
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.cast.zzm.zze(com.google.android.gms.internal.cast.zzl, int):com.google.android.gms.internal.cast.zzno");
    }

    public final zzno zzf(zzl zzlVar, int i, int i2) {
        zznn zznnVarZzh = zzh(zzlVar);
        zznf zznfVarZzc = zzng.zzc(zznnVarZzh.zza());
        zznfVarZzc.zzj(i);
        zznfVarZzc.zzi(i2);
        zznnVarZzh.zze((zzng) zznfVarZzc.zzq());
        return (zzno) zznnVarZzh.zzq();
    }

    public final zzno zzg(zzl zzlVar, int i) {
        zznn zznnVarZzh = zzh(zzlVar);
        zznf zznfVarZzc = zzng.zzc(zznnVarZzh.zza());
        zznfVarZzc.zzj(i);
        zznnVarZzh.zze((zzng) zznfVarZzc.zzq());
        return (zzno) zznnVarZzh.zzq();
    }
}
