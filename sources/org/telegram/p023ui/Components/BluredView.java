package org.telegram.p023ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import org.telegram.p023ui.ActionBar.Theme;

/* loaded from: classes6.dex */
public abstract class BluredView extends View {
    public final BlurBehindDrawable drawable;

    public BluredView(Context context, View view, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        BlurBehindDrawable blurBehindDrawable = new BlurBehindDrawable(view, this, 1, resourcesProvider);
        this.drawable = blurBehindDrawable;
        blurBehindDrawable.setAnimateAlpha(false);
        blurBehindDrawable.show(true);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) throws InterruptedException {
        this.drawable.draw(canvas);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) throws InterruptedException {
        super.onSizeChanged(i, i2, i3, i4);
        this.drawable.checkSizes();
    }

    public void update() {
        this.drawable.invalidate();
    }

    public boolean fullyDrawing() {
        return this.drawable.isFullyDrawing() && getVisibility() == 0;
    }
}
