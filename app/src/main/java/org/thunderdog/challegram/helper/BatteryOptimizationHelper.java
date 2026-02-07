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
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

/**
 * BatteryOptimizationHelper - Helper class for managing battery optimization settings.
 * 
 * This class provides methods to check if the app is whitelisted from battery optimizations
 * and to request the user to whitelist the app if needed. This is important for keeping
 * the app alive in the background without being killed by the system.
 */
public class BatteryOptimizationHelper {
  
  /**
   * Checks if the app is currently ignoring battery optimizations.
   * 
   * @param context The application context
   * @return true if the app is whitelisted, false otherwise or if API level < 23
   */
  @RequiresApi(api = Build.VERSION_CODES.M)
  public static boolean isIgnoringBatteryOptimizations(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
      if (powerManager != null) {
        String packageName = context.getPackageName();
        return powerManager.isIgnoringBatteryOptimizations(packageName);
      }
    }
    return false;
  }

  /**
   * Requests the user to ignore battery optimizations for this app.
   * 
   * This method triggers an intent that opens the system settings screen
   * where the user can whitelist the app from battery optimizations.
   * 
   * @param context The application context
   */
  @RequiresApi(api = Build.VERSION_CODES.M)
  public static void requestIgnoreBatteryOptimizations(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      // Check if we're already whitelisted
      if (!isIgnoringBatteryOptimizations(context)) {
        try {
          Intent intent = new Intent();
          intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
          intent.setData(Uri.parse("package:" + context.getPackageName()));
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          context.startActivity(intent);
        } catch (Exception e) {
          // If the direct request fails, open the general battery optimization settings
          try {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
          } catch (Exception ex) {
            // Ignore if both attempts fail
          }
        }
      }
    }
  }
}
