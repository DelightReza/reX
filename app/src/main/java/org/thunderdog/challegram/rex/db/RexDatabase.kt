/*
 * This file is a part of reX
 * Copyright © 2024 (DelightReza)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package org.thunderdog.challegram.rex.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Data class for stored messages
 */
data class SavedMessage(
    val id: Long = 0,
    val chatId: Long,
    val messageId: Long,
    val senderId: Long,
    val text: String?,
    val timestamp: Int,
    var isDeleted: Boolean = false,
    val contentType: Int = 0, // TdApi content constructor
    val mediaPath: String? = null // Path to saved media file
)

/**
 * Data class for edit history
 */
data class EditHistory(
    val id: Int = 0,
    val originalMessageId: Long,
    val chatId: Long = 0,
    val oldText: String,
    val timestamp: Int
)

/**
 * SQLite-based database for reX message persistence
 * Using direct SQLite instead of Room to avoid kapt annotation processing issues
 */
class RexDatabase private constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_SAVED_MESSAGES_TABLE)
        db.execSQL(CREATE_EDIT_HISTORY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SAVED_MESSAGES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EDIT_HISTORY")
        onCreate(db)
    }

    fun rexDao() = RexDao(this)

    inner class RexDao(private val db: RexDatabase) {
        fun insertMessage(msg: SavedMessage) {
            val database = db.writableDatabase
            val values = ContentValues().apply {
                put(COL_CHAT_ID, msg.chatId)
                put(COL_MESSAGE_ID, msg.messageId)
                put(COL_SENDER_ID, msg.senderId)
                put(COL_TEXT, msg.text)
                put(COL_TIMESTAMP, msg.timestamp)
                put(COL_IS_DELETED, if (msg.isDeleted) 1 else 0)
                put(COL_CONTENT_TYPE, msg.contentType)
                put(COL_MEDIA_PATH, msg.mediaPath)
            }
            database.insertWithOnConflict(TABLE_SAVED_MESSAGES, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        }

        fun markAsDeleted(chatId: Long, msgIds: List<Long>) {
            val database = db.writableDatabase
            val values = ContentValues().apply {
                put(COL_IS_DELETED, 1)
            }
            val placeholders = msgIds.joinToString(",") { "?" }
            val whereClause = "$COL_CHAT_ID = ? AND $COL_MESSAGE_ID IN ($placeholders)"
            val whereArgs = arrayOf(chatId.toString()) + msgIds.map { it.toString() }.toTypedArray()
            database.update(TABLE_SAVED_MESSAGES, values, whereClause, whereArgs)
        }

        fun getDeletedMessages(chatId: Long): List<SavedMessage> {
            val database = db.readableDatabase
            val cursor = database.query(
                TABLE_SAVED_MESSAGES, null,
                "$COL_CHAT_ID = ? AND $COL_IS_DELETED = 1",
                arrayOf(chatId.toString()), null, null, null
            )
            val messages = mutableListOf<SavedMessage>()
            cursor.use {
                while (it.moveToNext()) {
                    messages.add(SavedMessage(
                        id = it.getLong(it.getColumnIndexOrThrow(COL_ID)),
                        chatId = it.getLong(it.getColumnIndexOrThrow(COL_CHAT_ID)),
                        messageId = it.getLong(it.getColumnIndexOrThrow(COL_MESSAGE_ID)),
                        senderId = it.getLong(it.getColumnIndexOrThrow(COL_SENDER_ID)),
                        text = it.getString(it.getColumnIndexOrThrow(COL_TEXT)),
                        timestamp = it.getInt(it.getColumnIndexOrThrow(COL_TIMESTAMP)),
                        isDeleted = it.getInt(it.getColumnIndexOrThrow(COL_IS_DELETED)) == 1,
                        contentType = it.getInt(it.getColumnIndexOrThrow(COL_CONTENT_TYPE)),
                        mediaPath = it.getString(it.getColumnIndexOrThrow(COL_MEDIA_PATH))
                    ))
                }
            }
            return messages
        }

        fun getAllDeletedMessages(): List<SavedMessage> {
            val database = db.readableDatabase
            val cursor = database.query(
                TABLE_SAVED_MESSAGES, null,
                "$COL_IS_DELETED = 1", null,
                null, null, "$COL_TIMESTAMP DESC"
            )
            val messages = mutableListOf<SavedMessage>()
            cursor.use {
                while (it.moveToNext()) {
                    messages.add(SavedMessage(
                        id = it.getLong(it.getColumnIndexOrThrow(COL_ID)),
                        chatId = it.getLong(it.getColumnIndexOrThrow(COL_CHAT_ID)),
                        messageId = it.getLong(it.getColumnIndexOrThrow(COL_MESSAGE_ID)),
                        senderId = it.getLong(it.getColumnIndexOrThrow(COL_SENDER_ID)),
                        text = it.getString(it.getColumnIndexOrThrow(COL_TEXT)),
                        timestamp = it.getInt(it.getColumnIndexOrThrow(COL_TIMESTAMP)),
                        isDeleted = it.getInt(it.getColumnIndexOrThrow(COL_IS_DELETED)) == 1,
                        contentType = it.getInt(it.getColumnIndexOrThrow(COL_CONTENT_TYPE)),
                        mediaPath = it.getString(it.getColumnIndexOrThrow(COL_MEDIA_PATH))
                    ))
                }
            }
            return messages
        }
        
        fun clearDeletedMessages(chatId: Long) {
            val database = db.writableDatabase
            database.delete(TABLE_SAVED_MESSAGES, "$COL_CHAT_ID = ? AND $COL_IS_DELETED = 1", arrayOf(chatId.toString()))
        }
        
        fun clearAllDeletedMessages() {
            val database = db.writableDatabase
            database.delete(TABLE_SAVED_MESSAGES, "$COL_IS_DELETED = 1", null)
        }

        fun insertEdit(edit: EditHistory) {
            val database = db.writableDatabase
            val values = ContentValues().apply {
                put(COL_ORIGINAL_MESSAGE_ID, edit.originalMessageId)
                put(COL_CHAT_ID, edit.chatId)
                put(COL_OLD_TEXT, edit.oldText)
                put(COL_TIMESTAMP, edit.timestamp)
            }
            database.insert(TABLE_EDIT_HISTORY, null, values)
        }

        fun getEdits(messageId: Long): List<EditHistory> {
            val database = db.readableDatabase
            val cursor = database.query(
                TABLE_EDIT_HISTORY, null,
                "$COL_ORIGINAL_MESSAGE_ID = ?", arrayOf(messageId.toString()),
                null, null, "$COL_TIMESTAMP DESC"
            )
            val edits = mutableListOf<EditHistory>()
            cursor.use {
                while (it.moveToNext()) {
                    edits.add(EditHistory(
                        id = it.getInt(it.getColumnIndexOrThrow(COL_ID)),
                        originalMessageId = it.getLong(it.getColumnIndexOrThrow(COL_ORIGINAL_MESSAGE_ID)),
                        chatId = it.getLong(it.getColumnIndexOrThrow(COL_CHAT_ID)),
                        oldText = it.getString(it.getColumnIndexOrThrow(COL_OLD_TEXT)),
                        timestamp = it.getInt(it.getColumnIndexOrThrow(COL_TIMESTAMP))
                    ))
                }
            }
            return edits
        }

        fun hasEdits(messageId: Long): Int {
            val database = db.readableDatabase
            val cursor = database.rawQuery(
                "SELECT COUNT(*) FROM $TABLE_EDIT_HISTORY WHERE $COL_ORIGINAL_MESSAGE_ID = ?",
                arrayOf(messageId.toString())
            )
            cursor.use {
                return if (it.moveToFirst()) it.getInt(0) else 0
            }
        }
    }

    companion object {
        private const val DATABASE_NAME = "rex_database.db"
        private const val DATABASE_VERSION = 3 // Increased for new columns

        private const val TABLE_SAVED_MESSAGES = "rex_saved_messages"
        private const val TABLE_EDIT_HISTORY = "edit_history"

        private const val COL_ID = "id"
        private const val COL_CHAT_ID = "chatId"
        private const val COL_MESSAGE_ID = "messageId"
        private const val COL_SENDER_ID = "senderId"
        private const val COL_TEXT = "text"
        private const val COL_TIMESTAMP = "timestamp"
        private const val COL_IS_DELETED = "isDeleted"
        private const val COL_CONTENT_TYPE = "contentType"
        private const val COL_MEDIA_PATH = "mediaPath"
        private const val COL_ORIGINAL_MESSAGE_ID = "originalMessageId"
        private const val COL_OLD_TEXT = "oldText"

        private const val CREATE_SAVED_MESSAGES_TABLE = """
            CREATE TABLE $TABLE_SAVED_MESSAGES (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_CHAT_ID INTEGER NOT NULL,
                $COL_MESSAGE_ID INTEGER NOT NULL,
                $COL_SENDER_ID INTEGER NOT NULL,
                $COL_TEXT TEXT,
                $COL_TIMESTAMP INTEGER NOT NULL,
                $COL_IS_DELETED INTEGER NOT NULL DEFAULT 0,
                $COL_CONTENT_TYPE INTEGER NOT NULL DEFAULT 0,
                $COL_MEDIA_PATH TEXT
            )
        """

        private const val CREATE_EDIT_HISTORY_TABLE = """
            CREATE TABLE $TABLE_EDIT_HISTORY (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_ORIGINAL_MESSAGE_ID INTEGER NOT NULL,
                $COL_CHAT_ID INTEGER NOT NULL DEFAULT 0,
                $COL_OLD_TEXT TEXT NOT NULL,
                $COL_TIMESTAMP INTEGER NOT NULL
            )
        """

        @Volatile
        private var INSTANCE: RexDatabase? = null

        @JvmStatic
        fun get(context: Context): RexDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = RexDatabase(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
