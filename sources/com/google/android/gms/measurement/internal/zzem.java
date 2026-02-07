package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Parcel;

/* loaded from: classes4.dex */
public final class zzem extends zzf {
    private final zzel zza;
    private boolean zzb;

    zzem(zzgd zzgdVar) {
        super(zzgdVar);
        Context contextZzaw = this.zzt.zzaw();
        this.zzt.zzf();
        this.zza = new zzel(this, contextZzaw, "google_app_measurement_local.db");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:104:0x00ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x015e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x015e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x015e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0124 A[PHI: r8
      0x0124: PHI (r8v3 android.database.sqlite.SQLiteDatabase) = (r8v2 android.database.sqlite.SQLiteDatabase), (r8v4 android.database.sqlite.SQLiteDatabase) binds: [B:73:0x0122, B:91:0x015b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x016b  */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v18 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r10v6, types: [android.database.Cursor] */
    /* JADX WARN: Type inference failed for: r2v0 */
    /* JADX WARN: Type inference failed for: r2v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v12 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private final boolean zzq(int r18, byte[] r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 385
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzem.zzq(int, byte[]):boolean");
    }

    @Override // com.google.android.gms.measurement.internal.zzf
    protected final boolean zzf() {
        return false;
    }

    final SQLiteDatabase zzh() {
        if (this.zzb) {
            return null;
        }
        SQLiteDatabase writableDatabase = this.zza.getWritableDatabase();
        if (writableDatabase != null) {
            return writableDatabase;
        }
        this.zzb = true;
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x01f6 A[PHI: r9 r11
      0x01f6: PHI (r9v3 int) = (r9v1 int), (r9v1 int), (r9v4 int) binds: [B:110:0x01e4, B:127:0x0213, B:118:0x01f4] A[DONT_GENERATE, DONT_INLINE]
      0x01f6: PHI (r11v7 android.database.sqlite.SQLiteDatabase) = 
      (r11v5 android.database.sqlite.SQLiteDatabase)
      (r11v6 android.database.sqlite.SQLiteDatabase)
      (r11v8 android.database.sqlite.SQLiteDatabase)
     binds: [B:110:0x01e4, B:127:0x0213, B:118:0x01f4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0221  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.util.List zzi(int r23) {
        /*
            Method dump skipped, instructions count: 566
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzem.zzi(int):java.util.List");
    }

    public final void zzj() {
        int iDelete;
        zzg();
        try {
            SQLiteDatabase sQLiteDatabaseZzh = zzh();
            if (sQLiteDatabaseZzh == null || (iDelete = sQLiteDatabaseZzh.delete("messages", null, null)) <= 0) {
                return;
            }
            this.zzt.zzaA().zzj().zzb("Reset local analytics data. records", Integer.valueOf(iDelete));
        } catch (SQLiteException e) {
            this.zzt.zzaA().zzd().zzb("Error resetting local analytics data. error", e);
        }
    }

    public final boolean zzk() {
        return zzq(3, new byte[0]);
    }

    final boolean zzl() {
        Context contextZzaw = this.zzt.zzaw();
        this.zzt.zzf();
        return contextZzaw.getDatabasePath("google_app_measurement_local.db").exists();
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0066 A[PHI: r4
      0x0066: PHI (r4v3 int) = (r4v1 int), (r4v1 int), (r4v4 int) binds: [B:26:0x005d, B:33:0x007a, B:29:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean zzm() {
        /*
            r10 = this;
            java.lang.String r0 = "Error deleting app launch break from local database"
            r10.zzg()
            boolean r1 = r10.zzb
            r2 = 0
            if (r1 == 0) goto Lb
            return r2
        Lb:
            boolean r1 = r10.zzl()
            if (r1 == 0) goto L95
            r1 = 5
            r3 = 0
            r4 = 5
        L14:
            if (r3 >= r1) goto L86
            r5 = 0
            r6 = 1
            android.database.sqlite.SQLiteDatabase r5 = r10.zzh()     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40 android.database.sqlite.SQLiteDatabaseLockedException -> L5e android.database.sqlite.SQLiteFullException -> L6a
            if (r5 != 0) goto L21
            r10.zzb = r6     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40 android.database.sqlite.SQLiteDatabaseLockedException -> L5e android.database.sqlite.SQLiteFullException -> L6a
            return r2
        L21:
            r5.beginTransaction()     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40 android.database.sqlite.SQLiteDatabaseLockedException -> L5e android.database.sqlite.SQLiteFullException -> L6a
            java.lang.String r7 = "messages"
            java.lang.String r8 = "type == ?"
            r9 = 3
            java.lang.String r9 = java.lang.Integer.toString(r9)     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40 android.database.sqlite.SQLiteDatabaseLockedException -> L5e android.database.sqlite.SQLiteFullException -> L6a
            java.lang.String[] r9 = new java.lang.String[]{r9}     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40 android.database.sqlite.SQLiteDatabaseLockedException -> L5e android.database.sqlite.SQLiteFullException -> L6a
            r5.delete(r7, r8, r9)     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40 android.database.sqlite.SQLiteDatabaseLockedException -> L5e android.database.sqlite.SQLiteFullException -> L6a
            r5.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40 android.database.sqlite.SQLiteDatabaseLockedException -> L5e android.database.sqlite.SQLiteFullException -> L6a
            r5.endTransaction()     // Catch: java.lang.Throwable -> L3e android.database.sqlite.SQLiteException -> L40 android.database.sqlite.SQLiteDatabaseLockedException -> L5e android.database.sqlite.SQLiteFullException -> L6a
            r5.close()
            return r6
        L3e:
            r0 = move-exception
            goto L80
        L40:
            r7 = move-exception
            if (r5 == 0) goto L4c
            boolean r8 = r5.inTransaction()     // Catch: java.lang.Throwable -> L3e
            if (r8 == 0) goto L4c
            r5.endTransaction()     // Catch: java.lang.Throwable -> L3e
        L4c:
            com.google.android.gms.measurement.internal.zzgd r8 = r10.zzt     // Catch: java.lang.Throwable -> L3e
            com.google.android.gms.measurement.internal.zzet r8 = r8.zzaA()     // Catch: java.lang.Throwable -> L3e
            com.google.android.gms.measurement.internal.zzer r8 = r8.zzd()     // Catch: java.lang.Throwable -> L3e
            r8.zzb(r0, r7)     // Catch: java.lang.Throwable -> L3e
            r10.zzb = r6     // Catch: java.lang.Throwable -> L3e
            if (r5 == 0) goto L7d
            goto L66
        L5e:
            long r6 = (long) r4     // Catch: java.lang.Throwable -> L3e
            android.os.SystemClock.sleep(r6)     // Catch: java.lang.Throwable -> L3e
            int r4 = r4 + 20
            if (r5 == 0) goto L7d
        L66:
            r5.close()
            goto L7d
        L6a:
            r7 = move-exception
            com.google.android.gms.measurement.internal.zzgd r8 = r10.zzt     // Catch: java.lang.Throwable -> L3e
            com.google.android.gms.measurement.internal.zzet r8 = r8.zzaA()     // Catch: java.lang.Throwable -> L3e
            com.google.android.gms.measurement.internal.zzer r8 = r8.zzd()     // Catch: java.lang.Throwable -> L3e
            r8.zzb(r0, r7)     // Catch: java.lang.Throwable -> L3e
            r10.zzb = r6     // Catch: java.lang.Throwable -> L3e
            if (r5 == 0) goto L7d
            goto L66
        L7d:
            int r3 = r3 + 1
            goto L14
        L80:
            if (r5 == 0) goto L85
            r5.close()
        L85:
            throw r0
        L86:
            com.google.android.gms.measurement.internal.zzgd r0 = r10.zzt
            com.google.android.gms.measurement.internal.zzet r0 = r0.zzaA()
            com.google.android.gms.measurement.internal.zzer r0 = r0.zzk()
            java.lang.String r1 = "Error deleting app launch break from local database in reasonable time"
            r0.zza(r1)
        L95:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzem.zzm():boolean");
    }

    public final boolean zzn(zzac zzacVar) {
        byte[] bArrZzap = this.zzt.zzv().zzap(zzacVar);
        if (bArrZzap.length <= 131072) {
            return zzq(2, bArrZzap);
        }
        this.zzt.zzaA().zzh().zza("Conditional user property too long for local database. Sending directly to service");
        return false;
    }

    public final boolean zzo(zzau zzauVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzav.zza(zzauVar, parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zzq(0, bArrMarshall);
        }
        this.zzt.zzaA().zzh().zza("Event is too long for local database. Sending event directly to service");
        return false;
    }

    public final boolean zzp(zzlk zzlkVar) {
        Parcel parcelObtain = Parcel.obtain();
        zzll.zza(zzlkVar, parcelObtain, 0);
        byte[] bArrMarshall = parcelObtain.marshall();
        parcelObtain.recycle();
        if (bArrMarshall.length <= 131072) {
            return zzq(1, bArrMarshall);
        }
        this.zzt.zzaA().zzh().zza("User property too long for local database. Sending directly to service");
        return false;
    }
}
