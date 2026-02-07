package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class zzl extends zzam {
    private final zzab zzb;

    public zzl(zzab zzabVar) {
        this.zzb = zzabVar;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.google.android.gms.internal.measurement.zzam, com.google.android.gms.internal.measurement.zzap
    public final zzap zzbU(String str, zzg zzgVar, List list) {
        switch (str.hashCode()) {
            case 21624207:
                if (str.equals("getEventName")) {
                    zzh.zzh("getEventName", 0, list);
                    return new zzat(this.zzb.zzb().zzd());
                }
                break;
            case 45521504:
                if (str.equals("getTimestamp")) {
                    zzh.zzh("getTimestamp", 0, list);
                    return new zzah(Double.valueOf(this.zzb.zzb().zza()));
                }
                break;
            case 146575578:
                if (str.equals("getParamValue")) {
                    zzh.zzh("getParamValue", 1, list);
                    return zzi.zzb(this.zzb.zzb().zzc(zzgVar.zzb((zzap) list.get(0)).zzi()));
                }
                break;
            case 700587132:
                if (str.equals("getParams")) {
                    zzh.zzh("getParams", 0, list);
                    Map mapZze = this.zzb.zzb().zze();
                    zzam zzamVar = new zzam();
                    for (String str2 : mapZze.keySet()) {
                        zzamVar.zzr(str2, zzi.zzb(mapZze.get(str2)));
                    }
                    return zzamVar;
                }
                break;
            case 920706790:
                if (str.equals("setParamValue")) {
                    zzh.zzh("setParamValue", 2, list);
                    String strZzi = zzgVar.zzb((zzap) list.get(0)).zzi();
                    zzap zzapVarZzb = zzgVar.zzb((zzap) list.get(1));
                    this.zzb.zzb().zzg(strZzi, zzh.zzf(zzapVarZzb));
                    return zzapVarZzb;
                }
                break;
            case 1570616835:
                if (str.equals("setEventName")) {
                    zzh.zzh("setEventName", 1, list);
                    zzap zzapVarZzb2 = zzgVar.zzb((zzap) list.get(0));
                    if (zzap.zzf.equals(zzapVarZzb2) || zzap.zzg.equals(zzapVarZzb2)) {
                        throw new IllegalArgumentException("Illegal event name");
                    }
                    this.zzb.zzb().zzf(zzapVarZzb2.zzi());
                    return new zzat(zzapVarZzb2.zzi());
                }
                break;
        }
        return super.zzbU(str, zzgVar, list);
    }
}
