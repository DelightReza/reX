package org.telegram.messenger;

import android.appwidget.AppWidgetManager;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.collection.LongSparseArray;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.AyuConstants;
import com.radolyn.ayugram.AyuState;
import com.radolyn.ayugram.controllers.messages.SaveMessageRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLiteDatabase;
import org.telegram.SQLite.SQLiteException;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.Timer;
import org.telegram.messenger.TopicsController;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.LongSparseIntArray;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Adapters.DialogsSearchAdapter;
import org.telegram.p023ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p023ui.Stories.StoriesController;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.Vector;
import org.telegram.tgnet.p022tl.TL_account;
import org.telegram.tgnet.p022tl.TL_stars;
import org.telegram.tgnet.p022tl.TL_stories;
import p017j$.util.Objects;

/* loaded from: classes.dex */
public class MessagesStorage extends BaseController {
    public static final String[] DATABASE_TABLES;
    public static final int FORUM_TYPE_BOT = 8;
    public static final int FORUM_TYPE_CHAT = 1;
    public static final int FORUM_TYPE_CHAT_TABS = 2;
    public static final int FORUM_TYPE_DIRECT = 4;
    public static final int LAST_DB_VERSION = 168;
    public static final int SENT_FILE_TYPE_AUDIO = 1;
    public static final int SENT_FILE_TYPE_AUDIO_ENCRYPTED = 4;
    public static final int SENT_FILE_TYPE_PHOTO = 0;
    public static final int SENT_FILE_TYPE_PHOTO_ENCRYPTED = 3;
    public static final int SENT_FILE_TYPE_PHOTO_HIGH_QUALITY = 6;
    public static final int SENT_FILE_TYPE_PHOTO_HIGH_QUALITY_ENCRYPTED = 7;
    public static final int SENT_FILE_TYPE_VIDEO = 2;
    public static final int SENT_FILE_TYPE_VIDEO_ENCRYPTED = 5;
    private int archiveUnreadCount;
    private int[][] bots;
    private File cacheFile;
    private int[][] channels;
    private int[][] contacts;
    private SQLiteDatabase database;
    private boolean databaseCreated;
    private boolean databaseMigrationInProgress;
    private final ArrayList<MessagesController.DialogFilter> dialogFilters;
    private final SparseArray<MessagesController.DialogFilter> dialogFiltersMap;
    private final LongSparseIntArray dialogIsForumTyped;
    private LongSparseArray dialogsWithMentions;
    private LongSparseArray dialogsWithUnread;
    private int[][] groups;
    private int lastDateValue;
    private int lastPtsValue;
    private int lastQtsValue;
    private int lastSavedDate;
    private int lastSavedPts;
    private int lastSavedQts;
    private int lastSavedSeq;
    private int lastSecretVersion;
    private int lastSeqValue;
    private final AtomicLong lastTaskId;
    private int mainUnreadCount;
    private int[] mentionChannels;
    private int[] mentionGroups;
    private int[][] nonContacts;
    private final CountDownLatch openSync;
    private volatile int pendingArchiveUnreadCount;
    private volatile int pendingMainUnreadCount;
    private int secretG;
    private byte[] secretPBytes;
    private File shmCacheFile;
    public boolean showClearDatabaseAlert;
    private DispatchQueue storageQueue;
    private final SparseArray<ArrayList<Runnable>> tasks;
    boolean tryRecover;
    private final LongSparseArray unknownDialogsIds;
    private File walCacheFile;
    private static volatile MessagesStorage[] Instance = new MessagesStorage[16];
    private static final Object[] lockObjects = new Object[16];

    /* loaded from: classes4.dex */
    public interface BooleanCallback {
        void run(boolean z);
    }

    public interface IntCallback {
        void run(int i);
    }

    /* loaded from: classes4.dex */
    public interface LongCallback {
        void run(long j);
    }

    /* loaded from: classes4.dex */
    public interface StringCallback {
        void run(String str);
    }

    static {
        for (int i = 0; i < 16; i++) {
            lockObjects[i] = new Object();
        }
        DATABASE_TABLES = new String[]{"messages_holes", "media_holes_v2", "scheduled_messages_v2", "quick_replies", "messages_v2", "download_queue", "user_contacts_v7", "user_phones_v7", "dialogs", "dialog_filter", "dialog_filter_ep", "dialog_filter_pin_v2", "randoms_v2", "enc_tasks_v4", "messages_seq", PluginsConstants.PARAMS, "media_v4", "bot_keyboard", "bot_keyboard_topics", "chat_settings_v2", "user_settings", "chat_pinned_v2", "chat_pinned_count", "chat_hints", "botcache", "users_data", "users", "chats", "enc_chats", "channel_users_v2", "channel_admins_v3", "contacts", "dialog_photos", "dialog_settings", "web_recent_v3", "stickers_v2", "stickers_featured", "stickers_dice", "stickersets", "hashtag_recent_v2", "webpage_pending_v2", "sent_files_v2", "search_recent", "media_counts_v2", "keyvalue", "bot_info_v2", "pending_tasks", "requested_holes", "sharing_locations", "shortcut_widget", "emoji_keywords_v2", "emoji_keywords_info_v2", "wallpapers2", "unread_push_messages", "polls_v2", "reactions", "reaction_mentions", "downloading_documents", "animated_emoji", "attach_menu_bots", "premium_promo", "emoji_statuses", "messages_holes_topics", "messages_topics", "saved_dialogs", "media_topics", "media_holes_topics", "topics", "media_counts_topics", "reaction_mentions_topics", "emoji_groups"};
    }

    public static MessagesStorage getInstance(int i) {
        MessagesStorage messagesStorage;
        MessagesStorage messagesStorage2 = Instance[i];
        if (messagesStorage2 != null) {
            return messagesStorage2;
        }
        synchronized (lockObjects[i]) {
            try {
                messagesStorage = Instance[i];
                if (messagesStorage == null) {
                    MessagesStorage[] messagesStorageArr = Instance;
                    MessagesStorage messagesStorage3 = new MessagesStorage(i);
                    messagesStorageArr[i] = messagesStorage3;
                    messagesStorage = messagesStorage3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return messagesStorage;
    }

    private void ensureOpened() {
        try {
            this.openSync.await();
        } catch (Throwable unused) {
        }
    }

    public int getLastDateValue() {
        ensureOpened();
        return this.lastDateValue;
    }

    public void setLastDateValue(int i) {
        ensureOpened();
        this.lastDateValue = i;
    }

    public int getLastPtsValue() {
        ensureOpened();
        return this.lastPtsValue;
    }

    public int getMainUnreadCount() {
        return this.mainUnreadCount;
    }

    public int getArchiveUnreadCount() {
        return this.archiveUnreadCount;
    }

    public void setLastPtsValue(int i) {
        ensureOpened();
        this.lastPtsValue = i;
    }

    public int getLastQtsValue() {
        ensureOpened();
        return this.lastQtsValue;
    }

    public void setLastQtsValue(int i) {
        ensureOpened();
        this.lastQtsValue = i;
    }

    public int getLastSeqValue() {
        ensureOpened();
        return this.lastSeqValue;
    }

    public void setLastSeqValue(int i) {
        ensureOpened();
        this.lastSeqValue = i;
    }

    public int getLastSecretVersion() {
        ensureOpened();
        return this.lastSecretVersion;
    }

    public void setLastSecretVersion(int i) {
        ensureOpened();
        this.lastSecretVersion = i;
    }

    public byte[] getSecretPBytes() {
        ensureOpened();
        return this.secretPBytes;
    }

    public void setSecretPBytes(byte[] bArr) {
        ensureOpened();
        this.secretPBytes = bArr;
    }

    public int getSecretG() {
        ensureOpened();
        return this.secretG;
    }

    public void setSecretG(int i) {
        ensureOpened();
        this.secretG = i;
    }

    public MessagesStorage(int i) {
        super(i);
        this.lastTaskId = new AtomicLong(System.currentTimeMillis());
        this.tasks = new SparseArray<>();
        this.lastDateValue = 0;
        this.lastPtsValue = 0;
        this.lastQtsValue = 0;
        this.lastSeqValue = 0;
        this.lastSecretVersion = 0;
        this.secretPBytes = null;
        this.secretG = 0;
        this.lastSavedSeq = 0;
        this.lastSavedPts = 0;
        this.lastSavedDate = 0;
        this.lastSavedQts = 0;
        this.dialogFilters = new ArrayList<>();
        this.dialogFiltersMap = new SparseArray<>();
        this.unknownDialogsIds = new LongSparseArray();
        this.openSync = new CountDownLatch(1);
        this.dialogIsForumTyped = new LongSparseIntArray();
        this.contacts = new int[][]{new int[2], new int[2]};
        this.nonContacts = new int[][]{new int[2], new int[2]};
        this.bots = new int[][]{new int[2], new int[2]};
        this.channels = new int[][]{new int[2], new int[2]};
        this.groups = new int[][]{new int[2], new int[2]};
        this.mentionChannels = new int[2];
        this.mentionGroups = new int[2];
        this.dialogsWithMentions = new LongSparseArray();
        this.dialogsWithUnread = new LongSparseArray();
        DispatchQueue dispatchQueue = new DispatchQueue("storageQueue_" + i);
        this.storageQueue = dispatchQueue;
        dispatchQueue.setPriority(8);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda228
            @Override // java.lang.Runnable
            public final void run() throws Exception {
                this.f$0.lambda$new$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() throws Exception {
        openDatabase(1);
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public DispatchQueue getStorageQueue() {
        return this.storageQueue;
    }

    public void bindTaskToGuid(Runnable runnable, int i) {
        ArrayList<Runnable> arrayList = this.tasks.get(i);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.tasks.put(i, arrayList);
        }
        arrayList.add(runnable);
    }

    public void cancelTasksForGuid(int i) {
        ArrayList<Runnable> arrayList = this.tasks.get(i);
        if (arrayList == null) {
            return;
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.storageQueue.cancelRunnable(arrayList.get(i2));
        }
        this.tasks.remove(i);
    }

    public void completeTaskForGuid(Runnable runnable, int i) {
        ArrayList<Runnable> arrayList = this.tasks.get(i);
        if (arrayList == null) {
            return;
        }
        arrayList.remove(runnable);
        if (arrayList.isEmpty()) {
            this.tasks.remove(i);
        }
    }

    public long getDatabaseSize() {
        File file = this.cacheFile;
        long length = file != null ? file.length() : 0L;
        File file2 = this.shmCacheFile;
        return file2 != null ? length + file2.length() : length;
    }

    public void openDatabase(int i) throws Exception {
        if (!NativeLoader.loaded()) {
            int i2 = 0;
            while (!NativeLoader.loaded()) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i2++;
                if (i2 > 5) {
                    break;
                }
            }
        }
        File filesDirFixed = ApplicationLoader.getFilesDirFixed();
        if (this.currentAccount != 0) {
            File file = new File(filesDirFixed, "account" + this.currentAccount + "/");
            file.mkdirs();
            filesDirFixed = file;
        }
        this.cacheFile = new File(filesDirFixed, "cache4.db");
        this.walCacheFile = new File(filesDirFixed, "cache4.db-wal");
        this.shmCacheFile = new File(filesDirFixed, "cache4.db-shm");
        this.databaseCreated = false;
        boolean zExists = this.cacheFile.exists();
        try {
            SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(this.cacheFile.getPath());
            this.database = sQLiteDatabase;
            sQLiteDatabase.executeFast("PRAGMA secure_delete = ON").stepThis().dispose();
            this.database.executeFast("PRAGMA temp_store = MEMORY").stepThis().dispose();
            this.database.executeFast("PRAGMA journal_mode = " + AyuConfig.getWALMode()).stepThis().dispose();
            this.database.executeFast("PRAGMA journal_size_limit = 10485760").stepThis().dispose();
            if (!zExists) {
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1157d("create new database");
                }
                createTables(this.database);
            } else {
                int iIntValue = this.database.executeInt("PRAGMA user_version", new Object[0]).intValue();
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1157d("current db version = " + iIntValue);
                }
                if (iIntValue == 0) {
                    throw new Exception("malformed");
                }
                try {
                    SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT seq, pts, date, qts, lsv, sg, pbytes FROM params WHERE id = 1", new Object[0]);
                    if (sQLiteCursorQueryFinalized.next()) {
                        this.lastSeqValue = sQLiteCursorQueryFinalized.intValue(0);
                        this.lastPtsValue = sQLiteCursorQueryFinalized.intValue(1);
                        this.lastDateValue = sQLiteCursorQueryFinalized.intValue(2);
                        this.lastQtsValue = sQLiteCursorQueryFinalized.intValue(3);
                        this.lastSecretVersion = sQLiteCursorQueryFinalized.intValue(4);
                        this.secretG = sQLiteCursorQueryFinalized.intValue(5);
                        if (sQLiteCursorQueryFinalized.isNull(6)) {
                            this.secretPBytes = null;
                        } else {
                            byte[] bArrByteArrayValue = sQLiteCursorQueryFinalized.byteArrayValue(6);
                            this.secretPBytes = bArrByteArrayValue;
                            if (bArrByteArrayValue != null && bArrByteArrayValue.length == 1) {
                                this.secretPBytes = null;
                            }
                        }
                    }
                    sQLiteCursorQueryFinalized.dispose();
                } catch (Exception e2) {
                    FileLog.m1160e(e2);
                    if (e2.getMessage() != null && e2.getMessage().contains("malformed")) {
                        throw new RuntimeException("malformed");
                    }
                    try {
                        this.database.executeFast("CREATE TABLE IF NOT EXISTS params(id INTEGER PRIMARY KEY, seq INTEGER, pts INTEGER, date INTEGER, qts INTEGER, lsv INTEGER, sg INTEGER, pbytes BLOB)").stepThis().dispose();
                        this.database.executeFast("INSERT INTO params VALUES(1, 0, 0, 0, 0, 0, 0, NULL)").stepThis().dispose();
                    } catch (Exception e3) {
                        FileLog.m1160e(e3);
                    }
                }
                if (iIntValue < 168) {
                    try {
                        updateDbToLastVersion(iIntValue);
                    } catch (Exception e4) {
                        if (BuildVars.DEBUG_PRIVATE_VERSION) {
                            throw e4;
                        }
                        FileLog.m1160e(e4);
                        throw new RuntimeException("malformed");
                    }
                }
            }
            this.databaseCreated = true;
        } catch (Exception e5) {
            FileLog.m1160e(e5);
            if (i < 3 && e5.getMessage() != null && e5.getMessage().contains("malformed")) {
                if (i == 2) {
                    cleanupInternal(true);
                    clearLoadingDialogsOffsets();
                } else {
                    cleanupInternal(false);
                }
                openDatabase(i == 1 ? 2 : 3);
                return;
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda188
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openDatabase$1();
            }
        });
        loadDialogFilters();
        loadUnreadMessages();
        loadPendingTasks();
        try {
            this.openSync.countDown();
        } catch (Throwable unused) {
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda189
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openDatabase$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDatabase$1() {
        if (this.databaseMigrationInProgress) {
            this.databaseMigrationInProgress = false;
            NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.onDatabaseMigration, Boolean.FALSE);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openDatabase$2() {
        this.showClearDatabaseAlert = false;
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.onDatabaseOpened, new Object[0]);
    }

    private void clearLoadingDialogsOffsets() {
        for (int i = 0; i < 2; i++) {
            getUserConfig().setDialogsLoadOffset(i, 0, 0, 0L, 0L, 0L, 0L);
            getUserConfig().setTotalDialogsCount(i, 0);
        }
        getUserConfig().saveConfig(false);
    }

    private boolean recoverDatabase() throws Exception {
        this.database.close();
        boolean zRecoverDatabase = DatabaseMigrationHelper.recoverDatabase(this.cacheFile, this.walCacheFile, this.shmCacheFile, this.currentAccount);
        FileLog.m1158e("Database restored = " + zRecoverDatabase);
        if (zRecoverDatabase) {
            try {
                SQLiteDatabase sQLiteDatabase = new SQLiteDatabase(this.cacheFile.getPath());
                this.database = sQLiteDatabase;
                sQLiteDatabase.executeFast("PRAGMA secure_delete = ON").stepThis().dispose();
                this.database.executeFast("PRAGMA temp_store = MEMORY").stepThis().dispose();
                this.database.executeFast("PRAGMA journal_mode = " + AyuConfig.getWALMode()).stepThis().dispose();
                this.database.executeFast("PRAGMA journal_size_limit = 10485760").stepThis().dispose();
            } catch (SQLiteException e) {
                FileLog.m1160e(new Exception(e));
                zRecoverDatabase = false;
            }
        }
        if (!zRecoverDatabase) {
            cleanupInternal(true);
            openDatabase(1);
            zRecoverDatabase = this.databaseCreated;
            FileLog.m1158e("Try create new database = " + zRecoverDatabase);
        }
        if (zRecoverDatabase) {
            reset();
        }
        return zRecoverDatabase;
    }

    public static void createTables(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.executeFast("CREATE TABLE messages_holes(uid INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, start));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_end_messages_holes ON messages_holes(uid, end);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_holes_v2(uid INTEGER, type INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, type, start));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_end_media_holes_v2 ON media_holes_v2(uid, type, end);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE scheduled_messages_v2(mid INTEGER, uid INTEGER, send_state INTEGER, date INTEGER, data BLOB, ttl INTEGER, replydata BLOB, reply_to_message_id INTEGER, PRIMARY KEY(mid, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS send_state_idx_scheduled_messages_v2 ON scheduled_messages_v2(mid, send_state, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_date_idx_scheduled_messages_v2 ON scheduled_messages_v2(uid, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS reply_to_idx_scheduled_messages_v2 ON scheduled_messages_v2(mid, reply_to_message_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS idx_to_reply_scheduled_messages_v2 ON scheduled_messages_v2(reply_to_message_id, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE messages_v2(mid INTEGER, uid INTEGER, read_state INTEGER, send_state INTEGER, date INTEGER, data BLOB, out INTEGER, ttl INTEGER, media INTEGER, replydata BLOB, imp INTEGER, mention INTEGER, forwards INTEGER, replies_data BLOB, thread_reply_id INTEGER, is_channel INTEGER, reply_to_message_id INTEGER, custom_params BLOB, group_id INTEGER, reply_to_story_id INTEGER, PRIMARY KEY(mid, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_read_out_idx_messages_v2 ON messages_v2(uid, mid, read_state, out);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_date_mid_idx_messages_v2 ON messages_v2(uid, date, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS mid_out_idx_messages_v2 ON messages_v2(mid, out);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS task_idx_messages_v2 ON messages_v2(uid, out, read_state, ttl, date, send_state);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS send_state_idx_messages_v2 ON messages_v2(mid, send_state, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mention_idx_messages_v2 ON messages_v2(uid, mention, read_state);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS is_channel_idx_messages_v2 ON messages_v2(mid, is_channel);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS reply_to_idx_messages_v2 ON messages_v2(mid, reply_to_message_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS idx_to_reply_messages_v2 ON messages_v2(reply_to_message_id, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_groupid_messages_v2 ON messages_v2(uid, mid, group_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE saved_dialogs(did INTEGER, date INTEGER, last_mid INTEGER, pinned INTEGER, flags INTEGER, folder_id INTEGER, last_mid_group INTEGER, count INTEGER, forumChatId INTEGER, unread_count INTEGER, max_read_id INTEGER, read_outbox INTEGER, PRIMARY KEY (did, forumChatId))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS date_idx_dialogs ON saved_dialogs(date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS last_mid_idx_dialogs ON saved_dialogs(last_mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS folder_id_idx_dialogs ON saved_dialogs(folder_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS flags_idx_dialogs ON saved_dialogs(flags);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS forum_idx_dialogs ON saved_dialogs(forumChatId);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE download_queue(uid INTEGER, type INTEGER, date INTEGER, data BLOB, parent TEXT, PRIMARY KEY (uid, type));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS type_date_idx_download_queue ON download_queue(type, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE user_contacts_v7(key TEXT PRIMARY KEY, uid INTEGER, fname TEXT, sname TEXT, imported INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE user_phones_v7(key TEXT, phone TEXT, sphone TEXT, deleted INTEGER, PRIMARY KEY (key, phone))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS sphone_deleted_idx_user_phones ON user_phones_v7(sphone, deleted);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialogs(did INTEGER PRIMARY KEY, date INTEGER, unread_count INTEGER, last_mid INTEGER, inbox_max INTEGER, outbox_max INTEGER, last_mid_i INTEGER, unread_count_i INTEGER, pts INTEGER, date_i INTEGER, pinned INTEGER, flags INTEGER, folder_id INTEGER, data BLOB, unread_reactions INTEGER, last_mid_group INTEGER, ttl_period INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS date_idx_dialogs ON dialogs(date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS last_mid_idx_dialogs ON dialogs(last_mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS unread_count_idx_dialogs ON dialogs(unread_count);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS last_mid_i_idx_dialogs ON dialogs(last_mid_i);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS unread_count_i_idx_dialogs ON dialogs(unread_count_i);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS folder_id_idx_dialogs ON dialogs(folder_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS flags_idx_dialogs ON dialogs(flags);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialog_filter(id INTEGER PRIMARY KEY, ord INTEGER, unread_count INTEGER, flags INTEGER, title TEXT, color INTEGER DEFAULT -1, emoticon TEXT, entities BLOB, noanimate INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialog_filter_ep(id INTEGER, peer INTEGER, PRIMARY KEY (id, peer))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialog_filter_pin_v2(id INTEGER, peer INTEGER, pin INTEGER, PRIMARY KEY (id, peer))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE randoms_v2(random_id INTEGER, mid INTEGER, uid INTEGER, PRIMARY KEY (random_id, mid, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS mid_idx_randoms_v2 ON randoms_v2(mid, uid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE enc_tasks_v4(mid INTEGER, uid INTEGER, date INTEGER, media INTEGER, PRIMARY KEY(mid, uid, media))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS date_idx_enc_tasks_v4 ON enc_tasks_v4(date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE messages_seq(mid INTEGER PRIMARY KEY, seq_in INTEGER, seq_out INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS seq_idx_messages_seq ON messages_seq(seq_in, seq_out);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE params(id INTEGER PRIMARY KEY, seq INTEGER, pts INTEGER, date INTEGER, qts INTEGER, lsv INTEGER, sg INTEGER, pbytes BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("INSERT INTO params VALUES(1, 0, 0, 0, 0, 0, 0, NULL)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_v4(mid INTEGER, uid INTEGER, date INTEGER, type INTEGER, data BLOB, PRIMARY KEY(mid, uid, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_type_date_idx_media_v4 ON media_v4(uid, mid, type, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE bot_keyboard(uid INTEGER PRIMARY KEY, mid INTEGER, info BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS bot_keyboard_idx_mid_v2 ON bot_keyboard(mid, uid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE bot_keyboard_topics(uid INTEGER, tid INTEGER, mid INTEGER, info BLOB, PRIMARY KEY(uid, tid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS bot_keyboard_topics_idx_mid_v2 ON bot_keyboard_topics(mid, uid, tid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE chat_settings_v2(uid INTEGER PRIMARY KEY, info BLOB, pinned INTEGER, online INTEGER, inviter INTEGER, links INTEGER, participants_count INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS chat_settings_pinned_idx ON chat_settings_v2(uid, pinned) WHERE pinned != 0;").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE user_settings(uid INTEGER PRIMARY KEY, info BLOB, pinned INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS user_settings_pinned_idx ON user_settings(uid, pinned) WHERE pinned != 0;").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE chat_pinned_v2(uid INTEGER, mid INTEGER, data BLOB, PRIMARY KEY (uid, mid));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE chat_pinned_count(uid INTEGER PRIMARY KEY, count INTEGER, end INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE chat_hints(did INTEGER, type INTEGER, rating REAL, date INTEGER, PRIMARY KEY(did, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS chat_hints_rating_idx ON chat_hints(rating);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE botcache(id TEXT PRIMARY KEY, date INTEGER, data BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS botcache_date_idx ON botcache(date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE users_data(uid INTEGER PRIMARY KEY, about TEXT)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE users(uid INTEGER PRIMARY KEY, name TEXT, status INTEGER, data BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE chats(uid INTEGER PRIMARY KEY, name TEXT, data BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE enc_chats(uid INTEGER PRIMARY KEY, user INTEGER, name TEXT, data BLOB, g BLOB, authkey BLOB, ttl INTEGER, layer INTEGER, seq_in INTEGER, seq_out INTEGER, use_count INTEGER, exchange_id INTEGER, key_date INTEGER, fprint INTEGER, fauthkey BLOB, khash BLOB, in_seq_no INTEGER, admin_id INTEGER, mtproto_seq INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE channel_users_v2(did INTEGER, uid INTEGER, date INTEGER, data BLOB, PRIMARY KEY(did, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE channel_admins_v3(did INTEGER, uid INTEGER, data BLOB, PRIMARY KEY(did, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE contacts(uid INTEGER PRIMARY KEY, mutual INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialog_photos(uid INTEGER, id INTEGER, num INTEGER, data BLOB, PRIMARY KEY (uid, id))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialog_photos_count(uid INTEGER PRIMARY KEY, count INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE dialog_settings(did INTEGER PRIMARY KEY, flags INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE web_recent_v3(id TEXT, type INTEGER, image_url TEXT, thumb_url TEXT, local_url TEXT, width INTEGER, height INTEGER, size INTEGER, date INTEGER, document BLOB, PRIMARY KEY (id, type));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE stickers_v2(id INTEGER PRIMARY KEY, data BLOB, date INTEGER, hash INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE stickers_featured(id INTEGER PRIMARY KEY, data BLOB, unread BLOB, date INTEGER, hash INTEGER, premium INTEGER, emoji INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE stickers_dice(emoji TEXT PRIMARY KEY, data BLOB, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE hashtag_recent_v2(id TEXT PRIMARY KEY, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE webpage_pending_v2(id INTEGER, mid INTEGER, uid INTEGER, PRIMARY KEY (id, mid, uid));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE sent_files_v2(uid TEXT, type INTEGER, data BLOB, parent TEXT, PRIMARY KEY (uid, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE search_recent(did INTEGER PRIMARY KEY, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_counts_v2(uid INTEGER, type INTEGER, count INTEGER, old INTEGER, PRIMARY KEY(uid, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE keyvalue(id TEXT PRIMARY KEY, value TEXT)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE bot_info_v2(uid INTEGER, dialogId INTEGER, info BLOB, PRIMARY KEY(uid, dialogId))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE pending_tasks(id INTEGER PRIMARY KEY, data BLOB);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE requested_holes(uid INTEGER, seq_out_start INTEGER, seq_out_end INTEGER, PRIMARY KEY (uid, seq_out_start, seq_out_end));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE sharing_locations(uid INTEGER PRIMARY KEY, mid INTEGER, date INTEGER, period INTEGER, message BLOB, proximity INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE stickersets2(id INTEGER PRIMATE KEY, data BLOB, hash INTEGER, date INTEGER, short_name TEXT);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS stickersets2_id_index ON stickersets2(id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS stickersets2_id_short_name ON stickersets2(id, short_name);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS stickers_featured_emoji_index ON stickers_featured(emoji);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE shortcut_widget(id INTEGER, did INTEGER, ord INTEGER, PRIMARY KEY (id, did));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS shortcut_widget_did ON shortcut_widget(did);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE emoji_keywords_v2(lang TEXT, keyword TEXT, emoji TEXT, PRIMARY KEY(lang, keyword, emoji));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS emoji_keywords_v2_keyword ON emoji_keywords_v2(keyword);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE emoji_keywords_info_v2(lang TEXT PRIMARY KEY, alias TEXT, version INTEGER, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE wallpapers2(uid INTEGER PRIMARY KEY, data BLOB, num INTEGER)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS wallpapers_num ON wallpapers2(num);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE unread_push_messages(uid INTEGER, mid INTEGER, random INTEGER, date INTEGER, data BLOB, fm TEXT, name TEXT, uname TEXT, flags INTEGER, topicId INTEGER, is_reaction INTEGER, PRIMARY KEY(uid, mid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS unread_push_messages_idx_date ON unread_push_messages(date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS unread_push_messages_idx_random ON unread_push_messages(random);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE polls_v2(mid INTEGER, uid INTEGER, id INTEGER, PRIMARY KEY (mid, uid));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS polls_id_v2 ON polls_v2(id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE reactions(data BLOB, hash INTEGER, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE reaction_mentions(message_id INTEGER, state INTEGER, dialog_id INTEGER, PRIMARY KEY(message_id, dialog_id))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS reaction_mentions_did ON reaction_mentions(dialog_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE downloading_documents(data BLOB, hash INTEGER, id INTEGER, state INTEGER, date INTEGER, PRIMARY KEY(hash, id));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE animated_emoji(document_id INTEGER PRIMARY KEY, data BLOB);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE attach_menu_bots(data BLOB, hash INTEGER, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE premium_promo(data BLOB, date INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE emoji_statuses(data BLOB, type INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE messages_holes_topics(uid INTEGER, topic_id INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, topic_id, start));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_end_messages_holes ON messages_holes_topics(uid, topic_id, end);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE messages_topics(mid INTEGER, uid INTEGER, topic_id INTEGER, read_state INTEGER, send_state INTEGER, date INTEGER, data BLOB, out INTEGER, ttl INTEGER, media INTEGER, replydata BLOB, imp INTEGER, mention INTEGER, forwards INTEGER, replies_data BLOB, thread_reply_id INTEGER, is_channel INTEGER, reply_to_message_id INTEGER, custom_params BLOB, reply_to_story_id INTEGER, PRIMARY KEY(mid, topic_id, uid))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_date_mid_idx_messages_topics ON messages_topics(uid, date, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS mid_out_idx_messages_topics ON messages_topics(mid, out);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS task_idx_messages_topics ON messages_topics(uid, out, read_state, ttl, date, send_state);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS send_state_idx_messages_topics ON messages_topics(mid, send_state, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS is_channel_idx_messages_topics ON messages_topics(mid, is_channel);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS reply_to_idx_messages_topics ON messages_topics(mid, reply_to_message_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS idx_to_reply_messages_topics ON messages_topics(reply_to_message_id, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS mid_uid_messages_topics ON messages_topics(mid, uid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_read_out_idx_messages_topics ON messages_topics(uid, topic_id, mid, read_state, out);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mention_idx_messages_topics ON messages_topics(uid, topic_id, mention, read_state);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_topic_id_messages_topics ON messages_topics(uid, topic_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_topic_id_date_mid_messages_topics ON messages_topics(uid, topic_id, date, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_topic_id_mid_messages_topics ON messages_topics(uid, topic_id, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_topics(mid INTEGER, uid INTEGER, topic_id INTEGER, date INTEGER, type INTEGER, data BLOB, PRIMARY KEY(mid, uid, topic_id, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_mid_type_date_idx_media_topics ON media_topics(uid, topic_id, mid, type, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_holes_topics(uid INTEGER, topic_id INTEGER, type INTEGER, start INTEGER, end INTEGER, PRIMARY KEY(uid, topic_id, type, start));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS uid_end_media_holes_topics ON media_holes_topics(uid, topic_id, type, end);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE topics(did INTEGER, topic_id INTEGER, data BLOB, top_message INTEGER, topic_message BLOB, unread_count INTEGER, max_read_id INTEGER, unread_mentions INTEGER, unread_reactions INTEGER, read_outbox INTEGER, pinned INTEGER, total_messages_count INTEGER, hidden INTEGER, edit_date INTEGER, nopaid_messages_exception INTEGER, PRIMARY KEY(did, topic_id));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS did_top_message_topics ON topics(did, top_message);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS did_topics ON topics(did);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE media_counts_topics(uid INTEGER, topic_id INTEGER, type INTEGER, count INTEGER, old INTEGER, PRIMARY KEY(uid, topic_id, type))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE reaction_mentions_topics(message_id INTEGER, state INTEGER, dialog_id INTEGER, topic_id INTEGER, PRIMARY KEY(message_id, dialog_id, topic_id))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS reaction_mentions_topics_did ON reaction_mentions_topics(dialog_id, topic_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE emoji_groups(type INTEGER PRIMARY KEY, data BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE app_config(data BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE effects(data BLOB)").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE stories (dialog_id INTEGER, story_id INTEGER, data BLOB, custom_params BLOB, PRIMARY KEY (dialog_id, story_id));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE stories_counter (dialog_id INTEGER PRIMARY KEY, count INTEGER, max_read INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE profile_stories (dialog_id INTEGER, story_id INTEGER, data BLOB, type INTEGER, seen INTEGER, pin INTEGER, PRIMARY KEY(dialog_id, story_id, type));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE profile_stories_albums (dialog_id INTEGER, album_id INTEGER, order_index INTEGER, data BLOB, PRIMARY KEY(dialog_id, album_id));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE profile_stories_albums_links (dialog_id INTEGER, album_id INTEGER, story_id INTEGER, order_index INTEGER, PRIMARY KEY (dialog_id, album_id, story_id));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE story_drafts (id INTEGER PRIMARY KEY, date INTEGER, data BLOB, type INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE story_pushes (uid INTEGER, sid INTEGER, date INTEGER, localName TEXT, flags INTEGER, expire_date INTEGER, live INTEGER, PRIMARY KEY(uid, sid));").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE unconfirmed_auth (data BLOB);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE saved_reaction_tags (topic_id INTEGER PRIMARY KEY, data BLOB);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE tag_message_id(mid INTEGER, topic_id INTEGER, tag INTEGER, text TEXT);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS tag_idx_tag_message_id ON tag_message_id(tag);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS tag_text_idx_tag_message_id ON tag_message_id(tag, text COLLATE NOCASE);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS tag_topic_idx_tag_message_id ON tag_message_id(topic_id, tag);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS tag_topic_text_idx_tag_message_id ON tag_message_id(topic_id, tag, text COLLATE NOCASE);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE business_replies(topic_id INTEGER PRIMARY KEY, name TEXT, order_value INTEGER, count INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE quick_replies_messages(mid INTEGER, topic_id INTEGER, send_state INTEGER, date INTEGER, data BLOB, ttl INTEGER, replydata BLOB, reply_to_message_id INTEGER, PRIMARY KEY(mid, topic_id))").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS topic_date_idx_quick_replies_messages ON quick_replies_messages(topic_id, date);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS reply_to_idx_quick_replies_messages ON quick_replies_messages(mid, reply_to_message_id);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE INDEX IF NOT EXISTS idx_to_reply_quick_replies_messages ON quick_replies_messages(reply_to_message_id, mid);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE business_links(data BLOB, order_value INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE fact_checks(hash INTEGER PRIMARY KEY, data BLOB, expires INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE popular_bots(uid INTEGER PRIMARY KEY, time INTEGER, offset TEXT, pos INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE star_gifts2(id INTEGER PRIMARY KEY, data BLOB, hash INTEGER, time INTEGER, pos INTEGER);").stepThis().dispose();
        sQLiteDatabase.executeFast("CREATE TABLE gift_themes (slug TEXT PRIMARY KEY, data BLOB);").stepThis().dispose();
        sQLiteDatabase.executeFast("PRAGMA user_version = 168").stepThis().dispose();
    }

    public boolean isDatabaseMigrationInProgress() {
        return this.databaseMigrationInProgress;
    }

    private void updateDbToLastVersion(int i) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda36
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDbToLastVersion$3();
            }
        });
        FileLog.m1157d("MessagesStorage start db migration from " + i + " to 168");
        int iMigrate = DatabaseMigrationHelper.migrate(this, i);
        StringBuilder sb = new StringBuilder();
        sb.append("MessagesStorage db migration finished to varsion ");
        sb.append(iMigrate);
        FileLog.m1157d(sb.toString());
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda37
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDbToLastVersion$4();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDbToLastVersion$3() {
        this.databaseMigrationInProgress = true;
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.onDatabaseMigration, Boolean.TRUE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDbToLastVersion$4() {
        this.databaseMigrationInProgress = false;
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.onDatabaseMigration, Boolean.FALSE);
    }

    void executeNoException(String str) {
        try {
            this.database.executeFast(str).stepThis().dispose();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    private void cleanupInternal(boolean z) {
        if (z) {
            reset();
        } else {
            clearDatabaseValues();
        }
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
            this.database = null;
        }
        if (z) {
            File file = this.cacheFile;
            if (file != null) {
                file.delete();
                this.cacheFile = null;
            }
            File file2 = this.walCacheFile;
            if (file2 != null) {
                file2.delete();
                this.walCacheFile = null;
            }
            File file3 = this.shmCacheFile;
            if (file3 != null) {
                file3.delete();
                this.shmCacheFile = null;
            }
        }
    }

    public void clearDatabaseValues() {
        this.lastDateValue = 0;
        this.lastSeqValue = 0;
        this.lastPtsValue = 0;
        this.lastQtsValue = 0;
        this.lastSecretVersion = 0;
        this.mainUnreadCount = 0;
        this.archiveUnreadCount = 0;
        this.pendingMainUnreadCount = 0;
        this.pendingArchiveUnreadCount = 0;
        this.dialogFilters.clear();
        this.dialogFiltersMap.clear();
        this.unknownDialogsIds.clear();
        this.lastSavedSeq = 0;
        this.lastSavedPts = 0;
        this.lastSavedDate = 0;
        this.lastSavedQts = 0;
        this.secretPBytes = null;
        this.secretG = 0;
    }

    public void cleanup(final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda69
            @Override // java.lang.Runnable
            public final void run() throws Exception {
                this.f$0.lambda$cleanup$6(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanup$6(boolean z) throws Exception {
        cleanupInternal(true);
        openDatabase(1);
        if (z) {
            Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda78
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$cleanup$5();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cleanup$5() {
        getMessagesController().getDifference();
    }

    public void saveSecretParams(final int i, final int i2, final byte[] bArr) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda148
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$saveSecretParams$7(i, i2, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveSecretParams$7(int i, int i2, byte[] bArr) {
        try {
            SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE params SET lsv = ?, sg = ?, pbytes = ? WHERE id = 1");
            sQLitePreparedStatementExecuteFast.bindInteger(1, i);
            sQLitePreparedStatementExecuteFast.bindInteger(2, i2);
            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(bArr != null ? bArr.length : 1);
            if (bArr != null) {
                nativeByteBuffer.writeBytes(bArr);
            }
            sQLitePreparedStatementExecuteFast.bindByteBuffer(3, nativeByteBuffer);
            sQLitePreparedStatementExecuteFast.step();
            sQLitePreparedStatementExecuteFast.dispose();
            nativeByteBuffer.reuse();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void checkSQLException(Throwable th) {
        checkSQLException(th, true);
    }

    private void checkSQLException(Throwable th, boolean z) {
        if ((th instanceof SQLiteException) && th.getMessage() != null && th.getMessage().contains("is malformed") && !this.tryRecover) {
            this.tryRecover = true;
            FileLog.m1158e("disk image malformed detected, try recover");
            if (recoverDatabase()) {
                this.tryRecover = false;
                clearLoadingDialogsOffsets();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda209
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$checkSQLException$8();
                    }
                });
                FileLog.m1160e(new Exception("database restored!!"));
                return;
            }
            FileLog.m1160e(new Exception(th));
            return;
        }
        FileLog.m1160e(th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkSQLException$8() {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.onDatabaseReset, new Object[0]);
    }

    public void fixNotificationSettings() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda141
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$fixNotificationSettings$9();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fixNotificationSettings$9() {
        try {
            LongSparseArray longSparseArray = new LongSparseArray();
            Map<String, ?> all = MessagesController.getNotificationsSettings(this.currentAccount).getAll();
            for (Map.Entry<String, ?> entry : all.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith(NotificationsSettingsFacade.PROPERTY_NOTIFY)) {
                    Integer num = (Integer) entry.getValue();
                    if (num.intValue() == 2 || num.intValue() == 3) {
                        String strReplace = key.replace(NotificationsSettingsFacade.PROPERTY_NOTIFY, "");
                        long jIntValue = 1;
                        if (num.intValue() != 2) {
                            if (((Integer) all.get(NotificationsSettingsFacade.PROPERTY_NOTIFY_UNTIL + strReplace)) != null) {
                                jIntValue = 1 | (r4.intValue() << 32);
                            }
                        }
                        try {
                            longSparseArray.put(Long.parseLong(strReplace), Long.valueOf(jIntValue));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            try {
                this.database.beginTransaction();
                SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO dialog_settings VALUES(?, ?)");
                for (int i = 0; i < longSparseArray.size(); i++) {
                    sQLitePreparedStatementExecuteFast.requery();
                    sQLitePreparedStatementExecuteFast.bindLong(1, longSparseArray.keyAt(i));
                    sQLitePreparedStatementExecuteFast.bindLong(2, ((Long) longSparseArray.valueAt(i)).longValue());
                    sQLitePreparedStatementExecuteFast.step();
                }
                sQLitePreparedStatementExecuteFast.dispose();
                this.database.commitTransaction();
            } catch (Exception e2) {
                checkSQLException(e2);
            }
        } catch (Throwable th) {
            checkSQLException(th);
        }
    }

    public long createPendingTask(final NativeByteBuffer nativeByteBuffer) {
        if (nativeByteBuffer == null) {
            return 0L;
        }
        final long andAdd = this.lastTaskId.getAndAdd(1L);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda61
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createPendingTask$10(andAdd, nativeByteBuffer);
            }
        });
        return andAdd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPendingTask$10(long j, NativeByteBuffer nativeByteBuffer) {
        try {
            SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO pending_tasks VALUES(?, ?)");
            sQLitePreparedStatementExecuteFast.bindLong(1, j);
            sQLitePreparedStatementExecuteFast.bindByteBuffer(2, nativeByteBuffer);
            sQLitePreparedStatementExecuteFast.step();
            sQLitePreparedStatementExecuteFast.dispose();
        } catch (Exception e) {
            checkSQLException(e);
        } finally {
            nativeByteBuffer.reuse();
        }
    }

    public void removePendingTask(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda236
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removePendingTask$11(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removePendingTask$11(long j) {
        try {
            this.database.executeFast("DELETE FROM pending_tasks WHERE id = " + j).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    private void loadPendingTasks() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda58
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadPendingTasks$33();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00c0 A[Catch: Exception -> 0x0040, TryCatch #0 {Exception -> 0x0040, blocks: (B:2:0x0000, B:3:0x000b, B:5:0x0011, B:7:0x001c, B:8:0x0021, B:9:0x0024, B:84:0x0369, B:11:0x0029, B:13:0x003b, B:17:0x0045, B:18:0x004a, B:19:0x0055, B:20:0x006b, B:21:0x007e, B:22:0x009c, B:24:0x00aa, B:26:0x00b0, B:27:0x00b5, B:28:0x00c0, B:30:0x00d4, B:32:0x00da, B:34:0x00e0, B:35:0x00e8, B:36:0x00f4, B:37:0x0106, B:38:0x014e, B:40:0x015f, B:41:0x0164, B:42:0x016f, B:44:0x017f, B:45:0x018d, B:46:0x0198, B:48:0x01a8, B:49:0x01b6, B:50:0x01c1, B:51:0x01d5, B:52:0x01ff, B:53:0x0204, B:55:0x0219, B:56:0x0221, B:57:0x022d, B:58:0x0246, B:60:0x0255, B:62:0x025b, B:63:0x0260, B:64:0x026b, B:65:0x028a, B:66:0x02a9, B:67:0x02cb, B:69:0x0303, B:72:0x0313, B:75:0x031d, B:78:0x0327, B:79:0x032d, B:80:0x033f, B:81:0x0355, B:83:0x035f, B:85:0x036e), top: B:89:0x0000 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$loadPendingTasks$33() {
        /*
            Method dump skipped, instructions count: 954
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$loadPendingTasks$33():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$12(TLRPC.Chat chat, long j) {
        getMessagesController().loadUnknownChannel(chat, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$13(long j, int i, long j2) {
        getMessagesController().getChannelDifference(j, i, j2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$14(TLRPC.Dialog dialog, TLRPC.InputPeer inputPeer, long j) {
        getMessagesController().checkLastDialogMessage(dialog, inputPeer, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$15(long j, boolean z, TLRPC.InputPeer inputPeer, long j2) {
        getMessagesController().pinDialog(j, z, inputPeer, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$16(long j, int i, long j2, TLRPC.InputChannel inputChannel) {
        getMessagesController().getChannelDifference(j, i, j2, inputChannel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$17(long j, int i, long j2, TLRPC.InputChannel inputChannel) {
        getMessagesController().getChannelDifference(j, i, j2, inputChannel);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$18(long j, long j2, TLObject tLObject) {
        getMessagesController().deleteMessages(null, null, null, -j, true, 0, false, j2, tLObject, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$19(long j, long j2, TLObject tLObject) {
        getMessagesController().deleteMessages(null, null, null, j, true, 0, false, j2, tLObject, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$20(long j, long j2, TLObject tLObject, int i) {
        getMessagesController().deleteMessages(null, null, null, j, true, 0, false, j2, tLObject, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$21(long j, TLRPC.InputPeer inputPeer, long j2) {
        getMessagesController().markDialogAsUnread(j, inputPeer, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$22(long j, int i, TLRPC.InputChannel inputChannel, int i2, long j2) {
        getMessagesController().markMessageAsRead2(-j, i, inputChannel, i2, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$23(long j, int i, TLRPC.InputChannel inputChannel, int i2, long j2, int i3) {
        getMessagesController().markMessageAsRead2(j, i, inputChannel, i2, j2, i3 == 23);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$24(Theme.OverrideWallpaperInfo overrideWallpaperInfo, boolean z, long j) {
        getMessagesController().saveWallpaperToServer(null, overrideWallpaperInfo, z, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$25(long j, boolean z, int i, int i2, boolean z2, TLRPC.InputPeer inputPeer, long j2) {
        getMessagesController().deleteDialog(j, z ? 1 : 0, i, i2, z2, inputPeer, j2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$26(TLRPC.InputPeer inputPeer, long j) {
        getMessagesController().loadUnknownDialog(inputPeer, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$27(int i, ArrayList arrayList, long j) {
        getMessagesController().reorderPinnedDialogs(i, arrayList, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$28(int i, ArrayList arrayList, long j) {
        getMessagesController().addDialogToFolder(null, i, -1, arrayList, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$29(long j, long j2, TLObject tLObject) {
        getMessagesController().deleteMessages(null, null, null, j, true, 1, false, j2, tLObject, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$30(TLRPC.InputPeer inputPeer, long j) {
        getMessagesController().reloadMentionsCountForChannel(inputPeer, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$31(int i, boolean z, long j) {
        getSecretChatHelper().declineSecretChat(i, z, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadPendingTasks$32(long j, long j2, int i) {
        getMessagesController().lambda$checkDeletingTask$80(j, j2, i);
    }

    public void saveChannelPts(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda138
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$saveChannelPts$34(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveChannelPts$34(int i, long j) {
        try {
            SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE dialogs SET pts = ? WHERE did = ?");
            sQLitePreparedStatementExecuteFast.bindInteger(1, i);
            sQLitePreparedStatementExecuteFast.bindLong(2, -j);
            sQLitePreparedStatementExecuteFast.step();
            sQLitePreparedStatementExecuteFast.dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: saveDiffParamsInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$saveDiffParams$35(int i, int i2, int i3, int i4) {
        try {
            if (this.lastSavedSeq == i && this.lastSavedPts == i2 && this.lastSavedDate == i3 && this.lastQtsValue == i4) {
                return;
            }
            SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE params SET seq = ?, pts = ?, date = ?, qts = ? WHERE id = 1");
            sQLitePreparedStatementExecuteFast.bindInteger(1, i);
            sQLitePreparedStatementExecuteFast.bindInteger(2, i2);
            sQLitePreparedStatementExecuteFast.bindInteger(3, i3);
            sQLitePreparedStatementExecuteFast.bindInteger(4, i4);
            sQLitePreparedStatementExecuteFast.step();
            sQLitePreparedStatementExecuteFast.dispose();
            this.lastSavedSeq = i;
            this.lastSavedPts = i2;
            this.lastSavedDate = i3;
            this.lastSavedQts = i4;
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void saveDiffParams(final int i, final int i2, final int i3, final int i4) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda110
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$saveDiffParams$35(i, i2, i3, i4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMutedDialogsFiltersCounters$36() {
        resetAllUnreadCounters(true);
    }

    public void updateMutedDialogsFiltersCounters() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateMutedDialogsFiltersCounters$36();
            }
        });
    }

    public void setDialogFlags(final long j, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda168
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setDialogFlags$37(j, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDialogFlags$37(long j, long j2) {
        try {
            SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT flags FROM dialog_settings WHERE did = " + j, new Object[0]);
            int iIntValue = sQLiteCursorQueryFinalized.next() ? sQLiteCursorQueryFinalized.intValue(0) : 0;
            sQLiteCursorQueryFinalized.dispose();
            if (j2 == iIntValue) {
                return;
            }
            this.database.executeFast(String.format(Locale.US, "REPLACE INTO dialog_settings VALUES(%d, %d)", Long.valueOf(j), Long.valueOf(j2))).stepThis().dispose();
            resetAllUnreadCounters(true);
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void putStoryPushMessage(final NotificationsController.StoryNotification storyNotification) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda237
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$putStoryPushMessage$38(storyNotification);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putStoryPushMessage$38(NotificationsController.StoryNotification storyNotification) {
        try {
            this.database.executeFast("DELETE FROM story_pushes WHERE uid = " + storyNotification.dialogId).stepThis().dispose();
            SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO story_pushes VALUES(?, ?, ?, ?, ?, ?)");
            for (Map.Entry<Integer, Pair<Long, Long>> entry : storyNotification.dateByIds.entrySet()) {
                int iIntValue = entry.getKey().intValue();
                long jLongValue = ((Long) entry.getValue().first).longValue();
                long jLongValue2 = ((Long) entry.getValue().second).longValue();
                sQLitePreparedStatementExecuteFast.requery();
                sQLitePreparedStatementExecuteFast.bindLong(1, storyNotification.dialogId);
                sQLitePreparedStatementExecuteFast.bindInteger(2, iIntValue);
                sQLitePreparedStatementExecuteFast.bindLong(3, jLongValue);
                if (storyNotification.localName == null) {
                    storyNotification.localName = "";
                }
                sQLitePreparedStatementExecuteFast.bindString(4, storyNotification.localName);
                sQLitePreparedStatementExecuteFast.bindInteger(5, storyNotification.hidden ? 1 : 0);
                sQLitePreparedStatementExecuteFast.bindLong(6, jLongValue2);
                sQLitePreparedStatementExecuteFast.step();
            }
            sQLitePreparedStatementExecuteFast.dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void deleteStoryPushMessage(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda130
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteStoryPushMessage$39(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteStoryPushMessage$39(long j) {
        try {
            this.database.executeFast("DELETE FROM story_pushes WHERE uid = " + j).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void deleteAllStoryPushMessages() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda63
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteAllStoryPushMessages$40();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteAllStoryPushMessages$40() {
        try {
            this.database.executeFast("DELETE FROM story_pushes").stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void deleteAllStoryReactionPushMessages() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda81
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteAllStoryReactionPushMessages$41();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteAllStoryReactionPushMessages$41() {
        try {
            this.database.executeFast("DELETE FROM unread_push_messages WHERE is_reaction = 2").stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void putPushMessage(final MessageObject messageObject) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda127
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$putPushMessage$42(messageObject);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putPushMessage$42(MessageObject messageObject) {
        try {
            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(messageObject.messageOwner.getObjectSize());
            messageObject.messageOwner.serializeToStream(nativeByteBuffer);
            int i = messageObject.localType == 2 ? 1 : 0;
            if (messageObject.localChannel) {
                i |= 2;
            }
            SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO unread_push_messages VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            sQLitePreparedStatementExecuteFast.requery();
            sQLitePreparedStatementExecuteFast.bindLong(1, messageObject.getDialogId());
            sQLitePreparedStatementExecuteFast.bindInteger(2, messageObject.getId());
            sQLitePreparedStatementExecuteFast.bindLong(3, messageObject.messageOwner.random_id);
            sQLitePreparedStatementExecuteFast.bindInteger(4, messageObject.messageOwner.date);
            sQLitePreparedStatementExecuteFast.bindByteBuffer(5, nativeByteBuffer);
            CharSequence charSequence = messageObject.messageText;
            if (charSequence == null) {
                sQLitePreparedStatementExecuteFast.bindNull(6);
            } else {
                sQLitePreparedStatementExecuteFast.bindString(6, charSequence.toString());
            }
            String str = messageObject.localName;
            if (str == null) {
                sQLitePreparedStatementExecuteFast.bindNull(7);
            } else {
                sQLitePreparedStatementExecuteFast.bindString(7, str);
            }
            String str2 = messageObject.localUserName;
            if (str2 == null) {
                sQLitePreparedStatementExecuteFast.bindNull(8);
            } else {
                sQLitePreparedStatementExecuteFast.bindString(8, str2);
            }
            sQLitePreparedStatementExecuteFast.bindInteger(9, i);
            sQLitePreparedStatementExecuteFast.bindLong(10, MessageObject.getTopicId(this.currentAccount, messageObject.messageOwner, false));
            sQLitePreparedStatementExecuteFast.bindInteger(11, (messageObject.isReactionPush ? 1 : 0) + (messageObject.isStoryReactionPush ? 1 : 0));
            sQLitePreparedStatementExecuteFast.step();
            nativeByteBuffer.reuse();
            sQLitePreparedStatementExecuteFast.dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void clearLocalDatabase() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda199
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$clearLocalDatabase$44();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x048d  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0492  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0497  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x049c  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x04a6  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x04ab  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x04b0  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x04b5  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x03ea A[Catch: all -> 0x02d5, Exception -> 0x03f5, TryCatch #6 {Exception -> 0x03f5, blocks: (B:38:0x026c, B:40:0x0272, B:67:0x02f5, B:68:0x02f9, B:70:0x03ea, B:74:0x03fc), top: B:130:0x026c }] */
    /* JADX WARN: Type inference failed for: r6v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$clearLocalDatabase$44() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1212
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$clearLocalDatabase$44():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearLocalDatabase$43() {
        getMessagesController().getSavedMessagesController().cleanup();
    }

    public void saveTopics(final long j, final List<TLRPC.TL_forumTopic> list, final boolean z, boolean z2, final int i) throws Throwable {
        if (z2) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda220
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$saveTopics$45(j, list, z, i);
                }
            });
        } else {
            saveTopicsInternal(j, list, z, false, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveTopics$45(long j, List list, boolean z, int i) throws Throwable {
        saveTopicsInternal(j, list, z, true, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:75:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0285  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void saveTopicsInternal(long r18, java.util.List<org.telegram.tgnet.TLRPC.TL_forumTopic> r20, boolean r21, boolean r22, int r23) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 654
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.saveTopicsInternal(long, java.util.List, boolean, boolean, int):void");
    }

    public void updateTopicData(long j, TLRPC.TL_forumTopic tL_forumTopic, int i) {
        updateTopicData(j, tL_forumTopic, i, getConnectionsManager().getCurrentTime());
    }

    public void updateTopicData(final long j, final TLRPC.TL_forumTopic tL_forumTopic, final int i, final int i2) {
        if (tL_forumTopic == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda109
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateTopicData$47(i, tL_forumTopic, j, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(14:0|2|(10:81|4|79|5|(1:7)(1:14)|15|16|(1:18)|19|20)(1:25)|26|77|27|(7:29|(1:31)|38|(13:42|(1:44)|45|(1:47)|48|(1:50)|51|(1:53)|54|(1:56)|57|(1:59)|60)|(0)|19|20)(1:37)|36|38|(13:42|(0)|45|(0)|48|(0)|51|(0)|54|(0)|57|(0)|60)|(0)|19|20|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x009a, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x009b, code lost:
    
        r16 = r10;
        r10 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a0, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a1, code lost:
    
        r16 = r10;
        r10 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0136, code lost:
    
        r10.dispose();
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x013b, code lost:
    
        r16.dispose();
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004d A[PHI: r6
      0x004d: PHI (r6v3 org.telegram.SQLite.SQLitePreparedStatement) = (r6v2 org.telegram.SQLite.SQLitePreparedStatement), (r6v7 org.telegram.SQLite.SQLitePreparedStatement) binds: [B:17:0x004b, B:61:0x012d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00b7 A[Catch: all -> 0x0031, Exception -> 0x0037, TRY_ENTER, TryCatch #6 {Exception -> 0x0037, all -> 0x0031, blocks: (B:5:0x0019, B:7:0x002a, B:15:0x0040, B:26:0x005d, B:44:0x00b7, B:45:0x00bb, B:47:0x00bf, B:48:0x00c8, B:50:0x00cc, B:51:0x00d4, B:53:0x00d8, B:54:0x00db, B:56:0x00df, B:57:0x00e3, B:59:0x00e7, B:60:0x00eb, B:14:0x003d), top: B:79:0x0019 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00bf A[Catch: all -> 0x0031, Exception -> 0x0037, TryCatch #6 {Exception -> 0x0037, all -> 0x0031, blocks: (B:5:0x0019, B:7:0x002a, B:15:0x0040, B:26:0x005d, B:44:0x00b7, B:45:0x00bb, B:47:0x00bf, B:48:0x00c8, B:50:0x00cc, B:51:0x00d4, B:53:0x00d8, B:54:0x00db, B:56:0x00df, B:57:0x00e3, B:59:0x00e7, B:60:0x00eb, B:14:0x003d), top: B:79:0x0019 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00cc A[Catch: all -> 0x0031, Exception -> 0x0037, TryCatch #6 {Exception -> 0x0037, all -> 0x0031, blocks: (B:5:0x0019, B:7:0x002a, B:15:0x0040, B:26:0x005d, B:44:0x00b7, B:45:0x00bb, B:47:0x00bf, B:48:0x00c8, B:50:0x00cc, B:51:0x00d4, B:53:0x00d8, B:54:0x00db, B:56:0x00df, B:57:0x00e3, B:59:0x00e7, B:60:0x00eb, B:14:0x003d), top: B:79:0x0019 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00d8 A[Catch: all -> 0x0031, Exception -> 0x0037, TryCatch #6 {Exception -> 0x0037, all -> 0x0031, blocks: (B:5:0x0019, B:7:0x002a, B:15:0x0040, B:26:0x005d, B:44:0x00b7, B:45:0x00bb, B:47:0x00bf, B:48:0x00c8, B:50:0x00cc, B:51:0x00d4, B:53:0x00d8, B:54:0x00db, B:56:0x00df, B:57:0x00e3, B:59:0x00e7, B:60:0x00eb, B:14:0x003d), top: B:79:0x0019 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00df A[Catch: all -> 0x0031, Exception -> 0x0037, TryCatch #6 {Exception -> 0x0037, all -> 0x0031, blocks: (B:5:0x0019, B:7:0x002a, B:15:0x0040, B:26:0x005d, B:44:0x00b7, B:45:0x00bb, B:47:0x00bf, B:48:0x00c8, B:50:0x00cc, B:51:0x00d4, B:53:0x00d8, B:54:0x00db, B:56:0x00df, B:57:0x00e3, B:59:0x00e7, B:60:0x00eb, B:14:0x003d), top: B:79:0x0019 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00e7 A[Catch: all -> 0x0031, Exception -> 0x0037, TryCatch #6 {Exception -> 0x0037, all -> 0x0031, blocks: (B:5:0x0019, B:7:0x002a, B:15:0x0040, B:26:0x005d, B:44:0x00b7, B:45:0x00bb, B:47:0x00bf, B:48:0x00c8, B:50:0x00cc, B:51:0x00d4, B:53:0x00d8, B:54:0x00db, B:56:0x00df, B:57:0x00e3, B:59:0x00e7, B:60:0x00eb, B:14:0x003d), top: B:79:0x0019 }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0148  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$updateTopicData$47(final int r18, final org.telegram.tgnet.TLRPC.TL_forumTopic r19, final long r20, int r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 337
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateTopicData$47(int, org.telegram.tgnet.TLRPC$TL_forumTopic, long, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTopicData$46(long j, TLRPC.TL_forumTopic tL_forumTopic, int i) {
        getMessagesController().getTopicsController().updateTopicInUi(j, tL_forumTopic, i);
    }

    public void loadTopics(final long j, final Consumer<ArrayList<TLRPC.TL_forumTopic>> consumer) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda62
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$loadTopics$49(j, consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0310 A[Catch: all -> 0x0160, Exception -> 0x02de, TryCatch #0 {all -> 0x0160, blocks: (B:48:0x0114, B:50:0x0130, B:51:0x0136, B:53:0x013c, B:55:0x0147, B:57:0x0154, B:62:0x016c, B:65:0x017f, B:67:0x0185, B:68:0x0190, B:70:0x0196, B:72:0x019a, B:74:0x019e, B:76:0x01a4, B:78:0x01a8, B:80:0x01ac, B:85:0x01b5, B:87:0x01bb, B:89:0x01c1, B:91:0x01dc, B:94:0x01e3, B:96:0x01e7, B:98:0x01eb, B:104:0x01fd, B:106:0x0206, B:107:0x0231, B:109:0x0237, B:111:0x0242, B:113:0x024c, B:115:0x0256, B:121:0x0268, B:124:0x027f, B:126:0x0285, B:131:0x029f, B:134:0x02aa, B:138:0x02b3, B:142:0x02cc, B:144:0x02d6, B:147:0x02e3, B:149:0x02ed, B:150:0x02f5, B:152:0x0310, B:153:0x0317, B:155:0x031d, B:156:0x0320), top: B:180:0x0114 }] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x031d A[Catch: all -> 0x0160, Exception -> 0x02de, TryCatch #0 {all -> 0x0160, blocks: (B:48:0x0114, B:50:0x0130, B:51:0x0136, B:53:0x013c, B:55:0x0147, B:57:0x0154, B:62:0x016c, B:65:0x017f, B:67:0x0185, B:68:0x0190, B:70:0x0196, B:72:0x019a, B:74:0x019e, B:76:0x01a4, B:78:0x01a8, B:80:0x01ac, B:85:0x01b5, B:87:0x01bb, B:89:0x01c1, B:91:0x01dc, B:94:0x01e3, B:96:0x01e7, B:98:0x01eb, B:104:0x01fd, B:106:0x0206, B:107:0x0231, B:109:0x0237, B:111:0x0242, B:113:0x024c, B:115:0x0256, B:121:0x0268, B:124:0x027f, B:126:0x0285, B:131:0x029f, B:134:0x02aa, B:138:0x02b3, B:142:0x02cc, B:144:0x02d6, B:147:0x02e3, B:149:0x02ed, B:150:0x02f5, B:152:0x0310, B:153:0x0317, B:155:0x031d, B:156:0x0320), top: B:180:0x0114 }] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x034e  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0359  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$loadTopics$49(long r23, java.util.function.Consumer r25) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 861
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$loadTopics$49(long, java.util.function.Consumer):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadTopics$48(ArrayList arrayList, ArrayList arrayList2) {
        if (!arrayList.isEmpty()) {
            getMessagesController().putUsers(arrayList, true);
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        getMessagesController().putChats(arrayList2, true);
    }

    public void loadGroupedMessagesForTopicUpdates(ArrayList<TopicsController.TopicUpdate> arrayList) {
        if (arrayList == null) {
            return;
        }
        try {
            LongSparseArray longSparseArray = new LongSparseArray();
            for (int i = 0; i < arrayList.size(); i++) {
                if (!arrayList.get(i).reloadTopic && !arrayList.get(i).onlyCounters && arrayList.get(i).topMessage != null) {
                    long j = arrayList.get(i).topMessage.grouped_id;
                    if (j != 0) {
                        ArrayList arrayList2 = (ArrayList) longSparseArray.get(j);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                            longSparseArray.put(j, arrayList2);
                        }
                        arrayList2.add(arrayList.get(i));
                    }
                }
            }
            for (int i2 = 0; i2 < longSparseArray.size(); i2++) {
                long jKeyAt = longSparseArray.keyAt(i2);
                ArrayList arrayList3 = (ArrayList) longSparseArray.valueAt(i2);
                SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM messages_v2 WHERE uid = %s AND group_id = %s ORDER BY date DESC", Long.valueOf(((TopicsController.TopicUpdate) arrayList3.get(0)).dialogId), Long.valueOf(jKeyAt)), new Object[0]);
                ArrayList<MessageObject> arrayList4 = null;
                while (sQLiteCursorQueryFinalized.next()) {
                    NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                    TLRPC.Message messageTLdeserialize = TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                    if (messageTLdeserialize != null) {
                        messageTLdeserialize.readAttachPath(nativeByteBufferByteBufferValue, UserConfig.getInstance(this.currentAccount).clientUserId);
                    }
                    if (arrayList4 == null) {
                        arrayList4 = new ArrayList<>();
                    }
                    arrayList4.add(new MessageObject(this.currentAccount, messageTLdeserialize, false, false));
                }
                sQLiteCursorQueryFinalized.dispose();
                for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                    ((TopicsController.TopicUpdate) arrayList3.get(i3)).groupedMessages = arrayList4;
                }
            }
        } catch (Throwable th) {
            checkSQLException(th);
        }
    }

    public void loadGroupedMessagesForTopics(long j, ArrayList<TLRPC.TL_forumTopic> arrayList) {
        if (arrayList == null) {
            return;
        }
        try {
            LongSparseArray longSparseArray = new LongSparseArray();
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).topMessage != null) {
                    long j2 = arrayList.get(i).topMessage.grouped_id;
                    if (j2 != 0) {
                        ArrayList arrayList2 = (ArrayList) longSparseArray.get(j2);
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList();
                            longSparseArray.put(j2, arrayList2);
                        }
                        arrayList2.add(arrayList.get(i));
                    }
                }
            }
            for (int i2 = 0; i2 < longSparseArray.size(); i2++) {
                long jKeyAt = longSparseArray.keyAt(i2);
                ArrayList arrayList3 = (ArrayList) longSparseArray.valueAt(i2);
                SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM messages_v2 WHERE uid = %s AND group_id = %s ORDER BY date DESC", Long.valueOf(j), Long.valueOf(jKeyAt)), new Object[0]);
                ArrayList arrayList4 = null;
                while (sQLiteCursorQueryFinalized.next()) {
                    NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                    TLRPC.Message messageTLdeserialize = TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                    if (messageTLdeserialize != null) {
                        messageTLdeserialize.readAttachPath(nativeByteBufferByteBufferValue, UserConfig.getInstance(this.currentAccount).clientUserId);
                    }
                    if (arrayList4 == null) {
                        arrayList4 = new ArrayList();
                    }
                    arrayList4.add(new MessageObject(this.currentAccount, messageTLdeserialize, false, false));
                }
                sQLiteCursorQueryFinalized.dispose();
                for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                    ((TLRPC.TL_forumTopic) arrayList3.get(i3)).groupedMessages = arrayList4;
                }
            }
        } catch (Throwable th) {
            checkSQLException(th);
        }
    }

    public void getSavedDialogMaxMessageId(final long j, final IntCallback intCallback) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda211
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getSavedDialogMaxMessageId$51(j, intCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSavedDialogMaxMessageId$51(long j, final IntCallback intCallback) {
        final int[] iArr = new int[1];
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT MAX(mid) FROM messages_topics WHERE uid = ? AND topic_id = ?", Long.valueOf(getUserConfig().getClientUserId()), Long.valueOf(j));
                if (sQLiteCursorQueryFinalized.next()) {
                    iArr[0] = sQLiteCursorQueryFinalized.intValue(0);
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda238
                @Override // java.lang.Runnable
                public final void run() {
                    intCallback.run(iArr[0]);
                }
            });
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public void deleteSavedDialog(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda226
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$deleteSavedDialog$53(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:75:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$deleteSavedDialog$53(long r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 222
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$deleteSavedDialog$53(long):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteSavedDialog$52(long j, ArrayList arrayList) {
        getMessagesController().markDialogMessageAsDeleted(j, arrayList);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messagesDeleted, arrayList, 0L, Boolean.FALSE);
    }

    public void removeTopic(final long j, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda195
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removeTopic$54(j, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeTopic$54(long j, long j2) {
        try {
            SQLiteDatabase sQLiteDatabase = this.database;
            Locale locale = Locale.US;
            sQLiteDatabase.executeFast(String.format(locale, "DELETE FROM topics WHERE did = %d AND topic_id = %d", Long.valueOf(j), Long.valueOf(j2))).stepThis().dispose();
            this.database.executeFast(String.format(locale, "DELETE FROM messages_v2 WHERE uid = %d AND mid IN (SELECT mid FROM messages_topics WHERE uid = %d AND topic_id = %d)", Long.valueOf(j), Long.valueOf(j), Long.valueOf(j2))).stepThis().dispose();
            this.database.executeFast(String.format(locale, "DELETE FROM messages_topics WHERE uid = %d AND topic_id = %d", Long.valueOf(j), Long.valueOf(j2))).stepThis().dispose();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public void removeTopics(final long j, final ArrayList<Long> arrayList) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda71
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$removeTopics$55(arrayList, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeTopics$55(ArrayList arrayList, long j) {
        try {
            String strJoin = TextUtils.join(", ", arrayList);
            SQLiteDatabase sQLiteDatabase = this.database;
            Locale locale = Locale.US;
            sQLiteDatabase.executeFast(String.format(locale, "DELETE FROM topics WHERE did = %d AND topic_id IN (%s)", Long.valueOf(j), strJoin)).stepThis().dispose();
            try {
                this.database.executeFast(String.format(locale, "DELETE FROM messages_v2 WHERE uid = %d AND mid IN (SELECT mid FROM messages_topics WHERE uid = %d AND topic_id IN (%s))", Long.valueOf(j), Long.valueOf(j), strJoin)).stepThis().dispose();
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
            this.database.executeFast(String.format(Locale.US, "DELETE FROM messages_topics WHERE uid = %d AND topic_id IN (%s)", Long.valueOf(j), strJoin)).stepThis().dispose();
        } catch (SQLiteException e2) {
            e2.printStackTrace();
        }
    }

    public void updateTopicsWithReadMessages(final HashMap<TopicKey, Integer> map) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda90
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateTopicsWithReadMessages$56(map);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateTopicsWithReadMessages$56(HashMap map) {
        for (TopicKey topicKey : map.keySet()) {
            Integer num = (Integer) map.get(topicKey);
            num.intValue();
            try {
                this.database.executeFast(String.format(Locale.US, "UPDATE topics SET read_outbox = max((SELECT read_outbox FROM topics WHERE did = %d AND topic_id = %d), %d) WHERE did = %d AND topic_id = %d", Long.valueOf(topicKey.dialogId), Long.valueOf(topicKey.topicId), num, Long.valueOf(topicKey.dialogId), Long.valueOf(topicKey.topicId))).stepThis().dispose();
            } catch (SQLiteException e) {
                checkSQLException(e);
            }
        }
    }

    public void setDialogTtl(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda239
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setDialogTtl$57(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDialogTtl$57(int i, long j) {
        try {
            this.database.executeFast(String.format(Locale.US, "UPDATE dialogs SET ttl_period = %d WHERE did = %d", Integer.valueOf(i), Long.valueOf(j))).stepThis().dispose();
        } catch (SQLiteException e) {
            checkSQLException(e);
        }
    }

    public ArrayList<File> getDatabaseFiles() {
        ArrayList<File> arrayList = new ArrayList<>();
        arrayList.add(this.cacheFile);
        arrayList.add(this.walCacheFile);
        arrayList.add(this.shmCacheFile);
        return arrayList;
    }

    public void reset() {
        clearDatabaseValues();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda247
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$reset$58();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$reset$58() {
        for (int i = 0; i < 2; i++) {
            getUserConfig().setDialogsLoadOffset(i, 0, 0, 0L, 0L, 0L, 0L);
            getUserConfig().setTotalDialogsCount(i, 0);
        }
        getUserConfig().clearFilters();
        getUserConfig().clearPinnedDialogsLoaded();
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.didClearDatabase, new Object[0]);
        getMediaDataController().loadAttachMenuBots(false, true);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.onDatabaseReset, new Object[0]);
        getMessagesController().getStoriesController().cleanup();
    }

    public void fullReset() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda46
            @Override // java.lang.Runnable
            public final void run() throws Exception {
                this.f$0.lambda$fullReset$60();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fullReset$60() throws Exception {
        cleanupInternal(true);
        clearLoadingDialogsOffsets();
        openDatabase(1);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda98
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$fullReset$59();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fullReset$59() {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.onDatabaseReset, new Object[0]);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.didClearDatabase, new Object[0]);
        getMessagesController().getSavedMessagesController().cleanup();
    }

    /* loaded from: classes4.dex */
    private static class ReadDialog {
        public int date;
        public int lastMid;
        public int unreadCount;

        private ReadDialog() {
        }
    }

    public void readAllDialogs(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda126
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$readAllDialogs$62(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:95:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v5, types: [org.telegram.SQLite.SQLiteCursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$readAllDialogs$62(int r12) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 288
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$readAllDialogs$62(int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$readAllDialogs$61(ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, LongSparseArray longSparseArray) {
        MessagesStorage messagesStorage;
        getMessagesController().putUsers(arrayList, true);
        getMessagesController().putChats(arrayList2, true);
        getMessagesController().putEncryptedChats(arrayList3, true);
        for (int i = 0; i < longSparseArray.size(); i++) {
            long jKeyAt = longSparseArray.keyAt(i);
            ReadDialog readDialog = (ReadDialog) longSparseArray.valueAt(i);
            if (getMessagesController().isForum(jKeyAt)) {
                messagesStorage = this;
            } else {
                messagesStorage = this;
                if (messagesStorage.isForum(jKeyAt, 8) || messagesStorage.getMessagesController().isMonoForumWithManageRights(jKeyAt)) {
                }
                MessagesController messagesController = messagesStorage.getMessagesController();
                int i2 = readDialog.lastMid;
                messagesController.markDialogAsRead(jKeyAt, i2, i2, readDialog.date, false, 0L, readDialog.unreadCount, true, 0);
            }
            messagesStorage.getMessagesController().markAllTopicsAsRead(jKeyAt);
            MessagesController messagesController2 = messagesStorage.getMessagesController();
            int i22 = readDialog.lastMid;
            messagesController2.markDialogAsRead(jKeyAt, i22, i22, readDialog.date, false, 0L, readDialog.unreadCount, true, 0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:130:0x029f  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0412  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c7 A[Catch: all -> 0x0069, Exception -> 0x006d, TryCatch #0 {Exception -> 0x006d, blocks: (B:4:0x002d, B:6:0x0034, B:8:0x005c, B:18:0x0074, B:22:0x0093, B:26:0x00aa, B:30:0x00b3, B:32:0x00c7, B:34:0x00cf, B:35:0x00d4, B:37:0x00f0, B:38:0x00f7, B:40:0x010b, B:42:0x0115, B:44:0x0138, B:45:0x013a, B:80:0x01b6, B:82:0x01bc, B:84:0x01c2, B:85:0x01c5, B:87:0x01cb, B:89:0x01d9, B:90:0x01e1, B:92:0x01e7, B:94:0x01f1, B:95:0x01f9, B:97:0x0206, B:77:0x01aa, B:78:0x01ae, B:99:0x0211), top: B:210:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f0 A[Catch: all -> 0x0069, Exception -> 0x006d, TryCatch #0 {Exception -> 0x006d, blocks: (B:4:0x002d, B:6:0x0034, B:8:0x005c, B:18:0x0074, B:22:0x0093, B:26:0x00aa, B:30:0x00b3, B:32:0x00c7, B:34:0x00cf, B:35:0x00d4, B:37:0x00f0, B:38:0x00f7, B:40:0x010b, B:42:0x0115, B:44:0x0138, B:45:0x013a, B:80:0x01b6, B:82:0x01bc, B:84:0x01c2, B:85:0x01c5, B:87:0x01cb, B:89:0x01d9, B:90:0x01e1, B:92:0x01e7, B:94:0x01f1, B:95:0x01f9, B:97:0x0206, B:77:0x01aa, B:78:0x01ae, B:99:0x0211), top: B:210:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x010b A[Catch: all -> 0x0069, Exception -> 0x006d, TryCatch #0 {Exception -> 0x006d, blocks: (B:4:0x002d, B:6:0x0034, B:8:0x005c, B:18:0x0074, B:22:0x0093, B:26:0x00aa, B:30:0x00b3, B:32:0x00c7, B:34:0x00cf, B:35:0x00d4, B:37:0x00f0, B:38:0x00f7, B:40:0x010b, B:42:0x0115, B:44:0x0138, B:45:0x013a, B:80:0x01b6, B:82:0x01bc, B:84:0x01c2, B:85:0x01c5, B:87:0x01cb, B:89:0x01d9, B:90:0x01e1, B:92:0x01e7, B:94:0x01f1, B:95:0x01f9, B:97:0x0206, B:77:0x01aa, B:78:0x01ae, B:99:0x0211), top: B:210:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01bc A[Catch: all -> 0x0069, Exception -> 0x006d, TryCatch #0 {Exception -> 0x006d, blocks: (B:4:0x002d, B:6:0x0034, B:8:0x005c, B:18:0x0074, B:22:0x0093, B:26:0x00aa, B:30:0x00b3, B:32:0x00c7, B:34:0x00cf, B:35:0x00d4, B:37:0x00f0, B:38:0x00f7, B:40:0x010b, B:42:0x0115, B:44:0x0138, B:45:0x013a, B:80:0x01b6, B:82:0x01bc, B:84:0x01c2, B:85:0x01c5, B:87:0x01cb, B:89:0x01d9, B:90:0x01e1, B:92:0x01e7, B:94:0x01f1, B:95:0x01f9, B:97:0x0206, B:77:0x01aa, B:78:0x01ae, B:99:0x0211), top: B:210:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01cb A[Catch: all -> 0x0069, Exception -> 0x006d, TryCatch #0 {Exception -> 0x006d, blocks: (B:4:0x002d, B:6:0x0034, B:8:0x005c, B:18:0x0074, B:22:0x0093, B:26:0x00aa, B:30:0x00b3, B:32:0x00c7, B:34:0x00cf, B:35:0x00d4, B:37:0x00f0, B:38:0x00f7, B:40:0x010b, B:42:0x0115, B:44:0x0138, B:45:0x013a, B:80:0x01b6, B:82:0x01bc, B:84:0x01c2, B:85:0x01c5, B:87:0x01cb, B:89:0x01d9, B:90:0x01e1, B:92:0x01e7, B:94:0x01f1, B:95:0x01f9, B:97:0x0206, B:77:0x01aa, B:78:0x01ae, B:99:0x0211), top: B:210:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01e1 A[Catch: all -> 0x0069, Exception -> 0x006d, TryCatch #0 {Exception -> 0x006d, blocks: (B:4:0x002d, B:6:0x0034, B:8:0x005c, B:18:0x0074, B:22:0x0093, B:26:0x00aa, B:30:0x00b3, B:32:0x00c7, B:34:0x00cf, B:35:0x00d4, B:37:0x00f0, B:38:0x00f7, B:40:0x010b, B:42:0x0115, B:44:0x0138, B:45:0x013a, B:80:0x01b6, B:82:0x01bc, B:84:0x01c2, B:85:0x01c5, B:87:0x01cb, B:89:0x01d9, B:90:0x01e1, B:92:0x01e7, B:94:0x01f1, B:95:0x01f9, B:97:0x0206, B:77:0x01aa, B:78:0x01ae, B:99:0x0211), top: B:210:0x002d }] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v13, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v15 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.telegram.tgnet.TLRPC.messages_Dialogs loadDialogsByIds(java.lang.String r22, java.util.ArrayList<java.lang.Long> r23, java.util.ArrayList<java.lang.Long> r24, java.util.ArrayList<java.lang.Integer> r25) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1046
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.loadDialogsByIds(java.lang.String, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList):org.telegram.tgnet.TLRPC$messages_Dialogs");
    }

    private void loadDialogFilters() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda40
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$loadDialogFilters$64();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x02c4  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02cb  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x02d0  */
    /* JADX WARN: Removed duplicated region for block: B:134:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v10 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$loadDialogFilters$64() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 724
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$loadDialogFilters$64():void");
    }

    /* renamed from: $r8$lambda$Ra-4F-UOsBgoV3B4xI89NECDFIk, reason: not valid java name */
    public static /* synthetic */ int m3824$r8$lambda$Ra4FUOsBgoV3B4xI89NECDFIk(MessagesController.DialogFilter dialogFilter, MessagesController.DialogFilter dialogFilter2) {
        int i = dialogFilter.order;
        int i2 = dialogFilter2.order;
        if (i > i2) {
            return 1;
        }
        return i < i2 ? -1 : 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:124:0x031d A[Catch: all -> 0x002b, Exception -> 0x002f, TryCatch #5 {Exception -> 0x002f, all -> 0x002b, blocks: (B:7:0x000a, B:12:0x0033, B:13:0x0036, B:57:0x0139, B:59:0x015f, B:61:0x016a, B:63:0x018e, B:66:0x0198, B:67:0x019f, B:69:0x01a3, B:77:0x01d2, B:70:0x01b0, B:72:0x01b4, B:75:0x01b9, B:76:0x01c6, B:78:0x01e4, B:81:0x01f2, B:83:0x0209, B:85:0x0216, B:86:0x022c, B:88:0x0235, B:105:0x02ad, B:91:0x024d, B:93:0x026b, B:96:0x0275, B:97:0x027c, B:99:0x0280, B:102:0x0285, B:104:0x02a0, B:103:0x0294, B:107:0x02ba, B:109:0x02c1, B:111:0x02d1, B:113:0x02dd, B:116:0x02eb, B:118:0x0307, B:124:0x031d, B:128:0x032c, B:129:0x0334, B:131:0x033a, B:133:0x033e, B:135:0x0357, B:137:0x036f, B:134:0x034b, B:136:0x035f, B:138:0x0378, B:139:0x0381, B:142:0x0387, B:145:0x0398, B:154:0x03b2, B:156:0x03b7, B:158:0x03bc, B:160:0x03c7, B:162:0x03ce, B:164:0x03d3, B:166:0x03e1, B:168:0x03e6, B:170:0x03eb, B:172:0x03f0, B:174:0x03fc, B:175:0x0401, B:177:0x0406, B:179:0x0414, B:180:0x0417, B:182:0x041c, B:184:0x0421, B:186:0x042d, B:187:0x0432, B:189:0x0437, B:191:0x0445, B:192:0x0448, B:194:0x044d, B:196:0x0452, B:198:0x045e, B:199:0x0463, B:201:0x0468, B:203:0x0476, B:204:0x0479, B:206:0x047e, B:208:0x0483, B:210:0x048f, B:211:0x0494, B:213:0x0499, B:215:0x04a7, B:218:0x04af, B:220:0x04b8, B:228:0x04d9, B:236:0x04f1, B:238:0x04f5, B:246:0x0506, B:248:0x0509, B:261:0x0530, B:250:0x050e, B:252:0x0513, B:254:0x051d, B:256:0x0522, B:258:0x0527, B:239:0x04f8, B:241:0x04fc, B:244:0x0501, B:245:0x0504, B:233:0x04e8, B:286:0x0583, B:263:0x053a, B:265:0x0545, B:267:0x054b, B:269:0x054f, B:271:0x0554, B:273:0x0557, B:274:0x055a, B:276:0x055f, B:278:0x0568, B:281:0x0574, B:283:0x0579, B:270:0x0552, B:287:0x058a, B:289:0x0594, B:297:0x05b4, B:306:0x05cf, B:308:0x05d3, B:316:0x05e4, B:329:0x060b, B:318:0x05e7, B:320:0x05ec, B:324:0x05fb, B:326:0x0601, B:328:0x0609, B:309:0x05d6, B:311:0x05da, B:314:0x05df, B:315:0x05e2, B:302:0x05c3, B:352:0x0664, B:331:0x0616, B:333:0x0626, B:335:0x062c, B:337:0x0630, B:339:0x0635, B:341:0x0639, B:343:0x063f, B:345:0x0648, B:347:0x064e, B:349:0x0657, B:351:0x0662, B:338:0x0633, B:353:0x066d, B:355:0x0672, B:358:0x0678, B:360:0x067c, B:361:0x067f, B:363:0x0683, B:365:0x0687, B:146:0x039b, B:148:0x039f, B:150:0x03a7, B:151:0x03aa, B:152:0x03ac, B:153:0x03af), top: B:379:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0328  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x032c A[Catch: all -> 0x002b, Exception -> 0x002f, TryCatch #5 {Exception -> 0x002f, all -> 0x002b, blocks: (B:7:0x000a, B:12:0x0033, B:13:0x0036, B:57:0x0139, B:59:0x015f, B:61:0x016a, B:63:0x018e, B:66:0x0198, B:67:0x019f, B:69:0x01a3, B:77:0x01d2, B:70:0x01b0, B:72:0x01b4, B:75:0x01b9, B:76:0x01c6, B:78:0x01e4, B:81:0x01f2, B:83:0x0209, B:85:0x0216, B:86:0x022c, B:88:0x0235, B:105:0x02ad, B:91:0x024d, B:93:0x026b, B:96:0x0275, B:97:0x027c, B:99:0x0280, B:102:0x0285, B:104:0x02a0, B:103:0x0294, B:107:0x02ba, B:109:0x02c1, B:111:0x02d1, B:113:0x02dd, B:116:0x02eb, B:118:0x0307, B:124:0x031d, B:128:0x032c, B:129:0x0334, B:131:0x033a, B:133:0x033e, B:135:0x0357, B:137:0x036f, B:134:0x034b, B:136:0x035f, B:138:0x0378, B:139:0x0381, B:142:0x0387, B:145:0x0398, B:154:0x03b2, B:156:0x03b7, B:158:0x03bc, B:160:0x03c7, B:162:0x03ce, B:164:0x03d3, B:166:0x03e1, B:168:0x03e6, B:170:0x03eb, B:172:0x03f0, B:174:0x03fc, B:175:0x0401, B:177:0x0406, B:179:0x0414, B:180:0x0417, B:182:0x041c, B:184:0x0421, B:186:0x042d, B:187:0x0432, B:189:0x0437, B:191:0x0445, B:192:0x0448, B:194:0x044d, B:196:0x0452, B:198:0x045e, B:199:0x0463, B:201:0x0468, B:203:0x0476, B:204:0x0479, B:206:0x047e, B:208:0x0483, B:210:0x048f, B:211:0x0494, B:213:0x0499, B:215:0x04a7, B:218:0x04af, B:220:0x04b8, B:228:0x04d9, B:236:0x04f1, B:238:0x04f5, B:246:0x0506, B:248:0x0509, B:261:0x0530, B:250:0x050e, B:252:0x0513, B:254:0x051d, B:256:0x0522, B:258:0x0527, B:239:0x04f8, B:241:0x04fc, B:244:0x0501, B:245:0x0504, B:233:0x04e8, B:286:0x0583, B:263:0x053a, B:265:0x0545, B:267:0x054b, B:269:0x054f, B:271:0x0554, B:273:0x0557, B:274:0x055a, B:276:0x055f, B:278:0x0568, B:281:0x0574, B:283:0x0579, B:270:0x0552, B:287:0x058a, B:289:0x0594, B:297:0x05b4, B:306:0x05cf, B:308:0x05d3, B:316:0x05e4, B:329:0x060b, B:318:0x05e7, B:320:0x05ec, B:324:0x05fb, B:326:0x0601, B:328:0x0609, B:309:0x05d6, B:311:0x05da, B:314:0x05df, B:315:0x05e2, B:302:0x05c3, B:352:0x0664, B:331:0x0616, B:333:0x0626, B:335:0x062c, B:337:0x0630, B:339:0x0635, B:341:0x0639, B:343:0x063f, B:345:0x0648, B:347:0x064e, B:349:0x0657, B:351:0x0662, B:338:0x0633, B:353:0x066d, B:355:0x0672, B:358:0x0678, B:360:0x067c, B:361:0x067f, B:363:0x0683, B:365:0x0687, B:146:0x039b, B:148:0x039f, B:150:0x03a7, B:151:0x03aa, B:152:0x03ac, B:153:0x03af), top: B:379:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x034b A[Catch: all -> 0x002b, Exception -> 0x002f, TryCatch #5 {Exception -> 0x002f, all -> 0x002b, blocks: (B:7:0x000a, B:12:0x0033, B:13:0x0036, B:57:0x0139, B:59:0x015f, B:61:0x016a, B:63:0x018e, B:66:0x0198, B:67:0x019f, B:69:0x01a3, B:77:0x01d2, B:70:0x01b0, B:72:0x01b4, B:75:0x01b9, B:76:0x01c6, B:78:0x01e4, B:81:0x01f2, B:83:0x0209, B:85:0x0216, B:86:0x022c, B:88:0x0235, B:105:0x02ad, B:91:0x024d, B:93:0x026b, B:96:0x0275, B:97:0x027c, B:99:0x0280, B:102:0x0285, B:104:0x02a0, B:103:0x0294, B:107:0x02ba, B:109:0x02c1, B:111:0x02d1, B:113:0x02dd, B:116:0x02eb, B:118:0x0307, B:124:0x031d, B:128:0x032c, B:129:0x0334, B:131:0x033a, B:133:0x033e, B:135:0x0357, B:137:0x036f, B:134:0x034b, B:136:0x035f, B:138:0x0378, B:139:0x0381, B:142:0x0387, B:145:0x0398, B:154:0x03b2, B:156:0x03b7, B:158:0x03bc, B:160:0x03c7, B:162:0x03ce, B:164:0x03d3, B:166:0x03e1, B:168:0x03e6, B:170:0x03eb, B:172:0x03f0, B:174:0x03fc, B:175:0x0401, B:177:0x0406, B:179:0x0414, B:180:0x0417, B:182:0x041c, B:184:0x0421, B:186:0x042d, B:187:0x0432, B:189:0x0437, B:191:0x0445, B:192:0x0448, B:194:0x044d, B:196:0x0452, B:198:0x045e, B:199:0x0463, B:201:0x0468, B:203:0x0476, B:204:0x0479, B:206:0x047e, B:208:0x0483, B:210:0x048f, B:211:0x0494, B:213:0x0499, B:215:0x04a7, B:218:0x04af, B:220:0x04b8, B:228:0x04d9, B:236:0x04f1, B:238:0x04f5, B:246:0x0506, B:248:0x0509, B:261:0x0530, B:250:0x050e, B:252:0x0513, B:254:0x051d, B:256:0x0522, B:258:0x0527, B:239:0x04f8, B:241:0x04fc, B:244:0x0501, B:245:0x0504, B:233:0x04e8, B:286:0x0583, B:263:0x053a, B:265:0x0545, B:267:0x054b, B:269:0x054f, B:271:0x0554, B:273:0x0557, B:274:0x055a, B:276:0x055f, B:278:0x0568, B:281:0x0574, B:283:0x0579, B:270:0x0552, B:287:0x058a, B:289:0x0594, B:297:0x05b4, B:306:0x05cf, B:308:0x05d3, B:316:0x05e4, B:329:0x060b, B:318:0x05e7, B:320:0x05ec, B:324:0x05fb, B:326:0x0601, B:328:0x0609, B:309:0x05d6, B:311:0x05da, B:314:0x05df, B:315:0x05e2, B:302:0x05c3, B:352:0x0664, B:331:0x0616, B:333:0x0626, B:335:0x062c, B:337:0x0630, B:339:0x0635, B:341:0x0639, B:343:0x063f, B:345:0x0648, B:347:0x064e, B:349:0x0657, B:351:0x0662, B:338:0x0633, B:353:0x066d, B:355:0x0672, B:358:0x0678, B:360:0x067c, B:361:0x067f, B:363:0x0683, B:365:0x0687, B:146:0x039b, B:148:0x039f, B:150:0x03a7, B:151:0x03aa, B:152:0x03ac, B:153:0x03af), top: B:379:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:216:0x04ab A[PHI: r8
      0x04ab: PHI (r8v6 int) = (r8v5 int), (r8v26 int) binds: [B:205:0x047c, B:212:0x0497] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:218:0x04af A[Catch: all -> 0x002b, Exception -> 0x002f, TryCatch #5 {Exception -> 0x002f, all -> 0x002b, blocks: (B:7:0x000a, B:12:0x0033, B:13:0x0036, B:57:0x0139, B:59:0x015f, B:61:0x016a, B:63:0x018e, B:66:0x0198, B:67:0x019f, B:69:0x01a3, B:77:0x01d2, B:70:0x01b0, B:72:0x01b4, B:75:0x01b9, B:76:0x01c6, B:78:0x01e4, B:81:0x01f2, B:83:0x0209, B:85:0x0216, B:86:0x022c, B:88:0x0235, B:105:0x02ad, B:91:0x024d, B:93:0x026b, B:96:0x0275, B:97:0x027c, B:99:0x0280, B:102:0x0285, B:104:0x02a0, B:103:0x0294, B:107:0x02ba, B:109:0x02c1, B:111:0x02d1, B:113:0x02dd, B:116:0x02eb, B:118:0x0307, B:124:0x031d, B:128:0x032c, B:129:0x0334, B:131:0x033a, B:133:0x033e, B:135:0x0357, B:137:0x036f, B:134:0x034b, B:136:0x035f, B:138:0x0378, B:139:0x0381, B:142:0x0387, B:145:0x0398, B:154:0x03b2, B:156:0x03b7, B:158:0x03bc, B:160:0x03c7, B:162:0x03ce, B:164:0x03d3, B:166:0x03e1, B:168:0x03e6, B:170:0x03eb, B:172:0x03f0, B:174:0x03fc, B:175:0x0401, B:177:0x0406, B:179:0x0414, B:180:0x0417, B:182:0x041c, B:184:0x0421, B:186:0x042d, B:187:0x0432, B:189:0x0437, B:191:0x0445, B:192:0x0448, B:194:0x044d, B:196:0x0452, B:198:0x045e, B:199:0x0463, B:201:0x0468, B:203:0x0476, B:204:0x0479, B:206:0x047e, B:208:0x0483, B:210:0x048f, B:211:0x0494, B:213:0x0499, B:215:0x04a7, B:218:0x04af, B:220:0x04b8, B:228:0x04d9, B:236:0x04f1, B:238:0x04f5, B:246:0x0506, B:248:0x0509, B:261:0x0530, B:250:0x050e, B:252:0x0513, B:254:0x051d, B:256:0x0522, B:258:0x0527, B:239:0x04f8, B:241:0x04fc, B:244:0x0501, B:245:0x0504, B:233:0x04e8, B:286:0x0583, B:263:0x053a, B:265:0x0545, B:267:0x054b, B:269:0x054f, B:271:0x0554, B:273:0x0557, B:274:0x055a, B:276:0x055f, B:278:0x0568, B:281:0x0574, B:283:0x0579, B:270:0x0552, B:287:0x058a, B:289:0x0594, B:297:0x05b4, B:306:0x05cf, B:308:0x05d3, B:316:0x05e4, B:329:0x060b, B:318:0x05e7, B:320:0x05ec, B:324:0x05fb, B:326:0x0601, B:328:0x0609, B:309:0x05d6, B:311:0x05da, B:314:0x05df, B:315:0x05e2, B:302:0x05c3, B:352:0x0664, B:331:0x0616, B:333:0x0626, B:335:0x062c, B:337:0x0630, B:339:0x0635, B:341:0x0639, B:343:0x063f, B:345:0x0648, B:347:0x064e, B:349:0x0657, B:351:0x0662, B:338:0x0633, B:353:0x066d, B:355:0x0672, B:358:0x0678, B:360:0x067c, B:361:0x067f, B:363:0x0683, B:365:0x0687, B:146:0x039b, B:148:0x039f, B:150:0x03a7, B:151:0x03aa, B:152:0x03ac, B:153:0x03af), top: B:379:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:235:0x04ee  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x05c9  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0675  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x0693  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0699  */
    /* JADX WARN: Removed duplicated region for block: B:436:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void calcUnreadCounters(boolean r32) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1693
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.calcUnreadCounters(boolean):void");
    }

    private void saveDialogFilterInternal(MessagesController.DialogFilter dialogFilter, boolean z, boolean z2) throws Throwable {
        int i;
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                if (!this.dialogFilters.contains(dialogFilter)) {
                    if (z) {
                        if (this.dialogFilters.get(0).isDefault()) {
                            this.dialogFilters.add(1, dialogFilter);
                        } else {
                            this.dialogFilters.add(0, dialogFilter);
                        }
                    } else {
                        this.dialogFilters.add(dialogFilter);
                    }
                    this.dialogFiltersMap.put(dialogFilter.f1454id, dialogFilter);
                }
                sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO dialog_filter VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            sQLitePreparedStatementExecuteFast.bindInteger(1, dialogFilter.f1454id);
            sQLitePreparedStatementExecuteFast.bindInteger(2, dialogFilter.order);
            sQLitePreparedStatementExecuteFast.bindInteger(3, dialogFilter.unreadCount);
            sQLitePreparedStatementExecuteFast.bindInteger(4, dialogFilter.flags);
            sQLitePreparedStatementExecuteFast.bindString(5, dialogFilter.f1454id == 0 ? "ALL_CHATS" : dialogFilter.name);
            sQLitePreparedStatementExecuteFast.bindInteger(6, dialogFilter.color);
            String str = dialogFilter.emoticon;
            if (str != null) {
                sQLitePreparedStatementExecuteFast.bindString(7, str);
            } else {
                sQLitePreparedStatementExecuteFast.bindNull(7);
            }
            Vector vector = new Vector(new MessagesStorage$$ExternalSyntheticLambda42());
            vector.objects.addAll(dialogFilter.entities);
            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(vector.getObjectSize());
            vector.serializeToStream(nativeByteBuffer);
            sQLitePreparedStatementExecuteFast.bindByteBuffer(8, nativeByteBuffer);
            sQLitePreparedStatementExecuteFast.bindInteger(9, dialogFilter.title_noanimate ? 1 : 0);
            sQLitePreparedStatementExecuteFast.step();
            sQLitePreparedStatementExecuteFast.dispose();
            nativeByteBuffer.reuse();
            if (z2) {
                this.database.executeFast("DELETE FROM dialog_filter_ep WHERE id = " + dialogFilter.f1454id).stepThis().dispose();
                this.database.executeFast("DELETE FROM dialog_filter_pin_v2 WHERE id = " + dialogFilter.f1454id).stepThis().dispose();
                this.database.beginTransaction();
                SQLitePreparedStatement sQLitePreparedStatementExecuteFast2 = this.database.executeFast("REPLACE INTO dialog_filter_pin_v2 VALUES(?, ?, ?)");
                int size = dialogFilter.alwaysShow.size();
                for (int i2 = 0; i2 < size; i2++) {
                    long jLongValue = dialogFilter.alwaysShow.get(i2).longValue();
                    sQLitePreparedStatementExecuteFast2.requery();
                    sQLitePreparedStatementExecuteFast2.bindInteger(1, dialogFilter.f1454id);
                    sQLitePreparedStatementExecuteFast2.bindLong(2, jLongValue);
                    sQLitePreparedStatementExecuteFast2.bindInteger(3, dialogFilter.pinnedDialogs.get(jLongValue, TLObject.FLAG_31));
                    sQLitePreparedStatementExecuteFast2.step();
                }
                int size2 = dialogFilter.pinnedDialogs.size();
                for (int i3 = 0; i3 < size2; i3++) {
                    long jKeyAt = dialogFilter.pinnedDialogs.keyAt(i3);
                    if (DialogObject.isEncryptedDialog(jKeyAt)) {
                        sQLitePreparedStatementExecuteFast2.requery();
                        sQLitePreparedStatementExecuteFast2.bindInteger(1, dialogFilter.f1454id);
                        sQLitePreparedStatementExecuteFast2.bindLong(2, jKeyAt);
                        sQLitePreparedStatementExecuteFast2.bindInteger(3, dialogFilter.pinnedDialogs.valueAt(i3));
                        sQLitePreparedStatementExecuteFast2.step();
                    }
                }
                sQLitePreparedStatementExecuteFast2.dispose();
                sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO dialog_filter_ep VALUES(?, ?)");
                int size3 = dialogFilter.neverShow.size();
                for (i = 0; i < size3; i++) {
                    sQLitePreparedStatementExecuteFast.requery();
                    sQLitePreparedStatementExecuteFast.bindInteger(1, dialogFilter.f1454id);
                    sQLitePreparedStatementExecuteFast.bindLong(2, dialogFilter.neverShow.get(i).longValue());
                    sQLitePreparedStatementExecuteFast.step();
                }
                sQLitePreparedStatementExecuteFast.dispose();
                this.database.commitTransaction();
            }
            SQLiteDatabase sQLiteDatabase = this.database;
            if (sQLiteDatabase != null) {
                sQLiteDatabase.commitTransaction();
            }
        } catch (Exception e2) {
            e = e2;
            sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
            checkSQLException(e);
            SQLiteDatabase sQLiteDatabase2 = this.database;
            if (sQLiteDatabase2 != null) {
                sQLiteDatabase2.commitTransaction();
            }
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
        } catch (Throwable th2) {
            th = th2;
            sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
            SQLiteDatabase sQLiteDatabase3 = this.database;
            if (sQLiteDatabase3 != null) {
                sQLiteDatabase3.commitTransaction();
            }
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    private ArrayList<Long> toPeerIds(ArrayList<TLRPC.InputPeer> arrayList) {
        ArrayList<Long> arrayList2 = new ArrayList<>();
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                TLRPC.InputPeer inputPeer = arrayList.get(i);
                if (inputPeer != null) {
                    long j = inputPeer.user_id;
                    if (j == 0) {
                        long j2 = inputPeer.chat_id;
                        if (j2 == 0) {
                            j2 = inputPeer.channel_id;
                        }
                        j = -j2;
                    }
                    arrayList2.add(Long.valueOf(j));
                }
            }
        }
        return arrayList2;
    }

    public void checkLoadedRemoteFilters(final ArrayList<TLRPC.DialogFilter> arrayList, final Runnable runnable) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda67
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$checkLoadedRemoteFilters$66(arrayList, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0299 A[Catch: Exception -> 0x0020, TryCatch #1 {Exception -> 0x0020, blocks: (B:3:0x0002, B:5:0x0010, B:8:0x0024, B:10:0x005d, B:12:0x0076, B:15:0x007d, B:17:0x0081, B:18:0x0084, B:20:0x0088, B:21:0x008b, B:23:0x008f, B:24:0x0092, B:26:0x0096, B:27:0x0099, B:29:0x009d, B:30:0x00a0, B:32:0x00a4, B:33:0x00a7, B:35:0x00ab, B:36:0x00ae, B:38:0x00b2, B:40:0x00b9, B:41:0x00bc, B:43:0x00d0, B:45:0x00e3, B:50:0x00fd, B:52:0x0105, B:54:0x010b, B:56:0x0111, B:59:0x0118, B:61:0x011c, B:63:0x0122, B:65:0x012e, B:67:0x0136, B:69:0x013a, B:71:0x0140, B:73:0x015d, B:75:0x016f, B:78:0x017f, B:80:0x018d, B:82:0x01a5, B:86:0x01c5, B:85:0x01bc, B:90:0x01d3, B:92:0x01dc, B:96:0x01f7, B:95:0x01e9, B:97:0x01fa, B:99:0x0208, B:101:0x021a, B:107:0x022d, B:109:0x0237, B:111:0x0243, B:112:0x025b, B:114:0x0273, B:116:0x0279, B:118:0x0281, B:102:0x021f, B:106:0x0228, B:105:0x0226, B:121:0x0299, B:122:0x02a1, B:124:0x02a7, B:129:0x02cb, B:131:0x02d0, B:133:0x02d6, B:136:0x02dd, B:137:0x02e4, B:139:0x02ea, B:140:0x02f4, B:142:0x02fa, B:149:0x0310, B:152:0x031c, B:155:0x0324, B:156:0x0327, B:158:0x032c, B:134:0x02d9, B:130:0x02ce, B:159:0x032f, B:161:0x0335, B:164:0x0347, B:167:0x0351, B:227:0x049e, B:49:0x00f2, B:170:0x036a, B:172:0x0391, B:174:0x0395, B:178:0x03a4, B:180:0x03ad, B:182:0x03bc, B:188:0x03cf, B:190:0x03d7, B:191:0x03dc, B:193:0x03f9, B:194:0x03ff, B:183:0x03c1, B:187:0x03ca, B:186:0x03c8, B:197:0x0413, B:200:0x041a, B:202:0x041f, B:204:0x0426, B:206:0x0432, B:208:0x043c, B:209:0x043f, B:211:0x0445, B:224:0x047e, B:213:0x0450, B:217:0x0459, B:219:0x0468, B:220:0x046b, B:222:0x0473, B:216:0x0457, B:225:0x0485, B:201:0x041d, B:198:0x0416, B:226:0x0492, B:228:0x04b0, B:231:0x04c4, B:233:0x04da, B:235:0x04f3, B:237:0x04fe, B:239:0x0508, B:240:0x051a, B:242:0x0525, B:244:0x0533, B:245:0x0545, B:247:0x054b, B:249:0x0551, B:251:0x0557, B:234:0x04ee), top: B:262:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0335 A[Catch: Exception -> 0x0020, TryCatch #1 {Exception -> 0x0020, blocks: (B:3:0x0002, B:5:0x0010, B:8:0x0024, B:10:0x005d, B:12:0x0076, B:15:0x007d, B:17:0x0081, B:18:0x0084, B:20:0x0088, B:21:0x008b, B:23:0x008f, B:24:0x0092, B:26:0x0096, B:27:0x0099, B:29:0x009d, B:30:0x00a0, B:32:0x00a4, B:33:0x00a7, B:35:0x00ab, B:36:0x00ae, B:38:0x00b2, B:40:0x00b9, B:41:0x00bc, B:43:0x00d0, B:45:0x00e3, B:50:0x00fd, B:52:0x0105, B:54:0x010b, B:56:0x0111, B:59:0x0118, B:61:0x011c, B:63:0x0122, B:65:0x012e, B:67:0x0136, B:69:0x013a, B:71:0x0140, B:73:0x015d, B:75:0x016f, B:78:0x017f, B:80:0x018d, B:82:0x01a5, B:86:0x01c5, B:85:0x01bc, B:90:0x01d3, B:92:0x01dc, B:96:0x01f7, B:95:0x01e9, B:97:0x01fa, B:99:0x0208, B:101:0x021a, B:107:0x022d, B:109:0x0237, B:111:0x0243, B:112:0x025b, B:114:0x0273, B:116:0x0279, B:118:0x0281, B:102:0x021f, B:106:0x0228, B:105:0x0226, B:121:0x0299, B:122:0x02a1, B:124:0x02a7, B:129:0x02cb, B:131:0x02d0, B:133:0x02d6, B:136:0x02dd, B:137:0x02e4, B:139:0x02ea, B:140:0x02f4, B:142:0x02fa, B:149:0x0310, B:152:0x031c, B:155:0x0324, B:156:0x0327, B:158:0x032c, B:134:0x02d9, B:130:0x02ce, B:159:0x032f, B:161:0x0335, B:164:0x0347, B:167:0x0351, B:227:0x049e, B:49:0x00f2, B:170:0x036a, B:172:0x0391, B:174:0x0395, B:178:0x03a4, B:180:0x03ad, B:182:0x03bc, B:188:0x03cf, B:190:0x03d7, B:191:0x03dc, B:193:0x03f9, B:194:0x03ff, B:183:0x03c1, B:187:0x03ca, B:186:0x03c8, B:197:0x0413, B:200:0x041a, B:202:0x041f, B:204:0x0426, B:206:0x0432, B:208:0x043c, B:209:0x043f, B:211:0x0445, B:224:0x047e, B:213:0x0450, B:217:0x0459, B:219:0x0468, B:220:0x046b, B:222:0x0473, B:216:0x0457, B:225:0x0485, B:201:0x041d, B:198:0x0416, B:226:0x0492, B:228:0x04b0, B:231:0x04c4, B:233:0x04da, B:235:0x04f3, B:237:0x04fe, B:239:0x0508, B:240:0x051a, B:242:0x0525, B:244:0x0533, B:245:0x0545, B:247:0x054b, B:249:0x0551, B:251:0x0557, B:234:0x04ee), top: B:262:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0341  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0347 A[Catch: Exception -> 0x0020, TryCatch #1 {Exception -> 0x0020, blocks: (B:3:0x0002, B:5:0x0010, B:8:0x0024, B:10:0x005d, B:12:0x0076, B:15:0x007d, B:17:0x0081, B:18:0x0084, B:20:0x0088, B:21:0x008b, B:23:0x008f, B:24:0x0092, B:26:0x0096, B:27:0x0099, B:29:0x009d, B:30:0x00a0, B:32:0x00a4, B:33:0x00a7, B:35:0x00ab, B:36:0x00ae, B:38:0x00b2, B:40:0x00b9, B:41:0x00bc, B:43:0x00d0, B:45:0x00e3, B:50:0x00fd, B:52:0x0105, B:54:0x010b, B:56:0x0111, B:59:0x0118, B:61:0x011c, B:63:0x0122, B:65:0x012e, B:67:0x0136, B:69:0x013a, B:71:0x0140, B:73:0x015d, B:75:0x016f, B:78:0x017f, B:80:0x018d, B:82:0x01a5, B:86:0x01c5, B:85:0x01bc, B:90:0x01d3, B:92:0x01dc, B:96:0x01f7, B:95:0x01e9, B:97:0x01fa, B:99:0x0208, B:101:0x021a, B:107:0x022d, B:109:0x0237, B:111:0x0243, B:112:0x025b, B:114:0x0273, B:116:0x0279, B:118:0x0281, B:102:0x021f, B:106:0x0228, B:105:0x0226, B:121:0x0299, B:122:0x02a1, B:124:0x02a7, B:129:0x02cb, B:131:0x02d0, B:133:0x02d6, B:136:0x02dd, B:137:0x02e4, B:139:0x02ea, B:140:0x02f4, B:142:0x02fa, B:149:0x0310, B:152:0x031c, B:155:0x0324, B:156:0x0327, B:158:0x032c, B:134:0x02d9, B:130:0x02ce, B:159:0x032f, B:161:0x0335, B:164:0x0347, B:167:0x0351, B:227:0x049e, B:49:0x00f2, B:170:0x036a, B:172:0x0391, B:174:0x0395, B:178:0x03a4, B:180:0x03ad, B:182:0x03bc, B:188:0x03cf, B:190:0x03d7, B:191:0x03dc, B:193:0x03f9, B:194:0x03ff, B:183:0x03c1, B:187:0x03ca, B:186:0x03c8, B:197:0x0413, B:200:0x041a, B:202:0x041f, B:204:0x0426, B:206:0x0432, B:208:0x043c, B:209:0x043f, B:211:0x0445, B:224:0x047e, B:213:0x0450, B:217:0x0459, B:219:0x0468, B:220:0x046b, B:222:0x0473, B:216:0x0457, B:225:0x0485, B:201:0x041d, B:198:0x0416, B:226:0x0492, B:228:0x04b0, B:231:0x04c4, B:233:0x04da, B:235:0x04f3, B:237:0x04fe, B:239:0x0508, B:240:0x051a, B:242:0x0525, B:244:0x0533, B:245:0x0545, B:247:0x054b, B:249:0x0551, B:251:0x0557, B:234:0x04ee), top: B:262:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x034d  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0351 A[Catch: Exception -> 0x0020, TryCatch #1 {Exception -> 0x0020, blocks: (B:3:0x0002, B:5:0x0010, B:8:0x0024, B:10:0x005d, B:12:0x0076, B:15:0x007d, B:17:0x0081, B:18:0x0084, B:20:0x0088, B:21:0x008b, B:23:0x008f, B:24:0x0092, B:26:0x0096, B:27:0x0099, B:29:0x009d, B:30:0x00a0, B:32:0x00a4, B:33:0x00a7, B:35:0x00ab, B:36:0x00ae, B:38:0x00b2, B:40:0x00b9, B:41:0x00bc, B:43:0x00d0, B:45:0x00e3, B:50:0x00fd, B:52:0x0105, B:54:0x010b, B:56:0x0111, B:59:0x0118, B:61:0x011c, B:63:0x0122, B:65:0x012e, B:67:0x0136, B:69:0x013a, B:71:0x0140, B:73:0x015d, B:75:0x016f, B:78:0x017f, B:80:0x018d, B:82:0x01a5, B:86:0x01c5, B:85:0x01bc, B:90:0x01d3, B:92:0x01dc, B:96:0x01f7, B:95:0x01e9, B:97:0x01fa, B:99:0x0208, B:101:0x021a, B:107:0x022d, B:109:0x0237, B:111:0x0243, B:112:0x025b, B:114:0x0273, B:116:0x0279, B:118:0x0281, B:102:0x021f, B:106:0x0228, B:105:0x0226, B:121:0x0299, B:122:0x02a1, B:124:0x02a7, B:129:0x02cb, B:131:0x02d0, B:133:0x02d6, B:136:0x02dd, B:137:0x02e4, B:139:0x02ea, B:140:0x02f4, B:142:0x02fa, B:149:0x0310, B:152:0x031c, B:155:0x0324, B:156:0x0327, B:158:0x032c, B:134:0x02d9, B:130:0x02ce, B:159:0x032f, B:161:0x0335, B:164:0x0347, B:167:0x0351, B:227:0x049e, B:49:0x00f2, B:170:0x036a, B:172:0x0391, B:174:0x0395, B:178:0x03a4, B:180:0x03ad, B:182:0x03bc, B:188:0x03cf, B:190:0x03d7, B:191:0x03dc, B:193:0x03f9, B:194:0x03ff, B:183:0x03c1, B:187:0x03ca, B:186:0x03c8, B:197:0x0413, B:200:0x041a, B:202:0x041f, B:204:0x0426, B:206:0x0432, B:208:0x043c, B:209:0x043f, B:211:0x0445, B:224:0x047e, B:213:0x0450, B:217:0x0459, B:219:0x0468, B:220:0x046b, B:222:0x0473, B:216:0x0457, B:225:0x0485, B:201:0x041d, B:198:0x0416, B:226:0x0492, B:228:0x04b0, B:231:0x04c4, B:233:0x04da, B:235:0x04f3, B:237:0x04fe, B:239:0x0508, B:240:0x051a, B:242:0x0525, B:244:0x0533, B:245:0x0545, B:247:0x054b, B:249:0x0551, B:251:0x0557, B:234:0x04ee), top: B:262:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x035d  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01dc A[Catch: Exception -> 0x0020, TryCatch #1 {Exception -> 0x0020, blocks: (B:3:0x0002, B:5:0x0010, B:8:0x0024, B:10:0x005d, B:12:0x0076, B:15:0x007d, B:17:0x0081, B:18:0x0084, B:20:0x0088, B:21:0x008b, B:23:0x008f, B:24:0x0092, B:26:0x0096, B:27:0x0099, B:29:0x009d, B:30:0x00a0, B:32:0x00a4, B:33:0x00a7, B:35:0x00ab, B:36:0x00ae, B:38:0x00b2, B:40:0x00b9, B:41:0x00bc, B:43:0x00d0, B:45:0x00e3, B:50:0x00fd, B:52:0x0105, B:54:0x010b, B:56:0x0111, B:59:0x0118, B:61:0x011c, B:63:0x0122, B:65:0x012e, B:67:0x0136, B:69:0x013a, B:71:0x0140, B:73:0x015d, B:75:0x016f, B:78:0x017f, B:80:0x018d, B:82:0x01a5, B:86:0x01c5, B:85:0x01bc, B:90:0x01d3, B:92:0x01dc, B:96:0x01f7, B:95:0x01e9, B:97:0x01fa, B:99:0x0208, B:101:0x021a, B:107:0x022d, B:109:0x0237, B:111:0x0243, B:112:0x025b, B:114:0x0273, B:116:0x0279, B:118:0x0281, B:102:0x021f, B:106:0x0228, B:105:0x0226, B:121:0x0299, B:122:0x02a1, B:124:0x02a7, B:129:0x02cb, B:131:0x02d0, B:133:0x02d6, B:136:0x02dd, B:137:0x02e4, B:139:0x02ea, B:140:0x02f4, B:142:0x02fa, B:149:0x0310, B:152:0x031c, B:155:0x0324, B:156:0x0327, B:158:0x032c, B:134:0x02d9, B:130:0x02ce, B:159:0x032f, B:161:0x0335, B:164:0x0347, B:167:0x0351, B:227:0x049e, B:49:0x00f2, B:170:0x036a, B:172:0x0391, B:174:0x0395, B:178:0x03a4, B:180:0x03ad, B:182:0x03bc, B:188:0x03cf, B:190:0x03d7, B:191:0x03dc, B:193:0x03f9, B:194:0x03ff, B:183:0x03c1, B:187:0x03ca, B:186:0x03c8, B:197:0x0413, B:200:0x041a, B:202:0x041f, B:204:0x0426, B:206:0x0432, B:208:0x043c, B:209:0x043f, B:211:0x0445, B:224:0x047e, B:213:0x0450, B:217:0x0459, B:219:0x0468, B:220:0x046b, B:222:0x0473, B:216:0x0457, B:225:0x0485, B:201:0x041d, B:198:0x0416, B:226:0x0492, B:228:0x04b0, B:231:0x04c4, B:233:0x04da, B:235:0x04f3, B:237:0x04fe, B:239:0x0508, B:240:0x051a, B:242:0x0525, B:244:0x0533, B:245:0x0545, B:247:0x054b, B:249:0x0551, B:251:0x0557, B:234:0x04ee), top: B:262:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0208 A[Catch: Exception -> 0x0020, TryCatch #1 {Exception -> 0x0020, blocks: (B:3:0x0002, B:5:0x0010, B:8:0x0024, B:10:0x005d, B:12:0x0076, B:15:0x007d, B:17:0x0081, B:18:0x0084, B:20:0x0088, B:21:0x008b, B:23:0x008f, B:24:0x0092, B:26:0x0096, B:27:0x0099, B:29:0x009d, B:30:0x00a0, B:32:0x00a4, B:33:0x00a7, B:35:0x00ab, B:36:0x00ae, B:38:0x00b2, B:40:0x00b9, B:41:0x00bc, B:43:0x00d0, B:45:0x00e3, B:50:0x00fd, B:52:0x0105, B:54:0x010b, B:56:0x0111, B:59:0x0118, B:61:0x011c, B:63:0x0122, B:65:0x012e, B:67:0x0136, B:69:0x013a, B:71:0x0140, B:73:0x015d, B:75:0x016f, B:78:0x017f, B:80:0x018d, B:82:0x01a5, B:86:0x01c5, B:85:0x01bc, B:90:0x01d3, B:92:0x01dc, B:96:0x01f7, B:95:0x01e9, B:97:0x01fa, B:99:0x0208, B:101:0x021a, B:107:0x022d, B:109:0x0237, B:111:0x0243, B:112:0x025b, B:114:0x0273, B:116:0x0279, B:118:0x0281, B:102:0x021f, B:106:0x0228, B:105:0x0226, B:121:0x0299, B:122:0x02a1, B:124:0x02a7, B:129:0x02cb, B:131:0x02d0, B:133:0x02d6, B:136:0x02dd, B:137:0x02e4, B:139:0x02ea, B:140:0x02f4, B:142:0x02fa, B:149:0x0310, B:152:0x031c, B:155:0x0324, B:156:0x0327, B:158:0x032c, B:134:0x02d9, B:130:0x02ce, B:159:0x032f, B:161:0x0335, B:164:0x0347, B:167:0x0351, B:227:0x049e, B:49:0x00f2, B:170:0x036a, B:172:0x0391, B:174:0x0395, B:178:0x03a4, B:180:0x03ad, B:182:0x03bc, B:188:0x03cf, B:190:0x03d7, B:191:0x03dc, B:193:0x03f9, B:194:0x03ff, B:183:0x03c1, B:187:0x03ca, B:186:0x03c8, B:197:0x0413, B:200:0x041a, B:202:0x041f, B:204:0x0426, B:206:0x0432, B:208:0x043c, B:209:0x043f, B:211:0x0445, B:224:0x047e, B:213:0x0450, B:217:0x0459, B:219:0x0468, B:220:0x046b, B:222:0x0473, B:216:0x0457, B:225:0x0485, B:201:0x041d, B:198:0x0416, B:226:0x0492, B:228:0x04b0, B:231:0x04c4, B:233:0x04da, B:235:0x04f3, B:237:0x04fe, B:239:0x0508, B:240:0x051a, B:242:0x0525, B:244:0x0533, B:245:0x0545, B:247:0x054b, B:249:0x0551, B:251:0x0557, B:234:0x04ee), top: B:262:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$checkLoadedRemoteFilters$66(java.util.ArrayList r34, java.lang.Runnable r35) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1429
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$checkLoadedRemoteFilters$66(java.util.ArrayList, java.lang.Runnable):void");
    }

    public static /* synthetic */ int $r8$lambda$vVCm6WFQ5jw4QHXnAL2Z_FoTP3I(LongSparseIntArray longSparseIntArray, Long l, Long l2) {
        int i = longSparseIntArray.get(l.longValue());
        int i2 = longSparseIntArray.get(l2.longValue());
        if (i > i2) {
            return 1;
        }
        return i < i2 ? -1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: processLoadedFilterPeersInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$processLoadedFilterPeers$68(TLRPC.messages_Dialogs messages_dialogs, TLRPC.messages_Dialogs messages_dialogs2, ArrayList<TLRPC.User> arrayList, ArrayList<TLRPC.Chat> arrayList2, ArrayList<MessagesController.DialogFilter> arrayList3, SparseArray<MessagesController.DialogFilter> sparseArray, ArrayList<Integer> arrayList4, HashMap<Integer, HashSet<Long>> map, HashSet<Integer> hashSet, Runnable runnable) throws Throwable {
        putUsersAndChats(arrayList, arrayList2, true, false);
        int size = sparseArray.size();
        int i = 0;
        boolean z = false;
        while (i < size) {
            lambda$deleteDialogFilter$69(sparseArray.valueAt(i));
            i++;
            z = true;
        }
        Iterator<Integer> it = hashSet.iterator();
        while (it.hasNext()) {
            MessagesController.DialogFilter dialogFilter = this.dialogFiltersMap.get(it.next().intValue());
            if (dialogFilter != null) {
                dialogFilter.pendingUnreadCount = -1;
            }
        }
        for (Map.Entry<Integer, HashSet<Long>> entry : map.entrySet()) {
            MessagesController.DialogFilter dialogFilter2 = this.dialogFiltersMap.get(entry.getKey().intValue());
            if (dialogFilter2 != null) {
                Iterator<Long> it2 = entry.getValue().iterator();
                while (it2.hasNext()) {
                    dialogFilter2.pinnedDialogs.delete(it2.next().longValue());
                }
                z = true;
            }
        }
        int size2 = arrayList3.size();
        int i2 = 0;
        while (i2 < size2) {
            saveDialogFilterInternal(arrayList3.get(i2), false, true);
            i2++;
            z = true;
        }
        int size3 = this.dialogFilters.size();
        boolean z2 = false;
        for (int i3 = 0; i3 < size3; i3++) {
            MessagesController.DialogFilter dialogFilter3 = this.dialogFilters.get(i3);
            int iIndexOf = arrayList4.indexOf(Integer.valueOf(dialogFilter3.f1454id));
            if (dialogFilter3.order != iIndexOf) {
                dialogFilter3.order = iIndexOf;
                z2 = true;
                z = true;
            }
        }
        if (z2) {
            Collections.sort(this.dialogFilters, new Comparator() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda104
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return MessagesStorage.m3820$r8$lambda$NUPrCbAXNPh_rpmGlTpwyZFDA4((MessagesController.DialogFilter) obj, (MessagesController.DialogFilter) obj2);
                }
            });
            saveDialogFiltersOrderInternal();
        }
        int i4 = z ? 1 : 2;
        calcUnreadCounters(true);
        getMessagesController().processLoadedDialogFilters(new ArrayList<>(this.dialogFilters), messages_dialogs, messages_dialogs2, arrayList, arrayList2, null, i4, runnable);
    }

    /* renamed from: $r8$lambda$NUPrCbAX-NPh_rpmGlTpwyZFDA4, reason: not valid java name */
    public static /* synthetic */ int m3820$r8$lambda$NUPrCbAXNPh_rpmGlTpwyZFDA4(MessagesController.DialogFilter dialogFilter, MessagesController.DialogFilter dialogFilter2) {
        int i = dialogFilter.order;
        int i2 = dialogFilter2.order;
        if (i > i2) {
            return 1;
        }
        return i < i2 ? -1 : 0;
    }

    protected void processLoadedFilterPeers(final TLRPC.messages_Dialogs messages_dialogs, final TLRPC.messages_Dialogs messages_dialogs2, final ArrayList<TLRPC.User> arrayList, final ArrayList<TLRPC.Chat> arrayList2, final ArrayList<MessagesController.DialogFilter> arrayList3, final SparseArray<MessagesController.DialogFilter> sparseArray, final ArrayList<Integer> arrayList4, final HashMap<Integer, HashSet<Long>> map, final HashSet<Integer> hashSet, final Runnable runnable) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda53
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$processLoadedFilterPeers$68(messages_dialogs, messages_dialogs2, arrayList, arrayList2, arrayList3, sparseArray, arrayList4, map, hashSet, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: deleteDialogFilterInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$deleteDialogFilter$69(MessagesController.DialogFilter dialogFilter) {
        try {
            this.dialogFilters.remove(dialogFilter);
            this.dialogFiltersMap.remove(dialogFilter.f1454id);
            this.database.executeFast("DELETE FROM dialog_filter WHERE id = " + dialogFilter.f1454id).stepThis().dispose();
            this.database.executeFast("DELETE FROM dialog_filter_ep WHERE id = " + dialogFilter.f1454id).stepThis().dispose();
            this.database.executeFast("DELETE FROM dialog_filter_pin_v2 WHERE id = " + dialogFilter.f1454id).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void deleteDialogFilter(final MessagesController.DialogFilter dialogFilter) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda203
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteDialogFilter$69(dialogFilter);
            }
        });
    }

    public void saveDialogFilter(final MessagesController.DialogFilter dialogFilter, final boolean z, final boolean z2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda174
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$saveDialogFilter$71(dialogFilter, z, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDialogFilter$71(MessagesController.DialogFilter dialogFilter, boolean z, boolean z2) throws Throwable {
        saveDialogFilterInternal(dialogFilter, z, z2);
        calcUnreadCounters(false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda200
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$saveDialogFilter$70();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDialogFilter$70() {
        ArrayList<MessagesController.DialogFilter> arrayList = getMessagesController().dialogFilters;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).unreadCount = arrayList.get(i).pendingUnreadCount;
        }
        this.mainUnreadCount = this.pendingMainUnreadCount;
        this.archiveUnreadCount = this.pendingArchiveUnreadCount;
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_READ_DIALOG_MESSAGE));
    }

    public void saveDialogFiltersOrderInternal() {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE dialog_filter SET ord = ?, flags = ? WHERE id = ?");
                int size = this.dialogFilters.size();
                for (int i = 0; i < size; i++) {
                    MessagesController.DialogFilter dialogFilter = this.dialogFilters.get(i);
                    sQLitePreparedStatementExecuteFast.requery();
                    sQLitePreparedStatementExecuteFast.bindInteger(1, dialogFilter.order);
                    sQLitePreparedStatementExecuteFast.bindInteger(2, dialogFilter.flags);
                    sQLitePreparedStatementExecuteFast.bindInteger(3, dialogFilter.f1454id);
                    sQLitePreparedStatementExecuteFast.step();
                }
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void saveDialogFiltersOrder() {
        final ArrayList arrayList = new ArrayList(getMessagesController().dialogFilters);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda44
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$saveDialogFiltersOrder$72(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveDialogFiltersOrder$72(ArrayList arrayList) {
        this.dialogFilters.clear();
        this.dialogFiltersMap.clear();
        this.dialogFilters.addAll(arrayList);
        for (int i = 0; i < arrayList.size(); i++) {
            ((MessagesController.DialogFilter) arrayList.get(i)).order = i;
            this.dialogFiltersMap.put(((MessagesController.DialogFilter) arrayList.get(i)).f1454id, (MessagesController.DialogFilter) arrayList.get(i));
        }
        saveDialogFiltersOrderInternal();
    }

    protected static void addReplyMessages(TLRPC.Message message, LongSparseArray longSparseArray, LongSparseArray longSparseArray2) {
        int i = message.reply_to.reply_to_msg_id;
        long replyToDialogId = (message.flags & TLObject.FLAG_30) != 0 ? message.quick_reply_shortcut_id : MessageObject.getReplyToDialogId(message);
        SparseArray sparseArray = (SparseArray) longSparseArray.get(replyToDialogId);
        ArrayList arrayList = (ArrayList) longSparseArray2.get(replyToDialogId);
        if (sparseArray == null) {
            sparseArray = new SparseArray();
            longSparseArray.put(replyToDialogId, sparseArray);
        }
        if (arrayList == null) {
            arrayList = new ArrayList();
            longSparseArray2.put(replyToDialogId, arrayList);
        }
        ArrayList arrayList2 = (ArrayList) sparseArray.get(message.reply_to.reply_to_msg_id);
        if (arrayList2 == null) {
            arrayList2 = new ArrayList();
            sparseArray.put(message.reply_to.reply_to_msg_id, arrayList2);
            if (!arrayList.contains(Integer.valueOf(message.reply_to.reply_to_msg_id))) {
                arrayList.add(Integer.valueOf(message.reply_to.reply_to_msg_id));
            }
        }
        arrayList2.add(message);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ea A[Catch: all -> 0x012b, Exception -> 0x012f, TryCatch #4 {Exception -> 0x012f, all -> 0x012b, blocks: (B:41:0x00e4, B:43:0x00ea, B:45:0x00f0, B:47:0x0115, B:54:0x013a, B:56:0x0147, B:58:0x014e, B:53:0x0133, B:61:0x0167), top: B:76:0x00e4 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x017e  */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void loadReplyMessages(androidx.collection.LongSparseArray r24, androidx.collection.LongSparseArray r25, java.util.ArrayList<java.lang.Long> r26, java.util.ArrayList<java.lang.Long> r27, int r28) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 411
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.loadReplyMessages(androidx.collection.LongSparseArray, androidx.collection.LongSparseArray, java.util.ArrayList, java.util.ArrayList, int):void");
    }

    public void loadUnreadMessages() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda182
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$loadUnreadMessages$74();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:207:0x040c  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x05b2  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x05b9  */
    /* JADX WARN: Removed duplicated region for block: B:346:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x019b A[Catch: all -> 0x0212, Exception -> 0x0227, TRY_ENTER, TRY_LEAVE, TryCatch #6 {all -> 0x0212, blocks: (B:45:0x0122, B:47:0x0128, B:49:0x012f, B:66:0x01a2, B:74:0x01be, B:76:0x01c2, B:78:0x01c6, B:90:0x01df, B:92:0x01e5, B:94:0x01eb, B:62:0x019b), top: B:290:0x0122 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01da  */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r10v13, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v23 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$loadUnreadMessages$74() throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1469
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$loadUnreadMessages$74():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadUnreadMessages$73(LongSparseArray longSparseArray, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4, ArrayList arrayList5, HashMap map) {
        getNotificationsController().processLoadedUnreadMessages(longSparseArray, arrayList, arrayList2, arrayList3, arrayList4, arrayList5, map.values());
    }

    public void putWallpapers(final ArrayList<TLRPC.WallPaper> arrayList, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda167
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$putWallpapers$75(i, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$putWallpapers$75(int r11, java.util.ArrayList r12) throws java.lang.Throwable {
        /*
            r10 = this;
            r0 = 0
            r1 = 1
            if (r11 != r1) goto L1a
            org.telegram.SQLite.SQLiteDatabase r2 = r10.database     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
            java.lang.String r3 = "DELETE FROM wallpapers2 WHERE num >= -1"
            org.telegram.SQLite.SQLitePreparedStatement r2 = r2.executeFast(r3)     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
            org.telegram.SQLite.SQLitePreparedStatement r2 = r2.stepThis()     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
            r2.dispose()     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
            goto L1a
        L14:
            r11 = move-exception
            goto La0
        L17:
            r11 = move-exception
            goto L90
        L1a:
            org.telegram.SQLite.SQLiteDatabase r2 = r10.database     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
            r2.beginTransaction()     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
            if (r11 == 0) goto L2a
            org.telegram.SQLite.SQLiteDatabase r2 = r10.database     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
            java.lang.String r3 = "REPLACE INTO wallpapers2 VALUES(?, ?, ?)"
            org.telegram.SQLite.SQLitePreparedStatement r2 = r2.executeFast(r3)     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
            goto L32
        L2a:
            org.telegram.SQLite.SQLiteDatabase r2 = r10.database     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
            java.lang.String r3 = "UPDATE wallpapers2 SET data = ? WHERE uid = ?"
            org.telegram.SQLite.SQLitePreparedStatement r2 = r2.executeFast(r3)     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
        L32:
            int r3 = r12.size()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r4 = 0
        L37:
            if (r4 >= r3) goto L80
            java.lang.Object r5 = r12.get(r4)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            org.telegram.tgnet.TLRPC$WallPaper r5 = (org.telegram.tgnet.TLRPC.WallPaper) r5     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r2.requery()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            org.telegram.tgnet.NativeByteBuffer r6 = new org.telegram.tgnet.NativeByteBuffer     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            int r7 = r5.getObjectSize()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r5.serializeToStream(r6)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r7 = 2
            if (r11 == 0) goto L6f
            long r8 = r5.f1742id     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r2.bindLong(r1, r8)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r2.bindByteBuffer(r7, r6)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r5 = 3
            if (r11 >= 0) goto L66
            r2.bindInteger(r5, r11)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            goto L77
        L60:
            r11 = move-exception
            r0 = r2
            goto La0
        L63:
            r11 = move-exception
            r0 = r2
            goto L90
        L66:
            if (r11 != r7) goto L6a
            r7 = -1
            goto L6b
        L6a:
            r7 = r4
        L6b:
            r2.bindInteger(r5, r7)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            goto L77
        L6f:
            r2.bindByteBuffer(r1, r6)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            long r8 = r5.f1742id     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r2.bindLong(r7, r8)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
        L77:
            r2.step()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            r6.reuse()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            int r4 = r4 + 1
            goto L37
        L80:
            r2.dispose()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L63
            org.telegram.SQLite.SQLiteDatabase r11 = r10.database     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
            r11.commitTransaction()     // Catch: java.lang.Throwable -> L14 java.lang.Exception -> L17
            org.telegram.SQLite.SQLiteDatabase r11 = r10.database
            if (r11 == 0) goto L9f
            r11.commitTransaction()
            return
        L90:
            r10.checkSQLException(r11)     // Catch: java.lang.Throwable -> L14
            org.telegram.SQLite.SQLiteDatabase r11 = r10.database
            if (r11 == 0) goto L9a
            r11.commitTransaction()
        L9a:
            if (r0 == 0) goto L9f
            r0.dispose()
        L9f:
            return
        La0:
            org.telegram.SQLite.SQLiteDatabase r12 = r10.database
            if (r12 == 0) goto La7
            r12.commitTransaction()
        La7:
            if (r0 == 0) goto Lac
            r0.dispose()
        Lac:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putWallpapers$75(int, java.util.ArrayList):void");
    }

    public void deleteWallpaper(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda219
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteWallpaper$76(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteWallpaper$76(long j) {
        try {
            this.database.executeFast("DELETE FROM wallpapers2 WHERE uid = " + j).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void getWallpapers() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda159
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getWallpapers$78();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getWallpapers$78() {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT data FROM wallpapers2 WHERE 1 ORDER BY num ASC", new Object[0]);
                final ArrayList arrayList = new ArrayList();
                while (sQLiteCursorQueryFinalized.next()) {
                    NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                    if (nativeByteBufferByteBufferValue != null) {
                        TLRPC.WallPaper wallPaperTLdeserialize = TLRPC.WallPaper.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                        nativeByteBufferByteBufferValue.reuse();
                        if (wallPaperTLdeserialize != null) {
                            arrayList.add(wallPaperTLdeserialize);
                        }
                    }
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda153
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.wallpapersDidLoad, arrayList);
                    }
                });
                sQLiteCursorQueryFinalized.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public void addRecentLocalFile(final String str, final String str2, final TLRPC.Document document) {
        if (str == null || str.length() == 0) {
            return;
        }
        if ((str2 == null || str2.length() == 0) && document == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda194
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$addRecentLocalFile$79(document, str, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addRecentLocalFile$79(TLRPC.Document document, String str, String str2) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                if (document != null) {
                    sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE web_recent_v3 SET document = ? WHERE image_url = ?");
                    sQLitePreparedStatementExecuteFast.requery();
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(document.getObjectSize());
                    document.serializeToStream(nativeByteBuffer);
                    sQLitePreparedStatementExecuteFast.bindByteBuffer(1, nativeByteBuffer);
                    sQLitePreparedStatementExecuteFast.bindString(2, str);
                    sQLitePreparedStatementExecuteFast.step();
                    sQLitePreparedStatementExecuteFast.dispose();
                    nativeByteBuffer.reuse();
                } else {
                    sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE web_recent_v3 SET local_url = ? WHERE image_url = ?");
                    sQLitePreparedStatementExecuteFast.requery();
                    sQLitePreparedStatementExecuteFast.bindString(1, str2);
                    sQLitePreparedStatementExecuteFast.bindString(2, str);
                    sQLitePreparedStatementExecuteFast.step();
                    sQLitePreparedStatementExecuteFast.dispose();
                }
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void deleteUserChatHistory(final long j, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda231
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$deleteUserChatHistory$82(j, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a8 A[Catch: all -> 0x00ac, Exception -> 0x00af, TryCatch #0 {Exception -> 0x00af, blocks: (B:3:0x0005, B:41:0x00a4, B:43:0x00a8, B:53:0x00cf, B:57:0x00e1, B:63:0x00f3, B:65:0x0104, B:48:0x00b2, B:50:0x00b8, B:52:0x00bf), top: B:80:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b2 A[Catch: all -> 0x00ac, Exception -> 0x00af, TryCatch #0 {Exception -> 0x00af, blocks: (B:3:0x0005, B:41:0x00a4, B:43:0x00a8, B:53:0x00cf, B:57:0x00e1, B:63:0x00f3, B:65:0x0104, B:48:0x00b2, B:50:0x00b8, B:52:0x00bf), top: B:80:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0104 A[Catch: all -> 0x00ac, Exception -> 0x00af, TRY_LEAVE, TryCatch #0 {Exception -> 0x00af, blocks: (B:3:0x0005, B:41:0x00a4, B:43:0x00a8, B:53:0x00cf, B:57:0x00e1, B:63:0x00f3, B:65:0x0104, B:48:0x00b2, B:50:0x00b8, B:52:0x00bf), top: B:80:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$deleteUserChatHistory$82(final long r18, long r20) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 294
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$deleteUserChatHistory$82(long, long):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteUserChatHistory$80(ArrayList arrayList, long j, ArrayList arrayList2) {
        if (!AyuConfig.saveDeletedMessages) {
            getFileLoader().cancelLoadFiles(arrayList);
        }
        getMessagesController().markDialogMessageAsDeleted(j, arrayList2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteUserChatHistory$81(ArrayList arrayList, long j) {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messagesDeleted, arrayList, Long.valueOf(DialogObject.isChatDialog(j) ? -j : 0L), Boolean.FALSE);
    }

    private boolean addFilesToDelete(TLRPC.Message message, ArrayList<File> arrayList, ArrayList<Pair<Long, Integer>> arrayList2, ArrayList<String> arrayList3, boolean z) {
        long j;
        int i;
        int i2 = 0;
        if (message == null) {
            return false;
        }
        TLRPC.Document document = MessageObject.getDocument(message);
        TLRPC.Photo photo = MessageObject.getPhoto(message);
        if (MessageObject.isVoiceMessage(message)) {
            if (document == null || getMediaDataController().ringtoneDataStore.contains(document.f1579id)) {
                return false;
            }
            j = document.f1579id;
            i = 2;
        } else {
            if (MessageObject.isStickerMessage(message) || MessageObject.isAnimatedStickerMessage(message)) {
                if (document == null) {
                    return false;
                }
                j = document.f1579id;
            } else if (MessageObject.isVideoMessage(message) || MessageObject.isRoundVideoMessage(message) || MessageObject.isGifMessage(message)) {
                if (document == null) {
                    return false;
                }
                j = document.f1579id;
                i = 4;
            } else if (document != null) {
                if (getMediaDataController().ringtoneDataStore.contains(document.f1579id)) {
                    return false;
                }
                j = document.f1579id;
                i = 8;
            } else if (photo == null || FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.getPhotoSize()) == null) {
                j = 0;
                i = 0;
            } else {
                j = photo.f1603id;
            }
            i = 1;
        }
        if (j != 0) {
            arrayList2.add(new Pair<>(Long.valueOf(j), Integer.valueOf(i)));
        }
        if (photo != null) {
            int size = photo.sizes.size();
            while (i2 < size) {
                TLRPC.PhotoSize photoSize = (TLRPC.PhotoSize) photo.sizes.get(i2);
                String attachFileName = FileLoader.getAttachFileName(photoSize);
                if (!TextUtils.isEmpty(attachFileName)) {
                    arrayList3.add(attachFileName);
                }
                File pathToAttach = getFileLoader().getPathToAttach(photoSize, z);
                if (pathToAttach.toString().length() > 0) {
                    arrayList.add(pathToAttach);
                }
                i2++;
            }
            return true;
        }
        if (document == null) {
            return false;
        }
        String attachFileName2 = FileLoader.getAttachFileName(document);
        if (!TextUtils.isEmpty(attachFileName2)) {
            arrayList3.add(attachFileName2);
        }
        File pathToAttach2 = getFileLoader().getPathToAttach(document, z);
        if (pathToAttach2.toString().length() > 0) {
            arrayList.add(pathToAttach2);
        }
        int size2 = document.thumbs.size();
        while (i2 < size2) {
            File pathToAttach3 = getFileLoader().getPathToAttach(document.thumbs.get(i2));
            if (pathToAttach3.toString().length() > 0) {
                arrayList.add(pathToAttach3);
            }
            i2++;
        }
        return true;
    }

    public void deleteDialog(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda39
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$deleteDialog$85(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:121:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0334  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0439 A[Catch: all -> 0x045f, Exception -> 0x0462, TryCatch #29 {Exception -> 0x0462, all -> 0x045f, blocks: (B:169:0x0487, B:171:0x04cd, B:172:0x04e9, B:159:0x039d, B:161:0x0439, B:163:0x043f, B:168:0x0465), top: B:223:0x039d }] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0465 A[Catch: all -> 0x045f, Exception -> 0x0462, TryCatch #29 {Exception -> 0x0462, all -> 0x045f, blocks: (B:169:0x0487, B:171:0x04cd, B:172:0x04e9, B:159:0x039d, B:161:0x0439, B:163:0x043f, B:168:0x0465), top: B:223:0x039d }] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x04cd A[Catch: all -> 0x045f, Exception -> 0x0462, TryCatch #29 {Exception -> 0x0462, all -> 0x045f, blocks: (B:169:0x0487, B:171:0x04cd, B:172:0x04e9, B:159:0x039d, B:161:0x0439, B:163:0x043f, B:168:0x0465), top: B:223:0x039d }] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x05bb  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x05c0  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x05c5  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x05ca  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x05d1  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x05d6  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x05db  */
    /* JADX WARN: Removed duplicated region for block: B:199:0x05e0  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x021c A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:246:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0068 A[Catch: all -> 0x004b, Exception -> 0x0051, TRY_ENTER, TRY_LEAVE, TryCatch #23 {Exception -> 0x0051, all -> 0x004b, blocks: (B:4:0x000c, B:28:0x0057, B:69:0x011f, B:34:0x0068, B:59:0x00e5, B:61:0x00e9, B:62:0x00f4), top: B:235:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0096 A[Catch: all -> 0x00c3, Exception -> 0x00c6, TryCatch #27 {Exception -> 0x00c6, all -> 0x00c3, blocks: (B:37:0x0090, B:39:0x0096, B:41:0x009c), top: B:227:0x0090 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00e9 A[Catch: all -> 0x004b, Exception -> 0x0051, TryCatch #23 {Exception -> 0x0051, all -> 0x004b, blocks: (B:4:0x000c, B:28:0x0057, B:69:0x011f, B:34:0x0068, B:59:0x00e5, B:61:0x00e9, B:62:0x00f4), top: B:235:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x010d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x010f  */
    /* JADX WARN: Type inference failed for: r12v0 */
    /* JADX WARN: Type inference failed for: r12v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r12v8 */
    /* JADX WARN: Type inference failed for: r14v7 */
    /* JADX WARN: Type inference failed for: r14v8, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$deleteDialog$85(int r27, long r28) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1508
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$deleteDialog$85(int, long):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDialog$83(ArrayList arrayList) {
        getFileLoader().cancelLoadFiles(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteDialog$84() {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.needReloadRecentDialogsSearch, new Object[0]);
    }

    public void onDeleteQueryComplete(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda235
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onDeleteQueryComplete$86(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDeleteQueryComplete$86(long j) {
        try {
            this.database.executeFast("DELETE FROM media_counts_v2 WHERE uid = " + j).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void clearUserPhotos(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda171
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$clearUserPhotos$87(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearUserPhotos$87(long j) {
        try {
            this.database.executeFast("DELETE FROM dialog_photos WHERE uid = " + j).stepThis().dispose();
            this.database.executeFast("DELETE FROM dialog_photos_count WHERE uid = " + j).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void clearUserPhoto(final long j, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda89
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$clearUserPhoto$88(j, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearUserPhoto$88(long j, long j2) {
        try {
            this.database.executeFast("DELETE FROM dialog_photos WHERE uid = " + j + " AND id = " + j2).stepThis().dispose();
            this.database.executeFast("UPDATE dialog_photos_count SET count = count - 1 WHERE uid = " + j + " AND count > 0").stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void resetDialogs(final TLRPC.messages_Dialogs messages_dialogs, final int i, final int i2, final int i3, final int i4, final int i5, final LongSparseArray longSparseArray, final LongSparseArray longSparseArray2, final TLRPC.Message message, final int i6) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda76
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$resetDialogs$90(messages_dialogs, i6, i2, i3, i4, i5, message, i, longSparseArray, longSparseArray2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0367  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x036c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$resetDialogs$90(org.telegram.tgnet.TLRPC.messages_Dialogs r31, int r32, int r33, int r34, int r35, int r36, org.telegram.tgnet.TLRPC.Message r37, int r38, androidx.collection.LongSparseArray r39, androidx.collection.LongSparseArray r40) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 880
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$resetDialogs$90(org.telegram.tgnet.TLRPC$messages_Dialogs, int, int, int, int, int, org.telegram.tgnet.TLRPC$Message, int, androidx.collection.LongSparseArray, androidx.collection.LongSparseArray):void");
    }

    public static /* synthetic */ int $r8$lambda$kzsBmgH1x5upvkYeW9qC366tBhw(LongSparseIntArray longSparseIntArray, Long l, Long l2) {
        int i = longSparseIntArray.get(l.longValue());
        int i2 = longSparseIntArray.get(l2.longValue());
        if (i < i2) {
            return 1;
        }
        return i > i2 ? -1 : 0;
    }

    public void emptyMessagesMedia(final long j, final ArrayList<Integer> arrayList) {
        if (AyuConfig.saveDeletedMessages) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda91
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$emptyMessagesMedia$94(arrayList, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0239  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0245  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x024a  */
    /* JADX WARN: Removed duplicated region for block: B:142:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$emptyMessagesMedia$94(java.util.ArrayList r17, long r18) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 590
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$emptyMessagesMedia$94(java.util.ArrayList, long):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$emptyMessagesMedia$91(ArrayList arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateMessageMedia, arrayList.get(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$emptyMessagesMedia$92(ArrayList arrayList) {
        getFileLoader().cancelLoadFiles(arrayList);
    }

    private /* synthetic */ void lambda$emptyMessagesMedia$93(ArrayList arrayList) {
        if (getMessagesController().getSavedMessagesController().updateSavedDialogs(arrayList)) {
            getMessagesController().getSavedMessagesController().update();
        }
    }

    public void toggleTodo(final long j, final int i, final int i2, final boolean z, final long j2) {
        final long clientUserId = getUserConfig().getClientUserId();
        final int currentTime = getConnectionsManager().getCurrentTime();
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda93
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$toggleTodo$95(j, i, clientUserId, j2, i2, z, currentTime);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:105:0x00c3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:117:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:119:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0186 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0191  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$toggleTodo$95(long r27, int r29, long r30, long r32, int r34, boolean r35, int r36) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 405
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$toggleTodo$95(long, int, long, long, int, boolean, int):void");
    }

    public void updateMessagePollResults(final long j, final TLRPC.Poll poll, final TLRPC.PollResults pollResults) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda96
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateMessagePollResults$96(j, poll, pollResults);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v17 */
    public /* synthetic */ void lambda$updateMessagePollResults$96(long j, TLRPC.Poll poll, TLRPC.PollResults pollResults) throws Throwable {
        SQLiteCursor sQLiteCursor;
        SQLiteCursor sQLiteCursor2;
        SQLitePreparedStatement sQLitePreparedStatement;
        LongSparseArray longSparseArray;
        SQLitePreparedStatement sQLitePreparedStatement2;
        ?? r8;
        SQLitePreparedStatement sQLitePreparedStatement3;
        SQLiteCursor sQLiteCursorQueryFinalized;
        int i;
        SQLiteCursor sQLiteCursor3 = null;
        sQLiteCursor3 = null;
        SQLiteCursor sQLiteCursor4 = null;
        SQLiteCursor sQLiteCursor5 = null;
        try {
            try {
                int i2 = 1;
                int i3 = 0;
                SQLiteCursor sQLiteCursorQueryFinalized2 = this.database.queryFinalized(String.format(Locale.US, "SELECT uid, mid FROM polls_v2 WHERE id = %d", Long.valueOf(j)), new Object[0]);
                LongSparseArray longSparseArray2 = null;
                while (sQLiteCursorQueryFinalized2.next()) {
                    try {
                        long jLongValue = sQLiteCursorQueryFinalized2.longValue(0);
                        if (longSparseArray2 == null) {
                            longSparseArray2 = new LongSparseArray();
                        }
                        ArrayList arrayList = (ArrayList) longSparseArray2.get(jLongValue);
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                            longSparseArray2.put(jLongValue, arrayList);
                        }
                        arrayList.add(Integer.valueOf(sQLiteCursorQueryFinalized2.intValue(1)));
                    } catch (Exception e) {
                        e = e;
                        sQLiteCursor = sQLiteCursorQueryFinalized2;
                    } catch (Throwable th) {
                        th = th;
                        sQLiteCursor5 = sQLiteCursorQueryFinalized2;
                    }
                }
                sQLiteCursorQueryFinalized2.dispose();
                if (longSparseArray2 != null) {
                    this.database.beginTransaction();
                    SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE messages_v2 SET data = ? WHERE mid = ? AND uid = ?");
                    SQLitePreparedStatement sQLitePreparedStatementExecuteFast2 = this.database.executeFast("UPDATE messages_topics SET data = ? WHERE mid = ? AND uid = ?");
                    int size = longSparseArray2.size();
                    int i4 = 0;
                    while (i4 < size) {
                        long jKeyAt = longSparseArray2.keyAt(i4);
                        ArrayList arrayList2 = (ArrayList) longSparseArray2.valueAt(i4);
                        int size2 = arrayList2.size();
                        int i5 = 0;
                        SQLiteCursor sQLiteCursor6 = sQLiteCursor3;
                        while (i5 < size2) {
                            Integer num = (Integer) arrayList2.get(i5);
                            boolean z = false;
                            SQLiteCursor sQLiteCursor7 = sQLiteCursor6;
                            while (true) {
                                sQLiteCursor2 = sQLiteCursor7;
                                if (i3 >= 2) {
                                    break;
                                }
                                if (i3 == i2) {
                                    try {
                                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                                        longSparseArray = longSparseArray2;
                                        sQLitePreparedStatement2 = sQLitePreparedStatementExecuteFast2;
                                        sQLitePreparedStatement3 = sQLitePreparedStatement2;
                                        r8 = 0;
                                        sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM messages_topics WHERE mid = %d AND uid = %d", num, Long.valueOf(jKeyAt)), new Object[0]);
                                    } catch (Exception e2) {
                                        e = e2;
                                        sQLiteCursor = sQLiteCursor2;
                                        checkSQLException(e);
                                        if (sQLiteCursor != null) {
                                            sQLiteCursor.dispose();
                                            return;
                                        }
                                        return;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        sQLiteCursor5 = sQLiteCursor2;
                                        if (sQLiteCursor5 != null) {
                                            sQLiteCursor5.dispose();
                                        }
                                        throw th;
                                    }
                                } else {
                                    sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                                    longSparseArray = longSparseArray2;
                                    sQLitePreparedStatement2 = sQLitePreparedStatementExecuteFast2;
                                    r8 = 0;
                                    sQLitePreparedStatement3 = sQLitePreparedStatement;
                                    sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM messages_v2 WHERE mid = %d AND uid = %d", num, Long.valueOf(jKeyAt)), new Object[0]);
                                }
                                if (sQLiteCursorQueryFinalized.next()) {
                                    NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(r8);
                                    if (nativeByteBufferByteBufferValue != 0) {
                                        TLRPC.Message messageTLdeserialize = TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(r8), r8);
                                        i = size;
                                        messageTLdeserialize.readAttachPath(nativeByteBufferByteBufferValue, getUserConfig().clientUserId);
                                        nativeByteBufferByteBufferValue.reuse();
                                        TLRPC.MessageMedia messageMedia = messageTLdeserialize.media;
                                        if (messageMedia instanceof TLRPC.TL_messageMediaPoll) {
                                            TLRPC.TL_messageMediaPoll tL_messageMediaPoll = (TLRPC.TL_messageMediaPoll) messageMedia;
                                            if (poll != null) {
                                                tL_messageMediaPoll.poll = poll;
                                            }
                                            if (pollResults != null) {
                                                MessageObject.updatePollResults(tL_messageMediaPoll, pollResults);
                                            }
                                            MessageObject.normalizeFlags(messageTLdeserialize);
                                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(messageTLdeserialize.getObjectSize());
                                            messageTLdeserialize.serializeToStream(nativeByteBuffer);
                                            sQLitePreparedStatement3.requery();
                                            sQLitePreparedStatement3.bindByteBuffer(1, nativeByteBuffer);
                                            sQLitePreparedStatement3.bindInteger(2, num.intValue());
                                            sQLitePreparedStatement3.bindLong(3, jKeyAt);
                                            sQLitePreparedStatement3.step();
                                            nativeByteBuffer.reuse();
                                        }
                                    } else {
                                        i = size;
                                    }
                                    z = true;
                                } else {
                                    i = size;
                                }
                                sQLiteCursorQueryFinalized.dispose();
                                i3++;
                                sQLitePreparedStatementExecuteFast = sQLitePreparedStatement;
                                size = i;
                                longSparseArray2 = longSparseArray;
                                sQLitePreparedStatementExecuteFast2 = sQLitePreparedStatement2;
                                i2 = 1;
                                sQLiteCursor7 = sQLiteCursorQueryFinalized;
                            }
                            SQLitePreparedStatement sQLitePreparedStatement4 = sQLitePreparedStatementExecuteFast;
                            LongSparseArray longSparseArray3 = longSparseArray2;
                            SQLitePreparedStatement sQLitePreparedStatement5 = sQLitePreparedStatementExecuteFast2;
                            int i6 = size;
                            if (!z) {
                                this.database.executeFast(String.format(Locale.US, "DELETE FROM polls_v2 WHERE mid = %d AND uid = %d", num, Long.valueOf(jKeyAt))).stepThis().dispose();
                            }
                            i5++;
                            sQLiteCursor6 = sQLiteCursor2;
                            sQLitePreparedStatementExecuteFast = sQLitePreparedStatement4;
                            size = i6;
                            longSparseArray2 = longSparseArray3;
                            sQLitePreparedStatementExecuteFast2 = sQLitePreparedStatement5;
                            i2 = 1;
                            i3 = 0;
                        }
                        i4++;
                        i2 = 1;
                        i3 = 0;
                        sQLiteCursor3 = sQLiteCursor6;
                    }
                    SQLitePreparedStatement sQLitePreparedStatement6 = sQLitePreparedStatementExecuteFast2;
                    sQLitePreparedStatementExecuteFast.dispose();
                    sQLitePreparedStatement6.dispose();
                    this.database.commitTransaction();
                    sQLiteCursor4 = sQLiteCursor3;
                }
                if (sQLiteCursor4 != null) {
                    sQLiteCursor4.dispose();
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e3) {
            e = e3;
            sQLiteCursor = sQLiteCursor3;
        }
    }

    public void searchSavedByTag(final TLRPC.Reaction reaction, final long j, final String str, final int i, final int i2, final Utilities.Callback4<ArrayList<MessageObject>, ArrayList<TLRPC.User>, ArrayList<TLRPC.Chat>, ArrayList<TLRPC.Document>> callback4, final boolean z) {
        if (callback4 == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda95
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$searchSavedByTag$98(str, j, reaction, i, i2, z, callback4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:121:0x025a  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x025f  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0264  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x026b  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:156:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$searchSavedByTag$98(java.lang.String r38, long r39, org.telegram.tgnet.TLRPC.Reaction r41, int r42, int r43, boolean r44, final org.telegram.messenger.Utilities.Callback4 r45) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 633
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$searchSavedByTag$98(java.lang.String, long, org.telegram.tgnet.TLRPC$Reaction, int, int, boolean, org.telegram.messenger.Utilities$Callback4):void");
    }

    public void updateMessageReactions(final long j, final int i, final TLRPC.TL_messageReactions tL_messageReactions) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda139
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateMessageReactions$99(i, j, tL_messageReactions);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:105:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01df  */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v2, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$updateMessageReactions$99(int r25, long r26, org.telegram.tgnet.TLRPC.TL_messageReactions r28) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 496
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateMessageReactions$99(int, long, org.telegram.tgnet.TLRPC$TL_messageReactions):void");
    }

    /* loaded from: classes4.dex */
    private class SavedReactionsUpdate {
        TLRPC.TL_messageReactions last;
        TLRPC.TL_messageReactions old;
        long topic_id;

        public SavedReactionsUpdate(long j, TLRPC.Message message, TLRPC.Message message2) {
            this.topic_id = MessageObject.getSavedDialogId(j, message2);
            this.old = message.reactions;
            this.last = message2.reactions;
        }
    }

    private void onReactionsUpdate(final ArrayList<SavedReactionsUpdate> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onReactionsUpdate$100(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReactionsUpdate$100(ArrayList arrayList) {
        HashSet<Long> hashSet = new HashSet<>();
        LongSparseArray longSparseArray = new LongSparseArray();
        LongSparseArray longSparseArray2 = new LongSparseArray();
        boolean z = false;
        for (int i = 0; i < arrayList.size(); i++) {
            SavedReactionsUpdate savedReactionsUpdate = (SavedReactionsUpdate) arrayList.get(i);
            TLRPC.TL_messageReactions tL_messageReactions = savedReactionsUpdate.old;
            TLRPC.TL_messageReactions tL_messageReactions2 = savedReactionsUpdate.last;
            longSparseArray.clear();
            longSparseArray2.clear();
            if (tL_messageReactions != null && tL_messageReactions.results != null && tL_messageReactions.reactions_as_tags) {
                for (int i2 = 0; i2 < tL_messageReactions.results.size(); i2++) {
                    ReactionsLayoutInBubble.VisibleReaction visibleReactionFromTL = ReactionsLayoutInBubble.VisibleReaction.fromTL(((TLRPC.ReactionCount) tL_messageReactions.results.get(i2)).reaction);
                    if (visibleReactionFromTL != null) {
                        longSparseArray.put(visibleReactionFromTL.hash, visibleReactionFromTL);
                    }
                }
            }
            if (tL_messageReactions2 != null && tL_messageReactions2.results != null && tL_messageReactions2.reactions_as_tags) {
                for (int i3 = 0; i3 < tL_messageReactions2.results.size(); i3++) {
                    ReactionsLayoutInBubble.VisibleReaction visibleReactionFromTL2 = ReactionsLayoutInBubble.VisibleReaction.fromTL(((TLRPC.ReactionCount) tL_messageReactions2.results.get(i3)).reaction);
                    if (visibleReactionFromTL2 != null) {
                        longSparseArray2.put(visibleReactionFromTL2.hash, visibleReactionFromTL2);
                    }
                }
            }
            for (int i4 = 0; i4 < longSparseArray.size(); i4++) {
                long jKeyAt = longSparseArray.keyAt(i4);
                ReactionsLayoutInBubble.VisibleReaction visibleReaction = (ReactionsLayoutInBubble.VisibleReaction) longSparseArray.valueAt(i4);
                if (!longSparseArray2.containsKey(jKeyAt) && getMessagesController().updateSavedReactionTags(savedReactionsUpdate.topic_id, visibleReaction, false, false)) {
                    hashSet.add(Long.valueOf(savedReactionsUpdate.topic_id));
                    z = true;
                }
            }
            for (int i5 = 0; i5 < longSparseArray2.size(); i5++) {
                long jKeyAt2 = longSparseArray2.keyAt(i5);
                ReactionsLayoutInBubble.VisibleReaction visibleReaction2 = (ReactionsLayoutInBubble.VisibleReaction) longSparseArray2.valueAt(i5);
                if (!longSparseArray.containsKey(jKeyAt2) && getMessagesController().updateSavedReactionTags(savedReactionsUpdate.topic_id, visibleReaction2, true, false)) {
                    hashSet.add(Long.valueOf(savedReactionsUpdate.topic_id));
                    z = true;
                }
            }
        }
        if (!z || hashSet.isEmpty()) {
            return;
        }
        getMessagesController().updateSavedReactionTags(hashSet);
    }

    private Pair<Map<Long, TLRPC.MessagePeerReaction>, Map<Long, TLRPC.MessagePeerReaction>> getChangedReactionUserIds(ArrayList<TLRPC.MessagePeerReaction> arrayList, ArrayList<TLRPC.MessagePeerReaction> arrayList2) {
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            TLRPC.MessagePeerReaction messagePeerReaction = arrayList.get(i2);
            i2++;
            TLRPC.MessagePeerReaction messagePeerReaction2 = messagePeerReaction;
            long j = messagePeerReaction2.peer_id.user_id;
            if (j != 0) {
                map.put(Long.valueOf(j), messagePeerReaction2);
            }
        }
        int size2 = arrayList2.size();
        while (i < size2) {
            TLRPC.MessagePeerReaction messagePeerReaction3 = arrayList2.get(i);
            i++;
            TLRPC.MessagePeerReaction messagePeerReaction4 = messagePeerReaction3;
            long j2 = messagePeerReaction4.peer_id.user_id;
            if (j2 != 0) {
                map2.put(Long.valueOf(j2), messagePeerReaction4);
            }
        }
        HashMap map3 = new HashMap();
        HashMap map4 = new HashMap();
        for (Map.Entry entry : map.entrySet()) {
            if (!map2.containsKey(entry.getKey())) {
                map4.put((Long) entry.getKey(), (TLRPC.MessagePeerReaction) entry.getValue());
            }
        }
        for (Map.Entry entry2 : map2.entrySet()) {
            TLRPC.MessagePeerReaction messagePeerReaction5 = (TLRPC.MessagePeerReaction) entry2.getValue();
            TLRPC.MessagePeerReaction messagePeerReaction6 = (TLRPC.MessagePeerReaction) map.get(entry2.getKey());
            if (!map.containsKey(entry2.getKey()) || (messagePeerReaction5 != null && messagePeerReaction6 != null && messagePeerReaction5.date != messagePeerReaction6.date)) {
                map3.put((Long) entry2.getKey(), (TLRPC.MessagePeerReaction) entry2.getValue());
            }
        }
        return new Pair<>(map3, map4);
    }

    private void onReactionsUpdate(final long j, final TLRPC.TL_messageReactions tL_messageReactions, final TLRPC.TL_messageReactions tL_messageReactions2) {
        ArrayList arrayList;
        if (tL_messageReactions == null || (arrayList = tL_messageReactions.results) == null) {
            return;
        }
        if (arrayList == null || !arrayList.isEmpty() || tL_messageReactions2 == null || !tL_messageReactions2.results.isEmpty()) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda112
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onReactionsUpdate$101(tL_messageReactions, tL_messageReactions2, j);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onReactionsUpdate$101(TLRPC.TL_messageReactions tL_messageReactions, TLRPC.TL_messageReactions tL_messageReactions2, long j) {
        LongSparseArray longSparseArray = new LongSparseArray();
        LongSparseArray longSparseArray2 = new LongSparseArray();
        if (tL_messageReactions != null && tL_messageReactions.results != null && tL_messageReactions.reactions_as_tags) {
            for (int i = 0; i < tL_messageReactions.results.size(); i++) {
                ReactionsLayoutInBubble.VisibleReaction visibleReactionFromTL = ReactionsLayoutInBubble.VisibleReaction.fromTL(((TLRPC.ReactionCount) tL_messageReactions.results.get(i)).reaction);
                longSparseArray.put(visibleReactionFromTL.hash, visibleReactionFromTL);
            }
        }
        if (tL_messageReactions2 != null && tL_messageReactions2.results != null && tL_messageReactions2.reactions_as_tags) {
            for (int i2 = 0; i2 < tL_messageReactions2.results.size(); i2++) {
                ReactionsLayoutInBubble.VisibleReaction visibleReactionFromTL2 = ReactionsLayoutInBubble.VisibleReaction.fromTL(((TLRPC.ReactionCount) tL_messageReactions2.results.get(i2)).reaction);
                longSparseArray2.put(visibleReactionFromTL2.hash, visibleReactionFromTL2);
            }
        }
        boolean z = false;
        for (int i3 = 0; i3 < longSparseArray.size(); i3++) {
            long jKeyAt = longSparseArray.keyAt(i3);
            ReactionsLayoutInBubble.VisibleReaction visibleReaction = (ReactionsLayoutInBubble.VisibleReaction) longSparseArray.valueAt(i3);
            if (!longSparseArray2.containsKey(jKeyAt)) {
                z = getMessagesController().updateSavedReactionTags(j, visibleReaction, false, false) || z;
            }
        }
        for (int i4 = 0; i4 < longSparseArray2.size(); i4++) {
            long jKeyAt2 = longSparseArray2.keyAt(i4);
            ReactionsLayoutInBubble.VisibleReaction visibleReaction2 = (ReactionsLayoutInBubble.VisibleReaction) longSparseArray2.valueAt(i4);
            if (!longSparseArray.containsKey(jKeyAt2)) {
                z = getMessagesController().updateSavedReactionTags(j, visibleReaction2, true, false) || z;
            }
        }
        if (z) {
            if (j != 0) {
                getMessagesController().updateSavedReactionTags(0L);
            }
            getMessagesController().updateSavedReactionTags(j);
        }
    }

    private void bindMessageTags(SQLitePreparedStatement sQLitePreparedStatement, TLRPC.Message message) {
        ArrayList arrayList;
        long jHashCode;
        long clientUserId = getUserConfig().getClientUserId();
        TLRPC.TL_messageReactions tL_messageReactions = message.reactions;
        if (tL_messageReactions == null || !tL_messageReactions.reactions_as_tags || (arrayList = tL_messageReactions.results) == null || arrayList.isEmpty()) {
            return;
        }
        LocaleController localeController = LocaleController.getInstance();
        String str = message.message;
        if (str == null) {
            str = "";
        }
        String translitString = localeController.getTranslitString(str);
        ArrayList arrayList2 = message.reactions.results;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            TLRPC.ReactionCount reactionCount = (TLRPC.ReactionCount) obj;
            TLRPC.Reaction reaction = reactionCount.reaction;
            if ((reaction instanceof TLRPC.TL_reactionEmoji) || (reaction instanceof TLRPC.TL_reactionCustomEmoji)) {
                sQLitePreparedStatement.requery();
                sQLitePreparedStatement.bindLong(1, message.f1597id);
                sQLitePreparedStatement.bindLong(2, MessageObject.getSavedDialogId(clientUserId, message));
                TLRPC.Reaction reaction2 = reactionCount.reaction;
                if (reaction2 instanceof TLRPC.TL_reactionEmoji) {
                    jHashCode = ((TLRPC.TL_reactionEmoji) reaction2).emoticon.hashCode();
                } else {
                    jHashCode = reaction2 instanceof TLRPC.TL_reactionCustomEmoji ? ((TLRPC.TL_reactionCustomEmoji) reaction2).document_id : 0L;
                }
                sQLitePreparedStatement.bindLong(3, jHashCode);
                sQLitePreparedStatement.bindString(4, translitString == null ? "" : translitString);
                sQLitePreparedStatement.step();
            }
        }
    }

    public void updateMessageVoiceTranscriptionOpen(final long j, final int i, final TLRPC.Message message) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda131
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateMessageVoiceTranscriptionOpen$102(i, j, message);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageVoiceTranscriptionOpen$102(int i, long j, TLRPC.Message message) throws Throwable {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.beginTransaction();
                TLRPC.Message messageWithCustomParamsOnlyInternal = getMessageWithCustomParamsOnlyInternal(i, j);
                messageWithCustomParamsOnlyInternal.voiceTranscriptionOpen = message.voiceTranscriptionOpen;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionRated = message.voiceTranscriptionRated;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionFinal = message.voiceTranscriptionFinal;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionForce = message.voiceTranscriptionForce;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionId = message.voiceTranscriptionId;
                for (int i2 = 0; i2 < 2; i2++) {
                    if (i2 == 0) {
                        sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE messages_v2 SET custom_params = ? WHERE mid = ? AND uid = ?");
                    } else {
                        sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE messages_topics SET custom_params = ? WHERE mid = ? AND uid = ?");
                    }
                    try {
                        sQLitePreparedStatementExecuteFast.requery();
                        NativeByteBuffer nativeByteBufferWriteLocalParams = MessageCustomParamsHelper.writeLocalParams(messageWithCustomParamsOnlyInternal);
                        if (nativeByteBufferWriteLocalParams != null) {
                            sQLitePreparedStatementExecuteFast.bindByteBuffer(1, nativeByteBufferWriteLocalParams);
                        } else {
                            sQLitePreparedStatementExecuteFast.bindNull(1);
                        }
                        sQLitePreparedStatementExecuteFast.bindInteger(2, i);
                        sQLitePreparedStatementExecuteFast.bindLong(3, j);
                        sQLitePreparedStatementExecuteFast.step();
                        sQLitePreparedStatementExecuteFast.dispose();
                        if (nativeByteBufferWriteLocalParams != null) {
                            nativeByteBufferWriteLocalParams.reuse();
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        checkSQLException(e);
                        SQLiteDatabase sQLiteDatabase = this.database;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                }
                this.database.commitTransaction();
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.commitTransaction();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void updateMessageVoiceTranscription(final long j, final int i, final String str, final long j2, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda105
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateMessageVoiceTranscription$103(i, j, z, j2, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageVoiceTranscription$103(int i, long j, boolean z, long j2, String str) throws Throwable {
        TLRPC.Message messageWithCustomParamsOnlyInternal;
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.beginTransaction();
                messageWithCustomParamsOnlyInternal = getMessageWithCustomParamsOnlyInternal(i, j);
                messageWithCustomParamsOnlyInternal.voiceTranscriptionFinal = z;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionId = j2;
                messageWithCustomParamsOnlyInternal.voiceTranscription = str;
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE messages_v2 SET custom_params = ? WHERE mid = ? AND uid = ?");
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            sQLitePreparedStatementExecuteFast.requery();
            NativeByteBuffer nativeByteBufferWriteLocalParams = MessageCustomParamsHelper.writeLocalParams(messageWithCustomParamsOnlyInternal);
            if (nativeByteBufferWriteLocalParams != null) {
                sQLitePreparedStatementExecuteFast.bindByteBuffer(1, nativeByteBufferWriteLocalParams);
            } else {
                sQLitePreparedStatementExecuteFast.bindNull(1);
            }
            sQLitePreparedStatementExecuteFast.bindInteger(2, i);
            sQLitePreparedStatementExecuteFast.bindLong(3, j);
            sQLitePreparedStatementExecuteFast.step();
            sQLitePreparedStatementExecuteFast.dispose();
            this.database.commitTransaction();
            if (nativeByteBufferWriteLocalParams != null) {
                nativeByteBufferWriteLocalParams.reuse();
            }
            SQLiteDatabase sQLiteDatabase = this.database;
            if (sQLiteDatabase != null) {
                sQLiteDatabase.commitTransaction();
            }
        } catch (Exception e2) {
            e = e2;
            sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
            checkSQLException(e);
            SQLiteDatabase sQLiteDatabase2 = this.database;
            if (sQLiteDatabase2 != null) {
                sQLiteDatabase2.commitTransaction();
            }
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
        } catch (Throwable th2) {
            th = th2;
            sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
            SQLiteDatabase sQLiteDatabase3 = this.database;
            if (sQLiteDatabase3 != null) {
                sQLiteDatabase3.commitTransaction();
            }
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    public void updateMessageVoiceTranscription(final long j, final int i, final String str, final TLRPC.Message message) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda230
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateMessageVoiceTranscription$104(i, j, message, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageVoiceTranscription$104(int i, long j, TLRPC.Message message, String str) throws Throwable {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.beginTransaction();
                TLRPC.Message messageWithCustomParamsOnlyInternal = getMessageWithCustomParamsOnlyInternal(i, j);
                messageWithCustomParamsOnlyInternal.voiceTranscriptionOpen = message.voiceTranscriptionOpen;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionRated = message.voiceTranscriptionRated;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionFinal = message.voiceTranscriptionFinal;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionForce = message.voiceTranscriptionForce;
                messageWithCustomParamsOnlyInternal.voiceTranscriptionId = message.voiceTranscriptionId;
                messageWithCustomParamsOnlyInternal.voiceTranscription = str;
                for (int i2 = 0; i2 < 2; i2++) {
                    if (i2 == 0) {
                        sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE messages_v2 SET custom_params = ? WHERE mid = ? AND uid = ?");
                    } else {
                        sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE messages_topics SET custom_params = ? WHERE mid = ? AND uid = ?");
                    }
                    try {
                        sQLitePreparedStatementExecuteFast.requery();
                        NativeByteBuffer nativeByteBufferWriteLocalParams = MessageCustomParamsHelper.writeLocalParams(messageWithCustomParamsOnlyInternal);
                        if (nativeByteBufferWriteLocalParams != null) {
                            sQLitePreparedStatementExecuteFast.bindByteBuffer(1, nativeByteBufferWriteLocalParams);
                        } else {
                            sQLitePreparedStatementExecuteFast.bindNull(1);
                        }
                        sQLitePreparedStatementExecuteFast.bindInteger(2, i);
                        sQLitePreparedStatementExecuteFast.bindLong(3, j);
                        sQLitePreparedStatementExecuteFast.step();
                        sQLitePreparedStatementExecuteFast.dispose();
                        this.database.commitTransaction();
                        if (nativeByteBufferWriteLocalParams != null) {
                            nativeByteBufferWriteLocalParams.reuse();
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        checkSQLException(e);
                        SQLiteDatabase sQLiteDatabase = this.database;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                }
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.commitTransaction();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void updateMessageCustomParams(final long j, final TLRPC.Message message) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda88
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateMessageCustomParams$105(message, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageCustomParams$105(TLRPC.Message message, long j) throws Throwable {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.beginTransaction();
                TLRPC.Message messageWithCustomParamsOnlyInternal = getMessageWithCustomParamsOnlyInternal(message.f1597id, j);
                MessageCustomParamsHelper.copyParams(message, messageWithCustomParamsOnlyInternal);
                for (int i = 0; i < 2; i++) {
                    if (i == 0) {
                        sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE messages_v2 SET custom_params = ? WHERE mid = ? AND uid = ?");
                    } else {
                        sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE messages_topics SET custom_params = ? WHERE mid = ? AND uid = ?");
                    }
                    try {
                        sQLitePreparedStatementExecuteFast.requery();
                        NativeByteBuffer nativeByteBufferWriteLocalParams = MessageCustomParamsHelper.writeLocalParams(messageWithCustomParamsOnlyInternal);
                        if (nativeByteBufferWriteLocalParams != null) {
                            sQLitePreparedStatementExecuteFast.bindByteBuffer(1, nativeByteBufferWriteLocalParams);
                        } else {
                            sQLitePreparedStatementExecuteFast.bindNull(1);
                        }
                        sQLitePreparedStatementExecuteFast.bindInteger(2, message.f1597id);
                        sQLitePreparedStatementExecuteFast.bindLong(3, j);
                        sQLitePreparedStatementExecuteFast.step();
                        sQLitePreparedStatementExecuteFast.dispose();
                        if (nativeByteBufferWriteLocalParams != null) {
                            nativeByteBufferWriteLocalParams.reuse();
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        checkSQLException(e);
                        SQLiteDatabase sQLiteDatabase = this.database;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                }
                this.database.commitTransaction();
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.commitTransaction();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public TLRPC.Message getMessageWithCustomParamsOnlyInternal(int i, long j) throws Throwable {
        SQLiteCursor sQLiteCursorQueryFinalized;
        boolean z;
        TLRPC.TL_message tL_message = new TLRPC.TL_message();
        SQLiteCursor sQLiteCursorQueryFinalized2 = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT custom_params FROM messages_v2 WHERE mid = ? AND uid = ?", Integer.valueOf(i), Long.valueOf(j));
            } catch (SQLiteException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            if (sQLiteCursorQueryFinalized.next()) {
                MessageCustomParamsHelper.readLocalParams(tL_message, sQLiteCursorQueryFinalized.byteBufferValue(0));
                z = true;
            } else {
                z = false;
            }
            sQLiteCursorQueryFinalized.dispose();
            if (!z) {
                sQLiteCursorQueryFinalized2 = this.database.queryFinalized("SELECT custom_params FROM messages_topics WHERE mid = ? AND uid = ?", Integer.valueOf(i), Long.valueOf(j));
                if (sQLiteCursorQueryFinalized2.next()) {
                    MessageCustomParamsHelper.readLocalParams(tL_message, sQLiteCursorQueryFinalized2.byteBufferValue(0));
                }
                sQLiteCursorQueryFinalized2.dispose();
                return tL_message;
            }
        } catch (SQLiteException e2) {
            e = e2;
            sQLiteCursorQueryFinalized2 = sQLiteCursorQueryFinalized;
            checkSQLException(e);
            if (sQLiteCursorQueryFinalized2 != null) {
                sQLiteCursorQueryFinalized2.dispose();
            }
            return tL_message;
        } catch (Throwable th2) {
            th = th2;
            sQLiteCursorQueryFinalized2 = sQLiteCursorQueryFinalized;
            if (sQLiteCursorQueryFinalized2 != null) {
                sQLiteCursorQueryFinalized2.dispose();
            }
            throw th;
        }
        return tL_message;
    }

    public void getNewTask(final LongSparseArray longSparseArray, final LongSparseArray longSparseArray2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda82
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$getNewTask$106(longSparseArray, longSparseArray2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00ac A[Catch: all -> 0x00b2, Exception -> 0x00b6, TryCatch #4 {Exception -> 0x00b6, all -> 0x00b2, blocks: (B:17:0x008b, B:19:0x0091, B:25:0x00ac, B:34:0x00c5, B:36:0x00cd, B:37:0x00d5, B:32:0x00be, B:38:0x00df), top: B:51:0x008b }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00be A[Catch: all -> 0x00b2, Exception -> 0x00b6, TryCatch #4 {Exception -> 0x00b6, all -> 0x00b2, blocks: (B:17:0x008b, B:19:0x0091, B:25:0x00ac, B:34:0x00c5, B:36:0x00cd, B:37:0x00d5, B:32:0x00be, B:38:0x00df), top: B:51:0x008b }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:58:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$getNewTask$106(androidx.collection.LongSparseArray r14, androidx.collection.LongSparseArray r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 249
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getNewTask$106(androidx.collection.LongSparseArray, androidx.collection.LongSparseArray):void");
    }

    public void markMentionMessageAsRead(final long j, final int i, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda68
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$markMentionMessageAsRead$107(i, j, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMentionMessageAsRead$107(int i, long j, long j2) throws Throwable {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                SQLiteDatabase sQLiteDatabase = this.database;
                Locale locale = Locale.US;
                sQLiteDatabase.executeFast(String.format(locale, "UPDATE messages_v2 SET read_state = read_state | 2 WHERE mid = %d AND uid = %d", Integer.valueOf(i), Long.valueOf(j))).stepThis().dispose();
                SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT unread_count_i FROM dialogs WHERE did = " + j2, new Object[0]);
                try {
                    int iMax = sQLiteCursorQueryFinalized.next() ? Math.max(0, sQLiteCursorQueryFinalized.intValue(0) - 1) : 0;
                    sQLiteCursorQueryFinalized.dispose();
                    this.database.executeFast(String.format(locale, "UPDATE dialogs SET unread_count_i = %d WHERE did = %d", Integer.valueOf(iMax), Long.valueOf(j2))).stepThis().dispose();
                    LongSparseIntArray longSparseIntArray = new LongSparseIntArray(1);
                    longSparseIntArray.put(j2, iMax);
                    if (iMax == 0) {
                        updateFiltersReadCounter(null, longSparseIntArray, true);
                    }
                    getMessagesController().processDialogsUpdateRead(null, longSparseIntArray);
                    this.database.executeFast(String.format(locale, "UPDATE messages_topics SET read_state = read_state | 2 WHERE mid = %d AND uid = %d", Integer.valueOf(i), Long.valueOf(j))).stepThis().dispose();
                    SQLiteCursor sQLiteCursorQueryFinalized2 = this.database.queryFinalized(String.format(locale, "SELECT data FROM messages_topics WHERE mid = %d AND uid = %d", Integer.valueOf(i), Long.valueOf(j)), new Object[0]);
                    long topicId = 0;
                    while (sQLiteCursorQueryFinalized2.next()) {
                        try {
                            NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized2.byteBufferValue(0);
                            if (nativeByteBufferByteBufferValue != null) {
                                TLRPC.Message messageTLdeserialize = TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                                nativeByteBufferByteBufferValue.reuse();
                                topicId = MessageObject.getTopicId(this.currentAccount, messageTLdeserialize, getForumTypeFlags(j));
                            }
                        } catch (Exception e) {
                            e = e;
                            sQLiteCursor = sQLiteCursorQueryFinalized2;
                        } catch (Throwable th) {
                            th = th;
                            sQLiteCursor = sQLiteCursorQueryFinalized2;
                        }
                    }
                    sQLiteCursorQueryFinalized2.dispose();
                    if (topicId != 0) {
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        Locale locale2 = Locale.US;
                        SQLiteCursor sQLiteCursorQueryFinalized3 = sQLiteDatabase2.queryFinalized(String.format(locale2, "SELECT unread_mentions FROM topics WHERE did = %d AND topic_id = %d", Long.valueOf(j2), Long.valueOf(topicId)), new Object[0]);
                        try {
                            int iMax2 = sQLiteCursorQueryFinalized3.next() ? Math.max(0, sQLiteCursorQueryFinalized3.intValue(0) - 1) : 0;
                            sQLiteCursorQueryFinalized3.dispose();
                            this.database.executeFast(String.format(locale2, "UPDATE topics SET unread_mentions = %d WHERE did = %d AND topic_id = %d", Integer.valueOf(iMax2), Long.valueOf(j), Long.valueOf(topicId))).stepThis().dispose();
                            getMessagesController().getTopicsController().updateMentionsUnread(j, topicId, iMax2);
                        } catch (Exception e2) {
                            e = e2;
                            sQLiteCursor = sQLiteCursorQueryFinalized3;
                            checkSQLException(e);
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            sQLiteCursor = sQLiteCursorQueryFinalized3;
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                            }
                            throw th;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    sQLiteCursor = sQLiteCursorQueryFinalized;
                } catch (Throwable th3) {
                    th = th3;
                    sQLiteCursor = sQLiteCursorQueryFinalized;
                }
            } catch (Exception e4) {
                e = e4;
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public void markMessageAsMention(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda157
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$markMessageAsMention$108(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessageAsMention$108(int i, long j) {
        try {
            this.database.executeFast(String.format(Locale.US, "UPDATE messages_v2 SET mention = 1, read_state = read_state & ~2 WHERE mid = %d AND uid = %d", Integer.valueOf(i), Long.valueOf(j))).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void resetMentionsCount(final long j, final long j2, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda80
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$resetMentionsCount$109(j2, j, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetMentionsCount$109(long j, long j2, int i) throws Throwable {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                if (j == 0) {
                    SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT unread_count_i FROM dialogs WHERE did = " + j2, new Object[0]);
                    try {
                        int iIntValue = sQLiteCursorQueryFinalized.next() ? sQLiteCursorQueryFinalized.intValue(0) : 0;
                        sQLiteCursorQueryFinalized.dispose();
                        if (iIntValue == 0 && i == 0) {
                            return;
                        }
                        if (i == 0) {
                            this.database.executeFast(String.format(Locale.US, "UPDATE messages_v2 SET read_state = read_state | 2 WHERE uid = %d AND mention = 1 AND read_state IN(0, 1)", Long.valueOf(j2))).stepThis().dispose();
                        }
                        this.database.executeFast(String.format(Locale.US, "UPDATE dialogs SET unread_count_i = %d WHERE did = %d", Integer.valueOf(i), Long.valueOf(j2))).stepThis().dispose();
                        LongSparseIntArray longSparseIntArray = new LongSparseIntArray(1);
                        longSparseIntArray.put(j2, i);
                        getMessagesController().processDialogsUpdateRead(null, longSparseIntArray);
                        if (i == 0) {
                            updateFiltersReadCounter(null, longSparseIntArray, true);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e = e;
                        sQLiteCursor = sQLiteCursorQueryFinalized;
                        checkSQLException(e);
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLiteCursor = sQLiteCursorQueryFinalized;
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        throw th;
                    }
                }
                this.database.executeFast(String.format(Locale.US, "UPDATE topics SET unread_mentions = %d WHERE did = %d AND topic_id = %d", Integer.valueOf(i), Long.valueOf(j2), Long.valueOf(j))).stepThis().dispose();
                TopicsController.TopicUpdate topicUpdate = new TopicsController.TopicUpdate();
                topicUpdate.dialogId = j2;
                topicUpdate.topicId = j;
                topicUpdate.onlyCounters = true;
                topicUpdate.unreadMentions = i;
                topicUpdate.unreadCount = -1;
                getMessagesController().getTopicsController().processUpdate(Collections.singletonList(topicUpdate));
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void createTaskForMid(final long j, final int i, final int i2, final int i3, final int i4, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda245
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$createTaskForMid$111(i2, i3, i4, i, z, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createTaskForMid$111(int i, int i2, int i3, int i4, final boolean z, final long j) throws Throwable {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                int iMax = Math.max(i, i2) + i3;
                SparseArray<ArrayList<Integer>> sparseArray = new SparseArray<>();
                final ArrayList<Integer> arrayList = new ArrayList<>();
                arrayList.add(Integer.valueOf(i4));
                sparseArray.put(iMax, arrayList);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$createTaskForMid$110(z, j, arrayList);
                    }
                });
                SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO enc_tasks_v4 VALUES(?, ?, ?, ?)");
                for (int i5 = 0; i5 < sparseArray.size(); i5++) {
                    try {
                        int iKeyAt = sparseArray.keyAt(i5);
                        ArrayList<Integer> arrayList2 = sparseArray.get(iKeyAt);
                        for (int i6 = 0; i6 < arrayList2.size(); i6++) {
                            sQLitePreparedStatementExecuteFast.requery();
                            sQLitePreparedStatementExecuteFast.bindInteger(1, arrayList2.get(i6).intValue());
                            sQLitePreparedStatementExecuteFast.bindLong(2, j);
                            sQLitePreparedStatementExecuteFast.bindInteger(3, iKeyAt);
                            sQLitePreparedStatementExecuteFast.bindInteger(4, 1);
                            sQLitePreparedStatementExecuteFast.step();
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        checkSQLException(e);
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                }
                sQLitePreparedStatementExecuteFast.dispose();
                this.database.executeFast(String.format(Locale.US, "UPDATE messages_v2 SET ttl = 0 WHERE mid = %d AND uid = %d", Integer.valueOf(i4), Long.valueOf(j))).stepThis().dispose();
                getMessagesController().didAddedNewTask(iMax, j, sparseArray);
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createTaskForMid$110(boolean z, long j, ArrayList arrayList) {
        long j2;
        ArrayList arrayList2;
        if (z) {
            j2 = j;
            arrayList2 = arrayList;
        } else {
            j2 = j;
            arrayList2 = arrayList;
            markMessagesContentAsRead(j2, arrayList2, 0, 0);
        }
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messagesReadContent, Long.valueOf(j2), arrayList2);
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void createTaskForSecretMedia(long r17, android.util.SparseArray<java.util.ArrayList<java.lang.Integer>> r19) throws java.lang.Throwable {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r0 = r19
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            r5.<init>()     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            int r6 = r0.size()     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            if (r6 == 0) goto Lae
            org.telegram.SQLite.SQLiteDatabase r6 = r1.database     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            r6.beginTransaction()     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            org.telegram.SQLite.SQLiteDatabase r6 = r1.database     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            java.lang.String r7 = "REPLACE INTO enc_tasks_v4 VALUES(?, ?, ?, ?)"
            org.telegram.SQLite.SQLitePreparedStatement r6 = r6.executeFast(r7)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            r7 = 0
            r8 = 2147483647(0x7fffffff, float:NaN)
            r9 = 0
        L23:
            int r10 = r0.size()     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            r11 = 2
            r12 = 1
            if (r9 >= r10) goto L73
            int r10 = r0.keyAt(r9)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            java.lang.Object r13 = r0.get(r10)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            java.util.ArrayList r13 = (java.util.ArrayList) r13     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            r14 = 0
        L36:
            int r15 = r13.size()     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            if (r14 >= r15) goto L70
            java.lang.Object r15 = r13.get(r14)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            java.lang.Integer r15 = (java.lang.Integer) r15     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            int r15 = r15.intValue()     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            r6.requery()     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            r6.bindInteger(r12, r15)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            r6.bindLong(r11, r2)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            r4 = 3
            r6.bindInteger(r4, r10)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            r4 = 4
            r6.bindInteger(r4, r12)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            int r8 = java.lang.Math.min(r8, r15)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            r6.step()     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            java.lang.Object r4 = r13.get(r14)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            r5.add(r4)     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            int r14 = r14 + 1
            goto L36
        L6a:
            r0 = move-exception
            r4 = r6
            goto Lc7
        L6d:
            r0 = move-exception
            r4 = r6
            goto Lb6
        L70:
            int r9 = r9 + 1
            goto L23
        L73:
            r6.dispose()     // Catch: java.lang.Throwable -> L6a java.lang.Exception -> L6d
            org.telegram.SQLite.SQLiteDatabase r4 = r1.database     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            r4.commitTransaction()     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            org.telegram.SQLite.SQLiteDatabase r4 = r1.database     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            java.util.Locale r6 = java.util.Locale.US     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            java.lang.String r9 = "UPDATE messages_v2 SET ttl = 0 WHERE uid = %d AND mid IN(%s)"
            java.lang.Long r10 = java.lang.Long.valueOf(r2)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            java.lang.String r13 = ", "
            java.lang.String r5 = android.text.TextUtils.join(r13, r5)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            java.lang.Object[] r11 = new java.lang.Object[r11]     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            r11[r7] = r10     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            r11[r12] = r5     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            java.lang.String r5 = java.lang.String.format(r6, r9, r11)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            org.telegram.SQLite.SQLitePreparedStatement r4 = r4.executeFast(r5)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            org.telegram.SQLite.SQLitePreparedStatement r4 = r4.stepThis()     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            r4.dispose()     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            org.telegram.messenger.MessagesController r4 = r1.getMessagesController()     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            r4.didAddedNewTask(r8, r2, r0)     // Catch: java.lang.Throwable -> La8 java.lang.Exception -> Lab
            goto Lae
        La8:
            r0 = move-exception
            r4 = 0
            goto Lc7
        Lab:
            r0 = move-exception
            r4 = 0
            goto Lb6
        Lae:
            org.telegram.SQLite.SQLiteDatabase r0 = r1.database
            if (r0 == 0) goto Lc5
            r0.commitTransaction()
            return
        Lb6:
            r1.checkSQLException(r0)     // Catch: java.lang.Throwable -> Lc6
            org.telegram.SQLite.SQLiteDatabase r0 = r1.database
            if (r0 == 0) goto Lc0
            r0.commitTransaction()
        Lc0:
            if (r4 == 0) goto Lc5
            r4.dispose()
        Lc5:
            return
        Lc6:
            r0 = move-exception
        Lc7:
            org.telegram.SQLite.SQLiteDatabase r2 = r1.database
            if (r2 == 0) goto Lce
            r2.commitTransaction()
        Lce:
            if (r4 == 0) goto Ld3
            r4.dispose()
        Ld3:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.createTaskForSecretMedia(long, android.util.SparseArray):void");
    }

    public void createTaskForSecretChat(final int i, final int i2, final int i3, final int i4, final ArrayList<Long> arrayList) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda65
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$createTaskForSecretChat$113(i, arrayList, i4, i2, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:61:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:93:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$createTaskForSecretChat$113(int r20, java.util.ArrayList r21, int r22, int r23, int r24) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 384
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$createTaskForSecretChat$113(int, java.util.ArrayList, int, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createTaskForSecretChat$112(long j, ArrayList arrayList) {
        markMessagesContentAsRead(j, arrayList, 0, 0);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messagesReadContent, Long.valueOf(j), arrayList);
    }

    /* JADX WARN: Removed duplicated region for block: B:137:0x0339  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x03c7  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x03d3  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x03d8  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x040f  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x041b  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x041d  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x044c  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x04cf A[PHI: r9
      0x04cf: PHI (r9v111 int) = (r9v3 int), (r9v115 int) binds: [B:219:0x04b9, B:221:0x04c7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0505 A[PHI: r9
      0x0505: PHI (r9v105 int) = (r9v56 int), (r9v109 int) binds: [B:232:0x04ef, B:234:0x04fd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:283:0x05bd A[PHI: r9
      0x05bd: PHI (r9v89 int) = (r9v59 int), (r9v93 int) binds: [B:279:0x05a7, B:281:0x05b5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0621  */
    /* JADX WARN: Removed duplicated region for block: B:326:0x0656 A[PHI: r11
      0x0656: PHI (r11v47 int) = (r11v46 int), (r11v48 int) binds: [B:331:0x0666, B:325:0x0654] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:358:0x06c7  */
    /* JADX WARN: Removed duplicated region for block: B:384:0x0733  */
    /* JADX WARN: Removed duplicated region for block: B:458:0x0847 A[PHI: r9
      0x0847: PHI (r9v43 int) = (r9v5 int), (r9v47 int) binds: [B:454:0x0830, B:456:0x083f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:505:0x090e A[PHI: r9
      0x090e: PHI (r9v27 int) = (r9v8 int), (r9v31 int) binds: [B:501:0x08f7, B:503:0x0906] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:531:0x096f  */
    /* JADX WARN: Removed duplicated region for block: B:550:0x09d1 A[PHI: r16
      0x09d1: PHI (r16v13 int) = (r16v12 int), (r16v12 int), (r16v12 int), (r16v14 int) binds: [B:567:0x09f7, B:564:0x09f0, B:556:0x09e0, B:549:0x09cf] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:576:0x0a1f  */
    /* JADX WARN: Removed duplicated region for block: B:599:0x0a63  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void updateFiltersReadCounter(org.telegram.messenger.support.LongSparseIntArray r35, org.telegram.messenger.support.LongSparseIntArray r36, boolean r37) {
        /*
            Method dump skipped, instructions count: 2815
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.updateFiltersReadCounter(org.telegram.messenger.support.LongSparseIntArray, org.telegram.messenger.support.LongSparseIntArray, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateFiltersReadCounter$114() {
        ArrayList<MessagesController.DialogFilter> arrayList = getMessagesController().dialogFilters;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).unreadCount = arrayList.get(i).pendingUnreadCount;
        }
        this.mainUnreadCount = this.pendingMainUnreadCount;
        this.archiveUnreadCount = this.pendingArchiveUnreadCount;
    }

    private void updateDialogsWithReadMessagesInternal(ArrayList<Integer> arrayList, LongSparseIntArray longSparseIntArray, LongSparseIntArray longSparseIntArray2, LongSparseArray longSparseArray, LongSparseIntArray longSparseIntArray3) throws Throwable {
        boolean z;
        int i;
        int i2;
        int i3;
        LongSparseIntArray longSparseIntArray4 = longSparseIntArray;
        try {
            LongSparseIntArray longSparseIntArray5 = new LongSparseIntArray();
            LongSparseIntArray longSparseIntArray6 = new LongSparseIntArray();
            ArrayList<Long> arrayList2 = new ArrayList<>();
            int i4 = 2;
            if (!isEmpty(arrayList)) {
                SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT uid, read_state, out FROM messages_v2 WHERE mid IN(%s) AND is_channel = 0", TextUtils.join(",", arrayList)), new Object[0]);
                while (sQLiteCursorQueryFinalized.next()) {
                    if (sQLiteCursorQueryFinalized.intValue(2) == 0 && sQLiteCursorQueryFinalized.intValue(1) == 0) {
                        long jLongValue = sQLiteCursorQueryFinalized.longValue(0);
                        int i5 = longSparseIntArray5.get(jLongValue);
                        if (i5 == 0) {
                            longSparseIntArray5.put(jLongValue, 1);
                        } else {
                            longSparseIntArray5.put(jLongValue, i5 + 1);
                        }
                    }
                }
                sQLiteCursorQueryFinalized.dispose();
            } else {
                if (!isEmpty(longSparseIntArray4)) {
                    int i6 = 0;
                    while (i6 < longSparseIntArray4.size()) {
                        long jKeyAt = longSparseIntArray4.keyAt(i6);
                        int i7 = longSparseIntArray4.get(jKeyAt);
                        int i8 = longSparseIntArray3 == null ? -2 : longSparseIntArray3.get(jKeyAt, -2);
                        if (i8 >= 0) {
                            longSparseIntArray5.put(jKeyAt, i8);
                            if (BuildVars.DEBUG_VERSION) {
                                FileLog.m1157d(jKeyAt + " update unread messages count by still unread " + i8);
                            }
                            i = i6;
                        } else {
                            if (longSparseIntArray3 == null || i8 == -2) {
                                z = true;
                            } else {
                                SQLiteDatabase sQLiteDatabase = this.database;
                                Locale locale = Locale.US;
                                Long lValueOf = Long.valueOf(jKeyAt);
                                Integer numValueOf = Integer.valueOf(i7);
                                Object[] objArr = new Object[i4];
                                objArr[0] = lValueOf;
                                objArr[1] = numValueOf;
                                SQLiteCursor sQLiteCursorQueryFinalized2 = sQLiteDatabase.queryFinalized(String.format(locale, "SELECT start, end FROM messages_holes WHERE uid = %d AND end > %d", objArr), new Object[0]);
                                z = true;
                                while (sQLiteCursorQueryFinalized2.next()) {
                                    z = false;
                                }
                                sQLiteCursorQueryFinalized2.dispose();
                            }
                            if (z) {
                                SQLiteDatabase sQLiteDatabase2 = this.database;
                                Locale locale2 = Locale.US;
                                Long lValueOf2 = Long.valueOf(jKeyAt);
                                Integer numValueOf2 = Integer.valueOf(i7);
                                i = i6;
                                Object[] objArr2 = new Object[i4];
                                objArr2[0] = lValueOf2;
                                objArr2[1] = numValueOf2;
                                SQLiteCursor sQLiteCursorQueryFinalized3 = sQLiteDatabase2.queryFinalized(String.format(locale2, "SELECT COUNT(mid) FROM messages_v2 WHERE uid = %d AND mid > %d AND read_state IN(0,2) AND out = 0", objArr2), new Object[0]);
                                if (sQLiteCursorQueryFinalized3.next()) {
                                    int iIntValue = sQLiteCursorQueryFinalized3.intValue(0);
                                    longSparseIntArray5.put(jKeyAt, iIntValue);
                                    if (BuildVars.DEBUG_VERSION) {
                                        FileLog.m1157d(jKeyAt + " update unread messages count " + iIntValue);
                                    }
                                } else if (BuildVars.DEBUG_VERSION) {
                                    FileLog.m1157d(jKeyAt + " can't update unread messages count cursor trouble");
                                }
                                sQLiteCursorQueryFinalized3.dispose();
                            } else {
                                i = i6;
                                if (BuildVars.DEBUG_VERSION) {
                                    FileLog.m1157d(jKeyAt + " can't update unread messages count");
                                }
                            }
                        }
                        SQLiteCursor sQLiteCursorQueryFinalized4 = this.database.queryFinalized("SELECT inbox_max FROM dialogs WHERE did = " + jKeyAt, new Object[0]);
                        int iIntValue2 = sQLiteCursorQueryFinalized4.next() ? sQLiteCursorQueryFinalized4.intValue(0) : 0;
                        sQLiteCursorQueryFinalized4.dispose();
                        FileLog.m1157d(jKeyAt + " set inbox max " + i7);
                        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE dialogs SET inbox_max = max((SELECT inbox_max FROM dialogs WHERE did = ?), ?) WHERE did = ?");
                        sQLitePreparedStatementExecuteFast.requery();
                        sQLitePreparedStatementExecuteFast.bindLong(1, jKeyAt);
                        sQLitePreparedStatementExecuteFast.bindInteger(2, i7);
                        sQLitePreparedStatementExecuteFast.bindLong(3, jKeyAt);
                        sQLitePreparedStatementExecuteFast.step();
                        sQLitePreparedStatementExecuteFast.dispose();
                        if (isForum(jKeyAt, 14)) {
                            updateTopicsWithReadFromAllInternal(jKeyAt, iIntValue2, i7);
                        }
                        i6 = i + 1;
                        longSparseIntArray4 = longSparseIntArray;
                        i4 = 2;
                    }
                }
                if (!isEmpty(longSparseArray)) {
                    int size = longSparseArray.size();
                    int i9 = 0;
                    while (i9 < size) {
                        ArrayList arrayList3 = (ArrayList) longSparseArray.valueAt(i9);
                        ArrayList arrayList4 = new ArrayList(arrayList3);
                        int i10 = 0;
                        SQLiteCursor sQLiteCursorQueryFinalized5 = this.database.queryFinalized(String.format(Locale.US, "SELECT uid, read_state, out, mention, mid, is_channel FROM messages_v2 WHERE mid IN(%s)", TextUtils.join(",", arrayList3)), new Object[0]);
                        long jLongValue2 = 0;
                        while (sQLiteCursorQueryFinalized5.next()) {
                            int i11 = size;
                            int i12 = i9;
                            long jLongValue3 = sQLiteCursorQueryFinalized5.longValue(i10);
                            arrayList4.remove(Integer.valueOf(sQLiteCursorQueryFinalized5.intValue(4)));
                            if (sQLiteCursorQueryFinalized5.intValue(1) < 2 && sQLiteCursorQueryFinalized5.intValue(2) == 0 && sQLiteCursorQueryFinalized5.intValue(3) == 1) {
                                int i13 = longSparseIntArray6.get(jLongValue3, -1);
                                if (i13 < 0) {
                                    SQLiteCursor sQLiteCursorQueryFinalized6 = this.database.queryFinalized("SELECT unread_count_i FROM dialogs WHERE did = " + jLongValue3, new Object[0]);
                                    int iIntValue3 = sQLiteCursorQueryFinalized6.next() ? sQLiteCursorQueryFinalized6.intValue(0) : 0;
                                    sQLiteCursorQueryFinalized6.dispose();
                                    longSparseIntArray6.put(jLongValue3, Math.max(0, iIntValue3 - 1));
                                } else {
                                    longSparseIntArray6.put(jLongValue3, Math.max(0, i13 - 1));
                                }
                            }
                            jLongValue2 = sQLiteCursorQueryFinalized5.longValue(5);
                            size = i11;
                            i9 = i12;
                            i10 = 0;
                        }
                        int i14 = size;
                        int i15 = i9;
                        sQLiteCursorQueryFinalized5.dispose();
                        if (!arrayList4.isEmpty() && jLongValue2 != 0 && !arrayList2.contains(Long.valueOf(jLongValue2))) {
                            arrayList2.add(Long.valueOf(jLongValue2));
                        }
                        i9 = i15 + 1;
                        size = i14;
                    }
                }
                if (!isEmpty(longSparseIntArray2)) {
                    for (int i16 = 0; i16 < longSparseIntArray2.size(); i16++) {
                        long jKeyAt2 = longSparseIntArray2.keyAt(i16);
                        int i17 = longSparseIntArray2.get(jKeyAt2);
                        SQLitePreparedStatement sQLitePreparedStatementExecuteFast2 = this.database.executeFast("UPDATE dialogs SET outbox_max = max((SELECT outbox_max FROM dialogs WHERE did = ?), ?) WHERE did = ?");
                        sQLitePreparedStatementExecuteFast2.requery();
                        sQLitePreparedStatementExecuteFast2.bindLong(1, jKeyAt2);
                        sQLitePreparedStatementExecuteFast2.bindInteger(2, i17);
                        sQLitePreparedStatementExecuteFast2.bindLong(3, jKeyAt2);
                        sQLitePreparedStatementExecuteFast2.step();
                        sQLitePreparedStatementExecuteFast2.dispose();
                    }
                }
            }
            if (longSparseIntArray5.size() > 0 || longSparseIntArray6.size() > 0) {
                this.database.beginTransaction();
                if (longSparseIntArray5.size() > 0) {
                    ArrayList<Long> arrayList5 = new ArrayList<>();
                    SQLitePreparedStatement sQLitePreparedStatementExecuteFast3 = this.database.executeFast("UPDATE dialogs SET unread_count = ? WHERE did = ?");
                    int i18 = 0;
                    while (i18 < longSparseIntArray5.size()) {
                        long jKeyAt3 = longSparseIntArray5.keyAt(i18);
                        if (isForum(jKeyAt3, 13)) {
                            longSparseIntArray5.removeAt(i18);
                            i18--;
                            i3 = 1;
                        } else {
                            int iValueAt = longSparseIntArray5.valueAt(i18);
                            SQLiteCursor sQLiteCursorQueryFinalized7 = this.database.queryFinalized("SELECT unread_count FROM dialogs WHERE did = " + jKeyAt3, new Object[0]);
                            int iIntValue4 = sQLiteCursorQueryFinalized7.next() ? sQLiteCursorQueryFinalized7.intValue(0) : 0;
                            sQLiteCursorQueryFinalized7.dispose();
                            if (iIntValue4 == iValueAt) {
                                longSparseIntArray5.removeAt(i18);
                                i18--;
                                i3 = 1;
                            } else {
                                sQLitePreparedStatementExecuteFast3.requery();
                                i3 = 1;
                                sQLitePreparedStatementExecuteFast3.bindInteger(1, iValueAt);
                                sQLitePreparedStatementExecuteFast3.bindLong(2, jKeyAt3);
                                sQLitePreparedStatementExecuteFast3.step();
                                arrayList5.add(Long.valueOf(jKeyAt3));
                            }
                        }
                        i18 += i3;
                    }
                    sQLitePreparedStatementExecuteFast3.dispose();
                    updateWidgets(arrayList5);
                }
                if (longSparseIntArray6.size() > 0) {
                    SQLitePreparedStatement sQLitePreparedStatementExecuteFast4 = this.database.executeFast("UPDATE dialogs SET unread_count_i = ? WHERE did = ?");
                    int i19 = 0;
                    while (i19 < longSparseIntArray6.size()) {
                        long jKeyAt4 = longSparseIntArray6.keyAt(i19);
                        if (isForum(jKeyAt4, 13)) {
                            longSparseIntArray6.removeAt(i19);
                            i19--;
                            i2 = 1;
                        } else {
                            sQLitePreparedStatementExecuteFast4.requery();
                            i2 = 1;
                            sQLitePreparedStatementExecuteFast4.bindInteger(1, longSparseIntArray6.valueAt(i19));
                            sQLitePreparedStatementExecuteFast4.bindLong(2, jKeyAt4);
                            sQLitePreparedStatementExecuteFast4.step();
                        }
                        i19 += i2;
                    }
                    sQLitePreparedStatementExecuteFast4.dispose();
                }
                this.database.commitTransaction();
            }
            updateFiltersReadCounter(longSparseIntArray5, longSparseIntArray6, true);
            getMessagesController().processDialogsUpdateRead(longSparseIntArray5, longSparseIntArray6);
            if (arrayList2.isEmpty()) {
                return;
            }
            getMessagesController().reloadMentionsCountForChannels(arrayList2);
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    private static boolean isEmpty(SparseArray<?> sparseArray) {
        return sparseArray == null || sparseArray.size() == 0;
    }

    private static boolean isEmpty(LongSparseIntArray longSparseIntArray) {
        return longSparseIntArray == null || longSparseIntArray.size() == 0;
    }

    private static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    private static boolean isEmpty(SparseIntArray sparseIntArray) {
        return sparseIntArray == null || sparseIntArray.size() == 0;
    }

    private static boolean isEmpty(LongSparseArray longSparseArray) {
        return longSparseArray == null || longSparseArray.size() == 0;
    }

    public void updateDialogsWithReadMessages(final LongSparseIntArray longSparseIntArray, final LongSparseIntArray longSparseIntArray2, final LongSparseArray longSparseArray, final LongSparseIntArray longSparseIntArray3, boolean z) {
        if (isEmpty(longSparseIntArray) && isEmpty(longSparseIntArray2) && isEmpty(longSparseArray) && isEmpty(longSparseIntArray3)) {
            return;
        }
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda183
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$updateDialogsWithReadMessages$115(longSparseIntArray, longSparseIntArray2, longSparseArray, longSparseIntArray3);
                }
            });
        } else {
            updateDialogsWithReadMessagesInternal(null, longSparseIntArray, longSparseIntArray2, longSparseArray, longSparseIntArray3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsWithReadMessages$115(LongSparseIntArray longSparseIntArray, LongSparseIntArray longSparseIntArray2, LongSparseArray longSparseArray, LongSparseIntArray longSparseIntArray3) throws Throwable {
        updateDialogsWithReadMessagesInternal(null, longSparseIntArray, longSparseIntArray2, longSparseArray, longSparseIntArray3);
    }

    public void updateChatParticipants(final TLRPC.ChatParticipants chatParticipants) {
        if (chatParticipants == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda202
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateChatParticipants$117(chatParticipants);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateChatParticipants$117(TLRPC.ChatParticipants chatParticipants) throws Throwable {
        SQLiteCursor sQLiteCursorQueryFinalized;
        final TLRPC.ChatFull chatFullTLdeserialize;
        NativeByteBuffer nativeByteBufferByteBufferValue;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT info, pinned, online, inviter FROM chat_settings_v2 WHERE uid = " + chatParticipants.chat_id, new Object[0]);
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            new ArrayList();
            if (!sQLiteCursorQueryFinalized.next() || (nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0)) == null) {
                chatFullTLdeserialize = null;
            } else {
                chatFullTLdeserialize = TLRPC.ChatFull.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                nativeByteBufferByteBufferValue.reuse();
                chatFullTLdeserialize.pinned_msg_id = sQLiteCursorQueryFinalized.intValue(1);
                chatFullTLdeserialize.online_count = sQLiteCursorQueryFinalized.intValue(2);
                chatFullTLdeserialize.inviterId = sQLiteCursorQueryFinalized.longValue(3);
            }
            sQLiteCursorQueryFinalized.dispose();
            if (chatFullTLdeserialize instanceof TLRPC.TL_chatFull) {
                chatFullTLdeserialize.participants = chatParticipants;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda177
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$updateChatParticipants$116(chatFullTLdeserialize);
                    }
                });
                SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?, ?, ?, ?, ?)");
                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(chatFullTLdeserialize.getObjectSize());
                chatFullTLdeserialize.serializeToStream(nativeByteBuffer);
                sQLitePreparedStatementExecuteFast.bindLong(1, chatFullTLdeserialize.f1572id);
                sQLitePreparedStatementExecuteFast.bindByteBuffer(2, nativeByteBuffer);
                sQLitePreparedStatementExecuteFast.bindInteger(3, chatFullTLdeserialize.pinned_msg_id);
                sQLitePreparedStatementExecuteFast.bindInteger(4, chatFullTLdeserialize.online_count);
                sQLitePreparedStatementExecuteFast.bindLong(5, chatFullTLdeserialize.inviterId);
                sQLitePreparedStatementExecuteFast.bindInteger(6, chatFullTLdeserialize.invitesCount);
                sQLitePreparedStatementExecuteFast.bindInteger(7, chatFullTLdeserialize.participants_count);
                sQLitePreparedStatementExecuteFast.step();
                sQLitePreparedStatementExecuteFast.dispose();
                nativeByteBuffer.reuse();
            }
        } catch (Exception e2) {
            e = e2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            checkSQLException(e);
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateChatParticipants$116(TLRPC.ChatFull chatFull) {
        NotificationCenter notificationCenter = getNotificationCenter();
        int i = NotificationCenter.chatInfoDidLoad;
        Boolean bool = Boolean.FALSE;
        notificationCenter.lambda$postNotificationNameOnUIThread$1(i, chatFull, 0, bool, bool);
    }

    public void loadChannelAdmins(final long j) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$loadChannelAdmins$118(j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadChannelAdmins$118(long j) throws Throwable {
        SQLiteCursor sQLiteCursorQueryFinalized;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT uid, data FROM channel_admins_v3 WHERE did = " + j, new Object[0]);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            LongSparseArray longSparseArray = new LongSparseArray();
            while (sQLiteCursorQueryFinalized.next()) {
                NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(1);
                if (nativeByteBufferByteBufferValue != null) {
                    TLRPC.ChannelParticipant channelParticipantTLdeserialize = TLRPC.ChannelParticipant.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                    nativeByteBufferByteBufferValue.reuse();
                    if (channelParticipantTLdeserialize != null) {
                        longSparseArray.put(sQLiteCursorQueryFinalized.longValue(0), channelParticipantTLdeserialize);
                    }
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            getMessagesController().processLoadedChannelAdmins(longSparseArray, j, true);
        } catch (Exception e2) {
            e = e2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            checkSQLException(e);
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            throw th;
        }
    }

    public void putChannelAdmins(final long j, final LongSparseArray longSparseArray) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda64
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$putChannelAdmins$119(j, longSparseArray);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putChannelAdmins$119(long j, LongSparseArray longSparseArray) throws Throwable {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.executeFast("DELETE FROM channel_admins_v3 WHERE did = " + j).stepThis().dispose();
                this.database.beginTransaction();
                SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO channel_admins_v3 VALUES(?, ?, ?)");
                for (int i = 0; i < longSparseArray.size(); i++) {
                    try {
                        sQLitePreparedStatementExecuteFast.requery();
                        sQLitePreparedStatementExecuteFast.bindLong(1, j);
                        sQLitePreparedStatementExecuteFast.bindLong(2, longSparseArray.keyAt(i));
                        TLRPC.ChannelParticipant channelParticipant = (TLRPC.ChannelParticipant) longSparseArray.valueAt(i);
                        NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(channelParticipant.getObjectSize());
                        channelParticipant.serializeToStream(nativeByteBuffer);
                        sQLitePreparedStatementExecuteFast.bindByteBuffer(3, nativeByteBuffer);
                        sQLitePreparedStatementExecuteFast.step();
                        nativeByteBuffer.reuse();
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        checkSQLException(e);
                        SQLiteDatabase sQLiteDatabase = this.database;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                }
                sQLitePreparedStatementExecuteFast.dispose();
                this.database.commitTransaction();
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.commitTransaction();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void updateChannelUsers(final long j, final ArrayList<TLRPC.ChannelParticipant> arrayList) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda103
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateChannelUsers$120(j, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$updateChannelUsers$120(long r11, java.util.ArrayList r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 207
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateChannelUsers$120(long, java.util.ArrayList):void");
    }

    public void saveBotCache(final String str, final TLObject tLObject) {
        if (tLObject == null || TextUtils.isEmpty(str)) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda87
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$saveBotCache$121(tLObject, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveBotCache$121(TLObject tLObject, String str) throws Throwable {
        int currentTime;
        int i;
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                currentTime = getConnectionsManager().getCurrentTime();
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            if (tLObject instanceof TLRPC.TL_messages_botCallbackAnswer) {
                i = ((TLRPC.TL_messages_botCallbackAnswer) tLObject).cache_time;
            } else {
                if (tLObject instanceof TLRPC.TL_messages_botResults) {
                    i = ((TLRPC.TL_messages_botResults) tLObject).cache_time;
                }
                sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO botcache VALUES(?, ?, ?)");
                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tLObject.getObjectSize());
                tLObject.serializeToStream(nativeByteBuffer);
                sQLitePreparedStatementExecuteFast.bindString(1, str);
                sQLitePreparedStatementExecuteFast.bindInteger(2, currentTime);
                sQLitePreparedStatementExecuteFast.bindByteBuffer(3, nativeByteBuffer);
                sQLitePreparedStatementExecuteFast.step();
                sQLitePreparedStatementExecuteFast.dispose();
                nativeByteBuffer.reuse();
                return;
            }
            NativeByteBuffer nativeByteBuffer2 = new NativeByteBuffer(tLObject.getObjectSize());
            tLObject.serializeToStream(nativeByteBuffer2);
            sQLitePreparedStatementExecuteFast.bindString(1, str);
            sQLitePreparedStatementExecuteFast.bindInteger(2, currentTime);
            sQLitePreparedStatementExecuteFast.bindByteBuffer(3, nativeByteBuffer2);
            sQLitePreparedStatementExecuteFast.step();
            sQLitePreparedStatementExecuteFast.dispose();
            nativeByteBuffer2.reuse();
            return;
        } catch (Exception e2) {
            e = e2;
            sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
            checkSQLException(e);
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
                return;
            }
            return;
        } catch (Throwable th2) {
            th = th2;
            sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
        currentTime += i;
        sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO botcache VALUES(?, ?, ?)");
    }

    public void getBotCache(final String str, final RequestDelegate requestDelegate) {
        if (str == null || requestDelegate == null) {
            return;
        }
        final int currentTime = getConnectionsManager().getCurrentTime();
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda242
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$getBotCache$122(currentTime, str, requestDelegate);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v5, types: [org.telegram.SQLite.SQLiteCursor] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$getBotCache$122(int r5, java.lang.String r6, org.telegram.tgnet.RequestDelegate r7) throws java.lang.Throwable {
        /*
            r4 = this;
            r0 = 0
            org.telegram.SQLite.SQLiteDatabase r1 = r4.database     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            r2.<init>()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            java.lang.String r3 = "DELETE FROM botcache WHERE date < "
            r2.append(r3)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            r2.append(r5)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            java.lang.String r5 = r2.toString()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            org.telegram.SQLite.SQLitePreparedStatement r5 = r1.executeFast(r5)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            org.telegram.SQLite.SQLitePreparedStatement r5 = r5.stepThis()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            r5.dispose()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            org.telegram.SQLite.SQLiteDatabase r5 = r4.database     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            java.lang.String r1 = "SELECT data FROM botcache WHERE id = ?"
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L74
            r3 = 0
            r2[r3] = r6     // Catch: java.lang.Throwable -> L71 java.lang.Exception -> L74
            org.telegram.SQLite.SQLiteCursor r5 = r5.queryFinalized(r1, r2)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6d
            boolean r6 = r5.next()     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L66
            if (r6 == 0) goto L5e
            org.telegram.tgnet.NativeByteBuffer r6 = r5.byteBufferValue(r3)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a
            if (r6 == 0) goto L5e
            int r1 = r6.readInt32(r3)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a
            r2 = 911761060(0x36585ea4, float:3.2241596E-6)
            if (r1 != r2) goto L4d
            org.telegram.tgnet.TLRPC$TL_messages_botCallbackAnswer r1 = org.telegram.tgnet.TLRPC.TL_messages_botCallbackAnswer.TLdeserialize(r6, r1, r3)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a
            goto L51
        L47:
            r6 = move-exception
            r1 = r0
            goto L83
        L4a:
            r6 = move-exception
            r1 = r0
            goto L58
        L4d:
            org.telegram.tgnet.TLRPC$messages_BotResults r1 = org.telegram.tgnet.TLRPC.messages_BotResults.TLdeserialize(r6, r1, r3)     // Catch: java.lang.Throwable -> L47 java.lang.Exception -> L4a
        L51:
            r6.reuse()     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L57
            goto L5f
        L55:
            r6 = move-exception
            goto L83
        L57:
            r6 = move-exception
        L58:
            r4.checkSQLException(r6)     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L5c
            goto L5f
        L5c:
            r6 = move-exception
            goto L77
        L5e:
            r1 = r0
        L5f:
            r5.dispose()     // Catch: java.lang.Throwable -> L55 java.lang.Exception -> L5c
            r7.run(r1, r0)
            goto L82
        L66:
            r6 = move-exception
            r1 = r0
            goto L77
        L69:
            r6 = move-exception
        L6a:
            r5 = r0
            r1 = r5
            goto L83
        L6d:
            r6 = move-exception
        L6e:
            r5 = r0
            r1 = r5
            goto L77
        L71:
            r5 = move-exception
            r6 = r5
            goto L6a
        L74:
            r5 = move-exception
            r6 = r5
            goto L6e
        L77:
            r4.checkSQLException(r6)     // Catch: java.lang.Throwable -> L55
            r7.run(r1, r0)
            if (r5 == 0) goto L82
            r5.dispose()
        L82:
            return
        L83:
            r7.run(r1, r0)
            if (r5 == 0) goto L8b
            r5.dispose()
        L8b:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getBotCache$122(int, java.lang.String, org.telegram.tgnet.RequestDelegate):void");
    }

    public ArrayList<TLRPC.UserFull> loadUserInfos(HashSet<Long> hashSet) {
        ArrayList<TLRPC.UserFull> arrayList = new ArrayList<>();
        try {
            String strJoin = TextUtils.join(",", hashSet);
            SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT info, pinned FROM user_settings WHERE uid IN(" + strJoin + ")", new Object[0]);
            while (sQLiteCursorQueryFinalized.next()) {
                NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                if (nativeByteBufferByteBufferValue != null) {
                    TLRPC.UserFull userFullTLdeserialize = TLRPC.UserFull.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                    userFullTLdeserialize.pinned_msg_id = sQLiteCursorQueryFinalized.intValue(1);
                    arrayList.add(userFullTLdeserialize);
                    nativeByteBufferByteBufferValue.reuse();
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            return arrayList;
        } catch (Exception e) {
            checkSQLException(e);
            return arrayList;
        }
    }

    public void loadUserInfo(final TLRPC.User user, final boolean z, final int i, int i2) {
        if (user == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda210
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$loadUserInfo$124(user, z, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:135:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$loadUserInfo$124(org.telegram.tgnet.TLRPC.User r19, boolean r20, int r21) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 500
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$loadUserInfo$124(org.telegram.tgnet.TLRPC$User, boolean, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadUserInfo$123(ArrayList arrayList) {
        getMessagesController().putChats(arrayList, true);
    }

    public void updateUserInfo(final TLRPC.UserFull userFull, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateUserInfo$125(userFull, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003f A[Catch: all -> 0x0037, Exception -> 0x003b, TRY_ENTER, TRY_LEAVE, TryCatch #6 {Exception -> 0x003b, all -> 0x0037, blocks: (B:8:0x000c, B:20:0x003f, B:22:0x0067, B:24:0x0070, B:26:0x0086, B:31:0x0096, B:33:0x009c), top: B:52:0x000c }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c8  */
    /* JADX WARN: Type inference failed for: r11v14 */
    /* JADX WARN: Type inference failed for: r11v18 */
    /* JADX WARN: Type inference failed for: r11v4, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v7, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r11v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$updateUserInfo$125(org.telegram.tgnet.TLRPC.UserFull r10, boolean r11) throws java.lang.Throwable {
        /*
            r9 = this;
            org.telegram.tgnet.TLRPC$User r0 = r10.user
            if (r0 == 0) goto L7
            long r0 = r0.f1734id
            goto L9
        L7:
            long r0 = r10.f1735id
        L9:
            r2 = 0
            if (r11 == 0) goto L3f
            org.telegram.SQLite.SQLiteDatabase r11 = r9.database     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            r3.<init>()     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            java.lang.String r4 = "SELECT uid FROM user_settings WHERE uid = "
            r3.append(r4)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            r3.append(r0)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            r4 = 0
            java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            org.telegram.SQLite.SQLiteCursor r11 = r11.queryFinalized(r3, r4)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            boolean r3 = r11.next()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            r11.dispose()     // Catch: java.lang.Throwable -> L31 java.lang.Exception -> L34
            if (r3 != 0) goto L3f
            goto Lc0
        L31:
            r10 = move-exception
            goto Lc1
        L34:
            r10 = move-exception
            goto Lb3
        L37:
            r10 = move-exception
            r11 = r2
            goto Lc1
        L3b:
            r10 = move-exception
            r11 = r2
            goto Lb3
        L3f:
            org.telegram.SQLite.SQLiteDatabase r11 = r9.database     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            java.lang.String r3 = "REPLACE INTO user_settings VALUES(?, ?, ?)"
            org.telegram.SQLite.SQLitePreparedStatement r11 = r11.executeFast(r3)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            org.telegram.tgnet.NativeByteBuffer r3 = new org.telegram.tgnet.NativeByteBuffer     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            int r4 = r10.getObjectSize()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r10.serializeToStream(r3)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r4 = 1
            r11.bindLong(r4, r0)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r5 = 2
            r11.bindByteBuffer(r5, r3)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            int r6 = r10.pinned_msg_id     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r7 = 3
            r11.bindInteger(r7, r6)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r11.step()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r11.dispose()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r3.reuse()     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            int r11 = r10.flags     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            r11 = r11 & 2048(0x800, float:2.87E-42)
            if (r11 == 0) goto L96
            org.telegram.SQLite.SQLiteDatabase r11 = r9.database     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            java.lang.String r3 = "UPDATE dialogs SET folder_id = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r11 = r11.executeFast(r3)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            int r3 = r10.folder_id     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r11.bindInteger(r4, r3)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r11.bindLong(r5, r0)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r11.step()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r11.dispose()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            androidx.collection.LongSparseArray r11 = r9.unknownDialogsIds     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            r11.remove(r0)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            goto L96
        L8c:
            r10 = move-exception
            r8 = r2
            r2 = r11
            r11 = r8
            goto Lc1
        L91:
            r10 = move-exception
            r8 = r2
            r2 = r11
            r11 = r8
            goto Lb3
        L96:
            int r11 = r10.flags     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            r11 = r11 & 16384(0x4000, float:2.2959E-41)
            if (r11 == 0) goto Lc0
            org.telegram.SQLite.SQLiteDatabase r11 = r9.database     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            java.lang.String r3 = "UPDATE dialogs SET ttl_period = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r11 = r11.executeFast(r3)     // Catch: java.lang.Throwable -> L37 java.lang.Exception -> L3b
            int r10 = r10.ttl_period     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r11.bindInteger(r4, r10)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r11.bindLong(r5, r0)     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r11.step()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            r11.dispose()     // Catch: java.lang.Throwable -> L8c java.lang.Exception -> L91
            return
        Lb3:
            r9.checkSQLException(r10)     // Catch: java.lang.Throwable -> L31
            if (r2 == 0) goto Lbb
            r2.dispose()
        Lbb:
            if (r11 == 0) goto Lc0
            r11.dispose()
        Lc0:
            return
        Lc1:
            if (r2 == 0) goto Lc6
            r2.dispose()
        Lc6:
            if (r11 == 0) goto Lcb
            r11.dispose()
        Lcb:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateUserInfo$125(org.telegram.tgnet.TLRPC$UserFull, boolean):void");
    }

    public void updateUserInfoContactBlocked(final long j, final TL_account.RequirementToContact requirementToContact) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateUserInfoContactBlocked$126(j, requirementToContact);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateUserInfoContactBlocked$126(long j, TL_account.RequirementToContact requirementToContact) throws Throwable {
        SQLiteCursor sQLiteCursorQueryFinalized;
        TLRPC.UserFull userFullTLdeserialize;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT uid, info, pinned FROM user_settings WHERE uid = " + j, new Object[0]);
            try {
                try {
                    boolean next = sQLiteCursorQueryFinalized.next();
                    if (next) {
                        NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(1);
                        userFullTLdeserialize = TLRPC.UserFull.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(true), true);
                        if (userFullTLdeserialize != null) {
                            userFullTLdeserialize.pinned_msg_id = sQLiteCursorQueryFinalized.intValue(2);
                        }
                        nativeByteBufferByteBufferValue.reuse();
                    } else {
                        userFullTLdeserialize = null;
                    }
                    sQLiteCursorQueryFinalized.dispose();
                    if (next && userFullTLdeserialize != null && UserObject.applyRequirementToContact(userFullTLdeserialize, requirementToContact)) {
                        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO user_settings VALUES(?, ?, ?)");
                        try {
                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(userFullTLdeserialize.getObjectSize());
                            userFullTLdeserialize.serializeToStream(nativeByteBuffer);
                            sQLitePreparedStatementExecuteFast.bindLong(1, j);
                            sQLitePreparedStatementExecuteFast.bindByteBuffer(2, nativeByteBuffer);
                            sQLitePreparedStatementExecuteFast.bindInteger(3, userFullTLdeserialize.pinned_msg_id);
                            sQLitePreparedStatementExecuteFast.step();
                            sQLitePreparedStatementExecuteFast.dispose();
                            nativeByteBuffer.reuse();
                        } catch (Exception e) {
                            e = e;
                            sQLiteCursorQueryFinalized = null;
                            sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                            checkSQLException(e);
                            if (sQLitePreparedStatement != null) {
                                sQLitePreparedStatement.dispose();
                            }
                            if (sQLiteCursorQueryFinalized != null) {
                                sQLiteCursorQueryFinalized.dispose();
                            }
                        } catch (Throwable th) {
                            th = th;
                            sQLiteCursorQueryFinalized = null;
                            sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                            if (sQLitePreparedStatement != null) {
                                sQLitePreparedStatement.dispose();
                            }
                            if (sQLiteCursorQueryFinalized != null) {
                                sQLiteCursorQueryFinalized.dispose();
                            }
                            throw th;
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
            sQLiteCursorQueryFinalized = null;
        } catch (Throwable th3) {
            th = th3;
            sQLiteCursorQueryFinalized = null;
        }
    }

    public void saveChatInviter(final long j, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda108
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$saveChatInviter$127(j2, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveChatInviter$127(long j, long j2) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE chat_settings_v2 SET inviter = ? WHERE uid = ?");
                sQLitePreparedStatementExecuteFast.requery();
                sQLitePreparedStatementExecuteFast.bindLong(1, j);
                sQLitePreparedStatementExecuteFast.bindLong(2, j2);
                sQLitePreparedStatementExecuteFast.step();
                sQLitePreparedStatementExecuteFast.dispose();
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void saveChatLinksCount(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$saveChatLinksCount$128(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$saveChatLinksCount$128(int i, long j) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE chat_settings_v2 SET links = ? WHERE uid = ?");
                sQLitePreparedStatementExecuteFast.requery();
                sQLitePreparedStatementExecuteFast.bindInteger(1, i);
                sQLitePreparedStatementExecuteFast.bindLong(2, j);
                sQLitePreparedStatementExecuteFast.step();
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void updateChatInfo(final TLRPC.ChatFull chatFull, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda227
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateChatInfo$129(chatFull, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0169  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0179  */
    /* JADX WARN: Removed duplicated region for block: B:91:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r13v22 */
    /* JADX WARN: Type inference failed for: r13v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$updateChatInfo$129(org.telegram.tgnet.TLRPC.ChatFull r12, boolean r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 381
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateChatInfo$129(org.telegram.tgnet.TLRPC$ChatFull, boolean):void");
    }

    public void updateChatOnlineCount(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda45
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateChatOnlineCount$130(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateChatOnlineCount$130(int i, long j) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE chat_settings_v2 SET online = ? WHERE uid = ?");
                sQLitePreparedStatementExecuteFast.requery();
                sQLitePreparedStatementExecuteFast.bindInteger(1, i);
                sQLitePreparedStatementExecuteFast.bindLong(2, j);
                sQLitePreparedStatementExecuteFast.step();
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void updatePinnedMessages(final long j, final ArrayList<Integer> arrayList, final boolean z, final int i, final int i2, final boolean z2, final HashMap<Integer, MessageObject> map) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda107
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updatePinnedMessages$133(z, map, i2, j, arrayList, i, z2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0312  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0327  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x032c  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x033a  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x033f  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0344  */
    /* JADX WARN: Removed duplicated region for block: B:159:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:160:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v18, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v20 */
    /* JADX WARN: Type inference failed for: r8v33 */
    /* JADX WARN: Type inference failed for: r8v34, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r8v35 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$updatePinnedMessages$133(boolean r19, final java.util.HashMap r20, final int r21, final long r22, final java.util.ArrayList r24, int r25, boolean r26) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 840
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updatePinnedMessages$133(boolean, java.util.HashMap, int, long, java.util.ArrayList, int, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePinnedMessages$131(long j, ArrayList arrayList, HashMap map, int i, int i2, boolean z) {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.didLoadPinnedMessages, Long.valueOf(j), arrayList, Boolean.TRUE, null, map, Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updatePinnedMessages$132(long j, ArrayList arrayList, HashMap map, int i, int i2, boolean z) {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.didLoadPinnedMessages, Long.valueOf(j), arrayList, Boolean.FALSE, null, map, Integer.valueOf(i), Integer.valueOf(i2), Boolean.valueOf(z));
    }

    public void updateChatInfo(final long j, final long j2, final int i, final long j3, final int i2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda249
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateChatInfo$135(j, i, j2, j3, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateChatInfo$135(long j, int i, long j2, long j3, int i2) throws Throwable {
        int i3;
        SQLiteCursor sQLiteCursorQueryFinalized;
        final TLRPC.ChatFull chatFullTLdeserialize;
        TLRPC.ChatParticipant tL_chatParticipant;
        NativeByteBuffer nativeByteBufferByteBufferValue;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                i3 = 0;
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT info, pinned, online, inviter FROM chat_settings_v2 WHERE uid = " + j, new Object[0]);
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            new ArrayList();
            if (!sQLiteCursorQueryFinalized.next() || (nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0)) == null) {
                chatFullTLdeserialize = null;
            } else {
                chatFullTLdeserialize = TLRPC.ChatFull.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                nativeByteBufferByteBufferValue.reuse();
                chatFullTLdeserialize.pinned_msg_id = sQLiteCursorQueryFinalized.intValue(1);
                chatFullTLdeserialize.online_count = sQLiteCursorQueryFinalized.intValue(2);
                chatFullTLdeserialize.inviterId = sQLiteCursorQueryFinalized.longValue(3);
            }
            sQLiteCursorQueryFinalized.dispose();
            if (chatFullTLdeserialize instanceof TLRPC.TL_chatFull) {
                if (i == 1) {
                    while (true) {
                        if (i3 >= chatFullTLdeserialize.participants.participants.size()) {
                            break;
                        }
                        if (((TLRPC.ChatParticipant) chatFullTLdeserialize.participants.participants.get(i3)).user_id == j2) {
                            chatFullTLdeserialize.participants.participants.remove(i3);
                            break;
                        }
                        i3++;
                    }
                } else if (i == 0) {
                    ArrayList arrayList = chatFullTLdeserialize.participants.participants;
                    int size = arrayList.size();
                    while (i3 < size) {
                        Object obj = arrayList.get(i3);
                        i3++;
                        if (((TLRPC.ChatParticipant) obj).user_id == j2) {
                            return;
                        }
                    }
                    TLRPC.TL_chatParticipant tL_chatParticipant2 = new TLRPC.TL_chatParticipant();
                    tL_chatParticipant2.user_id = j2;
                    tL_chatParticipant2.inviter_id = j3;
                    tL_chatParticipant2.date = getConnectionsManager().getCurrentTime();
                    chatFullTLdeserialize.participants.participants.add(tL_chatParticipant2);
                } else if (i == 2) {
                    while (true) {
                        if (i3 >= chatFullTLdeserialize.participants.participants.size()) {
                            break;
                        }
                        TLRPC.ChatParticipant chatParticipant = (TLRPC.ChatParticipant) chatFullTLdeserialize.participants.participants.get(i3);
                        if (chatParticipant.user_id == j2) {
                            if (j3 == 1) {
                                tL_chatParticipant = new TLRPC.TL_chatParticipantAdmin();
                            } else {
                                tL_chatParticipant = new TLRPC.TL_chatParticipant();
                            }
                            tL_chatParticipant.user_id = chatParticipant.user_id;
                            tL_chatParticipant.date = chatParticipant.date;
                            tL_chatParticipant.inviter_id = chatParticipant.inviter_id;
                            chatFullTLdeserialize.participants.participants.set(i3, tL_chatParticipant);
                        } else {
                            i3++;
                        }
                    }
                }
                chatFullTLdeserialize.participants.version = i2;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda252
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$updateChatInfo$134(chatFullTLdeserialize);
                    }
                });
                SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO chat_settings_v2 VALUES(?, ?, ?, ?, ?, ?, ?)");
                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(chatFullTLdeserialize.getObjectSize());
                chatFullTLdeserialize.serializeToStream(nativeByteBuffer);
                sQLitePreparedStatementExecuteFast.bindLong(1, j);
                sQLitePreparedStatementExecuteFast.bindByteBuffer(2, nativeByteBuffer);
                sQLitePreparedStatementExecuteFast.bindInteger(3, chatFullTLdeserialize.pinned_msg_id);
                sQLitePreparedStatementExecuteFast.bindInteger(4, chatFullTLdeserialize.online_count);
                sQLitePreparedStatementExecuteFast.bindLong(5, chatFullTLdeserialize.inviterId);
                sQLitePreparedStatementExecuteFast.bindInteger(6, chatFullTLdeserialize.invitesCount);
                sQLitePreparedStatementExecuteFast.bindInteger(7, chatFullTLdeserialize.participants_count);
                sQLitePreparedStatementExecuteFast.step();
                sQLitePreparedStatementExecuteFast.dispose();
                nativeByteBuffer.reuse();
            }
        } catch (Exception e2) {
            e = e2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            checkSQLException(e);
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateChatInfo$134(TLRPC.ChatFull chatFull) {
        NotificationCenter notificationCenter = getNotificationCenter();
        int i = NotificationCenter.chatInfoDidLoad;
        Boolean bool = Boolean.FALSE;
        notificationCenter.lambda$postNotificationNameOnUIThread$1(i, chatFull, 0, bool, bool);
    }

    public boolean isMigratedChat(final long j) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final boolean[] zArr = new boolean[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$isMigratedChat$136(j, zArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$isMigratedChat$136(long j, boolean[] zArr, CountDownLatch countDownLatch) throws Throwable {
        SQLiteCursor sQLiteCursorQueryFinalized;
        TLRPC.ChatFull chatFullTLdeserialize;
        NativeByteBuffer nativeByteBufferByteBufferValue;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT info FROM chat_settings_v2 WHERE uid = " + j, new Object[0]);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            new ArrayList();
            if (!sQLiteCursorQueryFinalized.next() || (nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0)) == null) {
                chatFullTLdeserialize = null;
            } else {
                chatFullTLdeserialize = TLRPC.ChatFull.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                nativeByteBufferByteBufferValue.reuse();
            }
            sQLiteCursorQueryFinalized.dispose();
            zArr[0] = (chatFullTLdeserialize instanceof TLRPC.TL_channelFull) && chatFullTLdeserialize.migrated_from_chat_id != 0;
            countDownLatch.countDown();
            countDownLatch.countDown();
        } catch (Exception e2) {
            e = e2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            checkSQLException(e);
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            countDownLatch.countDown();
        } catch (Throwable th2) {
            th = th2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            countDownLatch.countDown();
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v2, types: [org.telegram.tgnet.TLRPC$Message] */
    public TLRPC.Message getMessageLegit(long j, long j2) {
        ?? r14;
        SQLiteCursor sQLiteCursorQueryFinalized;
        NativeByteBuffer nativeByteBufferByteBufferValue;
        int i;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                if (j != 0) {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT data, send_state, mid, date, replydata, ttl FROM messages_v2 WHERE uid = " + j + " AND mid = " + j2 + " LIMIT 1", new Object[0]);
                } else {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT data, send_state, mid, date, replydata, ttl FROM messages_v2 WHERE is_channel = 0 AND mid = " + j2 + " LIMIT 1", new Object[0]);
                }
                sQLiteCursor = sQLiteCursorQueryFinalized;
                TLRPC.Message message = null;
                while (sQLiteCursor.next()) {
                    try {
                        NativeByteBuffer nativeByteBufferByteBufferValue2 = sQLiteCursor.byteBufferValue(0);
                        if (nativeByteBufferByteBufferValue2 != null) {
                            TLRPC.Message messageTLdeserialize = TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue2, nativeByteBufferByteBufferValue2.readInt32(false), false);
                            messageTLdeserialize.send_state = sQLiteCursor.intValue(1);
                            int iIntValue = sQLiteCursor.intValue(2);
                            messageTLdeserialize.f1597id = iIntValue;
                            if (iIntValue > 0 && (i = messageTLdeserialize.send_state) != 0 && i != 3) {
                                messageTLdeserialize.send_state = 0;
                            }
                            if (j == getUserConfig().getClientUserId()) {
                                messageTLdeserialize.out = true;
                                messageTLdeserialize.unread = false;
                            } else {
                                messageTLdeserialize.unread = true;
                            }
                            messageTLdeserialize.readAttachPath(nativeByteBufferByteBufferValue2, getUserConfig().getClientUserId());
                            nativeByteBufferByteBufferValue2.reuse();
                            messageTLdeserialize.date = sQLiteCursor.intValue(3);
                            messageTLdeserialize.dialog_id = j;
                            if (messageTLdeserialize.ttl == 0) {
                                messageTLdeserialize.ttl = sQLiteCursor.intValue(5);
                            }
                            TLRPC.MessageReplyHeader messageReplyHeader = messageTLdeserialize.reply_to;
                            if (messageReplyHeader != null && ((messageReplyHeader.reply_to_msg_id != 0 || messageReplyHeader.reply_to_random_id != 0) && !sQLiteCursor.isNull(4) && (nativeByteBufferByteBufferValue = sQLiteCursor.byteBufferValue(4)) != null)) {
                                TLRPC.Message messageTLdeserialize2 = TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                                messageTLdeserialize.replyMessage = messageTLdeserialize2;
                                messageTLdeserialize2.readAttachPath(nativeByteBufferByteBufferValue, getUserConfig().getClientUserId());
                                nativeByteBufferByteBufferValue.reuse();
                            }
                            message = messageTLdeserialize;
                        }
                    } catch (Exception e) {
                        e = e;
                        r14 = message;
                        checkSQLException(e);
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        return r14;
                    }
                }
                sQLiteCursor.dispose();
                return message;
            } catch (Throwable th) {
                if (0 != 0) {
                    sQLiteCursor.dispose();
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            r14 = sQLiteCursor;
        }
    }

    public TLRPC.Message getMessage(final long j, final long j2) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final AtomicReference atomicReference = new AtomicReference();
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda175
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getMessage$137(j, j2, atomicReference, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return (TLRPC.Message) atomicReference.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMessage$137(long j, long j2, AtomicReference atomicReference, CountDownLatch countDownLatch) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT data FROM messages_v2 WHERE uid = " + j + " AND mid = " + j2 + " LIMIT 1", new Object[0]);
                while (sQLiteCursorQueryFinalized.next()) {
                    NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                    if (nativeByteBufferByteBufferValue != null) {
                        TLRPC.Message messageTLdeserialize = TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                        nativeByteBufferByteBufferValue.reuse();
                        atomicReference.set(messageTLdeserialize);
                    }
                }
                sQLiteCursorQueryFinalized.dispose();
                countDownLatch.countDown();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
                countDownLatch.countDown();
            }
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            countDownLatch.countDown();
            throw th;
        }
    }

    public boolean hasInviteMeMessage(final long j) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final boolean[] zArr = new boolean[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda79
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$hasInviteMeMessage$138(j, zArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hasInviteMeMessage$138(long j, boolean[] zArr, CountDownLatch countDownLatch) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                long clientUserId = getUserConfig().getClientUserId();
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT data FROM messages_v2 WHERE uid = " + (-j) + " AND out = 0 ORDER BY mid DESC LIMIT 100", new Object[0]);
                while (true) {
                    if (!sQLiteCursorQueryFinalized.next()) {
                        break;
                    }
                    NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                    if (nativeByteBufferByteBufferValue != null) {
                        TLRPC.Message messageTLdeserialize = TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                        nativeByteBufferByteBufferValue.reuse();
                        TLRPC.MessageAction messageAction = messageTLdeserialize.action;
                        if ((messageAction instanceof TLRPC.TL_messageActionChatAddUser) && messageAction.users.contains(Long.valueOf(clientUserId))) {
                            zArr[0] = true;
                            break;
                        }
                    }
                }
                sQLiteCursorQueryFinalized.dispose();
                countDownLatch.countDown();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
                countDownLatch.countDown();
            }
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            countDownLatch.countDown();
            throw th;
        }
    }

    public HashMap<Long, Integer> getSmallGroupsParticipantsCount() {
        HashMap<Long, Integer> map = new HashMap<>();
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT uid, info, participants_count FROM chat_settings_v2 WHERE participants_count > 1", new Object[0]);
                while (sQLiteCursorQueryFinalized.next()) {
                    long jLongValue = sQLiteCursorQueryFinalized.longValue(0);
                    NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(1);
                    int iIntValue = sQLiteCursorQueryFinalized.intValue(2);
                    if (nativeByteBufferByteBufferValue != null) {
                        TLRPC.ChatFull chatFullTLdeserialize = TLRPC.ChatFull.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                        nativeByteBufferByteBufferValue.reuse();
                        if (chatFullTLdeserialize instanceof TLRPC.TL_channelFull) {
                            map.put(Long.valueOf(jLongValue), Integer.valueOf(iIntValue));
                        }
                    }
                }
                sQLiteCursorQueryFinalized.dispose();
                return map;
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
                return map;
            }
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:156|(9:316|158|(0)|300|184|(8:328|186|(1:188)(1:189)|190|318|191|192|(4:314|194|(2:196|197)|342))(1:210)|211|212|271)(1:166)|167|168|298|169|300|184|(0)(0)|211|212|271) */
    /* JADX WARN: Can't wrap try/catch for region: R(15:0|2|312|3|306|4|5|(8:296|7|(3:9|310|10)|322|27|320|28|(32:30|(4:33|34|292|31)|340|41|104|(2:106|(1:108))|(2:114|(3:117|118|115))|304|119|120|294|121|(8:332|124|125|326|126|127|290|122)|341|136|324|137|338|138|139|(5:141|330|142|(1:145)|146)(1:151)|334|152|(10:336|154|(12:156|(9:316|158|(0)|300|184|(8:328|186|(1:188)(1:189)|190|318|191|192|(4:314|194|(2:196|197)|342))(1:210)|211|212|271)(1:166)|167|168|298|169|300|184|(0)(0)|211|212|271)|161|300|184|(0)(0)|211|212|271)|183|161|300|184|(0)(0)|211|212|271)(30:42|(13:308|44|45|302|46|279|47|(9:281|50|(2:285|52)(1:58)|59|(1:61)(1:63)|(5:65|66|283|67|(1:69))(1:74)|(1:346)(4:77|(1:79)|80|344)|81|48)|343|85|86|(3:89|288|87)|348)(1:41)|104|(0)|(4:110|112|114|(1:115))|304|119|120|294|121|(2:290|122)|341|136|324|137|338|138|139|(0)(0)|334|152|(0)|183|161|300|184|(0)(0)|211|212|271))|26|322|27|320|28|(0)(0)|(1:(0))) */
    /* JADX WARN: Can't wrap try/catch for region: R(19:30|(4:33|34|292|31)|340|41|104|(2:106|(1:108))|(2:114|(3:117|118|115))|(8:304|119|120|294|121|(8:332|124|125|326|126|127|290|122)|341|136)|(2:324|137)|(6:338|138|139|(5:141|330|142|(1:145)|146)(1:151)|334|152)|(10:336|154|(12:156|(9:316|158|(0)|300|184|(8:328|186|(1:188)(1:189)|190|318|191|192|(4:314|194|(2:196|197)|342))(1:210)|211|212|271)(1:166)|167|168|298|169|300|184|(0)(0)|211|212|271)|161|300|184|(0)(0)|211|212|271)|183|161|300|184|(0)(0)|211|212|271) */
    /* JADX WARN: Can't wrap try/catch for region: R(3:(6:332|124|125|326|126|127)|290|122) */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x02e8, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x02e9, code lost:
    
        r12 = r3;
        r2 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x02eb, code lost:
    
        r10 = r5;
        r5 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x02f1, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x02f2, code lost:
    
        r12 = r3;
        r2 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x02f4, code lost:
    
        r10 = r5;
        r5 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x0368, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x0369, code lost:
    
        r12 = r3;
        r10 = r5;
        r5 = r13;
        r13 = r20;
        r2 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x0371, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x0372, code lost:
    
        r12 = r3;
        r10 = r5;
        r5 = r13;
        r13 = r20;
        r2 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x03a2, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x03a3, code lost:
    
        r5 = r13;
        r10 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x03a7, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x03a8, code lost:
    
        r5 = r13;
        r10 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x03c2, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x03c4, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x03c6, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:254:0x03c7, code lost:
    
        r5 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x03ca, code lost:
    
        r2 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x03cd, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x03ce, code lost:
    
        r5 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x03d1, code lost:
    
        r2 = r7;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01ca A[Catch: all -> 0x01a0, Exception -> 0x01a6, TryCatch #59 {Exception -> 0x01a6, all -> 0x01a0, blocks: (B:106:0x01ca, B:108:0x01d0, B:110:0x01d9, B:112:0x01dd, B:115:0x01e4, B:117:0x01f1, B:87:0x0184, B:89:0x018c), top: B:288:0x0184 }] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x01f1 A[Catch: all -> 0x01a0, Exception -> 0x01a6, TRY_LEAVE, TryCatch #59 {Exception -> 0x01a6, all -> 0x01a0, blocks: (B:106:0x01ca, B:108:0x01d0, B:110:0x01d9, B:112:0x01dd, B:115:0x01e4, B:117:0x01f1, B:87:0x0184, B:89:0x018c), top: B:288:0x0184 }] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x027e A[Catch: all -> 0x0249, Exception -> 0x024d, TRY_ENTER, TRY_LEAVE, TryCatch #40 {Exception -> 0x024d, all -> 0x0249, blocks: (B:126:0x023a, B:141:0x027e), top: B:326:0x023a }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0352  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x03f5  */
    /* JADX WARN: Removed duplicated region for block: B:274:0x0409  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x0308 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:332:0x0230 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:336:0x02a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b3 A[Catch: all -> 0x03c2, Exception -> 0x03c4, TRY_ENTER, TRY_LEAVE, TryCatch #43 {Exception -> 0x03c4, all -> 0x03c2, blocks: (B:28:0x007b, B:42:0x00b3), top: B:320:0x007b }] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v28, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r5v29 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.telegram.tgnet.TLRPC.ChatFull loadChatInfoInternal(long r22, boolean r24, boolean r25, boolean r26, int r27) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1051
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.loadChatInfoInternal(long, boolean, boolean, boolean, int):org.telegram.tgnet.TLRPC$ChatFull");
    }

    public TLRPC.ChatFull loadChatInfo(long j, boolean z, CountDownLatch countDownLatch, boolean z2, boolean z3) {
        return loadChatInfo(j, z, countDownLatch, z2, z3, 0);
    }

    public TLRPC.ChatFull loadChatInfo(final long j, final boolean z, final CountDownLatch countDownLatch, final boolean z2, final boolean z3, final int i) {
        final TLRPC.ChatFull[] chatFullArr = new TLRPC.ChatFull[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda172
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadChatInfo$139(chatFullArr, j, z, z2, z3, i, countDownLatch);
            }
        });
        if (countDownLatch != null) {
            try {
                countDownLatch.await();
            } catch (Throwable unused) {
            }
        }
        return chatFullArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadChatInfo$139(TLRPC.ChatFull[] chatFullArr, long j, boolean z, boolean z2, boolean z3, int i, CountDownLatch countDownLatch) {
        chatFullArr[0] = loadChatInfoInternal(j, z, z2, z3, i);
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public TLRPC.ChatFull loadChatInfoInQueue(long j, boolean z, boolean z2, boolean z3, int i) {
        return loadChatInfoInternal(j, z, z2, z3, i);
    }

    public void processPendingRead(final long j, final int i, final int i2, final int i3) {
        final int i4 = this.lastSavedDate;
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda213
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$processPendingRead$140(j, i, i3, i4, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:101:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:169:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:170:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0169 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0195  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$processPendingRead$140(long r18, int r20, int r21, int r22, int r23) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 497
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$processPendingRead$140(long, int, int, int, int):void");
    }

    private void updateTopicsWithReadFromAllInternal(long j, long j2, long j3) throws Throwable {
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT topic_id FROM topics WHERE did = %d AND max_read_id < %d AND (top_message > %d OR unread_count > 0)", Long.valueOf(j), Long.valueOf(j3), Long.valueOf(j2)), new Object[0]);
                while (sQLiteCursorQueryFinalized.next()) {
                    try {
                        updateRepliesMaxReadIdInternal(j, sQLiteCursorQueryFinalized.longValue(0), (int) j3, -1);
                    } catch (Exception e) {
                        e = e;
                        sQLiteCursor = sQLiteCursorQueryFinalized;
                        checkSQLException(e);
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLiteCursor = sQLiteCursorQueryFinalized;
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        throw th;
                    }
                }
                sQLiteCursorQueryFinalized.dispose();
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void putContacts(ArrayList<TLRPC.TL_contact> arrayList, final boolean z) {
        if (!arrayList.isEmpty() || z) {
            final ArrayList arrayList2 = new ArrayList(arrayList);
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda56
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$putContacts$141(z, arrayList2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$putContacts$141(boolean r7, java.util.ArrayList r8) throws java.lang.Throwable {
        /*
            r6 = this;
            r0 = 0
            if (r7 == 0) goto L17
            org.telegram.SQLite.SQLiteDatabase r7 = r6.database     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L15
            java.lang.String r1 = "DELETE FROM contacts WHERE 1"
            org.telegram.SQLite.SQLitePreparedStatement r7 = r7.executeFast(r1)     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L15
            org.telegram.SQLite.SQLitePreparedStatement r7 = r7.stepThis()     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L15
            r7.dispose()     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L15
            goto L17
        L13:
            r7 = move-exception
            goto L6e
        L15:
            r7 = move-exception
            goto L5e
        L17:
            org.telegram.SQLite.SQLiteDatabase r7 = r6.database     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L15
            r7.beginTransaction()     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L15
            org.telegram.SQLite.SQLiteDatabase r7 = r6.database     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L15
            java.lang.String r1 = "REPLACE INTO contacts VALUES(?, ?)"
            org.telegram.SQLite.SQLitePreparedStatement r7 = r7.executeFast(r1)     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L15
            r1 = 0
        L25:
            int r2 = r8.size()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4a
            if (r1 >= r2) goto L4e
            java.lang.Object r2 = r8.get(r1)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4a
            org.telegram.tgnet.TLRPC$TL_contact r2 = (org.telegram.tgnet.TLRPC.TL_contact) r2     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4a
            r7.requery()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4a
            long r3 = r2.user_id     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4a
            r5 = 1
            r7.bindLong(r5, r3)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4a
            boolean r2 = r2.mutual     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4a
            r3 = 2
            r7.bindInteger(r3, r2)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4a
            r7.step()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4a
            int r1 = r1 + 1
            goto L25
        L46:
            r8 = move-exception
            r0 = r7
            r7 = r8
            goto L6e
        L4a:
            r8 = move-exception
            r0 = r7
            r7 = r8
            goto L5e
        L4e:
            r7.dispose()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L4a
            org.telegram.SQLite.SQLiteDatabase r7 = r6.database     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L15
            r7.commitTransaction()     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L15
            org.telegram.SQLite.SQLiteDatabase r7 = r6.database
            if (r7 == 0) goto L6d
            r7.commitTransaction()
            return
        L5e:
            r6.checkSQLException(r7)     // Catch: java.lang.Throwable -> L13
            if (r0 == 0) goto L66
            r0.dispose()
        L66:
            org.telegram.SQLite.SQLiteDatabase r7 = r6.database
            if (r7 == 0) goto L6d
            r7.commitTransaction()
        L6d:
            return
        L6e:
            if (r0 == 0) goto L73
            r0.dispose()
        L73:
            org.telegram.SQLite.SQLiteDatabase r8 = r6.database
            if (r8 == 0) goto L7a
            r8.commitTransaction()
        L7a:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putContacts$141(boolean, java.util.ArrayList):void");
    }

    public void deleteContacts(final ArrayList<Long> arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda147
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$deleteContacts$142(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteContacts$142(ArrayList arrayList) {
        try {
            String strJoin = TextUtils.join(",", arrayList);
            this.database.executeFast("DELETE FROM contacts WHERE uid IN(" + strJoin + ")").stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void applyPhoneBookUpdates(final String str, final String str2) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda102
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$applyPhoneBookUpdates$143(str, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$applyPhoneBookUpdates$143(String str, String str2) {
        try {
            if (str.length() != 0) {
                this.database.executeFast(String.format(Locale.US, "UPDATE user_phones_v7 SET deleted = 0 WHERE sphone IN(%s)", str)).stepThis().dispose();
            }
            if (str2.length() != 0) {
                this.database.executeFast(String.format(Locale.US, "UPDATE user_phones_v7 SET deleted = 1 WHERE sphone IN(%s)", str2)).stepThis().dispose();
            }
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void putCachedPhoneBook(final HashMap<String, ContactsController.Contact> map, final boolean z, boolean z2) {
        if (map != null) {
            if (!map.isEmpty() || z || z2) {
                this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda124
                    @Override // java.lang.Runnable
                    public final void run() throws Throwable {
                        this.f$0.lambda$putCachedPhoneBook$144(map, z);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0140  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$putCachedPhoneBook$144(java.util.HashMap r12, boolean r13) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 342
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putCachedPhoneBook$144(java.util.HashMap, boolean):void");
    }

    public void getCachedPhoneBook(final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda129
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getCachedPhoneBook$145(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00fd A[Catch: all -> 0x0131, TRY_LEAVE, TryCatch #2 {all -> 0x0131, blocks: (B:51:0x00ed, B:53:0x00fd), top: B:128:0x00ed }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0138 A[PHI: r6 r8 r13 r14
      0x0138: PHI (r6v11 int) = (r6v3 int), (r6v13 int), (r6v14 int), (r6v14 int) binds: [B:71:0x013f, B:68:0x0134, B:59:0x010e, B:60:0x0110] A[DONT_GENERATE, DONT_INLINE]
      0x0138: PHI (r8v19 int) = (r8v1 int), (r8v21 int), (r8v22 int), (r8v22 int) binds: [B:71:0x013f, B:68:0x0134, B:59:0x010e, B:60:0x0110] A[DONT_GENERATE, DONT_INLINE]
      0x0138: PHI (r13v9 org.telegram.SQLite.SQLiteCursor) = 
      (r13v4 org.telegram.SQLite.SQLiteCursor)
      (r13v10 org.telegram.SQLite.SQLiteCursor)
      (r13v10 org.telegram.SQLite.SQLiteCursor)
      (r13v10 org.telegram.SQLite.SQLiteCursor)
     binds: [B:71:0x013f, B:68:0x0134, B:59:0x010e, B:60:0x0110] A[DONT_GENERATE, DONT_INLINE]
      0x0138: PHI (r14v8 int) = (r14v1 int), (r14v9 int), (r14v11 int), (r14v11 int) binds: [B:71:0x013f, B:68:0x0134, B:59:0x010e, B:60:0x0110] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0149 A[Catch: all -> 0x0164, Exception -> 0x0167, TRY_ENTER, TryCatch #6 {Exception -> 0x0167, blocks: (B:75:0x0149, B:82:0x0175, B:84:0x017b, B:86:0x0187, B:88:0x01a9, B:89:0x01ab, B:91:0x01af, B:92:0x01b1, B:93:0x01b4, B:96:0x01bc, B:99:0x01c8, B:101:0x01ce, B:103:0x01d4, B:104:0x01d8, B:106:0x01f6, B:81:0x016a), top: B:132:0x0147, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x016a A[Catch: all -> 0x0164, Exception -> 0x0167, TryCatch #6 {Exception -> 0x0167, blocks: (B:75:0x0149, B:82:0x0175, B:84:0x017b, B:86:0x0187, B:88:0x01a9, B:89:0x01ab, B:91:0x01af, B:92:0x01b1, B:93:0x01b4, B:96:0x01bc, B:99:0x01c8, B:101:0x01ce, B:103:0x01d4, B:104:0x01d8, B:106:0x01f6, B:81:0x016a), top: B:132:0x0147, outer: #4 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x017b A[Catch: all -> 0x0164, Exception -> 0x0167, TryCatch #6 {Exception -> 0x0167, blocks: (B:75:0x0149, B:82:0x0175, B:84:0x017b, B:86:0x0187, B:88:0x01a9, B:89:0x01ab, B:91:0x01af, B:92:0x01b1, B:93:0x01b4, B:96:0x01bc, B:99:0x01c8, B:101:0x01ce, B:103:0x01d4, B:104:0x01d8, B:106:0x01f6, B:81:0x016a), top: B:132:0x0147, outer: #4 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$getCachedPhoneBook$145(boolean r25) {
        /*
            Method dump skipped, instructions count: 559
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getCachedPhoneBook$145(boolean):void");
    }

    public void getContacts() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda83
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$getContacts$146();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Not initialized variable reg: 4, insn: 0x0047: MOVE (r3 I:??[OBJECT, ARRAY]) = (r4 I:??[OBJECT, ARRAY]), block:B:13:0x0047 */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0076  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$getContacts$146() throws java.lang.Throwable {
        /*
            r11 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r2 = 1
            r3 = 0
            org.telegram.SQLite.SQLiteDatabase r4 = r11.database     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            java.lang.String r5 = "SELECT * FROM contacts WHERE 1"
            r6 = 0
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            org.telegram.SQLite.SQLiteCursor r4 = r4.queryFinalized(r5, r7)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            r5.<init>()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
        L1c:
            boolean r7 = r4.next()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            if (r7 == 0) goto L4b
            int r7 = r4.intValue(r6)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            long r7 = (long) r7     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            org.telegram.tgnet.TLRPC$TL_contact r9 = new org.telegram.tgnet.TLRPC$TL_contact     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            r9.<init>()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            r9.user_id = r7     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            int r7 = r4.intValue(r2)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            if (r7 != r2) goto L36
            r7 = 1
            goto L37
        L36:
            r7 = 0
        L37:
            r9.mutual = r7     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            r0.add(r9)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            long r7 = r9.user_id     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            java.lang.Long r7 = java.lang.Long.valueOf(r7)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            r5.add(r7)     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            goto L1c
        L46:
            r0 = move-exception
            r3 = r4
            goto L74
        L49:
            r3 = move-exception
            goto L5e
        L4b:
            r4.dispose()     // Catch: java.lang.Throwable -> L46 java.lang.Exception -> L49
            boolean r4 = r5.isEmpty()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            if (r4 != 0) goto L6c
            r11.getUsersInternal(r5, r1)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5a
            goto L6c
        L58:
            r0 = move-exception
            goto L74
        L5a:
            r4 = move-exception
            r10 = r4
            r4 = r3
            r3 = r10
        L5e:
            r0.clear()     // Catch: java.lang.Throwable -> L46
            r1.clear()     // Catch: java.lang.Throwable -> L46
            r11.checkSQLException(r3)     // Catch: java.lang.Throwable -> L46
            if (r4 == 0) goto L6c
            r4.dispose()
        L6c:
            org.telegram.messenger.ContactsController r3 = r11.getContactsController()
            r3.processLoadedContacts(r0, r1, r2)
            return
        L74:
            if (r3 == 0) goto L79
            r3.dispose()
        L79:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getContacts$146():void");
    }

    public void getUnsentMessages(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda201
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$getUnsentMessages$147(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x02ab  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02ca A[Catch: all -> 0x0210, Exception -> 0x0213, TryCatch #0 {all -> 0x0210, blocks: (B:61:0x01c4, B:63:0x01ca, B:65:0x01d0, B:67:0x01f3, B:69:0x0208, B:75:0x0216, B:77:0x0233, B:79:0x0243, B:89:0x0282, B:91:0x028a, B:93:0x0292, B:95:0x0298, B:102:0x02a8, B:99:0x02a3, B:81:0x024c, B:83:0x0254, B:85:0x0260, B:86:0x026a, B:88:0x0277, B:105:0x02af, B:107:0x02c4, B:109:0x02ca, B:111:0x02d0, B:113:0x02f3, B:115:0x0318, B:117:0x031e, B:119:0x0323, B:121:0x034e, B:123:0x035b, B:124:0x0362, B:126:0x0380, B:128:0x0390, B:138:0x03cf, B:140:0x03d7, B:142:0x03df, B:144:0x03e5, B:151:0x03f5, B:148:0x03f0, B:130:0x0399, B:132:0x03a1, B:134:0x03ad, B:135:0x03b7, B:137:0x03c4, B:154:0x03fe, B:180:0x0462), top: B:187:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x040a A[Catch: all -> 0x00aa, Exception -> 0x00ae, TRY_ENTER, TryCatch #7 {Exception -> 0x00ae, all -> 0x00aa, blocks: (B:4:0x0050, B:6:0x005a, B:8:0x0060, B:10:0x0086, B:12:0x00a2, B:18:0x00b2, B:20:0x00d5, B:22:0x00dc, B:24:0x00e9, B:26:0x00f9, B:36:0x0138, B:38:0x0140, B:40:0x0148, B:42:0x014e, B:46:0x015a, B:44:0x0156, B:28:0x0102, B:30:0x010a, B:32:0x0116, B:33:0x0120, B:35:0x012d, B:21:0x00d9, B:49:0x0167, B:155:0x0402, B:158:0x040a, B:159:0x0411, B:161:0x0417, B:163:0x041f, B:165:0x0425, B:166:0x042b, B:168:0x0431, B:170:0x043d, B:171:0x0440, B:172:0x0446, B:174:0x0452), top: B:192:0x0050 }] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0417 A[Catch: all -> 0x00aa, Exception -> 0x00ae, TryCatch #7 {Exception -> 0x00ae, all -> 0x00aa, blocks: (B:4:0x0050, B:6:0x005a, B:8:0x0060, B:10:0x0086, B:12:0x00a2, B:18:0x00b2, B:20:0x00d5, B:22:0x00dc, B:24:0x00e9, B:26:0x00f9, B:36:0x0138, B:38:0x0140, B:40:0x0148, B:42:0x014e, B:46:0x015a, B:44:0x0156, B:28:0x0102, B:30:0x010a, B:32:0x0116, B:33:0x0120, B:35:0x012d, B:21:0x00d9, B:49:0x0167, B:155:0x0402, B:158:0x040a, B:159:0x0411, B:161:0x0417, B:163:0x041f, B:165:0x0425, B:166:0x042b, B:168:0x0431, B:170:0x043d, B:171:0x0440, B:172:0x0446, B:174:0x0452), top: B:192:0x0050 }] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x041d  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0425 A[Catch: all -> 0x00aa, Exception -> 0x00ae, TryCatch #7 {Exception -> 0x00ae, all -> 0x00aa, blocks: (B:4:0x0050, B:6:0x005a, B:8:0x0060, B:10:0x0086, B:12:0x00a2, B:18:0x00b2, B:20:0x00d5, B:22:0x00dc, B:24:0x00e9, B:26:0x00f9, B:36:0x0138, B:38:0x0140, B:40:0x0148, B:42:0x014e, B:46:0x015a, B:44:0x0156, B:28:0x0102, B:30:0x010a, B:32:0x0116, B:33:0x0120, B:35:0x012d, B:21:0x00d9, B:49:0x0167, B:155:0x0402, B:158:0x040a, B:159:0x0411, B:161:0x0417, B:163:0x041f, B:165:0x0425, B:166:0x042b, B:168:0x0431, B:170:0x043d, B:171:0x0440, B:172:0x0446, B:174:0x0452), top: B:192:0x0050 }] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0450  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x0467  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x046d  */
    /* JADX WARN: Removed duplicated region for block: B:223:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01ca A[Catch: all -> 0x0210, Exception -> 0x0213, TryCatch #0 {all -> 0x0210, blocks: (B:61:0x01c4, B:63:0x01ca, B:65:0x01d0, B:67:0x01f3, B:69:0x0208, B:75:0x0216, B:77:0x0233, B:79:0x0243, B:89:0x0282, B:91:0x028a, B:93:0x0292, B:95:0x0298, B:102:0x02a8, B:99:0x02a3, B:81:0x024c, B:83:0x0254, B:85:0x0260, B:86:0x026a, B:88:0x0277, B:105:0x02af, B:107:0x02c4, B:109:0x02ca, B:111:0x02d0, B:113:0x02f3, B:115:0x0318, B:117:0x031e, B:119:0x0323, B:121:0x034e, B:123:0x035b, B:124:0x0362, B:126:0x0380, B:128:0x0390, B:138:0x03cf, B:140:0x03d7, B:142:0x03df, B:144:0x03e5, B:151:0x03f5, B:148:0x03f0, B:130:0x0399, B:132:0x03a1, B:134:0x03ad, B:135:0x03b7, B:137:0x03c4, B:154:0x03fe, B:180:0x0462), top: B:187:0x0002 }] */
    /* JADX WARN: Type inference failed for: r2v14 */
    /* JADX WARN: Type inference failed for: r7v32 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$getUnsentMessages$147(int r22) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1137
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getUnsentMessages$147(int):void");
    }

    public boolean checkMessageByRandomId(final long j) throws InterruptedException {
        final boolean[] zArr = new boolean[1];
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda165
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkMessageByRandomId$148(j, zArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkMessageByRandomId$148(long j, boolean[] zArr, CountDownLatch countDownLatch) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT random_id FROM randoms_v2 WHERE random_id = %d", Long.valueOf(j)), new Object[0]);
                if (sQLiteCursorQueryFinalized.next()) {
                    zArr[0] = true;
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            countDownLatch.countDown();
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public boolean checkMessageId(final long j, final int i) throws InterruptedException {
        final boolean[] zArr = new boolean[1];
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda184
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkMessageId$149(j, i, zArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkMessageId$149(long j, int i, boolean[] zArr, CountDownLatch countDownLatch) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT mid FROM messages_v2 WHERE uid = %d AND mid = %d", Long.valueOf(j), Integer.valueOf(i)), new Object[0]);
                if (sQLiteCursorQueryFinalized.next()) {
                    zArr[0] = true;
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            countDownLatch.countDown();
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public void getUnreadMention(final long j, final long j2, final IntCallback intCallback) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda75
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getUnreadMention$151(j2, j, intCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getUnreadMention$151(long j, long j2, final IntCallback intCallback) {
        SQLiteCursor sQLiteCursorQueryFinalized;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                if (j != 0) {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT MIN(mid) FROM messages_topics WHERE uid = %d AND topic_id = %d AND mention = 1 AND read_state IN(0, 1)", Long.valueOf(j2), Long.valueOf(j)), new Object[0]);
                } else {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT MIN(mid) FROM messages_v2 WHERE uid = %d AND mention = 1 AND read_state IN(0, 1)", Long.valueOf(j2)), new Object[0]);
                }
                sQLiteCursor = sQLiteCursorQueryFinalized;
                final int iIntValue = sQLiteCursor.next() ? sQLiteCursor.intValue(0) : 0;
                sQLiteCursor.dispose();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda48
                    @Override // java.lang.Runnable
                    public final void run() {
                        intCallback.run(iIntValue);
                    }
                });
                sQLiteCursor.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursor != null) {
                    sQLiteCursor.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            throw th;
        }
    }

    public void getMessagesCount(final long j, final IntCallback intCallback) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda158
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getMessagesCount$153(j, intCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMessagesCount$153(long j, final IntCallback intCallback) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(mid) FROM messages_v2 WHERE uid = %d", Long.valueOf(j)), new Object[0]);
                final int iIntValue = sQLiteCursorQueryFinalized.next() ? sQLiteCursorQueryFinalized.intValue(0) : 0;
                sQLiteCursorQueryFinalized.dispose();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda164
                    @Override // java.lang.Runnable
                    public final void run() {
                        intCallback.run(iIntValue);
                    }
                });
                sQLiteCursorQueryFinalized.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    /* JADX WARN: Not initialized variable reg: 47, insn: 0x0767: MOVE (r14 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r47 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]), block:B:337:0x0763 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 13 */
    public java.lang.Runnable getMessagesInternal(long r58, long r60, int r62, int r63, int r64, int r65, int r66, int r67, int r68, long r69, int r71, boolean r72, boolean r73, org.telegram.messenger.Timer r74) {
        /*
            Method dump skipped, instructions count: 8932
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.getMessagesInternal(long, long, int, int, int, int, int, int, int, long, int, boolean, boolean, org.telegram.messenger.Timer):java.lang.Runnable");
    }

    public static /* synthetic */ int $r8$lambda$5fnueE4K2xJV22kw4Gat3iBa5LM(TLRPC.Message message, TLRPC.Message message2) {
        int i;
        int i2;
        int i3 = message.f1597id;
        if (i3 > 0 && (i2 = message2.f1597id) > 0) {
            if (i3 > i2) {
                return -1;
            }
            return i3 < i2 ? 1 : 0;
        }
        if (i3 < 0 && (i = message2.f1597id) < 0) {
            if (i3 < i) {
                return -1;
            }
            return i3 > i ? 1 : 0;
        }
        int i4 = message.date;
        int i5 = message2.date;
        if (i4 > i5) {
            return -1;
        }
        return i4 < i5 ? 1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMessagesInternal$155(TLRPC.TL_messages_messages tL_messages_messages, int i, long j, long j2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, boolean z, int i11, long j3, int i12, boolean z2, int i13, boolean z3, boolean z4, Timer timer) {
        getMessagesController().processLoadedMessages(tL_messages_messages, i, j, j2, i2, i3, i4, true, i5, i6, i7, i8, i9, i10, z, i11, j3, i12, z2, i13, z3, z4, timer);
    }

    public void getAnimatedEmoji(String str, ArrayList<TLRPC.Document> arrayList) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM animated_emoji WHERE document_id IN (%s)", str), new Object[0]);
                while (sQLiteCursorQueryFinalized.next()) {
                    NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                    try {
                        TLRPC.Document documentTLdeserialize = TLRPC.Document.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(true), true);
                        if (documentTLdeserialize != null && documentTLdeserialize.f1579id != 0) {
                            arrayList.add(documentTLdeserialize);
                        }
                    } catch (Exception e) {
                        checkSQLException(e);
                    }
                    if (nativeByteBufferByteBufferValue != null) {
                        nativeByteBufferByteBufferValue.reuse();
                    }
                }
            } catch (SQLiteException e2) {
                e2.printStackTrace();
                if (sQLiteCursorQueryFinalized == null) {
                    return;
                }
            }
            sQLiteCursorQueryFinalized.dispose();
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public void getMessages(final long j, final long j2, boolean z, final int i, final int i2, final int i3, final int i4, final int i5, final int i6, final int i7, final long j3, final int i8, final boolean z2, final boolean z3, final Timer timer) {
        final Timer.Task taskStart = Timer.start(timer, "MessagesStorage.getMessages: storageQueue.postRunnable");
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda169
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getMessages$157(taskStart, timer, j, j2, i, i2, i3, i4, i5, i6, i7, j3, i8, z2, z3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getMessages$157(Timer.Task task, Timer timer, long j, long j2, int i, int i2, int i3, int i4, int i5, int i6, int i7, long j3, int i8, boolean z, boolean z2) {
        Timer.done(task);
        Timer.Task taskStart = Timer.start(timer, "MessagesStorage.getMessages");
        final Runnable messagesInternal = getMessagesInternal(j, j2, i, i2, i3, i4, i5, i6, i7, j3, i8, z, z2, timer);
        Timer.done(taskStart);
        final Timer.Task taskStart2 = Timer.start(timer, "MessagesStorage.getMessages: stageQueue.postRunnable");
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda190
            @Override // java.lang.Runnable
            public final void run() {
                MessagesStorage.$r8$lambda$Oa6_duNwLZvLtnrGIMsNi15Sutc(taskStart2, messagesInternal);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$Oa6_duNwLZvLtnrGIMsNi15Sutc(Timer.Task task, Runnable runnable) {
        Timer.done(task);
        runnable.run();
    }

    public void clearSentMedia() {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda41
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$clearSentMedia$158();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearSentMedia$158() {
        try {
            this.database.executeFast("DELETE FROM sent_files_v2 WHERE 1").stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public Object[] getSentFile(final String str, final int i) throws InterruptedException {
        if (str == null || str.toLowerCase().endsWith("attheme")) {
            return null;
        }
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Object[] objArr = new Object[2];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda128
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getSentFile$159(str, i, objArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        if (objArr[0] != null) {
            return objArr;
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getSentFile$159(String str, int i, Object[] objArr, CountDownLatch countDownLatch) {
        NativeByteBuffer nativeByteBufferByteBufferValue;
        try {
            try {
                String strMD5 = Utilities.MD5(str);
                if (strMD5 != null) {
                    SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data, parent FROM sent_files_v2 WHERE uid = '%s' AND type = %d", strMD5, Integer.valueOf(i)), new Object[0]);
                    if (sQLiteCursorQueryFinalized.next() && (nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0)) != null) {
                        TLRPC.MessageMedia messageMediaTLdeserialize = TLRPC.MessageMedia.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                        nativeByteBufferByteBufferValue.reuse();
                        if (messageMediaTLdeserialize instanceof TLRPC.TL_messageMediaDocument) {
                            objArr[0] = ((TLRPC.TL_messageMediaDocument) messageMediaTLdeserialize).document;
                        } else if (messageMediaTLdeserialize instanceof TLRPC.TL_messageMediaPhoto) {
                            objArr[0] = ((TLRPC.TL_messageMediaPhoto) messageMediaTLdeserialize).photo;
                        }
                        if (objArr[0] != null) {
                            objArr[1] = sQLiteCursorQueryFinalized.stringValue(1);
                        }
                    }
                    sQLiteCursorQueryFinalized.dispose();
                }
                countDownLatch.countDown();
            } catch (Exception e) {
                checkSQLException(e);
                countDownLatch.countDown();
            }
        } catch (Throwable th) {
            countDownLatch.countDown();
            throw th;
        }
    }

    private void updateWidgets(long j) {
        ArrayList<Long> arrayList = new ArrayList<>();
        arrayList.add(Long.valueOf(j));
        updateWidgets(arrayList);
    }

    private void updateWidgets(ArrayList<Long> arrayList) {
        if (arrayList.isEmpty()) {
            return;
        }
        try {
            TextUtils.join(",", arrayList);
            SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT DISTINCT id FROM shortcut_widget WHERE did IN(%s,-1)", TextUtils.join(",", arrayList)), new Object[0]);
            AppWidgetManager appWidgetManager = null;
            while (sQLiteCursorQueryFinalized.next()) {
                if (appWidgetManager == null) {
                    appWidgetManager = AppWidgetManager.getInstance(ApplicationLoader.applicationContext);
                }
                appWidgetManager.notifyAppWidgetViewDataChanged(sQLiteCursorQueryFinalized.intValue(0), C2369R.id.list_view);
            }
            sQLiteCursorQueryFinalized.dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void putWidgetDialogs(final int i, final ArrayList<TopicKey> arrayList) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda221
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$putWidgetDialogs$160(i, arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putWidgetDialogs$160(int i, ArrayList arrayList) {
        try {
            this.database.beginTransaction();
            this.database.executeFast("DELETE FROM shortcut_widget WHERE id = " + i).stepThis().dispose();
            SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO shortcut_widget VALUES(?, ?, ?)");
            if (arrayList.isEmpty()) {
                sQLitePreparedStatementExecuteFast.requery();
                sQLitePreparedStatementExecuteFast.bindInteger(1, i);
                sQLitePreparedStatementExecuteFast.bindLong(2, -1L);
                sQLitePreparedStatementExecuteFast.bindInteger(3, 0);
                sQLitePreparedStatementExecuteFast.step();
            } else {
                int size = arrayList.size();
                for (int i2 = 0; i2 < size; i2++) {
                    long j = ((TopicKey) arrayList.get(i2)).dialogId;
                    sQLitePreparedStatementExecuteFast.requery();
                    sQLitePreparedStatementExecuteFast.bindInteger(1, i);
                    sQLitePreparedStatementExecuteFast.bindLong(2, j);
                    sQLitePreparedStatementExecuteFast.bindInteger(3, i2);
                    sQLitePreparedStatementExecuteFast.step();
                }
            }
            sQLitePreparedStatementExecuteFast.dispose();
            this.database.commitTransaction();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void clearWidgetDialogs(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda246
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$clearWidgetDialogs$161(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearWidgetDialogs$161(int i) {
        try {
            this.database.executeFast("DELETE FROM shortcut_widget WHERE id = " + i).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void getWidgetDialogIds(final int i, final int i2, final ArrayList<Long> arrayList, final ArrayList<TLRPC.User> arrayList2, final ArrayList<TLRPC.Chat> arrayList3, final boolean z) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda181
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$getWidgetDialogIds$162(i, arrayList, arrayList2, arrayList3, z, i2, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getWidgetDialogIds$162(int i, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, boolean z, int i2, CountDownLatch countDownLatch) throws Throwable {
        Exception exc;
        Throwable th;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                ArrayList<Long> arrayList4 = new ArrayList<>();
                ArrayList arrayList5 = new ArrayList();
                try {
                    SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT did FROM shortcut_widget WHERE id = %d ORDER BY ord ASC", Integer.valueOf(i)), new Object[0]);
                    while (sQLiteCursorQueryFinalized.next()) {
                        try {
                            long jLongValue = sQLiteCursorQueryFinalized.longValue(0);
                            if (jLongValue != -1) {
                                arrayList.add(Long.valueOf(jLongValue));
                                if (arrayList2 != null && arrayList3 != null) {
                                    if (DialogObject.isUserDialog(jLongValue)) {
                                        arrayList4.add(Long.valueOf(jLongValue));
                                    } else {
                                        arrayList5.add(Long.valueOf(-jLongValue));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            exc = e;
                            sQLiteCursor = sQLiteCursorQueryFinalized;
                            checkSQLException(exc);
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                            }
                            countDownLatch.countDown();
                            return;
                        } catch (Throwable th2) {
                            th = th2;
                            sQLiteCursor = sQLiteCursorQueryFinalized;
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                            }
                            countDownLatch.countDown();
                            throw th;
                        }
                    }
                    sQLiteCursorQueryFinalized.dispose();
                    if (!z && arrayList.isEmpty()) {
                        if (i2 == 0) {
                            SQLiteCursor sQLiteCursorQueryFinalized2 = this.database.queryFinalized("SELECT did FROM dialogs WHERE folder_id = 0 ORDER BY pinned DESC, date DESC LIMIT 0,10", new Object[0]);
                            while (sQLiteCursorQueryFinalized2.next()) {
                                long jLongValue2 = sQLiteCursorQueryFinalized2.longValue(0);
                                if (!DialogObject.isFolderDialogId(jLongValue2)) {
                                    arrayList.add(Long.valueOf(jLongValue2));
                                    if (arrayList2 != null && arrayList3 != null) {
                                        if (DialogObject.isUserDialog(jLongValue2)) {
                                            arrayList4.add(Long.valueOf(jLongValue2));
                                        } else {
                                            arrayList5.add(Long.valueOf(-jLongValue2));
                                        }
                                    }
                                }
                            }
                            sQLiteCursorQueryFinalized2.dispose();
                        } else {
                            SQLiteCursor sQLiteCursorQueryFinalized3 = getMessagesStorage().getDatabase().queryFinalized("SELECT did FROM chat_hints WHERE type = 0 ORDER BY rating DESC LIMIT 4", new Object[0]);
                            while (sQLiteCursorQueryFinalized3.next()) {
                                long jLongValue3 = sQLiteCursorQueryFinalized3.longValue(0);
                                arrayList.add(Long.valueOf(jLongValue3));
                                if (arrayList2 != null && arrayList3 != null) {
                                    if (DialogObject.isUserDialog(jLongValue3)) {
                                        arrayList4.add(Long.valueOf(jLongValue3));
                                    } else {
                                        arrayList5.add(Long.valueOf(-jLongValue3));
                                    }
                                }
                            }
                            sQLiteCursorQueryFinalized3.dispose();
                        }
                    }
                    if (arrayList2 != null && arrayList3 != null) {
                        if (!arrayList5.isEmpty()) {
                            getChatsInternal(TextUtils.join(",", arrayList5), arrayList3);
                        }
                        if (!arrayList4.isEmpty()) {
                            getUsersInternal(arrayList4, (ArrayList<TLRPC.User>) arrayList2);
                        }
                    }
                    countDownLatch.countDown();
                } catch (Exception e2) {
                    exc = e2;
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (Exception e3) {
            exc = e3;
        }
    }

    public void getWidgetDialogs(final int i, final int i2, final ArrayList<Long> arrayList, final LongSparseArray longSparseArray, final LongSparseArray longSparseArray2, final ArrayList<TLRPC.User> arrayList2, final ArrayList<TLRPC.Chat> arrayList3) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda140
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$getWidgetDialogs$163(i, arrayList, i2, longSparseArray, longSparseArray2, arrayList3, arrayList2, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getWidgetDialogs$163(int i, ArrayList arrayList, int i2, LongSparseArray longSparseArray, LongSparseArray longSparseArray2, ArrayList arrayList2, ArrayList arrayList3, CountDownLatch countDownLatch) throws Throwable {
        boolean z;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                ArrayList<Long> arrayList4 = new ArrayList<>();
                ArrayList arrayList5 = new ArrayList();
                SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT did FROM shortcut_widget WHERE id = %d ORDER BY ord ASC", Integer.valueOf(i)), new Object[0]);
                while (sQLiteCursorQueryFinalized.next()) {
                    try {
                        long jLongValue = sQLiteCursorQueryFinalized.longValue(0);
                        if (jLongValue != -1) {
                            arrayList.add(Long.valueOf(jLongValue));
                            if (DialogObject.isUserDialog(jLongValue)) {
                                arrayList4.add(Long.valueOf(jLongValue));
                            } else {
                                arrayList5.add(Long.valueOf(-jLongValue));
                            }
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLiteCursor = sQLiteCursorQueryFinalized;
                        checkSQLException(e);
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        countDownLatch.countDown();
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLiteCursor = sQLiteCursorQueryFinalized;
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        countDownLatch.countDown();
                        throw th;
                    }
                }
                sQLiteCursorQueryFinalized.dispose();
                if (arrayList.isEmpty() && i2 == 1) {
                    SQLiteCursor sQLiteCursorQueryFinalized2 = getMessagesStorage().getDatabase().queryFinalized("SELECT did FROM chat_hints WHERE type = 0 ORDER BY rating DESC LIMIT 4", new Object[0]);
                    while (sQLiteCursorQueryFinalized2.next()) {
                        long jLongValue2 = sQLiteCursorQueryFinalized2.longValue(0);
                        arrayList.add(Long.valueOf(jLongValue2));
                        if (DialogObject.isUserDialog(jLongValue2)) {
                            arrayList4.add(Long.valueOf(jLongValue2));
                        } else {
                            arrayList5.add(Long.valueOf(-jLongValue2));
                        }
                    }
                    sQLiteCursorQueryFinalized2.dispose();
                }
                if (arrayList.isEmpty()) {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT d.did, d.last_mid, d.unread_count, d.date, m.data, m.read_state, m.mid, m.send_state, m.date FROM dialogs as d LEFT JOIN messages_v2 as m ON d.last_mid = m.mid AND d.did = m.uid WHERE d.folder_id = 0 ORDER BY d.pinned DESC, d.date DESC LIMIT 0,10", new Object[0]);
                    z = true;
                } else {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT d.did, d.last_mid, d.unread_count, d.date, m.data, m.read_state, m.mid, m.send_state, m.date FROM dialogs as d LEFT JOIN messages_v2 as m ON d.last_mid = m.mid AND d.did = m.uid WHERE d.did IN(%s)", TextUtils.join(",", arrayList)), new Object[0]);
                    z = false;
                }
                while (sQLiteCursorQueryFinalized.next()) {
                    long jLongValue3 = sQLiteCursorQueryFinalized.longValue(0);
                    if (!DialogObject.isFolderDialogId(jLongValue3)) {
                        if (z) {
                            arrayList.add(Long.valueOf(jLongValue3));
                        }
                        TLRPC.TL_dialog tL_dialog = new TLRPC.TL_dialog();
                        tL_dialog.f1577id = jLongValue3;
                        tL_dialog.top_message = sQLiteCursorQueryFinalized.intValue(1);
                        tL_dialog.unread_count = sQLiteCursorQueryFinalized.intValue(2);
                        tL_dialog.last_message_date = sQLiteCursorQueryFinalized.intValue(3);
                        longSparseArray.put(tL_dialog.f1577id, tL_dialog);
                        NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(4);
                        if (nativeByteBufferByteBufferValue != null) {
                            TLRPC.Message messageTLdeserialize = TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                            messageTLdeserialize.readAttachPath(nativeByteBufferByteBufferValue, getUserConfig().clientUserId);
                            nativeByteBufferByteBufferValue.reuse();
                            MessageObject.setUnreadFlags(messageTLdeserialize, sQLiteCursorQueryFinalized.intValue(5));
                            messageTLdeserialize.f1597id = sQLiteCursorQueryFinalized.intValue(6);
                            messageTLdeserialize.send_state = sQLiteCursorQueryFinalized.intValue(7);
                            int iIntValue = sQLiteCursorQueryFinalized.intValue(8);
                            if (iIntValue != 0) {
                                tL_dialog.last_message_date = iIntValue;
                            }
                            long j = tL_dialog.f1577id;
                            messageTLdeserialize.dialog_id = j;
                            longSparseArray2.put(j, messageTLdeserialize);
                            addUsersAndChatsFromMessage(messageTLdeserialize, arrayList4, arrayList5, null);
                        }
                    }
                }
                sQLiteCursorQueryFinalized.dispose();
                if (!z && arrayList.size() > longSparseArray.size()) {
                    int size = arrayList.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        Long l = (Long) arrayList.get(i3);
                        long jLongValue4 = l.longValue();
                        if (longSparseArray.get(((Long) arrayList.get(i3)).longValue()) == null) {
                            TLRPC.TL_dialog tL_dialog2 = new TLRPC.TL_dialog();
                            tL_dialog2.f1577id = jLongValue4;
                            longSparseArray.put(jLongValue4, tL_dialog2);
                            if (DialogObject.isChatDialog(jLongValue4)) {
                                long j2 = -jLongValue4;
                                if (arrayList5.contains(Long.valueOf(j2))) {
                                    arrayList5.add(Long.valueOf(j2));
                                }
                            } else if (arrayList4.contains(l)) {
                                arrayList4.add(l);
                            }
                        }
                    }
                }
                if (!arrayList5.isEmpty()) {
                    getChatsInternal(TextUtils.join(",", arrayList5), arrayList2);
                }
                if (!arrayList4.isEmpty()) {
                    getUsersInternal(arrayList4, (ArrayList<TLRPC.User>) arrayList3);
                }
                countDownLatch.countDown();
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void putSentFile(final String str, final TLObject tLObject, final int i, final String str2) {
        if (str == null || tLObject == null || str2 == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda77
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$putSentFile$164(str, tLObject, i, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putSentFile$164(String str, TLObject tLObject, int i, String str2) {
        TLRPC.MessageMedia tL_messageMediaDocument;
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                String strMD5 = Utilities.MD5(str);
                if (strMD5 != null) {
                    if (tLObject instanceof TLRPC.Photo) {
                        tL_messageMediaDocument = new TLRPC.TL_messageMediaPhoto();
                        tL_messageMediaDocument.photo = (TLRPC.Photo) tLObject;
                        tL_messageMediaDocument.flags |= 1;
                    } else if (tLObject instanceof TLRPC.Document) {
                        tL_messageMediaDocument = new TLRPC.TL_messageMediaDocument();
                        tL_messageMediaDocument.document = (TLRPC.Document) tLObject;
                        tL_messageMediaDocument.flags |= 1;
                    } else {
                        tL_messageMediaDocument = null;
                    }
                    if (tL_messageMediaDocument == null) {
                        return;
                    }
                    sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO sent_files_v2 VALUES(?, ?, ?, ?)");
                    sQLitePreparedStatementExecuteFast.requery();
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tL_messageMediaDocument.getObjectSize());
                    tL_messageMediaDocument.serializeToStream(nativeByteBuffer);
                    sQLitePreparedStatementExecuteFast.bindString(1, strMD5);
                    sQLitePreparedStatementExecuteFast.bindInteger(2, i);
                    sQLitePreparedStatementExecuteFast.bindByteBuffer(3, nativeByteBuffer);
                    sQLitePreparedStatementExecuteFast.bindString(4, str2);
                    sQLitePreparedStatementExecuteFast.step();
                    nativeByteBuffer.reuse();
                }
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void updateEncryptedChatSeq(final TLRPC.EncryptedChat encryptedChat, final boolean z) {
        if (encryptedChat == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda136
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateEncryptedChatSeq$165(encryptedChat, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEncryptedChatSeq$165(TLRPC.EncryptedChat encryptedChat, boolean z) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE enc_chats SET seq_in = ?, seq_out = ?, use_count = ?, in_seq_no = ?, mtproto_seq = ? WHERE uid = ?");
                sQLitePreparedStatementExecuteFast.bindInteger(1, encryptedChat.seq_in);
                sQLitePreparedStatementExecuteFast.bindInteger(2, encryptedChat.seq_out);
                sQLitePreparedStatementExecuteFast.bindInteger(3, (encryptedChat.key_use_count_in << 16) | encryptedChat.key_use_count_out);
                sQLitePreparedStatementExecuteFast.bindInteger(4, encryptedChat.in_seq_no);
                sQLitePreparedStatementExecuteFast.bindInteger(5, encryptedChat.mtproto_seq);
                sQLitePreparedStatementExecuteFast.bindInteger(6, encryptedChat.f1583id);
                sQLitePreparedStatementExecuteFast.step();
                if (z && encryptedChat.in_seq_no != 0) {
                    long encryptedChatId = DialogObject.getEncryptedChatId(encryptedChat.f1583id);
                    this.database.executeFast(String.format(Locale.US, "DELETE FROM messages_v2 WHERE mid IN (SELECT m.mid FROM messages_v2 as m LEFT JOIN messages_seq as s ON m.mid = s.mid WHERE m.uid = %d AND m.date = 0 AND m.mid < 0 AND s.seq_out <= %d) AND uid = %d", Long.valueOf(encryptedChatId), Integer.valueOf(encryptedChat.in_seq_no), Long.valueOf(encryptedChatId))).stepThis().dispose();
                }
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void updateEncryptedChatTTL(final TLRPC.EncryptedChat encryptedChat) {
        if (encryptedChat == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateEncryptedChatTTL$166(encryptedChat);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEncryptedChatTTL$166(TLRPC.EncryptedChat encryptedChat) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE enc_chats SET ttl = ? WHERE uid = ?");
                sQLitePreparedStatementExecuteFast.bindInteger(1, encryptedChat.ttl);
                sQLitePreparedStatementExecuteFast.bindInteger(2, encryptedChat.f1583id);
                sQLitePreparedStatementExecuteFast.step();
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void updateEncryptedChatLayer(final TLRPC.EncryptedChat encryptedChat) {
        if (encryptedChat == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda192
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateEncryptedChatLayer$167(encryptedChat);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEncryptedChatLayer$167(TLRPC.EncryptedChat encryptedChat) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE enc_chats SET layer = ? WHERE uid = ?");
                sQLitePreparedStatementExecuteFast.bindInteger(1, encryptedChat.layer);
                sQLitePreparedStatementExecuteFast.bindInteger(2, encryptedChat.f1583id);
                sQLitePreparedStatementExecuteFast.step();
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void updateEncryptedChat(final TLRPC.EncryptedChat encryptedChat) {
        if (encryptedChat == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda161
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateEncryptedChat$168(encryptedChat);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateEncryptedChat$168(TLRPC.EncryptedChat encryptedChat) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                byte[] bArr = encryptedChat.key_hash;
                if (bArr == null || bArr.length < 16) {
                    byte[] bArr2 = encryptedChat.auth_key;
                    if (bArr2 != null) {
                        encryptedChat.key_hash = AndroidUtilities.calcAuthKeyHash(bArr2);
                    }
                }
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE enc_chats SET data = ?, g = ?, authkey = ?, ttl = ?, layer = ?, seq_in = ?, seq_out = ?, use_count = ?, exchange_id = ?, key_date = ?, fprint = ?, fauthkey = ?, khash = ?, in_seq_no = ?, admin_id = ?, mtproto_seq = ? WHERE uid = ?");
                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(encryptedChat.getObjectSize());
                byte[] bArr3 = encryptedChat.a_or_b;
                NativeByteBuffer nativeByteBuffer2 = new NativeByteBuffer(bArr3 != null ? bArr3.length : 1);
                byte[] bArr4 = encryptedChat.auth_key;
                NativeByteBuffer nativeByteBuffer3 = new NativeByteBuffer(bArr4 != null ? bArr4.length : 1);
                byte[] bArr5 = encryptedChat.future_auth_key;
                NativeByteBuffer nativeByteBuffer4 = new NativeByteBuffer(bArr5 != null ? bArr5.length : 1);
                byte[] bArr6 = encryptedChat.key_hash;
                NativeByteBuffer nativeByteBuffer5 = new NativeByteBuffer(bArr6 != null ? bArr6.length : 1);
                encryptedChat.serializeToStream(nativeByteBuffer);
                sQLitePreparedStatementExecuteFast.bindByteBuffer(1, nativeByteBuffer);
                byte[] bArr7 = encryptedChat.a_or_b;
                if (bArr7 != null) {
                    nativeByteBuffer2.writeBytes(bArr7);
                }
                byte[] bArr8 = encryptedChat.auth_key;
                if (bArr8 != null) {
                    nativeByteBuffer3.writeBytes(bArr8);
                }
                byte[] bArr9 = encryptedChat.future_auth_key;
                if (bArr9 != null) {
                    nativeByteBuffer4.writeBytes(bArr9);
                }
                byte[] bArr10 = encryptedChat.key_hash;
                if (bArr10 != null) {
                    nativeByteBuffer5.writeBytes(bArr10);
                }
                sQLitePreparedStatementExecuteFast.bindByteBuffer(2, nativeByteBuffer2);
                sQLitePreparedStatementExecuteFast.bindByteBuffer(3, nativeByteBuffer3);
                sQLitePreparedStatementExecuteFast.bindInteger(4, encryptedChat.ttl);
                sQLitePreparedStatementExecuteFast.bindInteger(5, encryptedChat.layer);
                sQLitePreparedStatementExecuteFast.bindInteger(6, encryptedChat.seq_in);
                sQLitePreparedStatementExecuteFast.bindInteger(7, encryptedChat.seq_out);
                sQLitePreparedStatementExecuteFast.bindInteger(8, (encryptedChat.key_use_count_in << 16) | encryptedChat.key_use_count_out);
                sQLitePreparedStatementExecuteFast.bindLong(9, encryptedChat.exchange_id);
                sQLitePreparedStatementExecuteFast.bindInteger(10, encryptedChat.key_create_date);
                sQLitePreparedStatementExecuteFast.bindLong(11, encryptedChat.future_key_fingerprint);
                sQLitePreparedStatementExecuteFast.bindByteBuffer(12, nativeByteBuffer4);
                sQLitePreparedStatementExecuteFast.bindByteBuffer(13, nativeByteBuffer5);
                sQLitePreparedStatementExecuteFast.bindInteger(14, encryptedChat.in_seq_no);
                sQLitePreparedStatementExecuteFast.bindLong(15, encryptedChat.admin_id);
                sQLitePreparedStatementExecuteFast.bindInteger(16, encryptedChat.mtproto_seq);
                sQLitePreparedStatementExecuteFast.bindInteger(17, encryptedChat.f1583id);
                sQLitePreparedStatementExecuteFast.step();
                nativeByteBuffer.reuse();
                nativeByteBuffer2.reuse();
                nativeByteBuffer3.reuse();
                nativeByteBuffer4.reuse();
                nativeByteBuffer5.reuse();
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void isDialogHasTopMessage(final long j, final Runnable runnable) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$isDialogHasTopMessage$169(j, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$isDialogHasTopMessage$169(long j, Runnable runnable) {
        boolean z = false;
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT last_mid FROM dialogs WHERE did = %d", Long.valueOf(j)), new Object[0]);
                if (sQLiteCursorQueryFinalized.next()) {
                    if (sQLiteCursorQueryFinalized.intValue(0) != 0) {
                        z = true;
                    }
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            if (z) {
                return;
            }
            AndroidUtilities.runOnUIThread(runnable);
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public boolean hasAuthMessage(final int i) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final boolean[] zArr = new boolean[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda191
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$hasAuthMessage$170(i, zArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return zArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hasAuthMessage$170(int i, boolean[] zArr, CountDownLatch countDownLatch) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT mid FROM messages_v2 WHERE uid = 777000 AND date = %d AND mid < 0 LIMIT 1", Integer.valueOf(i)), new Object[0]);
                zArr[0] = sQLiteCursorQueryFinalized.next();
                sQLiteCursorQueryFinalized.dispose();
                countDownLatch.countDown();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
                countDownLatch.countDown();
            }
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            countDownLatch.countDown();
            throw th;
        }
    }

    public void getEncryptedChat(final long j, final CountDownLatch countDownLatch, final ArrayList<TLObject> arrayList) {
        if (countDownLatch == null || arrayList == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda72
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getEncryptedChat$171(j, arrayList, countDownLatch);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getEncryptedChat$171(long j, ArrayList arrayList, CountDownLatch countDownLatch) {
        try {
            ArrayList<Long> arrayList2 = new ArrayList<>();
            ArrayList<TLRPC.EncryptedChat> arrayList3 = new ArrayList<>();
            getEncryptedChatsInternal("" + j, arrayList3, arrayList2);
            if (!arrayList3.isEmpty() && !arrayList2.isEmpty()) {
                ArrayList<TLRPC.User> arrayList4 = new ArrayList<>();
                getUsersInternal(arrayList2, arrayList4);
                if (!arrayList4.isEmpty()) {
                    arrayList.add(arrayList3.get(0));
                    arrayList.add(arrayList4.get(0));
                }
            }
        } catch (Exception e) {
            checkSQLException(e);
        } finally {
            countDownLatch.countDown();
        }
    }

    public void putEncryptedChat(final TLRPC.EncryptedChat encryptedChat, final TLRPC.User user, final TLRPC.Dialog dialog) {
        if (encryptedChat == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda193
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$putEncryptedChat$172(encryptedChat, user, dialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:60:0x019a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$putEncryptedChat$172(org.telegram.tgnet.TLRPC.EncryptedChat r17, org.telegram.tgnet.TLRPC.User r18, org.telegram.tgnet.TLRPC.Dialog r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 414
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putEncryptedChat$172(org.telegram.tgnet.TLRPC$EncryptedChat, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Dialog):void");
    }

    private String formatUserSearchName(TLRPC.User user) {
        StringBuilder sb = new StringBuilder();
        String str = user.first_name;
        if (str != null && str.length() > 0) {
            sb.append(user.first_name);
        }
        String str2 = user.last_name;
        if (str2 != null && str2.length() > 0) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(user.last_name);
        }
        sb.append(";;;");
        String str3 = user.username;
        if (str3 != null && str3.length() > 0) {
            sb.append(user.username);
        } else {
            ArrayList arrayList = user.usernames;
            if (arrayList != null && arrayList.size() > 0) {
                for (int i = 0; i < user.usernames.size(); i++) {
                    TLRPC.TL_username tL_username = (TLRPC.TL_username) user.usernames.get(i);
                    if (tL_username != null && tL_username.active) {
                        sb.append(tL_username.username);
                        sb.append(";;");
                    }
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    public boolean containsLocalDialog(final long j) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Boolean[] boolArr = {Boolean.FALSE};
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda180
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$containsLocalDialog$173(j, boolArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return boolArr[0].booleanValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$containsLocalDialog$173(long j, Boolean[] boolArr, CountDownLatch countDownLatch) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT date FROM dialogs WHERE did = " + j, new Object[0]);
                boolArr[0] = Boolean.valueOf(sQLiteCursorQueryFinalized.next());
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            countDownLatch.countDown();
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    private void putUsersInternal(List<TLRPC.User> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO users VALUES(?, ?, ?, ?)");
        for (int i = 0; i < list.size(); i++) {
            TLRPC.User user = list.get(i);
            if (user != null) {
                if (user.min) {
                    SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM users WHERE uid = %d", Long.valueOf(user.f1734id)), new Object[0]);
                    if (sQLiteCursorQueryFinalized.next()) {
                        try {
                            NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                            if (nativeByteBufferByteBufferValue != null) {
                                TLRPC.User userTLdeserialize = TLRPC.User.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                                nativeByteBufferByteBufferValue.reuse();
                                if (userTLdeserialize != null) {
                                    String str = user.username;
                                    if (str != null) {
                                        userTLdeserialize.username = str;
                                        userTLdeserialize.flags |= 8;
                                    } else {
                                        userTLdeserialize.username = null;
                                        userTLdeserialize.flags &= -9;
                                    }
                                    if (user.apply_min_photo) {
                                        TLRPC.UserProfilePhoto userProfilePhoto = user.photo;
                                        if (userProfilePhoto != null) {
                                            userTLdeserialize.photo = userProfilePhoto;
                                            userTLdeserialize.flags |= 32;
                                        } else {
                                            userTLdeserialize.photo = null;
                                            userTLdeserialize.flags &= -33;
                                        }
                                    }
                                    user = userTLdeserialize;
                                }
                            }
                        } catch (Exception e) {
                            checkSQLException(e);
                        }
                    }
                    sQLiteCursorQueryFinalized.dispose();
                }
                sQLitePreparedStatementExecuteFast.requery();
                NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(user.getObjectSize());
                user.serializeToStream(nativeByteBuffer);
                sQLitePreparedStatementExecuteFast.bindLong(1, user.f1734id);
                sQLitePreparedStatementExecuteFast.bindString(2, formatUserSearchName(user));
                TLRPC.UserStatus userStatus = user.status;
                if (userStatus != null) {
                    if (userStatus instanceof TLRPC.TL_userStatusRecently) {
                        userStatus.expires = userStatus.by_me ? -1000 : -100;
                    } else if (userStatus instanceof TLRPC.TL_userStatusLastWeek) {
                        userStatus.expires = userStatus.by_me ? -1001 : -101;
                    } else if (userStatus instanceof TLRPC.TL_userStatusLastMonth) {
                        userStatus.expires = userStatus.by_me ? -1002 : -102;
                    }
                    sQLitePreparedStatementExecuteFast.bindInteger(3, userStatus.expires);
                } else {
                    sQLitePreparedStatementExecuteFast.bindInteger(3, 0);
                }
                sQLitePreparedStatementExecuteFast.bindByteBuffer(4, nativeByteBuffer);
                sQLitePreparedStatementExecuteFast.step();
                nativeByteBuffer.reuse();
                isForumCacheInvalidate(user.f1734id);
            }
        }
        sQLitePreparedStatementExecuteFast.dispose();
    }

    public void updateChatDefaultBannedRights(final long j, final TLRPC.TL_chatBannedRights tL_chatBannedRights, final int i) {
        if (tL_chatBannedRights == null || j == 0) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda47
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateChatDefaultBannedRights$174(j, i, tL_chatBannedRights);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:60:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v10 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$updateChatDefaultBannedRights$174(long r8, int r10, org.telegram.tgnet.TLRPC.TL_chatBannedRights r11) throws java.lang.Throwable {
        /*
            r7 = this;
            r0 = 0
            org.telegram.SQLite.SQLiteDatabase r1 = r7.database     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            java.util.Locale r2 = java.util.Locale.US     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            java.lang.String r3 = "SELECT data FROM chats WHERE uid = %d"
            java.lang.Long r8 = java.lang.Long.valueOf(r8)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            r9 = 1
            java.lang.Object[] r4 = new java.lang.Object[r9]     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8c
            r5 = 0
            r4[r5] = r8     // Catch: java.lang.Throwable -> L89 java.lang.Exception -> L8c
            java.lang.String r8 = java.lang.String.format(r2, r3, r4)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            java.lang.Object[] r2 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            org.telegram.SQLite.SQLiteCursor r8 = r1.queryFinalized(r8, r2)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            boolean r1 = r8.next()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L39
            if (r1 == 0) goto L3e
            org.telegram.tgnet.NativeByteBuffer r1 = r8.byteBufferValue(r5)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L39
            if (r1 == 0) goto L3e
            int r2 = r1.readInt32(r5)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L39
            org.telegram.tgnet.TLRPC$Chat r2 = org.telegram.tgnet.TLRPC.Chat.TLdeserialize(r1, r2, r5)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L39
            r1.reuse()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L39
            goto L3f
        L33:
            r9 = move-exception
            r6 = r0
            r0 = r8
            r8 = r6
            goto L9d
        L39:
            r9 = move-exception
            r6 = r0
            r0 = r8
            r8 = r6
            goto L8f
        L3e:
            r2 = r0
        L3f:
            r8.dispose()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L39
            if (r2 == 0) goto L9c
            org.telegram.tgnet.TLRPC$TL_chatBannedRights r8 = r2.default_banned_rights     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            if (r8 == 0) goto L53
            int r8 = r2.version     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            if (r10 >= r8) goto L53
            goto L9c
        L4d:
            r9 = move-exception
        L4e:
            r8 = r0
            goto L9d
        L50:
            r9 = move-exception
        L51:
            r8 = r0
            goto L8f
        L53:
            r2.default_banned_rights = r11     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            int r8 = r2.flags     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            r11 = 262144(0x40000, float:3.67342E-40)
            r8 = r8 | r11
            r2.flags = r8     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            r2.version = r10     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            org.telegram.SQLite.SQLiteDatabase r8 = r7.database     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            java.lang.String r10 = "UPDATE chats SET data = ? WHERE uid = ?"
            org.telegram.SQLite.SQLitePreparedStatement r8 = r8.executeFast(r10)     // Catch: java.lang.Throwable -> L4d java.lang.Exception -> L50
            org.telegram.tgnet.NativeByteBuffer r10 = new org.telegram.tgnet.NativeByteBuffer     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
            int r11 = r2.getObjectSize()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
            r10.<init>(r11)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
            r2.serializeToStream(r10)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
            r8.bindByteBuffer(r9, r10)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
            long r1 = r2.f1571id     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
            r9 = 2
            r8.bindLong(r9, r1)     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
            r8.step()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
            r10.reuse()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
            r8.dispose()     // Catch: java.lang.Throwable -> L85 java.lang.Exception -> L87
            return
        L85:
            r9 = move-exception
            goto L9d
        L87:
            r9 = move-exception
            goto L8f
        L89:
            r8 = move-exception
            r9 = r8
            goto L4e
        L8c:
            r8 = move-exception
            r9 = r8
            goto L51
        L8f:
            r7.checkSQLException(r9)     // Catch: java.lang.Throwable -> L85
            if (r0 == 0) goto L97
            r0.dispose()
        L97:
            if (r8 == 0) goto L9c
            r8.dispose()
        L9c:
            return
        L9d:
            if (r0 == 0) goto La2
            r0.dispose()
        La2:
            if (r8 == 0) goto La7
            r8.dispose()
        La7:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateChatDefaultBannedRights$174(long, int, org.telegram.tgnet.TLRPC$TL_chatBannedRights):void");
    }

    private void putChatsInternal(List<TLRPC.Chat> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO chats VALUES(?, ?, ?)");
        for (int i = 0; i < list.size(); i++) {
            TLRPC.Chat chat = list.get(i);
            if (chat.min) {
                SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM chats WHERE uid = %d", Long.valueOf(chat.f1571id)), new Object[0]);
                if (sQLiteCursorQueryFinalized.next()) {
                    try {
                        NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                        if (nativeByteBufferByteBufferValue != null) {
                            TLRPC.Chat chatTLdeserialize = TLRPC.Chat.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                            nativeByteBufferByteBufferValue.reuse();
                            if (chatTLdeserialize != null) {
                                chatTLdeserialize.title = chat.title;
                                chatTLdeserialize.photo = chat.photo;
                                chatTLdeserialize.broadcast = chat.broadcast;
                                chatTLdeserialize.verified = chat.verified;
                                chatTLdeserialize.megagroup = chat.megagroup;
                                chatTLdeserialize.call_not_empty = chat.call_not_empty;
                                chatTLdeserialize.call_active = chat.call_active;
                                chatTLdeserialize.monoforum = chat.monoforum;
                                chatTLdeserialize.broadcast_messages_allowed = chat.broadcast_messages_allowed;
                                if ((chat.flags2 & 262144) != 0) {
                                    chatTLdeserialize.linked_monoforum_id = chat.linked_monoforum_id;
                                    chatTLdeserialize.flags2 |= 262144;
                                }
                                TLRPC.TL_chatBannedRights tL_chatBannedRights = chat.default_banned_rights;
                                if (tL_chatBannedRights != null) {
                                    chatTLdeserialize.default_banned_rights = tL_chatBannedRights;
                                    chatTLdeserialize.flags |= 262144;
                                }
                                TLRPC.TL_chatAdminRights tL_chatAdminRights = chat.admin_rights;
                                if (tL_chatAdminRights != null) {
                                    chatTLdeserialize.admin_rights = tL_chatAdminRights;
                                    chatTLdeserialize.flags |= 16384;
                                }
                                TLRPC.TL_chatBannedRights tL_chatBannedRights2 = chat.banned_rights;
                                if (tL_chatBannedRights2 != null) {
                                    chatTLdeserialize.banned_rights = tL_chatBannedRights2;
                                    chatTLdeserialize.flags |= 32768;
                                }
                                String str = chat.username;
                                if (str != null) {
                                    chatTLdeserialize.username = str;
                                    chatTLdeserialize.flags |= 64;
                                } else {
                                    chatTLdeserialize.username = null;
                                    chatTLdeserialize.flags &= -65;
                                }
                                int i2 = chat.participants_count;
                                if (i2 > 0) {
                                    chatTLdeserialize.participants_count = i2;
                                }
                                chat = chatTLdeserialize;
                            }
                        }
                    } catch (Exception e) {
                        FileLog.m1160e(e);
                    }
                }
                sQLiteCursorQueryFinalized.dispose();
            }
            sQLitePreparedStatementExecuteFast.requery();
            chat.flags |= 131072;
            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(chat.getObjectSize());
            chat.serializeToStream(nativeByteBuffer);
            sQLitePreparedStatementExecuteFast.bindLong(1, chat.f1571id);
            String str2 = chat.title;
            if (str2 != null) {
                sQLitePreparedStatementExecuteFast.bindString(2, str2.toLowerCase());
            } else {
                sQLitePreparedStatementExecuteFast.bindString(2, "");
            }
            sQLitePreparedStatementExecuteFast.bindByteBuffer(3, nativeByteBuffer);
            sQLitePreparedStatementExecuteFast.step();
            nativeByteBuffer.reuse();
            isForumCacheInvalidate(-chat.f1571id);
        }
        sQLitePreparedStatementExecuteFast.dispose();
    }

    public void getUsersInternal(ArrayList<Long> arrayList, ArrayList<TLRPC.User> arrayList2) {
        getUsersInternal(arrayList, arrayList2, false);
    }

    public void getUsersInternal(ArrayList<Long> arrayList, ArrayList<TLRPC.User> arrayList2, boolean z) {
        if (arrayList == null || arrayList.isEmpty() || arrayList2 == null) {
            return;
        }
        if (arrayList.size() > 50) {
            int i = 0;
            while (i < arrayList.size()) {
                Long l = arrayList.get(i);
                l.longValue();
                TLRPC.User user = getMessagesController().getUser(l);
                if (user != null) {
                    arrayList2.add(user);
                    arrayList.remove(i);
                    i--;
                }
                i++;
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data, status FROM users WHERE uid IN(%s)", TextUtils.join(",", arrayList)), new Object[0]);
        while (sQLiteCursorQueryFinalized.next()) {
            try {
                NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                if (nativeByteBufferByteBufferValue != null) {
                    TLRPC.User userTLdeserialize = TLRPC.User.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                    nativeByteBufferByteBufferValue.reuse();
                    if (userTLdeserialize != null) {
                        TLRPC.UserStatus userStatus = userTLdeserialize.status;
                        if (userStatus != null) {
                            userStatus.expires = sQLiteCursorQueryFinalized.intValue(1);
                        }
                        arrayList2.add(userTLdeserialize);
                        if (arrayList.size() > 50 && z) {
                            getMessagesController().putUser(userTLdeserialize, true, false);
                        }
                    }
                }
            } catch (Exception e) {
                checkSQLException(e);
            }
        }
        sQLiteCursorQueryFinalized.dispose();
    }

    public void getUsersInternal(HashSet<Long> hashSet, ArrayList<TLRPC.User> arrayList) {
        if (hashSet == null || hashSet.isEmpty() || arrayList == null) {
            return;
        }
        if (hashSet.size() > 50) {
            Iterator<Long> it = hashSet.iterator();
            while (it.hasNext()) {
                TLRPC.User user = getMessagesController().getUser(it.next());
                if (user != null) {
                    arrayList.add(user);
                    it.remove();
                }
            }
        }
        if (hashSet.isEmpty()) {
            return;
        }
        SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data, status FROM users WHERE uid IN(%s)", TextUtils.join(",", hashSet)), new Object[0]);
        while (sQLiteCursorQueryFinalized.next()) {
            try {
                NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                if (nativeByteBufferByteBufferValue != null) {
                    TLRPC.User userTLdeserialize = TLRPC.User.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                    nativeByteBufferByteBufferValue.reuse();
                    if (userTLdeserialize != null) {
                        TLRPC.UserStatus userStatus = userTLdeserialize.status;
                        if (userStatus != null) {
                            userStatus.expires = sQLiteCursorQueryFinalized.intValue(1);
                        }
                        arrayList.add(userTLdeserialize);
                    }
                }
            } catch (Exception e) {
                checkSQLException(e);
            }
        }
        sQLiteCursorQueryFinalized.dispose();
    }

    public void getChatsInternal(String str, ArrayList<TLRPC.Chat> arrayList) {
        getChatsInternal(str, arrayList, true);
    }

    public void getChatsInternal(String str, ArrayList<TLRPC.Chat> arrayList, boolean z) {
        if (str == null || str.length() == 0 || arrayList == null) {
            return;
        }
        SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM chats WHERE uid IN(%s)", str), new Object[0]);
        ArrayList arrayList2 = null;
        while (sQLiteCursorQueryFinalized.next()) {
            try {
                NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                if (nativeByteBufferByteBufferValue != null) {
                    TLRPC.Chat chatTLdeserialize = TLRPC.Chat.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false, z);
                    nativeByteBufferByteBufferValue.reuse();
                    if (chatTLdeserialize != null) {
                        arrayList.add(chatTLdeserialize);
                        if (chatTLdeserialize.linked_monoforum_id != 0) {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            arrayList2.add(Long.valueOf(chatTLdeserialize.linked_monoforum_id));
                        }
                    }
                }
            } catch (Exception e) {
                checkSQLException(e);
            }
        }
        sQLiteCursorQueryFinalized.dispose();
        if (arrayList2 != null) {
            SQLiteCursor sQLiteCursorQueryFinalized2 = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM chats WHERE uid IN(%s)", TextUtils.join(", ", arrayList2)), new Object[0]);
            while (sQLiteCursorQueryFinalized2.next()) {
                try {
                    NativeByteBuffer nativeByteBufferByteBufferValue2 = sQLiteCursorQueryFinalized2.byteBufferValue(0);
                    if (nativeByteBufferByteBufferValue2 != null) {
                        TLRPC.Chat chatTLdeserialize2 = TLRPC.Chat.TLdeserialize(nativeByteBufferByteBufferValue2, nativeByteBufferByteBufferValue2.readInt32(false), false, z);
                        nativeByteBufferByteBufferValue2.reuse();
                        if (chatTLdeserialize2 != null) {
                            arrayList.add(chatTLdeserialize2);
                        }
                    }
                } catch (Exception e2) {
                    checkSQLException(e2);
                }
            }
            sQLiteCursorQueryFinalized2.dispose();
        }
    }

    public void getEncryptedChatsInternal(String str, ArrayList<TLRPC.EncryptedChat> arrayList, ArrayList<Long> arrayList2) {
        if (str == null || str.length() == 0 || arrayList == null) {
            return;
        }
        SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data, user, g, authkey, ttl, layer, seq_in, seq_out, use_count, exchange_id, key_date, fprint, fauthkey, khash, in_seq_no, admin_id, mtproto_seq FROM enc_chats WHERE uid IN(%s)", str), new Object[0]);
        while (sQLiteCursorQueryFinalized.next()) {
            try {
                NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                if (nativeByteBufferByteBufferValue != null) {
                    TLRPC.EncryptedChat encryptedChatTLdeserialize = TLRPC.EncryptedChat.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                    nativeByteBufferByteBufferValue.reuse();
                    if (encryptedChatTLdeserialize != null) {
                        long jLongValue = sQLiteCursorQueryFinalized.longValue(1);
                        encryptedChatTLdeserialize.user_id = jLongValue;
                        if (arrayList2 != null && !arrayList2.contains(Long.valueOf(jLongValue))) {
                            arrayList2.add(Long.valueOf(encryptedChatTLdeserialize.user_id));
                        }
                        encryptedChatTLdeserialize.a_or_b = sQLiteCursorQueryFinalized.byteArrayValue(2);
                        encryptedChatTLdeserialize.auth_key = sQLiteCursorQueryFinalized.byteArrayValue(3);
                        encryptedChatTLdeserialize.ttl = sQLiteCursorQueryFinalized.intValue(4);
                        encryptedChatTLdeserialize.layer = sQLiteCursorQueryFinalized.intValue(5);
                        encryptedChatTLdeserialize.seq_in = sQLiteCursorQueryFinalized.intValue(6);
                        encryptedChatTLdeserialize.seq_out = sQLiteCursorQueryFinalized.intValue(7);
                        int iIntValue = sQLiteCursorQueryFinalized.intValue(8);
                        encryptedChatTLdeserialize.key_use_count_in = (short) (iIntValue >> 16);
                        encryptedChatTLdeserialize.key_use_count_out = (short) iIntValue;
                        encryptedChatTLdeserialize.exchange_id = sQLiteCursorQueryFinalized.longValue(9);
                        encryptedChatTLdeserialize.key_create_date = sQLiteCursorQueryFinalized.intValue(10);
                        encryptedChatTLdeserialize.future_key_fingerprint = sQLiteCursorQueryFinalized.longValue(11);
                        encryptedChatTLdeserialize.future_auth_key = sQLiteCursorQueryFinalized.byteArrayValue(12);
                        encryptedChatTLdeserialize.key_hash = sQLiteCursorQueryFinalized.byteArrayValue(13);
                        encryptedChatTLdeserialize.in_seq_no = sQLiteCursorQueryFinalized.intValue(14);
                        long jLongValue2 = sQLiteCursorQueryFinalized.longValue(15);
                        if (jLongValue2 != 0) {
                            encryptedChatTLdeserialize.admin_id = jLongValue2;
                        }
                        encryptedChatTLdeserialize.mtproto_seq = sQLiteCursorQueryFinalized.intValue(16);
                        arrayList.add(encryptedChatTLdeserialize);
                    }
                }
            } catch (Exception e) {
                checkSQLException(e);
            }
        }
        sQLiteCursorQueryFinalized.dispose();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: putUsersAndChatsInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$putUsersAndChats$175(List<TLRPC.User> list, List<TLRPC.Chat> list2, boolean z) {
        if (z) {
            try {
                try {
                    this.database.beginTransaction();
                } catch (Exception e) {
                    checkSQLException(e);
                    SQLiteDatabase sQLiteDatabase = this.database;
                    if (sQLiteDatabase != null) {
                        sQLiteDatabase.commitTransaction();
                        return;
                    }
                    return;
                }
            } catch (Throwable th) {
                SQLiteDatabase sQLiteDatabase2 = this.database;
                if (sQLiteDatabase2 != null) {
                    sQLiteDatabase2.commitTransaction();
                }
                throw th;
            }
        }
        putUsersInternal(list);
        putChatsInternal(list2);
        SQLiteDatabase sQLiteDatabase3 = this.database;
        if (sQLiteDatabase3 != null) {
            sQLiteDatabase3.commitTransaction();
        }
    }

    public void putUsersAndChats(final List<TLRPC.User> list, final List<TLRPC.Chat> list2, final boolean z, boolean z2) {
        if (list == null || !list.isEmpty() || list2 == null || !list2.isEmpty()) {
            if (z2) {
                this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda170
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$putUsersAndChats$175(list, list2, z);
                    }
                });
            } else {
                lambda$putUsersAndChats$175(list, list2, z);
            }
        }
    }

    public void removeFromDownloadQueue(final long j, final int i, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda163
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$removeFromDownloadQueue$176(z, i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$removeFromDownloadQueue$176(boolean z, int i, long j) throws Throwable {
        Throwable th;
        Exception e;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                try {
                    if (!z) {
                        this.database.executeFast(String.format(Locale.US, "DELETE FROM download_queue WHERE uid = %d AND type = %d", Long.valueOf(j), Integer.valueOf(i))).stepThis().dispose();
                        return;
                    }
                    SQLiteDatabase sQLiteDatabase = this.database;
                    Locale locale = Locale.US;
                    SQLiteCursor sQLiteCursorQueryFinalized = sQLiteDatabase.queryFinalized(String.format(locale, "SELECT min(date) FROM download_queue WHERE type = %d", Integer.valueOf(i)), new Object[0]);
                    try {
                        int iIntValue = sQLiteCursorQueryFinalized.next() ? sQLiteCursorQueryFinalized.intValue(0) : -1;
                        sQLiteCursorQueryFinalized.dispose();
                        if (iIntValue != -1) {
                            this.database.executeFast(String.format(locale, "UPDATE download_queue SET date = %d WHERE uid = %d AND type = %d", Integer.valueOf(iIntValue - 1), Long.valueOf(j), Integer.valueOf(i))).stepThis().dispose();
                        }
                    } catch (Exception e2) {
                        e = e2;
                        sQLiteCursor = sQLiteCursorQueryFinalized;
                        checkSQLException(e);
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        sQLiteCursor = sQLiteCursorQueryFinalized;
                        if (sQLiteCursor != null) {
                            sQLiteCursor.dispose();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Exception e4) {
            e = e4;
        } catch (Throwable th4) {
            th = th4;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void deleteFromDownloadQueue(final java.util.ArrayList<android.util.Pair<java.lang.Long, java.lang.Integer>> r9, boolean r10) throws java.lang.Throwable {
        /*
            r8 = this;
            if (r9 == 0) goto L91
            boolean r0 = r9.isEmpty()
            if (r0 == 0) goto La
            goto L91
        La:
            r0 = 0
            if (r10 == 0) goto L18
            org.telegram.SQLite.SQLiteDatabase r1 = r8.database     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L16
            r1.beginTransaction()     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L16
            goto L18
        L13:
            r9 = move-exception
            goto L82
        L16:
            r9 = move-exception
            goto L70
        L18:
            org.telegram.SQLite.SQLiteDatabase r1 = r8.database     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L16
            java.lang.String r2 = "DELETE FROM download_queue WHERE uid = ? AND type = ?"
            org.telegram.SQLite.SQLitePreparedStatement r1 = r1.executeFast(r2)     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L16
            int r2 = r9.size()     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            r3 = 0
        L25:
            if (r3 >= r2) goto L54
            java.lang.Object r4 = r9.get(r3)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            android.util.Pair r4 = (android.util.Pair) r4     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            r1.requery()     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            java.lang.Object r5 = r4.first     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            java.lang.Long r5 = (java.lang.Long) r5     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            long r5 = r5.longValue()     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            r7 = 1
            r1.bindLong(r7, r5)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            java.lang.Object r4 = r4.second     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            java.lang.Integer r4 = (java.lang.Integer) r4     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            int r4 = r4.intValue()     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            r5 = 2
            r1.bindInteger(r5, r4)     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            r1.step()     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            int r3 = r3 + 1
            goto L25
        L4e:
            r9 = move-exception
            r0 = r1
            goto L82
        L51:
            r9 = move-exception
            r0 = r1
            goto L70
        L54:
            r1.dispose()     // Catch: java.lang.Throwable -> L4e java.lang.Exception -> L51
            if (r10 == 0) goto L5e
            org.telegram.SQLite.SQLiteDatabase r1 = r8.database     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L16
            r1.commitTransaction()     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L16
        L5e:
            org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda132 r1 = new org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda132     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L16
            r1.<init>()     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L16
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r1)     // Catch: java.lang.Throwable -> L13 java.lang.Exception -> L16
            if (r10 == 0) goto L91
            org.telegram.SQLite.SQLiteDatabase r9 = r8.database
            if (r9 == 0) goto L91
            r9.commitTransaction()
            return
        L70:
            r8.checkSQLException(r9)     // Catch: java.lang.Throwable -> L13
            if (r0 == 0) goto L78
            r0.dispose()
        L78:
            if (r10 == 0) goto L91
            org.telegram.SQLite.SQLiteDatabase r9 = r8.database
            if (r9 == 0) goto L91
            r9.commitTransaction()
            goto L91
        L82:
            if (r0 == 0) goto L87
            r0.dispose()
        L87:
            if (r10 == 0) goto L90
            org.telegram.SQLite.SQLiteDatabase r10 = r8.database
            if (r10 == 0) goto L90
            r10.commitTransaction()
        L90:
            throw r9
        L91:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.deleteFromDownloadQueue(java.util.ArrayList, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$deleteFromDownloadQueue$177(ArrayList arrayList) {
        getDownloadController().cancelDownloading(arrayList);
    }

    public void clearDownloadQueue(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda162
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$clearDownloadQueue$178(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$clearDownloadQueue$178(int i) {
        try {
            if (i == 0) {
                this.database.executeFast("DELETE FROM download_queue WHERE 1").stepThis().dispose();
            } else {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM download_queue WHERE type = %d", Integer.valueOf(i))).stepThis().dispose();
            }
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void getDownloadQueue(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda196
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$getDownloadQueue$180(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$getDownloadQueue$180(final int r11) throws java.lang.Throwable {
        /*
            r10 = this;
            r0 = 0
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            r1.<init>()     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            org.telegram.SQLite.SQLiteDatabase r2 = r10.database     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            java.util.Locale r3 = java.util.Locale.US     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            java.lang.String r4 = "SELECT uid, type, data, parent FROM download_queue WHERE type = %d ORDER BY date DESC LIMIT 3"
            java.lang.Integer r5 = java.lang.Integer.valueOf(r11)     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            r6 = 1
            java.lang.Object[] r7 = new java.lang.Object[r6]     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            r8 = 0
            r7[r8] = r5     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            java.lang.String r3 = java.lang.String.format(r3, r4, r7)     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            java.lang.Object[] r4 = new java.lang.Object[r8]     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            org.telegram.SQLite.SQLiteCursor r2 = r2.queryFinalized(r3, r4)     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
        L20:
            boolean r3 = r2.next()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            if (r3 == 0) goto Laa
            org.telegram.messenger.DownloadObject r3 = new org.telegram.messenger.DownloadObject     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r3.<init>()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            int r4 = r2.intValue(r6)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r3.type = r4     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            long r4 = r2.longValue(r8)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r3.f1437id = r4     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r4 = 3
            java.lang.String r4 = r2.stringValue(r4)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r3.parent = r4     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r4 = 2
            org.telegram.tgnet.NativeByteBuffer r4 = r2.byteBufferValue(r4)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            if (r4 == 0) goto La5
            int r5 = r4.readInt32(r8)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            org.telegram.tgnet.TLRPC$MessageMedia r5 = org.telegram.tgnet.TLRPC.MessageMedia.TLdeserialize(r4, r5, r8)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r4.reuse()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            org.telegram.tgnet.TLRPC$Document r4 = r5.document     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r7 = 2147483647(0x7fffffff, float:NaN)
            r9 = 60
            if (r4 == 0) goto L86
            r3.object = r4     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            boolean r4 = org.telegram.messenger.MessageObject.isVideoDocument(r4)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            if (r4 != 0) goto L78
            org.telegram.tgnet.TLRPC$Document r4 = r5.document     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            boolean r4 = org.telegram.messenger.MessageObject.isVoiceDocument(r4)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            if (r4 != 0) goto L78
            org.telegram.tgnet.TLRPC$Document r4 = r5.document     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            boolean r4 = org.telegram.messenger.MessageObject.isRoundVideoDocument(r4)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            if (r4 == 0) goto L82
            goto L78
        L72:
            r11 = move-exception
            r0 = r2
            goto Lc2
        L75:
            r11 = move-exception
            r0 = r2
            goto Lb9
        L78:
            int r4 = r5.ttl_seconds     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            if (r4 <= 0) goto L7e
            if (r4 <= r9) goto L80
        L7e:
            if (r4 != r7) goto L82
        L80:
            r4 = 1
            goto L83
        L82:
            r4 = 0
        L83:
            r3.secret = r4     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            goto L99
        L86:
            org.telegram.tgnet.TLRPC$Photo r4 = r5.photo     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            if (r4 == 0) goto L99
            r3.object = r4     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            int r4 = r5.ttl_seconds     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            if (r4 <= 0) goto L92
            if (r4 <= r9) goto L94
        L92:
            if (r4 != r7) goto L96
        L94:
            r4 = 1
            goto L97
        L96:
            r4 = 0
        L97:
            r3.secret = r4     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
        L99:
            int r4 = r5.flags     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r4 & r5
            if (r4 == 0) goto La2
            r4 = 1
            goto La3
        La2:
            r4 = 0
        La3:
            r3.forceCache = r4     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
        La5:
            r1.add(r3)     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            goto L20
        Laa:
            r2.dispose()     // Catch: java.lang.Throwable -> L72 java.lang.Exception -> L75
            org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda135 r2 = new org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda135     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            r2.<init>()     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r2)     // Catch: java.lang.Throwable -> Lb6 java.lang.Exception -> Lb8
            return
        Lb6:
            r11 = move-exception
            goto Lc2
        Lb8:
            r11 = move-exception
        Lb9:
            r10.checkSQLException(r11)     // Catch: java.lang.Throwable -> Lb6
            if (r0 == 0) goto Lc1
            r0.dispose()
        Lc1:
            return
        Lc2:
            if (r0 == 0) goto Lc7
            r0.dispose()
        Lc7:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getDownloadQueue$180(int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDownloadQueue$179(int i, ArrayList arrayList) {
        getDownloadController().processDownloadObjects(i, arrayList);
    }

    public int getMessageMediaType(TLRPC.Message message) {
        if (message instanceof TLRPC.TL_message_secret) {
            if (!(message.media instanceof TLRPC.TL_messageMediaPhoto) && !MessageObject.isGifMessage(message) && !MessageObject.isVoiceMessage(message) && !MessageObject.isVideoMessage(message) && !MessageObject.isRoundVideoMessage(message)) {
                return -1;
            }
            int i = message.ttl;
            return (i <= 0 || i > 60) ? 0 : 1;
        }
        if (message instanceof TLRPC.TL_message) {
            TLRPC.MessageMedia messageMedia = message.media;
            if (((messageMedia instanceof TLRPC.TL_messageMediaPhoto) || (messageMedia instanceof TLRPC.TL_messageMediaDocument)) && messageMedia.ttl_seconds != 0) {
                return 1;
            }
        }
        return ((message.media instanceof TLRPC.TL_messageMediaPhoto) || MessageObject.isVideoMessage(message)) ? 0 : -1;
    }

    public void putWebPages(final LongSparseArray longSparseArray) {
        if (isEmpty(longSparseArray)) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda248
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$putWebPages$182(longSparseArray);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:127:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$putWebPages$182(androidx.collection.LongSparseArray r20) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 475
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putWebPages$182(androidx.collection.LongSparseArray):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putWebPages$181(ArrayList arrayList) {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.didReceivedWebpages, arrayList);
    }

    public void overwriteChannel(final long j, final TLRPC.TL_updates_channelDifferenceTooLong tL_updates_channelDifferenceTooLong, final int i, final Runnable runnable) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda218
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$overwriteChannel$184(j, i, tL_updates_channelDifferenceTooLong, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0260 A[Catch: all -> 0x0253, Exception -> 0x0255, TRY_LEAVE, TryCatch #1 {all -> 0x0253, blocks: (B:3:0x0007, B:12:0x0037, B:16:0x0227, B:19:0x024b, B:24:0x0257, B:26:0x0260, B:32:0x0271), top: B:41:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x027b  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$overwriteChannel$184(long r15, int r17, final org.telegram.tgnet.TLRPC.TL_updates_channelDifferenceTooLong r18, java.lang.Runnable r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 645
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$overwriteChannel$184(long, int, org.telegram.tgnet.TLRPC$TL_updates_channelDifferenceTooLong, java.lang.Runnable):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$overwriteChannel$183(long j, TLRPC.TL_updates_channelDifferenceTooLong tL_updates_channelDifferenceTooLong) {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.removeAllMessagesFromDialog, Long.valueOf(j), Boolean.TRUE, tL_updates_channelDifferenceTooLong);
    }

    public void putChannelViews(final LongSparseArray longSparseArray, final LongSparseArray longSparseArray2, final LongSparseArray longSparseArray3, final boolean z) {
        if (isEmpty(longSparseArray) && isEmpty(longSparseArray2) && isEmpty(longSparseArray3)) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda94
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$putChannelViews$185(longSparseArray, longSparseArray2, longSparseArray3, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0243  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x024c  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:160:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00cf A[Catch: all -> 0x006d, Exception -> 0x0070, TRY_LEAVE, TryCatch #13 {Exception -> 0x0070, all -> 0x006d, blocks: (B:5:0x000e, B:7:0x0016, B:28:0x0075, B:30:0x007b, B:43:0x00c9, B:45:0x00cf, B:111:0x022f), top: B:135:0x000e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$putChannelViews$185(androidx.collection.LongSparseArray r25, androidx.collection.LongSparseArray r26, androidx.collection.LongSparseArray r27, boolean r28) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 608
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putChannelViews$185(androidx.collection.LongSparseArray, androidx.collection.LongSparseArray, androidx.collection.LongSparseArray, boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x02a7  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x02b3  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02b8  */
    /* JADX WARN: Removed duplicated region for block: B:124:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:125:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x012c A[Catch: all -> 0x0096, Exception -> 0x0099, TRY_LEAVE, TryCatch #8 {Exception -> 0x0099, all -> 0x0096, blocks: (B:3:0x000a, B:5:0x0014, B:32:0x009c, B:46:0x00d8, B:48:0x012c, B:90:0x01f9, B:93:0x01ff, B:94:0x0223, B:96:0x0296, B:95:0x0260, B:60:0x016f, B:66:0x01a3, B:68:0x01a9), top: B:122:0x000a }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void updateRepliesMaxReadIdInternal(final long r23, final long r25, final int r27, int r28) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 700
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.updateRepliesMaxReadIdInternal(long, long, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRepliesMaxReadIdInternal$186(long j, long j2, int i, int i2, int i3) {
        getMessagesController().getTopicsController().updateMaxReadId(-j, j2, i, i2, i3);
    }

    private void resetForumBadgeIfNeed(long j) {
        LongSparseIntArray longSparseIntArray;
        SQLiteCursor sQLiteCursor = null;
        try {
            SQLiteDatabase sQLiteDatabase = this.database;
            Locale locale = Locale.ENGLISH;
            SQLiteCursor sQLiteCursorQueryFinalized = sQLiteDatabase.queryFinalized(String.format(locale, "SELECT topic_id FROM topics WHERE did = %d AND unread_count > 0", Long.valueOf(j)), new Object[0]);
            try {
                if (sQLiteCursorQueryFinalized.next()) {
                    longSparseIntArray = null;
                } else {
                    longSparseIntArray = new LongSparseIntArray();
                    longSparseIntArray.put(j, 0);
                }
                sQLiteCursorQueryFinalized.dispose();
                if (longSparseIntArray != null) {
                    this.database.executeFast(String.format(locale, "UPDATE dialogs SET unread_count = 0, unread_count_i = 0 WHERE did = %d", Long.valueOf(j))).stepThis().dispose();
                }
                updateFiltersReadCounter(longSparseIntArray, null, true);
                getMessagesController().processDialogsUpdateRead(longSparseIntArray, null);
            } catch (Throwable th) {
                th = th;
                sQLiteCursor = sQLiteCursorQueryFinalized;
                try {
                    checkSQLException(th);
                } finally {
                    if (sQLiteCursor != null) {
                        sQLiteCursor.dispose();
                    }
                }
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateRepliesMaxReadId$187(long j, long j2, int i, int i2) throws Throwable {
        updateRepliesMaxReadIdInternal(-j, j2, i, i2);
    }

    public void updateRepliesMaxReadId(final long j, final long j2, final int i, final int i2, boolean z) {
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda233
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$updateRepliesMaxReadId$187(j, j2, i, i2);
                }
            });
        } else {
            updateRepliesMaxReadIdInternal(-j, j2, i, i2);
        }
    }

    public void updateRepliesCount(final long j, final int i, final ArrayList<TLRPC.Peer> arrayList, final int i2, final int i3) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda251
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateRepliesCount$188(i, j, i3, arrayList, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$updateRepliesCount$188(int r15, long r16, int r18, java.util.ArrayList r19, int r20) throws java.lang.Throwable {
        /*
            r14 = this;
            r0 = r19
            r1 = r20
            r2 = 0
            org.telegram.SQLite.SQLiteDatabase r3 = r14.database     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9f
            java.lang.String r4 = "UPDATE messages_v2 SET replies_data = ? WHERE mid = ? AND uid = ?"
            org.telegram.SQLite.SQLitePreparedStatement r3 = r3.executeFast(r4)     // Catch: java.lang.Throwable -> L9b java.lang.Exception -> L9f
            org.telegram.SQLite.SQLiteDatabase r4 = r14.database     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            java.util.Locale r5 = java.util.Locale.ENGLISH     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            java.lang.String r6 = "SELECT replies_data FROM messages_v2 WHERE mid = %d AND uid = %d"
            java.lang.Integer r7 = java.lang.Integer.valueOf(r15)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r8 = r16
            long r8 = -r8
            java.lang.Long r10 = java.lang.Long.valueOf(r8)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r11 = 2
            java.lang.Object[] r12 = new java.lang.Object[r11]     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r13 = 0
            r12[r13] = r7     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r7 = 1
            r12[r7] = r10     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            java.lang.String r5 = java.lang.String.format(r5, r6, r12)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            java.lang.Object[] r6 = new java.lang.Object[r13]     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            org.telegram.SQLite.SQLiteCursor r4 = r4.queryFinalized(r5, r6)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            boolean r5 = r4.next()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4e
            if (r5 == 0) goto L52
            org.telegram.tgnet.NativeByteBuffer r5 = r4.byteBufferValue(r13)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4e
            if (r5 == 0) goto L52
            int r6 = r5.readInt32(r13)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4e
            org.telegram.tgnet.TLRPC$MessageReplies r6 = org.telegram.tgnet.TLRPC.MessageReplies.TLdeserialize(r5, r6, r13)     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4e
            r5.reuse()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4e
            goto L53
        L49:
            r0 = move-exception
            r15 = r0
        L4b:
            r2 = r3
            goto Lb2
        L4e:
            r0 = move-exception
            r15 = r0
        L50:
            r2 = r3
            goto La2
        L52:
            r6 = r2
        L53:
            r4.dispose()     // Catch: java.lang.Throwable -> L49 java.lang.Exception -> L4e
            if (r6 == 0) goto L97
            int r4 = r6.replies     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            int r4 = r4 + r18
            r6.replies = r4     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            if (r4 >= 0) goto L6b
            r6.replies = r13     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            goto L6b
        L63:
            r0 = move-exception
            r15 = r0
            r4 = r2
            goto L4b
        L67:
            r0 = move-exception
            r15 = r0
            r4 = r2
            goto L50
        L6b:
            if (r0 == 0) goto L74
            r6.recent_repliers = r0     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            int r0 = r6.flags     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r0 = r0 | r11
            r6.flags = r0     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
        L74:
            if (r1 == 0) goto L78
            r6.max_id = r1     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
        L78:
            r3.requery()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            org.telegram.tgnet.NativeByteBuffer r0 = new org.telegram.tgnet.NativeByteBuffer     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            int r1 = r6.getObjectSize()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r6.serializeToStream(r0)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r3.bindByteBuffer(r7, r0)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r3.bindInteger(r11, r15)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r15 = 3
            r3.bindLong(r15, r8)     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r3.step()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            r0.reuse()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
        L97:
            r3.dispose()     // Catch: java.lang.Throwable -> L63 java.lang.Exception -> L67
            return
        L9b:
            r0 = move-exception
            r15 = r0
            r4 = r2
            goto Lb2
        L9f:
            r0 = move-exception
            r15 = r0
            r4 = r2
        La2:
            r14.checkSQLException(r15)     // Catch: java.lang.Throwable -> Lb0
            if (r2 == 0) goto Laa
            r2.dispose()
        Laa:
            if (r4 == 0) goto Laf
            r4.dispose()
        Laf:
            return
        Lb0:
            r0 = move-exception
            r15 = r0
        Lb2:
            if (r2 == 0) goto Lb7
            r2.dispose()
        Lb7:
            if (r4 == 0) goto Lbc
            r4.dispose()
        Lbc:
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateRepliesCount$188(int, long, int, java.util.ArrayList, int):void");
    }

    private boolean isValidKeyboardToSave(TLRPC.Message message) {
        TLRPC.ReplyMarkup replyMarkup = message.reply_markup;
        if (replyMarkup == null || (replyMarkup instanceof TLRPC.TL_replyInlineMarkup)) {
            return false;
        }
        return !replyMarkup.selective || message.mentioned;
    }

    public void updateMessageVerifyFlags(final ArrayList<TLRPC.Message> arrayList) {
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda149
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateMessageVerifyFlags$189(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageVerifyFlags$189(ArrayList arrayList) throws Throwable {
        SQLiteDatabase sQLiteDatabase;
        SQLiteDatabase sQLiteDatabase2;
        boolean z = false;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.beginTransaction();
                try {
                    SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE messages_v2 SET imp = ? WHERE mid = ? AND uid = ?");
                    try {
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            TLRPC.Message message = (TLRPC.Message) arrayList.get(i);
                            sQLitePreparedStatementExecuteFast.requery();
                            int i2 = message.stickerVerified;
                            sQLitePreparedStatementExecuteFast.bindInteger(1, i2 == 0 ? 1 : i2 == 2 ? 2 : 0);
                            sQLitePreparedStatementExecuteFast.bindInteger(2, message.f1597id);
                            sQLitePreparedStatementExecuteFast.bindLong(3, MessageObject.getDialogId(message));
                            sQLitePreparedStatementExecuteFast.step();
                        }
                        sQLitePreparedStatementExecuteFast.dispose();
                        this.database.commitTransaction();
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        z = true;
                        checkSQLException(e);
                        if (z && (sQLiteDatabase2 = this.database) != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        z = true;
                        if (z && (sQLiteDatabase = this.database) != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                } catch (Exception e2) {
                    e = e2;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:96)
        */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Not initialized variable reg: 67, insn: 0x021c: MOVE (r15 I:??[OBJECT, ARRAY]) = (r67 I:??[OBJECT, ARRAY]), block:B:116:0x021c */
    /* JADX WARN: Not initialized variable reg: 67, insn: 0x0222: MOVE (r15 I:??[OBJECT, ARRAY]) = (r67 I:??[OBJECT, ARRAY]), block:B:119:0x0222 */
    /* renamed from: putMessagesInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$putMessages$194(java.util.ArrayList<org.telegram.tgnet.TLRPC.Message> r60, boolean r61, boolean r62, int r63, boolean r64, int r65, long r66) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 8345
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putMessages$194(java.util.ArrayList, boolean, boolean, int, boolean, int, long):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putMessagesInternal$190(int i) {
        getDownloadController().newDownloadObjectsAvailable(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putMessagesInternal$191(ArrayList arrayList) {
        if (getMessagesController().getSavedMessagesController().updateSavedDialogs(arrayList)) {
            getMessagesController().getSavedMessagesController().update();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putMessagesInternal$192(ArrayList arrayList) {
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Pair pair = (Pair) obj;
            getMessagesController().reportMessageDelivery(((Long) pair.first).longValue(), ((Integer) pair.second).intValue(), false);
        }
    }

    private void createOrEditTopic(final long j, TLRPC.Message message) throws Throwable {
        final TLRPC.TL_forumTopic tL_forumTopic = new TLRPC.TL_forumTopic();
        tL_forumTopic.topicStartMessage = message;
        tL_forumTopic.top_message = message.f1597id;
        tL_forumTopic.topMessage = message;
        tL_forumTopic.from_id = message.from_id;
        tL_forumTopic.notify_settings = new TLRPC.TL_peerNotifySettings();
        tL_forumTopic.unread_count = 0;
        TLRPC.MessageAction messageAction = message.action;
        if (messageAction instanceof TLRPC.TL_messageActionTopicCreate) {
            TLRPC.TL_messageActionTopicCreate tL_messageActionTopicCreate = (TLRPC.TL_messageActionTopicCreate) messageAction;
            tL_forumTopic.f1631id = message.f1597id;
            long j2 = tL_messageActionTopicCreate.icon_emoji_id;
            tL_forumTopic.icon_emoji_id = j2;
            tL_forumTopic.title = tL_messageActionTopicCreate.title;
            tL_forumTopic.icon_color = tL_messageActionTopicCreate.icon_color;
            if (j2 != 0) {
                tL_forumTopic.flags |= 1;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(tL_forumTopic);
            saveTopics(j, arrayList, false, false, message.date);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda49
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createOrEditTopic$193(j, tL_forumTopic);
                }
            });
            return;
        }
        if (messageAction instanceof TLRPC.TL_messageActionTopicEdit) {
            TLRPC.TL_messageActionTopicEdit tL_messageActionTopicEdit = (TLRPC.TL_messageActionTopicEdit) messageAction;
            tL_forumTopic.f1631id = (int) MessageObject.getTopicId(this.currentAccount, message, true);
            tL_forumTopic.icon_emoji_id = tL_messageActionTopicEdit.icon_emoji_id;
            tL_forumTopic.title = tL_messageActionTopicEdit.title;
            tL_forumTopic.closed = tL_messageActionTopicEdit.closed;
            tL_forumTopic.hidden = tL_messageActionTopicEdit.hidden;
            int i = tL_messageActionTopicEdit.flags;
            int i2 = (i & 1) != 0 ? 1 : 0;
            if ((i & 2) != 0) {
                i2 += 2;
            }
            if ((i & 4) != 0) {
                i2 += 8;
            }
            if ((i & 8) != 0) {
                i2 += 32;
            }
            updateTopicData(j, tL_forumTopic, i2, message.date);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createOrEditTopic$193(long j, TLRPC.TL_forumTopic tL_forumTopic) {
        getMessagesController().getTopicsController().onTopicCreated(j, tL_forumTopic, false);
    }

    public void putMessages(ArrayList<TLRPC.Message> arrayList, boolean z, boolean z2, boolean z3, int i, int i2, long j) {
        putMessages(arrayList, z, z2, z3, i, false, i2, j);
    }

    public void putMessages(final ArrayList<TLRPC.Message> arrayList, final boolean z, boolean z2, final boolean z3, final int i, final boolean z4, final int i2, final long j) {
        if (arrayList.size() == 0) {
            return;
        }
        if (z2) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda51
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$putMessages$194(arrayList, z, z3, i, z4, i2, j);
                }
            });
        } else {
            lambda$putMessages$194(arrayList, z, z3, i, z4, i2, j);
        }
    }

    public void markMessageAsSendError(final TLRPC.Message message, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda178
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$markMessageAsSendError$195(i, message);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessageAsSendError$195(int i, TLRPC.Message message) {
        try {
            long j = message.f1597id;
            if (MessageObject.isQuickReply(message)) {
                i = 5;
            }
            if (i == 5) {
                this.database.executeFast(String.format(Locale.US, "UPDATE quick_replies_messages SET send_state = 2 WHERE mid = %d AND topic_id = %d", Long.valueOf(j), Integer.valueOf(MessageObject.getQuickReplyId(this.currentAccount, message)))).stepThis().dispose();
                return;
            }
            if (i == 1) {
                this.database.executeFast(String.format(Locale.US, "UPDATE scheduled_messages_v2 SET send_state = 2 WHERE mid = %d AND uid = %d", Long.valueOf(j), Long.valueOf(MessageObject.getDialogId(message)))).stepThis().dispose();
                return;
            }
            SQLiteDatabase sQLiteDatabase = this.database;
            Locale locale = Locale.US;
            sQLiteDatabase.executeFast(String.format(locale, "UPDATE messages_v2 SET send_state = 2 WHERE mid = %d AND uid = %d", Long.valueOf(j), Long.valueOf(MessageObject.getDialogId(message)))).stepThis().dispose();
            this.database.executeFast(String.format(locale, "UPDATE messages_topics SET send_state = 2 WHERE mid = %d AND uid = %d", Long.valueOf(j), Long.valueOf(MessageObject.getDialogId(message)))).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void markMessageAsSendErrorWithParams(final TLRPC.Message message, long j, long j2) {
        final long clientUserId = getUserConfig().getClientUserId();
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda118
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$markMessageAsSendErrorWithParams$196(message, clientUserId);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessageAsSendErrorWithParams$196(TLRPC.Message message, long j) throws Throwable {
        NativeByteBuffer nativeByteBufferByteBufferValue;
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                long j2 = message.f1597id;
                long dialogId = MessageObject.getDialogId(message);
                int i = 0;
                while (i < 2) {
                    String str = i == 0 ? "messages_v2" : "messages_topics";
                    SQLiteDatabase sQLiteDatabase = this.database;
                    Locale locale = Locale.US;
                    sQLiteCursorQueryFinalized = sQLiteDatabase.queryFinalized(String.format(locale, "SELECT data FROM messages_v2 WHERE mid = %d AND uid = %d LIMIT 1", Long.valueOf(j2), Long.valueOf(dialogId)), new Object[0]);
                    try {
                        if (sQLiteCursorQueryFinalized.next() && (nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0)) != null) {
                            TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false).readAttachPath(nativeByteBufferByteBufferValue, j);
                            nativeByteBufferByteBufferValue.reuse();
                        }
                    } catch (Exception e) {
                        e = e;
                    } catch (Throwable th) {
                        th = th;
                    }
                    try {
                        this.database.executeFast(String.format(locale, "UPDATE " + str + " SET send_state = 2 WHERE mid = %d AND uid = %d", Long.valueOf(j2), Long.valueOf(dialogId))).stepThis().dispose();
                        this.database.executeFast(String.format(locale, "UPDATE " + str + " SET send_state = 2 WHERE mid = %d AND uid = %d", Long.valueOf(j2), Long.valueOf(dialogId))).stepThis().dispose();
                        i++;
                        sQLiteCursorQueryFinalized = sQLiteCursorQueryFinalized;
                    } catch (Exception e2) {
                        e = e2;
                        sQLiteCursorQueryFinalized = sQLiteCursorQueryFinalized;
                        checkSQLException(e);
                        if (sQLiteCursorQueryFinalized != null) {
                            sQLiteCursorQueryFinalized.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th2) {
                        th = th2;
                        sQLiteCursorQueryFinalized = sQLiteCursorQueryFinalized;
                        if (sQLiteCursorQueryFinalized != null) {
                            sQLiteCursorQueryFinalized.dispose();
                        }
                        throw th;
                    }
                }
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
            } catch (Exception e3) {
                e = e3;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public void setMessageSeq(final int i, final int i2, final int i3) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda59
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setMessageSeq$197(i, i2, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMessageSeq$197(int i, int i2, int i3) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO messages_seq VALUES(?, ?, ?)");
                sQLitePreparedStatementExecuteFast.requery();
                sQLitePreparedStatementExecuteFast.bindInteger(1, i);
                sQLitePreparedStatementExecuteFast.bindInteger(2, i2);
                sQLitePreparedStatementExecuteFast.bindInteger(3, i3);
                sQLitePreparedStatementExecuteFast.step();
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(13:(1:359)|(1:136)(1:(1:143)(2:144|(1:146)(11:148|357|149|(1:151)(1:153)|152|154|(3:156|314|157)(1:167)|168|(1:170)|176|177)))|137|357|149|(0)(0)|152|154|(0)(0)|168|(0)|176|177) */
    /* JADX WARN: Can't wrap try/catch for region: R(13:298|(2:329|196)|(1:201)|(1:203)|(2:308|204)|205|(2:302|214)|215|317|225|226|287|288) */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x025b, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x025c, code lost:
    
        r4 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x025f, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0260, code lost:
    
        r4 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x03e6, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x03e7, code lost:
    
        checkSQLException(r0);
        r7 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x03ea, code lost:
    
        if (r7 != 0) goto L226;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 8, insn: 0x0039: MOVE (r7 I:??[OBJECT, ARRAY]) = (r8 I:??[OBJECT, ARRAY]), block:B:11:0x0039 */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0198 A[Catch: all -> 0x019e, Exception -> 0x01a1, TRY_LEAVE, TryCatch #40 {Exception -> 0x01a1, blocks: (B:98:0x0180, B:100:0x0198), top: B:342:0x0180 }] */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01bd A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x01d9 A[Catch: all -> 0x01df, Exception -> 0x01e2, TRY_LEAVE, TryCatch #14 {Exception -> 0x01e2, blocks: (B:116:0x01bf, B:118:0x01d9), top: B:319:0x01bf, outer: #27 }] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x01f8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0233 A[Catch: all -> 0x025b, Exception -> 0x025f, TryCatch #51 {Exception -> 0x025f, all -> 0x025b, blocks: (B:149:0x022a, B:151:0x0233, B:154:0x0238, B:156:0x0240), top: B:357:0x022a }] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0240 A[Catch: all -> 0x025b, Exception -> 0x025f, TRY_LEAVE, TryCatch #51 {Exception -> 0x025f, all -> 0x025b, blocks: (B:149:0x022a, B:151:0x0233, B:154:0x0238, B:156:0x0240), top: B:357:0x022a }] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0263  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x026a A[PHI: r4
      0x026a: PHI (r4v34 ??) = (r4v33 ??), (r4v38 ??) binds: [B:174:0x0276, B:169:0x0268] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0355 A[PHI: r3
      0x0355: PHI (r3v24 ??) = (r3v14 ??), (r3v26 ??) binds: [B:212:0x0383, B:204:0x033f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x039c A[PHI: r3
      0x039c: PHI (r3v22 ??) = (r3v18 ??), (r3v23 ??) binds: [B:222:0x03c9, B:214:0x0386] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:224:0x03cc  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0401  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:285:0x04ba  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x04d9  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x0138 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:305:0x040a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:319:0x01bf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:367:0x028f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b4 A[DONT_GENERATE] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0129 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0154 A[Catch: all -> 0x015a, Exception -> 0x015d, TRY_LEAVE, TryCatch #9 {Exception -> 0x015d, blocks: (B:76:0x014e, B:78:0x0154), top: B:310:0x014e }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0177  */
    /* JADX WARN: Type inference failed for: r0v40, types: [org.telegram.SQLite.SQLiteDatabase] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v15 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v20, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r3v22, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r3v23, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r3v24, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r3v25 */
    /* JADX WARN: Type inference failed for: r3v26, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r3v28, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r3v29 */
    /* JADX WARN: Type inference failed for: r3v35 */
    /* JADX WARN: Type inference failed for: r3v36 */
    /* JADX WARN: Type inference failed for: r3v37 */
    /* JADX WARN: Type inference failed for: r3v38 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r4v12 */
    /* JADX WARN: Type inference failed for: r4v13, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r4v14 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v26, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r4v29 */
    /* JADX WARN: Type inference failed for: r4v30 */
    /* JADX WARN: Type inference failed for: r4v31, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r4v32 */
    /* JADX WARN: Type inference failed for: r4v33 */
    /* JADX WARN: Type inference failed for: r4v34, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r4v35 */
    /* JADX WARN: Type inference failed for: r4v36 */
    /* JADX WARN: Type inference failed for: r4v37 */
    /* JADX WARN: Type inference failed for: r4v38 */
    /* JADX WARN: Type inference failed for: r4v40, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r4v41 */
    /* JADX WARN: Type inference failed for: r4v42 */
    /* JADX WARN: Type inference failed for: r4v43 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v18 */
    /* JADX WARN: Type inference failed for: r7v20 */
    /* JADX WARN: Type inference failed for: r7v21, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r7v22 */
    /* JADX WARN: Type inference failed for: r7v24, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r7v25 */
    /* JADX WARN: Type inference failed for: r7v31 */
    /* JADX WARN: Type inference failed for: r7v32, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r7v33 */
    /* JADX WARN: Type inference failed for: r7v34 */
    /* JADX WARN: Type inference failed for: r7v35 */
    /* JADX WARN: Type inference failed for: r7v36, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r7v37, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r7v39 */
    /* JADX WARN: Type inference failed for: r7v40 */
    /* JADX WARN: Type inference failed for: r7v41, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r7v42, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r7v43 */
    /* JADX WARN: Type inference failed for: r7v44 */
    /* JADX WARN: Type inference failed for: r7v45 */
    /* JADX WARN: Type inference failed for: r7v46 */
    /* JADX WARN: Type inference failed for: r7v47, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r7v48 */
    /* JADX WARN: Type inference failed for: r7v49, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v52 */
    /* JADX WARN: Type inference failed for: r7v55, types: [org.telegram.SQLite.SQLitePreparedStatement] */
    /* JADX WARN: Type inference failed for: r7v56 */
    /* JADX WARN: Type inference failed for: r7v58 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v62 */
    /* JADX WARN: Type inference failed for: r7v63 */
    /* JADX WARN: Type inference failed for: r7v66 */
    /* JADX WARN: Type inference failed for: r7v67 */
    /* JADX WARN: Type inference failed for: r7v68 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.lang.String] */
    /* renamed from: updateMessageStateAndIdInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public long[] lambda$updateMessageStateAndId$199(long r17, long r19, java.lang.Integer r21, int r22, int r23, int r24, int r25) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1245
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateMessageStateAndId$199(long, long, java.lang.Integer, int, int, int, int):long[]");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageStateAndIdInternal$198(TLRPC.TL_updates tL_updates) {
        getMessagesController().processUpdates(tL_updates, false);
    }

    public long[] updateMessageStateAndId(final long j, final long j2, final Integer num, final int i, final int i2, boolean z, final int i3, final int i4) {
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda137
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$updateMessageStateAndId$199(j, j2, num, i, i2, i3, i4);
                }
            });
            return null;
        }
        return lambda$updateMessageStateAndId$199(j, j2, num, i, i2, i3, i4);
    }

    public void updateMessageTopicId(final long j, final long j2, final int i) {
        executeInStorageQueue(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda176
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateMessageTopicId$200(i, j, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateMessageTopicId$200(int i, long j, long j2) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE messages_topics SET topic_id = ? WHERE uid = ? AND mid = ?");
                sQLitePreparedStatementExecuteFast.requery();
                sQLitePreparedStatementExecuteFast.bindLong(1, i);
                sQLitePreparedStatementExecuteFast.bindLong(2, j);
                sQLitePreparedStatementExecuteFast.bindLong(3, j2);
                sQLitePreparedStatementExecuteFast.step();
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: updateUsersInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$updateUsers$201(ArrayList<TLRPC.User> arrayList, boolean z, boolean z2) throws Throwable {
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                if (z) {
                    if (z2) {
                        this.database.beginTransaction();
                    }
                    SQLitePreparedStatement sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE users SET status = ? WHERE uid = ?");
                    try {
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            TLRPC.User user = arrayList.get(i);
                            sQLitePreparedStatementExecuteFast.requery();
                            TLRPC.UserStatus userStatus = user.status;
                            if (userStatus != null) {
                                sQLitePreparedStatementExecuteFast.bindInteger(1, userStatus.expires);
                            } else {
                                sQLitePreparedStatementExecuteFast.bindInteger(1, 0);
                            }
                            sQLitePreparedStatementExecuteFast.bindLong(2, user.f1734id);
                            sQLitePreparedStatementExecuteFast.step();
                        }
                        sQLitePreparedStatementExecuteFast.dispose();
                        if (z2) {
                            this.database.commitTransaction();
                        }
                    } catch (Exception e) {
                        e = e;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        checkSQLException(e);
                        SQLiteDatabase sQLiteDatabase = this.database;
                        if (sQLiteDatabase != null) {
                            sQLiteDatabase.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                            return;
                        }
                        return;
                    } catch (Throwable th) {
                        th = th;
                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                        SQLiteDatabase sQLiteDatabase2 = this.database;
                        if (sQLiteDatabase2 != null) {
                            sQLiteDatabase2.commitTransaction();
                        }
                        if (sQLitePreparedStatement != null) {
                            sQLitePreparedStatement.dispose();
                        }
                        throw th;
                    }
                } else {
                    ArrayList<Long> arrayList2 = new ArrayList<>();
                    LongSparseArray longSparseArray = new LongSparseArray();
                    int size2 = arrayList.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        TLRPC.User user2 = arrayList.get(i2);
                        arrayList2.add(Long.valueOf(user2.f1734id));
                        longSparseArray.put(user2.f1734id, user2);
                    }
                    ArrayList<TLRPC.User> arrayList3 = new ArrayList<>();
                    getUsersInternal(arrayList2, arrayList3);
                    int size3 = arrayList3.size();
                    for (int i3 = 0; i3 < size3; i3++) {
                        TLRPC.User user3 = arrayList3.get(i3);
                        TLRPC.User user4 = (TLRPC.User) longSparseArray.get(user3.f1734id);
                        if (user4 != null) {
                            if (user4.first_name != null && user4.last_name != null) {
                                if (!UserObject.isContact(user3)) {
                                    user3.first_name = user4.first_name;
                                    user3.last_name = user4.last_name;
                                }
                                user3.username = user4.username;
                            } else {
                                TLRPC.UserProfilePhoto userProfilePhoto = user4.photo;
                                if (userProfilePhoto != null) {
                                    user3.photo = userProfilePhoto;
                                } else {
                                    String str = user4.phone;
                                    if (str != null) {
                                        user3.phone = str;
                                    }
                                }
                            }
                        }
                    }
                    if (!arrayList3.isEmpty()) {
                        if (z2) {
                            this.database.beginTransaction();
                        }
                        putUsersInternal(arrayList3);
                        if (z2) {
                            this.database.commitTransaction();
                        }
                    }
                }
                SQLiteDatabase sQLiteDatabase3 = this.database;
                if (sQLiteDatabase3 != null) {
                    sQLiteDatabase3.commitTransaction();
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public void updateUsers(final ArrayList<TLRPC.User> arrayList, final boolean z, final boolean z2, boolean z3) {
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        if (z3) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda250
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$updateUsers$201(arrayList, z, z2);
                }
            });
        } else {
            lambda$updateUsers$201(arrayList, z, z2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* renamed from: markMessagesAsReadInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void lambda$markMessagesAsRead$203(org.telegram.messenger.support.LongSparseIntArray r19, org.telegram.messenger.support.LongSparseIntArray r20, android.util.SparseIntArray r21) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 257
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$markMessagesAsRead$203(org.telegram.messenger.support.LongSparseIntArray, org.telegram.messenger.support.LongSparseIntArray, android.util.SparseIntArray):void");
    }

    private void markMessagesContentAsReadInternal(long j, ArrayList<Integer> arrayList, int i) throws Throwable {
        SQLiteCursor sQLiteCursor = null;
        ArrayList<Integer> arrayList2 = null;
        sQLiteCursor = null;
        try {
            try {
                String strJoin = TextUtils.join(",", arrayList);
                getAyuSpyController().onMessageContentsRead(j, strJoin);
                SQLiteDatabase sQLiteDatabase = this.database;
                Locale locale = Locale.US;
                sQLiteDatabase.executeFast(String.format(locale, "UPDATE messages_v2 SET read_state = read_state | 2 WHERE mid IN (%s) AND uid = %d", strJoin, Long.valueOf(j))).stepThis().dispose();
                if (i != 0) {
                    SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(locale, "SELECT mid, ttl FROM messages_v2 WHERE mid IN (%s) AND uid = %d AND ttl > 0", strJoin, Long.valueOf(j)), new Object[0]);
                    while (sQLiteCursorQueryFinalized.next()) {
                        try {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList<>();
                            }
                            arrayList2.add(Integer.valueOf(sQLiteCursorQueryFinalized.intValue(0)));
                        } catch (Exception e) {
                            e = e;
                            sQLiteCursor = sQLiteCursorQueryFinalized;
                            checkSQLException(e);
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                                return;
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            sQLiteCursor = sQLiteCursorQueryFinalized;
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                            }
                            throw th;
                        }
                    }
                    if (arrayList2 != null) {
                        emptyMessagesMedia(j, arrayList2);
                    }
                    sQLiteCursorQueryFinalized.dispose();
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public void markMessagesContentAsRead(final long j, final ArrayList<Integer> arrayList, final int i, final int i2) {
        if (isEmpty(arrayList)) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda111
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$markMessagesContentAsRead$202(j, arrayList, i2, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesContentAsRead$202(long j, ArrayList arrayList, int i, int i2) throws Throwable {
        MessageObject existingMessageInAnyWay;
        TLRPC.Message message;
        int i3;
        if (j == 0) {
            SQLiteCursor sQLiteCursor = null;
            try {
                try {
                    LongSparseArray longSparseArray = new LongSparseArray();
                    LongSparseArray longSparseArray2 = new LongSparseArray();
                    LongSparseArray longSparseArray3 = new LongSparseArray();
                    SQLiteCursor sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT uid, mid, ttl FROM messages_v2 WHERE mid IN (%s) AND is_channel = 0", TextUtils.join(",", arrayList)), new Object[0]);
                    while (sQLiteCursorQueryFinalized.next()) {
                        try {
                            long jLongValue = sQLiteCursorQueryFinalized.longValue(0);
                            int iIntValue = sQLiteCursorQueryFinalized.intValue(1);
                            int iIntValue2 = sQLiteCursorQueryFinalized.intValue(2);
                            if (iIntValue2 <= 0 || iIntValue2 == Integer.MAX_VALUE || i == 0 || (i3 = i + iIntValue2) < i2) {
                                ArrayList arrayList2 = (ArrayList) longSparseArray.get(jLongValue);
                                if (arrayList2 == null) {
                                    arrayList2 = new ArrayList();
                                    longSparseArray.put(jLongValue, arrayList2);
                                }
                                arrayList2.add(Integer.valueOf(iIntValue));
                            } else {
                                SparseArray sparseArray = (SparseArray) longSparseArray2.get(jLongValue);
                                if (sparseArray == null) {
                                    sparseArray = new SparseArray();
                                    longSparseArray2.put(jLongValue, sparseArray);
                                }
                                ArrayList arrayList3 = (ArrayList) sparseArray.get(i3);
                                if (arrayList3 == null) {
                                    arrayList3 = new ArrayList();
                                    sparseArray.put(i3, arrayList3);
                                }
                                arrayList3.add(Integer.valueOf(iIntValue));
                            }
                            if (iIntValue2 > 0) {
                                if (!AyuState.isMessageBurned(this.currentAccount, jLongValue, iIntValue) && (existingMessageInAnyWay = getMessagesController().getExistingMessageInAnyWay(Long.valueOf(jLongValue), Integer.valueOf(iIntValue))) != null && (message = existingMessageInAnyWay.messageOwner) != null) {
                                    SaveMessageRequest saveMessageRequest = new SaveMessageRequest(this.currentAccount, message);
                                    saveMessageRequest.forceSaveEdited();
                                    getAyuMessagesController().onMessageEdited(saveMessageRequest, null);
                                }
                                AyuState.setMessageBurned(this.currentAccount, jLongValue, iIntValue);
                                ArrayList arrayList4 = (ArrayList) longSparseArray3.get(jLongValue);
                                if (arrayList4 == null) {
                                    arrayList4 = new ArrayList();
                                    longSparseArray3.put(jLongValue, arrayList4);
                                }
                                arrayList4.add(Integer.valueOf(iIntValue));
                            }
                        } catch (Exception e) {
                            e = e;
                            sQLiteCursor = sQLiteCursorQueryFinalized;
                            checkSQLException(e);
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                                return;
                            }
                            return;
                        } catch (Throwable th) {
                            th = th;
                            sQLiteCursor = sQLiteCursorQueryFinalized;
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                            }
                            throw th;
                        }
                    }
                    sQLiteCursorQueryFinalized.dispose();
                    int size = longSparseArray.size();
                    for (int i4 = 0; i4 < size; i4++) {
                        markMessagesContentAsReadInternal(longSparseArray.keyAt(i4), (ArrayList) longSparseArray.valueAt(i4), i2);
                    }
                    int size2 = longSparseArray2.size();
                    for (int i5 = 0; i5 < size2; i5++) {
                        createTaskForSecretMedia(longSparseArray2.keyAt(i5), (SparseArray) longSparseArray2.valueAt(i5));
                    }
                    int size3 = longSparseArray3.size();
                    for (int i6 = 0; i6 < size3; i6++) {
                        getNotificationCenter().postNotificationNameOnUIThread(AyuConstants.FORCE_MESSAGES_UPDATE, Long.valueOf(longSparseArray3.keyAt(i6)), longSparseArray3.valueAt(i6));
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } else {
            markMessagesContentAsReadInternal(j, arrayList, i2);
        }
    }

    public void markMessagesAsRead(final LongSparseIntArray longSparseIntArray, final LongSparseIntArray longSparseIntArray2, final SparseIntArray sparseIntArray, boolean z) {
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda216
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$markMessagesAsRead$203(longSparseIntArray, longSparseIntArray2, sparseIntArray);
                }
            });
        } else {
            lambda$markMessagesAsRead$203(longSparseIntArray, longSparseIntArray2, sparseIntArray);
        }
    }

    public void markMessagesAsDeletedByRandoms(final ArrayList<Long> arrayList) {
        if (arrayList.isEmpty()) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda198
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$markMessagesAsDeletedByRandoms$205(arrayList);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesAsDeletedByRandoms$205(ArrayList arrayList) throws Throwable {
        SQLiteCursor sQLiteCursorQueryFinalized;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT mid, uid FROM randoms_v2 WHERE random_id IN(%s)", TextUtils.join(",", arrayList)), new Object[0]);
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            LongSparseArray longSparseArray = new LongSparseArray();
            while (sQLiteCursorQueryFinalized.next()) {
                long jLongValue = sQLiteCursorQueryFinalized.longValue(1);
                ArrayList arrayList2 = (ArrayList) longSparseArray.get(jLongValue);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList();
                    longSparseArray.put(jLongValue, arrayList2);
                }
                arrayList2.add(Integer.valueOf(sQLiteCursorQueryFinalized.intValue(0)));
            }
            sQLiteCursorQueryFinalized.dispose();
            if (longSparseArray.isEmpty()) {
                return;
            }
            int size = longSparseArray.size();
            for (int i = 0; i < size; i++) {
                long jKeyAt = longSparseArray.keyAt(i);
                final ArrayList<Integer> arrayList3 = (ArrayList) longSparseArray.valueAt(i);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda66
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$markMessagesAsDeletedByRandoms$204(arrayList3);
                    }
                });
                updateDialogsWithReadMessagesInternal(arrayList3, null, null, null, null);
                lambda$markMessagesAsDeleted$213(jKeyAt, arrayList3, true, 0, 0);
                lambda$updateDialogsWithDeletedMessages$212(jKeyAt, 0L, arrayList3, null);
            }
        } catch (Exception e2) {
            e = e2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            checkSQLException(e);
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesAsDeletedByRandoms$204(ArrayList arrayList) {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.messagesDeleted, arrayList, 0L, Boolean.FALSE);
    }

    protected void deletePushMessages(long j, ArrayList<Integer> arrayList) {
        try {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM unread_push_messages WHERE uid = %d AND mid IN(%s)", Long.valueOf(j), TextUtils.join(",", arrayList))).stepThis().dispose();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    private void broadcastScheduledMessagesChange(final Long l) throws Throwable {
        SQLiteCursor sQLiteCursorQueryFinalized;
        SQLiteCursor sQLiteCursor = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT COUNT(mid) FROM scheduled_messages_v2 WHERE uid = %d", l), new Object[0]);
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            final int iIntValue = sQLiteCursorQueryFinalized.next() ? sQLiteCursorQueryFinalized.intValue(0) : 0;
            sQLiteCursorQueryFinalized.dispose();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda55
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$broadcastScheduledMessagesChange$206(l, iIntValue);
                }
            });
        } catch (Exception e2) {
            e = e2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            checkSQLException(e);
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
        } catch (Throwable th2) {
            th = th2;
            sQLiteCursor = sQLiteCursorQueryFinalized;
            if (sQLiteCursor != null) {
                sQLiteCursor.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$broadcastScheduledMessagesChange$206(Long l, int i) {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.scheduledMessagesUpdated, l, Integer.valueOf(i), Boolean.TRUE);
    }

    private void broadcastQuickRepliesMessagesChange(Long l, long j) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda84
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$broadcastQuickRepliesMessagesChange$207();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$broadcastQuickRepliesMessagesChange$207() {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.quickRepliesUpdated, new Object[0]);
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 4 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:127:0x02ed -> B:49:0x0177). Please report as a decompilation issue!!! */
    /* renamed from: markMessagesAsDeletedInternal, reason: merged with bridge method [inline-methods] */
    public java.util.ArrayList<java.lang.Long> lambda$markMessagesAsDeleted$213(long r44, java.util.ArrayList<java.lang.Integer> r46, boolean r47, int r48, int r49) {
        /*
            Method dump skipped, instructions count: 3597
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$markMessagesAsDeleted$213(long, java.util.ArrayList, boolean, int, int):java.util.ArrayList");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesAsDeletedInternal$208(ArrayList arrayList) {
        getFileLoader().cancelLoadFiles(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesAsDeletedInternal$209(LongSparseArray longSparseArray) {
        getMessagesController().getSavedMessagesController().updateDeleted(longSparseArray);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesAsDeletedInternal$210(ArrayList arrayList, long j) {
        HashSet<Long> hashSet = new HashSet<>();
        int size = arrayList.size();
        boolean z = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TLRPC.Message message = (TLRPC.Message) obj;
            if (getMessagesController().processDeletedReactionTags(message)) {
                hashSet.add(Long.valueOf(MessageObject.getSavedDialogId(j, message)));
                z = true;
            }
        }
        if (z) {
            getMessagesController().updateSavedReactionTags(hashSet);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesAsDeletedInternal$211(ArrayList arrayList) {
        HashSet<Long> hashSet = new HashSet<>();
        long[] jArr = new long[1];
        boolean z = false;
        for (int i = 0; i < arrayList.size(); i++) {
            if (getMediaDataController().processDeletedMessage(((Integer) arrayList.get(i)).intValue(), jArr)) {
                hashSet.add(Long.valueOf(jArr[0]));
                z = true;
            }
        }
        if (z) {
            getMessagesController().updateSavedReactionTags(hashSet);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:198:0x042e  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0438  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0441  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0446  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x044b  */
    /* JADX WARN: Removed duplicated region for block: B:259:? A[RETURN, SYNTHETIC] */
    /* renamed from: updateDialogsWithDeletedMessagesInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void lambda$updateDialogsWithDeletedMessages$212(long r25, long r27, java.util.ArrayList<java.lang.Integer> r29, java.util.ArrayList<java.lang.Long> r30) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1103
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateDialogsWithDeletedMessages$212(long, long, java.util.ArrayList, java.util.ArrayList):void");
    }

    public void updateDialogsWithDeletedMessages(final long j, final long j2, final ArrayList<Integer> arrayList, final ArrayList<Long> arrayList2, boolean z) {
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda70
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$updateDialogsWithDeletedMessages$212(j, j2, arrayList, arrayList2);
                }
            });
        } else {
            lambda$updateDialogsWithDeletedMessages$212(j, j2, arrayList, arrayList2);
        }
    }

    public ArrayList<Long> markMessagesAsDeleted(final long j, final ArrayList<Integer> arrayList, boolean z, final boolean z2, final int i, final int i2) {
        if (arrayList.isEmpty()) {
            return null;
        }
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda146
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$markMessagesAsDeleted$213(j, arrayList, z2, i, i2);
                }
            });
            return null;
        }
        return lambda$markMessagesAsDeleted$213(j, arrayList, z2, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:101:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00f6 A[Catch: all -> 0x0103, Exception -> 0x0108, TryCatch #13 {Exception -> 0x0108, all -> 0x0103, blocks: (B:5:0x0035, B:44:0x00f2, B:46:0x00f6, B:51:0x010e, B:52:0x0117, B:54:0x011d, B:62:0x015b, B:70:0x01ad, B:77:0x0215, B:91:0x0268), top: B:119:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x011d A[Catch: all -> 0x0103, Exception -> 0x0108, TRY_LEAVE, TryCatch #13 {Exception -> 0x0108, all -> 0x0103, blocks: (B:5:0x0035, B:44:0x00f2, B:46:0x00f6, B:51:0x010e, B:52:0x0117, B:54:0x011d, B:62:0x015b, B:70:0x01ad, B:77:0x0215, B:91:0x0268), top: B:119:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x020a A[Catch: all -> 0x008a, Exception -> 0x0151, TryCatch #2 {all -> 0x008a, blocks: (B:7:0x0055, B:10:0x005e, B:12:0x0066, B:14:0x0071, B:16:0x007b, B:22:0x0093, B:26:0x00a5, B:27:0x00b4, B:31:0x00c1, B:33:0x00c7, B:35:0x00de, B:42:0x00ec, B:43:0x00ef, B:55:0x0141, B:57:0x0147, B:61:0x0158, B:71:0x0204, B:73:0x020a, B:75:0x0210, B:78:0x022d, B:80:0x0233, B:88:0x0262), top: B:110:0x0055 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0215 A[Catch: all -> 0x0103, Exception -> 0x0108, TRY_ENTER, TRY_LEAVE, TryCatch #13 {Exception -> 0x0108, all -> 0x0103, blocks: (B:5:0x0035, B:44:0x00f2, B:46:0x00f6, B:51:0x010e, B:52:0x0117, B:54:0x011d, B:62:0x015b, B:70:0x01ad, B:77:0x0215, B:91:0x0268), top: B:119:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0326  */
    /* JADX WARN: Type inference failed for: r10v0, types: [androidx.collection.LongSparseArray] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v10, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v2 */
    /* JADX WARN: Type inference failed for: r13v3, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r13v6, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8 */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r16v10 */
    /* JADX WARN: Type inference failed for: r16v12 */
    /* JADX WARN: Type inference failed for: r16v13 */
    /* JADX WARN: Type inference failed for: r16v14 */
    /* JADX WARN: Type inference failed for: r16v3 */
    /* JADX WARN: Type inference failed for: r16v7, types: [java.util.ArrayList<java.lang.Long>] */
    /* JADX WARN: Type inference failed for: r25v0, types: [org.telegram.messenger.BaseController, org.telegram.messenger.MessagesStorage] */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v2, types: [org.telegram.SQLite.SQLiteCursor] */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* renamed from: markMessagesAsDeletedInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.ArrayList<java.lang.Long> lambda$markMessagesAsDeleted$215(long r26, int r28, boolean r29) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 826
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$markMessagesAsDeleted$215(long, int, boolean):java.util.ArrayList");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$markMessagesAsDeletedInternal$214(ArrayList arrayList) {
        getFileLoader().cancelLoadFiles(arrayList);
    }

    public ArrayList<Long> markMessagesAsDeleted(final long j, final int i, boolean z, final boolean z2) {
        if (z) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda99
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$markMessagesAsDeleted$215(j, i, z2);
                }
            });
            return null;
        }
        return lambda$markMessagesAsDeleted$215(j, i, z2);
    }

    private void fixUnsupportedMedia(TLRPC.Message message) {
        if (message == null) {
            return;
        }
        TLRPC.MessageMedia messageMedia = message.media;
        if (messageMedia instanceof TLRPC.TL_messageMediaUnsupported_old) {
            if (messageMedia.bytes.length == 0) {
                messageMedia.bytes = Utilities.intToBytes(220);
            }
        } else if (messageMedia instanceof TLRPC.TL_messageMediaUnsupported) {
            TLRPC.TL_messageMediaUnsupported_old tL_messageMediaUnsupported_old = new TLRPC.TL_messageMediaUnsupported_old();
            message.media = tL_messageMediaUnsupported_old;
            tL_messageMediaUnsupported_old.bytes = Utilities.intToBytes(220);
            message.flags |= 512;
        }
    }

    private void doneHolesInTable(String str, long j, int i, long j2) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast;
        int i2 = 2;
        if (j2 != 0) {
            if (i == 0) {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND topic_id = %d", Long.valueOf(j), Long.valueOf(j2))).stepThis().dispose();
            } else {
                this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND topic_id = %d AND start = 0", Long.valueOf(j), Long.valueOf(j2))).stepThis().dispose();
            }
        } else if (i == 0) {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d", Long.valueOf(j))).stepThis().dispose();
        } else {
            this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND start = 0", Long.valueOf(j))).stepThis().dispose();
        }
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                if (j2 != 0) {
                    sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO " + str + " VALUES(?, ?, ?, ?)");
                } else {
                    sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO " + str + " VALUES(?, ?, ?)");
                }
                sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                sQLitePreparedStatement.requery();
                sQLitePreparedStatement.bindLong(1, j);
                if (j2 != 0) {
                    sQLitePreparedStatement.bindLong(2, j2);
                    i2 = 3;
                }
                sQLitePreparedStatement.bindInteger(i2, 1);
                sQLitePreparedStatement.bindInteger(i2 + 1, 1);
                sQLitePreparedStatement.step();
                sQLitePreparedStatement.dispose();
            } catch (Exception e) {
                throw e;
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01ec  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void doneHolesInMedia(long r21, int r23, int r24, long r25) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 496
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.doneHolesInMedia(long, int, int, long):void");
    }

    private static class Hole {
        public int end;
        public int start;
        public int type;

        public Hole(int i, int i2) {
            this.start = i;
            this.end = i2;
        }

        public Hole(int i, int i2, int i3) {
            this.type = i;
            this.start = i2;
            this.end = i3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:119:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x01bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void closeHolesInMedia(long r37, int r39, int r40, int r41, long r42) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1211
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.closeHolesInMedia(long, int, int, int, long):void");
    }

    private void closeHolesInTable(String str, long j, int i, int i2, long j2) throws Throwable {
        SQLiteCursor sQLiteCursor;
        char c;
        char c2;
        SQLiteCursor sQLiteCursorQueryFinalized;
        int i3;
        ArrayList arrayList;
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast;
        long j3;
        int i4;
        int i5;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                if (j2 != 0) {
                    c = 2;
                    c2 = 3;
                    sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT start, end FROM " + str + " WHERE uid = %d AND topic_id = %d AND ((end >= %d AND end <= %d) OR (start >= %d AND start <= %d) OR (start >= %d AND end <= %d) OR (start <= %d AND end >= %d))", Long.valueOf(j), Long.valueOf(j2), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2)), new Object[0]);
                } else {
                    c = 2;
                    c2 = 3;
                    sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT start, end FROM " + str + " WHERE uid = %d AND ((end >= %d AND end <= %d) OR (start >= %d AND start <= %d) OR (start >= %d AND end <= %d) OR (start <= %d AND end >= %d))", Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i), Integer.valueOf(i2)), new Object[0]);
                }
                sQLiteCursor = sQLiteCursorQueryFinalized;
                ArrayList arrayList2 = null;
                while (sQLiteCursor.next()) {
                    try {
                        try {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            int iIntValue = sQLiteCursor.intValue(0);
                            int iIntValue2 = sQLiteCursor.intValue(1);
                            if (iIntValue != iIntValue2 || iIntValue != 1) {
                                arrayList2.add(new Hole(iIntValue, iIntValue2));
                            }
                        } catch (Exception e) {
                            e = e;
                        }
                    } catch (Throwable th) {
                        th = th;
                    }
                }
                sQLiteCursor.dispose();
                if (arrayList2 != null) {
                    int i6 = 0;
                    while (i6 < arrayList2.size()) {
                        Hole hole = (Hole) arrayList2.get(i6);
                        int i7 = hole.end;
                        if (i2 < i7 - 1 || i > hole.start + 1) {
                            i3 = i6;
                            arrayList = arrayList2;
                            if (i2 < i7 - 1) {
                                int i8 = hole.start;
                                if (i > i8 + 1) {
                                    if (j2 != 0) {
                                        Long lValueOf = Long.valueOf(j);
                                        Long lValueOf2 = Long.valueOf(j2);
                                        Integer numValueOf = Integer.valueOf(hole.start);
                                        Integer numValueOf2 = Integer.valueOf(hole.end);
                                        Object[] objArr = new Object[4];
                                        objArr[0] = lValueOf;
                                        objArr[1] = lValueOf2;
                                        objArr[c] = numValueOf;
                                        objArr[3] = numValueOf2;
                                        this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND topic_id = %d AND start = %d AND end = %d", objArr)).stepThis().dispose();
                                        sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO " + str + " VALUES(?, ?, ?, ?)");
                                    } else {
                                        Long lValueOf3 = Long.valueOf(j);
                                        Integer numValueOf3 = Integer.valueOf(hole.start);
                                        Integer numValueOf4 = Integer.valueOf(hole.end);
                                        Object[] objArr2 = new Object[3];
                                        objArr2[0] = lValueOf3;
                                        objArr2[1] = numValueOf3;
                                        objArr2[c] = numValueOf4;
                                        this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND start = %d AND end = %d", objArr2)).stepThis().dispose();
                                        sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO " + str + " VALUES(?, ?, ?)");
                                    }
                                    try {
                                        sQLitePreparedStatementExecuteFast.requery();
                                        sQLitePreparedStatementExecuteFast.bindLong(1, j);
                                        if (j2 != 0) {
                                            j3 = j2;
                                            sQLitePreparedStatementExecuteFast.bindLong(2, j3);
                                            i4 = 3;
                                        } else {
                                            j3 = j2;
                                            i4 = 2;
                                        }
                                        sQLitePreparedStatementExecuteFast.bindInteger(i4, hole.start);
                                        sQLitePreparedStatementExecuteFast.bindInteger(i4 + 1, i);
                                        sQLitePreparedStatementExecuteFast.step();
                                        sQLitePreparedStatementExecuteFast.requery();
                                        sQLitePreparedStatementExecuteFast.bindLong(1, j);
                                        if (j2 != 0) {
                                            sQLitePreparedStatementExecuteFast.bindLong(2, j3);
                                            i5 = 3;
                                        } else {
                                            i5 = 2;
                                        }
                                        sQLitePreparedStatementExecuteFast.bindInteger(i5, i2);
                                        sQLitePreparedStatementExecuteFast.bindInteger(i5 + 1, hole.end);
                                        sQLitePreparedStatementExecuteFast.step();
                                        sQLitePreparedStatementExecuteFast.dispose();
                                        i6 = i3 + 1;
                                        arrayList2 = arrayList;
                                        c = 2;
                                        c2 = 3;
                                    } catch (Exception e2) {
                                        e = e2;
                                        sQLiteCursor = null;
                                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                                        checkSQLException(e);
                                        if (sQLitePreparedStatement != null) {
                                            sQLitePreparedStatement.dispose();
                                        }
                                        if (sQLiteCursor != null) {
                                            sQLiteCursor.dispose();
                                            return;
                                        }
                                        return;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        sQLiteCursor = null;
                                        sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
                                        if (sQLitePreparedStatement != null) {
                                            sQLitePreparedStatement.dispose();
                                        }
                                        if (sQLiteCursor != null) {
                                            sQLiteCursor.dispose();
                                        }
                                        throw th;
                                    }
                                } else if (i8 != i2) {
                                    if (j2 != 0) {
                                        try {
                                            SQLiteDatabase sQLiteDatabase = this.database;
                                            Locale locale = Locale.US;
                                            Long lValueOf4 = Long.valueOf(j);
                                            Long lValueOf5 = Long.valueOf(j2);
                                            Integer numValueOf5 = Integer.valueOf(hole.start);
                                            Integer numValueOf6 = Integer.valueOf(hole.end);
                                            Object[] objArr3 = new Object[4];
                                            objArr3[0] = lValueOf4;
                                            objArr3[1] = lValueOf5;
                                            objArr3[c] = numValueOf5;
                                            objArr3[3] = numValueOf6;
                                            sQLiteDatabase.executeFast(String.format(locale, "DELETE FROM " + str + " WHERE uid = %d AND topic_id = %d AND start = %d AND end = %d", objArr3)).stepThis().dispose();
                                            Long lValueOf6 = Long.valueOf(j);
                                            Long lValueOf7 = Long.valueOf(j2);
                                            Integer numValueOf7 = Integer.valueOf(i2);
                                            Integer numValueOf8 = Integer.valueOf(hole.end);
                                            Object[] objArr4 = new Object[4];
                                            objArr4[0] = lValueOf6;
                                            objArr4[1] = lValueOf7;
                                            objArr4[c] = numValueOf7;
                                            objArr4[3] = numValueOf8;
                                            this.database.executeFast(String.format(locale, "REPLACE INTO " + str + " VALUES(%d, %d, %d, %d)", objArr4)).stepThis().dispose();
                                        } catch (Exception e3) {
                                            checkSQLException(e3, false);
                                        }
                                    } else {
                                        SQLiteDatabase sQLiteDatabase2 = this.database;
                                        Locale locale2 = Locale.US;
                                        Long lValueOf8 = Long.valueOf(j);
                                        Integer numValueOf9 = Integer.valueOf(hole.start);
                                        Integer numValueOf10 = Integer.valueOf(hole.end);
                                        Object[] objArr5 = new Object[3];
                                        objArr5[0] = lValueOf8;
                                        objArr5[1] = numValueOf9;
                                        objArr5[c] = numValueOf10;
                                        sQLiteDatabase2.executeFast(String.format(locale2, "DELETE FROM " + str + " WHERE uid = %d AND start = %d AND end = %d", objArr5)).stepThis().dispose();
                                        Long lValueOf9 = Long.valueOf(j);
                                        Integer numValueOf11 = Integer.valueOf(i2);
                                        Integer numValueOf12 = Integer.valueOf(hole.end);
                                        Object[] objArr6 = new Object[3];
                                        objArr6[0] = lValueOf9;
                                        objArr6[1] = numValueOf11;
                                        objArr6[c] = numValueOf12;
                                        this.database.executeFast(String.format(locale2, "REPLACE INTO " + str + " VALUES(%d, %d, %d)", objArr6)).stepThis().dispose();
                                    }
                                }
                            } else if (i7 != i) {
                                if (j2 != 0) {
                                    try {
                                        SQLiteDatabase sQLiteDatabase3 = this.database;
                                        Locale locale3 = Locale.US;
                                        Long lValueOf10 = Long.valueOf(j);
                                        Long lValueOf11 = Long.valueOf(j2);
                                        Integer numValueOf13 = Integer.valueOf(hole.start);
                                        Integer numValueOf14 = Integer.valueOf(hole.end);
                                        Object[] objArr7 = new Object[4];
                                        objArr7[0] = lValueOf10;
                                        objArr7[1] = lValueOf11;
                                        objArr7[c] = numValueOf13;
                                        objArr7[3] = numValueOf14;
                                        sQLiteDatabase3.executeFast(String.format(locale3, "DELETE FROM " + str + " WHERE uid = %d AND topic_id = %d AND start = %d AND end = %d", objArr7)).stepThis().dispose();
                                        Long lValueOf12 = Long.valueOf(j);
                                        Long lValueOf13 = Long.valueOf(j2);
                                        Integer numValueOf15 = Integer.valueOf(hole.start);
                                        Integer numValueOf16 = Integer.valueOf(i);
                                        Object[] objArr8 = new Object[4];
                                        objArr8[0] = lValueOf12;
                                        objArr8[1] = lValueOf13;
                                        objArr8[c] = numValueOf15;
                                        objArr8[3] = numValueOf16;
                                        this.database.executeFast(String.format(locale3, "REPLACE INTO " + str + " VALUES(%d, %d, %d, %d)", objArr8)).stepThis().dispose();
                                    } catch (Exception e4) {
                                        checkSQLException(e4, false);
                                    }
                                } else {
                                    SQLiteDatabase sQLiteDatabase4 = this.database;
                                    Locale locale4 = Locale.US;
                                    Long lValueOf14 = Long.valueOf(j);
                                    Integer numValueOf17 = Integer.valueOf(hole.start);
                                    Integer numValueOf18 = Integer.valueOf(hole.end);
                                    Object[] objArr9 = new Object[3];
                                    objArr9[0] = lValueOf14;
                                    objArr9[1] = numValueOf17;
                                    objArr9[c] = numValueOf18;
                                    sQLiteDatabase4.executeFast(String.format(locale4, "DELETE FROM " + str + " WHERE uid = %d AND start = %d AND end = %d", objArr9)).stepThis().dispose();
                                    Long lValueOf15 = Long.valueOf(j);
                                    Integer numValueOf19 = Integer.valueOf(hole.start);
                                    Integer numValueOf20 = Integer.valueOf(i);
                                    Object[] objArr10 = new Object[3];
                                    objArr10[0] = lValueOf15;
                                    objArr10[1] = numValueOf19;
                                    objArr10[c] = numValueOf20;
                                    this.database.executeFast(String.format(locale4, "REPLACE INTO " + str + " VALUES(%d, %d, %d)", objArr10)).stepThis().dispose();
                                }
                            }
                        } else if (j2 != 0) {
                            Long lValueOf16 = Long.valueOf(j);
                            Long lValueOf17 = Long.valueOf(j2);
                            i3 = i6;
                            Integer numValueOf21 = Integer.valueOf(hole.start);
                            Integer numValueOf22 = Integer.valueOf(hole.end);
                            arrayList = arrayList2;
                            Object[] objArr11 = new Object[4];
                            objArr11[0] = lValueOf16;
                            objArr11[1] = lValueOf17;
                            objArr11[c] = numValueOf21;
                            objArr11[c2] = numValueOf22;
                            this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND topic_id = %d AND start = %d AND end = %d", objArr11)).stepThis().dispose();
                        } else {
                            i3 = i6;
                            arrayList = arrayList2;
                            Long lValueOf18 = Long.valueOf(j);
                            Integer numValueOf23 = Integer.valueOf(hole.start);
                            Integer numValueOf24 = Integer.valueOf(hole.end);
                            Object[] objArr12 = new Object[3];
                            objArr12[0] = lValueOf18;
                            objArr12[1] = numValueOf23;
                            objArr12[c] = numValueOf24;
                            this.database.executeFast(String.format(Locale.US, "DELETE FROM " + str + " WHERE uid = %d AND start = %d AND end = %d", objArr12)).stepThis().dispose();
                        }
                        i6 = i3 + 1;
                        arrayList2 = arrayList;
                        c = 2;
                        c2 = 3;
                    }
                }
            } catch (Exception e5) {
                e = e5;
                sQLiteCursor = null;
            }
        } catch (Throwable th3) {
            th = th3;
            sQLiteCursor = null;
        }
    }

    public void replaceMessageIfExists(final TLRPC.Message message, final ArrayList<TLRPC.User> arrayList, final ArrayList<TLRPC.Chat> arrayList2, final boolean z) {
        TLRPC.MessageMedia media;
        if (message == null) {
            return;
        }
        if (!AyuConfig.saveDeletedMessages || (media = MessageObject.getMedia(message)) == null || media.ttl_seconds == 0 || !((media.photo instanceof TLRPC.TL_photoEmpty) || (media.document instanceof TLRPC.TL_documentEmpty))) {
            this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda121
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$replaceMessageIfExists$218(message, z, arrayList, arrayList2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(31:(1:43)(1:44)|45|(2:47|(3:49|208|126)(8:50|51|(3:198|53|(2:(1:56)|61))|184|(1:186)|(1:188)|(1:190)|191))(1:63)|62|200|65|(1:67)(1:72)|73|(1:79)(1:78)|80|(1:82)(1:83)|84|(1:86)(1:87)|88|(1:90)(1:(1:92)(1:93))|94|(1:96)(1:97)|98|(3:100|(1:102)(1:103)|104)(1:105)|106|(1:108)(1:109)|(3:111|(1:113)(1:115)|114)|116|(1:118)(1:119)|120|(1:122)|(1:124)|125|207|126|40) */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0112, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0113, code lost:
    
        r9 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0116, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0117, code lost:
    
        r9 = r14;
     */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x02b9 A[Catch: all -> 0x003f, Exception -> 0x0045, TRY_ENTER, TryCatch #11 {Exception -> 0x0045, all -> 0x003f, blocks: (B:7:0x0034, B:24:0x0059, B:36:0x006f, B:38:0x007c, B:39:0x007f, B:45:0x00af, B:126:0x022b, B:50:0x00ce, B:122:0x0222, B:124:0x0227, B:63:0x00f6, B:127:0x0237, B:136:0x024d, B:142:0x0265, B:143:0x026e, B:155:0x02b9, B:156:0x02bc, B:158:0x02c6, B:159:0x02d1, B:161:0x02d7, B:162:0x02eb, B:164:0x02f1, B:165:0x0305, B:167:0x0320, B:34:0x006b, B:172:0x0332, B:173:0x0335), top: B:204:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:158:0x02c6 A[Catch: all -> 0x003f, Exception -> 0x0045, TryCatch #11 {Exception -> 0x0045, all -> 0x003f, blocks: (B:7:0x0034, B:24:0x0059, B:36:0x006f, B:38:0x007c, B:39:0x007f, B:45:0x00af, B:126:0x022b, B:50:0x00ce, B:122:0x0222, B:124:0x0227, B:63:0x00f6, B:127:0x0237, B:136:0x024d, B:142:0x0265, B:143:0x026e, B:155:0x02b9, B:156:0x02bc, B:158:0x02c6, B:159:0x02d1, B:161:0x02d7, B:162:0x02eb, B:164:0x02f1, B:165:0x0305, B:167:0x0320, B:34:0x006b, B:172:0x0332, B:173:0x0335), top: B:204:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0320 A[Catch: all -> 0x003f, Exception -> 0x0045, TRY_LEAVE, TryCatch #11 {Exception -> 0x0045, all -> 0x003f, blocks: (B:7:0x0034, B:24:0x0059, B:36:0x006f, B:38:0x007c, B:39:0x007f, B:45:0x00af, B:126:0x022b, B:50:0x00ce, B:122:0x0222, B:124:0x0227, B:63:0x00f6, B:127:0x0237, B:136:0x024d, B:142:0x0265, B:143:0x026e, B:155:0x02b9, B:156:0x02bc, B:158:0x02c6, B:159:0x02d1, B:161:0x02d7, B:162:0x02eb, B:164:0x02f1, B:165:0x0305, B:167:0x0320, B:34:0x006b, B:172:0x0332, B:173:0x0335), top: B:204:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:170:0x032c  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x0332 A[Catch: all -> 0x003f, Exception -> 0x0045, TRY_ENTER, TryCatch #11 {Exception -> 0x0045, all -> 0x003f, blocks: (B:7:0x0034, B:24:0x0059, B:36:0x006f, B:38:0x007c, B:39:0x007f, B:45:0x00af, B:126:0x022b, B:50:0x00ce, B:122:0x0222, B:124:0x0227, B:63:0x00f6, B:127:0x0237, B:136:0x024d, B:142:0x0265, B:143:0x026e, B:155:0x02b9, B:156:0x02bc, B:158:0x02c6, B:159:0x02d1, B:161:0x02d7, B:162:0x02eb, B:164:0x02f1, B:165:0x0305, B:167:0x0320, B:34:0x006b, B:172:0x0332, B:173:0x0335), top: B:204:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x033d  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x0342  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x0347  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0350  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0355  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x035a  */
    /* JADX WARN: Removed duplicated region for block: B:215:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:217:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006b A[Catch: all -> 0x003f, Exception -> 0x0045, TRY_ENTER, TryCatch #11 {Exception -> 0x0045, all -> 0x003f, blocks: (B:7:0x0034, B:24:0x0059, B:36:0x006f, B:38:0x007c, B:39:0x007f, B:45:0x00af, B:126:0x022b, B:50:0x00ce, B:122:0x0222, B:124:0x0227, B:63:0x00f6, B:127:0x0237, B:136:0x024d, B:142:0x0265, B:143:0x026e, B:155:0x02b9, B:156:0x02bc, B:158:0x02c6, B:159:0x02d1, B:161:0x02d7, B:162:0x02eb, B:164:0x02f1, B:165:0x0305, B:167:0x0320, B:34:0x006b, B:172:0x0332, B:173:0x0335), top: B:204:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007c A[Catch: all -> 0x003f, Exception -> 0x0045, TryCatch #11 {Exception -> 0x0045, all -> 0x003f, blocks: (B:7:0x0034, B:24:0x0059, B:36:0x006f, B:38:0x007c, B:39:0x007f, B:45:0x00af, B:126:0x022b, B:50:0x00ce, B:122:0x0222, B:124:0x0227, B:63:0x00f6, B:127:0x0237, B:136:0x024d, B:142:0x0265, B:143:0x026e, B:155:0x02b9, B:156:0x02bc, B:158:0x02c6, B:159:0x02d1, B:161:0x02d7, B:162:0x02eb, B:164:0x02f1, B:165:0x0305, B:167:0x0320, B:34:0x006b, B:172:0x0332, B:173:0x0335), top: B:204:0x0007 }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$replaceMessageIfExists$218(org.telegram.tgnet.TLRPC.Message r27, boolean r28, java.util.ArrayList r29, java.util.ArrayList r30) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 862
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$replaceMessageIfExists$218(org.telegram.tgnet.TLRPC$Message, boolean, java.util.ArrayList, java.util.ArrayList):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$replaceMessageIfExists$216(MessageObject messageObject, ArrayList arrayList) {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.replaceMessagesObjects, Long.valueOf(messageObject.getDialogId()), arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$replaceMessageIfExists$217(ArrayList arrayList) {
        if (getMessagesController().getSavedMessagesController().updateSavedDialogs(arrayList)) {
            getMessagesController().getSavedMessagesController().update();
        }
    }

    public void loadMessageAttachPaths(final ArrayList<MessageObject> arrayList, final Runnable runnable) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda134
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadMessageAttachPaths$219(arrayList, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadMessageAttachPaths$219(ArrayList arrayList, Runnable runnable) {
        NativeByteBuffer nativeByteBufferByteBufferValue;
        long clientUserId = getUserConfig().getClientUserId();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            MessageObject messageObject = (MessageObject) obj;
            if (!messageObject.scheduled && !messageObject.isQuickReply()) {
                SQLiteCursor sQLiteCursorQueryFinalized = null;
                try {
                    try {
                        sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT data FROM messages_v2 WHERE uid = ? AND mid = ?", Long.valueOf(messageObject.getDialogId()), Integer.valueOf(messageObject.getId()));
                        if (sQLiteCursorQueryFinalized.next() && (nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0)) != null) {
                            TLRPC.Message messageTLdeserialize = TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                            messageTLdeserialize.readAttachPath(nativeByteBufferByteBufferValue, clientUserId);
                            nativeByteBufferByteBufferValue.reuse();
                            messageObject.messageOwner.attachPath = messageTLdeserialize.attachPath;
                            messageObject.checkMediaExistance();
                        }
                    } catch (Exception e) {
                        FileLog.m1160e(e);
                        if (sQLiteCursorQueryFinalized != null) {
                        }
                    }
                    sQLiteCursorQueryFinalized.dispose();
                } catch (Throwable th) {
                    if (sQLiteCursorQueryFinalized != null) {
                        sQLiteCursorQueryFinalized.dispose();
                    }
                    throw th;
                }
            }
        }
        AndroidUtilities.runOnUIThread(runnable);
    }

    public void putMessages(final TLRPC.messages_Messages messages_messages, final long j, final int i, final int i2, final boolean z, final int i3, final long j2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda142
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$putMessages$222(i3, messages_messages, j, j2, i, i2, z);
            }
        });
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putMessages$222(int r53, org.telegram.tgnet.TLRPC.messages_Messages r54, long r55, long r57, int r59, int r60, boolean r61) {
        /*
            Method dump skipped, instructions count: 3874
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$putMessages$222(int, org.telegram.tgnet.TLRPC$messages_Messages, long, long, int, int, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putMessages$220(ArrayList arrayList) {
        getFileLoader().cancelLoadFiles(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putMessages$221(ArrayList arrayList) {
        if (getMessagesController().getSavedMessagesController().updateSavedDialogs(arrayList)) {
            getMessagesController().getSavedMessagesController().update();
        }
    }

    public static void addUsersAndChatsFromMessage(TLRPC.Message message, ArrayList<Long> arrayList, ArrayList<Long> arrayList2, ArrayList<Long> arrayList3) {
        TL_stars.StarGift starGift;
        TLRPC.Peer peer;
        TLRPC.Peer peer2;
        String str;
        TLRPC.MessageFwdHeader messageFwdHeader;
        TLRPC.Peer peer3;
        TLRPC.Peer peer4;
        TLRPC.WebPage webPage;
        TLRPC.Peer peer5;
        TL_stories.StoryFwdHeader storyFwdHeader;
        TL_stories.StoryItem storyItem;
        TLRPC.Peer peer6;
        TLRPC.Peer peer7;
        long fromChatId = MessageObject.getFromChatId(message);
        if (DialogObject.isUserDialog(fromChatId)) {
            if (!arrayList.contains(Long.valueOf(fromChatId))) {
                arrayList.add(Long.valueOf(fromChatId));
            }
        } else if (DialogObject.isChatDialog(fromChatId)) {
            long j = -fromChatId;
            if (!arrayList2.contains(Long.valueOf(j))) {
                arrayList2.add(Long.valueOf(j));
            }
        }
        long j2 = message.via_bot_id;
        if (j2 != 0 && !arrayList.contains(Long.valueOf(j2))) {
            arrayList.add(Long.valueOf(message.via_bot_id));
        }
        TLRPC.MessageAction messageAction = message.action;
        if (messageAction != null) {
            long j3 = messageAction.user_id;
            if (j3 != 0 && !arrayList.contains(Long.valueOf(j3))) {
                arrayList.add(Long.valueOf(message.action.user_id));
            }
            long j4 = message.action.channel_id;
            if (j4 != 0 && !arrayList2.contains(Long.valueOf(j4))) {
                arrayList2.add(Long.valueOf(message.action.channel_id));
            }
            long j5 = message.action.chat_id;
            if (j5 != 0 && !arrayList2.contains(Long.valueOf(j5))) {
                arrayList2.add(Long.valueOf(message.action.chat_id));
            }
            TLRPC.MessageAction messageAction2 = message.action;
            if (messageAction2 instanceof TLRPC.TL_messageActionGiftCode) {
                addLoadPeerInfo(((TLRPC.TL_messageActionGiftCode) messageAction2).boost_peer, arrayList, arrayList2);
            }
            TLRPC.MessageAction messageAction3 = message.action;
            if (messageAction3 instanceof TLRPC.TL_messageActionGeoProximityReached) {
                TLRPC.TL_messageActionGeoProximityReached tL_messageActionGeoProximityReached = (TLRPC.TL_messageActionGeoProximityReached) messageAction3;
                addLoadPeerInfo(tL_messageActionGeoProximityReached.from_id, arrayList, arrayList2);
                addLoadPeerInfo(tL_messageActionGeoProximityReached.to_id, arrayList, arrayList2);
            }
            if (!message.action.users.isEmpty()) {
                for (int i = 0; i < message.action.users.size(); i++) {
                    Long l = (Long) message.action.users.get(i);
                    if (!arrayList.contains(l)) {
                        arrayList.add(l);
                    }
                }
            }
        }
        if (!message.entities.isEmpty()) {
            for (int i2 = 0; i2 < message.entities.size(); i2++) {
                TLRPC.MessageEntity messageEntity = (TLRPC.MessageEntity) message.entities.get(i2);
                if (messageEntity instanceof TLRPC.TL_messageEntityMentionName) {
                    arrayList.add(Long.valueOf(((TLRPC.TL_messageEntityMentionName) messageEntity).user_id));
                } else if (messageEntity instanceof TLRPC.TL_inputMessageEntityMentionName) {
                    arrayList.add(Long.valueOf(((TLRPC.TL_inputMessageEntityMentionName) messageEntity).user_id.user_id));
                } else if (arrayList3 != null && (messageEntity instanceof TLRPC.TL_messageEntityCustomEmoji)) {
                    arrayList3.add(Long.valueOf(((TLRPC.TL_messageEntityCustomEmoji) messageEntity).document_id));
                }
            }
        }
        TLRPC.MessageAction messageAction4 = message.action;
        if (messageAction4 instanceof TLRPC.TL_messageActionStarGift) {
            TL_stars.StarGift starGift2 = ((TLRPC.TL_messageActionStarGift) messageAction4).gift;
            if (starGift2 != null && (peer7 = starGift2.released_by) != null) {
                addLoadPeerInfo(peer7, arrayList, arrayList2);
            }
        } else if ((messageAction4 instanceof TLRPC.TL_messageActionStarGiftUnique) && (starGift = ((TLRPC.TL_messageActionStarGiftUnique) messageAction4).gift) != null && (peer = starGift.released_by) != null) {
            addLoadPeerInfo(peer, arrayList, arrayList2);
        }
        TLRPC.MessageMedia messageMedia = message.media;
        if (messageMedia != null) {
            long j6 = messageMedia.user_id;
            if (j6 != 0 && !arrayList.contains(Long.valueOf(j6))) {
                arrayList.add(Long.valueOf(message.media.user_id));
            }
            TLRPC.MessageMedia messageMedia2 = message.media;
            if (messageMedia2 instanceof TLRPC.TL_messageMediaGiveaway) {
                ArrayList arrayList4 = ((TLRPC.TL_messageMediaGiveaway) messageMedia2).channels;
                int size = arrayList4.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayList4.get(i3);
                    i3++;
                    Long l2 = (Long) obj;
                    if (!arrayList2.contains(l2)) {
                        arrayList2.add(l2);
                    }
                }
            }
            TLRPC.MessageMedia messageMedia3 = message.media;
            if (messageMedia3 instanceof TLRPC.TL_messageMediaGiveawayResults) {
                ArrayList arrayList5 = ((TLRPC.TL_messageMediaGiveawayResults) messageMedia3).winners;
                int size2 = arrayList5.size();
                int i4 = 0;
                while (i4 < size2) {
                    Object obj2 = arrayList5.get(i4);
                    i4++;
                    Long l3 = (Long) obj2;
                    if (!arrayList.contains(l3)) {
                        arrayList.add(l3);
                    }
                }
            }
            TLRPC.MessageMedia messageMedia4 = message.media;
            if (messageMedia4 instanceof TLRPC.TL_messageMediaPoll) {
                TLRPC.TL_messageMediaPoll tL_messageMediaPoll = (TLRPC.TL_messageMediaPoll) messageMedia4;
                if (!tL_messageMediaPoll.results.recent_voters.isEmpty()) {
                    for (int i5 = 0; i5 < tL_messageMediaPoll.results.recent_voters.size(); i5++) {
                        addLoadPeerInfo((TLRPC.Peer) tL_messageMediaPoll.results.recent_voters.get(i5), arrayList, arrayList2);
                    }
                }
            }
            TLRPC.MessageMedia messageMedia5 = message.media;
            if ((messageMedia5 instanceof TLRPC.TL_messageMediaStory) && (storyItem = messageMedia5.storyItem) != null) {
                TL_stories.StoryFwdHeader storyFwdHeader2 = storyItem.fwd_from;
                if (storyFwdHeader2 != null) {
                    addLoadPeerInfo(storyFwdHeader2.from, arrayList, arrayList2);
                }
                TL_stories.StoryItem storyItem2 = message.media.storyItem;
                if (storyItem2 != null && storyItem2.media_areas != null) {
                    for (int i6 = 0; i6 < message.media.storyItem.media_areas.size(); i6++) {
                        if (message.media.storyItem.media_areas.get(i6) instanceof TL_stories.TL_mediaAreaChannelPost) {
                            long j7 = ((TL_stories.TL_mediaAreaChannelPost) message.media.storyItem.media_areas.get(i6)).channel_id;
                            if (!arrayList2.contains(Long.valueOf(j7))) {
                                arrayList2.add(Long.valueOf(j7));
                            }
                        }
                    }
                }
                TL_stories.StoryItem storyItem3 = message.media.storyItem;
                if (storyItem3 != null && (peer6 = storyItem3.from_id) != null) {
                    addLoadPeerInfo(peer6, arrayList, arrayList2);
                }
            }
            TLRPC.MessageMedia messageMedia6 = message.media;
            if ((messageMedia6 instanceof TLRPC.TL_messageMediaWebPage) && (webPage = messageMedia6.webpage) != null && webPage.attributes != null) {
                for (int i7 = 0; i7 < message.media.webpage.attributes.size(); i7++) {
                    if (message.media.webpage.attributes.get(i7) instanceof TLRPC.TL_webPageAttributeStory) {
                        TLRPC.TL_webPageAttributeStory tL_webPageAttributeStory = (TLRPC.TL_webPageAttributeStory) message.media.webpage.attributes.get(i7);
                        TL_stories.StoryItem storyItem4 = tL_webPageAttributeStory.storyItem;
                        if (storyItem4 != null && (storyFwdHeader = storyItem4.fwd_from) != null) {
                            addLoadPeerInfo(storyFwdHeader.from, arrayList, arrayList2);
                        }
                        TL_stories.StoryItem storyItem5 = tL_webPageAttributeStory.storyItem;
                        if (storyItem5 != null && storyItem5.media_areas != null) {
                            for (int i8 = 0; i8 < tL_webPageAttributeStory.storyItem.media_areas.size(); i8++) {
                                if (tL_webPageAttributeStory.storyItem.media_areas.get(i8) instanceof TL_stories.TL_mediaAreaChannelPost) {
                                    long j8 = ((TL_stories.TL_mediaAreaChannelPost) tL_webPageAttributeStory.storyItem.media_areas.get(i8)).channel_id;
                                    if (!arrayList2.contains(Long.valueOf(j8))) {
                                        arrayList2.add(Long.valueOf(j8));
                                    }
                                }
                            }
                        }
                        TL_stories.StoryItem storyItem6 = tL_webPageAttributeStory.storyItem;
                        if (storyItem6 != null && (peer5 = storyItem6.from_id) != null) {
                            addLoadPeerInfo(peer5, arrayList, arrayList2);
                        }
                    }
                }
            }
            TLRPC.Peer peer8 = message.media.peer;
            if (peer8 != null) {
                addLoadPeerInfo(peer8, arrayList, arrayList2);
            }
        }
        TLRPC.MessageReplies messageReplies = message.replies;
        if (messageReplies != null) {
            int size3 = messageReplies.recent_repliers.size();
            for (int i9 = 0; i9 < size3; i9++) {
                addLoadPeerInfo((TLRPC.Peer) message.replies.recent_repliers.get(i9), arrayList, arrayList2);
            }
        }
        TLRPC.MessageReplyHeader messageReplyHeader = message.reply_to;
        if (messageReplyHeader != null && (peer4 = messageReplyHeader.reply_to_peer_id) != null) {
            addLoadPeerInfo(peer4, arrayList, arrayList2);
        }
        TLRPC.MessageFwdHeader messageFwdHeader2 = message.fwd_from;
        if (messageFwdHeader2 != null) {
            addLoadPeerInfo(messageFwdHeader2.from_id, arrayList, arrayList2);
            addLoadPeerInfo(message.fwd_from.saved_from_peer, arrayList, arrayList2);
        }
        TLRPC.MessageReplyHeader messageReplyHeader2 = message.reply_to;
        if (messageReplyHeader2 != null && (messageFwdHeader = messageReplyHeader2.reply_from) != null && (peer3 = messageFwdHeader.from_id) != null) {
            addLoadPeerInfo(peer3, arrayList, arrayList2);
        }
        HashMap map = message.params;
        if (map != null && (str = (String) map.get("fwd_peer")) != null) {
            long jLongValue = Utilities.parseLong(str).longValue();
            if (jLongValue < 0) {
                long j9 = -jLongValue;
                if (!arrayList2.contains(Long.valueOf(j9))) {
                    arrayList2.add(Long.valueOf(j9));
                }
            }
        }
        TLRPC.TL_messageReactions tL_messageReactions = message.reactions;
        if (tL_messageReactions == null || tL_messageReactions.top_reactors == null) {
            return;
        }
        for (int i10 = 0; i10 < message.reactions.top_reactors.size(); i10++) {
            TLRPC.MessageReactor messageReactor = (TLRPC.MessageReactor) message.reactions.top_reactors.get(i10);
            if (messageReactor != null && (peer2 = messageReactor.peer_id) != null) {
                addLoadPeerInfo(peer2, arrayList, arrayList2);
            }
        }
    }

    public static void addLoadPeerInfo(TLRPC.Peer peer, ArrayList<Long> arrayList, ArrayList<Long> arrayList2) {
        if (peer instanceof TLRPC.TL_peerUser) {
            if (arrayList.contains(Long.valueOf(peer.user_id))) {
                return;
            }
            arrayList.add(Long.valueOf(peer.user_id));
        } else if (peer instanceof TLRPC.TL_peerChannel) {
            if (arrayList2.contains(Long.valueOf(peer.channel_id))) {
                return;
            }
            arrayList2.add(Long.valueOf(peer.channel_id));
        } else {
            if (!(peer instanceof TLRPC.TL_peerChat) || arrayList2.contains(Long.valueOf(peer.chat_id))) {
                return;
            }
            arrayList2.add(Long.valueOf(peer.chat_id));
        }
    }

    public void getDialogs(final int i, final int i2, final int i3, boolean z) {
        LongSparseArray drafts;
        int size;
        long[] jArr = null;
        if (z && (size = (drafts = getMediaDataController().getDrafts()).size()) > 0) {
            jArr = new long[size];
            for (int i4 = 0; i4 < size; i4++) {
                if (((LongSparseArray) drafts.valueAt(i4)).get(0L) != null) {
                    jArr[i4] = drafts.keyAt(i4);
                }
            }
        }
        final long[] jArr2 = jArr;
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$getDialogs$224(i, i2, i3, jArr2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x02da A[Catch: all -> 0x028d, Exception -> 0x02a6, TryCatch #18 {Exception -> 0x02a6, blocks: (B:134:0x02c5, B:136:0x02cb, B:138:0x02d1, B:139:0x02d4, B:141:0x02da, B:143:0x02ea, B:145:0x02f4, B:147:0x02fc, B:149:0x0306, B:150:0x030d, B:151:0x0317, B:153:0x031f, B:155:0x032a, B:127:0x02a2, B:132:0x02b3), top: B:403:0x02cb }] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x02f4 A[Catch: all -> 0x028d, Exception -> 0x02a6, TryCatch #18 {Exception -> 0x02a6, blocks: (B:134:0x02c5, B:136:0x02cb, B:138:0x02d1, B:139:0x02d4, B:141:0x02da, B:143:0x02ea, B:145:0x02f4, B:147:0x02fc, B:149:0x0306, B:150:0x030d, B:151:0x0317, B:153:0x031f, B:155:0x032a, B:127:0x02a2, B:132:0x02b3), top: B:403:0x02cb }] */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0472  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x04a9  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x04b1 A[Catch: all -> 0x043c, Exception -> 0x04a7, TRY_LEAVE, TryCatch #16 {Exception -> 0x04a7, blocks: (B:234:0x04a3, B:240:0x04ad, B:242:0x04b1), top: B:401:0x04a3 }] */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0688  */
    /* JADX WARN: Removed duplicated region for block: B:373:0x06e6  */
    /* JADX WARN: Removed duplicated region for block: B:378:0x06ef  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x02cb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:470:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0175  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x017c  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x017e  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01be A[Catch: all -> 0x00db, Exception -> 0x00e0, TRY_ENTER, TRY_LEAVE, TryCatch #14 {Exception -> 0x00e0, blocks: (B:22:0x00b9, B:24:0x00c6, B:26:0x00cc, B:33:0x00e8, B:34:0x00f5, B:38:0x0100, B:44:0x0133, B:66:0x019b, B:68:0x019f, B:73:0x01be, B:77:0x01dd, B:86:0x0223), top: B:399:0x00b9 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01dd A[Catch: all -> 0x00db, Exception -> 0x00e0, TRY_ENTER, TRY_LEAVE, TryCatch #14 {Exception -> 0x00e0, blocks: (B:22:0x00b9, B:24:0x00c6, B:26:0x00cc, B:33:0x00e8, B:34:0x00f5, B:38:0x0100, B:44:0x0133, B:66:0x019b, B:68:0x019f, B:73:0x01be, B:77:0x01dd, B:86:0x0223), top: B:399:0x00b9 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01f1 A[Catch: all -> 0x0294, Exception -> 0x02ae, TryCatch #2 {Exception -> 0x02ae, blocks: (B:20:0x00af, B:41:0x0111, B:50:0x0140, B:54:0x015f, B:58:0x0176, B:62:0x017f, B:70:0x01a6, B:75:0x01d0, B:80:0x01eb, B:82:0x01f1, B:84:0x01fc, B:88:0x0226, B:40:0x010b), top: B:380:0x00af }] */
    /* JADX WARN: Type inference failed for: r0v171, types: [org.telegram.tgnet.TLRPC$TL_dialogFolder] */
    /* JADX WARN: Type inference failed for: r0v89, types: [org.telegram.tgnet.TLRPC$TL_dialog] */
    /* JADX WARN: Type inference failed for: r0v90 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$getDialogs$224(int r33, int r34, int r35, long[] r36) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1779
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$getDialogs$224(int, int, int, long[]):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDialogs$223(LongSparseArray longSparseArray) {
        MediaDataController mediaDataController = getMediaDataController();
        mediaDataController.clearDraftsFolderIds();
        if (longSparseArray != null) {
            int size = longSparseArray.size();
            for (int i = 0; i < size; i++) {
                mediaDataController.setDraftFolderId(longSparseArray.keyAt(i), ((Integer) longSparseArray.valueAt(i)).intValue());
            }
        }
    }

    public static void createFirstHoles(long j, SQLitePreparedStatement sQLitePreparedStatement, SQLitePreparedStatement sQLitePreparedStatement2, int i, long j2) {
        int i2;
        int i3;
        sQLitePreparedStatement.requery();
        sQLitePreparedStatement.bindLong(1, j);
        if (j2 != 0) {
            sQLitePreparedStatement.bindLong(2, j2);
            i2 = 3;
        } else {
            i2 = 2;
        }
        int i4 = i2 + 1;
        sQLitePreparedStatement.bindInteger(i2, i == 1 ? 1 : 0);
        sQLitePreparedStatement.bindInteger(i4, i);
        sQLitePreparedStatement.step();
        for (int i5 = 0; i5 < 8; i5++) {
            sQLitePreparedStatement2.requery();
            sQLitePreparedStatement2.bindLong(1, j);
            if (j2 != 0) {
                sQLitePreparedStatement2.bindLong(2, j2);
                i3 = 3;
            } else {
                i3 = 2;
            }
            int i6 = i3 + 1;
            sQLitePreparedStatement2.bindInteger(i3, i5);
            int i7 = i3 + 2;
            sQLitePreparedStatement2.bindInteger(i6, i == 1 ? 1 : 0);
            sQLitePreparedStatement2.bindInteger(i7, i);
            sQLitePreparedStatement2.step();
        }
    }

    public void updateDialogData(final TLRPC.Dialog dialog) {
        if (dialog == null) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateDialogData$225(dialog);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$updateDialogData$225(org.telegram.tgnet.TLRPC.Dialog r7) throws java.lang.Throwable {
        /*
            r6 = this;
            r0 = 0
            org.telegram.SQLite.SQLiteDatabase r1 = r6.database     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            r2.<init>()     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            java.lang.String r3 = "SELECT data FROM dialogs WHERE did = "
            r2.append(r3)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            long r3 = r7.f1577id     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            r2.append(r3)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            r3 = 0
            java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            org.telegram.SQLite.SQLiteCursor r1 = r1.queryFinalized(r2, r3)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            boolean r2 = r1.next()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L56
            if (r2 != 0) goto L27
            r1.dispose()
            return
        L27:
            org.telegram.SQLite.SQLiteDatabase r2 = r6.database     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L56
            java.lang.String r3 = "UPDATE dialogs SET data = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r2 = r2.executeFast(r3)     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L56
            org.telegram.tgnet.NativeByteBuffer r3 = new org.telegram.tgnet.NativeByteBuffer     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c
            int r4 = r7.getObjectSize()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c
            r7.serializeToStream(r3)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c
            r4 = 1
            r2.bindByteBuffer(r4, r3)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c
            long r4 = r7.f1577id     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c
            r7 = 2
            r2.bindLong(r7, r4)     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c
            r2.step()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c
            r2.dispose()     // Catch: java.lang.Throwable -> L5a java.lang.Exception -> L5c
            r3.reuse()     // Catch: java.lang.Throwable -> L52 java.lang.Exception -> L56
            r1.dispose()
            return
        L52:
            r7 = move-exception
            r2 = r0
        L54:
            r0 = r1
            goto L72
        L56:
            r7 = move-exception
            r2 = r0
        L58:
            r0 = r1
            goto L63
        L5a:
            r7 = move-exception
            goto L54
        L5c:
            r7 = move-exception
            goto L58
        L5e:
            r7 = move-exception
            r2 = r0
            goto L72
        L61:
            r7 = move-exception
            r2 = r0
        L63:
            r6.checkSQLException(r7)     // Catch: java.lang.Throwable -> L71
            if (r0 == 0) goto L6b
            r0.dispose()
        L6b:
            if (r2 == 0) goto L70
            r2.dispose()
        L70:
            return
        L71:
            r7 = move-exception
        L72:
            if (r0 == 0) goto L77
            r0.dispose()
        L77:
            if (r2 == 0) goto L7c
            r2.dispose()
        L7c:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateDialogData$225(org.telegram.tgnet.TLRPC$Dialog):void");
    }

    /*  JADX ERROR: Type inference failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:77)
        */
    private void putDialogsInternal(org.telegram.tgnet.TLRPC.messages_Dialogs r32, int r33) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 1989
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.putDialogsInternal(org.telegram.tgnet.TLRPC$messages_Dialogs, int):void");
    }

    private int getDialogFolderIdInternal(long j) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                if (this.unknownDialogsIds.get(j) == null) {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT folder_id FROM dialogs WHERE did = ?", Long.valueOf(j));
                    iIntValue = sQLiteCursorQueryFinalized.next() ? sQLiteCursorQueryFinalized.intValue(0) : -1;
                    sQLiteCursorQueryFinalized.dispose();
                }
                return iIntValue;
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
                return 0;
            }
        } finally {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
        }
    }

    public void getDialogFolderId(final long j, final IntCallback intCallback) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda119
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getDialogFolderId$227(j, intCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDialogFolderId$227(long j, final IntCallback intCallback) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                if (this.unknownDialogsIds.get(j) == null) {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT folder_id FROM dialogs WHERE did = ?", Long.valueOf(j));
                    iIntValue = sQLiteCursorQueryFinalized.next() ? sQLiteCursorQueryFinalized.intValue(0) : -1;
                    sQLiteCursorQueryFinalized.dispose();
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda154
                    @Override // java.lang.Runnable
                    public final void run() {
                        intCallback.run(iIntValue);
                    }
                });
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public void setDialogsFolderId(final ArrayList<TLRPC.TL_folderPeer> arrayList, final ArrayList<TLRPC.TL_inputFolderPeer> arrayList2, final long j, final int i) {
        if (arrayList == null && arrayList2 == null && j == 0) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda52
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$setDialogsFolderId$228(arrayList, arrayList2, i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDialogsFolderId$228(ArrayList arrayList, ArrayList arrayList2, int i, long j) throws Throwable {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast;
        boolean z;
        SQLitePreparedStatement sQLitePreparedStatement = null;
        try {
            try {
                this.database.beginTransaction();
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE dialogs SET folder_id = ?, pinned = ? WHERE did = ?");
            } catch (Throwable th) {
                th = th;
            }
        } catch (Exception e) {
            e = e;
        }
        try {
            if (arrayList != null) {
                int size = arrayList.size();
                z = false;
                for (int i2 = 0; i2 < size; i2++) {
                    TLRPC.TL_folderPeer tL_folderPeer = (TLRPC.TL_folderPeer) arrayList.get(i2);
                    long peerDialogId = DialogObject.getPeerDialogId(tL_folderPeer.peer);
                    sQLitePreparedStatementExecuteFast.requery();
                    sQLitePreparedStatementExecuteFast.bindInteger(1, tL_folderPeer.folder_id);
                    if (tL_folderPeer.folder_id == 1) {
                        z = true;
                    }
                    sQLitePreparedStatementExecuteFast.bindInteger(2, 0);
                    sQLitePreparedStatementExecuteFast.bindLong(3, peerDialogId);
                    sQLitePreparedStatementExecuteFast.step();
                    this.unknownDialogsIds.remove(peerDialogId);
                }
            } else if (arrayList2 != null) {
                int size2 = arrayList2.size();
                z = false;
                for (int i3 = 0; i3 < size2; i3++) {
                    TLRPC.TL_inputFolderPeer tL_inputFolderPeer = (TLRPC.TL_inputFolderPeer) arrayList2.get(i3);
                    long peerDialogId2 = DialogObject.getPeerDialogId(tL_inputFolderPeer.peer);
                    sQLitePreparedStatementExecuteFast.requery();
                    sQLitePreparedStatementExecuteFast.bindInteger(1, tL_inputFolderPeer.folder_id);
                    if (tL_inputFolderPeer.folder_id == 1) {
                        z = true;
                    }
                    sQLitePreparedStatementExecuteFast.bindInteger(2, 0);
                    sQLitePreparedStatementExecuteFast.bindLong(3, peerDialogId2);
                    sQLitePreparedStatementExecuteFast.step();
                    this.unknownDialogsIds.remove(peerDialogId2);
                }
            } else {
                sQLitePreparedStatementExecuteFast.requery();
                sQLitePreparedStatementExecuteFast.bindInteger(1, i);
                boolean z2 = i == 1;
                sQLitePreparedStatementExecuteFast.bindInteger(2, 0);
                sQLitePreparedStatementExecuteFast.bindLong(3, j);
                sQLitePreparedStatementExecuteFast.step();
                z = z2;
            }
            sQLitePreparedStatementExecuteFast.dispose();
            this.database.commitTransaction();
            if (!z) {
                lambda$checkIfFolderEmpty$230(1);
            }
            resetAllUnreadCounters(false);
            SQLiteDatabase sQLiteDatabase = this.database;
            if (sQLiteDatabase != null) {
                sQLiteDatabase.commitTransaction();
            }
        } catch (Exception e2) {
            e = e2;
            sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
            checkSQLException(e);
            SQLiteDatabase sQLiteDatabase2 = this.database;
            if (sQLiteDatabase2 != null) {
                sQLiteDatabase2.commitTransaction();
            }
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
        } catch (Throwable th2) {
            th = th2;
            sQLitePreparedStatement = sQLitePreparedStatementExecuteFast;
            SQLiteDatabase sQLiteDatabase3 = this.database;
            if (sQLiteDatabase3 != null) {
                sQLiteDatabase3.commitTransaction();
            }
            if (sQLitePreparedStatement != null) {
                sQLitePreparedStatement.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: checkIfFolderEmptyInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$checkIfFolderEmpty$230(final int i) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                boolean z = true;
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT did FROM dialogs WHERE folder_id = ?", Integer.valueOf(i));
                while (sQLiteCursorQueryFinalized.next()) {
                    long jLongValue = sQLiteCursorQueryFinalized.longValue(0);
                    if (!DialogObject.isUserDialog(jLongValue) && !DialogObject.isEncryptedDialog(jLongValue)) {
                        TLRPC.Chat chat = getChat(-jLongValue);
                        if (ChatObject.isNotInChat(chat) || chat.migrated_to != null) {
                        }
                    }
                    z = false;
                }
                sQLiteCursorQueryFinalized.dispose();
                if (z) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda152
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$checkIfFolderEmptyInternal$229(i);
                        }
                    });
                    this.database.executeFast("DELETE FROM dialogs WHERE did = " + DialogObject.makeFolderDialogId(i)).stepThis().dispose();
                }
                sQLiteCursorQueryFinalized.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkIfFolderEmptyInternal$229(int i) {
        getMessagesController().onFolderEmpty(i);
    }

    public void checkIfFolderEmpty(final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda215
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkIfFolderEmpty$230(i);
            }
        });
    }

    public void unpinAllDialogsExceptNew(final ArrayList<Long> arrayList, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda179
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$unpinAllDialogsExceptNew$231(arrayList, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:72:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$unpinAllDialogsExceptNew$231(java.util.ArrayList r10, int r11) throws java.lang.Throwable {
        /*
            r9 = this;
            r0 = 0
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L91
            r1.<init>()     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L91
            org.telegram.SQLite.SQLiteDatabase r2 = r9.database     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L91
            java.util.Locale r3 = java.util.Locale.US     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L91
            java.lang.String r4 = "SELECT did, folder_id FROM dialogs WHERE pinned > 0 AND did NOT IN (%s)"
            java.lang.String r5 = ","
            java.lang.String r10 = android.text.TextUtils.join(r5, r10)     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L91
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L97
            r7 = 0
            r6[r7] = r10     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L97
            java.lang.String r10 = java.lang.String.format(r3, r4, r6)     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L91
            java.lang.Object[] r3 = new java.lang.Object[r7]     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L91
            org.telegram.SQLite.SQLiteCursor r10 = r2.queryFinalized(r10, r3)     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L91
        L22:
            boolean r2 = r10.next()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4f
            if (r2 == 0) goto L54
            long r2 = r10.longValue(r7)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4f
            int r4 = r10.intValue(r5)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4f
            if (r4 != r11) goto L22
            boolean r4 = org.telegram.messenger.DialogObject.isEncryptedDialog(r2)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4f
            if (r4 != 0) goto L22
            boolean r2 = org.telegram.messenger.DialogObject.isFolderDialogId(r2)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4f
            if (r2 != 0) goto L22
            long r2 = r10.longValue(r7)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4f
            java.lang.Long r2 = java.lang.Long.valueOf(r2)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4f
            r1.add(r2)     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4f
            goto L22
        L4a:
            r11 = move-exception
            r8 = r0
            r0 = r10
            r10 = r8
            goto La8
        L4f:
            r11 = move-exception
            r8 = r0
            r0 = r10
            r10 = r8
            goto L9a
        L54:
            r10.dispose()     // Catch: java.lang.Throwable -> L4a java.lang.Exception -> L4f
            boolean r10 = r1.isEmpty()     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L91
            if (r10 != 0) goto La7
            org.telegram.SQLite.SQLiteDatabase r10 = r9.database     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L91
            java.lang.String r11 = "UPDATE dialogs SET pinned = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r10 = r10.executeFast(r11)     // Catch: java.lang.Throwable -> L8e java.lang.Exception -> L91
            r11 = 0
        L66:
            int r2 = r1.size()     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88
            if (r11 >= r2) goto L8a
            java.lang.Object r2 = r1.get(r11)     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88
            java.lang.Long r2 = (java.lang.Long) r2     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88
            long r2 = r2.longValue()     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88
            r10.requery()     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88
            r10.bindInteger(r5, r7)     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88
            r4 = 2
            r10.bindLong(r4, r2)     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88
            r10.step()     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88
            int r11 = r11 + 1
            goto L66
        L86:
            r11 = move-exception
            goto La8
        L88:
            r11 = move-exception
            goto L9a
        L8a:
            r10.dispose()     // Catch: java.lang.Throwable -> L86 java.lang.Exception -> L88
            return
        L8e:
            r11 = move-exception
        L8f:
            r10 = r0
            goto La8
        L91:
            r11 = move-exception
        L92:
            r10 = r0
            goto L9a
        L94:
            r10 = move-exception
            r11 = r10
            goto L8f
        L97:
            r10 = move-exception
            r11 = r10
            goto L92
        L9a:
            r9.checkSQLException(r11)     // Catch: java.lang.Throwable -> L86
            if (r0 == 0) goto La2
            r0.dispose()
        La2:
            if (r10 == 0) goto La7
            r10.dispose()
        La7:
            return
        La8:
            if (r0 == 0) goto Lad
            r0.dispose()
        Lad:
            if (r10 == 0) goto Lb2
            r10.dispose()
        Lb2:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$unpinAllDialogsExceptNew$231(java.util.ArrayList, int):void");
    }

    public void setDialogUnread(final long j, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda232
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setDialogUnread$232(j, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0044 A[Catch: all -> 0x002f, Exception -> 0x0031, TryCatch #4 {Exception -> 0x0031, blocks: (B:13:0x002b, B:29:0x0044, B:31:0x0049, B:30:0x0047, B:34:0x0067, B:35:0x006a, B:25:0x003d), top: B:46:0x0002, outer: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0047 A[Catch: all -> 0x002f, Exception -> 0x0031, TryCatch #4 {Exception -> 0x0031, blocks: (B:13:0x002b, B:29:0x0044, B:31:0x0049, B:30:0x0047, B:34:0x0067, B:35:0x006a, B:25:0x003d), top: B:46:0x0002, outer: #5 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0067 A[Catch: all -> 0x002f, Exception -> 0x0031, TRY_ENTER, TryCatch #4 {Exception -> 0x0031, blocks: (B:13:0x002b, B:29:0x0044, B:31:0x0049, B:30:0x0047, B:34:0x0067, B:35:0x006a, B:25:0x003d), top: B:46:0x0002, outer: #5 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$setDialogUnread$232(long r6, boolean r8) {
        /*
            r5 = this;
            r0 = 0
            r1 = 0
            org.telegram.SQLite.SQLiteDatabase r2 = r5.database     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            r3.<init>()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            java.lang.String r4 = "SELECT flags FROM dialogs WHERE did = "
            r3.append(r4)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            r3.append(r6)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            java.lang.Object[] r4 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            org.telegram.SQLite.SQLiteCursor r2 = r2.queryFinalized(r3, r4)     // Catch: java.lang.Throwable -> L33 java.lang.Exception -> L36
            boolean r3 = r2.next()     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L28
            if (r3 == 0) goto L2a
            int r3 = r2.intValue(r1)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L28
            goto L2b
        L26:
            r6 = move-exception
            goto L65
        L28:
            r3 = move-exception
            goto L38
        L2a:
            r3 = 0
        L2b:
            r2.dispose()     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            goto L41
        L2f:
            r6 = move-exception
            goto L72
        L31:
            r6 = move-exception
            goto L6b
        L33:
            r6 = move-exception
            r2 = r0
            goto L65
        L36:
            r3 = move-exception
            r2 = r0
        L38:
            r5.checkSQLException(r3)     // Catch: java.lang.Throwable -> L26
            if (r2 == 0) goto L40
            r2.dispose()     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
        L40:
            r3 = 0
        L41:
            r2 = 1
            if (r8 == 0) goto L47
            r8 = r3 | 1
            goto L49
        L47:
            r8 = r3 & (-2)
        L49:
            org.telegram.SQLite.SQLiteDatabase r3 = r5.database     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            java.lang.String r4 = "UPDATE dialogs SET flags = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r0 = r3.executeFast(r4)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            r0.bindInteger(r2, r8)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            r8 = 2
            r0.bindLong(r8, r6)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            r0.step()     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            r0.dispose()     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
            r5.resetAllUnreadCounters(r1)     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
        L61:
            r0.dispose()
            goto L71
        L65:
            if (r2 == 0) goto L6a
            r2.dispose()     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
        L6a:
            throw r6     // Catch: java.lang.Throwable -> L2f java.lang.Exception -> L31
        L6b:
            r5.checkSQLException(r6)     // Catch: java.lang.Throwable -> L2f
            if (r0 == 0) goto L71
            goto L61
        L71:
            return
        L72:
            if (r0 == 0) goto L77
            r0.dispose()
        L77:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$setDialogUnread$232(long, boolean):void");
    }

    public void setDialogViewThreadAsMessages(final long j, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda38
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setDialogViewThreadAsMessages$233(j, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:26:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0042  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$setDialogViewThreadAsMessages$233(long r6, boolean r8) {
        /*
            r5 = this;
            r0 = 0
            r1 = 0
            org.telegram.SQLite.SQLiteDatabase r2 = r5.database     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35
            r3.<init>()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35
            java.lang.String r4 = "SELECT flags FROM dialogs WHERE did = "
            r3.append(r4)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35
            r3.append(r6)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35
            java.lang.String r3 = r3.toString()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35
            java.lang.Object[] r4 = new java.lang.Object[r1]     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35
            org.telegram.SQLite.SQLiteCursor r2 = r2.queryFinalized(r3, r4)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35
            boolean r3 = r2.next()     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L28
            if (r3 == 0) goto L2a
            int r1 = r2.intValue(r1)     // Catch: java.lang.Throwable -> L26 java.lang.Exception -> L28
            goto L2a
        L26:
            r6 = move-exception
            goto L5e
        L28:
            r3 = move-exception
            goto L37
        L2a:
            r2.dispose()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            goto L3d
        L2e:
            r6 = move-exception
            goto L6b
        L30:
            r6 = move-exception
            goto L64
        L32:
            r6 = move-exception
            r2 = r0
            goto L5e
        L35:
            r3 = move-exception
            r2 = r0
        L37:
            r5.checkSQLException(r3)     // Catch: java.lang.Throwable -> L26
            if (r2 == 0) goto L3d
            goto L2a
        L3d:
            if (r8 == 0) goto L42
            r8 = r1 | 64
            goto L44
        L42:
            r8 = r1 & (-65)
        L44:
            org.telegram.SQLite.SQLiteDatabase r1 = r5.database     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            java.lang.String r2 = "UPDATE dialogs SET flags = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r0 = r1.executeFast(r2)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            r1 = 1
            r0.bindInteger(r1, r8)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            r8 = 2
            r0.bindLong(r8, r6)     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            r0.step()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
            r0.dispose()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
        L5a:
            r0.dispose()
            goto L6a
        L5e:
            if (r2 == 0) goto L63
            r2.dispose()     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
        L63:
            throw r6     // Catch: java.lang.Throwable -> L2e java.lang.Exception -> L30
        L64:
            r5.checkSQLException(r6)     // Catch: java.lang.Throwable -> L2e
            if (r0 == 0) goto L6a
            goto L5a
        L6a:
            return
        L6b:
            if (r0 == 0) goto L70
            r0.dispose()
        L70:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$setDialogViewThreadAsMessages$233(long, boolean):void");
    }

    public void resetAllUnreadCounters(boolean z) {
        int size = this.dialogFilters.size();
        for (int i = 0; i < size; i++) {
            MessagesController.DialogFilter dialogFilter = this.dialogFilters.get(i);
            if (z) {
                if ((dialogFilter.flags & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_MUTED) != 0) {
                    dialogFilter.pendingUnreadCount = -1;
                }
            } else {
                dialogFilter.pendingUnreadCount = -1;
            }
        }
        calcUnreadCounters(false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda244
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$resetAllUnreadCounters$234();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resetAllUnreadCounters$234() {
        ArrayList<MessagesController.DialogFilter> arrayList = getMessagesController().dialogFilters;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            arrayList.get(i).unreadCount = arrayList.get(i).pendingUnreadCount;
        }
        this.mainUnreadCount = this.pendingMainUnreadCount;
        this.archiveUnreadCount = this.pendingArchiveUnreadCount;
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_READ_DIALOG_MESSAGE));
    }

    public void setDialogPinned(final long j, final int i) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda208
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setDialogPinned$235(i, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDialogPinned$235(int i, long j) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE dialogs SET pinned = ? WHERE did = ?");
                sQLitePreparedStatementExecuteFast.bindInteger(1, i);
                sQLitePreparedStatementExecuteFast.bindLong(2, j);
                sQLitePreparedStatementExecuteFast.step();
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void setDialogsPinned(final ArrayList<Long> arrayList, final ArrayList<Integer> arrayList2) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda212
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setDialogsPinned$236(arrayList, arrayList2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDialogsPinned$236(ArrayList arrayList, ArrayList arrayList2) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                sQLitePreparedStatementExecuteFast = this.database.executeFast("UPDATE dialogs SET pinned = ? WHERE did = ?");
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    sQLitePreparedStatementExecuteFast.requery();
                    sQLitePreparedStatementExecuteFast.bindInteger(1, ((Integer) arrayList2.get(i)).intValue());
                    sQLitePreparedStatementExecuteFast.bindLong(2, ((Long) arrayList.get(i)).longValue());
                    sQLitePreparedStatementExecuteFast.step();
                }
                sQLitePreparedStatementExecuteFast.dispose();
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void putDialogs(final TLRPC.messages_Dialogs messages_dialogs, final int i) {
        if (messages_dialogs.dialogs.isEmpty()) {
            return;
        }
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda57
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$putDialogs$237(messages_dialogs, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putDialogs$237(TLRPC.messages_Dialogs messages_dialogs, int i) throws Throwable {
        putDialogsInternal(messages_dialogs, i);
        try {
            loadUnreadMessages();
        } catch (Exception e) {
            checkSQLException(e);
        }
    }

    public void getDialogMaxMessageId(final long j, final IntCallback intCallback) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda113
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getDialogMaxMessageId$239(j, intCallback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDialogMaxMessageId$239(long j, final IntCallback intCallback) {
        final int[] iArr = new int[1];
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT MAX(mid) FROM messages_v2 WHERE uid = " + j, new Object[0]);
                if (sQLiteCursorQueryFinalized.next()) {
                    iArr[0] = sQLiteCursorQueryFinalized.intValue(0);
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda122
                @Override // java.lang.Runnable
                public final void run() {
                    intCallback.run(iArr[0]);
                }
            });
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public int getDialogReadMaxSync(boolean z, long j) {
        int iIntValue = 0;
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                if (z) {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT outbox_max FROM dialogs WHERE did = " + j, new Object[0]);
                    if (sQLiteCursorQueryFinalized.next()) {
                        iIntValue = sQLiteCursorQueryFinalized.intValue(0);
                    }
                } else {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT last_mid, inbox_max FROM dialogs WHERE did = " + j, new Object[0]);
                    if (sQLiteCursorQueryFinalized.next()) {
                        int iIntValue2 = sQLiteCursorQueryFinalized.intValue(0);
                        int iIntValue3 = sQLiteCursorQueryFinalized.intValue(1);
                        if (iIntValue3 <= iIntValue2) {
                            iIntValue = iIntValue3;
                        }
                    }
                }
                sQLiteCursorQueryFinalized.dispose();
                return iIntValue;
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
                return iIntValue;
            }
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public int getDialogReadMax(final boolean z, final long j) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Integer[] numArr = {0};
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda106
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getDialogReadMax$240(z, j, numArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return numArr[0].intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getDialogReadMax$240(boolean z, long j, Integer[] numArr, CountDownLatch countDownLatch) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                if (z) {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT outbox_max FROM dialogs WHERE did = " + j, new Object[0]);
                    if (sQLiteCursorQueryFinalized.next()) {
                        numArr[0] = Integer.valueOf(sQLiteCursorQueryFinalized.intValue(0));
                    }
                } else {
                    sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT last_mid, inbox_max FROM dialogs WHERE did = " + j, new Object[0]);
                    if (sQLiteCursorQueryFinalized.next()) {
                        int iIntValue = sQLiteCursorQueryFinalized.intValue(0);
                        int iIntValue2 = sQLiteCursorQueryFinalized.intValue(1);
                        if (iIntValue2 > iIntValue) {
                            numArr[0] = 0;
                        } else {
                            numArr[0] = Integer.valueOf(iIntValue2);
                        }
                    }
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            countDownLatch.countDown();
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public int getChannelPtsSync(final long j) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final Integer[] numArr = {0};
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda217
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getChannelPtsSync$241(j, numArr, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return numArr[0].intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChannelPtsSync$241(long j, Integer[] numArr, CountDownLatch countDownLatch) {
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized("SELECT pts FROM dialogs WHERE did = " + (-j), new Object[0]);
                if (sQLiteCursorQueryFinalized.next()) {
                    numArr[0] = Integer.valueOf(sQLiteCursorQueryFinalized.intValue(0));
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            try {
                countDownLatch.countDown();
            } catch (Exception e2) {
                checkSQLException(e2);
            }
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public TLRPC.User getUserSync(final long j) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final TLRPC.User[] userArr = new TLRPC.User[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda114
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getUserSync$242(userArr, j, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return userArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getUserSync$242(TLRPC.User[] userArr, long j, CountDownLatch countDownLatch) {
        userArr[0] = getUser(j);
        countDownLatch.countDown();
    }

    public TLRPC.Chat getChatSync(final long j) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final TLRPC.Chat[] chatArr = new TLRPC.Chat[1];
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda225
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$getChatSync$243(chatArr, j, countDownLatch);
            }
        });
        try {
            countDownLatch.await();
        } catch (Exception e) {
            checkSQLException(e);
        }
        return chatArr[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getChatSync$243(TLRPC.Chat[] chatArr, long j, CountDownLatch countDownLatch) {
        chatArr[0] = getChat(j);
        countDownLatch.countDown();
    }

    public TLRPC.User getUser(long j) {
        try {
            ArrayList<TLRPC.User> arrayList = new ArrayList<>();
            ArrayList<Long> arrayList2 = new ArrayList<>();
            arrayList2.add(Long.valueOf(j));
            getUsersInternal(arrayList2, arrayList);
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList.get(0);
        } catch (Exception e) {
            checkSQLException(e);
            return null;
        }
    }

    public ArrayList<TLRPC.User> getUsers(ArrayList<Long> arrayList) {
        ArrayList<TLRPC.User> arrayList2 = new ArrayList<>();
        try {
            getUsersInternal(arrayList, arrayList2);
            return arrayList2;
        } catch (Exception e) {
            arrayList2.clear();
            checkSQLException(e);
            return arrayList2;
        }
    }

    public ArrayList<TLRPC.Chat> getChats(ArrayList<Long> arrayList) {
        ArrayList<TLRPC.Chat> arrayList2 = new ArrayList<>();
        try {
            getChatsInternal(TextUtils.join(",", arrayList), arrayList2);
            return arrayList2;
        } catch (Exception e) {
            arrayList2.clear();
            checkSQLException(e);
            return arrayList2;
        }
    }

    public TLRPC.Chat getChat(long j) {
        try {
            ArrayList<TLRPC.Chat> arrayList = new ArrayList<>();
            getChatsInternal("" + j, arrayList);
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList.get(0);
        } catch (Exception e) {
            checkSQLException(e);
            return null;
        }
    }

    public TLRPC.EncryptedChat getEncryptedChat(long j) {
        try {
            ArrayList<TLRPC.EncryptedChat> arrayList = new ArrayList<>();
            getEncryptedChatsInternal("" + j, arrayList, null);
            if (arrayList.isEmpty()) {
                return null;
            }
            return arrayList.get(0);
        } catch (Exception e) {
            checkSQLException(e);
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:144:0x02bd, code lost:
    
        r11 = (org.telegram.ui.Adapters.DialogsSearchAdapter.DialogSearchResult) r6.get(r13.f1734id);
        r12 = r13.status;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x02c7, code lost:
    
        if (r12 == null) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x02c9, code lost:
    
        r27 = r3;
        r28 = r5;
        r12.expires = r2.intValue(1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02d6, code lost:
    
        r27 = r3;
        r28 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02db, code lost:
    
        if (r8 != 1) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02dd, code lost:
    
        r11.name = org.telegram.messenger.AndroidUtilities.generateSearchName(r13.first_name, r13.last_name, r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x02e8, code lost:
    
        r11.name = org.telegram.messenger.AndroidUtilities.generateSearchName("@" + org.telegram.messenger.UserObject.getPublicUsername(r13), null, "@" + r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0311, code lost:
    
        r11.object = r13;
        r3 = r22 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x04d0, code lost:
    
        r12 = r2;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01e1 A[Catch: all -> 0x004f, Exception -> 0x0053, TRY_ENTER, TRY_LEAVE, TryCatch #7 {Exception -> 0x0053, all -> 0x004f, blocks: (B:8:0x0048, B:23:0x006a, B:27:0x0086, B:79:0x015b, B:85:0x016a, B:89:0x018b, B:94:0x0199, B:96:0x01aa, B:98:0x01bb, B:103:0x01e1, B:164:0x0369, B:255:0x04ed, B:316:0x06d5, B:318:0x06df, B:320:0x06e3, B:325:0x06f8), top: B:405:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0285 A[Catch: all -> 0x00dd, Exception -> 0x00e1, TryCatch #8 {Exception -> 0x00e1, all -> 0x00dd, blocks: (B:31:0x00ad, B:33:0x00b3, B:36:0x00d1, B:43:0x00e5, B:45:0x00eb, B:59:0x0103, B:61:0x010d, B:65:0x0119, B:67:0x0124, B:71:0x0131, B:73:0x013f, B:75:0x014c, B:105:0x0201, B:107:0x0207, B:110:0x021b, B:112:0x0222, B:116:0x0234, B:118:0x023e, B:121:0x0257, B:123:0x025d, B:127:0x0275, B:134:0x0285, B:136:0x0290, B:139:0x02a3, B:155:0x0325, B:144:0x02bd, B:146:0x02c9, B:150:0x02dd, B:152:0x0311, B:151:0x02e8, B:159:0x0347, B:174:0x03ac, B:177:0x03c5, B:179:0x03cb, B:256:0x050b, B:258:0x0511, B:263:0x052a, B:265:0x0531, B:269:0x053d, B:271:0x0545, B:274:0x055c, B:276:0x0562, B:280:0x057a, B:285:0x0585, B:287:0x058c, B:290:0x059c, B:292:0x05a2, B:296:0x05b4, B:298:0x063f, B:299:0x0641, B:301:0x064d, B:304:0x0657, B:306:0x06a7, B:305:0x067e, B:308:0x06b4, B:310:0x06be, B:336:0x072d, B:338:0x0733, B:341:0x073f, B:344:0x0753, B:346:0x075a, B:350:0x0767, B:352:0x076f, B:355:0x0786, B:357:0x078c, B:361:0x07a4, B:367:0x07b2, B:369:0x07bb, B:371:0x07ca, B:374:0x07d4, B:376:0x080b, B:375:0x07e1, B:378:0x0818, B:381:0x0831), top: B:403:0x00ad }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0359  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0369 A[Catch: all -> 0x004f, Exception -> 0x0053, TRY_ENTER, TRY_LEAVE, TryCatch #7 {Exception -> 0x0053, all -> 0x004f, blocks: (B:8:0x0048, B:23:0x006a, B:27:0x0086, B:79:0x015b, B:85:0x016a, B:89:0x018b, B:94:0x0199, B:96:0x01aa, B:98:0x01bb, B:103:0x01e1, B:164:0x0369, B:255:0x04ed, B:316:0x06d5, B:318:0x06df, B:320:0x06e3, B:325:0x06f8), top: B:405:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:250:0x04e1  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x04e9  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0582  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x06b4 A[Catch: all -> 0x00dd, Exception -> 0x00e1, LOOP:6: B:268:0x053b->B:308:0x06b4, LOOP_END, TryCatch #8 {Exception -> 0x00e1, all -> 0x00dd, blocks: (B:31:0x00ad, B:33:0x00b3, B:36:0x00d1, B:43:0x00e5, B:45:0x00eb, B:59:0x0103, B:61:0x010d, B:65:0x0119, B:67:0x0124, B:71:0x0131, B:73:0x013f, B:75:0x014c, B:105:0x0201, B:107:0x0207, B:110:0x021b, B:112:0x0222, B:116:0x0234, B:118:0x023e, B:121:0x0257, B:123:0x025d, B:127:0x0275, B:134:0x0285, B:136:0x0290, B:139:0x02a3, B:155:0x0325, B:144:0x02bd, B:146:0x02c9, B:150:0x02dd, B:152:0x0311, B:151:0x02e8, B:159:0x0347, B:174:0x03ac, B:177:0x03c5, B:179:0x03cb, B:256:0x050b, B:258:0x0511, B:263:0x052a, B:265:0x0531, B:269:0x053d, B:271:0x0545, B:274:0x055c, B:276:0x0562, B:280:0x057a, B:285:0x0585, B:287:0x058c, B:290:0x059c, B:292:0x05a2, B:296:0x05b4, B:298:0x063f, B:299:0x0641, B:301:0x064d, B:304:0x0657, B:306:0x06a7, B:305:0x067e, B:308:0x06b4, B:310:0x06be, B:336:0x072d, B:338:0x0733, B:341:0x073f, B:344:0x0753, B:346:0x075a, B:350:0x0767, B:352:0x076f, B:355:0x0786, B:357:0x078c, B:361:0x07a4, B:367:0x07b2, B:369:0x07bb, B:371:0x07ca, B:374:0x07d4, B:376:0x080b, B:375:0x07e1, B:378:0x0818, B:381:0x0831), top: B:403:0x00ad }] */
    /* JADX WARN: Removed duplicated region for block: B:312:0x06c6  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x06d5 A[Catch: all -> 0x004f, Exception -> 0x0053, TRY_ENTER, TryCatch #7 {Exception -> 0x0053, all -> 0x004f, blocks: (B:8:0x0048, B:23:0x006a, B:27:0x0086, B:79:0x015b, B:85:0x016a, B:89:0x018b, B:94:0x0199, B:96:0x01aa, B:98:0x01bb, B:103:0x01e1, B:164:0x0369, B:255:0x04ed, B:316:0x06d5, B:318:0x06df, B:320:0x06e3, B:325:0x06f8), top: B:405:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:325:0x06f8 A[Catch: all -> 0x004f, Exception -> 0x0053, TRY_ENTER, TRY_LEAVE, TryCatch #7 {Exception -> 0x0053, all -> 0x004f, blocks: (B:8:0x0048, B:23:0x006a, B:27:0x0086, B:79:0x015b, B:85:0x016a, B:89:0x018b, B:94:0x0199, B:96:0x01aa, B:98:0x01bb, B:103:0x01e1, B:164:0x0369, B:255:0x04ed, B:316:0x06d5, B:318:0x06df, B:320:0x06e3, B:325:0x06f8), top: B:405:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:329:0x0716  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x07af  */
    /* JADX WARN: Removed duplicated region for block: B:378:0x0818 A[Catch: all -> 0x00dd, Exception -> 0x00e1, LOOP:10: B:349:0x0765->B:378:0x0818, LOOP_END, TryCatch #8 {Exception -> 0x00e1, all -> 0x00dd, blocks: (B:31:0x00ad, B:33:0x00b3, B:36:0x00d1, B:43:0x00e5, B:45:0x00eb, B:59:0x0103, B:61:0x010d, B:65:0x0119, B:67:0x0124, B:71:0x0131, B:73:0x013f, B:75:0x014c, B:105:0x0201, B:107:0x0207, B:110:0x021b, B:112:0x0222, B:116:0x0234, B:118:0x023e, B:121:0x0257, B:123:0x025d, B:127:0x0275, B:134:0x0285, B:136:0x0290, B:139:0x02a3, B:155:0x0325, B:144:0x02bd, B:146:0x02c9, B:150:0x02dd, B:152:0x0311, B:151:0x02e8, B:159:0x0347, B:174:0x03ac, B:177:0x03c5, B:179:0x03cb, B:256:0x050b, B:258:0x0511, B:263:0x052a, B:265:0x0531, B:269:0x053d, B:271:0x0545, B:274:0x055c, B:276:0x0562, B:280:0x057a, B:285:0x0585, B:287:0x058c, B:290:0x059c, B:292:0x05a2, B:296:0x05b4, B:298:0x063f, B:299:0x0641, B:301:0x064d, B:304:0x0657, B:306:0x06a7, B:305:0x067e, B:308:0x06b4, B:310:0x06be, B:336:0x072d, B:338:0x0733, B:341:0x073f, B:344:0x0753, B:346:0x075a, B:350:0x0767, B:352:0x076f, B:355:0x0786, B:357:0x078c, B:361:0x07a4, B:367:0x07b2, B:369:0x07bb, B:371:0x07ca, B:374:0x07d4, B:376:0x080b, B:375:0x07e1, B:378:0x0818, B:381:0x0831), top: B:403:0x00ad }] */
    /* JADX WARN: Removed duplicated region for block: B:389:0x0845  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x084c  */
    /* JADX WARN: Removed duplicated region for block: B:449:0x0585 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:461:0x07b2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:464:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:468:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x016a A[Catch: all -> 0x004f, Exception -> 0x0053, PHI: r3
      0x016a: PHI (r3v80 java.lang.String) = (r3v4 java.lang.String), (r3v81 java.lang.String) binds: [B:84:0x0168, B:80:0x015f] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TryCatch #7 {Exception -> 0x0053, all -> 0x004f, blocks: (B:8:0x0048, B:23:0x006a, B:27:0x0086, B:79:0x015b, B:85:0x016a, B:89:0x018b, B:94:0x0199, B:96:0x01aa, B:98:0x01bb, B:103:0x01e1, B:164:0x0369, B:255:0x04ed, B:316:0x06d5, B:318:0x06df, B:320:0x06e3, B:325:0x06f8), top: B:405:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01aa A[Catch: all -> 0x004f, Exception -> 0x0053, TryCatch #7 {Exception -> 0x0053, all -> 0x004f, blocks: (B:8:0x0048, B:23:0x006a, B:27:0x0086, B:79:0x015b, B:85:0x016a, B:89:0x018b, B:94:0x0199, B:96:0x01aa, B:98:0x01bb, B:103:0x01e1, B:164:0x0369, B:255:0x04ed, B:316:0x06d5, B:318:0x06df, B:320:0x06e3, B:325:0x06f8), top: B:405:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01bb A[Catch: all -> 0x004f, Exception -> 0x0053, TRY_LEAVE, TryCatch #7 {Exception -> 0x0053, all -> 0x004f, blocks: (B:8:0x0048, B:23:0x006a, B:27:0x0086, B:79:0x015b, B:85:0x016a, B:89:0x018b, B:94:0x0199, B:96:0x01aa, B:98:0x01bb, B:103:0x01e1, B:164:0x0369, B:255:0x04ed, B:316:0x06d5, B:318:0x06df, B:320:0x06e3, B:325:0x06f8), top: B:405:0x0048 }] */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v3 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v9 */
    /* JADX WARN: Type inference failed for: r32v0, types: [java.util.ArrayList, java.util.ArrayList<java.lang.Object>] */
    /* JADX WARN: Type inference failed for: r8v28 */
    /* JADX WARN: Type inference failed for: r8v29 */
    /* JADX WARN: Type inference failed for: r8v35 */
    /* JADX WARN: Type inference failed for: r8v37 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void localSearch(int r30, java.lang.String r31, java.util.ArrayList<java.lang.Object> r32, java.util.ArrayList<java.lang.CharSequence> r33, java.util.ArrayList<org.telegram.tgnet.TLRPC.User> r34, java.util.ArrayList<java.lang.Long> r35, int r36) {
        /*
            Method dump skipped, instructions count: 2128
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.localSearch(int, java.lang.String, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList, int):void");
    }

    public static /* synthetic */ int $r8$lambda$PogMFf6d7FSkt7eXDu9CMO7f0As(DialogsSearchAdapter.DialogSearchResult dialogSearchResult, DialogsSearchAdapter.DialogSearchResult dialogSearchResult2) {
        int i = dialogSearchResult.date;
        int i2 = dialogSearchResult2.date;
        if (i < i2) {
            return 1;
        }
        return i > i2 ? -1 : 0;
    }

    public ArrayList<Integer> getCachedMessagesInRange(long j, int i, int i2) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT mid FROM messages_v2 WHERE uid = %d AND date >= %d AND date <= %d", Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2)), new Object[0]);
                while (sQLiteCursorQueryFinalized.next()) {
                    try {
                        arrayList.add(Integer.valueOf(sQLiteCursorQueryFinalized.intValue(0)));
                    } catch (Exception e) {
                        checkSQLException(e);
                    }
                }
                sQLiteCursorQueryFinalized.dispose();
            } catch (Exception e2) {
                checkSQLException(e2);
                if (0 != 0) {
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            return arrayList;
        } catch (Throwable th) {
            if (0 != 0) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public void updateUnreadReactionsCount(long j, long j2, int i) {
        updateUnreadReactionsCount(j, j2, i, false);
    }

    public void updateUnreadReactionsCount(final long j, final long j2, final int i, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda145
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateUnreadReactionsCount$245(j2, z, j, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$updateUnreadReactionsCount$245(long r10, boolean r12, long r13, int r15) throws java.lang.Throwable {
        /*
            r9 = this;
            r0 = 0
            r2 = 2
            r3 = 1
            r4 = 0
            r5 = 0
            int r6 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r6 == 0) goto L87
            if (r12 == 0) goto L3e
            org.telegram.SQLite.SQLiteDatabase r12 = r9.database     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            java.util.Locale r0 = java.util.Locale.ENGLISH     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            java.lang.String r1 = "SELECT unread_reactions FROM topics WHERE did = %d AND topic_id = %d"
            java.lang.Long r6 = java.lang.Long.valueOf(r13)     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            java.lang.Long r7 = java.lang.Long.valueOf(r10)     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            java.lang.Object[] r8 = new java.lang.Object[r2]     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            r8[r4] = r6     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            r8[r3] = r7     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            java.lang.String r0 = java.lang.String.format(r0, r1, r8)     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            java.lang.Object[] r1 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            org.telegram.SQLite.SQLiteCursor r12 = r12.queryFinalized(r0, r1)     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            boolean r0 = r12.next()     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            if (r0 == 0) goto L39
            int r0 = r12.intValue(r4)     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            goto L3a
        L35:
            r10 = move-exception
            goto L81
        L37:
            r10 = move-exception
            goto L78
        L39:
            r0 = 0
        L3a:
            r12.dispose()     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            goto L3f
        L3e:
            r0 = 0
        L3f:
            org.telegram.SQLite.SQLiteDatabase r12 = r9.database     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            java.lang.String r1 = "UPDATE topics SET unread_reactions = ? WHERE did = ? AND topic_id = ?"
            org.telegram.SQLite.SQLitePreparedStatement r12 = r12.executeFast(r1)     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            int r0 = r0 + r15
            int r0 = java.lang.Math.max(r0, r4)     // Catch: java.lang.Throwable -> L73 org.telegram.SQLite.SQLiteException -> L76
            r12.bindInteger(r3, r0)     // Catch: java.lang.Throwable -> L73 org.telegram.SQLite.SQLiteException -> L76
            r12.bindLong(r2, r13)     // Catch: java.lang.Throwable -> L73 org.telegram.SQLite.SQLiteException -> L76
            r0 = 3
            r12.bindLong(r0, r10)     // Catch: java.lang.Throwable -> L73 org.telegram.SQLite.SQLiteException -> L76
            r12.step()     // Catch: java.lang.Throwable -> L73 org.telegram.SQLite.SQLiteException -> L76
            r12.dispose()     // Catch: java.lang.Throwable -> L73 org.telegram.SQLite.SQLiteException -> L76
            if (r15 != 0) goto Lc6
            org.telegram.SQLite.SQLiteDatabase r12 = r9.database     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            java.lang.String r15 = "UPDATE reaction_mentions_topics SET state = 0 WHERE dialog_id = ? AND topic_id = ? "
            org.telegram.SQLite.SQLitePreparedStatement r5 = r12.executeFast(r15)     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            r5.bindLong(r3, r13)     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            r5.bindLong(r2, r10)     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            r5.step()     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            r5.dispose()     // Catch: java.lang.Throwable -> L35 org.telegram.SQLite.SQLiteException -> L37
            goto Lc6
        L73:
            r10 = move-exception
            r5 = r12
            goto L81
        L76:
            r10 = move-exception
            r5 = r12
        L78:
            r10.printStackTrace()     // Catch: java.lang.Throwable -> L35
            if (r5 == 0) goto Lc6
            r5.dispose()
            goto Lc6
        L81:
            if (r5 == 0) goto L86
            r5.dispose()
        L86:
            throw r10
        L87:
            org.telegram.SQLite.SQLiteDatabase r10 = r9.database     // Catch: java.lang.Throwable -> Lb3 org.telegram.SQLite.SQLiteException -> Lb5
            java.lang.String r11 = "UPDATE dialogs SET unread_reactions = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r10 = r10.executeFast(r11)     // Catch: java.lang.Throwable -> Lb3 org.telegram.SQLite.SQLiteException -> Lb5
            int r11 = java.lang.Math.max(r15, r4)     // Catch: java.lang.Throwable -> Lb7 org.telegram.SQLite.SQLiteException -> Lbb
            r10.bindInteger(r3, r11)     // Catch: java.lang.Throwable -> Lb7 org.telegram.SQLite.SQLiteException -> Lbb
            r10.bindLong(r2, r13)     // Catch: java.lang.Throwable -> Lb7 org.telegram.SQLite.SQLiteException -> Lbb
            r10.step()     // Catch: java.lang.Throwable -> Lb7 org.telegram.SQLite.SQLiteException -> Lbb
            r10.dispose()     // Catch: java.lang.Throwable -> Lb7 org.telegram.SQLite.SQLiteException -> Lbb
            if (r15 != 0) goto Lc6
            org.telegram.SQLite.SQLiteDatabase r10 = r9.database     // Catch: java.lang.Throwable -> Lb3 org.telegram.SQLite.SQLiteException -> Lb5
            java.lang.String r11 = "UPDATE reaction_mentions SET state = 0 WHERE dialog_id = ?"
            org.telegram.SQLite.SQLitePreparedStatement r5 = r10.executeFast(r11)     // Catch: java.lang.Throwable -> Lb3 org.telegram.SQLite.SQLiteException -> Lb5
            r5.bindLong(r3, r13)     // Catch: java.lang.Throwable -> Lb3 org.telegram.SQLite.SQLiteException -> Lb5
            r5.step()     // Catch: java.lang.Throwable -> Lb3 org.telegram.SQLite.SQLiteException -> Lb5
            r5.dispose()     // Catch: java.lang.Throwable -> Lb3 org.telegram.SQLite.SQLiteException -> Lb5
            return
        Lb3:
            r10 = move-exception
            goto Lc7
        Lb5:
            r10 = move-exception
            goto Lbe
        Lb7:
            r11 = move-exception
            r5 = r10
            r10 = r11
            goto Lc7
        Lbb:
            r11 = move-exception
            r5 = r10
            r10 = r11
        Lbe:
            r10.printStackTrace()     // Catch: java.lang.Throwable -> Lb3
            if (r5 == 0) goto Lc6
            r5.dispose()
        Lc6:
            return
        Lc7:
            if (r5 == 0) goto Lcc
            r5.dispose()
        Lcc:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateUnreadReactionsCount$245(long, boolean, long, int):void");
    }

    public void markMessageReactionsAsRead(final long j, final long j2, final int i, boolean z) {
        if (z) {
            getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda92
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$markMessageReactionsAsRead$246(j, j2, i);
                }
            });
        } else {
            lambda$markMessageReactionsAsRead$246(j, j2, i);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
    /* renamed from: markMessageReactionsAsReadInternal, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void lambda$markMessageReactionsAsRead$246(long r19, long r21, int r23) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 414
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$markMessageReactionsAsRead$246(long, long, int):void");
    }

    public void updateDialogUnreadReactions(final long j, final long j2, final int i, final boolean z) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda229
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$updateDialogUnreadReactions$247(z, j, i, j2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$updateDialogUnreadReactions$247(boolean r17, long r18, int r20, long r21) throws java.lang.Throwable {
        /*
            r16 = this;
            r1 = r16
            r2 = r18
            r4 = r21
            r6 = 0
            r0 = 0
            if (r17 == 0) goto L45
            org.telegram.SQLite.SQLiteDatabase r7 = r1.database     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            r8.<init>()     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.lang.String r9 = "SELECT unread_reactions FROM dialogs WHERE did = "
            r8.append(r9)     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            r8.append(r2)     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.lang.Object[] r9 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            org.telegram.SQLite.SQLiteCursor r7 = r7.queryFinalized(r8, r9)     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            boolean r8 = r7.next()     // Catch: java.lang.Throwable -> L32 org.telegram.SQLite.SQLiteException -> L35
            if (r8 == 0) goto L38
            int r8 = r7.intValue(r0)     // Catch: java.lang.Throwable -> L32 org.telegram.SQLite.SQLiteException -> L35
            int r8 = java.lang.Math.max(r0, r8)     // Catch: java.lang.Throwable -> L32 org.telegram.SQLite.SQLiteException -> L35
            goto L39
        L32:
            r0 = move-exception
            goto Ld3
        L35:
            r0 = move-exception
            goto Lc5
        L38:
            r8 = 0
        L39:
            r7.dispose()     // Catch: java.lang.Throwable -> L32 org.telegram.SQLite.SQLiteException -> L35
            goto L46
        L3d:
            r0 = move-exception
            r7 = r6
            goto Ld3
        L41:
            r0 = move-exception
            r7 = r6
            goto Lc5
        L45:
            r8 = 0
        L46:
            int r8 = r8 + r20
            org.telegram.messenger.MessagesStorage r7 = r1.getMessagesStorage()     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            org.telegram.SQLite.SQLiteDatabase r7 = r7.getDatabase()     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.lang.String r9 = "UPDATE dialogs SET unread_reactions = ? WHERE did = ?"
            org.telegram.SQLite.SQLitePreparedStatement r7 = r7.executeFast(r9)     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            r9 = 1
            r7.bindInteger(r9, r8)     // Catch: java.lang.Throwable -> Lbc org.telegram.SQLite.SQLiteException -> Lc1
            r8 = 2
            r7.bindLong(r8, r2)     // Catch: java.lang.Throwable -> Lbc org.telegram.SQLite.SQLiteException -> Lc1
            r7.step()     // Catch: java.lang.Throwable -> Lbc org.telegram.SQLite.SQLiteException -> Lc1
            r7.dispose()     // Catch: java.lang.Throwable -> Lbc org.telegram.SQLite.SQLiteException -> Lc1
            r10 = 0
            int r7 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r7 == 0) goto Ld2
            if (r17 == 0) goto L9b
            org.telegram.SQLite.SQLiteDatabase r7 = r1.database     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.util.Locale r10 = java.util.Locale.US     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.lang.String r11 = "SELECT unread_reactions FROM topics WHERE did = %d AND topic_id = %d"
            java.lang.Long r12 = java.lang.Long.valueOf(r2)     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.lang.Long r13 = java.lang.Long.valueOf(r4)     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.lang.Object[] r14 = new java.lang.Object[r8]     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            r14[r0] = r12     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            r14[r9] = r13     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.lang.String r10 = java.lang.String.format(r10, r11, r14)     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.lang.Object[] r11 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            org.telegram.SQLite.SQLiteCursor r7 = r7.queryFinalized(r10, r11)     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            boolean r10 = r7.next()     // Catch: java.lang.Throwable -> L32 org.telegram.SQLite.SQLiteException -> L35
            if (r10 == 0) goto L98
            int r10 = r7.intValue(r0)     // Catch: java.lang.Throwable -> L32 org.telegram.SQLite.SQLiteException -> L35
            int r0 = java.lang.Math.max(r0, r10)     // Catch: java.lang.Throwable -> L32 org.telegram.SQLite.SQLiteException -> L35
        L98:
            r7.dispose()     // Catch: java.lang.Throwable -> L32 org.telegram.SQLite.SQLiteException -> L35
        L9b:
            int r0 = r0 + r20
            org.telegram.messenger.MessagesStorage r7 = r1.getMessagesStorage()     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            org.telegram.SQLite.SQLiteDatabase r7 = r7.getDatabase()     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            java.lang.String r10 = "UPDATE topics SET unread_reactions = ? WHERE did = ? AND topic_id = ?"
            org.telegram.SQLite.SQLitePreparedStatement r7 = r7.executeFast(r10)     // Catch: java.lang.Throwable -> L3d org.telegram.SQLite.SQLiteException -> L41
            r7.bindInteger(r9, r0)     // Catch: java.lang.Throwable -> Lbc org.telegram.SQLite.SQLiteException -> Lc1
            r7.bindLong(r8, r2)     // Catch: java.lang.Throwable -> Lbc org.telegram.SQLite.SQLiteException -> Lc1
            r0 = 3
            r7.bindLong(r0, r4)     // Catch: java.lang.Throwable -> Lbc org.telegram.SQLite.SQLiteException -> Lc1
            r7.step()     // Catch: java.lang.Throwable -> Lbc org.telegram.SQLite.SQLiteException -> Lc1
            r7.dispose()     // Catch: java.lang.Throwable -> Lbc org.telegram.SQLite.SQLiteException -> Lc1
            return
        Lbc:
            r0 = move-exception
            r15 = r7
            r7 = r6
            r6 = r15
            goto Ld3
        Lc1:
            r0 = move-exception
            r15 = r7
            r7 = r6
            r6 = r15
        Lc5:
            r0.printStackTrace()     // Catch: java.lang.Throwable -> L32
            if (r6 == 0) goto Lcd
            r6.dispose()
        Lcd:
            if (r7 == 0) goto Ld2
            r7.dispose()
        Ld2:
            return
        Ld3:
            if (r6 == 0) goto Ld8
            r6.dispose()
        Ld8:
            if (r7 == 0) goto Ldd
            r7.dispose()
        Ldd:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$updateDialogUnreadReactions$247(boolean, long, int, long):void");
    }

    public void putGiftChatTheme(TLRPC.ChatTheme chatTheme) {
        putGiftChatThemes(Collections.singletonList(chatTheme));
    }

    public void putGiftChatThemes(final List<TLRPC.ChatTheme> list) {
        executeInStorageQueue(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda120
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$putGiftChatThemes$248(list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$putGiftChatThemes$248(List list) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                try {
                    sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO gift_themes VALUES(?, ?)");
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        TLRPC.ChatTheme chatTheme = (TLRPC.ChatTheme) it.next();
                        if (chatTheme instanceof TLRPC.TL_chatThemeUniqueGift) {
                            TLRPC.TL_chatThemeUniqueGift tL_chatThemeUniqueGift = (TLRPC.TL_chatThemeUniqueGift) chatTheme;
                            sQLitePreparedStatementExecuteFast.requery();
                            sQLitePreparedStatementExecuteFast.bindString(1, tL_chatThemeUniqueGift.gift.slug);
                            NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tL_chatThemeUniqueGift.getObjectSize());
                            tL_chatThemeUniqueGift.serializeToStream(nativeByteBuffer);
                            sQLitePreparedStatementExecuteFast.bindByteBuffer(2, nativeByteBuffer);
                            nativeByteBuffer.reuse();
                            sQLitePreparedStatementExecuteFast.step();
                        }
                    }
                    sQLitePreparedStatementExecuteFast.dispose();
                } catch (Exception e) {
                    FileLog.m1160e(e);
                    if (sQLitePreparedStatementExecuteFast == null) {
                        return;
                    }
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            } catch (SQLiteException e2) {
                checkSQLException(e2);
                if (sQLitePreparedStatementExecuteFast == null) {
                    return;
                }
                sQLitePreparedStatementExecuteFast.dispose();
            }
        } catch (Throwable th) {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            throw th;
        }
    }

    public void loadGiftChatTheme(final Utilities.Callback<List<TLRPC.TL_chatThemeUniqueGift>> callback) {
        executeInStorageQueue(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda214
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$loadGiftChatTheme$251(callback);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0033: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:12:0x0033 */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0054 A[PHI: r2
      0x0054: PHI (r2v4 org.telegram.SQLite.SQLiteCursor) = (r2v3 org.telegram.SQLite.SQLiteCursor), (r2v5 org.telegram.SQLite.SQLiteCursor) binds: [B:27:0x0052, B:30:0x005b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0069  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$loadGiftChatTheme$251(final org.telegram.messenger.Utilities.Callback r7) throws java.lang.Throwable {
        /*
            r6 = this;
            r0 = 0
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L47 org.telegram.SQLite.SQLiteException -> L4b
            r1.<init>()     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L47 org.telegram.SQLite.SQLiteException -> L4b
            org.telegram.SQLite.SQLiteDatabase r2 = r6.database     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L47 org.telegram.SQLite.SQLiteException -> L4b
            java.lang.String r3 = "SELECT data FROM gift_themes"
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L47 org.telegram.SQLite.SQLiteException -> L4b
            org.telegram.SQLite.SQLiteCursor r2 = r2.queryFinalized(r3, r5)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L47 org.telegram.SQLite.SQLiteException -> L4b
        L11:
            boolean r3 = r2.next()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 org.telegram.SQLite.SQLiteException -> L37
            if (r3 == 0) goto L39
            org.telegram.tgnet.NativeByteBuffer r3 = r2.byteBufferValue(r4)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 org.telegram.SQLite.SQLiteException -> L37
            if (r3 == 0) goto L11
            int r5 = r3.readInt32(r4)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 org.telegram.SQLite.SQLiteException -> L37
            org.telegram.tgnet.TLRPC$ChatTheme r5 = org.telegram.tgnet.TLRPC.ChatTheme.TLdeserialize(r3, r5, r4)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 org.telegram.SQLite.SQLiteException -> L37
            r3.reuse()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 org.telegram.SQLite.SQLiteException -> L37
            boolean r3 = r5 instanceof org.telegram.tgnet.TLRPC.TL_chatThemeUniqueGift     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 org.telegram.SQLite.SQLiteException -> L37
            if (r3 == 0) goto L11
            org.telegram.tgnet.TLRPC$TL_chatThemeUniqueGift r5 = (org.telegram.tgnet.TLRPC.TL_chatThemeUniqueGift) r5     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 org.telegram.SQLite.SQLiteException -> L37
            r1.add(r5)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 org.telegram.SQLite.SQLiteException -> L37
            goto L11
        L32:
            r7 = move-exception
            r0 = r2
            goto L67
        L35:
            r0 = move-exception
            goto L4f
        L37:
            r0 = move-exception
            goto L58
        L39:
            r2.dispose()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L35 org.telegram.SQLite.SQLiteException -> L37
            org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda240 r2 = new org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda240     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L47 org.telegram.SQLite.SQLiteException -> L4b
            r2.<init>()     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L47 org.telegram.SQLite.SQLiteException -> L4b
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r2)     // Catch: java.lang.Throwable -> L45 java.lang.Exception -> L47 org.telegram.SQLite.SQLiteException -> L4b
            return
        L45:
            r7 = move-exception
            goto L67
        L47:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L4f
        L4b:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L58
        L4f:
            org.telegram.messenger.FileLog.m1160e(r0)     // Catch: java.lang.Throwable -> L32
            if (r2 == 0) goto L5e
        L54:
            r2.dispose()
            goto L5e
        L58:
            r6.checkSQLException(r0)     // Catch: java.lang.Throwable -> L32
            if (r2 == 0) goto L5e
            goto L54
        L5e:
            org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda241 r0 = new org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda241
            r0.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r0)
            return
        L67:
            if (r0 == 0) goto L6c
            r0.dispose()
        L6c:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.MessagesStorage.lambda$loadGiftChatTheme$251(org.telegram.messenger.Utilities$Callback):void");
    }

    private void executeInStorageQueue(Runnable runnable) {
        if (this.storageQueue.getHandler().getLooper() != Looper.myLooper()) {
            this.storageQueue.postRunnable(runnable);
        } else {
            runnable.run();
        }
    }

    public void saveStoryAlbumsCache(final long j, final List<StoriesController.StoryAlbum> list) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda173
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$saveStoryAlbumsCache$252(j, list);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: saveStoryAlbumsCacheInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$saveStoryAlbumsCache$252(long j, List<StoriesController.StoryAlbum> list) {
        SQLitePreparedStatement sQLitePreparedStatementExecuteFast = null;
        try {
            try {
                this.database.beginTransaction();
                this.database.executeFast("DELETE FROM profile_stories_albums WHERE dialog_id = " + j).stepThis().dispose();
                sQLitePreparedStatementExecuteFast = this.database.executeFast("REPLACE INTO profile_stories_albums VALUES(?, ?, ?, ?)");
                for (int i = 0; i < list.size(); i++) {
                    StoriesController.StoryAlbum storyAlbum = list.get(i);
                    sQLitePreparedStatementExecuteFast.requery();
                    sQLitePreparedStatementExecuteFast.bindLong(1, j);
                    sQLitePreparedStatementExecuteFast.bindInteger(2, storyAlbum.album_id);
                    sQLitePreparedStatementExecuteFast.bindInteger(3, i);
                    TL_stories.TL_storyAlbum tl = storyAlbum.toTl();
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tl.getObjectSize());
                    tl.serializeToStream(nativeByteBuffer);
                    sQLitePreparedStatementExecuteFast.bindByteBuffer(4, nativeByteBuffer);
                    sQLitePreparedStatementExecuteFast.step();
                    nativeByteBuffer.reuse();
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLitePreparedStatementExecuteFast != null) {
                }
            }
        } finally {
            if (sQLitePreparedStatementExecuteFast != null) {
                sQLitePreparedStatementExecuteFast.dispose();
            }
            this.database.commitTransaction();
        }
    }

    public void loadStoryAlbumsCache(final long j, final Consumer<List<StoriesController.StoryAlbum>> consumer) {
        this.storageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.MessagesStorage$$ExternalSyntheticLambda60
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadStoryAlbumsCache$253(j, consumer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadStoryAlbumsCache$253(long j, Consumer consumer) {
        ArrayList arrayList = new ArrayList();
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = this.database.queryFinalized(String.format(Locale.US, "SELECT data FROM profile_stories_albums WHERE dialog_id = %d ORDER BY order_index ASC", Long.valueOf(j)), new Object[0]);
                while (sQLiteCursorQueryFinalized.next()) {
                    NativeByteBuffer nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0);
                    if (nativeByteBufferByteBufferValue != null) {
                        TL_stories.TL_storyAlbum tL_storyAlbumTLdeserialize = TL_stories.TL_storyAlbum.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                        nativeByteBufferByteBufferValue.reuse();
                        if (tL_storyAlbumTLdeserialize != null) {
                            arrayList.add(StoriesController.StoryAlbum.from(tL_storyAlbumTLdeserialize));
                        }
                    }
                }
            } catch (Exception e) {
                checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            consumer.m971v(arrayList);
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public SQLiteCursor createLoadStoriesCursor(long j, int i, int i2) {
        return this.database.queryFinalized(String.format(Locale.US, "SELECT data, seen, pin FROM profile_stories JOIN profile_stories_albums_links ON profile_stories.story_id = profile_stories_albums_links.story_id WHERE profile_stories.dialog_id = %d AND profile_stories_albums_links.dialog_id = %d  AND profile_stories_albums_links.album_id = %d AND profile_stories.type = %d ORDER BY profile_stories_albums_links.order_index ASC;", Long.valueOf(j), Long.valueOf(j), Integer.valueOf(i), Integer.valueOf(i2)), new Object[0]);
    }

    public boolean isMonoForum(long j) {
        return isForum(j, 4);
    }

    public int getForumTypeFlags(long j) {
        int i = this.dialogIsForumTyped.get(j, -1);
        if (i != -1) {
            return i;
        }
        int i2 = 0;
        if (j < 0) {
            TLRPC.Chat chat = getChat(-j);
            if (chat != null && chat.forum) {
                i2 = chat.forum_tabs ? 3 : 1;
            }
            if (chat != null && chat.monoforum) {
                i2 |= 4;
            }
        } else {
            TLRPC.User user = getUser(j);
            if (user != null && user.bot_forum_view) {
                i2 = 8;
            }
        }
        this.dialogIsForumTyped.put(j, i2);
        return i2;
    }

    public boolean isForum(long j, int i) {
        return (getForumTypeFlags(j) & i) != 0;
    }

    private void isForumCacheInvalidate(long j) {
        this.dialogIsForumTyped.delete(j);
    }

    public static class TopicKey {
        public long dialogId;
        public long topicId;

        /* renamed from: of */
        public static TopicKey m1182of(long j, long j2) {
            TopicKey topicKey = new TopicKey();
            topicKey.dialogId = j;
            topicKey.topicId = j2;
            return topicKey;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                TopicKey topicKey = (TopicKey) obj;
                if (this.dialogId == topicKey.dialogId && this.topicId == topicKey.topicId) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.dialogId), Long.valueOf(this.topicId));
        }

        public String toString() {
            return "TopicKey{dialogId=" + this.dialogId + ", topicId=" + this.topicId + '}';
        }
    }
}
