package com.radolyn.ayugram.utils.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import com.exteragram.messenger.utils.system.SystemUtils;

/* loaded from: classes4.dex */
public abstract class StorageUtils {
    public static boolean ensureHasPermissions(Activity activity) {
        int i = Build.VERSION.SDK_INT;
        if (i >= 23 && !SystemUtils.isStoragePermissionGranted()) {
            SystemUtils.requestStoragePermission(activity);
            return false;
        }
        if (i < 30 || Environment.isExternalStorageManager()) {
            return true;
        }
        activity.startActivity(new Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION"));
        return false;
    }

    public static boolean arePermissionsGranted() {
        int i = Build.VERSION.SDK_INT;
        if (i < 23 || SystemUtils.isStoragePermissionGranted()) {
            return i < 30 || Environment.isExternalStorageManager();
        }
        return false;
    }
}
