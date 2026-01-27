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

import android.content.Context
import androidx.room.*

/**
 * Entity for storing saved/deleted messages
 */
@Entity(tableName = "rex_saved_messages")
data class SavedMessage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val chatId: Long,
    val messageId: Long,
    val text: String?,
    val senderId: Long = 0,
    val timestamp: Int,
    var isDeleted: Boolean = false
)

/**
 * Entity for storing message edit history
 */
@Entity(tableName = "edit_history")
data class EditHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val originalMessageId: Long,
    val chatId: Long = 0,
    val oldText: String,
    val timestamp: Int
)

/**
 * DAO for reX database operations
 */
@Dao
interface RexDao {
    // SavedMessage operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(msg: SavedMessage)

    @Query("UPDATE rex_saved_messages SET isDeleted = 1 WHERE chatId = :chatId AND messageId IN (:msgIds)")
    fun markAsDeleted(chatId: Long, msgIds: List<Long>)

    @Query("SELECT * FROM rex_saved_messages WHERE chatId = :chatId AND isDeleted = 1")
    fun getDeletedMessages(chatId: Long): List<SavedMessage>

    @Query("SELECT * FROM rex_saved_messages WHERE isDeleted = 1 ORDER BY timestamp DESC")
    fun getAllDeletedMessages(): List<SavedMessage>

    // Edit history operations
    @Insert
    fun insertEdit(edit: EditHistory)

    @Query("SELECT * FROM edit_history WHERE originalMessageId = :messageId ORDER BY timestamp DESC")
    fun getEdits(messageId: Long): List<EditHistory>

    @Query("SELECT COUNT(*) FROM edit_history WHERE originalMessageId = :messageId")
    fun hasEdits(messageId: Long): Int
}

/**
 * Room database for reX data persistence
 */
@Database(entities = [SavedMessage::class, EditHistory::class], version = 2, exportSchema = false)
abstract class RexDatabase : RoomDatabase() {
    abstract fun rexDao(): RexDao

    companion object {
        @Volatile private var INSTANCE: RexDatabase? = null

        fun get(context: Context): RexDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RexDatabase::class.java,
                    "rex_database"
                )
                // TODO: For production, implement proper migration strategy
                // Currently uses destructive migration which deletes all data on schema changes
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build() // TODO: For production, use coroutines/background threads
                INSTANCE = instance
                instance
            }
        }
    }
}
