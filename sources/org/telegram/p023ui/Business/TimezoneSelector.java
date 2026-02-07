package org.telegram.p023ui.Business;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.TextCheckCell;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes5.dex */
public class TimezoneSelector extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private String currentTimezone;
    private LinearLayout emptyView;
    private UniversalRecyclerView listView;
    private String query;
    private ActionBarMenuItem searchItem;
    private boolean searching;
    private String systemTimezone;
    private boolean useSystem;
    private Utilities.Callback whenTimezoneSelected;

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setBackButtonImage(C2369R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString(C2369R.string.TimezoneTitle));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.Business.TimezoneSelector.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    TimezoneSelector.this.lambda$onBackPressed$371();
                }
            }
        });
        ActionBarMenuItem actionBarMenuItemSearchListener = this.actionBar.createMenu().addItem(1, C2369R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new ActionBarMenuItem.ActionBarMenuItemSearchListener() { // from class: org.telegram.ui.Business.TimezoneSelector.2
            @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
            public void onSearchExpand() {
                TimezoneSelector.this.searching = true;
                TimezoneSelector.this.listView.adapter.update(true);
                TimezoneSelector.this.listView.scrollToPosition(0);
            }

            @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
            public void onSearchCollapse() {
                TimezoneSelector.this.searching = false;
                TimezoneSelector.this.query = null;
                TimezoneSelector.this.listView.adapter.update(true);
                TimezoneSelector.this.listView.scrollToPosition(0);
            }

            @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener
            public void onTextChanged(EditText editText) {
                TimezoneSelector.this.query = editText.getText().toString();
                TimezoneSelector.this.listView.adapter.update(true);
                TimezoneSelector.this.listView.scrollToPosition(0);
            }
        });
        this.searchItem = actionBarMenuItemSearchListener;
        actionBarMenuItemSearchListener.setSearchFieldHint(LocaleController.getString(C2369R.string.Search));
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        UniversalRecyclerView universalRecyclerView = new UniversalRecyclerView(this, new Utilities.Callback2() { // from class: org.telegram.ui.Business.TimezoneSelector$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, new Utilities.Callback5() { // from class: org.telegram.ui.Business.TimezoneSelector$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback5
            public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                this.f$0.onClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
            }
        }, null);
        this.listView = universalRecyclerView;
        universalRecyclerView.adapter.setUseSectionStyle(true);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.Business.TimezoneSelector.3
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 1) {
                    AndroidUtilities.hideKeyboard(TimezoneSelector.this.getParentActivity().getCurrentFocus());
                }
            }
        });
        LinearLayout linearLayout = new LinearLayout(context);
        this.emptyView = linearLayout;
        linearLayout.setOrientation(1);
        this.emptyView.setMinimumHeight(AndroidUtilities.m1146dp(500.0f));
        BackupImageView backupImageView = new BackupImageView(context);
        backupImageView.getImageReceiver().setAllowLoadingOnAttachedOnly(false);
        MediaDataController.getInstance(this.currentAccount).setPlaceholderImage(backupImageView, "RestrictedEmoji", "🌖", "130_130");
        this.emptyView.addView(backupImageView, LayoutHelper.createLinear(Opcodes.IXOR, Opcodes.IXOR, 49, 0, 42, 0, 12));
        TextView textView = new TextView(context);
        textView.setText(LocaleController.getString(C2369R.string.TimezoneNotFound));
        textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText, this.resourceProvider));
        textView.setTextSize(1, 15.0f);
        this.emptyView.addView(textView, LayoutHelper.createLinear(-2, -2, 49, 0, 0, 0, 0));
        this.fragmentView = frameLayout;
        return frameLayout;
    }

    public TimezoneSelector setValue(String str) {
        this.currentTimezone = str;
        return this;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        String systemTimezoneId = TimezonesController.getInstance(this.currentAccount).getSystemTimezoneId();
        this.systemTimezone = systemTimezoneId;
        this.useSystem = TextUtils.equals(systemTimezoneId, this.currentTimezone);
        getNotificationCenter().addObserver(this, NotificationCenter.timezonesUpdated);
        return super.onFragmentCreate();
    }

    public TimezoneSelector whenSelected(Utilities.Callback callback) {
        this.whenTimezoneSelected = callback;
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00a1  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void fillItems(java.util.ArrayList r11, org.telegram.p023ui.Components.UniversalAdapter r12) {
        /*
            r10 = this;
            boolean r12 = r10.searching
            r0 = 0
            r1 = 1
            if (r12 == 0) goto L10
            java.lang.String r12 = r10.query
            boolean r12 = android.text.TextUtils.isEmpty(r12)
            if (r12 != 0) goto L10
            r12 = 1
            goto L11
        L10:
            r12 = 0
        L11:
            int r2 = r10.currentAccount
            org.telegram.ui.Business.TimezonesController r2 = org.telegram.p023ui.Business.TimezonesController.getInstance(r2)
            if (r12 != 0) goto L51
            int r3 = org.telegram.messenger.C2369R.string.TimezoneDetectAutomatically
            java.lang.String r3 = org.telegram.messenger.LocaleController.getString(r3)
            r4 = -1
            org.telegram.ui.Components.UItem r3 = org.telegram.p023ui.Components.UItem.asRippleCheck(r4, r3)
            boolean r4 = r10.useSystem
            org.telegram.ui.Components.UItem r3 = r3.setChecked(r4)
            r11.add(r3)
            int r3 = org.telegram.messenger.C2369R.string.TimezoneDetectAutomaticallyInfo
            java.lang.String r4 = r10.currentTimezone
            java.lang.String r4 = r2.getTimezoneName(r4, r1)
            java.lang.Object[] r5 = new java.lang.Object[r1]
            r5[r0] = r4
            java.lang.String r3 = org.telegram.messenger.LocaleController.formatString(r3, r5)
            org.telegram.ui.Components.UItem r3 = org.telegram.p023ui.Components.UItem.asShadow(r3)
            r11.add(r3)
            int r3 = org.telegram.messenger.C2369R.string.TimezoneHeader
            java.lang.String r3 = org.telegram.messenger.LocaleController.getString(r3)
            org.telegram.ui.Components.UItem r3 = org.telegram.p023ui.Components.UItem.asHeader(r3)
            r11.add(r3)
        L51:
            r3 = 0
            r4 = 1
        L53:
            java.util.ArrayList r5 = r2.getTimezones()
            int r5 = r5.size()
            if (r3 >= r5) goto Lce
            java.util.ArrayList r5 = r2.getTimezones()
            java.lang.Object r5 = r5.get(r3)
            org.telegram.tgnet.TLRPC$TL_timezone r5 = (org.telegram.tgnet.TLRPC.TL_timezone) r5
            if (r12 == 0) goto La1
            java.lang.String r6 = r5.name
            java.lang.String r6 = org.telegram.messenger.AndroidUtilities.translitSafe(r6)
            java.lang.String r6 = r6.toLowerCase()
            java.lang.String r7 = "/"
            java.lang.String r8 = " "
            java.lang.String r6 = r6.replace(r7, r8)
            java.lang.String r7 = r10.query
            java.lang.String r7 = org.telegram.messenger.AndroidUtilities.translitSafe(r7)
            java.lang.String r7 = r7.toLowerCase()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r8)
            r9.append(r7)
            java.lang.String r8 = r9.toString()
            boolean r8 = r6.contains(r8)
            if (r8 != 0) goto La1
            boolean r6 = r6.startsWith(r7)
            if (r6 != 0) goto La1
            goto Lcb
        La1:
            java.lang.String r4 = r2.getTimezoneName(r5, r0)
            java.lang.String r6 = r2.getTimezoneOffsetName(r5)
            org.telegram.ui.Components.UItem r4 = org.telegram.p023ui.Components.UItem.asRadio(r3, r4, r6)
            java.lang.String r5 = r5.f1719id
            java.lang.String r6 = r10.currentTimezone
            boolean r5 = android.text.TextUtils.equals(r5, r6)
            org.telegram.ui.Components.UItem r4 = r4.setChecked(r5)
            boolean r5 = r10.useSystem
            if (r5 == 0) goto Lc2
            if (r12 == 0) goto Lc0
            goto Lc2
        Lc0:
            r5 = 0
            goto Lc3
        Lc2:
            r5 = 1
        Lc3:
            org.telegram.ui.Components.UItem r4 = r4.setEnabled(r5)
            r11.add(r4)
            r4 = 0
        Lcb:
            int r3 = r3 + 1
            goto L53
        Lce:
            if (r4 == 0) goto Lde
            android.widget.LinearLayout r12 = r10.emptyView
            org.telegram.ui.Components.UItem r12 = org.telegram.p023ui.Components.UItem.asCustom(r12)
            org.telegram.ui.Components.UItem r12 = r12.setTransparent(r1)
            r11.add(r12)
            return
        Lde:
            r12 = 0
            org.telegram.ui.Components.UItem r12 = org.telegram.p023ui.Components.UItem.asShadow(r12)
            r11.add(r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Business.TimezoneSelector.fillItems(java.util.ArrayList, org.telegram.ui.Components.UniversalAdapter):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClick(UItem uItem, View view, int i, float f, float f2) {
        if (uItem.f2017id == -1) {
            boolean z = this.useSystem;
            this.useSystem = !z;
            if (!z) {
                String str = this.systemTimezone;
                this.currentTimezone = str;
                Utilities.Callback callback = this.whenTimezoneSelected;
                if (callback != null) {
                    callback.run(str);
                }
            }
            TextCheckCell textCheckCell = (TextCheckCell) view;
            textCheckCell.setChecked(this.useSystem);
            boolean z2 = this.useSystem;
            textCheckCell.setBackgroundColorAnimated(z2, Theme.getColor(z2 ? Theme.key_windowBackgroundChecked : Theme.key_windowBackgroundUnchecked));
            this.listView.adapter.update(true);
            return;
        }
        if (view.isEnabled()) {
            TimezonesController timezonesController = TimezonesController.getInstance(this.currentAccount);
            int i2 = uItem.f2017id;
            if (i2 < 0 || i2 >= timezonesController.getTimezones().size()) {
                return;
            }
            TLRPC.TL_timezone tL_timezone = (TLRPC.TL_timezone) timezonesController.getTimezones().get(uItem.f2017id);
            this.useSystem = false;
            String str2 = tL_timezone.f1719id;
            this.currentTimezone = str2;
            Utilities.Callback callback2 = this.whenTimezoneSelected;
            if (callback2 != null) {
                callback2.run(str2);
            }
            if (this.searching) {
                this.actionBar.closeSearchField(true);
            }
            this.listView.adapter.update(true);
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        UniversalRecyclerView universalRecyclerView;
        UniversalAdapter universalAdapter;
        if (i != NotificationCenter.timezonesUpdated || (universalRecyclerView = this.listView) == null || (universalAdapter = universalRecyclerView.adapter) == null) {
            return;
        }
        universalAdapter.update(true);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        getNotificationCenter().removeObserver(this, NotificationCenter.timezonesUpdated);
        super.onFragmentDestroy();
    }
}
