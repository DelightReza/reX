package com.radolyn.ayugram;

import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;
import com.exteragram.messenger.backup.PreferencesUtils;
import com.radolyn.ayugram.utils.fcm.CloudMessagingUtils;
import java.io.File;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.MessagesController;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes.dex */
public abstract class AyuConfig {
    public static boolean WALMode;
    private static boolean configLoaded;
    public static int deletedIcon;
    public static int deletedIconColor;
    public static boolean disableAds;
    public static boolean disableHook;
    public static boolean displayGhostStatus;
    public static SharedPreferences.Editor editor;
    public static boolean filtersEnabled;
    public static boolean forceShowDownloadButtons;
    public static boolean hideFromBlocked;
    public static boolean keepAliveService;
    public static boolean localPremium;
    public static SharedPreferences preferences;
    public static boolean probeUsingOtherAccounts;
    public static boolean regexFiltersInChats;
    public static boolean saveDeletedMessages;
    public static boolean saveForBots;
    public static boolean saveLocalOnline;
    public static boolean saveMedia;
    public static boolean saveMediaInPrivateChannels;
    public static boolean saveMediaInPrivateChats;
    public static boolean saveMediaInPrivateGroups;
    public static boolean saveMediaInPublicChannels;
    public static boolean saveMediaInPublicGroups;
    public static int saveMediaMaxCacheSize;
    public static long saveMediaOnCellularDataLimit;
    public static long saveMediaOnWiFiLimit;
    public static boolean saveMessagesHistory;
    public static boolean saveReadDate;
    public static boolean sawExteraChatsAlert;
    public static boolean sawFirstLaunchAlert;
    public static boolean sawLocalPremiumAlert;
    public static boolean sawSaveAttachmentsAlert;
    public static boolean semiTransparentDeletedMessages;
    public static boolean showScreenshot;
    public static boolean simpleQuotesAndReplies;
    private static final Object sync = new Object();
    private static final File defaultSavePath = new File(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), AyuConstants.APP_NAME), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315318228385318L));

    static {
        loadConfig();
    }

    public static void loadConfig() {
        synchronized (sync) {
            try {
                if (configLoaded) {
                    return;
                }
                SharedPreferences preferences2 = PreferencesUtils.getPreferences(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312170017357350L));
                preferences = preferences2;
                editor = preferences2.edit();
                saveDeletedMessages = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312212967030310L), true);
                saveMessagesHistory = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312298866376230L), true);
                saveForBots = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312384765722150L), true);
                saveMedia = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312436305329702L), true);
                saveMediaInPrivateChats = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312479255002662L), true);
                saveMediaInPublicChannels = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312582334217766L), false);
                saveMediaInPrivateChannels = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312694003367462L), true);
                saveMediaInPublicGroups = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312809967484454L), false);
                saveMediaInPrivateGroups = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019312913046699558L), true);
                saveMediaOnCellularDataLimit = preferences.getLong(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313020420881958L), 16777216L);
                saveMediaOnWiFiLimit = preferences.getLong(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313144974933542L), 67108864L);
                saveMediaMaxCacheSize = preferences.getInt(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313235169246758L), ConnectionsManager.DEFAULT_DATACENTER_ID);
                saveReadDate = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313329658527270L), false);
                saveLocalOnline = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313385493102118L), false);
                keepAliveService = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313454212578854L), !CloudMessagingUtils.spoofingNeeded());
                disableAds = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313527227022886L), true);
                localPremium = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313574471663142L), false);
                filtersEnabled = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313630306237990L), false);
                hideFromBlocked = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313694730747430L), false);
                regexFiltersInChats = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313763450224166L), false);
                simpleQuotesAndReplies = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313849349570086L), false);
                semiTransparentDeletedMessages = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019313948133817894L), true);
                deletedIconColor = preferences.getInt(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314081277804070L), 0);
                deletedIcon = preferences.getInt(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314154292248102L), 1);
                displayGhostStatus = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314205831855654L), false);
                WALMode = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314287436234278L), true);
                showScreenshot = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314321795972646L), false);
                forceShowDownloadButtons = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314386220482086L), false);
                probeUsingOtherAccounts = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314493594664486L), true);
                disableHook = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314596673879590L), false);
                sawFirstLaunchAlert = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314648213487142L), false);
                sawLocalPremiumAlert = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314734112833062L), false);
                sawExteraChatsAlert = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314824307146278L), false);
                sawSaveAttachmentsAlert = preferences.getBoolean(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019314910206492198L), false);
                configLoaded = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static void reloadConfig() {
        configLoaded = false;
        loadConfig();
    }

    public static boolean saveDeletedMessageFor(int i, long j) {
        if (!saveDeletedMessages) {
            return false;
        }
        TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(Math.abs(j)));
        return user == null || !user.bot || saveForBots;
    }

    public static boolean saveEditedMessageFor(int i, long j) {
        if (!saveMessagesHistory) {
            return false;
        }
        TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(Math.abs(j)));
        return user == null || !user.bot || saveForBots;
    }

    public static String getWALMode() {
        return Deobfuscator$AyuGram4A$TMessagesProj.getString(WALMode ? -2019315013285707302L : -2019315030465576486L);
    }

    public static String getSavePath() {
        return preferences.getString(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315047645445670L), defaultSavePath.getAbsolutePath());
    }

    public static File getSavePathJava() {
        return new File(getSavePath());
    }

    public static String getSavePathFolder() {
        try {
            String savePath = getSavePath();
            if (TextUtils.isEmpty(savePath)) {
                return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315163609562662L);
            }
            try {
                return new File(savePath).getName();
            } catch (Exception unused) {
                return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315240918973990L);
            }
        } catch (Exception unused2) {
            return Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019315086300151334L);
        }
    }
}
