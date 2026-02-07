package com.radolyn.ayugram.preferences;

import android.view.View;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.utils.system.VibratorUtils;
import java.util.ArrayList;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.browser.Browser;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.LaunchActivity;

/* loaded from: classes.dex */
public class AyuMainPreferencesActivity extends BasePreferencesActivity {
    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected boolean hasHeaderCell() {
        return true;
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected boolean hasWhiteActionBar() {
        return true;
    }

    private enum PreferenceItem {
        HEADER_CELL,
        GHOST_MODE_CATEGORY,
        SPY_CATEGORY,
        FILTERS_CATEGORY,
        CUSTOMIZATION_CATEGORY,
        CHANNEL,
        CHAT,
        CROWDIN,
        DOCS;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.AyuPreferences);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asHeaderSettingsCell(Integer.valueOf(PreferenceItem.HEADER_CELL.getId()), this.actionBar.getOccupyStatusBar()).showDivider(false).setChecked(true));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Categories)));
        arrayList.add(UItem.asButton(PreferenceItem.GHOST_MODE_CATEGORY.getId(), C2369R.drawable.ayu_ghost, LocaleController.getString(C2369R.string.CategoryGhostMode)).setSearchable(this).setLinkAlias(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019302828463488550L), this));
        arrayList.add(UItem.asButton(PreferenceItem.SPY_CATEGORY.getId(), C2369R.drawable.msg_bots, LocaleController.getString(C2369R.string.CategorySpy)).setSearchable(this).setLinkAlias(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019302871413161510L), this));
        arrayList.add(UItem.asButton(PreferenceItem.FILTERS_CATEGORY.getId(), C2369R.drawable.menu_tag_filter, LocaleController.getString(C2369R.string.CategoryFilters)).setSearchable(this).setLinkAlias(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019302888593030694L), this));
        arrayList.add(UItem.asButton(PreferenceItem.CUSTOMIZATION_CATEGORY.getId(), C2369R.drawable.msg_theme, LocaleController.getString(C2369R.string.CategoryCustomization)).setSearchable(this).setLinkAlias(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019302922952769062L), this));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Links)));
        arrayList.add(UItem.asButton(PreferenceItem.CHANNEL.getId(), C2369R.drawable.msg_channel, LocaleController.getString(C2369R.string.ProfileChannel), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019302983082311206L)));
        arrayList.add(UItem.asButton(PreferenceItem.CHAT.getId(), C2369R.drawable.msg_groups, LocaleController.getString(C2369R.string.SearchAllChatsShort), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303021737016870L)));
        arrayList.add(UItem.asButton(PreferenceItem.CROWDIN.getId(), C2369R.drawable.msg_translate, LocaleController.getString(C2369R.string.Crowdin), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303077571591718L)));
        arrayList.add(UItem.asButton(PreferenceItem.DOCS.getId(), C2369R.drawable.msg_language, LocaleController.getString(C2369R.string.DocsText), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303111931330086L)).showDivider(false));
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2;
        if (uItem != null && (i2 = uItem.f2017id) > 0 && i2 <= PreferenceItem.values().length) {
            switch (C14751.f400x956b1f45[PreferenceItem.values()[uItem.f2017id - 1].ordinal()]) {
                case 1:
                    if (!BuildVars.PM_BUILD) {
                        ((LaunchActivity) getParentActivity()).checkAppUpdate(true);
                        break;
                    }
                    break;
                case 2:
                    presentFragment(new GhostModePreferencesActivity());
                    break;
                case 3:
                    presentFragment(new SpyPreferencesActivity());
                    break;
                case 4:
                    presentFragment(new FiltersPreferencesActivity());
                    break;
                case 5:
                    presentFragment(new CustomizationPreferencesActivity());
                    break;
                case 6:
                    MessagesController.getInstance(this.currentAccount).openByUserName(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303163470937638L), this, 1);
                    break;
                case 7:
                    MessagesController.getInstance(this.currentAccount).openByUserName(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303197830676006L), this, 1);
                    break;
                case 8:
                    Browser.openUrl(getParentActivity(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303249370283558L));
                    break;
                case 9:
                    Browser.openUrl(getParentActivity(), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019303378219302438L));
                    break;
            }
        }
    }

    /* renamed from: com.radolyn.ayugram.preferences.AyuMainPreferencesActivity$1 */
    /* loaded from: classes4.dex */
    static /* synthetic */ class C14751 {

        /* renamed from: $SwitchMap$com$radolyn$ayugram$preferences$AyuMainPreferencesActivity$PreferenceItem */
        static final /* synthetic */ int[] f400x956b1f45;

        static {
            int[] iArr = new int[PreferenceItem.values().length];
            f400x956b1f45 = iArr;
            try {
                iArr[PreferenceItem.HEADER_CELL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f400x956b1f45[PreferenceItem.GHOST_MODE_CATEGORY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f400x956b1f45[PreferenceItem.SPY_CATEGORY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f400x956b1f45[PreferenceItem.FILTERS_CATEGORY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f400x956b1f45[PreferenceItem.CUSTOMIZATION_CATEGORY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f400x956b1f45[PreferenceItem.CHANNEL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f400x956b1f45[PreferenceItem.CHAT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f400x956b1f45[PreferenceItem.CROWDIN.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f400x956b1f45[PreferenceItem.DOCS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
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
            presentFragment(new DebugPreferencesActivity());
            view.performHapticFeedback(VibratorUtils.getType(3), 1);
            return true;
        }
        return super.onLongClick(uItem, view, i, f, f2);
    }
}
