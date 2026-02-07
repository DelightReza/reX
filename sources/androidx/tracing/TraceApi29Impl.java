package androidx.tracing;

/* loaded from: classes.dex */
abstract class TraceApi29Impl {
    public static boolean isEnabled() {
        return android.os.Trace.isEnabled();
    }

    public static void setCounter(String str, int i) {
        android.os.Trace.setCounter(str, i);
    }
}
