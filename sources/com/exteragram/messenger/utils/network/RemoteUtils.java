package com.exteragram.messenger.utils.network;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.exteragram.messenger.badges.BadgesController;
import com.exteragram.messenger.utils.AppUtils;
import com.exteragram.messenger.utils.ChatUtils;
import com.radolyn.ayugram.utils.remote.RemoteFetcher;
import com.radolyn.ayugram.utils.remote.RemoteHttp;
import com.radolyn.ayugram.utils.remote.RemoteTelegram;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes.dex */
public abstract class RemoteUtils {
    private static RemoteFetcher fetcher;
    private static long lastInitTime;
    public static SharedPreferences sharedPreferences;

    public static void initCached() {
        if (sharedPreferences == null) {
            sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("exteraremoteconfig", 0);
        }
    }

    public static void init() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - lastInitTime < 5000) {
            return;
        }
        lastInitTime = jCurrentTimeMillis;
        initCached();
        loadConfig();
    }

    private static void loadConfig() {
        getMessages(new Utilities.Callback2() { // from class: com.exteragram.messenger.utils.network.RemoteUtils$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                RemoteUtils.$r8$lambda$LmfFRuJdEdEVAug9vbi59pl5km0((TLRPC.messages_Messages) obj, (TLRPC.TL_error) obj2);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$LmfFRuJdEdEVAug9vbi59pl5km0(TLRPC.messages_Messages messages_messages, TLRPC.TL_error tL_error) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        if (tL_error == null && messages_messages != null) {
            editorEdit.putLong("last_successful_fetch", System.currentTimeMillis());
            editorEdit.putBoolean("last_attempt_failed", false);
            editorEdit.apply();
            HashSet hashSet = new HashSet();
            HashSet hashSet2 = new HashSet();
            HashSet hashSet3 = new HashSet();
            ArrayList arrayList = messages_messages.messages;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                TLRPC.Message message = (TLRPC.Message) obj;
                if (message instanceof TLRPC.TL_message) {
                    if (message.message.startsWith("remote_config")) {
                        String[] strArrSplit = message.message.split("\n");
                        if (strArrSplit.length > 1) {
                            for (String str : strArrSplit) {
                                String[] strArrSplit2 = str.split("=", 2);
                                if (strArrSplit2.length == 2) {
                                    String strTrim = strArrSplit2[0].trim();
                                    String strTrim2 = strArrSplit2[1].trim();
                                    if (!strTrim2.equals("null")) {
                                        updateValue(strTrim, strTrim2);
                                        hashSet.add(strTrim);
                                    }
                                }
                            }
                        }
                    } else if (message.message.startsWith("supporter_channels")) {
                        hashSet3.addAll(Arrays.asList(message.message.substring(18).trim().split(",\\s*")));
                        hashSet.add("supporter_channels");
                    } else if (message.message.startsWith("supporters")) {
                        hashSet2.addAll(Arrays.asList(message.message.substring(10).trim().split(",\\s*")));
                        hashSet.add("supporters");
                    }
                }
            }
            removeOldPreferences(hashSet);
            if (!hashSet3.isEmpty()) {
                addSetToPreferences(hashSet3, "supporter_channels");
            }
            if (!hashSet2.isEmpty()) {
                addSetToPreferences(hashSet2, "supporters");
            }
            BadgesController.INSTANCE.invalidateCustomBadgesCache();
            return;
        }
        editorEdit.putBoolean("last_attempt_failed", true);
        editorEdit.apply();
        checkAndHandleFallback();
    }

    private static void checkAndHandleFallback() {
        if ("telegram".equals(getStringConfigValue("rc_transport", "telegram"))) {
            boolean z = sharedPreferences.getBoolean("last_attempt_failed", false);
            long j = sharedPreferences.getLong("last_successful_fetch", 0L);
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (j == 0) {
                SharedPreferences.Editor editorEdit = sharedPreferences.edit();
                editorEdit.putLong("last_successful_fetch", jCurrentTimeMillis);
                editorEdit.apply();
            } else {
                if (!z || jCurrentTimeMillis - j < 1209600000) {
                    return;
                }
                FileLog.m1157d("Telegram rc transport failed for 2 weeks, falling back to HTTP");
                SharedPreferences.Editor editorEdit2 = sharedPreferences.edit();
                editorEdit2.putString("rc_transport", "http");
                editorEdit2.putBoolean("last_attempt_failed", false);
                editorEdit2.apply();
                loadConfig();
            }
        }
    }

    private static void addSetToPreferences(Set set, String str) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putStringSet(str, set);
        editorEdit.apply();
    }

    private static void removeOldPreferences(Set set) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        for (String str : sharedPreferences.getAll().keySet()) {
            if (!set.contains(str)) {
                editorEdit.remove(str);
            }
        }
        editorEdit.apply();
    }

    private static void updateValue(String str, String str2) {
        if (areValuesEqual(sharedPreferences.getAll().get(str), parseConfigValue(str2))) {
            return;
        }
        saveToPreferences(str, str2);
    }

    private static boolean areValuesEqual(Object obj, Object obj2) {
        if (obj == null || obj2 == null) {
            return obj == obj2;
        }
        return obj.equals(obj2);
    }

    private static void saveToPreferences(String str, String str2) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        saveConfigValueToPreferences(editorEdit, str, parseConfigValue(str2));
        editorEdit.apply();
    }

    private static Object parseConfigValue(String str) {
        if (str.matches("-?\\d+")) {
            try {
                return Long.valueOf(Long.parseLong(str));
            } catch (NumberFormatException unused) {
                return Float.valueOf(Float.parseFloat(str));
            }
        }
        if (str.matches("-?\\d+(\\.\\d+)")) {
            return Float.valueOf(Float.parseFloat(str));
        }
        if (str.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (str.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (str.startsWith("{") && str.endsWith("}")) {
            if (str.substring(1, str.length() - 1).isEmpty()) {
                return new StringListWrapper(str);
            }
            return new StringListWrapper(str);
        }
        if (!str.startsWith("[") || !str.endsWith("]")) {
            return str;
        }
        String strSubstring = str.substring(1, str.length() - 1);
        if (strSubstring.isEmpty()) {
            return new HashSet();
        }
        return new HashSet(Arrays.asList(strSubstring.split(",\\s*")));
    }

    private static void saveConfigValueToPreferences(SharedPreferences.Editor editor, String str, Object obj) {
        if (obj instanceof Long) {
            editor.putLong(str, ((Long) obj).longValue());
            return;
        }
        if (obj instanceof Float) {
            editor.putFloat(str, ((Float) obj).floatValue());
            return;
        }
        if (obj instanceof Boolean) {
            editor.putBoolean(str, ((Boolean) obj).booleanValue());
            return;
        }
        if (obj instanceof StringListWrapper) {
            editor.putString(str, ((StringListWrapper) obj).getValue());
        } else if (obj instanceof Set) {
            editor.putStringSet(str, (Set) obj);
        } else if (obj instanceof String) {
            editor.putString(str, (String) obj);
        }
    }

    public static synchronized void getMessages(Utilities.Callback2 callback2) {
        try {
            String stringConfigValue = getStringConfigValue("rc_transport", "telegram");
            if (stringConfigValue.equals("telegram")) {
                if (!(fetcher instanceof RemoteTelegram)) {
                    fetcher = new RemoteTelegram();
                }
            } else if (stringConfigValue.equals("http")) {
                if (!(fetcher instanceof RemoteHttp)) {
                    fetcher = new RemoteHttp();
                }
            } else if (!(fetcher instanceof RemoteHttp)) {
                fetcher = new RemoteHttp();
            }
            fetcher.fetch(callback2);
        } catch (Throwable th) {
            throw th;
        }
    }

    public static void getUpdateMessages(final Utilities.Callback2 callback2) {
        final Long longConfigValue = getLongConfigValue("updates_id", -2180718299L);
        String stringConfigValue = getStringConfigValue("updates_username", "UFhsu4hOFEufes73fkEeuw");
        if (longConfigValue.longValue() == 0 || TextUtils.isEmpty(stringConfigValue)) {
            callback2.run(null, null);
            return;
        }
        final AccountInstance accountInstance = AccountInstance.getInstance(UserConfig.selectedAccount);
        final TLRPC.TL_messages_getHistory tL_messages_getHistory = new TLRPC.TL_messages_getHistory();
        tL_messages_getHistory.peer = accountInstance.getMessagesController().getInputPeer(longConfigValue.longValue());
        tL_messages_getHistory.offset_id = 0;
        tL_messages_getHistory.limit = 75;
        final Runnable runnable = new Runnable() { // from class: com.exteragram.messenger.utils.network.RemoteUtils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                accountInstance.getConnectionsManager().sendRequest(tL_messages_getHistory, new RequestDelegate() { // from class: com.exteragram.messenger.utils.network.RemoteUtils$$ExternalSyntheticLambda3
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        RemoteUtils.$r8$lambda$yclt159qwMPaoiycpDC15gXzJRg(callback2, tLObject, tL_error);
                    }
                });
            }
        };
        if (tL_messages_getHistory.peer.access_hash != 0) {
            AndroidUtilities.runOnUIThread(runnable);
        } else {
            ChatUtils.getInstance().resolveChannel(stringConfigValue, new Utilities.Callback() { // from class: com.exteragram.messenger.utils.network.RemoteUtils$$ExternalSyntheticLambda2
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    RemoteUtils.$r8$lambda$ZlUmwD4Xex3OHVFg3sKw5aEbzWg(longConfigValue, tL_messages_getHistory, runnable, (TLRPC.Chat) obj);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$yclt159qwMPaoiycpDC15gXzJRg(Utilities.Callback2 callback2, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tL_error != null || tLObject == null) {
            callback2.run(null, tL_error);
        } else {
            callback2.run((TLRPC.messages_Messages) tLObject, null);
        }
    }

    public static /* synthetic */ void $r8$lambda$ZlUmwD4Xex3OHVFg3sKw5aEbzWg(Long l, TLRPC.TL_messages_getHistory tL_messages_getHistory, Runnable runnable, TLRPC.Chat chat) {
        if (chat == null || chat.f1571id != l.longValue()) {
            return;
        }
        TLRPC.TL_inputPeerChannel tL_inputPeerChannel = new TLRPC.TL_inputPeerChannel();
        tL_messages_getHistory.peer = tL_inputPeerChannel;
        tL_inputPeerChannel.channel_id = chat.f1571id;
        tL_inputPeerChannel.access_hash = chat.access_hash;
        AndroidUtilities.runOnUIThread(runnable);
    }

    public static Integer getIntConfigValue(String str, int i) {
        Object obj;
        try {
            obj = sharedPreferences.getAll().get(str);
        } catch (Exception e) {
            AppUtils.log("Error getting int config value for key: " + str, e);
        }
        if (obj instanceof String) {
            return Integer.valueOf(Integer.parseInt((String) obj));
        }
        if (obj instanceof Long) {
            return Integer.valueOf(((Long) obj).intValue());
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        }
        return Integer.valueOf(i);
    }

    public static Long getLongConfigValue(String str, long j) {
        Object obj;
        try {
            obj = sharedPreferences.getAll().get(str);
        } catch (Exception e) {
            AppUtils.log("Error getting value for key: " + str, e);
        }
        if (obj instanceof String) {
            return Long.valueOf(Long.parseLong((String) obj));
        }
        if (obj instanceof Long) {
            return (Long) obj;
        }
        return Long.valueOf(j);
    }

    public static Float getFloatConfigValue(String str, float f) {
        Object obj;
        try {
            obj = sharedPreferences.getAll().get(str);
        } catch (Exception e) {
            AppUtils.log("Error getting value for key: " + str, e);
        }
        if (obj instanceof String) {
            return Float.valueOf(Float.parseFloat((String) obj));
        }
        if (obj instanceof Float) {
            return (Float) obj;
        }
        if (obj instanceof Long) {
            return Float.valueOf(((Long) obj).floatValue());
        }
        if (obj instanceof Integer) {
            return Float.valueOf(((Integer) obj).floatValue());
        }
        return Float.valueOf(f);
    }

    public static Boolean getBooleanConfigValue(String str, boolean z) {
        Object obj;
        try {
            obj = sharedPreferences.getAll().get(str);
        } catch (Exception e) {
            AppUtils.log("Error getting value for key: " + str, e);
        }
        if (obj instanceof String) {
            return Boolean.valueOf(Boolean.parseBoolean((String) obj));
        }
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        return Boolean.valueOf(z);
    }

    public static Set getStringSetConfigValue(String str, Set set) {
        try {
            Object obj = sharedPreferences.getAll().get(str);
            if (obj instanceof Set) {
                return (Set) obj;
            }
            return obj instanceof String ? new HashSet(Arrays.asList(((String) obj).split(",\\s*"))) : set;
        } catch (Exception e) {
            AppUtils.log("Error getting value for key: " + str, e);
            return set;
        }
    }

    public static ArrayList getStringListConfigValue(String str, ArrayList arrayList) {
        try {
            Object obj = sharedPreferences.getAll().get(str);
            if (obj instanceof String) {
                String str2 = (String) obj;
                String strSubstring = str2.substring(1, str2.length() - 1);
                if (strSubstring.isEmpty()) {
                    return new ArrayList();
                }
                return new ArrayList(Arrays.asList(strSubstring.split(",\\s*")));
            }
            Log.e("REMOTECONFIG", "Unexpected type for config value: " + obj);
            return arrayList;
        } catch (Exception e) {
            Log.e("REMOTECONFIG", "Error getting StringArray config value for key: " + str, e);
            return arrayList;
        }
    }

    public static String getStringConfigValue(String str, String str2) {
        try {
            Object obj = sharedPreferences.getAll().get(str);
            return obj != null ? String.valueOf(obj) : str2;
        } catch (Exception e) {
            AppUtils.log("Error getting value for key: " + str, e);
            return str2;
        }
    }

    /* loaded from: classes3.dex */
    private static class StringListWrapper {
        private final String value;

        public StringListWrapper(String str) {
            this.value = str;
        }

        public String getValue() {
            return this.value;
        }
    }
}
