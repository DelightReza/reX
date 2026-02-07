package androidx.camera.core.featuregroup.impl.feature;

import androidx.camera.core.featuregroup.GroupableFeature;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class VideoStabilizationFeature extends GroupableFeature {
    public static final Companion Companion = new Companion(null);
    public static final StabilizationMode DEFAULT_STABILIZATION_MODE = StabilizationMode.OFF;
    private final FeatureTypeInternal featureTypeInternal;
    private final StabilizationMode mode;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static final class StabilizationMode {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ StabilizationMode[] $VALUES;
        public static final StabilizationMode OFF = new StabilizationMode("OFF", 0);

        /* renamed from: ON */
        public static final StabilizationMode f6ON = new StabilizationMode("ON", 1);
        public static final StabilizationMode PREVIEW = new StabilizationMode("PREVIEW", 2);

        private static final /* synthetic */ StabilizationMode[] $values() {
            return new StabilizationMode[]{OFF, f6ON, PREVIEW};
        }

        private StabilizationMode(String str, int i) {
        }

        static {
            StabilizationMode[] stabilizationModeArr$values = $values();
            $VALUES = stabilizationModeArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(stabilizationModeArr$values);
        }

        public static StabilizationMode valueOf(String str) {
            return (StabilizationMode) Enum.valueOf(StabilizationMode.class, str);
        }

        public static StabilizationMode[] values() {
            return (StabilizationMode[]) $VALUES.clone();
        }
    }

    public VideoStabilizationFeature(StabilizationMode mode) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        this.mode = mode;
        this.featureTypeInternal = FeatureTypeInternal.VIDEO_STABILIZATION;
    }

    public final StabilizationMode getMode() {
        return this.mode;
    }

    @Override // androidx.camera.core.featuregroup.GroupableFeature
    public FeatureTypeInternal getFeatureTypeInternal$camera_core_release() {
        return this.featureTypeInternal;
    }

    public String toString() {
        return "VideoStabilizationFeature(mode=" + this.mode.name() + ')';
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
