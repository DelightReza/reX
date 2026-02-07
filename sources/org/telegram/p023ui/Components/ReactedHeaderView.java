package org.telegram.p023ui.Components;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;
import com.radolyn.ayugram.controllers.AyuFilterController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.Vector;

/* loaded from: classes6.dex */
public class ReactedHeaderView extends FrameLayout {
    private AvatarsImageView avatarsImageView;
    private int currentAccount;
    private long dialogId;
    private int fixedWidth;
    private FlickerLoadingView flickerLoadingView;
    private ImageView iconView;
    private boolean ignoreLayout;
    private boolean isLoaded;
    private MessageObject message;
    private BackupImageView reactView;
    private Consumer seenCallback;
    private List seenUsers;
    private TextView titleView;
    private List users;

    public ReactedHeaderView(Context context, int i, MessageObject messageObject, long j) {
        super(context);
        this.seenUsers = new ArrayList();
        this.users = new ArrayList();
        this.currentAccount = i;
        this.message = messageObject;
        this.dialogId = j;
        FlickerLoadingView flickerLoadingView = new FlickerLoadingView(context);
        this.flickerLoadingView = flickerLoadingView;
        flickerLoadingView.setColors(Theme.key_actionBarDefaultSubmenuBackground, Theme.key_listSelector, -1);
        this.flickerLoadingView.setViewType(13);
        this.flickerLoadingView.setIsSingleCell(false);
        addView(this.flickerLoadingView, LayoutHelper.createFrame(-2, -1.0f));
        TextView textView = new TextView(context);
        this.titleView = textView;
        textView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
        this.titleView.setTextSize(1, 16.0f);
        this.titleView.setLines(1);
        this.titleView.setEllipsize(TextUtils.TruncateAt.END);
        addView(this.titleView, LayoutHelper.createFrameRelatively(-2.0f, -2.0f, 8388627, 40.0f, 0.0f, 62.0f, 0.0f));
        AvatarsImageView avatarsImageView = new AvatarsImageView(context, false);
        this.avatarsImageView = avatarsImageView;
        avatarsImageView.setStyle(11);
        this.avatarsImageView.setAvatarsTextSize(AndroidUtilities.m1146dp(22.0f));
        addView(this.avatarsImageView, LayoutHelper.createFrameRelatively(56.0f, -1.0f, 8388629, 0.0f, 0.0f, 0.0f, 0.0f));
        ImageView imageView = new ImageView(context);
        this.iconView = imageView;
        addView(imageView, LayoutHelper.createFrameRelatively(24.0f, 24.0f, 8388627, 11.0f, 0.0f, 0.0f, 0.0f));
        Drawable drawableMutate = ContextCompat.getDrawable(context, C2369R.drawable.msg_reactions).mutate();
        drawableMutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_actionBarDefaultSubmenuItemIcon), PorterDuff.Mode.MULTIPLY));
        this.iconView.setImageDrawable(drawableMutate);
        this.iconView.setVisibility(8);
        BackupImageView backupImageView = new BackupImageView(context);
        this.reactView = backupImageView;
        addView(backupImageView, LayoutHelper.createFrameRelatively(24.0f, 24.0f, 8388627, 11.0f, 0.0f, 0.0f, 0.0f));
        this.titleView.setAlpha(0.0f);
        this.avatarsImageView.setAlpha(0.0f);
        setBackground(Theme.getSelectorDrawable(false));
    }

    public void setSeenCallback(Consumer consumer) {
        this.seenCallback = consumer;
    }

    public static class UserSeen {
        public int date;
        long dialogId;
        public TLObject user;

        public UserSeen(TLObject tLObject, int i) {
            this.user = tLObject;
            this.date = i;
            if (tLObject instanceof TLRPC.User) {
                this.dialogId = ((TLRPC.User) tLObject).f1734id;
            } else if (tLObject instanceof TLRPC.Chat) {
                this.dialogId = -((TLRPC.Chat) tLObject).f1571id;
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isLoaded) {
            return;
        }
        MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
        final TLRPC.Chat chat = messagesController.getChat(Long.valueOf(this.message.getChatId()));
        TLRPC.ChatFull chatFull = messagesController.getChatFull(this.message.getChatId());
        if (chat != null && this.message.isOutOwner() && this.message.isSent() && !this.message.isEditing() && !this.message.isSending() && !this.message.isSendError() && !this.message.isContentUnread() && !this.message.isUnread() && ConnectionsManager.getInstance(this.currentAccount).getCurrentTime() - this.message.messageOwner.date < 604800 && ((ChatObject.isMegagroup(chat) || !ChatObject.isChannel(chat)) && chatFull != null && chatFull.participants_count <= MessagesController.getInstance(this.currentAccount).chatReadMarkSizeThreshold && !(this.message.messageOwner.action instanceof TLRPC.TL_messageActionChatJoinedByRequest))) {
            TLRPC.TL_messages_getMessageReadParticipants tL_messages_getMessageReadParticipants = new TLRPC.TL_messages_getMessageReadParticipants();
            tL_messages_getMessageReadParticipants.msg_id = this.message.getId();
            tL_messages_getMessageReadParticipants.peer = MessagesController.getInstance(this.currentAccount).getInputPeer(this.message.getDialogId());
            TLRPC.Peer peer = this.message.messageOwner.from_id;
            final long j = peer != null ? peer.user_id : 0L;
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_messages_getMessageReadParticipants, new RequestDelegate() { // from class: org.telegram.ui.Components.ReactedHeaderView$$ExternalSyntheticLambda0
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$onAttachedToWindow$5(j, chat, tLObject, tL_error);
                }
            }, 64);
            return;
        }
        loadReactions();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$5(long j, TLRPC.Chat chat, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject instanceof Vector) {
            final ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = ((Vector) tLObject).objects;
            int size = arrayList3.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList3.get(i);
                i++;
                if (obj instanceof Long) {
                    Long l = (Long) obj;
                    if (j != l.longValue()) {
                        arrayList.add(l);
                        arrayList2.add(0);
                    }
                } else if (obj instanceof TLRPC.TL_readParticipantDate) {
                    TLRPC.TL_readParticipantDate tL_readParticipantDate = (TLRPC.TL_readParticipantDate) obj;
                    long j2 = tL_readParticipantDate.user_id;
                    int i2 = tL_readParticipantDate.date;
                    if (j != j2) {
                        arrayList.add(Long.valueOf(j2));
                        arrayList2.add(Integer.valueOf(i2));
                    }
                }
            }
            arrayList.add(Long.valueOf(j));
            arrayList2.add(0);
            final ArrayList arrayList4 = new ArrayList();
            final Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.ReactedHeaderView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAttachedToWindow$0(arrayList4);
                }
            };
            if (ChatObject.isChannel(chat)) {
                TLRPC.TL_channels_getParticipants tL_channels_getParticipants = new TLRPC.TL_channels_getParticipants();
                tL_channels_getParticipants.limit = MessagesController.getInstance(this.currentAccount).chatReadMarkSizeThreshold;
                tL_channels_getParticipants.offset = 0;
                tL_channels_getParticipants.filter = new TLRPC.TL_channelParticipantsRecent();
                tL_channels_getParticipants.channel = MessagesController.getInstance(this.currentAccount).getInputChannel(chat.f1571id);
                ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_channels_getParticipants, new RequestDelegate() { // from class: org.telegram.ui.Components.ReactedHeaderView$$ExternalSyntheticLambda3
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject2, TLRPC.TL_error tL_error2) {
                        this.f$0.lambda$onAttachedToWindow$2(arrayList, arrayList4, arrayList2, runnable, tLObject2, tL_error2);
                    }
                });
                return;
            }
            TLRPC.TL_messages_getFullChat tL_messages_getFullChat = new TLRPC.TL_messages_getFullChat();
            tL_messages_getFullChat.chat_id = chat.f1571id;
            ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_messages_getFullChat, new RequestDelegate() { // from class: org.telegram.ui.Components.ReactedHeaderView$$ExternalSyntheticLambda4
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject2, TLRPC.TL_error tL_error2) {
                    this.f$0.lambda$onAttachedToWindow$4(arrayList, arrayList4, arrayList2, runnable, tLObject2, tL_error2);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$0(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            UserSeen userSeen = (UserSeen) it.next();
            if (!AyuFilterController.getInstance(this.currentAccount).isBlocked(MessageObject.getObjectPeerId(userSeen.user))) {
                this.seenUsers.add(userSeen);
                int i = 0;
                while (true) {
                    if (i < this.users.size()) {
                        if (AyuFilterController.getInstance(this.currentAccount).isBlocked(MessageObject.getObjectPeerId(((UserSeen) this.users.get(i)).user)) || MessageObject.getObjectPeerId(((UserSeen) this.users.get(i)).user) != MessageObject.getObjectPeerId(userSeen.user)) {
                            i++;
                        } else if (userSeen.date > 0) {
                            ((UserSeen) this.users.get(i)).date = userSeen.date;
                        }
                    } else {
                        this.users.add(userSeen);
                        break;
                    }
                }
            }
        }
        Consumer consumer = this.seenCallback;
        if (consumer != null) {
            consumer.accept(list);
        }
        loadReactions();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$2(final List list, final List list2, final List list3, final Runnable runnable, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ReactedHeaderView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onAttachedToWindow$1(tLObject, list, list2, list3, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$1(TLObject tLObject, List list, List list2, List list3, Runnable runnable) {
        if (tLObject != null) {
            TLRPC.TL_channels_channelParticipants tL_channels_channelParticipants = (TLRPC.TL_channels_channelParticipants) tLObject;
            for (int i = 0; i < tL_channels_channelParticipants.users.size(); i++) {
                TLRPC.User user = (TLRPC.User) tL_channels_channelParticipants.users.get(i);
                MessagesController.getInstance(this.currentAccount).putUser(user, false);
                int iIndexOf = list.indexOf(Long.valueOf(user.f1734id));
                if (!user.self && iIndexOf >= 0) {
                    list2.add(new UserSeen(user, ((Integer) list3.get(iIndexOf)).intValue()));
                }
            }
        }
        runnable.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$4(final List list, final List list2, final List list3, final Runnable runnable, final TLObject tLObject, TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.ReactedHeaderView$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onAttachedToWindow$3(tLObject, list, list2, list3, runnable);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAttachedToWindow$3(TLObject tLObject, List list, List list2, List list3, Runnable runnable) {
        if (tLObject != null) {
            TLRPC.TL_messages_chatFull tL_messages_chatFull = (TLRPC.TL_messages_chatFull) tLObject;
            for (int i = 0; i < tL_messages_chatFull.users.size(); i++) {
                TLRPC.User user = (TLRPC.User) tL_messages_chatFull.users.get(i);
                MessagesController.getInstance(this.currentAccount).putUser(user, false);
                int iIndexOf = list.indexOf(Long.valueOf(user.f1734id));
                if (!user.self && iIndexOf >= 0) {
                    list2.add(new UserSeen(user, ((Integer) list3.get(iIndexOf)).intValue()));
                }
            }
        }
        runnable.run();
    }

    private void loadReactions() {
        MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
        TLRPC.TL_messages_getMessageReactionsList tL_messages_getMessageReactionsList = new TLRPC.TL_messages_getMessageReactionsList();
        tL_messages_getMessageReactionsList.peer = messagesController.getInputPeer(this.message.getDialogId());
        tL_messages_getMessageReactionsList.f1671id = this.message.getId();
        tL_messages_getMessageReactionsList.limit = 3;
        tL_messages_getMessageReactionsList.reaction = null;
        tL_messages_getMessageReactionsList.offset = null;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_messages_getMessageReactionsList, new RequestDelegate() { // from class: org.telegram.ui.Components.ReactedHeaderView$$ExternalSyntheticLambda1
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$loadReactions$7(tLObject, tL_error);
            }
        }, 64);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadReactions$7(TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject instanceof TLRPC.TL_messages_messageReactionsList) {
            final TLRPC.TL_messages_messageReactionsList tL_messages_messageReactionsList = (TLRPC.TL_messages_messageReactionsList) tLObject;
            final int i = tL_messages_messageReactionsList.count;
            tL_messages_messageReactionsList.users.size();
            post(new Runnable() { // from class: org.telegram.ui.Components.ReactedHeaderView$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$loadReactions$6(i, tL_messages_messageReactionsList);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadReactions$6(int i, TLRPC.TL_messages_messageReactionsList tL_messages_messageReactionsList) {
        String pluralString;
        TLRPC.Peer peer;
        if (this.seenUsers.isEmpty() || this.seenUsers.size() < i) {
            pluralString = LocaleController.formatPluralString("ReactionsCount", i, new Object[0]);
        } else {
            pluralString = String.format(LocaleController.getPluralString("Reacted", i), i == this.seenUsers.size() ? String.valueOf(i) : i + "/" + this.seenUsers.size());
        }
        if (getMeasuredWidth() > 0) {
            this.fixedWidth = getMeasuredWidth();
        }
        this.titleView.setText(pluralString);
        TLRPC.TL_messageReactions tL_messageReactions = this.message.messageOwner.reactions;
        if (tL_messageReactions != null && tL_messageReactions.results.size() == 1 && !tL_messages_messageReactionsList.reactions.isEmpty()) {
            for (TLRPC.TL_availableReaction tL_availableReaction : MediaDataController.getInstance(this.currentAccount).getReactionsList()) {
                if (tL_availableReaction.reaction.equals(((TLRPC.MessagePeerReaction) tL_messages_messageReactionsList.reactions.get(0)).reaction)) {
                    this.reactView.setImage(ImageLocation.getForDocument(tL_availableReaction.center_icon), "40_40_lastreactframe", "webp", (Drawable) null, tL_availableReaction);
                    this.reactView.setVisibility(0);
                    this.reactView.setAlpha(0.0f);
                    this.reactView.animate().alpha(1.0f).start();
                    this.iconView.setVisibility(8);
                    break;
                }
            }
            this.iconView.setVisibility(0);
            this.iconView.setAlpha(0.0f);
            this.iconView.animate().alpha(1.0f).start();
        } else {
            this.iconView.setVisibility(0);
            this.iconView.setAlpha(0.0f);
            this.iconView.animate().alpha(1.0f).start();
        }
        ArrayList arrayList = tL_messages_messageReactionsList.users;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            TLRPC.User user = (TLRPC.User) obj;
            if (!AyuFilterController.getInstance(this.currentAccount).isBlocked(user.f1734id) && (peer = this.message.messageOwner.from_id) != null && user.f1734id != peer.user_id) {
                int i3 = 0;
                while (true) {
                    if (i3 >= this.users.size()) {
                        this.users.add(new UserSeen(user, 0));
                        break;
                    } else if (((UserSeen) this.users.get(i3)).dialogId == user.f1734id) {
                        break;
                    } else {
                        i3++;
                    }
                }
            }
        }
        ArrayList arrayList2 = tL_messages_messageReactionsList.chats;
        int size2 = arrayList2.size();
        int i4 = 0;
        while (i4 < size2) {
            Object obj2 = arrayList2.get(i4);
            i4++;
            TLRPC.Chat chat = (TLRPC.Chat) obj2;
            TLRPC.Peer peer2 = this.message.messageOwner.from_id;
            if (peer2 != null && chat.f1571id != peer2.user_id) {
                int i5 = 0;
                while (true) {
                    if (i5 >= this.users.size()) {
                        this.users.add(new UserSeen(chat, 0));
                        break;
                    } else if (((UserSeen) this.users.get(i5)).dialogId == (-chat.f1571id)) {
                        break;
                    } else {
                        i5++;
                    }
                }
            }
        }
        updateView();
    }

    public List<UserSeen> getSeenUsers() {
        return this.seenUsers;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void updateView() {
        /*
            r6 = this;
            java.util.List r0 = r6.users
            int r0 = r0.size()
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto Lc
            r0 = 1
            goto Ld
        Lc:
            r0 = 0
        Ld:
            r6.setEnabled(r0)
            r0 = 0
        L11:
            r3 = 3
            if (r0 >= r3) goto L39
            java.util.List r3 = r6.users
            int r3 = r3.size()
            if (r0 >= r3) goto L2e
            org.telegram.ui.Components.AvatarsImageView r3 = r6.avatarsImageView
            int r4 = r6.currentAccount
            java.util.List r5 = r6.users
            java.lang.Object r5 = r5.get(r0)
            org.telegram.ui.Components.ReactedHeaderView$UserSeen r5 = (org.telegram.ui.Components.ReactedHeaderView.UserSeen) r5
            org.telegram.tgnet.TLObject r5 = r5.user
            r3.setObject(r0, r4, r5)
            goto L36
        L2e:
            org.telegram.ui.Components.AvatarsImageView r3 = r6.avatarsImageView
            int r4 = r6.currentAccount
            r5 = 0
            r3.setObject(r0, r4, r5)
        L36:
            int r0 = r0 + 1
            goto L11
        L39:
            java.util.List r0 = r6.users
            int r0 = r0.size()
            r3 = 1094713344(0x41400000, float:12.0)
            r4 = 0
            if (r0 == r2) goto L4f
            r2 = 2
            if (r0 == r2) goto L49
            r0 = 0
            goto L56
        L49:
            int r0 = org.telegram.messenger.AndroidUtilities.m1146dp(r3)
        L4d:
            float r0 = (float) r0
            goto L56
        L4f:
            r0 = 1103101952(0x41c00000, float:24.0)
            int r0 = org.telegram.messenger.AndroidUtilities.m1146dp(r0)
            goto L4d
        L56:
            org.telegram.ui.Components.AvatarsImageView r2 = r6.avatarsImageView
            boolean r5 = org.telegram.messenger.LocaleController.isRTL
            if (r5 == 0) goto L61
            int r0 = org.telegram.messenger.AndroidUtilities.m1146dp(r3)
            float r0 = (float) r0
        L61:
            r2.setTranslationX(r0)
            org.telegram.ui.Components.AvatarsImageView r0 = r6.avatarsImageView
            r0.commitTransition(r1)
            android.widget.TextView r0 = r6.titleView
            android.view.ViewPropertyAnimator r0 = r0.animate()
            r1 = 1065353216(0x3f800000, float:1.0)
            android.view.ViewPropertyAnimator r0 = r0.alpha(r1)
            r2 = 220(0xdc, double:1.087E-321)
            android.view.ViewPropertyAnimator r0 = r0.setDuration(r2)
            r0.start()
            org.telegram.ui.Components.AvatarsImageView r0 = r6.avatarsImageView
            android.view.ViewPropertyAnimator r0 = r0.animate()
            android.view.ViewPropertyAnimator r0 = r0.alpha(r1)
            android.view.ViewPropertyAnimator r0 = r0.setDuration(r2)
            r0.start()
            org.telegram.ui.Components.FlickerLoadingView r0 = r6.flickerLoadingView
            android.view.ViewPropertyAnimator r0 = r0.animate()
            android.view.ViewPropertyAnimator r0 = r0.alpha(r4)
            android.view.ViewPropertyAnimator r0 = r0.setDuration(r2)
            org.telegram.ui.Components.HideViewAfterAnimation r1 = new org.telegram.ui.Components.HideViewAfterAnimation
            org.telegram.ui.Components.FlickerLoadingView r2 = r6.flickerLoadingView
            r1.<init>(r2)
            android.view.ViewPropertyAnimator r0 = r0.setListener(r1)
            r0.start()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.ReactedHeaderView.updateView():void");
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.ignoreLayout) {
            return;
        }
        super.requestLayout();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3 = this.fixedWidth;
        if (i3 > 0) {
            i = View.MeasureSpec.makeMeasureSpec(i3, TLObject.FLAG_30);
        }
        if (this.flickerLoadingView.getVisibility() == 0) {
            this.ignoreLayout = true;
            this.flickerLoadingView.setVisibility(8);
            super.onMeasure(i, i2);
            this.flickerLoadingView.getLayoutParams().width = getMeasuredWidth();
            this.flickerLoadingView.setVisibility(0);
            this.ignoreLayout = false;
            super.onMeasure(i, i2);
            return;
        }
        super.onMeasure(i, i2);
    }
}
