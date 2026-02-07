package org.telegram.p023ui.Charts.view_data;

import android.graphics.Paint;
import org.telegram.p023ui.Charts.BaseChartView;
import org.telegram.p023ui.Charts.data.ChartData;

/* loaded from: classes5.dex */
public class StackLinearViewData extends LineViewData {
    public StackLinearViewData(ChartData.Line line) {
        super(line, false);
        this.paint.setStyle(Paint.Style.FILL);
        if (BaseChartView.USE_LINES) {
            this.paint.setAntiAlias(false);
        }
    }
}
