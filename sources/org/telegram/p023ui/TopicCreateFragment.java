package org.telegram.p023ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AnimationNotificationsLocker;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.HeaderCell;
import org.telegram.p023ui.Cells.TextCheckCell2;
import org.telegram.p023ui.Cells.TextInfoPrivacyCell;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.CombinedDrawable;
import org.telegram.p023ui.Components.EditTextBoldCursor;
import org.telegram.p023ui.Components.Forum.ForumBubbleDrawable;
import org.telegram.p023ui.Components.Forum.ForumUtilities;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.LetterDrawable;
import org.telegram.p023ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p023ui.Components.ReplaceableIconDrawable;
import org.telegram.p023ui.Components.SizeNotifierFrameLayout;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_stars;

/* loaded from: classes5.dex */
public class TopicCreateFragment extends BaseFragment {
    BackupImageView[] backupImageView;
    long chatId;
    TextCheckCell2 checkBoxCell;
    boolean created;
    Drawable defaultIconDrawable;
    EditTextBoldCursor editTextBoldCursor;
    String firstSymbol;
    ForumBubbleDrawable forumBubbleDrawable;
    int iconColor;
    AnimationNotificationsLocker notificationsLocker;
    private ChatActivity openInChatActivity;
    ReplaceableIconDrawable replaceableIconDrawable;
    SelectAnimatedEmojiDialog selectAnimatedEmojiDialog;
    long selectedEmojiDocumentId;
    TLRPC.TL_forumTopic topicForEdit;
    long topicId;

    public TopicCreateFragment setOpenInChatActivity(ChatActivity chatActivity) {
        this.openInChatActivity = chatActivity;
        return this;
    }

    public static TopicCreateFragment create(long j, long j2) {
        Bundle bundle = new Bundle();
        bundle.putLong("chat_id", j);
        bundle.putLong("topic_id", j2);
        return new TopicCreateFragment(bundle);
    }

