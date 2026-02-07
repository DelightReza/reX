package com.exteragram.messenger.preferences.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.utils.text.LocaleUtils;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.Easings;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.tgnet.TLObject;
import p017j$.util.Objects;

/* loaded from: classes.dex */
public class ChatListPreviewCell extends FrameLayout implements CustomPreferenceCell {
    private ValueAnimator animator;
    private float centeredTitleProgress;
    private final Paint outlinePaint;
    private final FrameLayout preview;
    private final RectF rect;
    private float statusProgress;
    private final TextPaint textPaint;
    private float titleProgress;
    private String titleText;

    public ChatListPreviewCell(final Context context) {
        super(context);
        this.rect = new RectF();
        this.textPaint = new TextPaint(1);
        Paint paint = new Paint(1);
        this.outlinePaint = paint;
        setWillNotDraw(false);
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), 63));
        paint.setStrokeWidth(Math.max(2, AndroidUtilities.m1146dp(1.0f)));
        FrameLayout frameLayout = new FrameLayout(context) { // from class: com.exteragram.messenger.preferences.components.ChatListPreviewCell.1
            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                Canvas canvas2 = canvas;
                int color = Theme.getColor(Theme.key_switchTrack);
                int iRed = Color.red(color);
                int iGreen = Color.green(color);
                int iBlue = Color.blue(color);
                float measuredWidth = getMeasuredWidth();
                float measuredHeight = getMeasuredHeight();
                ChatListPreviewCell.this.textPaint.setColor(ColorUtils.blendARGB(0, Theme.getColor(Theme.key_windowBackgroundWhiteBlackText), ChatListPreviewCell.this.titleProgress));
                ChatListPreviewCell.this.textPaint.setTextSize(AndroidUtilities.m1146dp(20.0f));
                ChatListPreviewCell chatListPreviewCell = ChatListPreviewCell.this;
                chatListPreviewCell.titleText = (String) TextUtils.ellipsize(chatListPreviewCell.titleText, ChatListPreviewCell.this.textPaint, measuredWidth - AndroidUtilities.m1146dp((ChatListPreviewCell.this.statusProgress * 35.0f) + 130.0f), TextUtils.TruncateAt.END);
                ChatListPreviewCell.this.textPaint.setTextSize(AndroidUtilities.m1146dp((ChatListPreviewCell.this.titleProgress * 2.0f) + 18.0f));
                ChatListPreviewCell.this.textPaint.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
                ChatListPreviewCell.this.rect.set(0.0f, 0.0f, measuredWidth, measuredHeight);
                Theme.dialogs_onlineCirclePaint.setColor(Color.argb(20, iRed, iGreen, iBlue));
                canvas2.drawRoundRect(ChatListPreviewCell.this.rect, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), Theme.dialogs_onlineCirclePaint);
                float strokeWidth = ChatListPreviewCell.this.outlinePaint.getStrokeWidth() / 2.0f;
                ChatListPreviewCell.this.rect.set(strokeWidth, strokeWidth, measuredWidth - strokeWidth, measuredHeight - strokeWidth);
                canvas2.drawRoundRect(ChatListPreviewCell.this.rect, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), ChatListPreviewCell.this.outlinePaint);
                Drawable drawableMutate = ContextCompat.getDrawable(context, C2369R.drawable.ic_ab_search).mutate();
                drawableMutate.setColorFilter(new PorterDuffColorFilter(Color.argb(204, iRed, iGreen, iBlue), PorterDuff.Mode.MULTIPLY));
                int i = (int) measuredWidth;
                drawableMutate.setBounds(i - AndroidUtilities.m1146dp(39.0f), AndroidUtilities.m1146dp(22.0f), i - AndroidUtilities.m1146dp(12.0f), AndroidUtilities.m1146dp(49.0f));
                drawableMutate.draw(canvas2);
                Theme.dialogs_onlineCirclePaint.setColor(Color.argb(204, iRed, iGreen, iBlue));
                int i2 = 0;
                while (i2 < 3) {
                    float f = (i2 * 6.1f) + 28.0f;
                    canvas2.drawRoundRect(AndroidUtilities.dpf2(20.0f), AndroidUtilities.dpf2(f), AndroidUtilities.dpf2(40.0f), AndroidUtilities.dpf2(f + 2.8f), AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), Theme.dialogs_onlineCirclePaint);
                    i2++;
                    canvas2 = canvas;
                }
                float fMeasureText = ChatListPreviewCell.this.textPaint.measureText(ChatListPreviewCell.this.titleText);
                float fM1146dp = (ChatListPreviewCell.this.centeredTitleProgress * ((((measuredWidth - fMeasureText) - (AndroidUtilities.m1146dp(30.0f) * ChatListPreviewCell.this.statusProgress)) / 2.0f) - AndroidUtilities.m1146dp(78.0f))) + AndroidUtilities.m1146dp(78.0f);
                float f2 = fMeasureText + fM1146dp;
                Theme.dialogs_onlineCirclePaint.setColor(ColorUtils.blendARGB(0, ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), 95), ChatListPreviewCell.this.titleProgress * ChatListPreviewCell.this.statusProgress));
                canvas.drawRoundRect(AndroidUtilities.m1146dp(5.0f) + f2, AndroidUtilities.m1146dp(22.0f), f2 + AndroidUtilities.m1146dp(30.0f), AndroidUtilities.m1146dp(47.0f), AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), Theme.dialogs_onlineCirclePaint);
                canvas.drawText(ChatListPreviewCell.this.titleText, fM1146dp, AndroidUtilities.m1146dp(42.0f), ChatListPreviewCell.this.textPaint);
            }
        };
        this.preview = frameLayout;
        frameLayout.setWillNotDraw(false);
        addView(frameLayout, LayoutHelper.createFrame(-1, -1.0f, 49, 21.0f, 15.0f, 21.0f, 21.0f));
        updateStatus(false);
        updateCenteredTitle(false);
        updateTitle(false);
    }

    public void updateStatus(boolean z) {
        float f = !ExteraConfig.hideActionBarStatus ? 1.0f : 0.0f;
        if (!(f == this.statusProgress && z) && UserConfig.getInstance(UserConfig.selectedAccount).isPremium()) {
            if (z) {
                ValueAnimator duration = ValueAnimator.ofFloat(this.statusProgress, f).setDuration(250L);
                this.animator = duration;
                duration.setInterpolator(Easings.easeInOutQuad);
                this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.ChatListPreviewCell$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$updateStatus$0(valueAnimator);
                    }
                });
                this.animator.start();
                return;
            }
            this.statusProgress = f;
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateStatus$0(ValueAnimator valueAnimator) {
        this.statusProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.titleText = LocaleUtils.getActionBarTitle();
        invalidate();
    }

    public void updateCenteredTitle(boolean z) {
        float f = ExteraConfig.centerTitle ? 1.0f : 0.0f;
        float f2 = this.centeredTitleProgress;
        if (f == f2 && z) {
            return;
        }
        if (z) {
            ValueAnimator duration = ValueAnimator.ofFloat(f2, f).setDuration(250L);
            this.animator = duration;
            duration.setInterpolator(Easings.easeInOutQuad);
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.ChatListPreviewCell$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$updateCenteredTitle$1(valueAnimator);
                }
            });
            this.animator.start();
            return;
        }
        this.centeredTitleProgress = f;
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateCenteredTitle$1(ValueAnimator valueAnimator) {
        this.centeredTitleProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    public void updateTitle(boolean z) {
        if (Objects.equals(this.titleText, LocaleUtils.getActionBarTitle()) && z) {
            return;
        }
        if (z) {
            ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(250L);
            this.animator = duration;
            duration.setInterpolator(Easings.easeInOutQuad);
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.ChatListPreviewCell$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$updateTitle$2(valueAnimator);
                }
            });
            this.animator.addListener(new AnimatorListenerAdapter() { // from class: com.exteragram.messenger.preferences.components.ChatListPreviewCell.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    ChatListPreviewCell.this.titleText = LocaleUtils.getActionBarTitle();
                    ChatListPreviewCell.this.animator.setFloatValues(0.0f, 1.0f);
                    ChatListPreviewCell.this.animator.removeAllListeners();
                    ChatListPreviewCell.this.animator.start();
                }
            });
            this.animator.start();
            return;
        }
        this.titleText = LocaleUtils.getActionBarTitle();
        this.titleProgress = 1.0f;
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTitle$2(ValueAnimator valueAnimator) {
        this.titleProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        this.preview.invalidate();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.m1146dp(21.0f), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.m1146dp(21.0f) : 0), getMeasuredHeight() - 1, Theme.dividerPaint);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(108.0f), TLObject.FLAG_30));
    }

    @Override // com.exteragram.messenger.preferences.components.CustomPreferenceCell
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ChatListPreviewCell) {
            ChatListPreviewCell chatListPreviewCell = (ChatListPreviewCell) obj;
            if (Objects.equals(this.preview, chatListPreviewCell.preview) && Objects.equals(this.titleText, chatListPreviewCell.titleText)) {
                return true;
            }
        }
        return false;
    }
}
