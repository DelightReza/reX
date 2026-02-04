Telegram X (TDLib/Kotlin) Implementation Specification

Project Architecture for Telegram X

1. Project Structure

```
org.thunderdog.challegram/
├── data/
│   ├── rex/
│   │   ├── RexPreferences.kt
│   │   ├── RexDatabase.kt
│   │   ├── RexRepository.kt
│   │   └── TdlibAccountExt.kt
│   └── Tdlib.kt (extended)
├── ui/
│   ├── rex/
│   │   ├── RexSettingsController.kt
│   │   ├── GhostModeController.kt
│   │   ├── SpySettingsController.kt
│   │   ├── CustomizationController.kt
│   │   └── RexMessageMenu.kt
│   ├── SettingsController.kt (modified)
│   ├── MessagesController.kt (modified)
│   └── MessageOptionsController.kt (modified)
└── core/
    └── rex/
        ├── GhostModeManager.kt
        ├── SpyFeatureManager.kt
        └── AttachmentManager.kt
```

2. Detailed Kotlin Implementation

2.1 Settings Integration

Modifying SettingsController.kt

```kotlin
// In SettingsController.kt - after Language section
override fun onCreateView(context: Context): View {
    // ... existing code ...
    
    // Find language item index
    val languageIndex = items.indexOfFirst { 
        it.id == R.id.btn_language 
    }
    
    // Add reX header after language
    items.add(languageIndex + 1, ListItem(
        type = ListItem.TYPE_HEADER,
        iconResId = R.drawable.baseline_settings_24,
        string = "reX"
    ))
    
    // Add reX settings item
    items.add(languageIndex + 2, ListItem(
        type = ListItem.TYPE_SETTING,
        id = R.id.btn_rex_settings,
        iconResId = R.drawable.baseline_visibility_off_24,
        string = "reX Settings"
    ).apply {
        setOnClickListener {
            navigationController?.navigateTo(
                RexSettingsController(context, tdlib)
            )
        }
    })
    
    // Add divider
    items.add(languageIndex + 3, ListItem(
        type = ListItem.TYPE_SHADOW_TOP
    ))
    
    // ... rest of existing code ...
}
```

2.2 RexSettingsController.kt

```kotlin
package org.thunderdog.challegram.ui.rex

import android.content.Context
import org.thunderdog.challegram.R
import org.thunderdog.challegram.core.Lang
import org.thunderdog.challegram.data.rex.RexPreferences
import org.thunderdog.challegram.telegram.Tdlib
import org.thunderdog.challegram.ui.ListItem
import org.thunderdog.challegram.ui.SettingsAdapter
import org.thunderdog.challegram.ui.SettingsBaseController

class RexSettingsController(
    context: Context,
    tdlib: Tdlib
) : SettingsBaseController(context, tdlib) {
    
    private val preferences = RexPreferences(context)
    
    override fun getTitleId(): Int = R.string.rex_settings
    
    override fun getId(): Int = R.id.controller_rex_settings
    
    override fun createAdapter(): SettingsAdapter {
        return SettingsAdapter(this).apply {
            addItems(buildItems())
        }
    }
    
    private fun buildItems(): List<ListItem> {
        val items = mutableListOf<ListItem>()
        
        // Ghost Mode Section
        items.add(ListItem(
            type = ListItem.TYPE_HEADER,
            string = Lang.getString(R.string.ghost_mode_header)
        ))
        items.add(ListItem(
            type = ListItem.TYPE_SHADOW_TOP
        ))
        items.add(ListItem(
            type = ListItem.TYPE_SETTING,
            id = R.id.btn_ghost_mode,
            iconResId = R.drawable.baseline_visibility_off_24,
            string = Lang.getString(R.string.ghost_mode)
        ).apply {
            setOnClickListener {
                navigationController?.navigateTo(
                    GhostModeController(context, tdlib)
                )
            }
        }))
        
        // Spy Section
        items.add(ListItem(
            type = ListItem.TYPE_SHADOW_BOTTOM
        ))
        items.add(ListItem(
            type = ListItem.TYPE_HEADER,
            string = Lang.getString(R.string.spy_features_header)
        ))
        items.add(ListItem(
            type = ListItem.TYPE_SHADOW_TOP
        ))
        items.add(ListItem(
            type = ListItem.TYPE_SETTING,
            id = R.id.btn_spy_settings,
            iconResId = R.drawable.baseline_search_24,
            string = Lang.getString(R.string.spy_features)
        ).apply {
            setOnClickListener {
                navigationController?.navigateTo(
                    SpySettingsController(context, tdlib)
                )
            }
        }))
        
        // Customization Section
        items.add(ListItem(
            type = ListItem.TYPE_SHADOW_BOTTOM
        ))
        items.add(ListItem(
            type = ListItem.TYPE_HEADER,
            string = Lang.getString(R.string.customization_header)
        ))
        items.add(ListItem(
            type = ListItem.TYPE_SHADOW_TOP
        ))
        items.add(ListItem(
            type = ListItem.TYPE_SETTING,
            id = R.id.btn_customization,
            iconResId = R.drawable.baseline_palette_24,
            string = Lang.getString(R.string.customization)
        ).apply {
            setOnClickListener {
                navigationController?.navigateTo(
                    CustomizationController(context, tdlib)
                )
            }
        }))
        items.add(ListItem(
            type = ListItem.TYPE_SHADOW_BOTTOM
        ))
        
        return items
    }
}
```

