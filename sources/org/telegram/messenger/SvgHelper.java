package org.telegram.messenger;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import androidx.core.graphics.ColorUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.xml.parsers.SAXParserFactory;
import org.mvel2.Operator;
import org.mvel2.asm.Opcodes;
import org.mvel2.asm.TypeReference;
import org.mvel2.asm.signature.SignatureVisitor;
import org.telegram.messenger.SvgHelper;
import org.telegram.messenger.wallpaper.WallpaperGiftPatternPosition;
import org.telegram.p023ui.ActionBar.Theme;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes.dex */
public class SvgHelper {
    private static final Pattern SPLIT_BOUNDARY;
    private static final double[] pow10 = new double[128];

    /* loaded from: classes4.dex */
    public interface SvgResult {
        Bitmap getBitmap();

        SvgDrawable getDrawable();

        List<WallpaperGiftPatternPosition> getGiftPatternPositions();
    }

    private static void drawArc(Path path, float f, float f2, float f3, float f4, float f5, float f6, float f7, int i, int i2) {
    }

    /* loaded from: classes4.dex */
    private static class Line {

        /* renamed from: x1 */
        float f1464x1;

        /* renamed from: x2 */
        float f1465x2;

        /* renamed from: y1 */
        float f1466y1;

        /* renamed from: y2 */
        float f1467y2;

        public Line(float f, float f2, float f3, float f4) {
            this.f1464x1 = f;
            this.f1466y1 = f2;
            this.f1465x2 = f3;
            this.f1467y2 = f4;
        }
    }

    /* loaded from: classes4.dex */
    public static class Circle {
        float rad;

        /* renamed from: x1 */
        float f1462x1;

        /* renamed from: y1 */
        float f1463y1;

        public Circle(float f, float f2, float f3) {
            this.f1462x1 = f;
            this.f1463y1 = f2;
            this.rad = f3;
        }
    }

    /* loaded from: classes4.dex */
    private static class Oval {
        RectF rect;

        public Oval(RectF rectF) {
            this.rect = rectF;
        }
    }

    /* loaded from: classes4.dex */
    private static class RoundRect {
        RectF rect;

        /* renamed from: rx */
        float f1470rx;

        public RoundRect(RectF rectF, float f) {
            this.rect = rectF;
            this.f1470rx = f;
        }
    }

    public static class SvgDrawable extends Drawable {
        private static float gradientWidth;
        private static long lastUpdateTime;
        private static WeakReference<Drawable> shiftDrawable;
        private static Runnable shiftRunnable;
        private static float totalTranslation;
        private Paint backgroundPaint;
        private float colorAlpha;
        private int currentColorKey;
        private Theme.ResourcesProvider currentResourcesProvider;
        protected int height;
        private Integer overrideColor;
        private Paint overridePaint;
        private ImageReceiver parentImageReceiver;
        protected int width;
        private static final int[] parentPosition = new int[2];
        private static boolean lite = LiteMode.isEnabled(32);
        protected ArrayList<Object> commands = new ArrayList<>();
        protected HashMap<Object, Paint> paints = new HashMap<>();
        private final Bitmap[] backgroundBitmap = new Bitmap[3];
        private final Canvas[] backgroundCanvas = new Canvas[3];
        private final LinearGradient[] placeholderGradient = new LinearGradient[3];
        private final Matrix[] placeholderMatrix = new Matrix[3];
        private final int[] currentColor = new int[2];
        private float crossfadeAlpha = 1.0f;
        SparseArray<Paint> overridePaintByPosition = new SparseArray<>();
        private boolean aspectFill = true;
        private boolean aspectCenter = false;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -2;
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }

        public static void updateLiteValues() {
            lite = LiteMode.isEnabled(32);
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.width;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.height;
        }

        public void setAspectFill(boolean z) {
            this.aspectFill = z;
        }

        public void setAspectCenter(boolean z) {
            this.aspectCenter = z;
        }

        public void overrideWidthAndHeight(int i, int i2) {
            this.width = i;
            this.height = i2;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            drawInternal(canvas, false, 0, System.currentTimeMillis(), getBounds().left, getBounds().top, getBounds().width(), getBounds().height());
        }

