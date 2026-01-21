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
package org.thunderdog.challegram.core;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.drinkless.tdlib.TdApi;
import org.thunderdog.challegram.Log;
import org.thunderdog.challegram.data.ReXDatabase;
import org.thunderdog.challegram.data.ReXMessage;
import org.thunderdog.challegram.data.ReXMessageDao;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.unsorted.Settings;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SpyModeManager handles tracking and storing deleted/edited messages
 */
public class SpyModeManager {
  private static final String TAG = "SpyMode";
  
  private static volatile SpyModeManager instance;
  private final ExecutorService executor;
  private ReXDatabase database;
  private final Settings settings;
  
  private SpyModeManager() {
    this.executor = Executors.newSingleThreadExecutor();
    this.settings = Settings.instance();
  }
  
  public static SpyModeManager instance() {
    if (instance == null) {
      synchronized (SpyModeManager.class) {
        if (instance == null) {
          instance = new SpyModeManager();
        }
      }
    }
    return instance;
  }
  
  public void initialize(@NonNull Context context) {
    if (database == null) {
      database = ReXDatabase.getInstance(context);
      Log.i(TAG, "SpyModeManager initialized");
    }
  }
  
  /**
   * Called when a message is about to be deleted
   */
  public void onMessageDeleted(@NonNull Tdlib tdlib, long chatId, long messageId) {
    if (!settings.getReXSaveDeletedMessages()) {
      return;
    }
    
    // Check if we should save in bot dialogs
    if (!settings.getReXSaveInBotDialogs()) {
      tdlib.client().send(new TdApi.GetChat(chatId), result -> {
        if (result.getConstructor() == TdApi.Chat.CONSTRUCTOR) {
          TdApi.Chat chat = (TdApi.Chat) result;
          if (chat.type.getConstructor() == TdApi.ChatTypePrivate.CONSTRUCTOR) {
            TdApi.ChatTypePrivate privateChat = (TdApi.ChatTypePrivate) chat.type;
            tdlib.client().send(new TdApi.GetUser(privateChat.userId), userResult -> {
              if (userResult.getConstructor() == TdApi.User.CONSTRUCTOR) {
                TdApi.User user = (TdApi.User) userResult;
                if (user.type.getConstructor() == TdApi.UserTypeBot.CONSTRUCTOR) {
                  return; // Skip bot dialogs
                }
              }
              saveDeletedMessage(tdlib, chatId, messageId);
            });
          } else {
            saveDeletedMessage(tdlib, chatId, messageId);
          }
        }
      });
    } else {
      saveDeletedMessage(tdlib, chatId, messageId);
    }
  }
  
  private void saveDeletedMessage(@NonNull Tdlib tdlib, long chatId, long messageId) {
    // Get the full message before it's deleted
    tdlib.client().send(new TdApi.GetMessage(chatId, messageId), result -> {
      if (result.getConstructor() == TdApi.Message.CONSTRUCTOR) {
        TdApi.Message message = (TdApi.Message) result;
        saveMessageToDatabase(tdlib, message, true, false, null);
      }
    });
  }
  
  /**
   * Called when a message is edited
   */
  public void onMessageEdited(@NonNull Tdlib tdlib, long chatId, long messageId,
                               @NonNull TdApi.MessageContent oldContent) {
    if (!settings.getReXSaveEditsHistory()) {
      return;
    }
    
    // Get the full message
    tdlib.client().send(new TdApi.GetMessage(chatId, messageId), result -> {
      if (result.getConstructor() == TdApi.Message.CONSTRUCTOR) {
        TdApi.Message message = (TdApi.Message) result;
        String oldContentText = extractTextFromContent(oldContent);
        saveMessageToDatabase(tdlib, message, false, true, oldContentText);
      }
    });
  }
  
  private void saveMessageToDatabase(@NonNull Tdlib tdlib, @NonNull TdApi.Message message,
                                      boolean isDeleted, boolean isEdited,
                                      @Nullable String oldContent) {
    if (database == null) {
      Log.e(TAG, "Database not initialized");
      return;
    }
    
    executor.execute(() -> {
      try {
        ReXMessageDao dao = database.messageDao();
        
        String contentType = message.content.getClass().getSimpleName();
        String textContent = extractTextFromContent(message.content);
        String mediaPath = extractMediaPath(message.content);
        
        long senderId = 0;
        if (message.senderId != null) {
          if (message.senderId.getConstructor() == TdApi.MessageSenderUser.CONSTRUCTOR) {
            senderId = ((TdApi.MessageSenderUser) message.senderId).userId;
          }
        }
        
        long readTimestamp = 0;
        if (settings.getReXSaveReadDate()) {
          readTimestamp = System.currentTimeMillis();
        }
        
        ReXMessage rexMessage = new ReXMessage(
          tdlib.id(),
          message.chatId,
          message.id,
          senderId,
          contentType,
          textContent,
          mediaPath,
          message.date * 1000L,
          isDeleted,
          isEdited,
          oldContent,
          isEdited ? message.editDate * 1000L : 0,
          readTimestamp,
          false // isBotDialog - will be set separately if needed
        );
        
        dao.insert(rexMessage);
        Log.i(TAG, "Saved message: chatId=%d, msgId=%d, deleted=%b, edited=%b",
          message.chatId, message.id, isDeleted, isEdited);
        
      } catch (Exception e) {
        Log.e(TAG, "Error saving message to database", e);
      }
    });
  }
  