2.3 Ghost Mode Implementation

GhostModeManager.kt

```kotlin
package org.thunderdog.challegram.core.rex

import android.content.Context
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.thunderdog.challegram.data.rex.RexPreferences
import org.thunderdog.challegram.telegram.Tdlib
import org.thunderdog.challegram.telegram.TdlibManager
import org.drinkless.tdlib.TdApi
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

class GhostModeManager private constructor(
    private val context: Context,
    private val tdlib: Tdlib
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val preferences = RexPreferences(context)
    private val exclusions = mutableSetOf<String>()
    private val scheduledMessages = ConcurrentHashMap<Long, Job>()
    
    companion object {
        private val instances = mutableMapOf<Long, GhostModeManager>()
        
        fun getInstance(tdlib: Tdlib): GhostModeManager {
            return instances.getOrPut(tdlib.id()) {
                GhostModeManager(tdlib.context(), tdlib)
            }
        }
    }
    
    fun shouldSendReadReceipt(chatId: Long): Boolean {
        return !(preferences.isGhostModeEnabled() && 
                !exclusions.contains("dont_read_messages"))
    }
    
    fun shouldSendTyping(chatId: Long): Boolean {
        return !(preferences.isGhostModeEnabled() && 
                !exclusions.contains("dont_send_typing"))
    }
    
    fun shouldShowOnline(chatId: Long): Boolean {
        return !(preferences.isGhostModeEnabled() && 
                !exclusions.contains("dont_send_online"))
    }
    
    suspend fun scheduleMessage(
        chatId: Long,
        inputMessageContent: TdApi.InputMessageContent
    ): Long {
        if (!preferences.isScheduleMessagesEnabled()) {
            return sendMessageImmediately(chatId, inputMessageContent)
        }
        
        val delay = calculateDelay(inputMessageContent)
        
        return withContext(Dispatchers.IO) {
            delay(delay)
            
            // Set offline before sending
            tdlib.setOnline(false)
            
            val message = tdlib.sendMessage(
                chatId,
                0,
                0,
                null,
                null,
                inputMessageContent
            )
            
            // Keep offline
            tdlib.setOnline(false)
            
            message.id
        }
    }
    
    private fun calculateDelay(inputMessageContent: TdApi.InputMessageContent): Long {
        return when (inputMessageContent) {
            is TdApi.InputMessageVideo,
            is TdApi.InputMessageDocument -> {
                (15000 + (0..5000).random()).toLong()
            }
            is TdApi.InputMessagePhoto -> {
                (12000 + (0..3000).random()).toLong()
            }
            else -> {
                (10000 + (0..2000).random()).toLong()
            }
        }
    }
    
    private suspend fun sendMessageImmediately(
        chatId: Long,
        inputMessageContent: TdApi.InputMessageContent
    ): Long {
        return tdlib.sendMessage(
            chatId,
            0,
            0,
            null,
            null,
            inputMessageContent
        ).id
    }
    
    fun toggleExclusion(key: String) {
        if (exclusions.contains(key)) {
            exclusions.remove(key)
        } else {
            exclusions.add(key)
        }
        preferences.saveExclusions(exclusions.toList())
    }
    
    fun isExcluded(key: String): Boolean {
        return exclusions.contains(key)
    }
    
    fun destroy() {
        scope.cancel()
        scheduledMessages.values.forEach { it.cancel() }
        scheduledMessages.clear()
        instances.remove(tdlib.id())
    }
}
```

GhostModeController.kt

