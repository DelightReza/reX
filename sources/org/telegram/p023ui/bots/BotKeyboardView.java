package org.telegram.p023ui.bots;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import me.vkryl.android.animator.ListAnimator;
import me.vkryl.android.animator.ReplaceAnimator;
import me.vkryl.core.lambda.Destroyable;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.Emoji;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.p023ui.Components.inset.InAppKeyboardInsetView;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes6.dex */
public abstract class BotKeyboardView extends LinearLayout implements InAppKeyboardInsetView, ReplaceAnimator.Callback {
    private final ReplaceAnimator animator;
    private TLRPC.TL_replyKeyboardMarkup botButtons;
    private int buttonHeight;
    private final ArrayList buttonIcons;
    private final ArrayList buttonViews;
    private BotKeyboardViewDelegate delegate;
    private final FrameLayout frameLayout;
    private boolean isFullSize;
    private int panelHeight;
    private final Theme.ResourcesProvider resourcesProvider;
    private final ScrollView scrollView;

    public interface BotKeyboardViewDelegate {
        void didPressedButton(TLRPC.KeyboardButton keyboardButton);
    }

    @Override // org.telegram.p023ui.Components.inset.InAppKeyboardInsetView
    public void applyInAppKeyboardAnimatedHeight(float f) {
    }

    @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
    public /* synthetic */ boolean hasChanges(ReplaceAnimator replaceAnimator) {
        return ReplaceAnimator.Callback.CC.$default$hasChanges(this, replaceAnimator);
    }

    @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
    public /* synthetic */ boolean onApplyMetadataAnimation(ReplaceAnimator replaceAnimator, float f) {
        return ReplaceAnimator.Callback.CC.$default$onApplyMetadataAnimation(this, replaceAnimator, f);
    }

    @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
    public /* synthetic */ void onFinishMetadataAnimation(ReplaceAnimator replaceAnimator, boolean z) {
        ReplaceAnimator.Callback.CC.$default$onFinishMetadataAnimation(this, replaceAnimator, z);
    }

    @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
    public /* synthetic */ void onForceApplyChanges(ReplaceAnimator replaceAnimator) {
        ReplaceAnimator.Callback.CC.$default$onForceApplyChanges(this, replaceAnimator);
    }

    @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
    public /* synthetic */ void onPrepareMetadataAnimation(ReplaceAnimator replaceAnimator) {
        ReplaceAnimator.Callback.CC.$default$onPrepareMetadataAnimation(this, replaceAnimator);
    }

    public BotKeyboardView(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.buttonViews = new ArrayList();
        this.buttonIcons = new ArrayList();
        this.animator = new ReplaceAnimator(this, CubicBezierInterpolator.EASE_OUT_QUINT, 320L);
        this.resourcesProvider = resourcesProvider;
        setOrientation(1);
        ScrollView scrollView = new ScrollView(context);
        this.scrollView = scrollView;
        scrollView.setClipToPadding(false);
        addView(scrollView);
        FrameLayout frameLayout = new FrameLayout(context);
        this.frameLayout = frameLayout;
        scrollView.addView(frameLayout);
        updateColors();
    }

