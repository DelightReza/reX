package com.radolyn.ayugram.preferences.utils;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import com.radolyn.ayugram.utils.filters.AyuFilterUtils;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.p023ui.Components.StickerImageView;

/* loaded from: classes4.dex */
public class FiltersImportBottomSheet extends BottomSheet {
    public FiltersImportBottomSheet(final BaseFragment baseFragment, final AyuFilterUtils.ApplyChanges applyChanges, final Runnable runnable) {
        super(baseFragment.getParentActivity(), false, baseFragment.getResourceProvider());
        Activity parentActivity = baseFragment.getParentActivity();
        fixNavigationBar();
        StringBuilder sb = new StringBuilder();
        if (!applyChanges.newFilters.isEmpty()) {
            sb.append(LocaleController.formatPluralString(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303738996555302L), applyChanges.newFilters.size(), new Object[0]));
            sb.append(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303837780803110L));
        }
        if (!applyChanges.removeFiltersById.isEmpty()) {
            sb.append(LocaleController.formatPluralString(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303846370737702L), applyChanges.removeFiltersById.size(), new Object[0]));
            sb.append(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303962334854694L));
        }
        if (!applyChanges.filtersOverrides.isEmpty()) {
            sb.append(LocaleController.formatPluralString(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303970924789286L), applyChanges.filtersOverrides.size(), new Object[0]));
            sb.append(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019304086888906278L));
        }
        if (!applyChanges.newExclusions.isEmpty()) {
            sb.append(LocaleController.formatPluralString(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019304095478840870L), applyChanges.newExclusions.size(), new Object[0]));
            sb.append(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019304207147990566L));
        }
        if (!applyChanges.removeExclusions.isEmpty()) {
            sb.append(LocaleController.formatPluralString(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019304215737925158L), applyChanges.removeExclusions.size(), new Object[0]));
            sb.append(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019304344586944038L));
        }
        if (!applyChanges.peersToBeResolved.isEmpty()) {
            sb.append(LocaleController.formatPluralString(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019304353176878630L), applyChanges.peersToBeResolved.size(), new Object[0]));
            sb.append(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019304477730930214L));
        }
        FrameLayout frameLayout = new FrameLayout(parentActivity);
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        frameLayout.addView(linearLayout);
        StickerImageView stickerImageView = new StickerImageView(parentActivity, this.currentAccount);
        stickerImageView.setStickerPackName(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019304486320864806L));
        stickerImageView.setStickerNum(6);
        stickerImageView.getImageReceiver().setAutoRepeat(1);
        stickerImageView.getImageReceiver().setAutoRepeatCount(1);
        linearLayout.addView(stickerImageView, LayoutHelper.createLinear(Opcodes.D2F, Opcodes.D2F, 1, 0, 16, 0, 0));
        TextView textView = new TextView(parentActivity);
        textView.setGravity(1);
        textView.setTextColor(getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.getTypeface(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019304585105112614L)));
        textView.setText(LocaleController.getString(C2369R.string.FiltersSheetTitle));
        linearLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 0, 40.0f, 20.0f, 40.0f, 0.0f));
        TextView textView2 = new TextView(parentActivity);
        textView2.setGravity(1);
        textView2.setTextSize(1, 14.0f);
        textView2.setTextColor(getThemedColor(Theme.key_windowBackgroundWhiteGrayText));
        textView2.setText(AndroidUtilities.replaceTags(sb.toString()));
        linearLayout.addView(textView2, LayoutHelper.createFrame(-1, -2.0f, 0, 21.0f, 15.0f, 21.0f, 8.0f));
        TextView textView3 = new TextView(parentActivity);
        ScaleStateListAnimator.apply(textView3, 0.02f, 1.5f);
        textView3.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView3.setGravity(17);
        textView3.setTextSize(1, 14.0f);
        textView3.setTypeface(AndroidUtilities.getTypeface(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019304662414523942L)));
        textView3.setText(LocaleController.getString(C2369R.string.ImportConfirm));
        textView3.setOnClickListener(new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.utils.FiltersImportBottomSheet$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(applyChanges, baseFragment, runnable, view);
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
        textView4.setText(LocaleController.getString(C2369R.string.CancelConfirm));
        textView4.setTextColor(getThemedColor(i));
        textView4.setOnClickListener(new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.utils.FiltersImportBottomSheet$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$1(view);
            }
        });
        linearLayout.addView(textView4, LayoutHelper.createFrame(-1, 48.0f, 0, 16.0f, 0.0f, 16.0f, 0.0f));
        ScrollView scrollView = new ScrollView(parentActivity);
        scrollView.addView(frameLayout);
        setCustomView(scrollView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(AyuFilterUtils.ApplyChanges applyChanges, BaseFragment baseFragment, Runnable runnable, View view) {
        lambda$new$0();
        try {
            AyuFilterUtils.applyChanges(applyChanges);
            BulletinFactory.m1267of(baseFragment).createSimpleBulletin(C2369R.raw.contact_check, LocaleController.getString(C2369R.string.FiltersToastSuccess)).show();
        } catch (Exception unused) {
            BulletinFactory.m1267of(baseFragment).createSimpleBulletin(C2369R.raw.contact_check, LocaleController.getString(C2369R.string.FiltersToastFailImport)).show();
        } finally {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(View view) {
        lambda$new$0();
    }
}
