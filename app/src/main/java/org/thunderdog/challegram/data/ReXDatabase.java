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
package org.thunderdog.challegram.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Room Database for storing reX Spy Mode data
 */
@Database(entities = {ReXMessage.class}, version = 1, exportSchema = false)
public abstract class ReXDatabase extends RoomDatabase {
  
  private static final String DATABASE_NAME = "rex_spy_mode.db";
  
  private static volatile ReXDatabase instance;
  
  public abstract ReXMessageDao messageDao();
  
  public static ReXDatabase getInstance(@NonNull Context context) {
    if (instance == null) {
      synchronized (ReXDatabase.class) {
        if (instance == null) {
          instance = Room.databaseBuilder(
            context.getApplicationContext(),
            ReXDatabase.class,
            DATABASE_NAME
          )
          // TODO: Implement proper migration strategy for production
          // Currently using fallbackToDestructiveMigration() which will
          // delete all data on schema changes. For production, implement
          // Migration classes to preserve user data across updates.
          .fallbackToDestructiveMigration()
          .build();
        }
      }
    }
    return instance;
  }
  
  public static void closeDatabase() {
    if (instance != null && instance.isOpen()) {
      instance.close();
      instance = null;
    }
  }
}
