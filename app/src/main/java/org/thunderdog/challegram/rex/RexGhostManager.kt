/*
 * This file is a part of reX
 * Copyright © 2024 (DelightReza)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package org.thunderdog.challegram.rex

import android.content.Context
import org.thunderdog.challegram.rex.db.RexDatabase
import java.util.concurrent.ConcurrentHashMap

object RexGhostManager {
    // Stores IDs of messages that were deleted by server but kept by us
    // Using ConcurrentHashMap for thread safety (in-memory cache)
    private val ghostMessageIds = ConcurrentHashMap.newKeySet<Long>()

    fun addGhostMessage(messageId: Long) {
        ghostMessageIds.add(messageId)
    }

    fun markAsGhost(messageId: Long) {
        ghostMessageIds.add(messageId)
    }

    fun isGhost(messageId: Long): Boolean {
        return ghostMessageIds.contains(messageId)
    }

    fun removeGhost(messageId: Long) {
        ghostMessageIds.remove(messageId)
    }

    fun clear() {
        ghostMessageIds.clear()
    }
    
    /**
     * Load ghost messages for a specific chat from database into memory cache
     */
    fun loadGhostMessagesForChat(context: Context, chatId: Long) {
        try {
            val db = RexDatabase.get(context)
            val deletedMessages = db.rexDao().getDeletedMessages(chatId)
            deletedMessages.forEach { msg ->
                ghostMessageIds.add(msg.messageId)
            }
        } catch (e: Exception) {
            // Silently fail to avoid crashes
        }
    }
    
    /**
     * Load all ghost messages from database into memory cache
     * Call this on app start
     */
    fun loadAllGhostMessages(context: Context) {
        try {
            val db = RexDatabase.get(context)
            val deletedMessages = db.rexDao().getAllDeletedMessages()
            deletedMessages.forEach { msg ->
                ghostMessageIds.add(msg.messageId)
            }
        } catch (e: Exception) {
            // Silently fail to avoid crashes
        }
    }
}
