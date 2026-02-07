package com.exteragram.messenger.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.Property;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.ChatActivityEnterView;
import org.telegram.p023ui.Components.LayoutHelper;

/* loaded from: classes.dex */
public class ChatActivityEnterViewStaticIconView extends FrameLayout {
    private final ImageView[] buttonViews;
    private AnimatorSet buttonsAnimation;
    private State currentState;

    public ChatActivityEnterViewStaticIconView(Context context, ChatActivityEnterView chatActivityEnterView) {
        super(context);
        this.buttonViews = new ImageView[2];
        for (int i = 0; i < 2; i++) {
            this.buttonViews[i] = new ImageView(context);
            this.buttonViews[i].setColorFilter(new PorterDuffColorFilter(chatActivityEnterView.getThemedColor(Theme.key_chat_messagePanelIcons), PorterDuff.Mode.MULTIPLY));
            this.buttonViews[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            addView(this.buttonViews[i], LayoutHelper.createFrame(-1, -1.0f, 83, 0.0f, 0.0f, 0.0f, 0.0f));
        }
        this.buttonViews[0].setVisibility(0);
        this.buttonViews[1].setVisibility(8);
        this.buttonViews[1].setScaleX(0.1f);
        this.buttonViews[1].setScaleY(0.1f);
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.buttonViews[0].setColorFilter(colorFilter);
        this.buttonViews[1].setColorFilter(colorFilter);
    }

    public void setState(State state, boolean z) {
        if (z && state == this.currentState) {
            return;
        }
        State state2 = this.currentState;
        this.currentState = state;
        if (!z || state2 == null) {
            this.buttonViews[0].setImageResource(state.resource);
        } else {
            AnimatorSet animatorSet = this.buttonsAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            this.buttonViews[1].setVisibility(0);
            this.buttonViews[1].setImageResource(this.currentState.resource);
            this.buttonViews[0].setAlpha(1.0f);
            this.buttonViews[1].setAlpha(0.0f);
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.buttonsAnimation = animatorSet2;
            Property property = View.SCALE_X;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this.buttonViews[0], (Property<ImageView, Float>) property, 0.1f);
            Property property2 = View.SCALE_Y;
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.buttonViews[0], (Property<ImageView, Float>) property2, 0.1f);
            Property property3 = View.ALPHA;
            animatorSet2.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, ObjectAnimator.ofFloat(this.buttonViews[0], (Property<ImageView, Float>) property3, 0.0f), ObjectAnimator.ofFloat(this.buttonViews[1], (Property<ImageView, Float>) property, 1.0f), ObjectAnimator.ofFloat(this.buttonViews[1], (Property<ImageView, Float>) property2, 1.0f), ObjectAnimator.ofFloat(this.buttonViews[1], (Property<ImageView, Float>) property3, 1.0f));
            this.buttonsAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.exteragram.messenger.components.ChatActivityEnterViewStaticIconView.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (animator.equals(ChatActivityEnterViewStaticIconView.this.buttonsAnimation)) {
                        ChatActivityEnterViewStaticIconView.this.buttonsAnimation = null;
                        ImageView imageView = ChatActivityEnterViewStaticIconView.this.buttonViews[1];
                        ChatActivityEnterViewStaticIconView.this.buttonViews[1] = ChatActivityEnterViewStaticIconView.this.buttonViews[0];
                        ChatActivityEnterViewStaticIconView.this.buttonViews[0] = imageView;
                        ChatActivityEnterViewStaticIconView.this.buttonViews[1].setVisibility(4);
                        ChatActivityEnterViewStaticIconView.this.buttonViews[1].setAlpha(0.0f);
                        ChatActivityEnterViewStaticIconView.this.buttonViews[1].setScaleX(0.1f);
                        ChatActivityEnterViewStaticIconView.this.buttonViews[1].setScaleY(0.1f);
                    }
                }
            });
            this.buttonsAnimation.setDuration(200L);
            this.buttonsAnimation.start();
        }
        int i = C07892.f150xb3ccf09[state.ordinal()];
        if (i == 1) {
            setContentDescription(LocaleController.getString(C2369R.string.AccDescrVoiceMessage));
        } else {
            if (i != 2) {
                return;
            }
            setContentDescription(LocaleController.getString(C2369R.string.AccDescrVideoMessage));
        }
    }

    /* renamed from: com.exteragram.messenger.components.ChatActivityEnterViewStaticIconView$2 */
    static /* synthetic */ class C07892 {

        /* renamed from: $SwitchMap$com$exteragram$messenger$components$ChatActivityEnterViewStaticIconView$State */
        static final /* synthetic */ int[] f150xb3ccf09;

        static {
            int[] iArr = new int[State.values().length];
            f150xb3ccf09 = iArr;
            try {
                iArr[State.VOICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f150xb3ccf09[State.VIDEO.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public enum State {
        VOICE(C2369R.drawable.input_mic),
        VIDEO(C2369R.drawable.input_video),
        STICKER(C2369R.drawable.msg_sticker),
        KEYBOARD(C2369R.drawable.input_keyboard),
        SMILE(C2369R.drawable.input_smile),
        GIF(C2369R.drawable.msg_gif);

        final int resource;

        State(int i) {
            this.resource = i;
        }
    }
}
