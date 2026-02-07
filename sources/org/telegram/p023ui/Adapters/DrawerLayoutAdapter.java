package org.telegram.p023ui.Adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Keep;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.hooks.MenuItemRecord;
import com.exteragram.messenger.plugins.utils.MenuContextBuilder;
import com.exteragram.messenger.utils.ChatUtils;
import com.radolyn.ayugram.AyuUtils;
import com.radolyn.ayugram.controllers.AyuGhostController;
import com.radolyn.ayugram.preferences.GhostModePreferencesActivity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.DrawerLayoutContainer;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.DividerCell;
import org.telegram.p023ui.Cells.DrawerActionCell;
import org.telegram.p023ui.Cells.DrawerAddCell;
import org.telegram.p023ui.Cells.DrawerProfileCell;
import org.telegram.p023ui.Cells.DrawerUserCell;
import org.telegram.p023ui.Cells.EmptyCell;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.SideMenultItemAnimator;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public class DrawerLayoutAdapter extends RecyclerListView.SelectionAdapter {
    private boolean accountsShown;
    private SideMenultItemAnimator itemAnimator;
    private Context mContext;
    private DrawerLayoutContainer mDrawerLayoutContainer;
    private View.OnClickListener onPremiumDrawableClick;
    public DrawerProfileCell profileCell;
    private ArrayList items = new ArrayList(16);
    private ArrayList accountNumbers = new ArrayList();

    public DrawerLayoutAdapter(Context context, SideMenultItemAnimator sideMenultItemAnimator, DrawerLayoutContainer drawerLayoutContainer) {
        this.mContext = context;
        this.mDrawerLayoutContainer = drawerLayoutContainer;
        this.itemAnimator = sideMenultItemAnimator;
        this.accountsShown = UserConfig.getActivatedAccountsCount() > 1 && MessagesController.getGlobalMainSettings().getBoolean("accountsShown", true);
        Theme.createCommonDialogResources(context);
        resetItems();
    }

    private int getAccountRowsCount() {
        int size = this.accountNumbers.size();
        return this.accountNumbers.size() < 16 ? size + 2 : size + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        int size = this.items.size() + 2;
        return this.accountsShown ? size + getAccountRowsCount() : size;
    }

    public void setAccountsShown(boolean z, boolean z2) {
        if (this.accountsShown == z || this.itemAnimator.isRunning()) {
            return;
        }
        this.accountsShown = z;
        DrawerProfileCell drawerProfileCell = this.profileCell;
        if (drawerProfileCell != null) {
            drawerProfileCell.setAccountsShown(z, z2);
        }
        MessagesController.getGlobalMainSettings().edit().putBoolean("accountsShown", this.accountsShown).apply();
        if (z2) {
            this.itemAnimator.setShouldClipChildren(false);
            if (this.accountsShown) {
                notifyItemRangeInserted(2, getAccountRowsCount());
                return;
            } else {
                notifyItemRangeRemoved(2, getAccountRowsCount());
                return;
            }
        }
        notifyDataSetChanged();
    }

    public boolean isAccountsShown() {
        return this.accountsShown;
    }

    public void setOnPremiumDrawableClick(View.OnClickListener onClickListener) {
        this.onPremiumDrawableClick = onClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void notifyDataSetChanged() {
        resetItems();
        try {
            super.notifyDataSetChanged();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
    public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
        int itemViewType = viewHolder.getItemViewType();
        return itemViewType == 3 || itemViewType == 4 || itemViewType == 5 || itemViewType == 6;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View dividerCell;
        if (i == 0) {
            DrawerProfileCell drawerProfileCell = new DrawerProfileCell(this.mContext, this.mDrawerLayoutContainer) { // from class: org.telegram.ui.Adapters.DrawerLayoutAdapter.1
                @Override // org.telegram.p023ui.Cells.DrawerProfileCell
                protected void onPremiumClick() {
                    if (DrawerLayoutAdapter.this.onPremiumDrawableClick != null) {
                        DrawerLayoutAdapter.this.onPremiumDrawableClick.onClick(this);
                    }
                }
            };
            this.profileCell = drawerProfileCell;
            dividerCell = drawerProfileCell;
        } else if (i == 2) {
            dividerCell = new DividerCell(this.mContext);
        } else if (i == 3) {
            dividerCell = new DrawerActionCell(this.mContext);
        } else if (i == 4) {
            dividerCell = new DrawerUserCell(this.mContext);
        } else if (i == 5) {
            dividerCell = new DrawerAddCell(this.mContext);
        } else {
            dividerCell = new EmptyCell(this.mContext, AndroidUtilities.m1146dp(8.0f));
        }
        dividerCell.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
        return new RecyclerListView.Holder(dividerCell);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = viewHolder.getItemViewType();
        if (itemViewType == 0) {
            ((DrawerProfileCell) viewHolder.itemView).setUser(MessagesController.getInstance(UserConfig.selectedAccount).getUser(Long.valueOf(UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId())), this.accountsShown);
            return;
        }
        if (itemViewType != 3) {
            if (itemViewType != 4) {
                return;
            }
            ((DrawerUserCell) viewHolder.itemView).setAccount(((Integer) this.accountNumbers.get(i - 2)).intValue());
        } else {
            DrawerActionCell drawerActionCell = (DrawerActionCell) viewHolder.itemView;
            int accountRowsCount = i - 2;
            if (this.accountsShown) {
                accountRowsCount -= getAccountRowsCount();
            }
            ((Item) this.items.get(accountRowsCount)).bind(drawerActionCell);
            drawerActionCell.setPadding(0, 0, 0, 0);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (i == 0) {
            return 0;
        }
        if (i == 1) {
            return 1;
        }
        int accountRowsCount = i - 2;
        if (this.accountsShown) {
            if (accountRowsCount < this.accountNumbers.size()) {
                return 4;
            }
            if (this.accountNumbers.size() < 16) {
                if (accountRowsCount == this.accountNumbers.size()) {
                    return 5;
                }
                if (accountRowsCount == this.accountNumbers.size() + 1) {
                    return 2;
                }
            } else if (accountRowsCount == this.accountNumbers.size()) {
                return 2;
            }
            accountRowsCount -= getAccountRowsCount();
        }
        return (accountRowsCount < 0 || accountRowsCount >= this.items.size() || this.items.get(accountRowsCount) == null) ? 2 : 3;
    }

    public void swapElements(int i, int i2) {
        int i3 = i - 2;
        int i4 = i2 - 2;
        if (i3 < 0 || i4 < 0 || i3 >= this.accountNumbers.size() || i4 >= this.accountNumbers.size()) {
            return;
        }
        UserConfig userConfig = UserConfig.getInstance(((Integer) this.accountNumbers.get(i3)).intValue());
        UserConfig userConfig2 = UserConfig.getInstance(((Integer) this.accountNumbers.get(i4)).intValue());
        int i5 = userConfig.loginTime;
        userConfig.loginTime = userConfig2.loginTime;
        userConfig2.loginTime = i5;
        userConfig.saveConfig(false);
        userConfig2.saveConfig(false);
        Collections.swap(this.accountNumbers, i3, i4);
        notifyItemMoved(i, i2);
    }

    private void resetItems() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        Map<String, Object> map;
        int i9;
        UserConfig userConfig;
        ArrayList arrayList;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        String string;
        this.accountNumbers.clear();
        for (int i15 = 0; i15 < 16; i15++) {
            if (UserConfig.getInstance(i15).isClientActivated()) {
                this.accountNumbers.add(Integer.valueOf(i15));
            }
        }
        Collections.sort(this.accountNumbers, new Comparator() { // from class: org.telegram.ui.Adapters.DrawerLayoutAdapter$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return DrawerLayoutAdapter.$r8$lambda$DPSN6neiw4rCZmjHk8KNUA1aKYA((Integer) obj, (Integer) obj2);
            }
        });
        final Map<String, Object> mapBuild = MenuContextBuilder.create().withAccount(UserConfig.selectedAccount).withContext(this.mContext).withUser(UserConfig.getInstance(UserConfig.selectedAccount).getCurrentUser()).build();
        final List<MenuItemRecord> menuItemsForLocation = PluginsController.getInstance().getMenuItemsForLocation(PluginsConstants.MenuItemTypes.DRAWER_MENU, mapBuild);
        this.items.clear();
        if (UserConfig.getInstance(UserConfig.selectedAccount).isClientActivated()) {
            int eventType = Theme.getEventType();
            int i16 = C2369R.drawable.msg_archive;
            int i17 = C2369R.drawable.msg_qrcode;
            if (eventType == 0) {
                i = C2369R.drawable.msg_groups_ny;
                i2 = C2369R.drawable.msg_secret_ny;
                i3 = C2369R.drawable.msg_channel_ny;
                i4 = C2369R.drawable.msg_contacts_ny;
                i5 = C2369R.drawable.msg_calls_ny;
                i6 = C2369R.drawable.msg_saved_ny;
                i7 = C2369R.drawable.msg_settings_ny;
            } else if (eventType == 1) {
                i = C2369R.drawable.msg_groups_14;
                i2 = C2369R.drawable.msg_secret_14;
                i3 = C2369R.drawable.msg_channel_14;
                i4 = C2369R.drawable.msg_contacts_14;
                i5 = C2369R.drawable.msg_calls_14;
                i6 = C2369R.drawable.msg_saved_14;
                i7 = C2369R.drawable.msg_settings_14;
            } else if (eventType == 2) {
                i = C2369R.drawable.msg_groups_hw;
                i2 = C2369R.drawable.msg_secret_hw;
                i3 = C2369R.drawable.msg_channel_hw;
                i4 = C2369R.drawable.msg_contacts_hw;
                i5 = C2369R.drawable.msg_calls_hw;
                i6 = C2369R.drawable.msg_saved_hw;
                i7 = C2369R.drawable.msg_settings_hw;
            } else {
                i = C2369R.drawable.msg_groups;
                i2 = C2369R.drawable.msg_secret;
                i3 = C2369R.drawable.msg_channel;
                i4 = C2369R.drawable.msg_contacts;
                i5 = C2369R.drawable.msg_calls;
                i6 = C2369R.drawable.msg_saved;
                i7 = C2369R.drawable.msg_settings_old;
            }
            UserConfig userConfig2 = UserConfig.getInstance(UserConfig.selectedAccount);
            if (ExteraConfig.drawerLayout.isEmpty()) {
                ExteraConfig.loadConfig();
            }
            ArrayList arrayList2 = ExteraConfig.drawerLayout;
            int size = arrayList2.size();
            int i18 = 0;
            while (i18 < size) {
                Object obj = arrayList2.get(i18);
                i18++;
                Integer num = (Integer) obj;
                ExteraConfig.DrawerItem byId = ExteraConfig.DrawerItem.getById(num.intValue());
                if (byId != null) {
                    switch (C25952.$SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[byId.ordinal()]) {
                        case 1:
                            i8 = size;
                            map = mapBuild;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            i10 = i;
                            i11 = i16;
                            this.items.add(null);
                            break;
                        case 2:
                            i8 = size;
                            map = mapBuild;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            i10 = i;
                            i11 = i16;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.MyProfile), C2369R.drawable.left_status_profile));
                            break;
                        case 3:
                            i8 = size;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            i10 = i;
                            i11 = i16;
                            if (userConfig == null || !userConfig.isPremium()) {
                                map = mapBuild;
                                break;
                            } else if (userConfig.getEmojiStatus() != null) {
                                map = mapBuild;
                                this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.ChangeEmojiStatus), C2369R.drawable.msg_status_edit));
                                break;
                            } else {
                                map = mapBuild;
                                this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.SetEmojiStatus), C2369R.drawable.msg_status_set));
                                break;
                            }
                            break;
                        case 4:
                            i8 = size;
                            i12 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            i10 = i;
                            if (ChatUtils.getInstance().hasArchivedChats()) {
                                i11 = i12;
                                this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.ArchivedChats), i11));
                            } else {
                                i11 = i12;
                            }
                            map = mapBuild;
                            break;
                        case 5:
                            i8 = size;
                            i12 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            i10 = i;
                            TLRPC.TL_attachMenuBots attachMenuBots = MediaDataController.getInstance(UserConfig.selectedAccount).getAttachMenuBots();
                            if (attachMenuBots != null && attachMenuBots.bots != null) {
                                for (int i19 = 0; i19 < attachMenuBots.bots.size(); i19++) {
                                    TLRPC.TL_attachMenuBot tL_attachMenuBot = (TLRPC.TL_attachMenuBot) attachMenuBots.bots.get(i19);
                                    if (tL_attachMenuBot.show_in_side_menu) {
                                        this.items.add(new Item(tL_attachMenuBot));
                                    }
                                }
                            }
                            i11 = i12;
                            map = mapBuild;
                            break;
                        case 6:
                            i8 = size;
                            i12 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            i10 = i;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.NewGroup), i10));
                            i11 = i12;
                            map = mapBuild;
                            break;
                        case 7:
                            i8 = size;
                            i13 = i;
                            i14 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.NewSecretChat), i2));
                            i11 = i14;
                            i10 = i13;
                            map = mapBuild;
                            break;
                        case 8:
                            i8 = size;
                            i13 = i;
                            i14 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.NewChannel), i3));
                            i11 = i14;
                            i10 = i13;
                            map = mapBuild;
                            break;
                        case 9:
                            i8 = size;
                            i13 = i;
                            i14 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.Contacts), i4));
                            i11 = i14;
                            i10 = i13;
                            map = mapBuild;
                            break;
                        case 10:
                            i8 = size;
                            i13 = i;
                            i14 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.Calls), i5));
                            i11 = i14;
                            i10 = i13;
                            map = mapBuild;
                            break;
                        case 11:
                            i8 = size;
                            i13 = i;
                            i14 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.SavedMessages), i6));
                            i11 = i14;
                            i10 = i13;
                            map = mapBuild;
                            break;
                        case 12:
                            i8 = size;
                            i13 = i;
                            i14 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.Settings), i7));
                            i11 = i14;
                            i10 = i13;
                            map = mapBuild;
                            break;
                        case 13:
                            i8 = size;
                            i13 = i;
                            i14 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.Plugins), C2369R.drawable.msg_plugins));
                            if (!menuItemsForLocation.isEmpty() && ExteraConfig.drawerLayout.contains(Integer.valueOf(ExteraConfig.DrawerItem.PLUGINS.f145id))) {
                                for (final int i20 = 0; i20 < menuItemsForLocation.size(); i20++) {
                                    int i21 = menuItemsForLocation.get(i20).iconResId;
                                    String str = menuItemsForLocation.get(i20).text;
                                    if (str != null) {
                                        int i22 = i20 + 103;
                                        if (i21 == 0) {
                                            i21 = C2369R.drawable.msg_plugins;
                                        }
                                        Item item = new Item(i22, str, i21);
                                        item.onClick(new View.OnClickListener() { // from class: org.telegram.ui.Adapters.DrawerLayoutAdapter$$ExternalSyntheticLambda1
                                            @Override // android.view.View.OnClickListener
                                            public final void onClick(View view) {
                                                DrawerLayoutAdapter.$r8$lambda$A9mwtuzXzTyWicPkTyTScIUw9Rk(menuItemsForLocation, i20, mapBuild, view);
                                            }
                                        });
                                        this.items.add(item);
                                    }
                                }
                            }
                            i11 = i14;
                            i10 = i13;
                            map = mapBuild;
                            break;
                        case 14:
                            i8 = size;
                            i13 = i;
                            i14 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.BrowserSettingsTitle), C2369R.drawable.msg2_language));
                            i11 = i14;
                            i10 = i13;
                            map = mapBuild;
                            break;
                        case 15:
                            i8 = size;
                            i13 = i;
                            i14 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.AuthAnotherClient), i17));
                            i11 = i14;
                            i10 = i13;
                            map = mapBuild;
                            break;
                        case 16:
                            i8 = size;
                            i13 = i;
                            i14 = i16;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            ArrayList arrayList3 = this.items;
                            int iIntValue = num.intValue();
                            if (AyuGhostController.getInstance(UserConfig.selectedAccount).isGhostModeActive()) {
                                string = LocaleController.getString(C2369R.string.DisableGhostMode);
                            } else {
                                string = LocaleController.getString(C2369R.string.EnableGhostMode);
                            }
                            arrayList3.add(new Item(iIntValue, string, C2369R.drawable.ayu_ghost).onLongClick(new View.OnClickListener() { // from class: org.telegram.ui.Adapters.DrawerLayoutAdapter$$ExternalSyntheticLambda2
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    DrawerLayoutAdapter.$r8$lambda$8nm03nSd9webPuPjvlvwse6Eazg(view);
                                }
                            }));
                            i11 = i14;
                            i10 = i13;
                            map = mapBuild;
                            break;
                        case 17:
                            i8 = size;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            i14 = i16;
                            i13 = i;
                            this.items.add(new Item(num.intValue(), LocaleController.getString(C2369R.string.KillApp), ExteraConfig.iconPack == 2 ? C2369R.drawable.msg_block_remix : C2369R.drawable.msg_disable).onLongClick(new View.OnClickListener() { // from class: org.telegram.ui.Adapters.DrawerLayoutAdapter$$ExternalSyntheticLambda3
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view) {
                                    AyuUtils.restartApplication();
                                }
                            }));
                            i11 = i14;
                            i10 = i13;
                            map = mapBuild;
                            break;
                        default:
                            i8 = size;
                            map = mapBuild;
                            i9 = i18;
                            userConfig = userConfig2;
                            arrayList = arrayList2;
                            i10 = i;
                            i11 = i16;
                            break;
                    }
                    i16 = i11;
                    i = i10;
                    size = i8;
                    i18 = i9;
                    userConfig2 = userConfig;
                    arrayList2 = arrayList;
                    mapBuild = map;
                }
            }
        }
    }

    public static /* synthetic */ int $r8$lambda$DPSN6neiw4rCZmjHk8KNUA1aKYA(Integer num, Integer num2) {
        long j = UserConfig.getInstance(num.intValue()).loginTime;
        long j2 = UserConfig.getInstance(num2.intValue()).loginTime;
        if (j > j2) {
            return 1;
        }
        return j < j2 ? -1 : 0;
    }

    /* renamed from: org.telegram.ui.Adapters.DrawerLayoutAdapter$2 */
    static /* synthetic */ class C25952 {
        static final /* synthetic */ int[] $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem;

        static {
            int[] iArr = new int[ExteraConfig.DrawerItem.values().length];
            $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem = iArr;
            try {
                iArr[ExteraConfig.DrawerItem.DIVIDER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.PROFILE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.STATUS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.ARCHIVE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.BOTS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.NEW_GROUP.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.NEW_SECRET.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.NEW_CHANNEL.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.CONTACTS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.CALLS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.SAVED.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.SETTINGS.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.PLUGINS.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.BROWSER.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.QR.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.GHOST_MODE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                $SwitchMap$com$exteragram$messenger$ExteraConfig$DrawerItem[ExteraConfig.DrawerItem.KILL_APP.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$A9mwtuzXzTyWicPkTyTScIUw9Rk(List list, int i, Map map, View view) {
        try {
            ((MenuItemRecord) list.get(i)).onClickCallback.call(map);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    public static /* synthetic */ void $r8$lambda$8nm03nSd9webPuPjvlvwse6Eazg(View view) {
        BaseFragment lastFragment = LaunchActivity.getLastFragment();
        if (lastFragment != null) {
            lastFragment.presentFragment(new GhostModePreferencesActivity());
        }
    }

    public boolean click(View view, int i) {
        Item item;
        View.OnClickListener onClickListener;
        int accountRowsCount = i - 2;
        if (this.accountsShown) {
            accountRowsCount -= getAccountRowsCount();
        }
        if (accountRowsCount < 0 || accountRowsCount >= this.items.size() || (item = (Item) this.items.get(accountRowsCount)) == null || (onClickListener = item.listener) == null) {
            return false;
        }
        onClickListener.onClick(view);
        return true;
    }

    public boolean longClick(View view, int i) {
        Item item;
        View.OnClickListener onClickListener;
        int accountRowsCount = i - 2;
        if (this.accountsShown) {
            accountRowsCount -= getAccountRowsCount();
        }
        if (accountRowsCount < 0 || accountRowsCount >= this.items.size() || (item = (Item) this.items.get(accountRowsCount)) == null || (onClickListener = item.longClickListener) == null) {
            return false;
        }
        onClickListener.onClick(view);
        return true;
    }

    public int getId(int i) {
        Item item;
        int accountRowsCount = i - 2;
        if (this.accountsShown) {
            accountRowsCount -= getAccountRowsCount();
        }
        if (accountRowsCount < 0 || accountRowsCount >= this.items.size() || (item = (Item) this.items.get(accountRowsCount)) == null) {
            return -1;
        }
        return item.f1787id;
    }

    public int getFirstAccountPosition() {
        return !this.accountsShown ? -1 : 2;
    }

    public int getLastAccountPosition() {
        if (this.accountsShown) {
            return this.accountNumbers.size() + 1;
        }
        return -1;
    }

    public TLRPC.TL_attachMenuBot getAttachMenuBot(int i) {
        Item item;
        int accountRowsCount = i - 2;
        if (this.accountsShown) {
            accountRowsCount -= getAccountRowsCount();
        }
        if (accountRowsCount < 0 || accountRowsCount >= this.items.size() || (item = (Item) this.items.get(accountRowsCount)) == null) {
            return null;
        }
        return item.bot;
    }

    public static class Item {
        TLRPC.TL_attachMenuBot bot;
        public boolean error;
        public int icon;

        /* renamed from: id */
        public int f1787id;
        View.OnClickListener listener;
        View.OnClickListener longClickListener;
        public CharSequence text;

        public Item(int i, CharSequence charSequence, int i2) {
            this.icon = i2;
            this.f1787id = i;
            this.text = charSequence;
        }

        public Item(TLRPC.TL_attachMenuBot tL_attachMenuBot) {
            this.bot = tL_attachMenuBot;
            this.f1787id = (int) ((tL_attachMenuBot.bot_id >> 16) + 100);
        }

        public void bind(DrawerActionCell drawerActionCell) {
            TLRPC.TL_attachMenuBot tL_attachMenuBot = this.bot;
            if (tL_attachMenuBot != null) {
                drawerActionCell.setBot(tL_attachMenuBot);
                drawerActionCell.getImageView().setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_menuItemIcon), PorterDuff.Mode.SRC_IN));
            } else {
                drawerActionCell.setTextAndIcon(this.f1787id, this.text, this.icon);
                if (this.f1787id == ExteraConfig.DrawerItem.KILL_APP.f145id) {
                    drawerActionCell.getImageView().setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_menuItemIcon), PorterDuff.Mode.SRC_IN));
                }
            }
            drawerActionCell.setError(this.error);
        }

        @Keep
        public Item onClick(View.OnClickListener onClickListener) {
            this.listener = onClickListener;
            return this;
        }

        @Keep
        public Item onLongClick(View.OnClickListener onClickListener) {
            this.longClickListener = onClickListener;
            return this;
        }

        @Keep
        public Item withError() {
            this.error = true;
            return this;
        }
    }
}
