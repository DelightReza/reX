package com.exteragram.messenger.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.utils.network.ImgurUtils;
import com.exteragram.messenger.utils.network.RemoteUtils;
import com.exteragram.messenger.utils.system.SystemUtils;
import com.google.android.exoplayer2.util.Consumer;
import com.radolyn.ayugram.controllers.AyuAttachments;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.ChannelAdminLogActivity;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.ColoredImageSpan;
import org.telegram.p023ui.Components.TranscribeButton;
import org.telegram.p023ui.ProfileActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import p017j$.util.Objects;

/* loaded from: classes.dex */
public class ChatUtils {
    private static SpannableStringBuilder channelIcon;
    private static SpannableStringBuilder editedIcon;
    private final int selectedAccount;
    public static final DispatchQueue utilsQueue = new DispatchQueue("utilsQueue");
    private static final ChatUtils[] Instance = new ChatUtils[16];
    private static final Object[] lockObjects = new Object[16];
    private static final CharsetDecoder textDecoder = StandardCharsets.UTF_8.newDecoder();

    public static long extractOwnerId(long j) {
        long j2 = j >> 32;
        if (((j >> 16) & 255) == 63) {
            j2 |= 2147483648L;
        }
        return ((j >> 24) & 255) != 0 ? j2 + 4294967296L : j2;
    }

    static {
        for (int i = 0; i < 16; i++) {
            lockObjects[i] = new Object();
        }
    }

    public ChatUtils(int i) {
        this.selectedAccount = i;
    }

    public static CharSequence getEditedIcon() {
        if (editedIcon == null) {
            editedIcon = new SpannableStringBuilder("\u200d");
            ColoredImageSpan coloredImageSpan = new ColoredImageSpan(Theme.chat_pencilIconDrawable);
            coloredImageSpan.setTranslateX(-AndroidUtilities.m1146dp(1.0f));
            editedIcon.setSpan(coloredImageSpan, 0, 1, 33);
        }
        return editedIcon;
    }

