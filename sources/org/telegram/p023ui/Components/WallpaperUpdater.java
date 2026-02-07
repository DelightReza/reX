package org.telegram.p023ui.Components;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.FileProvider;
import com.exteragram.messenger.utils.system.SystemUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.PhotoAlbumPickerActivity;

/* loaded from: classes6.dex */
public class WallpaperUpdater {
    private String currentPicturePath;
    private File currentWallpaperPath;
    private WallpaperUpdaterDelegate delegate;
    private Activity parentActivity;
    private BaseFragment parentFragment;
    private File picturePath = null;

    public interface WallpaperUpdaterDelegate {
        void didSelectWallpaper(File file, Bitmap bitmap, boolean z);

        void needOpenColorPicker();
    }

    public void cleanup() {
    }

    public WallpaperUpdater(Activity activity, BaseFragment baseFragment, WallpaperUpdaterDelegate wallpaperUpdaterDelegate) {
        this.parentActivity = activity;
        this.parentFragment = baseFragment;
        this.delegate = wallpaperUpdaterDelegate;
    }

    public void showAlert(final boolean z) {
        CharSequence[] charSequenceArr;
        int[] iArr;
        BottomSheet.Builder builder = new BottomSheet.Builder(this.parentActivity);
        builder.setTitle(LocaleController.getString(C2369R.string.ChoosePhoto), true);
        if (z) {
            charSequenceArr = new CharSequence[]{LocaleController.getString(C2369R.string.ChooseTakePhoto), LocaleController.getString(C2369R.string.SelectFromGallery), LocaleController.getString(C2369R.string.SelectColor), LocaleController.getString(C2369R.string.Default)};
            iArr = null;
        } else {
            charSequenceArr = new CharSequence[]{LocaleController.getString(C2369R.string.ChooseTakePhoto), LocaleController.getString(C2369R.string.SelectFromGallery)};
            iArr = new int[]{C2369R.drawable.msg_camera, C2369R.drawable.msg_photos};
        }
        builder.setItems(charSequenceArr, iArr, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.WallpaperUpdater$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$showAlert$0(z, dialogInterface, i);
            }
        });
        builder.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAlert$0(boolean z, DialogInterface dialogInterface, int i) {
        try {
            if (i != 0) {
                if (i == 1) {
                    openGallery();
                    return;
                }
                if (z) {
                    if (i == 2) {
                        this.delegate.needOpenColorPicker();
                        return;
                    } else {
                        if (i == 3) {
                            this.delegate.didSelectWallpaper(null, null, false);
                            return;
                        }
                        return;
                    }
                }
                return;
            }
            try {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File fileGeneratePicturePath = AndroidUtilities.generatePicturePath();
                if (fileGeneratePicturePath != null) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        intent.putExtra("output", FileProvider.getUriForFile(this.parentActivity, ApplicationLoader.getApplicationId() + ".provider", fileGeneratePicturePath));
                        intent.addFlags(2);
                        intent.addFlags(1);
                    } else {
                        intent.putExtra("output", Uri.fromFile(fileGeneratePicturePath));
                    }
                    this.currentPicturePath = fileGeneratePicturePath.getAbsolutePath();
                }
                this.parentActivity.startActivityForResult(intent, 10);
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        } catch (Exception e2) {
            FileLog.m1160e(e2);
        }
    }

    public void openGallery() {
        BaseFragment baseFragment = this.parentFragment;
        if (baseFragment != null) {
            if (Build.VERSION.SDK_INT >= 23 && baseFragment.getParentActivity() != null && !SystemUtils.isImagesPermissionGranted()) {
                SystemUtils.requestImagesPermission(this.parentFragment.getParentActivity());
                return;
            }
            PhotoAlbumPickerActivity photoAlbumPickerActivity = new PhotoAlbumPickerActivity(PhotoAlbumPickerActivity.SELECT_TYPE_WALLPAPER, false, false, null);
            photoAlbumPickerActivity.setAllowSearchImages(false);
            photoAlbumPickerActivity.setDelegate(new PhotoAlbumPickerActivity.PhotoAlbumPickerActivityDelegate() { // from class: org.telegram.ui.Components.WallpaperUpdater.1
                @Override // org.telegram.ui.PhotoAlbumPickerActivity.PhotoAlbumPickerActivityDelegate
                public void didSelectPhotos(ArrayList arrayList, boolean z, int i) {
                    WallpaperUpdater.this.didSelectPhotos(arrayList);
                }

                @Override // org.telegram.ui.PhotoAlbumPickerActivity.PhotoAlbumPickerActivityDelegate
                public void startPhotoSelectActivity() {
                    try {
                        Intent intent = new Intent("android.intent.action.PICK");
                        intent.setType("image/*");
                        WallpaperUpdater.this.parentActivity.startActivityForResult(intent, 11);
                    } catch (Exception e) {
                        FileLog.m1160e(e);
                    }
                }
            });
            this.parentFragment.presentFragment(photoAlbumPickerActivity);
            return;
        }
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        this.parentActivity.startActivityForResult(intent, 11);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void didSelectPhotos(ArrayList arrayList) {
        try {
            if (arrayList.isEmpty()) {
                return;
            }
            SendMessagesHelper.SendingMediaInfo sendingMediaInfo = (SendMessagesHelper.SendingMediaInfo) arrayList.get(0);
            if (sendingMediaInfo.path != null) {
                this.currentWallpaperPath = new File(FileLoader.getDirectory(4), Utilities.random.nextInt() + ".jpg");
                Point realScreenSize = AndroidUtilities.getRealScreenSize();
                Bitmap bitmapLoadBitmap = ImageLoader.loadBitmap(sendingMediaInfo.path, null, (float) realScreenSize.x, (float) realScreenSize.y, true);
                bitmapLoadBitmap.compress(Bitmap.CompressFormat.JPEG, 87, new FileOutputStream(this.currentWallpaperPath));
                this.delegate.didSelectWallpaper(this.currentWallpaperPath, bitmapLoadBitmap, true);
            }
        } catch (Throwable th) {
            FileLog.m1160e(th);
        }
    }

    public String getCurrentPicturePath() {
        return this.currentPicturePath;
    }

    public void setCurrentPicturePath(String str) {
        this.currentPicturePath = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0074 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v11, types: [java.io.FileOutputStream] */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onActivityResult(int r8, int r9, android.content.Intent r10) {
        /*
            r7 = this;
            r0 = -1
            if (r9 != r0) goto Ld4
            r9 = 10
            r0 = 0
            r1 = 87
            r2 = 1
            java.lang.String r3 = ".jpg"
            r4 = 4
            r5 = 0
            if (r8 != r9) goto L7d
            java.lang.String r8 = r7.currentPicturePath
            org.telegram.messenger.AndroidUtilities.addMediaToGallery(r8)
            java.io.File r8 = new java.io.File     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            java.io.File r9 = org.telegram.messenger.FileLoader.getDirectory(r4)     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            r10.<init>()     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            java.security.SecureRandom r4 = org.telegram.messenger.Utilities.random     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            int r4 = r4.nextInt()     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            r10.append(r4)     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            r10.append(r3)     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            java.lang.String r10 = r10.toString()     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            r8.<init>(r9, r10)     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            r7.currentWallpaperPath = r8     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            android.graphics.Point r8 = org.telegram.messenger.AndroidUtilities.getRealScreenSize()     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            java.lang.String r9 = r7.currentPicturePath     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            int r10 = r8.x     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            float r10 = (float) r10     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            int r8 = r8.y     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            float r8 = (float) r8     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            android.graphics.Bitmap r8 = org.telegram.messenger.ImageLoader.loadBitmap(r9, r5, r10, r8, r2)     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            java.io.FileOutputStream r9 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            java.io.File r10 = r7.currentWallpaperPath     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            r9.<init>(r10)     // Catch: java.lang.Throwable -> L65 java.lang.Exception -> L67
            android.graphics.Bitmap$CompressFormat r10 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r8.compress(r10, r1, r9)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            org.telegram.ui.Components.WallpaperUpdater$WallpaperUpdaterDelegate r10 = r7.delegate     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            java.io.File r1 = r7.currentWallpaperPath     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r10.didSelectWallpaper(r1, r8, r0)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
        L57:
            r9.close()     // Catch: java.lang.Exception -> L5b
            goto L6f
        L5b:
            r8 = move-exception
            org.telegram.messenger.FileLog.m1160e(r8)
            goto L6f
        L60:
            r8 = move-exception
            r5 = r9
            goto L72
        L63:
            r8 = move-exception
            goto L69
        L65:
            r8 = move-exception
            goto L72
        L67:
            r8 = move-exception
            r9 = r5
        L69:
            org.telegram.messenger.FileLog.m1160e(r8)     // Catch: java.lang.Throwable -> L60
            if (r9 == 0) goto L6f
            goto L57
        L6f:
            r7.currentPicturePath = r5
            goto Ld4
        L72:
            if (r5 == 0) goto L7c
            r5.close()     // Catch: java.lang.Exception -> L78
            goto L7c
        L78:
            r9 = move-exception
            org.telegram.messenger.FileLog.m1160e(r9)
        L7c:
            throw r8
        L7d:
            r9 = 11
            if (r8 != r9) goto Ld4
            if (r10 == 0) goto Ld4
            android.net.Uri r8 = r10.getData()
            if (r8 != 0) goto L8a
            goto Ld4
        L8a:
            java.io.File r8 = new java.io.File     // Catch: java.lang.Exception -> Ld0
            java.io.File r9 = org.telegram.messenger.FileLoader.getDirectory(r4)     // Catch: java.lang.Exception -> Ld0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Ld0
            r4.<init>()     // Catch: java.lang.Exception -> Ld0
            java.security.SecureRandom r6 = org.telegram.messenger.Utilities.random     // Catch: java.lang.Exception -> Ld0
            int r6 = r6.nextInt()     // Catch: java.lang.Exception -> Ld0
            r4.append(r6)     // Catch: java.lang.Exception -> Ld0
            r4.append(r3)     // Catch: java.lang.Exception -> Ld0
            java.lang.String r3 = r4.toString()     // Catch: java.lang.Exception -> Ld0
            r8.<init>(r9, r3)     // Catch: java.lang.Exception -> Ld0
            r7.currentWallpaperPath = r8     // Catch: java.lang.Exception -> Ld0
            android.graphics.Point r8 = org.telegram.messenger.AndroidUtilities.getRealScreenSize()     // Catch: java.lang.Exception -> Ld0
            android.net.Uri r9 = r10.getData()     // Catch: java.lang.Exception -> Ld0
            int r10 = r8.x     // Catch: java.lang.Exception -> Ld0
            float r10 = (float) r10     // Catch: java.lang.Exception -> Ld0
            int r8 = r8.y     // Catch: java.lang.Exception -> Ld0
            float r8 = (float) r8     // Catch: java.lang.Exception -> Ld0
            android.graphics.Bitmap r8 = org.telegram.messenger.ImageLoader.loadBitmap(r5, r9, r10, r8, r2)     // Catch: java.lang.Exception -> Ld0
            java.io.FileOutputStream r9 = new java.io.FileOutputStream     // Catch: java.lang.Exception -> Ld0
            java.io.File r10 = r7.currentWallpaperPath     // Catch: java.lang.Exception -> Ld0
            r9.<init>(r10)     // Catch: java.lang.Exception -> Ld0
            android.graphics.Bitmap$CompressFormat r10 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch: java.lang.Exception -> Ld0
            r8.compress(r10, r1, r9)     // Catch: java.lang.Exception -> Ld0
            org.telegram.ui.Components.WallpaperUpdater$WallpaperUpdaterDelegate r9 = r7.delegate     // Catch: java.lang.Exception -> Ld0
            java.io.File r10 = r7.currentWallpaperPath     // Catch: java.lang.Exception -> Ld0
            r9.didSelectWallpaper(r10, r8, r0)     // Catch: java.lang.Exception -> Ld0
            return
        Ld0:
            r8 = move-exception
            org.telegram.messenger.FileLog.m1160e(r8)
        Ld4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.WallpaperUpdater.onActivityResult(int, int, android.content.Intent):void");
    }
}