```kotlin
package org.thunderdog.challegram.ui.rex

import android.content.Context
import org.thunderdog.challegram.R
import org.thunderdog.challegram.core.Lang
import org.thunderdog.challegram.core.rex.GhostModeManager
import org.thunderdog.challegram.data.rex.RexPreferences
import org.thunderdog.challegram.telegram.Tdlib
import org.thunderdog.challegram.ui.*
import org.thunderdog.challegram.widget.CheckBoxSettingView

class GhostModeController(
    context: Context,
    tdlib: Tdlib
) : SettingsBaseController(context, tdlib) {
    
    private val preferences = RexPreferences(context)
    private val ghostManager = GhostModeManager.getInstance(tdlib)
    
    override fun getTitleId(): Int = R.string.ghost_mode
    
    override fun getId(): Int = R.id.controller_ghost_mode
    
    override fun createAdapter(): SettingsAdapter {
        return SettingsAdapter(this).apply {
            addItems(buildGhostModeItems())
        }
    }
    
    private fun buildGhostModeItems(): List<ListItem> {
        val items = mutableListOf<ListItem>()
        
        // Header
        items.add(ListItem(
            type = ListItem.TYPE_HEADER,
            string = Lang.getString(R.string.ghost_essentials)
        ))
        items.add(ListItem(
            type = ListItem.TYPE_SHADOW_TOP
        ))
        
        // Main toggle
        items.add(ListItem(
            type = ListItem.TYPE_CHECKBOX_SETTING,
            id = R.id.toggle_ghost_mode,
            string = Lang.getString(R.string.ghost_mode),
            isChecked = preferences.isGhostModeEnabled()
        ).apply {
            setOnClickListener {
                val newValue = !preferences.isGhostModeEnabled()
                preferences.setGhostModeEnabled(newValue)
                ghostManager.applyGhostMode(newValue)
                updateItem(id)
            }
            
            // Long press for exclusions
            setOnLongClickListener { v ->
                showExclusionsMenu(v)
                true
            }
        })
        
        // Sub-options
        val ghostOptions = listOf(
            Triple(R.id.toggle_dont_read_messages, R.string.dont_read_messages, "dont_read_messages"),
            Triple(R.id.toggle_dont_read_stories, R.string.dont_read_stories, "dont_read_stories"),
            Triple(R.id.toggle_dont_send_online, R.string.dont_send_online, "dont_send_online"),
            Triple(R.id.toggle_dont_send_typing, R.string.dont_send_typing, "dont_send_typing"),
            Triple(R.id.toggle_go_offline_auto, R.string.go_offline_auto, "go_offline_auto")
        )
        
        ghostOptions.forEach { (id, stringRes, key) ->
            items.add(ListItem(
                type = ListItem.TYPE_CHECKBOX_SETTING,
                id = id,
                string = Lang.getString(stringRes),
                isChecked = preferences.getGhostOption(key)
            ).apply {
                setOnClickListener {
                    val newValue = !preferences.getGhostOption(key)
                    preferences.setGhostOption(key, newValue)
                    updateItem(id)
                }
                
                // Long press to toggle exclusion
                setOnLongClickListener { v ->
                    ghostManager.toggleExclusion(key)
                    showToast("Exclusion toggled for ${Lang.getString(stringRes)}")
                    true
                }
            })
        }
        
        // Read on Interact
        items.add(ListItem(
            type = ListItem.TYPE_SEPARATOR_FULL
        ))
        items.add(ListItem(
            type = ListItem.TYPE_CHECKBOX_SETTING,
            id = R.id.toggle_read_on_interact,
            string = Lang.getString(R.string.read_on_interact),
            isChecked = preferences.isReadOnInteractEnabled()
        ).apply {
            setOnClickListener {
                val newValue = !preferences.isReadOnInteractEnabled()
                preferences.setReadOnInteractEnabled(newValue)
                updateItem(id)
            }
        })
        items.add(ListItem(
            type = ListItem.TYPE_DESCRIPTION,
            string = Lang.getString(R.string.read_on_interact_desc)
        ))
        
        // Schedule Messages
        items.add(ListItem(
            type = ListItem.TYPE_SEPARATOR_FULL
        ))
        items.add(ListItem(
            type = ListItem.TYPE_CHECKBOX_SETTING,
            id = R.id.toggle_schedule_messages,
            string = Lang.getString(R.string.schedule_messages),
            isChecked = preferences.isScheduleMessagesEnabled()
        ).apply {
            setOnClickListener {
                val newValue = !preferences.isScheduleMessagesEnabled()
                preferences.setScheduleMessagesEnabled(newValue)
                updateItem(id)
            }
        })
        items.add(ListItem(
            type = ListItem.TYPE_DESCRIPTION,
            string = Lang.getString(R.string.schedule_messages_desc)
        ))
        
        // Send without Sound
        items.add(ListItem(
            type = ListItem.TYPE_SEPARATOR_FULL
        ))
        items.add(ListItem(
            type = ListItem.TYPE_CHECKBOX_SETTING,
            id = R.id.toggle_send_without_sound,
            string = Lang.getString(R.string.send_without_sound),
            isChecked = preferences.isSendWithoutSoundEnabled()
        ))
        
        // Story Ghost Mode Alert
        items.add(ListItem(
            type = ListItem.TYPE_SEPARATOR_FULL
        ))
        items.add(ListItem(
            type = ListItem.TYPE_CHECKBOX_SETTING,
            id = R.id.toggle_story_ghost_alert,
            string = Lang.getString(R.string.story_ghost_alert),
            isChecked = preferences.isStoryGhostAlertEnabled()
        ))
        items.add(ListItem(
            type = ListItem.TYPE_DESCRIPTION,
            string = Lang.getString(R.string.story_ghost_alert_desc)
        ))
        
        items.add(ListItem(
            type = ListItem.TYPE_SHADOW_BOTTOM
        ))
        
        return items
    }
    
    private fun showExclusionsMenu(view: View) {
        // Show exclusions selection dialog
        // Implementation for selecting which options to exclude
    }
}
```

2.4 Spy Features Implementation

RexDatabase.kt (using Room)

