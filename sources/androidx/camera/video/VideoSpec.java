package androidx.camera.video;

import android.util.Range;
import androidx.camera.video.AutoValue_VideoSpec;
import java.util.Arrays;
import org.telegram.tgnet.ConnectionsManager;

/* loaded from: classes3.dex */
public abstract class VideoSpec {
    public static final Range BITRATE_RANGE_AUTO = new Range(0, Integer.valueOf(ConnectionsManager.DEFAULT_DATACENTER_ID));
    public static final QualitySelector QUALITY_SELECTOR_AUTO;

    abstract int getAspectRatio();

    public abstract Range getBitrate();

    public abstract int getEncodeFrameRate();

    public abstract QualitySelector getQualitySelector();

    public abstract Builder toBuilder();

    static {
        Quality quality = Quality.FHD;
        QUALITY_SELECTOR_AUTO = QualitySelector.fromOrderedList(Arrays.asList(quality, Quality.f11HD, Quality.f12SD), FallbackStrategy.higherQualityOrLowerThan(quality));
    }

    VideoSpec() {
    }

    public static Builder builder() {
        return new AutoValue_VideoSpec.Builder().setQualitySelector(QUALITY_SELECTOR_AUTO).setEncodeFrameRate(0).setBitrate(BITRATE_RANGE_AUTO).setAspectRatio(-1);
    }

    public static abstract class Builder {
        public abstract VideoSpec build();

        abstract Builder setAspectRatio(int i);

        public abstract Builder setBitrate(Range range);

        public abstract Builder setEncodeFrameRate(int i);

        public abstract Builder setQualitySelector(QualitySelector qualitySelector);

        Builder() {
        }
    }
}
