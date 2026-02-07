package org.telegram.p023ui.Components.blur3.drawable;

import android.graphics.Canvas;
import org.telegram.p023ui.Components.blur3.source.BlurredBackgroundSource;

/* loaded from: classes6.dex */
public class BlurredBackgroundDrawableSource extends BlurredBackgroundDrawable {
    private final BlurredBackgroundSource source;

    public BlurredBackgroundDrawableSource(BlurredBackgroundSource blurredBackgroundSource) {
        this.source = blurredBackgroundSource;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        drawSource(canvas, this.source);
    }

    @Override // org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawable
    public BlurredBackgroundSource getSource() {
        return this.source;
    }
}
