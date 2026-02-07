package org.telegram.p023ui.Components.blur3.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderNode;
import android.os.Build;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BotFullscreenButtons$$ExternalSyntheticApiModelOutline0;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.blur3.LiquidGlassEffect;
import org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawable;
import org.telegram.p023ui.Components.blur3.source.BlurredBackgroundSource;
import org.telegram.p023ui.Components.blur3.source.BlurredBackgroundSourceRenderNode;

/* loaded from: classes3.dex */
public class BlurredBackgroundDrawableRenderNode extends BlurredBackgroundDrawable {
    private LiquidGlassEffect liquidGlassEffect;
    private final Outline outline = new Outline();
    private final Rect outlineRect = new Rect();
    private final Paint paintShadow;
    private final Paint paintStrokeBottom;
    private final Paint paintStrokeTop;
    private final RenderNode renderNode;
    private final RenderNode renderNodeFill;
    private boolean renderNodeInvalidated;
    private final RenderNode renderNodeStroke;
    private final BlurredBackgroundSourceRenderNode source;

    public BlurredBackgroundDrawableRenderNode(BlurredBackgroundSourceRenderNode blurredBackgroundSourceRenderNode) {
        Paint paint = new Paint(1);
        this.paintShadow = paint;
        Paint paint2 = new Paint(1);
        this.paintStrokeTop = paint2;
        Paint paint3 = new Paint(1);
        this.paintStrokeBottom = paint3;
        RenderNode renderNodeM1151m = BotFullscreenButtons$$ExternalSyntheticApiModelOutline0.m1151m("BlurredBackgroundDrawableRenderNode");
        this.renderNode = renderNodeM1151m;
        this.renderNodeFill = BotFullscreenButtons$$ExternalSyntheticApiModelOutline0.m1151m("BlurredBackgroundDrawableRenderNode.Fill");
        this.renderNodeStroke = BotFullscreenButtons$$ExternalSyntheticApiModelOutline0.m1151m("BlurredBackgroundDrawableRenderNode.Stroke");
        renderNodeM1151m.setClipToOutline(true);
        renderNodeM1151m.setClipToBounds(true);
        this.source = blurredBackgroundSourceRenderNode;
        paint.setColor(0);
        Paint.Style style = Paint.Style.STROKE;
        paint2.setStyle(style);
        paint3.setStyle(style);
    }

    public void setLiquidGlassEffectAllowed() {
        this.liquidGlassEffect = new LiquidGlassEffect(this.renderNodeFill);
    }

    @Override // org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawable
    public BlurredBackgroundSource getSource() {
        return this.source;
    }

    @Override // org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawable
    protected void onBoundPropsChanged() {
        super.onBoundPropsChanged();
        BlurredBackgroundDrawable.Props props = this.boundProps;
        if (props.useFullStroke) {
            this.paintStrokeTop.setStrokeWidth(props.strokeWidthFull);
        } else {
            this.paintStrokeTop.setStrokeWidth(props.strokeWidthTop);
            this.paintStrokeBottom.setStrokeWidth(this.boundProps.strokeWidthBottom);
        }
        this.outlineRect.set(0, 0, this.boundProps.boundsWithPadding.width(), this.boundProps.boundsWithPadding.height());
        BlurredBackgroundDrawable.getOutline(this.outline, this.outlineRect, this.boundProps.radii);
        this.outline.setAlpha(1.0f);
        if (this.boundProps.boundsWithPadding.isEmpty()) {
            return;
        }
        this.renderNodeFill.setPosition(0, 0, this.boundProps.boundsWithPadding.width(), this.boundProps.boundsWithPadding.height());
        this.renderNodeStroke.setPosition(0, 0, this.boundProps.boundsWithPadding.width(), this.boundProps.boundsWithPadding.height());
        this.renderNode.setPosition(0, 0, this.boundProps.boundsWithPadding.width(), this.boundProps.boundsWithPadding.height());
        this.renderNode.setOutline(this.outline);
        this.renderNodeInvalidated = true;
    }

    @Override // org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawable
    protected void onSourceOffsetChange(float f, float f2) {
        super.onSourceOffsetChange(f, f2);
        this.renderNodeInvalidated = true;
    }

    public boolean hasDisplayList() {
        return this.renderNode.hasDisplayList();
    }

