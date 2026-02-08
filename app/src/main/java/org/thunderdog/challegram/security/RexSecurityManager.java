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
package org.thunderdog.challegram.security;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.fragment.app.FragmentActivity;

import org.thunderdog.challegram.tool.UI;

import java.util.HashSet;
import java.util.Set;

public final class RexSecurityManager {

  private static volatile RexSecurityManager instance;

  private static final String PREFS_NAME = "rex_security";

  private static final String KEY_HIDDEN_CHAT_IDS = "hidden_chat_ids";
  private static final String KEY_LOCKED_CHAT_IDS = "locked_chat_ids";
  private static final String KEY_HIDDEN_ACCOUNT_IDS = "hidden_account_ids";
  private static final String KEY_PHONE_MASKED = "phone_masked";
  private static final String KEY_PHONE_OTHERS_MASKED = "phone_others_masked";
  private static final String KEY_AUTH_TIMEOUT = "auth_timeout";
  private static final String KEY_LAST_AUTH_TIME = "last_auth_time";
  private static final String KEY_LOCK_SCREENSHOTS = "lock_screenshots";
  private static final String KEY_LOCK_ARCHIVED = "lock_archived";
  private static final String KEY_LOCK_CALLS = "lock_calls";
  private static final String KEY_LOCK_SECRET = "lock_secret";
  private static final String KEY_LOCK_SETTINGS = "lock_settings";
  private static final String KEY_HIDE_LOCKED_CHATS = "hide_locked_chats";
  private static final String KEY_SHOW_NOTIFICATIONS = "show_notifications_locked";
  private static final String KEY_ALLOW_DEVICE_PIN = "allow_device_pin";

  private final SharedPreferences prefs;

  private RexSecurityManager (@NonNull Context context) {
    prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
  }

  @NonNull
  public static RexSecurityManager getInstance () {
    if (instance == null) {
      synchronized (RexSecurityManager.class) {
        if (instance == null) {
          instance = new RexSecurityManager(UI.getAppContext());
        }
      }
    }
    return instance;
  }

  // --- Hidden Chats ---

  @NonNull
  public Set<String> getHiddenChatIds () {
    Set<String> set = prefs.getStringSet(KEY_HIDDEN_CHAT_IDS, null);
    return set != null ? new HashSet<>(set) : new HashSet<>();
  }

  public void setHiddenChatIds (@NonNull Set<String> ids) {
    prefs.edit().putStringSet(KEY_HIDDEN_CHAT_IDS, ids).apply();
  }

  public void addHiddenChat (long chatId) {
    Set<String> ids = new HashSet<>(getHiddenChatIds());
    ids.add(String.valueOf(chatId));
    setHiddenChatIds(ids);
  }

  public void removeHiddenChat (long chatId) {
    Set<String> ids = new HashSet<>(getHiddenChatIds());
    ids.remove(String.valueOf(chatId));
    setHiddenChatIds(ids);
  }

  public boolean shouldHideChat (long chatId) {
    return getHiddenChatIds().contains(String.valueOf(chatId)) && !isSessionUnlocked();
  }

  // --- Locked Chats ---

  @NonNull
  public Set<String> getLockedChatIds () {
    Set<String> set = prefs.getStringSet(KEY_LOCKED_CHAT_IDS, null);
    return set != null ? new HashSet<>(set) : new HashSet<>();
  }

  public void setLockedChatIds (@NonNull Set<String> ids) {
    prefs.edit().putStringSet(KEY_LOCKED_CHAT_IDS, ids).apply();
  }

  public void addLockedChat (long chatId) {
    Set<String> ids = new HashSet<>(getLockedChatIds());
    ids.add(String.valueOf(chatId));
    setLockedChatIds(ids);
  }

  public void removeLockedChat (long chatId) {
    Set<String> ids = new HashSet<>(getLockedChatIds());
    ids.remove(String.valueOf(chatId));
    setLockedChatIds(ids);
  }

  public boolean isChatLocked (long chatId) {
    return getLockedChatIds().contains(String.valueOf(chatId));
  }

  // --- Hidden Accounts ---

  @NonNull
  public Set<String> getHiddenAccountIds () {
    Set<String> set = prefs.getStringSet(KEY_HIDDEN_ACCOUNT_IDS, null);
    return set != null ? new HashSet<>(set) : new HashSet<>();
  }

  public void setHiddenAccountIds (@NonNull Set<String> ids) {
    prefs.edit().putStringSet(KEY_HIDDEN_ACCOUNT_IDS, ids).apply();
  }

  public boolean isAccountHidden (int accountId) {
    return getHiddenAccountIds().contains(String.valueOf(accountId));
  }

  // --- Phone Masking ---

  public boolean isPhoneMasked () {
    return prefs.getBoolean(KEY_PHONE_MASKED, false);
  }

  public void setPhoneMasked (boolean masked) {
    prefs.edit().putBoolean(KEY_PHONE_MASKED, masked).apply();
  }

  @NonNull
  public String formatPhoneNumber (@Nullable String raw) {
    if (raw == null || raw.isEmpty()) {
      return "";
    }
    if (isPhoneMasked()) {
      return "+** ***";
    }
    return raw;
  }

  // --- Lock Screenshots ---