  private String extractTextFromContent(@NonNull TdApi.MessageContent content) {
    if (content.getConstructor() == TdApi.MessageText.CONSTRUCTOR) {
      return ((TdApi.MessageText) content).text.text;
    } else if (content.getConstructor() == TdApi.MessagePhoto.CONSTRUCTOR) {
      TdApi.MessagePhoto photo = (TdApi.MessagePhoto) content;
      return photo.caption != null ? photo.caption.text : "[Photo]";
    } else if (content.getConstructor() == TdApi.MessageVideo.CONSTRUCTOR) {
      TdApi.MessageVideo video = (TdApi.MessageVideo) content;
      return video.caption != null ? video.caption.text : "[Video]";
    } else if (content.getConstructor() == TdApi.MessageAudio.CONSTRUCTOR) {
      TdApi.MessageAudio audio = (TdApi.MessageAudio) content;
      return audio.caption != null ? audio.caption.text : "[Audio]";
    } else if (content.getConstructor() == TdApi.MessageDocument.CONSTRUCTOR) {
      TdApi.MessageDocument doc = (TdApi.MessageDocument) content;
      return doc.caption != null ? doc.caption.text : "[Document]";
    } else if (content.getConstructor() == TdApi.MessageVoiceNote.CONSTRUCTOR) {
      TdApi.MessageVoiceNote voice = (TdApi.MessageVoiceNote) content;
      return voice.caption != null ? voice.caption.text : "[Voice]";
    } else if (content.getConstructor() == TdApi.MessageSticker.CONSTRUCTOR) {
      return "[Sticker]";
    } else if (content.getConstructor() == TdApi.MessageAnimation.CONSTRUCTOR) {
      TdApi.MessageAnimation anim = (TdApi.MessageAnimation) content;
      return anim.caption != null ? anim.caption.text : "[Animation]";
    }
    return "[" + content.getClass().getSimpleName() + "]";
  }
  
  private String extractMediaPath(@NonNull TdApi.MessageContent content) {
    // Extract file paths for media content
    if (content.getConstructor() == TdApi.MessagePhoto.CONSTRUCTOR) {
      TdApi.MessagePhoto photo = (TdApi.MessagePhoto) content;
      if (photo.photo.sizes.length > 0) {
        return photo.photo.sizes[photo.photo.sizes.length - 1].photo.local.path;
      }
    } else if (content.getConstructor() == TdApi.MessageVideo.CONSTRUCTOR) {
      return ((TdApi.MessageVideo) content).video.video.local.path;
    } else if (content.getConstructor() == TdApi.MessageDocument.CONSTRUCTOR) {
      return ((TdApi.MessageDocument) content).document.document.local.path;
    }
    return null;
  }
  
  /**
   * Export database to file
   */
  public void exportDatabase(@NonNull Context context, @NonNull ExportCallback callback) {
    // TODO: Implement database export to JSON or custom format
    callback.onComplete(false, "Export not yet implemented");
  }
  
  /**
   * Import database from file
   */
  public void importDatabase(@NonNull Context context, @NonNull String filePath,
                              @NonNull ImportCallback callback) {
    // TODO: Implement database import from file
    callback.onComplete(false, "Import not yet implemented");
  }
  
  /**
   * Clear all spy mode data
   */
  public void clearDatabase(int accountId, @NonNull ClearCallback callback) {
    if (database == null) {
      callback.onComplete(false);
      return;
    }
    
    executor.execute(() -> {
      try {
        database.messageDao().clearAllMessages(accountId);
        callback.onComplete(true);
        Log.i(TAG, "Cleared spy mode database for account: %d", accountId);
      } catch (Exception e) {
        Log.e(TAG, "Error clearing database", e);
        callback.onComplete(false);
      }
    });
  }
  
  public interface ExportCallback {
    void onComplete(boolean success, String message);
  }
  
  public interface ImportCallback {
    void onComplete(boolean success, String message);
  }
  
  public interface ClearCallback {
    void onComplete(boolean success);
  }
}
