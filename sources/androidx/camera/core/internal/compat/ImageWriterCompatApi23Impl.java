package androidx.camera.core.internal.compat;

import android.media.ImageWriter;
import android.view.Surface;

/* loaded from: classes3.dex */
abstract class ImageWriterCompatApi23Impl {
    static ImageWriter newInstance(Surface surface, int i) {
        return ImageWriter.newInstance(surface, i);
    }
}
