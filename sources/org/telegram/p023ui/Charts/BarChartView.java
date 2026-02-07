package org.telegram.p023ui.Charts;

import android.content.Context;
import android.graphics.Canvas;
import org.telegram.p023ui.Charts.data.ChartData;
import org.telegram.p023ui.Charts.view_data.BarViewData;
import org.telegram.p023ui.Charts.view_data.ChartHorizontalLinesData;

/* loaded from: classes5.dex */
public class BarChartView extends BaseChartView {
    @Override // org.telegram.p023ui.Charts.BaseChartView
    protected void drawSelection(Canvas canvas) {
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    protected float getMinDistance() {
        return 0.1f;
    }

    public BarChartView(Context context) {
        super(context);
        this.superDraw = true;
        this.useAlphaSignature = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0093  */
    @Override // org.telegram.p023ui.Charts.BaseChartView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void drawChart(android.graphics.Canvas r25) {
        /*
            Method dump skipped, instructions count: 432
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Charts.BarChartView.drawChart(android.graphics.Canvas):void");
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    protected void drawPickerChart(Canvas canvas) {
        int i;
        int i2;
        int i3;
        int i4;
        float f;
        int measuredHeight = getMeasuredHeight();
        int i5 = BaseChartView.PICKER_PADDING;
        int i6 = measuredHeight - i5;
        int measuredHeight2 = (getMeasuredHeight() - this.pikerHeight) - i5;
        int size = this.lines.size();
        if (this.chartData != null) {
            int i7 = 0;
            while (i7 < size) {
                BarViewData barViewData = (BarViewData) this.lines.get(i7);
                if (barViewData.enabled || barViewData.alpha != 0.0f) {
                    barViewData.bottomLinePath.reset();
                    float[] fArr = this.chartData.xPercentage;
                    int length = fArr.length;
                    float f2 = fArr.length < 2 ? 1.0f : fArr[1] * this.pickerWidth;
                    long[] jArr = barViewData.line.f1825y;
                    float f3 = barViewData.alpha;
                    int i8 = 0;
                    int i9 = 0;
                    while (i8 < length) {
                        long j = jArr[i8];
                        if (j < 0) {
                            i3 = i6;
                            i4 = size;
                        } else {
                            ChartData chartData = this.chartData;
                            i3 = i6;
                            float f4 = chartData.xPercentage[i8] * this.pickerWidth;
                            if (BaseChartView.ANIMATE_PICKER_SIZES) {
                                f = this.pickerMaxHeight;
                                i4 = size;
                            } else {
                                i4 = size;
                                f = chartData.maxValue;
                            }
                            float f5 = (1.0f - ((j / f) * f3)) * (i3 - measuredHeight2);
                            float[] fArr2 = barViewData.linesPath;
                            fArr2[i9] = f4;
                            fArr2[i9 + 1] = f5;
                            int i10 = i9 + 3;
                            fArr2[i9 + 2] = f4;
                            i9 += 4;
                            fArr2[i10] = getMeasuredHeight() - this.chartBottom;
                        }
                        i8++;
                        i6 = i3;
                        size = i4;
                    }
                    i = i6;
                    i2 = size;
                    barViewData.paint.setStrokeWidth(f2 + 2.0f);
                    canvas.drawLines(barViewData.linesPath, 0, i9, barViewData.paint);
                } else {
                    i = i6;
                    i2 = size;
                }
                i7++;
                i6 = i;
                size = i2;
            }
        }
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    public BarViewData createLineViewData(ChartData.Line line) {
        return new BarViewData(line, this.resourcesProvider);
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView, android.view.View
    protected void onDraw(Canvas canvas) {
        tick();
        drawChart(canvas);
        drawBottomLine(canvas);
        this.tmpN = this.horizontalLines.size();
        int i = 0;
        while (true) {
            this.tmpI = i;
            int i2 = this.tmpI;
            if (i2 < this.tmpN) {
                drawHorizontalLines(canvas, (ChartHorizontalLinesData) this.horizontalLines.get(i2));
                drawSignaturesToHorizontalLines(canvas, (ChartHorizontalLinesData) this.horizontalLines.get(this.tmpI));
                i = this.tmpI + 1;
            } else {
                drawBottomSignature(canvas);
                drawPicker(canvas);
                drawSelection(canvas);
                super.onDraw(canvas);
                return;
            }
        }
    }
}
