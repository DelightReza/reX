package com.google.android.gms.measurement.internal;

import androidx.collection.ArrayMap;
import com.google.android.gms.internal.measurement.zzoy;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
final class zzu {
    final /* synthetic */ zzaa zza;
    private String zzb;
    private boolean zzc;
    private com.google.android.gms.internal.measurement.zzgi zzd;
    private BitSet zze;
    private BitSet zzf;
    private Map zzg;
    private Map zzh;

    /* synthetic */ zzu(zzaa zzaaVar, String str, zzt zztVar) {
        this.zza = zzaaVar;
        this.zzb = str;
        this.zzc = true;
        this.zze = new BitSet();
        this.zzf = new BitSet();
        this.zzg = new ArrayMap();
        this.zzh = new ArrayMap();
    }

    final com.google.android.gms.internal.measurement.zzfp zza(int i) {
        ArrayList arrayList;
        List list;
        com.google.android.gms.internal.measurement.zzfo zzfoVarZzb = com.google.android.gms.internal.measurement.zzfp.zzb();
        zzfoVarZzb.zza(i);
        zzfoVarZzb.zzc(this.zzc);
        com.google.android.gms.internal.measurement.zzgi zzgiVar = this.zzd;
        if (zzgiVar != null) {
            zzfoVarZzb.zzd(zzgiVar);
        }
        com.google.android.gms.internal.measurement.zzgh zzghVarZze = com.google.android.gms.internal.measurement.zzgi.zze();
        zzghVarZze.zzb(zzlj.zzs(this.zze));
        zzghVarZze.zzd(zzlj.zzs(this.zzf));
        Map map = this.zzg;
        if (map == null) {
            arrayList = null;
        } else {
            ArrayList arrayList2 = new ArrayList(map.size());
            for (Integer num : this.zzg.keySet()) {
                int iIntValue = num.intValue();
                Long l = (Long) this.zzg.get(num);
                if (l != null) {
                    com.google.android.gms.internal.measurement.zzfq zzfqVarZzc = com.google.android.gms.internal.measurement.zzfr.zzc();
                    zzfqVarZzc.zzb(iIntValue);
                    zzfqVarZzc.zza(l.longValue());
                    arrayList2.add((com.google.android.gms.internal.measurement.zzfr) zzfqVarZzc.zzaD());
                }
            }
            arrayList = arrayList2;
        }
        if (arrayList != null) {
            zzghVarZze.zza(arrayList);
        }
        Map map2 = this.zzh;
        if (map2 == null) {
            list = Collections.EMPTY_LIST;
        } else {
            ArrayList arrayList3 = new ArrayList(map2.size());
            for (Integer num2 : this.zzh.keySet()) {
                com.google.android.gms.internal.measurement.zzgj zzgjVarZzd = com.google.android.gms.internal.measurement.zzgk.zzd();
                zzgjVarZzd.zzb(num2.intValue());
                List list2 = (List) this.zzh.get(num2);
                if (list2 != null) {
                    Collections.sort(list2);
                    zzgjVarZzd.zza(list2);
                }
                arrayList3.add((com.google.android.gms.internal.measurement.zzgk) zzgjVarZzd.zzaD());
            }
            list = arrayList3;
        }
        zzghVarZze.zzc(list);
        zzfoVarZzb.zzb(zzghVarZze);
        return (com.google.android.gms.internal.measurement.zzfp) zzfoVarZzb.zzaD();
    }

    final void zzc(zzy zzyVar) {
        int iZza = zzyVar.zza();
        if (zzyVar.zzd != null) {
            this.zzf.set(iZza, true);
        }
        Boolean bool = zzyVar.zze;
        if (bool != null) {
            this.zze.set(iZza, bool.booleanValue());
        }
        if (zzyVar.zzf != null) {
            Map map = this.zzg;
            Integer numValueOf = Integer.valueOf(iZza);
            Long l = (Long) map.get(numValueOf);
            long jLongValue = zzyVar.zzf.longValue() / 1000;
            if (l == null || jLongValue > l.longValue()) {
                this.zzg.put(numValueOf, Long.valueOf(jLongValue));
            }
        }
        if (zzyVar.zzg != null) {
            Map map2 = this.zzh;
            Integer numValueOf2 = Integer.valueOf(iZza);
            List arrayList = (List) map2.get(numValueOf2);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.zzh.put(numValueOf2, arrayList);
            }
            if (zzyVar.zzc()) {
                arrayList.clear();
            }
            zzoy.zzc();
            zzag zzagVarZzf = this.zza.zzt.zzf();
            String str = this.zzb;
            zzef zzefVar = zzeg.zzY;
            if (zzagVarZzf.zzs(str, zzefVar) && zzyVar.zzb()) {
                arrayList.clear();
            }
            zzoy.zzc();
            if (!this.zza.zzt.zzf().zzs(this.zzb, zzefVar)) {
                arrayList.add(Long.valueOf(zzyVar.zzg.longValue() / 1000));
                return;
            }
            Long lValueOf = Long.valueOf(zzyVar.zzg.longValue() / 1000);
            if (arrayList.contains(lValueOf)) {
                return;
            }
            arrayList.add(lValueOf);
        }
    }

    /* synthetic */ zzu(zzaa zzaaVar, String str, com.google.android.gms.internal.measurement.zzgi zzgiVar, BitSet bitSet, BitSet bitSet2, Map map, Map map2, zzt zztVar) {
        this.zza = zzaaVar;
        this.zzb = str;
        this.zze = bitSet;
        this.zzf = bitSet2;
        this.zzg = map;
        this.zzh = new ArrayMap();
        for (Integer num : map2.keySet()) {
            ArrayList arrayList = new ArrayList();
            arrayList.add((Long) map2.get(num));
            this.zzh.put(num, arrayList);
        }
        this.zzc = false;
        this.zzd = zzgiVar;
    }
}
