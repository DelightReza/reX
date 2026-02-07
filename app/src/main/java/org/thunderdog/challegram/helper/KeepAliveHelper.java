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
import android.content.SharedPreferences;
import android.os.Build;

import org.thunderdog.challegram.service.TGXKeepAliveService;

/**
 * Helper class for managing the TGXKeepAliveService.
 * Provides methods to start, stop, and check the status of the keep-alive service.
 */
public class KeepAliveHelper {
  
  private static final String PREFS_NAME = "tgx_keepalive";
  private static final String KEY_ENABLED = "use_native_keepalive";

  /**
   * Checks if the keep-alive service is enabled
   * 
   * @param context Application context
   * @return true if the service is enabled, false otherwise
   */
  public static boolean isKeepAliveEnabled(Context context) {
    SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    return prefs.getBoolean(KEY_ENABLED, false);
  }

  /**
   * Sets the keep-alive service enabled/disabled state
   * 
   * @param context Application context
   * @param enabled Whether to enable or disable the service
   */
  public static void setKeepAliveEnabled(Context context, boolean enabled) {
    SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    prefs.edit().putBoolean(KEY_ENABLED, enabled).apply();
    
    if (enabled) {
      startKeepAliveService(context);
      // Request battery optimization exemption
      BatteryOptimizationHelper.requestIgnoreBatteryOptimizations(context);
    } else {
      stopKeepAliveService(context);
    }
  }

  /**
   * Starts the TGXKeepAliveService
   * 
   * @param context Application context
   */
  public static void startKeepAliveService(Context context) {
    Intent serviceIntent = new Intent(context, TGXKeepAliveService.class);
    
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(serviceIntent);
      } else {
        context.startService(serviceIntent);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Stops the TGXKeepAliveService
   * 
   * @param context Application context
   */
  public static void stopKeepAliveService(Context context) {
    Intent serviceIntent = new Intent(context, TGXKeepAliveService.class);
    context.stopService(serviceIntent);
  }
}
