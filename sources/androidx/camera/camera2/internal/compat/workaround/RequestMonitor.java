package androidx.camera.camera2.internal.compat.workaround;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CaptureFailure;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Log;
import androidx.arch.core.util.Function;
import androidx.camera.camera2.internal.Camera2CaptureCallbacks;
import androidx.camera.camera2.internal.compat.workaround.RequestMonitor;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import p017j$.util.DesugarCollections;
import p017j$.util.Objects;

/* loaded from: classes3.dex */
public class RequestMonitor {
    private final boolean mQuirkEnabled;
    private final List mRequestTasks = DesugarCollections.synchronizedList(new ArrayList());

    public RequestMonitor(boolean z) {
        this.mQuirkEnabled = z;
    }

    public boolean shouldMonitorRequest() {
        return this.mQuirkEnabled;
    }

    public ListenableFuture getRequestsProcessedFuture() {
        if (this.mRequestTasks.isEmpty()) {
            return Futures.immediateFuture(null);
        }
        return Futures.nonCancellationPropagating(Futures.transform(Futures.successfulAsList(new ArrayList(this.mRequestTasks)), new Function() { // from class: androidx.camera.camera2.internal.compat.workaround.RequestMonitor$$ExternalSyntheticLambda1
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                return RequestMonitor.$r8$lambda$jVAT5QA6o7a1cjXHpnYkiNFtQN8((List) obj);
            }
        }, CameraXExecutors.directExecutor()));
    }

    public static /* synthetic */ Void $r8$lambda$jVAT5QA6o7a1cjXHpnYkiNFtQN8(List list) {
        return null;
    }

    public CameraCaptureSession.CaptureCallback createMonitorListener(CameraCaptureSession.CaptureCallback captureCallback) {
        return shouldMonitorRequest() ? Camera2CaptureCallbacks.createComboCallback(createMonitorListener(), captureCallback) : captureCallback;
    }

    private CameraCaptureSession.CaptureCallback createMonitorListener() {
        final RequestCompleteListener requestCompleteListener = new RequestCompleteListener();
        final ListenableFuture listenableFuture = requestCompleteListener.mStartRequestFuture;
        this.mRequestTasks.add(listenableFuture);
        Log.d("RequestMonitor", "RequestListener " + requestCompleteListener + " monitoring " + this);
        listenableFuture.addListener(new Runnable() { // from class: androidx.camera.camera2.internal.compat.workaround.RequestMonitor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RequestMonitor.m1427$r8$lambda$GA98FVGjPubdbH2uPLqWeCauxw(this.f$0, requestCompleteListener, listenableFuture);
            }
        }, CameraXExecutors.directExecutor());
        return requestCompleteListener;
    }

    /* renamed from: $r8$lambda$-GA98FVGjPubdbH2uPLqWeCauxw, reason: not valid java name */
    public static /* synthetic */ void m1427$r8$lambda$GA98FVGjPubdbH2uPLqWeCauxw(RequestMonitor requestMonitor, RequestCompleteListener requestCompleteListener, ListenableFuture listenableFuture) {
        requestMonitor.getClass();
        Log.d("RequestMonitor", "RequestListener " + requestCompleteListener + " done " + requestMonitor);
        requestMonitor.mRequestTasks.remove(listenableFuture);
    }

    public void stop() {
        LinkedList linkedList = new LinkedList(this.mRequestTasks);
        while (!linkedList.isEmpty()) {
            ListenableFuture listenableFuture = (ListenableFuture) linkedList.poll();
            Objects.requireNonNull(listenableFuture);
            listenableFuture.cancel(true);
        }
    }

    static class RequestCompleteListener extends CameraCaptureSession.CaptureCallback {
        CallbackToFutureAdapter.Completer mStartRequestCompleter;
        final ListenableFuture mStartRequestFuture = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.compat.workaround.RequestMonitor$RequestCompleteListener$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return RequestMonitor.RequestCompleteListener.$r8$lambda$k4QhtcQbWPiAHBIvCRYzPg944F4(this.f$0, completer);
            }
        });

        RequestCompleteListener() {
        }

        public static /* synthetic */ Object $r8$lambda$k4QhtcQbWPiAHBIvCRYzPg944F4(RequestCompleteListener requestCompleteListener, CallbackToFutureAdapter.Completer completer) {
            requestCompleteListener.mStartRequestCompleter = completer;
            return "RequestCompleteListener[" + requestCompleteListener + "]";
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureStarted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, long j, long j2) {
            completeFuture();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceAborted(CameraCaptureSession cameraCaptureSession, int i) {
            completeFuture();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureCompleted(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, TotalCaptureResult totalCaptureResult) {
            completeFuture();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureFailed(CameraCaptureSession cameraCaptureSession, CaptureRequest captureRequest, CaptureFailure captureFailure) {
            completeFuture();
        }

        @Override // android.hardware.camera2.CameraCaptureSession.CaptureCallback
        public void onCaptureSequenceCompleted(CameraCaptureSession cameraCaptureSession, int i, long j) {
            completeFuture();
        }

        private void completeFuture() {
            CallbackToFutureAdapter.Completer completer = this.mStartRequestCompleter;
            if (completer != null) {
                completer.set(null);
                this.mStartRequestCompleter = null;
            }
        }
    }
}
