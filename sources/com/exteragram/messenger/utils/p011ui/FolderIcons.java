package com.exteragram.messenger.utils.p011ui;

import androidx.core.util.Pair;
import com.exteragram.messenger.ExteraConfig;
import java.util.LinkedHashMap;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;

/* loaded from: classes.dex */
public abstract class FolderIcons {
    public static LinkedHashMap folderIcons;

    static {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        folderIcons = linkedHashMap;
        linkedHashMap.put("🐱", Integer.valueOf(C2369R.drawable.filter_cat));
        folderIcons.put("📕", Integer.valueOf(C2369R.drawable.filter_book));
        folderIcons.put("💰", Integer.valueOf(C2369R.drawable.filter_money));
        folderIcons.put("🎮", Integer.valueOf(C2369R.drawable.filter_game));
        folderIcons.put("💡", Integer.valueOf(C2369R.drawable.filter_light));
        folderIcons.put("👌", Integer.valueOf(C2369R.drawable.filter_like));
        folderIcons.put("🎵", Integer.valueOf(C2369R.drawable.filter_note));
        folderIcons.put("🎨", Integer.valueOf(C2369R.drawable.filter_palette));
        folderIcons.put("✈", Integer.valueOf(C2369R.drawable.filter_travel));
        folderIcons.put("⚽", Integer.valueOf(C2369R.drawable.filter_sport));
        folderIcons.put("⭐", Integer.valueOf(C2369R.drawable.filter_favorite));
        folderIcons.put("🎓", Integer.valueOf(C2369R.drawable.filter_study));
        folderIcons.put("🛫", Integer.valueOf(C2369R.drawable.filter_airplane));
        folderIcons.put("👤", Integer.valueOf(C2369R.drawable.filter_private));
        folderIcons.put("👥", Integer.valueOf(C2369R.drawable.filter_group));
        folderIcons.put("💬", Integer.valueOf(C2369R.drawable.filter_all));
        folderIcons.put("✅", Integer.valueOf(C2369R.drawable.filter_unread));
        folderIcons.put("🤖", Integer.valueOf(C2369R.drawable.filter_bots));
        folderIcons.put("👑", Integer.valueOf(C2369R.drawable.filter_crown));
        folderIcons.put("🌹", Integer.valueOf(C2369R.drawable.filter_flower));
        folderIcons.put("🏠", Integer.valueOf(C2369R.drawable.filter_home));
        folderIcons.put("❤", Integer.valueOf(C2369R.drawable.filter_love));
        folderIcons.put("🎭", Integer.valueOf(C2369R.drawable.filter_mask));
        folderIcons.put("🍸", Integer.valueOf(C2369R.drawable.filter_party));
        folderIcons.put("📈", Integer.valueOf(C2369R.drawable.filter_trade));
        folderIcons.put("💼", Integer.valueOf(C2369R.drawable.filter_work));
        folderIcons.put("🔔", Integer.valueOf(C2369R.drawable.filter_unmuted));
        folderIcons.put("📢", Integer.valueOf(C2369R.drawable.filter_channels));
        folderIcons.put("📁", Integer.valueOf(C2369R.drawable.filter_custom));
        folderIcons.put("📋", Integer.valueOf(C2369R.drawable.filter_setup));
    }

    public static Pair getEmoticonFromFlags(int i) {
        String string;
        String str;
        int i2 = MessagesController.DIALOG_FILTER_FLAG_ALL_CHATS;
        int i3 = i & i2;
        if ((i3 & i2) == i2) {
            if ((MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ & i) != 0) {
                string = LocaleController.getString(C2369R.string.FilterNameUnread);
                str = "✅";
            } else if ((i & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_MUTED) != 0) {
                string = LocaleController.getString(C2369R.string.FilterNameNonMuted);
                str = "🔔";
            } else {
                string = "";
                str = "";
            }
        } else {
            int i4 = MessagesController.DIALOG_FILTER_FLAG_CONTACTS;
            if ((i3 & i4) != 0) {
                int i5 = (~i4) & i3;
                if (i5 == 0) {
                    string = LocaleController.getString(C2369R.string.FilterContacts);
                } else {
                    int i6 = MessagesController.DIALOG_FILTER_FLAG_NON_CONTACTS;
                    if ((i5 & i6) != 0 && (i5 & (~i6)) == 0) {
                        string = LocaleController.getString(C2369R.string.FilterContacts);
                    }
                    string = "";
                    str = "";
                }
                str = "👤";
            } else {
                int i7 = MessagesController.DIALOG_FILTER_FLAG_NON_CONTACTS;
                if ((i3 & i7) == 0) {
                    int i8 = MessagesController.DIALOG_FILTER_FLAG_GROUPS;
                    if ((i3 & i8) == 0) {
                        int i9 = MessagesController.DIALOG_FILTER_FLAG_BOTS;
                        if ((i3 & i9) == 0) {
                            int i10 = MessagesController.DIALOG_FILTER_FLAG_CHANNELS;
                            if ((i3 & i10) != 0 && ((~i10) & i3) == 0) {
                                string = LocaleController.getString(C2369R.string.FilterChannels);
                                str = "📢";
                            }
                        } else if (((~i9) & i3) == 0) {
                            string = LocaleController.getString(C2369R.string.FilterBots);
                            str = "🤖";
                        }
                    } else if (((~i8) & i3) == 0) {
                        string = LocaleController.getString(C2369R.string.FilterGroups);
                        str = "👥";
                    }
                } else if (((~i7) & i3) == 0) {
                    string = LocaleController.getString(C2369R.string.FilterNonContacts);
                    str = "👤";
                }
                string = "";
                str = "";
            }
        }
        return Pair.create(string, str);
    }

    public static int getIconWidth() {
        return AndroidUtilities.m1146dp(28.0f);
    }

    public static int getPadding() {
        if (ExteraConfig.tabIcons == 0) {
            return AndroidUtilities.m1146dp(6.0f);
        }
        return 0;
    }

    public static int getTotalIconWidth() {
        if (ExteraConfig.tabIcons != 1) {
            return getIconWidth() + getPadding();
        }
        return 0;
    }

    public static int getPaddingTab() {
        if (ExteraConfig.tabIcons != 2 || ExteraConfig.tabStyle >= 3) {
            return AndroidUtilities.m1146dp(32.0f);
        }
        return AndroidUtilities.m1146dp(16.0f);
    }

    public static int getTabIcon(String str) {
        Integer num;
        if (str != null && (num = (Integer) folderIcons.get(str)) != null) {
            return num.intValue();
        }
        return C2369R.drawable.filter_custom;
    }
}