```kotlin
package org.thunderdog.challegram.data.rex.database

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "deleted_messages",
    foreignKeys = [
        ForeignKey(
            entity = ChatEntity::class,
            parentColumns = ["id"],
            childColumns = ["chat_id"],
            onDelete = CASCADE
        )
    ],
    indices = [
        Index(value = ["chat_id"]),
        Index(value = ["original_message_id"])
    ]
)
data class DeletedMessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    @ColumnInfo(name = "original_message_id")
    val originalMessageId: Long,
    
    @ColumnInfo(name = "chat_id")
    val chatId: Long,
    
    @ColumnInfo(name = "sender_id")
    val senderId: Long,
    
    @ColumnInfo(name = "content_type")
    val contentType: Int,
    
    @ColumnInfo(name = "content_text")
    val contentText: String?,
    
    @ColumnInfo(name = "media_path")
    val mediaPath: String?,
    
    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    
    @ColumnInfo(name = "deleted_timestamp")
    val deletedTimestamp: Long
)

@Entity(
    tableName = "edit_history",
    foreignKeys = [
        ForeignKey(
            entity = DeletedMessageEntity::class,
            parentColumns = ["original_message_id"],
            childColumns = ["message_id"],
            onDelete = CASCADE
        )
    ]
)
data class EditHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    @ColumnInfo(name = "message_id")
    val messageId: Long,
    
    @ColumnInfo(name = "old_text")
    val oldText: String,
    
    @ColumnInfo(name = "new_text")
    val newText: String,
    
    @ColumnInfo(name = "edit_timestamp")
    val editTimestamp: Long
)

@Dao
interface RexDao {
    @Insert
    suspend fun insertDeletedMessage(message: DeletedMessageEntity): Long
    
    @Insert
    suspend fun insertEditHistory(history: EditHistoryEntity)
    
    @Query("SELECT * FROM deleted_messages WHERE chat_id = :chatId ORDER BY timestamp DESC")
    suspend fun getDeletedMessages(chatId: Long): List<DeletedMessageEntity>
    
    @Query("SELECT * FROM edit_history WHERE message_id = :messageId ORDER BY edit_timestamp DESC")
    suspend fun getEditHistory(messageId: Long): List<EditHistoryEntity>
    
    @Query("DELETE FROM deleted_messages WHERE chat_id = :chatId")
    suspend fun clearDeletedMessages(chatId: Long)
    
    @Query("SELECT COUNT(*) FROM deleted_messages")
    suspend fun getDeletedMessagesCount(): Int
}

@Database(
    entities = [DeletedMessageEntity::class, EditHistoryEntity::class, ChatEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RexDatabase : RoomDatabase() {
    abstract fun rexDao(): RexDao
    
    companion object {
        @Volatile
        private var INSTANCE: RexDatabase? = null
        
        fun getInstance(context: Context): RexDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    RexDatabase::class.java,
                    "rex_database.db"
                )
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
            }
        }
    }
}
```

SpyFeatureManager.kt

