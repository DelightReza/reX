package com.exteragram.messenger.preferences.components;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.ExteraConfig;
import java.util.Arrays;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.Easings;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.tgnet.TLObject;

/* loaded from: classes.dex */
public abstract class FabShapeCell extends LinearLayout implements CustomPreferenceCell {
    private final FabShape[] fabShape;

    protected abstract void rebuildFragments();

    public FabShapeCell(Context context) {
        super(context);
        this.fabShape = new FabShape[2];
        setWillNotDraw(false);
        setOrientation(0);
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        setPadding(AndroidUtilities.m1146dp(13.0f), AndroidUtilities.m1146dp(15.0f), AndroidUtilities.m1146dp(13.0f), AndroidUtilities.m1146dp(21.0f));
        for (int i = 0; i < 2; i++) {
            final boolean z = true;
            if (i != 1) {
                z = false;
            }
            this.fabShape[i] = new FabShape(context, z);
            ScaleStateListAnimator.apply(this.fabShape[i], 0.03f, 1.5f);
            addView(this.fabShape[i], LayoutHelper.createLinear(-1, -1, 0.5f, 8, 0, 8, 0));
            this.fabShape[i].setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.components.FabShapeCell$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$new$0(z, view);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(boolean z, View view) {
        for (int i = 0; i < 2; i++) {
            FabShape fabShape = this.fabShape[i];
            fabShape.setSelected(view == fabShape, true);
        }
        SharedPreferences.Editor editor = ExteraConfig.editor;
        ExteraConfig.squareFab = z;
        editor.putBoolean("squareFab", z).apply();
        rebuildFragments();
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        for (int i = 0; i < 2; i++) {
            this.fabShape[i].invalidate();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.m1146dp(21.0f), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.m1146dp(21.0f) : 0), getMeasuredHeight() - 1, Theme.dividerPaint);
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(110.0f), TLObject.FLAG_30));
    }

    @Override // com.exteragram.messenger.preferences.components.CustomPreferenceCell
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof FabShapeCell) {
            return Arrays.equals(this.fabShape, ((FabShapeCell) obj).fabShape);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class FabShape extends FrameLayout {
        private final Paint outlinePaint;
        private float progress;
        private final RectF rect;
        private final boolean squareFab;

        public FabShape(Context context, boolean z) {
            super(context);
            this.rect = new RectF();
            boolean z2 = true;
            Paint paint = new Paint(1);
            this.outlinePaint = paint;
            setWillNotDraw(false);
            this.squareFab = z;
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), 63));
            paint.setStrokeWidth(Math.max(2, AndroidUtilities.m1146dp(1.0f)));
            if ((!z || !ExteraConfig.squareFab) && (z || ExteraConfig.squareFab)) {
                z2 = false;
            }
            setSelected(z2, false);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            int color = Theme.getColor(Theme.key_switchTrack);
            int iRed = Color.red(color);
            int iGreen = Color.green(color);
            int iBlue = Color.blue(color);
            this.rect.set(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight());
            Theme.dialogs_onlineCirclePaint.setColor(Color.argb(20, iRed, iGreen, iBlue));
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), Theme.dialogs_onlineCirclePaint);
            float strokeWidth = this.outlinePaint.getStrokeWidth() / 2.0f;
            this.rect.set(strokeWidth, strokeWidth, getMeasuredWidth() - strokeWidth, getMeasuredHeight() - strokeWidth);
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), this.outlinePaint);
            int iM1146dp = AndroidUtilities.m1146dp(22.0f);
            int iM1146dp2 = AndroidUtilities.m1146dp(21.0f);
            int i = iM1146dp / 2;
            int i2 = 0;
            while (i2 < 2) {
                int iM1146dp3 = iM1146dp2 + AndroidUtilities.m1146dp(i2 == 0 ? 0.0f : 32.0f);
                Theme.dialogs_onlineCirclePaint.setColor(Color.argb(90, iRed, iGreen, iBlue));
                float f = i * 2;
                int i3 = i2;
                canvas.drawRoundRect(iM1146dp - i, iM1146dp3 - i, iM1146dp + i, iM1146dp3 + i, ExteraConfig.getAvatarCorners(f, true), ExteraConfig.getAvatarCorners(f, true), Theme.dialogs_onlineCirclePaint);
                int i4 = 0;
                while (i4 < 2) {
                    Theme.dialogs_onlineCirclePaint.setColor(Color.argb(i4 == 0 ? 204 : 90, iRed, iGreen, iBlue));
                    int i5 = i4 * 10;
                    this.rect.set(AndroidUtilities.m1146dp(41.0f), iM1146dp3 - AndroidUtilities.m1146dp(7 - i5), getMeasuredWidth() - AndroidUtilities.m1146dp(i4 == 0 ? 70.0f : 55.0f), iM1146dp3 - AndroidUtilities.m1146dp(3 - i5));
                    canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(2.0f), AndroidUtilities.m1146dp(2.0f), Theme.dialogs_onlineCirclePaint);
                    i4++;
                }
                i2 = i3 + 1;
                iM1146dp2 = iM1146dp3;
            }
            Theme.dialogs_onlineCirclePaint.setColor(Theme.getColor(Theme.key_chats_actionBackground));
            this.rect.set(getMeasuredWidth() - AndroidUtilities.m1146dp(42.0f), getMeasuredHeight() - AndroidUtilities.m1146dp(12.0f), getMeasuredWidth() - AndroidUtilities.m1146dp(12.0f), getMeasuredHeight() - AndroidUtilities.m1146dp(42.0f));
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(this.squareFab ? 9.0f : 100.0f), AndroidUtilities.m1146dp(this.squareFab ? 9.0f : 100.0f), Theme.dialogs_onlineCirclePaint);
            Drawable drawable = ContextCompat.getDrawable(getContext(), C2369R.drawable.floating_pencil);
            drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), PorterDuff.Mode.MULTIPLY));
            drawable.setBounds(getMeasuredWidth() - AndroidUtilities.m1146dp(33.0f), getMeasuredHeight() - AndroidUtilities.m1146dp(32.5f), getMeasuredWidth() - AndroidUtilities.m1146dp(21.0f), getMeasuredHeight() - AndroidUtilities.m1146dp(20.5f));
            drawable.draw(canvas);
        }

        private void setProgress(float f) {
            this.progress = f;
            this.outlinePaint.setColor(ColorUtils.blendARGB(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), 63), Theme.getColor(Theme.key_windowBackgroundWhiteValueText), f));
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
                duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.exteragram.messenger.preferences.components.FabShapeCell$FabShape$$ExternalSyntheticLambda0
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
