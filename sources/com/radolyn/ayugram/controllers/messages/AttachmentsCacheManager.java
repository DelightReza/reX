package com.radolyn.ayugram.controllers.messages;

import android.text.TextUtils;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.AyuConstants;
import com.radolyn.ayugram.AyuInfra;
import com.radolyn.ayugram.database.AyuData;
import com.radolyn.ayugram.database.other.CleanUpUnion;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.ToLongFunction;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.Utilities;
import p017j$.util.Comparator;
import p017j$.util.List;

/* loaded from: classes4.dex */
public abstract class AttachmentsCacheManager {
    private static final String MSG_TYPE_DELETED = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019347328619642406L);
    private static final String MSG_TYPE_EDITED = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019347362979380774L);
    private static final AtomicBoolean cleaningUp = new AtomicBoolean(false);

    public static void clearAll() {
        Utilities.clearDir(AyuConfig.getSavePathJava().getAbsolutePath(), 0, (int) (System.currentTimeMillis() / 1000), true);
        AyuInfra.initializeAttachmentsFolder();
    }

    public static void cleanUp() {
        List<CleanUpUnion> messagesForCleanUp;
        AtomicBoolean atomicBoolean = cleaningUp;
        if (atomicBoolean.compareAndSet(false, true)) {
            try {
            } finally {
                try {
                    return;
                } finally {
                }
            }
            if (AyuConfig.saveMediaMaxCacheSize != Integer.MAX_VALUE) {
                long jCalculateCurrentCacheSize = calculateCurrentCacheSize();
                long jCalculateCacheSizeLimit = calculateCacheSizeLimit();
                while (true) {
                    if (jCalculateCurrentCacheSize <= jCalculateCacheSizeLimit || (messagesForCleanUp = AyuData.getDeletedMessageDao().getMessagesForCleanUp()) == null || messagesForCleanUp.isEmpty()) {
                        break;
                    }
                    String canonicalPath = AyuConfig.getSavePathJava().getCanonicalPath();
                    boolean z = false;
                    for (CleanUpUnion cleanUpUnion : messagesForCleanUp) {
                        if (!TextUtils.isEmpty(cleanUpUnion.mediaPath) && isFileWithinDirectory(new File(cleanUpUnion.mediaPath), canonicalPath)) {
                            long jDeleteAttachmentFile = deleteAttachmentFile(cleanUpUnion.mediaPath, cleanUpUnion.fakeId, cleanUpUnion.msgType);
                            if (jDeleteAttachmentFile > 0) {
                                jCalculateCurrentCacheSize -= jDeleteAttachmentFile;
                                FileLog.m1157d(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019346323597295142L) + cleanUpUnion.mediaPath + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019346435266444838L) + jDeleteAttachmentFile + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019346473921150502L));
                                z = true;
                            }
                            if (jCalculateCurrentCacheSize <= jCalculateCacheSizeLimit) {
                                break;
                            }
                        }
                    }
                    if (!z) {
                        FileLog.m1157d(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019346572705398310L));
                        break;
                    }
                }
                if (jCalculateCurrentCacheSize > jCalculateCacheSizeLimit) {
                    File savePathJava = AyuConfig.getSavePathJava();
                    Iterator itFindOldestFiles = findOldestFiles(savePathJava, savePathJava.getCanonicalPath());
                    while (itFindOldestFiles.hasNext() && jCalculateCurrentCacheSize > jCalculateCacheSizeLimit) {
                        File file = (File) itFindOldestFiles.next();
                        long length = file.length();
                        if (file.delete()) {
                            jCalculateCurrentCacheSize -= length;
                            FileLog.m1157d(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019346894827945510L) + file.getAbsolutePath() + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019347045151800870L) + length + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019347083806506534L));
                        }
                    }
                }
                return;
            }
            atomicBoolean.set(false);
        }
    }

    private static long calculateCurrentCacheSize() {
        return Utilities.getDirSize(AyuConfig.getSavePathJava().getAbsolutePath(), 0, true);
    }

    private static long calculateCacheSizeLimit() {
        return (long) ((AyuConfig.saveMediaMaxCacheSize == AyuConstants.MAX_CACHE_SIZE_300_MB ? 0.3d : AyuConfig.saveMediaMaxCacheSize) * 1.073741824E9d);
    }

    private static long deleteAttachmentFile(String str, long j, String str2) {
        File file = new File(str);
        if (!file.exists()) {
            updateDatabaseAfterDeletion(j, str2);
            return 0L;
        }
        long length = file.length();
        if (!file.delete()) {
            return 0L;
        }
        updateDatabaseAfterDeletion(j, str2);
        return length;
    }

    private static void updateDatabaseAfterDeletion(long j, String str) {
        if (Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019347264195132966L).equals(str)) {
            AyuData.getDeletedMessageDao().deleteMedia(j);
        } else if (Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019347298554871334L).equals(str)) {
            AyuData.getEditedMessageDao().deleteMedia(j);
        }
    }

    private static Iterator findOldestFiles(File file, String str) {
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null || fileArrListFiles.length == 0) {
            return Collections.emptyIterator();
        }
        ArrayList arrayList = new ArrayList();
        for (File file2 : fileArrListFiles) {
            if (file2.isFile() && !isSymlink(file2) && isFileWithinDirectory(file2, str)) {
                arrayList.add(file2);
            }
        }
        List.EL.sort(arrayList, Comparator.CC.comparingLong(new ToLongFunction() { // from class: com.radolyn.ayugram.controllers.messages.AttachmentsCacheManager$$ExternalSyntheticLambda0
            @Override // java.util.function.ToLongFunction
            public final long applyAsLong(Object obj) {
                return ((File) obj).lastModified();
            }
        }));
        return arrayList.iterator();
    }

    private static boolean isSymlink(File file) {
        try {
            return !file.getCanonicalPath().equals(file.getAbsolutePath());
        } catch (Exception unused) {
            return true;
        }
    }

    private static boolean isFileWithinDirectory(File file, String str) throws IOException {
        try {
            String canonicalPath = file.getCanonicalPath();
            if (canonicalPath.startsWith(str + File.separator)) {
                return true;
            }
            return canonicalPath.equals(str);
        } catch (Exception unused) {
            return false;
        }
    }
}