    private void updateDisplayList() {
        float f = this.sourceOffsetX;
        float f2 = this.sourceOffsetY;
        RecordingCanvas recordingCanvasBeginRecording = this.renderNodeFill.beginRecording();
        recordingCanvasBeginRecording.save();
        Rect rect = this.boundProps.boundsWithPadding;
        recordingCanvasBeginRecording.translate(-(rect.left + f), -(rect.top + f2));
        LiquidGlassEffect liquidGlassEffect = this.liquidGlassEffect;
        if (liquidGlassEffect != null && Build.VERSION.SDK_INT >= 33) {
            float fWidth = this.boundProps.boundsWithPadding.width();
            float fHeight = this.boundProps.boundsWithPadding.height();
            BlurredBackgroundDrawable.Props props = this.boundProps;
            float[] fArr = props.shaderRadii;
            float f3 = fArr[0];
            float f4 = fArr[2];
            float f5 = fArr[4];
            float f6 = fArr[6];
            int iM1146dp = props.liquidThickness;
            if (iM1146dp <= 0) {
                iM1146dp = AndroidUtilities.m1146dp(11.0f);
            }
            float f7 = iM1146dp;
            BlurredBackgroundDrawable.Props props2 = this.boundProps;
            liquidGlassEffect.update(0.0f, 0.0f, fWidth, fHeight, f3, f4, f5, f6, f7, props2.liquidIntensity, props2.liquidIndex, this.backgroundColor);
        }
        BlurredBackgroundSourceRenderNode blurredBackgroundSourceRenderNode = this.source;
        Rect rect2 = this.boundProps.boundsWithPadding;
        blurredBackgroundSourceRenderNode.draw(recordingCanvasBeginRecording, rect2.left + f, rect2.top + f2, rect2.right + f, rect2.bottom + f2);
        recordingCanvasBeginRecording.restore();
        this.renderNodeFill.endRecording();
        boolean z = true;
        if (!this.boundProps.useFullStroke ? !(this.strokeColorTop != 0 || this.strokeColorBottom != 0) : this.strokeColorFull == 0) {
            z = false;
        }
        if (z) {
            RecordingCanvas recordingCanvasBeginRecording2 = this.renderNodeStroke.beginRecording();
            BlurredBackgroundDrawable.Props props3 = this.boundProps;
            if (props3.useFullStroke) {
                if (props3.radiiAreSame) {
                    float f8 = props3.strokeWidthFull / 2.0f;
                    float f9 = this.boundProps.radii[0];
                    recordingCanvasBeginRecording2.drawRoundRect(f8, f8, props3.boundsWithPadding.width() - f8, this.boundProps.boundsWithPadding.height() - f8, f9, f9, this.paintStrokeTop);
                } else {
                    recordingCanvasBeginRecording2.drawPath(props3.strokePathTop, this.paintStrokeTop);
                }
            } else {
                if (this.strokeColorTop != 0) {
                    float fWidth2 = props3.boundsWithPadding.width();
                    float fHeight2 = this.boundProps.boundsWithPadding.height();
                    BlurredBackgroundDrawable.Props props4 = this.boundProps;
                    BlurredBackgroundDrawable.drawStroke((Canvas) recordingCanvasBeginRecording2, 0.0f, 0.0f, fWidth2, fHeight2, props4.radii, props4.strokeWidthTop, true, this.paintStrokeTop);
                }
                if (this.strokeColorBottom != 0) {
                    float fWidth3 = this.boundProps.boundsWithPadding.width();
                    float fHeight3 = this.boundProps.boundsWithPadding.height();
                    BlurredBackgroundDrawable.Props props5 = this.boundProps;
                    BlurredBackgroundDrawable.drawStroke((Canvas) recordingCanvasBeginRecording2, 0.0f, 0.0f, fWidth3, fHeight3, props5.radii, props5.strokeWidthBottom, false, this.paintStrokeBottom);
                }
            }
            this.renderNodeStroke.endRecording();
        }
        RecordingCanvas recordingCanvasBeginRecording3 = this.renderNode.beginRecording();
        recordingCanvasBeginRecording3.drawRenderNode(this.renderNodeFill);
        if (this.liquidGlassEffect == null && Color.alpha(this.backgroundColor) != 0) {
            recordingCanvasBeginRecording3.drawColor(this.backgroundColor);
        }
        if (z) {
            recordingCanvasBeginRecording3.drawRenderNode(this.renderNodeStroke);
        }
        this.renderNode.endRecording();
    }

    @Override // org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawable
    public void updateColors() {
        super.updateColors();
        this.paintShadow.setShadowLayer(AndroidUtilities.dpf2(1.0f), 0.0f, AndroidUtilities.dpf2(0.33333334f), this.shadowColor);
        if (this.boundProps.useFullStroke) {
            this.paintStrokeTop.setColor(this.strokeColorFull);
        } else {
            this.paintStrokeTop.setColor(this.strokeColorTop);
            this.paintStrokeBottom.setColor(this.strokeColorBottom);
        }
        this.renderNodeInvalidated = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.boundProps.boundsWithPadding.isEmpty()) {
            return;
        }
        if (!canvas.isHardwareAccelerated()) {
            drawSource(canvas, this.source);
            return;
        }
        if (!this.renderNode.hasDisplayList()) {
            this.source.dispatchOnDrawablesRelativePositionChange();
            updateDisplayList();
        } else if (this.renderNodeInvalidated) {
            updateDisplayList();
        }
        this.renderNodeInvalidated = false;
        int iMultAlpha = Theme.multAlpha(this.shadowColor, this.renderNode.getAlpha());
        if (Color.alpha(iMultAlpha) != 0) {
            this.paintShadow.setShadowLayer(AndroidUtilities.dpf2(1.0f), 0.0f, AndroidUtilities.dpf2(0.33333334f), iMultAlpha);
            this.boundProps.drawShadows(canvas, this.paintShadow, this.inAppKeyboardOptimization);
        }
        canvas.save();
        Rect rect = this.boundProps.boundsWithPadding;
        canvas.translate(rect.left, rect.top);
        canvas.drawRenderNode(this.renderNode);
        canvas.restore();
    }

    @Override // org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawable, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        int alpha = getAlpha();
        super.setAlpha(i);
        this.renderNode.setAlpha(i / 255.0f);
        this.renderNodeInvalidated = true;
        if (alpha != 0 || i <= 0) {
            return;
        }
        this.source.dispatchOnDrawablesRelativePositionChange();
    }

    @Override // org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawable
    protected void onSourceRelativePositionChanged(RectF rectF) {
        super.onSourceRelativePositionChanged(rectF);
        this.source.dispatchOnDrawablesRelativePositionChange();
    }
}
