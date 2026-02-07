package androidx.camera.extensions.internal;

/* loaded from: classes3.dex */
public final class Camera2ExtensionsUtil {
    public static final Camera2ExtensionsUtil INSTANCE = new Camera2ExtensionsUtil();

    public static final boolean shouldUseCamera2Extensions(int i) {
        return i == 1;
    }

    private Camera2ExtensionsUtil() {
    }
}
