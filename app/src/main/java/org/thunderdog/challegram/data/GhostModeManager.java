package org.thunderdog.challegram.data;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GhostModeManager {
    
    private static final GhostModeManager INSTANCE = new GhostModeManager();
    
    // Preference Keys
    private static final String PREF_FILE = "ghost_mode_settings";
    
    // Ghost Essentials
    private static final String KEY_GHOST_ENABLED = "ghost_mode_enabled";
    private static final String KEY_DONT_READ = "dont_read";
    private static final String KEY_DONT_TYPE = "dont_type";
    private static final String KEY_DONT_ONLINE = "dont_online";
    private static final String KEY_OFFLINE_AUTO = "offline_auto";
    
    // Extra
    private static final String KEY_READ_ON_INTERACT = "read_on_interact";
    private static final String KEY_SCHEDULE_MESSAGES = "schedule_messages";
    private static final String KEY_SEND_NO_SOUND = "send_no_sound";
    
    // Spy Essentials
    private static final String KEY_SAVE_BOTS = "save_bots"; // Save in Bot Dialogs
    private static final String KEY_SAVE_READ_DATE = "save_read_date"; // Placeholder for UI
    private static final String KEY_SAVE_LAST_SEEN = "save_last_seen"; // Placeholder for UI
    private static final String KEY_SAVE_ATTACHMENTS = "save_attachments"; // Placeholder for UI

    private SharedPreferences prefs;
    private final Map<Long, Integer> chatUnreadOffsets = new ConcurrentHashMap<>();
    
    public static GhostModeManager getInstance() { return INSTANCE; }
    
    public void init(Context context) {
        if (context == null) return;
        this.prefs = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
    }
    
    // --- Ghost Essentials ---
    public boolean isGhostModeEnabled() { return prefs != null && prefs.getBoolean(KEY_GHOST_ENABLED, false); }
    public void setGhostModeEnabled(boolean v) { if (prefs != null) prefs.edit().putBoolean(KEY_GHOST_ENABLED, v).apply(); }
    
    public boolean isDontReadEnabled() { return prefs != null && prefs.getBoolean(KEY_DONT_READ, true); }
    public void setDontReadEnabled(boolean v) { if (prefs != null) prefs.edit().putBoolean(KEY_DONT_READ, v).apply(); }
    
    public boolean isDontTypeEnabled() { return prefs != null && prefs.getBoolean(KEY_DONT_TYPE, true); }
    public void setDontTypeEnabled(boolean v) { if (prefs != null) prefs.edit().putBoolean(KEY_DONT_TYPE, v).apply(); }
    
    public boolean isDontOnlineEnabled() { return prefs != null && prefs.getBoolean(KEY_DONT_ONLINE, false); }
    public void setDontOnlineEnabled(boolean v) { if (prefs != null) prefs.edit().putBoolean(KEY_DONT_ONLINE, v).apply(); }
    
    public boolean isOfflineAutoEnabled() { return prefs != null && prefs.getBoolean(KEY_OFFLINE_AUTO, true); }
    public void setOfflineAutoEnabled(boolean v) { if (prefs != null) prefs.edit().putBoolean(KEY_OFFLINE_AUTO, v).apply(); }
    
    // --- Extra ---
    public boolean isReadOnInteractEnabled() { return prefs != null && prefs.getBoolean(KEY_READ_ON_INTERACT, true); }
    public void setReadOnInteractEnabled(boolean v) { if (prefs != null) prefs.edit().putBoolean(KEY_READ_ON_INTERACT, v).apply(); }

    public boolean isScheduleMessagesEnabled() { return prefs != null && prefs.getBoolean(KEY_SCHEDULE_MESSAGES, false); }
    public void setScheduleMessagesEnabled(boolean v) { if (prefs != null) prefs.edit().putBoolean(KEY_SCHEDULE_MESSAGES, v).apply(); }
    
    public boolean isSendNoSoundEnabled() { return prefs != null && prefs.getBoolean(KEY_SEND_NO_SOUND, false); }
    public void setSendNoSoundEnabled(boolean v) { if (prefs != null) prefs.edit().putBoolean(KEY_SEND_NO_SOUND, v).apply(); }

    // --- Spy Essentials ---
    public boolean isSaveBotsEnabled() { return prefs != null && prefs.getBoolean(KEY_SAVE_BOTS, false); }
    public void setSaveBotsEnabled(boolean v) { if (prefs != null) prefs.edit().putBoolean(KEY_SAVE_BOTS, v).apply(); }
    
    // Logic Hooks
    public boolean shouldBlockReadReceipt() { return isGhostModeEnabled() && isDontReadEnabled(); }
    public boolean shouldBlockTyping() { return isGhostModeEnabled() && isDontTypeEnabled(); }
    public boolean shouldHideOnline() { return isGhostModeEnabled() && isDontOnlineEnabled(); }
    public boolean shouldGoOfflineAuto() { return isGhostModeEnabled() && isOfflineAutoEnabled(); }
    public boolean shouldReadOnInteract() { return isGhostModeEnabled() && isReadOnInteractEnabled(); }
    
    // Local Read Logic
    public void markChatLocallyRead(long chatId, int count) { if (count > 0) chatUnreadOffsets.put(chatId, count); }
    public int getChatUnreadOffset(long chatId) { Integer i = chatUnreadOffsets.get(chatId); return i != null ? i : 0; }
    public void clearLocallyRead(long chatId) { chatUnreadOffsets.remove(chatId); }

    // Drawer Logic
    public static final String KEY_DRAWER_CONTACTS = "drawer_contacts";
    public static final String KEY_DRAWER_CALLS = "drawer_calls";
    public static final String KEY_DRAWER_SAVED_MESSAGES = "drawer_saved_messages";
    public static final String KEY_DRAWER_SETTINGS = "drawer_settings";
    public static final String KEY_DRAWER_KAIMOD = "drawer_kaimod";
    public static final String KEY_DRAWER_INVITE = "drawer_invite";
    public static final String KEY_DRAWER_PROXY = "drawer_proxy";
    public static final String KEY_DRAWER_HELP = "drawer_help";
    public static final String KEY_DRAWER_NIGHT_MODE = "drawer_night_mode";
    public static final String KEY_DRAWER_FEATURE_TOGGLES = "drawer_feature_toggles";
    public static final String KEY_DRAWER_DEBUG_LOGS = "drawer_debug_logs";

    public boolean isDrawerItemVisible(String key) {
        if (prefs == null) return true;
        if (key.equals(KEY_DRAWER_FEATURE_TOGGLES) || key.equals(KEY_DRAWER_DEBUG_LOGS)) return prefs.getBoolean(key, false);
        return prefs.getBoolean(key, true);
    }
    public void setDrawerItemVisible(String key, boolean visible) {
        if (prefs != null) prefs.edit().putBoolean(key, visible).apply();
    }
}