    public void updateColors() {
        AndroidUtilities.setScrollViewEdgeEffectColor(this.scrollView, getThemedColor(Theme.key_chat_emojiPanelBackground));
        for (int i = 0; i < this.buttonViews.size(); i++) {
            TextView textView = (TextView) this.buttonViews.get(i);
            int i2 = Theme.key_chat_botKeyboardButtonText;
            textView.setTextColor(getThemedColor(i2));
            ((TextView) this.buttonViews.get(i)).setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(4.0f), getThemedColor(Theme.key_chat_botKeyboardButtonBackground), getThemedColor(Theme.key_chat_botKeyboardButtonBackgroundPressed)));
            ((ImageView) this.buttonIcons.get(i)).setColorFilter(getThemedColor(i2));
        }
        invalidate();
    }

    public void setDelegate(BotKeyboardViewDelegate botKeyboardViewDelegate) {
        this.delegate = botKeyboardViewDelegate;
    }

    public void setPanelHeight(int i) {
        TLRPC.TL_replyKeyboardMarkup tL_replyKeyboardMarkup;
        this.panelHeight = i;
        if (!this.isFullSize || (tL_replyKeyboardMarkup = this.botButtons) == null || tL_replyKeyboardMarkup.rows.isEmpty()) {
            return;
        }
        int iMax = !this.isFullSize ? 42 : (int) Math.max(42.0f, (((this.panelHeight - AndroidUtilities.m1146dp(20.0f)) - ((this.botButtons.rows.size() - 1) * AndroidUtilities.m1146dp(4.0f))) / this.botButtons.rows.size()) / AndroidUtilities.density);
        this.buttonHeight = iMax;
        int iM1146dp = AndroidUtilities.m1146dp(iMax);
        Iterator it = this.animator.iterator();
        while (it.hasNext()) {
            ListAnimator.Entry entry = (ListAnimator.Entry) it.next();
            int childCount = ((ButtonsLayout) entry.item).getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = ((ButtonsLayout) entry.item).getChildAt(i2);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                if (layoutParams.height != iM1146dp) {
                    layoutParams.height = iM1146dp;
                    childAt.setLayoutParams(layoutParams);
                }
            }
        }
    }

    public void invalidateViews() {
        for (int i = 0; i < this.buttonViews.size(); i++) {
            ((TextView) this.buttonViews.get(i)).invalidate();
            ((ImageView) this.buttonIcons.get(i)).invalidate();
        }
    }

    public boolean isFullSize() {
        return this.isFullSize;
    }

    public void setButtons(TLRPC.TL_replyKeyboardMarkup tL_replyKeyboardMarkup) {
        this.botButtons = tL_replyKeyboardMarkup;
        this.buttonViews.clear();
        this.buttonIcons.clear();
        float scrollY = this.scrollView.getScrollY();
        Iterator it = this.animator.iterator();
        while (it.hasNext()) {
            Object obj = ((ListAnimator.Entry) it.next()).item;
            ((ButtonsLayout) obj).setTranslationY(((ButtonsLayout) obj).getTranslationY() - scrollY);
        }
        this.scrollView.scrollTo(0, 0);
        if (tL_replyKeyboardMarkup != null && !this.botButtons.rows.isEmpty()) {
            ButtonsLayout buttonsLayout = new ButtonsLayout(getContext());
            buttonsLayout.setOrientation(1);
            buttonsLayout.setAlpha(0.0f);
            this.frameLayout.addView(buttonsLayout);
            boolean z = tL_replyKeyboardMarkup.resize;
            this.isFullSize = !z;
            this.buttonHeight = z ? 42 : (int) Math.max(42.0f, (((this.panelHeight - AndroidUtilities.m1146dp(20.0f)) - ((this.botButtons.rows.size() - 1) * AndroidUtilities.m1146dp(4.0f))) / this.botButtons.rows.size()) / AndroidUtilities.density);
            int i = 0;
            while (i < tL_replyKeyboardMarkup.rows.size()) {
                TLRPC.TL_keyboardButtonRow tL_keyboardButtonRow = (TLRPC.TL_keyboardButtonRow) tL_replyKeyboardMarkup.rows.get(i);
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(0);
                buttonsLayout.addView(linearLayout, LayoutHelper.createLinear(-1, this.buttonHeight, 10.0f, i == 0 ? 10.0f : 4.0f, 10.0f, i == tL_replyKeyboardMarkup.rows.size() - 1 ? 10.0f : 0.0f));
                float size = 1.0f / tL_keyboardButtonRow.buttons.size();
                int i2 = 0;
                while (i2 < tL_keyboardButtonRow.buttons.size()) {
                    TLRPC.KeyboardButton keyboardButton = (TLRPC.KeyboardButton) tL_keyboardButtonRow.buttons.get(i2);
                    Button button = new Button(getContext(), keyboardButton);
                    button.setBackground(i2 == 0, i == 0, i2 == tL_keyboardButtonRow.buttons.size() - 1, i == tL_replyKeyboardMarkup.rows.size() - 1);
                    FrameLayout frameLayout = new FrameLayout(getContext());
                    frameLayout.addView(button, LayoutHelper.createFrame(-1, -1.0f));
                    linearLayout.addView(frameLayout, LayoutHelper.createLinear(0, -1, size, 0, 0, i2 != tL_keyboardButtonRow.buttons.size() - 1 ? 4 : 0, 0));
                    button.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.bots.BotKeyboardView$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            this.f$0.lambda$setButtons$0(view);
                        }
                    });
                    ScaleStateListAnimator.apply(button, 0.02f, 1.5f);
                    this.buttonViews.add(button);
                    ImageView imageView = new ImageView(getContext());
                    imageView.setColorFilter(getThemedColor(Theme.key_chat_botKeyboardButtonText));
                    if ((keyboardButton instanceof TLRPC.TL_keyboardButtonWebView) || (keyboardButton instanceof TLRPC.TL_keyboardButtonSimpleWebView)) {
                        imageView.setImageResource(C2369R.drawable.bot_webview);
                        imageView.setVisibility(0);
                    } else {
                        imageView.setVisibility(8);
                    }
                    this.buttonIcons.add(imageView);
                    frameLayout.addView(imageView, LayoutHelper.createFrame(12, 12.0f, 53, 0.0f, 8.0f, 8.0f, 0.0f));
                    i2++;
                }
                i++;
            }
            this.animator.replace(buttonsLayout, true);
            return;
        }
        this.animator.clear(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setButtons$0(View view) {
        this.delegate.didPressedButton((TLRPC.KeyboardButton) view.getTag());
    }

    private class Button extends TextView {
        public Button(Context context, TLRPC.KeyboardButton keyboardButton) {
            super(context);
            setTag(keyboardButton);
            setTextColor(BotKeyboardView.this.getThemedColor(Theme.key_chat_botKeyboardButtonText));
            setTextSize(1, 16.0f);
            setGravity(17);
            setPadding(AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f), 0);
            setText(Emoji.replaceEmoji(keyboardButton.text, getPaint().getFontMetricsInt(), false));
        }

        public void setBackground(boolean z, boolean z2, boolean z3, boolean z4) {
            int iM1146dp = AndroidUtilities.m1146dp(10.0f);
            int iM1146dp2 = AndroidUtilities.m1146dp(4.0f);
            int i = (z && z2) ? iM1146dp : iM1146dp2;
            int i2 = (z3 && z2) ? iM1146dp : iM1146dp2;
            int i3 = (z3 && z4) ? iM1146dp : iM1146dp2;
            int i4 = (z && z4) ? iM1146dp : iM1146dp2;
            int themedColor = BotKeyboardView.this.getThemedColor(Theme.key_chat_botKeyboardButtonBackground);
            BotKeyboardView botKeyboardView = BotKeyboardView.this;
            int i5 = Theme.key_chat_botKeyboardButtonBackgroundPressed;
            setBackground(Theme.createSimpleSelectorRoundRectDrawable(i, i2, i3, i4, themedColor, botKeyboardView.getThemedColor(i5), BotKeyboardView.this.getThemedColor(i5)));
        }
    }

    public int getKeyboardHeight() {
        TLRPC.TL_replyKeyboardMarkup tL_replyKeyboardMarkup = this.botButtons;
        if (tL_replyKeyboardMarkup == null) {
            return 0;
        }
        return this.isFullSize ? this.panelHeight : (tL_replyKeyboardMarkup.rows.size() * AndroidUtilities.m1146dp(this.buttonHeight)) + AndroidUtilities.m1146dp(20.0f) + ((this.botButtons.rows.size() - 1) * AndroidUtilities.m1146dp(4.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    @Override // org.telegram.p023ui.Components.inset.InAppKeyboardInsetView
    public void applyNavigationBarHeight(int i) {
        if (this.scrollView.getPaddingBottom() != i) {
            this.scrollView.setPadding(0, 0, 0, i);
        }
    }

    @Override // me.vkryl.android.animator.ReplaceAnimator.Callback
    public void onItemChanged(ReplaceAnimator replaceAnimator) {
        Iterator it = this.animator.iterator();
        while (it.hasNext()) {
            ListAnimator.Entry entry = (ListAnimator.Entry) it.next();
            float visibility = entry.getVisibility();
            float fLerp = AndroidUtilities.lerp(0.7f, 1.0f, visibility);
            ((ButtonsLayout) entry.item).setAlpha(visibility);
            ((ButtonsLayout) entry.item).setScaleX(fLerp);
            ((ButtonsLayout) entry.item).setScaleY(fLerp);
        }
    }

    private static class ButtonsLayout extends LinearLayout implements Destroyable {
        public ButtonsLayout(Context context) {
            super(context);
        }

        @Override // me.vkryl.core.lambda.Destroyable
        public void performDestroy() {
            ((ViewGroup) getParent()).removeView(this);
        }
    }
}
