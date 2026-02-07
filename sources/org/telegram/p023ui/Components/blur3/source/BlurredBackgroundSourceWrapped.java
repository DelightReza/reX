package org.telegram.p023ui.Components.blur3.source;

import android.graphics.Canvas;
import org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawable;
import org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawableSource;

/* loaded from: classes3.dex */
public class BlurredBackgroundSourceWrapped implements BlurredBackgroundSource {
    private BlurredBackgroundSource sourceInternal;

    @Override // org.telegram.p023ui.Components.blur3.source.BlurredBackgroundSource
    public BlurredBackgroundDrawable createDrawable() {
        return new BlurredBackgroundDrawableSource(this);
    }

    public BlurredBackgroundSource getSource() {
        return this.sourceInternal;
    }

    public void setSource(BlurredBackgroundSource blurredBackgroundSource) {
        this.sourceInternal = blurredBackgroundSource;
    }

    @Override // org.telegram.p023ui.Components.blur3.source.BlurredBackgroundSource
    public void draw(Canvas canvas, float f, float f2, float f3, float f4) {
        BlurredBackgroundSource blurredBackgroundSource = this.sourceInternal;
        if (blurredBackgroundSource != null) {
            blurredBackgroundSource.draw(canvas, f, f2, f3, f4);
        }
    }
}
