package org.telegram.p023ui.Charts;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Charts.data.ChartData;
import org.telegram.p023ui.Charts.data.DoubleLinearChartData;
import org.telegram.p023ui.Charts.view_data.ChartHorizontalLinesData;
import org.telegram.p023ui.Charts.view_data.LineViewData;
import org.telegram.p023ui.Charts.view_data.TransitionParams;

/* loaded from: classes5.dex */
public class DoubleLinearChartView extends BaseChartView {
    public DoubleLinearChartView(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context, resourcesProvider);
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    protected void init() {
        this.useMinHeight = true;
        super.init();
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    protected void drawChart(Canvas canvas) {
        float f;
        float f2;
        float f3;
        if (this.chartData != null) {
            float f4 = this.chartWidth;
            ChartPickerDelegate chartPickerDelegate = this.pickerDelegate;
            float f5 = chartPickerDelegate.pickerEnd;
            float f6 = chartPickerDelegate.pickerStart;
            float f7 = f4 / (f5 - f6);
            float f8 = (f6 * f7) - BaseChartView.HORIZONTAL_PADDING;
            canvas.save();
            int i = this.transitionMode;
            float f9 = 0.0f;
            int i2 = 2;
            int i3 = 1;
            if (i == 2) {
                TransitionParams transitionParams = this.transitionParams;
                float f10 = transitionParams.progress;
                f = f10 > 0.5f ? 0.0f : 1.0f - (f10 * 2.0f);
                canvas.scale((f10 * 2.0f) + 1.0f, 1.0f, transitionParams.f1826pX, transitionParams.f1827pY);
            } else if (i == 1) {
                float f11 = this.transitionParams.progress;
                f = f11 < 0.3f ? 0.0f : f11;
                canvas.save();
                TransitionParams transitionParams2 = this.transitionParams;
                float f12 = transitionParams2.progress;
                canvas.scale(f12, f12, transitionParams2.f1826pX, transitionParams2.f1827pY);
            } else {
                f = i == 3 ? this.transitionParams.progress : 1.0f;
            }
            int i4 = 0;
            int i5 = 0;
            while (i5 < this.lines.size()) {
                LineViewData lineViewData = (LineViewData) this.lines.get(i5);
                if (lineViewData.enabled || lineViewData.alpha != f9) {
                    long[] jArr = lineViewData.line.f1825y;
                    lineViewData.chartPath.reset();
                    ChartData chartData = this.chartData;
                    int i6 = ((int) (BaseChartView.HORIZONTAL_PADDING / (((DoubleLinearChartData) chartData).xPercentage.length < i2 ? 1.0f : ((DoubleLinearChartData) chartData).xPercentage[i3] * f7))) + i3;
                    int iMax = Math.max(i4, this.startXIndex - i6);
                    int iMin = Math.min(((DoubleLinearChartData) this.chartData).xPercentage.length - i3, this.endXIndex + i6);
                    boolean z = true;
                    int i7 = 0;
                    while (iMax <= iMin) {
                        long j = jArr[iMax];
                        if (j < 0) {
                            f3 = f7;
                        } else {
                            ChartData chartData2 = this.chartData;
                            float f13 = (((DoubleLinearChartData) chartData2).xPercentage[iMax] * f7) - f8;
                            float f14 = j * ((DoubleLinearChartData) chartData2).linesK[i5];
                            float f15 = this.currentMinHeight;
                            float f16 = (f14 - f15) / (this.currentMaxHeight - f15);
                            float strokeWidth = lineViewData.paint.getStrokeWidth() / 2.0f;
                            f3 = f7;
                            float measuredHeight = ((getMeasuredHeight() - this.chartBottom) - strokeWidth) - (f16 * (((getMeasuredHeight() - this.chartBottom) - BaseChartView.SIGNATURE_TEXT_HEIGHT) - strokeWidth));
                            if (BaseChartView.USE_LINES) {
                                if (i7 == 0) {
                                    float[] fArr = lineViewData.linesPath;
                                    int i8 = i7 + 1;
                                    fArr[i7] = f13;
                                    i7 += 2;
                                    fArr[i8] = measuredHeight;
                                } else {
                                    float[] fArr2 = lineViewData.linesPath;
                                    fArr2[i7] = f13;
                                    fArr2[i7 + 1] = measuredHeight;
                                    int i9 = i7 + 3;
                                    fArr2[i7 + 2] = f13;
                                    i7 += 4;
                                    fArr2[i9] = measuredHeight;
                                }
                            } else if (z) {
                                lineViewData.chartPath.moveTo(f13, measuredHeight);
                                z = false;
                            } else {
                                lineViewData.chartPath.lineTo(f13, measuredHeight);
                            }
                        }
                        iMax++;
                        f7 = f3;
                    }
                    f2 = f7;
                    if (this.endXIndex - this.startXIndex > 100) {
                        lineViewData.paint.setStrokeCap(Paint.Cap.SQUARE);
                    } else {
                        lineViewData.paint.setStrokeCap(Paint.Cap.ROUND);
                    }
                    lineViewData.paint.setAlpha((int) (lineViewData.alpha * 255.0f * f));
                    if (BaseChartView.USE_LINES) {
                        canvas.drawLines(lineViewData.linesPath, 0, i7, lineViewData.paint);
                    } else {
                        canvas.drawPath(lineViewData.chartPath, lineViewData.paint);
                    }
                } else {
                    f2 = f7;
                }
                i5++;
                f7 = f2;
                i4 = 0;
                f9 = 0.0f;
                i2 = 2;
                i3 = 1;
            }
            canvas.restore();
        }
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
                LineViewData lineViewData = (LineViewData) this.lines.get(i7);
                if (lineViewData.enabled || lineViewData.alpha != 0.0f) {
                    lineViewData.bottomLinePath.reset();
                    int length = ((DoubleLinearChartData) this.chartData).xPercentage.length;
                    long[] jArr = lineViewData.line.f1825y;
                    lineViewData.chartPath.reset();
                    int i8 = 0;
                    int i9 = 0;
                    while (i8 < length) {
                        long j = jArr[i8];
                        if (j < 0) {
                            i3 = i6;
                            i4 = size;
                        } else {
                            ChartData chartData = this.chartData;
                            float f2 = ((DoubleLinearChartData) chartData).xPercentage[i8] * this.pickerWidth;
                            if (BaseChartView.ANIMATE_PICKER_SIZES) {
                                f = this.pickerMaxHeight;
                                i3 = i6;
                                i4 = size;
                            } else {
                                i3 = i6;
                                i4 = size;
                                f = ((DoubleLinearChartData) chartData).maxValue;
                            }
                            float f3 = (1.0f - ((j * ((DoubleLinearChartData) chartData).linesK[i7]) / f)) * (i3 - measuredHeight2);
                            if (BaseChartView.USE_LINES) {
                                if (i9 == 0) {
                                    float[] fArr = lineViewData.linesPathBottom;
                                    int i10 = i9 + 1;
                                    fArr[i9] = f2;
                                    i9 += 2;
                                    fArr[i10] = f3;
                                } else {
                                    float[] fArr2 = lineViewData.linesPathBottom;
                                    fArr2[i9] = f2;
                                    fArr2[i9 + 1] = f3;
                                    int i11 = i9 + 3;
                                    fArr2[i9 + 2] = f2;
                                    i9 += 4;
                                    fArr2[i11] = f3;
                                }
                            } else if (i8 == 0) {
                                lineViewData.bottomLinePath.moveTo(f2, f3);
                            } else {
                                lineViewData.bottomLinePath.lineTo(f2, f3);
                            }
                        }
                        i8++;
                        i6 = i3;
                        size = i4;
                    }
                    i = i6;
                    i2 = size;
                    lineViewData.linesPathBottomSize = i9;
                    if (lineViewData.enabled || lineViewData.alpha != 0.0f) {
                        lineViewData.bottomLinePaint.setAlpha((int) (lineViewData.alpha * 255.0f));
                        if (BaseChartView.USE_LINES) {
                            canvas.drawLines(lineViewData.linesPathBottom, 0, lineViewData.linesPathBottomSize, lineViewData.bottomLinePaint);
                        } else {
                            canvas.drawPath(lineViewData.bottomLinePath, lineViewData.bottomLinePaint);
                        }
                    }
                    i7++;
                    i6 = i;
                    size = i2;
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
    protected void drawSelection(Canvas canvas) {
        int i = this.selectedIndex;
        if (i < 0 || !this.legendShowing) {
            return;
        }
        int i2 = (int) (this.chartActiveLineAlpha * this.selectionA);
        float f = this.chartWidth;
        ChartPickerDelegate chartPickerDelegate = this.pickerDelegate;
        float f2 = chartPickerDelegate.pickerEnd;
        float f3 = chartPickerDelegate.pickerStart;
        float f4 = f / (f2 - f3);
        float f5 = (((DoubleLinearChartData) this.chartData).xPercentage[i] * f4) - ((f3 * f4) - BaseChartView.HORIZONTAL_PADDING);
        this.selectedLinePaint.setAlpha(i2);
        canvas.drawLine(f5, 0.0f, f5, this.chartArea.bottom, this.selectedLinePaint);
        this.tmpN = this.lines.size();
        int i3 = 0;
        while (true) {
            this.tmpI = i3;
            int i4 = this.tmpI;
            if (i4 >= this.tmpN) {
                return;
            }
            LineViewData lineViewData = (LineViewData) this.lines.get(i4);
            if (lineViewData.enabled || lineViewData.alpha != 0.0f) {
                float f6 = lineViewData.line.f1825y[this.selectedIndex] * ((DoubleLinearChartData) this.chartData).linesK[this.tmpI];
                float f7 = this.currentMinHeight;
                float measuredHeight = (getMeasuredHeight() - this.chartBottom) - (((f6 - f7) / (this.currentMaxHeight - f7)) * ((getMeasuredHeight() - this.chartBottom) - BaseChartView.SIGNATURE_TEXT_HEIGHT));
                lineViewData.selectionPaint.setAlpha((int) (lineViewData.alpha * 255.0f * this.selectionA));
                this.selectionBackgroundPaint.setAlpha((int) (lineViewData.alpha * 255.0f * this.selectionA));
                canvas.drawPoint(f5, measuredHeight, lineViewData.selectionPaint);
                canvas.drawPoint(f5, measuredHeight, this.selectionBackgroundPaint);
            }
            i3 = this.tmpI + 1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x007e  */
    @Override // org.telegram.p023ui.Charts.BaseChartView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void drawSignaturesToHorizontalLines(android.graphics.Canvas r19, org.telegram.p023ui.Charts.view_data.ChartHorizontalLinesData r20) {
        /*
            Method dump skipped, instructions count: 356
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Charts.DoubleLinearChartView.drawSignaturesToHorizontalLines(android.graphics.Canvas, org.telegram.ui.Charts.view_data.ChartHorizontalLinesData):void");
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    public LineViewData createLineViewData(ChartData.Line line) {
        return new LineViewData(line, false, this.resourcesProvider);
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    public long findMaxValue(int i, int i2) {
        if (this.lines.isEmpty()) {
            return 0L;
        }
        int size = this.lines.size();
        long j = 0;
        for (int i3 = 0; i3 < size; i3++) {
            long jRMaxQ = ((LineViewData) this.lines.get(i3)).enabled ? (long) (((ChartData.Line) ((DoubleLinearChartData) this.chartData).lines.get(i3)).segmentTree.rMaxQ(i, i2) * ((DoubleLinearChartData) this.chartData).linesK[i3]) : 0L;
            if (jRMaxQ > j) {
                j = jRMaxQ;
            }
        }
        return j;
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    public long findMinValue(int i, int i2) {
        if (this.lines.isEmpty()) {
            return 0L;
        }
        int size = this.lines.size();
        long j = Long.MAX_VALUE;
        for (int i3 = 0; i3 < size; i3++) {
            long jRMinQ = ((LineViewData) this.lines.get(i3)).enabled ? (int) (((ChartData.Line) ((DoubleLinearChartData) this.chartData).lines.get(i3)).segmentTree.rMinQ(i, i2) * ((DoubleLinearChartData) this.chartData).linesK[i3]) : 2147483647L;
            if (jRMinQ < j) {
                j = jRMinQ;
            }
        }
        return j;
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    protected void updatePickerMinMaxHeight() {
        if (BaseChartView.ANIMATE_PICKER_SIZES) {
            int i = 0;
            if (((LineViewData) this.lines.get(0)).enabled) {
                super.updatePickerMinMaxHeight();
                return;
            }
            ArrayList arrayList = this.lines;
            int size = arrayList.size();
            long j = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                LineViewData lineViewData = (LineViewData) obj;
                if (lineViewData.enabled) {
                    long j2 = lineViewData.line.maxValue;
                    if (j2 > j) {
                        j = j2;
                    }
                }
            }
            if (this.lines.size() > 1) {
                j = (long) (j * ((DoubleLinearChartData) this.chartData).linesK[1]);
            }
            if (j > 0) {
                float f = j;
                if (f != this.animatedToPickerMaxHeight) {
                    this.animatedToPickerMaxHeight = f;
                    Animator animator = this.pickerAnimator;
                    if (animator != null) {
                        animator.cancel();
                    }
                    ValueAnimator valueAnimatorCreateAnimator = createAnimator(this.pickerMaxHeight, this.animatedToPickerMaxHeight, new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Charts.DoubleLinearChartView.1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            DoubleLinearChartView.this.pickerMaxHeight = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            DoubleLinearChartView doubleLinearChartView = DoubleLinearChartView.this;
                            doubleLinearChartView.invalidatePickerChart = true;
                            doubleLinearChartView.invalidate();
                        }
                    });
                    this.pickerAnimator = valueAnimatorCreateAnimator;
                    valueAnimatorCreateAnimator.start();
                }
            }
        }
    }

    @Override // org.telegram.p023ui.Charts.BaseChartView
    protected ChartHorizontalLinesData createHorizontalLinesData(long j, long j2, int i) {
        float f;
        ChartData chartData = this.chartData;
        if (((DoubleLinearChartData) chartData).linesK.length < 2) {
            f = 1.0f;
        } else {
            f = ((DoubleLinearChartData) chartData).linesK[((DoubleLinearChartData) chartData).linesK[0] == 1.0f ? (char) 1 : (char) 0];
        }
        return new ChartHorizontalLinesData(j, j2, this.useMinHeight, f, i, this.signaturePaint, this.signaturePaint2);
    }
}
