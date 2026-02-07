package androidx.camera.core.internal.compat;

import android.media.ImageWriter;
import android.os.Build;
import android.view.Surface;

/* loaded from: classes3.dex */
public abstract class ImageWriterCompat {
    public static ImageWriter newInstance(Surface surface, int i) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 23) {
            return ImageWriterCompatApi23Impl.newInstance(surface, i);
        }
        throw new RuntimeException("Unable to call newInstance(Surface, int) on API " + i2 + ". Version 23 or higher required.");
    }
}
