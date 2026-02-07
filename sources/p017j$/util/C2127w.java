package p017j$.util;

import java.util.function.DoubleConsumer;
import p017j$.com.android.tools.p018r8.AbstractC1636a;

/* renamed from: j$.util.w */
/* loaded from: classes2.dex */
public final class C2127w implements DoubleConsumer {

    /* renamed from: a */
    public double f1397a;

    /* renamed from: b */
    public double f1398b;
    private long count;
    private double sum;
    private double min = Double.POSITIVE_INFINITY;
    private double max = Double.NEGATIVE_INFINITY;

    public final /* synthetic */ DoubleConsumer andThen(DoubleConsumer doubleConsumer) {
        return AbstractC1636a.m506b(this, doubleConsumer);
    }

    @Override // java.util.function.DoubleConsumer
    public final void accept(double d) {
        this.count++;
        this.f1398b += d;
        m1119b(d);
        this.min = Math.min(this.min, d);
        this.max = Math.max(this.max, d);
    }

    /* renamed from: a */
    public final void m1118a(C2127w c2127w) {
        this.count += c2127w.count;
        this.f1398b += c2127w.f1398b;
        m1119b(c2127w.sum);
        m1119b(c2127w.f1397a);
        this.min = Math.min(this.min, c2127w.min);
        this.max = Math.max(this.max, c2127w.max);
    }

    /* renamed from: b */
    public final void m1119b(double d) {
        double d2 = d - this.f1397a;
        double d3 = this.sum;
        double d4 = d3 + d2;
        this.f1397a = (d4 - d3) - d2;
        this.sum = d4;
    }

    public final String toString() {
        double d;
        String simpleName = C2127w.class.getSimpleName();
        Long lValueOf = Long.valueOf(this.count);
        double d2 = this.sum + this.f1397a;
        if (Double.isNaN(d2) && Double.isInfinite(this.f1398b)) {
            d2 = this.f1398b;
        }
        Double dValueOf = Double.valueOf(d2);
        Double dValueOf2 = Double.valueOf(this.min);
        if (this.count > 0) {
            double d3 = this.sum + this.f1397a;
            if (Double.isNaN(d3) && Double.isInfinite(this.f1398b)) {
                d3 = this.f1398b;
            }
            d = d3 / this.count;
        } else {
            d = 0.0d;
        }
        return String.format("%s{count=%d, sum=%f, min=%f, average=%f, max=%f}", simpleName, lValueOf, dValueOf, dValueOf2, Double.valueOf(d), Double.valueOf(this.max));
    }
}
