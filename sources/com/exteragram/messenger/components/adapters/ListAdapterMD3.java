package com.exteragram.messenger.components.adapters;

import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.RecyclerListView;

/* loaded from: classes.dex */
public abstract class ListAdapterMD3 extends RecyclerListView.SelectionAdapter implements ListAdapterMD3Mixin {
    private final ListAdapterMD3Impl impl = new ListAdapterMD3Impl() { // from class: com.exteragram.messenger.components.adapters.ListAdapterMD3.1
        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public Theme.ResourcesProvider getResourcesProvider() {
            return ListAdapterMD3.this.getResourcesProvider();
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean shouldIgnoreBackground(int i) {
            return ListAdapterMD3.this.shouldIgnoreBackground(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean shouldApplyBackground(int i) {
            return ListAdapterMD3.this.shouldApplyBackground(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public int getItemViewType(int i) {
            return ListAdapterMD3.this.getItemViewType(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public int getItemCount() {
            return ListAdapterMD3.this.getItemCount();
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean isFullscreen(int i) {
            return ListAdapterMD3.this.isFullscreen(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean isFullWidth(int i) {
            return ListAdapterMD3.this.isFullWidth(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean isIgnoreLayoutParams(int i) {
            return ListAdapterMD3.this.isIgnoreLayoutParams(i);
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean canUseMD3() {
            return ListAdapterMD3.this.canUseMD3();
        }

        @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Impl
        public boolean isHeader(int i) {
            return ListAdapterMD3.this.isHeader(i);
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
}
