package org.telegram.p023ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;

/* loaded from: classes6.dex */
public abstract class StickerMakerBackgroundView extends View {
    private final Paint backgroundPaint;
    private final Path path;

    public StickerMakerBackgroundView(Context context) {
        super(context);
        Paint paint = new Paint();
        this.backgroundPaint = paint;
        this.path = new Path();
        paint.setColor(-1);
        paint.setAlpha(40);
        setLayerType(2, null);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        Canvas canvas2;
        float fM1146dp = AndroidUtilities.m1146dp(10.0f);
        float f = fM1146dp * 2.0f;
        float measuredWidth = getMeasuredWidth() - f;
        float measuredHeight = getMeasuredHeight() - f;
        canvas.save();
        RectF rectF = AndroidUtilities.rectTmp;
        float f2 = fM1146dp + measuredWidth;
        rectF.set(fM1146dp, fM1146dp, f2, f2);
        rectF.offset(0.0f, (measuredHeight - rectF.height()) / 2.0f);
        float f3 = measuredWidth / 7.0f;
        this.path.rewind();
        this.path.addRoundRect(rectF, f3, f3, Path.Direction.CW);
        canvas.clipPath(this.path);
        int iM1146dp = AndroidUtilities.m1146dp(10.0f);
        canvas.save();
        canvas.translate(rectF.left, rectF.top);
        float f4 = iM1146dp;
        int iWidth = ((int) (rectF.width() / f4)) + 1;
        int iHeight = ((int) (rectF.height() / f4)) + 1;
        for (int i = 0; i < iHeight; i++) {
            canvas.save();
            int i2 = 0;
            while (i2 < iWidth) {
                int i3 = i2 % 2;
                if (!(i3 == 0 && i % 2 == 0) && (i3 == 0 || i % 2 == 0)) {
                    canvas2 = canvas;
                } else {
                    canvas2 = canvas;
                    canvas2.drawRect(0.0f, 0.0f, f4, f4, this.backgroundPaint);
                }
                canvas2.translate(f4, 0.0f);
                i2++;
                canvas = canvas2;
            }
            Canvas canvas3 = canvas;
            canvas3.restore();
            canvas3.translate(0.0f, f4);
        }
        Canvas canvas4 = canvas;
        canvas4.restore();
        canvas4.restore();
    }
}
