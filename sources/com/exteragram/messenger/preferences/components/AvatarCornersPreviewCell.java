package com.exteragram.messenger.preferences.components;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.graphics.ColorUtils;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.preferences.components.AltSeekbar;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.INavigationLayout;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.tgnet.TLObject;
import p017j$.util.Objects;

/* loaded from: classes.dex */
public class AvatarCornersPreviewCell extends FrameLayout implements CustomPreferenceCell {
    private int lastWidth;
    private final Paint outlinePaint;
    private final FrameLayout preview;
    private final RectF rect;
    private final AltSeekbar seekBar;

    public AvatarCornersPreviewCell(Context context, final INavigationLayout iNavigationLayout) {
        super(context);
        this.rect = new RectF();
        Paint paint = new Paint(1);
        this.outlinePaint = paint;
        setWillNotDraw(false);
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        AltSeekbar altSeekbar = new AltSeekbar(context, new AltSeekbar.OnDrag() { // from class: com.exteragram.messenger.preferences.components.AvatarCornersPreviewCell$$ExternalSyntheticLambda0
            @Override // com.exteragram.messenger.preferences.components.AltSeekbar.OnDrag
            public final void run(float f) {
                this.f$0.lambda$new$0(iNavigationLayout, f);
            }
        }, 0, 28, LocaleController.getString(C2369R.string.AvatarCorners), LocaleController.getString(C2369R.string.AvatarCornersLeft), LocaleController.getString(C2369R.string.AvatarCornersRight));
        this.seekBar = altSeekbar;
        altSeekbar.setProgress((ExteraConfig.avatarCorners - 0) / 28);
        addView(altSeekbar, LayoutHelper.createFrame(-1, -2.0f));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_switchTrack), 63));
        paint.setStrokeWidth(Math.max(2, AndroidUtilities.m1146dp(1.0f)));
        FrameLayout frameLayout = new FrameLayout(context) { // from class: com.exteragram.messenger.preferences.components.AvatarCornersPreviewCell.1
            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                int color = Theme.getColor(Theme.key_switchTrack);
                int iRed = Color.red(color);
                int iGreen = Color.green(color);
                int iBlue = Color.blue(color);
                float measuredWidth = getMeasuredWidth();
                float measuredHeight = getMeasuredHeight();
                AvatarCornersPreviewCell.this.rect.set(0.0f, 0.0f, measuredWidth, measuredHeight);
                Theme.dialogs_onlineCirclePaint.setColor(Color.argb(20, iRed, iGreen, iBlue));
                canvas.drawRoundRect(AvatarCornersPreviewCell.this.rect, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), Theme.dialogs_onlineCirclePaint);
                float strokeWidth = AvatarCornersPreviewCell.this.outlinePaint.getStrokeWidth() / 2.0f;
                AvatarCornersPreviewCell.this.rect.set(strokeWidth, strokeWidth, measuredWidth - strokeWidth, measuredHeight - strokeWidth);
                canvas.drawRoundRect(AvatarCornersPreviewCell.this.rect, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), AvatarCornersPreviewCell.this.outlinePaint);
                Theme.dialogs_onlineCirclePaint.setColor(Theme.getColor(Theme.key_chats_onlineCircle));
                float f = measuredHeight / 2.0f;
                canvas.drawCircle(AndroidUtilities.m1146dp(68.0f), AndroidUtilities.dpf2(20.5f) + f, AndroidUtilities.m1146dp(7.0f), Theme.dialogs_onlineCirclePaint);
                Theme.dialogs_onlineCirclePaint.setColor(Color.argb(204, iRed, iGreen, iBlue));
                float f2 = measuredWidth / 2.0f;
                canvas.drawRoundRect(AndroidUtilities.m1146dp(92.0f), f - AndroidUtilities.dpf2(7.5f), measuredWidth - AndroidUtilities.m1146dp(90.0f), f - AndroidUtilities.dpf2(15.5f), f2, f2, Theme.dialogs_onlineCirclePaint);
                Path path = new Path();
                path.addCircle(AndroidUtilities.m1146dp(68.0f), AndroidUtilities.dpf2(20.5f) + f, AndroidUtilities.m1146dp(12.0f), Path.Direction.CCW);
                canvas.clipPath(path, Region.Op.DIFFERENCE);
                Theme.dialogs_onlineCirclePaint.setColor(Color.argb(90, iRed, iGreen, iBlue));
                canvas.drawRoundRect(AndroidUtilities.m1146dp(92.0f), AndroidUtilities.dpf2(7.5f) + f, measuredWidth - AndroidUtilities.m1146dp(50.0f), AndroidUtilities.m1146dp(15.5f) + f, f2, f2, Theme.dialogs_onlineCirclePaint);
                canvas.drawRoundRect(AndroidUtilities.m1146dp(20.0f), f - AndroidUtilities.m1146dp(28.0f), AndroidUtilities.m1146dp(76.0f), f + AndroidUtilities.m1146dp(28.0f), ExteraConfig.getAvatarCorners(56.0f), ExteraConfig.getAvatarCorners(56.0f), Theme.dialogs_onlineCirclePaint);
            }
        };
        this.preview = frameLayout;
        frameLayout.setWillNotDraw(false);
        addView(frameLayout, LayoutHelper.createFrame(-1, -1.0f, 49, 21.0f, 112.0f, 21.0f, 21.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(INavigationLayout iNavigationLayout, float f) {
        SharedPreferences.Editor editor = ExteraConfig.editor;
        ExteraConfig.avatarCorners = f;
        editor.putFloat("avatarCorners", f).apply();
        invalidate();
        iNavigationLayout.rebuildAllFragmentViews(false, false);
    }

    @Override // android.view.View
    public void invalidate() {
        super.invalidate();
        this.preview.invalidate();
        this.seekBar.invalidate();
        this.lastWidth = -1;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.m1146dp(21.0f), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.m1146dp(21.0f) : 0), getMeasuredHeight() - 1, Theme.dividerPaint);
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(222.0f), TLObject.FLAG_30));
        int size = View.MeasureSpec.getSize(i);
        if (this.lastWidth != size) {
            this.lastWidth = size;
        }
    }

    @Override // com.exteragram.messenger.preferences.components.CustomPreferenceCell
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AvatarCornersPreviewCell) {
            AvatarCornersPreviewCell avatarCornersPreviewCell = (AvatarCornersPreviewCell) obj;
            if (Objects.equals(this.preview, avatarCornersPreviewCell.preview) && Objects.equals(this.seekBar, avatarCornersPreviewCell.seekBar)) {
                return true;
            }
        }
        return false;
    }
}
