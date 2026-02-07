package com.exteragram.messenger.updater;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.exteragram.messenger.plugins.PluginsConstants;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.SharedConfig;
import org.telegram.p023ui.ActionBar.SimpleTextView;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.RadialProgress2;
import org.telegram.p023ui.IUpdateLayout;

/* loaded from: classes.dex */
public class UpdateLayout extends IUpdateLayout {
    private final Activity activity;
    private final ViewGroup sideMenu;
    private final ViewGroup sideMenuContainer;
    private FrameLayout updateLayout;
    private RadialProgress2 updateLayoutIcon;
    private TextView updateSizeTextView;
    private AnimatorSet updateTextAnimator;
    private SimpleTextView[] updateTextViews;

    public UpdateLayout(Activity activity, ViewGroup viewGroup, ViewGroup viewGroup2) {
        super(activity, viewGroup, viewGroup2);
        this.activity = activity;
        this.sideMenu = viewGroup;
        this.sideMenuContainer = viewGroup2;
    }

    @Override // org.telegram.p023ui.IUpdateLayout
    public void updateFileProgress(Object[] objArr) {
        SimpleTextView[] simpleTextViewArr = this.updateTextViews;
        if (simpleTextViewArr == null || objArr == null || simpleTextViewArr[0] == null || !SharedConfig.isAppUpdateAvailable()) {
            return;
        }
        String str = (String) objArr[0];
        String attachFileName = FileLoader.getAttachFileName(SharedConfig.pendingAppUpdate.document);
        if (attachFileName == null || !attachFileName.equals(str)) {
            return;
        }
        float fLongValue = ((Long) objArr[1]).longValue() / ((Long) objArr[2]).longValue();
        this.updateLayoutIcon.setProgress(fLongValue, true);
        this.updateTextViews[0].setText(LocaleController.formatString("AppUpdateDownloading", C2369R.string.AppUpdateDownloading, Integer.valueOf((int) (fLongValue * 100.0f))));
    }

