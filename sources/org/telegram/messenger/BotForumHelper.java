package org.telegram.messenger;

import android.text.TextUtils;
import android.util.LongSparseArray;
import android.util.SparseIntArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.utils.tlutils.TlUtils;
import org.telegram.p023ui.MultiLayoutTypingAnimator;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_forum;

/* loaded from: classes4.dex */
public class BotForumHelper extends BaseController {
    private static volatile BotForumHelper[] Instance = new BotForumHelper[16];
    private final DialogTopicIdKeyMap<BotDraftMessage> botTextDraftsByRandomIds;
    private final LongSparseArray<List<MessagesStorage.IntCallback>> pendingBotTopics;

    public boolean isThinking(long j, int i) {
        LongSparseArray<BotDraftMessage> longSparseArray = this.botTextDraftsByRandomIds.get(j, i);
        return longSparseArray != null && longSparseArray.size() > 0;
    }

    private MessageObject createDraftMessage(long j, int i, long j2, int i2, TLRPC.TL_textWithEntities tL_textWithEntities) {
        TLRPC.TL_message tL_message = new TLRPC.TL_message();
        tL_message.dialog_id = j;
        tL_message.peer_id = getMessagesController().getPeer(j);
        tL_message.from_id = getMessagesController().getPeer(j);
        tL_message.local_id = i2;
        tL_message.f1597id = i2;
        tL_message.random_id = j2;
        tL_message.message = tL_textWithEntities.text;
        tL_message.entities = tL_textWithEntities.entities;
        tL_message.flags |= 128;
        tL_message.date = getConnectionsManager().getCurrentTime();
        TLRPC.TL_messageReplyHeader tL_messageReplyHeader = new TLRPC.TL_messageReplyHeader();
        tL_message.reply_to = tL_messageReplyHeader;
        tL_message.flags |= 16;
        tL_messageReplyHeader.forum_topic = true;
        tL_messageReplyHeader.reply_to_top_id = i;
        tL_messageReplyHeader.flags |= 2;
        tL_message.media = new TLRPC.TL_messageMediaEmpty();
        tL_message.flags |= 512;
        MessageObject messageObject = new MessageObject(this.currentAccount, tL_message, true, true);
        messageObject.isBotPendingDraft = true;
        messageObject.resetLayout();
        return messageObject;
    }