  public boolean isLockScreenshots () {
    return prefs.getBoolean(KEY_LOCK_SCREENSHOTS, false);
  }

  public void setLockScreenshots (boolean lock) {
    prefs.edit().putBoolean(KEY_LOCK_SCREENSHOTS, lock).apply();
  }

  // --- Lock Actions ---

  public boolean isLockArchived () {
    return prefs.getBoolean(KEY_LOCK_ARCHIVED, false);
  }

  public void setLockArchived (boolean lock) {
    prefs.edit().putBoolean(KEY_LOCK_ARCHIVED, lock).apply();
  }

  public boolean isLockCalls () {
    return prefs.getBoolean(KEY_LOCK_CALLS, false);
  }

  public void setLockCalls (boolean lock) {
    prefs.edit().putBoolean(KEY_LOCK_CALLS, lock).apply();
  }

  public boolean isLockSecret () {
    return prefs.getBoolean(KEY_LOCK_SECRET, false);
  }

  public void setLockSecret (boolean lock) {
    prefs.edit().putBoolean(KEY_LOCK_SECRET, lock).apply();
  }

  public boolean isLockSettings () {
    return prefs.getBoolean(KEY_LOCK_SETTINGS, false);
  }

  public void setLockSettings (boolean lock) {
    prefs.edit().putBoolean(KEY_LOCK_SETTINGS, lock).apply();
  }

  // --- Locked Chats Display ---

  public boolean isHideLockedChats () {
    return prefs.getBoolean(KEY_HIDE_LOCKED_CHATS, false);
  }

  public void setHideLockedChats (boolean hide) {
    prefs.edit().putBoolean(KEY_HIDE_LOCKED_CHATS, hide).apply();
  }

  public boolean isShowNotificationsLocked () {
    return prefs.getBoolean(KEY_SHOW_NOTIFICATIONS, true);
  }

  public void setShowNotificationsLocked (boolean show) {
    prefs.edit().putBoolean(KEY_SHOW_NOTIFICATIONS, show).apply();
  }

  // --- Biometric Options ---

  public boolean isAllowDevicePin () {
    return prefs.getBoolean(KEY_ALLOW_DEVICE_PIN, false);
  }

  public void setAllowDevicePin (boolean allow) {
    prefs.edit().putBoolean(KEY_ALLOW_DEVICE_PIN, allow).apply();
  }

  // --- Phone Masking (Others) ---

  public boolean isOthersPhoneMasked () {
    return prefs.getBoolean(KEY_PHONE_OTHERS_MASKED, false);
  }

  public void setOthersPhoneMasked (boolean masked) {
    prefs.edit().putBoolean(KEY_PHONE_OTHERS_MASKED, masked).apply();
  }

  // --- Auth Timeout ---

  public int getAuthTimeout () {
    return prefs.getInt(KEY_AUTH_TIMEOUT, 0);
  }

  public void setAuthTimeout (int seconds) {
    prefs.edit().putInt(KEY_AUTH_TIMEOUT, seconds).apply();
  }

  public long getLastAuthTime () {
    return prefs.getLong(KEY_LAST_AUTH_TIME, 0L);
  }

  public void setLastAuthTime (long time) {
    prefs.edit().putLong(KEY_LAST_AUTH_TIME, time).apply();
  }

  public boolean isSessionUnlocked () {
    int timeout = getAuthTimeout();
    if (timeout <= 0) {
      return false;
    }
    long now = System.currentTimeMillis();
    long last = getLastAuthTime();
    return (now - last) < (timeout * 1000L);
  }

  // --- Biometric Authentication ---

  public void authenticate (@NonNull Activity host, @NonNull Runnable onSuccess) {
    if (!(host instanceof FragmentActivity)) {
      return;
    }
    FragmentActivity fragmentActivity = (FragmentActivity) host;

    BiometricPrompt.PromptInfo.Builder builder = new BiometricPrompt.PromptInfo.Builder()
      .setTitle("reX Authentication")
      .setSubtitle("Verify your identity")
      .setConfirmationRequired(false);

    BiometricManager manager = BiometricManager.from(host);
    boolean strongAvailable = manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS;
    boolean weakAvailable = manager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) == BiometricManager.BIOMETRIC_SUCCESS;
    boolean deviceCredAvailable = manager.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS;

    if (strongAvailable) {
      builder.setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG);
      builder.setNegativeButtonText("Cancel");
    } else if (weakAvailable) {
      builder.setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK);
      builder.setNegativeButtonText("Cancel");
    } else if (deviceCredAvailable) {
      builder.setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK | BiometricManager.Authenticators.DEVICE_CREDENTIAL);
    } else {
      onSuccess.run();
      return;
    }

    BiometricPrompt prompt = new BiometricPrompt(fragmentActivity,
      new BiometricPrompt.AuthenticationCallback() {
        @Override
        public void onAuthenticationSucceeded (@NonNull BiometricPrompt.AuthenticationResult result) {
          setLastAuthTime(System.currentTimeMillis());
          onSuccess.run();
        }

        @Override
        public void onAuthenticationError (int errorCode, @NonNull CharSequence errString) {
          // Authentication failed or cancelled - do nothing
        }

        @Override
        public void onAuthenticationFailed () {
          // Biometric not recognized - prompt stays
        }
      });

    prompt.authenticate(builder.build());
  }
}
