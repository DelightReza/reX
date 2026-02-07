package org.telegram.p023ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.ViewGroup;
import org.mvel2.MVEL;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.Paint.Views.EntityView;
import org.telegram.p023ui.Components.Point;
import org.telegram.p023ui.Components.Rect;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_stories;

/* loaded from: classes6.dex */
public class LocationView extends EntityView {
    private int currentColor;
    private int currentType;
    private boolean hasColor;
    public TLRPC.MessageMedia location;
    public final LocationMarker marker;
    public TL_stories.MediaArea mediaArea;

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected float getMaxScale() {
        return 1.5f;
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected float getStickyPaddingLeft() {
        return this.marker.padx;
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected float getStickyPaddingTop() {
        return this.marker.pady;
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected float getStickyPaddingRight() {
        return this.marker.padx;
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected float getStickyPaddingBottom() {
        return this.marker.pady;
    }

    private static String deg(double d) {
        double dAbs = Math.abs(d);
        double dFloor = Math.floor(dAbs);
        double dFloor2 = Math.floor((dAbs - dFloor) * 60.0d);
        StringBuilder sb = new StringBuilder();
        sb.append("" + ((int) dFloor) + "°");
        sb.append(dFloor2 <= 0.0d ? MVEL.VERSION_SUB : "");
        sb.append(dFloor2 < 10.0d ? MVEL.VERSION_SUB : "");
        sb.append((int) dFloor2);
        sb.append("'");
        String string = sb.toString();
        double dFloor3 = Math.floor(Math.floor(dFloor2) * 60.0d);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(string);
        sb2.append(dFloor3 <= 0.0d ? MVEL.VERSION_SUB : "");
        sb2.append(dFloor3 < 10.0d ? MVEL.VERSION_SUB : "");
        sb2.append((int) dFloor3);
        sb2.append("\"");
        return sb2.toString();
    }

    public static String geo(double d, double d2) {
        StringBuilder sb = new StringBuilder();
        sb.append(deg(d));
        sb.append(d > 0.0d ? "N" : "S");
        sb.append(" ");
        sb.append(deg(d2));
        sb.append(d2 > 0.0d ? "E" : "W");
        return sb.toString();
    }

    public LocationView(Context context, Point point, int i, TLRPC.MessageMedia messageMedia, TL_stories.MediaArea mediaArea, float f, int i2) {
        super(context, point);
        LocationMarker locationMarker = new LocationMarker(context, 0, f, 0);
        this.marker = locationMarker;
        locationMarker.setMaxWidth(i2);
        setLocation(i, messageMedia, mediaArea);
        locationMarker.setType(0, this.currentColor);
        addView(locationMarker, LayoutHelper.createFrame(-2, -2, 51));
        setClipChildren(false);
        setClipToPadding(false);
        updatePosition();
    }

    public void setLocation(int i, TLRPC.MessageMedia messageMedia, TL_stories.MediaArea mediaArea) {
        String strGeo;
        this.location = messageMedia;
        this.mediaArea = mediaArea;
        String str = null;
        if (messageMedia instanceof TLRPC.TL_messageMediaGeo) {
            TLRPC.GeoPoint geoPoint = messageMedia.geo;
            strGeo = geo(geoPoint.lat, geoPoint._long);
        } else if (messageMedia instanceof TLRPC.TL_messageMediaVenue) {
            String upperCase = messageMedia.title.toUpperCase();
            str = ((TLRPC.TL_messageMediaVenue) messageMedia).emoji;
            strGeo = upperCase;
        } else {
            strGeo = "";
        }
        this.marker.setCodeEmoji(i, str);
        this.marker.setText(strGeo);
        updateSelectionView();
    }

    public void setMaxWidth(int i) {
        this.marker.setMaxWidth(i);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updatePosition();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        updatePosition();
    }

    public void setColor(int i) {
        this.hasColor = true;
        this.currentColor = i;
    }

    public boolean hasColor() {
        return this.hasColor;
    }

    public void setType(int i) {
        LocationMarker locationMarker = this.marker;
        this.currentType = i;
        locationMarker.setType(i, this.currentColor);
    }

    public int getTypesCount() {
        return this.marker.getTypesCount() - (!this.hasColor ? 1 : 0);
    }

    public int getColor() {
        return this.currentColor;
    }

    public int getType() {
        return this.currentType;
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    public Rect getSelectionBounds() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup == null) {
            return new Rect();
        }
        float scaleX = viewGroup.getScaleX();
        float measuredWidth = (getMeasuredWidth() * getScale()) + (AndroidUtilities.m1146dp(64.0f) / scaleX);
        float measuredHeight = (getMeasuredHeight() * getScale()) + (AndroidUtilities.m1146dp(64.0f) / scaleX);
        float positionX = (getPositionX() - (measuredWidth / 2.0f)) * scaleX;
        return new Rect(positionX, (getPositionY() - (measuredHeight / 2.0f)) * scaleX, ((measuredWidth * scaleX) + positionX) - positionX, measuredHeight * scaleX);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    public TextViewSelectionView createSelectionView() {
        return new TextViewSelectionView(getContext());
    }

    public class TextViewSelectionView extends EntityView.SelectionView {
        private final Paint clearPaint;
        private Path path;

        public TextViewSelectionView(Context context) {
            super(context);
            Paint paint = new Paint(1);
            this.clearPaint = paint;
            this.path = new Path();
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }

        @Override // org.telegram.ui.Components.Paint.Views.EntityView.SelectionView
        protected int pointInsideHandle(float f, float f2) {
            float fM1146dp = AndroidUtilities.m1146dp(1.0f);
            float fM1146dp2 = AndroidUtilities.m1146dp(19.5f);
            float f3 = fM1146dp + fM1146dp2;
            float f4 = f3 * 2.0f;
            float measuredWidth = getMeasuredWidth() - f4;
            float measuredHeight = ((getMeasuredHeight() - f4) / 2.0f) + f3;
            if (f > f3 - fM1146dp2 && f2 > measuredHeight - fM1146dp2 && f < f3 + fM1146dp2 && f2 < measuredHeight + fM1146dp2) {
                return 1;
            }
            float f5 = f3 + measuredWidth;
            return (f <= f5 - fM1146dp2 || f2 <= measuredHeight - fM1146dp2 || f >= f5 + fM1146dp2 || f2 >= measuredHeight + fM1146dp2) ? 0 : 2;
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            Canvas canvas2;
            super.onDraw(canvas);
            int saveCount = canvas.getSaveCount();
            float showAlpha = getShowAlpha();
            if (showAlpha <= 0.0f) {
                return;
            }
            if (showAlpha < 1.0f) {
                int i = (int) (showAlpha * 255.0f);
                canvas2 = canvas;
                canvas2.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), i, 31);
            } else {
                canvas2 = canvas;
            }
            float fM1146dp = AndroidUtilities.m1146dp(2.0f);
            float fDpf2 = AndroidUtilities.dpf2(5.66f);
            float fM1146dp2 = fM1146dp + fDpf2 + AndroidUtilities.m1146dp(15.0f);
            float f = fM1146dp2 * 2.0f;
            float measuredWidth = getMeasuredWidth() - f;
            float measuredHeight = getMeasuredHeight() - f;
            RectF rectF = AndroidUtilities.rectTmp;
            float f2 = fM1146dp2 + measuredWidth;
            float f3 = fM1146dp2 + measuredHeight;
            rectF.set(fM1146dp2, fM1146dp2, f2, f3);
            float fM1146dp3 = AndroidUtilities.m1146dp(12.0f);
            float fMin = Math.min(fM1146dp3, measuredWidth / 2.0f);
            float f4 = measuredHeight / 2.0f;
            float fMin2 = Math.min(fM1146dp3, f4);
            this.path.rewind();
            float f5 = fMin * 2.0f;
            float f6 = fM1146dp2 + f5;
            float f7 = 2.0f * fMin2;
            float f8 = fM1146dp2 + f7;
            rectF.set(fM1146dp2, fM1146dp2, f6, f8);
            this.path.arcTo(rectF, 180.0f, 90.0f);
            float f9 = f2 - f5;
            rectF.set(f9, fM1146dp2, f2, f8);
            this.path.arcTo(rectF, 270.0f, 90.0f);
            canvas2.drawPath(this.path, this.paint);
            this.path.rewind();
            float f10 = f3 - f7;
            rectF.set(fM1146dp2, f10, f6, f3);
            this.path.arcTo(rectF, 180.0f, -90.0f);
            rectF.set(f9, f10, f2, f3);
            this.path.arcTo(rectF, 90.0f, -90.0f);
            canvas2.drawPath(this.path, this.paint);
            float f11 = fM1146dp2 + f4;
            canvas2.drawCircle(fM1146dp2, f11, fDpf2, this.dotStrokePaint);
            canvas2.drawCircle(fM1146dp2, f11, (fDpf2 - AndroidUtilities.m1146dp(1.0f)) + 1.0f, this.dotPaint);
            canvas2.drawCircle(f2, f11, fDpf2, this.dotStrokePaint);
            canvas2.drawCircle(f2, f11, (fDpf2 - AndroidUtilities.m1146dp(1.0f)) + 1.0f, this.dotPaint);
            canvas2.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), 255, 31);
            float f12 = fM1146dp2 + fMin2;
            float f13 = f3 - fMin2;
            canvas.drawLine(fM1146dp2, f12, fM1146dp2, f13, this.paint);
            canvas.drawLine(f2, f12, f2, f13, this.paint);
            canvas.drawCircle(f2, f11, (AndroidUtilities.m1146dp(1.0f) + fDpf2) - 1.0f, this.clearPaint);
            canvas.drawCircle(fM1146dp2, f11, (fDpf2 + AndroidUtilities.m1146dp(1.0f)) - 1.0f, this.clearPaint);
            canvas.restoreToCount(saveCount);
        }
    }
}
