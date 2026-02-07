package androidx.camera.core.impl;

import java.util.Set;

/* loaded from: classes3.dex */
public interface SessionProcessor {

    public interface CaptureCallback {
        void onCaptureCompleted(long j, int i, CameraCaptureResult cameraCaptureResult);

        void onCaptureFailed(int i);

        void onCaptureProcessProgressed(int i);

        void onCaptureProcessStarted(int i);

        void onCaptureSequenceAborted(int i);

        void onCaptureSequenceCompleted(int i);

        void onCaptureStarted(int i, long j);
    }

    Set getSupportedCameraOperations();
}
