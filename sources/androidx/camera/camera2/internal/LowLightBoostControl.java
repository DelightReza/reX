package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Build;
import androidx.camera.camera2.internal.Camera2CameraControlImpl;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.core.CameraControl;
import androidx.camera.core.impl.utils.Threads;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
final class LowLightBoostControl {
    private final Camera2CameraControlImpl mCamera2CameraControlImpl;
    final Camera2CameraControlImpl.CaptureResultListener mCaptureResultListener;
    CallbackToFutureAdapter.Completer mEnableLlbCompleter;
    private final Executor mExecutor;
    private boolean mIsActive;
    private final boolean mIsLowLightBoostSupported;
    private final MutableLiveData mLowLightBoostState;
    boolean mTargetLlbEnabled;
    private final AtomicInteger mLowLightBoostStateAtomic = new AtomicInteger(-1);
    private final Object mLock = new Object();
    private boolean mLowLightBoostDisabledByUseCaseSessionConfig = false;

    LowLightBoostControl(Camera2CameraControlImpl camera2CameraControlImpl, CameraCharacteristicsCompat cameraCharacteristicsCompat, Executor executor) {
        this.mCamera2CameraControlImpl = camera2CameraControlImpl;
        this.mExecutor = executor;
        boolean zCheckLowLightBoostAvailability = checkLowLightBoostAvailability(cameraCharacteristicsCompat);
        this.mIsLowLightBoostSupported = zCheckLowLightBoostAvailability;
        this.mLowLightBoostState = new MutableLiveData(-1);
        Camera2CameraControlImpl.CaptureResultListener captureResultListener = new Camera2CameraControlImpl.CaptureResultListener() { // from class: androidx.camera.camera2.internal.LowLightBoostControl$$ExternalSyntheticLambda0
            @Override // androidx.camera.camera2.internal.Camera2CameraControlImpl.CaptureResultListener
            public final boolean onCaptureResult(TotalCaptureResult totalCaptureResult) {
                return LowLightBoostControl.m1411$r8$lambda$b8D2tSyWiEKHonrKr8NdL3U7JE(this.f$0, totalCaptureResult);
            }
        };
        this.mCaptureResultListener = captureResultListener;
        if (zCheckLowLightBoostAvailability) {
            camera2CameraControlImpl.addCaptureResultListener(captureResultListener);
        }
    }

    /* renamed from: $r8$lambda$b8D2tSyWiEKHonrKr8NdL3U-7JE, reason: not valid java name */
    public static /* synthetic */ boolean m1411$r8$lambda$b8D2tSyWiEKHonrKr8NdL3U7JE(LowLightBoostControl lowLightBoostControl, TotalCaptureResult totalCaptureResult) {
        Integer num;
        if (lowLightBoostControl.mEnableLlbCompleter != null) {
            Integer num2 = (Integer) totalCaptureResult.getRequest().get(CaptureRequest.CONTROL_AE_MODE);
            if (num2 != null) {
                if ((num2.intValue() == 6) == lowLightBoostControl.mTargetLlbEnabled) {
                    lowLightBoostControl.mEnableLlbCompleter.set(null);
                    lowLightBoostControl.mEnableLlbCompleter = null;
                    if (Build.VERSION.SDK_INT >= 35) {
                        lowLightBoostControl.setLiveDataValue(lowLightBoostControl.mLowLightBoostState, num.intValue());
                    }
                }
            }
        } else if (Build.VERSION.SDK_INT >= 35 && lowLightBoostControl.mTargetLlbEnabled && (num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_LOW_LIGHT_BOOST_STATE)) != null) {
            lowLightBoostControl.setLiveDataValue(lowLightBoostControl.mLowLightBoostState, num.intValue());
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
        if (this.mTargetLlbEnabled) {
            this.mTargetLlbEnabled = false;
            this.mCamera2CameraControlImpl.enableLowLightBoostInternal(false);
            setLiveDataValue(this.mLowLightBoostState, -1);
        }
        CallbackToFutureAdapter.Completer completer = this.mEnableLlbCompleter;
        if (completer != null) {
            completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
            this.mEnableLlbCompleter = null;
        }
    }

    void setLowLightBoostDisabledByUseCaseSessionConfig(boolean z) {
        synchronized (this.mLock) {
            try {
                this.mLowLightBoostDisabledByUseCaseSessionConfig = z;
                if (z) {
                    if (this.mTargetLlbEnabled) {
                        this.mTargetLlbEnabled = false;
                        this.mCamera2CameraControlImpl.enableLowLightBoostInternal(false);
                        setLiveDataValue(this.mLowLightBoostState, -1);
                        CallbackToFutureAdapter.Completer completer = this.mEnableLlbCompleter;
                        if (completer != null) {
                            completer.setException(new IllegalStateException("Low-light boost is disabled when expected frame rate range exceeds 30 or HDR 10-bit is on."));
                            this.mEnableLlbCompleter = null;
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    LiveData getLowLightBoostState() {
        return this.mLowLightBoostState;
    }

    static boolean checkLowLightBoostAvailability(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        int[] iArr;
        if (Build.VERSION.SDK_INT > 34 && (iArr = (int[]) cameraCharacteristicsCompat.get(CameraCharacteristics.CONTROL_AE_AVAILABLE_MODES)) != null) {
            for (int i : iArr) {
                if (i == 6) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setLiveDataValue(MutableLiveData mutableLiveData, int i) {
        if (this.mLowLightBoostStateAtomic.getAndSet(i) != i) {
            if (Threads.isMainThread()) {
                mutableLiveData.setValue(Integer.valueOf(i));
            } else {
                mutableLiveData.postValue(Integer.valueOf(i));
            }
        }
    }
}
