package com.exteragram.messenger.preferences.utils;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.TextUtils;
import androidx.core.graphics.PathParser;
import com.exteragram.messenger.ExteraConfig;
import kotlin.jvm.internal.Intrinsics;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;

/* loaded from: classes3.dex */
public final class IconShapeHelper {
    public static final IconShapeHelper INSTANCE = new IconShapeHelper();
    private static boolean cacheInitialized;
    private static Path cachedIconMask;

    private IconShapeHelper() {
    }

    private final Path getIconShapePath() throws Resources.NotFoundException {
        Path iconMask;
        if (cacheInitialized) {
            Path path = cachedIconMask;
            return path != null ? new Path(path) : getDefaultPath();
        }
        try {
            ColorDrawable colorDrawable = new ColorDrawable(0);
            IconShapeHelper$$ExternalSyntheticApiModelOutline1.m228m();
            iconMask = IconShapeHelper$$ExternalSyntheticApiModelOutline0.m227m(colorDrawable, colorDrawable).getIconMask();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        if (iconMask != null && !iconMask.isEmpty()) {
            cachedIconMask = new Path(iconMask);
            cacheInitialized = true;
            return new Path(iconMask);
        }
        Resources system = Resources.getSystem();
        int identifier = system.getIdentifier("config_icon_mask", "string", "android");
        if (identifier != 0) {
            String string = system.getString(identifier);
            Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
            if (!TextUtils.isEmpty(string)) {
                Path pathCreatePathFromPathData = PathParser.createPathFromPathData(string);
                Intrinsics.checkNotNullExpressionValue(pathCreatePathFromPathData, "createPathFromPathData(...)");
                if (!pathCreatePathFromPathData.isEmpty()) {
                    cachedIconMask = new Path(pathCreatePathFromPathData);
                    cacheInitialized = true;
                    return new Path(pathCreatePathFromPathData);
                }
            }
        }
        cacheInitialized = true;
        cachedIconMask = null;
        return getDefaultPath();
    }

    private final Path getDefaultPath() {
        Path pathCreatePathFromPathData = PathParser.createPathFromPathData("M50,0A50,50,0,0,1,50,100A50,50,0,0,1,50,0");
        Intrinsics.checkNotNullExpressionValue(pathCreatePathFromPathData, "createPathFromPathData(...)");
        return pathCreatePathFromPathData;
    }

    private final Path resizePath(Path path, float f, float f2) {
        if (path == null || path.isEmpty() || f <= 0.0f || f2 <= 0.0f) {
            return new Path();
        }
        RectF rectF = new RectF();
        path.computeBounds(rectF, true);
        if (rectF.width() <= 0.0f || rectF.height() <= 0.0f) {
            return new Path();
        }
        Matrix matrix = new Matrix();
        matrix.setRectToRect(rectF, new RectF(0.0f, 0.0f, f, f2), Matrix.ScaleToFit.FILL);
        Path path2 = new Path();
        path.transform(matrix, path2);
        return path2;
    }

    public final Path getFinalIconShapePath(float f, float f2, float f3) {
        Path defaultPath = (Build.VERSION.SDK_INT < 26 || !ExteraConfig.useSystemIconShape) ? getDefaultPath() : getIconShapePath();
        RectF rectF = new RectF();
        defaultPath.computeBounds(rectF, true);
        if (f3 > 0.0f && shouldUseRoundedRect(defaultPath, rectF)) {
            Path path = new Path();
            path.addRoundRect(new RectF(0.0f, 0.0f, AndroidUtilities.dpf2(f), AndroidUtilities.dpf2(f2)), AndroidUtilities.dpf2(f3), AndroidUtilities.dpf2(f3), Path.Direction.CW);
            return path;
        }
        return resizePath(defaultPath, AndroidUtilities.dpf2(f), AndroidUtilities.dpf2(f2));
    }

    private final boolean shouldUseRoundedRect(Path path, RectF rectF) {
        if (path.isEmpty()) {
            return false;
        }
        Region region = new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        Region region2 = new Region();
        region2.setPath(path, region);
        if (region2.isRect()) {
            return true;
        }
        return hasSharpCorners(path, rectF);
    }

    private final boolean hasSharpCorners(Path path, RectF rectF) {
        if (rectF.width() <= 0.0f || rectF.height() <= 0.0f) {
            return false;
        }
        int iMax = Math.max(3, (int) (Math.min(rectF.width(), rectF.height()) * 0.1f));
        Region region = new Region();
        region.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        float f = rectF.left;
        float f2 = rectF.top;
        Rect rect = new Rect((int) f, (int) f2, ((int) f) + iMax, ((int) f2) + iMax);
        float f3 = rectF.right;
        float f4 = rectF.top;
        Rect rect2 = new Rect(((int) f3) - iMax, (int) f4, (int) f3, ((int) f4) + iMax);
        float f5 = rectF.right;
        float f6 = rectF.bottom;
        Rect rect3 = new Rect(((int) f5) - iMax, ((int) f6) - iMax, (int) f5, (int) f6);
        float f7 = rectF.left;
        float f8 = rectF.bottom;
        Rect[] rectArr = {rect, rect2, rect3, new Rect((int) f7, ((int) f8) - iMax, ((int) f7) + iMax, (int) f8)};
        for (int i = 0; i < 4; i++) {
            if (!new Region(region).op(rectArr[i], Region.Op.INTERSECT)) {
                return false;
            }
        }
        return true;
    }
}
