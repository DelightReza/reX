package com.radolyn.ayugram.preferences.utils;

import android.view.View;
import com.radolyn.ayugram.controllers.AyuFilterCacheController;
import com.radolyn.ayugram.utils.filters.AyuFilterUtils;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.p023ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.tgnet.TLObject;

/* loaded from: classes4.dex */
public abstract class ShadowBanPopup {
    public static void show(BaseFragment baseFragment, View view, float f, float f2, long j) {
        if (baseFragment.getFragmentView() == null) {
            return;
        }
        ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(baseFragment.getContext());
        ActionBarPopupWindow actionBarPopupWindow = new ActionBarPopupWindow(actionBarPopupWindowLayout, -2, -2);
        ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayoutCreatePopupLayout = createPopupLayout(actionBarPopupWindowLayout, actionBarPopupWindow, baseFragment, j);
        actionBarPopupWindow.setPauseNotifications(true);
        actionBarPopupWindow.setDismissAnimationDuration(220);
        actionBarPopupWindow.setOutsideTouchable(true);
        actionBarPopupWindow.setClippingEnabled(true);
        actionBarPopupWindow.setAnimationStyle(C2369R.style.PopupContextAnimation);
        actionBarPopupWindow.setFocusable(true);
        actionBarPopupWindowLayoutCreatePopupLayout.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(1000.0f), TLObject.FLAG_31), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(1000.0f), TLObject.FLAG_31));
        actionBarPopupWindow.setInputMethodMode(2);
        actionBarPopupWindow.getContentView().setFocusableInTouchMode(true);
        while (view != baseFragment.getFragmentView()) {
            if (view.getParent() == null) {
                return;
            }
            f += view.getX();
            f2 += view.getY();
            view = (View) view.getParent();
        }
        actionBarPopupWindow.showAtLocation(baseFragment.getFragmentView(), 0, (int) (f - (actionBarPopupWindowLayoutCreatePopupLayout.getMeasuredWidth() / 2.0f)), (int) (f2 + (actionBarPopupWindowLayoutCreatePopupLayout.getMeasuredHeight() / 2.0f)));
        actionBarPopupWindow.dimBehind();
    }

    private static ActionBarPopupWindow.ActionBarPopupWindowLayout createPopupLayout(ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout, final ActionBarPopupWindow actionBarPopupWindow, final BaseFragment baseFragment, final long j) {
        actionBarPopupWindowLayout.setFitItems(true);
        ActionBarMenuSubItem actionBarMenuSubItemAddItem = ActionBarMenuItem.addItem(actionBarPopupWindowLayout, C2369R.drawable.msg_delete, LocaleController.getString(C2369R.string.Delete), false, baseFragment.getResourceProvider());
        actionBarMenuSubItemAddItem.setOnClickListener(new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.utils.ShadowBanPopup$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ShadowBanPopup.$r8$lambda$xmGdjm6sRhnreL4uhPW39FE7BgM(j, baseFragment, actionBarPopupWindow, view);
            }
        });
        int color = Theme.getColor(Theme.key_text_RedBold);
        actionBarMenuSubItemAddItem.setColors(color, color);
        return actionBarPopupWindowLayout;
    }

    public static /* synthetic */ void $r8$lambda$xmGdjm6sRhnreL4uhPW39FE7BgM(long j, BaseFragment baseFragment, ActionBarPopupWindow actionBarPopupWindow, View view) {
        AyuFilterUtils.removeShadowBan(j);
        AyuFilterCacheController.rebuildCache();
        baseFragment.onResume();
        actionBarPopupWindow.dismiss();
    }
}
