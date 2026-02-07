package org.telegram.p023ui.Components.Paint.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.OnBackPressedDispatcher$$ExternalSyntheticNonNull0;
import java.util.Iterator;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.Components.AnimatedEmojiSpan;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.Paint.PaintTypeface;
import org.telegram.p023ui.Components.Paint.Swatch;
import org.telegram.p023ui.Components.Paint.Views.EntityView;
import org.telegram.p023ui.Components.Point;
import org.telegram.p023ui.Components.Rect;

/* loaded from: classes6.dex */
public class TextPaintView extends EntityView {
    private int align;
    private int baseFontSize;
    private int currentType;
    private boolean disableAutoresize;
    private EditTextOutline editText;
    private String lastTypefaceKey;
    private int maxFontSize;
    private int minFontSize;
    private Runnable onFontChange;
    private Swatch swatch;
    private PaintTypeface typeface;

    public TextPaintView(Context context, Point point, int i, CharSequence charSequence, Swatch swatch, int i2) {
        super(context, point);
        this.typeface = PaintTypeface.ROBOTO_MEDIUM;
        this.baseFontSize = i;
        EditTextOutline editTextOutline = new EditTextOutline(context) { // from class: org.telegram.ui.Components.Paint.Views.TextPaintView.1
            @Override // org.telegram.p023ui.Components.EditTextEffects, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                EntityView.SelectionView selectionView = TextPaintView.this.selectionView;
                if (selectionView == null || selectionView.getVisibility() != 0) {
                    return false;
                }
                return super.dispatchTouchEvent(motionEvent);
            }

            @Override // org.telegram.p023ui.Components.EditTextEffects, android.widget.TextView, android.view.View
            protected void onLayout(boolean z, int i3, int i4, int i5, int i6) {
                super.onLayout(z, i3, i4, i5, i6);
                TextPaintView.this.updateSelectionView();
            }

            @Override // org.telegram.p023ui.Components.EditTextBoldCursor, android.widget.TextView, android.view.View
            protected void onMeasure(int i3, int i4) {
                super.onMeasure(i3, i4);
                TextPaintView.this.updateSelectionView();
            }
        };
        this.editText = editTextOutline;
        NotificationCenter.listenEmojiLoading(editTextOutline);
        this.editText.setGravity(19);
        this.editText.setBackgroundColor(0);
        this.editText.setPadding(AndroidUtilities.m1146dp(7.0f), AndroidUtilities.m1146dp(7.0f), AndroidUtilities.m1146dp(7.0f), AndroidUtilities.m1146dp(7.0f));
        this.editText.setClickable(false);
        this.editText.setEnabled(false);
        this.editText.setCursorColor(-1);
        this.editText.setTextSize(0, this.baseFontSize);
        this.editText.setCursorSize(AndroidUtilities.m1146dp(this.baseFontSize * 0.4f));
        this.editText.setText(charSequence);
        updateHint();
        this.editText.setTextColor(swatch.color);
        this.editText.setTypeface(null, 1);
        this.editText.setHorizontallyScrolling(false);
        int i3 = Build.VERSION.SDK_INT;
        if (i3 >= 26) {
            this.editText.setImeOptions(285212672);
        } else {
            this.editText.setImeOptions(268435456);
        }
        this.editText.setFocusableInTouchMode(true);
        this.editText.setInputType(16384);
        this.editText.setSingleLine(false);
        addView(this.editText, LayoutHelper.createFrame(-2, -2, 51));
        if (i3 >= 29 || i3 >= 23) {
            this.editText.setBreakStrategy(0);
        }
        setSwatch(swatch);
        setType(i2);
        updatePosition();
        this.editText.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Components.Paint.Views.TextPaintView.2
            boolean pasted;

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence2, int i4, int i5, int i6) {
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence2, int i4, int i5, int i6) {
                this.pasted = i6 > 3;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                int iClamp;
                if (this.pasted && TextPaintView.this.minFontSize > 0 && TextPaintView.this.maxFontSize > 0 && !TextPaintView.this.disableAutoresize && TextPaintView.this.editText.getLayout() != null) {
                    float f = AndroidUtilities.displaySize.y / 3.0f;
                    float height = TextPaintView.this.editText.getLayout().getHeight();
                    if (height > f && (iClamp = Utilities.clamp((int) ((f / height) * TextPaintView.this.getBaseFontSize()), TextPaintView.this.maxFontSize, TextPaintView.this.minFontSize)) != TextPaintView.this.getBaseFontSize()) {
                        TextPaintView.this.setBaseFontSize(iClamp);
                        if (TextPaintView.this.onFontChange != null) {
                            TextPaintView.this.onFontChange.run();
                        }
                    }
                }
                TextPaintView.this.updateHint();
            }
        });
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected float getStickyPaddingLeft() {
        RectF rectF = this.editText.framePadding;
        if (rectF == null) {
            return 0.0f;
        }
        return rectF.left;
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected float getStickyPaddingRight() {
        RectF rectF = this.editText.framePadding;
        if (rectF == null) {
            return 0.0f;
        }
        return rectF.right;
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected float getStickyPaddingTop() {
        RectF rectF = this.editText.framePadding;
        if (rectF == null) {
            return 0.0f;
        }
        return rectF.top;
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    protected float getStickyPaddingBottom() {
        RectF rectF = this.editText.framePadding;
        if (rectF == null) {
            return 0.0f;
        }
        return rectF.bottom;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHint() {
        if (this.editText.getText().length() <= 0) {
            this.editText.setHint(LocaleController.getString(C2369R.string.TextPlaceholder));
            this.editText.setHintTextColor(1627389951);
        } else {
            this.editText.setHint((CharSequence) null);
        }
    }

    public TextPaintView(Context context, TextPaintView textPaintView, Point point) {
        this(context, point, textPaintView.baseFontSize, textPaintView.getText(), textPaintView.getSwatch(), textPaintView.currentType);
        setRotation(textPaintView.getRotation());
        setScale(textPaintView.getScale());
        setTypeface(textPaintView.getTypeface());
        setAlign(textPaintView.getAlign());
        int align = getAlign();
        int i = 2;
        this.editText.setGravity(align != 1 ? align != 2 ? 19 : 21 : 17);
        int align2 = getAlign();
        if (align2 == 1) {
            i = 4;
        } else if (align2 == 2 ? !LocaleController.isRTL : LocaleController.isRTL) {
            i = 3;
        }
        this.editText.setTextAlignment(i);
    }

    public int getBaseFontSize() {
        return this.baseFontSize;
    }

    public void setMinMaxFontSize(int i, int i2, Runnable runnable) {
        this.minFontSize = i;
        this.maxFontSize = i2;
        this.onFontChange = runnable;
    }

    public void disableAutoresize(boolean z) {
        this.disableAutoresize = z;
    }

    public void setBaseFontSize(int i) {
        this.baseFontSize = i;
        float f = i;
        this.editText.setTextSize(0, f);
        this.editText.setCursorSize(AndroidUtilities.m1146dp(f * 0.4f));
        if (OnBackPressedDispatcher$$ExternalSyntheticNonNull0.m1m(this.editText.getText())) {
            Editable text = this.editText.getText();
            Emoji.EmojiSpan[] emojiSpanArr = (Emoji.EmojiSpan[]) text.getSpans(0, text.length(), Emoji.EmojiSpan.class);
            for (int i2 = 0; i2 < emojiSpanArr.length; i2++) {
                emojiSpanArr[i2].replaceFontMetrics(getFontMetricsInt());
                emojiSpanArr[i2].scale = 0.85f;
            }
            for (AnimatedEmojiSpan animatedEmojiSpan : (AnimatedEmojiSpan[]) text.getSpans(0, text.length(), AnimatedEmojiSpan.class)) {
                animatedEmojiSpan.replaceFontMetrics(getFontMetricsInt());
            }
            this.editText.invalidateForce();
        }
    }

    public void setAlign(int i) {
        this.align = i;
    }

    public int getAlign() {
        return this.align;
    }

    public void setTypeface(PaintTypeface paintTypeface) {
        this.typeface = paintTypeface;
        if (paintTypeface != null) {
            this.editText.setTypeface(paintTypeface.getTypeface());
        }
        updateSelectionView();
    }

    public void setTypeface(String str) {
        Iterator it = PaintTypeface.get().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            PaintTypeface paintTypeface = (PaintTypeface) it.next();
            if (paintTypeface.getKey().equals(str)) {
                setTypeface(paintTypeface);
                str = null;
                break;
            }
        }
        this.lastTypefaceKey = str;
        updateSelectionView();
    }

    public void updateTypeface() {
        String str = this.lastTypefaceKey;
        if (str != null) {
            setTypeface(str);
        }
    }

    public PaintTypeface getTypeface() {
        return this.typeface;
    }

    public EditTextOutline getEditText() {
        return this.editText;
    }

    public void setMaxWidth(int i) {
        this.editText.setMaxWidth(i);
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

    public CharSequence getText() {
        return this.editText.getText();
    }

    public void setText(CharSequence charSequence) {
        this.editText.setText(charSequence);
        updateHint();
    }

    public Paint.FontMetricsInt getFontMetricsInt() {
        return this.editText.getPaint().getFontMetricsInt();
    }

    public float getFontSize() {
        return this.editText.getTextSize();
    }

    public View getFocusedView() {
        return this.editText;
    }

    public void beginEditing() {
        this.editText.setEnabled(true);
        this.editText.setClickable(true);
        this.editText.requestFocus();
        EditTextOutline editTextOutline = this.editText;
        editTextOutline.setSelection(editTextOutline.getText().length());
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.Paint.Views.TextPaintView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$beginEditing$0();
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$beginEditing$0() {
        AndroidUtilities.showKeyboard(this.editText);
    }

    public void endEditing() {
        this.editText.clearFocus();
        this.editText.setEnabled(false);
        this.editText.setClickable(false);
        updateSelectionView();
    }

    public Swatch getSwatch() {
        return this.swatch;
    }

    public int getTextSize() {
        return (int) this.editText.getTextSize();
    }

    public void setSwatch(Swatch swatch) {
        this.swatch = swatch.clone();
        updateColor();
    }

    public void setType(int i) {
        this.currentType = i;
        updateColor();
    }

    public int getType() {
        return this.currentType;
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateColor() {
        /*
            r9 = this;
            org.telegram.ui.Components.Paint.Views.EditTextOutline r0 = r9.editText
            r1 = 0
            r2 = 0
            r0.setShadowLayer(r1, r1, r1, r2)
            org.telegram.ui.Components.Paint.Swatch r0 = r9.swatch
            int r0 = r0.color
            int r3 = r9.currentType
            r4 = 1060672373(0x3f389375, float:0.721)
            r5 = -1
            r6 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            if (r3 != 0) goto L34
            org.telegram.ui.Components.Paint.Views.EditTextOutline r0 = r9.editText
            r0.setStrokeColor(r2)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r0 = r9.editText
            org.telegram.ui.Components.Paint.Swatch r1 = r9.swatch
            int r1 = r1.color
            r0.setFrameColor(r1)
            org.telegram.ui.Components.Paint.Swatch r0 = r9.swatch
            int r0 = r0.color
            float r0 = org.telegram.messenger.AndroidUtilities.computePerceivedBrightness(r0)
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 < 0) goto L32
        L2f:
            r0 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            goto L8e
        L32:
            r0 = -1
            goto L8e
        L34:
            r7 = 1
            r8 = 1048576000(0x3e800000, float:0.25)
            if (r3 != r7) goto L4d
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            float r2 = org.telegram.messenger.AndroidUtilities.computePerceivedBrightness(r0)
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 < 0) goto L46
            r2 = -1728053248(0xffffffff99000000, float:-6.617445E-24)
            goto L49
        L46:
            r2 = -1711276033(0xffffffff99ffffff, float:-2.6469778E-23)
        L49:
            r1.setFrameColor(r2)
            goto L8e
        L4d:
            r7 = 2
            if (r3 != r7) goto L60
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            float r2 = org.telegram.messenger.AndroidUtilities.computePerceivedBrightness(r0)
            int r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1))
            if (r2 < 0) goto L5c
            r5 = -16777216(0xffffffffff000000, float:-1.7014118E38)
        L5c:
            r1.setFrameColor(r5)
            goto L8e
        L60:
            r7 = 3
            if (r3 != r7) goto L6e
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            r1.setStrokeColor(r2)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            r1.setFrameColor(r2)
            goto L8e
        L6e:
            org.telegram.ui.Components.Paint.Views.EditTextOutline r0 = r9.editText
            r0.setFrameColor(r2)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r0 = r9.editText
            org.telegram.ui.Components.Paint.Swatch r3 = r9.swatch
            int r3 = r3.color
            r0.setStrokeColor(r3)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r0 = r9.editText
            r0.setShadowLayer(r1, r1, r1, r2)
            org.telegram.ui.Components.Paint.Swatch r0 = r9.swatch
            int r0 = r0.color
            float r0 = org.telegram.messenger.AndroidUtilities.computePerceivedBrightness(r0)
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 < 0) goto L32
            goto L2f
        L8e:
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            r1.setTextColor(r0)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            r1.setCursorColor(r0)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            r1.setHandlesColor(r0)
            org.telegram.ui.Components.Paint.Views.EditTextOutline r1 = r9.editText
            r2 = 1053609165(0x3ecccccd, float:0.4)
            int r0 = org.telegram.p023ui.ActionBar.Theme.multAlpha(r0, r2)
            r1.setHighlightColor(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.Paint.Views.TextPaintView.updateColor():void");
    }

    @Override // org.telegram.p023ui.Components.Paint.Views.EntityView
    public Rect getSelectionBounds() {
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup == null) {
            return new Rect();
        }
        float scaleX = viewGroup.getScaleX();
        float measuredWidth = (getMeasuredWidth() * getScale()) + (AndroidUtilities.m1146dp(64.0f) / scaleX);
        float measuredHeight = (getMeasuredHeight() * getScale()) + (AndroidUtilities.m1146dp(52.0f) / scaleX);
        float positionX = (getPositionX() - (measuredWidth / 2.0f)) * scaleX;
        return new Rect(positionX, (getPositionY() - (((measuredHeight - this.editText.getExtendedPaddingTop()) - AndroidUtilities.dpf2(4.0f)) / 2.0f)) * scaleX, ((measuredWidth * scaleX) + positionX) - positionX, (measuredHeight - this.editText.getExtendedPaddingBottom()) * scaleX);
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
