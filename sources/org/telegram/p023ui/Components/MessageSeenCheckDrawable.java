package org.telegram.p023ui.Components;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.DialogCell;

/* loaded from: classes6.dex */
public class MessageSeenCheckDrawable {
    private int colorKey;
    private Drawable drawable;

    /* renamed from: h */
    private int f1890h;
    private int lastColor;
    private float lastDensity;
    private CharSequence lastSpanned;

    /* renamed from: oy */
    private float f1891oy;
    private int resId;

    /* renamed from: w */
    private int f1892w;

    public MessageSeenCheckDrawable(int i, int i2) {
        this.f1892w = -1;
        this.f1890h = -1;
        this.f1891oy = 4.66f;
        this.resId = i;
        this.colorKey = i2;
    }

    public MessageSeenCheckDrawable(int i, int i2, int i3, int i4, float f) {
        this(i, i2);
        this.f1892w = i3;
        this.f1890h = i4;
        this.f1891oy = f;
    }

    public CharSequence getSpanned(Context context, Theme.ResourcesProvider resourcesProvider) {
        if (this.lastSpanned != null && this.drawable != null && AndroidUtilities.density == this.lastDensity) {
            if (this.lastColor != Theme.getColor(this.colorKey, resourcesProvider)) {
                Drawable drawable = this.drawable;
                int color = Theme.getColor(this.colorKey, resourcesProvider);
                this.lastColor = color;
                drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            }
            return this.lastSpanned;
        }
        if (context == null) {
            return null;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("v ");
        this.lastDensity = AndroidUtilities.density;
        Drawable drawableMutate = context.getResources().getDrawable(this.resId).mutate();
        this.drawable = drawableMutate;
        int color2 = Theme.getColor(this.colorKey, resourcesProvider);
        this.lastColor = color2;
        drawableMutate.setColorFilter(new PorterDuffColorFilter(color2, PorterDuff.Mode.SRC_IN));
        int i = this.f1892w;
        int intrinsicWidth = i <= 0 ? this.drawable.getIntrinsicWidth() : AndroidUtilities.m1146dp(i);
        int i2 = this.f1890h;
        int intrinsicHeight = i2 <= 0 ? this.drawable.getIntrinsicHeight() : AndroidUtilities.m1146dp(i2);
        int iM1146dp = AndroidUtilities.m1146dp(this.f1891oy);
        this.drawable.setBounds(0, iM1146dp, intrinsicWidth, intrinsicHeight + iM1146dp);
        spannableStringBuilder.setSpan(new ImageSpan(this.drawable, 2), 0, 1, 33);
        spannableStringBuilder.setSpan(new DialogCell.FixedWidthSpan(AndroidUtilities.m1146dp(2.0f)), 1, 2, 33);
        this.lastSpanned = spannableStringBuilder;
        return spannableStringBuilder;
    }
}
