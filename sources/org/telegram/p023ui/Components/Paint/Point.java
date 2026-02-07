package org.telegram.p023ui.Components.Paint;

import android.graphics.PointF;

/* loaded from: classes6.dex */
public class Point {
    public boolean edge;

    /* renamed from: x */
    public double f1893x;

    /* renamed from: y */
    public double f1894y;

    /* renamed from: z */
    public double f1895z;

    public Point(double d, double d2, double d3) {
        this.f1893x = d;
        this.f1894y = d2;
        this.f1895z = d3;
    }

    public Point(double d, double d2, double d3, boolean z) {
        this.f1893x = d;
        this.f1894y = d2;
        this.f1895z = d3;
        this.edge = z;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Point)) {
            return false;
        }
        Point point = (Point) obj;
        return this.f1893x == point.f1893x && this.f1894y == point.f1894y && this.f1895z == point.f1895z;
    }

    Point multiplySum(Point point, double d) {
        return new Point((this.f1893x + point.f1893x) * d, (this.f1894y + point.f1894y) * d, (this.f1895z + point.f1895z) * d);
    }

    Point add(Point point) {
        return new Point(this.f1893x + point.f1893x, this.f1894y + point.f1894y, this.f1895z + point.f1895z);
    }

    Point substract(Point point) {
        return new Point(this.f1893x - point.f1893x, this.f1894y - point.f1894y, this.f1895z - point.f1895z);
    }

    Point multiplyByScalar(double d) {
        return new Point(this.f1893x * d, this.f1894y * d, this.f1895z * d);
    }

    float getDistanceTo(Point point) {
        return (float) Math.sqrt(Math.pow(this.f1893x - point.f1893x, 2.0d) + Math.pow(this.f1894y - point.f1894y, 2.0d) + Math.pow(this.f1895z - point.f1895z, 2.0d));
    }

    PointF toPointF() {
        return new PointF((float) this.f1893x, (float) this.f1894y);
    }
}
