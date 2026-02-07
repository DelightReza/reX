/*
 * This file is a part of reX
 * Copyright © 2024 (DelightReza)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package org.thunderdog.challegram.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import org.thunderdog.challegram.service.TGXKeepAliveService;

/**
 * BroadcastReceiver that starts the TGXKeepAliveService after device boot
 * if the user has enabled the native keep-alive feature.
 */
public class BootReceiver extends BroadcastReceiver {
  
  private static final String PREFS_NAME = "tgx_keepalive";
  private static final String KEY_ENABLED = "use_native_keepalive";
  
  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent == null || intent.getAction() == null) {
      return;
    }

    // Check if this is a BOOT_COMPLETED broadcast
    if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
      // Check if user has enabled native keep-alive
      SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
      boolean enabled = prefs.getBoolean(KEY_ENABLED, false);
      
      if (enabled) {
        startKeepAliveService(context);
      }
    }
  }

  /**
   * Starts the TGXKeepAliveService with proper handling for Android 8.0+
   */
  private void startKeepAliveService(Context context) {
    Intent serviceIntent = new Intent(context, TGXKeepAliveService.class);
    
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Android 8.0+ requires startForegroundService
        context.startForegroundService(serviceIntent);
      } else {
        context.startService(serviceIntent);
      }
    } catch (Exception e) {
      // Catch any exceptions to prevent crashes during boot
      e.printStackTrace();
    }
  }
}
