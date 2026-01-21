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

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entity class representing a message stored in the reX Spy Mode database
 */
@Entity(tableName = "rex_messages")
public class ReXMessage {
  
  @PrimaryKey(autoGenerate = true)
  public long id;
  
  @ColumnInfo(name = "account_id")
  public int accountId;
  
  @ColumnInfo(name = "chat_id")
  public long chatId;
  
  @ColumnInfo(name = "message_id")
  public long messageId;
  
  @ColumnInfo(name = "sender_id")
  public long senderId;
  
  @ColumnInfo(name = "content_type")
  public String contentType;
  
  @ColumnInfo(name = "text_content")
  public String textContent;
  
  @ColumnInfo(name = "media_path")
  public String mediaPath;
  
  @ColumnInfo(name = "timestamp")
  public long timestamp;
  
  @ColumnInfo(name = "is_deleted")
  public boolean isDeleted;
  
  @ColumnInfo(name = "is_edited")
  public boolean isEdited;
  
  @ColumnInfo(name = "old_content")
  public String oldContent;
  
  @ColumnInfo(name = "edit_timestamp")
  public long editTimestamp;
  
  @ColumnInfo(name = "read_timestamp")
  public long readTimestamp;
  
  @ColumnInfo(name = "is_bot_dialog")
  public boolean isBotDialog;
  
  public ReXMessage() {
    // Required empty constructor for Room
  }
  
  public ReXMessage(int accountId, long chatId, long messageId, long senderId,
                    @NonNull String contentType, String textContent, String mediaPath,
                    long timestamp, boolean isDeleted, boolean isEdited,
                    String oldContent, long editTimestamp, long readTimestamp,
                    boolean isBotDialog) {
    this.accountId = accountId;
    this.chatId = chatId;
    this.messageId = messageId;
    this.senderId = senderId;
    this.contentType = contentType;
    this.textContent = textContent;
    this.mediaPath = mediaPath;
    this.timestamp = timestamp;
    this.isDeleted = isDeleted;
    this.isEdited = isEdited;
    this.oldContent = oldContent;
    this.editTimestamp = editTimestamp;
    this.readTimestamp = readTimestamp;
    this.isBotDialog = isBotDialog;
  }
}