```kotlin
package org.thunderdog.challegram.core.rex

import android.content.Context
import kotlinx.coroutines.*
import org.thunderdog.challegram.data.rex.RexPreferences
import org.thunderdog.challegram.data.rex.database.RexDatabase
import org.thunderdog.challegram.data.rex.database.DeletedMessageEntity
import org.thunderdog.challegram.data.rex.database.EditHistoryEntity
import org.thunderdog.challegram.telegram.Tdlib
import org.drinkless.tdlib.TdApi
import java.io.File
import java.util.concurrent.ConcurrentHashMap

class SpyFeatureManager(
    private val context: Context,
    private val tdlib: Tdlib
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val preferences = RexPreferences(context)
    private val database = RexDatabase.getInstance(context)
    private val attachmentManager = AttachmentManager(context, preferences)
    
    private val chatListeners = ConcurrentHashMap<Long, ChatListener>()
    
    fun startMonitoring(chatId: Long) {
        if (!preferences.isSpyEnabled()) return
        
        val listener = ChatListener(chatId)
        chatListeners[chatId] = listener
        listener.start()
    }
    
    fun stopMonitoring(chatId: Long) {
        chatListeners[chatId]?.stop()
        chatListeners.remove(chatId)
    }
    
    inner class ChatListener(private val chatId: Long) {
        private var job: Job? = null
        
        fun start() {
            job = scope.launch {
                // Listen for deleted messages
                tdlib.listeners().addMessageDeletedListener(chatId) { messageId ->
                    handleDeletedMessage(chatId, messageId)
                }
                
                // Listen for edited messages
                tdlib.listeners().addMessageEditedListener(chatId) { message ->
                    handleEditedMessage(message)
                }
                
                // Monitor online status
                if (preferences.shouldSaveLastSeen()) {
                    monitorLastSeen()
                }
            }
        }
        
        fun stop() {
            job?.cancel()
            tdlib.listeners().removeMessageDeletedListener(chatId)
            tdlib.listeners().removeMessageEditedListener(chatId)
        }
        
        private suspend fun handleDeletedMessage(chatId: Long, messageId: Long) {
            if (!preferences.shouldSaveDeletedMessages()) return
            
            // Try to get message before deletion
            val message = try {
                tdlib.getMessage(chatId, messageId)
            } catch (e: Exception) {
                null
            }
            
            message?.let { msg ->
                val entity = DeletedMessageEntity(
                    originalMessageId = msg.id,
                    chatId = chatId,
                    senderId = msg.senderId,
                    contentType = getContentType(msg.content),
                    contentText = extractText(msg.content),
                    mediaPath = saveMediaIfNeeded(msg),
                    timestamp = msg.date,
                    deletedTimestamp = System.currentTimeMillis()
                )
                
                database.rexDao().insertDeletedMessage(entity)
                
                // Also save attachment if applicable
                if (preferences.shouldSaveAttachments()) {
                    attachmentManager.saveAttachment(msg)
                }
            }
        }
        
        private suspend fun handleEditedMessage(message: TdApi.Message) {
            if (!preferences.shouldSaveEditHistory()) return
            
            // Get previous version from database or cache
            val previousText = getPreviousMessageText(message.id)
            
            previousText?.let { oldText ->
                val newText = extractText(message.content)
                if (oldText != newText) {
                    val history = EditHistoryEntity(
                        messageId = message.id,
                        oldText = oldText,
                        newText = newText,
                        editTimestamp = System.currentTimeMillis()
                    )
                    database.rexDao().insertEditHistory(history)
                }
            }
        }
        
        private suspend fun monitorLastSeen() {
            // Track user actions to estimate last seen
            // Implementation depends on TDLib events
        }
        
        private fun getContentType(content: TdApi.MessageContent): Int {
            return when (content) {
                is TdApi.MessageText -> 1
                is TdApi.MessagePhoto -> 2
                is TdApi.MessageVideo -> 3
                is TdApi.MessageDocument -> 4
                else -> 0
            }
        }
        
        private fun extractText(content: TdApi.MessageContent): String? {
            return when (content) {
                is TdApi.MessageText -> content.text.text
                else -> null
            }
        }
        
        private suspend fun saveMediaIfNeeded(message: TdApi.Message): String? {
            if (!preferences.shouldSaveAttachments()) return null
            
            return attachmentManager.saveMediaMessage(message)
        }
    }
    
    suspend fun exportDatabase(): File {
        return withContext(Dispatchers.IO) {
            val dbFile = context.getDatabasePath("rex_database.db")
            val exportDir = File(context.getExternalFilesDir(null), "rex_exports")
            
            if (!exportDir.exists()) {
                exportDir.mkdirs()
            }
            
            val exportFile = File(exportDir, "rex_export_${System.currentTimeMillis()}.db")
            dbFile.copyTo(exportFile, overwrite = true)
            
            exportFile
        }
    }
    
    suspend fun importDatabase(file: File): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val dbFile = context.getDatabasePath("rex_database.db")
                file.copyTo(dbFile, overwrite = true)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
    
    fun destroy() {
        scope.cancel()
        chatListeners.values.forEach { it.stop() }
        chatListeners.clear()
        attachmentManager.cleanup()
    }
}
```

AttachmentManager.kt

```kotlin
package org.thunderdog.challegram.core.rex

import android.content.Context
import org.thunderdog.challegram.data.rex.RexPreferences
import org.drinkless.tdlib.TdApi
import java.io.File
import java.util.*

class AttachmentManager(
    private val context: Context,
    private val preferences: RexPreferences
) {
    private val attachmentsDir: File
    private val maxSizeBytes: Long
    private val lruCache = LinkedHashMap<String, File>(16, 0.75f, true)
    
    init {
        attachmentsDir = File(
            preferences.getAttachmentsPath() ?: context.getExternalFilesDir("rex_attachments")?.path
                ?: context.filesDir.resolve("rex_attachments").apply { mkdirs() }
        )
        
        maxSizeBytes = when (preferences.getMaxFolderSize()) {
            "300MB" -> 300 * 1024 * 1024L
            "1GB" -> 1024 * 1024 * 1024L
            "2GB" -> 2L * 1024 * 1024 * 1024L
            "5GB" -> 5L * 1024 * 1024 * 1024L
            "16GB" -> 16L * 1024 * 1024 * 1024L
            "No limit" -> Long.MAX_VALUE
            else -> 1024 * 1024 * 1024L // Default 1GB
        }
        
        loadExistingAttachments()
    }
    
    private fun loadExistingAttachments() {
        attachmentsDir.listFiles()?.forEach { file ->
            lruCache[file.absolutePath] = file
        }
    }
    
    suspend fun saveMediaMessage(message: TdApi.Message): String? {
        val file = when (val content = message.content) {
            is TdApi.MessagePhoto -> downloadFile(content.photo.sizes.last().photo)
            is TdApi.MessageVideo -> downloadFile(content.video.video)
            is TdApi.MessageDocument -> downloadFile(content.document.document)
            else -> null
        }
        
        file?.let {
            lruCache[it.absolutePath] = it
            enforceSizeLimit()
            return it.absolutePath
        }
        
        return null
    }
    
    private suspend fun downloadFile(file: TdApi.File): File? {
        // Implementation depends on Telegram X's file download system
        // This is a placeholder - you'll need to integrate with existing download system
        return withContext(Dispatchers.IO) {
            val localFile = File(attachmentsDir, "${UUID.randomUUID()}.${getExtension(file)}")
            // Actual download logic here
            localFile.takeIf { it.exists() }
        }
    }
    
    private fun getExtension(file: TdApi.File): String {
        val name = file.local.path
        return name.substringAfterLast('.', "")
    }
    
    private fun enforceSizeLimit() {
        var currentSize = calculateFolderSize()
        
        val iterator = lruCache.entries.iterator()
        while (currentSize > maxSizeBytes && iterator.hasNext()) {
            val (path, file) = iterator.next()
            val fileSize = file.length()
            if (file.delete()) {
                currentSize -= fileSize
                iterator.remove()
            }
        }
    }
    
    private fun calculateFolderSize(): Long {
        return attachmentsDir.walk()
            .filter { it.isFile }
            .map { it.length() }
            .sum()
    }
    
    fun cleanup() {
        // Cleanup resources if needed
    }
}
```

