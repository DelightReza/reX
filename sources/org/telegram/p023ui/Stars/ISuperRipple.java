package org.telegram.p023ui.Stars;

import android.view.View;

/* loaded from: classes6.dex */
public abstract class ISuperRipple {
    public final View view;

    public abstract void animate(float f, float f2, float f3);

    public ISuperRipple(View view) {
        this.view = view;
    }
}
