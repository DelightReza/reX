package androidx.camera.video.internal.config;

import android.util.Range;
import android.util.Size;
import androidx.camera.core.DynamicRange;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.Timebase;
import androidx.camera.video.VideoSpec;
import androidx.camera.video.internal.encoder.VideoEncoderConfig;
import androidx.camera.video.internal.utils.DynamicRangeUtil;
import androidx.core.util.Supplier;

/* loaded from: classes3.dex */
public class VideoEncoderConfigDefaultResolver implements Supplier {
    private static final Size VIDEO_SIZE_BASE = new Size(1280, 720);
    private final DynamicRange mDynamicRange;
    private final Range mExpectedFrameRateRange;
    private final Timebase mInputTimebase;
    private final String mMimeType;
    private final Size mSurfaceSize;
    private final VideoSpec mVideoSpec;

    public VideoEncoderConfigDefaultResolver(String str, Timebase timebase, VideoSpec videoSpec, Size size, DynamicRange dynamicRange, Range range) {
        this.mMimeType = str;
        this.mInputTimebase = timebase;
        this.mVideoSpec = videoSpec;
        this.mSurfaceSize = size;
        this.mDynamicRange = dynamicRange;
        this.mExpectedFrameRateRange = range;
    }

    @Override // androidx.core.util.Supplier
    public VideoEncoderConfig get() {
        CaptureEncodeRates captureEncodeRatesResolveFrameRates = VideoConfigUtil.resolveFrameRates(this.mVideoSpec, this.mExpectedFrameRateRange);
        Logger.m43d("VidEncCfgDefaultRslvr", "Resolved VIDEO frame rates: Capture frame rate = " + captureEncodeRatesResolveFrameRates.getCaptureRate() + "fps. Encode frame rate = " + captureEncodeRatesResolveFrameRates.getEncodeRate() + "fps.");
        Range bitrate = this.mVideoSpec.getBitrate();
        Logger.m43d("VidEncCfgDefaultRslvr", "Using fallback VIDEO bitrate");
        int bitDepth = this.mDynamicRange.getBitDepth();
        int encodeRate = captureEncodeRatesResolveFrameRates.getEncodeRate();
        int width = this.mSurfaceSize.getWidth();
        Size size = VIDEO_SIZE_BASE;
        int iScaleAndClampBitrate = VideoConfigUtil.scaleAndClampBitrate(14000000, bitDepth, 8, encodeRate, 30, width, size.getWidth(), this.mSurfaceSize.getHeight(), size.getHeight(), bitrate);
        int iDynamicRangeToCodecProfileLevelForMime = DynamicRangeUtil.dynamicRangeToCodecProfileLevelForMime(this.mMimeType, this.mDynamicRange);
        return VideoEncoderConfig.builder().setMimeType(this.mMimeType).setInputTimebase(this.mInputTimebase).setResolution(this.mSurfaceSize).setBitrate(iScaleAndClampBitrate).setCaptureFrameRate(captureEncodeRatesResolveFrameRates.getCaptureRate()).setEncodeFrameRate(captureEncodeRatesResolveFrameRates.getEncodeRate()).setProfile(iDynamicRangeToCodecProfileLevelForMime).setDataSpace(VideoConfigUtil.mimeAndProfileToEncoderDataSpace(this.mMimeType, iDynamicRangeToCodecProfileLevelForMime)).build();
    }
}
