package androidx.camera.video.internal.compat.quirk;

import android.os.Build;
import androidx.camera.core.impl.Quirk;

/* loaded from: classes3.dex */
public class DeactivateEncoderSurfaceBeforeStopEncoderQuirk implements Quirk {
    static boolean load() {
        return Build.VERSION.SDK_INT <= 22;
    }
}
