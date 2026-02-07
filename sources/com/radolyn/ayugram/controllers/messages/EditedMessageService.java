package com.radolyn.ayugram.controllers.messages;

import android.text.TextUtils;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.AyuUtils;
import com.radolyn.ayugram.controllers.AyuMessagesController;
import com.radolyn.ayugram.database.AyuData;
import com.radolyn.ayugram.database.entities.EditedMessage;
import java.io.IOException;
import java.util.List;
import org.lsposed.lsparanoid.Deobfuscator$AyuGram4A$TMessagesProj;
import org.telegram.messenger.FileLog;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes.dex */
public class EditedMessageService {
    private final AyuMessagesController controller;

    public EditedMessageService(AyuMessagesController ayuMessagesController) {
        this.controller = ayuMessagesController;
    }

    public void onMessageEdited(final SaveMessageRequest saveMessageRequest, TLRPC.Message message) throws IOException {
        try {
            if (AyuConfig.saveEditedMessageFor(this.controller.getCurrentAccount(), saveMessageRequest.getDialogId()) || saveMessageRequest.isForce()) {
                TLRPC.Message message2 = saveMessageRequest.getMessage();
                final boolean z = !saveMessageRequest.isForce() && isSameMedia(message2, message);
                if (z && TextUtils.equals(message2.message, message.message)) {
                    return;
                }
                if (!saveMessageRequest.isForce() && message.from_id.user_id != 0 && !message.edit_hide) {
                    this.controller.getAyuSpyControllerInternal().saveOnlineActivity(message.from_id.user_id, saveMessageRequest.getRequestCatchTime());
                }
                this.controller.executeAsync(new Runnable() { // from class: com.radolyn.ayugram.controllers.messages.EditedMessageService$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onMessageEdited$0(saveMessageRequest, z);
                    }
                }, Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019347461763628582L));
            }
        } catch (Throwable th) {
            AyuUtils.logError(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019347393044151846L), th);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x003a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean isSameMedia(org.telegram.tgnet.TLRPC.Message r5, org.telegram.tgnet.TLRPC.Message r6) {
        /*
            org.telegram.tgnet.TLRPC$MessageMedia r0 = r5.media
            org.telegram.tgnet.TLRPC$MessageMedia r1 = r6.media
            r2 = 1
            r3 = 0
            if (r0 == r1) goto L1b
            if (r0 == 0) goto L19
            if (r1 == 0) goto L19
            java.lang.Class r0 = r0.getClass()
            org.telegram.tgnet.TLRPC$MessageMedia r1 = r6.media
            java.lang.Class r1 = r1.getClass()
            if (r0 != r1) goto L19
            goto L1b
        L19:
            r0 = 0
            goto L1c
        L1b:
            r0 = 1
        L1c:
            org.telegram.tgnet.TLRPC$MessageMedia r5 = r5.media
            boolean r1 = r5 instanceof org.telegram.tgnet.TLRPC.TL_messageMediaPhoto
            if (r1 == 0) goto L3a
            org.telegram.tgnet.TLRPC$MessageMedia r1 = r6.media
            boolean r4 = r1 instanceof org.telegram.tgnet.TLRPC.TL_messageMediaPhoto
            if (r4 == 0) goto L3a
            org.telegram.tgnet.TLRPC$Photo r5 = r5.photo
            if (r5 == 0) goto L56
            org.telegram.tgnet.TLRPC$Photo r6 = r1.photo
            if (r6 == 0) goto L56
            long r0 = r5.f1603id
            long r5 = r6.f1603id
            int r4 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r4 != 0) goto L39
            return r2
        L39:
            return r3
        L3a:
            boolean r1 = r5 instanceof org.telegram.tgnet.TLRPC.TL_messageMediaDocument
            if (r1 == 0) goto L56
            org.telegram.tgnet.TLRPC$MessageMedia r6 = r6.media
            boolean r1 = r6 instanceof org.telegram.tgnet.TLRPC.TL_messageMediaDocument
            if (r1 == 0) goto L56
            org.telegram.tgnet.TLRPC$Document r5 = r5.document
            if (r5 == 0) goto L56
            org.telegram.tgnet.TLRPC$Document r6 = r6.document
            if (r6 == 0) goto L56
            long r0 = r5.f1579id
            long r5 = r6.f1579id
            int r4 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r4 != 0) goto L55
            return r2
        L55:
            return r3
        L56:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.radolyn.ayugram.controllers.messages.EditedMessageService.isSameMedia(org.telegram.tgnet.TLRPC$Message, org.telegram.tgnet.TLRPC$Message):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: saveEditedMessage, reason: merged with bridge method [inline-methods] */
    public void lambda$onMessageEdited$0(SaveMessageRequest saveMessageRequest, boolean z) {
        EditedMessage lastRevision;
        String str;
        EditedMessage editedMessage = new EditedMessage();
        this.controller.getAyuMapperInternal().map(saveMessageRequest, editedMessage);
        try {
            this.controller.getAyuMapperInternal().mapMedia(saveMessageRequest, editedMessage, !z);
        } catch (Exception e) {
            FileLog.m1159e(Deobfuscator$AyuGram4A$TMessagesProj.getString(-2019347539073039910L) + saveMessageRequest.getMessageId(), e);
        }
        if (!z && !TextUtils.isEmpty(editedMessage.mediaPath) && (lastRevision = AyuData.getEditedMessageDao().getLastRevision(this.controller.getUserId(), saveMessageRequest.getDialogId(), saveMessageRequest.getMessageId())) != null && !TextUtils.equals(editedMessage.mediaPath, lastRevision.mediaPath) && (str = lastRevision.mediaPath) != null && !str.contains(AyuConfig.getSavePathFolder())) {
            AyuData.getEditedMessageDao().updateMediaPathForRevisionsBetweenDates(this.controller.getUserId(), saveMessageRequest.getDialogId(), saveMessageRequest.getMessageId(), lastRevision.mediaPath, editedMessage.mediaPath);
        }
        AyuData.getEditedMessageDao().insert(editedMessage);
    }

    public boolean hasAnyRevisions(long j, int i) {
        return AyuData.getEditedMessageDao().hasAnyRevisions(this.controller.getUserId(), j, i);
    }

    public List getRevisions(long j, int i, int i2, int i3) {
        return AyuData.getEditedMessageDao().getAllRevisions(this.controller.getUserId(), j, i, i2, i3);
    }
}
