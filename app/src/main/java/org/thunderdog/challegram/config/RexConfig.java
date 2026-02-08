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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class RexConfig {

  private static volatile RexConfig instance;

  private static final String PREFS_NAME = "rex_config";

  // Ghost Mode keys
  private static final String KEY_GHOST_ENABLED = "ghost_enabled";
  private static final String KEY_GHOST_OPTIONS = "ghost_options";
  private static final String KEY_LOCKED_GHOST_OPTIONS = "locked_ghost_options";

  // Spy Mode keys
  private static final String KEY_SPY_ENABLED = "spy_enabled";
  private static final String KEY_SPY_OPTIONS = "spy_options";

  // Ghost option constants
  public static final String GHOST_NO_READ = "NoRead";
  public static final String GHOST_NO_STORIES = "NoStories";
  public static final String GHOST_NO_ONLINE = "NoOnline";
  public static final String GHOST_NO_TYPING = "NoTyping";

  // Spy option constants
  public static final String SPY_SAVE_DELETED = "SaveDeleted";
  public static final String SPY_SAVE_EDITS = "SaveEdits";
  public static final String SPY_SAVE_ATTACHMENTS = "SaveAttachments";

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
    return prefs.getStringSet(KEY_GHOST_OPTIONS, Collections.emptySet());
  }

  public void setGhostOptions (@NonNull Set<String> options) {
    prefs.edit().putStringSet(KEY_GHOST_OPTIONS, options).apply();
  }

  public boolean isGhostOptionEnabled (@NonNull String option) {
    return isGhostEnabled() && getGhostOptions().contains(option);
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
    return prefs.getStringSet(KEY_LOCKED_GHOST_OPTIONS, Collections.emptySet());
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

  // --- Spy Mode ---

  public boolean isSpyEnabled () {
    return prefs.getBoolean(KEY_SPY_ENABLED, false);
  }

  public void setSpyEnabled (boolean enabled) {
    prefs.edit().putBoolean(KEY_SPY_ENABLED, enabled).apply();
  }

  @NonNull
  public Set<String> getSpyOptions () {
    return prefs.getStringSet(KEY_SPY_OPTIONS, Collections.emptySet());
  }

  public void setSpyOptions (@NonNull Set<String> options) {
    prefs.edit().putStringSet(KEY_SPY_OPTIONS, options).apply();
  }

  public boolean isSpyOptionEnabled (@NonNull String option) {
    return isSpyEnabled() && getSpyOptions().contains(option);
  }

  public void toggleSpyOption (@NonNull String option, boolean enabled) {
    Set<String> options = new HashSet<>(getSpyOptions());
    if (enabled) {
      options.add(option);
    } else {
      options.remove(option);
    }
    setSpyOptions(options);
  }
}
