package org.telegram.p023ui.Components.Paint;

import android.graphics.Bitmap;
import android.graphics.PointF;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;
import org.telegram.p023ui.Components.Point;
import org.telegram.p023ui.Components.Size;

/* loaded from: classes6.dex */
public class PhotoFace {
    private float angle;
    private Point chinPoint;
    private Point eyesCenterPoint;
    private float eyesDistance;
    private Point foreheadPoint;
    private Point mouthPoint;
    private float width;

    public PhotoFace(Face face, Bitmap bitmap, Size size, boolean z) {
        Point pointTransposePoint = null;
        Point pointTransposePoint2 = null;
        Point pointTransposePoint3 = null;
        Point pointTransposePoint4 = null;
        for (Landmark landmark : face.getLandmarks()) {
            PointF position = landmark.getPosition();
            int type = landmark.getType();
            if (type == 4) {
                pointTransposePoint = transposePoint(position, bitmap, size, z);
            } else if (type == 5) {
                pointTransposePoint3 = transposePoint(position, bitmap, size, z);
            } else if (type == 10) {
                pointTransposePoint2 = transposePoint(position, bitmap, size, z);
            } else if (type == 11) {
                pointTransposePoint4 = transposePoint(position, bitmap, size, z);
            }
        }
        if (pointTransposePoint != null && pointTransposePoint2 != null) {
            if (pointTransposePoint.f1929x < pointTransposePoint2.f1929x) {
                Point point = pointTransposePoint2;
                pointTransposePoint2 = pointTransposePoint;
                pointTransposePoint = point;
            }
            this.eyesCenterPoint = new Point((pointTransposePoint.f1929x * 0.5f) + (pointTransposePoint2.f1929x * 0.5f), (pointTransposePoint.f1930y * 0.5f) + (pointTransposePoint2.f1930y * 0.5f));
            this.eyesDistance = (float) Math.hypot(pointTransposePoint2.f1929x - pointTransposePoint.f1929x, pointTransposePoint2.f1930y - pointTransposePoint.f1930y);
            this.angle = (float) Math.toDegrees(Math.atan2(pointTransposePoint2.f1930y - pointTransposePoint.f1930y, pointTransposePoint2.f1929x - pointTransposePoint.f1929x) + 3.141592653589793d);
            float f = this.eyesDistance;
            this.width = 2.35f * f;
            float f2 = f * 0.8f;
            double radians = (float) Math.toRadians(r12 - 90.0f);
            this.foreheadPoint = new Point(this.eyesCenterPoint.f1929x + (((float) Math.cos(radians)) * f2), this.eyesCenterPoint.f1930y + (f2 * ((float) Math.sin(radians))));
        }
        if (pointTransposePoint3 == null || pointTransposePoint4 == null) {
            return;
        }
        if (pointTransposePoint3.f1929x < pointTransposePoint4.f1929x) {
            Point point2 = pointTransposePoint4;
            pointTransposePoint4 = pointTransposePoint3;
            pointTransposePoint3 = point2;
        }
        this.mouthPoint = new Point((pointTransposePoint3.f1929x * 0.5f) + (pointTransposePoint4.f1929x * 0.5f), (pointTransposePoint3.f1930y * 0.5f) + (pointTransposePoint4.f1930y * 0.5f));
        float f3 = this.eyesDistance * 0.7f;
        double radians2 = (float) Math.toRadians(this.angle + 90.0f);
        this.chinPoint = new Point(this.mouthPoint.f1929x + (((float) Math.cos(radians2)) * f3), this.mouthPoint.f1930y + (f3 * ((float) Math.sin(radians2))));
    }

    public boolean isSufficient() {
        return this.eyesCenterPoint != null;
    }

    private Point transposePoint(PointF pointF, Bitmap bitmap, Size size, boolean z) {
        return new Point((size.width * pointF.x) / (z ? bitmap.getHeight() : bitmap.getWidth()), (size.height * pointF.y) / (z ? bitmap.getWidth() : bitmap.getHeight()));
    }

    public Point getPointForAnchor(int i) {
        if (i == 0) {
            return this.foreheadPoint;
        }
        if (i == 1) {
            return this.eyesCenterPoint;
        }
        if (i == 2) {
            return this.mouthPoint;
        }
        if (i != 3) {
            return null;
        }
        return this.chinPoint;
    }

    public float getWidthForAnchor(int i) {
        if (i == 1) {
            return this.eyesDistance;
        }
        return this.width;
    }

    public float getAngle() {
        return this.angle;
    }
}
