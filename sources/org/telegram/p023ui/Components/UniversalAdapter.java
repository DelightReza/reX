package org.telegram.p023ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.components.adapters.ListAdapterMD3Diff;
import java.util.ArrayList;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Business.QuickRepliesActivity;
import org.telegram.p023ui.Charts.BaseChartView;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.StatisticActivity;
import org.telegram.tgnet.TLObject;

/* loaded from: classes3.dex */
public class UniversalAdapter extends ListAdapterMD3Diff {
    private boolean allowReorder;
    private boolean applyBackground;
    private BaseChartView.SharedUiComponents chartSharedUI;
    private final int classGuid;
    private final Context context;
    private final int currentAccount;
    private Section currentReorderSection;
    private Section currentWhiteSection;
    private final boolean dialog;
    protected Utilities.Callback2 fillItems;
    private final ArrayList items;
    protected final RecyclerListView listView;
    private final ArrayList oldItems;
    private Utilities.Callback2 onReordered;
    private boolean orderChanged;
    private int orderChangedId;
    private final ArrayList reorderSections;
    private final Theme.ResourcesProvider resourcesProvider;
    private boolean useSectionStyle;
    private final ArrayList whiteSections;

    @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Diff
    public boolean isFullWidth(int i) {
        return i == 100;
    }

    @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Diff
    public boolean isFullscreen(int i) {
        return i == -3 || i == 102;
    }

    @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Diff
    public boolean isHeader(int i) {
        return i == 0 || i == 42 || i == 1;
    }

    @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Diff
    public boolean isIgnoreLayoutParams(int i) {
        return i == 9;
    }

    @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Diff
    public boolean shouldIgnoreBackground(int i) {
        return i == 9;
    }

    public void setUseSectionStyle(boolean z) {
        this.useSectionStyle = z;
    }

    public UniversalAdapter(RecyclerListView recyclerListView, Context context, int i, int i2, Utilities.Callback2 callback2, Theme.ResourcesProvider resourcesProvider) {
        this(recyclerListView, context, i, i2, false, callback2, resourcesProvider);
    }

    public UniversalAdapter(RecyclerListView recyclerListView, Context context, int i, int i2, boolean z, Utilities.Callback2 callback2, Theme.ResourcesProvider resourcesProvider) {
        this.applyBackground = true;
        this.oldItems = new ArrayList();
        this.items = new ArrayList();
        this.useSectionStyle = false;
        this.whiteSections = new ArrayList();
        this.reorderSections = new ArrayList();
        this.listView = recyclerListView;
        this.context = context;
        this.currentAccount = i;
        this.classGuid = i2;
        this.dialog = z;
        this.fillItems = callback2;
        this.resourcesProvider = resourcesProvider;
        update(false);
    }

    public void setApplyBackground(boolean z) {
        this.applyBackground = z;
    }

    private static class Section {
        public int end;
        public int start;

        private Section() {
        }

        public boolean contains(int i) {
            return i >= this.start && i <= this.end;
        }
    }

    public void whiteSectionStart() {
        Section section = new Section();
        this.currentWhiteSection = section;
        section.start = this.items.size();
        Section section2 = this.currentWhiteSection;
        section2.end = -1;
        this.whiteSections.add(section2);
    }

    public void whiteSectionEnd() {
        Section section = this.currentWhiteSection;
        if (section != null) {
            section.end = Math.max(0, this.items.size() - 1);
        }
    }

    public int reorderSectionStart() {
        Section section = new Section();
        this.currentReorderSection = section;
        section.start = this.items.size();
        Section section2 = this.currentReorderSection;
        section2.end = -1;
        this.reorderSections.add(section2);
        return this.reorderSections.size() - 1;
    }

    public void reorderSectionEnd() {
        Section section = this.currentReorderSection;
        if (section != null) {
            section.end = Math.max(0, this.items.size() - 1);
        }
    }

    public boolean isReorderItem(int i) {
        return getReorderSectionId(i) >= 0;
    }

    public int getReorderSectionId(int i) {
        for (int i2 = 0; i2 < this.reorderSections.size(); i2++) {
            if (((Section) this.reorderSections.get(i2)).contains(i)) {
                return i2;
            }
        }
        return -1;
    }

