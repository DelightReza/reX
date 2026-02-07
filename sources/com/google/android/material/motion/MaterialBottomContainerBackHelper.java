package com.google.android.material.motion;

import android.content.res.Resources;
import android.view.View;
import com.google.android.material.R$dimen;

/* loaded from: classes4.dex */
public class MaterialBottomContainerBackHelper extends MaterialBackAnimationHelper {
    private final float maxScaleXDistance;
    private final float maxScaleYDistance;

    public MaterialBottomContainerBackHelper(View view) {
        super(view);
        Resources resources = view.getResources();
        this.maxScaleXDistance = resources.getDimension(R$dimen.m3_back_progress_bottom_container_max_scale_x_distance);
        this.maxScaleYDistance = resources.getDimension(R$dimen.m3_back_progress_bottom_container_max_scale_y_distance);
    }
}
