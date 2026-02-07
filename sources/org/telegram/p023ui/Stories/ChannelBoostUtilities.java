package org.telegram.p023ui.Stories;

import android.text.TextUtils;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.MessagesController;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes6.dex */
public abstract class ChannelBoostUtilities {
    public static String createLink(int i, long j) {
        long j2 = -j;
        TLRPC.Chat chat = MessagesController.getInstance(i).getChat(Long.valueOf(j2));
        if (!TextUtils.isEmpty(ChatObject.getPublicUsername(chat))) {
            return "https://t.me/boost/" + ChatObject.getPublicUsername(chat);
        }
        return "https://t.me/boost/?c=" + j2;
    }
}
