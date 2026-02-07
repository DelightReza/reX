package org.telegram.messenger.voip;

import java.util.ArrayList;
import me.vkryl.core.BitwiseUtils;
import org.telegram.p023ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes5.dex */
public class GroupCallMessage {
    private static final int FLAG_IS_OUT = 1;
    private static final int FLAG_SEND_CONFIRMED = 8;
    private static final int FLAG_SEND_DELAYED = 2;
    private static final int FLAG_SEND_ERROR = 4;
    public final int currentAccount;
    private int flags;
    public final long fromId;
    private final ArrayList<Runnable> listeners = new ArrayList<>();
    public final TLRPC.TL_textWithEntities message;
    public final long randomId;
    public final long reactionAnimatedEmojiId;
    public final ReactionsLayoutInBubble.VisibleReaction visibleReaction;

    /* JADX WARN: Removed duplicated region for block: B:9:0x0031  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public GroupCallMessage(int r3, long r4, long r6, org.telegram.tgnet.TLRPC.TL_textWithEntities r8) {
        /*
            r2 = this;
            r2.<init>()
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2.listeners = r0
            r2.currentAccount = r3
            r2.fromId = r4
            r2.randomId = r6
            r2.message = r8
            java.util.ArrayList r4 = r8.entities
            r5 = 0
            if (r4 == 0) goto L31
            int r4 = r4.size()
            r7 = 1
            if (r4 != r7) goto L31
            java.util.ArrayList r4 = r8.entities
            r7 = 0
            java.lang.Object r4 = r4.get(r7)
            org.telegram.tgnet.TLRPC$MessageEntity r4 = (org.telegram.tgnet.TLRPC.MessageEntity) r4
            boolean r7 = r4 instanceof org.telegram.tgnet.TLRPC.TL_messageEntityCustomEmoji
            if (r7 == 0) goto L31
            org.telegram.tgnet.TLRPC$TL_messageEntityCustomEmoji r4 = (org.telegram.tgnet.TLRPC.TL_messageEntityCustomEmoji) r4
            long r0 = r4.document_id
            goto L32
        L31:
            r0 = r5
        L32:
            int r4 = (r0 > r5 ? 1 : (r0 == r5 ? 0 : -1))
            if (r4 == 0) goto L3f
            java.lang.Long r3 = java.lang.Long.valueOf(r0)
            org.telegram.ui.Components.Reactions.ReactionsLayoutInBubble$VisibleReaction r3 = org.telegram.ui.Components.Reactions.ReactionsLayoutInBubble.VisibleReaction.fromCustomEmoji(r3)
            goto L61
        L3f:
            java.util.ArrayList r4 = r8.entities
            if (r4 == 0) goto L49
            boolean r4 = r4.isEmpty()
            if (r4 == 0) goto L60
        L49:
            org.telegram.messenger.MediaDataController r3 = org.telegram.messenger.MediaDataController.getInstance(r3)
            java.util.HashMap r3 = r3.getReactionsMap()
            java.lang.String r4 = r8.text
            java.lang.Object r3 = r3.get(r4)
            org.telegram.tgnet.TLRPC$TL_availableReaction r3 = (org.telegram.tgnet.TLRPC.TL_availableReaction) r3
            if (r3 == 0) goto L60
            org.telegram.ui.Components.Reactions.ReactionsLayoutInBubble$VisibleReaction r3 = org.telegram.ui.Components.Reactions.ReactionsLayoutInBubble.VisibleReaction.fromEmojicon(r3)
            goto L61
        L60:
            r3 = 0
        L61:
            r2.reactionAnimatedEmojiId = r0
            r2.visibleReaction = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.voip.GroupCallMessage.<init>(int, long, long, org.telegram.tgnet.TLRPC$TL_textWithEntities):void");
    }

    public void setIsOut(boolean z) {
        this.flags = BitwiseUtils.setFlag(this.flags, 1, z);
    }

    public void setIsSendDelayed(boolean z) {
        this.flags = BitwiseUtils.setFlag(this.flags, 2, z);
    }

    public void setIsSendError(boolean z) {
        this.flags = BitwiseUtils.setFlag(this.flags, 4, z);
    }

    public void setIsSendConfirmed(boolean z) {
        this.flags = BitwiseUtils.setFlag(this.flags, 8, z);
    }

    public boolean isOut() {
        return BitwiseUtils.hasFlag(this.flags, 1);
    }

    public boolean isSendDelayed() {
        return BitwiseUtils.hasFlag(this.flags, 2);
    }

    public boolean isSendError() {
        return BitwiseUtils.hasFlag(this.flags, 4);
    }

    public boolean isSendConfirmed() {
        return BitwiseUtils.hasFlag(this.flags, 8);
    }

    public void subscribeToStateUpdates(Runnable runnable) {
        this.listeners.add(runnable);
    }

    public void unsubscribeFromStateUpdates(Runnable runnable) {
        this.listeners.remove(runnable);
    }

    public void notifyStateUpdate() {
        ArrayList<Runnable> arrayList = this.listeners;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Runnable runnable = arrayList.get(i);
            i++;
            runnable.run();
        }
    }
}