2.5 Message UI Enhancements

MessageOptionsController.kt (Extended)

```kotlin
package org.thunderdog.challegram.ui.rex

import android.content.Context
import android.view.View
import org.thunderdog.challegram.R
import org.thunderdog.challegram.core.Lang
import org.thunderdog.challegram.core.rex.GhostModeManager
import org.thunderdog.challegram.data.TD
import org.thunderdog.challegram.telegram.Tdlib
import org.thunderdog.challegram.ui.MessageOptionsController
import org.thunderdog.challegram.ui.PopupLayout
import org.drinkless.tdlib.TdApi

class RexMessageOptionsController(
    context: Context,
    tdlib: Tdlib,
    private val message: TdApi.Message,
    private val canMarkAsRead: Boolean
) : MessageOptionsController(context, tdlib, message) {
    
    private val ghostManager = GhostModeManager.getInstance(tdlib)
    private val rexDatabase = RexDatabase.getInstance(context)
    
    override fun onCreatePopupContent(context: Context): View {
        val view = super.onCreatePopupContent(context)
        
        // Add reX specific options
        addRexOptions()
        
        return view
    }
    
    private fun addRexOptions() {
        // Add "Mark as Read" if ghost mode is enabled and message is unread
        if (canMarkAsRead && ghostManager.shouldSendReadReceipt(message.chatId)) {
            addOption(R.drawable.baseline_done_all_24, Lang.getString(R.string.mark_as_read)) {
                markMessageAsRead()
            }
        }
        
        // Check if message is deleted (we have it saved)
        if (isMessageDeleted()) {
            addOption(R.drawable.baseline_delete_24, Lang.getString(R.string.view_deleted)) {
                showDeletedMessage()
            }
        }
        
        // Check if message has edit history
        if (hasEditHistory()) {
            addOption(R.drawable.baseline_history_24, Lang.getString(R.string.edit_history)) {
                showEditHistory()
            }
        }
        
        // Add reX submenu
        addOption(R.drawable.baseline_more_horiz_24, "reX") {
            showRexSubmenu()
        }
    }
    
    private fun markMessageAsRead() {
        tdlib.send(TdApi.ViewMessages(
            message.chatId,
            longArrayOf(message.id),
            TdApi.MessageSourceChatHistory(),
            true
        ))
    }
    
    private fun isMessageDeleted(): Boolean {
        // Check if we have this message in deleted messages database
        return runBlocking {
            rexDatabase.rexDao().getDeletedMessages(message.chatId)
                .any { it.originalMessageId == message.id }
        }
    }
    
    private fun showDeletedMessage() {
        runBlocking {
            val deleted = rexDatabase.rexDao().getDeletedMessages(message.chatId)
                .firstOrNull { it.originalMessageId == message.id }
            
            deleted?.let {
                // Show deleted message content
                showAlert("Deleted Message", it.contentText ?: "Media content")
            }
        }
    }
    
    private fun hasEditHistory(): Boolean {
        return runBlocking {
            rexDatabase.rexDao().getEditHistory(message.id).isNotEmpty()
        }
    }
    
    private fun showEditHistory() {
        runBlocking {
            val history = rexDatabase.rexDao().getEditHistory(message.id)
            
            val builder = StringBuilder()
            history.forEachIndexed { index, edit ->
                builder.append("Edit ${index + 1}:\n")
                builder.append("From: ${edit.oldText}\n")
                builder.append("To: ${edit.newText}\n\n")
            }
            
            showAlert("Edit History", builder.toString())
        }
    }
    
    private fun showRexSubmenu() {
        val popup = PopupLayout(context).apply {
            setTitle("reX Options")
            
            addOption("View All Deleted in Chat") {
                showAllDeletedMessages()
            }
            
            addOption("Read Exclusion") {
                toggleReadExclusion()
            }
            
            addOption("Type Exclusion") {
                toggleTypeExclusion()
            }
            
            addOption("Clear Deleted") {
                clearDeletedMessages()
            }
        }
        
        showPopup(popup)
    }
    
    private fun showAllDeletedMessages() {
        val controller = DeletedMessagesController(context, tdlib, message.chatId)
        navigationController?.navigateTo(controller)
    }
    
    private fun toggleReadExclusion() {
        ghostManager.toggleExclusion("dont_read_messages")
        showToast("Read exclusion toggled")
    }
    
    private fun toggleTypeExclusion() {
        ghostManager.toggleExclusion("dont_send_typing")
        showToast("Typing exclusion toggled")
    }
    
    private fun clearDeletedMessages() {
        runBlocking {
            rexDatabase.rexDao().clearDeletedMessages(message.chatId)
            showToast("Deleted messages cleared")
        }
    }
}
```

