package androidx.camera.video;

import android.util.Range;
import androidx.camera.video.VideoSpec;

/* loaded from: classes3.dex */
final class AutoValue_VideoSpec extends VideoSpec {
    private final int aspectRatio;
    private final Range bitrate;
    private final int encodeFrameRate;
    private final QualitySelector qualitySelector;

    private AutoValue_VideoSpec(QualitySelector qualitySelector, int i, Range range, int i2) {
        this.qualitySelector = qualitySelector;
        this.encodeFrameRate = i;
        this.bitrate = range;
        this.aspectRatio = i2;
    }

    @Override // androidx.camera.video.VideoSpec
    public QualitySelector getQualitySelector() {
        return this.qualitySelector;
    }

    @Override // androidx.camera.video.VideoSpec
    public int getEncodeFrameRate() {
        return this.encodeFrameRate;
    }

    @Override // androidx.camera.video.VideoSpec
    public Range getBitrate() {
        return this.bitrate;
    }

    @Override // androidx.camera.video.VideoSpec
    int getAspectRatio() {
        return this.aspectRatio;
    }

    public String toString() {
        return "VideoSpec{qualitySelector=" + this.qualitySelector + ", encodeFrameRate=" + this.encodeFrameRate + ", bitrate=" + this.bitrate + ", aspectRatio=" + this.aspectRatio + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof VideoSpec) {
            VideoSpec videoSpec = (VideoSpec) obj;
            if (this.qualitySelector.equals(videoSpec.getQualitySelector()) && this.encodeFrameRate == videoSpec.getEncodeFrameRate() && this.bitrate.equals(videoSpec.getBitrate()) && this.aspectRatio == videoSpec.getAspectRatio()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((this.qualitySelector.hashCode() ^ 1000003) * 1000003) ^ this.encodeFrameRate) * 1000003) ^ this.bitrate.hashCode()) * 1000003) ^ this.aspectRatio;
    }

    @Override // androidx.camera.video.VideoSpec
    public VideoSpec.Builder toBuilder() {
        return new Builder(this);
    }

    static final class Builder extends VideoSpec.Builder {
        private Integer aspectRatio;
        private Range bitrate;
        private Integer encodeFrameRate;
        private QualitySelector qualitySelector;

        Builder() {
        }

        private Builder(VideoSpec videoSpec) {
            this.qualitySelector = videoSpec.getQualitySelector();
            this.encodeFrameRate = Integer.valueOf(videoSpec.getEncodeFrameRate());
            this.bitrate = videoSpec.getBitrate();
            this.aspectRatio = Integer.valueOf(videoSpec.getAspectRatio());
        }

        @Override // androidx.camera.video.VideoSpec.Builder
        public VideoSpec.Builder setQualitySelector(QualitySelector qualitySelector) {
            if (qualitySelector == null) {
                throw new NullPointerException("Null qualitySelector");
            }
            this.qualitySelector = qualitySelector;
            return this;
        }

        @Override // androidx.camera.video.VideoSpec.Builder
        public VideoSpec.Builder setEncodeFrameRate(int i) {
            this.encodeFrameRate = Integer.valueOf(i);
            return this;
        }

        @Override // androidx.camera.video.VideoSpec.Builder
        public VideoSpec.Builder setBitrate(Range range) {
            if (range == null) {
                throw new NullPointerException("Null bitrate");
            }
            this.bitrate = range;
            return this;
        }

        @Override // androidx.camera.video.VideoSpec.Builder
        VideoSpec.Builder setAspectRatio(int i) {
            this.aspectRatio = Integer.valueOf(i);
            return this;
        }

        @Override // androidx.camera.video.VideoSpec.Builder
        public VideoSpec build() {
            String str = "";
            if (this.qualitySelector == null) {
                str = " qualitySelector";
            }
            if (this.encodeFrameRate == null) {
                str = str + " encodeFrameRate";
            }
            if (this.bitrate == null) {
                str = str + " bitrate";
            }
            if (this.aspectRatio == null) {
                str = str + " aspectRatio";
            }
            if (!str.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + str);
            }
            return new AutoValue_VideoSpec(this.qualitySelector, this.encodeFrameRate.intValue(), this.bitrate, this.aspectRatio.intValue());
        }
    }
}
