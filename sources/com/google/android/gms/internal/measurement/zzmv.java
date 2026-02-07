package com.google.android.gms.internal.measurement;

import java.util.List;

/* loaded from: classes4.dex */
abstract class zzmv {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final Class zzb;
    private static final zznk zzc;
    private static final zznk zzd;

    static {
        Class<?> cls;
        Class<?> cls2;
        zznk zznkVar = null;
        try {
            cls = Class.forName("com.google.protobuf.GeneratedMessage");
        } catch (Throwable unused) {
            cls = null;
        }
        zzb = cls;
        try {
            cls2 = Class.forName("com.google.protobuf.UnknownFieldSetSchema");
        } catch (Throwable unused2) {
            cls2 = null;
        }
        if (cls2 != null) {
            try {
                zznkVar = (zznk) cls2.getConstructor(null).newInstance(null);
            } catch (Throwable unused3) {
            }
        }
        zzc = zznkVar;
        zzd = new zznm();
    }

    static Object zzA(Object obj, int i, int i2, Object obj2, zznk zznkVar) {
        if (obj2 == null) {
            obj2 = zznkVar.zzc(obj);
        }
        zznkVar.zzf(obj2, i, i2);
        return obj2;
    }

    static void zzB(zznk zznkVar, Object obj, Object obj2) {
        zznkVar.zzh(obj, zznkVar.zze(zznkVar.zzd(obj), zznkVar.zzd(obj2)));
    }

    public static void zzC(Class cls) {
        Class cls2;
        if (!zzlb.class.isAssignableFrom(cls) && (cls2 = zzb) != null && !cls2.isAssignableFrom(cls)) {
            throw new IllegalArgumentException("Message classes must extend GeneratedMessage or GeneratedMessageLite");
        }
    }

    public static void zzD(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzc(i, list, z);
    }

