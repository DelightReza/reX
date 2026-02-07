package com.exteragram.messenger.preferences;

import android.content.Context;
import android.os.Parcelable;
import android.view.View;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.icons.p007ui.IconPacksActivity;
import com.exteragram.messenger.preferences.components.AvatarCornersPreviewCell;
import com.exteragram.messenger.preferences.components.ChatListPreviewCell;
import com.exteragram.messenger.preferences.components.FabShapeCell;
import com.exteragram.messenger.preferences.components.FoldersPreviewCell;
import com.exteragram.messenger.utils.p011ui.PopupUtils;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;

/* loaded from: classes.dex */
public class AppearancePreferencesActivity extends BasePreferencesActivity {
    private AvatarCornersPreviewCell avatarCornersPreviewCell;
    private ChatListPreviewCell chatListPreviewCell;
    private FabShapeCell fabShapeCell;
    private FoldersPreviewCell foldersPreviewCell;
    private Parcelable recyclerViewState;
    private static final CharSequence[] styles = {LocaleController.getString(C2369R.string.Default), LocaleController.getString(C2369R.string.TabStyleRounded), LocaleController.getString(C2369R.string.TabStyleTextOnly), LocaleController.getString(C2369R.string.TabStyleChips), LocaleController.getString(C2369R.string.TabStylePills)};
    private static final CharSequence[] titles = {LocaleController.getString(C2369R.string.exteraAppName), LocaleController.getString(C2369R.string.ActionBarTitleUsername), LocaleController.getString(C2369R.string.ActionBarTitleName), LocaleController.getString(C2369R.string.FilterChats)};
    private static final CharSequence[] tabIcons = {LocaleController.getString(C2369R.string.TabTitleStyleTextWithIcons), LocaleController.getString(C2369R.string.TabTitleStyleTextOnly), LocaleController.getString(C2369R.string.TabTitleStyleIconsOnly)};
    private static final CharSequence[] tabletMode = {LocaleController.getString(C2369R.string.DistanceUnitsAutomatic), LocaleController.getString(C2369R.string.PasswordOn), LocaleController.getString(C2369R.string.PasswordOff)};
    private static final CharSequence[] dividerTypes = {LocaleController.getString(C2369R.string.Hide), LocaleController.getString(C2369R.string.DividerTypeLine), LocaleController.getString(C2369R.string.DividerTypeSegments)};

