package com.radolyn.ayugram.utils;

import android.text.TextUtils;
import android.util.SparseArray;
import androidx.collection.LongSparseArray;
import androidx.core.util.Pair;
import com.exteragram.messenger.badges.BadgesController;
import com.radolyn.ayugram.controllers.AyuMapper;
import com.radolyn.ayugram.controllers.AyuMessagesController;
import com.radolyn.ayugram.database.entities.AyuMessageBase;
import com.radolyn.ayugram.database.entities.DeletedMessageFull;
import com.radolyn.ayugram.database.entities.DeletedMessageReaction;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.p023ui.ChatActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes.dex */
public abstract class AyuHistoryHook {
    private static final SparseArray instances = new SparseArray();

    public static synchronized void setInstance(ChatActivity chatActivity) {
        try {
            for (int size = instances.size() - 1; size >= 0; size--) {
                SparseArray sparseArray = instances;
                int iKeyAt = sparseArray.keyAt(size);
                ChatActivity chatActivity2 = (ChatActivity) ((WeakReference) sparseArray.valueAt(size)).get();
                if (chatActivity2 == null || (chatActivity2 == chatActivity && iKeyAt != chatActivity.getClassGuid())) {
                    sparseArray.remove(size);
                }
            }
            instances.put(chatActivity.getClassGuid(), new WeakReference(chatActivity));
        } catch (Throwable th) {
            throw th;
        }
    }

    public static synchronized void removeInstance(ChatActivity chatActivity) {
        instances.remove(chatActivity.getClassGuid());
    }

    private static boolean needHook(long j, long j2) {
        if (j == -1905581924) {
            return j2 == 1;
        }
        return !BadgesController.INSTANCE.isExtera(Math.abs(j));
    }