    private TopicCreateFragment(Bundle bundle) {
        super(bundle);
        this.backupImageView = new BackupImageView[2];
        this.firstSymbol = "";
        this.notificationsLocker = new AnimationNotificationsLocker();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        this.chatId = this.arguments.getLong("chat_id");
        long j = this.arguments.getLong("topic_id", 0L);
        this.topicId = j;
        if (j != 0) {
            TLRPC.TL_forumTopic tL_forumTopicFindTopic = getMessagesController().getTopicsController().findTopic(this.chatId, this.topicId);
            this.topicForEdit = tL_forumTopicFindTopic;
            if (tL_forumTopicFindTopic == null) {
                return false;
            }
            this.iconColor = tL_forumTopicFindTopic.icon_color;
        } else {
            int[] iArr = ForumBubbleDrawable.serverSupportedColor;
            this.iconColor = iArr[Math.abs(Utilities.random.nextInt() % iArr.length)];
        }
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        if (this.topicForEdit != null) {
            this.actionBar.setTitle(LocaleController.getString(C2369R.string.EditTopic));
        } else {
            this.actionBar.setTitle(LocaleController.getString(C2369R.string.NewTopic));
        }
        this.actionBar.setBackButtonImage(C2369R.drawable.ic_ab_back);
        this.actionBar.setActionBarMenuOnItemClick(new C67491());
        if (this.topicForEdit == null) {
            this.actionBar.createMenu().addItem(1, LocaleController.getString(C2369R.string.Create).toUpperCase());
        } else {
            this.actionBar.createMenu().addItem(2, C2369R.drawable.ic_ab_done);
        }
        SizeNotifierFrameLayout sizeNotifierFrameLayout = new SizeNotifierFrameLayout(context) { // from class: org.telegram.ui.TopicCreateFragment.2
            boolean keyboardWasShown;

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                measureKeyboardHeight();
                if (getKeyboardHeight() == 0 && !this.keyboardWasShown) {
                    int i3 = MessagesController.getGlobalEmojiSettings().getInt("kbd_height", AndroidUtilities.m1146dp(200.0f));
                    this.keyboardHeight = i3;
                    setPadding(0, 0, 0, i3);
                } else {
                    this.keyboardWasShown = true;
                    setPadding(0, 0, 0, 0);
                }
                super.onMeasure(i, i2);
            }
        };
        this.fragmentView = sizeNotifierFrameLayout;
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        sizeNotifierFrameLayout.addView(linearLayout);
        HeaderCell headerCell = new HeaderCell(context);
        TLRPC.TL_forumTopic tL_forumTopic = this.topicForEdit;
        if (tL_forumTopic != null && tL_forumTopic.f1631id == 1) {
            headerCell.setText(LocaleController.getString(C2369R.string.CreateGeneralTopicTitle));
        } else {
            headerCell.setText(LocaleController.getString(C2369R.string.CreateTopicTitle));
        }
        FrameLayout frameLayout = new FrameLayout(context);
        EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(context);
        this.editTextBoldCursor = editTextBoldCursor;
        editTextBoldCursor.setHintText(LocaleController.getString(C2369R.string.EnterTopicName));
        this.editTextBoldCursor.setHintColor(getThemedColor(Theme.key_chat_messagePanelHint));
        this.editTextBoldCursor.setTextColor(getThemedColor(Theme.key_chat_messagePanelText));
        this.editTextBoldCursor.setPadding(AndroidUtilities.m1146dp(0.0f), this.editTextBoldCursor.getPaddingTop(), AndroidUtilities.m1146dp(0.0f), this.editTextBoldCursor.getPaddingBottom());
        this.editTextBoldCursor.setBackgroundDrawable(null);
        this.editTextBoldCursor.setSingleLine(true);
        EditTextBoldCursor editTextBoldCursor2 = this.editTextBoldCursor;
        editTextBoldCursor2.setInputType(editTextBoldCursor2.getInputType() | 16384);
        frameLayout.addView(this.editTextBoldCursor, LayoutHelper.createFrame(-1, -1.0f, 0, 51.0f, 4.0f, 21.0f, 4.0f));
        this.editTextBoldCursor.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.TopicCreateFragment.3
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                String strTrim = editable.toString().trim();
                String str = TopicCreateFragment.this.firstSymbol;
                if (strTrim.length() > 0) {
                    TopicCreateFragment.this.firstSymbol = strTrim.substring(0, 1).toUpperCase();
                } else {
                    TopicCreateFragment.this.firstSymbol = "";
                }
                if (str.equals(TopicCreateFragment.this.firstSymbol)) {
                    return;
                }
                LetterDrawable letterDrawable = new LetterDrawable(null, 1);
                letterDrawable.setTitle(TopicCreateFragment.this.firstSymbol);
                ReplaceableIconDrawable replaceableIconDrawable = TopicCreateFragment.this.replaceableIconDrawable;
                if (replaceableIconDrawable != null) {
                    replaceableIconDrawable.setIcon((Drawable) letterDrawable, true);
                }
            }
        });
        C67524 c67524 = new C67524(context);
        c67524.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.TopicCreateFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$0(view);
            }
        });
        for (int i = 0; i < 2; i++) {
            this.backupImageView[i] = new BackupImageView(context);
            c67524.addView(this.backupImageView[i], LayoutHelper.createFrame(28, 28, 17));
        }
        frameLayout.addView(c67524, LayoutHelper.createFrame(40, 40.0f, 16, 10.0f, 0.0f, 0.0f, 0.0f));
        linearLayout.addView(headerCell);
        linearLayout.addView(frameLayout);
        FrameLayout frameLayout2 = new FrameLayout(context);
        int i2 = C2369R.drawable.greydivider_top;
        int i3 = Theme.key_windowBackgroundGrayShadow;
        CombinedDrawable combinedDrawable = new CombinedDrawable(new ColorDrawable(Theme.getColor(Theme.key_windowBackgroundGray)), Theme.getThemedDrawable(context, i2, Theme.getColor(i3)), 0, 0);
        combinedDrawable.setFullsize(true);
        frameLayout2.setBackgroundDrawable(combinedDrawable);
        frameLayout2.setClipChildren(false);
        TLRPC.TL_forumTopic tL_forumTopic2 = this.topicForEdit;
        if (tL_forumTopic2 == null || tL_forumTopic2.f1631id != 1) {
            SelectAnimatedEmojiDialog selectAnimatedEmojiDialog = new SelectAnimatedEmojiDialog(this, getContext(), false, null, 3, null) { // from class: org.telegram.ui.TopicCreateFragment.5
                private boolean firstLayout = true;

                @Override // org.telegram.p023ui.SelectAnimatedEmojiDialog, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
                protected void onLayout(boolean z, int i4, int i5, int i6, int i7) {
                    super.onLayout(z, i4, i5, i6, i7);
                    if (this.firstLayout) {
                        this.firstLayout = false;
                        TopicCreateFragment.this.selectAnimatedEmojiDialog.onShow(null);
                    }
                }

                @Override // org.telegram.p023ui.SelectAnimatedEmojiDialog
                protected void onEmojiSelected(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
                    boolean z = false;
                    if (!TextUtils.isEmpty(UserConfig.getInstance(((BaseFragment) TopicCreateFragment.this).currentAccount).defaultTopicIcons)) {
                        TLRPC.TL_messages_stickerSet stickerSetByEmojiOrName = TopicCreateFragment.this.getMediaDataController().getStickerSetByEmojiOrName(UserConfig.getInstance(((BaseFragment) TopicCreateFragment.this).currentAccount).defaultTopicIcons);
                        if ((stickerSetByEmojiOrName == null ? 0L : stickerSetByEmojiOrName.set.f1607id) == MediaDataController.getStickerSetId(document)) {
                            z = true;
                        }
                    }
                    TopicCreateFragment.this.selectEmoji(l, z);
                }
            };
            this.selectAnimatedEmojiDialog = selectAnimatedEmojiDialog;
            selectAnimatedEmojiDialog.setAnimationsEnabled(this.fragmentBeginToShow);
            this.selectAnimatedEmojiDialog.setClipChildren(false);
            frameLayout2.addView(this.selectAnimatedEmojiDialog, LayoutHelper.createFrame(-1, -1.0f, 0, 12.0f, 12.0f, 12.0f, 12.0f));
            Drawable drawableCreateTopicDrawable = ForumUtilities.createTopicDrawable("", this.iconColor, false);
            this.forumBubbleDrawable = (ForumBubbleDrawable) ((CombinedDrawable) drawableCreateTopicDrawable).getBackgroundDrawable();
            this.replaceableIconDrawable = new ReplaceableIconDrawable(context);
            CombinedDrawable combinedDrawable2 = new CombinedDrawable(drawableCreateTopicDrawable, this.replaceableIconDrawable, 0, 0);
            combinedDrawable2.setFullsize(true);
            this.selectAnimatedEmojiDialog.setForumIconDrawable(combinedDrawable2);
            this.defaultIconDrawable = combinedDrawable2;
            this.replaceableIconDrawable.addView(this.backupImageView[0]);
            this.replaceableIconDrawable.addView(this.backupImageView[1]);
            this.backupImageView[0].setImageDrawable(this.defaultIconDrawable);
            AndroidUtilities.updateViewVisibilityAnimated(this.backupImageView[0], true, 1.0f, false);
            AndroidUtilities.updateViewVisibilityAnimated(this.backupImageView[1], false, 1.0f, false);
            this.forumBubbleDrawable.addParent(this.backupImageView[0]);
            this.forumBubbleDrawable.addParent(this.backupImageView[1]);
        } else {
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(C2369R.drawable.msg_filled_general);
            imageView.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_chat_inMenu), PorterDuff.Mode.MULTIPLY));
            c67524.addView(imageView, LayoutHelper.createFrame(22, 22, 17));
            frameLayout2.addView(new ActionBarPopupWindow.GapView(context, getResourceProvider()), LayoutHelper.createFrame(-1, 8.0f));
            TextCheckCell2 textCheckCell2 = new TextCheckCell2(context);
            this.checkBoxCell = textCheckCell2;
            textCheckCell2.getCheckBox().setDrawIconType(0);
            this.checkBoxCell.setTextAndCheck(LocaleController.getString(C2369R.string.EditTopicHide), !this.topicForEdit.hidden, false);
            this.checkBoxCell.setBackground(Theme.createSelectorWithBackgroundDrawable(getThemedColor(Theme.key_windowBackgroundWhite), getThemedColor(Theme.key_listSelector)));
            this.checkBoxCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.TopicCreateFragment$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$createView$1(view);
                }
            });
            frameLayout2.addView(this.checkBoxCell, LayoutHelper.createFrame(-1, 50.0f, 48, 0.0f, 8.0f, 0.0f, 0.0f));
            TextInfoPrivacyCell textInfoPrivacyCell = new TextInfoPrivacyCell(context);
            textInfoPrivacyCell.setText(LocaleController.getString(C2369R.string.EditTopicHideInfo));
            textInfoPrivacyCell.setBackground(Theme.getThemedDrawableByKey(getContext(), C2369R.drawable.greydivider_bottom, i3, getResourceProvider()));
            frameLayout2.addView(textInfoPrivacyCell, LayoutHelper.createFrame(-1, -2.0f, 48, 0.0f, 58.0f, 0.0f, 0.0f));
        }
        linearLayout.addView(frameLayout2, LayoutHelper.createFrame(-1, -1.0f));
        TLRPC.TL_forumTopic tL_forumTopic3 = this.topicForEdit;
        if (tL_forumTopic3 != null) {
            this.editTextBoldCursor.setText(tL_forumTopic3.title);
            selectEmoji(Long.valueOf(this.topicForEdit.icon_emoji_id), true);
        } else {
            selectEmoji(0L, true);
        }
        return this.fragmentView;
    }

    /* renamed from: org.telegram.ui.TopicCreateFragment$1 */
    class C67491 extends ActionBar.ActionBarMenuOnItemClick {
        public static /* synthetic */ void $r8$lambda$amldw3qyO9IcHiCsIMINuL4IC4E(TLObject tLObject, TLRPC.TL_error tL_error) {
        }

        public static /* synthetic */ void $r8$lambda$otLZCyN9xeFR8YST98V_B6y2Ojk(TLObject tLObject, TLRPC.TL_error tL_error) {
        }

        C67491() {
        }

        /* JADX WARN: Removed duplicated region for block: B:38:0x00e3  */
        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onItemClick(int r12) {
            /*
                Method dump skipped, instructions count: 458
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.TopicCreateFragment.C67491.onItemClick(int):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$1(final String str, final AlertDialog alertDialog, final TLObject tLObject, TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.TopicCreateFragment$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException, NumberFormatException {
                    this.f$0.lambda$onItemClick$0(tLObject, str, alertDialog);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$0(TLObject tLObject, String str, AlertDialog alertDialog) throws Resources.NotFoundException, NumberFormatException {
            if (tLObject != null) {
                TLRPC.Updates updates = (TLRPC.Updates) tLObject;
                for (int i = 0; i < updates.updates.size(); i++) {
                    if (updates.updates.get(i) instanceof TLRPC.TL_updateMessageID) {
                        TLRPC.TL_updateMessageID tL_updateMessageID = (TLRPC.TL_updateMessageID) updates.updates.get(i);
                        TLRPC.TL_messageActionTopicCreate tL_messageActionTopicCreate = new TLRPC.TL_messageActionTopicCreate();
                        tL_messageActionTopicCreate.title = str;
                        TLRPC.TL_messageService tL_messageService = new TLRPC.TL_messageService();
                        tL_messageService.action = tL_messageActionTopicCreate;
                        tL_messageService.peer_id = TopicCreateFragment.this.getMessagesController().getPeer(-TopicCreateFragment.this.chatId);
                        tL_messageService.dialog_id = -TopicCreateFragment.this.chatId;
                        tL_messageService.f1597id = tL_updateMessageID.f1724id;
                        tL_messageService.date = (int) (System.currentTimeMillis() / 1000);
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(new MessageObject(((BaseFragment) TopicCreateFragment.this).currentAccount, tL_messageService, false, false));
                        TLRPC.Chat chat = TopicCreateFragment.this.getMessagesController().getChat(Long.valueOf(TopicCreateFragment.this.chatId));
                        TLRPC.TL_forumTopic tL_forumTopic = new TLRPC.TL_forumTopic();
                        tL_forumTopic.f1631id = tL_updateMessageID.f1724id;
                        TopicCreateFragment topicCreateFragment = TopicCreateFragment.this;
                        long j = topicCreateFragment.selectedEmojiDocumentId;
                        if (j != 0) {
                            tL_forumTopic.icon_emoji_id = j;
                            tL_forumTopic.flags |= 1;
                        }
                        tL_forumTopic.f1632my = true;
                        tL_forumTopic.flags |= 2;
                        tL_forumTopic.topicStartMessage = tL_messageService;
                        tL_forumTopic.title = str;
                        tL_forumTopic.top_message = tL_messageService.f1597id;
                        tL_forumTopic.topMessage = tL_messageService;
                        tL_forumTopic.from_id = topicCreateFragment.getMessagesController().getPeer(TopicCreateFragment.this.getUserConfig().clientUserId);
                        tL_forumTopic.notify_settings = new TLRPC.TL_peerNotifySettings();
                        TopicCreateFragment topicCreateFragment2 = TopicCreateFragment.this;
                        tL_forumTopic.icon_color = topicCreateFragment2.iconColor;
                        if (topicCreateFragment2.openInChatActivity != null) {
                            ChatActivity chatActivity = TopicCreateFragment.this.openInChatActivity;
                            chatActivity.resetForReload();
                            chatActivity.saveDraft();
                            chatActivity.setThreadMessages(arrayList, chat, tL_messageService.f1597id, 1, 1, tL_forumTopic);
                            chatActivity.justCreatedTopic = true;
                            chatActivity.firstLoadMessages();
                            chatActivity.updateTitle(true);
                            chatActivity.avatarContainer.updateSubtitle(true);
                            chatActivity.updateTopicTitleIcon();
                            chatActivity.topicsTabs.setCurrentTopic(chatActivity.getTopicId());
                            chatActivity.updateTopPanel(true);
                            chatActivity.updateBottomOverlay(true);
                            chatActivity.hideFieldPanel(true);
                            chatActivity.applyDraftMaybe(true, true);
                            chatActivity.reloadPinnedMessages();
                            TopicCreateFragment.this.getMessagesController().getTopicsController().onTopicCreated(-TopicCreateFragment.this.chatId, tL_forumTopic, true);
                            TopicCreateFragment.this.lambda$onBackPressed$371();
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putLong("chat_id", TopicCreateFragment.this.chatId);
                            bundle.putInt("message_id", 1);
                            bundle.putInt("unread_count", 0);
                            bundle.putBoolean("historyPreloaded", false);
                            ChatActivity chatActivity2 = new ChatActivity(bundle);
                            chatActivity2.setThreadMessages(arrayList, chat, tL_messageService.f1597id, 1, 1, tL_forumTopic);
                            chatActivity2.justCreatedTopic = true;
                            TopicCreateFragment.this.getMessagesController().getTopicsController().onTopicCreated(-TopicCreateFragment.this.chatId, tL_forumTopic, true);
                            TopicCreateFragment.this.presentFragment(chatActivity2);
                        }
                    }
                }
            }
            alertDialog.dismiss();
        }
    }

    /* renamed from: org.telegram.ui.TopicCreateFragment$4 */
    class C67524 extends FrameLayout {
        ValueAnimator backAnimator;
        boolean pressed;
        float pressedProgress;

        C67524(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            float f = ((1.0f - this.pressedProgress) * 0.2f) + 0.8f;
            canvas.save();
            canvas.scale(f, f, getMeasuredHeight() / 2.0f, getMeasuredWidth() / 2.0f);
            super.dispatchDraw(canvas);
            canvas.restore();
            updatePressedProgress();
        }

        @Override // android.view.View
        public void setPressed(boolean z) {
            ValueAnimator valueAnimator;
            super.setPressed(z);
            if (this.pressed != z) {
                this.pressed = z;
                invalidate();
                if (z && (valueAnimator = this.backAnimator) != null) {
                    valueAnimator.removeAllListeners();
                    this.backAnimator.cancel();
                }
                if (z) {
                    return;
                }
                float f = this.pressedProgress;
                if (f != 0.0f) {
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, 0.0f);
                    this.backAnimator = valueAnimatorOfFloat;
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.TopicCreateFragment$4$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            this.f$0.lambda$setPressed$0(valueAnimator2);
                        }
                    });
                    this.backAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.TopicCreateFragment.4.1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            C67524.this.backAnimator = null;
                        }
                    });
                    this.backAnimator.setInterpolator(new OvershootInterpolator(5.0f));
                    this.backAnimator.setDuration(350L);
                    this.backAnimator.start();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setPressed$0(ValueAnimator valueAnimator) {
            this.pressedProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            invalidate();
        }

        public void updatePressedProgress() {
            if (isPressed()) {
                float f = this.pressedProgress;
                if (f != 1.0f) {
                    this.pressedProgress = Utilities.clamp(f + 0.16f, 1.0f, 0.0f);
                    invalidate();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(View view) {
        if (this.selectedEmojiDocumentId == 0 && this.topicForEdit == null) {
            this.iconColor = this.forumBubbleDrawable.moveNexColor();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(View view) {
        this.checkBoxCell.setChecked(!r2.isChecked());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void selectEmoji(Long l, boolean z) {
        if (this.selectAnimatedEmojiDialog == null || this.replaceableIconDrawable == null) {
            return;
        }
        long jLongValue = l == null ? 0L : l.longValue();
        this.selectAnimatedEmojiDialog.setSelected(Long.valueOf(jLongValue));
        if (this.selectedEmojiDocumentId == jLongValue) {
            return;
        }
        if (!z && jLongValue != 0 && !getUserConfig().isPremium()) {
            TLRPC.Document documentFindDocument = AnimatedEmojiDrawable.findDocument(this.currentAccount, l.longValue());
            if (documentFindDocument != null) {
                BulletinFactory.m1267of(this).createEmojiBulletin(documentFindDocument, AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.UnlockPremiumEmojiHint)), LocaleController.getString(C2369R.string.PremiumMore), new Runnable() { // from class: org.telegram.ui.TopicCreateFragment$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$selectEmoji$2();
                    }
                }).show();
                return;
            }
            return;
        }
        this.selectedEmojiDocumentId = jLongValue;
        if (jLongValue != 0) {
            AnimatedEmojiDrawable animatedEmojiDrawable = new AnimatedEmojiDrawable(10, this.currentAccount, jLongValue);
            animatedEmojiDrawable.setColorFilter(Theme.chat_animatedEmojiTextColorFilter);
            this.backupImageView[1].setAnimatedEmojiDrawable(animatedEmojiDrawable);
            this.backupImageView[1].setImageDrawable(null);
        } else {
            LetterDrawable letterDrawable = new LetterDrawable(null, 1);
            letterDrawable.setTitle(this.firstSymbol);
            this.replaceableIconDrawable.setIcon((Drawable) letterDrawable, false);
            this.backupImageView[1].setImageDrawable(this.defaultIconDrawable);
            this.backupImageView[1].setAnimatedEmojiDrawable(null);
        }
        BackupImageView[] backupImageViewArr = this.backupImageView;
        BackupImageView backupImageView = backupImageViewArr[0];
        BackupImageView backupImageView2 = backupImageViewArr[1];
        backupImageViewArr[0] = backupImageView2;
        backupImageViewArr[1] = backupImageView;
        AndroidUtilities.updateViewVisibilityAnimated(backupImageView2, true, 0.5f, true);
        AndroidUtilities.updateViewVisibilityAnimated(this.backupImageView[1], false, 0.5f, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$selectEmoji$2() {
        new PremiumFeatureBottomSheet(this, 11, false).show();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onTransitionAnimationStart(boolean z, boolean z2) {
        super.onTransitionAnimationStart(z, z2);
        if (z) {
            this.notificationsLocker.lock();
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        super.onTransitionAnimationEnd(z, z2);
        if (!z && this.created) {
            removeSelfFromStack();
        }
        this.notificationsLocker.unlock();
        SelectAnimatedEmojiDialog selectAnimatedEmojiDialog = this.selectAnimatedEmojiDialog;
        if (selectAnimatedEmojiDialog != null) {
            selectAnimatedEmojiDialog.setAnimationsEnabled(this.fragmentBeginToShow);
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        this.editTextBoldCursor.requestFocus();
        AndroidUtilities.showKeyboard(this.editTextBoldCursor);
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
    }

    public void showKeyboard() {
        this.editTextBoldCursor.requestFocus();
        AndroidUtilities.showKeyboard(this.editTextBoldCursor);
    }
}
