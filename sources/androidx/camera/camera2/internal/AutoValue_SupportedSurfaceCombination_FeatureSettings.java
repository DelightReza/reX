package androidx.camera.camera2.internal;

import android.util.Range;
import androidx.camera.camera2.internal.SupportedSurfaceCombination;

/* loaded from: classes3.dex */
final class AutoValue_SupportedSurfaceCombination_FeatureSettings extends SupportedSurfaceCombination.FeatureSettings {
    private final int getCameraMode;
    private final int getRequiredMaxBitDepth;
    private final Range getTargetFpsRange;
    private final boolean hasVideoCapture;
    private final boolean isFeatureComboInvocation;
    private final boolean isHighSpeedOn;
    private final boolean isPreviewStabilizationOn;
    private final boolean isStrictFpsRequired;
    private final boolean isUltraHdrOn;
    private final boolean requiresFeatureComboQuery;

    AutoValue_SupportedSurfaceCombination_FeatureSettings(int i, boolean z, int i2, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, Range range, boolean z7) {
        this.getCameraMode = i;
        this.hasVideoCapture = z;
        this.getRequiredMaxBitDepth = i2;
        this.isPreviewStabilizationOn = z2;
        this.isUltraHdrOn = z3;
        this.isHighSpeedOn = z4;
        this.isFeatureComboInvocation = z5;
        this.requiresFeatureComboQuery = z6;
        if (range == null) {
            throw new NullPointerException("Null getTargetFpsRange");
        }
        this.getTargetFpsRange = range;
        this.isStrictFpsRequired = z7;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.FeatureSettings
    int getCameraMode() {
        return this.getCameraMode;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.FeatureSettings
    boolean hasVideoCapture() {
        return this.hasVideoCapture;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.FeatureSettings
    int getRequiredMaxBitDepth() {
        return this.getRequiredMaxBitDepth;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.FeatureSettings
    boolean isPreviewStabilizationOn() {
        return this.isPreviewStabilizationOn;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.FeatureSettings
    boolean isUltraHdrOn() {
        return this.isUltraHdrOn;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.FeatureSettings
    boolean isHighSpeedOn() {
        return this.isHighSpeedOn;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.FeatureSettings
    boolean isFeatureComboInvocation() {
        return this.isFeatureComboInvocation;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.FeatureSettings
    boolean requiresFeatureComboQuery() {
        return this.requiresFeatureComboQuery;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.FeatureSettings
    Range getTargetFpsRange() {
        return this.getTargetFpsRange;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.FeatureSettings
    boolean isStrictFpsRequired() {
        return this.isStrictFpsRequired;
    }

    public String toString() {
        return "FeatureSettings{getCameraMode=" + this.getCameraMode + ", hasVideoCapture=" + this.hasVideoCapture + ", getRequiredMaxBitDepth=" + this.getRequiredMaxBitDepth + ", isPreviewStabilizationOn=" + this.isPreviewStabilizationOn + ", isUltraHdrOn=" + this.isUltraHdrOn + ", isHighSpeedOn=" + this.isHighSpeedOn + ", isFeatureComboInvocation=" + this.isFeatureComboInvocation + ", requiresFeatureComboQuery=" + this.requiresFeatureComboQuery + ", getTargetFpsRange=" + this.getTargetFpsRange + ", isStrictFpsRequired=" + this.isStrictFpsRequired + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SupportedSurfaceCombination.FeatureSettings) {
            SupportedSurfaceCombination.FeatureSettings featureSettings = (SupportedSurfaceCombination.FeatureSettings) obj;
            if (this.getCameraMode == featureSettings.getCameraMode() && this.hasVideoCapture == featureSettings.hasVideoCapture() && this.getRequiredMaxBitDepth == featureSettings.getRequiredMaxBitDepth() && this.isPreviewStabilizationOn == featureSettings.isPreviewStabilizationOn() && this.isUltraHdrOn == featureSettings.isUltraHdrOn() && this.isHighSpeedOn == featureSettings.isHighSpeedOn() && this.isFeatureComboInvocation == featureSettings.isFeatureComboInvocation() && this.requiresFeatureComboQuery == featureSettings.requiresFeatureComboQuery() && this.getTargetFpsRange.equals(featureSettings.getTargetFpsRange()) && this.isStrictFpsRequired == featureSettings.isStrictFpsRequired()) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((this.getCameraMode ^ 1000003) * 1000003) ^ (this.hasVideoCapture ? 1231 : 1237)) * 1000003) ^ this.getRequiredMaxBitDepth) * 1000003) ^ (this.isPreviewStabilizationOn ? 1231 : 1237)) * 1000003) ^ (this.isUltraHdrOn ? 1231 : 1237)) * 1000003) ^ (this.isHighSpeedOn ? 1231 : 1237)) * 1000003) ^ (this.isFeatureComboInvocation ? 1231 : 1237)) * 1000003) ^ (this.requiresFeatureComboQuery ? 1231 : 1237)) * 1000003) ^ this.getTargetFpsRange.hashCode()) * 1000003) ^ (this.isStrictFpsRequired ? 1231 : 1237);
    }
}
