/*
 * This file is a part of Telegram X
 * Copyright © 2014 (tgx-android@pm.me)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.thunderdog.challegram.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.drinkless.tdlib.TdApi;
import org.thunderdog.challegram.Log;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.unsorted.Settings;

/**
 * GhostModeManager handles intercepting and blocking TdApi requests
 * based on Ghost Mode settings to prevent read receipts, typing indicators,
 * online status, etc.
 */
public class GhostModeManager {
  private static final String TAG = "GhostMode";
  
  private static volatile GhostModeManager instance;
  private final Settings settings;
  
  private GhostModeManager() {
    this.settings = Settings.instance();
  }
  
  public static GhostModeManager instance() {
    if (instance == null) {
      synchronized (GhostModeManager.class) {
        if (instance == null) {
          instance = new GhostModeManager();
        }
      }
    }
    return instance;
  }
  
  /**
   * Checks if a TdApi request should be blocked based on Ghost Mode settings.
   * Returns true if the request should be blocked, false if it should proceed.
   */
  public <T extends TdApi.Object> boolean shouldBlockRequest(@NonNull TdApi.Function<T> function) {
    if (!settings.getReXGhostModeMaster()) {
      return false; // Ghost mode disabled, allow all
    }
    
    switch (function.getConstructor()) {
      case TdApi.ViewMessages.CONSTRUCTOR:
        if (settings.getReXDontReadMessages()) {
          Log.i(TAG, "Blocked ViewMessages request (Ghost Mode)");
          return true;
        }
        break;
        
      case TdApi.SendChatAction.CONSTRUCTOR:
        TdApi.SendChatAction action = (TdApi.SendChatAction) function;
        // Allow ChatActionCancel to go through
        if (action.action.getConstructor() != TdApi.ChatActionCancel.CONSTRUCTOR) {
          if (settings.getReXDontSendTyping()) {
            Log.i(TAG, "Blocked SendChatAction request (Ghost Mode)");
            return true;
          }
        }
        break;
        
      case TdApi.SetOption.CONSTRUCTOR:
        TdApi.SetOption setOption = (TdApi.SetOption) function;
        if ("online".equals(setOption.name)) {
          if (settings.getReXDontSendOnline()) {
            // Check if trying to set online to true
            if (setOption.value instanceof TdApi.OptionValueBoolean) {
              TdApi.OptionValueBoolean boolValue = (TdApi.OptionValueBoolean) setOption.value;
              if (boolValue.value) {
                Log.i(TAG, "Blocked SetOption(online=true) request (Ghost Mode)");
                return true;
              }
            }
          }
        }
        break;
        
      case TdApi.OpenStory.CONSTRUCTOR:
        if (settings.getReXDontReadStories()) {
          Log.i(TAG, "Blocked OpenStory request (Ghost Mode)");
          return true;
        }
        break;
    }
    
    return false;
  }
  
  /**
   * Applies automatic Ghost Mode actions (like going offline automatically)
   */
  public void applyAutomaticActions(@NonNull Tdlib tdlib) {
    if (!settings.getReXGhostModeMaster()) {
      return;
    }
    
    if (settings.getReXGoOfflineAutomatically()) {
      // Set online status to false
      tdlib.client().send(new TdApi.SetOption("online", new TdApi.OptionValueBoolean(false)), result -> {
        if (result.getConstructor() == TdApi.Ok.CONSTRUCTOR) {
          Log.i(TAG, "Automatically set online status to false");
        }
      });
    }
  }
  
  /**
   * Should be called when Ghost Mode master switch is toggled
   */
  public void onGhostModeToggled(boolean enabled, @NonNull Tdlib tdlib) {
    if (enabled) {
      applyAutomaticActions(tdlib);
    } else {
      // Optionally restore online status when Ghost Mode is disabled
      Log.i(TAG, "Ghost Mode disabled");
    }
  }
}