2.6 Restricted Forwarding

RestrictedForwardHelper.kt

```kotlin
package org.thunderdog.challegram.core.rex

import android.content.ClipboardManager
import android.content.Context
import kotlinx.coroutines.*
import org.thunderdog.challegram.data.TD
import org.thunderdog.challegram.telegram.Tdlib
import org.thunderdog.challegram.telegram.TdlibChat
import org.drinkless.tdlib.TdApi

class RestrictedForwardHelper(
    private val context: Context,
    private val tdlib: Tdlib
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    
    suspend fun forwardFromRestrictedChat(
        message: TdApi.Message,
        targetChatId: Long
    ): Boolean {
        val chat = tdlib.chat(message.chatId)
        
        if (!isChatRestricted(chat)) {
            // Normal forwarding
            return forwardNormally(message, targetChatId)
        }
        
        return when (val content = message.content) {
            is TdApi.MessageText -> forwardTextMessage(content, targetChatId)
            is TdApi.MessagePhoto -> forwardPhotoMessage(content, targetChatId)
            is TdApi.MessageVideo -> forwardVideoMessage(content, targetChatId)
            is TdApi.MessageDocument -> forwardDocumentMessage(content, targetChatId)
            else -> false
        }
    }
    
    private fun isChatRestricted(chat: TdlibChat): Boolean {
        // Check if chat has restrictions on forwarding
        return chat.permissions?.canSendMessages == false ||
               chat.permissions?.canSendMediaMessages == false
    }
    
    private suspend fun forwardNormally(
        message: TdApi.Message,
        targetChatId: Long
    ): Boolean {
        return try {
            tdlib.send(TdApi.ForwardMessages(
                targetChatId,
                0,
                message.chatId,
                longArrayOf(message.id),
                TdApi.MessageSendOptions(),
                false,
                false
            ))
            true
        } catch (e: Exception) {
            false
        }
    }
    
    private suspend fun forwardTextMessage(
        content: TdApi.MessageText,
        targetChatId: Long
    ): Boolean {
        // Copy to clipboard first
        clipboard.text = content.text.text
        
        // Send as new message without attribution
        val inputMessage = TdApi.InputMessageText(
            content.text,
            content.webPage,
            false
        )
        
        return try {
            tdlib.sendMessage(
                targetChatId,
                0,
                0,
                null,
                null,
                inputMessage
            )
            true
        } catch (e: Exception) {
            false
        }
    }
    
    private suspend fun forwardPhotoMessage(
        content: TdApi.MessagePhoto,
        targetChatId: Long
    ): Boolean {
        // Download photo first
        val photoFile = downloadFile(content.photo.sizes.last().photo)
        
        photoFile?.let { file ->
            val inputMessage = TdApi.InputMessagePhoto(
                TdApi.InputFileLocal(file.path),
                null,
                null,
                content.photo.width,
                content.photo.height,
                content.caption,
                0
            )
            
            return try {
                tdlib.sendMessage(
                    targetChatId,
                    0,
                    0,
                    null,
                    null,
                    inputMessage
                )
                true
            } catch (e: Exception) {
                false
            }
        }
        
        return false
    }
    
    private suspend fun forwardVideoMessage(
        content: TdApi.MessageVideo,
        targetChatId: Long
    ): Boolean {
        val videoFile = downloadFile(content.video.video)
        
        videoFile?.let { file ->
            val inputMessage = TdApi.InputMessageVideo(
                TdApi.InputFileLocal(file.path),
                null,
                null,
                content.video.duration,
                content.video.width,
                content.video.height,
                content.video.supportsStreaming,
                content.caption,
                0
            )
            
            return try {
                tdlib.sendMessage(
                    targetChatId,
                    0,
                    0,
                    null,
                    null,
                    inputMessage
                )
                true
            } catch (e: Exception) {
                false
            }
        }
        
        return false
    }
    
    private suspend fun downloadFile(file: TdApi.File): File? {
        return withContext(Dispatchers.IO) {
            // Use Telegram X's file download mechanism
            val localFile = File.createTempFile("rex_", ".tmp")
            
            // This is simplified - actual implementation needs to integrate with TDLib
            // and wait for download completion
            
            localFile.takeIf { it.exists() && it.length() > 0 }
        }
    }
    
    fun destroy() {
        scope.cancel()
    }
}
```

