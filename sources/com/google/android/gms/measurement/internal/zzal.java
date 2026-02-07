package com.google.android.gms.measurement.internal;

import android.database.sqlite.SQLiteDatabase;
import java.io.File;

/* loaded from: classes4.dex */
public abstract class zzal {
    /* JADX WARN: Removed duplicated region for block: B:24:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007a A[Catch: SQLiteException -> 0x00a4, TryCatch #3 {SQLiteException -> 0x00a4, blocks: (B:26:0x0046, B:28:0x006c, B:30:0x007a, B:32:0x0082, B:33:0x0085, B:34:0x00a3, B:38:0x00a9, B:40:0x00ac, B:42:0x00b4, B:43:0x00bb, B:44:0x00be, B:46:0x00c4, B:49:0x00d5, B:50:0x00d9, B:27:0x0065), top: B:63:0x0046, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a9 A[Catch: SQLiteException -> 0x00a4, LOOP:1: B:38:0x00a9->B:43:0x00bb, LOOP_START, PHI: r0
      0x00a9: PHI (r0v11 int) = (r0v10 int), (r0v12 int) binds: [B:37:0x00a7, B:43:0x00bb] A[DONT_GENERATE, DONT_INLINE], TryCatch #3 {SQLiteException -> 0x00a4, blocks: (B:26:0x0046, B:28:0x006c, B:30:0x007a, B:32:0x0082, B:33:0x0085, B:34:0x00a3, B:38:0x00a9, B:40:0x00ac, B:42:0x00b4, B:43:0x00bb, B:44:0x00be, B:46:0x00c4, B:49:0x00d5, B:50:0x00d9, B:27:0x0065), top: B:63:0x0046, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c4 A[Catch: SQLiteException -> 0x00a4, TryCatch #3 {SQLiteException -> 0x00a4, blocks: (B:26:0x0046, B:28:0x006c, B:30:0x007a, B:32:0x0082, B:33:0x0085, B:34:0x00a3, B:38:0x00a9, B:40:0x00ac, B:42:0x00b4, B:43:0x00bb, B:44:0x00be, B:46:0x00c4, B:49:0x00d5, B:50:0x00d9, B:27:0x0065), top: B:63:0x0046, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:74:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    static void zza(com.google.android.gms.measurement.internal.zzet r10, android.database.sqlite.SQLiteDatabase r11, java.lang.String r12, java.lang.String r13, java.lang.String r14, java.lang.String[] r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 242
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzal.zza(com.google.android.gms.measurement.internal.zzet, android.database.sqlite.SQLiteDatabase, java.lang.String, java.lang.String, java.lang.String, java.lang.String[]):void");
    }

    static void zzb(zzet zzetVar, SQLiteDatabase sQLiteDatabase) {
        if (zzetVar == null) {
            throw new IllegalArgumentException("Monitor must not be null");
        }
        File file = new File(sQLiteDatabase.getPath());
        if (!file.setReadable(false, false)) {
            zzetVar.zzk().zza("Failed to turn off database read permission");
        }
        if (!file.setWritable(false, false)) {
            zzetVar.zzk().zza("Failed to turn off database write permission");
        }
        if (!file.setReadable(true, true)) {
            zzetVar.zzk().zza("Failed to turn on database read permission for owner");
        }
        if (file.setWritable(true, true)) {
            return;
        }
        zzetVar.zzk().zza("Failed to turn on database write permission for owner");
    }
}
