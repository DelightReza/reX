package com.google.android.gms.measurement.internal;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.internal.measurement.zzrd;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class zzkw extends zzkt {
    zzkw(zzlh zzlhVar) {
        super(zzlhVar);
    }

    private final String zzd(String str) throws Throwable {
        String strZzi = this.zzf.zzm().zzi(str);
        if (TextUtils.isEmpty(strZzi)) {
            return (String) zzeg.zzq.zza(null);
        }
        Uri uri = Uri.parse((String) zzeg.zzq.zza(null));
        Uri.Builder builderBuildUpon = uri.buildUpon();
        builderBuildUpon.authority(strZzi + "." + uri.getAuthority());
        return builderBuildUpon.build().toString();
    }

    public final zzkv zza(String str) {
        zzrd.zzc();
        zzkv zzkvVar = null;
        if (this.zzt.zzf().zzs(null, zzeg.zzaq)) {
            this.zzt.zzaA().zzj().zza("sgtm feature flag enabled.");
            zzh zzhVarZzj = this.zzf.zzh().zzj(str);
            if (zzhVarZzj == null) {
                return new zzkv(zzd(str));
            }
            if (zzhVarZzj.zzap()) {
                this.zzt.zzaA().zzj().zza("sgtm upload enabled in manifest.");
                com.google.android.gms.internal.measurement.zzff zzffVarZze = this.zzf.zzm().zze(zzhVarZzj.zzv());
                if (zzffVarZze != null) {
                    String strZzj = zzffVarZze.zzj();
                    if (!TextUtils.isEmpty(strZzj)) {
                        String strZzi = zzffVarZze.zzi();
                        this.zzt.zzaA().zzj().zzc("sgtm configured with upload_url, server_info", strZzj, true != TextUtils.isEmpty(strZzi) ? "N" : "Y");
                        if (TextUtils.isEmpty(strZzi)) {
                            this.zzt.zzay();
                            zzkvVar = new zzkv(strZzj);
                        } else {
                            HashMap map = new HashMap();
                            map.put("x-google-sgtm-server-info", strZzi);
                            zzkvVar = new zzkv(strZzj, map);
                        }
                    }
                }
            }
            if (zzkvVar != null) {
                return zzkvVar;
            }
        }
        return new zzkv(zzd(str));
    }
}
