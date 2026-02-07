package com.exteragram.messenger.components.adapters;

import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import java.util.ArrayList;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.ListView.AdapterWithDiffUtils;

/* loaded from: classes.dex */
public abstract class ListAdapterMD3Diff extends AdapterWithDiffUtils implements ListAdapterMD3Mixin {
    private final ListAdapterMD3Impl impl = new ListAdapterMD3Impl() { // from class: com.exteragram.messenger.components.adapters.ListAdapterMD3Diff.1
        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public Theme.ResourcesProvider getResourcesProvider() {
            return ListAdapterMD3Diff.this.getResourcesProvider();
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean shouldIgnoreBackground(int i) {
            return ListAdapterMD3Diff.this.shouldIgnoreBackground(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean shouldApplyBackground(int i) {
            return ListAdapterMD3Diff.this.shouldApplyBackground(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public int getItemViewType(int i) {
            return ListAdapterMD3Diff.this.getItemViewType(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public int getItemCount() {
            return ListAdapterMD3Diff.this.getItemCount();
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean isFullscreen(int i) {
            return ListAdapterMD3Diff.this.isFullscreen(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean isFullWidth(int i) {
            return ListAdapterMD3Diff.this.isFullWidth(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean isIgnoreLayoutParams(int i) {
            return ListAdapterMD3Diff.this.isIgnoreLayoutParams(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean canUseMD3() {
            return ListAdapterMD3Diff.this.canUseMD3();
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean isHeader(int i) {
            return ListAdapterMD3Diff.this.isHeader(i);
        }
    };

    public abstract Theme.ResourcesProvider getResourcesProvider();

    public boolean isFullWidth(int i) {
        return false;
    }

    public boolean isFullscreen(int i) {
        return false;
    }

    public abstract boolean isHeader(int i);

    public boolean isIgnoreLayoutParams(int i) {
        return false;
    }

    public abstract boolean shouldApplyBackground(int i);

    public boolean shouldIgnoreBackground(int i) {
        return false;
    }

    public boolean canUseMD3() {
        return ExteraConfig.md3Containers;
    }

    protected void updateRow(RecyclerView.ViewHolder viewHolder, int i) {
        this.impl.updateRow(viewHolder, i);
    }

    @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Mixin
    public int getCornersForPosition(int i) {
        return this.impl.getCornersForPosition(i);
    }

    @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Mixin
    public int getRadius(boolean z) {
        return this.impl.getRadius(z);
    }

    @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Mixin
    public int getSmallRadius(boolean z) {
        return this.impl.getSmallRadius(z);
    }

    @Override // org.telegram.p023ui.Components.ListView.AdapterWithDiffUtils
    protected boolean areVisualsTheSame(int i, int i2, ArrayList arrayList, ArrayList arrayList2) {
        return getVisualState(arrayList, i) == getVisualState(arrayList2, i2);
    }

    private int getVisualState(ArrayList arrayList, int i) {
        int i2;
        int i3 = 0;
        if (i < 0 || i >= arrayList.size()) {
            return 0;
        }
        int i4 = ((AdapterWithDiffUtils.Item) arrayList.get(i)).viewType;
        if (shouldApplyBackground(i4) && canUseMD3()) {
            i2 = !hasBackgroundAt(arrayList, i + (-1)) ? 1 : 0;
            if (!hasBackgroundAt(arrayList, i + 1)) {
                i2 |= 2;
            }
        } else {
            i2 = 4;
        }
        boolean zIsHeader = isHeader(i4);
        if (!isFullscreen(i4) && !isFullWidth(i4) && !zIsHeader) {
            i3 = 8;
        }
        if (i == 0 && !isFullscreen(i4) && !zIsHeader) {
            i3 |= 16;
        }
        if (zIsHeader) {
            i3 |= 32;
        } else if (!isFullscreen(i4) && !isFullWidth(i4) && ExteraConfig.dividerType == 2 && shouldApplyBackground(i4) && hasBackgroundAt(arrayList, i + 1)) {
            i3 |= 64;
        }
        return i2 | i3;
    }

    private boolean hasBackgroundAt(ArrayList arrayList, int i) {
        if (i >= 0 && i < arrayList.size()) {
            int i2 = ((AdapterWithDiffUtils.Item) arrayList.get(i)).viewType;
            boolean z = isHeader(i2) && (ExteraConfig.md3SeparatedHeaders || ExteraConfig.dividerType == 2);
            if (shouldApplyBackground(i2) && !z) {
                return true;
            }
        }
        return false;
    }
}
