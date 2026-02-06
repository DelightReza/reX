/*
 * This file is a part of reX
 * Copyright © 2024 (DelightReza)
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

import org.thunderdog.challegram.service.TGXKeepAliveService;

/**
 * BroadcastReceiver that listens for device boot completion
 * and starts the Keep-Alive service if enabled by the user
 */
public class BootReceiver extends BroadcastReceiver {
    
    private static final String PREFS_NAME = "rex_config";
    private static final String KEY_USE_NATIVE_KEEPALIVE = "use_native_keepalive";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || context == null) {
            return;
        }

        String action = intent.getAction();
        
        // Only handle BOOT_COMPLETED action
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
            // Check if user has enabled the Native Keep-Alive feature
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            boolean useNativeKeepAlive = prefs.getBoolean(KEY_USE_NATIVE_KEEPALIVE, false);
            
            if (useNativeKeepAlive) {
                // Start the Keep-Alive service
                TGXKeepAliveService.start(context);
            }
        }
    }
}
