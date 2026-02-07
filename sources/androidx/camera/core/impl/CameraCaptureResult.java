package androidx.camera.core.impl;

import android.hardware.camera2.CaptureResult;
import androidx.camera.core.impl.utils.ExifData;

/* loaded from: classes3.dex */
public interface CameraCaptureResult {
    CameraCaptureMetaData$AeState getAeState();

    CameraCaptureMetaData$AfState getAfState();

    CameraCaptureMetaData$AwbState getAwbState();

    CaptureResult getCaptureResult();

    CameraCaptureMetaData$FlashState getFlashState();

    TagBundle getTagBundle();

    long getTimestamp();

    void populateExifData(ExifData.Builder builder);

    /* renamed from: androidx.camera.core.impl.CameraCaptureResult$-CC, reason: invalid class name */
    public abstract /* synthetic */ class CC {
        public static void $default$populateExifData(CameraCaptureResult cameraCaptureResult, ExifData.Builder builder) {
            builder.setFlashState(cameraCaptureResult.getFlashState());
        }

        public static CaptureResult $default$getCaptureResult(CameraCaptureResult cameraCaptureResult) {
            return null;
        }
    }

    public static final class EmptyCameraCaptureResult implements CameraCaptureResult {
        @Override // androidx.camera.core.impl.CameraCaptureResult
        public CaptureResult getCaptureResult() {
            return null;
        }

        @Override // androidx.camera.core.impl.CameraCaptureResult
        public long getTimestamp() {
            return -1L;
        }

        @Override // androidx.camera.core.impl.CameraCaptureResult
        public /* synthetic */ void populateExifData(ExifData.Builder builder) {
            CC.$default$populateExifData(this, builder);
        }

        public static CameraCaptureResult create() {
            return new EmptyCameraCaptureResult();
        }

        @Override // androidx.camera.core.impl.CameraCaptureResult
        public CameraCaptureMetaData$AfState getAfState() {
            return CameraCaptureMetaData$AfState.UNKNOWN;
        }

        @Override // androidx.camera.core.impl.CameraCaptureResult
        public CameraCaptureMetaData$AeState getAeState() {
            return CameraCaptureMetaData$AeState.UNKNOWN;
        }

        @Override // androidx.camera.core.impl.CameraCaptureResult
        public CameraCaptureMetaData$AwbState getAwbState() {
            return CameraCaptureMetaData$AwbState.UNKNOWN;
        }

        @Override // androidx.camera.core.impl.CameraCaptureResult
        public CameraCaptureMetaData$FlashState getFlashState() {
            return CameraCaptureMetaData$FlashState.UNKNOWN;
        }

        @Override // androidx.camera.core.impl.CameraCaptureResult
        public TagBundle getTagBundle() {
            return TagBundle.emptyBundle();
        }
    }
}
