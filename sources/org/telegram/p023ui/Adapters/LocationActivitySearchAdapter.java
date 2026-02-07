package org.telegram.p023ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.GraySectionCell;
import org.telegram.p023ui.Cells.LocationCell;
import org.telegram.p023ui.Components.FlickerLoadingView;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes5.dex */
public abstract class LocationActivitySearchAdapter extends BaseLocationAdapter {
    private FlickerLoadingView globalGradientView;
    private Context mContext;
    private boolean myLocationDenied;
    private Theme.ResourcesProvider resourcesProvider;

    @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
    public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
        return true;
    }

    public void setMyLocationDenied(boolean z) {
        if (this.myLocationDenied == z) {
            return;
        }
        this.myLocationDenied = z;
    }

    public LocationActivitySearchAdapter(Context context, Theme.ResourcesProvider resourcesProvider, boolean z, boolean z2) {
        super(z, z2);
        this.myLocationDenied = false;
        this.mContext = context;
        this.resourcesProvider = resourcesProvider;
        FlickerLoadingView flickerLoadingView = new FlickerLoadingView(context);
        this.globalGradientView = flickerLoadingView;
        flickerLoadingView.setIsSingleCell(true);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int size = !this.locations.isEmpty() ? this.locations.size() + 1 : 0;
        if (this.myLocationDenied) {
            return size;
        }
        if (isSearching()) {
            return size + 3;
        }
        if (!this.locations.isEmpty() && !this.places.isEmpty()) {
            size++;
        }
        return size + this.places.size();
    }

    public boolean isEmpty() {
        return this.places.size() == 0 && this.locations.size() == 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View graySectionCell;
        if (i == 0) {
            graySectionCell = new LocationCell(this.mContext, false, this.resourcesProvider);
        } else {
            graySectionCell = new GraySectionCell(this.mContext, this.resourcesProvider);
        }
        return new RecyclerListView.Holder(graySectionCell);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0056  */
    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder r5, int r6) {
        /*
            r4 = this;
            int r0 = r5.getItemViewType()
            r1 = 1
            if (r0 != 0) goto L7d
            java.util.ArrayList r0 = r4.locations
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L12
            int r0 = r6 + (-1)
            goto L13
        L12:
            r0 = r6
        L13:
            if (r0 < 0) goto L27
            java.util.ArrayList r2 = r4.locations
            int r2 = r2.size()
            if (r0 >= r2) goto L27
            java.util.ArrayList r2 = r4.locations
            java.lang.Object r0 = r2.get(r0)
            org.telegram.tgnet.TLRPC$TL_messageMediaVenue r0 = (org.telegram.tgnet.TLRPC.TL_messageMediaVenue) r0
            r2 = 2
            goto L58
        L27:
            boolean r2 = r4.isSearching()
            if (r2 != 0) goto L56
            java.util.ArrayList r2 = r4.locations
            int r2 = r2.size()
            int r0 = r0 - r2
            boolean r2 = r4.searchingLocations
            if (r2 != 0) goto L42
            java.util.ArrayList r2 = r4.locations
            boolean r2 = r2.isEmpty()
            if (r2 != 0) goto L42
            int r0 = r0 + (-1)
        L42:
            r2 = r0
            if (r2 < 0) goto L56
            java.util.ArrayList r0 = r4.places
            int r0 = r0.size()
            if (r2 >= r0) goto L56
            java.util.ArrayList r0 = r4.places
            java.lang.Object r0 = r0.get(r2)
            org.telegram.tgnet.TLRPC$TL_messageMediaVenue r0 = (org.telegram.tgnet.TLRPC.TL_messageMediaVenue) r0
            goto L58
        L56:
            r0 = 0
            r2 = r6
        L58:
            android.view.View r5 = r5.itemView
            org.telegram.ui.Cells.LocationCell r5 = (org.telegram.p023ui.Cells.LocationCell) r5
            int r3 = r4.getItemCount()
            int r3 = r3 - r1
            if (r6 == r3) goto L78
            boolean r3 = r4.searchingLocations
            if (r3 != 0) goto L79
            java.util.ArrayList r3 = r4.locations
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L79
            java.util.ArrayList r3 = r4.locations
            int r3 = r3.size()
            if (r6 == r3) goto L78
            goto L79
        L78:
            r1 = 0
        L79:
            r5.setLocation(r0, r2, r1)
            return
        L7d:
            int r0 = r5.getItemViewType()
            if (r0 != r1) goto La8
            if (r6 != 0) goto L9b
            java.util.ArrayList r6 = r4.locations
            boolean r6 = r6.isEmpty()
            if (r6 != 0) goto L9b
            android.view.View r5 = r5.itemView
            org.telegram.ui.Cells.GraySectionCell r5 = (org.telegram.p023ui.Cells.GraySectionCell) r5
            int r6 = org.telegram.messenger.C2369R.string.LocationOnMap
            java.lang.String r6 = org.telegram.messenger.LocaleController.getString(r6)
            r5.setText(r6)
            return
        L9b:
            android.view.View r5 = r5.itemView
            org.telegram.ui.Cells.GraySectionCell r5 = (org.telegram.p023ui.Cells.GraySectionCell) r5
            int r6 = org.telegram.messenger.C2369R.string.NearbyVenue
            java.lang.String r6 = org.telegram.messenger.LocaleController.getString(r6)
            r5.setText(r6)
        La8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Adapters.LocationActivitySearchAdapter.onBindViewHolder(androidx.recyclerview.widget.RecyclerView$ViewHolder, int):void");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return ((i == 0 || i == this.locations.size() + 1) && !this.locations.isEmpty()) ? 1 : 0;
    }

    public TLRPC.TL_messageMediaVenue getItem(int i) {
        if (!this.locations.isEmpty()) {
            i--;
        }
        if (i >= 0 && i < this.locations.size()) {
            return (TLRPC.TL_messageMediaVenue) this.locations.get(i);
        }
        if (isSearching()) {
            return null;
        }
        int size = i - this.locations.size();
        if (!this.locations.isEmpty()) {
            size--;
        }
        if (size < 0 || size >= this.places.size()) {
            return null;
        }
        return (TLRPC.TL_messageMediaVenue) this.places.get(size);
    }
}
