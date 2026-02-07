package com.exteragram.messenger.icons;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import com.exteragram.messenger.icons.p007ui.picker.IconObserver;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class ExteraResources extends Resources {
    private final Resources mResources;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ExteraResources(Resources mResources) {
        super(mResources.getAssets(), mResources.getDisplayMetrics(), mResources.getConfiguration());
        Intrinsics.checkNotNullParameter(mResources, "mResources");
        this.mResources = mResources;
        IconManager.initialize$default(IconManager.INSTANCE, false, 1, null);
    }

    @Override // android.content.res.Resources
    public Drawable getDrawableForDensity(int i, int i2, Resources.Theme theme) throws Resources.NotFoundException {
        IconObserver.INSTANCE.log(i);
        IconManager iconManager = IconManager.INSTANCE;
        Drawable drawable = iconManager.getDrawable(i, i2, theme);
        return drawable != null ? drawable : this.mResources.getDrawableForDensity(iconManager.getIcon(i), i2, theme);
    }

    public final Drawable getOriginalDrawable(int i) {
        try {
            return this.mResources.getDrawable(i);
        } catch (Resources.NotFoundException unused) {
            return null;
        }
    }
}
