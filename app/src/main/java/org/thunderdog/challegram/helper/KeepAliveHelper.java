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
package org.thunderdog.challegram.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.content.ContextCompat;

import org.thunderdog.challegram.service.TGXKeepAliveService;

/**
 * KeepAliveHelper - Helper class for managing the TGXKeepAliveService.
 * 
 * This class provides convenience methods to enable/disable the keep-alive service
 * and manage its persistent state in SharedPreferences.
 */
public class KeepAliveHelper {
  private static final String PREF_NAME = "tgx_keepalive";
  private static final String KEY_USE_NATIVE_KEEPALIVE = "use_native_keepalive";

  /**
   * Checks if the keep-alive service is enabled.
   * 
   * @param context The application context
   * @return true if enabled, false otherwise
   */
  public static boolean isKeepAliveEnabled(Context context) {
    SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    return prefs.getBoolean(KEY_USE_NATIVE_KEEPALIVE, false);
  }

  /**
   * Enables or disables the keep-alive service.
   * 
   * When enabled:
   * - Saves the preference
   * - Starts the TGXKeepAliveService
   * - Requests battery optimization exemption
   * 
   * When disabled:
   * - Saves the preference
   * - Stops the TGXKeepAliveService
   * 
   * @param context The application context
   * @param enabled true to enable, false to disable
   */
  public static void setKeepAliveEnabled(Context context, boolean enabled) {
    // Save preference
    SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    prefs.edit().putBoolean(KEY_USE_NATIVE_KEEPALIVE, enabled).apply();

    Intent serviceIntent = new Intent(context, TGXKeepAliveService.class);

    if (enabled) {
      // Start the service
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        ContextCompat.startForegroundService(context, serviceIntent);
      } else {
        context.startService(serviceIntent);
      }

      // Request battery optimization exemption
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        BatteryOptimizationHelper.requestIgnoreBatteryOptimizations(context);
      }
    } else {
      // Stop the service
      context.stopService(serviceIntent);
    }
  }
}
