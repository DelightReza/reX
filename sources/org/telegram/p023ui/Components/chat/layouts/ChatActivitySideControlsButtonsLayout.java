package org.telegram.p023ui.Components.chat.layouts;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import me.vkryl.android.AnimatorUtils;
import me.vkryl.android.animator.BoolAnimator;
import me.vkryl.android.animator.FactorAnimator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.blur3.BlurredBackgroundDrawableViewFactory;
import org.telegram.p023ui.Components.blur3.drawable.color.BlurredBackgroundColorProvider;
import org.telegram.p023ui.Components.chat.buttons.ChatActivityBlurredRoundPageDownButton;

/* loaded from: classes3.dex */
public class ChatActivitySideControlsButtonsLayout extends FrameLayout implements FactorAnimator.Target {
    private static final int[] buttonIcons;
    private final BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableViewFactory;
    private final String[] buttonDescriptions;
    private final ButtonHolder[] buttonHolders;
    private final BlurredBackgroundColorProvider colorProvider;
    private ButtonOnClickListener onClickListener;
    private ButtonOnLongClickListener onLongClickListener;
    private final Theme.ResourcesProvider resourcesProvider;

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public /* synthetic */ void onFactorChangeFinished(int i, float f, FactorAnimator factorAnimator) {
        FactorAnimator.Target.CC.$default$onFactorChangeFinished(this, i, f, factorAnimator);
    }

    static {
        int i = C2369R.drawable.pagedown;
        buttonIcons = new int[]{i, C2369R.drawable.mentionbutton, C2369R.drawable.reactionbutton, i, i};
    }

    public ChatActivitySideControlsButtonsLayout(Context context, Theme.ResourcesProvider resourcesProvider, BlurredBackgroundColorProvider blurredBackgroundColorProvider, BlurredBackgroundDrawableViewFactory blurredBackgroundDrawableViewFactory) {
        super(context);
        this.buttonDescriptions = new String[]{LocaleController.getString(C2369R.string.AccDescrPageDown), LocaleController.getString(C2369R.string.AccDescrMentionDown), LocaleController.getString(C2369R.string.AccDescrReactionMentionDown), LocaleController.getString(C2369R.string.AccDescrSearchPrev), LocaleController.getString(C2369R.string.AccDescrSearchNext)};
        this.buttonHolders = new ButtonHolder[5];
        this.blurredBackgroundDrawableViewFactory = blurredBackgroundDrawableViewFactory;
        this.colorProvider = blurredBackgroundColorProvider;
        this.resourcesProvider = resourcesProvider;
    }

    public void setOnClickListener(ButtonOnClickListener buttonOnClickListener) {
        this.onClickListener = buttonOnClickListener;
    }

    public void setOnLongClickListener(ButtonOnLongClickListener buttonOnLongClickListener) {
        this.onLongClickListener = buttonOnLongClickListener;
    }

    public boolean getButtonLocationInWindow(int i, int[] iArr) {
        ButtonHolder buttonHolder = this.buttonHolders[i];
        if (buttonHolder == null) {
            return false;
        }
        buttonHolder.button.getLocationInWindow(iArr);
        return true;
    }

    public void updateColors() {
        for (ButtonHolder buttonHolder : this.buttonHolders) {
            if (buttonHolder != null) {
                buttonHolder.button.updateColors();
            }
        }
    }

    public void showButton(int i, boolean z, boolean z2) {
        if (this.buttonHolders[i] != null || z) {
            getOrCreateButtonHolder(i).visibilityAnimator.setValue(z, z2);
        }
    }

    public void setButtonCount(int i, int i2, boolean z) {
        getOrCreateButtonHolder(i).button.setCount(i2, z);
    }

    public void setButtonLoading(int i, boolean z, boolean z2) {
        getOrCreateButtonHolder(i).button.showLoading(z, z2);
    }

    public boolean isButtonVisible(int i) {
        ButtonHolder buttonHolder = getButtonHolder(i);
        return buttonHolder != null && buttonHolder.visibilityAnimator.getValue();
    }

    public void setButtonEnabled(int i, boolean z, boolean z2) {
        getOrCreateButtonHolder(i).button.setEnabled(z, z2);
    }

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public void onFactorChanged(int i, float f, float f2, FactorAnimator factorAnimator) {
        int i2 = i >> 16;
        int i3 = i & 65535;
        if (i2 >= 0) {
            ButtonHolder[] buttonHolderArr = this.buttonHolders;
            if (i2 >= buttonHolderArr.length || buttonHolderArr[i2] == null || i3 != 1) {
                return;
            }
            checkButtonsPositionsAndVisibility();
        }
    }