    public static CharSequence getChannelIcon() {
        if (channelIcon == null) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("\u200d");
            channelIcon = spannableStringBuilder;
            spannableStringBuilder.setSpan(new ColoredImageSpan(Theme.chat_channelIconDrawable), 0, 1, 33);
        }
        return channelIcon;
    }

    public static ChatUtils getInstance() {
        return getInstance(UserConfig.selectedAccount);
    }

    public static ChatUtils getInstance(int i) {
        ChatUtils chatUtils;
        ChatUtils[] chatUtilsArr = Instance;
        ChatUtils chatUtils2 = chatUtilsArr[i];
        if (chatUtils2 != null) {
            return chatUtils2;
        }
        synchronized (lockObjects) {
            try {
                chatUtils = chatUtilsArr[i];
                if (chatUtils == null) {
                    chatUtils = new ChatUtils(i);
                    chatUtilsArr[i] = chatUtils;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return chatUtils;
    }

    public static String getDCName(int i) {
        if (i == 1) {
            return "Miami FL, USA";
        }
        if (i == 2) {
            return "Amsterdam, NL";
        }
        if (i == 3) {
            return "Miami FL, USA";
        }
        if (i == 4) {
            return "Amsterdam, NL";
        }
        if (i != 5) {
            return null;
        }
        return "Singapore, SG";
    }

    private static boolean fileExists(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        return file.exists() && file.isFile();
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x007a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.lang.CharSequence getMessageText(org.telegram.messenger.MessageObject r9, org.telegram.messenger.MessageObject.GroupedMessages r10) {
        /*
            r8 = this;
            java.lang.String r0 = "\n"
            int r1 = r9.type
            r2 = 19
            r3 = 0
            if (r1 == r2) goto L7a
            r2 = 15
            if (r1 == r2) goto L7a
            r2 = 13
            if (r1 == r2) goto L7a
            java.lang.CharSequence r10 = r8.getMessageCaption(r9, r10)
            if (r10 != 0) goto L64
            boolean r1 = r9.isPoll()
            if (r1 == 0) goto L64
            org.telegram.tgnet.TLRPC$Message r1 = r9.messageOwner     // Catch: java.lang.Exception -> L55
            org.telegram.tgnet.TLRPC$MessageMedia r1 = r1.media     // Catch: java.lang.Exception -> L55
            org.telegram.tgnet.TLRPC$TL_messageMediaPoll r1 = (org.telegram.tgnet.TLRPC.TL_messageMediaPoll) r1     // Catch: java.lang.Exception -> L55
            org.telegram.tgnet.TLRPC$Poll r1 = r1.poll     // Catch: java.lang.Exception -> L55
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L55
            java.lang.String r4 = "📊 "
            r2.<init>(r4)     // Catch: java.lang.Exception -> L55
            org.telegram.tgnet.TLRPC$TL_textWithEntities r4 = r1.question     // Catch: java.lang.Exception -> L55
            java.lang.String r4 = r4.text     // Catch: java.lang.Exception -> L55
            r2.append(r4)     // Catch: java.lang.Exception -> L55
            r2.append(r0)     // Catch: java.lang.Exception -> L55
            java.util.ArrayList r1 = r1.answers     // Catch: java.lang.Exception -> L55
            int r4 = r1.size()     // Catch: java.lang.Exception -> L55
            r5 = 0
        L3e:
            if (r5 >= r4) goto L60
            java.lang.Object r6 = r1.get(r5)     // Catch: java.lang.Exception -> L55
            int r5 = r5 + 1
            org.telegram.tgnet.TLRPC$PollAnswer r6 = (org.telegram.tgnet.TLRPC.PollAnswer) r6     // Catch: java.lang.Exception -> L55
            java.lang.String r7 = "• "
            r2.append(r7)     // Catch: java.lang.Exception -> L55
            org.telegram.tgnet.TLRPC$TL_textWithEntities r6 = r6.text     // Catch: java.lang.Exception -> L55
            if (r6 != 0) goto L57
            java.lang.String r6 = ""
            goto L59
        L55:
            goto L64
        L57:
            java.lang.String r6 = r6.text     // Catch: java.lang.Exception -> L55
        L59:
            r2.append(r6)     // Catch: java.lang.Exception -> L55
            r2.append(r0)     // Catch: java.lang.Exception -> L55
            goto L3e
        L60:
            java.lang.String r10 = r2.toString()     // Catch: java.lang.Exception -> L55
        L64:
            if (r10 != 0) goto L72
            org.telegram.tgnet.TLRPC$Message r0 = r9.messageOwner
            boolean r0 = org.telegram.messenger.MessageObject.isMediaEmpty(r0)
            if (r0 == 0) goto L72
            java.lang.CharSequence r10 = r8.getMessageContent(r9)
        L72:
            if (r10 == 0) goto L7b
            boolean r0 = org.telegram.messenger.Emoji.fullyConsistsOfEmojis(r10)
            if (r0 == 0) goto L7b
        L7a:
            r10 = r3
        L7b:
            boolean r0 = r9.translated
            if (r0 != 0) goto L85
            boolean r9 = r9.isRestrictedMessage
            if (r9 == 0) goto L84
            goto L85
        L84:
            r3 = r10
        L85:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.utils.ChatUtils.getMessageText(org.telegram.messenger.MessageObject, org.telegram.messenger.MessageObject$GroupedMessages):java.lang.CharSequence");
    }

    private CharSequence getMessageCaption(MessageObject messageObject, MessageObject.GroupedMessages groupedMessages) {
        String restrictionReason = MessagesController.getInstance(this.selectedAccount).getRestrictionReason(messageObject.messageOwner.restriction_reason);
        if (!TextUtils.isEmpty(restrictionReason)) {
            return restrictionReason;
        }
        if (messageObject.isVoiceTranscriptionOpen() && !TranscribeButton.isTranscribing(messageObject)) {
            return messageObject.getVoiceTranscription();
        }
        CharSequence charSequence = messageObject.caption;
        if (charSequence != null) {
            return charSequence;
        }
        if (groupedMessages == null) {
            return null;
        }
        int size = groupedMessages.messages.size();
        CharSequence charSequence2 = null;
        for (int i = 0; i < size; i++) {
            CharSequence charSequence3 = groupedMessages.messages.get(i).caption;
            if (charSequence3 != null) {
                if (charSequence2 != null) {
                    return null;
                }
                charSequence2 = charSequence3;
            }
        }
        return charSequence2;
    }

    private CharSequence getMessageContent(MessageObject messageObject) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String restrictionReason = MessagesController.getInstance(this.selectedAccount).getRestrictionReason(messageObject.messageOwner.restriction_reason);
        if (!TextUtils.isEmpty(restrictionReason)) {
            spannableStringBuilder.append((CharSequence) restrictionReason);
        } else {
            CharSequence charSequence = messageObject.caption;
            if (charSequence != null) {
                spannableStringBuilder.append(charSequence);
            } else {
                spannableStringBuilder.append(messageObject.messageText);
            }
        }
        return spannableStringBuilder.toString();
    }

    public String getTextFromCallback(byte[] bArr) {
        try {
            return textDecoder.decode(ByteBuffer.wrap(bArr)).toString();
        } catch (CharacterCodingException unused) {
            return Base64.encodeToString(bArr, 3);
        }
    }

    public long getEmojiIdFrom(MessageObject messageObject, TLRPC.User user) {
        MessageObject messageObject2;
        TLRPC.Message message;
        TLRPC.Chat chat;
        if (messageObject == null || messageObject.messageOwner == null || (messageObject2 = messageObject.replyMessageObject) == null || (message = messageObject2.messageOwner) == null || message.from_id == null) {
            return 0L;
        }
        if (DialogObject.isEncryptedDialog(messageObject2.getDialogId())) {
            if (messageObject.replyMessageObject.isOutOwner()) {
                user = getUserConfig().getCurrentUser();
            }
            if (user != null) {
                return UserObject.getEmojiId(user);
            }
            return 0L;
        }
        if (messageObject.replyMessageObject.isFromUser()) {
            TLRPC.User user2 = getMessagesController().getUser(Long.valueOf(messageObject.replyMessageObject.messageOwner.from_id.user_id));
            if (user2 != null) {
                return UserObject.getEmojiId(user2);
            }
            return 0L;
        }
        if (!messageObject.replyMessageObject.isFromChannel() || (chat = getMessagesController().getChat(Long.valueOf(messageObject.replyMessageObject.messageOwner.from_id.channel_id))) == null) {
            return 0L;
        }
        return ChatObject.getEmojiId(chat);
    }

    public TLRPC.InputStickerSet getSetFrom(MessageObject messageObject, TLRPC.User user) {
        return AnimatedEmojiDrawable.findStickerSet(this.selectedAccount, getEmojiIdFrom(messageObject, user));
    }

    public TLRPC.InputStickerSet getSetFrom(TLRPC.User user) {
        return AnimatedEmojiDrawable.findStickerSet(this.selectedAccount, UserObject.getProfileEmojiId(user));
    }

    public TLRPC.InputStickerSet getSetFrom(TLRPC.Chat chat) {
        return AnimatedEmojiDrawable.findStickerSet(this.selectedAccount, ChatObject.getProfileEmojiId(chat));
    }

    public static void uploadImageTemporary(final File file, final Utilities.Callback callback) {
        utilsQueue.postRunnable(new Runnable() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                ChatUtils.m2087$r8$lambda$647etK4MdObE1fIF6qoMoG0PA8(file, callback);
            }
        });
    }

    /* renamed from: $r8$lambda$647etK4M-dObE1fIF6qoMoG0PA8, reason: not valid java name */
    public static /* synthetic */ void m2087$r8$lambda$647etK4MdObE1fIF6qoMoG0PA8(File file, Utilities.Callback callback) {
        try {
            final ImgurUtils.ImgurResponse imgurResponseUploadImage = ImgurUtils.uploadImage(file);
            if (imgurResponseUploadImage != null && !TextUtils.isEmpty(imgurResponseUploadImage.imageUrl)) {
                callback.run(imgurResponseUploadImage.imageUrl);
                utilsQueue.postRunnable(new Runnable() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda11
                    @Override // java.lang.Runnable
                    public final void run() {
                        ChatUtils.m2086$r8$lambda$3VcDUPICV0BeIL71c3i4ZDhNeM(imgurResponseUploadImage);
                    }
                }, 30000L);
            } else {
                callback.run(null);
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    /* renamed from: $r8$lambda$3VcDUPIC-V0BeIL71c3i4ZDhNeM, reason: not valid java name */
    public static /* synthetic */ void m2086$r8$lambda$3VcDUPICV0BeIL71c3i4ZDhNeM(ImgurUtils.ImgurResponse imgurResponse) {
        try {
            if (ImgurUtils.deleteImage(imgurResponse.deleteHash)) {
                return;
            }
            FileLog.m1158e("Unable to delete uploaded image with hash " + imgurResponse.deleteHash + ", URL: " + imgurResponse.imageUrl);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    public String getDC(TLRPC.User user) {
        return getDC(user, null);
    }

    public String getDC(TLRPC.Chat chat) {
        return getDC(null, chat);
    }

    public String getDC(TLRPC.User user, TLRPC.Chat chat) {
        int i;
        int currentDatacenterId = getConnectionsManager().getCurrentDatacenterId();
        if (user != null) {
            if (!UserObject.isUserSelf(user) || currentDatacenterId == -1) {
                TLRPC.UserProfilePhoto userProfilePhoto = user.photo;
                if (userProfilePhoto != null) {
                    i = userProfilePhoto.dc_id;
                    currentDatacenterId = i;
                }
                currentDatacenterId = -1;
            }
        } else if (chat != null) {
            TLRPC.ChatPhoto chatPhoto = chat.photo;
            if (chatPhoto != null) {
                i = chatPhoto.dc_id;
                currentDatacenterId = i;
            }
            currentDatacenterId = -1;
        } else {
            currentDatacenterId = 0;
        }
        return (currentDatacenterId == -1 || currentDatacenterId == 0) ? getDCName(0) : String.format(Locale.ROOT, "DC%d, %s", Integer.valueOf(currentDatacenterId), getDCName(currentDatacenterId));
    }

    public String getName(long j) {
        TLRPC.User user;
        String name = null;
        if (DialogObject.isEncryptedDialog(j)) {
            TLRPC.EncryptedChat encryptedChat = getMessagesController().getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(j)));
            if (encryptedChat != null && (user = getMessagesController().getUser(Long.valueOf(encryptedChat.user_id))) != null) {
                name = ContactsController.formatName(user.first_name, user.last_name);
            }
        } else if (DialogObject.isUserDialog(j)) {
            TLRPC.User user2 = getMessagesController().getUser(Long.valueOf(j));
            if (user2 != null) {
                name = ContactsController.formatName(user2);
            }
        } else {
            TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(-j));
            if (chat != null) {
                name = chat.title;
            }
        }
        return j == getUserConfig().getClientUserId() ? LocaleController.getString(C2369R.string.SavedMessages) : name;
    }

    public void searchUserById(Long l, final Utilities.Callback callback) {
        if (l.longValue() == 0) {
            return;
        }
        TLRPC.User user = getMessagesController().getUser(l);
        if (user != null) {
            callback.run(user);
        } else {
            searchUser(l.longValue(), true, true, new Utilities.Callback() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda2
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    ChatUtils.m2088$r8$lambda$EAlRxhISkKOpErfIbM48cqAts(callback, (TLRPC.User) obj);
                }
            });
        }
    }

    /* renamed from: $r8$lambda$EAlRxhISkKOpErfIbM48-c-qAts, reason: not valid java name */
    public static /* synthetic */ void m2088$r8$lambda$EAlRxhISkKOpErfIbM48cqAts(Utilities.Callback callback, TLRPC.User user) {
        if (user != null && user.access_hash != 0) {
            callback.run(user);
        } else {
            callback.run(null);
        }
    }

    public void sendBotRequest(final String str, final boolean z, final Utilities.Callback callback) {
        Pair apiBotInfo = ExteraConfig.getApiBotInfo();
        Long l = (Long) apiBotInfo.first;
        long jLongValue = l.longValue();
        String str2 = (String) apiBotInfo.second;
        TLRPC.User user = getMessagesController().getUser(l);
        if (user != null) {
            sendInlineBotRequest(user, str, z, new Utilities.Callback() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda16
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$sendBotRequest$3(callback, (TLRPC.messages_BotResults) obj);
                }
            });
        } else {
            resolveUser(str2, jLongValue, new Utilities.Callback() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda17
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$sendBotRequest$5(str, z, callback, (TLRPC.User) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendBotRequest$5(String str, boolean z, final Utilities.Callback callback, TLRPC.User user) {
        if (user != null) {
            sendInlineBotRequest(user, str, z, new Utilities.Callback() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda18
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$sendBotRequest$4(callback, (TLRPC.messages_BotResults) obj);
                }
            });
        } else {
            callback.run(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: processBotResponse, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$sendBotRequest$4(TLRPC.messages_BotResults messages_botresults, Utilities.Callback callback) {
        TLRPC.BotInlineMessage botInlineMessage;
        String str;
        if (messages_botresults != null && !messages_botresults.results.isEmpty() && (botInlineMessage = ((TLRPC.BotInlineResult) messages_botresults.results.get(0)).send_message) != null && (str = botInlineMessage.message) != null) {
            callback.run(str);
        } else {
            callback.run(null);
        }
    }

    public void sendInlineBotRequest(final TLRPC.User user, final String str, final boolean z, final Utilities.Callback callback) {
        if (user == null) {
            callback.run(null);
            return;
        }
        final String str2 = "bot_inline_query_" + user.f1734id + "_" + str;
        RequestDelegate requestDelegate = new RequestDelegate() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda3
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$sendInlineBotRequest$7(z, user, str, callback, str2, tLObject, tL_error);
            }
        };
        if (z) {
            getMessageStorage().getBotCache(str2, requestDelegate);
            return;
        }
        TLRPC.TL_messages_getInlineBotResults tL_messages_getInlineBotResults = new TLRPC.TL_messages_getInlineBotResults();
        tL_messages_getInlineBotResults.query = str;
        tL_messages_getInlineBotResults.bot = getMessagesController().getInputUser(user);
        tL_messages_getInlineBotResults.offset = "";
        tL_messages_getInlineBotResults.peer = new TLRPC.TL_inputPeerEmpty();
        getConnectionsManager().sendRequest(tL_messages_getInlineBotResults, requestDelegate, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendInlineBotRequest$7(final boolean z, final TLRPC.User user, final String str, final Utilities.Callback callback, final String str2, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$sendInlineBotRequest$6(z, tLObject, user, str, callback, str2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendInlineBotRequest$6(boolean z, TLObject tLObject, TLRPC.User user, String str, Utilities.Callback callback, String str2) {
        if (z && (!(tLObject instanceof TLRPC.messages_BotResults) || ((TLRPC.messages_BotResults) tLObject).results.isEmpty())) {
            sendInlineBotRequest(user, str, false, callback);
            return;
        }
        if (tLObject instanceof TLRPC.messages_BotResults) {
            TLRPC.messages_BotResults messages_botresults = (TLRPC.messages_BotResults) tLObject;
            if (!z && messages_botresults.cache_time != 0) {
                getMessageStorage().saveBotCache(str2, messages_botresults);
            }
            callback.run(messages_botresults);
            return;
        }
        callback.run(null);
    }

    private static Pair getBotInfo() throws NumberFormatException {
        String stringConfigValue = RemoteUtils.getStringConfigValue("search_bot", "7424190611:tgdb_search_bot");
        int iIndexOf = stringConfigValue.indexOf(":");
        if (iIndexOf != -1) {
            try {
                long j = Long.parseLong(stringConfigValue.substring(0, iIndexOf));
                return new Pair(Long.valueOf(j), stringConfigValue.substring(iIndexOf + 1));
            } catch (NumberFormatException e) {
                FileLog.m1160e(e);
            }
        }
        return new Pair(7424190611L, "tgdb_search_bot");
    }

    private void searchUser(final long j, boolean z, boolean z2, final Utilities.Callback callback) throws NumberFormatException {
        Pair botInfo = getBotInfo();
        Long l = (Long) botInfo.first;
        long jLongValue = l.longValue();
        TLRPC.User user = getMessagesController().getUser(l);
        if (user != null) {
            sendInlineBotRequest(user, String.valueOf(j), z2, new Utilities.Callback() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda7
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$searchUser$10(callback, (TLRPC.messages_BotResults) obj);
                }
            });
        } else if (z) {
            resolveUser((String) botInfo.second, jLongValue, new Utilities.Callback() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda6
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) throws NumberFormatException {
                    this.f$0.lambda$searchUser$8(j, callback, (TLRPC.User) obj);
                }
            });
        } else {
            callback.run(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchUser$8(long j, Utilities.Callback callback, TLRPC.User user) throws NumberFormatException {
        searchUser(j, false, false, callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchUser$10(final Utilities.Callback callback, TLRPC.messages_BotResults messages_botresults) {
        if (messages_botresults == null || messages_botresults.results.isEmpty()) {
            callback.run(null);
            return;
        }
        TLRPC.BotInlineResult botInlineResult = (TLRPC.BotInlineResult) messages_botresults.results.get(0);
        TLRPC.BotInlineMessage botInlineMessage = botInlineResult.send_message;
        if (botInlineMessage == null || TextUtils.isEmpty(botInlineMessage.message)) {
            callback.run(null);
            return;
        }
        String[] strArrSplit = botInlineResult.send_message.message.split("\n");
        if (strArrSplit.length < 3) {
            callback.run(null);
            return;
        }
        final TLRPC.TL_user tL_user = new TLRPC.TL_user();
        for (String str : strArrSplit) {
            String strTrim = str.replaceAll("\\p{C}", "").trim();
            if (strTrim.startsWith("🆔")) {
                tL_user.f1734id = Utilities.parseLong(strTrim.replaceAll("\\D+", "").trim()).longValue();
            } else if (strTrim.startsWith("📧")) {
                tL_user.username = strTrim.substring(strTrim.indexOf(64) + 1).trim();
            }
        }
        long j = tL_user.f1734id;
        if (j == 0) {
            callback.run(null);
            return;
        }
        String str2 = tL_user.username;
        if (str2 != null) {
            resolveUser(str2, j, new Utilities.Callback() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda12
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    ChatUtils.$r8$lambda$Rldv2nGxfvbPavDkYTIq5Q5tz3g(callback, tL_user, (TLRPC.User) obj);
                }
            });
        } else {
            callback.run(tL_user);
        }
    }

    public static /* synthetic */ void $r8$lambda$Rldv2nGxfvbPavDkYTIq5Q5tz3g(Utilities.Callback callback, TLRPC.TL_user tL_user, TLRPC.User user) {
        if (user != null) {
            callback.run(user);
        } else {
            tL_user.username = null;
            callback.run(tL_user);
        }
    }

    public void resolveUser(String str, final long j, final Utilities.Callback callback) {
        getMessagesController().getUserNameResolver().resolve(str, new Consumer() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda5
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$resolveUser$11(j, callback, (Long) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resolveUser$11(long j, Utilities.Callback callback, Long l) {
        if (l != null && l.longValue() > 0 && l.longValue() == j) {
            callback.run(getMessagesController().getUser(Long.valueOf(j)));
        } else {
            callback.run(null);
        }
    }

    public void searchChatById(long j, Utilities.Callback callback) {
        if (j == 0) {
            callback.run(null);
            return;
        }
        TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(j));
        if (chat != null) {
            callback.run(chat);
            return;
        }
        try {
            searchChat(Long.parseLong("-100" + j), true, true, callback);
        } catch (NumberFormatException unused) {
            callback.run(null);
        }
    }

    private void searchChat(final long j, boolean z, boolean z2, final Utilities.Callback callback) throws NumberFormatException {
        Pair botInfo = getBotInfo();
        Long l = (Long) botInfo.first;
        long jLongValue = l.longValue();
        TLRPC.User user = getMessagesController().getUser(l);
        if (user != null) {
            sendInlineBotRequest(user, String.valueOf(j), z2, new Utilities.Callback() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda1
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$searchChat$14(callback, (TLRPC.messages_BotResults) obj);
                }
            });
        } else if (z) {
            resolveUser((String) botInfo.second, jLongValue, new Utilities.Callback() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) throws NumberFormatException {
                    this.f$0.lambda$searchChat$12(j, callback, (TLRPC.User) obj);
                }
            });
        } else {
            callback.run(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchChat$12(long j, Utilities.Callback callback, TLRPC.User user) throws NumberFormatException {
        searchChat(j, false, false, callback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchChat$14(final Utilities.Callback callback, TLRPC.messages_BotResults messages_botresults) {
        if (messages_botresults == null || messages_botresults.results.isEmpty()) {
            callback.run(null);
            return;
        }
        TLRPC.BotInlineResult botInlineResult = (TLRPC.BotInlineResult) messages_botresults.results.get(0);
        TLRPC.BotInlineMessage botInlineMessage = botInlineResult.send_message;
        if (botInlineMessage == null || TextUtils.isEmpty(botInlineMessage.message)) {
            callback.run(null);
            return;
        }
        String[] strArrSplit = botInlineResult.send_message.message.split("\n");
        if (strArrSplit.length < 1) {
            callback.run(null);
            return;
        }
        final TLRPC.TL_channel tL_channel = new TLRPC.TL_channel();
        for (String str : strArrSplit) {
            String strTrim = str.replaceAll("\\p{C}", "").trim();
            if (strTrim.startsWith("🆔")) {
                try {
                    long jLongValue = Utilities.parseLong(strTrim.replaceAll("[^\\d-]", "")).longValue();
                    if (String.valueOf(jLongValue).startsWith("-100")) {
                        tL_channel.f1571id = Long.parseLong(String.valueOf(jLongValue).substring(4));
                    } else {
                        tL_channel.f1571id = jLongValue;
                    }
                } catch (Exception unused) {
                }
            } else if (strTrim.startsWith("📧")) {
                tL_channel.username = strTrim.substring(strTrim.indexOf(64) + 1).trim();
            }
        }
        if (tL_channel.f1571id == 0) {
            callback.run(null);
            return;
        }
        String str2 = tL_channel.username;
        if (str2 != null) {
            resolveChannel(str2, new Utilities.Callback() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda4
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    callback.run((TLRPC.Chat) Objects.requireNonNullElse((TLRPC.Chat) obj, tL_channel));
                }
            });
        } else {
            callback.run(tL_channel);
        }
    }

    public MessagesController getMessagesController() {
        return MessagesController.getInstance(this.selectedAccount);
    }

    public MessagesStorage getMessageStorage() {
        return MessagesStorage.getInstance(this.selectedAccount);
    }

    public ConnectionsManager getConnectionsManager() {
        return ConnectionsManager.getInstance(this.selectedAccount);
    }

    public FileLoader getFileLoader() {
        return FileLoader.getInstance(this.selectedAccount);
    }

    public UserConfig getUserConfig() {
        return UserConfig.getInstance(this.selectedAccount);
    }

    public void addMessageToClipboard(MessageObject messageObject, Runnable runnable) {
        String pathToMessage = getPathToMessage(messageObject);
        if (TextUtils.isEmpty(pathToMessage)) {
            return;
        }
        SystemUtils.addFileToClipboard(new File(pathToMessage), runnable);
    }

    public String getPathToMessage(MessageObject messageObject) {
        if (messageObject == null) {
            return null;
        }
        TLRPC.Message message = messageObject.messageOwner;
        if (message != null && !TextUtils.isEmpty(message.attachPath)) {
            String str = messageObject.messageOwner.attachPath;
            if (fileExists(str)) {
                return str;
            }
        }
        if (messageObject.messageOwner != null) {
            String string = getFileLoader().getPathToMessage(messageObject.messageOwner).toString();
            if (fileExists(string)) {
                return string;
            }
        }
        if (messageObject.getDocument() != null) {
            String string2 = getFileLoader().getPathToAttach(messageObject.getDocument(), true).toString();
            if (fileExists(string2)) {
                return string2;
            }
        }
        String existingPath = AyuAttachments.getInstance(this.selectedAccount).getExistingPath(messageObject, false);
        if (fileExists(existingPath)) {
            return existingPath;
        }
        return null;
    }

    public boolean canSaveSticker(MessageObject messageObject) {
        return MessageObject.isStickerDocument(messageObject.getDocument()) || isEmoji(messageObject.getDocument());
    }

    public boolean isEmoji(TLRPC.Document document) {
        return MessageObject.isAnimatedEmoji(document) && !MessageObject.isAnimatedStickerDocument(document, false);
    }

    public void saveStickerToGallery(Activity activity, MessageObject messageObject, Utilities.Callback callback) {
        saveStickerToGallery(activity, getPathToMessage(messageObject), messageObject.isVideoSticker(), callback);
    }

    public void saveStickerToGallery(Activity activity, TLRPC.Document document, Utilities.Callback callback) {
        String string = getFileLoader().getPathToAttach(document, true).toString();
        if (new File(string).exists()) {
            saveStickerToGallery(activity, string, MessageObject.isVideoSticker(document), callback);
        }
    }

    public void saveStickerToGallery(final Activity activity, final String str, final boolean z, final Utilities.Callback callback) {
        utilsQueue.postRunnable(new Runnable() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                ChatUtils.$r8$lambda$a34EikQmis9SdJXgH3yL5_Zr3Zo(str, z, activity, callback);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$a34EikQmis9SdJXgH3yL5_Zr3Zo(final String str, boolean z, final Activity activity, final Utilities.Callback callback) throws IOException {
        String strReplace;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            FileLog.m1158e(str);
            if (z) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaController.saveFile(str, activity, 1, null, null, callback);
                    }
                });
                return;
            }
            Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(str);
            if (bitmapDecodeFile != null) {
                if (str.endsWith(".webp")) {
                    strReplace = str.replace(".webp", ".png");
                } else {
                    strReplace = str + ".png";
                }
                final File file = new File(strReplace);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                bitmapDecodeFile.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.close();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        MediaController.saveFile(file.toString(), activity, 0, null, null, callback);
                    }
                });
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    public void resolveChannel(String str, final Utilities.Callback callback) {
        getMessagesController().getUserNameResolver().resolve(str, new Consumer() { // from class: com.exteragram.messenger.utils.ChatUtils$$ExternalSyntheticLambda13
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$resolveChannel$18(callback, (Long) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$resolveChannel$18(Utilities.Callback callback, Long l) {
        if (l != null && l.longValue() < 0) {
            callback.run(getMessagesController().getChat(Long.valueOf(-l.longValue())));
        } else {
            callback.run(null);
        }
    }

    public boolean hasArchivedChats() {
        return getMessagesController().dialogs_dict.get(DialogObject.makeFolderDialogId(1)) != null;
    }

    public long getLikeDialog() {
        return ExteraConfig.preferences.getLong("channelToSave" + this.selectedAccount, getUserConfig().getClientUserId());
    }

    public void setLikeDialog(long j) {
        ExteraConfig.editor.putLong("channelToSave" + this.selectedAccount, j).apply();
    }

    private boolean isPhoneStartsWith(String str) {
        TLRPC.User currentUser = UserConfig.getInstance(this.selectedAccount).getCurrentUser();
        if (currentUser == null || TextUtils.isEmpty(currentUser.phone)) {
            return false;
        }
        return currentUser.phone.startsWith(str);
    }

    public boolean isRussianUser() {
        return isPhoneStartsWith("7");
    }

    public boolean isFragmentUser() {
        return isPhoneStartsWith("888");
    }

    public boolean shouldAddTimestamp(MessageObject messageObject, CharSequence charSequence) {
        TLRPC.Message message = messageObject.messageOwner;
        if (message == null) {
            return false;
        }
        return ((messageObject.currentEvent == null && message.action == null) || TextUtils.isEmpty(charSequence)) ? false : true;
    }

    public CharSequence addTimestamp(CharSequence charSequence, long j, Theme.ResourcesProvider resourcesProvider) {
        String str = LocaleController.getInstance().getFormatterDay().format(j * 1000);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        ProfileActivity.ShowDrawable showDrawableFindDrawable = ChannelAdminLogActivity.findDrawable(charSequence);
        if (showDrawableFindDrawable == null) {
            showDrawableFindDrawable = new ProfileActivity.ShowDrawable(str);
            showDrawableFindDrawable.textDrawable.setTypeface(AndroidUtilities.bold());
            showDrawableFindDrawable.textDrawable.setTextSize(AndroidUtilities.m1146dp(10.0f));
            showDrawableFindDrawable.setTextColor(Theme.getThemePaint("paintChatActionText", resourcesProvider).getColor());
            showDrawableFindDrawable.setBackgroundColor(503316480);
        } else {
            showDrawableFindDrawable.textDrawable.setText(str, false);
        }
        showDrawableFindDrawable.setBounds(0, 0, showDrawableFindDrawable.getIntrinsicWidth(), showDrawableFindDrawable.getIntrinsicHeight());
        spannableStringBuilder.append((CharSequence) " S");
        spannableStringBuilder.setSpan(new ColoredImageSpan(showDrawableFindDrawable), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 33);
        return spannableStringBuilder;
    }
}
