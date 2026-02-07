package com.google.android.material.motion;

import android.content.res.Resources;
import android.view.View;
import com.google.android.material.R$dimen;

/* loaded from: classes4.dex */
public class MaterialSideContainerBackHelper extends MaterialBackAnimationHelper {
    private final float maxScaleXDistanceGrow;
    private final float maxScaleXDistanceShrink;
    private final float maxScaleYDistance;

    public MaterialSideContainerBackHelper(View view) {
        super(view);
        Resources resources = view.getResources();
        this.maxScaleXDistanceShrink = resources.getDimension(R$dimen.m3_back_progress_side_container_max_scale_x_distance_shrink);
        this.maxScaleXDistanceGrow = resources.getDimension(R$dimen.m3_back_progress_side_container_max_scale_x_distance_grow);
        this.maxScaleYDistance = resources.getDimension(R$dimen.m3_back_progress_side_container_max_scale_y_distance);
    }
}
