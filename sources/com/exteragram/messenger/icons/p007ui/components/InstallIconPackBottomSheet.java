package com.exteragram.messenger.icons.p007ui.components;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.icons.IconManager;
import com.exteragram.messenger.icons.IconPack;
import com.exteragram.messenger.utils.text.LocaleUtils;
import java.io.File;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.CheckBox2;
import org.telegram.p023ui.Components.EffectsTextView;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.p023ui.Stories.recorder.ButtonWithCounterView;

/* loaded from: classes3.dex */
public class InstallIconPackBottomSheet extends BottomSheet {
    private final InstallDelegate delegate;
    private final IconPack iconPack;

    public interface InstallDelegate {
        void onInstall(boolean z, boolean z2);
    }

    public InstallIconPackBottomSheet(Context context, final IconPack iconPack, InstallDelegate installDelegate) {
        super(context, false);
        this.iconPack = iconPack;
        this.delegate = installDelegate;
        setCustomView(createView(context));
        setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.exteragram.messenger.icons.ui.components.InstallIconPackBottomSheet$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$new$0(iconPack, dialogInterface);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(IconPack iconPack, DialogInterface dialogInterface) {
        if (iconPack.getLocation() != null) {
            recursiveDelete(iconPack.getLocation());
        }
    }

    private void recursiveDelete(File file) {
        File[] fileArrListFiles;
        if (file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
            for (File file2 : fileArrListFiles) {
                recursiveDelete(file2);
            }
        }
        file.delete();
    }

    private View createView(Context context) {
        final CheckBox2 checkBox2;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(0, AndroidUtilities.m1146dp(16.0f), 0, 0);
        IconPackPreviewView iconPackPreviewView = new IconPackPreviewView(context);
        iconPackPreviewView.setCircularMode(true);
        iconPackPreviewView.setIconPack(this.iconPack);
        linearLayout.addView(iconPackPreviewView, LayoutHelper.createLinear(-1, -2, 1, 0, 0, 0, 16));
        TextView textView = new TextView(context);
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        textView.setGravity(17);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        textView.setText(this.iconPack.getName());
        linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 24.0f, 0.0f, 24.0f, 0.0f));
        EffectsTextView effectsTextView = new EffectsTextView(context, this.resourcesProvider);
        effectsTextView.setGravity(1);
        effectsTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_REGULAR));
        effectsTextView.setMovementMethod(new AndroidUtilities.LinkMovementMethodMy());
        effectsTextView.setLinkTextColor(getThemedColor(Theme.key_dialogTextLink));
        effectsTextView.setTextSize(1, 14.0f);
        effectsTextView.setTextColor(getThemedColor(Theme.key_windowBackgroundWhiteGrayText));
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(this.iconPack.getAuthor())) {
            sb.append(this.iconPack.getAuthor());
        }
        if (!TextUtils.isEmpty(this.iconPack.getVersion())) {
            if (sb.length() > 0) {
                sb.append(" • ");
            }
            sb.append("v");
            sb.append(this.iconPack.getVersion());
        }
        effectsTextView.setText(LocaleUtils.fullyFormatText(sb, LaunchActivity.getSafeLastFragment(), new Runnable() { // from class: com.exteragram.messenger.icons.ui.components.InstallIconPackBottomSheet$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        }));
        linearLayout.addView(effectsTextView, LayoutHelper.createLinear(-1, -2, 24.0f, 4.0f, 24.0f, 24.0f));
        ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context, true, this.resourcesProvider);
        buttonWithCounterView.setCount(LocaleController.formatPluralString("IconCount", this.iconPack.getIcons().size(), new Object[0]), false);
        if (IconManager.INSTANCE.findPackById(this.iconPack.getId()) != null) {
            buttonWithCounterView.setText(LocaleController.getString(C2369R.string.UpdatePack), false);
        } else {
            buttonWithCounterView.setText(LocaleController.getString(C2369R.string.InstallPack), false);
        }
        linearLayout.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 0, 16, 0, 16, 16));
        if (ExteraConfig.iconPacksLayout.contains(this.iconPack.getId())) {
            checkBox2 = null;
        } else {
            checkBox2 = new CheckBox2(context, 21, this.resourcesProvider);
            checkBox2.setColor(Theme.key_radioBackgroundChecked, Theme.key_checkboxDisabled, Theme.key_checkboxCheck);
            checkBox2.setDrawUnchecked(true);
            checkBox2.setChecked(true, false);
            checkBox2.setDrawBackgroundAsArc(10);
            TextView textView2 = new TextView(context);
            textView2.setTextColor(getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
            textView2.setTextSize(1, 14.0f);
            textView2.setText(LocaleController.getString(C2369R.string.EnableAfterInstallation));
            FrameLayout frameLayout = new FrameLayout(context);
            frameLayout.addView(checkBox2, LayoutHelper.createFrame(21, 21.0f, 17, 0.0f, 0.0f, 0.0f, 0.0f));
            LinearLayout linearLayout2 = new LinearLayout(context);
            linearLayout2.setOrientation(0);
            linearLayout2.setPadding(AndroidUtilities.m1146dp(8.0f), AndroidUtilities.m1146dp(6.0f), AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(6.0f));
            linearLayout2.addView(frameLayout, LayoutHelper.createLinear(24, 24, 16, 0, 0, 6, 0));
            linearLayout2.addView(textView2, LayoutHelper.createLinear(-2, -2, 16));
            linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.icons.ui.components.InstallIconPackBottomSheet$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    checkBox2.setChecked(!r0.isChecked(), true);
                }
            });
            ScaleStateListAnimator.apply(linearLayout2, 0.05f, 1.2f);
            linearLayout2.setBackground(Theme.createRadSelectorDrawable(getThemedColor(Theme.key_listSelector), 8, 8));
            linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-2, -2, 1, 0, 0, 0, 8));
        }
        buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.icons.ui.components.InstallIconPackBottomSheet$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$2(checkBox2, view);
            }
        });
        return linearLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2(CheckBox2 checkBox2, View view) {
        lambda$new$0();
        this.delegate.onInstall(checkBox2 != null && checkBox2.isChecked(), IconManager.INSTANCE.findPackById(this.iconPack.getId()) != null);
    }
}
