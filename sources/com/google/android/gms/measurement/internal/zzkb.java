package com.google.android.gms.measurement.internal;

import android.content.pm.PackageManager;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public final class zzkb extends zzku {
    public final zzfe zza;
    public final zzfe zzb;
    public final zzfe zzc;
    public final zzfe zzd;
    public final zzfe zze;
    private final Map zzg;

    zzkb(zzlh zzlhVar) {
        super(zzlhVar);
        this.zzg = new HashMap();
        zzfi zzfiVarZzm = this.zzt.zzm();
        zzfiVarZzm.getClass();
        this.zza = new zzfe(zzfiVarZzm, "last_delete_stale", 0L);
        zzfi zzfiVarZzm2 = this.zzt.zzm();
        zzfiVarZzm2.getClass();
        this.zzb = new zzfe(zzfiVarZzm2, "backoff", 0L);
        zzfi zzfiVarZzm3 = this.zzt.zzm();
        zzfiVarZzm3.getClass();
        this.zzc = new zzfe(zzfiVarZzm3, "last_upload", 0L);
        zzfi zzfiVarZzm4 = this.zzt.zzm();
        zzfiVarZzm4.getClass();
        this.zzd = new zzfe(zzfiVarZzm4, "last_upload_attempt", 0L);
        zzfi zzfiVarZzm5 = this.zzt.zzm();
        zzfiVarZzm5.getClass();
        this.zze = new zzfe(zzfiVarZzm5, "midnight_offset", 0L);
    }

    final Pair zza(String str) {
        zzka zzkaVar;
        AdvertisingIdClient.Info advertisingIdInfo;
        zzg();
        long jElapsedRealtime = this.zzt.zzax().elapsedRealtime();
        zzka zzkaVar2 = (zzka) this.zzg.get(str);
        if (zzkaVar2 != null && jElapsedRealtime < zzkaVar2.zzc) {
            return new Pair(zzkaVar2.zza, Boolean.valueOf(zzkaVar2.zzb));
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        long jZzi = this.zzt.zzf().zzi(str, zzeg.zza) + jElapsedRealtime;
        try {
            long jZzi2 = this.zzt.zzf().zzi(str, zzeg.zzb);
            if (jZzi2 > 0) {
                try {
                    advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.zzt.zzaw());
                } catch (PackageManager.NameNotFoundException unused) {
                    if (zzkaVar2 != null && jElapsedRealtime < zzkaVar2.zzc + jZzi2) {
                        return new Pair(zzkaVar2.zza, Boolean.valueOf(zzkaVar2.zzb));
                    }
                    advertisingIdInfo = null;
                }
            } else {
                advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.zzt.zzaw());
            }
        } catch (Exception e) {
            this.zzt.zzaA().zzc().zzb("Unable to get advertising id", e);
            zzkaVar = new zzka("", false, jZzi);
        }
        if (advertisingIdInfo == null) {
            return new Pair("00000000-0000-0000-0000-000000000000", Boolean.FALSE);
        }
        String id = advertisingIdInfo.getId();
        zzkaVar = id != null ? new zzka(id, advertisingIdInfo.isLimitAdTrackingEnabled(), jZzi) : new zzka("", advertisingIdInfo.isLimitAdTrackingEnabled(), jZzi);
        this.zzg.put(str, zzkaVar);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair(zzkaVar.zza, Boolean.valueOf(zzkaVar.zzb));
    }

    @Override // com.google.android.gms.measurement.internal.zzku
    protected final boolean zzb() {
        return false;
    }

    final Pair zzd(String str, zzhb zzhbVar) {
        return zzhbVar.zzj(zzha.AD_STORAGE) ? zza(str) : new Pair("", Boolean.FALSE);
    }

    final String zzf(String str, boolean z) throws NoSuchAlgorithmException {
        zzg();
        String str2 = z ? (String) zza(str).first : "00000000-0000-0000-0000-000000000000";
        MessageDigest messageDigestZzF = zzlp.zzF();
        if (messageDigestZzF == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigestZzF.digest(str2.getBytes())));
    }
}
