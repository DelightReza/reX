/*
 * This file is a part of reX
 * Copyright © 2024 (DelightReza)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package org.thunderdog.challegram.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

/**
 * Helper class for managing battery optimization settings.
 * Provides methods to check if the app is whitelisted from battery optimizations
 * and to request the user to add the app to the whitelist.
 */
public class BatteryOptimizationHelper {

  /**
   * Checks if the app is currently ignoring battery optimizations
   * 
   * @param context Application context
   * @return true if the app is on the battery optimization whitelist, false otherwise
   */
  public static boolean isIgnoringBatteryOptimizations(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
      if (powerManager != null) {
        return powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
      }
    }
    // For devices below Android 6.0, battery optimization doesn't exist
    return true;
  }

  /**
   * Requests the user to add the app to the battery optimization whitelist.
   * Opens the system settings screen where the user can grant the permission.
   * 
   * @param context Activity context (must be an Activity to start the intent)
   * @return true if the intent was successfully started, false otherwise
   */
  public static boolean requestIgnoreBatteryOptimizations(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      try {
        // Check if already ignoring battery optimizations
        if (isIgnoringBatteryOptimizations(context)) {
          return true;
        }

        // Create intent to request battery optimization exemption
        Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        
        context.startActivity(intent);
        return true;
      } catch (Exception e) {
        // If ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS fails, 
        // try to open general battery optimization settings
        try {
          Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          context.startActivity(intent);
          return true;
        } catch (Exception ex) {
          ex.printStackTrace();
          return false;
        }
      }
    }
    // For devices below Android 6.0, no action needed
    return true;
  }
}
