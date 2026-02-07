package org.telegram.messenger.voip;

import android.text.TextUtils;
import android.util.LongSparseArray;
import androidx.annotation.Keep;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.voip.ConferenceCall;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_phone;
import p017j$.util.DesugarArrays;
import p017j$.util.function.Function$CC;
import p017j$.util.stream.Collectors;

@Keep
/* loaded from: classes5.dex */
public class ConferenceCall {
    public static final int PERMISSION_ADD = 1;
    public static final int PERMISSION_REMOVE = 2;
    private int currentAccount;
    public boolean destroyed;
    public TLRPC.GroupCall groupCall;
    public TLRPC.InputGroupCall inputGroupCall;
    public boolean joined;
    private String[] lastVerificationEmojis;
    private byte[] last_block;
    private long my_private_key_id;
    private byte[] my_public_key;
    private long my_public_key_id;
    private long my_user_id;
    private boolean polling;
    private CallState state;
    private byte[] zero_block;
    private long call_id = -1;
    public final HashSet<Long> joiningBlockchainParticipants = new HashSet<>();
    private HashSet<Long> lastParticipants = null;
    private final int[] last_offset = {-1, -1};
    private final LongSparseArray<byte[]>[] blocksQueue = {new LongSparseArray<>(), new LongSparseArray<>()};
    private final Runnable pollRunnable = new Runnable() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda9
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.poll();
        }
    };
    private final int[] pollRequestId = new int[2];

    public static native CallState call_apply_block(long j, byte[] bArr);

    public static native long call_create(long j, long j2, byte[] bArr);

    public static native byte[] call_create_change_state_block(long j, CallState callState);

    public static native byte[] call_create_self_add_block(long j, byte[] bArr, CallParticipant callParticipant);

    public static native byte[] call_create_zero_block(long j, CallState callState);

    public static native String call_describe(long j);

    public static native String call_describe_block(byte[] bArr);

    public static native String call_describe_message(byte[] bArr);

    public static native void call_destroy(long j);

    public static native void call_destroy_all();

    public static native int call_get_height(long j);

    public static native CallState call_get_state(long j);

    public static native CallVerificationState call_get_verification_state(long j);

    public static native CallVerificationWords call_get_verification_words(long j);

    public static native byte[][] call_pull_outbound_messages(long j);

    public static native CallVerificationState call_receive_inbound_message(long j, byte[] bArr);

    public static native long key_from_public_key(byte[] bArr);

    public static native long key_generate_temporary_private_key();

    public static native byte[] key_to_public_key(long j);

    protected void gotCallId(long j) {
    }

    @Keep
    public static final class CallParticipant {
        int permissions;
        long public_key_id;
        long user_id;

        public String toString() {
            return "CallParticipant{user_id=" + this.user_id + ", public_key_id=" + this.public_key_id + "}";
        }
    }

    @Keep
    public static final class CallState {
        int height;
        CallParticipant[] participants;

        public CallParticipant find(long j) {
            int i = 0;
            while (true) {
                CallParticipant[] callParticipantArr = this.participants;
                if (i >= callParticipantArr.length) {
                    return null;
                }
                CallParticipant callParticipant = callParticipantArr[i];
                if (callParticipant.user_id == j) {
                    return callParticipant;
                }
                i++;
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("CallState{height=" + this.height + ", participants=[");
            for (int i = 0; i < this.participants.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                CallParticipant callParticipant = this.participants[i];
                if (callParticipant == null) {
                    sb.append("null");
                } else {
                    sb.append(callParticipant.toString());
                }
            }
            sb.append("]}");
            return sb.toString();
        }
    }

    private static String blockStr(byte[] bArr) {
        return "Block{" + Utilities.bytesToHex(bArr) + "}";
    }

    @Keep
    public static final class CallVerificationState {
        byte[] emoji_hash;
        int height;

        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append("CallVerificationState{height=");
            sb.append(this.height);
            sb.append(", emoji_hash=");
            if (this.emoji_hash == null) {
                str = null;
            } else {
                str = "{" + Utilities.bytesToHex(this.emoji_hash) + "}";
            }
            sb.append(str);
            sb.append("}");
            return sb.toString();
        }
    }

    @Keep
    public static final class CallVerificationWords {
        int height;
        String[] words;

        public String toString() {
            StringBuilder sb = new StringBuilder("CallVerificationWords{height=" + this.height + ", words=[");
            for (int i = 0; i < this.words.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.words[i]);
            }
            sb.append("]}");
            return sb.toString();
        }
    }

    private void checkEmojiHash() {
        String[] verificationEmojis = getVerificationEmojis();
        if (m1216eq(verificationEmojis, this.lastVerificationEmojis)) {
            return;
        }
        this.lastVerificationEmojis = verificationEmojis;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkEmojiHash$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkEmojiHash$0() {
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.conferenceEmojiUpdated, new Object[0]);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:54:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void checkParticipants() {
        /*
            r6 = this;
            long r0 = r6.call_id
            r2 = 0
            r4 = 0
            int r5 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r5 < 0) goto L36
            org.telegram.messenger.voip.ConferenceCall$CallState r0 = call_get_state(r0)     // Catch: java.lang.Exception -> L32
            if (r0 == 0) goto L36
            org.telegram.messenger.voip.ConferenceCall$CallParticipant[] r1 = r0.participants     // Catch: java.lang.Exception -> L32
            int r1 = r1.length     // Catch: java.lang.Exception -> L32
            if (r1 <= 0) goto L36
            java.util.HashSet r1 = new java.util.HashSet     // Catch: java.lang.Exception -> L32
            r1.<init>()     // Catch: java.lang.Exception -> L32
            r2 = 0
        L1a:
            org.telegram.messenger.voip.ConferenceCall$CallParticipant[] r3 = r0.participants     // Catch: java.lang.Exception -> L2d
            int r4 = r3.length     // Catch: java.lang.Exception -> L2d
            if (r2 >= r4) goto L30
            r3 = r3[r2]     // Catch: java.lang.Exception -> L2d
            long r3 = r3.user_id     // Catch: java.lang.Exception -> L2d
            java.lang.Long r3 = java.lang.Long.valueOf(r3)     // Catch: java.lang.Exception -> L2d
            r1.add(r3)     // Catch: java.lang.Exception -> L2d
            int r2 = r2 + 1
            goto L1a
        L2d:
            r0 = move-exception
            r4 = r1
            goto L33
        L30:
            r4 = r1
            goto L36
        L32:
            r0 = move-exception
        L33:
            org.telegram.messenger.FileLog.m1160e(r0)
        L36:
            java.util.HashSet<java.lang.Long> r0 = r6.lastParticipants
            boolean r0 = r6.m1215eq(r4, r0)
            if (r0 != 0) goto L95
            java.util.HashSet<java.lang.Long> r0 = r6.lastParticipants
            if (r0 == 0) goto L86
            if (r4 == 0) goto L86
            java.util.Iterator r0 = r4.iterator()
        L48:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L65
            java.lang.Object r1 = r0.next()
            java.lang.Long r1 = (java.lang.Long) r1
            r1.longValue()
            java.util.HashSet<java.lang.Long> r2 = r6.lastParticipants
            boolean r2 = r2.contains(r1)
            if (r2 != 0) goto L48
            java.util.HashSet<java.lang.Long> r2 = r6.joiningBlockchainParticipants
            r2.add(r1)
            goto L48
        L65:
            java.util.HashSet<java.lang.Long> r0 = r6.joiningBlockchainParticipants
            java.util.Iterator r0 = r0.iterator()
        L6b:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L8b
            java.lang.Object r1 = r0.next()
            java.lang.Long r1 = (java.lang.Long) r1
            r1.longValue()
            java.util.HashSet<java.lang.Long> r2 = r6.lastParticipants
            boolean r1 = r2.contains(r1)
            if (r1 == 0) goto L6b
            r0.remove()
            goto L6b
        L86:
            java.util.HashSet<java.lang.Long> r0 = r6.joiningBlockchainParticipants
            r0.clear()
        L8b:
            r6.lastParticipants = r4
            org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda12 r0 = new org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda12
            r0.<init>()
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r0)
        L95:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.voip.ConferenceCall.checkParticipants():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkParticipants$1() {
        VoIPService sharedInstance;
        ChatObject.Call call;
        TLRPC.GroupCall groupCall;
        if (this.groupCall == null || (sharedInstance = VoIPService.getSharedInstance()) == null || (call = sharedInstance.groupCall) == null || (groupCall = call.call) == null || groupCall.f1586id != this.groupCall.f1586id) {
            return;
        }
        updateParticipants(call.sortedParticipants, false);
        sharedInstance.groupCall.shadyLeftParticipants.clear();
        ChatObject.Call call2 = sharedInstance.groupCall;
        call2.shadyLeftParticipants.addAll(sharedInstance.conference.getShadyLeftParticipants(call2.sortedParticipants));
        sharedInstance.groupCall.shadyJoinParticipants.clear();
        ChatObject.Call call3 = sharedInstance.groupCall;
        call3.shadyJoinParticipants.addAll(sharedInstance.conference.getShadyJoiningParticipants(call3.sortedParticipants));
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.groupCallUpdated, 0L, Long.valueOf(this.groupCall.f1586id), Boolean.FALSE);
    }

    /* renamed from: eq */
    private boolean m1216eq(String[] strArr, String[] strArr2) {
        if (strArr == strArr2) {
            return true;
        }
        if (strArr == null && strArr2 == null) {
            return true;
        }
        if (strArr == null || strArr2 == null || strArr.length != strArr2.length) {
            return false;
        }
        for (int i = 0; i < strArr.length; i++) {
            if (!TextUtils.equals(strArr[i], strArr2[i])) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: eq */
    private boolean m1215eq(HashSet<Long> hashSet, HashSet<Long> hashSet2) {
        if (hashSet == hashSet2) {
            return true;
        }
        if (hashSet == null && hashSet2 == null) {
            return true;
        }
        if (hashSet == null || hashSet2 == null || hashSet.size() != hashSet2.size()) {
            return false;
        }
        Iterator<Long> it = hashSet.iterator();
        while (it.hasNext()) {
            Long next = it.next();
            next.longValue();
            if (!hashSet2.contains(next)) {
                return false;
            }
        }
        return true;
    }

    private String[] getVerificationEmojis() {
        byte[] bArr;
        long j = this.call_id;
        if (j < 0) {
            return null;
        }
        try {
            bArr = call_get_verification_state(j).emoji_hash;
        } catch (Exception e) {
            FileLog.m1160e(e);
            bArr = null;
        }
        if (bArr == null) {
            return null;
        }
        if (bArr.length > 32) {
            byte[] bArr2 = new byte[32];
            System.arraycopy(bArr, 0, bArr2, 0, 32);
            bArr = bArr2;
        }
        return EncryptionKeyEmojifier.emojifyForCall(bArr);
    }

    public String[] getEmojis() {
        return this.lastVerificationEmojis;
    }

    public ConferenceCall(int i, long j) {
        this.currentAccount = i;
        this.my_user_id = j;
        init();
    }

    private void init() {
        long jKey_generate_temporary_private_key = key_generate_temporary_private_key();
        this.my_private_key_id = jKey_generate_temporary_private_key;
        byte[] bArrKey_to_public_key = key_to_public_key(jKey_generate_temporary_private_key);
        this.my_public_key = bArrKey_to_public_key;
        this.my_public_key_id = key_from_public_key(bArrKey_to_public_key);
        CallState callState = new CallState();
        this.state = callState;
        callState.height = 1;
        callState.participants = new CallParticipant[]{new CallParticipant()};
        CallParticipant callParticipant = this.state.participants[0];
        callParticipant.user_id = this.my_user_id;
        callParticipant.public_key_id = this.my_public_key_id;
        callParticipant.permissions = 3;
    }

    public byte[] getMyPublicKey() {
        return this.my_public_key;
    }

    public void requestLastBlock(final Runnable runnable) {
        final long jCurrentTimeMillis = System.currentTimeMillis();
        TL_phone.getGroupCallChainBlocks getgroupcallchainblocks = new TL_phone.getGroupCallChainBlocks();
        getgroupcallchainblocks.call = this.inputGroupCall;
        getgroupcallchainblocks.sub_chain_id = 0;
        getgroupcallchainblocks.offset = -1;
        getgroupcallchainblocks.limit = 1;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(getgroupcallchainblocks, new RequestDelegate() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda6
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$requestLastBlock$3(jCurrentTimeMillis, runnable, tLObject, tL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestLastBlock$3(final long j, final Runnable runnable, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$requestLastBlock$2(j, tLObject, tL_error, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$requestLastBlock$2(long j, TLObject tLObject, TLRPC.TL_error tL_error, Runnable runnable) {
        processUpdates(-1, Long.valueOf(j), tLObject, tL_error);
        if (runnable != null) {
            runnable.run();
        }
    }

    private boolean processUpdates(Integer num, Long l, final TLObject tLObject, TLRPC.TL_error tL_error) {
        if (!(tLObject instanceof TLRPC.Updates)) {
            return false;
        }
        ArrayList arrayListFindUpdatesAndRemove = MessagesController.findUpdatesAndRemove((TLRPC.Updates) tLObject, TLRPC.TL_updateGroupCallChainBlocks.class);
        int size = arrayListFindUpdatesAndRemove.size();
        boolean z = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayListFindUpdatesAndRemove.get(i);
            i++;
            if (applyUpdate(num, (TLRPC.TL_updateGroupCallChainBlocks) obj, false, l)) {
                z = true;
            }
        }
        Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$processUpdates$4(tLObject);
            }
        });
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$processUpdates$4(TLObject tLObject) {
        MessagesController.getInstance(this.currentAccount).processUpdates((TLRPC.Updates) tLObject, false);
    }

    public byte[] getLastBlock() {
        return this.last_block;
    }

    public byte[] generateAddSelfBlock() {
        CallParticipant callParticipant = new CallParticipant();
        callParticipant.user_id = this.my_user_id;
        callParticipant.public_key_id = this.my_public_key_id;
        callParticipant.permissions = 3;
        byte[] bArr = this.last_block;
        if (bArr == null) {
            CallState callState = new CallState();
            this.state = callState;
            callState.height = 1;
            callState.participants = new CallParticipant[]{callParticipant};
            byte[] bArrCall_create_zero_block = call_create_zero_block(this.my_private_key_id, callState);
            this.zero_block = bArrCall_create_zero_block;
            this.last_block = bArrCall_create_zero_block;
            FileLog.m1157d("[tde2e] call_create_zero_block(" + this.my_private_key_id + ", " + this.state + ")");
        } else {
            try {
                byte[] bArrCall_create_self_add_block = call_create_self_add_block(this.my_private_key_id, bArr, callParticipant);
                FileLog.m1157d("[tde2e] call_create_self_add_block(" + this.my_private_key_id + ", " + blockStr(this.last_block) + ", " + this.state + ") = " + blockStr(bArrCall_create_self_add_block));
                StringBuilder sb = new StringBuilder();
                sb.append("[tde2e] call_create_self_add_block last_block=");
                sb.append(call_describe_block(this.last_block));
                sb.append(" new_block=");
                sb.append(call_describe_block(bArrCall_create_self_add_block));
                FileLog.m1157d(sb.toString());
                this.last_block = bArrCall_create_self_add_block;
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
        return this.last_block;
    }

    public CallVerificationWords getVerificationWords() {
        return call_get_verification_words(this.call_id);
    }

    public CallVerificationState getVerificationState() {
        return call_get_verification_state(this.call_id);
    }

    private void readQueue(int i) {
        if (this.destroyed) {
            FileLog.m1157d("[tde2e] conference.readQueue(" + i + ") but destroyed!");
            return;
        }
        if (i != 0 && this.call_id < 0) {
            FileLog.m1157d("[tde2e] conference.readQueue(" + i + ") but there is no call yet!");
            return;
        }
        int i2 = this.last_offset[i];
        if (i2 == -1) {
            FileLog.m1157d("[tde2e] conference.readQueue(" + i + ") but last_offset == -1!");
            return;
        }
        int iMax = Math.max(0, i2);
        FileLog.m1157d("[tde2e] {subchain: " + i + "} processing blocks queue from " + iMax);
        while (true) {
            long j = iMax;
            byte[] bArr = this.blocksQueue[i].get(j);
            if (bArr == null) {
                FileLog.m1157d("[tde2e] {subchain: " + i + "} got into hole (might be the end) in " + i + " subchain at #" + iMax + ", when our last_offset[" + i + "] = " + this.last_offset[i]);
                this.last_offset[i] = iMax;
                return;
            }
            try {
                FileLog.m1157d("[tde2e] {subchain: " + i + "} processing #" + iMax + " block from queue");
                this.blocksQueue[i].remove(j);
                long j2 = this.call_id;
                if (j2 < 0) {
                    FileLog.m1157d("[tde2e] #" + iMax + " call_create block=" + call_describe_block(bArr));
                    long j3 = this.my_user_id;
                    long j4 = this.my_private_key_id;
                    this.last_block = bArr;
                    long jCall_create = call_create(j3, j4, bArr);
                    this.call_id = jCall_create;
                    gotCallId(jCall_create);
                } else if (i == 0) {
                    if (iMax > call_get_height(j2)) {
                        FileLog.m1157d("[tde2e] #" + iMax + " call_apply_block block=" + call_describe_block(bArr));
                        FileLog.m1157d("[tde2e] #" + iMax + " call_apply_block(" + this.call_id + ", " + blockStr(bArr) + ") = " + call_apply_block(this.call_id, bArr));
                    } else {
                        FileLog.m1157d("[tde2e] #" + iMax + " block from queue is under call's height!");
                    }
                } else if (i == 1) {
                    FileLog.m1157d("[tde2e] #" + iMax + " call_receive_inbound_message message=" + call_describe_message(bArr));
                    FileLog.m1157d("[tde2e] #" + iMax + " call_receive_inbound_message(" + this.call_id + ", " + blockStr(bArr) + ") = " + call_receive_inbound_message(this.call_id, bArr));
                }
                iMax++;
                this.last_offset[i] = iMax;
            } catch (Exception e) {
                FileLog.m1159e("[tde2e] {subchain: " + i + "} #" + iMax + " block got into error: ", e);
                return;
            }
        }
    }

    public boolean applyUpdate(Integer num, TLRPC.TL_updateGroupCallChainBlocks tL_updateGroupCallChainBlocks, boolean z, Long l) {
        if (this.destroyed) {
            FileLog.m1157d("[tde2e] conference.applyUpdate but destroyed!");
            return false;
        }
        if (tL_updateGroupCallChainBlocks == null) {
            return false;
        }
        TLRPC.GroupCall groupCall = this.groupCall;
        if (groupCall == null) {
            FileLog.m1157d("[tde2e] received updateGroupCallChainBlocks but we dont have groupcall yet!");
            return false;
        }
        if (tL_updateGroupCallChainBlocks.call.f1593id != groupCall.f1586id) {
            FileLog.m1157d("[tde2e] received updateGroupCallChainBlocks for " + tL_updateGroupCallChainBlocks.call.f1593id + " but we have " + this.groupCall.f1586id);
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("[tde2e] received update with ");
        sb.append(tL_updateGroupCallChainBlocks.blocks.size());
        sb.append(" blocks for ");
        sb.append(tL_updateGroupCallChainBlocks.sub_chain_id);
        sb.append(" subchain, next_offset=");
        sb.append(tL_updateGroupCallChainBlocks.next_offset);
        sb.append(" requested_offset=");
        sb.append(num);
        sb.append(l != null ? " in " + (System.currentTimeMillis() - l.longValue()) + "ms" : "");
        FileLog.m1157d(sb.toString());
        int i = tL_updateGroupCallChainBlocks.sub_chain_id;
        int i2 = tL_updateGroupCallChainBlocks.next_offset;
        if (i == 0 || i == 1) {
            for (int i3 = 0; i3 < tL_updateGroupCallChainBlocks.blocks.size(); i3++) {
                byte[] bArr = (byte[]) tL_updateGroupCallChainBlocks.blocks.get(i3);
                int size = (i2 - tL_updateGroupCallChainBlocks.blocks.size()) + i3;
                if (num == null || num.intValue() != -1) {
                    if (size >= this.last_offset[i]) {
                        FileLog.m1157d("[tde2e] {subchain: " + i + "} put #" + size + " into queue");
                        this.blocksQueue[i].put((long) size, bArr);
                    } else {
                        FileLog.m1157d("[tde2e] {subchain: " + i + "} received #" + size + " that was already processed from queue");
                    }
                } else if (i == 0) {
                    this.last_block = bArr;
                }
            }
            if (this.last_offset[i] == -1) {
                if (num != null && num.intValue() == 0) {
                    this.last_offset[i] = i2 - tL_updateGroupCallChainBlocks.blocks.size();
                } else if (num != null && num.intValue() == -1) {
                    FileLog.m1157d("[tde2e] no offset, but we were asking for last block anyway");
                } else {
                    FileLog.m1158e("[tde2e] received update where we can't know what the start offset is of " + i + " sub chain (we requested " + num + ")");
                }
            }
            if (this.last_offset[i] != -1) {
                boolean z2 = this.call_id >= 0;
                readQueue(i);
                boolean z3 = this.call_id >= 0;
                if (i == 0 && !z2 && z3) {
                    readQueue(1);
                }
            }
        }
        if (i == 1) {
            pull_outbound();
        }
        checkEmojiHash();
        checkParticipants();
        if (z && tL_updateGroupCallChainBlocks.blocks.size() > 0) {
            forcePoll();
        }
        return tL_updateGroupCallChainBlocks.blocks.size() > 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void pull_outbound() {
        /*
            Method dump skipped, instructions count: 241
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.voip.ConferenceCall.pull_outbound():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pull_outbound$6(final long j, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$pull_outbound$5(j, tLObject, tL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pull_outbound$5(long j, TLObject tLObject, TLRPC.TL_error tL_error) {
        processUpdates(null, Long.valueOf(j), tLObject, tL_error);
    }

    private long getPollTimeout() {
        return getVerificationEmojis() == null ? 1000L : 5000L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void poll() {
        if (this.destroyed) {
            FileLog.m1157d("[tde2e] conference.poll but destroyed!");
            return;
        }
        if (!this.joined) {
            FileLog.m1157d("[tde2e] conference.poll but not joined!");
            return;
        }
        this.polling = true;
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        for (int i = 0; i < 2; i++) {
            final TL_phone.getGroupCallChainBlocks getgroupcallchainblocks = new TL_phone.getGroupCallChainBlocks();
            getgroupcallchainblocks.call = this.inputGroupCall;
            getgroupcallchainblocks.sub_chain_id = i;
            getgroupcallchainblocks.offset = Math.max(0, this.last_offset[i]);
            getgroupcallchainblocks.limit = 10;
            FileLog.m1157d("[tde2e] requesting getGroupCallChainBlocks sub_chain_id=" + getgroupcallchainblocks.sub_chain_id + " offset=" + getgroupcallchainblocks.offset + " limit=10");
            final long jCurrentTimeMillis = System.currentTimeMillis();
            this.pollRequestId[i] = ConnectionsManager.getInstance(this.currentAccount).sendRequest(getgroupcallchainblocks, new RequestDelegate() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda13
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$poll$8(getgroupcallchainblocks, jCurrentTimeMillis, atomicBoolean, atomicInteger, tLObject, tL_error);
                }
            });
        }
        if (this.call_id >= 0) {
            try {
                FileLog.m1157d("[tde2e] state = " + call_get_verification_state(this.call_id));
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
            try {
                FileLog.m1157d("[tde2e] call_describe(" + this.call_id + "): " + call_describe(this.call_id));
                StringBuilder sb = new StringBuilder();
                sb.append("[tde2e] call users:\n ");
                sb.append(TextUtils.join("\n ", (Iterable) DesugarArrays.stream(call_get_state(this.call_id).participants).map(new Function() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda14
                    public /* synthetic */ Function andThen(Function function) {
                        return Function$CC.$default$andThen(this, function);
                    }

                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ConferenceCall.m4294$r8$lambda$l2DLYo9iQuRY1Qymj89k3Qq7zM((ConferenceCall.CallParticipant) obj);
                    }

                    public /* synthetic */ Function compose(Function function) {
                        return Function$CC.$default$compose(this, function);
                    }
                }).collect(Collectors.toSet())));
                FileLog.m1157d(sb.toString());
            } catch (Exception e2) {
                FileLog.m1160e(e2);
            }
        }
        checkEmojiHash();
        checkParticipants();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$poll$8(final TL_phone.getGroupCallChainBlocks getgroupcallchainblocks, final long j, final AtomicBoolean atomicBoolean, final AtomicInteger atomicInteger, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$poll$7(getgroupcallchainblocks, j, tLObject, tL_error, atomicBoolean, atomicInteger);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$poll$7(TL_phone.getGroupCallChainBlocks getgroupcallchainblocks, long j, TLObject tLObject, TLRPC.TL_error tL_error, AtomicBoolean atomicBoolean, AtomicInteger atomicInteger) {
        if (processUpdates(Integer.valueOf(getgroupcallchainblocks.offset), Long.valueOf(j), tLObject, tL_error)) {
            atomicBoolean.set(true);
        }
        if (atomicInteger.incrementAndGet() == 2) {
            this.polling = false;
            if (atomicBoolean.get()) {
                forcePoll();
            } else {
                AndroidUtilities.cancelRunOnUIThread(this.pollRunnable);
                AndroidUtilities.runOnUIThread(this.pollRunnable, getPollTimeout());
            }
        }
    }

    /* renamed from: $r8$lambda$l2DLYo9iQuRY1Qymj89-k3Qq7zM, reason: not valid java name */
    public static /* synthetic */ String m4294$r8$lambda$l2DLYo9iQuRY1Qymj89k3Qq7zM(CallParticipant callParticipant) {
        return "[" + callParticipant.user_id + "]: " + DialogObject.getName(callParticipant.user_id);
    }

    public void forcePoll() {
        if (this.destroyed) {
            FileLog.m1157d("[tde2e] conference.forcePoll but destroyed!");
            return;
        }
        if (!this.joined) {
            FileLog.m1157d("[tde2e] conference.forcePoll but not joined!");
        } else {
            if (this.polling) {
                return;
            }
            AndroidUtilities.cancelRunOnUIThread(this.pollRunnable);
            AndroidUtilities.runOnUIThread(this.pollRunnable);
        }
    }

    public void updateParticipants(ArrayList<TLRPC.GroupCallParticipant> arrayList, boolean z) {
        if (this.destroyed) {
            FileLog.m1157d("[tde2e] conference.updateParticipants but destroyed!");
            return;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            TLRPC.GroupCallParticipant groupCallParticipant = arrayList.get(i);
            i++;
            this.joiningBlockchainParticipants.remove(Long.valueOf(DialogObject.getPeerDialogId(groupCallParticipant.peer)));
        }
        HashSet<Long> shadyLeftParticipants = getShadyLeftParticipants(arrayList);
        if (!shadyLeftParticipants.isEmpty()) {
            try {
                CallState callStateCall_get_state = call_get_state(this.call_id);
                CallState callState = new CallState();
                callState.height = callStateCall_get_state.height + 1;
                ArrayList arrayList2 = new ArrayList();
                int i2 = 0;
                while (true) {
                    CallParticipant[] callParticipantArr = callStateCall_get_state.participants;
                    if (i2 >= callParticipantArr.length) {
                        break;
                    }
                    if (!shadyLeftParticipants.contains(Long.valueOf(callParticipantArr[i2].user_id))) {
                        CallParticipant callParticipant = new CallParticipant();
                        CallParticipant callParticipant2 = callStateCall_get_state.participants[i2];
                        callParticipant.user_id = callParticipant2.user_id;
                        callParticipant.public_key_id = callParticipant2.public_key_id;
                        callParticipant.permissions = callParticipant2.permissions;
                        arrayList2.add(callParticipant);
                    }
                    i2++;
                }
                callState.participants = (CallParticipant[]) arrayList2.toArray(new CallParticipant[0]);
                FileLog.m1157d("[tde2e] call_create_change_state_block from " + this.state + " to " + callState);
                byte[] bArrCall_create_change_state_block = call_create_change_state_block(this.call_id, callState);
                StringBuilder sb = new StringBuilder();
                sb.append("[tde2e] call_create_change_state_block returns ");
                sb.append(call_describe_block(bArrCall_create_change_state_block));
                FileLog.m1157d(sb.toString());
                this.state = callState;
                TL_phone.deleteConferenceCallParticipants deleteconferencecallparticipants = new TL_phone.deleteConferenceCallParticipants();
                deleteconferencecallparticipants.only_left = true;
                deleteconferencecallparticipants.call = this.inputGroupCall;
                deleteconferencecallparticipants.block = bArrCall_create_change_state_block;
                deleteconferencecallparticipants.ids.addAll(shadyLeftParticipants);
                final long jCurrentTimeMillis = System.currentTimeMillis();
                ConnectionsManager.getInstance(this.currentAccount).sendRequest(deleteconferencecallparticipants, new RequestDelegate() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda1
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$updateParticipants$11(jCurrentTimeMillis, tLObject, tL_error);
                    }
                });
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
        if (z) {
            forcePoll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParticipants$11(final long j, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateParticipants$10(j, tLObject, tL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateParticipants$10(long j, TLObject tLObject, TLRPC.TL_error tL_error) {
        processUpdates(null, Long.valueOf(j), tLObject, tL_error);
    }

    public void kick(long j) {
        if (this.destroyed) {
            FileLog.m1157d("[tde2e] conference.kick but destroyed!");
            return;
        }
        if (this.call_id < 0 || !getBlockchainParticipants().contains(Long.valueOf(j))) {
            return;
        }
        CallState callStateCall_get_state = call_get_state(this.call_id);
        CallState callState = new CallState();
        callState.height = callStateCall_get_state.height + 1;
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            CallParticipant[] callParticipantArr = callStateCall_get_state.participants;
            if (i >= callParticipantArr.length) {
                callState.participants = (CallParticipant[]) arrayList.toArray(new CallParticipant[0]);
                FileLog.m1157d("[tde2e] kick: call_create_change_state_block from " + this.state + " to " + callState);
                byte[] bArrCall_create_change_state_block = call_create_change_state_block(this.call_id, callState);
                StringBuilder sb = new StringBuilder();
                sb.append("[tde2e] kick: call_create_change_state_block returns ");
                sb.append(call_describe_block(bArrCall_create_change_state_block));
                FileLog.m1157d(sb.toString());
                this.state = callState;
                TL_phone.deleteConferenceCallParticipants deleteconferencecallparticipants = new TL_phone.deleteConferenceCallParticipants();
                deleteconferencecallparticipants.kick = true;
                deleteconferencecallparticipants.call = this.inputGroupCall;
                deleteconferencecallparticipants.block = bArrCall_create_change_state_block;
                deleteconferencecallparticipants.ids.add(Long.valueOf(j));
                final long jCurrentTimeMillis = System.currentTimeMillis();
                ConnectionsManager.getInstance(this.currentAccount).sendRequest(deleteconferencecallparticipants, new RequestDelegate() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda8
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$kick$13(jCurrentTimeMillis, tLObject, tL_error);
                    }
                });
                return;
            }
            if (j != callParticipantArr[i].user_id) {
                CallParticipant callParticipant = new CallParticipant();
                CallParticipant callParticipant2 = callStateCall_get_state.participants[i];
                callParticipant.user_id = callParticipant2.user_id;
                callParticipant.public_key_id = callParticipant2.public_key_id;
                callParticipant.permissions = callParticipant2.permissions;
                arrayList.add(callParticipant);
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$kick$13(final long j, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.messenger.voip.ConferenceCall$$ExternalSyntheticLambda11
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$kick$12(j, tLObject, tL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$kick$12(long j, TLObject tLObject, TLRPC.TL_error tL_error) {
        processUpdates(null, Long.valueOf(j), tLObject, tL_error);
    }

    public HashSet<Long> getBlockchainParticipants() {
        CallState callStateCall_get_state;
        HashSet<Long> hashSet = new HashSet<>();
        if (this.destroyed) {
            FileLog.m1157d("[tde2e] conference.getBlockchainParticipants but destroyed!");
            return hashSet;
        }
        long j = this.call_id;
        if (j >= 0) {
            try {
                callStateCall_get_state = call_get_state(j);
            } catch (Exception e) {
                FileLog.m1160e(e);
                callStateCall_get_state = null;
            }
            if (callStateCall_get_state != null) {
                int i = 0;
                while (true) {
                    CallParticipant[] callParticipantArr = callStateCall_get_state.participants;
                    if (i >= callParticipantArr.length) {
                        break;
                    }
                    hashSet.add(Long.valueOf(callParticipantArr[i].user_id));
                    i++;
                }
            }
        }
        return hashSet;
    }

    public HashSet<Long> getShadyJoiningParticipants(ArrayList<TLRPC.GroupCallParticipant> arrayList) {
        CallState callStateCall_get_state;
        TLRPC.GroupCallParticipant groupCallParticipant;
        HashSet<Long> hashSet = new HashSet<>();
        if (this.destroyed) {
            FileLog.m1157d("[tde2e] conference.getShadyJoiningParticipants but destroyed!");
            return hashSet;
        }
        long j = this.call_id;
        if (j >= 0) {
            try {
                callStateCall_get_state = call_get_state(j);
            } catch (Exception e) {
                FileLog.m1160e(e);
                callStateCall_get_state = null;
            }
            if (callStateCall_get_state != null) {
                int i = 0;
                while (true) {
                    CallParticipant[] callParticipantArr = callStateCall_get_state.participants;
                    if (i >= callParticipantArr.length) {
                        break;
                    }
                    long j2 = callParticipantArr[i].user_id;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= (arrayList == null ? 0 : arrayList.size())) {
                            groupCallParticipant = null;
                            break;
                        }
                        if (j2 == DialogObject.getPeerDialogId(arrayList.get(i2).peer)) {
                            groupCallParticipant = arrayList.get(i2);
                            break;
                        }
                        i2++;
                    }
                    if (groupCallParticipant == null && j2 != this.my_user_id && this.joiningBlockchainParticipants.contains(Long.valueOf(j2))) {
                        hashSet.add(Long.valueOf(j2));
                    }
                    i++;
                }
            }
        }
        return hashSet;
    }

    public HashSet<Long> getShadyLeftParticipants(ArrayList<TLRPC.GroupCallParticipant> arrayList) {
        CallState callStateCall_get_state;
        TLRPC.GroupCallParticipant groupCallParticipant;
        HashSet<Long> hashSet = new HashSet<>();
        if (this.destroyed) {
            FileLog.m1157d("[tde2e] conference.getShadyLeftParticipants but destroyed!");
            return hashSet;
        }
        long j = this.call_id;
        if (j >= 0) {
            try {
                callStateCall_get_state = call_get_state(j);
            } catch (Exception e) {
                FileLog.m1160e(e);
                callStateCall_get_state = null;
            }
            if (callStateCall_get_state != null) {
                int i = 0;
                while (true) {
                    CallParticipant[] callParticipantArr = callStateCall_get_state.participants;
                    if (i >= callParticipantArr.length) {
                        break;
                    }
                    long j2 = callParticipantArr[i].user_id;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= (arrayList == null ? 0 : arrayList.size())) {
                            groupCallParticipant = null;
                            break;
                        }
                        if (j2 == DialogObject.getPeerDialogId(arrayList.get(i2).peer)) {
                            groupCallParticipant = arrayList.get(i2);
                            break;
                        }
                        i2++;
                    }
                    if (groupCallParticipant == null && j2 != this.my_user_id && !this.joiningBlockchainParticipants.contains(Long.valueOf(j2))) {
                        hashSet.add(Long.valueOf(j2));
                    }
                    i++;
                }
            }
        }
        return hashSet;
    }

    public void reset() {
        AndroidUtilities.cancelRunOnUIThread(this.pollRunnable);
        if (this.call_id != -1) {
            for (int i = 0; i < 2; i++) {
                if (this.pollRequestId[i] != 0) {
                    ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.pollRequestId[i], true);
                    this.pollRequestId[i] = 0;
                }
            }
            call_destroy(this.call_id);
            FileLog.m1157d("[tde2e] call_destroy(" + this.call_id + ")");
            this.call_id = -1L;
        }
        int[] iArr = this.last_offset;
        iArr[0] = -1;
        iArr[1] = -1;
        this.blocksQueue[0].clear();
        this.blocksQueue[1].clear();
        init();
    }

    public void joined() {
        this.joined = true;
    }

    public void destroy() {
        this.destroyed = true;
        reset();
    }

    public long getCallId() {
        return this.call_id;
    }
}
