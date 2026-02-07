package org.telegram.p023ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p023ui.ActionBar.Theme;

/* loaded from: classes3.dex */
public class ChoosingStickerStatusDrawable extends StatusDrawable {
    int color;
    Paint fillPaint;
    float progress;
    Paint strokePaint;
    private boolean isChat = false;
    private long lastUpdateTime = 0;
    private boolean started = false;
    boolean increment = true;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public ChoosingStickerStatusDrawable(boolean z) {
        if (z) {
            this.strokePaint = new Paint(1);
            this.fillPaint = new Paint(1);
            this.strokePaint.setStyle(Paint.Style.STROKE);
            this.strokePaint.setStrokeWidth(AndroidUtilities.dpf2(1.2f));
        }
    }

    @Override // org.telegram.p023ui.Components.StatusDrawable
    public void start() {
        this.lastUpdateTime = System.currentTimeMillis();
        this.started = true;
        invalidateSelf();
    }

    @Override // org.telegram.p023ui.Components.StatusDrawable
    public void stop() {
        this.started = false;
    }

    @Override // org.telegram.p023ui.Components.StatusDrawable
    public void setIsChat(boolean z) {
        this.isChat = z;
    }

    @Override // org.telegram.p023ui.Components.StatusDrawable
    public void setColor(int i) {
        if (this.color != i) {
            this.fillPaint.setColor(i);
            this.strokePaint.setColor(i);
        }
        this.color = i;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float fM1146dp;
        float interpolation;
        float fMin = Math.min(this.progress, 1.0f);
        float interpolation2 = CubicBezierInterpolator.EASE_IN.getInterpolation(fMin < 0.3f ? fMin / 0.3f : 1.0f);
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT;
        float interpolation3 = cubicBezierInterpolator.getInterpolation(fMin < 0.3f ? 0.0f : (fMin - 0.3f) / 0.7f);
        if (this.increment) {
            fM1146dp = (AndroidUtilities.m1146dp(2.1f) * interpolation2) + ((AndroidUtilities.m1146dp(7.0f) - AndroidUtilities.m1146dp(2.1f)) * (1.0f - interpolation2));
            interpolation = AndroidUtilities.dpf2(1.5f) * (1.0f - cubicBezierInterpolator.getInterpolation(this.progress / 2.0f));
        } else {
            fM1146dp = (AndroidUtilities.m1146dp(2.1f) * (1.0f - interpolation2)) + ((AndroidUtilities.m1146dp(7.0f) - AndroidUtilities.m1146dp(2.1f)) * interpolation2);
            interpolation = CubicBezierInterpolator.EASE_OUT_QUINT.getInterpolation(this.progress / 2.0f) * AndroidUtilities.dpf2(1.5f);
        }
        float fM1146dp2 = AndroidUtilities.m1146dp(11.0f) / 2.0f;
        float fDpf2 = AndroidUtilities.dpf2(2.0f);
        float fDpf22 = (AndroidUtilities.dpf2(0.5f) * interpolation2) - (AndroidUtilities.dpf2(0.5f) * interpolation3);
        Paint paint = this.strokePaint;
        if (paint == null) {
            paint = Theme.chat_statusRecordPaint;
        }
        Paint paint2 = this.fillPaint;
        if (paint2 == null) {
            paint2 = Theme.chat_statusPaint;
        }
        if (paint.getStrokeWidth() != AndroidUtilities.m1146dp(0.8f)) {
            paint.setStrokeWidth(AndroidUtilities.m1146dp(0.8f));
        }
        for (int i = 0; i < 2; i++) {
            canvas.save();
            canvas.translate((paint.getStrokeWidth() / 2.0f) + interpolation + (AndroidUtilities.m1146dp(9.0f) * i) + getBounds().left + AndroidUtilities.dpf2(0.2f), (paint.getStrokeWidth() / 2.0f) + AndroidUtilities.dpf2(2.0f) + getBounds().top);
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(0.0f, fDpf22, AndroidUtilities.m1146dp(7.0f), AndroidUtilities.m1146dp(11.0f) - fDpf22);
            canvas.drawOval(rectF, paint);
            canvas.drawCircle(fM1146dp, fM1146dp2, fDpf2, paint2);
            canvas.restore();
        }
        if (this.started) {
            update();
        }
    }

    private void update() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long j = jCurrentTimeMillis - this.lastUpdateTime;
        this.lastUpdateTime = jCurrentTimeMillis;
        if (j > 16) {
            j = 16;
        }
        float f = this.progress + (j / 500.0f);
        this.progress = f;
        if (f >= 2.0f) {
            this.progress = 0.0f;
            this.increment = !this.increment;
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return AndroidUtilities.m1146dp(20.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return AndroidUtilities.m1146dp(18.0f);
    }
}