    private void checkButtonsPositionsAndVisibility() {
        int i = 0;
        float fM1146dp = 0.0f;
        while (true) {
            ButtonHolder[] buttonHolderArr = this.buttonHolders;
            if (i >= buttonHolderArr.length) {
                return;
            }
            ButtonHolder buttonHolder = buttonHolderArr[i];
            if (buttonHolder != null) {
                float floatValue = buttonHolder.visibilityAnimator.getFloatValue();
                buttonHolder.button.setVisibility(floatValue > 0.0f ? 0 : 8);
                buttonHolder.button.setAlpha(floatValue);
                buttonHolder.button.setScaleX(AndroidUtilities.lerp(0.7f, 1.0f, floatValue));
                buttonHolder.button.setScaleY(AndroidUtilities.lerp(0.7f, 1.0f, floatValue));
                buttonHolder.button.setTranslationY((AndroidUtilities.m1146dp(100.0f) * (1.0f - floatValue)) - fM1146dp);
                fM1146dp += (AndroidUtilities.m1146dp(48.0f) + AndroidUtilities.m1146dp((i == 4 || i == 3) ? 10.0f : 16.0f)) * floatValue;
            }
            i++;
        }
    }

    private ButtonHolder getButtonHolder(int i) {
        if (i < 0) {
            return null;
        }
        ButtonHolder[] buttonHolderArr = this.buttonHolders;
        if (i >= buttonHolderArr.length) {
            return null;
        }
        return buttonHolderArr[i];
    }

    private ButtonHolder getOrCreateButtonHolder(final int i) {
        ChatActivitySideControlsButtonsLayout chatActivitySideControlsButtonsLayout;
        if (this.buttonHolders[i] == null) {
            chatActivitySideControlsButtonsLayout = this;
            BoolAnimator boolAnimator = new BoolAnimator((i << 16) | 1, chatActivitySideControlsButtonsLayout, AnimatorUtils.DECELERATE_INTERPOLATOR, 280L);
            ChatActivityBlurredRoundPageDownButton chatActivityBlurredRoundPageDownButtonCreate = ChatActivityBlurredRoundPageDownButton.create(getContext(), chatActivitySideControlsButtonsLayout.resourcesProvider, chatActivitySideControlsButtonsLayout.blurredBackgroundDrawableViewFactory, chatActivitySideControlsButtonsLayout.colorProvider, buttonIcons[i]);
            chatActivityBlurredRoundPageDownButtonCreate.setPivotX(AndroidUtilities.m1146dp(28.0f));
            chatActivityBlurredRoundPageDownButtonCreate.setPivotY(AndroidUtilities.m1146dp(36.0f));
            chatActivityBlurredRoundPageDownButtonCreate.setVisibility(8);
            chatActivityBlurredRoundPageDownButtonCreate.setContentDescription(chatActivitySideControlsButtonsLayout.buttonDescriptions[i]);
            chatActivityBlurredRoundPageDownButtonCreate.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.chat.layouts.ChatActivitySideControlsButtonsLayout$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$getOrCreateButtonHolder$0(i, view);
                }
            });
            chatActivityBlurredRoundPageDownButtonCreate.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.telegram.ui.Components.chat.layouts.ChatActivitySideControlsButtonsLayout$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return this.f$0.lambda$getOrCreateButtonHolder$1(i, view);
                }
            });
            if (i == 4) {
                chatActivityBlurredRoundPageDownButtonCreate.reverseIconByY();
            }
            if (i == 0) {
                chatActivityBlurredRoundPageDownButtonCreate.reverseCounter();
            }
            addView(chatActivityBlurredRoundPageDownButtonCreate, LayoutHelper.createFrame(56, 64, 83));
            chatActivitySideControlsButtonsLayout.buttonHolders[i] = new ButtonHolder(chatActivityBlurredRoundPageDownButtonCreate, boolAnimator);
            checkButtonsPositionsAndVisibility();
        } else {
            chatActivitySideControlsButtonsLayout = this;
        }
        return chatActivitySideControlsButtonsLayout.buttonHolders[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getOrCreateButtonHolder$0(int i, View view) {
        ButtonOnClickListener buttonOnClickListener = this.onClickListener;
        if (buttonOnClickListener != null) {
            buttonOnClickListener.onClick(i, view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$getOrCreateButtonHolder$1(int i, View view) {
        ButtonOnLongClickListener buttonOnLongClickListener = this.onLongClickListener;
        if (buttonOnLongClickListener != null) {
            return buttonOnLongClickListener.onLongClick(i, view);
        }
        return false;
    }

    private static class ButtonHolder {
        public final ChatActivityBlurredRoundPageDownButton button;
        public final BoolAnimator visibilityAnimator;

        private ButtonHolder(ChatActivityBlurredRoundPageDownButton chatActivityBlurredRoundPageDownButton, BoolAnimator boolAnimator) {
            this.button = chatActivityBlurredRoundPageDownButton;
            this.visibilityAnimator = boolAnimator;
        }
    }
}
