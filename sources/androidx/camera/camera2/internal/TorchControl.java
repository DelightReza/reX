package androidx.camera.camera2.internal;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import androidx.camera.camera2.internal.Camera2CameraControlImpl;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.internal.compat.workaround.FlashAvailabilityChecker;
import androidx.camera.core.CameraControl;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;
import p017j$.util.Objects;

/* loaded from: classes3.dex */
final class TorchControl {
    private final Camera2CameraControlImpl mCamera2CameraControlImpl;
    private int mDefaultTorchStrength;
    CallbackToFutureAdapter.Completer mEnableTorchCompleter;
    private final Executor mExecutor;
    private final boolean mHasFlashUnit;
    private boolean mIsActive;
    private boolean mIsTorchStrengthSupported;
    boolean mTargetTorchEnabled;
    private int mTargetTorchStrength;
    private final MutableLiveData mTorchState;
    private final MutableLiveData mTorchStrength;
    private CallbackToFutureAdapter.Completer mTorchStrengthCompleter;

    TorchControl(Camera2CameraControlImpl camera2CameraControlImpl, CameraCharacteristicsCompat cameraCharacteristicsCompat, Executor executor) {
        this.mCamera2CameraControlImpl = camera2CameraControlImpl;
        this.mExecutor = executor;
        Objects.requireNonNull(cameraCharacteristicsCompat);
        boolean zIsFlashAvailable = FlashAvailabilityChecker.isFlashAvailable(new Camera2CameraInfoImpl$$ExternalSyntheticLambda0(cameraCharacteristicsCompat));
        this.mHasFlashUnit = zIsFlashAvailable;
        boolean zIsTorchStrengthLevelSupported = cameraCharacteristicsCompat.isTorchStrengthLevelSupported();
        this.mIsTorchStrengthSupported = zIsTorchStrengthLevelSupported;
        int defaultTorchStrengthLevel = (zIsFlashAvailable && zIsTorchStrengthLevelSupported) ? cameraCharacteristicsCompat.getDefaultTorchStrengthLevel() : 0;
        this.mDefaultTorchStrength = defaultTorchStrengthLevel;
        this.mTargetTorchStrength = defaultTorchStrengthLevel;
        this.mTorchState = new MutableLiveData(0);
        this.mTorchStrength = new MutableLiveData(Integer.valueOf(this.mDefaultTorchStrength));
        camera2CameraControlImpl.addCaptureResultListener(new Camera2CameraControlImpl.CaptureResultListener() { // from class: androidx.camera.camera2.internal.TorchControl$$ExternalSyntheticLambda1
            @Override // androidx.camera.camera2.internal.Camera2CameraControlImpl.CaptureResultListener
            public final boolean onCaptureResult(TotalCaptureResult totalCaptureResult) {
                return TorchControl.$r8$lambda$MV8zl7hvxBZxX63Mx1dz9hJ68r8(this.f$0, totalCaptureResult);
            }
        });
    }

    public static /* synthetic */ boolean $r8$lambda$MV8zl7hvxBZxX63Mx1dz9hJ68r8(TorchControl torchControl, TotalCaptureResult totalCaptureResult) {
        Integer num;
        if (torchControl.mEnableTorchCompleter != null) {
            Integer num2 = (Integer) totalCaptureResult.getRequest().get(CaptureRequest.FLASH_MODE);
            if ((num2 != null && num2.intValue() == 2) == torchControl.mTargetTorchEnabled) {
                torchControl.mEnableTorchCompleter.set(null);
                torchControl.mEnableTorchCompleter = null;
            }
        }
        if (torchControl.mIsTorchStrengthSupported && Build.VERSION.SDK_INT >= 35 && torchControl.mTorchStrengthCompleter != null && (num = (Integer) totalCaptureResult.get(CaptureResult.FLASH_STRENGTH_LEVEL)) != null && num.intValue() == torchControl.mTargetTorchStrength) {
            torchControl.mTorchStrengthCompleter.set(null);
            torchControl.mTorchStrengthCompleter = null;
        }
        return false;
    }

    void setActive(boolean z) {
        if (this.mIsActive == z) {
            return;
        }
        this.mIsActive = z;
        if (z) {
            return;
        }
        if (this.mTargetTorchEnabled) {
            this.mTargetTorchEnabled = false;
            this.mTargetTorchStrength = this.mDefaultTorchStrength;
            this.mCamera2CameraControlImpl.enableTorchInternal(0);
            setTorchState(0);
            setLiveDataValue(this.mTorchStrength, Integer.valueOf(this.mDefaultTorchStrength));
        }
        CallbackToFutureAdapter.Completer completer = this.mEnableTorchCompleter;
        if (completer != null) {
            completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
            this.mEnableTorchCompleter = null;
        }
        CallbackToFutureAdapter.Completer completer2 = this.mTorchStrengthCompleter;
        if (completer2 != null) {
            completer2.setException(new CameraControl.OperationCanceledException("Camera is not active."));
            this.mTorchStrengthCompleter = null;
        }
    }

    ListenableFuture enableTorch(final boolean z) {
        if (!this.mHasFlashUnit) {
            Logger.m43d("TorchControl", "Unable to enableTorch due to there is no flash unit.");
            return Futures.immediateFailedFuture(new IllegalStateException("No flash unit"));
        }
        setTorchState(z ? 1 : 0);
        final int i = z ? 1 : 0;
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.TorchControl$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return TorchControl.$r8$lambda$OF0u5aT34uQzw7TE39GnpseSyJ4(this.f$0, i, z, completer);
            }
        });
    }

    public static /* synthetic */ Object $r8$lambda$OF0u5aT34uQzw7TE39GnpseSyJ4(final TorchControl torchControl, final int i, boolean z, final CallbackToFutureAdapter.Completer completer) {
        torchControl.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.TorchControl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.enableTorchInternal(completer, i);
            }
        });
        return "enableTorch: " + z;
    }

    LiveData getTorchState() {
        return this.mTorchState;
    }

    LiveData getTorchStrengthLevel() {
        return this.mTorchStrength;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void enableTorchInternal(CallbackToFutureAdapter.Completer completer, int i) {
        if (!this.mHasFlashUnit) {
            if (completer != null) {
                completer.setException(new IllegalStateException("No flash unit"));
                return;
            }
            return;
        }
        if (!this.mIsActive) {
            setTorchState(0);
            if (completer != null) {
                completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
                return;
            }
            return;
        }
        if (this.mCamera2CameraControlImpl.isLowLightBoostOn()) {
            if (completer != null) {
                completer.setException(new IllegalStateException("Torch can not be enabled when low-light boost is on!"));
                return;
            }
            return;
        }
        this.mTargetTorchEnabled = i != 0;
        this.mCamera2CameraControlImpl.enableTorchInternal(i);
        setTorchState(i);
        CallbackToFutureAdapter.Completer completer2 = this.mEnableTorchCompleter;
        if (completer2 != null) {
            completer2.setException(new CameraControl.OperationCanceledException("There is a new enableTorch being set"));
        }
        this.mEnableTorchCompleter = completer;
    }

    void forceUpdateTorchStateToOff() {
        if (this.mTargetTorchEnabled) {
            this.mTargetTorchEnabled = false;
            setTorchState(0);
        }
    }

    private void setTorchState(int i) {
        setLiveDataValue(this.mTorchState, Integer.valueOf(i != 1 ? 0 : 1));
    }

    private void setLiveDataValue(MutableLiveData mutableLiveData, Object obj) {
        if (Threads.isMainThread()) {
            mutableLiveData.setValue(obj);
        } else {
            mutableLiveData.postValue(obj);
        }
    }
}
