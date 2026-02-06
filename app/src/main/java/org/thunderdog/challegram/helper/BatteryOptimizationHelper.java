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
package org.thunderdog.challegram.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

/**
 * Helper class for managing battery optimization settings
 * 
 * Provides methods to check battery optimization status and
 * request exemption from battery optimization restrictions
 */
public class BatteryOptimizationHelper {

    /**
     * Checks if the app is currently whitelisted from battery optimization
     * 
     * @param context Application context
     * @return true if the app is whitelisted (ignoring battery optimizations), false otherwise
     */
    public static boolean isIgnoringBatteryOptimizations(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (powerManager != null) {
                return powerManager.isIgnoringBatteryOptimizations(context.getPackageName());
            }
        }
        return true; // On older versions, always return true (no battery optimization)
    }

    /**
     * Requests the user to whitelist the app from battery optimization
     * 
     * This opens the system settings screen where the user can grant
     * the battery optimization exemption. This is required for the
     * Keep-Alive service to work reliably in the background.
     * 
     * @param context Application context
     * @return true if the intent was successfully launched, false otherwise
     */
    @SuppressLint("BatteryLife")
    public static boolean requestIgnoreBatteryOptimizations(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check if already whitelisted
            if (isIgnoringBatteryOptimizations(context)) {
                return false; // Already whitelisted, no need to request
            }

            try {
                // Create intent to request battery optimization exemption
                Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                
                context.startActivity(intent);
                return true;
            } catch (Exception e) {
                // If the specific action is not available, open general battery optimization settings
                try {
                    Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                } catch (Exception ex) {
                    // Unable to open settings
                    return false;
                }
            }
        }
        return false; // Not needed on older versions
    }

    /**
     * Opens the battery optimization settings page for the app
     * 
     * @param context Application context
     */
    public static void openBatteryOptimizationSettings(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
                // Fallback to general settings
                try {
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (Exception ex) {
                    // Unable to open settings
                }
            }
        }
    }
}
