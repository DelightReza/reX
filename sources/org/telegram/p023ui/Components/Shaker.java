package org.telegram.p023ui.Components;

import android.graphics.Canvas;
import android.view.View;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Utilities;
import p017j$.util.Objects;

/* loaded from: classes6.dex */
public class Shaker {
    private final Runnable invalidate;

    /* renamed from: r */
    private final float f1994r;
    private final long start;

    /* renamed from: sx */
    private final float f1995sx;

    /* renamed from: sy */
    private final float f1996sy;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public Shaker(View view) {
        this(new Shaker$$ExternalSyntheticLambda0(view));
        Objects.requireNonNull(view);
    }

    public Shaker(Runnable runnable) {
        this.start = System.currentTimeMillis();
        this.invalidate = runnable;
        this.f1994r = AndroidUtilities.lerp(5.0f, 9.0f, Utilities.clamp01(Utilities.fastRandom.nextFloat()));
        this.f1995sx = AndroidUtilities.lerp(2.5f, 5.0f, Utilities.clamp01(Utilities.fastRandom.nextFloat()));
        this.f1996sy = AndroidUtilities.lerp(2.5f, 5.2f, Utilities.clamp01(Utilities.fastRandom.nextFloat()));
    }

    public void concat(Canvas canvas, float f) {
        concat(canvas, f, 0.0f, 0.0f);
    }

    public void concat(Canvas canvas, float f, float f2, float f3) {
        Runnable runnable;
        float fCurrentTimeMillis = (System.currentTimeMillis() - this.start) / 1000.0f;
        canvas.translate(f2, f3);
        canvas.rotate(((float) Math.sin(this.f1994r * fCurrentTimeMillis * 3.141592653589793d)) * 1.0f * f);
        canvas.translate(((float) Math.cos(this.f1995sx * fCurrentTimeMillis * 3.141592653589793d)) * AndroidUtilities.m1146dp(0.5f) * f, ((float) Math.sin(fCurrentTimeMillis * this.f1996sy * 3.141592653589793d)) * AndroidUtilities.m1146dp(0.5f) * f);
        canvas.translate(-f2, -f3);
        if (f <= 0.0f || (runnable = this.invalidate) == null) {
            return;
        }
        runnable.run();
    }
}
