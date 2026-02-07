package org.telegram.p023ui.Components;

import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.animation.DecelerateInterpolator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p023ui.ActionBar.Theme;

/* loaded from: classes3.dex */
public class MediaActionDrawable extends Drawable {
    private float animatedDownloadProgress;
    private boolean animatingTransition;
    private ColorFilter colorFilter;
    private int currentIcon;
    private MediaActionDrawableDelegate delegate;
    private float downloadProgress;
    private float downloadProgressAnimationStart;
    private float downloadProgressTime;
    private float downloadRadOffset;
    private LinearGradient gradientDrawable;
    private Matrix gradientMatrix;
    private boolean hasOverlayImage;
    private boolean isMini;
    private long lastAnimationTime;
    private Theme.MessageDrawable messageDrawable;
    private int nextIcon;
    private String percentString;
    private int percentStringWidth;
    private float savedTransitionProgress;
    private TextPaint textPaint = new TextPaint(1);
    public Paint paint = new Paint(1);
    private Paint backPaint = new Paint(1);
    public Paint paint2 = new Paint(1);
    private Paint paint3 = new Paint(1);
    private RectF rect = new RectF();
    private float scale = 1.0f;
    private DecelerateInterpolator interpolator = new DecelerateInterpolator();
    private float transitionAnimationTime = 400.0f;
    private int lastPercent = -1;
    private float overrideAlpha = 1.0f;
    private float transitionProgress = 1.0f;

    public interface MediaActionDrawableDelegate {
        void invalidate();
    }

