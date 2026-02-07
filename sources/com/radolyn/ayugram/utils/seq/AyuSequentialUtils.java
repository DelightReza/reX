package com.radolyn.ayugram.utils.seq;

import android.text.TextUtils;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.controllers.AyuAttachments;
import com.radolyn.ayugram.utils.AyuMessageUtils;
import com.radolyn.ayugram.utils.network.AyuRequestUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes4.dex */
public abstract class AyuSequentialUtils {
    public static void loadDocumentsSync(int i, ArrayList arrayList) throws InterruptedException {
        ArrayList arrayList2 = new ArrayList(arrayList);
        DummyFileDownloadWaiter dummyFileDownloadWaiter = new DummyFileDownloadWaiter(i, arrayList2);
        dummyFileDownloadWaiter.subscribe();
        final FileLoader fileLoader = FileLoader.getInstance(i);
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            final MessageObject messageObject = (MessageObject) obj;
            if (!AyuMessageUtils.isMediaDownloadable(messageObject, false)) {
                synchronized (dummyFileDownloadWaiter.messagesLock) {
                    arrayList2.remove(messageObject);
                }
            } else {
                String existingPath = AyuAttachments.getInstance(i).getExistingPath(messageObject, false);
                File file = new File(existingPath);
                if ((file.exists() && !file.isDirectory() && file.length() == AyuMessageUtils.getMessageSize(messageObject)) || (!TextUtils.isEmpty(existingPath) && existingPath.startsWith(AyuConfig.getSavePath()))) {
                    synchronized (dummyFileDownloadWaiter.messagesLock) {
                        arrayList2.remove(messageObject);
                    }
                } else {
                    final TLRPC.Document document = AyuMessageUtils.getDocument(messageObject.messageOwner);
                    TLRPC.Photo photo = AyuMessageUtils.getPhoto(messageObject.messageOwner);
                    if (document != null) {
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.seq.AyuSequentialUtils$$ExternalSyntheticLambda4
                            @Override // java.lang.Runnable
                            public final void run() {
                                fileLoader.loadFile(document, messageObject, 3, 0);
                            }
                        });
                    } else if (photo != null) {
                        final ImageLocation forPhoto = ImageLocation.getForPhoto(FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.getPhotoSize(true)), photo);
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.seq.AyuSequentialUtils$$ExternalSyntheticLambda5
                            @Override // java.lang.Runnable
                            public final void run() {
                                fileLoader.loadFile(forPhoto, messageObject, null, 3, 0);
                            }
                        });
                    } else {
                        synchronized (dummyFileDownloadWaiter.messagesLock) {
                            arrayList2.remove(messageObject);
                        }
                    }
                }
            }
        }
        if (arrayList2.isEmpty()) {
            return;
        }
        dummyFileDownloadWaiter.await();
    }

    public static void sendTextMessageSync(int i, String str, long j, MessageObject messageObject, MessageObject messageObject2, TLRPC.WebPage webPage, boolean z, ArrayList arrayList, boolean z2) throws InterruptedException {
        Long dialogId = AyuRequestUtils.getDialogId(MessagesController.getInstance(i).getInputPeer(j));
        DummyMessageWaiter dummyMessageWaiter = new DummyMessageWaiter(i);
        dummyMessageWaiter.subscribe();
        final SendMessagesHelper sendMessagesHelper = SendMessagesHelper.getInstance(i);
        final SendMessagesHelper.SendMessageParams sendMessageParamsM1191of = SendMessagesHelper.SendMessageParams.m1191of(str, j, messageObject, messageObject2, webPage, z, arrayList, null, null, z2, 0, 0, null, false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.seq.AyuSequentialUtils$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                sendMessagesHelper.sendMessage(sendMessageParamsM1191of);
            }
        });
        dummyMessageWaiter.trySetSendingId(dialogId.longValue());
        dummyMessageWaiter.await();
    }

    public static void sendDocumentMessageSync(int i, TLRPC.TL_document tL_document, String str, long j, MessageObject messageObject, MessageObject messageObject2, String str2, ArrayList arrayList, boolean z, Long l, boolean z2, boolean z3, boolean z4) throws InterruptedException {
        Long dialogId = AyuRequestUtils.getDialogId(MessagesController.getInstance(i).getInputPeer(j));
        SendMessagesHelper.SendMessageParams sendMessageParamsM1193of = SendMessagesHelper.SendMessageParams.m1193of(null, str2, null, null, null, null, tL_document, null, null, null, j, str, messageObject, messageObject2, null, false, null, arrayList, null, createParams(l.longValue(), z2), z, 0, 0, 0, null, null, false, z3);
        sendMessageParamsM1193of.invert_media = z4;
        sendDocumentMessageSync(i, str, dialogId.longValue(), l.longValue(), z2, sendMessageParamsM1193of);
    }

    public static void sendDocumentMessageSync(int i, String str, long j, long j2, boolean z, final SendMessagesHelper.SendMessageParams sendMessageParams) throws InterruptedException {
        boolean z2 = j2 == 0 || z;
        DummyMessageWaiter dummyMessageWaiter = new DummyMessageWaiter(i);
        if (z2) {
            dummyMessageWaiter.subscribe();
        }
        DummyFileUploadWaiter dummyFileUploadWaiter = new DummyFileUploadWaiter(i, str);
        dummyFileUploadWaiter.subscribe();
        final SendMessagesHelper sendMessagesHelper = SendMessagesHelper.getInstance(i);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.seq.AyuSequentialUtils$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                sendMessagesHelper.sendMessage(sendMessageParams);
            }
        });
        if (z2) {
            dummyMessageWaiter.trySetSendingId(j);
        }
        if (j2 == 0) {
            dummyFileUploadWaiter.setMessageId(dummyMessageWaiter.sendingId);
            dummyFileUploadWaiter.await();
        }
        if (z2) {
            dummyMessageWaiter.await();
        }
    }

    public static void sendStickerMessageSync(int i, final TLRPC.Document document, final String str, final long j, final MessageObject messageObject, final MessageObject messageObject2, final boolean z, final int i2, final boolean z2) throws InterruptedException {
        Long dialogId = AyuRequestUtils.getDialogId(MessagesController.getInstance(i).getInputPeer(j));
        DummyMessageWaiter dummyMessageWaiter = new DummyMessageWaiter(i);
        dummyMessageWaiter.subscribe();
        final SendMessagesHelper sendMessagesHelper = SendMessagesHelper.getInstance(i);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.seq.AyuSequentialUtils$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                sendMessagesHelper.sendSticker(document, str, j, messageObject, messageObject2, null, null, null, z, i2, 0, z2, null, null, 0, 0L, 0L, null);
            }
        });
        dummyMessageWaiter.trySetSendingId(dialogId.longValue());
        dummyMessageWaiter.await();
    }

    public static void sendPhotoMessageSync(int i, TLRPC.TL_photo tL_photo, String str, long j, MessageObject messageObject, MessageObject messageObject2, String str2, ArrayList arrayList, boolean z, Long l, boolean z2, boolean z3, boolean z4) throws InterruptedException {
        DummyMessageWaiter dummyMessageWaiter;
        TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(tL_photo.sizes, AndroidUtilities.getPhotoSize(true));
        String str3 = FileLoader.getDirectory(4) + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316293185961510L) + closestPhotoSizeWithSize.location.volume_id + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316301775896102L) + closestPhotoSizeWithSize.location.local_id + Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316310365830694L);
        Long dialogId = AyuRequestUtils.getDialogId(MessagesController.getInstance(i).getInputPeer(j));
        boolean z5 = l.longValue() == 0 || z2;
        DummyMessageWaiter dummyMessageWaiter2 = new DummyMessageWaiter(i);
        if (z5) {
            dummyMessageWaiter2.subscribe();
        }
        DummyFileUploadWaiter dummyFileUploadWaiter = new DummyFileUploadWaiter(i, str3);
        dummyFileUploadWaiter.subscribe();
        final SendMessagesHelper sendMessagesHelper = SendMessagesHelper.getInstance(i);
        HashMap mapCreateParams = createParams(l.longValue(), z2);
        if (mapCreateParams != null) {
            mapCreateParams.put(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316331840667174L), str3);
        }
        final SendMessagesHelper.SendMessageParams sendMessageParamsM1202of = SendMessagesHelper.SendMessageParams.m1202of(tL_photo, str, j, messageObject, messageObject2, str2, arrayList, null, mapCreateParams, z, 0, 0, 0, null, false, z3);
        sendMessageParamsM1202of.invert_media = z4;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.seq.AyuSequentialUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                sendMessagesHelper.sendMessage(sendMessageParamsM1202of);
            }
        });
        if (z5) {
            dummyMessageWaiter = dummyMessageWaiter2;
            dummyMessageWaiter.trySetSendingId(dialogId.longValue());
        } else {
            dummyMessageWaiter = dummyMessageWaiter2;
        }
        if (l.longValue() == 0) {
            dummyFileUploadWaiter.await();
        }
        if (z5) {
            dummyMessageWaiter.await();
        }
    }

    public static void forwardMessagesSync(int i, final ArrayList arrayList, final long j, final boolean z, final boolean z2, final boolean z3, final MessageObject messageObject) throws InterruptedException {
        Long dialogId = AyuRequestUtils.getDialogId(MessagesController.getInstance(i).getInputPeer(j));
        DummyMessageWaiter dummyMessageWaiter = new DummyMessageWaiter(i);
        dummyMessageWaiter.subscribe();
        final SendMessagesHelper sendMessagesHelper = SendMessagesHelper.getInstance(i);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.radolyn.ayugram.utils.seq.AyuSequentialUtils$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                sendMessagesHelper.sendMessage(arrayList, j, z, z2, z3, 0, messageObject, -1, 0L);
            }
        });
        dummyMessageWaiter.trySetSendingId(dialogId.longValue());
        dummyMessageWaiter.await();
    }

    private static HashMap createParams(long j, boolean z) {
        if (j == 0) {
            return null;
        }
        HashMap map = new HashMap();
        map.put(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316387675242022L), String.valueOf(j));
        if (z) {
            map.put(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316422034980390L), Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019316447804784166L));
        }
        return map;
    }
}
