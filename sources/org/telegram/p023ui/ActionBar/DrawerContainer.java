package org.telegram.p023ui.ActionBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import org.telegram.messenger.AndroidUtilities;

/* loaded from: classes3.dex */
public class DrawerContainer extends FrameLayout {
    private int backgroundColor;
    private final Paint backgroundPaint;
    private int navbarInset;

    public DrawerContainer(Context context) {
        super(context);
        this.backgroundPaint = new Paint(1);
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() { // from class: org.telegram.ui.ActionBar.DrawerContainer$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return this.f$0.onApplyWindowInsets(view, windowInsetsCompat);
            }
        });
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        setMeasuredDimension(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
        int childCount = getChildCount();
        RecyclerView recyclerView = null;
        int i5 = 0;
        int measuredHeight = 0;
        while (i5 < childCount) {
            View childAt = getChildAt(i5);
            if (childAt instanceof RecyclerView) {
                i3 = i;
                i4 = i2;
                recyclerView = (RecyclerView) childAt;
            } else {
                ((FrameLayout.LayoutParams) childAt.getLayoutParams()).bottomMargin = this.navbarInset;
                i3 = i;
                i4 = i2;
                measureChildWithMargins(childAt, i3, 0, i4, 0);
                measuredHeight += childAt.getMeasuredHeight();
            }
            i5++;
            i = i3;
            i2 = i4;
        }
        int i6 = i;
        int i7 = i2;
        if (recyclerView != null) {
            if (measuredHeight == 0) {
                recyclerView.setPadding(0, 0, 0, this.navbarInset);
            } else {
                recyclerView.setPadding(0, 0, 0, 0);
                ((FrameLayout.LayoutParams) recyclerView.getLayoutParams()).bottomMargin = measuredHeight + this.navbarInset;
            }
            measureChildWithMargins(recyclerView, i6, 0, i7, 0);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.backgroundPaint.getAlpha() > 0) {
            canvas.drawRect(0.0f, getMeasuredHeight() - this.navbarInset, getMeasuredWidth(), getMeasuredHeight(), this.backgroundPaint);
        }
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        super.setBackgroundColor(i);
        this.backgroundColor = i;
        checkBackgroundColorPaint();
    }

    private void checkBackgroundColorPaint() {
        float navigationBarThirdButtonsFactor = AndroidUtilities.getNavigationBarThirdButtonsFactor(this.navbarInset);
        this.backgroundPaint.setColor(Theme.multAlpha(ColorUtils.compositeColors(536870912, this.backgroundColor), AndroidUtilities.lerp(0.0f, 0.75f, navigationBarThirdButtonsFactor)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        int i = windowInsetsCompat.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom;
        if (this.navbarInset != i) {
            this.navbarInset = i;
            checkBackgroundColorPaint();
            requestLayout();
        }
        return WindowInsetsCompat.CONSUMED;
    }
}
