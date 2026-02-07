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
 * TGXKeepAliveService - A foreground service that prevents the Android OS from killing the app process.
 * 
 * This service runs with a persistent notification (ID: 999) using low priority (IMPORTANCE_LOW)
 * so it doesn't make sound but remains visible in the status bar.
 * 
 * Requirements:
 * - Runs as a Foreground Service
 * - Handles Android 8.0+ (Oreo) Notification Channels properly
 * - Returns START_STICKY to restart service if killed
 * - Uses foregroundServiceType="dataSync" for Android 14 compliance
 */
public class TGXKeepAliveService extends Service {
  private static final int NOTIFICATION_ID = 999;
  private static final String CHANNEL_ID = "tgx_keepalive_channel";
  private static final String CHANNEL_NAME = "TelegramX Alive Service";
  
  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    createNotificationChannel();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    // Start foreground with persistent notification
    startForeground(NOTIFICATION_ID, createNotification());
    return START_STICKY; // Restart service if killed by system
  }

  /**
   * Creates notification channel for Android 8.0+ (Oreo)
   * Uses IMPORTANCE_LOW so it doesn't make sound but remains visible
   */
  private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      
      NotificationChannel channel = new NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_LOW // Low priority - no sound
      );
      channel.setDescription("Keeps Telegram X running in the background");
      channel.setShowBadge(false);
      
      notificationManager.createNotificationChannel(channel);
    }
  }

  /**
   * Creates the persistent notification for foreground service
   */
  private Notification createNotification() {
    Intent notificationIntent = new Intent(this, MainActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(
      this,
      0,
      notificationIntent,
      Intents.mutabilityFlags(false)
    );

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
      .setContentTitle(getString(R.string.TGXKeepAliveNotificationTitle))
      .setContentText(getString(R.string.TGXKeepAliveNotificationText))
      .setSmallIcon(R.drawable.baseline_access_time_24)
      .setContentIntent(pendingIntent)
      .setPriority(NotificationCompat.PRIORITY_LOW) // Low priority for compatibility
      .setOngoing(true) // Cannot be dismissed by user
      .setShowWhen(false);

    return builder.build();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }
}
