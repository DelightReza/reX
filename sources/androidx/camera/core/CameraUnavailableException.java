package androidx.camera.core;

/* loaded from: classes3.dex */
public class CameraUnavailableException extends Exception {
    private final int mReason;

    public CameraUnavailableException(int i, String str) {
        super(str);
        this.mReason = i;
    }

    public CameraUnavailableException(int i, Throwable th) {
        super(th);
        this.mReason = i;
    }
}
