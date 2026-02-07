package org.telegram.p023ui.Charts;

import android.animation.Animator;
import org.telegram.p023ui.Charts.data.ChartData;
import org.telegram.p023ui.Charts.view_data.StackLinearViewData;

/* loaded from: classes5.dex */
public class PieChartViewData extends StackLinearViewData {
    Animator animator;
    float drawingPart;
    float selectionA;

    public PieChartViewData(ChartData.Line line) {
        super(line);
    }
}
