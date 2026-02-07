package org.telegram.p023ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import java.util.ArrayList;
import org.telegram.SQLite.SQLiteCursor;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.DialogCell;
import org.telegram.p023ui.Components.AnimatedFloat;
import org.telegram.p023ui.Components.AnimatedTextView;
import org.telegram.p023ui.Components.ClickableAnimatedTextView;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.LoadingDrawable;
import org.telegram.p023ui.Stories.StoriesController;
import org.telegram.p023ui.Stories.StoriesListPlaceProvider;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_stories;

/* loaded from: classes5.dex */
public abstract class ProfileChannelCell extends FrameLayout {
    public final DialogCell dialogCell;
    private boolean loading;
    private AnimatedFloat loadingAlpha;
    private final LoadingDrawable loadingDrawable;
    private final Theme.ResourcesProvider resourcesProvider;
    private boolean set;
    private final AnimatedTextView subscribersView;

    public abstract int processColor(int i);

    public ProfileChannelCell(final BaseFragment baseFragment) {
        super(baseFragment.getContext());
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        this.loadingAlpha = new AnimatedFloat(320L, cubicBezierInterpolator);
        this.set = false;
        final Context context = baseFragment.getContext();
        Theme.ResourcesProvider resourceProvider = baseFragment.getResourceProvider();
        this.resourcesProvider = resourceProvider;
        ClickableAnimatedTextView clickableAnimatedTextView = new ClickableAnimatedTextView(context) { // from class: org.telegram.ui.Cells.ProfileChannelCell.1
            @Override // android.view.View
            protected void onSizeChanged(int i, int i2, int i3, int i4) {
                super.onSizeChanged(i, i2, i3, i4);
                DialogCell dialogCell = ProfileChannelCell.this.dialogCell;
                if (dialogCell == null || i <= 0) {
                    return;
                }
                int i5 = dialogCell.namePaddingEnd;
                dialogCell.namePaddingEnd = i;
                if (i5 != i) {
                    dialogCell.buildLayout();
                    ProfileChannelCell.this.dialogCell.invalidate();
                }
            }
        };
        this.subscribersView = clickableAnimatedTextView;
        clickableAnimatedTextView.getDrawable().setHacks(true, true, true);
        clickableAnimatedTextView.setAnimationProperties(0.3f, 0L, 165L, cubicBezierInterpolator);
        clickableAnimatedTextView.setTypeface(AndroidUtilities.bold());
        clickableAnimatedTextView.setTextSize(AndroidUtilities.m1146dp(11.0f));
        clickableAnimatedTextView.setPadding(AndroidUtilities.m1146dp(5.33f), 0, AndroidUtilities.m1146dp(5.33f), 0);
        clickableAnimatedTextView.setGravity(3);
        clickableAnimatedTextView.setTag(null);
        addView(clickableAnimatedTextView, LayoutHelper.createFrame(-2, 17, 51));
        DialogCell dialogCell = new DialogCell(null, context, false, true, UserConfig.selectedAccount, resourceProvider);
        this.dialogCell = dialogCell;
        dialogCell.setBackgroundColor(0);
        dialogCell.setDialogCellDelegate(new DialogCell.DialogCellDelegate() { // from class: org.telegram.ui.Cells.ProfileChannelCell.2
            @Override // org.telegram.ui.Cells.DialogCell.DialogCellDelegate
            public boolean canClickButtonInside() {
                return true;
            }

            @Override // org.telegram.ui.Cells.DialogCell.DialogCellDelegate
            public void onButtonClicked(DialogCell dialogCell2) {
            }

            @Override // org.telegram.ui.Cells.DialogCell.DialogCellDelegate
            public void onButtonLongPress(DialogCell dialogCell2) {
            }

            @Override // org.telegram.ui.Cells.DialogCell.DialogCellDelegate
            public void showChatPreview(DialogCell dialogCell2) {
            }

            @Override // org.telegram.ui.Cells.DialogCell.DialogCellDelegate
            public void openStory(DialogCell dialogCell2, Runnable runnable) {
                if (baseFragment.getMessagesController().getStoriesController().hasStories(dialogCell2.getDialogId())) {
                    baseFragment.getOrCreateStoryViewer().doOnAnimationReady(runnable);
                    baseFragment.getOrCreateStoryViewer().open(baseFragment.getContext(), dialogCell2.getDialogId(), StoriesListPlaceProvider.m1333of(ProfileChannelCell.this));
                }
            }

            @Override // org.telegram.ui.Cells.DialogCell.DialogCellDelegate
            public void openHiddenStories() {
                StoriesController storiesController = baseFragment.getMessagesController().getStoriesController();
                if (storiesController.getHiddenList().isEmpty()) {
                    return;
                }
                boolean z = storiesController.getUnreadState(DialogObject.getPeerDialogId(((TL_stories.PeerStories) storiesController.getHiddenList().get(0)).peer)) != 0;
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < storiesController.getHiddenList().size(); i++) {
                    long peerDialogId = DialogObject.getPeerDialogId(((TL_stories.PeerStories) storiesController.getHiddenList().get(i)).peer);
                    if (!z || storiesController.getUnreadState(peerDialogId) != 0) {
                        arrayList.add(Long.valueOf(peerDialogId));
                    }
                }
                baseFragment.getOrCreateStoryViewer().open(context, null, arrayList, 0, null, null, StoriesListPlaceProvider.m1333of(ProfileChannelCell.this), false);
            }
        });
        dialogCell.isForChannelSubscriberCell = true;
        dialogCell.avatarStart = 15;
        dialogCell.messagePaddingStart = 83;
        addView(dialogCell, LayoutHelper.createFrame(-1, -2.0f, 55, 0.0f, 4.0f, 0.0f, 0.0f));
        updateColors();
        setWillNotDraw(false);
        LoadingDrawable loadingDrawable = new LoadingDrawable();
        this.loadingDrawable = loadingDrawable;
        int i = Theme.key_listSelector;
        loadingDrawable.setColors(Theme.multAlpha(Theme.getColor(i, resourceProvider), 1.25f), Theme.multAlpha(Theme.getColor(i, resourceProvider), 0.8f));
        loadingDrawable.setRadiiDp(8.0f);
        updatePosition();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.subscribersView.getLeft() == 0) {
            this.subscribersView.layout(0, 0, 0, 0);
        }
    }

    private void updatePosition() {
        int iM1146dp = AndroidUtilities.m1146dp((this.dialogCell.useForceThreeLines || SharedConfig.useThreeLinesLayout) ? 10.0f : 13.0f);
        DialogCell dialogCell = this.dialogCell;
        if (((!dialogCell.useForceThreeLines && !SharedConfig.useThreeLinesLayout) || dialogCell.isForumCell()) && this.dialogCell.hasTags()) {
            iM1146dp -= AndroidUtilities.m1146dp(this.dialogCell.isForumCell() ? 8.0f : 9.0f);
        }
        if (this.dialogCell.nameLayout != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.subscribersView.getLayoutParams();
            int i = layoutParams.leftMargin;
            int i2 = layoutParams.topMargin;
            layoutParams.topMargin = iM1146dp + ((FrameLayout.LayoutParams) this.dialogCell.getLayoutParams()).topMargin;
            DialogCell dialogCell2 = this.dialogCell;
            int iM1146dp2 = dialogCell2.nameAdditionalsForChannelSubscriber;
            if (iM1146dp2 == 0) {
                if (!LocaleController.isRTL && dialogCell2.nameLayout.getLineLeft(0) <= 0.0f && AndroidUtilities.charSequenceContains(this.dialogCell.nameLayout.getText(), "…")) {
                    iM1146dp2 = AndroidUtilities.m1146dp(-12.0f);
                } else {
                    iM1146dp2 = AndroidUtilities.m1146dp(4.0f);
                }
            }
            if (LocaleController.isRTL) {
                layoutParams.leftMargin = (int) (((r0.nameLeft + this.dialogCell.nameLayoutTranslateX) - r0.namePaddingEnd) - iM1146dp2);
            } else {
                DialogCell dialogCell3 = this.dialogCell;
                float lineRight = dialogCell3.channelShouldUseLineWidth ? dialogCell3.nameLayout.getLineRight(0) : dialogCell3.nameWidth;
                layoutParams.leftMargin = (int) (r6.nameLeft + this.dialogCell.nameLayoutTranslateX + ((int) lineRight) + iM1146dp2);
            }
            this.subscribersView.setVisibility(0);
            int i3 = layoutParams.leftMargin;
            if (i3 != i || layoutParams.topMargin != i2) {
                this.subscribersView.requestLayout();
            } else if (i3 == 0) {
                this.subscribersView.postInvalidate();
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        updatePosition();
        super.dispatchDraw(canvas);
        float f = this.loadingAlpha.set(this.loading);
        if (f > 0.0f) {
            this.loadingDrawable.setAlpha((int) (f * 255.0f));
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(this.dialogCell.getX() + AndroidUtilities.m1146dp(this.dialogCell.messagePaddingStart + 6), this.dialogCell.getY() + AndroidUtilities.m1146dp(38.0f), this.dialogCell.getX() + AndroidUtilities.m1146dp(this.dialogCell.messagePaddingStart + 6) + (getWidth() * 0.5f), this.dialogCell.getY() + AndroidUtilities.m1146dp(46.33f));
            this.loadingDrawable.setBounds(rectF);
            this.loadingDrawable.draw(canvas);
            rectF.set(this.dialogCell.getX() + AndroidUtilities.m1146dp(this.dialogCell.messagePaddingStart + 6), this.dialogCell.getY() + AndroidUtilities.m1146dp(56.0f), this.dialogCell.getX() + AndroidUtilities.m1146dp(this.dialogCell.messagePaddingStart + 6) + (getWidth() * 0.36f), this.dialogCell.getY() + AndroidUtilities.m1146dp(64.33f));
            this.loadingDrawable.setBounds(rectF);
            this.loadingDrawable.draw(canvas);
            rectF.set(((this.dialogCell.getX() + this.dialogCell.getWidth()) - AndroidUtilities.m1146dp(16.0f)) - AndroidUtilities.m1146dp(43.0f), this.dialogCell.getY() + AndroidUtilities.m1146dp(12.0f), (this.dialogCell.getX() + this.dialogCell.getWidth()) - AndroidUtilities.m1146dp(16.0f), this.dialogCell.getY() + AndroidUtilities.m1146dp(20.33f));
            this.loadingDrawable.setBounds(rectF);
            this.loadingDrawable.draw(canvas);
            invalidate();
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return this.loadingDrawable == drawable || super.verifyDrawable(drawable);
    }

    public void set(TLRPC.Chat chat, MessageObject messageObject) {
        String shortNumber;
        boolean z = this.set;
        boolean z2 = chat != null;
        this.subscribersView.cancelAnimation();
        this.subscribersView.setPivotX(LocaleController.isRTL ? 1.0f : 0.0f);
        if (z && (chat == null || this.subscribersView.getTag() == null || ((Integer) this.subscribersView.getTag()).intValue() != chat.participants_count)) {
            this.subscribersView.animate().alpha(z2 ? 1.0f : 0.0f).scaleX(z2 ? 1.0f : 0.8f).scaleY(z2 ? 1.0f : 0.8f).setDuration(420L).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
        } else {
            this.subscribersView.setAlpha(z2 ? 1.0f : 0.0f);
            this.subscribersView.setScaleX(z2 ? 1.0f : 0.0f);
            this.subscribersView.setScaleY(z2 ? 1.0f : 0.0f);
        }
        if (chat != null) {
            int[] iArr = new int[1];
            this.subscribersView.setTag(Integer.valueOf(chat.participants_count));
            if (chat.participants_count > 0) {
                if (AndroidUtilities.isAccessibilityScreenReaderEnabled()) {
                    int i = chat.participants_count;
                    iArr[0] = i;
                    shortNumber = String.valueOf(i);
                } else {
                    shortNumber = LocaleController.formatShortNumber(chat.participants_count, iArr);
                }
                this.subscribersView.setText(LocaleController.formatPluralString("Subscribers", iArr[0], new Object[0]).replace(String.format("%d", Integer.valueOf(iArr[0])), shortNumber), true);
            } else {
                this.subscribersView.setText(LocaleController.getString(C2369R.string.PersonalChannel), true);
            }
            boolean z3 = messageObject == null;
            this.loading = z3;
            if (z3) {
                this.dialogCell.setDialog(-chat.f1571id, null, 0, false, z);
            } else {
                this.dialogCell.setDialog(-chat.f1571id, messageObject, messageObject.messageOwner.date, false, z);
            }
        }
        if (!z) {
            this.loadingAlpha.set(this.loading, true);
        }
        updatePosition();
        invalidate();
        this.set = true;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(86.0f), TLObject.FLAG_30));
    }

    public static class ChannelMessageFetcher {
        private ArrayList callbacks = new ArrayList();
        public long channel_id;
        public final int currentAccount;
        public boolean error;
        public boolean loaded;
        public boolean loading;
        public MessageObject messageObject;
        public int message_id;
        private int searchId;

        public ChannelMessageFetcher(int i) {
            this.currentAccount = i;
        }

        public void fetch(TLRPC.UserFull userFull) {
            if (userFull == null || (userFull.flags2 & 64) == 0) {
                this.searchId++;
                this.loaded = true;
                this.messageObject = null;
                done(false);
                return;
            }
            fetch(userFull.personal_channel_id, userFull.personal_channel_message);
        }

        public void fetch(final long j, final int i) {
            if (this.loaded || this.loading) {
                if (this.channel_id == j && this.message_id == i) {
                    return;
                }
                this.loaded = false;
                this.messageObject = null;
            }
            final int i2 = this.searchId + 1;
            this.searchId = i2;
            this.loading = true;
            this.channel_id = j;
            this.message_id = i;
            final long clientUserId = UserConfig.getInstance(this.currentAccount).getClientUserId();
            final MessagesStorage messagesStorage = MessagesStorage.getInstance(this.currentAccount);
            messagesStorage.getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.ui.Cells.ProfileChannelCell$ChannelMessageFetcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    this.f$0.lambda$fetch$3(i, messagesStorage, j, clientUserId, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v10 */
        /* JADX WARN: Type inference failed for: r3v2, types: [org.telegram.tgnet.TLRPC$Message] */
        /* JADX WARN: Type inference failed for: r3v4 */
        /* JADX WARN: Type inference failed for: r3v5 */
        /* JADX WARN: Type inference failed for: r3v6 */
        /* JADX WARN: Type inference failed for: r3v7, types: [org.telegram.tgnet.TLRPC$Message] */
        /* JADX WARN: Type inference failed for: r3v9 */
        public /* synthetic */ void lambda$fetch$3(final int i, final MessagesStorage messagesStorage, final long j, long j2, final int i2) throws Throwable {
            Throwable th;
            Object obj;
            final ?? r3;
            SQLiteCursor sQLiteCursorQueryFinalized;
            ?? TLdeserialize;
            NativeByteBuffer nativeByteBufferByteBufferValue;
            ArrayList<TLRPC.User> arrayList = new ArrayList<>();
            ArrayList<TLRPC.Chat> arrayList2 = new ArrayList<>();
            SQLiteCursor sQLiteCursor = null;
            try {
                try {
                    if (i <= 0) {
                        sQLiteCursorQueryFinalized = messagesStorage.getDatabase().queryFinalized("SELECT data, mid FROM messages_v2 WHERE uid = ? ORDER BY mid DESC LIMIT 1", Long.valueOf(-j));
                    } else {
                        sQLiteCursorQueryFinalized = messagesStorage.getDatabase().queryFinalized("SELECT data, mid FROM messages_v2 WHERE uid = ? AND mid = ? LIMIT 1", Long.valueOf(-j), Integer.valueOf(i));
                    }
                } catch (Exception e) {
                    e = e;
                    obj = null;
                }
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                try {
                    ArrayList<Long> arrayList3 = new ArrayList<>();
                    ArrayList arrayList4 = new ArrayList();
                    if (sQLiteCursorQueryFinalized.next() && (nativeByteBufferByteBufferValue = sQLiteCursorQueryFinalized.byteBufferValue(0)) != null) {
                        TLdeserialize = TLRPC.Message.TLdeserialize(nativeByteBufferByteBufferValue, nativeByteBufferByteBufferValue.readInt32(false), false);
                        try {
                            TLdeserialize.readAttachPath(nativeByteBufferByteBufferValue, j2);
                            nativeByteBufferByteBufferValue.reuse();
                            TLdeserialize.f1597id = sQLiteCursorQueryFinalized.intValue(1);
                            TLdeserialize.dialog_id = -j;
                            MessagesStorage.addUsersAndChatsFromMessage(TLdeserialize, arrayList3, arrayList4, null);
                            sQLiteCursor = TLdeserialize;
                        } catch (Exception e2) {
                            e = e2;
                            sQLiteCursor = sQLiteCursorQueryFinalized;
                            obj = TLdeserialize;
                            FileLog.m1160e(e);
                            r3 = obj;
                            if (sQLiteCursor != null) {
                                sQLiteCursor.dispose();
                                r3 = obj;
                            }
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Cells.ProfileChannelCell$ChannelMessageFetcher$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$fetch$2(i2, r3, j, i, messagesStorage);
                                }
                            });
                        }
                    }
                    sQLiteCursorQueryFinalized.dispose();
                    if (sQLiteCursor != null) {
                        if (!arrayList3.isEmpty()) {
                            messagesStorage.getUsersInternal(arrayList3, arrayList);
                        }
                        if (!arrayList4.isEmpty()) {
                            messagesStorage.getChatsInternal(TextUtils.join(",", arrayList4), arrayList2);
                        }
                    }
                    sQLiteCursorQueryFinalized.dispose();
                    r3 = sQLiteCursor;
                } catch (Exception e3) {
                    e = e3;
                    TLdeserialize = sQLiteCursor;
                }
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Cells.ProfileChannelCell$ChannelMessageFetcher$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$fetch$2(i2, r3, j, i, messagesStorage);
                    }
                });
            } catch (Throwable th3) {
                th = th3;
                sQLiteCursor = sQLiteCursorQueryFinalized;
                if (sQLiteCursor != null) {
                    sQLiteCursor.dispose();
                    throw th;
                }
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$fetch$2(final int i, TLRPC.Message message, final long j, final int i2, final MessagesStorage messagesStorage) {
            if (i != this.searchId) {
                return;
            }
            MessageObject messageObject = message != null ? new MessageObject(this.currentAccount, message, true, true) : null;
            if (messageObject != null) {
                this.messageObject = messageObject;
                done(false);
            } else {
                TLRPC.TL_channels_getMessages tL_channels_getMessages = new TLRPC.TL_channels_getMessages();
                tL_channels_getMessages.channel = MessagesController.getInstance(this.currentAccount).getInputChannel(j);
                tL_channels_getMessages.f1617id.add(Integer.valueOf(i2));
                ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_channels_getMessages, new RequestDelegate() { // from class: org.telegram.ui.Cells.ProfileChannelCell$ChannelMessageFetcher$$ExternalSyntheticLambda2
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$fetch$1(messagesStorage, j, i, i2, tLObject, tL_error);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$fetch$1(final MessagesStorage messagesStorage, final long j, final int i, final int i2, final TLObject tLObject, TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Cells.ProfileChannelCell$ChannelMessageFetcher$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$fetch$0(tLObject, messagesStorage, j, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$fetch$0(TLObject tLObject, MessagesStorage messagesStorage, long j, int i, int i2) {
            TLRPC.Message message;
            if (tLObject instanceof TLRPC.messages_Messages) {
                TLRPC.messages_Messages messages_messages = (TLRPC.messages_Messages) tLObject;
                MessagesController.getInstance(this.currentAccount).putUsers(messages_messages.users, false);
                MessagesController.getInstance(this.currentAccount).putChats(messages_messages.chats, false);
                messagesStorage.putUsersAndChats(messages_messages.users, messages_messages.chats, true, true);
                messagesStorage.putMessages(messages_messages, -j, -1, 0, false, 0, 0L);
                if (i != this.searchId) {
                    return;
                }
                ArrayList arrayList = messages_messages.messages;
                int size = arrayList.size();
                int i3 = 0;
                while (true) {
                    if (i3 >= size) {
                        message = null;
                        break;
                    }
                    Object obj = arrayList.get(i3);
                    i3++;
                    message = (TLRPC.Message) obj;
                    if (message.f1597id == i2) {
                        break;
                    }
                }
                if (message != null) {
                    if (message instanceof TLRPC.TL_messageEmpty) {
                        this.messageObject = null;
                    } else {
                        this.messageObject = new MessageObject(this.currentAccount, message, true, true);
                    }
                    done(false);
                    return;
                }
                return;
            }
            if (i != this.searchId) {
                return;
            }
            done(true);
        }

        public void subscribe(Runnable runnable) {
            if (this.loaded) {
                runnable.run();
            } else {
                this.callbacks.add(runnable);
            }
        }

        private void done(boolean z) {
            int i = 0;
            this.loading = false;
            this.loaded = true;
            this.error = z;
            ArrayList arrayList = this.callbacks;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((Runnable) obj).run();
            }
            this.callbacks.clear();
        }
    }

    public void updateColors() {
        int iProcessColor = processColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueHeader, this.resourcesProvider));
        this.subscribersView.setTextColor(iProcessColor);
        this.subscribersView.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.m1146dp(9.0f), AndroidUtilities.m1146dp(9.0f), Theme.multAlpha(iProcessColor, 0.1f)));
    }
}
