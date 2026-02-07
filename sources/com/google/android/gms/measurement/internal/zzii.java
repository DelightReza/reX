package com.google.android.gms.measurement.internal;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.internal.measurement.zzra;

/* loaded from: classes4.dex */
final class zzii implements Runnable {
    final /* synthetic */ boolean zza;
    final /* synthetic */ Uri zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ String zzd;
    final /* synthetic */ zzij zze;

    zzii(zzij zzijVar, boolean z, Uri uri, String str, String str2) {
        this.zze = zzijVar;
        this.zza = z;
        this.zzb = uri;
        this.zzc = str;
        this.zzd = str2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Bundle bundleZzs;
        zzij zzijVar = this.zze;
        boolean z = this.zza;
        Uri uri = this.zzb;
        String str = this.zzc;
        String str2 = this.zzd;
        zzijVar.zza.zzg();
        try {
            zzlp zzlpVarZzv = zzijVar.zza.zzt.zzv();
            zzra.zzc();
            zzag zzagVarZzf = zzijVar.zza.zzt.zzf();
            zzef zzefVar = zzeg.zzav;
            boolean zZzs = zzagVarZzf.zzs(null, zzefVar);
            if (TextUtils.isEmpty(str2)) {
                bundleZzs = null;
            } else {
                if (!str2.contains("gclid") && !str2.contains("utm_campaign") && !str2.contains("utm_source") && !str2.contains("utm_medium") && !str2.contains("utm_id") && !str2.contains("dclid") && !str2.contains("srsltid")) {
                    if (zZzs && str2.contains("sfmc_id")) {
                        zZzs = true;
                    }
                    zzlpVarZzv.zzt.zzaA().zzc().zza("Activity created with data 'referrer' without required params");
                    bundleZzs = null;
                }
                bundleZzs = zzlpVarZzv.zzs(Uri.parse("https://google.com/search?".concat(str2)), zZzs);
                if (bundleZzs != null) {
                    bundleZzs.putString("_cis", "referrer");
                }
            }
            if (z) {
                zzlp zzlpVarZzv2 = zzijVar.zza.zzt.zzv();
                zzra.zzc();
                Bundle bundleZzs2 = zzlpVarZzv2.zzs(uri, zzijVar.zza.zzt.zzf().zzs(null, zzefVar));
                if (bundleZzs2 != null) {
                    bundleZzs2.putString("_cis", "intent");
                    if (!bundleZzs2.containsKey("gclid") && bundleZzs != null && bundleZzs.containsKey("gclid")) {
                        bundleZzs2.putString("_cer", String.format("gclid=%s", bundleZzs.getString("gclid")));
                    }
                    zzijVar.zza.zzG(str, "_cmp", bundleZzs2);
                    zzijVar.zza.zzb.zza(str, bundleZzs2);
                }
            }
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            zzijVar.zza.zzt.zzaA().zzc().zzb("Activity created with referrer", str2);
            if (zzijVar.zza.zzt.zzf().zzs(null, zzeg.zzaa)) {
                if (bundleZzs != null) {
                    zzijVar.zza.zzG(str, "_cmp", bundleZzs);
                    zzijVar.zza.zzb.zza(str, bundleZzs);
                } else {
                    zzijVar.zza.zzt.zzaA().zzc().zzb("Referrer does not contain valid parameters", str2);
                }
                zzijVar.zza.zzW("auto", "_ldl", null, true);
                return;
            }
            if (!str2.contains("gclid") || (!str2.contains("utm_campaign") && !str2.contains("utm_source") && !str2.contains("utm_medium") && !str2.contains("utm_term") && !str2.contains("utm_content"))) {
                zzijVar.zza.zzt.zzaA().zzc().zza("Activity created with data 'referrer' without required params");
            } else {
                if (TextUtils.isEmpty(str2)) {
                    return;
                }
                zzijVar.zza.zzW("auto", "_ldl", str2, true);
            }
        } catch (RuntimeException e) {
            zzijVar.zza.zzt.zzaA().zzd().zzb("Throwable caught in handleReferrerForOnActivityCreated", e);
        }
    }
}
