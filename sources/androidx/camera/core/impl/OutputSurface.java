package androidx.camera.core.impl;

import android.util.Size;
import android.view.Surface;

/* loaded from: classes3.dex */
public abstract class OutputSurface {
    public abstract int getImageFormat();

    public abstract Size getSize();

    public abstract Surface getSurface();
}
