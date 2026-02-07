package org.telegram.p023ui;

import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.view.Choreographer;
import android.view.View;
import androidx.core.math.MathUtils;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessageObject;

/* loaded from: classes5.dex */
public final class MultiLayoutTypingAnimator implements Choreographer.FrameCallback {
    private static final LinearGradient GRADIENT;
    private static final Matrix GRAD_MTX = new Matrix();
    private static final Paint MASK_PAINT;
    private View invalidateTarget;
    private Runnable onFinishRunnable;
    private final Choreographer choreo = Choreographer.getInstance();
    private ArrayList blocks = new ArrayList();
    private int curBlockIdx = 0;
    private int curLineIdx = 0;
    private float xPosition = 0.0f;
    private boolean running = false;
    private boolean finished = true;
    private long lastFrameNs = 0;
    private float speedPxPerSec = AndroidUtilities.m1146dp(40.0f);

    public void setBlocks(ArrayList arrayList) {
        if (!this.blocks.isEmpty() && this.curBlockIdx >= this.blocks.size()) {
            this.curBlockIdx = this.blocks.size() - 1;
            this.curLineIdx = Math.max(0, ((MessageObject.TextLayoutBlock) this.blocks.get(r0)).textLayout.getLineCount() - 1);
            this.xPosition = ((MessageObject.TextLayoutBlock) this.blocks.get(this.curBlockIdx)).textLayout.getLineWidth(this.curLineIdx);
        }
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        this.blocks = arrayList;
        recalcSpeed();
        boolean zIsAtAbsoluteEnd = isAtAbsoluteEnd();
        this.finished = zIsAtAbsoluteEnd;
        if (!zIsAtAbsoluteEnd && !this.running) {
            start();
        }
        View view = this.invalidateTarget;
        if (view != null) {
            view.invalidate();
        }
    }

