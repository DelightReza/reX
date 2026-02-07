package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraManager;
import android.util.Log;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.core.CameraIdentifier;
import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.impl.AbstractCameraPresenceSource;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class Camera2PresenceSource extends AbstractCameraPresenceSource {
    private static final Companion Companion = new Companion(null);
    private final CameraManagerCompat cameraManager;
    private CameraManager.AvailabilityCallback systemAvailabilityCallback;
    private final Executor systemCallbackExecutor;

    public Camera2PresenceSource(CameraManagerCompat cameraManager, Executor systemCallbackExecutor) {
        Intrinsics.checkNotNullParameter(cameraManager, "cameraManager");
        Intrinsics.checkNotNullParameter(systemCallbackExecutor, "systemCallbackExecutor");
        this.cameraManager = cameraManager;
        this.systemCallbackExecutor = systemCallbackExecutor;
    }

    @Override // androidx.camera.core.impl.AbstractCameraPresenceSource
    public void startMonitoring() {
        if (this.systemAvailabilityCallback != null) {
            Log.w("Camera2PresenceSrc", "Monitoring already started. Unregistering existing callback.");
            stopMonitoring();
        }
        Log.i("Camera2PresenceSrc", "Starting system availability monitoring.");
        CameraManager.AvailabilityCallback availabilityCallback = new CameraManager.AvailabilityCallback() { // from class: androidx.camera.camera2.internal.Camera2PresenceSource.startMonitoring.1
            @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
            public void onCameraAvailable(String cameraId) {
                Intrinsics.checkNotNullParameter(cameraId, "cameraId");
                Log.d("Camera2PresenceSrc", "System onCameraAvailable: " + cameraId);
                Camera2PresenceSource camera2PresenceSource = Camera2PresenceSource.this;
                camera2PresenceSource.fetchDataAndForget(camera2PresenceSource.fetchData());
            }

            @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
            public void onCameraUnavailable(String cameraId) {
                Intrinsics.checkNotNullParameter(cameraId, "cameraId");
                Log.d("Camera2PresenceSrc", "System onCameraUnavailable: " + cameraId);
                Camera2PresenceSource camera2PresenceSource = Camera2PresenceSource.this;
                camera2PresenceSource.fetchDataAndForget(camera2PresenceSource.fetchData());
            }

            @Override // android.hardware.camera2.CameraManager.AvailabilityCallback
            public void onCameraAccessPrioritiesChanged() {
                Log.d("Camera2PresenceSrc", "System onCameraAccessPrioritiesChanged.");
                Camera2PresenceSource camera2PresenceSource = Camera2PresenceSource.this;
                camera2PresenceSource.fetchDataAndForget(camera2PresenceSource.fetchData());
            }
        };
        this.systemAvailabilityCallback = availabilityCallback;
        CameraManagerCompat cameraManagerCompat = this.cameraManager;
        Executor executor = this.systemCallbackExecutor;
        Intrinsics.checkNotNull(availabilityCallback);
        cameraManagerCompat.registerAvailabilityCallback(executor, availabilityCallback);
        fetchDataAndForget(fetchData());
    }

    @Override // androidx.camera.core.impl.AbstractCameraPresenceSource
    public void stopMonitoring() {
        Log.i("Camera2PresenceSrc", "Stopping system availability monitoring.");
        CameraManager.AvailabilityCallback availabilityCallback = this.systemAvailabilityCallback;
        if (availabilityCallback != null) {
            try {
                try {
                    this.cameraManager.unregisterAvailabilityCallback(availabilityCallback);
                    Unit unit = Unit.INSTANCE;
                } catch (Exception e) {
                    Log.w("Camera2PresenceSrc", "Failed to unregister system availability callback.", e);
                }
            } finally {
                this.systemAvailabilityCallback = null;
            }
        }
    }

    @Override // androidx.camera.core.impl.Observable
    public ListenableFuture fetchData() {
        ListenableFuture future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.Camera2PresenceSource$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return Camera2PresenceSource.fetchData$lambda$3(this.f$0, completer);
            }
        });
        Intrinsics.checkNotNullExpressionValue(future, "getFuture(...)");
        return future;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object fetchData$lambda$3(final Camera2PresenceSource camera2PresenceSource, final CallbackToFutureAdapter.Completer completer) {
        Intrinsics.checkNotNullParameter(completer, "completer");
        camera2PresenceSource.systemCallbackExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.Camera2PresenceSource$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Camera2PresenceSource.fetchData$lambda$3$lambda$2(this.f$0, completer);
            }
        });
        return "FetchData for CameraAvailability";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void fetchData$lambda$3$lambda$2(Camera2PresenceSource camera2PresenceSource, CallbackToFutureAdapter.Completer completer) {
        try {
            String[] cameraIdList = camera2PresenceSource.cameraManager.getCameraIdList();
            Intrinsics.checkNotNullExpressionValue(cameraIdList, "getCameraIdList(...)");
            ArrayList arrayList = new ArrayList(cameraIdList.length);
            for (String str : cameraIdList) {
                CameraIdentifier.Companion companion = CameraIdentifier.Companion;
                Intrinsics.checkNotNull(str);
                arrayList.add(CameraIdentifier.Companion.create$default(companion, str, null, null, 6, null));
            }
            Log.d("Camera2PresenceSrc", "[FetchData] Refreshed camera list: " + CollectionsKt.joinToString$default(arrayList, null, null, null, 0, null, null, 63, null));
            camera2PresenceSource.updateData(arrayList);
            completer.set(arrayList);
        } catch (CameraAccessExceptionCompat e) {
            Log.e("Camera2PresenceSrc", "[FetchData] Failed to get camera list for refresh.", e);
            CameraUnavailableException cameraUnavailableExceptionCreateFrom = CameraUnavailableExceptionHelper.createFrom(e);
            Intrinsics.checkNotNullExpressionValue(cameraUnavailableExceptionCreateFrom, "createFrom(...)");
            camera2PresenceSource.updateError(cameraUnavailableExceptionCreateFrom);
            completer.setException(cameraUnavailableExceptionCreateFrom);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void fetchDataAndForget(ListenableFuture listenableFuture) {
        Futures.transformAsyncOnCompletion(listenableFuture);
    }

    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
