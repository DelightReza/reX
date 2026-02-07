package org.telegram.p023ui.Cells;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ReplacementSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import androidx.collection.LongSparseArray;
import androidx.core.graphics.ColorUtils;
import com.chaquo.python.internal.Common;
import com.exteragram.messenger.ExteraConfig;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.controllers.AyuFilterController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.ToIntFunction;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ChatThemeController;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Adapters.DialogsAdapter;
import org.telegram.p023ui.AvatarSpan;
import org.telegram.p023ui.Cells.DialogCell;
import org.telegram.p023ui.Cells.ShareDialogCell;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.AnimatedEmojiSpan;
import org.telegram.p023ui.Components.AnimatedFloat;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.BubbleCounterPath;
import org.telegram.p023ui.Components.ButtonBounce;
import org.telegram.p023ui.Components.CanvasButton;
import org.telegram.p023ui.Components.CheckBox2;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.DialogCellTags;
import org.telegram.p023ui.Components.ForegroundColorSpanThemable;
import org.telegram.p023ui.Components.Forum.ForumBubbleDrawable;
import org.telegram.p023ui.Components.Forum.ForumUtilities;
import org.telegram.p023ui.Components.PhotoBubbleClip;
import org.telegram.p023ui.Components.Premium.PremiumGradient;
import org.telegram.p023ui.Components.PullForegroundDrawable;
import org.telegram.p023ui.Components.RLottieDrawable;
import org.telegram.p023ui.Components.Text;
import org.telegram.p023ui.Components.TimerDrawable;
import org.telegram.p023ui.Components.TypefaceSpan;
import org.telegram.p023ui.Components.spoilers.SpoilerEffect;
import org.telegram.p023ui.DialogsActivity;
import org.telegram.p023ui.Stars.StarsIntroActivity;
import org.telegram.p023ui.Stories.StoriesListPlaceProvider;
import org.telegram.p023ui.Stories.StoriesUtilities;
import org.telegram.p023ui.Stories.StoryViewer;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_account;
import org.telegram.tgnet.p022tl.TL_stories;
import p017j$.util.Comparator;

/* loaded from: classes3.dex */
public class DialogCell extends BaseCell implements StoriesListPlaceProvider.AvatarOverlaysView {
    private static final MessageObject[] filteredDummyMessages = new MessageObject[16];
    private int[] adaptiveEmojiColor;
    private ColorFilter[] adaptiveEmojiColorFilter;
    public int addForumHeightForTags;
    public int addHeightForTags;
    private boolean allowBotOpenButton;
    private int animateFromStatusDrawableParams;
    private int animateToStatusDrawableParams;
    private AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiStack;
    private AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiStack2;
    private AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiStack3;
    private AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiStackName;
    private boolean animatingArchiveAvatar;
    private float animatingArchiveAvatarProgress;
    private boolean applyName;
    private float archiveBackgroundProgress;
    private boolean archiveHidden;
    protected PullForegroundDrawable archivedChatsDrawable;
    private boolean attachedToWindow;
    private AvatarDrawable avatarDrawable;
    public ImageReceiver avatarImage;
    public int avatarStart;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable botVerification;
    private int bottomClip;
    private PhotoBubbleClip bubbleClip;
    private Paint buttonBackgroundPaint;
    private boolean buttonCreated;
    private StaticLayout buttonLayout;
    private int buttonLeft;
    private int buttonTop;
    CanvasButton canvasButton;
    public boolean channelShouldUseLineWidth;
    private TLRPC.Chat chat;
    private float chatCallProgress;
    protected CheckBox2 checkBox;
    private int checkDrawLeft;
    private int checkDrawLeft1;
    private int checkDrawTop;
    public float chekBoxPaddingTop;
    private boolean clearingDialog;
    private float clipProgress;
    private int clockDrawLeft;
    public float collapseOffset;
    public boolean collapsed;
    private float cornerProgress;
    private StaticLayout countAnimationInLayout;
    private boolean countAnimationIncrement;
    private StaticLayout countAnimationStableLayout;
    private ValueAnimator countAnimator;
    private float countChangeProgress;
    private StaticLayout countLayout;
    private int countLeft;
    private int countLeftOld;
    private StaticLayout countOldLayout;
    private int countTop;
    private int countWidth;
    private int countWidthOld;
    private Paint counterPaintOutline;
    private Path counterPath;
    private RectF counterPathRect;
    private int currentAccount;
    private int currentDialogFolderDialogsCount;
    private int currentDialogFolderId;
    private long currentDialogId;
    private int currentEditDate;
    private TextPaint currentMessagePaint;
    private float currentRevealBounceProgress;
    private float currentRevealProgress;
    private CustomDialog customDialog;
    private String customMessage;
    DialogCellDelegate delegate;
    private boolean dialogMuted;
    private float dialogMutedProgress;
    private int dialogsType;
    private TLRPC.DraftMessage draftMessage;
    private boolean draftVoice;
    public boolean drawArchive;
    public boolean drawAvatar;
    public boolean drawAvatarSelector;
    private boolean drawBadge;
    private boolean drawBotVerified;
    private boolean drawCheck1;
    private boolean drawCheck2;
    private boolean drawClock;
    private boolean drawCount;
    private boolean drawCount2;
    private boolean drawError;
    private boolean drawForwardIcon;
    private boolean drawGiftIcon;
    private boolean drawMention;
    public boolean drawMonoforumAvatar;
    private boolean drawNameLock;
    private boolean drawPin;
    private boolean drawPinBackground;
    private boolean drawPinForced;
    private boolean[] drawPlay;
    private boolean drawPremium;
    private boolean drawReactionMention;
    private boolean drawReorder;
    private boolean drawRevealBackground;
    private int drawScam;
    private boolean[] drawSpoiler;
    private boolean drawUnmute;
    private boolean drawVerified;
    public boolean drawingForBlur;
    private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable emojiStatus;
    private final View emojiStatusView;
    private TLRPC.EncryptedChat encryptedChat;
    private int errorLeft;
    private int errorTop;
    private Paint fadePaint;
    private Paint fadePaintBack;
    private int folderId;
    protected boolean forbidDraft;
    protected boolean forbidVerified;
    private ForumFormattedNames forumFormattedNames;
    public TLRPC.TL_forumTopic forumTopic;
    public boolean fullSeparator;
    public boolean fullSeparator2;
    private ArrayList groupMessages;
    private int halfCheckDrawLeft;
    private boolean hasCall;
    private boolean hasNameInMessage;
    private boolean hasUnmutedTopics;
    private boolean hasVideoThumb;
    public int heightDefault;
    public int heightThreeLines;
    public boolean inPreviewMode;
    private float innerProgress;
    private BounceInterpolator interpolator;
    public boolean isDialogCell;
    public boolean isForChannelSubscriberCell;
    private boolean isForum;
    public boolean isMonoForumTopicDialog;
    public boolean isSavedDialog;
    public boolean isSavedDialogCell;
    private boolean isSelected;
    private boolean isShareToStoryCell;
    private boolean isSliding;
    private boolean isTopic;
    public boolean isTransitionSupport;
    long lastDialogChangedTime;
    private int lastDrawSwipeMessageStringId;
    private RLottieDrawable lastDrawTranslationDrawable;
    private int lastMessageDate;
    private CharSequence lastMessageString;
    private CharSequence lastPrintString;
    private int lastSendState;
    int lastSize;
    private int lastStatusDrawableParams;
    private boolean lastTopicMessageUnread;
    private boolean lastUnreadState;
    private int lock2Left;
    private Drawable lockDrawable;
    private boolean markUnread;
    private int mentionCount;
    private StaticLayout mentionLayout;
    private int mentionLeft;
    private int mentionWidth;
    private MessageObject message;
    private final AvatarSpan messageAvatarSpan;
    private int messageId;
    private StaticLayout messageLayout;
    private int messageLeft;
    private StaticLayout messageNameLayout;
    private int messageNameLeft;
    private int messageNameTop;
    public int messagePaddingStart;
    private int messageTop;
    boolean moving;
    public int nameAdditionalsForChannelSubscriber;
    private boolean nameIsEllipsized;
    public StaticLayout nameLayout;
    private boolean nameLayoutEllipsizeByGradient;
    private boolean nameLayoutEllipsizeLeft;
    private boolean nameLayoutFits;
    public float nameLayoutTranslateX;
    public int nameLeft;
    private int nameLockLeft;
    private int nameLockTop;
    private int nameMuteLeft;
    public int namePaddingEnd;
    public int nameWidth;
    private boolean needEmoji;
    private Utilities.Callback onOpenButtonClick;
    private float onlineProgress;
    private boolean openBot;
    private final Paint openButtonBackgroundPaint;
    private final ButtonBounce openButtonBounce;
    private final RectF openButtonRect;
    private Text openButtonText;
    protected boolean overrideSwipeAction;
    protected int overrideSwipeActionBackgroundColorKey;
    protected RLottieDrawable overrideSwipeActionDrawable;
    protected int overrideSwipeActionRevealBackgroundColorKey;
    protected int overrideSwipeActionStringId;
    protected String overrideSwipeActionStringKey;
    private int paintIndex;
    private DialogsActivity parentFragment;
    private int pinLeft;
    private int pinTop;
    private DialogsAdapter.DialogsPreloader preloader;
    private boolean premiumBlocked;
    private final AnimatedFloat premiumBlockedT;
    private PremiumGradient.PremiumGradientTools premiumGradient;
    private int printingStringType;
    private int progressStage;
    private boolean promoDialog;
    private int reactionMentionCount;
    private int reactionMentionLeft;
    private ValueAnimator reactionsMentionsAnimator;
    private float reactionsMentionsChangeProgress;
    private int readOutboxMaxId;
    private RectF rect;
    private float reorderIconProgress;
    public ShareDialogCell.RepostStoryDrawable repostStoryDrawable;
    private final Theme.ResourcesProvider resourcesProvider;
    public float rightFragmentOffset;
    private float rightFragmentOpenedProgress;
    private boolean showTopicIconInName;
    private boolean showTtl;
    private List spoilers;
    private List spoilers2;
    private Stack spoilersPool;
    private Stack spoilersPool2;
    private Drawable starBg;
    private int starBgColor;
    private Drawable starFg;
    private final AnimatedFloat starsBlockedT;
    private long starsPriceBlocked;
    private boolean statusDrawableAnimationInProgress;
    private ValueAnimator statusDrawableAnimator;
    private int statusDrawableLeft;
    private float statusDrawableProgress;
    public final StoriesUtilities.AvatarStoryParams storyParams;
    public boolean swipeCanceled;
    private int swipeMessageTextId;
    private StaticLayout swipeMessageTextLayout;
    private int swipeMessageWidth;
    public DialogCellTags tags;
    private int tagsLeft;
    private int tagsRight;
    private Paint thumbBackgroundPaint;
    private ImageReceiver[] thumbImage;
    private boolean[] thumbImageSeen;
    private Path thumbPath;
    int thumbSize;
    private SpoilerEffect thumbSpoiler;
    private int thumbsCount;
    private StaticLayout timeLayout;
    private int timeLeft;
    private int timeTop;
    private TimerDrawable timerDrawable;
    private Paint timerPaint;
    private Paint timerPaint2;
    private int topClip;
    int topMessageTopicEndIndex;
    int topMessageTopicStartIndex;
    private Paint topicCounterPaint;
    protected Drawable[] topicIconInName;
    private boolean topicMuted;
    protected int translateY;
    private boolean translationAnimationStarted;
    private RLottieDrawable translationDrawable;
    protected float translationX;
    private int ttlPeriod;
    private float ttlProgress;
    private boolean twoLinesForName;
    private StaticLayout typingLayout;
    private int typingLeft;
    private int unreadCount;
    private Runnable unsubscribePremiumBlocked;
    private final DialogUpdateHelper updateHelper;
    private boolean updateLayout;
    public boolean useForceThreeLines;
    public boolean useFromUserAsAvatar;
    private boolean useMeForMyMessages;
    public boolean useSeparator;
    private TLRPC.User user;
    private boolean visibleOnScreen;
    private boolean wasDrawnOnline;
    protected float xOffset;

