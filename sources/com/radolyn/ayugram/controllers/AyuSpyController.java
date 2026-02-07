package com.radolyn.ayugram.controllers;

import android.util.LruCache;
import com.exteragram.messenger.utils.ChatUtils;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.database.AyuData;
import com.radolyn.ayugram.database.entities.SpyLastSeen;
import com.radolyn.ayugram.database.entities.SpyMessageContentsRead;
import com.radolyn.ayugram.database.entities.SpyMessageRead;
import com.radolyn.ayugram.utils.AyuLocalDatabaseUtils;
import java.util.Iterator;
import java.util.Locale;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BaseController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes.dex */
public class AyuSpyController extends BaseController {
    private static final AyuSpyController[] Instance = new AyuSpyController[16];
    private final LruCache onlineCache;

    public AyuSpyController(int i) {
        super(i);
        this.onlineCache = new LruCache(MediaDataController.MAX_STYLE_RUNS_COUNT);
    }

    public static AyuSpyController getInstance(int i) {
        AyuSpyController ayuSpyController;
        AyuSpyController[] ayuSpyControllerArr = Instance;
        AyuSpyController ayuSpyController2 = ayuSpyControllerArr[i];
        if (ayuSpyController2 != null) {
            return ayuSpyController2;
        }
        synchronized (AyuSpyController.class) {
            try {
                ayuSpyController = ayuSpyControllerArr[i];
                if (ayuSpyController == null) {
                    ayuSpyController = new AyuSpyController(i);
                    ayuSpyControllerArr[i] = ayuSpyController;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return ayuSpyController;
    }

    public void onMessageRead(long j, int i) {
        if (AyuConfig.saveReadDate) {
            try {
                onMessageReadInner(j, String.format(Locale.US, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019348157548330534L), Long.valueOf(j), Integer.valueOf(i)));
            } catch (Throwable unused) {
            }
        }
    }

    public void onEncryptedMessageRead(long j, int i) {
        if (AyuConfig.saveReadDate) {
            try {
                onMessageReadInner(j, String.format(Locale.US, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019348604224929318L), Long.valueOf(j), Integer.valueOf(i)));
            } catch (Throwable unused) {
            }
        }
    }

    private void onMessageReadInner(long j, String str) {
        int currentTime = getConnectionsManager().getCurrentTime();
        Iterator itIterateThroughMessageIds = AyuLocalDatabaseUtils.iterateThroughMessageIds(this.currentAccount, str);
        while (itIterateThroughMessageIds.hasNext()) {
            Integer num = (Integer) itIterateThroughMessageIds.next();
            SpyMessageRead spyMessageRead = new SpyMessageRead();
            spyMessageRead.userId = getUserConfig().getClientUserId();
            spyMessageRead.dialogId = j;
            spyMessageRead.messageId = num.intValue();
            spyMessageRead.entityCreateDate = currentTime;
            AyuData.getSpyDao().insert(spyMessageRead);
        }
    }

    public void onMessageContentsRead(long j, String str) {
        if (AyuConfig.saveReadDate) {
            try {
                onMessageContentsReadInner(j, str);
            } catch (Throwable unused) {
            }
        }
    }

    private void onMessageContentsReadInner(long j, String str) {
        int currentTime = getConnectionsManager().getCurrentTime();
        Iterator itIterateThroughMessageIds = AyuLocalDatabaseUtils.iterateThroughMessageIds(this.currentAccount, String.format(Locale.US, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019349003656887846L), str, Long.valueOf(j)));
        while (itIterateThroughMessageIds.hasNext()) {
            Integer num = (Integer) itIterateThroughMessageIds.next();
            SpyMessageContentsRead spyMessageContentsRead = new SpyMessageContentsRead();
            spyMessageContentsRead.userId = getUserConfig().getClientUserId();
            spyMessageContentsRead.dialogId = j;
            spyMessageContentsRead.messageId = num.intValue();
            spyMessageContentsRead.entityCreateDate = currentTime;
            AyuData.getSpyDao().insert(spyMessageContentsRead);
        }
    }

