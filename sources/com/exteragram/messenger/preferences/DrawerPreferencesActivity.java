package com.exteragram.messenger.preferences;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.utils.AppUtils;
import com.exteragram.messenger.utils.p011ui.PopupUtils;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import org.telegram.p023ui.PrivacySettingsActivity;

/* loaded from: classes.dex */
public class DrawerPreferencesActivity extends BasePreferencesActivity {
    private static final Map events;
    private Drawable reorderIcon;
    private ActionBarMenuItem resetItem;
    private final HashMap itemDetails = new HashMap();
    private final ArrayList stableDividerIds = new ArrayList();
    private int nextDividerId = -2000;

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        events = linkedHashMap;
        linkedHashMap.put(Integer.valueOf(C2369R.drawable.msg_calendar2), LocaleController.getString(C2369R.string.DependsOnTheDate));
        linkedHashMap.put(Integer.valueOf(C2369R.drawable.msg_block), LocaleController.getString(C2369R.string.Default));
        linkedHashMap.put(Integer.valueOf(C2369R.drawable.msg_settings_ny), LocaleController.getString(C2369R.string.NewYear));
        linkedHashMap.put(Integer.valueOf(C2369R.drawable.msg_saved_14), LocaleController.getString(C2369R.string.ValentinesDay));
        linkedHashMap.put(Integer.valueOf(C2369R.drawable.msg_contacts_hw), LocaleController.getString(C2369R.string.Halloween));
    }

    /* loaded from: classes3.dex */
    private static class ItemInfo {
        int iconRes;
        CharSequence name;

        ItemInfo(CharSequence charSequence, int i) {
            this.name = charSequence;
            this.iconRes = i;
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        initItemDetails();
        return super.onFragmentCreate();
    }

    private void initItemDetails() {
        int[] drawerIconPack = AppUtils.getDrawerIconPack();
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.GHOST_MODE.f145id), new ItemInfo(LocaleController.getString(C2369R.string.EnableGhostMode), C2369R.drawable.ayu_ghost));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.KILL_APP.f145id), new ItemInfo(LocaleController.getString(C2369R.string.KillApp), ExteraConfig.iconPack == 2 ? C2369R.drawable.msg_block_remix : C2369R.drawable.msg_disable));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.PROFILE.f145id), new ItemInfo(LocaleController.getString(C2369R.string.MyProfile), C2369R.drawable.left_status_profile));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.STATUS.f145id), new ItemInfo(PrivacySettingsActivity.addPremiumStar(LocaleController.getString(C2369R.string.ChangeEmojiStatus)), C2369R.drawable.msg_status_edit));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.ARCHIVE.f145id), new ItemInfo(LocaleController.getString(C2369R.string.ArchivedChats), C2369R.drawable.msg_archive));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.BOTS.f145id), new ItemInfo(LocaleController.getString(C2369R.string.FilterBots), C2369R.drawable.msg_bot));
        int i = 0;
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.NEW_GROUP.f145id), new ItemInfo(LocaleController.getString(C2369R.string.NewGroup), drawerIconPack[0]));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.NEW_SECRET.f145id), new ItemInfo(LocaleController.getString(C2369R.string.NewSecretChat), drawerIconPack[1]));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.NEW_CHANNEL.f145id), new ItemInfo(LocaleController.getString(C2369R.string.NewChannel), drawerIconPack[2]));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.CONTACTS.f145id), new ItemInfo(LocaleController.getString(C2369R.string.Contacts), drawerIconPack[3]));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.CALLS.f145id), new ItemInfo(LocaleController.getString(C2369R.string.Calls), drawerIconPack[4]));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.SAVED.f145id), new ItemInfo(LocaleController.getString(C2369R.string.SavedMessages), drawerIconPack[5]));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.SETTINGS.f145id), new ItemInfo(LocaleController.getString(C2369R.string.Settings), C2369R.drawable.msg_settings_old));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.PLUGINS.f145id), new ItemInfo(LocaleController.getString(C2369R.string.Plugins), C2369R.drawable.msg_plugins));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.BROWSER.f145id), new ItemInfo(LocaleController.getString(C2369R.string.BrowserSettingsTitle), C2369R.drawable.msg2_language));
        this.itemDetails.put(Integer.valueOf(ExteraConfig.DrawerItem.QR.f145id), new ItemInfo(LocaleController.getString(C2369R.string.AuthAnotherClient), C2369R.drawable.msg_qrcode));
        this.stableDividerIds.clear();
        this.nextDividerId = -2000;
        ArrayList arrayList = ExteraConfig.drawerLayout;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((Integer) obj).intValue() == ExteraConfig.DrawerItem.DIVIDER.f145id) {
                ArrayList arrayList2 = this.stableDividerIds;
                int i2 = this.nextDividerId;
                this.nextDividerId = i2 - 1;
                arrayList2.add(Integer.valueOf(i2));
            }
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.DrawerOptions);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        View viewCreateView = super.createView(context);
        ActionBarMenuItem actionBarMenuItemAddItem = this.actionBar.createMenu().addItem(0, C2369R.drawable.msg_reset);
        this.resetItem = actionBarMenuItemAddItem;
        actionBarMenuItemAddItem.setContentDescription(LocaleController.getString(C2369R.string.Reset));
        updateResetButtonVisibility();
        this.resetItem.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.preferences.DrawerPreferencesActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$0(view);
            }
        });
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null) {
            universalRecyclerView.allowReorder(true);
            this.listView.listenReorder(new Utilities.Callback2() { // from class: com.exteragram.messenger.preferences.DrawerPreferencesActivity$$ExternalSyntheticLambda1
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.updateConfigFromReorder(((Integer) obj).intValue(), (ArrayList) obj2);
                }
            });
        }
        return viewCreateView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(View view) {
        resetToDefault();
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        int iIntValue;
        if (this.reorderIcon == null) {
            this.reorderIcon = ContextCompat.getDrawable(getContext(), C2369R.drawable.list_reorder);
        }
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.DrawerOptions)));
        int i = 0;
        arrayList.add(UItem.asButton(-2, LocaleController.getString(C2369R.string.DrawerIconSet), ((CharSequence[]) events.values().toArray(new CharSequence[0]))[ExteraConfig.eventType]).setSearchable(this).setLinkAlias("drawerIconSet", this));
        arrayList.add(UItem.asCheck(-3, LocaleController.getString(C2369R.string.DrawerImmersiveAnimation)).setChecked(ExteraConfig.immersiveDrawerAnimation).setSearchable(this).setLinkAlias("immersiveDrawerAnimation", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.DrawerElements)));
        universalAdapter.whiteSectionStart();
        universalAdapter.reorderSectionStart();
        int i2 = 0;
        for (int i3 = 0; i3 < ExteraConfig.drawerLayout.size(); i3++) {
            Integer num = (Integer) ExteraConfig.drawerLayout.get(i3);
            if (num.intValue() == ExteraConfig.DrawerItem.DIVIDER.f145id) {
                if (i2 < this.stableDividerIds.size()) {
                    iIntValue = ((Integer) this.stableDividerIds.get(i2)).intValue();
                } else {
                    iIntValue = this.nextDividerId;
                    this.nextDividerId = iIntValue - 1;
                    this.stableDividerIds.add(Integer.valueOf(iIntValue));
                }
                UItem uItemAsButton = UItem.asButton(iIntValue, C2369R.drawable.msg_block, LocaleController.getString(C2369R.string.DrawerSeparator));
                uItemAsButton.object2 = this.reorderIcon;
                arrayList.add(uItemAsButton);
                i2++;
            } else {
                ItemInfo itemInfo = (ItemInfo) this.itemDetails.get(num);
                if (itemInfo != null) {
                    UItem uItemAsButton2 = UItem.asButton(num.intValue(), itemInfo.iconRes, itemInfo.name);
                    uItemAsButton2.object2 = this.reorderIcon;
                    arrayList.add(uItemAsButton2);
                }
            }
        }
        universalAdapter.reorderSectionEnd();
        universalAdapter.whiteSectionEnd();
        arrayList.add(UItem.asButton(-200, C2369R.drawable.msg_add, LocaleController.getString(C2369R.string.DrawerAddSeparator)).accent());
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.DrawerElementsInfo)));
        if (ExteraConfig.drawerHiddenItems.isEmpty()) {
            return;
        }
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.DrawerHiddenElements)));
        universalAdapter.whiteSectionStart();
        universalAdapter.reorderSectionStart();
        ArrayList arrayList2 = ExteraConfig.drawerHiddenItems;
        int size = arrayList2.size();
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            Integer num2 = (Integer) obj;
            ItemInfo itemInfo2 = (ItemInfo) this.itemDetails.get(num2);
            if (itemInfo2 != null) {
                UItem uItemAsButton3 = UItem.asButton(num2.intValue(), itemInfo2.iconRes, itemInfo2.name);
                uItemAsButton3.object2 = this.reorderIcon;
                arrayList.add(uItemAsButton3);
            }
        }
        universalAdapter.reorderSectionEnd();
        universalAdapter.whiteSectionEnd();
        arrayList.add(UItem.asShadow(null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConfigFromReorder(int i, ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        int i2 = 0;
        if (i == 0) {
            ArrayList arrayList3 = new ArrayList();
            int size = arrayList.size();
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                UItem uItem = (UItem) obj;
                int i3 = uItem.f2017id;
                if (i3 <= -2000) {
                    arrayList2.add(Integer.valueOf(ExteraConfig.DrawerItem.DIVIDER.f145id));
                    arrayList3.add(Integer.valueOf(uItem.f2017id));
                } else {
                    arrayList2.add(Integer.valueOf(i3));
                }
            }
            this.stableDividerIds.clear();
            this.stableDividerIds.addAll(arrayList3);
        } else {
            int size2 = arrayList.size();
            while (i2 < size2) {
                Object obj2 = arrayList.get(i2);
                i2++;
                int i4 = ((UItem) obj2).f2017id;
                if (i4 <= -2000) {
                    arrayList2.add(Integer.valueOf(ExteraConfig.DrawerItem.DIVIDER.f145id));
                } else {
                    arrayList2.add(Integer.valueOf(i4));
                }
            }
        }
        if (i == 0) {
            ExteraConfig.drawerLayout.clear();
            ExteraConfig.drawerLayout.addAll(arrayList2);
        } else if (i == 1) {
            ExteraConfig.drawerHiddenItems.clear();
            ExteraConfig.drawerHiddenItems.addAll(arrayList2);
        }
        saveAndNotify();
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        int i3 = 0;
        if (i2 == -2) {
            Map map = events;
            showListDialog(uItem, (CharSequence[]) map.values().toArray(new CharSequence[0]), unBox(map.keySet()), LocaleController.getString(C2369R.string.DrawerIconSet), ExteraConfig.eventType, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.DrawerPreferencesActivity$$ExternalSyntheticLambda2
                @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                public final void onClick(int i4) {
                    this.f$0.lambda$onClick$1(i4);
                }
            });
            return;
        }
        if (i2 == -3) {
            toggleBooleanSettingAndRefresh("immersiveDrawerAnimation", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.DrawerPreferencesActivity$$ExternalSyntheticLambda3
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    ExteraConfig.immersiveDrawerAnimation = ((Boolean) obj).booleanValue();
                }
            });
            return;
        }
        if (i2 == -200) {
            ArrayList arrayList = this.stableDividerIds;
            int i4 = this.nextDividerId;
            this.nextDividerId = i4 - 1;
            arrayList.add(Integer.valueOf(i4));
            ExteraConfig.drawerLayout.add(Integer.valueOf(ExteraConfig.DrawerItem.DIVIDER.f145id));
            saveAndNotify();
            return;
        }
        if (i2 <= -2000) {
            int iIndexOf = this.stableDividerIds.indexOf(Integer.valueOf(i2));
            if (iIndexOf != -1) {
                int i5 = 0;
                while (true) {
                    if (i3 >= ExteraConfig.drawerLayout.size()) {
                        i3 = -1;
                        break;
                    }
                    if (((Integer) ExteraConfig.drawerLayout.get(i3)).intValue() == ExteraConfig.DrawerItem.DIVIDER.f145id) {
                        if (i5 == iIndexOf) {
                            break;
                        } else {
                            i5++;
                        }
                    }
                    i3++;
                }
                if (i3 != -1) {
                    this.stableDividerIds.remove(iIndexOf);
                    ExteraConfig.drawerLayout.remove(i3);
                    saveAndNotify();
                    return;
                }
                return;
            }
            return;
        }
        if (i2 == ExteraConfig.DrawerItem.SETTINGS.f145id) {
            BulletinFactory.m1267of(this).createErrorBulletin(LocaleController.getString(C2369R.string.DrawerRemoveSettingsInfo)).show();
            return;
        }
        if (ExteraConfig.drawerLayout.contains(Integer.valueOf(i2))) {
            ExteraConfig.drawerLayout.remove(Integer.valueOf(i2));
            if (!ExteraConfig.drawerHiddenItems.contains(Integer.valueOf(i2))) {
                ExteraConfig.drawerHiddenItems.add(0, Integer.valueOf(i2));
            }
        } else if (ExteraConfig.drawerHiddenItems.contains(Integer.valueOf(i2))) {
            ExteraConfig.drawerHiddenItems.remove(Integer.valueOf(i2));
            ExteraConfig.drawerLayout.add(Integer.valueOf(i2));
        }
        saveAndNotify();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$1(int i) {
        ExteraConfig.eventType = i;
        changeIntSetting("eventType", i);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
        initItemDetails();
    }

    private void saveAndNotify() {
        UniversalAdapter universalAdapter;
        ExteraConfig.saveDrawerLayout();
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null && (universalAdapter = universalRecyclerView.adapter) != null) {
            universalAdapter.update(true);
        }
        updateResetButtonVisibility();
    }

    private void updateResetButtonVisibility() {
        if (this.resetItem == null) {
            return;
        }
        boolean zEquals = ExteraConfig.drawerLayout.equals(ExteraConfig.getDefaultDrawerLayout());
        if (!zEquals && this.resetItem.getVisibility() == 8) {
            AndroidUtilities.updateViewVisibilityAnimated(this.resetItem, true, 0.5f, true);
        } else if (zEquals && this.resetItem.getVisibility() == 0) {
            AndroidUtilities.updateViewVisibilityAnimated(this.resetItem, false, 0.5f, true);
        }
    }

    private void resetToDefault() {
        UniversalAdapter universalAdapter;
        ExteraConfig.drawerLayout.clear();
        ExteraConfig.drawerLayout.addAll(ExteraConfig.getDefaultDrawerLayout());
        ExteraConfig.drawerHiddenItems.clear();
        for (ExteraConfig.DrawerItem drawerItem : ExteraConfig.DrawerItem.values()) {
            if (drawerItem != ExteraConfig.DrawerItem.DIVIDER && !ExteraConfig.drawerLayout.contains(Integer.valueOf(drawerItem.f145id)) && (drawerItem != ExteraConfig.DrawerItem.PLUGINS || PluginsController.isPluginEngineSupported())) {
                ExteraConfig.drawerHiddenItems.add(Integer.valueOf(drawerItem.f145id));
            }
        }
        this.stableDividerIds.clear();
        this.nextDividerId = -2000;
        ArrayList arrayList = ExteraConfig.drawerLayout;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((Integer) obj).intValue() == ExteraConfig.DrawerItem.DIVIDER.f145id) {
                ArrayList arrayList2 = this.stableDividerIds;
                int i2 = this.nextDividerId;
                this.nextDividerId = i2 - 1;
                arrayList2.add(Integer.valueOf(i2));
            }
        }
        ExteraConfig.saveDrawerLayout();
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null && (universalAdapter = universalRecyclerView.adapter) != null) {
            universalAdapter.update(true);
        }
        updateResetButtonVisibility();
    }
}
