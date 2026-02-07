package com.yandex.runtime.view.internal;

import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes4.dex */
class GLRenderThread extends Thread {
    private EGLConfigChooserImpl configChooser;
    private EGL10 egl;
    private EGLConfig eglConfig;
    private EGLContext eglContext;
    private EGLDisplay eglDisplay;
    private EGLSurface eglSurface;
    private boolean forceRender;

    /* renamed from: gl */
    private GL10 f424gl;
    private GLContextFactory glContextFactory;
    private Rect newViewport;
    private boolean paused;
    private GLSurfaceView.Renderer renderer;
    private boolean requested;
    private boolean stopped;
    private Object surface;

    GLRenderThread(Object obj, EGLConfigChooserImpl eGLConfigChooserImpl, GLContextFactory gLContextFactory, GLSurfaceView.Renderer renderer) {
        super("YMK_RenderThread");
        this.stopped = false;
        this.requested = false;
        this.paused = false;
        this.forceRender = false;
        this.newViewport = null;
        this.surface = obj;
        this.configChooser = eGLConfigChooserImpl;
        this.glContextFactory = gLContextFactory;
        this.renderer = renderer;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001c A[Catch: all -> 0x0016, TRY_LEAVE, TryCatch #0 {all -> 0x0016, blocks: (B:4:0x000d, B:6:0x0011, B:13:0x001c, B:15:0x0020, B:17:0x0024, B:19:0x0028, B:23:0x002e, B:25:0x0032, B:27:0x0036, B:28:0x004a, B:29:0x004c, B:11:0x0018), top: B:36:0x000d }] */
    @Override // java.lang.Thread, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void run() {
        /*
            r4 = this;
            r4.initGL()
            android.opengl.GLSurfaceView$Renderer r0 = r4.renderer
            javax.microedition.khronos.opengles.GL10 r1 = r4.f424gl
            javax.microedition.khronos.egl.EGLConfig r2 = r4.eglConfig
            r0.onSurfaceCreated(r1, r2)
        Lc:
            monitor-enter(r4)
        Ld:
            boolean r0 = r4.paused     // Catch: java.lang.Throwable -> L16
            if (r0 == 0) goto L18
            boolean r1 = r4.forceRender     // Catch: java.lang.Throwable -> L16
            if (r1 == 0) goto L1c
            goto L18
        L16:
            r0 = move-exception
            goto L60
        L18:
            boolean r1 = r4.requested     // Catch: java.lang.Throwable -> L16
            if (r1 != 0) goto L24
        L1c:
            boolean r1 = r4.stopped     // Catch: java.lang.Throwable -> L16
            if (r1 != 0) goto L24
            r4.wait()     // Catch: java.lang.InterruptedException -> Ld java.lang.Throwable -> L16
            goto Ld
        L24:
            boolean r1 = r4.stopped     // Catch: java.lang.Throwable -> L16
            if (r1 == 0) goto L2d
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L16
            r4.finishGL()
            return
        L2d:
            r1 = 0
            r4.requested = r1     // Catch: java.lang.Throwable -> L16
            if (r0 != 0) goto L4a
            android.graphics.Rect r0 = r4.newViewport     // Catch: java.lang.Throwable -> L16
            if (r0 == 0) goto L4a
            android.opengl.GLSurfaceView$Renderer r1 = r4.renderer     // Catch: java.lang.Throwable -> L16
            javax.microedition.khronos.opengles.GL10 r2 = r4.f424gl     // Catch: java.lang.Throwable -> L16
            int r0 = r0.width()     // Catch: java.lang.Throwable -> L16
            android.graphics.Rect r3 = r4.newViewport     // Catch: java.lang.Throwable -> L16
            int r3 = r3.height()     // Catch: java.lang.Throwable -> L16
            r1.onSurfaceChanged(r2, r0, r3)     // Catch: java.lang.Throwable -> L16
            r0 = 0
            r4.newViewport = r0     // Catch: java.lang.Throwable -> L16
        L4a:
            boolean r0 = r4.paused     // Catch: java.lang.Throwable -> L16
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L16
            android.opengl.GLSurfaceView$Renderer r1 = r4.renderer
            javax.microedition.khronos.opengles.GL10 r2 = r4.f424gl
            r1.onDrawFrame(r2)
            if (r0 != 0) goto Lc
            javax.microedition.khronos.egl.EGL10 r0 = r4.egl
            javax.microedition.khronos.egl.EGLDisplay r1 = r4.eglDisplay
            javax.microedition.khronos.egl.EGLSurface r2 = r4.eglSurface
            r0.eglSwapBuffers(r1, r2)
            goto Lc
        L60:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L16
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yandex.runtime.view.internal.GLRenderThread.run():void");
    }

    public synchronized void requestRender() {
        try {
            if (!this.paused || this.forceRender) {
                this.requested = true;
                notifyAll();
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void onPause() {
        this.paused = true;
    }

    public synchronized void onResume() {
        this.paused = false;
        this.requested = true;
        notifyAll();
    }

    public synchronized void setForceRender(boolean z) {
        this.forceRender = z;
    }

    public synchronized void onSizeChanged(int i, int i2) {
        this.newViewport = new Rect(0, 0, i, i2);
        requestRender();
    }

    public synchronized void finish() {
        this.stopped = true;
        notifyAll();
    }

    private void initGL() {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        this.egl = egl10;
        EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.eglDisplay = eGLDisplayEglGetDisplay;
        if (eGLDisplayEglGetDisplay == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("eglGetDisplay() failed");
        }
        this.egl.eglInitialize(eGLDisplayEglGetDisplay, new int[2]);
        checkEglError("eglInitialize() failed");
        EGLConfig eGLConfigChooseConfig = this.configChooser.chooseConfig(this.egl, this.eglDisplay);
        this.eglConfig = eGLConfigChooseConfig;
        this.eglContext = this.glContextFactory.createContext(this.egl, this.eglDisplay, eGLConfigChooseConfig);
        this.eglSurface = this.egl.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, this.surface, null);
        checkEglError("eglCreateWindowSurface() failed");
        EGL10 egl102 = this.egl;
        EGLDisplay eGLDisplay = this.eglDisplay;
        EGLSurface eGLSurface = this.eglSurface;
        egl102.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.eglContext);
        checkEglError("eglMakeCurrent() failed");
        this.f424gl = (GL10) this.eglContext.getGL();
    }

    private void finishGL() {
        EGL10 egl10 = this.egl;
        EGLDisplay eGLDisplay = this.eglDisplay;
        EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
        egl10.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        checkEglError("eglMakeCurrent() failed");
        this.glContextFactory.destroyContext(this.egl, this.eglDisplay, this.eglContext);
        this.egl.eglDestroySurface(this.eglDisplay, this.eglSurface);
        checkEglError("eglDestroySurface() failed");
    }

    private void checkEglError(String str) {
        int iEglGetError = this.egl.eglGetError();
        if (iEglGetError == 12288) {
            return;
        }
        throw new RuntimeException(str + "; Got EGL error " + iEglGetError);
    }
}
