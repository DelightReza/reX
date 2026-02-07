package org.telegram.p023ui.Components.blur3.source;

import android.graphics.Canvas;
import org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawable;

/* loaded from: classes6.dex */
public interface BlurredBackgroundSource {
    BlurredBackgroundDrawable createDrawable();

    void draw(Canvas canvas, float f, float f2, float f3, float f4);
}
