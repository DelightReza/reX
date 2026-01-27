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
import android.content.SharedPreferences
import org.drinkless.tdlib.TdApi

/**
 * Central configuration module for reX features
 * Manages persistent settings via SharedPreferences and volatile runtime state
 */
object RexConfig {
    private const val PREFS_NAME = "rex_config"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // --- GHOST MODE SETTINGS (Persistent) ---
    
    /** Enable/disable Ghost Mode globally */
    var isGhostMode: Boolean
        get() = prefs.getBoolean("ghost_enabled", false)
        set(v) = prefs.edit().putBoolean("ghost_enabled", v).apply()

    /** Don't send read receipts (ViewMessages) */
    var noReadRequest: Boolean
        get() = prefs.getBoolean("ghost_no_read", true)
        set(v) = prefs.edit().putBoolean("ghost_no_read", v).apply()

    /** Don't send online status updates */
    var ghostNoOnline: Boolean
        get() = prefs.getBoolean("ghost_no_online", true)
        set(v) = prefs.edit().putBoolean("ghost_no_online", v).apply()

    /** Don't send typing indicators (SetChatAction) */
    var noTyping: Boolean
        get() = prefs.getBoolean("ghost_no_typing", true)
        set(v) = prefs.edit().putBoolean("ghost_no_typing", v).apply()

    // --- SPY MODE SETTINGS (Persistent) ---
    
    /** Enable/disable Spy Mode (message preservation) */
    var isSpyMode: Boolean
        get() = prefs.getBoolean("spy_enabled", false)
        set(v) = prefs.edit().putBoolean("spy_enabled", v).apply()
        
    /** Save deleted messages to database */
    var saveDeletedMessages: Boolean
        get() = prefs.getBoolean("spy_save_deleted", true)
        set(v) = prefs.edit().putBoolean("spy_save_deleted", v).apply()

    // --- RESTRICTED CONTENT SETTINGS (Persistent) ---
    
    /** Allow forwarding/saving of restricted content */
    var saveRestricted: Boolean
        get() = prefs.getBoolean("save_restricted", true)
        set(v) = prefs.edit().putBoolean("save_restricted", v).apply()

    // --- VOLATILE RUNTIME STATE ---
    
    /** 
     * Temporarily bypass Ghost Mode for one ViewMessages request
     * Must be reset to false after use
     */
    @Volatile
    var isForceReadRequest: Boolean = false

    /**
     * Stores a restricted message waiting to be cloned/forwarded
     * Used for the two-step forward flow: select message -> select target chat -> send clone
     */
    @Volatile
    var pendingRestrictedMessage: TdApi.Message? = null

    // --- LEGACY COMPATIBILITY ALIASES ---
    
    /** Alias for isGhostMode for backwards compatibility */
    @Deprecated("Use isGhostMode instead", ReplaceWith("isGhostMode"))
    var ghostEnabled: Boolean
        get() = isGhostMode
        set(v) { isGhostMode = v }

    /** Alias for noReadRequest for backwards compatibility */
    @Deprecated("Use noReadRequest instead", ReplaceWith("noReadRequest"))
    var ghostNoRead: Boolean
        get() = noReadRequest
        set(v) { noReadRequest = v }

    /** Alias for noTyping for backwards compatibility */
    @Deprecated("Use noTyping instead", ReplaceWith("noTyping"))
    var ghostNoTyping: Boolean
        get() = noTyping
        set(v) { noTyping = v }

    /** Alias for isSpyMode for backwards compatibility */
    @Deprecated("Use isSpyMode instead", ReplaceWith("isSpyMode"))
    var isSpyEnabled: Boolean
        get() = isSpyMode
        set(v) { isSpyMode = v }
}
