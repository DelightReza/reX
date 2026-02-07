package com.exteragram.messenger.components;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.preferences.OtherPreferencesActivity;
import com.exteragram.messenger.utils.network.BankingUtils;
import com.exteragram.messenger.utils.network.RemoteUtils;
import java.util.HashMap;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.ColoredImageSpan;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.LinkSpanDrawable;
import org.telegram.p023ui.Components.RLottieImageView;
import org.telegram.p023ui.Stories.recorder.ButtonWithCounterView;

/* loaded from: classes3.dex */
public class SupporterBottomSheet extends BottomSheet {
    public SupporterBottomSheet(final BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
        super(baseFragment.getParentActivity(), false, resourcesProvider);
        Activity parentActivity = baseFragment.getParentActivity();
        fixNavigationBar();
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        RLottieImageView rLottieImageView = new RLottieImageView(getContext());
        rLottieImageView.setScaleType(ImageView.ScaleType.CENTER);
        rLottieImageView.setImageResource(C2369R.drawable.extera_heart_large);
        rLottieImageView.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_IN));
        rLottieImageView.setBackground(Theme.createCircleDrawable(AndroidUtilities.m1146dp(80.0f), ContextCompat.getColor(parentActivity, C2369R.color.ic_background_extera)));
        linearLayout.addView(rLottieImageView, LayoutHelper.createLinear(80, 80, 1, 0, 28, 0, 0));
        TextView textView = new TextView(parentActivity);
        textView.setText(LocaleController.getString(C2369R.string.SupportDevelopment));
        textView.setTypeface(AndroidUtilities.bold());
        int i = Theme.key_windowBackgroundWhiteBlackText;
        textView.setTextColor(Theme.getColor(i, resourcesProvider));
        textView.setTextSize(1, 20.0f);
        textView.setGravity(1);
        linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, 1, 22, 14, 22, 0));
        TextView textView2 = new TextView(parentActivity);
        textView2.setText(LocaleController.getString(C2369R.string.SupportDevelopmentInfo));
        textView2.setTextColor(Theme.getColor(i, resourcesProvider));
        textView2.setTextSize(1, 14.0f);
        textView2.setGravity(1);
        linearLayout.addView(textView2, LayoutHelper.createLinear(-2, -2, 1, 22, 8, 22, 0));
        BankingUtils.ExchangeRates exchangeRates = ExteraConfig.getExchangeRates();
        if (exchangeRates == null) {
            HashMap map = new HashMap();
            map.put("ton", Double.valueOf(5.0d));
            map.put("rub", Double.valueOf(500.0d));
            exchangeRates = new BankingUtils.ExchangeRates(map);
        }
        String str = "$" + RemoteUtils.getFloatConfigValue("donates_amount_usd", 5.0f).floatValue();
        String str2 = "TON " + exchangeRates.format("ton");
        String str3 = Math.round(exchangeRates.format("rub")) + "₽";
        String string = LocaleController.formatString(C2369R.string.MakeDonationInfo, str, str2 + ", " + str3);
        int i2 = Theme.key_chat_messageLinkIn;
        SpannableStringBuilder spannableStringBuilderReplaceSingleTag = AndroidUtilities.replaceSingleTag(string, i2, 0, new Runnable() { // from class: com.exteragram.messenger.components.SupporterBottomSheet$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0(baseFragment);
            }
        });
        SpannableString spannableString = new SpannableString("TON");
        ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2369R.drawable.mini_ton);
        coloredImageSpan.setWidth(AndroidUtilities.m1146dp(13.0f));
        spannableString.setSpan(coloredImageSpan, 0, spannableString.length(), 33);
        linearLayout.addView(new FeatureCell(parentActivity, C2369R.drawable.menu_feature_paid, LocaleController.getString(C2369R.string.MakeDonation), AndroidUtilities.replaceCharSequence("TON", spannableStringBuilderReplaceSingleTag, spannableString)), LayoutHelper.createLinear(-1, -2, 0.0f, 0, 0, 20, 0, 0));
        linearLayout.addView(new FeatureCell(parentActivity, C2369R.drawable.menu_feature_wallpaper, LocaleController.getString(C2369R.string.SendProof), AndroidUtilities.replaceSingleTag(LocaleController.getString(C2369R.string.SendProofInfo), i2, 0, new Runnable() { // from class: com.exteragram.messenger.components.SupporterBottomSheet$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$1(baseFragment);
            }
        })), LayoutHelper.createLinear(-1, -2, 0.0f, 0, 0, 16, 0, 0));
        linearLayout.addView(new FeatureCell(parentActivity, C2369R.drawable.menu_feature_reactions, LocaleController.getString(C2369R.string.ReceiveBadge), LocaleController.getString(C2369R.string.ReceiveBadgeInfo)), LayoutHelper.createLinear(-1, -2, 0.0f, 0, 0, 16, 0, 0));
        ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(parentActivity, true, null);
        buttonWithCounterView.setText(LocaleController.getString(C2369R.string.Close), false);
        buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.components.SupporterBottomSheet$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$2(view);
            }
        });
        linearLayout.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 0, 14, 22, 14, 14));
        ScrollView scrollView = new ScrollView(getContext());
        scrollView.addView(linearLayout);
        setCustomView(scrollView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(BaseFragment baseFragment) {
        lambda$new$0();
        baseFragment.presentFragment(new OtherPreferencesActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(BaseFragment baseFragment) {
        lambda$new$0();
        MessagesController.getInstance(this.currentAccount).openByUserName("exteraOwner", baseFragment, 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        lambda$new$0();
    }

    public static SupporterBottomSheet showAlert(BaseFragment baseFragment) {
        return showAlert(baseFragment, baseFragment.getResourceProvider());
    }

    public static SupporterBottomSheet showAlert(BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
        SupporterBottomSheet supporterBottomSheet = new SupporterBottomSheet(baseFragment, resourcesProvider);
        if (baseFragment.getParentActivity() != null) {
            baseFragment.showDialog(supporterBottomSheet);
        }
        return supporterBottomSheet;
    }

    private class FeatureCell extends FrameLayout {
        public FeatureCell(Context context, int i, CharSequence charSequence, CharSequence charSequence2) {
            super(context);
            boolean z = LocaleController.isRTL;
            ImageView imageView = new ImageView(getContext());
            Drawable drawableMutate = ContextCompat.getDrawable(getContext(), i).mutate();
            int i2 = Theme.key_windowBackgroundWhiteBlackText;
            drawableMutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i2, ((BottomSheet) SupporterBottomSheet.this).resourcesProvider), PorterDuff.Mode.MULTIPLY));
            imageView.setImageDrawable(drawableMutate);
            addView(imageView, LayoutHelper.createFrame(24, 24.0f, z ? 5 : 3, z ? 0.0f : 27.0f, 6.0f, z ? 27.0f : 0.0f, 0.0f));
            TextView textView = new TextView(getContext());
            textView.setText(charSequence);
            textView.setTextColor(Theme.getColor(i2, ((BottomSheet) SupporterBottomSheet.this).resourcesProvider));
            textView.setTextSize(1, 14.0f);
            textView.setTypeface(AndroidUtilities.bold());
            addView(textView, LayoutHelper.createFrame(-2, -2.0f, z ? 5 : 3, z ? 27.0f : 68.0f, 0.0f, z ? 68.0f : 27.0f, 0.0f));
            LinkSpanDrawable.LinksTextView linksTextView = new LinkSpanDrawable.LinksTextView(getContext());
            linksTextView.setText(charSequence2);
            linksTextView.setTextSize(1, 14.0f);
            linksTextView.setTextColor(Theme.getColor(Theme.key_dialogTextGray3, ((BottomSheet) SupporterBottomSheet.this).resourcesProvider));
            linksTextView.setLinkTextColor(Theme.getColor(Theme.key_chat_messageLinkIn, ((BottomSheet) SupporterBottomSheet.this).resourcesProvider));
            linksTextView.setLineSpacing(AndroidUtilities.m1146dp(2.0f), 1.0f);
            addView(linksTextView, LayoutHelper.createFrame(-2, -2.0f, z ? 5 : 3, z ? 27.0f : 68.0f, 20.0f, z ? 68.0f : 27.0f, 0.0f));
        }
    }
}
