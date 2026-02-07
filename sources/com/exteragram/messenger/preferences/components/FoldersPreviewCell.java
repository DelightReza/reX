package com.exteragram.messenger.preferences.components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.utils.p011ui.FolderIcons;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.Easings;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.tgnet.TLObject;
import p017j$.util.Objects;

/* loaded from: classes.dex */
public class FoldersPreviewCell extends FrameLayout implements CustomPreferenceCell {
    private String allChatsTabIcon;
    private String allChatsTabName;
    private ValueAnimator animator;
    private float chipsStyleProgress;
    private float counterProgress;
    private int currentStyle;
    private final String[][] filters;
    private float hideAllChatsProgress;
    private float iconProgress;
    private int oldStyle;
    private final Paint outlinePaint;
    private float pillsStyleProgress;
    private final FrameLayout preview;
    private final RectF rect;
    private float roundedStyleProgress;
    private final TextPaint textPaint;
    private float textStyleProgress;
    private float titleProgress;

    public FoldersPreviewCell(final Context context) {
        super(context);
        this.rect = new RectF();
        this.textPaint = new TextPaint(1);
        Paint paint = new Paint(1);
        this.outlinePaint = paint;
        this.filters = new String[][]{new String[]{LocaleController.getString(C2369R.string.FilterAllChats), "💬"}, new String[]{LocaleController.getString(C2369R.string.FilterGroups), "👥"}, new String[]{LocaleController.getString(C2369R.string.FilterBots), "🤖"}, new String[]{LocaleController.getString(C2369R.string.FilterChannels), "📢"}, new String[]{LocaleController.getString(C2369R.string.FilterNameNonMuted), "🔔"}, new String[]{LocaleController.getString(C2369R.string.FilterContacts), "🏠"}, new String[]{LocaleController.getString(C2369R.string.FilterNameUnread), "✅"}, new String[]{LocaleController.getString(C2369R.string.FilterNonContacts), "🎭"}};
        this.roundedStyleProgress = 0.0f;
        this.chipsStyleProgress = 0.0f;
        this.textStyleProgress = 0.0f;
        this.pillsStyleProgress = 0.0f;
        this.iconProgress = 0.0f;
        this.titleProgress = 0.0f;
        this.counterProgress = 0.0f;
        setWillNotDraw(false);
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), 63));
        paint.setStrokeWidth(Math.max(2, AndroidUtilities.m1146dp(1.0f)));
        FrameLayout frameLayout = new FrameLayout(context) { // from class: com.exteragram.messenger.preferences.components.FoldersPreviewCell.1
            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                int i;
                int i2;
                Drawable drawable;
                float fM1146dp;
                float fDpf2;
                float f;
                int color = Theme.getColor(Theme.key_switchTrack);
                int iRed = Color.red(color);
                int iGreen = Color.green(color);
                int iBlue = Color.blue(color);
                float measuredWidth = getMeasuredWidth();
                float measuredHeight = getMeasuredHeight();
                FoldersPreviewCell.this.rect.set(0.0f, 0.0f, measuredWidth, measuredHeight);
                Theme.dialogs_onlineCirclePaint.setColor(Color.argb(20, iRed, iGreen, iBlue));
                canvas.drawRoundRect(FoldersPreviewCell.this.rect, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), Theme.dialogs_onlineCirclePaint);
                float strokeWidth = FoldersPreviewCell.this.outlinePaint.getStrokeWidth() / 2.0f;
                FoldersPreviewCell.this.rect.set(strokeWidth, strokeWidth, measuredWidth - strokeWidth, measuredHeight - strokeWidth);
                canvas.drawRoundRect(FoldersPreviewCell.this.rect, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), FoldersPreviewCell.this.outlinePaint);
                float fM1146dp2 = ((measuredHeight - AndroidUtilities.m1146dp(4.0f)) - AndroidUtilities.dpf2(FoldersPreviewCell.this.chipsStyleProgress * 4.5f)) - strokeWidth;
                Path path = new Path();
                path.addRect(0.0f, fM1146dp2 + AndroidUtilities.m1146dp(4.0f), getMeasuredWidth(), fM1146dp2 + AndroidUtilities.m1146dp(10.0f), Path.Direction.CCW);
                canvas.clipPath(path, Region.Op.DIFFERENCE);
                FoldersPreviewCell.this.textPaint.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
                float fM1146dp3 = AndroidUtilities.m1146dp(25.0f);
                int i3 = 0;
                int i4 = 0;
                while (i4 < FoldersPreviewCell.this.filters.length) {
                    FoldersPreviewCell.this.textPaint.setTextSize(AndroidUtilities.m1146dp(15.0f));
                    if (i4 == 0) {
                        TextPaint textPaint = FoldersPreviewCell.this.textPaint;
                        int i5 = Theme.key_windowBackgroundWhiteValueText;
                        textPaint.setColor(ColorUtils.blendARGB(i3, Theme.getColor(i5), FoldersPreviewCell.this.hideAllChatsProgress));
                        FoldersPreviewCell.this.textPaint.setTextScaleX(FoldersPreviewCell.this.hideAllChatsProgress * FoldersPreviewCell.this.titleProgress);
                        Theme.dialogs_onlineCirclePaint.setColor(ColorUtils.blendARGB(Theme.getColor(i5), ColorUtils.setAlphaComponent(Theme.getColor(i5), 31), FoldersPreviewCell.this.chipsStyleProgress));
                        Paint paint2 = Theme.dialogs_onlineCirclePaint;
                        paint2.setColor(ColorUtils.blendARGB(i3, paint2.getColor(), FoldersPreviewCell.this.hideAllChatsProgress));
                    } else {
                        FoldersPreviewCell.this.textPaint.setColor(ColorUtils.blendARGB(i3, color, FoldersPreviewCell.this.titleProgress));
                        FoldersPreviewCell.this.textPaint.setTextScaleX(FoldersPreviewCell.this.titleProgress);
                    }
                    FoldersPreviewCell foldersPreviewCell = FoldersPreviewCell.this;
                    String str = i4 == 0 ? foldersPreviewCell.allChatsTabName : foldersPreviewCell.filters[i4][i3];
                    Context context2 = context;
                    FoldersPreviewCell foldersPreviewCell2 = FoldersPreviewCell.this;
                    Drawable drawableMutate = ContextCompat.getDrawable(context2, FolderIcons.getTabIcon(i4 == 0 ? foldersPreviewCell2.allChatsTabIcon : foldersPreviewCell2.filters[i4][1])).mutate();
                    drawableMutate.setColorFilter(new PorterDuffColorFilter(ColorUtils.blendARGB(i3, i4 == 0 ? FoldersPreviewCell.this.textPaint.getColor() : color, FoldersPreviewCell.this.iconProgress), PorterDuff.Mode.MULTIPLY));
                    float fMeasureText = (((FoldersPreviewCell.this.textPaint.measureText(str) + (AndroidUtilities.m1146dp(34.0f) * FoldersPreviewCell.this.iconProgress)) + (i4 == 0 ? AndroidUtilities.dpf2(24.0f) * FoldersPreviewCell.this.counterProgress : 1.0f)) + (((1.0f - FoldersPreviewCell.this.iconProgress) * 14.0f) * FoldersPreviewCell.this.titleProgress)) - (((AndroidUtilities.m1146dp(4.0f) * FoldersPreviewCell.this.iconProgress) * (1.0f - FoldersPreviewCell.this.titleProgress)) * FoldersPreviewCell.this.counterProgress);
                    if (i4 == 0) {
                        float f2 = fM1146dp3 + fMeasureText;
                        String str2 = str;
                        i = i4;
                        i2 = color;
                        drawable = drawableMutate;
                        canvas.drawRoundRect(fM1146dp3, (fM1146dp2 + (AndroidUtilities.dpf2(6.0f) * FoldersPreviewCell.this.textStyleProgress)) - (AndroidUtilities.dpf2(37.5f) * FoldersPreviewCell.this.chipsStyleProgress), f2 + (AndroidUtilities.dpf2(4.0f) * (1.0f - FoldersPreviewCell.this.titleProgress) * (1.0f - FoldersPreviewCell.this.counterProgress)) + (AndroidUtilities.dpf2(22.0f) * FoldersPreviewCell.this.chipsStyleProgress), ((fM1146dp2 + AndroidUtilities.m1146dp(8.0f)) - (AndroidUtilities.dpf2(4.0f) * FoldersPreviewCell.this.roundedStyleProgress)) - (AndroidUtilities.dpf2(9.5f) * FoldersPreviewCell.this.chipsStyleProgress), AndroidUtilities.dpf2((FoldersPreviewCell.this.pillsStyleProgress * 15.0f) + 10.0f), AndroidUtilities.dpf2((FoldersPreviewCell.this.pillsStyleProgress * 15.0f) + 10.0f), Theme.dialogs_onlineCirclePaint);
                        float fDpf22 = (AndroidUtilities.dpf2(6.0f) * (1.0f - FoldersPreviewCell.this.titleProgress) * (1.0f - FoldersPreviewCell.this.counterProgress)) + fM1146dp3 + (AndroidUtilities.dpf2(11.0f) * FoldersPreviewCell.this.chipsStyleProgress);
                        int i6 = ((int) measuredHeight) / 2;
                        drawable.setBounds((int) fDpf22, i6 - AndroidUtilities.m1146dp(13.0f), (int) ((AndroidUtilities.dpf2(26.0f) * FoldersPreviewCell.this.iconProgress * FoldersPreviewCell.this.hideAllChatsProgress) + fDpf22), i6 + AndroidUtilities.m1146dp(13.0f));
                        canvas.drawText(str2, AndroidUtilities.m1146dp(FoldersPreviewCell.this.iconProgress * 30.0f) + fM1146dp3 + (AndroidUtilities.dpf2(10.0f) * FoldersPreviewCell.this.chipsStyleProgress) + ((1.0f - FoldersPreviewCell.this.iconProgress) * 7.0f * FoldersPreviewCell.this.titleProgress), fM1146dp2 - AndroidUtilities.m1146dp(14.0f), FoldersPreviewCell.this.textPaint);
                        FoldersPreviewCell.this.textPaint.setTextScaleX(FoldersPreviewCell.this.counterProgress);
                        FoldersPreviewCell.this.textPaint.setTextSize(AndroidUtilities.m1146dp(FoldersPreviewCell.this.hideAllChatsProgress * 14.0f * FoldersPreviewCell.this.counterProgress));
                        FoldersPreviewCell.this.textPaint.setColor(ColorUtils.blendARGB(0, Color.argb(20, iRed, iGreen, iBlue), FoldersPreviewCell.this.counterProgress));
                        Path path2 = new Path();
                        FoldersPreviewCell.this.textPaint.getTextPath("3", 0, 1, (int) (((f2 - AndroidUtilities.dpf2(15.5f)) + (AndroidUtilities.dpf2(12.0f) * FoldersPreviewCell.this.chipsStyleProgress)) - (AndroidUtilities.m1146dp(1.0f) * (1.0f - FoldersPreviewCell.this.titleProgress))), (int) (fM1146dp2 - AndroidUtilities.dpf2(15.0f)), path2);
                        canvas.clipPath(path2, Region.Op.DIFFERENCE);
                        FoldersPreviewCell.this.textPaint.setColor(ColorUtils.blendARGB(0, Theme.getColor(Theme.key_windowBackgroundWhiteValueText), FoldersPreviewCell.this.counterProgress * FoldersPreviewCell.this.hideAllChatsProgress));
                        canvas.drawCircle(((f2 - AndroidUtilities.dpf2(11.5f)) + (AndroidUtilities.dpf2(12.0f) * FoldersPreviewCell.this.chipsStyleProgress)) - (AndroidUtilities.m1146dp(1.0f) * (1.0f - FoldersPreviewCell.this.titleProgress)), measuredHeight / 2.0f, AndroidUtilities.m1146dp(FoldersPreviewCell.this.counterProgress * 10.0f * FoldersPreviewCell.this.hideAllChatsProgress), FoldersPreviewCell.this.textPaint);
                        fM1146dp = AndroidUtilities.m1146dp(25.0f) + fMeasureText;
                        fDpf2 = AndroidUtilities.dpf2(22.0f);
                        f = FoldersPreviewCell.this.chipsStyleProgress;
                    } else {
                        i = i4;
                        String str3 = str;
                        i2 = color;
                        drawable = drawableMutate;
                        int i7 = (int) fM1146dp3;
                        int i8 = ((int) measuredHeight) / 2;
                        drawable.setBounds(i7, i8 - AndroidUtilities.m1146dp(13.0f), AndroidUtilities.m1146dp(FoldersPreviewCell.this.iconProgress * 26.0f) + i7, i8 + AndroidUtilities.m1146dp(13.0f));
                        canvas.drawText(str3, (AndroidUtilities.m1146dp(30.0f) * FoldersPreviewCell.this.iconProgress) + fM1146dp3, fM1146dp2 - AndroidUtilities.m1146dp(14.0f), FoldersPreviewCell.this.textPaint);
                        fM1146dp = AndroidUtilities.m1146dp(25.0f) + fMeasureText;
                        fDpf2 = AndroidUtilities.dpf2(5.0f);
                        f = FoldersPreviewCell.this.chipsStyleProgress;
                    }
                    fM1146dp3 += fM1146dp + (fDpf2 * f);
                    drawable.draw(canvas);
                    i4 = i + 1;
                    color = i2;
                    i3 = 0;
                }
            }
        };
        this.preview = frameLayout;
        frameLayout.setWillNotDraw(false);
        addView(frameLayout, LayoutHelper.createFrame(-1, -1.0f, 49, 21.0f, 15.0f, 21.0f, 21.0f));
        updateTabStyle(false);
        updateTabIcons(false);
        updateTabTitle(false);
        updateAllChatsTabName(false);
        updateTabCounter(false);
    }

    public void updateAllChatsTabName(boolean z) {
        if (Objects.equals(this.allChatsTabName, getAllChatsTabName()) && z) {
            return;
        }
        if (z) {
            ValueAnimator duration = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(250L);
            this.animator = duration;
            duration.setInterpolator(Easings.easeInOutQuad);
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.FoldersPreviewCell$$ExternalSyntheticLambda3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$updateAllChatsTabName$0(valueAnimator);
                }
            });
            this.animator.addListener(new AnimatorListenerAdapter() { // from class: com.exteragram.messenger.preferences.components.FoldersPreviewCell.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    FoldersPreviewCell foldersPreviewCell = FoldersPreviewCell.this;
                    foldersPreviewCell.allChatsTabName = foldersPreviewCell.getAllChatsTabName();
                    FoldersPreviewCell foldersPreviewCell2 = FoldersPreviewCell.this;
                    foldersPreviewCell2.allChatsTabIcon = foldersPreviewCell2.getAllChatsTabIcon();
                    FoldersPreviewCell.this.animator.setFloatValues(0.0f, 1.0f);
                    FoldersPreviewCell.this.animator.removeAllListeners();
                    FoldersPreviewCell.this.animator.start();
                }
            });
            this.animator.start();
            return;
        }
        this.allChatsTabName = getAllChatsTabName();
        this.allChatsTabIcon = getAllChatsTabIcon();
        this.hideAllChatsProgress = 1.0f;
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAllChatsTabName$0(ValueAnimator valueAnimator) {
        this.hideAllChatsProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    public void updateTabStyle(boolean z) {
        if (Integer.valueOf(this.currentStyle).equals(Integer.valueOf(ExteraConfig.tabStyle)) && z) {
            return;
        }
        this.oldStyle = this.currentStyle;
        int i = ExteraConfig.tabStyle;
        this.currentStyle = i;
        if (z) {
            ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(250L);
            duration.setStartDelay(100L);
            Interpolator interpolator = Easings.easeInOutQuad;
            duration.setInterpolator(interpolator);
            duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.FoldersPreviewCell$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$updateTabStyle$1(valueAnimator);
                }
            });
            ValueAnimator duration2 = ValueAnimator.ofFloat(1.0f, 0.0f).setDuration(250L);
            this.animator = duration2;
            duration2.setStartDelay(100L);
            this.animator.setInterpolator(interpolator);
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.FoldersPreviewCell$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$updateTabStyle$2(valueAnimator);
                }
            });
            this.animator.start();
            duration.start();
            return;
        }
        if (i == 1) {
            this.roundedStyleProgress = 1.0f;
        } else if (i == 2) {
            this.textStyleProgress = 1.0f;
        } else if (i == 3) {
            this.chipsStyleProgress = 1.0f;
        } else if (i == 4) {
            this.chipsStyleProgress = 1.0f;
            this.pillsStyleProgress = 1.0f;
        }
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTabStyle$1(ValueAnimator valueAnimator) {
        int i = this.currentStyle;
        if (i == 1) {
            this.roundedStyleProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        } else if (i == 2) {
            this.textStyleProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        } else if (i != 3) {
            if (i == 4) {
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                this.pillsStyleProgress = fFloatValue;
                if (this.oldStyle != 3) {
                    this.chipsStyleProgress = fFloatValue;
                }
            }
        } else if (this.oldStyle != 4) {
            this.chipsStyleProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        }
        invalidate();
        requestLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTabStyle$2(ValueAnimator valueAnimator) {
        int i = this.oldStyle;
        if (i == 1) {
            this.roundedStyleProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        } else if (i == 2) {
            this.textStyleProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        } else if (i != 3) {
            if (i == 4) {
                this.pillsStyleProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (this.currentStyle != 3) {
                    this.chipsStyleProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                }
            }
        } else if (this.currentStyle != 4) {
            this.chipsStyleProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        }
        invalidate();
    }

    public void updateTabTitle(boolean z) {
        float f = ExteraConfig.tabIcons != 2 ? 1.0f : 0.0f;
        float f2 = this.titleProgress;
        if (f == f2 && z) {
            return;
        }
        if (z) {
            ValueAnimator duration = ValueAnimator.ofFloat(f2, f).setDuration(250L);
            this.animator = duration;
            duration.setInterpolator(Easings.easeInOutQuad);
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.FoldersPreviewCell$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$updateTabTitle$3(valueAnimator);
                }
            });
            this.animator.start();
            return;
        }
        this.titleProgress = f;
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTabTitle$3(ValueAnimator valueAnimator) {
        this.titleProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    public void updateTabIcons(boolean z) {
        float f = ExteraConfig.tabIcons != 1 ? 1.0f : 0.0f;
        float f2 = this.iconProgress;
        if (f == f2 && z) {
            return;
        }
        if (z) {
            ValueAnimator duration = ValueAnimator.ofFloat(f2, f).setDuration(250L);
            this.animator = duration;
            duration.setInterpolator(Easings.easeInOutQuad);
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.FoldersPreviewCell$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$updateTabIcons$4(valueAnimator);
                }
            });
            this.animator.start();
            return;
        }
        this.iconProgress = f;
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTabIcons$4(ValueAnimator valueAnimator) {
        this.iconProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    public void updateTabCounter(boolean z) {
        float f = ExteraConfig.tabCounter ? 1.0f : 0.0f;
        float f2 = this.counterProgress;
        if (f == f2 && z) {
            return;
        }
        if (z) {
            ValueAnimator duration = ValueAnimator.ofFloat(f2, f).setDuration(250L);
            this.animator = duration;
            duration.setInterpolator(Easings.easeInOutQuad);
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.FoldersPreviewCell$$ExternalSyntheticLambda5
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$updateTabCounter$5(valueAnimator);
                }
            });
            this.animator.start();
            return;
        }
        this.counterProgress = f;
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTabCounter$5(ValueAnimator valueAnimator) {
        this.counterProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAllChatsTabName() {
        return ExteraConfig.hideAllChats ? this.filters[6][0] : this.filters[0][0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getAllChatsTabIcon() {
        return ExteraConfig.hideAllChats ? this.filters[6][1] : this.filters[0][1];
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
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp((this.chipsStyleProgress * 8.0f) + 86.0f), TLObject.FLAG_30));
    }

    @Override // com.exteragram.messenger.preferences.components.CustomPreferenceCell
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FoldersPreviewCell) {
            FoldersPreviewCell foldersPreviewCell = (FoldersPreviewCell) obj;
            if (Objects.equals(this.preview, foldersPreviewCell.preview) && this.rect.equals(foldersPreviewCell.rect) && this.oldStyle == foldersPreviewCell.oldStyle && this.currentStyle == foldersPreviewCell.currentStyle) {
                return true;
            }
        }
        return false;
    }
}
