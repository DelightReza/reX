package com.exteragram.messenger.preferences.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AnimatedTextView;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.SeekBarView;
import org.telegram.tgnet.TLObject;
import p017j$.util.Objects;

/* loaded from: classes.dex */
public class AltSeekbar extends FrameLayout {
    private float currentValue;
    private final AnimatedTextView headerValue;
    private final TextView leftTextView;
    private final int max;
    private final int min;
    private final TextView rightTextView;
    private int roundedValue;
    public SeekBarView seekBarView;
    private int vibro;

    /* loaded from: classes3.dex */
    public interface OnDrag {
        void run(float f);
    }

    public AltSeekbar(Context context, final OnDrag onDrag, final int i, final int i2, String str, String str2, String str3) {
        super(context);
        this.vibro = -1;
        this.max = i2;
        this.min = i;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(LocaleController.isRTL ? 5 : 3);
        TextView textView = new TextView(context);
        textView.setTextSize(1, 15.0f);
        textView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        int i3 = Theme.key_windowBackgroundWhiteBlueHeader;
        textView.setTextColor(Theme.getColor(i3));
        textView.setGravity(LocaleController.isRTL ? 5 : 3);
        textView.setText(str);
        linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, 16));
        AnimatedTextView animatedTextView = new AnimatedTextView(context, false, true, true) { // from class: com.exteragram.messenger.preferences.components.AltSeekbar.1
            final Drawable backgroundDrawable = Theme.createRoundRectDrawable(AndroidUtilities.m1146dp(4.0f), Theme.multAlpha(Theme.getColor(Theme.key_windowBackgroundWhiteBlueHeader), 0.15f));

            @Override // org.telegram.p023ui.Components.AnimatedTextView, android.view.View
            protected void onDraw(Canvas canvas) {
                this.backgroundDrawable.setBounds(0, 0, (int) (getPaddingLeft() + getDrawable().getCurrentWidth() + getPaddingRight()), getMeasuredHeight());
                this.backgroundDrawable.draw(canvas);
                super.onDraw(canvas);
            }
        };
        this.headerValue = animatedTextView;
        animatedTextView.setAnimationProperties(0.45f, 0L, 240L, CubicBezierInterpolator.EASE_OUT_QUINT);
        animatedTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM));
        animatedTextView.setPadding(AndroidUtilities.m1146dp(5.33f), AndroidUtilities.m1146dp(2.0f), AndroidUtilities.m1146dp(5.33f), AndroidUtilities.m1146dp(2.0f));
        animatedTextView.setTextSize(AndroidUtilities.m1146dp(12.0f));
        animatedTextView.setTextColor(Theme.getColor(i3));
        linearLayout.addView(animatedTextView, LayoutHelper.createLinear(-2, 17, 16, 6, 1, 0, 0));
        addView(linearLayout, LayoutHelper.createFrame(-1, -2.0f, 55, 21.0f, 17.0f, 21.0f, 0.0f));
        SeekBarView seekBarView = new SeekBarView(context, true, null);
        this.seekBarView = seekBarView;
        seekBarView.setReportChanges(true);
        this.seekBarView.setDelegate(new SeekBarView.SeekBarViewDelegate() { // from class: com.exteragram.messenger.preferences.components.AltSeekbar$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public /* synthetic */ CharSequence getContentDescription() {
                return SeekBarView.SeekBarViewDelegate.CC.$default$getContentDescription(this);
            }

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public /* synthetic */ int getStepsCount() {
                return SeekBarView.SeekBarViewDelegate.CC.$default$getStepsCount(this);
            }

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public /* synthetic */ boolean needVisuallyDivideSteps() {
                return SeekBarView.SeekBarViewDelegate.CC.$default$needVisuallyDivideSteps(this);
            }

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public final void onSeekBarDrag(boolean z, float f) {
                this.f$0.lambda$new$0(i, i2, onDrag, z, f);
            }

            @Override // org.telegram.ui.Components.SeekBarView.SeekBarViewDelegate
            public /* synthetic */ void onSeekBarPressed(boolean z) {
                SeekBarView.SeekBarViewDelegate.CC.$default$onSeekBarPressed(this, z);
            }
        });
        addView(this.seekBarView, LayoutHelper.createFrame(-1, 44.0f, 48, 6.0f, 68.0f, 6.0f, 0.0f));
        FrameLayout frameLayout = new FrameLayout(context);
        TextView textView2 = new TextView(context);
        this.leftTextView = textView2;
        textView2.setTextSize(1, 13.0f);
        int i4 = Theme.key_windowBackgroundWhiteGrayText;
        textView2.setTextColor(Theme.getColor(i4));
        textView2.setGravity(3);
        textView2.setText(str2);
        frameLayout.addView(textView2, LayoutHelper.createFrame(-2, -2, 19));
        TextView textView3 = new TextView(context);
        this.rightTextView = textView3;
        textView3.setTextSize(1, 13.0f);
        textView3.setTextColor(Theme.getColor(i4));
        textView3.setGravity(5);
        textView3.setText(str3);
        frameLayout.addView(textView3, LayoutHelper.createFrame(-2, -2, 21));
        addView(frameLayout, LayoutHelper.createFrame(-1, -2.0f, 55, 21.0f, 52.0f, 21.0f, 0.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(int i, int i2, OnDrag onDrag, boolean z, float f) {
        float f2 = i + ((i2 - i) * f);
        onDrag.run(f2);
        if (Math.round(f2) != this.roundedValue) {
            setProgress(f);
        }
    }

    private void updateValues() {
        int i = this.max;
        int i2 = this.min;
        float f = this.currentValue;
        float f2 = (((i - i2) / 2) + i2) * 1.5f;
        if (f >= f2 - (i2 * 0.5f)) {
            TextView textView = this.rightTextView;
            int i3 = Theme.key_windowBackgroundWhiteGrayText;
            int color = Theme.getColor(i3);
            int color2 = Theme.getColor(Theme.key_windowBackgroundWhiteBlueText);
            float f3 = this.currentValue;
            int i4 = this.min;
            textView.setTextColor(ColorUtils.blendARGB(color, color2, (f3 - (f2 - (i4 * 0.5f))) / (this.max - (f2 - (i4 * 0.5f)))));
            this.leftTextView.setTextColor(Theme.getColor(i3));
            return;
        }
        if (f <= (i2 + r0) * 0.5f) {
            TextView textView2 = this.leftTextView;
            int i5 = Theme.key_windowBackgroundWhiteGrayText;
            textView2.setTextColor(ColorUtils.blendARGB(Theme.getColor(i5), Theme.getColor(Theme.key_windowBackgroundWhiteBlueText), (this.currentValue - ((r0 + r7) * 0.5f)) / (this.min - ((r0 + r7) * 0.5f))));
            this.rightTextView.setTextColor(Theme.getColor(i5));
            return;
        }
        TextView textView3 = this.leftTextView;
        int i6 = Theme.key_windowBackgroundWhiteGrayText;
        textView3.setTextColor(Theme.getColor(i6));
        this.rightTextView.setTextColor(Theme.getColor(i6));
    }

    public void setProgress(float f) {
        float f2 = this.min + ((this.max - r0) * f);
        this.currentValue = f2;
        this.roundedValue = Math.round(f2);
        this.seekBarView.setProgress(f);
        this.headerValue.cancelAnimation();
        this.headerValue.setText(getTextForHeader(), true);
        int i = this.roundedValue;
        int i2 = this.min;
        if ((i == i2 || i == this.max) && i != this.vibro) {
            this.vibro = (int) this.currentValue;
            performHapticFeedback(4, 2);
        } else if (i > i2 && i < this.max) {
            this.vibro = -1;
        }
        updateValues();
    }

    public CharSequence getTextForHeader() {
        CharSequence charSequenceValueOf;
        int i = this.roundedValue;
        if (i == this.min) {
            charSequenceValueOf = this.leftTextView.getText();
        } else if (i == this.max) {
            charSequenceValueOf = this.rightTextView.getText();
        } else {
            charSequenceValueOf = String.valueOf(i);
        }
        return charSequenceValueOf.toString().toUpperCase();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(112.0f), TLObject.FLAG_30));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AltSeekbar) {
            AltSeekbar altSeekbar = (AltSeekbar) obj;
            if (Objects.equals(this.headerValue, altSeekbar.headerValue) && Objects.equals(this.leftTextView, altSeekbar.leftTextView) && Objects.equals(this.rightTextView, altSeekbar.rightTextView) && Objects.equals(this.seekBarView, altSeekbar.seekBarView) && this.min == altSeekbar.min && this.max == altSeekbar.max && Float.compare(this.currentValue, altSeekbar.currentValue) == 0 && this.roundedValue == altSeekbar.roundedValue && this.vibro == altSeekbar.vibro) {
                return true;
            }
        }
        return false;
    }
}
