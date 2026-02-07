package androidx.camera.extensions.internal.compat.quirk;

import android.os.Build;
import androidx.camera.core.impl.Quirk;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt;

/* loaded from: classes3.dex */
public final class AvoidCaptureProcessProgressAvailabilityCheckQuirk implements Quirk {
    public static final Companion Companion = new Companion(null);

    public static final boolean load() {
        return Companion.load();
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean load() {
            return StringsKt.equals("Xiaomi", Build.BRAND, true) && StringsKt.equals("dada", Build.DEVICE, true);
        }
    }
}
