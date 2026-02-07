package com.radolyn.ayugram.preferences.utils;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import com.radolyn.ayugram.controllers.AyuMessagesController;
import com.radolyn.ayugram.database.AyuData;
import com.radolyn.ayugram.utils.android.StorageUtils;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.p023ui.Components.StickerImageView;

/* loaded from: classes4.dex */
public class DatabaseImportExportBottomSheet extends BottomSheet {
    private final boolean export;
    private final BaseFragment fragment;

    public DatabaseImportExportBottomSheet(final BaseFragment baseFragment, final boolean z) {
        super(baseFragment.getParentActivity(), false, baseFragment.getResourceProvider());
        this.fragment = baseFragment;
        this.export = z;
        Activity parentActivity = baseFragment.getParentActivity();
        fixNavigationBar();
        FrameLayout frameLayout = new FrameLayout(parentActivity);
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        frameLayout.addView(linearLayout);
        StickerImageView stickerImageView = new StickerImageView(parentActivity, this.currentAccount);
        stickerImageView.setStickerPackName(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303485593484838L));
        stickerImageView.setStickerNum(6);
        stickerImageView.getImageReceiver().setAutoRepeat(1);
        stickerImageView.getImageReceiver().setAutoRepeatCount(1);
        linearLayout.addView(stickerImageView, LayoutHelper.createLinear(Opcodes.D2F, Opcodes.D2F, 1, 0, 16, 0, 0));
        TextView textView = new TextView(parentActivity);
        textView.setGravity(1);
        textView.setTextColor(getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.getTypeface(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303584377732646L)));
        textView.setText(LocaleController.getString(z ? C2369R.string.ExportDataTitle : C2369R.string.ImportDataTitle));
        linearLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 0, 40.0f, 20.0f, 40.0f, 0.0f));
        TextView textView2 = new TextView(parentActivity);
        textView2.setGravity(1);
        textView2.setTextSize(1, 14.0f);
        textView2.setTextColor(getThemedColor(Theme.key_windowBackgroundWhiteGrayText));
        textView2.setText(LocaleController.getString(z ? C2369R.string.ExportDataDescription : C2369R.string.ImportDataDescription));
        linearLayout.addView(textView2, LayoutHelper.createFrame(-1, -2.0f, 0, 21.0f, 15.0f, 21.0f, 8.0f));
        TextView textView3 = new TextView(parentActivity);
        ScaleStateListAnimator.apply(textView3, 0.02f, 1.5f);
        textView3.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView3.setGravity(17);
        textView3.setTextSize(1, 14.0f);
        textView3.setTypeface(AndroidUtilities.getTypeface(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303661687143974L)));
        textView3.setText(LocaleController.getString(z ? C2369R.string.ExportDataConfirm : C2369R.string.ImportDataConfirm));
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.utils.DatabaseImportExportBottomSheet$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$2(z, baseFragment, view);
            }
        });
        textView3.setTextColor(getThemedColor(Theme.key_featuredStickers_buttonText));
        int iM1146dp = AndroidUtilities.m1146dp(8.0f);
        int i = Theme.key_featuredStickers_addButton;
        textView3.setBackground(Theme.createSimpleSelectorRoundRectDrawable(iM1146dp, getThemedColor(i), ColorUtils.setAlphaComponent(getThemedColor(Theme.key_windowBackgroundWhite), Opcodes.ISHL)));
        linearLayout.addView(textView3, LayoutHelper.createFrame(-1, 48.0f, 0, 16.0f, 15.0f, 16.0f, 8.0f));
        TextView textView4 = new TextView(parentActivity);
        ScaleStateListAnimator.apply(textView4, 0.02f, 1.5f);
        textView4.setGravity(17);
        textView4.setTextSize(1, 14.0f);
        textView4.setText(LocaleController.getString(z ? C2369R.string.ExportDataCancel : C2369R.string.ImportDataCancel));
        textView4.setTextColor(getThemedColor(i));
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.utils.DatabaseImportExportBottomSheet$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$3(view);
            }
        });
        linearLayout.addView(textView4, LayoutHelper.createFrame(-1, 48.0f, 0, 16.0f, 0.0f, 16.0f, 0.0f));
        ScrollView scrollView = new ScrollView(parentActivity);
        scrollView.addView(frameLayout);
        setCustomView(scrollView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(final boolean z, final BaseFragment baseFragment, View view) {
        lambda$new$0();
        AyuMessagesController.getInstance(UserConfig.selectedAccount).getExecutor().submit(new Runnable() { // from class: com.radolyn.ayugram.preferences.utils.DatabaseImportExportBottomSheet$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DatabaseImportExportBottomSheet.$r8$lambda$SeBnHDXXYb9YG5q19rE3HGmQw2g(z, baseFragment);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$SeBnHDXXYb9YG5q19rE3HGmQw2g(final boolean z, final BaseFragment baseFragment) {
        final boolean zExportDatabase = z ? AyuData.exportDatabase() : AyuData.importDatabase();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.preferences.utils.DatabaseImportExportBottomSheet$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                DatabaseImportExportBottomSheet.$r8$lambda$K8xO4aoJ5qCVehlNSmiMY4KrE3E(zExportDatabase, baseFragment, z);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$K8xO4aoJ5qCVehlNSmiMY4KrE3E(boolean z, BaseFragment baseFragment, boolean z2) {
        if (z) {
            BulletinFactory.m1267of(baseFragment).createSimpleBulletin(C2369R.raw.info, LocaleController.getString(z2 ? C2369R.string.ExportDataSuccess : C2369R.string.ImportDataSuccess)).show();
        } else {
            BulletinFactory.m1267of(baseFragment).createErrorBulletin(LocaleController.getString(z2 ? C2369R.string.ExportDataFailure : C2369R.string.ImportDataFailure)).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(View view) {
        lambda$new$0();
    }

    public void showIfPossible() {
        if (this.export) {
            show();
            return;
        }
        if (AyuData.canImportDatabase()) {
            if (StorageUtils.arePermissionsGranted()) {
                show();
                return;
            } else {
                StorageUtils.ensureHasPermissions(this.fragment.getParentActivity());
                return;
            }
        }
        if (AyuData.canImportDatabase()) {
            return;
        }
        BulletinFactory.m1267of(this.fragment).createErrorBulletin(LocaleController.getString(C2369R.string.ImportDataNotFound)).show();
    }
}