    public void swapElements(int i, int i2) {
        int i3;
        if (this.onReordered == null) {
            return;
        }
        int reorderSectionId = getReorderSectionId(i);
        int reorderSectionId2 = getReorderSectionId(i2);
        if (reorderSectionId < 0 || reorderSectionId != reorderSectionId2) {
            return;
        }
        UItem uItem = (UItem) this.items.get(i);
        UItem uItem2 = (UItem) this.items.get(i2);
        boolean zHasDivider = hasDivider(i);
        boolean zHasDivider2 = hasDivider(i2);
        this.items.set(i, uItem2);
        this.items.set(i2, uItem);
        notifyItemMoved(i, i2);
        if (hasDivider(i2) != zHasDivider) {
            notifyItemChanged(i2, 3);
        }
        if (hasDivider(i) != zHasDivider2) {
            notifyItemChanged(i, 3);
        }
        if (this.orderChanged && (i3 = this.orderChangedId) != reorderSectionId) {
            callReorder(i3);
        }
        this.orderChanged = true;
        this.orderChangedId = reorderSectionId;
    }

    private void callReorder(int i) {
        if (i < 0 || i >= this.reorderSections.size()) {
            return;
        }
        Section section = (Section) this.reorderSections.get(i);
        this.onReordered.run(Integer.valueOf(i), new ArrayList(this.items.subList(section.start, section.end + 1)));
        this.orderChanged = false;
    }

    public void reorderDone() {
        if (this.orderChanged) {
            callReorder(this.orderChangedId);
        }
    }

    public void listenReorder(Utilities.Callback2 callback2) {
        this.onReordered = callback2;
    }

    public void updateReorder(boolean z) {
        this.allowReorder = z;
    }

    public void drawWhiteSections(Canvas canvas, RecyclerListView recyclerListView) {
        for (int i = 0; i < this.whiteSections.size(); i++) {
            Section section = (Section) this.whiteSections.get(i);
            int i2 = section.end;
            if (i2 >= 0) {
                recyclerListView.drawSectionBackground(canvas, section.start, i2, getThemedColor(this.dialog ? Theme.key_dialogBackground : Theme.key_windowBackgroundWhite));
            }
        }
    }