    public static float getCircleValue(float f) {
        while (f > 360.0f) {
            f -= 360.0f;
        }
        return f;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    public MediaActionDrawable() {
        this.paint.setColor(-1);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.paint.setStrokeWidth(AndroidUtilities.m1146dp(3.0f));
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint3.setColor(-1);
        this.textPaint.setTypeface(AndroidUtilities.bold());
        this.textPaint.setTextSize(AndroidUtilities.m1146dp(13.0f));
        this.textPaint.setColor(-1);
        this.paint2.setColor(-1);
    }

    public void setOverrideAlpha(float f) {
        this.overrideAlpha = f;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        this.paint2.setColorFilter(colorFilter);
        this.paint3.setColorFilter(colorFilter);
        this.textPaint.setColorFilter(colorFilter);
    }

    public void setColor(int i) {
        int i2 = (-16777216) | i;
        this.paint.setColor(i2);
        this.paint2.setColor(i2);
        this.paint3.setColor(i2);
        this.textPaint.setColor(i2);
        this.colorFilter = new PorterDuffColorFilter(i, PorterDuff.Mode.MULTIPLY);
    }

    public void setBackColor(int i) {
        this.backPaint.setColor(i | (-16777216));
    }

    public void setMini(boolean z) {
        this.isMini = z;
        this.paint.setStrokeWidth(AndroidUtilities.m1146dp(z ? 2.0f : 3.0f));
    }

    public void setDelegate(MediaActionDrawableDelegate mediaActionDrawableDelegate) {
        this.delegate = mediaActionDrawableDelegate;
    }

    public boolean setIcon(int i, boolean z) {
        int i2;
        int i3;
        if (this.currentIcon == i && (i3 = this.nextIcon) != i) {
            this.currentIcon = i3;
            this.transitionProgress = 1.0f;
        }
        if (z) {
            int i4 = this.currentIcon;
            if (i4 == i || (i2 = this.nextIcon) == i) {
                return false;
            }
            if ((i4 == 0 && i == 1) || (i4 == 1 && i == 0)) {
                this.transitionAnimationTime = 300.0f;
            } else if (i4 == 2 && (i == 3 || i == 14)) {
                this.transitionAnimationTime = 400.0f;
            } else if (i4 != 4 && i == 6) {
                this.transitionAnimationTime = 360.0f;
            } else if ((i4 == 4 && i == 14) || (i4 == 14 && i == 4)) {
                this.transitionAnimationTime = 160.0f;
            } else {
                this.transitionAnimationTime = 220.0f;
            }
            if (this.animatingTransition) {
                this.currentIcon = i2;
            }
            this.animatingTransition = true;
            this.nextIcon = i;
            this.savedTransitionProgress = this.transitionProgress;
            this.transitionProgress = 0.0f;
        } else {
            if (this.currentIcon == i) {
                return false;
            }
            this.animatingTransition = false;
            this.nextIcon = i;
            this.currentIcon = i;
            this.savedTransitionProgress = this.transitionProgress;
            this.transitionProgress = 1.0f;
        }
        if (i == 3 || i == 14) {
            this.downloadRadOffset = 112.0f;
            this.animatedDownloadProgress = 0.0f;
            this.downloadProgressAnimationStart = 0.0f;
            this.downloadProgressTime = 0.0f;
        }
        invalidateSelf();
        return true;
    }

    public int getCurrentIcon() {
        return this.nextIcon;
    }

    public int getPreviousIcon() {
        return this.currentIcon;
    }

    public void setProgress(float f, boolean z) {
        if (this.downloadProgress == f) {
            return;
        }
        if (!z) {
            this.animatedDownloadProgress = f;
            this.downloadProgressAnimationStart = f;
        } else {
            if (this.animatedDownloadProgress > f) {
                this.animatedDownloadProgress = f;
            }
            this.downloadProgressAnimationStart = this.animatedDownloadProgress;
        }
        this.downloadProgress = f;
        this.downloadProgressTime = 0.0f;
        invalidateSelf();
    }

    public float getProgress() {
        return this.downloadProgress;
    }

    public float getTransitionProgress() {
        if (this.animatingTransition) {
            return this.transitionProgress;
        }
        return 1.0f;
    }

    public void setBackgroundDrawable(Theme.MessageDrawable messageDrawable) {
        this.messageDrawable = messageDrawable;
    }

    public void setBackgroundGradientDrawable(LinearGradient linearGradient) {
        this.gradientDrawable = linearGradient;
        this.gradientMatrix = new Matrix();
    }

    public void setHasOverlayImage(boolean z) {
        this.hasOverlayImage = z;
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        float intrinsicWidth = (i3 - i) / getIntrinsicWidth();
        this.scale = intrinsicWidth;
        if (intrinsicWidth < 0.7f) {
            this.paint.setStrokeWidth(AndroidUtilities.m1146dp(2.0f));
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        super.invalidateSelf();
        MediaActionDrawableDelegate mediaActionDrawableDelegate = this.delegate;
        if (mediaActionDrawableDelegate != null) {
            mediaActionDrawableDelegate.invalidate();
        }
    }

    public void applyShaderMatrix(boolean z) {
        Theme.MessageDrawable messageDrawable = this.messageDrawable;
        if (messageDrawable == null || !messageDrawable.hasGradient() || this.hasOverlayImage) {
            return;
        }
        Rect bounds = getBounds();
        Shader gradientShader = this.messageDrawable.getGradientShader();
        Matrix matrix = this.messageDrawable.getMatrix();
        matrix.reset();
        this.messageDrawable.applyMatrixScale();
        if (z) {
            matrix.postTranslate(-bounds.centerX(), (-this.messageDrawable.getTopY()) + bounds.top);
        } else {
            matrix.postTranslate(0.0f, -this.messageDrawable.getTopY());
        }
        gradientShader.setLocalMatrix(matrix);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0327  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x04f2  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x04fc  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x0506  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x057c  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0582  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0587  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x058f  */
    /* JADX WARN: Removed duplicated region for block: B:213:0x059c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:216:0x05aa  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x05ad  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x05ce  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x05d5  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0606  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x060b  */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0639  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x063d  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x064a  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x064f  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0658  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0660  */
    /* JADX WARN: Removed duplicated region for block: B:261:0x066f  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x0673  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x0682 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0684  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0697  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x069a  */
    /* JADX WARN: Removed duplicated region for block: B:277:0x06b3  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x06fa  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0703  */
    /* JADX WARN: Removed duplicated region for block: B:285:0x0707  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0718  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x071d  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x072e  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0731  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x0743  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x077b  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0784  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x0788  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x079a  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x079d  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x07c5  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x07df  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x0813  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x081a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:335:0x0822  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x0844  */
    /* JADX WARN: Removed duplicated region for block: B:350:0x0847  */
    /* JADX WARN: Removed duplicated region for block: B:355:0x0850 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:358:0x0855 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:371:0x08a9  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x08ac  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x08b5  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x08e0  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x090c  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x0939  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x093d  */
    /* JADX WARN: Removed duplicated region for block: B:411:0x094b  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x0976  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x09a6  */
    /* JADX WARN: Removed duplicated region for block: B:428:0x09dd  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x09e8  */
    /* JADX WARN: Removed duplicated region for block: B:432:0x0a04  */
    /* JADX WARN: Removed duplicated region for block: B:434:0x0a08 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:437:0x0a25  */
    /* JADX WARN: Removed duplicated region for block: B:438:0x0a28  */
    /* JADX WARN: Removed duplicated region for block: B:441:0x0a48  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x0a86 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:451:0x0a9b  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x0a9e  */
    /* JADX WARN: Removed duplicated region for block: B:455:0x0ac7  */
    /* JADX WARN: Removed duplicated region for block: B:458:0x0ad0  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x0ada  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x0b66  */
    /* JADX WARN: Removed duplicated region for block: B:485:0x0b6e  */
    /* JADX WARN: Removed duplicated region for block: B:495:0x0b81  */
    /* JADX WARN: Removed duplicated region for block: B:497:0x0b9a  */
    /* JADX WARN: Removed duplicated region for block: B:506:0x0bcd  */
    /* JADX WARN: Removed duplicated region for block: B:514:0x0bf0  */
    /* JADX WARN: Removed duplicated region for block: B:516:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v13, types: [boolean] */
    /* JADX WARN: Type inference failed for: r13v14 */
    /* JADX WARN: Type inference failed for: r13v25 */
    /* JADX WARN: Type inference failed for: r13v26 */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void draw(android.graphics.Canvas r39) {
        /*
            Method dump skipped, instructions count: 3060
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.MediaActionDrawable.draw(android.graphics.Canvas):void");
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return AndroidUtilities.m1146dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return AndroidUtilities.m1146dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return AndroidUtilities.m1146dp(48.0f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return AndroidUtilities.m1146dp(48.0f);
    }
}
