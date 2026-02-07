package com.radolyn.ayugram.preferences;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.utils.p011ui.PopupUtils;
import com.radolyn.ayugram.AyuGhostConfig;
import com.radolyn.ayugram.AyuState;
import com.radolyn.ayugram.preferences.components.AccountCell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.Cells.AccountSelectCell;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes.dex */
public class GhostModePreferencesActivity extends BasePreferencesActivity {
    private AyuGhostConfig.GhostModeSettings currentSettings;
    private boolean ghostModeMenuExpanded;
    private final CharSequence[] sendWithoutSoundItems = {LocaleController.getString(C2369R.string.SendWithoutSoundByDefaultNever), LocaleController.getString(C2369R.string.SendWithoutSoundByDefaultInGhostMode), LocaleController.getString(C2369R.string.SendWithoutSoundByDefaultAlways)};
    private ActionBarMenuItem switchItem;

    private enum ItemId {
        HEADER,
        GHOST_MODE,
        DONT_READ_MESSAGES,
        DONT_READ_STORIES,
        DONT_SEND_ONLINE,
        DONT_SEND_UPLOAD,
        SEND_OFFLINE_AFTER_ONLINE,
        GHOST_LONG_TAP_DESC,
        MARK_READ_AFTER_ACTION,
        MARK_READ_DESC,
        USE_SCHEDULED,
        USE_SCHEDULED_DESC,
        SEND_WITHOUT_SOUND,
        SEND_WITHOUT_SOUND_DESC,
        SUGGEST_BEFORE_STORY,
        SUGGEST_BEFORE_STORY_DESC;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        Drawable avatarDrawable;
        TLRPC.UserProfilePhoto userProfilePhoto;
        float f;
        if (UserConfig.getActivatedAccountsCount() == 1 && !AyuGhostConfig.isGlobalOverride()) {
            AyuGhostConfig.GhostModeSettings ghostModeSettings = AyuGhostConfig.getGhostModeSettings(getUserConfig().getClientUserId());
            ghostModeSettings.userId = -1L;
            ghostModeSettings.save();
            AyuGhostConfig.setGlobalOverride(true, BulletinFactory.m1267of(this));
            AyuGhostConfig.reloadConfig();
        }
        this.currentSettings = AyuGhostConfig.getGhostModeSettings(getUserConfig().getClientUserId());
        View viewCreateView = super.createView(context);
        this.switchItem = this.actionBar.menu.addItemWithWidth(1, 0, AndroidUtilities.m1146dp(56.0f));
        final AvatarDrawable avatarDrawable2 = new AvatarDrawable();
        avatarDrawable2.setTextSize(AndroidUtilities.m1146dp(12.0f));
        final BackupImageView backupImageView = new BackupImageView(context);
        backupImageView.setRoundRadius(ExteraConfig.getAvatarCorners(36.0f));
        this.switchItem.addView(backupImageView, LayoutHelper.createFrame(36, 36, 17));
        final HashMap map = new HashMap();
        final AccountCell accountCell = new AccountCell(context);
        accountCell.setSelected(AyuGhostConfig.isGlobalOverride());
        this.switchItem.addSubItem(10, accountCell, AndroidUtilities.m1146dp(268.0f), AndroidUtilities.m1146dp(48.0f));
        map.put(10L, accountCell);
        for (int i = 0; i < 16; i++) {
            TLRPC.User currentUser = AccountInstance.getInstance(i).getUserConfig().getCurrentUser();
            if (currentUser != null) {
                AccountSelectCell accountSelectCell = new AccountSelectCell(context, false);
                accountSelectCell.setAccount(i, true);
                if (AyuGhostConfig.isGlobalOverride()) {
                    f = 268.0f;
                } else {
                    f = 268.0f;
                    boolean z = currentUser.f1734id == this.currentSettings.userId;
                    accountSelectCell.setSelected(z);
                    int i2 = i + 20;
                    this.switchItem.addSubItem(i2, accountSelectCell, AndroidUtilities.m1146dp(f), AndroidUtilities.m1146dp(48.0f));
                    map.put(Long.valueOf(i2), accountSelectCell);
                }
                accountSelectCell.setSelected(z);
                int i22 = i + 20;
                this.switchItem.addSubItem(i22, accountSelectCell, AndroidUtilities.m1146dp(f), AndroidUtilities.m1146dp(48.0f));
                map.put(Long.valueOf(i22), accountSelectCell);
            }
        }
        final TLRPC.User currentUser2 = getUserConfig().getCurrentUser();
        avatarDrawable2.setInfo(this.currentAccount, currentUser2);
        backupImageView.getImageReceiver().setCurrentAccount(this.currentAccount);
        if (currentUser2 == null || (userProfilePhoto = currentUser2.photo) == null || (avatarDrawable = userProfilePhoto.strippedBitmap) == null) {
            avatarDrawable = avatarDrawable2;
        }
        if (AyuGhostConfig.isGlobalOverride()) {
            avatarDrawable = accountCell.getAvatarDrawable();
        }
        updateImage(backupImageView, currentUser2, avatarDrawable);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: com.radolyn.ayugram.preferences.GhostModePreferencesActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i3) {
                Drawable drawable;
                if (i3 == -1) {
                    GhostModePreferencesActivity.this.lambda$onBackPressed$371();
                } else if (i3 == 10) {
                    AyuGhostConfig.setGlobalOverride(true);
                    GhostModePreferencesActivity.this.currentSettings = AyuGhostConfig.getGhostModeSettings(-1L);
                    GhostModePreferencesActivity.this.updateImage(backupImageView, currentUser2, accountCell.getAvatarDrawable());
                } else {
                    AyuGhostConfig.setGlobalOverride(false);
                    int i4 = i3 - 20;
                    TLRPC.User currentUser3 = UserConfig.getInstance(i4).getCurrentUser();
                    if (currentUser3 != null) {
                        GhostModePreferencesActivity.this.currentSettings = AyuGhostConfig.getGhostModeSettings(currentUser3.f1734id);
                        avatarDrawable2.setInfo(i4, currentUser3);
                        TLRPC.UserProfilePhoto userProfilePhoto2 = currentUser3.photo;
                        if (userProfilePhoto2 == null || (drawable = userProfilePhoto2.strippedBitmap) == null) {
                            drawable = avatarDrawable2;
                        }
                        GhostModePreferencesActivity.this.updateImage(backupImageView, currentUser3, drawable);
                    }
                }
                for (Map.Entry entry : map.entrySet()) {
                    ((FrameLayout) entry.getValue()).setSelected(((Long) entry.getKey()).longValue() == ((long) i3));
                }
                ((BasePreferencesActivity) GhostModePreferencesActivity.this).listView.adapter.update(true);
                NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
            }
        });
        if (UserConfig.getActivatedAccountsCount() == 1) {
            this.switchItem.setVisibility(8);
        }
        return viewCreateView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateImage(BackupImageView backupImageView, TLRPC.User user, Drawable drawable) {
        if (AyuGhostConfig.isGlobalOverride()) {
            backupImageView.setImageDrawable(drawable);
        } else {
            backupImageView.setImage(ImageLocation.getForUserOrChat(user, 1), "50_50", ImageLocation.getForUserOrChat(user, 2), "50_50", drawable, user);
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.GhostEssentialsHeader)));
        arrayList.add(UItem.asExteraExpandableSwitch(ItemId.GHOST_MODE.getId(), LocaleController.getString(C2369R.string.GhostModeToggle), String.format(Locale.US, "%d/5", Integer.valueOf(getGhostModeSelectedCount())), new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.GhostModePreferencesActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$fillItems$0(view);
            }
        }).setChecked(AyuGhostConfig.isGhostModeActive(this.currentSettings.userId)).setCollapsed(!this.ghostModeMenuExpanded).setSearchable(this).setLinkAlias("ghostModeToggle", this));
        if (this.ghostModeMenuExpanded) {
            arrayList.add(UItem.asRoundCheckbox(ItemId.DONT_READ_MESSAGES.getId(), LocaleController.getString(C2369R.string.DontReadMessages)).setChecked(!this.currentSettings.sendReadMessagePackets).setEnabled(!this.currentSettings.sendReadMessagePacketsLocked).pad());
            arrayList.add(UItem.asRoundCheckbox(ItemId.DONT_READ_STORIES.getId(), LocaleController.getString(C2369R.string.DontReadStories)).setChecked(!this.currentSettings.sendReadStoryPackets).setEnabled(!this.currentSettings.sendReadStoryPacketsLocked).pad());
            arrayList.add(UItem.asRoundCheckbox(ItemId.DONT_SEND_ONLINE.getId(), LocaleController.getString(C2369R.string.DontSendOnlinePackets)).setChecked(!this.currentSettings.sendOnlinePackets).setEnabled(!this.currentSettings.sendOnlinePacketsLocked).pad());
            arrayList.add(UItem.asRoundCheckbox(ItemId.DONT_SEND_UPLOAD.getId(), LocaleController.getString(C2369R.string.DontSendUploadProgress)).setChecked(!this.currentSettings.sendUploadProgress).setEnabled(!this.currentSettings.sendUploadProgressLocked).pad());
            arrayList.add(UItem.asRoundCheckbox(ItemId.SEND_OFFLINE_AFTER_ONLINE.getId(), LocaleController.getString(C2369R.string.SendOfflinePacketAfterOnline)).setChecked(this.currentSettings.sendOfflinePacketAfterOnline).setEnabled(!this.currentSettings.sendOfflinePacketAfterOnlineLocked).pad());
            arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.GhostModeOptionLongTapDescription)));
        }
        arrayList.add(UItem.asCheck(ItemId.MARK_READ_AFTER_ACTION.getId(), LocaleController.getString(C2369R.string.MarkReadAfterAction)).setChecked(this.currentSettings.markReadAfterAction).setSearchable(this).setLinkAlias("markReadAfterAction", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.MarkReadAfterActionDescription)));
        arrayList.add(UItem.asCheck(ItemId.USE_SCHEDULED.getId(), LocaleController.getString(C2369R.string.UseScheduledMessages)).setChecked(this.currentSettings.useScheduledMessages).setSearchable(this).setLinkAlias("useScheduledMessages", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.UseScheduledMessagesDescription)));
        arrayList.add(UItem.asButton(ItemId.SEND_WITHOUT_SOUND.getId(), LocaleController.getString(C2369R.string.SendWithoutSoundByDefault), this.sendWithoutSoundItems[this.currentSettings.sendWithoutSound]).setSearchable(this).setLinkAlias("sendWithoutSound", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.SendWithoutSoundByDefaultDescription)));
        arrayList.add(UItem.asCheck(ItemId.SUGGEST_BEFORE_STORY.getId(), LocaleController.getString(C2369R.string.SuggestGhostModeBeforeViewingStory)).setChecked(this.currentSettings.suggestGhostModeBeforeViewingStory).setSearchable(this).setLinkAlias("suggestGhostModeBeforeViewingStory", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.SuggestGhostModeBeforeViewingStoryDescription)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillItems$0(View view) {
        AyuGhostConfig.toggleGhostMode(this.currentSettings.userId, BulletinFactory.m1267of(this));
        this.listView.adapter.update(true);
    }

    private int getGhostModeSelectedCount() {
        AyuGhostConfig.GhostModeSettings ghostModeSettings = this.currentSettings;
        int i = !ghostModeSettings.sendReadMessagePackets ? 1 : 0;
        if (!ghostModeSettings.sendReadStoryPackets) {
            i++;
        }
        if (!ghostModeSettings.sendOnlinePackets) {
            i++;
        }
        if (!ghostModeSettings.sendUploadProgress) {
            i++;
        }
        return ghostModeSettings.sendOfflinePacketAfterOnline ? i + 1 : i;
    }

    /* JADX WARN: Type inference failed for: r9v1, types: [boolean, int] */
    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected boolean onLongClick(UItem uItem, View view, int i, float f, float f2) {
        if (uItem == null) {
            return false;
        }
        int i2 = uItem.f2017id;
        ItemId itemId = ItemId.DONT_READ_MESSAGES;
        if (i2 >= itemId.getId()) {
            int i3 = uItem.f2017id;
            ItemId itemId2 = ItemId.SEND_OFFLINE_AFTER_ONLINE;
            if (i3 <= itemId2.getId()) {
                AyuGhostConfig.GhostModeSettings ghostModeSettings = this.currentSettings;
                ?? r9 = ghostModeSettings.sendReadMessagePacketsLocked;
                int i4 = r9;
                if (ghostModeSettings.sendReadStoryPacketsLocked) {
                    i4 = r9 + 1;
                }
                int i5 = i4;
                if (ghostModeSettings.sendOnlinePacketsLocked) {
                    i5 = i4 + 1;
                }
                int i6 = i5;
                if (ghostModeSettings.sendUploadProgressLocked) {
                    i6 = i5 + 1;
                }
                int i7 = i6;
                if (ghostModeSettings.sendOfflinePacketAfterOnlineLocked) {
                    i7 = i6 + 1;
                }
                boolean z = (uItem.f2017id == itemId.getId() && !this.currentSettings.sendReadMessagePacketsLocked) || (uItem.f2017id == ItemId.DONT_READ_STORIES.getId() && !this.currentSettings.sendReadStoryPacketsLocked) || ((uItem.f2017id == ItemId.DONT_SEND_ONLINE.getId() && !this.currentSettings.sendOnlinePacketsLocked) || ((uItem.f2017id == ItemId.DONT_SEND_UPLOAD.getId() && !this.currentSettings.sendUploadProgressLocked) || (uItem.f2017id == itemId2.getId() && !this.currentSettings.sendOfflinePacketAfterOnlineLocked)));
                if (i7 == 4 && z) {
                    BotWebViewVibrationEffect.NOTIFICATION_WARNING.vibrate();
                    return false;
                }
                if (uItem.f2017id == itemId.getId()) {
                    this.currentSettings.sendReadMessagePacketsLocked = !r7.sendReadMessagePacketsLocked;
                } else if (uItem.f2017id == ItemId.DONT_READ_STORIES.getId()) {
                    this.currentSettings.sendReadStoryPacketsLocked = !r7.sendReadStoryPacketsLocked;
                } else if (uItem.f2017id == ItemId.DONT_SEND_ONLINE.getId()) {
                    this.currentSettings.sendOnlinePacketsLocked = !r7.sendOnlinePacketsLocked;
                } else if (uItem.f2017id == ItemId.DONT_SEND_UPLOAD.getId()) {
                    this.currentSettings.sendUploadProgressLocked = !r7.sendUploadProgressLocked;
                } else if (uItem.f2017id == itemId2.getId()) {
                    this.currentSettings.sendOfflinePacketAfterOnlineLocked = !r7.sendOfflinePacketAfterOnlineLocked;
                }
                this.currentSettings.save();
                this.listView.adapter.update(true);
                return true;
            }
        }
        return super.onLongClick(uItem, view, i, f, f2);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        if (uItem != null) {
            if (uItem.f2017id == ItemId.GHOST_MODE.getId()) {
                this.ghostModeMenuExpanded = !this.ghostModeMenuExpanded;
                this.listView.adapter.update(true);
                return;
            }
            if (uItem.f2017id == ItemId.MARK_READ_AFTER_ACTION.getId()) {
                AyuGhostConfig.GhostModeSettings ghostModeSettings = this.currentSettings;
                ghostModeSettings.markReadAfterAction = !ghostModeSettings.markReadAfterAction;
                ghostModeSettings.save();
                AyuGhostConfig.GhostModeSettings ghostModeSettings2 = this.currentSettings;
                if (ghostModeSettings2.markReadAfterAction && ghostModeSettings2.useScheduledMessages) {
                    ghostModeSettings2.useScheduledMessages = false;
                    ghostModeSettings2.save();
                }
                this.listView.adapter.update(true);
                return;
            }
            if (uItem.f2017id == ItemId.DONT_READ_MESSAGES.getId()) {
                AyuGhostConfig.GhostModeSettings ghostModeSettings3 = this.currentSettings;
                if (!ghostModeSettings3.sendReadMessagePacketsLocked) {
                    ghostModeSettings3.sendReadMessagePackets = !ghostModeSettings3.sendReadMessagePackets;
                    ghostModeSettings3.save();
                    this.listView.adapter.update(true);
                    return;
                }
            }
            if (uItem.f2017id == ItemId.DONT_READ_STORIES.getId()) {
                AyuGhostConfig.GhostModeSettings ghostModeSettings4 = this.currentSettings;
                if (!ghostModeSettings4.sendReadStoryPacketsLocked) {
                    ghostModeSettings4.sendReadStoryPackets = !ghostModeSettings4.sendReadStoryPackets;
                    ghostModeSettings4.save();
                    this.listView.adapter.update(true);
                    return;
                }
            }
            if (uItem.f2017id == ItemId.DONT_SEND_ONLINE.getId()) {
                AyuGhostConfig.GhostModeSettings ghostModeSettings5 = this.currentSettings;
                if (!ghostModeSettings5.sendOnlinePacketsLocked) {
                    ghostModeSettings5.sendOnlinePackets = !ghostModeSettings5.sendOnlinePackets;
                    ghostModeSettings5.save();
                    this.listView.adapter.update(true);
                    return;
                }
            }
            if (uItem.f2017id == ItemId.DONT_SEND_UPLOAD.getId()) {
                AyuGhostConfig.GhostModeSettings ghostModeSettings6 = this.currentSettings;
                if (!ghostModeSettings6.sendUploadProgressLocked) {
                    ghostModeSettings6.sendUploadProgress = !ghostModeSettings6.sendUploadProgress;
                    ghostModeSettings6.save();
                    this.listView.adapter.update(true);
                    return;
                }
            }
            if (uItem.f2017id == ItemId.SEND_OFFLINE_AFTER_ONLINE.getId()) {
                AyuGhostConfig.GhostModeSettings ghostModeSettings7 = this.currentSettings;
                if (!ghostModeSettings7.sendOfflinePacketAfterOnlineLocked) {
                    ghostModeSettings7.sendOfflinePacketAfterOnline = !ghostModeSettings7.sendOfflinePacketAfterOnline;
                    ghostModeSettings7.save();
                    this.listView.adapter.update(true);
                    return;
                }
            }
            if (uItem.f2017id == ItemId.USE_SCHEDULED.getId()) {
                AyuGhostConfig.GhostModeSettings ghostModeSettings8 = this.currentSettings;
                ghostModeSettings8.useScheduledMessages = !ghostModeSettings8.useScheduledMessages;
                ghostModeSettings8.save();
                AyuState.setAutomaticallyScheduled(false, -1);
                AyuGhostConfig.GhostModeSettings ghostModeSettings9 = this.currentSettings;
                if (ghostModeSettings9.useScheduledMessages && ghostModeSettings9.markReadAfterAction) {
                    ghostModeSettings9.markReadAfterAction = false;
                    ghostModeSettings9.save();
                }
                this.listView.adapter.update(true);
                return;
            }
            if (uItem.f2017id == ItemId.SEND_WITHOUT_SOUND.getId()) {
                if (getParentActivity() != null) {
                    showListDialog(uItem, this.sendWithoutSoundItems, LocaleController.getString(C2369R.string.SendWithoutSoundByDefault), this.currentSettings.sendWithoutSound, new PopupUtils.OnItemClickListener() { // from class: com.radolyn.ayugram.preferences.GhostModePreferencesActivity$$ExternalSyntheticLambda0
                        @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                        public final void onClick(int i2) {
                            this.f$0.lambda$onClick$1(i2);
                        }
                    });
                }
            } else if (uItem.f2017id == ItemId.SUGGEST_BEFORE_STORY.getId()) {
                AyuGhostConfig.GhostModeSettings ghostModeSettings10 = this.currentSettings;
                ghostModeSettings10.suggestGhostModeBeforeViewingStory = !ghostModeSettings10.suggestGhostModeBeforeViewingStory;
                ghostModeSettings10.save();
                this.listView.adapter.update(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$1(int i) {
        AyuGhostConfig.GhostModeSettings ghostModeSettings = this.currentSettings;
        if (ghostModeSettings.sendWithoutSound == i) {
            return;
        }
        ghostModeSettings.sendWithoutSound = i;
        ghostModeSettings.save();
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.CategoryGhostMode);
    }
}
