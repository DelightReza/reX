package org.telegram.p023ui;

import android.content.Context;
import android.widget.FrameLayout;
import androidx.annotation.Keep;
import org.telegram.messenger.Utilities;

@Keep
/* loaded from: classes5.dex */
public abstract class IUpdateButton extends FrameLayout {
    @Keep
    public void onTranslationUpdate(Utilities.Callback<Float> callback) {
    }

    @Keep
    public void update(boolean z) {
    }

    @Keep
    public IUpdateButton(Context context) {
        super(context);
    }
}