    /* JADX WARN: Removed duplicated region for block: B:132:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0205 A[Catch: all -> 0x0041, TryCatch #0 {all -> 0x0041, blocks: (B:4:0x000b, B:13:0x001a, B:17:0x0028, B:21:0x0032, B:23:0x0038, B:28:0x0044, B:31:0x004a, B:34:0x0052, B:38:0x0062, B:44:0x0076, B:46:0x0080, B:48:0x0086, B:51:0x00ae, B:53:0x00b4, B:57:0x00bd, B:59:0x00d3, B:61:0x00d9, B:63:0x00dd, B:65:0x00e7, B:135:0x0205, B:137:0x021f, B:68:0x00f5, B:71:0x00fc, B:73:0x0103, B:75:0x0110, B:79:0x011d, B:81:0x0124, B:83:0x0131, B:84:0x0138, B:91:0x0146, B:93:0x014c, B:56:0x00b9, B:96:0x0158, B:99:0x016a, B:105:0x0179, B:113:0x018e), top: B:147:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x021f A[Catch: all -> 0x0041, TRY_LEAVE, TryCatch #0 {all -> 0x0041, blocks: (B:4:0x000b, B:13:0x001a, B:17:0x0028, B:21:0x0032, B:23:0x0038, B:28:0x0044, B:31:0x004a, B:34:0x0052, B:38:0x0062, B:44:0x0076, B:46:0x0080, B:48:0x0086, B:51:0x00ae, B:53:0x00b4, B:57:0x00bd, B:59:0x00d3, B:61:0x00d9, B:63:0x00dd, B:65:0x00e7, B:135:0x0205, B:137:0x021f, B:68:0x00f5, B:71:0x00fc, B:73:0x0103, B:75:0x0110, B:79:0x011d, B:81:0x0124, B:83:0x0131, B:84:0x0138, B:91:0x0146, B:93:0x014c, B:56:0x00b9, B:96:0x0158, B:99:0x016a, B:105:0x0179, B:113:0x018e), top: B:147:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0103 A[Catch: all -> 0x0041, TryCatch #0 {all -> 0x0041, blocks: (B:4:0x000b, B:13:0x001a, B:17:0x0028, B:21:0x0032, B:23:0x0038, B:28:0x0044, B:31:0x004a, B:34:0x0052, B:38:0x0062, B:44:0x0076, B:46:0x0080, B:48:0x0086, B:51:0x00ae, B:53:0x00b4, B:57:0x00bd, B:59:0x00d3, B:61:0x00d9, B:63:0x00dd, B:65:0x00e7, B:135:0x0205, B:137:0x021f, B:68:0x00f5, B:71:0x00fc, B:73:0x0103, B:75:0x0110, B:79:0x011d, B:81:0x0124, B:83:0x0131, B:84:0x0138, B:91:0x0146, B:93:0x014c, B:56:0x00b9, B:96:0x0158, B:99:0x016a, B:105:0x0179, B:113:0x018e), top: B:147:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x011d A[Catch: all -> 0x0041, TryCatch #0 {all -> 0x0041, blocks: (B:4:0x000b, B:13:0x001a, B:17:0x0028, B:21:0x0032, B:23:0x0038, B:28:0x0044, B:31:0x004a, B:34:0x0052, B:38:0x0062, B:44:0x0076, B:46:0x0080, B:48:0x0086, B:51:0x00ae, B:53:0x00b4, B:57:0x00bd, B:59:0x00d3, B:61:0x00d9, B:63:0x00dd, B:65:0x00e7, B:135:0x0205, B:137:0x021f, B:68:0x00f5, B:71:0x00fc, B:73:0x0103, B:75:0x0110, B:79:0x011d, B:81:0x0124, B:83:0x0131, B:84:0x0138, B:91:0x0146, B:93:0x014c, B:56:0x00b9, B:96:0x0158, B:99:0x016a, B:105:0x0179, B:113:0x018e), top: B:147:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0138 A[Catch: all -> 0x0041, TryCatch #0 {all -> 0x0041, blocks: (B:4:0x000b, B:13:0x001a, B:17:0x0028, B:21:0x0032, B:23:0x0038, B:28:0x0044, B:31:0x004a, B:34:0x0052, B:38:0x0062, B:44:0x0076, B:46:0x0080, B:48:0x0086, B:51:0x00ae, B:53:0x00b4, B:57:0x00bd, B:59:0x00d3, B:61:0x00d9, B:63:0x00dd, B:65:0x00e7, B:135:0x0205, B:137:0x021f, B:68:0x00f5, B:71:0x00fc, B:73:0x0103, B:75:0x0110, B:79:0x011d, B:81:0x0124, B:83:0x0131, B:84:0x0138, B:91:0x0146, B:93:0x014c, B:56:0x00b9, B:96:0x0158, B:99:0x016a, B:105:0x0179, B:113:0x018e), top: B:147:0x000b }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static synchronized int doHook(int r18, java.util.ArrayList r19, org.telegram.tgnet.TLRPC.messages_Messages r20, int r21, long r22, long r24, int r26, int r27, int r28, boolean r29, int r30, int r31, int r32, int r33, int r34, int r35, boolean r36, int r37, long r38, int r40, boolean r41, int r42, boolean r43, boolean r44) {
        /*
            Method dump skipped, instructions count: 555
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.radolyn.ayugram.utils.AyuHistoryHook.doHook(int, java.util.ArrayList, org.telegram.tgnet.TLRPC$messages_Messages, int, long, long, int, int, int, boolean, int, int, int, int, int, int, boolean, int, long, int, boolean, int, boolean, boolean):int");
    }

    private static void doHook(int i, ArrayList arrayList, SparseArray[] sparseArrayArr, int i2, int i3, long j, long j2, boolean z, boolean z2) {
        AyuMessagesController ayuMessagesController = AyuMessagesController.getInstance(i);
        List<DeletedMessageFull> messages = ayuMessagesController.getMessages(j, j2, i2, i3);
        if (messages.isEmpty()) {
            return;
        }
        LongSparseArray longSparseArray = new LongSparseArray();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (DeletedMessageFull deletedMessageFull : messages) {
            if (!isEmpty(deletedMessageFull.message)) {
                TLRPC.TL_message map = map(deletedMessageFull, i);
                longSparseArray.put(map.f1597id, map);
                MessagesStorage.addUsersAndChatsFromMessage(map, arrayList2, arrayList3, null);
            }
        }
        if (longSparseArray.isEmpty()) {
            return;
        }
        MessagesStorage messagesStorage = MessagesStorage.getInstance(i);
        MessagesController messagesController = MessagesController.getInstance(i);
        QuadroResult entities = getEntities(messagesStorage, arrayList2, arrayList3);
        Pair dicts = entities.getDicts();
        ArrayList users = entities.getUsers();
        ArrayList chats = entities.getChats();
        if (!users.isEmpty()) {
            messagesController.putUsers(users, true);
        }
        if (!chats.isEmpty()) {
            messagesController.putChats(chats, true);
        }
        ArrayList arrayList4 = new ArrayList();
        for (int i4 = 0; i4 < longSparseArray.size(); i4++) {
            try {
                arrayList4.add(new MessageObject(i, (TLRPC.Message) longSparseArray.get(longSparseArray.keyAt(i4)), (LongSparseArray) dicts.first, (LongSparseArray) dicts.second, false, true));
            } catch (Exception unused) {
            }
        }
        if (arrayList4.isEmpty()) {
            return;
        }
        merge(arrayList4, arrayList, z, z2);
        fixReplies(i, arrayList, sparseArrayArr, ayuMessagesController, dicts, messagesStorage);
    }

    public static void fixReplies(int i, List list, SparseArray[] sparseArrayArr, AyuMessagesController ayuMessagesController, Pair pair, MessagesStorage messagesStorage) {
        MessageObject messageObject;
        DeletedMessageFull message;
        TLRPC.Message message2;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MessageObject messageObject2 = (MessageObject) it.next();
            TLRPC.Message message3 = messageObject2.messageOwner;
            if (message3.reply_to != null && ((message3.replyMessage == null || messageObject2.replyMessageObject == null) && !messageObject2.isReplyToStory())) {
                if (sparseArrayArr != null) {
                    messageObject = (MessageObject) sparseArrayArr[0].get(messageObject2.messageOwner.reply_to.reply_to_msg_id);
                    if (messageObject == null || messageObject.getId() != messageObject2.messageOwner.reply_to.reply_to_msg_id) {
                        messageObject = (MessageObject) sparseArrayArr[1].get(messageObject2.messageOwner.reply_to.reply_to_msg_id);
                    }
                } else {
                    messageObject = null;
                }
                if (messageObject == null || messageObject.getId() != messageObject2.messageOwner.reply_to.reply_to_msg_id) {
                    Iterator it2 = list.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        MessageObject messageObject3 = (MessageObject) it2.next();
                        if (messageObject3.getId() == messageObject2.messageOwner.reply_to.reply_to_msg_id) {
                            messageObject = messageObject3;
                            break;
                        }
                    }
                }
                if ((messageObject == null || messageObject.getId() != messageObject2.messageOwner.reply_to.reply_to_msg_id) && (message = ayuMessagesController.getMessage(MessageObject.getDialogId(messageObject2.messageOwner), messageObject2.messageOwner.reply_to.reply_to_msg_id)) != null) {
                    try {
                        messageObject = new MessageObject(i, (TLRPC.Message) map(message, i), (LongSparseArray) pair.first, (LongSparseArray) pair.second, false, false);
                    } catch (Exception unused) {
                    }
                }
                if (messageObject == null && (message2 = messagesStorage.getMessage(MessageObject.getDialogId(messageObject2.messageOwner), messageObject2.messageOwner.reply_to.reply_to_msg_id)) != null) {
                    try {
                        messageObject = new MessageObject(i, message2, (LongSparseArray) pair.first, (LongSparseArray) pair.second, false, false);
                    } catch (Exception unused2) {
                    }
                }
                if (messageObject != null) {
                    TLRPC.Message message4 = messageObject2.messageOwner;
                    TLRPC.Message message5 = messageObject.messageOwner;
                    message4.replyMessage = message5;
                    message4.reply_to.reply_to_peer_id = message5.peer_id;
                    messageObject2.replyMessageObject = messageObject;
                }
            }
        }
    }

    private static void merge(ArrayList arrayList, ArrayList arrayList2, boolean z, boolean z2) {
        boolean z3 = (z && z2) || !(z || z2);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            MessageObject messageObject = (MessageObject) obj;
            if (arrayList2.isEmpty()) {
                arrayList2.add(messageObject);
            } else {
                for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                    MessageObject messageObject2 = (MessageObject) arrayList2.get(i2);
                    if (!z && ((messageObject.getId() < 0 && messageObject2.getId() > 0) || (messageObject.getId() > 0 && messageObject2.getId() < 0))) {
                        int i3 = messageObject.messageOwner.date;
                        int i4 = messageObject2.messageOwner.date;
                        if (z3) {
                            if (i3 < i4) {
                                arrayList2.add(i2, messageObject);
                                break;
                                break;
                            }
                        } else {
                            if (i3 > i4) {
                                arrayList2.add(i2, messageObject);
                                break;
                                break;
                            }
                        }
                    } else {
                        int id = messageObject.getId();
                        int id2 = messageObject2.getId();
                        if (z3) {
                            if (id < id2) {
                                arrayList2.add(i2, messageObject);
                                break;
                            }
                        } else {
                            if (id > id2) {
                                arrayList2.add(i2, messageObject);
                                break;
                                break;
                            }
                        }
                    }
                }
                MessageObject messageObject3 = (MessageObject) arrayList2.get(z3 ? arrayList2.size() - 1 : 0);
                if (!z && ((messageObject.getId() < 0 && messageObject3.getId() > 0) || (messageObject.getId() > 0 && messageObject3.getId() < 0))) {
                    int i5 = messageObject.messageOwner.date;
                    int i6 = messageObject3.messageOwner.date;
                    if (z3) {
                        if (i5 >= i6) {
                            arrayList2.add(messageObject);
                        } else {
                            arrayList2.add(0, messageObject);
                        }
                    } else if (i5 <= i6) {
                        arrayList2.add(messageObject);
                    } else {
                        arrayList2.add(0, messageObject);
                    }
                } else {
                    int id3 = messageObject.getId();
                    int id4 = messageObject3.getId();
                    if (z3) {
                        if (id3 >= id4) {
                            arrayList2.add(messageObject);
                        } else {
                            arrayList2.add(0, messageObject);
                        }
                    } else if (id3 <= id4) {
                        arrayList2.add(messageObject);
                    } else {
                        arrayList2.add(0, messageObject);
                    }
                }
            }
        }
    }

    public static QuadroResult getEntities(MessagesStorage messagesStorage, ArrayList arrayList, ArrayList arrayList2) {
        ArrayList<TLRPC.User> arrayList3 = new ArrayList<>();
        ArrayList<TLRPC.Chat> arrayList4 = new ArrayList<>();
        try {
            if (!arrayList.isEmpty()) {
                messagesStorage.getUsersInternal((ArrayList<Long>) arrayList, arrayList3);
            }
        } catch (Exception unused) {
        }
        try {
            if (!arrayList2.isEmpty()) {
                messagesStorage.getChatsInternal(TextUtils.join(",", arrayList2), arrayList4);
            }
        } catch (Exception unused2) {
        }
        return new QuadroResult(arrayList3, arrayList4);
    }

    public static boolean isEmpty(AyuMessageBase ayuMessageBase) {
        if (ayuMessageBase != null) {
            return TextUtils.isEmpty(ayuMessageBase.text) && TextUtils.isEmpty(ayuMessageBase.mediaPath) && ayuMessageBase.documentSerialized == null;
        }
        return true;
    }

    public static TLRPC.TL_message map(DeletedMessageFull deletedMessageFull, int i) {
        TLRPC.TL_message tL_message = new TLRPC.TL_message();
        AyuMapper.getInstance(i).map(deletedMessageFull.message, tL_message);
        AyuMapper.getInstance(i).mapMedia(deletedMessageFull.message, tL_message);
        List<DeletedMessageReaction> list = deletedMessageFull.reactions;
        if (list != null && !list.isEmpty()) {
            tL_message.reactions = new TLRPC.TL_messageReactions();
            int i2 = 0;
            for (DeletedMessageReaction deletedMessageReaction : deletedMessageFull.reactions) {
                TLRPC.TL_reactionCount tL_reactionCount = new TLRPC.TL_reactionCount();
                tL_reactionCount.count = deletedMessageReaction.count;
                tL_reactionCount.chosen = deletedMessageReaction.selfSelected;
                i2++;
                tL_reactionCount.chosen_order = i2;
                if (deletedMessageReaction.isCustom) {
                    TLRPC.TL_reactionCustomEmoji tL_reactionCustomEmoji = new TLRPC.TL_reactionCustomEmoji();
                    tL_reactionCustomEmoji.document_id = deletedMessageReaction.documentId;
                    tL_reactionCount.reaction = tL_reactionCustomEmoji;
                } else if (deletedMessageReaction.isPaid) {
                    tL_reactionCount.reaction = new TLRPC.TL_reactionPaid();
                } else {
                    TLRPC.TL_reactionEmoji tL_reactionEmoji = new TLRPC.TL_reactionEmoji();
                    tL_reactionEmoji.emoticon = deletedMessageReaction.emoticon;
                    tL_reactionCount.reaction = tL_reactionEmoji;
                }
                tL_message.reactions.results.add(tL_reactionCount);
            }
        }
        tL_message.ayuDeleted = true;
        return tL_message;
    }

    public static Pair getMinAndMaxIds(ArrayList arrayList, boolean z) {
        int size = arrayList.size();
        int i = ConnectionsManager.DEFAULT_DATACENTER_ID;
        int i2 = TLObject.FLAG_31;
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            MessageObject messageObject = (MessageObject) obj;
            if (messageObject.isSent() && messageObject.getId() != 0) {
                TLRPC.Message message = messageObject.messageOwner;
                if (!(message instanceof TLRPC.TL_messageEmpty) && (z || !(message instanceof TLRPC.TL_messageService))) {
                    int id = messageObject.getId();
                    if (id < i) {
                        i = id;
                    }
                    if (id > i2) {
                        i2 = id;
                    }
                }
            }
        }
        return new Pair(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public static class QuadroResult {
        private final ArrayList chats;
        private LongSparseArray chatsDict;
        private final ArrayList users;
        private LongSparseArray usersDict;

        public QuadroResult(ArrayList arrayList, ArrayList arrayList2) {
            this.users = arrayList;
            this.chats = arrayList2;
        }

        public Pair getDicts() {
            if (this.usersDict == null && this.chatsDict == null) {
                this.usersDict = new LongSparseArray();
                this.chatsDict = new LongSparseArray();
                ArrayList arrayList = this.users;
                int size = arrayList.size();
                int i = 0;
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    TLRPC.User user = (TLRPC.User) obj;
                    this.usersDict.put(user.f1734id, user);
                }
                ArrayList arrayList2 = this.chats;
                int size2 = arrayList2.size();
                while (i < size2) {
                    Object obj2 = arrayList2.get(i);
                    i++;
                    TLRPC.Chat chat = (TLRPC.Chat) obj2;
                    this.chatsDict.put(chat.f1571id, chat);
                }
            }
            return new Pair(this.usersDict, this.chatsDict);
        }

        public ArrayList getUsers() {
            return this.users;
        }

        public ArrayList getChats() {
            return this.chats;
        }
    }
}
