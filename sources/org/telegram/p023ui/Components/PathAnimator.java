package org.telegram.p023ui.Components;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;

/* loaded from: classes3.dex */
public class PathAnimator {
    private float durationScale;
    private float scale;

    /* renamed from: tx */
    private float f1916tx;

    /* renamed from: ty */
    private float f1917ty;
    private Path path = new Path();
    private float pathTime = -1.0f;
    private ArrayList keyFrames = new ArrayList();

    private static class KeyFrame {
        public ArrayList commands;
        public float time;

        private KeyFrame() {
            this.commands = new ArrayList();
        }
    }

    private static class MoveTo {

        /* renamed from: x */
        public float f1926x;

        /* renamed from: y */
        public float f1927y;

        private MoveTo() {
        }
    }

    private static class LineTo {

        /* renamed from: x */
        public float f1924x;

        /* renamed from: y */
        public float f1925y;

        private LineTo() {
        }
    }

    private static class CurveTo {

        /* renamed from: x */
        public float f1918x;

        /* renamed from: x1 */
        public float f1919x1;

        /* renamed from: x2 */
        public float f1920x2;

        /* renamed from: y */
        public float f1921y;

        /* renamed from: y1 */
        public float f1922y1;

        /* renamed from: y2 */
        public float f1923y2;

        private CurveTo() {
        }
    }

    public PathAnimator(float f, float f2, float f3, float f4) {
        this.scale = f;
        this.f1916tx = f2;
        this.f1917ty = f3;
        this.durationScale = f4;
    }

