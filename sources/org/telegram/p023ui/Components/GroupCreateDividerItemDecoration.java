package org.telegram.p023ui.Components;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.GroupCreateSectionCell;

/* loaded from: classes6.dex */
public class GroupCreateDividerItemDecoration extends RecyclerView.ItemDecoration {
    private boolean searching;
    private boolean single;
    private int skipRows;

    public void setSearching(boolean z) {
        this.searching = z;
    }

    public void setSingle(boolean z) {
        this.single = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        Canvas canvas2;
        int width = recyclerView.getWidth();
        int childCount = recyclerView.getChildCount() - (!this.single ? 1 : 0);
        int i = 0;
        while (i < childCount) {
            View childAt = recyclerView.getChildAt(i);
            View childAt2 = i < childCount + (-1) ? recyclerView.getChildAt(i + 1) : null;
            if (recyclerView.getChildAdapterPosition(childAt) < this.skipRows || (childAt instanceof GroupCreateSectionCell) || (childAt2 instanceof GroupCreateSectionCell)) {
                canvas2 = canvas;
            } else {
                float bottom = childAt.getBottom();
                canvas2 = canvas;
                canvas2.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.m1146dp(72.0f), bottom, width - (LocaleController.isRTL ? AndroidUtilities.m1146dp(72.0f) : 0), bottom, Theme.dividerPaint);
            }
            i++;
            canvas = canvas2;
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view, recyclerView, state);
        rect.top = 1;
    }
}
