package com.googlecode.mp4parser.util;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import java.nio.ByteBuffer;

/* loaded from: classes4.dex */
public class Matrix {

    /* renamed from: a */
    double f390a;

    /* renamed from: b */
    double f391b;

    /* renamed from: c */
    double f392c;

    /* renamed from: d */
    double f393d;

    /* renamed from: tx */
    double f394tx;

    /* renamed from: ty */
    double f395ty;

    /* renamed from: u */
    double f396u;

    /* renamed from: v */
    double f397v;

    /* renamed from: w */
    double f398w;
    public static final Matrix ROTATE_0 = new Matrix(1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    public static final Matrix ROTATE_90 = new Matrix(0.0d, 1.0d, -1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    public static final Matrix ROTATE_180 = new Matrix(-1.0d, 0.0d, 0.0d, -1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    public static final Matrix ROTATE_270 = new Matrix(0.0d, -1.0d, 1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);

    public Matrix(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        this.f396u = d5;
        this.f397v = d6;
        this.f398w = d7;
        this.f390a = d;
        this.f391b = d2;
        this.f392c = d3;
        this.f393d = d4;
        this.f394tx = d8;
        this.f395ty = d9;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Matrix matrix = (Matrix) obj;
        return Double.compare(matrix.f390a, this.f390a) == 0 && Double.compare(matrix.f391b, this.f391b) == 0 && Double.compare(matrix.f392c, this.f392c) == 0 && Double.compare(matrix.f393d, this.f393d) == 0 && Double.compare(matrix.f394tx, this.f394tx) == 0 && Double.compare(matrix.f395ty, this.f395ty) == 0 && Double.compare(matrix.f396u, this.f396u) == 0 && Double.compare(matrix.f397v, this.f397v) == 0 && Double.compare(matrix.f398w, this.f398w) == 0;
    }

    public int hashCode() {
        long jDoubleToLongBits = Double.doubleToLongBits(this.f396u);
        long jDoubleToLongBits2 = Double.doubleToLongBits(this.f397v);
        int i = (((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32))) * 31) + ((int) (jDoubleToLongBits2 ^ (jDoubleToLongBits2 >>> 32)));
        long jDoubleToLongBits3 = Double.doubleToLongBits(this.f398w);
        int i2 = (i * 31) + ((int) (jDoubleToLongBits3 ^ (jDoubleToLongBits3 >>> 32)));
        long jDoubleToLongBits4 = Double.doubleToLongBits(this.f390a);
        int i3 = (i2 * 31) + ((int) (jDoubleToLongBits4 ^ (jDoubleToLongBits4 >>> 32)));
        long jDoubleToLongBits5 = Double.doubleToLongBits(this.f391b);
        int i4 = (i3 * 31) + ((int) (jDoubleToLongBits5 ^ (jDoubleToLongBits5 >>> 32)));
        long jDoubleToLongBits6 = Double.doubleToLongBits(this.f392c);
        int i5 = (i4 * 31) + ((int) (jDoubleToLongBits6 ^ (jDoubleToLongBits6 >>> 32)));
        long jDoubleToLongBits7 = Double.doubleToLongBits(this.f393d);
        int i6 = (i5 * 31) + ((int) (jDoubleToLongBits7 ^ (jDoubleToLongBits7 >>> 32)));
        long jDoubleToLongBits8 = Double.doubleToLongBits(this.f394tx);
        int i7 = (i6 * 31) + ((int) (jDoubleToLongBits8 ^ (jDoubleToLongBits8 >>> 32)));
        long jDoubleToLongBits9 = Double.doubleToLongBits(this.f395ty);
        return (i7 * 31) + ((int) (jDoubleToLongBits9 ^ (jDoubleToLongBits9 >>> 32)));
    }

    public String toString() {
        if (equals(ROTATE_0)) {
            return "Rotate 0°";
        }
        if (equals(ROTATE_90)) {
            return "Rotate 90°";
        }
        if (equals(ROTATE_180)) {
            return "Rotate 180°";
        }
        if (equals(ROTATE_270)) {
            return "Rotate 270°";
        }
        return "Matrix{u=" + this.f396u + ", v=" + this.f397v + ", w=" + this.f398w + ", a=" + this.f390a + ", b=" + this.f391b + ", c=" + this.f392c + ", d=" + this.f393d + ", tx=" + this.f394tx + ", ty=" + this.f395ty + '}';
    }

    public static Matrix fromFileOrder(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        return new Matrix(d, d2, d4, d5, d3, d6, d9, d7, d8);
    }

    public static Matrix fromByteBuffer(ByteBuffer byteBuffer) {
        return fromFileOrder(IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint0230(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint0230(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint0230(byteBuffer));
    }

    public void getContent(ByteBuffer byteBuffer) {
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f390a);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f391b);
        IsoTypeWriter.writeFixedPoint0230(byteBuffer, this.f396u);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f392c);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f393d);
        IsoTypeWriter.writeFixedPoint0230(byteBuffer, this.f397v);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f394tx);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f395ty);
        IsoTypeWriter.writeFixedPoint0230(byteBuffer, this.f398w);
    }
}
