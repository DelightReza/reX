package kotlin.p019io;

import java.io.Closeable;
import java.io.IOException;
import kotlin.ExceptionsKt;

/* loaded from: classes.dex */
public abstract class CloseableKt {
    public static final void closeFinally(Closeable closeable, Throwable th) throws IOException {
        if (closeable != null) {
            if (th == null) {
                closeable.close();
                return;
            }
            try {
                closeable.close();
            } catch (Throwable th2) {
                ExceptionsKt.addSuppressed(th, th2);
            }
        }
    }
}
