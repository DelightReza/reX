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
    @get:JvmName("getGhostNoRead")
    @set:JvmName("setGhostNoRead")
    var noReadRequest: Boolean
        get() = prefs.getBoolean("ghost_no_read", true)
        set(v) = prefs.edit().putBoolean("ghost_no_read", v).apply()

    /** Don't send online status updates */
    @get:JvmName("getGhostNoOnline")
    @set:JvmName("setGhostNoOnline")
    var ghostNoOnline: Boolean
        get() = prefs.getBoolean("ghost_no_online", true)
        set(v) = prefs.edit().putBoolean("ghost_no_online", v).apply()

    /** Don't send typing indicators (SetChatAction) */
    @get:JvmName("getGhostNoTyping")
    @set:JvmName("setGhostNoTyping")
    var noTyping: Boolean
        get() = prefs.getBoolean("ghost_no_typing", true)
        set(v) = prefs.edit().putBoolean("ghost_no_typing", v).apply()

    /** Don't view stories (suppress ViewStory) */
    @get:JvmName("getGhostNoStories")
    @set:JvmName("setGhostNoStories")
    var noStories: Boolean
        get() = prefs.getBoolean("ghost_no_stories", true)
        set(v) = prefs.edit().putBoolean("ghost_no_stories", v).apply()

    /** Go offline automatically */
    @get:JvmName("getGoOfflineAuto")
    @set:JvmName("setGoOfflineAuto")
    var goOfflineAuto: Boolean
        get() = prefs.getBoolean("ghost_offline_auto", false)
        set(v) = prefs.edit().putBoolean("ghost_offline_auto", v).apply()

    /** Mark as read when sending message/reaction (Read on Interact) */
    @get:JvmName("getReadOnInteract")
    @set:JvmName("setReadOnInteract")
    var readOnInteract: Boolean
        get() = prefs.getBoolean("ghost_read_on_interact", false)
        set(v) = prefs.edit().putBoolean("ghost_read_on_interact", v).apply()

    /** Schedule messages with delay (~12s to hide online status) */
    @get:JvmName("getScheduleMessages")
    @set:JvmName("setScheduleMessages")
    var scheduleMessages: Boolean
        get() = prefs.getBoolean("ghost_schedule_messages", false)
        set(v) = prefs.edit().putBoolean("ghost_schedule_messages", v).apply()

    /** Send messages without sound by default */
    @get:JvmName("getSendWithoutSound")
    @set:JvmName("setSendWithoutSound")
    var sendWithoutSound: Boolean
        get() = prefs.getBoolean("ghost_send_without_sound", false)
        set(v) = prefs.edit().putBoolean("ghost_send_without_sound", v).apply()

    /** Show alert before opening stories (Story Ghost Mode Alert) */
    @get:JvmName("getStoryGhostAlert")
    @set:JvmName("setStoryGhostAlert")
    var storyGhostAlert: Boolean
        get() = prefs.getBoolean("ghost_story_alert", false)
        set(v) = prefs.edit().putBoolean("ghost_story_alert", v).apply()

    /** Locked Ghost Mode options (prevent Master Toggle from changing) */
    @JvmName("getLockedGhostOptions")
    fun getLockedGhostOptions(): Set<String> {
        return prefs.getStringSet("ghost_locked_options", emptySet()) ?: emptySet()
    }

    @JvmName("setLockedGhostOptions")
    fun setLockedGhostOptions(locked: Set<String>) {
        prefs.edit().putStringSet("ghost_locked_options", locked).apply()
    }

    // --- SPY MODE SETTINGS (Persistent) ---
    
    /** Enable/disable Spy Mode (message preservation) - DEPRECATED: use individual feature checks */
    @Deprecated("No longer used - check individual spy features instead", ReplaceWith("hasAnySpyFeatureEnabled()"))
    @get:JvmName("isSpyEnabled")
    @set:JvmName("setSpyEnabled")
    var isSpyMode: Boolean
        get() = hasAnySpyFeatureEnabled()
        set(v) = Unit // No-op, kept for compatibility
    
    /** Check if any spy feature is enabled */
    @JvmName("hasAnySpyFeatureEnabled")
    fun hasAnySpyFeatureEnabled(): Boolean {
        return saveDeletedMessages || saveEditsHistory || saveInBotDialogs || 
               saveReadDate || saveLastSeenDate || saveAttachments
    }
        
    /** Save deleted messages to database */
    @get:JvmName("getSaveDeletedMessages")
    @set:JvmName("setSaveDeletedMessages")
    var saveDeletedMessages: Boolean
        get() = prefs.getBoolean("spy_save_deleted", true)
        set(v) = prefs.edit().putBoolean("spy_save_deleted", v).apply()

    /** Save edits history to database */
    @get:JvmName("getSaveEditsHistory")
    @set:JvmName("setSaveEditsHistory")
    var saveEditsHistory: Boolean
        get() = prefs.getBoolean("spy_save_edits", true)
        set(v) = prefs.edit().putBoolean("spy_save_edits", v).apply()

    /** Save messages in bot dialogs */
    @get:JvmName("getSaveInBotDialogs")
    @set:JvmName("setSaveInBotDialogs")
    var saveInBotDialogs: Boolean
        get() = prefs.getBoolean("spy_save_bot_dialogs", false)
        set(v) = prefs.edit().putBoolean("spy_save_bot_dialogs", v).apply()

    /** Save read date (local storage) */
    @get:JvmName("getSaveReadDate")
    @set:JvmName("setSaveReadDate")
    var saveReadDate: Boolean
        get() = prefs.getBoolean("spy_save_read_date", false)
        set(v) = prefs.edit().putBoolean("spy_save_read_date", v).apply()

    /** Save last seen date (approximate logging) */
    @get:JvmName("getSaveLastSeenDate")
    @set:JvmName("setSaveLastSeenDate")
    var saveLastSeenDate: Boolean
        get() = prefs.getBoolean("spy_save_last_seen", false)
        set(v) = prefs.edit().putBoolean("spy_save_last_seen", v).apply()

    /** Save attachments */
    @get:JvmName("getSaveAttachments")
    @set:JvmName("setSaveAttachments")
    var saveAttachments: Boolean
        get() = prefs.getBoolean("spy_save_attachments", false)
        set(v) = prefs.edit().putBoolean("spy_save_attachments", v).apply()

    /** Attachments folder path */
    @get:JvmName("getAttachmentsFolder")
    @set:JvmName("setAttachmentsFolder")
    var attachmentsFolder: String
        get() = prefs.getString("spy_attachments_folder", "") ?: ""
        set(v) = prefs.edit().putString("spy_attachments_folder", v).apply()

    /** Max folder size in MB (0 = no limit) */
    @get:JvmName("getMaxFolderSize")
    @set:JvmName("setMaxFolderSize")
    var maxFolderSize: Int
        get() = prefs.getInt("spy_max_folder_size", 0)
        set(v) = prefs.edit().putInt("spy_max_folder_size", v).apply()

    // --- RESTRICTED CONTENT SETTINGS (Persistent) ---
    
    /** Allow forwarding/saving of restricted content */
    @get:JvmName("isSaveRestricted")
    @set:JvmName("setSaveRestricted")
    var saveRestricted: Boolean
        get() = prefs.getBoolean("save_restricted", true)
        set(v) = prefs.edit().putBoolean("save_restricted", v).apply()

    // --- CUSTOMIZATION SETTINGS (Persistent) ---

    /** Disable colorful replies */
    @get:JvmName("getDisableColorfulReplies")
    @set:JvmName("setDisableColorfulReplies")
    var disableColorfulReplies: Boolean
        get() = prefs.getBoolean("custom_disable_colorful_replies", false)
        set(v) = prefs.edit().putBoolean("custom_disable_colorful_replies", v).apply()

    /** Translucent deleted messages */
    @get:JvmName("getTranslucentDeletedMessages")
    @set:JvmName("setTranslucentDeletedMessages")
    var translucentDeletedMessages: Boolean
        get() = prefs.getBoolean("custom_translucent_deleted", true)
        set(v) = prefs.edit().putBoolean("custom_translucent_deleted", v).apply()

    /** Deleted mark icon (0=trash, 1=cross, 2=ghost, etc.) */
    @get:JvmName("getDeletedMarkIcon")
    @set:JvmName("setDeletedMarkIcon")
    var deletedMarkIcon: Int
        get() = prefs.getInt("custom_deleted_mark_icon", 0)
        set(v) = prefs.edit().putInt("custom_deleted_mark_icon", v).apply()

    /** Deleted mark color (ARGB) */
    @get:JvmName("getDeletedMarkColor")
    @set:JvmName("setDeletedMarkColor")
    var deletedMarkColor: Int
        get() = prefs.getInt("custom_deleted_mark_color", 0xFFFF0000.toInt())
        set(v) = prefs.edit().putInt("custom_deleted_mark_color", v).apply()

    /** Drawer order (comma-separated list of item IDs) */
    @get:JvmName("getDrawerOrder")
    @set:JvmName("setDrawerOrder")
    var drawerOrder: String
        get() = prefs.getString("custom_drawer_order", "") ?: ""
        set(v) = prefs.edit().putString("custom_drawer_order", v).apply()

    /** Hidden drawer items (comma-separated list of item IDs) */
    @get:JvmName("getHiddenDrawerItems")
    @set:JvmName("setHiddenDrawerItems")
    var hiddenDrawerItems: String
        get() = prefs.getString("custom_hidden_drawer_items", "") ?: ""
        set(v) = prefs.edit().putString("custom_hidden_drawer_items", v).apply()

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

}
