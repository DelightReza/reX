package androidx.mediarouter.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.media.MediaRouter2;
import android.os.Build;
import java.util.Iterator;
import org.mvel2.asm.Opcodes;

/* loaded from: classes3.dex */
public abstract class SystemOutputSwitcherDialogController {
    /* JADX WARN: Removed duplicated region for block: B:15:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean showDialog(android.content.Context r4) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 34
            r2 = 1
            r3 = 0
            if (r0 < r1) goto Ld
            boolean r0 = showDialogForAndroidUAndAbove(r4)
            goto L29
        Ld:
            r1 = 31
            if (r0 < r1) goto L1f
            boolean r0 = showDialogForAndroidSAndT(r4)
            if (r0 != 0) goto L1d
            boolean r0 = showDialogForAndroidR(r4)
            if (r0 == 0) goto L28
        L1d:
            r0 = 1
            goto L29
        L1f:
            r1 = 30
            if (r0 != r1) goto L28
            boolean r0 = showDialogForAndroidR(r4)
            goto L29
        L28:
            r0 = 0
        L29:
            if (r0 == 0) goto L2c
            return r2
        L2c:
            boolean r0 = isRunningOnWear(r4)
            if (r0 == 0) goto L39
            boolean r4 = showBluetoothSettingsFragment(r4)
            if (r4 == 0) goto L39
            return r2
        L39:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.SystemOutputSwitcherDialogController.showDialog(android.content.Context):boolean");
    }

    private static boolean showDialogForAndroidUAndAbove(Context context) {
        int i = Build.VERSION.SDK_INT;
        if (i < 30) {
            return false;
        }
        MediaRouter2 api30Impl = Api30Impl.getInstance(context);
        if (i >= 34) {
            return Api34Impl.showSystemOutputSwitcher(api30Impl);
        }
        return false;
    }

    private static boolean showDialogForAndroidSAndT(Context context) {
        ApplicationInfo applicationInfo;
        Intent intentPutExtra = new Intent().setAction("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_DIALOG").setPackage("com.android.systemui").putExtra("package_name", context.getPackageName());
        Iterator<ResolveInfo> it = context.getPackageManager().queryBroadcastReceivers(intentPutExtra, 0).iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            if (activityInfo != null && (applicationInfo = activityInfo.applicationInfo) != null && (applicationInfo.flags & Opcodes.LOR) != 0) {
                context.sendBroadcast(intentPutExtra);
                return true;
            }
        }
        return false;
    }

    private static boolean showDialogForAndroidR(Context context) {
        ApplicationInfo applicationInfo;
        Intent intentPutExtra = new Intent().addFlags(268435456).setAction("com.android.settings.panel.action.MEDIA_OUTPUT").putExtra("com.android.settings.panel.extra.PACKAGE_NAME", context.getPackageName());
        Iterator<ResolveInfo> it = context.getPackageManager().queryIntentActivities(intentPutExtra, 0).iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            if (activityInfo != null && (applicationInfo = activityInfo.applicationInfo) != null && (applicationInfo.flags & Opcodes.LOR) != 0) {
                context.startActivity(intentPutExtra);
                return true;
            }
        }
        return false;
    }

    private static boolean showBluetoothSettingsFragment(Context context) {
        ApplicationInfo applicationInfo;
        Intent intentPutExtra = new Intent("android.settings.BLUETOOTH_SETTINGS").addFlags(268468224).putExtra("EXTRA_CONNECTION_ONLY", true).putExtra("android.bluetooth.devicepicker.extra.FILTER_TYPE", 1);
        Iterator<ResolveInfo> it = context.getPackageManager().queryIntentActivities(intentPutExtra, 0).iterator();
        while (it.hasNext()) {
            ActivityInfo activityInfo = it.next().activityInfo;
            if (activityInfo != null && (applicationInfo = activityInfo.applicationInfo) != null && (applicationInfo.flags & Opcodes.LOR) != 0) {
                context.startActivity(intentPutExtra);
                return true;
            }
        }
        return false;
    }

    private static boolean isRunningOnWear(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.type.watch");
    }

    static class Api30Impl {
        static MediaRouter2 getInstance(Context context) {
            return MediaRouter2.getInstance(context);
        }
    }

    static class Api34Impl {
        static boolean showSystemOutputSwitcher(MediaRouter2 mediaRouter2) {
            return mediaRouter2.showSystemOutputSwitcher();
        }
    }
}
