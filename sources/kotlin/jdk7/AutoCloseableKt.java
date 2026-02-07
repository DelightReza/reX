package kotlin.jdk7;

import androidx.camera.camera2.impl.AbstractC0111xb15b98f9;
import kotlin.ExceptionsKt;

/* loaded from: classes.dex */
public abstract class AutoCloseableKt {
    public static final void closeFinally(AutoCloseable autoCloseable, Throwable th) throws Exception {
        if (autoCloseable != null) {
            if (th == null) {
                AbstractC0111xb15b98f9.m15m(autoCloseable);
                return;
            }
            try {
                AbstractC0111xb15b98f9.m15m(autoCloseable);
            } catch (Throwable th2) {
                ExceptionsKt.addSuppressed(th, th2);
            }
        }
    }
}
