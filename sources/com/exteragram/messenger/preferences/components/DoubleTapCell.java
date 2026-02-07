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
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.ExteraConfig;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.Easings;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.tgnet.TLObject;

/* loaded from: classes.dex */
public class DoubleTapCell extends LinearLayout implements CustomPreferenceCell {
    private final int[] actionIcon;
    private final ValueAnimator[] animator;
    private final ValueAnimator[] circleAnimator;
    private final Paint[] circleOutlinePaint;
    private final float[] circleProgress;
    private final ValueAnimator[] circleSizeAnimator;
    private final float[] circleSizeProgress;
    private final float[] iconChangingProgress;
    private final Theme.MessageDrawable[] messages;
    private final Paint outlinePaint;
    private final FrameLayout preview;
    private final RectF rect;
    private static final int[] doubleTapIcons = {C2369R.drawable.msg_block, C2369R.drawable.msg_reactions2, C2369R.drawable.menu_reply, C2369R.drawable.msg_copy, C2369R.drawable.msg_forward, C2369R.drawable.msg_edit, C2369R.drawable.msg_saved, C2369R.drawable.msg_delete, C2369R.drawable.msg_translate, C2369R.drawable.msg_view_file};
    private static final int[] ICON_WIDTH = {AndroidUtilities.m1146dp(12.0f), AndroidUtilities.m1146dp(12.0f)};

