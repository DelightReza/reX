package org.telegram.p023ui.Components;

import android.view.View;
import org.telegram.messenger.ImageReceiver;

/* loaded from: classes3.dex */
public interface AttachableDrawable {
    void onAttachedToWindow(ImageReceiver imageReceiver);

    void onDetachedFromWindow(ImageReceiver imageReceiver);

    void setParent(View view);

    /* renamed from: org.telegram.ui.Components.AttachableDrawable$-CC, reason: invalid class name */
    /* loaded from: classes6.dex */
    public abstract /* synthetic */ class CC {
        public static void $default$setParent(AttachableDrawable attachableDrawable, View view) {
        }
    }
}