    public void addSvgKeyFrame(String str, float f) {
        if (str == null) {
            return;
        }
        try {
            KeyFrame keyFrame = new KeyFrame();
            keyFrame.time = f * this.durationScale;
            String[] strArrSplit = str.split(" ");
            int i = 0;
            while (i < strArrSplit.length) {
                char cCharAt = strArrSplit[i].charAt(0);
                if (cCharAt == 'C') {
                    CurveTo curveTo = new CurveTo();
                    curveTo.f1919x1 = (Float.parseFloat(strArrSplit[i + 1]) + this.f1916tx) * this.scale;
                    curveTo.f1922y1 = (Float.parseFloat(strArrSplit[i + 2]) + this.f1917ty) * this.scale;
                    curveTo.f1920x2 = (Float.parseFloat(strArrSplit[i + 3]) + this.f1916tx) * this.scale;
                    curveTo.f1923y2 = (Float.parseFloat(strArrSplit[i + 4]) + this.f1917ty) * this.scale;
                    curveTo.f1918x = (Float.parseFloat(strArrSplit[i + 5]) + this.f1916tx) * this.scale;
                    i += 6;
                    curveTo.f1921y = (Float.parseFloat(strArrSplit[i]) + this.f1917ty) * this.scale;
                    keyFrame.commands.add(curveTo);
                } else if (cCharAt == 'L') {
                    LineTo lineTo = new LineTo();
                    lineTo.f1924x = (Float.parseFloat(strArrSplit[i + 1]) + this.f1916tx) * this.scale;
                    i += 2;
                    lineTo.f1925y = (Float.parseFloat(strArrSplit[i]) + this.f1917ty) * this.scale;
                    keyFrame.commands.add(lineTo);
                } else if (cCharAt == 'M') {
                    MoveTo moveTo = new MoveTo();
                    moveTo.f1926x = (Float.parseFloat(strArrSplit[i + 1]) + this.f1916tx) * this.scale;
                    i += 2;
                    moveTo.f1927y = (Float.parseFloat(strArrSplit[i]) + this.f1917ty) * this.scale;
                    keyFrame.commands.add(moveTo);
                }
                i++;
            }
            this.keyFrames.add(keyFrame);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    public void draw(Canvas canvas, Paint paint, float f) {
        float f2;
        if (this.pathTime != f) {
            this.pathTime = f;
            int size = this.keyFrames.size();
            KeyFrame keyFrame = null;
            KeyFrame keyFrame2 = null;
            for (int i = 0; i < size; i++) {
                KeyFrame keyFrame3 = (KeyFrame) this.keyFrames.get(i);
                if ((keyFrame2 == null || keyFrame2.time < keyFrame3.time) && keyFrame3.time <= f) {
                    keyFrame2 = keyFrame3;
                }
                if ((keyFrame == null || keyFrame.time > keyFrame3.time) && keyFrame3.time >= f) {
                    keyFrame = keyFrame3;
                }
            }
            if (keyFrame == keyFrame2) {
                keyFrame2 = null;
            }
            if (keyFrame2 != null && keyFrame == null) {
                keyFrame = keyFrame2;
                keyFrame2 = null;
            }
            if (keyFrame == null) {
                return;
            }
            if (keyFrame2 != null && keyFrame2.commands.size() != keyFrame.commands.size()) {
                return;
            }
            this.path.reset();
            int size2 = keyFrame.commands.size();
            for (int i2 = 0; i2 < size2; i2++) {
                Object obj = keyFrame2 != null ? keyFrame2.commands.get(i2) : null;
                Object obj2 = keyFrame.commands.get(i2);
                if (obj != null && obj.getClass() != obj2.getClass()) {
                    return;
                }
                if (keyFrame2 != null) {
                    float f3 = keyFrame2.time;
                    f2 = (f - f3) / (keyFrame.time - f3);
                } else {
                    f2 = 1.0f;
                }
                if (obj2 instanceof MoveTo) {
                    MoveTo moveTo = (MoveTo) obj2;
                    MoveTo moveTo2 = (MoveTo) obj;
                    if (moveTo2 != null) {
                        Path path = this.path;
                        float f4 = moveTo2.f1926x;
                        float fDpf2 = AndroidUtilities.dpf2(f4 + ((moveTo.f1926x - f4) * f2));
                        float f5 = moveTo2.f1927y;
                        path.moveTo(fDpf2, AndroidUtilities.dpf2(f5 + ((moveTo.f1927y - f5) * f2)));
                    } else {
                        this.path.moveTo(AndroidUtilities.dpf2(moveTo.f1926x), AndroidUtilities.dpf2(moveTo.f1927y));
                    }
                } else if (obj2 instanceof LineTo) {
                    LineTo lineTo = (LineTo) obj2;
                    LineTo lineTo2 = (LineTo) obj;
                    if (lineTo2 != null) {
                        Path path2 = this.path;
                        float f6 = lineTo2.f1924x;
                        float fDpf22 = AndroidUtilities.dpf2(f6 + ((lineTo.f1924x - f6) * f2));
                        float f7 = lineTo2.f1925y;
                        path2.lineTo(fDpf22, AndroidUtilities.dpf2(f7 + ((lineTo.f1925y - f7) * f2)));
                    } else {
                        this.path.lineTo(AndroidUtilities.dpf2(lineTo.f1924x), AndroidUtilities.dpf2(lineTo.f1925y));
                    }
                } else if (obj2 instanceof CurveTo) {
                    CurveTo curveTo = (CurveTo) obj2;
                    CurveTo curveTo2 = (CurveTo) obj;
                    if (curveTo2 != null) {
                        Path path3 = this.path;
                        float f8 = curveTo2.f1919x1;
                        float fDpf23 = AndroidUtilities.dpf2(f8 + ((curveTo.f1919x1 - f8) * f2));
                        float f9 = curveTo2.f1922y1;
                        float fDpf24 = AndroidUtilities.dpf2(f9 + ((curveTo.f1922y1 - f9) * f2));
                        float f10 = curveTo2.f1920x2;
                        float fDpf25 = AndroidUtilities.dpf2(f10 + ((curveTo.f1920x2 - f10) * f2));
                        float f11 = curveTo2.f1923y2;
                        float fDpf26 = AndroidUtilities.dpf2(f11 + ((curveTo.f1923y2 - f11) * f2));
                        float f12 = curveTo2.f1918x;
                        float fDpf27 = AndroidUtilities.dpf2(f12 + ((curveTo.f1918x - f12) * f2));
                        float f13 = curveTo2.f1921y;
                        path3.cubicTo(fDpf23, fDpf24, fDpf25, fDpf26, fDpf27, AndroidUtilities.dpf2(f13 + ((curveTo.f1921y - f13) * f2)));
                    } else {
                        this.path.cubicTo(AndroidUtilities.dpf2(curveTo.f1919x1), AndroidUtilities.dpf2(curveTo.f1922y1), AndroidUtilities.dpf2(curveTo.f1920x2), AndroidUtilities.dpf2(curveTo.f1923y2), AndroidUtilities.dpf2(curveTo.f1918x), AndroidUtilities.dpf2(curveTo.f1921y));
                    }
                }
            }
            this.path.close();
        }
        canvas.drawPath(this.path, paint);
    }
}
