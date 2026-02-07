package androidx.camera.camera2.internal.compat.quirk;

import android.os.Build;
import androidx.camera.core.impl.Quirk;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class Nexus4AndroidLTargetAspectRatioQuirk implements Quirk {
    private static final List DEVICE_MODELS = Arrays.asList("NEXUS 4");

    public int getCorrectedAspectRatio() {
        return 2;
    }

    static boolean load() {
        return "GOOGLE".equalsIgnoreCase(Build.BRAND) && Build.VERSION.SDK_INT < 23 && DEVICE_MODELS.contains(Build.MODEL.toUpperCase(Locale.US));
    }
}
