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

import android.os.Handler
import android.os.Looper
import org.drinkless.tdlib.TdApi
import org.thunderdog.challegram.telegram.Tdlib
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Schedules outgoing messages with a delay to hide online status
 * Default delay: ~12 seconds
 */
object RexScheduler {
    private const val DEFAULT_DELAY_MS = 12000L // 12 seconds
    
    private val handler = Handler(Looper.getMainLooper())
    private val messageQueue = ConcurrentLinkedQueue<ScheduledMessage>()
    
    data class ScheduledMessage(
        val tdlib: Tdlib,
        val request: TdApi.SendMessage,
        val delayMs: Long = DEFAULT_DELAY_MS
    )
    
    /**
     * Schedule a message to be sent after delay
     * @param tdlib Tdlib instance
     * @param request SendMessage request
     * @param delayMs Delay in milliseconds (default: 12000ms)
     */
    fun scheduleMessage(tdlib: Tdlib, request: TdApi.SendMessage, delayMs: Long = DEFAULT_DELAY_MS) {
        val scheduled = ScheduledMessage(tdlib, request, delayMs)
        messageQueue.offer(scheduled)
        
        handler.postDelayed({
            sendScheduledMessage(scheduled)
        }, delayMs)
    }
    
    /**
     * Send a scheduled message
     */
    private fun sendScheduledMessage(scheduled: ScheduledMessage) {
        messageQueue.remove(scheduled)
        scheduled.tdlib.send(scheduled.request, scheduled.tdlib.okHandler())
    }
    
    /**
     * Cancel all scheduled messages
     */
    fun cancelAll() {
        handler.removeCallbacksAndMessages(null)
        messageQueue.clear()
    }
    
    /**
     * Cancel scheduled messages for a specific chat
     */
    fun cancelForChat(chatId: Long) {
        val toRemove = messageQueue.filter { it.request.chatId == chatId }
        messageQueue.removeAll(toRemove.toSet())
    }
    
    /**
     * Get number of pending scheduled messages
     */
    fun getPendingCount(): Int = messageQueue.size
    
    /**
     * Get number of pending scheduled messages for a chat
     */
    fun getPendingCountForChat(chatId: Long): Int {
        return messageQueue.count { it.request.chatId == chatId }
    }
}
