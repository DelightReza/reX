package org.telegram.p023ui.Cells;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Property;
import android.util.SparseArray;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.widget.Toast;
import androidx.annotation.Keep;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.math.MathUtils;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.api.dto.BadgeDTO;
import com.exteragram.messenger.badges.BadgesController;
import com.exteragram.messenger.speech.VoiceRecognitionController;
import com.radolyn.ayugram.AyuConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;
import me.vkryl.android.animator.BoolAnimator;
import me.vkryl.android.animator.FactorAnimator;
import org.mvel2.DataTypes;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BotForumHelper;
import org.telegram.messenger.BotInlineKeyboard;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatMessageSharedResources;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.FlagSecureReason;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.WebFile;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.utils.FrameTickScheduler;
import org.telegram.messenger.video.OldVideoPlayerRewinder;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.AvatarSpan;
import org.telegram.p023ui.Cells.BaseCell;
import org.telegram.p023ui.Cells.TextSelectionHelper;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.AnimatedEmojiSpan;
import org.telegram.p023ui.Components.AnimatedFileDrawable;
import org.telegram.p023ui.Components.AnimatedFloat;
import org.telegram.p023ui.Components.AnimatedNumberLayout;
import org.telegram.p023ui.Components.AnimatedTextView;
import org.telegram.p023ui.Components.AudioVisualizerDrawable;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.AvatarsDrawable;
import org.telegram.p023ui.Components.ButtonBounce;
import org.telegram.p023ui.Components.CheckBoxBase;
import org.telegram.p023ui.Components.ColoredImageSpan;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.FloatSeekBarAccessibilityDelegate;
import org.telegram.p023ui.Components.ForwardBackground;
import org.telegram.p023ui.Components.InfiniteProgress;
import org.telegram.p023ui.Components.LinkPath;
import org.telegram.p023ui.Components.LinkSpanDrawable;
import org.telegram.p023ui.Components.LoadingDrawable;
import org.telegram.p023ui.Components.MessageBackgroundDrawable;
import org.telegram.p023ui.Components.MotionBackgroundDrawable;
import org.telegram.p023ui.Components.MsgClockDrawable;
import org.telegram.p023ui.Components.Point;
import org.telegram.p023ui.Components.Premium.boosts.BoostCounterSpan;
import org.telegram.p023ui.Components.Premium.boosts.cells.msg.GiveawayMessageCell;
import org.telegram.p023ui.Components.Premium.boosts.cells.msg.GiveawayResultsMessageCell;
import org.telegram.p023ui.Components.QuoteHighlight;
import org.telegram.p023ui.Components.RLottieDrawable;
import org.telegram.p023ui.Components.RadialProgress2;
import org.telegram.p023ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.ReplyMessageLine;
import org.telegram.p023ui.Components.RoundVideoPlayingDrawable;
import org.telegram.p023ui.Components.SeekBar;
import org.telegram.p023ui.Components.SeekBarAccessibilityDelegate;
import org.telegram.p023ui.Components.SeekBarWaveform;
import org.telegram.p023ui.Components.SizeNotifierFrameLayout;
import org.telegram.p023ui.Components.SlotsDrawable;
import org.telegram.p023ui.Components.StaticLayoutEx;
import org.telegram.p023ui.Components.StickerSetLinkIcon;
import org.telegram.p023ui.Components.SuggestionOffer;
import org.telegram.p023ui.Components.Text;
import org.telegram.p023ui.Components.TextStyleSpan;
import org.telegram.p023ui.Components.TimerParticles;
import org.telegram.p023ui.Components.TopicSeparator;
import org.telegram.p023ui.Components.TranscribeButton;
import org.telegram.p023ui.Components.TypefaceSpan;
import org.telegram.p023ui.Components.URLSpanBotCommand;
import org.telegram.p023ui.Components.URLSpanBrowser;
import org.telegram.p023ui.Components.URLSpanMono;
import org.telegram.p023ui.Components.URLSpanNoUnderline;
import org.telegram.p023ui.Components.VideoForwardDrawable;
import org.telegram.p023ui.Components.VideoPlayer;
import org.telegram.p023ui.Components.spoilers.SpoilerEffect;
import org.telegram.p023ui.Components.spoilers.SpoilerEffect2;
import org.telegram.p023ui.GradientClip;
import org.telegram.p023ui.MultiLayoutTypingAnimator;
import org.telegram.p023ui.PhotoViewer;
import org.telegram.p023ui.PinchToZoomHelper;
import org.telegram.p023ui.Stories.recorder.CaptionContainerView;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_stars;

/* loaded from: classes3.dex */
public class ChatMessageCell extends BaseCell implements SeekBar.SeekBarDelegate, ImageReceiver.ImageReceiverDelegate, DownloadController.FileDownloadProgressListener, TextSelectionHelper.SelectableView, NotificationCenter.NotificationCenterDelegate, FactorAnimator.Target {
    private static float[] radii = new float[8];
    private final boolean ALPHA_PROPERTY_WORKAROUND;
    public Property ANIMATION_OFFSET_X;
    private int TAG;
    CharSequence accessibilityText;
    private boolean accessibilityTextContentUnread;
    private long accessibilityTextFileSize;
    private boolean accessibilityTextUnread;
    private SparseArray accessibilityVirtualViewBounds;
    private float actionAlpha;
    private int[] adaptiveEmojiColor;
    private ColorFilter[] adaptiveEmojiColorFilter;
    private int addedCaptionHeight;
    private boolean addedForTest;
    private int additionalPaddingHeight;
    private int additionalTimeOffsetY;
    private StaticLayout adminLayout;
    private boolean allowAssistant;
    private float alphaInternal;
    private int animateFromStatusDrawableParams;
    private boolean animatePollAnswer;
    private boolean animatePollAnswerAlpha;
    private boolean animatePollAvatars;
    private int animateToStatusDrawableParams;
    public AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiDescriptionStack;
    public AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiPollQuestion;
    public AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiReplyStack;
    public AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiStack;
    private AnimatedTextView.AnimatedTextDrawable animatedInfoLayout;
    private int animatingDrawVideoImageButton;
    private float animatingDrawVideoImageButtonProgress;
    private float animatingLoadingProgressProgress;
    private int animatingNoSound;
    private boolean animatingNoSoundPlaying;
    private float animatingNoSoundProgress;
    private float animationOffsetX;
    private boolean animationRunning;
    public int askBotForumBottomPadding;
    private BotAskCellDrawable askBotForumBubble;
    private TopicSeparator askBotForumSeparator;
    private boolean attachedToWindow;
    private StaticLayout authorLayout;
    private int authorLayoutLeft;
    private int authorLayoutWidth;
    private boolean autoPlayingMedia;
    private int availableTimeWidth;
    protected AvatarDrawable avatarDrawable;
    private ImageReceiver avatarImage;
    private boolean avatarPressed;
    public boolean ayuDeleted;
    public AnimatorSet ayuDeletedAnimation;
    private Theme.MessageDrawable.PathDrawParams backgroundCacheParams;
    private MessageBackgroundDrawable backgroundDrawable;
    private int backgroundDrawableBottom;
    private int backgroundDrawableLeft;
    private int backgroundDrawableRight;
    private int backgroundDrawableTop;
    public int backgroundHeight;
    public int backgroundWidth;
    private ImageReceiver blurredPhotoImage;
    public int blurredViewBottomOffset;
    public int blurredViewTopOffset;
    private RectF boostCounterBounds;
    private Drawable boostCounterLayoutSelector;
    private boolean boostCounterPressed;
    private int boostCounterSelectorColor;
    private BoostCounterSpan boostCounterSpan;
    private Path botButtonPath;
    private float[] botButtonRadii;
    private ArrayList botButtons;
    private HashMap botButtonsByData;
    private HashMap botButtonsByPosition;
    private String botButtonsLayout;
    public MultiLayoutTypingAnimator botDraftTypingAnimator;
    private boolean bottomNearToSet;
    private int buttonPressed;
    private int buttonState;
    private int buttonX;
    private int buttonY;
    private final boolean canDrawBackgroundInParent;
    private boolean canStreamVideo;
    public boolean captionAbove;
    private int captionFullWidth;
    private int captionHeight;
    public MessageObject.TextLayoutBlocks captionLayout;
    private int captionOffsetX;
    private int captionWidth;
    private float captionX;
    public float captionY;
    public ChannelRecommendationsCell channelRecommendationsCell;
    private CheckBoxBase checkBox;
    private boolean checkBoxAnimationInProgress;
    private float checkBoxAnimationProgress;
    public int checkBoxTranslation;
    private boolean checkBoxVisible;
    private boolean checkOnlyButtonPressed;
    public int childPosition;
    private GradientClip clip;
    private Paint clipPaint;
    public boolean clipToGroupBounds;
    private ButtonBounce closeSponsoredBounce;
    private RectF closeSponsoredBounds;
    private String closeTimeText;
    private int closeTimeWidth;
    private int commentArrowX;
    private AvatarDrawable[] commentAvatarDrawables;
    private ImageReceiver[] commentAvatarImages;
    private boolean[] commentAvatarImagesVisible;
    private boolean commentButtonPressed;
    private Rect commentButtonRect;
    private boolean commentDrawUnread;
    private StaticLayout commentLayout;
    private LoadingDrawable commentLoading;
    private AnimatedNumberLayout commentNumberLayout;
    private int commentNumberWidth;
    private InfiniteProgress commentProgress;
    private float commentProgressAlpha;
    private long commentProgressLastUpadteTime;
    private int commentUnreadX;
    private int commentWidth;
    private int commentX;
    public MessageObject.TextLayoutBlocks computedCaptionLayout;
    public int computedGroupCaptionY;
    private AvatarDrawable contactAvatarDrawable;
    public ButtonBounce contactBounce;
    private ArrayList contactButtons;
    public ReplyMessageLine contactLine;
    private boolean contactPressed;
    private RectF contactRect;
    private float controlsAlpha;
    public final int currentAccount;
    public Theme.MessageDrawable currentBackgroundDrawable;
    private Theme.MessageDrawable currentBackgroundSelectedDrawable;
    private CharSequence currentCaption;
    private TLRPC.Chat currentChat;
    private int currentFocusedVirtualView;
    private TLRPC.Chat currentForwardChannel;
    private String currentForwardName;
    private String currentForwardNameString;
    private TLRPC.User currentForwardUser;
    private int currentMapProvider;
    private MessageObject currentMessageObject;
    private MessageObject.GroupedMessages currentMessagesGroup;
    private long currentNameBotVerificationId;
    public AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable currentNameEmojiStatusDrawable;
    private Object currentNameStatus;
    public AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable currentNameStatusDrawable;
    private String currentNameString;
    private TLRPC.FileLocation currentPhoto;
    private String currentPhotoFilter;
    private String currentPhotoFilterThumb;
    private ImageLocation currentPhotoLocation;
    private TLRPC.PhotoSize currentPhotoObject;
    private TLRPC.PhotoSize currentPhotoObjectThumb;
    private BitmapDrawable currentPhotoObjectThumbStripped;
    private ImageLocation currentPhotoThumbLocation;
    private MessageObject.GroupedMessagePosition currentPosition;
    private String currentRepliesString;
    private TLRPC.PhotoSize currentReplyPhoto;
    private float currentSelectedBackgroundAlpha;
    private SpannableStringBuilder currentTimeString;
    private String currentUnlockString;
    private String currentUrl;
    private TLRPC.User currentUser;
    private TLRPC.User currentViaBotUser;
    private String currentViewsString;
    private WebFile currentWebFile;
    private ChatMessageCellDelegate delegate;
    private RectF deleteProgressRect;
    private StaticLayout descriptionLayout;
    private int descriptionLayoutLeft;
    private int descriptionLayoutWidth;
    private int descriptionX;
    private int descriptionY;
    private Runnable diceFinishCallback;
    private boolean disallowLongPress;
    public boolean doNotDraw;
    public int doNotDrawTaskId;
    private StaticLayout docTitleLayout;
    private int docTitleOffsetX;
    private int docTitleWidth;
    private TLRPC.Document documentAttach;
    private int documentAttachType;
    public BotForumHelper.BotDraftAnimationsPool draftAnimationsPool;
    private boolean drawBackground;
    private boolean drawCommentButton;
    private boolean drawCommentNumber;
    private boolean drawContact;
    private boolean drawContactAdd;
    private boolean drawContactSendMessage;
    private boolean drawContactView;
    public boolean drawForBlur;
    private boolean drawForwardedName;
    public boolean drawFromPinchToZoom;
    private boolean drawImageButton;
    private boolean drawInstantView;
    public int drawInstantViewType;
    private boolean drawMediaCheckBox;
    private boolean drawName;
    private boolean drawNameAvatar;
    private boolean drawNameLayout;
    public boolean drawPhotoImage;
    public boolean drawPinnedBottom;
    public boolean drawPinnedTop;
    private boolean drawRadialCheckBackground;
    private boolean drawSelectionBackground;
    private int drawSideButton;
    private int drawSideButton2;
    private boolean drawTime;
    private float drawTimeX;
    private float drawTimeY;
    private boolean drawTopic;
    private boolean drawVideoImageButton;
    private boolean drawVideoSize;
    public boolean drawingToBitmap;
    private int drawnContactButtonsFlag;
    private Paint drillHolePaint;
    private Path drillHolePath;
    private StaticLayout durationLayout;
    private int durationWidth;
    private boolean edited;
    private AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable effectDrawable;
    private ButtonBounce effectDrawableBounce;
    private long effectId;
    private int effectMessageId;
    boolean enterTransitionInProgress;
    public ExpiredStoryView expiredStoryView;
    private Drawable factCheckArrow;
    private int factCheckArrowColor;
    private ButtonBounce factCheckBounce;
    private int factCheckHeight;
    private boolean factCheckLarge;
    public ReplyMessageLine factCheckLine;
    private LinkSpanDrawable.LinkCollector factCheckLinks;
    private boolean factCheckPressed;
    private StaticLayout factCheckText2Layout;
    private int factCheckText2LayoutLeft;
    private StaticLayout factCheckTextLayout;
    private int factCheckTextLayoutHeight;
    private boolean factCheckTextLayoutLastLineEnd;
    private int factCheckTextLayoutLeft;
    private Text factCheckTitle;
    private Text factCheckWhat;
    private ButtonBounce factCheckWhatBounce;
    private boolean factCheckWhatPressed;
    private int factCheckWidth;
    private int factCheckY;
    private ColorMatrixColorFilter fancyBlurFilter;
    private boolean firstCircleLength;
    public boolean firstInChat;
    private boolean firstInChatToSet;
    private int firstVisibleBlockNum;
    private int firstVisiblePollButton;
    private boolean fitPhotoImage;
    private FlagSecureReason flagSecure;
    private boolean flipImage;
    private boolean forceNotDrawTime;
    private Integer forceSemiTransparent;
    private boolean forcedLayout;
    private Drawable foreverDrawable;
    private int foreverDrawableColor;
    private AvatarSpan forwardAvatar;
    private ForwardBackground forwardBg;
    private boolean forwardBotPressed;
    private int forwardHeight;
    private int forwardNameCenterX;
    private final float[] forwardNameOffsetX;
    private boolean forwardNamePressed;
    private float forwardNameX;
    private int forwardNameY;
    private final StaticLayout[] forwardedNameLayout;
    private int forwardedNameWidth;
    private boolean frozen;
    private boolean fullyDraw;
    private boolean gamePreviewPressed;
    public final GiveawayMessageCell giveawayMessageCell;
    public final GiveawayResultsMessageCell giveawayResultsMessageCell;
    private Drawable gradientDrawable;
    private LinearGradient gradientShader;
    private Drawable groupCallDrawable;
    private int groupCallDrawableColor;
    private AvatarsDrawable groupCallParticipantsAvatars;
    private Text groupCallParticipantsText;
    public GroupMedia groupMedia;
    private boolean groupPhotoInvisible;
    private MessageObject.GroupedMessages groupedMessagesToSet;
    private boolean hadLongPress;
    public boolean hasDiscussion;
    private boolean hasEmbed;
    private boolean hasFactCheck;
    private boolean hasGamePreview;
    private boolean hasInvoicePreview;
    private boolean hasInvoicePrice;
    private boolean hasLinkPreview;
    private int hasMiniProgress;
    private boolean hasNewLineForTime;
    private boolean hasOldCaptionPreview;
    private boolean hasPsaHint;
    public boolean hasReplyQuote;
    private boolean hideSideButtonByQuickShare;
    private int highlightCaptionToSetEnd;
    private int highlightCaptionToSetStart;
    private LinkPath highlightPath;
    private long highlightPathStart;
    private int highlightProgress;
    private boolean highlightedQuote;
    private float hintButtonProgress;
    private boolean hintButtonVisible;
    private boolean ignoreDeletedAlpha;
    private int imageBackgroundColor;
    private int imageBackgroundGradientColor1;
    private int imageBackgroundGradientColor2;
    private int imageBackgroundGradientColor3;
    private int imageBackgroundGradientRotation;
    private float imageBackgroundIntensity;
    private int imageBackgroundSideColor;
    private int imageBackgroundSideWidth;
    private boolean imageDrawn;
    private boolean imagePressed;
    boolean imageReceiversAttachState;
    boolean imageReceiversVisibleState;
    private boolean inLayout;
    private boolean inQuickShareMode;
    private StaticLayout infoLayout;
    private int infoWidth;
    private ButtonBounce instantButtonBounce;
    private LoadingDrawable instantButtonLoading;
    private boolean instantButtonPressed;
    private RectF instantButtonRect;
    public Drawable instantDrawable;
    public int instantDrawableColor;
    private Paint instantLinkArrowPaint;
    private Path instantLinkArrowPath;
    private boolean instantPressed;
    private int instantTextLeftX;
    private boolean instantTextNewLine;
    private int instantTextX;
    public String instantViewButtonText;
    private StaticLayout instantViewLayout;
    private float instantViewLayoutLeft;
    private float instantViewLayoutWidth;
    private TL_stars.StarGift instantViewTypeIsGiftAuction;
    private int instantWidth;
    private Runnable invalidateListener;
    private final Runnable invalidateOutboundsRunnable;
    private Runnable invalidateRunnable;
    private boolean invalidateSpoilersParent;
    private boolean invalidatesParent;
    public boolean isAllChats;
    public boolean isAvatarVisible;
    public boolean isBlurred;
    public boolean isBot;
    public boolean isBotForum;
    public boolean isChat;
    private boolean isCheckPressed;
    public boolean isForum;
    public boolean isForumGeneral;
    private boolean isHighlighted;
    private boolean isHighlightedAnimated;
    private boolean isMedia;
    public boolean isMegagroup;
    public boolean isMonoForum;
    public boolean isPinned;
    public boolean isPinnedChat;
    private boolean isPlayingRound;
    private boolean isPressed;
    public boolean isRepliesChat;
    public boolean isReplyQuote;
    public boolean isReplyTask;
    public boolean isReportChat;
    private boolean isRoundVideo;
    public boolean isSavedChat;
    public boolean isSavedPreviewChat;
    public boolean isSideMenuEnabled;
    public boolean isSideMenued;
    private boolean isSmallImage;
    private boolean isSpoilerRevealing;
    private final BoolAnimator isSponsoredMessageHidden;
    public boolean isThreadChat;
    private boolean isThreadPost;
    private boolean isTitleLabelPressed;
    private boolean isUpdating;
    private int keyboardHeight;
    private long lastAnimationTime;
    private long lastCheckBoxAnimationTime;
    private long lastControlsAlphaChangeTime;
    private int lastDeleteDate;
    private float lastDrawingAudioProgress;
    private int lastHeight;
    private long lastHighlightProgressTime;
    public boolean lastInChatList;
    private boolean lastInChatListToSet;
    private long lastLoadingSizeTotal;
    private long lastNamesAnimationTime;
    private TLRPC.Poll lastPoll;
    private long lastPollCloseTime;
    private ArrayList lastPollResults;
    private int lastPollResultsVoters;
    private String lastPostAuthor;
    private TLRPC.TL_messageReactions lastReactions;
    private int lastRepliesCount;
    private TLRPC.Message lastReplyMessage;
    private long lastSeekUpdateTime;
    private int lastSendState;
    int lastSize;
    private double lastTime;
    private float lastTouchX;
    private float lastTouchY;
    private boolean lastTranslated;
    private int lastViewsCount;
    private int lastVisibleBlockNum;
    private int lastVisiblePollButton;
    private WebFile lastWebFile;
    private int lastWidth;
    public int layoutHeight;
    public int layoutWidth;
    public int linkBlockNum;
    public ReplyMessageLine linkLine;
    public boolean linkPreviewAbove;
    private ButtonBounce linkPreviewBounce;
    public int linkPreviewHeight;
    private boolean linkPreviewPressed;
    private Drawable linkPreviewSelector;
    public int linkPreviewSelectorColor;
    private int linkPreviewY;
    private int linkSelectionBlockNum;
    public long linkedChatId;
    public LinkSpanDrawable.LinkCollector links;
    private StaticLayout loadingProgressLayout;
    private long loadingProgressLayoutHash;
    private boolean locationExpired;
    private ImageReceiver locationImageReceiver;
    private Drawable locationLoadingThumb;
    public boolean makeVisibleAfterChange;
    private boolean mediaBackground;
    private CheckBoxBase mediaCheckBox;
    private int mediaOffsetY;
    private SpoilerEffect mediaSpoilerEffect;
    private SpoilerEffect2 mediaSpoilerEffect2;
    private Integer mediaSpoilerEffect2Index;
    private Path mediaSpoilerPath;
    private float[] mediaSpoilerRadii;
    private float mediaSpoilerRevealMaxRadius;
    private float mediaSpoilerRevealProgress;
    private float mediaSpoilerRevealX;
    private float mediaSpoilerRevealY;
    private boolean mediaWasInvisible;
    private MessageObject messageObjectToSet;
    private int miniButtonPressed;
    private int miniButtonState;
    private MotionBackgroundDrawable motionBackgroundDrawable;
    private StaticLayout nameLayout;
    private boolean nameLayoutPressed;
    private Drawable nameLayoutSelector;
    private int nameLayoutSelectorColor;
    private int nameLayoutWidth;
    private float nameOffsetX;
    private boolean nameStatusPressed;
    private Drawable nameStatusSelector;
    private int nameStatusSelectorColor;
    private String nameStatusSlug;
    private int nameWidth;
    private float nameX;
    private float nameY;
    public int namesOffset;
    private boolean needNewVisiblePart;
    public boolean needReplyImage;
    private int noSoundCenterX;
    private final ArrayList oldPollButtons;
    private Paint onceClearPaint;
    private RLottieDrawable onceFire;
    private CaptionContainerView.PeriodDrawable oncePeriod;
    private Paint onceRadialCutPaint;
    private Paint onceRadialPaint;
    private Paint onceRadialStrokePaint;
    private boolean otherPressed;
    private int otherX;
    private int otherY;
    private int overideShouldDrawTimeOnMedia;
    private Runnable overrideInvalidate;
    private AudioVisualizerDrawable overridenAudioVisualizer;
    private long overridenDuration;
    public int parentBoundsBottom;
    public float parentBoundsTop;
    public int parentHeight;
    public float parentViewTopOffset;
    public int parentWidth;
    private StaticLayout performerLayout;
    private int performerX;
    private ImageReceiver photoImage;
    private Path photoImageClipPath;
    private float[] photoImageClipPathRadii;
    private boolean photoImageOutOfBounds;
    private boolean photoNotSet;
    private TLObject photoParentObject;
    private StaticLayout photosCountLayout;
    private int photosCountWidth;
    public boolean pinnedBottom;
    public boolean pinnedTop;
    private float pollAnimationProgress;
    private float pollAnimationProgressTime;
    private AvatarDrawable[] pollAvatarDrawables;
    private ImageReceiver[] pollAvatarImages;
    private boolean[] pollAvatarImagesVisible;
    private final ArrayList pollButtons;
    private CheckBoxBase[] pollCheckBox;
    private Paint pollCheckPaint;
    private Path pollCheckPath;
    private boolean pollClosed;
    private Paint pollCutAvatarPaint;
    private boolean pollHintPressed;
    private int pollHintX;
    private int pollHintY;
    private boolean pollInstantViewTouchesBottom;
    private boolean pollUnvoteInProgress;
    private boolean pollVoteInProgress;
    private int pollVoteInProgressNum;
    private boolean pollVoted;
    private int pressedBlock;
    private int pressedBotButton;
    private MessageObject.TextLayoutBlock pressedCopyCode;
    private boolean pressedEffect;
    private AnimatedEmojiSpan pressedEmoji;
    private LinkSpanDrawable pressedFactCheckLink;
    private LinkSpanDrawable pressedLink;
    private int pressedLinkType;
    private int pressedSideButton;
    private final int[] pressedState;
    private int pressedVoteButton;
    public MessageObject.TextLayoutBlocks prevCaptionLayout;
    private CharacterStyle progressLoadingLink;
    private LoadingDrawable progressLoadingLinkCurrentDrawable;
    private ArrayList progressLoadingLinkDrawables;
    private float psaButtonProgress;
    private boolean psaButtonVisible;
    private int psaHelpX;
    private int psaHelpY;
    private boolean psaHintPressed;
    public Drawable quoteArrow;
    public int quoteArrowColor;
    public Drawable[] quoteDrawable;
    public int[] quoteDrawableColor;
    public QuoteHighlight quoteHighlight;
    public ReplyMessageLine quoteLine;
    private RadialProgress2 radialProgress;
    protected float radialProgressAlpha;
    public final ReactionsLayoutInBubble reactionsLayoutInBubble;
    private boolean reactionsVisible;
    private RectF rect;
    private Path rectPath;
    private StaticLayout repliesLayout;
    private int repliesTextWidth;
    public ButtonBounce replyBounce;
    public float replyBounceX;
    public float replyBounceY;
    public float replyHeight;
    public ImageReceiver replyImageReceiver;
    public ReplyMessageLine replyLine;
    public StaticLayout replyNameLayout;
    private int replyNameOffset;
    private int replyNameWidth;
    private boolean replyPanelIsForward;
    private boolean replyPressed;
    private AnimatedFloat replyPressedFloat;
    public Drawable replyQuoteDrawable;
    public int replyQuoteDrawableColor;
    private Path replyRoundRectPath;
    public Drawable replySelector;
    private boolean replySelectorCanBePressed;
    public int replySelectorColor;
    private boolean replySelectorPressed;
    public float replySelectorRadLeft;
    public float replySelectorRadRight;
    public RectF replySelectorRect;
    public List replySpoilers;
    private final Stack replySpoilersPool;
    public int replyStartX;
    public int replyStartY;
    public CheckBoxBase replyTaskCheckbox;
    private int replyTextHeight;
    public StaticLayout replyTextLayout;
    public int replyTextOffset;
    public boolean replyTextRTL;
    private int replyTextWidth;
    private float replyTouchX;
    private float replyTouchY;
    private Theme.ResourcesProvider resourcesProvider;
    private float roundPlayingDrawableProgress;
    private float roundProgressAlpha;
    float roundSeekbarOutAlpha;
    float roundSeekbarOutProgress;
    int roundSeekbarTouched;
    private float roundToPauseProgress;
    private float roundToPauseProgress2;
    private AnimatedFloat roundVideoPlayPipFloat;
    private RoundVideoPlayingDrawable roundVideoPlayingDrawable;
    private final Path sPath;
    private boolean scheduledInvalidate;
    private Rect scrollRect;
    private SeekBar seekBar;
    private SeekBarAccessibilityDelegate seekBarAccessibilityDelegate;
    private int seekBarTranslateX;
    private SeekBarWaveform seekBarWaveform;
    private int seekBarWaveformTranslateX;
    private int seekBarX;
    private int seekBarY;
    float seekbarRoundX;
    float seekbarRoundY;
    private float selectedBackgroundProgress;
    private Paint selectionOverlayPaint;
    private final Drawable[] selectorDrawable;
    private int selectorDrawableColor;
    private int[] selectorDrawableMaskType;
    private final MaskDrawable[] selectorMaskDrawable;
    private Text sensitiveText;
    private Text sensitiveTextShort;
    private Text sensitiveTextShort2;
    private AnimatorSet shakeAnimation;
    private ChatMessageSharedResources sharedResources;
    public boolean shouldCheckVisibleOnScreen;
    public boolean showTopicSeparator;
    private Path sideButtonPath1;
    private Path sideButtonPath2;
    private float[] sideButtonPathCorners1;
    private float[] sideButtonPathCorners2;
    private boolean sideButtonPressed;
    private boolean sideButtonVisible;
    private ImageReceiver sideImage;
    public float sideMenuAlpha;
    public int sideMenuWidth;
    private int sideNameWidth;
    private float sideStartX;
    private float sideStartY;
    public int signWidth;
    private StaticLayout siteNameLayout;
    private float siteNameLayoutWidth;
    private float siteNameLeft;
    private int siteNameWidth;
    private boolean skipFrameUpdate;
    private float slidingOffsetX;
    private StaticLayout songLayout;
    private int songX;
    private SpoilerEffect spoilerPressed;
    private AtomicReference spoilersPatchedReplyTextLayout;
    private long starsPrice;
    private Text starsPriceText;
    private LinkPath starsPriceTextPath;
    private CornerPathEffect starsPriceTextPathEffect;
    public int starsPriceTopPadding;
    private boolean statusDrawableAnimationInProgress;
    private ValueAnimator statusDrawableAnimator;
    private float statusDrawableProgress;
    private StickerSetLinkIcon stickerSetIcons;
    private int substractBackgroundHeight;
    public SuggestionOffer suggestionOffer;
    private int suggestionOfferTopPadding;
    public int textX;
    public int textY;
    private float timeAlpha;
    private int timeAudioX;
    public StaticLayout timeLayout;
    private boolean timePressed;
    private int timeTextWidth;
    private boolean timeWasInvisible;
    public int timeWidth;
    private int timeWidthAudio;
    public int timeX;
    private TimerParticles timerParticles;
    private AnimatedFloat timerParticlesAlpha;
    private float timerTransitionProgress;
    private ButtonBounce titleLabelBounce;
    private StaticLayout titleLabelLayout;
    private float titleLabelLayoutHeight;
    private float titleLabelLayoutWidth;
    private float titleLabelX;
    private float titleLabelY;
    private StaticLayout titleLayout;
    private int titleLayoutLeft;
    private int titleLayoutWidth;
    private int titleX;
    private float toSeekBarProgress;
    private Runnable todoLongPressRunnable;
    private boolean topNearToSet;
    public TopicSeparator topicSeparator;
    public int topicSeparatorTopPadding;
    private long totalChangeTime;
    private int totalCommentWidth;
    public int totalHeight;
    private int totalVisibleBlocksCount;
    public TranscribeButton transcribeButton;
    private float transcribeX;
    private float transcribeY;
    public final TransitionParams transitionParams;
    public float transitionYOffsetForDrawables;
    private LoadingDrawable translationLoadingDrawable;
    private ArrayList translationLoadingDrawableText;
    private AnimatedFloat translationLoadingFloat;
    private LinkPath translationLoadingPath;
    private float unlockAlpha;
    private StaticLayout unlockLayout;
    private SpoilerEffect unlockSpoilerEffect;
    private Path unlockSpoilerPath;
    private float[] unlockSpoilerRadii;
    private int unlockTextWidth;
    private float unlockX;
    private float unlockY;
    private int unmovedTextX;
    private ArrayList urlPathCache;
    private ArrayList urlPathSelection;
    private boolean useSeekBarWaveform;
    private boolean useTranscribeButton;
    private int viaNameWidth;
    private boolean viaOnly;
    private TypefaceSpan viaSpan1;
    private TypefaceSpan viaSpan2;
    private int viaWidth;
    private boolean vibrateOnPollVote;
    private int videoButtonPressed;
    private int videoButtonX;
    private int videoButtonY;
    VideoForwardDrawable videoForwardDrawable;
    private StaticLayout videoInfoLayout;
    OldVideoPlayerRewinder videoPlayerRewinder;
    private RadialProgress2 videoRadialProgress;
    public float viewTop;
    private StaticLayout viewsLayout;
    private int viewsTextWidth;
    public int visibleHeight;
    private boolean visibleOnScreen;
    public int visibleParent;
    public float visibleParentOffset;
    public float visibleTop;
    private float voteCurrentCircleLength;
    private float voteCurrentProgressTime;
    private long voteLastUpdateTime;
    private float voteRadOffset;
    private boolean voteRisingCircleLength;
    private boolean wasAllChats;
    private boolean wasLayout;
    private boolean wasPinned;
    private boolean wasSending;
    private boolean wasTranscriptionOpen;
    private int widthBeforeNewTimeLine;
    private int widthForButtons;
    private boolean willRemoved;
    private boolean wouldBeInPip;

    private boolean intersect(float f, float f2, float f3, float f4) {
        return f <= f3 ? f2 >= f3 : f <= f4;
    }

    @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
    public /* synthetic */ void didSetImageBitmap(int i, String str, Drawable drawable) {
        ImageReceiver.ImageReceiverDelegate.CC.$default$didSetImageBitmap(this, i, str, drawable);
    }

    protected boolean isWidthAdaptive() {
        return false;
    }

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public /* synthetic */ void onFactorChangeFinished(int i, float f, FactorAnimator factorAnimator) {
        FactorAnimator.Target.CC.$default$onFactorChangeFinished(this, i, f, factorAnimator);
    }

    public RadialProgress2 getRadialProgress() {
        return this.radialProgress;
    }

    public void setEnterTransitionInProgress(boolean z) {
        this.enterTransitionInProgress = z;
        invalidate();
    }

    public ReactionsLayoutInBubble.ReactionButton getReactionButton(ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
        return this.reactionsLayoutInBubble.getReactionButton(visibleReaction);
    }

    public MessageObject getPrimaryMessageObject() {
        MessageObject messageObject = this.currentMessageObject;
        MessageObject messageObjectFindPrimaryMessageObject = (messageObject == null || this.currentMessagesGroup == null || !messageObject.hasValidGroupId()) ? null : this.currentMessagesGroup.findPrimaryMessageObject();
        return messageObjectFindPrimaryMessageObject != null ? messageObjectFindPrimaryMessageObject : this.currentMessageObject;
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        MessageObject messageObject;
        if (i == NotificationCenter.startSpoilers) {
            setSpoilersSuppressed(false);
            return;
        }
        if (i == NotificationCenter.stopSpoilers) {
            setSpoilersSuppressed(true);
            return;
        }
        if (i == NotificationCenter.userInfoDidLoad) {
            TLRPC.User user = this.currentUser;
            if (user != null) {
                if (user.f1734id == ((Long) objArr[0]).longValue()) {
                    setAvatar(this.currentMessageObject);
                    return;
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.emojiLoaded) {
            invalidate();
            return;
        }
        if (i != NotificationCenter.didUpdatePremiumGiftStickers || (messageObject = this.currentMessageObject) == null) {
            return;
        }
        TLRPC.MessageMedia messageMedia = messageObject.messageOwner.media;
        if ((messageMedia instanceof TLRPC.TL_messageMediaGiveaway) || (messageMedia instanceof TLRPC.TL_messageMediaGiveawayResults)) {
            setMessageObject(messageObject, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop, this.firstInChat);
        }
    }

    public void setAvatar(MessageObject messageObject) {
        if (messageObject == null) {
            return;
        }
        if (this.isAvatarVisible) {
            Drawable drawable = messageObject.customAvatarDrawable;
            if (drawable != null) {
                this.avatarImage.setImageBitmap(drawable);
                return;
            }
            TLRPC.User user = this.currentUser;
            if (user != null) {
                TLRPC.UserProfilePhoto userProfilePhoto = user.photo;
                if (userProfilePhoto != null) {
                    this.currentPhoto = userProfilePhoto.photo_small;
                } else {
                    this.currentPhoto = null;
                }
                this.avatarDrawable.setInfo(this.currentAccount, user);
                this.avatarImage.setForUserOrChat(this.currentUser, this.avatarDrawable, null, LiteMode.isEnabled(LiteMode.FLAGS_CHAT), 1, false);
                return;
            }
            TLRPC.Chat chat = this.currentChat;
            if (chat != null) {
                TLRPC.ChatPhoto chatPhoto = chat.photo;
                if (chatPhoto != null) {
                    this.currentPhoto = chatPhoto.photo_small;
                } else {
                    this.currentPhoto = null;
                }
                if (chat.signature_profiles && messageObject.getDialogId() != UserObject.REPLY_BOT) {
                    long peerDialogId = DialogObject.getPeerDialogId(messageObject.messageOwner.from_id);
                    if (peerDialogId >= 0) {
                        TLRPC.User user2 = MessagesController.getInstance(messageObject.currentAccount).getUser(Long.valueOf(peerDialogId));
                        this.avatarDrawable.setInfo(this.currentAccount, user2);
                        this.avatarImage.setForUserOrChat(user2, this.avatarDrawable);
                        return;
                    } else {
                        TLRPC.Chat chat2 = MessagesController.getInstance(messageObject.currentAccount).getChat(Long.valueOf(-peerDialogId));
                        this.avatarDrawable.setInfo(this.currentAccount, chat2);
                        this.avatarImage.setForUserOrChat(chat2, this.avatarDrawable);
                        return;
                    }
                }
                this.avatarDrawable.setInfo(this.currentAccount, this.currentChat);
                this.avatarImage.setForUserOrChat(this.currentChat, this.avatarDrawable);
                return;
            }
            if (messageObject.isSponsored()) {
                TLRPC.Photo photo = messageObject.sponsoredPhoto;
                if (photo != null) {
                    this.avatarImage.setImage(ImageLocation.getForPhoto(FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.m1146dp(50.0f), false, null, true), messageObject.sponsoredPhoto), "50_50", this.avatarDrawable, null, null, 0);
                    return;
                }
                return;
            }
            this.currentPhoto = null;
            this.avatarDrawable.setInfo(messageObject.getFromChatId(), null, null);
            this.avatarImage.setImage(null, null, this.avatarDrawable, null, null, 0);
            return;
        }
        this.currentPhoto = null;
    }

    public void setSpoilersSuppressed(boolean z) {
        for (int i = 0; i < this.replySpoilers.size(); i++) {
            ((SpoilerEffect) this.replySpoilers.get(i)).setSuppressUpdates(z);
        }
        MessageObject.TextLayoutBlocks textLayoutBlocks = this.captionLayout;
        if (textLayoutBlocks != null && textLayoutBlocks.textLayoutBlocks != null) {
            for (int i2 = 0; i2 < this.captionLayout.textLayoutBlocks.size(); i2++) {
                MessageObject.TextLayoutBlock textLayoutBlock = this.captionLayout.textLayoutBlocks.get(i2);
                for (int i3 = 0; i3 < textLayoutBlock.spoilers.size(); i3++) {
                    textLayoutBlock.spoilers.get(i3).setSuppressUpdates(z);
                }
            }
        }
        if (getMessageObject() == null || getMessageObject().textLayoutBlocks == null) {
            return;
        }
        for (int i4 = 0; i4 < getMessageObject().textLayoutBlocks.size(); i4++) {
            MessageObject.TextLayoutBlock textLayoutBlock2 = getMessageObject().textLayoutBlocks.get(i4);
            for (int i5 = 0; i5 < textLayoutBlock2.spoilers.size(); i5++) {
                textLayoutBlock2.spoilers.get(i5).setSuppressUpdates(z);
            }
        }
    }

    public boolean hasSpoilers() {
        ArrayList<MessageObject.TextLayoutBlock> arrayList;
        MessageObject.TextLayoutBlocks textLayoutBlocks = this.captionLayout;
        if (textLayoutBlocks != null && (arrayList = textLayoutBlocks.textLayoutBlocks) != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                MessageObject.TextLayoutBlock textLayoutBlock = arrayList.get(i);
                i++;
                if (!textLayoutBlock.spoilers.isEmpty()) {
                    return true;
                }
            }
        }
        if (getMessageObject() != null && getMessageObject().textLayoutBlocks != null) {
            ArrayList<MessageObject.TextLayoutBlock> arrayList2 = getMessageObject().textLayoutBlocks;
            int size2 = arrayList2.size();
            int i2 = 0;
            while (i2 < size2) {
                MessageObject.TextLayoutBlock textLayoutBlock2 = arrayList2.get(i2);
                i2++;
                if (!textLayoutBlock2.spoilers.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateSpoilersVisiblePart(int i, int i2) {
        ArrayList<MessageObject.TextLayoutBlock> arrayList;
        MessageObject.TextLayoutBlocks textLayoutBlocks = this.captionLayout;
        int i3 = 0;
        if (textLayoutBlocks != null && (arrayList = textLayoutBlocks.textLayoutBlocks) != null) {
            int size = arrayList.size();
            int i4 = 0;
            while (i4 < size) {
                MessageObject.TextLayoutBlock textLayoutBlock = arrayList.get(i4);
                i4++;
                MessageObject.TextLayoutBlock textLayoutBlock2 = textLayoutBlock;
                Iterator<SpoilerEffect> it = textLayoutBlock2.spoilers.iterator();
                while (it.hasNext()) {
                    it.next().setVisibleBounds(0.0f, (i - textLayoutBlock2.textYOffset(this.captionLayout.textLayoutBlocks, this.transitionParams)) - this.captionX, getWidth(), (i2 - textLayoutBlock2.textYOffset(this.captionLayout.textLayoutBlocks, this.transitionParams)) - this.captionY);
                }
            }
        }
        StaticLayout staticLayout = this.replyTextLayout;
        if (staticLayout != null) {
            float height = (-this.replyStartY) - staticLayout.getHeight();
            Iterator it2 = this.replySpoilers.iterator();
            while (it2.hasNext()) {
                ((SpoilerEffect) it2.next()).setVisibleBounds(0.0f, i + height, getWidth(), i2 + height);
            }
        }
        if (getMessageObject() == null || getMessageObject().textLayoutBlocks == null) {
            return;
        }
        ArrayList<MessageObject.TextLayoutBlock> arrayList2 = getMessageObject().textLayoutBlocks;
        int size2 = arrayList2.size();
        while (i3 < size2) {
            MessageObject.TextLayoutBlock textLayoutBlock3 = arrayList2.get(i3);
            i3++;
            MessageObject.TextLayoutBlock textLayoutBlock4 = textLayoutBlock3;
            Iterator<SpoilerEffect> it3 = textLayoutBlock4.spoilers.iterator();
            while (it3.hasNext()) {
                it3.next().setVisibleBounds(0.0f, (i - textLayoutBlock4.textYOffset(getMessageObject().textLayoutBlocks, this.transitionParams)) - this.textY, getWidth(), (i2 - textLayoutBlock4.textYOffset(getMessageObject().textLayoutBlocks, this.transitionParams)) - this.textY);
            }
        }
    }

    public void setScrimReaction(Integer num) {
        this.reactionsLayoutInBubble.setScrimReaction(num);
    }

    public void drawScrimReaction(Canvas canvas, Integer num, float f, boolean z) {
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if (groupedMessagePosition != null) {
            int i = groupedMessagePosition.flags;
            if ((i & 8) == 0 || (i & 1) == 0) {
                return;
            }
        }
        ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
        if (reactionsLayoutInBubble.isSmall) {
            return;
        }
        reactionsLayoutInBubble.setScrimProgress(f, z);
        this.reactionsLayoutInBubble.draw(canvas, this.transitionParams.animateChangeProgress, num);
    }

    public void drawScrimReactionPreview(View view, Canvas canvas, int i, Integer num, float f) {
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if (groupedMessagePosition != null) {
            int i2 = groupedMessagePosition.flags;
            if ((i2 & 8) == 0 || (i2 & 1) == 0) {
                return;
            }
        }
        ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
        if (reactionsLayoutInBubble.isSmall) {
            return;
        }
        reactionsLayoutInBubble.setScrimProgress(f);
        this.reactionsLayoutInBubble.drawPreview(view, canvas, i, num);
    }

    public boolean checkUnreadReactions(float f, int i) {
        if (!this.reactionsLayoutInBubble.hasUnreadReactions) {
            return false;
        }
        float y = getY();
        float f2 = y + r2.f1967y;
        return f2 > f && (f2 + ((float) this.reactionsLayoutInBubble.height)) - ((float) AndroidUtilities.m1146dp(16.0f)) < ((float) i);
    }

    public void markReactionsAsRead() {
        this.reactionsLayoutInBubble.hasUnreadReactions = false;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return;
        }
        messageObject.markReactionsAsRead();
    }

    public void setVisibleOnScreen(boolean z, float f, float f2) {
        if (this.visibleOnScreen != z) {
            this.visibleOnScreen = z;
            checkImageReceiversAttachState();
            if (z) {
                invalidate();
            }
        }
        float imageY = f - this.photoImage.getImageY();
        float measuredHeight = f2 - (getMeasuredHeight() - this.photoImage.getImageY2());
        float imageHeight = this.photoImage.getImageHeight();
        if (imageY > 0.0f) {
            imageHeight -= imageY;
        }
        if (measuredHeight > 0.0f) {
            imageHeight -= measuredHeight;
        }
        ImageReceiver imageReceiver = this.photoImage;
        boolean z2 = imageHeight / imageReceiver.getImageHeight() < 0.25f;
        this.skipFrameUpdate = z2;
        imageReceiver.setSkipUpdateFrame(z2);
    }

    public void setParentBounds(float f, int i) {
        this.parentBoundsTop = f;
        this.parentBoundsBottom = i;
        if (this.photoImageOutOfBounds) {
            float y = getY() + getPaddingTop() + this.photoImage.getImageY();
            if (this.photoImage.getImageHeight() + y < this.parentBoundsTop || y > this.parentBoundsBottom) {
                return;
            }
            invalidate();
        }
    }

    public void setSponsoredMessageVisible(boolean z, boolean z2) {
        this.isSponsoredMessageHidden.setValue(!z, z2);
    }

    @Override // me.vkryl.android.animator.FactorAnimator.Target
    public void onFactorChanged(int i, float f, float f2, FactorAnimator factorAnimator) {
        if (i == 0) {
            invalidate();
            invalidateOutbounds();
        }
    }

    public void setIgnoreDeletedAlpha(boolean z) {
        this.ignoreDeletedAlpha = z;
    }

    public void setForceSemiTransparent(Integer num) {
        this.forceSemiTransparent = num;
    }

    public void startDeletedAlphaAnimation(float f) {
        if (this.ignoreDeletedAlpha) {
            return;
        }
        AnimatorSet animatorSet = this.ayuDeletedAnimation;
        if (animatorSet != null) {
            animatorSet.end();
            this.ayuDeletedAnimation = null;
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.ayuDeletedAnimation = animatorSet2;
        animatorSet2.playTogether(ObjectAnimator.ofFloat(this, (Property<ChatMessageCell, Float>) View.ALPHA, f));
        this.ayuDeletedAnimation.setInterpolator(CubicBezierInterpolator.EASE_OUT);
        this.ayuDeletedAnimation.setDuration(250L);
        this.ayuDeletedAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.ChatMessageCell.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ChatMessageCell.this.ayuDeletedAnimation = null;
            }
        });
        this.ayuDeletedAnimation.start();
    }

    /* loaded from: classes5.dex */
    public interface ChatMessageCellDelegate {
        boolean canDrawOutboundsContent();

        boolean canPerformActions();

        boolean canPerformReply();

        void didLongPress(ChatMessageCell chatMessageCell, float f, float f2);

        void didLongPressBotButton(ChatMessageCell chatMessageCell, TLRPC.KeyboardButton keyboardButton);

        boolean didLongPressChannelAvatar(ChatMessageCell chatMessageCell, TLRPC.Chat chat, int i, float f, float f2);

        void didLongPressCustomBotButton(ChatMessageCell chatMessageCell, BotInlineKeyboard.ButtonCustom buttonCustom);

        boolean didLongPressToDoButton(ChatMessageCell chatMessageCell, TLRPC.TodoItem todoItem);

        boolean didLongPressUserAvatar(ChatMessageCell chatMessageCell, TLRPC.User user, float f, float f2);

        void didPressAboutRevenueSharingAds();

        boolean didPressAnimatedEmoji(ChatMessageCell chatMessageCell, AnimatedEmojiSpan animatedEmojiSpan);

        void didPressBoostCounter(ChatMessageCell chatMessageCell);

        void didPressBotButton(ChatMessageCell chatMessageCell, TLRPC.KeyboardButton keyboardButton);

        void didPressCancelSendButton(ChatMessageCell chatMessageCell);

        void didPressChannelAvatar(ChatMessageCell chatMessageCell, TLRPC.Chat chat, int i, float f, float f2, boolean z);

        void didPressChannelRecommendation(ChatMessageCell chatMessageCell, TLObject tLObject, boolean z);

        void didPressChannelRecommendationsClose(ChatMessageCell chatMessageCell);

        void didPressCodeCopy(ChatMessageCell chatMessageCell, MessageObject.TextLayoutBlock textLayoutBlock);

        void didPressCommentButton(ChatMessageCell chatMessageCell);

        void didPressCustomBotButton(ChatMessageCell chatMessageCell, BotInlineKeyboard.ButtonCustom buttonCustom);

        void didPressEffect(ChatMessageCell chatMessageCell);

        void didPressExtendedMediaPreview(ChatMessageCell chatMessageCell, TLRPC.KeyboardButton keyboardButton);

        void didPressFactCheck(ChatMessageCell chatMessageCell);

        void didPressFactCheckWhat(ChatMessageCell chatMessageCell, int i, int i2);

        void didPressGiveawayChatButton(ChatMessageCell chatMessageCell, int i);

        void didPressGroupImage(ChatMessageCell chatMessageCell, ImageReceiver imageReceiver, TLRPC.MessageExtendedMedia messageExtendedMedia, float f, float f2);

        void didPressHiddenForward(ChatMessageCell chatMessageCell);

        void didPressHint(ChatMessageCell chatMessageCell, int i);

        void didPressImage(ChatMessageCell chatMessageCell, float f, float f2, boolean z);

        void didPressInstantButton(ChatMessageCell chatMessageCell, int i);

        void didPressMoreChannelRecommendations(ChatMessageCell chatMessageCell);

        void didPressOther(ChatMessageCell chatMessageCell, float f, float f2);

        void didPressReaction(ChatMessageCell chatMessageCell, TLRPC.ReactionCount reactionCount, boolean z, float f, float f2);

        void didPressReplyMessage(ChatMessageCell chatMessageCell, int i, float f, float f2, boolean z);

        void didPressRevealSensitiveContent(ChatMessageCell chatMessageCell);

        void didPressSideButton(ChatMessageCell chatMessageCell);

        void didPressSponsoredClose(ChatMessageCell chatMessageCell);

        void didPressSponsoredInfo(ChatMessageCell chatMessageCell, float f, float f2);

        void didPressTime(ChatMessageCell chatMessageCell);

        boolean didPressToDoButton(ChatMessageCell chatMessageCell, TLRPC.TodoItem todoItem, boolean z);

        void didPressUrl(ChatMessageCell chatMessageCell, CharacterStyle characterStyle, boolean z);

        void didPressUserAvatar(ChatMessageCell chatMessageCell, TLRPC.User user, float f, float f2, boolean z);

        void didPressUserStatus(ChatMessageCell chatMessageCell, TLRPC.User user, TLRPC.Document document, String str);

        void didPressViaBot(ChatMessageCell chatMessageCell, String str);

        void didPressViaBotNotInline(ChatMessageCell chatMessageCell, long j);

        void didPressVoteButtons(ChatMessageCell chatMessageCell, ArrayList arrayList, int i, int i2, int i3);

        void didPressWebPage(ChatMessageCell chatMessageCell, TLRPC.WebPage webPage, String str, boolean z);

        void didQuickShareEnd(ChatMessageCell chatMessageCell, float f, float f2);

        void didQuickShareMove(ChatMessageCell chatMessageCell, float f, float f2);

        void didQuickShareStart(ChatMessageCell chatMessageCell, float f, float f2);

        void didStartVideoStream(MessageObject messageObject);

        boolean doNotShowLoadingReply(MessageObject messageObject);

        void forceUpdate(ChatMessageCell chatMessageCell, boolean z);

        String getAdminRank(long j);

        PinchToZoomHelper getPinchToZoomHelper();

        String getProgressLoadingBotButtonUrl(ChatMessageCell chatMessageCell);

        CharacterStyle getProgressLoadingLink(ChatMessageCell chatMessageCell);

        TextSelectionHelper.ChatListTextSelectionHelper getTextSelectionHelper();

        boolean hasSelectedMessages();

        void invalidateBlur();

        boolean isLandscape();

        boolean isProgressLoading(ChatMessageCell chatMessageCell, int i);

        boolean isReplyOrSelf();

        boolean keyboardIsOpened();

        void needOpenWebView(MessageObject messageObject, String str, String str2, String str3, String str4, int i, int i2);

        boolean needPlayMessage(ChatMessageCell chatMessageCell, MessageObject messageObject, boolean z);

        void needReloadPolls();

        void needShowPremiumBulletin(int i);

        boolean onAccessibilityAction(int i, Bundle bundle);

        void onDiceFinished();

        void setShouldNotRepeatSticker(MessageObject messageObject);

        boolean shouldDrawThreadProgress(ChatMessageCell chatMessageCell, boolean z);

        boolean shouldRepeatSticker(MessageObject messageObject);

        void videoTimerReached();

        /* renamed from: org.telegram.ui.Cells.ChatMessageCell$ChatMessageCellDelegate$-CC, reason: invalid class name */
        public abstract /* synthetic */ class CC {
            public static boolean $default$isReplyOrSelf(ChatMessageCellDelegate chatMessageCellDelegate) {
                return false;
            }

            public static void $default$didPressExtendedMediaPreview(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC.KeyboardButton keyboardButton) {
            }

            public static void $default$didPressUserStatus(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC.User user, TLRPC.Document document, String str) {
            }

            public static void $default$didPressUserAvatar(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC.User user, float f, float f2, boolean z) {
            }

            public static boolean $default$didLongPressUserAvatar(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC.User user, float f, float f2) {
                return false;
            }

            public static void $default$didPressHiddenForward(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressViaBotNotInline(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, long j) {
            }

            public static void $default$didPressViaBot(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, String str) {
            }

            public static void $default$didPressBoostCounter(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressChannelAvatar(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC.Chat chat, int i, float f, float f2, boolean z) {
            }

            public static boolean $default$didLongPressChannelAvatar(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC.Chat chat, int i, float f, float f2) {
                return false;
            }

            public static void $default$didPressCancelSendButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didLongPress(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, float f, float f2) {
            }

            public static void $default$didPressReplyMessage(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, int i, float f, float f2, boolean z) {
            }

            public static boolean $default$isProgressLoading(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, int i) {
                return false;
            }

            public static String $default$getProgressLoadingBotButtonUrl(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
                return null;
            }

            public static CharacterStyle $default$getProgressLoadingLink(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
                return null;
            }

            public static void $default$didPressUrl(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, CharacterStyle characterStyle, boolean z) {
            }

            public static void $default$didPressCodeCopy(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, MessageObject.TextLayoutBlock textLayoutBlock) {
            }

            public static void $default$didPressChannelRecommendation(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLObject tLObject, boolean z) {
            }

            public static void $default$didPressMoreChannelRecommendations(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressChannelRecommendationsClose(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$needOpenWebView(ChatMessageCellDelegate chatMessageCellDelegate, MessageObject messageObject, String str, String str2, String str3, String str4, int i, int i2) {
            }

            public static void $default$didPressImage(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, float f, float f2, boolean z) {
            }

            public static void $default$didPressGroupImage(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, ImageReceiver imageReceiver, TLRPC.MessageExtendedMedia messageExtendedMedia, float f, float f2) {
            }

            public static void $default$didPressSideButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didQuickShareStart(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, float f, float f2) {
            }

            public static void $default$didQuickShareMove(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, float f, float f2) {
            }

            public static void $default$didQuickShareEnd(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, float f, float f2) {
            }

            public static void $default$didPressOther(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, float f, float f2) {
            }

            public static void $default$didPressSponsoredClose(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressSponsoredInfo(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, float f, float f2) {
            }

            public static void $default$didPressTime(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressBotButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC.KeyboardButton keyboardButton) {
            }

            public static void $default$didLongPressBotButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC.KeyboardButton keyboardButton) {
            }

            public static void $default$didPressCustomBotButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, BotInlineKeyboard.ButtonCustom buttonCustom) {
            }

            public static void $default$didLongPressCustomBotButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, BotInlineKeyboard.ButtonCustom buttonCustom) {
            }

            public static void $default$didPressReaction(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC.ReactionCount reactionCount, boolean z, float f, float f2) {
            }

            public static void $default$didPressVoteButtons(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, ArrayList arrayList, int i, int i2, int i3) {
            }

            public static boolean $default$didPressToDoButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC.TodoItem todoItem, boolean z) {
                return false;
            }

            public static boolean $default$didLongPressToDoButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, TLRPC.TodoItem todoItem) {
                return false;
            }

            public static void $default$didPressInstantButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, int i) {
            }

            public static void $default$didPressGiveawayChatButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, int i) {
            }

            public static void $default$didPressCommentButton(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressHint(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, int i) {
            }

            public static void $default$needShowPremiumBulletin(ChatMessageCellDelegate chatMessageCellDelegate, int i) {
            }

            public static String $default$getAdminRank(ChatMessageCellDelegate chatMessageCellDelegate, long j) {
                return null;
            }

            public static boolean $default$needPlayMessage(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, MessageObject messageObject, boolean z) {
                return false;
            }

            public static boolean $default$canPerformActions(ChatMessageCellDelegate chatMessageCellDelegate) {
                return false;
            }

            public static boolean $default$onAccessibilityAction(ChatMessageCellDelegate chatMessageCellDelegate, int i, Bundle bundle) {
                return false;
            }

            public static void $default$videoTimerReached(ChatMessageCellDelegate chatMessageCellDelegate) {
            }

            public static void $default$didStartVideoStream(ChatMessageCellDelegate chatMessageCellDelegate, MessageObject messageObject) {
            }

            public static boolean $default$shouldRepeatSticker(ChatMessageCellDelegate chatMessageCellDelegate, MessageObject messageObject) {
                return true;
            }

            public static void $default$setShouldNotRepeatSticker(ChatMessageCellDelegate chatMessageCellDelegate, MessageObject messageObject) {
            }

            public static TextSelectionHelper.ChatListTextSelectionHelper $default$getTextSelectionHelper(ChatMessageCellDelegate chatMessageCellDelegate) {
                return null;
            }

            public static boolean $default$hasSelectedMessages(ChatMessageCellDelegate chatMessageCellDelegate) {
                return false;
            }

            public static void $default$needReloadPolls(ChatMessageCellDelegate chatMessageCellDelegate) {
            }

            public static void $default$onDiceFinished(ChatMessageCellDelegate chatMessageCellDelegate) {
            }

            public static boolean $default$shouldDrawThreadProgress(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, boolean z) {
                return false;
            }

            public static PinchToZoomHelper $default$getPinchToZoomHelper(ChatMessageCellDelegate chatMessageCellDelegate) {
                return null;
            }

            public static boolean $default$keyboardIsOpened(ChatMessageCellDelegate chatMessageCellDelegate) {
                return false;
            }

            public static boolean $default$isLandscape(ChatMessageCellDelegate chatMessageCellDelegate) {
                return false;
            }

            public static void $default$invalidateBlur(ChatMessageCellDelegate chatMessageCellDelegate) {
            }

            public static boolean $default$canDrawOutboundsContent(ChatMessageCellDelegate chatMessageCellDelegate) {
                return true;
            }

            public static boolean $default$didPressAnimatedEmoji(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, AnimatedEmojiSpan animatedEmojiSpan) {
                return false;
            }

            public static boolean $default$doNotShowLoadingReply(ChatMessageCellDelegate chatMessageCellDelegate, MessageObject messageObject) {
                return messageObject != null && messageObject.getDialogId() == UserObject.REPLY_BOT;
            }

            public static void $default$didPressAboutRevenueSharingAds(ChatMessageCellDelegate chatMessageCellDelegate) {
            }

            public static void $default$didPressRevealSensitiveContent(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressEffect(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$didPressFactCheckWhat(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, int i, int i2) {
            }

            public static void $default$didPressFactCheck(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell) {
            }

            public static void $default$forceUpdate(ChatMessageCellDelegate chatMessageCellDelegate, ChatMessageCell chatMessageCell, boolean z) {
            }
        }
    }

    public class PollButton {
        public int animateHeight;
        private StaticLayout animateTitle;
        private AnimatedEmojiSpan.EmojiGroupedSpans animateTitleEmoji;
        public int animateY;
        public AnimatedEmojiSpan.EmojiGroupedSpans animatedEmoji;
        private TLRPC.PollAnswer answer;
        private Text author;
        private AvatarDrawable avatarDrawable;
        private ImageReceiver avatarImageReceiver;
        private boolean chosen;
        private boolean correct;
        private int count;
        private float decimal;
        public int height;
        private int percent;
        private float percentProgress;
        private boolean prevChosen;
        private int prevPercent;
        private float prevPercentProgress;
        public Drawable selectorDrawable;
        public int selectorDrawableColor;
        private TLRPC.TodoItem task;
        public StaticLayout title;
        public float titleX;
        public float titleY;
        private boolean translated;

        /* renamed from: x */
        public int f1805x;

        /* renamed from: y */
        public int f1806y;

        public PollButton() {
        }

        public void attach() {
            ImageReceiver imageReceiver = this.avatarImageReceiver;
            if (imageReceiver != null) {
                imageReceiver.onAttachedToWindow();
            }
        }

        public void detach() {
            ImageReceiver imageReceiver = this.avatarImageReceiver;
            if (imageReceiver != null) {
                imageReceiver.onDetachedFromWindow();
            }
        }

        public void destroy() {
            detach();
            AnimatedEmojiSpan.release(ChatMessageCell.this, this.animatedEmoji);
            this.animatedEmoji = null;
            AnimatedEmojiSpan.release(ChatMessageCell.this, this.animateTitleEmoji);
            this.animateTitleEmoji = null;
        }
    }

    private static class InstantViewButton {
        private ButtonBounce buttonBounce;
        private float buttonWidth;
        private StaticLayout layout;
        private final RectF rect;
        private Drawable selectorDrawable;
        private float textX;
        private int type;

        private InstantViewButton() {
            this.rect = new RectF();
        }
    }

    public boolean isCellAttachedToWindow() {
        return this.attachedToWindow;
    }

    public float getLastTouchX() {
        return this.lastTouchX;
    }

    public float getLastTouchY() {
        return this.lastTouchY;
    }

    /* loaded from: classes5.dex */
    class LoadingDrawableLocation {
        int blockNum;
        LoadingDrawable drawable;

        LoadingDrawableLocation() {
        }
    }

    public ChatMessageCell(Context context, int i) {
        this(context, i, false, null, null);
    }

    public ChatMessageCell(Context context, int i, boolean z, ChatMessageSharedResources chatMessageSharedResources, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.visibleOnScreen = true;
        this.isSponsoredMessageHidden = new BoolAnimator(0, this, CubicBezierInterpolator.EASE_OUT_QUINT, 380L);
        this.reactionsLayoutInBubble = new ReactionsLayoutInBubble(this);
        this.giveawayMessageCell = new GiveawayMessageCell(this);
        this.giveawayResultsMessageCell = new GiveawayResultsMessageCell(this);
        this.scrollRect = new Rect();
        this.drawnContactButtonsFlag = 0;
        this.imageBackgroundGradientRotation = 45;
        this.selectorDrawable = new Drawable[2];
        this.selectorMaskDrawable = new MaskDrawable[2];
        this.selectorDrawableMaskType = new int[2];
        this.instantButtonRect = new RectF();
        this.pressedState = new int[]{R.attr.state_enabled, R.attr.state_pressed};
        this.highlightCaptionToSetStart = -1;
        this.highlightCaptionToSetEnd = -1;
        this.deleteProgressRect = new RectF();
        this.rect = new RectF();
        this.foreverDrawableColor = -1;
        this.timeAlpha = 1.0f;
        this.actionAlpha = 1.0f;
        this.controlsAlpha = 1.0f;
        this.pressedBlock = -1;
        this.links = new LinkSpanDrawable.LinkCollector(this);
        this.urlPathCache = new ArrayList();
        this.urlPathSelection = new ArrayList();
        this.rectPath = new Path();
        this.oldPollButtons = new ArrayList();
        this.pollButtons = new ArrayList();
        this.reactionsVisible = true;
        this.botButtons = new ArrayList();
        this.botButtonPath = new Path();
        this.botButtonRadii = new float[8];
        this.botButtonsByData = new HashMap();
        this.botButtonsByPosition = new HashMap();
        this.doNotDrawTaskId = -1;
        this.isCheckPressed = true;
        this.drawBackground = true;
        this.backgroundWidth = 100;
        this.commentButtonRect = new Rect();
        this.spoilersPatchedReplyTextLayout = new AtomicReference();
        this.forwardedNameLayout = new StaticLayout[2];
        this.forwardNameOffsetX = new float[2];
        this.drawTime = true;
        this.mediaSpoilerPath = new Path();
        this.mediaSpoilerRadii = new float[8];
        this.unlockAlpha = 1.0f;
        this.unlockSpoilerPath = new Path();
        this.unlockSpoilerRadii = new float[8];
        this.replySelectorRect = new RectF();
        this.ALPHA_PROPERTY_WORKAROUND = Build.VERSION.SDK_INT == 28;
        this.alphaInternal = 1.0f;
        this.transitionParams = new TransitionParams();
        this.roundVideoPlayPipFloat = new AnimatedFloat(this, 200L, CubicBezierInterpolator.EASE_OUT);
        this.diceFinishCallback = new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell.2
            @Override // java.lang.Runnable
            public void run() {
                if (ChatMessageCell.this.delegate != null) {
                    ChatMessageCell.this.delegate.onDiceFinished();
                }
            }
        };
        this.invalidateRunnable = new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell.3
            @Override // java.lang.Runnable
            public void run() {
                ChatMessageCell.this.checkLocationExpired();
                if (ChatMessageCell.this.locationExpired) {
                    ChatMessageCell.this.invalidate();
                    ChatMessageCell.this.scheduledInvalidate = false;
                    return;
                }
                ChatMessageCell.this.invalidate(((int) r0.rect.left) - 5, ((int) ChatMessageCell.this.rect.top) - 5, ((int) ChatMessageCell.this.rect.right) + 5, ((int) ChatMessageCell.this.rect.bottom) + 5);
                if (ChatMessageCell.this.scheduledInvalidate) {
                    AndroidUtilities.runOnUIThread(ChatMessageCell.this.invalidateRunnable, 1000L);
                }
            }
        };
        this.accessibilityVirtualViewBounds = new SparseArray();
        this.currentFocusedVirtualView = -1;
        this.backgroundCacheParams = new Theme.MessageDrawable.PathDrawParams();
        this.replySpoilers = new ArrayList();
        this.replySpoilersPool = new Stack();
        this.sPath = new Path();
        this.pressedEffect = false;
        this.overridenDuration = -1L;
        this.hadLongPress = false;
        this.invalidateOutboundsRunnable = new ChatMessageCell$$ExternalSyntheticLambda5(this);
        this.showTopicSeparator = true;
        this.radialProgressAlpha = 1.0f;
        this.ANIMATION_OFFSET_X = new Property(Float.class, "animationOffsetX") { // from class: org.telegram.ui.Cells.ChatMessageCell.11
            @Override // android.util.Property
            public Float get(ChatMessageCell chatMessageCell) {
                return Float.valueOf(chatMessageCell.animationOffsetX);
            }

            @Override // android.util.Property
            public void set(ChatMessageCell chatMessageCell, Float f) {
                chatMessageCell.setAnimationOffsetX(f.floatValue());
            }
        };
        this.currentAccount = i;
        this.resourcesProvider = resourcesProvider;
        this.canDrawBackgroundInParent = z;
        this.sharedResources = chatMessageSharedResources;
        if (chatMessageSharedResources == null) {
            this.sharedResources = new ChatMessageSharedResources(context);
        }
        setClipChildren(false);
        setClipToPadding(false);
        this.backgroundDrawable = new MessageBackgroundDrawable(this);
        ImageReceiver imageReceiver = new ImageReceiver();
        this.avatarImage = imageReceiver;
        imageReceiver.setAllowLoadingOnAttachedOnly(true);
        this.avatarImage.setRoundRadius(ExteraConfig.getAvatarCorners(42.0f));
        this.avatarDrawable = new AvatarDrawable();
        ImageReceiver imageReceiver2 = new ImageReceiver(this);
        this.replyImageReceiver = imageReceiver2;
        imageReceiver2.setAllowLoadingOnAttachedOnly(true);
        this.replyImageReceiver.setRoundRadius(AndroidUtilities.m1146dp(4.0f));
        ImageReceiver imageReceiver3 = new ImageReceiver(this);
        this.locationImageReceiver = imageReceiver3;
        imageReceiver3.setAllowLoadingOnAttachedOnly(true);
        this.locationImageReceiver.setRoundRadius(AndroidUtilities.m1146dp(26.1f));
        this.TAG = DownloadController.getInstance(i).generateObserverTag();
        this.contactAvatarDrawable = new AvatarDrawable();
        ImageReceiver imageReceiver4 = new ImageReceiver(this);
        this.photoImage = imageReceiver4;
        imageReceiver4.setAllowLoadingOnAttachedOnly(true);
        this.photoImage.setUseRoundForThumbDrawable(true);
        this.photoImage.setDelegate(this);
        ImageReceiver imageReceiver5 = new ImageReceiver(this);
        this.blurredPhotoImage = imageReceiver5;
        imageReceiver5.setAllowLoadingOnAttachedOnly(true);
        this.blurredPhotoImage.setUseRoundForThumbDrawable(true);
        this.radialProgress = new RadialProgress2(this, resourcesProvider);
        RadialProgress2 radialProgress2 = new RadialProgress2(this, resourcesProvider);
        this.videoRadialProgress = radialProgress2;
        radialProgress2.setDrawBackground(false);
        this.videoRadialProgress.setCircleRadius(AndroidUtilities.m1146dp(15.0f));
        SeekBar seekBar = new SeekBar(this) { // from class: org.telegram.ui.Cells.ChatMessageCell.4
            @Override // org.telegram.p023ui.Components.SeekBar
            protected void onTimestampUpdate(URLSpanNoUnderline uRLSpanNoUnderline) {
                ChatMessageCell.this.setHighlightedSpan(uRLSpanNoUnderline);
            }
        };
        this.seekBar = seekBar;
        seekBar.setDelegate(this);
        SeekBarWaveform seekBarWaveform = new SeekBarWaveform(context);
        this.seekBarWaveform = seekBarWaveform;
        seekBarWaveform.setDelegate(this);
        this.seekBarWaveform.setParentView(this);
        this.seekBarAccessibilityDelegate = new FloatSeekBarAccessibilityDelegate() { // from class: org.telegram.ui.Cells.ChatMessageCell.5
            @Override // org.telegram.p023ui.Components.FloatSeekBarAccessibilityDelegate
            public float getProgress() {
                if (ChatMessageCell.this.currentMessageObject.isMusic()) {
                    return ChatMessageCell.this.seekBar.getProgress();
                }
                if (ChatMessageCell.this.currentMessageObject.isVoice()) {
                    if (ChatMessageCell.this.useSeekBarWaveform) {
                        return ChatMessageCell.this.seekBarWaveform.getProgress();
                    }
                    return ChatMessageCell.this.seekBar.getProgress();
                }
                if (ChatMessageCell.this.currentMessageObject.isRoundVideo()) {
                    return ChatMessageCell.this.currentMessageObject.audioProgress;
                }
                return 0.0f;
            }

            @Override // org.telegram.p023ui.Components.FloatSeekBarAccessibilityDelegate
            public void setProgress(float f) {
                if (ChatMessageCell.this.currentMessageObject.isMusic()) {
                    ChatMessageCell.this.seekBar.setProgress(f);
                } else if (ChatMessageCell.this.currentMessageObject.isVoice()) {
                    if (ChatMessageCell.this.useSeekBarWaveform) {
                        ChatMessageCell.this.seekBarWaveform.setProgress(f);
                    } else {
                        ChatMessageCell.this.seekBar.setProgress(f);
                    }
                } else {
                    if (!ChatMessageCell.this.currentMessageObject.isRoundVideo()) {
                        return;
                    }
                    if (ChatMessageCell.this.useSeekBarWaveform) {
                        if (ChatMessageCell.this.seekBarWaveform != null) {
                            ChatMessageCell.this.seekBarWaveform.setProgress(f);
                        }
                    } else if (ChatMessageCell.this.seekBar != null) {
                        ChatMessageCell.this.seekBar.setProgress(f);
                    }
                    ChatMessageCell.this.currentMessageObject.audioProgress = f;
                }
                ChatMessageCell.this.onSeekBarDrag(f);
                ChatMessageCell.this.invalidate();
            }
        };
        this.roundVideoPlayingDrawable = new RoundVideoPlayingDrawable(this, resourcesProvider);
        setImportantForAccessibility(1);
    }

    public void setResourcesProvider(Theme.ResourcesProvider resourcesProvider) {
        this.resourcesProvider = resourcesProvider;
        RadialProgress2 radialProgress2 = this.radialProgress;
        if (radialProgress2 != null) {
            radialProgress2.setResourcesProvider(resourcesProvider);
        }
        RadialProgress2 radialProgress22 = this.videoRadialProgress;
        if (radialProgress22 != null) {
            radialProgress22.setResourcesProvider(resourcesProvider);
        }
        RoundVideoPlayingDrawable roundVideoPlayingDrawable = this.roundVideoPlayingDrawable;
        if (roundVideoPlayingDrawable != null) {
            roundVideoPlayingDrawable.setResourcesProvider(resourcesProvider);
        }
    }

    public Theme.ResourcesProvider getResourcesProvider() {
        return this.resourcesProvider;
    }

    private void createPollUI(int i) {
        CheckBoxBase[] checkBoxBaseArr = this.pollCheckBox;
        int i2 = 0;
        if (checkBoxBaseArr == null || checkBoxBaseArr.length != i) {
            if (checkBoxBaseArr != null) {
                int i3 = 0;
                while (true) {
                    CheckBoxBase[] checkBoxBaseArr2 = this.pollCheckBox;
                    if (i3 >= checkBoxBaseArr2.length) {
                        break;
                    }
                    checkBoxBaseArr2[i3].onDetachedFromWindow();
                    i3++;
                }
            }
            this.pollCheckBox = new CheckBoxBase[i];
            int i4 = 0;
            while (true) {
                CheckBoxBase[] checkBoxBaseArr3 = this.pollCheckBox;
                if (i4 >= checkBoxBaseArr3.length) {
                    break;
                }
                checkBoxBaseArr3[i4] = new CheckBoxBase(this, 20, this.resourcesProvider);
                this.pollCheckBox[i4].setDrawUnchecked(false);
                this.pollCheckBox[i4].setBackgroundType(9);
                i4++;
            }
        }
        if (this.pollAvatarImages != null) {
            return;
        }
        this.pollAvatarImages = new ImageReceiver[3];
        this.pollAvatarDrawables = new AvatarDrawable[3];
        this.pollAvatarImagesVisible = new boolean[3];
        while (true) {
            ImageReceiver[] imageReceiverArr = this.pollAvatarImages;
            if (i2 >= imageReceiverArr.length) {
                return;
            }
            imageReceiverArr[i2] = new ImageReceiver(this);
            this.pollAvatarImages[i2].setRoundRadius(ExteraConfig.getAvatarCorners(16.0f));
            this.pollAvatarDrawables[i2] = new AvatarDrawable();
            this.pollAvatarDrawables[i2].setTextSize(AndroidUtilities.m1146dp(22.0f));
            i2++;
        }
    }

    private void createCommentUI() {
        if (this.commentAvatarImages != null) {
            return;
        }
        this.commentAvatarImages = new ImageReceiver[3];
        this.commentAvatarDrawables = new AvatarDrawable[3];
        this.commentAvatarImagesVisible = new boolean[3];
        int i = 0;
        while (true) {
            ImageReceiver[] imageReceiverArr = this.commentAvatarImages;
            if (i >= imageReceiverArr.length) {
                return;
            }
            imageReceiverArr[i] = new ImageReceiver(this);
            this.commentAvatarImages[i].setRoundRadius(ExteraConfig.getAvatarCorners(24.0f));
            this.commentAvatarDrawables[i] = new AvatarDrawable();
            this.commentAvatarDrawables[i].setTextSize(AndroidUtilities.m1146dp(18.0f));
            i++;
        }
    }

    public void resetPressedLink(int i) {
        if (i != -1) {
            this.links.removeLinks(Integer.valueOf(i));
        } else {
            this.links.clear();
        }
        LinkSpanDrawable.LinkCollector linkCollector = this.factCheckLinks;
        if (linkCollector != null) {
            linkCollector.clear();
        }
        this.pressedEmoji = null;
        this.pressedFactCheckLink = null;
        if (this.pressedLink != null) {
            if (this.pressedLinkType == i || i == -1) {
                this.pressedLink = null;
                this.pressedLinkType = -1;
                invalidate();
            }
        }
    }

    private void resetUrlPaths() {
        if (this.quoteHighlight != null) {
            this.quoteHighlight = null;
        }
        if (this.urlPathSelection.isEmpty()) {
            return;
        }
        this.urlPathCache.addAll(this.urlPathSelection);
        this.urlPathSelection.clear();
    }

    private LinkPath obtainNewUrlPath() {
        LinkPath linkPath;
        if (!this.urlPathCache.isEmpty()) {
            linkPath = (LinkPath) this.urlPathCache.get(0);
            this.urlPathCache.remove(0);
        } else {
            linkPath = new LinkPath(true);
        }
        linkPath.reset();
        this.urlPathSelection.add(linkPath);
        return linkPath;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int[] getRealSpanStartAndEnd(Spannable spannable, CharacterStyle characterStyle) {
        int spanStart;
        int spanEnd;
        boolean z;
        TextStyleSpan.TextStyleRun style;
        TLRPC.MessageEntity messageEntity;
        if (!(characterStyle instanceof URLSpanBrowser) || (style = ((URLSpanBrowser) characterStyle).getStyle()) == null || (messageEntity = style.urlEntity) == null) {
            spanStart = 0;
            spanEnd = 0;
            z = false;
        } else {
            spanStart = messageEntity.offset;
            spanEnd = messageEntity.length + spanStart;
            z = true;
        }
        if (!z) {
            spanStart = spannable.getSpanStart(characterStyle);
            spanEnd = spannable.getSpanEnd(characterStyle);
        }
        return new int[]{spanStart, spanEnd};
    }

    private boolean checkQuickShareMotionEvent(MotionEvent motionEvent) {
        ChatMessageCellDelegate chatMessageCellDelegate;
        if (!this.inQuickShareMode) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 1 && action != 3) {
            if (action == 2 && (chatMessageCellDelegate = this.delegate) != null) {
                chatMessageCellDelegate.didQuickShareMove(this, getEventX(motionEvent), getEventY(motionEvent));
            }
            return true;
        }
        this.inQuickShareMode = false;
        ChatMessageCellDelegate chatMessageCellDelegate2 = this.delegate;
        if (chatMessageCellDelegate2 != null) {
            chatMessageCellDelegate2.didQuickShareEnd(this, getEventX(motionEvent), getEventY(motionEvent));
        }
        return true;
    }

    private boolean checkAdminMotionEvent(MotionEvent motionEvent) {
        RectF rectF;
        ChatMessageCellDelegate chatMessageCellDelegate;
        Drawable drawable;
        boolean z = false;
        if (this.adminLayout == null || (rectF = this.boostCounterBounds) == null || (this.currentUser == null && this.currentChat == null)) {
            this.boostCounterPressed = false;
            return false;
        }
        boolean zContains = rectF.contains((int) getEventX(motionEvent), (int) getEventY(motionEvent));
        if (motionEvent.getAction() == 0) {
            SpannableString spannableString = new SpannableString(this.adminLayout.getText());
            BoostCounterSpan[] boostCounterSpanArr = (BoostCounterSpan[]) spannableString.getSpans(0, spannableString.length(), BoostCounterSpan.class);
            if (zContains && boostCounterSpanArr != null && boostCounterSpanArr.length > 0) {
                z = true;
            }
            this.boostCounterPressed = z;
            if (z && (drawable = this.boostCounterLayoutSelector) != null) {
                drawable.setHotspot((int) getEventX(motionEvent), (int) getEventY(motionEvent));
                this.boostCounterLayoutSelector.setState(this.pressedState);
            }
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (motionEvent.getAction() == 1 && this.boostCounterPressed && (chatMessageCellDelegate = this.delegate) != null) {
                chatMessageCellDelegate.didPressBoostCounter(this);
            }
            Drawable drawable2 = this.boostCounterLayoutSelector;
            if (drawable2 != null) {
                drawable2.setState(StateSet.NOTHING);
            }
            this.boostCounterPressed = false;
        }
        return this.boostCounterPressed;
    }

    private boolean checkNameMotionEvent(MotionEvent motionEvent) {
        Drawable drawable;
        ChatMessageCell chatMessageCell;
        ChatMessageCellDelegate chatMessageCellDelegate;
        TLRPC.Chat chat;
        int i;
        int i2;
        if (!this.drawNameLayout || this.nameLayout == null || (drawable = this.nameLayoutSelector) == null || (this.currentUser == null && this.currentChat == null)) {
            this.nameLayoutPressed = false;
            return false;
        }
        boolean zContains = drawable.getBounds().contains((int) getEventX(motionEvent), (int) getEventY(motionEvent));
        if (motionEvent.getAction() == 0) {
            this.nameLayoutPressed = zContains;
            if (zContains) {
                this.nameLayoutSelector.setHotspot((int) getEventX(motionEvent), (int) getEventY(motionEvent));
                this.nameLayoutSelector.setState(this.pressedState);
            }
        } else {
            if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                if (motionEvent.getAction() == 1 && this.nameLayoutPressed && (chatMessageCellDelegate = this.delegate) != null) {
                    if (this.viaOnly) {
                        TLRPC.User user = this.currentViaBotUser;
                        if (user != null && user.bot_inline_placeholder == null) {
                            chatMessageCellDelegate.didPressViaBotNotInline(this, user != null ? user.f1734id : 0L);
                        } else {
                            chatMessageCellDelegate.didPressViaBot(this, user != null ? user.username : this.currentMessageObject.messageOwner.via_bot_name);
                        }
                        chatMessageCell = this;
                        chatMessageCell.nameLayoutSelector.setState(StateSet.NOTHING);
                        chatMessageCell.nameLayoutPressed = false;
                    } else {
                        TLRPC.User user2 = this.currentUser;
                        if (user2 != null) {
                            chatMessageCell = this;
                            chatMessageCellDelegate.didPressUserAvatar(chatMessageCell, user2, getEventX(motionEvent), getEventY(motionEvent), false);
                        } else {
                            chatMessageCell = this;
                            TLRPC.Chat chat2 = chatMessageCell.currentChat;
                            if (chat2 != null) {
                                TLRPC.MessageFwdHeader messageFwdHeader = chatMessageCell.currentMessageObject.messageOwner.fwd_from;
                                if (messageFwdHeader != null) {
                                    if (chat2 == null && (messageFwdHeader.flags & 16) != 0) {
                                        i2 = messageFwdHeader.saved_from_msg_id;
                                        chat = chat2;
                                    } else {
                                        i2 = messageFwdHeader.channel_post;
                                        chat = chatMessageCell.currentForwardChannel;
                                    }
                                    i = i2;
                                } else {
                                    chat = chat2;
                                    i = 0;
                                }
                                if (chat == null) {
                                    chat = chat2;
                                }
                                chatMessageCellDelegate.didPressChannelAvatar(chatMessageCell, chat, i, chatMessageCell.lastTouchX, chatMessageCell.lastTouchY, false);
                            }
                        }
                        chatMessageCell.nameLayoutSelector.setState(StateSet.NOTHING);
                        chatMessageCell.nameLayoutPressed = false;
                    }
                } else {
                    chatMessageCell = this;
                    chatMessageCell.nameLayoutSelector.setState(StateSet.NOTHING);
                    chatMessageCell.nameLayoutPressed = false;
                }
            }
            return chatMessageCell.nameLayoutPressed;
        }
        chatMessageCell = this;
        return chatMessageCell.nameLayoutPressed;
    }

    private boolean checkNameStatusMotionEvent(MotionEvent motionEvent) {
        if (!this.drawNameLayout || this.nameLayout == null || this.nameLayoutSelector == null || ((this.currentUser == null && this.currentChat == null) || this.currentNameStatus == null || this.currentNameStatusDrawable == null)) {
            this.nameStatusPressed = false;
            return false;
        }
        boolean zContains = this.nameStatusSelector.getBounds().contains((int) getEventX(motionEvent), (int) getEventY(motionEvent));
        if (motionEvent.getAction() == 0) {
            this.nameStatusPressed = zContains;
            if (zContains) {
                this.nameStatusSelector.setHotspot((int) getEventX(motionEvent), (int) getEventY(motionEvent));
                this.nameStatusSelector.setState(this.pressedState);
            }
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (motionEvent.getAction() == 1 && this.nameStatusPressed && this.delegate != null && this.currentUser != null) {
                this.delegate.didPressUserStatus(this, this.currentUser, this.currentNameStatusDrawable.getDrawable() instanceof AnimatedEmojiDrawable ? ((AnimatedEmojiDrawable) this.currentNameStatusDrawable.getDrawable()).getDocument() : null, this.nameStatusSlug);
                invalidateOutbounds();
            }
            this.nameStatusSelector.setState(StateSet.NOTHING);
            this.nameStatusPressed = false;
        }
        return this.nameStatusPressed;
    }

    private void resetCodeSelectors() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.textLayoutBlocks != null) {
            for (int i = 0; i < this.currentMessageObject.textLayoutBlocks.size(); i++) {
                MessageObject.TextLayoutBlock textLayoutBlock = this.currentMessageObject.textLayoutBlocks.get(i);
                Drawable drawable = textLayoutBlock.copySelector;
                if (drawable != null) {
                    drawable.setCallback(this);
                    textLayoutBlock.copySelector.setState(StateSet.NOTHING);
                }
            }
        }
        if (this.captionLayout != null) {
            for (int i2 = 0; i2 < this.captionLayout.textLayoutBlocks.size(); i2++) {
                MessageObject.TextLayoutBlock textLayoutBlock2 = this.captionLayout.textLayoutBlocks.get(i2);
                Drawable drawable2 = textLayoutBlock2.copySelector;
                if (drawable2 != null) {
                    drawable2.setCallback(this);
                    textLayoutBlock2.copySelector.setState(StateSet.NOTHING);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:154:0x02c5 A[Catch: Exception -> 0x02bd, TryCatch #0 {Exception -> 0x02bd, blocks: (B:146:0x029b, B:152:0x02bf, B:154:0x02c5, B:156:0x02ca, B:158:0x02d6, B:161:0x02f1, B:163:0x02f4, B:166:0x02ff, B:159:0x02e3, B:149:0x02b9), top: B:281:0x029b }] */
    /* JADX WARN: Removed duplicated region for block: B:195:0x038a  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x03bc  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x03f0  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x045d  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x04c1  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x04eb A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0456 A[EDGE_INSN: B:292:0x0456->B:241:0x0456 BREAK  A[LOOP:1: B:220:0x03e6->B:240:0x0453], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:316:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean checkTextBlockMotionEvent(android.view.MotionEvent r21) {
        /*
            Method dump skipped, instructions count: 1261
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.checkTextBlockMotionEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:146:0x02b8 A[Catch: Exception -> 0x02b0, TryCatch #0 {Exception -> 0x02b0, blocks: (B:138:0x028e, B:144:0x02b2, B:146:0x02b8, B:148:0x02bd, B:150:0x02c9, B:153:0x02e4, B:155:0x02e7, B:158:0x02f2, B:151:0x02d6, B:141:0x02ac), top: B:269:0x028e }] */
    /* JADX WARN: Removed duplicated region for block: B:187:0x037d  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x03af  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x03bd  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x045d  */
    /* JADX WARN: Removed duplicated region for block: B:255:0x04b9  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x04e3 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:302:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean checkCaptionMotionEvent(android.view.MotionEvent r21) {
        /*
            Method dump skipped, instructions count: 1253
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.checkCaptionMotionEvent(android.view.MotionEvent):boolean");
    }

    private boolean checkGameMotionEvent(MotionEvent motionEvent) {
        int i;
        int i2;
        if (!this.hasGamePreview) {
            return false;
        }
        int eventX = (int) getEventX(motionEvent);
        int eventY = (int) getEventY(motionEvent);
        if (motionEvent.getAction() == 0) {
            if (this.drawPhotoImage && this.drawImageButton && this.buttonState != -1 && eventX >= (i = this.buttonX) && eventX <= i + AndroidUtilities.m1146dp(48.0f) && eventY >= (i2 = this.buttonY) && eventY <= i2 + AndroidUtilities.m1146dp(48.0f) && this.radialProgress.getIcon() != 4) {
                this.buttonPressed = 1;
                invalidate();
                return true;
            }
            if (this.drawPhotoImage && this.photoImage.isInsideImage(eventX, eventY)) {
                this.gamePreviewPressed = true;
                return true;
            }
            if (this.descriptionLayout != null && eventY >= this.descriptionY) {
                try {
                    int iM1146dp = eventX - ((this.unmovedTextX + AndroidUtilities.m1146dp(10.0f)) + this.descriptionX);
                    int i3 = eventY - this.descriptionY;
                    int lineForVertical = this.descriptionLayout.getLineForVertical(i3);
                    float f = iM1146dp;
                    int offsetForHorizontal = this.descriptionLayout.getOffsetForHorizontal(lineForVertical, f);
                    float lineLeft = this.descriptionLayout.getLineLeft(lineForVertical);
                    if (lineLeft <= f && lineLeft + this.descriptionLayout.getLineWidth(lineForVertical) >= f) {
                        Spannable spannable = (Spannable) this.currentMessageObject.linkDescription;
                        ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
                        if (clickableSpanArr.length != 0 && ((!(clickableSpanArr[0] instanceof URLSpanBotCommand) || URLSpanBotCommand.enabled) && !AndroidUtilities.isAccessibilityScreenReaderEnabled())) {
                            LinkSpanDrawable linkSpanDrawable = this.pressedLink;
                            if (linkSpanDrawable == null || linkSpanDrawable.getSpan() != clickableSpanArr[0]) {
                                this.links.removeLink(this.pressedLink);
                                ClickableSpan clickableSpan = clickableSpanArr[0];
                                LinkSpanDrawable linkSpanDrawable2 = new LinkSpanDrawable(clickableSpan, this.resourcesProvider, f, i3, spanSupportsLongPress(clickableSpan));
                                this.pressedLink = linkSpanDrawable2;
                                linkSpanDrawable2.setColor(getThemedColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outLinkSelectBackground : Theme.key_chat_linkSelectBackground));
                                this.linkBlockNum = -10;
                                this.pressedLinkType = 2;
                                try {
                                    LinkPath linkPathObtainNewPath = this.pressedLink.obtainNewPath();
                                    int[] realSpanStartAndEnd = getRealSpanStartAndEnd(spannable, this.pressedLink.getSpan());
                                    linkPathObtainNewPath.setCurrentLayout(this.descriptionLayout, realSpanStartAndEnd[0], 0.0f);
                                    this.descriptionLayout.getSelectionPath(realSpanStartAndEnd[0], realSpanStartAndEnd[1], linkPathObtainNewPath);
                                } catch (Exception e) {
                                    FileLog.m1160e(e);
                                }
                                this.links.addLink(this.pressedLink, 2);
                            }
                            invalidate();
                            return true;
                        }
                    }
                } catch (Exception e2) {
                    FileLog.m1160e(e2);
                }
            }
        } else if (motionEvent.getAction() == 1) {
            if (this.pressedLinkType == 2 || this.gamePreviewPressed || this.buttonPressed != 0) {
                if (this.buttonPressed != 0) {
                    this.buttonPressed = 0;
                    playSoundEffect(0);
                    didPressButton(true, false);
                    invalidate();
                } else {
                    LinkSpanDrawable linkSpanDrawable3 = this.pressedLink;
                    if (linkSpanDrawable3 != null) {
                        if (linkSpanDrawable3.getSpan() instanceof URLSpan) {
                            Browser.openUrl(getContext(), ((URLSpan) this.pressedLink.getSpan()).getURL());
                        } else if (this.pressedLink.getSpan() instanceof ClickableSpan) {
                            ((ClickableSpan) this.pressedLink.getSpan()).onClick(this);
                        }
                        resetPressedLink(2);
                    } else {
                        this.gamePreviewPressed = false;
                        int i4 = 0;
                        while (true) {
                            if (i4 >= this.botButtons.size()) {
                                break;
                            }
                            BotButton botButton = (BotButton) this.botButtons.get(i4);
                            if (botButton.button instanceof TLRPC.TL_keyboardButtonGame) {
                                playSoundEffect(0);
                                this.delegate.didPressBotButton(this, botButton.button);
                                invalidate();
                                break;
                            }
                            i4++;
                        }
                        resetPressedLink(2);
                        return true;
                    }
                }
            } else {
                resetPressedLink(2);
            }
        }
        return false;
    }

    private boolean checkTranscribeButtonMotionEvent(MotionEvent motionEvent) {
        TranscribeButton transcribeButton;
        if (this.useTranscribeButton) {
            return (!this.isPlayingRound || getVideoTranscriptionProgress() > 0.0f || this.wasTranscriptionOpen) && (transcribeButton = this.transcribeButton) != null && transcribeButton.onTouch(motionEvent.getAction(), getEventX(motionEvent), getEventY(motionEvent));
        }
        return false;
    }

    private boolean checkLinkPreviewMotionEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        int i;
        MessageObject messageObject;
        TLRPC.TL_channelAdminLogEvent tL_channelAdminLogEvent;
        int i2;
        int i3;
        int i4;
        int i5;
        int themedColor;
        MessageObject messageObject2;
        int i6 = this.currentMessageObject.type;
        if ((i6 != 0 && i6 != 24) || !this.hasLinkPreview) {
            return false;
        }
        int eventX = (int) getEventX(motionEvent);
        int eventY = (int) getEventY(motionEvent);
        int i7 = this.unmovedTextX;
        if (eventX >= i7 && eventX <= (i7 + this.backgroundWidth) - AndroidUtilities.m1146dp(14.0f) && eventY >= (i = this.linkPreviewY)) {
            if (eventY <= i + this.linkPreviewHeight + AndroidUtilities.m1146dp((this.drawInstantView ? 46 : 0) + 8)) {
                if (motionEvent.getAction() == 0) {
                    if (this.descriptionLayout != null && eventY >= this.descriptionY && !this.currentMessageObject.preview) {
                        try {
                            int iM1146dp = eventX - ((this.unmovedTextX + AndroidUtilities.m1146dp(10.0f)) + this.descriptionX);
                            int i8 = eventY - this.descriptionY;
                            if (i8 <= this.descriptionLayout.getHeight()) {
                                int lineForVertical = this.descriptionLayout.getLineForVertical(i8);
                                float f = iM1146dp;
                                int offsetForHorizontal = this.descriptionLayout.getOffsetForHorizontal(lineForVertical, f);
                                float lineLeft = this.descriptionLayout.getLineLeft(lineForVertical);
                                if (lineLeft <= f && lineLeft + this.descriptionLayout.getLineWidth(lineForVertical) >= f) {
                                    Spannable spannable = (Spannable) (this.currentMessageObject.isSponsored() ? this.currentMessageObject.messageText : this.currentMessageObject.linkDescription);
                                    ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
                                    if (clickableSpanArr.length != 0 && ((!(clickableSpanArr[0] instanceof URLSpanBotCommand) || URLSpanBotCommand.enabled) && !AndroidUtilities.isAccessibilityScreenReaderEnabled())) {
                                        LinkSpanDrawable linkSpanDrawable = this.pressedLink;
                                        if (linkSpanDrawable == null || linkSpanDrawable.getSpan() != clickableSpanArr[0]) {
                                            this.links.removeLink(this.pressedLink);
                                            ClickableSpan clickableSpan = clickableSpanArr[0];
                                            LinkSpanDrawable linkSpanDrawable2 = new LinkSpanDrawable(clickableSpan, this.resourcesProvider, eventX, eventY, spanSupportsLongPress(clickableSpan));
                                            this.pressedLink = linkSpanDrawable2;
                                            if (!this.hasLinkPreview || this.linkLine == null || (messageObject2 = this.currentMessageObject) == null || messageObject2.isOutOwner()) {
                                                themedColor = getThemedColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outLinkSelectBackground : Theme.key_chat_linkSelectBackground);
                                            } else {
                                                themedColor = Theme.multAlpha(this.linkLine.getColor(), 0.1f);
                                            }
                                            linkSpanDrawable2.setColor(themedColor);
                                            this.linkBlockNum = -10;
                                            this.pressedLinkType = 2;
                                            startCheckLongPress();
                                            try {
                                                LinkPath linkPathObtainNewPath = this.pressedLink.obtainNewPath();
                                                int[] realSpanStartAndEnd = getRealSpanStartAndEnd(spannable, this.pressedLink.getSpan());
                                                linkPathObtainNewPath.setCurrentLayout(this.descriptionLayout, realSpanStartAndEnd[0], 0.0f);
                                                this.descriptionLayout.getSelectionPath(realSpanStartAndEnd[0], realSpanStartAndEnd[1], linkPathObtainNewPath);
                                            } catch (Exception e) {
                                                FileLog.m1160e(e);
                                            }
                                            this.links.addLink(this.pressedLink, 2);
                                        }
                                        invalidate();
                                        return true;
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            FileLog.m1160e(e2);
                        }
                    }
                    if (this.pressedLink == null) {
                        int iM1146dp2 = AndroidUtilities.m1146dp(48.0f);
                        if (this.miniButtonState >= 0) {
                            int iM1146dp3 = AndroidUtilities.m1146dp(27.0f);
                            int i9 = this.buttonX;
                            if (eventX >= i9 + iM1146dp3 && eventX <= i9 + iM1146dp3 + iM1146dp2) {
                                int i10 = this.buttonY;
                                if (eventY >= i10 + iM1146dp3 && eventY <= i10 + iM1146dp3 + iM1146dp2) {
                                    this.miniButtonPressed = 1;
                                    invalidate();
                                    return true;
                                }
                            }
                        }
                        if (this.drawVideoImageButton && this.buttonState != -1 && eventX >= (i4 = this.videoButtonX) && eventX <= i4 + AndroidUtilities.m1146dp(34.0f) + Math.max(this.infoWidth, this.docTitleWidth) && eventY >= (i5 = this.videoButtonY) && eventY <= i5 + AndroidUtilities.m1146dp(30.0f)) {
                            this.videoButtonPressed = 1;
                            invalidate();
                            return true;
                        }
                        if ((this.drawPhotoImage && this.currentMessageObject.sponsoredMedia != null && this.photoImage.isInsideImage(eventX, eventY)) || (this.drawPhotoImage && this.drawImageButton && this.buttonState != -1 && ((!this.checkOnlyButtonPressed && this.photoImage.isInsideImage(eventX, eventY)) || (eventX >= (i2 = this.buttonX) && eventX <= i2 + AndroidUtilities.m1146dp(48.0f) && eventY >= (i3 = this.buttonY) && eventY <= i3 + AndroidUtilities.m1146dp(48.0f) && this.radialProgress.getIcon() != 4)))) {
                            this.buttonPressed = 1;
                            invalidate();
                            TLRPC.MessageMedia messageMedia = this.currentMessageObject.sponsoredMedia;
                            if (messageMedia != null && (MessageObject.isGifDocument(messageMedia.document) || this.currentMessageObject.sponsoredMedia.photo != null)) {
                                Drawable drawable = this.selectorDrawable[0];
                                if (drawable != null && drawable.getBounds().contains(eventX, eventY)) {
                                    this.selectorDrawable[0].setHotspot(eventX, eventY);
                                    this.selectorDrawable[0].setState(this.pressedState);
                                }
                                Drawable drawable2 = this.linkPreviewSelector;
                                if (drawable2 != null && drawable2.getBounds().contains(eventX, eventY)) {
                                    this.linkPreviewSelector.setHotspot(eventX, eventY);
                                    this.linkPreviewSelector.setState(this.pressedState);
                                }
                                setInstantButtonPressed(true);
                                ButtonBounce buttonBounce = this.linkPreviewBounce;
                                if (buttonBounce != null) {
                                    buttonBounce.setPressed(true);
                                }
                            }
                            return true;
                        }
                        this.instantPressed = true;
                        this.selectorDrawableMaskType[0] = 0;
                        Drawable drawable3 = this.selectorDrawable[0];
                        if (drawable3 != null && drawable3.getBounds().contains(eventX, eventY)) {
                            this.selectorDrawable[0].setHotspot(eventX, eventY);
                            this.selectorDrawable[0].setState(this.pressedState);
                        }
                        Drawable drawable4 = this.linkPreviewSelector;
                        if (drawable4 != null && drawable4.getBounds().contains(eventX, eventY)) {
                            this.linkPreviewSelector.setHotspot(eventX, eventY);
                            this.linkPreviewSelector.setState(this.pressedState);
                        }
                        setInstantButtonPressed(true);
                        ButtonBounce buttonBounce2 = this.linkPreviewBounce;
                        if (buttonBounce2 != null) {
                            buttonBounce2.setPressed(true);
                        }
                        invalidate();
                        return true;
                    }
                } else if (motionEvent.getAction() == 1) {
                    if (this.instantPressed) {
                        if (this.documentAttachType == 7) {
                            if (!MediaController.getInstance().isPlayingMessage(this.currentMessageObject) || MediaController.getInstance().isMessagePaused()) {
                                this.delegate.needPlayMessage(this, this.currentMessageObject, false);
                            } else {
                                MediaController.getInstance().lambda$startAudioAgain$7(this.currentMessageObject);
                            }
                        } else if (this.drawInstantView || ((messageObject = this.currentMessageObject) != null && (tL_channelAdminLogEvent = messageObject.currentEvent) != null && (tL_channelAdminLogEvent.action instanceof TLRPC.TL_channelAdminLogEventActionEditMessage))) {
                            ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
                            if (chatMessageCellDelegate != null) {
                                chatMessageCellDelegate.didPressInstantButton(this, this.drawInstantViewType);
                            }
                        } else if (messageObject != null && !messageObject.preview) {
                            TLRPC.WebPage webPage = MessageObject.getMedia(messageObject.messageOwner).webpage;
                            if (webPage != null && !TextUtils.isEmpty(webPage.embed_url)) {
                                ChatMessageCellDelegate chatMessageCellDelegate2 = this.delegate;
                                if (chatMessageCellDelegate2 != null) {
                                    chatMessageCellDelegate2.needOpenWebView(this.currentMessageObject, webPage.embed_url, webPage.site_name, webPage.title, webPage.url, webPage.embed_width, webPage.embed_height);
                                }
                            } else if (webPage != null) {
                                if (this.delegate != null) {
                                    if (this.drawPhotoImage && this.photoImage.isInsideImage(eventX, eventY)) {
                                        this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY, false);
                                    } else {
                                        this.delegate.didPressWebPage(this, webPage, webPage.url, MessageObject.getMedia(this.currentMessageObject.messageOwner).safe);
                                    }
                                } else {
                                    Browser.openUrl(getContext(), webPage.url);
                                }
                            }
                        }
                        playSoundEffect(0);
                        Drawable drawable5 = this.selectorDrawable[0];
                        if (drawable5 != null) {
                            drawable5.setState(StateSet.NOTHING);
                        }
                        Drawable drawable6 = this.linkPreviewSelector;
                        if (drawable6 != null) {
                            drawable6.setState(StateSet.NOTHING);
                        }
                        ButtonBounce buttonBounce3 = this.linkPreviewBounce;
                        if (buttonBounce3 != null) {
                            buttonBounce3.setPressed(false);
                        }
                        this.instantPressed = false;
                        setInstantButtonPressed(false);
                        invalidate();
                    } else if (this.pressedLinkType == 2 || this.buttonPressed != 0 || this.miniButtonPressed != 0 || this.videoButtonPressed != 0 || this.linkPreviewPressed) {
                        if (this.videoButtonPressed == 1) {
                            this.videoButtonPressed = 0;
                            playSoundEffect(0);
                            didPressButton(true, true);
                            invalidate();
                        } else if (this.buttonPressed != 0) {
                            this.buttonPressed = 0;
                            playSoundEffect(0);
                            if (this.drawVideoImageButton || this.currentMessageObject.sponsoredMedia != null) {
                                didClickedImage();
                            } else {
                                didPressButton(true, false);
                            }
                            invalidate();
                        } else if (this.miniButtonPressed != 0) {
                            this.miniButtonPressed = 0;
                            playSoundEffect(0);
                            didPressMiniButton(true);
                            invalidate();
                        } else {
                            LinkSpanDrawable linkSpanDrawable3 = this.pressedLink;
                            if (linkSpanDrawable3 != null) {
                                if (linkSpanDrawable3.getSpan() instanceof URLSpan) {
                                    this.delegate.didPressUrl(this, this.pressedLink.getSpan(), false);
                                } else if (this.pressedLink.getSpan() instanceof ClickableSpan) {
                                    ((ClickableSpan) this.pressedLink.getSpan()).onClick(this);
                                }
                                resetPressedLink(2);
                            } else {
                                AnimatedEmojiSpan animatedEmojiSpan = this.pressedEmoji;
                                if (animatedEmojiSpan != null && this.delegate.didPressAnimatedEmoji(this, animatedEmojiSpan)) {
                                    this.pressedEmoji = null;
                                    resetPressedLink(2);
                                } else {
                                    if (this.documentAttachType == 2 && this.drawImageButton) {
                                        int i11 = this.buttonState;
                                        if (i11 == -1) {
                                            if (SharedConfig.isAutoplayGifs() && !this.currentMessageObject.isRepostPreview) {
                                                this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY, false);
                                            } else {
                                                this.buttonState = 2;
                                                this.currentMessageObject.gifState = 1.0f;
                                                this.photoImage.setAllowStartAnimation(false);
                                                this.photoImage.stopAnimation();
                                                this.radialProgress.setIcon(getIconForCurrentState(), false, true);
                                                invalidate();
                                                playSoundEffect(0);
                                            }
                                        } else if (i11 == 2 || i11 == 0) {
                                            didPressButton(true, false);
                                            playSoundEffect(0);
                                        }
                                    } else {
                                        MessageObject messageObject3 = this.currentMessageObject;
                                        if (!messageObject3.preview) {
                                            TLRPC.WebPage webPage2 = MessageObject.getMedia(messageObject3.messageOwner).webpage;
                                            if (webPage2 != null && !TextUtils.isEmpty(webPage2.embed_url)) {
                                                this.delegate.needOpenWebView(this.currentMessageObject, webPage2.embed_url, webPage2.site_name, webPage2.title, webPage2.url, webPage2.embed_width, webPage2.embed_height);
                                            } else {
                                                int i12 = this.buttonState;
                                                if (i12 == -1 || i12 == 3) {
                                                    this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY, false);
                                                    playSoundEffect(0);
                                                } else if (webPage2 != null) {
                                                    ChatMessageCellDelegate chatMessageCellDelegate3 = this.delegate;
                                                    if (chatMessageCellDelegate3 != null) {
                                                        chatMessageCellDelegate3.didPressWebPage(this, webPage2, webPage2.url, MessageObject.getMedia(this.currentMessageObject.messageOwner).safe);
                                                    } else {
                                                        Browser.openUrl(getContext(), webPage2.url);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    resetPressedLink(2);
                                    return true;
                                }
                            }
                        }
                    } else if (!this.hadLongPress) {
                        this.hadLongPress = false;
                        resetPressedLink(2);
                    }
                } else if (motionEvent.getAction() == 2 && this.instantButtonPressed) {
                    Drawable drawable7 = this.selectorDrawable[0];
                    if (drawable7 != null) {
                        drawable7.setHotspot(eventX, eventY);
                    }
                    Drawable drawable8 = this.linkPreviewSelector;
                    if (drawable8 != null) {
                        drawable8.setHotspot(eventX, eventY);
                    }
                }
            }
        }
        return false;
    }

    private boolean checkEffectMotionEvent(MotionEvent motionEvent) {
        ChatMessageCellDelegate chatMessageCellDelegate;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.getEffect() == null) {
            return false;
        }
        int timeX = (int) ((getTimeX() - AndroidUtilities.m1146dp(this.effectId == 0 ? 0.0f : 18.0f)) + (this.timeWidth * (this.currentMessageObject.sendPreview ? 1.0f - this.timeAlpha : 0.0f)));
        int timeY = (int) getTimeY();
        int iM1146dp = timeX - AndroidUtilities.m1146dp(2.0f);
        int iM1146dp2 = timeY - AndroidUtilities.m1146dp(2.0f);
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(iM1146dp, iM1146dp2, iM1146dp + AndroidUtilities.m1146dp(16.0f) + this.timeWidth, iM1146dp2 + AndroidUtilities.m1146dp(16.0f));
        boolean zContains = rectF.contains(getEventX(motionEvent), getEventY(motionEvent));
        if (motionEvent.getAction() == 0) {
            if (zContains) {
                if (this.effectDrawableBounce == null) {
                    this.effectDrawableBounce = new ButtonBounce(this);
                }
                this.pressedEffect = true;
            }
        } else if (motionEvent.getAction() == 2) {
            this.pressedEffect = zContains;
        } else if (motionEvent.getAction() == 1) {
            if (this.pressedEffect && (chatMessageCellDelegate = this.delegate) != null) {
                chatMessageCellDelegate.didPressEffect(this);
            }
            this.pressedEffect = false;
        } else if (motionEvent.getAction() == 3) {
            this.pressedEffect = false;
        }
        ButtonBounce buttonBounce = this.effectDrawableBounce;
        if (buttonBounce != null) {
            buttonBounce.setPressed(this.pressedEffect);
        }
        return this.pressedEffect;
    }

    private boolean checkFactCheckMotionEvent(MotionEvent motionEvent) {
        int iM1146dp;
        int extraTextX;
        int iM1146dp2;
        ChatMessageCellDelegate chatMessageCellDelegate;
        if (!this.hasFactCheck || this.factCheckTitle == null || this.factCheckWhat == null) {
            return false;
        }
        if (this.factCheckWhatBounce == null) {
            this.factCheckWhatBounce = new ButtonBounce(this);
        }
        if (this.factCheckBounce == null) {
            ButtonBounce buttonBounce = new ButtonBounce(this);
            this.factCheckBounce = buttonBounce;
            buttonBounce.setAdditionalInvalidate(new ChatMessageCell$$ExternalSyntheticLambda5(this));
        }
        float backgroundDrawableRight = getBackgroundDrawableRight();
        TransitionParams transitionParams = this.transitionParams;
        float fM1146dp = ((backgroundDrawableRight + (transitionParams != null ? transitionParams.deltaRight : 0.0f)) - AndroidUtilities.m1146dp(10 + ((!this.currentMessageObject.isOutOwner() || this.mediaBackground || this.drawPinnedBottom) ? 0 : 6))) - getExtraTextX();
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null && !groupedMessages.isDocuments) {
            iM1146dp2 = (int) this.captionX;
        } else {
            if (this.currentMessageObject.isOutOwner()) {
                iM1146dp2 = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(12.0f) + getExtraTextX();
                if (this.currentMessageObject.type == 19) {
                    iM1146dp2 -= Math.max(0, ((Math.max(this.replyNameWidth, this.replyTextWidth) + iM1146dp2) + AndroidUtilities.m1146dp(14.0f)) - AndroidUtilities.displaySize.x);
                }
            } else {
                if (this.mediaBackground) {
                    iM1146dp = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(12.0f);
                    extraTextX = getExtraTextX();
                } else {
                    iM1146dp = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(this.drawPinnedBottom ? 12.0f : 18.0f);
                    extraTextX = getExtraTextX();
                }
                iM1146dp2 = iM1146dp + extraTextX;
            }
        }
        int iM1146dp3 = ((int) (iM1146dp2 + this.transitionParams.deltaLeft)) - AndroidUtilities.m1146dp(1.33f);
        int i = this.factCheckY;
        float f = iM1146dp3;
        int i2 = (int) (fM1146dp - f);
        int eventX = (int) getEventX(motionEvent);
        int eventY = (int) getEventY(motionEvent);
        int iM1146dp4 = (int) (AndroidUtilities.m1146dp(10.0f) + iM1146dp3 + this.factCheckTitle.getCurrentWidth() + AndroidUtilities.m1146dp(4.0f));
        int iM1146dp5 = AndroidUtilities.m1146dp(4.33f) + i;
        RectF rectF = AndroidUtilities.rectTmp;
        float f2 = iM1146dp4;
        rectF.set(f2, iM1146dp5, this.factCheckWhat.getCurrentWidth() + f2 + AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(17.33f) + iM1146dp5);
        boolean zContains = rectF.contains(getEventX(motionEvent), getEventY(motionEvent));
        rectF.set(f, i, iM1146dp3 + i2, this.factCheckHeight + i);
        boolean z = !zContains && !this.hadLongPress && this.pressedFactCheckLink == null && rectF.contains(getEventX(motionEvent), getEventY(motionEvent));
        if (((z && !this.hadLongPress) || this.pressedFactCheckLink != null) && this.factCheckTextLayout != null) {
            if (this.factCheckLinks == null) {
                LinkSpanDrawable.LinkCollector linkCollector = new LinkSpanDrawable.LinkCollector(this);
                this.factCheckLinks = linkCollector;
                linkCollector.setAdditionalInvalidate(new ChatMessageCell$$ExternalSyntheticLambda5(this));
            }
            int iM1146dp6 = (iM1146dp3 + AndroidUtilities.m1146dp(10.0f)) - this.factCheckTextLayoutLeft;
            int iM1146dp7 = i + AndroidUtilities.m1146dp(22.0f);
            if (motionEvent.getAction() == 0 || (motionEvent.getAction() == 1 && this.pressedFactCheckLink != null)) {
                if (eventX >= iM1146dp6 && eventY >= iM1146dp7 && eventX <= i2 + iM1146dp6 && eventY <= this.factCheckTextLayout.getHeight() + iM1146dp7) {
                    try {
                        int i3 = eventY - iM1146dp7;
                        int lineForVertical = this.factCheckTextLayout.getLineForVertical(i3);
                        float f3 = eventX - iM1146dp6;
                        int offsetForHorizontal = this.factCheckTextLayout.getOffsetForHorizontal(lineForVertical, f3);
                        float lineLeft = this.factCheckTextLayout.getLineLeft(lineForVertical);
                        if (lineLeft <= f3 && lineLeft + this.factCheckTextLayout.getLineWidth(lineForVertical) >= f3) {
                            Spannable spannable = (Spannable) this.factCheckTextLayout.getText();
                            CharacterStyle[] characterStyleArr = (CharacterStyle[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
                            if (characterStyleArr == null || characterStyleArr.length == 0) {
                                characterStyleArr = (CharacterStyle[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, URLSpanMono.class);
                            }
                            if (characterStyleArr.length != 0 && ((!(characterStyleArr[0] instanceof URLSpanBotCommand) || URLSpanBotCommand.enabled) && !AndroidUtilities.isAccessibilityScreenReaderEnabled())) {
                                if (motionEvent.getAction() == 0) {
                                    LinkSpanDrawable linkSpanDrawable = this.pressedFactCheckLink;
                                    if (linkSpanDrawable == null || linkSpanDrawable.getSpan() != characterStyleArr[0]) {
                                        this.factCheckLinks.removeLink(this.pressedFactCheckLink);
                                        CharacterStyle characterStyle = characterStyleArr[0];
                                        LinkSpanDrawable linkSpanDrawable2 = new LinkSpanDrawable(characterStyle, this.resourcesProvider, f3, i3, spanSupportsLongPress(characterStyle));
                                        this.pressedFactCheckLink = linkSpanDrawable2;
                                        ReplyMessageLine replyMessageLine = this.factCheckLine;
                                        if (replyMessageLine != null) {
                                            linkSpanDrawable2.setColor(Theme.multAlpha(replyMessageLine.getColor(), 0.1f));
                                        }
                                        try {
                                            LinkPath linkPathObtainNewPath = this.pressedFactCheckLink.obtainNewPath();
                                            int[] realSpanStartAndEnd = getRealSpanStartAndEnd(spannable, this.pressedFactCheckLink.getSpan());
                                            linkPathObtainNewPath.setCurrentLayout(this.factCheckTextLayout, realSpanStartAndEnd[0], 0.0f);
                                            this.factCheckTextLayout.getSelectionPath(realSpanStartAndEnd[0], realSpanStartAndEnd[1], linkPathObtainNewPath);
                                        } catch (Exception e) {
                                            FileLog.m1160e(e);
                                        }
                                        this.factCheckLinks.addLink(this.pressedFactCheckLink, 1);
                                        z = false;
                                    }
                                    invalidate();
                                } else {
                                    LinkSpanDrawable linkSpanDrawable3 = this.pressedFactCheckLink;
                                    if (linkSpanDrawable3 != null && characterStyleArr[0] == linkSpanDrawable3.getSpan()) {
                                        this.delegate.didPressUrl(this, this.pressedFactCheckLink.getSpan(), false);
                                        resetPressedLink(1);
                                        this.factCheckWhatPressed = false;
                                        this.factCheckPressed = false;
                                        this.pressedFactCheckLink = null;
                                    }
                                }
                            }
                        }
                    } catch (Exception e2) {
                        FileLog.m1160e(e2);
                    }
                } else {
                    resetPressedLink(1);
                }
            }
        }
        boolean z2 = this.factCheckLarge && z;
        if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
            this.factCheckWhatPressed = zContains;
            this.factCheckPressed = z2;
        } else if (motionEvent.getAction() == 1) {
            if (this.factCheckWhatPressed) {
                ChatMessageCellDelegate chatMessageCellDelegate2 = this.delegate;
                if (chatMessageCellDelegate2 != null) {
                    chatMessageCellDelegate2.didPressFactCheckWhat(this, (int) (iM1146dp4 + AndroidUtilities.m1146dp(4.0f) + (this.factCheckWhat.getCurrentWidth() / 2.0f)), iM1146dp5);
                }
            } else if (this.factCheckPressed && !this.transitionParams.animateFactCheckExpanded && (chatMessageCellDelegate = this.delegate) != null) {
                chatMessageCellDelegate.didPressFactCheck(this);
            }
            this.factCheckWhatPressed = false;
            this.factCheckPressed = false;
        } else if (motionEvent.getAction() == 3) {
            this.factCheckWhatPressed = false;
            this.factCheckPressed = false;
            LinkSpanDrawable.LinkCollector linkCollector2 = this.factCheckLinks;
            if (linkCollector2 != null) {
                linkCollector2.clear();
            }
            this.pressedFactCheckLink = null;
        }
        this.factCheckWhatBounce.setPressed(this.factCheckWhatPressed);
        this.factCheckBounce.setPressed(this.factCheckPressed);
        return this.factCheckWhatPressed || this.factCheckPressed || this.pressedFactCheckLink != null;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0211  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x021b  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0255 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean checkPollButtonMotionEvent(android.view.MotionEvent r22) {
        /*
            Method dump skipped, instructions count: 1033
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.checkPollButtonMotionEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x00e3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean checkInstantButtonMotionEvent(android.view.MotionEvent r9) {
        /*
            Method dump skipped, instructions count: 264
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.checkInstantButtonMotionEvent(android.view.MotionEvent):boolean");
    }

    private boolean checkContactMotionEvent(MotionEvent motionEvent) {
        Drawable drawable;
        if (this.currentMessageObject.type != 12) {
            return false;
        }
        int eventX = (int) getEventX(motionEvent);
        int eventY = (int) getEventY(motionEvent);
        if (motionEvent.getAction() == 0) {
            ArrayList arrayList = this.contactButtons;
            if (arrayList != null && arrayList.size() > 1) {
                for (int i = 0; i < this.contactButtons.size(); i++) {
                    InstantViewButton instantViewButton = (InstantViewButton) this.contactButtons.get(i);
                    float f = eventX;
                    float f2 = eventY;
                    if (instantViewButton.rect.contains(f, f2)) {
                        if (instantViewButton.buttonBounce == null) {
                            instantViewButton.buttonBounce = new ButtonBounce(this);
                        }
                        instantViewButton.buttonBounce.setPressed(true);
                        if (instantViewButton.selectorDrawable != null) {
                            instantViewButton.selectorDrawable.setHotspot(f, f2);
                            instantViewButton.selectorDrawable.setState(this.pressedState);
                        }
                        invalidate();
                        return true;
                    }
                }
            }
            float f3 = eventX;
            float f4 = eventY;
            if (this.contactRect.contains(f3, f4)) {
                this.contactPressed = true;
                this.contactBounce.setPressed(true);
                Drawable drawable2 = this.selectorDrawable[0];
                if (drawable2 != null) {
                    drawable2.setHotspot(f3, f4);
                    this.selectorDrawable[0].setState(this.pressedState);
                }
                invalidate();
                return true;
            }
        } else if (motionEvent.getAction() == 1) {
            if (this.contactPressed) {
                if (this.delegate != null) {
                    ArrayList arrayList2 = this.contactButtons;
                    if (arrayList2 != null && arrayList2.size() == 1) {
                        this.delegate.didPressInstantButton(this, ((InstantViewButton) this.contactButtons.get(0)).type);
                    } else {
                        this.delegate.didPressInstantButton(this, 5);
                    }
                }
                playSoundEffect(0);
                Drawable drawable3 = this.selectorDrawable[0];
                if (drawable3 != null) {
                    drawable3.setState(StateSet.NOTHING);
                }
                this.contactPressed = false;
                this.contactBounce.setPressed(false);
                invalidate();
            } else {
                ArrayList arrayList3 = this.contactButtons;
                if (arrayList3 != null && arrayList3.size() > 1) {
                    for (int i2 = 0; i2 < this.contactButtons.size(); i2++) {
                        InstantViewButton instantViewButton2 = (InstantViewButton) this.contactButtons.get(i2);
                        if (instantViewButton2.buttonBounce != null && instantViewButton2.buttonBounce.isPressed()) {
                            ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
                            if (chatMessageCellDelegate != null) {
                                chatMessageCellDelegate.didPressInstantButton(this, instantViewButton2.type);
                            }
                            if (instantViewButton2.selectorDrawable != null) {
                                instantViewButton2.selectorDrawable.setState(StateSet.NOTHING);
                            }
                            instantViewButton2.buttonBounce.setPressed(false);
                            playSoundEffect(0);
                            invalidate();
                        }
                    }
                }
            }
        } else if (motionEvent.getAction() == 2) {
            if (this.contactPressed && (drawable = this.selectorDrawable[0]) != null) {
                drawable.setHotspot(eventX, eventY);
            } else {
                ArrayList arrayList4 = this.contactButtons;
                if (arrayList4 != null && arrayList4.size() > 1) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= this.contactButtons.size()) {
                            break;
                        }
                        InstantViewButton instantViewButton3 = (InstantViewButton) this.contactButtons.get(i3);
                        if (instantViewButton3.buttonBounce == null || !instantViewButton3.buttonBounce.isPressed()) {
                            i3++;
                        } else if (instantViewButton3.selectorDrawable != null) {
                            instantViewButton3.selectorDrawable.setHotspot(eventX, eventY);
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkTitleLabelMotion(MotionEvent motionEvent) {
        if (this.currentMessageObject.isSponsored() && this.currentMessageObject.sponsoredCanReport) {
            int eventX = (int) getEventX(motionEvent);
            int eventY = (int) getEventY(motionEvent);
            if (motionEvent.getAction() == 0) {
                float f = eventX;
                if (f >= this.titleLabelX - AndroidUtilities.m1146dp(6.0f) && f <= this.titleLabelX + this.titleLabelLayoutWidth + AndroidUtilities.m1146dp(6.0f)) {
                    float f2 = eventY;
                    if (f2 >= this.titleLabelY - AndroidUtilities.m1146dp(2.0f) && f2 <= this.titleLabelY + this.titleLabelLayoutHeight + AndroidUtilities.m1146dp(2.0f)) {
                        ButtonBounce buttonBounce = this.titleLabelBounce;
                        if (buttonBounce != null) {
                            buttonBounce.setPressed(true);
                        }
                        this.isTitleLabelPressed = true;
                        return true;
                    }
                }
            } else if (motionEvent.getAction() == 1 && this.isTitleLabelPressed) {
                ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
                if (chatMessageCellDelegate != null) {
                    chatMessageCellDelegate.didPressAboutRevenueSharingAds();
                }
                ButtonBounce buttonBounce2 = this.titleLabelBounce;
                if (buttonBounce2 != null) {
                    buttonBounce2.setPressed(false);
                }
                playSoundEffect(0);
                this.isTitleLabelPressed = false;
            }
        }
        return false;
    }

    private void invalidateWithParent() {
        if (this.currentMessagesGroup != null && getParent() != null) {
            ((ViewGroup) getParent()).invalidate();
        }
        invalidate();
    }

    private boolean checkCommentButtonMotionEvent(MotionEvent motionEvent) {
        Drawable drawable;
        if (!this.drawCommentButton) {
            return false;
        }
        int eventX = (int) getEventX(motionEvent);
        int eventY = (int) getEventY(motionEvent);
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if (groupedMessagePosition != null) {
            int i = groupedMessagePosition.flags;
            if ((i & 1) == 0 || (i & 8) == 0) {
                ViewGroup viewGroup = (ViewGroup) getParent();
                int childCount = viewGroup.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = viewGroup.getChildAt(i2);
                    if (childAt != this && (childAt instanceof ChatMessageCell)) {
                        ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                        if (chatMessageCell.drawCommentButton && chatMessageCell.currentMessagesGroup == this.currentMessagesGroup) {
                            int i3 = chatMessageCell.currentPosition.flags;
                            if ((i3 & 1) != 0 && (i3 & 8) != 0) {
                                MotionEvent motionEventObtain = MotionEvent.obtain(0L, 0L, motionEvent.getActionMasked(), (getEventX(motionEvent) + getLeft()) - chatMessageCell.getLeft(), (getEventY(motionEvent) + getTop()) - chatMessageCell.getTop(), 0);
                                boolean zCheckCommentButtonMotionEvent = chatMessageCell.checkCommentButtonMotionEvent(motionEventObtain);
                                motionEventObtain.recycle();
                                return zCheckCommentButtonMotionEvent;
                            }
                        }
                    }
                }
                return false;
            }
        }
        if (motionEvent.getAction() == 0) {
            if (this.commentButtonRect.contains(eventX, eventY)) {
                if (this.currentMessageObject.isSent()) {
                    this.selectorDrawableMaskType[1] = 2;
                    this.commentButtonPressed = true;
                    Drawable drawable2 = this.selectorDrawable[1];
                    if (drawable2 != null) {
                        drawable2.setHotspot(eventX, eventY);
                        this.selectorDrawable[1].setState(this.pressedState);
                    }
                    invalidateWithParent();
                }
                return true;
            }
        } else if (motionEvent.getAction() == 1) {
            if (this.commentButtonPressed) {
                ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
                if (chatMessageCellDelegate != null) {
                    if (this.isRepliesChat) {
                        chatMessageCellDelegate.didPressSideButton(this);
                    } else {
                        chatMessageCellDelegate.didPressCommentButton(this);
                    }
                }
                playSoundEffect(0);
                Drawable drawable3 = this.selectorDrawable[1];
                if (drawable3 != null) {
                    drawable3.setState(StateSet.NOTHING);
                }
                this.commentButtonPressed = false;
                invalidateWithParent();
            }
        } else if (motionEvent.getAction() == 2 && this.commentButtonPressed && (drawable = this.selectorDrawable[1]) != null) {
            drawable.setHotspot(eventX, eventY);
        }
        return false;
    }

    private boolean checkSponsoredCloseMotionEvent(MotionEvent motionEvent) {
        ButtonBounce buttonBounce;
        ChatMessageCellDelegate chatMessageCellDelegate;
        RectF rectF;
        if (motionEvent.getAction() == 0 && (rectF = this.closeSponsoredBounds) != null && this.closeSponsoredBounce != null && rectF.contains(getEventX(motionEvent), getEventY(motionEvent))) {
            this.closeSponsoredBounce.setPressed(true);
            return true;
        }
        if (motionEvent.getAction() == 1) {
            ButtonBounce buttonBounce2 = this.closeSponsoredBounce;
            if (buttonBounce2 != null && buttonBounce2.isPressed() && (chatMessageCellDelegate = this.delegate) != null) {
                chatMessageCellDelegate.didPressSponsoredClose(this);
            }
            ButtonBounce buttonBounce3 = this.closeSponsoredBounce;
            if (buttonBounce3 != null) {
                buttonBounce3.setPressed(false);
            }
        } else if (motionEvent.getAction() == 3 && (buttonBounce = this.closeSponsoredBounce) != null) {
            buttonBounce.setPressed(false);
        }
        ButtonBounce buttonBounce4 = this.closeSponsoredBounce;
        return buttonBounce4 != null && buttonBounce4.isPressed();
    }

    private boolean checkOtherButtonMotionEvent(MotionEvent motionEvent) {
        MessageObject.GroupedMessagePosition groupedMessagePosition;
        Drawable drawable;
        Drawable drawable2;
        int i = this.documentAttachType;
        if ((i == 5 || i == 1) && (groupedMessagePosition = this.currentPosition) != null && (groupedMessagePosition.flags & 4) == 0) {
            return false;
        }
        int i2 = this.currentMessageObject.type;
        boolean z = i2 == 16;
        if (!z) {
            z = ((i != 1 && i2 != 12 && i != 5 && i != 4 && i != 2 && i2 != 8) || this.hasGamePreview || this.hasInvoicePreview) ? false : true;
        }
        if (!z) {
            return false;
        }
        int eventX = (int) getEventX(motionEvent);
        int eventY = (int) getEventY(motionEvent);
        if (motionEvent.getAction() == 0) {
            MessageObject messageObject = this.currentMessageObject;
            if (messageObject.type == 16) {
                boolean zIsVideoCall = messageObject.isVideoCall();
                int i3 = this.otherX;
                if (eventX >= i3) {
                    if (eventX <= i3 + (LocaleController.isRTL ? 0 : this.backgroundWidth - AndroidUtilities.m1146dp(70.0f)) + AndroidUtilities.m1146dp((!zIsVideoCall ? 2 : 0) + 30) && eventY >= this.otherY - AndroidUtilities.m1146dp(14.0f) && eventY <= this.otherY + AndroidUtilities.m1146dp(50.0f)) {
                        this.otherPressed = true;
                        this.selectorDrawableMaskType[0] = 4;
                        if (this.selectorDrawable[0] != null) {
                            int iM1146dp = this.otherX + (LocaleController.isRTL ? 0 : this.backgroundWidth - AndroidUtilities.m1146dp(70.0f)) + AndroidUtilities.m1146dp(!zIsVideoCall ? 2 : 0) + (Theme.chat_msgInCallDrawable[zIsVideoCall ? 1 : 0].getIntrinsicWidth() / 2);
                            int intrinsicHeight = this.otherY + (Theme.chat_msgInCallDrawable[zIsVideoCall ? 1 : 0].getIntrinsicHeight() / 2);
                            this.selectorDrawable[0].setBounds(iM1146dp - AndroidUtilities.m1146dp(20.0f), intrinsicHeight - AndroidUtilities.m1146dp(20.0f), iM1146dp + AndroidUtilities.m1146dp(20.0f), intrinsicHeight + AndroidUtilities.m1146dp(20.0f));
                            this.selectorDrawable[0].setHotspot(eventX, eventY);
                            this.selectorDrawable[0].setState(this.pressedState);
                        }
                        invalidate();
                        return true;
                    }
                }
            } else if (eventX >= this.otherX - AndroidUtilities.m1146dp(20.0f) && eventX <= this.otherX + AndroidUtilities.m1146dp(20.0f) && eventY >= this.otherY - AndroidUtilities.m1146dp(4.0f) && eventY <= this.otherY + AndroidUtilities.m1146dp(30.0f)) {
                this.otherPressed = true;
                invalidate();
                return true;
            }
        } else if (motionEvent.getAction() == 1) {
            if (this.otherPressed) {
                if (this.currentMessageObject.type == 16 && (drawable2 = this.selectorDrawable[0]) != null) {
                    drawable2.setState(StateSet.NOTHING);
                }
                this.otherPressed = false;
                playSoundEffect(0);
                this.delegate.didPressOther(this, this.otherX, this.otherY);
                invalidate();
                return true;
            }
        } else if (motionEvent.getAction() == 2 && this.currentMessageObject.type == 16 && this.otherPressed && (drawable = this.selectorDrawable[0]) != null) {
            drawable.setHotspot(eventX, eventY);
        }
        return false;
    }

    private void setInstantButtonPressed(boolean z) {
        if (this.instantButtonBounce == null) {
            this.instantButtonBounce = new ButtonBounce(this);
        }
        ButtonBounce buttonBounce = this.instantButtonBounce;
        this.instantButtonPressed = z;
        buttonBounce.setPressed(z);
    }

    private void resetContactButtonsPressedState() {
        this.contactPressed = false;
        ButtonBounce buttonBounce = this.contactBounce;
        if (buttonBounce != null) {
            buttonBounce.setPressed(false);
        }
        Drawable drawable = this.selectorDrawable[0];
        if (drawable != null) {
            drawable.setState(StateSet.NOTHING);
        }
        if (this.contactButtons != null) {
            for (int i = 0; i < this.contactButtons.size(); i++) {
                InstantViewButton instantViewButton = (InstantViewButton) this.contactButtons.get(i);
                if (instantViewButton.buttonBounce != null) {
                    instantViewButton.buttonBounce.setPressed(false);
                }
                if (instantViewButton.selectorDrawable != null) {
                    instantViewButton.selectorDrawable.setState(StateSet.NOTHING);
                }
            }
        }
    }

    private boolean checkDateMotionEvent(MotionEvent motionEvent) {
        if (!this.currentMessageObject.isImportedForward()) {
            return false;
        }
        int eventX = (int) getEventX(motionEvent);
        int eventY = (int) getEventY(motionEvent);
        if (motionEvent.getAction() == 0) {
            float f = eventX;
            float f2 = this.drawTimeX;
            if (f >= f2 && f <= f2 + this.timeWidth) {
                float f3 = eventY;
                float f4 = this.drawTimeY;
                if (f3 >= f4 && f3 <= f4 + AndroidUtilities.m1146dp(20.0f)) {
                    this.timePressed = true;
                    invalidate();
                    return true;
                }
            }
        } else if (motionEvent.getAction() == 1 && this.timePressed) {
            this.timePressed = false;
            playSoundEffect(0);
            this.delegate.didPressTime(this);
            invalidate();
            return true;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0077  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean checkRoundSeekbar(android.view.MotionEvent r18) {
        /*
            Method dump skipped, instructions count: 406
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.checkRoundSeekbar(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x013e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean checkPhotoImageMotionEvent(android.view.MotionEvent r9) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 648
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.checkPhotoImageMotionEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x014d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean checkAudioMotionEvent(android.view.MotionEvent r13) {
        /*
            Method dump skipped, instructions count: 451
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.checkAudioMotionEvent(android.view.MotionEvent):boolean");
    }

    public boolean checkSpoilersMotionEvent(MotionEvent motionEvent, int i) {
        int i2;
        MessageObject.GroupedMessages groupedMessages;
        if (i <= 15 && getParent() != null) {
            if (this.currentMessageObject.hasValidGroupId() && (groupedMessages = this.currentMessagesGroup) != null && !groupedMessages.isDocuments) {
                ViewGroup viewGroup = (ViewGroup) getParent();
                for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                    View childAt = viewGroup.getChildAt(i3);
                    if (childAt instanceof ChatMessageCell) {
                        ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                        MessageObject.GroupedMessages currentMessagesGroup = chatMessageCell.getCurrentMessagesGroup();
                        MessageObject.GroupedMessagePosition currentPosition = chatMessageCell.getCurrentPosition();
                        if (currentMessagesGroup != null && currentMessagesGroup.groupId == this.currentMessagesGroup.groupId) {
                            int i4 = currentPosition.flags;
                            if ((i4 & 8) != 0 && (i4 & 1) != 0 && chatMessageCell != this) {
                                motionEvent.offsetLocation(getLeft() - chatMessageCell.getLeft(), getTop() - chatMessageCell.getTop());
                                boolean zCheckSpoilersMotionEvent = chatMessageCell.checkSpoilersMotionEvent(motionEvent, i + 1);
                                motionEvent.offsetLocation(-(getLeft() - chatMessageCell.getLeft()), -(getTop() - chatMessageCell.getTop()));
                                return zCheckSpoilersMotionEvent;
                            }
                        }
                    }
                }
            }
            if (this.isSpoilerRevealing) {
                return false;
            }
            int eventX = (int) getEventX(motionEvent);
            int eventY = (int) getEventY(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked == 0) {
                int i5 = this.textX;
                if (eventX >= i5 && eventY >= (i2 = this.textY)) {
                    MessageObject messageObject = this.currentMessageObject;
                    if (eventX <= i5 + messageObject.textWidth && eventY <= i2 + messageObject.textHeight(this.transitionParams)) {
                        ArrayList<MessageObject.TextLayoutBlock> arrayList = this.currentMessageObject.textLayoutBlocks;
                        for (int i6 = 0; i6 < arrayList.size() && arrayList.get(i6).textYOffset(this.currentMessageObject.textLayoutBlocks, this.transitionParams) <= eventY; i6++) {
                            MessageObject.TextLayoutBlock textLayoutBlock = arrayList.get(i6);
                            int i7 = textLayoutBlock.isRtl() ? (int) this.currentMessageObject.textXOffset : 0;
                            for (SpoilerEffect spoilerEffect : textLayoutBlock.spoilers) {
                                if (spoilerEffect.getBounds().contains((eventX - this.textX) + i7, (int) ((eventY - this.textY) - textLayoutBlock.textYOffset(this.currentMessageObject.textLayoutBlocks, this.transitionParams)))) {
                                    this.spoilerPressed = spoilerEffect;
                                    return true;
                                }
                            }
                        }
                    }
                }
                if (this.captionLayout != null) {
                    float f = eventX;
                    float f2 = this.captionX;
                    if (f >= f2) {
                        float f3 = eventY;
                        float f4 = this.captionY;
                        if (f3 >= f4 && f <= f2 + r1.textWidth && f3 <= f4 + r1.textHeight(this.transitionParams)) {
                            ArrayList<MessageObject.TextLayoutBlock> arrayList2 = this.captionLayout.textLayoutBlocks;
                            for (int i8 = 0; i8 < arrayList2.size() && arrayList2.get(i8).textYOffset(this.captionLayout.textLayoutBlocks, this.transitionParams) <= f3; i8++) {
                                MessageObject.TextLayoutBlock textLayoutBlock2 = arrayList2.get(i8);
                                int i9 = textLayoutBlock2.isRtl() ? (int) this.captionLayout.textXOffset : 0;
                                for (SpoilerEffect spoilerEffect2 : textLayoutBlock2.spoilers) {
                                    if (spoilerEffect2.getBounds().contains((int) ((f - this.captionX) + i9), (int) ((f3 - this.captionY) - textLayoutBlock2.textYOffset(this.captionLayout.textLayoutBlocks, this.transitionParams)))) {
                                        this.spoilerPressed = spoilerEffect2;
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (actionMasked == 1 && this.spoilerPressed != null) {
                playSoundEffect(0);
                this.sPath.rewind();
                MessageObject.TextLayoutBlocks textLayoutBlocks = this.captionLayout;
                if (textLayoutBlocks != null) {
                    ArrayList<MessageObject.TextLayoutBlock> arrayList3 = textLayoutBlocks.textLayoutBlocks;
                    int size = arrayList3.size();
                    int i10 = 0;
                    while (i10 < size) {
                        MessageObject.TextLayoutBlock textLayoutBlock3 = arrayList3.get(i10);
                        i10++;
                        MessageObject.TextLayoutBlock textLayoutBlock4 = textLayoutBlock3;
                        Iterator<SpoilerEffect> it = textLayoutBlock4.spoilers.iterator();
                        while (it.hasNext()) {
                            Rect bounds = it.next().getBounds();
                            this.sPath.addRect(bounds.left, bounds.top + textLayoutBlock4.textYOffset(this.captionLayout.textLayoutBlocks, this.transitionParams), bounds.right, bounds.bottom + textLayoutBlock4.textYOffset(this.captionLayout.textLayoutBlocks, this.transitionParams), Path.Direction.CW);
                        }
                    }
                } else {
                    ArrayList<MessageObject.TextLayoutBlock> arrayList4 = this.currentMessageObject.textLayoutBlocks;
                    int size2 = arrayList4.size();
                    int i11 = 0;
                    while (i11 < size2) {
                        MessageObject.TextLayoutBlock textLayoutBlock5 = arrayList4.get(i11);
                        i11++;
                        MessageObject.TextLayoutBlock textLayoutBlock6 = textLayoutBlock5;
                        Iterator<SpoilerEffect> it2 = textLayoutBlock6.spoilers.iterator();
                        while (it2.hasNext()) {
                            Rect bounds2 = it2.next().getBounds();
                            this.sPath.addRect(bounds2.left, bounds2.top + textLayoutBlock6.textYOffset(this.currentMessageObject.textLayoutBlocks, this.transitionParams), bounds2.right, textLayoutBlock6.textYOffset(this.currentMessageObject.textLayoutBlocks, this.transitionParams) + bounds2.bottom, Path.Direction.CW);
                        }
                    }
                }
                this.sPath.computeBounds(this.rect, false);
                float fSqrt = (float) Math.sqrt(Math.pow(this.rect.width(), 2.0d) + Math.pow(this.rect.height(), 2.0d));
                this.isSpoilerRevealing = true;
                this.spoilerPressed.setOnRippleEndCallback(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$checkSpoilersMotionEvent$1();
                    }
                });
                MessageObject.TextLayoutBlocks textLayoutBlocks2 = this.captionLayout;
                if (textLayoutBlocks2 != null) {
                    ArrayList<MessageObject.TextLayoutBlock> arrayList5 = textLayoutBlocks2.textLayoutBlocks;
                    int size3 = arrayList5.size();
                    int i12 = 0;
                    while (i12 < size3) {
                        MessageObject.TextLayoutBlock textLayoutBlock7 = arrayList5.get(i12);
                        i12++;
                        MessageObject.TextLayoutBlock textLayoutBlock8 = textLayoutBlock7;
                        int i13 = textLayoutBlock8.isRtl() ? (int) this.captionLayout.textXOffset : 0;
                        Iterator<SpoilerEffect> it3 = textLayoutBlock8.spoilers.iterator();
                        while (it3.hasNext()) {
                            it3.next().startRipple((eventX - this.captionX) + i13, (eventY - textLayoutBlock8.textYOffset(this.captionLayout.textLayoutBlocks, this.transitionParams)) - this.captionY, fSqrt);
                        }
                    }
                } else {
                    ArrayList<MessageObject.TextLayoutBlock> arrayList6 = this.currentMessageObject.textLayoutBlocks;
                    if (arrayList6 != null) {
                        int size4 = arrayList6.size();
                        int i14 = 0;
                        while (i14 < size4) {
                            MessageObject.TextLayoutBlock textLayoutBlock9 = arrayList6.get(i14);
                            i14++;
                            MessageObject.TextLayoutBlock textLayoutBlock10 = textLayoutBlock9;
                            int i15 = textLayoutBlock10.isRtl() ? (int) this.currentMessageObject.textXOffset : 0;
                            Iterator<SpoilerEffect> it4 = textLayoutBlock10.spoilers.iterator();
                            while (it4.hasNext()) {
                                it4.next().startRipple((eventX - this.textX) + i15, (eventY - textLayoutBlock10.textYOffset(this.currentMessageObject.textLayoutBlocks, this.transitionParams)) - this.textY, fSqrt);
                            }
                        }
                    }
                }
                if (getParent() instanceof RecyclerListView) {
                    ViewGroup viewGroup2 = (ViewGroup) getParent();
                    for (int i16 = 0; i16 < viewGroup2.getChildCount(); i16++) {
                        View childAt2 = viewGroup2.getChildAt(i16);
                        if (childAt2 instanceof ChatMessageCell) {
                            final ChatMessageCell chatMessageCell2 = (ChatMessageCell) childAt2;
                            if (chatMessageCell2.getMessageObject() != null && chatMessageCell2.getMessageObject().getReplyMsgId() == getMessageObject().getId() && !chatMessageCell2.replySpoilers.isEmpty()) {
                                ((SpoilerEffect) chatMessageCell2.replySpoilers.get(0)).setOnRippleEndCallback(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        this.f$0.lambda$checkSpoilersMotionEvent$3(chatMessageCell2);
                                    }
                                });
                                Iterator it5 = chatMessageCell2.replySpoilers.iterator();
                                while (it5.hasNext()) {
                                    ((SpoilerEffect) it5.next()).startRipple(r6.getBounds().centerX(), r6.getBounds().centerY(), fSqrt);
                                }
                            }
                        }
                    }
                }
                this.spoilerPressed = null;
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkSpoilersMotionEvent$1() {
        post(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkSpoilersMotionEvent$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkSpoilersMotionEvent$0() {
        int i = 0;
        this.isSpoilerRevealing = false;
        getMessageObject().isSpoilersRevealed = true;
        MessageObject.TextLayoutBlocks textLayoutBlocks = this.captionLayout;
        if (textLayoutBlocks != null) {
            ArrayList<MessageObject.TextLayoutBlock> arrayList = textLayoutBlocks.textLayoutBlocks;
            int size = arrayList.size();
            while (i < size) {
                MessageObject.TextLayoutBlock textLayoutBlock = arrayList.get(i);
                i++;
                textLayoutBlock.spoilers.clear();
            }
        } else {
            ArrayList<MessageObject.TextLayoutBlock> arrayList2 = this.currentMessageObject.textLayoutBlocks;
            if (arrayList2 != null) {
                int size2 = arrayList2.size();
                while (i < size2) {
                    MessageObject.TextLayoutBlock textLayoutBlock2 = arrayList2.get(i);
                    i++;
                    textLayoutBlock2.spoilers.clear();
                }
            }
        }
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkSpoilersMotionEvent$3(final ChatMessageCell chatMessageCell) {
        post(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                ChatMessageCell.$r8$lambda$fv64VxAXJYc74uexfw0I_K4wEos(this.f$0);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$fv64VxAXJYc74uexfw0I_K4wEos(ChatMessageCell chatMessageCell) {
        chatMessageCell.getMessageObject().replyMessageObject.isSpoilersRevealed = true;
        chatMessageCell.replySpoilers.clear();
        chatMessageCell.invalidate();
    }

    private boolean checkBotButtonMotionEvent(MotionEvent motionEvent) {
        int i;
        int iM1146dp;
        if (this.botButtons.isEmpty()) {
            return false;
        }
        int eventX = (int) getEventX(motionEvent);
        int eventY = (int) getEventY(motionEvent);
        if (motionEvent.getAction() == 0) {
            int widthForButtons = getWidthForButtons();
            if (this.currentMessageObject.isOutOwner()) {
                iM1146dp = (getMeasuredWidth() - widthForButtons) - AndroidUtilities.m1146dp(10.0f);
            } else {
                iM1146dp = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(this.mediaBackground ? 1.0f : 7.0f);
            }
            for (int i2 = 0; i2 < this.botButtons.size(); i2++) {
                BotButton botButton = (BotButton) this.botButtons.get(i2);
                int iM1146dp2 = (botButton.f1800y + this.layoutHeight) - AndroidUtilities.m1146dp(2.0f);
                float f = eventX;
                float f2 = botButton.f1799x;
                float f3 = widthForButtons;
                float f4 = iM1146dp;
                if (f >= (f2 * f3) + f4 && f <= (f2 * f3) + f4 + (botButton.width * f3) && eventY >= iM1146dp2 && eventY <= botButton.height + iM1146dp2) {
                    this.pressedBotButton = i2;
                    invalidateOutbounds();
                    if (botButton.selectorDrawable == null) {
                        Drawable drawableCreateRadSelectorDrawable = Theme.createRadSelectorDrawable(getThemedColor(Theme.key_chat_serviceBackgroundSelector), 6, 6);
                        botButton.selectorDrawable = drawableCreateRadSelectorDrawable;
                        drawableCreateRadSelectorDrawable.setCallback(this);
                        Drawable drawable = botButton.selectorDrawable;
                        float f5 = botButton.f1799x;
                        drawable.setBounds(((int) (f5 * f3)) + iM1146dp, iM1146dp2, ((int) (f5 * f3)) + iM1146dp + ((int) (botButton.width * f3)), botButton.height + iM1146dp2);
                    }
                    botButton.selectorDrawable.setHotspot(f, eventY);
                    botButton.selectorDrawable.setState(this.pressedState);
                    botButton.setPressed(!botButton.isLocked);
                    final int i3 = this.pressedBotButton;
                    postDelayed(new Runnable() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda8
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$checkBotButtonMotionEvent$4(i3);
                        }
                    }, ViewConfiguration.getLongPressTimeout() - 1);
                    return true;
                }
            }
        } else if (motionEvent.getAction() == 1) {
            if (this.pressedBotButton != -1) {
                playSoundEffect(0);
                BotButton botButton2 = (BotButton) this.botButtons.get(this.pressedBotButton);
                Drawable drawable2 = botButton2.selectorDrawable;
                if (drawable2 != null) {
                    drawable2.setState(StateSet.NOTHING);
                }
                botButton2.setPressed(false);
                if (this.currentMessageObject.scheduled) {
                    Toast.makeText(getContext(), LocaleController.getString(C2369R.string.MessageScheduledBotAction), 1).show();
                } else {
                    ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
                    if (chatMessageCellDelegate != null && !botButton2.isLocked) {
                        BotInlineKeyboard.ButtonCustom buttonCustom = botButton2.buttonCustom;
                        if (buttonCustom != null) {
                            chatMessageCellDelegate.didPressCustomBotButton(this, buttonCustom);
                        } else {
                            TLRPC.KeyboardButton keyboardButton = botButton2.button;
                            if (keyboardButton != null) {
                                chatMessageCellDelegate.didPressBotButton(this, keyboardButton);
                            }
                        }
                    }
                }
                this.pressedBotButton = -1;
                invalidateOutbounds();
                return false;
            }
        } else if (motionEvent.getAction() == 3 && (i = this.pressedBotButton) != -1) {
            BotButton botButton3 = (BotButton) this.botButtons.get(i);
            Drawable drawable3 = botButton3.selectorDrawable;
            if (drawable3 != null) {
                drawable3.setState(StateSet.NOTHING);
            }
            botButton3.setPressed(false);
            this.pressedBotButton = -1;
            invalidateOutbounds();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkBotButtonMotionEvent$4(int i) {
        int i2 = this.pressedBotButton;
        if (i == i2) {
            BotButton botButton = (BotButton) this.botButtons.get(i2);
            if (botButton != null) {
                Drawable drawable = botButton.selectorDrawable;
                if (drawable != null) {
                    drawable.setState(StateSet.NOTHING);
                }
                botButton.setPressed(false);
                if (!this.currentMessageObject.scheduled) {
                    if (botButton.buttonCustom != null) {
                        cancelCheckLongPress();
                        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
                        if (chatMessageCellDelegate != null) {
                            chatMessageCellDelegate.didLongPressCustomBotButton(this, botButton.buttonCustom);
                        }
                    } else if (botButton.button != null) {
                        cancelCheckLongPress();
                        ChatMessageCellDelegate chatMessageCellDelegate2 = this.delegate;
                        if (chatMessageCellDelegate2 != null) {
                            chatMessageCellDelegate2.didLongPressBotButton(this, botButton.button);
                        }
                    }
                }
            }
            this.pressedBotButton = -1;
            invalidateOutbounds();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x01b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean checkReplyTouchEvent(android.view.MotionEvent r14) {
        /*
            Method dump skipped, instructions count: 470
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.checkReplyTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkReplyTouchEvent$5() {
        if (this.replyPressed && !this.replySelectorPressed && this.replySelectorCanBePressed) {
            this.replySelectorPressed = true;
            this.replySelector.setState(new int[]{R.attr.state_pressed, R.attr.state_enabled});
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkReplyTouchEvent$6() {
        this.replySelector.setState(new int[0]);
        invalidate();
    }

    /* JADX WARN: Code restructure failed: missing block: B:395:0x054f, code lost:
    
        if (r6 <= (r0 + r21.forwardHeight)) goto L450;
     */
    /* JADX WARN: Removed duplicated region for block: B:183:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:354:0x04dc  */
    /* JADX WARN: Removed duplicated region for block: B:448:0x060d  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r22) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 1591
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private boolean checkReactionsTouchEvent(MotionEvent motionEvent) {
        MessageObject.GroupedMessages groupedMessages;
        if (this.currentMessageObject.hasValidGroupId() && (groupedMessages = this.currentMessagesGroup) != null && !groupedMessages.isDocuments) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            if (viewGroup == null) {
                return false;
            }
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childAt = viewGroup.getChildAt(i);
                if (childAt instanceof ChatMessageCell) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                    MessageObject.GroupedMessages currentMessagesGroup = chatMessageCell.getCurrentMessagesGroup();
                    MessageObject.GroupedMessagePosition currentPosition = chatMessageCell.getCurrentPosition();
                    if (currentMessagesGroup != null && currentMessagesGroup.groupId == this.currentMessagesGroup.groupId) {
                        int i2 = currentPosition.flags;
                        if ((i2 & 8) != 0 && (i2 & 1) != 0) {
                            if (chatMessageCell == this) {
                                return this.reactionsLayoutInBubble.checkTouchEvent(motionEvent);
                            }
                            motionEvent.offsetLocation(getLeft() - chatMessageCell.getLeft(), getTop() - chatMessageCell.getTop());
                            boolean zCheckTouchEvent = chatMessageCell.reactionsLayoutInBubble.checkTouchEvent(motionEvent);
                            motionEvent.offsetLocation(-(getLeft() - chatMessageCell.getLeft()), -(getTop() - chatMessageCell.getTop()));
                            return zCheckTouchEvent;
                        }
                    }
                }
            }
            return false;
        }
        return this.reactionsLayoutInBubble.checkTouchEvent(motionEvent);
    }

    private boolean checkPinchToZoom(MotionEvent motionEvent) {
        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
        PinchToZoomHelper pinchToZoomHelper = chatMessageCellDelegate == null ? null : chatMessageCellDelegate.getPinchToZoomHelper();
        if (this.currentMessageObject == null || !this.photoImage.hasNotThumb() || pinchToZoomHelper == null || ((this.currentMessageObject.isVideo() && !this.autoPlayingMedia) || this.isRoundVideo || ((this.currentMessageObject.isDocument() && !this.currentMessageObject.isGif()) || this.currentMessageObject.needDrawBluredPreview()))) {
            return false;
        }
        ImageReceiver imageReceiver = this.photoImage;
        MessageObject messageObject = this.currentMessageObject;
        SpoilerEffect2 spoilerEffect2 = this.mediaSpoilerEffect2;
        return pinchToZoomHelper.checkPinchToZoom(motionEvent, this, imageReceiver, null, null, messageObject, spoilerEffect2 == null ? 0 : spoilerEffect2.getAttachIndex(this));
    }

    private boolean checkTextSelection(MotionEvent motionEvent) {
        TextSelectionHelper.ChatListTextSelectionHelper textSelectionHelper;
        int i;
        int iM1146dp;
        int iM1146dp2;
        int iM1146dp3;
        int extraTextX;
        int iMax;
        MessageObject.GroupedMessages groupedMessages;
        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
        if (chatMessageCellDelegate == null || (textSelectionHelper = chatMessageCellDelegate.getTextSelectionHelper()) == null || textSelectionHelper.isMenuEmpty()) {
            return false;
        }
        ArrayList<MessageObject.TextLayoutBlock> arrayList = this.currentMessageObject.textLayoutBlocks;
        if ((arrayList == null || arrayList.isEmpty()) && !hasCaptionLayout()) {
            return false;
        }
        if ((!this.drawSelectionBackground && this.currentMessagesGroup == null) || (this.currentMessagesGroup != null && !this.delegate.hasSelectedMessages())) {
            return false;
        }
        if (this.currentMessageObject.hasValidGroupId() && (groupedMessages = this.currentMessagesGroup) != null && !groupedMessages.isDocuments) {
            ViewGroup viewGroup = (ViewGroup) getParent();
            if (viewGroup == null) {
                return false;
            }
            for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                View childAt = viewGroup.getChildAt(i2);
                if (childAt instanceof ChatMessageCell) {
                    ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                    MessageObject.GroupedMessages currentMessagesGroup = chatMessageCell.getCurrentMessagesGroup();
                    MessageObject.GroupedMessagePosition currentPosition = chatMessageCell.getCurrentPosition();
                    if (currentMessagesGroup != null && currentMessagesGroup.groupId == this.currentMessagesGroup.groupId && (currentPosition.flags & captionFlag()) != 0 && (currentPosition.flags & 1) != 0) {
                        textSelectionHelper.setMaybeTextCord((int) chatMessageCell.captionX, (int) chatMessageCell.captionY);
                        textSelectionHelper.setMessageObject(chatMessageCell);
                        if (chatMessageCell == this) {
                            return textSelectionHelper.onTouchEvent(motionEvent);
                        }
                        motionEvent.offsetLocation(getLeft() - chatMessageCell.getLeft(), getTop() - chatMessageCell.getTop());
                        boolean zOnTouchEvent = textSelectionHelper.onTouchEvent(motionEvent);
                        motionEvent.offsetLocation(-(getLeft() - chatMessageCell.getLeft()), -(getTop() - chatMessageCell.getTop()));
                        return zOnTouchEvent;
                    }
                }
            }
            return false;
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && !messageObject.preview && this.factCheckTextLayout != null && getEventY(motionEvent) >= this.factCheckY) {
            textSelectionHelper.setIsDescription(false);
            textSelectionHelper.setIsFactCheck(true);
            MessageObject.GroupedMessages groupedMessages2 = this.currentMessagesGroup;
            if (groupedMessages2 != null && !groupedMessages2.isDocuments) {
                iMax = (int) this.captionX;
            } else if (this.currentMessageObject.isOutOwner()) {
                int iM1146dp4 = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(12.0f) + getExtraTextX();
                iMax = this.currentMessageObject.type == 19 ? iM1146dp4 - Math.max(0, ((Math.max(this.replyNameWidth, this.replyTextWidth) + iM1146dp4) + AndroidUtilities.m1146dp(14.0f)) - AndroidUtilities.displaySize.x) : iM1146dp4;
            } else {
                if (this.mediaBackground) {
                    iM1146dp3 = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(12.0f);
                    extraTextX = getExtraTextX();
                } else {
                    iM1146dp3 = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(this.drawPinnedBottom ? 12.0f : 18.0f);
                    extraTextX = getExtraTextX();
                }
                iMax = iM1146dp3 + extraTextX;
            }
            textSelectionHelper.setMaybeTextCord(((((int) (iMax + this.transitionParams.deltaLeft)) - AndroidUtilities.m1146dp(1.33f)) + AndroidUtilities.m1146dp(10.0f)) - this.factCheckTextLayoutLeft, this.factCheckY + AndroidUtilities.m1146dp(22.0f));
        } else if (hasCaptionLayout()) {
            textSelectionHelper.setIsDescription(false);
            textSelectionHelper.setIsFactCheck(false);
            textSelectionHelper.setMaybeTextCord((int) this.captionX, (int) this.captionY);
        } else {
            MessageObject messageObject2 = this.currentMessageObject;
            if (messageObject2 != null && !messageObject2.preview && this.descriptionLayout != null && (!this.linkPreviewAbove ? getEventY(motionEvent) > this.descriptionY : getEventY(motionEvent) < this.textY)) {
                textSelectionHelper.setIsDescription(true);
                textSelectionHelper.setIsFactCheck(false);
                if (this.hasGamePreview) {
                    iM1146dp2 = this.unmovedTextX - AndroidUtilities.m1146dp(10.0f);
                } else {
                    if (this.hasInvoicePreview) {
                        i = this.unmovedTextX;
                        iM1146dp = AndroidUtilities.m1146dp(1.0f);
                    } else {
                        i = this.unmovedTextX;
                        iM1146dp = AndroidUtilities.m1146dp(1.0f);
                    }
                    iM1146dp2 = i + iM1146dp;
                }
                textSelectionHelper.setMaybeTextCord(iM1146dp2 + AndroidUtilities.m1146dp(10.0f) + this.descriptionX, this.descriptionY);
            } else {
                textSelectionHelper.setIsDescription(false);
                textSelectionHelper.setIsFactCheck(false);
                textSelectionHelper.setMaybeTextCord(this.textX, this.textY);
            }
        }
        textSelectionHelper.setMessageObject(this);
        return textSelectionHelper.onTouchEvent(motionEvent);
    }

    private void updateSelectionTextPosition() {
        int iM1146dp;
        int extraTextX;
        int iM1146dp2;
        int i;
        int iM1146dp3;
        int iM1146dp4;
        if (getDelegate() == null || getDelegate().getTextSelectionHelper() == null || !getDelegate().getTextSelectionHelper().isSelected(this.currentMessageObject)) {
            return;
        }
        int textSelectionType = getDelegate().getTextSelectionHelper().getTextSelectionType(this);
        if (textSelectionType == TextSelectionHelper.ChatListTextSelectionHelper.TYPE_DESCRIPTION) {
            if (this.hasGamePreview) {
                iM1146dp4 = this.unmovedTextX - AndroidUtilities.m1146dp(10.0f);
            } else {
                if (this.hasInvoicePreview) {
                    i = this.unmovedTextX;
                    iM1146dp3 = AndroidUtilities.m1146dp(1.0f);
                } else {
                    i = this.unmovedTextX;
                    iM1146dp3 = AndroidUtilities.m1146dp(1.0f);
                }
                iM1146dp4 = i + iM1146dp3;
            }
            getDelegate().getTextSelectionHelper().updateTextPosition(iM1146dp4 + AndroidUtilities.m1146dp(10.0f) + this.descriptionX, this.descriptionY);
            return;
        }
        if (textSelectionType == TextSelectionHelper.ChatListTextSelectionHelper.TYPE_CAPTION) {
            updateCaptionLayout();
            getDelegate().getTextSelectionHelper().updateTextPosition((int) this.captionX, (int) this.captionY);
            return;
        }
        if (textSelectionType == TextSelectionHelper.ChatListTextSelectionHelper.TYPE_FACTCHECK) {
            MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
            if (groupedMessages != null && !groupedMessages.isDocuments) {
                iM1146dp2 = (int) this.captionX;
            } else {
                if (this.currentMessageObject.isOutOwner()) {
                    iM1146dp2 = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(12.0f) + getExtraTextX();
                    if (this.currentMessageObject.type == 19) {
                        iM1146dp2 -= Math.max(0, ((Math.max(this.replyNameWidth, this.replyTextWidth) + iM1146dp2) + AndroidUtilities.m1146dp(14.0f)) - AndroidUtilities.displaySize.x);
                    }
                } else {
                    if (this.mediaBackground) {
                        iM1146dp = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(12.0f);
                        extraTextX = getExtraTextX();
                    } else {
                        iM1146dp = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(this.drawPinnedBottom ? 12.0f : 18.0f);
                        extraTextX = getExtraTextX();
                    }
                    iM1146dp2 = iM1146dp + extraTextX;
                }
            }
            int iM1146dp5 = ((int) (iM1146dp2 + this.transitionParams.deltaLeft)) - AndroidUtilities.m1146dp(1.33f);
            int i2 = this.factCheckY;
            updateCaptionLayout();
            getDelegate().getTextSelectionHelper().updateTextPosition((iM1146dp5 + AndroidUtilities.m1146dp(10.0f)) - this.factCheckTextLayoutLeft, i2 + AndroidUtilities.m1146dp(22.0f));
            return;
        }
        getDelegate().getTextSelectionHelper().updateTextPosition(this.textX, this.textY);
    }

    public ArrayList<PollButton> getPollButtons() {
        return this.pollButtons;
    }

    public void toggleTodoCheck(int i, boolean z) {
        if (i < 0 || i >= this.pollButtons.size()) {
            return;
        }
        PollButton pollButton = (PollButton) this.pollButtons.get(i);
        if (this.delegate.didPressToDoButton(this, pollButton.task, !pollButton.chosen)) {
            if (z) {
                try {
                    performHapticFeedback(3, 2);
                } catch (Exception unused) {
                }
            }
            long dialogId = this.currentMessageObject.getDialogId();
            long sendAsPeerId = ChatObject.getSendAsPeerId(MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(dialogId)), MessagesController.getInstance(this.currentAccount).getChatFull(dialogId), true);
            TLRPC.TL_messageMediaToDo tL_messageMediaToDo = (TLRPC.TL_messageMediaToDo) MessageObject.getMedia(this.currentMessageObject);
            MessageObject.toggleTodo(this.currentAccount, sendAsPeerId, tL_messageMediaToDo, pollButton.task.f1732id, !pollButton.chosen, ConnectionsManager.getInstance(this.currentAccount).getCurrentTime());
            if (!pollButton.chosen) {
                TLObject userOrChat = MessagesController.getInstance(this.currentAccount).getUserOrChat(sendAsPeerId);
                pollButton.avatarDrawable.setInfo(userOrChat);
                pollButton.avatarImageReceiver.setForUserOrChat(userOrChat, pollButton.avatarDrawable);
                pollButton.author = new Text(DialogObject.getName(userOrChat), 12.0f);
            }
            this.pollCheckBox[i].setChecked(!pollButton.chosen, true);
            if (this.animatedInfoLayout != null) {
                if (!this.currentMessageObject.isOutOwner() && this.currentMessageObject.getDialogId() >= 0 && !tL_messageMediaToDo.todo.others_can_complete) {
                    this.animatedInfoLayout.setText(LocaleController.formatPluralStringComma("TodoCompletedBy", tL_messageMediaToDo.todo.list.size(), Integer.valueOf(MessageObject.getCompletionsCount(tL_messageMediaToDo)), DialogObject.getName(this.currentMessageObject.getFromChatId())));
                } else {
                    this.animatedInfoLayout.setText(LocaleController.formatPluralStringComma("TodoCompleted", tL_messageMediaToDo.todo.list.size(), Integer.valueOf(MessageObject.getCompletionsCount(tL_messageMediaToDo))));
                }
            }
            pollButton.chosen = !pollButton.chosen;
            invalidate();
            return;
        }
        this.pollVoteInProgress = false;
    }

    public void syncTodoCheck(int i, ChatMessageCell chatMessageCell) {
        CheckBoxBase checkBoxBase;
        CheckBoxBase[] checkBoxBaseArr;
        CheckBoxBase checkBoxBase2;
        CheckBoxBase[] checkBoxBaseArr2 = this.pollCheckBox;
        if (checkBoxBaseArr2 == null || i < 0 || i >= checkBoxBaseArr2.length || (checkBoxBase = checkBoxBaseArr2[i]) == null || chatMessageCell == null || (checkBoxBaseArr = chatMessageCell.pollCheckBox) == null || i < 0 || i >= checkBoxBaseArr.length || (checkBoxBase2 = checkBoxBaseArr[i]) == null) {
            return;
        }
        checkBoxBase.cancelCheckAnimator();
        checkBoxBase.setProgress(checkBoxBase2.getProgress());
        checkBoxBase.setChecked(checkBoxBase2.isChecked(), true);
    }

    public void updatePlayingMessageProgress() {
        double d;
        double dMax;
        double currentProgressMs;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return;
        }
        OldVideoPlayerRewinder oldVideoPlayerRewinder = this.videoPlayerRewinder;
        if (oldVideoPlayerRewinder != null && oldVideoPlayerRewinder.rewindCount != 0 && oldVideoPlayerRewinder.rewindByBackSeek) {
            messageObject.audioProgress = oldVideoPlayerRewinder.getVideoProgress();
        }
        double d2 = 0.0d;
        if (this.documentAttachType == 4) {
            SeekBar seekBar = this.seekBar;
            if (seekBar != null) {
                seekBar.clearTimestamps();
            }
            if (this.infoLayout == null || !(PhotoViewer.isPlayingMessage(this.currentMessageObject) || MediaController.getInstance().isGoingToShowMessageObject(this.currentMessageObject))) {
                AnimatedFileDrawable animation = this.photoImage.getAnimation();
                if (animation != null) {
                    MessageObject messageObject2 = this.currentMessageObject;
                    int durationMs = animation.getDurationMs() / MediaDataController.MAX_STYLE_RUNS_COUNT;
                    messageObject2.audioPlayerDuration = durationMs;
                    currentProgressMs = durationMs;
                    MessageObject messageObject3 = this.currentMessageObject;
                    TLRPC.Message message = messageObject3.messageOwner;
                    if (message.ttl > 0 && message.destroyTime == 0 && !messageObject3.needDrawBluredPreview() && this.currentMessageObject.isVideo() && animation.hasBitmap()) {
                        this.delegate.didStartVideoStream(this.currentMessageObject);
                    }
                } else {
                    currentProgressMs = 0.0d;
                }
                if (currentProgressMs == 0.0d) {
                    currentProgressMs = this.currentMessageObject.getDuration();
                }
                if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                    currentProgressMs -= this.currentMessageObject.audioProgress * currentProgressMs;
                } else if (animation != null) {
                    if (currentProgressMs != 0.0d) {
                        currentProgressMs -= animation.getCurrentProgressMs() / MediaDataController.MAX_STYLE_RUNS_COUNT;
                    }
                    if (this.delegate != null && animation.getCurrentProgressMs() >= 3000) {
                        this.delegate.videoTimerReached();
                    }
                }
                if (this.lastTime != currentProgressMs) {
                    String shortDuration = AndroidUtilities.formatShortDuration((int) currentProgressMs);
                    this.infoWidth = (int) Math.ceil(Theme.chat_infoPaint.measureText(shortDuration));
                    this.infoLayout = new StaticLayout(shortDuration, Theme.chat_infoPaint, this.infoWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                    this.lastTime = currentProgressMs;
                    return;
                }
                return;
            }
            return;
        }
        if (this.isRoundVideo) {
            if (this.useSeekBarWaveform) {
                if (!this.seekBarWaveform.isDragging()) {
                    this.seekBarWaveform.setProgress(this.currentMessageObject.audioProgress, true);
                }
            } else {
                if (!this.seekBar.isDragging()) {
                    this.seekBar.setProgress(this.currentMessageObject.audioProgress);
                    this.seekBar.setBufferedProgress(this.currentMessageObject.bufferedProgress);
                }
                this.seekBar.clearTimestamps();
            }
            TLRPC.Document document = this.currentMessageObject.getDocument();
            if (document != null) {
                while (i < document.attributes.size()) {
                    TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i);
                    if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                        dMax = documentAttribute.duration;
                        break;
                    }
                    i++;
                }
                dMax = 0.0d;
            } else {
                dMax = 0.0d;
            }
            long j = this.overridenDuration;
            if (j >= 0) {
                dMax = j;
            } else if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                dMax = Math.max(0.0d, dMax - this.currentMessageObject.audioProgressSec);
            }
            if (this.lastTime != dMax) {
                this.lastTime = dMax;
                String longDuration = AndroidUtilities.formatLongDuration((int) dMax);
                this.timeWidthAudio = (int) Math.ceil(Theme.chat_timePaint.measureText(longDuration));
                this.durationLayout = new StaticLayout(longDuration, Theme.chat_timePaint, this.timeWidthAudio, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            }
            float f = this.currentMessageObject.audioProgress;
            if (f != 0.0f) {
                this.lastDrawingAudioProgress = f;
                if (f > 0.9f) {
                    this.lastDrawingAudioProgress = 1.0f;
                }
            }
            invalidate();
            return;
        }
        if (this.documentAttach != null) {
            if (this.useSeekBarWaveform) {
                if (!this.seekBarWaveform.isDragging()) {
                    this.seekBarWaveform.setProgress(this.currentMessageObject.audioProgress, true);
                }
            } else {
                if (!this.seekBar.isDragging()) {
                    this.seekBar.setProgress(this.currentMessageObject.audioProgress);
                    this.seekBar.setBufferedProgress(this.currentMessageObject.bufferedProgress);
                }
                this.seekBar.updateTimestamps(this.currentMessageObject, null);
            }
            if (this.documentAttachType == 3) {
                long j2 = this.overridenDuration;
                if (j2 >= 0) {
                    d = j2;
                } else if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                    d = this.currentMessageObject.audioProgressSec;
                } else {
                    while (true) {
                        if (i >= this.documentAttach.attributes.size()) {
                            break;
                        }
                        TLRPC.DocumentAttribute documentAttribute2 = this.documentAttach.attributes.get(i);
                        if (documentAttribute2 instanceof TLRPC.TL_documentAttributeAudio) {
                            d2 = documentAttribute2.duration;
                            break;
                        }
                        i++;
                    }
                    d = d2;
                }
                if (this.lastTime != d) {
                    this.lastTime = d;
                    String longDuration2 = AndroidUtilities.formatLongDuration((int) d);
                    this.timeWidthAudio = (int) Math.ceil(Theme.chat_audioTimePaint.measureText(longDuration2));
                    this.durationLayout = new StaticLayout(longDuration2, Theme.chat_audioTimePaint, this.timeWidthAudio, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                }
            } else {
                double duration = this.currentMessageObject.getDuration();
                i = MediaController.getInstance().isPlayingMessage(this.currentMessageObject) ? this.currentMessageObject.audioProgressSec : 0;
                double d3 = i;
                if (this.lastTime != d3) {
                    this.lastTime = d3;
                    this.durationLayout = new StaticLayout(AndroidUtilities.formatShortDuration(i, (int) duration), Theme.chat_audioTimePaint, (int) Math.ceil(Theme.chat_audioTimePaint.measureText(r3)), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                }
            }
            invalidate();
        }
    }

    public void overrideDuration(long j) {
        this.overridenDuration = j;
    }

    public void setFullyDraw(boolean z) {
        this.fullyDraw = z;
    }

    public void setParentViewSize(int i, int i2) {
        Theme.MessageDrawable messageDrawable;
        this.parentWidth = i;
        this.parentHeight = i2;
        this.backgroundHeight = i2;
        if (!(this.currentMessageObject != null && hasGradientService() && this.currentMessageObject.shouldDrawWithoutBackground()) && ((messageDrawable = this.currentBackgroundDrawable) == null || messageDrawable.getGradientShader() == null)) {
            return;
        }
        invalidate();
    }

    public void copyVisiblePartTo(ChatMessageCell chatMessageCell) {
        if (chatMessageCell == null) {
            return;
        }
        chatMessageCell.setVisiblePart(this.childPosition, this.visibleHeight, this.visibleParent, this.visibleParentOffset, this.visibleTop, this.parentWidth, this.parentHeight, this.blurredViewTopOffset, this.blurredViewBottomOffset);
    }

    public void copyParamsTo(ChatMessageCell chatMessageCell) {
        if (chatMessageCell == null) {
            return;
        }
        chatMessageCell.isChat = this.isChat;
        chatMessageCell.isReportChat = this.isReportChat;
        chatMessageCell.isSavedChat = this.isSavedChat;
        chatMessageCell.isSavedPreviewChat = this.isSavedPreviewChat;
        chatMessageCell.isBot = this.isBot;
        chatMessageCell.isMegagroup = this.isMegagroup;
        chatMessageCell.isForum = this.isForum;
        chatMessageCell.isMonoForum = this.isMonoForum;
        chatMessageCell.isForumGeneral = this.isForumGeneral;
        chatMessageCell.isThreadChat = this.isThreadChat;
        chatMessageCell.hasDiscussion = this.hasDiscussion;
        chatMessageCell.isPinned = this.isPinned;
        chatMessageCell.linkedChatId = this.linkedChatId;
        chatMessageCell.isRepliesChat = this.isRepliesChat;
        chatMessageCell.isPinnedChat = this.isPinnedChat;
        chatMessageCell.isAllChats = this.isAllChats;
        chatMessageCell.isSideMenued = this.isSideMenued;
        chatMessageCell.isSideMenuEnabled = this.isSideMenuEnabled;
        chatMessageCell.sideMenuAlpha = this.sideMenuAlpha;
        chatMessageCell.sideMenuWidth = this.sideMenuWidth;
    }

    public void setVisiblePart(int i, int i2, int i3, float f, float f2, int i4, int i5, int i6, int i7) {
        MessageObject messageObject;
        this.childPosition = i;
        this.visibleHeight = i2;
        this.visibleParent = i3;
        this.parentWidth = i4;
        this.parentHeight = i5;
        this.visibleTop = f2;
        this.visibleParentOffset = f;
        this.backgroundHeight = i5;
        this.blurredViewTopOffset = i6;
        this.blurredViewBottomOffset = i7;
        if ((!this.botButtons.isEmpty() || (this.channelRecommendationsCell != null && (messageObject = this.currentMessageObject) != null && messageObject.type == 27)) && this.viewTop != f2) {
            invalidate();
        }
        this.viewTop = f2;
        if (i3 != this.parentHeight || f != this.parentViewTopOffset) {
            this.parentViewTopOffset = f;
            this.parentHeight = i3;
        }
        if (this.currentMessageObject != null && hasGradientService() && this.currentMessageObject.shouldDrawWithoutBackground()) {
            invalidate();
        }
        MessageObject messageObject2 = this.currentMessageObject;
        if (messageObject2 != null && messageObject2.textLayoutBlocks != null) {
            int i8 = i - this.textY;
            int i9 = 0;
            for (int i10 = 0; i10 < this.currentMessageObject.textLayoutBlocks.size() && this.currentMessageObject.textLayoutBlocks.get(i10).textYOffset(this.currentMessageObject.textLayoutBlocks, this.transitionParams) <= i8; i10++) {
                i9 = i10;
            }
            int i11 = -1;
            int i12 = -1;
            int i13 = 0;
            while (i9 < this.currentMessageObject.textLayoutBlocks.size()) {
                float fTextYOffset = this.currentMessageObject.textLayoutBlocks.get(i9).textYOffset(this.currentMessageObject.textLayoutBlocks, this.transitionParams);
                float fHeight = r13.padTop + fTextYOffset + r13.height(this.transitionParams) + r13.padBottom;
                float f3 = i8;
                if (!intersect(fTextYOffset, fHeight, f3, i8 + i2)) {
                    if (fTextYOffset > f3) {
                        break;
                    }
                } else {
                    if (i11 == -1) {
                        i11 = i9;
                    }
                    i13++;
                    i12 = i9;
                }
                i9++;
            }
            if (this.lastVisibleBlockNum != i12 || this.firstVisibleBlockNum != i11 || this.totalVisibleBlocksCount != i13) {
                this.lastVisibleBlockNum = i12;
                this.firstVisibleBlockNum = i11;
                this.totalVisibleBlocksCount = i13;
                invalidate();
            } else if (this.animatedEmojiStack != null) {
                int i14 = 0;
                while (true) {
                    if (i14 >= this.animatedEmojiStack.holders.size()) {
                        break;
                    }
                    AnimatedEmojiSpan.AnimatedEmojiHolder animatedEmojiHolder = (AnimatedEmojiSpan.AnimatedEmojiHolder) this.animatedEmojiStack.holders.get(i14);
                    if (animatedEmojiHolder != null && animatedEmojiHolder.skipDraw && !animatedEmojiHolder.outOfBounds((this.parentBoundsTop - getY()) - animatedEmojiHolder.drawingYOffset, (this.parentBoundsBottom - getY()) - animatedEmojiHolder.drawingYOffset)) {
                        invalidate();
                        break;
                    }
                    i14++;
                }
            }
        }
        if (!this.pollButtons.isEmpty()) {
            int i15 = -1;
            int i16 = -1;
            for (int i17 = 0; i17 < this.pollButtons.size(); i17++) {
                PollButton pollButton = (PollButton) this.pollButtons.get(i17);
                int i18 = pollButton.f1806y;
                int i19 = this.namesOffset;
                if (intersect(i18 + i19, i18 + i19 + pollButton.height, this.childPosition, r10 + this.visibleHeight)) {
                    if (i16 == -1) {
                        i16 = i17;
                    }
                    i15 = i17;
                }
            }
            if (this.lastVisiblePollButton != i15 || this.firstVisiblePollButton != i16) {
                this.lastVisiblePollButton = i15;
                this.firstVisiblePollButton = i16;
                invalidate();
            }
        }
        ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
        int i20 = reactionsLayoutInBubble.f1967y;
        boolean zIntersect = intersect(i20, i20 + reactionsLayoutInBubble.height, this.childPosition, r6 + this.visibleHeight);
        if (this.reactionsVisible != zIntersect) {
            this.reactionsVisible = zIntersect;
            invalidate();
        }
    }

    public static StaticLayout generateStaticLayout(CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3, int i4) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        StaticLayout staticLayout = new StaticLayout(charSequence, textPaint, i2, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        int i5 = 0;
        for (int i6 = 0; i6 < i3; i6++) {
            staticLayout.getLineDirections(i6);
            if (staticLayout.getLineLeft(i6) != 0.0f || staticLayout.isRtlCharAt(staticLayout.getLineStart(i6)) || staticLayout.isRtlCharAt(staticLayout.getLineEnd(i6))) {
                i = i2;
            }
            int lineEnd = staticLayout.getLineEnd(i6);
            if (lineEnd == charSequence.length()) {
                break;
            }
            int i7 = (lineEnd - 1) + i5;
            if (spannableStringBuilder.charAt(i7) == ' ') {
                spannableStringBuilder.replace(i7, i7 + 1, (CharSequence) "\n");
            } else if (spannableStringBuilder.charAt(i7) != '\n') {
                spannableStringBuilder.insert(i7, (CharSequence) "\n");
                i5++;
            }
            if (i6 == staticLayout.getLineCount() - 1 || i6 == i4 - 1) {
                break;
            }
        }
        int i8 = i;
        return StaticLayoutEx.createStaticLayout(spannableStringBuilder, textPaint, i8, Layout.Alignment.ALIGN_NORMAL, 1.0f, AndroidUtilities.m1146dp(1.0f), false, TextUtils.TruncateAt.END, i8, i4, true);
    }

    private void didClickedImage() throws Resources.NotFoundException {
        MessageObject messageObject;
        ChatMessageCellDelegate chatMessageCellDelegate;
        TLRPC.WebPage webPage;
        TLRPC.MessageMedia messageMedia;
        TLRPC.ReplyMarkup replyMarkup;
        if (this.currentMessageObject.hasMediaSpoilers() && !this.currentMessageObject.needDrawBluredPreview()) {
            MessageObject messageObject2 = this.currentMessageObject;
            if (!messageObject2.isMediaSpoilersRevealed) {
                if (this.delegate != null && messageObject2.isSensitive()) {
                    this.delegate.didPressRevealSensitiveContent(this);
                    return;
                } else {
                    startRevealMedia(this.lastTouchX, this.lastTouchY);
                    return;
                }
            }
        }
        MessageObject messageObject3 = this.currentMessageObject;
        int i = messageObject3.type;
        int i2 = 0;
        if (i == 20) {
            TLRPC.Message message = messageObject3.messageOwner;
            if (message != null && (messageMedia = message.media) != null && !messageMedia.extended_media.isEmpty() && (replyMarkup = this.currentMessageObject.messageOwner.reply_markup) != null) {
                ArrayList arrayList = replyMarkup.rows;
                int size = arrayList.size();
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    Iterator it = ((TLRPC.TL_keyboardButtonRow) obj).buttons.iterator();
                    if (it.hasNext()) {
                        this.delegate.didPressExtendedMediaPreview(this, (TLRPC.KeyboardButton) it.next());
                        return;
                    }
                }
            }
            return;
        }
        if (i == 1 || messageObject3.isAnyKindOfSticker()) {
            int i3 = this.buttonState;
            if (i3 == -1) {
                this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY, false);
                return;
            } else {
                if (i3 == 0) {
                    didPressButton(true, false);
                    return;
                }
                return;
            }
        }
        MessageObject messageObject4 = this.currentMessageObject;
        int i4 = messageObject4.type;
        if (i4 == 12) {
            long j = MessageObject.getMedia(messageObject4.messageOwner).user_id;
            this.delegate.didPressUserAvatar(this, j != 0 ? MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(j)) : null, this.lastTouchX, this.lastTouchY, false);
            return;
        }
        if (i4 == 5) {
            if (this.buttonState != -1) {
                didPressButton(true, false);
                return;
            } else if (!MediaController.getInstance().isPlayingMessage(this.currentMessageObject) || MediaController.getInstance().isMessagePaused()) {
                this.delegate.needPlayMessage(this, this.currentMessageObject, false);
                return;
            } else {
                MediaController.getInstance().lambda$startAudioAgain$7(this.currentMessageObject);
                return;
            }
        }
        if (i4 == 8) {
            int i5 = this.buttonState;
            if (i5 == -1 || (i5 == 1 && this.canStreamVideo && this.autoPlayingMedia)) {
                this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY, false);
                return;
            } else {
                if (i5 == 2 || i5 == 0) {
                    didPressButton(true, false);
                    return;
                }
                return;
            }
        }
        if (this.documentAttachType == 4 || messageObject4.hasVideoQualities()) {
            if (this.buttonState == -1 || (this.drawVideoImageButton && (this.autoPlayingMedia || (((messageObject = this.currentMessageObject) != null && messageObject.hasVideoQualities()) || (SharedConfig.streamMedia && this.canStreamVideo))))) {
                this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY, false);
                return;
            }
            if (this.drawVideoImageButton) {
                didPressButton(true, true);
                return;
            }
            int i6 = this.buttonState;
            if (i6 == 0 || i6 == 3) {
                didPressButton(true, false);
                return;
            }
            return;
        }
        MessageObject messageObject5 = this.currentMessageObject;
        int i7 = messageObject5.type;
        if (i7 == 4 || i7 == 23 || i7 == 24) {
            this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY, false);
            return;
        }
        int i8 = this.documentAttachType;
        if (i8 == 1) {
            if (this.buttonState == -1) {
                this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY, false);
                return;
            }
            return;
        }
        if (messageObject5.sponsoredMedia != null) {
            ChatMessageCellDelegate chatMessageCellDelegate2 = this.delegate;
            if (chatMessageCellDelegate2 != null) {
                chatMessageCellDelegate2.didPressImage(this, this.lastTouchX, this.lastTouchY, false);
                return;
            }
            return;
        }
        if (i8 == 2) {
            if (this.buttonState != -1 || (webPage = MessageObject.getMedia(messageObject5.messageOwner).webpage) == null) {
                return;
            }
            String str = webPage.embed_url;
            if (str != null && str.length() != 0) {
                this.delegate.needOpenWebView(this.currentMessageObject, webPage.embed_url, webPage.site_name, webPage.description, webPage.url, webPage.embed_width, webPage.embed_height);
                return;
            } else {
                Browser.openUrl(getContext(), webPage.url);
                return;
            }
        }
        if (this.hasInvoicePreview) {
            if (this.buttonState == -1) {
                this.delegate.didPressImage(this, this.lastTouchX, this.lastTouchY, false);
            }
        } else {
            if (Build.VERSION.SDK_INT < 26 || (chatMessageCellDelegate = this.delegate) == null) {
                return;
            }
            if (i7 == 16) {
                chatMessageCellDelegate.didLongPress(this, 0.0f, 0.0f);
            } else {
                chatMessageCellDelegate.didPressOther(this, this.otherX, this.otherY);
            }
        }
    }

    private void updateSecretTimeText(MessageObject messageObject) {
        CharSequence secretTimeString;
        if (messageObject == null || !messageObject.needDrawBluredPreview() || (secretTimeString = messageObject.getSecretTimeString()) == null) {
            return;
        }
        if (secretTimeString instanceof String) {
            int iCeil = (int) Math.ceil(Theme.chat_infoPaint.measureText((String) secretTimeString));
            this.infoWidth = iCeil;
            this.infoLayout = new StaticLayout(TextUtils.ellipsize(secretTimeString, Theme.chat_infoPaint, iCeil, TextUtils.TruncateAt.END), Theme.chat_infoPaint, this.infoWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        } else {
            StaticLayout staticLayout = new StaticLayout(secretTimeString, Theme.chat_infoBoldPaint, getMeasuredWidth() > 0 ? getMeasuredWidth() : 9999, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            this.infoLayout = staticLayout;
            this.infoWidth = staticLayout.getLineCount() > 0 ? (int) this.infoLayout.getLineWidth(0) : 0;
        }
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean isPhotoDataChanged(org.telegram.messenger.MessageObject r24) {
        /*
            Method dump skipped, instructions count: 333
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.isPhotoDataChanged(org.telegram.messenger.MessageObject):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRepliesCount() {
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null && !groupedMessages.messages.isEmpty()) {
            return this.currentMessagesGroup.messages.get(0).getRepliesCount();
        }
        return this.currentMessageObject.getRepliesCount();
    }

    private ArrayList<TLRPC.Peer> getRecentRepliers() {
        TLRPC.MessageReplies messageReplies;
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null && !groupedMessages.messages.isEmpty() && (messageReplies = this.currentMessagesGroup.messages.get(0).messageOwner.replies) != null) {
            return messageReplies.recent_repliers;
        }
        TLRPC.MessageReplies messageReplies2 = this.currentMessageObject.messageOwner.replies;
        if (messageReplies2 != null) {
            return messageReplies2.recent_repliers;
        }
        return null;
    }

    public void updateAnimatedEmojis() {
        MessageObject messageObject;
        ArrayList<MessageObject.TextLayoutBlock> arrayList;
        if (!this.imageReceiversAttachState || (messageObject = this.currentMessageObject) == null) {
            return;
        }
        int cacheTypeForEnterView = messageObject.wasJustSent ? AnimatedEmojiDrawable.getCacheTypeForEnterView() : 0;
        MessageObject.TextLayoutBlocks textLayoutBlocks = this.captionLayout;
        if (textLayoutBlocks != null && (arrayList = textLayoutBlocks.textLayoutBlocks) != null) {
            this.animatedEmojiStack = AnimatedEmojiSpan.update(cacheTypeForEnterView, (View) this, false, this.animatedEmojiStack, arrayList);
        } else {
            ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
            this.animatedEmojiStack = AnimatedEmojiSpan.update(cacheTypeForEnterView, this, chatMessageCellDelegate == null || !chatMessageCellDelegate.canDrawOutboundsContent(), this.animatedEmojiStack, this.currentMessageObject.textLayoutBlocks);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x00da  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean isUserDataChanged() {
        /*
            Method dump skipped, instructions count: 279
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.isUserDataChanged():boolean");
    }

    public ImageReceiver getPhotoImage() {
        return this.photoImage;
    }

    public ImageReceiver getPhotoImage(int i) {
        GroupMedia groupMedia = this.groupMedia;
        if (groupMedia != null) {
            return groupMedia.getPhotoImage(i);
        }
        return this.photoImage;
    }

    public ImageReceiver getBlurredPhotoImage() {
        return this.blurredPhotoImage;
    }

    public int getNoSoundIconCenterX() {
        return this.noSoundCenterX;
    }

    public int getForwardNameCenterX() {
        float centerX;
        TLRPC.User user = this.currentUser;
        if (user != null && user.f1734id == 0) {
            centerX = this.avatarImage.getCenterX();
        } else {
            centerX = this.forwardNameX + this.forwardNameCenterX;
        }
        return (int) centerX;
    }

    public int getChecksX() {
        return this.layoutWidth - AndroidUtilities.m1146dp(SharedConfig.bubbleRadius >= 10 ? 27.3f : 25.3f);
    }

    public int getChecksY() {
        float f;
        int intrinsicHeight;
        if (this.currentMessageObject.shouldDrawWithoutBackground()) {
            f = this.drawTimeY;
            intrinsicHeight = getThemedDrawable("drawableMsgStickerCheck").getIntrinsicHeight();
        } else {
            f = this.drawTimeY;
            intrinsicHeight = Theme.chat_msgMediaCheckDrawable.getIntrinsicHeight();
        }
        return (int) (f - intrinsicHeight);
    }

    public void overrideAudioVisualizer(AudioVisualizerDrawable audioVisualizerDrawable) {
        this.overridenAudioVisualizer = audioVisualizerDrawable;
    }

    public TLRPC.User getCurrentUser() {
        return this.currentUser;
    }

    public TLRPC.Chat getCurrentChat() {
        return this.currentChat;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.startSpoilers);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.stopSpoilers);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didUpdatePremiumGiftStickers);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.userInfoDidLoad);
        cancelShakeAnimation();
        CheckBoxBase checkBoxBase = this.checkBox;
        if (checkBoxBase != null) {
            checkBoxBase.onDetachedFromWindow();
        }
        ArrayList arrayList = this.pollButtons;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((PollButton) obj).detach();
        }
        AvatarsDrawable avatarsDrawable = this.groupCallParticipantsAvatars;
        if (avatarsDrawable != null) {
            avatarsDrawable.onDetachedFromWindow();
        }
        CheckBoxBase checkBoxBase2 = this.mediaCheckBox;
        if (checkBoxBase2 != null) {
            checkBoxBase2.onDetachedFromWindow();
        }
        if (this.pollCheckBox != null) {
            int i2 = 0;
            while (true) {
                CheckBoxBase[] checkBoxBaseArr = this.pollCheckBox;
                if (i2 >= checkBoxBaseArr.length) {
                    break;
                }
                checkBoxBaseArr[i2].onDetachedFromWindow();
                i2++;
            }
        }
        CheckBoxBase checkBoxBase3 = this.replyTaskCheckbox;
        if (checkBoxBase3 != null) {
            checkBoxBase3.onDetachedFromWindow();
        }
        this.attachedToWindow = false;
        GroupMedia groupMedia = this.groupMedia;
        if (groupMedia != null) {
            groupMedia.onDetachedFromWindow();
        }
        this.avatarImage.onDetachedFromWindow();
        ImageReceiver imageReceiver = this.sideImage;
        if (imageReceiver != null) {
            imageReceiver.onDetachedFromWindow();
        }
        TopicSeparator topicSeparator = this.topicSeparator;
        if (topicSeparator != null) {
            topicSeparator.detach();
        }
        checkImageReceiversAttachState();
        if (this.addedForTest && this.currentUrl != null && this.currentWebFile != null) {
            ImageLoader.getInstance().removeTestWebFile(this.currentUrl);
            this.addedForTest = false;
        }
        StickerSetLinkIcon stickerSetLinkIcon = this.stickerSetIcons;
        if (stickerSetLinkIcon != null) {
            stickerSetLinkIcon.detach(this);
        }
        DownloadController.getInstance(this.currentAccount).removeLoadingFileObserver(this);
        if (getDelegate() != null && getDelegate().getTextSelectionHelper() != null) {
            getDelegate().getTextSelectionHelper().onChatMessageCellDetached(this);
        }
        this.transitionParams.onDetach();
        if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
            Theme.getCurrentAudiVisualizerDrawable().setParentView(null);
        }
        ValueAnimator valueAnimator = this.statusDrawableAnimator;
        if (valueAnimator != null) {
            valueAnimator.removeAllListeners();
            this.statusDrawableAnimator.cancel();
        }
        this.reactionsLayoutInBubble.onDetachFromWindow();
        this.statusDrawableAnimationInProgress = false;
        FlagSecureReason flagSecureReason = this.flagSecure;
        if (flagSecureReason != null) {
            flagSecureReason.detach();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.currentNameStatusDrawable;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.detach();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = this.currentNameEmojiStatusDrawable;
        if (swapAnimatedEmojiDrawable2 != null) {
            swapAnimatedEmojiDrawable2.detach();
        }
        SpoilerEffect2 spoilerEffect2 = this.mediaSpoilerEffect2;
        if (spoilerEffect2 != null) {
            spoilerEffect2.detach(this);
        }
        ChannelRecommendationsCell channelRecommendationsCell = this.channelRecommendationsCell;
        if (channelRecommendationsCell != null) {
            channelRecommendationsCell.onDetachedFromWindow();
        }
        FrameTickScheduler.unsubscribe(this.invalidateOutboundsRunnable);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() throws Resources.NotFoundException {
        ChatMessageCell chatMessageCell;
        super.onAttachedToWindow();
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.startSpoilers);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.stopSpoilers);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didUpdatePremiumGiftStickers);
        NotificationCenter.getInstance(this.currentAccount).addObserver(this, NotificationCenter.userInfoDidLoad);
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null) {
            messageObject.animateComments = false;
        }
        AvatarsDrawable avatarsDrawable = this.groupCallParticipantsAvatars;
        if (avatarsDrawable != null) {
            avatarsDrawable.onAttachedToWindow();
        }
        ArrayList arrayList = this.pollButtons;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((PollButton) obj).attach();
        }
        MessageObject messageObject2 = this.messageObjectToSet;
        if (messageObject2 != null) {
            messageObject2.animateComments = false;
            chatMessageCell = this;
            chatMessageCell.setMessageContent(messageObject2, this.groupedMessagesToSet, this.bottomNearToSet, this.topNearToSet, this.firstInChatToSet, this.lastInChatListToSet);
            chatMessageCell.messageObjectToSet = null;
            chatMessageCell.groupedMessagesToSet = null;
        } else {
            chatMessageCell = this;
        }
        CheckBoxBase checkBoxBase = chatMessageCell.checkBox;
        if (checkBoxBase != null) {
            checkBoxBase.onAttachedToWindow();
        }
        CheckBoxBase checkBoxBase2 = chatMessageCell.mediaCheckBox;
        if (checkBoxBase2 != null) {
            checkBoxBase2.onAttachedToWindow();
        }
        TopicSeparator topicSeparator = chatMessageCell.topicSeparator;
        if (topicSeparator != null) {
            topicSeparator.attach();
        }
        CheckBoxBase checkBoxBase3 = chatMessageCell.replyTaskCheckbox;
        if (checkBoxBase3 != null) {
            checkBoxBase3.onAttachedToWindow();
        }
        if (chatMessageCell.pollCheckBox != null) {
            int i2 = 0;
            while (true) {
                CheckBoxBase[] checkBoxBaseArr = chatMessageCell.pollCheckBox;
                if (i2 >= checkBoxBaseArr.length) {
                    break;
                }
                checkBoxBaseArr[i2].onAttachedToWindow();
                i2++;
            }
        }
        chatMessageCell.attachedToWindow = true;
        chatMessageCell.animationOffsetX = 0.0f;
        chatMessageCell.slidingOffsetX = 0.0f;
        chatMessageCell.checkBoxTranslation = 0;
        updateTranslation();
        chatMessageCell.avatarImage.setParentView((View) getParent());
        chatMessageCell.avatarImage.onAttachedToWindow();
        ImageReceiver imageReceiver = chatMessageCell.sideImage;
        if (imageReceiver != null) {
            imageReceiver.onAttachedToWindow();
        }
        checkImageReceiversAttachState();
        MessageObject messageObject3 = chatMessageCell.currentMessageObject;
        if (messageObject3 != null) {
            setAvatar(messageObject3);
        }
        int i3 = chatMessageCell.documentAttachType;
        if (i3 == 4 && chatMessageCell.autoPlayingMedia) {
            boolean zIsPlayingMessage = MediaController.getInstance().isPlayingMessage(chatMessageCell.currentMessageObject);
            chatMessageCell.animatingNoSoundPlaying = zIsPlayingMessage;
            chatMessageCell.animatingNoSoundProgress = zIsPlayingMessage ? 0.0f : 1.0f;
            chatMessageCell.animatingNoSound = 0;
        } else {
            chatMessageCell.animatingNoSoundPlaying = false;
            chatMessageCell.animatingNoSoundProgress = 0.0f;
            chatMessageCell.animatingDrawVideoImageButtonProgress = ((i3 == 4 || i3 == 2) && chatMessageCell.drawVideoSize) ? 1.0f : 0.0f;
        }
        if (getDelegate() != null && getDelegate().getTextSelectionHelper() != null) {
            getDelegate().getTextSelectionHelper().onChatMessageCellAttached(this);
        }
        if (chatMessageCell.documentAttachType == 5) {
            chatMessageCell.toSeekBarProgress = MediaController.getInstance().isPlayingMessage(chatMessageCell.currentMessageObject) ? 1.0f : 0.0f;
        }
        chatMessageCell.reactionsLayoutInBubble.onAttachToWindow();
        FlagSecureReason flagSecureReason = chatMessageCell.flagSecure;
        if (flagSecureReason != null) {
            flagSecureReason.attach();
        }
        GroupMedia groupMedia = chatMessageCell.groupMedia;
        if (groupMedia != null) {
            groupMedia.onAttachedToWindow();
        }
        updateFlagSecure();
        MessageObject messageObject4 = chatMessageCell.currentMessageObject;
        if (messageObject4 != null && messageObject4.type == 20 && chatMessageCell.unlockLayout != null) {
            invalidate();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = chatMessageCell.currentNameStatusDrawable;
        if (swapAnimatedEmojiDrawable != null) {
            swapAnimatedEmojiDrawable.attach();
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = chatMessageCell.currentNameEmojiStatusDrawable;
        if (swapAnimatedEmojiDrawable2 != null) {
            swapAnimatedEmojiDrawable2.attach();
        }
        SpoilerEffect2 spoilerEffect2 = chatMessageCell.mediaSpoilerEffect2;
        if (spoilerEffect2 != null) {
            if (spoilerEffect2.destroyed) {
                SpoilerEffect2 spoilerEffect2MakeSpoilerEffect = makeSpoilerEffect();
                chatMessageCell.mediaSpoilerEffect2 = spoilerEffect2MakeSpoilerEffect;
                Integer num = chatMessageCell.mediaSpoilerEffect2Index;
                if (num != null) {
                    spoilerEffect2MakeSpoilerEffect.reassignAttach(this, num.intValue());
                }
            } else {
                spoilerEffect2.attach(this);
            }
        }
        ChannelRecommendationsCell channelRecommendationsCell = chatMessageCell.channelRecommendationsCell;
        if (channelRecommendationsCell != null) {
            channelRecommendationsCell.onAttachedToWindow();
        }
        StickerSetLinkIcon stickerSetLinkIcon = chatMessageCell.stickerSetIcons;
        if (stickerSetLinkIcon != null) {
            stickerSetLinkIcon.attach(this);
        }
    }

    protected SpoilerEffect2 makeSpoilerEffect() {
        return SpoilerEffect2.getInstance(this);
    }

    public void copySpoilerEffect2AttachIndexFrom(ChatMessageCell chatMessageCell) {
        SpoilerEffect2 spoilerEffect2;
        if (chatMessageCell == null || (spoilerEffect2 = chatMessageCell.mediaSpoilerEffect2) == null) {
            return;
        }
        Integer numValueOf = Integer.valueOf(spoilerEffect2.getAttachIndex(chatMessageCell));
        this.mediaSpoilerEffect2Index = numValueOf;
        SpoilerEffect2 spoilerEffect22 = this.mediaSpoilerEffect2;
        if (spoilerEffect22 != null) {
            spoilerEffect22.reassignAttach(this, numValueOf.intValue());
        }
    }

    private void checkImageReceiversAttachState() {
        boolean z = this.attachedToWindow;
        boolean z2 = false;
        if (z != this.imageReceiversAttachState) {
            this.imageReceiversAttachState = z;
            if (z) {
                this.radialProgress.onAttachedToWindow();
                this.videoRadialProgress.onAttachedToWindow();
                if (this.pollAvatarImages != null) {
                    int i = 0;
                    while (true) {
                        ImageReceiver[] imageReceiverArr = this.pollAvatarImages;
                        if (i >= imageReceiverArr.length) {
                            break;
                        }
                        imageReceiverArr[i].onAttachedToWindow();
                        i++;
                    }
                }
                if (this.commentAvatarImages != null) {
                    int i2 = 0;
                    while (true) {
                        ImageReceiver[] imageReceiverArr2 = this.commentAvatarImages;
                        if (i2 >= imageReceiverArr2.length) {
                            break;
                        }
                        imageReceiverArr2[i2].onAttachedToWindow();
                        i2++;
                    }
                }
                this.giveawayMessageCell.onAttachedToWindow();
                this.giveawayResultsMessageCell.onAttachedToWindow();
                this.replyImageReceiver.onAttachedToWindow();
                this.locationImageReceiver.onAttachedToWindow();
                this.blurredPhotoImage.onAttachedToWindow();
                if (!this.photoImage.onAttachedToWindow() || this.drawPhotoImage) {
                    updateButtonState(false, false, false);
                }
                this.animatedEmojiReplyStack = AnimatedEmojiSpan.update(0, (View) this, false, this.animatedEmojiReplyStack, this.replyTextLayout);
                this.animatedEmojiDescriptionStack = AnimatedEmojiSpan.update(0, (View) this, false, this.animatedEmojiDescriptionStack, this.descriptionLayout);
                updateAnimatedEmojis();
            } else {
                this.radialProgress.onDetachedFromWindow();
                this.videoRadialProgress.onDetachedFromWindow();
                if (this.pollAvatarImages != null) {
                    int i3 = 0;
                    while (true) {
                        ImageReceiver[] imageReceiverArr3 = this.pollAvatarImages;
                        if (i3 >= imageReceiverArr3.length) {
                            break;
                        }
                        imageReceiverArr3[i3].onDetachedFromWindow();
                        i3++;
                    }
                }
                if (this.commentAvatarImages != null) {
                    int i4 = 0;
                    while (true) {
                        ImageReceiver[] imageReceiverArr4 = this.commentAvatarImages;
                        if (i4 >= imageReceiverArr4.length) {
                            break;
                        }
                        imageReceiverArr4[i4].onDetachedFromWindow();
                        i4++;
                    }
                }
                this.replyImageReceiver.onDetachedFromWindow();
                this.locationImageReceiver.onDetachedFromWindow();
                this.photoImage.onDetachedFromWindow();
                this.blurredPhotoImage.onDetachedFromWindow();
                this.giveawayMessageCell.onDetachedFromWindow();
                this.giveawayResultsMessageCell.onDetachedFromWindow();
                AnimatedEmojiSpan.release(this, this.animatedEmojiDescriptionStack);
                AnimatedEmojiSpan.release(this, this.animatedEmojiReplyStack);
                AnimatedEmojiSpan.release(this, this.animatedEmojiStack);
            }
        }
        if (this.attachedToWindow && (this.visibleOnScreen || !this.shouldCheckVisibleOnScreen)) {
            z2 = true;
        }
        if (z2 != this.imageReceiversVisibleState) {
            this.imageReceiversVisibleState = z2;
            if (z2) {
                fileAttach(true, this.currentMessageObject);
            } else {
                fileDetach(this.currentMessageObject);
            }
        }
    }

    private void fileAttach(boolean z, MessageObject messageObject) {
        MessageObject messageObject2;
        ImageReceiver imageReceiver = this.photoImage;
        if (imageReceiver != null) {
            imageReceiver.setFileLoadingPriority(1);
        }
        if (z && messageObject != null && (this.isRoundVideo || messageObject.isVideo())) {
            checkVideoPlayback(true, null);
        }
        if (messageObject == null || messageObject.mediaExists) {
            return;
        }
        int iCanDownloadMediaType = DownloadController.getInstance(this.currentAccount).canDownloadMediaType(messageObject);
        TLRPC.Document document = messageObject.getDocument();
        if (MessageObject.isStickerDocument(document) || MessageObject.isAnimatedStickerDocument(document, true) || MessageObject.isGifDocument(document) || MessageObject.isRoundVideoDocument(document) || messageObject.hasVideoQualities() || this.isSmallImage) {
            messageObject2 = messageObject;
        } else {
            TLRPC.PhotoSize closestPhotoSizeWithSize = document == null ? FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize()) : null;
            if (iCanDownloadMediaType == 2 || (iCanDownloadMediaType == 1 && messageObject.isVideo())) {
                messageObject2 = messageObject;
                if (iCanDownloadMediaType != 2 && document != null && !messageObject2.shouldEncryptPhotoOrVideo() && messageObject2.canStreamVideo()) {
                    FileLoader.getInstance(this.currentAccount).loadFile(document, messageObject2, 1, 0);
                }
            } else if (iCanDownloadMediaType == 0) {
                messageObject2 = messageObject;
            } else {
                if (document != null) {
                    FileLoader.getInstance(this.currentAccount).loadFile(document, messageObject, 1, ((MessageObject.isVideoDocument(document) || messageObject.isVoiceOnce() || messageObject.isRoundOnce()) && messageObject.shouldEncryptPhotoOrVideo()) ? 2 : 0);
                } else if (closestPhotoSizeWithSize != null) {
                    messageObject2 = messageObject;
                    FileLoader.getInstance(this.currentAccount).loadFile(ImageLocation.getForObject(closestPhotoSizeWithSize, messageObject.photoThumbsObject), messageObject2, null, 1, messageObject.shouldEncryptPhotoOrVideo() ? 2 : 0);
                }
                messageObject2 = messageObject;
            }
            if (z) {
                updateButtonState(false, false, false);
            }
        }
        if (messageObject2.hasVideoQualities()) {
            VideoPlayer.VideoUri videoUri = messageObject2.highestQuality;
            if (videoUri != null && !videoUri.isManifestCached()) {
                FileLoader.getInstance(this.currentAccount).loadFile(messageObject2.highestQuality.manifestDocument, messageObject2, 1, 0);
            }
            VideoPlayer.VideoUri videoUri2 = messageObject2.thumbQuality;
            if (videoUri2 == null || videoUri2.isManifestCached()) {
                return;
            }
            FileLoader.getInstance(this.currentAccount).loadFile(messageObject2.thumbQuality.manifestDocument, messageObject2, 1, 0);
        }
    }

    private void fileDetach(MessageObject messageObject) {
        ImageReceiver imageReceiver = this.photoImage;
        if (imageReceiver != null) {
            imageReceiver.setFileLoadingPriority(0);
        }
        cancelLoading(messageObject);
    }

    private void cancelLoading(MessageObject messageObject) {
        if (messageObject == null || messageObject.mediaExists || messageObject.putInDownloadsStore || DownloadController.getInstance(this.currentAccount).isDownloading(messageObject.messageOwner.f1597id) || PhotoViewer.getInstance().isVisible()) {
            return;
        }
        TLRPC.Document document = messageObject.getDocument();
        if (MessageObject.isStickerDocument(document) || MessageObject.isAnimatedStickerDocument(document, true) || MessageObject.isGifDocument(document) || MessageObject.isRoundVideoDocument(document)) {
            return;
        }
        if (document != null) {
            FileLoader.getInstance(this.currentAccount).cancelLoadFile(document);
            return;
        }
        TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize());
        if (closestPhotoSizeWithSize != null) {
            FileLoader.getInstance(this.currentAccount).cancelLoadFile(closestPhotoSizeWithSize);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(15:(6:7186|1323|(1:1325)(2:1328|(1:1330)(1:1331))|1332|(1:1334)(2:1335|(1:1340)(1:1341))|1342)|(9:1347|1322|1369|(2:1372|1375)|1376|(2:1379|1382)|1378|1383|(0)(0))|1348|7158|1349|(1:1354)(2:1355|(9:1364|1365|1369|(0)|1376|(0)|1378|1383|(0)(0)))|1363|1368|1369|(0)|1376|(0)|1378|1383|(0)(0)) */
    /* JADX WARN: Can't wrap try/catch for region: R(15:7122|2801|(1:2803)(1:2804)|2805|(1:2807)(2:2808|(1:2813)(1:2814))|2815|(5:2820|2799|2800|2843|(0)(21:2846|2849|2850|(0)|2854|(0)|2858|(6:2861|2864|2867|(0)|2871|(0))|2875|(0)|2879|(0)|2905|(0)|2913|(4:2916|2919|(0)(0)|2923)|2924|(0)(0)|2928|(0)(0)|2947))|2823|7184|2824|(1:2829)(2:2830|(4:2839|2840|2843|(0)(0)))|2838|2800|2843|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:1366:0x1321, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:1367:0x1322, code lost:
    
        org.telegram.messenger.FileLog.m1160e(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:2841:0x33bb, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:2842:0x33bc, code lost:
    
        org.telegram.messenger.FileLog.m1160e(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:4315:0x5295, code lost:
    
        r2 = null;
     */
    /* JADX WARN: Multi-variable search skipped. Vars limit reached: 10056 (expected less than 5000) */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:1026:0x0e14  */
    /* JADX WARN: Removed duplicated region for block: B:1037:0x0e31  */
    /* JADX WARN: Removed duplicated region for block: B:1044:0x0e45  */
    /* JADX WARN: Removed duplicated region for block: B:1049:0x0e4f  */
    /* JADX WARN: Removed duplicated region for block: B:1059:0x0e67  */
    /* JADX WARN: Removed duplicated region for block: B:1078:0x0e98  */
    /* JADX WARN: Removed duplicated region for block: B:1087:0x0ea8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1089:0x0eac  */
    /* JADX WARN: Removed duplicated region for block: B:1099:0x0ec6  */
    /* JADX WARN: Removed duplicated region for block: B:1118:0x0f18 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:1120:0x0f1b  */
    /* JADX WARN: Removed duplicated region for block: B:1133:0x0f46  */
    /* JADX WARN: Removed duplicated region for block: B:1146:0x0f87  */
    /* JADX WARN: Removed duplicated region for block: B:1147:0x0f8f  */
    /* JADX WARN: Removed duplicated region for block: B:1156:0x0fbc  */
    /* JADX WARN: Removed duplicated region for block: B:1157:0x0fbf  */
    /* JADX WARN: Removed duplicated region for block: B:1160:0x0fe9  */
    /* JADX WARN: Removed duplicated region for block: B:1161:0x0fec  */
    /* JADX WARN: Removed duplicated region for block: B:1164:0x0ff4  */
    /* JADX WARN: Removed duplicated region for block: B:1165:0x0ff6  */
    /* JADX WARN: Removed duplicated region for block: B:1169:0x1003  */
    /* JADX WARN: Removed duplicated region for block: B:1172:0x100a  */
    /* JADX WARN: Removed duplicated region for block: B:1184:0x1041  */
    /* JADX WARN: Removed duplicated region for block: B:1191:0x1062  */
    /* JADX WARN: Removed duplicated region for block: B:1203:0x1084  */
    /* JADX WARN: Removed duplicated region for block: B:1204:0x1086  */
    /* JADX WARN: Removed duplicated region for block: B:1212:0x10a8  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0143 A[PHI: r9
      0x0143: PHI (r9v8 boolean) = (r9v7 boolean), (r9v9 boolean) binds: [B:116:0x013b, B:119:0x0140] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:1254:0x1160  */
    /* JADX WARN: Removed duplicated region for block: B:1255:0x116c  */
    /* JADX WARN: Removed duplicated region for block: B:1264:0x1197  */
    /* JADX WARN: Removed duplicated region for block: B:1279:0x11c4  */
    /* JADX WARN: Removed duplicated region for block: B:1280:0x11c6  */
    /* JADX WARN: Removed duplicated region for block: B:1287:0x11dc  */
    /* JADX WARN: Removed duplicated region for block: B:1300:0x1208  */
    /* JADX WARN: Removed duplicated region for block: B:1306:0x1217  */
    /* JADX WARN: Removed duplicated region for block: B:1314:0x1246  */
    /* JADX WARN: Removed duplicated region for block: B:1322:0x125e A[PHI: r2
      0x125e: PHI (r2v1069 int) = (r2v907 int), (r2v910 int) binds: [B:1321:0x125c, B:1347:0x12d5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:1372:0x1337  */
    /* JADX WARN: Removed duplicated region for block: B:1379:0x1348  */
    /* JADX WARN: Removed duplicated region for block: B:1385:0x135f  */
    /* JADX WARN: Removed duplicated region for block: B:1387:0x1367  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x018b  */
    /* JADX WARN: Removed duplicated region for block: B:1543:0x1654  */
    /* JADX WARN: Removed duplicated region for block: B:1544:0x1655  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x01a7  */
    /* JADX WARN: Removed duplicated region for block: B:1594:0x16d4  */
    /* JADX WARN: Removed duplicated region for block: B:1595:0x16d6  */
    /* JADX WARN: Removed duplicated region for block: B:1598:0x16dd  */
    /* JADX WARN: Removed duplicated region for block: B:1695:0x18d7  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x01b8  */
    /* JADX WARN: Removed duplicated region for block: B:1717:0x1955  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:1824:0x1c75  */
    /* JADX WARN: Removed duplicated region for block: B:1836:0x1d4f  */
    /* JADX WARN: Removed duplicated region for block: B:1991:0x2058  */
    /* JADX WARN: Removed duplicated region for block: B:1997:0x2083  */
    /* JADX WARN: Removed duplicated region for block: B:1999:0x20a1  */
    /* JADX WARN: Removed duplicated region for block: B:2003:0x20b9  */
    /* JADX WARN: Removed duplicated region for block: B:2007:0x20c2  */
    /* JADX WARN: Removed duplicated region for block: B:2010:0x20d7  */
    /* JADX WARN: Removed duplicated region for block: B:2011:0x20da  */
    /* JADX WARN: Removed duplicated region for block: B:2013:0x20e3  */
    /* JADX WARN: Removed duplicated region for block: B:2019:0x2112  */
    /* JADX WARN: Removed duplicated region for block: B:2023:0x211b  */
    /* JADX WARN: Removed duplicated region for block: B:2024:0x211e  */
    /* JADX WARN: Removed duplicated region for block: B:2027:0x2130  */
    /* JADX WARN: Removed duplicated region for block: B:2067:0x22b3  */
    /* JADX WARN: Removed duplicated region for block: B:2083:0x2341  */
    /* JADX WARN: Removed duplicated region for block: B:2087:0x2351  */
    /* JADX WARN: Removed duplicated region for block: B:2092:0x2363  */
    /* JADX WARN: Removed duplicated region for block: B:2098:0x2377  */
    /* JADX WARN: Removed duplicated region for block: B:2099:0x238d  */
    /* JADX WARN: Removed duplicated region for block: B:2140:0x24d7  */
    /* JADX WARN: Removed duplicated region for block: B:2143:0x24ec  */
    /* JADX WARN: Removed duplicated region for block: B:2144:0x24ee  */
    /* JADX WARN: Removed duplicated region for block: B:2165:0x254b  */
    /* JADX WARN: Removed duplicated region for block: B:2169:0x2563  */
    /* JADX WARN: Removed duplicated region for block: B:2200:0x266c  */
    /* JADX WARN: Removed duplicated region for block: B:2272:0x28b8  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x027c A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x027e  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:2537:0x2ebc  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:2541:0x2ed4  */
    /* JADX WARN: Removed duplicated region for block: B:2557:0x2f1c  */
    /* JADX WARN: Removed duplicated region for block: B:2565:0x2f2c  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0299  */
    /* JADX WARN: Removed duplicated region for block: B:2581:0x2f56  */
    /* JADX WARN: Removed duplicated region for block: B:2585:0x2f74  */
    /* JADX WARN: Removed duplicated region for block: B:2602:0x2fb1  */
    /* JADX WARN: Removed duplicated region for block: B:2608:0x2fd1  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:2630:0x3012  */
    /* JADX WARN: Removed duplicated region for block: B:2631:0x3014  */
    /* JADX WARN: Removed duplicated region for block: B:2635:0x302a  */
    /* JADX WARN: Removed duplicated region for block: B:2651:0x305d  */
    /* JADX WARN: Removed duplicated region for block: B:2652:0x305f  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:2678:0x30be  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x02b3  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x02bc  */
    /* JADX WARN: Removed duplicated region for block: B:2713:0x3131  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:2720:0x3167  */
    /* JADX WARN: Removed duplicated region for block: B:2727:0x3178  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x02cb A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:2846:0x33c6  */
    /* JADX WARN: Removed duplicated region for block: B:2848:0x33ca  */
    /* JADX WARN: Removed duplicated region for block: B:2853:0x33e6  */
    /* JADX WARN: Removed duplicated region for block: B:2857:0x340a  */
    /* JADX WARN: Removed duplicated region for block: B:2870:0x343a  */
    /* JADX WARN: Removed duplicated region for block: B:2874:0x3455  */
    /* JADX WARN: Removed duplicated region for block: B:2878:0x3463  */
    /* JADX WARN: Removed duplicated region for block: B:2882:0x3476  */
    /* JADX WARN: Removed duplicated region for block: B:2908:0x34f0  */
    /* JADX WARN: Removed duplicated region for block: B:2921:0x3516  */
    /* JADX WARN: Removed duplicated region for block: B:2922:0x3518  */
    /* JADX WARN: Removed duplicated region for block: B:2926:0x3525  */
    /* JADX WARN: Removed duplicated region for block: B:2927:0x3535  */
    /* JADX WARN: Removed duplicated region for block: B:2930:0x354c  */
    /* JADX WARN: Removed duplicated region for block: B:2943:0x356f  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:3015:0x3766  */
    /* JADX WARN: Removed duplicated region for block: B:3029:0x37a0  */
    /* JADX WARN: Removed duplicated region for block: B:3030:0x37a3  */
    /* JADX WARN: Removed duplicated region for block: B:3034:0x37b1  */
    /* JADX WARN: Removed duplicated region for block: B:3065:0x385c  */
    /* JADX WARN: Removed duplicated region for block: B:3069:0x387b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:3070:0x387d  */
    /* JADX WARN: Removed duplicated region for block: B:3076:0x3891  */
    /* JADX WARN: Removed duplicated region for block: B:3105:0x38db  */
    /* JADX WARN: Removed duplicated region for block: B:3106:0x38e4  */
    /* JADX WARN: Removed duplicated region for block: B:3112:0x38f3  */
    /* JADX WARN: Removed duplicated region for block: B:3144:0x393e  */
    /* JADX WARN: Removed duplicated region for block: B:3150:0x3948  */
    /* JADX WARN: Removed duplicated region for block: B:3154:0x395c  */
    /* JADX WARN: Removed duplicated region for block: B:3155:0x395f  */
    /* JADX WARN: Removed duplicated region for block: B:3182:0x39a7  */
    /* JADX WARN: Removed duplicated region for block: B:3183:0x39aa  */
    /* JADX WARN: Removed duplicated region for block: B:3186:0x39b1  */
    /* JADX WARN: Removed duplicated region for block: B:3187:0x39b3  */
    /* JADX WARN: Removed duplicated region for block: B:3190:0x39c9  */
    /* JADX WARN: Removed duplicated region for block: B:3191:0x39cb  */
    /* JADX WARN: Removed duplicated region for block: B:3193:0x39d1  */
    /* JADX WARN: Removed duplicated region for block: B:3287:0x3c90  */
    /* JADX WARN: Removed duplicated region for block: B:3303:0x3ce7  */
    /* JADX WARN: Removed duplicated region for block: B:3312:0x3d12  */
    /* JADX WARN: Removed duplicated region for block: B:3318:0x3d24  */
    /* JADX WARN: Removed duplicated region for block: B:3323:0x3d31  */
    /* JADX WARN: Removed duplicated region for block: B:3324:0x3d33  */
    /* JADX WARN: Removed duplicated region for block: B:3343:0x3d90  */
    /* JADX WARN: Removed duplicated region for block: B:3344:0x3d92  */
    /* JADX WARN: Removed duplicated region for block: B:3348:0x3dab  */
    /* JADX WARN: Removed duplicated region for block: B:3350:0x3dc2  */
    /* JADX WARN: Removed duplicated region for block: B:3357:0x3df3  */
    /* JADX WARN: Removed duplicated region for block: B:3362:0x3e1b A[LOOP:36: B:3295:0x3cc3->B:3362:0x3e1b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:3471:0x4192  */
    /* JADX WARN: Removed duplicated region for block: B:3735:0x467c  */
    /* JADX WARN: Removed duplicated region for block: B:3736:0x4685  */
    /* JADX WARN: Removed duplicated region for block: B:3739:0x4692  */
    /* JADX WARN: Removed duplicated region for block: B:3749:0x46db  */
    /* JADX WARN: Removed duplicated region for block: B:3758:0x46f5  */
    /* JADX WARN: Removed duplicated region for block: B:3759:0x46f7  */
    /* JADX WARN: Removed duplicated region for block: B:3760:0x46fb  */
    /* JADX WARN: Removed duplicated region for block: B:3779:0x478d  */
    /* JADX WARN: Removed duplicated region for block: B:3790:0x47c7  */
    /* JADX WARN: Removed duplicated region for block: B:3802:0x47e9  */
    /* JADX WARN: Removed duplicated region for block: B:3806:0x4816  */
    /* JADX WARN: Removed duplicated region for block: B:3816:0x484b  */
    /* JADX WARN: Removed duplicated region for block: B:3849:0x4936  */
    /* JADX WARN: Removed duplicated region for block: B:3869:0x4993  */
    /* JADX WARN: Removed duplicated region for block: B:3877:0x49b2  */
    /* JADX WARN: Removed duplicated region for block: B:3880:0x4a1e  */
    /* JADX WARN: Removed duplicated region for block: B:3888:0x4a30  */
    /* JADX WARN: Removed duplicated region for block: B:3895:0x4a81  */
    /* JADX WARN: Removed duplicated region for block: B:3903:0x4ab7  */
    /* JADX WARN: Removed duplicated region for block: B:3909:0x4af7  */
    /* JADX WARN: Removed duplicated region for block: B:3910:0x4afc  */
    /* JADX WARN: Removed duplicated region for block: B:3914:0x4b0c  */
    /* JADX WARN: Removed duplicated region for block: B:3918:0x4b36  */
    /* JADX WARN: Removed duplicated region for block: B:3928:0x4b74  */
    /* JADX WARN: Removed duplicated region for block: B:3930:0x4b9b A[LOOP:52: B:3897:0x4a93->B:3930:0x4b9b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:4352:0x534b  */
    /* JADX WARN: Removed duplicated region for block: B:4356:0x5355  */
    /* JADX WARN: Removed duplicated region for block: B:4359:0x535a  */
    /* JADX WARN: Removed duplicated region for block: B:4373:0x5389  */
    /* JADX WARN: Removed duplicated region for block: B:4376:0x53b3  */
    /* JADX WARN: Removed duplicated region for block: B:4377:0x53b5  */
    /* JADX WARN: Removed duplicated region for block: B:4384:0x5405  */
    /* JADX WARN: Removed duplicated region for block: B:4392:0x5423  */
    /* JADX WARN: Removed duplicated region for block: B:4398:0x545a  */
    /* JADX WARN: Removed duplicated region for block: B:4399:0x546b  */
    /* JADX WARN: Removed duplicated region for block: B:4403:0x547a  */
    /* JADX WARN: Removed duplicated region for block: B:4421:0x54c2  */
    /* JADX WARN: Removed duplicated region for block: B:4425:0x54d2  */
    /* JADX WARN: Removed duplicated region for block: B:4435:0x550c  */
    /* JADX WARN: Removed duplicated region for block: B:4442:0x551d  */
    /* JADX WARN: Removed duplicated region for block: B:4450:0x5538  */
    /* JADX WARN: Removed duplicated region for block: B:4454:0x554d  */
    /* JADX WARN: Removed duplicated region for block: B:4456:0x5555  */
    /* JADX WARN: Removed duplicated region for block: B:4656:0x57f2 A[PHI: r24
      0x57f2: PHI (r24v51 org.telegram.tgnet.TLRPC$Photo) = (r24v49 org.telegram.tgnet.TLRPC$Photo), (r24v54 org.telegram.tgnet.TLRPC$Photo) binds: [B:4640:0x57d3, B:4654:0x57ee] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:4659:0x57fb  */
    /* JADX WARN: Removed duplicated region for block: B:4660:0x57fe  */
    /* JADX WARN: Removed duplicated region for block: B:4685:0x582d  */
    /* JADX WARN: Removed duplicated region for block: B:4831:0x5a6a  */
    /* JADX WARN: Removed duplicated region for block: B:4835:0x5a75  */
    /* JADX WARN: Removed duplicated region for block: B:4839:0x5a7d  */
    /* JADX WARN: Removed duplicated region for block: B:4848:0x5ad8  */
    /* JADX WARN: Removed duplicated region for block: B:4881:0x5baa A[Catch: Exception -> 0x5c1c, TRY_ENTER, TRY_LEAVE, TryCatch #24 {Exception -> 0x5c1c, blocks: (B:4875:0x5b86, B:4881:0x5baa), top: B:7154:0x5b86 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:4952:0x5d0e  */
    /* JADX WARN: Removed duplicated region for block: B:4953:0x5d15  */
    /* JADX WARN: Removed duplicated region for block: B:5017:0x5e2d  */
    /* JADX WARN: Removed duplicated region for block: B:5019:0x5e31  */
    /* JADX WARN: Removed duplicated region for block: B:5027:0x5e4a  */
    /* JADX WARN: Removed duplicated region for block: B:5028:0x5e4b A[Catch: Exception -> 0x5e9a, TRY_LEAVE, TryCatch #21 {Exception -> 0x5e9a, blocks: (B:5025:0x5e46, B:5034:0x5e6f, B:5038:0x5e78, B:5028:0x5e4b), top: B:7149:0x5e46 }] */
    /* JADX WARN: Removed duplicated region for block: B:5038:0x5e78 A[Catch: Exception -> 0x5e9a, TRY_LEAVE, TryCatch #21 {Exception -> 0x5e9a, blocks: (B:5025:0x5e46, B:5034:0x5e6f, B:5038:0x5e78, B:5028:0x5e4b), top: B:7149:0x5e46 }] */
    /* JADX WARN: Removed duplicated region for block: B:5056:0x5ef2 A[Catch: Exception -> 0x5ef0, TryCatch #13 {Exception -> 0x5ef0, blocks: (B:5050:0x5eb6, B:5051:0x5ec2, B:5053:0x5eca, B:5056:0x5ef2, B:5069:0x5f13, B:5059:0x5efc, B:5068:0x5f0b), top: B:7133:0x5eb6 }] */
    /* JADX WARN: Removed duplicated region for block: B:505:0x05db  */
    /* JADX WARN: Removed duplicated region for block: B:5074:0x5f35  */
    /* JADX WARN: Removed duplicated region for block: B:509:0x060d  */
    /* JADX WARN: Removed duplicated region for block: B:5137:0x605c  */
    /* JADX WARN: Removed duplicated region for block: B:5145:0x606d  */
    /* JADX WARN: Removed duplicated region for block: B:5146:0x6070  */
    /* JADX WARN: Removed duplicated region for block: B:5149:0x6078  */
    /* JADX WARN: Removed duplicated region for block: B:516:0x0620  */
    /* JADX WARN: Removed duplicated region for block: B:522:0x0630  */
    /* JADX WARN: Removed duplicated region for block: B:523:0x0632  */
    /* JADX WARN: Removed duplicated region for block: B:527:0x0687  */
    /* JADX WARN: Removed duplicated region for block: B:531:0x0692  */
    /* JADX WARN: Removed duplicated region for block: B:535:0x06ab A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:539:0x06b1  */
    /* JADX WARN: Removed duplicated region for block: B:5461:0x6699  */
    /* JADX WARN: Removed duplicated region for block: B:5510:0x675a  */
    /* JADX WARN: Removed duplicated region for block: B:5515:0x6761  */
    /* JADX WARN: Removed duplicated region for block: B:5518:0x676b  */
    /* JADX WARN: Removed duplicated region for block: B:553:0x0726  */
    /* JADX WARN: Removed duplicated region for block: B:559:0x073b  */
    /* JADX WARN: Removed duplicated region for block: B:564:0x0743  */
    /* JADX WARN: Removed duplicated region for block: B:567:0x074b  */
    /* JADX WARN: Removed duplicated region for block: B:568:0x074d  */
    /* JADX WARN: Removed duplicated region for block: B:572:0x0760  */
    /* JADX WARN: Removed duplicated region for block: B:5837:0x6ccc  */
    /* JADX WARN: Removed duplicated region for block: B:5848:0x6cef  */
    /* JADX WARN: Removed duplicated region for block: B:590:0x07d2  */
    /* JADX WARN: Removed duplicated region for block: B:591:0x07d4  */
    /* JADX WARN: Removed duplicated region for block: B:5947:0x6fad  */
    /* JADX WARN: Removed duplicated region for block: B:5994:0x70af  */
    /* JADX WARN: Removed duplicated region for block: B:6012:0x7150  */
    /* JADX WARN: Removed duplicated region for block: B:6015:0x7162  */
    /* JADX WARN: Removed duplicated region for block: B:6020:0x717a  */
    /* JADX WARN: Removed duplicated region for block: B:6023:0x71e9  */
    /* JADX WARN: Removed duplicated region for block: B:6024:0x71eb  */
    /* JADX WARN: Removed duplicated region for block: B:6028:0x71fa  */
    /* JADX WARN: Removed duplicated region for block: B:605:0x07ef  */
    /* JADX WARN: Removed duplicated region for block: B:6079:0x72c1  */
    /* JADX WARN: Removed duplicated region for block: B:6081:0x72c5  */
    /* JADX WARN: Removed duplicated region for block: B:6086:0x72ea  */
    /* JADX WARN: Removed duplicated region for block: B:6097:0x7356  */
    /* JADX WARN: Removed duplicated region for block: B:6106:0x738e  */
    /* JADX WARN: Removed duplicated region for block: B:6115:0x73a8  */
    /* JADX WARN: Removed duplicated region for block: B:6144:0x73f1  */
    /* JADX WARN: Removed duplicated region for block: B:6152:0x740b  */
    /* JADX WARN: Removed duplicated region for block: B:6162:0x7429  */
    /* JADX WARN: Removed duplicated region for block: B:6168:0x743a  */
    /* JADX WARN: Removed duplicated region for block: B:6169:0x743c  */
    /* JADX WARN: Removed duplicated region for block: B:6173:0x7457  */
    /* JADX WARN: Removed duplicated region for block: B:6184:0x7470  */
    /* JADX WARN: Removed duplicated region for block: B:6185:0x7473  */
    /* JADX WARN: Removed duplicated region for block: B:618:0x080b  */
    /* JADX WARN: Removed duplicated region for block: B:6201:0x74b3  */
    /* JADX WARN: Removed duplicated region for block: B:6210:0x74ff  */
    /* JADX WARN: Removed duplicated region for block: B:6217:0x7519  */
    /* JADX WARN: Removed duplicated region for block: B:621:0x0821  */
    /* JADX WARN: Removed duplicated region for block: B:6220:0x751d  */
    /* JADX WARN: Removed duplicated region for block: B:6226:0x7530  */
    /* JADX WARN: Removed duplicated region for block: B:622:0x0829  */
    /* JADX WARN: Removed duplicated region for block: B:6235:0x754b  */
    /* JADX WARN: Removed duplicated region for block: B:6250:0x7596 A[Catch: Exception -> 0x75ab, TryCatch #5 {Exception -> 0x75ab, blocks: (B:6241:0x7558, B:6248:0x758b, B:6250:0x7596, B:6259:0x75b9, B:6263:0x75c6, B:6273:0x75f3, B:6279:0x75ff, B:6285:0x760e, B:6289:0x761c, B:6295:0x7638, B:6298:0x7647, B:6292:0x7633, B:6288:0x7618, B:6282:0x7604, B:6276:0x75fa, B:6266:0x75d7, B:6272:0x75e1, B:6269:0x75dc, B:6262:0x75be, B:6253:0x759f, B:6256:0x75ae, B:6244:0x755f, B:6247:0x7564), top: B:7118:0x7558 }] */
    /* JADX WARN: Removed duplicated region for block: B:6256:0x75ae A[Catch: Exception -> 0x75ab, TryCatch #5 {Exception -> 0x75ab, blocks: (B:6241:0x7558, B:6248:0x758b, B:6250:0x7596, B:6259:0x75b9, B:6263:0x75c6, B:6273:0x75f3, B:6279:0x75ff, B:6285:0x760e, B:6289:0x761c, B:6295:0x7638, B:6298:0x7647, B:6292:0x7633, B:6288:0x7618, B:6282:0x7604, B:6276:0x75fa, B:6266:0x75d7, B:6272:0x75e1, B:6269:0x75dc, B:6262:0x75be, B:6253:0x759f, B:6256:0x75ae, B:6244:0x755f, B:6247:0x7564), top: B:7118:0x7558 }] */
    /* JADX WARN: Removed duplicated region for block: B:6258:0x75b7  */
    /* JADX WARN: Removed duplicated region for block: B:6259:0x75b9 A[Catch: Exception -> 0x75ab, TryCatch #5 {Exception -> 0x75ab, blocks: (B:6241:0x7558, B:6248:0x758b, B:6250:0x7596, B:6259:0x75b9, B:6263:0x75c6, B:6273:0x75f3, B:6279:0x75ff, B:6285:0x760e, B:6289:0x761c, B:6295:0x7638, B:6298:0x7647, B:6292:0x7633, B:6288:0x7618, B:6282:0x7604, B:6276:0x75fa, B:6266:0x75d7, B:6272:0x75e1, B:6269:0x75dc, B:6262:0x75be, B:6253:0x759f, B:6256:0x75ae, B:6244:0x755f, B:6247:0x7564), top: B:7118:0x7558 }] */
    /* JADX WARN: Removed duplicated region for block: B:6319:0x76ea A[Catch: Exception -> 0x76e8, TRY_LEAVE, TryCatch #20 {Exception -> 0x76e8, blocks: (B:6312:0x7692, B:6313:0x76c6, B:6315:0x76ce, B:6319:0x76ea), top: B:7147:0x7692 }] */
    /* JADX WARN: Removed duplicated region for block: B:6324:0x7707  */
    /* JADX WARN: Removed duplicated region for block: B:6325:0x7708 A[Catch: Exception -> 0x775b, TryCatch #22 {Exception -> 0x775b, blocks: (B:6322:0x7701, B:6326:0x7711, B:6327:0x774c, B:6331:0x7757, B:6334:0x775d, B:6337:0x776d, B:6339:0x7771, B:6340:0x7779, B:6325:0x7708), top: B:7151:0x7701 }] */
    /* JADX WARN: Removed duplicated region for block: B:6330:0x7756  */
    /* JADX WARN: Removed duplicated region for block: B:6331:0x7757 A[Catch: Exception -> 0x775b, TryCatch #22 {Exception -> 0x775b, blocks: (B:6322:0x7701, B:6326:0x7711, B:6327:0x774c, B:6331:0x7757, B:6334:0x775d, B:6337:0x776d, B:6339:0x7771, B:6340:0x7779, B:6325:0x7708), top: B:7151:0x7701 }] */
    /* JADX WARN: Removed duplicated region for block: B:6334:0x775d A[Catch: Exception -> 0x775b, TryCatch #22 {Exception -> 0x775b, blocks: (B:6322:0x7701, B:6326:0x7711, B:6327:0x774c, B:6331:0x7757, B:6334:0x775d, B:6337:0x776d, B:6339:0x7771, B:6340:0x7779, B:6325:0x7708), top: B:7151:0x7701 }] */
    /* JADX WARN: Removed duplicated region for block: B:6352:0x7791  */
    /* JADX WARN: Removed duplicated region for block: B:6356:0x77a6  */
    /* JADX WARN: Removed duplicated region for block: B:6363:0x77c2  */
    /* JADX WARN: Removed duplicated region for block: B:6377:0x77e5  */
    /* JADX WARN: Removed duplicated region for block: B:6382:0x780a  */
    /* JADX WARN: Removed duplicated region for block: B:638:0x0857  */
    /* JADX WARN: Removed duplicated region for block: B:6408:0x7859  */
    /* JADX WARN: Removed duplicated region for block: B:6409:0x785c  */
    /* JADX WARN: Removed duplicated region for block: B:6413:0x7869  */
    /* JADX WARN: Removed duplicated region for block: B:6433:0x78c8  */
    /* JADX WARN: Removed duplicated region for block: B:6445:0x78ee  */
    /* JADX WARN: Removed duplicated region for block: B:6458:0x7919  */
    /* JADX WARN: Removed duplicated region for block: B:6460:0x791d  */
    /* JADX WARN: Removed duplicated region for block: B:6467:0x7936  */
    /* JADX WARN: Removed duplicated region for block: B:6491:0x797b  */
    /* JADX WARN: Removed duplicated region for block: B:6492:0x797e  */
    /* JADX WARN: Removed duplicated region for block: B:6507:0x79b3  */
    /* JADX WARN: Removed duplicated region for block: B:6519:0x79ed  */
    /* JADX WARN: Removed duplicated region for block: B:6520:0x79ef  */
    /* JADX WARN: Removed duplicated region for block: B:6526:0x7a11  */
    /* JADX WARN: Removed duplicated region for block: B:6527:0x7a17  */
    /* JADX WARN: Removed duplicated region for block: B:6537:0x7a62  */
    /* JADX WARN: Removed duplicated region for block: B:653:0x087d  */
    /* JADX WARN: Removed duplicated region for block: B:6594:0x7ae0 A[PHI: r1 r3 r5 r6
      0x7ae0: PHI (r1v203 int) = (r1v202 int), (r1v202 int), (r1v215 int) binds: [B:6590:0x7ad9, B:6593:0x7ade, B:6608:0x7afe] A[DONT_GENERATE, DONT_INLINE]
      0x7ae0: PHI (r3v143 int) = (r3v142 int), (r3v142 int), (r3v149 int) binds: [B:6590:0x7ad9, B:6593:0x7ade, B:6608:0x7afe] A[DONT_GENERATE, DONT_INLINE]
      0x7ae0: PHI (r5v59 int) = (r5v58 int), (r5v58 int), (r5v65 int) binds: [B:6590:0x7ad9, B:6593:0x7ade, B:6608:0x7afe] A[DONT_GENERATE, DONT_INLINE]
      0x7ae0: PHI (r6v40 int) = (r6v39 int), (r6v39 int), (r6v46 int) binds: [B:6590:0x7ad9, B:6593:0x7ade, B:6608:0x7afe] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:6663:0x7b7f  */
    /* JADX WARN: Removed duplicated region for block: B:6673:0x7ba1  */
    /* JADX WARN: Removed duplicated region for block: B:6700:0x7bea  */
    /* JADX WARN: Removed duplicated region for block: B:6701:0x7bee  */
    /* JADX WARN: Removed duplicated region for block: B:6747:0x7d51  */
    /* JADX WARN: Removed duplicated region for block: B:6767:0x7d74  */
    /* JADX WARN: Removed duplicated region for block: B:6771:0x7d8c  */
    /* JADX WARN: Removed duplicated region for block: B:6792:0x7dd7  */
    /* JADX WARN: Removed duplicated region for block: B:6800:0x7df4  */
    /* JADX WARN: Removed duplicated region for block: B:6803:0x7e12  */
    /* JADX WARN: Removed duplicated region for block: B:6807:0x7e1d  */
    /* JADX WARN: Removed duplicated region for block: B:680:0x08d5  */
    /* JADX WARN: Removed duplicated region for block: B:6814:0x7e40  */
    /* JADX WARN: Removed duplicated region for block: B:6817:0x7e57  */
    /* JADX WARN: Removed duplicated region for block: B:6834:0x7e7d  */
    /* JADX WARN: Removed duplicated region for block: B:6840:0x7e88  */
    /* JADX WARN: Removed duplicated region for block: B:684:0x08df  */
    /* JADX WARN: Removed duplicated region for block: B:685:0x08e1 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:6901:0x7f3e  */
    /* JADX WARN: Removed duplicated region for block: B:6918:0x7f7d  */
    /* JADX WARN: Removed duplicated region for block: B:6920:0x7f81  */
    /* JADX WARN: Removed duplicated region for block: B:6926:0x7f91  */
    /* JADX WARN: Removed duplicated region for block: B:692:0x0921  */
    /* JADX WARN: Removed duplicated region for block: B:6937:0x7fba  */
    /* JADX WARN: Removed duplicated region for block: B:6939:0x7fbd  */
    /* JADX WARN: Removed duplicated region for block: B:693:0x0923  */
    /* JADX WARN: Removed duplicated region for block: B:6944:0x7fd4  */
    /* JADX WARN: Removed duplicated region for block: B:6957:0x8002  */
    /* JADX WARN: Removed duplicated region for block: B:6970:0x809b  */
    /* JADX WARN: Removed duplicated region for block: B:698:0x092f  */
    /* JADX WARN: Removed duplicated region for block: B:701:0x0940  */
    /* JADX WARN: Removed duplicated region for block: B:7095:0x837a  */
    /* JADX WARN: Removed duplicated region for block: B:7100:0x839b A[LOOP:56: B:585:0x0794->B:7100:0x839b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:7118:0x7558 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7129:0x5ae9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7137:0x5c27 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7139:0x5b8e A[EDGE_INSN: B:7139:0x5b8e->B:4877:0x5b8e BREAK  A[LOOP:19: B:7154:0x5b86->B:4913:0x5c0e], EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7182:0x5cc0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7186:0x1262 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7281:0x391a A[EDGE_INSN: B:7281:0x391a->B:3123:0x391a BREAK  A[LOOP:35: B:3121:0x3915->B:3486:0x4208], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:745:0x0a2b  */
    /* JADX WARN: Removed duplicated region for block: B:746:0x0a2e  */
    /* JADX WARN: Removed duplicated region for block: B:749:0x0a3a  */
    /* JADX WARN: Removed duplicated region for block: B:752:0x0a75  */
    /* JADX WARN: Removed duplicated region for block: B:753:0x0a77  */
    /* JADX WARN: Removed duplicated region for block: B:762:0x0a8f  */
    /* JADX WARN: Removed duplicated region for block: B:763:0x0a96  */
    /* JADX WARN: Removed duplicated region for block: B:768:0x0ae5  */
    /* JADX WARN: Removed duplicated region for block: B:939:0x0cf1  */
    /* JADX WARN: Removed duplicated region for block: B:955:0x0d20  */
    /* JADX WARN: Removed duplicated region for block: B:976:0x0d53  */
    /* JADX WARN: Removed duplicated region for block: B:982:0x0d5d  */
    /* JADX WARN: Removed duplicated region for block: B:985:0x0d76  */
    /* JADX WARN: Removed duplicated region for block: B:990:0x0d89  */
    /* JADX WARN: Removed duplicated region for block: B:993:0x0d93  */
    /* JADX WARN: Type inference failed for: r0v1526, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r0v196, types: [org.telegram.ui.Components.RadialProgress2] */
    /* JADX WARN: Type inference failed for: r0v303, types: [org.telegram.ui.Components.RadialProgress2] */
    /* JADX WARN: Type inference failed for: r11v233, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r2v77, types: [android.text.SpannableStringBuilder, java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r2v80, types: [android.text.SpannableStringBuilder] */
    /* JADX WARN: Type inference failed for: r3v117 */
    /* JADX WARN: Type inference failed for: r3v118, types: [java.lang.Object, org.telegram.tgnet.TLRPC$Document, org.telegram.tgnet.TLRPC$PhotoSize] */
    /* JADX WARN: Type inference failed for: r3v157 */
    /* JADX WARN: Type inference failed for: r3v938, types: [android.text.StaticLayout, java.lang.String, org.telegram.messenger.WebFile] */
    /* JADX WARN: Type inference failed for: r3v945 */
    /* JADX WARN: Type inference failed for: r3v946 */
    /* JADX WARN: Type inference failed for: r3v947 */
    /* JADX WARN: Type inference failed for: r3v948 */
    /* JADX WARN: Type inference failed for: r3v949 */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v25, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v29 */
    /* JADX WARN: Type inference failed for: r4v30, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v34 */
    /* JADX WARN: Type inference failed for: r4v35, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v720 */
    /* JADX WARN: Type inference failed for: r4v724 */
    /* JADX WARN: Type inference failed for: r4v727 */
    /* JADX WARN: Type inference failed for: r4v728 */
    /* JADX WARN: Type inference failed for: r4v729 */
    /* JADX WARN: Type inference failed for: r6v203 */
    /* JADX WARN: Type inference failed for: r6v204 */
    /* JADX WARN: Type inference failed for: r6v206 */
    /* JADX WARN: Type inference failed for: r6v207 */
    /* JADX WARN: Type inference failed for: r6v208 */
    /* JADX WARN: Type inference failed for: r6v209 */
    /* JADX WARN: Type inference failed for: r6v212, types: [int] */
    /* JADX WARN: Type inference failed for: r6v214, types: [float] */
    /* JADX WARN: Type inference failed for: r6v215 */
    /* JADX WARN: Type inference failed for: r6v216, types: [float] */
    /* JADX WARN: Type inference failed for: r6v222 */
    /* JADX WARN: Type inference failed for: r6v223, types: [int] */
    /* JADX WARN: Type inference failed for: r6v226 */
    /* JADX WARN: Type inference failed for: r6v227 */
    /* JADX WARN: Type inference failed for: r6v239 */
    /* JADX WARN: Type inference failed for: r6v364 */
    /* JADX WARN: Type inference failed for: r6v383 */
    /* JADX WARN: Type inference failed for: r6v384 */
    /* JADX WARN: Type inference failed for: r6v391 */
    /* JADX WARN: Type inference failed for: r6v395 */
    /* JADX WARN: Type inference failed for: r6v396 */
    /* JADX WARN: Type inference failed for: r6v397 */
    /* JADX WARN: Type inference failed for: r8v304, types: [float] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setMessageContent(org.telegram.messenger.MessageObject r96, org.telegram.messenger.MessageObject.GroupedMessages r97, boolean r98, boolean r99, boolean r100, boolean r101) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 33780
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.setMessageContent(org.telegram.messenger.MessageObject, org.telegram.messenger.MessageObject$GroupedMessages, boolean, boolean, boolean, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMessageContent$7(long j, int i) {
        BotForumHelper.BotDraftAnimationsPool botDraftAnimationsPool = this.draftAnimationsPool;
        if (botDraftAnimationsPool != null) {
            botDraftAnimationsPool.removeAnimator(j, i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMessageContent$8(TLRPC.User user, int i, TLRPC.Chat chat, long j) {
        if (user != null) {
            this.commentAvatarDrawables[i].setInfo(this.currentAccount, user);
            this.commentAvatarImages[i].setForUserOrChat(user, this.commentAvatarDrawables[i]);
        } else if (chat != null) {
            this.commentAvatarDrawables[i].setInfo(this.currentAccount, chat);
            this.commentAvatarImages[i].setForUserOrChat(chat, this.commentAvatarDrawables[i]);
        } else {
            this.commentAvatarDrawables[i].setInfo(j, "", "");
        }
    }

    public static /* synthetic */ int $r8$lambda$V7hXBEEF4IOClaZfAJW80oU5i9w(PollButton pollButton, PollButton pollButton2) {
        if (pollButton.decimal > pollButton2.decimal) {
            return -1;
        }
        if (pollButton.decimal < pollButton2.decimal) {
            return 1;
        }
        if (pollButton.decimal != pollButton2.decimal) {
            return 0;
        }
        if (pollButton.percent > pollButton2.percent) {
            return 1;
        }
        return pollButton.percent < pollButton2.percent ? -1 : 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setMessageContent$10() {
        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
        if (chatMessageCellDelegate != null) {
            chatMessageCellDelegate.didPressSideButton(this);
        }
    }

    private boolean loopStickers() {
        return LiteMode.isEnabled(2);
    }

    private void calculateUnlockXY() {
        if (this.currentMessageObject.type != 20 || this.unlockLayout == null) {
            return;
        }
        this.unlockX = this.backgroundDrawableLeft + ((this.photoImage.getImageWidth() - this.unlockLayout.getWidth()) / 2.0f);
        this.unlockY = this.backgroundDrawableTop + this.photoImage.getImageY() + ((this.photoImage.getImageHeight() - this.unlockLayout.getHeight()) / 2.0f);
    }

    private void updateFlagSecure() {
        if (this.flagSecure == null) {
            Activity activityFindActivity = AndroidUtilities.findActivity(getContext());
            Window window = activityFindActivity == null ? null : activityFindActivity.getWindow();
            if (window != null) {
                FlagSecureReason flagSecureReason = new FlagSecureReason(window, new FlagSecureReason.FlagSecureCondition() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda11
                    @Override // org.telegram.messenger.FlagSecureReason.FlagSecureCondition
                    public final boolean run() {
                        return this.f$0.lambda$updateFlagSecure$11();
                    }
                });
                this.flagSecure = flagSecureReason;
                if (this.attachedToWindow) {
                    flagSecureReason.attach();
                }
            }
        }
        FlagSecureReason flagSecureReason2 = this.flagSecure;
        if (flagSecureReason2 != null) {
            flagSecureReason2.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updateFlagSecure$11() {
        TLRPC.Message message;
        GroupMedia groupMedia;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || (message = messageObject.messageOwner) == null) {
            return false;
        }
        return (messageObject.type == 29 && ((groupMedia = this.groupMedia) == null || !groupMedia.hidden)) || message.noforwards || messageObject.isVoiceOnce() || this.currentMessageObject.hasRevealedExtendedMedia();
    }

    public void checkVideoPlayback(boolean z, Bitmap bitmap) {
        if (this.currentMessageObject.isVideo()) {
            if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                this.photoImage.setAllowStartAnimation(false);
                this.photoImage.stopAnimation();
                return;
            } else {
                this.photoImage.setAllowStartAnimation(true);
                this.photoImage.startAnimation();
                return;
            }
        }
        if (z) {
            MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
            z = playingMessageObject == null || !playingMessageObject.isRoundVideo();
        }
        this.photoImage.setAllowStartAnimation(z);
        if (bitmap != null) {
            this.photoImage.startCrossfadeFromStaticThumb(bitmap);
        }
        if (z) {
            this.photoImage.startAnimation();
        } else {
            this.photoImage.stopAnimation();
        }
    }

    private static boolean spanSupportsLongPress(CharacterStyle characterStyle) {
        return (characterStyle instanceof URLSpanMono) || (characterStyle instanceof URLSpan);
    }

    /* JADX WARN: Removed duplicated region for block: B:217:0x03a4  */
    @Override // org.telegram.p023ui.Cells.BaseCell
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean onLongPress() {
        /*
            Method dump skipped, instructions count: 946
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.onLongPress():boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongPress$12() {
        this.replySelector.setState(new int[0]);
        invalidate();
    }

    public void showHintButton(boolean z, boolean z2, int i) {
        if (i == -1 || i == 0) {
            if (this.hintButtonVisible == z) {
                return;
            }
            this.hintButtonVisible = z;
            if (!z2) {
                this.hintButtonProgress = z ? 1.0f : 0.0f;
            } else {
                invalidate();
            }
        }
        if ((i == -1 || i == 1) && this.psaButtonVisible != z) {
            this.psaButtonVisible = z;
            if (!z2) {
                this.psaButtonProgress = z ? 1.0f : 0.0f;
            } else {
                setInvalidatesParent(true);
                invalidate();
            }
        }
    }

    public void setCheckPressed(boolean z, boolean z2) {
        this.isCheckPressed = z;
        this.isPressed = z2;
        updateRadialProgressBackground();
        if (this.useSeekBarWaveform) {
            this.seekBarWaveform.setSelected(isDrawSelectionBackground());
        } else {
            this.seekBar.setSelected(isDrawSelectionBackground());
        }
        invalidate();
    }

    public void setInvalidateSpoilersParent(boolean z) {
        this.invalidateSpoilersParent = z;
    }

    public void setInvalidatesParent(boolean z) {
        this.invalidatesParent = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean invalidateParentForce() {
        if (!this.links.isEmpty() || !this.reactionsLayoutInBubble.isEmpty) {
            return true;
        }
        MessageObject messageObject = this.currentMessageObject;
        return messageObject != null && messageObject.preview;
    }

    public void invalidateOutbounds() {
        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
        if (chatMessageCellDelegate == null || !chatMessageCellDelegate.canDrawOutboundsContent()) {
            if (getParent() instanceof View) {
                ((View) getParent()).invalidate();
                return;
            }
            return;
        }
        super.invalidate();
    }

    @Override // org.telegram.p023ui.Cells.BaseCell, android.view.View
    public void invalidate() {
        ChatMessageCellDelegate chatMessageCellDelegate;
        if (this.currentMessageObject == null) {
            return;
        }
        Runnable runnable = this.invalidateCallback;
        if (runnable != null) {
            runnable.run();
        }
        Runnable runnable2 = this.overrideInvalidate;
        if (runnable2 != null) {
            runnable2.run();
            return;
        }
        Runnable runnable3 = this.invalidateListener;
        if (runnable3 != null) {
            runnable3.run();
        }
        super.invalidate();
        if ((this.invalidatesParent || (this.currentMessagesGroup != null && invalidateParentForce())) && getParent() != null) {
            View view = (View) getParent();
            if (view.getParent() != null) {
                view.invalidate();
                ((View) view.getParent()).invalidate();
            }
        }
        if (!this.isBlurred || (chatMessageCellDelegate = this.delegate) == null) {
            return;
        }
        chatMessageCellDelegate.invalidateBlur();
    }

    @Override // org.telegram.p023ui.Cells.BaseCell
    public void invalidateLite() {
        ChatMessageCellDelegate chatMessageCellDelegate;
        if (this.currentMessageObject == null) {
            return;
        }
        Runnable runnable = this.overrideInvalidate;
        if (runnable != null) {
            runnable.run();
            return;
        }
        Runnable runnable2 = this.invalidateListener;
        if (runnable2 != null) {
            runnable2.run();
        }
        super.invalidate();
        if ((this.invalidatesParent || (this.currentMessagesGroup != null && invalidateParentForce())) && getParent() != null) {
            View view = (View) getParent();
            if (view.getParent() != null) {
                view.invalidate();
                ((View) view.getParent()).invalidate();
            }
        }
        if (!this.isBlurred || (chatMessageCellDelegate = this.delegate) == null) {
            return;
        }
        chatMessageCellDelegate.invalidateBlur();
    }

    public void setOverrideInvalidate(Runnable runnable) {
        this.overrideInvalidate = runnable;
    }

    public void setInvalidateListener(Runnable runnable) {
        this.invalidateListener = runnable;
    }

    @Override // android.view.View
    public void invalidate(int i, int i2, int i3, int i4) {
        ChatMessageCellDelegate chatMessageCellDelegate;
        if (this.currentMessageObject == null) {
            return;
        }
        Runnable runnable = this.overrideInvalidate;
        if (runnable != null) {
            runnable.run();
            return;
        }
        Runnable runnable2 = this.invalidateListener;
        if (runnable2 != null) {
            runnable2.run();
        }
        super.invalidate(i, i2, i3, i4);
        if (this.invalidatesParent && getParent() != null) {
            ((View) getParent()).invalidate(((int) getX()) + i, ((int) getY()) + i2, ((int) getX()) + i3, ((int) getY()) + i4);
        }
        if (!this.isBlurred || (chatMessageCellDelegate = this.delegate) == null) {
            return;
        }
        chatMessageCellDelegate.invalidateBlur();
    }

    public boolean isHighlightedAnimated() {
        return this.isHighlightedAnimated;
    }

    public void setHighlightedAnimated() {
        this.isHighlightedAnimated = true;
        this.highlightProgress = MediaDataController.MAX_STYLE_RUNS_COUNT;
        this.lastHighlightProgressTime = System.currentTimeMillis();
        invalidate();
        if (getParent() != null) {
            ((View) getParent()).invalidate();
        }
    }

    public boolean isHighlighted() {
        return this.isHighlighted;
    }

    public void setHighlighted(boolean z) {
        if (this.isHighlighted == z) {
            return;
        }
        this.isHighlighted = z;
        if (z) {
            this.quoteHighlight = null;
        }
        if (!z) {
            this.lastHighlightProgressTime = System.currentTimeMillis();
            this.isHighlightedAnimated = true;
            this.highlightProgress = DataTypes.UNIT;
        } else {
            this.isHighlightedAnimated = false;
            this.highlightProgress = 0;
        }
        updateRadialProgressBackground();
        if (this.useSeekBarWaveform) {
            this.seekBarWaveform.setSelected(isDrawSelectionBackground());
        } else {
            this.seekBar.setSelected(isDrawSelectionBackground());
        }
        invalidate();
        if (getParent() != null) {
            ((View) getParent()).invalidate();
        }
    }

    @Override // android.view.View
    public void setPressed(boolean z) {
        super.setPressed(z);
        updateRadialProgressBackground();
        if (this.useSeekBarWaveform) {
            this.seekBarWaveform.setSelected(isDrawSelectionBackground());
        } else {
            this.seekBar.setSelected(isDrawSelectionBackground());
        }
        invalidate();
    }

    private void updateRadialProgressBackground() {
        if (this.drawRadialCheckBackground) {
            return;
        }
        boolean z = true;
        boolean z2 = (this.isHighlighted || this.isPressed || isPressed()) && !(this.drawPhotoImage && this.photoImage.hasBitmapImage());
        this.radialProgress.setPressed(z2 || this.buttonPressed != 0, false);
        if (this.hasMiniProgress != 0) {
            this.radialProgress.setPressed(z2 || this.miniButtonPressed != 0, true);
        }
        RadialProgress2 radialProgress2 = this.videoRadialProgress;
        if (!z2 && this.videoButtonPressed == 0) {
            z = false;
        }
        radialProgress2.setPressed(z, false);
    }

    @Override // org.telegram.ui.Components.SeekBar.SeekBarDelegate
    public void onSeekBarPressed() {
        requestDisallowInterceptTouchEvent(true);
    }

    @Override // org.telegram.ui.Components.SeekBar.SeekBarDelegate
    public void onSeekBarReleased() {
        requestDisallowInterceptTouchEvent(false);
    }

    @Override // org.telegram.ui.Components.SeekBar.SeekBarDelegate
    public boolean isSeekBarDragAllowed() {
        MessageObject messageObject = this.currentMessageObject;
        return messageObject == null || !messageObject.isVoiceOnce();
    }

    @Override // org.telegram.ui.Components.SeekBar.SeekBarDelegate
    public boolean reverseWaveform() {
        MessageObject messageObject = this.currentMessageObject;
        return messageObject != null && messageObject.isVoiceOnce();
    }

    @Override // org.telegram.ui.Components.SeekBar.SeekBarDelegate
    public void onSeekBarDrag(float f) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return;
        }
        messageObject.audioProgress = f;
        MediaController.getInstance().seekToProgress(this.currentMessageObject, f);
        updatePlayingMessageProgress();
    }

    @Override // org.telegram.ui.Components.SeekBar.SeekBarDelegate
    public void onSeekBarContinuousDrag(float f) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return;
        }
        messageObject.audioProgress = f;
        messageObject.audioProgressSec = (int) (messageObject.getDuration() * f);
        updatePlayingMessageProgress();
    }

    public boolean isAnimatingPollAnswer() {
        return this.animatePollAnswerAlpha;
    }

    private void updateWaveform() {
        TLRPC.Message message;
        TLRPC.MessageMedia messageMedia;
        MessageObject messageObject;
        TLRPC.Message message2;
        TLRPC.Message message3;
        MessageObject messageObject2 = this.currentMessageObject;
        if (messageObject2 != null) {
            int i = this.documentAttachType;
            if (i == 3 || i == 7) {
                byte[] waveform = messageObject2.getWaveform();
                boolean z = false;
                this.useSeekBarWaveform = waveform != null;
                SeekBarWaveform seekBarWaveform = this.seekBarWaveform;
                if (seekBarWaveform != null) {
                    seekBarWaveform.setWaveform(waveform);
                }
                MessageObject messageObject3 = this.currentMessageObject;
                if (messageObject3 != null && !messageObject3.isQuickReply()) {
                    MessageObject messageObject4 = this.currentMessageObject;
                    if (!messageObject4.isRepostPreview && ((!messageObject4.isOutOwner() || this.currentMessageObject.isSent()) && ((VoiceRecognitionController.isCustomRecognitionEnabled() || UserConfig.getInstance(this.currentAccount).isPremium() || ((!TextUtils.isEmpty(this.currentMessageObject.messageOwner.voiceTranscription) && this.currentMessageObject.messageOwner.voiceTranscriptionFinal) || TranscribeButton.isFreeTranscribeInChat(this.currentMessageObject) || ((MessagesController.getInstance(this.currentAccount).transcribeAudioTrialWeeklyNumber > 0 && this.currentMessageObject.getDuration() <= MessagesController.getInstance(this.currentAccount).transcribeAudioTrialDurationMax && (((message3 = this.currentMessageObject.messageOwner) != null && (!TextUtils.isEmpty(message3.voiceTranscription) || this.currentMessageObject.messageOwner.voiceTranscriptionFinal)) || TranscribeButton.canTranscribeTrial(this.currentMessageObject))) || (MessagesController.getInstance(this.currentAccount).transcribeAudioTrialWeeklyNumber <= 0 && !MessagesController.getInstance(this.currentAccount).premiumFeaturesBlocked() && !MessagesController.getInstance(this.currentAccount).didPressTranscribeButtonEnough() && !this.currentMessageObject.isOutOwner() && (((message2 = (messageObject = this.currentMessageObject).messageOwner) != null && message2.voiceTranscriptionForce) || messageObject.getDuration() >= 60.0d))))) && (((this.currentMessageObject.isVoice() && this.useSeekBarWaveform) || this.currentMessageObject.isRoundVideo()) && (message = this.currentMessageObject.messageOwner) != null && !(MessageObject.getMedia(message) instanceof TLRPC.TL_messageMediaWebPage) && ((messageMedia = this.currentMessageObject.messageOwner.media) == null || messageMedia.ttl_seconds == 0))))) {
                        z = true;
                    }
                }
                this.useTranscribeButton = z;
                updateSeekBarWaveformWidth(null);
            }
        }
    }

    private void updateSeekBarWaveformWidth(Canvas canvas) {
        int i;
        this.seekBarWaveformTranslateX = 0;
        this.seekBarTranslateX = 0;
        int i2 = -AndroidUtilities.m1146dp((this.hasLinkPreview ? 10 : 0) + 92);
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateBackgroundBoundsInner && ((i = this.documentAttachType) == 3 || i == 7)) {
            int i3 = this.backgroundWidth;
            int iM1146dp = (int) ((i3 - transitionParams.toDeltaLeft) + transitionParams.toDeltaRight);
            int videoTranscriptionProgress = (int) ((i3 - transitionParams.deltaLeft) + transitionParams.deltaRight);
            if (this.isRoundVideo && !this.drawBackground) {
                videoTranscriptionProgress = (int) (videoTranscriptionProgress + (getVideoTranscriptionProgress() * AndroidUtilities.m1146dp(8.0f)));
                iM1146dp += AndroidUtilities.m1146dp(8.0f);
            }
            TransitionParams transitionParams2 = this.transitionParams;
            if (transitionParams2.toDeltaLeft == 0.0f && transitionParams2.toDeltaRight == 0.0f) {
                iM1146dp = videoTranscriptionProgress;
            }
            SeekBarWaveform seekBarWaveform = this.seekBarWaveform;
            if (seekBarWaveform != null) {
                if (transitionParams2.animateUseTranscribeButton) {
                    seekBarWaveform.setSize(((videoTranscriptionProgress + i2) - ((int) (AndroidUtilities.m1146dp(34.0f) * getUseTranscribeButtonProgress()))) - AndroidUtilities.m1146dp(this.hasLinkPreview ? 10.0f : 0.0f), AndroidUtilities.m1146dp(30.0f), i3 + i2 + (!this.useTranscribeButton ? -AndroidUtilities.m1146dp(34.0f) : 0), iM1146dp + i2 + (this.useTranscribeButton ? -AndroidUtilities.m1146dp(34.0f) : 0));
                } else {
                    seekBarWaveform.setSize(((videoTranscriptionProgress + i2) - ((int) (AndroidUtilities.m1146dp(34.0f) * getUseTranscribeButtonProgress()))) - AndroidUtilities.m1146dp(this.hasLinkPreview ? 10.0f : 0.0f), AndroidUtilities.m1146dp(30.0f), (i3 + i2) - ((int) (AndroidUtilities.m1146dp(34.0f) * getUseTranscribeButtonProgress())), (iM1146dp + i2) - ((int) (AndroidUtilities.m1146dp(34.0f) * getUseTranscribeButtonProgress())));
                }
            }
            SeekBar seekBar = this.seekBar;
            if (seekBar != null) {
                seekBar.setSize((videoTranscriptionProgress - ((int) (getUseTranscribeButtonProgress() * AndroidUtilities.m1146dp(34.0f)))) - AndroidUtilities.m1146dp((this.documentAttachType == 5 ? 65 : 72) + (this.hasLinkPreview ? 20 : 0)), AndroidUtilities.m1146dp(30.0f));
                return;
            }
            return;
        }
        SeekBarWaveform seekBarWaveform2 = this.seekBarWaveform;
        if (seekBarWaveform2 != null) {
            if (transitionParams.animateUseTranscribeButton) {
                seekBarWaveform2.setSize(((this.backgroundWidth + i2) - ((int) (AndroidUtilities.m1146dp(34.0f) * getUseTranscribeButtonProgress()))) - AndroidUtilities.m1146dp(this.hasLinkPreview ? 10.0f : 0.0f), AndroidUtilities.m1146dp(30.0f), ((this.backgroundWidth + i2) + (!this.useTranscribeButton ? -AndroidUtilities.m1146dp(34.0f) : 0)) - AndroidUtilities.m1146dp(this.hasLinkPreview ? 10.0f : 0.0f), this.backgroundWidth + i2 + (this.useTranscribeButton ? -AndroidUtilities.m1146dp(34.0f) : 0));
            } else {
                seekBarWaveform2.setSize(((this.backgroundWidth + i2) - ((int) (AndroidUtilities.m1146dp(34.0f) * getUseTranscribeButtonProgress()))) - AndroidUtilities.m1146dp(this.hasLinkPreview ? 10.0f : 0.0f), AndroidUtilities.m1146dp(30.0f));
            }
        }
        SeekBar seekBar2 = this.seekBar;
        if (seekBar2 != null) {
            seekBar2.setSize((this.backgroundWidth - ((int) (getUseTranscribeButtonProgress() * AndroidUtilities.m1146dp(34.0f)))) - AndroidUtilities.m1146dp((this.documentAttachType == 5 ? 65 : 72) + (this.hasLinkPreview ? 20 : 0)), AndroidUtilities.m1146dp(30.0f));
        }
    }

    private int createDocumentLayout(int i, MessageObject messageObject) {
        int iMin;
        int iM1146dp = i;
        TLRPC.MessageMedia messageMedia = messageObject.sponsoredMedia;
        if (messageMedia != null) {
            this.documentAttach = messageMedia.document;
        } else if (messageObject.type == 0) {
            TLRPC.MessageMedia media = MessageObject.getMedia(messageObject.messageOwner);
            TLRPC.WebPage webPage = media == null ? null : media.webpage;
            this.documentAttach = webPage == null ? null : webPage.document;
        } else {
            this.documentAttach = messageObject.getDocument();
        }
        TLRPC.Document document = this.documentAttach;
        int i2 = 0;
        if (document == null) {
            return 0;
        }
        double d = 0.0d;
        if (MessageObject.isVoiceDocument(document)) {
            this.documentAttachType = 3;
            int i3 = 0;
            while (true) {
                if (i3 >= this.documentAttach.attributes.size()) {
                    break;
                }
                TLRPC.DocumentAttribute documentAttribute = this.documentAttach.attributes.get(i3);
                if (documentAttribute instanceof TLRPC.TL_documentAttributeAudio) {
                    d = documentAttribute.duration;
                    break;
                }
                i3++;
            }
            this.widthBeforeNewTimeLine = (iM1146dp - AndroidUtilities.m1146dp(94.0f)) - ((int) Math.ceil(Theme.chat_audioTimePaint.measureText("00:00")));
            this.availableTimeWidth = iM1146dp - AndroidUtilities.m1146dp(18.0f);
            measureTime(messageObject);
            int iM1146dp2 = AndroidUtilities.m1146dp(174.0f) + this.timeWidth;
            if (!this.hasLinkPreview) {
                this.backgroundWidth = Math.min(iM1146dp, iM1146dp2 + ((int) Math.ceil(Theme.chat_audioTimePaint.measureText(AndroidUtilities.formatLongDuration((int) d)))));
            }
            this.seekBarWaveform.setMessageObject(messageObject);
            return 0;
        }
        if (MessageObject.isVideoDocument(this.documentAttach)) {
            this.documentAttachType = 4;
            if (!messageObject.needDrawBluredPreview()) {
                updatePlayingMessageProgress();
                String str = String.format("%s", AndroidUtilities.formatFileSize(this.documentAttach.size));
                this.docTitleWidth = (int) Math.ceil(Theme.chat_infoPaint.measureText(str));
                this.docTitleLayout = new StaticLayout(str, Theme.chat_infoPaint, this.docTitleWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            }
            return 0;
        }
        if (MessageObject.isMusicDocument(this.documentAttach)) {
            this.documentAttachType = 5;
            int iM1146dp3 = iM1146dp - AndroidUtilities.m1146dp(92.0f);
            if (iM1146dp3 < 0) {
                iM1146dp3 = AndroidUtilities.m1146dp(100.0f);
            }
            int i4 = iM1146dp3;
            String strReplace = messageObject.getMusicTitle().replace('\n', ' ');
            TextPaint textPaint = Theme.chat_audioTitlePaint;
            float fM1146dp = i4 - AndroidUtilities.m1146dp(12.0f);
            TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
            CharSequence charSequenceEllipsize = TextUtils.ellipsize(strReplace, textPaint, fM1146dp, truncateAt);
            TextPaint textPaint2 = Theme.chat_audioTitlePaint;
            Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
            StaticLayout staticLayout = new StaticLayout(charSequenceEllipsize, textPaint2, i4, alignment, 1.0f, 0.0f, false);
            this.songLayout = staticLayout;
            if (staticLayout.getLineCount() > 0) {
                this.songX = -((int) Math.ceil(this.songLayout.getLineLeft(0)));
            }
            StaticLayout staticLayout2 = new StaticLayout(TextUtils.ellipsize(messageObject.getMusicAuthor().replace('\n', ' '), Theme.chat_audioPerformerPaint, i4, truncateAt), Theme.chat_audioPerformerPaint, i4, alignment, 1.0f, 0.0f, false);
            this.performerLayout = staticLayout2;
            if (staticLayout2.getLineCount() > 0) {
                this.performerX = -((int) Math.ceil(this.performerLayout.getLineLeft(0)));
            }
            while (true) {
                if (i2 >= this.documentAttach.attributes.size()) {
                    break;
                }
                TLRPC.DocumentAttribute documentAttribute2 = this.documentAttach.attributes.get(i2);
                if (documentAttribute2 instanceof TLRPC.TL_documentAttributeAudio) {
                    d = documentAttribute2.duration;
                    break;
                }
                i2++;
            }
            int i5 = (int) d;
            int iCeil = (int) Math.ceil(Theme.chat_audioTimePaint.measureText(AndroidUtilities.formatShortDuration(i5, i5)));
            this.widthBeforeNewTimeLine = (this.backgroundWidth - AndroidUtilities.m1146dp(86.0f)) - iCeil;
            this.availableTimeWidth = this.backgroundWidth - AndroidUtilities.m1146dp(28.0f);
            return iCeil;
        }
        if (MessageObject.isGifDocument(this.documentAttach, messageObject.hasValidGroupId())) {
            this.documentAttachType = 2;
            if (!messageObject.needDrawBluredPreview()) {
                String string = LocaleController.getString("AttachGif", C2369R.string.AttachGif);
                this.infoWidth = (int) Math.ceil(Theme.chat_infoPaint.measureText(string));
                TextPaint textPaint3 = Theme.chat_infoPaint;
                int i6 = this.infoWidth;
                Layout.Alignment alignment2 = Layout.Alignment.ALIGN_NORMAL;
                this.infoLayout = new StaticLayout(string, textPaint3, i6, alignment2, 1.0f, 0.0f, false);
                String str2 = String.format("%s", AndroidUtilities.formatFileSize(this.documentAttach.size));
                this.docTitleWidth = (int) Math.ceil(Theme.chat_infoPaint.measureText(str2));
                this.docTitleLayout = new StaticLayout(str2, Theme.chat_infoPaint, this.docTitleWidth, alignment2, 1.0f, 0.0f, false);
            }
            return 0;
        }
        String str3 = this.documentAttach.mime_type;
        boolean z = (str3 != null && (str3.toLowerCase().startsWith("image/") || this.documentAttach.mime_type.toLowerCase().startsWith("video/mp4"))) || MessageObject.isDocumentHasThumb(this.documentAttach);
        this.drawPhotoImage = z;
        if (!z) {
            iM1146dp += AndroidUtilities.m1146dp(30.0f);
        }
        int i7 = iM1146dp;
        this.documentAttachType = 1;
        String documentFileName = FileLoader.getDocumentFileName(this.documentAttach);
        if (documentFileName.length() == 0) {
            documentFileName = LocaleController.getString("AttachDocument", C2369R.string.AttachDocument);
        }
        StaticLayout staticLayoutCreateStaticLayout = StaticLayoutEx.createStaticLayout(documentFileName, Theme.chat_docNamePaint, i7, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false, TextUtils.TruncateAt.MIDDLE, i7, 2, false);
        this.docTitleLayout = staticLayoutCreateStaticLayout;
        this.docTitleOffsetX = TLObject.FLAG_31;
        if (staticLayoutCreateStaticLayout != null && staticLayoutCreateStaticLayout.getLineCount() > 0) {
            int iMax = 0;
            while (i2 < this.docTitleLayout.getLineCount()) {
                iMax = Math.max(iMax, (int) Math.ceil(this.docTitleLayout.getLineWidth(i2)));
                this.docTitleOffsetX = Math.max(this.docTitleOffsetX, (int) Math.ceil(-this.docTitleLayout.getLineLeft(i2)));
                i2++;
            }
            iMin = Math.min(i7, iMax);
        } else {
            this.docTitleOffsetX = 0;
            iMin = i7;
        }
        String str4 = AndroidUtilities.formatFileSize(this.documentAttach.size) + " " + FileLoader.getDocumentExtension(this.documentAttach);
        int iM1146dp4 = i7 - AndroidUtilities.m1146dp(30.0f);
        TextPaint textPaint4 = Theme.chat_infoPaint;
        int iMin2 = Math.min(iM1146dp4, (int) Math.ceil(textPaint4.measureText("000.0 mm / " + AndroidUtilities.formatFileSize(this.documentAttach.size))));
        this.infoWidth = iMin2;
        CharSequence charSequenceEllipsize2 = TextUtils.ellipsize(str4, Theme.chat_infoPaint, (float) iMin2, TextUtils.TruncateAt.END);
        try {
            if (this.infoWidth < 0) {
                this.infoWidth = AndroidUtilities.m1146dp(10.0f);
            }
            this.infoLayout = new StaticLayout(charSequenceEllipsize2, Theme.chat_infoPaint, this.infoWidth + AndroidUtilities.m1146dp(6.0f), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        if (this.drawPhotoImage) {
            this.currentPhotoObject = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 320);
            this.currentPhotoObjectThumb = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 40);
            if (this.currentMessageObject.isHiddenSensitive() || (DownloadController.getInstance(this.currentAccount).getAutodownloadMask() & 1) == 0) {
                this.currentPhotoObject = null;
            }
            TLRPC.PhotoSize photoSize = this.currentPhotoObject;
            if (photoSize == null || photoSize == this.currentPhotoObjectThumb) {
                this.currentPhotoObject = null;
                this.photoImage.setNeedsQualityThumb(true);
                this.photoImage.setShouldGenerateQualityThumb(true);
            } else {
                BitmapDrawable bitmapDrawable = this.currentMessageObject.strippedThumb;
                if (bitmapDrawable != null) {
                    this.currentPhotoObjectThumb = null;
                    this.currentPhotoObjectThumbStripped = bitmapDrawable;
                }
            }
            this.currentPhotoFilter = "86_86_b";
            this.photoImage.setImage(ImageLocation.getForObject(this.currentPhotoObject, messageObject.photoThumbsObject), "86_86", ImageLocation.getForObject(this.currentPhotoObjectThumb, messageObject.photoThumbsObject), this.currentPhotoFilter, this.currentPhotoObjectThumbStripped, 0L, null, messageObject, 1);
        }
        return iMin;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x009d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void calcBackgroundWidth(int r7, int r8, int r9) {
        /*
            Method dump skipped, instructions count: 254
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.calcBackgroundWidth(int, int, int):void");
    }

    public boolean setHighlightedText(String str) {
        return setHighlightedText(str, false, -1, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x00ba A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x00b4 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00b6 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0124  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean setHighlightedText(java.lang.String r18, boolean r19, int r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 475
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.setHighlightedText(java.lang.String, boolean, int, boolean):boolean");
    }

    public boolean setHighlightedTask(int i) {
        MessageObject messageObject = this.messageObjectToSet;
        if (messageObject == null) {
            messageObject = this.currentMessageObject;
        }
        if (messageObject == null) {
            this.quoteHighlight = null;
            return false;
        }
        QuoteHighlight quoteHighlight = this.quoteHighlight;
        if (quoteHighlight == null || !quoteHighlight.todo || quoteHighlight.start != (-i)) {
            this.quoteHighlight = new QuoteHighlight(this, messageObject.getId(), i);
        }
        this.highlightedQuote = true;
        return true;
    }

    private void highlight(int i, int i2, ArrayList arrayList) {
        if (arrayList == null) {
            return;
        }
        int i3 = i2 - i;
        int i4 = 0;
        while (true) {
            if (i4 >= arrayList.size()) {
                break;
            }
            MessageObject.TextLayoutBlock textLayoutBlock = (MessageObject.TextLayoutBlock) arrayList.get(i4);
            if (i < textLayoutBlock.charactersOffset || i >= textLayoutBlock.charactersEnd) {
                i4++;
            } else {
                this.linkSelectionBlockNum = i4;
                resetUrlPaths();
                try {
                    LinkPath linkPathObtainNewUrlPath = obtainNewUrlPath();
                    linkPathObtainNewUrlPath.setUseCornerPathImplementation(true);
                    linkPathObtainNewUrlPath.setCurrentLayout(textLayoutBlock.textLayout, i, 0.0f);
                    textLayoutBlock.textLayout.getSelectionPath(i, i2, linkPathObtainNewUrlPath);
                    linkPathObtainNewUrlPath.closeRects();
                    float f = textLayoutBlock.height + textLayoutBlock.padBottom;
                    if (i2 >= textLayoutBlock.charactersOffset + i3) {
                        for (int i5 = i4 + 1; i5 < arrayList.size(); i5++) {
                            MessageObject.TextLayoutBlock textLayoutBlock2 = (MessageObject.TextLayoutBlock) arrayList.get(i5);
                            int i6 = textLayoutBlock2.charactersEnd - textLayoutBlock2.charactersOffset;
                            LinkPath linkPathObtainNewUrlPath2 = obtainNewUrlPath();
                            linkPathObtainNewUrlPath2.setUseCornerPathImplementation(true);
                            float f2 = f + textLayoutBlock2.padTop;
                            linkPathObtainNewUrlPath2.setCurrentLayout(textLayoutBlock2.textLayout, 0, f2);
                            f = f2 + textLayoutBlock2.height + textLayoutBlock2.padBottom;
                            textLayoutBlock2.textLayout.getSelectionPath(0, i2 - textLayoutBlock2.charactersOffset, linkPathObtainNewUrlPath2);
                            linkPathObtainNewUrlPath2.closeRects();
                            if (i2 < (textLayoutBlock.charactersOffset + i6) - 1) {
                                break;
                            }
                        }
                    }
                    if (this.highlightedQuote) {
                        this.isHighlightedAnimated = true;
                        this.highlightProgress = 2500;
                    }
                } catch (Exception e) {
                    FileLog.m1160e(e);
                }
                invalidate();
            }
        }
        this.highlightCaptionToSetStart = -1;
        this.highlightCaptionToSetEnd = -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0115  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean setHighlightedSpan(android.text.style.CharacterStyle r13) {
        /*
            Method dump skipped, instructions count: 421
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.setHighlightedSpan(android.text.style.CharacterStyle):boolean");
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        ReactionsLayoutInBubble reactionsLayoutInBubble;
        if (!super.verifyDrawable(drawable)) {
            Drawable[] drawableArr = this.selectorDrawable;
            if (drawable != drawableArr[0] && drawable != this.animatedInfoLayout && drawable != drawableArr[1] && drawable != this.linkPreviewSelector && drawable != this.nameLayoutSelector && drawable != this.replySelector && (((reactionsLayoutInBubble = this.reactionsLayoutInBubble) == null || !reactionsLayoutInBubble.verifyDrawable(drawable)) && !(drawable instanceof LoadingDrawable))) {
                for (int i = 0; i < this.pollButtons.size(); i++) {
                    if (((PollButton) this.pollButtons.get(i)).selectorDrawable == drawable) {
                        return true;
                    }
                }
                return false;
            }
        }
        return true;
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable);
        if (this.currentMessagesGroup != null) {
            invalidateWithParent();
        }
    }

    private boolean isCurrentLocationTimeExpired(MessageObject messageObject) {
        int i = MessageObject.getMedia(this.currentMessageObject.messageOwner).period;
        int currentTime = ConnectionsManager.getInstance(this.currentAccount).getCurrentTime();
        if (i == Integer.MAX_VALUE) {
            return false;
        }
        return i % 60 == 0 ? Math.abs(currentTime - messageObject.messageOwner.date) > i : Math.abs(currentTime - messageObject.messageOwner.date) > i + (-5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkLocationExpired() {
        boolean zIsCurrentLocationTimeExpired;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || (zIsCurrentLocationTimeExpired = isCurrentLocationTimeExpired(messageObject)) == this.locationExpired) {
            return;
        }
        this.locationExpired = zIsCurrentLocationTimeExpired;
        if (!zIsCurrentLocationTimeExpired) {
            AndroidUtilities.runOnUIThread(this.invalidateRunnable, 1000L);
            this.scheduledInvalidate = true;
            int iM1146dp = this.backgroundWidth - AndroidUtilities.m1146dp(91.0f);
            this.docTitleLayout = new StaticLayout(TextUtils.ellipsize(LocaleController.getString(C2369R.string.AttachLiveLocation), Theme.chat_locationTitlePaint, iM1146dp, TextUtils.TruncateAt.END), Theme.chat_locationTitlePaint, iM1146dp, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            return;
        }
        MessageObject messageObject2 = this.currentMessageObject;
        this.currentMessageObject = null;
        setMessageObject(messageObject2, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop, this.firstInChat);
    }

    public void setIsUpdating(boolean z) {
        this.isUpdating = true;
    }

    public void setMessageObject(MessageObject messageObject, MessageObject.GroupedMessages groupedMessages, boolean z, boolean z2, boolean z3) {
        setMessageObject(messageObject, groupedMessages, z, z2, z3, false);
    }

    public void setMessageObject(MessageObject messageObject, MessageObject.GroupedMessages groupedMessages, boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.attachedToWindow && !this.frozen) {
            setMessageContent(messageObject, groupedMessages, z, z2, z3, z4);
            return;
        }
        this.messageObjectToSet = messageObject;
        this.groupedMessagesToSet = groupedMessages;
        this.bottomNearToSet = z;
        this.topNearToSet = z2;
        this.firstInChatToSet = z3;
        this.lastInChatListToSet = z4;
    }

    private int getAdditionalWidthForPosition(MessageObject.GroupedMessagePosition groupedMessagePosition) {
        if (groupedMessagePosition == null) {
            return 0;
        }
        int iM1146dp = (groupedMessagePosition.flags & 2) == 0 ? AndroidUtilities.m1146dp(4.0f) : 0;
        return (groupedMessagePosition.flags & 1) == 0 ? iM1146dp + AndroidUtilities.m1146dp(4.0f) : iM1146dp;
    }

    public void createSelectorDrawable(final int i) {
        int themedColor;
        ReplyMessageLine replyMessageLine;
        ReplyMessageLine replyMessageLine2;
        if (this.currentMessageObject.isUnsupported()) {
            themedColor = getThemedColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outPreviewInstantText : Theme.key_chat_inPreviewInstantText);
        } else if (i == 0 && this.psaHintPressed) {
            themedColor = getThemedColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outViews : Theme.key_chat_inViews);
        } else if (i == 0 && (replyMessageLine2 = this.linkLine) != null) {
            themedColor = replyMessageLine2.getColor();
        } else if (i == 0 && (replyMessageLine = this.contactLine) != null) {
            themedColor = replyMessageLine.getColor();
        } else {
            themedColor = getThemedColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outPreviewInstantText : Theme.key_chat_inPreviewInstantText);
        }
        Drawable drawable = this.selectorDrawable[i];
        if (drawable == null) {
            this.selectorMaskDrawable[i] = new MaskDrawable() { // from class: org.telegram.ui.Cells.ChatMessageCell.7
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super();
                }

                /* JADX WARN: Removed duplicated region for block: B:39:0x00cf  */
                /* JADX WARN: Removed duplicated region for block: B:51:0x01a5  */
                @Override // org.telegram.ui.Cells.ChatMessageCell.MaskDrawable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public void updatePath() {
                    /*
                        Method dump skipped, instructions count: 509
                        To view this dump add '--comments-level debug' option
                    */
                    throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.C28217.updatePath():void");
                }
            };
            this.selectorDrawable[i] = new BaseCell.RippleDrawableSafe(new ColorStateList(new int[][]{StateSet.WILD_CARD}, new int[]{436207615 & getThemedColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outPreviewInstantText : Theme.key_chat_inPreviewInstantText)}), null, this.selectorMaskDrawable[i]);
            this.selectorDrawable[i].setCallback(this);
        } else {
            Theme.setSelectorDrawableColor(drawable, themedColor & 436207615, true);
        }
        this.selectorDrawable[i].setVisible(true, false);
    }

    private static class MaskDrawable extends Drawable {
        protected final Paint maskPaint;
        public final Path path;
        public int pathX;
        public int pathY;
        public final RectF rect;

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -2;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }

        public abstract void updatePath();

        private MaskDrawable() {
            Paint paint = new Paint(1);
            this.maskPaint = paint;
            paint.setColor(-1);
            this.rect = new RectF();
            this.path = new Path();
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            updatePath();
            canvas.drawPath(this.path, this.maskPaint);
        }
    }

    private void createInstantViewButton() {
        int iMeasureText;
        boolean z;
        if (this.drawInstantView) {
            createSelectorDrawable(0);
        }
        if (this.drawInstantView && this.instantViewLayout == null) {
            this.instantWidth = AndroidUtilities.m1146dp(33.0f);
            CharSequence string = this.instantViewButtonText;
            if (string == null) {
                int i = this.drawInstantViewType;
                if (i == 12) {
                    string = LocaleController.getString(C2369R.string.OpenChannelPost);
                } else if (i == 1) {
                    string = LocaleController.getString(C2369R.string.OpenChannel);
                } else if (i == 29) {
                    string = LocaleController.getString(C2369R.string.OpenChannelDirect);
                } else if (i == 13) {
                    string = LocaleController.getString(C2369R.string.SendMessage).toUpperCase();
                } else if (i == 32) {
                    string = LocaleController.getString(C2369R.string.OpenProfile).toUpperCase();
                } else if (i == 10) {
                    string = LocaleController.getString(C2369R.string.OpenBot);
                } else if (i == 2) {
                    string = LocaleController.getString(C2369R.string.OpenGroup);
                } else if (i == 3) {
                    string = LocaleController.getString(C2369R.string.OpenMessage);
                } else if (i == 5) {
                    string = LocaleController.getString(C2369R.string.ViewContact);
                } else if (i == 6) {
                    string = LocaleController.getString(C2369R.string.OpenBackground);
                } else if (i == 7) {
                    string = LocaleController.getString(C2369R.string.OpenTheme);
                } else if (i == 8) {
                    if (this.pollVoted || this.pollClosed) {
                        string = LocaleController.getString(C2369R.string.PollViewResults);
                    } else {
                        string = LocaleController.getString(C2369R.string.PollSubmitVotes);
                    }
                } else if (i == 9 || i == 11) {
                    TLRPC.TL_webPage tL_webPage = (TLRPC.TL_webPage) MessageObject.getMedia(this.currentMessageObject.messageOwner).webpage;
                    if (tL_webPage != null && tL_webPage.url.contains("voicechat=")) {
                        string = LocaleController.getString(C2369R.string.VoipGroupJoinAsSpeaker);
                    } else {
                        string = LocaleController.getString(C2369R.string.VoipGroupJoinAsLinstener);
                    }
                } else if (i == 25) {
                    string = LocaleController.getString(C2369R.string.VoipGroupJoinAsLinstener);
                } else if (i == 14) {
                    string = LocaleController.getString(C2369R.string.ViewChatList).toUpperCase();
                } else if (i == 15) {
                    string = LocaleController.getString(C2369R.string.BotWebAppInstantViewOpen).toUpperCase();
                } else if (i == 16) {
                    string = LocaleController.getString(C2369R.string.OpenLink).toUpperCase();
                } else if (i == 17) {
                    string = LocaleController.getString(C2369R.string.ViewStory).toUpperCase();
                } else if (i == 18 || i == 22) {
                    string = LocaleController.getString(C2369R.string.BoostLinkButton);
                } else if (i == 19) {
                    string = LocaleController.getString(C2369R.string.BoostingHowItWork);
                } else if (i == 20) {
                    string = LocaleController.getString(C2369R.string.OpenGift);
                } else if (i == 21) {
                    string = LocaleController.getString(C2369R.string.AppUpdate);
                } else if (i == 23) {
                    string = LocaleController.getString(C2369R.string.OpenStickerSet);
                } else if (i == 24) {
                    string = LocaleController.getString(C2369R.string.OpenEmojiSet);
                } else if (i == 26) {
                    TL_stars.StarGift starGift = this.instantViewTypeIsGiftAuction;
                    if (starGift != null) {
                        if (starGift.auction_start_date > ConnectionsManager.getInstance(this.currentAccount).getCurrentTime()) {
                            string = LocaleController.getString(C2369R.string.OpenGiftAuctionView);
                            z = false;
                        } else {
                            if (this.instantViewTypeIsGiftAuction.sold_out) {
                                string = LocaleController.getString(C2369R.string.OpenGiftAuctionResults);
                            } else {
                                string = LocaleController.getString(C2369R.string.OpenGiftAuctionActive);
                            }
                            z = true;
                        }
                        if (z) {
                            SpannableString spannableString = new SpannableString("*");
                            spannableString.setSpan(new ColoredImageSpan(C2369R.drawable.filled_gift_sell_24), 0, spannableString.length(), 33);
                            string = TextUtils.concat(spannableString, " ", string);
                        }
                    } else {
                        string = LocaleController.getString(C2369R.string.OpenUniqueGift);
                    }
                } else if (i == 27) {
                    string = LocaleController.getString(C2369R.string.JoinCall).toUpperCase();
                } else if (i == 28) {
                    string = LocaleController.getString(C2369R.string.ViewCollection);
                } else {
                    string = LocaleController.getString(C2369R.string.InstantView);
                }
            }
            if (this.currentMessageObject.isSponsored() && this.backgroundWidth < (iMeasureText = (int) (Theme.chat_instantViewPaint.measureText(string, 0, string.length()) + AndroidUtilities.m1146dp(75.0f)))) {
                this.backgroundWidth = iMeasureText;
            }
            int iM1146dp = this.backgroundWidth - AndroidUtilities.m1146dp(75.0f);
            StaticLayout staticLayout = new StaticLayout(TextUtils.ellipsize(string, Theme.chat_instantViewPaint, iM1146dp, TextUtils.TruncateAt.END), Theme.chat_instantViewPaint, AndroidUtilities.m1146dp(2.0f) + iM1146dp, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            this.instantViewLayout = staticLayout;
            this.instantViewLayoutWidth = staticLayout.getLineCount() > 0 ? this.instantViewLayout.getLineWidth(0) : 0.0f;
            this.instantViewLayoutLeft = this.instantViewLayout.getLineCount() > 0 ? this.instantViewLayout.getLineLeft(0) : 0.0f;
            this.instantWidth = this.backgroundWidth - AndroidUtilities.m1146dp(this.drawInstantViewType == 8 ? 13.0f : 34.0f);
            int iM1146dp2 = this.totalHeight + AndroidUtilities.m1146dp(46.0f);
            this.totalHeight = iM1146dp2;
            if (this.currentMessageObject.type == 12) {
                this.totalHeight = iM1146dp2 + AndroidUtilities.m1146dp(14.0f);
            }
            if (this.currentMessageObject.isSponsored()) {
                this.totalHeight += AndroidUtilities.m1146dp(2.0f);
            }
            StaticLayout staticLayout2 = this.instantViewLayout;
            if (staticLayout2 == null || staticLayout2.getLineCount() <= 0) {
                return;
            }
            this.instantTextX = (((int) (this.instantWidth - Math.ceil(this.instantViewLayout.getLineWidth(0)))) / 2) + (this.drawInstantViewType == 0 ? AndroidUtilities.m1146dp(8.0f) : 0);
            int lineLeft = (int) this.instantViewLayout.getLineLeft(0);
            this.instantTextLeftX = lineLeft;
            this.instantTextX += -lineLeft;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void createContactButtons() {
        int i;
        boolean z;
        createSelectorDrawable(0);
        if (this.drawContact) {
            boolean z2 = this.drawContactView;
            if (this.drawContactSendMessage) {
                boolean z3 = (z2 ? 1 : 0) | 2;
                i = (z2 ? 1 : 0) + 1;
                z = z3;
            } else {
                i = z2 ? 1 : 0;
                z = z2;
            }
            boolean z4 = z;
            if (this.drawContactAdd) {
                i++;
                z4 = (z ? 1 : 0) | 4;
            }
            if (i == 0) {
                this.contactButtons = null;
                this.drawnContactButtonsFlag = 0;
                return;
            }
            this.totalHeight += AndroidUtilities.m1146dp(60.0f);
            if (z4 != this.drawnContactButtonsFlag) {
                this.drawnContactButtonsFlag = 0;
                int iM1146dp = (this.backgroundWidth - AndroidUtilities.m1146dp(75.0f)) / i;
                float fDpf2 = (this.backgroundWidth - AndroidUtilities.dpf2(37.0f)) / i;
                ArrayList arrayList = this.contactButtons;
                if (arrayList == null) {
                    this.contactButtons = new ArrayList(i);
                } else {
                    arrayList.clear();
                }
                if (this.drawContactView) {
                    this.drawnContactButtonsFlag |= 1;
                    this.contactButtons.add(createInstantViewButton(5, LocaleController.getString("ViewContact", C2369R.string.ViewContact), iM1146dp, fDpf2));
                }
                if (this.drawContactSendMessage) {
                    this.drawnContactButtonsFlag |= 2;
                    this.contactButtons.add(createInstantViewButton(30, LocaleController.getString("SharedContactMessage", C2369R.string.SharedContactMessage), iM1146dp, fDpf2));
                }
                if (this.drawContactAdd) {
                    this.drawnContactButtonsFlag |= 4;
                    this.contactButtons.add(createInstantViewButton(31, LocaleController.getString("SharedContactAdd", C2369R.string.SharedContactAdd), iM1146dp, fDpf2));
                }
            }
        }
    }

    private InstantViewButton createInstantViewButton(int i, String str, int i2, float f) {
        InstantViewButton instantViewButton = new InstantViewButton();
        instantViewButton.type = i;
        instantViewButton.layout = new StaticLayout(TextUtils.ellipsize(str, Theme.chat_instantViewPaint, i2, TextUtils.TruncateAt.END), Theme.chat_instantViewPaint, i2 + AndroidUtilities.m1146dp(2.0f), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        instantViewButton.buttonWidth = f;
        if (instantViewButton.layout.getLineCount() > 0) {
            instantViewButton.textX = ((float) (instantViewButton.buttonWidth - Math.ceil(instantViewButton.layout.getLineWidth(0)))) / 2.0f;
            instantViewButton.textX -= (int) instantViewButton.layout.getLineLeft(0);
        }
        return instantViewButton;
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.inLayout) {
            return;
        }
        super.requestLayout();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        ChatMessageCell chatMessageCell;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || (!messageObject.checkLayout() && this.lastHeight == AndroidUtilities.displaySize.y)) {
            chatMessageCell = this;
        } else {
            this.inLayout = true;
            MessageObject messageObject2 = this.currentMessageObject;
            this.currentMessageObject = null;
            chatMessageCell = this;
            chatMessageCell.setMessageObject(messageObject2, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop, this.firstInChat);
            chatMessageCell.inLayout = false;
        }
        updateSelectionTextPosition();
        int i3 = chatMessageCell.starsPriceTopPadding + chatMessageCell.topicSeparatorTopPadding + chatMessageCell.suggestionOfferTopPadding + chatMessageCell.totalHeight + chatMessageCell.keyboardHeight + chatMessageCell.askBotForumBottomPadding;
        chatMessageCell.additionalPaddingHeight = Math.max(0, 0 - i3);
        setMeasuredDimension(isWidthAdaptive() ? getBoundsRight() - getBoundsLeft() : View.MeasureSpec.getSize(i), Math.max(0, i3));
    }

    public void forceResetMessageObject() {
        MessageObject messageObject = this.messageObjectToSet;
        if (messageObject == null) {
            messageObject = this.currentMessageObject;
        }
        MessageObject messageObject2 = messageObject;
        this.currentMessageObject = null;
        setMessageObject(messageObject2, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop, this.firstInChat);
    }

    private int getGroupPhotosWidth() {
        int parentWidth = getParentWidth();
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.preview) {
            parentWidth = this.parentWidth;
        }
        if (AndroidUtilities.isInMultiwindow || !AndroidUtilities.isTablet()) {
            return parentWidth;
        }
        if (AndroidUtilities.isSmallTablet() && getResources().getConfiguration().orientation != 2) {
            return parentWidth;
        }
        int iM1146dp = (parentWidth / 100) * 35;
        if (iM1146dp < AndroidUtilities.m1146dp(320.0f)) {
            iM1146dp = AndroidUtilities.m1146dp(320.0f);
        }
        return parentWidth - iM1146dp;
    }

    int getExtraTextX() {
        int i = SharedConfig.bubbleRadius;
        if (i >= 15) {
            return AndroidUtilities.m1146dp(2.0f);
        }
        if (i >= 11) {
            return AndroidUtilities.m1146dp(1.0f);
        }
        return 0;
    }

    private int getExtraTimeX() {
        int i;
        if (!this.currentMessageObject.isOutOwner() && ((!this.mediaBackground || this.captionLayout != null) && (i = SharedConfig.bubbleRadius) > 11)) {
            return AndroidUtilities.m1146dp((i - 11) / 1.5f);
        }
        if (!this.currentMessageObject.isOutOwner() && this.isPlayingRound && this.isAvatarVisible && this.currentMessageObject.type == 5) {
            return (int) ((AndroidUtilities.roundPlayingMessageSize(this.isSideMenued) - AndroidUtilities.roundMessageSize) * 0.7f);
        }
        return 0;
    }

    public void relayout() {
        this.forcedLayout = true;
        forceLayout();
    }

    /* JADX WARN: Removed duplicated region for block: B:138:0x035f  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0370  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onLayout(boolean r24, int r25, int r26, int r27, int r28) {
        /*
            Method dump skipped, instructions count: 2529
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.onLayout(boolean, int, int, int, int):void");
    }

    public boolean needDelayRoundProgressDraw() {
        MessageObject messageObject;
        int i = this.documentAttachType;
        return (i == 7 || i == 4) && (messageObject = this.currentMessageObject) != null && messageObject.type != 5 && MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x007b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawRoundProgress(android.graphics.Canvas r21) {
        /*
            Method dump skipped, instructions count: 748
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawRoundProgress(android.graphics.Canvas):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x009c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void updatePollAnimations(long r9) {
        /*
            Method dump skipped, instructions count: 275
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.updatePollAnimations(long):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0549  */
    /* JADX WARN: Removed duplicated region for block: B:492:0x0962  */
    /* JADX WARN: Removed duplicated region for block: B:501:0x09ba  */
    /* JADX WARN: Removed duplicated region for block: B:653:0x0d21  */
    /* JADX WARN: Removed duplicated region for block: B:804:0x110e  */
    /* JADX WARN: Removed duplicated region for block: B:862:0x126e  */
    /* JADX WARN: Removed duplicated region for block: B:863:0x1271  */
    /* JADX WARN: Removed duplicated region for block: B:866:0x128d  */
    /* JADX WARN: Removed duplicated region for block: B:867:0x1290  */
    /* JADX WARN: Removed duplicated region for block: B:870:0x12b4  */
    /* JADX WARN: Removed duplicated region for block: B:871:0x12bc  */
    /* JADX WARN: Removed duplicated region for block: B:874:0x12f0  */
    /* JADX WARN: Removed duplicated region for block: B:883:0x133c  */
    /* JADX WARN: Removed duplicated region for block: B:886:0x1345  */
    /* JADX WARN: Removed duplicated region for block: B:887:0x1351  */
    /* JADX WARN: Removed duplicated region for block: B:890:0x1360  */
    /* JADX WARN: Removed duplicated region for block: B:894:0x13b0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawContent(android.graphics.Canvas r40, boolean r41) {
        /*
            Method dump skipped, instructions count: 6410
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawContent(android.graphics.Canvas, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$drawContent$13(Canvas canvas) {
        this.radialProgress.draw(canvas);
    }

    public void startRevealMedia() throws Resources.NotFoundException {
        startRevealMedia(this.photoImage.getImageX() + (this.photoImage.getImageWidth() / 2.0f), this.photoImage.getImageY() + (this.photoImage.getImageHeight() / 2.0f));
    }

    public void startRevealMedia(float f, float f2) throws Resources.NotFoundException {
        float fSqrt = (float) Math.sqrt(Math.pow(this.photoImage.getImageWidth(), 2.0d) + Math.pow(this.photoImage.getImageHeight(), 2.0d));
        this.mediaSpoilerRevealMaxRadius = fSqrt;
        startRevealMedia(f, f2, fSqrt);
    }

    private void startRevealMedia(float f, float f2, float f3) throws Resources.NotFoundException {
        ChatMessageCell chatMessageCell;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject.isMediaSpoilersRevealed || this.mediaSpoilerRevealProgress != 0.0f) {
            return;
        }
        if (messageObject.type == 3) {
            messageObject.forceUpdate = true;
            messageObject.revealingMediaSpoilers = true;
            chatMessageCell = this;
            chatMessageCell.setMessageContent(messageObject, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop, this.firstInChat, this.lastInChatList);
            MessageObject messageObject2 = chatMessageCell.currentMessageObject;
            messageObject2.revealingMediaSpoilers = false;
            messageObject2.forceUpdate = false;
            if (chatMessageCell.currentMessagesGroup != null) {
                chatMessageCell.radialProgress.setProgress(0.0f, false);
            }
        } else {
            chatMessageCell = this;
        }
        chatMessageCell.mediaSpoilerRevealX = f;
        chatMessageCell.mediaSpoilerRevealY = f2;
        ValueAnimator duration = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration((long) MathUtils.clamp(chatMessageCell.mediaSpoilerRevealMaxRadius * 0.3f, 250.0f, 550.0f));
        duration.setInterpolator(CubicBezierInterpolator.EASE_BOTH);
        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda12
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$startRevealMedia$14(valueAnimator);
            }
        });
        duration.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.ChatMessageCell.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ChatMessageCell.this.currentMessageObject.isMediaSpoilersRevealed = true;
                ChatMessageCell.this.invalidate();
            }
        });
        duration.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startRevealMedia$14(ValueAnimator valueAnimator) {
        this.mediaSpoilerRevealProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    public void drawBlurredPhoto(Canvas canvas) {
        if (this.currentMessageObject.isMediaSpoilersRevealed || this.mediaSpoilerRevealProgress == 1.0f) {
            return;
        }
        int[] roundRadius = this.photoImage.getRoundRadius();
        float[] fArr = this.mediaSpoilerRadii;
        float f = roundRadius[0];
        fArr[1] = f;
        fArr[0] = f;
        float f2 = roundRadius[1];
        fArr[3] = f2;
        fArr[2] = f2;
        float f3 = roundRadius[2];
        fArr[5] = f3;
        fArr[4] = f3;
        float f4 = roundRadius[3];
        fArr[7] = f4;
        fArr[6] = f4;
        this.mediaSpoilerPath.rewind();
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(this.photoImage.getImageX(), this.photoImage.getImageY(), this.photoImage.getImageX2(), this.photoImage.getImageY2());
        Path path = this.mediaSpoilerPath;
        float[] fArr2 = this.mediaSpoilerRadii;
        Path.Direction direction = Path.Direction.CW;
        path.addRoundRect(rectF, fArr2, direction);
        canvas.save();
        canvas.clipPath(this.mediaSpoilerPath);
        if (this.mediaSpoilerRevealProgress != 0.0f) {
            this.mediaSpoilerPath.rewind();
            this.mediaSpoilerPath.addCircle(this.mediaSpoilerRevealX, this.mediaSpoilerRevealY, this.mediaSpoilerRevealMaxRadius * this.mediaSpoilerRevealProgress, direction);
            canvas.clipPath(this.mediaSpoilerPath, Region.Op.DIFFERENCE);
        }
        if (this.currentMessageObject.needDrawBluredPreview()) {
            this.photoImage.draw(canvas);
        } else {
            this.blurredPhotoImage.setImageCoords(this.photoImage.getImageX(), this.photoImage.getImageY(), this.photoImage.getImageWidth(), this.photoImage.getImageHeight());
            this.blurredPhotoImage.setRoundRadius(this.photoImage.getRoundRadius());
            this.blurredPhotoImage.draw(canvas);
        }
        drawBlurredPhotoParticles(canvas);
        canvas.restore();
    }

    public void drawBlurredPhotoParticles(Canvas canvas) {
        if (this.mediaSpoilerEffect2 == null) {
            return;
        }
        canvas.save();
        canvas.translate(this.photoImage.getImageX(), this.photoImage.getImageY());
        this.mediaSpoilerEffect2.draw(canvas, this, (int) this.photoImage.getImageWidth(), (int) this.photoImage.getImageHeight(), this.photoImage.getAlpha(), this.drawingToBitmap);
        canvas.restore();
        invalidate();
    }

    private float getUseTranscribeButtonProgress() {
        TransitionParams transitionParams = this.transitionParams;
        if (!transitionParams.animateUseTranscribeButton) {
            return this.useTranscribeButton ? 1.0f : 0.0f;
        }
        if (this.useTranscribeButton) {
            return transitionParams.animateChangeProgress;
        }
        return 1.0f - transitionParams.animateChangeProgress;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateReactionLayoutPosition() {
        /*
            Method dump skipped, instructions count: 580
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.updateReactionLayoutPosition():void");
    }

    public float getPhotoBottom() {
        if (this.groupMedia != null) {
            return r0.f1811y + r0.height;
        }
        return this.photoImage.getImageY2();
    }

    public void drawVoiceOnce(Canvas canvas, float f, Runnable runnable) {
        Canvas canvas2 = canvas;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.isVoiceOnce()) {
            float fCenterX = this.radialProgress.progressRect.centerX() + (((float) Math.cos((AndroidUtilities.lerp(Opcodes.ARRAYLENGTH, 45, f) / 180.0f) * 3.141592653589793d)) * AndroidUtilities.m1146dp(22.6274f));
            float fCenterY = this.radialProgress.progressRect.centerY() + (((float) Math.sin((AndroidUtilities.lerp(Opcodes.ARRAYLENGTH, 45, f) / 180.0f) * 3.141592653589793d)) * AndroidUtilities.m1146dp(22.6274f));
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(this.radialProgress.progressRect);
            rectF.inset(-AndroidUtilities.m1146dp(1.0f), -AndroidUtilities.m1146dp(1.0f));
            canvas2.saveLayerAlpha(rectF, 255, 31);
            this.radialProgress.setBackgroundDrawable(isDrawSelectionBackground() ? this.currentBackgroundSelectedDrawable : this.currentBackgroundDrawable);
            this.radialProgress.iconScale = f;
            runnable.run();
            if (this.onceClearPaint == null) {
                Paint paint = new Paint(1);
                this.onceClearPaint = paint;
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            }
            if (f < 1.0f) {
                canvas2.save();
                float f2 = 1.0f - f;
                float f3 = 0.7f * f2;
                float fCenterX2 = this.radialProgress.progressRect.centerX();
                RectF rectF2 = this.radialProgress.progressRect;
                canvas2.scale(f3, f3, fCenterX2, AndroidUtilities.lerp(rectF2.top, rectF2.bottom, 0.5f));
                if (this.onceFire == null) {
                    RLottieDrawable rLottieDrawable = new RLottieDrawable(C2369R.raw.fire_once, "fire_once", AndroidUtilities.m1146dp(32.0f), AndroidUtilities.m1146dp(32.0f), true, null);
                    this.onceFire = rLottieDrawable;
                    rLottieDrawable.setMasterParent(this);
                    this.onceFire.setAllowDecodeSingleFrame(true);
                    this.onceFire.setAutoRepeat(1);
                    this.onceFire.start();
                    this.onceFire.scaleByCanvas = true;
                }
                RLottieDrawable rLottieDrawable2 = this.onceFire;
                RectF rectF3 = this.radialProgress.progressRect;
                rLottieDrawable2.setBounds((int) rectF3.left, (int) rectF3.top, (int) rectF3.right, (int) rectF3.bottom);
                if (this.onceRadialPaint == null) {
                    this.onceRadialPaint = new Paint(1);
                }
                if (this.onceRadialCutPaint == null) {
                    Paint paint2 = new Paint(1);
                    this.onceRadialCutPaint = paint2;
                    paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
                }
                if (this.onceRadialStrokePaint == null) {
                    Paint paint3 = new Paint(1);
                    this.onceRadialStrokePaint = paint3;
                    paint3.setStyle(Paint.Style.STROKE);
                }
                RadialProgress2 radialProgress2 = this.radialProgress;
                int i = radialProgress2.iconColorKey;
                int themedColor = i >= 0 ? getThemedColor(i) : radialProgress2.iconColor;
                this.onceRadialPaint.setColor(themedColor);
                this.onceRadialStrokePaint.setColor(themedColor);
                this.radialProgress.mediaActionDrawable.applyShaderMatrix(false);
                this.onceRadialPaint.setShader(this.radialProgress.mediaActionDrawable.paint2.getShader());
                this.onceRadialStrokePaint.setShader(this.radialProgress.mediaActionDrawable.paint2.getShader());
                rectF.set(this.onceFire.getBounds());
                canvas2.saveLayerAlpha(rectF, 255, 31);
                rectF.inset(1.0f, 1.0f);
                canvas2.drawRect(rectF, this.onceRadialPaint);
                this.onceFire.draw(canvas2, this.onceRadialCutPaint);
                canvas2.restore();
                canvas2.restore();
                this.onceRadialStrokePaint.setAlpha((int) (255.0f * f2));
                this.onceRadialStrokePaint.setStrokeWidth(AndroidUtilities.m1146dp(1.66f));
                this.rect.set(this.radialProgress.progressRect);
                this.rect.inset(AndroidUtilities.m1146dp(3.0f), AndroidUtilities.m1146dp(3.0f));
                canvas2.drawArc(this.rect, -90.0f, (1.0f - this.seekBarWaveform.explodeProgress) * (-360.0f), false, this.onceRadialStrokePaint);
                if (this.timerParticles == null) {
                    this.timerParticles = new TimerParticles();
                }
                this.timerParticles.draw(canvas, this.onceRadialStrokePaint, this.rect, (1.0f - this.seekBarWaveform.explodeProgress) * (-360.0f), f2);
                canvas2 = canvas;
            } else {
                RLottieDrawable rLottieDrawable3 = this.onceFire;
                if (rLottieDrawable3 != null) {
                    rLottieDrawable3.recycle(true);
                    this.onceFire = null;
                    if (this.timerParticles != null) {
                        this.timerParticles = null;
                    }
                }
            }
            canvas2.drawCircle(fCenterX, fCenterY, AndroidUtilities.m1146dp((f * 1.5f) + 10.0f) * f, this.onceClearPaint);
            canvas2.restore();
            if (this.oncePeriod == null) {
                CaptionContainerView.PeriodDrawable periodDrawable = new CaptionContainerView.PeriodDrawable(3);
                this.oncePeriod = periodDrawable;
                periodDrawable.updateColors(-1, 0, 0);
                CaptionContainerView.PeriodDrawable periodDrawable2 = this.oncePeriod;
                periodDrawable2.diameterDp = 14.0f;
                periodDrawable2.setTextSize(10.0f);
                this.oncePeriod.strokePaint.setStrokeWidth(AndroidUtilities.dpf2(1.5f));
                this.oncePeriod.setValue(1, false, false);
                this.oncePeriod.textOffsetX = -AndroidUtilities.dpf2(0.33f);
                this.oncePeriod.textOffsetY = AndroidUtilities.dpf2(0.33f);
            }
            CaptionContainerView.PeriodDrawable periodDrawable3 = this.oncePeriod;
            periodDrawable3.diameterDp = f * 14.0f;
            periodDrawable3.setTextSize(f * 10.0f);
            canvas2.saveLayerAlpha(fCenterX - AndroidUtilities.m1146dp(10.0f), fCenterY - AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f) + fCenterX, AndroidUtilities.m1146dp(10.0f) + fCenterY, 255, 31);
            canvas2.drawCircle(fCenterX, fCenterY, AndroidUtilities.m1146dp(10.0f) * f, this.radialProgress.circlePaint);
            this.oncePeriod.setClear(AndroidUtilities.computePerceivedBrightness(this.radialProgress.circlePaint.getColor()) > 0.8f);
            this.oncePeriod.setCenterXY(fCenterX, fCenterY);
            this.oncePeriod.draw(canvas2, f);
            canvas2.restore();
            return;
        }
        runnable.run();
    }

    /* JADX WARN: Removed duplicated region for block: B:680:0x131c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawLinkPreview(android.graphics.Canvas r48, float r49) {
        /*
            Method dump skipped, instructions count: 5462
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawLinkPreview(android.graphics.Canvas, float):void");
    }

    public void drawFactCheck(Canvas canvas, float f) {
        int iM1146dp;
        int extraTextX;
        int iM1146dp2;
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if (groupedMessagePosition == null || groupedMessagePosition.last) {
            float fLerp = this.hasFactCheck ? 1.0f : 0.0f;
            TransitionParams transitionParams = this.transitionParams;
            if (transitionParams.animateFactCheck) {
                fLerp = AndroidUtilities.lerp(1.0f - fLerp, fLerp, transitionParams.animateChangeProgress);
            }
            float f2 = f * fLerp;
            if (f2 <= 0.0f) {
                return;
            }
            float backgroundDrawableRight = getBackgroundDrawableRight();
            TransitionParams transitionParams2 = this.transitionParams;
            float fM1146dp = ((backgroundDrawableRight + (transitionParams2 != null ? transitionParams2.deltaRight : 0.0f)) - AndroidUtilities.m1146dp(10 + ((!this.currentMessageObject.isOutOwner() || this.mediaBackground || this.drawPinnedBottom) ? 0 : 6))) - getExtraTextX();
            MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
            if (groupedMessages != null && !groupedMessages.isDocuments) {
                iM1146dp2 = (int) this.captionX;
            } else if (this.currentMessageObject.isOutOwner()) {
                iM1146dp2 = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(12.0f) + getExtraTextX();
                if (this.currentMessageObject.type == 19) {
                    iM1146dp2 -= Math.max(0, ((Math.max(this.replyNameWidth, this.replyTextWidth) + iM1146dp2) + AndroidUtilities.m1146dp(14.0f)) - AndroidUtilities.displaySize.x);
                }
            } else {
                if (this.mediaBackground) {
                    iM1146dp = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(12.0f);
                    extraTextX = getExtraTextX();
                } else {
                    iM1146dp = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(this.drawPinnedBottom ? 12.0f : 18.0f);
                    extraTextX = getExtraTextX();
                }
                iM1146dp2 = iM1146dp + extraTextX;
            }
            int iM1146dp3 = ((int) (iM1146dp2 + this.transitionParams.deltaLeft)) - AndroidUtilities.m1146dp(1.33f);
            int i = this.factCheckY;
            float f3 = iM1146dp3;
            int i2 = (int) (fM1146dp - f3);
            int iLerp = this.factCheckHeight;
            TransitionParams transitionParams3 = this.transitionParams;
            if (transitionParams3.animateFactCheckHeight) {
                iLerp = AndroidUtilities.lerp(transitionParams3.animateFactCheckHeightFrom, iLerp, transitionParams3.animateChangeProgress);
            }
            float f4 = (!this.factCheckLarge || (getPrimaryMessageObject() != null && getPrimaryMessageObject().factCheckExpanded)) ? 1.0f : 0.0f;
            TransitionParams transitionParams4 = this.transitionParams;
            if (transitionParams4.animateFactCheckExpanded) {
                AndroidUtilities.lerp(1.0f - f4, f4, transitionParams4.animateChangeProgress);
            }
            if (this.factCheckLine == null) {
                this.factCheckLine = new ReplyMessageLine(this);
            }
            int factCheck = this.factCheckLine.setFactCheck(this.resourcesProvider);
            canvas.save();
            canvas.translate(f3, i);
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(0.0f, 0.0f, i2, iLerp);
            ButtonBounce buttonBounce = this.factCheckBounce;
            float scale = buttonBounce != null ? buttonBounce.getScale(0.01f) : 1.0f;
            canvas.scale(scale, scale, rectF.centerX(), rectF.centerY());
            this.factCheckLine.drawBackground(canvas, rectF, 5.0f, 5.0f, 5.0f, f2, false, false);
            this.factCheckLine.drawLine(canvas, rectF, f2);
            Text text = this.factCheckTitle;
            if (text != null) {
                text.draw(canvas, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(12.0f), factCheck, f2);
                if (this.factCheckWhat != null) {
                    rectF.set((int) (AndroidUtilities.m1146dp(10.0f) + this.factCheckTitle.getCurrentWidth() + AndroidUtilities.m1146dp(4.0f)), AndroidUtilities.m1146dp(4.33f), AndroidUtilities.m1146dp(10.0f) + r0 + this.factCheckWhat.getCurrentWidth(), AndroidUtilities.m1146dp(21.66f));
                    ButtonBounce buttonBounce2 = this.factCheckWhatBounce;
                    float scale2 = buttonBounce2 != null ? buttonBounce2.getScale(0.1f) : 1.0f;
                    canvas.save();
                    canvas.scale(scale2, scale2, rectF.centerX(), rectF.centerY());
                    canvas.drawRoundRect(rectF, AndroidUtilities.m1146dp(17.0f), AndroidUtilities.m1146dp(17.0f), this.factCheckLine.backgroundPaint);
                    this.factCheckWhat.draw(canvas, r0 + AndroidUtilities.m1146dp(5.0f), AndroidUtilities.m1146dp(12.0f), factCheck, f2);
                    canvas.restore();
                }
            }
            canvas.restore();
        }
    }

    public void drawFactCheckText(Canvas canvas, float f) {
        int iM1146dp;
        int extraTextX;
        int iM1146dp2;
        RectF rectF;
        float f2;
        float f3;
        float f4;
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if (groupedMessagePosition == null || groupedMessagePosition.last) {
            float fLerp = this.hasFactCheck ? 1.0f : 0.0f;
            TransitionParams transitionParams = this.transitionParams;
            if (transitionParams.animateFactCheck) {
                fLerp = AndroidUtilities.lerp(1.0f - fLerp, fLerp, transitionParams.animateChangeProgress);
            }
            float f5 = f * fLerp;
            if (f5 <= 0.0f) {
                return;
            }
            float backgroundDrawableRight = getBackgroundDrawableRight();
            TransitionParams transitionParams2 = this.transitionParams;
            float fM1146dp = ((backgroundDrawableRight + (transitionParams2 != null ? transitionParams2.deltaRight : 0.0f)) - AndroidUtilities.m1146dp(10 + ((!this.currentMessageObject.isOutOwner() || this.mediaBackground || this.drawPinnedBottom) ? 0 : 6))) - getExtraTextX();
            MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
            if (groupedMessages != null && !groupedMessages.isDocuments) {
                iM1146dp2 = (int) this.captionX;
            } else {
                if (this.currentMessageObject.isOutOwner()) {
                    iM1146dp2 = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(12.0f) + getExtraTextX();
                    if (this.currentMessageObject.type == 19) {
                        iM1146dp2 -= Math.max(0, ((Math.max(this.replyNameWidth, this.replyTextWidth) + iM1146dp2) + AndroidUtilities.m1146dp(14.0f)) - AndroidUtilities.displaySize.x);
                    }
                } else {
                    if (this.mediaBackground) {
                        iM1146dp = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(12.0f);
                        extraTextX = getExtraTextX();
                    } else {
                        iM1146dp = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(this.drawPinnedBottom ? 12.0f : 18.0f);
                        extraTextX = getExtraTextX();
                    }
                    iM1146dp2 = iM1146dp + extraTextX;
                }
            }
            int iM1146dp3 = ((int) (iM1146dp2 + this.transitionParams.deltaLeft)) - AndroidUtilities.m1146dp(1.33f);
            int i = this.factCheckY;
            float f6 = iM1146dp3;
            int i2 = (int) (fM1146dp - f6);
            int iLerp = this.factCheckHeight;
            TransitionParams transitionParams3 = this.transitionParams;
            if (transitionParams3.animateFactCheckHeight) {
                iLerp = AndroidUtilities.lerp(transitionParams3.animateFactCheckHeightFrom, iLerp, transitionParams3.animateChangeProgress);
            }
            int i3 = iLerp;
            float fLerp2 = (!this.factCheckLarge || (getPrimaryMessageObject() != null && getPrimaryMessageObject().factCheckExpanded)) ? 1.0f : 0.0f;
            TransitionParams transitionParams4 = this.transitionParams;
            if (transitionParams4.animateFactCheckExpanded) {
                fLerp2 = AndroidUtilities.lerp(1.0f - fLerp2, fLerp2, transitionParams4.animateChangeProgress);
            }
            float f7 = fLerp2;
            if (this.factCheckLine == null) {
                this.factCheckLine = new ReplyMessageLine(this);
            }
            int factCheck = this.factCheckLine.setFactCheck(this.resourcesProvider);
            canvas.save();
            canvas.translate(f6, i);
            RectF rectF2 = AndroidUtilities.rectTmp;
            float f8 = i2;
            float f9 = i3;
            rectF2.set(0.0f, 0.0f, f8, f9);
            ButtonBounce buttonBounce = this.factCheckBounce;
            float scale = buttonBounce != null ? buttonBounce.getScale(0.01f) : 1.0f;
            canvas.scale(scale, scale, rectF2.centerX(), rectF2.centerY());
            if (this.factCheckTextLayout != null) {
                if (this.factCheckLarge) {
                    rectF = rectF2;
                    f2 = f9;
                    f3 = 1.0f;
                    canvas.saveLayerAlpha(0.0f, 0.0f, f8, i3 - 1, 255, 31);
                } else {
                    rectF = rectF2;
                    f2 = f9;
                    f3 = 1.0f;
                }
                canvas.save();
                canvas.translate(AndroidUtilities.m1146dp(10.0f) - this.factCheckTextLayoutLeft, AndroidUtilities.m1146dp(22.0f));
                Theme.chat_replyTextPaint.linkColor = factCheck;
                LinkSpanDrawable.LinkCollector linkCollector = this.factCheckLinks;
                if (linkCollector != null && linkCollector.draw(canvas)) {
                    invalidateOutbounds();
                }
                if (this.currentMessageObject.isOutOwner()) {
                    Theme.chat_replyTextPaint.setColor(getThemedColor(Theme.key_chat_messageTextOut));
                } else {
                    Theme.chat_replyTextPaint.setColor(getThemedColor(Theme.key_chat_messageTextIn));
                }
                int alpha = Theme.chat_replyTextPaint.getAlpha();
                Theme.chat_replyTextPaint.setAlpha((int) (alpha * f5));
                ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
                if (chatMessageCellDelegate == null || chatMessageCellDelegate.getTextSelectionHelper() == null || !getDelegate().getTextSelectionHelper().isSelected(this.currentMessageObject)) {
                    f4 = 10.0f;
                } else {
                    f4 = 10.0f;
                    this.delegate.getTextSelectionHelper().drawFactCheck(this.currentMessageObject.isOutOwner(), this.factCheckTextLayout, canvas);
                }
                this.factCheckTextLayout.draw(canvas);
                canvas.restore();
                Theme.chat_replyTextPaint.setAlpha(alpha);
                rectF.set(AndroidUtilities.m1146dp(f4), AndroidUtilities.m1146dp(22.0f) + this.factCheckTextLayoutHeight + AndroidUtilities.m1146dp(6.33f), i2 - AndroidUtilities.m1146dp(f4), AndroidUtilities.m1146dp(22.0f) + this.factCheckTextLayoutHeight + AndroidUtilities.m1146dp(6.33f) + 1);
                Theme.chat_titleLabelTextPaint.setColor(factCheck);
                Theme.chat_titleLabelTextPaint.setAlpha((int) (r2.getAlpha() * 0.5f * f5));
                canvas.drawRect(rectF, Theme.chat_titleLabelTextPaint);
                canvas.save();
                canvas.translate(AndroidUtilities.m1146dp(f4) - this.factCheckText2LayoutLeft, AndroidUtilities.m1146dp(22.0f) + this.factCheckTextLayoutHeight + AndroidUtilities.m1146dp(12.66f));
                Theme.chat_titleLabelTextPaint.setColor(factCheck);
                Theme.chat_titleLabelTextPaint.setAlpha((int) (r2.getAlpha() * f5));
                this.factCheckText2Layout.draw(canvas);
                canvas.restore();
                if (this.clip == null) {
                    this.clip = new GradientClip();
                }
                if (this.factCheckLarge) {
                    canvas.save();
                    int iM1146dp4 = AndroidUtilities.m1146dp((AndroidUtilities.m1146dp(22.0f) + this.factCheckTextLayoutHeight) + AndroidUtilities.m1146dp(12.66f) < i3 ? 20.0f : 24.0f);
                    rectF.set(0.0f, (i3 - AndroidUtilities.m1146dp(6.66f)) - iM1146dp4, f8, f2);
                    float f10 = (f3 - f7) * f5;
                    this.clip.draw(canvas, rectF, 3, f10);
                    float f11 = i3 - iM1146dp4;
                    rectF.set(i2 - AndroidUtilities.m1146dp(60.0f), f11, i2 - AndroidUtilities.m1146dp(32.0f), f2);
                    this.clip.draw(canvas, rectF, 2, f10);
                    rectF.set(i2 - AndroidUtilities.m1146dp(32.0f), f11, f8, f2);
                    canvas.drawRect(rectF, this.clip.getPaint(2, f10));
                    canvas.restore();
                    canvas.restore();
                }
            }
            if (this.factCheckLarge) {
                if (this.factCheckArrow == null) {
                    Drawable drawableMutate = getContext().getResources().getDrawable(C2369R.drawable.arrow_more).mutate();
                    this.factCheckArrow = drawableMutate;
                    this.factCheckArrowColor = factCheck;
                    drawableMutate.setColorFilter(new PorterDuffColorFilter(factCheck, PorterDuff.Mode.SRC_IN));
                }
                if (factCheck != this.factCheckArrowColor) {
                    Drawable drawable = this.factCheckArrow;
                    this.factCheckArrowColor = factCheck;
                    drawable.setColorFilter(new PorterDuffColorFilter(factCheck, PorterDuff.Mode.SRC_IN));
                }
                canvas.save();
                int iM1146dp5 = AndroidUtilities.m1146dp(16.0f);
                this.factCheckArrow.setBounds((i2 - iM1146dp5) - AndroidUtilities.m1146dp(7.0f), (i3 - iM1146dp5) - AndroidUtilities.m1146dp(5.0f), i2 - AndroidUtilities.m1146dp(7.0f), i3 - AndroidUtilities.m1146dp(5.0f));
                canvas.rotate(AndroidUtilities.lerp(0, Opcodes.GETFIELD, f7), this.factCheckArrow.getBounds().centerX(), this.factCheckArrow.getBounds().centerY());
                this.factCheckArrow.setAlpha((int) (f5 * 255.0f));
                this.factCheckArrow.draw(canvas);
                canvas.restore();
            }
            canvas.restore();
        }
    }

    private float isSmallImage() {
        TransitionParams transitionParams = this.transitionParams;
        if (!transitionParams.animateSmallImage) {
            return this.isSmallImage ? 1.0f : 0.0f;
        }
        boolean z = this.isSmallImage;
        float f = transitionParams.animateChangeProgress;
        return z ? f : 1.0f - f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldDrawMenuDrawable() {
        if ((this.currentMessagesGroup != null && (this.currentPosition.flags & 4) == 0) || this.hasLinkPreview) {
            return false;
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null) {
            return (messageObject.isRepostPreview || messageObject.isSponsored()) ? false : true;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0340 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x031e  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x033d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawContact(android.graphics.Canvas r22) {
        /*
            Method dump skipped, instructions count: 847
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawContact(android.graphics.Canvas):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x02ba A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0355  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0361  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0368  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x037e  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0383  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x03f7  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x03fa  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0406  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0435  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0261  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x029d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawBotButtons(android.graphics.Canvas r23, java.util.ArrayList r24, int r25) {
        /*
            Method dump skipped, instructions count: 1323
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawBotButtons(android.graphics.Canvas, java.util.ArrayList, int):void");
    }

    private boolean allowDrawPhotoImage() {
        return !this.currentMessageObject.hasMediaSpoilers() || this.currentMessageObject.isMediaSpoilersRevealed || this.mediaSpoilerRevealProgress != 0.0f || this.blurredPhotoImage.getBitmap() == null;
    }

    public void layoutTextXY(boolean z) {
        int iM1146dp;
        int i;
        int iM1146dp2;
        int iM1146dp3;
        if (this.currentMessageObject.isOutOwner()) {
            this.textX = (z ? (int) (this.backgroundDrawableLeft + this.transitionParams.deltaLeft) : getCurrentBackgroundLeft()) + AndroidUtilities.m1146dp(11.0f) + getExtraTextX();
        } else {
            int currentBackgroundLeft = z ? (int) (this.backgroundDrawableLeft + this.transitionParams.deltaLeft) : getCurrentBackgroundLeft();
            if (this.currentMessageObject.type == 19) {
                iM1146dp = 0;
            } else {
                iM1146dp = AndroidUtilities.m1146dp((this.mediaBackground || !this.drawPinnedBottom) ? 17.0f : 11.0f);
            }
            this.textX = currentBackgroundLeft + iM1146dp + getExtraTextX();
        }
        if (this.hasGamePreview) {
            this.textX += AndroidUtilities.m1146dp(11.0f);
            int iM1146dp4 = AndroidUtilities.m1146dp(14.0f) + this.namesOffset;
            this.textY = iM1146dp4;
            StaticLayout staticLayout = this.siteNameLayout;
            if (staticLayout != null) {
                this.textY = iM1146dp4 + staticLayout.getLineBottom(staticLayout.getLineCount() - 1);
            }
        } else if (this.hasInvoicePreview) {
            int iM1146dp5 = AndroidUtilities.m1146dp(14.0f) + this.namesOffset;
            this.textY = iM1146dp5;
            StaticLayout staticLayout2 = this.siteNameLayout;
            if (staticLayout2 != null) {
                this.textY = iM1146dp5 + staticLayout2.getLineBottom(staticLayout2.getLineCount() - 1);
            }
        } else if (this.currentMessageObject.type == 19) {
            this.textY = AndroidUtilities.m1146dp(6.0f) + this.namesOffset;
            if (!this.currentMessageObject.isOut()) {
                this.textX = getCurrentBackgroundLeft();
            } else {
                this.textX -= AndroidUtilities.m1146dp(4.0f);
            }
        } else {
            int iM1146dp6 = AndroidUtilities.m1146dp(8.0f) + this.namesOffset;
            this.textY = iM1146dp6;
            if (this.currentMessageObject.hasCodeAtTop && (i = SharedConfig.bubbleRadius) > 10) {
                this.textY = iM1146dp6 + AndroidUtilities.m1146dp(i < 15 ? 1.0f : 2.0f);
            }
            if (this.currentMessageObject.hasCodeAtTop && this.namesOffset > 0) {
                this.textY += AndroidUtilities.m1146dp(5.0f);
            }
        }
        if (this.currentMessageObject.isSponsored()) {
            this.linkPreviewY = this.textY + AndroidUtilities.m1146dp(14.0f);
        } else if (this.linkPreviewAbove) {
            this.linkPreviewY = this.textY + AndroidUtilities.m1146dp(10.0f);
            this.textY += this.linkPreviewHeight + AndroidUtilities.m1146dp(13.0f);
            if (this.drawInstantView && !this.hasInvoicePreview && !this.currentMessageObject.isGiveawayOrGiveawayResults()) {
                this.textY += AndroidUtilities.m1146dp(44.0f);
            }
        } else {
            this.linkPreviewY = this.textY + this.currentMessageObject.textHeight(this.transitionParams) + AndroidUtilities.m1146dp(10.0f);
        }
        if (this.linkPreviewAbove) {
            iM1146dp2 = this.textY + this.currentMessageObject.textHeight(this.transitionParams);
            iM1146dp3 = AndroidUtilities.m1146dp(10.0f);
        } else {
            iM1146dp2 = this.linkPreviewY + this.linkPreviewHeight + AndroidUtilities.m1146dp(this.drawInstantView ? 46.0f : 0.0f);
            iM1146dp3 = AndroidUtilities.m1146dp(this.linkPreviewHeight <= 0 ? -8.0f : 4.0f);
        }
        this.factCheckY = iM1146dp2 + iM1146dp3;
        this.unmovedTextX = this.textX;
        if (this.currentMessageObject.textXOffset == 0.0f || this.replyNameLayout == null) {
            return;
        }
        int iM1146dp7 = this.backgroundWidth - AndroidUtilities.m1146dp(31.0f);
        MessageObject messageObject = this.currentMessageObject;
        int iM1146dp8 = iM1146dp7 - messageObject.textWidth;
        if (!this.hasNewLineForTime) {
            iM1146dp8 -= this.timeWidth + AndroidUtilities.m1146dp((messageObject.isOutOwner() ? 20 : 0) + 4);
        }
        if (iM1146dp8 > 0) {
            this.textX += iM1146dp8 - getExtraTimeX();
        }
    }

    public void drawMessageText(Canvas canvas) {
        float f;
        Canvas canvas2;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.isSponsored()) {
            return;
        }
        int i = this.textY;
        float fTextHeight = i;
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateText) {
            float f2 = transitionParams.animateFromTextY;
            float f3 = transitionParams.animateChangeProgress;
            fTextHeight = (f2 * (1.0f - f3)) + (i * f3);
        }
        if (transitionParams.animateChangeProgress != 1.0f && transitionParams.animateMessageText) {
            canvas.save();
            Theme.MessageDrawable messageDrawable = this.currentBackgroundDrawable;
            if (messageDrawable != null) {
                Rect bounds = messageDrawable.getBounds();
                if (this.currentMessageObject.isOutOwner() && !this.mediaBackground && !this.pinnedBottom) {
                    canvas.clipRect(bounds.left + AndroidUtilities.m1146dp(4.0f), bounds.top + AndroidUtilities.m1146dp(4.0f), bounds.right - AndroidUtilities.m1146dp(10.0f), bounds.bottom - AndroidUtilities.m1146dp(4.0f));
                } else {
                    canvas.clipRect(bounds.left + AndroidUtilities.m1146dp(4.0f), bounds.top + AndroidUtilities.m1146dp(4.0f), bounds.right - AndroidUtilities.m1146dp(4.0f), bounds.bottom - AndroidUtilities.m1146dp(4.0f));
                }
            }
            MultiLayoutTypingAnimator multiLayoutTypingAnimator = this.botDraftTypingAnimator;
            if (multiLayoutTypingAnimator != null && multiLayoutTypingAnimator.isRunning()) {
                float f4 = this.textX;
                MessageObject messageObject2 = this.currentMessageObject;
                drawMessageText(f4, fTextHeight, canvas, messageObject2.textLayoutBlocks, messageObject2.textXOffset, true, 1.0f, true, false, false);
                canvas2 = canvas;
            } else {
                drawMessageText(this.textX, fTextHeight, canvas, this.transitionParams.animateOutTextBlocks, this.transitionParams.animateOutTextXOffset, false, 1.0f - this.transitionParams.animateChangeProgress, true, false, false);
                float f5 = this.textX;
                MessageObject messageObject3 = this.currentMessageObject;
                canvas2 = canvas;
                drawMessageText(f5, fTextHeight, canvas2, messageObject3.textLayoutBlocks, messageObject3.textXOffset, true, this.transitionParams.animateChangeProgress, true, false, false);
            }
            canvas2.restore();
            return;
        }
        boolean z = transitionParams.animateLinkAbove;
        if (z && this.currentBackgroundDrawable != null) {
            if (z) {
                float f6 = i;
                float fTextHeight2 = (this.linkPreviewAbove ? 1 : -1) * this.currentMessageObject.textHeight(transitionParams);
                TransitionParams transitionParams2 = this.transitionParams;
                f = (fTextHeight2 * (1.0f - transitionParams2.animateChangeProgress)) + f6;
                fTextHeight = transitionParams2.animateFromTextY - (((this.linkPreviewAbove ? 1 : -1) * this.currentMessageObject.textHeight(transitionParams2)) * this.transitionParams.animateChangeProgress);
            } else {
                f = fTextHeight;
            }
            canvas.save();
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(this.currentBackgroundDrawable.getBounds());
            if (this.currentMessageObject.isOutOwner() && !this.mediaBackground && !this.pinnedBottom) {
                rectF.left += AndroidUtilities.m1146dp(4.0f);
                rectF.right -= AndroidUtilities.m1146dp(10.0f);
            } else {
                rectF.left += AndroidUtilities.m1146dp(4.0f);
                rectF.right -= AndroidUtilities.m1146dp(4.0f);
            }
            float f7 = rectF.left;
            float f8 = this.transitionParams.animateFromTextY;
            canvas.clipRect(f7, f8, rectF.right, this.currentMessageObject.textHeight(r4) + f8 + AndroidUtilities.m1146dp(4.0f));
            float f9 = this.textX;
            MessageObject messageObject4 = this.currentMessageObject;
            drawMessageText(f9, fTextHeight, canvas, messageObject4.textLayoutBlocks, messageObject4.textXOffset, false, 1.0f - this.transitionParams.animateChangeProgress, true, false, false);
            canvas.restore();
            canvas.save();
            rectF.set(this.currentBackgroundDrawable.getBounds());
            if (this.currentMessageObject.isOutOwner() && !this.mediaBackground && !this.pinnedBottom) {
                rectF.left += AndroidUtilities.m1146dp(4.0f);
                rectF.right -= AndroidUtilities.m1146dp(10.0f);
            } else {
                rectF.left += AndroidUtilities.m1146dp(4.0f);
                rectF.right -= AndroidUtilities.m1146dp(4.0f);
            }
            canvas.clipRect(rectF.left, this.textY, rectF.right, r2 + this.currentMessageObject.textHeight(this.transitionParams) + AndroidUtilities.m1146dp(4.0f));
            float f10 = this.textX;
            MessageObject messageObject5 = this.currentMessageObject;
            drawMessageText(f10, f, canvas, messageObject5.textLayoutBlocks, messageObject5.textXOffset, true, 1.0f, true, false, false);
            canvas.restore();
            return;
        }
        float f11 = this.textX;
        MessageObject messageObject6 = this.currentMessageObject;
        drawMessageText(f11, fTextHeight, canvas, messageObject6.textLayoutBlocks, messageObject6.textXOffset, true, 1.0f, true, false, false);
    }

    public void drawMessageText(Canvas canvas, ArrayList arrayList, boolean z, float f, boolean z2) {
        int i = this.textY;
        float f2 = i;
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateText) {
            float f3 = transitionParams.animateFromTextY;
            float f4 = transitionParams.animateChangeProgress;
            f2 = (f3 * (1.0f - f4)) + (i * f4);
        }
        float f5 = this.textX;
        MessageObject messageObject = this.currentMessageObject;
        drawMessageText(f5, f2, canvas, arrayList, messageObject == null ? 0.0f : messageObject.textXOffset, z, f, false, z2, false);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(22:172|(3:174|(1:176)(1:177)|178)(1:179)|180|(2:(1:185)|186)|187|(1:189)(1:190)|191|(19:193|(1:195)|196|(1:198)|199|(1:203)|204|(1:206)(1:207)|208|(1:210)|211|(1:213)|214|(1:216)|217|(1:219)(1:220)|221|(1:232)(5:225|(1:227)|228|(1:230)|231)|233)(5:234|(1:236)(1:237)|238|(20:240|(1:242)|243|(1:249)|250|(1:252)(1:253)|254|(1:256)(1:257)|258|(1:260)|261|(1:275)(2:266|(9:270|274|273|276|(3:278|(1:280)(1:281)|282)(1:283)|284|(1:286)(1:287)|288|(1:290))(1:271))|272|273|276|(0)(0)|284|(0)(0)|288|(0))(1:291)|292)|293|(12:300|(1:305)(1:304)|306|(1:313)(1:312)|314|(1:316)(1:317)|318|(1:320)(1:321)|322|(2:325|323)|407|326)|327|(1:(1:335)(1:336))|392|337|(1:352)(5:341|(2:350|351)(2:343|(3:345|350|351)(2:349|351))|374|(2:382|405)(4:378|(1:380)|381|406)|383)|(10:354|355|390|356|357|(1:359)(1:360)|361|394|362|363)(1:369)|364|396|370|374|(3:376|382|405)(0)|383) */
    /* JADX WARN: Code restructure failed: missing block: B:372:0x0952, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0332  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03c0  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x03ce  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x03e7  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x040a  */
    /* JADX WARN: Removed duplicated region for block: B:190:0x040d  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x041e  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0681  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0778  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x079f  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x07ac  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x07bf  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x07cd  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x081a  */
    /* JADX WARN: Removed duplicated region for block: B:313:0x082f  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x0843  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0847  */
    /* JADX WARN: Removed duplicated region for block: B:320:0x0852  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0857  */
    /* JADX WARN: Removed duplicated region for block: B:325:0x0868 A[LOOP:3: B:323:0x0860->B:325:0x0868, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:335:0x088f  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x089b  */
    /* JADX WARN: Removed duplicated region for block: B:352:0x08f3  */
    /* JADX WARN: Removed duplicated region for block: B:354:0x08f6 A[Catch: Exception -> 0x08d7, TRY_LEAVE, TryCatch #1 {Exception -> 0x08d7, blocks: (B:337:0x08a6, B:339:0x08af, B:341:0x08b5, B:343:0x08bd, B:345:0x08c5, B:351:0x08ef, B:354:0x08f6), top: B:392:0x08a6 }] */
    /* JADX WARN: Removed duplicated region for block: B:369:0x093c  */
    /* JADX WARN: Removed duplicated region for block: B:376:0x095a  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x0992  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x09a7  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x09be  */
    /* JADX WARN: Removed duplicated region for block: B:411:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x018c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawMessageText(float r41, float r42, android.graphics.Canvas r43, java.util.ArrayList r44, float r45, boolean r46, float r47, boolean r48, boolean r49, boolean r50) {
        /*
            Method dump skipped, instructions count: 2498
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawMessageText(float, float, android.graphics.Canvas, java.util.ArrayList, float, boolean, float, boolean, boolean, boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.telegram.p023ui.Components.AnimatedEmojiSpan[] getAnimatedEmojiSpans() {
        /*
            r7 = this;
            org.telegram.messenger.MessageObject r0 = r7.currentMessageObject
            java.lang.Class<org.telegram.ui.Components.AnimatedEmojiSpan> r1 = org.telegram.p023ui.Components.AnimatedEmojiSpan.class
            r2 = 0
            r3 = 0
            if (r0 == 0) goto L1c
            java.lang.CharSequence r0 = r0.messageText
            boolean r4 = r0 instanceof android.text.Spanned
            if (r4 == 0) goto L1c
            r4 = r0
            android.text.Spanned r4 = (android.text.Spanned) r4
            int r0 = r0.length()
            java.lang.Object[] r0 = r4.getSpans(r3, r0, r1)
            org.telegram.ui.Components.AnimatedEmojiSpan[] r0 = (org.telegram.p023ui.Components.AnimatedEmojiSpan[]) r0
            goto L1d
        L1c:
            r0 = r2
        L1d:
            org.telegram.messenger.MessageObject r4 = r7.currentMessageObject
            if (r4 == 0) goto L35
            java.lang.CharSequence r4 = r4.caption
            boolean r5 = r4 instanceof android.text.Spanned
            if (r5 == 0) goto L35
            r5 = r4
            android.text.Spanned r5 = (android.text.Spanned) r5
            int r4 = r4.length()
            java.lang.Object[] r1 = r5.getSpans(r3, r4, r1)
            org.telegram.ui.Components.AnimatedEmojiSpan[] r1 = (org.telegram.p023ui.Components.AnimatedEmojiSpan[]) r1
            goto L36
        L35:
            r1 = r2
        L36:
            if (r0 == 0) goto L3b
            int r4 = r0.length
            if (r4 != 0) goto L41
        L3b:
            if (r1 == 0) goto L6d
            int r4 = r1.length
            if (r4 != 0) goto L41
            goto L6d
        L41:
            if (r0 != 0) goto L45
            r2 = 0
            goto L46
        L45:
            int r2 = r0.length
        L46:
            if (r1 != 0) goto L4a
            r4 = 0
            goto L4b
        L4a:
            int r4 = r1.length
        L4b:
            int r2 = r2 + r4
            org.telegram.ui.Components.AnimatedEmojiSpan[] r2 = new org.telegram.p023ui.Components.AnimatedEmojiSpan[r2]
            if (r0 == 0) goto L5e
            r4 = 0
            r5 = 0
        L52:
            int r6 = r0.length
            if (r4 >= r6) goto L5f
            r6 = r0[r4]
            r2[r5] = r6
            int r4 = r4 + 1
            int r5 = r5 + 1
            goto L52
        L5e:
            r5 = 0
        L5f:
            if (r1 == 0) goto L6d
        L61:
            int r0 = r1.length
            if (r3 >= r0) goto L6d
            r0 = r1[r3]
            r2[r5] = r0
            int r3 = r3 + 1
            int r5 = r5 + 1
            goto L61
        L6d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.getAnimatedEmojiSpans():org.telegram.ui.Components.AnimatedEmojiSpan[]");
    }

    public void updateCaptionLayout() {
        float imageX;
        float imageY;
        float imageHeight;
        float fM1146dp;
        int i;
        int iM1146dp;
        MessageObject messageObject = this.currentMessageObject;
        int i2 = messageObject.type;
        boolean z = true;
        if (i2 == 1 || i2 == 20 || this.documentAttachType == 4 || i2 == 8 || i2 == 23) {
            TransitionParams transitionParams = this.transitionParams;
            if (transitionParams.imageChangeBoundsTransition) {
                imageX = transitionParams.animateToImageX;
                imageY = transitionParams.animateToImageY;
                imageHeight = transitionParams.animateToImageH;
            } else {
                imageX = this.photoImage.getImageX();
                imageY = this.photoImage.getImageY();
                imageHeight = this.photoImage.getImageHeight();
            }
            this.captionX = imageX + AndroidUtilities.m1146dp(5.0f) + this.captionOffsetX;
            float fM1146dp2 = imageY + imageHeight + AndroidUtilities.m1146dp(6.0f);
            this.captionY = imageY + AndroidUtilities.lerp(imageHeight + AndroidUtilities.m1146dp(6.0f), (this.captionLayout == null ? 0 : -r6.textHeight()) - AndroidUtilities.m1146dp(4.0f), mediaAbove());
            fM1146dp = fM1146dp2;
            z = false;
        } else {
            float f = 43.0f;
            float f2 = 0.0f;
            if (this.hasOldCaptionPreview) {
                this.captionX = this.backgroundDrawableLeft + AndroidUtilities.m1146dp(messageObject.isOutOwner() ? 11.0f : 17.0f) + this.captionOffsetX;
                int iM1146dp2 = (((this.totalHeight - this.captionHeight) - AndroidUtilities.m1146dp(this.drawPinnedTop ? 9.0f : 10.0f)) - this.linkPreviewHeight) - AndroidUtilities.m1146dp(17.0f);
                if (!this.drawCommentButton || this.drawSideButton == 3) {
                    f = 0.0f;
                } else if (shouldDrawTimeOnMedia()) {
                    f = 41.3f;
                }
                fM1146dp = iM1146dp2 - AndroidUtilities.m1146dp(f);
                this.captionY = AndroidUtilities.lerp(fM1146dp, AndroidUtilities.m1146dp(9.0f) + this.namesOffset, mediaAbove());
            } else {
                if (this.isRoundVideo) {
                    this.captionX = getBackgroundDrawableLeft() + AndroidUtilities.m1146dp((this.currentMessageObject.isOutOwner() ? 0 : 6) + 11);
                } else {
                    int i3 = this.backgroundDrawableLeft;
                    if (!messageObject.isOutOwner() && !this.mediaBackground && !this.drawPinnedBottom) {
                        f = 17.0f;
                    }
                    this.captionX = i3 + AndroidUtilities.m1146dp(f) + this.captionOffsetX;
                }
                int iM1146dp3 = (this.totalHeight - this.captionHeight) - AndroidUtilities.m1146dp(this.drawPinnedTop ? 9.0f : 10.0f);
                if (!this.drawCommentButton || this.drawSideButton == 3) {
                    f = 0.0f;
                } else if (shouldDrawTimeOnMedia()) {
                    f = 41.3f;
                }
                int iM1146dp4 = iM1146dp3 - AndroidUtilities.m1146dp(f);
                ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
                if (reactionsLayoutInBubble.isEmpty || reactionsLayoutInBubble.isSmall) {
                    iM1146dp = 0;
                } else {
                    if (this.currentMessageObject.type == 9 && this.currentPosition == null) {
                        f2 = 10.0f;
                    }
                    iM1146dp = AndroidUtilities.m1146dp(f2) + this.reactionsLayoutInBubble.totalHeight;
                }
                fM1146dp = iM1146dp4 - iM1146dp;
                this.captionY = AndroidUtilities.lerp(fM1146dp, AndroidUtilities.m1146dp(9.0f) + this.namesOffset, mediaAbove());
            }
        }
        this.captionX += getExtraTextX();
        MessageObject messageObject2 = this.currentMessageObject;
        if (messageObject2 == null || (i = messageObject2.type) == 0 || i == 24 || messageObject2.isGiveawayOrGiveawayResults() || this.currentMessageObject.isSponsored()) {
            return;
        }
        if (z && this.hasFactCheck) {
            this.captionY -= (this.factCheckHeight + AndroidUtilities.m1146dp(16.0f)) * (1.0f - mediaAbove());
        }
        this.factCheckY = (int) (((fM1146dp - (z ? this.factCheckHeight + AndroidUtilities.m1146dp(14.0f) : 0)) + AndroidUtilities.lerp(this.captionLayout != null ? r1.textHeight(this.transitionParams) + AndroidUtilities.m1146dp(4.0f) : 0, AndroidUtilities.m1146dp(6.0f), mediaAbove())) - this.transitionParams.deltaTop);
    }

    private float mediaAbove() {
        TransitionParams transitionParams = this.transitionParams;
        if (!transitionParams.animateMediaAbove) {
            return this.captionAbove ? 1.0f : 0.0f;
        }
        boolean z = this.captionAbove;
        return AndroidUtilities.lerp(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f, transitionParams.animateChangeProgress);
    }

    public int captionFlag() {
        return this.captionAbove ? 4 : 8;
    }

    private boolean textIsSelectionMode() {
        ChatMessageCellDelegate chatMessageCellDelegate;
        return getCurrentMessagesGroup() == null && (chatMessageCellDelegate = this.delegate) != null && chatMessageCellDelegate.getTextSelectionHelper() != null && this.delegate.getTextSelectionHelper().isSelected(this.currentMessageObject);
    }

    public float getViewTop() {
        return this.viewTop;
    }

    public int getBackgroundHeight() {
        return this.backgroundHeight;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMiniIconForCurrentState() {
        int i = this.miniButtonState;
        if (i < 0) {
            return 4;
        }
        return i == 0 ? 2 : 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getIconForCurrentState() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.hasExtendedMedia()) {
            return 4;
        }
        if (this.documentAttachType == 7 && this.currentMessageObject.isVoiceTranscriptionOpen() && this.canStreamVideo) {
            int i = this.buttonState;
            return (i == 1 || i == 4) ? 1 : 0;
        }
        int i2 = this.documentAttachType;
        if (i2 == 3 || i2 == 5) {
            if (this.currentMessageObject.isOutOwner()) {
                this.radialProgress.setColorKeys(Theme.key_chat_outLoader, Theme.key_chat_outLoaderSelected, Theme.key_chat_outMediaIcon, Theme.key_chat_outMediaIconSelected);
            } else {
                ReplyMessageLine replyMessageLine = this.linkLine;
                if (replyMessageLine != null && this.hasLinkPreview) {
                    this.radialProgress.setColors(replyMessageLine.getColor(), this.linkLine.getColor(), Theme.blendOver(-1, Theme.multAlpha(this.linkLine.getColor(), 0.01f)), Theme.blendOver(-1, Theme.multAlpha(this.linkLine.getColor(), 0.05f)));
                } else {
                    this.radialProgress.setColorKeys(Theme.key_chat_inLoader, Theme.key_chat_inLoaderSelected, Theme.key_chat_inMediaIcon, Theme.key_chat_inMediaIconSelected);
                }
            }
            int i3 = this.buttonState;
            if (i3 == 1) {
                return 1;
            }
            if (i3 == 2) {
                return 2;
            }
            return i3 == 4 ? 3 : 0;
        }
        if (i2 == 1 && !this.drawPhotoImage) {
            if (this.currentMessageObject.isOutOwner()) {
                this.radialProgress.setColorKeys(Theme.key_chat_outLoader, Theme.key_chat_outLoaderSelected, Theme.key_chat_outMediaIcon, Theme.key_chat_outMediaIconSelected);
            } else {
                ReplyMessageLine replyMessageLine2 = this.linkLine;
                if (replyMessageLine2 != null && this.hasLinkPreview) {
                    this.radialProgress.setColors(replyMessageLine2.getColor(), this.linkLine.getColor(), Theme.blendOver(-1, Theme.multAlpha(this.linkLine.getColor(), 0.01f)), Theme.blendOver(-1, Theme.multAlpha(this.linkLine.getColor(), 0.05f)));
                } else {
                    this.radialProgress.setColorKeys(Theme.key_chat_inLoader, Theme.key_chat_inLoaderSelected, Theme.key_chat_inMediaIcon, Theme.key_chat_inMediaIconSelected);
                }
            }
            int i4 = this.buttonState;
            if (i4 == -1) {
                return 5;
            }
            if (i4 == 0) {
                return 2;
            }
            if (i4 == 1) {
                return 3;
            }
        } else {
            RadialProgress2 radialProgress2 = this.radialProgress;
            int i5 = Theme.key_chat_mediaLoaderPhoto;
            int i6 = Theme.key_chat_mediaLoaderPhotoSelected;
            int i7 = Theme.key_chat_mediaLoaderPhotoIcon;
            int i8 = Theme.key_chat_mediaLoaderPhotoIconSelected;
            radialProgress2.setColorKeys(i5, i6, i7, i8);
            this.videoRadialProgress.setColorKeys(i5, i6, i7, i8);
            int i9 = this.buttonState;
            if (i9 >= 0 && i9 < 4) {
                if (i9 == 0) {
                    return 2;
                }
                if (i9 == 1) {
                    return 3;
                }
                return (i9 != 2 && this.autoPlayingMedia) ? 4 : 0;
            }
            if (i9 == -1) {
                if (this.documentAttachType == 1) {
                    return (!this.drawPhotoImage || (this.currentPhotoObject == null && this.currentPhotoObjectThumb == null) || !(this.photoImage.hasBitmapImage() || this.currentMessageObject.mediaExists() || this.currentMessageObject.attachPathExists)) ? 5 : 4;
                }
                if (this.currentMessageObject.needDrawBluredPreview()) {
                    return 7;
                }
                if (this.hasEmbed) {
                    return 0;
                }
            }
        }
        MessageObject messageObject2 = this.currentMessageObject;
        return (messageObject2 != null && this.isRoundVideo && messageObject2.isVoiceTranscriptionOpen()) ? 0 : 4;
    }

    public int getMaxNameWidth() {
        int iMin;
        int iM1146dp;
        MessageObject messageObject;
        MessageObject messageObject2;
        int i;
        int iM1146dp2;
        int iM1146dp3;
        int parentWidth;
        int i2 = this.documentAttachType;
        if (i2 == 6 || i2 == 8 || (i = (messageObject2 = this.currentMessageObject).type) == 5) {
            float f = 0.0f;
            if (AndroidUtilities.isTablet()) {
                iMin = AndroidUtilities.getMinTabletSide();
                if (this.isSideMenued) {
                    f = 71.0f;
                } else if (needDrawAvatar()) {
                    f = 42.0f;
                }
                iM1146dp = AndroidUtilities.m1146dp(f);
            } else {
                iMin = Math.min(getParentWidth(), AndroidUtilities.displaySize.y);
                if (this.isSideMenued) {
                    f = 71.0f;
                } else if (needDrawAvatar()) {
                    f = 42.0f;
                }
                iM1146dp = AndroidUtilities.m1146dp(f);
            }
            int iM1146dp4 = iMin - iM1146dp;
            MessageObject messageObject3 = this.currentMessageObject;
            if (messageObject3 != null && messageObject3.isSaved && messageObject3.isOutOwner() && checkNeedDrawShareButton(this.currentMessageObject)) {
                iM1146dp4 -= AndroidUtilities.m1146dp(25.0f);
            }
            if (this.isPlayingRound && ((messageObject = this.currentMessageObject) == null || !messageObject.isVoiceTranscriptionOpen())) {
                return (iM1146dp4 - (this.backgroundWidth - (AndroidUtilities.roundPlayingMessageSize(this.isSideMenued) - AndroidUtilities.roundMessageSize))) - AndroidUtilities.m1146dp(57.0f);
            }
            if (this.isSideMenued && (this.currentMessageObject.type == 5 || this.documentAttachType == 6)) {
                return this.backgroundWidth - AndroidUtilities.m1146dp(57.0f);
            }
            return (iM1146dp4 - this.backgroundWidth) - AndroidUtilities.m1146dp(57.0f);
        }
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null && !groupedMessages.isDocuments) {
            if (AndroidUtilities.isTablet()) {
                parentWidth = AndroidUtilities.getMinTabletSide();
            } else {
                parentWidth = getParentWidth();
            }
            iM1146dp2 = 0;
            for (int i3 = 0; i3 < this.currentMessagesGroup.posArray.size(); i3++) {
                if (this.currentMessagesGroup.posArray.get(i3).minY != 0) {
                    break;
                }
                iM1146dp2 = (int) (iM1146dp2 + Math.ceil(((r4.f1451pw + r4.leftSpanOffset) / 1000.0f) * parentWidth));
            }
            if (this.isSideMenued) {
                i = 71;
            } else if (needDrawAvatar()) {
                i = 48;
            }
            iM1146dp3 = AndroidUtilities.m1146dp(i + 31);
        } else {
            if (i == 19) {
                return Math.max(messageObject2.textWidth, (int) (((AndroidUtilities.displaySize.x - AndroidUtilities.m1146dp(52.0f)) - (this.isAvatarVisible ? AndroidUtilities.m1146dp(48.0f) : 0)) * 0.5f));
            }
            iM1146dp2 = this.backgroundWidth;
            if (messageObject2 != null && messageObject2.isSaved && messageObject2.isOutOwner() && checkNeedDrawShareButton(this.currentMessageObject)) {
                iM1146dp2 -= AndroidUtilities.m1146dp(25.0f);
            }
            iM1146dp3 = AndroidUtilities.m1146dp(this.mediaBackground ? 22.0f : 31.0f);
        }
        return iM1146dp2 - iM1146dp3;
    }

    /* JADX WARN: Removed duplicated region for block: B:166:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:374:0x058e  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0146  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateButtonState(boolean r20, boolean r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 2284
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.updateButtonState(boolean, boolean, boolean):void");
    }

    private void didPressMiniButton(boolean z) {
        int i = this.miniButtonState;
        if (i != 0) {
            if (i == 1) {
                int i2 = this.documentAttachType;
                if ((i2 == 3 || i2 == 5 || i2 == 7) && MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                    MediaController.getInstance().cleanupPlayer(true, true);
                }
                this.miniButtonState = 0;
                this.currentMessageObject.loadingCancelled = true;
                FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.documentAttach);
                this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, true);
                invalidate();
                return;
            }
            return;
        }
        this.miniButtonState = 1;
        this.radialProgress.setProgress(0.0f, false);
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && !messageObject.isAnyKindOfSticker()) {
            this.currentMessageObject.putInDownloadsStore = true;
        }
        int i3 = this.documentAttachType;
        if (i3 == 3 || i3 == 5) {
            FileLoader fileLoader = FileLoader.getInstance(this.currentAccount);
            TLRPC.Document document = this.documentAttach;
            MessageObject messageObject2 = this.currentMessageObject;
            fileLoader.loadFile(document, messageObject2, 2, messageObject2.shouldEncryptPhotoOrVideo() ? 2 : 0);
            this.currentMessageObject.loadingCancelled = false;
        } else if (i3 == 4 || i3 == 7) {
            createLoadingProgressLayout(this.documentAttach);
            FileLoader fileLoader2 = FileLoader.getInstance(this.currentAccount);
            TLRPC.Document document2 = this.documentAttach;
            MessageObject messageObject3 = this.currentMessageObject;
            fileLoader2.loadFile(document2, messageObject3, 2, messageObject3.shouldEncryptPhotoOrVideo() ? 2 : 0);
            this.currentMessageObject.loadingCancelled = false;
        }
        this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, true);
        invalidate();
    }

    private void didPressButton(boolean z, boolean z2) {
        MessageObject messageObject;
        MessageObject playingMessageObject;
        MessageObject messageObject2;
        TLRPC.PhotoSize photoSize;
        String str;
        MessageObject messageObject3;
        if (this.delegate != null && this.currentMessageObject.isSensitive() && this.currentMessageObject.hasMediaSpoilers() && !this.currentMessageObject.needDrawBluredPreview() && !this.currentMessageObject.isMediaSpoilersRevealed) {
            this.delegate.didPressRevealSensitiveContent(this);
            return;
        }
        MessageObject messageObject4 = this.currentMessageObject;
        if (messageObject4 != null && !messageObject4.isAnyKindOfSticker()) {
            this.currentMessageObject.putInDownloadsStore = true;
        }
        int i = this.buttonState;
        if (i == 0 && (!this.drawVideoImageButton || z2)) {
            int i2 = this.documentAttachType;
            if (i2 == 3 || i2 == 5 || (i2 == 7 && (messageObject3 = this.currentMessageObject) != null && messageObject3.isVoiceTranscriptionOpen() && this.currentMessageObject.mediaExists)) {
                if (this.miniButtonState == 0) {
                    FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
                    this.currentMessageObject.loadingCancelled = false;
                }
                if (this.delegate.needPlayMessage(this, this.currentMessageObject, false)) {
                    if (this.hasMiniProgress == 2 && this.miniButtonState != 1) {
                        this.miniButtonState = 1;
                        this.radialProgress.setProgress(0.0f, false);
                        this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, true);
                    }
                    updatePlayingMessageProgress();
                    this.buttonState = 1;
                    this.radialProgress.setIcon(getIconForCurrentState(), false, true);
                    invalidate();
                    return;
                }
                return;
            }
            if (z2) {
                this.videoRadialProgress.setProgress(0.0f, false);
            } else {
                this.radialProgress.setProgress(0.0f, false);
            }
            if (this.currentPhotoObject != null && (this.photoImage.hasNotThumb() || this.currentPhotoObjectThumb == null)) {
                photoSize = this.currentPhotoObject;
                str = ((photoSize instanceof TLRPC.TL_photoStrippedSize) || "s".equals(photoSize.type)) ? this.currentPhotoFilterThumb : this.currentPhotoFilter;
            } else {
                photoSize = this.currentPhotoObjectThumb;
                str = this.currentPhotoFilterThumb;
            }
            String str2 = str;
            int i3 = this.currentMessageObject.shouldEncryptPhotoOrVideo() ? 2 : 0;
            MessageObject messageObject5 = this.currentMessageObject;
            int i4 = messageObject5.type;
            if (i4 == 1 || i4 == 20) {
                int i5 = i3;
                this.photoImage.setForceLoading(true);
                this.photoImage.setImage(ImageLocation.getForObject(this.currentPhotoObject, this.photoParentObject), this.currentPhotoFilter, ImageLocation.getForObject(this.currentPhotoObjectThumb, this.photoParentObject), this.currentPhotoFilterThumb, this.currentPhotoObjectThumbStripped, this.currentPhotoObject.size, null, this.currentMessageObject, i5);
            } else if (i4 == 8) {
                FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
                if (this.currentMessageObject.loadedFileSize > 0) {
                    createLoadingProgressLayout(this.documentAttach);
                }
            } else if (this.isRoundVideo) {
                if (messageObject5.isSecretMedia()) {
                    FileLoader fileLoader = FileLoader.getInstance(this.currentAccount);
                    TLRPC.Document document = this.currentMessageObject.getDocument();
                    MessageObject messageObject6 = this.currentMessageObject;
                    fileLoader.loadFile(document, messageObject6, 2, messageObject6.shouldEncryptPhotoOrVideo() ? 2 : 1);
                } else {
                    MessageObject messageObject7 = this.currentMessageObject;
                    messageObject7.gifState = 2.0f;
                    TLRPC.Document document2 = messageObject7.getDocument();
                    this.photoImage.setForceLoading(true);
                    this.photoImage.setImage(ImageLocation.getForDocument(document2), null, ImageLocation.getForObject(photoSize, document2), str2, document2.size, null, this.currentMessageObject, 0);
                }
                this.wouldBeInPip = true;
                invalidate();
            } else if (i4 == 9) {
                FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
                if (this.currentMessageObject.loadedFileSize > 0) {
                    createLoadingProgressLayout(this.documentAttach);
                }
            } else {
                int i6 = this.documentAttachType;
                if (i6 == 4) {
                    FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 1, i3);
                    MessageObject messageObject8 = this.currentMessageObject;
                    if (messageObject8.loadedFileSize > 0) {
                        createLoadingProgressLayout(messageObject8.getDocument());
                    }
                } else if (i4 != 0 || i6 == 0) {
                    this.photoImage.setForceLoading(true);
                    this.photoImage.setImage(ImageLocation.getForObject(this.currentPhotoObject, this.photoParentObject), this.currentPhotoFilter, ImageLocation.getForObject(this.currentPhotoObjectThumb, this.photoParentObject), this.currentPhotoFilterThumb, this.currentPhotoObjectThumbStripped, 0L, null, this.currentMessageObject, 0);
                } else if (i6 == 2) {
                    this.photoImage.setForceLoading(true);
                    this.photoImage.setImage(ImageLocation.getForDocument(this.documentAttach), null, ImageLocation.getForDocument(this.currentPhotoObject, this.documentAttach), this.currentPhotoFilterThumb, this.documentAttach.size, null, this.currentMessageObject, i3);
                    MessageObject messageObject9 = this.currentMessageObject;
                    messageObject9.gifState = 2.0f;
                    if (messageObject9.loadedFileSize > 0) {
                        createLoadingProgressLayout(messageObject9.getDocument());
                    }
                } else if (i6 == 1) {
                    FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
                } else if (i6 == 8) {
                    this.photoImage.setImage(ImageLocation.getForDocument(this.documentAttach), this.currentPhotoFilter, ImageLocation.getForDocument(this.currentPhotoObject, this.documentAttach), "b1", 0L, "jpg", this.currentMessageObject, 1);
                }
            }
            this.currentMessageObject.loadingCancelled = false;
            this.buttonState = 1;
            if (z2) {
                this.videoRadialProgress.setIcon(14, false, z);
            } else {
                this.radialProgress.setIcon(getIconForCurrentState(), false, z);
            }
            invalidate();
            return;
        }
        if (i == 1 && (!this.drawVideoImageButton || z2)) {
            this.photoImage.setForceLoading(false);
            int i7 = this.documentAttachType;
            if (i7 == 3 || i7 == 5 || (i7 == 7 && (messageObject2 = this.currentMessageObject) != null && messageObject2.isVoiceTranscriptionOpen())) {
                if (MediaController.getInstance().lambda$startAudioAgain$7(this.currentMessageObject)) {
                    this.buttonState = 0;
                    this.radialProgress.setIcon(getIconForCurrentState(), false, z);
                    invalidate();
                    return;
                }
                return;
            }
            if (this.currentMessageObject.isOut() && !this.drawVideoImageButton && (this.currentMessageObject.isSending() || this.currentMessageObject.isEditing())) {
                if (this.radialProgress.getIcon() != 6) {
                    this.delegate.didPressCancelSendButton(this);
                    return;
                }
                return;
            }
            MessageObject messageObject10 = this.currentMessageObject;
            messageObject10.loadingCancelled = true;
            int i8 = this.documentAttachType;
            if (i8 == 2 || i8 == 4 || i8 == 1 || i8 == 8) {
                FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.documentAttach);
            } else {
                int i9 = messageObject10.type;
                if (i9 == 0 || i9 == 1 || i9 == 20 || i9 == 8 || i9 == 5) {
                    ImageLoader.getInstance().cancelForceLoadingForImageReceiver(this.photoImage);
                    this.photoImage.cancelLoadImage();
                } else if (i9 == 9) {
                    FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.currentMessageObject.getDocument());
                }
            }
            this.buttonState = 0;
            if (z2) {
                this.videoRadialProgress.setIcon(2, false, z);
            } else {
                this.radialProgress.setIcon(getIconForCurrentState(), false, z);
            }
            invalidate();
            return;
        }
        if (i != 2) {
            if (i == 3 || i == 0) {
                if (this.hasMiniProgress == 2 && this.miniButtonState != 1) {
                    this.miniButtonState = 1;
                    this.radialProgress.setProgress(0.0f, false);
                    this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, z);
                }
                ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
                if (chatMessageCellDelegate != null) {
                    chatMessageCellDelegate.didPressImage(this, 0.0f, 0.0f, false);
                    return;
                }
                return;
            }
            if (i == 4) {
                int i10 = this.documentAttachType;
                if (i10 == 3 || i10 == 5 || (i10 == 7 && (messageObject = this.currentMessageObject) != null && messageObject.isVoiceTranscriptionOpen())) {
                    if ((this.currentMessageObject.isOut() && (this.currentMessageObject.isSending() || this.currentMessageObject.isEditing())) || this.currentMessageObject.isSendError()) {
                        if (this.delegate == null || this.radialProgress.getIcon() == 6) {
                            return;
                        }
                        this.delegate.didPressCancelSendButton(this);
                        return;
                    }
                    this.currentMessageObject.loadingCancelled = true;
                    FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.documentAttach);
                    this.buttonState = 2;
                    this.radialProgress.setIcon(getIconForCurrentState(), false, z);
                    invalidate();
                    return;
                }
                return;
            }
            return;
        }
        MessageObject messageObject11 = this.currentMessageObject;
        if (messageObject11 != null && messageObject11.type == 23) {
            this.delegate.didPressImage(this, 0.0f, 0.0f, false);
            return;
        }
        if (this.documentAttachType == 7 && messageObject11 != null && messageObject11.isVoiceTranscriptionOpen()) {
            if (this.miniButtonState == 0) {
                FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
                this.currentMessageObject.loadingCancelled = false;
            }
            if (this.delegate.needPlayMessage(this, this.currentMessageObject, false)) {
                if (this.hasMiniProgress == 2 && this.miniButtonState != 1) {
                    this.miniButtonState = 1;
                    this.radialProgress.setProgress(0.0f, false);
                    this.radialProgress.setMiniIcon(getMiniIconForCurrentState(), false, true);
                }
                updatePlayingMessageProgress();
                this.buttonState = 1;
                this.radialProgress.setIcon(getIconForCurrentState(), false, true);
                invalidate();
            }
            if (this.isRoundVideo) {
                this.wouldBeInPip = true;
                invalidate();
                return;
            }
            return;
        }
        int i11 = this.documentAttachType;
        if (i11 == 3 || i11 == 5) {
            this.radialProgress.setProgress(0.0f, false);
            FileLoader.getInstance(this.currentAccount).loadFile(this.documentAttach, this.currentMessageObject, 2, 0);
            this.currentMessageObject.loadingCancelled = false;
            this.buttonState = 4;
            this.radialProgress.setIcon(getIconForCurrentState(), true, z);
            invalidate();
            return;
        }
        if (!this.isRoundVideo || (playingMessageObject = MediaController.getInstance().getPlayingMessageObject()) == null || !playingMessageObject.isRoundVideo()) {
            this.photoImage.setAllowStartAnimation(true);
            this.photoImage.startAnimation();
        }
        this.currentMessageObject.gifState = 0.0f;
        this.buttonState = -1;
        this.radialProgress.setIcon(getIconForCurrentState(), false, z);
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onFailedDownload(String str, boolean z) {
        int i = this.documentAttachType;
        updateButtonState(true, i == 3 || i == 5, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:80:0x019c  */
    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onSuccessDownload(java.lang.String r36) {
        /*
            Method dump skipped, instructions count: 699
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.onSuccessDownload(java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0044  */
    @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void didSetImage(org.telegram.messenger.ImageReceiver r5, boolean r6, boolean r7, boolean r8) {
        /*
            r4 = this;
            org.telegram.messenger.MessageObject r0 = r4.currentMessageObject
            if (r0 == 0) goto L4b
            if (r6 == 0) goto L4b
            r1 = 0
            r2 = 1
            if (r8 != 0) goto L10
            boolean r8 = r0.wasUnread
            if (r8 != 0) goto L10
            r8 = 1
            goto L11
        L10:
            r8 = 0
        L11:
            boolean r8 = r4.setCurrentDiceValue(r8)
            if (r8 == 0) goto L19
            goto La0
        L19:
            if (r7 == 0) goto L27
            org.telegram.messenger.MessageObject r8 = r4.currentMessageObject
            int r0 = r8.type
            r3 = 20
            if (r0 != r3) goto L27
            boolean r8 = r8.mediaExists
            if (r8 == 0) goto L44
        L27:
            if (r7 != 0) goto L4b
            org.telegram.messenger.MessageObject r7 = r4.currentMessageObject
            boolean r8 = r7.mediaExists
            if (r8 != 0) goto L4b
            boolean r8 = r7.attachPathExists
            if (r8 != 0) goto L4b
            int r7 = r7.type
            if (r7 != 0) goto L42
            int r8 = r4.documentAttachType
            r0 = 8
            if (r8 == r0) goto L44
            if (r8 == 0) goto L44
            r0 = 6
            if (r8 == r0) goto L44
        L42:
            if (r7 != r2) goto L4b
        L44:
            org.telegram.messenger.MessageObject r7 = r4.currentMessageObject
            r7.mediaExists = r2
            r4.updateButtonState(r1, r2, r1)
        L4b:
            if (r6 == 0) goto La0
            org.telegram.messenger.MessageObject r6 = r4.currentMessageObject
            if (r6 == 0) goto La0
            org.telegram.messenger.ImageReceiver r6 = r4.blurredPhotoImage
            android.graphics.Bitmap r6 = r6.getBitmap()
            if (r6 == 0) goto L68
            org.telegram.messenger.ImageReceiver r6 = r4.blurredPhotoImage
            android.graphics.Bitmap r6 = r6.getBitmap()
            r6.recycle()
            org.telegram.messenger.ImageReceiver r6 = r4.blurredPhotoImage
            r7 = 0
            r6.setImageBitmap(r7)
        L68:
            org.telegram.messenger.MessageObject r6 = r4.currentMessageObject
            boolean r6 = r6.hasMediaSpoilers()
            if (r6 != 0) goto L74
            boolean r6 = r4.fitPhotoImage
            if (r6 == 0) goto La0
        L74:
            android.graphics.Bitmap r6 = r5.getBitmap()
            if (r6 == 0) goto La0
            android.graphics.Bitmap r6 = r5.getBitmap()
            boolean r6 = r6.isRecycled()
            if (r6 != 0) goto La0
            org.telegram.messenger.ImageReceiver r6 = r4.blurredPhotoImage
            android.graphics.Bitmap r5 = r5.getBitmap()
            org.telegram.messenger.MessageObject r7 = r4.currentMessageObject
            boolean r7 = r7.isRoundVideo()
            android.graphics.Bitmap r5 = org.telegram.messenger.Utilities.stackBlurBitmapMax(r5, r7)
            r6.setImageBitmap(r5)
            org.telegram.messenger.ImageReceiver r5 = r4.blurredPhotoImage
            android.graphics.ColorMatrixColorFilter r6 = r4.getFancyBlurFilter()
            r5.setColorFilter(r6)
        La0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.didSetImage(org.telegram.messenger.ImageReceiver, boolean, boolean, boolean):void");
    }

    public boolean setCurrentDiceValue(boolean z) {
        MessagesController.DiceFrameSuccess diceFrameSuccess;
        if (!this.currentMessageObject.isDice()) {
            return false;
        }
        Drawable drawable = this.photoImage.getDrawable();
        if (drawable instanceof RLottieDrawable) {
            RLottieDrawable rLottieDrawable = (RLottieDrawable) drawable;
            String diceEmoji = this.currentMessageObject.getDiceEmoji();
            TLRPC.TL_messages_stickerSet stickerSetByEmojiOrName = MediaDataController.getInstance(this.currentAccount).getStickerSetByEmojiOrName(diceEmoji);
            if (stickerSetByEmojiOrName != null) {
                int diceValue = this.currentMessageObject.getDiceValue();
                if ("🎰".equals(this.currentMessageObject.getDiceEmoji())) {
                    if (diceValue >= 0 && diceValue <= 64) {
                        ((SlotsDrawable) rLottieDrawable).setDiceNumber(this, diceValue, stickerSetByEmojiOrName, z);
                        if (this.currentMessageObject.isOut()) {
                            rLottieDrawable.setOnFinishCallback(this.diceFinishCallback, ConnectionsManager.DEFAULT_DATACENTER_ID);
                        }
                        this.currentMessageObject.wasUnread = false;
                    }
                    if (!rLottieDrawable.hasBaseDice() && stickerSetByEmojiOrName.documents.size() > 0) {
                        ((SlotsDrawable) rLottieDrawable).setBaseDice(this, stickerSetByEmojiOrName);
                    }
                } else {
                    if (!rLottieDrawable.hasBaseDice() && stickerSetByEmojiOrName.documents.size() > 0) {
                        TLRPC.Document document = (TLRPC.Document) stickerSetByEmojiOrName.documents.get(0);
                        if (rLottieDrawable.setBaseDice(FileLoader.getInstance(this.currentAccount).getPathToAttach(document, true))) {
                            DownloadController.getInstance(this.currentAccount).removeLoadingFileObserver(this);
                        } else {
                            DownloadController.getInstance(this.currentAccount).addLoadingFileObserver(FileLoader.getAttachFileName(document), this.currentMessageObject, this);
                            FileLoader.getInstance(this.currentAccount).loadFile(document, stickerSetByEmojiOrName, 1, 1);
                        }
                    }
                    if (diceValue >= 0 && diceValue < stickerSetByEmojiOrName.documents.size()) {
                        if (!z && this.currentMessageObject.isOut() && (diceFrameSuccess = MessagesController.getInstance(this.currentAccount).diceSuccess.get(diceEmoji)) != null && diceFrameSuccess.num == diceValue) {
                            rLottieDrawable.setOnFinishCallback(this.diceFinishCallback, diceFrameSuccess.frame);
                        }
                        TLRPC.Document document2 = (TLRPC.Document) stickerSetByEmojiOrName.documents.get(Math.max(diceValue, 0));
                        if (rLottieDrawable.setDiceNumber(FileLoader.getInstance(this.currentAccount).getPathToAttach(document2, true), z)) {
                            DownloadController.getInstance(this.currentAccount).removeLoadingFileObserver(this);
                        } else {
                            DownloadController.getInstance(this.currentAccount).addLoadingFileObserver(FileLoader.getAttachFileName(document2), this.currentMessageObject, this);
                            FileLoader.getInstance(this.currentAccount).loadFile(document2, stickerSetByEmojiOrName, 1, 1);
                        }
                        this.currentMessageObject.wasUnread = false;
                    }
                }
            } else {
                MediaDataController.getInstance(this.currentAccount).loadStickersByEmojiOrName(diceEmoji, true, true);
            }
        }
        return true;
    }

    @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
    public void onAnimationReady(ImageReceiver imageReceiver) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && imageReceiver == this.photoImage && messageObject.isAnimatedSticker()) {
            this.delegate.setShouldNotRepeatSticker(this.currentMessageObject);
        }
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onProgressDownload(String str, long j, long j2) {
        float fMin = j2 == 0 ? 0.0f : Math.min(1.0f, j / j2);
        this.currentMessageObject.loadedFileSize = j;
        createLoadingProgressLayout(j, j2);
        if (this.drawVideoImageButton) {
            this.videoRadialProgress.setProgress(fMin, true);
        } else {
            this.radialProgress.setProgress(fMin, true);
        }
        int i = this.documentAttachType;
        if (i == 3 || i == 5) {
            if (this.hasMiniProgress != 0) {
                if (this.miniButtonState != 1) {
                    updateButtonState(false, false, false);
                    return;
                }
                return;
            } else {
                if (this.buttonState != 4) {
                    updateButtonState(false, false, false);
                    return;
                }
                return;
            }
        }
        if (this.hasMiniProgress != 0) {
            if (this.miniButtonState != 1) {
                updateButtonState(false, false, false);
            }
        } else if (this.buttonState != 1) {
            updateButtonState(false, false, false);
        }
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public void onProgressUpload(String str, long j, long j2, boolean z) {
        int i;
        float fMin = j2 == 0 ? 0.0f : Math.min(1.0f, j / j2);
        this.currentMessageObject.loadedFileSize = j;
        this.radialProgress.setProgress(fMin, true);
        if (j == j2 && ((this.currentPosition != null || this.currentMessageObject.isPaid()) && SendMessagesHelper.getInstance(this.currentAccount).isSendingMessage(this.currentMessageObject.getId()) && ((i = this.buttonState) == 1 || (i == 4 && this.documentAttachType == 5)))) {
            this.drawRadialCheckBackground = true;
            getIconForCurrentState();
            this.radialProgress.setIcon(6, false, true);
        }
        long j3 = this.lastLoadingSizeTotal;
        if (j3 > 0 && Math.abs(j3 - j2) > 1048576) {
            this.lastLoadingSizeTotal = j2;
        }
        createLoadingProgressLayout(j, j2);
    }

    private void createLoadingProgressLayout(TLRPC.Document document) {
        if (document == null) {
            return;
        }
        long[] fileProgressSizes = ImageLoader.getInstance().getFileProgressSizes(FileLoader.getDocumentFileName(document));
        if (fileProgressSizes != null) {
            createLoadingProgressLayout(fileProgressSizes[0], fileProgressSizes[1]);
        } else {
            createLoadingProgressLayout(this.currentMessageObject.loadedFileSize, document.size);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0104  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void createLoadingProgressLayout(long r22, long r24) {
        /*
            Method dump skipped, instructions count: 284
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.createLoadingProgressLayout(long, long):void");
    }

    @Override // android.view.View
    public void onProvideStructure(ViewStructure viewStructure) {
        CharSequence charSequence;
        CharSequence charSequence2;
        super.onProvideStructure(viewStructure);
        if (!this.allowAssistant || Build.VERSION.SDK_INT < 23) {
            return;
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && (charSequence2 = messageObject.messageText) != null && charSequence2.length() > 0) {
            viewStructure.setText(this.currentMessageObject.messageText);
            return;
        }
        MessageObject messageObject2 = this.currentMessageObject;
        if (messageObject2 == null || (charSequence = messageObject2.caption) == null || charSequence.length() <= 0) {
            return;
        }
        viewStructure.setText(this.currentMessageObject.caption);
    }

    public void setDelegate(ChatMessageCellDelegate chatMessageCellDelegate) {
        this.delegate = chatMessageCellDelegate;
    }

    public ChatMessageCellDelegate getDelegate() {
        return this.delegate;
    }

    public void setAllowAssistant(boolean z) {
        this.allowAssistant = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void measureTime(org.telegram.messenger.MessageObject r26) {
        /*
            Method dump skipped, instructions count: 1980
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.measureTime(org.telegram.messenger.MessageObject):void");
    }

    private boolean shouldDrawSelectionOverlay() {
        if (!hasSelectionOverlay()) {
            return false;
        }
        if (!((isPressed() && this.isCheckPressed) || ((!this.isCheckPressed && this.isPressed) || this.isHighlighted || this.isHighlightedAnimated)) || textIsSelectionMode()) {
            return false;
        }
        return (this.currentMessagesGroup == null || this.drawSelectionBackground) && this.currentBackgroundDrawable != null;
    }

    private int getSelectionOverlayColor() {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        if (resourcesProvider == null) {
            return 0;
        }
        MessageObject messageObject = this.currentMessageObject;
        return resourcesProvider.getColor((messageObject == null || !messageObject.isOut()) ? Theme.key_chat_inBubbleSelectedOverlay : Theme.key_chat_outBubbleSelectedOverlay);
    }

    private boolean hasSelectionOverlay() {
        int selectionOverlayColor = getSelectionOverlayColor();
        return (selectionOverlayColor == 0 || selectionOverlayColor == -65536) ? false : true;
    }

    public boolean isDrawSelectionBackground() {
        if ((!(isPressed() && this.isCheckPressed) && ((this.isCheckPressed || !this.isPressed) && !this.isHighlighted)) || textIsSelectionMode() || hasSelectionOverlay()) {
            return false;
        }
        MessageObject messageObject = this.currentMessageObject;
        return messageObject == null || !messageObject.preview;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOpenChatByShare(MessageObject messageObject) {
        TLRPC.MessageFwdHeader messageFwdHeader = messageObject.messageOwner.fwd_from;
        if (messageFwdHeader == null || messageFwdHeader.saved_from_peer == null) {
            return false;
        }
        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
        return chatMessageCellDelegate == null || chatMessageCellDelegate.isReplyOrSelf();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected boolean checkNeedDrawShareButton(org.telegram.messenger.MessageObject r5) {
        /*
            r4 = this;
            boolean r0 = r4.isReportChat
            r1 = 0
            if (r0 == 0) goto L6
            return r1
        L6:
            org.telegram.messenger.MessageObject r0 = r4.currentMessageObject
            boolean r2 = r0.deleted
            if (r2 == 0) goto L11
            boolean r2 = r0.deletedByThanos
            if (r2 != 0) goto L11
            return r1
        L11:
            boolean r0 = r0.isSponsored()
            if (r0 == 0) goto L18
            return r1
        L18:
            org.telegram.messenger.MessageObject$GroupedMessages r0 = r4.currentMessagesGroup
            if (r0 == 0) goto L3e
            org.telegram.messenger.MessageObject$GroupedMessagePosition r0 = r4.currentPosition
            if (r0 == 0) goto L3e
            int r0 = r0.flags
            r2 = r0 & 8
            if (r2 == 0) goto L34
            boolean r2 = r5.isOutOwner()
            r3 = 1
            if (r2 == 0) goto L2f
            r2 = 1
            goto L30
        L2f:
            r2 = 2
        L30:
            r0 = r0 & r2
            if (r0 == 0) goto L34
            goto L35
        L34:
            r3 = 0
        L35:
            org.telegram.messenger.MessageObject$GroupedMessages r0 = r4.currentMessagesGroup
            boolean r0 = r0.isDocuments
            if (r0 != 0) goto L3e
            if (r3 != 0) goto L3e
            return r1
        L3e:
            boolean r5 = r5.needDrawShareButton()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.checkNeedDrawShareButton(org.telegram.messenger.MessageObject):boolean");
    }

    public boolean isInsideBackground(float f, float f2) {
        if (this.currentBackgroundDrawable == null) {
            return false;
        }
        int i = this.backgroundDrawableLeft;
        return f >= ((float) i) && f <= ((float) (i + this.backgroundDrawableRight));
    }

    private void updateCurrentUserAndChat() {
        TLRPC.Peer peer;
        if (this.currentMessageObject == null) {
            return;
        }
        MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
        TLRPC.MessageFwdHeader messageFwdHeader = this.currentMessageObject.messageOwner.fwd_from;
        long clientUserId = UserConfig.getInstance(this.currentAccount).getClientUserId();
        if (messageFwdHeader != null && (messageFwdHeader.from_id instanceof TLRPC.TL_peerChannel) && (this.currentMessageObject.getDialogId() == clientUserId || this.currentMessageObject.getDialogId() == UserObject.REPLY_BOT)) {
            this.currentChat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(messageFwdHeader.from_id.channel_id));
            return;
        }
        if (messageFwdHeader != null && this.currentMessageObject.getDialogId() == UserObject.VERIFY) {
            long peerDialogId = DialogObject.getPeerDialogId(messageFwdHeader.from_id);
            if (peerDialogId >= 0) {
                this.currentUser = messagesController.getUser(Long.valueOf(peerDialogId));
                return;
            } else {
                this.currentChat = messagesController.getChat(Long.valueOf(-peerDialogId));
                return;
            }
        }
        if (messageFwdHeader != null && (peer = messageFwdHeader.saved_from_peer) != null) {
            long j = peer.user_id;
            if (j != 0) {
                if (!this.isSavedChat) {
                    TLRPC.Peer peer2 = messageFwdHeader.from_id;
                    if (peer2 instanceof TLRPC.TL_peerUser) {
                        this.currentUser = messagesController.getUser(Long.valueOf(peer2.user_id));
                        return;
                    }
                }
                this.currentUser = messagesController.getUser(Long.valueOf(j));
                return;
            }
            if (peer.channel_id != 0) {
                if (this.currentMessageObject.isSavedFromMegagroup()) {
                    TLRPC.Peer peer3 = messageFwdHeader.from_id;
                    if (peer3 instanceof TLRPC.TL_peerUser) {
                        this.currentUser = messagesController.getUser(Long.valueOf(peer3.user_id));
                        return;
                    }
                }
                this.currentChat = messagesController.getChat(Long.valueOf(messageFwdHeader.saved_from_peer.channel_id));
                return;
            }
            long j2 = peer.chat_id;
            if (j2 != 0) {
                TLRPC.Peer peer4 = messageFwdHeader.from_id;
                if (peer4 instanceof TLRPC.TL_peerUser) {
                    this.currentUser = messagesController.getUser(Long.valueOf(peer4.user_id));
                    return;
                } else {
                    this.currentChat = messagesController.getChat(Long.valueOf(j2));
                    return;
                }
            }
            return;
        }
        if (messageFwdHeader != null && (messageFwdHeader.from_id instanceof TLRPC.TL_peerUser) && (messageFwdHeader.imported || this.currentMessageObject.getDialogId() == clientUserId)) {
            this.currentUser = messagesController.getUser(Long.valueOf(messageFwdHeader.from_id.user_id));
            return;
        }
        if (messageFwdHeader != null && !TextUtils.isEmpty(messageFwdHeader.saved_from_name) && (messageFwdHeader.imported || this.currentMessageObject.getDialogId() == clientUserId)) {
            TLRPC.TL_user tL_user = new TLRPC.TL_user();
            this.currentUser = tL_user;
            tL_user.first_name = messageFwdHeader.saved_from_name;
            return;
        }
        if (messageFwdHeader != null && !TextUtils.isEmpty(messageFwdHeader.from_name) && (messageFwdHeader.imported || this.currentMessageObject.getDialogId() == clientUserId)) {
            TLRPC.TL_user tL_user2 = new TLRPC.TL_user();
            this.currentUser = tL_user2;
            tL_user2.first_name = messageFwdHeader.from_name;
            return;
        }
        long dialogId = this.currentMessageObject.getDialogId();
        long fromChatId = this.currentMessageObject.getFromChatId();
        TLRPC.Chat chat = DialogObject.isChatDialog(fromChatId) ? messagesController.getChat(Long.valueOf(-fromChatId)) : null;
        TLRPC.Chat chat2 = DialogObject.isChatDialog(dialogId) ? messagesController.getChat(Long.valueOf(-dialogId)) : null;
        if (DialogObject.isEncryptedDialog(this.currentMessageObject.getDialogId())) {
            if (this.currentMessageObject.isOutOwner()) {
                this.currentUser = UserConfig.getInstance(this.currentAccount).getCurrentUser();
                return;
            }
            TLRPC.EncryptedChat encryptedChat = messagesController.getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(this.currentMessageObject.getDialogId())));
            if (encryptedChat != null) {
                this.currentUser = messagesController.getUser(Long.valueOf(encryptedChat.user_id));
                return;
            }
            return;
        }
        if (DialogObject.isUserDialog(fromChatId) && (!this.currentMessageObject.messageOwner.post || (chat != null && chat.signature_profiles))) {
            this.currentUser = messagesController.getUser(Long.valueOf(fromChatId));
            return;
        }
        if (this.currentMessageObject.messageOwner.post && chat2 != null && !chat2.signature_profiles) {
            this.currentChat = chat2;
            return;
        }
        if (DialogObject.isChatDialog(fromChatId)) {
            this.currentChat = chat;
            return;
        }
        TLRPC.Message message = this.currentMessageObject.messageOwner;
        if (message.post) {
            this.currentChat = messagesController.getChat(Long.valueOf(message.peer_id.channel_id));
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(36:404|(2:414|(1:(1:420))(1:418))|421|(1:425)|426|(5:428|(1:431)|432|(3:438|(1:442)|443)|437)(2:444|(1:446))|447|(1:454)(1:453)|455|(1:460)(1:459)|461|(1:466)(1:465)|(1:471)|472|(1:477)(1:476)|478|(2:496|(9:516|578|(1:580)(1:581)|582|(4:584|(3:586|(3:588|(1:590)(1:591)|592)(1:593)|594)(1:595)|596|(1:598))(1:599)|600|(2:627|(1:661)(11:642|(1:644)(1:645)|646|(1:648)(1:650)|649|651|(1:653)(1:655)|654|656|(1:658)(1:659)|660))(3:606|(1:608)(2:609|(1:611))|(1:613)(7:614|(1:616)(1:617)|618|(1:620)(1:621)|622|(1:624)(1:625)|626))|662|(5:664|(2:669|(1:671)(1:720))(1:668)|(1:722)|723|(30:744|(2:808|(1:814))(1:(2:759|(2:764|(1:766)(2:767|(1:769)(2:770|(2:787|(1:807)(8:793|(1:795)|796|(1:798)|799|(1:803)|804|(1:806)))(6:774|(1:776)|777|(1:781)|782|(1:786)))))(1:763))(1:758))|815|(3:817|(1:819)|820)(1:821)|1015|822|825|(1:827)|828|(1:830)(1:831)|1019|832|(1:834)|837|(1:839)|840|(1:842)(1:843)|844|(2:846|(1:848))|849|(1:859)(3:854|(1:856)(1:857)|858)|860|(1:862)|1017|865|(1:867)(1:870)|871|(8:873|(3:875|(2:877|1022)(1:1023)|878)|1021|879|(1:883)|884|(2:(1:891)|892)|(14:894|(2:896|898)|897|899|(4:901|(1:906)(1:905)|907|(2:909|(3:911|(1:913)(1:914)|915)(0)))(1:916)|917|(1:927)|928|(4:930|(4:933|(1:1026)(4:935|(1:937)(1:938)|(1:940)(1:941)|(2:943|1025)(1:1027))|944|931)|1024|945)|946|(1:948)|949|(1:955)|956)(6:898|897|899|(0)(0)|917|(9:921|923|927|928|(0)|946|(0)|949|(4:951|953|955|956)(0))(8:919|927|928|(0)|946|(0)|949|(0)(0))))|957|(1:959))(2:731|(23:733|(2:735|(1:737))|738|763|815|(0)(0)|1015|822|825|(0)|828|(0)(0)|1019|832|(0)|837|(0)|840|(0)(0)|844|(0)|849|(10:859|860|(0)|1017|865|(0)(0)|871|(0)|957|(0))(0))(21:739|(0)(1:743)|815|(0)(0)|1015|822|825|(0)|828|(0)(0)|1019|832|(0)|837|(0)|840|(0)(0)|844|(0)|849|(0)(0))))(5:672|(2:677|(2:679|(2:681|(2:683|(1:685)(0))(1:686))(1:687))(2:688|(1:690)(2:691|(1:(3:699|(1:701)(1:702)|(2:704|(2:706|(1:708))(1:(2:710|(1:712))(2:713|(1:719)))))(0))(1:697))))(1:676)|(0)|723|(3:725|744|(24:746|748|750|808|(3:810|812|814)|815|(0)(0)|1015|822|825|(0)|828|(0)(0)|1019|832|(0)|837|(0)|840|(0)(0)|844|(0)|849|(0)(0))(0))(0)))(4:517|(4:519|(1:521)(2:522|(1:524)(2:525|(1:527)))|528|(1:530))|531|(15:539|(1:541)|542|(2:544|(1:546)(1:547))(2:548|(1:550)(30:552|553|(1:555)(1:556)|557|(1:560)|561|(1:563)(1:564)|565|(1:569)|570|(1:576)(1:575)|577|490|1015|822|825|(0)|828|(0)(0)|1019|832|(0)|837|(0)|840|(0)(0)|844|(0)|849|(0)(0)))|551|553|(0)(0)|557|(0)|561|(0)(0)|565|(2:567|569)|570|(21:572|576|577|490|1015|822|825|(0)|828|(0)(0)|1019|832|(0)|837|(0)|840|(0)(0)|844|(0)|849|(0)(0))(0))(1:538)))(3:482|(3:(1:492)(1:494)|493|495)(1:487)|488)|489|490|1015|822|825|(0)|828|(0)(0)|1019|832|(0)|837|(0)|840|(0)(0)|844|(0)|849|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:835:0x1342, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:864:0x1411, code lost:
    
        org.telegram.messenger.FileLog.m1160e(r0);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0220  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0229  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x028c  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x02e1  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0411  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x043f A[Catch: Exception -> 0x0460, TryCatch #0 {Exception -> 0x0460, blocks: (B:163:0x041a, B:165:0x043f, B:167:0x0456, B:170:0x0463, B:172:0x0475, B:174:0x0479, B:175:0x0485, B:177:0x0489, B:178:0x0492, B:180:0x049a, B:181:0x04a3, B:183:0x04ab, B:185:0x04c8, B:187:0x04f8, B:186:0x04db, B:188:0x0500, B:171:0x046d), top: B:1008:0x041a }] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x046d A[Catch: Exception -> 0x0460, TryCatch #0 {Exception -> 0x0460, blocks: (B:163:0x041a, B:165:0x043f, B:167:0x0456, B:170:0x0463, B:172:0x0475, B:174:0x0479, B:175:0x0485, B:177:0x0489, B:178:0x0492, B:180:0x049a, B:181:0x04a3, B:183:0x04ab, B:185:0x04c8, B:187:0x04f8, B:186:0x04db, B:188:0x0500, B:171:0x046d), top: B:1008:0x041a }] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x0479 A[Catch: Exception -> 0x0460, TryCatch #0 {Exception -> 0x0460, blocks: (B:163:0x041a, B:165:0x043f, B:167:0x0456, B:170:0x0463, B:172:0x0475, B:174:0x0479, B:175:0x0485, B:177:0x0489, B:178:0x0492, B:180:0x049a, B:181:0x04a3, B:183:0x04ab, B:185:0x04c8, B:187:0x04f8, B:186:0x04db, B:188:0x0500, B:171:0x046d), top: B:1008:0x041a }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0489 A[Catch: Exception -> 0x0460, TryCatch #0 {Exception -> 0x0460, blocks: (B:163:0x041a, B:165:0x043f, B:167:0x0456, B:170:0x0463, B:172:0x0475, B:174:0x0479, B:175:0x0485, B:177:0x0489, B:178:0x0492, B:180:0x049a, B:181:0x04a3, B:183:0x04ab, B:185:0x04c8, B:187:0x04f8, B:186:0x04db, B:188:0x0500, B:171:0x046d), top: B:1008:0x041a }] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x049a A[Catch: Exception -> 0x0460, TryCatch #0 {Exception -> 0x0460, blocks: (B:163:0x041a, B:165:0x043f, B:167:0x0456, B:170:0x0463, B:172:0x0475, B:174:0x0479, B:175:0x0485, B:177:0x0489, B:178:0x0492, B:180:0x049a, B:181:0x04a3, B:183:0x04ab, B:185:0x04c8, B:187:0x04f8, B:186:0x04db, B:188:0x0500, B:171:0x046d), top: B:1008:0x041a }] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x04ab A[Catch: Exception -> 0x0460, TryCatch #0 {Exception -> 0x0460, blocks: (B:163:0x041a, B:165:0x043f, B:167:0x0456, B:170:0x0463, B:172:0x0475, B:174:0x0479, B:175:0x0485, B:177:0x0489, B:178:0x0492, B:180:0x049a, B:181:0x04a3, B:183:0x04ab, B:185:0x04c8, B:187:0x04f8, B:186:0x04db, B:188:0x0500, B:171:0x046d), top: B:1008:0x041a }] */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0500 A[Catch: Exception -> 0x0460, TRY_LEAVE, TryCatch #0 {Exception -> 0x0460, blocks: (B:163:0x041a, B:165:0x043f, B:167:0x0456, B:170:0x0463, B:172:0x0475, B:174:0x0479, B:175:0x0485, B:177:0x0489, B:178:0x0492, B:180:0x049a, B:181:0x04a3, B:183:0x04ab, B:185:0x04c8, B:187:0x04f8, B:186:0x04db, B:188:0x0500, B:171:0x046d), top: B:1008:0x041a }] */
    /* JADX WARN: Removed duplicated region for block: B:193:0x050d  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0521  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x0528  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0566  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x057e  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x0585  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0599  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x05a2  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x064a  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x07d9  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x07e9  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x07eb  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x080a  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x080e  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x0825  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x0872  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0889  */
    /* JADX WARN: Removed duplicated region for block: B:331:0x08af  */
    /* JADX WARN: Removed duplicated region for block: B:555:0x0cd5  */
    /* JADX WARN: Removed duplicated region for block: B:556:0x0cd8  */
    /* JADX WARN: Removed duplicated region for block: B:560:0x0ce8  */
    /* JADX WARN: Removed duplicated region for block: B:563:0x0cf8  */
    /* JADX WARN: Removed duplicated region for block: B:564:0x0cfc  */
    /* JADX WARN: Removed duplicated region for block: B:576:0x0d32  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x016d  */
    /* JADX WARN: Removed duplicated region for block: B:720:0x106c  */
    /* JADX WARN: Removed duplicated region for block: B:722:0x106f  */
    /* JADX WARN: Removed duplicated region for block: B:744:0x1101  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:807:0x1273  */
    /* JADX WARN: Removed duplicated region for block: B:808:0x1275  */
    /* JADX WARN: Removed duplicated region for block: B:817:0x12f8  */
    /* JADX WARN: Removed duplicated region for block: B:821:0x1309  */
    /* JADX WARN: Removed duplicated region for block: B:827:0x131a  */
    /* JADX WARN: Removed duplicated region for block: B:830:0x1324  */
    /* JADX WARN: Removed duplicated region for block: B:831:0x1327  */
    /* JADX WARN: Removed duplicated region for block: B:834:0x133a A[Catch: Exception -> 0x1342, TryCatch #6 {Exception -> 0x1342, blocks: (B:832:0x1336, B:834:0x133a, B:837:0x1345, B:839:0x1366, B:840:0x136d, B:842:0x1375, B:844:0x1388, B:846:0x138d, B:848:0x13ae, B:849:0x13cf, B:852:0x13d5, B:854:0x13d9, B:858:0x13e2, B:860:0x13fd, B:862:0x1405, B:859:0x13f4), top: B:1019:0x1336 }] */
    /* JADX WARN: Removed duplicated region for block: B:839:0x1366 A[Catch: Exception -> 0x1342, TryCatch #6 {Exception -> 0x1342, blocks: (B:832:0x1336, B:834:0x133a, B:837:0x1345, B:839:0x1366, B:840:0x136d, B:842:0x1375, B:844:0x1388, B:846:0x138d, B:848:0x13ae, B:849:0x13cf, B:852:0x13d5, B:854:0x13d9, B:858:0x13e2, B:860:0x13fd, B:862:0x1405, B:859:0x13f4), top: B:1019:0x1336 }] */
    /* JADX WARN: Removed duplicated region for block: B:842:0x1375 A[Catch: Exception -> 0x1342, TryCatch #6 {Exception -> 0x1342, blocks: (B:832:0x1336, B:834:0x133a, B:837:0x1345, B:839:0x1366, B:840:0x136d, B:842:0x1375, B:844:0x1388, B:846:0x138d, B:848:0x13ae, B:849:0x13cf, B:852:0x13d5, B:854:0x13d9, B:858:0x13e2, B:860:0x13fd, B:862:0x1405, B:859:0x13f4), top: B:1019:0x1336 }] */
    /* JADX WARN: Removed duplicated region for block: B:843:0x1387  */
    /* JADX WARN: Removed duplicated region for block: B:846:0x138d A[Catch: Exception -> 0x1342, TryCatch #6 {Exception -> 0x1342, blocks: (B:832:0x1336, B:834:0x133a, B:837:0x1345, B:839:0x1366, B:840:0x136d, B:842:0x1375, B:844:0x1388, B:846:0x138d, B:848:0x13ae, B:849:0x13cf, B:852:0x13d5, B:854:0x13d9, B:858:0x13e2, B:860:0x13fd, B:862:0x1405, B:859:0x13f4), top: B:1019:0x1336 }] */
    /* JADX WARN: Removed duplicated region for block: B:851:0x13d3 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:859:0x13f4 A[Catch: Exception -> 0x1342, TryCatch #6 {Exception -> 0x1342, blocks: (B:832:0x1336, B:834:0x133a, B:837:0x1345, B:839:0x1366, B:840:0x136d, B:842:0x1375, B:844:0x1388, B:846:0x138d, B:848:0x13ae, B:849:0x13cf, B:852:0x13d5, B:854:0x13d9, B:858:0x13e2, B:860:0x13fd, B:862:0x1405, B:859:0x13f4), top: B:1019:0x1336 }] */
    /* JADX WARN: Removed duplicated region for block: B:862:0x1405 A[Catch: Exception -> 0x1342, TRY_LEAVE, TryCatch #6 {Exception -> 0x1342, blocks: (B:832:0x1336, B:834:0x133a, B:837:0x1345, B:839:0x1366, B:840:0x136d, B:842:0x1375, B:844:0x1388, B:846:0x138d, B:848:0x13ae, B:849:0x13cf, B:852:0x13d5, B:854:0x13d9, B:858:0x13e2, B:860:0x13fd, B:862:0x1405, B:859:0x13f4), top: B:1019:0x1336 }] */
    /* JADX WARN: Removed duplicated region for block: B:867:0x141c A[Catch: Exception -> 0x1423, TryCatch #5 {Exception -> 0x1423, blocks: (B:865:0x1414, B:867:0x141c, B:871:0x1427, B:873:0x142c, B:875:0x1443, B:877:0x144f, B:878:0x1453, B:879:0x1456, B:881:0x1460, B:883:0x1468, B:884:0x146d, B:886:0x1471, B:888:0x1475, B:892:0x147d, B:894:0x14b1, B:899:0x14c0, B:901:0x14c8, B:903:0x14e1, B:905:0x14e7, B:907:0x14f3, B:909:0x14ff, B:911:0x1505, B:915:0x1518, B:917:0x153d, B:919:0x1549, B:927:0x155d, B:928:0x157b, B:930:0x1583, B:931:0x158d, B:933:0x1595, B:935:0x15b3, B:943:0x15c1, B:944:0x15c7, B:945:0x15d2, B:946:0x15dc, B:948:0x15e0, B:949:0x15e9, B:951:0x15f2, B:953:0x15fa, B:956:0x1613, B:955:0x1604, B:921:0x154d, B:923:0x1553, B:925:0x1557, B:916:0x151b, B:898:0x14b8, B:957:0x1623, B:959:0x1629), top: B:1017:0x1414 }] */
    /* JADX WARN: Removed duplicated region for block: B:870:0x1426  */
    /* JADX WARN: Removed duplicated region for block: B:873:0x142c A[Catch: Exception -> 0x1423, TryCatch #5 {Exception -> 0x1423, blocks: (B:865:0x1414, B:867:0x141c, B:871:0x1427, B:873:0x142c, B:875:0x1443, B:877:0x144f, B:878:0x1453, B:879:0x1456, B:881:0x1460, B:883:0x1468, B:884:0x146d, B:886:0x1471, B:888:0x1475, B:892:0x147d, B:894:0x14b1, B:899:0x14c0, B:901:0x14c8, B:903:0x14e1, B:905:0x14e7, B:907:0x14f3, B:909:0x14ff, B:911:0x1505, B:915:0x1518, B:917:0x153d, B:919:0x1549, B:927:0x155d, B:928:0x157b, B:930:0x1583, B:931:0x158d, B:933:0x1595, B:935:0x15b3, B:943:0x15c1, B:944:0x15c7, B:945:0x15d2, B:946:0x15dc, B:948:0x15e0, B:949:0x15e9, B:951:0x15f2, B:953:0x15fa, B:956:0x1613, B:955:0x1604, B:921:0x154d, B:923:0x1553, B:925:0x1557, B:916:0x151b, B:898:0x14b8, B:957:0x1623, B:959:0x1629), top: B:1017:0x1414 }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:901:0x14c8 A[Catch: Exception -> 0x1423, TryCatch #5 {Exception -> 0x1423, blocks: (B:865:0x1414, B:867:0x141c, B:871:0x1427, B:873:0x142c, B:875:0x1443, B:877:0x144f, B:878:0x1453, B:879:0x1456, B:881:0x1460, B:883:0x1468, B:884:0x146d, B:886:0x1471, B:888:0x1475, B:892:0x147d, B:894:0x14b1, B:899:0x14c0, B:901:0x14c8, B:903:0x14e1, B:905:0x14e7, B:907:0x14f3, B:909:0x14ff, B:911:0x1505, B:915:0x1518, B:917:0x153d, B:919:0x1549, B:927:0x155d, B:928:0x157b, B:930:0x1583, B:931:0x158d, B:933:0x1595, B:935:0x15b3, B:943:0x15c1, B:944:0x15c7, B:945:0x15d2, B:946:0x15dc, B:948:0x15e0, B:949:0x15e9, B:951:0x15f2, B:953:0x15fa, B:956:0x1613, B:955:0x1604, B:921:0x154d, B:923:0x1553, B:925:0x1557, B:916:0x151b, B:898:0x14b8, B:957:0x1623, B:959:0x1629), top: B:1017:0x1414 }] */
    /* JADX WARN: Removed duplicated region for block: B:914:0x1517  */
    /* JADX WARN: Removed duplicated region for block: B:916:0x151b A[Catch: Exception -> 0x1423, TryCatch #5 {Exception -> 0x1423, blocks: (B:865:0x1414, B:867:0x141c, B:871:0x1427, B:873:0x142c, B:875:0x1443, B:877:0x144f, B:878:0x1453, B:879:0x1456, B:881:0x1460, B:883:0x1468, B:884:0x146d, B:886:0x1471, B:888:0x1475, B:892:0x147d, B:894:0x14b1, B:899:0x14c0, B:901:0x14c8, B:903:0x14e1, B:905:0x14e7, B:907:0x14f3, B:909:0x14ff, B:911:0x1505, B:915:0x1518, B:917:0x153d, B:919:0x1549, B:927:0x155d, B:928:0x157b, B:930:0x1583, B:931:0x158d, B:933:0x1595, B:935:0x15b3, B:943:0x15c1, B:944:0x15c7, B:945:0x15d2, B:946:0x15dc, B:948:0x15e0, B:949:0x15e9, B:951:0x15f2, B:953:0x15fa, B:956:0x1613, B:955:0x1604, B:921:0x154d, B:923:0x1553, B:925:0x1557, B:916:0x151b, B:898:0x14b8, B:957:0x1623, B:959:0x1629), top: B:1017:0x1414 }] */
    /* JADX WARN: Removed duplicated region for block: B:930:0x1583 A[Catch: Exception -> 0x1423, TryCatch #5 {Exception -> 0x1423, blocks: (B:865:0x1414, B:867:0x141c, B:871:0x1427, B:873:0x142c, B:875:0x1443, B:877:0x144f, B:878:0x1453, B:879:0x1456, B:881:0x1460, B:883:0x1468, B:884:0x146d, B:886:0x1471, B:888:0x1475, B:892:0x147d, B:894:0x14b1, B:899:0x14c0, B:901:0x14c8, B:903:0x14e1, B:905:0x14e7, B:907:0x14f3, B:909:0x14ff, B:911:0x1505, B:915:0x1518, B:917:0x153d, B:919:0x1549, B:927:0x155d, B:928:0x157b, B:930:0x1583, B:931:0x158d, B:933:0x1595, B:935:0x15b3, B:943:0x15c1, B:944:0x15c7, B:945:0x15d2, B:946:0x15dc, B:948:0x15e0, B:949:0x15e9, B:951:0x15f2, B:953:0x15fa, B:956:0x1613, B:955:0x1604, B:921:0x154d, B:923:0x1553, B:925:0x1557, B:916:0x151b, B:898:0x14b8, B:957:0x1623, B:959:0x1629), top: B:1017:0x1414 }] */
    /* JADX WARN: Removed duplicated region for block: B:948:0x15e0 A[Catch: Exception -> 0x1423, TryCatch #5 {Exception -> 0x1423, blocks: (B:865:0x1414, B:867:0x141c, B:871:0x1427, B:873:0x142c, B:875:0x1443, B:877:0x144f, B:878:0x1453, B:879:0x1456, B:881:0x1460, B:883:0x1468, B:884:0x146d, B:886:0x1471, B:888:0x1475, B:892:0x147d, B:894:0x14b1, B:899:0x14c0, B:901:0x14c8, B:903:0x14e1, B:905:0x14e7, B:907:0x14f3, B:909:0x14ff, B:911:0x1505, B:915:0x1518, B:917:0x153d, B:919:0x1549, B:927:0x155d, B:928:0x157b, B:930:0x1583, B:931:0x158d, B:933:0x1595, B:935:0x15b3, B:943:0x15c1, B:944:0x15c7, B:945:0x15d2, B:946:0x15dc, B:948:0x15e0, B:949:0x15e9, B:951:0x15f2, B:953:0x15fa, B:956:0x1613, B:955:0x1604, B:921:0x154d, B:923:0x1553, B:925:0x1557, B:916:0x151b, B:898:0x14b8, B:957:0x1623, B:959:0x1629), top: B:1017:0x1414 }] */
    /* JADX WARN: Removed duplicated region for block: B:951:0x15f2 A[Catch: Exception -> 0x1423, TryCatch #5 {Exception -> 0x1423, blocks: (B:865:0x1414, B:867:0x141c, B:871:0x1427, B:873:0x142c, B:875:0x1443, B:877:0x144f, B:878:0x1453, B:879:0x1456, B:881:0x1460, B:883:0x1468, B:884:0x146d, B:886:0x1471, B:888:0x1475, B:892:0x147d, B:894:0x14b1, B:899:0x14c0, B:901:0x14c8, B:903:0x14e1, B:905:0x14e7, B:907:0x14f3, B:909:0x14ff, B:911:0x1505, B:915:0x1518, B:917:0x153d, B:919:0x1549, B:927:0x155d, B:928:0x157b, B:930:0x1583, B:931:0x158d, B:933:0x1595, B:935:0x15b3, B:943:0x15c1, B:944:0x15c7, B:945:0x15d2, B:946:0x15dc, B:948:0x15e0, B:949:0x15e9, B:951:0x15f2, B:953:0x15fa, B:956:0x1613, B:955:0x1604, B:921:0x154d, B:923:0x1553, B:925:0x1557, B:916:0x151b, B:898:0x14b8, B:957:0x1623, B:959:0x1629), top: B:1017:0x1414 }] */
    /* JADX WARN: Removed duplicated region for block: B:955:0x1604 A[Catch: Exception -> 0x1423, TryCatch #5 {Exception -> 0x1423, blocks: (B:865:0x1414, B:867:0x141c, B:871:0x1427, B:873:0x142c, B:875:0x1443, B:877:0x144f, B:878:0x1453, B:879:0x1456, B:881:0x1460, B:883:0x1468, B:884:0x146d, B:886:0x1471, B:888:0x1475, B:892:0x147d, B:894:0x14b1, B:899:0x14c0, B:901:0x14c8, B:903:0x14e1, B:905:0x14e7, B:907:0x14f3, B:909:0x14ff, B:911:0x1505, B:915:0x1518, B:917:0x153d, B:919:0x1549, B:927:0x155d, B:928:0x157b, B:930:0x1583, B:931:0x158d, B:933:0x1595, B:935:0x15b3, B:943:0x15c1, B:944:0x15c7, B:945:0x15d2, B:946:0x15dc, B:948:0x15e0, B:949:0x15e9, B:951:0x15f2, B:953:0x15fa, B:956:0x1613, B:955:0x1604, B:921:0x154d, B:923:0x1553, B:925:0x1557, B:916:0x151b, B:898:0x14b8, B:957:0x1623, B:959:0x1629), top: B:1017:0x1414 }] */
    /* JADX WARN: Removed duplicated region for block: B:959:0x1629 A[Catch: Exception -> 0x1423, TRY_LEAVE, TryCatch #5 {Exception -> 0x1423, blocks: (B:865:0x1414, B:867:0x141c, B:871:0x1427, B:873:0x142c, B:875:0x1443, B:877:0x144f, B:878:0x1453, B:879:0x1456, B:881:0x1460, B:883:0x1468, B:884:0x146d, B:886:0x1471, B:888:0x1475, B:892:0x147d, B:894:0x14b1, B:899:0x14c0, B:901:0x14c8, B:903:0x14e1, B:905:0x14e7, B:907:0x14f3, B:909:0x14ff, B:911:0x1505, B:915:0x1518, B:917:0x153d, B:919:0x1549, B:927:0x155d, B:928:0x157b, B:930:0x1583, B:931:0x158d, B:933:0x1595, B:935:0x15b3, B:943:0x15c1, B:944:0x15c7, B:945:0x15d2, B:946:0x15dc, B:948:0x15e0, B:949:0x15e9, B:951:0x15f2, B:953:0x15fa, B:956:0x1613, B:955:0x1604, B:921:0x154d, B:923:0x1553, B:925:0x1557, B:916:0x151b, B:898:0x14b8, B:957:0x1623, B:959:0x1629), top: B:1017:0x1414 }] */
    /* JADX WARN: Removed duplicated region for block: B:981:0x1672  */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r15v45 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v230 */
    /* JADX WARN: Type inference failed for: r5v3, types: [java.lang.String, org.telegram.tgnet.TLRPC$Chat, org.telegram.tgnet.TLRPC$User] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setMessageObjectInternal(org.telegram.messenger.MessageObject r50) {
        /*
            Method dump skipped, instructions count: 5909
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.setMessageObjectInternal(org.telegram.messenger.MessageObject):void");
    }

    private String getNameFromDialogId(long j) {
        TLRPC.Chat chat;
        if (j > 0) {
            TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(j));
            if (user != null) {
                return UserObject.getUserName(user);
            }
            return null;
        }
        if (j >= 0 || (chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-j))) == null) {
            return null;
        }
        return chat.title;
    }

    protected boolean isNeedAuthorName() {
        TLRPC.Message message;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject.forceAvatar || !(messageObject.getDialogId() != UserObject.VERIFY || (message = this.currentMessageObject.messageOwner) == null || message.fwd_from == null)) {
            return true;
        }
        if (this.currentMessageObject.isSponsored() || this.currentMessageObject.isGiveawayOrGiveawayResults()) {
            return false;
        }
        if (!this.isBotForum || this.isPinnedChat) {
            return (this.isPinnedChat && this.currentMessageObject.type == 0) || (!this.pinnedTop && this.drawName && this.isChat && (!this.currentMessageObject.isOutOwner() || ((this.currentMessageObject.isSupergroup() && this.currentMessageObject.isFromGroup()) || this.currentMessageObject.isRepostPreview))) || (this.currentMessageObject.isImportedForward() && this.currentMessageObject.messageOwner.fwd_from.from_id == null);
        }
        return false;
    }

    private String getAuthorName() {
        TLRPC.User user = this.currentUser;
        if (user != null) {
            return UserObject.getUserName(user);
        }
        if (this.currentChat != null) {
            MessageObject messageObject = this.currentMessageObject;
            if (messageObject != null && messageObject.getDialogId() != UserObject.REPLY_BOT && this.currentChat.signature_profiles) {
                long peerDialogId = DialogObject.getPeerDialogId(this.currentMessageObject.messageOwner.from_id);
                if (peerDialogId >= 0) {
                    TLRPC.User user2 = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(peerDialogId));
                    if (user2 != null) {
                        return UserObject.getUserName(user2);
                    }
                } else {
                    TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-peerDialogId));
                    if (chat != null) {
                        return chat.title;
                    }
                }
            }
            return this.currentChat.title;
        }
        MessageObject messageObject2 = this.currentMessageObject;
        if (messageObject2 != null && messageObject2.isSponsored()) {
            return this.currentMessageObject.sponsoredTitle;
        }
        return "DELETED";
    }

    private Object getAuthorStatus() {
        MessageObject messageObject;
        TLRPC.User user = this.currentUser;
        if (user != null) {
            Long emojiStatusDocumentId = UserObject.getEmojiStatusDocumentId(user);
            BadgeDTO badge = BadgesController.INSTANCE.getBadge(this.currentUser);
            if (emojiStatusDocumentId != null) {
                TLRPC.EmojiStatus emojiStatus = this.currentUser.emoji_status;
                if (emojiStatus instanceof TLRPC.TL_emojiStatusCollectible) {
                    this.nameStatusSlug = ((TLRPC.TL_emojiStatusCollectible) emojiStatus).slug;
                }
                return emojiStatusDocumentId;
            }
            if (badge != null) {
                return badge;
            }
            if (this.currentUser.premium) {
                return ContextCompat.getDrawable(ApplicationLoader.applicationContext, C2369R.drawable.msg_premium_liststar).mutate();
            }
            return null;
        }
        if (this.currentChat == null || (messageObject = this.currentMessageObject) == null || messageObject.getDialogId() == UserObject.REPLY_BOT || !this.currentChat.signature_profiles) {
            return null;
        }
        long peerDialogId = DialogObject.getPeerDialogId(this.currentMessageObject.messageOwner.from_id);
        if (peerDialogId >= 0) {
            TLRPC.User user2 = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(peerDialogId));
            if (user2 != null) {
                TLRPC.EmojiStatus emojiStatus2 = user2.emoji_status;
                if (emojiStatus2 instanceof TLRPC.TL_emojiStatusCollectible) {
                    this.nameStatusSlug = ((TLRPC.TL_emojiStatusCollectible) emojiStatus2).slug;
                }
            }
            return UserObject.getEmojiStatusDocumentId(user2);
        }
        TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-peerDialogId));
        if (chat == null) {
            return null;
        }
        TLRPC.EmojiStatus emojiStatus3 = chat.emoji_status;
        if (emojiStatus3 instanceof TLRPC.TL_emojiStatusCollectible) {
            this.nameStatusSlug = ((TLRPC.TL_emojiStatusCollectible) emojiStatus3).slug;
        }
        return Long.valueOf(DialogObject.getEmojiStatusDocumentId(emojiStatus3));
    }

    private long getAuthorBotVerificationId() {
        MessageObject messageObject;
        TLRPC.User user = this.currentUser;
        if (user != null) {
            return DialogObject.getBotVerificationIcon(user);
        }
        if (this.currentChat != null && (messageObject = this.currentMessageObject) != null && messageObject.getDialogId() != UserObject.REPLY_BOT && this.currentChat.signature_profiles) {
            long peerDialogId = DialogObject.getPeerDialogId(this.currentMessageObject.messageOwner.from_id);
            if (peerDialogId >= 0) {
                TLRPC.User user2 = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(peerDialogId));
                if (user2 != null) {
                    return DialogObject.getBotVerificationIcon(user2);
                }
            } else {
                TLRPC.Chat chat = MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-peerDialogId));
                if (chat != null) {
                    return DialogObject.getBotVerificationIcon(chat);
                }
            }
        }
        return 0L;
    }

    private String getForwardedMessageText(MessageObject messageObject) {
        if (this.hasPsaHint) {
            String string = LocaleController.getString("PsaMessage_" + messageObject.messageOwner.fwd_from.psa_type);
            return string == null ? LocaleController.getString("PsaMessageDefault", C2369R.string.PsaMessageDefault) : string;
        }
        return LocaleController.getString(C2369R.string.ForwardedFrom);
    }

    public int getExtraInsetHeight() {
        int iM1146dp = this.addedCaptionHeight;
        if (this.hasFactCheck) {
            iM1146dp += AndroidUtilities.m1146dp((this.reactionsLayoutInBubble.isEmpty ? 18 : 0) + 2) + this.factCheckHeight;
        }
        if (this.drawCommentButton) {
            iM1146dp += AndroidUtilities.m1146dp(shouldDrawTimeOnMedia() ? 41.3f : 43.0f);
        }
        return (this.reactionsLayoutInBubble.isEmpty || !this.currentMessageObject.shouldDrawReactionsInLayout()) ? iM1146dp : iM1146dp + this.reactionsLayoutInBubble.totalHeight;
    }

    public ImageReceiver getAvatarImage() {
        if (this.isAvatarVisible) {
            return this.avatarImage;
        }
        return null;
    }

    public float getCheckBoxTranslation() {
        return this.checkBoxTranslation;
    }

    public boolean shouldDrawAlphaLayer() {
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        return (groupedMessages == null || !groupedMessages.transitionParams.backgroundChangeBounds) && getAlpha() != 1.0f;
    }

    public float getCaptionX() {
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateBackgroundBoundsInner) {
            if (transitionParams.transformGroupToSingleMessage) {
                this.captionX += transitionParams.deltaLeft;
            } else if (transitionParams.moveCaption) {
                float f = this.captionX;
                TransitionParams transitionParams2 = this.transitionParams;
                float f2 = transitionParams2.animateChangeProgress;
                this.captionX = (f * f2) + (transitionParams2.captionFromX * (1.0f - f2));
            } else if (!this.currentMessageObject.isVoice() || !TextUtils.isEmpty(this.currentMessageObject.caption)) {
                this.captionX += this.transitionParams.deltaLeft;
            }
        }
        return this.captionX;
    }

    public float getCaptionY() {
        float f = this.captionY;
        TransitionParams transitionParams = this.transitionParams;
        if (!transitionParams.animateBackgroundBoundsInner) {
            return f;
        }
        if (transitionParams.transformGroupToSingleMessage) {
            return f - getTranslationY();
        }
        if (!transitionParams.moveCaption) {
            return f;
        }
        float f2 = this.captionY;
        TransitionParams transitionParams2 = this.transitionParams;
        float f3 = transitionParams2.animateChangeProgress;
        return (f2 * f3) + (transitionParams2.captionFromY * (1.0f - f3));
    }

    public boolean isDrawPinnedBottom() {
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        return this.mediaBackground || this.drawPinnedBottom || (groupedMessagePosition != null && (groupedMessagePosition.flags & 8) == 0 && this.currentMessagesGroup.isDocuments);
    }

    public void drawCheckBox(Canvas canvas) {
        float top;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.isSending()) {
            return;
        }
        MessageObject messageObject2 = this.currentMessageObject;
        if (messageObject2.type == 27 || messageObject2.isSendError() || this.checkBox == null) {
            return;
        }
        if (this.checkBoxVisible || this.checkBoxAnimationInProgress) {
            MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
            if (groupedMessagePosition != null) {
                int i = groupedMessagePosition.flags;
                if ((i & 8) == 0 || (i & 1) == 0) {
                    return;
                }
            }
            canvas.save();
            float y = getY() + getPaddingTop();
            MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
            if (groupedMessages != null && groupedMessages.messages.size() > 1) {
                top = (getTop() + this.currentMessagesGroup.transitionParams.offsetTop) - getTranslationY();
            } else {
                top = y + this.transitionParams.deltaTop;
            }
            canvas.translate(this.sideMenuWidth, top + this.transitionYOffsetForDrawables);
            this.checkBox.draw(canvas);
            canvas.restore();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003f A[PHI: r3 r5
      0x003f: PHI (r3v3 int) = (r3v2 int), (r3v10 int) binds: [B:16:0x001c, B:18:0x002c] A[DONT_GENERATE, DONT_INLINE]
      0x003f: PHI (r5v1 int) = (r5v0 int), (r5v9 int) binds: [B:16:0x001c, B:18:0x002c] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setBackgroundTopY(boolean r14) {
        /*
            r13 = this;
            r0 = 0
            r1 = 0
        L2:
            r2 = 2
            if (r1 >= r2) goto L79
            r2 = 1
            if (r1 != r2) goto Lc
            if (r14 != 0) goto Lc
            goto L79
        Lc:
            if (r1 != 0) goto L12
            org.telegram.ui.ActionBar.Theme$MessageDrawable r3 = r13.currentBackgroundDrawable
        L10:
            r4 = r3
            goto L15
        L12:
            org.telegram.ui.ActionBar.Theme$MessageDrawable r3 = r13.currentBackgroundSelectedDrawable
            goto L10
        L15:
            if (r4 != 0) goto L18
            goto L76
        L18:
            int r3 = r13.parentWidth
            int r5 = r13.parentHeight
            if (r5 != 0) goto L3f
            int r3 = r13.getParentWidth()
            android.graphics.Point r5 = org.telegram.messenger.AndroidUtilities.displaySize
            int r5 = r5.y
            android.view.ViewParent r6 = r13.getParent()
            boolean r6 = r6 instanceof android.view.View
            if (r6 == 0) goto L3f
            android.view.ViewParent r3 = r13.getParent()
            android.view.View r3 = (android.view.View) r3
            int r5 = r3.getMeasuredWidth()
            int r3 = r3.getMeasuredHeight()
            r7 = r3
            r6 = r5
            goto L41
        L3f:
            r6 = r3
            r7 = r5
        L41:
            if (r14 == 0) goto L48
            float r3 = r13.getY()
            goto L4d
        L48:
            int r3 = r13.getTop()
            float r3 = (float) r3
        L4d:
            float r5 = r13.parentViewTopOffset
            float r3 = r3 + r5
            int r3 = (int) r3
            int r8 = (int) r5
            int r9 = r13.blurredViewTopOffset
            int r10 = r13.blurredViewBottomOffset
            boolean r11 = r13.pinnedTop
            boolean r5 = r13.pinnedBottom
            if (r5 != 0) goto L6a
            org.telegram.ui.Cells.ChatMessageCell$TransitionParams r5 = r13.transitionParams
            float r5 = r5.changePinnedBottomProgress
            r12 = 1065353216(0x3f800000, float:1.0)
            int r5 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r5 == 0) goto L67
            goto L6a
        L67:
            r12 = 0
        L68:
            r5 = r3
            goto L6c
        L6a:
            r12 = 1
            goto L68
        L6c:
            r4.setTop(r5, r6, r7, r8, r9, r10, r11, r12)
            boolean r2 = r13.hasInlineBotButtons()
            r4.setBotButtonsBottom(r2)
        L76:
            int r1 = r1 + 1
            goto L2
        L79:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.setBackgroundTopY(boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0028 A[PHI: r1 r2
      0x0028: PHI (r1v1 int) = (r1v0 int), (r1v10 int) binds: [B:3:0x0006, B:5:0x0016] A[DONT_GENERATE, DONT_INLINE]
      0x0028: PHI (r2v1 int) = (r2v0 int), (r2v5 int) binds: [B:3:0x0006, B:5:0x0016] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setBackgroundTopY(int r10) {
        /*
            r9 = this;
            org.telegram.ui.ActionBar.Theme$MessageDrawable r0 = r9.currentBackgroundDrawable
            int r1 = r9.parentWidth
            int r2 = r9.parentHeight
            if (r2 != 0) goto L28
            int r1 = r9.getParentWidth()
            android.graphics.Point r2 = org.telegram.messenger.AndroidUtilities.displaySize
            int r2 = r2.y
            android.view.ViewParent r3 = r9.getParent()
            boolean r3 = r3 instanceof android.view.View
            if (r3 == 0) goto L28
            android.view.ViewParent r1 = r9.getParent()
            android.view.View r1 = (android.view.View) r1
            int r2 = r1.getMeasuredWidth()
            int r1 = r1.getMeasuredHeight()
            r3 = r1
            goto L2a
        L28:
            r3 = r2
            r2 = r1
        L2a:
            float r1 = r9.parentViewTopOffset
            float r10 = (float) r10
            float r10 = r10 + r1
            int r10 = (int) r10
            int r4 = (int) r1
            int r5 = r9.blurredViewTopOffset
            int r6 = r9.blurredViewBottomOffset
            boolean r7 = r9.pinnedTop
            boolean r1 = r9.pinnedBottom
            if (r1 != 0) goto L49
            org.telegram.ui.Cells.ChatMessageCell$TransitionParams r1 = r9.transitionParams
            float r1 = r1.changePinnedBottomProgress
            r8 = 1065353216(0x3f800000, float:1.0)
            int r1 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r1 == 0) goto L45
            goto L49
        L45:
            r1 = 0
            r8 = 0
        L47:
            r1 = r10
            goto L4c
        L49:
            r1 = 1
            r8 = 1
            goto L47
        L4c:
            r0.setTop(r1, r2, r3, r4, r5, r6, r7, r8)
            boolean r10 = r9.hasInlineBotButtons()
            r0.setBotButtonsBottom(r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.setBackgroundTopY(int):void");
    }

    public void setDrawableBoundsInner(Drawable drawable, int i, int i2, int i3, int i4) {
        if (drawable != null) {
            float f = i4 + i2;
            TransitionParams transitionParams = this.transitionParams;
            float f2 = transitionParams.deltaBottom;
            this.transitionYOffsetForDrawables = (f + f2) - ((int) (f + f2));
            drawable.setBounds((int) (i + transitionParams.deltaLeft), (int) (i2 + transitionParams.deltaTop), (int) (i + i3 + transitionParams.deltaRight), (int) (f + f2));
        }
    }

    public void setupTextColors() {
        int i;
        if (this.currentMessageObject.isOutOwner()) {
            TextPaint textPaint = Theme.chat_msgTextPaint;
            int i2 = Theme.key_chat_messageTextOut;
            textPaint.setColor(getThemedColor(i2));
            Theme.chat_msgGameTextPaint.setColor(getThemedColor(i2));
            Theme.chat_msgTextCodePaint.setColor(getThemedColor(i2));
            Theme.chat_msgTextCode2Paint.setColor(getThemedColor(i2));
            Theme.chat_msgTextCode3Paint.setColor(getThemedColor(i2));
            TextPaint textPaint2 = Theme.chat_msgGameTextPaint;
            TextPaint textPaint3 = Theme.chat_replyTextPaint;
            TextPaint textPaint4 = Theme.chat_quoteTextPaint;
            TextPaint textPaint5 = Theme.chat_msgTextPaint;
            TextPaint textPaint6 = Theme.chat_msgTextCodePaint;
            TextPaint textPaint7 = Theme.chat_msgTextCode2Paint;
            TextPaint textPaint8 = Theme.chat_msgTextCode3Paint;
            int themedColor = getThemedColor(Theme.key_chat_messageLinkOut);
            textPaint8.linkColor = themedColor;
            textPaint7.linkColor = themedColor;
            textPaint6.linkColor = themedColor;
            textPaint5.linkColor = themedColor;
            textPaint4.linkColor = themedColor;
            textPaint3.linkColor = themedColor;
            textPaint2.linkColor = themedColor;
        } else {
            TextPaint textPaint9 = Theme.chat_msgTextPaint;
            int i3 = Theme.key_chat_messageTextIn;
            textPaint9.setColor(getThemedColor(i3));
            Theme.chat_msgGameTextPaint.setColor(getThemedColor(i3));
            Theme.chat_msgTextCodePaint.setColor(getThemedColor(i3));
            Theme.chat_msgTextCode2Paint.setColor(getThemedColor(i3));
            Theme.chat_msgTextCode3Paint.setColor(getThemedColor(i3));
            TextPaint textPaint10 = Theme.chat_msgGameTextPaint;
            TextPaint textPaint11 = Theme.chat_replyTextPaint;
            TextPaint textPaint12 = Theme.chat_quoteTextPaint;
            TextPaint textPaint13 = Theme.chat_msgTextPaint;
            TextPaint textPaint14 = Theme.chat_msgTextCodePaint;
            TextPaint textPaint15 = Theme.chat_msgTextCode2Paint;
            TextPaint textPaint16 = Theme.chat_msgTextCode3Paint;
            int themedColor2 = getThemedColor(Theme.key_chat_messageLinkIn);
            textPaint16.linkColor = themedColor2;
            textPaint15.linkColor = themedColor2;
            textPaint14.linkColor = themedColor2;
            textPaint13.linkColor = themedColor2;
            textPaint12.linkColor = themedColor2;
            textPaint11.linkColor = themedColor2;
            textPaint10.linkColor = themedColor2;
        }
        if (this.documentAttach != null) {
            int i4 = this.documentAttachType;
            if (i4 == 3 || i4 == 7) {
                if (this.currentMessageObject.isOutOwner()) {
                    this.seekBarWaveform.setColors(getThemedColor(Theme.key_chat_outVoiceSeekbar), getThemedColor(Theme.key_chat_outVoiceSeekbarFill), getThemedColor(Theme.key_chat_outVoiceSeekbarSelected));
                    SeekBar seekBar = this.seekBar;
                    int themedColor3 = getThemedColor(Theme.key_chat_outAudioSeekbar);
                    int themedColor4 = getThemedColor(Theme.key_chat_outAudioCacheSeekbar);
                    int i5 = Theme.key_chat_outAudioSeekbarFill;
                    seekBar.setColors(themedColor3, themedColor4, getThemedColor(i5), getThemedColor(i5), getThemedColor(Theme.key_chat_outAudioSeekbarSelected));
                } else if (this.hasLinkPreview && this.linkLine != null) {
                    this.seekBarWaveform.setColors(Theme.adaptHue(getThemedColor(Theme.key_chat_inVoiceSeekbar), this.linkLine.getColor()), Theme.adaptHue(getThemedColor(Theme.key_chat_inVoiceSeekbarFill), this.linkLine.getColor()), Theme.adaptHue(getThemedColor(Theme.key_chat_inVoiceSeekbarSelected), this.linkLine.getColor()));
                    SeekBar seekBar2 = this.seekBar;
                    int iAdaptHue = Theme.adaptHue(getThemedColor(Theme.key_chat_inAudioSeekbar), this.linkLine.getColor());
                    int iAdaptHue2 = Theme.adaptHue(getThemedColor(Theme.key_chat_inAudioCacheSeekbar), this.linkLine.getColor());
                    int i6 = Theme.key_chat_inAudioSeekbarFill;
                    seekBar2.setColors(iAdaptHue, iAdaptHue2, Theme.adaptHue(getThemedColor(i6), this.linkLine.getColor()), Theme.adaptHue(getThemedColor(i6), this.linkLine.getColor()), Theme.adaptHue(getThemedColor(Theme.key_chat_inAudioSeekbarSelected), this.linkLine.getColor()));
                } else {
                    this.seekBarWaveform.setColors(getThemedColor(Theme.key_chat_inVoiceSeekbar), getThemedColor(Theme.key_chat_inVoiceSeekbarFill), getThemedColor(Theme.key_chat_inVoiceSeekbarSelected));
                    SeekBar seekBar3 = this.seekBar;
                    int themedColor5 = getThemedColor(Theme.key_chat_inAudioSeekbar);
                    int themedColor6 = getThemedColor(Theme.key_chat_inAudioCacheSeekbar);
                    int i7 = Theme.key_chat_inAudioSeekbarFill;
                    seekBar3.setColors(themedColor5, themedColor6, getThemedColor(i7), getThemedColor(i7), getThemedColor(Theme.key_chat_inAudioSeekbarSelected));
                }
            } else if (i4 == 5) {
                if (this.currentMessageObject.isOutOwner()) {
                    SeekBar seekBar4 = this.seekBar;
                    int themedColor7 = getThemedColor(Theme.key_chat_outAudioSeekbar);
                    int themedColor8 = getThemedColor(Theme.key_chat_outAudioCacheSeekbar);
                    int i8 = Theme.key_chat_outAudioSeekbarFill;
                    seekBar4.setColors(themedColor7, themedColor8, getThemedColor(i8), getThemedColor(i8), getThemedColor(Theme.key_chat_outAudioSeekbarSelected));
                } else if (this.hasLinkPreview && this.linkLine != null) {
                    SeekBar seekBar5 = this.seekBar;
                    int iAdaptHue3 = Theme.adaptHue(getThemedColor(Theme.key_chat_inAudioSeekbar), this.linkLine.getColor());
                    int iAdaptHue4 = Theme.adaptHue(getThemedColor(Theme.key_chat_inAudioCacheSeekbar), this.linkLine.getColor());
                    int i9 = Theme.key_chat_inAudioSeekbarFill;
                    seekBar5.setColors(iAdaptHue3, iAdaptHue4, Theme.adaptHue(getThemedColor(i9), this.linkLine.getColor()), Theme.adaptHue(getThemedColor(i9), this.linkLine.getColor()), Theme.adaptHue(getThemedColor(Theme.key_chat_inAudioSeekbarSelected), this.linkLine.getColor()));
                } else {
                    SeekBar seekBar6 = this.seekBar;
                    int themedColor9 = getThemedColor(Theme.key_chat_inAudioSeekbar);
                    int themedColor10 = getThemedColor(Theme.key_chat_inAudioCacheSeekbar);
                    int i10 = Theme.key_chat_inAudioSeekbarFill;
                    seekBar6.setColors(themedColor9, themedColor10, getThemedColor(i10), getThemedColor(i10), getThemedColor(Theme.key_chat_inAudioSeekbarSelected));
                }
            }
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject.type == 5) {
            TextPaint textPaint17 = Theme.chat_timePaint;
            int themedColor11 = getThemedColor(Theme.key_chat_serviceText);
            if (isDrawSelectionBackground()) {
                i = this.currentMessageObject.isOutOwner() ? Theme.key_chat_outTimeSelectedText : Theme.key_chat_inTimeSelectedText;
            } else {
                i = this.currentMessageObject.isOutOwner() ? Theme.key_chat_outTimeText : Theme.key_chat_inTimeText;
            }
            textPaint17.setColor(ColorUtils.blendARGB(themedColor11, getThemedColor(i), getVideoTranscriptionProgress()));
            return;
        }
        if (this.mediaBackground) {
            if (messageObject.shouldDrawWithoutBackground()) {
                Theme.chat_timePaint.setColor(getThemedColor(Theme.key_chat_serviceText));
                return;
            } else {
                Theme.chat_timePaint.setColor(getThemedColor(Theme.key_chat_mediaTimeText));
                return;
            }
        }
        if (messageObject.isOutOwner()) {
            Theme.chat_timePaint.setColor(getThemedColor(isDrawSelectionBackground() ? Theme.key_chat_outTimeSelectedText : Theme.key_chat_outTimeText));
        } else {
            Theme.chat_timePaint.setColor(getThemedColor(isDrawSelectionBackground() ? Theme.key_chat_inTimeSelectedText : Theme.key_chat_inTimeText));
        }
    }

    @Override // org.telegram.p023ui.Cells.BaseCell
    public int getBoundsLeft() {
        int iM1146dp;
        int iM1146dp2;
        float f;
        MessageObject messageObject = this.currentMessageObject;
        boolean z = messageObject != null && messageObject.isOutOwner();
        if (needDrawAvatar()) {
            if (this.currentPosition != null) {
                f = 73.0f;
            } else {
                MessageObject messageObject2 = this.currentMessageObject;
                f = (messageObject2 == null || !messageObject2.isRepostPreview) ? 63 : 42;
            }
            iM1146dp = AndroidUtilities.m1146dp(f);
        } else {
            iM1146dp = 0;
        }
        int backgroundDrawableLeft = (getBackgroundDrawableLeft() - iM1146dp) - ((z && (checkNeedDrawShareButton(this.currentMessageObject) || this.useTranscribeButton)) ? AndroidUtilities.m1146dp(48.0f) : 0);
        if (this.botButtons != null) {
            int widthForButtons = getWidthForButtons();
            MessageObject messageObject3 = this.currentMessageObject;
            if (messageObject3 != null && messageObject3.isOutOwner()) {
                iM1146dp2 = (getMeasuredWidth() - widthForButtons) - AndroidUtilities.m1146dp(10.0f);
            } else {
                iM1146dp2 = this.backgroundDrawableLeft + AndroidUtilities.m1146dp((this.mediaBackground || this.drawPinnedBottom) ? 1.0f : 7.0f);
            }
            int iMax = ConnectionsManager.DEFAULT_DATACENTER_ID;
            for (int i = 0; i < this.botButtons.size(); i++) {
                iMax = Math.max(iMax, ((int) (((BotButton) this.botButtons.get(i)).f1799x * widthForButtons)) + iM1146dp2);
            }
            backgroundDrawableLeft = Math.min(backgroundDrawableLeft, iMax);
        }
        if (this.starsPriceText != null) {
            backgroundDrawableLeft = Math.min(backgroundDrawableLeft, ((int) ((getParentWidth() - this.starsPriceText.getWidth()) - AndroidUtilities.m1146dp(18.0f))) / 2);
        }
        if (this.topicSeparator != null) {
            backgroundDrawableLeft = Math.min(this.sideMenuWidth, backgroundDrawableLeft);
        }
        return Math.max(0, backgroundDrawableLeft);
    }

    @Override // org.telegram.p023ui.Cells.BaseCell
    public int getBoundsRight() {
        int iM1146dp;
        MessageObject messageObject = this.currentMessageObject;
        int backgroundDrawableRight = getBackgroundDrawableRight() + ((messageObject == null || messageObject.isOutOwner() || !(checkNeedDrawShareButton(this.currentMessageObject) || this.useTranscribeButton)) ? 0 : AndroidUtilities.m1146dp(48.0f));
        if (this.botButtons != null) {
            int widthForButtons = getWidthForButtons();
            MessageObject messageObject2 = this.currentMessageObject;
            if (messageObject2 != null && messageObject2.isOutOwner()) {
                iM1146dp = (getMeasuredWidth() - getWidthForButtons()) - AndroidUtilities.m1146dp(10.0f);
            } else {
                iM1146dp = this.backgroundDrawableLeft + AndroidUtilities.m1146dp((this.mediaBackground || this.drawPinnedBottom) ? 1.0f : 7.0f);
            }
            int iMax = 0;
            for (int i = 0; i < this.botButtons.size(); i++) {
                BotButton botButton = (BotButton) this.botButtons.get(i);
                float f = widthForButtons;
                iMax = Math.max(iMax, ((int) (botButton.f1799x * f)) + iM1146dp + ((int) (botButton.width * f)));
            }
            backgroundDrawableRight = Math.max(backgroundDrawableRight, iMax);
        }
        if (this.starsPriceText != null) {
            backgroundDrawableRight = Math.max(backgroundDrawableRight, ((int) ((getParentWidth() + this.starsPriceText.getWidth()) + AndroidUtilities.m1146dp(18.0f))) / 2);
        }
        return this.topicSeparator != null ? Math.max(backgroundDrawableRight, getWidth()) : backgroundDrawableRight;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        drawInternal(canvas);
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x02ab  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawInternal(android.graphics.Canvas r20) {
        /*
            Method dump skipped, instructions count: 1255
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawInternal(android.graphics.Canvas):void");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:377:0x07ae A[PHI: r3 r4
      0x07ae: PHI (r3v50 int) = (r3v49 int), (r3v65 int) binds: [B:373:0x0789, B:375:0x0799] A[DONT_GENERATE, DONT_INLINE]
      0x07ae: PHI (r4v37 int) = (r4v36 int), (r4v41 int) binds: [B:373:0x0789, B:375:0x0799] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawBackgroundInternal(android.graphics.Canvas r35, boolean r36) {
        /*
            Method dump skipped, instructions count: 2176
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawBackgroundInternal(android.graphics.Canvas, boolean):void");
    }

    private void animateCheckboxTranslation() {
        boolean z = this.checkBoxVisible;
        if (z || this.checkBoxAnimationInProgress) {
            if ((z && this.checkBoxAnimationProgress == 1.0f) || (!z && this.checkBoxAnimationProgress == 0.0f)) {
                this.checkBoxAnimationInProgress = false;
            }
            this.checkBoxTranslation = (int) Math.ceil((z ? CubicBezierInterpolator.EASE_OUT : CubicBezierInterpolator.EASE_IN).getInterpolation(this.checkBoxAnimationProgress) * AndroidUtilities.m1146dp(35.0f));
            if (!this.currentMessageObject.isOutOwner() || this.currentMessageObject.hasWideCode) {
                updateTranslation();
            }
            if (this.checkBoxAnimationInProgress) {
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                long j = jElapsedRealtime - this.lastCheckBoxAnimationTime;
                this.lastCheckBoxAnimationTime = jElapsedRealtime;
                if (this.checkBoxVisible) {
                    float f = this.checkBoxAnimationProgress + (j / 200.0f);
                    this.checkBoxAnimationProgress = f;
                    if (f > 1.0f) {
                        this.checkBoxAnimationProgress = 1.0f;
                    }
                } else {
                    float f2 = this.checkBoxAnimationProgress - (j / 200.0f);
                    this.checkBoxAnimationProgress = f2;
                    if (f2 <= 0.0f) {
                        this.checkBoxAnimationProgress = 0.0f;
                    }
                }
                invalidate();
                ((View) getParent()).invalidate();
            }
        }
    }

    public boolean drawBackgroundInParent() {
        MessageObject messageObject;
        return this.canDrawBackgroundInParent && (messageObject = this.currentMessageObject) != null && messageObject.isOutOwner() && getThemedColor(Theme.key_chat_outBubbleGradient1) != 0;
    }

    public void drawServiceBackground(Canvas canvas, RectF rectF, float f, float f2) {
        applyServiceShaderMatrix();
        if (f2 != 1.0f) {
            int alpha = getThemedPaint("paintChatActionBackground").getAlpha();
            getThemedPaint("paintChatActionBackground").setAlpha((int) (alpha * f2));
            canvas.drawRoundRect(rectF, f, f, getThemedPaint("paintChatActionBackground"));
            getThemedPaint("paintChatActionBackground").setAlpha(alpha);
        } else {
            canvas.drawRoundRect(rectF, f, f, getThemedPaint(this.sideButtonPressed ? "paintChatActionBackgroundSelected" : "paintChatActionBackground"));
        }
        if (hasGradientService()) {
            if (f2 != 1.0f) {
                int alpha2 = Theme.chat_actionBackgroundGradientDarkenPaint.getAlpha();
                Theme.chat_actionBackgroundGradientDarkenPaint.setAlpha((int) (f2 * alpha2));
                canvas.drawRoundRect(rectF, f, f, Theme.chat_actionBackgroundGradientDarkenPaint);
                Theme.chat_actionBackgroundGradientDarkenPaint.setAlpha(alpha2);
                return;
            }
            canvas.drawRoundRect(rectF, f, f, Theme.chat_actionBackgroundGradientDarkenPaint);
        }
    }

    public void drawCommentButton(Canvas canvas, float f) {
        if (this.drawSideButton != 3) {
            return;
        }
        int iM1146dp = AndroidUtilities.m1146dp(32.0f);
        if (this.commentLayout != null) {
            this.sideStartY -= AndroidUtilities.m1146dp(18.0f);
            iM1146dp += AndroidUtilities.m1146dp(18.0f);
        }
        RectF rectF = this.rect;
        float f2 = this.sideStartX;
        rectF.set(f2, this.sideStartY, AndroidUtilities.m1146dp(32.0f) + f2, this.sideStartY + iM1146dp);
        applyServiceShaderMatrix();
        if (f != 1.0f) {
            int alpha = getThemedPaint("paintChatActionBackground").getAlpha();
            getThemedPaint("paintChatActionBackground").setAlpha((int) (alpha * f));
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(16.0f), getThemedPaint("paintChatActionBackground"));
            getThemedPaint("paintChatActionBackground").setAlpha(alpha);
        } else {
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(16.0f), getThemedPaint(this.sideButtonPressed ? "paintChatActionBackgroundSelected" : "paintChatActionBackground"));
        }
        if (hasGradientService()) {
            if (f != 1.0f) {
                int alpha2 = Theme.chat_actionBackgroundGradientDarkenPaint.getAlpha();
                Theme.chat_actionBackgroundGradientDarkenPaint.setAlpha((int) (alpha2 * f));
                canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(16.0f), Theme.chat_actionBackgroundGradientDarkenPaint);
                Theme.chat_actionBackgroundGradientDarkenPaint.setAlpha(alpha2);
            } else {
                canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(16.0f), Theme.chat_actionBackgroundGradientDarkenPaint);
            }
        }
        Drawable themeDrawable = Theme.getThemeDrawable("drawableCommentSticker");
        BaseCell.setDrawableBounds(themeDrawable, this.sideStartX + AndroidUtilities.m1146dp(4.0f), this.sideStartY + AndroidUtilities.m1146dp(4.0f));
        if (f != 1.0f) {
            themeDrawable.setAlpha((int) (f * 255.0f));
            themeDrawable.draw(canvas);
            themeDrawable.setAlpha(255);
        } else {
            themeDrawable.draw(canvas);
        }
        if (this.commentLayout != null) {
            Theme.chat_stickerCommentCountPaint.setColor(getThemedColor(Theme.key_chat_stickerReplyNameText));
            Theme.chat_stickerCommentCountPaint.setAlpha((int) (f * 255.0f));
            if (this.transitionParams.animateComments) {
                if (this.transitionParams.animateCommentsLayout != null) {
                    canvas.save();
                    Theme.chat_stickerCommentCountPaint.setAlpha((int) ((1.0d - this.transitionParams.animateChangeProgress) * 255.0d * f));
                    canvas.translate(this.sideStartX + ((AndroidUtilities.m1146dp(32.0f) - this.transitionParams.animateTotalCommentWidth) / 2), this.sideStartY + AndroidUtilities.m1146dp(30.0f));
                    this.transitionParams.animateCommentsLayout.draw(canvas);
                    canvas.restore();
                }
                Theme.chat_stickerCommentCountPaint.setAlpha((int) (this.transitionParams.animateChangeProgress * 255.0f));
            }
            canvas.save();
            canvas.translate(this.sideStartX + ((AndroidUtilities.m1146dp(32.0f) - this.totalCommentWidth) / 2), this.sideStartY + AndroidUtilities.m1146dp(30.0f));
            this.commentLayout.draw(canvas);
            canvas.restore();
        }
    }

    public void applyServiceShaderMatrix() {
        applyServiceShaderMatrix(getMeasuredWidth(), this.backgroundHeight, getX(), this.viewTop);
    }

    public void applyServiceShaderMatrix(int i, int i2, float f, float f2) {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        if (resourcesProvider != null) {
            resourcesProvider.applyServiceShaderMatrix(i, i2, f, f2 + this.starsPriceTopPadding + this.topicSeparatorTopPadding + this.suggestionOfferTopPadding);
        } else {
            Theme.applyServiceShaderMatrix(i, i2, f, f2 + this.starsPriceTopPadding + this.topicSeparatorTopPadding + this.suggestionOfferTopPadding);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x0055, code lost:
    
        if ((r0 & 1) != 0) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0067, code lost:
    
        if ((r0 & 1) != 0) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0079, code lost:
    
        if ((r0 & 1) != 0) goto L57;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean hasOutboundsContent() {
        /*
            Method dump skipped, instructions count: 263
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.hasOutboundsContent():boolean");
    }

    public void setShowTopic(boolean z) {
        if (this.showTopicSeparator != z) {
            this.showTopicSeparator = z;
            invalidateOutbounds();
            invalidate();
        }
    }

    public int getTopicSeparatorTopPadding() {
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateMonoforumPadding) {
            return AndroidUtilities.lerp(transitionParams.animateMonoforumPaddingFrom, this.topicSeparatorTopPadding, transitionParams.animateChangeProgress);
        }
        return this.topicSeparatorTopPadding;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0187  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawOutboundsContent(android.graphics.Canvas r19) {
        /*
            Method dump skipped, instructions count: 1682
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawOutboundsContent(android.graphics.Canvas):void");
    }

    public void drawAnimatedEmojis(Canvas canvas, float f) {
        drawAnimatedEmojiMessageText(canvas, f);
        if (shouldDrawCaptionLayout()) {
            drawAnimatedEmojiCaption(canvas, f);
        }
    }

    private void drawAnimatedEmojiMessageText(Canvas canvas, float f) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.isSponsored()) {
            return;
        }
        int i = this.textY;
        float f2 = i;
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateText) {
            float f3 = transitionParams.animateFromTextY;
            float f4 = transitionParams.animateChangeProgress;
            f2 = (f3 * (1.0f - f4)) + (i * f4);
        }
        if (transitionParams.animateChangeProgress != 1.0f && transitionParams.animateMessageText) {
            canvas.save();
            Theme.MessageDrawable messageDrawable = this.currentBackgroundDrawable;
            if (messageDrawable != null) {
                Rect bounds = messageDrawable.getBounds();
                if (this.currentMessageObject.isOutOwner() && !this.mediaBackground && !this.pinnedBottom) {
                    canvas.clipRect(bounds.left + AndroidUtilities.m1146dp(4.0f), bounds.top + AndroidUtilities.m1146dp(4.0f), bounds.right - AndroidUtilities.m1146dp(10.0f), bounds.bottom - AndroidUtilities.m1146dp(4.0f));
                } else {
                    canvas.clipRect(bounds.left + AndroidUtilities.m1146dp(4.0f), bounds.top + AndroidUtilities.m1146dp(4.0f), bounds.right - AndroidUtilities.m1146dp(4.0f), bounds.bottom - AndroidUtilities.m1146dp(4.0f));
                }
            }
            drawAnimatedEmojiMessageText(this.textX, f2, canvas, this.transitionParams.animateOutTextBlocks, this.transitionParams.animateOutAnimateEmoji, false, f * (1.0f - this.transitionParams.animateChangeProgress), this.currentMessageObject.textXOffset, false);
            float f5 = this.textX;
            MessageObject messageObject2 = this.currentMessageObject;
            drawAnimatedEmojiMessageText(f5, f2, canvas, messageObject2.textLayoutBlocks, this.animatedEmojiStack, true, f * this.transitionParams.animateChangeProgress, messageObject2.textXOffset, false);
            canvas.restore();
            return;
        }
        float f6 = this.textX;
        MessageObject messageObject3 = this.currentMessageObject;
        drawAnimatedEmojiMessageText(f6, f2, canvas, messageObject3.textLayoutBlocks, this.animatedEmojiStack, true, f, messageObject3.textXOffset, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawAnimatedEmojiMessageText(float r24, float r25, android.graphics.Canvas r26, java.util.ArrayList r27, org.telegram.ui.Components.AnimatedEmojiSpan.EmojiGroupedSpans r28, boolean r29, float r30, float r31, boolean r32) {
        /*
            Method dump skipped, instructions count: 615
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawAnimatedEmojiMessageText(float, float, android.graphics.Canvas, java.util.ArrayList, org.telegram.ui.Components.AnimatedEmojiSpan$EmojiGroupedSpans, boolean, float, float, boolean):void");
    }

    public void drawAnimatedEmojiCaption(Canvas canvas, float f) {
        float f2;
        float f3;
        float f4;
        if (this.captionLayout == null) {
            return;
        }
        float translationY = this.captionY;
        float f5 = this.captionX;
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateBackgroundBoundsInner) {
            if (transitionParams.transformGroupToSingleMessage) {
                translationY -= getTranslationY();
                f4 = this.transitionParams.deltaLeft;
            } else if (transitionParams.moveCaption) {
                float f6 = this.captionX;
                TransitionParams transitionParams2 = this.transitionParams;
                float f7 = transitionParams2.animateChangeProgress;
                f3 = (f6 * f7) + (transitionParams2.captionFromX * (1.0f - f7));
                f2 = (transitionParams2.captionFromY * (1.0f - f7)) + (this.captionY * f7);
            } else {
                if (!this.currentMessageObject.isVoice() || !TextUtils.isEmpty(this.currentMessageObject.caption)) {
                    f4 = this.transitionParams.deltaLeft;
                }
                float f8 = f5;
                f2 = translationY;
                f3 = f8;
            }
            f5 += f4;
            float f82 = f5;
            f2 = translationY;
            f3 = f82;
        } else {
            float f822 = f5;
            f2 = translationY;
            f3 = f822;
        }
        TransitionParams transitionParams3 = this.transitionParams;
        if (transitionParams3.animateReplaceCaptionLayout && transitionParams3.animateChangeProgress != 1.0f) {
            ArrayList<MessageObject.TextLayoutBlock> arrayList = transitionParams3.animateOutCaptionLayout != null ? this.transitionParams.animateOutCaptionLayout.textLayoutBlocks : null;
            AnimatedEmojiSpan.EmojiGroupedSpans emojiGroupedSpans = this.transitionParams.animateOutAnimateEmoji;
            TransitionParams transitionParams4 = this.transitionParams;
            drawAnimatedEmojiMessageText(f3, f2, canvas, arrayList, emojiGroupedSpans, false, f * (1.0f - transitionParams4.animateChangeProgress), transitionParams4.animateOutCaptionLayout != null ? this.transitionParams.animateOutCaptionLayout.textXOffset : 0.0f, true);
            MessageObject.TextLayoutBlocks textLayoutBlocks = this.captionLayout;
            drawAnimatedEmojiMessageText(f3, f2, canvas, textLayoutBlocks != null ? textLayoutBlocks.textLayoutBlocks : null, this.animatedEmojiStack, true, f * this.transitionParams.animateChangeProgress, textLayoutBlocks != null ? textLayoutBlocks.textXOffset : 0.0f, true);
            return;
        }
        MessageObject.TextLayoutBlocks textLayoutBlocks2 = this.captionLayout;
        drawAnimatedEmojiMessageText(f3, f2, canvas, textLayoutBlocks2 != null ? textLayoutBlocks2.textLayoutBlocks : null, this.animatedEmojiStack, true, f, textLayoutBlocks2 != null ? textLayoutBlocks2.textXOffset : 0.0f, true);
    }

    public void setHideSideButtonByQuickShare(boolean z) {
        if (this.hideSideButtonByQuickShare != z) {
            this.hideSideButtonByQuickShare = z;
            boolean z2 = this.invalidatesParent;
            this.invalidatesParent = true;
            invalidate();
            this.invalidatesParent = z2;
        }
    }

    public void drawSideButton(Canvas canvas) {
        drawSideButton(canvas, false);
    }

    public void drawSideButton(Canvas canvas, boolean z) {
        float f;
        int iSaveLayerAlpha;
        float f2;
        float f3;
        MessageObject.GroupedMessages groupedMessages;
        if ((!this.hideSideButtonByQuickShare || z) && this.drawSideButton != 0) {
            MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
            if (groupedMessagePosition == null || (groupedMessages = this.currentMessagesGroup) == null || !groupedMessages.isDocuments || groupedMessagePosition.last) {
                if (this.currentMessageObject.isOutOwner()) {
                    float fM1146dp = this.transitionParams.lastBackgroundLeft - AndroidUtilities.m1146dp(40.0f);
                    this.sideStartX = fM1146dp;
                    MessageObject.GroupedMessages groupedMessages2 = this.currentMessagesGroup;
                    if (groupedMessages2 != null) {
                        this.sideStartX = fM1146dp + (groupedMessages2.transitionParams.offsetLeft - this.animationOffsetX);
                    }
                } else {
                    float fM1146dp2 = this.transitionParams.lastBackgroundRight + AndroidUtilities.m1146dp(8.0f);
                    this.sideStartX = fM1146dp2;
                    MessageObject.GroupedMessages groupedMessages3 = this.currentMessagesGroup;
                    if (groupedMessages3 != null) {
                        this.sideStartX = fM1146dp2 + (groupedMessages3.transitionParams.offsetRight - this.animationOffsetX);
                    }
                }
                if (this.drawSideButton == 4) {
                    this.sideStartY = AndroidUtilities.m1146dp(6.0f);
                } else {
                    float fM1146dp3 = (this.layoutHeight + this.transitionParams.deltaBottom) - AndroidUtilities.m1146dp(41.0f);
                    this.sideStartY = fM1146dp3;
                    MessageObject messageObject = this.currentMessageObject;
                    if (messageObject.type == 19 && messageObject.textWidth < this.timeTextWidth) {
                        this.sideStartY = fM1146dp3 - AndroidUtilities.m1146dp(22.0f);
                    }
                    MessageObject.GroupedMessages groupedMessages4 = this.currentMessagesGroup;
                    if (groupedMessages4 != null) {
                        float f4 = this.sideStartY;
                        MessageObject.GroupedMessages.TransitionParams transitionParams = groupedMessages4.transitionParams;
                        float f5 = f4 + transitionParams.offsetBottom;
                        this.sideStartY = f5;
                        if (transitionParams.backgroundChangeBounds) {
                            this.sideStartY = f5 - getTranslationY();
                        }
                    }
                    if (this.currentMessageObject.shouldDrawReactions()) {
                        ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
                        if (!reactionsLayoutInBubble.isSmall) {
                            if (this.isRoundVideo) {
                                this.sideStartY -= reactionsLayoutInBubble.getCurrentTotalHeight(this.transitionParams.animateChangeProgress) * (1.0f - getVideoTranscriptionProgress());
                            } else if (reactionsLayoutInBubble.drawServiceShaderBackground > 0.0f) {
                                this.sideStartY -= reactionsLayoutInBubble.getCurrentTotalHeight(this.transitionParams.animateChangeProgress);
                            }
                        }
                    }
                }
                if (this.drawSideButton != 4) {
                    float fM1146dp4 = ((this.layoutHeight + this.transitionParams.deltaBottom) - AndroidUtilities.m1146dp(32.0f)) / 2.0f;
                    if (this.sideStartY < fM1146dp4) {
                        this.sideStartY = fM1146dp4;
                    }
                }
                if (this.currentMessageObject.type == 19) {
                    if (this.drawSideButton == 3 && this.commentLayout != null) {
                        this.sideStartY = AndroidUtilities.m1146dp(18.0f);
                    } else {
                        this.sideStartY = 0.0f;
                    }
                }
                if (!this.currentMessageObject.isOutOwner() && this.isRoundVideo && !this.hasLinkPreview) {
                    float fRoundPlayingMessageSize = this.isAvatarVisible ? (AndroidUtilities.roundPlayingMessageSize(this.isSideMenued) - AndroidUtilities.roundMessageSize) * 0.7f : AndroidUtilities.m1146dp(50.0f);
                    float videoTranscriptionProgress = this.isPlayingRound ? (1.0f - getVideoTranscriptionProgress()) * fRoundPlayingMessageSize : 0.0f;
                    float fM1146dp5 = this.isPlayingRound ? AndroidUtilities.m1146dp(28.0f) * (1.0f - getVideoTranscriptionProgress()) : 0.0f;
                    TransitionParams transitionParams2 = this.transitionParams;
                    if (transitionParams2.animatePlayingRound) {
                        videoTranscriptionProgress = (this.isPlayingRound ? transitionParams2.animateChangeProgress : 1.0f - transitionParams2.animateChangeProgress) * (1.0f - getVideoTranscriptionProgress()) * fRoundPlayingMessageSize;
                        fM1146dp5 = AndroidUtilities.m1146dp(28.0f) * (this.isPlayingRound ? this.transitionParams.animateChangeProgress : 1.0f - this.transitionParams.animateChangeProgress) * (1.0f - getVideoTranscriptionProgress());
                    }
                    this.sideStartX -= videoTranscriptionProgress;
                    this.sideStartY -= fM1146dp5;
                }
                this.sideButtonVisible = true;
                if (this.drawSideButton == 3) {
                    if (!this.enterTransitionInProgress || this.currentMessageObject.isVoice()) {
                        drawCommentButton(canvas, 1.0f);
                        return;
                    }
                    return;
                }
                if (SizeNotifierFrameLayout.drawingBlur) {
                    return;
                }
                RectF rectF = this.rect;
                float f6 = this.sideStartX;
                rectF.set(f6, this.sideStartY, AndroidUtilities.m1146dp(32.0f) + f6, this.sideStartY + AndroidUtilities.m1146dp(this.drawSideButton2 == 5 ? 64.0f : 32.0f));
                if (this.rect.right >= getMeasuredWidth()) {
                    this.sideButtonVisible = false;
                    return;
                }
                int floatValue = (int) ((1.0f - this.isSponsoredMessageHidden.getFloatValue()) * 255.0f);
                if (floatValue != 255) {
                    float f7 = this.sideStartX;
                    f = 2.0f;
                    iSaveLayerAlpha = canvas.saveLayerAlpha(f7, this.sideStartY, AndroidUtilities.m1146dp(32.0f) + f7, this.sideStartY + AndroidUtilities.m1146dp(64.0f), floatValue);
                } else {
                    f = 2.0f;
                    iSaveLayerAlpha = -1;
                }
                applyServiceShaderMatrix();
                if (this.drawSideButton == 4 && this.drawSideButton2 == 5 && this.sideButtonPressed) {
                    Path path = this.sideButtonPath1;
                    if (path == null) {
                        this.sideButtonPath1 = new Path();
                    } else {
                        path.rewind();
                    }
                    Path path2 = this.sideButtonPath2;
                    if (path2 == null) {
                        this.sideButtonPath2 = new Path();
                    } else {
                        path2.rewind();
                    }
                    f2 = 16.0f;
                    if (this.sideButtonPathCorners1 == null) {
                        this.sideButtonPathCorners1 = new float[]{fM1146dp, fM1146dp, fM1146dp, fM1146dp, 0.0f, 0.0f, 0.0f, 0.0f};
                        f3 = 32.0f;
                        float fM1146dp6 = AndroidUtilities.m1146dp(16.0f);
                    } else {
                        f3 = 32.0f;
                    }
                    if (this.sideButtonPathCorners2 == null) {
                        this.sideButtonPathCorners2 = new float[]{0.0f, 0.0f, 0.0f, 0.0f, fM1146dp, fM1146dp, fM1146dp, fM1146dp};
                        float fM1146dp7 = AndroidUtilities.m1146dp(16.0f);
                    }
                    RectF rectF2 = AndroidUtilities.rectTmp;
                    float f8 = this.sideStartX;
                    rectF2.set(f8, this.sideStartY, AndroidUtilities.m1146dp(f3) + f8, this.sideStartY + AndroidUtilities.m1146dp(f3));
                    Path path3 = this.sideButtonPath1;
                    float[] fArr = this.sideButtonPathCorners1;
                    Path.Direction direction = Path.Direction.CW;
                    path3.addRoundRect(rectF2, fArr, direction);
                    rectF2.set(this.sideStartX, this.sideStartY + AndroidUtilities.m1146dp(f3), this.sideStartX + AndroidUtilities.m1146dp(f3), this.sideStartY + AndroidUtilities.m1146dp(64.0f));
                    this.sideButtonPath2.addRoundRect(rectF2, this.sideButtonPathCorners2, direction);
                    if (this.pressedSideButton == 4) {
                        canvas.drawPath(this.sideButtonPath1, getThemedPaint("paintChatActionBackgroundSelected"));
                        canvas.drawPath(this.sideButtonPath2, getThemedPaint("paintChatActionBackground"));
                    } else {
                        canvas.drawPath(this.sideButtonPath1, getThemedPaint("paintChatActionBackground"));
                        canvas.drawPath(this.sideButtonPath2, getThemedPaint("paintChatActionBackgroundSelected"));
                    }
                } else {
                    f2 = 16.0f;
                    canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(16.0f), getThemedPaint(this.sideButtonPressed ? "paintChatActionBackgroundSelected" : "paintChatActionBackground"));
                }
                if (hasGradientService()) {
                    canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(f2), AndroidUtilities.m1146dp(f2), Theme.chat_actionBackgroundGradientDarkenPaint);
                }
                int i = this.drawSideButton;
                if (i == 2) {
                    Drawable themedDrawable = getThemedDrawable("drawableGoIcon");
                    BaseCell.setDrawableBounds(themedDrawable, (this.sideStartX + AndroidUtilities.m1146dp(f2)) - (themedDrawable.getIntrinsicWidth() / f), (this.sideStartY + AndroidUtilities.m1146dp(f2)) - (themedDrawable.getIntrinsicHeight() / f));
                    themedDrawable.draw(canvas);
                } else if (i == 4) {
                    int iM1146dp = (int) (this.sideStartX + AndroidUtilities.m1146dp(f2));
                    int iM1146dp2 = (int) (this.sideStartY + AndroidUtilities.m1146dp(f2));
                    Drawable themedDrawable2 = getThemedDrawable("drawableCloseIcon");
                    int intrinsicWidth = themedDrawable2.getIntrinsicWidth() / 2;
                    int intrinsicHeight = themedDrawable2.getIntrinsicHeight() / 2;
                    themedDrawable2.setBounds(iM1146dp - intrinsicWidth, iM1146dp2 - intrinsicHeight, intrinsicWidth + iM1146dp, intrinsicHeight + iM1146dp2);
                    BaseCell.setDrawableBounds(themedDrawable2, this.sideStartX + AndroidUtilities.m1146dp(4.0f), this.sideStartY + AndroidUtilities.m1146dp(4.0f));
                    canvas.save();
                    canvas.scale(0.65f, 0.65f, themedDrawable2.getBounds().centerX(), themedDrawable2.getBounds().centerY());
                    themedDrawable2.draw(canvas);
                    canvas.restore();
                    if (this.drawSideButton2 == 5) {
                        Drawable themedDrawable3 = getThemedDrawable("drawableMoreIcon");
                        int intrinsicWidth2 = themedDrawable3.getIntrinsicWidth() / 2;
                        int intrinsicHeight2 = themedDrawable3.getIntrinsicHeight() / 2;
                        themedDrawable3.setBounds(iM1146dp - intrinsicWidth2, iM1146dp2 - intrinsicHeight2, iM1146dp + intrinsicWidth2, iM1146dp2 + intrinsicHeight2);
                        BaseCell.setDrawableBounds(themedDrawable3, this.sideStartX + AndroidUtilities.m1146dp(4.0f), this.sideStartY + AndroidUtilities.m1146dp(34.0f));
                        themedDrawable3.draw(canvas);
                    }
                } else {
                    int iM1146dp3 = (int) (this.sideStartX + AndroidUtilities.m1146dp(f2));
                    int iM1146dp4 = (int) (this.sideStartY + AndroidUtilities.m1146dp(f2));
                    Drawable themedDrawable4 = getThemedDrawable("drawableShareIcon");
                    int intrinsicWidth3 = themedDrawable4.getIntrinsicWidth() / 2;
                    int intrinsicHeight3 = themedDrawable4.getIntrinsicHeight() / 2;
                    themedDrawable4.setBounds(iM1146dp3 - intrinsicWidth3, iM1146dp4 - intrinsicHeight3, iM1146dp3 + intrinsicWidth3, iM1146dp4 + intrinsicHeight3);
                    BaseCell.setDrawableBounds(themedDrawable4, this.sideStartX + AndroidUtilities.m1146dp(4.0f), this.sideStartY + AndroidUtilities.m1146dp(4.0f));
                    themedDrawable4.draw(canvas);
                }
                if (iSaveLayerAlpha != -1) {
                    canvas.restoreToCount(iSaveLayerAlpha);
                }
            }
        }
    }

    public float getSideButtonStartX() {
        return this.sideStartX;
    }

    public float getSideButtonStartY() {
        return this.sideStartY;
    }

    public void setTimeAlpha(float f) {
        this.timeAlpha = f;
    }

    public float getTimeAlpha() {
        return this.timeAlpha;
    }

    private boolean isSideMenuPossibleLeftMargin() {
        MessageObject messageObject;
        return this.isSideMenued && (messageObject = this.currentMessageObject) != null && !messageObject.isOutOwner() && this.currentPosition == null;
    }

    private boolean isSideMenuLeftMargin() {
        MessageObject messageObject;
        return this.isSideMenuEnabled && (messageObject = this.currentMessageObject) != null && !messageObject.isOutOwner() && this.currentPosition == null;
    }

    public int getBackgroundDrawableLeft() {
        int iM1146dp;
        int iM1146dp2;
        MessageObject.GroupedMessages groupedMessages;
        int i;
        MessageObject messageObject = getMessageObject();
        if (messageObject != null && messageObject.isOutOwner()) {
            if (this.isRoundVideo) {
                return (this.layoutWidth - this.backgroundWidth) - ((int) ((1.0f - getVideoTranscriptionProgress()) * AndroidUtilities.m1146dp(9.0f)));
            }
            return (this.layoutWidth - this.backgroundWidth) - (this.mediaBackground ? AndroidUtilities.m1146dp(9.0f) : 0);
        }
        float f = 71.0f;
        if (this.isRoundVideo) {
            if (!isSideMenuLeftMargin()) {
                if ((this.isChat || ((messageObject != null && (messageObject.isRepostPreview || messageObject.forceAvatar)) || messageObject.getDialogId() == UserObject.VERIFY)) && this.isAvatarVisible) {
                    i = 48;
                }
                f = i + 3;
            }
            iM1146dp = AndroidUtilities.m1146dp(f);
            iM1146dp2 = (int) (AndroidUtilities.m1146dp(6.0f) * (1.0f - getVideoTranscriptionProgress()));
        } else {
            if (!isSideMenuLeftMargin()) {
                if ((this.isChat || ((messageObject != null && (messageObject.isRepostPreview || messageObject.forceAvatar)) || messageObject.getDialogId() == UserObject.VERIFY)) && this.isAvatarVisible) {
                    i = 48;
                }
                f = i;
            }
            iM1146dp = AndroidUtilities.m1146dp(f);
            iM1146dp2 = AndroidUtilities.m1146dp(this.mediaBackground ? 9.0f : 3.0f);
        }
        int iCeil = iM1146dp + iM1146dp2;
        MessageObject.GroupedMessages groupedMessages2 = this.currentMessagesGroup;
        if (groupedMessages2 != null && !groupedMessages2.isDocuments && (i = this.currentPosition.leftSpanOffset) != 0) {
            iCeil += (int) Math.ceil((i / 1000.0f) * getGroupPhotosWidth());
        }
        return this.isRoundVideo ? this.drawPinnedBottom ? iCeil + ((int) (AndroidUtilities.m1146dp(6.0f) * (1.0f - getVideoTranscriptionProgress()))) : iCeil : !this.mediaBackground ? (this.drawPinnedBottom || (ExteraConfig.removeMessageTail && (groupedMessages = this.currentMessagesGroup) != null && groupedMessages.isDocuments)) ? iCeil + AndroidUtilities.m1146dp(6.0f) : iCeil : iCeil;
    }

    public int getBackgroundDrawableRight() {
        int iM1146dp;
        int backgroundDrawableLeft;
        MessageObject.GroupedMessages groupedMessages;
        MessageObject messageObject;
        MessageObject messageObject2;
        int i = this.backgroundWidth;
        if (this.isRoundVideo) {
            iM1146dp = i - ((int) (getVideoTranscriptionProgress() * AndroidUtilities.m1146dp(3.0f)));
            if (this.drawPinnedBottom && (messageObject2 = this.currentMessageObject) != null && messageObject2.isOutOwner()) {
                iM1146dp = (int) (iM1146dp - (AndroidUtilities.m1146dp(6.0f) * (1.0f - getVideoTranscriptionProgress())));
            }
            if (this.drawPinnedBottom && ((messageObject = this.currentMessageObject) == null || !messageObject.isOutOwner())) {
                iM1146dp = (int) (iM1146dp - (AndroidUtilities.m1146dp(6.0f) * (1.0f - getVideoTranscriptionProgress())));
            }
            backgroundDrawableLeft = getBackgroundDrawableLeft();
        } else {
            iM1146dp = i - (this.mediaBackground ? 0 : AndroidUtilities.m1146dp(3.0f));
            if (!this.mediaBackground && (this.drawPinnedBottom || (ExteraConfig.removeMessageTail && (groupedMessages = this.currentMessagesGroup) != null && groupedMessages.isDocuments))) {
                iM1146dp -= AndroidUtilities.m1146dp(6.0f);
            }
            backgroundDrawableLeft = getBackgroundDrawableLeft();
        }
        return backgroundDrawableLeft + iM1146dp;
    }

    public int getBackgroundDrawableTop() {
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        int iM1146dp = ((groupedMessagePosition == null || (groupedMessagePosition.flags & 4) != 0) ? 0 : 0 - AndroidUtilities.m1146dp(3.0f)) + (this.drawPinnedTop ? 0 : AndroidUtilities.m1146dp(1.0f));
        return (this.mediaBackground || !this.drawPinnedTop) ? iM1146dp : iM1146dp - AndroidUtilities.m1146dp(1.0f);
    }

    public int getBackgroundDrawableBottom() {
        int iM1146dp;
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        int iM1146dp2 = 0;
        if (groupedMessagePosition != null) {
            int i = 4;
            iM1146dp = (groupedMessagePosition.flags & 4) == 0 ? AndroidUtilities.m1146dp(3.0f) : 0;
            if ((this.currentPosition.flags & 8) == 0) {
                MessageObject messageObject = this.currentMessageObject;
                if (messageObject != null && messageObject.isOutOwner()) {
                    i = 3;
                }
                iM1146dp += AndroidUtilities.m1146dp(i);
            }
        } else {
            iM1146dp = 0;
        }
        boolean z = this.drawPinnedBottom;
        if (!z || !this.drawPinnedTop) {
            if (z) {
                iM1146dp2 = (!ExteraConfig.removeMessageTail || this.pinnedBottom) ? AndroidUtilities.m1146dp(1.0f) : AndroidUtilities.m1146dp(2.0f);
            } else {
                iM1146dp2 = AndroidUtilities.m1146dp(2.0f);
            }
        }
        int backgroundDrawableTop = ((getBackgroundDrawableTop() + this.layoutHeight) - iM1146dp2) + iM1146dp;
        if (this.mediaBackground) {
            return backgroundDrawableTop;
        }
        if (this.drawPinnedTop) {
            backgroundDrawableTop += AndroidUtilities.m1146dp(1.0f);
        }
        return this.drawPinnedBottom ? backgroundDrawableTop + AndroidUtilities.m1146dp(1.0f) : backgroundDrawableTop;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0088 A[PHI: r6 r7
      0x0088: PHI (r6v13 int) = (r6v12 int), (r6v20 int) binds: [B:29:0x0065, B:31:0x0075] A[DONT_GENERATE, DONT_INLINE]
      0x0088: PHI (r7v1 int) = (r7v0 int), (r7v5 int) binds: [B:29:0x0065, B:31:0x0075] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawBackground(android.graphics.Canvas r18, int r19, int r20, int r21, int r22, boolean r23, boolean r24, boolean r25, int r26) {
        /*
            Method dump skipped, instructions count: 231
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawBackground(android.graphics.Canvas, int, int, int, int, boolean, boolean, boolean, int):void");
    }

    private boolean hasInlineBotButtons() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || !messageObject.hasInlineBotButtons()) {
            return this.lastInChatList && this.isAllChats && this.isBotForum && !this.isPinnedChat;
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean hasNameLayout() {
        /*
            r4 = this;
            boolean r0 = r4.drawNameLayout
            r1 = 1
            if (r0 == 0) goto L9
            android.text.StaticLayout r0 = r4.nameLayout
            if (r0 != 0) goto L28
        L9:
            boolean r0 = r4.drawForwardedName
            r2 = 0
            if (r0 == 0) goto L24
            android.text.StaticLayout[] r0 = r4.forwardedNameLayout
            r3 = r0[r2]
            if (r3 == 0) goto L24
            r0 = r0[r1]
            if (r0 == 0) goto L24
            org.telegram.messenger.MessageObject$GroupedMessagePosition r0 = r4.currentPosition
            if (r0 == 0) goto L28
            byte r3 = r0.minY
            if (r3 != 0) goto L24
            byte r0 = r0.minX
            if (r0 == 0) goto L28
        L24:
            android.text.StaticLayout r0 = r4.replyNameLayout
            if (r0 == 0) goto L29
        L28:
            return r1
        L29:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.hasNameLayout():boolean");
    }

    public boolean isDrawNameLayout() {
        return this.drawNameLayout && this.nameLayout != null;
    }

    public boolean isAdminLayoutChanged() {
        return !TextUtils.equals(this.lastPostAuthor, this.currentMessageObject.messageOwner.post_author);
    }

    /* JADX WARN: Removed duplicated region for block: B:349:0x0877  */
    /* JADX WARN: Removed duplicated region for block: B:502:0x0c54  */
    /* JADX WARN: Removed duplicated region for block: B:505:0x0c58  */
    /* JADX WARN: Removed duplicated region for block: B:506:0x0c66  */
    /* JADX WARN: Removed duplicated region for block: B:550:0x0d53  */
    /* JADX WARN: Removed duplicated region for block: B:556:0x0d6a  */
    /* JADX WARN: Removed duplicated region for block: B:559:0x0d6f  */
    /* JADX WARN: Removed duplicated region for block: B:570:0x0dca  */
    /* JADX WARN: Removed duplicated region for block: B:580:0x0e29  */
    /* JADX WARN: Removed duplicated region for block: B:583:0x0e30  */
    /* JADX WARN: Removed duplicated region for block: B:592:0x0e6d  */
    /* JADX WARN: Removed duplicated region for block: B:605:0x0ef1  */
    /* JADX WARN: Removed duplicated region for block: B:686:0x1171  */
    /* JADX WARN: Removed duplicated region for block: B:711:0x121f  */
    /* JADX WARN: Removed duplicated region for block: B:730:0x12a9  */
    /* JADX WARN: Removed duplicated region for block: B:777:0x1388  */
    /* JADX WARN: Removed duplicated region for block: B:807:0x1432  */
    /* JADX WARN: Removed duplicated region for block: B:815:0x145e  */
    /* JADX WARN: Removed duplicated region for block: B:823:0x147d  */
    /* JADX WARN: Removed duplicated region for block: B:831:0x1498  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawNamesLayout(android.graphics.Canvas r45, float r46) {
        /*
            Method dump skipped, instructions count: 6675
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawNamesLayout(android.graphics.Canvas, float):void");
    }

    public boolean hasCaptionLayout() {
        return this.captionLayout != null;
    }

    public boolean hasCommentLayout() {
        return this.drawCommentButton;
    }

    public MessageObject.TextLayoutBlocks getCaptionLayout() {
        return this.captionLayout;
    }

    public void setDrawSelectionBackground(boolean z) {
        if (this.drawSelectionBackground != z) {
            this.drawSelectionBackground = z;
            invalidate();
        }
    }

    public boolean isDrawingSelectionBackground() {
        return this.drawSelectionBackground || this.isHighlightedAnimated || this.isHighlighted;
    }

    public float getHighlightAlpha() {
        return getHighlightAlpha(false);
    }

    public float getHighlightAlpha(boolean z) {
        float f;
        QuoteHighlight quoteHighlight;
        if (this.drawSelectionBackground || !this.isHighlightedAnimated) {
            f = 1.0f;
        } else {
            int i = this.highlightProgress;
            f = (i >= 300 ? 1.0f : i / 300.0f) * 1.0f;
        }
        return (z || (quoteHighlight = this.quoteHighlight) == null) ? f : f * (1.0f - quoteHighlight.getT());
    }

    public void setCheckBoxVisible(boolean z, boolean z2) {
        MessageObject.GroupedMessages groupedMessages;
        MessageObject.GroupedMessages groupedMessages2;
        MessageObject messageObject;
        if (z2 && (messageObject = this.currentMessageObject) != null && messageObject.deletedByThanos) {
            return;
        }
        if (z) {
            this.quoteHighlight = null;
            CheckBoxBase checkBoxBase = this.checkBox;
            if (checkBoxBase == null) {
                CheckBoxBase checkBoxBase2 = new CheckBoxBase(this, 21, this.resourcesProvider);
                this.checkBox = checkBoxBase2;
                if (this.attachedToWindow) {
                    checkBoxBase2.onAttachedToWindow();
                }
            } else {
                checkBoxBase.setResourcesProvider(this.resourcesProvider);
            }
        }
        if (z && (((groupedMessages = this.currentMessagesGroup) != null && groupedMessages.messages.size() > 1) || ((groupedMessages2 = this.groupedMessagesToSet) != null && groupedMessages2.messages.size() > 1))) {
            CheckBoxBase checkBoxBase3 = this.mediaCheckBox;
            if (checkBoxBase3 == null) {
                CheckBoxBase checkBoxBase4 = new CheckBoxBase(this, 21, this.resourcesProvider);
                this.mediaCheckBox = checkBoxBase4;
                checkBoxBase4.setUseDefaultCheck(true);
                if (this.attachedToWindow) {
                    this.mediaCheckBox.onAttachedToWindow();
                }
            } else {
                checkBoxBase3.setResourcesProvider(this.resourcesProvider);
            }
        }
        if (this.checkBoxVisible == z) {
            if (z2 == this.checkBoxAnimationInProgress || z2) {
                return;
            }
            this.checkBoxAnimationProgress = z ? 1.0f : 0.0f;
            invalidate();
            return;
        }
        this.checkBoxAnimationInProgress = z2;
        this.checkBoxVisible = z;
        if (z2) {
            this.lastCheckBoxAnimationTime = SystemClock.elapsedRealtime();
        } else {
            this.checkBoxAnimationProgress = z ? 1.0f : 0.0f;
        }
        invalidate();
    }

    public boolean isCheckBoxVisible() {
        return this.checkBoxVisible || this.checkBoxAnimationInProgress;
    }

    public void setChecked(boolean z, boolean z2, boolean z3) {
        MessageObject messageObject;
        if (z || !z3 || (messageObject = this.currentMessageObject) == null || !messageObject.deletedByThanos) {
            CheckBoxBase checkBoxBase = this.checkBox;
            if (checkBoxBase != null) {
                checkBoxBase.setChecked(z2, z3);
            }
            CheckBoxBase checkBoxBase2 = this.mediaCheckBox;
            if (checkBoxBase2 != null) {
                checkBoxBase2.setChecked(z, z3);
            }
            this.backgroundDrawable.setSelected(z2, z3);
        }
    }

    public void setLastTouchCoords(float f, float f2) {
        this.lastTouchX = f;
        this.lastTouchY = f2;
        this.backgroundDrawable.setTouchCoords(f + getTranslationX(), this.lastTouchY);
    }

    public MessageBackgroundDrawable getBackgroundDrawable() {
        return this.backgroundDrawable;
    }

    public Theme.MessageDrawable getCurrentBackgroundDrawable(boolean z) {
        if (z) {
            MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
            boolean z2 = groupedMessagePosition != null && (groupedMessagePosition.flags & 8) == 0 && this.currentMessagesGroup.isDocuments && !this.drawPinnedBottom;
            if (this.currentMessageObject.isOutOwner()) {
                if (!this.mediaBackground && !this.drawPinnedBottom && !z2) {
                    this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgOut");
                } else {
                    this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgOutMedia");
                }
            } else if (!this.mediaBackground && !this.drawPinnedBottom && !z2) {
                this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgIn");
            } else {
                this.currentBackgroundDrawable = (Theme.MessageDrawable) getThemedDrawable("drawableMsgInMedia");
            }
        }
        this.currentBackgroundDrawable.getBackgroundDrawable();
        return this.currentBackgroundDrawable;
    }

    private boolean shouldDrawCaptionLayout() {
        MessageObject.GroupedMessages groupedMessages;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.preview || messageObject.isSponsored()) {
            return false;
        }
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if ((groupedMessagePosition == null || ((groupedMessages = this.currentMessagesGroup) != null && groupedMessages.isDocuments && (groupedMessagePosition.flags & 8) == 0)) && !this.transitionParams.animateBackgroundBoundsInner) {
            return (this.enterTransitionInProgress && this.currentMessageObject.isVoice()) ? false : true;
        }
        return false;
    }

    public void drawCaptionLayout(Canvas canvas, boolean z, float f) {
        if (this.animatedEmojiStack != null && !(canvas instanceof SizeNotifierFrameLayout.SimplerCanvas) && (this.captionLayout != null || this.transitionParams.animateOutCaptionLayout != null)) {
            this.animatedEmojiStack.clearPositions();
        }
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateReplaceCaptionLayout && transitionParams.animateChangeProgress != 1.0f) {
            drawCaptionLayout(canvas, transitionParams.animateOutCaptionLayout, false, z, f * (1.0f - this.transitionParams.animateChangeProgress));
            drawCaptionLayout(canvas, this.captionLayout, true, z, f * this.transitionParams.animateChangeProgress);
        } else {
            drawCaptionLayout(canvas, this.captionLayout, true, z, f);
        }
        if (!z) {
            drawAnimatedEmojiCaption(canvas, f);
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.messageOwner == null || !messageObject.isVoiceTranscriptionOpen()) {
            return;
        }
        MessageObject messageObject2 = this.currentMessageObject;
        if (messageObject2.messageOwner.voiceTranscriptionFinal || !TranscribeButton.isTranscribing(messageObject2)) {
            return;
        }
        invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:182:0x03d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawCommentLayout(android.graphics.Canvas r31, float r32) {
        /*
            Method dump skipped, instructions count: 2037
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawCommentLayout(android.graphics.Canvas, float):void");
    }

    public boolean hasReactionsToDraw() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || !messageObject.shouldDrawReactions()) {
            return false;
        }
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if (groupedMessagePosition != null) {
            int i = groupedMessagePosition.flags;
            if ((i & 8) == 0 || (i & 1) == 0) {
                return false;
            }
        }
        return !this.reactionsLayoutInBubble.isSmall;
    }

    public void drawReactionsLayout(Canvas canvas, float f, Integer num) {
        boolean z;
        if (this.isRoundVideo) {
            this.reactionsLayoutInBubble.drawServiceShaderBackground = 1.0f - getVideoTranscriptionProgress();
        }
        if (this.reactionsVisible && hasReactionsToDraw()) {
            if (this.reactionsLayoutInBubble.drawServiceShaderBackground > 0.0f) {
                applyServiceShaderMatrix();
            }
            if (getAlpha() * f != 1.0f) {
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(0.0f, 0.0f, getWidth(), getHeight());
                canvas.saveLayerAlpha(rectF, (int) (f * 255.0f * getAlpha()), 31);
                z = true;
            } else {
                z = false;
            }
            ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
            if (reactionsLayoutInBubble.drawServiceShaderBackground > 0.0f || !this.transitionParams.animateBackgroundBoundsInner || this.currentPosition != null || this.isRoundVideo) {
                reactionsLayoutInBubble.setScrimProgress(0.0f, false);
                ReactionsLayoutInBubble reactionsLayoutInBubble2 = this.reactionsLayoutInBubble;
                TransitionParams transitionParams = this.transitionParams;
                reactionsLayoutInBubble2.draw(canvas, transitionParams.animateChange ? transitionParams.animateChangeProgress : 1.0f, num);
            } else {
                canvas.save();
                canvas.clipRect(0.0f, 0.0f, getMeasuredWidth(), getBackgroundDrawableBottom() + this.transitionParams.deltaBottom);
                this.reactionsLayoutInBubble.setScrimProgress(0.0f, false);
                ReactionsLayoutInBubble reactionsLayoutInBubble3 = this.reactionsLayoutInBubble;
                TransitionParams transitionParams2 = this.transitionParams;
                reactionsLayoutInBubble3.draw(canvas, transitionParams2.animateChange ? transitionParams2.animateChangeProgress : 1.0f, num);
                canvas.restore();
            }
            if (z) {
                canvas.restore();
            }
        }
    }

    public boolean drawReactionsLayoutOverlay(Canvas canvas, float f) {
        if (this.isRoundVideo) {
            this.reactionsLayoutInBubble.drawServiceShaderBackground = 1.0f - getVideoTranscriptionProgress();
        }
        boolean z = false;
        if (!this.reactionsVisible || !hasReactionsToDraw()) {
            return false;
        }
        if (this.reactionsLayoutInBubble.drawServiceShaderBackground > 0.0f) {
            applyServiceShaderMatrix();
        }
        if (getAlpha() * f != 1.0f) {
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(0.0f, 0.0f, getWidth(), getHeight());
            canvas.saveLayerAlpha(rectF, (int) (f * 255.0f * getAlpha()), 31);
            z = true;
        }
        ReactionsLayoutInBubble reactionsLayoutInBubble = this.reactionsLayoutInBubble;
        if (reactionsLayoutInBubble.drawServiceShaderBackground > 0.0f || !this.transitionParams.animateBackgroundBoundsInner || this.currentPosition != null || this.isRoundVideo) {
            TransitionParams transitionParams = this.transitionParams;
            reactionsLayoutInBubble.drawOverlay(canvas, transitionParams.animateChange ? transitionParams.animateChangeProgress : 1.0f);
        } else {
            canvas.save();
            canvas.clipRect(0.0f, 0.0f, getMeasuredWidth(), getBackgroundDrawableBottom() + this.transitionParams.deltaBottom);
            ReactionsLayoutInBubble reactionsLayoutInBubble2 = this.reactionsLayoutInBubble;
            TransitionParams transitionParams2 = this.transitionParams;
            reactionsLayoutInBubble2.drawOverlay(canvas, transitionParams2.animateChange ? transitionParams2.animateChangeProgress : 1.0f);
            canvas.restore();
        }
        if (z) {
            canvas.restore();
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:127:0x028d  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02f6  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0330  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0460  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawCaptionLayout(android.graphics.Canvas r23, org.telegram.messenger.MessageObject.TextLayoutBlocks r24, boolean r25, boolean r26, float r27) {
        /*
            Method dump skipped, instructions count: 1127
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawCaptionLayout(android.graphics.Canvas, org.telegram.messenger.MessageObject$TextLayoutBlocks, boolean, boolean, float):void");
    }

    public void drawProgressLoadingLink(Canvas canvas, int i) {
        updateProgressLoadingLink();
        ArrayList arrayList = this.progressLoadingLinkDrawables;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        MessageObject messageObject = this.currentMessageObject;
        int themedColor = getThemedColor((messageObject == null || !messageObject.isOutOwner()) ? Theme.key_chat_linkSelectBackground : Theme.key_chat_outLinkSelectBackground);
        int i2 = 0;
        while (i2 < this.progressLoadingLinkDrawables.size()) {
            LoadingDrawableLocation loadingDrawableLocation = (LoadingDrawableLocation) this.progressLoadingLinkDrawables.get(i2);
            if (loadingDrawableLocation.blockNum == i) {
                LoadingDrawable loadingDrawable = loadingDrawableLocation.drawable;
                loadingDrawable.setColors(Theme.multAlpha(themedColor, 0.85f), Theme.multAlpha(themedColor, 2.0f), Theme.multAlpha(themedColor, 3.5f), Theme.multAlpha(themedColor, 6.0f));
                loadingDrawable.draw(canvas);
                invalidate();
                if (loadingDrawable.isDisappeared()) {
                    this.progressLoadingLinkDrawables.remove(i2);
                    i2--;
                }
            }
            i2++;
        }
    }

    public void updateProgressLoadingLink() {
        MessageObject messageObject;
        ChatMessageCellDelegate chatMessageCellDelegate = this.delegate;
        if (chatMessageCellDelegate != null) {
            if (!chatMessageCellDelegate.isProgressLoading(this, 1)) {
                this.progressLoadingLink = null;
                ArrayList arrayList = this.progressLoadingLinkDrawables;
                if (arrayList != null && !arrayList.isEmpty()) {
                    for (int i = 0; i < this.progressLoadingLinkDrawables.size(); i++) {
                        LoadingDrawableLocation loadingDrawableLocation = (LoadingDrawableLocation) this.progressLoadingLinkDrawables.get(i);
                        if (!loadingDrawableLocation.drawable.isDisappearing()) {
                            loadingDrawableLocation.drawable.disappear();
                        }
                    }
                }
            } else {
                CharacterStyle progressLoadingLink = this.delegate.getProgressLoadingLink(this);
                if (progressLoadingLink != this.progressLoadingLink) {
                    this.progressLoadingLink = progressLoadingLink;
                    LoadingDrawable loadingDrawable = this.progressLoadingLinkCurrentDrawable;
                    if (loadingDrawable != null) {
                        loadingDrawable.disappear();
                        this.progressLoadingLinkCurrentDrawable = null;
                    }
                    LoadingDrawable loadingDrawable2 = new LoadingDrawable();
                    this.progressLoadingLinkCurrentDrawable = loadingDrawable2;
                    loadingDrawable2.setAppearByGradient(true);
                    LinkPath linkPath = new LinkPath(true);
                    this.progressLoadingLinkCurrentDrawable.usePath(linkPath);
                    this.progressLoadingLinkCurrentDrawable.setRadiiDp(5.0f);
                    LoadingDrawableLocation loadingDrawableLocation2 = new LoadingDrawableLocation();
                    loadingDrawableLocation2.drawable = this.progressLoadingLinkCurrentDrawable;
                    loadingDrawableLocation2.blockNum = -3;
                    if (this.progressLoadingLinkDrawables == null) {
                        this.progressLoadingLinkDrawables = new ArrayList();
                    }
                    this.progressLoadingLinkDrawables.add(loadingDrawableLocation2);
                    if (this.progressLoadingLink != null) {
                        if (findProgressLoadingLink(loadingDrawableLocation2, linkPath, this.descriptionLayout, 0.0f, -2)) {
                            return;
                        }
                        MessageObject.TextLayoutBlocks textLayoutBlocks = this.captionLayout;
                        if ((textLayoutBlocks == null || !findProgressLoadingLink(loadingDrawableLocation2, linkPath, textLayoutBlocks.textLayoutBlocks)) && (messageObject = this.currentMessageObject) != null) {
                            findProgressLoadingLink(loadingDrawableLocation2, linkPath, messageObject.textLayoutBlocks);
                        }
                    }
                }
            }
        }
    }

    private boolean findProgressLoadingLink(LoadingDrawableLocation loadingDrawableLocation, LinkPath linkPath, ArrayList arrayList) {
        if (arrayList == null) {
            return false;
        }
        int i = 0;
        while (i < arrayList.size()) {
            LoadingDrawableLocation loadingDrawableLocation2 = loadingDrawableLocation;
            LinkPath linkPath2 = linkPath;
            if (findProgressLoadingLink(loadingDrawableLocation2, linkPath2, ((MessageObject.TextLayoutBlock) arrayList.get(i)).textLayout, 0.0f, i)) {
                return true;
            }
            i++;
            loadingDrawableLocation = loadingDrawableLocation2;
            linkPath = linkPath2;
        }
        return false;
    }

    private boolean findProgressLoadingLink(LoadingDrawableLocation loadingDrawableLocation, LinkPath linkPath, Layout layout, float f, int i) {
        if (layout == null || !(layout.getText() instanceof Spanned)) {
            return false;
        }
        Spanned spanned = (Spanned) layout.getText();
        CharacterStyle[] characterStyleArr = (CharacterStyle[]) spanned.getSpans(0, spanned.length(), CharacterStyle.class);
        if (characterStyleArr != null) {
            int i2 = 0;
            while (true) {
                if (i2 >= characterStyleArr.length) {
                    break;
                }
                if (characterStyleArr[i2] == this.progressLoadingLink) {
                    loadingDrawableLocation.blockNum = i;
                    break;
                }
                i2++;
            }
        }
        if (loadingDrawableLocation.blockNum != i) {
            return false;
        }
        linkPath.rewind();
        int spanStart = spanned.getSpanStart(this.progressLoadingLink);
        int spanEnd = spanned.getSpanEnd(this.progressLoadingLink);
        linkPath.setUseCornerPathImplementation(true);
        linkPath.setCurrentLayout(layout, spanStart, f);
        layout.getSelectionPath(spanStart, spanEnd, linkPath);
        linkPath.closeRects();
        this.progressLoadingLinkCurrentDrawable.updateBounds();
        return true;
    }

    public boolean needDrawTime() {
        if (this.forceNotDrawTime) {
            return false;
        }
        MessageObject messageObject = this.currentMessageObject;
        return messageObject == null || messageObject.type != 27;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean shouldDrawTimeOnMedia() {
        /*
            r4 = this;
            int r0 = r4.overideShouldDrawTimeOnMedia
            r1 = 0
            r2 = 1
            if (r0 == 0) goto La
            if (r0 != r2) goto L9
            return r2
        L9:
            return r1
        La:
            boolean r0 = r4.mediaBackground
            if (r0 == 0) goto L32
            org.telegram.messenger.MessageObject$TextLayoutBlocks r0 = r4.captionLayout
            if (r0 == 0) goto L16
            boolean r0 = r4.captionAbove
            if (r0 == 0) goto L32
        L16:
            org.telegram.ui.Components.Reactions.ReactionsLayoutInBubble r0 = r4.reactionsLayoutInBubble
            boolean r3 = r0.isEmpty
            if (r3 != 0) goto L3c
            boolean r0 = r0.isSmall
            if (r0 != 0) goto L3c
            org.telegram.messenger.MessageObject r0 = r4.currentMessageObject
            if (r0 == 0) goto L32
            boolean r0 = r0.isAnyKindOfSticker()
            if (r0 != 0) goto L3c
            org.telegram.messenger.MessageObject r0 = r4.currentMessageObject
            boolean r0 = r0.isRoundVideo()
            if (r0 != 0) goto L3c
        L32:
            org.telegram.messenger.MessageObject r0 = r4.currentMessageObject
            if (r0 == 0) goto L3d
            int r0 = r0.type
            r3 = 29
            if (r0 != r3) goto L3d
        L3c:
            return r2
        L3d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.shouldDrawTimeOnMedia():boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:63:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0117  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0136  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawTime(android.graphics.Canvas r15, float r16, boolean r17) {
        /*
            Method dump skipped, instructions count: 536
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawTime(android.graphics.Canvas, float, boolean):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:137:0x0237  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0266  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0271  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0278  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0293  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x02a0  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02bb  */
    /* JADX WARN: Removed duplicated region for block: B:181:0x02f7  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0336  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0341  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0367  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0377  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:410:0x0840  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawTimeInternal(android.graphics.Canvas r42, float r43, boolean r44, float r45, android.text.StaticLayout r46, float r47, boolean r48) {
        /*
            Method dump skipped, instructions count: 3003
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawTimeInternal(android.graphics.Canvas, float, boolean, float, android.text.StaticLayout, float, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createStatusDrawableAnimator(int i, int i2, final boolean z) {
        boolean z2 = false;
        boolean z3 = (i2 & 1) != 0;
        boolean z4 = (i2 & 2) != 0;
        boolean z5 = (i & 1) != 0;
        boolean z6 = (i & 2) != 0;
        if ((i & 4) == 0 && z6 && z4 && !z5 && z3) {
            z2 = true;
        }
        if (!this.transitionParams.messageEntering || z2) {
            this.statusDrawableProgress = 0.0f;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.statusDrawableAnimator = valueAnimatorOfFloat;
            if (z2) {
                valueAnimatorOfFloat.setDuration(220L);
            } else {
                valueAnimatorOfFloat.setDuration(150L);
            }
            this.statusDrawableAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.animateFromStatusDrawableParams = i;
            this.animateToStatusDrawableParams = i2;
            this.statusDrawableAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Cells.ChatMessageCell$$ExternalSyntheticLambda16
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$createStatusDrawableAnimator$15(z, valueAnimator);
                }
            });
            this.statusDrawableAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Cells.ChatMessageCell.10
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    int iCreateStatusDrawableParams = ChatMessageCell.this.transitionParams.createStatusDrawableParams();
                    if (ChatMessageCell.this.animateToStatusDrawableParams != iCreateStatusDrawableParams) {
                        ChatMessageCell chatMessageCell = ChatMessageCell.this;
                        chatMessageCell.createStatusDrawableAnimator(chatMessageCell.animateToStatusDrawableParams, iCreateStatusDrawableParams, z);
                    } else {
                        ChatMessageCell.this.statusDrawableAnimationInProgress = false;
                        ChatMessageCell chatMessageCell2 = ChatMessageCell.this;
                        chatMessageCell2.transitionParams.lastStatusDrawableParams = chatMessageCell2.animateToStatusDrawableParams;
                    }
                }
            });
            this.statusDrawableAnimationInProgress = true;
            this.statusDrawableAnimator.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createStatusDrawableAnimator$15(boolean z, ValueAnimator valueAnimator) {
        this.statusDrawableProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
        if (!z || getParent() == null) {
            return;
        }
        ((View) getParent()).invalidate();
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:74:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawClockOrErrorLayout(android.graphics.Canvas r7, boolean r8, boolean r9, float r10, float r11, float r12, float r13, float r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawClockOrErrorLayout(android.graphics.Canvas, boolean, boolean, float, float, float, float, float, boolean):void");
    }

    public float getTimeX() {
        return this.transitionParams.shouldAnimateTimeX ? AndroidUtilities.lerp(r0.animateFromTimeX, this.timeX, r0.animateChangeProgress) : this.timeX;
    }

    public float getTimeY() {
        int iM1146dp;
        int iM1146dp2 = 0;
        if (shouldDrawTimeOnMedia()) {
            if (this.drawCommentButton) {
                iM1146dp2 = AndroidUtilities.m1146dp(41.3f);
            }
        } else {
            if (this.currentMessageObject.isSponsored()) {
                iM1146dp = -AndroidUtilities.m1146dp(48.0f);
                if (this.hasNewLineForTime) {
                    iM1146dp -= AndroidUtilities.m1146dp(4.0f);
                }
                return getTimeY(iM1146dp);
            }
            if (this.drawCommentButton) {
                iM1146dp2 = AndroidUtilities.m1146dp(43.0f);
            }
        }
        iM1146dp = -iM1146dp2;
        return getTimeY(iM1146dp);
    }

    public float getTimeY(float f) {
        if (shouldDrawTimeOnMedia() && this.documentAttachType != 7) {
            return ((getPhotoBottom() + this.additionalTimeOffsetY) - AndroidUtilities.m1146dp(7.3f)) - this.timeLayout.getHeight();
        }
        float fM1146dp = ((this.layoutHeight - AndroidUtilities.m1146dp((this.pinnedBottom || this.pinnedTop) ? 7.5f : 6.5f)) - this.timeLayout.getHeight()) + f;
        if (this.isRoundVideo) {
            fM1146dp -= (AndroidUtilities.m1146dp(this.drawPinnedBottom ? 4.0f : 5.0f) + this.reactionsLayoutInBubble.getCurrentTotalHeight(this.transitionParams.animateChangeProgress)) * (1.0f - getVideoTranscriptionProgress());
        }
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null) {
            MessageObject.GroupedMessages.TransitionParams transitionParams = groupedMessages.transitionParams;
            float f2 = fM1146dp + transitionParams.offsetBottom;
            return transitionParams.backgroundChangeBounds ? f2 - getTranslationY() : f2;
        }
        TransitionParams transitionParams2 = this.transitionParams;
        return fM1146dp + (transitionParams2.deltaBottom - transitionParams2.deltaTop);
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01a9  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01c3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawViewsAndRepliesLayout(android.graphics.Canvas r25, float r26, float r27, float r28, float r29, float r30, boolean r31) {
        /*
            Method dump skipped, instructions count: 1100
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawViewsAndRepliesLayout(android.graphics.Canvas, float, float, float, float, float, boolean):void");
    }

    private void drawStatusDrawable(Canvas canvas, boolean z, boolean z2, boolean z3, boolean z4, float f, boolean z5, float f2, float f3, float f4, boolean z6, boolean z7) {
        float photoBottom;
        float f5;
        float f6;
        float f7;
        int iM1146dp;
        int iM1146dp2;
        Drawable themedDrawable;
        Drawable themedDrawable2;
        int themedColor;
        MessageObject messageObject;
        boolean z8 = (f4 == 1.0f || z6) ? false : true;
        float f8 = (f4 * 0.5f) + 0.5f;
        float f9 = z8 ? f * f4 : f;
        if (this.documentAttachType != 7 || ((messageObject = this.currentMessageObject) != null && messageObject.isRoundOnce())) {
            photoBottom = getPhotoBottom() + this.additionalTimeOffsetY;
        } else {
            photoBottom = f3 - ((AndroidUtilities.m1146dp(this.drawPinnedBottom ? 4.0f : 5.0f) + this.reactionsLayoutInBubble.getCurrentTotalHeight(this.transitionParams.animateChangeProgress)) * (1.0f - getVideoTranscriptionProgress()));
        }
        float fM1146dp = photoBottom - AndroidUtilities.m1146dp(8.5f);
        MessageObject messageObject2 = this.currentMessageObject;
        float fM1146dp2 = (messageObject2 == null || !messageObject2.isAnyKindOfSticker()) ? 0.0f : AndroidUtilities.m1146dp(-6.0f);
        float f10 = 22.0f;
        if (z3) {
            MsgClockDrawable msgClockDrawable = Theme.chat_msgClockDrawable;
            if (shouldDrawTimeOnMedia()) {
                if (this.currentMessageObject.shouldDrawWithoutBackground()) {
                    themedColor = getThemedColor(Theme.key_chat_serviceText);
                    f7 = 1.0f;
                    f5 = 4.0f;
                    BaseCell.setDrawableBounds(msgClockDrawable, ((this.layoutWidth - AndroidUtilities.m1146dp(z5 ? 24.0f : 22.0f)) - msgClockDrawable.getIntrinsicWidth()) + fM1146dp2, (fM1146dp - msgClockDrawable.getIntrinsicHeight()) + f2);
                    msgClockDrawable.setAlpha((int) (this.timeAlpha * 255.0f * f9));
                } else {
                    f5 = 4.0f;
                    f7 = 1.0f;
                    themedColor = getThemedColor(Theme.key_chat_mediaSentClock);
                    BaseCell.setDrawableBounds(msgClockDrawable, ((this.layoutWidth - AndroidUtilities.m1146dp(z5 ? 24.0f : 22.0f)) - msgClockDrawable.getIntrinsicWidth()) + fM1146dp2, (fM1146dp - msgClockDrawable.getIntrinsicHeight()) + f2);
                    msgClockDrawable.setAlpha((int) (f9 * 255.0f));
                }
                f6 = 18.5f;
            } else {
                f5 = 4.0f;
                f7 = 1.0f;
                int themedColor2 = getThemedColor(Theme.key_chat_outSentClock);
                f6 = 18.5f;
                BaseCell.setDrawableBounds(msgClockDrawable, (this.layoutWidth - AndroidUtilities.m1146dp(18.5f)) - msgClockDrawable.getIntrinsicWidth(), ((f3 - AndroidUtilities.m1146dp(8.5f)) - msgClockDrawable.getIntrinsicHeight()) + f2);
                msgClockDrawable.setAlpha((int) (f9 * 255.0f));
                themedColor = themedColor2;
            }
            msgClockDrawable.setColor(themedColor);
            if (z8) {
                canvas.save();
                canvas.scale(f8, f8, msgClockDrawable.getBounds().centerX(), msgClockDrawable.getBounds().centerY());
            }
            msgClockDrawable.draw(canvas);
            msgClockDrawable.setAlpha(255);
            if (z8) {
                canvas.restore();
            }
            invalidate();
        } else {
            f5 = 4.0f;
            f6 = 18.5f;
            f7 = 1.0f;
        }
        if (z2) {
            if (shouldDrawTimeOnMedia()) {
                if (z6) {
                    canvas.save();
                }
                if (this.currentMessageObject.shouldDrawWithoutBackground()) {
                    themedDrawable2 = getThemedDrawable("drawableMsgStickerCheck");
                    if (z) {
                        if (z6) {
                            canvas.translate(AndroidUtilities.m1146dp(4.8f) * (f7 - f4), 0.0f);
                        }
                        BaseCell.setDrawableBounds(themedDrawable2, ((this.layoutWidth - AndroidUtilities.m1146dp(z5 ? 28.3f : 26.3f)) - themedDrawable2.getIntrinsicWidth()) + fM1146dp2, (fM1146dp - themedDrawable2.getIntrinsicHeight()) + f2);
                    } else {
                        BaseCell.setDrawableBounds(themedDrawable2, ((this.layoutWidth - AndroidUtilities.m1146dp(z5 ? 23.5f : 21.5f)) - themedDrawable2.getIntrinsicWidth()) + fM1146dp2, (fM1146dp - themedDrawable2.getIntrinsicHeight()) + f2);
                    }
                    themedDrawable2.setAlpha((int) (this.timeAlpha * 255.0f * f9));
                } else {
                    if (z) {
                        if (z6) {
                            canvas.translate(AndroidUtilities.m1146dp(4.8f) * (f7 - f4), 0.0f);
                        }
                        BaseCell.setDrawableBounds(Theme.chat_msgMediaCheckDrawable, (this.layoutWidth - AndroidUtilities.m1146dp(z5 ? 28.3f : 26.3f)) - Theme.chat_msgMediaCheckDrawable.getIntrinsicWidth(), (fM1146dp - Theme.chat_msgMediaCheckDrawable.getIntrinsicHeight()) + f2);
                    } else {
                        BaseCell.setDrawableBounds(Theme.chat_msgMediaCheckDrawable, (this.layoutWidth - AndroidUtilities.m1146dp(z5 ? 23.5f : 21.5f)) - Theme.chat_msgMediaCheckDrawable.getIntrinsicWidth(), (fM1146dp - Theme.chat_msgMediaCheckDrawable.getIntrinsicHeight()) + f2);
                    }
                    Theme.chat_msgMediaCheckDrawable.setAlpha((int) (this.timeAlpha * 255.0f * f9));
                    themedDrawable2 = Theme.chat_msgMediaCheckDrawable;
                }
                if (z8) {
                    canvas.save();
                    canvas.scale(f8, f8, themedDrawable2.getBounds().centerX(), themedDrawable2.getBounds().centerY());
                }
                themedDrawable2.draw(canvas);
                if (z8) {
                    canvas.restore();
                }
                if (z6) {
                    canvas.restore();
                }
                themedDrawable2.setAlpha(255);
            } else {
                if (z6) {
                    canvas.save();
                }
                if (z) {
                    if (z6) {
                        canvas.translate(AndroidUtilities.m1146dp(f5) * (f7 - f4), 0.0f);
                    }
                    themedDrawable = getThemedDrawable(z7 ? "drawableMsgOutCheckReadSelected" : "drawableMsgOutCheckRead");
                    BaseCell.setDrawableBounds(themedDrawable, ((this.layoutWidth - AndroidUtilities.m1146dp(22.5f)) - themedDrawable.getIntrinsicWidth()) + fM1146dp2, ((f3 - AndroidUtilities.m1146dp((this.pinnedBottom || this.pinnedTop) ? 9.0f : 8.0f)) - themedDrawable.getIntrinsicHeight()) + f2);
                } else {
                    themedDrawable = getThemedDrawable(z7 ? "drawableMsgOutCheckSelected" : "drawableMsgOutCheck");
                    BaseCell.setDrawableBounds(themedDrawable, ((this.layoutWidth - AndroidUtilities.m1146dp(f6)) - themedDrawable.getIntrinsicWidth()) + fM1146dp2, ((f3 - AndroidUtilities.m1146dp((this.pinnedBottom || this.pinnedTop) ? 9.0f : 8.0f)) - themedDrawable.getIntrinsicHeight()) + f2);
                }
                themedDrawable.setAlpha((int) (f9 * 255.0f));
                if (z8) {
                    canvas.save();
                    canvas.scale(f8, f8, themedDrawable.getBounds().centerX(), themedDrawable.getBounds().centerY());
                }
                themedDrawable.draw(canvas);
                if (z8) {
                    canvas.restore();
                }
                if (z6) {
                    canvas.restore();
                }
                themedDrawable.setAlpha(255);
            }
        }
        if (z) {
            if (shouldDrawTimeOnMedia()) {
                Drawable themedDrawable3 = this.currentMessageObject.shouldDrawWithoutBackground() ? getThemedDrawable("drawableMsgStickerHalfCheck") : Theme.chat_msgMediaHalfCheckDrawable;
                BaseCell.setDrawableBounds(themedDrawable3, ((this.layoutWidth - AndroidUtilities.m1146dp(z5 ? 23.5f : 21.5f)) - themedDrawable3.getIntrinsicWidth()) + fM1146dp2, (fM1146dp - themedDrawable3.getIntrinsicHeight()) + f2);
                themedDrawable3.setAlpha((int) (this.timeAlpha * 255.0f * f9));
                if (z8 || z6) {
                    canvas.save();
                    canvas.scale(f8, f8, themedDrawable3.getBounds().centerX(), themedDrawable3.getBounds().centerY());
                }
                themedDrawable3.draw(canvas);
                if (z8 || z6) {
                    canvas.restore();
                }
                themedDrawable3.setAlpha(255);
            } else {
                Drawable themedDrawable4 = getThemedDrawable(z7 ? "drawableMsgOutHalfCheckSelected" : "drawableMsgOutHalfCheck");
                BaseCell.setDrawableBounds(themedDrawable4, (this.layoutWidth - AndroidUtilities.m1146dp(18.0f)) - themedDrawable4.getIntrinsicWidth(), ((f3 - AndroidUtilities.m1146dp((this.pinnedBottom || this.pinnedTop) ? 9.0f : 8.0f)) - themedDrawable4.getIntrinsicHeight()) + f2);
                themedDrawable4.setAlpha((int) (f9 * 255.0f));
                if (z8 || z6) {
                    canvas.save();
                    canvas.scale(f8, f8, themedDrawable4.getBounds().centerX(), themedDrawable4.getBounds().centerY());
                }
                themedDrawable4.draw(canvas);
                if (z8 || z6) {
                    canvas.restore();
                }
                themedDrawable4.setAlpha(255);
            }
        }
        if (z4) {
            if (shouldDrawTimeOnMedia()) {
                iM1146dp = this.layoutWidth - AndroidUtilities.m1146dp(34.5f);
                iM1146dp2 = AndroidUtilities.m1146dp(26.5f);
            } else {
                iM1146dp = this.layoutWidth - AndroidUtilities.m1146dp(32.0f);
                if (!this.pinnedBottom && !this.pinnedTop) {
                    f10 = 21.0f;
                }
                iM1146dp2 = AndroidUtilities.m1146dp(f10);
            }
            float f11 = (f3 - iM1146dp2) + f2;
            this.rect.set((int) (iM1146dp + fM1146dp2), f11, AndroidUtilities.m1146dp(14.0f) + r2, AndroidUtilities.m1146dp(14.0f) + f11);
            int alpha = Theme.chat_msgErrorPaint.getAlpha();
            Theme.chat_msgErrorPaint.setAlpha((int) (alpha * f9));
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(3.0f), AndroidUtilities.m1146dp(3.0f), Theme.chat_msgErrorPaint);
            BaseCell.setDrawableBounds(Theme.chat_msgErrorDrawable, r2 + AndroidUtilities.m1146dp(6.0f), f11 + AndroidUtilities.m1146dp(2.0f));
            Theme.chat_msgErrorPaint.setAlpha(alpha);
            Theme.chat_msgErrorDrawable.setAlpha((int) (f9 * 255.0f));
            if (z8) {
                canvas.save();
                canvas.scale(f8, f8, Theme.chat_msgErrorDrawable.getBounds().centerX(), Theme.chat_msgErrorDrawable.getBounds().centerY());
            }
            Theme.chat_msgErrorDrawable.draw(canvas);
            Theme.chat_msgErrorDrawable.setAlpha(255);
            if (z8) {
                canvas.restore();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:1001:0x1a63  */
    /* JADX WARN: Removed duplicated region for block: B:1002:0x1a66  */
    /* JADX WARN: Removed duplicated region for block: B:1005:0x1a6d  */
    /* JADX WARN: Removed duplicated region for block: B:1006:0x1a72  */
    /* JADX WARN: Removed duplicated region for block: B:1023:0x1b1b  */
    /* JADX WARN: Removed duplicated region for block: B:1024:0x1b1d  */
    /* JADX WARN: Removed duplicated region for block: B:1028:0x1b54  */
    /* JADX WARN: Removed duplicated region for block: B:1032:0x1b69  */
    /* JADX WARN: Removed duplicated region for block: B:1040:0x1b86  */
    /* JADX WARN: Removed duplicated region for block: B:1044:0x1b9c  */
    /* JADX WARN: Removed duplicated region for block: B:1061:0x1bcc  */
    /* JADX WARN: Removed duplicated region for block: B:1067:0x1bda  */
    /* JADX WARN: Removed duplicated region for block: B:1071:0x1be9  */
    /* JADX WARN: Removed duplicated region for block: B:1121:0x1d50  */
    /* JADX WARN: Removed duplicated region for block: B:1135:0x1d7b  */
    /* JADX WARN: Removed duplicated region for block: B:1235:0x1f4c  */
    /* JADX WARN: Removed duplicated region for block: B:1354:0x2205  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x07fd  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x080c  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x0818  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x083b  */
    /* JADX WARN: Removed duplicated region for block: B:281:0x0843  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x084f  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x0923  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x093e  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x09e3  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x0c99 A[PHI: r3
      0x0c99: PHI (r3v114 android.graphics.Canvas) = (r3v112 android.graphics.Canvas), (r3v110 android.graphics.Canvas) binds: [B:421:0x0cee, B:417:0x0c96] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:429:0x0d0d  */
    /* JADX WARN: Removed duplicated region for block: B:433:0x0d19  */
    /* JADX WARN: Removed duplicated region for block: B:436:0x0d49  */
    /* JADX WARN: Removed duplicated region for block: B:439:0x0d54  */
    /* JADX WARN: Removed duplicated region for block: B:442:0x0d5e  */
    /* JADX WARN: Removed duplicated region for block: B:446:0x0d77  */
    /* JADX WARN: Removed duplicated region for block: B:501:0x0f8e  */
    /* JADX WARN: Removed duplicated region for block: B:504:0x0f9b  */
    /* JADX WARN: Removed duplicated region for block: B:509:0x0fb5  */
    /* JADX WARN: Removed duplicated region for block: B:546:0x1027  */
    /* JADX WARN: Removed duplicated region for block: B:548:0x1031  */
    /* JADX WARN: Removed duplicated region for block: B:549:0x1034  */
    /* JADX WARN: Removed duplicated region for block: B:553:0x1041  */
    /* JADX WARN: Removed duplicated region for block: B:568:0x1112  */
    /* JADX WARN: Removed duplicated region for block: B:577:0x114f  */
    /* JADX WARN: Removed duplicated region for block: B:588:0x1185  */
    /* JADX WARN: Removed duplicated region for block: B:590:0x118b  */
    /* JADX WARN: Removed duplicated region for block: B:595:0x11a2  */
    /* JADX WARN: Removed duplicated region for block: B:602:0x11bc  */
    /* JADX WARN: Removed duplicated region for block: B:605:0x11d4  */
    /* JADX WARN: Removed duplicated region for block: B:638:0x12e1  */
    /* JADX WARN: Removed duplicated region for block: B:656:0x135d  */
    /* JADX WARN: Removed duplicated region for block: B:658:0x1365  */
    /* JADX WARN: Removed duplicated region for block: B:663:0x137a  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:670:0x1392  */
    /* JADX WARN: Removed duplicated region for block: B:672:0x13a8  */
    /* JADX WARN: Removed duplicated region for block: B:673:0x13ab  */
    /* JADX WARN: Removed duplicated region for block: B:713:0x1509  */
    /* JADX WARN: Removed duplicated region for block: B:717:0x153d  */
    /* JADX WARN: Removed duplicated region for block: B:720:0x1542  */
    /* JADX WARN: Removed duplicated region for block: B:746:0x15e5  */
    /* JADX WARN: Removed duplicated region for block: B:783:0x16c5  */
    /* JADX WARN: Removed duplicated region for block: B:841:0x17b4  */
    /* JADX WARN: Removed duplicated region for block: B:844:0x17bd  */
    /* JADX WARN: Removed duplicated region for block: B:845:0x17c6  */
    /* JADX WARN: Removed duplicated region for block: B:938:0x18ae  */
    /* JADX WARN: Removed duplicated region for block: B:988:0x19e9  */
    /* JADX WARN: Removed duplicated region for block: B:989:0x1a24  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawOverlays(android.graphics.Canvas r62) {
        /*
            Method dump skipped, instructions count: 9508
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawOverlays(android.graphics.Canvas):void");
    }

    public int getTodoIndex(int i) {
        for (int i2 = 0; i2 < this.pollButtons.size(); i2++) {
            PollButton pollButton = (PollButton) this.pollButtons.get(i2);
            if (pollButton.task != null && pollButton.task.f1732id == i) {
                return i2;
            }
        }
        return -1;
    }

    public float getPollButtonTop(int i) {
        if (i < 0 || i >= this.pollButtons.size()) {
            return 0.0f;
        }
        int i2 = ((PollButton) this.pollButtons.get(i)).f1806y;
        int i3 = this.namesOffset;
        float f = i2 + i3;
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateForwardedLayout) {
            float f2 = transitionParams.animateChangeProgress;
            f += (i3 * f2) + (transitionParams.animateForwardedNamesOffset * (1.0f - f2));
            if (this.currentMessageObject.needDrawForwarded()) {
                f -= this.namesOffset;
            }
        }
        TransitionParams transitionParams2 = this.transitionParams;
        if (transitionParams2.animateBackgroundBoundsInner) {
            f += transitionParams2.deltaTop;
        }
        return f - AndroidUtilities.m1146dp(13.0f);
    }

    public float getPollButtonsLeft() {
        int iM1146dp;
        if (getMessageObject() != null && getMessageObject().isOutOwner()) {
            iM1146dp = (AndroidUtilities.m1146dp(3.0f) + this.layoutWidth) - this.backgroundWidth;
        } else if (this.isSideMenuEnabled) {
            iM1146dp = AndroidUtilities.m1146dp(82.0f);
        } else if (needDrawAvatar()) {
            iM1146dp = AndroidUtilities.m1146dp(59.0f);
        } else {
            iM1146dp = AndroidUtilities.m1146dp(11.0f);
        }
        return iM1146dp;
    }

    public float getPollButtonsRight() {
        return (getPollButtonsLeft() + this.backgroundWidth) - AndroidUtilities.m1146dp(15.0f);
    }

    public float getPollButtonBottom(int i) {
        if (i < 0 || i >= this.pollButtons.size()) {
            return 0.0f;
        }
        int i2 = ((PollButton) this.pollButtons.get(i)).f1806y;
        int i3 = this.namesOffset;
        float f = i2 + i3;
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateForwardedLayout) {
            float f2 = transitionParams.animateChangeProgress;
            f += (i3 * f2) + (transitionParams.animateForwardedNamesOffset * (1.0f - f2));
            if (this.currentMessageObject.needDrawForwarded()) {
                f -= this.namesOffset;
            }
        }
        TransitionParams transitionParams2 = this.transitionParams;
        if (transitionParams2.animateBackgroundBoundsInner) {
            f += transitionParams2.deltaTop;
        }
        return f + r6.height + AndroidUtilities.m1146dp(13.0f);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void drawRadialProgress(android.graphics.Canvas r12) {
        /*
            Method dump skipped, instructions count: 376
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.drawRadialProgress(android.graphics.Canvas):void");
    }

    protected void drawPhotoBlurRect(Canvas canvas, RectF rectF) {
        this.rectPath.rewind();
        this.rectPath.addRoundRect(rectF, rectF.width() / 2.0f, rectF.height() / 2.0f, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(this.rectPath);
        float alpha = this.photoImage.getAlpha();
        this.photoImage.setAlpha((this.currentMessageObject.isRoundOnce() ? 1.0f : 0.5f) * alpha);
        this.photoImage.draw(canvas);
        this.photoImage.setAlpha(alpha);
        canvas.restore();
        Paint themedPaint = getThemedPaint("paintChatTimeBackground");
        int alpha2 = themedPaint.getAlpha();
        themedPaint.setAlpha((int) (alpha2 * this.controlsAlpha * 0.4f));
        canvas.drawRoundRect(rectF, rectF.width() / 2.0f, rectF.height() / 2.0f, themedPaint);
        themedPaint.setAlpha(alpha2);
    }

    @Override // org.telegram.messenger.DownloadController.FileDownloadProgressListener
    public int getObserverTag() {
        return this.TAG;
    }

    public MessageObject getMessageObject() {
        MessageObject messageObject = this.messageObjectToSet;
        return messageObject != null ? messageObject : this.currentMessageObject;
    }

    public TLRPC.Document getStreamingMedia() {
        int i = this.documentAttachType;
        if (i == 4 || i == 7 || i == 2) {
            return this.documentAttach;
        }
        return null;
    }

    public boolean drawPinnedBottom() {
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null && groupedMessages.isDocuments) {
            MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
            if (groupedMessagePosition == null || (groupedMessagePosition.flags & 8) == 0) {
                return true;
            }
            return this.pinnedBottom;
        }
        return this.pinnedBottom;
    }

    public float getVideoTranscriptionProgress() {
        MessageObject messageObject;
        if (this.transitionParams == null || (messageObject = this.currentMessageObject) == null || !messageObject.isRoundVideo()) {
            return 1.0f;
        }
        TransitionParams transitionParams = this.transitionParams;
        if (!transitionParams.animateDrawBackground) {
            return this.drawBackground ? 1.0f : 0.0f;
        }
        if (this.drawBackground) {
            return transitionParams.animateChangeProgress;
        }
        return 1.0f - transitionParams.animateChangeProgress;
    }

    public boolean drawPinnedTop() {
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null && groupedMessages.isDocuments) {
            MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
            if (groupedMessagePosition == null || (groupedMessagePosition.flags & 4) == 0) {
                return true;
            }
            return this.pinnedTop;
        }
        return this.pinnedTop;
    }

    public boolean isPinnedBottom() {
        if (this.messageObjectToSet != null) {
            return this.bottomNearToSet;
        }
        return this.pinnedBottom;
    }

    public boolean isPinnedTop() {
        if (this.messageObjectToSet != null) {
            return this.topNearToSet;
        }
        return this.pinnedTop;
    }

    public boolean isFirstInChat() {
        if (this.messageObjectToSet != null) {
            return this.firstInChatToSet;
        }
        return this.firstInChat;
    }

    public boolean isLastInChatList() {
        if (this.messageObjectToSet != null) {
            return this.lastInChatListToSet;
        }
        return this.lastInChatList;
    }

    public MessageObject.GroupedMessages getCurrentMessagesGroup() {
        return this.currentMessagesGroup;
    }

    public MessageObject.GroupedMessagePosition getCurrentPosition() {
        return this.currentPosition;
    }

    public int getLayoutHeight() {
        return this.layoutHeight;
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) throws Resources.NotFoundException {
        ChatMessageCellDelegate chatMessageCellDelegate;
        ChatMessageCell chatMessageCell;
        ChatMessageCellDelegate chatMessageCellDelegate2 = this.delegate;
        if (chatMessageCellDelegate2 != null && chatMessageCellDelegate2.onAccessibilityAction(i, bundle)) {
            return false;
        }
        if (i == 16) {
            int iconForCurrentState = getIconForCurrentState();
            if (iconForCurrentState != 4 && iconForCurrentState != 5) {
                didPressButton(true, false);
            } else if (this.currentMessageObject.type == 16) {
                this.delegate.didPressOther(this, this.otherX, this.otherY);
            } else {
                didClickedImage();
            }
            return true;
        }
        if (i == C2369R.id.acc_action_small_button) {
            didPressMiniButton(true);
        } else if (i == C2369R.id.acc_action_msg_options) {
            ChatMessageCellDelegate chatMessageCellDelegate3 = this.delegate;
            if (chatMessageCellDelegate3 != null) {
                if (this.currentMessageObject.type == 16) {
                    chatMessageCellDelegate3.didLongPress(this, 0.0f, 0.0f);
                } else {
                    chatMessageCellDelegate3.didPressOther(this, this.otherX, this.otherY);
                }
            }
        } else {
            if (i == C2369R.id.acc_action_open_forwarded_origin && (chatMessageCellDelegate = this.delegate) != null) {
                TLRPC.Chat chat = this.currentForwardChannel;
                if (chat != null) {
                    chatMessageCell = this;
                    chatMessageCellDelegate.didPressChannelAvatar(chatMessageCell, chat, this.currentMessageObject.messageOwner.fwd_from.channel_post, this.lastTouchX, this.lastTouchY, false);
                } else {
                    chatMessageCell = this;
                    TLRPC.User user = chatMessageCell.currentForwardUser;
                    if (user != null) {
                        chatMessageCellDelegate.didPressUserAvatar(chatMessageCell, user, chatMessageCell.lastTouchX, chatMessageCell.lastTouchY, false);
                    } else if (chatMessageCell.currentForwardName != null) {
                        chatMessageCellDelegate.didPressHiddenForward(this);
                    }
                }
            }
            if ((chatMessageCell.currentMessageObject.isVoice() && !chatMessageCell.currentMessageObject.isRoundVideo() && (!chatMessageCell.currentMessageObject.isMusic() || !MediaController.getInstance().isPlayingMessage(chatMessageCell.currentMessageObject))) || !chatMessageCell.seekBarAccessibilityDelegate.performAccessibilityActionInternal(i, bundle)) {
                return super.performAccessibilityAction(i, bundle);
            }
        }
        chatMessageCell = this;
        return chatMessageCell.currentMessageObject.isVoice() ? true : true;
        return super.performAccessibilityAction(i, bundle);
    }

    public void setAnimationRunning(boolean z, boolean z2) {
        this.animationRunning = z;
        if (z) {
            this.willRemoved = z2;
        } else {
            this.willRemoved = false;
        }
    }

    public float getEventX(MotionEvent motionEvent) {
        return motionEvent.getX();
    }

    public float getEventY(MotionEvent motionEvent) {
        return ((motionEvent.getY() - this.starsPriceTopPadding) - this.topicSeparatorTopPadding) - this.suggestionOfferTopPadding;
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int eventX = (int) getEventX(motionEvent);
        int eventY = (int) getEventY(motionEvent);
        if (motionEvent.getAction() == 9 || motionEvent.getAction() == 7) {
            for (int i = 0; i < this.accessibilityVirtualViewBounds.size(); i++) {
                if (((Rect) this.accessibilityVirtualViewBounds.valueAt(i)).contains(eventX, eventY)) {
                    int iKeyAt = this.accessibilityVirtualViewBounds.keyAt(i);
                    if (iKeyAt == this.currentFocusedVirtualView) {
                        return true;
                    }
                    this.currentFocusedVirtualView = iKeyAt;
                    sendAccessibilityEventForVirtualView(iKeyAt, 32768);
                    return true;
                }
            }
        } else if (motionEvent.getAction() == 10) {
            this.currentFocusedVirtualView = 0;
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    @Override // android.view.View
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        return new MessageAccessibilityNodeProvider();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAccessibilityEventForVirtualView(int i, int i2) {
        sendAccessibilityEventForVirtualView(i, i2, null);
    }

    private void sendAccessibilityEventForVirtualView(int i, int i2, String str) {
        if (((AccessibilityManager) getContext().getSystemService("accessibility")).isTouchExplorationEnabled()) {
            AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i2);
            accessibilityEventObtain.setPackageName(getContext().getPackageName());
            accessibilityEventObtain.setSource(this, i);
            if (str != null) {
                accessibilityEventObtain.getText().add(str);
            }
            if (getParent() != null) {
                getParent().requestSendAccessibilityEvent(this, accessibilityEventObtain);
            }
        }
    }

    public static Point getMessageSize(int i, int i2) {
        return getMessageSize(i, i2, 0, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static org.telegram.p023ui.Components.Point getMessageSize(int r3, int r4, int r5, int r6) {
        /*
            if (r6 == 0) goto L4
            if (r5 != 0) goto L50
        L4:
            boolean r5 = org.telegram.messenger.AndroidUtilities.isTablet()
            r6 = 1060320051(0x3f333333, float:0.7)
            if (r5 == 0) goto L16
            int r5 = org.telegram.messenger.AndroidUtilities.getMinTabletSide()
        L11:
            float r5 = (float) r5
            float r5 = r5 * r6
            int r5 = (int) r5
            goto L35
        L16:
            if (r3 < r4) goto L2a
            android.graphics.Point r5 = org.telegram.messenger.AndroidUtilities.displaySize
            int r6 = r5.x
            int r5 = r5.y
            int r5 = java.lang.Math.min(r6, r5)
            r6 = 1115684864(0x42800000, float:64.0)
            int r6 = org.telegram.messenger.AndroidUtilities.m1146dp(r6)
            int r5 = r5 - r6
            goto L35
        L2a:
            android.graphics.Point r5 = org.telegram.messenger.AndroidUtilities.displaySize
            int r0 = r5.x
            int r5 = r5.y
            int r5 = java.lang.Math.min(r0, r5)
            goto L11
        L35:
            r6 = 1120403456(0x42c80000, float:100.0)
            int r6 = org.telegram.messenger.AndroidUtilities.m1146dp(r6)
            int r6 = r6 + r5
            int r0 = org.telegram.messenger.AndroidUtilities.getPhotoSize()
            if (r5 <= r0) goto L46
            int r5 = org.telegram.messenger.AndroidUtilities.getPhotoSize()
        L46:
            int r0 = org.telegram.messenger.AndroidUtilities.getPhotoSize()
            if (r6 <= r0) goto L50
            int r6 = org.telegram.messenger.AndroidUtilities.getPhotoSize()
        L50:
            float r3 = (float) r3
            float r5 = (float) r5
            float r0 = r3 / r5
            float r1 = r3 / r0
            int r1 = (int) r1
            float r4 = (float) r4
            float r0 = r4 / r0
            int r0 = (int) r0
            r2 = 1125515264(0x43160000, float:150.0)
            if (r1 != 0) goto L63
            int r1 = org.telegram.messenger.AndroidUtilities.m1146dp(r2)
        L63:
            if (r0 != 0) goto L69
            int r0 = org.telegram.messenger.AndroidUtilities.m1146dp(r2)
        L69:
            if (r0 <= r6) goto L72
            float r3 = (float) r0
            float r4 = (float) r6
            float r3 = r3 / r4
            float r4 = (float) r1
            float r4 = r4 / r3
            int r1 = (int) r4
            goto L88
        L72:
            r6 = 1123024896(0x42f00000, float:120.0)
            int r2 = org.telegram.messenger.AndroidUtilities.m1146dp(r6)
            if (r0 >= r2) goto L87
            int r6 = org.telegram.messenger.AndroidUtilities.m1146dp(r6)
            float r0 = (float) r6
            float r4 = r4 / r0
            float r3 = r3 / r4
            int r4 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r4 >= 0) goto L88
            int r1 = (int) r3
            goto L88
        L87:
            r6 = r0
        L88:
            org.telegram.ui.Components.Point r3 = new org.telegram.ui.Components.Point
            float r4 = (float) r1
            float r5 = (float) r6
            r3.<init>(r4, r5)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.getMessageSize(int, int, int, int):org.telegram.ui.Components.Point");
    }

    public StaticLayout getDescriptionlayout() {
        return this.descriptionLayout;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00c3  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00fb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public float getDescriptionLayoutX() {
        /*
            Method dump skipped, instructions count: 268
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.getDescriptionLayoutX():float");
    }

    public float getDescriptionLayoutY() {
        float f = this.descriptionY;
        TransitionParams transitionParams = this.transitionParams;
        return f + (!transitionParams.animateLinkPreviewY ? (-transitionParams.deltaTop) + transitionParams.deltaBottom : 0.0f);
    }

    public StaticLayout getFactCheckLayout() {
        return this.factCheckTextLayout;
    }

    public void setSelectedBackgroundProgress(float f) {
        this.selectedBackgroundProgress = f;
        invalidate();
    }

    public int computeHeight(MessageObject messageObject, MessageObject.GroupedMessages groupedMessages, boolean z) {
        this.photoImage.setIgnoreImageSet(true);
        this.avatarImage.setIgnoreImageSet(true);
        this.replyImageReceiver.setIgnoreImageSet(true);
        this.locationImageReceiver.setIgnoreImageSet(true);
        if (groupedMessages != null && groupedMessages.messages.size() != 1) {
            if (groupedMessages.messages.size() != groupedMessages.positions.size()) {
                groupedMessages.calculate();
            }
            this.computedGroupCaptionY = 0;
            int i = 0;
            for (int i2 = 0; i2 < groupedMessages.messages.size(); i2++) {
                MessageObject messageObject2 = groupedMessages.messages.get(i2);
                MessageObject.GroupedMessagePosition position = groupedMessages.getPosition(messageObject2);
                if (position != null && (position.flags & 1) != 0) {
                    setMessageContent(messageObject2, groupedMessages, false, false, false, false);
                    if (z && !TextUtils.isEmpty(this.currentCaption)) {
                        updateCaptionLayout();
                        this.computedGroupCaptionY = (int) (i + this.captionY);
                        this.computedCaptionLayout = this.captionLayout;
                    }
                    i += this.totalHeight + this.keyboardHeight;
                }
            }
            return i;
        }
        setMessageContent(messageObject, groupedMessages, false, false, false, false);
        this.photoImage.setIgnoreImageSet(false);
        this.avatarImage.setIgnoreImageSet(false);
        this.replyImageReceiver.setIgnoreImageSet(false);
        this.locationImageReceiver.setIgnoreImageSet(false);
        updateCaptionLayout();
        return this.totalHeight + this.keyboardHeight;
    }

    public int computeWidth(MessageObject messageObject, MessageObject.GroupedMessages groupedMessages) throws Resources.NotFoundException {
        this.photoImage.setIgnoreImageSet(true);
        this.avatarImage.setIgnoreImageSet(true);
        this.replyImageReceiver.setIgnoreImageSet(true);
        this.locationImageReceiver.setIgnoreImageSet(true);
        if (groupedMessages != null && groupedMessages.messages.size() != 1) {
            if (groupedMessages.messages.size() != groupedMessages.positions.size()) {
                groupedMessages.calculate();
            }
            this.computedGroupCaptionY = 0;
            int i = 0;
            for (int i2 = 0; i2 < groupedMessages.messages.size(); i2++) {
                MessageObject messageObject2 = groupedMessages.messages.get(i2);
                MessageObject.GroupedMessagePosition position = groupedMessages.getPosition(messageObject2);
                if (position != null && (position.flags & 4) != 0) {
                    setMessageContent(messageObject2, groupedMessages, false, false, false, false);
                    i += this.backgroundWidth;
                }
            }
            return i;
        }
        setMessageContent(messageObject, groupedMessages, false, false, false, false);
        this.photoImage.setIgnoreImageSet(false);
        this.avatarImage.setIgnoreImageSet(false);
        this.replyImageReceiver.setIgnoreImageSet(false);
        this.locationImageReceiver.setIgnoreImageSet(false);
        updateCaptionLayout();
        return this.backgroundWidth;
    }

    public void shakeView() {
        PropertyValuesHolder propertyValuesHolderOfKeyframe = PropertyValuesHolder.ofKeyframe(View.ROTATION, Keyframe.ofFloat(0.0f, 0.0f), Keyframe.ofFloat(0.2f, 3.0f), Keyframe.ofFloat(0.4f, -3.0f), Keyframe.ofFloat(0.6f, 3.0f), Keyframe.ofFloat(0.8f, -3.0f), Keyframe.ofFloat(1.0f, 0.0f));
        Keyframe keyframeOfFloat = Keyframe.ofFloat(0.0f, 1.0f);
        Keyframe keyframeOfFloat2 = Keyframe.ofFloat(0.5f, 0.97f);
        Keyframe keyframeOfFloat3 = Keyframe.ofFloat(1.0f, 1.0f);
        PropertyValuesHolder propertyValuesHolderOfKeyframe2 = PropertyValuesHolder.ofKeyframe(View.SCALE_X, keyframeOfFloat, keyframeOfFloat2, keyframeOfFloat3);
        PropertyValuesHolder propertyValuesHolderOfKeyframe3 = PropertyValuesHolder.ofKeyframe(View.SCALE_Y, keyframeOfFloat, keyframeOfFloat2, keyframeOfFloat3);
        AnimatorSet animatorSet = new AnimatorSet();
        this.shakeAnimation = animatorSet;
        animatorSet.playTogether(ObjectAnimator.ofPropertyValuesHolder(this, propertyValuesHolderOfKeyframe), ObjectAnimator.ofPropertyValuesHolder(this, propertyValuesHolderOfKeyframe2), ObjectAnimator.ofPropertyValuesHolder(this, propertyValuesHolderOfKeyframe3));
        this.shakeAnimation.setDuration(500L);
        this.shakeAnimation.start();
    }

    private void cancelShakeAnimation() {
        AnimatorSet animatorSet = this.shakeAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.shakeAnimation = null;
            setScaleX(1.0f);
            setScaleY(1.0f);
            setRotation(0.0f);
        }
    }

    public void setSlidingOffset(float f) {
        if (this.slidingOffsetX != f) {
            this.slidingOffsetX = f;
            updateTranslation();
        }
    }

    public void setAnimationOffsetX(float f) {
        if (this.animationOffsetX != f) {
            this.animationOffsetX = f;
            updateTranslation();
        }
    }

    public void updateTranslation() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return;
        }
        float fM1146dp = this.slidingOffsetX + this.animationOffsetX;
        if (!messageObject.isOutOwner() || this.currentMessageObject.hasWideCode) {
            fM1146dp += this.checkBoxTranslation;
        }
        if (this.isSideMenued && !this.currentMessageObject.isOutOwner() && this.currentPosition != null) {
            fM1146dp += AndroidUtilities.m1146dp(71 - (needDrawAvatar() ? 48 : 0)) * this.sideMenuAlpha;
        }
        setTranslationX(fM1146dp);
    }

    public float getNonAnimationTranslationX(boolean z) {
        boolean z2;
        float f = this.slidingOffsetX;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.isOutOwner()) {
            return f;
        }
        if (z && ((z2 = this.checkBoxVisible) || this.checkBoxAnimationInProgress)) {
            this.checkBoxTranslation = (int) Math.ceil((z2 ? CubicBezierInterpolator.EASE_OUT : CubicBezierInterpolator.EASE_IN).getInterpolation(this.checkBoxAnimationProgress) * AndroidUtilities.m1146dp(35.0f));
        }
        float f2 = f + this.checkBoxTranslation;
        if (!this.isSideMenued || this.currentPosition == null) {
            return f2;
        }
        return f2 + (AndroidUtilities.m1146dp(71 - (needDrawAvatar() ? 48 : 0)) * this.sideMenuAlpha);
    }

    public float getSlidingOffsetX() {
        return this.slidingOffsetX;
    }

    public boolean willRemovedAfterAnimation() {
        return this.willRemoved;
    }

    public float getAnimationOffsetX() {
        return this.animationOffsetX;
    }

    @Override // android.view.View
    public void setTranslationX(float f) {
        super.setTranslationX(f);
    }

    public SeekBar getSeekBar() {
        return this.seekBar;
    }

    public SeekBarWaveform getSeekBarWaveform() {
        return this.seekBarWaveform;
    }

    private class MessageAccessibilityNodeProvider extends AccessibilityNodeProvider {
        private Path linkPath;
        private Rect rect;
        private RectF rectF;

        private MessageAccessibilityNodeProvider() {
            this.linkPath = new Path();
            this.rectF = new RectF();
            this.rect = new Rect();
        }

        /* loaded from: classes5.dex */
        private class ProfileSpan extends ClickableSpan {
            private TLRPC.User user;

            public ProfileSpan(TLRPC.User user) {
                this.user = user;
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                if (ChatMessageCell.this.delegate != null) {
                    ChatMessageCell.this.delegate.didPressUserAvatar(ChatMessageCell.this, this.user, 0.0f, 0.0f, false);
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:582:0x13e5  */
        /* JADX WARN: Removed duplicated region for block: B:583:0x13e7  */
        /* JADX WARN: Removed duplicated region for block: B:590:0x13fb  */
        @Override // android.view.accessibility.AccessibilityNodeProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public android.view.accessibility.AccessibilityNodeInfo createAccessibilityNodeInfo(int r25) {
            /*
                Method dump skipped, instructions count: 5261
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.MessageAccessibilityNodeProvider.createAccessibilityNodeInfo(int):android.view.accessibility.AccessibilityNodeInfo");
        }

        /* JADX WARN: Removed duplicated region for block: B:104:0x021d  */
        @Override // android.view.accessibility.AccessibilityNodeProvider
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean performAction(int r10, int r11, android.os.Bundle r12) throws android.content.res.Resources.NotFoundException {
            /*
                Method dump skipped, instructions count: 776
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.MessageAccessibilityNodeProvider.performAction(int, int, android.os.Bundle):boolean");
        }

        private ClickableSpan getLinkById(int i, boolean z) {
            if (i == 5000) {
                return null;
            }
            if (z) {
                int i2 = i - 3000;
                if (!(ChatMessageCell.this.currentMessageObject.caption instanceof Spannable) || i2 < 0) {
                    return null;
                }
                Spannable spannable = (Spannable) ChatMessageCell.this.currentMessageObject.caption;
                ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(0, spannable.length(), ClickableSpan.class);
                if (clickableSpanArr.length <= i2) {
                    return null;
                }
                return clickableSpanArr[i2];
            }
            int i3 = i - 2000;
            if (!(ChatMessageCell.this.currentMessageObject.messageText instanceof Spannable) || i3 < 0) {
                return null;
            }
            Spannable spannable2 = (Spannable) ChatMessageCell.this.currentMessageObject.messageText;
            ClickableSpan[] clickableSpanArr2 = (ClickableSpan[]) spannable2.getSpans(0, spannable2.length(), ClickableSpan.class);
            if (clickableSpanArr2.length <= i3) {
                return null;
            }
            return clickableSpanArr2[i3];
        }
    }

    public void setImageCoords(RectF rectF) {
        setImageCoords(rectF.left, rectF.top, rectF.width(), rectF.height());
    }

    public void setImageCoords(float f, float f2, float f3, float f4) {
        this.photoImage.setImageCoords(f, f2, f3, f4);
        int i = this.documentAttachType;
        if (i == 4 || i == 2) {
            this.videoButtonX = (int) (this.photoImage.getImageX() + AndroidUtilities.m1146dp(8.0f));
            int imageY = (int) (this.photoImage.getImageY() + AndroidUtilities.m1146dp(8.0f));
            this.videoButtonY = imageY;
            RadialProgress2 radialProgress2 = this.videoRadialProgress;
            int i2 = this.videoButtonX;
            radialProgress2.setProgressRect(i2, imageY, AndroidUtilities.m1146dp(24.0f) + i2, this.videoButtonY + AndroidUtilities.m1146dp(24.0f));
            this.buttonX = (int) (f + ((this.photoImage.getImageWidth() - AndroidUtilities.m1146dp(48.0f)) / 2.0f));
            int imageY2 = (int) (this.photoImage.getImageY() + ((this.photoImage.getImageHeight() - AndroidUtilities.m1146dp(48.0f)) / 2.0f));
            this.buttonY = imageY2;
            RadialProgress2 radialProgress22 = this.radialProgress;
            int i3 = this.buttonX;
            radialProgress22.setProgressRect(i3, imageY2, AndroidUtilities.m1146dp(48.0f) + i3, this.buttonY + AndroidUtilities.m1146dp(48.0f));
        }
    }

    @Override // android.view.View
    public float getAlpha() {
        if (this.ALPHA_PROPERTY_WORKAROUND) {
            return this.alphaInternal;
        }
        return super.getAlpha();
    }

    @Override // android.view.View
    @Keep
    public void setAlpha(float f) {
        if (AyuConfig.semiTransparentDeletedMessages && !this.ignoreDeletedAlpha && this.ayuDeleted && this.ayuDeletedAnimation == null && f > 0.75f) {
            setAlpha(0.7f);
            return;
        }
        if ((f == 1.0f) != (getAlpha() == 1.0f)) {
            invalidate();
        }
        if (this.ALPHA_PROPERTY_WORKAROUND) {
            this.alphaInternal = f;
            invalidate();
        } else {
            super.setAlpha(f);
        }
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if ((groupedMessagePosition != null && (groupedMessagePosition.minY != 0 || groupedMessagePosition.minX != 0)) || ((this.enterTransitionInProgress && !this.currentMessageObject.isVoice()) || this.replyNameLayout == null || this.replyTextLayout == null)) {
            MessageObject.GroupedMessagePosition groupedMessagePosition2 = this.currentPosition;
            if (groupedMessagePosition2 != null) {
                int i = groupedMessagePosition2.flags;
                if ((i & 8) == 0 || (i & 1) == 0) {
                    return;
                }
            }
            if (this.reactionsLayoutInBubble.isSmall) {
                return;
            }
        }
        invalidate();
    }

    public int getCurrentBackgroundLeft() {
        Theme.MessageDrawable messageDrawable = this.currentBackgroundDrawable;
        if (messageDrawable == null) {
            return 0;
        }
        int i = messageDrawable.getBounds().left;
        if (this.currentMessageObject.isOutOwner() || this.transitionParams.changePinnedBottomProgress == 1.0f) {
            return i;
        }
        boolean z = this.isRoundVideo;
        if ((!z && this.mediaBackground) || this.drawPinnedBottom) {
            return i;
        }
        if (z) {
            return (int) (i - (AndroidUtilities.m1146dp(6.0f) * getVideoTranscriptionProgress()));
        }
        return i - AndroidUtilities.m1146dp(6.0f);
    }

    public int getCurrentBackgroundRight() {
        Theme.MessageDrawable messageDrawable = this.currentBackgroundDrawable;
        if (messageDrawable == null) {
            return getWidth();
        }
        int i = messageDrawable.getBounds().right;
        if (!this.currentMessageObject.isOutOwner() || this.transitionParams.changePinnedBottomProgress == 1.0f) {
            return i;
        }
        boolean z = this.isRoundVideo;
        if ((!z && this.mediaBackground) || this.drawPinnedBottom) {
            return i;
        }
        if (z) {
            return (int) (i + (AndroidUtilities.m1146dp(6.0f) * getVideoTranscriptionProgress()));
        }
        return i + AndroidUtilities.m1146dp(6.0f);
    }

    public float getPaddingTopAnimated() {
        return getTopicSeparatorTopPadding() + this.starsPriceTopPadding + this.suggestionOfferTopPadding;
    }

    public TransitionParams getTransitionParams() {
        return this.transitionParams;
    }

    public int getTopMediaOffset() {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || messageObject.type != 14) {
            return 0;
        }
        return this.mediaOffsetY + this.namesOffset;
    }

    public int getMediaOffsetY() {
        if (this.transitionParams.animateMediaOffsetY) {
            return AndroidUtilities.lerp(this.transitionParams.animateFromMediaOffsetY, this.mediaOffsetY, this.transitionParams.animateChangeProgress);
        }
        return this.mediaOffsetY;
    }

    public int getTextX() {
        return this.textX;
    }

    public int getTextY() {
        return this.textY;
    }

    public boolean isPlayingRound() {
        return this.isRoundVideo && this.isPlayingRound;
    }

    public int getParentWidth() {
        int i;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            messageObject = this.messageObjectToSet;
        }
        return (messageObject == null || !messageObject.preview || (i = this.parentWidth) <= 0) ? AndroidUtilities.displaySize.x : i;
    }

    public class TransitionParams {
        public boolean animateBackgroundBoundsInner;
        public boolean animateBackgroundWidth;
        boolean animateBotButtonsChanged;
        private boolean animateButton;
        public boolean animateChange;
        private int animateCommentArrowX;
        private boolean animateCommentDrawUnread;
        private int animateCommentUnreadX;
        private float animateCommentX;
        private boolean animateComments;
        private StaticLayout animateCommentsLayout;
        public boolean animateDrawAvatar;
        public boolean animateDrawBackground;
        private boolean animateDrawCommentNumber;
        public boolean animateDrawNameLayout;
        public boolean animateDrawTopic;
        public boolean animateDrawingSideMenuEnabled;
        public boolean animateDrawingTimeAlpha;
        private boolean animateEditedEnter;
        private StaticLayout animateEditedLayout;
        private int animateEditedWidthDiff;
        public boolean animateExpandedQuotes;
        public HashSet animateExpandedQuotesFrom;
        public boolean animateFactCheck;
        public boolean animateFactCheckExpanded;
        public boolean animateFactCheckHeight;
        public int animateFactCheckHeightFrom;
        int animateForwardNameWidth;
        float animateForwardNameX;
        public boolean animateForwardedLayout;
        public int animateForwardedNamesOffset;
        private float animateFromButtonX;
        private float animateFromButtonY;
        public int animateFromLinkPreviewHeight;
        public int animateFromLinkPreviewY;
        private int animateFromMediaOffsetY;
        public boolean animateFromRecommendationsExpanded;
        private float animateFromReplyTextHeight;
        public float animateFromReplyY;
        public float animateFromRoundVideoDotY;
        public float animateFromTextY;
        public int animateFromTimeX;
        public float animateFromTimeXPinned;
        private float animateFromTimeXReplies;
        private float animateFromTimeXViews;
        public int animateFromWidthForButton;
        public boolean animateLinkAbove;
        public boolean animateLinkPreviewHeight;
        public boolean animateLinkPreviewY;
        public boolean animateLocationIsExpired;
        public boolean animateMediaAbove;
        private boolean animateMediaOffsetY;
        public boolean animateMessageText;
        public boolean animateMonoforumPadding;
        public int animateMonoforumPaddingFrom;
        private float animateNameX;
        public boolean animateNamesOffset;
        public int animateNamesOffsetFrom;
        private AnimatedEmojiSpan.EmojiGroupedSpans animateOutAnimateEmoji;
        private AnimatedEmojiSpan.EmojiGroupedSpans animateOutAnimateEmojiReply;
        private MessageObject.TextLayoutBlocks animateOutCaptionLayout;
        private ArrayList animateOutTextBlocks;
        private float animateOutTextXOffset;
        private boolean animatePinned;
        public boolean animatePlayingRound;
        public boolean animateRadius;
        public boolean animateRecommendationsExpanded;
        boolean animateReplaceCaptionLayout;
        private boolean animateReplies;
        private StaticLayout animateRepliesLayout;
        private StaticLayout animateReplyTextLayout;
        public float animateReplyTextOffset;
        public boolean animateReplyY;
        public boolean animateRoundVideoDotY;
        private boolean animateShouldDrawMenuDrawable;
        private boolean animateShouldDrawTimeOnMedia;
        private boolean animateSign;
        public boolean animateSmallImage;
        public boolean animateText;
        private StaticLayout animateTimeLayout;
        private int animateTimeWidth;
        public StaticLayout animateTitleLayout;
        private AnimatedEmojiSpan.EmojiGroupedSpans animateTitleLayoutEmoji;
        public float animateToImageH;
        public float animateToImageW;
        public float animateToImageX;
        public float animateToImageY;
        public int[] animateToRadius;
        private int animateTotalCommentWidth;
        public boolean animateUseTranscribeButton;
        private StaticLayout animateViewsLayout;
        public boolean animateWidthForButton;
        public float captionFromX;
        public float captionFromY;
        public float deltaBottom;
        public float deltaLeft;
        public float deltaRight;
        public float deltaTop;
        public boolean drawPinnedBottomBackground;
        public boolean ignoreAlpha;
        public boolean imageChangeBoundsTransition;
        public int lastBackgroundLeft;
        public int lastBackgroundRight;
        public float lastButtonX;
        public float lastButtonY;
        public int lastCommentArrowX;
        public boolean lastCommentDrawUnread;
        public StaticLayout lastCommentLayout;
        public int lastCommentUnreadX;
        public float lastCommentX;
        public int lastCommentsCount;
        public boolean lastDrawAvatar;
        public boolean lastDrawBackground;
        public boolean lastDrawCommentNumber;
        public StaticLayout lastDrawDocTitleLayout;
        public StaticLayout lastDrawInfoLayout;
        public float lastDrawLocationExpireProgress;
        public String lastDrawLocationExpireText;
        public boolean lastDrawNameLayout;
        public float lastDrawReplyY;
        public float lastDrawRoundVideoDotY;
        public boolean lastDrawTime;
        public boolean lastDrawTopic;
        public MessageObject.TextLayoutBlocks lastDrawingCaptionLayout;
        public float lastDrawingCaptionX;
        public float lastDrawingCaptionY;
        public boolean lastDrawingEdited;
        public HashSet lastDrawingExpandedQuotes;
        public boolean lastDrawingFactCheck;
        public boolean lastDrawingFactCheckExpanded;
        public int lastDrawingFactCheckHeight;
        public float lastDrawingImageH;
        public float lastDrawingImageW;
        public float lastDrawingImageX;
        public float lastDrawingImageY;
        public boolean lastDrawingLinkAbove;
        public int lastDrawingLinkPreviewHeight;
        public int lastDrawingLinkPreviewY;
        public boolean lastDrawingMediaAbove;
        public boolean lastDrawingRecommendationsExpanded;
        public float lastDrawingReplyTextHeight;
        public boolean lastDrawingSideMenuEnabled;
        public boolean lastDrawingSmallImage;
        public ArrayList lastDrawingTextBlocks;
        private int lastDrawingTextWidth;
        public float lastDrawingTextX;
        public float lastDrawingTextY;
        public boolean lastDrawnForwardedName;
        public int lastDrawnMonoforumPadding;
        public StaticLayout lastDrawnReplyTextLayout;
        public StaticLayout lastDrawnTitleLayout;
        public boolean lastDrawnTranslated;
        public int lastDrawnWidthForButtons;
        public int lastForwardNameWidth;
        public float lastForwardNameX;
        public int lastForwardedNamesOffset;
        public boolean lastIsPinned;
        public boolean lastIsPlayingRound;
        public boolean lastLocatinIsExpired;
        public int lastMediaOffsetY;
        public int lastNamesOffset;
        public int lastRepliesCount;
        public StaticLayout lastRepliesLayout;
        public int lastReplyTextXOffset;
        public boolean lastShouldDrawMenuDrawable;
        public boolean lastShouldDrawTimeOnMedia;
        public String lastSignMessage;
        public float lastTextXOffset;
        public StaticLayout lastTimeLayout;
        public int lastTimeWidth;
        public int lastTimeX;
        public float lastTimeXPinned;
        private float lastTimeXReplies;
        private float lastTimeXViews;
        public int lastTopOffset;
        public int lastTotalCommentWidth;
        public boolean lastUseTranscribeButton;
        public int lastViewsCount;
        public StaticLayout lastViewsLayout;
        public boolean messageEntering;
        private boolean moveCaption;
        public boolean needsStopClipping;
        public float photoImageFromCenterX;
        public float photoImageFromCenterY;
        public float photoImageFromHeight;
        public float photoImageFromWidth;
        public boolean shouldAnimateTimeX;
        public float toDeltaLeft;
        public float toDeltaRight;
        public boolean transformGroupToSingleMessage;
        public boolean updatePhotoImageX;
        public boolean wasDraw;
        public int[] imageRoundRadius = new int[4];
        public float captionEnterProgress = 1.0f;
        public float changePinnedBottomProgress = 1.0f;
        public Rect lastDrawingBackgroundRect = new Rect();
        public float animateChangeProgress = 1.0f;
        private ArrayList lastDrawBotButtons = new ArrayList();
        private ArrayList transitionBotButtons = new ArrayList();
        public int lastStatusDrawableParams = -1;
        public StaticLayout[] lastDrawnForwardedNameLayout = new StaticLayout[2];
        public StaticLayout[] animatingForwardedNameLayout = new StaticLayout[2];

        public boolean supportChangeAnimation() {
            return true;
        }

        public TransitionParams() {
        }

        public void recordDrawingState() {
            ChannelRecommendationsCell channelRecommendationsCell;
            this.wasDraw = true;
            this.lastDrawingImageX = ChatMessageCell.this.photoImage.getImageX();
            this.lastDrawingImageY = ChatMessageCell.this.photoImage.getImageY();
            this.lastDrawingImageW = ChatMessageCell.this.photoImage.getImageWidth();
            this.lastDrawingImageH = ChatMessageCell.this.photoImage.getImageHeight();
            System.arraycopy(ChatMessageCell.this.photoImage.getRoundRadius(), 0, this.imageRoundRadius, 0, 4);
            Theme.MessageDrawable messageDrawable = ChatMessageCell.this.currentBackgroundDrawable;
            if (messageDrawable != null) {
                this.lastDrawingBackgroundRect.set(messageDrawable.getBounds());
            }
            ChatMessageCell chatMessageCell = ChatMessageCell.this;
            this.lastDrawingSideMenuEnabled = chatMessageCell.isSideMenuEnabled;
            this.lastDrawingTextBlocks = chatMessageCell.currentMessageObject != null ? ChatMessageCell.this.currentMessageObject.textLayoutBlocks : null;
            this.lastDrawingTextWidth = ChatMessageCell.this.currentMessageObject != null ? ChatMessageCell.this.currentMessageObject.textWidth : 0;
            this.lastDrawingEdited = ChatMessageCell.this.edited || ChatMessageCell.this.ayuDeleted;
            this.lastDrawingCaptionX = ChatMessageCell.this.captionX;
            ChatMessageCell chatMessageCell2 = ChatMessageCell.this;
            this.lastDrawingCaptionY = chatMessageCell2.captionY;
            this.lastDrawingCaptionLayout = chatMessageCell2.captionLayout;
            this.lastDrawBotButtons.clear();
            if (!ChatMessageCell.this.botButtons.isEmpty()) {
                this.lastDrawBotButtons.addAll(ChatMessageCell.this.botButtons);
            }
            this.lastDrawingSmallImage = ChatMessageCell.this.isSmallImage;
            ChatMessageCell chatMessageCell3 = ChatMessageCell.this;
            this.lastDrawnMonoforumPadding = chatMessageCell3.topicSeparatorTopPadding;
            this.lastDrawingLinkPreviewHeight = chatMessageCell3.linkPreviewHeight;
            this.lastDrawingLinkAbove = chatMessageCell3.linkPreviewAbove;
            this.lastDrawingMediaAbove = chatMessageCell3.captionAbove;
            this.lastDrawingRecommendationsExpanded = chatMessageCell3.currentMessageObject != null && ChatMessageCell.this.currentMessageObject.type == 27 && (channelRecommendationsCell = ChatMessageCell.this.channelRecommendationsCell) != null && channelRecommendationsCell.isExpanded();
            if (ChatMessageCell.this.commentLayout != null) {
                this.lastCommentsCount = ChatMessageCell.this.getRepliesCount();
                this.lastTotalCommentWidth = ChatMessageCell.this.totalCommentWidth;
                this.lastCommentLayout = ChatMessageCell.this.commentLayout;
                this.lastCommentArrowX = ChatMessageCell.this.commentArrowX;
                this.lastCommentUnreadX = ChatMessageCell.this.commentUnreadX;
                this.lastCommentDrawUnread = ChatMessageCell.this.commentDrawUnread;
                this.lastCommentX = ChatMessageCell.this.commentX;
                this.lastDrawCommentNumber = ChatMessageCell.this.drawCommentNumber;
            }
            this.lastRepliesCount = ChatMessageCell.this.getRepliesCount();
            this.lastViewsCount = ChatMessageCell.this.getMessageObject().messageOwner.views;
            this.lastRepliesLayout = ChatMessageCell.this.repliesLayout;
            this.lastViewsLayout = ChatMessageCell.this.viewsLayout;
            ChatMessageCell chatMessageCell4 = ChatMessageCell.this;
            this.lastIsPinned = chatMessageCell4.isPinned;
            this.lastSignMessage = chatMessageCell4.lastPostAuthor;
            this.lastDrawBackground = ChatMessageCell.this.drawBackground;
            this.lastUseTranscribeButton = ChatMessageCell.this.useTranscribeButton;
            this.lastButtonX = ChatMessageCell.this.buttonX;
            this.lastButtonY = ChatMessageCell.this.buttonY;
            this.lastMediaOffsetY = ChatMessageCell.this.mediaOffsetY;
            this.lastDrawTime = !ChatMessageCell.this.forceNotDrawTime;
            ChatMessageCell chatMessageCell5 = ChatMessageCell.this;
            this.lastTimeX = chatMessageCell5.timeX;
            this.lastTimeLayout = chatMessageCell5.timeLayout;
            this.lastTimeWidth = chatMessageCell5.timeWidth;
            this.lastShouldDrawTimeOnMedia = chatMessageCell5.shouldDrawTimeOnMedia();
            this.lastTopOffset = ChatMessageCell.this.getTopMediaOffset();
            this.lastShouldDrawMenuDrawable = ChatMessageCell.this.shouldDrawMenuDrawable();
            this.lastLocatinIsExpired = ChatMessageCell.this.locationExpired;
            this.lastIsPlayingRound = ChatMessageCell.this.isPlayingRound;
            ChatMessageCell chatMessageCell6 = ChatMessageCell.this;
            this.lastDrawingTextY = chatMessageCell6.textY;
            this.lastDrawingTextX = chatMessageCell6.textX;
            this.lastDrawingLinkPreviewY = chatMessageCell6.linkPreviewY;
            this.lastDrawnWidthForButtons = ChatMessageCell.this.widthForButtons;
            this.lastDrawnForwardedNameLayout[0] = ChatMessageCell.this.forwardedNameLayout[0];
            this.lastDrawnForwardedNameLayout[1] = ChatMessageCell.this.forwardedNameLayout[1];
            this.lastDrawnForwardedName = ChatMessageCell.this.currentMessageObject != null && ChatMessageCell.this.currentMessageObject.needDrawForwarded();
            this.lastForwardNameX = ChatMessageCell.this.forwardNameX;
            ChatMessageCell chatMessageCell7 = ChatMessageCell.this;
            int i = chatMessageCell7.namesOffset;
            this.lastForwardedNamesOffset = i;
            this.lastNamesOffset = i;
            this.lastForwardNameWidth = chatMessageCell7.forwardedNameWidth;
            this.lastBackgroundLeft = ChatMessageCell.this.getCurrentBackgroundLeft();
            Theme.MessageDrawable messageDrawable2 = ChatMessageCell.this.currentBackgroundDrawable;
            if (messageDrawable2 != null) {
                this.lastBackgroundRight = messageDrawable2.getBounds().right;
            }
            this.lastTextXOffset = ChatMessageCell.this.currentMessageObject != null ? ChatMessageCell.this.currentMessageObject.textXOffset : 0.0f;
            this.lastDrawingReplyTextHeight = ChatMessageCell.this.replyTextHeight;
            ChatMessageCell chatMessageCell8 = ChatMessageCell.this;
            this.lastDrawnReplyTextLayout = chatMessageCell8.replyTextLayout;
            this.lastReplyTextXOffset = chatMessageCell8.replyTextOffset;
            chatMessageCell8.reactionsLayoutInBubble.recordDrawingState();
            ChatMessageCell chatMessageCell9 = ChatMessageCell.this;
            if (chatMessageCell9.replyNameLayout != null) {
                this.lastDrawReplyY = chatMessageCell9.replyStartY;
            } else {
                this.lastDrawReplyY = 0.0f;
            }
            this.lastDrawNameLayout = chatMessageCell9.drawNameLayout;
            this.lastDrawAvatar = ChatMessageCell.this.drawNameAvatar;
            this.lastDrawTopic = ChatMessageCell.this.drawTopic;
            this.lastDrawingFactCheckHeight = ChatMessageCell.this.factCheckHeight;
            this.lastDrawingFactCheckExpanded = ChatMessageCell.this.getPrimaryMessageObject() != null && ChatMessageCell.this.getPrimaryMessageObject().factCheckExpanded;
            this.lastDrawingFactCheck = ChatMessageCell.this.hasFactCheck;
            this.lastDrawingExpandedQuotes = ChatMessageCell.this.getPrimaryMessageObject() != null ? ChatMessageCell.this.getPrimaryMessageObject().expandedQuotes : null;
            this.lastDrawnTranslated = ChatMessageCell.this.currentMessageObject != null && ChatMessageCell.this.currentMessageObject.translated;
            this.lastDrawnTitleLayout = ChatMessageCell.this.titleLayout;
        }

        public void recordDrawingStatePreview() {
            this.lastDrawnForwardedNameLayout[0] = ChatMessageCell.this.forwardedNameLayout[0];
            this.lastDrawnForwardedNameLayout[1] = ChatMessageCell.this.forwardedNameLayout[1];
            this.lastDrawnForwardedName = ChatMessageCell.this.currentMessageObject.needDrawForwarded();
            this.lastForwardNameX = ChatMessageCell.this.forwardNameX;
            ChatMessageCell chatMessageCell = ChatMessageCell.this;
            int i = chatMessageCell.namesOffset;
            this.lastForwardedNamesOffset = i;
            this.lastNamesOffset = i;
            this.lastForwardNameWidth = chatMessageCell.forwardedNameWidth;
        }

        /* JADX WARN: Removed duplicated region for block: B:148:0x034c  */
        /* JADX WARN: Removed duplicated region for block: B:164:0x0387  */
        /* JADX WARN: Removed duplicated region for block: B:273:0x05ef  */
        /* JADX WARN: Removed duplicated region for block: B:276:0x05fb  */
        /* JADX WARN: Removed duplicated region for block: B:279:0x0607  */
        /* JADX WARN: Removed duplicated region for block: B:282:0x0615  */
        /* JADX WARN: Removed duplicated region for block: B:285:0x0622  */
        /* JADX WARN: Removed duplicated region for block: B:288:0x0633  */
        /* JADX WARN: Removed duplicated region for block: B:294:0x0650  */
        /* JADX WARN: Removed duplicated region for block: B:296:0x0653  */
        /* JADX WARN: Removed duplicated region for block: B:299:0x0660  */
        /* JADX WARN: Removed duplicated region for block: B:302:0x066d  */
        /* JADX WARN: Removed duplicated region for block: B:305:0x067b  */
        /* JADX WARN: Removed duplicated region for block: B:312:0x06b4  */
        /* JADX WARN: Removed duplicated region for block: B:314:0x06bc  */
        /* JADX WARN: Removed duplicated region for block: B:317:0x06d0  */
        /* JADX WARN: Removed duplicated region for block: B:320:0x06dd  */
        /* JADX WARN: Removed duplicated region for block: B:332:0x070e  */
        /* JADX WARN: Removed duplicated region for block: B:339:0x0727  */
        /* JADX WARN: Removed duplicated region for block: B:342:0x0733  */
        /* JADX WARN: Removed duplicated region for block: B:345:0x0738  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x00ec  */
        /* JADX WARN: Removed duplicated region for block: B:48:0x0112  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean animateChange() {
            /*
                Method dump skipped, instructions count: 1878
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.TransitionParams.animateChange():boolean");
        }

        public void onDetach() {
            this.wasDraw = false;
        }

        public void resetAnimation() {
            this.animateChange = false;
            this.animatePinned = false;
            this.animateBackgroundBoundsInner = false;
            this.animateBackgroundWidth = false;
            this.deltaLeft = 0.0f;
            this.deltaRight = 0.0f;
            this.deltaBottom = 0.0f;
            this.deltaTop = 0.0f;
            this.toDeltaLeft = 0.0f;
            this.toDeltaRight = 0.0f;
            if (this.imageChangeBoundsTransition && this.animateToImageW != 0.0f && this.animateToImageH != 0.0f) {
                ChatMessageCell.this.photoImage.setImageCoords(this.animateToImageX, this.animateToImageY, this.animateToImageW, this.animateToImageH);
            }
            if (this.animateRadius) {
                ChatMessageCell.this.photoImage.setRoundRadius(this.animateToRadius);
            }
            this.animateToImageX = 0.0f;
            this.animateToImageY = 0.0f;
            this.animateToImageW = 0.0f;
            this.animateToImageH = 0.0f;
            this.imageChangeBoundsTransition = false;
            this.changePinnedBottomProgress = 1.0f;
            this.captionEnterProgress = 1.0f;
            this.animateRadius = false;
            this.animateChangeProgress = 1.0f;
            this.animateMessageText = false;
            this.animateDrawingSideMenuEnabled = false;
            this.animateDrawNameLayout = false;
            this.animateDrawAvatar = false;
            this.animateDrawTopic = false;
            this.animateOutTextBlocks = null;
            this.animateEditedLayout = null;
            this.animateTimeLayout = null;
            this.animateEditedEnter = false;
            this.animateReplaceCaptionLayout = false;
            this.transformGroupToSingleMessage = false;
            this.animateOutCaptionLayout = null;
            AnimatedEmojiSpan.release(ChatMessageCell.this, this.animateOutAnimateEmoji);
            this.animateOutAnimateEmoji = null;
            this.moveCaption = false;
            this.animateDrawingTimeAlpha = false;
            this.transitionBotButtons.clear();
            this.animateButton = false;
            this.animateBotButtonsChanged = false;
            this.animateWidthForButton = false;
            this.animateMediaOffsetY = false;
            this.animateReplyTextLayout = null;
            this.animateReplies = false;
            this.animateRepliesLayout = null;
            this.animateComments = false;
            this.animateCommentsLayout = null;
            this.animateViewsLayout = null;
            this.animateShouldDrawTimeOnMedia = false;
            this.animateShouldDrawMenuDrawable = false;
            this.shouldAnimateTimeX = false;
            this.animateDrawBackground = false;
            this.animateSign = false;
            this.animateSmallImage = false;
            this.animateMonoforumPadding = false;
            this.needsStopClipping = false;
            this.animateLinkAbove = false;
            this.animateMediaAbove = false;
            this.animateRecommendationsExpanded = false;
            this.animateDrawingTimeAlpha = false;
            this.animateLocationIsExpired = false;
            this.animatePlayingRound = false;
            this.animateText = false;
            this.animateLinkPreviewY = false;
            this.animateFactCheckHeight = false;
            this.animateFactCheckExpanded = false;
            this.animateExpandedQuotes = false;
            this.animateFactCheck = false;
            this.animateForwardedLayout = false;
            this.animateNamesOffset = false;
            StaticLayout[] staticLayoutArr = this.animatingForwardedNameLayout;
            staticLayoutArr[0] = null;
            staticLayoutArr[1] = null;
            this.animateRoundVideoDotY = false;
            this.animateReplyY = false;
            ChatMessageCell.this.reactionsLayoutInBubble.resetAnimation();
            this.animateTitleLayout = null;
            AnimatedEmojiSpan.release(ChatMessageCell.this, this.animateTitleLayoutEmoji);
        }

        /* JADX WARN: Removed duplicated region for block: B:28:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0085  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x0087  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x008c  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x0091  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public int createStatusDrawableParams() {
            /*
                r8 = this;
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r0)
                boolean r0 = r0.isOutOwner()
                r1 = 8
                r2 = 4
                r3 = 1
                r4 = 0
                if (r0 == 0) goto L94
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r0)
                boolean r0 = r0.isSending()
                if (r0 != 0) goto L66
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r0)
                boolean r0 = r0.isEditing()
                if (r0 == 0) goto L2a
                goto L66
            L2a:
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r0)
                boolean r0 = r0.isSendError()
                if (r0 == 0) goto L3b
                r0 = 0
                r3 = 0
                r5 = 0
                r6 = 1
                goto L6a
            L3b:
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r0)
                boolean r0 = r0.isSent()
                if (r0 == 0) goto L63
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r0)
                boolean r0 = r0.scheduled
                if (r0 != 0) goto L5f
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r0)
                boolean r0 = r0.isUnread()
                if (r0 != 0) goto L5f
                r0 = 1
                goto L60
            L5f:
                r0 = 0
            L60:
                r5 = 0
            L61:
                r6 = 0
                goto L6a
            L63:
                r0 = 0
                r3 = 0
                goto L60
            L66:
                r0 = 0
                r3 = 0
                r5 = 1
                goto L61
            L6a:
                org.telegram.ui.Cells.ChatMessageCell r7 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r7 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r7)
                boolean r7 = r7.notime
                if (r7 != 0) goto L80
                org.telegram.ui.Cells.ChatMessageCell r7 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r7 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r7)
                boolean r7 = r7.isQuickReply()
                if (r7 == 0) goto L83
            L80:
                r0 = 0
                r3 = 0
                r5 = 0
            L83:
                if (r3 == 0) goto L87
                r3 = 2
                goto L88
            L87:
                r3 = 0
            L88:
                r0 = r0 | r3
                if (r5 == 0) goto L8c
                goto L8d
            L8c:
                r2 = 0
            L8d:
                r0 = r0 | r2
                if (r6 == 0) goto L91
                goto L92
            L91:
                r1 = 0
            L92:
                r0 = r0 | r1
                return r0
            L94:
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r0)
                boolean r0 = r0.isSending()
                if (r0 != 0) goto Lae
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r0)
                boolean r0 = r0.isEditing()
                if (r0 == 0) goto Lad
                goto Lae
            Lad:
                r3 = 0
            Lae:
                org.telegram.ui.Cells.ChatMessageCell r0 = org.telegram.p023ui.Cells.ChatMessageCell.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.Cells.ChatMessageCell.m5721$$Nest$fgetcurrentMessageObject(r0)
                boolean r0 = r0.isSendError()
                if (r3 == 0) goto Lbb
                goto Lbc
            Lbb:
                r2 = 0
            Lbc:
                if (r0 == 0) goto Lbf
                goto Lc0
            Lbf:
                r1 = 0
            Lc0:
                r0 = r2 | r1
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.TransitionParams.createStatusDrawableParams():int");
        }
    }

    public int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    private Drawable getThemedDrawable(String str) {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        Drawable drawable = resourcesProvider != null ? resourcesProvider.getDrawable(str) : null;
        return drawable != null ? drawable : Theme.getThemeDrawable(str);
    }

    public Paint getThemedPaint(String str) {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        Paint paint = resourcesProvider != null ? resourcesProvider.getPaint(str) : null;
        return paint != null ? paint : Theme.getThemePaint(str);
    }

    public boolean hasGradientService() {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        return resourcesProvider != null ? resourcesProvider.hasGradientService() : Theme.hasGradientService();
    }

    private ColorMatrixColorFilter getFancyBlurFilter() {
        if (this.fancyBlurFilter == null) {
            ColorMatrix colorMatrix = new ColorMatrix();
            AndroidUtilities.multiplyBrightnessColorMatrix(colorMatrix, 0.9f);
            AndroidUtilities.adjustSaturationColorMatrix(colorMatrix, 0.6f);
            this.fancyBlurFilter = new ColorMatrixColorFilter(colorMatrix);
        }
        return this.fancyBlurFilter;
    }

    public int getNameStatusX() {
        return (int) (this.nameX + this.nameOffsetX + (this.viaNameWidth > 0 ? r1 - AndroidUtilities.m1146dp(32.0f) : this.nameLayoutWidth) + AndroidUtilities.m1146dp(2.0f) + (AndroidUtilities.m1146dp(20.0f) / 2));
    }

    public int getNameStatusY() {
        return (int) (this.nameY + ((this.nameLayout == null ? 0 : r1.getHeight()) / 2));
    }

    @Override // android.view.View
    public void computeScroll() {
        super.computeScroll();
        ChannelRecommendationsCell channelRecommendationsCell = this.channelRecommendationsCell;
        if (channelRecommendationsCell != null) {
            channelRecommendationsCell.computeScroll();
        }
    }

    private ColorFilter getAdaptiveEmojiColorFilter(int i, int i2) {
        if (this.adaptiveEmojiColorFilter == null) {
            this.adaptiveEmojiColor = new int[3];
            this.adaptiveEmojiColorFilter = new ColorFilter[3];
        }
        if (i2 != this.adaptiveEmojiColor[i] || this.adaptiveEmojiColorFilter[i] == null) {
            ColorFilter[] colorFilterArr = this.adaptiveEmojiColorFilter;
            this.adaptiveEmojiColor[i] = i2;
            colorFilterArr[i] = new PorterDuffColorFilter(i2, PorterDuff.Mode.SRC_IN);
        }
        return this.adaptiveEmojiColorFilter[i];
    }

    public boolean needDrawAvatar() {
        MessageObject messageObject;
        if (this.isChat && !this.isSavedPreviewChat && ((!this.isThreadPost || this.isForum) && (messageObject = this.currentMessageObject) != null && !messageObject.isOutOwner() && this.currentMessageObject.needDrawAvatar())) {
            return true;
        }
        MessageObject messageObject2 = this.currentMessageObject;
        if (messageObject2 != null && messageObject2.getDialogId() == UserObject.VERIFY) {
            return true;
        }
        MessageObject messageObject3 = this.currentMessageObject;
        return messageObject3 != null && messageObject3.forceAvatar;
    }

    protected boolean drawPhotoImage(Canvas canvas) {
        return this.photoImage.draw(canvas);
    }

    public boolean areTags() {
        TLRPC.Message message;
        TLRPC.TL_messageReactions tL_messageReactions;
        MessageObject primaryMessageObject = getPrimaryMessageObject();
        if (primaryMessageObject == null || (message = primaryMessageObject.messageOwner) == null || (tL_messageReactions = message.reactions) == null) {
            return false;
        }
        return tL_messageReactions.reactions_as_tags;
    }

    public String getFilename() {
        int i;
        int i2;
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return null;
        }
        int i3 = messageObject.type;
        if (i3 == 1) {
            TLRPC.PhotoSize photoSize = this.currentPhotoObject;
            if (photoSize == null) {
                return null;
            }
            return FileLoader.getAttachFileName(photoSize);
        }
        if (i3 == 8 || (i2 = this.documentAttachType) == 7 || i2 == 4 || i2 == 8 || i3 == 9 || i2 == 3 || i2 == 5) {
            if (messageObject.useCustomPhoto) {
                return null;
            }
            if (messageObject.attachPathExists && !TextUtils.isEmpty(messageObject.messageOwner.attachPath)) {
                return this.currentMessageObject.messageOwner.attachPath;
            }
            if (!this.currentMessageObject.isSendError() || (i = this.documentAttachType) == 3 || i == 5) {
                return this.currentMessageObject.getFileName();
            }
        } else {
            if (i2 != 0) {
                return FileLoader.getAttachFileName(this.documentAttach);
            }
            TLRPC.PhotoSize photoSize2 = this.currentPhotoObject;
            if (photoSize2 != null) {
                return FileLoader.getAttachFileName(photoSize2);
            }
        }
        return null;
    }

    public boolean checkLoadCaughtPremiumFloodWait() {
        return FileLoader.getInstance(this.currentAccount).checkLoadCaughtPremiumFloodWait(getFilename());
    }

    public boolean checkUploadCaughtPremiumFloodWait() {
        return FileLoader.getInstance(this.currentAccount).checkUploadCaughtPremiumFloodWait(getFilename());
    }

    public TLRPC.TL_availableEffect getEffect() {
        MessageObject messageObject;
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if ((groupedMessagePosition == null || groupedMessagePosition.last) && (messageObject = this.currentMessageObject) != null) {
            return messageObject.getEffect();
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x01e6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int layoutFactCheck(int r28) {
        /*
            Method dump skipped, instructions count: 557
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Cells.ChatMessageCell.layoutFactCheck(int):int");
    }

    public int getWidthForButtons() {
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateWidthForButton) {
            return AndroidUtilities.lerp(transitionParams.animateFromWidthForButton, this.widthForButtons, transitionParams.animateChangeProgress);
        }
        return this.widthForButtons;
    }

    public void drawVideoTimestamps(Canvas canvas, int i) {
        float videoSavedProgress;
        if (this.currentMessageObject == null || this.controlsAlpha <= 0.0f || !this.photoImage.getVisible()) {
            return;
        }
        MessageObject messageObject = this.currentMessageObject;
        if (!messageObject.openedInViewer && messageObject.getVideoStartsTimestamp() != -1) {
            videoSavedProgress = this.currentMessageObject.getVideoStartsTimestamp() / ((float) this.currentMessageObject.getDuration());
        } else {
            videoSavedProgress = this.currentMessageObject.getVideoSavedProgress();
        }
        float fClamp01 = Utilities.clamp01(videoSavedProgress);
        if (fClamp01 > 0.0f) {
            int[] roundRadius = this.photoImage.getRoundRadius();
            canvas.save();
            if (roundRadius[0] <= 0 && roundRadius[1] <= 0 && roundRadius[2] <= 0 && roundRadius[3] <= 0) {
                canvas.clipRect(this.photoImage.getImageX(), this.photoImage.getImageY(), this.photoImage.getImageX2(), this.photoImage.getImageY2());
            } else {
                if (this.photoImageClipPath == null) {
                    this.photoImageClipPath = new Path();
                    this.photoImageClipPathRadii = new float[8];
                }
                float[] fArr = this.photoImageClipPathRadii;
                float fMax = Math.max(0, roundRadius[0]);
                fArr[1] = fMax;
                fArr[0] = fMax;
                float[] fArr2 = this.photoImageClipPathRadii;
                float fMax2 = Math.max(0, roundRadius[1]);
                fArr2[3] = fMax2;
                fArr2[2] = fMax2;
                float[] fArr3 = this.photoImageClipPathRadii;
                float fMax3 = Math.max(0, roundRadius[2]);
                fArr3[5] = fMax3;
                fArr3[4] = fMax3;
                float[] fArr4 = this.photoImageClipPathRadii;
                float fMax4 = Math.max(0, roundRadius[3]);
                fArr4[7] = fMax4;
                fArr4[6] = fMax4;
                this.photoImageClipPath.rewind();
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(this.photoImage.getImageX(), this.photoImage.getImageY(), this.photoImage.getImageX2(), this.photoImage.getImageY2());
                this.photoImageClipPath.addRoundRect(rectF, this.photoImageClipPathRadii, Path.Direction.CW);
                canvas.clipPath(this.photoImageClipPath);
            }
            Theme.chat_videoProgressPaint.setColor(Theme.multAlpha(-1, this.controlsAlpha * 0.35f));
            canvas.drawRect(this.photoImage.getImageX(), this.photoImage.getImageY2() - AndroidUtilities.m1146dp(3.0f), this.photoImage.getImageX2(), this.photoImage.getImageY2(), Theme.chat_videoProgressPaint);
            Theme.chat_videoProgressPaint.setColor(Theme.multAlpha(i, this.controlsAlpha));
            RectF rectF2 = AndroidUtilities.rectTmp;
            rectF2.set(this.photoImage.getImageX() - AndroidUtilities.m1146dp(2.0f), this.photoImage.getImageY2() - AndroidUtilities.m1146dp(3.0f), this.photoImage.getImageX() + (this.photoImage.getImageWidth() * fClamp01), this.photoImage.getImageY2());
            canvas.drawRoundRect(rectF2, AndroidUtilities.m1146dp(2.0f), AndroidUtilities.m1146dp(2.0f), Theme.chat_videoProgressPaint);
            canvas.restore();
        }
    }

    public long getStarsPrice() {
        TLRPC.Message message;
        TLRPC.Message message2;
        MessageObject.GroupedMessages groupedMessages = this.currentMessagesGroup;
        if (groupedMessages != null) {
            ArrayList<MessageObject> arrayList = groupedMessages.messages;
            int size = arrayList.size();
            int i = 0;
            long j = 0;
            while (i < size) {
                MessageObject messageObject = arrayList.get(i);
                i++;
                MessageObject messageObject2 = messageObject;
                j += (messageObject2 == null || (message2 = messageObject2.messageOwner) == null) ? 0L : message2.paid_message_stars;
            }
            return j;
        }
        MessageObject messageObject3 = this.currentMessageObject;
        if (messageObject3 == null || (message = messageObject3.messageOwner) == null) {
            return 0L;
        }
        return message.paid_message_stars;
    }

    private int getNameHeight() {
        if (this.drawNameAvatar) {
            if (this.adminLayout == null) {
                return AndroidUtilities.m1146dp(31.0f);
            }
            return AndroidUtilities.m1146dp(37.66f);
        }
        return (int) (AndroidUtilities.m1146dp(5.0f) + Theme.chat_namePaint.getTextSize());
    }

    private float getNameHeightAnimated() {
        float fLerp;
        TransitionParams transitionParams = this.transitionParams;
        if (transitionParams.animateDrawAvatar) {
            boolean z = this.drawNameAvatar;
            fLerp = AndroidUtilities.lerp(!z, z, transitionParams.animateChangeProgress);
        } else {
            fLerp = this.drawNameAvatar ? 1.0f : 0.0f;
        }
        return AndroidUtilities.lerp(AndroidUtilities.m1146dp(5.0f) + Theme.chat_namePaint.getTextSize(), AndroidUtilities.m1146dp(this.adminLayout == null ? 31.0f : 35.0f), fLerp);
    }

    private int getTailExtraSpace() {
        if (!ExteraConfig.removeMessageTail || this.pinnedBottom) {
            return 0;
        }
        MessageObject.GroupedMessagePosition groupedMessagePosition = this.currentPosition;
        if (groupedMessagePosition == null || (groupedMessagePosition.flags & 8) != 0) {
            return AndroidUtilities.m1146dp(6.0f);
        }
        return 0;
    }
}
