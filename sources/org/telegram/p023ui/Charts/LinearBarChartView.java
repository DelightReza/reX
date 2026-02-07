package org.telegram.p023ui.Charts;

import android.content.Context;
import android.graphics.Canvas;
import org.telegram.p023ui.Charts.data.ChartData;
import org.telegram.p023ui.Charts.view_data.LineViewData;

/* loaded from: classes5.dex */
public class LinearBarChartView extends BaseChartView {
    public LinearBarChartView(Context context) {
        super(context);
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    protected void init() {
        this.useMinHeight = true;
        super.init();
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01ec  */
    @Override // org.telegram.p023ui.Charts.BaseChartView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void drawChart(android.graphics.Canvas r24) {
        /*
            Method dump skipped, instructions count: 511
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Charts.LinearBarChartView.drawChart(android.graphics.Canvas):void");
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    protected void drawPickerChart(Canvas canvas) {
        int i;
        float f;
        int i2;
        float f2;
        float f3;
        float f4;
        float f5;
        getMeasuredHeight();
        getMeasuredHeight();
        int size = this.lines.size();
        ChartData chartData = this.chartData;
        if (chartData != null) {
            float[] fArr = chartData.xPercentage;
            float f6 = fArr.length < 2 ? 1.0f : fArr[1] * this.pickerWidth;
            int i3 = 0;
            while (i3 < size) {
                LineViewData lineViewData = (LineViewData) this.lines.get(i3);
                if (lineViewData.enabled || lineViewData.alpha != 0.0f) {
                    lineViewData.bottomLinePath.reset();
                    int length = this.chartData.xPercentage.length;
                    long[] jArr = lineViewData.line.f1825y;
                    lineViewData.chartPath.reset();
                    int i4 = 0;
                    int i5 = 0;
                    while (i4 < length) {
                        long j = jArr[i4];
                        if (j < 0) {
                            i2 = size;
                            f2 = f6;
                        } else {
                            ChartData chartData2 = this.chartData;
                            float f7 = chartData2.xPercentage[i4] * this.pickerWidth;
                            boolean z = BaseChartView.ANIMATE_PICKER_SIZES;
                            if (z) {
                                i2 = size;
                                f3 = this.pickerMaxHeight;
                                f2 = f6;
                            } else {
                                i2 = size;
                                f2 = f6;
                                f3 = chartData2.maxValue;
                            }
                            if (z) {
                                f5 = this.pickerMinHeight;
                                f4 = f3;
                            } else {
                                f4 = f3;
                                f5 = chartData2.minValue;
                            }
                            float f8 = (1.0f - ((j - f5) / (f4 - f5))) * this.pikerHeight;
                            if (!BaseChartView.USE_LINES) {
                                if (i4 == 0) {
                                    lineViewData.bottomLinePath.moveTo(f7 - (f2 / 2.0f), f8);
                                } else {
                                    lineViewData.bottomLinePath.lineTo(f7 - (f2 / 2.0f), f8);
                                }
                                lineViewData.bottomLinePath.lineTo(f7 + (f2 / 2.0f), f8);
                            } else if (i5 == 0) {
                                float[] fArr2 = lineViewData.linesPathBottom;
                                float f9 = f2 / 2.0f;
                                fArr2[i5] = f7 - f9;
                                fArr2[i5 + 1] = f8;
                                float f10 = f7 + f9;
                                fArr2[i5 + 2] = f10;
                                fArr2[i5 + 3] = f8;
                                int i6 = i5 + 5;
                                fArr2[i5 + 4] = f10;
                                i5 += 6;
                                fArr2[i6] = f8;
                            } else if (i4 == length - 1) {
                                float[] fArr3 = lineViewData.linesPathBottom;
                                float f11 = f2 / 2.0f;
                                float f12 = f7 - f11;
                                fArr3[i5] = f12;
                                fArr3[i5 + 1] = f8;
                                fArr3[i5 + 2] = f12;
                                fArr3[i5 + 3] = f8;
                                float f13 = f7 + f11;
                                fArr3[i5 + 4] = f13;
                                fArr3[i5 + 5] = f8;
                                fArr3[i5 + 6] = f13;
                                fArr3[i5 + 7] = f8;
                                int i7 = i5 + 9;
                                fArr3[i5 + 8] = f13;
                                i5 += 10;
                                fArr3[i7] = 0.0f;
                            } else {
                                float[] fArr4 = lineViewData.linesPathBottom;
                                float f14 = f2 / 2.0f;
                                float f15 = f7 - f14;
                                fArr4[i5] = f15;
                                fArr4[i5 + 1] = f8;
                                fArr4[i5 + 2] = f15;
                                fArr4[i5 + 3] = f8;
                                float f16 = f7 + f14;
                                fArr4[i5 + 4] = f16;
                                fArr4[i5 + 5] = f8;
                                int i8 = i5 + 7;
                                fArr4[i5 + 6] = f16;
                                i5 += 8;
                                fArr4[i8] = f8;
                            }
                        }
                        i4++;
                        size = i2;
                        f6 = f2;
                    }
                    i = size;
                    f = f6;
                    lineViewData.linesPathBottomSize = i5;
                    if (lineViewData.enabled || lineViewData.alpha != 0.0f) {
                        lineViewData.bottomLinePaint.setAlpha((int) (lineViewData.alpha * 255.0f));
                        if (BaseChartView.USE_LINES) {
                            canvas.drawLines(lineViewData.linesPathBottom, 0, lineViewData.linesPathBottomSize, lineViewData.bottomLinePaint);
                        } else {
                            canvas.drawPath(lineViewData.bottomLinePath, lineViewData.bottomLinePaint);
                        }
                    }
                } else {
                    i = size;
                    f = f6;
                }
                i3++;
                size = i;
                f6 = f;
            }
        }
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    public LineViewData createLineViewData(ChartData.Line line) {
        return new LineViewData(line, true);
    }
}
