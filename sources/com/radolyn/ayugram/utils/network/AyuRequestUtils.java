package com.radolyn.ayugram.utils.network;

import android.text.TextUtils;
import com.google.android.exoplayer2.util.Consumer;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.AyuConstants;
import com.radolyn.ayugram.AyuGhostConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SecretChatHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.Vector;
import org.telegram.tgnet.p022tl.TL_account;
import org.telegram.tgnet.p022tl.TL_forum;
import org.telegram.tgnet.p022tl.TL_phone;
import org.telegram.tgnet.p022tl.TL_stories;

/* loaded from: classes.dex */
public abstract class AyuRequestUtils {
    private static final DispatchQueue queue = new DispatchQueue(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317392697589286L));

    /* loaded from: classes4.dex */
    public interface ProbeConsumer {
        void accept(String str, int i);
    }

    /* loaded from: classes4.dex */
    public interface ReadCallback {
        void onResult(boolean z);
    }

    public static /* synthetic */ void $r8$lambda$M59_RTkgj9pO2iVAIxhBrXRWVok(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    public static /* synthetic */ void $r8$lambda$XXKEWg49hnIqfKbkdiEljtghN7o(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    public static void markReadOnServer(int i, long j, int i2, int i3, long j2) {
        markReadOnServer(i, j, i2, i3, j2, false);
    }

    public static void markReadOnServer(int i, long j, int i2, int i3, long j2, ReadCallback readCallback) {
        markReadOnServer(i, j, i2, i3, j2, false, readCallback);
    }

    public static void markReadOnServer(int i, long j, int i2, int i3, long j2, boolean z) {
        markReadOnServer(i, j, i2, i3, j2, z, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void markReadOnServer(int i, long j, int i2, int i3, long j2, boolean z, final ReadCallback readCallback) {
        long j3;
        final TLRPC.TL_messages_readHistory tL_messages_readHistory;
        final MessagesController messagesController = MessagesController.getInstance(i);
        final ConnectionsManager connectionsManager = ConnectionsManager.getInstance(i);
        if (!MessagesStorage.getInstance(i).isMonoForum(j)) {
            j3 = 0;
        } else {
            if (j2 == 0 && z) {
                if (readCallback != null) {
                    readCallback.onResult(false);
                    return;
                }
                return;
            }
            j3 = j2;
        }
        if (j2 != 0 && j3 == 0) {
            final TLRPC.TL_messages_readDiscussion tL_messages_readDiscussion = new TLRPC.TL_messages_readDiscussion();
            tL_messages_readDiscussion.msg_id = (int) j2;
            tL_messages_readDiscussion.peer = messagesController.getInputPeer(j);
            tL_messages_readDiscussion.read_max_id = i2;
            queue.postRunnable(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    connectionsManager.sendRequest(TLRPCWrappedBypass.wrap(tL_messages_readDiscussion), new RequestDelegate() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda8
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                            AyuRequestUtils.$r8$lambda$KKGE1_Q6wtYdxmx5jWKbx5UAPe0(readCallback, tLObject, tL_error);
                        }
                    });
                }
            });
            return;
        }
        if (!DialogObject.isEncryptedDialog(j)) {
            TLRPC.InputPeer inputPeer = messagesController.getInputPeer(j);
            if (j3 != 0) {
                TLRPC.TL_messages_readSavedHistory tL_messages_readSavedHistory = new TLRPC.TL_messages_readSavedHistory();
                tL_messages_readSavedHistory.parent_peer = messagesController.getInputPeer(j);
                tL_messages_readSavedHistory.peer = messagesController.getInputPeer(j3);
                tL_messages_readSavedHistory.max_id = i2;
                tL_messages_readHistory = tL_messages_readSavedHistory;
            } else if (inputPeer instanceof TLRPC.TL_inputPeerChannel) {
                TLRPC.TL_channels_readHistory tL_channels_readHistory = new TLRPC.TL_channels_readHistory();
                tL_channels_readHistory.channel = messagesController.getInputChannel(-j);
                tL_channels_readHistory.max_id = i2;
                tL_messages_readHistory = tL_channels_readHistory;
            } else {
                TLRPC.TL_messages_readHistory tL_messages_readHistory2 = new TLRPC.TL_messages_readHistory();
                tL_messages_readHistory2.peer = inputPeer;
                tL_messages_readHistory2.max_id = i2;
                tL_messages_readHistory = tL_messages_readHistory2;
            }
            queue.postRunnable(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    connectionsManager.sendRequest(TLRPCWrappedBypass.wrap(tL_messages_readHistory), new RequestDelegate() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda9
                        @Override // org.telegram.tgnet.RequestDelegate
                        public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                            AyuRequestUtils.m2937$r8$lambda$h9jgfy0uj059eoZqm0t_dTcFxw(messagesController, readCallback, tLObject, tL_error);
                        }
                    });
                }
            });
            return;
        }
        markReadOnServerSecret(i, i3, messagesController.getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(j))), readCallback);
    }

    public static /* synthetic */ void $r8$lambda$KKGE1_Q6wtYdxmx5jWKbx5UAPe0(ReadCallback readCallback, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (readCallback != null) {
            readCallback.onResult(tL_error == null);
        }
    }

    /* renamed from: $r8$lambda$h9jgfy0uj059eoZq-m0t_dTcFxw, reason: not valid java name */
    public static /* synthetic */ void m2937$r8$lambda$h9jgfy0uj059eoZqm0t_dTcFxw(MessagesController messagesController, ReadCallback readCallback, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tL_error == null && (tLObject instanceof TLRPC.TL_messages_affectedMessages)) {
            TLRPC.TL_messages_affectedMessages tL_messages_affectedMessages = (TLRPC.TL_messages_affectedMessages) tLObject;
            messagesController.processNewDifferenceParams(-1, tL_messages_affectedMessages.pts, -1, tL_messages_affectedMessages.pts_count);
        }
        if (readCallback != null) {
            readCallback.onResult(tL_error == null);
        }
    }

    public static void markContentReadOnServer(int i, MessageObject messageObject) {
        MessagesController.getInstance(i).markMessageContentAsRead(messageObject, true);
        messageObject.setContentIsRead();
    }

    public static void markReadOnServerSecret(int i, int i2, TLRPC.EncryptedChat encryptedChat, final ReadCallback readCallback) {
        final ConnectionsManager connectionsManager = ConnectionsManager.getInstance(i);
        byte[] bArr = encryptedChat.auth_key;
        if (bArr == null || bArr.length <= 1) {
            if (readCallback != null) {
                readCallback.onResult(false);
                return;
            }
            return;
        }
        final TLRPC.TL_messages_readEncryptedHistory tL_messages_readEncryptedHistory = new TLRPC.TL_messages_readEncryptedHistory();
        TLRPC.TL_inputEncryptedChat tL_inputEncryptedChat = new TLRPC.TL_inputEncryptedChat();
        tL_messages_readEncryptedHistory.peer = tL_inputEncryptedChat;
        tL_inputEncryptedChat.chat_id = encryptedChat.f1583id;
        tL_inputEncryptedChat.access_hash = encryptedChat.access_hash;
        tL_messages_readEncryptedHistory.max_date = i2;
        queue.postRunnable(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                connectionsManager.sendRequest(TLRPCWrappedBypass.wrap(tL_messages_readEncryptedHistory), new RequestDelegate() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda12
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        AyuRequestUtils.$r8$lambda$6oH2Mwstf4KtNI6im9kSIxSa8js(readCallback, tLObject, tL_error);
                    }
                });
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$6oH2Mwstf4KtNI6im9kSIxSa8js(ReadCallback readCallback, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (readCallback != null) {
            readCallback.onResult(tL_error == null);
        }
    }

    public static void burnMedia(int i, boolean z, long j, MessageObject messageObject, ReadCallback readCallback) {
        if (!z) {
            burnNormalMedia(i, messageObject.getId(), readCallback);
        } else {
            burnSecretMedia(i, j, messageObject.messageOwner.random_id, readCallback);
        }
    }

    private static void burnNormalMedia(final int i, int i2, final ReadCallback readCallback) {
        TLRPC.TL_messages_readMessageContents tL_messages_readMessageContents = new TLRPC.TL_messages_readMessageContents();
        tL_messages_readMessageContents.f1682id.add(Integer.valueOf(i2));
        ConnectionsManager.getInstance(i).sendRequest(TLRPCWrappedBypass.wrap(tL_messages_readMessageContents), new RequestDelegate() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda15
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AyuRequestUtils.$r8$lambda$y8IbBlmppYeSjo3VycNSwfMroXE(i, readCallback, tLObject, tL_error);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$y8IbBlmppYeSjo3VycNSwfMroXE(int i, ReadCallback readCallback, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tL_error == null) {
            TLRPC.TL_messages_affectedMessages tL_messages_affectedMessages = (TLRPC.TL_messages_affectedMessages) tLObject;
            MessagesController.getInstance(i).processNewDifferenceParams(-1, tL_messages_affectedMessages.pts, -1, tL_messages_affectedMessages.pts_count);
        }
        if (readCallback != null) {
            readCallback.onResult(tL_error == null);
        }
    }

    private static void burnSecretMedia(int i, long j, long j2, ReadCallback readCallback) {
        if (j2 == 0 || j == 0) {
            if (readCallback != null) {
                readCallback.onResult(false);
                return;
            }
            return;
        }
        if (!DialogObject.isEncryptedDialog(j)) {
            if (readCallback != null) {
                readCallback.onResult(false);
                return;
            }
            return;
        }
        TLRPC.EncryptedChat encryptedChat = MessagesController.getInstance(i).getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(j)));
        if (encryptedChat == null) {
            if (readCallback != null) {
                readCallback.onResult(false);
            }
        } else {
            ArrayList<Long> arrayList = new ArrayList<>();
            arrayList.add(Long.valueOf(j2));
            SecretChatHelper.getInstance(i).sendMessagesReadMessage(encryptedChat, arrayList, null);
            if (readCallback != null) {
                readCallback.onResult(true);
            }
        }
    }

    public static void requestUser(int i, long j, final Consumer consumer) {
        MessagesController messagesController = MessagesController.getInstance(i);
        ConnectionsManager connectionsManager = ConnectionsManager.getInstance(i);
        TLRPC.TL_users_getUsers tL_users_getUsers = new TLRPC.TL_users_getUsers();
        tL_users_getUsers.f1727id.add(messagesController.getInputUser(j));
        connectionsManager.sendRequest(tL_users_getUsers, new RequestDelegate() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda10
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AyuRequestUtils.m2936$r8$lambda$dpLyp4WUQy1i7yayoq13q7iIU(consumer, tLObject, tL_error);
            }
        });
    }

    /* renamed from: $r8$lambda$dpLyp4WU-Qy1i7yayoq13q7i-IU, reason: not valid java name */
    public static /* synthetic */ void m2936$r8$lambda$dpLyp4WUQy1i7yayoq13q7iIU(Consumer consumer, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tL_error == null && (tLObject instanceof Vector)) {
            ArrayList arrayList = ((Vector) tLObject).objects;
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i) instanceof TLRPC.User) {
                    arrayList2.add((TLRPC.User) arrayList.get(i));
                }
            }
            if (arrayList2.isEmpty()) {
                return;
            }
            consumer.accept((TLRPC.User) arrayList2.get(0));
        }
    }

    public static void sendOnline(int i) {
        ConnectionsManager connectionsManager = ConnectionsManager.getInstance(i);
        TL_account.updateStatus updatestatus = new TL_account.updateStatus();
        updatestatus.offline = false;
        connectionsManager.sendRequest(TLRPCWrappedBypass.wrap(updatestatus), new RequestDelegate() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda7
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AyuRequestUtils.$r8$lambda$M59_RTkgj9pO2iVAIxhBrXRWVok(tLObject, tL_error);
            }
        });
    }

    public static void sendOffline(int i) {
        ConnectionsManager connectionsManager = ConnectionsManager.getInstance(i);
        TL_account.updateStatus updatestatus = new TL_account.updateStatus();
        updatestatus.offline = true;
        connectionsManager.sendRequest(TLRPCWrappedBypass.wrap(updatestatus), new RequestDelegate() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda18
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AyuRequestUtils.$r8$lambda$XXKEWg49hnIqfKbkdiEljtghN7o(tLObject, tL_error);
            }
        });
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0008 A[PHI: r0
      0x0008: PHI (r0v4 long) = (r0v0 long), (r0v1 long) binds: [B:3:0x0006, B:6:0x000e] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.Long getDialogId(org.telegram.tgnet.TLRPC.InputPeer r5) {
        /*
            long r0 = r5.chat_id
            r2 = 0
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto La
        L8:
            long r0 = -r0
            goto L13
        La:
            long r0 = r5.channel_id
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 == 0) goto L11
            goto L8
        L11:
            long r0 = r5.user_id
        L13:
            java.lang.Long r5 = java.lang.Long.valueOf(r0)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.radolyn.ayugram.utils.network.AyuRequestUtils.getDialogId(org.telegram.tgnet.TLRPC$InputPeer):java.lang.Long");
    }

    public static Long getDialogId(TLRPC.InputChannel inputChannel) {
        return Long.valueOf(-inputChannel.channel_id);
    }

    public static void probeOnline(final TLRPC.User user, final ProbeConsumer probeConsumer) {
        queue.postRunnable(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                AyuRequestUtils.probeOnlineInner(user, probeConsumer);
            }
        });
    }

    public static void probeOnlineInner(TLRPC.User user, final ProbeConsumer probeConsumer) throws InterruptedException {
        if (user == null) {
            return;
        }
        final boolean[] zArr = new boolean[1];
        final ProbeConsumer probeConsumer2 = new ProbeConsumer() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda13
            @Override // com.radolyn.ayugram.utils.network.AyuRequestUtils.ProbeConsumer
            public final void accept(String str, int i) {
                AyuRequestUtils.$r8$lambda$h9fjq_BOI0Z9CvwVD6XNDjTujZU(zArr, probeConsumer, str, i);
            }
        };
        long j = user.f1734id;
        String str = user.username;
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        probeWithAccount(UserConfig.selectedAccount, j, str, new ProbeConsumer() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda14
            @Override // com.radolyn.ayugram.utils.network.AyuRequestUtils.ProbeConsumer
            public final void accept(String str2, int i) {
                AyuRequestUtils.$r8$lambda$eEJ3JVye6JqQ8kzCEzFy7BPdlVk(probeConsumer2, countDownLatch, str2, i);
            }
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException unused) {
        }
        if (!zArr[0] && AyuConfig.probeUsingOtherAccounts) {
            for (int i = 0; i < 16; i++) {
                if (i != UserConfig.selectedAccount) {
                    probeWithAccount(i, j, str, probeConsumer2);
                }
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$h9fjq_BOI0Z9CvwVD6XNDjTujZU(boolean[] zArr, final ProbeConsumer probeConsumer, final String str, final int i) {
        synchronized (zArr) {
            try {
                if (!TextUtils.isEmpty(str) && !zArr[0]) {
                    zArr[0] = true;
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda21
                        @Override // java.lang.Runnable
                        public final void run() {
                            probeConsumer.accept(str, i);
                        }
                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$eEJ3JVye6JqQ8kzCEzFy7BPdlVk(ProbeConsumer probeConsumer, CountDownLatch countDownLatch, String str, int i) {
        probeConsumer.accept(str, i);
        countDownLatch.countDown();
    }

    private static void probeWithAccount(final int i, long j, String str, final ProbeConsumer probeConsumer) {
        if (UserConfig.getInstance(i).isClientActivated()) {
            final MessagesController messagesController = MessagesController.getInstance(i);
            MessagesStorage messagesStorage = MessagesStorage.getInstance(i);
            TLRPC.User user = messagesController.getUser(Long.valueOf(j));
            if (user == null) {
                user = messagesStorage.getUserSync(j);
            }
            if (user == null) {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                messagesController.getUserNameResolver().resolve(str, new Consumer() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda16
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        AyuRequestUtils.$r8$lambda$k9kTgWcExmW9fP29o1byymAMCSU(messagesController, i, probeConsumer, (Long) obj);
                    }
                });
                return;
            }
            probeOnline(i, user, probeConsumer);
        }
    }

    public static /* synthetic */ void $r8$lambda$k9kTgWcExmW9fP29o1byymAMCSU(MessagesController messagesController, int i, ProbeConsumer probeConsumer, Long l) {
        if (l != null) {
            TLRPC.User user = messagesController.getUser(l);
            if (user != null) {
                probeOnline(i, user, probeConsumer);
                return;
            } else {
                probeConsumer.accept(null, i);
                return;
            }
        }
        probeConsumer.accept(null, i);
    }

    private static void probeOnline(final int i, final TLRPC.User user, final ProbeConsumer probeConsumer) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda19
            @Override // java.lang.Runnable
            public final void run() {
                ContactsController.getInstance(i).loadPrivacySettings();
            }
        });
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                AyuRequestUtils.probeOnlineInner(i, user, probeConsumer);
            }
        }, 1200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00f6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void probeOnlineInner(final int r17, org.telegram.tgnet.TLRPC.User r18, final com.radolyn.ayugram.utils.network.AyuRequestUtils.ProbeConsumer r19) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.radolyn.ayugram.utils.network.AyuRequestUtils.probeOnlineInner(int, org.telegram.tgnet.TLRPC$User, com.radolyn.ayugram.utils.network.AyuRequestUtils$ProbeConsumer):void");
    }

    private static void performHack(final int i, final int i2, final TLRPC.User user, final ArrayList arrayList, final ArrayList arrayList2, final MessagesController messagesController, final Consumer consumer) {
        TL_account.setPrivacy setprivacy = new TL_account.setPrivacy();
        setprivacy.key = new TLRPC.TL_inputPrivacyKeyStatusTimestamp();
        fillPrivacy(i2, true, user, arrayList, arrayList2, messagesController, setprivacy);
        ConnectionsManager.getInstance(i).sendRequest(setprivacy, new RequestDelegate() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda26
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda29
                    @Override // java.lang.Runnable
                    public final void run() {
                        AyuRequestUtils.$r8$lambda$rUhBlon6rpd_TRoXMkjGaV7WCAU(tL_error, tLObject, i, i, user, arrayList, arrayList, messagesController, consumer);
                    }
                });
            }
        }, 2);
    }

    public static /* synthetic */ void $r8$lambda$rUhBlon6rpd_TRoXMkjGaV7WCAU(TLRPC.TL_error tL_error, TLObject tLObject, final int i, final int i2, final TLRPC.User user, final ArrayList arrayList, final ArrayList arrayList2, final MessagesController messagesController, final Consumer consumer) {
        if (tL_error == null) {
            TL_account.privacyRules privacyrules = (TL_account.privacyRules) tLObject;
            MessagesController.getInstance(i).putUsers(privacyrules.users, false);
            MessagesController.getInstance(i).putChats(privacyrules.chats, false);
            ContactsController.getInstance(i).setPrivacyRules(privacyrules.rules, 0);
            fetchAndSet(i, user, new Consumer() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda2
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    AyuRequestUtils.$r8$lambda$ZMB9_yPHR62wWuM3FZjg8MSXYP0(i2, user, arrayList, arrayList2, messagesController, i, consumer, (String) obj);
                }
            });
            return;
        }
        consumer.accept(null);
    }

    public static /* synthetic */ void $r8$lambda$ZMB9_yPHR62wWuM3FZjg8MSXYP0(int i, TLRPC.User user, ArrayList arrayList, ArrayList arrayList2, MessagesController messagesController, final int i2, Consumer consumer, String str) {
        TL_account.setPrivacy setprivacy = new TL_account.setPrivacy();
        setprivacy.key = new TLRPC.TL_inputPrivacyKeyStatusTimestamp();
        fillPrivacy(i, false, user, arrayList, arrayList2, messagesController, setprivacy);
        ConnectionsManager.getInstance(i2).sendRequest(setprivacy, new RequestDelegate() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda3
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        AyuRequestUtils.$r8$lambda$iUA9EAqJpEmiXYJDatQGXbQ2GB8(tL_error, tLObject, i);
                    }
                });
            }
        }, 2);
        consumer.accept(str);
    }

    public static /* synthetic */ void $r8$lambda$iUA9EAqJpEmiXYJDatQGXbQ2GB8(TLRPC.TL_error tL_error, TLObject tLObject, int i) {
        if (tL_error == null) {
            TL_account.privacyRules privacyrules = (TL_account.privacyRules) tLObject;
            MessagesController.getInstance(i).putUsers(privacyrules.users, false);
            MessagesController.getInstance(i).putChats(privacyrules.chats, false);
            ContactsController.getInstance(i).setPrivacyRules(privacyrules.rules, 0);
        }
    }

    private static void fillPrivacy(int i, boolean z, TLRPC.User user, ArrayList arrayList, ArrayList arrayList2, MessagesController messagesController, TL_account.setPrivacy setprivacy) {
        if (i == 0) {
            if (z) {
                fillPrivacyEverybody(Long.valueOf(user.f1734id), arrayList2, messagesController, setprivacy);
            } else {
                fillPrivacyEverybody(null, arrayList2, messagesController, setprivacy);
            }
        } else if (i == 1) {
            fillPrivacyNobody(user, !z, arrayList, messagesController, setprivacy);
        } else if (i == 2) {
            if (z) {
                fillPrivacyEverybody(Long.valueOf(user.f1734id), arrayList2, messagesController, setprivacy);
                fillPrivacyNobody(user, false, arrayList, messagesController, setprivacy);
            } else {
                fillPrivacyEverybody(null, arrayList2, messagesController, setprivacy);
                fillPrivacyNobody(user, true, arrayList, messagesController, setprivacy);
            }
        }
        if (i == 0) {
            setprivacy.rules.add(new TLRPC.TL_inputPrivacyValueAllowAll());
        } else if (i == 1) {
            setprivacy.rules.add(new TLRPC.TL_inputPrivacyValueDisallowAll());
        } else if (i == 2) {
            setprivacy.rules.add(new TLRPC.TL_inputPrivacyValueAllowContacts());
        }
    }

    private static void fillPrivacyEverybody(Long l, ArrayList arrayList, MessagesController messagesController, TL_account.setPrivacy setprivacy) {
        TLRPC.InputUser inputUser;
        if (arrayList.isEmpty()) {
            return;
        }
        TLRPC.TL_inputPrivacyValueDisallowUsers tL_inputPrivacyValueDisallowUsers = new TLRPC.TL_inputPrivacyValueDisallowUsers();
        TLRPC.TL_inputPrivacyValueDisallowChatParticipants tL_inputPrivacyValueDisallowChatParticipants = new TLRPC.TL_inputPrivacyValueDisallowChatParticipants();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Long l2 = (Long) obj;
            long jLongValue = l2.longValue();
            if (l == null || jLongValue != l.longValue()) {
                if (DialogObject.isUserDialog(jLongValue)) {
                    TLRPC.User user = messagesController.getUser(l2);
                    if (user != null && (inputUser = messagesController.getInputUser(user)) != null) {
                        tL_inputPrivacyValueDisallowUsers.users.add(inputUser);
                    }
                } else {
                    tL_inputPrivacyValueDisallowChatParticipants.chats.add(Long.valueOf(-jLongValue));
                }
            }
        }
        setprivacy.rules.add(tL_inputPrivacyValueDisallowUsers);
        setprivacy.rules.add(tL_inputPrivacyValueDisallowChatParticipants);
    }

    private static void fillPrivacyNobody(TLRPC.User user, boolean z, ArrayList arrayList, MessagesController messagesController, TL_account.setPrivacy setprivacy) {
        TLRPC.InputUser inputUser;
        TLRPC.InputUser inputUser2;
        if (arrayList.isEmpty() && z) {
            return;
        }
        TLRPC.TL_inputPrivacyValueAllowUsers tL_inputPrivacyValueAllowUsers = new TLRPC.TL_inputPrivacyValueAllowUsers();
        TLRPC.TL_inputPrivacyValueAllowChatParticipants tL_inputPrivacyValueAllowChatParticipants = new TLRPC.TL_inputPrivacyValueAllowChatParticipants();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Long l = (Long) obj;
            long jLongValue = l.longValue();
            if (!z || jLongValue != user.f1734id) {
                if (DialogObject.isUserDialog(jLongValue)) {
                    TLRPC.User user2 = messagesController.getUser(l);
                    if (user2 != null && (inputUser2 = messagesController.getInputUser(user2)) != null) {
                        tL_inputPrivacyValueAllowUsers.users.add(inputUser2);
                    }
                } else {
                    tL_inputPrivacyValueAllowChatParticipants.chats.add(Long.valueOf(-jLongValue));
                }
            }
        }
        if (!z && (inputUser = messagesController.getInputUser(user)) != null) {
            tL_inputPrivacyValueAllowUsers.users.add(inputUser);
        }
        setprivacy.rules.add(tL_inputPrivacyValueAllowUsers);
        setprivacy.rules.add(tL_inputPrivacyValueAllowChatParticipants);
    }

    private static void fetchAndSet(final int i, final TLRPC.User user, final Consumer consumer) {
        TLRPC.TL_users_getFullUser tL_users_getFullUser = new TLRPC.TL_users_getFullUser();
        TLRPC.TL_inputUser tL_inputUser = new TLRPC.TL_inputUser();
        tL_users_getFullUser.f1726id = tL_inputUser;
        tL_inputUser.user_id = user.f1734id;
        tL_inputUser.access_hash = user.access_hash;
        ConnectionsManager.getInstance(i).sendRequest(tL_users_getFullUser, new RequestDelegate() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda4
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AyuRequestUtils.$r8$lambda$0ZECZWXDFGH6NfaqVWEzO8uHBH0(i, consumer, user, tLObject, tL_error);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$0ZECZWXDFGH6NfaqVWEzO8uHBH0(int i, Consumer consumer, TLRPC.User user, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tL_error == null) {
            if (tLObject instanceof TLRPC.TL_userFull) {
                formatStatusIfSuccess(i, ((TLRPC.TL_userFull) tLObject).user, consumer);
                return;
            }
            if (tLObject instanceof TLRPC.TL_users_userFull) {
                TLRPC.TL_users_userFull tL_users_userFull = (TLRPC.TL_users_userFull) tLObject;
                TLRPC.UserFull userFull = tL_users_userFull.full_user;
                TLRPC.User user2 = userFull != null ? userFull.user : null;
                if (user2 == null) {
                    ArrayList arrayList = tL_users_userFull.users;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        Object obj = arrayList.get(i2);
                        i2++;
                        TLRPC.User user3 = (TLRPC.User) obj;
                        if (user3.f1734id == user.f1734id) {
                            user2 = user3;
                            break;
                        }
                    }
                }
                formatStatusIfSuccess(i, user2, consumer);
                return;
            }
            consumer.accept(null);
            return;
        }
        consumer.accept(null);
    }

    public static boolean isKnownOnline(TLRPC.User user) {
        TLRPC.UserStatus userStatus;
        TLRPC.UserStatus userStatus2;
        int i;
        if (user == null || (userStatus = user.status) == null || userStatus.expires == 0 || UserObject.isDeleted(user) || (user instanceof TLRPC.TL_userEmpty) || (i = (userStatus2 = user.status).expires) == -1 || i == -100 || i == -101 || i == -102) {
            return false;
        }
        return (userStatus2 instanceof TLRPC.TL_userStatusOffline) || (userStatus2 instanceof TLRPC.TL_userStatusOnline);
    }

    private static void formatStatusIfSuccess(int i, TLRPC.User user, Consumer consumer) {
        if (isKnownOnline(user)) {
            consumer.accept(LocaleController.formatUserStatus(i, user));
        } else {
            consumer.accept(null);
        }
    }

    public static void resolveAllChats(final HashMap map) {
        queue.postRunnable(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda17
            @Override // java.lang.Runnable
            public final void run() throws InterruptedException {
                AyuRequestUtils.m2939$r8$lambda$yfFzEImhTJ1emGpECdVe4z2j7I(map);
            }
        });
    }

    /* renamed from: $r8$lambda$y-fFzEImhTJ1emGpECdVe4z2j7I, reason: not valid java name */
    public static /* synthetic */ void m2939$r8$lambda$yfFzEImhTJ1emGpECdVe4z2j7I(HashMap map) throws InterruptedException {
        HashMap map2 = new HashMap(map);
        while (!map2.isEmpty()) {
            for (Map.Entry entry : new HashMap(map2).entrySet()) {
                final Long l = (Long) entry.getKey();
                final String str = (String) entry.getValue();
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                final boolean[] zArr = new boolean[1];
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda22
                    @Override // java.lang.Runnable
                    public final void run() {
                        AyuRequestUtils.resolveChat(str, l.longValue(), new Consumer() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda24
                            @Override // com.google.android.exoplayer2.util.Consumer
                            public final void accept(Object obj) {
                                AyuRequestUtils.$r8$lambda$Oi9bQ59CNW0buC3yBvWJh15lnBA(zArr, countDownLatch, (Boolean) obj);
                            }
                        });
                    }
                });
                try {
                    countDownLatch.await();
                } catch (InterruptedException unused) {
                }
                if (!zArr[0]) {
                    try {
                        Thread.sleep(20000L);
                    } catch (InterruptedException unused2) {
                    }
                } else {
                    map2.remove(l);
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda23
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(AyuConstants.PEER_RESOLVED_NOTIFICATION, new Object[0]);
                        }
                    });
                    Thread.sleep(1500L);
                }
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$Oi9bQ59CNW0buC3yBvWJh15lnBA(boolean[] zArr, CountDownLatch countDownLatch, Boolean bool) {
        zArr[0] = bool.booleanValue();
        countDownLatch.countDown();
    }

    public static void resolveChat(String str, final long j, final Consumer consumer) {
        final MessagesController messagesController = MessagesController.getInstance(UserConfig.selectedAccount);
        if (str.startsWith(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317246668701222L))) {
            messagesController.getUserNameResolver().resolve(str.substring(1), new Consumer() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda27
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    consumer.accept(Boolean.TRUE);
                }
            });
        } else {
            if (str.startsWith(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317255258635814L)) || str.startsWith(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317276733472294L))) {
                String strSubstring = str.substring(str.lastIndexOf(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317332568047142L)) + 2);
                TLRPC.TL_messages_checkChatInvite tL_messages_checkChatInvite = new TLRPC.TL_messages_checkChatInvite();
                tL_messages_checkChatInvite.hash = strSubstring;
                ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tL_messages_checkChatInvite, new RequestDelegate() { // from class: com.radolyn.ayugram.utils.network.AyuRequestUtils$$ExternalSyntheticLambda28
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        AyuRequestUtils.m2933$r8$lambda$80AYbOzS_7KgMSVI2jbsW4HOg(j, messagesController, consumer, tLObject, tL_error);
                    }
                });
                return;
            }
            consumer.accept(Boolean.TRUE);
        }
    }

    /* renamed from: $r8$lambda$80AYbOzS_7KgMS-VI-2jbsW4HOg, reason: not valid java name */
    public static /* synthetic */ void m2933$r8$lambda$80AYbOzS_7KgMSVI2jbsW4HOg(long j, MessagesController messagesController, Consumer consumer, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tL_error == null && (tLObject instanceof TLRPC.ChatInvite)) {
            TLRPC.ChatInvite chatInvite = (TLRPC.ChatInvite) tLObject;
            if (chatInvite.chat != null && Math.abs(j) == Math.abs(chatInvite.chat.f1571id)) {
                MessagesStorage messagesStorage = MessagesStorage.getInstance(UserConfig.selectedAccount);
                ArrayList arrayList = new ArrayList();
                arrayList.add(chatInvite.chat);
                messagesController.putChat(chatInvite.chat, false);
                messagesStorage.putUsersAndChats(null, arrayList, true, true);
            }
        }
        if (tL_error != null && tL_error.text.contains(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019317345452949030L))) {
            consumer.accept(Boolean.FALSE);
        } else {
            consumer.accept(Boolean.TRUE);
        }
    }

    public static TLRPC.InputPeer extractPeer(TLObject tLObject) {
        TLRPC.InputPeer inputPeer;
        if (tLObject instanceof TLRPC.TL_messages_sendMessage) {
            inputPeer = ((TLRPC.TL_messages_sendMessage) tLObject).peer;
        } else if (tLObject instanceof TLRPC.TL_messages_sendMedia) {
            inputPeer = ((TLRPC.TL_messages_sendMedia) tLObject).peer;
        } else if (tLObject instanceof TLRPC.TL_messages_sendMultiMedia) {
            inputPeer = ((TLRPC.TL_messages_sendMultiMedia) tLObject).peer;
        } else if (tLObject instanceof TLRPC.TL_messages_forwardMessages) {
            inputPeer = ((TLRPC.TL_messages_forwardMessages) tLObject).to_peer;
        } else {
            inputPeer = tLObject instanceof TLRPC.TL_messages_sendInlineBotResult ? ((TLRPC.TL_messages_sendInlineBotResult) tLObject).peer : null;
        }
        if (tLObject instanceof TLRPC.TL_messages_sendReaction) {
            return ((TLRPC.TL_messages_sendReaction) tLObject).peer;
        }
        return tLObject instanceof TLRPC.TL_messages_sendVote ? ((TLRPC.TL_messages_sendVote) tLObject).peer : inputPeer;
    }

    public static long getDialogIdFromReadReq(TLObject tLObject) {
        if (tLObject instanceof TLRPC.TL_messages_readHistory) {
            return getDialogId(((TLRPC.TL_messages_readHistory) tLObject).peer).longValue();
        }
        if (tLObject instanceof TLRPC.TL_messages_readEncryptedHistory) {
            return ((TLRPC.TL_messages_readEncryptedHistory) tLObject).peer.chat_id;
        }
        if (tLObject instanceof TLRPC.TL_messages_readDiscussion) {
            return getDialogId(((TLRPC.TL_messages_readDiscussion) tLObject).peer).longValue();
        }
        if (tLObject instanceof TLRPC.TL_messages_readMessageContents) {
            try {
                ArrayList arrayList = ((TLRPC.TL_messages_readMessageContents) tLObject).f1682id;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    MessageObject existingMessageInAnyWay = MessagesController.getInstance(UserConfig.selectedAccount).getExistingMessageInAnyWay(0L, (Integer) obj);
                    if (existingMessageInAnyWay != null) {
                        long dialogId = existingMessageInAnyWay.getDialogId();
                        if (dialogId != 0) {
                            return dialogId;
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        } else {
            if (tLObject instanceof TLRPC.TL_channels_readHistory) {
                return getDialogId(((TLRPC.TL_channels_readHistory) tLObject).channel).longValue();
            }
            if (tLObject instanceof TLRPC.TL_channels_readMessageContents) {
                return getDialogId(((TLRPC.TL_channels_readMessageContents) tLObject).channel).longValue();
            }
            if (tLObject instanceof TLRPC.TL_messages_markDialogUnread) {
                TLRPC.InputDialogPeer inputDialogPeer = ((TLRPC.TL_messages_markDialogUnread) tLObject).peer;
                if (inputDialogPeer instanceof TLRPC.TL_inputDialogPeer) {
                    return getDialogId(((TLRPC.TL_inputDialogPeer) inputDialogPeer).peer).longValue();
                }
            }
        }
        return 0L;
    }

    public static long getDialogIdFromTypingReq(TLObject tLObject) {
        if (tLObject instanceof TLRPC.TL_messages_setTyping) {
            return getDialogId(((TLRPC.TL_messages_setTyping) tLObject).peer).longValue();
        }
        if (tLObject instanceof TLRPC.TL_messages_setEncryptedTyping) {
            return ((TLRPC.TL_messages_setEncryptedTyping) tLObject).peer.chat_id;
        }
        return 0L;
    }

    public static boolean isOnlineRequest(long j, TLObject tLObject) {
        if (isSendMessageRequest(tLObject) || isReadMessageRequest(tLObject) || isEditMessageRequest(tLObject)) {
            return true;
        }
        if (AyuGhostConfig.isMarkReadAfterAction(j) && isSendReactionRequest(tLObject)) {
            return true;
        }
        return (AyuGhostConfig.isMarkReadAfterAction(j) && isSendPollRequest(tLObject)) || (tLObject instanceof TLRPC.TL_messages_createChat) || (tLObject instanceof TLRPC.TL_channels_createChannel) || (tLObject instanceof TL_forum.TL_messages_createForumTopic) || (tLObject instanceof TLRPC.TL_channels_leaveChannel) || (tLObject instanceof TL_forum.TL_messages_deleteTopicHistory) || (tLObject instanceof TL_forum.TL_messages_editForumTopic) || (tLObject instanceof TLRPC.TL_messages_updatePinnedMessage) || (tLObject instanceof TL_phone.requestCall) || (tLObject instanceof TL_phone.acceptCall) || (tLObject instanceof TL_phone.confirmCall) || (tLObject instanceof TL_stories.TL_stories_sendStory) || (tLObject instanceof TL_stories.TL_stories_sendReaction) || (tLObject instanceof TL_stories.TL_stories_readStories);
    }

    public static boolean isSetTypingRequest(TLObject tLObject) {
        return (tLObject instanceof TLRPC.TL_messages_setTyping) || (tLObject instanceof TLRPC.TL_messages_setEncryptedTyping);
    }

    public static boolean isSendMessageRequest(TLObject tLObject) {
        return (tLObject instanceof TLRPC.TL_messages_sendMessage) || (tLObject instanceof TLRPC.TL_messages_sendMedia) || (tLObject instanceof TLRPC.TL_messages_sendMultiMedia) || (tLObject instanceof TLRPC.TL_messages_forwardMessages) || (tLObject instanceof TLRPC.TL_messages_sendInlineBotResult) || (tLObject instanceof TLRPC.TL_messages_sendEncrypted) || (tLObject instanceof TLRPC.TL_messages_sendEncryptedFile) || (tLObject instanceof TLRPC.TL_messages_sendEncryptedMultiMedia) || (tLObject instanceof TLRPC.TL_messages_sendEncryptedService);
    }

    public static boolean isEditMessageRequest(TLObject tLObject) {
        return tLObject instanceof TLRPC.TL_messages_editMessage;
    }

    public static boolean isSendReactionRequest(TLObject tLObject) {
        return tLObject instanceof TLRPC.TL_messages_sendReaction;
    }

    public static boolean isSendPollRequest(TLObject tLObject) {
        return tLObject instanceof TLRPC.TL_messages_sendVote;
    }

    public static boolean isReadMessageRequest(TLObject tLObject) {
        return (tLObject instanceof TLRPC.TL_messages_readHistory) || (tLObject instanceof TLRPC.TL_messages_readEncryptedHistory) || (tLObject instanceof TLRPC.TL_messages_readDiscussion) || (tLObject instanceof TLRPC.TL_messages_readMessageContents) || (tLObject instanceof TLRPC.TL_messages_readSavedHistory) || (tLObject instanceof TLRPC.TL_channels_readHistory) || (tLObject instanceof TLRPC.TL_channels_readMessageContents) || (tLObject instanceof TLRPC.TL_messages_markDialogUnread);
    }

    public static boolean isReadStoryRequest(TLObject tLObject) {
        return (tLObject instanceof TL_stories.TL_stories_readStories) || (tLObject instanceof TL_stories.TL_stories_incrementStoryViews);
    }

    public static Integer getMessageId(TLObject tLObject) {
        if (tLObject instanceof TLRPC.TL_messages_sendReaction) {
            return Integer.valueOf(((TLRPC.TL_messages_sendReaction) tLObject).msg_id);
        }
        if (tLObject instanceof TLRPC.TL_messages_sendVote) {
            return Integer.valueOf(((TLRPC.TL_messages_sendVote) tLObject).msg_id);
        }
        return null;
    }

    public static long getThreadId(long j) {
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (!(safeLastFragment instanceof ChatActivity)) {
            return 0L;
        }
        ChatActivity chatActivity = (ChatActivity) safeLastFragment;
        if (chatActivity.getDialogId() == j) {
            return chatActivity.getThreadId();
        }
        return 0L;
    }
}
