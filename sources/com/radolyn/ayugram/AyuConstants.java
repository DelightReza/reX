package com.radolyn.ayugram;

import com.exteragram.messenger.backup.PreferencesUtils$$ExternalSyntheticBackport1;
import java.util.ArrayList;
import java.util.Set;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.zzz0;

/* loaded from: classes.dex */
public abstract class AyuConstants {
    public static final int FIX_FORWARD;
    public static final int FORCE_MESSAGES_UPDATE;
    public static final int HISTORY_FLUSHED_NOTIFICATION;
    public static final int MESSAGES_DELETED_NOTIFICATION;
    private static int OPTIONS;
    public static final int OPTION_DEBUG_SEND_SCREENSHOT;
    public static final int OPTION_DELETED_HISTORY;
    public static final int OPTION_GHOST_READ_EXCLUSION;
    public static final int OPTION_GHOST_TYPING_EXCLUSION;
    public static final int OPTION_SWITCH_FILTERING;
    public static final int OPTION_VIEW_FILTERS;
    public static final int PEEK_ONLINE_ITEM;
    public static final int PEER_RESOLVED_NOTIFICATION;
    public static final int SHADOW_BAN_ITEM;
    public static final int UPDATE_CHAT_RESTRICTION;
    private static int notificationId;
    public static final String DEFAULT_JUMPSCARES_CHANNEL = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019349373024075302L);
    public static final String UPDATES_CHANNEL_USERNAME = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019349437448584742L);
    public static final int OPTION_CLEAR_DELETED = 80;
    public static String AYU_DATABASE = zzz0.m1232d(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019349536232832550L));
    public static String APP_NAME = zzz0.m1232d(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019349652196949542L));
    public static String BUILD_STORE_PACKAGE = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019349755276164646L);
    public static String BUILD_ORIGINAL_PACKAGE = zzz0.m1232d(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019349759571131942L));
    public static int MAX_CACHE_SIZE_300_MB = -10;
    public static final ArrayList DEFAULT_JUMPSCARES_KEYS = new ArrayList() { // from class: com.radolyn.ayugram.AyuConstants.1
        {
            add(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019310009648807462L));
            add(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019310048303513126L));
            add(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019310082663251494L));
            add(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019310108433055270L));
            add(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019310134202859046L));
            add(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019310159972662822L));
            add(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019310190037433894L));
        }
    };
    public static final ArrayList DEFAULT_JUMPSCARES_VALUES = new ArrayList() { // from class: com.radolyn.ayugram.AyuConstants.2
        {
            add(String.valueOf(5));
            add(String.valueOf(6));
            add(String.valueOf(7));
            add(String.valueOf(8));
            add(String.valueOf(9));
            add(String.valueOf(10));
            add(String.valueOf(11));
        }
    };
    public static final Set ALLOWED_PASTE_SERVICES = PreferencesUtils$$ExternalSyntheticBackport1.m189m(new Object[]{Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019350103168515622L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019350150413155878L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019350266377272870L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019350322211847718L)});
    public static final int FIX_SCHEDULED_BAR = 6969;

    static {
        int i = 80 + 1;
        OPTION_VIEW_FILTERS = i;
        OPTION_SWITCH_FILTERING = i + 1;
        PEEK_ONLINE_ITEM = i + 2;
        SHADOW_BAN_ITEM = i + 3;
        OPTION_DELETED_HISTORY = i + 4;
        OPTION_GHOST_READ_EXCLUSION = i + 5;
        OPTION_GHOST_TYPING_EXCLUSION = i + 6;
        OPTIONS = i + 8;
        OPTION_DEBUG_SEND_SCREENSHOT = i + 7;
        int i2 = 6969 + 1;
        FIX_FORWARD = i2;
        MESSAGES_DELETED_NOTIFICATION = i2 + 1;
        HISTORY_FLUSHED_NOTIFICATION = i2 + 2;
        PEER_RESOLVED_NOTIFICATION = i2 + 3;
        UPDATE_CHAT_RESTRICTION = i2 + 4;
        notificationId = i2 + 6;
        FORCE_MESSAGES_UPDATE = i2 + 5;
    }
}
