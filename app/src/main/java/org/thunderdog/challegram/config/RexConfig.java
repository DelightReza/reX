/*
 * This file is a part of reX
 * Copyright © 2024
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.thunderdog.challegram.config;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import org.thunderdog.challegram.tool.UI;

import java.util.HashSet;
import java.util.Set;

public final class RexConfig {

  private static volatile RexConfig instance;

  private static final String PREFS_NAME = "rex_config";

  // Ghost Mode keys
  private static final String KEY_GHOST_ENABLED = "ghost_enabled";
  private static final String KEY_GHOST_OPTIONS = "ghost_options";
  private static final String KEY_LOCKED_GHOST_OPTIONS = "locked_ghost_options";
  private static final String KEY_GHOST_GO_OFFLINE = "ghost_go_offline";
  private static final String KEY_GHOST_READ_ON_INTERACT = "ghost_read_on_interact";
  private static final String KEY_GHOST_SCHEDULE_MESSAGES = "ghost_schedule_messages";
  private static final String KEY_GHOST_SEND_WITHOUT_SOUND = "ghost_send_without_sound";
  private static final String KEY_GHOST_STORY_ALERT = "ghost_story_alert";

  // Spy Mode keys (each function has its own independent toggle)
  private static final String KEY_SPY_SAVE_DELETED = "spy_save_deleted";
  private static final String KEY_SPY_SAVE_EDITS = "spy_save_edits";
  private static final String KEY_SPY_SAVE_BOT_DIALOGS = "spy_save_bot_dialogs";
  private static final String KEY_SPY_SAVE_READ_DATE = "spy_save_read_date";
  private static final String KEY_SPY_SAVE_LAST_SEEN = "spy_save_last_seen";
  private static final String KEY_SPY_SAVE_ATTACHMENTS = "spy_save_attachments";
  private static final String KEY_SPY_ATTACHMENTS_FOLDER = "spy_attachments_folder";
  private static final String KEY_SPY_MAX_FOLDER_SIZE = "spy_max_folder_size";

  // Customization keys
  private static final String KEY_CUSTOM_DISABLE_COLORFUL_REPLIES = "custom_disable_colorful_replies";
  private static final String KEY_CUSTOM_TRANSLUCENT_DELETED = "custom_translucent_deleted";
  private static final String KEY_CUSTOM_BYPASS_RESTRICTIONS = "custom_bypass_restrictions";

  // Ghost option constants
  public static final String GHOST_NO_READ = "NoRead";
  public static final String GHOST_NO_STORIES = "NoStories";
  public static final String GHOST_NO_ONLINE = "NoOnline";
  public static final String GHOST_NO_TYPING = "NoTyping";
  public static final String GHOST_GO_OFFLINE = "GoOffline";

  public static final String[] ALL_GHOST_OPTIONS = {GHOST_NO_READ, GHOST_NO_STORIES, GHOST_NO_ONLINE, GHOST_NO_TYPING};

  private final SharedPreferences prefs;

  private RexConfig (@NonNull Context context) {
    prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
  }

  @NonNull
  public static RexConfig getInstance () {
    if (instance == null) {
      synchronized (RexConfig.class) {
        if (instance == null) {
          instance = new RexConfig(UI.getAppContext());
        }
      }
    }
    return instance;
  }

  // --- Ghost Mode ---

  public boolean isGhostEnabled () {
    return prefs.getBoolean(KEY_GHOST_ENABLED, false);
  }

  public void setGhostEnabled (boolean enabled) {
    prefs.edit().putBoolean(KEY_GHOST_ENABLED, enabled).apply();
  }

  @NonNull
  public Set<String> getGhostOptions () {
    Set<String> set = prefs.getStringSet(KEY_GHOST_OPTIONS, null);
    return set != null ? new HashSet<>(set) : new HashSet<>();
  }

  public void setGhostOptions (@NonNull Set<String> options) {
    prefs.edit().putStringSet(KEY_GHOST_OPTIONS, options).apply();
  }

  public boolean isGhostOptionEnabled (@NonNull String option) {
    return isGhostEnabled() && getGhostOptions().contains(option);
  }

  public boolean isGhostOptionRawEnabled (@NonNull String option) {
    return getGhostOptions().contains(option);
  }

  public void toggleGhostOption (@NonNull String option, boolean enabled) {
    Set<String> options = new HashSet<>(getGhostOptions());
    if (enabled) {
      options.add(option);
    } else {
      options.remove(option);
    }
    setGhostOptions(options);
  }

  @NonNull
  public Set<String> getLockedGhostOptions () {
    Set<String> set = prefs.getStringSet(KEY_LOCKED_GHOST_OPTIONS, null);
    return set != null ? new HashSet<>(set) : new HashSet<>();
  }

  public void setLockedGhostOptions (@NonNull Set<String> locked) {
    prefs.edit().putStringSet(KEY_LOCKED_GHOST_OPTIONS, locked).apply();
  }

  public boolean isGhostOptionLocked (@NonNull String option) {
    return getLockedGhostOptions().contains(option);
  }

  public void toggleGhostOptionLock (@NonNull String option) {
    Set<String> locked = new HashSet<>(getLockedGhostOptions());
    if (locked.contains(option)) {
      locked.remove(option);
    } else {
      locked.add(option);
    }
    setLockedGhostOptions(locked);
  }

  public int getEnabledGhostOptionCount () {
    return getGhostOptions().size();
  }

  // Ghost additional toggles

  public boolean isGoOfflineAuto () {
    return prefs.getBoolean(KEY_GHOST_GO_OFFLINE, false);
  }

  public void setGoOfflineAuto (boolean enabled) {
    prefs.edit().putBoolean(KEY_GHOST_GO_OFFLINE, enabled).apply();
  }

  public boolean isReadOnInteract () {
    return prefs.getBoolean(KEY_GHOST_READ_ON_INTERACT, false);
  }

  public void setReadOnInteract (boolean enabled) {
    prefs.edit().putBoolean(KEY_GHOST_READ_ON_INTERACT, enabled).apply();
  }

  public boolean isScheduleMessages () {
    return prefs.getBoolean(KEY_GHOST_SCHEDULE_MESSAGES, false);
  }

  public void setScheduleMessages (boolean enabled) {
    prefs.edit().putBoolean(KEY_GHOST_SCHEDULE_MESSAGES, enabled).apply();
  }

  public boolean isSendWithoutSound () {
    return prefs.getBoolean(KEY_GHOST_SEND_WITHOUT_SOUND, false);
  }

  public void setSendWithoutSound (boolean enabled) {
    prefs.edit().putBoolean(KEY_GHOST_SEND_WITHOUT_SOUND, enabled).apply();
  }

  public boolean isStoryGhostAlert () {
    return prefs.getBoolean(KEY_GHOST_STORY_ALERT, false);
  }

  public void setStoryGhostAlert (boolean enabled) {
    prefs.edit().putBoolean(KEY_GHOST_STORY_ALERT, enabled).apply();
  }

  // --- Spy Mode (independent toggles per function) ---

  public boolean isSaveDeletedEnabled () {
    return prefs.getBoolean(KEY_SPY_SAVE_DELETED, false);
  }

  public void setSaveDeletedEnabled (boolean enabled) {
    prefs.edit().putBoolean(KEY_SPY_SAVE_DELETED, enabled).apply();
  }

  public boolean isSaveEditsEnabled () {
    return prefs.getBoolean(KEY_SPY_SAVE_EDITS, false);
  }

  public void setSaveEditsEnabled (boolean enabled) {
    prefs.edit().putBoolean(KEY_SPY_SAVE_EDITS, enabled).apply();
  }

  public boolean isSaveBotDialogsEnabled () {
    return prefs.getBoolean(KEY_SPY_SAVE_BOT_DIALOGS, false);
  }

  public void setSaveBotDialogsEnabled (boolean enabled) {
    prefs.edit().putBoolean(KEY_SPY_SAVE_BOT_DIALOGS, enabled).apply();
  }

  public boolean isSaveReadDateEnabled () {
    return prefs.getBoolean(KEY_SPY_SAVE_READ_DATE, false);
  }

  public void setSaveReadDateEnabled (boolean enabled) {
    prefs.edit().putBoolean(KEY_SPY_SAVE_READ_DATE, enabled).apply();
  }

  public boolean isSaveLastSeenEnabled () {
    return prefs.getBoolean(KEY_SPY_SAVE_LAST_SEEN, false);
  }

  public void setSaveLastSeenEnabled (boolean enabled) {
    prefs.edit().putBoolean(KEY_SPY_SAVE_LAST_SEEN, enabled).apply();
  }

  public boolean isSaveAttachmentsEnabled () {
    return prefs.getBoolean(KEY_SPY_SAVE_ATTACHMENTS, false);
  }

  public void setSaveAttachmentsEnabled (boolean enabled) {
    prefs.edit().putBoolean(KEY_SPY_SAVE_ATTACHMENTS, enabled).apply();
  }

  @NonNull
  public String getAttachmentsFolder () {
    return prefs.getString(KEY_SPY_ATTACHMENTS_FOLDER, "Saved Attachments");
  }

  public void setAttachmentsFolder (@NonNull String folder) {
    prefs.edit().putString(KEY_SPY_ATTACHMENTS_FOLDER, folder).apply();
  }

  public int getMaxFolderSizeMb () {
    return prefs.getInt(KEY_SPY_MAX_FOLDER_SIZE, 300);
  }

  public void setMaxFolderSizeMb (int sizeMb) {
    prefs.edit().putInt(KEY_SPY_MAX_FOLDER_SIZE, sizeMb).apply();
  }

  // --- Customization ---

  public boolean isDisableColorfulReplies () {
    return prefs.getBoolean(KEY_CUSTOM_DISABLE_COLORFUL_REPLIES, false);
  }

  public void setDisableColorfulReplies (boolean enabled) {
    prefs.edit().putBoolean(KEY_CUSTOM_DISABLE_COLORFUL_REPLIES, enabled).apply();
  }

  public boolean isTranslucentDeleted () {
    return prefs.getBoolean(KEY_CUSTOM_TRANSLUCENT_DELETED, false);
  }

  public void setTranslucentDeleted (boolean enabled) {
    prefs.edit().putBoolean(KEY_CUSTOM_TRANSLUCENT_DELETED, enabled).apply();
  }

  // --- Restriction Bypass ---

  public boolean isBypassRestrictionsEnabled () {
    return prefs.getBoolean(KEY_CUSTOM_BYPASS_RESTRICTIONS, false);
  }

  public void setBypassRestrictionsEnabled (boolean enabled) {
    prefs.edit().putBoolean(KEY_CUSTOM_BYPASS_RESTRICTIONS, enabled).apply();
  }
}
