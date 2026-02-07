package org.telegram.messenger;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.radolyn.ayugram.AyuConstants;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.mvel2.asm.Opcodes;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.StickerImageView;
import p017j$.util.function.Consumer$CC;
import p017j$.util.stream.Stream;

/* loaded from: classes.dex */
public class FilesMigrationService extends Service {
    public static FilesMigrationBottomSheet filesMigrationBottomSheet = null;
    public static boolean hasOldFolder = false;
    public static boolean isRunning = false;
    private static boolean wasShown = false;
    long lastUpdateTime;
    private int movedFilesCount;
    private int totalFilesCount;

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void start() {
        ApplicationLoader.applicationContext.startService(new Intent(ApplicationLoader.applicationContext, (Class<?>) FilesMigrationService.class));
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        NotificationsController.checkOtherNotificationsChannel();
        FilesMigrationService$$ExternalSyntheticApiModelOutline2.m1164m();
        Notification notificationBuild = FilesMigrationService$$ExternalSyntheticApiModelOutline1.m1163m(this, NotificationsController.OTHER_NOTIFICATIONS_CHANNEL).setContentTitle(getText(C2369R.string.MigratingFiles)).setAutoCancel(false).setSmallIcon(C2369R.drawable.notification).build();
        isRunning = true;
        new C23111().start();
        startForeground(301, notificationBuild);
        return super.onStartCommand(intent, i, i2);
    }

    /* renamed from: org.telegram.messenger.FilesMigrationService$1 */
    /* loaded from: classes4.dex */
    class C23111 extends Thread {
        C23111() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            FilesMigrationService.this.migrateOldFolder();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.FilesMigrationService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$run$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0() {
            FilesMigrationService.isRunning = false;
            FilesMigrationService.this.stopForeground(true);
            FilesMigrationService.this.stopSelf();
        }
    }

