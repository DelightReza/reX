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
package org.thunderdog.challegram.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.core.content.ContextCompat;

import org.thunderdog.challegram.service.TGXKeepAliveService;

/**
 * BootReceiver - Broadcast receiver that listens for BOOT_COMPLETED action.
 * 
 * When the device boots up, this receiver checks if the user has enabled the
 * native keep-alive feature in SharedPreferences. If enabled, it starts the
 * TGXKeepAliveService immediately after boot to ensure the app stays alive.
 */
public class BootReceiver extends BroadcastReceiver {
  private static final String PREF_NAME = "tgx_keepalive";
  private static final String KEY_USE_NATIVE_KEEPALIVE = "use_native_keepalive";

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent != null && Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
      // Check if keep-alive feature is enabled in preferences
      SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
      boolean keepAliveEnabled = prefs.getBoolean(KEY_USE_NATIVE_KEEPALIVE, false);
      
      if (keepAliveEnabled) {
        // Start the keep-alive service
        Intent serviceIntent = new Intent(context, TGXKeepAliveService.class);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          // For Android O and above, use startForegroundService
          ContextCompat.startForegroundService(context, serviceIntent);
        } else {
          context.startService(serviceIntent);
        }
      }
    }
  }
}
