/*
 * This file is a part of reX
 * Copyright © 2024 (DelightReza)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
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

/**
 * Foreground service that keeps TDLib connection alive on non-GMS devices.
 * Prevents the Android OS from killing the app process by displaying
 * a persistent low-priority notification.
 */
public class TGXKeepAliveService extends Service {
  private static final String CHANNEL_ID = "tgx_keepalive_channel";
  private static final int NOTIFICATION_ID = 999;

  @Override
  public void onCreate() {
    super.onCreate();
    createNotificationChannel();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    // Start foreground service with persistent notification
    startForeground(NOTIFICATION_ID, createNotification());
    
    // START_STICKY ensures the service is restarted if killed by the system
    return START_STICKY;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    // This service doesn't support binding
    return null;
  }

  /**
   * Creates the notification channel for Android 8.0+ (API 26+)
   * Uses IMPORTANCE_LOW so the notification doesn't make sound or vibrate
   */
  private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel channel = new NotificationChannel(
        CHANNEL_ID,
        "Keep Alive Service",
        NotificationManager.IMPORTANCE_LOW
      );
      channel.setDescription("Keeps the app connection alive in the background");
      channel.setShowBadge(false);
      channel.enableLights(false);
      channel.enableVibration(false);
      channel.setSound(null, null);

      NotificationManager notificationManager = getSystemService(NotificationManager.class);
      if (notificationManager != null) {
        notificationManager.createNotificationChannel(channel);
      }
    }
  }

  /**
   * Creates the persistent notification shown in the status bar
   */
  private Notification createNotification() {
    // Create intent to open the app when notification is tapped
    Intent notificationIntent = new Intent(this, MainActivity.class);
    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    
    PendingIntent pendingIntent = PendingIntent.getActivity(
      this,
      0,
      notificationIntent,
      Build.VERSION.SDK_INT >= Build.VERSION_CODES.M 
        ? PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT
        : PendingIntent.FLAG_UPDATE_CURRENT
    );

    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
      .setContentTitle("Telegram X")
      .setContentText("Running in background")
      .setSmallIcon(R.drawable.baseline_sync_24)
      .setContentIntent(pendingIntent)
      .setPriority(NotificationCompat.PRIORITY_LOW)
      .setOngoing(true)
      .setShowWhen(false)
      .setSilent(true);

    // For Android 8.0+, set foreground service type
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
      // Android 14+ requires explicit foreground service type
      builder.setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE);
    }

    return builder.build();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    stopForeground(true);
  }
}