2.7 Integration Points

In MessagesController.kt (for message sending)

```kotlin
// In sendMessage method
private fun sendMessage(content: TdApi.InputMessageContent) {
    val ghostManager = GhostModeManager.getInstance(tdlib)
    
    if (ghostManager.shouldScheduleMessages()) {
        // Schedule message
        scope.launch {
            ghostManager.scheduleMessage(chatId, content)
        }
    } else {
        // Send immediately
        if (ghostManager.shouldSendWithoutSound()) {
            // Modify content to send without sound
            // TDLib doesn't directly support this, might need client-side handling
        }
        
        tdlib.sendMessage(chatId, 0, 0, null, null, content)
    }
}
```

In ChatListener.kt (for read receipts)

```kotlin
// When viewing messages
override fun onMessagesViewed(messages: LongArray) {
    val ghostManager = GhostModeManager.getInstance(tdlib)
    
    if (!ghostManager.shouldSendReadReceipt(chatId)) {
        return
    }
    
    tdlib.send(TdApi.ViewMessages(
        chatId,
        messages,
        TdApi.MessageSourceChatHistory(),
        true
    ))
}
```

3. String Resources (strings.xml)

```xml
<!-- reX Settings -->
<string name="rex_settings">reX Settings</string>
<string name="rex">reX</string>
<string name="ghost_mode">Ghost Mode</string>
<string name="spy_features">Spy</string>
<string name="customization">Customization</string>

<!-- Ghost Mode -->
<string name="ghost_mode_header">Ghost essentials</string>
<string name="dont_read_messages">Don\'t Read Messages</string>
<string name="dont_read_stories">Don\'t Read Stories</string>
<string name="dont_send_online">Don\'t Send Online</string>
<string name="dont_send_typing">Don\'t Send Typing</string>
<string name="go_offline_auto">Go Offline Automatically</string>
<string name="read_on_interact">Read on Interact</string>
<string name="read_on_interact_desc">Automatically marks a message as read when you send a new one or a reaction</string>
<string name="schedule_messages">Schedule Messages</string>
<string name="schedule_messages_desc">Automatically schedules outgoing messages to send after ~12 seconds (longer for media). Using this feature, you won\'t appear online. Avoid using on unreliable networks.</string>
<string name="send_without_sound">Send without Sound</string>
<string name="story_ghost_alert">Story Ghost Mode Alert</string>
<string name="story_ghost_alert_desc">Displays an alert before opening a story, suggesting you enable Ghost Mode</string>

<!-- Spy Features -->
<string name="spy_essentials">Spy essentials</string>
<string name="save_deleted_messages">Save Deleted Messages</string>
<string name="save_edits_history">Save Edits History</string>
<string name="save_in_bot_dialogs">Save in Bot Dialogs</string>
<string name="save_read_date">Save Read Date</string>
<string name="save_last_seen_date">Save Last Seen Date</string>
<string name="save_attachments">Save Attachments</string>
<string name="max_folder_size">Max folder size</string>
<string name="export_database">Export Database</string>
<string name="import_database">Import Database</string>
<string name="clear">Clear</string>

<!-- Message Options -->
<string name="mark_as_read">Mark as Read</string>
<string name="view_deleted">View Deleted</string>
<string name="edit_history">Edit History</string>
```

4. Build Configuration

build.gradle (Module level)

```gradle
dependencies {
    // Room Database
    implementation "androidx.room:room-runtime:2.4.3"
    implementation "androidx.room:room-ktx:2.4.3"
    kapt "androidx.room:room-compiler:2.4.3"
    
    // Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
    
    // File handling
    implementation "commons-io:commons-io:2.11.0"
}
```

5. Integration Instructions for GitHub Copilot

For Each Feature, Prompt Copilot With:

1. Ghost Mode Toggle:
   ```
   "In Telegram X (org.thunderdog.challegram), implement a GhostModeManager that:
   - Uses TDLib's TdApi for message operations
   - Schedules messages with random delays
   - Controls read receipts via ViewMessages
   - Manages exclusions with SharedPreferences
   "
   ```
2. Message Context Menu:
   ```
   "Extend Telegram X's MessageOptionsController to add:
   - 'Mark as Read' option when ghost mode is enabled
   - 'View Deleted' option that shows saved deleted messages
   - 'Edit History' option showing previous edits
   - reX submenu with additional options
   "
   ```
3. Restricted Forwarding:
   ```
   "Create a RestrictedForwardHelper for Telegram X that:
   - Checks chat.permissions.canSendMessages
   - Downloads media using TDLib's file system
   - Forwards as new messages without sender attribution
   - Uses InputFileLocal for downloaded files
   "
   ```
4. Database Operations:
   ```
   "Implement Room database for Telegram X with:
   - DeletedMessageEntity with TDLib message IDs
   - EditHistoryEntity tracking text changes
   - DAO methods using coroutines
   - Export/import with FileProvider
   "
   ```

This specification is tailored specifically for Telegram X's Kotlin/TDLib architecture and should work correctly with GitHub Copilot.
