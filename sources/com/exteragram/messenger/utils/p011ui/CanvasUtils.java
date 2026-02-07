package com.exteragram.messenger.utils.p011ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.ExteraConfig;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.CombinedDrawable;

/* loaded from: classes.dex */
public final class CanvasUtils {
    public static final CanvasUtils INSTANCE = new CanvasUtils();
    private static final float[] nowPlayingPattern = {-5.5f, 20.0f, 20.0f, 0.35f, -5.5f, -20.0f, 20.0f, 0.35f, -36.0f, -42.0f, 22.0f, 0.375f, -36.0f, 0.0f, 25.0f, 0.425f, -36.0f, 42.0f, 22.0f, 0.375f, -70.0f, 22.0f, 23.0f, 0.35f, -70.0f, -22.0f, 23.0f, 0.35f, -99.0f, 46.0f, 21.0f, 0.275f, -99.0f, 0.0f, 22.0f, 0.325f, -99.0f, -46.0f, 21.0f, 0.275f, -128.0f, -23.0f, 20.0f, 0.225f, -128.0f, 23.0f, 20.0f, 0.225f};

    public static final Drawable createFabBackground() {
        return createFabBackground$default(0, 0, 0, 7, null);
    }

    public final int getAdaptedSurfaceColor() {
        return getAdaptedSurfaceColor$default(this, null, 1, null);
    }

    private CanvasUtils() {
    }

    public static /* synthetic */ Drawable createFabBackground$default(int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = 56;
        }
        if ((i4 & 2) != 0) {
            i2 = Theme.getColor(Theme.key_chats_actionBackground);
        }
        if ((i4 & 4) != 0) {
            i3 = Theme.getColor(Theme.key_chats_actionPressedBackground);
        }
        return createFabBackground(i, i2, i3);
    }

    public static final Drawable createFabBackground(int i, int i2, int i3) {
        Pair pairM1122to;
        if (i == 40) {
            int i4 = Theme.key_windowBackgroundWhite;
            pairM1122to = TuplesKt.m1122to(Integer.valueOf(ColorUtils.blendARGB(Theme.getColor(i4), -1, 0.1f)), Integer.valueOf(Theme.blendOver(Theme.getColor(i4), Theme.getColor(Theme.key_listSelector))));
        } else {
            pairM1122to = TuplesKt.m1122to(Integer.valueOf(i2), Integer.valueOf(i3));
        }
        Drawable drawableCreateSimpleSelectorRoundRectDrawable = Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(ExteraConfig.squareFab ? (float) Math.ceil((i * 16) / 56.0f) : i / 2.0f), ((Number) pairM1122to.component1()).intValue(), ((Number) pairM1122to.component2()).intValue());
        Intrinsics.checkNotNullExpressionValue(drawableCreateSimpleSelectorRoundRectDrawable, "createSimpleSelectorRoundRectDrawable(...)");
        return drawableCreateSimpleSelectorRoundRectDrawable;
    }

    public static final CombinedDrawable createCircleDrawableWithIcon(Context context, int i, int i2) {
        Drawable drawable;
        Intrinsics.checkNotNullParameter(context, "context");
        Drawable drawableMutate = null;
        if (i != 0 && (drawable = ContextCompat.getDrawable(context, i)) != null) {
            drawableMutate = drawable.mutate();
        }
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(-1);
        shapeDrawable.setIntrinsicWidth(i2);
        shapeDrawable.setIntrinsicHeight(i2);
        CombinedDrawable combinedDrawable = new CombinedDrawable(shapeDrawable, drawableMutate);
        combinedDrawable.setCustomSize(i2, i2);
        return combinedDrawable;
    }

    public static final Bitmap drawableToBitmap(Drawable drawable, int i, int i2) {
        Intrinsics.checkNotNullParameter(drawable, "drawable");
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    public static /* synthetic */ int adjustHsl$default(CanvasUtils canvasUtils, int i, float f, float f2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            f2 = -1.0f;
        }
        return canvasUtils.adjustHsl(i, f, f2);
    }

    public final int adjustHsl(int i, float f, float f2) {
        float[] fArr = new float[3];
        ColorUtils.colorToHSL(i, fArr);
        if (f2 > 0.0f) {
            fArr[1] = RangesKt.coerceAtMost(fArr[1] * f2, 1.0f);
        }
        fArr[2] = RangesKt.coerceAtMost(fArr[2] * f, 1.0f);
        return ColorUtils.HSLToColor(fArr);
    }

    public final void drawNowPlayingPattern(Canvas canvas, Drawable pattern, float f, float f2, float f3) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(pattern, "pattern");
        if (f3 <= 0.0f) {
            return;
        }
        int i = 0;
        while (true) {
            float[] fArr = nowPlayingPattern;
            if (i >= fArr.length) {
                return;
            }
            float f4 = fArr[i];
            float f5 = fArr[i + 1];
            float f6 = fArr[i + 2];
            float f7 = fArr[i + 3];
            float f8 = f2 / 2.0f;
            pattern.setBounds((int) ((AndroidUtilities.dpf2(f4) + f) - (AndroidUtilities.dpf2(f6) / 2.0f)), (int) ((AndroidUtilities.dpf2(f5) + f8) - (AndroidUtilities.dpf2(f6) / 2.0f)), (int) (AndroidUtilities.dpf2(f4) + f + (AndroidUtilities.dpf2(f6) / 2.0f)), (int) (f8 + AndroidUtilities.dpf2(f5) + (AndroidUtilities.dpf2(f6) / 2.0f)));
            pattern.setAlpha((int) (255 * f3 * f7));
            pattern.draw(canvas);
            i += 4;
        }
    }

    public static /* synthetic */ int getAdaptedSurfaceColor$default(CanvasUtils canvasUtils, Theme.ResourcesProvider resourcesProvider, int i, Object obj) {
        if ((i & 1) != 0) {
            resourcesProvider = null;
        }
        return canvasUtils.getAdaptedSurfaceColor(resourcesProvider);
    }

    public final int getAdaptedSurfaceColor(Theme.ResourcesProvider resourcesProvider) {
        int color = Theme.getColor(Theme.key_windowBackgroundWhite, resourcesProvider);
        return color != Theme.getColor(Theme.key_windowBackgroundGray, resourcesProvider) ? color : adaptColor(color, new Function1() { // from class: com.exteragram.messenger.utils.ui.CanvasUtils$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return CanvasUtils.getAdaptedSurfaceColor$lambda$4((float[]) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getAdaptedSurfaceColor$lambda$4(float[] hsv) {
        Intrinsics.checkNotNullParameter(hsv, "hsv");
        float f = hsv[2];
        hsv[2] = f > 0.5f ? RangesKt.coerceAtLeast(f - 0.03f, 0.0f) : RangesKt.coerceAtMost(f + 0.03f, 1.0f);
        return Unit.INSTANCE;
    }

    public final int adaptColor(int i, Function1 transform) {
        Intrinsics.checkNotNullParameter(transform, "transform");
        float[] fArr = new float[3];
        Color.colorToHSV(i, fArr);
        transform.invoke(fArr);
        return Color.HSVToColor(Color.alpha(i), fArr);
    }
}