    public DoubleTapCell(final Context context) {
        super(context);
        this.rect = new RectF();
        Paint paint = new Paint(1);
        this.outlinePaint = paint;
        this.circleOutlinePaint = new Paint[2];
        this.messages = new Theme.MessageDrawable[]{new Theme.MessageDrawable(0, false, false), new Theme.MessageDrawable(0, true, false)};
        this.animator = new ValueAnimator[2];
        this.circleAnimator = new ValueAnimator[2];
        this.circleSizeAnimator = new ValueAnimator[2];
        this.circleSizeProgress = new float[4];
        this.iconChangingProgress = new float[2];
        this.circleProgress = new float[4];
        this.actionIcon = new int[2];
        setWillNotDraw(false);
        setOrientation(1);
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        setPadding(AndroidUtilities.m1146dp(13.0f), 0, AndroidUtilities.m1146dp(13.0f), AndroidUtilities.m1146dp(10.0f));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), 63));
        paint.setStrokeWidth(Math.max(2, AndroidUtilities.m1146dp(1.0f)));
        FrameLayout frameLayout = new FrameLayout(context) { // from class: com.exteragram.messenger.preferences.components.DoubleTapCell.1
            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                int color = Theme.getColor(Theme.key_switchTrack);
                int iRed = Color.red(color);
                int iGreen = Color.green(color);
                int iBlue = Color.blue(color);
                Rect rect = new Rect();
                float f = 2.0f;
                float strokeWidth = DoubleTapCell.this.outlinePaint.getStrokeWidth() / 2.0f;
                Theme.dialogs_onlineCirclePaint.setColor(Color.argb(20, iRed, iGreen, iBlue));
                int i = 0;
                while (true) {
                    int i2 = 2;
                    if (i >= 2) {
                        return;
                    }
                    if (i == 0) {
                        DoubleTapCell.this.rect.set(AndroidUtilities.m1146dp(8.0f) + strokeWidth, AndroidUtilities.m1146dp(10.0f) + strokeWidth, ((getMeasuredWidth() / f) - AndroidUtilities.m1146dp(8.0f)) - strokeWidth, AndroidUtilities.m1146dp(75.0f) - strokeWidth);
                    } else {
                        canvas.translate(0.0f, AndroidUtilities.m1146dp(80.0f));
                        DoubleTapCell.this.rect.set((getMeasuredWidth() / f) + strokeWidth + AndroidUtilities.m1146dp(8.0f), AndroidUtilities.m1146dp(5.0f) + strokeWidth, (getMeasuredWidth() - AndroidUtilities.m1146dp(8.0f)) - strokeWidth, AndroidUtilities.m1146dp(70.0f) - strokeWidth);
                    }
                    DoubleTapCell.this.rect.round(rect);
                    DoubleTapCell.this.messages[i].setBounds(rect);
                    DoubleTapCell.this.messages[i].draw(canvas, Theme.dialogs_onlineCirclePaint);
                    DoubleTapCell.this.messages[i].draw(canvas, DoubleTapCell.this.outlinePaint);
                    int i3 = 0;
                    while (true) {
                        float f2 = 3.0f;
                        if (i3 >= i2) {
                            break;
                        }
                        DoubleTapCell.this.circleOutlinePaint[i3] = new Paint(1);
                        DoubleTapCell.this.circleOutlinePaint[i3].setStyle(Paint.Style.STROKE);
                        int i4 = i + (i3 * 2);
                        DoubleTapCell.this.circleOutlinePaint[i3].setColor(ColorUtils.blendARGB(0, Color.argb(76, iRed, iGreen, iBlue), DoubleTapCell.this.circleProgress[i4]));
                        DoubleTapCell.this.circleOutlinePaint[i3].setStrokeWidth(AndroidUtilities.m1146dp(1.5f) * DoubleTapCell.this.circleProgress[i4] * DoubleTapCell.this.circleProgress[i4]);
                        float measuredWidth = ((i == 0 ? 1 : 3) * getMeasuredWidth()) / 4.0f;
                        float measuredHeight = getMeasuredHeight() / 4.0f;
                        if (i != 0) {
                            f2 = -2.0f;
                        }
                        canvas.drawCircle(measuredWidth, measuredHeight + AndroidUtilities.dpf2(f2), AndroidUtilities.m1146dp(25 - (i3 * 6)) * DoubleTapCell.this.circleSizeProgress[i4], DoubleTapCell.this.circleOutlinePaint[i3]);
                        i3++;
                        i2 = 2;
                    }
                    Drawable drawable = ContextCompat.getDrawable(context, DoubleTapCell.this.actionIcon[i]);
                    if (i == 0) {
                        drawable.setBounds((getMeasuredWidth() / 4) - DoubleTapCell.ICON_WIDTH[i], (int) (((getMeasuredHeight() / 4) - DoubleTapCell.ICON_WIDTH[i]) + AndroidUtilities.dpf2(3.0f)), (getMeasuredWidth() / 4) + DoubleTapCell.ICON_WIDTH[i], (int) ((getMeasuredHeight() / 4) + DoubleTapCell.ICON_WIDTH[i] + AndroidUtilities.dpf2(3.0f)));
                    } else {
                        drawable.setBounds(((getMeasuredWidth() * 3) / 4) - DoubleTapCell.ICON_WIDTH[i], (int) (((getMeasuredHeight() / 4) - DoubleTapCell.ICON_WIDTH[i]) - AndroidUtilities.dpf2(2.0f)), ((getMeasuredWidth() * 3) / 4) + DoubleTapCell.ICON_WIDTH[i], (int) (((getMeasuredHeight() / 4) + DoubleTapCell.ICON_WIDTH[i]) - AndroidUtilities.dpf2(2.0f)));
                    }
                    drawable.setBounds(drawable.getBounds().left - AndroidUtilities.m1146dp(4.0f - (DoubleTapCell.this.iconChangingProgress[i] * 4.0f)), drawable.getBounds().top - AndroidUtilities.m1146dp(4.0f - (DoubleTapCell.this.iconChangingProgress[i] * 4.0f)), drawable.getBounds().right + AndroidUtilities.m1146dp(4.0f - (DoubleTapCell.this.iconChangingProgress[i] * 4.0f)), drawable.getBounds().bottom + AndroidUtilities.m1146dp(4.0f - (DoubleTapCell.this.iconChangingProgress[i] * 4.0f)));
                    drawable.setColorFilter(new PorterDuffColorFilter(ColorUtils.blendARGB(0, Theme.getColor(Theme.key_chats_menuItemIcon), DoubleTapCell.this.iconChangingProgress[i]), PorterDuff.Mode.MULTIPLY));
                    drawable.draw(canvas);
                    i++;
                    f = 2.0f;
                }
            }
        };
        this.preview = frameLayout;
        frameLayout.setWillNotDraw(false);
        addView(frameLayout, LayoutHelper.createFrame(-1, -1.0f));
        updateIcons(0, false);
    }

    public void updateIcons(int i, boolean z) {
        final int i2 = 0;
        while (i2 < 2) {
            if ((i2 != 0 || i != 2) && (i2 != 1 || i != 1)) {
                if (z) {
                    for (final int i3 = 0; i3 < 2; i3++) {
                        this.circleSizeAnimator[i3] = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(1300L);
                        long j = i3;
                        this.circleSizeAnimator[i3].setStartDelay(60 * j);
                        ValueAnimator valueAnimator = this.circleSizeAnimator[i3];
                        Interpolator interpolator = Easings.easeInOutQuad;
                        valueAnimator.setInterpolator(interpolator);
                        this.circleSizeAnimator[i3].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.DoubleTapCell$$ExternalSyntheticLambda0
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                this.f$0.lambda$updateIcons$0(i3, i2, valueAnimator2);
                            }
                        });
                        this.circleAnimator[i3] = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(700L);
                        this.circleAnimator[i3].setStartDelay((j * 80) + 150);
                        this.circleAnimator[i3].setInterpolator(interpolator);
                        this.circleAnimator[i3].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.DoubleTapCell$$ExternalSyntheticLambda1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                this.f$0.lambda$updateIcons$1(i3, i2, valueAnimator2);
                            }
                        });
                        this.circleAnimator[i3].addListener(new AnimatorListenerAdapter() { // from class: com.exteragram.messenger.preferences.components.DoubleTapCell.2
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                super.onAnimationEnd(animator);
                                DoubleTapCell.this.circleAnimator[i3].setFloatValues(1.0f, 0.0f);
                                DoubleTapCell.this.circleAnimator[i3].setDuration(700L);
                                DoubleTapCell.this.circleAnimator[i3].removeAllListeners();
                                DoubleTapCell.this.circleAnimator[i3].start();
                            }
                        });
                        this.circleSizeAnimator[i3].start();
                        this.circleAnimator[i3].start();
                    }
                    this.animator[i2] = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(250L);
                    this.animator[i2].setInterpolator(Easings.easeInOutQuad);
                    this.animator[i2].addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.DoubleTapCell$$ExternalSyntheticLambda2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            this.f$0.lambda$updateIcons$2(i2, valueAnimator2);
                        }
                    });
                    this.animator[i2].addListener(new AnimatorListenerAdapter() { // from class: com.exteragram.messenger.preferences.components.DoubleTapCell.3
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            int[] iArr = DoubleTapCell.this.actionIcon;
                            int i4 = i2;
                            int[] iArr2 = DoubleTapCell.doubleTapIcons;
                            iArr[i4] = i4 == 0 ? iArr2[ExteraConfig.doubleTapAction] : iArr2[ExteraConfig.doubleTapActionOutOwner];
                            DoubleTapCell.this.animator[i2].setFloatValues(0.0f, 1.0f);
                            DoubleTapCell.this.animator[i2].removeAllListeners();
                            DoubleTapCell.this.animator[i2].addListener(new AnimatorListenerAdapter() { // from class: com.exteragram.messenger.preferences.components.DoubleTapCell.3.1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animator2) {
                                    super.onAnimationEnd(animator2);
                                    DoubleTapCell.this.performHapticFeedback(3, 2);
                                }
                            });
                            DoubleTapCell.this.animator[i2].start();
                        }
                    });
                    this.animator[i2].start();
                } else {
                    this.circleSizeProgress[i2] = 0.0f;
                    this.circleProgress[i2] = 0.0f;
                    this.iconChangingProgress[i2] = 1.0f;
                    int[] iArr = this.actionIcon;
                    int[] iArr2 = doubleTapIcons;
                    iArr[i2] = i2 == 0 ? iArr2[ExteraConfig.doubleTapAction] : iArr2[ExteraConfig.doubleTapActionOutOwner];
                    invalidate();
                }
            }
            i2++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateIcons$0(int i, int i2, ValueAnimator valueAnimator) {
        this.circleSizeProgress[(i * 2) + i2] = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateIcons$1(int i, int i2, ValueAnimator valueAnimator) {
        this.circleProgress[(i * 2) + i2] = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateIcons$2(int i, ValueAnimator valueAnimator) {
        this.iconChangingProgress[i] = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        this.preview.invalidate();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.m1146dp(21.0f), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.m1146dp(21.0f) : 0), getMeasuredHeight() - 1, Theme.dividerPaint);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(170.0f), TLObject.FLAG_30));
    }

    @Override // com.exteragram.messenger.preferences.components.CustomPreferenceCell
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof DoubleTapCell) && this.actionIcon == ((DoubleTapCell) obj).actionIcon;
    }
}