    public void update(final boolean z) {
        this.oldItems.clear();
        this.oldItems.addAll(this.items);
        this.items.clear();
        this.whiteSections.clear();
        this.reorderSections.clear();
        Utilities.Callback2 callback2 = this.fillItems;
        if (callback2 != null) {
            callback2.run(this.items, this);
            RecyclerListView recyclerListView = this.listView;
            if (recyclerListView != null && recyclerListView.isComputingLayout()) {
                this.listView.post(new Runnable() { // from class: org.telegram.ui.Components.UniversalAdapter$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$update$0(z);
                    }
                });
            } else if (z) {
                setItems(this.oldItems, this.items);
            } else {
                notifyDataSetChanged();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$update$0(boolean z) {
        if (this.listView.isComputingLayout()) {
            return;
        }
        if (z) {
            setItems(this.oldItems, this.items);
        } else {
            notifyDataSetChanged();
        }
    }

    public void updateWithoutNotify() {
        this.oldItems.clear();
        this.oldItems.addAll(this.items);
        this.items.clear();
        this.whiteSections.clear();
        this.reorderSections.clear();
        Utilities.Callback2 callback2 = this.fillItems;
        if (callback2 != null) {
            callback2.run(this.items, this);
        }
    }

    @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Diff
    public Theme.ResourcesProvider getResourcesProvider() {
        return this.resourcesProvider;
    }

    @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Diff
    public boolean canUseMD3() {
        return super.canUseMD3() && this.useSectionStyle;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0037 A[FALL_THROUGH, RETURN] */
    @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3Diff
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean shouldApplyBackground(int r4) {
        /*
            r3 = this;
            boolean r0 = r3.applyBackground
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            int r0 = org.telegram.p023ui.Components.UItem.factoryViewTypeStartsWith
            r2 = 1
            if (r4 < r0) goto Lc
            return r2
        Lc:
            r0 = -3
            if (r4 == r0) goto L37
            r0 = 101(0x65, float:1.42E-43)
            if (r4 == r0) goto L37
            r0 = 150(0x96, float:2.1E-43)
            if (r4 == r0) goto L37
            r0 = -1
            if (r4 == r0) goto L37
            if (r4 == 0) goto L37
            if (r4 == r2) goto L37
            r0 = 3
            if (r4 == r0) goto L37
            r0 = 4
            if (r4 == r0) goto L37
            r0 = 5
            if (r4 == r0) goto L37
            r0 = 6
            if (r4 == r0) goto L37
            switch(r4) {
                case 9: goto L37;
                case 10: goto L37;
                case 11: goto L37;
                case 12: goto L37;
                case 13: goto L37;
                case 14: goto L37;
                case 15: goto L37;
                case 16: goto L37;
                case 17: goto L37;
                case 18: goto L37;
                case 19: goto L37;
                case 20: goto L37;
                case 21: goto L37;
                case 22: goto L37;
                case 23: goto L37;
                case 24: goto L37;
                case 25: goto L37;
                default: goto L2d;
            }
        L2d:
            switch(r4) {
                case 27: goto L37;
                case 28: goto L37;
                case 29: goto L37;
                case 30: goto L37;
                default: goto L30;
            }
        L30:
            switch(r4) {
                case 32: goto L37;
                case 33: goto L37;
                case 34: goto L37;
                case 35: goto L37;
                case 36: goto L37;
                case 37: goto L37;
                default: goto L33;
            }
        L33:
            switch(r4) {
                case 39: goto L37;
                case 40: goto L37;
                case 41: goto L37;
                case 42: goto L37;
                default: goto L36;
            }
        L36:
            return r1
        L37:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.UniversalAdapter.shouldApplyBackground(int):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x025b  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(android.view.ViewGroup r13, int r14) {
        /*
            Method dump skipped, instructions count: 746
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.UniversalAdapter.onCreateViewHolder(android.view.ViewGroup, int):androidx.recyclerview.widget.RecyclerView$ViewHolder");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        UItem item = getItem(i);
        if (item == null) {
            return 0;
        }
        return item.viewType;
    }

    private boolean hasDivider(int i) {
        UItem item = getItem(i);
        UItem item2 = getItem(i + 1);
        return (item == null || item2 == null || item.hideDivider || ((ExteraConfig.md3SeparatedHeaders || ExteraConfig.dividerType == 2) && isHeader(item2.viewType)) || isShadow(item2.viewType) != isShadow(item.viewType)) ? false : true;
    }

    public boolean isShadow(int i) {
        if (i < UItem.factoryViewTypeStartsWith) {
            return i == 7 || i == 8 || i == 38 || i == 31 || i == 34;
        }
        UItem.UItemFactory uItemFactoryFindFactory = UItem.findFactory(i);
        return uItemFactoryFindFactory != null && uItemFactoryFindFactory.isShadow();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:124:0x025e  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r24, int r25) {
        /*
            Method dump skipped, instructions count: 2194
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.UniversalAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
    }

    public static /* synthetic */ void $r8$lambda$zkYqYy_dtBSQIc15WzODxdmLimw(UItem uItem, int i) {
        Utilities.Callback callback = uItem.intCallback;
        if (callback != null) {
            callback.run(Integer.valueOf(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ StatisticActivity.BaseChartCell lambda$onBindViewHolder$2(UItem uItem) {
        View viewFindViewByItemObject = findViewByItemObject(uItem.object);
        if (viewFindViewByItemObject instanceof StatisticActivity.UniversalChartCell) {
            return (StatisticActivity.UniversalChartCell) viewFindViewByItemObject;
        }
        return null;
    }

    private View findViewByItemObject(Object obj) {
        int i = 0;
        while (true) {
            if (i >= getItemCount()) {
                i = -1;
                break;
            }
            UItem item = getItem(i);
            if (item != null && item.object == obj) {
                break;
            }
            i++;
        }
        if (i == -1) {
            return null;
        }
        for (int i2 = 0; i2 < this.listView.getChildCount(); i2++) {
            View childAt = this.listView.getChildAt(i2);
            int childAdapterPosition = this.listView.getChildAdapterPosition(childAt);
            if (childAdapterPosition != -1 && childAdapterPosition == i) {
                return childAt;
            }
        }
        return null;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
        updateReorder(viewHolder, this.allowReorder);
        updateColors(viewHolder);
    }

    private void updateColors(RecyclerView.ViewHolder viewHolder) {
        UItem item = getItem(viewHolder.getAdapterPosition());
        if (canUseMD3()) {
            updateRow(viewHolder, viewHolder.getAdapterPosition());
            if (item != null && item.transparent) {
                viewHolder.itemView.setBackground(null);
            }
            if (item == null || item.view == null || item.transparent) {
                return;
            }
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType == -1 || itemViewType == -2) {
                item.view.setBackground(null);
                return;
            }
            return;
        }
        ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
        if (layoutParams instanceof RecyclerView.LayoutParams) {
            RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) layoutParams;
            if (((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin != 0 || ((ViewGroup.MarginLayoutParams) layoutParams2).rightMargin != 0 || ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin != 0 || ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin != 0) {
                layoutParams2.setMargins(0, 0, 0, 0);
                viewHolder.itemView.setLayoutParams(layoutParams2);
            }
        }
        if ((item == null || !item.transparent) ? shouldApplyBackground(viewHolder.getItemViewType()) : false) {
            viewHolder.itemView.setBackgroundColor(getThemedColor(this.dialog ? Theme.key_dialogBackground : Theme.key_windowBackgroundWhite));
        } else {
            viewHolder.itemView.setBackground(null);
        }
        KeyEvent.Callback callback = viewHolder.itemView;
        if (callback instanceof Theme.Colorable) {
            ((Theme.Colorable) callback).updateColors();
        }
    }

    public void updateReorder(RecyclerView.ViewHolder viewHolder, boolean z) {
        if (viewHolder == null) {
            return;
        }
        int itemViewType = viewHolder.getItemViewType();
        if (itemViewType < UItem.factoryViewTypeStartsWith) {
            if (itemViewType != 16) {
                return;
            }
            ((QuickRepliesActivity.QuickReplyView) viewHolder.itemView).setReorder(z);
        } else {
            UItem.UItemFactory uItemFactoryFindFactory = UItem.findFactory(itemViewType);
            if (uItemFactoryFindFactory != null) {
                uItemFactoryFindFactory.attachedView(viewHolder.itemView, getItem(viewHolder.getAdapterPosition()));
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.items.size();
    }

    @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
    public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
        int itemViewType = viewHolder.getItemViewType();
        UItem item = getItem(viewHolder.getAdapterPosition());
        if (itemViewType >= UItem.factoryViewTypeStartsWith) {
            UItem.UItemFactory uItemFactoryFindFactory = UItem.findFactory(itemViewType);
            if (uItemFactoryFindFactory == null || !uItemFactoryFindFactory.isClickable()) {
                return false;
            }
        } else if (itemViewType != 101 && itemViewType != 3 && itemViewType != 5 && itemViewType != 6 && itemViewType != 30 && itemViewType != 4 && itemViewType != 10 && itemViewType != 11 && itemViewType != 12 && itemViewType != 17 && itemViewType != 16 && itemViewType != 29 && itemViewType != 25 && itemViewType != 27 && itemViewType != 32 && itemViewType != 33 && itemViewType != 35 && itemViewType != 36 && itemViewType != 37 && itemViewType != 41 && itemViewType != 39 && itemViewType != 40 && itemViewType != 38 && itemViewType != 150) {
            return false;
        }
        return item == null || item.enabled;
    }

    public UItem getItem(int i) {
        if (i < 0 || i >= this.items.size()) {
            return null;
        }
        return (UItem) this.items.get(i);
    }

    protected int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    /* loaded from: classes6.dex */
    private class FullscreenCustomFrameLayout extends FrameLayout {
        private int minusHeight;

        public FullscreenCustomFrameLayout(Context context) {
            super(context);
            this.minusHeight = 0;
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            if ((getParent() instanceof View) && ((View) getParent()).getMeasuredHeight() > 0) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(((View) getParent()).getMeasuredHeight() - this.minusHeight, TLObject.FLAG_30));
                return;
            }
            if (View.MeasureSpec.getMode(i2) != 0) {
                super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2) - this.minusHeight, TLObject.FLAG_30));
                return;
            }
            int size = View.MeasureSpec.getSize(i2);
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30);
            measureChildren(iMakeMeasureSpec, i2);
            int iMin = 0;
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                iMin = Math.max(iMin, getChildAt(i3).getMeasuredHeight());
            }
            if (size > 0) {
                iMin = Math.min(iMin, size - this.minusHeight);
            }
            super.onMeasure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(iMin, TLObject.FLAG_30));
        }

        public void setMinusHeight(int i) {
            this.minusHeight = i;
        }
    }
}
