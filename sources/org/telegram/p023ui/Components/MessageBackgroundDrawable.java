package org.telegram.p023ui.Components;

import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes3.dex */
public class MessageBackgroundDrawable extends Drawable {
    private boolean animationInProgress;
    private float currentAnimationProgress;
    private float finalRadius;
    private boolean isSelected;
    private long lastAnimationTime;
    private long lastTouchTime;
    private View parentView;
    private Paint paint = new Paint(1);
    private Paint customPaint = null;
    private float touchX = -1.0f;
    private float touchY = -1.0f;
    private float touchOverrideX = -1.0f;
    private float touchOverrideY = -1.0f;

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    public MessageBackgroundDrawable(View view) {
        this.parentView = view;
    }

    public void setColor(int i) {
        this.paint.setColor(i);
    }

    public void setCustomPaint(Paint paint) {
        this.customPaint = paint;
    }

    public void setSelected(boolean z, boolean z2) {
        if (this.isSelected == z) {
            if (this.animationInProgress == z2 || z2) {
                return;
            }
            this.currentAnimationProgress = z ? 1.0f : 0.0f;
            this.animationInProgress = false;
            return;
        }
        this.isSelected = z;
        this.animationInProgress = z2;
        if (z2) {
            this.lastAnimationTime = SystemClock.elapsedRealtime();
        } else {
            this.currentAnimationProgress = z ? 1.0f : 0.0f;
        }
        calcRadius();
        invalidate();
    }

    private void invalidate() {
        View view = this.parentView;
        if (view != null) {
            view.invalidate();
            if (this.parentView.getParent() != null) {
                ((ViewGroup) this.parentView.getParent()).invalidate();
            }
        }
    }

    private void calcRadius() {
        Rect bounds = getBounds();
        float fCenterX = bounds.centerX();
        float fCenterY = bounds.centerY();
        int i = bounds.left;
        int i2 = bounds.top;
        this.finalRadius = (float) Math.ceil(Math.sqrt(((i - fCenterX) * (i - fCenterX)) + ((i2 - fCenterY) * (i2 - fCenterY))));
    }

    public void setTouchCoords(float f, float f2) {
        this.touchX = f;
        this.touchY = f2;
        this.lastTouchTime = SystemClock.elapsedRealtime();
    }

    public void setTouchCoordsOverride(float f, float f2) {
        this.touchOverrideX = f;
        this.touchOverrideY = f2;
    }

    public float getTouchX() {
        return this.touchX;
    }

    public float getTouchY() {
        return this.touchY;
    }

    public long getLastTouchTime() {
        return this.lastTouchTime;
    }

    public boolean isAnimationInProgress() {
        return this.animationInProgress;
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        calcRadius();
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(Rect rect) {
        super.setBounds(rect);
        calcRadius();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.paint.setAlpha(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0059  */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void draw(android.graphics.Canvas r10) {
        /*
            r9 = this;
            float r0 = r9.currentAnimationProgress
            r1 = 0
            r2 = 1065353216(0x3f800000, float:1.0)
            int r3 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r3 != 0) goto L18
            android.graphics.Rect r0 = r9.getBounds()
            android.graphics.Paint r3 = r9.customPaint
            if (r3 == 0) goto L12
            goto L14
        L12:
            android.graphics.Paint r3 = r9.paint
        L14:
            r10.drawRect(r0, r3)
            goto L73
        L18:
            int r3 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r3 == 0) goto L73
            boolean r3 = r9.isSelected
            if (r3 == 0) goto L27
            org.telegram.ui.Components.CubicBezierInterpolator r3 = org.telegram.p023ui.Components.CubicBezierInterpolator.EASE_OUT_QUINT
            float r0 = r3.getInterpolation(r0)
            goto L31
        L27:
            org.telegram.ui.Components.CubicBezierInterpolator r3 = org.telegram.p023ui.Components.CubicBezierInterpolator.EASE_OUT_QUINT
            float r0 = r2 - r0
            float r0 = r3.getInterpolation(r0)
            float r0 = r2 - r0
        L31:
            android.graphics.Rect r3 = r9.getBounds()
            int r4 = r3.centerX()
            float r4 = (float) r4
            int r3 = r3.centerY()
            float r3 = (float) r3
            float r5 = r9.touchOverrideX
            int r6 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r6 < 0) goto L4c
            float r6 = r9.touchOverrideY
            int r7 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r7 < 0) goto L4c
            goto L5b
        L4c:
            float r5 = r9.touchX
            int r6 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r6 < 0) goto L59
            float r6 = r9.touchY
            int r7 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r7 < 0) goto L59
            goto L5b
        L59:
            r6 = r3
            r5 = r4
        L5b:
            float r7 = r2 - r0
            float r5 = r5 - r4
            float r5 = r5 * r7
            float r4 = r4 + r5
            float r6 = r6 - r3
            float r7 = r7 * r6
            float r3 = r3 + r7
            float r5 = r9.finalRadius
            float r5 = r5 * r0
            android.graphics.Paint r0 = r9.customPaint
            if (r0 == 0) goto L6e
            goto L70
        L6e:
            android.graphics.Paint r0 = r9.paint
        L70:
            r10.drawCircle(r4, r3, r5, r0)
        L73:
            boolean r10 = r9.animationInProgress
            if (r10 == 0) goto Lba
            long r3 = android.os.SystemClock.elapsedRealtime()
            long r5 = r9.lastAnimationTime
            long r5 = r3 - r5
            r7 = 20
            int r10 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r10 <= 0) goto L87
            r5 = 17
        L87:
            r9.lastAnimationTime = r3
            boolean r10 = r9.isSelected
            r0 = 1131413504(0x43700000, float:240.0)
            if (r10 == 0) goto L9d
            float r10 = r9.currentAnimationProgress
            float r1 = (float) r5
            float r1 = r1 / r0
            float r10 = r10 + r1
            r9.currentAnimationProgress = r10
            int r10 = (r10 > r2 ? 1 : (r10 == r2 ? 0 : -1))
            if (r10 < 0) goto Lb7
            r9.currentAnimationProgress = r2
            goto Laa
        L9d:
            float r10 = r9.currentAnimationProgress
            float r2 = (float) r5
            float r2 = r2 / r0
            float r10 = r10 - r2
            r9.currentAnimationProgress = r10
            int r10 = (r10 > r1 ? 1 : (r10 == r1 ? 0 : -1))
            if (r10 > 0) goto Lb7
            r9.currentAnimationProgress = r1
        Laa:
            r10 = -1082130432(0xffffffffbf800000, float:-1.0)
            r9.touchX = r10
            r9.touchY = r10
            r9.touchOverrideX = r10
            r9.touchOverrideY = r10
            r10 = 0
            r9.animationInProgress = r10
        Lb7:
            r9.invalidate()
        Lba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.MessageBackgroundDrawable.draw(android.graphics.Canvas):void");
    }
}
