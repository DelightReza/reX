package org.telegram.p023ui.Components.Premium.boosts.cells;

import android.content.Context;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.TextCheckCell;

/* loaded from: classes6.dex */
public class SwitcherCell extends TextCheckCell {
    public static int TYPE_ADDITION_PRIZE = 1;
    public static int TYPE_WINNERS;
    private int type;

    public SwitcherCell(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context, resourcesProvider);
    }

    public int getType() {
        return this.type;
    }

    public void setData(CharSequence charSequence, boolean z, boolean z2, int i) {
        this.type = i;
        setTextAndCheck(charSequence, z, z2);
    }
}
