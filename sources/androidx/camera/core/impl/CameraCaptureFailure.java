package androidx.camera.core.impl;

/* loaded from: classes3.dex */
public class CameraCaptureFailure {
    private final Reason mReason;

    public enum Reason {
        ERROR
    }

    public Object getCaptureFailure() {
        return null;
    }

    public CameraCaptureFailure(Reason reason) {
        this.mReason = reason;
    }
}
