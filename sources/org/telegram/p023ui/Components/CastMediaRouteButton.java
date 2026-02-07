package org.telegram.p023ui.Components;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import androidx.mediarouter.app.MediaRouteButton;
import java.lang.reflect.Field;
import org.telegram.messenger.ApplicationLoader;

/* loaded from: classes6.dex */
public abstract class CastMediaRouteButton extends MediaRouteButton {
    private boolean lastConnected;

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
    }

    public abstract void stateUpdated(boolean z);

    public CastMediaRouteButton(Context context) {
        super(new ContextWrapper(context) { // from class: org.telegram.ui.Components.CastMediaRouteButton.1
            @Override // android.content.ContextWrapper, android.content.Context
            public Resources getResources() {
                return ApplicationLoader.applicationContext.getResources();
            }
        });
    }

    public boolean isConnected() throws NoSuchFieldException, SecurityException {
        Field declaredField;
        try {
            declaredField = MediaRouteButton.class.getDeclaredField("mConnectionState");
            declaredField.setAccessible(true);
        } catch (Exception unused) {
        }
        return ((Integer) declaredField.get(this)).intValue() > 0;
    }

    @Override // android.view.View
    protected void dispatchDraw(Canvas canvas) throws NoSuchFieldException, SecurityException {
        checkConnected();
    }

    @Override // androidx.mediarouter.app.MediaRouteButton, android.view.View
    protected void onDraw(Canvas canvas) throws NoSuchFieldException, SecurityException {
        checkConnected();
    }

    @Override // android.view.View
    public void invalidate() throws NoSuchFieldException, SecurityException {
        super.invalidate();
        checkConnected();
    }

    @Override // androidx.mediarouter.app.MediaRouteButton, android.view.View
    public void onAttachedToWindow() throws NoSuchFieldException, SecurityException {
        super.onAttachedToWindow();
        checkConnected();
    }

    private void checkConnected() throws NoSuchFieldException, SecurityException {
        boolean zIsConnected = isConnected();
        if (this.lastConnected != zIsConnected) {
            this.lastConnected = zIsConnected;
            stateUpdated(zIsConnected);
        }
    }
}
