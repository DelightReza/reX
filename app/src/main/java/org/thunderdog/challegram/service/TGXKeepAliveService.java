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
package org.thunderdog.challegram.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import org.thunderdog.challegram.MainActivity;
import org.thunderdog.challegram.R;
import org.thunderdog.challegram.tool.Intents;

/**
 * Native Push Service (Keep-Alive) for Telegram X
 * 
 * This foreground service keeps the application process alive in the background,
 * allowing TDLib to maintain its TCP connection and receive notifications
 * without using Google Mobile Services (FCM/GMS).
 * 
 * The service runs as a foreground service with a low-priority notification
 * to prevent Android OS from killing the app process.
 */
public class TGXKeepAliveService extends Service {
    private static final String TAG = "TGXKeepAliveService";
    
    // Notification ID for the foreground service
    public static final int NOTIFICATION_ID = 999;
    
    // Notification channel for Android 8.0+ (Oreo)
    private static final String CHANNEL_ID = "tgx_keepalive_channel";
    private static final String CHANNEL_NAME = "Native Push Service";
    private static final String CHANNEL_DESCRIPTION = "Keeps Telegram X connected for receiving notifications";

    @Override
    public void onCreate() {
        super.onCreate();
        
        // Create notification channel for Android 8.0+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create and show the foreground notification
        Notification notification = createNotification();
        startForeground(NOTIFICATION_ID, notification);
        
        // START_STICKY ensures the service is restarted if killed by the system
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // This service doesn't support binding
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Service is being destroyed
    }

    /**
     * Creates a notification channel for Android 8.0+ (Oreo)
     * Sets IMPORTANCE_LOW so the notification doesn't make sound or vibrate
     */
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = 
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            
            if (notificationManager != null) {
                NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_LOW // Low priority - no sound, stays visible
                );
                channel.setDescription(CHANNEL_DESCRIPTION);
                channel.setShowBadge(false);
                channel.enableVibration(false);
                channel.setSound(null, null);
                
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /**
     * Creates the persistent notification for the foreground service
     * 
     * @return Notification to display in the status bar
     */
    private Notification createNotification() {
        // Create intent to launch MainActivity when notification is tapped
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
            this, 
            0, 
            notificationIntent, 
            Intents.mutabilityFlags(false)
        );

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.NativeKeepAliveTitle))
            .setContentText(getString(R.string.NativeKeepAliveText))
            .setSmallIcon(R.drawable.baseline_sync_24)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true) // Makes the notification persistent
            .setShowWhen(false);

        return builder.build();
    }

    /**
     * Starts the Keep-Alive service
     * 
     * @param context Application context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, TGXKeepAliveService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    /**
     * Stops the Keep-Alive service
     * 
     * @param context Application context
     */
    public static void stop(Context context) {
        Intent intent = new Intent(context, TGXKeepAliveService.class);
        context.stopService(intent);
    }
}
