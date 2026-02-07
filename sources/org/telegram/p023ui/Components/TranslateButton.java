package org.telegram.p023ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.TranslateController;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.p023ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.RestrictedLanguagesSelectActivity;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes6.dex */
public abstract class TranslateButton extends FrameLayout {
    private boolean[] accusative;
    private final int currentAccount;
    private final long dialogId;
    private final BaseFragment fragment;
    private ImageView menuView;
    private Theme.ResourcesProvider resourcesProvider;
    private AnimatedTextView textView;
    private final Drawable translateDrawable;
    public final SpannableString translateIcon;

    protected abstract void onButtonClick();

    protected abstract void onCloseClick();

    public TranslateButton(Context context, ChatActivity chatActivity, Theme.ResourcesProvider resourcesProvider) {
        this(context, chatActivity.getCurrentAccount(), chatActivity.getDialogId(), chatActivity, resourcesProvider);
    }

    public TranslateButton(Context context, final int i, final long j, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.accusative = new boolean[1];
        this.currentAccount = i;
        this.dialogId = j;
        this.fragment = baseFragment;
        this.resourcesProvider = resourcesProvider;
        AnimatedTextView animatedTextView = new AnimatedTextView(context, true, true, false);
        this.textView = animatedTextView;
        animatedTextView.setAnimationProperties(0.3f, 0L, 450L, CubicBezierInterpolator.EASE_OUT_QUINT);
        this.textView.setTextSize(AndroidUtilities.m1146dp(15.0f));
        this.textView.setTypeface(AndroidUtilities.bold());
        this.textView.setPadding(AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f), 0);
        this.textView.setGravity(1);
        this.textView.setIgnoreRTL(!LocaleController.isRTL);
        AnimatedTextView animatedTextView2 = this.textView;
        animatedTextView2.adaptWidth = false;
        animatedTextView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.TranslateButton$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        });
        addView(this.textView, LayoutHelper.createFrame(-1, -1.0f));
        Drawable drawableMutate = getContext().getResources().getDrawable(C2369R.drawable.msg_translate).mutate();
        this.translateDrawable = drawableMutate;
        drawableMutate.setBounds(0, AndroidUtilities.m1146dp(-8.0f), AndroidUtilities.m1146dp(20.0f), AndroidUtilities.m1146dp(12.0f));
        SpannableString spannableString = new SpannableString("x");
        this.translateIcon = spannableString;
        spannableString.setSpan(new ImageSpan(drawableMutate, 0), 0, 1, 33);
        ImageView imageView = new ImageView(context);
        this.menuView = imageView;
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        this.menuView.setImageResource(C2369R.drawable.msg_mini_customize);
        this.menuView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.TranslateButton$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$1(i, j, view);
            }
        });
        addView(this.menuView, LayoutHelper.createFrame(32, 32.0f, 21, 0.0f, 0.0f, 8.0f, 0.0f));
        updateColors();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        onButtonClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(int i, long j, View view) {
        TLRPC.Chat chat = MessagesController.getInstance(i).getChat(Long.valueOf(-j));
        if (UserConfig.getInstance(i).isPremium() || (chat != null && chat.autotranslation)) {
            onMenuClick();
        } else {
            onCloseClick();
        }
    }

    public void updateColors() {
        AnimatedTextView animatedTextView = this.textView;
        int i = Theme.key_chat_addContact;
        animatedTextView.setTextColor(Theme.getColor(i, this.resourcesProvider));
        this.textView.setBackground(Theme.createSelectorDrawable(Theme.getColor(i, this.resourcesProvider) & 436207615, 3));
        this.menuView.setBackground(Theme.createSelectorDrawable(Theme.getColor(i, this.resourcesProvider) & 436207615, 7));
        ImageView imageView = this.menuView;
        int color = Theme.getColor(i, this.resourcesProvider);
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        imageView.setColorFilter(new PorterDuffColorFilter(color, mode));
        this.translateDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i, this.resourcesProvider), mode));
    }

    public void setLeftMargin(float f) {
        this.textView.setTranslationX(f / 2.0f);
    }

    protected void onMenuClick() {
        final String str;
        String string;
        String strCapitalFirst;
        final TranslateController translateController = MessagesController.getInstance(this.currentAccount).getTranslateController();
        final ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(getContext(), C2369R.drawable.popup_fixed_alert2, this.resourcesProvider, 1);
        final ActionBarPopupWindow actionBarPopupWindow = new ActionBarPopupWindow(actionBarPopupWindowLayout, -2, -2);
        actionBarPopupWindowLayout.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuBackground, this.resourcesProvider));
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        ScrollView scrollView = new ScrollView(getContext()) { // from class: org.telegram.ui.Components.TranslateButton.1
            AnimatedFloat alphaFloat = new AnimatedFloat(this, 350, CubicBezierInterpolator.EASE_OUT_QUINT);
            Drawable topShadowDrawable;
            private boolean wasCanScrollVertically;

            @Override // android.widget.ScrollView, android.view.ViewGroup, android.view.ViewParent
            public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
                super.onNestedScroll(view, i, i2, i3, i4);
                boolean zCanScrollVertically = canScrollVertically(-1);
                if (this.wasCanScrollVertically != zCanScrollVertically) {
                    invalidate();
                    this.wasCanScrollVertically = zCanScrollVertically;
                }
            }

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                float f = this.alphaFloat.set(canScrollVertically(-1) ? 1.0f : 0.0f) * 0.5f;
                if (f > 0.0f) {
                    if (this.topShadowDrawable == null) {
                        this.topShadowDrawable = getContext().getResources().getDrawable(C2369R.drawable.header_shadow);
                    }
                    this.topShadowDrawable.setBounds(0, getScrollY(), getWidth(), getScrollY() + this.topShadowDrawable.getIntrinsicHeight());
                    this.topShadowDrawable.setAlpha((int) (f * 255.0f));
                    this.topShadowDrawable.draw(canvas);
                }
            }
        };
        LinearLayout linearLayout2 = new LinearLayout(getContext());
        scrollView.addView(linearLayout2);
        linearLayout2.setOrientation(1);
        actionBarPopupWindowLayout.swipeBackGravityRight = true;
        final int iAddViewToSwipeBack = actionBarPopupWindowLayout.addViewToSwipeBack(linearLayout);
        ActionBarMenuSubItem actionBarMenuSubItem = new ActionBarMenuSubItem(getContext(), true, false, this.resourcesProvider);
        actionBarMenuSubItem.setTextAndIcon(LocaleController.getString(C2369R.string.TranslateTo), C2369R.drawable.msg_translate);
        actionBarMenuSubItem.setSubtext(TranslateAlert2.capitalFirst(TranslateAlert2.languageName(translateController.getDialogTranslateTo(this.dialogId))));
        actionBarMenuSubItem.setItemHeight(56);
        actionBarMenuSubItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.TranslateButton$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                actionBarPopupWindowLayout.getSwipeBack().openForeground(iAddViewToSwipeBack);
            }
        });
        actionBarPopupWindowLayout.addView(actionBarMenuSubItem);
        ActionBarMenuSubItem actionBarMenuSubItem2 = new ActionBarMenuSubItem(getContext(), true, false, this.resourcesProvider);
        actionBarMenuSubItem2.setTextAndIcon(LocaleController.getString(C2369R.string.Back), C2369R.drawable.ic_ab_back);
        actionBarMenuSubItem2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.TranslateButton$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                actionBarPopupWindowLayout.getSwipeBack().closeForeground();
            }
        });
        linearLayout.addView(actionBarMenuSubItem2);
        linearLayout.addView(scrollView, LayoutHelper.createLinear(-1, 420));
        final String dialogDetectedLanguage = translateController.getDialogDetectedLanguage(this.dialogId);
        TranslateAlert2.languageName(dialogDetectedLanguage);
        String strLanguageName = TranslateAlert2.languageName(dialogDetectedLanguage, this.accusative);
        String dialogTranslateTo = translateController.getDialogTranslateTo(this.dialogId);
        ArrayList<TranslateController.Language> suggestedLanguages = TranslateController.getSuggestedLanguages(dialogTranslateTo);
        ArrayList<TranslateController.Language> languages = TranslateController.getLanguages();
        linearLayout2.addView(new ActionBarPopupWindow.GapView(getContext(), this.resourcesProvider), LayoutHelper.createLinear(-1, 8));
        if (dialogTranslateTo != null && (strCapitalFirst = TranslateAlert2.capitalFirst(TranslateAlert2.languageName(dialogTranslateTo))) != null) {
            ActionBarMenuSubItem actionBarMenuSubItem3 = new ActionBarMenuSubItem(getContext(), 2, false, false, this.resourcesProvider);
            actionBarMenuSubItem3.setChecked(true);
            actionBarMenuSubItem3.setText(strCapitalFirst);
            linearLayout2.addView(actionBarMenuSubItem3);
        }
        int size = suggestedLanguages.size();
        int i = 0;
        while (i < size) {
            TranslateController.Language language = suggestedLanguages.get(i);
            i++;
            TranslateController.Language language2 = language;
            final String str2 = language2.code;
            if (!TextUtils.equals(str2, dialogDetectedLanguage)) {
                ActionBarMenuSubItem actionBarMenuSubItem4 = new ActionBarMenuSubItem(getContext(), 2, false, false, this.resourcesProvider);
                boolean z = dialogTranslateTo != null && dialogTranslateTo.equals(str2);
                actionBarMenuSubItem4.setChecked(z);
                actionBarMenuSubItem4.setText(language2.displayName);
                if (!z) {
                    actionBarMenuSubItem4.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.TranslateButton$$ExternalSyntheticLambda4
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$onMenuClick$4(translateController, str2, actionBarPopupWindow, view);
                        }
                    });
                }
                linearLayout2.addView(actionBarMenuSubItem4);
            }
        }
        linearLayout2.addView(new ActionBarPopupWindow.GapView(getContext(), this.resourcesProvider), LayoutHelper.createLinear(-1, 8));
        int size2 = languages.size();
        int i2 = 0;
        while (i2 < size2) {
            TranslateController.Language language3 = languages.get(i2);
            i2++;
            TranslateController.Language language4 = language3;
            final String str3 = language4.code;
            if (!TextUtils.equals(str3, dialogDetectedLanguage)) {
                boolean z2 = dialogTranslateTo != null && dialogTranslateTo.equals(str3);
                ActionBarMenuSubItem actionBarMenuSubItem5 = new ActionBarMenuSubItem(getContext(), 2, false, false, this.resourcesProvider);
                actionBarMenuSubItem5.setChecked(z2);
                actionBarMenuSubItem5.setText(language4.displayName);
                if (!z2) {
                    actionBarMenuSubItem5.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.TranslateButton$$ExternalSyntheticLambda5
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$onMenuClick$5(translateController, str3, actionBarPopupWindow, view);
                        }
                    });
                }
                linearLayout2.addView(actionBarMenuSubItem5);
            }
        }
        actionBarPopupWindowLayout.addView(new ActionBarPopupWindow.GapView(getContext(), this.resourcesProvider), LayoutHelper.createLinear(-1, 8));
        if (!UserConfig.getInstance(this.currentAccount).isPremium() || strLanguageName == null) {
            str = strLanguageName;
        } else {
            ActionBarMenuSubItem actionBarMenuSubItem6 = new ActionBarMenuSubItem(getContext(), false, false, this.resourcesProvider);
            if (this.accusative[0]) {
                string = LocaleController.formatString(C2369R.string.DoNotTranslateLanguage, strLanguageName);
            } else {
                string = LocaleController.formatString(C2369R.string.DoNotTranslateLanguageOther, strLanguageName);
            }
            actionBarMenuSubItem6.setTextAndIcon(string, C2369R.drawable.msg_block2);
            str = strLanguageName;
            actionBarMenuSubItem6.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.TranslateButton$$ExternalSyntheticLambda6
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onMenuClick$7(dialogDetectedLanguage, translateController, str, actionBarPopupWindow, view);
                }
            });
            actionBarPopupWindowLayout.addView(actionBarMenuSubItem6);
        }
        ActionBarMenuSubItem actionBarMenuSubItem7 = new ActionBarMenuSubItem(getContext(), str == null, true, this.resourcesProvider);
        actionBarMenuSubItem7.setTextAndIcon(LocaleController.getString(C2369R.string.Hide), C2369R.drawable.msg_cancel);
        actionBarMenuSubItem7.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.TranslateButton$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onMenuClick$9(translateController, actionBarPopupWindow, view);
            }
        });
        actionBarPopupWindowLayout.addView(actionBarMenuSubItem7);
        actionBarPopupWindow.setPauseNotifications(true);
        actionBarPopupWindow.setDismissAnimationDuration(220);
        actionBarPopupWindow.setOutsideTouchable(true);
        actionBarPopupWindow.setClippingEnabled(true);
        actionBarPopupWindow.setAnimationStyle(C2369R.style.PopupContextAnimation);
        actionBarPopupWindow.setFocusable(true);
        actionBarPopupWindow.setInputMethodMode(2);
        actionBarPopupWindow.setSoftInputMode(0);
        ImageView imageView = this.menuView;
        actionBarPopupWindow.showAsDropDown(imageView, 0, (-imageView.getMeasuredHeight()) - AndroidUtilities.m1146dp(8.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMenuClick$4(TranslateController translateController, String str, ActionBarPopupWindow actionBarPopupWindow, View view) {
        translateController.setDialogTranslateTo(this.dialogId, str);
        actionBarPopupWindow.dismiss();
        updateText();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMenuClick$5(TranslateController translateController, String str, ActionBarPopupWindow actionBarPopupWindow, View view) {
        translateController.setDialogTranslateTo(this.dialogId, str);
        actionBarPopupWindow.dismiss();
        updateText();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMenuClick$7(String str, TranslateController translateController, String str2, ActionBarPopupWindow actionBarPopupWindow, View view) {
        String string;
        RestrictedLanguagesSelectActivity.toggleLanguage(str, true);
        translateController.checkRestrictedLanguagesUpdate();
        translateController.setHideTranslateDialog(this.dialogId, true);
        if (this.accusative[0]) {
            string = LocaleController.formatString("AddedToDoNotTranslate", C2369R.string.AddedToDoNotTranslate, str2);
        } else {
            string = LocaleController.formatString("AddedToDoNotTranslateOther", C2369R.string.AddedToDoNotTranslateOther, str2);
        }
        BulletinFactory.m1267of(this.fragment).createSimpleBulletin(C2369R.raw.msg_translate, TranslateAlert2.capitalFirst(AndroidUtilities.replaceTags(string)), LocaleController.getString(C2369R.string.Settings), new Runnable() { // from class: org.telegram.ui.Components.TranslateButton$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onMenuClick$6();
            }
        }).show();
        actionBarPopupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMenuClick$6() {
        this.fragment.presentFragment(new RestrictedLanguagesSelectActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMenuClick$9(final TranslateController translateController, ActionBarPopupWindow actionBarPopupWindow, View view) {
        String string;
        translateController.setHideTranslateDialog(this.dialogId, true);
        TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-this.dialogId));
        if (chat != null && ChatObject.isChannelAndNotMegaGroup(chat)) {
            string = LocaleController.getString(C2369R.string.TranslationBarHiddenForChannel);
        } else if (chat != null) {
            string = LocaleController.getString(C2369R.string.TranslationBarHiddenForGroup);
        } else {
            string = LocaleController.getString(C2369R.string.TranslationBarHiddenForChat);
        }
        BulletinFactory.m1267of(this.fragment).createSimpleBulletin(C2369R.raw.msg_translate, AndroidUtilities.replaceTags(string), LocaleController.getString(C2369R.string.Undo), new Runnable() { // from class: org.telegram.ui.Components.TranslateButton$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onMenuClick$8(translateController);
            }
        }).show();
        actionBarPopupWindow.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onMenuClick$8(TranslateController translateController) {
        translateController.setHideTranslateDialog(this.dialogId, false);
    }

    public void updateText() {
        String string;
        TranslateController translateController = MessagesController.getInstance(this.currentAccount).getTranslateController();
        TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-this.dialogId));
        if (translateController.isTranslatingDialog(this.dialogId)) {
            String strLanguageName = TranslateAlert2.languageName(translateController.getDialogDetectedLanguage(this.dialogId));
            if (!TextUtils.isEmpty(strLanguageName)) {
                this.textView.setText(TextUtils.concat(this.translateIcon, " ", LocaleController.formatString(C2369R.string.ShowOriginalButtonLanguage, strLanguageName)));
            } else {
                this.textView.setText(TextUtils.concat(this.translateIcon, " ", LocaleController.getString(C2369R.string.ShowOriginalButton)));
            }
        } else {
            String dialogTranslateTo = translateController.getDialogTranslateTo(this.dialogId);
            if (dialogTranslateTo == null) {
                dialogTranslateTo = "en";
            }
            String strLanguageName2 = TranslateAlert2.languageName(dialogTranslateTo, this.accusative);
            if (this.accusative[0]) {
                string = LocaleController.formatString(C2369R.string.TranslateToButton, strLanguageName2);
            } else {
                string = LocaleController.formatString(C2369R.string.TranslateToButtonOther, strLanguageName2);
            }
            this.textView.setText(TextUtils.concat(this.translateIcon, " ", string));
        }
        this.menuView.setImageResource((UserConfig.getInstance(this.currentAccount).isPremium() || (chat != null && chat.autotranslation)) ? C2369R.drawable.msg_mini_customize : C2369R.drawable.msg_close);
    }
}