    public SpyMessageRead getMessageRead(long j, int i) {
        if (AyuConfig.saveReadDate) {
            return AyuData.getSpyDao().getMessageRead(getUserConfig().getClientUserId(), j, i);
        }
        return null;
    }

    public SpyMessageContentsRead getMessageContentsRead(long j, int i) {
        if (AyuConfig.saveReadDate) {
            return AyuData.getSpyDao().getMessageContentsRead(getUserConfig().getClientUserId(), j, i);
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x00a0, code lost:
    
        if (r4 == 0) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00b1, code lost:
    
        if (r4 == 0) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00c2, code lost:
    
        if (r4 == 0) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00d3, code lost:
    
        if (r4 == 0) goto L76;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean saveOnlineActivity(org.telegram.tgnet.TLRPC.Update r9, int r10) {
        /*
            Method dump skipped, instructions count: 400
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.radolyn.ayugram.controllers.AyuSpyController.saveOnlineActivity(org.telegram.tgnet.TLRPC$Update, int):boolean");
    }

    public boolean saveOnlineActivity(long j, int i) {
        if (!AyuConfig.saveLocalOnline || UserConfig.getInstance(this.currentAccount).getClientUserId() == j || i < 1397411401) {
            return false;
        }
        Integer lastSeen = getLastSeen(j);
        if (lastSeen != null && lastSeen.intValue() >= i) {
            return false;
        }
        SpyLastSeen spyLastSeen = new SpyLastSeen();
        spyLastSeen.userId = j;
        spyLastSeen.lastSeenDate = i;
        AyuData.getSpyDao().insert(spyLastSeen);
        MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
        messagesController.onlinePrivacy.put(Long.valueOf(j), Integer.valueOf(i));
        this.onlineCache.put(Long.valueOf(j), Integer.valueOf(i));
        TLRPC.User user = messagesController.getUser(Long.valueOf(j));
        return (user == null || user.bot || !isBadStatus(user.status)) ? false : true;
    }

    public Integer getLastSeenCached(long j) {
        if (AyuConfig.saveLocalOnline) {
            return (Integer) this.onlineCache.get(Long.valueOf(j));
        }
        return null;
    }

    public Integer getLastSeen(long j) {
        if (!AyuConfig.saveLocalOnline) {
            return null;
        }
        Integer num = (Integer) this.onlineCache.get(Long.valueOf(j));
        if (num != null && num.intValue() >= 1397411401) {
            return num;
        }
        SpyLastSeen lastSeen = AyuData.getSpyDao().getLastSeen(j);
        if (lastSeen == null || lastSeen.lastSeenDate < 1397411401) {
            return null;
        }
        this.onlineCache.put(Long.valueOf(j), Integer.valueOf(lastSeen.lastSeenDate));
        return Integer.valueOf(lastSeen.lastSeenDate);
    }

    public void loadLastSeen(final long j) {
        if (AyuConfig.saveLocalOnline) {
            Integer num = (Integer) this.onlineCache.get(Long.valueOf(j));
            if (num == null || num.intValue() <= 1397411401) {
                ChatUtils.utilsQueue.postRunnable(new Runnable() { // from class: com.radolyn.ayugram.controllers.AyuSpyController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$loadLastSeen$0(j);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadLastSeen$0(long j) {
        if (getLastSeen(j) != null) {
            updateInterfaces();
        }
    }

    public void updateInterfaces() {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.controllers.AyuSpyController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateInterfaces$1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateInterfaces$1() {
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_STATUS));
    }

    public static boolean isBadStatus(TLRPC.UserStatus userStatus) {
        int i;
        return userStatus == null || (userStatus instanceof TLRPC.TL_userStatusRecently) || (userStatus instanceof TLRPC.TL_userStatusLastWeek) || (userStatus instanceof TLRPC.TL_userStatusLastMonth) || (i = userStatus.expires) == -1 || i == -100 || i == -101 || i == -102 || i == -1000 || i == -1001 || i == -1002;
    }
}
