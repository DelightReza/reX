package org.telegram.p023ui;

import android.content.Context;
import android.view.View;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.Components.SizeNotifierFrameLayout;

/* loaded from: classes.dex */
public abstract class EmptyBaseFragment extends BaseFragment {
    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        SizeNotifierFrameLayout sizeNotifierFrameLayout = new SizeNotifierFrameLayout(context);
        this.fragmentView = sizeNotifierFrameLayout;
        return sizeNotifierFrameLayout;
    }
}
