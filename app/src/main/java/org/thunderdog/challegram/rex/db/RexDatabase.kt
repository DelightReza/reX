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

@Entity(tableName = "rex_saved_messages")
data class SavedMessage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val chatId: Long,
    val messageId: Long,
    val text: String?,
    val senderId: Long,
    val timestamp: Int,
    var isDeleted: Boolean = false // If true, this was deleted by the sender
)

@Dao
interface RexDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(msg: SavedMessage)

    @Query("UPDATE rex_saved_messages SET isDeleted = 1 WHERE chatId = :chatId AND messageId IN (:msgIds)")
    fun markAsDeleted(chatId: Long, msgIds: List<Long>)

    @Query("SELECT * FROM rex_saved_messages WHERE chatId = :chatId AND isDeleted = 1")
    fun getDeletedMessages(chatId: Long): List<SavedMessage>

    @Query("SELECT * FROM rex_saved_messages WHERE isDeleted = 1 ORDER BY timestamp DESC")
    fun getAllDeletedMessages(): List<SavedMessage>
}

@Database(entities = [SavedMessage::class], version = 1)
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
                ).allowMainThreadQueries().build() // For simplicity. In prod use coroutines.
                INSTANCE = instance
                instance
            }
        }
    }
}
