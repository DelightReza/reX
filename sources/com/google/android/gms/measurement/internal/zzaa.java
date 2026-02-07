package com.google.android.gms.measurement.internal;

import java.util.Map;
import java.util.Set;

/* loaded from: classes4.dex */
final class zzaa extends zzku {
    private String zza;
    private Set zzb;
    private Map zzc;
    private Long zzd;
    private Long zze;

    zzaa(zzlh zzlhVar) {
        super(zzlhVar);
    }

    private final zzu zzd(Integer num) {
        if (this.zzc.containsKey(num)) {
            return (zzu) this.zzc.get(num);
        }
        zzu zzuVar = new zzu(this, this.zza, null);
        this.zzc.put(num, zzuVar);
        return zzuVar;
    }

    private final boolean zzf(int i, int i2) {
        zzu zzuVar = (zzu) this.zzc.get(Integer.valueOf(i));
        if (zzuVar == null) {
            return false;
        }
        return zzuVar.zze.get(i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:396:0x09b9, code lost:
    
        r0 = r39.zzt.zzaA().zzk();
        r2 = com.google.android.gms.measurement.internal.zzet.zzn(r39.zza);
     */
    /* JADX WARN: Code restructure failed: missing block: B:397:0x09cd, code lost:
    
        if (r12.zzj() == false) goto L399;
     */
    /* JADX WARN: Code restructure failed: missing block: B:398:0x09cf, code lost:
    
        r7 = java.lang.Integer.valueOf(r12.zza());
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x09d8, code lost:
    
        r7 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x09d9, code lost:
    
        r0.zzc("Invalid property filter ID. appId, id", r2, java.lang.String.valueOf(r7));
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x03f5  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:239:0x05a6  */
    /* JADX WARN: Removed duplicated region for block: B:276:0x0705 A[PHI: r0 r5 r20 r22
      0x0705: PHI (r0v167 java.util.Map) = (r0v169 java.util.Map), (r0v175 java.util.Map) binds: [B:288:0x072d, B:275:0x0703] A[DONT_GENERATE, DONT_INLINE]
      0x0705: PHI (r5v37 android.database.Cursor) = (r5v38 android.database.Cursor), (r5v39 android.database.Cursor) binds: [B:288:0x072d, B:275:0x0703] A[DONT_GENERATE, DONT_INLINE]
      0x0705: PHI (r20v9 long) = (r20v10 long), (r20v13 long) binds: [B:288:0x072d, B:275:0x0703] A[DONT_GENERATE, DONT_INLINE]
      0x0705: PHI (r22v17 com.google.android.gms.measurement.internal.zzaq) = (r22v18 com.google.android.gms.measurement.internal.zzaq), (r22v20 com.google.android.gms.measurement.internal.zzaq) binds: [B:288:0x072d, B:275:0x0703] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:298:0x074e  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x07d2  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x08a2 A[PHI: r0 r13 r41
      0x08a2: PHI (r0v123 java.util.Map) = (r0v125 java.util.Map), (r0v131 java.util.Map) binds: [B:361:0x08c6, B:351:0x08a0] A[DONT_GENERATE, DONT_INLINE]
      0x08a2: PHI (r13v12 android.database.Cursor) = (r13v13 android.database.Cursor), (r13v14 android.database.Cursor) binds: [B:361:0x08c6, B:351:0x08a0] A[DONT_GENERATE, DONT_INLINE]
      0x08a2: PHI (r41v4 java.util.Iterator) = (r41v5 java.util.Iterator), (r41v9 java.util.Iterator) binds: [B:361:0x08c6, B:351:0x08a0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:371:0x08e3  */
    /* JADX WARN: Removed duplicated region for block: B:408:0x0a0e  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01a4 A[Catch: all -> 0x01b2, SQLiteException -> 0x01b5, TRY_LEAVE, TryCatch #15 {all -> 0x01b2, blocks: (B:59:0x019e, B:61:0x01a4, B:69:0x01be, B:70:0x01c3, B:71:0x01cd, B:72:0x01dd, B:81:0x0209, B:74:0x01ec, B:78:0x01fc, B:80:0x0202, B:98:0x0232), top: B:446:0x019e }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01be A[Catch: all -> 0x01b2, SQLiteException -> 0x01b5, TRY_ENTER, TryCatch #15 {all -> 0x01b2, blocks: (B:59:0x019e, B:61:0x01a4, B:69:0x01be, B:70:0x01c3, B:71:0x01cd, B:72:0x01dd, B:81:0x0209, B:74:0x01ec, B:78:0x01fc, B:80:0x0202, B:98:0x0232), top: B:446:0x019e }] */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v15 */
    /* JADX WARN: Type inference failed for: r11v2, types: [java.lang.Object, java.util.Map] */
    /* JADX WARN: Type inference failed for: r5v49, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v50, types: [android.database.sqlite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r5v8 */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    final java.util.List zza(java.lang.String r40, java.util.List r41, java.util.List r42, java.lang.Long r43, java.lang.Long r44) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 2716
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzaa.zza(java.lang.String, java.util.List, java.util.List, java.lang.Long, java.lang.Long):java.util.List");
    }

    @Override // com.google.android.gms.measurement.internal.zzku
    protected final boolean zzb() {
        return false;
    }
}