    @Override // org.telegram.p023ui.IUpdateLayout
    public void createUpdateUI(final int i) {
        if (this.sideMenuContainer == null || this.updateLayout != null) {
            return;
        }
        FrameLayout frameLayout = new FrameLayout(this.activity) { // from class: com.exteragram.messenger.updater.UpdateLayout.1
            private int lastGradientWidth;
            private LinearGradient updateGradient;
            private final Paint paint = new Paint();
            private final Matrix matrix = new Matrix();

            @Override // android.view.View
            public void draw(Canvas canvas) {
                Canvas canvas2;
                if (this.updateGradient != null) {
                    this.paint.setColor(-1);
                    this.paint.setShader(this.updateGradient);
                    this.updateGradient.setLocalMatrix(this.matrix);
                    canvas2 = canvas;
                    canvas2.drawRect(0.0f, 0.0f, getMeasuredWidth(), getMeasuredHeight(), this.paint);
                    UpdateLayout.this.updateLayoutIcon.setBackgroundGradientDrawable(this.updateGradient);
                    UpdateLayout.this.updateLayoutIcon.draw(canvas2);
                } else {
                    canvas2 = canvas;
                }
                super.draw(canvas2);
            }

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i2, int i3) {
                super.onMeasure(i2, i3);
                int size = View.MeasureSpec.getSize(i2);
                if (this.lastGradientWidth != size) {
                    int i4 = Theme.key_featuredStickers_addButton;
                    this.updateGradient = new LinearGradient(0.0f, 0.0f, size, 0.0f, new int[]{Theme.getColor(i4), Theme.getColor(i4)}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP);
                    this.lastGradientWidth = size;
                }
            }
        };
        this.updateLayout = frameLayout;
        frameLayout.setWillNotDraw(false);
        this.updateLayout.setVisibility(4);
        this.updateLayout.setTranslationY(AndroidUtilities.m1146dp(44.0f));
        this.updateLayout.setBackground(Theme.getRoundRectSelectorDrawable(AndroidUtilities.m1146dp(0.0f), Theme.getColor(Theme.key_featuredStickers_addButtonPressed)));
        this.sideMenuContainer.addView(this.updateLayout, LayoutHelper.createFrame(-1, 44, 83));
        this.updateLayout.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.updater.UpdateLayout$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createUpdateUI$0(i, view);
            }
        });
        this.updateLayoutIcon = new RadialProgress2(this.updateLayout);
        int color = Theme.getColor(Theme.key_featuredStickers_buttonText);
        this.updateLayoutIcon.setColors(color, color, color, color);
        this.updateLayoutIcon.setProgressRect(AndroidUtilities.m1146dp(22.0f), AndroidUtilities.m1146dp(11.0f), AndroidUtilities.m1146dp(44.0f), AndroidUtilities.m1146dp(33.0f));
        this.updateLayoutIcon.setCircleRadius(AndroidUtilities.m1146dp(11.0f));
        this.updateLayoutIcon.setAsMini();
        this.updateTextViews = new SimpleTextView[2];
        for (int i2 = 0; i2 < 2; i2++) {
            this.updateTextViews[i2] = new SimpleTextView(this.activity);
            this.updateTextViews[i2].setTextSize(15);
            this.updateTextViews[i2].setTypeface(AndroidUtilities.bold());
            this.updateTextViews[i2].setTextColor(color);
            this.updateTextViews[i2].setGravity(3);
            this.updateLayout.addView(this.updateTextViews[i2], LayoutHelper.createFrame(-2, -2.0f, 16, 74.0f, 0.0f, 0.0f, 0.0f));
        }
        this.updateTextViews[0].setText(LocaleController.getString(C2369R.string.AppUpdate));
        this.updateTextViews[1].setAlpha(0.0f);
        this.updateTextViews[1].setVisibility(8);
        TextView textView = new TextView(this.activity);
        this.updateSizeTextView = textView;
        textView.setTextSize(1, 15.0f);
        this.updateSizeTextView.setTypeface(AndroidUtilities.bold());
        this.updateSizeTextView.setGravity(5);
        this.updateSizeTextView.setTextColor(color);
        this.updateLayout.addView(this.updateSizeTextView, LayoutHelper.createFrame(-2, -2.0f, 21, 0.0f, 0.0f, 17.0f, 0.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createUpdateUI$0(int i, View view) {
        if (SharedConfig.isAppUpdateAvailable()) {
            if (this.updateLayoutIcon.getIcon() == 2) {
                FileLoader.getInstance(i).loadFile(SharedConfig.pendingAppUpdate.document, PluginsConstants.UPDATE, 1, 1);
                updateAppUpdateViews(i, true);
            } else if (this.updateLayoutIcon.getIcon() == 3) {
                FileLoader.getInstance(i).cancelLoadFile(SharedConfig.pendingAppUpdate.document);
                updateAppUpdateViews(i, true);
            } else {
                ApplicationLoader.applicationLoaderInstance.openApkInstall(this.activity, SharedConfig.pendingAppUpdate.document);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:50:? A[RETURN, SYNTHETIC] */
    @Override // org.telegram.p023ui.IUpdateLayout
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateAppUpdateViews(int r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 411
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.updater.UpdateLayout.updateAppUpdateViews(int, boolean):void");
    }

    private void setUpdateText(String str, boolean z) {
        if (TextUtils.equals(this.updateTextViews[0].getText(), str)) {
            return;
        }
        AnimatorSet animatorSet = this.updateTextAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.updateTextAnimator = null;
        }
        if (z) {
            SimpleTextView[] simpleTextViewArr = this.updateTextViews;
            simpleTextViewArr[1].setText(simpleTextViewArr[0].getText());
            this.updateTextViews[0].setText(str);
            this.updateTextViews[0].setAlpha(0.0f);
            this.updateTextViews[1].setAlpha(1.0f);
            this.updateTextViews[0].setVisibility(0);
            this.updateTextViews[1].setVisibility(0);
            ArrayList arrayList = new ArrayList();
            SimpleTextView simpleTextView = this.updateTextViews[1];
            Property property = View.ALPHA;
            arrayList.add(ObjectAnimator.ofFloat(simpleTextView, (Property<SimpleTextView, Float>) property, 0.0f));
            arrayList.add(ObjectAnimator.ofFloat(this.updateTextViews[0], (Property<SimpleTextView, Float>) property, 1.0f));
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.updateTextAnimator = animatorSet2;
            animatorSet2.playTogether(arrayList);
            this.updateTextAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.exteragram.messenger.updater.UpdateLayout.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (UpdateLayout.this.updateTextAnimator == animator) {
                        UpdateLayout.this.updateTextViews[1].setVisibility(8);
                        UpdateLayout.this.updateTextAnimator = null;
                    }
                }
            });
            this.updateTextAnimator.setDuration(320L);
            this.updateTextAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
            this.updateTextAnimator.start();
            return;
        }
        this.updateTextViews[0].setText(str);
        this.updateTextViews[0].setAlpha(1.0f);
        this.updateTextViews[0].setVisibility(0);
        this.updateTextViews[1].setVisibility(8);
    }
}