    public static class BounceInterpolator implements Interpolator {
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            if (f < 0.33f) {
                return (f / 0.33f) * 0.1f;
            }
            float f2 = f - 0.33f;
            return f2 < 0.33f ? 0.1f - ((f2 / 0.34f) * 0.15f) : (((f2 - 0.34f) / 0.33f) * 0.05f) - 0.05f;
        }
    }

    public static class CustomDialog {
        public int date;

        /* renamed from: id */
        public int f1807id;
        public boolean isMedia;
        public String message;
        public boolean muted;
        public String name;
        public boolean pinned;
        public int sent = -1;
        public int type;
        public int unread_count;
        public boolean verified;
    }

    public interface DialogCellDelegate {
        boolean canClickButtonInside();

        void onButtonClicked(DialogCell dialogCell);

        void onButtonLongPress(DialogCell dialogCell);

        void openHiddenStories();

        void openStory(DialogCell dialogCell, Runnable runnable);

        void showChatPreview(DialogCell dialogCell);
    }

    public boolean checkCurrentDialogIndex(boolean z) {
        return false;
    }

    protected boolean drawLock2() {
        return false;
    }

    @Override // org.telegram.p023ui.Cells.BaseCell, android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public static class ThumbnailSpan extends FixedWidthSpan {
        public ThumbnailSpan(int i) {
            super(i);
        }
    }

    public void setMoving(boolean z) {
        this.moving = z;
    }

    public boolean isMoving() {
        return this.moving;
    }

    public void setForumTopic(TLRPC.TL_forumTopic tL_forumTopic, long j, MessageObject messageObject, boolean z, boolean z2) {
        PullForegroundDrawable pullForegroundDrawable;
        this.forumTopic = tL_forumTopic;
        this.isTopic = tL_forumTopic != null;
        if (this.currentDialogId != j) {
            this.lastStatusDrawableParams = -1;
        }
        if (messageObject != null) {
            Drawable drawable = messageObject.topicIconDrawable[0];
            if (drawable instanceof ForumBubbleDrawable) {
                ((ForumBubbleDrawable) drawable).setColor(tL_forumTopic.icon_color);
            }
        }
        this.currentDialogId = j;
        this.lastDialogChangedTime = System.currentTimeMillis();
        this.message = messageObject;
        filterCurrentMessage();
        this.isDialogCell = false;
        this.showTopicIconInName = z;
        if (messageObject != null) {
            TLRPC.Message message = messageObject.messageOwner;
            this.lastMessageDate = message.date;
            this.currentEditDate = message.edit_date;
            this.markUnread = false;
            this.messageId = messageObject.getId();
            this.lastUnreadState = messageObject.isUnread();
        }
        MessageObject messageObject2 = this.message;
        if (messageObject2 != null) {
            this.lastSendState = messageObject2.messageOwner.send_state;
        }
        if (!z2) {
            this.lastStatusDrawableParams = -1;
        }
        if (tL_forumTopic != null) {
            this.groupMessages = tL_forumTopic.groupedMessages;
        }
        TLRPC.TL_forumTopic tL_forumTopic2 = this.forumTopic;
        if (tL_forumTopic2 != null && tL_forumTopic2.f1631id == 1 && (pullForegroundDrawable = this.archivedChatsDrawable) != null) {
            pullForegroundDrawable.setCell(this);
        }
        update(0, z2);
    }

    public void setRightFragmentOpenedProgress(float f) {
        if (this.rightFragmentOpenedProgress != f) {
            this.rightFragmentOpenedProgress = f;
            invalidate();
        }
    }

    public void setIsTransitionSupport(boolean z) {
        this.isTransitionSupport = z;
    }

    public void setIsShareToStoryCell() {
        this.repostStoryDrawable = new ShareDialogCell.RepostStoryDrawable(getContext(), this, C2369R.drawable.forward_to_stories, this.resourcesProvider);
        this.isShareToStoryCell = true;
    }

    public void checkHeight() {
        if (getMeasuredHeight() <= 0 || getMeasuredHeight() == computeHeight()) {
            return;
        }
        requestLayout();
    }

    public void setVisible(boolean z) {
        if (this.visibleOnScreen == z) {
            return;
        }
        this.visibleOnScreen = z;
        if (z) {
            invalidate();
        }
    }

    public static class FixedWidthSpan extends ReplacementSpan {
        private int width;

        @Override // android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
        }

        public FixedWidthSpan(int i) {
            this.width = i;
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            if (fontMetricsInt == null) {
                fontMetricsInt = paint.getFontMetricsInt();
            }
            if (fontMetricsInt != null) {
                int i3 = 1 - (fontMetricsInt.descent - fontMetricsInt.ascent);
                fontMetricsInt.descent = i3;
                fontMetricsInt.bottom = i3;
                fontMetricsInt.ascent = -1;
                fontMetricsInt.top = -1;
            }
            return this.width;
        }
    }

    public void setOpenBotButton(boolean z) {
        if (this.openBot == z) {
            return;
        }
        if (this.openButtonText == null) {
            this.openButtonText = new Text(LocaleController.getString(C2369R.string.BotOpen), 14.0f, AndroidUtilities.bold());
        }
        this.openBot = z;
        this.openButtonBounce.setPressed(false);
    }

    public DialogCell allowBotOpenButton(boolean z, Utilities.Callback callback) {
        this.allowBotOpenButton = z;
        this.onOpenButtonClick = callback;
        return this;
    }

    public boolean isBlocked() {
        return this.premiumBlocked;
    }

    public long getStarsPrice() {
        return this.starsPriceBlocked;
    }

    public DialogCell(DialogsActivity dialogsActivity, Context context, boolean z, boolean z2) {
        this(dialogsActivity, context, z, z2, UserConfig.selectedAccount, null);
    }

    public DialogCell(DialogsActivity dialogsActivity, Context context, boolean z, boolean z2, int i, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.drawArchive = true;
        this.drawAvatar = true;
        this.drawMonoforumAvatar = false;
        this.avatarStart = 10;
        this.messagePaddingStart = 72;
        this.heightDefault = 72;
        this.heightThreeLines = 78;
        this.addHeightForTags = 3;
        this.addForumHeightForTags = 11;
        this.chekBoxPaddingTop = 42.0f;
        StoriesUtilities.AvatarStoryParams avatarStoryParams = new StoriesUtilities.AvatarStoryParams(false) { // from class: org.telegram.ui.Cells.DialogCell.1
            @Override // org.telegram.ui.Stories.StoriesUtilities.AvatarStoryParams
            public void openStory(long j, Runnable runnable) {
                DialogCell dialogCell = DialogCell.this;
                if (dialogCell.delegate == null) {
                    return;
                }
                if (dialogCell.currentDialogFolderId != 0) {
                    DialogCell.this.delegate.openHiddenStories();
                    return;
                }
                DialogCell dialogCell2 = DialogCell.this;
                DialogCellDelegate dialogCellDelegate = dialogCell2.delegate;
                if (dialogCellDelegate != null) {
                    dialogCellDelegate.openStory(dialogCell2, runnable);
                }
            }

            @Override // org.telegram.ui.Stories.StoriesUtilities.AvatarStoryParams
            public void onLongPress() {
                DialogCell dialogCell = DialogCell.this;
                DialogCellDelegate dialogCellDelegate = dialogCell.delegate;
                if (dialogCellDelegate == null) {
                    return;
                }
                dialogCellDelegate.showChatPreview(dialogCell);
            }
        };
        this.storyParams = avatarStoryParams;
        this.visibleOnScreen = true;
        this.collapseOffset = 0.0f;
        this.hasUnmutedTopics = false;
        this.openButtonBounce = new ButtonBounce(this);
        this.openButtonBackgroundPaint = new Paint(1);
        this.openButtonRect = new RectF();
        this.overrideSwipeAction = false;
        this.thumbImageSeen = new boolean[3];
        this.thumbImage = new ImageReceiver[3];
        this.drawPlay = new boolean[3];
        this.drawSpoiler = new boolean[3];
        this.avatarImage = new ImageReceiver(this);
        this.avatarDrawable = new AvatarDrawable();
        this.interpolator = new BounceInterpolator();
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        this.premiumBlockedT = new AnimatedFloat(this, 0L, 350L, cubicBezierInterpolator);
        this.starsBlockedT = new AnimatedFloat(this, 0L, 350L, cubicBezierInterpolator);
        this.spoilersPool = new Stack();
        this.spoilers = new ArrayList();
        this.spoilersPool2 = new Stack();
        this.spoilers2 = new ArrayList();
        this.drawCount2 = true;
        this.countChangeProgress = 1.0f;
        this.reactionsMentionsChangeProgress = 1.0f;
        this.rect = new RectF();
        this.lastStatusDrawableParams = -1;
        this.readOutboxMaxId = -1;
        this.updateHelper = new DialogUpdateHelper();
        avatarStoryParams.allowLongress = true;
        this.resourcesProvider = resourcesProvider;
        this.parentFragment = dialogsActivity;
        Theme.createDialogsResources(context);
        this.drawMonoforumAvatar = false;
        this.avatarImage.setRoundRadius(ExteraConfig.getAvatarCorners(56.0f));
        int i2 = 0;
        while (true) {
            ImageReceiver[] imageReceiverArr = this.thumbImage;
            if (i2 < imageReceiverArr.length) {
                imageReceiverArr[i2] = new ImageReceiver(this);
                ImageReceiver imageReceiver = this.thumbImage[i2];
                imageReceiver.ignoreNotifications = true;
                imageReceiver.setRoundRadius(AndroidUtilities.m1146dp(2.0f));
                this.thumbImage[i2].setAllowLoadingOnAttachedOnly(true);
                i2++;
            } else {
                this.useForceThreeLines = z2;
                this.currentAccount = i;
                View view = new View(context) { // from class: org.telegram.ui.Cells.DialogCell.2
                    @Override // android.view.View
                    protected void onDraw(Canvas canvas) {
                        DialogCell.this.emojiStatus.setBounds(0, 0, getWidth(), getHeight());
                        DialogCell.this.emojiStatus.draw(canvas);
                    }
                };
                this.emojiStatusView = view;
                addView(view);
                this.emojiStatus = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(view, AndroidUtilities.m1146dp(22.0f));
                this.botVerification = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(this, AndroidUtilities.m1146dp(17.0f));
                this.avatarImage.setAllowLoadingOnAttachedOnly(true);
                AvatarSpan avatarSpan = new AvatarSpan(this, this.currentAccount, 18.0f);
                this.messageAvatarSpan = avatarSpan;
                avatarSpan.needDrawShadow = false;
                return;
            }
        }
    }

    public void setCustomMessage(String str) {
        if (TextUtils.equals(this.customMessage, str)) {
            return;
        }
        this.customMessage = str;
        buildLayout();
        requestLayout();
    }

    public void setDialog(TLRPC.Dialog dialog, int i, int i2) {
        if (this.currentDialogId != dialog.f1577id) {
            ValueAnimator valueAnimator = this.statusDrawableAnimator;
            if (valueAnimator != null) {
                valueAnimator.removeAllListeners();
                this.statusDrawableAnimator.cancel();
            }
            this.statusDrawableAnimationInProgress = false;
            this.lastStatusDrawableParams = -1;
        }
        this.currentDialogId = dialog.f1577id;
        this.lastDialogChangedTime = System.currentTimeMillis();
        this.isDialogCell = true;
        if (dialog instanceof TLRPC.TL_dialogFolder) {
            this.currentDialogFolderId = ((TLRPC.TL_dialogFolder) dialog).folder.f1630id;
            PullForegroundDrawable pullForegroundDrawable = this.archivedChatsDrawable;
            if (pullForegroundDrawable != null) {
                pullForegroundDrawable.setCell(this);
            }
        } else {
            this.currentDialogFolderId = 0;
        }
        this.dialogsType = i;
        showPremiumBlocked(i == 3);
        if (this.tags == null) {
            this.tags = new DialogCellTags(this);
        }
        this.folderId = i2;
        this.messageId = 0;
        if (update(0, false)) {
            requestLayout();
        }
        checkOnline();
        checkGroupCall();
        checkChatTheme();
        checkTtl();
    }

    public void setDialog(CustomDialog customDialog) {
        this.customDialog = customDialog;
        this.messageId = 0;
        update(0);
        checkOnline();
        checkGroupCall();
        checkChatTheme();
        checkTtl();
    }

    private void checkOnline() {
        TLRPC.User user;
        if (this.user != null && (user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.user.f1734id))) != null) {
            this.user = user;
        }
        this.onlineProgress = isOnline() ? 1.0f : 0.0f;
    }

    private boolean isOnline() {
        TLRPC.User user;
        if (!isForumCell() && !this.storyParams.drawnLive && (user = this.user) != null && !user.self) {
            TLRPC.UserStatus userStatus = user.status;
            if (userStatus != null && userStatus.expires <= 0 && MessagesController.getInstance(this.currentAccount).onlinePrivacy.containsKey(Long.valueOf(this.user.f1734id))) {
                return true;
            }
            TLRPC.UserStatus userStatus2 = this.user.status;
            if (userStatus2 != null && userStatus2.expires > ConnectionsManager.getInstance(this.currentAccount).getCurrentTime()) {
                return true;
            }
        }
        return false;
    }

    private void checkGroupCall() {
        TLRPC.Chat chat = this.chat;
        boolean z = chat != null && chat.call_active && chat.call_not_empty;
        this.hasCall = z;
        this.chatCallProgress = z ? 1.0f : 0.0f;
    }

    private void checkTtl() {
        CheckBox2 checkBox2;
        boolean z = this.ttlPeriod > 0 && !this.hasCall && !isOnline() && ((checkBox2 = this.checkBox) == null || !checkBox2.isChecked()) && !this.storyParams.drawnLive;
        this.showTtl = z;
        this.ttlProgress = z ? 1.0f : 0.0f;
    }

    private void checkChatTheme() {
        TLRPC.Message message;
        MessageObject messageObject = this.message;
        if (messageObject == null || (message = messageObject.messageOwner) == null) {
            return;
        }
        TLRPC.MessageAction messageAction = message.action;
        if ((messageAction instanceof TLRPC.TL_messageActionSetChatTheme) && this.lastUnreadState) {
            ChatThemeController.getInstance(this.currentAccount).setDialogTheme(this.currentDialogId, ((TLRPC.TL_messageActionSetChatTheme) messageAction).theme, false);
        }
    }

    public void setDialog(long j, MessageObject messageObject, int i, boolean z, boolean z2) {
        if (this.currentDialogId != j) {
            this.lastStatusDrawableParams = -1;
        }
        this.currentDialogId = j;
        this.lastDialogChangedTime = System.currentTimeMillis();
        this.message = messageObject;
        filterCurrentMessage();
        this.useMeForMyMessages = z;
        this.isDialogCell = false;
        this.lastMessageDate = i;
        this.currentEditDate = messageObject != null ? messageObject.messageOwner.edit_date : 0;
        this.unreadCount = 0;
        this.markUnread = false;
        this.messageId = messageObject != null ? messageObject.getId() : 0;
        this.mentionCount = 0;
        this.reactionMentionCount = 0;
        this.lastUnreadState = messageObject != null && messageObject.isUnread();
        MessageObject messageObject2 = this.message;
        if (messageObject2 != null) {
            this.lastSendState = messageObject2.messageOwner.send_state;
        }
        update(0, z2);
    }

    public long getDialogId() {
        return this.currentDialogId;
    }

    public int getMessageId() {
        return this.messageId;
    }

    public void setPreloader(DialogsAdapter.DialogsPreloader dialogsPreloader) {
        this.preloader = dialogsPreloader;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.isSliding = false;
        this.drawRevealBackground = false;
        this.currentRevealProgress = 0.0f;
        this.attachedToWindow = false;
        this.reorderIconProgress = (getIsPinned() && this.drawReorder) ? 1.0f : 0.0f;
        this.avatarImage.onDetachedFromWindow();
        int i = 0;
        while (true) {
            ImageReceiver[] imageReceiverArr = this.thumbImage;
            if (i >= imageReceiverArr.length) {
                break;
            }
            imageReceiverArr[i].onDetachedFromWindow();
            i++;
        }
        RLottieDrawable rLottieDrawable = this.translationDrawable;
        if (rLottieDrawable != null) {
            rLottieDrawable.stop();
            this.translationDrawable.setProgress(0.0f);
            this.translationDrawable.setCallback(null);
            this.translationDrawable = null;
            this.translationAnimationStarted = false;
        }
        DialogsAdapter.DialogsPreloader dialogsPreloader = this.preloader;
        if (dialogsPreloader != null) {
            dialogsPreloader.remove(this.currentDialogId);
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.emojiStatus;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.detach();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = this.botVerification;
        if (swapAnimatedEmojiDrawable2 != null) {
            swapAnimatedEmojiDrawable2.detach();
        }
        AnimatedEmojiSpan.release(this, this.animatedEmojiStack);
        AnimatedEmojiSpan.release(this, this.animatedEmojiStack2);
        AnimatedEmojiSpan.release(this, this.animatedEmojiStack3);
        AnimatedEmojiSpan.release(this, this.animatedEmojiStackName);
        this.storyParams.onDetachFromWindow();
        this.canvasButton = null;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.avatarImage.onAttachedToWindow();
        int i = 0;
        while (true) {
            ImageReceiver[] imageReceiverArr = this.thumbImage;
            if (i >= imageReceiverArr.length) {
                break;
            }
            imageReceiverArr[i].onAttachedToWindow();
            i++;
        }
        resetPinnedArchiveState();
        this.animatedEmojiStack = AnimatedEmojiSpan.update(0, this, this.animatedEmojiStack, this.messageLayout);
        this.animatedEmojiStack2 = AnimatedEmojiSpan.update(0, this, this.animatedEmojiStack2, this.messageNameLayout);
        this.animatedEmojiStack3 = AnimatedEmojiSpan.update(0, this, this.animatedEmojiStack3, this.buttonLayout);
        this.animatedEmojiStackName = AnimatedEmojiSpan.update(0, this, this.animatedEmojiStackName, this.nameLayout);
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.emojiStatus;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.attach();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = this.botVerification;
        if (swapAnimatedEmojiDrawable2 != null) {
            swapAnimatedEmojiDrawable2.attach();
        }
    }

    public void resetPinnedArchiveState() {
        boolean z = SharedConfig.archiveHidden;
        this.archiveHidden = z;
        float f = z ? 0.0f : 1.0f;
        this.archiveBackgroundProgress = f;
        this.avatarDrawable.setArchivedAvatarHiddenProgress(f);
        this.clipProgress = 0.0f;
        this.isSliding = false;
        this.reorderIconProgress = (getIsPinned() && this.drawReorder) ? 1.0f : 0.0f;
        this.attachedToWindow = true;
        this.cornerProgress = 0.0f;
        setTranslationX(0.0f);
        setTranslationY(0.0f);
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.emojiStatus;
        if (swapAnimatedEmojiDrawable != null && this.attachedToWindow) {
            swapAnimatedEmojiDrawable.attach();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = this.botVerification;
        if (swapAnimatedEmojiDrawable2 == null || !this.attachedToWindow) {
            return;
        }
        swapAnimatedEmojiDrawable2.attach();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        View view = this.emojiStatusView;
        if (view != null) {
            view.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(22.0f), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(22.0f), TLObject.FLAG_30));
        }
        CheckBox2 checkBox2 = this.checkBox;
        if (checkBox2 != null) {
            checkBox2.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(24.0f), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(24.0f), TLObject.FLAG_30));
        }
        if (this.isTopic) {
            setMeasuredDimension(View.MeasureSpec.getSize(i), AndroidUtilities.m1146dp(((this.useForceThreeLines || SharedConfig.useThreeLinesLayout) ? this.heightThreeLines : this.heightDefault) + ((!hasTags() || ((this.useForceThreeLines || SharedConfig.useThreeLinesLayout) && !isForumCell())) ? 0 : isForumCell() ? this.addForumHeightForTags : this.addHeightForTags)) + (this.useSeparator ? 1 : 0));
            checkTwoLinesForName();
        }
        setMeasuredDimension(View.MeasureSpec.getSize(i), computeHeight());
        this.topClip = 0;
        this.bottomClip = getMeasuredHeight();
    }

    private int computeHeight() {
        if (isForumCell() && !this.isTransitionSupport && !this.collapsed) {
            int iM1146dp = AndroidUtilities.m1146dp((this.useForceThreeLines || SharedConfig.useThreeLinesLayout) ? 86.0f : 91.0f);
            if (this.useSeparator) {
                iM1146dp++;
            }
            return hasTags() ? iM1146dp + AndroidUtilities.m1146dp(this.addForumHeightForTags) : iM1146dp;
        }
        return getCollapsedHeight();
    }

    private int getCollapsedHeight() {
        int iM1146dp = AndroidUtilities.m1146dp((this.useForceThreeLines || SharedConfig.useThreeLinesLayout) ? this.heightThreeLines : this.heightDefault);
        if (this.useSeparator) {
            iM1146dp++;
        }
        if (this.twoLinesForName) {
            iM1146dp += AndroidUtilities.m1146dp(20.0f);
        }
        if (!hasTags()) {
            return iM1146dp;
        }
        if ((this.useForceThreeLines || SharedConfig.useThreeLinesLayout) && !isForumCell()) {
            return iM1146dp;
        }
        return iM1146dp + AndroidUtilities.m1146dp(isForumCell() ? this.addForumHeightForTags : this.addHeightForTags);
    }

    private void checkTwoLinesForName() {
        this.twoLinesForName = false;
        if (!this.isTopic || hasTags()) {
            return;
        }
        buildLayout();
        if (this.nameIsEllipsized) {
            this.twoLinesForName = true;
            buildLayout();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iM1146dp;
        if (this.currentDialogId == 0 && this.customDialog == null) {
            return;
        }
        View view = this.emojiStatusView;
        if (view != null) {
            view.layout(0, 0, AndroidUtilities.m1146dp(22.0f), AndroidUtilities.m1146dp(22.0f));
        }
        if (this.checkBox != null) {
            int iM1146dp2 = AndroidUtilities.m1146dp(this.messagePaddingStart - ((this.useForceThreeLines || SharedConfig.useThreeLinesLayout) ? 26 : 28));
            if (this.inPreviewMode) {
                iM1146dp2 = AndroidUtilities.m1146dp(8.0f);
                iM1146dp = (getMeasuredHeight() - this.checkBox.getMeasuredHeight()) >> 1;
            } else {
                if (LocaleController.isRTL) {
                    iM1146dp2 = (i3 - i) - iM1146dp2;
                }
                iM1146dp = AndroidUtilities.m1146dp(this.chekBoxPaddingTop + ((this.useForceThreeLines || SharedConfig.useThreeLinesLayout) ? 4 : 0));
            }
            CheckBox2 checkBox2 = this.checkBox;
            checkBox2.layout(iM1146dp2, iM1146dp, checkBox2.getMeasuredWidth() + iM1146dp2, this.checkBox.getMeasuredHeight() + iM1146dp);
        }
        int measuredHeight = (getMeasuredHeight() + getMeasuredWidth()) << 16;
        if (measuredHeight != this.lastSize || this.updateLayout) {
            this.updateLayout = false;
            this.lastSize = measuredHeight;
            try {
                buildLayout();
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
    }

    public boolean getHasUnread() {
        return this.unreadCount != 0 || this.markUnread;
    }

    public boolean getIsMuted() {
        return this.dialogMuted;
    }

    public boolean getIsPinned() {
        return this.drawPin || this.drawPinForced;
    }

    public void setPinForced(boolean z) {
        this.drawPinForced = z;
        if (getMeasuredWidth() > 0 && getMeasuredHeight() > 0) {
            buildLayout();
        }
        invalidate();
    }

    private CharSequence formatArchivedDialogNames() {
        TLRPC.User user;
        String strEscape;
        MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
        ArrayList<TLRPC.Dialog> dialogs = messagesController.getDialogs(this.currentDialogFolderId);
        this.currentDialogFolderDialogsCount = dialogs.size();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int size = dialogs.size();
        for (int i = 0; i < size; i++) {
            TLRPC.Dialog dialog = dialogs.get(i);
            if (!messagesController.isHiddenByUndo(dialog.f1577id)) {
                TLRPC.Chat chat = null;
                if (DialogObject.isEncryptedDialog(dialog.f1577id)) {
                    TLRPC.EncryptedChat encryptedChat = messagesController.getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(dialog.f1577id)));
                    user = encryptedChat != null ? messagesController.getUser(Long.valueOf(encryptedChat.user_id)) : null;
                } else if (DialogObject.isUserDialog(dialog.f1577id)) {
                    user = messagesController.getUser(Long.valueOf(dialog.f1577id));
                } else {
                    chat = messagesController.getChat(Long.valueOf(-dialog.f1577id));
                    user = null;
                }
                if (chat != null) {
                    strEscape = chat.title.replace('\n', ' ');
                } else if (user == null) {
                    continue;
                } else if (UserObject.isDeleted(user)) {
                    strEscape = LocaleController.getString(C2369R.string.HiddenName);
                } else {
                    strEscape = AndroidUtilities.escape(ContactsController.formatName(user.first_name, user.last_name).replace('\n', ' '));
                }
                if (spannableStringBuilder.length() > 0) {
                    spannableStringBuilder.append((CharSequence) ", ");
                }
                int length = spannableStringBuilder.length();
                int length2 = strEscape.length() + length;
                spannableStringBuilder.append((CharSequence) strEscape);
                if (dialog.unread_count > 0) {
                    spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold(), 0, Theme.getColor(Theme.key_chats_nameArchived, this.resourcesProvider)), length, length2, 33);
                }
                if (spannableStringBuilder.length() > 150) {
                    break;
                }
            }
        }
        if (MessagesController.getInstance(this.currentAccount).storiesController.getTotalStoriesCount(true) > 0) {
            int iMax = Math.max(1, MessagesController.getInstance(this.currentAccount).storiesController.getTotalStoriesCount(true));
            if (spannableStringBuilder.length() > 0) {
                spannableStringBuilder.append((CharSequence) ", ");
            }
            spannableStringBuilder.append((CharSequence) LocaleController.formatPluralString("Stories", iMax, new Object[0]));
        }
        return Emoji.replaceEmoji(spannableStringBuilder, Theme.dialogs_messagePaint[this.paintIndex].getFontMetricsInt(), false);
    }

    public boolean hasTags() {
        DialogCellTags dialogCellTags = this.tags;
        return (dialogCellTags == null || dialogCellTags.isEmpty()) ? false : true;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(94:14|(1:20)(1:19)|21|(1:28)(1:27)|29|(1:34)(1:33)|35|(1:37)|38|(1:49)(1:50)|51|(1:53)|54|(1:56)(1:57)|58|(7:60|(1:62)|1939|63|(1:65)|1940|66)(1:67)|68|(1:70)(1:71)|72|(9:74|(2:76|(2:85|(1:87)(1:88))(2:81|(1:83)(1:84)))(4:89|(1:94)(1:93)|95|(2:104|(1:106)(1:107))(2:100|(1:102)(1:103)))|108|(3:110|(1:112)(4:113|(1:115)|116|(1:122)(1:121))|123)(3:124|(1:126)|127)|128|(1:130)(1:131)|132|(1:134)(1:(1:136)(1:(1:138)(1:139)))|140)(87:142|(1:154)(1:152)|153|155|(2:164|(1:166)(1:167))(2:160|(1:162)(1:163))|168|(2:170|(2:172|(2:182|(1:184)(1:185))(2:177|(1:179)(1:181))))(91:186|(5:188|(4:190|(1:192)(2:194|(1:196)(3:197|(1:199)(2:200|(5:205|(1:210)(1:209)|211|(1:216)(1:215)|217)(1:204))|218))|193|218)(2:220|(9:222|(1:224)(2:225|(1:227)(5:228|(1:233)(1:232)|234|(1:241)(1:240)|242))|243|(1:249)(1:248)|250|(4:252|(0)(1:256)|259|(2:261|(1:263)(2:264|(1:266)(1:267)))(2:268|(1:270)))(1:257)|258|259|(0)(0))(3:271|272|(1:276)))|219|272|(2:274|276))|277|(1:281)|282|(6:284|(1:286)(1:287)|288|(1:290)(1:291)|292|(1:296))(2:297|(5:303|(1:305)(1:306)|307|(1:309)(1:310)|311)(1:302))|312|(9:353|354|(11:356|(1:358)(1:359)|360|(3:(1:363)|364|(1:366))|367|(1:369)(1:370)|371|(1:373)(1:374)|375|(2:377|(1:380))|381)(2:384|(1:386)(7:(6:388|(1:390)(1:391)|392|(1:394)(1:395)|(1:397)(1:398)|399)(1:400)|401|(5:405|882|(7:896|(1:898)(2:899|(2:901|(1:903))(1:904))|905|(2:907|(3:911|(1:913)(1:914)|915))(2:916|(1:918))|919|(1:925)|926)(2:886|(2:892|893)(1:894))|895|893)(4:406|(1:408)(2:410|(2:412|(1:414)(3:415|(2:417|(1:419)(2:420|(1:422)(2:423|(1:425)(2:426|(2:428|(1:430)(1:431))))))(2:433|(0)(3:437|(1:442)(1:441)|443))|432))(4:444|(1:446)(1:447)|448|(3:477|478|(7:481|(3:483|(2:485|(2:487|(2:489|(1:491)(2:492|(1:494)(1:495)))(2:496|(1:498)(2:499|(1:501)(2:502|(1:504)(1:505)))))(0))(1:506)|507)(2:509|(7:520|(2:528|(15:547|(1:556)(1:555)|557|(2:571|(5:573|(3:575|(0)|587)(1:588)|589|(13:598|(2:601|(5:603|(1:610)|609|611|(1:615))(2:616|(2:623|(2:625|(2:630|(1:632)(2:633|(1:635)(1:636)))(2:637|(5:639|(1:641)(2:642|(1:644)(2:645|(1:647)(2:648|(1:650)(2:651|(1:653)(1:654)))))|655|(3:671|(3:673|(1:675)(1:676)|677)|678)(4:659|(2:661|(1:663)(1:664))|(1:668)|669)|670)(2:679|(3:681|(1:(1:684)(1:685))(1:(1:687)(1:688))|689)(2:690|(4:692|(3:694|(1:696)(1:697)|698)(3:700|(1:702)(1:703)|704)|699|705)(5:706|(2:708|(4:710|(1:712)(1:713)|(1:715)(1:716)|717)(0))(2:719|(1:721)(2:722|(2:724|(1:729)(1:728))(2:730|(2:732|(1:737)(1:736))(2:738|(1:740)(2:741|(1:743)(2:744|(1:746)(2:747|(3:761|(4:768|(1:770)|771|(2:773|(3:775|(1:777)(1:778)|779)))(2:765|(1:767))|780)(2:751|(3:753|(2:755|(1:757))(1:758)|759)(1:760)))))))))|718|781|(1:785))))))(0))(1:622)))|786|(1:788)(1:789)|790|(7:792|(3:799|(1:801)|802)(2:796|(1:798))|803|(1:805)|806|(1:812)(1:810)|811)(1:813)|814|(1:818)|819|878|(1:880)|881|383)|587)(0))(1:568)|569|(1:(1:823)(1:824))|825|(2:833|(3:835|(1:841)(1:840)|842))|843|(1:862)(5:1913|853|854|1931|855)|863|(1:867)|868|(4:870|(1:872)|873|(1:875)(1:876))|877)(9:534|(2:539|(1:541)(1:542))(1:538)|543|(1:545)(1:546)|480|878|(0)|881|383))(1:526)|527|878|(0)|881|383)(3:513|(1:518)(1:517)|519))|508|878|(0)|881|383)(5:480|878|(0)|881|383))(4:462|(3:464|(0)(3:468|(1:470)(3:472|(1:474)(1:475)|476)|471)|(0)(0))(0)|478|(0)(0))))|409|383)|927|(3:943|(1:947)|948)|949|(11:963|964|(3:1054|(1:1061)(1:1060)|1062)(4:969|(3:971|(1:(1:974)(1:975))(1:976)|977)(6:978|(1:980)(3:981|(2:991|(1:993)(1:994))(1:989)|990)|995|(1:997)(1:998)|999|(1:1001)(1:1002))|1003|(1:1053)(2:1008|(2:1010|(1:1012)(2:1013|(1:1015)(2:1016|(3:1018|(3:1020|(1:1022)(1:1023)|1024)(2:1025|(3:1027|(1:1038)(1:1039)|1040)(3:1041|(1:1049)(1:1048)|1050))|1051)(1:1052))))(0)))|1063|(2:1065|(7:1067|(1:1069)(6:1071|(4:1073|(1:1075)|1076|(1:1078))|1082|(1:1084)|1085|(1:1087)(2:1088|(5:1090|(4:1092|(1:1094)|1095|(4:1097|1098|(1:1154)|1155))(2:1100|(4:1102|(1:1104)|1105|(1:1107)(1:1108))(4:1109|(1:1118)(3:1113|(1:1115)(1:1116)|1117)|(2:1152|1154)|1155))|1099|(0)|1155)(4:1119|(2:1121|(1:1123)(2:1124|(1:1126)(2:1127|(2:1141|(4:1143|(1:1145)|1146|(1:1148)(1:1149))(1:1150))(2:1131|(1:1133)(2:1134|(1:1136)(3:1137|(1:1139)|1140))))))(1:1098)|(0)|1155)))|1070|1082|(0)|1085|(0)(0)))(1:1080)|1079|1081|1082|(0)|1085|(0)(0))(4:954|(1:956)(2:957|(1:959)(2:960|(1:962)(0)))|964|(3:966|1054|(11:1056|1061|1062|1063|(0)(0)|1079|1081|1082|(0)|1085|(0)(0))(0))(0))))|382|383|927|(10:929|931|933|935|937|939|941|943|(2:945|947)|948)|949|(4:951|963|964|(0)(0))(0))(2:331|(2:333|(10:341|(0)|354|(0)(0)|382|383|927|(0)|949|(0)(0))(0))(0))|(2:1157|(1:1159)(1:1160))(1:1161)|1162|(3:1164|(1:1166)(1:1167)|1168)(1:1169)|1170|(1:1172)(1:1173)|1174|(3:1176|(1:1178)|1179)|1180|(2:1182|(1:1184)(1:1185))(2:1187|(2:1189|(2:1191|(1:1193)(1:1194))(2:1195|(1:1197)(1:1198))))|1186|1199|(2:1206|(2:1216|(1:1218))(2:1219|(2:1221|(1:1223))(2:1224|(2:1226|(1:1228))(2:1229|(2:1231|(1:1233))(2:1234|(4:1236|(1:1238)(1:1239)|1240|(1:1242)))))))(2:1203|(1:1205))|1243|(1:1245)|1246|(2:1248|(1:1250))|1251|1937|1252|(1:1254)|1255|(4:1257|1258|1927|1259)(1:1265)|1266|1917|(8:1268|1269|1925|1270|1271|1272|(1:1274)(1:1275)|1276)(2:1281|(3:1283|(1:1285)(1:1286)|1287))|1288|(1:1290)(1:1291)|1292|(1:1294)|1295|(1:1302)(1:1301)|1303|(1:1305)(1:1306)|1307|(1:1312)(1:1311)|1313|1316|(6:1320|1353|(1:1355)(1:1356)|1357|(2:1358|(5:1360|(1:1362)(1:1363)|1364|(2:1373|1960)(2:1372|1961)|1374)(1:1959))|1375)(10:1321|(1:1323)(1:1324)|1325|(1:1327)(1:1328)|1329|(1:1331)(1:1333)|1332|1334|(2:1335|(5:1337|(1:1339)(1:1340)|1341|(2:1350|1942)(2:1349|1943)|1351)(1:1941))|1352)|1376|(1:1378)(1:1379)|1380|(1:1382)|1383|(1:1393)|1394|(2:1396|(1:1398)(1:1399))|1400|(2:1402|(1:1404)(1:1405))(1:(4:(4:1446|(1:1448)(1:1450)|1449|1451)(1:1452)|(6:1454|(1:1456)(1:1457)|1458|(3:1460|(1:1462)(1:1463)|1464)(3:1466|(1:1468)(1:1469)|1470)|1465|1471)(1:1472)|1473|(2:1475|(4:1477|(3:1479|(1:1481)(1:1482)|1483)|1484|(3:1486|(1:1488)(1:1489)|1490))(5:1491|(3:1493|(1:1495)(1:1496)|1497)|1498|(3:1500|(1:1502)(1:1503)|1504)|1505)))(2:1411|(3:1439|(2:1441|(1:1443))|1444)(6:1423|(1:1432)(2:1428|(1:1430)(1:1431))|1433|(1:1435)(1:1437)|1436|1438)))|(7:(1:1508)|1509|(1:1511)|1512|(1:1520)(1:1521)|1522|(1:1526))|1527|(1:1533)(1:1532)|1534|(3:1542|(1:1544)(1:1545)|1546)|1547|(4:1549|(1:1555)(1:1554)|1556|(2:1557|(1:1559)(1:1944)))(2:1560|(4:1573|1915|1574|(12:1583|1919|1584|1585|1921|1586|1587|1588|1594|(1:1599)(1:1598)|1600|(2:1601|(1:1603)(1:1958)))(11:1578|(1:1580)(0)|1919|1584|1585|1921|1586|1587|1588|1594|(4:1596|1599|1600|(3:1601|(0)(0)|1603))(0)))(3:1565|1604|(4:1610|(1:1615)(1:1614)|1616|(2:1617|(1:1619)(1:1957)))(1:1609)))|1620|(1:1622)|1623|1935|1624|(1:1626)(1:1627)|1934|1628|1933|1629|(2:1631|(2:1639|(1:1641)(7:1636|1642|1643|1923|1644|1645|1646))(0))|1911|1651|(3:1653|(5:1655|(1:1676)(2:1666|(0))|1677|(1:1948)(2:1681|1946)|1682)|1945)|1683|(1:1693)(3:1694|(2:1707|(1:1714)(1:1713))|1706)|1715|(1:1721)(1:1719)|1720|1722|(9:1728|(2:1731|1732)|1733|1929|1734|(1:1736)(1:1737)|1738|1739|1752)(7:1742|(2:1744|(1:1748))|1749|1909|1750|1751|1752)|1753|1757|(10:1759|(8:1763|(1:1765)|1766|(1:1768)|1769|(1:1779)(2:1780|(1:1782)(2:1783|(1:1785)(2:1786|(1:1788)(2:1789|(3:1791|(1:1793)(1:1794)|1795)(1:1796)))))|1797|(2:1799|(1:1801)))|1802|(3:1806|(1:(1:1950)(2:1808|(1:1810)(2:1949|1811)))|(1:1813))|1814|(3:1818|(1:(1:1951)(2:1820|(1:1822)(2:1952|1823)))|(1:1825))|1826|(2:1832|(1:1834))|1835|(4:1839|(1:1841)|1953|1842))(10:1843|(7:1847|(1:1849)|1850|(4:1852|(1:1854)|1855|(1:1857))|1858|(1:1860)|1861)|1862|(4:1866|(1:1868)|1954|1869)|1870|(4:1874|(1:1876)|1955|1877)|1878|(4:1882|(1:1884)|1956|1885)|1886|(1:1890))|1891|(3:(1:1901)(1:1900)|1902|(1:1904)(1:1905))|1906|1907)|180|277|(2:279|281)|282|(0)(0)|312|(13:314|317|319|321|353|354|(0)(0)|382|383|927|(0)|949|(0)(0))(12:317|319|321|353|354|(0)(0)|382|383|927|(0)|949|(0)(0))|(0)(0)|1162|(0)(0)|1170|(0)(0)|1174|(0)|1180|(0)(0)|1186|1199|(3:1201|1206|(6:1208|1210|1212|1214|1216|(0))(5:1210|1212|1214|1216|(0)))(0)|1243|(0)|1246|(0)|1251|1937|1252|(0)|1255|(0)(0)|1266|1917|(0)(0)|1288|(0)(0)|1292|(0)|1295|(6:1297|1302|1303|(0)(0)|1307|(3:1309|1312|1313)(0))(0)|1316|(7:1318|1320|1353|(0)(0)|1357|(3:1358|(0)(0)|1374)|1375)(0)|1376|(0)(0)|1380|(0)|1383|(4:1387|1389|1391|1393)(4:1385|1389|1391|1393)|1394|(0)|1400|(0)(0)|(0)|1527|(2:1529|1533)(0)|1534|(5:1538|1540|1542|(0)(0)|1546)(5:1536|1540|1542|(0)(0)|1546)|1547|(0)(0)|1620|(0)|1623|1935|1624|(0)(0)|1934|1628|1933|1629|(0)|1911|1651|(0)|1683|(7:1685|1687|1689|1691|1693|1715|(5:1717|1721|1720|1722|(11:1724|1726|1728|(2:1731|1732)|1733|1929|1734|(0)(0)|1738|1739|1752)(10:1726|1728|(0)|1733|1929|1734|(0)(0)|1738|1739|1752))(0))(6:1687|1689|1691|1693|1715|(0)(0))|1753|1757|(0)(0)|1891|(3:1893|1895|(4:1898|1901|1902|(0)(0))(0))|1906|1907)|141|(0)(0)|1162|(0)(0)|1170|(0)(0)|1174|(0)|1180|(0)(0)|1186|1199|(0)(0)|1243|(0)|1246|(0)|1251|1937|1252|(0)|1255|(0)(0)|1266|1917|(0)(0)|1288|(0)(0)|1292|(0)|1295|(0)(0)|1316|(0)(0)|1376|(0)(0)|1380|(0)|1383|(0)(0)|1394|(0)|1400|(0)(0)|(0)|1527|(0)(0)|1534|(0)(0)|1547|(0)(0)|1620|(0)|1623|1935|1624|(0)(0)|1934|1628|1933|1629|(0)|1911|1651|(0)|1683|(0)(0)|1753|1757|(0)(0)|1891|(0)|1906|1907) */
    /* JADX WARN: Code restructure failed: missing block: B:1263:0x1a42, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1264:0x1a43, code lost:
    
        r24 = 36.0f;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1277:0x1a78, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1278:0x1a79, code lost:
    
        r31 = r31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1637:0x2304, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1664:0x238c, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:1053:0x1557  */
    /* JADX WARN: Removed duplicated region for block: B:1054:0x1561  */
    /* JADX WARN: Removed duplicated region for block: B:1061:0x157f  */
    /* JADX WARN: Removed duplicated region for block: B:1065:0x159a  */
    /* JADX WARN: Removed duplicated region for block: B:1080:0x15ee  */
    /* JADX WARN: Removed duplicated region for block: B:1084:0x15f6  */
    /* JADX WARN: Removed duplicated region for block: B:1087:0x1602  */
    /* JADX WARN: Removed duplicated region for block: B:1088:0x1614  */
    /* JADX WARN: Removed duplicated region for block: B:1152:0x1785  */
    /* JADX WARN: Removed duplicated region for block: B:1157:0x179f  */
    /* JADX WARN: Removed duplicated region for block: B:1161:0x17db  */
    /* JADX WARN: Removed duplicated region for block: B:1164:0x17ec  */
    /* JADX WARN: Removed duplicated region for block: B:1169:0x181a  */
    /* JADX WARN: Removed duplicated region for block: B:1172:0x181f  */
    /* JADX WARN: Removed duplicated region for block: B:1173:0x1830  */
    /* JADX WARN: Removed duplicated region for block: B:1176:0x1851  */
    /* JADX WARN: Removed duplicated region for block: B:1182:0x186d  */
    /* JADX WARN: Removed duplicated region for block: B:1187:0x189b  */
    /* JADX WARN: Removed duplicated region for block: B:1201:0x1921  */
    /* JADX WARN: Removed duplicated region for block: B:1206:0x1942  */
    /* JADX WARN: Removed duplicated region for block: B:1218:0x196f  */
    /* JADX WARN: Removed duplicated region for block: B:1245:0x1a01  */
    /* JADX WARN: Removed duplicated region for block: B:1248:0x1a0e  */
    /* JADX WARN: Removed duplicated region for block: B:1254:0x1a29  */
    /* JADX WARN: Removed duplicated region for block: B:1257:0x1a2e A[Catch: Exception -> 0x1a42, TRY_LEAVE, TryCatch #14 {Exception -> 0x1a42, blocks: (B:1252:0x1a20, B:1255:0x1a2a, B:1257:0x1a2e), top: B:1937:0x1a20 }] */
    /* JADX WARN: Removed duplicated region for block: B:1265:0x1a46  */
    /* JADX WARN: Removed duplicated region for block: B:1268:0x1a4c A[Catch: Exception -> 0x1a3b, TRY_LEAVE, TryCatch #9 {Exception -> 0x1a3b, blocks: (B:1259:0x1a36, B:1266:0x1a48, B:1268:0x1a4c), top: B:1927:0x1a36 }] */
    /* JADX WARN: Removed duplicated region for block: B:1281:0x1a7d A[Catch: Exception -> 0x1a78, TryCatch #4 {Exception -> 0x1a78, blocks: (B:1272:0x1a5b, B:1276:0x1a6a, B:1288:0x1aa1, B:1292:0x1ab7, B:1294:0x1abd, B:1295:0x1ac9, B:1297:0x1adc, B:1299:0x1ae2, B:1303:0x1af3, B:1305:0x1af7, B:1307:0x1b35, B:1309:0x1b39, B:1311:0x1b42, B:1313:0x1b4a, B:1306:0x1b14, B:1281:0x1a7d, B:1283:0x1a85, B:1287:0x1a9f), top: B:1917:0x1a4a }] */
    /* JADX WARN: Removed duplicated region for block: B:1290:0x1ab4  */
    /* JADX WARN: Removed duplicated region for block: B:1291:0x1ab6  */
    /* JADX WARN: Removed duplicated region for block: B:1294:0x1abd A[Catch: Exception -> 0x1a78, TryCatch #4 {Exception -> 0x1a78, blocks: (B:1272:0x1a5b, B:1276:0x1a6a, B:1288:0x1aa1, B:1292:0x1ab7, B:1294:0x1abd, B:1295:0x1ac9, B:1297:0x1adc, B:1299:0x1ae2, B:1303:0x1af3, B:1305:0x1af7, B:1307:0x1b35, B:1309:0x1b39, B:1311:0x1b42, B:1313:0x1b4a, B:1306:0x1b14, B:1281:0x1a7d, B:1283:0x1a85, B:1287:0x1a9f), top: B:1917:0x1a4a }] */
    /* JADX WARN: Removed duplicated region for block: B:1297:0x1adc A[Catch: Exception -> 0x1a78, TryCatch #4 {Exception -> 0x1a78, blocks: (B:1272:0x1a5b, B:1276:0x1a6a, B:1288:0x1aa1, B:1292:0x1ab7, B:1294:0x1abd, B:1295:0x1ac9, B:1297:0x1adc, B:1299:0x1ae2, B:1303:0x1af3, B:1305:0x1af7, B:1307:0x1b35, B:1309:0x1b39, B:1311:0x1b42, B:1313:0x1b4a, B:1306:0x1b14, B:1281:0x1a7d, B:1283:0x1a85, B:1287:0x1a9f), top: B:1917:0x1a4a }] */
    /* JADX WARN: Removed duplicated region for block: B:1302:0x1af1  */
    /* JADX WARN: Removed duplicated region for block: B:1305:0x1af7 A[Catch: Exception -> 0x1a78, TryCatch #4 {Exception -> 0x1a78, blocks: (B:1272:0x1a5b, B:1276:0x1a6a, B:1288:0x1aa1, B:1292:0x1ab7, B:1294:0x1abd, B:1295:0x1ac9, B:1297:0x1adc, B:1299:0x1ae2, B:1303:0x1af3, B:1305:0x1af7, B:1307:0x1b35, B:1309:0x1b39, B:1311:0x1b42, B:1313:0x1b4a, B:1306:0x1b14, B:1281:0x1a7d, B:1283:0x1a85, B:1287:0x1a9f), top: B:1917:0x1a4a }] */
    /* JADX WARN: Removed duplicated region for block: B:1306:0x1b14 A[Catch: Exception -> 0x1a78, TryCatch #4 {Exception -> 0x1a78, blocks: (B:1272:0x1a5b, B:1276:0x1a6a, B:1288:0x1aa1, B:1292:0x1ab7, B:1294:0x1abd, B:1295:0x1ac9, B:1297:0x1adc, B:1299:0x1ae2, B:1303:0x1af3, B:1305:0x1af7, B:1307:0x1b35, B:1309:0x1b39, B:1311:0x1b42, B:1313:0x1b4a, B:1306:0x1b14, B:1281:0x1a7d, B:1283:0x1a85, B:1287:0x1a9f), top: B:1917:0x1a4a }] */
    /* JADX WARN: Removed duplicated region for block: B:1312:0x1b49  */
    /* JADX WARN: Removed duplicated region for block: B:1318:0x1b75  */
    /* JADX WARN: Removed duplicated region for block: B:1320:0x1b79  */
    /* JADX WARN: Removed duplicated region for block: B:1355:0x1cf3  */
    /* JADX WARN: Removed duplicated region for block: B:1356:0x1d14  */
    /* JADX WARN: Removed duplicated region for block: B:1360:0x1d52  */
    /* JADX WARN: Removed duplicated region for block: B:1378:0x1da3  */
    /* JADX WARN: Removed duplicated region for block: B:1379:0x1dba  */
    /* JADX WARN: Removed duplicated region for block: B:1382:0x1dcf  */
    /* JADX WARN: Removed duplicated region for block: B:1385:0x1ddc  */
    /* JADX WARN: Removed duplicated region for block: B:1387:0x1de0  */
    /* JADX WARN: Removed duplicated region for block: B:1396:0x1e08  */
    /* JADX WARN: Removed duplicated region for block: B:1402:0x1e2d  */
    /* JADX WARN: Removed duplicated region for block: B:1406:0x1e63  */
    /* JADX WARN: Removed duplicated region for block: B:1507:0x20db  */
    /* JADX WARN: Removed duplicated region for block: B:1529:0x2130  */
    /* JADX WARN: Removed duplicated region for block: B:1533:0x2138  */
    /* JADX WARN: Removed duplicated region for block: B:1536:0x2144  */
    /* JADX WARN: Removed duplicated region for block: B:1538:0x2148  */
    /* JADX WARN: Removed duplicated region for block: B:1544:0x215c  */
    /* JADX WARN: Removed duplicated region for block: B:1545:0x215f  */
    /* JADX WARN: Removed duplicated region for block: B:1549:0x216e  */
    /* JADX WARN: Removed duplicated region for block: B:1560:0x2193  */
    /* JADX WARN: Removed duplicated region for block: B:1583:0x21da  */
    /* JADX WARN: Removed duplicated region for block: B:1596:0x220e  */
    /* JADX WARN: Removed duplicated region for block: B:1599:0x2217  */
    /* JADX WARN: Removed duplicated region for block: B:1603:0x221e A[LOOP:12: B:1601:0x2219->B:1603:0x221e, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:1622:0x2272  */
    /* JADX WARN: Removed duplicated region for block: B:1626:0x2293 A[Catch: Exception -> 0x22e2, TryCatch #13 {Exception -> 0x22e2, blocks: (B:1624:0x228b, B:1626:0x2293, B:1627:0x22df), top: B:1935:0x228b }] */
    /* JADX WARN: Removed duplicated region for block: B:1627:0x22df A[Catch: Exception -> 0x22e2, TRY_LEAVE, TryCatch #13 {Exception -> 0x22e2, blocks: (B:1624:0x228b, B:1626:0x2293, B:1627:0x22df), top: B:1935:0x228b }] */
    /* JADX WARN: Removed duplicated region for block: B:1631:0x22f8 A[Catch: Exception -> 0x2304, TryCatch #12 {Exception -> 0x2304, blocks: (B:1629:0x22f2, B:1631:0x22f8, B:1633:0x22fc, B:1642:0x232e, B:1646:0x2358, B:1639:0x2306, B:1641:0x230c), top: B:1933:0x22f2 }] */
    /* JADX WARN: Removed duplicated region for block: B:1636:0x2301  */
    /* JADX WARN: Removed duplicated region for block: B:1653:0x2365 A[Catch: Exception -> 0x238c, TryCatch #1 {Exception -> 0x238c, blocks: (B:1651:0x2361, B:1653:0x2365, B:1655:0x2377, B:1657:0x237d, B:1659:0x2381, B:1661:0x2387, B:1666:0x2390, B:1668:0x2394, B:1670:0x2398, B:1672:0x239c, B:1674:0x23a0, B:1677:0x23ad, B:1679:0x23b3, B:1681:0x23b7, B:1682:0x23bd, B:1676:0x23aa, B:1683:0x23c0, B:1685:0x23c4, B:1694:0x23e4, B:1696:0x23e8, B:1707:0x240b, B:1709:0x2411, B:1711:0x2415, B:1713:0x2428, B:1715:0x2453, B:1717:0x2457, B:1719:0x245b, B:1722:0x2463, B:1724:0x2467, B:1742:0x24ad, B:1744:0x24b1, B:1746:0x24c4, B:1748:0x24ca, B:1752:0x24f5, B:1726:0x246b, B:1728:0x2471, B:1731:0x2477, B:1721:0x2460, B:1714:0x2443, B:1698:0x23ec, B:1701:0x23f4, B:1703:0x23fc, B:1687:0x23c8, B:1689:0x23ce, B:1691:0x23d2, B:1693:0x23d7), top: B:1911:0x2361 }] */
    /* JADX WARN: Removed duplicated region for block: B:1676:0x23aa A[Catch: Exception -> 0x238c, TryCatch #1 {Exception -> 0x238c, blocks: (B:1651:0x2361, B:1653:0x2365, B:1655:0x2377, B:1657:0x237d, B:1659:0x2381, B:1661:0x2387, B:1666:0x2390, B:1668:0x2394, B:1670:0x2398, B:1672:0x239c, B:1674:0x23a0, B:1677:0x23ad, B:1679:0x23b3, B:1681:0x23b7, B:1682:0x23bd, B:1676:0x23aa, B:1683:0x23c0, B:1685:0x23c4, B:1694:0x23e4, B:1696:0x23e8, B:1707:0x240b, B:1709:0x2411, B:1711:0x2415, B:1713:0x2428, B:1715:0x2453, B:1717:0x2457, B:1719:0x245b, B:1722:0x2463, B:1724:0x2467, B:1742:0x24ad, B:1744:0x24b1, B:1746:0x24c4, B:1748:0x24ca, B:1752:0x24f5, B:1726:0x246b, B:1728:0x2471, B:1731:0x2477, B:1721:0x2460, B:1714:0x2443, B:1698:0x23ec, B:1701:0x23f4, B:1703:0x23fc, B:1687:0x23c8, B:1689:0x23ce, B:1691:0x23d2, B:1693:0x23d7), top: B:1911:0x2361 }] */
    /* JADX WARN: Removed duplicated region for block: B:1685:0x23c4 A[Catch: Exception -> 0x238c, TryCatch #1 {Exception -> 0x238c, blocks: (B:1651:0x2361, B:1653:0x2365, B:1655:0x2377, B:1657:0x237d, B:1659:0x2381, B:1661:0x2387, B:1666:0x2390, B:1668:0x2394, B:1670:0x2398, B:1672:0x239c, B:1674:0x23a0, B:1677:0x23ad, B:1679:0x23b3, B:1681:0x23b7, B:1682:0x23bd, B:1676:0x23aa, B:1683:0x23c0, B:1685:0x23c4, B:1694:0x23e4, B:1696:0x23e8, B:1707:0x240b, B:1709:0x2411, B:1711:0x2415, B:1713:0x2428, B:1715:0x2453, B:1717:0x2457, B:1719:0x245b, B:1722:0x2463, B:1724:0x2467, B:1742:0x24ad, B:1744:0x24b1, B:1746:0x24c4, B:1748:0x24ca, B:1752:0x24f5, B:1726:0x246b, B:1728:0x2471, B:1731:0x2477, B:1721:0x2460, B:1714:0x2443, B:1698:0x23ec, B:1701:0x23f4, B:1703:0x23fc, B:1687:0x23c8, B:1689:0x23ce, B:1691:0x23d2, B:1693:0x23d7), top: B:1911:0x2361 }] */
    /* JADX WARN: Removed duplicated region for block: B:1687:0x23c8 A[Catch: Exception -> 0x238c, TryCatch #1 {Exception -> 0x238c, blocks: (B:1651:0x2361, B:1653:0x2365, B:1655:0x2377, B:1657:0x237d, B:1659:0x2381, B:1661:0x2387, B:1666:0x2390, B:1668:0x2394, B:1670:0x2398, B:1672:0x239c, B:1674:0x23a0, B:1677:0x23ad, B:1679:0x23b3, B:1681:0x23b7, B:1682:0x23bd, B:1676:0x23aa, B:1683:0x23c0, B:1685:0x23c4, B:1694:0x23e4, B:1696:0x23e8, B:1707:0x240b, B:1709:0x2411, B:1711:0x2415, B:1713:0x2428, B:1715:0x2453, B:1717:0x2457, B:1719:0x245b, B:1722:0x2463, B:1724:0x2467, B:1742:0x24ad, B:1744:0x24b1, B:1746:0x24c4, B:1748:0x24ca, B:1752:0x24f5, B:1726:0x246b, B:1728:0x2471, B:1731:0x2477, B:1721:0x2460, B:1714:0x2443, B:1698:0x23ec, B:1701:0x23f4, B:1703:0x23fc, B:1687:0x23c8, B:1689:0x23ce, B:1691:0x23d2, B:1693:0x23d7), top: B:1911:0x2361 }] */
    /* JADX WARN: Removed duplicated region for block: B:1717:0x2457 A[Catch: Exception -> 0x238c, TryCatch #1 {Exception -> 0x238c, blocks: (B:1651:0x2361, B:1653:0x2365, B:1655:0x2377, B:1657:0x237d, B:1659:0x2381, B:1661:0x2387, B:1666:0x2390, B:1668:0x2394, B:1670:0x2398, B:1672:0x239c, B:1674:0x23a0, B:1677:0x23ad, B:1679:0x23b3, B:1681:0x23b7, B:1682:0x23bd, B:1676:0x23aa, B:1683:0x23c0, B:1685:0x23c4, B:1694:0x23e4, B:1696:0x23e8, B:1707:0x240b, B:1709:0x2411, B:1711:0x2415, B:1713:0x2428, B:1715:0x2453, B:1717:0x2457, B:1719:0x245b, B:1722:0x2463, B:1724:0x2467, B:1742:0x24ad, B:1744:0x24b1, B:1746:0x24c4, B:1748:0x24ca, B:1752:0x24f5, B:1726:0x246b, B:1728:0x2471, B:1731:0x2477, B:1721:0x2460, B:1714:0x2443, B:1698:0x23ec, B:1701:0x23f4, B:1703:0x23fc, B:1687:0x23c8, B:1689:0x23ce, B:1691:0x23d2, B:1693:0x23d7), top: B:1911:0x2361 }] */
    /* JADX WARN: Removed duplicated region for block: B:1721:0x2460 A[Catch: Exception -> 0x238c, TryCatch #1 {Exception -> 0x238c, blocks: (B:1651:0x2361, B:1653:0x2365, B:1655:0x2377, B:1657:0x237d, B:1659:0x2381, B:1661:0x2387, B:1666:0x2390, B:1668:0x2394, B:1670:0x2398, B:1672:0x239c, B:1674:0x23a0, B:1677:0x23ad, B:1679:0x23b3, B:1681:0x23b7, B:1682:0x23bd, B:1676:0x23aa, B:1683:0x23c0, B:1685:0x23c4, B:1694:0x23e4, B:1696:0x23e8, B:1707:0x240b, B:1709:0x2411, B:1711:0x2415, B:1713:0x2428, B:1715:0x2453, B:1717:0x2457, B:1719:0x245b, B:1722:0x2463, B:1724:0x2467, B:1742:0x24ad, B:1744:0x24b1, B:1746:0x24c4, B:1748:0x24ca, B:1752:0x24f5, B:1726:0x246b, B:1728:0x2471, B:1731:0x2477, B:1721:0x2460, B:1714:0x2443, B:1698:0x23ec, B:1701:0x23f4, B:1703:0x23fc, B:1687:0x23c8, B:1689:0x23ce, B:1691:0x23d2, B:1693:0x23d7), top: B:1911:0x2361 }] */
    /* JADX WARN: Removed duplicated region for block: B:1730:0x2475 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1736:0x248c  */
    /* JADX WARN: Removed duplicated region for block: B:1737:0x248f  */
    /* JADX WARN: Removed duplicated region for block: B:1759:0x252e  */
    /* JADX WARN: Removed duplicated region for block: B:1843:0x270a  */
    /* JADX WARN: Removed duplicated region for block: B:1893:0x27f3  */
    /* JADX WARN: Removed duplicated region for block: B:1901:0x281e  */
    /* JADX WARN: Removed duplicated region for block: B:1904:0x2830  */
    /* JADX WARN: Removed duplicated region for block: B:1905:0x2838  */
    /* JADX WARN: Removed duplicated region for block: B:1958:0x226e A[EDGE_INSN: B:1958:0x226e->B:1620:0x226e BREAK  A[LOOP:12: B:1601:0x2219->B:1603:0x221e], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:1959:0x1d9a A[EDGE_INSN: B:1959:0x1d9a->B:1375:0x1d9a BREAK  A[LOOP:13: B:1358:0x1d4d->B:1374:0x1d84], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:261:0x05f1  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x063b  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0670  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x06ac  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x072c  */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0746  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x0755  */
    /* JADX WARN: Removed duplicated region for block: B:384:0x07e9  */
    /* JADX WARN: Removed duplicated region for block: B:477:0x0a59  */
    /* JADX WARN: Removed duplicated region for block: B:480:0x0a60 A[PHI: r5 r25
      0x0a60: PHI (r5v270 java.lang.CharSequence) = (r5v71 java.lang.CharSequence), (r5v247 java.lang.CharSequence), (r5v248 java.lang.CharSequence) binds: [B:479:0x0a5e, B:546:0x0b7a, B:545:0x0b6e] A[DONT_GENERATE, DONT_INLINE]
      0x0a60: PHI (r25v5 boolean) = (r25v0 boolean), (r25v1 boolean), (r25v2 boolean) binds: [B:479:0x0a5e, B:546:0x0b7a, B:545:0x0b6e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:481:0x0a69  */
    /* JADX WARN: Removed duplicated region for block: B:496:0x0aa5  */
    /* JADX WARN: Removed duplicated region for block: B:588:0x0bf4  */
    /* JADX WARN: Removed duplicated region for block: B:637:0x0cba  */
    /* JADX WARN: Removed duplicated region for block: B:713:0x0e27  */
    /* JADX WARN: Removed duplicated region for block: B:870:0x1217  */
    /* JADX WARN: Removed duplicated region for block: B:880:0x127a  */
    /* JADX WARN: Removed duplicated region for block: B:929:0x1364  */
    /* JADX WARN: Removed duplicated region for block: B:951:0x13dc  */
    /* JADX WARN: Removed duplicated region for block: B:963:0x1405  */
    /* JADX WARN: Removed duplicated region for block: B:966:0x140b  */
    /* JADX WARN: Type inference failed for: r0v31 */
    /* JADX WARN: Type inference failed for: r0v51, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v70 */
    /* JADX WARN: Type inference failed for: r0v97, types: [android.graphics.drawable.Drawable[]] */
    /* JADX WARN: Type inference failed for: r12v70, types: [android.text.SpannableStringBuilder] */
    /* JADX WARN: Type inference failed for: r21v0 */
    /* JADX WARN: Type inference failed for: r21v11 */
    /* JADX WARN: Type inference failed for: r21v12 */
    /* JADX WARN: Type inference failed for: r21v13 */
    /* JADX WARN: Type inference failed for: r21v17 */
    /* JADX WARN: Type inference failed for: r21v2 */
    /* JADX WARN: Type inference failed for: r21v3 */
    /* JADX WARN: Type inference failed for: r21v6 */
    /* JADX WARN: Type inference failed for: r31v10 */
    /* JADX WARN: Type inference failed for: r31v18 */
    /* JADX WARN: Type inference failed for: r31v19 */
    /* JADX WARN: Type inference failed for: r31v20 */
    /* JADX WARN: Type inference failed for: r31v21 */
    /* JADX WARN: Type inference failed for: r31v23 */
    /* JADX WARN: Type inference failed for: r31v24 */
    /* JADX WARN: Type inference failed for: r31v25 */
    /* JADX WARN: Type inference failed for: r31v26 */
    /* JADX WARN: Type inference failed for: r3v100, types: [android.graphics.drawable.Drawable[]] */
    /* JADX WARN: Type inference failed for: r3v92, types: [android.graphics.drawable.Drawable[]] */
    /* JADX WARN: Type inference failed for: r4v78, types: [android.text.SpannableStringBuilder] */
    /* JADX WARN: Type inference failed for: r52v0, types: [android.view.View, org.telegram.ui.Cells.DialogCell] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void buildLayout() {
        /*
            Method dump skipped, instructions count: 10315
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.DialogCell.buildLayout():void");
    }

    private CharSequence addSenderAvatar(CharSequence charSequence, MessageObject messageObject) {
        if (ExteraConfig.senderMiniAvatars && messageObject != null && !TextUtils.isEmpty(charSequence)) {
            long fromChatId = messageObject.getFromChatId();
            if (fromChatId != UserConfig.getInstance(this.currentAccount).getClientUserId()) {
                this.messageAvatarSpan.setDialogId(fromChatId);
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("A ");
                spannableStringBuilder.setSpan(this.messageAvatarSpan, 0, 1, 33);
                int length = spannableStringBuilder.length();
                spannableStringBuilder.append((CharSequence) " ");
                spannableStringBuilder.setSpan(new FixedWidthSpan(AndroidUtilities.m1146dp(1.0f)), length, spannableStringBuilder.length(), 33);
                spannableStringBuilder.append(charSequence);
                return spannableStringBuilder;
            }
        }
        return charSequence;
    }

    private SpannableStringBuilder formatInternal(int i, CharSequence charSequence, CharSequence charSequence2) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (i == 1) {
            spannableStringBuilder.append(charSequence2).append((CharSequence) ": \u2068").append(charSequence).append((CharSequence) "\u2069");
            return spannableStringBuilder;
        }
        if (i == 2) {
            spannableStringBuilder.append((CharSequence) "\u2068").append(charSequence).append((CharSequence) "\u2069");
            return spannableStringBuilder;
        }
        if (i == 3) {
            spannableStringBuilder.append(charSequence2).append((CharSequence) ": ").append(charSequence);
            return spannableStringBuilder;
        }
        if (i != 4) {
            return spannableStringBuilder;
        }
        spannableStringBuilder.append(charSequence);
        return spannableStringBuilder;
    }

    private void updateThumbsPosition() {
        if (this.thumbsCount > 0) {
            StaticLayout staticLayout = isForumCell() ? this.buttonLayout : this.messageLayout;
            int i = isForumCell() ? this.buttonLeft : this.messageLeft;
            if (staticLayout == null) {
                return;
            }
            try {
                CharSequence text = staticLayout.getText();
                if (text instanceof Spanned) {
                    ThumbnailSpan[] thumbnailSpanArr = (ThumbnailSpan[]) ((Spanned) text).getSpans(0, text.length(), ThumbnailSpan.class);
                    if (thumbnailSpanArr == null || thumbnailSpanArr.length <= 0) {
                        for (int i2 = 0; i2 < 3; i2++) {
                            this.thumbImageSeen[i2] = false;
                        }
                        return;
                    }
                    int spanStart = ((Spanned) text).getSpanStart(thumbnailSpanArr[0]);
                    if (spanStart < 0) {
                        spanStart = 0;
                    }
                    int iCeil = (int) Math.ceil(Math.min(staticLayout.getPrimaryHorizontal(spanStart), staticLayout.getPrimaryHorizontal(spanStart + 1)));
                    if (iCeil != 0 && !this.drawForwardIcon && !this.drawGiftIcon) {
                        iCeil += AndroidUtilities.m1146dp(3.0f);
                    }
                    for (int i3 = 0; i3 < this.thumbsCount; i3++) {
                        this.thumbImage[i3].setImageX(i + iCeil + AndroidUtilities.m1146dp((this.thumbSize + 2) * i3));
                        this.thumbImageSeen[i3] = true;
                    }
                }
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
    }

    private CharSequence applyThumbs(CharSequence charSequence) {
        if (this.thumbsCount <= 0) {
            return charSequence;
        }
        SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(charSequence);
        spannableStringBuilderValueOf.insert(0, (CharSequence) " ");
        spannableStringBuilderValueOf.setSpan(new ThumbnailSpan(AndroidUtilities.m1146dp(((this.thumbSize + 2) * this.thumbsCount) + 3)), 0, 1, 33);
        return spannableStringBuilderValueOf;
    }

    private CharSequence formatTopicsNames() {
        if (this.forumFormattedNames == null) {
            this.forumFormattedNames = new ForumFormattedNames(this);
        }
        this.forumFormattedNames.formatTopicsNames(this.currentAccount, this.message, this.chat);
        ForumFormattedNames forumFormattedNames = this.forumFormattedNames;
        this.topMessageTopicStartIndex = forumFormattedNames.topMessageTopicStartIndex;
        this.topMessageTopicEndIndex = forumFormattedNames.topMessageTopicEndIndex;
        this.lastTopicMessageUnread = forumFormattedNames.lastTopicMessageUnread;
        return forumFormattedNames.formattedNames;
    }

    public boolean isForumCell() {
        TLRPC.Chat chat;
        if (isDialogFolder() || (chat = this.chat) == null) {
            return false;
        }
        return (chat.forum || (ChatObject.isMonoForum(chat) && ChatObject.canManageMonoForum(this.currentAccount, this.chat))) && !this.isTopic;
    }

    private void drawCheckStatus(Canvas canvas, boolean z, boolean z2, boolean z3, boolean z4, float f) {
        if (f != 0.0f || z4) {
            float f2 = (f * 0.5f) + 0.5f;
            if (z) {
                BaseCell.setDrawableBounds(Theme.dialogs_clockDrawable, this.clockDrawLeft, this.checkDrawTop);
                if (f != 1.0f) {
                    canvas.save();
                    canvas.scale(f2, f2, Theme.dialogs_clockDrawable.getBounds().centerX(), Theme.dialogs_halfCheckDrawable.getBounds().centerY());
                    Theme.dialogs_clockDrawable.setAlpha((int) (f * 255.0f));
                }
                Theme.dialogs_clockDrawable.draw(canvas);
                if (f != 1.0f) {
                    canvas.restore();
                    Theme.dialogs_clockDrawable.setAlpha(255);
                }
                invalidate();
                return;
            }
            if (z3) {
                if (z2) {
                    BaseCell.setDrawableBounds(Theme.dialogs_halfCheckDrawable, this.halfCheckDrawLeft, this.checkDrawTop);
                    if (z4) {
                        canvas.save();
                        canvas.scale(f2, f2, Theme.dialogs_halfCheckDrawable.getBounds().centerX(), Theme.dialogs_halfCheckDrawable.getBounds().centerY());
                        Theme.dialogs_halfCheckDrawable.setAlpha((int) (f * 255.0f));
                    }
                    if (!z4 && f != 0.0f) {
                        canvas.save();
                        canvas.scale(f2, f2, Theme.dialogs_halfCheckDrawable.getBounds().centerX(), Theme.dialogs_halfCheckDrawable.getBounds().centerY());
                        int i = (int) (255.0f * f);
                        Theme.dialogs_halfCheckDrawable.setAlpha(i);
                        Theme.dialogs_checkReadDrawable.setAlpha(i);
                    }
                    Theme.dialogs_halfCheckDrawable.draw(canvas);
                    if (z4) {
                        canvas.restore();
                        canvas.save();
                        canvas.translate(AndroidUtilities.m1146dp(4.0f) * (1.0f - f), 0.0f);
                    }
                    BaseCell.setDrawableBounds(Theme.dialogs_checkReadDrawable, this.checkDrawLeft, this.checkDrawTop);
                    Theme.dialogs_checkReadDrawable.draw(canvas);
                    if (z4) {
                        canvas.restore();
                        Theme.dialogs_halfCheckDrawable.setAlpha(255);
                    }
                    if (z4 || f == 0.0f) {
                        return;
                    }
                    canvas.restore();
                    Theme.dialogs_halfCheckDrawable.setAlpha(255);
                    Theme.dialogs_checkReadDrawable.setAlpha(255);
                    return;
                }
                BaseCell.setDrawableBounds(Theme.dialogs_checkDrawable, this.checkDrawLeft1, this.checkDrawTop);
                if (f != 1.0f) {
                    canvas.save();
                    canvas.scale(f2, f2, Theme.dialogs_checkDrawable.getBounds().centerX(), Theme.dialogs_halfCheckDrawable.getBounds().centerY());
                    Theme.dialogs_checkDrawable.setAlpha((int) (f * 255.0f));
                }
                Theme.dialogs_checkDrawable.draw(canvas);
                if (f != 1.0f) {
                    canvas.restore();
                    Theme.dialogs_checkDrawable.setAlpha(255);
                }
            }
        }
    }

    public boolean isPointInsideAvatar(float f, float f2) {
        return !LocaleController.isRTL ? f >= 0.0f && f < ((float) AndroidUtilities.m1146dp(60.0f)) : f >= ((float) (getMeasuredWidth() - AndroidUtilities.m1146dp(60.0f))) && f < ((float) getMeasuredWidth());
    }

    public void setDialogSelected(boolean z) {
        if (this.isSelected != z) {
            invalidate();
        }
        this.isSelected = z;
    }

    public void animateArchiveAvatar() {
        if (this.avatarDrawable.getAvatarType() != 2) {
            return;
        }
        this.animatingArchiveAvatar = true;
        this.animatingArchiveAvatarProgress = 0.0f;
        Theme.dialogs_archiveAvatarDrawable.setProgress(0.0f);
        Theme.dialogs_archiveAvatarDrawable.start();
        invalidate();
    }

    public void setChecked(boolean z, boolean z2) {
        CheckBox2 checkBox2 = this.checkBox;
        if (checkBox2 != null || z) {
            if (checkBox2 == null) {
                CheckBox2 checkBox22 = new CheckBox2(getContext(), 21, this.resourcesProvider) { // from class: org.telegram.ui.Cells.DialogCell.3
                    @Override // android.view.View
                    public void invalidate() {
                        super.invalidate();
                        DialogCell.this.invalidate();
                    }
                };
                this.checkBox = checkBox22;
                checkBox22.setColor(-1, Theme.key_windowBackgroundWhite, Theme.key_checkboxCheck);
                this.checkBox.setDrawUnchecked(false);
                this.checkBox.setDrawBackgroundAsArc(3);
                addView(this.checkBox);
            }
            this.checkBox.setChecked(z, z2);
            checkTtl();
        }
    }

    private MessageObject findFolderTopMessage() {
        ArrayList dialogsArray;
        DialogsActivity dialogsActivity = this.parentFragment;
        if (dialogsActivity == null || (dialogsArray = dialogsActivity.getDialogsArray(this.currentAccount, this.dialogsType, this.currentDialogFolderId, false)) == null || dialogsArray.isEmpty()) {
            return null;
        }
        int size = dialogsArray.size();
        MessageObject messageObject = null;
        for (int i = 0; i < size; i++) {
            TLRPC.Dialog dialog = (TLRPC.Dialog) dialogsArray.get(i);
            LongSparseArray longSparseArray = MessagesController.getInstance(this.currentAccount).dialogMessage;
            if (longSparseArray != null) {
                ArrayList arrayList = (ArrayList) longSparseArray.get(dialog.f1577id);
                MessageObject messageObject2 = (arrayList == null || arrayList.isEmpty()) ? null : (MessageObject) arrayList.get(0);
                if (messageObject2 != null && (messageObject == null || messageObject2.messageOwner.date > messageObject.messageOwner.date)) {
                    messageObject = messageObject2;
                }
                if (dialog.pinnedNum == 0 && messageObject != null) {
                    return messageObject;
                }
            }
        }
        return messageObject;
    }

    public boolean isFolderCell() {
        return this.currentDialogFolderId != 0;
    }

    public boolean update(int i) {
        return update(i, true);
    }

    static {
        for (int i = 0; i < 16; i++) {
            TLRPC.TL_message tL_message = new TLRPC.TL_message();
            tL_message.f1597id = ConnectionsManager.DEFAULT_DATACENTER_ID;
            filteredDummyMessages[i] = new MessageObject(i, tL_message, false, false);
        }
    }

    private void filterCurrentMessage() {
        MessageObject.GroupedMessages groupedMessages;
        if (AyuConfig.filtersEnabled) {
            ArrayList arrayList = this.groupMessages;
            if (arrayList == null || arrayList.size() <= 1) {
                groupedMessages = null;
            } else {
                groupedMessages = new MessageObject.GroupedMessages();
                groupedMessages.messages = this.groupMessages;
                groupedMessages.groupId = this.message.getGroupId();
            }
            MessageObject captionMessage = getCaptionMessage();
            if (AyuFilterController.getInstance(this.currentAccount).isFiltered(this.chat, this.message, groupedMessages) || !(captionMessage == null || captionMessage == this.message || !AyuFilterController.getInstance(this.currentAccount).isFiltered(this.chat, captionMessage, groupedMessages))) {
                this.message = filteredDummyMessages[this.currentAccount];
                this.groupMessages = null;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:132:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0542 A[PHI: r10
      0x0542: PHI (r10v44 boolean) = (r10v43 boolean), (r10v46 boolean), (r10v46 boolean), (r10v46 boolean) binds: [B:246:0x0486, B:253:0x049f, B:279:0x0522, B:287:0x0534] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:297:0x0557  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x055c  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x0560  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0570  */
    /* JADX WARN: Removed duplicated region for block: B:308:0x058d  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x05e7  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x068e  */
    /* JADX WARN: Removed duplicated region for block: B:351:0x06b4  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x07d7  */
    /* JADX WARN: Removed duplicated region for block: B:440:0x0935  */
    /* JADX WARN: Removed duplicated region for block: B:441:0x0937  */
    /* JADX WARN: Removed duplicated region for block: B:443:0x093a  */
    /* JADX WARN: Removed duplicated region for block: B:453:0x098e  */
    /* JADX WARN: Removed duplicated region for block: B:461:0x099f  */
    /* JADX WARN: Removed duplicated region for block: B:482:0x09df  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x09ef  */
    /* JADX WARN: Removed duplicated region for block: B:492:0x0a0c  */
    /* JADX WARN: Removed duplicated region for block: B:493:0x0a0e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean update(int r45, boolean r46) {
        /*
            Method dump skipped, instructions count: 2698
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.DialogCell.update(int, boolean):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$update$0(ValueAnimator valueAnimator) {
        this.countChangeProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$update$1(ValueAnimator valueAnimator) {
        this.reactionsMentionsChangeProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getTopicId() {
        TLRPC.TL_forumTopic tL_forumTopic = this.forumTopic;
        if (tL_forumTopic == null) {
            return 0;
        }
        return tL_forumTopic.f1631id;
    }

    @Override // android.view.View
    public float getTranslationX() {
        return this.translationX;
    }

    @Override // android.view.View
    public void setTranslationX(float f) {
        if (f == this.translationX) {
            return;
        }
        this.translationX = f;
        RLottieDrawable rLottieDrawable = this.translationDrawable;
        if (rLottieDrawable != null && f == 0.0f) {
            rLottieDrawable.setProgress(0.0f);
            this.translationAnimationStarted = false;
            this.archiveHidden = SharedConfig.archiveHidden;
            this.currentRevealProgress = 0.0f;
            this.isSliding = false;
        }
        float f2 = this.translationX;
        if (f2 != 0.0f) {
            this.isSliding = true;
        } else {
            this.currentRevealBounceProgress = 0.0f;
            this.currentRevealProgress = 0.0f;
            this.drawRevealBackground = false;
        }
        if (this.isSliding && !this.swipeCanceled) {
            boolean z = this.drawRevealBackground;
            boolean z2 = Math.abs(f2) >= ((float) getMeasuredWidth()) * 0.45f;
            this.drawRevealBackground = z2;
            if (z != z2 && this.archiveHidden == SharedConfig.archiveHidden) {
                try {
                    performHapticFeedback(3, 2);
                } catch (Exception unused) {
                }
            }
        }
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:1007:0x1a2f  */
    /* JADX WARN: Removed duplicated region for block: B:1012:0x1a42  */
    /* JADX WARN: Removed duplicated region for block: B:1015:0x1a47  */
    /* JADX WARN: Removed duplicated region for block: B:1027:0x1a6f  */
    /* JADX WARN: Removed duplicated region for block: B:1037:0x1a92  */
    /* JADX WARN: Removed duplicated region for block: B:1038:0x1a94  */
    /* JADX WARN: Removed duplicated region for block: B:1041:0x1a9b  */
    /* JADX WARN: Removed duplicated region for block: B:1066:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x05a9  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x05b8  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x05c1  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x0bb2  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x0bbd  */
    /* JADX WARN: Removed duplicated region for block: B:363:0x0bcb  */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0bcf  */
    /* JADX WARN: Removed duplicated region for block: B:371:0x0be1  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0be4  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x0bf6  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x0c2e  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x0c40  */
    /* JADX WARN: Removed duplicated region for block: B:423:0x0ce6  */
    /* JADX WARN: Removed duplicated region for block: B:485:0x0eec  */
    /* JADX WARN: Removed duplicated region for block: B:488:0x0ef2  */
    /* JADX WARN: Removed duplicated region for block: B:549:0x0f9d  */
    /* JADX WARN: Removed duplicated region for block: B:552:0x0fa8  */
    /* JADX WARN: Removed duplicated region for block: B:573:0x1010  */
    /* JADX WARN: Removed duplicated region for block: B:577:0x1017  */
    /* JADX WARN: Removed duplicated region for block: B:598:0x1051  */
    /* JADX WARN: Removed duplicated region for block: B:640:0x1122  */
    /* JADX WARN: Removed duplicated region for block: B:642:0x1126  */
    /* JADX WARN: Removed duplicated region for block: B:658:0x1177  */
    /* JADX WARN: Removed duplicated region for block: B:727:0x1292  */
    /* JADX WARN: Removed duplicated region for block: B:729:0x129a  */
    /* JADX WARN: Removed duplicated region for block: B:732:0x12bc  */
    /* JADX WARN: Removed duplicated region for block: B:734:0x130b  */
    /* JADX WARN: Removed duplicated region for block: B:799:0x152e  */
    /* JADX WARN: Removed duplicated region for block: B:836:0x16d6  */
    /* JADX WARN: Removed duplicated region for block: B:845:0x170f  */
    /* JADX WARN: Removed duplicated region for block: B:848:0x1720  */
    /* JADX WARN: Removed duplicated region for block: B:851:0x1743  */
    /* JADX WARN: Removed duplicated region for block: B:863:0x175e  */
    /* JADX WARN: Removed duplicated region for block: B:867:0x17a5  */
    /* JADX WARN: Removed duplicated region for block: B:883:0x17d4  */
    /* JADX WARN: Removed duplicated region for block: B:886:0x17df  */
    /* JADX WARN: Removed duplicated region for block: B:891:0x17ef  */
    /* JADX WARN: Removed duplicated region for block: B:895:0x17f7  */
    /* JADX WARN: Removed duplicated region for block: B:897:0x17fb  */
    /* JADX WARN: Removed duplicated region for block: B:911:0x1867  */
    /* JADX WARN: Removed duplicated region for block: B:914:0x1870  */
    /* JADX WARN: Removed duplicated region for block: B:917:0x1877  */
    /* JADX WARN: Removed duplicated region for block: B:932:0x18be  */
    /* JADX WARN: Removed duplicated region for block: B:959:0x1942  */
    /* JADX WARN: Removed duplicated region for block: B:962:0x194c  */
    /* JADX WARN: Removed duplicated region for block: B:968:0x199f  */
    /* JADX WARN: Removed duplicated region for block: B:972:0x19aa  */
    /* JADX WARN: Removed duplicated region for block: B:973:0x19ac  */
    /* JADX WARN: Removed duplicated region for block: B:979:0x19c0  */
    /* JADX WARN: Removed duplicated region for block: B:988:0x19d8  */
    /* JADX WARN: Removed duplicated region for block: B:997:0x1a01  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onDraw(android.graphics.Canvas r46) {
        /*
            Method dump skipped, instructions count: 6832
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.DialogCell.onDraw(android.graphics.Canvas):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDraw$2() {
        DialogCellDelegate dialogCellDelegate = this.delegate;
        if (dialogCellDelegate != null) {
            dialogCellDelegate.onButtonClicked(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDraw$3() {
        DialogCellDelegate dialogCellDelegate = this.delegate;
        if (dialogCellDelegate != null) {
            dialogCellDelegate.onButtonLongPress(this);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:198:0x060b  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0693  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x069c  */
    /* JADX WARN: Removed duplicated region for block: B:214:0x06bb  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x06cc  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x06e2  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x06e9  */
    /* JADX WARN: Removed duplicated region for block: B:233:0x06f5  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x022c  */
    @Override // org.telegram.ui.Stories.StoriesListPlaceProvider.AvatarOverlaysView
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean drawAvatarOverlays(android.graphics.Canvas r21) {
        /*
            Method dump skipped, instructions count: 1803
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.DialogCell.drawAvatarOverlays(android.graphics.Canvas):boolean");
    }

    private void drawCounter(Canvas canvas, boolean z, int i, int i2, int i3, float f, boolean z2) {
        Paint paint;
        boolean z3;
        RectF rectF;
        float interpolation;
        RectF rectF2;
        boolean z4 = isForumCell() || isFolderCell();
        if (!(this.drawCount && this.drawCount2) && this.countChangeProgress == 1.0f) {
            return;
        }
        float f2 = (this.unreadCount != 0 || this.markUnread) ? this.countChangeProgress : 1.0f - this.countChangeProgress;
        int i4 = 255;
        if (z2) {
            if (this.counterPaintOutline == null) {
                Paint paint2 = new Paint();
                this.counterPaintOutline = paint2;
                paint2.setStyle(Paint.Style.STROKE);
                this.counterPaintOutline.setStrokeWidth(AndroidUtilities.m1146dp(2.0f));
                this.counterPaintOutline.setStrokeJoin(Paint.Join.ROUND);
                this.counterPaintOutline.setStrokeCap(Paint.Cap.ROUND);
            }
            this.counterPaintOutline.setColor(ColorUtils.blendARGB(Theme.getColor(Theme.key_windowBackgroundWhite), ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_chats_pinnedOverlay), 255), Color.alpha(r13) / 255.0f));
        }
        if (this.isTopic && this.forumTopic.read_inbox_max_id == 0) {
            if (this.topicCounterPaint == null) {
                this.topicCounterPaint = new Paint();
            }
            paint = this.topicCounterPaint;
            int color = Theme.getColor(z ? Theme.key_topics_unreadCounterMuted : Theme.key_topics_unreadCounter, this.resourcesProvider);
            paint.setColor(color);
            Theme.dialogs_countTextPaint.setColor(color);
            i4 = z ? 30 : 40;
            z3 = true;
        } else {
            paint = (z || this.currentDialogFolderId != 0) ? Theme.dialogs_countGrayPaint : Theme.dialogs_countPaint;
            z3 = false;
        }
        StaticLayout staticLayout = this.countOldLayout;
        if (staticLayout == null || this.unreadCount == 0) {
            if (this.unreadCount != 0) {
                staticLayout = this.countLayout;
            }
            paint.setAlpha((int) ((1.0f - this.reorderIconProgress) * i4));
            Theme.dialogs_countTextPaint.setAlpha((int) ((1.0f - this.reorderIconProgress) * 255.0f));
            this.rect.set(i2 - AndroidUtilities.m1146dp(5.5f), i, r9 + this.countWidth + AndroidUtilities.m1146dp(11.0f), AndroidUtilities.m1146dp(23.0f) + i);
            int iSave = canvas.save();
            if (f != 1.0f) {
                canvas.scale(f, f, this.rect.centerX(), this.rect.centerY());
            }
            if (f2 != 1.0f) {
                if (getIsPinned()) {
                    Theme.dialogs_pinnedDrawable.setAlpha((int) ((1.0f - this.reorderIconProgress) * 255.0f));
                    BaseCell.setDrawableBounds(Theme.dialogs_pinnedDrawable, this.pinLeft, this.pinTop);
                    canvas.save();
                    float f3 = 1.0f - f2;
                    canvas.scale(f3, f3, Theme.dialogs_pinnedDrawable.getBounds().centerX(), Theme.dialogs_pinnedDrawable.getBounds().centerY());
                    Theme.dialogs_pinnedDrawable.draw(canvas);
                    canvas.restore();
                }
                canvas.scale(f2, f2, this.rect.centerX(), this.rect.centerY());
            }
            if (z4) {
                if (this.counterPath == null || (rectF = this.counterPathRect) == null || !rectF.equals(this.rect)) {
                    RectF rectF3 = this.counterPathRect;
                    if (rectF3 == null) {
                        this.counterPathRect = new RectF(this.rect);
                    } else {
                        rectF3.set(this.rect);
                    }
                    if (this.counterPath == null) {
                        this.counterPath = new Path();
                    }
                    BubbleCounterPath.addBubbleRect(this.counterPath, this.counterPathRect, AndroidUtilities.m1146dp(11.5f));
                }
                canvas.drawPath(this.counterPath, paint);
                if (z2) {
                    canvas.drawPath(this.counterPath, this.counterPaintOutline);
                }
            } else {
                canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(11.5f), AndroidUtilities.m1146dp(11.5f), paint);
                if (z2) {
                    canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(11.5f), AndroidUtilities.m1146dp(11.5f), this.counterPaintOutline);
                }
            }
            if (staticLayout != null) {
                canvas.save();
                canvas.translate(i2, i + AndroidUtilities.m1146dp(4.0f));
                staticLayout.draw(canvas);
                canvas.restore();
            }
            canvas.restoreToCount(iSave);
        } else {
            paint.setAlpha((int) ((1.0f - this.reorderIconProgress) * i4));
            Theme.dialogs_countTextPaint.setAlpha((int) ((1.0f - this.reorderIconProgress) * 255.0f));
            float f4 = f2 * 2.0f;
            float f5 = f4 > 1.0f ? 1.0f : f4;
            float f6 = 1.0f - f5;
            float f7 = (i2 * f5) + (i3 * f6);
            float fM1146dp = f7 - AndroidUtilities.m1146dp(5.5f);
            float f8 = i;
            this.rect.set(fM1146dp, f8, (this.countWidth * f5) + fM1146dp + (this.countWidthOld * f6) + AndroidUtilities.m1146dp(11.0f), AndroidUtilities.m1146dp(23.0f) + i);
            if (f2 <= 0.5f) {
                interpolation = CubicBezierInterpolator.EASE_OUT.getInterpolation(f4);
            } else {
                interpolation = CubicBezierInterpolator.EASE_IN.getInterpolation(1.0f - ((f2 - 0.5f) * 2.0f));
            }
            float f9 = (interpolation * 0.1f) + 1.0f;
            canvas.save();
            float f10 = f9 * f;
            canvas.scale(f10, f10, this.rect.centerX(), this.rect.centerY());
            if (z4) {
                if (this.counterPath == null || (rectF2 = this.counterPathRect) == null || !rectF2.equals(this.rect)) {
                    RectF rectF4 = this.counterPathRect;
                    if (rectF4 == null) {
                        this.counterPathRect = new RectF(this.rect);
                    } else {
                        rectF4.set(this.rect);
                    }
                    if (this.counterPath == null) {
                        this.counterPath = new Path();
                    }
                    BubbleCounterPath.addBubbleRect(this.counterPath, this.counterPathRect, AndroidUtilities.m1146dp(11.5f));
                }
                canvas.drawPath(this.counterPath, paint);
                if (z2) {
                    canvas.drawPath(this.counterPath, this.counterPaintOutline);
                }
            } else {
                canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(11.5f), AndroidUtilities.m1146dp(11.5f), paint);
                if (z2) {
                    canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(11.5f), AndroidUtilities.m1146dp(11.5f), this.counterPaintOutline);
                }
            }
            if (this.countAnimationStableLayout != null) {
                canvas.save();
                canvas.translate(f7, i + AndroidUtilities.m1146dp(4.0f));
                this.countAnimationStableLayout.draw(canvas);
                canvas.restore();
            }
            int alpha = Theme.dialogs_countTextPaint.getAlpha();
            float f11 = alpha;
            Theme.dialogs_countTextPaint.setAlpha((int) (f11 * f5));
            if (this.countAnimationInLayout != null) {
                canvas.save();
                canvas.translate(f7, ((this.countAnimationIncrement ? AndroidUtilities.m1146dp(13.0f) : -AndroidUtilities.m1146dp(13.0f)) * f6) + f8 + AndroidUtilities.m1146dp(4.0f));
                this.countAnimationInLayout.draw(canvas);
                canvas.restore();
            } else if (this.countLayout != null) {
                canvas.save();
                canvas.translate(f7, ((this.countAnimationIncrement ? AndroidUtilities.m1146dp(13.0f) : -AndroidUtilities.m1146dp(13.0f)) * f6) + f8 + AndroidUtilities.m1146dp(4.0f));
                this.countLayout.draw(canvas);
                canvas.restore();
            }
            if (this.countOldLayout != null) {
                Theme.dialogs_countTextPaint.setAlpha((int) (f11 * f6));
                canvas.save();
                canvas.translate(f7, ((this.countAnimationIncrement ? -AndroidUtilities.m1146dp(13.0f) : AndroidUtilities.m1146dp(13.0f)) * f5) + f8 + AndroidUtilities.m1146dp(4.0f));
                this.countOldLayout.draw(canvas);
                canvas.restore();
            }
            Theme.dialogs_countTextPaint.setAlpha(alpha);
            canvas.restore();
        }
        if (z3) {
            Theme.dialogs_countTextPaint.setColor(Theme.getColor(Theme.key_chats_unreadCounterText));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createStatusDrawableAnimator(int i, int i2) {
        this.statusDrawableProgress = 0.0f;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.statusDrawableAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(220L);
        this.statusDrawableAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
        this.animateFromStatusDrawableParams = i;
        this.animateToStatusDrawableParams = i2;
        this.statusDrawableAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.DialogCell$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createStatusDrawableAnimator$4(valueAnimator);
            }
        });
        this.statusDrawableAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.DialogCell.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                int i3 = (DialogCell.this.drawClock ? 1 : 0) + (DialogCell.this.drawCheck1 ? 2 : 0) + (DialogCell.this.drawCheck2 ? 4 : 0);
                if (DialogCell.this.animateToStatusDrawableParams != i3) {
                    DialogCell dialogCell = DialogCell.this;
                    dialogCell.createStatusDrawableAnimator(dialogCell.animateToStatusDrawableParams, i3);
                } else {
                    DialogCell.this.statusDrawableAnimationInProgress = false;
                    DialogCell dialogCell2 = DialogCell.this;
                    dialogCell2.lastStatusDrawableParams = dialogCell2.animateToStatusDrawableParams;
                }
                DialogCell.this.invalidate();
            }
        });
        this.statusDrawableAnimationInProgress = true;
        this.statusDrawableAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createStatusDrawableAnimator$4(ValueAnimator valueAnimator) {
        this.statusDrawableProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    public void startOutAnimation() {
        PullForegroundDrawable pullForegroundDrawable = this.archivedChatsDrawable;
        if (pullForegroundDrawable != null) {
            if (this.isTopic) {
                pullForegroundDrawable.outCy = AndroidUtilities.m1146dp(24.0f);
                this.archivedChatsDrawable.outCx = AndroidUtilities.m1146dp(24.0f);
                PullForegroundDrawable pullForegroundDrawable2 = this.archivedChatsDrawable;
                pullForegroundDrawable2.outRadius = 0.0f;
                pullForegroundDrawable2.outImageSize = 0.0f;
            } else {
                pullForegroundDrawable.outCy = this.storyParams.originalAvatarRect.centerY();
                this.archivedChatsDrawable.outCx = this.storyParams.originalAvatarRect.centerX();
                this.archivedChatsDrawable.outRadius = this.storyParams.originalAvatarRect.width() / 2.0f;
                if (MessagesController.getInstance(this.currentAccount).getStoriesController().hasHiddenStories()) {
                    this.archivedChatsDrawable.outRadius -= AndroidUtilities.dpf2(3.5f);
                }
                this.archivedChatsDrawable.outImageSize = this.avatarImage.getBitmapWidth();
            }
            this.archivedChatsDrawable.startOutAnimation();
        }
    }

    public void onReorderStateChanged(boolean z, boolean z2) {
        if ((!getIsPinned() && z) || this.drawReorder == z) {
            if (getIsPinned()) {
                return;
            }
            this.drawReorder = false;
        } else {
            this.drawReorder = z;
            if (z2) {
                this.reorderIconProgress = z ? 0.0f : 1.0f;
            } else {
                this.reorderIconProgress = z ? 1.0f : 0.0f;
            }
            invalidate();
        }
    }

    public void setSliding(boolean z) {
        this.isSliding = z;
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (drawable == this.translationDrawable || drawable == Theme.dialogs_archiveAvatarDrawable) {
            invalidate(drawable.getBounds());
        } else {
            super.invalidateDrawable(drawable);
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        DialogsActivity dialogsActivity;
        if (i == C2369R.id.acc_action_chat_preview && (dialogsActivity = this.parentFragment) != null) {
            dialogsActivity.showChatPreview(this);
            return true;
        }
        return super.performAccessibilityAction(i, bundle);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        PullForegroundDrawable pullForegroundDrawable;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (isFolderCell() && (pullForegroundDrawable = this.archivedChatsDrawable) != null && SharedConfig.archiveHidden && pullForegroundDrawable.pullProgress == 0.0f) {
            accessibilityNodeInfo.setVisibleToUser(false);
        } else {
            accessibilityNodeInfo.addAction(16);
            accessibilityNodeInfo.addAction(32);
            if (!isFolderCell() && this.parentFragment != null) {
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(C2369R.id.acc_action_chat_preview, LocaleController.getString(C2369R.string.AccActionChatPreview)));
            }
        }
        CheckBox2 checkBox2 = this.checkBox;
        if (checkBox2 == null || !checkBox2.isChecked()) {
            return;
        }
        accessibilityNodeInfo.setClassName("android.widget.CheckBox");
        accessibilityNodeInfo.setCheckable(true);
        accessibilityNodeInfo.setChecked(true);
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        MessageObject captionMessage;
        TLRPC.User user;
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        StringBuilder sb = new StringBuilder();
        if (this.currentDialogFolderId == 1) {
            sb.append(LocaleController.getString(C2369R.string.ArchivedChats));
            sb.append(". ");
        } else {
            if (this.encryptedChat != null) {
                sb.append(LocaleController.getString(C2369R.string.AccDescrSecretChat));
                sb.append(". ");
            }
            if (this.isTopic && this.forumTopic != null) {
                sb.append(LocaleController.getString(C2369R.string.AccDescrTopic));
                sb.append(". ");
                sb.append(this.forumTopic.title);
                sb.append(". ");
            } else {
                TLRPC.User user2 = this.user;
                if (user2 != null) {
                    if (UserObject.isReplyUser(user2)) {
                        sb.append(LocaleController.getString(C2369R.string.RepliesTitle));
                    } else if (UserObject.isAnonymous(this.user)) {
                        sb.append(LocaleController.getString(C2369R.string.AnonymousForward));
                    } else {
                        if (this.user.bot) {
                            sb.append(LocaleController.getString(C2369R.string.Bot));
                            sb.append(". ");
                        }
                        TLRPC.User user3 = this.user;
                        if (user3.self) {
                            sb.append(LocaleController.getString(C2369R.string.SavedMessages));
                        } else {
                            sb.append(ContactsController.formatName(user3.first_name, user3.last_name));
                        }
                    }
                    sb.append(". ");
                } else {
                    TLRPC.Chat chat = this.chat;
                    if (chat != null) {
                        if (chat.broadcast) {
                            sb.append(LocaleController.getString(C2369R.string.AccDescrChannel));
                        } else {
                            sb.append(LocaleController.getString(C2369R.string.AccDescrGroup));
                        }
                        sb.append(". ");
                        sb.append(this.chat.title);
                        sb.append(". ");
                    }
                }
            }
        }
        if (this.drawVerified) {
            sb.append(LocaleController.getString(C2369R.string.AccDescrVerified));
            sb.append(". ");
        }
        if (this.dialogMuted) {
            sb.append(LocaleController.getString(C2369R.string.AccDescrNotificationsMuted));
            sb.append(". ");
        }
        if (isOnline()) {
            sb.append(LocaleController.getString(C2369R.string.AccDescrUserOnline));
            sb.append(". ");
        }
        int i = this.unreadCount;
        if (i > 0) {
            sb.append(LocaleController.formatPluralString("NewMessages", i, new Object[0]));
            sb.append(". ");
        }
        int i2 = this.mentionCount;
        if (i2 > 0) {
            sb.append(LocaleController.formatPluralString("AccDescrMentionCount", i2, new Object[0]));
            sb.append(". ");
        }
        if (this.reactionMentionCount > 0) {
            sb.append(LocaleController.getString(C2369R.string.AccDescrMentionReaction));
            sb.append(". ");
        }
        MessageObject messageObject = this.message;
        if (messageObject == null || this.currentDialogFolderId != 0) {
            accessibilityEvent.setContentDescription(sb);
            setContentDescription(sb);
            return;
        }
        int i3 = this.lastMessageDate;
        if (i3 == 0) {
            i3 = messageObject.messageOwner.date;
        }
        String dateAudio = LocaleController.formatDateAudio(i3, true);
        if (this.message.isOut()) {
            sb.append(LocaleController.formatString("AccDescrSentDate", C2369R.string.AccDescrSentDate, dateAudio));
        } else {
            sb.append(LocaleController.formatString("AccDescrReceivedDate", C2369R.string.AccDescrReceivedDate, dateAudio));
        }
        sb.append(". ");
        if (this.chat != null && !this.message.isOut() && this.message.isFromUser() && this.message.messageOwner.action == null && (user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.message.messageOwner.from_id.user_id))) != null) {
            sb.append(ContactsController.formatName(user.first_name, user.last_name));
            sb.append(". ");
        }
        if (this.encryptedChat == null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.message.messageText);
            if (!this.message.isMediaEmpty() && (captionMessage = getCaptionMessage()) != null && !TextUtils.isEmpty(captionMessage.caption)) {
                if (sb2.length() > 0) {
                    sb2.append(". ");
                }
                sb2.append(captionMessage.caption);
            }
            StaticLayout staticLayout = this.messageLayout;
            int length = staticLayout == null ? -1 : staticLayout.getText().length();
            if (length > 0) {
                int length2 = sb2.length();
                int iIndexOf = sb2.indexOf("\n", length);
                if (iIndexOf < length2 && iIndexOf >= 0) {
                    length2 = iIndexOf;
                }
                int iIndexOf2 = sb2.indexOf("\t", length);
                if (iIndexOf2 < length2 && iIndexOf2 >= 0) {
                    length2 = iIndexOf2;
                }
                int iIndexOf3 = sb2.indexOf(" ", length);
                if (iIndexOf3 < length2 && iIndexOf3 >= 0) {
                    length2 = iIndexOf3;
                }
                sb.append(sb2.substring(0, length2));
            } else {
                sb.append((CharSequence) sb2);
            }
        }
        accessibilityEvent.setContentDescription(sb);
        setContentDescription(sb);
    }

    private MessageObject getCaptionMessage() {
        CharSequence charSequence;
        if (this.groupMessages == null) {
            MessageObject messageObject = this.message;
            if (messageObject == null || messageObject.caption == null) {
                return null;
            }
            return messageObject;
        }
        MessageObject messageObject2 = null;
        int i = 0;
        for (int i2 = 0; i2 < this.groupMessages.size(); i2++) {
            MessageObject messageObject3 = (MessageObject) this.groupMessages.get(i2);
            if (messageObject3 != null && (charSequence = messageObject3.caption) != null) {
                if (!TextUtils.isEmpty(charSequence)) {
                    i++;
                }
                messageObject2 = messageObject3;
            }
        }
        if (i > 1) {
            return null;
        }
        return messageObject2;
    }

    public void updateMessageThumbs() {
        TLRPC.Message message;
        int i;
        MessageObject messageObject = this.message;
        if (messageObject == null) {
            return;
        }
        String restrictionReason = MessagesController.getInstance(messageObject.currentAccount).getRestrictionReason(this.message.messageOwner.restriction_reason);
        MessageObject messageObject2 = this.message;
        int i2 = 0;
        if (messageObject2 != null && (message = messageObject2.messageOwner) != null) {
            TLRPC.MessageMedia messageMedia = message.media;
            if (messageMedia instanceof TLRPC.TL_messageMediaPaidMedia) {
                this.thumbsCount = 0;
                this.hasVideoThumb = false;
                TLRPC.TL_messageMediaPaidMedia tL_messageMediaPaidMedia = (TLRPC.TL_messageMediaPaidMedia) messageMedia;
                int i3 = 0;
                while (i2 < tL_messageMediaPaidMedia.extended_media.size() && this.thumbsCount < 3) {
                    TLRPC.MessageExtendedMedia messageExtendedMedia = tL_messageMediaPaidMedia.extended_media.get(i2);
                    if (messageExtendedMedia instanceof TLRPC.TL_messageExtendedMediaPreview) {
                        i = i3 + 1;
                        setThumb(i3, ((TLRPC.TL_messageExtendedMediaPreview) messageExtendedMedia).thumb);
                    } else if (messageExtendedMedia instanceof TLRPC.TL_messageExtendedMedia) {
                        i = i3 + 1;
                        setThumb(i3, ((TLRPC.TL_messageExtendedMedia) messageExtendedMedia).media);
                    } else {
                        i2++;
                    }
                    i3 = i;
                    i2++;
                }
                return;
            }
        }
        ArrayList arrayList = this.groupMessages;
        if (arrayList != null && arrayList.size() > 1 && TextUtils.isEmpty(restrictionReason) && this.currentDialogFolderId == 0 && this.encryptedChat == null) {
            this.thumbsCount = 0;
            this.hasVideoThumb = false;
            Collections.sort(this.groupMessages, Comparator.CC.comparingInt(new ToIntFunction() { // from class: org.telegram.ui.Cells.DialogCell$$ExternalSyntheticLambda4
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj) {
                    return ((MessageObject) obj).getId();
                }
            }));
            while (i2 < Math.min(3, this.groupMessages.size())) {
                MessageObject messageObject3 = (MessageObject) this.groupMessages.get(i2);
                if (messageObject3 != null && !messageObject3.needDrawBluredPreview() && (messageObject3.isPhoto() || messageObject3.isNewGif() || messageObject3.isVideo() || messageObject3.isRoundVideo() || messageObject3.isStoryMedia())) {
                    String str = messageObject3.isWebpage() ? messageObject3.messageOwner.media.webpage.type : null;
                    if (!Common.ASSET_APP.equals(str) && !"profile".equals(str) && !"article".equals(str) && (str == null || !str.startsWith("telegram_"))) {
                        setThumb(i2, messageObject3);
                    }
                }
                i2++;
            }
            return;
        }
        MessageObject messageObject4 = this.message;
        if (messageObject4 == null || this.currentDialogFolderId != 0) {
            return;
        }
        this.thumbsCount = 0;
        this.hasVideoThumb = false;
        if (messageObject4.needDrawBluredPreview()) {
            return;
        }
        if (this.message.isPhoto() || this.message.isNewGif() || this.message.isVideo() || this.message.isRoundVideo() || this.message.isStoryMedia()) {
            String str2 = this.message.isWebpage() ? this.message.messageOwner.media.webpage.type : null;
            if (Common.ASSET_APP.equals(str2) || "profile".equals(str2) || "article".equals(str2)) {
                return;
            }
            if (str2 == null || !str2.startsWith("telegram_")) {
                setThumb(0, this.message);
            }
        }
    }

    private void setThumb(int i, MessageObject messageObject) {
        TLRPC.MessageMedia messageMedia;
        ArrayList<TLRPC.PhotoSize> arrayList = messageObject.photoThumbs;
        TLObject tLObject = messageObject.photoThumbsObject;
        if (messageObject.isStoryMedia()) {
            TL_stories.StoryItem storyItem = messageObject.messageOwner.media.storyItem;
            if (storyItem == null || (messageMedia = storyItem.media) == null) {
                return;
            }
            TLRPC.Document document = messageMedia.document;
            if (document != null) {
                arrayList = document.thumbs;
                tLObject = document;
            } else {
                TLRPC.Photo photo = messageMedia.photo;
                if (photo != null) {
                    arrayList = photo.sizes;
                    tLObject = photo;
                }
            }
        }
        TLRPC.PhotoSize strippedPhotoSize = FileLoader.getStrippedPhotoSize(arrayList);
        if (strippedPhotoSize == null) {
            strippedPhotoSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, 40);
        }
        TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, AndroidUtilities.getPhotoSize(), false, null, true);
        TLRPC.PhotoSize photoSize = strippedPhotoSize != closestPhotoSizeWithSize ? closestPhotoSizeWithSize : null;
        if (photoSize == null || !DownloadController.getInstance(this.currentAccount).canDownloadMedia(messageObject)) {
            photoSize = strippedPhotoSize;
        }
        if (strippedPhotoSize != null) {
            this.hasVideoThumb = this.hasVideoThumb || messageObject.isVideo() || messageObject.isRoundVideo();
            int i2 = this.thumbsCount;
            if (i2 < 3) {
                this.thumbsCount = i2 + 1;
                this.drawPlay[i] = (messageObject.isVideo() || messageObject.isRoundVideo()) && !messageObject.hasMediaSpoilers();
                this.drawSpoiler[i] = messageObject.hasMediaSpoilers();
                int i3 = (messageObject.type != 1 || photoSize == null) ? 0 : photoSize.size;
                String str = messageObject.hasMediaSpoilers() ? "5_5_b" : "20_20";
                this.thumbImage[i].setImage(ImageLocation.getForObject(photoSize, tLObject), str, ImageLocation.getForObject(strippedPhotoSize, tLObject), str, i3, null, messageObject, 0);
                this.thumbImage[i].setRoundRadius(AndroidUtilities.m1146dp(messageObject.isRoundVideo() ? 18.0f : 2.0f));
                this.needEmoji = false;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setThumb(int r19, org.telegram.tgnet.TLRPC.MessageMedia r20) {
        /*
            r18 = this;
            r0 = r18
            r1 = r20
            boolean r2 = r1 instanceof org.telegram.tgnet.TLRPC.TL_messageMediaPhoto
            r3 = 0
            r4 = 0
            if (r2 == 0) goto L10
            org.telegram.tgnet.TLRPC$Photo r1 = r1.photo
            java.util.ArrayList r2 = r1.sizes
        Le:
            r5 = 0
            goto L27
        L10:
            boolean r2 = r1 instanceof org.telegram.tgnet.TLRPC.TL_messageMediaDocument
            if (r2 == 0) goto L24
            org.telegram.tgnet.TLRPC$Document r2 = r1.document
            boolean r2 = org.telegram.messenger.MessageObject.isVideoDocument(r2)
            org.telegram.tgnet.TLRPC$Document r1 = r1.document
            java.util.ArrayList<org.telegram.tgnet.TLRPC$PhotoSize> r5 = r1.thumbs
            r17 = r5
            r5 = r2
            r2 = r17
            goto L27
        L24:
            r1 = r3
            r2 = r1
            goto Le
        L27:
            org.telegram.tgnet.TLRPC$TL_photoStrippedSize r6 = org.telegram.messenger.FileLoader.getStrippedPhotoSize(r2)
            if (r6 != 0) goto L33
            r6 = 40
            org.telegram.tgnet.TLRPC$PhotoSize r6 = org.telegram.messenger.FileLoader.getClosestPhotoSizeWithSize(r2, r6)
        L33:
            int r7 = org.telegram.messenger.AndroidUtilities.getPhotoSize()
            r8 = 1
            org.telegram.tgnet.TLRPC$PhotoSize r2 = org.telegram.messenger.FileLoader.getClosestPhotoSizeWithSize(r2, r7, r4, r3, r8)
            if (r6 != r2) goto L3f
            goto L40
        L3f:
            r3 = r2
        L40:
            if (r3 == 0) goto L51
            int r2 = r0.currentAccount
            org.telegram.messenger.DownloadController r2 = org.telegram.messenger.DownloadController.getInstance(r2)
            int r7 = r3.size
            long r9 = (long) r7
            boolean r2 = r2.canDownloadMedia(r8, r9)
            if (r2 != 0) goto L52
        L51:
            r3 = r6
        L52:
            if (r6 == 0) goto L9f
            boolean r2 = r0.hasVideoThumb
            if (r2 != 0) goto L5d
            if (r5 == 0) goto L5b
            goto L5d
        L5b:
            r2 = 0
            goto L5e
        L5d:
            r2 = 1
        L5e:
            r0.hasVideoThumb = r2
            int r2 = r0.thumbsCount
            r7 = 3
            if (r2 >= r7) goto L9f
            int r2 = r2 + r8
            r0.thumbsCount = r2
            boolean[] r2 = r0.drawPlay
            r2[r19] = r5
            boolean[] r2 = r0.drawSpoiler
            r2[r19] = r4
            if (r5 != 0) goto L77
            if (r3 == 0) goto L77
            int r2 = r3.size
            goto L78
        L77:
            r2 = 0
        L78:
            org.telegram.messenger.ImageReceiver[] r5 = r0.thumbImage
            r7 = r5[r19]
            org.telegram.messenger.ImageLocation r8 = org.telegram.messenger.ImageLocation.getForObject(r3, r1)
            org.telegram.messenger.ImageLocation r10 = org.telegram.messenger.ImageLocation.getForObject(r6, r1)
            long r12 = (long) r2
            org.telegram.messenger.MessageObject r15 = r0.message
            r16 = 0
            java.lang.String r9 = "20_20"
            r14 = 0
            r11 = r9
            r7.setImage(r8, r9, r10, r11, r12, r14, r15, r16)
            org.telegram.messenger.ImageReceiver[] r1 = r0.thumbImage
            r1 = r1[r19]
            r2 = 1073741824(0x40000000, float:2.0)
            int r2 = org.telegram.messenger.AndroidUtilities.m1146dp(r2)
            r1.setRoundRadius(r2)
            r0.needEmoji = r4
        L9f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.DialogCell.setThumb(int, org.telegram.tgnet.TLRPC$MessageMedia):void");
    }

    private void setThumb(int i, TLRPC.PhotoSize photoSize) {
        if (i < 3 && photoSize != null) {
            this.hasVideoThumb = false;
            int i2 = this.thumbsCount;
            if (i2 < 3) {
                this.thumbsCount = i2 + 1;
                this.drawPlay[i] = false;
                this.drawSpoiler[i] = true;
                this.thumbImage[i].setImage(ImageLocation.getForObject(photoSize, this.message.messageOwner), "2_2_b", null, null, 0, null, this.message, 0);
                this.thumbImage[i].setRoundRadius(AndroidUtilities.m1146dp(2.0f));
                this.needEmoji = false;
            }
        }
    }

    public String getMessageNameString() {
        TLRPC.Chat chat;
        TLRPC.User user;
        String str;
        TLRPC.Message message;
        TLRPC.MessageFwdHeader messageFwdHeader;
        String str2;
        MessageObject messageObject;
        TLRPC.Message message2;
        TLRPC.User user2;
        MessageObject messageObject2;
        TLRPC.Message message3;
        TLRPC.MessageFwdHeader messageFwdHeader2;
        TLRPC.Message message4;
        TLRPC.MessageFwdHeader messageFwdHeader3;
        TLRPC.MessageFwdHeader messageFwdHeader4;
        MessageObject messageObject3 = this.message;
        if (messageObject3 == null) {
            return null;
        }
        long fromChatId = messageObject3.getFromChatId();
        long clientUserId = UserConfig.getInstance(this.currentAccount).getClientUserId();
        if (!this.isSavedDialog && this.currentDialogId == clientUserId) {
            long savedDialogId = this.message.getSavedDialogId();
            if (savedDialogId == clientUserId) {
                return null;
            }
            if (savedDialogId != UserObject.ANONYMOUS) {
                TLRPC.Message message5 = this.message.messageOwner;
                if (message5 != null && (messageFwdHeader4 = message5.fwd_from) != null) {
                    long peerDialogId = DialogObject.getPeerDialogId(messageFwdHeader4.saved_from_id);
                    if (peerDialogId == 0) {
                        peerDialogId = DialogObject.getPeerDialogId(this.message.messageOwner.fwd_from.from_id);
                    }
                    if (peerDialogId > 0 && peerDialogId != savedDialogId) {
                        return null;
                    }
                }
                fromChatId = savedDialogId;
            }
        }
        if (this.isSavedDialog && (message4 = this.message.messageOwner) != null && (messageFwdHeader3 = message4.fwd_from) != null) {
            fromChatId = DialogObject.getPeerDialogId(messageFwdHeader3.saved_from_id);
            if (fromChatId == 0) {
                fromChatId = DialogObject.getPeerDialogId(this.message.messageOwner.fwd_from.from_id);
            }
        }
        if (DialogObject.isUserDialog(fromChatId)) {
            user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(fromChatId));
            chat = null;
        } else {
            chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-fromChatId));
            user = null;
        }
        long j = this.currentDialogId;
        if (j == clientUserId) {
            if (user != null) {
                return AndroidUtilities.escape(UserObject.getFirstName(user).replace("\n", ""));
            }
            if (chat != null) {
                return AndroidUtilities.escape(chat.title.replace("\n", ""));
            }
            return null;
        }
        if (j == UserObject.VERIFY && (messageObject2 = this.message) != null && (message3 = messageObject2.messageOwner) != null && (messageFwdHeader2 = message3.fwd_from) != null) {
            String str3 = messageFwdHeader2.from_name;
            if (str3 != null) {
                return AndroidUtilities.escape(str3);
            }
            long peerDialogId2 = DialogObject.getPeerDialogId(messageFwdHeader2.from_id);
            if (DialogObject.isUserDialog(peerDialogId2)) {
                return UserObject.getUserName(MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(peerDialogId2)));
            }
            TLRPC.Chat chat2 = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-peerDialogId2));
            return chat2 == null ? "" : chat2.title;
        }
        if (this.message.isOutOwner() && user != null) {
            return LocaleController.getString(C2369R.string.FromYou);
        }
        if (!this.isSavedDialog && (messageObject = this.message) != null && (message2 = messageObject.messageOwner) != null && (message2.from_id instanceof TLRPC.TL_peerUser) && (user2 = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(this.message.messageOwner.from_id.user_id))) != null) {
            return AndroidUtilities.escape(UserObject.getFirstName(user2).replace("\n", ""));
        }
        MessageObject messageObject4 = this.message;
        if (messageObject4 != null && (message = messageObject4.messageOwner) != null && (messageFwdHeader = message.fwd_from) != null && (str2 = messageFwdHeader.from_name) != null) {
            return AndroidUtilities.escape(str2);
        }
        if (user == null) {
            if (chat != null && (str = chat.title) != null) {
                return AndroidUtilities.escape(str.replace("\n", ""));
            }
            return LocaleController.getString(C2369R.string.HiddenName);
        }
        if (this.useForceThreeLines || SharedConfig.useThreeLinesLayout) {
            if (UserObject.isDeleted(user)) {
                return LocaleController.getString(C2369R.string.HiddenName);
            }
            return AndroidUtilities.escape(ContactsController.formatName(user.first_name, user.last_name).replace("\n", ""));
        }
        return AndroidUtilities.escape(UserObject.getFirstName(user).replace("\n", ""));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v0, types: [android.view.View, org.telegram.ui.Cells.DialogCell] */
    /* JADX WARN: Type inference failed for: r13v5, types: [android.text.Spannable, android.text.SpannableString, java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r13v6, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r13v7, types: [java.lang.CharSequence] */
    public SpannableStringBuilder getMessageStringFormatted(int i, String str, CharSequence charSequence, boolean z) {
        TLRPC.Message message;
        CharSequence string;
        String pluralString;
        String pluralString2;
        CharSequence charSequence2;
        String str2;
        SpannableStringBuilder spannableStringBuilderValueOf;
        TLRPC.TL_forumTopic tL_forumTopicFindTopic;
        MessageObject captionMessage = getCaptionMessage();
        MessageObject messageObject = this.message;
        CharSequence charSequence3 = messageObject != null ? messageObject.messageText : null;
        this.applyName = true;
        if (!TextUtils.isEmpty(str)) {
            return formatInternal(i, str, charSequence);
        }
        MessageObject messageObject2 = this.message;
        TLRPC.Message message2 = messageObject2.messageOwner;
        if (message2 instanceof TLRPC.TL_messageService) {
            CharSequence charSequence4 = messageObject2.messageTextShort;
            if (charSequence4 == null || ((message2.action instanceof TLRPC.TL_messageActionTopicCreate) && this.isTopic)) {
                charSequence4 = messageObject2.messageText;
            }
            if (MessageObject.isTopicActionMessage(messageObject2)) {
                spannableStringBuilderValueOf = formatInternal(i, charSequence4, charSequence);
                if ((this.message.topicIconDrawable[0] instanceof ForumBubbleDrawable) && (tL_forumTopicFindTopic = MessagesController.getInstance(this.currentAccount).getTopicsController().findTopic(-this.message.getDialogId(), MessageObject.getTopicId(this.currentAccount, this.message.messageOwner, true))) != null) {
                    ((ForumBubbleDrawable) this.message.topicIconDrawable[0]).setColor(tL_forumTopicFindTopic.icon_color);
                }
            } else {
                this.applyName = false;
                spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(charSequence4);
            }
            if (z) {
                applyThumbs(spannableStringBuilderValueOf);
            }
            return spannableStringBuilderValueOf;
        }
        if (captionMessage != null && (charSequence2 = captionMessage.caption) != null) {
            CharSequence string2 = charSequence2.toString();
            if (!this.needEmoji) {
                str2 = "";
            } else if (captionMessage.isVideo()) {
                str2 = "📹 ";
            } else if (captionMessage.isVoice()) {
                str2 = "🎤 ";
            } else if (captionMessage.isMusic()) {
                str2 = "🎧 ";
            } else if (captionMessage.isPhoto()) {
                str2 = "🖼 ";
            } else {
                str2 = "📎 ";
            }
            if (captionMessage.hasHighlightedWords() && !TextUtils.isEmpty(captionMessage.messageOwner.message)) {
                CharSequence string3 = captionMessage.messageTrimmedToHighlight;
                int measuredWidth = getMeasuredWidth() - AndroidUtilities.m1146dp(this.messagePaddingStart + 47);
                if (this.hasNameInMessage) {
                    if (!TextUtils.isEmpty(charSequence)) {
                        measuredWidth = (int) (measuredWidth - this.currentMessagePaint.measureText(charSequence.toString()));
                    }
                    measuredWidth = (int) (measuredWidth - this.currentMessagePaint.measureText(": "));
                }
                if (measuredWidth > 0 && captionMessage.messageTrimmedToHighlightCut) {
                    string3 = AndroidUtilities.ellipsizeCenterEnd(string3, captionMessage.highlightedWords.get(0), measuredWidth, this.currentMessagePaint, Opcodes.IXOR).toString();
                }
                return new SpannableStringBuilder(str2).append(string3);
            }
            if (string2.length() > 150) {
                string2 = string2.subSequence(0, 150);
            }
            SpannableString spannableString = new SpannableString(string2);
            captionMessage.spoilLoginCode();
            MediaDataController.addTextStyleRuns(captionMessage.messageOwner.entities, string2, spannableString, 264);
            TLRPC.Message message3 = captionMessage.messageOwner;
            if (message3 != null) {
                ArrayList arrayList = message3.entities;
                TextPaint textPaint = this.currentMessagePaint;
                MediaDataController.addAnimatedEmojiSpans(arrayList, spannableString, textPaint != null ? textPaint.getFontMetricsInt() : null);
            }
            CharSequence charSequenceAppend = new SpannableStringBuilder(str2).append(AndroidUtilities.replaceNewLines(spannableString));
            if (z) {
                charSequenceAppend = applyThumbs(charSequenceAppend);
            }
            return formatInternal(i, charSequenceAppend, charSequence);
        }
        if (message2.media != null && !messageObject2.isMediaEmpty()) {
            this.currentMessagePaint = Theme.dialogs_messagePrintingPaint[this.paintIndex];
            int i2 = Theme.key_chats_attachMessage;
            MessageObject messageObject3 = this.message;
            TLRPC.MessageMedia messageMedia = messageObject3.messageOwner.media;
            if (messageMedia instanceof TLRPC.TL_messageMediaPoll) {
                TLRPC.TL_messageMediaPoll tL_messageMediaPoll = (TLRPC.TL_messageMediaPoll) messageMedia;
                TLRPC.TL_textWithEntities tL_textWithEntities = tL_messageMediaPoll.poll.question;
                if (tL_textWithEntities == null || tL_textWithEntities.entities == null) {
                    string = String.format("📊 \u2068%s\u2069", tL_textWithEntities.text);
                } else {
                    SpannableString spannableString2 = new SpannableString(tL_messageMediaPoll.poll.question.text.replace('\n', ' '));
                    TLRPC.TL_textWithEntities tL_textWithEntities2 = tL_messageMediaPoll.poll.question;
                    MediaDataController.addTextStyleRuns((ArrayList<TLRPC.MessageEntity>) tL_textWithEntities2.entities, tL_textWithEntities2.text, spannableString2);
                    MediaDataController.addAnimatedEmojiSpans(tL_messageMediaPoll.poll.question.entities, spannableString2, Theme.dialogs_messagePaint[this.paintIndex].getFontMetricsInt());
                    string = new SpannableStringBuilder("📊 \u2068").append((CharSequence) spannableString2).append((CharSequence) "\u2069");
                }
            } else if (messageMedia instanceof TLRPC.TL_messageMediaToDo) {
                TLRPC.TL_messageMediaToDo tL_messageMediaToDo = (TLRPC.TL_messageMediaToDo) messageMedia;
                TLRPC.TL_textWithEntities tL_textWithEntities3 = tL_messageMediaToDo.todo.title;
                if (tL_textWithEntities3 == null || tL_textWithEntities3.entities == null) {
                    string = String.format("✅ \u2068%s\u2069", tL_textWithEntities3.text);
                } else {
                    SpannableString spannableString3 = new SpannableString(tL_messageMediaToDo.todo.title.text.replace('\n', ' '));
                    TLRPC.TL_textWithEntities tL_textWithEntities4 = tL_messageMediaToDo.todo.title;
                    MediaDataController.addTextStyleRuns((ArrayList<TLRPC.MessageEntity>) tL_textWithEntities4.entities, tL_textWithEntities4.text, spannableString3);
                    MediaDataController.addAnimatedEmojiSpans(tL_messageMediaToDo.todo.title.entities, spannableString3, Theme.dialogs_messagePaint[this.paintIndex].getFontMetricsInt());
                    string = new SpannableStringBuilder("✅ \u2068").append((CharSequence) spannableString3).append((CharSequence) "\u2069");
                }
            } else if (messageMedia instanceof TLRPC.TL_messageMediaGame) {
                string = String.format("🎮 \u2068%s\u2069", messageMedia.game.title);
            } else if (messageMedia instanceof TLRPC.TL_messageMediaInvoice) {
                string = messageMedia.title;
            } else if (messageObject3.type == 14) {
                string = String.format("🎧 \u2068%s - %s\u2069", messageObject3.getMusicAuthor(), this.message.getMusicTitle());
            } else if (messageMedia instanceof TLRPC.TL_messageMediaPaidMedia) {
                int size = ((TLRPC.TL_messageMediaPaidMedia) messageMedia).extended_media.size();
                if (this.hasVideoThumb) {
                    pluralString2 = size > 1 ? LocaleController.formatPluralString("Media", size, new Object[0]) : LocaleController.getString(C2369R.string.AttachVideo);
                } else {
                    pluralString2 = size > 1 ? LocaleController.formatPluralString("Photos", size, new Object[0]) : LocaleController.getString(C2369R.string.AttachPhoto);
                }
                string = StarsIntroActivity.replaceStars(LocaleController.formatString(C2369R.string.AttachPaidMedia, pluralString2));
                i2 = Theme.key_chats_actionMessage;
            } else if (this.thumbsCount > 1) {
                if (this.hasVideoThumb) {
                    ArrayList arrayList2 = this.groupMessages;
                    pluralString = LocaleController.formatPluralString("Media", arrayList2 == null ? 0 : arrayList2.size(), new Object[0]);
                } else {
                    ArrayList arrayList3 = this.groupMessages;
                    pluralString = LocaleController.formatPluralString("Photos", arrayList3 == null ? 0 : arrayList3.size(), new Object[0]);
                }
                string = pluralString;
                i2 = Theme.key_chats_actionMessage;
            } else {
                string = charSequence3.toString();
                i2 = Theme.key_chats_actionMessage;
            }
            if (string instanceof String) {
                string = ((String) string).replace('\n', ' ');
            }
            if (z) {
                string = applyThumbs(string);
            }
            SpannableStringBuilder internal = formatInternal(i, string, charSequence);
            if (!isForumCell()) {
                try {
                    internal.setSpan(new ForegroundColorSpanThemable(i2, this.resourcesProvider), this.hasNameInMessage ? charSequence.length() + 2 : 0, internal.length(), 33);
                } catch (Exception e) {
                    FileLog.m1160e(e);
                }
            }
            return internal;
        }
        MessageObject messageObject4 = this.message;
        CharSequence charSequenceReplaceNewLines = messageObject4.messageOwner.message;
        if (charSequenceReplaceNewLines != null) {
            if (messageObject4.hasHighlightedWords()) {
                CharSequence charSequence5 = this.message.messageTrimmedToHighlight;
                if (charSequence5 != null) {
                    charSequenceReplaceNewLines = charSequence5;
                }
                int measuredWidth2 = getMeasuredWidth() - AndroidUtilities.m1146dp(this.messagePaddingStart + 33);
                if (this.hasNameInMessage) {
                    if (!TextUtils.isEmpty(charSequence)) {
                        measuredWidth2 = (int) (measuredWidth2 - this.currentMessagePaint.measureText(charSequence.toString()));
                    }
                    measuredWidth2 = (int) (measuredWidth2 - this.currentMessagePaint.measureText(": "));
                }
                if (measuredWidth2 > 0) {
                    charSequenceReplaceNewLines = AndroidUtilities.ellipsizeCenterEnd(charSequenceReplaceNewLines, this.message.highlightedWords.get(0), measuredWidth2, this.currentMessagePaint, Opcodes.IXOR).toString();
                }
            } else {
                if (charSequenceReplaceNewLines.length() > 150) {
                    charSequenceReplaceNewLines = charSequenceReplaceNewLines.subSequence(0, 150);
                }
                charSequenceReplaceNewLines = AndroidUtilities.replaceNewLines(charSequenceReplaceNewLines);
            }
            ?? spannableString4 = new SpannableString(charSequenceReplaceNewLines);
            MessageObject messageObject5 = this.message;
            if (messageObject5 != null) {
                messageObject5.spoilLoginCode();
            }
            MediaDataController.addTextStyleRuns(this.message, (Spannable) spannableString4, 264);
            MessageObject messageObject6 = this.message;
            if (messageObject6 != null && (message = messageObject6.messageOwner) != null) {
                ArrayList arrayList4 = message.entities;
                TextPaint textPaint2 = this.currentMessagePaint;
                MediaDataController.addAnimatedEmojiSpans(arrayList4, spannableString4, textPaint2 != null ? textPaint2.getFontMetricsInt() : null);
            }
            if (z) {
                spannableString4 = applyThumbs(spannableString4);
            }
            return formatInternal(i, spannableString4, charSequence);
        }
        return new SpannableStringBuilder();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.rightFragmentOpenedProgress != 0.0f || this.isTopic || this.isShareToStoryCell || !this.storyParams.checkOnTouchEvent(motionEvent, this)) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if ((!this.isTopic && !this.isShareToStoryCell && motionEvent.getAction() == 1) || motionEvent.getAction() == 3) {
            this.storyParams.checkOnTouchEvent(motionEvent, this);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        CanvasButton canvasButton;
        int i;
        if (this.rightFragmentOpenedProgress == 0.0f && !this.isTopic && !this.isShareToStoryCell && this.storyParams.checkOnTouchEvent(motionEvent, this)) {
            return true;
        }
        DialogCellDelegate dialogCellDelegate = this.delegate;
        if (dialogCellDelegate == null || dialogCellDelegate.canClickButtonInside()) {
            if (this.openBot) {
                boolean zContains = this.openButtonRect.contains(motionEvent.getX(), motionEvent.getY());
                if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
                    this.openButtonBounce.setPressed(zContains);
                } else {
                    if (this.openButtonBounce.isPressed() && motionEvent.getAction() == 1) {
                        Utilities.Callback callback = this.onOpenButtonClick;
                        if (callback != null) {
                            callback.run(this.user);
                        }
                        this.openButtonBounce.setPressed(false);
                        return true;
                    }
                    if (this.openButtonBounce.isPressed() && motionEvent.getAction() == 3) {
                        this.openButtonBounce.setPressed(false);
                        return true;
                    }
                }
                if (zContains) {
                    return true;
                }
            }
            if (this.lastTopicMessageUnread && (canvasButton = this.canvasButton) != null && this.buttonLayout != null && (((i = this.dialogsType) == 0 || i == 7 || i == 8) && canvasButton.checkTouchEvent(motionEvent))) {
                return true;
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setClipProgress(float f) {
        this.clipProgress = f;
        invalidate();
    }

    public float getClipProgress() {
        return this.clipProgress;
    }

    public void setTopClip(int i) {
        this.topClip = i;
    }

    public void setBottomClip(int i) {
        this.bottomClip = i;
    }

    public void setArchivedPullAnimation(PullForegroundDrawable pullForegroundDrawable) {
        this.archivedChatsDrawable = pullForegroundDrawable;
    }

    public int getCurrentDialogFolderId() {
        return this.currentDialogFolderId;
    }

    public boolean isDialogFolder() {
        return this.currentDialogFolderId > 0;
    }

    public MessageObject getMessage() {
        return this.message;
    }

    public void setDialogCellDelegate(DialogCellDelegate dialogCellDelegate) {
        this.delegate = dialogCellDelegate;
    }

    private class DialogUpdateHelper {
        public long lastDrawnDialogId;
        public boolean lastDrawnDialogIsFolder;
        public int lastDrawnDraftHash;
        public boolean lastDrawnHasCall;
        public long lastDrawnMessageId;
        public boolean lastDrawnPinned;
        public Integer lastDrawnPrintingType;
        public long lastDrawnReadState;
        public int lastDrawnSizeHash;
        public boolean lastDrawnTranslated;
        public int lastKnownTypingType;
        public int lastTopicsCount;
        long startWaitingTime;
        public boolean typingOutToTop;
        public float typingProgres;
        boolean waitngNewMessageFroTypingAnimation;

        private DialogUpdateHelper() {
            this.waitngNewMessageFroTypingAnimation = false;
        }

        /* JADX WARN: Removed duplicated region for block: B:137:0x02d5  */
        /* JADX WARN: Removed duplicated region for block: B:138:0x02d9  */
        /* JADX WARN: Removed duplicated region for block: B:140:0x02de  */
        /* JADX WARN: Removed duplicated region for block: B:41:0x0107  */
        /* JADX WARN: Removed duplicated region for block: B:51:0x015c  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean update() {
            /*
                Method dump skipped, instructions count: 780
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.DialogCell.DialogUpdateHelper.update():boolean");
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0025  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void updateAnimationValues() {
            /*
                r5 = this;
                boolean r0 = r5.waitngNewMessageFroTypingAnimation
                if (r0 != 0) goto L40
                java.lang.Integer r0 = r5.lastDrawnPrintingType
                r1 = 0
                r2 = 1034147594(0x3da3d70a, float:0.08)
                r3 = 1065353216(0x3f800000, float:1.0)
                if (r0 == 0) goto L25
                org.telegram.ui.Cells.DialogCell r0 = org.telegram.p023ui.Cells.DialogCell.this
                android.text.StaticLayout r0 = org.telegram.p023ui.Cells.DialogCell.m5936$$Nest$fgettypingLayout(r0)
                if (r0 == 0) goto L25
                float r0 = r5.typingProgres
                int r4 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
                if (r4 == 0) goto L25
                float r0 = r0 + r2
                r5.typingProgres = r0
                org.telegram.ui.Cells.DialogCell r0 = org.telegram.p023ui.Cells.DialogCell.this
                r0.invalidate()
                goto L37
            L25:
                java.lang.Integer r0 = r5.lastDrawnPrintingType
                if (r0 != 0) goto L37
                float r0 = r5.typingProgres
                int r4 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
                if (r4 == 0) goto L37
                float r0 = r0 - r2
                r5.typingProgres = r0
                org.telegram.ui.Cells.DialogCell r0 = org.telegram.p023ui.Cells.DialogCell.this
                r0.invalidate()
            L37:
                float r0 = r5.typingProgres
                float r0 = org.telegram.messenger.Utilities.clamp(r0, r3, r1)
                r5.typingProgres = r0
                return
            L40:
                long r0 = java.lang.System.currentTimeMillis()
                long r2 = r5.startWaitingTime
                long r0 = r0 - r2
                r2 = 100
                int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r4 <= 0) goto L50
                r0 = 0
                r5.waitngNewMessageFroTypingAnimation = r0
            L50:
                org.telegram.ui.Cells.DialogCell r0 = org.telegram.p023ui.Cells.DialogCell.this
                r0.invalidate()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.DialogCell.DialogUpdateHelper.updateAnimationValues():void");
        }
    }

    @Override // org.telegram.p023ui.Cells.BaseCell, android.view.View
    public void invalidate() {
        if (StoryViewer.animationInProgress) {
            return;
        }
        super.invalidate();
    }

    @Override // android.view.View
    public void invalidate(int i, int i2, int i3, int i4) {
        if (StoryViewer.animationInProgress) {
            return;
        }
        super.invalidate(i, i2, i3, i4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ForumFormattedNames {
        HashMap avatarSpans;
        CharSequence formattedNames;
        boolean isLoadingState;
        int lastMessageId;
        boolean lastTopicMessageUnread;
        private final DialogCell parent;
        int topMessageTopicEndIndex;
        int topMessageTopicStartIndex;

        ForumFormattedNames(DialogCell dialogCell) {
            this.parent = dialogCell;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void invalidateSpans() {
            HashMap map = this.avatarSpans;
            if (map == null || map.isEmpty()) {
                return;
            }
            for (Map.Entry entry : this.avatarSpans.entrySet()) {
                ((AvatarSpan) entry.getValue()).setDialogId(((Long) entry.getKey()).longValue());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void formatTopicsNames(int i, MessageObject messageObject, TLRPC.Chat chat) {
            long topicId;
            boolean z;
            int length;
            int id = (messageObject == null || chat == null) ? 0 : messageObject.getId();
            if (this.lastMessageId != id || this.isLoadingState) {
                this.avatarSpans = null;
                this.topMessageTopicStartIndex = 0;
                this.topMessageTopicEndIndex = 0;
                this.lastTopicMessageUnread = false;
                this.isLoadingState = false;
                this.lastMessageId = id;
                TextPaint textPaint = Theme.dialogs_messagePaint[0];
                if (chat != null) {
                    ArrayList<TLRPC.TL_forumTopic> topics = MessagesController.getInstance(i).getTopicsController().getTopics(chat.f1571id);
                    boolean z2 = true;
                    if (topics != null && !topics.isEmpty()) {
                        ArrayList arrayList = new ArrayList(topics);
                        Collections.sort(arrayList, Comparator.CC.comparingInt(new ToIntFunction() { // from class: org.telegram.ui.Cells.DialogCell$ForumFormattedNames$$ExternalSyntheticLambda0
                            @Override // java.util.function.ToIntFunction
                            public final int applyAsInt(Object obj) {
                                return DialogCell.ForumFormattedNames.$r8$lambda$uICq3jFQRNqxUSEcmTlTge6AEXo((TLRPC.TL_forumTopic) obj);
                            }
                        }));
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                        if (messageObject == null || ChatObject.isMonoForum(chat)) {
                            topicId = 0;
                            z = false;
                            length = 0;
                        } else {
                            topicId = MessageObject.getTopicId(i, messageObject.messageOwner, true);
                            TLRPC.TL_forumTopic tL_forumTopicFindTopic = MessagesController.getInstance(i).getTopicsController().findTopic(chat.f1571id, topicId);
                            if (tL_forumTopicFindTopic != null) {
                                CharSequence topicSpannedName = ForumUtilities.getTopicSpannedName(tL_forumTopicFindTopic, textPaint, false);
                                spannableStringBuilder.append(topicSpannedName);
                                length = tL_forumTopicFindTopic.unread_count > 0 ? topicSpannedName.length() : 0;
                                this.topMessageTopicStartIndex = 0;
                                this.topMessageTopicEndIndex = topicSpannedName.length();
                                if (messageObject.isOutOwner()) {
                                    this.lastTopicMessageUnread = false;
                                } else {
                                    this.lastTopicMessageUnread = tL_forumTopicFindTopic.unread_count > 0;
                                }
                            } else {
                                this.lastTopicMessageUnread = false;
                                length = 0;
                            }
                            if (this.lastTopicMessageUnread) {
                                spannableStringBuilder.append((CharSequence) " ");
                                spannableStringBuilder.setSpan(new FixedWidthSpan(AndroidUtilities.m1146dp(3.0f)), spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 0);
                                z = true;
                            } else {
                                z = false;
                            }
                        }
                        if (ChatObject.isMonoForum(chat)) {
                            this.avatarSpans = new HashMap();
                            for (int i2 = 0; i2 < Math.min(4, arrayList.size()); i2++) {
                                if (spannableStringBuilder.length() != 0) {
                                    spannableStringBuilder.append((CharSequence) "  ");
                                }
                                long peerDialogId = DialogObject.getPeerDialogId(((TLRPC.TL_forumTopic) arrayList.get(i2)).from_id);
                                AvatarSpan avatarSpan = new AvatarSpan(this.parent, i);
                                avatarSpan.needDrawShadow = false;
                                avatarSpan.setDialogId(peerDialogId);
                                this.avatarSpans.put(Long.valueOf(peerDialogId), avatarSpan);
                                SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(DialogObject.getName(peerDialogId));
                                spannableStringBuilderValueOf.insert(0, (CharSequence) "  ");
                                spannableStringBuilderValueOf.setSpan(avatarSpan, 0, 1, 33);
                                spannableStringBuilder.append((CharSequence) spannableStringBuilderValueOf);
                            }
                        } else {
                            int i3 = 0;
                            for (int i4 = 4; i3 < Math.min(i4, arrayList.size()); i4 = 4) {
                                if (((TLRPC.TL_forumTopic) arrayList.get(i3)).f1631id != topicId) {
                                    if (spannableStringBuilder.length() != 0) {
                                        if (z2 && z) {
                                            spannableStringBuilder.append((CharSequence) " ");
                                        } else {
                                            spannableStringBuilder.append((CharSequence) ", ");
                                        }
                                    }
                                    spannableStringBuilder.append(ForumUtilities.getTopicSpannedName((TLRPC.ForumTopic) arrayList.get(i3), textPaint, false));
                                    z2 = false;
                                }
                                i3++;
                            }
                        }
                        if (length > 0) {
                            spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold(), 0, Theme.key_chats_name, null), 0, Math.min(spannableStringBuilder.length(), length + 2), 0);
                        }
                        this.formattedNames = spannableStringBuilder;
                        return;
                    }
                    if (MessagesController.getInstance(i).getTopicsController().endIsReached(chat.f1571id)) {
                        this.formattedNames = LocaleController.getString(ChatObject.isMonoForum(chat) ? C2369R.string.NoMonoforumTopicsCreated : C2369R.string.NoTopicsCreated);
                        return;
                    }
                    MessagesController.getInstance(i).getTopicsController().preloadTopics(chat.f1571id);
                    this.formattedNames = LocaleController.getString(C2369R.string.Loading);
                    this.isLoadingState = true;
                }
            }
        }

        public static /* synthetic */ int $r8$lambda$uICq3jFQRNqxUSEcmTlTge6AEXo(TLRPC.TL_forumTopic tL_forumTopic) {
            return -tL_forumTopic.top_message;
        }
    }

    private ColorFilter getAdaptiveEmojiColorFilter(int i, int i2) {
        if (this.adaptiveEmojiColorFilter == null) {
            this.adaptiveEmojiColor = new int[4];
            this.adaptiveEmojiColorFilter = new ColorFilter[4];
        }
        if (i2 != this.adaptiveEmojiColor[i] || this.adaptiveEmojiColorFilter[i] == null) {
            ColorFilter[] colorFilterArr = this.adaptiveEmojiColorFilter;
            this.adaptiveEmojiColor[i] = i2;
            colorFilterArr[i] = new PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_IN);
        }
        return this.adaptiveEmojiColorFilter[i];
    }

    public void showPremiumBlocked(boolean z) {
        Runnable runnable = this.unsubscribePremiumBlocked;
        if (z != (runnable != null)) {
            if (!z && runnable != null) {
                runnable.run();
                this.unsubscribePremiumBlocked = null;
            } else if (z) {
                this.unsubscribePremiumBlocked = NotificationCenter.getInstance(this.currentAccount).listen(this, NotificationCenter.userIsPremiumBlockedUpadted, new Utilities.Callback() { // from class: org.telegram.ui.Cells.DialogCell$$ExternalSyntheticLambda6
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$showPremiumBlocked$5((Object[]) obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPremiumBlocked$5(Object[] objArr) {
        updatePremiumBlocked(true);
    }

    private void updatePremiumBlocked(boolean z) {
        TL_account.RequirementToContact requirementToContactIsUserContactBlocked = (this.unsubscribePremiumBlocked == null || this.user == null) ? null : MessagesController.getInstance(this.currentAccount).isUserContactBlocked(this.user.f1734id);
        if (this.premiumBlocked == DialogObject.isPremiumBlocked(requirementToContactIsUserContactBlocked) && this.starsPriceBlocked == DialogObject.getMessagesStarsPrice(requirementToContactIsUserContactBlocked)) {
            return;
        }
        this.premiumBlocked = DialogObject.isPremiumBlocked(requirementToContactIsUserContactBlocked);
        this.starsPriceBlocked = DialogObject.getMessagesStarsPrice(requirementToContactIsUserContactBlocked);
        if (!z) {
            this.premiumBlockedT.set(this.premiumBlocked, true);
            this.starsBlockedT.set(this.starsPriceBlocked > 0, true);
        }
        invalidate();
    }

    @Override // org.telegram.p023ui.Cells.BaseCell
    protected boolean allowCaching() {
        return this.rightFragmentOpenedProgress <= 0.0f;
    }
}
