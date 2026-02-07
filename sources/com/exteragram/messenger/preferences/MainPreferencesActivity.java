package com.exteragram.messenger.preferences;

import android.content.SharedPreferences;
import android.view.View;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.p009ui.PluginsActivity;
import com.exteragram.messenger.utils.system.VibratorUtils;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.p015ui.AlertUtils;
import java.util.ArrayList;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.browser.Browser;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.LaunchActivity;

/* loaded from: classes.dex */
public class MainPreferencesActivity extends BasePreferencesActivity {
    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected boolean hasHeaderCell() {
        return true;
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected boolean hasWhiteActionBar() {
        return true;
    }

    public enum PreferenceItem {
        HEADER_CELL,
        GENERAL_CATEGORY,
        APPEARANCE_CATEGORY,
        CHATS_CATEGORY,
        PLUGINS_CATEGORY,
        OTHER_CATEGORY,
        CHANNEL,
        CHAT,
        CROWDIN,
        WEBSITE;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.Preferences);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asHeaderSettingsCell(Integer.valueOf(PreferenceItem.HEADER_CELL.getId()), this.actionBar.getOccupyStatusBar()).showDivider(false).setTransparent(true));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Categories)));
        arrayList.add(UItem.asButton(PreferenceItem.GENERAL_CATEGORY.getId(), C2369R.drawable.msg_media, LocaleController.getString(C2369R.string.General)).setSearchable(this).setLinkAlias("general", this));
        arrayList.add(UItem.asButton(PreferenceItem.APPEARANCE_CATEGORY.getId(), C2369R.drawable.msg_theme, LocaleController.getString(C2369R.string.Appearance)).setSearchable(this).setLinkAlias("appearance", this));
        arrayList.add(UItem.asButton(PreferenceItem.CHATS_CATEGORY.getId(), C2369R.drawable.msg_discussion, LocaleController.getString(C2369R.string.SearchAllChatsShort)).setSearchable(this).setLinkAlias("chats", this));
        if (PluginsController.isPluginEngineSupported()) {
            arrayList.add(UItem.asButton(PreferenceItem.PLUGINS_CATEGORY.getId(), C2369R.drawable.msg_plugins, LocaleController.getString(C2369R.string.Plugins)).setSearchable(this).setLinkAlias(PluginsConstants.PLUGINS, this));
        }
        arrayList.add(UItem.asButton(PreferenceItem.OTHER_CATEGORY.getId(), C2369R.drawable.msg_fave, LocaleController.getString(C2369R.string.LocalOther)).setSearchable(this).setLinkAlias("other", this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Links)));
        arrayList.add(UItem.asButton(PreferenceItem.CHANNEL.getId(), C2369R.drawable.msg_channel, LocaleController.getString(C2369R.string.ProfileChannel), "@exteraGram").setSearchable(this).setLinkAlias("channel", this));
        arrayList.add(UItem.asButton(PreferenceItem.CHAT.getId(), C2369R.drawable.msg_groups, LocaleController.getString(C2369R.string.SearchAllChatsShort), "@exteraChat").setSearchable(this).setLinkAlias("chat", this));
        arrayList.add(UItem.asButton(PreferenceItem.CROWDIN.getId(), C2369R.drawable.msg_translate, LocaleController.getString(C2369R.string.Crowdin), "Crowdin").setSearchable(this).setLinkAlias("crowdin", this));
        arrayList.add(UItem.asButton(PreferenceItem.WEBSITE.getId(), C2369R.drawable.msg_language, LocaleController.getString(C2369R.string.Website), "exteraGram.app").showDivider(false).setSearchable(this).setLinkAlias("website", this));
        arrayList.add(UItem.asShadow());
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        if (i2 <= 0 || i2 > PreferenceItem.values().length) {
            return;
        }
        switch (C08891.f191x1a61f1d8[PreferenceItem.values()[uItem.f2017id - 1].ordinal()]) {
            case 1:
                if (!BuildVars.PM_BUILD) {
                    ((LaunchActivity) getParentActivity()).checkAppUpdate(true);
                    break;
                }
                break;
            case 2:
                presentFragment(new GeneralPreferencesActivity());
                break;
            case 3:
                presentFragment(new AppearancePreferencesActivity());
                break;
            case 4:
                presentFragment(new ChatsPreferencesActivity());
                break;
            case 5:
                presentFragment(new PluginsActivity());
                break;
            case 6:
                presentFragment(new OtherPreferencesActivity());
                break;
            case 7:
                showNoticeBeforeOpeningChat("exteraGram");
                break;
            case 8:
                showNoticeBeforeOpeningChat("exteraChat");
                break;
            case 9:
                Browser.openUrl(getParentActivity(), "https://crowdin.com/project/exteralocales");
                break;
            case 10:
                Browser.openUrl(getParentActivity(), "https://exteraGram.app");
                break;
        }
    }

    /* renamed from: com.exteragram.messenger.preferences.MainPreferencesActivity$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C08891 {

        /* renamed from: $SwitchMap$com$exteragram$messenger$preferences$MainPreferencesActivity$PreferenceItem */
        static final /* synthetic */ int[] f191x1a61f1d8;

        static {
            int[] iArr = new int[PreferenceItem.values().length];
            f191x1a61f1d8 = iArr;
            try {
                iArr[PreferenceItem.HEADER_CELL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f191x1a61f1d8[PreferenceItem.GENERAL_CATEGORY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f191x1a61f1d8[PreferenceItem.APPEARANCE_CATEGORY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f191x1a61f1d8[PreferenceItem.CHATS_CATEGORY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f191x1a61f1d8[PreferenceItem.PLUGINS_CATEGORY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f191x1a61f1d8[PreferenceItem.OTHER_CATEGORY.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f191x1a61f1d8[PreferenceItem.CHANNEL.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f191x1a61f1d8[PreferenceItem.CHAT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f191x1a61f1d8[PreferenceItem.CROWDIN.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f191x1a61f1d8[PreferenceItem.WEBSITE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected boolean onLongClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        if (i2 <= 0 || i2 > PreferenceItem.values().length) {
            return false;
        }
        if (PreferenceItem.values()[uItem.f2017id - 1] == PreferenceItem.HEADER_CELL) {
            SharedPreferences.Editor editor = ExteraConfig.editor;
            boolean z = !ExteraConfig.useSystemIconShape;
            ExteraConfig.useSystemIconShape = z;
            editor.putBoolean("useSystemIconShape", z).apply();
            view.performHapticFeedback(VibratorUtils.getType(3), 1);
            view.invalidate();
            return true;
        }
        return super.onLongClick(uItem, view, i, f, f2);
    }

    private void showNoticeBeforeOpeningChat(String str) {
        if (AyuConfig.sawExteraChatsAlert) {
            getMessagesController().openByUserName(str, this, 1);
        } else {
            AlertUtils.showExteraChatsAlert(this, str);
        }
    }
}
