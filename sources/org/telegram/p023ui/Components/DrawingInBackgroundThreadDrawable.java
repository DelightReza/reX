package org.telegram.p023ui.Components;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.p023ui.ActionBar.Theme;

/* loaded from: classes6.dex */
public abstract class DrawingInBackgroundThreadDrawable implements NotificationCenter.NotificationCenterDelegate {
    public static DispatchQueuePool queuePool;
    boolean attachedToWindow;
    Bitmap backgroundBitmap;
    Canvas backgroundCanvas;
    private final DispatchQueue backgroundQueue;
    Bitmap bitmap;
    Canvas bitmapCanvas;
    private boolean bitmapUpdating;
    private int currentOpenedLayerFlags;
    boolean error;
    int frameGuid;
    int height;
    private int lastFrameId;
    boolean needSwapBitmaps;
    int padding;
    protected boolean paused;
    private boolean reset;
    public final int threadIndex;
    int width;
    private int currentLayerNum = 1;
    private Paint paint = new Paint(1);
    private final Runnable bitmapCreateTask = new Runnable() { // from class: org.telegram.ui.Components.DrawingInBackgroundThreadDrawable.1
        /* JADX WARN: Removed duplicated region for block: B:11:0x0020 A[Catch: Exception -> 0x001e, TryCatch #0 {Exception -> 0x001e, blocks: (B:2:0x0000, B:4:0x000b, B:6:0x0015, B:15:0x0042, B:11:0x0020, B:13:0x0026, B:14:0x0029), top: B:20:0x0000 }] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void run() {
            /*
                r4 = this;
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                int r1 = r0.height     // Catch: java.lang.Exception -> L1e
                int r2 = r0.padding     // Catch: java.lang.Exception -> L1e
                int r1 = r1 + r2
                android.graphics.Bitmap r0 = r0.backgroundBitmap     // Catch: java.lang.Exception -> L1e
                if (r0 == 0) goto L20
                int r0 = r0.getWidth()     // Catch: java.lang.Exception -> L1e
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r2 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                int r3 = r2.width     // Catch: java.lang.Exception -> L1e
                if (r0 != r3) goto L20
                android.graphics.Bitmap r0 = r2.backgroundBitmap     // Catch: java.lang.Exception -> L1e
                int r0 = r0.getHeight()     // Catch: java.lang.Exception -> L1e
                if (r0 == r1) goto L42
                goto L20
            L1e:
                r0 = move-exception
                goto L72
            L20:
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                android.graphics.Bitmap r0 = r0.backgroundBitmap     // Catch: java.lang.Exception -> L1e
                if (r0 == 0) goto L29
                r0.recycle()     // Catch: java.lang.Exception -> L1e
            L29:
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                int r2 = r0.width     // Catch: java.lang.Exception -> L1e
                android.graphics.Bitmap$Config r3 = android.graphics.Bitmap.Config.ARGB_8888     // Catch: java.lang.Exception -> L1e
                android.graphics.Bitmap r1 = android.graphics.Bitmap.createBitmap(r2, r1, r3)     // Catch: java.lang.Exception -> L1e
                r0.backgroundBitmap = r1     // Catch: java.lang.Exception -> L1e
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                android.graphics.Canvas r1 = new android.graphics.Canvas     // Catch: java.lang.Exception -> L1e
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r2 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                android.graphics.Bitmap r2 = r2.backgroundBitmap     // Catch: java.lang.Exception -> L1e
                r1.<init>(r2)     // Catch: java.lang.Exception -> L1e
                r0.backgroundCanvas = r1     // Catch: java.lang.Exception -> L1e
            L42:
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                android.graphics.Bitmap r0 = r0.backgroundBitmap     // Catch: java.lang.Exception -> L1e
                r1 = 0
                r0.eraseColor(r1)     // Catch: java.lang.Exception -> L1e
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                android.graphics.Canvas r0 = r0.backgroundCanvas     // Catch: java.lang.Exception -> L1e
                r0.save()     // Catch: java.lang.Exception -> L1e
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                android.graphics.Canvas r1 = r0.backgroundCanvas     // Catch: java.lang.Exception -> L1e
                int r0 = r0.padding     // Catch: java.lang.Exception -> L1e
                float r0 = (float) r0     // Catch: java.lang.Exception -> L1e
                r2 = 0
                r1.translate(r2, r0)     // Catch: java.lang.Exception -> L1e
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                android.graphics.Canvas r1 = r0.backgroundCanvas     // Catch: java.lang.Exception -> L1e
                r0.drawInBackground(r1)     // Catch: java.lang.Exception -> L1e
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                android.graphics.Canvas r0 = r0.backgroundCanvas     // Catch: java.lang.Exception -> L1e
                r0.restore()     // Catch: java.lang.Exception -> L1e
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this     // Catch: java.lang.Exception -> L1e
                android.graphics.Bitmap r0 = r0.backgroundBitmap     // Catch: java.lang.Exception -> L1e
                r0.prepareToDraw()     // Catch: java.lang.Exception -> L1e
                goto L7a
            L72:
                org.telegram.messenger.FileLog.m1160e(r0)
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this
                r1 = 1
                r0.error = r1
            L7a:
                org.telegram.ui.Components.DrawingInBackgroundThreadDrawable r0 = org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.this
                java.lang.Runnable r0 = r0.uiFrameRunnable
                org.telegram.messenger.AndroidUtilities.runOnUIThread(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.DrawingInBackgroundThreadDrawable.RunnableC37401.run():void");
        }
    };
    Runnable uiFrameRunnable = new Runnable() { // from class: org.telegram.ui.Components.DrawingInBackgroundThreadDrawable.2
        @Override // java.lang.Runnable
        public void run() {
            DrawingInBackgroundThreadDrawable.this.bitmapUpdating = false;
            DrawingInBackgroundThreadDrawable.this.onFrameReady();
            DrawingInBackgroundThreadDrawable drawingInBackgroundThreadDrawable = DrawingInBackgroundThreadDrawable.this;
            if (!drawingInBackgroundThreadDrawable.attachedToWindow) {
                drawingInBackgroundThreadDrawable.recycleBitmaps();
            } else {
                if (drawingInBackgroundThreadDrawable.frameGuid != drawingInBackgroundThreadDrawable.lastFrameId) {
                    return;
                }
                DrawingInBackgroundThreadDrawable.this.needSwapBitmaps = true;
            }
        }
    };

    public abstract void drawInBackground(Canvas canvas);

    protected abstract void drawInUiThread(Canvas canvas, float f);

    public void onFrameReady() {
    }

    public void onPaused() {
    }

    public void onResume() {
    }

    public abstract void prepareDraw(long j);

    public DrawingInBackgroundThreadDrawable() {
        if (queuePool == null) {
            queuePool = new DispatchQueuePool(2);
        }
        this.backgroundQueue = queuePool.getNextQueue();
        this.threadIndex = queuePool.pointer;
    }

    public void draw(Canvas canvas, long j, int i, int i2, float f) {
        if (this.error) {
            if (BuildVars.DEBUG_PRIVATE_VERSION) {
                canvas.drawRect(0.0f, 0.0f, i, i2, Theme.DEBUG_RED);
                return;
            }
            return;
        }
        this.height = i2;
        this.width = i;
        if (this.needSwapBitmaps) {
            this.needSwapBitmaps = false;
            Bitmap bitmap = this.bitmap;
            Canvas canvas2 = this.bitmapCanvas;
            this.bitmap = this.backgroundBitmap;
            this.bitmapCanvas = this.backgroundCanvas;
            this.backgroundBitmap = bitmap;
            this.backgroundCanvas = canvas2;
        }
        Bitmap bitmap2 = this.bitmap;
        if (bitmap2 == null || this.reset) {
            this.reset = false;
            if (bitmap2 != null) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(this.bitmap);
                AndroidUtilities.recycleBitmaps(arrayList);
                this.bitmap = null;
            }
            int i3 = this.height + this.padding;
            Bitmap bitmap3 = this.bitmap;
            if (bitmap3 == null || bitmap3.getHeight() != i3 || this.bitmap.getWidth() != this.width) {
                this.bitmap = Bitmap.createBitmap(this.width, i3, Bitmap.Config.ARGB_8888);
                this.bitmapCanvas = new Canvas(this.bitmap);
            } else {
                this.bitmap.eraseColor(0);
            }
            this.bitmapCanvas.save();
            this.bitmapCanvas.translate(0.0f, this.padding);
            drawInUiThread(this.bitmapCanvas, f);
            this.bitmapCanvas.restore();
        }
        if (!this.bitmapUpdating && !this.paused) {
            this.bitmapUpdating = true;
            prepareDraw(j);
            this.lastFrameId = this.frameGuid;
            this.backgroundQueue.postRunnable(this.bitmapCreateTask);
        }
        Bitmap bitmap4 = this.bitmap;
        if (bitmap4 != null) {
            this.paint.setAlpha((int) (f * 255.0f));
            canvas.save();
            canvas.translate(0.0f, -this.padding);
            drawBitmap(canvas, bitmap4, this.paint);
            canvas.restore();
        }
    }

    protected void drawBitmap(Canvas canvas, Bitmap bitmap, Paint paint) {
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
    }

    public void onAttachToWindow() {
        if (this.attachedToWindow) {
            return;
        }
        this.attachedToWindow = true;
        this.error = false;
        int currentHeavyOperationFlags = NotificationCenter.getGlobalInstance().getCurrentHeavyOperationFlags() & (~this.currentLayerNum);
        this.currentOpenedLayerFlags = currentHeavyOperationFlags;
        if (currentHeavyOperationFlags == 0 && this.paused) {
            this.paused = false;
            onResume();
        }
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.stopAllHeavyOperations);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.startAllHeavyOperations);
    }

    public void onDetachFromWindow() {
        if (this.attachedToWindow) {
            if (!this.bitmapUpdating) {
                recycleBitmaps();
            }
            this.attachedToWindow = false;
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.stopAllHeavyOperations);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.startAllHeavyOperations);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void recycleBitmaps() {
        ArrayList arrayList = new ArrayList();
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            arrayList.add(bitmap);
        }
        Bitmap bitmap2 = this.backgroundBitmap;
        if (bitmap2 != null) {
            arrayList.add(bitmap2);
        }
        this.bitmap = null;
        this.backgroundBitmap = null;
        this.backgroundCanvas = null;
        this.bitmapCanvas = null;
        AndroidUtilities.recycleBitmaps(arrayList);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        int i3;
        if (i == NotificationCenter.stopAllHeavyOperations) {
            Integer num = (Integer) objArr[0];
            if (this.currentLayerNum < num.intValue()) {
                if (num.intValue() != 512 || SharedConfig.getDevicePerformanceClass() < 2) {
                    int iIntValue = num.intValue() | this.currentOpenedLayerFlags;
                    this.currentOpenedLayerFlags = iIntValue;
                    if (iIntValue == 0 || this.paused) {
                        return;
                    }
                    this.paused = true;
                    onPaused();
                    return;
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.startAllHeavyOperations) {
            Integer num2 = (Integer) objArr[0];
            if (this.currentLayerNum >= num2.intValue() || (i3 = this.currentOpenedLayerFlags) == 0) {
                return;
            }
            int i4 = (~num2.intValue()) & i3;
            this.currentOpenedLayerFlags = i4;
            if (i4 == 0 && this.paused) {
                this.paused = false;
                onResume();
            }
        }
    }

    public void reset() {
        this.reset = true;
        this.frameGuid++;
        if (this.bitmap != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(this.bitmap);
            this.bitmap = null;
            AndroidUtilities.recycleBitmaps(arrayList);
        }
    }

    public static class DispatchQueuePool {
        int pointer;
        public final DispatchQueue[] pool;
        final int size;

        private DispatchQueuePool(int i) {
            this.size = i;
            this.pool = new DispatchQueue[i];
        }

        public DispatchQueue getNextQueue() {
            int i = this.pointer + 1;
            this.pointer = i;
            if (i > this.size - 1) {
                this.pointer = 0;
            }
            DispatchQueue[] dispatchQueueArr = this.pool;
            int i2 = this.pointer;
            DispatchQueue dispatchQueue = dispatchQueueArr[i2];
            if (dispatchQueue != null) {
                return dispatchQueue;
            }
            DispatchQueue dispatchQueue2 = new DispatchQueue("draw_background_queue_" + this.pointer);
            dispatchQueueArr[i2] = dispatchQueue2;
            return dispatchQueue2;
        }
    }

    public void setLayerNum(int i) {
        this.currentLayerNum = i;
        if (this.attachedToWindow) {
            this.currentOpenedLayerFlags = NotificationCenter.getGlobalInstance().getCurrentHeavyOperationFlags() & (~this.currentLayerNum);
        }
    }
}
