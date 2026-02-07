package org.telegram.messenger.voip;

import android.util.LongSparseArray;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BaseController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.json.TLJsonBuilder;
import org.telegram.tgnet.p022tl.TL_phone;

/* loaded from: classes.dex */
public class GroupCallMessagesController extends BaseController {
    private static volatile GroupCallMessagesController[] Instance = new GroupCallMessagesController[16];
    private final LongSparseArray<MessagesList> callMessagesList;
    private final LongSparseArray<List<CallMessageListener>> callMessagesListeners;

    public interface CallMessageListener {
        void onNewGroupCallMessage(long j, GroupCallMessage groupCallMessage);

        void onPopGroupCallMessage();
    }

    private static native byte[] groupCallMessageDecryptImpl(long j, long j2, byte[] bArr);

    private static native byte[] groupCallMessageEncryptImpl(long j, byte[] bArr);

    public void processUpdate(TLRPC.TL_updateGroupCallMessage tL_updateGroupCallMessage) {
        final long j = tL_updateGroupCallMessage.call.f1593id;
        long peerDialogId = DialogObject.getPeerDialogId(tL_updateGroupCallMessage.message.from_id);
        long j2 = tL_updateGroupCallMessage.message.f1587id;
        if (getUserConfig().clientUserId == peerDialogId) {
            return;
        }
        final GroupCallMessage groupCallMessage = new GroupCallMessage(this.currentAccount, peerDialogId, j2, tL_updateGroupCallMessage.message.message);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.voip.GroupCallMessagesController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processUpdate$0(j, groupCallMessage);
            }
        });
    }

    public void processUpdate(TLRPC.TL_updateGroupCallEncryptedMessage tL_updateGroupCallEncryptedMessage) {
        final long j = tL_updateGroupCallEncryptedMessage.call.f1593id;
        final long peerDialogId = DialogObject.getPeerDialogId(tL_updateGroupCallEncryptedMessage.from_id);
        final byte[] bArr = tL_updateGroupCallEncryptedMessage.encrypted_message;
        if (getUserConfig().clientUserId == peerDialogId) {
            return;
        }
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.voip.GroupCallMessagesController$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processUpdate$3(j, peerDialogId, bArr);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$processUpdate$3(long r10, long r12, byte[] r14) {
        /*
            r9 = this;
            r1 = 0
            byte[] r14 = r9.groupCallMessageDecrypt(r10, r12, r14)     // Catch: java.lang.Exception -> L21
            r4 = r12
            r11 = r10
            r10 = r9
            if (r14 == 0) goto L29
            java.lang.String r13 = new java.lang.String     // Catch: java.lang.Exception -> L1e
            r13.<init>(r14)     // Catch: java.lang.Exception -> L1e
            org.json.JSONObject r14 = new org.json.JSONObject     // Catch: java.lang.Exception -> L1e
            r14.<init>(r13)     // Catch: java.lang.Exception -> L1e
            org.telegram.tgnet.json.TLJsonParser r13 = new org.telegram.tgnet.json.TLJsonParser     // Catch: java.lang.Exception -> L1e
            r13.<init>(r14)     // Catch: java.lang.Exception -> L1e
            org.telegram.tgnet.TLRPC$TL_groupCallMessage r1 = org.telegram.tgnet.TLRPC.TL_groupCallMessage.TLJsonDeserialize(r13)     // Catch: java.lang.Exception -> L1e
            goto L29
        L1e:
            r0 = move-exception
        L1f:
            r13 = r0
            goto L26
        L21:
            r0 = move-exception
            r4 = r12
            r11 = r10
            r10 = r9
            goto L1f
        L26:
            org.telegram.messenger.FileLog.m1160e(r13)
        L29:
            if (r1 == 0) goto L3f
            org.telegram.messenger.voip.GroupCallMessage r2 = new org.telegram.messenger.voip.GroupCallMessage
            int r3 = r10.currentAccount
            long r6 = r1.random_id
            org.telegram.tgnet.TLRPC$TL_textWithEntities r8 = r1.message
            r2.<init>(r3, r4, r6, r8)
            org.telegram.messenger.voip.GroupCallMessagesController$$ExternalSyntheticLambda3 r13 = new org.telegram.messenger.voip.GroupCallMessagesController$$ExternalSyntheticLambda3
            r13.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r13)
            goto L66
        L3f:
            org.telegram.tgnet.TLRPC$TL_groupCallMessage r13 = new org.telegram.tgnet.TLRPC$TL_groupCallMessage
            r13.<init>()
            org.telegram.tgnet.TLRPC$TL_textWithEntities r14 = new org.telegram.tgnet.TLRPC$TL_textWithEntities
            r14.<init>()
            r13.message = r14
            int r0 = org.telegram.messenger.C2369R.string.GroupCalMessageDecryptionError
            java.lang.String r0 = org.telegram.messenger.LocaleController.getString(r0)
            r14.text = r0
            org.telegram.messenger.voip.GroupCallMessage r2 = new org.telegram.messenger.voip.GroupCallMessage
            int r3 = r10.currentAccount
            r6 = 0
            org.telegram.tgnet.TLRPC$TL_textWithEntities r8 = r13.message
            r2.<init>(r3, r4, r6, r8)
            org.telegram.messenger.voip.GroupCallMessagesController$$ExternalSyntheticLambda4 r13 = new org.telegram.messenger.voip.GroupCallMessagesController$$ExternalSyntheticLambda4
            r13.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r13)
        L66:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.voip.GroupCallMessagesController.lambda$processUpdate$3(long, long, byte[]):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean sendCallMessage(long j, TLRPC.TL_textWithEntities tL_textWithEntities, long j2, TLRPC.InputGroupCall inputGroupCall) {
        TL_phone.sendGroupCallMessage sendgroupcallmessage;
        TLRPC.GroupCall groupCall;
        byte[] bArrGroupCallMessageEncryptImpl;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null || sharedInstance.getAccount() != this.currentAccount) {
            return false;
        }
        long nextRandomId = getSendMessagesHelper().getNextRandomId();
        if (sharedInstance.isConference()) {
            ConferenceCall conferenceCall = sharedInstance.conference;
            if (conferenceCall == null || (groupCall = conferenceCall.groupCall) == null || groupCall.f1586id != j2) {
                return false;
            }
            long callId = conferenceCall.getCallId();
            if (callId == -1) {
                return false;
            }
            TLRPC.TL_groupCallMessage tL_groupCallMessage = new TLRPC.TL_groupCallMessage();
            tL_groupCallMessage.message = tL_textWithEntities;
            tL_groupCallMessage.random_id = nextRandomId;
            JSONObject jSONObjectSerialize = TLJsonBuilder.serialize(tL_groupCallMessage);
            if (jSONObjectSerialize == null || (bArrGroupCallMessageEncryptImpl = groupCallMessageEncryptImpl(callId, jSONObjectSerialize.toString().getBytes(StandardCharsets.UTF_8))) == null) {
                return false;
            }
            TL_phone.sendGroupCallEncryptedMessage sendgroupcallencryptedmessage = new TL_phone.sendGroupCallEncryptedMessage();
            sendgroupcallencryptedmessage.call = inputGroupCall;
            sendgroupcallencryptedmessage.encrypted_message = bArrGroupCallMessageEncryptImpl;
            sendgroupcallmessage = sendgroupcallencryptedmessage;
        } else {
            TL_phone.sendGroupCallMessage sendgroupcallmessage2 = new TL_phone.sendGroupCallMessage();
            sendgroupcallmessage2.call = inputGroupCall;
            sendgroupcallmessage2.message = tL_textWithEntities;
            sendgroupcallmessage2.random_id = nextRandomId;
            sendgroupcallmessage = sendgroupcallmessage2;
        }
        TL_phone.sendGroupCallMessage sendgroupcallmessage3 = sendgroupcallmessage;
        final GroupCallMessage groupCallMessage = new GroupCallMessage(this.currentAccount, j, nextRandomId, tL_textWithEntities);
        groupCallMessage.setIsOut(true);
        lambda$processUpdate$2(j2, groupCallMessage);
        final Runnable runnable = new Runnable() { // from class: org.telegram.messenger.voip.GroupCallMessagesController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                GroupCallMessagesController.m4297$r8$lambda$BoiMzYkL9iUych8eS3PH9Yfbqk(groupCallMessage);
            }
        };
        AndroidUtilities.runOnUIThread(runnable, 1000L);
        getConnectionsManager().sendRequest(sendgroupcallmessage3, new RequestDelegate() { // from class: org.telegram.messenger.voip.GroupCallMessagesController$$ExternalSyntheticLambda7
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$sendCallMessage$5(runnable, groupCallMessage, tLObject, tL_error);
            }
        });
        return true;
    }

    /* renamed from: $r8$lambda$BoiMzYkL9iUych8eS3-PH9Yfbqk, reason: not valid java name */
    public static /* synthetic */ void m4297$r8$lambda$BoiMzYkL9iUych8eS3PH9Yfbqk(GroupCallMessage groupCallMessage) {
        groupCallMessage.setIsSendDelayed(true);
        groupCallMessage.notifyStateUpdate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCallMessage$5(Runnable runnable, final GroupCallMessage groupCallMessage, TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.cancelRunOnUIThread(runnable);
        groupCallMessage.setIsSendDelayed(false);
        if (tLObject instanceof TLRPC.Bool) {
            if (tLObject instanceof TLRPC.TL_boolTrue) {
                groupCallMessage.setIsSendConfirmed(true);
            } else {
                groupCallMessage.setIsSendError(true);
            }
        } else if (tLObject instanceof TLRPC.Updates) {
            groupCallMessage.setIsSendConfirmed(true);
            getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.voip.GroupCallMessagesController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                groupCallMessage.notifyStateUpdate();
            }
        });
    }

    public List<GroupCallMessage> getCallMessages(long j) {
        MessagesList messagesList = this.callMessagesList.get(j);
        return messagesList != null ? new ArrayList(messagesList.messages) : new ArrayList();
    }

    public void subscribeToCallMessages(long j, CallMessageListener callMessageListener) {
        List<CallMessageListener> arrayList = this.callMessagesListeners.get(j);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
            this.callMessagesListeners.put(j, arrayList);
        }
        arrayList.add(callMessageListener);
    }

    public void unsubscribeFromCallMessages(long j, CallMessageListener callMessageListener) {
        List<CallMessageListener> list = this.callMessagesListeners.get(j);
        if (list == null) {
            return;
        }
        list.remove(callMessageListener);
        if (list.isEmpty()) {
            this.callMessagesListeners.remove(j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: pushMessageToList, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$processUpdate$2(final long j, GroupCallMessage groupCallMessage) {
        MessagesList messagesList = this.callMessagesList.get(j);
        if (messagesList == null) {
            messagesList = new MessagesList();
            this.callMessagesList.put(j, messagesList);
        }
        if (messagesList.push(groupCallMessage)) {
            List<CallMessageListener> list = this.callMessagesListeners.get(j);
            if (list != null) {
                Iterator<CallMessageListener> it = list.iterator();
                while (it.hasNext()) {
                    it.next().onNewGroupCallMessage(j, groupCallMessage);
                }
            }
            List<CallMessageListener> list2 = this.callMessagesListeners.get(0L);
            if (list2 != null) {
                Iterator<CallMessageListener> it2 = list2.iterator();
                while (it2.hasNext()) {
                    it2.next().onNewGroupCallMessage(j, groupCallMessage);
                }
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.voip.GroupCallMessagesController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$pushMessageToList$6(j);
                }
            }, getAppGlobalConfig().groupCallMessageTtl.get(TimeUnit.MILLISECONDS));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: popMessageFromList, reason: merged with bridge method [inline-methods] */
    public void lambda$pushMessageToList$6(long j) {
        MessagesList messagesList = this.callMessagesList.get(j);
        if (messagesList == null) {
            return;
        }
        messagesList.pop();
        if (messagesList.isEmpty()) {
            this.callMessagesList.remove(j);
        }
        List<CallMessageListener> list = this.callMessagesListeners.get(j);
        if (list != null) {
            Iterator<CallMessageListener> it = list.iterator();
            while (it.hasNext()) {
                it.next().onPopGroupCallMessage();
            }
        }
        List<CallMessageListener> list2 = this.callMessagesListeners.get(0L);
        if (list2 != null) {
            Iterator<CallMessageListener> it2 = list2.iterator();
            while (it2.hasNext()) {
                it2.next().onPopGroupCallMessage();
            }
        }
    }

    /* loaded from: classes5.dex */
    private static class MessagesList {
        private final List<GroupCallMessage> messages;
        private final Set<Long> randomIds;

        private MessagesList() {
            this.messages = new ArrayList();
            this.randomIds = new HashSet();
        }

        boolean push(GroupCallMessage groupCallMessage) {
            long j = groupCallMessage.randomId;
            if (j != 0 && !this.randomIds.add(Long.valueOf(j))) {
                return false;
            }
            this.messages.add(0, groupCallMessage);
            return true;
        }

        void pop() {
            if (this.messages.isEmpty()) {
                return;
            }
            long j = this.messages.remove(r0.size() - 1).randomId;
            if (j != 0) {
                this.randomIds.remove(Long.valueOf(j));
            }
        }

        boolean isEmpty() {
            return this.messages.isEmpty();
        }
    }

    private byte[] groupCallMessageDecrypt(long j, long j2, byte[] bArr) {
        ConferenceCall conferenceCall;
        TLRPC.GroupCall groupCall;
        VoIPService sharedInstance = VoIPService.getSharedInstance();
        if (sharedInstance == null || sharedInstance.getAccount() != this.currentAccount || (conferenceCall = sharedInstance.conference) == null || (groupCall = conferenceCall.groupCall) == null || groupCall.f1586id != j) {
            return null;
        }
        long callId = conferenceCall.getCallId();
        if (callId == -1) {
            return null;
        }
        return groupCallMessageDecryptImpl(callId, j2, bArr);
    }

    public static GroupCallMessagesController getInstance(int i) {
        GroupCallMessagesController groupCallMessagesController;
        GroupCallMessagesController groupCallMessagesController2 = Instance[i];
        if (groupCallMessagesController2 != null) {
            return groupCallMessagesController2;
        }
        synchronized (GroupCallMessagesController.class) {
            try {
                groupCallMessagesController = Instance[i];
                if (groupCallMessagesController == null) {
                    GroupCallMessagesController[] groupCallMessagesControllerArr = Instance;
                    GroupCallMessagesController groupCallMessagesController3 = new GroupCallMessagesController(i);
                    groupCallMessagesControllerArr[i] = groupCallMessagesController3;
                    groupCallMessagesController = groupCallMessagesController3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return groupCallMessagesController;
    }

    private GroupCallMessagesController(int i) {
        super(i);
        this.callMessagesListeners = new LongSparseArray<>();
        this.callMessagesList = new LongSparseArray<>();
    }
}