    public static void zzE(int i, List list, zzoc zzocVar) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zze(i, list);
    }

    public static void zzF(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzg(i, list, z);
    }

    public static void zzG(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzj(i, list, z);
    }

    public static void zzH(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzl(i, list, z);
    }

    public static void zzI(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzn(i, list, z);
    }

    public static void zzJ(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzp(i, list, z);
    }

    public static void zzK(int i, List list, zzoc zzocVar, zzmt zzmtVar) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ((zzkj) zzocVar).zzq(i, list.get(i2), zzmtVar);
        }
    }

    public static void zzL(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzs(i, list, z);
    }

    public static void zzM(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzu(i, list, z);
    }

    public static void zzN(int i, List list, zzoc zzocVar, zzmt zzmtVar) throws zzkg {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i2 = 0; i2 < list.size(); i2++) {
            ((zzkj) zzocVar).zzv(i, list.get(i2), zzmtVar);
        }
    }

    public static void zzO(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzx(i, list, z);
    }

    public static void zzP(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzz(i, list, z);
    }

    public static void zzQ(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzB(i, list, z);
    }

    public static void zzR(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzD(i, list, z);
    }

    public static void zzS(int i, List list, zzoc zzocVar) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzG(i, list);
    }

    public static void zzT(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzI(i, list, z);
    }

    public static void zzU(int i, List list, zzoc zzocVar, boolean z) {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzocVar.zzK(i, list, z);
    }

    static boolean zzV(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    static int zza(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzki.zzx(i << 3) + 1);
    }

    static int zzb(int i, List list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzx = size * zzki.zzx(i << 3);
        for (int i2 = 0; i2 < list.size(); i2++) {
            int iZzd = ((zzka) list.get(i2)).zzd();
            iZzx += zzki.zzx(iZzd) + iZzd;
        }
        return iZzx;
    }

    static int zzc(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzd(list) + (size * zzki.zzx(i << 3));
    }

    static int zzd(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzlc)) {
            int iZzu = 0;
            while (i < size) {
                iZzu += zzki.zzu(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzu;
        }
        zzlc zzlcVar = (zzlc) list;
        int iZzu2 = 0;
        while (i < size) {
            iZzu2 += zzki.zzu(zzlcVar.zze(i));
            i++;
        }
        return iZzu2;
    }

    static int zze(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzki.zzx(i << 3) + 4);
    }

    static int zzf(List list) {
        return list.size() * 4;
    }

    static int zzg(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzki.zzx(i << 3) + 8);
    }

    static int zzh(List list) {
        return list.size() * 8;
    }

    static int zzi(int i, List list, zzmt zzmtVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzt = 0;
        for (int i2 = 0; i2 < size; i2++) {
            iZzt += zzki.zzt(i, (zzmi) list.get(i2), zzmtVar);
        }
        return iZzt;
    }

    static int zzj(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzk(list) + (size * zzki.zzx(i << 3));
    }

    static int zzk(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzlc)) {
            int iZzu = 0;
            while (i < size) {
                iZzu += zzki.zzu(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzu;
        }
        zzlc zzlcVar = (zzlc) list;
        int iZzu2 = 0;
        while (i < size) {
            iZzu2 += zzki.zzu(zzlcVar.zze(i));
            i++;
        }
        return iZzu2;
    }

    static int zzl(int i, List list, boolean z) {
        if (list.size() == 0) {
            return 0;
        }
        return zzm(list) + (list.size() * zzki.zzx(i << 3));
    }

    static int zzm(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzlx)) {
            int iZzy = 0;
            while (i < size) {
                iZzy += zzki.zzy(((Long) list.get(i)).longValue());
                i++;
            }
            return iZzy;
        }
        zzlx zzlxVar = (zzlx) list;
        int iZzy2 = 0;
        while (i < size) {
            iZzy2 += zzki.zzy(zzlxVar.zza(i));
            i++;
        }
        return iZzy2;
    }

    static int zzo(int i, List list, zzmt zzmtVar) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iZzx = zzki.zzx(i << 3) * size;
        for (int i2 = 0; i2 < size; i2++) {
            iZzx += zzki.zzv((zzmi) list.get(i2), zzmtVar);
        }
        return iZzx;
    }

    static int zzp(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzq(list) + (size * zzki.zzx(i << 3));
    }

    static int zzq(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzlc)) {
            int iZzx = 0;
            while (i < size) {
                int iIntValue = ((Integer) list.get(i)).intValue();
                iZzx += zzki.zzx((iIntValue >> 31) ^ (iIntValue + iIntValue));
                i++;
            }
            return iZzx;
        }
        zzlc zzlcVar = (zzlc) list;
        int iZzx2 = 0;
        while (i < size) {
            int iZze = zzlcVar.zze(i);
            iZzx2 += zzki.zzx((iZze >> 31) ^ (iZze + iZze));
            i++;
        }
        return iZzx2;
    }

    static int zzr(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzs(list) + (size * zzki.zzx(i << 3));
    }

    static int zzs(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzlx)) {
            int iZzy = 0;
            while (i < size) {
                long jLongValue = ((Long) list.get(i)).longValue();
                iZzy += zzki.zzy((jLongValue >> 63) ^ (jLongValue + jLongValue));
                i++;
            }
            return iZzy;
        }
        zzlx zzlxVar = (zzlx) list;
        int iZzy2 = 0;
        while (i < size) {
            long jZza = zzlxVar.zza(i);
            iZzy2 += zzki.zzy((jZza >> 63) ^ (jZza + jZza));
            i++;
        }
        return iZzy2;
    }

    static int zzt(int i, List list) {
        int size = list.size();
        int i2 = 0;
        if (size == 0) {
            return 0;
        }
        boolean z = list instanceof zzlq;
        int iZzx = zzki.zzx(i << 3) * size;
        if (!z) {
            while (i2 < size) {
                Object obj = list.get(i2);
                if (obj instanceof zzka) {
                    int iZzd = ((zzka) obj).zzd();
                    iZzx += zzki.zzx(iZzd) + iZzd;
                } else {
                    iZzx += zzki.zzw((String) obj);
                }
                i2++;
            }
            return iZzx;
        }
        zzlq zzlqVar = (zzlq) list;
        while (i2 < size) {
            Object objZzf = zzlqVar.zzf(i2);
            if (objZzf instanceof zzka) {
                int iZzd2 = ((zzka) objZzf).zzd();
                iZzx += zzki.zzx(iZzd2) + iZzd2;
            } else {
                iZzx += zzki.zzw((String) objZzf);
            }
            i2++;
        }
        return iZzx;
    }

    static int zzu(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzv(list) + (size * zzki.zzx(i << 3));
    }

    static int zzv(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzlc)) {
            int iZzx = 0;
            while (i < size) {
                iZzx += zzki.zzx(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzx;
        }
        zzlc zzlcVar = (zzlc) list;
        int iZzx2 = 0;
        while (i < size) {
            iZzx2 += zzki.zzx(zzlcVar.zze(i));
            i++;
        }
        return iZzx2;
    }

    static int zzw(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return zzx(list) + (size * zzki.zzx(i << 3));
    }

    static int zzx(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzlx)) {
            int iZzy = 0;
            while (i < size) {
                iZzy += zzki.zzy(((Long) list.get(i)).longValue());
                i++;
            }
            return iZzy;
        }
        zzlx zzlxVar = (zzlx) list;
        int iZzy2 = 0;
        while (i < size) {
            iZzy2 += zzki.zzy(zzlxVar.zza(i));
            i++;
        }
        return iZzy2;
    }

    public static zznk zzy() {
        return zzc;
    }

    public static zznk zzz() {
        return zzd;
    }

    static int zzn(int i, Object obj, zzmt zzmtVar) {
        return zzki.zzx(i << 3) + zzki.zzv((zzmi) obj, zzmtVar);
    }
}
