package com.radolyn.ayugram.p015ui;

import android.content.SharedPreferences;
import com.radolyn.ayugram.AyuConfig;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BaseFragment;

/* loaded from: classes4.dex */
public abstract class AlertUtils {
    public static void showFirstLaunchAlert(final BaseFragment baseFragment) {
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity());
        builder.setTitle(LocaleController.getString(C2369R.string.AppName));
        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.FirstLaunchAlert)));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), null);
        builder.setNeutralButton(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019301570038070822L), new AlertDialog.OnButtonClickListener() { // from class: com.radolyn.ayugram.ui.AlertUtils$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                MessagesController.getInstance(UserConfig.selectedAccount).openByUserName(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019302012419702310L), baseFragment, 1);
            }
        });
        SharedPreferences.Editor editor = AyuConfig.editor;
        String string = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019301643052514854L);
        AyuConfig.sawFirstLaunchAlert = true;
        editor.putBoolean(string, true).apply();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.ui.AlertUtils$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                baseFragment.showDialog(builder.create());
            }
        }, 2500L);
    }

    public static void showExteraChatsAlert(final BaseFragment baseFragment, final String str) {
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity());
        builder.setTitle(LocaleController.getString(C2369R.string.AppName));
        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.ExteraChatsAlert)));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), new AlertDialog.OnButtonClickListener() { // from class: com.radolyn.ayugram.ui.AlertUtils$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                MessagesController.getInstance(UserConfig.selectedAccount).openByUserName(str, baseFragment, 1);
            }
        });
        builder.setNeutralButton(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019301728951860774L), new AlertDialog.OnButtonClickListener() { // from class: com.radolyn.ayugram.ui.AlertUtils$$ExternalSyntheticLambda1
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                MessagesController.getInstance(UserConfig.selectedAccount).openByUserName(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019301960880094758L), baseFragment, 1);
            }
        });
        SharedPreferences.Editor editor = AyuConfig.editor;
        String string = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019301784786435622L);
        AyuConfig.sawExteraChatsAlert = true;
        editor.putBoolean(string, true).apply();
        baseFragment.showDialog(builder.create());
    }

    public static void showLocalPremiumAlert(final BaseFragment baseFragment) {
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity());
        builder.setTitle(LocaleController.getString(C2369R.string.AppName));
        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.LocalPremiumAlert)));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), null);
        SharedPreferences.Editor editor = AyuConfig.editor;
        String string = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019301870685781542L);
        AyuConfig.sawLocalPremiumAlert = true;
        editor.putBoolean(string, true).apply();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.ui.AlertUtils$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                baseFragment.showDialog(builder.create());
            }
        }, 350L);
    }
}
