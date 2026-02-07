package com.radolyn.ayugram;

import com.android.tools.p002r8.RecordTag;
import com.exteragram.messenger.p003ai.p004ui.AbstractC0746x1d8a54ff;
import com.radolyn.ayugram.utils.AyuMessageUtils;
import com.radolyn.ayugram.utils.seq.AyuSequentialUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentMap;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import p017j$.util.Objects;
import p017j$.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public abstract class AyuForward {
    private static final DispatchQueue queue = new DispatchQueue(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019309962404167206L));
    private static final ConcurrentMap forwardStates = new ConcurrentHashMap();

    public static DispatchQueue getQueue() {
        return queue;
    }

    public static boolean isForwarding(long j) {
        int i;
        ForwardState forwardState = (ForwardState) forwardStates.get(Long.valueOf(j));
        if (j == 0 || forwardState == null || (i = forwardState.state) == 3) {
            return false;
        }
        int i2 = forwardState.currentChunk;
        int i3 = forwardState.totalChunks;
        if (i2 >= i3 || forwardState.stopRequested) {
            return false;
        }
        return !(i3 == 0 || forwardState.totalMessages == 0) || i == 1;
    }

    public static boolean isFullAyuForwardsNeeded(int i, ArrayList arrayList) {
        return isFullAyuForwardsNeeded(i, (MessageObject) arrayList.get(0));
    }

    public static boolean isFullAyuForwardsNeeded(int i, MessageObject messageObject) {
        return AyuMessageUtils.isChatNoForwards(i, messageObject.getDialogId());
    }

    public static boolean isAyuForwardNeeded(ArrayList arrayList) {
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (isAyuForwardNeeded((MessageObject) obj)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAyuForwardNeeded(MessageObject messageObject) {
        return AyuMessageUtils.isUnforwardable(messageObject);
    }

    public static void intelligentForward(int i, ArrayList arrayList, long j, boolean z, boolean z2, boolean z3, int i2, MessageObject messageObject) {
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        ForwardChunk forwardChunk = new ForwardChunk(isAyuForwardNeeded((MessageObject) arrayList.get(0)), arrayList3);
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            MessageObject messageObject2 = (MessageObject) obj;
            if (isAyuForwardNeeded(messageObject2) != forwardChunk.isAyuForwardNeeded) {
                arrayList2.add(forwardChunk);
                arrayList3 = new ArrayList();
                forwardChunk = new ForwardChunk(isAyuForwardNeeded(messageObject2), arrayList3);
            }
            arrayList3.add(messageObject2);
        }
        arrayList2.add(forwardChunk);
        ForwardState forwardState = new ForwardState(arrayList2.size());
        forwardStates.put(Long.valueOf(j), forwardState);
        int size2 = arrayList2.size();
        int i4 = 0;
        while (i4 < size2) {
            Object obj2 = arrayList2.get(i4);
            i4++;
            ForwardChunk forwardChunk2 = (ForwardChunk) obj2;
            if (forwardChunk2.isAyuForwardNeeded) {
                forwardMessages(i, forwardChunk2.messages, j, z2, z3, messageObject, forwardState);
            } else {
                forwardState.totalMessages = forwardChunk2.messages.size();
                forwardState.sentMessages = 0;
                forwardState.setState(2);
                AyuSequentialUtils.forwardMessagesSync(i, forwardChunk2.messages, j, z, z2, z3, messageObject);
                forwardState.sentMessages = forwardState.totalMessages;
                forwardState.setState(3);
            }
            forwardState.increaseChunk();
        }
        forwardState.setState(3);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0117  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void forwardMessages(int r27, java.util.ArrayList r28, long r29, boolean r31, boolean r32, org.telegram.messenger.MessageObject r33, com.radolyn.ayugram.AyuForward.ForwardState r34) {
        /*
            Method dump skipped, instructions count: 576
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.radolyn.ayugram.AyuForward.forwardMessages(int, java.util.ArrayList, long, boolean, boolean, org.telegram.messenger.MessageObject, com.radolyn.ayugram.AyuForward$ForwardState):void");
    }

    private static TLRPC.TL_document mapDocument(int i, MessageObject messageObject, TLRPC.Document document, File file, boolean z) {
        TLRPC.TL_document tL_document = new TLRPC.TL_document();
        tL_document.flags = document.flags;
        tL_document.file_reference = z ? document.file_reference : new byte[0];
        tL_document.dc_id = z ? document.dc_id : TLObject.FLAG_31;
        tL_document.user_id = document.user_id;
        tL_document.version = document.version;
        tL_document.mime_type = document.mime_type;
        tL_document.file_name = document.file_name;
        tL_document.file_name_fixed = document.file_name_fixed;
        tL_document.date = AccountInstance.getInstance(i).getConnectionsManager().getCurrentTime();
        if (file != null) {
            tL_document.size = (int) file.length();
        }
        tL_document.thumbs = document.thumbs;
        tL_document.video_thumbs = document.video_thumbs;
        tL_document.localThumbPath = document.localThumbPath;
        tL_document.attributes = document.attributes;
        if (file != null) {
            tL_document.localPath = file.getAbsolutePath();
        }
        if (messageObject.isGif()) {
            tL_document.mime_type = Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019309893684690470L);
        }
        return tL_document;
    }

    private static TLRPC.TL_photo mapPhoto(int i, TLRPC.Photo photo, File file) {
        TLRPC.TL_photo tL_photoGeneratePhotoSizes = SendMessagesHelper.getInstance(i).generatePhotoSizes(file.getAbsolutePath(), null);
        tL_photoGeneratePhotoSizes.flags = photo.flags;
        tL_photoGeneratePhotoSizes.has_stickers = photo.has_stickers;
        tL_photoGeneratePhotoSizes.date = AccountInstance.getInstance(i).getConnectionsManager().getCurrentTime();
        tL_photoGeneratePhotoSizes.geo = photo.geo;
        tL_photoGeneratePhotoSizes.caption = photo.caption;
        return tL_photoGeneratePhotoSizes;
    }

    public static String getForwardingStatus(long j) {
        String string;
        ForwardState forwardState = (ForwardState) forwardStates.get(Long.valueOf(j));
        if (forwardState == null) {
            return null;
        }
        String string2 = LocaleController.formatString(C2369R.string.AyuForwardStatusSentCount, Integer.valueOf(forwardState.sentMessages), Integer.valueOf(forwardState.totalMessages));
        String string3 = LocaleController.formatString(C2369R.string.AyuForwardStatusChunkCount, Integer.valueOf(forwardState.currentChunk + 1), Integer.valueOf(forwardState.totalChunks));
        if (forwardState.totalChunks > 1) {
            string2 = string2 + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019309936634363430L) + string3;
        }
        int i = forwardState.state;
        if (i == 0) {
            string = LocaleController.getString(C2369R.string.AyuForwardStatusPreparing);
        } else {
            if (i == 1) {
                return LocaleController.getString(C2369R.string.AyuForwardStatusLoadingMedia);
            }
            if (i == 2) {
                string = LocaleController.getString(C2369R.string.AyuForwardStatusForwarding);
            } else {
                string = LocaleController.getString(C2369R.string.AyuForwardStatusFinished);
            }
        }
        return string + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019309953814232614L) + string2;
    }

    public static boolean stop(long j) {
        ForwardState forwardState = (ForwardState) forwardStates.get(Long.valueOf(j));
        if (forwardState == null) {
            return false;
        }
        forwardState.requestStop();
        return true;
    }

    public static void forceStop() {
        forwardStates.clear();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.AyuForward$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(AyuConstants.UPDATE_CHAT_RESTRICTION, new Object[0]);
            }
        });
    }

    /* loaded from: classes4.dex */
    private static final class ForwardChunk extends RecordTag {
        private final boolean isAyuForwardNeeded;
        private final ArrayList messages;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof ForwardChunk)) {
                return false;
            }
            ForwardChunk forwardChunk = (ForwardChunk) obj;
            return this.isAyuForwardNeeded == forwardChunk.isAyuForwardNeeded && Objects.equals(this.messages, forwardChunk.messages);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{Boolean.valueOf(this.isAyuForwardNeeded), this.messages};
        }

        private ForwardChunk(boolean z, ArrayList arrayList) {
            this.isAyuForwardNeeded = z;
            this.messages = arrayList;
        }

        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        public final int hashCode() {
            return AyuForward$ForwardChunk$$ExternalSyntheticRecord0.m464m(this.isAyuForwardNeeded, this.messages);
        }

        public final String toString() {
            return AbstractC0746x1d8a54ff.m185m($record$getFieldsAsObjects(), ForwardChunk.class, "isAyuForwardNeeded;messages");
        }
    }

    /* loaded from: classes4.dex */
    public static class ForwardState {
        public final int totalChunks;
        public int currentChunk = 0;
        public int totalMessages = 0;
        public int sentMessages = 0;
        public int state = 0;
        public boolean stopRequested = false;

        public ForwardState(int i) {
            this.totalChunks = i;
        }

        public void increaseChunk() {
            this.currentChunk++;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.AyuForward$ForwardState$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(AyuConstants.UPDATE_CHAT_RESTRICTION, new Object[0]);
                }
            });
        }

        public void increaseSentMessages() {
            this.sentMessages++;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.AyuForward$ForwardState$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(AyuConstants.UPDATE_CHAT_RESTRICTION, new Object[0]);
                }
            });
        }

        public void decreaseTotalMessages() {
            this.totalMessages--;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.AyuForward$ForwardState$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(AyuConstants.UPDATE_CHAT_RESTRICTION, new Object[0]);
                }
            });
        }

        public void requestStop() {
            this.stopRequested = true;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.AyuForward$ForwardState$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(AyuConstants.UPDATE_CHAT_RESTRICTION, new Object[0]);
                }
            });
        }

        public void setState(int i) {
            this.state = i;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.AyuForward$ForwardState$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationCenter.getInstance(UserConfig.selectedAccount).lambda$postNotificationNameOnUIThread$1(AyuConstants.UPDATE_CHAT_RESTRICTION, new Object[0]);
                }
            });
        }
    }
}