        public void drawInternal(Canvas canvas, boolean z, int i, long j, float f, float f2, float f3, float f4) {
            long j2;
            int i2;
            int i3 = this.currentColorKey;
            if (i3 >= 0) {
                setupGradient(i3, this.currentResourcesProvider, this.colorAlpha, z);
            }
            float scale = getScale((int) f3, (int) f4);
            if (this.placeholderGradient[i] != null) {
                float f5 = gradientWidth;
                if (f5 > 0.0f && lite) {
                    if (z) {
                        long j3 = j - lastUpdateTime;
                        j2 = j3 <= 64 ? j3 : 64L;
                        if (j2 > 0) {
                            lastUpdateTime = j;
                            totalTranslation += (j2 * f5) / 1800.0f;
                            while (true) {
                                float f6 = totalTranslation;
                                float f7 = gradientWidth;
                                if (f6 < f7 * 2.0f) {
                                    break;
                                } else {
                                    totalTranslation = f6 - (f7 * 2.0f);
                                }
                            }
                        }
                    } else if (shiftRunnable == null || shiftDrawable.get() == this) {
                        long j4 = j - lastUpdateTime;
                        j2 = j4 <= 64 ? j4 : 64L;
                        long j5 = j2 >= 0 ? j2 : 0L;
                        lastUpdateTime = j;
                        totalTranslation += (j5 * gradientWidth) / 1800.0f;
                        while (true) {
                            float f8 = totalTranslation;
                            float f9 = gradientWidth;
                            if (f8 < f9 / 2.0f) {
                                break;
                            } else {
                                totalTranslation = f8 - f9;
                            }
                        }
                        shiftDrawable = new WeakReference<>(this);
                        Runnable runnable = shiftRunnable;
                        if (runnable != null) {
                            AndroidUtilities.cancelRunOnUIThread(runnable);
                        }
                        Runnable runnable2 = new Runnable() { // from class: org.telegram.messenger.SvgHelper$SvgDrawable$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SvgHelper.SvgDrawable.shiftRunnable = null;
                            }
                        };
                        shiftRunnable = runnable2;
                        AndroidUtilities.runOnUIThread(runnable2, ((int) (1000.0f / AndroidUtilities.screenRefreshRate)) - 1);
                    }
                    ImageReceiver imageReceiver = this.parentImageReceiver;
                    if (imageReceiver == null || z) {
                        i2 = 0;
                    } else {
                        int[] iArr = parentPosition;
                        imageReceiver.getParentPosition(iArr);
                        i2 = iArr[0];
                    }
                    int i4 = z ? i + 1 : 0;
                    Matrix matrix = this.placeholderMatrix[i4];
                    if (matrix != null) {
                        matrix.reset();
                        if (z) {
                            this.placeholderMatrix[i4].postTranslate(((-i2) + totalTranslation) - f, 0.0f);
                        } else {
                            this.placeholderMatrix[i4].postTranslate(((-i2) + totalTranslation) - f, 0.0f);
                        }
                        float f10 = 1.0f / scale;
                        this.placeholderMatrix[i4].postScale(f10, f10);
                        this.placeholderGradient[i4].setLocalMatrix(this.placeholderMatrix[i4]);
                        ImageReceiver imageReceiver2 = this.parentImageReceiver;
                        if (imageReceiver2 != null && !z) {
                            imageReceiver2.invalidate();
                        }
                    }
                }
            }
            canvas.save();
            canvas.translate(f, f2);
            if (!this.aspectFill || this.aspectCenter) {
                canvas.translate((f3 - (this.width * scale)) / 2.0f, (f4 - (this.height * scale)) / 2.0f);
            }
            canvas.scale(scale, scale);
            int size = this.commands.size();
            for (int i5 = 0; i5 < size; i5++) {
                Object obj = this.commands.get(i5);
                if (obj instanceof Matrix) {
                    canvas.save();
                    canvas.concat((Matrix) obj);
                } else if (obj == null) {
                    canvas.restore();
                } else {
                    Paint paint = this.overridePaintByPosition.get(i5);
                    if (paint == null) {
                        paint = this.overridePaint;
                    }
                    if (z) {
                        paint = this.backgroundPaint;
                    } else if (paint == null) {
                        paint = this.paints.get(obj);
                    }
                    int alpha = paint.getAlpha();
                    paint.setAlpha((int) (this.crossfadeAlpha * alpha));
                    if (obj instanceof Path) {
                        canvas.drawPath((Path) obj, paint);
                    } else if (obj instanceof Rect) {
                        canvas.drawRect((Rect) obj, paint);
                    } else if (obj instanceof RectF) {
                        canvas.drawRect((RectF) obj, paint);
                    } else if (obj instanceof Line) {
                        Line line = (Line) obj;
                        canvas.drawLine(line.f1464x1, line.f1466y1, line.f1465x2, line.f1467y2, paint);
                    } else if (obj instanceof Circle) {
                        Circle circle = (Circle) obj;
                        canvas.drawCircle(circle.f1462x1, circle.f1463y1, circle.rad, paint);
                    } else if (obj instanceof Oval) {
                        canvas.drawOval(((Oval) obj).rect, paint);
                    } else if (obj instanceof RoundRect) {
                        RoundRect roundRect = (RoundRect) obj;
                        RectF rectF = roundRect.rect;
                        float f11 = roundRect.f1470rx;
                        canvas.drawRoundRect(rectF, f11, f11, paint);
                    }
                    paint.setAlpha(alpha);
                }
            }
            canvas.restore();
        }

        public float getScale(int i, int i2) {
            float f = i / this.width;
            float f2 = i2 / this.height;
            return this.aspectFill ? Math.max(f, f2) : Math.min(f, f2);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            this.crossfadeAlpha = i / 255.0f;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addCommand(Object obj, Paint paint) {
            this.commands.add(obj);
            this.paints.put(obj, new Paint(paint));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addCommand(Object obj) {
            this.commands.add(obj);
        }

        public void setParent(ImageReceiver imageReceiver) {
            this.parentImageReceiver = imageReceiver;
        }

        public void setupGradient(int i, float f, boolean z) {
            setupGradient(i, null, f, z);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r16v1, types: [android.graphics.LinearGradient] */
        /* JADX WARN: Type inference failed for: r28v1 */
        /* JADX WARN: Type inference failed for: r28v10 */
        /* JADX WARN: Type inference failed for: r28v11 */
        /* JADX WARN: Type inference failed for: r28v12 */
        /* JADX WARN: Type inference failed for: r28v13 */
        /* JADX WARN: Type inference failed for: r28v14 */
        /* JADX WARN: Type inference failed for: r28v2 */
        /* JADX WARN: Type inference failed for: r28v3 */
        /* JADX WARN: Type inference failed for: r28v4 */
        /* JADX WARN: Type inference failed for: r28v5 */
        /* JADX WARN: Type inference failed for: r28v6 */
        /* JADX WARN: Type inference failed for: r28v7 */
        /* JADX WARN: Type inference failed for: r28v8 */
        /* JADX WARN: Type inference failed for: r28v9 */
        public void setupGradient(int i, Theme.ResourcesProvider resourcesProvider, float f, boolean z) {
            BitmapShader bitmapShader;
            Integer num = this.overrideColor;
            int color = num == null ? Theme.getColor(i, resourcesProvider) : num.intValue();
            this.currentResourcesProvider = resourcesProvider;
            int[] iArr = this.currentColor;
            if (iArr[z ? 1 : 0] != color) {
                this.colorAlpha = f;
                this.currentColorKey = i;
                iArr[z ? 1 : 0] = color;
                gradientWidth = AndroidUtilities.displaySize.x * 2;
                if (!lite) {
                    int alphaComponent = ColorUtils.setAlphaComponent(color, 70);
                    if (z) {
                        if (this.backgroundPaint == null) {
                            this.backgroundPaint = new Paint(1);
                        }
                        this.backgroundPaint.setShader(null);
                        this.backgroundPaint.setColor(alphaComponent);
                        return;
                    }
                    for (Paint paint : this.paints.values()) {
                        paint.setShader(null);
                        paint.setColor(alphaComponent);
                    }
                    return;
                }
                float fM1146dp = AndroidUtilities.m1146dp(180.0f) / gradientWidth;
                int iArgb = Color.argb((int) ((Color.alpha(color) / 2) * this.colorAlpha), Color.red(color), Color.green(color), Color.blue(color));
                float f2 = (1.0f - fM1146dp) / 2.0f;
                LinearGradient[] linearGradientArr = this.placeholderGradient;
                float f3 = fM1146dp / 2.0f;
                Shader.TileMode tileMode = Shader.TileMode.REPEAT;
                linearGradientArr[z ? 1 : 0] = new LinearGradient(0.0f, 0.0f, gradientWidth, 0.0f, new int[]{0, 0, iArgb, 0, 0}, new float[]{0.0f, f2 - f3, f2, f3 + f2, 1.0f}, tileMode);
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 28) {
                    bitmapShader = new LinearGradient(0.0f, 0.0f, gradientWidth, 0.0f, new int[]{iArgb, iArgb}, (float[]) null, tileMode);
                } else {
                    Bitmap[] bitmapArr = this.backgroundBitmap;
                    if (bitmapArr[z ? 1 : 0] == null) {
                        bitmapArr[z ? 1 : 0] = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
                        this.backgroundCanvas[z ? 1 : 0] = new Canvas(this.backgroundBitmap[z ? 1 : 0]);
                    }
                    this.backgroundCanvas[z ? 1 : 0].drawColor(iArgb);
                    bitmapShader = new BitmapShader(this.backgroundBitmap[z ? 1 : 0], tileMode, tileMode);
                }
                this.placeholderMatrix[z ? 1 : 0] = new Matrix();
                this.placeholderGradient[z ? 1 : 0].setLocalMatrix(this.placeholderMatrix[z ? 1 : 0]);
                if (z) {
                    if (this.backgroundPaint == null) {
                        this.backgroundPaint = new Paint(1);
                    }
                    if (i2 <= 22) {
                        this.backgroundPaint.setShader(bitmapShader);
                        return;
                    } else {
                        this.backgroundPaint.setShader(new ComposeShader(this.placeholderGradient[z ? 1 : 0], bitmapShader, PorterDuff.Mode.ADD));
                        return;
                    }
                }
                for (Paint paint2 : this.paints.values()) {
                    if (Build.VERSION.SDK_INT <= 22) {
                        paint2.setShader(bitmapShader);
                    } else {
                        paint2.setShader(new ComposeShader(this.placeholderGradient[z ? 1 : 0], bitmapShader, PorterDuff.Mode.ADD));
                    }
                }
            }
        }

        public void setColorKey(int i) {
            this.currentColorKey = i;
        }

        public void setColorKey(int i, Theme.ResourcesProvider resourcesProvider) {
            this.currentColorKey = i;
            this.currentResourcesProvider = resourcesProvider;
        }

        public void setColor(int i) {
            this.overrideColor = Integer.valueOf(i);
        }

        public void setPaint(Paint paint) {
            this.overridePaint = paint;
        }

        public void setPaint(Paint paint, int i) {
            this.overridePaintByPosition.put(i, paint);
        }

        public void copyCommandFromPosition(int i) {
            ArrayList<Object> arrayList = this.commands;
            arrayList.add(arrayList.get(i));
        }

        public SvgDrawable clone() {
            SvgDrawable svgDrawable = new SvgDrawable();
            for (int i = 0; i < this.commands.size(); i++) {
                svgDrawable.commands.add(this.commands.get(i));
                Paint paint = this.paints.get(this.commands.get(i));
                if (paint != null) {
                    Paint paint2 = new Paint();
                    paint2.setColor(paint.getColor());
                    paint2.setStrokeCap(paint.getStrokeCap());
                    paint2.setStrokeJoin(paint.getStrokeJoin());
                    paint2.setStrokeWidth(paint.getStrokeWidth());
                    paint2.setStyle(paint.getStyle());
                    svgDrawable.paints.put(this.commands.get(i), paint2);
                }
            }
            svgDrawable.width = this.width;
            svgDrawable.height = this.height;
            return svgDrawable;
        }
    }

    public static Bitmap getBitmap(int i, int i2, int i3, int i4) {
        return getBitmap(i, i2, i3, i4, 1.0f);
    }

    public static Bitmap getBitmap(int i, int i2, int i3, int i4, float f) throws Resources.NotFoundException, IOException {
        try {
            InputStream inputStreamOpenRawResource = ApplicationLoader.applicationContext.getResources().openRawResource(i);
            try {
                XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                SVGHandler sVGHandler = new SVGHandler(i2, i3, Integer.valueOf(i4), false, f);
                xMLReader.setContentHandler(sVGHandler);
                xMLReader.parse(new InputSource(inputStreamOpenRawResource));
                Bitmap bitmap = sVGHandler.getBitmap();
                if (inputStreamOpenRawResource != null) {
                    inputStreamOpenRawResource.close();
                }
                return bitmap;
            } finally {
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    public static Bitmap getBitmap(InputStream inputStream, int i, int i2, Integer num, float f) throws IOException {
        try {
            try {
                if (inputStream == null) {
                    return null;
                }
                try {
                    XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                    SVGHandler sVGHandler = new SVGHandler(i, i2, num, false, f);
                    xMLReader.setContentHandler(sVGHandler);
                    xMLReader.parse(new InputSource(inputStream));
                    Bitmap bitmap = sVGHandler.getBitmap();
                    inputStream.close();
                    return bitmap;
                } catch (Exception e) {
                    FileLog.m1160e(e);
                    inputStream.close();
                    return null;
                }
            } finally {
            }
        } catch (Exception e2) {
            FileLog.m1160e(e2);
            return null;
        }
    }

    public static Bitmap getBitmap(InputStream inputStream, int i, int i2, boolean z) throws SAXException, IOException {
        try {
            XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            SVGHandler sVGHandler = new SVGHandler(i, i2, z ? -1 : null, false, 1.0f);
            xMLReader.setContentHandler(sVGHandler);
            xMLReader.parse(new InputSource(inputStream));
            return sVGHandler.getBitmap();
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    public static Bitmap getBitmap(File file, int i, int i2, boolean z) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                SVGHandler sVGHandler = new SVGHandler(i, i2, z ? -1 : null, false, 1.0f);
                if (!z) {
                    sVGHandler.alphaOnly = true;
                }
                xMLReader.setContentHandler(sVGHandler);
                xMLReader.parse(new InputSource(fileInputStream));
                Bitmap bitmap = sVGHandler.getBitmap();
                fileInputStream.close();
                return bitmap;
            } finally {
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    public static SvgResult getSvgBitmap(File file, int i, int i2, boolean z) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                SVGHandler sVGHandler = new SVGHandler(i, i2, z ? -1 : null, false, 1.0f);
                if (!z) {
                    sVGHandler.alphaOnly = true;
                }
                xMLReader.setContentHandler(sVGHandler);
                xMLReader.parse(new InputSource(fileInputStream));
                fileInputStream.close();
                return sVGHandler;
            } finally {
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    public static Bitmap getBitmap(String str, int i, int i2, boolean z) throws SAXException, IOException {
        try {
            XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            SVGHandler sVGHandler = new SVGHandler(i, i2, z ? -1 : null, false, 1.0f);
            xMLReader.setContentHandler(sVGHandler);
            xMLReader.parse(new InputSource(new StringReader(str)));
            return sVGHandler.getBitmap();
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    public static SvgDrawable getDrawable(String str) throws SAXException, IOException {
        try {
            XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            SVGHandler sVGHandler = new SVGHandler(0, 0, null, true, 1.0f);
            xMLReader.setContentHandler(sVGHandler);
            xMLReader.parse(new InputSource(new StringReader(str)));
            return sVGHandler.getDrawable();
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    public static SvgDrawable getDrawable(int i, Integer num) throws SAXException, IOException {
        try {
            XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            SVGHandler sVGHandler = new SVGHandler(0, 0, num, true, 1.0f);
            xMLReader.setContentHandler(sVGHandler);
            xMLReader.parse(new InputSource(ApplicationLoader.applicationContext.getResources().openRawResource(i)));
            return sVGHandler.getDrawable();
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    public static SvgDrawable getDrawableByPath(String str, int i, int i2) {
        try {
            Path pathDoPath = doPath(str);
            SvgDrawable svgDrawable = new SvgDrawable();
            svgDrawable.commands.add(pathDoPath);
            svgDrawable.paints.put(pathDoPath, new Paint(1));
            svgDrawable.width = i;
            svgDrawable.height = i2;
            return svgDrawable;
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    public static SvgDrawable getDrawableByPath(Path path, int i, int i2) {
        try {
            SvgDrawable svgDrawable = new SvgDrawable();
            svgDrawable.commands.add(path);
            svgDrawable.paints.put(path, new Paint(1));
            svgDrawable.width = i;
            svgDrawable.height = i2;
            return svgDrawable;
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    public static Bitmap getBitmapByPathOnly(String str, int i, int i2, int i3, int i4) {
        try {
            Path pathDoPath = doPath(str);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.scale(i3 / i, i4 / i2);
            Paint paint = new Paint();
            paint.setColor(-1);
            canvas.drawPath(pathDoPath, paint);
            return bitmapCreateBitmap;
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    private static NumberParse parseNumbers(String str) {
        int length = str.length();
        ArrayList arrayList = new ArrayList();
        int length2 = 0;
        boolean z = false;
        for (int i = 1; i < length; i++) {
            if (z) {
                z = false;
            } else {
                char cCharAt = str.charAt(i);
                switch (cCharAt) {
                    case '\t':
                    case '\n':
                    case ' ':
                    case Operator.SWITCH /* 44 */:
                    case Operator.f1407DO /* 45 */:
                        if (cCharAt != '-' || str.charAt(i - 1) != 'e') {
                            String strSubstring = str.substring(length2, i);
                            if (strSubstring.trim().length() > 0) {
                                arrayList.add(Float.valueOf(Float.parseFloat(strSubstring)));
                                if (cCharAt == '-') {
                                    length2 = i;
                                    break;
                                } else {
                                    length2 = i + 1;
                                    z = true;
                                    break;
                                }
                            } else {
                                length2++;
                                break;
                            }
                        } else {
                            break;
                        }
                    case Operator.WHILE /* 41 */:
                    case 'A':
                    case TypeReference.INSTANCEOF /* 67 */:
                    case TypeReference.CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT /* 72 */:
                    case 'L':
                    case 'M':
                    case Opcodes.FASTORE /* 81 */:
                    case Opcodes.AASTORE /* 83 */:
                    case Opcodes.BASTORE /* 84 */:
                    case Opcodes.SASTORE /* 86 */:
                    case Opcodes.DUP_X1 /* 90 */:
                    case 'a':
                    case 'c':
                    case 'h':
                    case 'l':
                    case 'm':
                    case 'q':
                    case 's':
                    case 't':
                    case Opcodes.FNEG /* 118 */:
                    case Opcodes.ISHR /* 122 */:
                        String strSubstring2 = str.substring(length2, i);
                        if (strSubstring2.trim().length() > 0) {
                            arrayList.add(Float.valueOf(Float.parseFloat(strSubstring2)));
                        }
                        return new NumberParse(arrayList, i);
                }
            }
        }
        String strSubstring3 = str.substring(length2);
        if (strSubstring3.length() > 0) {
            try {
                arrayList.add(Float.valueOf(Float.parseFloat(strSubstring3)));
            } catch (NumberFormatException unused) {
            }
            length2 = str.length();
        }
        return new NumberParse(arrayList, length2);
    }

    public static Matrix parseTransform(String str) {
        Matrix matrix = new Matrix();
        Iterator<String> it = splitSvgTransforms(str).iterator();
        while (it.hasNext()) {
            Matrix transformCommand = parseTransformCommand(it.next());
            if (transformCommand != null) {
                matrix.preConcat(transformCommand);
            }
        }
        return matrix;
    }

    private static Matrix parseTransformCommand(String str) {
        float fFloatValue;
        if (str.startsWith("matrix(")) {
            NumberParse numbers = parseNumbers(str.substring(7));
            if (numbers.numbers.size() != 6) {
                return null;
            }
            Matrix matrix = new Matrix();
            matrix.setValues(new float[]{((Float) numbers.numbers.get(0)).floatValue(), ((Float) numbers.numbers.get(2)).floatValue(), ((Float) numbers.numbers.get(4)).floatValue(), ((Float) numbers.numbers.get(1)).floatValue(), ((Float) numbers.numbers.get(3)).floatValue(), ((Float) numbers.numbers.get(5)).floatValue(), 0.0f, 0.0f, 1.0f});
            return matrix;
        }
        if (str.startsWith("translate(")) {
            NumberParse numbers2 = parseNumbers(str.substring(10));
            if (numbers2.numbers.size() <= 0) {
                return null;
            }
            float fFloatValue2 = ((Float) numbers2.numbers.get(0)).floatValue();
            fFloatValue = numbers2.numbers.size() > 1 ? ((Float) numbers2.numbers.get(1)).floatValue() : 0.0f;
            Matrix matrix2 = new Matrix();
            matrix2.postTranslate(fFloatValue2, fFloatValue);
            return matrix2;
        }
        if (str.startsWith("scale(")) {
            NumberParse numbers3 = parseNumbers(str.substring(6));
            if (numbers3.numbers.size() <= 0) {
                return null;
            }
            float fFloatValue3 = ((Float) numbers3.numbers.get(0)).floatValue();
            fFloatValue = numbers3.numbers.size() > 1 ? ((Float) numbers3.numbers.get(1)).floatValue() : 0.0f;
            Matrix matrix3 = new Matrix();
            matrix3.postScale(fFloatValue3, fFloatValue);
            return matrix3;
        }
        if (str.startsWith("skewX(")) {
            NumberParse numbers4 = parseNumbers(str.substring(6));
            if (numbers4.numbers.size() <= 0) {
                return null;
            }
            float fFloatValue4 = ((Float) numbers4.numbers.get(0)).floatValue();
            Matrix matrix4 = new Matrix();
            matrix4.postSkew((float) Math.tan(fFloatValue4), 0.0f);
            return matrix4;
        }
        if (str.startsWith("skewY(")) {
            NumberParse numbers5 = parseNumbers(str.substring(6));
            if (numbers5.numbers.size() <= 0) {
                return null;
            }
            float fFloatValue5 = ((Float) numbers5.numbers.get(0)).floatValue();
            Matrix matrix5 = new Matrix();
            matrix5.postSkew(0.0f, (float) Math.tan(fFloatValue5));
            return matrix5;
        }
        if (!str.startsWith("rotate(")) {
            return null;
        }
        NumberParse numbers6 = parseNumbers(str.substring(7));
        if (numbers6.numbers.size() <= 0) {
            return null;
        }
        Matrix matrix6 = new Matrix();
        float fFloatValue6 = ((Float) numbers6.numbers.get(0)).floatValue();
        if (numbers6.numbers.size() > 2) {
            matrix6.postRotate(fFloatValue6, ((Float) numbers6.numbers.get(1)).floatValue(), ((Float) numbers6.numbers.get(2)).floatValue());
            return matrix6;
        }
        matrix6.postRotate(fFloatValue6);
        return matrix6;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:41:0x0079. Please report as an issue. */
    public static Path doPath(String str) {
        char c;
        float f;
        float fNextFloat;
        float fNextFloat2;
        float fNextFloat3;
        if (ApplicationLoader.isAndroidTestEnvironment()) {
            return new Path();
        }
        int length = str.length();
        ParserHelper parserHelper = new ParserHelper(str, 0);
        parserHelper.skipWhitespace();
        Path path = new Path();
        char c2 = 0;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        while (true) {
            int i = parserHelper.pos;
            if (i >= length) {
                return path;
            }
            char cCharAt = str.charAt(i);
            switch (cCharAt) {
                case Operator.FOR /* 43 */:
                case Operator.f1407DO /* 45 */:
                case '.':
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    if (c2 != 'm' && c2 != 'M') {
                        if (c2 != 'c' && c2 != 'C' && c2 != 'l' && c2 != 'L' && c2 != 's' && c2 != 'S' && c2 != 'h' && c2 != 'H' && c2 != 'v' && c2 != 'V') {
                        }
                        c = c2;
                        break;
                    } else {
                        c = c2;
                        c2 = (char) (c2 - 1);
                        break;
                    }
                case Operator.SWITCH /* 44 */:
                case '/':
                default:
                    parserHelper.advance();
                    c2 = cCharAt;
                    c = c2;
                    break;
            }
            boolean z = true;
            switch (c2) {
                case 'A':
                case 'a':
                    float fNextFloat4 = parserHelper.nextFloat();
                    float fNextFloat5 = parserHelper.nextFloat();
                    float f8 = f4;
                    float fNextFloat6 = parserHelper.nextFloat();
                    int iNextFloat = (int) parserHelper.nextFloat();
                    int iNextFloat2 = (int) parserHelper.nextFloat();
                    float f9 = f2;
                    float f10 = f3;
                    float fNextFloat7 = parserHelper.nextFloat();
                    float fNextFloat8 = parserHelper.nextFloat();
                    drawArc(path, f9, f10, fNextFloat7, fNextFloat8, fNextFloat4, fNextFloat5, fNextFloat6, iNextFloat, iNextFloat2);
                    f5 = f5;
                    f2 = fNextFloat7;
                    f3 = fNextFloat8;
                    f4 = f8;
                    z = false;
                    break;
                case TypeReference.INSTANCEOF /* 67 */:
                case 'c':
                    float fNextFloat9 = parserHelper.nextFloat();
                    float fNextFloat10 = parserHelper.nextFloat();
                    float fNextFloat11 = parserHelper.nextFloat();
                    float fNextFloat12 = parserHelper.nextFloat();
                    float fNextFloat13 = parserHelper.nextFloat();
                    float fNextFloat14 = parserHelper.nextFloat();
                    if (c2 == 'c') {
                        fNextFloat9 += f2;
                        fNextFloat11 += f2;
                        fNextFloat13 += f2;
                        fNextFloat10 += f3;
                        fNextFloat12 += f3;
                        fNextFloat14 += f3;
                    }
                    float f11 = fNextFloat10;
                    f = fNextFloat11;
                    fNextFloat = fNextFloat12;
                    fNextFloat2 = fNextFloat13;
                    fNextFloat3 = fNextFloat14;
                    path.cubicTo(fNextFloat9, f11, f, fNextFloat, fNextFloat2, fNextFloat3);
                    f6 = f;
                    f7 = fNextFloat;
                    f2 = fNextFloat2;
                    f3 = fNextFloat3;
                    break;
                case TypeReference.CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT /* 72 */:
                case 'h':
                    float fNextFloat15 = parserHelper.nextFloat();
                    if (c2 == 'h') {
                        path.rLineTo(fNextFloat15, 0.0f);
                        f2 += fNextFloat15;
                    } else {
                        path.lineTo(fNextFloat15, f3);
                        f2 = fNextFloat15;
                    }
                    z = false;
                    break;
                case 'L':
                case 'l':
                    float fNextFloat16 = parserHelper.nextFloat();
                    float fNextFloat17 = parserHelper.nextFloat();
                    if (c2 == 'l') {
                        path.rLineTo(fNextFloat16, fNextFloat17);
                        f2 += fNextFloat16;
                        f3 += fNextFloat17;
                    } else {
                        path.lineTo(fNextFloat16, fNextFloat17);
                        f2 = fNextFloat16;
                        f3 = fNextFloat17;
                    }
                    z = false;
                    break;
                case 'M':
                case 'm':
                    float fNextFloat18 = parserHelper.nextFloat();
                    float fNextFloat19 = parserHelper.nextFloat();
                    if (c2 == 'm') {
                        f4 += fNextFloat18;
                        f5 += fNextFloat19;
                        path.rMoveTo(fNextFloat18, fNextFloat19);
                        f2 += fNextFloat18;
                        f3 += fNextFloat19;
                    } else {
                        path.moveTo(fNextFloat18, fNextFloat19);
                        f2 = fNextFloat18;
                        f4 = f2;
                        f3 = fNextFloat19;
                        f5 = f3;
                    }
                    z = false;
                    break;
                case Opcodes.AASTORE /* 83 */:
                case 's':
                    float fNextFloat20 = parserHelper.nextFloat();
                    fNextFloat = parserHelper.nextFloat();
                    fNextFloat2 = parserHelper.nextFloat();
                    fNextFloat3 = parserHelper.nextFloat();
                    if (c2 == 's') {
                        fNextFloat20 += f2;
                        fNextFloat2 += f2;
                        fNextFloat += f3;
                        fNextFloat3 += f3;
                    }
                    float f12 = (f3 * 2.0f) - f7;
                    f = fNextFloat20;
                    path.cubicTo((f2 * 2.0f) - f6, f12, f, fNextFloat, fNextFloat2, fNextFloat3);
                    f6 = f;
                    f7 = fNextFloat;
                    f2 = fNextFloat2;
                    f3 = fNextFloat3;
                    break;
                case Opcodes.SASTORE /* 86 */:
                case Opcodes.FNEG /* 118 */:
                    float fNextFloat21 = parserHelper.nextFloat();
                    if (c2 == 'v') {
                        path.rLineTo(0.0f, fNextFloat21);
                        f3 += fNextFloat21;
                    } else {
                        path.lineTo(f2, fNextFloat21);
                        f3 = fNextFloat21;
                    }
                    z = false;
                    break;
                case Opcodes.DUP_X1 /* 90 */:
                case Opcodes.ISHR /* 122 */:
                    path.close();
                    path.moveTo(f4, f5);
                    f2 = f4;
                    f6 = f2;
                    f3 = f5;
                    f7 = f3;
                    break;
                default:
                    z = false;
                    break;
            }
            if (!z) {
                f6 = f2;
                f7 = f3;
            }
            parserHelper.skipWhitespace();
            c2 = c;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static NumberParse getNumberParseAttr(String str, Attributes attributes) {
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            if (attributes.getLocalName(i).equals(str)) {
                return parseNumbers(attributes.getValue(i));
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getStringAttr(String str, Attributes attributes) {
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            if (attributes.getLocalName(i).equals(str)) {
                return attributes.getValue(i);
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Float getFloatAttr(String str, Attributes attributes) {
        return getFloatAttr(str, attributes, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Float getFloatAttr(String str, Attributes attributes, Float f) {
        String stringAttr = getStringAttr(str, attributes);
        if (stringAttr == null) {
            return f;
        }
        if (stringAttr.endsWith("px")) {
            stringAttr = stringAttr.substring(0, stringAttr.length() - 2);
        } else if (stringAttr.endsWith("mm")) {
            return null;
        }
        return Float.valueOf(Float.parseFloat(stringAttr));
    }

    private static Integer getHexAttr(String str, Attributes attributes) {
        String stringAttr = getStringAttr(str, attributes);
        if (stringAttr == null) {
            return null;
        }
        try {
            return Integer.valueOf(Integer.parseInt(stringAttr.substring(1), 16));
        } catch (NumberFormatException unused) {
            return getColorByName(stringAttr);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:4:0x000f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.Integer getColorByName(java.lang.String r2) {
        /*
            java.lang.String r2 = r2.toLowerCase()
            r2.getClass()
            int r0 = r2.hashCode()
            r1 = -1
            switch(r0) {
                case -734239628: goto L6f;
                case 112785: goto L63;
                case 3027034: goto L58;
                case 3068707: goto L4d;
                case 3181155: goto L42;
                case 93818879: goto L37;
                case 98619139: goto L2c;
                case 113101865: goto L20;
                case 828922025: goto L12;
                default: goto Lf;
            }
        Lf:
            r2 = -1
            goto L7a
        L12:
            java.lang.String r0 = "magenta"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L1c
            goto Lf
        L1c:
            r2 = 8
            goto L7a
        L20:
            java.lang.String r0 = "white"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L2a
            goto Lf
        L2a:
            r2 = 7
            goto L7a
        L2c:
            java.lang.String r0 = "green"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L35
            goto Lf
        L35:
            r2 = 6
            goto L7a
        L37:
            java.lang.String r0 = "black"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L40
            goto Lf
        L40:
            r2 = 5
            goto L7a
        L42:
            java.lang.String r0 = "gray"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L4b
            goto Lf
        L4b:
            r2 = 4
            goto L7a
        L4d:
            java.lang.String r0 = "cyan"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L56
            goto Lf
        L56:
            r2 = 3
            goto L7a
        L58:
            java.lang.String r0 = "blue"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L61
            goto Lf
        L61:
            r2 = 2
            goto L7a
        L63:
            java.lang.String r0 = "red"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L6d
            goto Lf
        L6d:
            r2 = 1
            goto L7a
        L6f:
            java.lang.String r0 = "yellow"
            boolean r2 = r2.equals(r0)
            if (r2 != 0) goto L79
            goto Lf
        L79:
            r2 = 0
        L7a:
            switch(r2) {
                case 0: goto Lba;
                case 1: goto Lb3;
                case 2: goto Lab;
                case 3: goto La3;
                case 4: goto L9b;
                case 5: goto L94;
                case 6: goto L8c;
                case 7: goto L87;
                case 8: goto L7f;
                default: goto L7d;
            }
        L7d:
            r2 = 0
            return r2
        L7f:
            r2 = -65281(0xffffffffffff00ff, float:NaN)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L87:
            java.lang.Integer r2 = java.lang.Integer.valueOf(r1)
            return r2
        L8c:
            r2 = -16711936(0xffffffffff00ff00, float:-1.7146522E38)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L94:
            r2 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        L9b:
            r2 = -7829368(0xffffffffff888888, float:NaN)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        La3:
            r2 = -16711681(0xffffffffff00ffff, float:-1.714704E38)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        Lab:
            r2 = -16776961(0xffffffffff0000ff, float:-1.7014636E38)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        Lb3:
            r2 = -65536(0xffffffffffff0000, float:NaN)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        Lba:
            r2 = -256(0xffffffffffffff00, float:NaN)
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SvgHelper.getColorByName(java.lang.String):java.lang.Integer");
    }

    /* loaded from: classes4.dex */
    private static class NumberParse {
        private int nextCmd;
        private ArrayList<Float> numbers;

        public NumberParse(ArrayList<Float> arrayList, int i) {
            this.numbers = arrayList;
            this.nextCmd = i;
        }

        public int getNextCmd() {
            return this.nextCmd;
        }

        public float getNumber(int i) {
            return this.numbers.get(i).floatValue();
        }
    }

    /* loaded from: classes4.dex */
    private static class StyleSet {
        HashMap<String, String> styleMap;

        private StyleSet(StyleSet styleSet) {
            HashMap<String, String> map = new HashMap<>();
            this.styleMap = map;
            map.putAll(styleSet.styleMap);
        }

        private StyleSet(String str) {
            this.styleMap = new HashMap<>();
            for (String str2 : str.split(";")) {
                String[] strArrSplit = str2.split(":");
                if (strArrSplit.length == 2) {
                    this.styleMap.put(strArrSplit[0].trim(), strArrSplit[1].trim());
                }
            }
        }

        public String getStyle(String str) {
            return this.styleMap.get(str);
        }
    }

    /* loaded from: classes4.dex */
    private static class Properties {
        Attributes atts;
        ArrayList<StyleSet> styles;

        private Properties(Attributes attributes, HashMap<String, StyleSet> map) {
            this.atts = attributes;
            String stringAttr = SvgHelper.getStringAttr("style", attributes);
            if (stringAttr != null) {
                ArrayList<StyleSet> arrayList = new ArrayList<>();
                this.styles = arrayList;
                arrayList.add(new StyleSet(stringAttr));
                return;
            }
            String stringAttr2 = SvgHelper.getStringAttr("class", attributes);
            if (stringAttr2 != null) {
                this.styles = new ArrayList<>();
                for (String str : stringAttr2.split(" ")) {
                    StyleSet styleSet = map.get(str.trim());
                    if (styleSet != null) {
                        this.styles.add(styleSet);
                    }
                }
            }
        }

        public String getAttr(String str) {
            ArrayList<StyleSet> arrayList = this.styles;
            String style = null;
            if (arrayList != null && !arrayList.isEmpty()) {
                int size = this.styles.size();
                for (int i = 0; i < size; i++) {
                    style = this.styles.get(i).getStyle(str);
                    if (style != null) {
                        break;
                    }
                }
            }
            return style == null ? SvgHelper.getStringAttr(str, this.atts) : style;
        }

        public String getString(String str) {
            return getAttr(str);
        }

        public Integer getHex(String str) {
            String attr = getAttr(str);
            if (attr == null) {
                return null;
            }
            try {
                return Integer.valueOf(Integer.parseInt(attr.substring(1), 16));
            } catch (NumberFormatException unused) {
                return SvgHelper.getColorByName(attr);
            }
        }

        public Float getFloat(String str, float f) {
            Float f2 = getFloat(str);
            return f2 == null ? Float.valueOf(f) : f2;
        }

        public Float getFloat(String str) {
            String attr = getAttr(str);
            if (attr == null) {
                return null;
            }
            try {
                return Float.valueOf(Float.parseFloat(attr));
            } catch (NumberFormatException unused) {
                return null;
            }
        }
    }

    /* loaded from: classes4.dex */
    private static class SVGHandler extends DefaultHandler implements SvgResult {
        private boolean alphaOnly;
        private Bitmap bitmap;
        private boolean boundsMode;
        private Canvas canvas;
        private int desiredHeight;
        private int desiredWidth;
        private SvgDrawable drawable;
        private float globalScale;
        private HashMap<String, StyleSet> globalStyles;
        private boolean insideGiftRect;
        private int insideGiftRectDepth;
        private List<WallpaperGiftPatternPosition> insideGiftRectPositions;
        private Paint paint;
        private Integer paintColor;
        boolean pushed;
        private RectF rect;
        private RectF rectTmp;
        private float scale;
        private StringBuilder styles;

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endDocument() {
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startDocument() {
        }

        private SVGHandler(int i, int i2, Integer num, boolean z, float f) {
            this.scale = 1.0f;
            this.paint = new Paint(1);
            this.rect = new RectF();
            this.rectTmp = new RectF();
            this.globalScale = 1.0f;
            this.pushed = false;
            this.globalStyles = new HashMap<>();
            this.insideGiftRect = false;
            this.insideGiftRectDepth = 0;
            this.globalScale = f;
            this.desiredWidth = i;
            this.desiredHeight = i2;
            this.paintColor = num;
            if (z) {
                this.drawable = new SvgDrawable();
            }
        }

        private boolean doFill(Properties properties) {
            if ("none".equals(properties.getString("display"))) {
                return false;
            }
            String string = properties.getString("fill");
            if (string != null && string.startsWith("url(#")) {
                string.substring(5, string.length() - 1);
                return false;
            }
            Integer hex = properties.getHex("fill");
            if (hex != null) {
                doColor(properties, hex, true);
                this.paint.setStyle(Paint.Style.FILL);
                return true;
            }
            if (properties.getString("fill") != null || properties.getString("stroke") != null) {
                return false;
            }
            this.paint.setStyle(Paint.Style.FILL);
            Integer num = this.paintColor;
            if (num != null) {
                this.paint.setColor(num.intValue());
            } else {
                this.paint.setColor(-16777216);
            }
            return true;
        }

        private boolean doStroke(Properties properties) {
            Integer hex;
            if ("none".equals(properties.getString("display")) || (hex = properties.getHex("stroke")) == null) {
                return false;
            }
            doColor(properties, hex, false);
            Float f = properties.getFloat("stroke-width");
            if (f != null) {
                this.paint.setStrokeWidth(f.floatValue());
            }
            String string = properties.getString("stroke-linecap");
            if ("round".equals(string)) {
                this.paint.setStrokeCap(Paint.Cap.ROUND);
            } else if ("square".equals(string)) {
                this.paint.setStrokeCap(Paint.Cap.SQUARE);
            } else if ("butt".equals(string)) {
                this.paint.setStrokeCap(Paint.Cap.BUTT);
            }
            String string2 = properties.getString("stroke-linejoin");
            if ("miter".equals(string2)) {
                this.paint.setStrokeJoin(Paint.Join.MITER);
            } else if ("round".equals(string2)) {
                this.paint.setStrokeJoin(Paint.Join.ROUND);
            } else if ("bevel".equals(string2)) {
                this.paint.setStrokeJoin(Paint.Join.BEVEL);
            }
            this.paint.setStyle(Paint.Style.STROKE);
            return true;
        }

        private void doColor(Properties properties, Integer num, boolean z) {
            Integer num2 = this.paintColor;
            if (num2 != null) {
                this.paint.setColor(num2.intValue());
            } else {
                this.paint.setColor((num.intValue() & 16777215) | (-16777216));
            }
            Float f = properties.getFloat("opacity");
            if (f == null) {
                f = properties.getFloat(z ? "fill-opacity" : "stroke-opacity");
            }
            if (f == null) {
                this.paint.setAlpha(255);
            } else {
                this.paint.setAlpha((int) (f.floatValue() * 255.0f));
            }
        }

        private void pushTransform(Attributes attributes) {
            String stringAttr = SvgHelper.getStringAttr("transform", attributes);
            boolean z = stringAttr != null;
            this.pushed = z;
            if (z) {
                Matrix transform = SvgHelper.parseTransform(stringAttr);
                SvgDrawable svgDrawable = this.drawable;
                if (svgDrawable != null) {
                    svgDrawable.addCommand(transform);
                } else {
                    this.canvas.save();
                    this.canvas.concat(transform);
                }
            }
        }

        private void popTransform() {
            if (this.pushed) {
                SvgDrawable svgDrawable = this.drawable;
                if (svgDrawable != null) {
                    svgDrawable.addCommand(null);
                } else {
                    this.canvas.restore();
                }
            }
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void startElement(String str, String str2, String str3, Attributes attributes) {
            WallpaperGiftPatternPosition wallpaperGiftPatternPositionCreate;
            String stringAttr;
            int i;
            Float fValueOf = Float.valueOf(0.0f);
            if (ImageLoader.AUTOPLAY_FILTER.equals(str3) && !this.insideGiftRect) {
                if ("GiftPatterns".equals(attributes.getValue("id"))) {
                    this.insideGiftRect = true;
                    this.insideGiftRectDepth = 1;
                }
            } else if (this.insideGiftRect) {
                this.insideGiftRectDepth++;
                if (!"rect".equals(str3) || (wallpaperGiftPatternPositionCreate = WallpaperGiftPatternPosition.create(attributes, this.scale)) == null) {
                    return;
                }
                if (this.insideGiftRectPositions == null) {
                    this.insideGiftRectPositions = new ArrayList();
                }
                this.insideGiftRectPositions.add(wallpaperGiftPatternPositionCreate);
                return;
            }
            if (!this.boundsMode || str2.equals("style")) {
                str2.getClass();
                char c = 65535;
                switch (str2.hashCode()) {
                    case -1656480802:
                        if (str2.equals("ellipse")) {
                            c = 0;
                            break;
                        }
                        break;
                    case -1360216880:
                        if (str2.equals("circle")) {
                            c = 1;
                            break;
                        }
                        break;
                    case -397519558:
                        if (str2.equals("polygon")) {
                            c = 2;
                            break;
                        }
                        break;
                    case 103:
                        if (str2.equals(ImageLoader.AUTOPLAY_FILTER)) {
                            c = 3;
                            break;
                        }
                        break;
                    case 114276:
                        if (str2.equals("svg")) {
                            c = 4;
                            break;
                        }
                        break;
                    case 3079438:
                        if (str2.equals("defs")) {
                            c = 5;
                            break;
                        }
                        break;
                    case 3321844:
                        if (str2.equals("line")) {
                            c = 6;
                            break;
                        }
                        break;
                    case 3433509:
                        if (str2.equals("path")) {
                            c = 7;
                            break;
                        }
                        break;
                    case 3496420:
                        if (str2.equals("rect")) {
                            c = '\b';
                            break;
                        }
                        break;
                    case 109780401:
                        if (str2.equals("style")) {
                            c = '\t';
                            break;
                        }
                        break;
                    case 561938880:
                        if (str2.equals("polyline")) {
                            c = '\n';
                            break;
                        }
                        break;
                    case 917656469:
                        if (str2.equals("clipPath")) {
                            c = 11;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        Float floatAttr = SvgHelper.getFloatAttr("cx", attributes);
                        Float floatAttr2 = SvgHelper.getFloatAttr("cy", attributes);
                        Float floatAttr3 = SvgHelper.getFloatAttr("rx", attributes);
                        Float floatAttr4 = SvgHelper.getFloatAttr("ry", attributes);
                        if (floatAttr != null && floatAttr2 != null && floatAttr3 != null && floatAttr4 != null) {
                            pushTransform(attributes);
                            Properties properties = new Properties(attributes, this.globalStyles);
                            this.rect.set(floatAttr.floatValue() - floatAttr3.floatValue(), floatAttr2.floatValue() - floatAttr4.floatValue(), floatAttr.floatValue() + floatAttr3.floatValue(), floatAttr2.floatValue() + floatAttr4.floatValue());
                            if (doFill(properties)) {
                                SvgDrawable svgDrawable = this.drawable;
                                if (svgDrawable != null) {
                                    svgDrawable.addCommand(new Oval(this.rect), this.paint);
                                } else {
                                    this.canvas.drawOval(this.rect, this.paint);
                                }
                            }
                            if (doStroke(properties)) {
                                SvgDrawable svgDrawable2 = this.drawable;
                                if (svgDrawable2 != null) {
                                    svgDrawable2.addCommand(new Oval(this.rect), this.paint);
                                } else {
                                    this.canvas.drawOval(this.rect, this.paint);
                                }
                            }
                            popTransform();
                            break;
                        }
                        break;
                    case 1:
                        Float floatAttr5 = SvgHelper.getFloatAttr("cx", attributes);
                        Float floatAttr6 = SvgHelper.getFloatAttr("cy", attributes);
                        Float floatAttr7 = SvgHelper.getFloatAttr("r", attributes);
                        if (floatAttr5 != null && floatAttr6 != null && floatAttr7 != null) {
                            pushTransform(attributes);
                            Properties properties2 = new Properties(attributes, this.globalStyles);
                            if (doFill(properties2)) {
                                SvgDrawable svgDrawable3 = this.drawable;
                                if (svgDrawable3 != null) {
                                    svgDrawable3.addCommand(new Circle(floatAttr5.floatValue(), floatAttr6.floatValue(), floatAttr7.floatValue()), this.paint);
                                } else {
                                    this.canvas.drawCircle(floatAttr5.floatValue(), floatAttr6.floatValue(), floatAttr7.floatValue(), this.paint);
                                }
                            }
                            if (doStroke(properties2)) {
                                SvgDrawable svgDrawable4 = this.drawable;
                                if (svgDrawable4 != null) {
                                    svgDrawable4.addCommand(new Circle(floatAttr5.floatValue(), floatAttr6.floatValue(), floatAttr7.floatValue()), this.paint);
                                } else {
                                    this.canvas.drawCircle(floatAttr5.floatValue(), floatAttr6.floatValue(), floatAttr7.floatValue(), this.paint);
                                }
                            }
                            popTransform();
                            break;
                        }
                        break;
                    case 2:
                    case '\n':
                        NumberParse numberParseAttr = SvgHelper.getNumberParseAttr("points", attributes);
                        if (numberParseAttr != null) {
                            Path path = new Path();
                            ArrayList arrayList = numberParseAttr.numbers;
                            if (arrayList.size() > 1) {
                                pushTransform(attributes);
                                Properties properties3 = new Properties(attributes, this.globalStyles);
                                path.moveTo(((Float) arrayList.get(0)).floatValue(), ((Float) arrayList.get(1)).floatValue());
                                for (int i2 = 2; i2 < arrayList.size(); i2 += 2) {
                                    path.lineTo(((Float) arrayList.get(i2)).floatValue(), ((Float) arrayList.get(i2 + 1)).floatValue());
                                }
                                if (str2.equals("polygon")) {
                                    path.close();
                                }
                                if (doFill(properties3)) {
                                    SvgDrawable svgDrawable5 = this.drawable;
                                    if (svgDrawable5 != null) {
                                        svgDrawable5.addCommand(path, this.paint);
                                    } else {
                                        this.canvas.drawPath(path, this.paint);
                                    }
                                }
                                if (doStroke(properties3)) {
                                    SvgDrawable svgDrawable6 = this.drawable;
                                    if (svgDrawable6 != null) {
                                        svgDrawable6.addCommand(path, this.paint);
                                    } else {
                                        this.canvas.drawPath(path, this.paint);
                                    }
                                }
                                popTransform();
                                break;
                            }
                        }
                        break;
                    case 3:
                        if ("bounds".equalsIgnoreCase(SvgHelper.getStringAttr("id", attributes))) {
                            this.boundsMode = true;
                            break;
                        }
                        break;
                    case 4:
                        Float floatAttr8 = SvgHelper.getFloatAttr("width", attributes);
                        Float floatAttr9 = SvgHelper.getFloatAttr("height", attributes);
                        if ((floatAttr8 == null || floatAttr9 == null) && (stringAttr = SvgHelper.getStringAttr("viewBox", attributes)) != null) {
                            String[] strArrSplit = stringAttr.split(" ");
                            Float fValueOf2 = Float.valueOf(Float.parseFloat(strArrSplit[2]));
                            floatAttr9 = Float.valueOf(Float.parseFloat(strArrSplit[3]));
                            floatAttr8 = fValueOf2;
                        }
                        if (floatAttr8 == null || floatAttr9 == null) {
                            floatAttr8 = Float.valueOf(this.desiredWidth);
                            floatAttr9 = Float.valueOf(this.desiredHeight);
                        }
                        int iCeil = (int) Math.ceil(floatAttr8.floatValue());
                        int iCeil2 = (int) Math.ceil(floatAttr9.floatValue());
                        if (iCeil == 0 || iCeil2 == 0) {
                            iCeil = this.desiredWidth;
                            iCeil2 = this.desiredHeight;
                        } else {
                            int i3 = this.desiredWidth;
                            if (i3 != 0 && (i = this.desiredHeight) != 0) {
                                float f = iCeil;
                                float f2 = iCeil2;
                                float fMin = Math.min(i3 / f, i / f2);
                                this.scale = fMin;
                                iCeil = (int) (f * fMin);
                                iCeil2 = (int) (f2 * fMin);
                            }
                        }
                        SvgDrawable svgDrawable7 = this.drawable;
                        if (svgDrawable7 == null) {
                            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iCeil, iCeil2, this.alphaOnly ? Bitmap.Config.ALPHA_8 : Bitmap.Config.ARGB_8888);
                            this.bitmap = bitmapCreateBitmap;
                            bitmapCreateBitmap.eraseColor(0);
                            Canvas canvas = new Canvas(this.bitmap);
                            this.canvas = canvas;
                            float f3 = this.scale;
                            if (f3 != 0.0f) {
                                float f4 = this.globalScale;
                                canvas.scale(f4 * f3, f4 * f3);
                                break;
                            }
                        } else {
                            svgDrawable7.width = iCeil;
                            svgDrawable7.height = iCeil2;
                            break;
                        }
                        break;
                    case 5:
                    case 11:
                        this.boundsMode = true;
                        break;
                    case 6:
                        Float floatAttr10 = SvgHelper.getFloatAttr("x1", attributes);
                        Float floatAttr11 = SvgHelper.getFloatAttr("x2", attributes);
                        Float floatAttr12 = SvgHelper.getFloatAttr("y1", attributes);
                        Float floatAttr13 = SvgHelper.getFloatAttr("y2", attributes);
                        if (doStroke(new Properties(attributes, this.globalStyles))) {
                            pushTransform(attributes);
                            SvgDrawable svgDrawable8 = this.drawable;
                            if (svgDrawable8 != null) {
                                svgDrawable8.addCommand(new Line(floatAttr10.floatValue(), floatAttr12.floatValue(), floatAttr11.floatValue(), floatAttr13.floatValue()), this.paint);
                            } else {
                                this.canvas.drawLine(floatAttr10.floatValue(), floatAttr12.floatValue(), floatAttr11.floatValue(), floatAttr13.floatValue(), this.paint);
                            }
                            popTransform();
                            break;
                        }
                        break;
                    case 7:
                        Path pathDoPath = SvgHelper.doPath(SvgHelper.getStringAttr("d", attributes));
                        pushTransform(attributes);
                        Properties properties4 = new Properties(attributes, this.globalStyles);
                        if (doFill(properties4)) {
                            SvgDrawable svgDrawable9 = this.drawable;
                            if (svgDrawable9 != null) {
                                svgDrawable9.addCommand(pathDoPath, this.paint);
                            } else {
                                this.canvas.drawPath(pathDoPath, this.paint);
                            }
                        }
                        if (doStroke(properties4)) {
                            SvgDrawable svgDrawable10 = this.drawable;
                            if (svgDrawable10 != null) {
                                svgDrawable10.addCommand(pathDoPath, this.paint);
                            } else {
                                this.canvas.drawPath(pathDoPath, this.paint);
                            }
                        }
                        popTransform();
                        break;
                    case '\b':
                        Float floatAttr14 = SvgHelper.getFloatAttr("x", attributes);
                        if (floatAttr14 == null) {
                            floatAttr14 = fValueOf;
                        }
                        Float floatAttr15 = SvgHelper.getFloatAttr("y", attributes);
                        if (floatAttr15 != null) {
                            fValueOf = floatAttr15;
                        }
                        Float floatAttr16 = SvgHelper.getFloatAttr("width", attributes);
                        Float floatAttr17 = SvgHelper.getFloatAttr("height", attributes);
                        Float floatAttr18 = SvgHelper.getFloatAttr("rx", attributes, null);
                        pushTransform(attributes);
                        Properties properties5 = new Properties(attributes, this.globalStyles);
                        if (doFill(properties5)) {
                            SvgDrawable svgDrawable11 = this.drawable;
                            if (svgDrawable11 != null) {
                                if (floatAttr18 != null) {
                                    svgDrawable11.addCommand(new RoundRect(new RectF(floatAttr14.floatValue(), fValueOf.floatValue(), floatAttr14.floatValue() + floatAttr16.floatValue(), fValueOf.floatValue() + floatAttr17.floatValue()), floatAttr18.floatValue()), this.paint);
                                } else {
                                    svgDrawable11.addCommand(new RectF(floatAttr14.floatValue(), fValueOf.floatValue(), floatAttr14.floatValue() + floatAttr16.floatValue(), fValueOf.floatValue() + floatAttr17.floatValue()), this.paint);
                                }
                            } else if (floatAttr18 != null) {
                                this.rectTmp.set(floatAttr14.floatValue(), fValueOf.floatValue(), floatAttr14.floatValue() + floatAttr16.floatValue(), fValueOf.floatValue() + floatAttr17.floatValue());
                                this.canvas.drawRoundRect(this.rectTmp, floatAttr18.floatValue(), floatAttr18.floatValue(), this.paint);
                            } else {
                                this.canvas.drawRect(floatAttr14.floatValue(), fValueOf.floatValue(), floatAttr14.floatValue() + floatAttr16.floatValue(), fValueOf.floatValue() + floatAttr17.floatValue(), this.paint);
                            }
                        }
                        if (doStroke(properties5)) {
                            SvgDrawable svgDrawable12 = this.drawable;
                            if (svgDrawable12 != null) {
                                if (floatAttr18 != null) {
                                    svgDrawable12.addCommand(new RoundRect(new RectF(floatAttr14.floatValue(), fValueOf.floatValue(), floatAttr14.floatValue() + floatAttr16.floatValue(), fValueOf.floatValue() + floatAttr17.floatValue()), floatAttr18.floatValue()), this.paint);
                                } else {
                                    svgDrawable12.addCommand(new RectF(floatAttr14.floatValue(), fValueOf.floatValue(), floatAttr14.floatValue() + floatAttr16.floatValue(), fValueOf.floatValue() + floatAttr17.floatValue()), this.paint);
                                }
                            } else if (floatAttr18 != null) {
                                this.rectTmp.set(floatAttr14.floatValue(), fValueOf.floatValue(), floatAttr14.floatValue() + floatAttr16.floatValue(), fValueOf.floatValue() + floatAttr17.floatValue());
                                this.canvas.drawRoundRect(this.rectTmp, floatAttr18.floatValue(), floatAttr18.floatValue(), this.paint);
                            } else {
                                this.canvas.drawRect(floatAttr14.floatValue(), fValueOf.floatValue(), floatAttr14.floatValue() + floatAttr16.floatValue(), fValueOf.floatValue() + floatAttr17.floatValue(), this.paint);
                            }
                        }
                        popTransform();
                        break;
                    case '\t':
                        this.styles = new StringBuilder();
                        break;
                }
            }
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void characters(char[] cArr, int i, int i2) {
            StringBuilder sb = this.styles;
            if (sb != null) {
                sb.append(cArr, i, i2);
            }
        }

        @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
        public void endElement(String str, String str2, String str3) {
            int iIndexOf;
            if (this.insideGiftRect) {
                int i = this.insideGiftRectDepth - 1;
                this.insideGiftRectDepth = i;
                if (i == 0) {
                    this.insideGiftRect = false;
                }
                return;
            }
            str2.getClass();
            switch (str2) {
                case "g":
                case "defs":
                case "clipPath":
                    this.boundsMode = false;
                    break;
                case "style":
                    StringBuilder sb = this.styles;
                    if (sb != null) {
                        String[] strArrSplit = sb.toString().split("\\}");
                        int i2 = 0;
                        while (true) {
                            if (i2 < strArrSplit.length) {
                                String strReplace = strArrSplit[i2].trim().replace("\t", "").replace("\n", "");
                                strArrSplit[i2] = strReplace;
                                if (strReplace.length() != 0 && strArrSplit[i2].charAt(0) == '.' && (iIndexOf = strArrSplit[i2].indexOf(Opcodes.LSHR)) >= 0) {
                                    this.globalStyles.put(strArrSplit[i2].substring(1, iIndexOf).trim(), new StyleSet(strArrSplit[i2].substring(iIndexOf + 1)));
                                }
                                i2++;
                            } else {
                                this.styles = null;
                                break;
                            }
                        }
                    }
                    break;
            }
        }

        @Override // org.telegram.messenger.SvgHelper.SvgResult
        public Bitmap getBitmap() {
            return this.bitmap;
        }

        @Override // org.telegram.messenger.SvgHelper.SvgResult
        public List<WallpaperGiftPatternPosition> getGiftPatternPositions() {
            return this.insideGiftRectPositions;
        }

        @Override // org.telegram.messenger.SvgHelper.SvgResult
        public SvgDrawable getDrawable() {
            return this.drawable;
        }
    }

    static {
        int i = 0;
        while (true) {
            double[] dArr = pow10;
            if (i < dArr.length) {
                dArr[i] = Math.pow(10.0d, i);
                i++;
            } else {
                SPLIT_BOUNDARY = Pattern.compile("(?<=\\))\\s*(?=[A-Za-z])");
                return;
            }
        }
    }

    public static class ParserHelper {
        private char current;

        /* renamed from: n */
        private int f1468n;
        public int pos;

        /* renamed from: s */
        private CharSequence f1469s;

        public ParserHelper(CharSequence charSequence, int i) {
            this.f1469s = charSequence;
            this.pos = i;
            this.f1468n = charSequence.length();
            this.current = charSequence.charAt(i);
        }

        private char read() {
            int i = this.pos;
            int i2 = this.f1468n;
            if (i < i2) {
                this.pos = i + 1;
            }
            int i3 = this.pos;
            if (i3 == i2) {
                return (char) 0;
            }
            return this.f1469s.charAt(i3);
        }

        public void skipWhitespace() {
            while (true) {
                int i = this.pos;
                if (i >= this.f1468n || !Character.isWhitespace(this.f1469s.charAt(i))) {
                    return;
                } else {
                    advance();
                }
            }
        }

        public void skipNumberSeparator() {
            while (true) {
                int i = this.pos;
                if (i >= this.f1468n) {
                    return;
                }
                char cCharAt = this.f1469s.charAt(i);
                if (cCharAt != '\t' && cCharAt != '\n' && cCharAt != ' ' && cCharAt != ',') {
                    return;
                } else {
                    advance();
                }
            }
        }

        public void advance() {
            this.current = read();
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0028 A[LOOP:0: B:13:0x0028->B:84:?, LOOP_START] */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0038  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0058  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0060  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0099 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:48:0x009c  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x00a4  */
        /* JADX WARN: Removed duplicated region for block: B:55:0x00ae A[PHI: r3
          0x00ae: PHI (r3v1 boolean) = (r3v0 boolean), (r3v3 boolean) binds: [B:49:0x00a2, B:54:0x00ad] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:57:0x00b7  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x00bb A[PHI: r3
          0x00bb: PHI (r3v2 boolean) = (r3v1 boolean), (r3v0 boolean) binds: [B:56:0x00b4, B:51:0x00a6] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:73:0x00e4  */
        /* JADX WARN: Removed duplicated region for block: B:76:0x00e8  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public float parseFloat() {
            /*
                Method dump skipped, instructions count: 506
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.SvgHelper.ParserHelper.parseFloat():float");
        }

        private void reportUnexpectedCharacterError(char c) {
            throw new RuntimeException("Unexpected char '" + c + "'.");
        }

        public float buildFloat(int i, int i2) {
            if (i2 < -125 || i == 0) {
                return 0.0f;
            }
            if (i2 >= 128) {
                return i > 0 ? Float.POSITIVE_INFINITY : Float.NEGATIVE_INFINITY;
            }
            if (i2 == 0) {
                return i;
            }
            if (i >= 67108864) {
                i++;
            }
            double d = i;
            double[] dArr = SvgHelper.pow10;
            return (float) (i2 > 0 ? d * dArr[i2] : d / dArr[-i2]);
        }

        public float nextFloat() {
            skipWhitespace();
            float f = parseFloat();
            skipNumberSeparator();
            return f;
        }
    }

    public static String decompress(byte[] bArr) {
        try {
            StringBuilder sb = new StringBuilder(bArr.length * 2);
            sb.append('M');
            for (byte b : bArr) {
                int i = b & 255;
                if (i >= 192) {
                    sb.append("AACAAAAHAAALMAAAQASTAVAAAZaacaaaahaaalmaaaqastava.az0123456789-,".charAt(i - 192));
                } else {
                    if (i >= 128) {
                        sb.append(',');
                    } else if (i >= 64) {
                        sb.append(SignatureVisitor.SUPER);
                    }
                    sb.append(b & 63);
                }
            }
            sb.append('z');
            return sb.toString();
        } catch (Exception e) {
            FileLog.m1160e(e);
            return "";
        }
    }

    private static List<String> splitSvgTransforms(String str) {
        if (str == null) {
            return Collections.EMPTY_LIST;
        }
        String strTrim = str.trim();
        if (strTrim.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        String[] strArrSplit = SPLIT_BOUNDARY.split(strTrim);
        ArrayList arrayList = new ArrayList(strArrSplit.length);
        for (String str2 : strArrSplit) {
            String strTrim2 = str2.trim();
            if (!strTrim2.isEmpty()) {
                arrayList.add(strTrim2);
            }
        }
        return arrayList;
    }
}
