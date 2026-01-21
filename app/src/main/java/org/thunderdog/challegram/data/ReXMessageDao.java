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

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * DAO (Data Access Object) for ReXMessage entity
 */
@Dao
public interface ReXMessageDao {
  
  @Insert
  void insert(ReXMessage message);
  
  @Query("SELECT * FROM rex_messages WHERE account_id = :accountId ORDER BY timestamp DESC")
  List<ReXMessage> getAllMessages(int accountId);
  
  @Query("SELECT * FROM rex_messages WHERE account_id = :accountId AND chat_id = :chatId ORDER BY timestamp DESC")
  List<ReXMessage> getMessagesForChat(int accountId, long chatId);
  
  @Query("SELECT * FROM rex_messages WHERE account_id = :accountId AND is_deleted = 1 ORDER BY timestamp DESC")
  List<ReXMessage> getDeletedMessages(int accountId);
  
  @Query("SELECT * FROM rex_messages WHERE account_id = :accountId AND is_edited = 1 ORDER BY timestamp DESC")
  List<ReXMessage> getEditedMessages(int accountId);
  
  @Query("SELECT * FROM rex_messages WHERE account_id = :accountId AND chat_id = :chatId AND message_id = :messageId")
  ReXMessage getMessage(int accountId, long chatId, long messageId);
  
  @Query("DELETE FROM rex_messages WHERE account_id = :accountId")
  void clearAllMessages(int accountId);
  
  @Query("DELETE FROM rex_messages WHERE account_id = :accountId AND chat_id = :chatId")
  void clearMessagesForChat(int accountId, long chatId);
  
  @Query("SELECT COUNT(*) FROM rex_messages WHERE account_id = :accountId AND is_deleted = 1")
  int getDeletedMessagesCount(int accountId);
  
  @Query("SELECT COUNT(*) FROM rex_messages WHERE account_id = :accountId AND is_edited = 1")
  int getEditedMessagesCount(int accountId);
}
