package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.view.Surface;
import androidx.camera.camera2.internal.compat.CameraCaptureSessionCompat;
import androidx.camera.camera2.internal.compat.params.SessionConfigurationCompat;
import androidx.camera.core.impl.Quirks;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;

/* loaded from: classes3.dex */
public interface SynchronizedCaptureSession {

    public interface Opener {
        SessionConfigurationCompat createSessionConfigurationCompat(int i, List list, StateCallback stateCallback);

        Executor getExecutor();

        ListenableFuture openCaptureSession(CameraDevice cameraDevice, SessionConfigurationCompat sessionConfigurationCompat, List list);

        ListenableFuture startWithDeferrableSurface(List list, long j);

        boolean stop();
    }

    public static abstract class StateCallback {
        void onActive(SynchronizedCaptureSession synchronizedCaptureSession) {
        }

        void onCaptureQueueEmpty(SynchronizedCaptureSession synchronizedCaptureSession) {
        }

        public void onClosed(SynchronizedCaptureSession synchronizedCaptureSession) {
        }

        public abstract void onConfigureFailed(SynchronizedCaptureSession synchronizedCaptureSession);

        abstract void onConfigured(SynchronizedCaptureSession synchronizedCaptureSession);

        abstract void onReady(SynchronizedCaptureSession synchronizedCaptureSession);

        abstract void onSessionFinished(SynchronizedCaptureSession synchronizedCaptureSession);

        void onSurfacePrepared(SynchronizedCaptureSession synchronizedCaptureSession, Surface surface) {
        }
    }

    void abortCaptures();

    int captureBurstRequests(List list, CameraCaptureSession.CaptureCallback captureCallback);

    void close();

    List createHighSpeedRequestList(CaptureRequest captureRequest);

    void finishClose();

    CameraDevice getDevice();

    ListenableFuture getOpeningBlocker();

    StateCallback getStateCallback();

    void onCameraDeviceError(int i);

    int setRepeatingBurstRequests(List list, CameraCaptureSession.CaptureCallback captureCallback);

    int setSingleRepeatingRequest(CaptureRequest captureRequest, CameraCaptureSession.CaptureCallback captureCallback);

    void stopRepeating();

    CameraCaptureSessionCompat toCameraCaptureSessionCompat();

    public static class OpenerBuilder {
        private final Quirks mCameraQuirks;
        private final CaptureSessionRepository mCaptureSessionRepository;
        private final Handler mCompatHandler;
        private final Quirks mDeviceQuirks;
        private final Executor mExecutor;
        private final ScheduledExecutorService mScheduledExecutorService;

        OpenerBuilder(Executor executor, ScheduledExecutorService scheduledExecutorService, Handler handler, CaptureSessionRepository captureSessionRepository, Quirks quirks, Quirks quirks2) {
            this.mExecutor = executor;
            this.mScheduledExecutorService = scheduledExecutorService;
            this.mCompatHandler = handler;
            this.mCaptureSessionRepository = captureSessionRepository;
            this.mCameraQuirks = quirks;
            this.mDeviceQuirks = quirks2;
        }

        Opener build() {
            return new SynchronizedCaptureSessionImpl(this.mCameraQuirks, this.mDeviceQuirks, this.mCaptureSessionRepository, this.mExecutor, this.mScheduledExecutorService, this.mCompatHandler);
        }
    }
}
