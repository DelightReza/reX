package com.exteragram.messenger.updater;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInstaller;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.arch.core.util.Function;
import androidx.core.app.NotificationChannelCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import com.exteragram.messenger.utils.AppUtils;
import com.exteragram.messenger.utils.network.RemoteUtils;
import com.radolyn.ayugram.AyuInfra;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.XiaomiUtilities;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AlertsCreator;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.StickerImageView;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes.dex */
public abstract class UpdaterUtils {
    private static AlertDialog dialog;

    public static void getAppUpdate(final Utilities.Callback2 callback2) {
        RemoteUtils.getMessages(new Utilities.Callback2() { // from class: com.exteragram.messenger.updater.UpdaterUtils$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) throws NumberFormatException {
                UpdaterUtils.m2085$r8$lambda$IowTZwq1pPTn6AMIS595dkH1as(callback2, (TLRPC.messages_Messages) obj, (TLRPC.TL_error) obj2);
            }
        });
    }

    /* renamed from: $r8$lambda$IowTZwq1pPTn6AMIS59-5dkH1as, reason: not valid java name */
    public static /* synthetic */ void m2085$r8$lambda$IowTZwq1pPTn6AMIS595dkH1as(final Utilities.Callback2 callback2, TLRPC.messages_Messages messages_messages, TLRPC.TL_error tL_error) throws NumberFormatException {
        if (tL_error != null || messages_messages == null) {
            callback2.run(null, tL_error);
        } else {
            parseUpdateResponse(messages_messages, new Utilities.Callback() { // from class: com.exteragram.messenger.updater.UpdaterUtils$$ExternalSyntheticLambda3
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    UpdaterUtils.$r8$lambda$ghzDpb6CudUboMzPfRBCUQzDaBY(callback2, (TLRPC.TL_help_appUpdate) obj);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$ghzDpb6CudUboMzPfRBCUQzDaBY(Utilities.Callback2 callback2, TLRPC.TL_help_appUpdate tL_help_appUpdate) {
        if (tL_help_appUpdate.f1636id > 0 && !AyuInfra.isModified()) {
            Log.d("AyuGram", "Update found and app is not modified");
            callback2.run(tL_help_appUpdate, null);
        } else {
            TLRPC.TL_error tL_error = new TLRPC.TL_error();
            tL_error.text = "NO_UPDATE_METADATA";
            callback2.run(tL_help_appUpdate, tL_error);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void parseUpdateResponse(org.telegram.tgnet.TLRPC.messages_Messages r13, final org.telegram.messenger.Utilities.Callback r14) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 330
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.updater.UpdaterUtils.parseUpdateResponse(org.telegram.tgnet.TLRPC$messages_Messages, org.telegram.messenger.Utilities$Callback):void");
    }

    /* renamed from: $r8$lambda$FJGawJwRbcTI-KWZBZLd1XMg7BA, reason: not valid java name */
    public static /* synthetic */ void m2084$r8$lambda$FJGawJwRbcTIKWZBZLd1XMg7BA(Utilities.Callback callback, TLRPC.TL_help_appUpdate tL_help_appUpdate, int i, int i2, int i3, TLRPC.messages_Messages messages_messages, TLRPC.TL_error tL_error) {
        if (messages_messages == null || tL_error != null) {
            callback.run(tL_help_appUpdate);
            return;
        }
        ArrayList arrayList = messages_messages.messages;
        int size = arrayList.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            TLRPC.Message message = (TLRPC.Message) obj;
            if (message instanceof TLRPC.TL_message) {
                if (message.f1597id == i) {
                    tL_help_appUpdate.entities = message.entities;
                    tL_help_appUpdate.text = message.message;
                } else {
                    TLRPC.MessageMedia messageMedia = message.media;
                    if (messageMedia != null) {
                        TLRPC.Document document = messageMedia.getDocument();
                        if (message.f1597id == i2 && !document.attributes.isEmpty() && document.attributes.get(0).file_name.endsWith(".apk")) {
                            tL_help_appUpdate.document = document;
                            tL_help_appUpdate.flags |= 2;
                        } else if (message.f1597id == i3 && MessageObject.isStickerDocument(document)) {
                            tL_help_appUpdate.sticker = document;
                            tL_help_appUpdate.flags |= 8;
                        }
                    }
                }
            }
        }
        callback.run(tL_help_appUpdate);
    }

    public static void installUpdate(final Activity activity, TLRPC.Document document) {
        if (activity == null || document == null) {
            return;
        }
        if (XiaomiUtilities.isMIUI()) {
            AndroidUtilities.openForView(document, activity);
            return;
        }
        final File pathToAttach = FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(document, true);
        if (pathToAttach == null) {
            return;
        }
        AlertDialog alertDialog = dialog;
        if (alertDialog == null || !alertDialog.isShowing()) {
            showInstallDialog(activity);
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: com.exteragram.messenger.updater.UpdaterUtils$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws InterruptedException, IOException {
                    UpdaterUtils.$r8$lambda$yqFMWMGrZzPs9teqMLorV3QSXjY(activity, pathToAttach);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$yqFMWMGrZzPs9teqMLorV3QSXjY(Activity activity, File file) throws InterruptedException, IOException {
        InstallReceiver installReceiverRegister = register(activity, new Runnable() { // from class: com.exteragram.messenger.updater.UpdaterUtils$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                UpdaterUtils.$r8$lambda$Yyx_ymCvZCiUCedMnTmQ4uD9eD0();
            }
        });
        installApk(activity, file);
        Intent intentWaitIntent = installReceiverRegister.waitIntent();
        if (intentWaitIntent != null) {
            activity.startActivity(intentWaitIntent);
        }
    }

    public static /* synthetic */ void $r8$lambda$Yyx_ymCvZCiUCedMnTmQ4uD9eD0() {
        AlertDialog alertDialog = dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            dialog = null;
        }
    }

    private static void showInstallDialog(Activity activity) {
        String string;
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(LayoutHelper.createFrame(-1, -2.0f, 51, 4.0f, 4.0f, 4.0f, 4.0f));
        StickerImageView stickerImageView = new StickerImageView(activity, UserConfig.selectedAccount);
        stickerImageView.setStickerPackName("UtyaDuckFull");
        stickerImageView.setStickerNum(0);
        stickerImageView.getImageReceiver().setAutoRepeat(1);
        linearLayout.addView(stickerImageView, LayoutHelper.createLinear(Opcodes.IF_ICMPNE, Opcodes.IF_ICMPNE, 49, 17, 24, 17, 0));
        TextView textView = new TextView(activity);
        textView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        textView.setTextSize(1, 16.0f);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        textView.setSingleLine(true);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setText(LocaleController.getString(C2369R.string.UpdateInstalling));
        linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, 49, 17, 20, 17, 0));
        TextView textView2 = new TextView(activity);
        textView2.setGravity(17);
        textView2.setTextSize(1, 13.0f);
        textView2.setTextColor(Theme.getColor(Theme.key_dialogTextGray));
        if (Build.VERSION.SDK_INT < 29 || Settings.canDrawOverlays(activity)) {
            string = LocaleController.getString(C2369R.string.UpdateInstallingRelaunch);
        } else {
            string = LocaleController.getString(C2369R.string.UpdateInstallingNotification);
        }
        textView2.setText(string);
        linearLayout.addView(textView2, LayoutHelper.createLinear(-2, -2, 49, 17, 4, 17, 24));
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(linearLayout);
        AlertDialog alertDialogCreate = builder.create();
        dialog = alertDialogCreate;
        alertDialogCreate.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    private static void installApk(Activity activity, File file) throws IOException {
        PendingIntent broadcast = PendingIntent.getBroadcast(activity, 0, new Intent(UpdaterUtils.class.getName()).setPackage(activity.getPackageName()), 167772160);
        PackageInstaller packageInstaller = activity.getPackageManager().getPackageInstaller();
        PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
        if (Build.VERSION.SDK_INT >= 31) {
            sessionParams.setRequireUserAction(2);
        }
        try {
            PackageInstaller.Session sessionOpenSession = packageInstaller.openSession(packageInstaller.createSession(sessionParams));
            try {
                OutputStream outputStreamOpenWrite = sessionOpenSession.openWrite(file.getName(), 0L, file.length());
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    try {
                        transfer(fileInputStream, outputStreamOpenWrite);
                        fileInputStream.close();
                        if (outputStreamOpenWrite != null) {
                            outputStreamOpenWrite.close();
                        }
                        sessionOpenSession.commit(broadcast.getIntentSender());
                        sessionOpenSession.close();
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            FileLog.m1160e(e);
            handleInstallError(activity, file, e);
        }
    }

    private static void handleInstallError(Activity activity, File file, IOException iOException) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.updater.UpdaterUtils$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                UpdaterUtils.$r8$lambda$7ZDy7OZWWRRScjHttmHHPHeOFco();
            }
        });
        AlertsCreator.createSimpleAlert(activity, LocaleController.getString(C2369R.string.ErrorOccurred) + "\n" + iOException.getLocalizedMessage()).show();
        AndroidUtilities.openForView(file, "install.apk", "application/vnd.android.package-archive", activity, null, false);
    }

    public static /* synthetic */ void $r8$lambda$7ZDy7OZWWRRScjHttmHHPHeOFco() {
        AlertDialog alertDialog = dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            dialog = null;
        }
    }

    private static void transfer(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        while (true) {
            int i = inputStream.read(bArr, 0, 8192);
            if (i < 0) {
                return;
            } else {
                outputStream.write(bArr, 0, i);
            }
        }
    }

    private static InstallReceiver register(Context context, Runnable runnable) {
        InstallReceiver installReceiver = new InstallReceiver(context, ApplicationLoader.getApplicationId(), runnable);
        ContextCompat.registerReceiver(context, installReceiver, new IntentFilter(UpdaterUtils.class.getName()), 4);
        return installReceiver;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    static class InstallReceiver extends BroadcastReceiver {
        private final Context context;
        private Intent intent;
        private final CountDownLatch latch;
        private final Runnable onSuccess;
        private final String packageName;

        private InstallReceiver(Context context, String str, Runnable runnable) {
            this.latch = new CountDownLatch(1);
            this.intent = null;
            this.context = context;
            this.packageName = str;
            this.onSuccess = runnable;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.PACKAGE_ADDED".equals(intent.getAction())) {
                Uri data = intent.getData();
                if (data == null || this.onSuccess == null || !data.getSchemeSpecificPart().equals(this.packageName)) {
                    return;
                }
                this.onSuccess.run();
                this.context.unregisterReceiver(this);
                return;
            }
            handlePackageInstallerResult(intent);
        }

        private void handlePackageInstallerResult(Intent intent) {
            int intExtra = intent.getIntExtra("android.content.pm.extra.STATUS", 4);
            if (intExtra == -1) {
                this.intent = (Intent) intent.getParcelableExtra("android.intent.extra.INTENT");
            } else {
                if (intExtra == 1 || intExtra == 2 || intExtra == 4 || intExtra == 5 || intExtra == 6 || intExtra == 7) {
                    handleFailure(intent);
                }
                Runnable runnable = this.onSuccess;
                if (runnable != null) {
                    runnable.run();
                }
                this.context.unregisterReceiver(this);
            }
            this.latch.countDown();
        }

        private void handleFailure(final Intent intent) {
            PackageInstaller packageInstaller;
            PackageInstaller.SessionInfo sessionInfo;
            int intExtra = intent.getIntExtra("android.content.pm.extra.SESSION_ID", 0);
            if (intExtra > 0 && (sessionInfo = (packageInstaller = this.context.getPackageManager().getPackageInstaller()).getSessionInfo(intExtra)) != null) {
                packageInstaller.abandonSession(sessionInfo.getSessionId());
            }
            Context context = this.context;
            if (context instanceof LaunchActivity) {
                ((LaunchActivity) context).showBulletin(new Function() { // from class: com.exteragram.messenger.updater.UpdaterUtils$InstallReceiver$$ExternalSyntheticLambda0
                    @Override // androidx.arch.core.util.Function
                    public final Object apply(Object obj) {
                        return ((BulletinFactory) obj).createErrorBulletin(LocaleController.formatString(C2369R.string.UpdateFailedToInstall, Integer.valueOf(intent.getIntExtra("android.content.pm.extra.STATUS", 1))));
                    }
                });
            }
        }

        public Intent waitIntent() throws InterruptedException {
            try {
                this.latch.await(5L, TimeUnit.SECONDS);
            } catch (Exception unused) {
            }
            return this.intent;
        }
    }

    /* loaded from: classes3.dex */
    public static class UpdateReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.MY_PACKAGE_REPLACED".equals(intent.getAction())) {
                String packageName = context.getPackageName();
                if (packageName.equals(context.getPackageManager().getInstallerPackageName(packageName))) {
                    launchApp(context);
                }
            }
        }

        private void launchApp(Context context) {
            Intent flags = new Intent(context, (Class<?>) LaunchActivity.class).setFlags(268435456);
            if (Build.VERSION.SDK_INT < 29 || Settings.canDrawOverlays(context)) {
                context.startActivity(flags);
            } else {
                showNotification(context, flags);
            }
        }

        private void showNotification(Context context, Intent intent) {
            NotificationChannelCompat notificationChannelCompatBuild = new NotificationChannelCompat.Builder("updated", 4).setName(LocaleController.getString(C2369R.string.UpdateApp)).setLightsEnabled(false).setVibrationEnabled(false).setSound(null, null).build();
            NotificationManagerCompat notificationManagerCompatFrom = NotificationManagerCompat.from(context);
            notificationManagerCompatFrom.createNotificationChannel(notificationChannelCompatBuild);
            notificationManagerCompatFrom.notify(8732833, new NotificationCompat.Builder(context, "updated").setSmallIcon(C2369R.drawable.notification).setColor(AppUtils.getNotificationColor()).setShowWhen(false).setContentText(LocaleController.getString(C2369R.string.UpdateInstalledNotification)).setCategory("status").setContentIntent(PendingIntent.getActivity(context, 0, intent, 201326592)).build());
        }
    }
}
