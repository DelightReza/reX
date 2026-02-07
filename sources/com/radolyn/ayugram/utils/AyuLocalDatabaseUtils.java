package com.radolyn.ayugram.utils;

import android.text.TextUtils;
import android.util.Pair;
import androidx.collection.LongSparseArray;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.SQLite.SQLiteDatabase;
import org.telegram.messenger.MessagesStorage;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes.dex */
public abstract class AyuLocalDatabaseUtils {
    public static Pair getMinAndMaxForDialog(int i, long j) {
        MessagesStorage messagesStorage = MessagesStorage.getInstance(i);
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = messagesStorage.getDatabase().queryFinalized(String.format(Locale.US, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316572358835750L), Long.valueOf(j)), new Object[0]);
                if (sQLiteCursorQueryFinalized.next()) {
                    Pair pair = new Pair(Integer.valueOf(sQLiteCursorQueryFinalized.intValue(0)), Integer.valueOf(sQLiteCursorQueryFinalized.intValue(1)));
                    sQLiteCursorQueryFinalized.dispose();
                    return pair;
                }
            } catch (Exception e) {
                messagesStorage.checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                }
            }
            sQLiteCursorQueryFinalized.dispose();
            return new Pair(0, 0);
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public static LongSparseArray getMessageIdsByRandomIds(int i, ArrayList arrayList) {
        if (arrayList.isEmpty()) {
            return new LongSparseArray();
        }
        MessagesStorage messagesStorage = MessagesStorage.getInstance(i);
        SQLiteDatabase database = messagesStorage.getDatabase();
        SQLiteCursor sQLiteCursorQueryFinalized = null;
        try {
            try {
                sQLiteCursorQueryFinalized = database.queryFinalized(String.format(Locale.US, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316830056873510L), TextUtils.join(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316821466938918L), arrayList)), new Object[0]);
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
                return longSparseArray;
            } catch (Exception e) {
                messagesStorage.checkSQLException(e);
                if (sQLiteCursorQueryFinalized != null) {
                    sQLiteCursorQueryFinalized.dispose();
                }
                return new LongSparseArray();
            }
        } catch (Throwable th) {
            if (sQLiteCursorQueryFinalized != null) {
                sQLiteCursorQueryFinalized.dispose();
            }
            throw th;
        }
    }

    public static Iterator iterateThroughMessages(int i, long j) {
        return iterateThroughMessages(i, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317066280074790L) + j);
    }

    public static Iterator iterateThroughMessages(int i, String str) {
        return new Iterator(i, str) { // from class: com.radolyn.ayugram.utils.AyuLocalDatabaseUtils.1
            private SQLiteCursor cursor;
            private TLRPC.Message nextMessage = null;
            final /* synthetic */ int val$accountNum;
            final /* synthetic */ String val$sqlQuery;

            {
                this.val$accountNum = i;
                this.val$sqlQuery = str;
                this.cursor = null;
                try {
                    this.cursor = MessagesStorage.getInstance(i).getDatabase().queryFinalized(str, new Object[0]);
                    advanceCursor();
                } catch (Exception e) {
                    handleException(e);
                }
            }

            /* JADX WARN: Code restructure failed: missing block: B:14:0x0030, code lost:
            
                disposeCursor();
             */
            /* JADX WARN: Code restructure failed: missing block: B:15:0x0033, code lost:
            
                return;
             */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            private void advanceCursor() {
                /*
                    r4 = this;
                L0:
                    org.telegram.SQLite.SQLiteCursor r0 = r4.cursor     // Catch: java.lang.Exception -> L2e
                    if (r0 == 0) goto L30
                    boolean r0 = r0.next()     // Catch: java.lang.Exception -> L2e
                    if (r0 == 0) goto L30
                    org.telegram.SQLite.SQLiteCursor r0 = r4.cursor     // Catch: java.lang.Exception -> L2e
                    r1 = 0
                    org.telegram.tgnet.NativeByteBuffer r0 = r0.byteBufferValue(r1)     // Catch: java.lang.Exception -> L2e
                    if (r0 == 0) goto L0
                    int r2 = r0.readInt32(r1)     // Catch: java.lang.Exception -> L2e
                    org.telegram.tgnet.TLRPC$Message r1 = org.telegram.tgnet.TLRPC.Message.TLdeserialize(r0, r2, r1)     // Catch: java.lang.Exception -> L2e
                    if (r1 == 0) goto L0
                    int r2 = r4.val$accountNum     // Catch: java.lang.Exception -> L2e
                    org.telegram.messenger.UserConfig r2 = org.telegram.messenger.UserConfig.getInstance(r2)     // Catch: java.lang.Exception -> L2e
                    long r2 = r2.clientUserId     // Catch: java.lang.Exception -> L2e
                    r1.readAttachPath(r0, r2)     // Catch: java.lang.Exception -> L2e
                    r0.reuse()     // Catch: java.lang.Exception -> L2e
                    r4.nextMessage = r1     // Catch: java.lang.Exception -> L2e
                    return
                L2e:
                    r0 = move-exception
                    goto L34
                L30:
                    r4.disposeCursor()     // Catch: java.lang.Exception -> L2e
                    return
                L34:
                    r4.handleException(r0)
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.radolyn.ayugram.utils.AyuLocalDatabaseUtils.C15401.advanceCursor():void");
            }

            private void disposeCursor() {
                SQLiteCursor sQLiteCursor = this.cursor;
                if (sQLiteCursor != null) {
                    sQLiteCursor.dispose();
                    this.cursor = null;
                }
            }

            private void handleException(Exception exc) {
                MessagesStorage.getInstance(this.val$accountNum).checkSQLException(exc);
                disposeCursor();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.nextMessage != null;
            }

            @Override // java.util.Iterator
            public TLRPC.Message next() {
                TLRPC.Message message = this.nextMessage;
                this.nextMessage = null;
                advanceCursor();
                return message;
            }
        };
    }

    public static Iterator iterateThroughMessageIds(int i, String str) {
        return new Iterator(i, str) { // from class: com.radolyn.ayugram.utils.AyuLocalDatabaseUtils.2
            private SQLiteCursor cursor;
            private Integer nextMessageId = null;
            final /* synthetic */ int val$accountNum;
            final /* synthetic */ String val$sqlQuery;

            {
                this.val$accountNum = i;
                this.val$sqlQuery = str;
                this.cursor = null;
                try {
                    this.cursor = MessagesStorage.getInstance(i).getDatabase().queryFinalized(str, new Object[0]);
                    advanceCursor();
                } catch (Exception e) {
                    handleException(e);
                }
            }

            private void advanceCursor() {
                NativeByteBuffer nativeByteBufferByteBufferValue;
                do {
                    try {
                        SQLiteCursor sQLiteCursor = this.cursor;
                        if (sQLiteCursor != null && sQLiteCursor.next()) {
                            nativeByteBufferByteBufferValue = this.cursor.byteBufferValue(0);
                        } else {
                            disposeCursor();
                            return;
                        }
                    } catch (Exception e) {
                        handleException(e);
                        return;
                    }
                } while (nativeByteBufferByteBufferValue == null);
                this.nextMessageId = Integer.valueOf(this.cursor.intValue(0));
                nativeByteBufferByteBufferValue.reuse();
            }

            private void disposeCursor() {
                SQLiteCursor sQLiteCursor = this.cursor;
                if (sQLiteCursor != null) {
                    sQLiteCursor.dispose();
                    this.cursor = null;
                }
            }

            private void handleException(Exception exc) {
                MessagesStorage.getInstance(this.val$accountNum).checkSQLException(exc);
                disposeCursor();
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.nextMessageId != null;
            }

            @Override // java.util.Iterator
            public Integer next() {
                Integer num = this.nextMessageId;
                this.nextMessageId = null;
                advanceCursor();
                return num;
            }
        };
    }
}
