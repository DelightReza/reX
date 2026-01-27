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

object RexConfig {
    private const val PREFS_NAME = "rex_config"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // --- GHOST MODE SETTINGS ---
    var isGhostMode: Boolean
        get() = prefs.getBoolean("ghost_enabled", false)
        set(v) = prefs.edit().putBoolean("ghost_enabled", v).apply()

    var ghostNoRead: Boolean
        get() = prefs.getBoolean("ghost_no_read", true)
        set(v) = prefs.edit().putBoolean("ghost_no_read", v).apply()

    var ghostNoOnline: Boolean
        get() = prefs.getBoolean("ghost_no_online", true)
        set(v) = prefs.edit().putBoolean("ghost_no_online", v).apply()

    var ghostNoTyping: Boolean
        get() = prefs.getBoolean("ghost_no_typing", true)
        set(v) = prefs.edit().putBoolean("ghost_no_typing", v).apply()

    // --- SPY MODE SETTINGS ---
    var isSpyEnabled: Boolean
        get() = prefs.getBoolean("spy_enabled", false)
        set(v) = prefs.edit().putBoolean("spy_enabled", v).apply()
        
    var saveDeletedMessages: Boolean
        get() = prefs.getBoolean("spy_save_deleted", true)
        set(v) = prefs.edit().putBoolean("spy_save_deleted", v).apply()

    // --- FORCE READ REQUEST (for bypass) ---
    var isForceReadRequest: Boolean = false
}
