package com.exteragram.messenger.components.adapters;

import android.os.Build;
import android.view.KeyEvent;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.utils.p011ui.CanvasUtils;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.p023ui.ActionBar.Theme;

/* loaded from: classes.dex */
public abstract class ListAdapterMD3Impl {
    public abstract boolean canUseMD3();

    public abstract int getItemCount();

    public abstract int getItemViewType(int i);

    public abstract Theme.ResourcesProvider getResourcesProvider();

    public abstract boolean isFullWidth(int i);

    public abstract boolean isFullscreen(int i);

    public abstract boolean isHeader(int i);

    public abstract boolean isIgnoreLayoutParams(int i);

    public abstract boolean shouldApplyBackground(int i);

    public abstract boolean shouldIgnoreBackground(int i);

    protected void updateRow(RecyclerView.ViewHolder viewHolder, int i) {
        KeyEvent.Callback callback = viewHolder.itemView;
        if (callback instanceof Theme.Colorable) {
            ((Theme.Colorable) callback).updateColors();
        }
        if (canUseMD3()) {
            updateRowBackground(viewHolder, i);
            updateRowMargins(viewHolder, i);
        }
    }

    public void updateRowBackground(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            viewHolder.itemView.setForeground(null);
        }
        int itemViewType = getItemViewType(i);
        if (shouldIgnoreBackground(itemViewType)) {
            return;
        }
        if (needSeparatedHeader(itemViewType)) {
            viewHolder.itemView.setBackgroundColor(0);
            return;
        }
        if (!shouldApplyBackground(itemViewType)) {
            viewHolder.itemView.setBackground(null);
            return;
        }
        Theme.ResourcesProvider resourcesProvider = getResourcesProvider();
        int cornersForPosition = getCornersForPosition(i);
        int radius = getRadius(true);
        int smallRadius = getSmallRadius(true);
        int i2 = (cornersForPosition & 1) != 0 ? radius : smallRadius;
        if ((cornersForPosition & 2) == 0) {
            radius = smallRadius;
        }
        viewHolder.itemView.setBackground(Theme.createRoundRectDrawable(i2, radius, CanvasUtils.INSTANCE.getAdaptedSurfaceColor(resourcesProvider)));
        viewHolder.itemView.setClipToOutline(i2 > 0 || radius > 0);
    }

    public void updateRowMargins(RecyclerView.ViewHolder viewHolder, int i) {
        RecyclerView.LayoutParams layoutParams;
        if (viewHolder == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams2 = viewHolder.itemView.getLayoutParams();
        if (layoutParams2 instanceof RecyclerView.LayoutParams) {
            layoutParams = (RecyclerView.LayoutParams) layoutParams2;
        } else {
            layoutParams = new RecyclerView.LayoutParams(-1, -2);
        }
        int itemViewType = getItemViewType(i);
        if (isIgnoreLayoutParams(itemViewType)) {
            return;
        }
        int iM1146dp = AndroidUtilities.m1146dp(12.0f);
        int iM1146dp2 = AndroidUtilities.m1146dp(6.0f);
        boolean zIsFullscreen = isFullscreen(itemViewType);
        boolean zIsFullWidth = isFullWidth(itemViewType);
        boolean zNeedSeparatedHeader = needSeparatedHeader(itemViewType);
        int i2 = (zIsFullscreen || zIsFullWidth || zNeedSeparatedHeader) ? 0 : iM1146dp;
        if (i != 0 || zIsFullscreen || zNeedSeparatedHeader) {
            iM1146dp = 0;
        }
        if (!zNeedSeparatedHeader) {
            iM1146dp2 = (!zIsFullscreen && !zIsFullWidth && ExteraConfig.dividerType == 2 && shouldApplyBackground(itemViewType) && hasBackgroundAt(i + 1)) ? AndroidUtilities.m1146dp(2.0f) : 0;
        }
        if (((ViewGroup.MarginLayoutParams) layoutParams).leftMargin == i2 && ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin == i2 && ((ViewGroup.MarginLayoutParams) layoutParams).topMargin == iM1146dp && ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin == iM1146dp2) {
            return;
        }
        layoutParams.setMargins(i2, iM1146dp, i2, iM1146dp2);
        viewHolder.itemView.setLayoutParams(layoutParams);
    }

    public int getCornersForPosition(int i) {
        if (!shouldApplyBackground(getItemViewType(i)) || !canUseMD3()) {
            return 4;
        }
        int i2 = !hasBackgroundAt(i + (-1)) ? 1 : 0;
        return !hasBackgroundAt(i + 1) ? i2 | 2 : i2;
    }

    private boolean hasBackgroundAt(int i) {
        if (i >= 0 && i < getItemCount()) {
            int itemViewType = getItemViewType(i);
            if (shouldApplyBackground(itemViewType) && !needSeparatedHeader(itemViewType)) {
                return true;
            }
        }
        return false;
    }

    private boolean needSeparatedHeader(int i) {
        if (isHeader(i)) {
            return ExteraConfig.md3SeparatedHeaders || ExteraConfig.dividerType == 2;
        }
        return false;
    }

    public int getRadius(boolean z) {
        int i = canUseMD3() ? 16 : 0;
        return z ? AndroidUtilities.m1146dp(i) : i;
    }

    public int getSmallRadius(boolean z) {
        int i = (ExteraConfig.dividerType == 2 && canUseMD3()) ? 4 : 0;
        return z ? AndroidUtilities.m1146dp(i) : i;
    }
}
