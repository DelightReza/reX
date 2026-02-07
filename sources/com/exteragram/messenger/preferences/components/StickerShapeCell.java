package com.exteragram.messenger.preferences.components;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.text.TextPaint;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.ExteraConfig;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.SharedConfig;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.Easings;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.tgnet.TLObject;

/* loaded from: classes.dex */
public abstract class StickerShapeCell extends LinearLayout implements CustomPreferenceCell {
    private final StickerShape[] stickerShape;

    protected abstract void updateStickerPreview();

    public StickerShapeCell(Context context) {
        super(context);
        this.stickerShape = new StickerShape[3];
        setOrientation(0);
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        setPadding(AndroidUtilities.m1146dp(13.0f), AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(13.0f), 0);
        final int i = 0;
        while (i < 3) {
            boolean z = true;
            boolean z2 = i == 1;
            if (i != 2) {
                z = false;
            }
            this.stickerShape[i] = new StickerShape(context, z2, z);
            ScaleStateListAnimator.apply(this.stickerShape[i], 0.03f, 1.5f);
            addView(this.stickerShape[i], LayoutHelper.createLinear(-1, -1, 0.5f, 8, 0, 8, 0));
            this.stickerShape[i].setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.components.StickerShapeCell$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(i, view);
                }
            });
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, View view) {
        for (int i2 = 0; i2 < 3; i2++) {
            StickerShape stickerShape = this.stickerShape[i2];
            stickerShape.setSelected(view == stickerShape, true);
        }
        SharedPreferences.Editor editor = ExteraConfig.editor;
        ExteraConfig.stickerShape = i;
        editor.putInt("stickerShape", i).apply();
        updateStickerPreview();
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        for (int i = 0; i < 3; i++) {
            this.stickerShape[i].invalidate();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(130.0f), TLObject.FLAG_30));
    }

    @Override // com.exteragram.messenger.preferences.components.CustomPreferenceCell
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof StickerShapeCell) && this.stickerShape == ((StickerShapeCell) obj).stickerShape;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class StickerShape extends FrameLayout {
        private final boolean isRounded;
        private final boolean isRoundedAsMsg;
        private final Paint outlinePaint;
        private float progress;
        private final RectF rect;
        private final TextPaint textPaint;

        public StickerShape(Context context, boolean z, boolean z2) {
            super(context);
            boolean z3 = true;
            TextPaint textPaint = new TextPaint(1);
            this.textPaint = textPaint;
            this.rect = new RectF();
            Paint paint = new Paint(1);
            this.outlinePaint = paint;
            setWillNotDraw(false);
            this.isRounded = z;
            this.isRoundedAsMsg = z2;
            textPaint.setTextSize(AndroidUtilities.m1146dp(13.0f));
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), 63));
            paint.setStrokeWidth(Math.max(2, AndroidUtilities.m1146dp(1.0f)));
            if ((z || z2 || ExteraConfig.stickerShape != 0) && ((!z || ExteraConfig.stickerShape != 1) && (!z2 || ExteraConfig.stickerShape != 2))) {
                z3 = false;
            }
            setSelected(z3, false);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            int color = Theme.getColor(Theme.key_switchTrack);
            int iRed = Color.red(color);
            int iGreen = Color.green(color);
            int iBlue = Color.blue(color);
            this.rect.set(0.0f, 0.0f, getMeasuredWidth(), AndroidUtilities.m1146dp(80.0f));
            Theme.dialogs_onlineCirclePaint.setColor(Color.argb(20, iRed, iGreen, iBlue));
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), Theme.dialogs_onlineCirclePaint);
            float strokeWidth = this.outlinePaint.getStrokeWidth() / 2.0f;
            this.rect.set(strokeWidth, strokeWidth, getMeasuredWidth() - strokeWidth, AndroidUtilities.m1146dp(80.0f) - strokeWidth);
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), this.outlinePaint);
            canvas.drawText(LocaleController.getString(this.isRounded ? C2369R.string.StickerShapeRounded : this.isRoundedAsMsg ? C2369R.string.StickerShapeRoundedMsg : C2369R.string.Default), (getMeasuredWidth() - ((int) Math.ceil(this.textPaint.measureText(r3)))) >> 1, AndroidUtilities.m1146dp(102.0f), this.textPaint);
            this.rect.set(AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), getMeasuredWidth() - AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(70.0f));
            Theme.dialogs_onlineCirclePaint.setColor(Color.argb(90, iRed, iGreen, iBlue));
            boolean z = this.isRounded;
            if (!z && !this.isRoundedAsMsg) {
                canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(0.0f), AndroidUtilities.m1146dp(0.0f), Theme.dialogs_onlineCirclePaint);
                return;
            }
            if (z) {
                canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(6.0f), AndroidUtilities.m1146dp(6.0f), Theme.dialogs_onlineCirclePaint);
                return;
            }
            Rect rect = new Rect();
            this.rect.round(rect);
            int iM1146dp = AndroidUtilities.m1146dp(SharedConfig.bubbleRadius);
            float f = iM1146dp;
            float f2 = iM1146dp / 3;
            ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{f, f, f, f, f, f, f2, f2}, null, null));
            shapeDrawable.getPaint().setColor(Theme.dialogs_onlineCirclePaint.getColor());
            shapeDrawable.setBounds(rect);
            shapeDrawable.draw(canvas);
        }

        private void setProgress(float f) {
            this.progress = f;
            TextPaint textPaint = this.textPaint;
            int color = Theme.getColor(Theme.key_windowBackgroundWhiteBlackText);
            int i = Theme.key_windowBackgroundWhiteValueText;
            textPaint.setColor(ColorUtils.blendARGB(color, Theme.getColor(i), f));
            this.outlinePaint.setColor(ColorUtils.blendARGB(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), 63), Theme.getColor(i), f));
            this.outlinePaint.setStrokeWidth(Math.max(2, AndroidUtilities.m1146dp(AndroidUtilities.lerp(1.0f, 2.0f, f))));
            invalidate();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setSelected(boolean z, boolean z2) {
            float f = z ? 1.0f : 0.0f;
            float f2 = this.progress;
            if (f == f2 && z2) {
                return;
            }
            if (z2) {
                ValueAnimator duration = ValueAnimator.ofFloat(f2, f).setDuration(250L);
                duration.setInterpolator(Easings.easeInOutQuad);
                duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.StickerShapeCell$StickerShape$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$setSelected$0(valueAnimator);
                    }
                });
                duration.start();
                return;
            }
            setProgress(f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSelected$0(ValueAnimator valueAnimator) {
            setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }
}
