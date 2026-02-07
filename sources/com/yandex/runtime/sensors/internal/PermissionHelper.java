package com.yandex.runtime.sensors.internal;

/* loaded from: classes4.dex */
public class PermissionHelper {
    private static final String TAG = "com.yandex.runtime.sensors.internal.PermissionHelper";

    /* JADX WARN: Code restructure failed: missing block: B:21:0x003d, code lost:
    
        r4 = r4 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean checkPermissions(java.lang.String[] r9) throws android.content.pm.PackageManager.NameNotFoundException {
        /*
            r0 = 1
            if (r9 != 0) goto L4
            return r0
        L4:
            r1 = 0
            android.content.Context r2 = com.yandex.runtime.Runtime.getApplicationContext()     // Catch: java.lang.Exception -> L43
            android.content.pm.PackageManager r3 = r2.getPackageManager()     // Catch: java.lang.Exception -> L43
            java.lang.String r2 = r2.getPackageName()     // Catch: java.lang.Exception -> L43
            r4 = 4096(0x1000, float:5.74E-42)
            android.content.pm.PackageInfo r2 = r3.getPackageInfo(r2, r4)     // Catch: java.lang.Exception -> L43
            java.lang.String[] r3 = r2.requestedPermissions     // Catch: java.lang.Exception -> L43
            if (r3 == 0) goto L47
            int[] r3 = r2.requestedPermissionsFlags     // Catch: java.lang.Exception -> L43
            if (r3 != 0) goto L20
            goto L47
        L20:
            int r3 = r9.length     // Catch: java.lang.Exception -> L43
            r4 = 0
        L22:
            if (r4 >= r3) goto L46
            r5 = r9[r4]     // Catch: java.lang.Exception -> L43
            r6 = 0
        L27:
            java.lang.String[] r7 = r2.requestedPermissions     // Catch: java.lang.Exception -> L43
            int r8 = r7.length     // Catch: java.lang.Exception -> L43
            if (r6 >= r8) goto L45
            int[] r8 = r2.requestedPermissionsFlags     // Catch: java.lang.Exception -> L43
            r8 = r8[r6]     // Catch: java.lang.Exception -> L43
            r8 = r8 & 2
            if (r8 != 0) goto L35
            goto L40
        L35:
            r7 = r7[r6]     // Catch: java.lang.Exception -> L43
            boolean r7 = r7.equals(r5)     // Catch: java.lang.Exception -> L43
            if (r7 == 0) goto L40
            int r4 = r4 + 1
            goto L22
        L40:
            int r6 = r6 + 1
            goto L27
        L43:
            r9 = move-exception
            goto L48
        L45:
            return r1
        L46:
            return r0
        L47:
            return r1
        L48:
            java.lang.String r0 = com.yandex.runtime.sensors.internal.PermissionHelper.TAG
            java.lang.String r2 = "Exception of calling getPackageInfo"
            android.util.Log.e(r0, r2, r9)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yandex.runtime.sensors.internal.PermissionHelper.checkPermissions(java.lang.String[]):boolean");
    }
}
