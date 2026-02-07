package androidx.camera.camera2.internal;

import androidx.camera.camera2.internal.SupportedSurfaceCombination;
import java.util.List;

/* renamed from: androidx.camera.camera2.internal.AutoValue_SupportedSurfaceCombination_BestSizesAndMaxFpsForConfigs */
/* loaded from: classes3.dex */
final class C0113xf44dce2 extends SupportedSurfaceCombination.BestSizesAndMaxFpsForConfigs {
    private final List bestSizes;
    private final List bestSizesForStreamUseCase;
    private final int maxFpsForAllSizes;
    private final int maxFpsForBestSizes;
    private final int maxFpsForStreamUseCase;

    C0113xf44dce2(List list, List list2, int i, int i2, int i3) {
        this.bestSizes = list;
        this.bestSizesForStreamUseCase = list2;
        this.maxFpsForBestSizes = i;
        this.maxFpsForStreamUseCase = i2;
        this.maxFpsForAllSizes = i3;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.BestSizesAndMaxFpsForConfigs
    List getBestSizes() {
        return this.bestSizes;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.BestSizesAndMaxFpsForConfigs
    List getBestSizesForStreamUseCase() {
        return this.bestSizesForStreamUseCase;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.BestSizesAndMaxFpsForConfigs
    int getMaxFpsForBestSizes() {
        return this.maxFpsForBestSizes;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.BestSizesAndMaxFpsForConfigs
    int getMaxFpsForStreamUseCase() {
        return this.maxFpsForStreamUseCase;
    }

    @Override // androidx.camera.camera2.internal.SupportedSurfaceCombination.BestSizesAndMaxFpsForConfigs
    int getMaxFpsForAllSizes() {
        return this.maxFpsForAllSizes;
    }

    public String toString() {
        return "BestSizesAndMaxFpsForConfigs{bestSizes=" + this.bestSizes + ", bestSizesForStreamUseCase=" + this.bestSizesForStreamUseCase + ", maxFpsForBestSizes=" + this.maxFpsForBestSizes + ", maxFpsForStreamUseCase=" + this.maxFpsForStreamUseCase + ", maxFpsForAllSizes=" + this.maxFpsForAllSizes + "}";
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof SupportedSurfaceCombination.BestSizesAndMaxFpsForConfigs) {
            SupportedSurfaceCombination.BestSizesAndMaxFpsForConfigs bestSizesAndMaxFpsForConfigs = (SupportedSurfaceCombination.BestSizesAndMaxFpsForConfigs) obj;
            List list = this.bestSizes;
            if (list != null ? list.equals(bestSizesAndMaxFpsForConfigs.getBestSizes()) : bestSizesAndMaxFpsForConfigs.getBestSizes() == null) {
                List list2 = this.bestSizesForStreamUseCase;
                if (list2 != null ? list2.equals(bestSizesAndMaxFpsForConfigs.getBestSizesForStreamUseCase()) : bestSizesAndMaxFpsForConfigs.getBestSizesForStreamUseCase() == null) {
                    if (this.maxFpsForBestSizes == bestSizesAndMaxFpsForConfigs.getMaxFpsForBestSizes() && this.maxFpsForStreamUseCase == bestSizesAndMaxFpsForConfigs.getMaxFpsForStreamUseCase() && this.maxFpsForAllSizes == bestSizesAndMaxFpsForConfigs.getMaxFpsForAllSizes()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int hashCode() {
        List list = this.bestSizes;
        int iHashCode = ((list == null ? 0 : list.hashCode()) ^ 1000003) * 1000003;
        List list2 = this.bestSizesForStreamUseCase;
        return ((((((iHashCode ^ (list2 != null ? list2.hashCode() : 0)) * 1000003) ^ this.maxFpsForBestSizes) * 1000003) ^ this.maxFpsForStreamUseCase) * 1000003) ^ this.maxFpsForAllSizes;
    }
}
