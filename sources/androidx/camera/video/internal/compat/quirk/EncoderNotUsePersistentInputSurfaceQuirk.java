package androidx.camera.video.internal.compat.quirk;

import android.os.Build;
import androidx.camera.core.impl.Quirk;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes3.dex */
public class EncoderNotUsePersistentInputSurfaceQuirk implements Quirk {
    private static final List DEVICE_MODELS = Arrays.asList("SM-N9208", "SM-G920V");

    static boolean load() {
        return Build.VERSION.SDK_INT <= 22 || DEVICE_MODELS.contains(Build.MODEL.toUpperCase());
    }
}