    public enum AppearanceItem {
        AVATAR_CORNERS_PREVIEW,
        SINGLE_CORNER_RADIUS,
        CHAT_LIST_PREVIEW,
        ACTION_BAR_TITLE,
        HIDE_STORIES,
        HIDE_ACTION_BAR_STATUS,
        CENTER_TITLE,
        SENDER_MINI_AVATARS,
        FOLDERS_PREVIEW,
        TAB_TITLE,
        TAB_STYLE,
        TAB_COUNTER,
        HIDE_ALL_CHATS,
        DRAWER_SETTINGS,
        ICON_PACKS,
        FAB_SHAPE,
        MD3_CONTAINERS,
        SEPARATE_HEADERS,
        DIVIDER_TYPE,
        NEW_SWITCH_STYLE,
        REMOVE_MESSAGE_TAIL,
        TABLET_MODE,
        USE_SYSTEM_FONTS,
        USE_SYSTEM_EMOJI,
        GOOEY_AVATAR_ANIMATION,
        CUSTOM_THEMES,
        FORCE_SNOW,
        SPRING_ANIMATIONS,
        FORCE_BLUR,
        GLARE_ON_ELEMENTS;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.avatarCornersPreviewCell = new AvatarCornersPreviewCell(context, this.parentLayout);
        this.chatListPreviewCell = new ChatListPreviewCell(context);
        this.foldersPreviewCell = new FoldersPreviewCell(context);
        this.fabShapeCell = new FabShapeCell(context) { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity.1
            @Override // com.exteragram.messenger.preferences.components.FabShapeCell
            protected void rebuildFragments() {
                ((BaseFragment) AppearancePreferencesActivity.this).parentLayout.rebuildFragments(0);
            }
        };
        return super.createView(context);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.Appearance);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asCustom(AppearanceItem.AVATAR_CORNERS_PREVIEW.getId(), this.avatarCornersPreviewCell).setLinkAlias("avatarCorners", this));
        arrayList.add(UItem.asCheck(AppearanceItem.SINGLE_CORNER_RADIUS.getId(), LocaleController.getString(C2369R.string.SingleCornerRadius)).setChecked(ExteraConfig.singleCornerRadius).setSearchable(this).setLinkAlias("singleCornerRadius", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.SingleCornerRadiusInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.ListOfChats)));
        arrayList.add(UItem.asCustom(AppearanceItem.CHAT_LIST_PREVIEW.getId(), this.chatListPreviewCell));
        arrayList.add(UItem.asButton(AppearanceItem.ACTION_BAR_TITLE.getId(), LocaleController.getString(C2369R.string.ActionBarTitle), titles[ExteraConfig.titleText]).setSearchable(this).setLinkAlias("actionBarTitle", this));
        arrayList.add(UItem.asCheck(AppearanceItem.HIDE_STORIES.getId(), LocaleController.getString(C2369R.string.HideStories)).setChecked(ExteraConfig.hideStories).setSearchable(this).setLinkAlias("hideStories", this));
        if (getUserConfig().isPremium()) {
            arrayList.add(UItem.asCheck(AppearanceItem.HIDE_ACTION_BAR_STATUS.getId(), LocaleController.getString(C2369R.string.HideActionBarStatus)).setChecked(ExteraConfig.hideActionBarStatus).setSearchable(this).setLinkAlias("hideActionBarStatus", this));
        }
        arrayList.add(UItem.asCheck(AppearanceItem.CENTER_TITLE.getId(), LocaleController.getString(C2369R.string.CenterTitle)).setChecked(ExteraConfig.centerTitle).setSearchable(this).setLinkAlias("centerTitle", this));
        arrayList.add(UItem.asCheck(AppearanceItem.SENDER_MINI_AVATARS.getId(), LocaleController.getString(C2369R.string.SenderMiniAvatars)).setChecked(ExteraConfig.senderMiniAvatars).setSearchable(this).setLinkAlias("senderMiniAvatars", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.ListOfChatsInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Filters)));
        arrayList.add(UItem.asCustom(AppearanceItem.FOLDERS_PREVIEW.getId(), this.foldersPreviewCell));
        arrayList.add(UItem.asButton(AppearanceItem.TAB_TITLE.getId(), LocaleController.getString(C2369R.string.TabTitleStyle), tabIcons[ExteraConfig.tabIcons]).setSearchable(this).setLinkAlias("tabTitleStyle", this));
        arrayList.add(UItem.asButton(AppearanceItem.TAB_STYLE.getId(), LocaleController.getString(C2369R.string.TabStyle), styles[ExteraConfig.tabStyle]).setSearchable(this).setLinkAlias("tabStyle", this));
        arrayList.add(UItem.asCheck(AppearanceItem.TAB_COUNTER.getId(), LocaleController.getString(C2369R.string.TabCounter)).setChecked(ExteraConfig.tabCounter).setSearchable(this).setLinkAlias("tabCounter", this));
        arrayList.add(UItem.asCheck(AppearanceItem.HIDE_ALL_CHATS.getId(), LocaleController.formatString(C2369R.string.HideAllChats, LocaleController.getString(C2369R.string.FilterAllChats))).setChecked(ExteraConfig.hideAllChats).setSearchable(this).setLinkAlias("hideAllChats", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.FoldersInfo)));
        arrayList.add(UItem.asButtonWithSubtext(AppearanceItem.DRAWER_SETTINGS.getId(), C2369R.drawable.msg_list, LocaleController.getString(C2369R.string.DrawerOptions), LocaleController.getString(C2369R.string.DrawerOptionsInfo), 64, 60).setSearchable(this).setLinkAlias("drawerSettings", this));
        arrayList.add(UItem.asButtonWithSubtext(AppearanceItem.ICON_PACKS.getId(), C2369R.drawable.msg_sticker, LocaleController.getString(C2369R.string.IconPacks), LocaleController.getString(C2369R.string.IconPacksInfo), 64, 60).setSearchable(this).setLinkAlias("iconPacks", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.MaterialDesign3)));
        arrayList.add(UItem.asCustom(AppearanceItem.FAB_SHAPE.getId(), this.fabShapeCell).setLinkAlias("fabShape", this));
        arrayList.add(UItem.asButton(AppearanceItem.DIVIDER_TYPE.getId(), LocaleController.getString(C2369R.string.DividerType), dividerTypes[ExteraConfig.dividerType]).setSearchable(this).setLinkAlias("dividerType", this));
        arrayList.add(UItem.asCheck(AppearanceItem.MD3_CONTAINERS.getId(), LocaleController.getString(C2369R.string.MD3Containers)).setChecked(ExteraConfig.md3Containers).setSearchable(this).setLinkAlias("md3Containers", this));
        if (ExteraConfig.md3Containers) {
            arrayList.add(UItem.asCheck(AppearanceItem.SEPARATE_HEADERS.getId(), LocaleController.getString(C2369R.string.SeparateHeaders)).setChecked(ExteraConfig.md3SeparatedHeaders).setSearchable(this).setLinkAlias("md3SeparatedHeaders", this));
        }
        arrayList.add(UItem.asCheck(AppearanceItem.NEW_SWITCH_STYLE.getId(), LocaleController.getString(C2369R.string.NewSwitchStyle)).setChecked(ExteraConfig.newSwitchStyle).setSearchable(this).setLinkAlias("newSwitchStyle", this));
        arrayList.add(UItem.asCheck(AppearanceItem.REMOVE_MESSAGE_TAIL.getId(), LocaleController.getString(C2369R.string.RemoveMessageTail)).setChecked(ExteraConfig.removeMessageTail).setSearchable(this).setLinkAlias("removeMessageTail", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Appearance)));
        arrayList.add(UItem.asButton(AppearanceItem.TABLET_MODE.getId(), LocaleController.getString(C2369R.string.TabletMode), tabletMode[ExteraConfig.tabletMode]).setSearchable(this).setLinkAlias("tabletMode", this));
        arrayList.add(UItem.asCheck(AppearanceItem.USE_SYSTEM_FONTS.getId(), LocaleController.getString(C2369R.string.UseSystemFonts)).setChecked(ExteraConfig.useSystemFonts).setSearchable(this).setLinkAlias("useSystemFonts", this));
        arrayList.add(UItem.asCheck(AppearanceItem.USE_SYSTEM_EMOJI.getId(), LocaleController.getString(C2369R.string.UseSystemEmoji)).setChecked(SharedConfig.useSystemEmoji).setSearchable(this).setLinkAlias("useSystemEmoji", this));
        arrayList.add(UItem.asCheck(AppearanceItem.GOOEY_AVATAR_ANIMATION.getId(), LocaleController.getString(C2369R.string.GooeyAvatarAnimation)).setChecked(ExteraConfig.gooeyAvatarAnimation).setSearchable(this).setLinkAlias("gooeyAvatarAnimation", this));
        arrayList.add(UItem.asCheck(AppearanceItem.CUSTOM_THEMES.getId(), LocaleController.getString(C2369R.string.CustomChatThemes), LocaleController.getString(C2369R.string.CustomChatThemesInfo), true).setChecked(ExteraConfig.customThemes).setSearchable(this).setLinkAlias("customThemes", this));
        arrayList.add(UItem.asCheck(AppearanceItem.FORCE_SNOW.getId(), LocaleController.getString(C2369R.string.ForceSnow), LocaleController.getString(C2369R.string.ForceSnowInfo), true).setChecked(ExteraConfig.forceSnow).setSearchable(this).setLinkAlias("forceSnow", this));
        arrayList.add(UItem.asCheck(AppearanceItem.SPRING_ANIMATIONS.getId(), LocaleController.getString(C2369R.string.SpringAnimations)).setChecked(ExteraConfig.springAnimations).setSearchable(this).setLinkAlias("springAnimations", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.SpringAnimationsInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.BlurOptions)));
        arrayList.add(UItem.asCheck(AppearanceItem.GLARE_ON_ELEMENTS.getId(), LocaleController.getString(C2369R.string.GlareOnElements), LocaleController.getString(C2369R.string.GlareOnElementsInfo), true).setChecked(ExteraConfig.glareOnElements).setSearchable(this).setLinkAlias("glareOnElements", this));
        arrayList.add(UItem.asCheck(AppearanceItem.FORCE_BLUR.getId(), LocaleController.getString(C2369R.string.ForceBlur)).setChecked(ExteraConfig.forceBlur).setSearchable(this).setLinkAlias("forceBlur", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.ForceBlurInfo)));
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        if (i2 <= 0 || i2 > AppearanceItem.values().length) {
            return;
        }
        switch (C08782.f187xea9de9cc[AppearanceItem.values()[uItem.f2017id - 1].ordinal()]) {
            case 1:
                toggleBooleanSettingAndRefresh("singleCornerRadius", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda0
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.singleCornerRadius = ((Boolean) obj).booleanValue();
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 2:
                showListDialog(uItem, titles, LocaleController.getString(C2369R.string.ActionBarTitle), ExteraConfig.titleText, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda11
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$1(i3);
                    }
                });
                break;
            case 3:
                toggleBooleanSettingAndRefresh("hideStories", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda15
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.hideStories = ((Boolean) obj).booleanValue();
                    }
                });
                getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.storiesEnabledUpdate, new Object[0]);
                break;
            case 4:
                toggleBooleanSettingAndRefresh("hideActionBarStatus", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda16
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.hideActionBarStatus = ((Boolean) obj).booleanValue();
                    }
                });
                handleHideActionBarStatusChange();
                break;
            case 5:
                toggleBooleanSettingAndRefresh("centerTitle", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda17
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.centerTitle = ((Boolean) obj).booleanValue();
                    }
                });
                handleCenterTitleChange();
                break;
            case 6:
                toggleBooleanSettingAndRefresh("senderMiniAvatars", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda18
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.senderMiniAvatars = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 7:
                showListDialog(uItem, tabIcons, LocaleController.getString(C2369R.string.TabTitleStyle), ExteraConfig.tabIcons, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda19
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$6(i3);
                    }
                });
                break;
            case 8:
                showListDialog(uItem, styles, LocaleController.getString(C2369R.string.TabStyle), ExteraConfig.tabStyle, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda20
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$7(i3);
                    }
                });
                break;
            case 9:
                toggleBooleanSettingAndRefresh("tabCounter", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda21
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.tabCounter = ((Boolean) obj).booleanValue();
                    }
                });
                handleTabCounterClick();
                break;
            case 10:
                toggleBooleanSettingAndRefresh("hideAllChats", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda22
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.hideAllChats = ((Boolean) obj).booleanValue();
                    }
                });
                handleHideAllChatsClick();
                break;
            case 11:
                presentFragment(new DrawerPreferencesActivity());
                break;
            case 12:
                presentFragment(new IconPacksActivity());
                break;
            case 13:
                showListDialog(uItem, tabletMode, LocaleController.getString(C2369R.string.TabletMode), ExteraConfig.tabletMode, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda1
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$10(i3);
                    }
                });
                break;
            case 14:
                toggleBooleanSettingAndRefresh("md3Containers", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda2
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$onClick$11((Boolean) obj);
                    }
                });
                break;
            case 15:
                toggleBooleanSettingAndRefresh("md3SeparatedHeaders", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda3
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.md3SeparatedHeaders = ((Boolean) obj).booleanValue();
                    }
                });
                this.parentLayout.rebuildFragments(0);
                break;
            case 16:
                CharSequence[] charSequenceArr = dividerTypes;
                if (!ExteraConfig.md3Containers) {
                    CharSequence[] charSequenceArr2 = {charSequenceArr[0], charSequenceArr[1]};
                    if (ExteraConfig.dividerType == 2) {
                        ExteraConfig.dividerType = 1;
                        changeIntSetting("dividerType", 1);
                        handleDividerTypeChange();
                    }
                    charSequenceArr = charSequenceArr2;
                }
                showListDialog(uItem, charSequenceArr, LocaleController.getString(C2369R.string.DividerType), ExteraConfig.dividerType, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda4
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$13(i3);
                    }
                });
                break;
            case 17:
                toggleBooleanSettingAndRefresh("useSystemFonts", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda5
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.useSystemFonts = ((Boolean) obj).booleanValue();
                    }
                });
                handleUseSystemFontsClick();
                break;
            case 18:
                handleUseSystemEmojiClick(uItem);
                break;
            case 19:
                toggleBooleanSettingAndRefresh("newSwitchStyle", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda6
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.newSwitchStyle = ((Boolean) obj).booleanValue();
                    }
                });
                rebuildListWithStateRestore();
                break;
            case 20:
                toggleBooleanSettingAndRefresh("removeMessageTail", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda7
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.removeMessageTail = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 21:
                toggleBooleanSettingAndRefresh("gooeyAvatarAnimation", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda8
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.gooeyAvatarAnimation = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 22:
                toggleBooleanSettingAndRefresh("customThemes", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda9
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.customThemes = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 23:
                toggleBooleanSettingAndRefresh("forceSnow", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda10
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.forceSnow = ((Boolean) obj).booleanValue();
                    }
                });
                showRestartBulletin();
                break;
            case 24:
                toggleBooleanSettingAndRefresh("springAnimations", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda12
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.springAnimations = ((Boolean) obj).booleanValue();
                    }
                });
                handleSpringAnimationsClick();
                break;
            case 25:
                toggleBooleanSettingAndRefresh("glareOnElements", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda13
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.glareOnElements = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 26:
                toggleBooleanSettingAndRefresh("forceBlur", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.AppearancePreferencesActivity$$ExternalSyntheticLambda14
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.forceBlur = ((Boolean) obj).booleanValue();
                    }
                });
                handleForceBlurChange();
                break;
        }
    }

    /* renamed from: com.exteragram.messenger.preferences.AppearancePreferencesActivity$2 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C08782 {

        /* renamed from: $SwitchMap$com$exteragram$messenger$preferences$AppearancePreferencesActivity$AppearanceItem */
        static final /* synthetic */ int[] f187xea9de9cc;

        static {
            int[] iArr = new int[AppearanceItem.values().length];
            f187xea9de9cc = iArr;
            try {
                iArr[AppearanceItem.SINGLE_CORNER_RADIUS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f187xea9de9cc[AppearanceItem.ACTION_BAR_TITLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f187xea9de9cc[AppearanceItem.HIDE_STORIES.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f187xea9de9cc[AppearanceItem.HIDE_ACTION_BAR_STATUS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f187xea9de9cc[AppearanceItem.CENTER_TITLE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f187xea9de9cc[AppearanceItem.SENDER_MINI_AVATARS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f187xea9de9cc[AppearanceItem.TAB_TITLE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f187xea9de9cc[AppearanceItem.TAB_STYLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f187xea9de9cc[AppearanceItem.TAB_COUNTER.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f187xea9de9cc[AppearanceItem.HIDE_ALL_CHATS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f187xea9de9cc[AppearanceItem.DRAWER_SETTINGS.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f187xea9de9cc[AppearanceItem.ICON_PACKS.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f187xea9de9cc[AppearanceItem.TABLET_MODE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f187xea9de9cc[AppearanceItem.MD3_CONTAINERS.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f187xea9de9cc[AppearanceItem.SEPARATE_HEADERS.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
            try {
                f187xea9de9cc[AppearanceItem.DIVIDER_TYPE.ordinal()] = 16;
            } catch (NoSuchFieldError unused16) {
            }
            try {
                f187xea9de9cc[AppearanceItem.USE_SYSTEM_FONTS.ordinal()] = 17;
            } catch (NoSuchFieldError unused17) {
            }
            try {
                f187xea9de9cc[AppearanceItem.USE_SYSTEM_EMOJI.ordinal()] = 18;
            } catch (NoSuchFieldError unused18) {
            }
            try {
                f187xea9de9cc[AppearanceItem.NEW_SWITCH_STYLE.ordinal()] = 19;
            } catch (NoSuchFieldError unused19) {
            }
            try {
                f187xea9de9cc[AppearanceItem.REMOVE_MESSAGE_TAIL.ordinal()] = 20;
            } catch (NoSuchFieldError unused20) {
            }
            try {
                f187xea9de9cc[AppearanceItem.GOOEY_AVATAR_ANIMATION.ordinal()] = 21;
            } catch (NoSuchFieldError unused21) {
            }
            try {
                f187xea9de9cc[AppearanceItem.CUSTOM_THEMES.ordinal()] = 22;
            } catch (NoSuchFieldError unused22) {
            }
            try {
                f187xea9de9cc[AppearanceItem.FORCE_SNOW.ordinal()] = 23;
            } catch (NoSuchFieldError unused23) {
            }
            try {
                f187xea9de9cc[AppearanceItem.SPRING_ANIMATIONS.ordinal()] = 24;
            } catch (NoSuchFieldError unused24) {
            }
            try {
                f187xea9de9cc[AppearanceItem.GLARE_ON_ELEMENTS.ordinal()] = 25;
            } catch (NoSuchFieldError unused25) {
            }
            try {
                f187xea9de9cc[AppearanceItem.FORCE_BLUR.ordinal()] = 26;
            } catch (NoSuchFieldError unused26) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$1(int i) {
        ExteraConfig.titleText = i;
        changeIntSetting("titleText", i);
        handleActionBarTitleClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$6(int i) {
        ExteraConfig.tabIcons = i;
        changeIntSetting("tabIcons", i);
        handleTabTitleClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$7(int i) {
        ExteraConfig.tabStyle = i;
        changeIntSetting("tabStyle", i);
        handleTabStyleClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$10(int i) {
        ExteraConfig.tabletMode = i;
        changeIntSetting("tabletMode", i);
        showRestartBulletin();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$11(Boolean bool) {
        ExteraConfig.md3Containers = bool.booleanValue();
        this.listView.adapter.setUseSectionStyle(bool.booleanValue());
        if (!bool.booleanValue() && ExteraConfig.dividerType == 2) {
            ExteraConfig.dividerType = 1;
            changeIntSetting("dividerType", 1);
            handleDividerTypeChange();
        }
        this.parentLayout.rebuildFragments(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$13(int i) {
        ExteraConfig.dividerType = i;
        changeIntSetting("dividerType", i);
        handleDividerTypeChange();
    }

    private void handleHideActionBarStatusChange() {
        this.chatListPreviewCell.updateStatus(true);
        this.parentLayout.rebuildFragments(0);
    }

    private void handleCenterTitleChange() {
        this.chatListPreviewCell.updateCenteredTitle(true);
        showRestartBulletin();
    }

    private void handleActionBarTitleClick() {
        this.chatListPreviewCell.updateTitle(true);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.currentUserPremiumStatusChanged, new Object[0]);
    }

    private void handleTabTitleClick() {
        this.foldersPreviewCell.updateTabIcons(true);
        this.foldersPreviewCell.updateTabTitle(true);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogFiltersUpdated, new Object[0]);
    }

    private void handleTabStyleClick() {
        this.foldersPreviewCell.updateTabStyle(true);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogFiltersUpdated, new Object[0]);
    }

    private void handleTabCounterClick() {
        this.foldersPreviewCell.updateTabCounter(true);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogFiltersUpdated, new Object[0]);
    }

    private void handleHideAllChatsClick() {
        this.foldersPreviewCell.updateAllChatsTabName(true);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogFiltersUpdated, new Object[0]);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
    }

    private void handleUseSystemFontsClick() {
        AndroidUtilities.clearTypefaceCache();
        rebuildListWithStateRestore();
    }

    private void rebuildListWithStateRestore() {
        if (this.listView.getLayoutManager() != null) {
            this.recyclerViewState = this.listView.getLayoutManager().onSaveInstanceState();
        }
        this.parentLayout.rebuildFragments(1);
        if (this.listView.getLayoutManager() != null) {
            this.listView.getLayoutManager().onRestoreInstanceState(this.recyclerViewState);
        }
    }

    private void handleDividerTypeChange() {
        Theme.applyCommonTheme();
        this.avatarCornersPreviewCell.invalidate();
        this.chatListPreviewCell.invalidate();
        this.foldersPreviewCell.invalidate();
        this.fabShapeCell.invalidate();
        this.parentLayout.rebuildFragments(0);
    }

    private void handleSpringAnimationsClick() {
        if (ExteraConfig.springAnimations) {
            MessagesController.getGlobalMainSettings().edit().putBoolean("view_animations", true).apply();
            SharedConfig.setAnimationsEnabled(true);
        }
    }

    private void handleForceBlurChange() {
        if (SharedConfig.chatBlurEnabled() || !ExteraConfig.forceBlur) {
            return;
        }
        SharedConfig.toggleChatBlur();
    }

    private void handleUseSystemEmojiClick(UItem uItem) {
        SharedConfig.toggleUseSystemEmoji();
        uItem.setChecked(!SharedConfig.useSystemEmoji);
        this.parentLayout.rebuildFragments(0);
        this.listView.adapter.update(true);
    }
}
