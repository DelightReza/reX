package org.telegram.p023ui.Components.Premium;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.LayoutHelper;

/* loaded from: classes6.dex */
public class PremiumNotAvailableBottomSheet extends BottomSheet {
    public PremiumNotAvailableBottomSheet(final BaseFragment baseFragment) {
        super(baseFragment.getParentActivity(), false);
        Activity parentActivity = baseFragment.getParentActivity();
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        TextView textView = new TextView(parentActivity);
        textView.setGravity(8388611);
        int i = Theme.key_dialogTextBlack;
        textView.setTextColor(Theme.getColor(i));
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        linearLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, 0, 21.0f, 16.0f, 21.0f, 0.0f));
        TextView textView2 = new TextView(parentActivity);
        textView2.setGravity(8388611);
        textView2.setTextSize(1, 16.0f);
        textView2.setTextColor(Theme.getColor(i));
        linearLayout.addView(textView2, LayoutHelper.createFrame(-1, -2.0f, 0, 21.0f, 15.0f, 21.0f, 16.0f));
        TextView textView3 = new TextView(parentActivity);
        textView3.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView3.setGravity(17);
        textView3.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
        textView3.setTextSize(1, 14.0f);
        textView3.setTypeface(AndroidUtilities.bold());
        int i2 = Theme.key_featuredStickers_addButton;
        textView3.setBackground(Theme.AdaptiveRipple.filledRectByKey(i2, 8.0f));
        textView3.setText(LocaleController.getString(C2369R.string.InstallOfficialApp));
        textView3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.Premium.PremiumNotAvailableBottomSheet$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PremiumNotAvailableBottomSheet.$r8$lambda$txcDGP6h515KnUS4SmifJGMaQKM(view);
            }
        });
        TextView textView4 = new TextView(parentActivity);
        textView4.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView4.setGravity(17);
        textView4.setTextColor(Theme.getColor(i2));
        textView4.setTextSize(1, 14.0f);
        textView4.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        int i3 = Theme.key_dialogBackground;
        textView4.setBackground(Theme.AdaptiveRipple.filledRect(i3, 8.0f));
        textView4.setText(LocaleController.getString(C2369R.string.OpenPremiumBot));
        textView4.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.Premium.PremiumNotAvailableBottomSheet$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MessagesController.getInstance(UserConfig.selectedAccount).openByUserName("PremiumBot", baseFragment, 1);
            }
        });
        FrameLayout frameLayout = new FrameLayout(parentActivity);
        frameLayout.addView(textView3, LayoutHelper.createFrame(-1, 48.0f, 48, 16.0f, 0.0f, 16.0f, 0.0f));
        frameLayout.addView(textView4, LayoutHelper.createFrame(-1, 48.0f, 48, 16.0f, 56.0f, 16.0f, 0.0f));
        frameLayout.setBackgroundColor(getThemedColor(i3));
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, 104, 80));
        textView.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.SubscribeToPremiumOfficialAppNeeded)));
        textView2.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.SubscribeToPremiumOfficialAppNeededDescription)));
        ScrollView scrollView = new ScrollView(parentActivity);
        scrollView.addView(linearLayout);
        setCustomView(scrollView);
    }

    public static /* synthetic */ void $r8$lambda$txcDGP6h515KnUS4SmifJGMaQKM(View view) {
        try {
            view.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger")));
        } catch (ActivityNotFoundException e) {
            FileLog.m1160e(e);
        }
    }
}
