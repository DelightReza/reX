package com.google.android.gms.common.internal;

import p017j$.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class LibraryVersion {
    private static final GmsLogger zza = new GmsLogger("LibraryVersion", "");
    private static final LibraryVersion zzb = new LibraryVersion();
    private final ConcurrentHashMap zzc = new ConcurrentHashMap();

    protected LibraryVersion() {
    }

    public static LibraryVersion getInstance() {
        return zzb;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.String getVersion(java.lang.String r9) throws java.lang.Throwable {
        /*
            r8 = this;
            java.lang.String r0 = "Failed to get app version for libraryName: "
            java.lang.String r1 = "LibraryVersion"
            java.lang.String r2 = "Please provide a valid libraryName"
            com.google.android.gms.common.internal.Preconditions.checkNotEmpty(r9, r2)
            j$.util.concurrent.ConcurrentHashMap r2 = r8.zzc
            boolean r2 = r2.containsKey(r9)
            if (r2 == 0) goto L1a
            j$.util.concurrent.ConcurrentHashMap r0 = r8.zzc
            java.lang.Object r9 = r0.get(r9)
            java.lang.String r9 = (java.lang.String) r9
            return r9
        L1a:
            java.util.Properties r2 = new java.util.Properties
            r2.<init>()
            r3 = 0
            java.lang.String r4 = "/%s.properties"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: java.io.IOException -> L76 java.lang.Throwable -> L78
            r6 = 0
            r5[r6] = r9     // Catch: java.io.IOException -> L76 java.lang.Throwable -> L78
            java.lang.String r4 = java.lang.String.format(r4, r5)     // Catch: java.io.IOException -> L76 java.lang.Throwable -> L78
            java.lang.Class<com.google.android.gms.common.internal.LibraryVersion> r5 = com.google.android.gms.common.internal.LibraryVersion.class
            java.io.InputStream r4 = r5.getResourceAsStream(r4)     // Catch: java.io.IOException -> L76 java.lang.Throwable -> L78
            if (r4 == 0) goto L5f
            r2.load(r4)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            java.lang.String r5 = "version"
            java.lang.String r3 = r2.getProperty(r5, r3)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            com.google.android.gms.common.internal.GmsLogger r2 = com.google.android.gms.common.internal.LibraryVersion.zza     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r5.<init>()     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r5.append(r9)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            java.lang.String r6 = " version is "
            r5.append(r6)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r5.append(r3)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r2.m307v(r1, r5)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            goto L92
        L58:
            r9 = move-exception
            goto L74
        L5a:
            r2 = move-exception
            r7 = r4
            r4 = r3
            r3 = r7
            goto L7b
        L5f:
            com.google.android.gms.common.internal.GmsLogger r2 = com.google.android.gms.common.internal.LibraryVersion.zza     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r5.<init>()     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r5.append(r0)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r5.append(r9)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            r2.m308w(r1, r5)     // Catch: java.lang.Throwable -> L58 java.io.IOException -> L5a
            goto L92
        L74:
            r3 = r4
            goto La8
        L76:
            r2 = move-exception
            goto L7a
        L78:
            r9 = move-exception
            goto La8
        L7a:
            r4 = r3
        L7b:
            com.google.android.gms.common.internal.GmsLogger r5 = com.google.android.gms.common.internal.LibraryVersion.zza     // Catch: java.lang.Throwable -> L78
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L78
            r6.<init>()     // Catch: java.lang.Throwable -> L78
            r6.append(r0)     // Catch: java.lang.Throwable -> L78
            r6.append(r9)     // Catch: java.lang.Throwable -> L78
            java.lang.String r0 = r6.toString()     // Catch: java.lang.Throwable -> L78
            r5.m306e(r1, r0, r2)     // Catch: java.lang.Throwable -> L78
            r7 = r4
            r4 = r3
            r3 = r7
        L92:
            if (r4 == 0) goto L97
            com.google.android.gms.common.util.IOUtils.closeQuietly(r4)
        L97:
            if (r3 != 0) goto La2
            com.google.android.gms.common.internal.GmsLogger r0 = com.google.android.gms.common.internal.LibraryVersion.zza
            java.lang.String r2 = ".properties file is dropped during release process. Failure to read app version is expected during Google internal testing where locally-built libraries are used"
            r0.m304d(r1, r2)
            java.lang.String r3 = "UNKNOWN"
        La2:
            j$.util.concurrent.ConcurrentHashMap r0 = r8.zzc
            r0.put(r9, r3)
            return r3
        La8:
            if (r3 == 0) goto Lad
            com.google.android.gms.common.util.IOUtils.closeQuietly(r3)
        Lad:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.LibraryVersion.getVersion(java.lang.String):java.lang.String");
    }
}