    public void onBotForumDraftUpdate(final long j, final int i, final long j2, TLRPC.TL_textWithEntities tL_textWithEntities) {
        FileLog.m1157d("[BotForum] onDraftNewDraft " + j + " " + i + " " + j2);
        long j3 = (long) i;
        long j4 = j3;
        BotDraftMessage botDraftMessage = this.botTextDraftsByRandomIds.get(j, j3, j2);
        if (botDraftMessage == null) {
            botDraftMessage = new BotDraftMessage(j, i, j2, getUserConfig().getNewMessageId());
            this.botTextDraftsByRandomIds.put(j, j4, j2, botDraftMessage);
            j4 = j4;
        }
        BotDraftMessage botDraftMessage2 = botDraftMessage;
        long j5 = j4;
        boolean z = botDraftMessage2.messageObject == null;
        if (botDraftMessage2.selfDestruct != null) {
            AndroidUtilities.cancelRunOnUIThread(botDraftMessage2.selfDestruct);
        }
        botDraftMessage2.selfDestruct = new Runnable() { // from class: org.telegram.messenger.BotForumHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onBotForumDraftUpdate$0(j, i, j2);
            }
        };
        botDraftMessage2.text = tL_textWithEntities;
        botDraftMessage2.messageObject = createDraftMessage(j, i, j2, botDraftMessage2.localMessageId, tL_textWithEntities);
        AndroidUtilities.runOnUIThread(botDraftMessage2.selfDestruct, getAppGlobalConfig().messageTypingDraftTtl.get(TimeUnit.MILLISECONDS));
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.botForumDraftUpdate, new BotForumTextDraftUpdateNotification(j, j5, botDraftMessage2.messageObject, z));
    }

    public MessageObject onBotForumDraftCheckNewMessages(long j, int i, int i2, String str) {
        long j2 = i;
        LongSparseArray<BotDraftMessage> longSparseArray = this.botTextDraftsByRandomIds.get(j, j2);
        if (longSparseArray == null) {
            return null;
        }
        for (int i3 = 0; i3 < longSparseArray.size(); i3++) {
            BotDraftMessage botDraftMessageValueAt = longSparseArray.valueAt(i3);
            if (str.startsWith(botDraftMessageValueAt.text.text)) {
                if (botDraftMessageValueAt.selfDestruct != null) {
                    AndroidUtilities.cancelRunOnUIThread(botDraftMessageValueAt.selfDestruct);
                }
                this.botTextDraftsByRandomIds.remove(j, j2, botDraftMessageValueAt.randomId);
                FileLog.m1157d("[BotForum] onDraftNewMessage " + j + " " + i);
                return botDraftMessageValueAt.messageObject;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onBotForumDraftTimeout, reason: merged with bridge method [inline-methods] */
    public void lambda$onBotForumDraftUpdate$0(long j, int i, long j2) {
        long j3 = i;
        BotDraftMessage botDraftMessageRemove = this.botTextDraftsByRandomIds.remove(j, j3, j2);
        if (botDraftMessageRemove == null) {
            return;
        }
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.botForumDraftDelete, new BotForumTextDraftDeleteNotification(j, j3, botDraftMessageRemove.localMessageId));
    }

    private static class BotDraftMessage {
        public final int localMessageId;
        private MessageObject messageObject;
        public final long randomId;
        private Runnable selfDestruct;
        private TLRPC.TL_textWithEntities text;
        public final int topicId;
        public final long userId;

        private BotDraftMessage(long j, int i, long j2, int i2) {
            this.userId = j;
            this.topicId = i;
            this.randomId = j2;
            this.localMessageId = i2;
        }
    }

    public boolean beforeSendingFinalRequest(TLObject tLObject, MessageObject messageObject, Runnable runnable) {
        return beforeSendingFinalRequest(tLObject, Collections.singletonList(messageObject), runnable);
    }

    public boolean beforeSendingFinalRequest(final TLObject tLObject, List<MessageObject> list, final Runnable runnable) {
        if (list != null && !list.isEmpty()) {
            TLRPC.InputPeer inputPeerFromSendMessageRequest = TlUtils.getInputPeerFromSendMessageRequest(tLObject);
            final long peerDialogId = DialogObject.getPeerDialogId(inputPeerFromSendMessageRequest);
            if (inputPeerFromSendMessageRequest == null || peerDialogId <= 0 || !UserObject.isBotForum(getMessagesController().getUser(Long.valueOf(peerDialogId)))) {
                return true;
            }
            final long[] jArr = new long[list.size()];
            for (int i = 0; i < list.size(); i++) {
                jArr[i] = list.get(i).getId();
            }
            long orCalculateRandomIdFromSendMessageRequest = TlUtils.getOrCalculateRandomIdFromSendMessageRequest(tLObject);
            if (TlUtils.getInputReplyToFromSendMessageRequest(tLObject) instanceof TLRPC.TL_inputReplyToMessage) {
                return true;
            }
            if ((tLObject instanceof TLRPC.TL_messages_forwardMessages) && ((TLRPC.TL_messages_forwardMessages) tLObject).top_msg_id != 0) {
                return true;
            }
            String messageFromSendMessageRequest = TlUtils.getMessageFromSendMessageRequest(tLObject);
            long nextRandomId = orCalculateRandomIdFromSendMessageRequest != 0 ? ~orCalculateRandomIdFromSendMessageRequest : getSendMessagesHelper().getNextRandomId();
            if (!TextUtils.isEmpty(messageFromSendMessageRequest)) {
                if (messageFromSendMessageRequest.length() > 16) {
                    messageFromSendMessageRequest = messageFromSendMessageRequest.substring(0, 16) + "...";
                }
            } else {
                messageFromSendMessageRequest = LocaleController.getString(C2369R.string.TopicsTitleMedia);
            }
            performSendBotTopicCreate(inputPeerFromSendMessageRequest, messageFromSendMessageRequest, nextRandomId, new MessagesStorage.IntCallback() { // from class: org.telegram.messenger.BotForumHelper$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.MessagesStorage.IntCallback
                public final void run(int i2) {
                    this.f$0.lambda$beforeSendingFinalRequest$2(tLObject, jArr, peerDialogId, runnable, i2);
                }
            });
            return false;
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$beforeSendingFinalRequest$2(TLObject tLObject, final long[] jArr, final long j, final Runnable runnable, final int i) {
        if (tLObject instanceof TLRPC.TL_messages_forwardMessages) {
            TLRPC.TL_messages_forwardMessages tL_messages_forwardMessages = (TLRPC.TL_messages_forwardMessages) tLObject;
            tL_messages_forwardMessages.top_msg_id = i;
            tL_messages_forwardMessages.flags |= 512;
        } else {
            TLRPC.TL_inputReplyToMessage tL_inputReplyToMessage = new TLRPC.TL_inputReplyToMessage();
            tL_inputReplyToMessage.reply_to_msg_id = i;
            TlUtils.setInputReplyToFromSendMessageRequest(tLObject, tL_inputReplyToMessage);
        }
        getMessagesStorage().getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.messenger.BotForumHelper$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$beforeSendingFinalRequest$1(jArr, j, i, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$beforeSendingFinalRequest$1(long[] jArr, long j, int i, Runnable runnable) {
        for (long j2 : jArr) {
            getMessagesStorage().updateMessageTopicId(j, j2, i);
        }
        AndroidUtilities.runOnUIThread(runnable);
    }

    private void performSendBotTopicCreate(TLRPC.InputPeer inputPeer, final String str, long j, MessagesStorage.IntCallback intCallback) {
        final long peerDialogId = DialogObject.getPeerDialogId(inputPeer);
        List<MessagesStorage.IntCallback> list = this.pendingBotTopics.get(peerDialogId);
        if (list != null) {
            list.add(intCallback);
            return;
        }
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(intCallback);
        this.pendingBotTopics.put(peerDialogId, arrayList);
        TL_forum.TL_messages_createForumTopic tL_messages_createForumTopic = new TL_forum.TL_messages_createForumTopic();
        tL_messages_createForumTopic.title = TextUtils.isEmpty(str) ? "#New Chat" : str;
        tL_messages_createForumTopic.title_missing = true;
        tL_messages_createForumTopic.peer = inputPeer;
        tL_messages_createForumTopic.random_id = j;
        getConnectionsManager().sendRequestTyped(tL_messages_createForumTopic, new BotForumHelper$$ExternalSyntheticLambda2(), new Utilities.Callback2() { // from class: org.telegram.messenger.BotForumHelper$$ExternalSyntheticLambda3
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$performSendBotTopicCreate$3(peerDialogId, str, (TLRPC.Updates) obj, (TLRPC.TL_error) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSendBotTopicCreate$3(long j, String str, TLRPC.Updates updates, TLRPC.TL_error tL_error) {
        TLRPC.TL_updateMessageID tL_updateMessageID;
        if (updates == null) {
            performSendBotTopicCreateComplete(j, -1);
            return;
        }
        getMessagesController().processUpdates(updates, false);
        ArrayList<TLRPC.Update> arrayList = updates.updates;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                tL_updateMessageID = null;
                break;
            }
            TLRPC.Update update = arrayList.get(i);
            i++;
            TLRPC.Update update2 = update;
            if (update2 instanceof TLRPC.TL_updateMessageID) {
                tL_updateMessageID = (TLRPC.TL_updateMessageID) update2;
                break;
            }
        }
        if (tL_updateMessageID == null) {
            performSendBotTopicCreateComplete(j, -1);
            return;
        }
        TLRPC.TL_forumTopic tL_forumTopic = new TLRPC.TL_forumTopic();
        TLRPC.TL_messageService tL_messageService = new TLRPC.TL_messageService();
        TLRPC.TL_messageActionTopicCreate tL_messageActionTopicCreate = new TLRPC.TL_messageActionTopicCreate();
        tL_messageActionTopicCreate.title = str;
        tL_messageService.action = tL_messageActionTopicCreate;
        tL_messageService.peer_id = getMessagesController().getPeer(j);
        tL_messageService.dialog_id = j;
        tL_messageService.f1597id = tL_updateMessageID.f1724id;
        tL_messageService.date = (int) (System.currentTimeMillis() / 1000);
        int i2 = tL_updateMessageID.f1724id;
        tL_forumTopic.f1631id = i2;
        tL_forumTopic.f1632my = true;
        tL_forumTopic.flags |= 2;
        tL_forumTopic.topicStartMessage = tL_messageService;
        tL_forumTopic.title = str;
        tL_forumTopic.top_message = i2;
        tL_forumTopic.topMessage = tL_messageService;
        tL_forumTopic.from_id = getMessagesController().getPeer(getUserConfig().clientUserId);
        tL_forumTopic.notify_settings = new TLRPC.TL_peerNotifySettings();
        tL_forumTopic.icon_color = 0;
        tL_forumTopic.title_missing = true;
        getMessagesController().getTopicsController().onTopicCreated(j, tL_forumTopic, true);
        performSendBotTopicCreateComplete(j, tL_updateMessageID.f1724id);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.botForumTopicDidCreate, new BotForumTopicCreateNotification(j, tL_updateMessageID.f1724id));
    }

    private void performSendBotTopicCreateComplete(long j, int i) {
        List<MessagesStorage.IntCallback> list = this.pendingBotTopics.get(j);
        if (list != null) {
            this.pendingBotTopics.remove(j);
            Iterator<MessagesStorage.IntCallback> it = list.iterator();
            while (it.hasNext()) {
                it.next().run(i);
            }
        }
    }

    public static class BotForumTopicCreateNotification {
        public final long dialogId;
        public final int topicId;

        public BotForumTopicCreateNotification(long j, int i) {
            this.dialogId = j;
            this.topicId = i;
        }
    }

    public static class BotForumTextDraftUpdateNotification {
        public final long botTopicId;
        public final long botUserId;
        public final boolean isNew;
        public final MessageObject messageObject;

        public BotForumTextDraftUpdateNotification(long j, long j2, MessageObject messageObject, boolean z) {
            this.botUserId = j;
            this.botTopicId = j2;
            this.messageObject = messageObject;
            this.isNew = z;
        }
    }

    public static class BotForumTextDraftDeleteNotification {
        public final long botTopicId;
        public final long botUserId;
        public final int messageId;

        public BotForumTextDraftDeleteNotification(long j, long j2, int i) {
            this.botUserId = j;
            this.botTopicId = j2;
            this.messageId = i;
        }
    }

    public static boolean isBotForum(int i, long j) {
        if (j > 0) {
            return UserObject.isBotForum(MessagesController.getInstance(i).getUser(Long.valueOf(j)));
        }
        MessagesController.getInstance(i).getChat(Long.valueOf(-j));
        return false;
    }

    private BotForumHelper(int i) {
        super(i);
        this.botTextDraftsByRandomIds = new DialogTopicIdKeyMap<>();
        this.pendingBotTopics = new LongSparseArray<>();
    }

    public static BotForumHelper getInstance(int i) {
        BotForumHelper botForumHelper;
        BotForumHelper botForumHelper2 = Instance[i];
        if (botForumHelper2 != null) {
            return botForumHelper2;
        }
        synchronized (BotForumHelper.class) {
            try {
                botForumHelper = Instance[i];
                if (botForumHelper == null) {
                    BotForumHelper[] botForumHelperArr = Instance;
                    BotForumHelper botForumHelper3 = new BotForumHelper(i);
                    botForumHelperArr[i] = botForumHelper3;
                    botForumHelper = botForumHelper3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return botForumHelper;
    }

    /* loaded from: classes.dex */
    public static class BotDraftAnimationsPool {
        private final DialogTopicIdKeyMap<MultiLayoutTypingAnimator> animators = new DialogTopicIdKeyMap<>();
        private final SparseIntArray ids = new SparseIntArray();

        public MultiLayoutTypingAnimator getAnimator(long j, int i, boolean z) {
            if (i > 0) {
                i = this.ids.get(i, 0);
            }
            if (i == 0) {
                return null;
            }
            long j2 = i;
            MultiLayoutTypingAnimator multiLayoutTypingAnimator = this.animators.get(j, 0L, j2);
            if (multiLayoutTypingAnimator != null || !z) {
                return multiLayoutTypingAnimator;
            }
            MultiLayoutTypingAnimator multiLayoutTypingAnimator2 = new MultiLayoutTypingAnimator();
            this.animators.put(j, 0L, j2, multiLayoutTypingAnimator2);
            return multiLayoutTypingAnimator2;
        }

        public void bind(int i, int i2) {
            this.ids.put(i2, i);
        }

        public void removeAnimator(long j, int i) {
            if (i > 0) {
                i = this.ids.get(i, 0);
            }
            if (i == 0) {
                return;
            }
            this.animators.remove(j, 0L, i);
        }
    }

    /* loaded from: classes.dex */
    public static class DialogTopicIdKeyMap<T> {
        private final LongSparseArray<LongSparseArray<LongSparseArray<T>>> map = new LongSparseArray<>();

        public LongSparseArray<T> get(long j, long j2) {
            LongSparseArray<LongSparseArray<T>> longSparseArray = this.map.get(j);
            if (longSparseArray == null) {
                return null;
            }
            return longSparseArray.get(j2);
        }

        public T get(long j, long j2, long j3) {
            LongSparseArray<T> longSparseArray = get(j, j2);
            if (longSparseArray == null) {
                return null;
            }
            return longSparseArray.get(j3);
        }

        public T put(long j, long j2, long j3, T t) {
            LongSparseArray<LongSparseArray<T>> longSparseArray = this.map.get(j);
            if (longSparseArray == null) {
                longSparseArray = new LongSparseArray<>();
                this.map.put(j, longSparseArray);
            }
            LongSparseArray<T> longSparseArray2 = longSparseArray.get(j2);
            if (longSparseArray2 == null) {
                longSparseArray2 = new LongSparseArray<>();
                longSparseArray.put(j2, longSparseArray2);
            }
            T t2 = longSparseArray2.get(j3);
            longSparseArray2.put(j3, t);
            return t2;
        }

        public T remove(long j, long j2, long j3) {
            LongSparseArray<T> longSparseArray;
            LongSparseArray<LongSparseArray<T>> longSparseArray2 = this.map.get(j);
            if (longSparseArray2 == null || (longSparseArray = longSparseArray2.get(j2)) == null) {
                return null;
            }
            T t = longSparseArray.get(j3);
            longSparseArray.remove(j3);
            return t;
        }
    }
}