    public void migrateOldFolder() {
        ArrayList<File> rootDirs;
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (!TextUtils.isEmpty(SharedConfig.storageCacheDir) && (rootDirs = AndroidUtilities.getRootDirs()) != null) {
            int size = rootDirs.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    break;
                }
                File file = rootDirs.get(i);
                if (file.getAbsolutePath().startsWith(SharedConfig.storageCacheDir)) {
                    externalStorageDirectory = file;
                    break;
                }
                i++;
            }
        }
        File file2 = new File(ApplicationLoader.applicationContext.getExternalFilesDir(null), AyuConstants.APP_NAME);
        File file3 = new File(externalStorageDirectory, AyuConstants.APP_NAME);
        this.totalFilesCount = getFilesCount(file3);
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (file3.canRead() && file3.canWrite()) {
            moveDirectory(file3, file2);
        }
        FileLog.m1157d("move time = " + (System.currentTimeMillis() - jCurrentTimeMillis));
        ApplicationLoader.applicationContext.getSharedPreferences("systemConfig", 0).edit().putBoolean("migration_to_scoped_storage_finished", true).apply();
    }

    private int getFilesCount(File file) {
        File[] fileArrListFiles;
        if (!file.exists() || (fileArrListFiles = file.listFiles()) == null) {
            return 0;
        }
        int filesCount = 0;
        for (int i = 0; i < fileArrListFiles.length; i++) {
            filesCount = fileArrListFiles[i].isDirectory() ? filesCount + getFilesCount(fileArrListFiles[i]) : filesCount + 1;
        }
        return filesCount;
    }

    private void moveDirectory(File file, final File file2) {
        if (file.exists()) {
            if (file2.exists() || file2.mkdir()) {
                try {
                    Stream streamConvert = Stream.VivifiedWrapper.convert(Files.list(file.toPath()));
                    try {
                        streamConvert.forEach(new Consumer() { // from class: org.telegram.messenger.FilesMigrationService$$ExternalSyntheticLambda3
                            @Override // java.util.function.Consumer
                            /* renamed from: accept */
                            public final void m971v(Object obj) throws IOException {
                                this.f$0.lambda$moveDirectory$0(file2, (Path) obj);
                            }

                            public /* synthetic */ Consumer andThen(Consumer consumer) {
                                return Consumer$CC.$default$andThen(this, consumer);
                            }
                        });
                        streamConvert.close();
                    } finally {
                    }
                } catch (Exception e) {
                    FileLog.m1160e(e);
                }
                try {
                    file.delete();
                } catch (Exception e2) {
                    FileLog.m1160e(e2);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$moveDirectory$0(File file, Path path) throws IOException {
        File file2 = new File(file, path.getFileName().toString());
        if (Files.isDirectory(path, new LinkOption[0])) {
            moveDirectory(path.toFile(), file2);
            return;
        }
        try {
            Files.move(path, file2.toPath(), new CopyOption[0]);
        } catch (Exception e) {
            FileLog.m1160e(e);
            try {
                path.toFile().delete();
            } catch (Exception e2) {
                FileLog.m1160e(e2);
            }
        }
        this.movedFilesCount++;
        updateProgress();
    }

    private void updateProgress() {
        if (System.currentTimeMillis() - this.lastUpdateTime > 20 || this.movedFilesCount >= this.totalFilesCount - 1) {
            final int i = this.movedFilesCount;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.FilesMigrationService$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateProgress$1(i);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateProgress$1(int i) {
        FilesMigrationService$$ExternalSyntheticApiModelOutline2.m1164m();
        ((NotificationManager) getSystemService("notification")).notify(301, FilesMigrationService$$ExternalSyntheticApiModelOutline1.m1163m(this, NotificationsController.OTHER_NOTIFICATIONS_CHANNEL).setContentTitle(getText(C2369R.string.MigratingFiles)).setContentText(String.format("%s/%s", Integer.valueOf(i), Integer.valueOf(this.totalFilesCount))).setSmallIcon(C2369R.drawable.notification).setAutoCancel(false).setProgress(this.totalFilesCount, i, false).build());
    }

    public static void checkBottomSheet(BaseFragment baseFragment) {
        ArrayList<File> rootDirs;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("systemConfig", 0);
        if (!Environment.isExternalStorageLegacy() || sharedPreferences.getBoolean("migration_to_scoped_storage_finished", false) || sharedPreferences.getInt("migration_to_scoped_storage_count", 0) >= 5 || wasShown || filesMigrationBottomSheet != null || isRunning) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 30) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            if (!TextUtils.isEmpty(SharedConfig.storageCacheDir) && (rootDirs = AndroidUtilities.getRootDirs()) != null) {
                int size = rootDirs.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        break;
                    }
                    File file = rootDirs.get(i);
                    if (file.getAbsolutePath().startsWith(SharedConfig.storageCacheDir)) {
                        externalStorageDirectory = file;
                        break;
                    }
                    i++;
                }
            }
            hasOldFolder = new File(externalStorageDirectory, AyuConstants.APP_NAME).exists();
        }
        if (hasOldFolder) {
            FilesMigrationBottomSheet filesMigrationBottomSheet2 = new FilesMigrationBottomSheet(baseFragment);
            filesMigrationBottomSheet = filesMigrationBottomSheet2;
            filesMigrationBottomSheet2.show();
            wasShown = true;
            sharedPreferences.edit().putInt("migration_to_scoped_storage_count", sharedPreferences.getInt("migration_to_scoped_storage_count", 0) + 1).apply();
            return;
        }
        sharedPreferences.edit().putBoolean("migration_to_scoped_storage_finished", true).apply();
    }

    /* loaded from: classes4.dex */
    public static class FilesMigrationBottomSheet extends BottomSheet {
        BaseFragment fragment;

        @Override // org.telegram.p023ui.ActionBar.BottomSheet
        protected boolean canDismissWithSwipe() {
            return false;
        }

        @Override // org.telegram.p023ui.ActionBar.BottomSheet
        protected boolean canDismissWithTouchOutside() {
            return false;
        }

        @Override // org.telegram.p023ui.ActionBar.BottomSheet, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
        public /* bridge */ /* synthetic */ void setLastVisible(boolean z) {
            BaseFragment.AttachedSheet.CC.$default$setLastVisible(this, z);
        }

        public FilesMigrationBottomSheet(BaseFragment baseFragment) {
            super(baseFragment.getParentActivity(), false);
            this.fragment = baseFragment;
            setCanceledOnTouchOutside(false);
            Activity parentActivity = baseFragment.getParentActivity();
            LinearLayout linearLayout = new LinearLayout(parentActivity);
            linearLayout.setOrientation(1);
            StickerImageView stickerImageView = new StickerImageView(parentActivity, this.currentAccount);
            stickerImageView.setStickerNum(7);
            stickerImageView.getImageReceiver().setAutoRepeat(1);
            linearLayout.addView(stickerImageView, LayoutHelper.createLinear(Opcodes.D2F, Opcodes.D2F, 1, 0, 16, 0, 0));
            TextView textView = new TextView(parentActivity);
            textView.setGravity(8388611);
            int i = Theme.key_dialogTextBlack;
            textView.setTextColor(Theme.getColor(i));
            textView.setTextSize(1, 20.0f);
            textView.setTypeface(AndroidUtilities.bold());
            textView.setText(LocaleController.getString(C2369R.string.MigrateOldFolderTitle));
            linearLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 0, 21.0f, 30.0f, 21.0f, 0.0f));
            TextView textView2 = new TextView(parentActivity);
            textView2.setGravity(8388611);
            textView2.setTextSize(1, 15.0f);
            textView2.setTextColor(Theme.getColor(i));
            textView2.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.MigrateOldFolderDescription)));
            linearLayout.addView(textView2, LayoutHelper.createFrame(-1, -2.0f, 0, 21.0f, 15.0f, 21.0f, 16.0f));
            TextView textView3 = new TextView(parentActivity);
            textView3.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
            textView3.setGravity(17);
            textView3.setTextSize(1, 14.0f);
            textView3.setTypeface(AndroidUtilities.bold());
            textView3.setText(LocaleController.getString(C2369R.string.MigrateOldFolderButton));
            textView3.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
            textView3.setBackground(Theme.AdaptiveRipple.filledRectByKey(Theme.key_featuredStickers_addButton, 6.0f));
            linearLayout.addView(textView3, LayoutHelper.createFrame(-1, 48.0f, 0, 16.0f, 15.0f, 16.0f, 16.0f));
            textView3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.messenger.FilesMigrationService$FilesMigrationBottomSheet$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(view);
                }
            });
            ScrollView scrollView = new ScrollView(parentActivity);
            scrollView.addView(linearLayout);
            setCustomView(scrollView);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view) {
            migrateOldFolder();
        }

        public void migrateOldFolder() {
            Activity parentActivity = this.fragment.getParentActivity();
            boolean z = false;
            boolean z2 = parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
            int i = Build.VERSION.SDK_INT;
            if ((i >= 33 && parentActivity.checkSelfPermission("android.permission.READ_MEDIA_IMAGES") == 0 && parentActivity.checkSelfPermission("android.permission.READ_MEDIA_VIDEO") == 0 && parentActivity.checkSelfPermission("android.permission.READ_MEDIA_AUDIO") == 0) || (i < 33 && parentActivity.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0)) {
                z = true;
            }
            if (!z || !z2) {
                ArrayList arrayList = new ArrayList();
                if (!z) {
                    if (i >= 33) {
                        arrayList.add("android.permission.READ_MEDIA_IMAGES");
                        arrayList.add("android.permission.READ_MEDIA_VIDEO");
                        arrayList.add("android.permission.READ_MEDIA_AUDIO");
                    } else {
                        arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
                    }
                }
                if (!z2) {
                    arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
                }
                parentActivity.requestPermissions((String[]) arrayList.toArray(new String[arrayList.size()]), 4);
                return;
            }
            FilesMigrationService.start();
            lambda$new$0();
        }

        @Override // org.telegram.p023ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
        /* renamed from: dismiss */
        public void lambda$new$0() {
            super.lambda$new$0();
            FilesMigrationService.filesMigrationBottomSheet = null;
        }
    }
}
