package com.exteragram.messenger.plugins.p009ui.components;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.components.VerticalImageSpan;
import com.exteragram.messenger.plugins.Plugin;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.PythonPluginsEngine;
import com.exteragram.messenger.utils.text.LocaleUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.CheckBox2;
import org.telegram.p023ui.Components.EffectsTextView;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.RLottieImageView;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.p023ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.p023ui.Stories.recorder.HintView2;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public class InstallPluginBottomSheet extends BottomSheet {
    private HintView2 currentHint;
    private boolean enableAfterInstallation;

    @Override // org.telegram.p023ui.ActionBar.BottomSheet, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
    public /* bridge */ /* synthetic */ void setLastVisible(boolean z) {
        BaseFragment.AttachedSheet.CC.$default$setLastVisible(this, z);
    }

    public InstallPluginBottomSheet(final BaseFragment baseFragment, final PluginsController.PluginValidationResult pluginValidationResult, final PluginInstallParams pluginInstallParams) {
        boolean z;
        boolean z2;
        super(baseFragment.getParentActivity(), false, baseFragment.getResourceProvider());
        this.enableAfterInstallation = false;
        Activity parentActivity = baseFragment.getParentActivity();
        fixNavigationBar();
        boolean zContainsKey = PluginsController.getInstance().plugins.containsKey(pluginValidationResult.plugin.getId());
        FrameLayout frameLayout = new FrameLayout(parentActivity);
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        linearLayout.setClipChildren(false);
        linearLayout.setClipToPadding(false);
        frameLayout.addView(linearLayout);
        if (pluginValidationResult.plugin.getPack() != null && pluginValidationResult.plugin.getIndex() >= 0) {
            BackupImageView backupImageView = new BackupImageView(parentActivity) { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet.1
                @Override // org.telegram.p023ui.Components.BackupImageView, android.view.View
                @SuppressLint({"DrawAllocation"})
                public void onDraw(Canvas canvas) {
                    Path path = new Path();
                    float fM1146dp = AndroidUtilities.m1146dp(12.0f);
                    path.addRoundRect(new RectF(0.0f, 0.0f, getWidth(), getHeight()), fM1146dp, fM1146dp, Path.Direction.CW);
                    canvas.save();
                    canvas.clipPath(path);
                    super.onDraw(canvas);
                    canvas.restore();
                    Paint paint = new Paint(1);
                    paint.setColor(InstallPluginBottomSheet.this.getThemedColor(Theme.key_dialogBackground));
                    paint.setStrokeWidth(AndroidUtilities.m1146dp(4.0f));
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(getMeasuredWidth() - AndroidUtilities.m1146dp(10.0f), getMeasuredHeight() - AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(12.0f), paint);
                    Paint paint2 = new Paint(1);
                    paint2.setColor(InstallPluginBottomSheet.this.getThemedColor(Theme.key_featuredStickers_addButton));
                    paint2.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(getMeasuredWidth() - AndroidUtilities.m1146dp(10.0f), getMeasuredHeight() - AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(12.0f), paint2);
                    Drawable drawable = ContextCompat.getDrawable(getContext(), C2369R.drawable.plugin_large);
                    if (drawable != null) {
                        drawable.setColorFilter(new PorterDuffColorFilter(InstallPluginBottomSheet.this.getThemedColor(Theme.key_featuredStickers_buttonText), PorterDuff.Mode.SRC_IN));
                        drawable.setBounds(getMeasuredWidth() - AndroidUtilities.m1146dp(18.0f), getMeasuredHeight() - AndroidUtilities.m1146dp(18.0f), getMeasuredWidth() - AndroidUtilities.m1146dp(2.0f), getMeasuredHeight() - AndroidUtilities.m1146dp(2.0f));
                        drawable.draw(canvas);
                    }
                }
            };
            backupImageView.setRoundRadius(AndroidUtilities.m1146dp(12.0f));
            backupImageView.getImageReceiver().setAutoRepeat(1);
            backupImageView.getImageReceiver().setAutoRepeatCount(1);
            linearLayout.addView(backupImageView, LayoutHelper.createLinear(78, 78, 1, 0, 28, 0, 0));
            MediaDataController.getInstance(UserConfig.selectedAccount).setPlaceholderImageByIndex(backupImageView, pluginValidationResult.plugin.getPack(), pluginValidationResult.plugin.getIndex(), "150_150");
        } else {
            RLottieImageView rLottieImageView = new RLottieImageView(getContext());
            rLottieImageView.setScaleType(ImageView.ScaleType.CENTER);
            rLottieImageView.setImageResource(C2369R.drawable.plugin_large);
            rLottieImageView.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_featuredStickers_buttonText), PorterDuff.Mode.SRC_IN));
            rLottieImageView.setBackground(Theme.createCircleDrawable(AndroidUtilities.m1146dp(78.0f), getThemedColor(Theme.key_featuredStickers_addButton)));
            linearLayout.addView(rLottieImageView, LayoutHelper.createLinear(78, 78, 1, 0, 28, 0, 0));
        }
        TextView textView = new TextView(parentActivity);
        textView.setGravity(1);
        int i = Theme.key_windowBackgroundWhiteBlackText;
        textView.setTextColor(getThemedColor(i));
        textView.setTextSize(1, 18.0f);
        textView.setTypeface(AndroidUtilities.bold());
        textView.setText(pluginValidationResult.plugin.getName());
        linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 0, 40, 16, 40, 0));
        EffectsTextView effectsTextView = new EffectsTextView(parentActivity, baseFragment.getResourceProvider());
        effectsTextView.setGravity(1);
        effectsTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_REGULAR));
        effectsTextView.setMovementMethod(new AndroidUtilities.LinkMovementMethodMy());
        int i2 = Theme.key_dialogTextLink;
        effectsTextView.setLinkTextColor(getThemedColor(i2));
        effectsTextView.setTextSize(1, 14.0f);
        int i3 = Theme.key_windowBackgroundWhiteGrayText;
        effectsTextView.setTextColor(getThemedColor(i3));
        SpannableStringBuilder spannableStringBuilderAppend = new SpannableStringBuilder(LocaleController.getString(C2369R.string.PluginVersion)).append((CharSequence) " ");
        int length = spannableStringBuilderAppend.length();
        if (zContainsKey) {
            z = zContainsKey;
            Plugin plugin = PluginsController.getInstance().plugins.get(pluginValidationResult.plugin.getId());
            if (plugin != null) {
                spannableStringBuilderAppend.append((CharSequence) plugin.getVersion()).append((CharSequence) " -> ").append((CharSequence) pluginValidationResult.plugin.getVersion());
                spannableStringBuilderAppend = VerticalImageSpan.createSpan(getContext(), C2369R.drawable.msg_mini_arrow_mediathin, spannableStringBuilderAppend.toString(), "->", i3, this.resourcesProvider);
                spannableStringBuilderAppend.setSpan(new StrikethroughSpan(), length, plugin.getVersion().length() + length, 33);
            }
        } else {
            z = zContainsKey;
            spannableStringBuilderAppend.append((CharSequence) pluginValidationResult.plugin.getVersion());
        }
        spannableStringBuilderAppend.append((CharSequence) " • ").append(LocaleUtils.formatWithUsernames(pluginValidationResult.plugin.getAuthor(), baseFragment, new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        }));
        effectsTextView.setText(spannableStringBuilderAppend);
        linearLayout.addView(effectsTextView, LayoutHelper.createLinear(-1, -2, 0, 21, 4, 21, 0));
        boolean z3 = pluginInstallParams.trusted;
        int i4 = z3 ? C2369R.drawable.trusted_mini : C2369R.drawable.unknown_mini;
        int themedColor = getThemedColor(z3 ? Theme.key_windowBackgroundWhiteGreenText : Theme.key_text_RedRegular);
        String string = LocaleController.getString(pluginInstallParams.trusted ? C2369R.string.PluginSourceTrusted : C2369R.string.PluginSourceUnknown);
        final LinearLayout linearLayout2 = new LinearLayout(parentActivity);
        ScaleStateListAnimator.apply(linearLayout2, 0.05f, 1.5f);
        linearLayout2.setOrientation(0);
        linearLayout2.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1146dp(20.0f), AndroidUtilities.m1146dp(20.0f), AndroidUtilities.multiplyAlphaComponent(themedColor, 0.1f)));
        linearLayout2.setPadding(AndroidUtilities.m1146dp(12.0f), AndroidUtilities.m1146dp(6.0f), AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(6.0f));
        linearLayout2.setGravity(17);
        ImageView imageView = new ImageView(parentActivity);
        imageView.setImageResource(i4);
        imageView.setColorFilter(new PorterDuffColorFilter(themedColor, PorterDuff.Mode.SRC_IN));
        linearLayout2.addView(imageView, LayoutHelper.createLinear(14, 14, 16, 0, 0, 6, 0));
        TextView textView2 = new TextView(parentActivity);
        textView2.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_REGULAR));
        textView2.setTextColor(themedColor);
        textView2.setTextSize(1, 13.0f);
        textView2.setText(string);
        linearLayout2.addView(textView2);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-2, -2, 17, 0, 12, 0, 0));
        linearLayout2.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$1(pluginInstallParams, view);
            }
        });
        EffectsTextView effectsTextView2 = new EffectsTextView(parentActivity);
        effectsTextView2.setGravity(3);
        effectsTextView2.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_REGULAR));
        effectsTextView2.setMovementMethod(new AndroidUtilities.LinkMovementMethodMy());
        effectsTextView2.setLinkTextColor(getThemedColor(i2));
        effectsTextView2.setTextSize(1, 15.0f);
        effectsTextView2.setTextColor(getThemedColor(i));
        effectsTextView2.setText(LocaleUtils.fullyFormatText(pluginValidationResult.plugin.getDescription(), baseFragment, new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        }));
        linearLayout.addView(effectsTextView2, LayoutHelper.createLinear(-1, -2, 0, 21, 28, 21, 0));
        ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(parentActivity, true, this.resourcesProvider);
        buttonWithCounterView.setText(LocaleController.getString(z ? C2369R.string.UpdatePlugin : C2369R.string.InstallPlugin), false);
        final boolean z4 = z;
        buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$8(pluginInstallParams, pluginValidationResult, baseFragment, z4, view);
            }
        });
        linearLayout.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 0, 16, 28, 16, 16));
        if (!pluginValidationResult.plugin.isEnabled()) {
            final CheckBox2 checkBox2 = new CheckBox2(parentActivity, 21, this.resourcesProvider);
            checkBox2.setColor(Theme.key_radioBackgroundChecked, Theme.key_checkboxDisabled, Theme.key_checkboxCheck);
            checkBox2.setDrawUnchecked(true);
            checkBox2.setChecked(this.enableAfterInstallation, false);
            checkBox2.setDrawBackgroundAsArc(10);
            TextView textView3 = new TextView(parentActivity);
            textView3.setTextColor(getThemedColor(i));
            textView3.setTextSize(1, 14.0f);
            textView3.setText(LocaleController.getString(C2369R.string.EnableAfterInstallation));
            FrameLayout frameLayout2 = new FrameLayout(parentActivity);
            frameLayout2.addView(checkBox2, LayoutHelper.createFrame(21, 21.0f, 17, 0.0f, 0.0f, 0.0f, 0.0f));
            LinearLayout linearLayout3 = new LinearLayout(parentActivity);
            linearLayout3.setOrientation(0);
            linearLayout3.setPadding(AndroidUtilities.m1146dp(8.0f), AndroidUtilities.m1146dp(6.0f), AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(6.0f));
            linearLayout3.addView(frameLayout2, LayoutHelper.createLinear(24, 24, 16, 0, 0, 6, 0));
            linearLayout3.addView(textView3, LayoutHelper.createLinear(-2, -2, 16));
            linearLayout3.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$9(checkBox2, view);
                }
            });
            ScaleStateListAnimator.apply(linearLayout3, 0.05f, 1.2f);
            linearLayout3.setBackground(Theme.createRadSelectorDrawable(getThemedColor(Theme.key_listSelector), 8, 8));
            linearLayout.addView(linearLayout3, LayoutHelper.createLinear(-2, -2, 1, 0, 0, 0, 8));
        }
        ImageView imageView2 = new ImageView(parentActivity);
        ScaleStateListAnimator.apply(imageView2, 0.15f, 1.5f);
        imageView2.setImageDrawable(ContextCompat.getDrawable(parentActivity, C2369R.drawable.msg_openin).mutate());
        imageView2.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_windowBackgroundWhiteGrayIcon), PorterDuff.Mode.MULTIPLY));
        imageView2.setScaleType(ImageView.ScaleType.CENTER);
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$10(pluginInstallParams, baseFragment, view);
            }
        });
        imageView2.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_dialogButtonSelector), 1, AndroidUtilities.m1146dp(20.0f)));
        frameLayout.addView(imageView2, LayoutHelper.createFrame(40, 40.0f, 53, 0.0f, 16.0f, 16.0f, 0.0f));
        ScrollView scrollView = new ScrollView(parentActivity);
        scrollView.addView(frameLayout);
        setCustomView(scrollView);
        if (pluginInstallParams.trusted) {
            z2 = false;
            if (ExteraConfig.preferences.getBoolean("trusted_source_hint", false)) {
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    InstallPluginBottomSheet.$r8$lambda$NCk_PnzBOhorfCXboXcLY60hXac(linearLayout2, pluginInstallParams);
                }
            }, 600L);
        }
        z2 = false;
        if (pluginInstallParams.trusted || ExteraConfig.preferences.getBoolean("unknown_source_hint", z2)) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                InstallPluginBottomSheet.$r8$lambda$NCk_PnzBOhorfCXboXcLY60hXac(linearLayout2, pluginInstallParams);
            }
        }, 600L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(PluginInstallParams pluginInstallParams, final View view) {
        HintView2 hintView2 = this.currentHint;
        if (hintView2 != null) {
            hintView2.hide();
            this.currentHint = null;
        }
        final HintView2 rounding = new HintView2(getContext(), 3).setMultilineText(true).setBgColor(getThemedColor(Theme.key_undo_background)).setTextColor(getThemedColor(Theme.key_undo_infoColor)).setText(AndroidUtilities.replaceTags(LocaleController.getString(pluginInstallParams.trusted ? C2369R.string.PluginSourceTrustedInfo : C2369R.string.PluginSourceUnknownInfo))).setTextAlign(Layout.Alignment.ALIGN_CENTER).allowBlur(true).setRounding(12.0f);
        rounding.setMaxWidthPx(HintView2.cutInFancyHalf(rounding.getText(), rounding.getTextPaint()));
        this.container.addView(rounding, LayoutHelper.createFrame(-1, 100.0f, 55, 32.0f, 0.0f, 32.0f, 0.0f));
        this.currentHint = rounding;
        this.container.post(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0(view, rounding);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view, HintView2 hintView2) {
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        int[] iArr2 = new int[2];
        this.container.getLocationInWindow(iArr2);
        iArr[0] = iArr[0] - iArr2[0];
        int i = iArr[1] - iArr2[1];
        iArr[1] = i;
        hintView2.setTranslationY((i - AndroidUtilities.m1146dp(100.0f)) - AndroidUtilities.m1146dp(6.0f));
        hintView2.setJointPx(0.0f, (-AndroidUtilities.m1146dp(32.0f)) + iArr[0] + (view.getMeasuredWidth() / 2.0f));
        hintView2.setDuration(5500L);
        hintView2.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$8(PluginInstallParams pluginInstallParams, final PluginsController.PluginValidationResult pluginValidationResult, final BaseFragment baseFragment, final boolean z, View view) {
        lambda$new$0();
        PythonPluginsEngine pythonPluginsEngine = (PythonPluginsEngine) PluginsController.engines.get(PluginsConstants.PYTHON);
        if (pythonPluginsEngine == null) {
            return;
        }
        pythonPluginsEngine.loadPluginFromFile(pluginInstallParams.filePath, pluginValidationResult.plugin, new Utilities.Callback() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$new$7(baseFragment, pluginValidationResult, z, (String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$7(final BaseFragment baseFragment, final PluginsController.PluginValidationResult pluginValidationResult, final boolean z, final String str) {
        if (str != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    BaseFragment baseFragment2 = baseFragment;
                    BulletinFactory.m1267of(baseFragment2).createSimpleBulletin(C2369R.raw.error, LocaleController.formatString(C2369R.string.PluginInstallError, pluginValidationResult.plugin.getName()), LocaleUtils.createCopySpan(baseFragment2), new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda12
                        @Override // java.lang.Runnable
                        public final void run() {
                            InstallPluginBottomSheet.m1931$r8$lambda$A7aFGsHMGMa4HVzPbK7QOFrcVE(str, baseFragment2);
                        }
                    }).show();
                }
            });
        } else if (this.enableAfterInstallation) {
            PluginsController.getInstance().setPluginEnabled(pluginValidationResult.plugin.getId(), true, new Utilities.Callback() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda14
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$new$5(baseFragment, pluginValidationResult, z, (String) obj);
                }
            });
        } else {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$6(baseFragment, pluginValidationResult, z);
                }
            });
        }
    }

    /* renamed from: $r8$lambda$A7aFGsHMGMa4HVzPbK7Q-OFrcVE, reason: not valid java name */
    public static /* synthetic */ void m1931$r8$lambda$A7aFGsHMGMa4HVzPbK7QOFrcVE(String str, BaseFragment baseFragment) {
        if (AndroidUtilities.addToClipboard(str)) {
            BulletinFactory.m1267of(baseFragment).createCopyBulletin(LocaleController.getString(C2369R.string.TextCopied)).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(final BaseFragment baseFragment, PluginsController.PluginValidationResult pluginValidationResult, boolean z, final String str) {
        if (str == null) {
            showSuccessBulletin(baseFragment, pluginValidationResult.plugin, z);
        } else {
            BulletinFactory.m1267of(baseFragment).createSimpleBulletin(C2369R.raw.error, LocaleController.formatString(C2369R.string.PluginInstalledButFailedToEnable, pluginValidationResult.plugin.getName()), LocaleUtils.createCopySpan(baseFragment), new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    InstallPluginBottomSheet.m1934$r8$lambda$lvJDf0WVltZZt4leIihxTBEd94(str, baseFragment);
                }
            }).show();
        }
    }

    /* renamed from: $r8$lambda$lvJDf0W-VltZZt4leIihxTBEd94, reason: not valid java name */
    public static /* synthetic */ void m1934$r8$lambda$lvJDf0WVltZZt4leIihxTBEd94(String str, BaseFragment baseFragment) {
        if (AndroidUtilities.addToClipboard(str)) {
            BulletinFactory.m1267of(baseFragment).createCopyBulletin(LocaleController.getString(C2369R.string.TextCopied)).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(BaseFragment baseFragment, PluginsController.PluginValidationResult pluginValidationResult, boolean z) {
        showSuccessBulletin(baseFragment, pluginValidationResult.plugin, z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$9(CheckBox2 checkBox2, View view) {
        checkBox2.setChecked(!checkBox2.isChecked(), true);
        this.enableAfterInstallation = checkBox2.isChecked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$10(PluginInstallParams pluginInstallParams, BaseFragment baseFragment, View view) {
        lambda$new$0();
        File file = new File(pluginInstallParams.filePath);
        if (file.exists()) {
            AndroidUtilities.openForView(file, file.getName(), "text/plain", baseFragment.getParentActivity(), baseFragment.getResourceProvider(), false);
        }
    }

    public static /* synthetic */ void $r8$lambda$NCk_PnzBOhorfCXboXcLY60hXac(LinearLayout linearLayout, PluginInstallParams pluginInstallParams) {
        linearLayout.performClick();
        ExteraConfig.editor.putBoolean(pluginInstallParams.trusted ? "trusted_source_hint" : "unknown_source_hint", true).apply();
    }

    private void showSuccessBulletin(BaseFragment baseFragment, final Plugin plugin, final boolean z) {
        final BulletinFactory bulletinFactoryM1267of = BulletinFactory.m1267of(baseFragment);
        final String name = plugin.getName();
        if (plugin.getPack() == null || plugin.getIndex() < 0) {
            bulletinFactoryM1267of.createSimpleBulletin(C2369R.raw.contact_check, LocaleController.formatString(z ? C2369R.string.PluginUpdated : C2369R.string.PluginInstalled, name)).show();
            return;
        }
        TLRPC.TL_inputStickerSetShortName tL_inputStickerSetShortName = new TLRPC.TL_inputStickerSetShortName();
        tL_inputStickerSetShortName.short_name = plugin.getPack();
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final Runnable runnable = new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                InstallPluginBottomSheet.$r8$lambda$kcbDSEF050atqSMHze63IYrPAl8(atomicBoolean, bulletinFactoryM1267of, z, name);
            }
        };
        AndroidUtilities.runOnUIThread(runnable, 300L);
        MediaDataController.getInstance(UserConfig.selectedAccount).getStickerSet(tL_inputStickerSetShortName, 0, true, new Utilities.Callback() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda5
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        InstallPluginBottomSheet.$r8$lambda$gFusRrJW5ywX3P7VLfUG86lgGyk(atomicBoolean, tL_messages_stickerSet, plugin, runnable, bulletinFactory, z, str);
                    }
                });
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$kcbDSEF050atqSMHze63IYrPAl8(AtomicBoolean atomicBoolean, BulletinFactory bulletinFactory, boolean z, String str) {
        if (atomicBoolean.getAndSet(true)) {
            return;
        }
        bulletinFactory.createSimpleBulletin(C2369R.raw.contact_check, LocaleController.formatString(z ? C2369R.string.PluginUpdated : C2369R.string.PluginInstalled, str)).show();
    }

    public static /* synthetic */ void $r8$lambda$gFusRrJW5ywX3P7VLfUG86lgGyk(AtomicBoolean atomicBoolean, TLRPC.TL_messages_stickerSet tL_messages_stickerSet, Plugin plugin, Runnable runnable, BulletinFactory bulletinFactory, boolean z, String str) {
        ArrayList arrayList;
        int index;
        if (atomicBoolean.get()) {
            return;
        }
        TLRPC.Document document = (tL_messages_stickerSet == null || (arrayList = tL_messages_stickerSet.documents) == null || arrayList.isEmpty() || (index = plugin.getIndex()) < 0 || index >= tL_messages_stickerSet.documents.size()) ? null : (TLRPC.Document) tL_messages_stickerSet.documents.get(index);
        if (document == null || atomicBoolean.getAndSet(true)) {
            return;
        }
        AndroidUtilities.cancelRunOnUIThread(runnable);
        bulletinFactory.createSimpleBulletin(document, AndroidUtilities.replaceTags(LocaleController.formatString(z ? C2369R.string.PluginUpdated : C2369R.string.PluginInstalled, str))).show();
    }

    @Override // org.telegram.p023ui.ActionBar.BottomSheet, android.app.Dialog, android.content.DialogInterface, org.telegram.ui.ActionBar.BaseFragment.AttachedSheet
    /* renamed from: dismiss */
    public void lambda$new$0() {
        HintView2 hintView2 = this.currentHint;
        if (hintView2 != null) {
            hintView2.hide();
            this.currentHint = null;
        }
        super.lambda$new$0();
    }

    @Override // org.telegram.p023ui.ActionBar.BottomSheet
    protected void onSwipeStarts() {
        HintView2 hintView2 = this.currentHint;
        if (hintView2 != null) {
            hintView2.hide();
            this.currentHint = null;
        }
    }

    /* loaded from: classes.dex */
    public static class PluginInstallParams {
        public String filePath;
        public boolean trusted;

        public PluginInstallParams(String str, boolean z) {
            this.filePath = str;
            this.trusted = z;
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x004f  */
        /* renamed from: of */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public static com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet.PluginInstallParams m219of(org.telegram.messenger.MessageObject r6) {
            /*
                com.exteragram.messenger.utils.ChatUtils r0 = com.exteragram.messenger.utils.ChatUtils.getInstance()
                java.lang.String r0 = r0.getPathToMessage(r6)
                boolean r1 = r6.isForwarded()
                r2 = 1
                r3 = 0
                if (r1 == 0) goto L2f
                java.lang.Long r6 = r6.getForwardedFromId()
                if (r6 == 0) goto L51
                com.exteragram.messenger.badges.BadgesController r1 = com.exteragram.messenger.badges.BadgesController.INSTANCE
                long r4 = r6.longValue()
                long r4 = -r4
                boolean r4 = r1.isTrusted(r4)
                if (r4 != 0) goto L50
                long r4 = r6.longValue()
                long r4 = -r4
                boolean r6 = r1.isExtera(r4)
                if (r6 == 0) goto L4f
                goto L50
            L2f:
                boolean r1 = r6.isFromChannel()
                if (r1 == 0) goto L51
                boolean r1 = r6.isFromChat()
                if (r1 != 0) goto L51
                long r4 = r6.getDialogId()
                long r4 = -r4
                com.exteragram.messenger.badges.BadgesController r6 = com.exteragram.messenger.badges.BadgesController.INSTANCE
                boolean r1 = r6.isTrusted(r4)
                if (r1 != 0) goto L50
                boolean r6 = r6.isExtera(r4)
                if (r6 == 0) goto L4f
                goto L50
            L4f:
                r2 = 0
            L50:
                r3 = r2
            L51:
                com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$PluginInstallParams r6 = new com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$PluginInstallParams
                r6.<init>(r0, r3)
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet.PluginInstallParams.m219of(org.telegram.messenger.MessageObject):com.exteragram.messenger.plugins.ui.components.InstallPluginBottomSheet$PluginInstallParams");
        }
    }
}
