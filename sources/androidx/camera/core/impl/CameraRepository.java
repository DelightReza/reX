package androidx.camera.core.impl;

import androidx.camera.core.CameraUnavailableException;
import androidx.camera.core.InitializationException;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: classes3.dex */
public final class CameraRepository {
    private CallbackToFutureAdapter.Completer mDeinitCompleter;
    private ListenableFuture mDeinitFuture;
    private final Object mCamerasLock = new Object();
    private final Map mCameras = new LinkedHashMap();
    private final Set mReleasingCameras = new HashSet();

    public void init(CameraFactory cameraFactory) {
        synchronized (this.mCamerasLock) {
            try {
                for (String str : cameraFactory.getAvailableCameraIds()) {
                    Logger.m43d("CameraRepository", "Added camera: " + str);
                    CameraInternal cameraInternal = (CameraInternal) this.mCameras.put(str, cameraFactory.getCamera(str));
                    if (cameraInternal != null) {
                        cameraInternal.release();
                    }
                }
            } catch (CameraUnavailableException e) {
                throw new InitializationException(e);
            }
        }
    }

    public ListenableFuture deinit() {
        synchronized (this.mCamerasLock) {
            try {
                if (this.mCameras.isEmpty()) {
                    ListenableFuture listenableFutureImmediateFuture = this.mDeinitFuture;
                    if (listenableFutureImmediateFuture == null) {
                        listenableFutureImmediateFuture = Futures.immediateFuture(null);
                    }
                    return listenableFutureImmediateFuture;
                }
                ListenableFuture future = this.mDeinitFuture;
                if (future == null) {
                    future = CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.core.impl.CameraRepository$$ExternalSyntheticLambda0
                        @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                        public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                            return CameraRepository.$r8$lambda$EVR2_LFGuegIUMqvZY9O12S_ZVM(this.f$0, completer);
                        }
                    });
                    this.mDeinitFuture = future;
                }
                this.mReleasingCameras.addAll(this.mCameras.values());
                for (final CameraInternal cameraInternal : this.mCameras.values()) {
                    cameraInternal.release().addListener(new Runnable() { // from class: androidx.camera.core.impl.CameraRepository$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            CameraRepository.$r8$lambda$6tNk7BHQV_7i2HLwF1CZkzV1hrM(this.f$0, cameraInternal);
                        }
                    }, CameraXExecutors.directExecutor());
                }
                this.mCameras.clear();
                return future;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static /* synthetic */ Object $r8$lambda$EVR2_LFGuegIUMqvZY9O12S_ZVM(CameraRepository cameraRepository, CallbackToFutureAdapter.Completer completer) {
        synchronized (cameraRepository.mCamerasLock) {
            cameraRepository.mDeinitCompleter = completer;
        }
        return "CameraRepository-deinit";
    }

    public static /* synthetic */ void $r8$lambda$6tNk7BHQV_7i2HLwF1CZkzV1hrM(CameraRepository cameraRepository, CameraInternal cameraInternal) {
        synchronized (cameraRepository.mCamerasLock) {
            try {
                cameraRepository.mReleasingCameras.remove(cameraInternal);
                if (cameraRepository.mReleasingCameras.isEmpty()) {
                    Preconditions.checkNotNull(cameraRepository.mDeinitCompleter);
                    cameraRepository.mDeinitCompleter.set(null);
                    cameraRepository.mDeinitCompleter = null;
                    cameraRepository.mDeinitFuture = null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public CameraInternal getCamera(String str) {
        CameraInternal cameraInternal;
        synchronized (this.mCamerasLock) {
            try {
                cameraInternal = (CameraInternal) this.mCameras.get(str);
                if (cameraInternal == null) {
                    throw new IllegalArgumentException("Invalid camera: " + str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return cameraInternal;
    }

    public LinkedHashSet getCameras() {
        LinkedHashSet linkedHashSet;
        synchronized (this.mCamerasLock) {
            linkedHashSet = new LinkedHashSet(this.mCameras.values());
        }
        return linkedHashSet;
    }
}
