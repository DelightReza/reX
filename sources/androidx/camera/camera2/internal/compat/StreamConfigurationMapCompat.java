package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.util.Range;
import android.util.Size;
import androidx.camera.camera2.internal.compat.workaround.OutputSizesCorrector;
import androidx.camera.core.Logger;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class StreamConfigurationMapCompat {
    private final StreamConfigurationMapCompatImpl mImpl;
    private final OutputSizesCorrector mOutputSizesCorrector;
    private final Map mCachedFormatOutputSizes = new HashMap();
    private final Map mCachedFormatHighResolutionOutputSizes = new HashMap();
    private final Map mCachedClassOutputSizes = new HashMap();

    interface StreamConfigurationMapCompatImpl {
        Size[] getHighResolutionOutputSizes(int i);

        Range[] getHighSpeedVideoFpsRangesFor(Size size);

        Size[] getHighSpeedVideoSizes();

        Size[] getHighSpeedVideoSizesFor(Range range);

        int[] getOutputFormats();

        long getOutputMinFrameDuration(int i, Size size);

        Size[] getOutputSizes(int i);

        StreamConfigurationMap unwrap();
    }

    private StreamConfigurationMapCompat(StreamConfigurationMap streamConfigurationMap, OutputSizesCorrector outputSizesCorrector) {
        if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = new StreamConfigurationMapCompatApi23Impl(streamConfigurationMap);
        } else {
            this.mImpl = new StreamConfigurationMapCompatBaseImpl(streamConfigurationMap);
        }
        this.mOutputSizesCorrector = outputSizesCorrector;
    }

    static StreamConfigurationMapCompat toStreamConfigurationMapCompat(StreamConfigurationMap streamConfigurationMap, OutputSizesCorrector outputSizesCorrector) {
        return new StreamConfigurationMapCompat(streamConfigurationMap, outputSizesCorrector);
    }

    public int[] getOutputFormats() {
        int[] outputFormats = this.mImpl.getOutputFormats();
        if (outputFormats == null) {
            return null;
        }
        return (int[]) outputFormats.clone();
    }

    public Size[] getOutputSizes(int i) {
        Size[] outputSizes = null;
        if (this.mCachedFormatOutputSizes.containsKey(Integer.valueOf(i))) {
            if (((Size[]) this.mCachedFormatOutputSizes.get(Integer.valueOf(i))) == null) {
                return null;
            }
            return (Size[]) ((Size[]) this.mCachedFormatOutputSizes.get(Integer.valueOf(i))).clone();
        }
        try {
            outputSizes = this.mImpl.getOutputSizes(i);
        } catch (Throwable th) {
            Logger.m49w("StreamConfigurationMapCompat", "Failed to get output sizes for " + i, th);
        }
        if (outputSizes == null || outputSizes.length == 0) {
            Logger.m48w("StreamConfigurationMapCompat", "Retrieved output sizes array is null or empty for format " + i);
            return outputSizes;
        }
        Size[] sizeArrApplyQuirks = this.mOutputSizesCorrector.applyQuirks(outputSizes, i);
        this.mCachedFormatOutputSizes.put(Integer.valueOf(i), sizeArrApplyQuirks);
        return (Size[]) sizeArrApplyQuirks.clone();
    }

    public long getOutputMinFrameDuration(int i, Size size) {
        try {
            return this.mImpl.getOutputMinFrameDuration(i, size);
        } catch (RuntimeException e) {
            Logger.m49w("StreamConfigurationMapCompat", "Failed to get min frame duration for format = " + i + " and size = " + size, e);
            return 0L;
        }
    }

    public Size[] getHighResolutionOutputSizes(int i) {
        if (this.mCachedFormatHighResolutionOutputSizes.containsKey(Integer.valueOf(i))) {
            if (((Size[]) this.mCachedFormatHighResolutionOutputSizes.get(Integer.valueOf(i))) == null) {
                return null;
            }
            return (Size[]) ((Size[]) this.mCachedFormatHighResolutionOutputSizes.get(Integer.valueOf(i))).clone();
        }
        Size[] highResolutionOutputSizes = this.mImpl.getHighResolutionOutputSizes(i);
        if (highResolutionOutputSizes != null && highResolutionOutputSizes.length > 0) {
            highResolutionOutputSizes = this.mOutputSizesCorrector.applyQuirks(highResolutionOutputSizes, i);
        }
        this.mCachedFormatHighResolutionOutputSizes.put(Integer.valueOf(i), highResolutionOutputSizes);
        if (highResolutionOutputSizes != null) {
            return (Size[]) highResolutionOutputSizes.clone();
        }
        return null;
    }

    public Range[] getHighSpeedVideoFpsRangesFor(Size size) {
        return this.mImpl.getHighSpeedVideoFpsRangesFor(size);
    }

    public Size[] getHighSpeedVideoSizes() {
        return this.mImpl.getHighSpeedVideoSizes();
    }

    public Size[] getHighSpeedVideoSizesFor(Range range) {
        return this.mImpl.getHighSpeedVideoSizesFor(range);
    }

    public StreamConfigurationMap toStreamConfigurationMap() {
        return this.mImpl.unwrap();
    }
}