    public void start() {
        if (this.running) {
            return;
        }
        this.running = true;
        if (isAtAbsoluteEnd()) {
            this.finished = true;
        }
        this.lastFrameNs = 0L;
        this.choreo.postFrameCallback(this);
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setOnFinishListener(Runnable runnable) {
        this.onFinishRunnable = runnable;
    }

    public int getFadeLineIndex(MessageObject.TextLayoutBlock textLayoutBlock) {
        if (isFadeBlock(textLayoutBlock)) {
            return this.curLineIdx;
        }
        return -1;
    }

    public float getFadeXPosition(MessageObject.TextLayoutBlock textLayoutBlock) {
        if (isFadeBlock(textLayoutBlock)) {
            return this.xPosition;
        }
        return 0.0f;
    }

    public boolean needDraw(MessageObject.TextLayoutBlock textLayoutBlock) {
        int iIndexOf = indexOf(textLayoutBlock);
        if (iIndexOf < 0 || this.blocks.isEmpty()) {
            return false;
        }
        int i = this.curBlockIdx;
        return iIndexOf < i || iIndexOf <= i;
    }

    public boolean isFadeBlock(MessageObject.TextLayoutBlock textLayoutBlock) {
        StaticLayout staticLayout;
        return indexOf(textLayoutBlock) == this.curBlockIdx && (staticLayout = textLayoutBlock.textLayout) != null && this.curLineIdx < staticLayout.getLineCount();
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long j) {
        if (this.running) {
            if (this.lastFrameNs != 0) {
                advance((j - r0) * 1.0E-9f);
            }
            this.lastFrameNs = j;
            View view = this.invalidateTarget;
            if (view != null) {
                view.invalidate();
            }
            if (this.finished) {
                this.running = false;
                Runnable runnable = this.onFinishRunnable;
                if (runnable != null) {
                    runnable.run();
                    this.onFinishRunnable = null;
                    return;
                }
                return;
            }
            this.choreo.postFrameCallback(this);
        }
    }

    private void advance(float f) {
        if (this.blocks.isEmpty() || f <= 0.0f) {
            this.finished = this.blocks.isEmpty();
            return;
        }
        float f2 = this.speedPxPerSec * f;
        while (true) {
            if (f2 <= 0.0f) {
                break;
            }
            if (this.curBlockIdx >= this.blocks.size()) {
                this.finished = true;
                break;
            }
            StaticLayout staticLayout = ((MessageObject.TextLayoutBlock) this.blocks.get(this.curBlockIdx)).textLayout;
            if (staticLayout == null || staticLayout.getLineCount() == 0) {
                this.curBlockIdx++;
                this.curLineIdx = 0;
                this.xPosition = 0.0f;
            } else {
                if (this.curLineIdx >= staticLayout.getLineCount()) {
                    int lineCount = staticLayout.getLineCount() - 1;
                    this.curLineIdx = lineCount;
                    this.xPosition = lineWidth(staticLayout, lineCount);
                }
                float fLineWidth = lineWidth(staticLayout, this.curLineIdx);
                if (fLineWidth <= 0.001f) {
                    if (nextLineOrBlock(staticLayout)) {
                        break;
                    }
                } else {
                    float f3 = this.xPosition;
                    float f4 = fLineWidth - f3;
                    if (f4 <= 0.001f) {
                        if (nextLineOrBlock(staticLayout)) {
                            break;
                        }
                    } else {
                        if (f2 < f4) {
                            f4 = f2;
                        }
                        float f5 = f3 + f4;
                        this.xPosition = f5;
                        f2 -= f4;
                        if (fLineWidth - f5 <= 0.001f && !nextLineOrBlock(staticLayout)) {
                            f2 = 0.0f;
                        }
                    }
                }
            }
        }
        this.finished = isAtAbsoluteEnd();
    }

    private boolean nextLineOrBlock(Layout layout) {
        int i = this.curLineIdx + 1;
        this.curLineIdx = i;
        this.xPosition = 0.0f;
        if (i >= layout.getLineCount()) {
            int i2 = this.curBlockIdx + 1;
            this.curBlockIdx = i2;
            this.curLineIdx = 0;
            this.xPosition = 0.0f;
            if (i2 >= this.blocks.size()) {
                return true;
            }
        }
        return false;
    }

    private void recalcSpeed() {
        float fComputeRemainingPixels = computeRemainingPixels();
        float fM1146dp = AndroidUtilities.m1146dp(40.0f);
        if (fComputeRemainingPixels <= 0.001f) {
            this.speedPxPerSec = fM1146dp;
        } else {
            this.speedPxPerSec = Math.max(fM1146dp, fComputeRemainingPixels / 1.05f);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x005a A[PHI: r5
      0x005a: PHI (r5v2 float) = (r5v1 float), (r5v1 float), (r5v3 float) binds: [B:21:0x004f, B:22:0x0051, B:24:0x0058] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private float computeRemainingPixels() {
        /*
            r8 = this;
            java.util.ArrayList r0 = r8.blocks
            boolean r0 = r0.isEmpty()
            r1 = 0
            if (r0 == 0) goto La
            return r1
        La:
            int r0 = r8.curBlockIdx
        Lc:
            java.util.ArrayList r2 = r8.blocks
            int r2 = r2.size()
            if (r0 >= r2) goto L61
            java.util.ArrayList r2 = r8.blocks
            java.lang.Object r2 = r2.get(r0)
            org.telegram.messenger.MessageObject$TextLayoutBlock r2 = (org.telegram.messenger.MessageObject.TextLayoutBlock) r2
            android.text.StaticLayout r2 = r2.textLayout
            if (r2 != 0) goto L21
            goto L5e
        L21:
            int r3 = r8.curBlockIdx
            r4 = 0
            if (r0 != r3) goto L3a
            int r3 = r8.curLineIdx
            int r3 = java.lang.Math.max(r3, r4)
            int r5 = r2.getLineCount()
            int r5 = r5 + (-1)
            int r4 = java.lang.Math.max(r4, r5)
            int r4 = java.lang.Math.min(r3, r4)
        L3a:
            r3 = r4
        L3b:
            int r5 = r2.getLineCount()
            if (r3 >= r5) goto L5e
            float r5 = r8.lineWidth(r2, r3)
            r6 = 981668463(0x3a83126f, float:0.001)
            int r7 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r7 > 0) goto L4d
            goto L5b
        L4d:
            int r7 = r8.curBlockIdx
            if (r0 != r7) goto L5a
            if (r3 != r4) goto L5a
            float r7 = r8.xPosition
            float r5 = r5 - r7
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 <= 0) goto L5b
        L5a:
            float r1 = r1 + r5
        L5b:
            int r3 = r3 + 1
            goto L3b
        L5e:
            int r0 = r0 + 1
            goto Lc
        L61:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.MultiLayoutTypingAnimator.computeRemainingPixels():float");
    }

    private boolean isAtAbsoluteEnd() {
        if (this.blocks.isEmpty()) {
            return true;
        }
        int size = this.blocks.size() - 1;
        StaticLayout staticLayout = null;
        while (size >= 0) {
            staticLayout = ((MessageObject.TextLayoutBlock) this.blocks.get(size)).textLayout;
            if (staticLayout != null && staticLayout.getLineCount() > 0) {
                break;
            }
            size--;
        }
        if (size < 0 || staticLayout == null) {
            return true;
        }
        int i = this.curBlockIdx;
        if (i < size) {
            return false;
        }
        if (i > size) {
            return true;
        }
        int lineCount = staticLayout.getLineCount() - 1;
        return this.curLineIdx >= lineCount && this.xPosition >= lineWidth(staticLayout, lineCount) - 0.001f;
    }

    private float lineWidth(Layout layout, int i) {
        float lineRight = layout.getLineRight(i) - layout.getLineLeft(i);
        return lineRight >= 0.0f ? lineRight : -lineRight;
    }

    public int indexOf(MessageObject.TextLayoutBlock textLayoutBlock) {
        int size = this.blocks.size();
        for (int i = 0; i < size; i++) {
            if (this.blocks.get(i) == textLayoutBlock) {
                return i;
            }
        }
        return -1;
    }

    static {
        Paint paint = new Paint(1);
        MASK_PAINT = paint;
        if (Build.VERSION.SDK_INT >= 29) {
            paint.setBlendMode(BlendMode.DST_IN);
        } else {
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 1.0f, 0.0f, -1, 16777215, Shader.TileMode.CLAMP);
        GRADIENT = linearGradient;
        paint.setShader(linearGradient);
    }

    public static void drawLayoutWithLastLineFade(Canvas canvas, Layout layout, int i, float f) {
        if (layout == null) {
            return;
        }
        int lineCount = layout.getLineCount();
        if (i < 0 || i >= lineCount) {
            return;
        }
        int width = layout.getWidth();
        layout.getHeight();
        int lineTop = layout.getLineTop(i);
        int lineBottom = layout.getLineBottom(i);
        if (lineTop > 0) {
            canvas.save();
            canvas.clipRect(0.0f, 0.0f, width, lineTop);
            layout.draw(canvas);
            canvas.restore();
        }
        float lineLeft = layout.getLineLeft(i);
        float lineRight = layout.getLineRight(i);
        float fMin = Math.min(lineLeft, lineRight);
        float fMax = Math.max(lineLeft, lineRight);
        if (fMax <= fMin) {
            return;
        }
        int paragraphDirection = layout.getParagraphDirection(i);
        float f2 = fMax - fMin;
        float fClamp = MathUtils.clamp(f, 0.0f, f2);
        float f3 = fClamp / f2;
        if (fClamp <= 0.0f) {
            return;
        }
        if (fClamp >= f2) {
            canvas.save();
            canvas.clipRect(0.0f, lineTop, width, lineBottom);
            layout.draw(canvas);
            canvas.restore();
            return;
        }
        float fLerp = AndroidUtilities.lerp(-AndroidUtilities.m1146dp(50.0f), f2, f3);
        AndroidUtilities.m1146dp(50.0f);
        float f4 = lineTop;
        float f5 = lineBottom;
        int iSaveLayer = canvas.saveLayer(fMin, f4, fMax, f5, null);
        canvas.save();
        canvas.clipRect(fMin, f4, fMax, f5);
        layout.draw(canvas);
        canvas.restore();
        Matrix matrix = GRAD_MTX;
        matrix.reset();
        if (paragraphDirection >= 0) {
            matrix.setScale(AndroidUtilities.m1146dp(50.0f), 1.0f);
            matrix.postTranslate(fLerp, 0.0f);
        } else {
            matrix.setScale(-AndroidUtilities.m1146dp(50.0f), 1.0f);
            matrix.postTranslate(f2 - fLerp, 0.0f);
        }
        GRADIENT.setLocalMatrix(matrix);
        canvas.drawRect(fMin, f4, fMax, f5, MASK_PAINT);
        canvas.restoreToCount(iSaveLayer);
    }
}
