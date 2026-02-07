package org.telegram.p023ui;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderNode;
import android.graphics.Shader;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.net.Uri;
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
import android.text.style.ClickableSpan;
import android.text.style.LineHeightSpan;
import android.text.style.URLSpan;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.util.FloatProperty;
import android.util.Pair;
import android.util.Property;
import android.util.Range;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.Scroller;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import androidx.annotation.Keep;
import androidx.collection.LongSparseArray;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScrollerEnd;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.components.SearchPhotoPopupWrapper;
import com.exteragram.messenger.utils.ChatUtils;
import com.exteragram.messenger.utils.p011ui.PopupUtils;
import com.exteragram.messenger.utils.system.SystemUtils;
import com.exteragram.messenger.utils.text.TranslatorUtils;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.p013ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.video.VideoSize;
import com.google.android.gms.cast.framework.CastContext;
import de.robv.android.xposed.callbacks.XCallback;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.mvel2.DataTypes;
import org.mvel2.MVEL;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AnimationNotificationsLocker;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Bitmaps;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.BringAppForegroundService;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.DownloadController;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessageSuggestionParams;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsSettingsFacade;
import org.telegram.messenger.SecureDocument;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.TranslateController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.WebFile;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.camera.Size;
import org.telegram.messenger.chromecast.ChromecastController;
import org.telegram.messenger.pip.PipSource;
import org.telegram.messenger.pip.source.IPipSourceDelegate;
import org.telegram.messenger.pip.utils.PipUtils;
import org.telegram.messenger.video.OldVideoPlayerRewinder;
import org.telegram.messenger.video.VideoAds;
import org.telegram.messenger.video.VideoFramesRewinder;
import org.telegram.messenger.video.VideoPlayerRewinder;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.ActionBarMenu;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.ActionBar.ActionBarMenuSlider;
import org.telegram.p023ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.p023ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p023ui.ActionBar.AdjustPanLayoutHelper;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.SimpleTextView;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.CheckBoxCell;
import org.telegram.p023ui.Cells.PhotoPickerPhotoCell;
import org.telegram.p023ui.Cells.TextSelectionHelper;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.ChooseDownloadQualityLayout;
import org.telegram.p023ui.Components.AlertsCreator;
import org.telegram.p023ui.Components.AnimatedEmojiSpan;
import org.telegram.p023ui.Components.AnimatedFileDrawable;
import org.telegram.p023ui.Components.AnimatedFloat;
import org.telegram.p023ui.Components.AnimatedTextView;
import org.telegram.p023ui.Components.AnimationProperties;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.BlurringShader;
import org.telegram.p023ui.Components.Bulletin;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.CaptionPhotoViewer;
import org.telegram.p023ui.Components.CastMediaRouteButton;
import org.telegram.p023ui.Components.ChatActivityEnterView;
import org.telegram.p023ui.Components.ChatAttachAlert;
import org.telegram.p023ui.Components.CheckBox;
import org.telegram.p023ui.Components.ClippingImageView;
import org.telegram.p023ui.Components.ColoredImageSpan;
import org.telegram.p023ui.Components.CombinedDrawable;
import org.telegram.p023ui.Components.Crop.CropAreaView;
import org.telegram.p023ui.Components.Crop.CropTransform;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.EditCoverButton;
import org.telegram.p023ui.Components.EditTextEmoji;
import org.telegram.p023ui.Components.FilterGLThread;
import org.telegram.p023ui.Components.FilterShaders;
import org.telegram.p023ui.Components.FloatSeekBarAccessibilityDelegate;
import org.telegram.p023ui.Components.Forum.ForumUtilities;
import org.telegram.p023ui.Components.GestureDetector2;
import org.telegram.p023ui.Components.GroupedPhotosListView;
import org.telegram.p023ui.Components.HideViewAfterAnimation;
import org.telegram.p023ui.Components.ImageUpdater;
import org.telegram.p023ui.Components.ItemOptions;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.LinkSpanDrawable;
import org.telegram.p023ui.Components.LoadingDrawable;
import org.telegram.p023ui.Components.MentionsContainerView;
import org.telegram.p023ui.Components.MuteDrawable;
import org.telegram.p023ui.Components.Paint.Views.LPhotoPaintView;
import org.telegram.p023ui.Components.Paint.Views.MaskPaintView;
import org.telegram.p023ui.Components.Paint.Views.PaintCancelView;
import org.telegram.p023ui.Components.Paint.Views.PaintDoneView;
import org.telegram.p023ui.Components.Paint.Views.PaintWeightChooserView;
import org.telegram.p023ui.Components.Paint.Views.StickerCutOutBtn;
import org.telegram.p023ui.Components.Paint.Views.StickerMakerBackgroundView;
import org.telegram.p023ui.Components.Paint.Views.StickerMakerView;
import org.telegram.p023ui.Components.PaintingOverlay;
import org.telegram.p023ui.Components.PhotoCropView;
import org.telegram.p023ui.Components.PhotoFilterView;
import org.telegram.p023ui.Components.PhotoViewerBlurDrawable;
import org.telegram.p023ui.Components.PhotoViewerCoverEditor;
import org.telegram.p023ui.Components.PhotoViewerWebView;
import org.telegram.p023ui.Components.PickerBottomLayoutViewer;
import org.telegram.p023ui.Components.PipVideoOverlay;
import org.telegram.p023ui.Components.PlayPauseDrawable;
import org.telegram.p023ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p023ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p023ui.Components.QuoteSpan;
import org.telegram.p023ui.Components.RLottieDrawable;
import org.telegram.p023ui.Components.RadialProgressView;
import org.telegram.p023ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.p023ui.Components.SeekSpeedDrawable;
import org.telegram.p023ui.Components.ShareAlert;
import org.telegram.p023ui.Components.SizeNotifierFrameLayoutPhoto;
import org.telegram.p023ui.Components.SpeedIconDrawable;
import org.telegram.p023ui.Components.StickersAlert;
import org.telegram.p023ui.Components.TextViewSwitcher;
import org.telegram.p023ui.Components.Tooltip;
import org.telegram.p023ui.Components.TranslateAlert2;
import org.telegram.p023ui.Components.TypefaceSpan;
import org.telegram.p023ui.Components.URLSpanReplacement;
import org.telegram.p023ui.Components.UndoView;
import org.telegram.p023ui.Components.VideoCompressButton;
import org.telegram.p023ui.Components.VideoEditTextureView;
import org.telegram.p023ui.Components.VideoForwardDrawable;
import org.telegram.p023ui.Components.VideoPlayer;
import org.telegram.p023ui.Components.VideoPlayerSeekBar;
import org.telegram.p023ui.Components.VideoSeekPreviewImage;
import org.telegram.p023ui.Components.VideoTimelinePlayView;
import org.telegram.p023ui.Components.ViewHelper;
import org.telegram.p023ui.Components.spoilers.SpoilersTextView;
import org.telegram.p023ui.ContentPreviewViewer;
import org.telegram.p023ui.PhotoViewer;
import org.telegram.p023ui.SpeedButtonsLayout;
import org.telegram.p023ui.Stars.StarsController;
import org.telegram.p023ui.Stories.DarkThemeResourceProvider;
import org.telegram.p023ui.Stories.recorder.CaptionContainerView;
import org.telegram.p023ui.Stories.recorder.HintView2;
import org.telegram.p023ui.Stories.recorder.KeyboardNotifier;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.webrtc.MediaStreamTrack;
import p017j$.util.List;
import p017j$.util.Objects;

@SuppressLint({"WrongConstant"})
/* loaded from: classes3.dex */
public class PhotoViewer implements NotificationCenter.NotificationCenterDelegate, GestureDetector2.OnGestureListener, GestureDetector2.OnDoubleTapListener, IPipSourceDelegate, AudioManager.OnAudioFocusChangeListener {
    private static volatile PhotoViewer Instance;
    private static volatile PhotoViewer Instance2;
    private static volatile PhotoViewer PipInstance;
    private static final Property VPC_PROGRESS;
    private static DecelerateInterpolator decelerateInterpolator;
    private static Drawable[] progressDrawables;
    private static Paint progressPaint;
    private boolean ALLOW_USE_SURFACE;
    public final Property FLASH_VIEW_VALUE;
    private int aboutToSwitchTo;
    private ActionBar actionBar;
    private AnimatorSet actionBarAnimator;
    private PhotoViewerActionBarContainer actionBarContainer;
    private Map actionBarItemsVisibility;
    private boolean actionBarWasShownBeforeByEnd;
    private Context activityContext;
    private TextView adButtonTextView;
    private FrameLayout adButtonView;
    private VideoAds ads;
    private ActionBarMenuSubItem allMediaItem;
    private boolean allowOrder;
    private boolean allowShare;
    private boolean allowShowFullscreenButton;
    private float animateToMirror;
    private float animateToRotate;
    private float animateToScale;
    private float animateToX;
    private float animateToY;
    private ClippingImageView animatingImageView;
    private Runnable animationEndRunnable;
    private int animationInProgress;
    private long animationStartTime;
    private float animationValue;
    private float[][] animationValues;
    private boolean applying;
    private AspectRatioFrameLayout aspectRatioFrameLayout;
    private boolean attachedToWindow;
    private long audioFramesSize;
    private final AudioManager audioManager;
    private float avatarStartProgress;
    private long avatarStartTime;
    private final ArrayList avatarsArr;
    private long avatarsDialogId;
    private BackgroundDrawable backgroundDrawable;
    private volatile int bitrate;
    private Paint blackPaint;
    private final AnimatedFloat blurAlpha;
    private RectF blurBounds;
    public BlurringShader.BlurManager blurManager;
    private FrameLayout bottomBulletinUnderCaption;
    private LinearGradient bottomFancyShadow;
    private Matrix bottomFancyShadowMatrix;
    private Paint bottomFancyShadowPaint;
    private FrameLayout bottomLayout;
    private LinearLayout btnLayout;
    private boolean canDragDown;
    private boolean canEditAvatar;
    private boolean canZoom;
    private FrameLayout captionContainer;
    private String captionDetectedLanguage;
    private CaptionPhotoViewer captionEdit;
    private final float[] captionEditAlpha;
    private FrameLayout captionEditContainer;
    public CharSequence captionForAllMedia;
    private boolean captionHwLayerEnabled;
    private CaptionScrollView captionScrollView;
    private CaptionTextViewSwitcher captionTextViewSwitcher;
    private boolean captionTranslated;
    private long captureFrameAtTime;
    private long captureFrameReadyAtTime;
    private ActionBarMenuSubItem castItem;
    private CastMediaRouteButton castItemButton;
    private BlurringShader.ThumbBlurer centerBlur;
    public ImageReceiver centerImage;
    private AnimatedFloat[] centerImageInsideBlur;
    private boolean centerImageIsVideo;
    private Matrix centerImageTransform;
    private boolean centerImageTransformLocked;
    private AnimatorSet changeModeAnimation;
    public TextureView changedTextureView;
    private boolean changingPage;
    private boolean changingTextureView;
    private CheckBox checkImageView;
    private ChooseDownloadQualityLayout chooseDownloadQualityLayout;
    private SpeedButtonsLayout chooseSpeedLayout;
    private int classGuid;
    private Path clipFancyShadows;
    private float clippingImageProgress;
    public boolean closePhotoAfterSelect;
    public boolean closePhotoAfterSelectWithAnimation;
    private VideoCompressButton compressItem;
    private HintView2 compressPhotoHint;
    private volatile int compressionsCount;
    private FrameLayoutDrawer containerView;
    private PhotoCountView countView;
    public PhotoViewerCoverEditor coverEditor;
    private boolean cropInitied;
    private ImageView cropItem;
    private CropTransform cropTransform;
    private int currentAccount;
    private AnimatedFileDrawable currentAnimation;
    private ImageLocation currentAvatarLocation;
    private Bitmap currentBitmap;
    private TLRPC.BotInlineResult currentBotInlineResult;
    private float currentCropScale;
    private float currentCropX;
    private float currentCropY;
    private long currentDialogId;
    private int currentEditMode;
    private ImageLocation currentFileLocation;
    private ImageLocation currentFileLocationVideo;
    private String[] currentFileNames;
    private String currentFilterQuery;
    private ReactionsLayoutInBubble.VisibleReaction currentFilterTag;
    private boolean currentFiltered;
    private String currentImageFaceKey;
    private int currentImageHasFace;
    private String currentImagePath;
    private int currentIndex;
    private AnimatorSet currentListViewAnimation;
    private Runnable currentLoadingVideoRunnable;
    private MessageObject currentMessageObject;
    private TLRPC.PageBlock currentPageBlock;
    private float currentPanTranslationY;
    private String currentPathObject;
    private PlaceProviderObject currentPlaceObject;
    private Uri currentPlayingVideoFile;
    private ArrayList currentPlayingVideoQualityFiles;
    private SecureDocument currentSecureDocument;
    private String currentSubtitle;
    private ImageReceiver.BitmapHolder currentThumb;
    private boolean currentVideoFinishedLoading;
    private float currentVideoSpeed;
    public Utilities.Callback2 customStickerHandler;
    private CharSequence customTitle;
    private BlurButton cutOutBtn;
    private int dateOverride;
    private MessagesController.DialogPhotos dialogPhotos;
    private boolean disableShowCheck;
    private boolean discardTap;
    private TextView docInfoTextView;
    private TextView docNameTextView;
    private TextView doneButtonFullWidth;
    public boolean doneButtonPressed;
    private boolean dontAutoPlay;
    private boolean dontChangeCaptionPosition;
    private boolean dontResetZoomOnFirstLayout;
    private boolean doubleTap;
    private boolean doubleTapEnabled;
    private float dragY;
    private boolean draggingDown;
    private boolean[] drawPressedDrawable;
    private EditCoverButton editCoverButton;
    private ActionBarMenuItem editItem;
    private EditState editState;
    private boolean editing;
    private PickerBottomLayoutViewer editorDoneLayout;
    private boolean[] endReached;
    private long endTime;
    private BlurButton eraseBtn;
    private long estimatedDuration;
    private long estimatedSize;
    private ImageView exitFullscreenButton;
    private boolean fancyShadows;
    private boolean firstAnimationDelay;
    private boolean firstFrameRendered;
    private FirstFrameView firstFrameView;
    private AnimatorSet flashAnimator;
    private View flashView;
    public final VideoFramesRewinder framesRewinder;
    boolean fromCamera;
    private ImageView[] fullscreenButton;
    private int fullscreenedByButton;
    private ActionBarMenuSubItem galleryButton;
    private GestureDetector2 gestureDetector;
    private GroupedPhotosListView groupedPhotosListView;
    private boolean hasAudioFocus;
    public boolean hasCaptionForAllMedia;
    private Runnable hideActionBarRunnable;
    private PlaceProviderObject hideAfterAnimation;
    private UndoView hintView;
    private Rect hitRect;
    private boolean ignoreDidSetImage;
    private boolean ignorePlayerUpdate;
    private RectF imageBounds;
    private Matrix imageBoundsMatrix;
    private float[] imageBoundsPoints;
    private AnimatorSet imageMoveAnimation;
    private final ArrayList imagesArr;
    private final ArrayList imagesArrLocals;
    private final ArrayList imagesArrLocations;
    private final ArrayList imagesArrLocationsSizes;
    private final ArrayList imagesArrLocationsVideo;
    private final ArrayList imagesArrMessages;
    private final ArrayList imagesArrTemp;
    private final SparseArray[] imagesByIds;
    private final SparseArray[] imagesByIdsTemp;
    private boolean inBubbleMode;
    private boolean inPreview;
    private VideoPlayer injectingVideoPlayer;
    private SurfaceTexture injectingVideoPlayerSurface;
    private float inlineOutAnimationProgress;
    private final Rect insets;
    private DecelerateInterpolator interpolator;
    private boolean invalidCoords;
    private boolean isActionBarVisible;
    private boolean isCurrentVideo;
    private boolean isDocumentsPicker;
    private boolean isEmbedVideo;
    private boolean isEvent;
    private boolean isFirstLoading;
    private volatile boolean isH264Video;
    private boolean isInline;
    private boolean isPhotosListViewVisible;
    private boolean isPlaying;
    private boolean isStreaming;
    private boolean isVisible;
    private boolean isVisibleOrAnimating;
    private LinearLayout itemsLayout;
    private boolean keepScreenOnFlagSet;
    private int keyboardSize;
    private long lastBufferedPositionCheck;
    private boolean lastCaptionTranslating;
    private String lastControlFrameDuration;
    private Bitmap lastFrameBitmap;
    private ImageView lastFrameImageView;
    private int lastImageId;
    private long lastPhotoSetTime;
    private int lastQualityIndexSelected;
    private long lastSaveTime;
    private CharSequence lastTitle;
    float lastX;
    private BlurringShader.ThumbBlurer leftBlur;
    private MediaController.CropState leftCropState;
    private CropTransform leftCropTransform;
    private ImageReceiver leftImage;
    private boolean leftImageIsVideo;
    private PaintingOverlay leftPaintingOverlay;
    private Bulletin limitBulletin;
    private boolean loadInitialVideo;
    private boolean loadingMoreImages;
    Runnable longPressRunnable;
    float longPressX;
    OldVideoPlayerRewinder longVideoPlayerRewinder;
    private ActionBarMenuSubItem loopItem;
    private boolean manuallyPaused;
    private MaskPaintView maskPaintView;
    private boolean maskPaintViewEraser;
    private boolean maskPaintViewShuttingDown;
    private int maskPaintViewTouched;
    private StickersAlert masksAlert;
    private ActionBarMenuItem masksItem;
    private int maxSelectedPhotos;
    private float maxX;
    private float maxY;
    private ActionBarMenu menu;
    private ActionBarMenuItem menuItem;
    private long mergeDialogId;
    private float minX;
    private float minY;
    private AnimatorSet miniProgressAnimator;
    private final Runnable miniProgressShowRunnable;
    private RadialProgressView miniProgressView;
    private float mirror;
    private ImageView mirrorItem;
    private float moveStartX;
    private float moveStartY;
    private boolean moving;
    private ImageView muteButton;
    private MuteDrawable muteDrawable;
    private ArrayList muteHints;
    private boolean muteVideo;
    private String nameOverride;
    private View navigationBar;
    private int navigationBarHeight;
    private boolean needCaptionLayout;
    private long needCaptureFrameReadyAtTime;
    private boolean needSearchImageInArr;
    private boolean needShowOnReady;
    private Runnable onUserLeaveHintListener;
    private boolean openedFromProfile;
    private boolean openedFullScreenVideo;
    private boolean opennedFromMedia;
    private OrientationEventListener orientationEventListener;
    private volatile int originalBitrate;
    private volatile int originalHeight;
    private long originalSize;
    private volatile int originalWidth;
    private BlurButton outlineBtn;
    private boolean padImageForHorizontalInsets;
    private PageBlocksAdapter pageBlocksAdapter;
    private ImageView paintItem;
    private AnimatorSet paintKeyboardAnimator;
    private KeyboardNotifier paintKeyboardNotifier;
    private int paintViewTouched;
    private PaintingOverlay paintingOverlay;
    private Activity parentActivity;
    private ChatAttachAlert parentAlert;
    private ChatActivity parentChatActivity;
    private BaseFragment parentFragment;
    private Runnable parentFragmentNullifier;
    private boolean pauseOnMinimize;
    private PhotoCropView photoCropView;
    private PhotoFilterView photoFilterView;
    private LPhotoPaintView photoPaintView;
    private PhotoProgressView[] photoProgressViews;
    private PhotoViewerWebView photoViewerWebView;
    private CounterView photosCounterView;
    private FrameLayout pickerView;
    private ChatActivityEnterView.SendButton pickerViewSendButton;
    private float pinchCenterX;
    private float pinchCenterY;
    private float pinchStartAngle;
    private float pinchStartDistance;
    private float pinchStartRotate;
    private float pinchStartScale;
    private float pinchStartX;
    private float pinchStartY;
    private boolean pipAnimationInProgress;
    private boolean pipAvailable;
    public Runnable pipFirstFrameCallback;
    private ActionBarMenuSubItem pipItem;
    private View pipPlaceholderView;
    private int[] pipPosition;
    private PipSource pipSource;
    private TextureView pipTextureView;
    private boolean pipVideoOverlayAnimateFlag;
    private PhotoViewerProvider placeProvider;
    private View playButtonAccessibilityOverlay;
    private boolean playerAutoStarted;
    private boolean playerInjected;
    private boolean playerLooping;
    private boolean playerWasPlaying;
    private boolean playerWasReady;
    private GradientDrawable[] pressedDrawable;
    private float[] pressedDrawableAlpha;
    private int prevOrientation;
    private int previousCompression;
    private boolean previousCropMirrored;
    private int previousCropOrientation;
    private float previousCropPh;
    private float previousCropPw;
    private float previousCropPx;
    private float previousCropPy;
    private float previousCropRotation;
    private float previousCropScale;
    private boolean previousHasTransform;
    private RadialProgressView progressView;
    private QualityChooseView qualityChooseView;
    private AnimatorSet qualityChooseViewAnimation;
    private PickerBottomLayoutViewer qualityPicker;
    private RenderNode renderNode;
    public TLRPC.Document replacedSticker;
    private boolean requestingPreview;
    private TextView resetButton;
    private Theme.ResourcesProvider resourcesProvider;
    private BlurButton restoreBtn;
    private volatile int resultHeight;
    private volatile int resultWidth;
    private BlurringShader.ThumbBlurer rightBlur;
    private MediaController.CropState rightCropState;
    private CropTransform rightCropTransform;
    private ImageReceiver rightImage;
    private boolean rightImageIsVideo;
    private PaintingOverlay rightPaintingOverlay;
    private float rotate;
    private ImageView rotateItem;
    private int rotationValue;
    private float savedRotation;
    private float savedScale;
    private SavedState savedState;
    private float savedTx;
    private float savedTy;
    private float scale;
    private Scroller scroller;
    private ActionBarPopupWindow.GapView searchGap;
    private ActionBarMenuSubItem searchItem;
    SearchPhotoPopupWrapper searchPhotoPopupWrapper;
    private final ArrayList secureDocuments;
    private SeekSpeedDrawable seekSpeedDrawable;
    private float seekToProgressPending;
    private float seekToProgressPending2;
    private volatile int selectedCompression;
    private ArrayList selectedEmojis;
    private ListAdapter selectedPhotosAdapter;
    private SelectedPhotosListView selectedPhotosListView;
    private ActionBarMenuItem sendItem;
    private int sendPhotoType;
    private ActionBarPopupWindow.ActionBarPopupWindowLayout sendPopupLayout;
    private ActionBarPopupWindow sendPopupWindow;
    private ImageUpdater.AvatarFor setAvatarFor;
    private Runnable setLoadingRunnable;
    private BlurringShader.StoryBlurDrawer shadowBlurer;
    private int sharedMediaType;
    private float shiftDp;
    private String shouldSavePositionForCurrentVideo;
    private String shouldSavePositionForCurrentVideoShortTerm;
    private PlaceProviderObject showAfterAnimation;
    private boolean shownControlsByEnd;
    private ImageReceiver sideImage;
    private boolean skipFirstBufferingProgress;
    public boolean skipLastFrameDraw;
    private int slideshowMessageId;
    private ActionBarPopupWindow.GapView speedGap;
    private ActionBarMenuSlider.SpeedSlider speedItem;
    private int startOffset;
    private boolean startReached;
    private long startTime;
    private long startedPlayTime;
    public boolean stickerEmpty;
    public boolean stickerEmptySent;
    private StickerMakerBackgroundView stickerMakerBackgroundView;
    public StickerMakerView stickerMakerView;
    private boolean streamingAlertShown;
    private Paint surfaceBlackoutPaint;
    private TextureView.SurfaceTextureListener surfaceTextureListener;
    private int switchImageAfterAnimation;
    private final Runnable switchToInlineRunnable;
    private boolean switchingInlineMode;
    private int switchingToIndex;
    private int switchingToMode;
    private int[] tempInt;
    private TextSelectionHelper.SimpleTextSelectionHelper textSelectionHelper;
    private ImageView textureImageView;
    private boolean textureUploaded;
    private boolean textureViewSkipRender;
    private Tooltip tooltip;
    private FrameLayout topBulletinUnderCaption;
    private CaptionPhotoViewer topCaptionEdit;
    private final float[] topCaptionEditAlpha;
    private FrameLayout topCaptionEditContainer;
    private LinearGradient topFancyShadow;
    private Matrix topFancyShadowMatrix;
    private Paint topFancyShadowPaint;
    private long topicId;
    private int totalImagesCount;
    private int totalImagesCountMerge;
    private int touchSlop;
    private long transitionAnimationStartTime;
    private AnimationNotificationsLocker transitionNotificationLocker;
    private float translateY;
    private ValueAnimator translateYAnimator;
    private float translationX;
    private float translationY;
    private boolean tryStartRequestPreviewOnFinish;
    private ImageView tuneItem;
    private BlurButton undoBtn;
    private final Runnable updateContainerFlagsRunnable;
    private Runnable updateProgressRunnable;
    private boolean usedSurfaceView;
    private VelocityTracker velocityTracker;
    private TextView videoAvatarTooltip;
    private volatile boolean videoConvertSupported;
    private float videoCrossfadeAlpha;
    private long videoCrossfadeAlphaLastTime;
    private boolean videoCrossfadeStarted;
    private float videoCutEnd;
    private float videoCutStart;
    private float videoDuration;
    private VideoForwardDrawable videoForwardDrawable;
    private Bitmap videoFrameBitmap;
    private Paint videoFrameBitmapPaint;
    private int videoFramerate;
    private long videoFramesSize;
    private int videoHeight;
    private ActionBarMenuItem videoItem;
    private ChooseQualityLayout$QualityIcon videoItemIcon;
    private Runnable videoPlayRunnable;
    private VideoPlayer videoPlayer;
    private Animator videoPlayerControlAnimator;
    private VideoPlayerControlFrameLayout videoPlayerControlFrameLayout;
    private boolean videoPlayerControlVisible;
    private int[] videoPlayerCurrentTime;
    private final VideoPlayerRewinder videoPlayerRewinder;
    private VideoPlayerSeekBar videoPlayerSeekbar;
    private View videoPlayerSeekbarView;
    private SimpleTextView videoPlayerTime;
    private int[] videoPlayerTotalTime;
    private VideoSeekPreviewImage videoPreviewFrame;
    private AnimatorSet videoPreviewFrameAnimation;
    private MessageObject videoPreviewMessageObject;
    private final ArrayList videoQualityItems;
    private LinearLayout videoQualityLayout;
    private boolean videoSizeSet;
    private SurfaceView videoSurfaceView;
    private TextureView videoTextureView;
    private ObjectAnimator videoTimelineAnimator;
    private VideoTimelinePlayView videoTimelineView;
    private FrameLayout videoTimelineViewContainer;
    private int videoWidth;
    private AlertDialog visibleDialog;
    private int waitingForDraw;
    private int waitingForFirstTextureUpload;
    private boolean wasCountViewShown;
    private boolean wasLayout;
    private boolean wasRotated;
    private WindowManager.LayoutParams windowLayoutParams;
    public FrameLayout windowView;
    private boolean windowViewSkipRender;
    private boolean zoomAnimation;
    private boolean zooming;
    public static Paint bitmapPaint = new Paint(2);
    private static final HashMap savedVideoPositions = new HashMap();

    public static class EmptyPhotoViewerProvider implements PhotoViewerProvider {
        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean allowCaption() {
            return true;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean allowSendingSubmenu() {
            return true;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean canCaptureMorePhotos() {
            return true;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean canEdit(int i) {
            return false;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ boolean canLoadMoreAvatars() {
            return PhotoViewerProvider.CC.$default$canLoadMoreAvatars(this);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ boolean canMoveCaptionAbove() {
            return PhotoViewerProvider.CC.$default$canMoveCaptionAbove(this);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean canReplace(int i) {
            return false;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean canScrollAway() {
            return true;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean cancelButtonPressed() {
            return true;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean closeKeyboard() {
            return false;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void deleteImageAtIndex(int i) {
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ boolean forceAllInGroup() {
            return PhotoViewerProvider.CC.$default$forceAllInGroup(this);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public String getDeleteMessageString() {
            return null;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ long getDialogId() {
            return PhotoViewerProvider.CC.$default$getDialogId(this);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public MessageObject getEditingMessageObject() {
            return null;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public int getPhotoIndex(int i) {
            return -1;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC.FileLocation fileLocation, int i, boolean z, boolean z2) {
            return null;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public int getSelectedCount() {
            return 0;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public HashMap getSelectedPhotos() {
            return null;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public ArrayList getSelectedPhotosOrder() {
            return null;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public ImageReceiver.BitmapHolder getThumbForPhoto(MessageObject messageObject, TLRPC.FileLocation fileLocation, int i) {
            return null;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public int getTotalImageCount() {
            return -1;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ boolean isCaptionAbove() {
            return PhotoViewerProvider.CC.$default$isCaptionAbove(this);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ boolean isEditingMessage() {
            return PhotoViewerProvider.CC.$default$isEditingMessage(this);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ boolean isEditingMessageResend() {
            return PhotoViewerProvider.CC.$default$isEditingMessageResend(this);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean isPhotoChecked(int i) {
            return false;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean loadMore() {
            return false;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ void moveCaptionAbove(boolean z) {
            PhotoViewerProvider.CC.$default$moveCaptionAbove(this, z);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void needAddMorePhotos() {
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void onApplyCaption(CharSequence charSequence) {
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void onClose() {
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ boolean onDeletePhoto(int i) {
            return PhotoViewerProvider.CC.$default$onDeletePhoto(this, i);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ void onEditModeChanged(boolean z) {
            PhotoViewerProvider.CC.$default$onEditModeChanged(this, z);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void onOpen() {
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ void onPreClose() {
            PhotoViewerProvider.CC.$default$onPreClose(this);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ void onPreOpen() {
            PhotoViewerProvider.CC.$default$onPreOpen(this);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public /* synthetic */ void onReleasePlayerBeforeClose(int i) {
            PhotoViewerProvider.CC.$default$onReleasePlayerBeforeClose(this, i);
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void openPhotoForEdit(String str, String str2, boolean z) {
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void replaceButtonPressed(int i, VideoEditedInfo videoEditedInfo) {
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public boolean scaleToFill() {
            return false;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo, boolean z, int i2, int i3, boolean z2) {
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public int setPhotoChecked(int i, VideoEditedInfo videoEditedInfo) {
            return -1;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public int setPhotoUnchecked(Object obj) {
            return -1;
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void updatePhotoAtIndex(int i) {
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void willHidePhotoViewer() {
        }

        @Override // org.telegram.ui.PhotoViewer.PhotoViewerProvider
        public void willSwitchFromPhoto(MessageObject messageObject, TLRPC.FileLocation fileLocation, int i) {
        }
    }

    /* loaded from: classes5.dex */
    public interface PageBlocksAdapter {
        TLRPC.PageBlock get(int i);

        List getAll();

        CharSequence getCaption(int i);

        File getFile(int i);

        TLRPC.PhotoSize getFileLocation(TLObject tLObject, int[] iArr);

        String getFileName(int i);

        int getItemsCount();

        TLObject getMedia(int i);

        Object getParentObject();

        boolean isHardwarePlayer(int i);

        boolean isVideo(int i);

        void updateSlideshowCell(TLRPC.PageBlock pageBlock);
    }

    /* loaded from: classes5.dex */
    public static class PlaceProviderObject {
        public ClippingImageView animatingImageView;
        public int animatingImageViewYOffset;
        public boolean canEdit;
        public int clipBottomAddition;
        public int clipTopAddition;
        public long dialogId;
        public boolean fadeIn;
        public boolean fromAyuDeleted;
        public ImageReceiver imageReceiver;
        public boolean isEvent;
        public View parentView;
        public int[] radius;
        public long size;
        public int starOffset;
        public ImageReceiver.BitmapHolder thumb;
        public int viewX;
        public int viewY;
        public float scale = 1.0f;
        public boolean allowTakeAnimation = true;
    }

    public static /* synthetic */ void $r8$lambda$OEv6YSCoYBzuTsii8BTCCKyzfCI(AlertDialog alertDialog, int i) {
    }

    public static /* synthetic */ void $r8$lambda$mqNpWh2fQFzLLCV1GmjhPU_NxIs() {
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    @Override // org.telegram.ui.Components.GestureDetector2.OnDoubleTapListener
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // org.telegram.ui.Components.GestureDetector2.OnGestureListener
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override // org.telegram.ui.Components.GestureDetector2.OnGestureListener
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override // org.telegram.ui.Components.GestureDetector2.OnGestureListener
    public void onShowPress(MotionEvent motionEvent) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        toggleMiniProgressInternal(true);
    }

    public TextureView getVideoTextureView() {
        return this.videoTextureView;
    }

    public boolean isVisibleOrAnimating() {
        return this.isVisibleOrAnimating;
    }

    public SurfaceView getVideoSurfaceView() {
        return this.videoSurfaceView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    static class PhotoViewerActionBarContainer extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
        private FrameLayout container;
        private boolean hasSubtitle;
        int lastHeight;
        private float rightPadding;
        private ValueAnimator rightPaddingAnimator;
        private AnimatorSet subtitleAnimator;
        AnimatedTextView subtitleTextView;
        private AnimatorSet titleAnimator;
        private FrameLayout titleLayout;
        SimpleTextView[] titleTextView;

        public PhotoViewerActionBarContainer(Context context) {
            int iM1146dp;
            super(context);
            FrameLayout frameLayout = new FrameLayout(context);
            this.container = frameLayout;
            if (ExteraConfig.centerTitle) {
                iM1146dp = 0;
            } else {
                iM1146dp = AndroidUtilities.m1146dp((AndroidUtilities.isTablet() ? 80 : 72) - 16);
            }
            frameLayout.setPadding(iM1146dp, 0, 0, 0);
            addView(this.container, LayoutHelper.createFrame(-1, -1, Opcodes.DNEG));
            FrameLayout frameLayout2 = new FrameLayout(context) { // from class: org.telegram.ui.PhotoViewer.PhotoViewerActionBarContainer.1
                @Override // android.widget.FrameLayout, android.view.View
                protected void onMeasure(int i, int i2) {
                    super.onMeasure(i, i2);
                    setPivotY(getMeasuredHeight());
                }
            };
            this.titleLayout = frameLayout2;
            frameLayout2.setPivotX(AndroidUtilities.m1146dp(16.0f));
            this.titleLayout.setPadding(ExteraConfig.centerTitle ? 0 : AndroidUtilities.m1146dp(16.0f), 0, 0, 0);
            this.titleLayout.setClipToPadding(false);
            this.container.addView(this.titleLayout, LayoutHelper.createFrame(-1, -1, Opcodes.DNEG));
            this.titleTextView = new SimpleTextView[2];
            int i = 0;
            while (true) {
                if (i >= 2) {
                    break;
                }
                this.titleTextView[i] = new SimpleTextView(context);
                this.titleTextView[i].setGravity(ExteraConfig.centerTitle ? 17 : 19);
                this.titleTextView[i].setTextColor(-1);
                this.titleTextView[i].setTextSize(20);
                this.titleTextView[i].setTypeface(AndroidUtilities.bold());
                this.titleTextView[i].setDrawablePadding(AndroidUtilities.m1146dp(4.0f));
                this.titleTextView[i].setScrollNonFitText(true);
                FrameLayout frameLayout3 = this.titleLayout;
                SimpleTextView simpleTextView = this.titleTextView[i];
                boolean z = ExteraConfig.centerTitle;
                frameLayout3.addView(simpleTextView, LayoutHelper.createFrame(-1, -2.0f, z ? 17 : 19, z ? 96.0f : 0.0f, 0.0f, z ? 96.0f : 0.0f, 0.0f));
                i++;
            }
            AnimatedTextView animatedTextView = new AnimatedTextView(context, true, false, false);
            this.subtitleTextView = animatedTextView;
            animatedTextView.setAnimationProperties(0.4f, 0L, 320L, CubicBezierInterpolator.EASE_OUT_QUINT);
            this.subtitleTextView.setTextSize(AndroidUtilities.m1146dp(14.0f));
            this.subtitleTextView.setGravity(ExteraConfig.centerTitle ? 17 : 19);
            this.subtitleTextView.setTextColor(-1);
            this.subtitleTextView.setEllipsizeByGradient(true);
            FrameLayout frameLayout4 = this.container;
            AnimatedTextView animatedTextView2 = this.subtitleTextView;
            boolean z2 = ExteraConfig.centerTitle;
            frameLayout4.addView(animatedTextView2, LayoutHelper.createFrame(-1, 20.0f, z2 ? 49 : 51, z2 ? 0.0f : 16.0f, 0.0f, 0.0f, 0.0f));
        }

        public void setTextShadows(boolean z) {
            this.titleTextView[0].getPaint().setShadowLayer(AndroidUtilities.dpf2(0.66f), 0.0f, 1.0f, z ? 1912602624 : 0);
            this.titleTextView[1].getPaint().setShadowLayer(AndroidUtilities.dpf2(0.66f), 0.0f, 1.0f, z ? 1912602624 : 0);
            this.subtitleTextView.getDrawable().setShadowLayer(AndroidUtilities.dpf2(0.66f), 0.0f, 1.0f, z ? 1912602624 : 0);
        }

        public void setTitle(CharSequence charSequence) {
            this.titleTextView[1].setAlpha(0.0f);
            this.titleTextView[1].setVisibility(8);
            if (!areStringsEqual(this.titleTextView[0].getText(), charSequence)) {
                this.titleTextView[0].resetScrolling();
            }
            this.titleTextView[0].setText(charSequence);
            this.titleTextView[0].setAlpha(1.0f);
            this.titleTextView[0].setTranslationX(0.0f);
            this.titleTextView[0].setTranslationY(0.0f);
        }

        private boolean areStringsEqual(CharSequence charSequence, CharSequence charSequence2) {
            if (charSequence == null && charSequence2 == null) {
                return true;
            }
            if ((charSequence == null) != (charSequence2 == null)) {
                return false;
            }
            return TextUtils.equals(charSequence.toString(), charSequence2.toString());
        }

        public void setTitleAnimated(CharSequence charSequence, boolean z, boolean z2) {
            if (areStringsEqual(this.titleTextView[0].getText(), charSequence)) {
                return;
            }
            AnimatorSet animatorSet = this.titleAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.titleAnimator = null;
            }
            SimpleTextView[] simpleTextViewArr = this.titleTextView;
            simpleTextViewArr[1].copyScrolling(simpleTextViewArr[0]);
            SimpleTextView[] simpleTextViewArr2 = this.titleTextView;
            simpleTextViewArr2[1].setText(simpleTextViewArr2[0].getText());
            this.titleTextView[1].setRightPadding((int) this.rightPadding);
            this.titleTextView[0].resetScrolling();
            this.titleTextView[0].setText(charSequence);
            float fM1146dp = AndroidUtilities.m1146dp(8.0f) * (z2 ? 1 : -1);
            this.titleTextView[1].setTranslationX(0.0f);
            this.titleTextView[1].setTranslationY(0.0f);
            if (z) {
                this.titleTextView[0].setTranslationX(0.0f);
                this.titleTextView[0].setTranslationY(-fM1146dp);
            } else {
                this.titleTextView[0].setTranslationX(-fM1146dp);
                this.titleTextView[0].setTranslationY(0.0f);
            }
            this.titleTextView[0].setAlpha(0.0f);
            this.titleTextView[1].setAlpha(1.0f);
            this.titleTextView[0].setVisibility(0);
            this.titleTextView[1].setVisibility(0);
            ArrayList arrayList = new ArrayList();
            Property property = View.ALPHA;
            arrayList.add(ObjectAnimator.ofFloat(this.titleTextView[1], (Property<SimpleTextView, Float>) property, 0.0f));
            arrayList.add(ObjectAnimator.ofFloat(this.titleTextView[0], (Property<SimpleTextView, Float>) property, 1.0f));
            SimpleTextView simpleTextView = this.titleTextView[1];
            Property property2 = View.TRANSLATION_X;
            Property property3 = View.TRANSLATION_Y;
            arrayList.add(ObjectAnimator.ofFloat(simpleTextView, (Property<SimpleTextView, Float>) (z ? property3 : property2), fM1146dp));
            SimpleTextView simpleTextView2 = this.titleTextView[0];
            if (z) {
                property2 = property3;
            }
            arrayList.add(ObjectAnimator.ofFloat(simpleTextView2, (Property<SimpleTextView, Float>) property2, 0.0f));
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.titleAnimator = animatorSet2;
            animatorSet2.playTogether(arrayList);
            this.titleAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.PhotoViewerActionBarContainer.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (PhotoViewerActionBarContainer.this.titleAnimator == animator) {
                        PhotoViewerActionBarContainer.this.titleTextView[1].setVisibility(8);
                        PhotoViewerActionBarContainer.this.titleAnimator = null;
                    }
                }
            });
            this.titleAnimator.setDuration(320L);
            this.titleAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
            this.titleAnimator.start();
        }

        public void setSubtitle(CharSequence charSequence) {
            setSubtitle(charSequence, true);
        }

        public void setSubtitle(CharSequence charSequence, boolean z) {
            boolean zIsEmpty = TextUtils.isEmpty(charSequence);
            boolean z2 = !zIsEmpty;
            if (z2 != this.hasSubtitle) {
                this.hasSubtitle = z2;
                AnimatorSet animatorSet = this.subtitleAnimator;
                if (animatorSet != null) {
                    animatorSet.cancel();
                }
                Point point = AndroidUtilities.displaySize;
                int i = point.x;
                int i2 = point.y;
                int iM1146dp = AndroidUtilities.m1146dp(((!zIsEmpty ? 30 : 33) - (AndroidUtilities.isTablet() ? 2 : 0)) + (ExteraConfig.centerTitle ? 2 : 1));
                float fM1146dp = 0.0f;
                if (z) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(ObjectAnimator.ofFloat(this.subtitleTextView, (Property<AnimatedTextView, Float>) View.ALPHA, !zIsEmpty ? 1.0f : 0.0f));
                    AnimatedTextView animatedTextView = this.subtitleTextView;
                    float[] fArr = {iM1146dp};
                    Property property = View.TRANSLATION_Y;
                    arrayList.add(ObjectAnimator.ofFloat(animatedTextView, (Property<AnimatedTextView, Float>) property, fArr));
                    FrameLayout frameLayout = this.titleLayout;
                    if (!zIsEmpty) {
                        fM1146dp = AndroidUtilities.m1146dp((-9) - (ExteraConfig.centerTitle ? 2 : 4));
                    }
                    arrayList.add(ObjectAnimator.ofFloat(frameLayout, (Property<FrameLayout, Float>) property, fM1146dp));
                    arrayList.add(ObjectAnimator.ofFloat(this.titleLayout, (Property<FrameLayout, Float>) View.SCALE_X, (zIsEmpty || ExteraConfig.centerTitle) ? 1.0f : 0.95f));
                    arrayList.add(ObjectAnimator.ofFloat(this.titleLayout, (Property<FrameLayout, Float>) View.SCALE_Y, (zIsEmpty || ExteraConfig.centerTitle) ? 1.0f : 0.95f));
                    AnimatorSet animatorSet2 = new AnimatorSet();
                    this.subtitleAnimator = animatorSet2;
                    animatorSet2.playTogether(arrayList);
                    this.subtitleAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                    this.subtitleAnimator.start();
                } else {
                    this.subtitleTextView.setAlpha(!zIsEmpty ? 1.0f : 0.0f);
                    this.subtitleTextView.setTranslationY(iM1146dp);
                    FrameLayout frameLayout2 = this.titleLayout;
                    if (!zIsEmpty) {
                        fM1146dp = AndroidUtilities.m1146dp((-9) - (ExteraConfig.centerTitle ? 2 : 4));
                    }
                    frameLayout2.setTranslationY(fM1146dp);
                    this.titleLayout.setScaleX((zIsEmpty || ExteraConfig.centerTitle) ? 1.0f : 0.95f);
                    this.titleLayout.setScaleY((zIsEmpty || ExteraConfig.centerTitle) ? 1.0f : 0.95f);
                }
            }
            this.subtitleTextView.setText(charSequence, z);
        }

        public void updateOrientation() {
            this.hasSubtitle = !this.hasSubtitle;
            setSubtitle(this.subtitleTextView.getText(), false);
        }

        public void updateRightPadding(final float f, boolean z) {
            if (ExteraConfig.centerTitle) {
                return;
            }
            ValueAnimator valueAnimator = this.rightPaddingAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.rightPaddingAnimator = null;
            }
            if (z) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.rightPadding, f);
                this.rightPaddingAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$PhotoViewerActionBarContainer$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$updateRightPadding$0(f, valueAnimator2);
                    }
                });
                this.rightPaddingAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.PhotoViewerActionBarContainer.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        PhotoViewerActionBarContainer.this.updateRightPadding(f, false);
                    }
                });
                this.rightPaddingAnimator.setDuration(320L);
                this.rightPaddingAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                this.rightPaddingAnimator.start();
                return;
            }
            this.rightPadding = f;
            this.titleTextView[0].setRightPadding((int) f);
            this.subtitleTextView.setRightPadding(f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateRightPadding$0(float f, ValueAnimator valueAnimator) {
            this.rightPadding = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            int i = (int) f;
            this.titleTextView[0].setRightPadding(i);
            this.titleTextView[1].setRightPadding(i);
            this.subtitleTextView.setRightPadding(f);
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            int i3 = AndroidUtilities.statusBarHeight;
            int i4 = this.lastHeight;
            int i5 = AndroidUtilities.displaySize.y;
            if (i4 != i5) {
                this.lastHeight = i5;
                updateOrientation();
            }
            this.container.measure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2 - i3, TLObject.FLAG_30));
            setMeasuredDimension(size, size2);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            this.container.layout(0, AndroidUtilities.statusBarHeight, i3 - i, i4 - i2);
        }

        @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
        public void didReceivedNotification(int i, int i2, Object... objArr) {
            if (i == NotificationCenter.emojiLoaded) {
                this.titleTextView[0].invalidate();
                this.titleTextView[1].invalidate();
                this.subtitleTextView.invalidate();
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        }
    }

    /* loaded from: classes5.dex */
    private static class PhotoCountView extends View {
        Paint backgroundPaint;
        StaticLayout center;
        float centerTop;
        float centerWidth;
        AnimatedTextView.AnimatedTextDrawable left;
        private String lng;
        private int marginTop;
        private boolean nextNotAnimate;
        TextPaint paint;
        AnimatedTextView.AnimatedTextDrawable right;
        private AnimatedFloat showT;
        private boolean shown;

        public PhotoCountView(Context context) {
            super(context);
            this.backgroundPaint = new Paint(1);
            this.paint = new TextPaint(1);
            this.shown = false;
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
            this.showT = new AnimatedFloat(this, 0L, 350L, cubicBezierInterpolator);
            this.backgroundPaint.setColor(2130706432);
            AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
            this.left = animatedTextDrawable;
            animatedTextDrawable.setAnimationProperties(0.3f, 0L, 320L, cubicBezierInterpolator);
            this.left.setTextColor(-1);
            this.left.setTextSize(AndroidUtilities.m1146dp(14.0f));
            this.left.setTypeface(AndroidUtilities.bold());
            this.left.setCallback(this);
            this.left.setText(MVEL.VERSION_SUB);
            this.left.setOverrideFullWidth(AndroidUtilities.displaySize.x);
            this.paint.setColor(-1);
            this.paint.setTextSize(AndroidUtilities.m1146dp(14.0f));
            this.paint.setTypeface(AndroidUtilities.bold());
            setCenterText();
            AnimatedTextView.AnimatedTextDrawable animatedTextDrawable2 = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
            this.right = animatedTextDrawable2;
            animatedTextDrawable2.setAnimationProperties(0.3f, 0L, 320L, cubicBezierInterpolator);
            this.right.setTextColor(-1);
            this.right.setTextSize(AndroidUtilities.m1146dp(14.0f));
            this.right.setTypeface(AndroidUtilities.bold());
            this.right.setCallback(this);
            this.right.setText(MVEL.VERSION_SUB);
            this.right.setOverrideFullWidth(AndroidUtilities.displaySize.x);
        }

        private void setCenterText() {
            StaticLayout staticLayout = new StaticLayout(getOf(), this.paint, AndroidUtilities.m1146dp(200.0f), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
            this.center = staticLayout;
            if (staticLayout.getLineCount() >= 1) {
                this.centerWidth = this.center.getLineWidth(0);
                this.centerTop = this.center.getLineDescent(0);
            } else {
                this.centerWidth = 0.0f;
                this.centerTop = 0.0f;
            }
        }

        private String getOf() {
            this.lng = LocaleController.getInstance().getCurrentLocaleInfo().shortName;
            return LocaleController.getString(C2369R.string.f1460Of).replace("%1$d", "").replace("%2$d", "");
        }

        public void set(int i, int i2) {
            set(i, i2, true);
        }

        public void set(int i, int i2, boolean z) {
            boolean z2 = false;
            int iMax = Math.max(0, i);
            int iMax2 = Math.max(iMax, i2);
            if (LocaleController.getInstance().getCurrentLocaleInfo() != null && !TextUtils.equals(this.lng, LocaleController.getInstance().getCurrentLocaleInfo().shortName)) {
                setCenterText();
            }
            this.left.setText(String.format("%d", Integer.valueOf(LocaleController.isRTL ? iMax2 : iMax)), (!z || this.nextNotAnimate || LocaleController.isRTL) ? false : true);
            AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = this.right;
            if (!LocaleController.isRTL) {
                iMax = iMax2;
            }
            String str = String.format("%d", Integer.valueOf(iMax));
            if (z && !this.nextNotAnimate && !LocaleController.isRTL) {
                z2 = true;
            }
            animatedTextDrawable.setText(str, z2);
            this.nextNotAnimate = !z;
        }

        @Override // android.view.View
        protected boolean verifyDrawable(Drawable drawable) {
            return this.left == drawable || this.right == drawable || super.verifyDrawable(drawable);
        }

        public void updateShow(boolean z, boolean z2) {
            if (this.shown != z) {
                this.shown = z;
                if (!z) {
                    this.nextNotAnimate = true;
                }
                if (!z2) {
                    this.showT.set(z ? 1.0f : 0.0f, true);
                }
                invalidate();
            }
        }

        @Override // android.view.View
        public boolean isShown() {
            return this.shown;
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float f = this.showT.set(this.shown ? 1.0f : 0.0f);
            if (f <= 0.0f) {
                return;
            }
            float currentWidth = this.left.getCurrentWidth() + this.centerWidth + this.right.getCurrentWidth() + AndroidUtilities.m1146dp(18.0f);
            float f2 = this.marginTop + ((1.0f - f) * (-AndroidUtilities.m1146dp(8.0f)));
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set((getWidth() - currentWidth) / 2.0f, AndroidUtilities.dpf2(10.0f) + f2, (getWidth() + currentWidth) / 2.0f, AndroidUtilities.dpf2(33.0f) + f2);
            int alpha = this.backgroundPaint.getAlpha();
            this.backgroundPaint.setAlpha((int) (alpha * f));
            canvas.drawRoundRect(rectF, AndroidUtilities.dpf2(12.0f), AndroidUtilities.dpf2(12.0f), this.backgroundPaint);
            this.backgroundPaint.setAlpha(alpha);
            canvas.save();
            canvas.translate(((getWidth() - currentWidth) / 2.0f) + AndroidUtilities.m1146dp(9.0f), f2 + AndroidUtilities.m1146dp(9.5f));
            AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = this.left;
            animatedTextDrawable.setBounds(0, 0, (int) animatedTextDrawable.getCurrentWidth(), AndroidUtilities.m1146dp(23.0f));
            int i = (int) (f * 255.0f);
            this.left.setAlpha(i);
            this.left.draw(canvas);
            canvas.translate(this.left.getCurrentWidth(), 0.0f);
            canvas.save();
            canvas.translate((-(this.center.getWidth() - this.centerWidth)) / 2.0f, ((AndroidUtilities.m1146dp(23.0f) - this.center.getHeight()) + (this.centerTop / 2.0f)) / 2.0f);
            this.paint.setAlpha(i);
            this.center.draw(canvas);
            canvas.restore();
            canvas.translate(this.centerWidth, 0.0f);
            AnimatedTextView.AnimatedTextDrawable animatedTextDrawable2 = this.right;
            animatedTextDrawable2.setBounds(0, 0, (int) animatedTextDrawable2.getCurrentWidth(), AndroidUtilities.m1146dp(23.0f));
            this.right.setAlpha(i);
            this.right.draw(canvas);
            canvas.restore();
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            this.marginTop = ActionBar.getCurrentActionBarHeight() + AndroidUtilities.statusBarHeight;
            this.left.setOverrideFullWidth(size);
            this.right.setOverrideFullWidth(size);
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(this.marginTop + AndroidUtilities.m1146dp(43.0f), TLObject.FLAG_30));
        }
    }

    public void requestAudioFocus(boolean z) {
        MessageObject messageObject;
        if (z && (messageObject = this.currentMessageObject) != null && !messageObject.isGif()) {
            if (SharedConfig.pauseMusicOnMedia && this.audioManager.requestAudioFocus(this, 3, 2) == 1) {
                this.hasAudioFocus = true;
                return;
            }
            return;
        }
        if (this.hasAudioFocus && this.audioManager.abandonAudioFocus(this) == 1) {
            this.hasAudioFocus = false;
        }
    }

    @Override // android.media.AudioManager.OnAudioFocusChangeListener
    public void onAudioFocusChange(final int i) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda67
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onAudioFocusChange$1(i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAudioFocusChange$1(int i) {
        if (i != -2 && i != -1) {
            if (i == 1 && !isVideoPlaying()) {
                playVideoOrWeb();
                return;
            }
            return;
        }
        if (isVideoPlaying()) {
            pauseVideoOrWeb();
        }
        this.hasAudioFocus = false;
    }

    static {
        String str = "progress";
        if (Build.VERSION.SDK_INT >= 24) {
            VPC_PROGRESS = new FloatProperty(str) { // from class: org.telegram.ui.PhotoViewer.12
                @Override // android.util.FloatProperty
                public void setValue(VideoPlayerControlFrameLayout videoPlayerControlFrameLayout, float f) {
                    videoPlayerControlFrameLayout.setProgress(f);
                }

                @Override // android.util.Property
                public Float get(VideoPlayerControlFrameLayout videoPlayerControlFrameLayout) {
                    return Float.valueOf(videoPlayerControlFrameLayout.getProgress());
                }
            };
        } else {
            VPC_PROGRESS = new Property(Float.class, str) { // from class: org.telegram.ui.PhotoViewer.13
                @Override // android.util.Property
                public void set(VideoPlayerControlFrameLayout videoPlayerControlFrameLayout, Float f) {
                    videoPlayerControlFrameLayout.setProgress(f.floatValue());
                }

                @Override // android.util.Property
                public Float get(VideoPlayerControlFrameLayout videoPlayerControlFrameLayout) {
                    return Float.valueOf(videoPlayerControlFrameLayout.getProgress());
                }
            };
        }
        Instance = null;
        PipInstance = null;
        Instance2 = null;
    }

    public void addPhoto(MessageObject messageObject, int i) throws Resources.NotFoundException {
        if (i != this.classGuid) {
            return;
        }
        if (this.imagesByIds[0].indexOfKey(messageObject.getId()) < 0) {
            if (this.opennedFromMedia) {
                this.imagesArr.add(messageObject);
            } else {
                this.imagesArr.add(0, messageObject);
            }
            this.imagesByIds[0].put(messageObject.getId(), messageObject);
        }
        this.endReached[0] = this.imagesArr.size() == this.totalImagesCount;
        setImages();
    }

    public int getClassGuid() {
        return this.classGuid;
    }

    public void setCaption(CharSequence charSequence) {
        this.hasCaptionForAllMedia = true;
        this.captionForAllMedia = charSequence;
        setCurrentCaption(null, charSequence, false, false);
        updateCaptionTextForCurrentPhoto(null);
    }

    public void setAvatarFor(ImageUpdater.AvatarFor avatarFor) {
        String str;
        int i;
        TLRPC.User user;
        this.setAvatarFor = avatarFor;
        if (this.sendPhotoType == 1) {
            if (useFullWidthSendButton()) {
                this.doneButtonFullWidth.setVisibility(0);
                this.pickerViewSendButton.setVisibility(8);
            } else {
                this.pickerViewSendButton.setVisibility(0);
                this.doneButtonFullWidth.setVisibility(8);
            }
            if (avatarFor != null && (user = avatarFor.fromObject) != null && avatarFor.type == 1 && this.setAvatarFor.self) {
                if (avatarFor.isVideo) {
                    this.photoCropView.setSubtitle(LocaleController.formatString("SetSuggestedVideoTooltip", C2369R.string.SetSuggestedVideoTooltip, user.first_name));
                } else {
                    this.photoCropView.setSubtitle(LocaleController.formatString("SetSuggestedPhotoTooltip", C2369R.string.SetSuggestedPhotoTooltip, user.first_name));
                }
            } else {
                this.photoCropView.setSubtitle(null);
            }
        }
        if (avatarFor != null) {
            if (avatarFor.type == 2) {
                if (avatarFor.isVideo) {
                    str = "SuggestVideo";
                    i = C2369R.string.SuggestVideo;
                } else {
                    str = "SuggestPhoto";
                    i = C2369R.string.SuggestPhoto;
                }
                setTitle(LocaleController.getString(str, i));
            }
            if (avatarFor.isVideo) {
                this.videoAvatarTooltip.setText(LocaleController.getString("SetCover", C2369R.string.SetCover));
            }
            this.actionBar.setBackground(null);
            this.actionBar.setElevation(2.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean useFullWidthSendButton() {
        ImageUpdater.AvatarFor avatarFor = this.setAvatarFor;
        return (avatarFor == null || !avatarFor.self || avatarFor.isVideo) ? false : true;
    }

    /* loaded from: classes5.dex */
    private static class SavedVideoPosition {
        public final float position;
        public final long timestamp;

        public SavedVideoPosition(float f, long j) {
            this.position = f;
            this.timestamp = j;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onLinkClick(ClickableSpan clickableSpan, TextView textView) throws NumberFormatException {
        if (clickableSpan instanceof URLSpan) {
            String url = ((URLSpan) clickableSpan).getURL();
            if (url.startsWith(MediaStreamTrack.VIDEO_TRACK_KIND)) {
                if (this.videoPlayer == null || this.currentMessageObject == null) {
                    return;
                }
                int iIntValue = Utilities.parseInt((CharSequence) url).intValue();
                if (this.videoPlayer.getDuration() == -9223372036854775807L) {
                    this.seekToProgressPending = iIntValue / ((float) this.currentMessageObject.getDuration());
                    return;
                }
                long j = iIntValue * 1000;
                this.videoPlayer.seekTo(j);
                this.videoPlayerSeekbar.setProgress(j / this.videoPlayer.getDuration(), true);
                this.videoPlayerSeekbarView.invalidate();
                return;
            }
            if (url.startsWith("#")) {
                if (this.parentActivity instanceof LaunchActivity) {
                    DialogsActivity dialogsActivity = new DialogsActivity(null);
                    dialogsActivity.setSearchString(url);
                    ((LaunchActivity) this.parentActivity).presentFragment(dialogsActivity, false, true);
                    closePhoto(false, false);
                    return;
                }
                return;
            }
            if (this.parentChatActivity != null && ((clickableSpan instanceof URLSpanReplacement) || AndroidUtilities.shouldShowUrlInAlert(url))) {
                AlertsCreator.showOpenUrlAlert(this.parentChatActivity, url, true, true);
                return;
            } else {
                clickableSpan.onClick(textView);
                return;
            }
        }
        clickableSpan.onClick(textView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Can't wrap try/catch for region: R(10:7|(3:9|62|10)(1:13)|14|(1:16)(2:18|(6:20|55|60|56|57|58)(1:(6:26|(1:37)|38|(2:40|(1:42)(2:43|(1:45)))|46|(2:48|(1:50))(2:51|(1:54)))))|17|55|60|56|57|58) */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onLinkLongPress(final android.text.style.ClickableSpan r17, final android.widget.TextView r18, final java.lang.Runnable r19) {
        /*
            Method dump skipped, instructions count: 396
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.onLinkLongPress(android.text.style.ClickableSpan, android.widget.TextView, java.lang.Runnable):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLinkLongPress$2(ClickableSpan clickableSpan, TextView textView, String str, boolean z, DialogInterface dialogInterface, int i) throws NumberFormatException {
        String string;
        if (i == 0) {
            onLinkClick(clickableSpan, textView);
            return;
        }
        if (i == 1) {
            AndroidUtilities.addToClipboard(str);
            if (z) {
                string = LocaleController.getString("PhoneCopied", C2369R.string.PhoneCopied);
            } else if (str.startsWith("#")) {
                string = LocaleController.getString("HashtagCopied", C2369R.string.HashtagCopied);
            } else if (str.startsWith("@")) {
                string = LocaleController.getString("UsernameCopied", C2369R.string.UsernameCopied);
            } else {
                string = LocaleController.getString("LinkCopied", C2369R.string.LinkCopied);
            }
            if (AndroidUtilities.shouldShowClipboardToast()) {
                BulletinFactory.m1266of(this.containerView, this.resourcesProvider).createSimpleBulletin(C2369R.raw.voip_invite, string).show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelFlashAnimations() {
        View view = this.flashView;
        if (view != null) {
            view.animate().setListener(null).cancel();
            this.flashView.setAlpha(0.0f);
        }
        AnimatorSet animatorSet = this.flashAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.flashAnimator = null;
        }
        PhotoCropView photoCropView = this.photoCropView;
        if (photoCropView != null) {
            photoCropView.cancelThumbAnimation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelVideoPlayRunnable() {
        Runnable runnable = this.videoPlayRunnable;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
            this.videoPlayRunnable = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getCurrentVideoPosition() {
        PhotoViewerWebView photoViewerWebView = this.photoViewerWebView;
        if (photoViewerWebView != null && photoViewerWebView.isControllable()) {
            return this.photoViewerWebView.getCurrentPosition();
        }
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer == null) {
            return 0L;
        }
        return videoPlayer.getCurrentPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getVideoDuration() {
        PhotoViewerWebView photoViewerWebView = this.photoViewerWebView;
        if (photoViewerWebView != null && photoViewerWebView.isControllable()) {
            return this.photoViewerWebView.getVideoDuration();
        }
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer == null) {
            return 0L;
        }
        return videoPlayer.getDuration();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void seekVideoOrWebTo(long j) {
        PhotoViewerWebView photoViewerWebView = this.photoViewerWebView;
        if (photoViewerWebView != null && photoViewerWebView.isControllable()) {
            this.photoViewerWebView.seekTo(j);
        } else {
            VideoPlayer videoPlayer = this.videoPlayer;
            if (videoPlayer != null) {
                videoPlayer.seekTo(j);
            }
        }
        updateVideoPlayerTime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isVideoPlaying() {
        PhotoViewerWebView photoViewerWebView = this.photoViewerWebView;
        if (photoViewerWebView != null && photoViewerWebView.isControllable()) {
            return this.photoViewerWebView.isPlaying();
        }
        VideoPlayer videoPlayer = this.videoPlayer;
        return videoPlayer != null && videoPlayer.isPlaying();
    }

    /* renamed from: org.telegram.ui.PhotoViewer$6 */
    class RunnableC58336 implements Runnable {
        RunnableC58336() {
        }

        @Override // java.lang.Runnable
        public void run() {
            float bufferedProgressFromPosition;
            if (PhotoViewer.this.videoPlayer != null || (PhotoViewer.this.photoViewerWebView != null && PhotoViewer.this.photoViewerWebView.isControllable())) {
                if (PhotoViewer.this.isCurrentVideo) {
                    if (!PhotoViewer.this.videoTimelineView.isDragging()) {
                        float currentVideoPosition = (!PhotoViewer.this.shownControlsByEnd || PhotoViewer.this.actionBarWasShownBeforeByEnd) ? PhotoViewer.this.getCurrentVideoPosition() / PhotoViewer.this.getVideoDuration() : 0.0f;
                        if (PhotoViewer.this.inPreview || (PhotoViewer.this.currentEditMode == 0 && PhotoViewer.this.videoTimelineViewContainer.getVisibility() != 0)) {
                            if (PhotoViewer.this.sendPhotoType != 1) {
                                PhotoViewer.this.videoTimelineView.setProgress(currentVideoPosition);
                            }
                        } else if (currentVideoPosition >= PhotoViewer.this.videoTimelineView.getRightProgress()) {
                            PhotoViewer.this.videoTimelineView.setProgress(PhotoViewer.this.videoTimelineView.getLeftProgress());
                            PhotoViewer.this.videoPlayer.seekTo((int) (PhotoViewer.this.videoTimelineView.getLeftProgress() * PhotoViewer.this.getVideoDuration()));
                            PhotoViewer.this.manuallyPaused = false;
                            PhotoViewer.this.cancelVideoPlayRunnable();
                            if (PhotoViewer.this.muteVideo || PhotoViewer.this.sendPhotoType == 1 || PhotoViewer.this.currentEditMode != 0 || PhotoViewer.this.switchingToMode > 0) {
                                PhotoViewer.this.playVideoOrWeb();
                            } else {
                                PhotoViewer.this.pauseVideoOrWeb();
                            }
                            PhotoViewer.this.containerView.invalidate();
                        } else {
                            PhotoViewer.this.videoTimelineView.setProgress(currentVideoPosition);
                        }
                        PhotoViewer.this.updateVideoPlayerTime();
                    }
                } else {
                    final float currentVideoPosition2 = PhotoViewer.this.getCurrentVideoPosition() / PhotoViewer.this.getVideoDuration();
                    if (PhotoViewer.this.shownControlsByEnd && !PhotoViewer.this.actionBarWasShownBeforeByEnd) {
                        currentVideoPosition2 = 0.0f;
                    }
                    if (PhotoViewer.this.currentVideoFinishedLoading) {
                        bufferedProgressFromPosition = 1.0f;
                    } else {
                        long jElapsedRealtime = SystemClock.elapsedRealtime();
                        if (Math.abs(jElapsedRealtime - PhotoViewer.this.lastBufferedPositionCheck) >= 500) {
                            if (PhotoViewer.this.photoViewerWebView != null && PhotoViewer.this.photoViewerWebView.isControllable()) {
                                bufferedProgressFromPosition = PhotoViewer.this.photoViewerWebView.getBufferedPosition();
                            } else if (PhotoViewer.this.isStreaming) {
                                bufferedProgressFromPosition = FileLoader.getInstance(PhotoViewer.this.currentAccount).getBufferedProgressFromPosition(PhotoViewer.this.seekToProgressPending != 0.0f ? PhotoViewer.this.seekToProgressPending : currentVideoPosition2, PhotoViewer.this.currentFileNames[0]);
                            } else {
                                bufferedProgressFromPosition = 1.0f;
                            }
                            PhotoViewer.this.lastBufferedPositionCheck = jElapsedRealtime;
                        } else {
                            bufferedProgressFromPosition = -1.0f;
                        }
                    }
                    if (PhotoViewer.this.inPreview || PhotoViewer.this.videoTimelineViewContainer.getVisibility() != 0) {
                        if (PhotoViewer.this.seekToProgressPending == 0.0f) {
                            PhotoViewer photoViewer = PhotoViewer.this;
                            OldVideoPlayerRewinder oldVideoPlayerRewinder = photoViewer.longVideoPlayerRewinder;
                            if (oldVideoPlayerRewinder.rewindCount == 0 || (!oldVideoPlayerRewinder.rewindByBackSeek && !photoViewer.videoPlayerRewinder.rewindByBackSeek)) {
                                PhotoViewer.this.videoPlayerSeekbar.setProgress(currentVideoPosition2, false);
                            }
                        }
                        if (bufferedProgressFromPosition != -1.0f) {
                            PhotoViewer.this.videoPlayerSeekbar.setBufferedProgress(bufferedProgressFromPosition);
                            PipVideoOverlay.setBufferedProgress(bufferedProgressFromPosition);
                        }
                    } else if (currentVideoPosition2 >= PhotoViewer.this.videoTimelineView.getRightProgress()) {
                        PhotoViewer.this.manuallyPaused = false;
                        PhotoViewer.this.pauseVideoOrWeb();
                        PhotoViewer.this.videoPlayerSeekbar.setProgress(0.0f);
                        PhotoViewer.this.seekVideoOrWebTo((int) (r1.videoTimelineView.getLeftProgress() * PhotoViewer.this.getVideoDuration()));
                        PhotoViewer.this.containerView.invalidate();
                    } else {
                        float leftProgress = currentVideoPosition2 - PhotoViewer.this.videoTimelineView.getLeftProgress();
                        if (leftProgress < 0.0f) {
                            leftProgress = 0.0f;
                        }
                        currentVideoPosition2 = leftProgress / (PhotoViewer.this.videoTimelineView.getRightProgress() - PhotoViewer.this.videoTimelineView.getLeftProgress());
                        if (currentVideoPosition2 > 1.0f) {
                            currentVideoPosition2 = 1.0f;
                        }
                        PhotoViewer.this.videoPlayerSeekbar.setProgress(currentVideoPosition2);
                    }
                    PhotoViewer.this.videoPlayerSeekbarView.invalidate();
                    if (PhotoViewer.this.shouldSavePositionForCurrentVideo != null && currentVideoPosition2 >= 0.0f && SystemClock.elapsedRealtime() - PhotoViewer.this.lastSaveTime >= 1000) {
                        final String str = PhotoViewer.this.shouldSavePositionForCurrentVideo;
                        PhotoViewer.this.lastSaveTime = SystemClock.elapsedRealtime();
                        if (PhotoViewer.this.currentMessageObject != null) {
                            PhotoViewer.this.currentMessageObject.cachedSavedTimestamp = Float.valueOf(currentVideoPosition2);
                        }
                        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.PhotoViewer$6$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ApplicationLoader.applicationContext.getSharedPreferences("media_saved_pos", 0).edit().putFloat(str, currentVideoPosition2).apply();
                            }
                        });
                    }
                    PhotoViewer.this.updateVideoPlayerTime();
                }
            }
            if (PhotoViewer.this.firstFrameView != null) {
                PhotoViewer.this.firstFrameView.updateAlpha();
            }
            if (PhotoViewer.this.isPlaying) {
                AndroidUtilities.runOnUIThread(PhotoViewer.this.updateProgressRunnable, 17L);
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoViewer$7 */
    class RunnableC58447 implements Runnable {
        RunnableC58447() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (PipVideoOverlay.isVisible()) {
                PipVideoOverlay.dismiss();
                AndroidUtilities.runOnUIThread(this, 250L);
                return;
            }
            PhotoViewer.this.switchingInlineMode = false;
            if (PhotoViewer.this.currentBitmap != null) {
                PhotoViewer.this.currentBitmap.recycle();
                PhotoViewer.this.currentBitmap = null;
            }
            PhotoViewer.this.changingTextureView = true;
            final TextureViewContainer textureViewContainer = new TextureViewContainer(PhotoViewer.this.parentActivity);
            try {
                if (PhotoViewer.this.usedSurfaceView) {
                    Drawable drawable = PhotoViewer.this.textureImageView.getDrawable();
                    if (drawable instanceof BitmapDrawable) {
                        PhotoViewer.this.currentBitmap = ((BitmapDrawable) drawable).getBitmap();
                        if (PhotoViewer.this.currentBitmap != null) {
                            if (PhotoViewer.this.textureImageView != null) {
                                PhotoViewer.this.textureImageView.setVisibility(0);
                                PhotoViewer.this.textureImageView.setImageBitmap(PhotoViewer.this.currentBitmap);
                            }
                            textureViewContainer.imageReceiver.setImageBitmap(PhotoViewer.this.currentBitmap);
                        }
                    } else {
                        PhotoViewer photoViewer = PhotoViewer.this;
                        photoViewer.currentBitmap = Bitmaps.createBitmap(photoViewer.videoSurfaceView.getWidth(), PhotoViewer.this.videoSurfaceView.getHeight(), Bitmap.Config.ARGB_8888);
                        if (Build.VERSION.SDK_INT >= 24) {
                            AndroidUtilities.getBitmapFromSurface(PhotoViewer.this.videoSurfaceView, PhotoViewer.this.currentBitmap, new Runnable() { // from class: org.telegram.ui.PhotoViewer$7$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$run$0(textureViewContainer);
                                }
                            });
                        }
                    }
                } else {
                    PhotoViewer photoViewer2 = PhotoViewer.this;
                    photoViewer2.currentBitmap = Bitmaps.createBitmap(photoViewer2.videoTextureView.getWidth(), PhotoViewer.this.videoTextureView.getHeight(), Bitmap.Config.ARGB_8888);
                    PhotoViewer.this.videoTextureView.getBitmap(PhotoViewer.this.currentBitmap);
                    if (PhotoViewer.this.currentBitmap != null) {
                        if (PhotoViewer.this.textureImageView != null) {
                            PhotoViewer.this.textureImageView.setVisibility(0);
                            PhotoViewer.this.textureImageView.setImageBitmap(PhotoViewer.this.currentBitmap);
                        }
                        textureViewContainer.imageReceiver.setImageBitmap(PhotoViewer.this.currentBitmap);
                    }
                }
            } catch (Throwable th) {
                if (PhotoViewer.this.currentBitmap != null) {
                    PhotoViewer.this.currentBitmap.recycle();
                    PhotoViewer.this.currentBitmap = null;
                }
                FileLog.m1160e(th);
            }
            PhotoViewer.this.isInline = true;
            PhotoViewer photoViewer3 = PhotoViewer.this;
            photoViewer3.changedTextureView = textureViewContainer.textureView;
            if (PipVideoOverlay.show(false, photoViewer3.parentActivity, textureViewContainer, PhotoViewer.this.videoWidth, PhotoViewer.this.videoHeight, PhotoViewer.this.pipVideoOverlayAnimateFlag)) {
                PipVideoOverlay.setPhotoViewer(PhotoViewer.this);
            }
            PhotoViewer.this.pipVideoOverlayAnimateFlag = true;
            if (PhotoViewer.this.usedSurfaceView) {
                if (PhotoViewer.this.aspectRatioFrameLayout != null) {
                    PhotoViewer.this.aspectRatioFrameLayout.removeView(PhotoViewer.this.videoTextureView);
                    PhotoViewer.this.aspectRatioFrameLayout.removeView(PhotoViewer.this.videoSurfaceView);
                }
                PhotoViewer.this.videoPlayer.setSurfaceView(null);
                PhotoViewer.this.videoPlayer.setTextureView(null);
                PhotoViewer.this.videoPlayer.play();
                PhotoViewer.this.videoPlayer.setTextureView(PhotoViewer.this.changedTextureView);
                PhotoViewer.this.checkChangedTextureView(true);
                PhotoViewer.this.changedTextureView.setVisibility(0);
                return;
            }
            PhotoViewer.this.changedTextureView.setVisibility(4);
            if (PhotoViewer.this.aspectRatioFrameLayout != null) {
                PhotoViewer.this.aspectRatioFrameLayout.removeView(PhotoViewer.this.videoTextureView);
                PhotoViewer.this.aspectRatioFrameLayout.removeView(PhotoViewer.this.videoSurfaceView);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(TextureViewContainer textureViewContainer) {
            if (PhotoViewer.this.currentBitmap != null) {
                if (PhotoViewer.this.textureImageView != null) {
                    PhotoViewer.this.textureImageView.setVisibility(0);
                    PhotoViewer.this.textureImageView.setImageBitmap(PhotoViewer.this.currentBitmap);
                }
                textureViewContainer.imageReceiver.setImageBitmap(PhotoViewer.this.currentBitmap);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkChangedTextureView(boolean z) {
        if (z) {
            TextureView textureView = this.changedTextureView;
            if (textureView == null) {
                return;
            }
            textureView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserverOnPreDrawListenerC58649());
            this.changedTextureView.invalidate();
            return;
        }
        if (this.waitingForFirstTextureUpload == 2) {
            ImageView imageView = this.textureImageView;
            if (imageView != null) {
                imageView.setVisibility(4);
                this.textureImageView.setImageDrawable(null);
                Bitmap bitmap = this.currentBitmap;
                if (bitmap != null) {
                    bitmap.recycle();
                    this.currentBitmap = null;
                }
            }
            this.switchingInlineMode = false;
            final View view = this.usedSurfaceView ? this.videoSurfaceView : this.videoTextureView;
            AspectRatioFrameLayout aspectRatioFrameLayout = this.aspectRatioFrameLayout;
            if (aspectRatioFrameLayout == null) {
                return;
            }
            aspectRatioFrameLayout.getLocationInWindow(this.pipPosition);
            this.pipPosition[1] = (int) (r4[1] - this.containerView.getTranslationY());
            ImageView imageView2 = this.textureImageView;
            if (imageView2 != null) {
                imageView2.setTranslationX(imageView2.getTranslationX() + getLeftInset());
            }
            if (view != null) {
                view.setTranslationX((view.getTranslationX() + getLeftInset()) - this.aspectRatioFrameLayout.getX());
            }
            FirstFrameView firstFrameView = this.firstFrameView;
            if (firstFrameView != null) {
                firstFrameView.setTranslationX(view.getTranslationX());
            }
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda152
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$checkChangedTextureView$4(valueAnimator);
                }
            });
            float x = this.usedSurfaceView ? 0.0f : this.pipPosition[0] - this.aspectRatioFrameLayout.getX();
            float y = this.usedSurfaceView ? 0.0f : this.pipPosition[1] - this.aspectRatioFrameLayout.getY();
            AnimatorSet animatorSet = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            arrayList.add(valueAnimatorOfFloat);
            ImageView imageView3 = this.textureImageView;
            Property property = View.SCALE_X;
            arrayList.add(ObjectAnimator.ofFloat(imageView3, (Property<ImageView, Float>) property, 1.0f));
            ImageView imageView4 = this.textureImageView;
            Property property2 = View.SCALE_Y;
            arrayList.add(ObjectAnimator.ofFloat(imageView4, (Property<ImageView, Float>) property2, 1.0f));
            ImageView imageView5 = this.textureImageView;
            Property property3 = View.TRANSLATION_X;
            arrayList.add(ObjectAnimator.ofFloat(imageView5, (Property<ImageView, Float>) property3, this.usedSurfaceView ? 0.0f : this.pipPosition[0]));
            ImageView imageView6 = this.textureImageView;
            Property property4 = View.TRANSLATION_Y;
            arrayList.add(ObjectAnimator.ofFloat(imageView6, (Property<ImageView, Float>) property4, this.usedSurfaceView ? 0.0f : this.pipPosition[1]));
            arrayList.add(ObjectAnimator.ofFloat(view, (Property<View, Float>) property, 1.0f));
            arrayList.add(ObjectAnimator.ofFloat(view, (Property<View, Float>) property2, 1.0f));
            arrayList.add(ObjectAnimator.ofFloat(view, (Property<View, Float>) property3, x));
            arrayList.add(ObjectAnimator.ofFloat(view, (Property<View, Float>) property4, y));
            arrayList.add(ObjectAnimator.ofInt(this.backgroundDrawable, (Property<BackgroundDrawable, Integer>) AnimationProperties.COLOR_DRAWABLE_ALPHA, 255));
            FirstFrameView firstFrameView2 = this.firstFrameView;
            if (firstFrameView2 != null) {
                arrayList.add(ObjectAnimator.ofFloat(firstFrameView2, (Property<FirstFrameView, Float>) property, 1.0f));
                arrayList.add(ObjectAnimator.ofFloat(this.firstFrameView, (Property<FirstFrameView, Float>) property2, 1.0f));
                arrayList.add(ObjectAnimator.ofFloat(this.firstFrameView, (Property<FirstFrameView, Float>) property3, x));
                arrayList.add(ObjectAnimator.ofFloat(this.firstFrameView, (Property<FirstFrameView, Float>) property4, y));
            }
            float f = PipVideoOverlay.getPipRect(false, this.aspectRatioFrameLayout.getAspectRatio()).width;
            view.getWidth();
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda153
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$checkChangedTextureView$5(view, valueAnimator);
                }
            });
            arrayList.add(valueAnimatorOfFloat2);
            animatorSet.playTogether(arrayList);
            DecelerateInterpolator decelerateInterpolator2 = new DecelerateInterpolator();
            animatorSet.setInterpolator(decelerateInterpolator2);
            animatorSet.setDuration(250L);
            SurfaceView surfaceView = this.videoSurfaceView;
            if (surfaceView != null) {
                surfaceView.setVisibility(0);
            }
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.10
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    PhotoViewer.this.pipAnimationInProgress = false;
                    view.setOutlineProvider(null);
                    if (PhotoViewer.this.textureImageView != null) {
                        PhotoViewer.this.textureImageView.setOutlineProvider(null);
                    }
                    if (PhotoViewer.this.firstFrameView != null) {
                        PhotoViewer.this.firstFrameView.setOutlineProvider(null);
                    }
                    if (PhotoViewer.this.videoSurfaceView != null) {
                        PhotoViewer.this.videoSurfaceView.setVisibility(0);
                    }
                }
            });
            animatorSet.start();
            toggleActionBar(true, true, new ActionBarToggleParams().enableStatusBarAnimation(false).enableTranslationAnimation(false).animationDuration(MediaDataController.MAX_LINKS_COUNT).animationInterpolator(decelerateInterpolator2));
            this.waitingForFirstTextureUpload = 0;
        }
    }

    /* renamed from: org.telegram.ui.PhotoViewer$9 */
    /* loaded from: classes5.dex */
    class ViewTreeObserverOnPreDrawListenerC58649 implements ViewTreeObserver.OnPreDrawListener {
        ViewTreeObserverOnPreDrawListenerC58649() {
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            PhotoViewer.this.changedTextureView.getViewTreeObserver().removeOnPreDrawListener(this);
            if (PhotoViewer.this.textureImageView != null) {
                if (PhotoViewer.this.isInline) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$9$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onPreDraw$0();
                        }
                    }, 300L);
                } else {
                    PhotoViewer.this.textureImageView.setVisibility(4);
                    PhotoViewer.this.textureImageView.setImageDrawable(null);
                    if (PhotoViewer.this.currentBitmap != null) {
                        PhotoViewer.this.currentBitmap.recycle();
                        PhotoViewer.this.currentBitmap = null;
                    }
                }
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$9$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onPreDraw$1();
                }
            });
            PhotoViewer.this.waitingForFirstTextureUpload = 0;
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreDraw$0() {
            PhotoViewer.this.textureImageView.setVisibility(4);
            PhotoViewer.this.textureImageView.setImageDrawable(null);
            if (PhotoViewer.this.currentBitmap != null) {
                PhotoViewer.this.currentBitmap.recycle();
                PhotoViewer.this.currentBitmap = null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreDraw$1() {
            if (PhotoViewer.this.isInline) {
                PhotoViewer.this.dismissInternal();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkChangedTextureView$4(ValueAnimator valueAnimator) {
        this.clippingImageProgress = 1.0f - ((Float) valueAnimator.getAnimatedValue()).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkChangedTextureView$5(View view, ValueAnimator valueAnimator) {
        this.inlineOutAnimationProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        view.invalidateOutline();
        ImageView imageView = this.textureImageView;
        if (imageView != null) {
            imageView.invalidateOutline();
        }
        FirstFrameView firstFrameView = this.firstFrameView;
        if (firstFrameView != null) {
            firstFrameView.invalidateOutline();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6() {
        if (this.isVisible && this.animationInProgress == 0) {
            updateContainerFlags(this.isActionBarVisible);
        }
    }

    public static class EditState {
        public long averageDuration;
        public MediaController.CropState cropState;
        public ArrayList croppedMediaEntities;
        public String croppedPaintPath;
        public ArrayList mediaEntities;
        public String paintPath;
        public MediaController.SavedFilterState savedFilterState;

        public void reset() {
            this.paintPath = null;
            this.cropState = null;
            this.savedFilterState = null;
            this.mediaEntities = null;
            this.croppedPaintPath = null;
            this.croppedMediaEntities = null;
            this.averageDuration = 0L;
        }
    }

    /* loaded from: classes5.dex */
    private class SavedState {
        private int index;
        private ArrayList messages;
        private PhotoViewerProvider provider;

        public SavedState(int i, ArrayList arrayList, PhotoViewerProvider photoViewerProvider) {
            this.messages = arrayList;
            this.index = i;
            this.provider = photoViewerProvider;
        }

        public void restore() throws Resources.NotFoundException, NumberFormatException {
            PhotoViewer.this.placeProvider = this.provider;
            PhotoViewer.this.windowLayoutParams.flags = -2147286784;
            PhotoViewer.this.windowLayoutParams.softInputMode = 272;
            PhotoViewer.this.windowView.setFocusable(false);
            PhotoViewer.this.containerView.setFocusable(false);
            PhotoViewer.this.backgroundDrawable.setAlpha(255);
            PhotoViewer.this.containerView.setAlpha(1.0f);
            PhotoViewer photoViewer = PhotoViewer.this;
            ArrayList arrayList = this.messages;
            int i = this.index;
            photoViewer.onPhotoShow(null, null, null, null, arrayList, null, null, i, this.provider.getPlaceForPhoto((MessageObject) arrayList.get(i), null, this.index, true, false));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class BackgroundDrawable extends ColorDrawable {
        private boolean allowDrawContent;
        private Runnable drawRunnable;
        private final Paint paint;
        private final RectF rect;
        private final RectF visibleRect;

        public BackgroundDrawable(int i) {
            super(i);
            this.rect = new RectF();
            this.visibleRect = new RectF();
            Paint paint = new Paint(1);
            this.paint = paint;
            paint.setColor(i);
        }

        @Override // android.graphics.drawable.ColorDrawable, android.graphics.drawable.Drawable
        @Keep
        public void setAlpha(int i) {
            if (PhotoViewer.this.parentActivity instanceof LaunchActivity) {
                this.allowDrawContent = (PhotoViewer.this.isVisible && i == 255) ? false : true;
                ((LaunchActivity) PhotoViewer.this.parentActivity).drawerLayoutContainer.setAllowDrawContent(this.allowDrawContent);
                if (PhotoViewer.this.parentAlert != null) {
                    if (!this.allowDrawContent) {
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$BackgroundDrawable$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$setAlpha$0();
                            }
                        }, 50L);
                    } else {
                        PhotoViewer.this.parentAlert.setAllowDrawContent(true);
                    }
                }
            }
            super.setAlpha(i);
            this.paint.setAlpha(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setAlpha$0() {
            if (PhotoViewer.this.parentAlert != null) {
                PhotoViewer.this.parentAlert.setAllowDrawContent(this.allowDrawContent);
            }
        }

        @Override // android.graphics.drawable.ColorDrawable, android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            Runnable runnable;
            if (PhotoViewer.this.textureViewSkipRender) {
                return;
            }
            if (PhotoViewer.this.animationInProgress != 0 && !AndroidUtilities.isTablet() && PhotoViewer.this.currentPlaceObject != null && PhotoViewer.this.currentPlaceObject.animatingImageView != null) {
                PhotoViewer.this.animatingImageView.getClippedVisibleRect(this.visibleRect);
                if (!this.visibleRect.isEmpty()) {
                    this.visibleRect.inset(AndroidUtilities.m1146dp(1.0f), AndroidUtilities.m1146dp(1.0f));
                    Rect bounds = getBounds();
                    float f = bounds.right;
                    float f2 = bounds.bottom;
                    for (int i = 0; i < 4; i++) {
                        if (i == 0) {
                            RectF rectF = this.rect;
                            RectF rectF2 = this.visibleRect;
                            rectF.set(0.0f, rectF2.top, rectF2.left, rectF2.bottom);
                        } else if (i == 1) {
                            this.rect.set(0.0f, 0.0f, f, this.visibleRect.top);
                        } else if (i == 2) {
                            RectF rectF3 = this.rect;
                            RectF rectF4 = this.visibleRect;
                            rectF3.set(rectF4.right, rectF4.top, f, rectF4.bottom);
                        } else if (i == 3) {
                            this.rect.set(0.0f, this.visibleRect.bottom, f, f2);
                        }
                        canvas.drawRect(this.rect, this.paint);
                    }
                }
            } else {
                super.draw(canvas);
            }
            if (getAlpha() == 0 || (runnable = this.drawRunnable) == null) {
                return;
            }
            AndroidUtilities.runOnUIThread(runnable);
            this.drawRunnable = null;
        }
    }

    /* loaded from: classes5.dex */
    private static class SelectedPhotosListView extends RecyclerListView {
        private Drawable arrowDrawable;
        private Paint paint;
        private RectF rect;

        public SelectedPhotosListView(Context context) {
            super(context);
            this.paint = new Paint(1);
            this.rect = new RectF();
            setWillNotDraw(false);
            setClipToPadding(false);
            setTranslationY(-AndroidUtilities.m1146dp(10.0f));
            DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator() { // from class: org.telegram.ui.PhotoViewer.SelectedPhotosListView.1
                @Override // androidx.recyclerview.widget.DefaultItemAnimator
                protected void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                    SelectedPhotosListView.this.invalidate();
                }
            };
            setItemAnimator(defaultItemAnimator);
            defaultItemAnimator.setDelayAnimations(false);
            defaultItemAnimator.setSupportsChangeAnimations(false);
            setPadding(AndroidUtilities.m1146dp(12.0f), AndroidUtilities.m1146dp(12.0f), AndroidUtilities.m1146dp(12.0f), AndroidUtilities.m1146dp(6.0f));
            this.paint.setColor(2130706432);
            this.arrowDrawable = context.getResources().getDrawable(C2369R.drawable.photo_tooltip2).mutate();
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            int childCount = getChildCount();
            if (childCount > 0) {
                int measuredWidth = getMeasuredWidth() - AndroidUtilities.m1146dp(87.0f);
                Drawable drawable = this.arrowDrawable;
                drawable.setBounds(measuredWidth, 0, drawable.getIntrinsicWidth() + measuredWidth, AndroidUtilities.m1146dp(6.0f));
                this.arrowDrawable.draw(canvas);
                int iMin = ConnectionsManager.DEFAULT_DATACENTER_ID;
                int iMax = TLObject.FLAG_31;
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    iMin = (int) Math.min(iMin, Math.floor(childAt.getX()));
                    iMax = (int) Math.max(iMax, Math.ceil(childAt.getX() + childAt.getMeasuredWidth()));
                }
                if (iMin == Integer.MAX_VALUE || iMax == Integer.MIN_VALUE) {
                    return;
                }
                this.rect.set(iMin - AndroidUtilities.m1146dp(6.0f), AndroidUtilities.m1146dp(6.0f), iMax + AndroidUtilities.m1146dp(6.0f), AndroidUtilities.m1146dp(103.0f));
                canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(8.0f), AndroidUtilities.m1146dp(8.0f), this.paint);
            }
        }
    }

    /* loaded from: classes5.dex */
    private static class CounterView extends View {
        private int currentCount;
        private int height;
        private Paint paint;
        private RectF rect;
        private float rotation;
        private StaticLayout staticLayout;
        private TextPaint textPaint;
        private int width;

        public CounterView(Context context) {
            super(context);
            this.currentCount = 0;
            TextPaint textPaint = new TextPaint(1);
            this.textPaint = textPaint;
            textPaint.setTextSize(AndroidUtilities.m1146dp(15.0f));
            this.textPaint.setTypeface(AndroidUtilities.bold());
            this.textPaint.setColor(-1);
            Paint paint = new Paint(1);
            this.paint = paint;
            paint.setColor(-1);
            this.paint.setStrokeWidth(AndroidUtilities.m1146dp(2.0f));
            this.paint.setStyle(Paint.Style.STROKE);
            this.paint.setStrokeJoin(Paint.Join.ROUND);
            this.rect = new RectF();
            setCount(0);
        }

        @Override // android.view.View
        @Keep
        public void setScaleX(float f) {
            super.setScaleX(f);
            invalidate();
        }

        @Override // android.view.View
        @Keep
        public void setRotationX(float f) {
            this.rotation = f;
            invalidate();
        }

        @Override // android.view.View
        public float getRotationX() {
            return this.rotation;
        }

        public void setCount(int i) {
            StaticLayout staticLayout = new StaticLayout("" + Math.max(1, i), this.textPaint, AndroidUtilities.m1146dp(100.0f), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            this.staticLayout = staticLayout;
            this.width = (int) Math.ceil((double) staticLayout.getLineWidth(0));
            this.height = this.staticLayout.getLineBottom(0);
            AnimatorSet animatorSet = new AnimatorSet();
            Property property = View.SCALE_Y;
            Property property2 = View.SCALE_X;
            if (i == 0) {
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, (Property<CounterView, Float>) property2, 0.0f);
                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this, (Property<CounterView, Float>) property, 0.0f);
                Paint paint = this.paint;
                Property property3 = AnimationProperties.PAINT_ALPHA;
                animatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, ObjectAnimator.ofInt(paint, (Property<Paint, Integer>) property3, 0), ObjectAnimator.ofInt(this.textPaint, (Property<TextPaint, Integer>) property3, 0));
                animatorSet.setInterpolator(new DecelerateInterpolator());
            } else {
                int i2 = this.currentCount;
                if (i2 == 0) {
                    ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this, (Property<CounterView, Float>) property2, 0.0f, 1.0f);
                    ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(this, (Property<CounterView, Float>) property, 0.0f, 1.0f);
                    Paint paint2 = this.paint;
                    Property property4 = AnimationProperties.PAINT_ALPHA;
                    animatorSet.playTogether(objectAnimatorOfFloat3, objectAnimatorOfFloat4, ObjectAnimator.ofInt(paint2, (Property<Paint, Integer>) property4, 0, 255), ObjectAnimator.ofInt(this.textPaint, (Property<TextPaint, Integer>) property4, 0, 255));
                    animatorSet.setInterpolator(new DecelerateInterpolator());
                } else if (i < i2) {
                    animatorSet.playTogether(ObjectAnimator.ofFloat(this, (Property<CounterView, Float>) property2, 1.1f, 1.0f), ObjectAnimator.ofFloat(this, (Property<CounterView, Float>) property, 1.1f, 1.0f));
                    animatorSet.setInterpolator(new OvershootInterpolator());
                } else {
                    animatorSet.playTogether(ObjectAnimator.ofFloat(this, (Property<CounterView, Float>) property2, 0.9f, 1.0f), ObjectAnimator.ofFloat(this, (Property<CounterView, Float>) property, 0.9f, 1.0f));
                    animatorSet.setInterpolator(new OvershootInterpolator());
                }
            }
            animatorSet.setDuration(180L);
            animatorSet.start();
            requestLayout();
            this.currentCount = i;
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(Math.max(this.width + AndroidUtilities.m1146dp(20.0f), AndroidUtilities.m1146dp(30.0f)), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(40.0f), TLObject.FLAG_30));
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            int measuredHeight = getMeasuredHeight() / 2;
            this.paint.setAlpha(255);
            this.rect.set(AndroidUtilities.m1146dp(1.0f), measuredHeight - AndroidUtilities.m1146dp(14.0f), getMeasuredWidth() - AndroidUtilities.m1146dp(1.0f), measuredHeight + AndroidUtilities.m1146dp(14.0f));
            canvas.drawRoundRect(this.rect, AndroidUtilities.m1146dp(15.0f), AndroidUtilities.m1146dp(15.0f), this.paint);
            if (this.staticLayout != null) {
                this.textPaint.setAlpha((int) ((1.0f - this.rotation) * 255.0f));
                canvas.save();
                canvas.translate((getMeasuredWidth() - this.width) / 2, ((getMeasuredHeight() - this.height) / 2) + AndroidUtilities.dpf2(0.2f) + (this.rotation * AndroidUtilities.m1146dp(5.0f)));
                this.staticLayout.draw(canvas);
                canvas.restore();
                this.paint.setAlpha((int) (this.rotation * 255.0f));
                int iCenterX = (int) this.rect.centerX();
                int iCenterY = (int) (((int) this.rect.centerY()) - (AndroidUtilities.m1146dp(5.0f) * (1.0f - this.rotation)));
                canvas.drawLine(AndroidUtilities.m1146dp(5.0f) + iCenterX, iCenterY - AndroidUtilities.m1146dp(5.0f), iCenterX - AndroidUtilities.m1146dp(5.0f), AndroidUtilities.m1146dp(5.0f) + iCenterY, this.paint);
                canvas.drawLine(iCenterX - AndroidUtilities.m1146dp(5.0f), iCenterY - AndroidUtilities.m1146dp(5.0f), iCenterX + AndroidUtilities.m1146dp(5.0f), iCenterY + AndroidUtilities.m1146dp(5.0f), this.paint);
            }
        }
    }

    /* loaded from: classes5.dex */
    private class PhotoProgressView {
        private View parent;
        private final CombinedDrawable playDrawable;
        private final PlayPauseDrawable playPauseDrawable;
        private boolean visible;
        private long lastUpdateTime = 0;
        private float radOffset = 0.0f;
        private float currentProgress = 0.0f;
        private float animationProgressStart = 0.0f;
        private long currentProgressTime = 0;
        private float animatedProgressValue = 0.0f;
        private RectF progressRect = new RectF();
        private int backgroundState = -1;
        private int size = AndroidUtilities.m1146dp(64.0f);
        private int previousBackgroundState = -2;
        private float animatedAlphaValue = 1.0f;
        private float[] animAlphas = new float[3];
        private float[] alphas = new float[3];
        private float scale = 1.0f;

        protected abstract void onBackgroundStateUpdated(int i);

        protected abstract void onVisibilityChanged(boolean z);

        public PhotoProgressView(View view) {
            if (PhotoViewer.decelerateInterpolator == null) {
                PhotoViewer.decelerateInterpolator = new DecelerateInterpolator(1.5f);
                PhotoViewer.progressPaint = new Paint(1);
                PhotoViewer.progressPaint.setStyle(Paint.Style.STROKE);
                PhotoViewer.progressPaint.setStrokeCap(Paint.Cap.ROUND);
                PhotoViewer.progressPaint.setStrokeWidth(AndroidUtilities.m1146dp(3.0f));
                PhotoViewer.progressPaint.setColor(-1);
            }
            this.parent = view;
            resetAlphas();
            PlayPauseDrawable playPauseDrawable = new PlayPauseDrawable(28);
            this.playPauseDrawable = playPauseDrawable;
            playPauseDrawable.setDuration(DataTypes.EMPTY);
            this.playDrawable = new CombinedDrawable(ContextCompat.getDrawable(PhotoViewer.this.parentActivity, C2369R.drawable.circle_big).mutate(), playPauseDrawable);
        }

        private void updateAnimation(boolean z) {
            boolean z2;
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j = jCurrentTimeMillis - this.lastUpdateTime;
            if (j > 18) {
                j = 18;
            }
            this.lastUpdateTime = jCurrentTimeMillis;
            int i = 0;
            if (z) {
                if (this.animatedProgressValue == 1.0f && this.currentProgress == 1.0f) {
                    z2 = false;
                } else {
                    this.radOffset += (360 * j) / 3000.0f;
                    float f = this.currentProgress - this.animationProgressStart;
                    if (Math.abs(f) > 0.0f) {
                        long j2 = this.currentProgressTime + j;
                        this.currentProgressTime = j2;
                        if (j2 >= 300) {
                            float f2 = this.currentProgress;
                            this.animatedProgressValue = f2;
                            this.animationProgressStart = f2;
                            this.currentProgressTime = 0L;
                        } else {
                            this.animatedProgressValue = this.animationProgressStart + (f * PhotoViewer.decelerateInterpolator.getInterpolation(this.currentProgressTime / 300.0f));
                        }
                    }
                    z2 = true;
                }
                float f3 = this.animatedAlphaValue;
                if (f3 > 0.0f && this.previousBackgroundState != -2) {
                    float f4 = f3 - (j / 200.0f);
                    this.animatedAlphaValue = f4;
                    if (f4 <= 0.0f) {
                        this.animatedAlphaValue = 0.0f;
                        this.previousBackgroundState = -2;
                    }
                    z2 = true;
                }
            } else {
                z2 = false;
            }
            while (true) {
                float[] fArr = this.alphas;
                if (i >= fArr.length) {
                    break;
                }
                float f5 = fArr[i];
                float[] fArr2 = this.animAlphas;
                float f6 = fArr2[i];
                if (f5 > f6) {
                    fArr2[i] = Math.min(1.0f, f6 + (j / 200.0f));
                } else if (f5 < f6) {
                    fArr2[i] = Math.max(0.0f, f6 - (j / 200.0f));
                } else {
                    i++;
                }
                z2 = true;
                i++;
            }
            if (z2) {
                this.parent.postInvalidateOnAnimation();
            }
        }

        public void setProgress(float f, boolean z) {
            if (!z) {
                this.animatedProgressValue = f;
                this.animationProgressStart = f;
            } else {
                this.animationProgressStart = this.animatedProgressValue;
            }
            this.currentProgress = f;
            this.currentProgressTime = 0L;
            this.parent.invalidate();
        }

        public void setBackgroundState(int i, boolean z, boolean z2) {
            int i2;
            int i3 = this.backgroundState;
            if (i3 == i) {
                return;
            }
            PlayPauseDrawable playPauseDrawable = this.playPauseDrawable;
            if (playPauseDrawable != null) {
                boolean z3 = z2 && (i3 == 3 || i3 == 4);
                if (i == 3) {
                    playPauseDrawable.setPause(false, z3);
                } else if (i == 4) {
                    playPauseDrawable.setPause(true, z3);
                }
                this.playPauseDrawable.setParent(this.parent);
                this.playPauseDrawable.invalidateSelf();
            }
            this.lastUpdateTime = System.currentTimeMillis();
            if (z && (i2 = this.backgroundState) != i) {
                this.previousBackgroundState = i2;
                this.animatedAlphaValue = 1.0f;
            } else {
                this.previousBackgroundState = -2;
            }
            this.backgroundState = i;
            onBackgroundStateUpdated(i);
            this.parent.invalidate();
        }

        public void setAlpha(float f) {
            setIndexedAlpha(0, f, false);
        }

        public void setScale(float f) {
            this.scale = f;
        }

        public void setIndexedAlpha(int i, float f, boolean z) {
            float[] fArr = this.alphas;
            if (fArr[i] != f) {
                fArr[i] = f;
                if (!z) {
                    this.animAlphas[i] = f;
                }
                checkVisibility();
                this.parent.invalidate();
            }
        }

        public void resetAlphas() {
            int i = 0;
            while (true) {
                float[] fArr = this.alphas;
                if (i < fArr.length) {
                    this.animAlphas[i] = 1.0f;
                    fArr[i] = 1.0f;
                    i++;
                } else {
                    checkVisibility();
                    return;
                }
            }
        }

        private float calculateAlpha() {
            float interpolation;
            float f = 1.0f;
            int i = 0;
            while (true) {
                float[] fArr = this.animAlphas;
                if (i >= fArr.length) {
                    return f;
                }
                if (i == 2) {
                    interpolation = AndroidUtilities.accelerateInterpolator.getInterpolation(fArr[i]);
                } else {
                    interpolation = fArr[i];
                }
                f *= interpolation;
                i++;
            }
        }

        private void checkVisibility() {
            boolean z = false;
            int i = 0;
            while (true) {
                float[] fArr = this.alphas;
                if (i >= fArr.length) {
                    z = true;
                    break;
                } else if (fArr[i] != 1.0f) {
                    break;
                } else {
                    i++;
                }
            }
            if (z != this.visible) {
                this.visible = z;
                onVisibilityChanged(z);
            }
        }

        public boolean isVisible() {
            return this.visible;
        }

        public int getX() {
            return (PhotoViewer.this.containerView.getWidth() - ((int) (this.size * this.scale))) / 2;
        }

        public int getY() {
            int i = (int) ((((AndroidUtilities.displaySize.y + (PhotoViewer.this.isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0)) - ((int) (this.size * this.scale))) / 2) + PhotoViewer.this.currentPanTranslationY);
            return PhotoViewer.this.sendPhotoType == 1 ? i - AndroidUtilities.m1146dp(38.0f) : i;
        }

        public void onDraw(Canvas canvas) {
            int i;
            Drawable drawable;
            Drawable drawable2;
            int i2 = (int) (this.size * this.scale);
            int x = getX();
            int y = getY();
            float fCalculateAlpha = calculateAlpha();
            int i3 = this.previousBackgroundState;
            if (i3 >= 0 && i3 < PhotoViewer.progressDrawables.length + 2) {
                if (this.previousBackgroundState < PhotoViewer.progressDrawables.length) {
                    drawable2 = PhotoViewer.progressDrawables[this.previousBackgroundState];
                } else {
                    drawable2 = this.playDrawable;
                }
                if (drawable2 != null) {
                    drawable2.setAlpha((int) (this.animatedAlphaValue * 255.0f * fCalculateAlpha));
                    drawable2.setBounds(x, y, x + i2, y + i2);
                    drawable2.draw(canvas);
                }
            }
            int i4 = this.backgroundState;
            if (i4 >= 0 && i4 < PhotoViewer.progressDrawables.length + 2) {
                if (this.backgroundState < PhotoViewer.progressDrawables.length) {
                    drawable = PhotoViewer.progressDrawables[this.backgroundState];
                } else {
                    drawable = this.playDrawable;
                }
                if (drawable != null) {
                    if (this.previousBackgroundState != -2) {
                        drawable.setAlpha((int) ((1.0f - this.animatedAlphaValue) * 255.0f * fCalculateAlpha));
                    } else {
                        drawable.setAlpha((int) (fCalculateAlpha * 255.0f));
                    }
                    drawable.setBounds(x, y, x + i2, y + i2);
                    drawable.draw(canvas);
                }
            }
            int i5 = this.backgroundState;
            if (i5 == 0 || i5 == 1 || (i = this.previousBackgroundState) == 0 || i == 1) {
                int iM1146dp = AndroidUtilities.m1146dp(4.0f);
                if (this.previousBackgroundState != -2) {
                    PhotoViewer.progressPaint.setAlpha((int) (this.animatedAlphaValue * 255.0f * fCalculateAlpha));
                } else {
                    PhotoViewer.progressPaint.setAlpha((int) (fCalculateAlpha * 255.0f));
                }
                this.progressRect.set(x + iM1146dp, y + iM1146dp, (x + i2) - iM1146dp, (y + i2) - iM1146dp);
                canvas.drawArc(this.progressRect, this.radOffset - 90.0f, Math.max(4.0f, this.animatedProgressValue * 360.0f), false, PhotoViewer.progressPaint);
                updateAnimation(true);
                return;
            }
            updateAnimation(false);
        }
    }

    public interface PhotoViewerProvider {
        boolean allowCaption();

        boolean allowSendingSubmenu();

        boolean canCaptureMorePhotos();

        boolean canEdit(int i);

        boolean canLoadMoreAvatars();

        boolean canMoveCaptionAbove();

        boolean canReplace(int i);

        boolean canScrollAway();

        boolean cancelButtonPressed();

        boolean closeKeyboard();

        void deleteImageAtIndex(int i);

        boolean forceAllInGroup();

        String getDeleteMessageString();

        long getDialogId();

        MessageObject getEditingMessageObject();

        int getPhotoIndex(int i);

        PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC.FileLocation fileLocation, int i, boolean z, boolean z2);

        int getSelectedCount();

        HashMap getSelectedPhotos();

        ArrayList getSelectedPhotosOrder();

        ImageReceiver.BitmapHolder getThumbForPhoto(MessageObject messageObject, TLRPC.FileLocation fileLocation, int i);

        int getTotalImageCount();

        boolean isCaptionAbove();

        boolean isEditingMessage();

        boolean isEditingMessageResend();

        boolean isPhotoChecked(int i);

        boolean loadMore();

        void moveCaptionAbove(boolean z);

        void needAddMorePhotos();

        void onApplyCaption(CharSequence charSequence);

        void onClose();

        boolean onDeletePhoto(int i);

        void onEditModeChanged(boolean z);

        void onOpen();

        void onPreClose();

        void onPreOpen();

        void onReleasePlayerBeforeClose(int i);

        void openPhotoForEdit(String str, String str2, boolean z);

        void replaceButtonPressed(int i, VideoEditedInfo videoEditedInfo);

        boolean scaleToFill();

        void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo, boolean z, int i2, int i3, boolean z2);

        int setPhotoChecked(int i, VideoEditedInfo videoEditedInfo);

        int setPhotoUnchecked(Object obj);

        void updatePhotoAtIndex(int i);

        void willHidePhotoViewer();

        void willSwitchFromPhoto(MessageObject messageObject, TLRPC.FileLocation fileLocation, int i);

        /* renamed from: org.telegram.ui.PhotoViewer$PhotoViewerProvider$-CC, reason: invalid class name */
        /* loaded from: classes5.dex */
        public abstract /* synthetic */ class CC {
            public static void $default$onPreOpen(PhotoViewerProvider photoViewerProvider) {
            }

            public static void $default$onPreClose(PhotoViewerProvider photoViewerProvider) {
            }

            public static void $default$onEditModeChanged(PhotoViewerProvider photoViewerProvider, boolean z) {
            }

            public static boolean $default$onDeletePhoto(PhotoViewerProvider photoViewerProvider, int i) {
                return true;
            }

            public static boolean $default$canLoadMoreAvatars(PhotoViewerProvider photoViewerProvider) {
                return true;
            }

            public static void $default$onReleasePlayerBeforeClose(PhotoViewerProvider photoViewerProvider, int i) {
            }

            public static long $default$getDialogId(PhotoViewerProvider photoViewerProvider) {
                return 0L;
            }

            public static boolean $default$forceAllInGroup(PhotoViewerProvider photoViewerProvider) {
                return false;
            }

            public static boolean $default$isCaptionAbove(PhotoViewerProvider photoViewerProvider) {
                return false;
            }

            public static boolean $default$canMoveCaptionAbove(PhotoViewerProvider photoViewerProvider) {
                return false;
            }

            public static void $default$moveCaptionAbove(PhotoViewerProvider photoViewerProvider, boolean z) {
            }

            public static boolean $default$isEditingMessage(PhotoViewerProvider photoViewerProvider) {
                return false;
            }

            public static boolean $default$isEditingMessageResend(PhotoViewerProvider photoViewerProvider) {
                return false;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    class FrameLayoutDrawer extends SizeNotifierFrameLayoutPhoto {
        private boolean captionAbove;
        private ArrayList exclusionRects;
        private boolean ignoreLayout;
        private Paint paint;

        public FrameLayoutDrawer(Context context, Activity activity) {
            super(context, activity, false);
            this.paint = new Paint();
            setWillNotDraw(false);
            this.paint.setColor(AndroidUtilities.DARK_STATUS_BAR_OVERLAY);
            setLayerType(2, null);
        }

        /* JADX WARN: Removed duplicated region for block: B:90:0x0225  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x0228  */
        @Override // android.widget.FrameLayout, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected void onMeasure(int r17, int r18) {
            /*
                Method dump skipped, instructions count: 772
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PhotoViewer.FrameLayoutDrawer.onMeasure(int, int):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0077  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x0085  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x009c  */
        @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayoutPhoto, org.telegram.p023ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected void onLayout(boolean r20, int r21, int r22, int r23, int r24) {
            /*
                Method dump skipped, instructions count: 683
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PhotoViewer.FrameLayoutDrawer.onLayout(boolean, int, int, int, int):void");
        }

        public void updateExclusionRects() {
            if (Build.VERSION.SDK_INT >= 29) {
                if (this.exclusionRects == null) {
                    this.exclusionRects = new ArrayList();
                }
                this.exclusionRects.clear();
                if (PhotoViewer.this.currentEditMode == 1 || PhotoViewer.this.switchingToMode == 1) {
                    int measuredHeight = getMeasuredHeight();
                    int measuredWidth = getMeasuredWidth();
                    this.exclusionRects.add(new Rect(0, (measuredHeight - AndroidUtilities.m1146dp(200.0f)) / 2, AndroidUtilities.m1146dp(100.0f), (AndroidUtilities.m1146dp(200.0f) + measuredHeight) / 2));
                    this.exclusionRects.add(new Rect(measuredWidth - AndroidUtilities.m1146dp(100.0f), (measuredHeight - AndroidUtilities.m1146dp(200.0f)) / 2, measuredWidth, (measuredHeight + AndroidUtilities.m1146dp(200.0f)) / 2));
                }
                setSystemGestureExclusionRects(this.exclusionRects);
                invalidate();
            }
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            Canvas canvas2;
            PhotoViewer.this.onDraw(canvas);
            if (!isStatusBarVisible() || AndroidUtilities.statusBarHeight == 0 || PhotoViewer.this.actionBar == null) {
                return;
            }
            this.paint.setAlpha((int) (PhotoViewer.this.actionBar.getAlpha() * 255.0f * 0.498f));
            if (getPaddingRight() > 0) {
                canvas.drawRect(getMeasuredWidth() - getPaddingRight(), 0.0f, getMeasuredWidth(), getMeasuredHeight(), this.paint);
                canvas2 = canvas;
            } else {
                canvas2 = canvas;
            }
            if (getPaddingLeft() > 0) {
                canvas2.drawRect(0.0f, 0.0f, getPaddingLeft(), getMeasuredHeight(), this.paint);
            }
            if (getPaddingBottom() > 0) {
                float fDpf2 = AndroidUtilities.dpf2(24.0f) * (1.0f - PhotoViewer.this.actionBar.getAlpha());
                canvas2.drawRect(0.0f, (getMeasuredHeight() - getPaddingBottom()) + fDpf2, getMeasuredWidth(), getMeasuredHeight() + fDpf2, this.paint);
            }
        }

        @Override // android.view.View
        public void draw(Canvas canvas) {
            super.draw(canvas);
            if (PhotoViewer.this.photoViewerWebView == null || !PhotoViewer.this.photoViewerWebView.isControllable()) {
                return;
            }
            int measuredHeight = ((int) (PhotoViewer.this.photoViewerWebView.getWebView().getMeasuredHeight() * (PhotoViewer.this.scale - 1.0f))) / 2;
            if (PhotoViewer.this.videoForwardDrawable != null && PhotoViewer.this.videoForwardDrawable.isAnimating()) {
                PhotoViewer.this.videoForwardDrawable.setBounds(PhotoViewer.this.photoViewerWebView.getLeft(), (PhotoViewer.this.photoViewerWebView.getWebView().getTop() - measuredHeight) + ((int) (PhotoViewer.this.translationY / PhotoViewer.this.scale)), PhotoViewer.this.photoViewerWebView.getRight(), PhotoViewer.this.photoViewerWebView.getWebView().getBottom() + measuredHeight + ((int) (PhotoViewer.this.translationY / PhotoViewer.this.scale)));
                PhotoViewer.this.videoForwardDrawable.draw(canvas);
            }
            if (PhotoViewer.this.seekSpeedDrawable == null || !PhotoViewer.this.seekSpeedDrawable.isShown()) {
                return;
            }
            PhotoViewer.this.seekSpeedDrawable.setBounds(PhotoViewer.this.photoViewerWebView.getLeft(), (int) (AndroidUtilities.statusBarHeight + (AndroidUtilities.m1146dp(90.0f) * PhotoViewer.this.actionBar.getAlpha())), PhotoViewer.this.photoViewerWebView.getRight(), PhotoViewer.this.photoViewerWebView.getWebView().getBottom() + measuredHeight + ((int) (PhotoViewer.this.translationY / PhotoViewer.this.scale)));
            PhotoViewer.this.seekSpeedDrawable.draw(canvas);
        }

        @Override // android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view, long j) {
            if (view == PhotoViewer.this.leftPaintingOverlay || view == PhotoViewer.this.rightPaintingOverlay) {
                return false;
            }
            return drawChildInternal(canvas, view, j);
        }

        protected boolean drawChildInternal(Canvas canvas, View view, long j) {
            if (view == PhotoViewer.this.miniProgressView) {
                return false;
            }
            if (view == PhotoViewer.this.videoTimelineViewContainer && PhotoViewer.this.videoTimelineViewContainer.getTranslationY() > 0.0f && PhotoViewer.this.pickerView.getTranslationY() == 0.0f) {
                canvas.save();
                canvas.clipRect(PhotoViewer.this.videoTimelineViewContainer.getX(), PhotoViewer.this.videoTimelineViewContainer.getY(), PhotoViewer.this.videoTimelineViewContainer.getX() + PhotoViewer.this.videoTimelineViewContainer.getMeasuredWidth(), PhotoViewer.this.videoTimelineViewContainer.getBottom());
                boolean zDrawChild = super.drawChild(canvas, view, j);
                canvas.restore();
                return zDrawChild;
            }
            try {
                if (view != PhotoViewer.this.aspectRatioFrameLayout && view != PhotoViewer.this.paintingOverlay) {
                    if (super.drawChild(canvas, view, j)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable unused) {
                return true;
            }
        }

        @Override // android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }

        @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            Bulletin.addDelegate(this, new Bulletin.Delegate() { // from class: org.telegram.ui.PhotoViewer.FrameLayoutDrawer.1
                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public /* synthetic */ boolean allowLayoutChanges() {
                    return Bulletin.Delegate.CC.$default$allowLayoutChanges(this);
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public /* synthetic */ boolean bottomOffsetAnimated() {
                    return Bulletin.Delegate.CC.$default$bottomOffsetAnimated(this);
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public /* synthetic */ boolean clipWithGradient(int i) {
                    return Bulletin.Delegate.CC.$default$clipWithGradient(this, i);
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public /* synthetic */ void onBottomOffsetChange(float f) {
                    Bulletin.Delegate.CC.$default$onBottomOffsetChange(this, f);
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public /* synthetic */ void onHide(Bulletin bulletin) {
                    Bulletin.Delegate.CC.$default$onHide(this, bulletin);
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public /* synthetic */ void onShow(Bulletin bulletin) {
                    Bulletin.Delegate.CC.$default$onShow(this, bulletin);
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public int getBottomOffset(int i) {
                    int height = 0;
                    if (PhotoViewer.this.editing) {
                        if (PhotoViewer.this.captionEdit != null) {
                            height = PhotoViewer.this.captionEdit.keyboardNotifier.getKeyboardHeight();
                            if (PhotoViewer.this.captionEdit.getVisibility() == 0 && (PhotoViewer.this.placeProvider == null || !PhotoViewer.this.placeProvider.isCaptionAbove())) {
                                height += PhotoViewer.this.captionEdit.getEditTextHeight() + AndroidUtilities.m1146dp(12.0f);
                            }
                        }
                        return (PhotoViewer.this.pickerView == null || PhotoViewer.this.pickerView.getVisibility() != 0) ? height : (PhotoViewer.this.captionEdit == null || !PhotoViewer.this.captionEdit.keyboardNotifier.keyboardVisible()) ? height + PhotoViewer.this.pickerView.getHeight() : height;
                    }
                    if (PhotoViewer.this.bottomLayout != null && PhotoViewer.this.bottomLayout.getVisibility() == 0) {
                        height = (int) (0 + (PhotoViewer.this.bottomLayout.getHeight() * PhotoViewer.this.bottomLayout.getAlpha()));
                    }
                    return (PhotoViewer.this.groupedPhotosListView == null || !PhotoViewer.this.groupedPhotosListView.hasPhotos() || (!AndroidUtilities.isTablet() && PhotoViewer.this.containerView.getMeasuredHeight() <= PhotoViewer.this.containerView.getMeasuredWidth())) ? height : (int) (height + (PhotoViewer.this.groupedPhotosListView.getHeight() * PhotoViewer.this.groupedPhotosListView.getAlpha()));
                }

                @Override // org.telegram.ui.Components.Bulletin.Delegate
                public int getTopOffset(int i) {
                    return (FrameLayoutDrawer.this.isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight() + ((int) (PhotoViewer.this.topCaptionEdit.getAlpha() * PhotoViewer.this.topCaptionEdit.getEditTextHeight()));
                }
            });
        }

        @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            Bulletin.removeDelegate(this);
        }

        @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayoutPhoto, org.telegram.p023ui.Components.SizeNotifierFrameLayout
        public void notifyHeightChanged() {
            super.notifyHeightChanged();
            if (PhotoViewer.this.isCurrentVideo) {
                PhotoViewer.this.photoProgressViews[0].setIndexedAlpha(2, getKeyboardHeight() <= AndroidUtilities.m1146dp(20.0f) ? 1.0f : 0.0f, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    class VideoPlayerControlFrameLayout extends FrameLayout {
        private boolean ignoreLayout;
        private int lastTimeWidth;
        private int parentHeight;
        private int parentWidth;
        private float progress;
        private boolean seekBarTransitionEnabled;
        private SpringAnimation timeSpring;
        private FloatValueHolder timeValue;
        private boolean translationYAnimationEnabled;

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(DynamicAnimation dynamicAnimation, float f, float f2) {
            PhotoViewer.this.videoPlayerSeekbar.setSize((int) (((getMeasuredWidth() - AndroidUtilities.m1146dp(16.0f)) - f) - (this.parentWidth > this.parentHeight ? AndroidUtilities.m1146dp(48.0f) : 0)), getMeasuredHeight());
        }

        public VideoPlayerControlFrameLayout(Context context) {
            super(context);
            this.progress = 1.0f;
            this.translationYAnimationEnabled = true;
            this.timeValue = new FloatValueHolder(0.0f);
            this.timeSpring = (SpringAnimation) new SpringAnimation(this.timeValue).setSpring(new SpringForce(0.0f).setStiffness(750.0f).setDampingRatio(1.0f)).addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: org.telegram.ui.PhotoViewer$VideoPlayerControlFrameLayout$$ExternalSyntheticLambda0
                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                    this.f$0.lambda$new$0(dynamicAnimation, f, f2);
                }
            });
            setWillNotDraw(false);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (this.progress < 1.0f) {
                return false;
            }
            if (PhotoViewer.this.videoPlayerSeekbar.onTouch(motionEvent.getAction(), motionEvent.getX() - AndroidUtilities.m1146dp(2.0f), motionEvent.getY())) {
                getParent().requestDisallowInterceptTouchEvent(true);
                PhotoViewer.this.videoPlayerSeekbarView.invalidate();
            }
            return true;
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.timeValue.setValue(0.0f);
            this.lastTimeWidth = 0;
        }

        @Override // android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }

        /* JADX WARN: Removed duplicated region for block: B:32:0x0125  */
        @Override // android.widget.FrameLayout, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected void onMeasure(int r12, int r13) {
            /*
                Method dump skipped, instructions count: 328
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PhotoViewer.VideoPlayerControlFrameLayout.onMeasure(int, int):void");
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            float currentPosition = PhotoViewer.this.videoPlayer != null ? PhotoViewer.this.videoPlayer.getCurrentPosition() / PhotoViewer.this.videoPlayer.getDuration() : 0.0f;
            if (PhotoViewer.this.playerWasReady) {
                PhotoViewer.this.videoPlayerSeekbar.setProgress(currentPosition);
            }
            PhotoViewer.this.videoTimelineView.setProgress(currentPosition);
        }

        public float getProgress() {
            return this.progress;
        }

        public void setProgress(float f) {
            if (this.progress != f) {
                this.progress = f;
                onProgressChanged(f);
            }
        }

        private void onProgressChanged(float f) {
            PhotoViewer.this.videoPlayerTime.setAlpha(f);
            PhotoViewer.this.exitFullscreenButton.setAlpha(f);
            if (this.seekBarTransitionEnabled) {
                PhotoViewer.this.videoPlayerTime.setPivotX(PhotoViewer.this.videoPlayerTime.getWidth());
                PhotoViewer.this.videoPlayerTime.setPivotY(PhotoViewer.this.videoPlayerTime.getHeight());
                float f2 = 1.0f - f;
                float f3 = 1.0f - (0.1f * f2);
                PhotoViewer.this.videoPlayerTime.setScaleX(f3);
                PhotoViewer.this.videoPlayerTime.setScaleY(f3);
                PhotoViewer.this.videoPlayerSeekbar.setTransitionProgress(f2);
                return;
            }
            if (this.translationYAnimationEnabled) {
                setTranslationY(AndroidUtilities.dpf2(24.0f) * (1.0f - f));
            }
            PhotoViewer.this.videoPlayerSeekbarView.setAlpha(f);
        }

        public void setSeekBarTransitionEnabled(boolean z) {
            if (this.seekBarTransitionEnabled != z) {
                this.seekBarTransitionEnabled = z;
                if (z) {
                    setTranslationY(0.0f);
                    PhotoViewer.this.videoPlayerSeekbarView.setAlpha(1.0f);
                } else {
                    PhotoViewer.this.videoPlayerTime.setScaleX(1.0f);
                    PhotoViewer.this.videoPlayerTime.setScaleY(1.0f);
                    PhotoViewer.this.videoPlayerSeekbar.setTransitionProgress(0.0f);
                }
                onProgressChanged(this.progress);
            }
        }

        public void setTranslationYAnimationEnabled(boolean z) {
            if (this.translationYAnimationEnabled != z) {
                this.translationYAnimationEnabled = z;
                if (!z) {
                    setTranslationY(0.0f);
                }
                onProgressChanged(this.progress);
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class CaptionTextViewSwitcher extends TextViewSwitcher {
        private float alpha;
        private FrameLayout container;
        private boolean inScrollView;
        private NestedScrollView scrollView;

        public CaptionTextViewSwitcher(Context context) {
            super(context);
            this.inScrollView = false;
            this.alpha = 1.0f;
        }

        public void setScrollView(NestedScrollView nestedScrollView) {
            this.scrollView = nestedScrollView;
        }

        public void setContainer(FrameLayout frameLayout) {
            this.container = frameLayout;
        }

        @Override // android.view.View
        public void setVisibility(int i) {
            setVisibility(i, true);
        }

        public void setVisibility(int i, boolean z) {
            super.setVisibility(i);
            if (this.inScrollView && z) {
                this.scrollView.setVisibility(i);
            }
        }

        @Override // android.view.View
        public void setAlpha(float f) {
            this.alpha = f;
            if (this.inScrollView) {
                this.scrollView.setAlpha(f);
            } else {
                super.setAlpha(f);
            }
        }

        @Override // android.view.View
        public float getAlpha() {
            if (this.inScrollView) {
                return this.alpha;
            }
            return super.getAlpha();
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            super.setTranslationY(f);
            if (this.inScrollView) {
                this.scrollView.invalidate();
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (this.container == null || getParent() != this.container) {
                return;
            }
            this.inScrollView = true;
            this.scrollView.setVisibility(getVisibility());
            this.scrollView.setAlpha(this.alpha);
            super.setAlpha(1.0f);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (this.inScrollView) {
                this.inScrollView = false;
                this.scrollView.setVisibility(8);
                super.setAlpha(this.alpha);
            }
        }
    }

    /* loaded from: classes5.dex */
    public static class CaptionScrollView extends NestedScrollView {
        private Method abortAnimatedScrollMethod;
        public float backgroundAlpha;
        private final FrameLayout captionContainer;
        private final CaptionTextViewSwitcher captionTextViewSwitcher;
        public boolean dontChangeTopMargin;
        private boolean isLandscape;
        private boolean nestedScrollStarted;
        private float overScrollY;
        private final Paint paint;
        private int pendingTopMargin;
        private int prevHeight;
        private OverScroller scroller;
        private final SpringAnimation springAnimation;
        private int textHash;
        private float velocitySign;
        private float velocityY;

        @Override // androidx.core.widget.NestedScrollView, android.view.View
        protected float getBottomFadingEdgeStrength() {
            return 1.0f;
        }

        @Override // androidx.core.widget.NestedScrollView, android.view.View
        protected float getTopFadingEdgeStrength() {
            return 1.0f;
        }

        protected boolean isStatusBarVisible() {
            return true;
        }

        protected void onScrollEnd() {
        }

        protected void onScrollStart() {
        }

        protected void onScrollUpdate() {
        }

        public CaptionScrollView(Context context, CaptionTextViewSwitcher captionTextViewSwitcher, FrameLayout frameLayout) throws NoSuchFieldException, NoSuchMethodException, SecurityException {
            super(context);
            Paint paint = new Paint(1);
            this.paint = paint;
            this.backgroundAlpha = 1.0f;
            this.pendingTopMargin = -1;
            this.captionTextViewSwitcher = captionTextViewSwitcher;
            this.captionContainer = frameLayout;
            setClipChildren(false);
            setOverScrollMode(2);
            paint.setColor(-16777216);
            setFadingEdgeLength(AndroidUtilities.m1146dp(12.0f));
            setVerticalFadingEdgeEnabled(true);
            setWillNotDraw(false);
            SpringAnimation springAnimation = new SpringAnimation(captionTextViewSwitcher, DynamicAnimation.TRANSLATION_Y, 0.0f);
            this.springAnimation = springAnimation;
            springAnimation.getSpring().setStiffness(100.0f);
            springAnimation.setMinimumVisibleChange(1.0f);
            springAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: org.telegram.ui.PhotoViewer$CaptionScrollView$$ExternalSyntheticLambda0
                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f, float f2) {
                    this.f$0.lambda$new$0(dynamicAnimation, f, f2);
                }
            });
            springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: org.telegram.ui.PhotoViewer$CaptionScrollView$$ExternalSyntheticLambda1
                @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                    this.f$0.lambda$new$1(dynamicAnimation, z, f, f2);
                }
            });
            springAnimation.getSpring().setDampingRatio(1.0f);
            try {
                Method declaredMethod = NestedScrollView.class.getDeclaredMethod("abortAnimatedScroll", null);
                this.abortAnimatedScrollMethod = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (Exception e) {
                this.abortAnimatedScrollMethod = null;
                FileLog.m1160e(e);
            }
            try {
                Field declaredField = NestedScrollView.class.getDeclaredField("mScroller");
                declaredField.setAccessible(true);
                this.scroller = (OverScroller) declaredField.get(this);
            } catch (Exception e2) {
                this.scroller = null;
                FileLog.m1160e(e2);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(DynamicAnimation dynamicAnimation, float f, float f2) {
            this.overScrollY = f;
            this.velocityY = f2;
            onScrollUpdate();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
            onScrollEnd();
        }

        @Override // androidx.core.widget.NestedScrollView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() != 0 || motionEvent.getY() >= (this.captionContainer.getTop() - getScrollY()) + this.captionTextViewSwitcher.getTranslationY()) {
                return super.onTouchEvent(motionEvent);
            }
            return false;
        }

        @Override // androidx.core.widget.NestedScrollView, android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            updateTopMargin(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
            super.onMeasure(i, i2);
        }

        public void applyPendingTopMargin() {
            this.dontChangeTopMargin = false;
            if (this.pendingTopMargin >= 0) {
                ((ViewGroup.MarginLayoutParams) this.captionContainer.getLayoutParams()).topMargin = this.pendingTopMargin;
                this.pendingTopMargin = -1;
                requestLayout();
            }
        }

        public int getPendingMarginTopDiff() {
            int i = this.pendingTopMargin;
            if (i >= 0) {
                return i - ((ViewGroup.MarginLayoutParams) this.captionContainer.getLayoutParams()).topMargin;
            }
            return 0;
        }

        public void updateTopMargin() {
            updateTopMargin(getWidth(), getHeight());
        }

        private void updateTopMargin(int i, int i2) {
            int iCalculateNewContainerMarginTop = calculateNewContainerMarginTop(i, i2);
            if (iCalculateNewContainerMarginTop >= 0) {
                if (this.dontChangeTopMargin) {
                    this.pendingTopMargin = iCalculateNewContainerMarginTop;
                } else {
                    ((ViewGroup.MarginLayoutParams) this.captionContainer.getLayoutParams()).topMargin = iCalculateNewContainerMarginTop;
                    this.pendingTopMargin = -1;
                }
            }
        }

        public int calculateNewContainerMarginTop(int i, int i2) {
            int fontMetricsInt;
            int iM1146dp;
            if (i == 0 || i2 == 0) {
                return -1;
            }
            TextView currentView = this.captionTextViewSwitcher.getCurrentView();
            CharSequence text = currentView.getText();
            int iHashCode = text.hashCode();
            Point point = AndroidUtilities.displaySize;
            boolean z = point.x > point.y;
            if (this.textHash == iHashCode && this.isLandscape == z && this.prevHeight == i2) {
                return -1;
            }
            this.textHash = iHashCode;
            this.isLandscape = z;
            this.prevHeight = i2;
            currentView.measure(View.MeasureSpec.makeMeasureSpec(i, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(i2, TLObject.FLAG_31));
            Layout layout = currentView.getLayout();
            int lineCount = layout.getLineCount();
            if ((z && lineCount <= 2) || (!z && lineCount <= 5)) {
                fontMetricsInt = i2 - currentView.getMeasuredHeight();
                iM1146dp = this.captionTextViewSwitcher.getPaddingBottom();
            } else {
                int iMin = Math.min(z ? 2 : 5, lineCount);
                loop0: while (iMin > 1) {
                    int i3 = iMin - 1;
                    for (int lineStart = layout.getLineStart(i3); lineStart < layout.getLineEnd(i3); lineStart++) {
                        if (!Character.isWhitespace(text.charAt(lineStart))) {
                            break loop0;
                        }
                    }
                    iMin--;
                }
                fontMetricsInt = i2 - (currentView.getPaint().getFontMetricsInt(null) * iMin);
                iM1146dp = AndroidUtilities.m1146dp(8.0f);
            }
            return fontMetricsInt - iM1146dp;
        }

        public void reset() {
            scrollTo(0, 0);
        }

        public void stopScrolling() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            Method method = this.abortAnimatedScrollMethod;
            if (method != null) {
                try {
                    method.invoke(this, null);
                } catch (Exception e) {
                    FileLog.m1160e(e);
                }
            }
        }

        @Override // androidx.core.widget.NestedScrollView
        public void fling(int i) {
            super.fling(i);
            this.velocitySign = Math.signum(i);
            this.velocityY = 0.0f;
        }

        @Override // androidx.core.widget.NestedScrollView
        public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2, int i3) {
            iArr[1] = 0;
            if (this.nestedScrollStarted) {
                float f = this.overScrollY;
                if ((f > 0.0f && i2 > 0) || (f < 0.0f && i2 < 0)) {
                    float f2 = i2;
                    float f3 = f - f2;
                    if (f > 0.0f) {
                        if (f3 < 0.0f) {
                            this.overScrollY = 0.0f;
                            iArr[1] = (int) (0 + f2 + f3);
                        } else {
                            this.overScrollY = f3;
                            iArr[1] = i2;
                        }
                    } else if (f3 > 0.0f) {
                        this.overScrollY = 0.0f;
                        iArr[1] = (int) (0 + f2 + f3);
                    } else {
                        this.overScrollY = f3;
                        iArr[1] = i2;
                    }
                    onScrollUpdate();
                    this.captionTextViewSwitcher.setTranslationY(this.overScrollY);
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.core.widget.NestedScrollView
        public void dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr, int i5, int[] iArr2) {
            float f;
            if (i4 != 0) {
                int iRound = Math.round(i4 * (1.0f - Math.abs((-this.overScrollY) / (this.captionContainer.getTop() - ((isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight())))));
                if (iRound != 0) {
                    if (!this.nestedScrollStarted) {
                        if (!this.springAnimation.isRunning()) {
                            OverScroller overScroller = this.scroller;
                            float currVelocity = overScroller != null ? overScroller.getCurrVelocity() : Float.NaN;
                            if (Float.isNaN(currVelocity)) {
                                f = 0.0f;
                            } else {
                                Point point = AndroidUtilities.displaySize;
                                float fMin = Math.min(point.x > point.y ? 3000.0f : 5000.0f, currVelocity);
                                iRound = (int) ((iRound * fMin) / currVelocity);
                                f = fMin * (-this.velocitySign);
                            }
                            if (iRound != 0) {
                                float f2 = this.overScrollY - iRound;
                                this.overScrollY = f2;
                                this.captionTextViewSwitcher.setTranslationY(f2);
                            }
                            startSpringAnimationIfNotRunning(f);
                        }
                    } else {
                        float f3 = this.overScrollY - iRound;
                        this.overScrollY = f3;
                        this.captionTextViewSwitcher.setTranslationY(f3);
                    }
                }
                onScrollUpdate();
            }
        }

        private void startSpringAnimationIfNotRunning(float f) {
            if (this.springAnimation.isRunning()) {
                return;
            }
            this.springAnimation.setStartVelocity(f);
            this.springAnimation.start();
        }

        @Override // androidx.core.widget.NestedScrollView
        public boolean startNestedScroll(int i, int i2) {
            if (i2 == 0) {
                this.springAnimation.cancel();
                this.nestedScrollStarted = true;
                this.overScrollY = this.captionTextViewSwitcher.getTranslationY();
                onScrollStart();
            }
            return true;
        }

        @Override // androidx.core.widget.NestedScrollView, android.view.View
        public void computeScroll() {
            OverScroller overScroller;
            super.computeScroll();
            if (!this.nestedScrollStarted && this.overScrollY != 0.0f && (overScroller = this.scroller) != null && overScroller.isFinished()) {
                startSpringAnimationIfNotRunning(0.0f);
            }
            onScrollUpdate();
        }

        @Override // androidx.core.widget.NestedScrollView
        public void stopNestedScroll(int i) {
            OverScroller overScroller;
            if (this.nestedScrollStarted && i == 0) {
                this.nestedScrollStarted = false;
                if (this.overScrollY != 0.0f && (overScroller = this.scroller) != null && overScroller.isFinished()) {
                    startSpringAnimationIfNotRunning(this.velocityY);
                }
                onScrollEnd();
            }
        }

        @Override // androidx.core.widget.NestedScrollView, android.view.View
        public void draw(Canvas canvas) {
            int width = getWidth();
            int height = getHeight();
            int scrollY = getScrollY();
            int iSave = canvas.save();
            int i = height + scrollY;
            canvas.clipRect(0, scrollY, width, i);
            this.paint.setAlpha((int) (this.backgroundAlpha * 127.0f));
            canvas.drawRect(0.0f, this.captionContainer.getTop() + this.captionTextViewSwitcher.getTranslationY(), width, i, this.paint);
            super.draw(canvas);
            canvas.restoreToCount(iSave);
        }
    }

    public static PhotoViewer getPipInstance() {
        return PipInstance;
    }

    public static PhotoViewer getInstance() {
        PhotoViewer photoViewer;
        PhotoViewer photoViewer2 = Instance;
        if (photoViewer2 != null) {
            return photoViewer2;
        }
        synchronized (PhotoViewer.class) {
            try {
                photoViewer = Instance;
                if (photoViewer == null) {
                    photoViewer = new PhotoViewer();
                    Instance = photoViewer;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return photoViewer;
    }

    public boolean isOpenedFullScreenVideo() {
        return this.openedFullScreenVideo;
    }

    public static boolean hasInstance() {
        return Instance != null;
    }

    public PhotoViewer() {
        this.ALLOW_USE_SURFACE = Build.VERSION.SDK_INT >= 30;
        this.maxSelectedPhotos = -1;
        this.allowOrder = true;
        this.miniProgressShowRunnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda68
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        };
        this.isActionBarVisible = true;
        this.closePhotoAfterSelect = true;
        this.closePhotoAfterSelectWithAnimation = false;
        this.videoQualityItems = new ArrayList();
        this.actionBarItemsVisibility = new HashMap(3);
        this.backgroundDrawable = new BackgroundDrawable(-16777216);
        this.blackPaint = new Paint();
        this.photoProgressViews = new PhotoProgressView[3];
        this.onUserLeaveHintListener = new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda69
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onUserLeaveHint();
            }
        };
        this.pressedDrawable = new GradientDrawable[2];
        this.drawPressedDrawable = new boolean[2];
        this.pressedDrawableAlpha = new float[2];
        this.cropTransform = new CropTransform();
        this.leftCropTransform = new CropTransform();
        this.rightCropTransform = new CropTransform();
        this.shiftDp = -8.0f;
        this.insets = new Rect();
        this.pauseOnMinimize = false;
        this.setLoadingRunnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer.1
            @Override // java.lang.Runnable
            public void run() {
                if (PhotoViewer.this.currentMessageObject == null) {
                    return;
                }
                FileLoader.getInstance(PhotoViewer.this.currentMessageObject.currentAccount).setLoadingVideo(PhotoViewer.this.currentMessageObject.getDocument(), true, false);
            }
        };
        this.hideActionBarRunnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer.2
            @Override // java.lang.Runnable
            public void run() {
                if (PhotoViewer.this.videoPlayerControlVisible && PhotoViewer.this.isPlaying && !ApplicationLoader.mainInterfacePaused) {
                    if (PhotoViewer.this.menuItem == null || !PhotoViewer.this.menuItem.isSubMenuShowing()) {
                        if (PhotoViewer.this.videoItem == null || !PhotoViewer.this.videoItem.isSubMenuShowing()) {
                            if (PhotoViewer.this.captionScrollView == null || PhotoViewer.this.captionScrollView.getScrollY() == 0) {
                                if (PhotoViewer.this.miniProgressView == null || PhotoViewer.this.miniProgressView.getVisibility() != 0) {
                                    PhotoViewer photoViewer = PhotoViewer.PipInstance;
                                    PhotoViewer photoViewer2 = PhotoViewer.this;
                                    if (photoViewer == photoViewer2) {
                                        return;
                                    }
                                    photoViewer2.toggleActionBar(false, true);
                                }
                            }
                        }
                    }
                }
            }
        };
        this.videoPlayerControlVisible = true;
        this.videoPlayerCurrentTime = new int[2];
        this.videoPlayerTotalTime = new int[2];
        this.fullscreenButton = new ImageView[3];
        this.pipPosition = new int[2];
        this.pipVideoOverlayAnimateFlag = true;
        this.lastImageId = -1;
        this.prevOrientation = -10;
        this.longVideoPlayerRewinder = new OldVideoPlayerRewinder() { // from class: org.telegram.ui.PhotoViewer.3
            @Override // org.telegram.messenger.video.OldVideoPlayerRewinder
            protected void onRewindCanceled() throws NumberFormatException {
                PhotoViewer.this.onTouchEvent(MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0));
                PhotoViewer.this.videoForwardDrawable.setShowing(false);
                PipVideoOverlay.onRewindCanceled();
            }

            @Override // org.telegram.messenger.video.OldVideoPlayerRewinder
            protected void updateRewindProgressUi(long j, float f, boolean z) {
                PhotoViewer.this.videoForwardDrawable.setTime(Math.abs(j));
                if (z) {
                    PhotoViewer.this.videoPlayerSeekbar.setProgress(f);
                    PhotoViewer.this.videoPlayerSeekbarView.invalidate();
                }
                PipVideoOverlay.onUpdateRewindProgressUi(j, f, z);
            }

            @Override // org.telegram.messenger.video.OldVideoPlayerRewinder
            protected void onRewindStart(boolean z) {
                PhotoViewer.this.videoForwardDrawable.setOneShootAnimation(false);
                PhotoViewer.this.videoForwardDrawable.setLeftSide(!z);
                PhotoViewer.this.videoForwardDrawable.setShowing(true);
                PhotoViewer.this.containerView.invalidate();
                PipVideoOverlay.onRewindStart(z);
            }
        };
        VideoFramesRewinder videoFramesRewinder = new VideoFramesRewinder();
        this.framesRewinder = videoFramesRewinder;
        this.videoPlayerRewinder = new VideoPlayerRewinder(videoFramesRewinder) { // from class: org.telegram.ui.PhotoViewer.4
            @Override // org.telegram.messenger.video.VideoPlayerRewinder
            protected void onRewindCanceled() throws NumberFormatException {
                PhotoViewer.this.onTouchEvent(MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0));
                PhotoViewer.this.videoForwardDrawable.setShowing(false);
                PipVideoOverlay.onRewindCanceled();
            }

            @Override // org.telegram.messenger.video.VideoPlayerRewinder
            protected void updateRewindProgressUi(long j, float f, boolean z) {
                PhotoViewer.this.videoForwardDrawable.setTime(Math.abs(j));
                if (z) {
                    PhotoViewer.this.videoPlayerSeekbar.setProgress(f);
                    PhotoViewer.this.videoPlayerSeekbarView.invalidate();
                }
                PipVideoOverlay.onUpdateRewindProgressUi(j, f, z);
            }

            @Override // org.telegram.messenger.video.VideoPlayerRewinder
            protected void onRewindStart(boolean z) {
                PhotoViewer.this.videoForwardDrawable.setOneShootAnimation(false);
                PhotoViewer.this.videoForwardDrawable.setLeftSide(!z);
                PhotoViewer.this.videoForwardDrawable.setShowing(true);
                PhotoViewer.this.containerView.invalidate();
                PipVideoOverlay.onRewindStart(z);
            }
        };
        this.FLASH_VIEW_VALUE = new AnimationProperties.FloatProperty("flashViewAlpha") { // from class: org.telegram.ui.PhotoViewer.5
            @Override // org.telegram.ui.Components.AnimationProperties.FloatProperty
            public void setValue(View view, float f) {
                view.setAlpha(f);
                if (PhotoViewer.this.photoCropView != null) {
                    PhotoViewer.this.photoCropView.setVideoThumbFlashAlpha(f);
                }
            }

            @Override // android.util.Property
            public Float get(View view) {
                return Float.valueOf(view.getAlpha());
            }
        };
        this.updateProgressRunnable = new RunnableC58336();
        this.switchToInlineRunnable = new RunnableC58447();
        this.surfaceTextureListener = new TextureView.SurfaceTextureListener() { // from class: org.telegram.ui.PhotoViewer.8
            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                if (PhotoViewer.this.videoTextureView == null) {
                    return true;
                }
                if (PipVideoOverlay.isVisible() && PipVideoOverlay.getPipSource() != null && PipVideoOverlay.getPipSource().state2.isAttachedToPip()) {
                    PipVideoOverlay.getPipTextureView().setSurfaceTexture(surfaceTexture);
                    PipVideoOverlay.getPipTextureView().setVisibility(0);
                    return false;
                }
                if (!PhotoViewer.this.changingTextureView) {
                    return true;
                }
                if (PhotoViewer.this.switchingInlineMode) {
                    PhotoViewer.this.waitingForFirstTextureUpload = 2;
                }
                PhotoViewer.this.videoTextureView.setSurfaceTexture(surfaceTexture);
                PhotoViewer.this.videoTextureView.setVisibility(0);
                PhotoViewer.this.changingTextureView = false;
                PhotoViewer.this.containerView.invalidate();
                return false;
            }

            @Override // android.view.TextureView.SurfaceTextureListener
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                if (PhotoViewer.this.waitingForFirstTextureUpload == 1) {
                    PhotoViewer.this.checkChangedTextureView(true);
                }
            }
        };
        this.animationValues = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 2, 14);
        this.updateContainerFlagsRunnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda70
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$6();
            }
        };
        this.leftImage = new ImageReceiver();
        this.centerImage = new ImageReceiver() { // from class: org.telegram.ui.PhotoViewer.11
            @Override // org.telegram.messenger.ImageReceiver
            protected boolean setImageBitmapByKey(Drawable drawable, String str, int i, boolean z, int i2) {
                boolean imageBitmapByKey = super.setImageBitmapByKey(drawable, str, i, z, i2);
                PhotoViewer.this.prepareSegmentImage();
                return imageBitmapByKey;
            }
        };
        this.rightImage = new ImageReceiver();
        this.leftBlur = new BlurringShader.ThumbBlurer(1, new PhotoViewer$$ExternalSyntheticLambda71(this));
        this.centerBlur = new BlurringShader.ThumbBlurer(1, new PhotoViewer$$ExternalSyntheticLambda71(this));
        this.rightBlur = new BlurringShader.ThumbBlurer(1, new PhotoViewer$$ExternalSyntheticLambda71(this));
        this.centerImageTransformLocked = false;
        this.centerImageTransform = new Matrix();
        this.videoFrameBitmapPaint = new Paint();
        this.videoFrameBitmap = null;
        this.editState = new EditState();
        this.currentFileNames = new String[3];
        this.endReached = new boolean[]{false, true};
        this.startReached = false;
        this.scale = 1.0f;
        this.currentCropScale = 1.0f;
        this.currentCropX = 0.0f;
        this.currentCropY = 0.0f;
        this.rotate = 0.0f;
        this.mirror = 0.0f;
        this.switchingToMode = -1;
        this.interpolator = new DecelerateInterpolator(1.5f);
        this.pinchStartScale = 1.0f;
        this.pinchStartRotate = 0.0f;
        this.canZoom = true;
        this.canDragDown = true;
        this.shownControlsByEnd = false;
        this.actionBarWasShownBeforeByEnd = false;
        this.imagesArrTemp = new ArrayList();
        this.imagesByIdsTemp = new SparseArray[]{new SparseArray(), new SparseArray()};
        this.imagesArr = new ArrayList();
        this.imagesByIds = new SparseArray[]{new SparseArray(), new SparseArray()};
        this.imagesArrLocations = new ArrayList();
        this.imagesArrLocationsVideo = new ArrayList();
        this.imagesArrLocationsSizes = new ArrayList();
        this.imagesArrMessages = new ArrayList();
        this.secureDocuments = new ArrayList();
        this.avatarsArr = new ArrayList();
        this.imagesArrLocals = new ArrayList();
        this.currentAvatarLocation = null;
        this.savedState = null;
        this.hitRect = new Rect();
        this.transitionNotificationLocker = new AnimationNotificationsLocker(new int[]{NotificationCenter.dialogsNeedReload, NotificationCenter.closeChats, NotificationCenter.mediaCountDidLoad, NotificationCenter.mediaDidLoad, NotificationCenter.dialogPhotosUpdate});
        this.captionEditAlpha = new float[]{1.0f};
        this.topCaptionEditAlpha = new float[]{1.0f};
        this.longPressRunnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda72
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.onLongPress();
            }
        };
        this.tempInt = new int[2];
        this.captureFrameAtTime = -1L;
        this.captureFrameReadyAtTime = -1L;
        this.needCaptureFrameReadyAtTime = -1L;
        this.compressionsCount = -1;
        this.blurAlpha = new AnimatedFloat(new PhotoViewer$$ExternalSyntheticLambda71(this), 180L, CubicBezierInterpolator.EASE_OUT);
        this.blackPaint.setColor(-16777216);
        this.videoFrameBitmapPaint.setColor(-1);
        this.centerImage.setFileLoadingPriority(3);
        this.audioManager = (AudioManager) ApplicationLoader.applicationContext.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x01ab  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x05ab  */
    /* JADX WARN: Removed duplicated region for block: B:409:0x0775  */
    /* JADX WARN: Removed duplicated region for block: B:425:0x07ae  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x07d8 A[PHI: r23
      0x07d8: PHI (r23v5 boolean) = (r23v3 boolean), (r23v6 boolean) binds: [B:436:0x07e6, B:430:0x07d6] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void didReceivedNotification(int r23, int r24, java.lang.Object... r25) throws android.content.res.Resources.NotFoundException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 2311
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.didReceivedNotification(int, int, java.lang.Object[]):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDownloadAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.parentActivity, this.resourcesProvider);
        builder.setTitle(LocaleController.getString("AppName", C2369R.string.AppName));
        builder.setPositiveButton(LocaleController.getString("OK", C2369R.string.f1459OK), null);
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.isVideo() && FileLoader.getInstance(this.currentMessageObject.currentAccount).isLoadingFile(this.currentFileNames[0])) {
            builder.setMessage(LocaleController.getString(C2369R.string.PleaseStreamDownload));
        } else {
            builder.setMessage(LocaleController.getString(C2369R.string.PleaseDownload));
        }
        showAlertDialog(builder);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSharePressed() {
        boolean zIsVideo;
        String mimeType;
        if (this.parentActivity == null || !this.allowShare) {
            return;
        }
        try {
            MessageObject messageObject = this.currentMessageObject;
            File file = null;
            if (messageObject != null) {
                messageObject.isVideo();
                if (!TextUtils.isEmpty(this.currentMessageObject.messageOwner.attachPath)) {
                    File file2 = new File(this.currentMessageObject.messageOwner.attachPath);
                    if (file2.exists()) {
                        file = file2;
                    }
                }
                if (file == null) {
                    file = FileLoader.getInstance(this.currentAccount).getPathToMessage(this.currentMessageObject.messageOwner);
                }
                zIsVideo = this.currentMessageObject.isVideo();
            } else {
                if (this.currentFileLocationVideo != null) {
                    file = FileLoader.getInstance(this.currentAccount).getPathToAttach(getFileLocation(this.currentFileLocationVideo), getFileLocationExt(this.currentFileLocationVideo), !(Objects.equals(getFileLocationExt(this.currentFileLocationVideo), "mp4") || this.avatarsDialogId == 0) || this.isEvent);
                    if (file == null || !file.exists()) {
                        file = FileLoader.getInstance(this.currentAccount).getPathToAttach(getFileLocation(this.currentFileLocationVideo), getFileLocationExt(this.currentFileLocationVideo), false);
                    }
                } else {
                    PageBlocksAdapter pageBlocksAdapter = this.pageBlocksAdapter;
                    if (pageBlocksAdapter != null) {
                        file = pageBlocksAdapter.getFile(this.currentIndex);
                        zIsVideo = this.pageBlocksAdapter.isVideo(this.currentIndex);
                    }
                }
                zIsVideo = false;
            }
            if (file != null && !file.exists()) {
                file = new File(FileLoader.getDirectory(4), file.getName());
            }
            if (file != null && file.exists()) {
                Intent intent = new Intent("android.intent.action.SEND");
                if (zIsVideo) {
                    mimeType = "video/mp4";
                } else {
                    MessageObject messageObject2 = this.currentMessageObject;
                    if (messageObject2 != null) {
                        mimeType = messageObject2.getMimeType();
                    } else {
                        mimeType = "image/jpeg";
                    }
                }
                Uri uriForFile = FileProvider.getUriForFile(this.parentActivity, ApplicationLoader.getApplicationId() + ".provider", file);
                intent.setFlags(1);
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
                intent.setDataAndType(uriForFile, mimeType);
                this.parentActivity.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("ShareFile", C2369R.string.ShareFile)), 500);
                return;
            }
            showDownloadAlert();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScaleToFill() {
        float bitmapWidth = this.centerImage.getBitmapWidth();
        float bitmapHeight = this.centerImage.getBitmapHeight();
        if (bitmapWidth == 0.0f || bitmapHeight == 0.0f) {
            return;
        }
        float containerViewWidth = getContainerViewWidth();
        float containerViewHeight = getContainerViewHeight();
        float fMin = Math.min(containerViewHeight / bitmapHeight, containerViewWidth / bitmapWidth);
        float fMax = Math.max(containerViewWidth / ((int) (bitmapWidth * fMin)), containerViewHeight / ((int) (bitmapHeight * fMin)));
        this.scale = fMax;
        updateMinMax(fMax);
    }

    public void setParentAlert(ChatAttachAlert chatAttachAlert) {
        this.parentAlert = chatAttachAlert;
    }

    public void nullifyParentAlert(ChatAttachAlert chatAttachAlert) {
        if (this.parentAlert == chatAttachAlert) {
            this.parentAlert = null;
        }
    }

    public void setParentActivity(Activity activity) {
        setParentActivity(activity, null, null);
    }

    public void setParentActivity(Activity activity, Theme.ResourcesProvider resourcesProvider) {
        setParentActivity(activity, null, resourcesProvider);
    }

    public void setParentActivity(BaseFragment baseFragment) {
        setParentActivity(baseFragment, (Theme.ResourcesProvider) null);
    }

    public void setParentActivity(BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
        setParentActivity(null, baseFragment, resourcesProvider);
    }

    public Activity getParentActivity() {
        return this.parentActivity;
    }

    public void setParentActivity(Activity activity, BaseFragment baseFragment, final Theme.ResourcesProvider resourcesProvider) {
        boolean z;
        EditTextEmoji editTextEmoji;
        EditTextEmoji editTextEmoji2;
        final Activity parentActivity = activity != null ? activity : baseFragment.getParentActivity();
        int i = 0;
        Theme.createChatResources(parentActivity, false);
        this.resourcesProvider = resourcesProvider;
        this.parentFragment = baseFragment;
        Runnable runnable = this.parentFragmentNullifier;
        if (runnable != null) {
            AndroidUtilities.cancelRunOnUIThread(runnable);
        }
        int i2 = UserConfig.selectedAccount;
        this.currentAccount = i2;
        this.centerImage.setCurrentAccount(i2);
        this.leftImage.setCurrentAccount(this.currentAccount);
        this.rightImage.setCurrentAccount(this.currentAccount);
        CaptionPhotoViewer captionPhotoViewer = this.captionEdit;
        if (captionPhotoViewer != null) {
            captionPhotoViewer.setAccount(this.currentAccount);
        }
        StickerMakerView stickerMakerView = this.stickerMakerView;
        if (stickerMakerView != null) {
            stickerMakerView.setCurrentAccount(this.currentAccount);
        }
        if (this.parentActivity == parentActivity || parentActivity == null) {
            updateColors();
            return;
        }
        this.inBubbleMode = parentActivity instanceof BubbleActivity;
        this.parentActivity = parentActivity;
        this.activityContext = new ContextThemeWrapper(this.parentActivity, C2369R.style.Theme_TMessages);
        this.touchSlop = ViewConfiguration.get(this.parentActivity).getScaledTouchSlop();
        boolean z2 = true;
        if (progressDrawables == null) {
            progressDrawables = new Drawable[]{ContextCompat.getDrawable(this.parentActivity, C2369R.drawable.circle_big), ContextCompat.getDrawable(this.parentActivity, C2369R.drawable.cancel_big), ContextCompat.getDrawable(this.parentActivity, C2369R.drawable.load_big)};
        }
        this.scroller = new Scroller(parentActivity);
        C578414 c578414 = new C578414(parentActivity);
        this.windowView = c578414;
        c578414.setBackground(this.backgroundDrawable);
        this.windowView.setFocusable(false);
        ClippingImageView clippingImageView = new ClippingImageView(parentActivity);
        this.animatingImageView = clippingImageView;
        clippingImageView.setAnimationValues(this.animationValues, false, false);
        this.windowView.addView(this.animatingImageView, LayoutHelper.createFrame(40, 40.0f));
        FrameLayoutDrawer frameLayoutDrawer = new FrameLayoutDrawer(parentActivity, parentActivity) { // from class: org.telegram.ui.PhotoViewer.15
            @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayout
            public int getBottomPadding() {
                return PhotoViewer.this.pickerView.getHeight();
            }

            @Override // android.view.ViewGroup, android.view.View
            public boolean dispatchTouchEvent(MotionEvent motionEvent) {
                PhotoViewer.this.textSelectionHelper.getOverlayView(getContext()).checkCancelAction(motionEvent);
                if (PhotoViewer.this.textSelectionHelper.isInSelectionMode()) {
                    PhotoViewer.this.textSelectionHelper.getOverlayView(getContext()).onTouchEvent(motionEvent);
                    return true;
                }
                return super.dispatchTouchEvent(motionEvent);
            }

            @Override // org.telegram.ui.PhotoViewer.FrameLayoutDrawer, android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view, long j) {
                if (view == PhotoViewer.this.textSelectionHelper.getOverlayView(PhotoViewer.this.windowView.getContext()) || view == PhotoViewer.this.stickerMakerBackgroundView) {
                    return false;
                }
                return super.drawChild(canvas, view, j);
            }

            @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                PhotoViewer.this.textSelectionHelper.getOverlayView(PhotoViewer.this.windowView.getContext()).draw(canvas);
            }

            @Override // org.telegram.ui.PhotoViewer.FrameLayoutDrawer, org.telegram.p023ui.Components.SizeNotifierFrameLayoutPhoto, org.telegram.p023ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z3, int i3, int i4, int i5, int i6) {
                if (PhotoViewer.this.btnLayout != null && PhotoViewer.this.undoBtn != null) {
                    int iM1146dp = (i5 - i3) - AndroidUtilities.m1146dp(20.0f);
                    PhotoViewer.this.undoBtn.setTranslationY(((-iM1146dp) / 2.0f) - AndroidUtilities.m1146dp(47.0f));
                    float f = iM1146dp / 2.0f;
                    PhotoViewer.this.btnLayout.setTranslationY(AndroidUtilities.m1146dp(47.0f) + f);
                    PhotoViewer.this.cutOutBtn.setTranslationY(AndroidUtilities.m1146dp(47.0f) + f);
                    PhotoViewer.this.outlineBtn.setTranslationY(f + AndroidUtilities.m1146dp(95.0f));
                }
                super.onLayout(z3, i3, i4, i5, i6);
            }
        };
        this.containerView = frameLayoutDrawer;
        frameLayoutDrawer.setFocusable(false);
        VideoFramesRewinder videoFramesRewinder = this.framesRewinder;
        if (videoFramesRewinder != null) {
            videoFramesRewinder.setParentView(this.containerView);
        }
        this.containerView.setClipChildren(true);
        this.containerView.setClipToPadding(true);
        this.windowView.setClipChildren(false);
        this.windowView.setClipToPadding(false);
        BlurringShader.BlurManager blurManager = new BlurringShader.BlurManager(this.containerView);
        this.blurManager = blurManager;
        blurManager.padding = 1;
        this.shadowBlurer = new BlurringShader.StoryBlurDrawer(blurManager, this.containerView, 6);
        this.windowView.addView(this.containerView, LayoutHelper.createFrame(-1, -1, 51));
        this.containerView.setFitsSystemWindows(true);
        this.containerView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda15
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return this.f$0.lambda$setParentActivity$7(view, windowInsets);
            }
        });
        this.containerView.setSystemUiVisibility(1792);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        this.windowLayoutParams = layoutParams;
        layoutParams.height = -1;
        layoutParams.format = -3;
        layoutParams.width = -1;
        layoutParams.gravity = 51;
        layoutParams.type = 99;
        if (Build.VERSION.SDK_INT >= 28) {
            layoutParams.layoutInDisplayCutoutMode = 1;
        }
        layoutParams.flags = -2147286784;
        PaintingOverlay paintingOverlay = new PaintingOverlay(this.parentActivity);
        this.paintingOverlay = paintingOverlay;
        this.containerView.addView(paintingOverlay, LayoutHelper.createFrame(-2, -2.0f));
        PaintingOverlay paintingOverlay2 = new PaintingOverlay(this.parentActivity);
        this.leftPaintingOverlay = paintingOverlay2;
        this.containerView.addView(paintingOverlay2, LayoutHelper.createFrame(-2, -2.0f));
        PaintingOverlay paintingOverlay3 = new PaintingOverlay(this.parentActivity);
        this.rightPaintingOverlay = paintingOverlay3;
        this.containerView.addView(paintingOverlay3, LayoutHelper.createFrame(-2, -2.0f));
        ActionBar actionBar = new ActionBar(parentActivity) { // from class: org.telegram.ui.PhotoViewer.16
            @Override // android.view.View
            public void setAlpha(float f) {
                super.setAlpha(f);
                PhotoViewer.this.containerView.invalidate();
            }
        };
        this.actionBar = actionBar;
        actionBar.setOverlayTitleAnimation(true);
        this.actionBar.setTitleColor(-1);
        this.actionBar.setSubtitleColor(-1);
        this.actionBar.setBackgroundColor(2130706432);
        this.actionBar.setOccupyStatusBar(isStatusBarVisible());
        this.actionBar.setItemsBackgroundColor(1090519039, false);
        this.actionBar.setItemsColor(-1, false);
        this.actionBar.setBackButtonImage(C2369R.drawable.ic_ab_back);
        PhotoViewerActionBarContainer photoViewerActionBarContainer = new PhotoViewerActionBarContainer(parentActivity);
        this.actionBarContainer = photoViewerActionBarContainer;
        this.actionBar.addView(photoViewerActionBarContainer, LayoutHelper.createFrame(-1, -1, Opcodes.DNEG));
        this.containerView.addView(this.actionBar, LayoutHelper.createFrame(-1, -2.0f));
        PhotoCountView photoCountView = new PhotoCountView(parentActivity);
        this.countView = photoCountView;
        this.containerView.addView(photoCountView, LayoutHelper.createFrame(-1, -2, 55));
        if (ExteraConfig.hidePhotoCounter) {
            this.countView.setVisibility(8);
        }
        this.actionBar.setActionBarMenuOnItemClick(new C578717(resourcesProvider));
        ActionBarMenu actionBarMenuCreateMenu = this.actionBar.createMenu();
        this.menu = actionBarMenuCreateMenu;
        actionBarMenuCreateMenu.setOnLayoutListener(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda26
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.updateActionBarTitlePadding();
            }
        });
        ActionBarMenuItem actionBarMenuItemAddItem = this.menu.addItem(11, C2369R.drawable.msg_mask);
        this.masksItem = actionBarMenuItemAddItem;
        actionBarMenuItemAddItem.setContentDescription(LocaleController.getString(C2369R.string.Masks));
        ActionBarMenuItem actionBarMenuItemAddItem2 = this.menu.addItem(18, C2369R.drawable.msg_header_draw);
        this.editItem = actionBarMenuItemAddItem2;
        actionBarMenuItemAddItem2.setContentDescription(LocaleController.getString(C2369R.string.AccDescrPhotoEditor));
        ActionBarMenuItem actionBarMenuItemAddItem3 = this.menu.addItem(4, C2369R.drawable.msg_header_share);
        this.sendItem = actionBarMenuItemAddItem3;
        actionBarMenuItemAddItem3.setContentDescription(LocaleController.getString(C2369R.string.Forward));
        ActionBarMenu actionBarMenu = this.menu;
        ChooseQualityLayout$QualityIcon chooseQualityLayout$QualityIcon = new ChooseQualityLayout$QualityIcon(this.activityContext, C2369R.drawable.video_settings, new DarkThemeResourceProvider());
        this.videoItemIcon = chooseQualityLayout$QualityIcon;
        ActionBarMenuItem actionBarMenuItemAddItem4 = actionBarMenu.addItem(1, chooseQualityLayout$QualityIcon);
        this.videoItem = actionBarMenuItemAddItem4;
        this.videoItemIcon.setCallback(actionBarMenuItemAddItem4.getIconView());
        this.videoItem.getPopupLayout().setSwipeBackForegroundColor(-14540254);
        this.videoItem.getPopupLayout().swipeBackGravityRight = true;
        this.videoItem.getPopupLayout().setFitItems(true);
        this.videoItem.setMenuXOffset(AndroidUtilities.m1146dp(3.0f));
        ActionBarMenuSlider.SpeedSlider speedSlider = new ActionBarMenuSlider.SpeedSlider(this.activityContext, resourcesProvider);
        this.speedItem = speedSlider;
        speedSlider.setStops(new float[]{0.5f, 1.0f, 1.5f, 2.0f, 2.5f});
        this.speedItem.setMinimumWidth(AndroidUtilities.m1146dp(196.0f));
        this.speedItem.setDrawShadow(false);
        this.speedItem.setBackgroundColor(-14540254);
        this.speedItem.setTextColor(-1);
        this.speedItem.setLabel(LocaleController.getString(C2369R.string.VideoPlayerSpeed));
        this.speedItem.setOnValueChange(new Utilities.Callback2() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda36
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$setParentActivity$8((Float) obj, (Boolean) obj2);
            }
        });
        this.videoItem.getPopupLayout().addView((View) this.speedItem, LayoutHelper.createLinear(-1, 44));
        ActionBarPopupWindow.GapView gapViewAddColoredGap = this.videoItem.addColoredGap();
        this.speedGap = gapViewAddColoredGap;
        gapViewAddColoredGap.setColor(-15198184);
        ActionBarPopupWindow.ActionBarPopupWindowLayout popupLayout = this.videoItem.getPopupLayout();
        SpeedButtonsLayout speedButtonsLayout = new SpeedButtonsLayout(this.activityContext, new SpeedButtonsLayout.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda47
            @Override // org.telegram.ui.SpeedButtonsLayout.Callback
            public final void onSpeedSelected(float f, boolean z3, boolean z4) {
                this.f$0.chooseSpeed(f, z3, z4);
            }
        });
        this.chooseSpeedLayout = speedButtonsLayout;
        popupLayout.addView(speedButtonsLayout);
        LinearLayout linearLayout = new LinearLayout(this.activityContext);
        this.videoQualityLayout = linearLayout;
        linearLayout.setOrientation(1);
        this.videoItem.getPopupLayout().addView(this.videoQualityLayout);
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem = this.videoItem.addSubItem(22, C2369R.drawable.menu_video_loop, LocaleController.getString(C2369R.string.VideoPlayerLoop));
        this.loopItem = actionBarMenuSubItemAddSubItem;
        actionBarMenuSubItemAddSubItem.setSelectorColor(268435455);
        CastMediaRouteButton castMediaRouteButton = new CastMediaRouteButton(this.activityContext) { // from class: org.telegram.ui.PhotoViewer.18
            @Override // org.telegram.p023ui.Components.CastMediaRouteButton
            public void stateUpdated(boolean z3) {
                if (PhotoViewer.this.castItem != null) {
                    PhotoViewer.this.castItem.setEnabledByColor(z3, -1, -9194260);
                    PhotoViewer.this.castItem.setSelectorColor(z3 ? 259241196 : 268435455);
                }
                if (PhotoViewer.this.videoPlayer != null) {
                    PhotoViewer.this.videoPlayer.setMute(CastSync.isActive() || PhotoViewer.this.muteVideo);
                }
                if (PhotoViewer.this.videoItemIcon != null) {
                    PhotoViewer.this.videoItemIcon.setCasting(CastSync.isActive(), true);
                }
            }
        };
        this.castItemButton = castMediaRouteButton;
        try {
            castMediaRouteButton.setRouteSelector(CastContext.getSharedInstance(this.activityContext).getMergedSelector());
            z = true;
        } catch (Exception e) {
            FileLog.m1160e(e);
            z = false;
        }
        this.castItemButton.setVisibility(4);
        if (z) {
            ActionBarMenuSubItem actionBarMenuSubItemAddSubItem2 = this.videoItem.addSubItem(24, C2369R.drawable.menu_video_chromecast, LocaleController.getString(C2369R.string.VideoPlayerChromecast));
            this.castItem = actionBarMenuSubItemAddSubItem2;
            actionBarMenuSubItemAddSubItem2.setEnabledByColor(false, -1, -9194260);
            this.castItem.setSelectorColor(268435455);
            this.castItem.addView(this.castItemButton, 0, LayoutHelper.createFrame(-1, -1.0f));
        }
        this.videoItem.redrawPopup(-115203550);
        this.videoItem.setOnMenuDismiss(new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda57
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$setParentActivity$9((Boolean) obj);
            }
        });
        ActionBarMenuItem actionBarMenuItemAddItem5 = this.menu.addItem(0, C2369R.drawable.media_more);
        this.menuItem = actionBarMenuItemAddItem5;
        actionBarMenuItemAddItem5.setContentDescription(LocaleController.getString(C2369R.string.AccDescrMoreOptions));
        this.menuItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda58
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setParentActivity$10(view);
            }
        });
        this.menuItem.setOnMenuDismiss(new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda59
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$setParentActivity$11((Boolean) obj);
            }
        });
        this.menuItem.getPopupLayout().setSwipeBackForegroundColor(-14540254);
        this.menuItem.getPopupLayout().swipeBackGravityRight = true;
        this.menuItem.getPopupLayout().setFitItems(true);
        this.searchPhotoPopupWrapper = new SearchPhotoPopupWrapper(this.activityContext, this.menuItem.getPopupLayout().getSwipeBack(), new Utilities.Callback2() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda60
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$setParentActivity$14(resourcesProvider, (String) obj, (Boolean) obj2);
            }
        });
        this.chooseDownloadQualityLayout = new ChooseDownloadQualityLayout(this.activityContext, this.menuItem.getPopupLayout().getSwipeBack(), new ChooseDownloadQualityLayout.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda61
            @Override // org.telegram.ui.ChooseDownloadQualityLayout.Callback
            public final void onQualitySelected(MessageObject messageObject, VideoPlayer.Quality quality) {
                this.f$0.lambda$setParentActivity$17(messageObject, quality);
            }
        });
        ActionBarMenuSubItem actionBarMenuSubItemAddSwipeBackItem = this.menuItem.addSwipeBackItem(C2369R.drawable.msg_search, null, LocaleController.getString(C2369R.string.Search), this.searchPhotoPopupWrapper.searchSwipeBackLayout);
        this.searchItem = actionBarMenuSubItemAddSwipeBackItem;
        actionBarMenuSubItemAddSwipeBackItem.setColors(-328966, -328966);
        ActionBarPopupWindow.GapView gapViewAddColoredGap2 = this.menuItem.addColoredGap();
        this.searchGap = gapViewAddColoredGap2;
        gapViewAddColoredGap2.setColor(-15198184);
        ActionBarMenuSubItem colors = this.menuItem.addSwipeBackItem(C2369R.drawable.msg_gallery, null, LocaleController.getString(C2369R.string.SaveToGallery), this.chooseDownloadQualityLayout.layout).setColors(-328966, -328966);
        this.galleryButton = colors;
        colors.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda62
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$setParentActivity$18(view);
            }
        });
        this.menuItem.addSubItem(10, C2369R.drawable.msg_openin, LocaleController.getString(C2369R.string.OpenInExternalApp)).setColors(-328966, -328966);
        this.pipItem = this.menuItem.addSubItem(6, C2369R.drawable.menu_video_pip, LocaleController.getString(C2369R.string.PipMinimize)).setColors(-328966, -328966);
        ActionBarMenuSubItem actionBarMenuSubItemAddSubItem3 = this.menuItem.addSubItem(3, C2369R.drawable.msg_media, LocaleController.getString(C2369R.string.ShowAllMedia));
        this.allMediaItem = actionBarMenuSubItemAddSubItem3;
        actionBarMenuSubItemAddSubItem3.setColors(-328966, -328966);
        this.menuItem.addSubItem(12, C2369R.drawable.msg_gif, LocaleController.getString(C2369R.string.SaveToGIFs)).setColors(-328966, -328966);
        this.menuItem.addSubItem(5, C2369R.drawable.msg_message, LocaleController.getString(C2369R.string.ShowInChat)).setColors(-328966, -328966);
        this.menuItem.addSubItem(21, C2369R.drawable.menu_reply, LocaleController.getString(C2369R.string.Reply)).setColors(-328966, -328966);
        this.menuItem.addSubItem(23, C2369R.drawable.msg_report, LocaleController.getString(C2369R.string.ReportProfilePhoto)).setColors(-328966, -328966);
        this.menuItem.addSubItem(9, C2369R.drawable.msg_shareout, LocaleController.getString(C2369R.string.ShareFile)).setColors(-328966, -328966);
        this.menuItem.addSubItem(50, C2369R.drawable.msg_copy_photo, LocaleController.getString(C2369R.string.CopyPhoto)).setColors(-328966, -328966);
        this.menuItem.addSubItem(51, C2369R.drawable.msg_copy_photo, LocaleController.getString(C2369R.string.CopyFrame)).setColors(-328966, -328966);
        this.menuItem.addSubItem(52, C2369R.drawable.msg_forward, LocaleController.getString(C2369R.string.Forward)).setColors(-328966, -328966);
        this.menuItem.addSubItem(49, C2369R.drawable.media_draw, LocaleController.getString(C2369R.string.EditPhoto)).setColors(-328966, -328966);
        this.menuItem.addSubItem(13, C2369R.drawable.msg_sticker, LocaleController.getString(C2369R.string.ShowStickers)).setColors(-328966, -328966);
        this.menuItem.addSubItem(14, C2369R.drawable.msg_openprofile, LocaleController.getString(C2369R.string.SetAsMain)).setColors(-328966, -328966);
        this.menuItem.addSubItem(19, C2369R.drawable.msg_translate, LocaleController.getString(C2369R.string.TranslateMessage)).setColors(-328966, -328966);
        this.menuItem.addSubItem(20, C2369R.drawable.msg_translate, LocaleController.getString(C2369R.string.HideTranslation)).setColors(-328966, -328966);
        this.menuItem.addSubItem(7, C2369R.drawable.msg_delete, LocaleController.getString(C2369R.string.Delete)).setColors(-328966, -328966);
        this.menuItem.addSubItem(8, C2369R.drawable.msg_cancel, LocaleController.getString(C2369R.string.StopDownload)).setColors(-328966, -328966);
        this.menuItem.redrawPopup(-115203550);
        this.menuItem.hideSubItem(19);
        this.menuItem.hideSubItem(20);
        setMenuItemIcon(false, true);
        this.menuItem.setPopupItemsSelectorColor(268435455);
        this.menuItem.setSubMenuDelegate(new ActionBarMenuItem.ActionBarSubMenuItemDelegate() { // from class: org.telegram.ui.PhotoViewer.19
            @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarSubMenuItemDelegate
            public void onShowSubMenu() {
                if (PhotoViewer.this.videoPlayerControlVisible && PhotoViewer.this.isPlaying) {
                    AndroidUtilities.cancelRunOnUIThread(PhotoViewer.this.hideActionBarRunnable);
                }
            }

            @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarSubMenuItemDelegate
            public void onHideSubMenu() {
                if (PhotoViewer.this.videoPlayerControlVisible && PhotoViewer.this.isPlaying) {
                    PhotoViewer.this.scheduleActionBarHide();
                }
            }
        });
        FrameLayout frameLayout = new FrameLayout(this.activityContext) { // from class: org.telegram.ui.PhotoViewer.20
            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
            }
        };
        this.bottomLayout = frameLayout;
        frameLayout.setBackgroundColor(2130706432);
        this.containerView.addView(this.bottomLayout, LayoutHelper.createFrame(-1, 48, 83));
        View view = new View(this.activityContext);
        this.navigationBar = view;
        view.setBackgroundColor(this.sendPhotoType == 11 ? -16777216 : 2130706432);
        this.windowView.addView(this.navigationBar, LayoutHelper.createFrame(-1.0f, this.navigationBarHeight / AndroidUtilities.density, 87));
        this.pressedDrawable[0] = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{838860800, 0});
        this.pressedDrawable[0].setShape(0);
        this.pressedDrawable[1] = new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, new int[]{838860800, 0});
        this.pressedDrawable[1].setShape(0);
        GroupedPhotosListView groupedPhotosListView = new GroupedPhotosListView(this.activityContext, AndroidUtilities.m1146dp(10.0f));
        this.groupedPhotosListView = groupedPhotosListView;
        this.containerView.addView(groupedPhotosListView, LayoutHelper.createFrame(-1, 68, 83));
        this.groupedPhotosListView.setDelegate(new GroupedPhotosListView.GroupedPhotosListViewDelegate() { // from class: org.telegram.ui.PhotoViewer.21
            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public int getCurrentIndex() {
                return PhotoViewer.this.currentIndex;
            }

            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public int getCurrentAccount() {
                return PhotoViewer.this.currentAccount;
            }

            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public long getAvatarsDialogId() {
                return PhotoViewer.this.avatarsDialogId;
            }

            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public int getSlideshowMessageId() {
                return PhotoViewer.this.slideshowMessageId;
            }

            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public ArrayList getImagesArrLocations() {
                return PhotoViewer.this.imagesArrLocations;
            }

            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public ArrayList getImagesArr() {
                return PhotoViewer.this.imagesArr;
            }

            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public List getPageBlockArr() {
                if (PhotoViewer.this.pageBlocksAdapter != null) {
                    return PhotoViewer.this.pageBlocksAdapter.getAll();
                }
                return null;
            }

            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public Object getParentObject() {
                if (PhotoViewer.this.pageBlocksAdapter != null) {
                    return PhotoViewer.this.pageBlocksAdapter.getParentObject();
                }
                return null;
            }

            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public void setCurrentIndex(int i3) throws Resources.NotFoundException, NumberFormatException {
                PhotoViewer.this.currentIndex = -1;
                if (PhotoViewer.this.currentThumb != null) {
                    PhotoViewer.this.currentThumb.release();
                    PhotoViewer.this.currentThumb = null;
                }
                PhotoViewer.this.dontAutoPlay = true;
                PhotoViewer.this.setImageIndex(i3);
                PhotoViewer.this.dontAutoPlay = false;
            }

            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public void onShowAnimationStart() {
                PhotoViewer.this.containerView.requestLayout();
            }

            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public void onStopScrolling() throws NumberFormatException {
                PhotoViewer photoViewer = PhotoViewer.this;
                if (photoViewer.shouldMessageObjectAutoPlayed(photoViewer.currentMessageObject)) {
                    PhotoViewer.this.playerAutoStarted = true;
                    PhotoViewer.this.onActionClick(true);
                    PhotoViewer.this.checkProgress(0, false, true);
                }
            }

            @Override // org.telegram.ui.Components.GroupedPhotosListView.GroupedPhotosListViewDelegate
            public boolean forceAll() {
                return PhotoViewer.this.placeProvider != null && PhotoViewer.this.placeProvider.forceAllInGroup();
            }
        });
        for (int i3 = 0; i3 < 3; i3++) {
            this.fullscreenButton[i3] = new ImageView(this.parentActivity);
            this.fullscreenButton[i3].setImageResource(C2369R.drawable.msg_maxvideo);
            this.fullscreenButton[i3].setContentDescription(LocaleController.getString("AccSwitchToFullscreen", C2369R.string.AccSwitchToFullscreen));
            this.fullscreenButton[i3].setScaleType(ImageView.ScaleType.CENTER);
            this.fullscreenButton[i3].setBackground(Theme.createSelectorDrawable(1090519039));
            this.fullscreenButton[i3].setVisibility(4);
            this.fullscreenButton[i3].setAlpha(1.0f);
            this.containerView.addView(this.fullscreenButton[i3], LayoutHelper.createFrame(48, 48.0f));
            this.fullscreenButton[i3].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda16
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$setParentActivity$19(view2);
                }
            });
        }
        this.textSelectionHelper = new TextSelectionHelper.SimpleTextSelectionHelper(null, new DarkThemeResourceProvider()) { // from class: org.telegram.ui.PhotoViewer.22
            @Override // org.telegram.p023ui.Cells.TextSelectionHelper
            public int getParentBottomPadding() {
                return 0;
            }
        };
        CaptionTextViewSwitcher captionTextViewSwitcher = new CaptionTextViewSwitcher(this.containerView.getContext());
        this.captionTextViewSwitcher = captionTextViewSwitcher;
        captionTextViewSwitcher.setFactory(new ViewSwitcher.ViewFactory() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda17
            @Override // android.widget.ViewSwitcher.ViewFactory
            public final View makeView() {
                return this.f$0.lambda$setParentActivity$20();
            }
        });
        this.captionTextViewSwitcher.setVisibility(4);
        setCaptionHwLayerEnabled(true);
        for (int i4 = 0; i4 < 3; i4++) {
            this.photoProgressViews[i4] = new PhotoProgressView(this.containerView) { // from class: org.telegram.ui.PhotoViewer.23
                @Override // org.telegram.ui.PhotoViewer.PhotoProgressView
                protected void onBackgroundStateUpdated(int i5) {
                    if (this == PhotoViewer.this.photoProgressViews[0]) {
                        PhotoViewer.this.updateAccessibilityOverlayVisibility();
                    }
                }

                @Override // org.telegram.ui.PhotoViewer.PhotoProgressView
                protected void onVisibilityChanged(boolean z3) {
                    if (this == PhotoViewer.this.photoProgressViews[0]) {
                        PhotoViewer.this.updateAccessibilityOverlayVisibility();
                    }
                }
            };
            this.photoProgressViews[i4].setBackgroundState(0, false, true);
        }
        RadialProgressView radialProgressView = new RadialProgressView(this.activityContext, resourcesProvider) { // from class: org.telegram.ui.PhotoViewer.24
            @Override // org.telegram.p023ui.Components.RadialProgressView, android.view.View
            public void setAlpha(float f) {
                super.setAlpha(f);
                if (PhotoViewer.this.containerView != null) {
                    PhotoViewer.this.containerView.invalidate();
                }
            }

            @Override // android.view.View
            public void invalidate() {
                super.invalidate();
                if (PhotoViewer.this.containerView != null) {
                    PhotoViewer.this.containerView.invalidate();
                }
            }
        };
        this.miniProgressView = radialProgressView;
        radialProgressView.setUseSelfAlpha(true);
        this.miniProgressView.setProgressColor(-1);
        this.miniProgressView.setSize(AndroidUtilities.m1146dp(54.0f));
        this.miniProgressView.setBackgroundResource(C2369R.drawable.circle_big);
        this.miniProgressView.setVisibility(4);
        this.miniProgressView.setAlpha(0.0f);
        this.containerView.addView(this.miniProgressView, LayoutHelper.createFrame(64, 64, 17));
        createVideoControlsInterface();
        RadialProgressView radialProgressView2 = new RadialProgressView(this.parentActivity, resourcesProvider);
        this.progressView = radialProgressView2;
        radialProgressView2.setProgressColor(-1);
        this.progressView.setBackgroundResource(C2369R.drawable.circle_big);
        this.progressView.setVisibility(4);
        this.containerView.addView(this.progressView, LayoutHelper.createFrame(54, 54, 17));
        PickerBottomLayoutViewer pickerBottomLayoutViewer = new PickerBottomLayoutViewer(this.parentActivity);
        this.qualityPicker = pickerBottomLayoutViewer;
        pickerBottomLayoutViewer.setBackgroundColor(2130706432);
        this.qualityPicker.updateSelectedCount(0, false);
        this.qualityPicker.setTranslationY(AndroidUtilities.m1146dp(120.0f));
        this.qualityPicker.doneButton.setText(LocaleController.getString("Done", C2369R.string.Done).toUpperCase());
        TextView textView = this.qualityPicker.doneButton;
        int i5 = Theme.key_chat_editMediaButton;
        textView.setTextColor(getThemedColor(i5));
        this.containerView.addView(this.qualityPicker, LayoutHelper.createFrame(-1, 48, 83));
        this.qualityPicker.cancelButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda18
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws NumberFormatException {
                this.f$0.lambda$setParentActivity$21(view2);
            }
        });
        this.qualityPicker.doneButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws NumberFormatException {
                this.f$0.lambda$setParentActivity$22(view2);
            }
        });
        VideoForwardDrawable videoForwardDrawable = new VideoForwardDrawable(false);
        this.videoForwardDrawable = videoForwardDrawable;
        videoForwardDrawable.setDelegate(new VideoForwardDrawable.VideoForwardDrawableDelegate() { // from class: org.telegram.ui.PhotoViewer.25
            @Override // org.telegram.ui.Components.VideoForwardDrawable.VideoForwardDrawableDelegate
            public void onAnimationEnd() {
            }

            @Override // org.telegram.ui.Components.VideoForwardDrawable.VideoForwardDrawableDelegate
            public void invalidate() {
                PhotoViewer.this.containerView.invalidate();
            }
        });
        final FrameLayoutDrawer frameLayoutDrawer2 = this.containerView;
        Objects.requireNonNull(frameLayoutDrawer2);
        this.seekSpeedDrawable = new SeekSpeedDrawable(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda20
            @Override // java.lang.Runnable
            public final void run() {
                frameLayoutDrawer2.invalidate();
            }
        }, false, false);
        QualityChooseView qualityChooseView = new QualityChooseView(this.parentActivity);
        this.qualityChooseView = qualityChooseView;
        qualityChooseView.setTranslationY(AndroidUtilities.m1146dp(120.0f));
        this.qualityChooseView.setVisibility(4);
        this.qualityChooseView.setBackgroundColor(2130706432);
        this.containerView.addView(this.qualityChooseView, LayoutHelper.createFrame(-1, 70.0f, 83, 0.0f, 0.0f, 0.0f, 48.0f));
        new Paint().setColor(2130706432);
        FrameLayout frameLayout2 = new FrameLayout(this.activityContext) { // from class: org.telegram.ui.PhotoViewer.26
            private final Paint bgPaint = new Paint(3);
            private final LinearGradient bgGradient = new LinearGradient(0.0f, 0.0f, 0.0f, 16.0f, new int[]{0, 2130706432}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP);
            private final Matrix bgMatrix = new Matrix();

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                Canvas canvas2;
                if (PhotoViewer.this.fancyShadows) {
                    canvas2 = canvas;
                } else {
                    int measuredHeight = PhotoViewer.this.doneButtonFullWidth.getVisibility() == 0 ? getMeasuredHeight() - AndroidUtilities.m1146dp(48.0f) : 0;
                    if (PhotoViewer.this.sendPhotoType == 0 || PhotoViewer.this.sendPhotoType == 2 || PhotoViewer.this.sendPhotoType == -1) {
                        this.bgMatrix.reset();
                        float fMin = Math.min(AndroidUtilities.m1146dp(40.0f), getMeasuredHeight() - measuredHeight);
                        this.bgMatrix.postTranslate(0.0f, measuredHeight);
                        this.bgMatrix.postScale(1.0f, fMin / 16.0f);
                        this.bgGradient.setLocalMatrix(this.bgMatrix);
                        this.bgPaint.setShader(this.bgGradient);
                    } else {
                        this.bgPaint.setShader(null);
                        this.bgPaint.setColor(2130706432);
                    }
                    canvas2 = canvas;
                    canvas2.drawRect(0.0f, measuredHeight, getMeasuredWidth(), getMeasuredHeight(), this.bgPaint);
                }
                super.dispatchDraw(canvas2);
            }

            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i6, int i7) {
                ((FrameLayout.LayoutParams) PhotoViewer.this.itemsLayout.getLayoutParams()).rightMargin = PhotoViewer.this.pickerViewSendButton.getVisibility() == 0 ? AndroidUtilities.m1146dp(70.0f) : 0;
                super.onMeasure(i6, i7);
            }

            @Override // android.view.View
            public void setTranslationY(float f) {
                super.setTranslationY(f);
                if (PhotoViewer.this.videoTimelineViewContainer != null && PhotoViewer.this.videoTimelineViewContainer.getVisibility() != 8) {
                    PhotoViewer.this.videoTimelineViewContainer.setTranslationY(PhotoViewer.this.pickerView.getTranslationY() - (Math.max(0, PhotoViewer.this.captionEdit.getEditTextHeight() - AndroidUtilities.m1146dp(46.0f)) * PhotoViewer.this.captionEdit.getAlpha()));
                }
                if (PhotoViewer.this.captionEditContainer != null) {
                    PhotoViewer.this.captionEditContainer.setTranslationY(f);
                }
                if (PhotoViewer.this.videoAvatarTooltip == null || PhotoViewer.this.videoAvatarTooltip.getVisibility() == 8) {
                    return;
                }
                PhotoViewer.this.videoAvatarTooltip.setTranslationY(f);
            }

            @Override // android.view.View
            public void setAlpha(float f) {
                super.setAlpha(f);
                if (PhotoViewer.this.videoTimelineViewContainer != null && PhotoViewer.this.videoTimelineViewContainer.getVisibility() != 8) {
                    PhotoViewer.this.videoTimelineViewContainer.setAlpha(f);
                }
                if (PhotoViewer.this.captionEdit != null && PhotoViewer.this.captionEdit.getVisibility() != 8) {
                    PhotoViewer.this.captionEdit.setAlpha(PhotoViewer.this.captionEditAlpha[0] * f);
                }
                if (PhotoViewer.this.topCaptionEdit == null || PhotoViewer.this.topCaptionEdit.getVisibility() == 8) {
                    return;
                }
                PhotoViewer.this.topCaptionEdit.setAlpha(f * PhotoViewer.this.topCaptionEditAlpha[0]);
            }

            @Override // android.view.View
            public void setVisibility(int i6) {
                super.setVisibility(i6);
                if (PhotoViewer.this.videoTimelineViewContainer == null || PhotoViewer.this.videoTimelineViewContainer.getVisibility() == 8) {
                    return;
                }
                PhotoViewer.this.videoTimelineViewContainer.setVisibility(i6 == 0 ? 0 : 4);
            }

            @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z3, int i6, int i7, int i8, int i9) {
                super.onLayout(z3, i6, i7, i8, i9);
                if (PhotoViewer.this.itemsLayout.getVisibility() != 8) {
                    int iM1146dp = (((i8 - i6) - (PhotoViewer.this.pickerViewSendButton.getVisibility() == 0 ? AndroidUtilities.m1146dp(70.0f) : 0)) - PhotoViewer.this.itemsLayout.getMeasuredWidth()) / 2;
                    PhotoViewer.this.itemsLayout.layout(iM1146dp, PhotoViewer.this.itemsLayout.getTop(), PhotoViewer.this.itemsLayout.getMeasuredWidth() + iM1146dp, PhotoViewer.this.itemsLayout.getTop() + PhotoViewer.this.itemsLayout.getMeasuredHeight());
                }
            }
        };
        this.pickerView = frameLayout2;
        this.containerView.addView(frameLayout2, LayoutHelper.createFrame(-1, -2, 83));
        TextView textView2 = new TextView(this.containerView.getContext());
        this.docNameTextView = textView2;
        textView2.setTextSize(1, 15.0f);
        this.docNameTextView.setTypeface(AndroidUtilities.bold());
        this.docNameTextView.setSingleLine(true);
        this.docNameTextView.setMaxLines(1);
        TextView textView3 = this.docNameTextView;
        TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
        textView3.setEllipsize(truncateAt);
        this.docNameTextView.setTextColor(-1);
        this.docNameTextView.setGravity(3);
        this.pickerView.addView(this.docNameTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 20.0f, 23.0f, 84.0f, 0.0f));
        TextView textView4 = new TextView(this.containerView.getContext());
        this.docInfoTextView = textView4;
        textView4.setTextSize(1, 14.0f);
        this.docInfoTextView.setSingleLine(true);
        this.docInfoTextView.setMaxLines(1);
        this.docInfoTextView.setEllipsize(truncateAt);
        this.docInfoTextView.setTextColor(-1);
        this.docInfoTextView.setGravity(3);
        this.pickerView.addView(this.docInfoTextView, LayoutHelper.createFrame(-1, -2.0f, 51, 20.0f, 46.0f, 84.0f, 0.0f));
        TextView textView5 = new TextView(this.containerView.getContext());
        this.doneButtonFullWidth = textView5;
        int i6 = Theme.key_featuredStickers_addButton;
        textView5.setBackground(Theme.AdaptiveRipple.filledRect(getThemedColor(i6), 6.0f));
        TextView textView6 = this.doneButtonFullWidth;
        int i7 = Theme.key_featuredStickers_buttonText;
        textView6.setTextColor(getThemedColor(i7));
        this.doneButtonFullWidth.setEllipsize(truncateAt);
        this.doneButtonFullWidth.setGravity(17);
        this.doneButtonFullWidth.setLines(1);
        this.doneButtonFullWidth.setSingleLine(true);
        this.doneButtonFullWidth.setText(LocaleController.getString("SetAsMyPhoto", C2369R.string.SetAsMyPhoto));
        this.doneButtonFullWidth.setTextSize(1, 15.0f);
        this.doneButtonFullWidth.setTypeface(AndroidUtilities.bold());
        this.doneButtonFullWidth.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda21
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws NumberFormatException {
                this.f$0.lambda$setParentActivity$23(view2);
            }
        });
        this.doneButtonFullWidth.setVisibility(8);
        this.pickerView.addView(this.doneButtonFullWidth, LayoutHelper.createFrame(-1, 48.0f, 51, 20.0f, 0.0f, 20.0f, 64.0f));
        VideoTimelinePlayView videoTimelinePlayView = new VideoTimelinePlayView(this.parentActivity) { // from class: org.telegram.ui.PhotoViewer.27
            private final BlurringShader.StoryBlurDrawer blur;
            private final Path path = new Path();

            @Override // org.telegram.p023ui.Components.VideoTimelinePlayView
            protected boolean customBlur() {
                return true;
            }

            {
                this.blur = new BlurringShader.StoryBlurDrawer(PhotoViewer.this.blurManager, this, 0);
            }

            @Override // android.view.View
            public void setTranslationY(float f) {
                if (getTranslationY() != f) {
                    super.setTranslationY(f);
                    PhotoViewer.this.containerView.invalidate();
                }
            }

            @Override // org.telegram.p023ui.Components.VideoTimelinePlayView
            protected void drawBlur(Canvas canvas, RectF rectF) {
                canvas.save();
                canvas.clipRect(rectF);
                canvas.translate((-getX()) - PhotoViewer.this.videoTimelineViewContainer.getX(), (-getY()) - PhotoViewer.this.videoTimelineViewContainer.getY());
                PhotoViewer.this.drawCaptionBlur(canvas, this.blur, -14803426, AndroidUtilities.DARK_STATUS_BAR_OVERLAY, false, true, false);
                canvas.restore();
            }

            @Override // android.view.View
            public void invalidate() {
                if (SharedConfig.photoViewerBlur && (PhotoViewer.this.animationInProgress == 1 || PhotoViewer.this.animationInProgress == 2 || PhotoViewer.this.animationInProgress == 3)) {
                    return;
                }
                super.invalidate();
            }
        };
        this.videoTimelineView = videoTimelinePlayView;
        videoTimelinePlayView.setDelegate(new C579928());
        FrameLayout frameLayout3 = new FrameLayout(this.parentActivity);
        this.videoTimelineViewContainer = frameLayout3;
        frameLayout3.setClipChildren(false);
        this.videoTimelineViewContainer.addView(this.videoTimelineView, LayoutHelper.createFrame(-1, 54, 83));
        showVideoTimeline(false, false);
        this.containerView.addView(this.videoTimelineViewContainer, LayoutHelper.createFrame(-1, 54.0f, 83, 0.0f, 8.0f, 0.0f, 0.0f));
        EditCoverButton editCoverButton = new EditCoverButton(this.parentActivity, this, LocaleController.getString(C2369R.string.EditorSetCover), true);
        this.editCoverButton = editCoverButton;
        editCoverButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$24(view2);
            }
        });
        this.containerView.addView(this.editCoverButton, LayoutHelper.createFrame(-1, 32.0f, 87, 60.0f, 0.0f, 60.0f, 0.0f));
        PhotoViewerCoverEditor photoViewerCoverEditor = new PhotoViewerCoverEditor(this.parentActivity, resourcesProvider, this, this.blurManager);
        this.coverEditor = photoViewerCoverEditor;
        photoViewerCoverEditor.setVisibility(8);
        this.coverEditor.setAlpha(0.0f);
        this.coverEditor.setOnClose(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda23
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setParentActivity$25();
            }
        });
        this.containerView.addView(this.coverEditor, LayoutHelper.createFrame(-1, -1, Opcodes.DNEG));
        this.coverEditor.button.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$32(view2);
            }
        });
        this.coverEditor.setOnGalleryImage(new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda25
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$setParentActivity$37((MediaController.PhotoEntry) obj);
            }
        });
        ImageView imageView = new ImageView(this.parentActivity) { // from class: org.telegram.ui.PhotoViewer.29
            @Override // android.view.View
            public void setTranslationY(float f) {
                super.setTranslationY(f);
                if (PhotoViewer.this.muteHints != null) {
                    ArrayList arrayList = PhotoViewer.this.muteHints;
                    int size = arrayList.size();
                    int i8 = 0;
                    while (i8 < size) {
                        Object obj = arrayList.get(i8);
                        i8++;
                        ((HintView2) obj).setTranslationY(f);
                    }
                }
                if (PhotoViewer.this.editCoverButton != null) {
                    PhotoViewer.this.editCoverButton.setTranslationY(f);
                }
            }
        };
        this.muteButton = imageView;
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
        imageView.setScaleType(scaleType);
        ImageView imageView2 = this.muteButton;
        MuteDrawable muteDrawable = new MuteDrawable(this.parentActivity);
        this.muteDrawable = muteDrawable;
        imageView2.setImageDrawable(muteDrawable);
        this.muteButton.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_IN));
        this.muteButton.setBackground(new PhotoViewerBlurDrawable(this, this.blurManager, this.muteButton));
        ScaleStateListAnimator.apply(this.muteButton);
        this.containerView.addView(this.muteButton, LayoutHelper.createFrame(32, 32.0f, 83, 12.0f, 0.0f, 0.0f, 0.0f));
        this.muteButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda27
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$39(view2);
            }
        });
        CaptionPhotoViewer captionPhotoViewer2 = this.captionEdit;
        if (captionPhotoViewer2 != null && (editTextEmoji2 = captionPhotoViewer2.editText) != null) {
            editTextEmoji2.onDestroy();
        }
        CaptionPhotoViewer captionPhotoViewer3 = this.topCaptionEdit;
        if (captionPhotoViewer3 != null && (editTextEmoji = captionPhotoViewer3.editText) != null) {
            editTextEmoji.onDestroy();
        }
        Context context = this.containerView.getContext();
        FrameLayout frameLayout4 = this.windowView;
        FrameLayoutDrawer frameLayoutDrawer3 = this.containerView;
        CaptionPhotoViewer captionPhotoViewer4 = new CaptionPhotoViewer(context, frameLayout4, frameLayoutDrawer3, frameLayoutDrawer3, resourcesProvider, this.blurManager, new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.applyCaption();
            }
        }) { // from class: org.telegram.ui.PhotoViewer.30
            private final Path path = new Path();

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected boolean customBlur() {
                return true;
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected boolean isAtTop() {
                return false;
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected boolean ignoreTouches(float f, float f2) {
                return (this.keyboardShown || PhotoViewer.this.currentEditMode == 0) ? false : true;
            }

            @Override // org.telegram.p023ui.Components.CaptionPhotoViewer, org.telegram.p023ui.Stories.recorder.CaptionContainerView
            public void updateKeyboard(int i8) {
                super.updateKeyboard(i8);
                Bulletin visibleBulletin = Bulletin.getVisibleBulletin();
                if (visibleBulletin != null) {
                    visibleBulletin.updatePosition();
                }
                updateMoveCaptionButton();
                boolean z3 = false;
                if (PhotoViewer.this.bottomBulletinUnderCaption != null) {
                    PhotoViewer.this.bottomBulletinUnderCaption.animate().translationY(-Math.max(0, i8 - PhotoViewer.this.pickerView.getHeight())).setDuration(250L).setInterpolator(AdjustPanLayoutHelper.keyboardInterpolator).start();
                }
                PhotoViewer.this.actionBar.animate().alpha((!PhotoViewer.this.isActionBarVisible || (PhotoViewer.this.getCaptionView() == PhotoViewer.this.topCaptionEdit && PhotoViewer.this.topCaptionEdit.keyboardNotifier.keyboardVisible())) ? 0.0f : 1.0f).start();
                if (PhotoViewer.this.pickerView.getVisibility() == 0) {
                    PhotoViewer photoViewer = PhotoViewer.this;
                    if (photoViewer.isActionBarVisible && (PhotoViewer.this.getCaptionView() != PhotoViewer.this.topCaptionEdit || !PhotoViewer.this.topCaptionEdit.keyboardNotifier.keyboardVisible())) {
                        z3 = true;
                    }
                    photoViewer.toggleOnlyCheckImageView(z3);
                }
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            public void setText(CharSequence charSequence) {
                super.setText(charSequence);
                updateMoveCaptionButton();
            }

            private void updateMoveCaptionButton() {
                setShowMoveButtonVisible(PhotoViewer.this.placeProvider != null && PhotoViewer.this.placeProvider.canMoveCaptionAbove() && (PhotoViewer.this.captionEdit.keyboardNotifier.keyboardVisible() || !(PhotoViewer.this.isCurrentVideo || TextUtils.isEmpty(PhotoViewer.this.getCaptionView().getText()))), true);
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected void drawBlur(BlurringShader.StoryBlurDrawer storyBlurDrawer, Canvas canvas, RectF rectF, float f, boolean z3, float f2, float f3, boolean z4, float f4) {
                canvas.save();
                this.path.rewind();
                this.path.addRoundRect(rectF, f, f, Path.Direction.CW);
                canvas.clipPath(this.path);
                if (z4) {
                    canvas.translate(((-getX()) - PhotoViewer.this.captionEditContainer.getX()) + f2, ((-getY()) - PhotoViewer.this.captionEditContainer.getY()) + f3);
                } else {
                    canvas.translate(f2, f3);
                }
                PhotoViewer.this.drawCaptionBlur(canvas, storyBlurDrawer, Theme.multAlpha(z3 ? -8882056 : -14277082, f4), Theme.multAlpha(z4 ? z3 ? 0 : AndroidUtilities.DARK_STATUS_BAR_OVERLAY : 1140850688, f4), false, !z3, !z3 && z4);
                canvas.restore();
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected boolean captionLimitToast() {
                if (PhotoViewer.this.limitBulletin != null && Bulletin.getVisibleBulletin() == PhotoViewer.this.limitBulletin) {
                    return false;
                }
                PhotoViewer photoViewer = PhotoViewer.this;
                return photoViewer.showCaptionLimitBulletin(photoViewer.containerView);
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected void setupMentionContainer() {
                this.mentionContainer.getAdapter().setAllowStickers(false);
                this.mentionContainer.getAdapter().setAllowBots(false);
                this.mentionContainer.getAdapter().setAllowChats(false);
                this.mentionContainer.getAdapter().setSearchInDailogs(true);
                if (PhotoViewer.this.parentChatActivity != null) {
                    this.mentionContainer.getAdapter().setChatInfo(PhotoViewer.this.parentChatActivity.chatInfo);
                    this.mentionContainer.getAdapter().setNeedUsernames(PhotoViewer.this.parentChatActivity.currentChat != null);
                } else {
                    this.mentionContainer.getAdapter().setChatInfo(null);
                    this.mentionContainer.getAdapter().setNeedUsernames(false);
                }
                this.mentionContainer.getAdapter().setNeedBotContext(false);
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            public void updateMentionsLayoutPosition() {
                MentionsContainerView mentionsContainerView = this.mentionContainer;
                if (mentionsContainerView != null) {
                    mentionsContainerView.setTranslationY((-getEditTextHeight()) - this.keyboardNotifier.getKeyboardHeight());
                }
            }

            @Override // org.telegram.p023ui.Components.CaptionPhotoViewer, org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected void onUpdateShowKeyboard(float f) {
                super.onUpdateShowKeyboard(f);
                float f2 = 1.0f - f;
                PhotoViewer.this.muteButton.setAlpha((PhotoViewer.this.muteButton.getTag() != null ? 1 : 0) * f2);
                PhotoViewer.this.videoTimelineViewContainer.setAlpha(f2 * (PhotoViewer.this.videoTimelineViewContainer.getTag() != null ? 1 : 0));
            }

            @Override // android.view.View
            public void invalidate() {
                if (SharedConfig.photoViewerBlur && (PhotoViewer.this.animationInProgress == 1 || PhotoViewer.this.animationInProgress == 2 || PhotoViewer.this.animationInProgress == 3)) {
                    return;
                }
                super.invalidate();
            }

            @Override // org.telegram.p023ui.Components.CaptionPhotoViewer
            protected boolean showMoveButton() {
                return PhotoViewer.this.placeProvider != null && PhotoViewer.this.placeProvider.canMoveCaptionAbove();
            }

            @Override // org.telegram.p023ui.Components.CaptionPhotoViewer
            protected void onMoveButtonClick() {
                PhotoViewer.this.toggleCaptionAbove();
            }

            @Override // org.telegram.p023ui.Components.CaptionPhotoViewer
            protected void openedKeyboard() {
                expandMoveButton();
                if (PhotoViewer.this.topCaptionEdit != null) {
                    PhotoViewer.this.topCaptionEdit.expandMoveButton();
                }
            }
        };
        this.captionEdit = captionPhotoViewer4;
        captionPhotoViewer4.setOnTimerChange(new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda29
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$setParentActivity$40((Integer) obj);
            }
        });
        this.captionEdit.setAccount(this.currentAccount);
        this.captionEdit.setOnHeightUpdate(new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda30
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$setParentActivity$41((Integer) obj);
            }
        });
        this.captionEdit.setOnAddPhotoClick(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda31
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws NumberFormatException {
                this.f$0.lambda$setParentActivity$42(view2);
            }
        });
        Context context2 = this.activityContext;
        FrameLayout frameLayout5 = this.windowView;
        FrameLayoutDrawer frameLayoutDrawer4 = this.containerView;
        CaptionPhotoViewer captionPhotoViewer5 = new CaptionPhotoViewer(context2, frameLayout5, frameLayoutDrawer4, frameLayoutDrawer4, resourcesProvider, this.blurManager, new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.applyCaption();
            }
        }) { // from class: org.telegram.ui.PhotoViewer.31
            private final Path path = new Path();

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected boolean customBlur() {
                return true;
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected boolean isAtTop() {
                return true;
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected boolean ignoreTouches(float f, float f2) {
                return (this.keyboardShown || PhotoViewer.this.currentEditMode == 0) ? false : true;
            }

            @Override // org.telegram.p023ui.Components.CaptionPhotoViewer, org.telegram.p023ui.Stories.recorder.CaptionContainerView
            public void updateKeyboard(int i8) {
                super.updateKeyboard(i8);
                PhotoViewer.this.actionBar.animate().alpha((!PhotoViewer.this.isActionBarVisible || (PhotoViewer.this.getCaptionView() == PhotoViewer.this.topCaptionEdit && PhotoViewer.this.topCaptionEdit.keyboardNotifier.keyboardVisible())) ? 0.0f : 1.0f).start();
                if (PhotoViewer.this.pickerView.getVisibility() == 0) {
                    PhotoViewer photoViewer = PhotoViewer.this;
                    photoViewer.toggleOnlyCheckImageView(photoViewer.isActionBarVisible && !(PhotoViewer.this.getCaptionView() == PhotoViewer.this.topCaptionEdit && PhotoViewer.this.topCaptionEdit.keyboardNotifier.keyboardVisible()));
                }
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected void drawBlur(BlurringShader.StoryBlurDrawer storyBlurDrawer, Canvas canvas, RectF rectF, float f, boolean z3, float f2, float f3, boolean z4, float f4) {
                canvas.save();
                this.path.rewind();
                this.path.addRoundRect(rectF, f, f, Path.Direction.CW);
                canvas.clipPath(this.path);
                if (z4) {
                    canvas.translate(((-getX()) - PhotoViewer.this.topCaptionEditContainer.getX()) + f2, ((-getY()) - PhotoViewer.this.topCaptionEditContainer.getY()) + f3);
                } else {
                    canvas.translate(f2, f3);
                }
                PhotoViewer.this.drawCaptionBlur(canvas, storyBlurDrawer, Theme.multAlpha(z3 ? -8882056 : -14277082, f4), Theme.multAlpha(z4 ? z3 ? 0 : AndroidUtilities.DARK_STATUS_BAR_OVERLAY : 1140850688, f4), false, !z3, !z3 && z4);
                canvas.restore();
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected boolean captionLimitToast() {
                if (PhotoViewer.this.limitBulletin != null && Bulletin.getVisibleBulletin() == PhotoViewer.this.limitBulletin) {
                    return false;
                }
                PhotoViewer photoViewer = PhotoViewer.this;
                return photoViewer.showCaptionLimitBulletin(photoViewer.containerView);
            }

            @Override // android.view.View
            public void invalidate() {
                if (SharedConfig.photoViewerBlur && (PhotoViewer.this.animationInProgress == 1 || PhotoViewer.this.animationInProgress == 2 || PhotoViewer.this.animationInProgress == 3)) {
                    return;
                }
                super.invalidate();
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            protected void setupMentionContainer() {
                this.mentionContainer.setReversed(true);
                this.mentionContainer.getAdapter().setAllowStickers(false);
                this.mentionContainer.getAdapter().setAllowBots(false);
                this.mentionContainer.getAdapter().setAllowChats(false);
                this.mentionContainer.getAdapter().setSearchInDailogs(true);
                if (PhotoViewer.this.parentChatActivity != null) {
                    this.mentionContainer.getAdapter().setChatInfo(PhotoViewer.this.parentChatActivity.chatInfo);
                    this.mentionContainer.getAdapter().setNeedUsernames(PhotoViewer.this.parentChatActivity.currentChat != null);
                } else {
                    this.mentionContainer.getAdapter().setChatInfo(null);
                    this.mentionContainer.getAdapter().setNeedUsernames(false);
                }
                this.mentionContainer.getAdapter().setNeedBotContext(false);
                this.mentionContainer.setLayoutParams(LayoutHelper.createFrame(-1, -1, 51));
            }

            @Override // org.telegram.p023ui.Stories.recorder.CaptionContainerView
            public void updateMentionsLayoutPosition() {
                MentionsContainerView mentionsContainerView = this.mentionContainer;
                if (mentionsContainerView != null) {
                    mentionsContainerView.setTranslationY(getEditTextHeight());
                }
            }

            @Override // org.telegram.p023ui.Components.CaptionPhotoViewer
            protected boolean showMoveButton() {
                return PhotoViewer.this.placeProvider != null && PhotoViewer.this.placeProvider.canMoveCaptionAbove();
            }

            @Override // org.telegram.p023ui.Components.CaptionPhotoViewer
            protected void onMoveButtonClick() {
                PhotoViewer.this.toggleCaptionAbove();
            }

            @Override // org.telegram.p023ui.Components.CaptionPhotoViewer
            protected void openedKeyboard() {
                expandMoveButton();
                if (PhotoViewer.this.captionEdit != null) {
                    PhotoViewer.this.captionEdit.expandMoveButton();
                }
            }
        };
        this.topCaptionEdit = captionPhotoViewer5;
        captionPhotoViewer5.setShowMoveButtonVisible(true, false);
        this.topCaptionEdit.setOnTimerChange(new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda32
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$setParentActivity$43((Integer) obj);
            }
        });
        this.topCaptionEdit.setAccount(this.currentAccount);
        this.topCaptionEdit.setOnHeightUpdate(new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda33
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$setParentActivity$44((Integer) obj);
            }
        });
        this.topCaptionEdit.setOnAddPhotoClick(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda34
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws NumberFormatException {
                this.f$0.lambda$setParentActivity$45(view2);
            }
        });
        StickerMakerBackgroundView stickerMakerBackgroundView = new StickerMakerBackgroundView(this.activityContext) { // from class: org.telegram.ui.PhotoViewer.32
            @Override // android.view.View
            public void setAlpha(float f) {
                super.setAlpha(f);
                FrameLayout frameLayout6 = PhotoViewer.this.windowView;
                if (frameLayout6 != null) {
                    frameLayout6.invalidate();
                }
            }
        };
        this.stickerMakerBackgroundView = stickerMakerBackgroundView;
        stickerMakerBackgroundView.setVisibility(8);
        this.containerView.addView(this.stickerMakerBackgroundView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
        StickerMakerView stickerMakerView2 = new StickerMakerView(this.activityContext, resourcesProvider);
        this.stickerMakerView = stickerMakerView2;
        stickerMakerView2.setCurrentAccount(this.currentAccount);
        FrameLayoutDrawer frameLayoutDrawer5 = this.containerView;
        frameLayoutDrawer5.addView(this.stickerMakerView, frameLayoutDrawer5.indexOfChild(this.actionBar) - 1, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
        BlurButton blurButton = new BlurButton();
        this.cutOutBtn = blurButton;
        blurButton.setRad(18);
        this.cutOutBtn.wrapContentDynamic();
        this.stickerMakerView.setStickerCutOutBtn(this.cutOutBtn);
        this.cutOutBtn.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda35
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$51(view2);
            }
        });
        this.cutOutBtn.setCutOutState(false);
        this.containerView.addView(this.cutOutBtn, LayoutHelper.createFrame(-1, 36, 17));
        LinearLayout linearLayout2 = new LinearLayout(this.parentActivity);
        this.btnLayout = linearLayout2;
        linearLayout2.setOrientation(0);
        BlurButton blurButton2 = new BlurButton();
        this.eraseBtn = blurButton2;
        blurButton2.wrapContent();
        this.eraseBtn.setRad(18);
        this.eraseBtn.setEraseState(false);
        this.eraseBtn.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda37
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$52(view2);
            }
        });
        this.btnLayout.addView(this.eraseBtn, LayoutHelper.createLinear(-2, 36));
        this.btnLayout.addView(new Space(this.parentActivity), LayoutHelper.createLinear(12, -1));
        BlurButton blurButton3 = new BlurButton();
        this.restoreBtn = blurButton3;
        blurButton3.wrapContent();
        this.restoreBtn.setRad(18);
        this.restoreBtn.setRestoreState(false);
        this.restoreBtn.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda38
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$53(view2);
            }
        });
        this.btnLayout.addView(this.restoreBtn, LayoutHelper.createLinear(-2, 36));
        this.containerView.addView(this.btnLayout, LayoutHelper.createFrame(-2, 36, 17));
        BlurButton blurButton4 = new BlurButton();
        this.undoBtn = blurButton4;
        blurButton4.setUndoState(false);
        this.undoBtn.setRad(18);
        this.undoBtn.wrapContent();
        this.undoBtn.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda39
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$54(view2);
            }
        });
        this.containerView.addView(this.undoBtn, LayoutHelper.createFrame(-2, 36, 17));
        BlurButton blurButton5 = new BlurButton();
        this.outlineBtn = blurButton5;
        blurButton5.setOutlineState(false);
        this.outlineBtn.setRad(18);
        this.outlineBtn.wrapContent();
        this.outlineBtn.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda40
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$55(view2);
            }
        });
        this.containerView.addView(this.outlineBtn, LayoutHelper.createFrame(-2, 36, 17));
        showEditCaption(false, false);
        showStickerMode(false, false);
        FrameLayout frameLayout6 = new FrameLayout(this.parentActivity) { // from class: org.telegram.ui.PhotoViewer.33
            @Override // android.view.View
            public void setTranslationY(float f) {
                super.setTranslationY(f);
                PhotoViewer.this.invalidateBlur();
            }
        };
        this.captionEditContainer = frameLayout6;
        frameLayout6.addView(this.captionEdit, LayoutHelper.createFrame(-1, -1, 83));
        this.containerView.addView(this.captionEditContainer, LayoutHelper.createFrame(-1, -1.0f, 83, 0.0f, 8.0f, 0.0f, 0.0f));
        FrameLayout frameLayout7 = new FrameLayout(this.parentActivity);
        this.topCaptionEditContainer = frameLayout7;
        frameLayout7.addView(this.topCaptionEdit, LayoutHelper.createFrame(-1, -1, 51));
        this.containerView.addView(this.topCaptionEditContainer, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 8.0f, 0.0f, 0.0f));
        FrameLayout frameLayout8 = new FrameLayout(this.parentActivity);
        this.topBulletinUnderCaption = frameLayout8;
        this.containerView.addView(frameLayout8, LayoutHelper.createFrame(-1, 120.0f, 55, 0.0f, 0.0f, 0.0f, 0.0f));
        FrameLayout frameLayout9 = new FrameLayout(this.parentActivity);
        this.bottomBulletinUnderCaption = frameLayout9;
        this.containerView.addView(frameLayout9, LayoutHelper.createFrame(-1, 120.0f, 87, 0.0f, 0.0f, 0.0f, 0.0f));
        TextView textView7 = new TextView(this.parentActivity);
        this.videoAvatarTooltip = textView7;
        textView7.setSingleLine(true);
        this.videoAvatarTooltip.setVisibility(8);
        this.videoAvatarTooltip.setText(LocaleController.getString("ChooseCover", C2369R.string.ChooseCover));
        this.videoAvatarTooltip.setGravity(1);
        this.videoAvatarTooltip.setTextSize(1, 14.0f);
        this.videoAvatarTooltip.setTextColor(-7566196);
        this.containerView.addView(this.videoAvatarTooltip, LayoutHelper.createFrame(-1, -2.0f, 83, 0.0f, 8.0f, 0.0f, 0.0f));
        ChatActivityEnterView.SendButton sendButton = new ChatActivityEnterView.SendButton(this.parentActivity, C2369R.drawable.msg_input_send_mini, resourcesProvider) { // from class: org.telegram.ui.PhotoViewer.34
            @Override // org.telegram.ui.Components.ChatActivityEnterView.SendButton
            public boolean isInactive() {
                return false;
            }

            @Override // org.telegram.ui.Components.ChatActivityEnterView.SendButton
            public boolean isOpen() {
                return true;
            }

            @Override // org.telegram.ui.Components.ChatActivityEnterView.SendButton
            public boolean shouldDrawBackground() {
                return true;
            }

            @Override // org.telegram.ui.Components.ChatActivityEnterView.SendButton
            public boolean isInScheduleMode() {
                return super.isInScheduleMode();
            }

            @Override // org.telegram.ui.Components.ChatActivityEnterView.SendButton
            public int getFillColor() {
                return PhotoViewer.this.getThemedColor(Theme.key_chat_editMediaButton);
            }
        };
        this.pickerViewSendButton = sendButton;
        sendButton.setCircleSize(AndroidUtilities.m1146dp(48.0f));
        this.containerView.addView(this.pickerViewSendButton, LayoutHelper.createFrame(Opcodes.ISHL, 120.0f, 85, 0.0f, 0.0f, 14.0f, 2.33f));
        this.pickerViewSendButton.setContentDescription(LocaleController.getString("Send", C2369R.string.Send));
        ScaleStateListAnimator.apply(this.pickerViewSendButton);
        this.pickerViewSendButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda41
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws NumberFormatException {
                this.f$0.lambda$setParentActivity$56(view2);
            }
        });
        this.pickerViewSendButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda42
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                return this.f$0.lambda$setParentActivity$63(resourcesProvider, view2);
            }
        });
        LinearLayout linearLayout3 = new LinearLayout(this.parentActivity) { // from class: org.telegram.ui.PhotoViewer.36
            boolean ignoreLayout;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i8, int i9) {
                int childCount = getChildCount();
                int i10 = 0;
                for (int i11 = 0; i11 < childCount; i11++) {
                    if (getChildAt(i11).getVisibility() == 0) {
                        i10++;
                    }
                }
                int size = View.MeasureSpec.getSize(i8);
                int size2 = View.MeasureSpec.getSize(i9);
                if (i10 != 0) {
                    int iMin = Math.min(AndroidUtilities.m1146dp(70.0f), size / i10);
                    if (PhotoViewer.this.compressItem.getVisibility() == 0) {
                        this.ignoreLayout = true;
                        int iMax = Math.max(0, (iMin - AndroidUtilities.m1146dp(PhotoViewer.this.selectedCompression < 2 ? 48 : 64)) / 2);
                        PhotoViewer.this.compressItem.setPadding(iMax, 0, iMax, 0);
                        this.ignoreLayout = false;
                    }
                    for (int i12 = 0; i12 < childCount; i12++) {
                        View childAt = getChildAt(i12);
                        if (childAt.getVisibility() != 8) {
                            childAt.measure(View.MeasureSpec.makeMeasureSpec(iMin, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size2, TLObject.FLAG_30));
                        }
                    }
                    setMeasuredDimension(iMin * i10, size2);
                    return;
                }
                setMeasuredDimension(size, size2);
            }
        };
        this.itemsLayout = linearLayout3;
        linearLayout3.setOrientation(0);
        this.pickerView.addView(this.itemsLayout, LayoutHelper.createFrame(-2, 48.0f, 81, 0.0f, 0.0f, 70.0f, 0.0f));
        ImageView imageView3 = new ImageView(this.parentActivity);
        this.cropItem = imageView3;
        imageView3.setScaleType(scaleType);
        this.cropItem.setImageResource(C2369R.drawable.media_crop);
        this.cropItem.setBackgroundDrawable(Theme.createSelectorDrawable(1090519039));
        this.itemsLayout.addView(this.cropItem, LayoutHelper.createLinear(48, 48));
        this.cropItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda43
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$64(view2);
            }
        });
        this.cropItem.setContentDescription(LocaleController.getString("CropImage", C2369R.string.CropImage));
        ImageView imageView4 = new ImageView(this.parentActivity);
        this.rotateItem = imageView4;
        imageView4.setScaleType(scaleType);
        this.rotateItem.setImageResource(C2369R.drawable.msg_photo_rotate);
        this.rotateItem.setBackgroundDrawable(Theme.createSelectorDrawable(1090519039));
        this.itemsLayout.addView(this.rotateItem, LayoutHelper.createLinear(48, 48));
        this.rotateItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda44
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$65(view2);
            }
        });
        this.rotateItem.setContentDescription(LocaleController.getString("AccDescrRotate", C2369R.string.AccDescrRotate));
        ImageView imageView5 = new ImageView(this.parentActivity);
        this.mirrorItem = imageView5;
        imageView5.setScaleType(scaleType);
        this.mirrorItem.setImageResource(C2369R.drawable.media_flip);
        this.mirrorItem.setBackgroundDrawable(Theme.createSelectorDrawable(1090519039));
        this.itemsLayout.addView(this.mirrorItem, LayoutHelper.createLinear(48, 48));
        this.mirrorItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda45
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$66(view2);
            }
        });
        this.mirrorItem.setContentDescription(LocaleController.getString("AccDescrMirror", C2369R.string.AccDescrMirror));
        ImageView imageView6 = new ImageView(this.parentActivity);
        this.paintItem = imageView6;
        imageView6.setScaleType(scaleType);
        this.paintItem.setImageResource(C2369R.drawable.media_draw);
        this.paintItem.setBackgroundDrawable(Theme.createSelectorDrawable(1090519039));
        this.itemsLayout.addView(this.paintItem, LayoutHelper.createLinear(48, 48));
        this.paintItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda46
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$67(view2);
            }
        });
        this.paintItem.setContentDescription(LocaleController.getString("AccDescrPhotoEditor", C2369R.string.AccDescrPhotoEditor));
        VideoCompressButton videoCompressButton = new VideoCompressButton(this.parentActivity);
        this.compressItem = videoCompressButton;
        videoCompressButton.setTag(1);
        this.compressItem.setBackground(Theme.createSelectorDrawable(1090519039));
        this.selectedCompression = selectCompression();
        this.compressItem.setState(this.videoConvertSupported && this.compressionsCount > 1, this.muteVideo, Math.min(this.resultWidth, this.resultHeight));
        this.compressItem.setContentDescription(LocaleController.getString("AccDescrVideoQuality", C2369R.string.AccDescrVideoQuality));
        this.itemsLayout.addView(this.compressItem, LayoutHelper.createLinear(48, 48));
        this.compressItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda48
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws NumberFormatException {
                this.f$0.lambda$setParentActivity$68(parentActivity, view2);
            }
        });
        ImageView imageView7 = new ImageView(this.parentActivity);
        this.tuneItem = imageView7;
        imageView7.setScaleType(scaleType);
        this.tuneItem.setImageResource(C2369R.drawable.media_settings);
        this.tuneItem.setBackgroundDrawable(Theme.createSelectorDrawable(1090519039));
        this.itemsLayout.addView(this.tuneItem, LayoutHelper.createLinear(48, 48));
        this.tuneItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda49
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$69(view2);
            }
        });
        this.tuneItem.setContentDescription(LocaleController.getString("AccDescrPhotoAdjust", C2369R.string.AccDescrPhotoAdjust));
        PickerBottomLayoutViewer pickerBottomLayoutViewer2 = new PickerBottomLayoutViewer(this.activityContext);
        this.editorDoneLayout = pickerBottomLayoutViewer2;
        pickerBottomLayoutViewer2.setBackgroundColor(-872415232);
        this.editorDoneLayout.updateSelectedCount(0, false);
        this.editorDoneLayout.setVisibility(8);
        this.containerView.addView(this.editorDoneLayout, LayoutHelper.createFrame(-1, 48, 83));
        this.editorDoneLayout.cancelButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda50
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$71(view2);
            }
        });
        this.editorDoneLayout.doneButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda51
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$72(view2);
            }
        });
        TextView textView8 = new TextView(this.activityContext);
        this.resetButton = textView8;
        textView8.setClickable(false);
        this.resetButton.setVisibility(8);
        this.resetButton.setTextSize(1, 14.0f);
        this.resetButton.setTextColor(-1);
        this.resetButton.setGravity(17);
        this.resetButton.setBackgroundDrawable(Theme.createSelectorDrawable(-12763843, 0));
        this.resetButton.setPadding(AndroidUtilities.m1146dp(20.0f), 0, AndroidUtilities.m1146dp(20.0f), 0);
        this.resetButton.setText(LocaleController.getString(C2369R.string.CropReset).toUpperCase());
        this.resetButton.setTypeface(AndroidUtilities.bold());
        this.editorDoneLayout.addView(this.resetButton, LayoutHelper.createFrame(-2, -1, 49));
        this.resetButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda52
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$74(view2);
            }
        });
        GestureDetector2 gestureDetector2 = new GestureDetector2(this.containerView.getContext(), this);
        this.gestureDetector = gestureDetector2;
        gestureDetector2.setIsLongpressEnabled(false);
        setDoubleTapEnabled(true);
        ImageReceiver.ImageReceiverDelegate imageReceiverDelegate = new ImageReceiver.ImageReceiverDelegate() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda53
            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public final void didSetImage(ImageReceiver imageReceiver, boolean z3, boolean z4, boolean z5) {
                this.f$0.lambda$setParentActivity$75(imageReceiver, z3, z4, z5);
            }

            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public /* synthetic */ void didSetImageBitmap(int i8, String str, Drawable drawable) {
                ImageReceiver.ImageReceiverDelegate.CC.$default$didSetImageBitmap(this, i8, str, drawable);
            }

            @Override // org.telegram.messenger.ImageReceiver.ImageReceiverDelegate
            public /* synthetic */ void onAnimationReady(ImageReceiver imageReceiver) {
                ImageReceiver.ImageReceiverDelegate.CC.$default$onAnimationReady(this, imageReceiver);
            }
        };
        this.centerImage.setParentView(this.containerView);
        this.centerImage.setCrossfadeAlpha((byte) 2);
        this.centerImage.setInvalidateAll(true);
        this.centerImage.setDelegate(imageReceiverDelegate);
        this.leftImage.setParentView(this.containerView);
        this.leftImage.setCrossfadeAlpha((byte) 2);
        this.leftImage.setInvalidateAll(true);
        this.leftImage.setDelegate(imageReceiverDelegate);
        this.rightImage.setParentView(this.containerView);
        this.rightImage.setCrossfadeAlpha((byte) 2);
        this.rightImage.setInvalidateAll(true);
        this.rightImage.setDelegate(imageReceiverDelegate);
        int rotation = ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
        CheckBox checkBox = new CheckBox(this.containerView.getContext(), C2369R.drawable.selectphoto_large);
        this.checkImageView = checkBox;
        checkBox.setDrawBackground(true);
        this.checkImageView.setHasBorder(true);
        this.checkImageView.setSize(34);
        this.checkImageView.setCheckOffset(AndroidUtilities.m1146dp(1.0f));
        this.checkImageView.setColor(getThemedColor(i5), -1);
        this.checkImageView.setVisibility(8);
        this.containerView.addView(this.checkImageView, LayoutHelper.createFrame(34, 34.0f, 53, 0.0f, (rotation == 3 || rotation == 1) ? 61.0f : 71.0f, 11.0f, 0.0f));
        if (isStatusBarVisible()) {
            ((FrameLayout.LayoutParams) this.checkImageView.getLayoutParams()).topMargin += AndroidUtilities.statusBarHeight;
        }
        this.checkImageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda54
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$76(view2);
            }
        });
        CounterView counterView = new CounterView(this.parentActivity);
        this.photosCounterView = counterView;
        this.containerView.addView(counterView, LayoutHelper.createFrame(40, 40.0f, 53, 0.0f, (rotation == 3 || rotation == 1) ? 58.0f : 68.0f, 64.0f, 0.0f));
        if (isStatusBarVisible()) {
            ((FrameLayout.LayoutParams) this.photosCounterView.getLayoutParams()).topMargin += AndroidUtilities.statusBarHeight;
        }
        this.photosCounterView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda55
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$setParentActivity$77(view2);
            }
        });
        SelectedPhotosListView selectedPhotosListView = new SelectedPhotosListView(this.parentActivity);
        this.selectedPhotosListView = selectedPhotosListView;
        selectedPhotosListView.setVisibility(8);
        this.selectedPhotosListView.setAlpha(0.0f);
        this.selectedPhotosListView.setLayoutManager(new LinearLayoutManager(this.parentActivity, i, z2) { // from class: org.telegram.ui.PhotoViewer.37
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i8) {
                LinearSmoothScrollerEnd linearSmoothScrollerEnd = new LinearSmoothScrollerEnd(recyclerView.getContext()) { // from class: org.telegram.ui.PhotoViewer.37.1
                    @Override // androidx.recyclerview.widget.LinearSmoothScrollerEnd
                    protected int calculateTimeForDeceleration(int i9) {
                        return Math.max(Opcodes.GETFIELD, super.calculateTimeForDeceleration(i9));
                    }
                };
                linearSmoothScrollerEnd.setTargetPosition(i8);
                startSmoothScroll(linearSmoothScrollerEnd);
            }
        });
        SelectedPhotosListView selectedPhotosListView2 = this.selectedPhotosListView;
        ListAdapter listAdapter = new ListAdapter(this.parentActivity);
        this.selectedPhotosAdapter = listAdapter;
        selectedPhotosListView2.setAdapter(listAdapter);
        this.containerView.addView(this.selectedPhotosListView, LayoutHelper.createFrame(-1, 103, 51));
        this.selectedPhotosListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda56
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view2, int i8) throws Resources.NotFoundException, NumberFormatException {
                this.f$0.lambda$setParentActivity$78(view2, i8);
            }
        });
        UndoView undoView = new UndoView(this.activityContext, null, false, resourcesProvider);
        this.hintView = undoView;
        undoView.setAdditionalTranslationY(AndroidUtilities.m1146dp(112.0f));
        this.hintView.setColors(-115203550, -1);
        this.containerView.addView(this.hintView, LayoutHelper.createFrame(-1, -2.0f, 83, 8.0f, 0.0f, 8.0f, 8.0f));
        if (AndroidUtilities.isAccessibilityScreenReaderEnabled()) {
            View view2 = new View(this.activityContext);
            this.playButtonAccessibilityOverlay = view2;
            view2.setContentDescription(LocaleController.getString("AccActionPlay", C2369R.string.AccActionPlay));
            this.playButtonAccessibilityOverlay.setFocusable(true);
            this.containerView.addView(this.playButtonAccessibilityOverlay, LayoutHelper.createFrame(64, 64, 17));
        }
        this.doneButtonFullWidth.setBackground(Theme.AdaptiveRipple.filledRect(getThemedColor(i6), 6.0f));
        this.doneButtonFullWidth.setTextColor(getThemedColor(i7));
        TextSelectionHelper.SimpleTextSelectionHelper simpleTextSelectionHelper = this.textSelectionHelper;
        simpleTextSelectionHelper.allowScrollPrentRelative = true;
        simpleTextSelectionHelper.useMovingOffset = false;
        TextSelectionHelper.TextSelectionOverlay overlayView = simpleTextSelectionHelper.getOverlayView(this.windowView.getContext());
        if (overlayView != null) {
            AndroidUtilities.removeFromParent(overlayView);
            this.containerView.addView(overlayView);
        }
        this.textSelectionHelper.setParentView(this.containerView);
        this.textSelectionHelper.setInvalidateParent();
    }

    /* renamed from: org.telegram.ui.PhotoViewer$14 */
    /* loaded from: classes5.dex */
    class C578414 extends FrameLayout {
        C578414(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return PhotoViewer.this.isVisible && super.onInterceptTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return PhotoViewer.this.isVisible && PhotoViewer.this.onTouchEvent(motionEvent);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            keyEvent.getKeyCode();
            if (!PhotoViewer.this.muteVideo && PhotoViewer.this.sendPhotoType != 1 && PhotoViewer.this.isCurrentVideo && PhotoViewer.this.videoPlayer != null && keyEvent.getRepeatCount() == 0 && keyEvent.getAction() == 0 && (keyEvent.getKeyCode() == 24 || keyEvent.getKeyCode() == 25)) {
                PhotoViewer.this.videoPlayer.setVolume(1.0f);
            }
            return super.dispatchKeyEvent(keyEvent);
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x0023  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x003d  */
        @Override // android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean dispatchTouchEvent(android.view.MotionEvent r3) {
            /*
                r2 = this;
                org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.this
                boolean r0 = org.telegram.p023ui.PhotoViewer.m15515$$Nest$fgetvideoPlayerControlVisible(r0)
                if (r0 == 0) goto L46
                org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.this
                boolean r0 = org.telegram.p023ui.PhotoViewer.m15404$$Nest$fgetisPlaying(r0)
                if (r0 == 0) goto L46
                int r0 = r3.getActionMasked()
                if (r0 == 0) goto L3d
                r1 = 1
                if (r0 == r1) goto L23
                r1 = 3
                if (r0 == r1) goto L23
                r1 = 5
                if (r0 == r1) goto L3d
                r1 = 6
                if (r0 == r1) goto L23
                goto L46
            L23:
                org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.PhotoViewer.m15361$$Nest$fgetcurrentMessageObject(r0)
                if (r0 == 0) goto L37
                org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.this
                org.telegram.messenger.MessageObject r0 = org.telegram.p023ui.PhotoViewer.m15361$$Nest$fgetcurrentMessageObject(r0)
                boolean r0 = r0.isSponsored()
                if (r0 != 0) goto L46
            L37:
                org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.this
                org.telegram.p023ui.PhotoViewer.m15684$$Nest$mscheduleActionBarHide(r0)
                goto L46
            L3d:
                org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.this
                java.lang.Runnable r0 = org.telegram.p023ui.PhotoViewer.m15384$$Nest$fgethideActionBarRunnable(r0)
                org.telegram.messenger.AndroidUtilities.cancelRunOnUIThread(r0)
            L46:
                boolean r3 = super.dispatchTouchEvent(r3)
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.C578414.dispatchTouchEvent(android.view.MotionEvent):boolean");
        }

        @Override // android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view, long j) {
            try {
                return super.drawChild(canvas, view, j);
            } catch (Throwable unused) {
                return false;
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            if (!PhotoViewer.this.inBubbleMode && AndroidUtilities.incorrectDisplaySizeFix) {
                int i3 = AndroidUtilities.displaySize.y;
                if (size2 > i3) {
                    size2 = i3;
                }
                size2 += AndroidUtilities.statusBarHeight;
            }
            int i4 = size2 - PhotoViewer.this.insets.bottom;
            int paddingLeft = size - (getPaddingLeft() + getPaddingRight());
            int paddingBottom = i4 - getPaddingBottom();
            setMeasuredDimension(paddingLeft, paddingBottom);
            ViewGroup.LayoutParams layoutParams = PhotoViewer.this.animatingImageView.getLayoutParams();
            PhotoViewer.this.animatingImageView.measure(View.MeasureSpec.makeMeasureSpec(layoutParams.width, TLObject.FLAG_31), View.MeasureSpec.makeMeasureSpec(layoutParams.height, TLObject.FLAG_31));
            PhotoViewer.this.containerView.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(paddingBottom, TLObject.FLAG_30));
            PhotoViewer.this.navigationBar.measure(View.MeasureSpec.makeMeasureSpec(paddingLeft, TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(PhotoViewer.this.navigationBarHeight, TLObject.FLAG_30));
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            PhotoViewer.this.animatingImageView.layout(getPaddingLeft(), 0, getPaddingLeft() + PhotoViewer.this.animatingImageView.getMeasuredWidth(), PhotoViewer.this.animatingImageView.getMeasuredHeight());
            PhotoViewer.this.containerView.layout(getPaddingLeft(), 0, getPaddingLeft() + PhotoViewer.this.containerView.getMeasuredWidth(), PhotoViewer.this.containerView.getMeasuredHeight());
            PhotoViewer.this.navigationBar.layout(getPaddingLeft(), PhotoViewer.this.containerView.getMeasuredHeight(), PhotoViewer.this.navigationBar.getMeasuredWidth(), PhotoViewer.this.containerView.getMeasuredHeight() + PhotoViewer.this.navigationBar.getMeasuredHeight());
            PhotoViewer.this.wasLayout = true;
            if (z) {
                if (!PhotoViewer.this.dontResetZoomOnFirstLayout) {
                    PhotoViewer photoViewer = PhotoViewer.this;
                    photoViewer.scale = photoViewer.scale1();
                    PhotoViewer.this.translationX = 0.0f;
                    PhotoViewer.this.translationY = 0.0f;
                    PhotoViewer photoViewer2 = PhotoViewer.this;
                    photoViewer2.updateMinMax(photoViewer2.scale);
                }
                if (PhotoViewer.this.checkImageView != null) {
                    PhotoViewer.this.checkImageView.post(new Runnable() { // from class: org.telegram.ui.PhotoViewer$14$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onLayout$0();
                        }
                    });
                }
            }
            if (PhotoViewer.this.dontResetZoomOnFirstLayout) {
                PhotoViewer.this.setScaleToFill();
                PhotoViewer.this.dontResetZoomOnFirstLayout = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLayout$0() {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) PhotoViewer.this.checkImageView.getLayoutParams();
            ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
            int currentActionBarHeight = ((ActionBar.getCurrentActionBarHeight() - AndroidUtilities.m1146dp(34.0f)) / 2) + (PhotoViewer.this.isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0);
            if (currentActionBarHeight != layoutParams.topMargin) {
                layoutParams.topMargin = currentActionBarHeight;
                PhotoViewer.this.checkImageView.setLayoutParams(layoutParams);
            }
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) PhotoViewer.this.photosCounterView.getLayoutParams();
            int currentActionBarHeight2 = ((ActionBar.getCurrentActionBarHeight() - AndroidUtilities.m1146dp(40.0f)) / 2) + (PhotoViewer.this.isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0);
            if (layoutParams2.topMargin != currentActionBarHeight2) {
                layoutParams2.topMargin = currentActionBarHeight2;
                PhotoViewer.this.photosCounterView.setLayoutParams(layoutParams2);
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            PhotoViewer.this.centerImage.onAttachedToWindow();
            PhotoViewer.this.leftImage.onAttachedToWindow();
            PhotoViewer.this.rightImage.onAttachedToWindow();
            PhotoViewer.this.attachedToWindow = true;
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            PhotoViewer.this.centerImage.onDetachedFromWindow();
            PhotoViewer.this.leftImage.onDetachedFromWindow();
            PhotoViewer.this.rightImage.onDetachedFromWindow();
            PhotoViewer.this.attachedToWindow = false;
            PhotoViewer.this.wasLayout = false;
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) throws NumberFormatException {
            if (keyEvent != null && keyEvent.getKeyCode() == 4 && keyEvent.getAction() == 1) {
                if (PhotoViewer.this.textSelectionHelper.isInSelectionMode()) {
                    PhotoViewer.this.textSelectionHelper.clear();
                }
                if (PhotoViewer.this.isCaptionOpen()) {
                    PhotoViewer.this.closeCaptionEnter(true);
                    return false;
                }
                if (ContentPreviewViewer.getInstance().isVisible()) {
                    ContentPreviewViewer.getInstance().closeWithMenu();
                    return false;
                }
                PhotoViewer.getInstance().closePhoto(true, false);
                return true;
            }
            return super.dispatchKeyEventPreIme(keyEvent);
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0063  */
        @Override // android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected void onDraw(android.graphics.Canvas r17) {
            /*
                r16 = this;
                r0 = r16
                org.telegram.ui.PhotoViewer r1 = org.telegram.p023ui.PhotoViewer.this
                org.telegram.ui.Components.Paint.Views.StickerMakerBackgroundView r1 = org.telegram.p023ui.PhotoViewer.m15486$$Nest$fgetstickerMakerBackgroundView(r1)
                if (r1 == 0) goto L63
                org.telegram.ui.PhotoViewer r1 = org.telegram.p023ui.PhotoViewer.this
                org.telegram.ui.Components.Paint.Views.StickerMakerBackgroundView r1 = org.telegram.p023ui.PhotoViewer.m15486$$Nest$fgetstickerMakerBackgroundView(r1)
                int r1 = r1.getVisibility()
                if (r1 != 0) goto L63
                org.telegram.ui.PhotoViewer r1 = org.telegram.p023ui.PhotoViewer.this
                org.telegram.ui.Components.Paint.Views.StickerMakerBackgroundView r1 = org.telegram.p023ui.PhotoViewer.m15486$$Nest$fgetstickerMakerBackgroundView(r1)
                android.view.ViewParent r1 = r1.getParent()
                android.view.View r1 = (android.view.View) r1
                org.telegram.ui.PhotoViewer r2 = org.telegram.p023ui.PhotoViewer.this
                org.telegram.ui.Components.Paint.Views.StickerMakerBackgroundView r2 = org.telegram.p023ui.PhotoViewer.m15486$$Nest$fgetstickerMakerBackgroundView(r2)
                float r2 = r2.getAlpha()
                if (r1 == 0) goto L33
                float r1 = r1.getAlpha()
                goto L35
            L33:
                r1 = 1065353216(0x3f800000, float:1.0)
            L35:
                float r1 = java.lang.Math.min(r2, r1)
                r2 = 0
                int r2 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
                if (r2 <= 0) goto L63
                int r2 = r0.getWidth()
                float r6 = (float) r2
                int r2 = r0.getHeight()
                float r7 = (float) r2
                r2 = 1132396544(0x437f0000, float:255.0)
                float r1 = r1 * r2
                int r8 = (int) r1
                r9 = 31
                r4 = 0
                r5 = 0
                r3 = r17
                r3.saveLayerAlpha(r4, r5, r6, r7, r8, r9)
                org.telegram.ui.PhotoViewer r1 = org.telegram.p023ui.PhotoViewer.this
                org.telegram.ui.Components.Paint.Views.StickerMakerBackgroundView r1 = org.telegram.p023ui.PhotoViewer.m15486$$Nest$fgetstickerMakerBackgroundView(r1)
                r1.draw(r3)
                r3.restore()
                goto L65
            L63:
                r3 = r17
            L65:
                org.telegram.ui.PhotoViewer r1 = org.telegram.p023ui.PhotoViewer.this
                boolean r1 = org.telegram.p023ui.PhotoViewer.m15406$$Nest$fgetisVisible(r1)
                if (r1 == 0) goto La3
                org.telegram.ui.PhotoViewer r1 = org.telegram.p023ui.PhotoViewer.this
                android.graphics.Paint r1 = org.telegram.p023ui.PhotoViewer.m15332$$Nest$fgetblackPaint(r1)
                org.telegram.ui.PhotoViewer r2 = org.telegram.p023ui.PhotoViewer.this
                org.telegram.ui.PhotoViewer$BackgroundDrawable r2 = org.telegram.p023ui.PhotoViewer.m15330$$Nest$fgetbackgroundDrawable(r2)
                int r2 = r2.getAlpha()
                r1.setAlpha(r2)
                int r1 = r0.getMeasuredHeight()
                float r12 = (float) r1
                int r1 = r0.getMeasuredWidth()
                float r13 = (float) r1
                int r1 = r0.getMeasuredHeight()
                org.telegram.ui.PhotoViewer r2 = org.telegram.p023ui.PhotoViewer.this
                android.graphics.Rect r2 = org.telegram.p023ui.PhotoViewer.m15398$$Nest$fgetinsets(r2)
                int r2 = r2.bottom
                int r1 = r1 + r2
                float r14 = (float) r1
                org.telegram.ui.PhotoViewer r1 = org.telegram.p023ui.PhotoViewer.this
                android.graphics.Paint r15 = org.telegram.p023ui.PhotoViewer.m15332$$Nest$fgetblackPaint(r1)
                r11 = 0
                r10 = r3
                r10.drawRect(r11, r12, r13, r14, r15)
            La3:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.C578414.onDraw(android.graphics.Canvas):void");
        }

        @Override // android.view.View
        public void draw(Canvas canvas) {
            if (PhotoViewer.this.windowViewSkipRender) {
                return;
            }
            super.draw(canvas);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            UndoView undoView;
            super.dispatchDraw(canvas);
            if (PhotoViewer.this.parentChatActivity == null || (undoView = PhotoViewer.this.parentChatActivity.getUndoView()) == null || undoView.getVisibility() != 0) {
                return;
            }
            canvas.save();
            View view = (View) undoView.getParent();
            canvas.clipRect(view.getX(), view.getY(), view.getX() + view.getWidth(), view.getY() + view.getHeight());
            canvas.translate(undoView.getX(), undoView.getY());
            undoView.draw(canvas);
            canvas.restore();
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ WindowInsets lambda$setParentActivity$7(View view, WindowInsets windowInsets) {
        Rect rect = new Rect(this.insets);
        int i = Build.VERSION.SDK_INT;
        if (i >= 30) {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.displayCutout() | WindowInsetsCompat.Type.systemBars());
            this.insets.set(insets.left, insets.top, insets.right, insets.bottom);
        } else {
            this.insets.set(windowInsets.getStableInsetLeft(), windowInsets.getStableInsetTop(), windowInsets.getStableInsetRight(), windowInsets.getStableInsetBottom());
        }
        int i2 = this.insets.top;
        Activity activity = this.parentActivity;
        if ((activity instanceof LaunchActivity) && ((i2 != 0 || AndroidUtilities.isInMultiwindow) && !this.inBubbleMode && AndroidUtilities.statusBarHeight != i2)) {
            AndroidUtilities.statusBarHeight = i2;
            ((LaunchActivity) activity).drawerLayoutContainer.requestLayout();
        }
        if (!rect.equals(windowInsets)) {
            int i3 = this.animationInProgress;
            if (i3 == 1 || i3 == 3) {
                ClippingImageView clippingImageView = this.animatingImageView;
                clippingImageView.setTranslationX(clippingImageView.getTranslationX() - getLeftInset());
                this.animationValues[0][2] = this.animatingImageView.getTranslationX();
            }
            FrameLayout frameLayout = this.windowView;
            if (frameLayout != null) {
                frameLayout.requestLayout();
            }
        }
        View view2 = this.navigationBar;
        if (view2 != null) {
            this.navigationBarHeight = this.insets.bottom;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            int i4 = this.navigationBarHeight;
            marginLayoutParams.height = i4;
            marginLayoutParams.bottomMargin = (-i4) / 2;
            this.navigationBar.setLayoutParams(marginLayoutParams);
        }
        this.containerView.setPadding(windowInsets.getSystemWindowInsetLeft(), 0, windowInsets.getSystemWindowInsetRight(), 0);
        if (this.actionBar != null) {
            AndroidUtilities.cancelRunOnUIThread(this.updateContainerFlagsRunnable);
            if (this.isVisible && this.animationInProgress == 0) {
                AndroidUtilities.runOnUIThread(this.updateContainerFlagsRunnable, 200L);
            }
        }
        if (i >= 30) {
            return WindowInsets.CONSUMED;
        }
        return windowInsets.consumeSystemWindowInsets();
    }

    /* renamed from: org.telegram.ui.PhotoViewer$17 */
    /* loaded from: classes5.dex */
    class C578717 extends ActionBar.ActionBarMenuOnItemClick {
        final /* synthetic */ Theme.ResourcesProvider val$resourcesProvider;

        C578717(Theme.ResourcesProvider resourcesProvider) {
            this.val$resourcesProvider = resourcesProvider;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$0(boolean z, Uri uri) {
            BulletinFactory.createSaveToGalleryBulletin(PhotoViewer.this.containerView, z, -115203550, -1).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$2(AlertDialog alertDialog, int i) {
            File pathToMessage;
            if (PhotoViewer.this.currentMessageObject == null) {
                return;
            }
            if ((MessageObject.getMedia(PhotoViewer.this.currentMessageObject.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && MessageObject.getMedia(PhotoViewer.this.currentMessageObject.messageOwner).webpage != null && MessageObject.getMedia(PhotoViewer.this.currentMessageObject.messageOwner).webpage.document == null) {
                PhotoViewer photoViewer = PhotoViewer.this;
                pathToMessage = FileLoader.getInstance(PhotoViewer.this.currentAccount).getPathToAttach(photoViewer.getFileLocation(photoViewer.currentIndex, null), true);
            } else {
                pathToMessage = FileLoader.getInstance(PhotoViewer.this.currentAccount).getPathToMessage(PhotoViewer.this.currentMessageObject.messageOwner);
            }
            final boolean zIsVideo = PhotoViewer.this.currentMessageObject.isVideo();
            if (pathToMessage != null && pathToMessage.exists()) {
                MediaController.saveFile(pathToMessage.toString(), PhotoViewer.this.parentActivity, zIsVideo ? 1 : 0, null, null, new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$17$$ExternalSyntheticLambda18
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$onItemClick$1(zIsVideo, (Uri) obj);
                    }
                });
                return;
            }
            PhotoViewer.this.showDownloadAlert();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$1(boolean z, Uri uri) {
            BulletinFactory.createSaveToGalleryBulletin(PhotoViewer.this.containerView, z, -115203550, -1).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$5(final boolean z, ArrayList arrayList, AlertDialog alertDialog, int i) {
            File pathToMessage;
            final int[] iArr = new int[1];
            final int[] iArr2 = new int[1];
            final Runnable runnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer$17$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onItemClick$3(iArr2, iArr, z);
                }
            };
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                MessageObject messageObject = (MessageObject) arrayList.get(i2);
                if (messageObject != null) {
                    if ((MessageObject.getMedia(messageObject.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && MessageObject.getMedia(messageObject.messageOwner).webpage != null && MessageObject.getMedia(messageObject.messageOwner).webpage.document == null) {
                        FileLoader fileLoader = FileLoader.getInstance(PhotoViewer.this.currentAccount);
                        PhotoViewer photoViewer = PhotoViewer.this;
                        pathToMessage = fileLoader.getPathToAttach(photoViewer.getFileLocation(photoViewer.currentIndex, null), true);
                    } else {
                        pathToMessage = FileLoader.getInstance(PhotoViewer.this.currentAccount).getPathToMessage(messageObject.messageOwner);
                    }
                    boolean zIsVideo = messageObject.isVideo();
                    if (pathToMessage != null && pathToMessage.exists()) {
                        iArr[0] = iArr[0] + 1;
                        MediaController.saveFile(pathToMessage.toString(), PhotoViewer.this.parentActivity, zIsVideo ? 1 : 0, null, null, new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$17$$ExternalSyntheticLambda20
                            @Override // org.telegram.messenger.Utilities.Callback
                            public final void run(Object obj) {
                                AndroidUtilities.runOnUIThread(runnable);
                            }
                        });
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$3(int[] iArr, int[] iArr2, boolean z) {
            int i = iArr[0] + 1;
            iArr[0] = i;
            if (i == iArr2[0]) {
                BulletinFactory.createSaveToGalleryBulletin(PhotoViewer.this.containerView, iArr2[0], z, -115203550, -1).show();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$7(AlertDialog alertDialog, int i) throws Resources.NotFoundException {
            ArrayList arrayList = new ArrayList(1);
            arrayList.add(PhotoViewer.this.currentMessageObject);
            PhotoViewer.this.showShareAlert(arrayList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$8(ArrayList arrayList, AlertDialog alertDialog, int i) throws Resources.NotFoundException {
            PhotoViewer.this.showShareAlert(arrayList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ boolean lambda$onItemClick$10(ArrayList arrayList, ChatActivity chatActivity, DialogsActivity dialogsActivity, ArrayList arrayList2, CharSequence charSequence, boolean z, boolean z2, int i, TopicsFragment topicsFragment) {
            UndoView undoView;
            long j;
            if (arrayList2.size() > 1 || ((MessagesStorage.TopicKey) arrayList2.get(0)).dialogId == UserConfig.getInstance(PhotoViewer.this.currentAccount).getClientUserId() || charSequence != null) {
                ArrayList arrayList3 = arrayList;
                int i2 = 0;
                while (i2 < arrayList2.size()) {
                    long j2 = ((MessagesStorage.TopicKey) arrayList2.get(i2)).dialogId;
                    if (charSequence != null) {
                        j = j2;
                        SendMessagesHelper.getInstance(PhotoViewer.this.currentAccount).sendMessage(SendMessagesHelper.SendMessageParams.m1191of(charSequence.toString(), j, null, null, null, true, null, null, null, true, 0, 0, null, false));
                    } else {
                        j = j2;
                    }
                    SendMessagesHelper.getInstance(PhotoViewer.this.currentAccount).sendMessage(arrayList3, j, false, false, true, 0, 0L);
                    i2++;
                    arrayList3 = arrayList;
                }
                dialogsActivity.lambda$onBackPressed$371();
                if (chatActivity != null && (undoView = chatActivity.getUndoView()) != null) {
                    if (arrayList2.size() == 1) {
                        undoView.showWithAction(((MessagesStorage.TopicKey) arrayList2.get(0)).dialogId, 53, Integer.valueOf(arrayList.size()));
                    } else {
                        undoView.showWithAction(0L, 53, Integer.valueOf(arrayList.size()), Integer.valueOf(arrayList2.size()), (Runnable) null, (Runnable) null);
                    }
                }
            } else {
                MessagesStorage.TopicKey topicKey = (MessagesStorage.TopicKey) arrayList2.get(0);
                long j3 = topicKey.dialogId;
                Bundle bundle = new Bundle();
                bundle.putBoolean("scrollToTopOnResume", true);
                if (DialogObject.isEncryptedDialog(j3)) {
                    bundle.putInt("enc_id", DialogObject.getEncryptedChatId(j3));
                } else if (DialogObject.isUserDialog(j3)) {
                    bundle.putLong("user_id", j3);
                } else {
                    bundle.putLong("chat_id", -j3);
                }
                ChatActivity chatActivity2 = new ChatActivity(bundle);
                if (topicKey.topicId != 0) {
                    ForumUtilities.applyTopic(chatActivity2, topicKey);
                }
                if (((LaunchActivity) PhotoViewer.this.parentActivity).presentFragment(chatActivity2, true, false)) {
                    chatActivity2.showFieldPanelForForward(true, arrayList);
                } else {
                    dialogsActivity.lambda$onBackPressed$371();
                }
            }
            return true;
        }

        public static /* synthetic */ void $r8$lambda$2oAfWMOmEqYhZHQKrygs6NAoPzc(boolean[] zArr, View view) {
            boolean z = !zArr[0];
            zArr[0] = z;
            ((CheckBoxCell) view).setChecked(z, true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$12(boolean[] zArr, AlertDialog alertDialog, int i) throws Resources.NotFoundException, NumberFormatException {
            ArrayList<Long> arrayList;
            TLRPC.EncryptedChat encryptedChat;
            if (!PhotoViewer.this.placeProvider.onDeletePhoto(PhotoViewer.this.currentIndex)) {
                PhotoViewer.this.closePhoto(false, false);
                return;
            }
            if (!PhotoViewer.this.imagesArr.isEmpty()) {
                if (PhotoViewer.this.currentIndex < 0 || PhotoViewer.this.currentIndex >= PhotoViewer.this.imagesArr.size()) {
                    return;
                }
                MessageObject messageObject = (MessageObject) PhotoViewer.this.imagesArr.get(PhotoViewer.this.currentIndex);
                if (messageObject.isSent()) {
                    PhotoViewer.this.closePhoto(false, false);
                    ArrayList<Integer> arrayList2 = new ArrayList<>();
                    if (PhotoViewer.this.slideshowMessageId != 0) {
                        arrayList2.add(Integer.valueOf(PhotoViewer.this.slideshowMessageId));
                    } else {
                        arrayList2.add(Integer.valueOf(messageObject.getId()));
                    }
                    if (!DialogObject.isEncryptedDialog(messageObject.getDialogId()) || messageObject.messageOwner.random_id == 0) {
                        arrayList = null;
                        encryptedChat = null;
                    } else {
                        ArrayList<Long> arrayList3 = new ArrayList<>();
                        arrayList3.add(Long.valueOf(messageObject.messageOwner.random_id));
                        encryptedChat = MessagesController.getInstance(PhotoViewer.this.currentAccount).getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(messageObject.getDialogId())));
                        arrayList = arrayList3;
                    }
                    MessagesController.getInstance(PhotoViewer.this.currentAccount).deleteMessages(arrayList2, arrayList, encryptedChat, messageObject.getDialogId(), messageObject.getQuickReplyId(), zArr[0], messageObject.getChatMode());
                    return;
                }
                return;
            }
            if (!PhotoViewer.this.avatarsArr.isEmpty()) {
                if (PhotoViewer.this.currentIndex < 0 || PhotoViewer.this.currentIndex >= PhotoViewer.this.avatarsArr.size()) {
                    return;
                }
                TLRPC.Message message = (TLRPC.Message) PhotoViewer.this.imagesArrMessages.get(PhotoViewer.this.currentIndex);
                if (message != null) {
                    ArrayList<Integer> arrayList4 = new ArrayList<>();
                    arrayList4.add(Integer.valueOf(message.f1597id));
                    MessagesController.getInstance(PhotoViewer.this.currentAccount).deleteMessages(arrayList4, null, null, MessageObject.getDialogId(message), message.quick_reply_shortcut_id, true, 0);
                    NotificationCenter.getInstance(PhotoViewer.this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.reloadDialogPhotos, new Object[0]);
                }
                if (PhotoViewer.this.isCurrentAvatarSet()) {
                    if (PhotoViewer.this.avatarsDialogId > 0) {
                        MessagesController.getInstance(PhotoViewer.this.currentAccount).deleteUserPhoto(null);
                    } else {
                        MessagesController.getInstance(PhotoViewer.this.currentAccount).changeChatAvatar(-PhotoViewer.this.avatarsDialogId, null, null, null, null, 0.0d, null, null, null, null);
                    }
                    PhotoViewer.this.closePhoto(false, false);
                    return;
                }
                TLRPC.Photo photo = (TLRPC.Photo) PhotoViewer.this.avatarsArr.get(PhotoViewer.this.currentIndex);
                if (photo == null) {
                    return;
                }
                TLRPC.TL_inputPhoto tL_inputPhoto = new TLRPC.TL_inputPhoto();
                tL_inputPhoto.f1595id = photo.f1603id;
                tL_inputPhoto.access_hash = photo.access_hash;
                byte[] bArr = photo.file_reference;
                tL_inputPhoto.file_reference = bArr;
                if (bArr == null) {
                    tL_inputPhoto.file_reference = new byte[0];
                }
                if (PhotoViewer.this.avatarsDialogId > 0) {
                    MessagesController.getInstance(PhotoViewer.this.currentAccount).deleteUserPhoto(tL_inputPhoto);
                }
                MessagesStorage.getInstance(PhotoViewer.this.currentAccount).clearUserPhoto(PhotoViewer.this.avatarsDialogId, photo.f1603id);
                PhotoViewer.this.imagesArrLocations.remove(PhotoViewer.this.currentIndex);
                PhotoViewer.this.imagesArrLocationsSizes.remove(PhotoViewer.this.currentIndex);
                PhotoViewer.this.imagesArrLocationsVideo.remove(PhotoViewer.this.currentIndex);
                PhotoViewer.this.imagesArrMessages.remove(PhotoViewer.this.currentIndex);
                PhotoViewer.this.avatarsArr.remove(PhotoViewer.this.currentIndex);
                if (PhotoViewer.this.imagesArrLocations.isEmpty()) {
                    PhotoViewer.this.closePhoto(false, false);
                } else {
                    int size = PhotoViewer.this.currentIndex;
                    if (size >= PhotoViewer.this.avatarsArr.size()) {
                        size = PhotoViewer.this.avatarsArr.size() - 1;
                    }
                    PhotoViewer.this.currentIndex = -1;
                    PhotoViewer.this.setImageIndex(size);
                }
                if (message == null) {
                    NotificationCenter.getInstance(PhotoViewer.this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.reloadDialogPhotos, new Object[0]);
                    return;
                }
                return;
            }
            if (PhotoViewer.this.secureDocuments.isEmpty() || PhotoViewer.this.placeProvider == null) {
                return;
            }
            PhotoViewer.this.secureDocuments.remove(PhotoViewer.this.currentIndex);
            PhotoViewer.this.placeProvider.deleteImageAtIndex(PhotoViewer.this.currentIndex);
            if (PhotoViewer.this.secureDocuments.isEmpty()) {
                PhotoViewer.this.closePhoto(false, false);
                return;
            }
            int size2 = PhotoViewer.this.currentIndex;
            if (size2 >= PhotoViewer.this.secureDocuments.size()) {
                size2 = PhotoViewer.this.secureDocuments.size() - 1;
            }
            PhotoViewer.this.currentIndex = -1;
            PhotoViewer.this.setImageIndex(size2);
        }

        /* JADX WARN: Removed duplicated region for block: B:161:0x03f3  */
        /* JADX WARN: Removed duplicated region for block: B:454:0x0dc9  */
        /* JADX WARN: Type inference failed for: r7v17 */
        /* JADX WARN: Type inference failed for: r7v19 */
        /* JADX WARN: Type inference failed for: r7v20, types: [boolean, int] */
        /* JADX WARN: Type inference failed for: r7v21 */
        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onItemClick(int r27) throws android.content.res.Resources.NotFoundException, java.lang.NumberFormatException {
            /*
                Method dump skipped, instructions count: 4163
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.C578717.onItemClick(int):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$13(Theme.ResourcesProvider resourcesProvider) {
            BulletinFactory.m1266of(PhotoViewer.this.containerView, null).createCopyBulletin(LocaleController.getString(C2369R.string.FrameCopied), resourcesProvider).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$15(final UserConfig userConfig, final TLRPC.Photo photo, final TLObject tLObject, TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$17$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onItemClick$14(tLObject, userConfig, photo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$14(TLObject tLObject, UserConfig userConfig, TLRPC.Photo photo) {
            if (tLObject instanceof TLRPC.TL_photos_photo) {
                TLRPC.TL_photos_photo tL_photos_photo = (TLRPC.TL_photos_photo) tLObject;
                MessagesController.getInstance(PhotoViewer.this.currentAccount).putUsers(tL_photos_photo.users, false);
                TLRPC.User user = MessagesController.getInstance(PhotoViewer.this.currentAccount).getUser(Long.valueOf(userConfig.clientUserId));
                if (tL_photos_photo.photo instanceof TLRPC.TL_photo) {
                    int iIndexOf = PhotoViewer.this.avatarsArr.indexOf(photo);
                    if (iIndexOf >= 0) {
                        PhotoViewer.this.avatarsArr.set(iIndexOf, tL_photos_photo.photo);
                    }
                    if (user != null) {
                        user.photo.photo_id = tL_photos_photo.photo.f1603id;
                        userConfig.setCurrentUser(user);
                        userConfig.saveConfig(true);
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$16() {
            if (PhotoViewer.this.menuItem == null) {
                return;
            }
            PhotoViewer.this.menuItem.hideSubItem(14);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$17(Theme.ResourcesProvider resourcesProvider) {
            BulletinFactory.m1266of(PhotoViewer.this.containerView, null).createCopyBulletin(LocaleController.getString(C2369R.string.PhotoCopied), resourcesProvider).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$18() {
            PhotoViewer.this.menuItem.hideSubItem(19);
            PhotoViewer.this.menuItem.showSubItem(20);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$19() {
            PhotoViewer.this.menuItem.showSubItem(19);
            PhotoViewer.this.menuItem.hideSubItem(20);
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public boolean canOpenMenu() {
            if (PhotoViewer.this.currentMessageObject != null || PhotoViewer.this.currentSecureDocument != null) {
                return true;
            }
            if (PhotoViewer.this.currentFileLocationVideo == null) {
                return PhotoViewer.this.pageBlocksAdapter != null;
            }
            File pathToAttach = FileLoader.getInstance(PhotoViewer.this.currentAccount).getPathToAttach(PhotoViewer.getFileLocation(PhotoViewer.this.currentFileLocationVideo), PhotoViewer.getFileLocationExt(PhotoViewer.this.currentFileLocationVideo), !(Objects.equals(PhotoViewer.getFileLocationExt(PhotoViewer.this.currentFileLocationVideo), "mp4") || PhotoViewer.this.avatarsDialogId == 0) || PhotoViewer.this.isEvent);
            return pathToAttach.exists() || new File(FileLoader.getDirectory(4), pathToAttach.getName()).exists() || FileLoader.getInstance(PhotoViewer.this.currentAccount).getPathToAttach(PhotoViewer.getFileLocation(PhotoViewer.this.currentFileLocationVideo), PhotoViewer.getFileLocationExt(PhotoViewer.this.currentFileLocationVideo), false).exists();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$8(Float f, Boolean bool) {
        chooseSpeed((f.floatValue() * 2.8f) + 0.2f, bool.booleanValue(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$9(Boolean bool) {
        checkProgress(0, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$10(View view) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.isSponsored()) {
            openAdsMenu();
        } else if (this.actionBar.actionBarMenuOnItemClick.canOpenMenu()) {
            this.menuItem.toggleSubMenu();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$11(Boolean bool) {
        checkProgress(0, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$14(final Theme.ResourcesProvider resourcesProvider, final String str, Boolean bool) {
        int i;
        int i2;
        int i3 = this.switchingToIndex;
        if (i3 >= 0) {
            if (i3 < this.imagesArr.size() || this.switchingToIndex < this.avatarsArr.size()) {
                File photo = (isCurrentAvatarSet() || (((i = this.switchingToIndex) < 0 || i >= this.imagesArr.size() || this.imagesArr.get(this.switchingToIndex) == null || !((MessageObject) this.imagesArr.get(this.switchingToIndex)).isVideo()) && ((i2 = this.switchingToIndex) < 0 || i2 >= this.avatarsArr.size() || this.avatarsArr.get(this.switchingToIndex) == null || ((TLRPC.Photo) this.avatarsArr.get(this.switchingToIndex)).video_sizes.isEmpty()))) ? getPhoto() : getFrameAsFile();
                if (bool.booleanValue()) {
                    this.menuItem.toggleSubMenu();
                    SystemUtils.shareImageWithGoogleLens(this.parentActivity, FileProvider.getUriForFile(this.activityContext, ApplicationLoader.getApplicationId() + ".provider", photo));
                    return;
                }
                final AlertDialog alertDialog = new AlertDialog(this.activityContext, 3, resourcesProvider);
                ChatUtils.uploadImageTemporary(photo, new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda112
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$setParentActivity$13(alertDialog, str, resourcesProvider, (String) obj);
                    }
                });
                alertDialog.setCanCancel(false);
                alertDialog.show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$13(final AlertDialog alertDialog, final String str, final Theme.ResourcesProvider resourcesProvider, final String str2) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda141
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setParentActivity$12(alertDialog, str2, str, resourcesProvider);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$12(AlertDialog alertDialog, String str, String str2, Theme.ResourcesProvider resourcesProvider) {
        if (alertDialog.isShowing()) {
            try {
                alertDialog.dismiss();
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
        this.menuItem.toggleSubMenu();
        if (str != null) {
            Browser.openUrl(this.parentActivity, str2 + str);
            return;
        }
        BulletinFactory.m1266of(this.containerView, resourcesProvider).createSimpleBulletin(C2369R.raw.error, LocaleController.getString(C2369R.string.PhotoUploadFailed)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$17(MessageObject messageObject, VideoPlayer.Quality quality) {
        TLRPC.Document downloadDocument;
        if (quality == null || (downloadDocument = quality.getDownloadDocument()) == null) {
            return;
        }
        File pathToAttach = FileLoader.getInstance(this.currentAccount).getPathToAttach(downloadDocument, null, false, true);
        if (pathToAttach == null || !pathToAttach.exists()) {
            pathToAttach = FileLoader.getInstance(this.currentAccount).getPathToAttach(downloadDocument, null, true, true);
        }
        if (pathToAttach != null && pathToAttach.exists()) {
            MediaController.saveFile(pathToAttach.toString(), this.parentActivity, 1, null, null, new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda162
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$setParentActivity$15((Uri) obj);
                }
            });
        } else {
            ArrayList arrayList = new ArrayList();
            messageObject.qualityToSave = downloadDocument;
            arrayList.add(messageObject);
            MediaController.saveFilesFromMessages(this.parentActivity, AccountInstance.getInstance(this.currentAccount), arrayList, new MessagesStorage.IntCallback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda163
                @Override // org.telegram.messenger.MessagesStorage.IntCallback
                public final void run(int i) {
                    this.f$0.lambda$setParentActivity$16(i);
                }
            });
        }
        this.menuItem.toggleSubMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$15(Uri uri) {
        BulletinFactory.createSaveToGalleryBulletin(this.containerView, true, -115203550, -1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$16(int i) {
        FrameLayoutDrawer frameLayoutDrawer;
        if (this.parentActivity == null || (frameLayoutDrawer = this.containerView) == null || i <= 0) {
            return;
        }
        BulletinFactory.createSaveToGalleryBulletin(frameLayoutDrawer, true, -115203550, -1).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$18(View view) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && messageObject.hasVideoQualities() && this.chooseDownloadQualityLayout.update(this.currentMessageObject)) {
            this.galleryButton.openSwipeBack();
            return;
        }
        ActionBar actionBar = this.actionBar;
        if (actionBar == null || actionBar.getActionBarMenuOnItemClick() == null) {
            return;
        }
        this.actionBar.getActionBarMenuOnItemClick().onItemClick(2);
        this.menuItem.toggleSubMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$19(View view) {
        Activity activity = this.parentActivity;
        if (activity == null) {
            return;
        }
        this.wasRotated = false;
        this.fullscreenedByButton = 1;
        if (this.prevOrientation == -10) {
            this.prevOrientation = activity.getRequestedOrientation();
        }
        if (((WindowManager) this.parentActivity.getSystemService("window")).getDefaultDisplay().getRotation() == 3) {
            this.parentActivity.setRequestedOrientation(8);
        } else {
            this.parentActivity.setRequestedOrientation(0);
        }
        toggleActionBar(false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ View lambda$setParentActivity$20() {
        return new CaptionTextView(this.activityContext, this.captionScrollView, this.textSelectionHelper, new Utilities.Callback2() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda90
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) throws NumberFormatException {
                this.f$0.onLinkClick((ClickableSpan) obj, (TextView) obj2);
            }
        }, new Utilities.Callback3() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda91
            @Override // org.telegram.messenger.Utilities.Callback3
            public final void run(Object obj, Object obj2, Object obj3) {
                this.f$0.onLinkLongPress((ClickableSpan) obj, (TextView) obj2, (Runnable) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$21(View view) throws NumberFormatException {
        this.selectedCompression = this.previousCompression;
        didChangedCompressionLevel(false);
        showQualityView(false);
        requestVideoPreview(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$22(View view) throws NumberFormatException {
        Object obj = this.imagesArrLocals.get(this.currentIndex);
        if (obj instanceof MediaController.MediaEditState) {
            ((MediaController.MediaEditState) obj).editedInfo = getCurrentVideoEditedInfo();
        }
        showQualityView(false);
        requestVideoPreview(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$23(View view) throws NumberFormatException {
        sendPressed(false, 0, 0);
    }

    /* renamed from: org.telegram.ui.PhotoViewer$28 */
    /* loaded from: classes5.dex */
    class C579928 implements VideoTimelinePlayView.VideoTimelineViewDelegate {
        private int seekTo;
        private Runnable seekToRunnable;
        private boolean wasPlaying;

        C579928() {
        }

        @Override // org.telegram.ui.Components.VideoTimelinePlayView.VideoTimelineViewDelegate
        public void onLeftProgressChanged(float f) {
            if (PhotoViewer.this.videoPlayer == null) {
                return;
            }
            if (PhotoViewer.this.videoPlayer.isPlaying()) {
                PhotoViewer.this.manuallyPaused = false;
                PhotoViewer.this.videoPlayer.pause();
                PhotoViewer.this.containerView.invalidate();
            }
            updateAvatarStartTime(1);
            seekTo(f);
            PhotoViewer.this.videoPlayerSeekbar.setProgress(0.0f);
            PhotoViewer.this.videoTimelineView.setProgress(f);
            PhotoViewer.this.updateVideoInfo();
        }

        @Override // org.telegram.ui.Components.VideoTimelinePlayView.VideoTimelineViewDelegate
        public void onRightProgressChanged(float f) {
            if (PhotoViewer.this.videoPlayer == null) {
                return;
            }
            if (PhotoViewer.this.videoPlayer.isPlaying()) {
                PhotoViewer.this.manuallyPaused = false;
                PhotoViewer.this.videoPlayer.pause();
                PhotoViewer.this.containerView.invalidate();
            }
            updateAvatarStartTime(2);
            seekTo(f);
            PhotoViewer.this.videoPlayerSeekbar.setProgress(1.0f);
            PhotoViewer.this.videoTimelineView.setProgress(f);
            PhotoViewer.this.updateVideoInfo();
        }

        @Override // org.telegram.ui.Components.VideoTimelinePlayView.VideoTimelineViewDelegate
        public void onPlayProgressChanged(float f) {
            if (PhotoViewer.this.videoPlayer == null) {
                return;
            }
            if (PhotoViewer.this.sendPhotoType == 1) {
                updateAvatarStartTime(0);
            }
            seekTo(f);
        }

        private void seekTo(float f) {
            this.seekTo = (int) (PhotoViewer.this.videoDuration * f);
            if (SharedConfig.getDevicePerformanceClass() == 2) {
                PhotoViewer.this.seekVideoOrWebTo(this.seekTo);
                if (PhotoViewer.this.sendPhotoType == 1) {
                    PhotoViewer.this.needCaptureFrameReadyAtTime = this.seekTo;
                    if (PhotoViewer.this.captureFrameReadyAtTime != PhotoViewer.this.needCaptureFrameReadyAtTime) {
                        PhotoViewer.this.captureFrameReadyAtTime = -1L;
                    }
                }
                this.seekToRunnable = null;
                return;
            }
            if (this.seekToRunnable == null) {
                Runnable runnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer$28$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$seekTo$0();
                    }
                };
                this.seekToRunnable = runnable;
                AndroidUtilities.runOnUIThread(runnable, 100L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$seekTo$0() {
            PhotoViewer.this.seekVideoOrWebTo(this.seekTo);
            if (PhotoViewer.this.sendPhotoType == 1) {
                PhotoViewer.this.needCaptureFrameReadyAtTime = this.seekTo;
                if (PhotoViewer.this.captureFrameReadyAtTime != PhotoViewer.this.needCaptureFrameReadyAtTime) {
                    PhotoViewer.this.captureFrameReadyAtTime = -1L;
                }
            }
            this.seekToRunnable = null;
        }

        private void updateAvatarStartTime(int i) {
            if (PhotoViewer.this.sendPhotoType != 1) {
                return;
            }
            if (i != 0) {
                if (PhotoViewer.this.photoCropView != null) {
                    if (PhotoViewer.this.videoTimelineView.getLeftProgress() > PhotoViewer.this.avatarStartProgress || PhotoViewer.this.videoTimelineView.getRightProgress() < PhotoViewer.this.avatarStartProgress) {
                        PhotoViewer.this.photoCropView.setVideoThumbVisible(false);
                        if (i == 1) {
                            PhotoViewer photoViewer = PhotoViewer.this;
                            photoViewer.avatarStartTime = (long) (photoViewer.videoDuration * 1000.0f * PhotoViewer.this.videoTimelineView.getLeftProgress());
                        } else {
                            PhotoViewer photoViewer2 = PhotoViewer.this;
                            photoViewer2.avatarStartTime = (long) (photoViewer2.videoDuration * 1000.0f * PhotoViewer.this.videoTimelineView.getRightProgress());
                        }
                        PhotoViewer.this.captureFrameAtTime = -1L;
                        return;
                    }
                    return;
                }
                return;
            }
            PhotoViewer photoViewer3 = PhotoViewer.this;
            photoViewer3.avatarStartProgress = photoViewer3.videoTimelineView.getProgress();
            PhotoViewer photoViewer4 = PhotoViewer.this;
            photoViewer4.avatarStartTime = (long) (photoViewer4.videoDuration * 1000.0f * PhotoViewer.this.avatarStartProgress);
        }

        @Override // org.telegram.ui.Components.VideoTimelinePlayView.VideoTimelineViewDelegate
        public void didStartDragging(int i) {
            if (i == VideoTimelinePlayView.TYPE_PROGRESS) {
                PhotoViewer.this.cancelVideoPlayRunnable();
                if (PhotoViewer.this.sendPhotoType == 1) {
                    PhotoViewer.this.cancelFlashAnimations();
                    PhotoViewer.this.captureFrameAtTime = -1L;
                }
                boolean zIsVideoPlaying = PhotoViewer.this.isVideoPlaying();
                this.wasPlaying = zIsVideoPlaying;
                if (zIsVideoPlaying) {
                    PhotoViewer.this.manuallyPaused = false;
                    PhotoViewer.this.pauseVideoOrWeb();
                    PhotoViewer.this.containerView.invalidate();
                }
            }
        }

        @Override // org.telegram.ui.Components.VideoTimelinePlayView.VideoTimelineViewDelegate
        public void didStopDragging(int i) {
            Runnable runnable = this.seekToRunnable;
            if (runnable != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable);
                this.seekToRunnable.run();
            }
            PhotoViewer.this.cancelVideoPlayRunnable();
            if (PhotoViewer.this.sendPhotoType == 1 && PhotoViewer.this.flashView != null && i == VideoTimelinePlayView.TYPE_PROGRESS) {
                PhotoViewer.this.cancelFlashAnimations();
                PhotoViewer photoViewer = PhotoViewer.this;
                photoViewer.captureFrameAtTime = photoViewer.avatarStartTime;
                if (PhotoViewer.this.captureFrameReadyAtTime == this.seekTo) {
                    PhotoViewer.this.captureCurrentFrame();
                    return;
                }
                return;
            }
            if (PhotoViewer.this.sendPhotoType == 1 || this.wasPlaying) {
                PhotoViewer.this.manuallyPaused = false;
                PhotoViewer.this.playVideoOrWeb();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$24(View view) {
        switchToEditMode(5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$25() {
        switchToEditMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$32(View view) {
        int i;
        if (this.coverEditor.button.isLoading() || (i = this.currentIndex) < 0 || i >= this.imagesArrLocals.size() || !(this.imagesArrLocals.get(this.currentIndex) instanceof MediaController.PhotoEntry)) {
            return;
        }
        final MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) this.imagesArrLocals.get(this.currentIndex);
        final long time = this.coverEditor.getTime();
        final String tempFileAbsolutePath = getTempFileAbsolutePath();
        this.coverEditor.button.setLoading(true);
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda132
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setParentActivity$31(tempFileAbsolutePath, photoEntry, time);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$31(final String str, final MediaController.PhotoEntry photoEntry, final long j) {
        final Utilities.Callback callback = new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda142
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) throws IOException {
                this.f$0.lambda$setParentActivity$29(str, photoEntry, j, (Bitmap) obj);
            }
        };
        if (this.usedSurfaceView) {
            final Bitmap bitmapCreateBitmap = Bitmap.createBitmap(this.videoSurfaceView.getWidth(), this.videoSurfaceView.getHeight(), Bitmap.Config.ARGB_8888);
            AndroidUtilities.getBitmapFromSurface(this.videoSurfaceView, bitmapCreateBitmap, new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda143
                @Override // java.lang.Runnable
                public final void run() {
                    callback.run(bitmapCreateBitmap);
                }
            });
            return;
        }
        TextureView textureView = this.videoTextureView;
        Bitmap bitmap = textureView.getBitmap(textureView.getWidth(), this.videoTextureView.getHeight());
        if (bitmap == null) {
            callback.run(SendMessagesHelper.createVideoThumbnailAtTime(photoEntry.path, j, null, true));
        } else {
            callback.run(bitmap);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$29(final String str, final MediaController.PhotoEntry photoEntry, final long j, Bitmap bitmap) throws IOException {
        if (bitmap == null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda172
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setParentActivity$26();
                }
            });
            return;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
            final Bitmap bitmapCreateBitmap = Bitmap.createBitmap(AndroidUtilities.m1146dp(26.0f), AndroidUtilities.m1146dp(26.0f), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Paint paint = new Paint(3);
            canvas.translate(bitmapCreateBitmap.getWidth() / 2.0f, bitmapCreateBitmap.getHeight() / 2.0f);
            float fMax = Math.max(bitmapCreateBitmap.getWidth() / bitmap.getWidth(), bitmapCreateBitmap.getHeight() / bitmap.getHeight());
            canvas.scale(fMax, fMax);
            canvas.drawBitmap(bitmap, (-bitmap.getWidth()) / 2.0f, (-bitmap.getHeight()) / 2.0f, paint);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda174
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setParentActivity$28(photoEntry, j, str, bitmapCreateBitmap);
                }
            });
        } catch (Exception e) {
            FileLog.m1160e(e);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda173
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setParentActivity$27();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$26() {
        this.coverEditor.button.setLoading(false);
        switchToEditMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$27() {
        this.coverEditor.button.setLoading(false);
        switchToEditMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$28(MediaController.PhotoEntry photoEntry, long j, String str, Bitmap bitmap) {
        if (photoEntry.coverPath != null) {
            try {
                new File(photoEntry.coverPath).delete();
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
        photoEntry.coverSavedPosition = j;
        photoEntry.coverPath = str;
        photoEntry.coverPhoto = null;
        photoEntry.coverPhotoParentObject = null;
        this.coverEditor.button.setLoading(false);
        PhotoViewerProvider photoViewerProvider = this.placeProvider;
        if (photoViewerProvider != null) {
            photoViewerProvider.updatePhotoAtIndex(this.currentIndex);
        }
        EditCoverButton editCoverButton = this.editCoverButton;
        if (editCoverButton != null) {
            editCoverButton.lambda$setImage$0(bitmap);
        }
        switchToEditMode(0);
        if (this.checkImageView.isChecked()) {
            return;
        }
        this.checkImageView.callOnClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$37(final MediaController.PhotoEntry photoEntry) {
        int i;
        if (this.coverEditor.button.isLoading() || (i = this.currentIndex) < 0 || i >= this.imagesArrLocals.size() || !(this.imagesArrLocals.get(this.currentIndex) instanceof MediaController.PhotoEntry)) {
            return;
        }
        this.coverEditor.closeGallery();
        this.coverEditor.button.setLoading(true);
        final MediaController.PhotoEntry photoEntry2 = (MediaController.PhotoEntry) this.imagesArrLocals.get(this.currentIndex);
        final String tempFileAbsolutePath = getTempFileAbsolutePath();
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda106
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                this.f$0.lambda$setParentActivity$36(photoEntry, photoEntry2, tempFileAbsolutePath);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$36(MediaController.PhotoEntry photoEntry, final MediaController.PhotoEntry photoEntry2, final String str) throws IOException {
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(photoEntry.path);
        if (bitmapDecodeFile == null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda159
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setParentActivity$33();
                }
            });
            return;
        }
        int[] iArr = new int[11];
        AnimatedFileDrawable.getVideoInfo(photoEntry2.path, iArr);
        int iMax = Math.max(iArr[1], photoEntry2.width);
        int iMax2 = Math.max(iArr[2], photoEntry2.height);
        if ((iArr[8] / 90) % 2 == 1) {
            iMax2 = iMax;
            iMax = iMax2;
        }
        float f = iMax;
        float f2 = iMax2;
        float fMax = Math.max(bitmapDecodeFile.getWidth() / f, bitmapDecodeFile.getHeight() / f2);
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) (f * fMax), (int) (f2 * fMax), config);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint(3);
        canvas.translate(bitmapCreateBitmap.getWidth() / 2, bitmapCreateBitmap.getHeight() / 2);
        float fMax2 = Math.max(bitmapCreateBitmap.getWidth() / bitmapDecodeFile.getWidth(), bitmapCreateBitmap.getHeight() / bitmapDecodeFile.getHeight());
        canvas.scale(fMax2, fMax2);
        canvas.drawBitmap(bitmapDecodeFile, (-bitmapDecodeFile.getWidth()) / 2, (-bitmapDecodeFile.getHeight()) / 2, paint);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
            fileOutputStream.close();
            final Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(AndroidUtilities.m1146dp(26.0f), AndroidUtilities.m1146dp(26.0f), config);
            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
            canvas2.translate(bitmapCreateBitmap2.getWidth() / 2.0f, bitmapCreateBitmap2.getHeight() / 2.0f);
            float fMax3 = Math.max(bitmapCreateBitmap2.getWidth() / bitmapCreateBitmap.getWidth(), bitmapCreateBitmap2.getHeight() / bitmapCreateBitmap.getHeight());
            canvas2.scale(fMax3, fMax3);
            canvas2.drawBitmap(bitmapCreateBitmap, (-bitmapCreateBitmap.getWidth()) / 2.0f, (-bitmapCreateBitmap.getHeight()) / 2.0f, paint);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda161
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setParentActivity$35(photoEntry2, str, bitmapCreateBitmap2);
                }
            });
        } catch (Exception e) {
            FileLog.m1160e(e);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda160
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setParentActivity$34();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$33() {
        this.coverEditor.button.setLoading(false);
        switchToEditMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$34() {
        this.coverEditor.button.setLoading(false);
        switchToEditMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$35(MediaController.PhotoEntry photoEntry, String str, Bitmap bitmap) {
        if (photoEntry.coverPath != null) {
            try {
                new File(photoEntry.coverPath).delete();
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
        photoEntry.coverSavedPosition = -1L;
        photoEntry.coverPath = str;
        photoEntry.coverPhoto = null;
        photoEntry.coverPhotoParentObject = null;
        this.coverEditor.button.setLoading(false);
        PhotoViewerProvider photoViewerProvider = this.placeProvider;
        if (photoViewerProvider != null) {
            photoViewerProvider.updatePhotoAtIndex(this.currentIndex);
        }
        EditCoverButton editCoverButton = this.editCoverButton;
        if (editCoverButton != null) {
            editCoverButton.lambda$setImage$0(bitmap);
        }
        switchToEditMode(0);
        if (this.checkImageView.isChecked()) {
            return;
        }
        this.checkImageView.callOnClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$39(View view) {
        if (isCaptionOpen()) {
            return;
        }
        this.muteVideo = !this.muteVideo;
        ArrayList arrayList = this.muteHints;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((HintView2) obj).hide();
            }
        }
        if (this.muteVideo) {
            final HintView2 hintView2 = new HintView2(this.parentActivity, 3);
            hintView2.setMultilineText(true);
            hintView2.setText(LocaleController.getString(C2369R.string.EditorMuteHint));
            hintView2.setMaxWidthPx(HintView2.cutInFancyHalf(hintView2.getText(), hintView2.getTextPaint()));
            hintView2.setPadding(AndroidUtilities.m1146dp(6.0f), 0, AndroidUtilities.m1146dp(6.0f), 0);
            hintView2.setJoint(0.0f, 22.0f);
            hintView2.setOnHiddenListener(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda84
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setParentActivity$38(hintView2);
                }
            });
            if (this.muteHints == null) {
                this.muteHints = new ArrayList();
            }
            this.containerView.addView(hintView2, LayoutHelper.createFrame(-1, DataTypes.EMPTY, 83));
            this.muteHints.add(hintView2);
            hintView2.show();
        }
        updateMuteButton();
        updateVideoInfo();
        if (this.muteVideo && !this.checkImageView.isChecked()) {
            this.checkImageView.callOnClick();
            return;
        }
        Object obj2 = this.imagesArrLocals.get(this.currentIndex);
        if (obj2 instanceof MediaController.MediaEditState) {
            ((MediaController.MediaEditState) obj2).editedInfo = getCurrentVideoEditedInfo();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$38(HintView2 hintView2) {
        ArrayList arrayList = this.muteHints;
        if (arrayList != null) {
            arrayList.remove(hintView2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$40(Integer num) {
        Object obj = this.imagesArrLocals.get(this.currentIndex);
        if (obj instanceof MediaController.PhotoEntry) {
            ((MediaController.PhotoEntry) obj).ttl = num.intValue();
        } else if (obj instanceof MediaController.SearchImage) {
            ((MediaController.SearchImage) obj).ttl = num.intValue();
        }
        if (num.intValue() != 0 && !this.placeProvider.isPhotoChecked(this.currentIndex)) {
            setPhotoChecked();
        }
        this.topCaptionEdit.setTimer(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$41(Integer num) {
        FrameLayout frameLayout = this.videoTimelineViewContainer;
        if (frameLayout != null && frameLayout.getVisibility() != 8) {
            this.videoTimelineViewContainer.setTranslationY(this.pickerView.getTranslationY() - (Math.max(0, this.captionEdit.getEditTextHeight() - AndroidUtilities.m1146dp(46.0f)) * this.captionEdit.getAlpha()));
        }
        this.muteButton.setTranslationY((-Math.max(0, num.intValue() - AndroidUtilities.m1146dp(46.0f))) * this.captionEdit.getAlpha());
        this.editCoverButton.setTranslationY((-Math.max(0, num.intValue() - AndroidUtilities.m1146dp(46.0f))) * this.captionEdit.getAlpha());
        MentionsContainerView mentionsContainerView = this.captionEdit.mentionContainer;
        if (mentionsContainerView != null) {
            mentionsContainerView.setTranslationY(-num.intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$42(View view) throws NumberFormatException {
        if (this.placeProvider == null || isCaptionOpen()) {
            return;
        }
        this.placeProvider.needAddMorePhotos();
        closePhoto(true, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$43(Integer num) {
        Object obj = this.imagesArrLocals.get(this.currentIndex);
        if (obj instanceof MediaController.PhotoEntry) {
            ((MediaController.PhotoEntry) obj).ttl = num.intValue();
        } else if (obj instanceof MediaController.SearchImage) {
            ((MediaController.SearchImage) obj).ttl = num.intValue();
        }
        if (num.intValue() != 0 && !this.placeProvider.isPhotoChecked(this.currentIndex)) {
            setPhotoChecked();
        }
        this.captionEdit.setTimer(num.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$44(Integer num) {
        MentionsContainerView mentionsContainerView = this.topCaptionEdit.mentionContainer;
        if (mentionsContainerView != null) {
            mentionsContainerView.setTranslationY(num.intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$45(View view) throws NumberFormatException {
        if (this.placeProvider == null || isCaptionOpen()) {
            return;
        }
        this.placeProvider.needAddMorePhotos();
        closePhoto(true, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$51(View view) {
        int i;
        if (this.stickerEmpty || this.cutOutBtn.isLoading() || this.cutOutBtn.isUndoCutState() || (i = this.currentIndex) < 0 || i >= this.imagesArrLocals.size() || this.stickerMakerView.isThanosInProgress) {
            return;
        }
        final MediaController.MediaEditState mediaEditState = (MediaController.MediaEditState) this.imagesArrLocals.get(this.currentIndex);
        final boolean z = !TextUtils.isEmpty(mediaEditState.filterPath);
        if (this.cutOutBtn.isCutOutState()) {
            this.cutOutBtn.setCancelState(true);
            this.stickerMakerView.enableClippingMode(new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda77
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$setParentActivity$50(z, mediaEditState, (StickerMakerView.SegmentedObject) obj);
                }
            });
            this.containerView.invalidate();
        } else {
            if (this.cutOutBtn.isCancelState()) {
                this.cutOutBtn.setCutOutState(true);
                showEditStickerMode(false, true);
                this.stickerMakerView.disableClippingMode();
                this.containerView.invalidate();
                return;
            }
            this.stickerMakerView.resetPaths();
            this.stickerMakerView.getThanosEffect();
            this.stickerMakerView.setSegmentedState(false, null);
            this.centerImage.setImageBitmap(this.stickerMakerView.getSourceBitmap(z));
            this.cutOutBtn.setCutOutState(true);
            showEditStickerMode(false, true);
            applyCurrentEditMode();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00fb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$setParentActivity$50(boolean r17, org.telegram.messenger.MediaController.MediaEditState r18, org.telegram.ui.Components.Paint.Views.StickerMakerView.SegmentedObject r19) {
        /*
            Method dump skipped, instructions count: 474
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.lambda$setParentActivity$50(boolean, org.telegram.messenger.MediaController$MediaEditState, org.telegram.ui.Components.Paint.Views.StickerMakerView$SegmentedObject):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$47() {
        this.stickerMakerView.isThanosInProgress = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$48(Bitmap bitmap, Runnable runnable) {
        this.centerImage.setImageBitmap(bitmap);
        this.cutOutBtn.setUndoCutState(true);
        showStickerMode(true, true);
        AndroidUtilities.cancelRunOnUIThread(runnable);
        AndroidUtilities.runOnUIThread(runnable, 800L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$52(View view) {
        this.eraseBtn.setActive(true, true);
        this.restoreBtn.setActive(false, true);
        StickerMakerView stickerMakerView = this.stickerMakerView;
        if (stickerMakerView != null) {
            stickerMakerView.setOutlineVisible(false);
        }
        this.maskPaintViewEraser = true;
        MaskPaintView maskPaintView = this.maskPaintView;
        if (maskPaintView != null) {
            maskPaintView.setEraser(true);
        }
        switchToEditMode(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$53(View view) {
        this.eraseBtn.setActive(false, true);
        this.restoreBtn.setActive(true, true);
        StickerMakerView stickerMakerView = this.stickerMakerView;
        if (stickerMakerView != null) {
            stickerMakerView.setOutlineVisible(false);
        }
        this.maskPaintViewEraser = false;
        MaskPaintView maskPaintView = this.maskPaintView;
        if (maskPaintView != null) {
            maskPaintView.setEraser(false);
        }
        switchToEditMode(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$54(View view) {
        MaskPaintView maskPaintView = this.maskPaintView;
        if (maskPaintView == null || !maskPaintView.undo()) {
            switchToEditMode(0);
            this.stickerMakerView.resetPaths();
            boolean z = !TextUtils.isEmpty(((MediaController.MediaEditState) this.imagesArrLocals.get(this.currentIndex)).filterPath);
            StickerMakerView stickerMakerView = this.stickerMakerView;
            if (stickerMakerView != null && !stickerMakerView.empty) {
                stickerMakerView.setSegmentedState(false, null);
            }
            this.centerImage.setImageBitmap(this.stickerMakerView.getSourceBitmap(z));
            StickerMakerView stickerMakerView2 = this.stickerMakerView;
            if (stickerMakerView2 == null || !stickerMakerView2.empty) {
                this.cutOutBtn.setCutOutState(true);
            }
            showStickerMode(true, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$55(View view) {
        if (this.stickerMakerView != null) {
            this.outlineBtn.setActive(!r3.isActive(), true);
            this.stickerMakerView.setOutlineVisible((!this.outlineBtn.isActive() || this.eraseBtn.isActive() || this.restoreBtn.isActive()) ? false : true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$56(View view) throws NumberFormatException {
        MessageObject messageObject;
        MessageSuggestionParams messageSuggestionParamsM1170of;
        ChatActivity chatActivity = this.parentChatActivity;
        if (chatActivity != null && (messageObject = chatActivity.editingMessageObject) != null && messageObject.needResendWhenEdit() && !ChatObject.canManageMonoForum(this.currentAccount, this.parentChatActivity.editingMessageObject.getDialogId())) {
            if (this.parentFragment == null || (messageSuggestionParamsM1170of = this.parentChatActivity.messageSuggestionParams) == null) {
                messageSuggestionParamsM1170of = MessageSuggestionParams.m1170of(this.parentChatActivity.editingMessageObject.messageOwner.suggested_post);
            }
            if (!StarsController.isEnoughAmount(this.currentAccount, messageSuggestionParamsM1170of.amount)) {
                ChatActivity chatActivity2 = this.parentChatActivity;
                if (chatActivity2 != null) {
                    chatActivity2.showSuggestionOfferForEditMessage(messageSuggestionParamsM1170of);
                    return;
                }
                return;
            }
        }
        if (this.captionEdit.isCaptionOverLimit()) {
            AnimatedTextView animatedTextView = this.captionEdit.limitTextView;
            float f = -this.shiftDp;
            this.shiftDp = f;
            AndroidUtilities.shakeViewSpring(animatedTextView, f);
            BotWebViewVibrationEffect.APP_ERROR.vibrate();
            if (MessagesController.getInstance(this.currentAccount).premiumFeaturesBlocked() || MessagesController.getInstance(this.currentAccount).captionLengthLimitPremium <= this.captionEdit.getCodePointCount()) {
                return;
            }
            showCaptionLimitBulletin(this.containerView);
            return;
        }
        ChatActivity chatActivity3 = this.parentChatActivity;
        if (chatActivity3 != null && chatActivity3.isInScheduleMode() && !this.parentChatActivity.isEditingMessageMedia()) {
            showScheduleDatePickerDialog();
        } else {
            sendPressed(true, 0, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:100:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0184  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ boolean lambda$setParentActivity$63(final org.telegram.ui.ActionBar.Theme.ResourcesProvider r19, android.view.View r20) {
        /*
            Method dump skipped, instructions count: 750
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.lambda$setParentActivity$63(org.telegram.ui.ActionBar.Theme$ResourcesProvider, android.view.View):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$setParentActivity$57(View view, MotionEvent motionEvent) {
        ActionBarPopupWindow actionBarPopupWindow;
        if (motionEvent.getActionMasked() != 0 || (actionBarPopupWindow = this.sendPopupWindow) == null || !actionBarPopupWindow.isShowing()) {
            return false;
        }
        view.getHitRect(this.hitRect);
        if (this.hitRect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
            return false;
        }
        this.sendPopupWindow.dismiss();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$58(KeyEvent keyEvent) {
        ActionBarPopupWindow actionBarPopupWindow;
        if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && (actionBarPopupWindow = this.sendPopupWindow) != null && actionBarPopupWindow.isShowing()) {
            this.sendPopupWindow.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$60(final ActionBarMenuSubItem actionBarMenuSubItem, View view) {
        PopupUtils.showDialog(TranslatorUtils.getLanguageTitles(), LocaleController.getString(C2369R.string.Language), TranslatorUtils.getLanguageIndexByIso(ExteraConfig.targetLang), this.parentActivity, new PopupUtils.OnItemClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda144
            @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
            public final void onClick(int i) {
                PhotoViewer.$r8$lambda$LgQHnUqSrBOaRAXXuyIfO2rQVyw(actionBarMenuSubItem, i);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$LgQHnUqSrBOaRAXXuyIfO2rQVyw(ActionBarMenuSubItem actionBarMenuSubItem, int i) {
        SharedPreferences.Editor editor = ExteraConfig.editor;
        String langCodeByIndex = TranslatorUtils.getLangCodeByIndex(i);
        ExteraConfig.targetLang = langCodeByIndex;
        editor.putString("targetLang", langCodeByIndex).apply();
        actionBarMenuSubItem.setSubtext(ExteraConfig.getCurrentLangName());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$62(int i, final Theme.ResourcesProvider resourcesProvider, View view) throws NumberFormatException {
        PhotoViewerProvider photoViewerProvider;
        ActionBarPopupWindow actionBarPopupWindow = this.sendPopupWindow;
        if (actionBarPopupWindow != null && actionBarPopupWindow.isShowing()) {
            this.sendPopupWindow.dismiss();
        }
        if (i == 0) {
            showScheduleDatePickerDialog();
            return;
        }
        if (i == 1) {
            sendPressed(false, 0, 0);
            return;
        }
        if (i == 2) {
            replacePressed();
            return;
        }
        if (i == 3) {
            sendPressed(true, 0, 0);
            return;
        }
        if (i == 4) {
            sendPressed(true, 0, 0, false, true, false);
            return;
        }
        if (i == 5) {
            TranslatorUtils.translate(this.captionEdit.getText(), new TranslatorUtils.TranslateCallback() { // from class: org.telegram.ui.PhotoViewer.35
                @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                public /* synthetic */ void onReqId(int i2) {
                    TranslatorUtils.TranslateCallback.CC.$default$onReqId(this, i2);
                }

                @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                public /* synthetic */ void onSuccess(TLObject tLObject, TLRPC.TL_error tL_error) {
                    TranslatorUtils.TranslateCallback.CC.$default$onSuccess(this, tLObject, tL_error);
                }

                @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                public void onSuccess(String str) {
                    PhotoViewer.this.captionEdit.setText(str);
                    PhotoViewer.this.setCaption(str);
                }

                @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                public void onFailed() {
                    BulletinFactory.m1266of(PhotoViewer.this.containerView, resourcesProvider).createErrorBulletin(LocaleController.getString(C2369R.string.TranslationFailedAlert2)).show();
                }
            });
            return;
        }
        if (i == 6) {
            this.bitrate = -2;
            this.selectedCompression = -2;
            this.muteVideo = false;
            this.editState.reset();
            this.cropTransform = new CropTransform();
            PaintingOverlay paintingOverlay = this.paintingOverlay;
            if (paintingOverlay != null) {
                paintingOverlay.reset();
                this.paintingOverlay.setVisibility(8);
            }
            updateWidthHeightBitrateForCompression();
            updateVideoInfo();
            Object obj = this.imagesArrLocals.get(this.currentIndex);
            if (obj instanceof MediaController.MediaEditState) {
                MediaController.MediaEditState mediaEditState = (MediaController.MediaEditState) obj;
                mediaEditState.resetEdit();
                mediaEditState.editedInfo = getCurrentVideoEditedInfo();
            }
            int i2 = this.sendPhotoType;
            if ((i2 == 0 || i2 == 4) && (photoViewerProvider = this.placeProvider) != null) {
                photoViewerProvider.updatePhotoAtIndex(this.currentIndex);
            }
            showQualityView(false);
            requestVideoPreview(2);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda168
                @Override // java.lang.Runnable
                public final void run() throws NumberFormatException {
                    this.f$0.lambda$setParentActivity$61();
                }
            }, 200L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$61() throws NumberFormatException {
        sendPressed(true, 0, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$64(View view) {
        cancelStickerClippingMode();
        if (isCaptionOpen()) {
            return;
        }
        if (this.isCurrentVideo) {
            if (!this.videoConvertSupported) {
                return;
            }
            TextureView textureView = this.videoTextureView;
            if (!(textureView instanceof VideoEditTextureView)) {
                return;
            }
            VideoEditTextureView videoEditTextureView = (VideoEditTextureView) textureView;
            if (videoEditTextureView.getVideoWidth() <= 0 || videoEditTextureView.getVideoHeight() <= 0) {
                return;
            }
        }
        switchToEditMode(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$65(View view) {
        cropRotate(-90.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$66(View view) {
        cropMirror();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$67(View view) {
        cancelStickerClippingMode();
        if (isCaptionOpen()) {
            return;
        }
        if (this.isCurrentVideo) {
            if (!this.videoConvertSupported) {
                return;
            }
            TextureView textureView = this.videoTextureView;
            if (!(textureView instanceof VideoEditTextureView)) {
                return;
            }
            VideoEditTextureView videoEditTextureView = (VideoEditTextureView) textureView;
            if (videoEditTextureView.getVideoWidth() <= 0 || videoEditTextureView.getVideoHeight() <= 0) {
                return;
            }
        }
        switchToEditMode(3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$68(Activity activity, View view) throws NumberFormatException {
        if (isCaptionOpen() || this.muteVideo) {
            return;
        }
        int i = this.currentIndex;
        if (i >= 0 && i < this.imagesArrLocals.size()) {
            Object obj = this.imagesArrLocals.get(this.currentIndex);
            if (obj instanceof MediaController.PhotoEntry) {
                MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) obj;
                if (!photoEntry.isVideo) {
                    boolean z = !photoEntry.highQuality;
                    photoEntry.highQuality = z;
                    this.compressItem.setPhotoState(z);
                    showPhotoQualityHint(photoEntry.highQuality);
                    return;
                }
            }
        }
        if (this.compressItem.getTag() == null) {
            if (this.videoConvertSupported) {
                if (this.tooltip == null) {
                    this.tooltip = new Tooltip(activity, this.containerView, -871296751, -1);
                }
                this.tooltip.setText(LocaleController.getString("VideoQualityIsTooLow", C2369R.string.VideoQualityIsTooLow));
                this.tooltip.show(this.compressItem);
                return;
            }
            return;
        }
        showQualityView(true);
        requestVideoPreview(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$69(View view) {
        if (view.getAlpha() < 0.9f) {
            return;
        }
        cancelStickerClippingMode();
        if (isCaptionOpen()) {
            return;
        }
        if (this.isCurrentVideo) {
            if (!this.videoConvertSupported) {
                return;
            }
            TextureView textureView = this.videoTextureView;
            if (!(textureView instanceof VideoEditTextureView)) {
                return;
            }
            VideoEditTextureView videoEditTextureView = (VideoEditTextureView) textureView;
            if (videoEditTextureView.getVideoWidth() <= 0 || videoEditTextureView.getVideoHeight() <= 0) {
                return;
            }
        }
        switchToEditMode(2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$71(View view) {
        if (this.imageMoveAnimation != null) {
            return;
        }
        Runnable runnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda74
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setParentActivity$70();
            }
        };
        if (!this.previousHasTransform) {
            float stateOrientation = this.previousCropOrientation - this.photoCropView.cropView.getStateOrientation();
            if (Math.abs(stateOrientation) > 180.0f) {
                stateOrientation = stateOrientation < 0.0f ? stateOrientation + 360.0f : -(360.0f - stateOrientation);
            }
            cropRotate(stateOrientation, this.photoCropView.cropView.getStateMirror(), runnable);
            return;
        }
        runnable.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$70() {
        this.cropTransform.setViewTransform(this.previousHasTransform, this.previousCropPx, this.previousCropPy, this.previousCropRotation, this.previousCropOrientation, this.previousCropScale, scale1(), scale1(), this.previousCropPw, this.previousCropPh, 0.0f, 0.0f, this.previousCropMirrored);
        switchToEditMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$72(View view) {
        if (this.currentEditMode != 1 || this.photoCropView.isReady()) {
            applyCurrentEditMode();
            switchToEditMode(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$74(View view) {
        float f = -this.photoCropView.cropView.getStateOrientation();
        if (Math.abs(f) > 180.0f) {
            f = f < 0.0f ? f + 360.0f : -(360.0f - f);
        }
        cropRotate(f, this.photoCropView.cropView.getStateMirror(), new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda83
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setParentActivity$73();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$73() {
        this.photoCropView.reset(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$75(ImageReceiver imageReceiver, boolean z, boolean z2, boolean z3) {
        PhotoViewerProvider photoViewerProvider;
        int i;
        Bitmap bitmap;
        int i2;
        if (imageReceiver == this.centerImage && z && !z2) {
            if (!this.isCurrentVideo && ((this.currentEditMode == 1 || (i2 = this.sendPhotoType) == 1 || i2 == 11) && this.photoCropView != null && (bitmap = imageReceiver.getBitmap()) != null)) {
                PhotoCropView photoCropView = this.photoCropView;
                int orientation = imageReceiver.getOrientation();
                int i3 = this.sendPhotoType;
                photoCropView.setBitmap(bitmap, orientation, (i3 == 1 || i3 == 11) ? false : true, true, this.paintingOverlay, this.cropTransform, null, null);
            }
            if (this.paintingOverlay.getVisibility() == 0) {
                this.containerView.requestLayout();
            }
            detectFaces();
        }
        if (imageReceiver != this.centerImage || !z || (photoViewerProvider = this.placeProvider) == null || !photoViewerProvider.scaleToFill() || this.ignoreDidSetImage || (i = this.sendPhotoType) == 1 || i == 11) {
            return;
        }
        if (!this.wasLayout) {
            this.dontResetZoomOnFirstLayout = true;
        } else {
            setScaleToFill();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$76(View view) {
        if (isCaptionOpen()) {
            return;
        }
        setPhotoChecked();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$77(View view) {
        PhotoViewerProvider photoViewerProvider;
        if (isCaptionOpen() || (photoViewerProvider = this.placeProvider) == null || photoViewerProvider.getSelectedPhotosOrder() == null || this.placeProvider.getSelectedPhotosOrder().isEmpty()) {
            return;
        }
        togglePhotosListView(!this.isPhotosListViewVisible, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setParentActivity$78(View view, int i) throws Resources.NotFoundException, NumberFormatException {
        int i2;
        if (!this.imagesArrLocals.isEmpty() && (i2 = this.currentIndex) >= 0 && i2 < this.imagesArrLocals.size()) {
            Object obj = this.imagesArrLocals.get(this.currentIndex);
            if (obj instanceof MediaController.MediaEditState) {
                ((MediaController.MediaEditState) obj).editedInfo = getCurrentVideoEditedInfo();
            }
        }
        this.ignoreDidSetImage = true;
        int iIndexOf = this.imagesArrLocals.indexOf(view.getTag());
        if (iIndexOf >= 0) {
            this.currentIndex = -1;
            setImageIndex(iIndexOf);
        }
        this.ignoreDidSetImage = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0088 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0089 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v2 */
    /* JADX WARN: Type inference failed for: r1v24 */
    /* JADX WARN: Type inference failed for: r1v25 */
    /* JADX WARN: Type inference failed for: r1v26 */
    /* JADX WARN: Type inference failed for: r1v27 */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v4, types: [android.graphics.Bitmap] */
    /* JADX WARN: Type inference failed for: r1v6, types: [android.graphics.Bitmap] */
    /* JADX WARN: Type inference failed for: r1v7, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.io.File getFrameAsFile() {
        /*
            r6 = this;
            r0 = 0
            boolean r1 = r6.usedSurfaceView     // Catch: java.lang.Throwable -> L16
            if (r1 == 0) goto L3c
            android.widget.ImageView r1 = r6.textureImageView     // Catch: java.lang.Throwable -> L16
            android.graphics.drawable.Drawable r1 = r1.getDrawable()     // Catch: java.lang.Throwable -> L16
            boolean r2 = r1 instanceof android.graphics.drawable.BitmapDrawable     // Catch: java.lang.Throwable -> L16
            if (r2 == 0) goto L19
            android.graphics.drawable.BitmapDrawable r1 = (android.graphics.drawable.BitmapDrawable) r1     // Catch: java.lang.Throwable -> L16
            android.graphics.Bitmap r1 = r1.getBitmap()     // Catch: java.lang.Throwable -> L16
            goto L6a
        L16:
            r1 = move-exception
            r2 = r0
            goto L60
        L19:
            android.view.SurfaceView r1 = r6.videoSurfaceView     // Catch: java.lang.Throwable -> L16
            int r1 = r1.getWidth()     // Catch: java.lang.Throwable -> L16
            android.view.SurfaceView r2 = r6.videoSurfaceView     // Catch: java.lang.Throwable -> L16
            int r2 = r2.getHeight()     // Catch: java.lang.Throwable -> L16
            android.graphics.Bitmap$Config r3 = android.graphics.Bitmap.Config.ARGB_8888     // Catch: java.lang.Throwable -> L16
            android.graphics.Bitmap r1 = org.telegram.messenger.Bitmaps.createBitmap(r1, r2, r3)     // Catch: java.lang.Throwable -> L16
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch: java.lang.Throwable -> L37
            r3 = 24
            if (r2 < r3) goto L6a
            android.view.SurfaceView r2 = r6.videoSurfaceView     // Catch: java.lang.Throwable -> L37
            org.telegram.messenger.AndroidUtilities.getBitmapFromSurface(r2, r1)     // Catch: java.lang.Throwable -> L37
            goto L6a
        L37:
            r2 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
            goto L60
        L3c:
            android.view.TextureView r1 = r6.videoTextureView     // Catch: java.lang.Throwable -> L16
            if (r1 == 0) goto L5e
            boolean r1 = r1.isAvailable()     // Catch: java.lang.Throwable -> L16
            if (r1 == 0) goto L5e
            android.view.TextureView r1 = r6.videoTextureView     // Catch: java.lang.Throwable -> L16
            int r1 = r1.getWidth()     // Catch: java.lang.Throwable -> L16
            android.view.TextureView r2 = r6.videoTextureView     // Catch: java.lang.Throwable -> L16
            int r2 = r2.getHeight()     // Catch: java.lang.Throwable -> L16
            android.graphics.Bitmap$Config r3 = android.graphics.Bitmap.Config.ARGB_8888     // Catch: java.lang.Throwable -> L16
            android.graphics.Bitmap r1 = org.telegram.messenger.Bitmaps.createBitmap(r1, r2, r3)     // Catch: java.lang.Throwable -> L16
            android.view.TextureView r2 = r6.videoTextureView     // Catch: java.lang.Throwable -> L37
            r2.getBitmap(r1)     // Catch: java.lang.Throwable -> L37
            goto L6a
        L5e:
            r1 = r0
            goto L6a
        L60:
            if (r2 == 0) goto L66
            r2.recycle()
            r2 = r0
        L66:
            org.telegram.messenger.FileLog.m1160e(r1)
            r1 = r2
        L6a:
            if (r1 != 0) goto L86
            org.telegram.messenger.ImageReceiver r2 = r6.centerImage
            if (r2 == 0) goto L86
            org.telegram.messenger.ImageReceiver$BitmapHolder r2 = r2.getBitmapSafe()
            if (r2 == 0) goto L86
            android.graphics.Bitmap r2 = r2.bitmap
            if (r2 == 0) goto L86
            android.graphics.Bitmap$Config r3 = android.graphics.Bitmap.Config.ARGB_8888     // Catch: java.lang.Throwable -> L82
            r4 = 1
            android.graphics.Bitmap r1 = r2.copy(r3, r4)     // Catch: java.lang.Throwable -> L82
            goto L86
        L82:
            r2 = move-exception
            org.telegram.messenger.FileLog.m1160e(r2)
        L86:
            if (r1 != 0) goto L89
            return r0
        L89:
            java.io.File r0 = com.exteragram.messenger.utils.system.SystemUtils.getFileFromBitmap(r1)     // Catch: java.lang.Exception -> L8d
        L8d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.getFrameAsFile():java.io.File");
    }

    public boolean showCaptionLimitBulletin(FrameLayout frameLayout) {
        BaseFragment baseFragment = this.parentFragment;
        if (!(baseFragment instanceof ChatActivity) || !ChatObject.isChannelAndNotMegaGroup(((ChatActivity) baseFragment).getCurrentChat())) {
            return false;
        }
        this.limitBulletin = BulletinFactory.m1266of(frameLayout, this.resourcesProvider).createCaptionLimitBulletin(MessagesController.getInstance(this.currentAccount).captionLengthLimitPremium, new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda79
            @Override // java.lang.Runnable
            public final void run() throws NumberFormatException {
                this.f$0.lambda$showCaptionLimitBulletin$79();
            }
        }).setOnHideListener(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda80
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showCaptionLimitBulletin$80();
            }
        }).show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCaptionLimitBulletin$79() throws NumberFormatException {
        closePhoto(false, false);
        ChatAttachAlert chatAttachAlert = this.parentAlert;
        if (chatAttachAlert != null) {
            chatAttachAlert.dismiss(true);
        }
        BaseFragment baseFragment = this.parentFragment;
        if (baseFragment != null) {
            baseFragment.presentFragment(new PremiumPreviewFragment("caption_limit"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showCaptionLimitBulletin$80() {
        this.limitBulletin = null;
    }

    private void showScheduleDatePickerDialog() {
        if (this.parentChatActivity == null) {
            return;
        }
        AlertsCreator.createScheduleDatePickerDialog(this.parentActivity, this.parentChatActivity.getDialogId(), new AlertsCreator.ScheduleDatePickerDelegate() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda129
            @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
            public final void didSelectDate(boolean z, int i, int i2) throws NumberFormatException {
                this.f$0.lambda$showScheduleDatePickerDialog$82(z, i, i2);
            }
        }, new AlertsCreator.ScheduleDatePickerColors(-1, -14342875, -1, 520093695, -1, -115203550, 620756991));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showScheduleDatePickerDialog$82(boolean z, int i, int i2) throws NumberFormatException {
        sendPressed(z, i, 0);
    }

    private void sendPressed(boolean z, int i, int i2) throws NumberFormatException {
        sendPressed(z, i, i2, false, false, false);
    }

    private void replacePressed() throws NumberFormatException {
        sendPressed(false, 0, 0, true, false, false);
    }

    private void sendPressed(final boolean z, final int i, final int i2, final boolean z2, final boolean z3, boolean z4) throws NumberFormatException {
        final VideoEditedInfo videoEditedInfo;
        Object obj;
        MediaController.CropState cropState;
        int i3;
        ChatActivity chatActivity;
        ImageUpdater.AvatarFor avatarFor;
        String str;
        long j;
        String string;
        TextureView textureView;
        if (isCaptionOpen() || this.placeProvider == null || this.doneButtonPressed) {
            return;
        }
        String str2 = null;
        if (this.sendPhotoType == 1) {
            if (!z4 && (avatarFor = this.setAvatarFor) != null) {
                TLObject tLObject = avatarFor.object;
                if (!(tLObject instanceof TLRPC.User)) {
                    str = "";
                    j = 0;
                } else {
                    str = ((TLRPC.User) tLObject).first_name;
                    j = ((TLRPC.User) tLObject).f1734id;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this.containerView.getContext());
                builder.setAdditionalHorizontalPadding(AndroidUtilities.m1146dp(8.0f));
                SuggestUserPhotoView suggestUserPhotoView = new SuggestUserPhotoView(this.containerView.getContext());
                suggestUserPhotoView.setImages(this.setAvatarFor.object, this.containerView, this.photoCropView);
                builder.setTopView(suggestUserPhotoView);
                if (this.setAvatarFor.type == 1) {
                    if (UserConfig.getInstance(this.currentAccount).clientUserId == j) {
                        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString("SetUserPhotoSelfAlertMessage", C2369R.string.SetUserPhotoSelfAlertMessage)));
                    } else {
                        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("SetUserPhotoAlertMessage", C2369R.string.SetUserPhotoAlertMessage, str, str)));
                    }
                    if (this.centerImageIsVideo) {
                        string = LocaleController.getString("SetVideo", C2369R.string.SetVideo);
                    } else {
                        string = LocaleController.getString("SetPhoto", C2369R.string.SetPhoto);
                    }
                } else {
                    if (this.centerImageIsVideo) {
                        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("SuggestVideoAlertMessage", C2369R.string.SuggestVideoAlertMessage, str)));
                    } else {
                        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("SuggestPhotoAlertMessage", C2369R.string.SuggestPhotoAlertMessage, str)));
                    }
                    string = LocaleController.getString("SuggestPhotoShort", C2369R.string.SuggestPhotoShort);
                }
                String str3 = string;
                builder.setNegativeButton(LocaleController.getString("Cancel", C2369R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda86
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i4) {
                        PhotoViewer.$r8$lambda$OEv6YSCoYBzuTsii8BTCCKyzfCI(alertDialog, i4);
                    }
                });
                builder.setPositiveButton(str3, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda87
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i4) throws NumberFormatException {
                        this.f$0.lambda$sendPressed$84(z, i, i2, z2, z3, alertDialog, i4);
                    }
                });
                builder.setDialogButtonColorKey(Theme.key_voipgroup_listeningText);
                AlertDialog alertDialogCreate = builder.create();
                alertDialogCreate.setBlurParams(0.8f, false, true);
                alertDialogCreate.setBackgroundColor(ColorUtils.setAlphaComponent(-15461356, 204));
                alertDialogCreate.show();
                alertDialogCreate.setTextColor(Theme.getColor(Theme.key_voipgroup_nameText));
                alertDialogCreate.setOnDismissListener(new DialogInterfaceOnDismissListenerC581039());
                if (!this.isCurrentVideo || (textureView = this.videoTextureView) == null) {
                    return;
                }
                try {
                    this.lastFrameBitmap = textureView.getBitmap();
                    ImageView imageView = this.lastFrameImageView;
                    if (imageView != null) {
                        this.aspectRatioFrameLayout.removeView(imageView);
                        this.lastFrameImageView = null;
                    }
                    ImageView imageView2 = new ImageView(this.videoTextureView.getContext());
                    this.lastFrameImageView = imageView2;
                    imageView2.setBackground(new BitmapDrawable(this.lastFrameBitmap));
                    this.aspectRatioFrameLayout.addView(this.lastFrameImageView);
                    return;
                } catch (Throwable th) {
                    Bitmap bitmap = this.currentBitmap;
                    if (bitmap != null) {
                        bitmap.recycle();
                        this.currentBitmap = null;
                    }
                    FileLog.m1160e(th);
                    return;
                }
            }
            applyCurrentEditMode();
        }
        if (!z2 && (chatActivity = this.parentChatActivity) != null) {
            TLRPC.Chat currentChat = chatActivity.getCurrentChat();
            if (this.parentChatActivity.getCurrentUser() != null || ((ChatObject.isChannel(currentChat) && currentChat.megagroup) || !ChatObject.isChannel(currentChat))) {
                MessagesController.getNotificationsSettings(this.currentAccount).edit().putBoolean(NotificationsSettingsFacade.PROPERTY_SILENT + this.parentChatActivity.getDialogId(), !z).apply();
            }
        }
        VideoEditedInfo currentVideoEditedInfo = getCurrentVideoEditedInfo();
        if (!this.imagesArrLocals.isEmpty() && (i3 = this.currentIndex) >= 0 && i3 < this.imagesArrLocals.size()) {
            Object obj2 = this.imagesArrLocals.get(this.currentIndex);
            if (obj2 instanceof MediaController.MediaEditState) {
                ((MediaController.MediaEditState) obj2).editedInfo = currentVideoEditedInfo;
            }
        }
        ChatActivity chatActivity2 = this.parentChatActivity;
        if (chatActivity2 != null && chatActivity2.getCurrentChat() != null) {
            boolean z5 = this.isCurrentVideo || currentVideoEditedInfo != null;
            if (z5 && !ChatObject.canSendVideo(this.parentChatActivity.getCurrentChat())) {
                BulletinFactory.m1266of(this.containerView, this.resourcesProvider).createErrorBulletin(LocaleController.getString(C2369R.string.GlobalAttachVideoRestricted)).show();
                return;
            } else if (!z5 && !ChatObject.canSendPhoto(this.parentChatActivity.getCurrentChat())) {
                BulletinFactory.m1266of(this.containerView, this.resourcesProvider).createErrorBulletin(LocaleController.getString(C2369R.string.GlobalAttachPhotoRestricted)).show();
                return;
            }
        }
        this.doneButtonPressed = true;
        if (currentVideoEditedInfo != null) {
            long j2 = (long) (currentVideoEditedInfo.estimatedSize * 0.9f);
            if ((j2 > FileLoader.DEFAULT_MAX_FILE_SIZE && !UserConfig.getInstance(this.currentAccount).isPremium()) || j2 > FileLoader.DEFAULT_MAX_FILE_SIZE_PREMIUM) {
                if (this.parentAlert != null) {
                    new LimitReachedBottomSheet(this.parentAlert.getBaseFragment(), this.parentAlert.getContainer().getContext(), 6, UserConfig.selectedAccount, null).show();
                    return;
                }
                return;
            }
        }
        if (!z2) {
            if (this.sendPhotoType == 11) {
                Object obj3 = this.imagesArrLocals.get(this.currentIndex);
                if (obj3 instanceof MediaController.PhotoEntry) {
                    final MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) obj3;
                    Bitmap bitmapCreateBitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmapCreateBitmap);
                    Path path = new Path();
                    RectF rectF = new RectF();
                    rectF.set(0.0f, 0.0f, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
                    int i4 = 8;
                    float width = bitmapCreateBitmap.getWidth() / 8;
                    path.addRoundRect(rectF, width, width, Path.Direction.CW);
                    canvas.clipPath(path);
                    int containerViewWidth = getContainerViewWidth();
                    getContainerViewHeight();
                    float fM1146dp = containerViewWidth - AndroidUtilities.m1146dp(20.0f);
                    StickerMakerView stickerMakerView = this.stickerMakerView;
                    if (stickerMakerView != null && stickerMakerView.outlineVisible && stickerMakerView.getSourceBitmap() != null) {
                        canvas.save();
                        canvas.translate(bitmapCreateBitmap.getWidth() / 2.0f, bitmapCreateBitmap.getHeight() / 2.0f);
                        canvas.scale(bitmapCreateBitmap.getWidth() / fM1146dp, bitmapCreateBitmap.getHeight() / fM1146dp);
                        applyTransformToOutline(canvas);
                        this.stickerMakerView.drawOutline(canvas, false, null, false);
                        canvas.restore();
                        canvas.save();
                        canvas.translate(bitmapCreateBitmap.getWidth() / 2.0f, bitmapCreateBitmap.getHeight() / 2.0f);
                        canvas.scale(bitmapCreateBitmap.getWidth() / fM1146dp, bitmapCreateBitmap.getHeight() / fM1146dp);
                        applyTransformToOutline(canvas);
                        this.centerImage.draw(canvas);
                        canvas.restore();
                        canvas.save();
                        canvas.translate(bitmapCreateBitmap.getWidth() / 2.0f, bitmapCreateBitmap.getHeight() / 2.0f);
                        canvas.scale(bitmapCreateBitmap.getWidth() / fM1146dp, bitmapCreateBitmap.getHeight() / fM1146dp);
                        applyTransformToOutline(canvas);
                        this.stickerMakerView.drawOutline(canvas, true, null, false);
                        canvas.restore();
                    } else {
                        canvas.save();
                        canvas.translate(bitmapCreateBitmap.getWidth() / 2.0f, bitmapCreateBitmap.getHeight() / 2.0f);
                        canvas.scale(bitmapCreateBitmap.getWidth() / fM1146dp, bitmapCreateBitmap.getHeight() / fM1146dp);
                        applyTransformToOutline(canvas);
                        this.centerImage.draw(canvas);
                        canvas.restore();
                    }
                    if (this.paintingOverlay != null) {
                        canvas.save();
                        canvas.translate(bitmapCreateBitmap.getWidth() / 2.0f, bitmapCreateBitmap.getHeight() / 2.0f);
                        canvas.scale(bitmapCreateBitmap.getWidth() / fM1146dp, bitmapCreateBitmap.getHeight() / fM1146dp);
                        applyTransformToOutline(canvas);
                        canvas.translate((-this.centerImage.getImageWidth()) / 2.0f, (-this.centerImage.getImageHeight()) / 2.0f);
                        canvas.scale(this.centerImage.getImageWidth() / this.paintingOverlay.getMeasuredWidth(), this.centerImage.getImageHeight() / this.paintingOverlay.getMeasuredHeight());
                        this.paintingOverlay.drawChildren = !hasAnimatedMediaEntities();
                        this.paintingOverlay.draw(canvas);
                        this.paintingOverlay.drawChildren = true;
                        canvas.restore();
                    }
                    if (hasAnimatedMediaEntities()) {
                        Matrix matrix = new Matrix();
                        matrix.reset();
                        float f = fM1146dp / 2.0f;
                        matrix.preTranslate(f, f);
                        applyTransformToMatrix(matrix);
                        matrix.preTranslate((-this.centerImage.getImageWidth()) / 2.0f, (-this.centerImage.getImageHeight()) / 2.0f);
                        matrix.preScale(this.centerImage.getImageWidth(), this.centerImage.getImageHeight());
                        ArrayList<VideoEditedInfo.MediaEntity> arrayList = new ArrayList<>();
                        ArrayList<VideoEditedInfo.MediaEntity> arrayList2 = photoEntry.mediaEntities;
                        int size = arrayList2.size();
                        String strFindAnimatedEmojiEmoticon = null;
                        int i5 = 0;
                        while (i5 < size) {
                            VideoEditedInfo.MediaEntity mediaEntity = arrayList2.get(i5);
                            int i6 = i5 + 1;
                            VideoEditedInfo.MediaEntity mediaEntityCopy = mediaEntity.copy();
                            float f2 = mediaEntityCopy.f1476x;
                            float f3 = mediaEntityCopy.f1477y;
                            float f4 = mediaEntityCopy.width + f2;
                            float f5 = mediaEntityCopy.height + f3;
                            float[] fArr = new float[i4];
                            fArr[0] = f2;
                            fArr[1] = f3;
                            fArr[2] = f4;
                            fArr[3] = f3;
                            fArr[4] = f4;
                            fArr[5] = f5;
                            fArr[6] = f2;
                            fArr[7] = f5;
                            matrix.mapPoints(fArr);
                            float f6 = fM1146dp;
                            ArrayList<VideoEditedInfo.MediaEntity> arrayList3 = arrayList2;
                            mediaEntityCopy.width = ((float) Math.sqrt(Math.pow(fArr[0] - fArr[2], 2.0d) + Math.pow(fArr[1] - fArr[3], 2.0d))) / f6;
                            float fSqrt = ((float) Math.sqrt(Math.pow(fArr[0] - fArr[6], 2.0d) + Math.pow(fArr[1] - fArr[7], 2.0d))) / f6;
                            mediaEntityCopy.height = fSqrt;
                            mediaEntityCopy.f1476x = (((fArr[0] + fArr[4]) / 2.0f) / f6) - (mediaEntityCopy.width / 2.0f);
                            mediaEntityCopy.f1477y = (((fArr[1] + fArr[5]) / 2.0f) / f6) - (fSqrt / 2.0f);
                            mediaEntityCopy.scale = 1.0f;
                            mediaEntityCopy.customTextView = true;
                            if (photoEntry.isCropped && (cropState = photoEntry.cropState) != null) {
                                mediaEntityCopy.rotation = (float) (mediaEntityCopy.rotation - ((cropState.transformRotation / 180.0f) * 3.141592653589793d));
                            }
                            mediaEntityCopy.rotation = (float) (mediaEntityCopy.rotation - ((this.rotate / 180.0f) * 3.141592653589793d));
                            arrayList.add(mediaEntityCopy);
                            TLRPC.Document document = mediaEntityCopy.document;
                            if (document == null || strFindAnimatedEmojiEmoticon != null) {
                                obj = null;
                            } else {
                                obj = null;
                                strFindAnimatedEmojiEmoticon = MessageObject.findAnimatedEmojiEmoticon(document, null);
                            }
                            i5 = i6;
                            fM1146dp = f6;
                            arrayList2 = arrayList3;
                            i4 = 8;
                        }
                        VideoEditedInfo videoEditedInfo2 = new VideoEditedInfo();
                        videoEditedInfo2.isPhoto = true;
                        videoEditedInfo2.resultWidth = 512;
                        videoEditedInfo2.originalWidth = 512;
                        videoEditedInfo2.resultHeight = 512;
                        videoEditedInfo2.originalHeight = 512;
                        videoEditedInfo2.mediaEntities = arrayList;
                        long jClamp = Utilities.clamp(photoEntry.averageDuration, 2999L, 800L);
                        videoEditedInfo2.estimatedDuration = jClamp;
                        videoEditedInfo2.originalDuration = jClamp;
                        videoEditedInfo2.bitrate = 200000;
                        videoEditedInfo2.framerate = 30;
                        videoEditedInfo2.isSticker = true;
                        videoEditedInfo2.estimatedSize = 262144L;
                        videoEditedInfo = videoEditedInfo2;
                        str2 = strFindAnimatedEmojiEmoticon;
                    } else {
                        videoEditedInfo = null;
                    }
                    float f7 = 512;
                    final String string2 = FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(ImageLoader.scaleAndSaveImage(bitmapCreateBitmap, Bitmap.CompressFormat.WEBP, f7, f7, 100, false, 101, 101), "webp", true).toString();
                    if (videoEditedInfo != null) {
                        videoEditedInfo.originalPath = string2;
                    }
                    if (this.selectedEmojis == null) {
                        this.selectedEmojis = new ArrayList();
                    }
                    if (this.selectedEmojis.isEmpty()) {
                        String str4 = this.stickerMakerView.detectedEmoji;
                        if (str4 != null && Emoji.getEmojiDrawable(str4) != null) {
                            this.selectedEmojis.add(this.stickerMakerView.detectedEmoji);
                        } else if (str2 != null) {
                            this.selectedEmojis.add(str2);
                        } else {
                            this.selectedEmojis.add("👍");
                        }
                    }
                    this.doneButtonPressed = false;
                    ContentPreviewViewer.getInstance().showCustomStickerActions(string2, videoEditedInfo, this.stickerMakerView, this.selectedEmojis, new ContentPreviewViewer.ContentPreviewViewerDelegate() { // from class: org.telegram.ui.PhotoViewer.40
                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean can() {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$can(this);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean canDeleteSticker(TLRPC.Document document2) {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$canDeleteSticker(this, document2);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean canEditSticker() {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$canEditSticker(this);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean canSchedule() {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$canSchedule(this);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ Boolean canSetAsStatus(TLRPC.Document document2) {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$canSetAsStatus(this, document2);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void copyEmoji(TLRPC.Document document2) {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$copyEmoji(this, document2);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void deleteSticker(TLRPC.Document document2) {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$deleteSticker(this, document2);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void editSticker(TLRPC.Document document2) {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$editSticker(this, document2);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ String getQuery(boolean z6) {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$getQuery(this, z6);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void gifAddedOrDeleted() {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$gifAddedOrDeleted(this);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean isInScheduleMode() {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$isInScheduleMode(this);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean isPhotoEditor() {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$isPhotoEditor(this);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public boolean isStickerEditor() {
                            return true;
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean needCopy(TLRPC.Document document2) {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$needCopy(this, document2);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean needMenu() {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$needMenu(this);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean needOpen() {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$needOpen(this);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean needRemove() {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$needRemove(this);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean needRemoveFromRecent(TLRPC.Document document2) {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$needRemoveFromRecent(this, document2);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ boolean needSend(int i7) {
                            return ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$needSend(this, i7);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void openSet(TLRPC.InputStickerSet inputStickerSet, boolean z6) {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$openSet(this, inputStickerSet, z6);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void remove(SendMessagesHelper.ImportingSticker importingSticker) {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$remove(this, importingSticker);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void removeFromRecent(TLRPC.Document document2) {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$removeFromRecent(this, document2);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void resetTouch() {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$resetTouch(this);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void sendEmoji(TLRPC.Document document2) {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$sendEmoji(this, document2);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void sendGif(Object obj4, Object obj5, boolean z6, int i7, int i8) {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$sendGif(this, obj4, obj5, z6, i7, i8);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void sendSticker(TLRPC.Document document2, String str5, Object obj4, boolean z6, int i7, int i8) {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$sendSticker(this, document2, str5, obj4, z6, i7, i8);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public /* synthetic */ void setAsEmojiStatus(TLRPC.Document document2, Integer num) {
                            ContentPreviewViewer.ContentPreviewViewerDelegate.CC.$default$setAsEmojiStatus(this, document2, num);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public void sendSticker() {
                            if (PhotoViewer.this.placeProvider == null) {
                                return;
                            }
                            PhotoViewer.this.stickerEmptySent = true;
                            generateThumb();
                            MediaController.PhotoEntry photoEntry2 = photoEntry;
                            photoEntry2.highQuality = false;
                            photoEntry2.imagePath = string2;
                            PhotoViewer.this.placeProvider.sendButtonPressed(PhotoViewer.this.currentIndex, videoEditedInfo, z, i, i2, z3);
                            NotificationCenter.getInstance(UserConfig.selectedAccount).postNotificationNameOnUIThread(NotificationCenter.customStickerCreated, Boolean.TRUE);
                        }

                        private void generateThumb() {
                            ContentPreviewViewer contentPreviewViewer = ContentPreviewViewer.getInstance();
                            if (photoEntry.thumbPath != null) {
                                try {
                                    new File(photoEntry.thumbPath).delete();
                                } catch (Exception e) {
                                    FileLog.m1160e(e);
                                }
                                photoEntry.thumbPath = null;
                            }
                            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(512, 512, Bitmap.Config.ARGB_8888);
                            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
                            ImageReceiver imageReceiver = contentPreviewViewer.centerImage;
                            if (imageReceiver != null) {
                                imageReceiver.setAlpha(1.0f);
                                contentPreviewViewer.centerImage.setImageCoords(0.0f, 0.0f, bitmapCreateBitmap2.getWidth(), bitmapCreateBitmap2.getHeight());
                                contentPreviewViewer.centerImage.draw(canvas2);
                            }
                            if (contentPreviewViewer.paintingOverlay != null) {
                                canvas2.save();
                                canvas2.scale(bitmapCreateBitmap2.getWidth() / contentPreviewViewer.paintingOverlay.getWidth(), bitmapCreateBitmap2.getHeight() / contentPreviewViewer.paintingOverlay.getHeight());
                                contentPreviewViewer.paintingOverlay.setAlpha(1.0f);
                                Path path2 = new Path();
                                path2.rewind();
                                RectF rectF2 = AndroidUtilities.rectTmp;
                                rectF2.set(0.0f, 0.0f, contentPreviewViewer.paintingOverlay.getWidth(), contentPreviewViewer.paintingOverlay.getHeight());
                                path2.addRoundRect(rectF2, contentPreviewViewer.paintingOverlay.getWidth() / 8.0f, contentPreviewViewer.paintingOverlay.getHeight() / 8.0f, Path.Direction.CW);
                                canvas2.clipPath(path2);
                                contentPreviewViewer.paintingOverlay.draw(canvas2);
                                canvas2.restore();
                            }
                            photoEntry.thumbPath = FileLoader.getInstance(PhotoViewer.this.currentAccount).getPathToAttach(ImageLoader.scaleAndSaveImage(bitmapCreateBitmap2, PhotoViewer.this.getCompressFormat(), 512.0f, 512.0f, 83, false, 101, 101), true).toString();
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public void addToFavoriteSelected(String str5) {
                            PhotoViewer.this.stickerEmptySent = true;
                            generateThumb();
                            PhotoViewer.this.stickerMakerView.uploadStickerFile(string2, videoEditedInfo, str5, null, true, null, null, photoEntry.thumbPath, null, null);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public void stickerSetSelected(TLRPC.StickerSet stickerSet, String str5) {
                            PhotoViewer.this.stickerEmptySent = true;
                            generateThumb();
                            PhotoViewer photoViewer = PhotoViewer.this;
                            photoViewer.stickerMakerView.uploadStickerFile(string2, videoEditedInfo, str5, null, false, stickerSet, photoViewer.replacedSticker, photoEntry.thumbPath, null, null);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public void newStickerPackSelected(CharSequence charSequence, String str5, Utilities.Callback callback) {
                            PhotoViewer.this.stickerEmptySent = true;
                            generateThumb();
                            PhotoViewer.this.stickerMakerView.uploadStickerFile(string2, videoEditedInfo, str5, charSequence, false, null, null, photoEntry.thumbPath, callback, null);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public void setIntroSticker(String str5) {
                            PhotoViewer.this.stickerEmptySent = true;
                            generateThumb();
                            PhotoViewer photoViewer = PhotoViewer.this;
                            photoViewer.stickerMakerView.uploadStickerFile(string2, videoEditedInfo, str5, null, false, null, null, photoEntry.thumbPath, null, photoViewer.customStickerHandler);
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public boolean isReplacedSticker() {
                            return PhotoViewer.this.replacedSticker != null;
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public boolean isSettingIntroSticker() {
                            return PhotoViewer.this.customStickerHandler != null;
                        }

                        @Override // org.telegram.ui.ContentPreviewViewer.ContentPreviewViewerDelegate
                        public long getDialogId() {
                            return PhotoViewer.this.currentDialogId;
                        }
                    });
                    return;
                }
            }
            this.placeProvider.sendButtonPressed(this.currentIndex, currentVideoEditedInfo, z, i, i2, z3);
        } else {
            this.placeProvider.replaceButtonPressed(this.currentIndex, currentVideoEditedInfo);
        }
        if (this.closePhotoAfterSelect) {
            if (this.closePhotoAfterSelectWithAnimation) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda88
                    @Override // java.lang.Runnable
                    public final void run() throws NumberFormatException {
                        this.f$0.lambda$sendPressed$85();
                    }
                }, 200L);
            } else {
                closePhoto(false, false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendPressed$84(boolean z, int i, int i2, boolean z2, boolean z3, AlertDialog alertDialog, int i3) throws NumberFormatException {
        sendPressed(z, i, i2, z2, z3, true);
    }

    /* renamed from: org.telegram.ui.PhotoViewer$39 */
    /* loaded from: classes5.dex */
    class DialogInterfaceOnDismissListenerC581039 implements DialogInterface.OnDismissListener {
        DialogInterfaceOnDismissListenerC581039() {
        }

        @Override // android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialogInterface) {
            if (PhotoViewer.this.lastFrameImageView != null) {
                PhotoViewer.this.lastFrameImageView.animate().alpha(0.0f).withEndAction(new Runnable() { // from class: org.telegram.ui.PhotoViewer$39$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDismiss$0();
                    }
                }).setDuration(150L).start();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDismiss$0() {
            if (PhotoViewer.this.lastFrameImageView == null || PhotoViewer.this.lastFrameImageView.getParent() == null) {
                return;
            }
            ((ViewGroup) PhotoViewer.this.lastFrameImageView.getParent()).removeView(PhotoViewer.this.lastFrameImageView);
            if (PhotoViewer.this.lastFrameBitmap != null) {
                if (PhotoViewer.this.lastFrameImageView != null) {
                    PhotoViewer.this.lastFrameImageView.setBackground(null);
                }
                AndroidUtilities.recycleBitmap(PhotoViewer.this.lastFrameBitmap);
                PhotoViewer.this.lastFrameBitmap = null;
            }
            PhotoViewer.this.lastFrameImageView = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendPressed$85() throws NumberFormatException {
        closePhoto(true, false);
    }

    public static String getTempFileAbsolutePath() {
        return new File(FileLoader.getDirectory(4), SharedConfig.getLastLocalId() + "_temp.jpg").getAbsolutePath();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap.CompressFormat getCompressFormat() {
        return this.sendPhotoType == 11 ? Bitmap.CompressFormat.PNG : Bitmap.CompressFormat.JPEG;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCaptionOpen() {
        CaptionContainerView captionView = getCaptionView();
        if (captionView != null) {
            return captionView.keyboardNotifier.keyboardVisible() || captionView.editText.isPopupShowing();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showShareAlert(ArrayList arrayList) throws Resources.NotFoundException {
        boolean z;
        VideoPlayer videoPlayer;
        MessageObject messageObject;
        boolean z2;
        FrameLayoutDrawer frameLayoutDrawer = this.containerView;
        requestAdjustToNothing();
        ChatActivity chatActivity = this.parentChatActivity;
        if (chatActivity == null || chatActivity.getChatActivityEnterView() == null || this.parentChatActivity.getFragmentView() == null) {
            z = false;
        } else {
            if (this.parentChatActivity.getChatActivityEnterView().isKeyboardVisible()) {
                this.parentChatActivity.getChatActivityEnterView().showEmojiView();
                z2 = true;
            } else {
                z2 = false;
            }
            AndroidUtilities.setAdjustResizeToNothing(this.parentChatActivity.getParentActivity(), this.classGuid);
            this.parentChatActivity.getFragmentView().requestLayout();
            z = z2;
        }
        String str = null;
        Integer numValueOf = (arrayList == null || arrayList.size() != 1 || (videoPlayer = this.videoPlayer) == null || videoPlayer.getCurrentPosition() <= 1500 || (messageObject = this.currentMessageObject) == null || !messageObject.isVideo()) ? null : Integer.valueOf((int) (this.videoPlayer.getCurrentPosition() / 1000));
        if (arrayList != null && arrayList.size() == 1) {
            MessageObject messageObject2 = (MessageObject) arrayList.get(0);
            String publicUsername = ChatObject.getPublicUsername(MessagesController.getInstance(this.currentAccount).getChat(Long.valueOf(-messageObject2.getDialogId())));
            if (!TextUtils.isEmpty(publicUsername)) {
                str = "https://" + MessagesController.getInstance(this.currentAccount).linkPrefix + "/" + publicUsername + "/" + messageObject2.getId();
            }
        }
        final DialogC581341 dialogC581341 = new DialogC581341(this.parentActivity, this.parentChatActivity, arrayList, null, null, null, false, str, null, false, true, false, numValueOf, null, frameLayoutDrawer, z);
        dialogC581341.setFocusable(false);
        dialogC581341.getWindow().setSoftInputMode(48);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda135
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showShareAlert$86(dialogC581341);
            }
        }, 250L);
        dialogC581341.show();
    }

    /* renamed from: org.telegram.ui.PhotoViewer$41 */
    /* loaded from: classes5.dex */
    class DialogC581341 extends ShareAlert {
        final /* synthetic */ boolean val$finalOpenKeyboardOnShareAlertClose;
        final /* synthetic */ FrameLayout val$photoContainerView;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        DialogC581341(Context context, ChatActivity chatActivity, ArrayList arrayList, String str, String str2, String str3, boolean z, String str4, String str5, boolean z2, boolean z3, boolean z4, Integer num, Theme.ResourcesProvider resourcesProvider, FrameLayout frameLayout, boolean z5) {
            super(context, chatActivity, arrayList, str, str2, str3, z, str4, str5, z2, z3, z4, num, resourcesProvider);
            this.val$photoContainerView = frameLayout;
            this.val$finalOpenKeyboardOnShareAlertClose = z5;
        }

        @Override // org.telegram.p023ui.Components.ShareAlert
        protected void onSend(final LongSparseArray longSparseArray, final int i, TLRPC.TL_forumTopic tL_forumTopic, boolean z) {
            if (z) {
                final FrameLayout frameLayout = this.val$photoContainerView;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$41$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSend$0(frameLayout, longSparseArray, i);
                    }
                }, 250L);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSend$0(FrameLayout frameLayout, LongSparseArray longSparseArray, int i) {
            BulletinFactory.createForwardedBulletin(PhotoViewer.this.parentActivity, null, frameLayout, longSparseArray.size(), longSparseArray.size() == 1 ? ((TLRPC.Dialog) longSparseArray.valueAt(0)).f1577id : 0L, i, -115203550, -1, 1500).show();
        }

        @Override // org.telegram.p023ui.Components.ShareAlert, org.telegram.p023ui.ActionBar.BottomSheet
        public void dismissInternal() {
            super.dismissInternal();
            if (this.val$finalOpenKeyboardOnShareAlertClose) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$41$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$dismissInternal$1();
                    }
                }, 50L);
            }
            PhotoViewer.this.requestAdjust();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$dismissInternal$1() {
            if (PhotoViewer.this.parentChatActivity == null || PhotoViewer.this.parentChatActivity.getChatActivityEnterView() == null) {
                return;
            }
            PhotoViewer.this.parentChatActivity.getChatActivityEnterView().openKeyboard();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showShareAlert$86(ShareAlert shareAlert) {
        if (shareAlert == null || shareAlert.getWindow() == null) {
            return;
        }
        shareAlert.setFocusable(true);
        ChatActivity chatActivity = this.parentChatActivity;
        if (chatActivity == null || chatActivity.getChatActivityEnterView() == null) {
            return;
        }
        this.parentChatActivity.getChatActivityEnterView().hidePopup(false);
    }

    public void showChromecastBulletin(String str, String str2) {
        if (this.activityContext == null || this.containerView == null || !isVisible()) {
            return;
        }
        BulletinFactory.m1266of(this.containerView, new DarkThemeResourceProvider()).createSimpleBulletin(C2369R.raw.forward, !TextUtils.isEmpty(str2) ? LocaleController.formatString(C2369R.string.ChromecastStartedTo, str2) : LocaleController.getString(C2369R.string.ChromecastStarted)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateActionBarTitlePadding() {
        if (this.menu == null || this.actionBarContainer == null) {
            return;
        }
        float fMax = 0.0f;
        for (int i = 0; i < this.menu.getChildCount(); i++) {
            View childAt = this.menu.getChildAt(i);
            if (childAt.getVisibility() == 0) {
                fMax += Math.min(0.5f, childAt.getAlpha()) * 2.0f * childAt.getWidth();
            }
        }
        CheckBox checkBox = this.checkImageView;
        if (checkBox != null && checkBox.getVisibility() == 0) {
            fMax = Math.max(fMax, AndroidUtilities.m1146dp(48.0f));
        }
        CounterView counterView = this.photosCounterView;
        if (counterView != null && counterView.getVisibility() == 0) {
            fMax = Math.max(fMax, AndroidUtilities.m1146dp(100.0f));
        }
        this.actionBarContainer.updateRightPadding(fMax, false);
    }

    private void setMenuItemIcon(boolean z, boolean z2) {
        if (this.speedItem.getVisibility() != 0 || Math.abs(this.currentVideoSpeed - 1.0f) < 0.001f) {
            this.videoItemIcon.topText.setText("", z);
        } else {
            this.videoItemIcon.topText.setText(SpeedIconDrawable.formatNumber(this.currentVideoSpeed) + "x", z);
        }
        this.speedItem.setSpeed(this.currentVideoSpeed, z);
        this.chooseSpeedLayout.update(this.currentVideoSpeed, z2);
    }

    private void chooseQuality(int i) throws NumberFormatException {
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer != null) {
            videoPlayer.setSelectedQuality(i);
        }
        if (i == -1) {
            VideoPlayer.saveQuality(null, this.currentMessageObject);
        } else {
            VideoPlayer videoPlayer2 = this.videoPlayer;
            if (videoPlayer2 != null) {
                VideoPlayer.saveQuality(videoPlayer2.getQuality(i), this.currentMessageObject);
            }
        }
        updateQualityItems();
        this.videoItem.toggleSubMenu();
        try {
            CastSync.check(0);
            if (ChromecastController.getInstance().isCasting()) {
                ChromecastController.getInstance().setCurrentMediaAndCastIfNeeded(getCurrentChromecastMedia());
            }
            VideoPlayer videoPlayer3 = this.videoPlayer;
            if (videoPlayer3 != null) {
                CastSync.setPlaying(videoPlayer3.isPlaying());
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0442  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x045a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateQualityItems() {
        /*
            Method dump skipped, instructions count: 1529
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.updateQualityItems():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateQualityItems$87(int i, View view) throws NumberFormatException {
        chooseQuality(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateQualityItems$88(int i, View view) throws NumberFormatException {
        chooseQuality(i);
    }

    public float getCurrentVideoSpeed() {
        return this.currentVideoSpeed;
    }

    private boolean checkInlinePermissions() {
        Activity activity = this.parentActivity;
        if (activity == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 23 || PipUtils.checkAnyPipPermissions(activity)) {
            return true;
        }
        AlertsCreator.createDrawOverlayPermissionDialog(this.parentActivity, null, true).show();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void captureCurrentFrame() {
        TextureView textureView;
        if (this.captureFrameAtTime == -1 || (textureView = this.videoTextureView) == null) {
            return;
        }
        this.captureFrameAtTime = -1L;
        final Bitmap bitmap = textureView.getBitmap();
        this.flashView.animate().alpha(1.0f).setInterpolator(CubicBezierInterpolator.EASE_BOTH).setDuration(85L).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.42
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PhotoViewer.this.photoCropView.setVideoThumb(bitmap, 0);
                PhotoViewer.this.flashAnimator = new AnimatorSet();
                PhotoViewer.this.flashAnimator.playTogether(ObjectAnimator.ofFloat(PhotoViewer.this.flashView, (Property<View, Float>) PhotoViewer.this.FLASH_VIEW_VALUE, 0.0f));
                PhotoViewer.this.flashAnimator.setDuration(85L);
                PhotoViewer.this.flashAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT);
                PhotoViewer.this.flashAnimator.addListener(new AnonymousClass1());
                PhotoViewer.this.flashAnimator.start();
            }

            /* renamed from: org.telegram.ui.PhotoViewer$42$1, reason: invalid class name */
            class AnonymousClass1 extends AnimatorListenerAdapter {
                AnonymousClass1() {
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (PhotoViewer.this.flashAnimator == null) {
                        return;
                    }
                    PhotoViewer photoViewer = PhotoViewer.this;
                    Runnable runnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer$42$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onAnimationEnd$0();
                        }
                    };
                    photoViewer.videoPlayRunnable = runnable;
                    AndroidUtilities.runOnUIThread(runnable, 860L);
                }

                /* JADX INFO: Access modifiers changed from: private */
                public /* synthetic */ void lambda$onAnimationEnd$0() {
                    PhotoViewer.this.manuallyPaused = false;
                    if (PhotoViewer.this.videoPlayer != null) {
                        PhotoViewer.this.videoPlayer.play();
                    }
                    PhotoViewer.this.videoPlayRunnable = null;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    PhotoViewer.this.flashAnimator = null;
                }
            }
        }).start();
    }

    /* loaded from: classes5.dex */
    public static class CaptionTextView extends SpoilersTextView {
        private AnimatedEmojiSpan.EmojiGroupedSpans animatedEmojiDrawables;
        private boolean hasQuote;
        private Layout lastLayout;
        private Layout lastLoadingLayout;
        private boolean loading;
        private LoadingDrawable loadingDrawable;
        private Path loadingPath;
        private ArrayList quoteBlocks;
        private final TextSelectionHelper.SimpleTextSelectionHelper textSelectionHelper;

        public CaptionTextView(Context context, final CaptionScrollView captionScrollView, TextSelectionHelper.SimpleTextSelectionHelper simpleTextSelectionHelper, final Utilities.Callback2 callback2, final Utilities.Callback3 callback3) {
            super(context);
            setClearLinkOnLongPress(false);
            setDisablePaddingsOffsetY(false);
            this.onPressListener = new LinkSpanDrawable.LinksTextView.OnLinkPress() { // from class: org.telegram.ui.PhotoViewer$CaptionTextView$$ExternalSyntheticLambda0
                @Override // org.telegram.ui.Components.LinkSpanDrawable.LinksTextView.OnLinkPress
                public final void run(ClickableSpan clickableSpan) {
                    this.f$0.lambda$new$0(callback2, clickableSpan);
                }
            };
            this.onLongPressListener = new LinkSpanDrawable.LinksTextView.OnLinkPress() { // from class: org.telegram.ui.PhotoViewer$CaptionTextView$$ExternalSyntheticLambda1
                @Override // org.telegram.ui.Components.LinkSpanDrawable.LinksTextView.OnLinkPress
                public final void run(ClickableSpan clickableSpan) {
                    this.f$0.lambda$new$1(callback3, clickableSpan);
                }
            };
            this.textSelectionHelper = simpleTextSelectionHelper;
            ViewHelper.setPadding(this, 16.0f, 8.0f, 16.0f, 8.0f);
            setLinkTextColor(-8796932);
            setTextColor(-1);
            setHighlightColor(872415231);
            setGravity(LayoutHelper.getAbsoluteGravityStart() | 16);
            setTextSize(1, 16.0f);
            setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$CaptionTextView$$ExternalSyntheticLambda2
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PhotoViewer.CaptionTextView.$r8$lambda$68Zn0di2eVfMWIVJiaBZsioMkX4(captionScrollView, view);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(Utilities.Callback2 callback2, ClickableSpan clickableSpan) {
            callback2.run(clickableSpan, this);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(Utilities.Callback3 callback3, ClickableSpan clickableSpan) {
            callback3.run(clickableSpan, this, new Runnable() { // from class: org.telegram.ui.PhotoViewer$CaptionTextView$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.clearLinks();
                }
            });
        }

        public static /* synthetic */ void $r8$lambda$68Zn0di2eVfMWIVJiaBZsioMkX4(CaptionScrollView captionScrollView, View view) {
            if (captionScrollView != null) {
                captionScrollView.smoothScrollBy(0, AndroidUtilities.m1146dp(64.0f));
            }
        }

        @Override // android.view.View
        public void setPressed(boolean z) {
            boolean z2 = z != isPressed();
            super.setPressed(z);
            if (z2) {
                invalidate();
            }
        }

        @Override // org.telegram.p023ui.Components.spoilers.SpoilersTextView, android.widget.TextView, android.view.View
        protected void onDraw(Canvas canvas) {
            TextSelectionHelper.SimpleTextSelectionHelper simpleTextSelectionHelper = this.textSelectionHelper;
            if (simpleTextSelectionHelper != null && simpleTextSelectionHelper.isInSelectionMode()) {
                canvas.save();
                canvas.translate(getPaddingLeft(), getPaddingTop());
                if (this.textSelectionHelper != null && getStaticTextLayout() != null && this.textSelectionHelper.isCurrent(this)) {
                    this.textSelectionHelper.draw(canvas);
                }
                canvas.restore();
            }
            super.onDraw(canvas);
            if (this.lastLayout != getLayout()) {
                this.animatedEmojiDrawables = AnimatedEmojiSpan.update(0, this, this.animatedEmojiDrawables, getLayout());
                this.quoteBlocks = QuoteSpan.updateQuoteBlocksSpanned(getLayout(), this.quoteBlocks);
                boolean z = getLayout() != null && (getLayout().getText() instanceof Spanned) && ((QuoteSpan.QuoteStyleSpan[]) ((Spanned) getLayout().getText()).getSpans(0, getLayout().getText().length(), QuoteSpan.QuoteStyleSpan.class)).length > 0;
                this.hasQuote = z;
                ViewHelper.setPadding(this, 16.0f, 8.0f, (z ? 32 : 0) + 16, 8.0f);
                this.lastLayout = getLayout();
            }
        }

        @Override // android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            AnimatedEmojiSpan.release(this, this.animatedEmojiDrawables);
            this.quoteBlocks = QuoteSpan.updateQuoteBlocksSpanned(null, this.quoteBlocks);
        }

        @Override // org.telegram.p023ui.Components.spoilers.SpoilersTextView, android.widget.TextView
        protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            super.onTextChanged(charSequence, i, i2, i3);
            this.animatedEmojiDrawables = AnimatedEmojiSpan.update(0, this, this.animatedEmojiDrawables, getLayout());
            this.quoteBlocks = QuoteSpan.updateQuoteBlocksSpanned(getLayout(), this.quoteBlocks);
        }

        @Override // android.view.View
        protected void dispatchDraw(Canvas canvas) {
            if (this.loading) {
                checkLoadingPath();
                if (this.loadingDrawable == null) {
                    LoadingDrawable loadingDrawable = new LoadingDrawable();
                    this.loadingDrawable = loadingDrawable;
                    loadingDrawable.usePath(this.loadingPath);
                    this.loadingDrawable.setRadiiDp(4.0f);
                    this.loadingDrawable.setColors(Theme.multAlpha(-1, 0.3f), Theme.multAlpha(-1, 0.1f), Theme.multAlpha(-1, 0.2f), Theme.multAlpha(-1, 0.7f));
                    this.loadingDrawable.setCallback(this);
                }
                this.loadingDrawable.setBounds(0, 0, getWidth(), getHeight());
                this.loadingDrawable.draw(canvas);
            }
            if (this.loading) {
                canvas.saveLayerAlpha(0.0f, 0.0f, getWidth(), getHeight(), Opcodes.GETSTATIC, 31);
            }
            if (this.quoteBlocks != null && this.hasQuote) {
                canvas.save();
                canvas.translate(getPaddingLeft(), getPaddingTop());
                for (int i = 0; i < this.quoteBlocks.size(); i++) {
                    ((QuoteSpan.Block) this.quoteBlocks.get(i)).draw(canvas, 0.0f, (this.hasQuote ? AndroidUtilities.m1146dp(32.0f) : 0) + ((getWidth() - getPaddingLeft()) - getPaddingRight()), -1, 1.0f, getPaint());
                }
                canvas.restore();
            }
            super.dispatchDraw(canvas);
            if (this.loading) {
                canvas.restore();
            }
            canvas.save();
            canvas.translate(getPaddingLeft(), getPaddingTop());
            canvas.clipRect(0.0f, getScrollY(), getWidth() - getPaddingRight(), (getHeight() + getScrollY()) - (getPaddingBottom() * 0.75f));
            AnimatedEmojiSpan.drawAnimatedEmojis(canvas, getLayout(), this.animatedEmojiDrawables, 0.0f, null, 0.0f, 0.0f, 0.0f, 1.0f);
            canvas.restore();
        }

        public void setLoading(boolean z) {
            if (this.loading == z) {
                return;
            }
            this.loading = z;
            invalidate();
        }

        private void checkLoadingPath() {
            Layout layout = getLayout();
            Path path = this.loadingPath;
            if (path == null || this.lastLoadingLayout != layout) {
                if (path == null) {
                    this.loadingPath = new Path();
                } else {
                    path.rewind();
                }
                if (layout != null) {
                    float fM1146dp = AndroidUtilities.m1146dp(16.0f);
                    float fM1146dp2 = AndroidUtilities.m1146dp(8.0f);
                    float lineTop = 0.0f;
                    int i = 0;
                    while (i < layout.getLineCount()) {
                        float f = fM1146dp / 3.0f;
                        float lineLeft = layout.getLineLeft(i) - f;
                        float lineRight = layout.getLineRight(i) + f;
                        if (i == 0) {
                            lineTop = layout.getLineTop(i) - (fM1146dp2 / 3.0f);
                        }
                        float lineBottom = layout.getLineBottom(i);
                        if (i >= layout.getLineCount() - 1) {
                            lineBottom += fM1146dp2 / 3.0f;
                        }
                        this.loadingPath.addRect(getPaddingLeft() + lineLeft, getPaddingTop() + lineTop, getPaddingLeft() + lineRight, getPaddingTop() + lineBottom, Path.Direction.CW);
                        i++;
                        lineTop = lineBottom;
                    }
                }
                this.lastLoadingLayout = layout;
            }
        }

        @Override // android.widget.TextView, android.view.View
        protected boolean verifyDrawable(Drawable drawable) {
            return drawable == this.loadingDrawable || super.verifyDrawable(drawable);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getLeftInset() {
        return this.insets.left;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getRightInset() {
        return this.insets.right;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dismissInternal() {
        try {
            if (this.windowView.getParent() != null) {
                Activity activity = this.parentActivity;
                if (activity instanceof LaunchActivity) {
                    ((LaunchActivity) activity).drawerLayoutContainer.setAllowDrawContent(true);
                }
                ((WindowManager) this.parentActivity.getSystemService("window")).removeView(this.windowView);
                onHideView();
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchToPip(boolean z) {
        float translationX;
        float translationY;
        float translationY2;
        float f;
        boolean z2;
        Runnable runnable;
        CubicBezierInterpolator cubicBezierInterpolator;
        if (this.videoPlayer == null || !this.textureUploaded || !checkInlinePermissions() || this.changingTextureView || this.switchingInlineMode || this.isInline) {
            return;
        }
        if (PipInstance != null) {
            PipInstance.destroyPhotoViewer();
        }
        this.openedFullScreenVideo = false;
        PipInstance = Instance;
        Instance = null;
        this.switchingInlineMode = true;
        this.isVisible = false;
        this.isVisibleOrAnimating = false;
        AndroidUtilities.cancelRunOnUIThread(this.hideActionBarRunnable);
        PlaceProviderObject placeProviderObject = this.currentPlaceObject;
        if (placeProviderObject != null && !placeProviderObject.imageReceiver.getVisible()) {
            this.currentPlaceObject.imageReceiver.setVisible(true, true);
            final AnimatedFileDrawable animation = this.currentPlaceObject.imageReceiver.getAnimation();
            if (animation != null) {
                Bitmap animatedBitmap = animation.getAnimatedBitmap();
                if (animatedBitmap != null) {
                    try {
                        if (this.usedSurfaceView && Build.VERSION.SDK_INT >= 24) {
                            final Bitmap bitmapCreateBitmap = Bitmap.createBitmap(animatedBitmap);
                            AndroidUtilities.getBitmapFromSurface(this.videoSurfaceView, bitmapCreateBitmap, new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda99
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PhotoViewer.m15267$r8$lambda$2VpYp4m2quzOfU987jeRA0dr_w(animation, bitmapCreateBitmap);
                                }
                            });
                        } else {
                            Bitmap bitmap = this.videoTextureView.getBitmap(animatedBitmap.getWidth(), animatedBitmap.getHeight());
                            new Canvas(animatedBitmap).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
                            bitmap.recycle();
                        }
                    } catch (Throwable th) {
                        FileLog.m1160e(th);
                    }
                }
                animation.seekTo(this.videoPlayer.getCurrentPosition(), true);
                if (z) {
                    this.currentPlaceObject.imageReceiver.setAlpha(0.0f);
                    final ImageReceiver imageReceiver = this.currentPlaceObject.imageReceiver;
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda100
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            imageReceiver.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                        }
                    });
                    valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.43
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            imageReceiver.setAlpha(1.0f);
                        }
                    });
                    valueAnimatorOfFloat.setDuration(250L);
                    valueAnimatorOfFloat.start();
                }
                this.currentPlaceObject.imageReceiver.setAllowStartAnimation(true);
                this.currentPlaceObject.imageReceiver.startAnimation();
            }
        }
        this.pipAnimationInProgress = true;
        org.telegram.p023ui.Components.Rect pipRect = PipVideoOverlay.getPipRect(true, this.aspectRatioFrameLayout.getAspectRatio());
        final View view = this.usedSurfaceView ? this.videoSurfaceView : this.videoTextureView;
        final float width = pipRect.width / view.getWidth();
        final ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        if (this.usedSurfaceView) {
            translationX = this.aspectRatioFrameLayout.getLeft() + view.getTranslationX();
            translationY = this.aspectRatioFrameLayout.getTop() + view.getTranslationY() + this.translationY;
            translationY2 = this.aspectRatioFrameLayout.getTop() + this.textureImageView.getTranslationY();
            f = this.translationY;
        } else {
            translationX = view.getTranslationX();
            translationY = view.getTranslationY() + this.translationY;
            translationY2 = this.textureImageView.getTranslationY();
            f = this.translationY;
        }
        final float f2 = translationY2 + f;
        final float f3 = translationY;
        final float f4 = translationX;
        final float f5 = pipRect.f1970x;
        final float leftInset = getLeftInset() + (f5 - this.aspectRatioFrameLayout.getX());
        final float f6 = pipRect.f1971y;
        final float y = f6 - this.aspectRatioFrameLayout.getY();
        final ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: org.telegram.ui.PhotoViewer.44
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view2, Outline outline) {
                outline.setRoundRect(0, 0, view2.getMeasuredWidth(), view2.getMeasuredHeight(), ((Float) valueAnimatorOfFloat2.getAnimatedValue()).floatValue() * AndroidUtilities.m1146dp(10.0f) * (1.0f / width));
            }
        };
        final AnimatorSet animatorSet = new AnimatorSet();
        final Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda101
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$switchToPip$91(view, viewOutlineProvider, f2, f3, animatorSet);
            }
        };
        if (this.videoSurfaceView != null) {
            this.videoPlayer.player.pause();
            if (this.usedSurfaceView) {
                final Bitmap bitmapCreateBitmap2 = Bitmaps.createBitmap(this.videoSurfaceView.getWidth(), this.videoSurfaceView.getHeight(), Bitmap.Config.ARGB_8888);
                final boolean[] zArr = new boolean[1];
                AndroidUtilities.getBitmapFromSurface(this.videoSurfaceView, bitmapCreateBitmap2, new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda102
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$switchToPip$92(bitmapCreateBitmap2, zArr, runnable2);
                    }
                });
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda103
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$switchToPip$93(zArr, runnable2);
                    }
                }, 600L);
                z2 = true;
            } else {
                this.textureImageView.setVisibility(0);
                this.videoSurfaceView.setVisibility(4);
                z2 = false;
            }
        } else {
            z2 = false;
        }
        if (!z) {
            runnable = runnable2;
            cubicBezierInterpolator = null;
        } else if (f3 < y) {
            runnable = runnable2;
            cubicBezierInterpolator = new CubicBezierInterpolator(0.5d, 0.0d, 0.9d, 0.9d);
        } else {
            runnable = runnable2;
            cubicBezierInterpolator = new CubicBezierInterpolator(0.0d, 0.5d, 0.9d, 0.9d);
        }
        Runnable runnable3 = runnable;
        final View view2 = view;
        final CubicBezierInterpolator cubicBezierInterpolator2 = cubicBezierInterpolator;
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda104
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$switchToPip$94(cubicBezierInterpolator2, f4, f5, f2, f6, view2, leftInset, f3, y, valueAnimator);
            }
        });
        ValueAnimator valueAnimatorOfFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda105
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$switchToPip$95(valueAnimator);
            }
        });
        ImageView imageView = this.textureImageView;
        Property property = View.SCALE_X;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(imageView, (Property<ImageView, Float>) property, width);
        ImageView imageView2 = this.textureImageView;
        Property property2 = View.SCALE_Y;
        animatorSet.playTogether(valueAnimatorOfFloat3, objectAnimatorOfFloat, ObjectAnimator.ofFloat(imageView2, (Property<ImageView, Float>) property2, width), ObjectAnimator.ofFloat(view2, (Property<View, Float>) property, width), ObjectAnimator.ofFloat(view2, (Property<View, Float>) property2, width), ObjectAnimator.ofInt(this.backgroundDrawable, (Property<BackgroundDrawable, Integer>) AnimationProperties.COLOR_DRAWABLE_ALPHA, 0), valueAnimatorOfFloat2);
        if (z) {
            animatorSet.setInterpolator(CubicBezierInterpolator.EASE_OUT);
            animatorSet.setDuration(300L);
        } else {
            animatorSet.setInterpolator(new DecelerateInterpolator());
            animatorSet.setDuration(250L);
        }
        animatorSet.addListener(new C581745(view2));
        if (!z2) {
            runnable3.run();
        }
        if (!z) {
            toggleActionBar(false, true, new ActionBarToggleParams().enableStatusBarAnimation(false).enableTranslationAnimation(false).animationDuration(MediaDataController.MAX_LINKS_COUNT).animationInterpolator(new DecelerateInterpolator()));
        }
        ChatActivity chatActivity = this.parentChatActivity;
        if (chatActivity == null || chatActivity.getFragmentView() == null) {
            return;
        }
        this.parentChatActivity.getFragmentView().invalidate();
    }

    /* renamed from: $r8$lambda$2VpYp4m2quzOfU987jeRA-0dr_w, reason: not valid java name */
    public static /* synthetic */ void m15267$r8$lambda$2VpYp4m2quzOfU987jeRA0dr_w(AnimatedFileDrawable animatedFileDrawable, Bitmap bitmap) {
        if (animatedFileDrawable == null) {
            return;
        }
        animatedFileDrawable.replaceAnimatedBitmap(bitmap);
        animatedFileDrawable.invalidateInternal();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToPip$91(View view, ViewOutlineProvider viewOutlineProvider, float f, float f2, AnimatorSet animatorSet) {
        view.setOutlineProvider(viewOutlineProvider);
        view.setClipToOutline(true);
        this.textureImageView.setOutlineProvider(viewOutlineProvider);
        this.textureImageView.setClipToOutline(true);
        FirstFrameView firstFrameView = this.firstFrameView;
        if (firstFrameView != null) {
            firstFrameView.setOutlineProvider(viewOutlineProvider);
            this.firstFrameView.setClipToOutline(true);
        }
        this.textureImageView.setTranslationY(f);
        view.setTranslationY(f2);
        FirstFrameView firstFrameView2 = this.firstFrameView;
        if (firstFrameView2 != null) {
            firstFrameView2.setTranslationY(f2);
        }
        this.translationY = 0.0f;
        this.containerView.invalidate();
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToPip$92(Bitmap bitmap, boolean[] zArr, Runnable runnable) {
        ImageView imageView = this.textureImageView;
        if (imageView != null) {
            imageView.setImageBitmap(bitmap);
            this.textureImageView.setVisibility(0);
            SurfaceView surfaceView = this.videoSurfaceView;
            if (surfaceView != null) {
                surfaceView.setVisibility(4);
            }
            if (zArr[0]) {
                return;
            }
            zArr[0] = true;
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToPip$93(boolean[] zArr, Runnable runnable) {
        if (zArr[0]) {
            return;
        }
        ImageView imageView = this.textureImageView;
        if (imageView != null) {
            imageView.setVisibility(0);
        }
        SurfaceView surfaceView = this.videoSurfaceView;
        if (surfaceView != null) {
            surfaceView.setVisibility(4);
        }
        zArr[0] = true;
        runnable.run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToPip$94(CubicBezierInterpolator cubicBezierInterpolator, float f, float f2, float f3, float f4, View view, float f5, float f6, float f7, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float interpolation = cubicBezierInterpolator == null ? fFloatValue : cubicBezierInterpolator.getInterpolation(fFloatValue);
        ImageView imageView = this.textureImageView;
        if (imageView != null) {
            imageView.setTranslationX(((1.0f - fFloatValue) * f) + (f2 * fFloatValue));
            this.textureImageView.setTranslationY((f3 * (1.0f - interpolation)) + (f4 * interpolation));
            this.textureImageView.invalidateOutline();
        }
        view.setTranslationX((f * (1.0f - fFloatValue)) + (f5 * fFloatValue));
        view.setTranslationY((f6 * (1.0f - interpolation)) + (f7 * interpolation));
        view.invalidateOutline();
        FirstFrameView firstFrameView = this.firstFrameView;
        if (firstFrameView != null) {
            firstFrameView.setTranslationX(view.getTranslationX());
            this.firstFrameView.setTranslationY(view.getTranslationY());
            this.firstFrameView.setScaleX(view.getScaleX());
            this.firstFrameView.setScaleY(view.getScaleY());
            this.firstFrameView.invalidateOutline();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToPip$95(ValueAnimator valueAnimator) {
        this.clippingImageProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
    }

    /* renamed from: org.telegram.ui.PhotoViewer$45 */
    /* loaded from: classes5.dex */
    class C581745 extends AnimatorListenerAdapter {
        final /* synthetic */ View val$textureView;

        C581745(View view) {
            this.val$textureView = view;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            PhotoViewer.this.pipAnimationInProgress = false;
            PhotoViewer.this.switchToInlineRunnable.run();
            final View view = this.val$textureView;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$45$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAnimationEnd$0(view);
                }
            }, 100L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAnimationEnd$0(View view) {
            view.setOutlineProvider(null);
            if (PhotoViewer.this.textureImageView != null) {
                PhotoViewer.this.textureImageView.setOutlineProvider(null);
            }
            if (PhotoViewer.this.firstFrameView != null) {
                PhotoViewer.this.firstFrameView.setOutlineProvider(null);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean cropMirror() {
        if (this.imageMoveAnimation != null || this.photoCropView == null) {
            return false;
        }
        this.mirror = 0.0f;
        this.animateToMirror = 1.0f;
        this.animationStartTime = System.currentTimeMillis();
        AnimatorSet animatorSet = new AnimatorSet();
        this.imageMoveAnimation = animatorSet;
        animatorSet.playTogether(ObjectAnimator.ofFloat(this, (Property<PhotoViewer, Float>) AnimationProperties.PHOTO_VIEWER_ANIMATION_VALUE, 0.0f, 1.0f));
        this.imageMoveAnimation.setDuration(250L);
        this.imageMoveAnimation.setInterpolator(CubicBezierInterpolator.DEFAULT);
        this.imageMoveAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.46
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PhotoViewer.this.imageMoveAnimation = null;
                if (PhotoViewer.this.photoCropView == null) {
                    return;
                }
                if (PhotoViewer.this.photoCropView.mirror()) {
                    PhotoViewer.this.mirrorItem.setColorFilter(new PorterDuffColorFilter(PhotoViewer.this.getThemedColor(Theme.key_chat_editMediaButton), PorterDuff.Mode.MULTIPLY));
                } else {
                    PhotoViewer.this.mirrorItem.setColorFilter((ColorFilter) null);
                }
                PhotoViewer photoViewer = PhotoViewer.this;
                photoViewer.animateToMirror = 0.0f;
                photoViewer.mirror = 0.0f;
                PhotoViewer.this.containerView.invalidate();
            }
        });
        this.imageMoveAnimation.start();
        return !this.photoCropView.cropView.isMirrored();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean cropRotate(float f) {
        return cropRotate(f, false, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float scale1() {
        return scale1(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float scale1(boolean z) {
        int i;
        if (this.sendPhotoType != 11) {
            return 1.0f;
        }
        int containerViewWidth = getContainerViewWidth();
        if (containerViewWidth == 0) {
            containerViewWidth = AndroidUtilities.displaySize.x;
        }
        float fM1146dp = ((containerViewWidth - AndroidUtilities.m1146dp(20.0f)) + 1) / containerViewWidth;
        if (!z) {
            return fM1146dp;
        }
        int bitmapWidth = this.centerImage.getBitmapWidth();
        int bitmapHeight = this.centerImage.getBitmapHeight();
        if ((bitmapWidth <= 1 || bitmapHeight <= 1) && (i = this.currentIndex) >= 0 && i < this.imagesArrLocals.size()) {
            Object obj = this.imagesArrLocals.get(this.currentIndex);
            if (obj instanceof MediaController.PhotoEntry) {
                MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) obj;
                if ((photoEntry.orientation / 90) % 2 != 0) {
                    bitmapWidth = photoEntry.height;
                    bitmapHeight = photoEntry.width;
                } else {
                    bitmapWidth = photoEntry.width;
                    bitmapHeight = photoEntry.height;
                }
            }
        }
        return (bitmapWidth <= 1 || bitmapHeight <= 1 || bitmapWidth <= bitmapHeight) ? fM1146dp : fM1146dp * (bitmapWidth / bitmapHeight);
    }

    private boolean cropRotate(final float f, boolean z, final Runnable runnable) {
        PhotoCropView photoCropView;
        if (this.imageMoveAnimation != null || (photoCropView = this.photoCropView) == null) {
            return false;
        }
        photoCropView.cropView.maximize(true);
        this.rotate = 0.0f;
        this.animateToRotate = 0.0f + f;
        if (z) {
            this.mirror = 0.0f;
            this.animateToMirror = 1.0f;
        }
        this.animationStartTime = System.currentTimeMillis();
        this.imageMoveAnimation = new AnimatorSet();
        if (this.sendPhotoType == 1) {
            this.animateToScale = 1.0f;
            this.scale = 1.0f;
        } else {
            ImageReceiver imageReceiver = this.centerImage;
            if (imageReceiver != null) {
                int bitmapWidth = imageReceiver.getBitmapWidth();
                int bitmapHeight = this.centerImage.getBitmapHeight();
                if (Math.abs((((int) this.photoCropView.cropView.getStateOrientation()) / 90) % 2) == 1) {
                    bitmapHeight = bitmapWidth;
                    bitmapWidth = bitmapHeight;
                }
                MediaController.CropState cropState = this.editState.cropState;
                if (cropState != null) {
                    bitmapWidth = (int) (bitmapWidth * cropState.cropPw);
                    bitmapHeight = (int) (bitmapHeight * cropState.cropPh);
                }
                float f2 = bitmapWidth;
                float f3 = bitmapHeight;
                float fMin = Math.min(getContainerViewWidth(1) / f2, getContainerViewHeight(1) / f3);
                this.animateToScale = (Math.abs((f / 90.0f) % 2.0f) == 1.0f ? Math.min(getContainerViewWidth(1) / f3, getContainerViewHeight(1) / f2) : fMin) / fMin;
            }
        }
        if (this.sendPhotoType == 11) {
            this.scale *= scale1(false);
            this.animateToScale *= scale1(false);
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        final float rotation = this.photoCropView.wheelView.getRotation();
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda73
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$cropRotate$96(f, rotation, valueAnimator);
            }
        });
        this.imageMoveAnimation.playTogether(ObjectAnimator.ofFloat(this, (Property<PhotoViewer, Float>) AnimationProperties.PHOTO_VIEWER_ANIMATION_VALUE, 0.0f, 1.0f), valueAnimatorOfFloat);
        this.imageMoveAnimation.setDuration(250L);
        this.imageMoveAnimation.setInterpolator(CubicBezierInterpolator.DEFAULT);
        this.imageMoveAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.47
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PhotoViewer.this.imageMoveAnimation = null;
                PhotoViewer photoViewer = PhotoViewer.this;
                photoViewer.animateToRotate = 0.0f;
                photoViewer.rotate = 0.0f;
                PhotoViewer photoViewer2 = PhotoViewer.this;
                photoViewer2.animateToMirror = 0.0f;
                photoViewer2.mirror = 0.0f;
                PhotoViewer photoViewer3 = PhotoViewer.this;
                float fScale1 = photoViewer3.scale1(false);
                photoViewer3.animateToScale = fScale1;
                photoViewer3.scale = fScale1;
                PhotoViewer.this.containerView.invalidate();
                PhotoViewer.this.photoCropView.cropView.areaView.setRotationScaleTranslation(0.0f, PhotoViewer.this.scale1(false), 0.0f, 0.0f);
                PhotoViewer.this.photoCropView.wheelView.setRotated(false);
                if (Math.abs(f) > 0.0f) {
                    if (PhotoViewer.this.photoCropView.rotate(f)) {
                        PhotoViewer.this.rotateItem.setColorFilter(new PorterDuffColorFilter(PhotoViewer.this.getThemedColor(Theme.key_chat_editMediaButton), PorterDuff.Mode.MULTIPLY));
                    } else {
                        PhotoViewer.this.rotateItem.setColorFilter((ColorFilter) null);
                    }
                }
                if (PhotoViewer.this.editState.cropState != null) {
                    MediaController.CropState cropState2 = PhotoViewer.this.editState.cropState;
                    PhotoViewer.this.editState.cropState.cropPy = 0.0f;
                    cropState2.cropPx = 0.0f;
                    MediaController.CropState cropState3 = PhotoViewer.this.editState.cropState;
                    PhotoViewer.this.editState.cropState.cropPh = 1.0f;
                    cropState3.cropPw = 1.0f;
                }
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        this.imageMoveAnimation.start();
        return Math.abs(this.photoCropView.cropView.getStateOrientation() + f) > 0.01f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cropRotate$96(float f, float f2, ValueAnimator valueAnimator) {
        CropAreaView cropAreaView = this.photoCropView.cropView.areaView;
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue() * f;
        float f3 = this.scale;
        cropAreaView.setRotationScaleTranslation(fFloatValue, f3 + ((this.animateToScale - f3) * this.animationValue), 0.0f, 0.0f);
        this.photoCropView.wheelView.setRotation(AndroidUtilities.lerp(f2, 0.0f, ((Float) valueAnimator.getAnimatedValue()).floatValue()), false);
    }

    public VideoPlayer getVideoPlayer() {
        return this.videoPlayer;
    }

    public void exitFromPip() throws NumberFormatException {
        if (this.isInline) {
            if (Instance != null) {
                Instance.closePhoto(false, true);
            }
            PhotoViewerWebView photoViewerWebView = this.photoViewerWebView;
            if (photoViewerWebView != null) {
                photoViewerWebView.exitFromPip();
            }
            Instance = PipInstance;
            PipInstance = null;
            if (this.photoViewerWebView == null) {
                this.switchingInlineMode = true;
                Bitmap bitmap = this.currentBitmap;
                if (bitmap != null) {
                    bitmap.recycle();
                    this.currentBitmap = null;
                }
                this.changingTextureView = true;
            }
            this.isInline = false;
            View view = this.usedSurfaceView ? this.videoSurfaceView : this.videoTextureView;
            if (this.photoViewerWebView == null && view != null) {
                AndroidUtilities.removeFromParent(view);
                view.setVisibility(4);
                this.aspectRatioFrameLayout.addView(view);
            }
            if (ApplicationLoader.mainInterfacePaused) {
                try {
                    this.parentActivity.startService(new Intent(ApplicationLoader.applicationContext, (Class<?>) BringAppForegroundService.class));
                } catch (Throwable th) {
                    FileLog.m1160e(th);
                }
            }
            if (this.photoViewerWebView != null) {
                this.clippingImageProgress = 0.0f;
            } else if (view != null) {
                this.pipAnimationInProgress = true;
                org.telegram.p023ui.Components.Rect pipRect = PipVideoOverlay.getPipRect(false, this.aspectRatioFrameLayout.getAspectRatio());
                final float f = pipRect.width / this.textureImageView.getLayoutParams().width;
                this.textureImageView.setScaleX(f);
                this.textureImageView.setScaleY(f);
                this.textureImageView.setTranslationX(pipRect.f1970x);
                this.textureImageView.setTranslationY(pipRect.f1971y);
                view.setScaleX(f);
                view.setScaleY(f);
                view.setTranslationX(pipRect.f1970x - this.aspectRatioFrameLayout.getX());
                view.setTranslationY(pipRect.f1971y - this.aspectRatioFrameLayout.getY());
                FirstFrameView firstFrameView = this.firstFrameView;
                if (firstFrameView != null) {
                    firstFrameView.setScaleX(f);
                    this.firstFrameView.setScaleY(f);
                    this.firstFrameView.setTranslationX(view.getTranslationX());
                    this.firstFrameView.setTranslationY(view.getTranslationY());
                }
                this.inlineOutAnimationProgress = 0.0f;
                ViewOutlineProvider viewOutlineProvider = new ViewOutlineProvider() { // from class: org.telegram.ui.PhotoViewer.48
                    @Override // android.view.ViewOutlineProvider
                    public void getOutline(View view2, Outline outline) {
                        outline.setRoundRect(0, 0, view2.getMeasuredWidth(), view2.getMeasuredHeight(), (1.0f - PhotoViewer.this.inlineOutAnimationProgress) * AndroidUtilities.m1146dp(10.0f) * (1.0f / f));
                    }
                };
                view.setOutlineProvider(viewOutlineProvider);
                view.setClipToOutline(true);
                this.textureImageView.setOutlineProvider(viewOutlineProvider);
                this.textureImageView.setClipToOutline(true);
                FirstFrameView firstFrameView2 = this.firstFrameView;
                if (firstFrameView2 != null) {
                    firstFrameView2.setOutlineProvider(viewOutlineProvider);
                    this.firstFrameView.setClipToOutline(true);
                }
            } else {
                PipVideoOverlay.dismiss(true);
            }
            try {
                this.isVisible = true;
                this.isVisibleOrAnimating = true;
                ((WindowManager) this.parentActivity.getSystemService("window")).addView(this.windowView, this.windowLayoutParams);
                onShowView();
                PlaceProviderObject placeProviderObject = this.currentPlaceObject;
                if (placeProviderObject != null) {
                    placeProviderObject.imageReceiver.setVisible(false, false);
                }
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
            if (this.usedSurfaceView) {
                ExoPlayer exoPlayer = this.videoPlayer.player;
                if (exoPlayer != null) {
                    exoPlayer.setVideoTextureView(null);
                }
                this.videoPlayer.setSurfaceView(this.videoSurfaceView);
                this.videoSurfaceView.setVisibility(4);
                this.waitingForFirstTextureUpload = 2;
                this.changingTextureView = false;
                this.containerView.invalidate();
                this.waitingForDraw = 4;
                return;
            }
            this.waitingForDraw = 4;
        }
    }

    private void onShowView() {
        Activity activity = this.parentActivity;
        if (activity instanceof LaunchActivity) {
            ((LaunchActivity) activity).addOnUserLeaveHintListener(this.onUserLeaveHintListener);
        }
    }

    private void onHideView() {
        Activity activity = this.parentActivity;
        if (activity instanceof LaunchActivity) {
            ((LaunchActivity) activity).removeOnUserLeaveHintListener(this.onUserLeaveHintListener);
        }
        BaseFragment baseFragment = this.parentFragment;
        if (baseFragment != null && baseFragment.getFragmentView() != null) {
            this.clippingImageProgress = 1.0f;
            View fragmentView = this.parentFragment.getFragmentView();
            fragmentView.setScaleX(1.0f);
            fragmentView.setScaleY(1.0f);
            ChatAttachAlert chatAttachAlert = this.parentAlert;
            if (chatAttachAlert != null) {
                BottomSheet.ContainerView container = chatAttachAlert.getContainer();
                container.setScaleX(1.0f);
                container.setScaleY(1.0f);
            }
        }
        this.resourcesProvider = null;
        this.currentPlaceObject = null;
        this.hideAfterAnimation = null;
        this.showAfterAnimation = null;
        this.hasCaptionForAllMedia = false;
        this.captionForAllMedia = null;
        PhotoCropView photoCropView = this.photoCropView;
        if (photoCropView != null) {
            this.containerView.removeView(photoCropView);
            this.photoCropView = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserLeaveHint() {
        if (this.pipItem.isEnabled() && AndroidUtilities.checkInlinePermissions(this.parentActivity) && !PipVideoOverlay.isVisible() && this.isPlaying) {
            if (this.isEmbedVideo) {
                PhotoViewerWebView photoViewerWebView = this.photoViewerWebView;
                if (photoViewerWebView == null || photoViewerWebView.isInAppOnly() || !this.photoViewerWebView.openInPip()) {
                    return;
                }
                this.pipVideoOverlayAnimateFlag = false;
                if (PipInstance != null) {
                    PipInstance.destroyPhotoViewer();
                }
                this.isInline = true;
                PipInstance = Instance;
                Instance = null;
                this.isVisible = false;
                PlaceProviderObject placeProviderObject = this.currentPlaceObject;
                if (placeProviderObject != null && !placeProviderObject.imageReceiver.getVisible()) {
                    this.currentPlaceObject.imageReceiver.setVisible(true, true);
                }
                this.clippingImageProgress = 1.0f;
                this.containerView.invalidate();
                dismissInternal();
                return;
            }
            this.pipVideoOverlayAnimateFlag = false;
            if (ExteraConfig.pauseOnMinimizeVideo) {
                return;
            }
            switchToPip(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVideoSeekPreviewPosition() {
        int thumbX = (this.videoPlayerSeekbar.getThumbX() + AndroidUtilities.m1146dp(2.0f)) - (this.videoPreviewFrame.getMeasuredWidth() / 2);
        int iM1146dp = AndroidUtilities.m1146dp(10.0f);
        int measuredWidth = (this.videoPlayerControlFrameLayout.getMeasuredWidth() - AndroidUtilities.m1146dp(10.0f)) - (this.videoPreviewFrame.getMeasuredWidth() / 2);
        if (thumbX < iM1146dp) {
            this.videoPreviewFrame.setPivotX(Utilities.clamp((r4.getMeasuredWidth() / 2.0f) - (iM1146dp - thumbX), this.videoPreviewFrame.getMeasuredWidth(), 0.0f));
            thumbX = iM1146dp;
        } else if (thumbX >= measuredWidth) {
            this.videoPreviewFrame.setPivotX(Utilities.clamp((r3.getMeasuredWidth() / 2.0f) + (thumbX - measuredWidth), this.videoPreviewFrame.getMeasuredWidth(), 0.0f));
            thumbX = measuredWidth;
        } else {
            this.videoPreviewFrame.setPivotX(r2.getMeasuredWidth() / 2.0f);
        }
        this.videoPreviewFrame.setTranslationX(thumbX);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showVideoSeekPreviewPosition(boolean z) {
        PhotoViewerWebView photoViewerWebView;
        if (!z || this.videoPreviewFrame.getTag() == null) {
            if (z || this.videoPreviewFrame.getTag() != null) {
                if (z && !this.videoPreviewFrame.isReady() && ((photoViewerWebView = this.photoViewerWebView) == null || !photoViewerWebView.isYouTube() || !this.photoViewerWebView.hasYoutubeStoryboards())) {
                    this.needShowOnReady = true;
                    return;
                }
                AnimatorSet animatorSet = this.videoPreviewFrameAnimation;
                if (animatorSet != null) {
                    animatorSet.cancel();
                }
                this.videoPreviewFrame.setTag(z ? 1 : null);
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.videoPreviewFrameAnimation = animatorSet2;
                animatorSet2.playTogether(ObjectAnimator.ofFloat(this.videoPreviewFrame, (Property<VideoSeekPreviewImage, Float>) View.ALPHA, z ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.videoPreviewFrame, (Property<VideoSeekPreviewImage, Float>) View.SCALE_X, z ? 1.0f : 0.5f), ObjectAnimator.ofFloat(this.videoPreviewFrame, (Property<VideoSeekPreviewImage, Float>) View.SCALE_Y, z ? 1.0f : 0.5f), ObjectAnimator.ofFloat(this.videoPreviewFrame, (Property<VideoSeekPreviewImage, Float>) View.TRANSLATION_Y, z ? 0.0f : AndroidUtilities.m1146dp(12.0f)));
                this.videoPreviewFrameAnimation.setDuration(380L);
                this.videoPreviewFrameAnimation.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                this.videoPreviewFrameAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.49
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        PhotoViewer.this.videoPreviewFrameAnimation = null;
                    }
                });
                this.videoPreviewFrameAnimation.start();
            }
        }
    }

    private void createVideoControlsInterface() {
        VideoPlayerControlFrameLayout videoPlayerControlFrameLayout = new VideoPlayerControlFrameLayout(this.containerView.getContext());
        this.videoPlayerControlFrameLayout = videoPlayerControlFrameLayout;
        this.containerView.addView(videoPlayerControlFrameLayout, LayoutHelper.createFrame(-1, 48, 83));
        final VideoPlayerSeekBar.SeekBarDelegate seekBarDelegate = new VideoPlayerSeekBar.SeekBarDelegate() { // from class: org.telegram.ui.PhotoViewer.50
            @Override // org.telegram.ui.Components.VideoPlayerSeekBar.SeekBarDelegate
            public void onSeekBarDrag(float f) {
                if (PhotoViewer.this.videoPlayer != null || (PhotoViewer.this.photoViewerWebView != null && PhotoViewer.this.photoViewerWebView.isControllable())) {
                    if (!PhotoViewer.this.inPreview && PhotoViewer.this.videoTimelineViewContainer.getVisibility() == 0) {
                        f = PhotoViewer.this.videoTimelineView.getLeftProgress() + ((PhotoViewer.this.videoTimelineView.getRightProgress() - PhotoViewer.this.videoTimelineView.getLeftProgress()) * f);
                    }
                    if (PhotoViewer.this.getVideoDuration() == -9223372036854775807L) {
                        PhotoViewer.this.seekToProgressPending = f;
                    } else {
                        PhotoViewer.this.seekVideoOrWebTo((int) (f * r0));
                    }
                    PhotoViewer.this.showVideoSeekPreviewPosition(false);
                    PhotoViewer.this.needShowOnReady = false;
                }
            }

            @Override // org.telegram.ui.Components.VideoPlayerSeekBar.SeekBarDelegate
            public void onSeekBarContinuousDrag(float f) {
                if (PhotoViewer.this.photoViewerWebView != null && PhotoViewer.this.photoViewerWebView.isYouTube() && PhotoViewer.this.videoPreviewFrame != null) {
                    PhotoViewer.this.videoPreviewFrame.setProgressForYouTube(PhotoViewer.this.photoViewerWebView, f, PhotoViewer.this.videoPlayerSeekbar.getWidth());
                } else if (PhotoViewer.this.videoPlayer != null && PhotoViewer.this.videoPreviewFrame != null) {
                    PhotoViewer.this.videoPreviewFrame.setProgress(PhotoViewer.this.currentMessageObject, f, PhotoViewer.this.videoPlayerSeekbar.getWidth());
                }
                PhotoViewer.this.showVideoSeekPreviewPosition(true);
                PhotoViewer.this.updateVideoSeekPreviewPosition();
            }
        };
        FloatSeekBarAccessibilityDelegate floatSeekBarAccessibilityDelegate = new FloatSeekBarAccessibilityDelegate() { // from class: org.telegram.ui.PhotoViewer.51
            @Override // org.telegram.p023ui.Components.FloatSeekBarAccessibilityDelegate
            public float getProgress() {
                return PhotoViewer.this.videoPlayerSeekbar.getProgress();
            }

            @Override // org.telegram.p023ui.Components.FloatSeekBarAccessibilityDelegate
            public void setProgress(float f) {
                seekBarDelegate.onSeekBarDrag(f);
                PhotoViewer.this.videoPlayerSeekbar.setProgress(f);
                PhotoViewer.this.videoPlayerSeekbarView.invalidate();
            }

            @Override // org.telegram.p023ui.Components.SeekBarAccessibilityDelegate
            public String getContentDescription(View view) {
                return LocaleController.formatString("AccDescrPlayerDuration", C2369R.string.AccDescrPlayerDuration, LocaleController.formatPluralString("Minutes", PhotoViewer.this.videoPlayerCurrentTime[0], new Object[0]) + ' ' + LocaleController.formatPluralString("Seconds", PhotoViewer.this.videoPlayerCurrentTime[1], new Object[0]), LocaleController.formatPluralString("Minutes", PhotoViewer.this.videoPlayerTotalTime[0], new Object[0]) + ' ' + LocaleController.formatPluralString("Seconds", PhotoViewer.this.videoPlayerTotalTime[1], new Object[0]));
            }
        };
        View view = new View(this.containerView.getContext()) { // from class: org.telegram.ui.PhotoViewer.52
            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                PhotoViewer.this.videoPlayerSeekbar.draw(canvas, this);
            }
        };
        this.videoPlayerSeekbarView = view;
        view.setAccessibilityDelegate(floatSeekBarAccessibilityDelegate);
        this.videoPlayerSeekbarView.setImportantForAccessibility(1);
        this.videoPlayerControlFrameLayout.addView(this.videoPlayerSeekbarView, LayoutHelper.createFrame(-1, -1.0f));
        VideoPlayerSeekBar videoPlayerSeekBar = new VideoPlayerSeekBar(this.videoPlayerSeekbarView);
        this.videoPlayerSeekbar = videoPlayerSeekBar;
        videoPlayerSeekBar.setHorizontalPadding(AndroidUtilities.m1146dp(2.0f));
        this.videoPlayerSeekbar.setColors(872415231, 872415231, -1, -1, -1, 1509949439);
        this.videoPlayerSeekbar.setDelegate(seekBarDelegate);
        VideoSeekPreviewImage videoSeekPreviewImage = new VideoSeekPreviewImage(this.containerView.getContext(), new VideoSeekPreviewImage.VideoSeekPreviewImageDelegate() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda81
            @Override // org.telegram.ui.Components.VideoSeekPreviewImage.VideoSeekPreviewImageDelegate
            public final void onReady() {
                this.f$0.lambda$createVideoControlsInterface$97();
            }
        }) { // from class: org.telegram.ui.PhotoViewer.53
            @Override // android.view.View
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                PhotoViewer.this.updateVideoSeekPreviewPosition();
            }

            @Override // android.view.View
            public void setVisibility(int i) {
                super.setVisibility(i);
                if (i == 0) {
                    PhotoViewer.this.updateVideoSeekPreviewPosition();
                }
            }
        };
        this.videoPreviewFrame = videoSeekPreviewImage;
        videoSeekPreviewImage.setAlpha(0.0f);
        this.containerView.addView(this.videoPreviewFrame, LayoutHelper.createFrame(-2, -2.0f, 83, 0.0f, 0.0f, 0.0f, 58.0f));
        SimpleTextView simpleTextView = new SimpleTextView(this.containerView.getContext());
        this.videoPlayerTime = simpleTextView;
        simpleTextView.setTextColor(-1);
        this.videoPlayerTime.setGravity(53);
        this.videoPlayerTime.setTextSize(14);
        this.videoPlayerTime.setImportantForAccessibility(2);
        this.videoPlayerControlFrameLayout.addView(this.videoPlayerTime, LayoutHelper.createFrame(-2, -2.0f, 53, 0.0f, 15.0f, 12.0f, 0.0f));
        ImageView imageView = new ImageView(this.containerView.getContext());
        this.exitFullscreenButton = imageView;
        imageView.setImageResource(C2369R.drawable.msg_minvideo);
        this.exitFullscreenButton.setContentDescription(LocaleController.getString("AccExitFullscreen", C2369R.string.AccExitFullscreen));
        this.exitFullscreenButton.setScaleType(ImageView.ScaleType.CENTER);
        this.exitFullscreenButton.setBackground(Theme.createSelectorDrawable(1090519039));
        this.exitFullscreenButton.setVisibility(4);
        this.videoPlayerControlFrameLayout.addView(this.exitFullscreenButton, LayoutHelper.createFrame(48, 48, 53));
        this.exitFullscreenButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda82
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$createVideoControlsInterface$98(view2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createVideoControlsInterface$97() {
        if (this.needShowOnReady) {
            showVideoSeekPreviewPosition(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createVideoControlsInterface$98(View view) {
        Activity activity = this.parentActivity;
        if (activity == null) {
            return;
        }
        this.wasRotated = false;
        this.fullscreenedByButton = 2;
        if (this.prevOrientation == -10) {
            this.prevOrientation = activity.getRequestedOrientation();
        }
        this.parentActivity.setRequestedOrientation(1);
    }

    private int[] fixVideoWidthHeight(int i, int i2) {
        int[] iArr = {i, i2};
        MediaCodec mediaCodecCreateEncoderByType = null;
        try {
            try {
                mediaCodecCreateEncoderByType = MediaCodec.createEncoderByType(MediaController.VIDEO_MIME_TYPE);
                MediaCodecInfo.VideoCapabilities videoCapabilities = mediaCodecCreateEncoderByType.getCodecInfo().getCapabilitiesForType(MediaController.VIDEO_MIME_TYPE).getVideoCapabilities();
                Range<Integer> supportedWidths = videoCapabilities.getSupportedWidths();
                Range<Integer> supportedHeights = videoCapabilities.getSupportedHeights();
                iArr[0] = Math.max(((Integer) supportedWidths.getLower()).intValue(), Math.round(i / 16.0f) * 16);
                iArr[1] = Math.max(((Integer) supportedHeights.getLower()).intValue(), Math.round(i2 / 16.0f) * 16);
                mediaCodecCreateEncoderByType.release();
                return iArr;
            } catch (Exception unused) {
                return iArr;
            }
        } catch (Exception unused2) {
            if (mediaCodecCreateEncoderByType != null) {
                mediaCodecCreateEncoderByType.release();
            }
            return iArr;
        } catch (Throwable th) {
            if (mediaCodecCreateEncoderByType != null) {
                try {
                    mediaCodecCreateEncoderByType.release();
                } catch (Exception unused3) {
                }
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public VideoEditedInfo getCurrentVideoEditedInfo() {
        int i;
        long j;
        if (!this.isCurrentVideo && hasAnimatedMediaEntities() && this.centerImage.getBitmapWidth() > 0) {
            int i2 = this.sendPhotoType;
            float f = i2 == 1 ? 800.0f : i2 == 11 ? 500.0f : 854.0f;
            VideoEditedInfo videoEditedInfo = new VideoEditedInfo();
            videoEditedInfo.startTime = 0L;
            videoEditedInfo.start = 0L;
            videoEditedInfo.endTime = Math.min(3000L, this.editState.averageDuration);
            while (true) {
                j = videoEditedInfo.endTime;
                if (j <= 0 || j >= 1000) {
                    break;
                }
                videoEditedInfo.endTime = j * 2;
            }
            videoEditedInfo.end = j;
            videoEditedInfo.compressQuality = this.selectedCompression;
            videoEditedInfo.rotationValue = 0;
            videoEditedInfo.originalPath = this.currentImagePath;
            long j2 = videoEditedInfo.endTime;
            videoEditedInfo.estimatedSize = (int) ((j2 / 1000.0f) * 115200.0f);
            videoEditedInfo.estimatedDuration = j2;
            videoEditedInfo.framerate = 30;
            videoEditedInfo.originalDuration = j2;
            EditState editState = this.editState;
            videoEditedInfo.filterState = editState.savedFilterState;
            String str = editState.croppedPaintPath;
            if (str != null) {
                videoEditedInfo.paintPath = str;
                ArrayList arrayList = editState.croppedMediaEntities;
                videoEditedInfo.mediaEntities = (arrayList == null || arrayList.isEmpty()) ? null : this.editState.croppedMediaEntities;
            } else {
                videoEditedInfo.paintPath = editState.paintPath;
                videoEditedInfo.mediaEntities = editState.mediaEntities;
            }
            videoEditedInfo.isPhoto = true;
            int bitmapWidth = this.centerImage.getBitmapWidth();
            int bitmapHeight = this.centerImage.getBitmapHeight();
            MediaController.CropState cropState = this.editState.cropState;
            if (cropState != null) {
                int i3 = cropState.transformRotation;
                if (i3 == 90 || i3 == 270) {
                    bitmapHeight = bitmapWidth;
                    bitmapWidth = bitmapHeight;
                }
                bitmapWidth = (int) (bitmapWidth * cropState.cropPw);
                bitmapHeight = (int) (bitmapHeight * cropState.cropPh);
            }
            int i4 = this.sendPhotoType;
            if (i4 == 1 || i4 == 11) {
                bitmapWidth = bitmapHeight;
            }
            float f2 = bitmapWidth;
            float f3 = bitmapHeight;
            float fMax = Math.max(f2 / f, f3 / f);
            if (fMax < 1.0f) {
                fMax = 1.0f;
            }
            int iMax = (int) (f2 / fMax);
            int iMax2 = (int) (f3 / fMax);
            if (iMax % 16 != 0) {
                iMax = Math.max(1, Math.round(iMax / 16.0f)) * 16;
            }
            if (iMax2 % 16 != 0) {
                iMax2 = Math.max(1, Math.round(iMax2 / 16.0f)) * 16;
            }
            videoEditedInfo.resultWidth = iMax;
            videoEditedInfo.originalWidth = iMax;
            videoEditedInfo.resultHeight = iMax2;
            videoEditedInfo.originalHeight = iMax2;
            videoEditedInfo.bitrate = -1;
            videoEditedInfo.muted = true;
            videoEditedInfo.avatarStartTime = 0L;
            return videoEditedInfo;
        }
        if (!this.isCurrentVideo || ((this.currentPlayingVideoFile == null && this.currentPlayingVideoQualityFiles == null) || this.compressionsCount == 0)) {
            return null;
        }
        VideoEditedInfo videoEditedInfo2 = new VideoEditedInfo();
        videoEditedInfo2.startTime = this.startTime;
        videoEditedInfo2.endTime = this.endTime;
        videoEditedInfo2.start = this.videoCutStart;
        videoEditedInfo2.end = this.videoCutEnd;
        videoEditedInfo2.compressQuality = this.selectedCompression;
        videoEditedInfo2.rotationValue = this.rotationValue;
        videoEditedInfo2.originalWidth = this.originalWidth;
        videoEditedInfo2.originalHeight = this.originalHeight;
        videoEditedInfo2.bitrate = this.bitrate;
        videoEditedInfo2.originalPath = this.currentPathObject;
        long j3 = this.estimatedSize;
        if (j3 == 0) {
            j3 = 1;
        }
        videoEditedInfo2.estimatedSize = j3;
        videoEditedInfo2.estimatedDuration = this.estimatedDuration;
        videoEditedInfo2.framerate = this.videoFramerate;
        videoEditedInfo2.originalDuration = (long) (this.videoDuration * 1000.0f);
        EditState editState2 = this.editState;
        videoEditedInfo2.filterState = editState2.savedFilterState;
        String str2 = editState2.croppedPaintPath;
        if (str2 != null) {
            videoEditedInfo2.paintPath = str2;
            ArrayList arrayList2 = editState2.croppedMediaEntities;
            videoEditedInfo2.mediaEntities = (arrayList2 == null || arrayList2.isEmpty()) ? null : this.editState.croppedMediaEntities;
        } else {
            videoEditedInfo2.paintPath = editState2.paintPath;
            ArrayList arrayList3 = editState2.mediaEntities;
            videoEditedInfo2.mediaEntities = (arrayList3 == null || arrayList3.isEmpty()) ? null : this.editState.mediaEntities;
        }
        if (this.sendPhotoType != 1 && !this.muteVideo && (this.compressItem.getTag() == null || (videoEditedInfo2.resultWidth == this.originalWidth && videoEditedInfo2.resultHeight == this.originalHeight))) {
            videoEditedInfo2.resultWidth = this.originalWidth;
            videoEditedInfo2.resultHeight = this.originalHeight;
            videoEditedInfo2.bitrate = this.muteVideo ? -1 : this.originalBitrate;
        } else {
            if (this.muteVideo || this.sendPhotoType == 1) {
                this.selectedCompression = 1;
                updateWidthHeightBitrateForCompression();
            }
            videoEditedInfo2.resultWidth = this.resultWidth;
            videoEditedInfo2.resultHeight = this.resultHeight;
            if (!this.muteVideo && this.sendPhotoType != 1) {
                i = this.bitrate;
            }
            videoEditedInfo2.bitrate = i;
        }
        MediaController.CropState cropState2 = this.editState.cropState;
        videoEditedInfo2.cropState = cropState2;
        if (cropState2 != null) {
            videoEditedInfo2.rotationValue += cropState2.transformRotation;
            while (true) {
                i = videoEditedInfo2.rotationValue;
                if (i < 360) {
                    break;
                }
                videoEditedInfo2.rotationValue = i - 360;
            }
            if (i == 90 || i == 270) {
                MediaController.CropState cropState3 = videoEditedInfo2.cropState;
                cropState3.transformWidth = (int) (videoEditedInfo2.resultWidth * cropState3.cropPh);
                cropState3.transformHeight = (int) (videoEditedInfo2.resultHeight * cropState3.cropPw);
            } else {
                MediaController.CropState cropState4 = videoEditedInfo2.cropState;
                cropState4.transformWidth = (int) (videoEditedInfo2.resultWidth * cropState4.cropPw);
                cropState4.transformHeight = (int) (videoEditedInfo2.resultHeight * cropState4.cropPh);
            }
            if (this.sendPhotoType == 1) {
                MediaController.CropState cropState5 = videoEditedInfo2.cropState;
                if (cropState5.transformWidth > 800) {
                    cropState5.transformWidth = 800;
                }
                if (cropState5.transformHeight > 800) {
                    cropState5.transformHeight = 800;
                }
                int iMin = Math.min(cropState5.transformWidth, cropState5.transformHeight);
                cropState5.transformHeight = iMin;
                cropState5.transformWidth = iMin;
            }
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1157d("original transformed w = " + videoEditedInfo2.cropState.transformWidth + " h = " + videoEditedInfo2.cropState.transformHeight + " r = " + videoEditedInfo2.rotationValue);
            }
            MediaController.CropState cropState6 = videoEditedInfo2.cropState;
            int[] iArrFixVideoWidthHeight = fixVideoWidthHeight(cropState6.transformWidth, cropState6.transformHeight);
            MediaController.CropState cropState7 = videoEditedInfo2.cropState;
            cropState7.transformWidth = iArrFixVideoWidthHeight[0];
            cropState7.transformHeight = iArrFixVideoWidthHeight[1];
            if (BuildVars.LOGS_ENABLED) {
                FileLog.m1157d("fixed transformed w = " + videoEditedInfo2.cropState.transformWidth + " h = " + videoEditedInfo2.cropState.transformHeight);
            }
        }
        if (this.sendPhotoType == 1) {
            videoEditedInfo2.avatarStartTime = this.avatarStartTime;
            videoEditedInfo2.originalBitrate = this.originalBitrate;
        }
        videoEditedInfo2.muted = this.muteVideo || this.sendPhotoType == 1;
        return videoEditedInfo2;
    }

    private boolean supportsSendingNewEntities() {
        ChatActivity chatActivity = this.parentChatActivity;
        if (chatActivity == null) {
            return false;
        }
        TLRPC.EncryptedChat encryptedChat = chatActivity.currentEncryptedChat;
        return encryptedChat == null || AndroidUtilities.getPeerLayerVersion(encryptedChat.layer) >= 101;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeCaptionEnter(boolean z) {
        int i = this.currentIndex;
        if (i < 0 || i >= this.imagesArrLocals.size() || !isCaptionOpen()) {
            return;
        }
        if (z) {
            applyCaption();
        }
        getCaptionView().onBackPressed();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CaptionContainerView getCaptionView() {
        PhotoViewerProvider photoViewerProvider = this.placeProvider;
        return (photoViewerProvider == null || !photoViewerProvider.isCaptionAbove()) ? this.captionEdit : this.topCaptionEdit;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public CharSequence applyCaption() {
        int i;
        if (!isVisible() || this.placeProvider == null || (i = this.currentIndex) < 0 || i >= this.imagesArrLocals.size()) {
            return null;
        }
        Object obj = this.imagesArrLocals.get(this.currentIndex);
        CharSequence text = getCaptionView().getText();
        CharSequence[] charSequenceArr = {text};
        if (this.hasCaptionForAllMedia && !TextUtils.equals(this.captionForAllMedia, text) && this.placeProvider.getPhotoIndex(this.currentIndex) != 0 && this.placeProvider.getSelectedCount() > 0) {
            this.hasCaptionForAllMedia = false;
        }
        ArrayList<TLRPC.MessageEntity> entities = MediaDataController.getInstance(this.currentAccount).getEntities(charSequenceArr, supportsSendingNewEntities());
        CharSequence charSequence = charSequenceArr[0];
        this.captionForAllMedia = charSequence;
        if (obj instanceof MediaController.PhotoEntry) {
            MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) obj;
            photoEntry.caption = charSequence;
            photoEntry.entities = entities;
        } else if (obj instanceof MediaController.SearchImage) {
            MediaController.SearchImage searchImage = (MediaController.SearchImage) obj;
            searchImage.caption = charSequence;
            searchImage.entities = entities;
        }
        if (text.length() != 0 && !this.placeProvider.isPhotoChecked(this.currentIndex)) {
            setPhotoChecked();
        }
        PhotoViewerProvider photoViewerProvider = this.placeProvider;
        if (photoViewerProvider != null) {
            photoViewerProvider.onApplyCaption(text);
        }
        return text;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVideoPlayerTime() {
        String str;
        String str2;
        Arrays.fill(this.videoPlayerCurrentTime, 0);
        Arrays.fill(this.videoPlayerTotalTime, 0);
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer != null) {
            long jMax = Math.max(0L, videoPlayer.getCurrentPosition());
            if (this.shownControlsByEnd && !this.actionBarWasShownBeforeByEnd) {
                jMax = 0;
            }
            long jMax2 = Math.max(0L, this.videoPlayer.getDuration());
            if (!this.inPreview && this.videoTimelineViewContainer.getVisibility() == 0) {
                jMax2 = (long) (jMax2 * (this.videoTimelineView.getRightProgress() - this.videoTimelineView.getLeftProgress()));
                jMax = (long) (jMax - (this.videoTimelineView.getLeftProgress() * jMax2));
                if (jMax > jMax2) {
                    jMax = jMax2;
                }
            }
            long j = jMax / 1000;
            long j2 = jMax2 / 1000;
            int[] iArr = this.videoPlayerCurrentTime;
            iArr[0] = (int) (j / 60);
            iArr[1] = (int) (j % 60);
            int[] iArr2 = this.videoPlayerTotalTime;
            iArr2[0] = (int) (j2 / 60);
            iArr2[1] = (int) (j2 % 60);
        } else {
            PhotoViewerWebView photoViewerWebView = this.photoViewerWebView;
            if (photoViewerWebView != null && photoViewerWebView.isControllable()) {
                long jMax3 = (!this.shownControlsByEnd || this.actionBarWasShownBeforeByEnd) ? Math.max(0, this.photoViewerWebView.getCurrentPosition()) : 0L;
                long jMax4 = Math.max(0, this.photoViewerWebView.getVideoDuration());
                if (!this.inPreview && this.videoTimelineViewContainer.getVisibility() == 0) {
                    jMax4 = (long) (jMax4 * (this.videoTimelineView.getRightProgress() - this.videoTimelineView.getLeftProgress()));
                    jMax3 = (long) (jMax3 - (this.videoTimelineView.getLeftProgress() * jMax4));
                    if (jMax3 > jMax4) {
                        jMax3 = jMax4;
                    }
                }
                long j3 = jMax3 / 1000;
                long j4 = jMax4 / 1000;
                int[] iArr3 = this.videoPlayerCurrentTime;
                iArr3[0] = (int) (j3 / 60);
                iArr3[1] = (int) (j3 % 60);
                int[] iArr4 = this.videoPlayerTotalTime;
                iArr4[0] = (int) (j4 / 60);
                iArr4[1] = (int) (j4 % 60);
            }
        }
        int[] iArr5 = this.videoPlayerCurrentTime;
        int i = iArr5[0];
        if (i >= 60) {
            str = format(i / 60, i % 60, iArr5[1]);
        } else {
            str = format(i, iArr5[1]);
        }
        int[] iArr6 = this.videoPlayerTotalTime;
        int i2 = iArr6[0];
        if (i2 >= 60) {
            str2 = format(i2 / 60, i2 % 60, iArr6[1]);
        } else {
            str2 = format(i2, iArr6[1]);
        }
        this.videoPlayerTime.setText(str + " / " + str2);
        if (Objects.equals(this.lastControlFrameDuration, str2)) {
            return;
        }
        this.lastControlFrameDuration = str2;
        this.videoPlayerControlFrameLayout.requestLayout();
    }

    private String format(int i, int i2, int i3) {
        char c = (char) (((i >= 100 ? 99 : i) / 10) + 48);
        if (i >= 100) {
            i = 99;
        }
        char c2 = (char) ((i % 10) + 48);
        char c3 = (char) (((i2 >= 100 ? 99 : i2) / 10) + 48);
        if (i2 >= 100) {
            i2 = 99;
        }
        char c4 = (char) ((i2 % 10) + 48);
        char c5 = (char) (((i3 >= 100 ? 99 : i3) / 10) + 48);
        if (i3 >= 100) {
            i3 = 99;
        }
        return new String(new char[]{c, c2, ':', c3, c4, ':', c5, (char) ((i3 % 10) + 48)});
    }

    private String format(int i, int i2) {
        char c = (char) (((i >= 100 ? 99 : i) / 10) + 48);
        if (i >= 100) {
            i = 99;
        }
        char c2 = (char) ((i % 10) + 48);
        char c3 = (char) (((i2 >= 100 ? 99 : i2) / 10) + 48);
        if (i2 >= 100) {
            i2 = 99;
        }
        return new String(new char[]{c, c2, ':', c3, (char) ((i2 % 10) + 48)});
    }

    private void checkBufferedProgress(float f) {
        MessageObject messageObject;
        TLRPC.Document document;
        if (!this.isStreaming || this.parentActivity == null || this.streamingAlertShown || this.videoPlayer == null || (messageObject = this.currentMessageObject) == null || (document = messageObject.getDocument()) == null || this.currentMessageObject.getDuration() < 20.0d) {
            return;
        }
        boolean z = document.size >= 2147483648L;
        if ((DownloadController.getInstance(this.currentAccount).getAutodownloadMask() & 4) == 0 || f >= 0.9f) {
            return;
        }
        long j = document.size;
        if (j * f >= 5242880.0f || (f >= 0.5f && j >= 2097152)) {
            if (Math.abs(SystemClock.elapsedRealtime() - this.startedPlayTime) >= (z ? XCallback.PRIORITY_HIGHEST : 3000)) {
                if (this.videoPlayer.getDuration() == -9223372036854775807L) {
                    Toast.makeText(this.parentActivity, LocaleController.getString(C2369R.string.VideoDoesNotSupportStreaming), 1).show();
                }
                this.streamingAlertShown = true;
            }
        }
    }

    public void updateColors() {
        int i = Theme.key_chat_editMediaButton;
        int themedColor = getThemedColor(i);
        ChatActivityEnterView.SendButton sendButton = this.pickerViewSendButton;
        if (sendButton != null) {
            sendButton.updateColors();
            this.pickerViewSendButton.invalidate();
        }
        CheckBox checkBox = this.checkImageView;
        if (checkBox != null) {
            checkBox.setColor(getThemedColor(i), -1);
        }
        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(themedColor, PorterDuff.Mode.MULTIPLY);
        ImageView imageView = this.paintItem;
        if (imageView != null && imageView.getColorFilter() != null) {
            this.paintItem.setColorFilter(porterDuffColorFilter);
        }
        ImageView imageView2 = this.cropItem;
        if (imageView2 != null && imageView2.getColorFilter() != null) {
            this.cropItem.setColorFilter(porterDuffColorFilter);
        }
        ImageView imageView3 = this.tuneItem;
        if (imageView3 != null && imageView3.getColorFilter() != null) {
            this.tuneItem.setColorFilter(porterDuffColorFilter);
        }
        ImageView imageView4 = this.rotateItem;
        if (imageView4 != null && imageView4.getColorFilter() != null) {
            this.rotateItem.setColorFilter(porterDuffColorFilter);
        }
        ImageView imageView5 = this.mirrorItem;
        if (imageView5 != null && imageView5.getColorFilter() != null) {
            this.mirrorItem.setColorFilter(porterDuffColorFilter);
        }
        PickerBottomLayoutViewer pickerBottomLayoutViewer = this.editorDoneLayout;
        if (pickerBottomLayoutViewer != null) {
            pickerBottomLayoutViewer.doneButton.setTextColor(themedColor);
        }
        PickerBottomLayoutViewer pickerBottomLayoutViewer2 = this.qualityPicker;
        if (pickerBottomLayoutViewer2 != null) {
            pickerBottomLayoutViewer2.doneButton.setTextColor(themedColor);
        }
        LPhotoPaintView lPhotoPaintView = this.photoPaintView;
        if (lPhotoPaintView != null) {
            lPhotoPaintView.updateColors();
        }
        PhotoFilterView photoFilterView = this.photoFilterView;
        if (photoFilterView != null) {
            photoFilterView.updateColors();
        }
        CaptionPhotoViewer captionPhotoViewer = this.captionEdit;
        if (captionPhotoViewer != null) {
            captionPhotoViewer.updateColors(this.resourcesProvider);
        }
        CaptionPhotoViewer captionPhotoViewer2 = this.topCaptionEdit;
        if (captionPhotoViewer2 != null) {
            captionPhotoViewer2.updateColors(this.resourcesProvider);
        }
        VideoTimelinePlayView videoTimelinePlayView = this.videoTimelineView;
        if (videoTimelinePlayView != null) {
            videoTimelinePlayView.invalidate();
        }
        SelectedPhotosListView selectedPhotosListView = this.selectedPhotosListView;
        if (selectedPhotosListView != null) {
            int childCount = selectedPhotosListView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = this.selectedPhotosListView.getChildAt(i2);
                if (childAt instanceof PhotoPickerPhotoCell) {
                    ((PhotoPickerPhotoCell) childAt).updateColors();
                }
            }
        }
        StickersAlert stickersAlert = this.masksAlert;
        if (stickersAlert != null) {
            stickersAlert.updateColors(true);
        }
    }

    public void injectVideoPlayer(VideoPlayer videoPlayer) {
        this.injectingVideoPlayer = videoPlayer;
    }

    public void injectVideoPlayerSurface(SurfaceTexture surfaceTexture) {
        this.injectingVideoPlayerSurface = surfaceTexture;
    }

    public boolean isInjectingVideoPlayer() {
        return this.injectingVideoPlayer != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void scheduleActionBarHide() {
        scheduleActionBarHide(3000);
    }

    private void scheduleActionBarHide(int i) {
        if (isAccessibilityEnabled()) {
            return;
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || !messageObject.isSponsored()) {
            AndroidUtilities.cancelRunOnUIThread(this.hideActionBarRunnable);
            AndroidUtilities.runOnUIThread(this.hideActionBarRunnable, i);
        }
    }

    private boolean isAccessibilityEnabled() {
        try {
            AccessibilityManager accessibilityManager = (AccessibilityManager) this.activityContext.getSystemService("accessibility");
            if (accessibilityManager.isEnabled()) {
                if (accessibilityManager.isTouchExplorationEnabled()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            FileLog.m1160e(e);
            return false;
        }
    }

    public void updateWebPlayerState(boolean z, int i) {
        updatePlayerState(z, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:182:0x025d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updatePlayerState(boolean r18, int r19) {
        /*
            Method dump skipped, instructions count: 812
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.updatePlayerState(boolean, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playVideoOrWeb() {
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer != null) {
            videoPlayer.play();
            return;
        }
        PhotoViewerWebView photoViewerWebView = this.photoViewerWebView;
        if (photoViewerWebView != null) {
            photoViewerWebView.playVideo();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pauseVideoOrWeb() {
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer != null) {
            videoPlayer.pause();
            return;
        }
        PhotoViewerWebView photoViewerWebView = this.photoViewerWebView;
        if (photoViewerWebView != null) {
            photoViewerWebView.pauseVideo();
        }
    }

    private void seekVideoOrWebToProgress(float f) {
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer != null) {
            videoPlayer.seekTo((long) (f * videoPlayer.getDuration()));
            return;
        }
        PhotoViewerWebView photoViewerWebView = this.photoViewerWebView;
        if (photoViewerWebView != null) {
            photoViewerWebView.seekTo((long) (f * photoViewerWebView.getVideoDuration()));
        }
    }

    private void preparePlayer(ArrayList arrayList, Uri uri, boolean z, boolean z2) throws NumberFormatException {
        preparePlayer(arrayList, uri, z, z2, null);
    }

    private void preparePlayer(ArrayList arrayList, Uri uri, boolean z, boolean z2, MediaController.SavedFilterState savedFilterState) throws NumberFormatException {
        boolean z3;
        PageBlocksAdapter pageBlocksAdapter;
        VideoSize videoSize;
        int i;
        int i2;
        if (!z2) {
            this.currentPlayingVideoFile = uri;
            this.currentPlayingVideoQualityFiles = arrayList;
        }
        if (this.parentActivity == null) {
            return;
        }
        this.streamingAlertShown = false;
        this.startedPlayTime = SystemClock.elapsedRealtime();
        this.currentVideoFinishedLoading = false;
        this.lastBufferedPositionCheck = 0L;
        this.firstAnimationDelay = true;
        this.inPreview = z2;
        releasePlayer(false);
        if (this.imagesArrLocals.isEmpty()) {
            createVideoTextureView(null);
        }
        if (this.textureImageView == null) {
            ImageView imageView = new ImageView(this.parentActivity);
            this.textureImageView = imageView;
            imageView.setBackgroundColor(Opcodes.V_PREVIEW);
            this.textureImageView.setPivotX(0.0f);
            this.textureImageView.setPivotY(0.0f);
            this.textureImageView.setVisibility(4);
            this.containerView.addView(this.textureImageView);
        }
        checkFullscreenButton();
        if (this.orientationEventListener == null) {
            OrientationEventListener orientationEventListener = new OrientationEventListener(ApplicationLoader.applicationContext) { // from class: org.telegram.ui.PhotoViewer.54
                @Override // android.view.OrientationEventListener
                public void onOrientationChanged(int i3) {
                    if (PhotoViewer.this.orientationEventListener == null || PhotoViewer.this.aspectRatioFrameLayout == null || PhotoViewer.this.aspectRatioFrameLayout.getVisibility() != 0 || PhotoViewer.this.parentActivity == null || PhotoViewer.this.fullscreenedByButton == 0) {
                        return;
                    }
                    if (PhotoViewer.this.fullscreenedByButton != 1) {
                        if (i3 > 0 && (i3 >= 330 || i3 <= 30)) {
                            PhotoViewer.this.wasRotated = true;
                            return;
                        }
                        if (!PhotoViewer.this.wasRotated || i3 < 240 || i3 > 300) {
                            return;
                        }
                        PhotoViewer.this.parentActivity.setRequestedOrientation(PhotoViewer.this.prevOrientation);
                        PhotoViewer.this.fullscreenedByButton = 0;
                        PhotoViewer.this.wasRotated = false;
                        return;
                    }
                    if (i3 >= 240 && i3 <= 300) {
                        PhotoViewer.this.wasRotated = true;
                        return;
                    }
                    if (!PhotoViewer.this.wasRotated || i3 <= 0) {
                        return;
                    }
                    if (i3 >= 330 || i3 <= 30) {
                        PhotoViewer.this.parentActivity.setRequestedOrientation(PhotoViewer.this.prevOrientation);
                        PhotoViewer.this.fullscreenedByButton = 0;
                        PhotoViewer.this.wasRotated = false;
                    }
                }
            };
            this.orientationEventListener = orientationEventListener;
            if (orientationEventListener.canDetectOrientation()) {
                this.orientationEventListener.enable();
            } else {
                this.orientationEventListener.disable();
                this.orientationEventListener = null;
            }
        }
        this.textureUploaded = false;
        this.videoSizeSet = false;
        this.videoCrossfadeStarted = false;
        this.playerWasReady = false;
        this.playerWasPlaying = false;
        this.captureFrameReadyAtTime = -1L;
        this.captureFrameAtTime = -1L;
        this.needCaptureFrameReadyAtTime = -1L;
        this.firstFrameRendered = false;
        PipSource pipSource = this.pipSource;
        if (pipSource != null) {
            pipSource.destroy();
            this.pipSource = null;
        }
        if (this.videoPlayer == null) {
            VideoPlayer videoPlayer = this.injectingVideoPlayer;
            if (videoPlayer != null) {
                this.videoPlayer = videoPlayer;
                this.injectingVideoPlayer = null;
                this.playerInjected = true;
                updatePlayerState(videoPlayer.getPlayWhenReady(), this.videoPlayer.getPlaybackState());
                z3 = false;
            } else {
                VideoPlayer videoPlayer2 = new VideoPlayer() { // from class: org.telegram.ui.PhotoViewer.55
                    @Override // org.telegram.p023ui.Components.VideoPlayer
                    public void play() {
                        super.play();
                        PhotoViewer.this.playOrStopAnimatedStickers(true);
                        if (PhotoViewer.this.ignorePlayerUpdate) {
                            return;
                        }
                        CastSync.syncPosition(getCurrentPosition());
                        CastSync.setPlaying(true);
                    }

                    @Override // org.telegram.p023ui.Components.VideoPlayer
                    public void pause() {
                        super.pause();
                        if (PhotoViewer.this.currentEditMode == 0) {
                            PhotoViewer.this.playOrStopAnimatedStickers(false);
                        }
                        if (PhotoViewer.this.ignorePlayerUpdate) {
                            return;
                        }
                        CastSync.syncPosition(getCurrentPosition());
                        CastSync.setPlaying(false);
                    }

                    @Override // org.telegram.p023ui.Components.VideoPlayer
                    public void seekTo(long j) {
                        super.seekTo(j);
                        if (PhotoViewer.this.isCurrentVideo) {
                            PhotoViewer.this.seekAnimatedStickersTo(j);
                        }
                        if (PhotoViewer.this.ignorePlayerUpdate) {
                            return;
                        }
                        CastSync.syncPosition(j);
                    }

                    @Override // org.telegram.p023ui.Components.VideoPlayer, com.google.android.exoplayer2.Player.Listener
                    public void onRenderedFirstFrame() {
                        super.onRenderedFirstFrame();
                        boolean z4 = true;
                        PhotoViewer.this.firstFrameRendered = true;
                        if (PhotoViewer.this.usedSurfaceView) {
                            PhotoViewer.this.containerView.invalidate();
                        }
                        PhotoViewer.this.updateQualityItems();
                        if (!CastSync.isActive() && !PhotoViewer.this.muteVideo) {
                            z4 = false;
                        }
                        setMute(z4);
                        if (PhotoViewer.this.ignorePlayerUpdate) {
                            return;
                        }
                        CastSync.syncPosition(getCurrentPosition());
                        CastSync.setSpeed(getPlaybackSpeed());
                    }

                    @Override // org.telegram.p023ui.Components.VideoPlayer
                    public void setPlaybackSpeed(float f) {
                        super.setPlaybackSpeed(f);
                        if (PhotoViewer.this.ignorePlayerUpdate) {
                            return;
                        }
                        CastSync.setSpeed(f);
                    }
                };
                this.videoPlayer = videoPlayer2;
                videoPlayer2.setOnQualityChangeListener(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.updateQualityItems();
                    }
                });
                if (PipUtils.checkPermissions(this.parentActivity) == 1) {
                    this.pipSource = new PipSource.Builder(this.parentActivity, this).setTagPrefix("photo-viewer-" + this.videoPlayer.playerId).setContentView(this.aspectRatioFrameLayout).setPlaceholderView(this.pipPlaceholderView).setNeedMediaSession(true).build();
                }
                z3 = true;
            }
            TextureView textureView = this.videoTextureView;
            if (textureView != null) {
                this.videoPlayer.setTextureView(textureView);
            } else {
                SurfaceView surfaceView = this.videoSurfaceView;
                if (surfaceView != null) {
                    this.videoPlayer.setSurfaceView(surfaceView);
                }
            }
            FirstFrameView firstFrameView = this.firstFrameView;
            if (firstFrameView != null) {
                firstFrameView.clear();
            }
            this.videoPlayer.setDelegate(new C582956());
        } else {
            z3 = false;
        }
        if (!this.imagesArrLocals.isEmpty()) {
            createVideoTextureView(savedFilterState);
        }
        this.videoCrossfadeAlpha = 0.0f;
        TextureView textureView2 = this.videoTextureView;
        if (textureView2 != null) {
            textureView2.setAlpha(0.0f);
        }
        SurfaceView surfaceView2 = this.videoSurfaceView;
        if (surfaceView2 != null) {
            surfaceView2.setVisibility(4);
        }
        PaintingOverlay paintingOverlay = this.paintingOverlay;
        if (paintingOverlay != null) {
            paintingOverlay.setAlpha(this.videoCrossfadeAlpha);
        }
        this.shouldSavePositionForCurrentVideo = null;
        this.shouldSavePositionForCurrentVideoShortTerm = null;
        this.lastSaveTime = 0L;
        if (z3) {
            this.seekToProgressPending = this.seekToProgressPending2;
            this.videoPlayerSeekbar.setProgress(0.0f);
            VideoTimelinePlayView videoTimelinePlayView = this.videoTimelineView;
            videoTimelinePlayView.setProgress(videoTimelinePlayView.getLeftProgress());
            this.videoPlayerSeekbar.setBufferedProgress(0.0f);
            MessageObject messageObject = this.currentMessageObject;
            if (messageObject != null) {
                int duration = (int) messageObject.getDuration();
                String fileNameFast = this.currentMessageObject.getFileNameFast();
                if (!TextUtils.isEmpty(fileNameFast)) {
                    if (duration >= 10) {
                        SavedVideoPosition savedVideoPosition = (SavedVideoPosition) savedVideoPositions.get(fileNameFast);
                        MessageObject messageObject2 = this.currentMessageObject;
                        if (messageObject2.forceSeekTo < 0.0f && savedVideoPosition != null) {
                            float f = savedVideoPosition.position;
                            if (f > 0.0f && f < 0.999f) {
                                messageObject2.forceSeekTo = f;
                                this.videoPlayerSeekbar.setProgress(f);
                            }
                        }
                        this.shouldSavePositionForCurrentVideoShortTerm = fileNameFast;
                    }
                    if (duration >= 300) {
                        if (this.currentMessageObject.forceSeekTo < 0.0f) {
                            float f2 = ApplicationLoader.applicationContext.getSharedPreferences("media_saved_pos", 0).getFloat(fileNameFast, -1.0f);
                            if (f2 > 0.0f && f2 < 0.999f) {
                                this.currentMessageObject.forceSeekTo = f2;
                                this.videoPlayerSeekbar.setProgress(f2);
                            }
                        }
                        this.shouldSavePositionForCurrentVideo = fileNameFast;
                    }
                }
            }
            if (arrayList != null) {
                this.videoPlayer.preparePlayer(arrayList, VideoPlayer.getSavedQuality(arrayList, this.currentMessageObject));
            } else {
                this.videoPlayer.preparePlayer(uri, "other");
            }
            updateQualityItems();
            this.videoPlayer.setPlayWhenReady(z);
            PipSource pipSource2 = this.pipSource;
            if (pipSource2 != null) {
                pipSource2.setPlayer(this.videoPlayer.player);
                ExoPlayer exoPlayer = this.videoPlayer.player;
                if (exoPlayer != null && (videoSize = exoPlayer.getVideoSize()) != null && (i = videoSize.width) > 0 && (i2 = videoSize.height) > 0) {
                    int i3 = videoSize.unappliedRotationDegrees;
                    if (i3 == 90 || i3 == 270) {
                        this.pipSource.setContentRatio(i2, i);
                    } else {
                        this.pipSource.setContentRatio(i, i2);
                    }
                }
            }
        }
        Boolean looping = VideoPlayer.getLooping(this.currentMessageObject);
        if (looping != null) {
            this.playerLooping = looping.booleanValue();
        } else {
            MessageObject messageObject3 = this.currentMessageObject;
            this.playerLooping = (messageObject3 != null && messageObject3.getDuration() <= 30.0d) || ((pageBlocksAdapter = this.pageBlocksAdapter) != null && pageBlocksAdapter.isHardwarePlayer(this.currentIndex));
        }
        this.videoPlayerControlFrameLayout.setSeekBarTransitionEnabled(this.playerLooping);
        this.videoPlayer.setLooping(this.playerLooping);
        this.loopItem.setEnabledByColor(this.playerLooping, -1, -9194260);
        this.loopItem.setSelectorColor(this.playerLooping ? 259241196 : 268435455);
        MessageObject messageObject4 = this.currentMessageObject;
        if (messageObject4 != null) {
            float f3 = messageObject4.forceSeekTo;
            if (f3 >= 0.0f) {
                this.seekToProgressPending = f3;
                messageObject4.forceSeekTo = -1.0f;
            }
        }
        TLRPC.BotInlineResult botInlineResult = this.currentBotInlineResult;
        if (botInlineResult != null && (botInlineResult.type.equals(MediaStreamTrack.VIDEO_TRACK_KIND) || MessageObject.isVideoDocument(this.currentBotInlineResult.document))) {
            this.bottomLayout.setVisibility(0);
            this.bottomLayout.setPadding(0, 0, AndroidUtilities.m1146dp(84.0f), 0);
            this.pickerView.setVisibility(8);
        } else {
            this.bottomLayout.setPadding(0, 0, 0, 0);
        }
        if (this.pageBlocksAdapter != null) {
            this.bottomLayout.setVisibility(0);
        }
        PageBlocksAdapter pageBlocksAdapter2 = this.pageBlocksAdapter;
        if (pageBlocksAdapter2 != null && pageBlocksAdapter2.isHardwarePlayer(this.currentIndex) && !this.pageBlocksAdapter.isVideo(this.currentIndex)) {
            setVideoPlayerControlVisible(false, true);
        } else {
            setVideoPlayerControlVisible(!this.isCurrentVideo, true);
        }
        if (!this.isCurrentVideo) {
            scheduleActionBarHide(this.playerAutoStarted ? 3000 : MediaDataController.MAX_STYLE_RUNS_COUNT);
        }
        if (this.currentMessageObject != null) {
            this.videoPlayer.setPlaybackSpeed(this.currentVideoSpeed);
        }
        this.inPreview = z2;
    }

    /* renamed from: org.telegram.ui.PhotoViewer$56 */
    /* loaded from: classes5.dex */
    class C582956 implements VideoPlayer.VideoPlayerDelegate {
        private boolean firstState = true;

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public /* synthetic */ void onSeekFinished(AnalyticsListener.EventTime eventTime) {
            VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekFinished(this, eventTime);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public /* synthetic */ void onSeekStarted(AnalyticsListener.EventTime eventTime) {
            VideoPlayer.VideoPlayerDelegate.CC.$default$onSeekStarted(this, eventTime);
        }

        C582956() {
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onStateChanged(boolean z, int i) {
            if (PhotoViewer.this.videoPlayer != null) {
                PhotoViewer.this.videoPlayer.setMute(CastSync.isActive() || PhotoViewer.this.muteVideo);
            }
            if (this.firstState && PhotoViewer.this.videoPlayer != null && PhotoViewer.this.videoPlayer.getDuration() != -9223372036854775807L) {
                this.firstState = false;
                if (PhotoViewer.this.imagesArr.isEmpty() && PhotoViewer.this.secureDocuments.isEmpty() && PhotoViewer.this.imagesArrLocations.isEmpty() && !PhotoViewer.this.imagesArrLocals.isEmpty() && PhotoViewer.this.switchingToIndex >= 0 && PhotoViewer.this.switchingToIndex < PhotoViewer.this.imagesArrLocals.size()) {
                    Object obj = PhotoViewer.this.imagesArrLocals.get(PhotoViewer.this.switchingToIndex);
                    if (obj instanceof MediaController.PhotoEntry) {
                        MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) obj;
                        if (photoEntry.isVideo && photoEntry.editedInfo != null) {
                            PhotoViewer.this.videoPlayer.seekTo((long) (photoEntry.editedInfo.start * PhotoViewer.this.videoPlayer.getDuration()));
                            if (PhotoViewer.this.videoTimelineView != null) {
                                PhotoViewer.this.videoTimelineView.setProgress(photoEntry.editedInfo.start);
                            }
                        }
                    }
                }
            }
            PhotoViewer.this.updatePlayerState(z, i);
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onError(VideoPlayer videoPlayer, Exception exc) {
            if (PhotoViewer.this.videoPlayer != videoPlayer) {
                return;
            }
            FileLog.m1160e(exc);
            if (PhotoViewer.this.menuItem.isSubItemVisible(10)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhotoViewer.this.parentActivity, PhotoViewer.this.resourcesProvider);
                builder.setTitle(LocaleController.getString("AppName", C2369R.string.AppName));
                builder.setMessage(LocaleController.getString(C2369R.string.CantPlayVideo));
                builder.setPositiveButton(LocaleController.getString("Open", C2369R.string.Open), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.PhotoViewer$56$$ExternalSyntheticLambda2
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i) {
                        this.f$0.lambda$onError$0(alertDialog, i);
                    }
                });
                builder.setNegativeButton(LocaleController.getString("Cancel", C2369R.string.Cancel), null);
                PhotoViewer.this.showAlertDialog(builder);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$0(AlertDialog alertDialog, int i) {
            try {
                AndroidUtilities.openForView(PhotoViewer.this.currentMessageObject, PhotoViewer.this.parentActivity, PhotoViewer.this.resourcesProvider, true);
                PhotoViewer.this.closePhoto(false, false);
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
            if (PhotoViewer.this.aspectRatioFrameLayout != null) {
                if (i3 == 90 || i3 == 270) {
                    i2 = i;
                    i = i2;
                }
                float f2 = i * f;
                int i4 = (int) f2;
                PhotoViewer.this.videoWidth = i4;
                float f3 = i2;
                PhotoViewer.this.videoHeight = (int) (f * f3);
                if (PhotoViewer.this.pipSource != null) {
                    PhotoViewer.this.pipSource.setContentRatio(PhotoViewer.this.videoWidth, PhotoViewer.this.videoHeight);
                }
                PhotoViewer.this.aspectRatioFrameLayout.setAspectRatio(i2 == 0 ? 1.0f : f2 / f3, i3);
                if (PhotoViewer.this.videoTextureView instanceof VideoEditTextureView) {
                    ((VideoEditTextureView) PhotoViewer.this.videoTextureView).setHDRInfo(PhotoViewer.this.videoPlayer.getHDRStaticInfo(null));
                    ((VideoEditTextureView) PhotoViewer.this.videoTextureView).setVideoSize(i4, i2);
                    if (PhotoViewer.this.sendPhotoType == 1) {
                        PhotoViewer.this.setCropBitmap();
                    }
                }
                PhotoViewer.this.videoSizeSet = true;
            }
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onRenderedFirstFrame() {
            if (!PhotoViewer.this.textureUploaded) {
                PhotoViewer.this.textureUploaded = true;
                PhotoViewer.this.containerView.invalidate();
            }
            if (PhotoViewer.this.firstFrameView != null) {
                if (PhotoViewer.this.videoPlayer == null || !PhotoViewer.this.videoPlayer.isLooping()) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$56$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onRenderedFirstFrame$1();
                        }
                    }, 64L);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRenderedFirstFrame$1() {
            PhotoViewer.this.firstFrameView.updateAlpha();
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onRenderedFirstFrame(AnalyticsListener.EventTime eventTime) {
            Runnable runnable = PhotoViewer.this.pipFirstFrameCallback;
            if (runnable != null) {
                runnable.run();
                PhotoViewer.this.pipFirstFrameCallback = null;
            }
            if (eventTime.eventPlaybackPositionMs == PhotoViewer.this.needCaptureFrameReadyAtTime) {
                PhotoViewer.this.captureFrameReadyAtTime = eventTime.eventPlaybackPositionMs;
                PhotoViewer.this.needCaptureFrameReadyAtTime = -1L;
                PhotoViewer.this.captureCurrentFrame();
            }
            if (PhotoViewer.this.firstFrameView != null) {
                if (PhotoViewer.this.videoPlayer == null || !PhotoViewer.this.videoPlayer.isLooping()) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$56$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onRenderedFirstFrame$2();
                        }
                    }, 64L);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRenderedFirstFrame$2() {
            PhotoViewer.this.firstFrameView.updateAlpha();
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
            TextureView textureView;
            if (PipVideoOverlay.getPipSource() != null && PipVideoOverlay.getPipSource().state2.isAttachedToPip() && (textureView = PhotoViewer.this.changedTextureView) != null && textureView.getSurfaceTexture() == surfaceTexture) {
                PipVideoOverlay.getPipTextureView().setSurfaceTexture(surfaceTexture);
                PipVideoOverlay.getPipTextureView().setVisibility(0);
                return true;
            }
            if (PhotoViewer.this.changingTextureView) {
                PhotoViewer.this.changingTextureView = false;
                if (PhotoViewer.this.isInline) {
                    PhotoViewer.this.waitingForFirstTextureUpload = 1;
                    PhotoViewer.this.changedTextureView.setSurfaceTexture(surfaceTexture);
                    PhotoViewer photoViewer = PhotoViewer.this;
                    photoViewer.changedTextureView.setSurfaceTextureListener(photoViewer.surfaceTextureListener);
                    PhotoViewer.this.changedTextureView.setVisibility(0);
                    return true;
                }
            }
            return false;
        }

        @Override // org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
            PhotoViewer.this.checkChangedTextureView(false);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$56$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSurfaceTextureUpdated$3();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSurfaceTextureUpdated$3() {
            if (PhotoViewer.this.firstFrameView != null) {
                PhotoViewer.this.firstFrameView.checkFromPlayer(PhotoViewer.this.videoPlayer);
            }
        }
    }

    public void checkFullscreenButton() {
        MessageObject messageObject;
        int measuredWidth;
        TextureView textureView;
        int measuredHeight;
        TextureView textureView2;
        float f;
        MessageObject messageObject2;
        if (this.imagesArr.isEmpty() || ((messageObject = this.currentMessageObject) != null && messageObject.isSponsored())) {
            for (int i = 0; i < 3; i++) {
                this.fullscreenButton[i].setVisibility(4);
            }
            return;
        }
        int i2 = 0;
        while (i2 < 3) {
            int i3 = this.currentIndex;
            if (i2 == 1) {
                i3++;
            } else if (i2 == 2) {
                i3--;
            }
            if (i3 < 0 || i3 >= this.imagesArr.size()) {
                this.fullscreenButton[i2].setVisibility(4);
            } else {
                MessageObject messageObject3 = (MessageObject) this.imagesArr.get(i3);
                if (!messageObject3.isVideo() && !messageObject3.isYouTubeVideo()) {
                    this.fullscreenButton[i2].setVisibility(4);
                } else {
                    boolean z = messageObject3.isYouTubeVideo() && (messageObject2 = this.currentMessageObject) != null && messageObject2.getId() == messageObject3.getId();
                    if (z) {
                        measuredWidth = messageObject3.messageOwner.media.webpage.embed_width;
                    } else {
                        measuredWidth = (i2 != 0 || (textureView = this.videoTextureView) == null) ? 0 : textureView.getMeasuredWidth();
                    }
                    if (z) {
                        measuredHeight = messageObject3.messageOwner.media.webpage.embed_height;
                    } else {
                        measuredHeight = (i2 != 0 || (textureView2 = this.videoTextureView) == null) ? 0 : textureView2.getMeasuredHeight();
                    }
                    TLRPC.Document document = messageObject3.getDocument();
                    if (document != null) {
                        int size = document.attributes.size();
                        int i4 = 0;
                        while (true) {
                            if (i4 >= size) {
                                break;
                            }
                            TLRPC.DocumentAttribute documentAttribute = document.attributes.get(i4);
                            if (documentAttribute instanceof TLRPC.TL_documentAttributeVideo) {
                                measuredWidth = documentAttribute.f1582w;
                                measuredHeight = documentAttribute.f1581h;
                                break;
                            }
                            i4++;
                        }
                    }
                    Point point = AndroidUtilities.displaySize;
                    if (point.y > point.x && measuredWidth > measuredHeight) {
                        if (this.fullscreenButton[i2].getVisibility() != 0) {
                            this.fullscreenButton[i2].setVisibility(0);
                        }
                        if (this.isActionBarVisible) {
                            this.fullscreenButton[i2].setAlpha(1.0f);
                        }
                        ((FrameLayout.LayoutParams) this.fullscreenButton[i2].getLayoutParams()).topMargin = ((this.containerView.getMeasuredHeight() + ((int) (measuredHeight / (measuredWidth / this.containerView.getMeasuredWidth())))) / 2) - AndroidUtilities.m1146dp(48.0f);
                    } else if (this.fullscreenButton[i2].getVisibility() != 4) {
                        this.fullscreenButton[i2].setVisibility(4);
                    }
                    if (this.imageMoveAnimation != null) {
                        float f2 = this.translationX;
                        f = f2 + ((this.animateToX - f2) * this.animationValue);
                    } else {
                        f = this.translationX;
                    }
                    float fM1146dp = 0.0f;
                    if (i2 != 1) {
                        if (i2 == 2) {
                            fM1146dp = ((-AndroidUtilities.displaySize.x) - AndroidUtilities.m1146dp(15.0f)) + (f - this.maxX);
                        } else {
                            float f3 = this.minX;
                            if (f < f3) {
                                fM1146dp = f - f3;
                            }
                        }
                    }
                    this.fullscreenButton[i2].setTranslationX((fM1146dp + AndroidUtilities.displaySize.x) - AndroidUtilities.m1146dp(48.0f));
                }
            }
            i2++;
        }
    }

    private void createVideoTextureView(final MediaController.SavedFilterState savedFilterState) {
        if (this.videoTextureView != null) {
            return;
        }
        AspectRatioFrameLayout aspectRatioFrameLayout = new AspectRatioFrameLayout(this.parentActivity) { // from class: org.telegram.ui.PhotoViewer.57
            @Override // com.google.android.exoplayer2.p013ui.AspectRatioFrameLayout, android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                super.onMeasure(i, i2);
                if (PhotoViewer.this.textureImageView != null) {
                    ViewGroup.LayoutParams layoutParams = PhotoViewer.this.textureImageView.getLayoutParams();
                    layoutParams.width = getMeasuredWidth();
                    layoutParams.height = getMeasuredHeight();
                }
                if (PhotoViewer.this.videoTextureView instanceof VideoEditTextureView) {
                    PhotoViewer.this.videoTextureView.setPivotX(PhotoViewer.this.videoTextureView.getMeasuredWidth() / 2);
                    PhotoViewer.this.firstFrameView.setPivotX(PhotoViewer.this.videoTextureView.getMeasuredWidth() / 2);
                } else {
                    if (PhotoViewer.this.videoTextureView != null) {
                        PhotoViewer.this.videoTextureView.setPivotX(0.0f);
                    }
                    if (PhotoViewer.this.videoSurfaceView != null) {
                        PhotoViewer.this.videoSurfaceView.setPivotX(0.0f);
                    }
                    PhotoViewer.this.firstFrameView.setPivotX(0.0f);
                }
                PhotoViewer.this.checkFullscreenButton();
            }

            @Override // android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view, long j) {
                if (view == PhotoViewer.this.lastFrameImageView && PhotoViewer.this.skipLastFrameDraw) {
                    return true;
                }
                return super.drawChild(canvas, view, j);
            }

            @Override // android.view.View
            public void draw(Canvas canvas) {
                if (PhotoViewer.this.textureViewSkipRender) {
                    return;
                }
                super.draw(canvas);
            }
        };
        this.aspectRatioFrameLayout = aspectRatioFrameLayout;
        aspectRatioFrameLayout.setWillNotDraw(false);
        this.aspectRatioFrameLayout.setVisibility(4);
        this.containerView.addView(this.aspectRatioFrameLayout, 0, LayoutHelper.createFrame(-1, -1, 17));
        this.usedSurfaceView = false;
        if (this.imagesArrLocals.isEmpty()) {
            if (this.ALLOW_USE_SURFACE && this.injectingVideoPlayerSurface == null) {
                this.videoSurfaceView = new SurfaceView(this.parentActivity);
                this.usedSurfaceView = true;
            } else {
                this.videoTextureView = new TextureView(this.parentActivity);
            }
        } else {
            VideoEditTextureView videoEditTextureView = new VideoEditTextureView(this.parentActivity, this.videoPlayer);
            this.blurManager.resetBitmap();
            videoEditTextureView.updateUiBlurManager(this.blurManager);
            if (savedFilterState != null) {
                videoEditTextureView.setDelegate(new VideoEditTextureView.VideoEditTextureViewDelegate() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda113
                    @Override // org.telegram.ui.Components.VideoEditTextureView.VideoEditTextureViewDelegate
                    public final void onEGLThreadAvailable(FilterGLThread filterGLThread) {
                        filterGLThread.setFilterGLThreadDelegate(FilterShaders.getFilterShadersDelegate(savedFilterState));
                    }
                });
            }
            this.videoTextureView = videoEditTextureView;
        }
        SurfaceTexture surfaceTexture = this.injectingVideoPlayerSurface;
        if (surfaceTexture != null) {
            this.videoTextureView.setSurfaceTexture(surfaceTexture);
            this.textureUploaded = true;
            this.videoSizeSet = true;
            this.injectingVideoPlayerSurface = null;
        }
        TextureView textureView = this.videoTextureView;
        if (textureView != null) {
            textureView.setPivotX(0.0f);
            this.videoTextureView.setPivotY(0.0f);
            this.videoTextureView.setOpaque(false);
            this.aspectRatioFrameLayout.addView(this.videoTextureView, LayoutHelper.createFrame(-1, -1, 17));
        } else {
            this.aspectRatioFrameLayout.addView(this.videoSurfaceView, LayoutHelper.createFrame(-1, -1, 17));
        }
        FirstFrameView firstFrameView = new FirstFrameView(this.parentActivity);
        this.firstFrameView = firstFrameView;
        firstFrameView.setPivotX(0.0f);
        this.firstFrameView.setPivotY(0.0f);
        this.firstFrameView.setScaleType(ImageView.ScaleType.FIT_XY);
        this.aspectRatioFrameLayout.addView(this.firstFrameView, LayoutHelper.createFrame(-1, -1, 17));
        View view = new View(this.parentActivity);
        this.pipPlaceholderView = view;
        this.aspectRatioFrameLayout.addView(view, LayoutHelper.createFrame(-1, -1.0f));
        if (this.sendPhotoType == 1) {
            View view2 = new View(this.parentActivity);
            this.flashView = view2;
            view2.setBackgroundColor(-1);
            this.flashView.setAlpha(0.0f);
            this.aspectRatioFrameLayout.addView(this.flashView, LayoutHelper.createFrame(-1, -1, 17));
        }
        PipSource pipSource = this.pipSource;
        if (pipSource != null) {
            pipSource.setContentView(this.aspectRatioFrameLayout);
            this.pipSource.setPlaceholderView(this.pipPlaceholderView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releasePlayer(boolean z) {
        this.usedSurfaceView = false;
        PipSource pipSource = this.pipSource;
        if (pipSource != null) {
            pipSource.destroy();
            this.pipSource = null;
        }
        if (this.videoPlayer != null) {
            cancelVideoPlayRunnable();
            AndroidUtilities.cancelRunOnUIThread(this.setLoadingRunnable);
            AndroidUtilities.cancelRunOnUIThread(this.hideActionBarRunnable);
            if (this.shouldSavePositionForCurrentVideoShortTerm != null) {
                float currentPosition = this.videoPlayer.getCurrentPosition() / this.videoPlayer.getDuration();
                savedVideoPositions.put(this.shouldSavePositionForCurrentVideoShortTerm, new SavedVideoPosition(currentPosition, SystemClock.elapsedRealtime()));
                MessageObject messageObject = this.currentMessageObject;
                if (messageObject != null) {
                    messageObject.cachedSavedTimestamp = Float.valueOf(currentPosition);
                }
            }
            if (z) {
                requestAudioFocus(false);
            }
            this.videoPlayer.releasePlayer(true);
            this.videoPlayer = null;
        } else {
            this.playerWasPlaying = false;
        }
        if (this.photoViewerWebView != null) {
            AndroidUtilities.cancelRunOnUIThread(this.hideActionBarRunnable);
            if (this.shouldSavePositionForCurrentVideoShortTerm != null) {
                float currentVideoPosition = getCurrentVideoPosition() / getVideoDuration();
                savedVideoPositions.put(this.shouldSavePositionForCurrentVideoShortTerm, new SavedVideoPosition(currentVideoPosition, SystemClock.elapsedRealtime()));
                MessageObject messageObject2 = this.currentMessageObject;
                if (messageObject2 != null) {
                    messageObject2.cachedSavedTimestamp = Float.valueOf(currentVideoPosition);
                }
            }
        }
        OrientationEventListener orientationEventListener = this.orientationEventListener;
        if (orientationEventListener != null) {
            orientationEventListener.disable();
            this.orientationEventListener = null;
        }
        this.videoPreviewFrame.close();
        toggleMiniProgress(false, false);
        this.pipAvailable = false;
        this.playerInjected = false;
        if (this.pipItem.isEnabled()) {
            this.pipItem.setEnabled(false);
            this.pipItem.animate().alpha(0.5f).setDuration(175L).withEndAction(null).start();
        }
        if (this.keepScreenOnFlagSet) {
            try {
                this.parentActivity.getWindow().clearFlags(128);
                this.keepScreenOnFlagSet = false;
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
        AspectRatioFrameLayout aspectRatioFrameLayout = this.aspectRatioFrameLayout;
        if (aspectRatioFrameLayout != null) {
            try {
                this.containerView.removeView(aspectRatioFrameLayout);
            } catch (Throwable unused) {
            }
            this.aspectRatioFrameLayout = null;
        }
        cancelFlashAnimations();
        this.flashView = null;
        TextureView textureView = this.videoTextureView;
        if (textureView != null) {
            if (textureView instanceof VideoEditTextureView) {
                ((VideoEditTextureView) textureView).release();
            }
            this.videoTextureView = null;
        }
        this.blurManager.resetBitmap();
        if (this.videoSurfaceView != null) {
            this.videoSurfaceView = null;
        }
        if (this.isPlaying) {
            this.isPlaying = false;
            AndroidUtilities.cancelRunOnUIThread(this.updateProgressRunnable);
        }
        if (!z && !this.inPreview && !this.requestingPreview) {
            setVideoPlayerControlVisible(false, true);
        }
        this.photoProgressViews[0].resetAlphas();
    }

    private void setVideoPlayerControlVisible(final boolean z, boolean z2) {
        if (this.videoPlayerControlVisible != z) {
            if (z) {
                this.bottomLayout.setTag(1);
            } else {
                this.bottomLayout.setTag(null);
            }
            Animator animator = this.videoPlayerControlAnimator;
            if (animator != null) {
                animator.cancel();
            }
            this.videoPlayerControlVisible = z;
            if (z2) {
                if (z) {
                    this.videoPlayerControlFrameLayout.setVisibility(0);
                }
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.videoPlayerControlFrameLayout.getAlpha(), z ? 1.0f : 0.0f);
                valueAnimatorOfFloat.setDuration(200L);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$setVideoPlayerControlVisible$100(valueAnimator);
                    }
                });
                valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.58
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator2) {
                        if (z) {
                            return;
                        }
                        PhotoViewer.this.videoPlayerControlFrameLayout.setVisibility(8);
                    }
                });
                this.videoPlayerControlAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.start();
            } else {
                this.videoPlayerControlFrameLayout.setVisibility(z ? 0 : 8);
                this.videoPlayerControlFrameLayout.setAlpha(z ? 1.0f : 0.0f);
            }
            if (this.allowShare && this.pageBlocksAdapter == null) {
                if (z) {
                    this.menuItem.showSubItem(9);
                } else {
                    this.menuItem.hideSubItem(9);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setVideoPlayerControlVisible$100(ValueAnimator valueAnimator) {
        this.videoPlayerControlFrameLayout.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    private void updateCaptionTextForCurrentPhoto(Object obj) {
        CharSequence charSequence;
        if (this.hasCaptionForAllMedia) {
            charSequence = this.captionForAllMedia;
        } else if (obj instanceof MediaController.PhotoEntry) {
            charSequence = ((MediaController.PhotoEntry) obj).caption;
        } else {
            charSequence = (!(obj instanceof TLRPC.BotInlineResult) && (obj instanceof MediaController.SearchImage)) ? ((MediaController.SearchImage) obj).caption : null;
        }
        if (TextUtils.isEmpty(charSequence)) {
            getCaptionView().setText("");
        } else {
            getCaptionView().setText(AnimatedEmojiSpan.cloneSpans(charSequence, 3));
        }
        getCaptionView().editText.getEditText().setAllowTextEntitiesIntersection(supportsSendingNewEntities());
    }

    public void showAlertDialog(AlertDialog.Builder builder) {
        if (this.parentActivity == null) {
            return;
        }
        try {
            AlertDialog alertDialog = this.visibleDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.visibleDialog = null;
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        try {
            AlertDialog alertDialogShow = builder.show();
            this.visibleDialog = alertDialogShow;
            alertDialogShow.setCanceledOnTouchOutside(true);
            this.visibleDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda150
                @Override // android.content.DialogInterface.OnDismissListener
                public final void onDismiss(DialogInterface dialogInterface) {
                    this.f$0.lambda$showAlertDialog$101(dialogInterface);
                }
            });
        } catch (Exception e2) {
            FileLog.m1160e(e2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAlertDialog$101(DialogInterface dialogInterface) {
        this.visibleDialog = null;
    }

    public void mergeImages(String str, String str2, Bitmap bitmap, Bitmap bitmap2, float f, boolean z) {
        mergeImages(str, str2, bitmap, bitmap2, getCompressFormat(), f, z);
    }

    public static void mergeImages(String str, String str2, Bitmap bitmap, Bitmap bitmap2, Bitmap.CompressFormat compressFormat, float f, boolean z) {
        boolean z2;
        if (bitmap == null) {
            try {
                bitmap = BitmapFactory.decodeFile(str2);
                z2 = true;
            } catch (Throwable th) {
                FileLog.m1160e(th);
                return;
            }
        } else {
            z2 = false;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f2 = width;
        if (f2 > f || height > f) {
            float fMax = Math.max(width, height) / f;
            height = (int) (height / fMax);
            width = (int) (f2 / fMax);
        }
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Rect rect = new Rect(0, 0, width, height);
        if (z) {
            canvas.drawBitmap(bitmap2, (Rect) null, rect, bitmapPaint);
            canvas.drawBitmap(bitmap, (Rect) null, rect, bitmapPaint);
        } else {
            canvas.drawBitmap(bitmap, (Rect) null, rect, bitmapPaint);
            canvas.drawBitmap(bitmap2, (Rect) null, rect, bitmapPaint);
        }
        FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
        bitmapCreateBitmap.compress(compressFormat, f == 512.0f ? 83 : 87, fileOutputStream);
        try {
            fileOutputStream.close();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        if (z2) {
            bitmap.recycle();
        }
        bitmapCreateBitmap.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void seekAnimatedStickersTo(long j) {
        RLottieDrawable lottieAnimation;
        ArrayList arrayList = this.editState.mediaEntities;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                VideoEditedInfo.MediaEntity mediaEntity = (VideoEditedInfo.MediaEntity) this.editState.mediaEntities.get(i);
                if (mediaEntity.type == 0 && (mediaEntity.subType & 1) != 0) {
                    View view = mediaEntity.view;
                    if ((view instanceof BackupImageView) && (lottieAnimation = ((BackupImageView) view).getImageReceiver().getLottieAnimation()) != null) {
                        long j2 = this.startTime;
                        lottieAnimation.setProgressMs(j - (j2 > 0 ? j2 / 1000 : 0L));
                    }
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playOrStopAnimatedStickers(boolean z) {
        RLottieDrawable lottieAnimation;
        ArrayList arrayList = this.editState.mediaEntities;
        if (arrayList != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                VideoEditedInfo.MediaEntity mediaEntity = (VideoEditedInfo.MediaEntity) this.editState.mediaEntities.get(i);
                if (mediaEntity.type == 0 && (mediaEntity.subType & 1) != 0) {
                    View view = mediaEntity.view;
                    if ((view instanceof BackupImageView) && (lottieAnimation = ((BackupImageView) view).getImageReceiver().getLottieAnimation()) != null) {
                        if (z) {
                            lottieAnimation.start();
                        } else {
                            lottieAnimation.stop();
                        }
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int getAnimatedMediaEntitiesCount(boolean r7) {
        /*
            r6 = this;
            org.telegram.ui.PhotoViewer$EditState r0 = r6.editState
            java.util.ArrayList r0 = r0.mediaEntities
            r1 = 0
            if (r0 == 0) goto L37
            int r0 = r0.size()
            r2 = 0
        Lc:
            if (r1 >= r0) goto L36
            org.telegram.ui.PhotoViewer$EditState r3 = r6.editState
            java.util.ArrayList r3 = r3.mediaEntities
            java.lang.Object r3 = r3.get(r1)
            org.telegram.messenger.VideoEditedInfo$MediaEntity r3 = (org.telegram.messenger.VideoEditedInfo.MediaEntity) r3
            byte r4 = r3.type
            if (r4 != 0) goto L26
            byte r4 = r3.subType
            r5 = r4 & 1
            if (r5 != 0) goto L2e
            r4 = r4 & 4
            if (r4 != 0) goto L2e
        L26:
            java.util.ArrayList<org.telegram.messenger.VideoEditedInfo$EmojiEntity> r3 = r3.entities
            boolean r3 = r3.isEmpty()
            if (r3 != 0) goto L33
        L2e:
            int r2 = r2 + 1
            if (r7 == 0) goto L33
            return r2
        L33:
            int r1 = r1 + 1
            goto Lc
        L36:
            return r2
        L37:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.getAnimatedMediaEntitiesCount(boolean):int");
    }

    private boolean hasAnimatedMediaEntities() {
        return getAnimatedMediaEntitiesCount(true) != 0;
    }

    public static Bitmap createCroppedBitmap(Bitmap bitmap, MediaController.CropState cropState, int[] iArr, boolean z) {
        int i;
        int i2;
        try {
            int i3 = 0;
            int i4 = (cropState.transformRotation + (iArr != null ? iArr[0] : 0)) % 360;
            if (iArr != null && iArr.length > 1) {
                i3 = iArr[1];
            }
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            if (i4 == 90 || i4 == 270) {
                i = width;
                i2 = height;
            } else {
                i2 = width;
                i = height;
            }
            float f = i2;
            float f2 = i;
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) (cropState.cropPw * f), (int) (cropState.cropPh * f2), Bitmap.Config.ARGB_8888);
            Matrix matrix = new Matrix();
            matrix.postTranslate((-width) / 2, (-height) / 2);
            if (z && cropState.mirrored) {
                if (i4 == 90 || i4 == 270) {
                    matrix.postScale(1.0f, -1.0f);
                } else {
                    matrix.postScale(-1.0f, 1.0f);
                }
            }
            if (i3 == 1) {
                matrix.postScale(-1.0f, 1.0f);
            } else if (i3 == 2) {
                matrix.postScale(1.0f, -1.0f);
            }
            matrix.postRotate(cropState.cropRotate + i4);
            matrix.postTranslate(cropState.cropPx * f, cropState.cropPy * f2);
            float f3 = cropState.cropScale;
            matrix.postScale(f3, f3);
            matrix.postTranslate(r10 / 2, r11 / 2);
            new Canvas(bitmapCreateBitmap).drawBitmap(bitmap, matrix, new Paint(2));
            return bitmapCreateBitmap;
        } catch (Throwable th) {
            FileLog.m1160e(th);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x012a  */
    /* renamed from: applyCurrentEditMode, reason: merged with bridge method [inline-methods] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void lambda$setParentActivity$46(android.graphics.Bitmap r14) {
        /*
            Method dump skipped, instructions count: 478
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.lambda$setParentActivity$46(android.graphics.Bitmap):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0317  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x0591  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x06ce  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x06d3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void applyCurrentEditMode() {
        /*
            Method dump skipped, instructions count: 2133
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.applyCurrentEditMode():void");
    }

    private void setPhotoChecked() {
        ChatActivity chatActivity;
        TLRPC.Chat currentChat;
        PhotoViewerProvider photoViewerProvider = this.placeProvider;
        if (photoViewerProvider != null) {
            if (photoViewerProvider.getSelectedPhotos() != null && this.maxSelectedPhotos > 0 && this.placeProvider.getSelectedPhotos().size() >= this.maxSelectedPhotos && !this.placeProvider.isPhotoChecked(this.currentIndex)) {
                if (!this.allowOrder || (chatActivity = this.parentChatActivity) == null || (currentChat = chatActivity.getCurrentChat()) == null || ChatObject.hasAdminRights(currentChat) || !currentChat.slowmode_enabled) {
                    return;
                }
                AlertsCreator.createSimpleAlert(this.parentActivity, LocaleController.getString("Slowmode", C2369R.string.Slowmode), LocaleController.getString("SlowmodeSelectSendError", C2369R.string.SlowmodeSelectSendError)).show();
                return;
            }
            int photoChecked = this.placeProvider.setPhotoChecked(this.currentIndex, getCurrentVideoEditedInfo());
            boolean zIsPhotoChecked = this.placeProvider.isPhotoChecked(this.currentIndex);
            this.checkImageView.setChecked(zIsPhotoChecked, true);
            if (photoChecked >= 0) {
                if (zIsPhotoChecked) {
                    this.selectedPhotosAdapter.notifyItemInserted(photoChecked);
                    this.selectedPhotosListView.smoothScrollToPosition(photoChecked);
                } else {
                    this.selectedPhotosAdapter.notifyItemRemoved(photoChecked);
                    if (photoChecked == 0) {
                        this.selectedPhotosAdapter.notifyItemChanged(0);
                    }
                }
            }
            updateSelectedCount();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateResetButtonVisibility(final boolean z) {
        if (this.resetButton.isClickable() != z) {
            this.resetButton.setClickable(z);
            this.resetButton.setVisibility(0);
            this.resetButton.clearAnimation();
            this.resetButton.animate().alpha(z ? 1.0f : 0.0f).setInterpolator(CubicBezierInterpolator.DEFAULT).setDuration(150L).withEndAction(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda158
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateResetButtonVisibility$102(z);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateResetButtonVisibility$102(boolean z) {
        if (z) {
            return;
        }
        this.resetButton.setVisibility(8);
    }

    private void createCropView() {
        if (this.photoCropView != null) {
            return;
        }
        PhotoCropView photoCropView = new PhotoCropView(this.activityContext, this.resourcesProvider);
        this.photoCropView = photoCropView;
        photoCropView.setVisibility(8);
        this.photoCropView.onDisappear();
        this.containerView.addView(this.photoCropView, this.containerView.indexOfChild(this.videoTimelineViewContainer) - 1, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, 48.0f));
        this.photoCropView.setDelegate(new C583259());
    }

    /* renamed from: org.telegram.ui.PhotoViewer$59 */
    /* loaded from: classes5.dex */
    class C583259 implements PhotoCropView.PhotoCropViewDelegate {
        C583259() {
        }

        @Override // org.telegram.ui.Components.PhotoCropView.PhotoCropViewDelegate
        public void onChange(boolean z) {
            PhotoViewer.this.updateResetButtonVisibility(!z);
        }

        @Override // org.telegram.ui.Components.PhotoCropView.PhotoCropViewDelegate
        public void onUpdate() {
            PhotoViewer.this.containerView.invalidate();
        }

        @Override // org.telegram.ui.Components.PhotoCropView.PhotoCropViewDelegate
        public void onTapUp() {
            if (PhotoViewer.this.sendPhotoType == 1) {
                PhotoViewer.this.manuallyPaused = true;
                PhotoViewer.this.toggleVideoPlayer();
            }
        }

        @Override // org.telegram.ui.Components.PhotoCropView.PhotoCropViewDelegate
        public void onVideoThumbClick() {
            if (PhotoViewer.this.videoPlayer == null) {
                return;
            }
            PhotoViewer.this.videoPlayer.seekTo((long) (PhotoViewer.this.videoPlayer.getDuration() * PhotoViewer.this.avatarStartProgress));
            PhotoViewer.this.videoPlayer.pause();
            PhotoViewer.this.videoTimelineView.setProgress(PhotoViewer.this.avatarStartProgress);
            PhotoViewer.this.cancelVideoPlayRunnable();
            PhotoViewer photoViewer = PhotoViewer.this;
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer$59$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onVideoThumbClick$0();
                }
            };
            photoViewer.videoPlayRunnable = runnable;
            AndroidUtilities.runOnUIThread(runnable, 860L);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onVideoThumbClick$0() {
            PhotoViewer.this.manuallyPaused = false;
            if (PhotoViewer.this.videoPlayer != null) {
                PhotoViewer.this.videoPlayer.play();
            }
            PhotoViewer.this.videoPlayRunnable = null;
        }

        @Override // org.telegram.ui.Components.PhotoCropView.PhotoCropViewDelegate
        public boolean rotate() {
            return PhotoViewer.this.cropRotate(-90.0f);
        }

        @Override // org.telegram.ui.Components.PhotoCropView.PhotoCropViewDelegate
        public boolean mirror() {
            return PhotoViewer.this.cropMirror();
        }

        @Override // org.telegram.ui.Components.PhotoCropView.PhotoCropViewDelegate
        public int getVideoThumbX() {
            return (int) (AndroidUtilities.m1146dp(16.0f) + ((PhotoViewer.this.videoTimelineView.getMeasuredWidth() - AndroidUtilities.m1146dp(32.0f)) * PhotoViewer.this.avatarStartProgress));
        }
    }

    private void startVideoPlayer() {
        VideoPlayer videoPlayer;
        if (!this.isCurrentVideo || (videoPlayer = this.videoPlayer) == null || videoPlayer.isPlaying()) {
            return;
        }
        if (!this.muteVideo || this.sendPhotoType == 1) {
            this.videoPlayer.setVolume(0.0f);
        }
        this.manuallyPaused = false;
        toggleVideoPlayer();
    }

    private void detectFaces() {
        if (this.centerImage.getAnimation() != null || this.imagesArrLocals.isEmpty() || this.sendPhotoType == 1) {
            return;
        }
        String imageKey = this.centerImage.getImageKey();
        String str = this.currentImageFaceKey;
        if (str == null || !str.equals(imageKey)) {
            this.currentImageHasFace = 0;
            detectFaces(imageKey, this.centerImage.getBitmapSafe(), this.centerImage.getOrientation());
        }
    }

    private void detectFaces(final String str, final ImageReceiver.BitmapHolder bitmapHolder, final int i) {
        if (str == null || bitmapHolder == null || bitmapHolder.bitmap == null) {
            return;
        }
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda128
            @Override // java.lang.Runnable
            public final void run() throws Throwable {
                this.f$0.lambda$detectFaces$105(bitmapHolder, i, str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$detectFaces$105(final org.telegram.messenger.ImageReceiver.BitmapHolder r10, int r11, final java.lang.String r12) throws java.lang.Throwable {
        /*
            r9 = this;
            r1 = 0
            com.google.android.gms.vision.face.FaceDetector$Builder r0 = new com.google.android.gms.vision.face.FaceDetector$Builder     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            android.content.Context r2 = org.telegram.messenger.ApplicationLoader.applicationContext     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            r2 = 0
            com.google.android.gms.vision.face.FaceDetector$Builder r0 = r0.setMode(r2)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            com.google.android.gms.vision.face.FaceDetector$Builder r0 = r0.setLandmarkType(r2)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            com.google.android.gms.vision.face.FaceDetector$Builder r0 = r0.setTrackingEnabled(r2)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            com.google.android.gms.vision.face.FaceDetector r1 = r0.build()     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            boolean r0 = r1.isOperational()     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            if (r0 == 0) goto L64
            com.google.android.gms.vision.Frame$Builder r0 = new com.google.android.gms.vision.Frame$Builder     // Catch: java.lang.Throwable -> L41
            r0.<init>()     // Catch: java.lang.Throwable -> L41
            android.graphics.Bitmap r3 = r10.bitmap     // Catch: java.lang.Throwable -> L41
            com.google.android.gms.vision.Frame$Builder r0 = r0.setBitmap(r3)     // Catch: java.lang.Throwable -> L41
            com.google.android.gms.vision.Frame$Builder r11 = r0.setRotation(r11)     // Catch: java.lang.Throwable -> L41
            com.google.android.gms.vision.Frame r11 = r11.build()     // Catch: java.lang.Throwable -> L41
            android.util.SparseArray r11 = r1.detect(r11)     // Catch: java.lang.Throwable -> L41
            r0 = 1
            if (r11 == 0) goto L44
            int r11 = r11.size()     // Catch: java.lang.Throwable -> L41
            if (r11 == 0) goto L44
            r2 = 1
            goto L44
        L41:
            r0 = move-exception
            r11 = r0
            goto L47
        L44:
            r8 = r2
            r5 = 1
            goto L4c
        L47:
            org.telegram.messenger.FileLog.m1160e(r11)     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            r5 = 0
            r8 = 0
        L4c:
            org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda133 r3 = new org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda133     // Catch: java.lang.Throwable -> L5e java.lang.Exception -> L61
            r4 = r9
            r6 = r10
            r7 = r12
            r3.<init>()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r3)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            goto L78
        L58:
            r0 = move-exception
        L59:
            r10 = r0
            goto L83
        L5b:
            r0 = move-exception
        L5c:
            r10 = r0
            goto L7c
        L5e:
            r0 = move-exception
            r4 = r9
            goto L59
        L61:
            r0 = move-exception
            r4 = r9
            goto L5c
        L64:
            r4 = r9
            r6 = r10
            r7 = r12
            boolean r10 = org.telegram.messenger.BuildVars.LOGS_ENABLED     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            if (r10 == 0) goto L70
            java.lang.String r10 = "face detection is not operational"
            org.telegram.messenger.FileLog.m1158e(r10)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
        L70:
            org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda134 r10 = new org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda134     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            r10.<init>()     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
            org.telegram.messenger.AndroidUtilities.runOnUIThread(r10)     // Catch: java.lang.Throwable -> L58 java.lang.Exception -> L5b
        L78:
            r1.release()
            goto L82
        L7c:
            org.telegram.messenger.FileLog.m1160e(r10)     // Catch: java.lang.Throwable -> L58
            if (r1 == 0) goto L82
            goto L78
        L82:
            return
        L83:
            if (r1 == 0) goto L88
            r1.release()
        L88:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.lambda$detectFaces$105(org.telegram.messenger.ImageReceiver$BitmapHolder, int, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$detectFaces$103(boolean z, ImageReceiver.BitmapHolder bitmapHolder, String str, boolean z2) {
        if (!z) {
            bitmapHolder.release();
        }
        if (str.equals(this.centerImage.getImageKey())) {
            this.currentImageHasFace = z ? z2 ? 1 : 0 : 2;
            this.currentImageFaceKey = str;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$detectFaces$104(ImageReceiver.BitmapHolder bitmapHolder, String str) {
        bitmapHolder.release();
        if (str.equals(this.centerImage.getImageKey())) {
            this.currentImageHasFace = 2;
            this.currentImageFaceKey = str;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0083  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void switchToEditMode(final int r37) {
        /*
            Method dump skipped, instructions count: 3898
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.switchToEditMode(int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToEditMode$106(ValueAnimator valueAnimator) {
        this.photoCropView.cropView.areaView.setRotationScaleTranslation(0.0f, AndroidUtilities.lerp(this.scale, this.animateToScale, this.animationValue), AndroidUtilities.lerp(this.translationX, this.animateToX, this.animationValue), AndroidUtilities.lerp(this.translationY, this.animateToY, this.animationValue));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToEditMode$107(ValueAnimator valueAnimator) {
        this.photoPaintView.setOffsetTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f, 0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToEditMode$108(ValueAnimator valueAnimator) {
        this.photoPaintView.setOffsetTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* renamed from: org.telegram.ui.PhotoViewer$60 */
    /* loaded from: classes5.dex */
    class C583460 extends AnimatorListenerAdapter {
        final /* synthetic */ int val$mode;

        C583460(int i) {
            this.val$mode = i;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) throws Resources.NotFoundException, NumberFormatException {
            if (PhotoViewer.this.currentEditMode == 1) {
                PhotoViewer.this.photoCropView.onDisappear();
                PhotoViewer.this.photoCropView.onHide();
                PhotoViewer.this.editorDoneLayout.setVisibility(8);
                PhotoViewer.this.photoCropView.setVisibility(8);
                PhotoViewer.this.photoCropView.cropView.areaView.setRotationScaleTranslation(0.0f, 1.0f, 0.0f, 0.0f);
            } else if (PhotoViewer.this.currentEditMode == 2) {
                try {
                    PhotoViewer.this.containerView.removeView(PhotoViewer.this.photoFilterView);
                } catch (Exception e) {
                    FileLog.m1160e(e);
                }
                PhotoViewer.this.photoFilterView = null;
            } else if (PhotoViewer.this.currentEditMode == 3) {
                PhotoViewer.this.photoPaintView.onAnimationStateChanged(false);
                try {
                    PhotoViewer.this.containerView.removeView(PhotoViewer.this.photoPaintView.getView());
                } catch (Exception e2) {
                    FileLog.m1160e(e2);
                }
                PhotoViewer.this.photoPaintView = null;
            } else if (PhotoViewer.this.currentEditMode == 4) {
                PhotoViewer.this.maskPaintViewShuttingDown = true;
                PhotoViewer.this.containerView.invalidate();
                final MaskPaintView maskPaintView = PhotoViewer.this.maskPaintView;
                PhotoViewer.this.containerView.post(new Runnable() { // from class: org.telegram.ui.PhotoViewer$60$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onAnimationEnd$0(maskPaintView);
                    }
                });
                PhotoViewer.this.maskPaintView = null;
                PhotoViewer.this.eraseBtn.setActive(false, true);
                PhotoViewer.this.restoreBtn.setActive(false, true);
            } else if (PhotoViewer.this.currentEditMode == 5) {
                PhotoViewer.this.coverEditor.setVisibility(8);
                PhotoViewer.this.coverEditor.destroy();
            }
            PhotoViewer.this.imageMoveAnimation = null;
            final int i = PhotoViewer.this.currentEditMode;
            PhotoViewer.this.currentEditMode = this.val$mode;
            PhotoViewer.this.getCaptionView().keyboardNotifier.ignore(PhotoViewer.this.currentEditMode != 0);
            if (PhotoViewer.this.paintKeyboardNotifier != null) {
                PhotoViewer.this.paintKeyboardNotifier.ignore(PhotoViewer.this.currentEditMode != 3);
            }
            if (PhotoViewer.this.currentEditMode != 3) {
                PhotoViewer.this.translateY = 0.0f;
            }
            PhotoViewer.this.applying = false;
            if (PhotoViewer.this.sendPhotoType == 1) {
                PhotoViewer.this.photoCropView.setVisibility(0);
            }
            if (PhotoViewer.this.sendPhotoType == 11 && (i == 3 || i == 2 || i == 1 || i == 4)) {
                PhotoViewer photoViewer = PhotoViewer.this;
                photoViewer.animateToX = photoViewer.savedTx;
                PhotoViewer photoViewer2 = PhotoViewer.this;
                photoViewer2.animateToY = photoViewer2.savedTy;
                PhotoViewer photoViewer3 = PhotoViewer.this;
                photoViewer3.animateToScale = photoViewer3.savedScale;
                PhotoViewer photoViewer4 = PhotoViewer.this;
                photoViewer4.animateToRotate = photoViewer4.savedRotation;
            } else {
                PhotoViewer photoViewer5 = PhotoViewer.this;
                float fScale1 = photoViewer5.scale1(false);
                photoViewer5.animateToScale = fScale1;
                photoViewer5.scale = fScale1;
                PhotoViewer photoViewer6 = PhotoViewer.this;
                photoViewer6.updateMinMax(photoViewer6.scale);
                PhotoViewer.this.animateToX = 0.0f;
                PhotoViewer.this.animateToY = 0.0f;
            }
            PhotoViewer.this.switchingToMode = -1;
            PhotoViewer.this.containerView.invalidate();
            if (PhotoViewer.this.savedState != null) {
                PhotoViewer.this.savedState.restore();
                PhotoViewer.this.savedState = null;
                ActionBarToggleParams actionBarToggleParamsEnableStatusBarAnimation = new ActionBarToggleParams().enableStatusBarAnimation(false);
                PhotoViewer.this.toggleActionBar(false, false, actionBarToggleParamsEnableStatusBarAnimation);
                PhotoViewer.this.toggleActionBar(true, true, actionBarToggleParamsEnableStatusBarAnimation);
                return;
            }
            AnimatorSet animatorSet = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            FrameLayout frameLayout = PhotoViewer.this.pickerView;
            Property property = View.TRANSLATION_Y;
            arrayList.add(ObjectAnimator.ofFloat(frameLayout, (Property<FrameLayout, Float>) property, 0.0f));
            FrameLayout frameLayout2 = PhotoViewer.this.pickerView;
            Property property2 = View.ALPHA;
            arrayList.add(ObjectAnimator.ofFloat(frameLayout2, (Property<FrameLayout, Float>) property2, 1.0f));
            StickerMakerView stickerMakerView = PhotoViewer.this.stickerMakerView;
            if (stickerMakerView != null) {
                arrayList.add(ObjectAnimator.ofFloat(stickerMakerView, (Property<StickerMakerView, Float>) property2, 1.0f));
            }
            if (PhotoViewer.this.stickerMakerBackgroundView != null) {
                arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.stickerMakerBackgroundView, (Property<StickerMakerBackgroundView, Float>) property2, 1.0f));
            }
            arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.pickerViewSendButton, (Property<ChatActivityEnterView.SendButton, Float>) property, 0.0f));
            if (PhotoViewer.this.sendPhotoType != 1) {
                arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.actionBar, (Property<ActionBar, Float>) property, 0.0f));
            }
            if (PhotoViewer.this.needCaptionLayout) {
                arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.captionTextViewSwitcher, (Property<CaptionTextViewSwitcher, Float>) property, 0.0f));
            }
            if (PhotoViewer.this.sendPhotoType == 0 || PhotoViewer.this.sendPhotoType == 4) {
                arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.checkImageView, (Property<CheckBox, Float>) property2, 1.0f));
                arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.photosCounterView, (Property<CounterView, Float>) property2, 1.0f));
            } else if (PhotoViewer.this.sendPhotoType == 1) {
                arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.photoCropView, (Property<PhotoCropView, Float>) property2, 1.0f));
            }
            if (PhotoViewer.this.muteButton.getTag() != null) {
                PhotoViewer.this.muteButton.setVisibility(0);
                arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.muteButton, (Property<ImageView, Float>) property2, 1.0f));
            }
            if (PhotoViewer.this.editCoverButton.getTag() != null) {
                PhotoViewer.this.editCoverButton.setVisibility(0);
                arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.editCoverButton, (Property<EditCoverButton, Float>) property2, 1.0f));
            }
            if (PhotoViewer.this.navigationBar != null) {
                PhotoViewer.this.navigationBar.setVisibility(0);
                arrayList.add(ObjectAnimator.ofFloat(PhotoViewer.this.navigationBar, (Property<View, Float>) property2, 1.0f));
            }
            animatorSet.playTogether(arrayList);
            animatorSet.setDuration(200L);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.60.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator2) {
                    PhotoViewer.this.pickerView.setVisibility(0);
                    if (PhotoViewer.this.useFullWidthSendButton()) {
                        PhotoViewer.this.doneButtonFullWidth.setVisibility(0);
                    } else {
                        PhotoViewer.this.pickerViewSendButton.setVisibility(0);
                    }
                    PhotoViewer.this.actionBar.setVisibility(0);
                    if (PhotoViewer.this.needCaptionLayout) {
                        PhotoViewer.this.captionTextViewSwitcher.setVisibility(PhotoViewer.this.captionTextViewSwitcher.getTag() != null ? 0 : 4);
                    }
                    if (PhotoViewer.this.sendPhotoType == 0 || PhotoViewer.this.sendPhotoType == 4 || ((PhotoViewer.this.sendPhotoType == 2 || PhotoViewer.this.sendPhotoType == 5) && PhotoViewer.this.imagesArrLocals.size() > 1)) {
                        PhotoViewer.this.checkImageView.setVisibility(0);
                        PhotoViewer.this.photosCounterView.setVisibility(0);
                        PhotoViewer.this.updateActionBarTitlePadding();
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator2) {
                    if (PhotoViewer.this.videoConvertSupported && PhotoViewer.this.isCurrentVideo) {
                        PhotoViewer.this.updateVideoInfo();
                    }
                    if (i == 3) {
                        PhotoViewer photoViewer7 = PhotoViewer.this;
                        photoViewer7.setIsAboutToSwitchToIndex(photoViewer7.currentIndex, false, true, true);
                    }
                }
            });
            animatorSet.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAnimationEnd$0(MaskPaintView maskPaintView) {
            maskPaintView.shutdown();
            try {
                PhotoViewer.this.containerView.removeView(maskPaintView);
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToEditMode$109(View view) {
        applyCurrentEditMode();
        switchToEditMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToEditMode$111(View view) {
        if (this.photoFilterView.hasChanges()) {
            Activity activity = this.parentActivity;
            if (activity == null) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(activity, this.resourcesProvider);
            builder.setMessage(LocaleController.getString("DiscardChanges", C2369R.string.DiscardChanges));
            builder.setTitle(LocaleController.getString("AppName", C2369R.string.AppName));
            builder.setPositiveButton(LocaleController.getString("OK", C2369R.string.f1459OK), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda156
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$switchToEditMode$110(alertDialog, i);
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", C2369R.string.Cancel), null);
            showAlertDialog(builder);
            return;
        }
        switchToEditMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToEditMode$110(AlertDialog alertDialog, int i) {
        switchToEditMode(0);
    }

    private void createMaskPaintView() {
        MediaController.CropState cropState;
        if (this.maskPaintView != null) {
            return;
        }
        if (this.sendPhotoType == 1) {
            cropState = new MediaController.CropState();
            cropState.transformRotation = this.cropTransform.getOrientation();
        } else {
            cropState = this.editState.cropState;
        }
        MediaController.CropState cropState2 = cropState;
        int bitmapWidth = this.centerImage.getBitmapWidth();
        int bitmapHeight = this.centerImage.getBitmapHeight();
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, config);
        StickerMakerView stickerMakerView = this.stickerMakerView;
        if (stickerMakerView != null && stickerMakerView.getSourceBitmap() != null) {
            Bitmap sourceBitmap = this.stickerMakerView.getSourceBitmap();
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            canvas.translate(bitmapCreateBitmap.getWidth() / 2.0f, bitmapCreateBitmap.getHeight() / 2.0f);
            canvas.rotate(this.stickerMakerView.orientation);
            float width = bitmapCreateBitmap.getWidth() / ((this.stickerMakerView.orientation / 90) % 2 != 0 ? sourceBitmap.getHeight() : sourceBitmap.getWidth());
            RectF rectF = AndroidUtilities.rectTmp;
            rectF.set(((-sourceBitmap.getWidth()) / 2.0f) * width, ((-sourceBitmap.getHeight()) / 2.0f) * width, (sourceBitmap.getWidth() / 2.0f) * width, (sourceBitmap.getHeight() / 2.0f) * width);
            canvas.drawBitmap(sourceBitmap, (Rect) null, rectF, new Paint(3));
        }
        Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(this.centerImage.getBitmapWidth(), this.centerImage.getBitmapHeight(), config);
        if (this.centerImage.getBitmap() != null) {
            Bitmap bitmap = this.centerImage.getBitmap();
            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
            canvas2.translate(bitmapCreateBitmap2.getWidth() / 2.0f, bitmapCreateBitmap2.getHeight() / 2.0f);
            canvas2.rotate(this.centerImage.getOrientation());
            float width2 = bitmapCreateBitmap2.getWidth() / ((this.centerImage.getOrientation() / 90) % 2 != 0 ? bitmap.getHeight() : bitmap.getWidth());
            RectF rectF2 = AndroidUtilities.rectTmp;
            rectF2.set(((-bitmap.getWidth()) / 2.0f) * width2, ((-bitmap.getHeight()) / 2.0f) * width2, (bitmap.getWidth() / 2.0f) * width2, (bitmap.getHeight() / 2.0f) * width2);
            canvas2.drawBitmap(bitmap, (Rect) null, rectF2, new Paint(3));
        }
        MaskPaintView maskPaintView = new MaskPaintView(this.parentActivity, this.currentAccount, bitmapCreateBitmap2, bitmapCreateBitmap, this.centerImage.getOrientation(), cropState2) { // from class: org.telegram.ui.PhotoViewer.66
            @Override // org.telegram.p023ui.Components.Paint.Views.MaskPaintView
            protected void onRenderViewAlphaUpdate(ValueAnimator valueAnimator) {
                super.onRenderViewAlphaUpdate(valueAnimator);
                PhotoViewer.this.containerView.invalidate();
            }

            @Override // org.telegram.p023ui.Components.Paint.Views.MaskPaintView
            protected void onDrawn() {
                PhotoViewer.this.showEditStickerMode(true, true);
            }
        };
        this.maskPaintView = maskPaintView;
        maskPaintView.cancelButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda148
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createMaskPaintView$112(view);
            }
        });
        this.maskPaintView.doneButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda149
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createMaskPaintView$113(view);
            }
        });
        this.maskPaintView.setEraser(this.maskPaintViewEraser);
        int iIndexOfChild = this.containerView.indexOfChild(this.btnLayout) - 1;
        if (iIndexOfChild < 0) {
            iIndexOfChild = this.containerView.getChildCount();
        }
        this.containerView.addView(this.maskPaintView, iIndexOfChild, LayoutHelper.createFrame(-1, -1.0f));
        this.maskPaintViewShuttingDown = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createMaskPaintView$112(View view) {
        this.eraseBtn.setActive(false, true);
        this.restoreBtn.setActive(false, true);
        switchToEditMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createMaskPaintView$113(View view) {
        this.eraseBtn.setActive(false, true);
        this.restoreBtn.setActive(false, true);
        applyCurrentEditMode();
        switchToEditMode(0);
    }

    private void createPaintView() {
        int bitmapWidth;
        int bitmapHeight;
        MediaController.CropState cropState;
        if (this.photoPaintView == null) {
            TextureView textureView = this.videoTextureView;
            if (textureView != null) {
                VideoEditTextureView videoEditTextureView = (VideoEditTextureView) textureView;
                bitmapWidth = videoEditTextureView.getVideoWidth();
                bitmapHeight = videoEditTextureView.getVideoHeight();
                while (true) {
                    if (bitmapWidth <= 1280 && bitmapHeight <= 1280) {
                        break;
                    }
                    bitmapWidth /= 2;
                    bitmapHeight /= 2;
                }
            } else {
                bitmapWidth = this.centerImage.getBitmapWidth();
                bitmapHeight = this.centerImage.getBitmapHeight();
            }
            Bitmap bitmap = this.paintingOverlay.getBitmap();
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
            }
            Bitmap bitmap2 = bitmap;
            if (this.sendPhotoType == 1) {
                cropState = new MediaController.CropState();
                cropState.transformRotation = this.cropTransform.getOrientation();
            } else {
                cropState = this.editState.cropState;
            }
            MediaController.CropState cropState2 = cropState;
            KeyboardNotifier keyboardNotifier = new KeyboardNotifier(this.windowView, new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda164
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$createPaintView$115((Integer) obj);
                }
            });
            this.paintKeyboardNotifier = keyboardNotifier;
            keyboardNotifier.ignore(this.currentEditMode != 3);
            Activity activity = this.parentActivity;
            LPhotoPaintView lPhotoPaintView = new LPhotoPaintView(activity, activity, this.currentAccount, bitmap2, this.isCurrentVideo ? null : this.centerImage.getBitmap(), this.centerImage.getOrientation(), this.editState.mediaEntities, cropState2, new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda165
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createPaintView$116();
                }
            }, this.resourcesProvider) { // from class: org.telegram.ui.PhotoViewer.67
                @Override // org.telegram.p023ui.Components.Paint.Views.LPhotoPaintView
                protected void onOpenCloseStickersAlert(boolean z) {
                    if (PhotoViewer.this.videoPlayer == null) {
                        return;
                    }
                    PhotoViewer.this.manuallyPaused = false;
                    PhotoViewer.this.cancelVideoPlayRunnable();
                    if (z) {
                        PhotoViewer.this.videoPlayer.pause();
                    } else {
                        PhotoViewer.this.videoPlayer.play();
                    }
                }

                @Override // org.telegram.p023ui.Components.Paint.Views.LPhotoPaintView
                protected int getPKeyboardHeight() {
                    if (PhotoViewer.this.paintKeyboardNotifier != null) {
                        return PhotoViewer.this.paintKeyboardNotifier.getKeyboardHeight();
                    }
                    return 0;
                }

                @Override // org.telegram.p023ui.Components.Paint.Views.LPhotoPaintView
                protected void onEmojiViewCloseByClick() {
                    if (PhotoViewer.this.paintKeyboardNotifier != null) {
                        PhotoViewer.this.paintKeyboardNotifier.awaitKeyboard();
                    }
                }

                @Override // org.telegram.p023ui.Components.Paint.Views.LPhotoPaintView
                protected void updateKeyboard() {
                    if (PhotoViewer.this.paintKeyboardNotifier != null) {
                        PhotoViewer.this.paintKeyboardNotifier.fire();
                    }
                }

                @Override // org.telegram.p023ui.Components.Paint.Views.LPhotoPaintView
                protected void didSetAnimatedSticker(RLottieDrawable rLottieDrawable) {
                    if (PhotoViewer.this.videoPlayer == null) {
                        return;
                    }
                    rLottieDrawable.setProgressMs(PhotoViewer.this.videoPlayer.getCurrentPosition() - (PhotoViewer.this.startTime > 0 ? PhotoViewer.this.startTime / 1000 : 0L));
                }

                @Override // org.telegram.p023ui.Components.Paint.Views.LPhotoPaintView
                protected void onTextAdd() {
                    PhotoViewer.this.windowView.isFocusable();
                }
            };
            this.photoPaintView = lPhotoPaintView;
            this.containerView.addView(lPhotoPaintView.getView(), LayoutHelper.createFrame(-1, -1.0f));
            this.photoPaintView.setOnDoneButtonClickedListener(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda166
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createPaintView$117();
                }
            });
            this.photoPaintView.getCancelView().setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda167
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$createPaintView$118(view);
                }
            });
            this.photoPaintView.setOffsetTranslationY(AndroidUtilities.m1146dp(126.0f), 0.0f, 0, false);
            this.photoPaintView.setOffsetTranslationX(-AndroidUtilities.m1146dp(12.0f));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPaintView$115(Integer num) {
        this.photoPaintView.keyboardVisible = this.paintKeyboardNotifier.keyboardVisible();
        this.containerView.invalidate();
        int iMax = Math.max(num.intValue(), this.photoPaintView.getEmojiPadding(false));
        translateY((!this.photoPaintView.isCurrentText() || iMax <= 0) ? 0.0f : ((AndroidUtilities.displaySize.y - iMax) - AndroidUtilities.m1146dp(80.0f)) - this.photoPaintView.getSelectedEntityBottom());
        AnimatorSet animatorSet = this.paintKeyboardAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda169
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$createPaintView$114(valueAnimator);
            }
        });
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.paintKeyboardAnimator = animatorSet2;
        PaintWeightChooserView paintWeightChooserView = this.photoPaintView.weightChooserView;
        Property property = View.TRANSLATION_Y;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(paintWeightChooserView, (Property<PaintWeightChooserView, Float>) property, (-iMax) / 2.5f);
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.photoPaintView.bottomLayout, (Property<FrameLayout, Float>) property, Math.min(0, r9 + AndroidUtilities.m1146dp(40.0f)));
        LinearLayout linearLayout = this.photoPaintView.tabsLayout;
        Property property2 = View.ALPHA;
        animatorSet2.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, ObjectAnimator.ofFloat(linearLayout, (Property<LinearLayout, Float>) property2, iMax > AndroidUtilities.m1146dp(20.0f) ? 0.0f : 1.0f), ObjectAnimator.ofFloat(this.photoPaintView.cancelButton, (Property<PaintCancelView, Float>) property2, iMax > AndroidUtilities.m1146dp(20.0f) ? 0.0f : 1.0f), ObjectAnimator.ofFloat(this.photoPaintView.doneButton, (Property<PaintDoneView, Float>) property2, iMax <= AndroidUtilities.m1146dp(20.0f) ? 1.0f : 0.0f), valueAnimatorOfFloat);
        animatorSet2.setDuration(320L);
        animatorSet2.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        animatorSet2.start();
        this.photoPaintView.updatePlusEmojiKeyboardButton();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPaintView$114(ValueAnimator valueAnimator) {
        LPhotoPaintView lPhotoPaintView = this.photoPaintView;
        if (lPhotoPaintView != null) {
            lPhotoPaintView.overlayLayout.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPaintView$116() {
        this.paintingOverlay.hideBitmap();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPaintView$117() {
        this.savedState = null;
        applyCurrentEditMode();
        switchToEditMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createPaintView$118(View view) {
        closePaintMode();
    }

    private void translateY(float f) {
        ValueAnimator valueAnimator = this.translateYAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.translateYAnimator = null;
        }
        if (this.currentEditMode != 3) {
            f = 0.0f;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.translateY, f);
        this.translateYAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda175
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$translateY$119(valueAnimator2);
            }
        });
        this.translateYAnimator.setDuration(320L);
        this.translateYAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        this.translateYAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$translateY$119(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.translateY = fFloatValue;
        LPhotoPaintView lPhotoPaintView = this.photoPaintView;
        if (lPhotoPaintView != null) {
            lPhotoPaintView.translateY(fFloatValue);
        }
        this.containerView.invalidate();
    }

    private void closePaintMode() {
        this.photoPaintView.maybeShowDismissalAlert(this, this.parentActivity, new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda114
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$closePaintMode$120();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closePaintMode$120() {
        switchToEditMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchToPaintMode() {
        EditState editState;
        this.changeModeAnimation = null;
        this.pickerView.setVisibility(8);
        this.pickerViewSendButton.setVisibility(8);
        this.doneButtonFullWidth.setVisibility(8);
        this.muteButton.setVisibility(8);
        this.editCoverButton.setVisibility(8);
        PhotoCropView photoCropView = this.photoCropView;
        if (photoCropView != null) {
            photoCropView.setVisibility(4);
        }
        this.selectedPhotosListView.setVisibility(8);
        this.selectedPhotosListView.setAlpha(0.0f);
        this.selectedPhotosListView.setTranslationY(-AndroidUtilities.m1146dp(10.0f));
        this.photosCounterView.setRotationX(0.0f);
        this.selectedPhotosListView.setEnabled(false);
        this.isPhotosListViewVisible = false;
        if (this.needCaptionLayout) {
            this.captionTextViewSwitcher.setVisibility(4);
        }
        int i = this.sendPhotoType;
        if (i == 0 || i == 4 || ((i == 2 || i == 5) && this.imagesArrLocals.size() > 1)) {
            this.checkImageView.setVisibility(8);
            this.photosCounterView.setVisibility(8);
            updateActionBarTitlePadding();
        }
        showEditCaption(false, true);
        showStickerMode(false, false, true);
        Bitmap bitmap = this.centerImage.getBitmap();
        float f = this.scale;
        if (this.sendPhotoType == 11) {
            this.savedTy = this.translationY;
            this.savedTx = this.translationX;
            this.savedScale = f;
            this.savedRotation = this.rotate;
            this.animateToRotate = 0.0f;
        }
        if (bitmap != null) {
            int bitmapWidth = this.centerImage.getBitmapWidth();
            int bitmapHeight = this.centerImage.getBitmapHeight();
            if (this.sendPhotoType == 1) {
                float fM1146dp = AndroidUtilities.m1146dp(12.0f);
                this.animateToY = fM1146dp;
                if (this.photoPaintView != null) {
                    this.animateToY = fM1146dp + (r15.getAdditionalTop() / 2.0f);
                }
                if (this.cropTransform.getOrientation() == 90 || this.cropTransform.getOrientation() == 270) {
                    bitmapHeight = bitmapWidth;
                    bitmapWidth = bitmapHeight;
                }
            } else {
                float f2 = (-AndroidUtilities.m1146dp(44.0f)) + (isStatusBarVisible() ? AndroidUtilities.statusBarHeight / 2 : 0);
                this.animateToY = f2;
                if (this.photoPaintView != null) {
                    float additionalTop = f2 + (r15.getAdditionalTop() / 2.0f);
                    this.animateToY = additionalTop;
                    this.animateToY = additionalTop - (this.photoPaintView.getAdditionalBottom() / 2.0f);
                }
                MediaController.CropState cropState = this.editState.cropState;
                if (cropState != null) {
                    int i2 = cropState.transformRotation;
                    if (i2 == 90 || i2 == 270) {
                        bitmapHeight = bitmapWidth;
                        bitmapWidth = bitmapHeight;
                    }
                    bitmapWidth = (int) (bitmapWidth * cropState.cropPw);
                    bitmapHeight = (int) (bitmapHeight * cropState.cropPh);
                }
            }
            float f3 = bitmapWidth;
            float f4 = bitmapHeight;
            this.animateToScale = (Math.min(getContainerViewWidth(3) / f3, getContainerViewHeight(3) / f4) / Math.min(getContainerViewWidth() / f3, getContainerViewHeight() / f4)) * scale1(false);
            this.animateToX = (getLeftInset() / 2) - (getRightInset() / 2);
            this.animationStartTime = System.currentTimeMillis();
            this.zoomAnimation = true;
        }
        LPhotoPaintView lPhotoPaintView = this.photoPaintView;
        if (lPhotoPaintView != null) {
            lPhotoPaintView.setDrawShadow(this.sendPhotoType == 11 && ((editState = this.editState) == null || editState.cropState == null));
        }
        this.windowView.setClipChildren(true);
        this.navigationBar.setVisibility(4);
        this.imageMoveAnimation = new AnimatorSet();
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(AndroidUtilities.m1146dp(126.0f), 0.0f);
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(-AndroidUtilities.m1146dp(12.0f), 0.0f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda170
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$switchToPaintMode$121(valueAnimator);
            }
        });
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda171
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$switchToPaintMode$122(valueAnimator);
            }
        });
        this.imageMoveAnimation.playTogether(ObjectAnimator.ofFloat(this, (Property<PhotoViewer, Float>) AnimationProperties.PHOTO_VIEWER_ANIMATION_VALUE, 0.0f, 1.0f), valueAnimatorOfFloat, valueAnimatorOfFloat2);
        this.photoPaintView.onAnimationStateChanged(true);
        this.imageMoveAnimation.setDuration(200L);
        this.imageMoveAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.68
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PhotoViewer.this.photoPaintView.onAnimationStateChanged(false);
                PhotoViewer.this.photoPaintView.init();
                PhotoViewer.this.paintingOverlay.hideEntities();
                PhotoViewer.this.imageMoveAnimation = null;
                PhotoViewer.this.currentEditMode = 3;
                PhotoViewer.this.getCaptionView().keyboardNotifier.ignore(PhotoViewer.this.currentEditMode != 0);
                if (PhotoViewer.this.paintKeyboardNotifier != null) {
                    PhotoViewer.this.paintKeyboardNotifier.ignore(PhotoViewer.this.currentEditMode != 3);
                }
                PhotoViewer.this.switchingToMode = -1;
                PhotoViewer photoViewer = PhotoViewer.this;
                float fScale1 = photoViewer.scale1(false);
                photoViewer.scale = fScale1;
                photoViewer.animateToScale = fScale1;
                PhotoViewer.this.animateToX = 0.0f;
                PhotoViewer.this.animateToY = 0.0f;
                PhotoViewer photoViewer2 = PhotoViewer.this;
                photoViewer2.updateMinMax(photoViewer2.scale);
                PhotoViewer.this.padImageForHorizontalInsets = true;
                PhotoViewer.this.containerView.invalidate();
                if (PhotoViewer.this.placeProvider == null || !PhotoViewer.this.placeProvider.closeKeyboard()) {
                    PhotoViewer.this.makeFocusable();
                }
            }
        });
        this.imageMoveAnimation.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToPaintMode$121(ValueAnimator valueAnimator) {
        this.photoPaintView.setOffsetTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f, 0, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$switchToPaintMode$122(ValueAnimator valueAnimator) {
        this.photoPaintView.setOffsetTranslationX(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleOnlyCheckImageView(boolean z) {
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        float fDpf2 = AndroidUtilities.dpf2(24.0f);
        int i = this.sendPhotoType;
        if (i == 0 || i == 4) {
            CheckBox checkBox = this.checkImageView;
            Property property = View.ALPHA;
            arrayList.add(ObjectAnimator.ofFloat(checkBox, (Property<CheckBox, Float>) property, z ? 1.0f : 0.0f));
            CheckBox checkBox2 = this.checkImageView;
            Property property2 = View.TRANSLATION_Y;
            arrayList.add(ObjectAnimator.ofFloat(checkBox2, (Property<CheckBox, Float>) property2, z ? 0.0f : -fDpf2));
            arrayList.add(ObjectAnimator.ofFloat(this.photosCounterView, (Property<CounterView, Float>) property, z ? 1.0f : 0.0f));
            arrayList.add(ObjectAnimator.ofFloat(this.photosCounterView, (Property<CounterView, Float>) property2, z ? 0.0f : -fDpf2));
        }
        animatorSet.playTogether(arrayList);
        animatorSet.setDuration(200L);
        animatorSet.start();
    }

    private void toggleCheckImageView(boolean z) {
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        float fDpf2 = AndroidUtilities.dpf2(24.0f);
        FrameLayout frameLayout = this.pickerView;
        Property property = View.ALPHA;
        arrayList.add(ObjectAnimator.ofFloat(frameLayout, (Property<FrameLayout, Float>) property, z ? 1.0f : 0.0f));
        FrameLayout frameLayout2 = this.pickerView;
        Property property2 = View.TRANSLATION_Y;
        arrayList.add(ObjectAnimator.ofFloat(frameLayout2, (Property<FrameLayout, Float>) property2, z ? 0.0f : fDpf2));
        StickerMakerView stickerMakerView = this.stickerMakerView;
        if (stickerMakerView != null) {
            arrayList.add(ObjectAnimator.ofFloat(stickerMakerView, (Property<StickerMakerView, Float>) property, z ? 1.0f : 0.0f));
        }
        if (this.stickerMakerView != null) {
            arrayList.add(ObjectAnimator.ofFloat(this.stickerMakerBackgroundView, (Property<StickerMakerBackgroundView, Float>) property, z ? 1.0f : 0.0f));
        }
        arrayList.add(ObjectAnimator.ofFloat(this.pickerViewSendButton, (Property<ChatActivityEnterView.SendButton, Float>) property, z ? 1.0f : 0.0f));
        arrayList.add(ObjectAnimator.ofFloat(this.pickerViewSendButton, (Property<ChatActivityEnterView.SendButton, Float>) property2, z ? 0.0f : fDpf2));
        int i = this.sendPhotoType;
        if (i == 0 || i == 4) {
            arrayList.add(ObjectAnimator.ofFloat(this.checkImageView, (Property<CheckBox, Float>) property, z ? 1.0f : 0.0f));
            arrayList.add(ObjectAnimator.ofFloat(this.checkImageView, (Property<CheckBox, Float>) property2, z ? 0.0f : -fDpf2));
            arrayList.add(ObjectAnimator.ofFloat(this.photosCounterView, (Property<CounterView, Float>) property, z ? 1.0f : 0.0f));
            arrayList.add(ObjectAnimator.ofFloat(this.photosCounterView, (Property<CounterView, Float>) property2, z ? 0.0f : -fDpf2));
        }
        animatorSet.playTogether(arrayList);
        animatorSet.setDuration(200L);
        animatorSet.start();
        if (z || !isCaptionOpen()) {
            return;
        }
        closeCaptionEnter(true);
        if (this.captionEdit.editText.isPopupShowing()) {
            this.captionEdit.editText.hidePopup(true);
        }
        if (this.topCaptionEdit.editText.isPopupShowing()) {
            this.topCaptionEdit.editText.hidePopup(true);
        }
        this.captionEdit.editText.closeKeyboard();
        this.topCaptionEdit.editText.closeKeyboard();
    }

    private void toggleMiniProgressInternal(final boolean z) {
        if (z) {
            this.miniProgressView.setVisibility(0);
        }
        AnimatorSet animatorSet = new AnimatorSet();
        this.miniProgressAnimator = animatorSet;
        animatorSet.playTogether(ObjectAnimator.ofFloat(this.miniProgressView, (Property<RadialProgressView, Float>) View.ALPHA, z ? 1.0f : 0.0f));
        this.miniProgressAnimator.setDuration(200L);
        this.miniProgressAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.69
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (animator.equals(PhotoViewer.this.miniProgressAnimator)) {
                    if (!z) {
                        PhotoViewer.this.miniProgressView.setVisibility(4);
                    }
                    PhotoViewer.this.miniProgressAnimator = null;
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                if (animator.equals(PhotoViewer.this.miniProgressAnimator)) {
                    PhotoViewer.this.miniProgressAnimator = null;
                }
            }
        });
        this.miniProgressAnimator.start();
    }

    private void toggleMiniProgress(boolean z, boolean z2) {
        AndroidUtilities.cancelRunOnUIThread(this.miniProgressShowRunnable);
        if (z2) {
            toggleMiniProgressInternal(z);
            if (z) {
                AnimatorSet animatorSet = this.miniProgressAnimator;
                if (animatorSet != null) {
                    animatorSet.cancel();
                    this.miniProgressAnimator = null;
                }
                if (this.firstAnimationDelay) {
                    this.firstAnimationDelay = false;
                    toggleMiniProgressInternal(true);
                    return;
                } else {
                    AndroidUtilities.runOnUIThread(this.miniProgressShowRunnable, 500L);
                    return;
                }
            }
            AnimatorSet animatorSet2 = this.miniProgressAnimator;
            if (animatorSet2 != null) {
                animatorSet2.cancel();
                toggleMiniProgressInternal(false);
                return;
            }
            return;
        }
        AnimatorSet animatorSet3 = this.miniProgressAnimator;
        if (animatorSet3 != null) {
            animatorSet3.cancel();
            this.miniProgressAnimator = null;
        }
        this.miniProgressView.setAlpha(z ? 1.0f : 0.0f);
        this.miniProgressView.setVisibility(z ? 0 : 4);
    }

    private void updateContainerFlags(boolean z) {
        FrameLayoutDrawer frameLayoutDrawer;
        int i;
        if (this.sendPhotoType == 1 || (frameLayoutDrawer = this.containerView) == null) {
            return;
        }
        if (z) {
            i = 1792;
        } else {
            i = (frameLayoutDrawer.getPaddingLeft() > 0 || this.containerView.getPaddingRight() > 0) ? 5894 : 1796;
        }
        this.containerView.setSystemUiVisibility(i);
    }

    /* loaded from: classes5.dex */
    private static class ActionBarToggleParams {
        public static final ActionBarToggleParams DEFAULT = new ActionBarToggleParams();
        public Interpolator animationInterpolator;
        public int animationDuration = DataTypes.EMPTY;
        public boolean enableStatusBarAnimation = true;
        public boolean enableTranslationAnimation = true;

        public ActionBarToggleParams enableStatusBarAnimation(boolean z) {
            this.enableStatusBarAnimation = z;
            return this;
        }

        public ActionBarToggleParams enableTranslationAnimation(boolean z) {
            this.enableTranslationAnimation = z;
            return this;
        }

        public ActionBarToggleParams animationDuration(int i) {
            this.animationDuration = i;
            return this;
        }

        public ActionBarToggleParams animationInterpolator(Interpolator interpolator) {
            this.animationInterpolator = interpolator;
            return this;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleActionBar(boolean z, boolean z2) {
        toggleActionBar(z, z2, ActionBarToggleParams.DEFAULT);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:186:0x02e0  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00a9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void toggleActionBar(final boolean r12, boolean r13, org.telegram.ui.PhotoViewer.ActionBarToggleParams r14) {
        /*
            Method dump skipped, instructions count: 960
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.toggleActionBar(boolean, boolean, org.telegram.ui.PhotoViewer$ActionBarToggleParams):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toggleActionBar$124(ValueAnimator valueAnimator) {
        this.photoProgressViews[0].setIndexedAlpha(1, ((Float) valueAnimator.getAnimatedValue()).floatValue(), false);
    }

    private void togglePhotosListView(boolean z, boolean z2) {
        if (z == this.isPhotosListViewVisible) {
            return;
        }
        if (z) {
            this.selectedPhotosListView.setVisibility(0);
        }
        this.isPhotosListViewVisible = z;
        this.selectedPhotosListView.setEnabled(z);
        if (z2) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofFloat(this.selectedPhotosListView, (Property<SelectedPhotosListView, Float>) View.ALPHA, z ? 1.0f : 0.0f));
            arrayList.add(ObjectAnimator.ofFloat(this.selectedPhotosListView, (Property<SelectedPhotosListView, Float>) View.TRANSLATION_Y, z ? 0.0f : -AndroidUtilities.m1146dp(10.0f)));
            arrayList.add(ObjectAnimator.ofFloat(this.photosCounterView, (Property<CounterView, Float>) View.ROTATION_X, z ? 1.0f : 0.0f));
            AnimatorSet animatorSet = new AnimatorSet();
            this.currentListViewAnimation = animatorSet;
            animatorSet.playTogether(arrayList);
            if (!z) {
                this.currentListViewAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.71
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (PhotoViewer.this.currentListViewAnimation == null || !PhotoViewer.this.currentListViewAnimation.equals(animator)) {
                            return;
                        }
                        PhotoViewer.this.selectedPhotosListView.setVisibility(8);
                        PhotoViewer.this.currentListViewAnimation = null;
                    }
                });
            }
            this.currentListViewAnimation.setDuration(200L);
            this.currentListViewAnimation.start();
            return;
        }
        this.selectedPhotosListView.setAlpha(z ? 1.0f : 0.0f);
        this.selectedPhotosListView.setTranslationY(z ? 0.0f : -AndroidUtilities.m1146dp(10.0f));
        this.photosCounterView.setRotationX(z ? 1.0f : 0.0f);
        if (z) {
            return;
        }
        this.selectedPhotosListView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleVideoPlayer() {
        VideoPlayer videoPlayer;
        VideoPlayer videoPlayer2;
        PhotoViewerWebView photoViewerWebView;
        if (this.videoPlayer != null || ((photoViewerWebView = this.photoViewerWebView) != null && photoViewerWebView.isControllable())) {
            boolean zIsPlaying = this.videoPlayer != null ? this.isPlaying : this.photoViewerWebView.isPlaying();
            cancelVideoPlayRunnable();
            AndroidUtilities.cancelRunOnUIThread(this.hideActionBarRunnable);
            if (zIsPlaying) {
                pauseVideoOrWeb();
            } else {
                if (!this.isCurrentVideo) {
                    if (Math.abs(this.videoPlayerSeekbar.getProgress() - this.videoTimelineView.getRightProgress()) < 0.01f || ((videoPlayer = this.videoPlayer) != null && videoPlayer.getCurrentPosition() == this.videoPlayer.getDuration())) {
                        seekVideoOrWebToProgress(0.0f);
                    }
                    scheduleActionBarHide();
                } else if (Math.abs(this.videoTimelineView.getProgress() - this.videoTimelineView.getRightProgress()) < 0.01f || ((videoPlayer2 = this.videoPlayer) != null && videoPlayer2.getCurrentPosition() == this.videoPlayer.getDuration())) {
                    seekVideoOrWebToProgress(this.videoTimelineView.getLeftProgress());
                }
                playVideoOrWeb();
            }
            requestAudioFocus(!zIsPlaying);
            this.containerView.invalidate();
        }
    }

    private String getFileName(int i) {
        if (i < 0) {
            return null;
        }
        if (!this.secureDocuments.isEmpty()) {
            if (i >= this.secureDocuments.size()) {
                return null;
            }
            SecureDocument secureDocument = (SecureDocument) this.secureDocuments.get(i);
            return secureDocument.secureFile.dc_id + "_" + secureDocument.secureFile.f1714id + ".jpg";
        }
        if (!this.imagesArrLocations.isEmpty() || !this.imagesArr.isEmpty()) {
            if (!this.imagesArrLocations.isEmpty()) {
                if (i >= this.imagesArrLocations.size()) {
                    return null;
                }
                ImageLocation imageLocation = (ImageLocation) this.imagesArrLocations.get(i);
                ImageLocation imageLocation2 = (ImageLocation) this.imagesArrLocationsVideo.get(i);
                if (imageLocation == null) {
                    return null;
                }
                if (imageLocation2 != null && imageLocation2 != imageLocation) {
                    return imageLocation2.location.volume_id + "_" + imageLocation2.location.local_id + ".mp4";
                }
                return imageLocation.location.volume_id + "_" + imageLocation.location.local_id + ".jpg";
            }
            if (i >= this.imagesArr.size()) {
                return null;
            }
            return FileLoader.getMessageFileName(((MessageObject) this.imagesArr.get(i)).messageOwner);
        }
        if (!this.imagesArrLocals.isEmpty()) {
            if (i >= this.imagesArrLocals.size()) {
                return null;
            }
            Object obj = this.imagesArrLocals.get(i);
            if (obj instanceof MediaController.SearchImage) {
                return ((MediaController.SearchImage) obj).getAttachName();
            }
            if (obj instanceof TLRPC.BotInlineResult) {
                TLRPC.BotInlineResult botInlineResult = (TLRPC.BotInlineResult) obj;
                TLRPC.Document document = botInlineResult.document;
                if (document != null) {
                    return FileLoader.getAttachFileName(document);
                }
                TLRPC.Photo photo = botInlineResult.photo;
                if (photo != null) {
                    return FileLoader.getAttachFileName(FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.getPhotoSize()));
                }
                if (botInlineResult.content instanceof TLRPC.TL_webDocument) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(Utilities.MD5(botInlineResult.content.url));
                    sb.append(".");
                    TLRPC.WebDocument webDocument = botInlineResult.content;
                    sb.append(ImageLoader.getHttpUrlExtension(webDocument.url, FileLoader.getMimeTypePart(webDocument.mime_type)));
                    return sb.toString();
                }
            }
        } else {
            PageBlocksAdapter pageBlocksAdapter = this.pageBlocksAdapter;
            if (pageBlocksAdapter != null) {
                return pageBlocksAdapter.getFileName(i);
            }
        }
        return null;
    }

    private ImageLocation getImageLocation(int i, long[] jArr) {
        if (i < 0) {
            return null;
        }
        if (!this.secureDocuments.isEmpty()) {
            if (i >= this.secureDocuments.size()) {
                return null;
            }
            if (jArr != null) {
                jArr[0] = ((SecureDocument) this.secureDocuments.get(i)).secureFile.size;
            }
            return ImageLocation.getForSecureDocument((SecureDocument) this.secureDocuments.get(i));
        }
        if (!this.imagesArrLocations.isEmpty()) {
            if (i >= this.imagesArrLocations.size()) {
                return null;
            }
            if (jArr != null && this.imagesArrLocationsSizes.get(i) != null) {
                jArr[0] = ((Long) this.imagesArrLocationsSizes.get(i)).longValue();
            }
            return (ImageLocation) this.imagesArrLocationsVideo.get(i);
        }
        if (this.imagesArr.isEmpty() || i >= this.imagesArr.size()) {
            return null;
        }
        MessageObject messageObject = (MessageObject) this.imagesArr.get(i);
        TLRPC.Message message = messageObject.messageOwner;
        if (message instanceof TLRPC.TL_messageService) {
            if (message.action instanceof TLRPC.TL_messageActionUserUpdatedPhoto) {
                return null;
            }
            TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize());
            if (closestPhotoSizeWithSize != null) {
                if (jArr != null) {
                    long j = closestPhotoSizeWithSize.size;
                    jArr[0] = j;
                    if (j == 0) {
                        jArr[0] = -1;
                    }
                }
                return ImageLocation.getForObject(closestPhotoSizeWithSize, messageObject.photoThumbsObject);
            }
            if (jArr != null) {
                jArr[0] = -1;
            }
        } else {
            TLRPC.MessageMedia messageMedia = messageObject.sponsoredMedia;
            if (messageMedia != null) {
                TLRPC.Document document = messageMedia.document;
                if (document != null) {
                    return ImageLocation.getForDocument(document);
                }
                TLRPC.Photo photo = messageMedia.photo;
                if (photo != null) {
                    TLRPC.PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.getPhotoSize(), false, null, true);
                    if (closestPhotoSizeWithSize2 != null) {
                        if (jArr != null) {
                            long j2 = closestPhotoSizeWithSize2.size;
                            jArr[0] = j2;
                            if (j2 == 0) {
                                jArr[0] = -1;
                            }
                        }
                        return ImageLocation.getForObject(closestPhotoSizeWithSize2, messageObject.sponsoredMedia.photo);
                    }
                    if (jArr != null) {
                        jArr[0] = -1;
                    }
                }
            } else if (((MessageObject.getMedia(message) instanceof TLRPC.TL_messageMediaPhoto) && MessageObject.getMedia(messageObject.messageOwner).photo != null) || ((MessageObject.getMedia(messageObject.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && MessageObject.getMedia(messageObject.messageOwner).webpage != null)) {
                if (messageObject.isGif()) {
                    return ImageLocation.getForDocument(messageObject.getDocument());
                }
                TLRPC.PhotoSize closestPhotoSizeWithSize3 = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize(true), false, null, true);
                if (closestPhotoSizeWithSize3 != null) {
                    if (jArr != null) {
                        long j3 = closestPhotoSizeWithSize3.size;
                        jArr[0] = j3;
                        if (j3 == 0) {
                            jArr[0] = -1;
                        }
                    }
                    return ImageLocation.getForObject(closestPhotoSizeWithSize3, messageObject.photoThumbsObject);
                }
                if (jArr != null) {
                    jArr[0] = -1;
                }
            } else {
                if (MessageObject.getMedia(messageObject.messageOwner) instanceof TLRPC.TL_messageMediaInvoice) {
                    return ImageLocation.getForWebFile(WebFile.createWithWebDocument(((TLRPC.TL_messageMediaInvoice) MessageObject.getMedia(messageObject.messageOwner)).webPhoto));
                }
                if (messageObject.getDocument() != null) {
                    TLRPC.Document document2 = messageObject.getDocument();
                    if (this.sharedMediaType == 5 || MessageObject.isStickerDocument(document2)) {
                        return ImageLocation.getForDocument(document2);
                    }
                    if (MessageObject.isDocumentHasThumb(messageObject.getDocument())) {
                        TLRPC.PhotoSize closestPhotoSizeWithSize4 = FileLoader.getClosestPhotoSizeWithSize(document2.thumbs, 90);
                        if (jArr != null) {
                            long j4 = closestPhotoSizeWithSize4.size;
                            jArr[0] = j4;
                            if (j4 == 0) {
                                jArr[0] = -1;
                            }
                        }
                        return ImageLocation.getForDocument(closestPhotoSizeWithSize4, document2);
                    }
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TLObject getFileLocation(int i, long[] jArr) {
        if (i < 0) {
            return null;
        }
        if (!this.secureDocuments.isEmpty()) {
            if (i >= this.secureDocuments.size()) {
                return null;
            }
            if (jArr != null) {
                jArr[0] = ((SecureDocument) this.secureDocuments.get(i)).secureFile.size;
            }
            return (TLObject) this.secureDocuments.get(i);
        }
        if (!this.imagesArrLocations.isEmpty()) {
            if (i >= this.imagesArrLocations.size()) {
                return null;
            }
            if (jArr != null && this.imagesArrLocationsSizes.get(i) != null) {
                jArr[0] = ((Long) this.imagesArrLocationsSizes.get(i)).longValue();
            }
            if (this.imagesArrLocationsVideo.get(i) != null) {
                return ((ImageLocation) this.imagesArrLocationsVideo.get(i)).location;
            }
            return null;
        }
        if (this.imagesArr.isEmpty() || i >= this.imagesArr.size()) {
            return null;
        }
        MessageObject messageObject = (MessageObject) this.imagesArr.get(i);
        TLRPC.Message message = messageObject.messageOwner;
        if (message instanceof TLRPC.TL_messageService) {
            TLRPC.MessageAction messageAction = message.action;
            if (messageAction instanceof TLRPC.TL_messageActionUserUpdatedPhoto) {
                return messageAction.newUserPhoto.photo_big;
            }
            TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize());
            if (closestPhotoSizeWithSize != null) {
                if (jArr != null) {
                    long j = closestPhotoSizeWithSize.size;
                    jArr[0] = j;
                    if (j == 0) {
                        jArr[0] = -1;
                    }
                }
                return closestPhotoSizeWithSize;
            }
            if (jArr != null) {
                jArr[0] = -1;
            }
        } else {
            TLRPC.MessageMedia messageMedia = messageObject.sponsoredMedia;
            if (messageMedia != null) {
                TLRPC.Document document = messageMedia.document;
                if (document != null) {
                    TLRPC.PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 90);
                    if (jArr != null) {
                        long j2 = closestPhotoSizeWithSize2.size;
                        jArr[0] = j2;
                        if (j2 == 0) {
                            jArr[0] = -1;
                        }
                    }
                    return closestPhotoSizeWithSize2;
                }
                TLRPC.Photo photo = messageMedia.photo;
                if (photo != null) {
                    TLRPC.PhotoSize closestPhotoSizeWithSize3 = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.getPhotoSize(), false, null, true);
                    if (closestPhotoSizeWithSize3 != null) {
                        if (jArr != null) {
                            long j3 = closestPhotoSizeWithSize3.size;
                            jArr[0] = j3;
                            if (j3 == 0) {
                                jArr[0] = -1;
                            }
                        }
                        return closestPhotoSizeWithSize3;
                    }
                    if (jArr != null) {
                        jArr[0] = -1;
                    }
                }
            } else if (((MessageObject.getMedia(message) instanceof TLRPC.TL_messageMediaPhoto) && MessageObject.getMedia(messageObject.messageOwner).photo != null) || ((MessageObject.getMedia(messageObject.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && MessageObject.getMedia(messageObject.messageOwner).webpage != null)) {
                TLRPC.PhotoSize closestPhotoSizeWithSize4 = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize(true), false, null, true);
                if (closestPhotoSizeWithSize4 != null) {
                    if (jArr != null) {
                        long j4 = closestPhotoSizeWithSize4.size;
                        jArr[0] = j4;
                        if (j4 == 0) {
                            jArr[0] = -1;
                        }
                    }
                    return closestPhotoSizeWithSize4;
                }
                if (jArr != null) {
                    jArr[0] = -1;
                }
            } else {
                if (MessageObject.getMedia(messageObject.messageOwner) instanceof TLRPC.TL_messageMediaInvoice) {
                    return ((TLRPC.TL_messageMediaInvoice) MessageObject.getMedia(messageObject.messageOwner)).photo;
                }
                if (messageObject.getDocument() != null && MessageObject.isDocumentHasThumb(messageObject.getDocument())) {
                    TLRPC.PhotoSize closestPhotoSizeWithSize5 = FileLoader.getClosestPhotoSizeWithSize(messageObject.getDocument().thumbs, 90);
                    if (jArr != null) {
                        long j5 = closestPhotoSizeWithSize5.size;
                        jArr[0] = j5;
                        if (j5 == 0) {
                            jArr[0] = -1;
                        }
                    }
                    return closestPhotoSizeWithSize5;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectedCount() {
        MessageObject messageObject;
        PhotoViewerProvider photoViewerProvider = this.placeProvider;
        if (photoViewerProvider == null) {
            return;
        }
        int selectedCount = photoViewerProvider.getSelectedCount();
        this.photosCounterView.setCount(selectedCount);
        if (selectedCount == 0) {
            togglePhotosListView(false, true);
        }
        if (this.pickerViewSendButton != null) {
            PhotoViewerProvider photoViewerProvider2 = this.placeProvider;
            long dialogId = photoViewerProvider2 != null ? photoViewerProvider2.getDialogId() : 0L;
            if (dialogId == 0 && (messageObject = this.currentMessageObject) != null) {
                dialogId = messageObject.getDialogId();
            }
            ChatActivityEnterView.SendButton sendButton = this.pickerViewSendButton;
            PhotoViewerProvider photoViewerProvider3 = this.placeProvider;
            long sendPaidMessagesStars = (photoViewerProvider3 == null || !photoViewerProvider3.isEditingMessage() || this.placeProvider.isEditingMessageResend()) ? MessagesController.getInstance(this.currentAccount).getSendPaidMessagesStars(dialogId) : 0L;
            PhotoViewerProvider photoViewerProvider4 = this.placeProvider;
            sendButton.setStarsPrice(sendPaidMessagesStars, Math.max(1, photoViewerProvider4 == null ? 1 : photoViewerProvider4.getSelectedCount()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCurrentAvatarSet() {
        int i;
        if (this.currentAvatarLocation != null && (i = this.currentIndex) >= 0 && i < this.avatarsArr.size()) {
            TLRPC.Photo photo = (TLRPC.Photo) this.avatarsArr.get(this.currentIndex);
            ImageLocation imageLocation = (ImageLocation) this.imagesArrLocations.get(this.currentIndex);
            if (photo instanceof TLRPC.TL_photoEmpty) {
                photo = null;
            }
            if (photo != null) {
                int size = photo.sizes.size();
                for (int i2 = 0; i2 < size; i2++) {
                    TLRPC.FileLocation fileLocation = ((TLRPC.PhotoSize) photo.sizes.get(i2)).location;
                    if (fileLocation != null) {
                        int i3 = fileLocation.local_id;
                        TLRPC.TL_fileLocationToBeDeprecated tL_fileLocationToBeDeprecated = this.currentAvatarLocation.location;
                        if (i3 == tL_fileLocationToBeDeprecated.local_id && fileLocation.volume_id == tL_fileLocationToBeDeprecated.volume_id) {
                            return true;
                        }
                    }
                }
            } else if (imageLocation != null) {
                TLRPC.TL_fileLocationToBeDeprecated tL_fileLocationToBeDeprecated2 = imageLocation.location;
                int i4 = tL_fileLocationToBeDeprecated2.local_id;
                TLRPC.TL_fileLocationToBeDeprecated tL_fileLocationToBeDeprecated3 = this.currentAvatarLocation.location;
                if (i4 == tL_fileLocationToBeDeprecated3.local_id && tL_fileLocationToBeDeprecated2.volume_id == tL_fileLocationToBeDeprecated3.volume_id) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setItemVisible(View view, boolean z, boolean z2) {
        setItemVisible(view, z, z2, 1.0f);
    }

    private void setItemVisible(final View view, final boolean z, boolean z2, float f) {
        if ((view == this.masksItem || view == this.editItem || view == this.sendItem) && ExteraConfig.centerTitle && z) {
            return;
        }
        Boolean bool = (Boolean) this.actionBarItemsVisibility.get(view);
        if (bool == null || bool.booleanValue() != z) {
            this.actionBarItemsVisibility.put(view, Boolean.valueOf(z));
            view.animate().cancel();
            float f2 = (z ? 1.0f : 0.0f) * f;
            if (z2 && bool != null) {
                if (z) {
                    view.setVisibility(0);
                }
                view.animate().alpha(f2).setDuration(100L).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda130
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$setItemVisible$125(valueAnimator);
                    }
                }).setInterpolator(new LinearInterpolator()).withEndAction(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda131
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$setItemVisible$126(z, view);
                    }
                }).start();
            } else {
                view.setVisibility(z ? 0 : 8);
                view.setAlpha(f2);
                updateActionBarTitlePadding();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setItemVisible$125(ValueAnimator valueAnimator) {
        updateActionBarTitlePadding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setItemVisible$126(boolean z, View view) {
        if (!z) {
            view.setVisibility(8);
        }
        updateActionBarTitlePadding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0566  */
    /* JADX WARN: Removed duplicated region for block: B:566:0x0bfc  */
    /* JADX WARN: Removed duplicated region for block: B:576:0x0c1c  */
    /* JADX WARN: Removed duplicated region for block: B:577:0x0c21  */
    /* JADX WARN: Removed duplicated region for block: B:581:0x0c33 A[Catch: Exception -> 0x0c3f, TryCatch #0 {Exception -> 0x0c3f, blocks: (B:579:0x0c26, B:581:0x0c33, B:584:0x0c41, B:586:0x0c45), top: B:590:0x0c26 }] */
    /* JADX WARN: Removed duplicated region for block: B:586:0x0c45 A[Catch: Exception -> 0x0c3f, TRY_LEAVE, TryCatch #0 {Exception -> 0x0c3f, blocks: (B:579:0x0c26, B:581:0x0c33, B:584:0x0c41, B:586:0x0c45), top: B:590:0x0c26 }] */
    /* JADX WARN: Removed duplicated region for block: B:611:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onPhotoShow(org.telegram.messenger.MessageObject r22, org.telegram.tgnet.TLRPC.FileLocation r23, org.telegram.messenger.ImageLocation r24, org.telegram.messenger.ImageLocation r25, java.util.ArrayList r26, java.util.ArrayList r27, java.util.List r28, int r29, org.telegram.ui.PhotoViewer.PlaceProviderObject r30) throws android.content.res.Resources.NotFoundException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 3153
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.onPhotoShow(org.telegram.messenger.MessageObject, org.telegram.tgnet.TLRPC$FileLocation, org.telegram.messenger.ImageLocation, org.telegram.messenger.ImageLocation, java.util.ArrayList, java.util.ArrayList, java.util.List, int, org.telegram.ui.PhotoViewer$PlaceProviderObject):void");
    }

    public static /* synthetic */ int $r8$lambda$sRUpyuJM6TldQeG4qIhCg8fXAJ8(MessageObject messageObject, MessageObject messageObject2) {
        return messageObject.getId() - messageObject2.getId();
    }

    private boolean canSendMediaToParentChatActivity() {
        ChatActivity chatActivity = this.parentChatActivity;
        if (chatActivity == null) {
            return false;
        }
        if (chatActivity.currentUser != null) {
            return true;
        }
        TLRPC.Chat chat = chatActivity.currentChat;
        if (chat == null || ChatObject.isNotInChat(chat)) {
            return false;
        }
        return ChatObject.canSendPhoto(this.parentChatActivity.currentChat) || ChatObject.canSendVideo(this.parentChatActivity.currentChat);
    }

    private void setDoubleTapEnabled(boolean z) {
        this.doubleTapEnabled = z;
        this.gestureDetector.setOnDoubleTapListener(z ? this : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImages() throws Resources.NotFoundException {
        if (this.animationInProgress == 0) {
            setIndexToImage(this.centerImage, this.currentIndex, null);
            setIndexToPaintingOverlay(this.currentIndex, this.paintingOverlay);
            setIndexToImage(this.rightImage, this.currentIndex + 1, this.rightCropTransform);
            setIndexToPaintingOverlay(this.currentIndex + 1, this.rightPaintingOverlay);
            setIndexToImage(this.leftImage, this.currentIndex - 1, this.leftCropTransform);
            setIndexToPaintingOverlay(this.currentIndex - 1, this.leftPaintingOverlay);
        }
    }

    private void setIsAboutToSwitchToIndex(int i, boolean z, boolean z2) {
        setIsAboutToSwitchToIndex(i, z, z2, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:150:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0306  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0451  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x04aa  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x04b3  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x0570  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x079c  */
    /* JADX WARN: Removed duplicated region for block: B:393:0x07a0 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:398:0x07bf  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x07ca  */
    /* JADX WARN: Removed duplicated region for block: B:447:0x0940  */
    /* JADX WARN: Removed duplicated region for block: B:619:0x0d44  */
    /* JADX WARN: Removed duplicated region for block: B:646:0x0db6  */
    /* JADX WARN: Removed duplicated region for block: B:648:0x0ddc  */
    /* JADX WARN: Removed duplicated region for block: B:719:0x0f34  */
    /* JADX WARN: Removed duplicated region for block: B:720:0x0f36  */
    /* JADX WARN: Removed duplicated region for block: B:723:0x0f3e  */
    /* JADX WARN: Removed duplicated region for block: B:724:0x0f40  */
    /* JADX WARN: Removed duplicated region for block: B:727:0x0f48  */
    /* JADX WARN: Removed duplicated region for block: B:728:0x0f4a  */
    /* JADX WARN: Removed duplicated region for block: B:731:0x0f52  */
    /* JADX WARN: Removed duplicated region for block: B:743:0x0f6b  */
    /* JADX WARN: Removed duplicated region for block: B:755:0x0f82  */
    /* JADX WARN: Removed duplicated region for block: B:765:0x0f96  */
    /* JADX WARN: Type inference failed for: r3v165 */
    /* JADX WARN: Type inference failed for: r3v166, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v187 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setIsAboutToSwitchToIndex(int r45, boolean r46, boolean r47, boolean r48) {
        /*
            Method dump skipped, instructions count: 4502
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.setIsAboutToSwitchToIndex(int, boolean, boolean, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setIsAboutToSwitchToIndex$128(int i, TranslateController translateController, MessageObject messageObject, String str) {
        if (i != this.switchingToIndex) {
            return;
        }
        this.captionDetectedLanguage = str;
        if (translateController.isContextTranslateEnabled() && translateController.canTranslatePhoto(messageObject, this.captionDetectedLanguage)) {
            if (this.captionTranslated) {
                this.menuItem.showSubItem(20);
                this.menuItem.hideSubItem(19);
                return;
            } else {
                this.menuItem.showSubItem(19);
                this.menuItem.hideSubItem(20);
                return;
            }
        }
        this.menuItem.hideSubItem(19);
        this.menuItem.hideSubItem(20);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCaptionTranslated() {
        int i;
        MessageObject messageObject;
        TLRPC.Message message;
        if (this.imagesArr.isEmpty() || (i = this.switchingToIndex) < 0 || i >= this.imagesArr.size() || (messageObject = (MessageObject) this.imagesArr.get(this.switchingToIndex)) == null) {
            return;
        }
        if (this.captionTranslated && (message = messageObject.messageOwner) != null && message.translatedText != null && TextUtils.equals(message.translatedToLanguage, TranslateAlert2.getToLanguage())) {
            setCurrentCaption(messageObject, postProcessTranslated(messageObject), false, true);
        } else {
            setCurrentCaption(messageObject, messageObject.caption, this.captionTranslated, true);
        }
    }

    private CharSequence postProcessTranslated(MessageObject messageObject) {
        if (messageObject == null || messageObject.messageOwner == null) {
            return "";
        }
        Spannable spannableReplaceAnimatedEmoji = MessageObject.replaceAnimatedEmoji(Emoji.replaceEmoji(new SpannableStringBuilder(messageObject.messageOwner.translatedText.text), Theme.chat_msgTextPaint.getFontMetricsInt(), false), messageObject.messageOwner.translatedText.entities, Theme.chat_msgTextPaint.getFontMetricsInt(), false);
        if (MessageObject.containsUrls(spannableReplaceAnimatedEmoji)) {
            try {
                AndroidUtilities.addLinksSafe(spannableReplaceAnimatedEmoji, 5, false, true);
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
        MessageObject.addUrlsByPattern(messageObject.isOutOwner(), spannableReplaceAnimatedEmoji, true, 0, 0, true);
        MessageObject.addEntitiesToText(spannableReplaceAnimatedEmoji, messageObject.messageOwner.translatedText.entities, messageObject.isOutOwner(), true, true, true);
        if (messageObject.isVideo()) {
            MessageObject.addUrlsByPattern(messageObject.isOutOwner(), spannableReplaceAnimatedEmoji, true, 3, (int) messageObject.getDuration(), false);
        } else if (messageObject.isMusic() || messageObject.isVoice()) {
            MessageObject.addUrlsByPattern(messageObject.isOutOwner(), spannableReplaceAnimatedEmoji, true, 4, (int) messageObject.getDuration(), false);
        }
        return spannableReplaceAnimatedEmoji;
    }

    public void enableStickerMode(TLRPC.Document document, boolean z, Utilities.Callback2 callback2) {
        this.replacedSticker = document;
        this.stickerEmpty = z;
        this.stickerEmptySent = false;
        this.customStickerHandler = callback2;
        this.rotate = 0.0f;
        this.animateToRotate = 0.0f;
        if (this.stickerMakerView != null) {
            BlurButton blurButton = this.outlineBtn;
            if (blurButton != null) {
                blurButton.setActive(false, false);
            }
            this.stickerMakerView.clean();
            ArrayList arrayList = this.selectedEmojis;
            if (arrayList != null) {
                arrayList.clear();
            }
        }
        if (this.replacedSticker != null) {
            ArrayList arrayList2 = this.selectedEmojis;
            if (arrayList2 == null) {
                this.selectedEmojis = new ArrayList();
            } else {
                arrayList2.clear();
            }
            ArrayList<String> arrayListFindStickerEmoticons = MessageObject.findStickerEmoticons(document, Integer.valueOf(this.currentAccount));
            if (arrayListFindStickerEmoticons != null) {
                this.selectedEmojis.addAll(arrayListFindStickerEmoticons);
            }
        }
        BlurButton blurButton2 = this.cutOutBtn;
        if (blurButton2 != null) {
            blurButton2.clean();
        }
        showStickerMode(true, false);
        ImageView imageView = this.tuneItem;
        if (imageView != null) {
            imageView.setAlpha(this.stickerEmpty ? 0.4f : 1.0f);
        }
    }

    public void prepareSegmentImage() {
        StickerMakerView stickerMakerView = this.stickerMakerView;
        if (stickerMakerView == null || this.sendPhotoType != 11) {
            return;
        }
        if (this.stickerEmpty) {
            stickerMakerView.clean();
        } else {
            stickerMakerView.segmentImage(this.centerImage.getBitmap(), this.centerImage.getOrientation(), getContainerViewWidth(), getContainerViewHeight(), new Utilities.Callback() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda127
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$prepareSegmentImage$129((StickerMakerView.SegmentedObject) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$prepareSegmentImage$129(StickerMakerView.SegmentedObject segmentedObject) {
        try {
            boolean z = !TextUtils.isEmpty(((MediaController.MediaEditState) this.imagesArrLocals.get(this.currentIndex)).filterPath);
            this.stickerMakerView.setSegmentedState(true, segmentedObject);
            this.centerImage.setImageBitmap(this.stickerMakerView.getSegmentedImage(this.centerImage.getBitmap(), z, this.centerImage.getOrientation()));
            this.cutOutBtn.setUndoCutState(true);
            showStickerMode(true, true, true);
            this.cutOutBtn.post(new PhotoViewer$$ExternalSyntheticLambda136(this));
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    private void showStickerMode(boolean z, boolean z2) {
        showStickerMode(z, z, z2);
    }

    private void showStickerMode(boolean z, boolean z2, boolean z3) {
        if (!z3) {
            this.stickerMakerView.animate().setListener(null).cancel();
            this.stickerMakerView.setVisibility(z ? 0 : 8);
            this.stickerMakerView.setAlpha(this.pickerView.getAlpha());
            this.stickerMakerBackgroundView.animate().setListener(null).cancel();
            this.stickerMakerBackgroundView.setVisibility(z ? 0 : 8);
            this.stickerMakerBackgroundView.setAlpha(z ? 1.0f : 0.0f);
        } else if (z && this.stickerMakerView.getTag() == null) {
            this.stickerMakerView.animate().setListener(null).cancel();
            this.stickerMakerBackgroundView.animate().setListener(null).cancel();
            if (this.stickerMakerView.getVisibility() != 0) {
                this.stickerMakerView.setVisibility(0);
                this.stickerMakerView.animate().alpha(1.0f).start();
                this.stickerMakerBackgroundView.setVisibility(0);
                this.stickerMakerBackgroundView.animate().alpha(1.0f).start();
            }
        } else if (!z && this.stickerMakerView.getTag() != null) {
            this.stickerMakerView.animate().setListener(null).cancel();
            this.stickerMakerView.animate().alpha(0.0f).setListener(new HideViewAfterAnimation(this.stickerMakerView)).start();
            this.stickerMakerBackgroundView.animate().setListener(null).cancel();
            this.stickerMakerBackgroundView.animate().alpha(0.0f).setListener(new HideViewAfterAnimation(this.stickerMakerBackgroundView)).start();
        }
        this.stickerMakerView.setTag(z ? num : null);
        boolean z4 = (!z || this.cutOutBtn.isUndoCutState() || this.stickerEmpty) ? false : true;
        if (!z3) {
            this.cutOutBtn.animate().setListener(null).cancel();
            this.cutOutBtn.setVisibility(z4 ? 0 : 8);
            this.cutOutBtn.setAlpha(z4 ? 1.0f : 0.0f);
        } else if (z4 && this.cutOutBtn.getTag() == null) {
            this.cutOutBtn.animate().setListener(null).cancel();
            if (this.cutOutBtn.getVisibility() != 0) {
                this.cutOutBtn.setVisibility(0);
            }
            this.cutOutBtn.animate().alpha(1.0f).start();
        } else if (!z4 && this.cutOutBtn.getTag() != null) {
            this.cutOutBtn.animate().setListener(null).cancel();
            this.cutOutBtn.animate().alpha(0.0f).setListener(new HideViewAfterAnimation(this.cutOutBtn)).start();
        }
        this.cutOutBtn.setTag(z4 ? num : null);
        showEditStickerMode(z && this.cutOutBtn.isUndoCutState() && !this.stickerEmpty, z3);
        this.stickerMakerView.setOutlineVisible(z && this.cutOutBtn.isUndoCutState() && this.outlineBtn.isActive() && !this.eraseBtn.isActive() && !this.restoreBtn.isActive());
        boolean z5 = z && this.cutOutBtn.isUndoCutState() && !this.eraseBtn.isActive() && !this.restoreBtn.isActive();
        if (!z3) {
            this.outlineBtn.animate().setListener(null).cancel();
            this.outlineBtn.setVisibility(z5 ? 0 : 8);
            this.outlineBtn.setAlpha(z5 ? 1.0f : 0.0f);
            this.outlineBtn.setScaleX(z5 ? 1.0f : 0.8f);
            this.outlineBtn.setScaleY(z5 ? 1.0f : 0.8f);
        } else if (z5 && this.outlineBtn.getTag() == null) {
            this.outlineBtn.animate().setListener(null).cancel();
            if (this.outlineBtn.getVisibility() != 0) {
                this.outlineBtn.setVisibility(0);
            }
            this.outlineBtn.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(400L).start();
        } else if (!z5 && this.outlineBtn.getTag() != null) {
            this.outlineBtn.animate().setListener(null).cancel();
            this.outlineBtn.animate().alpha(0.0f).scaleX(0.8f).scaleY(0.8f).setListener(new HideViewAfterAnimation(this.outlineBtn)).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(400L).start();
        }
        this.outlineBtn.setTag(z5 ? 1 : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showEditStickerMode(boolean z, boolean z2) {
        StickerMakerView stickerMakerView;
        MaskPaintView maskPaintView;
        boolean z3 = true;
        if (!z || (stickerMakerView = this.stickerMakerView) == null || (stickerMakerView.empty && !stickerMakerView.overriddenPaths() && ((maskPaintView = this.maskPaintView) == null || !maskPaintView.canUndo()))) {
            z3 = false;
        }
        if (!z2) {
            this.undoBtn.animate().setListener(null).cancel();
            this.undoBtn.setVisibility(z3 ? 0 : 8);
            this.undoBtn.setAlpha(z3 ? 1.0f : 0.0f);
            this.undoBtn.setScaleX(z3 ? 1.0f : 0.8f);
            this.undoBtn.setScaleY(z3 ? 1.0f : 0.8f);
        } else if (z3 && this.undoBtn.getTag() == null) {
            this.undoBtn.animate().setListener(null).cancel();
            if (this.undoBtn.getVisibility() != 0) {
                this.undoBtn.setVisibility(0);
                this.undoBtn.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(400L).start();
            }
        } else if (!z3 && this.undoBtn.getTag() != null) {
            this.undoBtn.animate().setListener(null).cancel();
            this.undoBtn.animate().alpha(0.0f).scaleX(0.8f).scaleY(0.8f).setListener(new HideViewAfterAnimation(this.undoBtn)).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(400L).start();
        }
        this.undoBtn.setTag(z3 ? num : null);
        if (!z2) {
            this.eraseBtn.animate().setListener(null).cancel();
            this.eraseBtn.setVisibility(z ? 0 : 8);
            this.eraseBtn.setAlpha(z ? 1.0f : 0.0f);
            this.eraseBtn.setScaleX(z ? 1.0f : 0.8f);
            this.eraseBtn.setScaleY(z ? 1.0f : 0.8f);
            this.restoreBtn.animate().setListener(null).cancel();
            this.restoreBtn.setVisibility(z ? 0 : 8);
            this.restoreBtn.setAlpha(z ? 1.0f : 0.0f);
            this.restoreBtn.setScaleX(z ? 1.0f : 0.8f);
            this.restoreBtn.setScaleY(z ? 1.0f : 0.8f);
        } else if (z && this.eraseBtn.getTag() == null) {
            this.eraseBtn.animate().setListener(null).cancel();
            this.restoreBtn.animate().setListener(null).cancel();
            if (this.eraseBtn.getVisibility() != 0) {
                this.eraseBtn.setVisibility(0);
                ViewPropertyAnimator viewPropertyAnimatorScaleY = this.eraseBtn.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f);
                CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
                viewPropertyAnimatorScaleY.setInterpolator(cubicBezierInterpolator).setDuration(400L).start();
                this.restoreBtn.setVisibility(0);
                this.restoreBtn.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setInterpolator(cubicBezierInterpolator).setDuration(400L).start();
            }
        } else if (!z && this.eraseBtn.getTag() != null) {
            this.eraseBtn.animate().setListener(null).cancel();
            ViewPropertyAnimator listener = this.eraseBtn.animate().alpha(0.0f).scaleX(0.8f).scaleY(0.8f).setListener(new HideViewAfterAnimation(this.eraseBtn));
            CubicBezierInterpolator cubicBezierInterpolator2 = CubicBezierInterpolator.EASE_OUT_QUINT;
            listener.setInterpolator(cubicBezierInterpolator2).setDuration(400L).start();
            this.restoreBtn.animate().setListener(null).cancel();
            this.restoreBtn.animate().alpha(0.0f).scaleX(0.8f).scaleY(0.8f).setListener(new HideViewAfterAnimation(this.restoreBtn)).setInterpolator(cubicBezierInterpolator2).setDuration(400L).start();
        }
        this.eraseBtn.setTag(z ? 1 : null);
    }

    private void showEditCaption(boolean z, boolean z2) {
        PhotoViewerProvider photoViewerProvider = this.placeProvider;
        boolean z3 = false;
        boolean z4 = photoViewerProvider != null && photoViewerProvider.isCaptionAbove();
        showEditCaption(this.captionEdit, z && !z4, z2, this.captionEditAlpha);
        CaptionPhotoViewer captionPhotoViewer = this.topCaptionEdit;
        if (z && z4) {
            z3 = true;
        }
        showEditCaption(captionPhotoViewer, z3, z2, this.topCaptionEditAlpha);
    }

    private void showEditCaption(final View view, boolean z, boolean z2, final float[] fArr) {
        PhotoViewerProvider photoViewerProvider = this.placeProvider;
        float fM1146dp = AndroidUtilities.m1146dp((photoViewerProvider == null || !photoViewerProvider.canMoveCaptionAbove()) ? 58.0f : 175.0f) * (view == this.topCaptionEdit ? -1.0f : 1.0f);
        if (!z2) {
            view.animate().setListener(null).cancel();
            view.setVisibility(z ? 0 : 8);
            if (z) {
                fM1146dp = 0.0f;
            }
            view.setTranslationY(fM1146dp);
            float alpha = this.pickerView.getAlpha();
            float f = z ? 1.0f : 0.0f;
            fArr[0] = f;
            view.setAlpha(alpha * f);
        } else if (z && view.getTag() == null) {
            if (view.getVisibility() != 0) {
                view.setVisibility(0);
                view.setAlpha(this.pickerView.getAlpha());
                view.setTranslationY(fM1146dp);
            }
            view.animate().translationY(0.0f).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda96
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$showEditCaption$130(view, fArr, valueAnimator);
                }
            }).setDuration(420L).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).start();
        } else if (!z && view.getTag() != null) {
            view.animate().translationY(fM1146dp).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda97
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$showEditCaption$131(view, fArr, valueAnimator);
                }
            }).setDuration(420L).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).withEndAction(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda98
                @Override // java.lang.Runnable
                public final void run() {
                    view.setVisibility(8);
                }
            }).start();
        }
        view.setTag(z ? 1 : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditCaption$130(View view, float[] fArr, ValueAnimator valueAnimator) {
        float alpha = this.pickerView.getAlpha();
        float animatedFraction = valueAnimator.getAnimatedFraction();
        fArr[0] = animatedFraction;
        view.setAlpha(alpha * animatedFraction);
        if (view == this.captionEdit) {
            FrameLayout frameLayout = this.videoTimelineViewContainer;
            if (frameLayout != null) {
                frameLayout.setTranslationY(this.pickerView.getTranslationY() - (Math.max(0, this.captionEdit.getEditTextHeight() - AndroidUtilities.m1146dp(46.0f)) * this.captionEdit.getAlpha()));
            }
            this.muteButton.setTranslationY((-Math.max(0, this.captionEdit.getEditTextHeight() - AndroidUtilities.m1146dp(46.0f))) * this.captionEdit.getAlpha());
            this.editCoverButton.setTranslationY((-Math.max(0, this.captionEdit.getEditTextHeight() - AndroidUtilities.m1146dp(46.0f))) * this.captionEdit.getAlpha());
        }
        invalidateBlur();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showEditCaption$131(View view, float[] fArr, ValueAnimator valueAnimator) {
        float alpha = this.pickerView.getAlpha();
        float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
        fArr[0] = animatedFraction;
        view.setAlpha(alpha * animatedFraction);
        if (view == this.captionEdit) {
            FrameLayout frameLayout = this.videoTimelineViewContainer;
            if (frameLayout != null) {
                frameLayout.setTranslationY(this.pickerView.getTranslationY() - (Math.max(0, this.captionEdit.getEditTextHeight() - AndroidUtilities.m1146dp(46.0f)) * this.captionEdit.getAlpha()));
            }
            this.muteButton.setTranslationY((-Math.max(0, this.captionEdit.getEditTextHeight() - AndroidUtilities.m1146dp(46.0f))) * this.captionEdit.getAlpha());
            this.editCoverButton.setTranslationY((-Math.max(0, this.captionEdit.getEditTextHeight() - AndroidUtilities.m1146dp(46.0f))) * this.captionEdit.getAlpha());
        }
        invalidateBlur();
    }

    private void showVideoTimeline(boolean z, boolean z2) {
        if (!z2) {
            this.videoTimelineViewContainer.animate().setListener(null).cancel();
            this.videoTimelineViewContainer.setVisibility(z ? 0 : 8);
            this.videoTimelineView.setTranslationY(0.0f);
            this.videoTimelineViewContainer.setAlpha(this.pickerView.getAlpha());
        } else if (z && this.videoTimelineViewContainer.getTag() == null) {
            if (this.videoTimelineViewContainer.getVisibility() != 0) {
                this.videoTimelineViewContainer.setVisibility(0);
                this.videoTimelineViewContainer.setAlpha(this.pickerView.getAlpha());
                this.videoTimelineView.setTranslationY(AndroidUtilities.m1146dp(58.0f));
            }
            ObjectAnimator objectAnimator = this.videoTimelineAnimator;
            if (objectAnimator != null) {
                objectAnimator.removeAllListeners();
                this.videoTimelineAnimator.cancel();
            }
            VideoTimelinePlayView videoTimelinePlayView = this.videoTimelineView;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(videoTimelinePlayView, (Property<VideoTimelinePlayView, Float>) View.TRANSLATION_Y, videoTimelinePlayView.getTranslationY(), 0.0f);
            this.videoTimelineAnimator = objectAnimatorOfFloat;
            objectAnimatorOfFloat.setDuration(220L);
            this.videoTimelineAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.videoTimelineAnimator.start();
        } else if (!z && this.videoTimelineViewContainer.getTag() != null) {
            ObjectAnimator objectAnimator2 = this.videoTimelineAnimator;
            if (objectAnimator2 != null) {
                objectAnimator2.removeAllListeners();
                this.videoTimelineAnimator.cancel();
            }
            VideoTimelinePlayView videoTimelinePlayView2 = this.videoTimelineView;
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(videoTimelinePlayView2, (Property<VideoTimelinePlayView, Float>) View.TRANSLATION_Y, videoTimelinePlayView2.getTranslationY(), AndroidUtilities.m1146dp(58.0f));
            this.videoTimelineAnimator = objectAnimatorOfFloat2;
            objectAnimatorOfFloat2.addListener(new HideViewAfterAnimation(this.videoTimelineViewContainer));
            this.videoTimelineAnimator.setDuration(220L);
            this.videoTimelineAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.videoTimelineAnimator.start();
        }
        FrameLayout frameLayout = this.videoTimelineViewContainer;
        if (frameLayout != null && frameLayout.getVisibility() != 8) {
            this.videoTimelineViewContainer.setTranslationY(this.pickerView.getTranslationY() - (Math.max(0, this.captionEdit.getEditTextHeight() - AndroidUtilities.m1146dp(46.0f)) * this.captionEdit.getAlpha()));
        }
        this.videoTimelineViewContainer.setTag(z ? 1 : null);
    }

    public static TLRPC.FileLocation getFileLocation(ImageLocation imageLocation) {
        if (imageLocation == null) {
            return null;
        }
        return imageLocation.location;
    }

    public static String getFileLocationExt(ImageLocation imageLocation) {
        if (imageLocation == null || imageLocation.imageType != 2) {
            return null;
        }
        return "mp4";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImageIndex(int i) throws Resources.NotFoundException, NumberFormatException {
        setImageIndex(i, true, false);
    }

    private void setImageIndex(int i, boolean z, boolean z2) throws Resources.NotFoundException, NumberFormatException {
        setImageIndex(i, z, z2, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01fa  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x049c  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x04c6  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x04d6  */
    /* JADX WARN: Removed duplicated region for block: B:231:0x04d9  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x055a  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x055d A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0566  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0568  */
    /* JADX WARN: Removed duplicated region for block: B:259:0x057d  */
    /* JADX WARN: Removed duplicated region for block: B:262:0x0587 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:279:0x06a7  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x06ae A[LOOP:0: B:280:0x06ab->B:282:0x06ae, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:285:0x06bc  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x06e7  */
    /* JADX WARN: Removed duplicated region for block: B:327:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r3v49 */
    /* JADX WARN: Type inference failed for: r3v50, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v51 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setImageIndex(int r37, boolean r38, boolean r39, boolean r40) throws android.content.res.Resources.NotFoundException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 1846
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.setImageIndex(int, boolean, boolean, boolean):void");
    }

    private void resetIndexForDeferredImageLoading() throws Resources.NotFoundException {
        Object mark = this.centerImage.getMark();
        if (mark == null || !mark.equals(1)) {
            return;
        }
        setIndexToImage(this.centerImage, this.currentIndex, null);
    }

    private void setCurrentCaption(MessageObject messageObject, CharSequence charSequence, boolean z, boolean z2) {
        int i;
        boolean z3;
        TLRPC.Message message;
        CharSequence charSequenceCloneSpans = AnimatedEmojiSpan.cloneSpans(charSequence, 3);
        showEditCaption(this.editing, z2);
        if (!this.editing) {
            if (this.sendPhotoType != 1) {
                this.captionEdit.setVisibility(8);
                this.topCaptionEdit.setVisibility(8);
                if (this.needCaptionLayout) {
                    if (this.captionTextViewSwitcher.getParent() != this.pickerView) {
                        FrameLayout frameLayout = this.captionContainer;
                        if (frameLayout != null) {
                            frameLayout.removeView(this.captionTextViewSwitcher);
                        }
                        this.captionTextViewSwitcher.setMeasureAllChildren(false);
                        this.pickerView.addView(this.captionTextViewSwitcher, LayoutHelper.createFrame(-1, -2.0f, 83, 0.0f, 0.0f, 76.0f, 48.0f));
                    }
                } else {
                    if (this.captionScrollView == null) {
                        FrameLayout frameLayout2 = new FrameLayout(this.containerView.getContext());
                        this.captionContainer = frameLayout2;
                        this.captionTextViewSwitcher.setContainer(frameLayout2);
                        CaptionScrollView captionScrollView = new CaptionScrollView(this.containerView.getContext(), this.captionTextViewSwitcher, this.captionContainer) { // from class: org.telegram.ui.PhotoViewer.72
                            @Override // org.telegram.ui.PhotoViewer.CaptionScrollView
                            protected boolean isStatusBarVisible() {
                                return !PhotoViewer.this.inBubbleMode;
                            }

                            @Override // android.view.View
                            public void invalidate() {
                                super.invalidate();
                                if (PhotoViewer.this.isActionBarVisible) {
                                    int scrollY = getScrollY();
                                    float translationY = PhotoViewer.this.captionTextViewSwitcher.getTranslationY();
                                    boolean z4 = scrollY == 0 && translationY == 0.0f;
                                    boolean z5 = scrollY == 0 && translationY == 0.0f;
                                    if (!z4) {
                                        int y = PhotoViewer.this.photoProgressViews[0].getY() + PhotoViewer.this.photoProgressViews[0].size;
                                        int top = (((PhotoViewer.this.captionContainer.getTop() + ((int) translationY)) - scrollY) + ((isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight())) - AndroidUtilities.m1146dp(12.0f);
                                        z5 = top > ((int) PhotoViewer.this.fullscreenButton[0].getY()) + AndroidUtilities.m1146dp(32.0f);
                                        z4 = top > y;
                                    }
                                    if (PhotoViewer.this.allowShowFullscreenButton) {
                                        if (PhotoViewer.this.fullscreenButton[0].getTag() != null && ((Integer) PhotoViewer.this.fullscreenButton[0].getTag()).intValue() == 3 && z5) {
                                            PhotoViewer.this.fullscreenButton[0].setTag(2);
                                            PhotoViewer.this.fullscreenButton[0].animate().alpha(1.0f).setDuration(150L).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.72.1
                                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                                public void onAnimationEnd(Animator animator) {
                                                    PhotoViewer.this.fullscreenButton[0].setTag(null);
                                                }
                                            }).start();
                                        } else if (PhotoViewer.this.fullscreenButton[0].getTag() == null && !z5) {
                                            PhotoViewer.this.fullscreenButton[0].setTag(3);
                                            PhotoViewer.this.fullscreenButton[0].animate().alpha(0.0f).setListener(null).setDuration(150L).start();
                                        }
                                    }
                                    PhotoViewer.this.photoProgressViews[0].setIndexedAlpha(2, z4 ? 1.0f : 0.0f, true);
                                }
                            }
                        };
                        this.captionScrollView = captionScrollView;
                        this.captionTextViewSwitcher.setScrollView(captionScrollView);
                        this.captionContainer.setClipChildren(false);
                        this.captionScrollView.addView(this.captionContainer, new ViewGroup.LayoutParams(-1, -2));
                        this.containerView.addView(this.captionScrollView, LayoutHelper.createFrame(-1, -1, 80));
                    }
                    if (this.captionTextViewSwitcher.getParent() != this.captionContainer) {
                        this.pickerView.removeView(this.captionTextViewSwitcher);
                        this.captionTextViewSwitcher.setMeasureAllChildren(true);
                        this.captionContainer.addView(this.captionTextViewSwitcher, -1, -2);
                        this.videoPreviewFrame.bringToFront();
                    }
                    if (messageObject != null && messageObject.isSponsored()) {
                        createAdButtonView();
                        AndroidUtilities.removeFromParent(this.adButtonView);
                        this.adButtonTextView.setText(messageObject.sponsoredButtonText);
                        this.captionContainer.addView(this.adButtonView, LayoutHelper.createFrame(-1, 44.0f, 87, 16.0f, 0.0f, 16.0f, 12.0f));
                        this.captionTextViewSwitcher.setPadding(0, 0, 0, AndroidUtilities.m1146dp(64.0f));
                        this.adButtonView.bringToFront();
                    } else {
                        FrameLayout frameLayout3 = this.adButtonView;
                        if (frameLayout3 != null) {
                            AndroidUtilities.removeFromParent(frameLayout3);
                            this.captionTextViewSwitcher.setPadding(0, 0, 0, 0);
                        }
                    }
                }
                boolean zIsEmpty = TextUtils.isEmpty(charSequenceCloneSpans);
                boolean zIsEmpty2 = TextUtils.isEmpty(this.captionTextViewSwitcher.getCurrentView().getText());
                CaptionTextViewSwitcher captionTextViewSwitcher = this.captionTextViewSwitcher;
                TextView nextView = z2 ? captionTextViewSwitcher.getNextView() : captionTextViewSwitcher.getCurrentView();
                if (this.isCurrentVideo) {
                    if (nextView.getMaxLines() != 1) {
                        this.captionTextViewSwitcher.getCurrentView().setMaxLines(1);
                        this.captionTextViewSwitcher.getNextView().setMaxLines(1);
                        this.captionTextViewSwitcher.getCurrentView().setSingleLine(true);
                        this.captionTextViewSwitcher.getNextView().setSingleLine(true);
                        TextView currentView = this.captionTextViewSwitcher.getCurrentView();
                        TextUtils.TruncateAt truncateAt = TextUtils.TruncateAt.END;
                        currentView.setEllipsize(truncateAt);
                        this.captionTextViewSwitcher.getNextView().setEllipsize(truncateAt);
                    }
                } else {
                    int maxLines = nextView.getMaxLines();
                    if (maxLines == 1) {
                        this.captionTextViewSwitcher.getCurrentView().setSingleLine(false);
                        this.captionTextViewSwitcher.getNextView().setSingleLine(false);
                    }
                    if (this.needCaptionLayout) {
                        Point point = AndroidUtilities.displaySize;
                        i = point.x > point.y ? 5 : 10;
                    } else {
                        i = ConnectionsManager.DEFAULT_DATACENTER_ID;
                    }
                    if (maxLines != i) {
                        this.captionTextViewSwitcher.getCurrentView().setMaxLines(i);
                        this.captionTextViewSwitcher.getNextView().setMaxLines(i);
                        this.captionTextViewSwitcher.getCurrentView().setEllipsize(null);
                        this.captionTextViewSwitcher.getNextView().setEllipsize(null);
                    }
                }
                nextView.setScrollX(0);
                boolean z4 = this.needCaptionLayout;
                this.dontChangeCaptionPosition = !z4 && z2 && zIsEmpty;
                if (!z4) {
                    this.captionScrollView.dontChangeTopMargin = false;
                }
                if (z2) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        TransitionManager.endTransitions(z4 ? this.pickerView : this.captionScrollView);
                    }
                    if (this.needCaptionLayout) {
                        TransitionSet transitionSet = new TransitionSet();
                        transitionSet.setOrdering(0);
                        transitionSet.addTransition(new ChangeBounds());
                        transitionSet.addTransition(new Fade(2));
                        transitionSet.addTransition(new Fade(1));
                        transitionSet.setDuration(200L);
                        TransitionManager.beginDelayedTransition(this.pickerView, transitionSet);
                    } else {
                        TransitionSet duration = new TransitionSet().addTransition(new C584974(2, zIsEmpty2, zIsEmpty)).addTransition(new C584873(1, zIsEmpty2, zIsEmpty)).setDuration(200L);
                        if (!zIsEmpty2) {
                            this.captionScrollView.dontChangeTopMargin = true;
                            duration.addTransition(new C585075());
                        }
                        if (zIsEmpty2 && !zIsEmpty) {
                            duration.addTarget((View) this.captionTextViewSwitcher);
                        }
                        TransitionManager.beginDelayedTransition(this.captionScrollView, duration);
                    }
                    z3 = true;
                } else {
                    this.captionTextViewSwitcher.getCurrentView().setText((CharSequence) null);
                    CaptionScrollView captionScrollView2 = this.captionScrollView;
                    if (captionScrollView2 != null) {
                        captionScrollView2.scrollTo(0, 0);
                    }
                    z3 = false;
                }
                if (!zIsEmpty) {
                    Theme.createChatResources(null, true);
                    if (messageObject == null || !this.captionTranslated || (message = messageObject.messageOwner) == null || message.translatedText == null || !TextUtils.equals(message.translatedToLanguage, TranslateAlert2.getToLanguage())) {
                        if (messageObject != null && !messageObject.messageOwner.entities.isEmpty()) {
                            SpannableString spannableString = new SpannableString(charSequenceCloneSpans);
                            messageObject.addEntitiesToText(spannableString, true, false);
                            if (messageObject.isVideo()) {
                                MessageObject.addUrlsByPattern(messageObject.isOutOwner(), spannableString, false, 3, (int) messageObject.getDuration(), false);
                            }
                            charSequenceCloneSpans = Emoji.replaceEmoji(spannableString, nextView.getPaint().getFontMetricsInt(), false);
                        } else {
                            charSequenceCloneSpans = Emoji.replaceEmoji(new SpannableStringBuilder(charSequenceCloneSpans), nextView.getPaint().getFontMetricsInt(), false);
                        }
                    }
                    if (messageObject != null && messageObject.isSponsored()) {
                        charSequenceCloneSpans = sponsoredCaption(messageObject, charSequenceCloneSpans);
                    }
                    this.captionTextViewSwitcher.setTag(charSequenceCloneSpans);
                    try {
                        this.captionTextViewSwitcher.setText(charSequenceCloneSpans, z2, this.lastCaptionTranslating != z);
                        CaptionScrollView captionScrollView3 = this.captionScrollView;
                        if (captionScrollView3 != null) {
                            captionScrollView3.updateTopMargin();
                        }
                    } catch (Exception e) {
                        FileLog.m1160e(e);
                    }
                    nextView.setScrollY(0);
                    nextView.setTextColor(-1);
                    this.captionTextViewSwitcher.setVisibility(this.isActionBarVisible && (!this.isCurrentVideo || this.pickerView.getVisibility() == 0 || this.pageBlocksAdapter != null) ? 0 : 4);
                } else if (this.needCaptionLayout) {
                    this.captionTextViewSwitcher.setText(LocaleController.getString("AddCaption", C2369R.string.AddCaption), z2);
                    this.captionTextViewSwitcher.getCurrentView().setTextColor(-1291845633);
                    this.captionTextViewSwitcher.setTag("empty");
                    this.captionTextViewSwitcher.setVisibility(0);
                } else {
                    this.captionTextViewSwitcher.setText(null, z2);
                    this.captionTextViewSwitcher.getCurrentView().setTextColor(-1);
                    this.captionTextViewSwitcher.setVisibility(4, !z3 || zIsEmpty2);
                    this.captionTextViewSwitcher.setTag(null);
                }
                if (this.captionTextViewSwitcher.getCurrentView() instanceof CaptionTextView) {
                    ((CaptionTextView) this.captionTextViewSwitcher.getCurrentView()).setLoading(z);
                }
                this.lastCaptionTranslating = !zIsEmpty && z;
                return;
            }
        }
        getCaptionView().setText(charSequenceCloneSpans);
        this.captionTextViewSwitcher.setVisibility(8);
    }

    /* renamed from: org.telegram.ui.PhotoViewer$74 */
    /* loaded from: classes5.dex */
    class C584974 extends Fade {
        final /* synthetic */ boolean val$isCaptionEmpty;
        final /* synthetic */ boolean val$isCurrentCaptionEmpty;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C584974(int i, boolean z, boolean z2) {
            super(i);
            this.val$isCurrentCaptionEmpty = z;
            this.val$isCaptionEmpty = z2;
        }

        @Override // android.transition.Fade, android.transition.Visibility
        public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
            Animator animatorOnDisappear = super.onDisappear(viewGroup, view, transitionValues, transitionValues2);
            if (!this.val$isCurrentCaptionEmpty && this.val$isCaptionEmpty && view == PhotoViewer.this.captionTextViewSwitcher) {
                animatorOnDisappear.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.74.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        PhotoViewer.this.captionScrollView.setVisibility(4);
                        PhotoViewer.this.captionScrollView.backgroundAlpha = 1.0f;
                    }
                });
                ((ObjectAnimator) animatorOnDisappear).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$74$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$onDisappear$0(valueAnimator);
                    }
                });
            }
            return animatorOnDisappear;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisappear$0(ValueAnimator valueAnimator) {
            PhotoViewer.this.captionScrollView.backgroundAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            PhotoViewer.this.captionScrollView.invalidate();
        }
    }

    /* renamed from: org.telegram.ui.PhotoViewer$73 */
    /* loaded from: classes5.dex */
    class C584873 extends Fade {
        final /* synthetic */ boolean val$isCaptionEmpty;
        final /* synthetic */ boolean val$isCurrentCaptionEmpty;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C584873(int i, boolean z, boolean z2) {
            super(i);
            this.val$isCurrentCaptionEmpty = z;
            this.val$isCaptionEmpty = z2;
        }

        @Override // android.transition.Fade, android.transition.Visibility
        public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
            Animator animatorOnAppear = super.onAppear(viewGroup, view, transitionValues, transitionValues2);
            if (this.val$isCurrentCaptionEmpty && !this.val$isCaptionEmpty && view == PhotoViewer.this.captionTextViewSwitcher) {
                animatorOnAppear.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.73.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        PhotoViewer.this.captionScrollView.backgroundAlpha = 1.0f;
                    }
                });
                ((ObjectAnimator) animatorOnAppear).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$73$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$onAppear$0(valueAnimator);
                    }
                });
            }
            return animatorOnAppear;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAppear$0(ValueAnimator valueAnimator) {
            PhotoViewer.this.captionScrollView.backgroundAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            PhotoViewer.this.captionScrollView.invalidate();
        }
    }

    /* renamed from: org.telegram.ui.PhotoViewer$75 */
    /* loaded from: classes5.dex */
    class C585075 extends Transition {
        C585075() {
        }

        @Override // android.transition.Transition
        public void captureStartValues(TransitionValues transitionValues) {
            if (transitionValues.view == PhotoViewer.this.captionScrollView) {
                transitionValues.values.put("scrollY", Integer.valueOf(PhotoViewer.this.captionScrollView.getScrollY()));
            }
        }

        @Override // android.transition.Transition
        public void captureEndValues(TransitionValues transitionValues) {
            if (transitionValues.view == PhotoViewer.this.captionTextViewSwitcher) {
                transitionValues.values.put("translationY", Integer.valueOf(PhotoViewer.this.captionScrollView.getPendingMarginTopDiff()));
            }
        }

        @Override // android.transition.Transition
        public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
            int iIntValue;
            if (transitionValues.view == PhotoViewer.this.captionScrollView) {
                ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(((Integer) transitionValues.values.get("scrollY")).intValue(), 0);
                valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.75.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        PhotoViewer.this.captionTextViewSwitcher.getNextView().setText((CharSequence) null);
                        PhotoViewer.this.captionScrollView.applyPendingTopMargin();
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                        PhotoViewer.this.captionScrollView.stopScrolling();
                    }
                });
                valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$75$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$createAnimator$0(valueAnimator);
                    }
                });
                return valueAnimatorOfInt;
            }
            if (transitionValues2.view != PhotoViewer.this.captionTextViewSwitcher || (iIntValue = ((Integer) transitionValues2.values.get("translationY")).intValue()) == 0) {
                return null;
            }
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(PhotoViewer.this.captionTextViewSwitcher, (Property<CaptionTextViewSwitcher, Float>) View.TRANSLATION_Y, 0.0f, iIntValue);
            objectAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.75.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    PhotoViewer.this.captionTextViewSwitcher.setTranslationY(0.0f);
                }
            });
            return objectAnimatorOfFloat;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createAnimator$0(ValueAnimator valueAnimator) {
            PhotoViewer.this.captionScrollView.scrollTo(0, ((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCaptionHwLayerEnabled(boolean z) {
        if (this.captionHwLayerEnabled != z) {
            this.captionHwLayerEnabled = z;
            this.captionTextViewSwitcher.setLayerType(2, null);
            this.captionTextViewSwitcher.getCurrentView().setLayerType(2, null);
            this.captionTextViewSwitcher.getNextView().setLayerType(2, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v15, types: [org.telegram.messenger.FileLoader$FileResolver] */
    /* JADX WARN: Type inference failed for: r5v22 */
    /* JADX WARN: Type inference failed for: r5v24 */
    /* JADX WARN: Type inference failed for: r5v25 */
    public void checkProgress(final int i, boolean z, final boolean z2) {
        MessageObject messageObject;
        final File file;
        FileLoader.FileResolver fileResolver;
        boolean zShouldIndexAutoPlayed;
        final boolean z3;
        MessageObject messageObject2;
        final boolean z4;
        File pathToAttach;
        File file2;
        boolean z5;
        final ?? r5;
        FileLoader.FileResolver fileResolver2;
        AnimatedFileDrawable animatedFileDrawable;
        int i2 = this.currentIndex;
        int i3 = i == 1 ? i2 + 1 : i == 2 ? i2 - 1 : i2;
        boolean z6 = false;
        if (this.currentFileNames[i] != null) {
            boolean zHasBitmap = (i == 0 && i2 == 0 && (animatedFileDrawable = this.currentAnimation) != null) ? animatedFileDrawable.hasBitmap() : false;
            File file3 = null;
            if (this.currentMessageObject != null) {
                if (i3 < 0 || i3 >= this.imagesArr.size()) {
                    this.photoProgressViews[i].setBackgroundState(-1, z2, true);
                    return;
                }
                MessageObject messageObject3 = (MessageObject) this.imagesArr.get(i3);
                zShouldIndexAutoPlayed = shouldMessageObjectAutoPlayed(messageObject3);
                if (this.sharedMediaType == 1 && !messageObject3.canPreviewDocument()) {
                    this.photoProgressViews[i].setBackgroundState(-1, z2, true);
                    return;
                }
                File file4 = !TextUtils.isEmpty(messageObject3.messageOwner.attachPath) ? new File(messageObject3.messageOwner.attachPath) : null;
                if ((MessageObject.getMedia(messageObject3.messageOwner) instanceof TLRPC.TL_messageMediaWebPage) && MessageObject.getMedia(messageObject3.messageOwner).webpage != null && MessageObject.getMedia(messageObject3.messageOwner).webpage.document == null) {
                    final TLObject fileLocation = getFileLocation(i3, null);
                    fileResolver2 = new FileLoader.FileResolver() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda64
                        @Override // org.telegram.messenger.FileLoader.FileResolver
                        public final File getFile() {
                            return this.f$0.lambda$checkProgress$134(fileLocation);
                        }
                    };
                } else {
                    final TLRPC.Message message = messageObject3.messageOwner;
                    fileResolver2 = new FileLoader.FileResolver() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda65
                        @Override // org.telegram.messenger.FileLoader.FileResolver
                        public final File getFile() {
                            return this.f$0.lambda$checkProgress$135(message);
                        }
                    };
                }
                if (messageObject3.isVideo()) {
                    FileLoader.FileResolver fileResolver3 = fileResolver2;
                    z4 = (SharedConfig.streamMedia && messageObject3.canStreamVideo() && !DialogObject.isEncryptedDialog(messageObject3.getDialogId())) || messageObject3.hasVideoQualities();
                    messageObject = messageObject3;
                    file = file4;
                    r5 = fileResolver3;
                    z3 = true;
                } else {
                    messageObject = messageObject3;
                    file = file4;
                    fileResolver = fileResolver2;
                    z4 = false;
                    z3 = false;
                    r5 = fileResolver;
                }
            } else if (this.currentBotInlineResult != null) {
                if (i3 < 0 || i3 >= this.imagesArrLocals.size()) {
                    this.photoProgressViews[i].setBackgroundState(-1, z2, true);
                    return;
                }
                TLRPC.BotInlineResult botInlineResult = (TLRPC.BotInlineResult) this.imagesArrLocals.get(i3);
                if (botInlineResult.type.equals(MediaStreamTrack.VIDEO_TRACK_KIND) || MessageObject.isVideoDocument(botInlineResult.document)) {
                    if (botInlineResult.document != null) {
                        file2 = FileLoader.getInstance(this.currentAccount).getPathToAttach(botInlineResult.document);
                    } else if (botInlineResult.content instanceof TLRPC.TL_webDocument) {
                        file2 = new File(FileLoader.getDirectory(4), Utilities.MD5(botInlineResult.content.url) + "." + ImageLoader.getHttpUrlExtension(botInlineResult.content.url, "mp4"));
                    } else {
                        file2 = null;
                    }
                    z5 = true;
                } else {
                    if (botInlineResult.document != null) {
                        file2 = new File(FileLoader.getDirectory(3), this.currentFileNames[i]);
                    } else {
                        file2 = botInlineResult.photo != null ? new File(FileLoader.getDirectory(0), this.currentFileNames[i]) : null;
                    }
                    z5 = false;
                }
                z3 = z5;
                r5 = 0;
                z4 = false;
                file = file2;
                file3 = new File(FileLoader.getDirectory(4), this.currentFileNames[i]);
                messageObject = null;
                zShouldIndexAutoPlayed = false;
            } else {
                if (this.currentFileLocation != null) {
                    if (i3 < 0 || i3 >= this.imagesArrLocationsVideo.size()) {
                        this.photoProgressViews[i].setBackgroundState(-1, z2, true);
                        return;
                    }
                    ImageLocation imageLocation = (ImageLocation) this.imagesArrLocationsVideo.get(i3);
                    if (imageLocation != null) {
                        file = FileLoader.getInstance(this.currentAccount).getPathToAttach(imageLocation.location, getFileLocationExt(imageLocation), false);
                        pathToAttach = FileLoader.getInstance(this.currentAccount).getPathToAttach(imageLocation.location, getFileLocationExt(imageLocation), true);
                    } else {
                        pathToAttach = null;
                        file = null;
                    }
                } else if (this.currentSecureDocument != null) {
                    if (i3 < 0 || i3 >= this.secureDocuments.size()) {
                        this.photoProgressViews[i].setBackgroundState(-1, z2, true);
                        return;
                    } else {
                        SecureDocument secureDocument = (SecureDocument) this.secureDocuments.get(i3);
                        file = FileLoader.getInstance(this.currentAccount).getPathToAttach(secureDocument, true);
                        pathToAttach = FileLoader.getInstance(this.currentAccount).getPathToAttach(secureDocument, false);
                    }
                } else {
                    if (this.currentPathObject != null) {
                        File file5 = new File(FileLoader.getDirectory(3), this.currentFileNames[i]);
                        messageObject2 = null;
                        zShouldIndexAutoPlayed = false;
                        z4 = false;
                        z3 = false;
                        file3 = new File(FileLoader.getDirectory(4), this.currentFileNames[i]);
                        file = file5;
                    } else {
                        PageBlocksAdapter pageBlocksAdapter = this.pageBlocksAdapter;
                        if (pageBlocksAdapter != null) {
                            file = pageBlocksAdapter.getFile(i3);
                            z3 = this.pageBlocksAdapter.isVideo(i3) || this.pageBlocksAdapter.isHardwarePlayer(i3);
                            messageObject2 = null;
                            z4 = false;
                            zShouldIndexAutoPlayed = shouldIndexAutoPlayed(i3);
                        } else {
                            messageObject = null;
                            file = null;
                            fileResolver = null;
                            zShouldIndexAutoPlayed = false;
                            z4 = false;
                            z3 = false;
                            r5 = fileResolver;
                        }
                    }
                    messageObject = messageObject2;
                    r5 = messageObject2;
                }
                messageObject2 = null;
                zShouldIndexAutoPlayed = false;
                z4 = false;
                z3 = false;
                file3 = pathToAttach;
                messageObject = messageObject2;
                r5 = messageObject2;
            }
            final boolean z7 = !(i == 0 && this.dontAutoPlay) && zShouldIndexAutoPlayed;
            final File file6 = file3;
            final MessageObject messageObject4 = messageObject;
            final boolean z8 = zHasBitmap;
            Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda66
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$checkProgress$137(z8, file, file6, r5, i, messageObject4, z4, z3, z7, z2);
                }
            });
            return;
        }
        if (!this.imagesArrLocals.isEmpty() && i3 >= 0 && i3 < this.imagesArrLocals.size()) {
            Object obj = this.imagesArrLocals.get(i3);
            if (obj instanceof MediaController.PhotoEntry) {
                z6 = ((MediaController.PhotoEntry) obj).isVideo;
            }
        }
        if (z6) {
            this.photoProgressViews[i].setBackgroundState(3, z2, true);
        } else {
            this.photoProgressViews[i].setBackgroundState(-1, z2, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ File lambda$checkProgress$134(TLObject tLObject) {
        return FileLoader.getInstance(this.currentAccount).getPathToAttach(tLObject, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ File lambda$checkProgress$135(TLRPC.Message message) {
        return FileLoader.getInstance(this.currentAccount).getPathToMessage(message);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkProgress$137(boolean z, final File file, File file2, FileLoader.FileResolver fileResolver, final int i, MessageObject messageObject, final boolean z2, final boolean z3, final boolean z4, final boolean z5) {
        final File file3;
        ChatActivity chatActivity;
        TLRPC.Document document;
        boolean zExists = (z || file == null) ? z : file.exists();
        if (file2 == null && fileResolver != null) {
            file3 = fileResolver.getFile();
        } else {
            file = fileResolver != null ? fileResolver.getFile() : null;
            file3 = file2;
        }
        if (!zExists && file3 != null) {
            zExists = file3.exists();
        }
        if (!zExists && file != null) {
            zExists = file.exists();
        }
        final boolean z6 = zExists;
        if (!z6 && i != 0 && messageObject != null && z2 && DownloadController.getInstance(this.currentAccount).canDownloadMedia(messageObject.messageOwner) != 0 && (((chatActivity = this.parentChatActivity) == null || chatActivity.getCurrentEncryptedChat() == null) && !messageObject.shouldEncryptPhotoOrVideo() && (document = messageObject.getDocument()) != null)) {
            FileLoader.getInstance(this.currentAccount).loadFile(document, messageObject, 0, 10);
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda85
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkProgress$136(i, file, file3, z6, z2, z3, z4, z5);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkProgress$136(int i, File file, File file2, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        boolean z6 = false;
        if (this.shownControlsByEnd && !this.actionBarWasShownBeforeByEnd && this.isPlaying) {
            this.photoProgressViews[i].setBackgroundState(3, false, false);
            return;
        }
        if ((file != null || file2 != null) && (z || z2)) {
            if (i != 0 || !this.isPlaying) {
                if (z3 && (!z4 || (i == 0 && this.playerWasPlaying))) {
                    this.photoProgressViews[i].setBackgroundState(3, z5, true);
                } else {
                    this.photoProgressViews[i].setBackgroundState(-1, z5, true);
                }
            }
            if (i == 0 && !this.menuItem.isSubMenuShowing()) {
                if (z || !FileLoader.getInstance(this.currentAccount).isLoadingFile(this.currentFileNames[i])) {
                    this.menuItem.hideSubItem(8);
                } else {
                    this.menuItem.showSubItem(8);
                }
            }
        } else {
            if (z3) {
                if (!FileLoader.getInstance(this.currentAccount).isLoadingFile(this.currentFileNames[i])) {
                    this.photoProgressViews[i].setBackgroundState(2, false, true);
                } else {
                    this.photoProgressViews[i].setBackgroundState(1, false, true);
                }
            } else {
                this.photoProgressViews[i].setBackgroundState(0, z5, true);
            }
            Float fileProgress = ImageLoader.getInstance().getFileProgress(this.currentFileNames[i]);
            if (fileProgress == null) {
                fileProgress = Float.valueOf(0.0f);
            }
            this.photoProgressViews[i].setProgress(fileProgress.floatValue(), false);
        }
        if (i == 0) {
            if (!this.isEmbedVideo && (!this.imagesArrLocals.isEmpty() || (this.currentFileNames[0] != null && this.photoProgressViews[0].backgroundState != 0))) {
                z6 = true;
            }
            this.canZoom = z6;
        }
    }

    public int getSelectionLength() {
        if (getCaptionView().editText != null) {
            return getCaptionView().getSelectionLength();
        }
        return 0;
    }

    private void setIndexToPaintingOverlay(int i, PaintingOverlay paintingOverlay) {
        String str;
        ArrayList<VideoEditedInfo.MediaEntity> arrayList;
        boolean z;
        if (paintingOverlay == null) {
            return;
        }
        paintingOverlay.reset();
        paintingOverlay.setVisibility(8);
        if (this.imagesArrLocals.isEmpty() || i < 0 || i >= this.imagesArrLocals.size()) {
            return;
        }
        Object obj = this.imagesArrLocals.get(i);
        if (obj instanceof MediaController.PhotoEntry) {
            MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) obj;
            boolean z2 = photoEntry.isVideo;
            String str2 = photoEntry.paintPath;
            arrayList = photoEntry.mediaEntities;
            z = z2;
            str = str2;
        } else {
            if (obj instanceof MediaController.SearchImage) {
                MediaController.SearchImage searchImage = (MediaController.SearchImage) obj;
                String str3 = searchImage.paintPath;
                arrayList = searchImage.mediaEntities;
                str = str3;
            } else {
                str = null;
                arrayList = null;
            }
            z = false;
        }
        paintingOverlay.setVisibility(0);
        paintingOverlay.setData(str, arrayList, z, false, this.sendPhotoType != 11);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:168:0x038d  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x03bf  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03c7  */
    /* JADX WARN: Removed duplicated region for block: B:176:0x03d0  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x0768  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x079d  */
    /* JADX WARN: Type inference failed for: r10v13, types: [org.telegram.tgnet.TLRPC$User] */
    /* JADX WARN: Type inference failed for: r10v7, types: [org.telegram.tgnet.TLRPC$Chat] */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v3, types: [org.telegram.tgnet.TLObject] */
    /* JADX WARN: Type inference failed for: r9v6, types: [org.telegram.tgnet.TLRPC$Photo] */
    /* JADX WARN: Type inference failed for: r9v8, types: [org.telegram.tgnet.TLObject] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setIndexToImage(org.telegram.messenger.ImageReceiver r28, int r29, org.telegram.p023ui.Components.Crop.CropTransform r30) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 2083
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.setIndexToImage(org.telegram.messenger.ImageReceiver, int, org.telegram.ui.Components.Crop.CropTransform):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0048  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean isShowingImage(org.telegram.messenger.MessageObject r7) {
        /*
            org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.Instance
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L48
            org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.Instance
            boolean r0 = r0.pipAnimationInProgress
            if (r0 != 0) goto L48
            org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.Instance
            boolean r0 = r0.isVisible
            if (r0 == 0) goto L48
            org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.Instance
            boolean r0 = r0.disableShowCheck
            if (r0 != 0) goto L48
            if (r7 == 0) goto L48
            org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.Instance
            org.telegram.messenger.MessageObject r0 = r0.currentMessageObject
            if (r0 != 0) goto L2e
            org.telegram.ui.PhotoViewer r3 = org.telegram.p023ui.PhotoViewer.Instance
            org.telegram.ui.PhotoViewer$PhotoViewerProvider r3 = r3.placeProvider
            if (r3 == 0) goto L2e
            org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.Instance
            org.telegram.ui.PhotoViewer$PhotoViewerProvider r0 = r0.placeProvider
            org.telegram.messenger.MessageObject r0 = r0.getEditingMessageObject()
        L2e:
            if (r0 == 0) goto L48
            int r3 = r0.getId()
            int r4 = r7.getId()
            if (r3 != r4) goto L48
            long r3 = r0.getDialogId()
            long r5 = r7.getDialogId()
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 != 0) goto L48
            r0 = 1
            goto L49
        L48:
            r0 = 0
        L49:
            if (r0 != 0) goto L83
            org.telegram.ui.PhotoViewer r3 = org.telegram.p023ui.PhotoViewer.PipInstance
            if (r3 == 0) goto L83
            org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.PipInstance
            boolean r0 = r0.isVisible
            if (r0 == 0) goto L82
            org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.PipInstance
            boolean r0 = r0.disableShowCheck
            if (r0 != 0) goto L82
            if (r7 == 0) goto L82
            org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.PipInstance
            org.telegram.messenger.MessageObject r0 = r0.currentMessageObject
            if (r0 == 0) goto L82
            org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.PipInstance
            org.telegram.messenger.MessageObject r0 = r0.currentMessageObject
            int r0 = r0.getId()
            int r3 = r7.getId()
            if (r0 != r3) goto L82
            org.telegram.ui.PhotoViewer r0 = org.telegram.p023ui.PhotoViewer.PipInstance
            org.telegram.messenger.MessageObject r0 = r0.currentMessageObject
            long r3 = r0.getDialogId()
            long r5 = r7.getDialogId()
            int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r7 != 0) goto L82
            return r1
        L82:
            return r2
        L83:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.isShowingImage(org.telegram.messenger.MessageObject):boolean");
    }

    public static boolean isPlayingMessageInPip(MessageObject messageObject) {
        return (PipInstance == null || messageObject == null || PipInstance.currentMessageObject == null || PipInstance.currentMessageObject.getId() != messageObject.getId() || PipInstance.currentMessageObject.getDialogId() != messageObject.getDialogId()) ? false : true;
    }

    public static boolean isPlayingMessage(MessageObject messageObject) {
        return (Instance == null || Instance.pipAnimationInProgress || !Instance.isVisible || messageObject == null || Instance.currentMessageObject == null || Instance.currentMessageObject.getId() != messageObject.getId() || Instance.currentMessageObject.getDialogId() != messageObject.getDialogId()) ? false : true;
    }

    public static boolean isShowingImage(TLRPC.FileLocation fileLocation) {
        if (Instance != null && Instance.isVisible && !Instance.disableShowCheck && fileLocation != null) {
            if (Instance.currentFileLocation != null && fileLocation.local_id == Instance.currentFileLocation.location.local_id && fileLocation.volume_id == Instance.currentFileLocation.location.volume_id && fileLocation.dc_id == Instance.currentFileLocation.dc_id) {
                return true;
            }
            if (Instance.currentFileLocationVideo != null && fileLocation.local_id == Instance.currentFileLocationVideo.location.local_id && fileLocation.volume_id == Instance.currentFileLocationVideo.location.volume_id && fileLocation.dc_id == Instance.currentFileLocationVideo.dc_id) {
                return true;
            }
        }
        return false;
    }

    public static boolean isShowingImage(TLRPC.BotInlineResult botInlineResult) {
        return (Instance == null || !Instance.isVisible || Instance.disableShowCheck || botInlineResult == null || Instance.currentBotInlineResult == null || botInlineResult.f1569id != Instance.currentBotInlineResult.f1569id) ? false : true;
    }

    public static boolean isShowingImage(String str) {
        return (Instance == null || !Instance.isVisible || Instance.disableShowCheck || str == null || !str.equals(Instance.currentPathObject)) ? false : true;
    }

    public void setParentChatActivity(ChatActivity chatActivity) {
        this.parentChatActivity = chatActivity;
    }

    public void setMaxSelectedPhotos(int i, boolean z) {
        this.maxSelectedPhotos = i;
        this.allowOrder = z;
    }

    public void checkCurrentImageVisibility() {
        PlaceProviderObject placeProviderObject = this.currentPlaceObject;
        if (placeProviderObject != null) {
            placeProviderObject.imageReceiver.setVisible(true, true);
        }
        PhotoViewerProvider photoViewerProvider = this.placeProvider;
        PlaceProviderObject placeForPhoto = photoViewerProvider == null ? null : photoViewerProvider.getPlaceForPhoto(this.currentMessageObject, getFileLocation(this.currentFileLocation), this.currentIndex, false, false);
        this.currentPlaceObject = placeForPhoto;
        if (placeForPhoto != null) {
            placeForPhoto.imageReceiver.setVisible(false, true);
        }
    }

    public boolean openPhoto(MessageObject messageObject, ChatActivity chatActivity, long j, long j2, long j3, PhotoViewerProvider photoViewerProvider) {
        return openPhoto(messageObject, null, null, null, null, null, null, 0, photoViewerProvider, chatActivity, j, j2, j3, true, null, null);
    }

    public boolean openPhoto(MessageObject messageObject, int i, ChatActivity chatActivity, long j, long j2, long j3, PhotoViewerProvider photoViewerProvider) {
        return openPhoto(messageObject, null, null, null, null, null, null, 0, photoViewerProvider, chatActivity, j, j2, j3, true, null, Integer.valueOf(i));
    }

    public boolean openPhoto(MessageObject messageObject, long j, long j2, long j3, PhotoViewerProvider photoViewerProvider, boolean z) {
        return openPhoto(messageObject, null, null, null, null, null, null, 0, photoViewerProvider, null, j, j2, j3, z, null, null);
    }

    public boolean openPhoto(TLRPC.FileLocation fileLocation, PhotoViewerProvider photoViewerProvider) {
        return openPhoto(null, fileLocation, null, null, null, null, null, 0, photoViewerProvider, null, 0L, 0L, 0L, true, null, null);
    }

    public boolean openPhotoWithVideo(TLRPC.FileLocation fileLocation, ImageLocation imageLocation, PhotoViewerProvider photoViewerProvider) {
        return openPhoto(null, fileLocation, null, imageLocation, null, null, null, 0, photoViewerProvider, null, 0L, 0L, 0L, true, null, null);
    }

    public boolean openPhoto(TLRPC.FileLocation fileLocation, ImageLocation imageLocation, PhotoViewerProvider photoViewerProvider) {
        return openPhoto(null, fileLocation, imageLocation, null, null, null, null, 0, photoViewerProvider, null, 0L, 0L, 0L, true, null, null);
    }

    public boolean openPhoto(ArrayList arrayList, int i, long j, long j2, long j3, PhotoViewerProvider photoViewerProvider) {
        return openPhoto((MessageObject) arrayList.get(i), null, null, null, arrayList, null, null, i, photoViewerProvider, null, j, j2, j3, true, null, null);
    }

    public boolean openPhoto(ArrayList arrayList, int i, PhotoViewerProvider photoViewerProvider) {
        return openPhoto(null, null, null, null, null, arrayList, null, i, photoViewerProvider, null, 0L, 0L, 0L, true, null, null);
    }

    public boolean openPhoto(int i, PageBlocksAdapter pageBlocksAdapter, PhotoViewerProvider photoViewerProvider) {
        return openPhoto(null, null, null, null, null, null, null, i, photoViewerProvider, null, 0L, 0L, 0L, true, pageBlocksAdapter, null);
    }

    public boolean openPhotoForSelect(ArrayList arrayList, int i, int i2, boolean z, PhotoViewerProvider photoViewerProvider, ChatActivity chatActivity) {
        return openPhotoForSelect(null, null, arrayList, i, i2, z, photoViewerProvider, chatActivity);
    }

    public boolean openPhotoForSelect(TLRPC.FileLocation fileLocation, ImageLocation imageLocation, ArrayList arrayList, int i, int i2, boolean z, PhotoViewerProvider photoViewerProvider, ChatActivity chatActivity) throws Resources.NotFoundException, NumberFormatException {
        this.isDocumentsPicker = z;
        ChatActivityEnterView.SendButton sendButton = this.pickerViewSendButton;
        if (sendButton != null) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) sendButton.getLayoutParams();
            if (i2 == 4 || i2 == 5) {
                this.pickerViewSendButton.setResourceId(C2369R.drawable.msg_input_send_mini);
                layoutParams.bottomMargin = AndroidUtilities.m1146dp(7.33f);
            } else if (i2 == 1 || i2 == 3 || i2 == 10 || i2 == 11) {
                this.pickerViewSendButton.setResourceId(C2369R.drawable.floating_check);
                this.pickerViewSendButton.setPadding(0, AndroidUtilities.m1146dp(1.0f), 0, 0);
                layoutParams.bottomMargin = AndroidUtilities.m1146dp(7.33f);
            } else {
                this.pickerViewSendButton.setResourceId(C2369R.drawable.msg_input_send_mini);
                layoutParams.bottomMargin = AndroidUtilities.m1146dp(2.33f);
            }
            this.pickerViewSendButton.setLayoutParams(layoutParams);
        }
        if (i2 != 11 && this.stickerMakerView != null) {
            this.stickerEmpty = false;
            ImageView imageView = this.tuneItem;
            if (imageView != null) {
                imageView.setAlpha(1.0f);
            }
            BlurButton blurButton = this.outlineBtn;
            if (blurButton != null) {
                blurButton.setActive(false, false);
            }
            this.stickerMakerView.clean();
            ArrayList arrayList2 = this.selectedEmojis;
            if (arrayList2 != null) {
                arrayList2.clear();
            }
        }
        if (this.sendPhotoType != 1 && i2 == 1 && this.isVisible) {
            this.sendPhotoType = i2;
            this.doneButtonPressed = false;
            this.actionBarContainer.setTitle("");
            this.actionBarContainer.setSubtitle("", false);
            this.placeProvider = photoViewerProvider;
            this.mergeDialogId = 0L;
            this.currentDialogId = 0L;
            this.selectedPhotosAdapter.notifyDataSetChanged();
            this.pageBlocksAdapter = null;
            if (this.velocityTracker == null) {
                this.velocityTracker = VelocityTracker.obtain();
            }
            this.isVisible = true;
            this.isVisibleOrAnimating = true;
            togglePhotosListView(false, false);
            this.openedFullScreenVideo = false;
            createCropView();
            toggleActionBar(false, false);
            this.seekToProgressPending2 = 0.0f;
            this.skipFirstBufferingProgress = false;
            this.playerInjected = false;
            makeFocusable();
            this.backgroundDrawable.setAlpha(255);
            this.containerView.setAlpha(1.0f);
            onPhotoShow(null, fileLocation, imageLocation, null, null, null, arrayList, i, null);
            initCropView();
            setCropBitmap();
            return true;
        }
        this.sendPhotoType = i2;
        if (i2 == 11) {
            this.navigationBar.setBackgroundColor(-16777216);
        }
        return openPhoto(null, fileLocation, imageLocation, null, null, null, arrayList, i, photoViewerProvider, chatActivity, 0L, 0L, 0L, true, null, null);
    }

    public void setTitle(CharSequence charSequence) {
        PhotoViewerActionBarContainer photoViewerActionBarContainer = this.actionBarContainer;
        this.customTitle = charSequence;
        photoViewerActionBarContainer.setTitle(charSequence);
        toggleActionBar(true, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openCurrentPhotoInPaintModeForSelect() {
        final File file;
        final MessageObject messageObject;
        final boolean zIsVideo;
        final boolean z;
        final boolean z2;
        if (canSendMediaToParentChatActivity()) {
            MessageObject messageObject2 = this.currentMessageObject;
            File pathToMessage = null;
            if (messageObject2 != null) {
                boolean z3 = messageObject2.canEditMedia() && !this.currentMessageObject.isDocument();
                boolean z4 = z3 && this.currentMessageObject.isOutOwner();
                this.currentMessageObject.isVideo();
                if (!TextUtils.isEmpty(this.currentMessageObject.messageOwner.attachPath)) {
                    File file2 = new File(this.currentMessageObject.messageOwner.attachPath);
                    if (file2.exists()) {
                        pathToMessage = file2;
                    }
                }
                if (pathToMessage == null) {
                    pathToMessage = FileLoader.getInstance(this.currentAccount).getPathToMessage(this.currentMessageObject.messageOwner);
                }
                messageObject = messageObject2;
                file = pathToMessage;
                z = z3;
                z2 = z4;
                zIsVideo = this.currentMessageObject.isVideo();
            } else {
                if (this.currentFileLocationVideo != null) {
                    file = FileLoader.getInstance(this.currentAccount).getPathToAttach(getFileLocation(this.currentFileLocationVideo), getFileLocationExt(this.currentFileLocationVideo), !(Objects.equals(getFileLocationExt(this.currentFileLocationVideo), "mp4") || this.avatarsDialogId == 0) || this.isEvent);
                    messageObject = null;
                } else {
                    PageBlocksAdapter pageBlocksAdapter = this.pageBlocksAdapter;
                    if (pageBlocksAdapter != null) {
                        file = pageBlocksAdapter.getFile(this.currentIndex);
                        messageObject = null;
                        zIsVideo = this.pageBlocksAdapter.isVideo(this.currentIndex);
                        z = false;
                        z2 = false;
                    } else {
                        file = null;
                        messageObject = null;
                    }
                }
                zIsVideo = false;
                z = false;
                z2 = false;
            }
            if (file != null && file.exists()) {
                this.savedState = new SavedState(this.currentIndex, new ArrayList(this.imagesArr), this.placeProvider);
                toggleActionBar(false, true, new ActionBarToggleParams().enableStatusBarAnimation(false));
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda157
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException, NumberFormatException {
                        this.f$0.lambda$openCurrentPhotoInPaintModeForSelect$138(file, zIsVideo, messageObject, z, z2);
                    }
                }, r0.animationDuration);
                return;
            }
            showDownloadAlert();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openCurrentPhotoInPaintModeForSelect$138(File file, boolean z, final MessageObject messageObject, final boolean z2, final boolean z3) throws Resources.NotFoundException, NumberFormatException {
        Pair<Integer, Integer> imageOrientation = AndroidUtilities.getImageOrientation(file);
        int i = this.lastImageId;
        this.lastImageId = i - 1;
        final MediaController.PhotoEntry orientation = new MediaController.PhotoEntry(0, i, 0L, file.getAbsolutePath(), z ? 0 : ((Integer) imageOrientation.first).intValue(), z, 0, 0, 0L).setOrientation(imageOrientation);
        this.sendPhotoType = 2;
        this.doneButtonPressed = false;
        final PhotoViewerProvider photoViewerProvider = this.placeProvider;
        this.placeProvider = new EmptyPhotoViewerProvider() { // from class: org.telegram.ui.PhotoViewer.76
            private final ImageReceiver.BitmapHolder thumbHolder;

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public boolean canCaptureMorePhotos() {
                return false;
            }

            {
                this.thumbHolder = PhotoViewer.this.centerImage.getBitmapSafe();
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject2, TLRPC.FileLocation fileLocation, int i2, boolean z4, boolean z5) {
                PhotoViewerProvider photoViewerProvider2 = photoViewerProvider;
                if (photoViewerProvider2 != null) {
                    return photoViewerProvider2.getPlaceForPhoto(messageObject, null, 0, z4, false);
                }
                return null;
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public ImageReceiver.BitmapHolder getThumbForPhoto(MessageObject messageObject2, TLRPC.FileLocation fileLocation, int i2) {
                return this.thumbHolder;
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public void sendButtonPressed(int i2, VideoEditedInfo videoEditedInfo, boolean z4, int i3, int i4, boolean z5) {
                sendMedia(videoEditedInfo, z4, i3, 0, false, z5);
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public void replaceButtonPressed(int i2, VideoEditedInfo videoEditedInfo) {
                MediaController.PhotoEntry photoEntry = orientation;
                if (photoEntry.isCropped || photoEntry.isPainted || photoEntry.isFiltered || videoEditedInfo != null || !TextUtils.isEmpty(photoEntry.caption)) {
                    sendMedia(videoEditedInfo, false, 0, 0, true, false);
                }
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public boolean canEdit(int i2) {
                return photoViewerProvider != null && z2;
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public boolean canReplace(int i2) {
                return photoViewerProvider != null && z3;
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public MessageObject getEditingMessageObject() {
                return messageObject;
            }

            private void sendMedia(VideoEditedInfo videoEditedInfo, boolean z4, int i2, int i3, boolean z5, boolean z6) {
                if (PhotoViewer.this.parentChatActivity != null) {
                    MessageObject messageObject2 = z5 ? messageObject : null;
                    if (messageObject2 != null && !TextUtils.isEmpty(orientation.caption)) {
                        MediaController.PhotoEntry photoEntry = orientation;
                        messageObject2.editingMessage = photoEntry.caption;
                        messageObject2.editingMessageEntities = photoEntry.entities;
                    }
                    MediaController.PhotoEntry photoEntry2 = orientation;
                    if (photoEntry2.isVideo) {
                        if (videoEditedInfo != null) {
                            AccountInstance accountInstance = PhotoViewer.this.parentChatActivity.getAccountInstance();
                            String str = orientation.path;
                            long dialogId = PhotoViewer.this.parentChatActivity.getDialogId();
                            MessageObject replyMessage = PhotoViewer.this.parentChatActivity.getReplyMessage();
                            MessageObject threadMessage = PhotoViewer.this.parentChatActivity.getThreadMessage();
                            ChatActivity.ReplyQuote replyQuote = PhotoViewer.this.parentChatActivity.getReplyQuote();
                            MediaController.PhotoEntry photoEntry3 = orientation;
                            SendMessagesHelper.prepareSendingVideo(accountInstance, str, videoEditedInfo, null, null, dialogId, replyMessage, threadMessage, null, replyQuote, photoEntry3.entities, photoEntry3.ttl, messageObject2, z4, i2, i3, z6, photoEntry3.hasSpoiler, photoEntry3.caption, PhotoViewer.this.parentChatActivity.quickReplyShortcut, PhotoViewer.this.parentChatActivity.getQuickReplyId(), 0L, 0L, PhotoViewer.this.parentChatActivity.getSendMonoForumPeerId(), PhotoViewer.this.parentChatActivity.getSendMessageSuggestionParams());
                            return;
                        }
                        AccountInstance accountInstance2 = PhotoViewer.this.parentChatActivity.getAccountInstance();
                        String str2 = orientation.path;
                        long dialogId2 = PhotoViewer.this.parentChatActivity.getDialogId();
                        MessageObject replyMessage2 = PhotoViewer.this.parentChatActivity.getReplyMessage();
                        MessageObject threadMessage2 = PhotoViewer.this.parentChatActivity.getThreadMessage();
                        ChatActivity.ReplyQuote replyQuote2 = PhotoViewer.this.parentChatActivity.getReplyQuote();
                        MediaController.PhotoEntry photoEntry4 = orientation;
                        SendMessagesHelper.prepareSendingVideo(accountInstance2, str2, null, null, null, dialogId2, replyMessage2, threadMessage2, null, replyQuote2, photoEntry4.entities, photoEntry4.ttl, messageObject2, z4, i2, i3, z6, photoEntry4.hasSpoiler, photoEntry4.caption, PhotoViewer.this.parentChatActivity.quickReplyShortcut, PhotoViewer.this.parentChatActivity.getQuickReplyId(), 0L, 0L, PhotoViewer.this.parentChatActivity.getSendMonoForumPeerId(), PhotoViewer.this.parentChatActivity.getSendMessageSuggestionParams());
                        return;
                    }
                    if (photoEntry2.imagePath != null) {
                        AccountInstance accountInstance3 = PhotoViewer.this.parentChatActivity.getAccountInstance();
                        MediaController.PhotoEntry photoEntry5 = orientation;
                        String str3 = photoEntry5.imagePath;
                        String str4 = photoEntry5.thumbPath;
                        long dialogId3 = PhotoViewer.this.parentChatActivity.getDialogId();
                        MessageObject replyMessage3 = PhotoViewer.this.parentChatActivity.getReplyMessage();
                        MessageObject threadMessage3 = PhotoViewer.this.parentChatActivity.getThreadMessage();
                        ChatActivity.ReplyQuote replyQuote3 = PhotoViewer.this.parentChatActivity.getReplyQuote();
                        MediaController.PhotoEntry photoEntry6 = orientation;
                        SendMessagesHelper.prepareSendingPhoto(accountInstance3, str3, str4, null, dialogId3, replyMessage3, threadMessage3, null, replyQuote3, photoEntry6.entities, photoEntry6.stickers, null, photoEntry6.ttl, messageObject2, videoEditedInfo, z4, i2, i3, 0, z6, photoEntry6.caption, PhotoViewer.this.parentChatActivity.quickReplyShortcut, PhotoViewer.this.parentChatActivity.getQuickReplyId(), 0L, 0L, PhotoViewer.this.parentChatActivity.getSendMonoForumPeerId(), PhotoViewer.this.parentChatActivity.getSendMessageSuggestionParams());
                        return;
                    }
                    if (photoEntry2.path != null) {
                        AccountInstance accountInstance4 = PhotoViewer.this.parentChatActivity.getAccountInstance();
                        MediaController.PhotoEntry photoEntry7 = orientation;
                        String str5 = photoEntry7.path;
                        String str6 = photoEntry7.thumbPath;
                        long dialogId4 = PhotoViewer.this.parentChatActivity.getDialogId();
                        MessageObject replyMessage4 = PhotoViewer.this.parentChatActivity.getReplyMessage();
                        MessageObject threadMessage4 = PhotoViewer.this.parentChatActivity.getThreadMessage();
                        ChatActivity.ReplyQuote replyQuote4 = PhotoViewer.this.parentChatActivity.getReplyQuote();
                        MediaController.PhotoEntry photoEntry8 = orientation;
                        SendMessagesHelper.prepareSendingPhoto(accountInstance4, str5, str6, null, dialogId4, replyMessage4, threadMessage4, null, replyQuote4, photoEntry8.entities, photoEntry8.stickers, null, photoEntry8.ttl, messageObject2, videoEditedInfo, z4, i2, i3, 0, z6, photoEntry8.caption, PhotoViewer.this.parentChatActivity.quickReplyShortcut, PhotoViewer.this.parentChatActivity.getQuickReplyId(), 0L, 0L, PhotoViewer.this.parentChatActivity.getSendMonoForumPeerId(), PhotoViewer.this.parentChatActivity.getSendMessageSuggestionParams());
                    }
                }
            }
        };
        this.selectedPhotosAdapter.notifyDataSetChanged();
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        this.aboutToSwitchTo = 3;
        togglePhotosListView(false, false);
        toggleActionBar(true, false);
        ChatActivity chatActivity = this.parentChatActivity;
        if (chatActivity != null && chatActivity.getChatActivityEnterView() != null && this.parentChatActivity.isKeyboardVisible()) {
            this.parentChatActivity.getChatActivityEnterView().closeKeyboard();
        } else {
            makeFocusable();
        }
        this.backgroundDrawable.setAlpha(255);
        this.containerView.setAlpha(1.0f);
        onPhotoShow(null, null, null, null, null, null, Collections.singletonList(orientation), 0, null);
        this.pickerView.setTranslationY(AndroidUtilities.m1146dp(this.isCurrentVideo ? 154.0f : 96.0f));
        this.pickerViewSendButton.setTranslationY(AndroidUtilities.m1146dp(this.isCurrentVideo ? 154.0f : 96.0f));
        this.actionBar.setTranslationY(-r1.getHeight());
        this.captionTextViewSwitcher.setTranslationY(AndroidUtilities.m1146dp(this.isCurrentVideo ? 154.0f : 96.0f));
        createPaintView();
        switchToPaintMode();
        this.aboutToSwitchTo = 0;
    }

    private boolean checkAnimation() {
        if (this.animationInProgress != 0) {
            invalidateBlur();
            if (Math.abs(this.transitionAnimationStartTime - System.currentTimeMillis()) >= 500) {
                Runnable runnable = this.animationEndRunnable;
                if (runnable != null) {
                    runnable.run();
                    this.animationEndRunnable = null;
                }
                this.animationInProgress = 0;
            }
        }
        return this.animationInProgress != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCropBitmap() {
        VideoEditTextureView videoEditTextureView;
        if (this.cropInitied || this.sendPhotoType != 1) {
            return;
        }
        if (!this.isCurrentVideo || ((videoEditTextureView = (VideoEditTextureView) this.videoTextureView) != null && videoEditTextureView.getVideoWidth() > 0 && videoEditTextureView.getVideoHeight() > 0)) {
            this.cropInitied = true;
            Bitmap bitmap = this.centerImage.getBitmap();
            int orientation = this.centerImage.getOrientation();
            if (bitmap == null) {
                bitmap = this.animatingImageView.getBitmap();
                orientation = this.animatingImageView.getOrientation();
            }
            Bitmap bitmap2 = bitmap;
            int i = orientation;
            if (bitmap2 == null && this.videoTextureView == null) {
                return;
            }
            this.photoCropView.setBitmap(bitmap2, i, false, false, this.paintingOverlay, this.cropTransform, this.isCurrentVideo ? (VideoEditTextureView) this.videoTextureView : null, this.editState.cropState);
        }
    }

    private void initCropView() {
        PhotoCropView photoCropView = this.photoCropView;
        if (photoCropView == null) {
            return;
        }
        photoCropView.setBitmap(null, 0, false, false, null, null, null, null);
        if (this.sendPhotoType != 1) {
            return;
        }
        this.photoCropView.onAppear();
        this.photoCropView.setVisibility(0);
        this.photoCropView.setAlpha(1.0f);
        this.photoCropView.onAppeared();
        this.padImageForHorizontalInsets = true;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00a2 A[Catch: Exception -> 0x0066, TryCatch #0 {Exception -> 0x0066, blocks: (B:22:0x0052, B:24:0x005f, B:43:0x00a2, B:45:0x00b1, B:29:0x0069, B:31:0x0071, B:34:0x0082, B:36:0x0092, B:38:0x0096, B:41:0x009c, B:44:0x00a6), top: B:178:0x0052 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean openPhoto(org.telegram.messenger.MessageObject r18, org.telegram.tgnet.TLRPC.FileLocation r19, org.telegram.messenger.ImageLocation r20, org.telegram.messenger.ImageLocation r21, java.util.ArrayList r22, java.util.ArrayList r23, java.util.ArrayList r24, int r25, final org.telegram.ui.PhotoViewer.PhotoViewerProvider r26, org.telegram.p023ui.ChatActivity r27, long r28, long r30, long r32, boolean r34, org.telegram.ui.PhotoViewer.PageBlocksAdapter r35, final java.lang.Integer r36) throws android.content.res.Resources.NotFoundException, java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 1088
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.openPhoto(org.telegram.messenger.MessageObject, org.telegram.tgnet.TLRPC$FileLocation, org.telegram.messenger.ImageLocation, org.telegram.messenger.ImageLocation, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList, int, org.telegram.ui.PhotoViewer$PhotoViewerProvider, org.telegram.ui.ChatActivity, long, long, long, boolean, org.telegram.ui.PhotoViewer$PageBlocksAdapter, java.lang.Integer):boolean");
    }

    /* renamed from: org.telegram.ui.PhotoViewer$77 */
    /* loaded from: classes5.dex */
    class ViewTreeObserverOnPreDrawListenerC585277 implements ViewTreeObserver.OnPreDrawListener {
        final /* synthetic */ ClippingImageView[] val$animatingImageViews;
        final /* synthetic */ Integer val$embedSeekTime;
        final /* synthetic */ boolean val$isDeleted;
        final /* synthetic */ ViewGroup.LayoutParams val$layoutParams;
        final /* synthetic */ float val$left;
        final /* synthetic */ PlaceProviderObject val$object;
        final /* synthetic */ ArrayList val$photos;
        final /* synthetic */ PhotoViewerProvider val$provider;
        final /* synthetic */ float val$top;

        ViewTreeObserverOnPreDrawListenerC585277(ClippingImageView[] clippingImageViewArr, ViewGroup.LayoutParams layoutParams, float f, PlaceProviderObject placeProviderObject, float f2, boolean z, PhotoViewerProvider photoViewerProvider, ArrayList arrayList, Integer num) {
            this.val$animatingImageViews = clippingImageViewArr;
            this.val$layoutParams = layoutParams;
            this.val$left = f;
            this.val$object = placeProviderObject;
            this.val$top = f2;
            this.val$isDeleted = z;
            this.val$provider = photoViewerProvider;
            this.val$photos = arrayList;
            this.val$embedSeekTime = num;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            float fMax;
            float f;
            float measuredWidth;
            ClippingImageView[] clippingImageViewArr;
            ClippingImageView[] clippingImageViewArr2 = this.val$animatingImageViews;
            if (clippingImageViewArr2.length > 1) {
                clippingImageViewArr2[1].setAlpha(1.0f);
                this.val$animatingImageViews[1].setAdditionalTranslationX(-PhotoViewer.this.getLeftInset());
            }
            ClippingImageView clippingImageView = this.val$animatingImageViews[0];
            clippingImageView.setTranslationX(clippingImageView.getTranslationX() + PhotoViewer.this.getLeftInset());
            PhotoViewer.this.windowView.getViewTreeObserver().removeOnPreDrawListener(this);
            if (PhotoViewer.this.sendPhotoType == 1) {
                float f2 = PhotoViewer.this.isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0;
                float measuredHeight = (PhotoViewer.this.photoCropView.getMeasuredHeight() - AndroidUtilities.m1146dp(64.0f)) - f2;
                float measuredWidth2 = PhotoViewer.this.photoCropView.getMeasuredWidth() / 2.0f;
                float f3 = f2 + (measuredHeight / 2.0f);
                float fMin = (Math.min(PhotoViewer.this.photoCropView.getMeasuredWidth(), measuredHeight) - (AndroidUtilities.m1146dp(16.0f) * 2)) / 2.0f;
                float f4 = f3 - fMin;
                ViewGroup.LayoutParams layoutParams = this.val$layoutParams;
                float f5 = (f3 + fMin) - f4;
                fMax = Math.max(((measuredWidth2 + fMin) - (measuredWidth2 - fMin)) / layoutParams.width, f5 / layoutParams.height);
                f = f4 + ((f5 - (this.val$layoutParams.height * fMax)) / 2.0f);
                measuredWidth = ((((PhotoViewer.this.windowView.getMeasuredWidth() - PhotoViewer.this.getLeftInset()) - PhotoViewer.this.getRightInset()) - (this.val$layoutParams.width * fMax)) / 2.0f) + PhotoViewer.this.getLeftInset();
            } else {
                float fMin2 = Math.min(PhotoViewer.this.windowView.getMeasuredWidth() / this.val$layoutParams.width, (AndroidUtilities.displaySize.y + (PhotoViewer.this.isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0)) / this.val$layoutParams.height);
                if (PhotoViewer.this.sendPhotoType == 11) {
                    fMin2 *= PhotoViewer.this.scale1();
                }
                fMax = fMin2;
                f = ((AndroidUtilities.displaySize.y + (PhotoViewer.this.isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0)) - (this.val$layoutParams.height * fMax)) / 2.0f;
                measuredWidth = (PhotoViewer.this.windowView.getMeasuredWidth() - (this.val$layoutParams.width * fMax)) / 2.0f;
                PhotoViewer.this.rotate = 0.0f;
                PhotoViewer.this.animateToRotate = 0.0f;
            }
            int iAbs = (int) Math.abs(this.val$left - this.val$object.imageReceiver.getImageX());
            int iAbs2 = (int) Math.abs(this.val$top - this.val$object.imageReceiver.getImageY());
            if (this.val$object.imageReceiver.isAspectFit()) {
                iAbs = 0;
            }
            int[] iArr = new int[2];
            this.val$object.parentView.getLocationInWindow(iArr);
            int i = iArr[1];
            int i2 = this.val$object.viewY;
            float f6 = this.val$top;
            int i3 = (int) ((i - (i2 + f6)) + r14.clipTopAddition);
            if (i3 < 0) {
                i3 = 0;
            }
            int height = (int) ((((i2 + f6) + this.val$layoutParams.height) - (i + r14.parentView.getHeight())) + this.val$object.clipBottomAddition);
            if (height < 0) {
                height = 0;
            }
            int iMax = Math.max(i3, iAbs2);
            int iMax2 = Math.max(height, iAbs2);
            PhotoViewer.this.animationValues[0][0] = PhotoViewer.this.animatingImageView.getScaleX();
            PhotoViewer.this.animationValues[0][1] = PhotoViewer.this.animatingImageView.getScaleY();
            PhotoViewer.this.animationValues[0][2] = PhotoViewer.this.animatingImageView.getTranslationX();
            PhotoViewer.this.animationValues[0][3] = PhotoViewer.this.animatingImageView.getTranslationY();
            float f7 = iAbs;
            PhotoViewer.this.animationValues[0][4] = this.val$object.scale * f7;
            PhotoViewer.this.animationValues[0][5] = iMax * this.val$object.scale;
            PhotoViewer.this.animationValues[0][6] = iMax2 * this.val$object.scale;
            int[] radius = PhotoViewer.this.animatingImageView.getRadius();
            for (int i4 = 0; i4 < 4; i4++) {
                PhotoViewer.this.animationValues[0][i4 + 7] = radius != null ? radius[i4] : 0.0f;
            }
            PhotoViewer.this.animationValues[0][11] = iAbs2 * this.val$object.scale;
            PhotoViewer.this.animationValues[0][12] = f7 * this.val$object.scale;
            PhotoViewer.this.animationValues[0][13] = this.val$isDeleted ? 0.7f : 1.0f;
            PhotoViewer.this.animationValues[1][0] = fMax;
            PhotoViewer.this.animationValues[1][1] = fMax;
            PhotoViewer.this.animationValues[1][2] = measuredWidth;
            PhotoViewer.this.animationValues[1][3] = f;
            PhotoViewer.this.animationValues[1][4] = 0.0f;
            PhotoViewer.this.animationValues[1][5] = 0.0f;
            PhotoViewer.this.animationValues[1][6] = 0.0f;
            PhotoViewer.this.animationValues[1][7] = 0.0f;
            PhotoViewer.this.animationValues[1][8] = 0.0f;
            PhotoViewer.this.animationValues[1][9] = 0.0f;
            PhotoViewer.this.animationValues[1][10] = 0.0f;
            PhotoViewer.this.animationValues[1][11] = 0.0f;
            PhotoViewer.this.animationValues[1][12] = 0.0f;
            PhotoViewer.this.animationValues[1][13] = 1.0f;
            int i5 = 0;
            while (true) {
                ClippingImageView[] clippingImageViewArr3 = this.val$animatingImageViews;
                if (i5 >= clippingImageViewArr3.length) {
                    break;
                }
                clippingImageViewArr3[i5].setAnimationProgress(0.0f);
                i5++;
            }
            PhotoViewer.this.backgroundDrawable.setAlpha(0);
            PhotoViewer.this.containerView.setAlpha(0.0f);
            PhotoViewer.this.navigationBar.setAlpha(0.0f);
            PhotoViewerProvider photoViewerProvider = this.val$provider;
            if (photoViewerProvider != null) {
                photoViewerProvider.onPreOpen();
            }
            PhotoViewer photoViewer = PhotoViewer.this;
            final ClippingImageView[] clippingImageViewArr4 = this.val$animatingImageViews;
            final ArrayList arrayList = this.val$photos;
            final Integer num = this.val$embedSeekTime;
            final PhotoViewerProvider photoViewerProvider2 = this.val$provider;
            photoViewer.animationEndRunnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer$77$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    this.f$0.lambda$onPreDraw$0(clippingImageViewArr4, arrayList, num, photoViewerProvider2);
                }
            };
            if (!PhotoViewer.this.openedFullScreenVideo) {
                final AnimatorSet animatorSet = new AnimatorSet();
                int i6 = PhotoViewer.this.sendPhotoType != 1 ? 2 : 3;
                ClippingImageView[] clippingImageViewArr5 = this.val$animatingImageViews;
                ArrayList arrayList2 = new ArrayList(i6 + clippingImageViewArr5.length + (clippingImageViewArr5.length > 1 ? 1 : 0));
                int i7 = 0;
                while (true) {
                    clippingImageViewArr = this.val$animatingImageViews;
                    if (i7 >= clippingImageViewArr.length) {
                        break;
                    }
                    ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(clippingImageViewArr[i7], (Property<ClippingImageView, Float>) AnimationProperties.CLIPPING_IMAGE_VIEW_PROGRESS, 0.0f, 1.0f);
                    if (i7 == 0) {
                        objectAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$77$$ExternalSyntheticLambda1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                this.f$0.lambda$onPreDraw$1(valueAnimator);
                            }
                        });
                    }
                    arrayList2.add(objectAnimatorOfFloat);
                    i7++;
                }
                if (clippingImageViewArr.length > 1) {
                    arrayList2.add(ObjectAnimator.ofFloat(PhotoViewer.this.animatingImageView, (Property<ClippingImageView, Float>) View.ALPHA, 0.0f, 1.0f));
                }
                arrayList2.add(ObjectAnimator.ofInt(PhotoViewer.this.backgroundDrawable, (Property<BackgroundDrawable, Integer>) AnimationProperties.COLOR_DRAWABLE_ALPHA, 0, 255));
                FrameLayoutDrawer frameLayoutDrawer = PhotoViewer.this.containerView;
                Property property = View.ALPHA;
                arrayList2.add(ObjectAnimator.ofFloat(frameLayoutDrawer, (Property<FrameLayoutDrawer, Float>) property, 0.0f, 1.0f));
                arrayList2.add(ObjectAnimator.ofFloat(PhotoViewer.this.navigationBar, (Property<View, Float>) property, 0.0f, 1.0f));
                if (PhotoViewer.this.sendPhotoType == 1) {
                    arrayList2.add(ObjectAnimator.ofFloat(PhotoViewer.this.photoCropView, (Property<PhotoCropView, Float>) property, 0.0f, 1.0f));
                }
                animatorSet.playTogether(arrayList2);
                animatorSet.setDuration(200L);
                animatorSet.addListener(new AnonymousClass1());
                PhotoViewer.this.containerView.setLayerType(2, null);
                PhotoViewer.this.setCaptionHwLayerEnabled(false);
                PhotoViewer.this.transitionAnimationStartTime = System.currentTimeMillis();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$77$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onPreDraw$2(animatorSet);
                    }
                });
            } else {
                if (PhotoViewer.this.animationEndRunnable != null) {
                    PhotoViewer.this.animationEndRunnable.run();
                    PhotoViewer.this.animationEndRunnable = null;
                }
                PhotoViewer.this.containerView.setAlpha(1.0f);
                PhotoViewer.this.backgroundDrawable.setAlpha(255);
                int i8 = 0;
                while (true) {
                    ClippingImageView[] clippingImageViewArr6 = this.val$animatingImageViews;
                    if (i8 >= clippingImageViewArr6.length) {
                        break;
                    }
                    clippingImageViewArr6[i8].setAnimationProgress(1.0f);
                    i8++;
                }
                if (PhotoViewer.this.sendPhotoType == 1) {
                    PhotoViewer.this.photoCropView.setAlpha(1.0f);
                }
            }
            BackgroundDrawable backgroundDrawable = PhotoViewer.this.backgroundDrawable;
            final PlaceProviderObject placeProviderObject = this.val$object;
            final boolean z = this.val$isDeleted;
            backgroundDrawable.drawRunnable = new Runnable() { // from class: org.telegram.ui.PhotoViewer$77$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onPreDraw$3(placeProviderObject, z);
                }
            };
            if (PhotoViewer.this.parentChatActivity != null && PhotoViewer.this.parentChatActivity.getFragmentView() != null) {
                UndoView undoView = PhotoViewer.this.parentChatActivity.getUndoView();
                if (undoView != null) {
                    undoView.hide(false, 1);
                }
                PhotoViewer.this.parentChatActivity.getFragmentView().invalidate();
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreDraw$0(ClippingImageView[] clippingImageViewArr, ArrayList arrayList, Integer num, PhotoViewerProvider photoViewerProvider) throws Resources.NotFoundException {
            PhotoViewer.this.animationEndRunnable = null;
            if (PhotoViewer.this.containerView != null) {
                PhotoViewer photoViewer = PhotoViewer.this;
                if (photoViewer.windowView == null) {
                    return;
                }
                photoViewer.containerView.setLayerType(0, null);
                PhotoViewer.this.animationInProgress = 0;
                PhotoViewer.this.invalidateBlur();
                PhotoViewer.this.transitionAnimationStartTime = 0L;
                PhotoViewer.this.leftCropState = null;
                PhotoViewer.this.leftCropTransform.setViewTransform(false);
                PhotoViewer.this.rightCropState = null;
                PhotoViewer.this.rightCropTransform.setViewTransform(false);
                PhotoViewer.this.setImages();
                PhotoViewer.this.setCropBitmap();
                PhotoViewer.this.containerView.invalidate();
                for (ClippingImageView clippingImageView : clippingImageViewArr) {
                    clippingImageView.setVisibility(8);
                }
                if (PhotoViewer.this.showAfterAnimation != null) {
                    PhotoViewer.this.showAfterAnimation.imageReceiver.setVisible(true, true);
                }
                if (PhotoViewer.this.hideAfterAnimation != null) {
                    PhotoViewer.this.hideAfterAnimation.imageReceiver.setVisible(false, true);
                }
                if (arrayList != null && PhotoViewer.this.sendPhotoType != 3 && PhotoViewer.this.sendPhotoType != 1 && (PhotoViewer.this.placeProvider == null || !PhotoViewer.this.placeProvider.closeKeyboard())) {
                    PhotoViewer.this.makeFocusable();
                }
                if (PhotoViewer.this.videoPlayer != null && PhotoViewer.this.videoPlayer.isPlaying() && PhotoViewer.this.isCurrentVideo && !PhotoViewer.this.imagesArrLocals.isEmpty()) {
                    PhotoViewer photoViewer2 = PhotoViewer.this;
                    photoViewer2.seekAnimatedStickersTo(photoViewer2.videoPlayer.getCurrentPosition());
                    PhotoViewer.this.playOrStopAnimatedStickers(true);
                }
                if (PhotoViewer.this.isEmbedVideo) {
                    PhotoViewer.this.initEmbedVideo(num.intValue());
                }
                if (photoViewerProvider != null) {
                    photoViewerProvider.onOpen();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreDraw$1(ValueAnimator valueAnimator) {
            PhotoViewer.this.clippingImageProgress = 1.0f - ((Float) valueAnimator.getAnimatedValue()).floatValue();
            PhotoViewer.this.invalidateBlur();
        }

        /* renamed from: org.telegram.ui.PhotoViewer$77$1, reason: invalid class name */
        class AnonymousClass1 extends AnimatorListenerAdapter {
            AnonymousClass1() {
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$77$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onAnimationEnd$0();
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onAnimationEnd$0() {
                PhotoViewer.this.transitionNotificationLocker.unlock();
                if (PhotoViewer.this.animationEndRunnable != null) {
                    PhotoViewer.this.animationEndRunnable.run();
                    PhotoViewer.this.animationEndRunnable = null;
                }
                PhotoViewer.this.setCaptionHwLayerEnabled(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreDraw$2(AnimatorSet animatorSet) {
            PhotoViewer.this.transitionNotificationLocker.lock();
            animatorSet.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onPreDraw$3(PlaceProviderObject placeProviderObject, boolean z) {
            PhotoViewer.this.disableShowCheck = false;
            placeProviderObject.imageReceiver.setVisible(false, true);
            if (z) {
                PhotoViewer.this.animatingImageView.setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initEmbedVideo(int i) {
        if (this.isEmbedVideo) {
            PhotoViewerWebView photoViewerWebView = new PhotoViewerWebView(this, this.parentActivity, this.pipItem) { // from class: org.telegram.ui.PhotoViewer.79
                Rect rect = new Rect();

                @Override // org.telegram.p023ui.Components.PhotoViewerWebView
                protected void processTouch(MotionEvent motionEvent) {
                }

                @Override // org.telegram.p023ui.Components.PhotoViewerWebView
                protected void drawBlackBackground(Canvas canvas, int i2, int i3) {
                    Bitmap bitmap = PhotoViewer.this.centerImage.getBitmap();
                    if (bitmap != null) {
                        float fMin = Math.min(i2 / bitmap.getWidth(), i3 / bitmap.getHeight());
                        int width = (int) (bitmap.getWidth() * fMin);
                        int height = (int) (bitmap.getHeight() * fMin);
                        int i4 = (i3 - height) / 2;
                        int i5 = (i2 - width) / 2;
                        this.rect.set(i5, i4, width + i5, height + i4);
                        canvas.drawBitmap(bitmap, (Rect) null, this.rect, (Paint) null);
                    }
                }
            };
            this.photoViewerWebView = photoViewerWebView;
            photoViewerWebView.setMenuItem(this.menuItem);
            this.photoViewerWebView.init(i, MessageObject.getMedia(this.currentMessageObject.messageOwner).webpage);
            this.photoViewerWebView.setPlaybackSpeed(this.currentVideoSpeed);
            this.containerView.addView(this.photoViewerWebView, 0, LayoutHelper.createFrame(-1, -1.0f));
            if (this.photoViewerWebView.isControllable()) {
                setVideoPlayerControlVisible(true, true);
            }
            this.videoPlayerSeekbar.clearTimestamps();
            updateVideoPlayerTime();
            this.shouldSavePositionForCurrentVideo = null;
            this.shouldSavePositionForCurrentVideoShortTerm = null;
            this.lastSaveTime = 0L;
            this.seekToProgressPending = this.seekToProgressPending2;
            this.videoPlayerSeekbar.setProgress(0.0f);
            this.videoTimelineView.setProgress(0.0f);
            this.videoPlayerSeekbar.setBufferedProgress(0.0f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void makeFocusable() {
        WindowManager.LayoutParams layoutParams = this.windowLayoutParams;
        layoutParams.flags = -2147417856;
        layoutParams.softInputMode = 272;
        try {
            ((WindowManager) this.parentActivity.getSystemService("window")).updateViewLayout(this.windowView, this.windowLayoutParams);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        this.windowView.setFocusable(true);
        this.containerView.setFocusable(true);
    }

    private void requestAdjustToNothing() {
        this.windowLayoutParams.softInputMode = 48;
        try {
            ((WindowManager) this.parentActivity.getSystemService("window")).updateViewLayout(this.windowView, this.windowLayoutParams);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestAdjust() {
        this.windowLayoutParams.softInputMode = 272;
        try {
            ((WindowManager) this.parentActivity.getSystemService("window")).updateViewLayout(this.windowView, this.windowLayoutParams);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    public void injectVideoPlayerToMediaController() {
        if (this.videoPlayer.isPlaying()) {
            if (this.playerLooping) {
                this.videoPlayer.setLooping(false);
            }
            MediaController.getInstance().injectVideoPlayer(this.videoPlayer, this.currentMessageObject);
            this.videoPlayer = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01cf  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:166:0x02dc  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x02df  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x02e5  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x07c1  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x07d3  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x0261 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:338:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void closePhoto(boolean r31, boolean r32) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 2050
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.closePhoto(boolean, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closePhoto$139(MaskPaintView maskPaintView) {
        maskPaintView.shutdown();
        this.containerView.removeView(this.maskPaintView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closePhoto$140(AnimatedFileDrawable animatedFileDrawable, TLRPC.Document document, long j, PlaceProviderObject placeProviderObject) {
        ImageReceiver imageReceiver;
        if (animatedFileDrawable != null && document != null) {
            FileLog.m1157d("seeking from photo viewer to animation object");
            animatedFileDrawable.seekTo(j, !FileLoader.getInstance(this.currentAccount).isLoadingVideo(document, true), true);
        }
        if (placeProviderObject == null || (imageReceiver = placeProviderObject.imageReceiver) == null) {
            return;
        }
        imageReceiver.setAllowStartAnimation(true);
        placeProviderObject.imageReceiver.startAnimation();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closePhoto$141(ValueAnimator valueAnimator) {
        this.clippingImageProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateBlur();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closePhoto$142(ValueAnimator valueAnimator) {
        this.clippingImageProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closePhoto$143(PlaceProviderObject placeProviderObject) throws NumberFormatException {
        ArrayList arrayList;
        this.animationEndRunnable = null;
        int i = 0;
        this.containerView.setLayerType(0, null);
        this.animationInProgress = 0;
        invalidateBlur();
        onPhotoClosed(placeProviderObject);
        MediaController.getInstance().tryResumePausedAudio();
        if (!this.stickerEmpty || this.stickerEmptySent || (arrayList = this.imagesArrLocals) == null) {
            return;
        }
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (obj instanceof MediaController.PhotoEntry) {
                ((MediaController.PhotoEntry) obj).deleteAll();
            }
        }
    }

    /* renamed from: org.telegram.ui.PhotoViewer$80 */
    /* loaded from: classes5.dex */
    class C585680 extends AnimatorListenerAdapter {
        C585680() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$80$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onAnimationEnd$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onAnimationEnd$0() {
            if (PhotoViewer.this.animationEndRunnable != null) {
                PhotoViewer.this.animationEndRunnable.run();
                PhotoViewer.this.animationEndRunnable = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closePhoto$144(PlaceProviderObject placeProviderObject, AnimatorSet animatorSet) {
        if (placeProviderObject != null) {
            placeProviderObject.imageReceiver.setVisible(false, true);
        }
        if (!this.doneButtonPressed) {
            releasePlayer(true);
        }
        this.animationInProgress = 3;
        this.containerView.invalidate();
        this.transitionAnimationStartTime = System.currentTimeMillis();
        this.containerView.setLayerType(2, null);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closePhoto$145(ValueAnimator valueAnimator) {
        this.clippingImageProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closePhoto$146(PlaceProviderObject placeProviderObject) throws NumberFormatException {
        ArrayList arrayList;
        this.animationEndRunnable = null;
        FrameLayoutDrawer frameLayoutDrawer = this.containerView;
        if (frameLayoutDrawer == null) {
            return;
        }
        int i = 0;
        frameLayoutDrawer.setLayerType(0, null);
        this.animationInProgress = 0;
        onPhotoClosed(placeProviderObject);
        this.containerView.setScaleX(1.0f);
        this.containerView.setScaleY(1.0f);
        MediaController.getInstance().tryResumePausedAudio();
        if (!this.stickerEmpty || this.stickerEmptySent || (arrayList = this.imagesArrLocals) == null) {
            return;
        }
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (obj instanceof MediaController.PhotoEntry) {
                ((MediaController.PhotoEntry) obj).deleteAll();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closePhoto$147(AnimatorSet animatorSet, PlaceProviderObject placeProviderObject) {
        if (!this.doneButtonPressed) {
            releasePlayer(true);
        }
        this.animationInProgress = 2;
        this.transitionAnimationStartTime = System.currentTimeMillis();
        this.containerView.setLayerType(2, null);
        animatorSet.start();
        if (placeProviderObject != null) {
            placeProviderObject.imageReceiver.setVisible(true, true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$closePhoto$148(BaseFragment baseFragment) {
        if (this.parentFragment == baseFragment) {
            this.parentFragment = null;
        }
        this.parentFragmentNullifier = null;
    }

    private ClippingImageView[] getAnimatingImageViews(PlaceProviderObject placeProviderObject) {
        int i = (AndroidUtilities.isTablet() || placeProviderObject == null || placeProviderObject.animatingImageView == null) ? 0 : 1;
        ClippingImageView[] clippingImageViewArr = new ClippingImageView[i + 1];
        clippingImageViewArr[0] = this.animatingImageView;
        if (i != 0) {
            ClippingImageView clippingImageView = placeProviderObject.animatingImageView;
            clippingImageViewArr[1] = clippingImageView;
            clippingImageView.setAdditionalTranslationY(placeProviderObject.animatingImageViewYOffset);
        }
        return clippingImageViewArr;
    }

    private void removeObservers() {
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoadFailed);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoaded);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.customStickerCreated);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileLoadProgressChanged);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.mediaCountDidLoad);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.mediaDidLoad);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.dialogPhotosUpdate);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.messagesDeleted);
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.filePreparingFailed);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.fileNewChunkAvailable);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.replaceMessagesObjects);
        NotificationCenter.getInstance(this.currentAccount).removeObserver(this, NotificationCenter.dialogDeleted);
        ConnectionsManager.getInstance(this.currentAccount).cancelRequestsForGuid(this.classGuid);
    }

    public void destroyPhotoViewer() {
        if (this.parentActivity == null && this.windowView == null) {
            return;
        }
        if (PipVideoOverlay.isVisible()) {
            PipVideoOverlay.dismiss();
        }
        removeObservers();
        releasePlayer(false);
        try {
            FrameLayout frameLayout = this.windowView;
            if (frameLayout != null && frameLayout.getParent() != null) {
                ((WindowManager) this.parentActivity.getSystemService("window")).removeViewImmediate(this.windowView);
                onHideView();
            }
            this.windowView = null;
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        ImageReceiver.BitmapHolder bitmapHolder = this.currentThumb;
        if (bitmapHolder != null) {
            bitmapHolder.release();
            this.currentThumb = null;
        }
        this.animatingImageView.setImageBitmap(null);
        if (this == PipInstance) {
            PipInstance = null;
        } else {
            Instance = null;
        }
        onHideView();
    }

    private void onPhotoClosed(final PlaceProviderObject placeProviderObject) throws NumberFormatException {
        ClippingImageView clippingImageView;
        if (this.doneButtonPressed) {
            releasePlayer(true);
        }
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject != null && !messageObject.putInDownloadsStore) {
            FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.currentMessageObject.getDocument());
        }
        this.isVisible = false;
        this.isVisibleOrAnimating = false;
        this.cropInitied = false;
        this.disableShowCheck = true;
        this.currentMessageObject = null;
        this.currentBotInlineResult = null;
        this.currentFileLocation = null;
        this.currentFileLocationVideo = null;
        this.currentSecureDocument = null;
        this.currentPageBlock = null;
        this.currentPathObject = null;
        this.dialogPhotos = null;
        VideoAds videoAds = this.ads;
        if (videoAds != null) {
            videoAds.stop();
            this.ads = null;
        }
        if (this.videoPlayerControlFrameLayout != null) {
            setVideoPlayerControlVisible(false, false);
        }
        CaptionScrollView captionScrollView = this.captionScrollView;
        if (captionScrollView != null) {
            captionScrollView.reset();
        }
        this.sendPhotoType = 0;
        this.isDocumentsPicker = false;
        ImageReceiver.BitmapHolder bitmapHolder = this.currentThumb;
        if (bitmapHolder != null) {
            bitmapHolder.release();
            this.currentThumb = null;
        }
        this.parentAlert = null;
        AnimatedFileDrawable animatedFileDrawable = this.currentAnimation;
        if (animatedFileDrawable != null) {
            animatedFileDrawable.removeSecondParentView(this.containerView);
            this.currentAnimation = null;
        }
        for (int i = 0; i < 3; i++) {
            PhotoProgressView photoProgressView = this.photoProgressViews[i];
            if (photoProgressView != null) {
                photoProgressView.setBackgroundState(-1, false, true);
            }
        }
        requestVideoPreview(0);
        VideoTimelinePlayView videoTimelinePlayView = this.videoTimelineView;
        if (videoTimelinePlayView != null) {
            videoTimelinePlayView.destroy();
        }
        this.hintView.hide(false, 0);
        this.centerImage.setImageBitmap((Bitmap) null);
        this.centerBlur.destroy();
        this.leftImage.setImageBitmap((Bitmap) null);
        this.leftBlur.destroy();
        this.rightImage.setImageBitmap((Bitmap) null);
        this.rightBlur.destroy();
        this.containerView.post(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda115
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onPhotoClosed$149(placeProviderObject);
            }
        });
        PhotoViewerProvider photoViewerProvider = this.placeProvider;
        if (photoViewerProvider != null) {
            photoViewerProvider.willHidePhotoViewer();
        }
        this.groupedPhotosListView.clear();
        PhotoViewerProvider photoViewerProvider2 = this.placeProvider;
        if (photoViewerProvider2 != null) {
            photoViewerProvider2.onClose();
        }
        this.placeProvider = null;
        this.selectedPhotosAdapter.notifyDataSetChanged();
        this.pageBlocksAdapter = null;
        this.disableShowCheck = false;
        this.shownControlsByEnd = false;
        this.videoCutStart = 0.0f;
        this.videoCutEnd = 1.0f;
        if (placeProviderObject != null) {
            placeProviderObject.imageReceiver.setVisible(true, true);
            if (placeProviderObject.fromAyuDeleted && (clippingImageView = this.animatingImageView) != null) {
                clippingImageView.setVisibility(8);
            }
        }
        ChatActivity chatActivity = this.parentChatActivity;
        if (chatActivity != null) {
            chatActivity.getFragmentView().invalidate();
        }
        Bitmap bitmap = this.videoFrameBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.videoFrameBitmap = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPhotoClosed$149(PlaceProviderObject placeProviderObject) {
        ClippingImageView clippingImageView;
        this.animatingImageView.setImageBitmap(null);
        if (placeProviderObject != null && !AndroidUtilities.isTablet() && (clippingImageView = placeProviderObject.animatingImageView) != null) {
            clippingImageView.setImageBitmap(null);
        }
        try {
            if (this.windowView.getParent() != null) {
                ((WindowManager) this.parentActivity.getSystemService("window")).removeView(this.windowView);
                onHideView();
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    private void redraw(final int i) {
        FrameLayoutDrawer frameLayoutDrawer;
        if (i >= 6 || (frameLayoutDrawer = this.containerView) == null) {
            return;
        }
        frameLayoutDrawer.invalidate();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda78
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$redraw$150(i);
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$redraw$150(int i) {
        redraw(i + 1);
    }

    public void onResume() {
        VideoPlayer videoPlayer;
        redraw(0);
        VideoPlayer videoPlayer2 = this.videoPlayer;
        if (videoPlayer2 != null) {
            videoPlayer2.seekTo(videoPlayer2.getCurrentPosition() + 1);
            if (this.playerLooping) {
                this.videoPlayer.setLooping(true);
            }
        }
        LPhotoPaintView lPhotoPaintView = this.photoPaintView;
        if (lPhotoPaintView != null) {
            lPhotoPaintView.onResume();
        }
        if (!this.pauseOnMinimize || !ExteraConfig.pauseOnMinimizeVideo || (videoPlayer = this.videoPlayer) == null || videoPlayer.isPlaying()) {
            return;
        }
        this.pauseOnMinimize = false;
        this.videoPlayer.play();
        requestAudioFocus(true);
    }

    public void onPause() throws NumberFormatException {
        VideoPlayer videoPlayer;
        if (this.currentAnimation != null) {
            closePhoto(false, false);
            return;
        }
        if (this.lastTitle != null) {
            closeCaptionEnter(true);
        }
        VideoPlayer videoPlayer2 = this.videoPlayer;
        if (videoPlayer2 != null && this.playerLooping) {
            videoPlayer2.setLooping(allowLoopingOnPause());
        }
        if (ExteraConfig.pauseOnMinimizeVideo && (videoPlayer = this.videoPlayer) != null && videoPlayer.isPlaying()) {
            this.pauseOnMinimize = true;
            this.videoPlayer.pause();
            requestAudioFocus(false);
        }
    }

    private boolean allowLoopingOnPause() {
        return AndroidUtilities.isInPictureInPictureMode(this.parentActivity);
    }

    public boolean isVisible() {
        return this.isVisible && this.placeProvider != null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMinMax(float f) {
        AspectRatioFrameLayout aspectRatioFrameLayout = this.aspectRatioFrameLayout;
        if (aspectRatioFrameLayout != null && aspectRatioFrameLayout.getVisibility() == 0 && this.textureUploaded) {
            View view = this.usedSurfaceView ? this.videoSurfaceView : this.videoTextureView;
            f *= Math.min(getContainerViewWidth() / view.getMeasuredWidth(), getContainerViewHeight() / view.getMeasuredHeight());
        }
        float imageWidth = this.centerImage.getImageWidth();
        float imageHeight = this.centerImage.getImageHeight();
        MediaController.CropState cropState = this.editState.cropState;
        if (cropState != null) {
            imageWidth *= cropState.cropPw;
            imageHeight *= cropState.cropPh;
        }
        int containerViewWidth = this.sendPhotoType == 11 ? (int) (imageWidth * f) : ((int) ((imageWidth * f) - getContainerViewWidth())) / 2;
        int containerViewHeight = this.sendPhotoType == 11 ? (int) (imageHeight * f) : ((int) ((imageHeight * f) - getContainerViewHeight())) / 2;
        if (containerViewWidth > 0) {
            this.minX = -containerViewWidth;
            this.maxX = containerViewWidth;
        } else {
            this.maxX = 0.0f;
            this.minX = 0.0f;
        }
        if (containerViewHeight > 0) {
            this.minY = -containerViewHeight;
            this.maxY = containerViewHeight;
        } else {
            this.maxY = 0.0f;
            this.minY = 0.0f;
        }
        LPhotoPaintView lPhotoPaintView = this.photoPaintView;
        if (lPhotoPaintView != null) {
            lPhotoPaintView.updateZoom(f <= 1.1f);
        }
    }

    private int getAdditionX(int i) {
        if (i == 1 || (i == 0 && this.sendPhotoType == 1)) {
            return AndroidUtilities.m1146dp(16.0f);
        }
        if (i == 0 || i == 5 || i == 4 || i == 3) {
            return 0;
        }
        return AndroidUtilities.m1146dp(14.0f);
    }

    private int getAdditionY(int i) {
        if (i < 0) {
            return 0;
        }
        if (i == 1 || (i == 0 && this.sendPhotoType == 1)) {
            return AndroidUtilities.m1146dp(16.0f) + (isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0);
        }
        if (i == 3 && this.photoPaintView != null) {
            return AndroidUtilities.m1146dp(8.0f) + (isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0) + this.photoPaintView.getAdditionalTop();
        }
        if (i == 0 || i == 4 || i == 5) {
            return 0;
        }
        return AndroidUtilities.m1146dp(14.0f) + (isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getContainerViewWidth() {
        return getContainerViewWidth(this.currentEditMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getContainerViewWidth(int i) {
        int iM1146dp;
        int width = this.containerView.getWidth();
        if (i == 1 || (i == 0 && this.sendPhotoType == 1)) {
            iM1146dp = AndroidUtilities.m1146dp(32.0f);
        } else {
            if (i == 0 || i == 4 || i == 5 || i == 3) {
                return width;
            }
            iM1146dp = AndroidUtilities.m1146dp(28.0f);
        }
        return width - iM1146dp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getContainerViewHeight() {
        return getContainerViewHeight(this.currentEditMode);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getContainerViewHeight(int i) {
        return getContainerViewHeight(false, i);
    }

    private int getContainerViewHeight(boolean z, int i) {
        int measuredHeight;
        int iM1146dp;
        if (z || this.inBubbleMode) {
            measuredHeight = this.containerView.getMeasuredHeight();
        } else {
            measuredHeight = AndroidUtilities.displaySize.y + (AndroidUtilities.navigationBarHeight - this.insets.bottom);
            if ((i == 0 || i == 4 || i == 5) && this.sendPhotoType != 1 && isStatusBarVisible()) {
                measuredHeight += AndroidUtilities.statusBarHeight;
            }
        }
        if ((i == 0 && this.sendPhotoType == 1) || i == 1) {
            iM1146dp = AndroidUtilities.m1146dp(144.0f);
        } else if (i == 2) {
            iM1146dp = AndroidUtilities.m1146dp(214.0f);
        } else {
            if (i != 3) {
                return measuredHeight;
            }
            iM1146dp = AndroidUtilities.m1146dp(48.0f) + this.photoPaintView.getAdditionalBottom() + ActionBar.getCurrentActionBarHeight() + this.photoPaintView.getAdditionalTop();
        }
        return measuredHeight - iM1146dp;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:214:0x0402  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x040f  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x0426  */
    /* JADX WARN: Removed duplicated region for block: B:264:0x0518 A[PHI: r4
      0x0518: PHI (r4v65 float) = (r4v60 float), (r4v61 float) binds: [B:263:0x0516, B:266:0x051e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0527 A[PHI: r4
      0x0527: PHI (r4v64 float) = (r4v62 float), (r4v63 float) binds: [B:269:0x0525, B:272:0x052d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0529  */
    /* JADX WARN: Removed duplicated region for block: B:279:0x054c A[PHI: r4
      0x054c: PHI (r4v46 float) = (r4v41 float), (r4v42 float) binds: [B:278:0x054a, B:281:0x0552] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:285:0x055d A[PHI: r4
      0x055d: PHI (r4v45 float) = (r4v43 float), (r4v44 float) binds: [B:284:0x055b, B:287:0x0563] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:286:0x055f  */
    /* JADX WARN: Removed duplicated region for block: B:344:0x0669 A[PHI: r4
      0x0669: PHI (r4v22 float) = (r4v17 float), (r4v18 float) binds: [B:343:0x0667, B:346:0x066f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:350:0x067a A[PHI: r4
      0x067a: PHI (r4v21 float) = (r4v19 float), (r4v20 float) binds: [B:349:0x0678, B:352:0x0680] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:351:0x067c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEvent(android.view.MotionEvent r21) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 2089
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0022 A[PHI: r3
      0x0022: PHI (r3v4 float) = (r3v2 float), (r3v3 float) binds: [B:9:0x0020, B:12:0x0028] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:4:0x0011 A[PHI: r3
      0x0011: PHI (r3v5 float) = (r3v0 float), (r3v1 float) binds: [B:3:0x000f, B:6:0x0017] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void checkMinMax(boolean r6) {
        /*
            r5 = this;
            float r0 = r5.translationX
            float r1 = r5.translationY
            float r2 = r5.scale
            r5.updateMinMax(r2)
            float r2 = r5.translationX
            float r3 = r5.minX
            int r4 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r4 >= 0) goto L13
        L11:
            r0 = r3
            goto L1a
        L13:
            float r3 = r5.maxX
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L1a
            goto L11
        L1a:
            float r2 = r5.translationY
            float r3 = r5.minY
            int r4 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r4 >= 0) goto L24
        L22:
            r1 = r3
            goto L2b
        L24:
            float r3 = r5.maxY
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L2b
            goto L22
        L2b:
            float r2 = r5.scale
            r5.animateTo(r2, r0, r1, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.checkMinMax(boolean):void");
    }

    private void goToNext() {
        float containerViewWidth = this.scale != 1.0f ? ((getContainerViewWidth() - this.centerImage.getImageWidth()) / 2.0f) * this.scale : 0.0f;
        this.switchImageAfterAnimation = 1;
        animateTo(this.scale, ((this.minX - getContainerViewWidth()) - containerViewWidth) - (AndroidUtilities.m1146dp(30.0f) / 2), this.translationY, false);
    }

    private void goToPrev() {
        float containerViewWidth = this.scale != 1.0f ? ((getContainerViewWidth() - this.centerImage.getImageWidth()) / 2.0f) * this.scale : 0.0f;
        this.switchImageAfterAnimation = 2;
        animateTo(this.scale, this.maxX + getContainerViewWidth() + containerViewWidth + (AndroidUtilities.m1146dp(30.0f) / 2), this.translationY, false);
    }

    private void cancelMoveZoomAnimation() {
        AnimatorSet animatorSet = this.imageMoveAnimation;
        if (animatorSet == null) {
            return;
        }
        float f = this.scale;
        float f2 = this.animateToScale - f;
        float f3 = this.animationValue;
        float f4 = f + (f2 * f3);
        float f5 = this.translationX;
        float f6 = f5 + ((this.animateToX - f5) * f3);
        float f7 = this.translationY;
        float f8 = f7 + ((this.animateToY - f7) * f3);
        float f9 = this.rotate;
        float f10 = f9 + ((this.animateToRotate - f9) * f3);
        animatorSet.cancel();
        this.scale = f4;
        this.translationX = f6;
        this.translationY = f8;
        this.animationStartTime = 0L;
        this.rotate = f10;
        updateMinMax(f4);
        this.zoomAnimation = false;
        this.containerView.invalidate();
    }

    public void zoomOut() {
        animateTo(1.0f, 0.0f, 0.0f, false);
    }

    private void animateTo(float f, float f2, float f3, boolean z) {
        animateTo(f, f2, f3, z, MediaDataController.MAX_LINKS_COUNT);
    }

    private void animateTo(float f, float f2, float f3, boolean z, int i) {
        if (this.scale == f && this.translationX == f2 && this.translationY == f3) {
            return;
        }
        this.zoomAnimation = z;
        this.animateToScale = f;
        this.animateToX = f2;
        this.animateToY = f3;
        this.animationStartTime = System.currentTimeMillis();
        AnimatorSet animatorSet = new AnimatorSet();
        this.imageMoveAnimation = animatorSet;
        animatorSet.playTogether(ObjectAnimator.ofFloat(this, (Property<PhotoViewer, Float>) AnimationProperties.PHOTO_VIEWER_ANIMATION_VALUE, 0.0f, 1.0f));
        this.imageMoveAnimation.setInterpolator(this.interpolator);
        this.imageMoveAnimation.setDuration(i);
        this.imageMoveAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.82
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PhotoViewer.this.imageMoveAnimation = null;
                PhotoViewer.this.containerView.invalidate();
            }
        });
        this.imageMoveAnimation.start();
    }

    public int getCurrentIndex() {
        return this.currentIndex;
    }

    public List getImagesArrLocals() {
        return this.imagesArrLocals;
    }

    @Keep
    public void setAnimationValue(float f) {
        this.animationValue = f;
        this.containerView.invalidate();
        invalidateBlur();
    }

    @Keep
    public float getAnimationValue() {
        return this.animationValue;
    }

    private void switchToNextIndex(int i, boolean z) throws Resources.NotFoundException, NumberFormatException {
        if (this.currentMessageObject != null) {
            releasePlayer(false);
            FileLoader.getInstance(this.currentAccount).cancelLoadFile(this.currentMessageObject.getDocument());
        } else if (this.currentPageBlock != null) {
            TLObject media = this.pageBlocksAdapter.getMedia(this.currentIndex);
            if (media instanceof TLRPC.Document) {
                releasePlayer(false);
                FileLoader.getInstance(this.currentAccount).cancelLoadFile((TLRPC.Document) media);
            }
        }
        GroupedPhotosListView groupedPhotosListView = this.groupedPhotosListView;
        if (groupedPhotosListView != null) {
            groupedPhotosListView.setAnimateBackground(true);
        }
        this.playerAutoStarted = false;
        setImageIndex(this.currentIndex + i, z, true);
        if (shouldMessageObjectAutoPlayed(this.currentMessageObject) || shouldIndexAutoPlayed(this.currentIndex)) {
            this.playerAutoStarted = true;
            onActionClick(true);
            checkProgress(0, false, true);
        }
        checkFullscreenButton();
        try {
            CastSync.check(0);
            if (ChromecastController.getInstance().isCasting()) {
                ChromecastController.getInstance().setCurrentMediaAndCastIfNeeded(getCurrentChromecastMedia());
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean shouldMessageObjectAutoPlayed(MessageObject messageObject) {
        if (messageObject == null) {
            return false;
        }
        if ((messageObject.isVideo() && SharedConfig.isAutoplayVideo()) || (messageObject.isGif() && SharedConfig.isAutoplayGifs())) {
            return (messageObject.mediaExists || messageObject.attachPathExists || messageObject.hasVideoQualities() || (messageObject.canStreamVideo() && SharedConfig.streamMedia)) && SharedConfig.isAutoplayVideo();
        }
        return false;
    }

    private boolean shouldIndexAutoPlayed(int i) {
        File file;
        PageBlocksAdapter pageBlocksAdapter = this.pageBlocksAdapter;
        if (pageBlocksAdapter != null) {
            return (pageBlocksAdapter.isVideo(i) || this.pageBlocksAdapter.isHardwarePlayer(i)) && SharedConfig.isAutoplayVideo() && (file = this.pageBlocksAdapter.getFile(i)) != null && file.exists();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getCropFillScale(boolean z) {
        ImageReceiver imageReceiver = this.centerImage;
        int bitmapHeight = z ? imageReceiver.getBitmapHeight() : imageReceiver.getBitmapWidth();
        int bitmapWidth = z ? this.centerImage.getBitmapWidth() : this.centerImage.getBitmapHeight();
        float fMin = Math.min(this.photoCropView.getMeasuredWidth(), (this.photoCropView.getMeasuredHeight() - AndroidUtilities.m1146dp(64.0f)) - (isStatusBarVisible() ? AndroidUtilities.statusBarHeight : 0)) - (AndroidUtilities.m1146dp(16.0f) * 2);
        return Math.max(fMin / bitmapHeight, fMin / bitmapWidth);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isStatusBarVisible() {
        return !this.inBubbleMode;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDraw$151() throws Resources.NotFoundException, NumberFormatException {
        switchToNextIndex(1, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDraw$152() throws Resources.NotFoundException, NumberFormatException {
        switchToNextIndex(-1, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:219:0x04cf  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x053f  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x05e2  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x05e5  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0668  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0720  */
    /* JADX WARN: Removed duplicated region for block: B:341:0x082f  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x0832  */
    /* JADX WARN: Removed duplicated region for block: B:345:0x083d  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x0840  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x085c  */
    /* JADX WARN: Removed duplicated region for block: B:356:0x08b0  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x08cc  */
    /* JADX WARN: Removed duplicated region for block: B:395:0x09b2  */
    /* JADX WARN: Removed duplicated region for block: B:404:0x09cc  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x09fa  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x0a2a  */
    /* JADX WARN: Removed duplicated region for block: B:435:0x0a33  */
    /* JADX WARN: Removed duplicated region for block: B:468:0x0ab3  */
    /* JADX WARN: Removed duplicated region for block: B:471:0x0abe  */
    /* JADX WARN: Removed duplicated region for block: B:479:0x0ad6  */
    /* JADX WARN: Removed duplicated region for block: B:483:0x0afa  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x0b1f  */
    /* JADX WARN: Removed duplicated region for block: B:513:0x0b67  */
    /* JADX WARN: Removed duplicated region for block: B:515:0x0b6b  */
    /* JADX WARN: Removed duplicated region for block: B:524:0x0ba5  */
    /* JADX WARN: Removed duplicated region for block: B:527:0x0baf  */
    /* JADX WARN: Removed duplicated region for block: B:539:0x0bea  */
    /* JADX WARN: Removed duplicated region for block: B:542:0x0c08  */
    /* JADX WARN: Removed duplicated region for block: B:545:0x0c30  */
    /* JADX WARN: Removed duplicated region for block: B:548:0x0c59  */
    /* JADX WARN: Removed duplicated region for block: B:558:0x0c77  */
    /* JADX WARN: Removed duplicated region for block: B:560:0x0c80  */
    /* JADX WARN: Removed duplicated region for block: B:562:0x0c88  */
    /* JADX WARN: Removed duplicated region for block: B:565:0x0c93  */
    /* JADX WARN: Removed duplicated region for block: B:571:0x0cbd  */
    /* JADX WARN: Removed duplicated region for block: B:575:0x0cd9  */
    /* JADX WARN: Removed duplicated region for block: B:578:0x0ce3  */
    /* JADX WARN: Removed duplicated region for block: B:581:0x0cec  */
    /* JADX WARN: Removed duplicated region for block: B:582:0x0d08  */
    /* JADX WARN: Removed duplicated region for block: B:587:0x0d20  */
    /* JADX WARN: Removed duplicated region for block: B:589:0x0d25  */
    /* JADX WARN: Removed duplicated region for block: B:649:0x0e3b  */
    /* JADX WARN: Removed duplicated region for block: B:651:0x0e43  */
    /* JADX WARN: Removed duplicated region for block: B:653:0x0e4e  */
    /* JADX WARN: Removed duplicated region for block: B:670:0x0e7f  */
    /* JADX WARN: Removed duplicated region for block: B:679:0x0ea5  */
    /* JADX WARN: Removed duplicated region for block: B:683:0x0eb5  */
    /* JADX WARN: Removed duplicated region for block: B:734:0x0ff5  */
    /* JADX WARN: Removed duplicated region for block: B:736:0x0ff8  */
    /* JADX WARN: Removed duplicated region for block: B:747:0x106b  */
    /* JADX WARN: Removed duplicated region for block: B:814:0x0f55 A[EDGE_INSN: B:814:0x0f55->B:708:0x0f55 BREAK  A[LOOP:1: B:681:0x0eb0->B:707:0x0f51], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onDraw(android.graphics.Canvas r50) {
        /*
            Method dump skipped, instructions count: 4577
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.onDraw(android.graphics.Canvas):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onDraw$153() {
        checkChangedTextureView(false);
        PipVideoOverlay.dismiss(true, true);
    }

    private void drawFancyShadows(Canvas canvas) {
        float f;
        if (this.fancyShadows) {
            if (SharedConfig.photoViewerBlur) {
                AnimatedFloat animatedFloat = this.blurAlpha;
                int i = this.animationInProgress;
                f = animatedFloat.set(i == 0 || i == 2 || i == 3);
            } else {
                f = 1.0f;
            }
            if (f <= 0.0f) {
                return;
            }
            int currentActionBarHeight = ((int) (AndroidUtilities.statusBarHeight * 1.5f)) + ActionBar.getCurrentActionBarHeight();
            int height = AndroidUtilities.navigationBarHeight + this.pickerView.getHeight() + (this.captionEdit.getVisibility() == 0 ? (this.captionEdit.getEditTextHeightClosedKeyboard() / 2) + AndroidUtilities.m1146dp(20.0f) : 0);
            if (this.clipFancyShadows == null) {
                this.clipFancyShadows = new Path();
                Paint paint = new Paint(1);
                this.topFancyShadowPaint = paint;
                PorterDuff.Mode mode = PorterDuff.Mode.DST_IN;
                paint.setXfermode(new PorterDuffXfermode(mode));
                Paint paint2 = new Paint(1);
                this.bottomFancyShadowPaint = paint2;
                paint2.setXfermode(new PorterDuffXfermode(mode));
                Shader.TileMode tileMode = Shader.TileMode.CLAMP;
                this.topFancyShadow = new LinearGradient(0.0f, 0.0f, 0.0f, 16.0f, new int[]{-16777216, 0}, new float[]{0.0f, 1.0f}, tileMode);
                this.bottomFancyShadow = new LinearGradient(0.0f, 0.0f, 0.0f, 16.0f, new int[]{0, -16777216}, new float[]{0.0f, 1.0f}, tileMode);
                this.topFancyShadowMatrix = new Matrix();
                this.bottomFancyShadowMatrix = new Matrix();
                this.topFancyShadowPaint.setShader(this.topFancyShadow);
                this.bottomFancyShadowPaint.setShader(this.bottomFancyShadow);
            }
            canvas.saveLayerAlpha(0.0f, 0.0f, this.containerView.getWidth(), this.containerView.getHeight() + AndroidUtilities.navigationBarHeight, (int) (f * (this.backgroundDrawable.getAlpha() - 127) * 2.007874f), 31);
            this.clipFancyShadows.rewind();
            Path path = this.clipFancyShadows;
            float width = this.containerView.getWidth();
            float f2 = currentActionBarHeight;
            Path.Direction direction = Path.Direction.CW;
            path.addRect(0.0f, 0.0f, width, f2, direction);
            this.clipFancyShadows.addRect(0.0f, (this.containerView.getHeight() + AndroidUtilities.navigationBarHeight) - height, this.containerView.getWidth(), this.containerView.getHeight() + AndroidUtilities.navigationBarHeight, direction);
            canvas.clipPath(this.clipFancyShadows);
            canvas.drawColor(-16777216);
            drawCaptionBlur(canvas, this.shadowBlurer, 0, 0, true, true, false);
            canvas.save();
            this.topFancyShadowMatrix.reset();
            this.topFancyShadowMatrix.postScale(1.0f, f2 / 16.0f);
            this.topFancyShadow.setLocalMatrix(this.topFancyShadowMatrix);
            this.topFancyShadowPaint.setAlpha(208);
            canvas.drawRect(0.0f, 0.0f, this.containerView.getWidth(), f2, this.topFancyShadowPaint);
            this.bottomFancyShadowMatrix.reset();
            this.bottomFancyShadowMatrix.postScale(1.0f, height / 16.0f);
            this.bottomFancyShadowMatrix.postTranslate(0.0f, (this.containerView.getHeight() - height) + AndroidUtilities.navigationBarHeight);
            this.bottomFancyShadow.setLocalMatrix(this.bottomFancyShadowMatrix);
            this.bottomFancyShadowPaint.setAlpha(Opcodes.NEW);
            canvas.drawRect(0.0f, (this.containerView.getHeight() + AndroidUtilities.navigationBarHeight) - height, this.containerView.getWidth(), this.containerView.getHeight() + AndroidUtilities.navigationBarHeight, this.bottomFancyShadowPaint);
            canvas.restore();
            canvas.restore();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawCenterImageInternal(android.graphics.Canvas r7, float r8, float r9) {
        /*
            r6 = this;
            java.util.ArrayList r0 = r6.imagesArrLocals
            boolean r0 = r0.isEmpty()
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L26
            int r0 = r6.currentEditMode
            if (r0 == r1) goto L1f
            int r0 = r6.sendPhotoType
            if (r0 != r1) goto L13
            goto L1f
        L13:
            org.telegram.ui.PhotoViewer$EditState r0 = r6.editState
            org.telegram.messenger.MediaController$CropState r0 = r0.cropState
            if (r0 == 0) goto L26
            boolean r0 = r0.mirrored
            if (r0 == 0) goto L26
            r0 = 1
            goto L27
        L1f:
            org.telegram.ui.Components.Crop.CropTransform r0 = r6.cropTransform
            boolean r0 = r0.isMirrored()
            goto L27
        L26:
            r0 = 0
        L27:
            r3 = 1065353216(0x3f800000, float:1.0)
            if (r0 == 0) goto L35
            r7.save()
            r0 = -1082130432(0xffffffffbf800000, float:-1.0)
            r7.scale(r0, r3)
            r0 = 1
            goto L36
        L35:
            r0 = 0
        L36:
            r4 = 0
            int r5 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1))
            if (r5 <= 0) goto L58
            if (r0 != 0) goto L41
            r7.save()
            r0 = 1
        L41:
            r5 = 1073741824(0x40000000, float:2.0)
            float r5 = r5 * r8
            float r5 = r3 - r5
            r7.scale(r5, r3)
            r5 = 1082130432(0x40800000, float:4.0)
            float r5 = r5 * r8
            float r3 = r3 - r8
            float r5 = r5 * r3
            r8 = 1048576000(0x3e800000, float:0.25)
            float r5 = r5 * r8
            r7.skew(r4, r5)
        L58:
            org.telegram.ui.Components.PhotoViewerWebView r8 = r6.photoViewerWebView
            if (r8 == 0) goto L62
            boolean r8 = r8.isLoaded()
            if (r8 != 0) goto La9
        L62:
            boolean r8 = r6.centerImageTransformLocked
            if (r8 != 0) goto L77
            android.graphics.Matrix r8 = r6.centerImageTransform
            org.telegram.messenger.ImageReceiver r3 = r6.centerImage
            float r3 = r3.getImageX()
            org.telegram.messenger.ImageReceiver r4 = r6.centerImage
            float r4 = r4.getImageY()
            r8.preTranslate(r3, r4)
        L77:
            org.telegram.ui.Components.Paint.Views.StickerMakerView r8 = r6.stickerMakerView
            org.telegram.ui.PhotoViewer$FrameLayoutDrawer r3 = r6.containerView
            int r4 = r6.switchingToMode
            r5 = -1
            if (r4 == r5) goto L82
            r4 = 1
            goto L83
        L82:
            r4 = 0
        L83:
            r8.drawOutline(r7, r2, r3, r4)
            org.telegram.messenger.ImageReceiver r8 = r6.centerImage
            r8.setAlpha(r9)
            org.telegram.messenger.ImageReceiver r8 = r6.centerImage
            r8.draw(r7)
            org.telegram.ui.Components.Paint.Views.StickerMakerView r8 = r6.stickerMakerView
            org.telegram.ui.PhotoViewer$FrameLayoutDrawer r9 = r6.containerView
            int r3 = r6.switchingToMode
            if (r3 == r5) goto L99
            r2 = 1
        L99:
            r8.drawOutline(r7, r1, r9, r2)
            org.telegram.ui.Components.Paint.Views.StickerMakerView r8 = r6.stickerMakerView
            org.telegram.messenger.ImageReceiver r9 = r6.centerImage
            android.graphics.Matrix r2 = r6.centerImageTransform
            org.telegram.ui.PhotoViewer$FrameLayoutDrawer r3 = r6.containerView
            r8.drawSegmentBorderPath(r7, r9, r2, r3)
            r6.centerImageTransformLocked = r1
        La9:
            if (r0 == 0) goto Lae
            r7.restore()
        Lae:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.drawCenterImageInternal(android.graphics.Canvas, float, float):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void drawProgress(android.graphics.Canvas r9, float r10, float r11, float r12, float r13) {
        /*
            Method dump skipped, instructions count: 251
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.drawProgress(android.graphics.Canvas, float, float, float, float):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int[] applyCrop(android.graphics.Canvas r23, int r24, int r25, int r26, int r27, float r28, org.telegram.p023ui.Components.Crop.CropTransform r29, org.telegram.messenger.MediaController.CropState r30) {
        /*
            Method dump skipped, instructions count: 401
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.applyCrop(android.graphics.Canvas, int, int, int, int, float, org.telegram.ui.Components.Crop.CropTransform, org.telegram.messenger.MediaController$CropState):int[]");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02ae  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00dc A[PHI: r0
      0x00dc: PHI (r0v26 java.io.File) = (r0v11 java.io.File), (r0v21 java.io.File), (r0v24 java.io.File), (r0v63 java.io.File) binds: [B:93:0x0199, B:69:0x013b, B:64:0x00fa, B:22:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01a7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01b1 A[ADDED_TO_REGION] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void onActionClick(boolean r10) throws java.lang.NumberFormatException {
        /*
            Method dump skipped, instructions count: 708
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.onActionClick(boolean):void");
    }

    @Override // org.telegram.ui.Components.GestureDetector2.OnGestureListener
    public boolean onDown(MotionEvent motionEvent) {
        if (!this.doubleTap && this.checkImageView.getVisibility() != 0) {
            boolean[] zArr = this.drawPressedDrawable;
            if (!zArr[0] && !zArr[1]) {
                float x = motionEvent.getX();
                if (x < Math.min(Opcodes.I2D, this.containerView.getMeasuredWidth() / 8)) {
                    if (this.leftImage.hasImageSet()) {
                        this.drawPressedDrawable[0] = true;
                        this.containerView.invalidate();
                    }
                } else if (x > this.containerView.getMeasuredWidth() - r0 && this.rightImage.hasImageSet()) {
                    this.drawPressedDrawable[1] = true;
                    this.containerView.invalidate();
                }
            }
        }
        return false;
    }

    @Override // org.telegram.ui.Components.GestureDetector2.OnDoubleTapListener
    public boolean canDoubleTap(MotionEvent motionEvent) {
        MessageObject messageObject;
        PhotoViewerWebView photoViewerWebView;
        if (this.checkImageView.getVisibility() != 0) {
            boolean[] zArr = this.drawPressedDrawable;
            if (!zArr[0] && !zArr[1]) {
                float x = motionEvent.getX();
                if ((x < Math.min(Opcodes.I2D, this.containerView.getMeasuredWidth() / 8) || x > this.containerView.getMeasuredWidth() - r3) && (messageObject = this.currentMessageObject) != null && ((!messageObject.isGif() && !this.currentMessageObject.isVideo() && ((photoViewerWebView = this.photoViewerWebView) == null || !photoViewerWebView.isControllable())) || SystemClock.elapsedRealtime() - this.lastPhotoSetTime < 500 || !canDoubleTapSeekVideo(motionEvent))) {
                    return false;
                }
            }
        }
        return true;
    }

    private void hidePressedDrawables() {
        boolean[] zArr = this.drawPressedDrawable;
        zArr[1] = false;
        zArr[0] = false;
        this.containerView.invalidate();
    }

    @Override // org.telegram.ui.Components.GestureDetector2.OnGestureListener
    public void onUp(MotionEvent motionEvent) {
        hidePressedDrawables();
    }

    @Override // org.telegram.ui.Components.GestureDetector2.OnGestureListener
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if (!this.canZoom && !this.doubleTapEnabled) {
            return onSingleTapConfirmed(motionEvent);
        }
        FrameLayoutDrawer frameLayoutDrawer = this.containerView;
        if (frameLayoutDrawer != null && frameLayoutDrawer.getTag() != null && this.photoProgressViews[0] != null) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if ((x < ((float) (getContainerViewWidth() - AndroidUtilities.m1146dp(100.0f))) / 2.0f || x > ((float) (getContainerViewWidth() + AndroidUtilities.m1146dp(100.0f))) / 2.0f || y < ((float) (getContainerViewHeight() - AndroidUtilities.m1146dp(100.0f))) / 2.0f || y > ((float) (getContainerViewHeight() + AndroidUtilities.m1146dp(100.0f))) / 2.0f) ? false : onSingleTapConfirmed(motionEvent)) {
                this.discardTap = true;
                return true;
            }
        }
        return false;
    }

    public void onLongPress() {
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer == null || this.scale > 1.35f) {
            return;
        }
        long currentPosition = videoPlayer.getCurrentPosition();
        long duration = this.videoPlayer.getDuration();
        if (currentPosition == -9223372036854775807L || duration < 8000) {
            return;
        }
        float f = this.longPressX;
        int containerViewWidth = getContainerViewWidth();
        boolean z = false;
        if (duration > 180000) {
            int i = containerViewWidth / 3;
            if (f >= i * 2) {
                z = true;
            } else if (f >= i) {
                return;
            }
            this.longVideoPlayerRewinder.startRewind(this.videoPlayer, z, this.currentVideoSpeed);
            return;
        }
        this.videoPlayerRewinder.startRewind(this.videoPlayer, f > ((float) (containerViewWidth / 3)), this.longPressX, this.currentVideoSpeed, this.seekSpeedDrawable);
    }

    public VideoPlayerRewinder getVideoPlayerRewinder() {
        return this.videoPlayerRewinder;
    }

    @Override // org.telegram.ui.Components.GestureDetector2.OnGestureListener
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.scale == 1.0f || this.sendPhotoType == 11) {
            return false;
        }
        this.scroller.abortAnimation();
        this.scroller.fling(Math.round(this.translationX), Math.round(this.translationY), Math.round(f), Math.round(f2), (int) this.minX, (int) this.maxX, (int) this.minY, (int) this.maxY);
        this.containerView.postInvalidate();
        return false;
    }

    @Override // org.telegram.ui.Components.GestureDetector2.OnDoubleTapListener
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) throws Resources.NotFoundException, NumberFormatException {
        PhotoViewerWebView photoViewerWebView;
        MessageObject messageObject;
        MessageObject messageObject2;
        if (this.discardTap) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.checkImageView.getVisibility() != 0 && SharedConfig.nextMediaTap && this.sendPhotoType != 11 && y > ActionBar.getCurrentActionBarHeight() + AndroidUtilities.statusBarHeight + AndroidUtilities.m1146dp(40.0f)) {
            if (x < Math.min(Opcodes.I2D, this.containerView.getMeasuredWidth() / 8)) {
                if (this.leftImage.hasImageSet()) {
                    switchToNextIndex(-1, true);
                    return true;
                }
            } else if (x > this.containerView.getMeasuredWidth() - r4 && this.rightImage.hasImageSet()) {
                switchToNextIndex(1, true);
                return true;
            }
        }
        MessageObject messageObject3 = this.currentMessageObject;
        if (messageObject3 != null && messageObject3.isSponsored() && x >= (getContainerViewWidth() - this.centerImage.getImageWidth()) / 2.0f && x <= (getContainerViewWidth() + this.centerImage.getImageWidth()) / 2.0f && y >= (getContainerViewHeight() - this.centerImage.getImageHeight()) / 2.0f && y <= (getContainerViewHeight() + this.centerImage.getImageHeight()) / 2.0f) {
            BaseFragment baseFragment = this.parentFragment;
            if (baseFragment instanceof ChatActivity) {
                ((ChatActivity) baseFragment).logSponsoredClicked(this.currentMessageObject, true, true);
            }
            closePhoto(true, false);
            String str = this.currentMessageObject.sponsoredUrl;
            if (str != null) {
                Context context = LaunchActivity.instance;
                if (context == null) {
                    context = this.activityContext;
                }
                Browser.openUrl(context, Uri.parse(str), true, false, false, null, null, false, MessagesController.getInstance(this.currentAccount).sponsoredLinksInappAllow, false);
            }
            return true;
        }
        PhotoViewerWebView photoViewerWebView2 = this.photoViewerWebView;
        if (photoViewerWebView2 != null && photoViewerWebView2.isControllable() && this.isActionBarVisible) {
            WebView webView = this.photoViewerWebView.getWebView();
            if (x >= webView.getX() && x <= webView.getX() + webView.getWidth() && y >= webView.getY() && y <= webView.getY() + webView.getHeight()) {
                MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                motionEventObtain.setAction(0);
                motionEventObtain.offsetLocation(-webView.getX(), -webView.getY());
                webView.dispatchTouchEvent(motionEventObtain);
                motionEventObtain.setAction(1);
                webView.dispatchTouchEvent(motionEventObtain);
                motionEventObtain.recycle();
                scheduleActionBarHide();
                return true;
            }
        }
        if (this.containerView.getTag() != null) {
            AspectRatioFrameLayout aspectRatioFrameLayout = this.aspectRatioFrameLayout;
            boolean z = (aspectRatioFrameLayout != null && aspectRatioFrameLayout.getVisibility() == 0) || ((photoViewerWebView = this.photoViewerWebView) != null && photoViewerWebView.isControllable());
            if (this.sharedMediaType == 1 && (messageObject2 = this.currentMessageObject) != null) {
                if (!messageObject2.canPreviewDocument()) {
                    float containerViewHeight = (getContainerViewHeight() - AndroidUtilities.m1146dp(360.0f)) / 2.0f;
                    if (y >= containerViewHeight && y <= containerViewHeight + AndroidUtilities.m1146dp(360.0f)) {
                        onActionClick(true);
                        return true;
                    }
                }
            } else {
                PhotoProgressView photoProgressView = this.photoProgressViews[0];
                if (photoProgressView != null && this.containerView != null) {
                    int i = photoProgressView.backgroundState;
                    if (x >= (getContainerViewWidth() - AndroidUtilities.m1146dp(100.0f)) / 2.0f && x <= (getContainerViewWidth() + AndroidUtilities.m1146dp(100.0f)) / 2.0f && y >= (getContainerViewHeight() - AndroidUtilities.m1146dp(100.0f)) / 2.0f && y <= (getContainerViewHeight() + AndroidUtilities.m1146dp(100.0f)) / 2.0f) {
                        if (z) {
                            if ((i == 3 || i == 4) && this.photoProgressViews[0].isVisible()) {
                                this.manuallyPaused = true;
                                toggleVideoPlayer();
                                return true;
                            }
                        } else if (i > 0 && i <= 3) {
                            onActionClick(true);
                            checkProgress(0, false, true);
                            return true;
                        }
                    }
                }
            }
            PhotoViewerWebView photoViewerWebView3 = this.photoViewerWebView;
            if ((photoViewerWebView3 == null || !photoViewerWebView3.isControllable() || this.photoViewerWebView.isPlaying() || !this.isActionBarVisible) && ((messageObject = this.currentMessageObject) == null || !messageObject.isSponsored())) {
                toggleActionBar(!this.isActionBarVisible, true);
            }
        } else {
            int i2 = this.sendPhotoType;
            if (i2 == 0 || i2 == 4) {
                if (this.isCurrentVideo) {
                    VideoPlayer videoPlayer = this.videoPlayer;
                    if (videoPlayer != null && !this.muteVideo && i2 != 1) {
                        videoPlayer.setVolume(1.0f);
                    }
                    this.manuallyPaused = true;
                    toggleVideoPlayer();
                } else {
                    this.checkImageView.performClick();
                }
            } else {
                TLRPC.BotInlineResult botInlineResult = this.currentBotInlineResult;
                if (botInlineResult != null && (botInlineResult.type.equals(MediaStreamTrack.VIDEO_TRACK_KIND) || MessageObject.isVideoDocument(this.currentBotInlineResult.document))) {
                    int i3 = this.photoProgressViews[0].backgroundState;
                    if (i3 > 0 && i3 <= 3 && x >= (getContainerViewWidth() - AndroidUtilities.m1146dp(100.0f)) / 2.0f && x <= (getContainerViewWidth() + AndroidUtilities.m1146dp(100.0f)) / 2.0f && y >= (getContainerViewHeight() - AndroidUtilities.m1146dp(100.0f)) / 2.0f && y <= (getContainerViewHeight() + AndroidUtilities.m1146dp(100.0f)) / 2.0f) {
                        onActionClick(true);
                        checkProgress(0, false, true);
                        return true;
                    }
                } else if (this.sendPhotoType == 2 && this.isCurrentVideo) {
                    this.manuallyPaused = true;
                    toggleVideoPlayer();
                }
            }
        }
        return true;
    }

    private boolean canDoubleTapSeekVideo(MotionEvent motionEvent) {
        PhotoViewerWebView photoViewerWebView;
        if (this.videoPlayer == null && ((photoViewerWebView = this.photoViewerWebView) == null || !photoViewerWebView.isControllable())) {
            return false;
        }
        boolean z = motionEvent.getX() >= ((float) ((getContainerViewWidth() / 3) * 2));
        long currentVideoPosition = getCurrentVideoPosition();
        long videoDuration = getVideoDuration();
        if (currentVideoPosition != -9223372036854775807L) {
            if (videoDuration > (ExteraConfig.doubleTapSeekDuration == 0 ? 10 : 15) && (!z || videoDuration - currentVideoPosition > ExteraConfig.getDoubleTapSeekDuration())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0160 A[PHI: r14
      0x0160: PHI (r14v22 float) = (r14v17 float), (r14v18 float) binds: [B:62:0x015e, B:65:0x0166] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x016f A[PHI: r14
      0x016f: PHI (r14v21 float) = (r14v19 float), (r14v20 float) binds: [B:68:0x016d, B:71:0x0175] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0171  */
    @Override // org.telegram.ui.Components.GestureDetector2.OnDoubleTapListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onDoubleTap(android.view.MotionEvent r14) {
        /*
            Method dump skipped, instructions count: 390
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.onDoubleTap(android.view.MotionEvent):boolean");
    }

    private boolean enableSwipeToPiP() {
        VideoPlayer videoPlayer;
        return (this.pipItem.getVisibility() == 0 || (this.menuItem.getVisibility() == 0 && this.menuItem.isSubItemVisible(6))) && ExteraConfig.swipeToPip && this.pipAvailable && this.textureUploaded && (videoPlayer = this.videoPlayer) != null && videoPlayer.getRepeatCount() == 0 && checkInlinePermissions() && !this.changingTextureView && !this.switchingInlineMode && !this.isInline;
    }

    /* loaded from: classes5.dex */
    private class QualityChooseView extends View {
        private int circleSize;
        private int gapSize;
        private String hightQualityDescription;
        private int lineSize;
        private String lowQualityDescription;
        private Paint paint;
        private int sideSide;
        private int startMovingQuality;
        private TextPaint textPaint;

        public QualityChooseView(Context context) {
            super(context);
            this.paint = new Paint(1);
            TextPaint textPaint = new TextPaint(1);
            this.textPaint = textPaint;
            textPaint.setTextSize(AndroidUtilities.m1146dp(14.0f));
            this.textPaint.setColor(-3289651);
            this.lowQualityDescription = LocaleController.getString("AccDescrVideoCompressLow", C2369R.string.AccDescrVideoCompressLow);
            this.hightQualityDescription = LocaleController.getString("AccDescrVideoCompressHigh", C2369R.string.AccDescrVideoCompressHigh);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) throws NumberFormatException {
            float x = motionEvent.getX();
            if (motionEvent.getAction() == 0) {
                this.startMovingQuality = PhotoViewer.this.selectedCompression;
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            if (motionEvent.getAction() == 0 || motionEvent.getAction() == 2) {
                int i = 0;
                while (true) {
                    if (i >= PhotoViewer.this.compressionsCount) {
                        break;
                    }
                    int i2 = this.sideSide;
                    int i3 = this.lineSize;
                    int i4 = this.gapSize;
                    int i5 = this.circleSize;
                    int i6 = i2 + (((i4 * 2) + i3 + i5) * i) + (i5 / 2);
                    int i7 = (i3 / 2) + (i5 / 2) + i4;
                    if (x <= i6 - i7 || x >= i6 + i7) {
                        i++;
                    } else if (PhotoViewer.this.selectedCompression != i) {
                        PhotoViewer.this.selectedCompression = i;
                        PhotoViewer.this.didChangedCompressionLevel(false);
                        invalidate();
                    }
                }
            } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                if (PhotoViewer.this.selectedCompression != this.startMovingQuality) {
                    PhotoViewer.this.requestVideoPreview(1);
                }
                PhotoViewer.this.moving = false;
            }
            return true;
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            this.circleSize = AndroidUtilities.m1146dp(8.0f);
            this.gapSize = AndroidUtilities.m1146dp(2.0f);
            this.sideSide = AndroidUtilities.m1146dp(18.0f);
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            if (PhotoViewer.this.compressionsCount != 1) {
                this.lineSize = (((getMeasuredWidth() - (this.circleSize * PhotoViewer.this.compressionsCount)) - (this.gapSize * ((PhotoViewer.this.compressionsCount * 2) - 2))) - (this.sideSide * 2)) / (PhotoViewer.this.compressionsCount - 1);
            } else {
                this.lineSize = ((getMeasuredWidth() - (this.circleSize * PhotoViewer.this.compressionsCount)) - (this.gapSize * 2)) - (this.sideSide * 2);
            }
            int measuredHeight = (getMeasuredHeight() / 2) + AndroidUtilities.m1146dp(6.0f);
            int i = 0;
            while (i < PhotoViewer.this.compressionsCount) {
                int i2 = this.sideSide;
                int i3 = this.lineSize + (this.gapSize * 2);
                int i4 = this.circleSize;
                int i5 = i2 + ((i3 + i4) * i) + (i4 / 2);
                if (i <= PhotoViewer.this.selectedCompression) {
                    this.paint.setColor(-11292945);
                } else {
                    this.paint.setColor(1728053247);
                }
                canvas.drawCircle(i5, measuredHeight, i == PhotoViewer.this.selectedCompression ? AndroidUtilities.m1146dp(6.0f) : this.circleSize / 2, this.paint);
                if (i != 0) {
                    canvas.drawRect((((i5 - (this.circleSize / 2)) - this.gapSize) - this.lineSize) + (i == PhotoViewer.this.selectedCompression + 1 ? AndroidUtilities.dpf2(2.0f) : 0.0f), measuredHeight - AndroidUtilities.m1146dp(1.0f), (r1 + this.lineSize) - (i == PhotoViewer.this.selectedCompression ? AndroidUtilities.dpf2(2.0f) : 0.0f), AndroidUtilities.m1146dp(2.0f) + measuredHeight, this.paint);
                }
                i++;
            }
            canvas.drawText(this.lowQualityDescription, this.sideSide, measuredHeight - AndroidUtilities.m1146dp(16.0f), this.textPaint);
            canvas.drawText(this.hightQualityDescription, (getMeasuredWidth() - this.sideSide) - this.textPaint.measureText(this.hightQualityDescription), measuredHeight - AndroidUtilities.m1146dp(16.0f), this.textPaint);
        }
    }

    public void updateMuteButton() {
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer != null) {
            videoPlayer.setMute(CastSync.isActive() || this.muteVideo);
        }
        if (!this.videoConvertSupported) {
            this.muteButton.setEnabled(false);
            this.muteButton.setClickable(false);
            this.muteButton.animate().alpha(0.5f).setDuration(180L).start();
            this.videoTimelineView.setMode(0);
            return;
        }
        this.muteButton.setEnabled(true);
        this.muteButton.setClickable(true);
        this.muteButton.animate().alpha(1.0f).setDuration(180L).start();
        if (this.muteVideo) {
            if (this.customTitle == null) {
                this.actionBarContainer.setSubtitle(LocaleController.getString("SoundMuted", C2369R.string.SoundMuted));
            }
            this.muteDrawable.setMuted(true, true);
            if (this.compressItem.getTag() != null) {
                this.compressItem.setAlpha(0.5f);
                this.compressItem.setEnabled(false);
            }
            if (this.sendPhotoType == 1) {
                this.videoTimelineView.setMaxProgressDiff(9600.0f / this.videoDuration);
                this.videoTimelineView.setMode(1);
                updateVideoInfo();
                return;
            } else {
                this.videoTimelineView.setMaxProgressDiff(1.0f);
                this.videoTimelineView.setMode(0);
                return;
            }
        }
        this.actionBarContainer.setSubtitle(this.currentSubtitle);
        this.muteDrawable.setMuted(false, true);
        if (this.compressItem.getTag() != null) {
            this.compressItem.setAlpha(1.0f);
            this.compressItem.setEnabled(true);
        }
        this.videoTimelineView.setMaxProgressDiff(1.0f);
        this.videoTimelineView.setMode(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void didChangedCompressionLevel(boolean z) throws NumberFormatException {
        SharedPreferences.Editor editorEdit = MessagesController.getGlobalMainSettings().edit();
        editorEdit.putInt(String.format("compress_video_%d", Integer.valueOf(this.compressionsCount)), this.selectedCompression);
        editorEdit.apply();
        updateWidthHeightBitrateForCompression();
        updateVideoInfo();
        if (z) {
            requestVideoPreview(1);
        }
    }

    private void calculateEstimatedVideoSize(boolean z, boolean z2) {
        if (z) {
            long j = (long) (((z2 ? 0L : this.audioFramesSize) + this.videoFramesSize) * (this.estimatedDuration / this.videoDuration));
            this.estimatedSize = j + ((j / 32768) * 16);
            return;
        }
        float f = this.originalSize;
        long j2 = this.estimatedDuration;
        float f2 = this.videoDuration;
        long j3 = (long) (f * (j2 / f2));
        this.estimatedSize = j3;
        if (z2) {
            this.estimatedSize = j3 - ((long) (this.audioFramesSize * (j2 / f2)));
        }
    }

    private boolean needEncoding() {
        if (this.bitrate == -2) {
            return false;
        }
        EditState editState = this.editState;
        ArrayList arrayList = null;
        if (editState.croppedPaintPath != null) {
            ArrayList arrayList2 = editState.croppedMediaEntities;
            if (arrayList2 != null && !arrayList2.isEmpty()) {
                arrayList = this.editState.croppedMediaEntities;
            }
        } else {
            ArrayList arrayList3 = editState.mediaEntities;
            if (arrayList3 != null && !arrayList3.isEmpty()) {
                arrayList = this.editState.mediaEntities;
            }
        }
        EditState editState2 = this.editState;
        String str = editState2.croppedPaintPath;
        if (str == null) {
            str = editState2.paintPath;
        }
        if (this.isH264Video && this.videoCutStart == 0.0f && this.rotationValue == 0 && this.resultWidth == this.originalWidth && this.resultHeight == this.originalHeight) {
            EditState editState3 = this.editState;
            if (editState3.cropState == null && arrayList == null && str == null && editState3.savedFilterState == null && this.sendPhotoType != 1) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVideoInfo() {
        int i;
        if (this.actionBar == null) {
            return;
        }
        if (this.compressionsCount == 0) {
            this.actionBarContainer.setSubtitle(null);
            return;
        }
        this.compressItem.setState(this.videoConvertSupported && this.compressionsCount > 1, this.muteVideo, Math.min(this.resultWidth, this.resultHeight));
        this.itemsLayout.requestLayout();
        this.estimatedDuration = (long) Math.ceil((this.videoTimelineView.getRightProgress() - this.videoTimelineView.getLeftProgress()) * this.videoDuration);
        this.videoCutStart = this.videoTimelineView.getLeftProgress();
        this.videoCutEnd = this.videoTimelineView.getRightProgress();
        int i2 = this.rotationValue;
        int i3 = (i2 == 90 || i2 == 270) ? this.resultHeight : this.resultWidth;
        int i4 = this.rotationValue;
        int i5 = (i4 == 90 || i4 == 270) ? this.resultWidth : this.resultHeight;
        boolean zNeedEncoding = needEncoding();
        if (this.muteVideo) {
            if (this.sendPhotoType == 1) {
                long j = this.estimatedDuration;
                i = j <= 2000 ? 2600000 : j <= 5000 ? 2200000 : 1560000;
            } else {
                i = 921600;
            }
            long j2 = (long) ((i / 8) * (this.estimatedDuration / 1000.0f));
            this.estimatedSize = j2 + ((j2 / 32768) * 16);
        } else if (this.bitrate == -2) {
            this.estimatedSize = this.originalSize;
        } else {
            calculateEstimatedVideoSize(zNeedEncoding, this.sendPhotoType == 1);
        }
        float f = this.videoCutStart;
        if (f == 0.0f) {
            this.startTime = -1L;
        } else {
            this.startTime = ((long) (f * this.videoDuration)) * 1000;
        }
        float f2 = this.videoCutEnd;
        if (f2 == 1.0f) {
            this.endTime = -1L;
        } else {
            this.endTime = ((long) (f2 * this.videoDuration)) * 1000;
        }
        this.currentSubtitle = String.format("%s, %s", String.format("%dx%d", Integer.valueOf(i3), Integer.valueOf(i5)), String.format("%s, ~%s", AndroidUtilities.formatShortDuration((int) (this.estimatedDuration / 1000)), AndroidUtilities.formatFileSize(this.estimatedSize)));
        this.actionBar.beginDelayedTransition();
        if (this.customTitle == null) {
            this.actionBarContainer.setSubtitle(this.muteVideo ? LocaleController.getString("SoundMuted", C2369R.string.SoundMuted) : this.currentSubtitle);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestVideoPreview(int i) throws NumberFormatException {
        if (this.videoPreviewMessageObject != null) {
            MediaController.getInstance().cancelVideoConvert(this.videoPreviewMessageObject);
        }
        boolean z = this.requestingPreview && !this.tryStartRequestPreviewOnFinish;
        this.requestingPreview = false;
        this.loadInitialVideo = false;
        this.progressView.setVisibility(4);
        if (i == 1) {
            if (this.resultHeight == this.originalHeight && this.resultWidth == this.originalWidth) {
                this.tryStartRequestPreviewOnFinish = false;
                PhotoProgressView photoProgressView = this.photoProgressViews[0];
                photoProgressView.setProgress(0.0f, photoProgressView.backgroundState == 0 || this.photoProgressViews[0].previousBackgroundState == 0);
                this.photoProgressViews[0].setBackgroundState(3, false, true);
                if (!z) {
                    preparePlayer(this.currentPlayingVideoQualityFiles, this.currentPlayingVideoFile, false, false, this.editState.savedFilterState);
                    this.videoPlayer.seekTo((long) (this.videoTimelineView.getLeftProgress() * this.videoDuration));
                } else {
                    this.loadInitialVideo = true;
                }
            } else {
                releasePlayer(false);
                if (this.videoPreviewMessageObject == null) {
                    TLRPC.TL_message tL_message = new TLRPC.TL_message();
                    tL_message.f1597id = 0;
                    tL_message.message = "";
                    tL_message.media = new TLRPC.TL_messageMediaEmpty();
                    tL_message.action = new TLRPC.TL_messageActionEmpty();
                    tL_message.dialog_id = this.currentDialogId;
                    MessageObject messageObject = new MessageObject(UserConfig.selectedAccount, tL_message, false, false);
                    this.videoPreviewMessageObject = messageObject;
                    messageObject.messageOwner.attachPath = new File(FileLoader.getDirectory(4), "video_preview.mp4").getAbsolutePath();
                    this.videoPreviewMessageObject.videoEditedInfo = new VideoEditedInfo();
                    VideoEditedInfo videoEditedInfo = this.videoPreviewMessageObject.videoEditedInfo;
                    videoEditedInfo.rotationValue = this.rotationValue;
                    videoEditedInfo.originalWidth = this.originalWidth;
                    this.videoPreviewMessageObject.videoEditedInfo.originalHeight = this.originalHeight;
                    VideoEditedInfo videoEditedInfo2 = this.videoPreviewMessageObject.videoEditedInfo;
                    videoEditedInfo2.framerate = this.videoFramerate;
                    Uri uri = this.currentPlayingVideoFile;
                    if (uri == null) {
                        videoEditedInfo2.originalPath = uri.getPath();
                    } else {
                        videoEditedInfo2.originalPath = uri.getPath();
                    }
                }
                VideoEditedInfo videoEditedInfo3 = this.videoPreviewMessageObject.videoEditedInfo;
                long j = this.startTime;
                videoEditedInfo3.startTime = j;
                long j2 = this.endTime;
                videoEditedInfo3.endTime = j2;
                if (j == -1) {
                    j = 0;
                }
                if (j2 == -1) {
                    j2 = (long) (this.videoDuration * 1000.0f);
                }
                if (j2 - j > 5000000) {
                    videoEditedInfo3.endTime = j + 5000000;
                }
                videoEditedInfo3.bitrate = this.bitrate;
                this.videoPreviewMessageObject.videoEditedInfo.resultWidth = this.resultWidth;
                this.videoPreviewMessageObject.videoEditedInfo.resultHeight = this.resultHeight;
                VideoEditedInfo videoEditedInfo4 = this.videoPreviewMessageObject.videoEditedInfo;
                videoEditedInfo4.needUpdateProgress = true;
                videoEditedInfo4.originalDuration = (long) (this.videoDuration * 1000.0f);
                if (!MediaController.getInstance().scheduleVideoConvert(this.videoPreviewMessageObject, true, true, true)) {
                    this.tryStartRequestPreviewOnFinish = true;
                }
                this.requestingPreview = true;
                PhotoProgressView photoProgressView2 = this.photoProgressViews[0];
                photoProgressView2.setProgress(0.0f, photoProgressView2.backgroundState == 0 || this.photoProgressViews[0].previousBackgroundState == 0);
                this.photoProgressViews[0].setBackgroundState(0, false, true);
            }
        } else {
            this.tryStartRequestPreviewOnFinish = false;
            this.photoProgressViews[0].setBackgroundState(3, false, true);
            if (i == 2) {
                preparePlayer(this.currentPlayingVideoQualityFiles, this.currentPlayingVideoFile, false, false, this.editState.savedFilterState);
                this.videoPlayer.seekTo((long) (this.videoTimelineView.getLeftProgress() * this.videoDuration));
            }
        }
        this.containerView.invalidate();
    }

    private Size calculateResultVideoSize() {
        int iRound;
        int i;
        if (this.compressionsCount == 1) {
            return new Size(this.originalWidth, this.originalHeight);
        }
        int i2 = this.selectedCompression;
        float f = (i2 != 0 ? i2 != 1 ? i2 != 2 ? i2 != 3 ? i2 != 4 ? i2 != 5 ? 4096.0f : 3840.0f : 2560.0f : 1920.0f : 1280.0f : 854.0f : 480.0f) / (this.originalWidth > this.originalHeight ? this.originalWidth : this.originalHeight);
        if (this.selectedCompression == this.compressionsCount - 1 && f >= 1.0f) {
            i = this.originalWidth;
            iRound = this.originalHeight;
        } else {
            int iRound2 = Math.round((this.originalWidth * f) / 2.0f) * 2;
            iRound = Math.round((this.originalHeight * f) / 2.0f) * 2;
            i = iRound2;
        }
        int i3 = i % 4;
        if (i3 != 0 || iRound % 4 != 0) {
            i = (i - i3) - (iRound % 4);
        }
        return new Size(i, iRound);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void prepareRealEncoderBitrate() {
        if (this.bitrate == 0 || this.sendPhotoType == 1) {
            return;
        }
        Size sizeCalculateResultVideoSize = calculateResultVideoSize();
        if (sizeCalculateResultVideoSize.getWidth() == this.originalWidth && sizeCalculateResultVideoSize.getHeight() == this.originalHeight) {
            MediaController.extractRealEncoderBitrate(sizeCalculateResultVideoSize.getWidth(), sizeCalculateResultVideoSize.getHeight(), this.originalBitrate, false);
        } else {
            MediaController.extractRealEncoderBitrate(sizeCalculateResultVideoSize.getWidth(), sizeCalculateResultVideoSize.getHeight(), MediaController.makeVideoBitrate(this.originalHeight, this.originalWidth, this.originalBitrate, sizeCalculateResultVideoSize.getHeight(), sizeCalculateResultVideoSize.getWidth()), false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWidthHeightBitrateForCompression() {
        int iExtractRealEncoderBitrate;
        if (this.compressionsCount <= 0) {
            return;
        }
        if (this.selectedCompression == -2) {
            this.resultWidth = this.originalWidth;
            this.resultHeight = this.originalHeight;
            this.bitrate = -2;
            return;
        }
        if (this.selectedCompression >= this.compressionsCount) {
            this.selectedCompression = this.compressionsCount - 1;
        }
        if (this.sendPhotoType == 1) {
            float fMax = Math.max(800.0f / this.originalWidth, 800.0f / this.originalHeight);
            this.resultWidth = Math.round((this.originalWidth * fMax) / 2.0f) * 2;
            this.resultHeight = Math.round((this.originalHeight * fMax) / 2.0f) * 2;
        } else {
            Size sizeCalculateResultVideoSize = calculateResultVideoSize();
            this.resultWidth = sizeCalculateResultVideoSize.getWidth();
            this.resultHeight = sizeCalculateResultVideoSize.getHeight();
        }
        if (this.bitrate != 0) {
            if (this.sendPhotoType == 1) {
                this.bitrate = 1560000;
                iExtractRealEncoderBitrate = this.bitrate;
            } else if (this.resultWidth == this.originalWidth && this.resultHeight == this.originalHeight) {
                this.bitrate = this.originalBitrate;
                iExtractRealEncoderBitrate = MediaController.extractRealEncoderBitrate(this.resultWidth, this.resultHeight, this.bitrate, false);
            } else {
                this.bitrate = MediaController.makeVideoBitrate(this.originalHeight, this.originalWidth, this.originalBitrate, this.resultHeight, this.resultWidth);
                iExtractRealEncoderBitrate = MediaController.extractRealEncoderBitrate(this.resultWidth, this.resultHeight, this.bitrate, false);
            }
            this.videoFramesSize = (long) (((iExtractRealEncoderBitrate / 8) * this.videoDuration) / 1000.0f);
        }
    }

    private void showQualityView(final boolean z) {
        TextureView textureView;
        if (z && this.textureUploaded && this.videoSizeSet && !this.changingTextureView && (textureView = this.videoTextureView) != null) {
            this.videoFrameBitmap = textureView.getBitmap();
        }
        if (z) {
            this.previousCompression = this.selectedCompression;
        }
        AnimatorSet animatorSet = this.qualityChooseViewAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.qualityChooseViewAnimation = new AnimatorSet();
        if (z) {
            if (this.fancyShadows) {
                this.navigationBar.setVisibility(0);
                this.navigationBar.setAlpha(this.sendPhotoType == 11 ? 1.0f : 0.0f);
                this.navigationBar.setBackgroundColor(this.sendPhotoType == 11 ? 1711276032 : 2130706432);
            }
            this.qualityChooseView.setTag(1);
            AnimatorSet animatorSet2 = this.qualityChooseViewAnimation;
            FrameLayout frameLayout = this.pickerView;
            Property property = View.TRANSLATION_Y;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(frameLayout, (Property<FrameLayout, Float>) property, 0.0f, frameLayout.getHeight() + this.captionEdit.getEditTextHeight() + (this.isCurrentVideo ? AndroidUtilities.m1146dp(58.0f) : 0));
            FrameLayout frameLayout2 = this.pickerView;
            Property property2 = View.ALPHA;
            animatorSet2.playTogether(objectAnimatorOfFloat, ObjectAnimator.ofFloat(frameLayout2, (Property<FrameLayout, Float>) property2, 0.0f), ObjectAnimator.ofFloat(this.pickerViewSendButton, (Property<ChatActivityEnterView.SendButton, Float>) property, 0.0f, AndroidUtilities.m1146dp(158.0f)), ObjectAnimator.ofFloat(this.navigationBar, (Property<View, Float>) property2, this.fancyShadows ? 0.0f : 1.0f, 1.0f));
        } else {
            this.qualityChooseView.setTag(null);
            AnimatorSet animatorSet3 = this.qualityChooseViewAnimation;
            QualityChooseView qualityChooseView = this.qualityChooseView;
            Property property3 = View.TRANSLATION_Y;
            animatorSet3.playTogether(ObjectAnimator.ofFloat(qualityChooseView, (Property<QualityChooseView, Float>) property3, 0.0f, AndroidUtilities.m1146dp(166.0f)), ObjectAnimator.ofFloat(this.qualityPicker, (Property<PickerBottomLayoutViewer, Float>) property3, 0.0f, AndroidUtilities.m1146dp(166.0f)), ObjectAnimator.ofFloat(this.navigationBar, (Property<View, Float>) View.ALPHA, 1.0f, this.fancyShadows ? 0.0f : 1.0f));
        }
        this.qualityChooseViewAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.85
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (animator.equals(PhotoViewer.this.qualityChooseViewAnimation)) {
                    PhotoViewer.this.qualityChooseViewAnimation = new AnimatorSet();
                    if (z) {
                        PhotoViewer.this.qualityChooseView.setVisibility(0);
                        PhotoViewer.this.qualityPicker.setVisibility(0);
                        AnimatorSet animatorSet4 = PhotoViewer.this.qualityChooseViewAnimation;
                        QualityChooseView qualityChooseView2 = PhotoViewer.this.qualityChooseView;
                        Property property4 = View.TRANSLATION_Y;
                        animatorSet4.playTogether(ObjectAnimator.ofFloat(qualityChooseView2, (Property<QualityChooseView, Float>) property4, 0.0f), ObjectAnimator.ofFloat(PhotoViewer.this.qualityPicker, (Property<PickerBottomLayoutViewer, Float>) property4, 0.0f));
                    } else {
                        if (PhotoViewer.this.fancyShadows) {
                            PhotoViewer.this.navigationBar.setVisibility(8);
                            PhotoViewer.this.navigationBar.setAlpha(0.0f);
                            PhotoViewer.this.navigationBar.setBackgroundColor(PhotoViewer.this.sendPhotoType == 11 ? -16777216 : 2130706432);
                        }
                        PhotoViewer.this.qualityChooseView.setVisibility(4);
                        PhotoViewer.this.qualityPicker.setVisibility(4);
                        AnimatorSet animatorSet5 = PhotoViewer.this.qualityChooseViewAnimation;
                        FrameLayout frameLayout3 = PhotoViewer.this.pickerView;
                        Property property5 = View.TRANSLATION_Y;
                        animatorSet5.playTogether(ObjectAnimator.ofFloat(frameLayout3, (Property<FrameLayout, Float>) property5, 0.0f), ObjectAnimator.ofFloat(PhotoViewer.this.pickerView, (Property<FrameLayout, Float>) View.ALPHA, 1.0f), ObjectAnimator.ofFloat(PhotoViewer.this.pickerViewSendButton, (Property<ChatActivityEnterView.SendButton, Float>) property5, 0.0f));
                    }
                    PhotoViewer.this.qualityChooseViewAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PhotoViewer.85.1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator2) {
                            if (animator2.equals(PhotoViewer.this.qualityChooseViewAnimation)) {
                                PhotoViewer.this.qualityChooseViewAnimation = null;
                            }
                        }
                    });
                    PhotoViewer.this.qualityChooseViewAnimation.setDuration(200L);
                    PhotoViewer.this.qualityChooseViewAnimation.setInterpolator(AndroidUtilities.decelerateInterpolator);
                    PhotoViewer.this.qualityChooseViewAnimation.start();
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                PhotoViewer.this.qualityChooseViewAnimation = null;
            }
        });
        this.qualityChooseViewAnimation.setDuration(200L);
        this.qualityChooseViewAnimation.setInterpolator(AndroidUtilities.accelerateInterpolator);
        this.qualityChooseViewAnimation.start();
        if (this.muteButton.getVisibility() == 0) {
            this.muteButton.animate().scaleX(z ? 0.25f : 1.0f).scaleY(z ? 0.25f : 1.0f).alpha(z ? 0.0f : 1.0f).setDuration(200L);
        }
        if (this.editCoverButton.getVisibility() == 0) {
            this.editCoverButton.animate().scaleX(z ? 0.25f : 1.0f).scaleY(z ? 0.25f : 1.0f).alpha(z ? 0.0f : 1.0f).setDuration(200L);
        }
    }

    private void processOpenVideo(String str, boolean z, float f, float f2, int i) {
        if (this.currentLoadingVideoRunnable != null) {
            Utilities.globalQueue.cancelRunnable(this.currentLoadingVideoRunnable);
            this.currentLoadingVideoRunnable = null;
        }
        this.videoTimelineView.setVideoPath(str, f, f2);
        this.videoPreviewMessageObject = null;
        boolean z2 = true;
        if (!z && this.sendPhotoType != 1) {
            z2 = false;
        }
        this.muteVideo = z2;
        this.compressionsCount = -1;
        this.rotationValue = 0;
        this.videoFramerate = 25;
        this.originalSize = new File(str).length();
        DispatchQueue dispatchQueue = Utilities.globalQueue;
        RunnableC586286 runnableC586286 = new RunnableC586286(str, i);
        this.currentLoadingVideoRunnable = runnableC586286;
        dispatchQueue.postRunnable(runnableC586286);
    }

    /* renamed from: org.telegram.ui.PhotoViewer$86 */
    /* loaded from: classes5.dex */
    class RunnableC586286 implements Runnable {
        final /* synthetic */ int val$compressQuality;
        final /* synthetic */ String val$videoPath;

        RunnableC586286(String str, int i) {
            this.val$videoPath = str;
            this.val$compressQuality = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (PhotoViewer.this.currentLoadingVideoRunnable != this) {
                return;
            }
            int videoBitrate = MediaController.getVideoBitrate(this.val$videoPath);
            final int[] iArr = new int[11];
            AnimatedFileDrawable.getVideoInfo(this.val$videoPath, iArr);
            boolean z = false;
            boolean z2 = iArr[10] != 0;
            PhotoViewer photoViewer = PhotoViewer.this;
            if (iArr[0] != 0 && (!z2 || iArr[9] != 0)) {
                z = true;
            }
            photoViewer.videoConvertSupported = z;
            PhotoViewer photoViewer2 = PhotoViewer.this;
            if (videoBitrate == -1) {
                videoBitrate = iArr[3];
            }
            photoViewer2.bitrate = videoBitrate;
            photoViewer2.originalBitrate = videoBitrate;
            if (PhotoViewer.this.videoConvertSupported) {
                PhotoViewer photoViewer3 = PhotoViewer.this;
                int i = iArr[1];
                photoViewer3.originalWidth = i;
                photoViewer3.resultWidth = i;
                PhotoViewer photoViewer4 = PhotoViewer.this;
                int i2 = iArr[2];
                photoViewer4.originalHeight = i2;
                photoViewer4.resultHeight = i2;
                PhotoViewer photoViewer5 = PhotoViewer.this;
                photoViewer5.updateCompressionsCount(photoViewer5.originalWidth, PhotoViewer.this.originalHeight);
                PhotoViewer photoViewer6 = PhotoViewer.this;
                int iSelectCompression = this.val$compressQuality;
                if (iSelectCompression == -1) {
                    iSelectCompression = photoViewer6.selectCompression();
                }
                photoViewer6.selectedCompression = iSelectCompression;
                PhotoViewer.this.prepareRealEncoderBitrate();
                PhotoViewer.this.isH264Video = MediaController.isH264Video(this.val$videoPath);
            }
            if (PhotoViewer.this.currentLoadingVideoRunnable != this) {
                return;
            }
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$86$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$run$0(this, iArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$run$0(Runnable runnable, int[] iArr) {
            if (PhotoViewer.this.parentActivity == null || runnable != PhotoViewer.this.currentLoadingVideoRunnable) {
                return;
            }
            PhotoViewer.this.currentLoadingVideoRunnable = null;
            PhotoViewer.this.audioFramesSize = iArr[5];
            PhotoViewer.this.videoDuration = iArr[4];
            PhotoViewer.this.videoFramerate = iArr[7];
            PhotoViewer.this.videoFramesSize = (long) (((r5.bitrate / 8) * PhotoViewer.this.videoDuration) / 1000.0f);
            if (PhotoViewer.this.videoConvertSupported) {
                PhotoViewer.this.rotationValue = iArr[8];
                PhotoViewer.this.updateWidthHeightBitrateForCompression();
                if (PhotoViewer.this.selectedCompression > PhotoViewer.this.compressionsCount - 1) {
                    PhotoViewer photoViewer = PhotoViewer.this;
                    photoViewer.selectedCompression = photoViewer.compressionsCount - 1;
                }
                PhotoViewer.this.compressItem.setState(PhotoViewer.this.compressionsCount > 1, PhotoViewer.this.muteVideo, Math.min(PhotoViewer.this.resultWidth, PhotoViewer.this.resultHeight));
                if (BuildVars.LOGS_ENABLED) {
                    FileLog.m1157d("compressionsCount = " + PhotoViewer.this.compressionsCount + " w = " + PhotoViewer.this.originalWidth + " h = " + PhotoViewer.this.originalHeight + " r = " + PhotoViewer.this.rotationValue);
                }
                PhotoViewer.this.qualityChooseView.invalidate();
            } else {
                PhotoViewer.this.compressItem.setState(false, PhotoViewer.this.muteVideo, Math.min(PhotoViewer.this.resultWidth, PhotoViewer.this.resultHeight));
                PhotoViewer.this.compressionsCount = 0;
            }
            PhotoViewer.this.updateVideoInfo();
            PhotoViewer.this.updateMuteButton();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int selectCompression() {
        if (this.originalSize > 1048576000) {
            return this.compressionsCount - 1;
        }
        SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
        int i = this.compressionsCount;
        while (i < 8) {
            int i2 = globalMainSettings.getInt(String.format(Locale.US, "compress_video_%d", Integer.valueOf(i)), -1);
            if (i2 >= 0) {
                return Math.min(i2, 5);
            }
            i++;
        }
        return Math.min(5, Math.round(DownloadController.getInstance(this.currentAccount).getMaxVideoBitrate() / (100.0f / i)) - 1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCompressionsCount(int i, int i2) {
        int iMax = Math.max(i, i2);
        if (iMax > 3840) {
            this.compressionsCount = 7;
            return;
        }
        if (iMax > 2560) {
            this.compressionsCount = 6;
            return;
        }
        if (iMax > 1920) {
            this.compressionsCount = 5;
            return;
        }
        if (iMax > 1280) {
            this.compressionsCount = 4;
            return;
        }
        if (iMax > 854) {
            this.compressionsCount = 3;
        } else if (iMax > 480) {
            this.compressionsCount = 2;
        } else {
            this.compressionsCount = 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAccessibilityOverlayVisibility() {
        if (this.playButtonAccessibilityOverlay != null) {
            int i = this.photoProgressViews[0].backgroundState;
            if (this.photoProgressViews[0].isVisible() && (i == 3 || i == 4 || i == 2 || i == 1)) {
                if (i == 3) {
                    this.playButtonAccessibilityOverlay.setContentDescription(LocaleController.getString("AccActionPlay", C2369R.string.AccActionPlay));
                } else if (i == 2) {
                    this.playButtonAccessibilityOverlay.setContentDescription(LocaleController.getString("AccActionDownload", C2369R.string.AccActionDownload));
                } else if (i == 1) {
                    this.playButtonAccessibilityOverlay.setContentDescription(LocaleController.getString("AccActionCancelDownload", C2369R.string.AccActionCancelDownload));
                } else {
                    this.playButtonAccessibilityOverlay.setContentDescription(LocaleController.getString("AccActionPause", C2369R.string.AccActionPause));
                }
                this.playButtonAccessibilityOverlay.setVisibility(0);
                return;
            }
            this.playButtonAccessibilityOverlay.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    class ListAdapter extends RecyclerListView.SelectionAdapter {
        private Context mContext;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return 0;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return false;
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            if (PhotoViewer.this.placeProvider == null || PhotoViewer.this.placeProvider.getSelectedPhotosOrder() == null) {
                return 0;
            }
            return PhotoViewer.this.placeProvider.getSelectedPhotosOrder().size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            PhotoPickerPhotoCell photoPickerPhotoCell = new PhotoPickerPhotoCell(this.mContext);
            photoPickerPhotoCell.checkFrame.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$ListAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$onCreateViewHolder$0(view);
                }
            });
            return new RecyclerListView.Holder(photoPickerPhotoCell);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$0(View view) {
            Object tag = ((View) view.getParent()).getTag();
            int iIndexOf = PhotoViewer.this.imagesArrLocals.indexOf(tag);
            if (iIndexOf >= 0) {
                int photoChecked = PhotoViewer.this.placeProvider.setPhotoChecked(iIndexOf, PhotoViewer.this.getCurrentVideoEditedInfo());
                boolean zIsPhotoChecked = PhotoViewer.this.placeProvider.isPhotoChecked(iIndexOf);
                if (iIndexOf == PhotoViewer.this.currentIndex) {
                    PhotoViewer.this.checkImageView.setChecked(-1, zIsPhotoChecked, true);
                }
                if (photoChecked >= 0) {
                    PhotoViewer.this.selectedPhotosAdapter.notifyItemRemoved(photoChecked);
                    if (photoChecked == 0) {
                        PhotoViewer.this.selectedPhotosAdapter.notifyItemChanged(0);
                    }
                }
                PhotoViewer.this.updateSelectedCount();
                return;
            }
            int photoUnchecked = PhotoViewer.this.placeProvider.setPhotoUnchecked(tag);
            if (photoUnchecked >= 0) {
                PhotoViewer.this.selectedPhotosAdapter.notifyItemRemoved(photoUnchecked);
                if (photoUnchecked == 0) {
                    PhotoViewer.this.selectedPhotosAdapter.notifyItemChanged(0);
                }
                PhotoViewer.this.updateSelectedCount();
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            PhotoPickerPhotoCell photoPickerPhotoCell = (PhotoPickerPhotoCell) viewHolder.itemView;
            photoPickerPhotoCell.setItemWidth(AndroidUtilities.m1146dp(85.0f), i != 0 ? AndroidUtilities.m1146dp(6.0f) : 0);
            BackupImageView backupImageView = photoPickerPhotoCell.imageView;
            backupImageView.setOrientation(0, true);
            Object obj = PhotoViewer.this.placeProvider.getSelectedPhotos().get(PhotoViewer.this.placeProvider.getSelectedPhotosOrder().get(i));
            if (obj instanceof MediaController.PhotoEntry) {
                MediaController.PhotoEntry photoEntry = (MediaController.PhotoEntry) obj;
                photoPickerPhotoCell.setTag(photoEntry);
                photoPickerPhotoCell.videoInfoContainer.setVisibility(4);
                String str = photoEntry.thumbPath;
                if (str != null) {
                    backupImageView.setImage(str, null, this.mContext.getResources().getDrawable(C2369R.drawable.nophotos));
                } else if (photoEntry.path != null) {
                    backupImageView.setOrientation(photoEntry.orientation, photoEntry.invert, true);
                    if (photoEntry.isVideo) {
                        photoPickerPhotoCell.videoInfoContainer.setVisibility(0);
                        photoPickerPhotoCell.videoTextView.setText(AndroidUtilities.formatShortDuration(photoEntry.duration));
                        backupImageView.setImage("vthumb://" + photoEntry.imageId + ":" + photoEntry.path, null, this.mContext.getResources().getDrawable(C2369R.drawable.nophotos));
                    } else {
                        backupImageView.setImage("thumb://" + photoEntry.imageId + ":" + photoEntry.path, null, this.mContext.getResources().getDrawable(C2369R.drawable.nophotos));
                    }
                } else {
                    backupImageView.setImageResource(C2369R.drawable.nophotos);
                }
                photoPickerPhotoCell.setChecked(-1, true, false);
                photoPickerPhotoCell.checkBox.setVisibility(0);
                return;
            }
            if (obj instanceof MediaController.SearchImage) {
                MediaController.SearchImage searchImage = (MediaController.SearchImage) obj;
                photoPickerPhotoCell.setTag(searchImage);
                photoPickerPhotoCell.setImage(searchImage);
                photoPickerPhotoCell.videoInfoContainer.setVisibility(4);
                photoPickerPhotoCell.setChecked(-1, true, false);
                photoPickerPhotoCell.checkBox.setVisibility(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    class FirstFrameView extends ImageView {
        private VideoPlayer currentVideoPlayer;
        private ValueAnimator fadeAnimator;
        private final TimeInterpolator fadeInterpolator;
        private boolean gettingFrame;
        private int gettingFrameIndex;
        private boolean gotError;
        private boolean hasFrame;

        public FirstFrameView(Context context) {
            super(context);
            this.gettingFrameIndex = 0;
            this.gettingFrame = false;
            this.hasFrame = false;
            this.gotError = false;
            this.fadeInterpolator = CubicBezierInterpolator.EASE_IN;
            setAlpha(0.0f);
        }

        public void clear() {
            this.hasFrame = false;
            this.gotError = false;
            if (this.gettingFrame) {
                this.gettingFrameIndex++;
                this.gettingFrame = false;
            }
            setImageResource(R.color.transparent);
        }

        public void checkFromPlayer(VideoPlayer videoPlayer) {
            if (this.currentVideoPlayer != videoPlayer) {
                this.gotError = false;
                clear();
            }
            if (videoPlayer != null && !videoPlayer.isHDR()) {
                long duration = videoPlayer.getDuration() - videoPlayer.getCurrentPosition();
                if (!this.hasFrame && !this.gotError && !this.gettingFrame && duration < 5250.0f) {
                    final Uri currentUri = videoPlayer.getCurrentUri();
                    final int i = this.gettingFrameIndex + 1;
                    this.gettingFrameIndex = i;
                    Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.PhotoViewer$FirstFrameView$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$checkFromPlayer$2(currentUri, i);
                        }
                    });
                    this.gettingFrame = true;
                }
            }
            this.currentVideoPlayer = videoPlayer;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$checkFromPlayer$2(Uri uri, final int i) {
            try {
                File file = new File(uri.getPath());
                int i2 = UserConfig.selectedAccount;
                Point point = AndroidUtilities.displaySize;
                AnimatedFileDrawable animatedFileDrawable = new AnimatedFileDrawable(file, true, 0L, 0, null, null, null, 0L, i2, false, point.x, point.y, null);
                final Bitmap frameAtTime = animatedFileDrawable.getFrameAtTime(0L);
                animatedFileDrawable.recycle();
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$FirstFrameView$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$checkFromPlayer$0(i, frameAtTime);
                    }
                });
            } catch (Throwable th) {
                FileLog.m1160e(th);
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PhotoViewer$FirstFrameView$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$checkFromPlayer$1();
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$checkFromPlayer$0(int i, Bitmap bitmap) {
            if (i == this.gettingFrameIndex) {
                setImageBitmap(bitmap);
                this.hasFrame = true;
                this.gettingFrame = false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$checkFromPlayer$1() {
            this.gotError = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateAlpha() {
            if (PhotoViewer.this.videoPlayer == null || PhotoViewer.this.videoPlayer.getDuration() == -9223372036854775807L) {
                ValueAnimator valueAnimator = this.fadeAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    this.fadeAnimator = null;
                }
                setAlpha(0.0f);
                return;
            }
            long jMax = Math.max(0L, PhotoViewer.this.videoPlayer.getDuration() - PhotoViewer.this.videoPlayer.getCurrentPosition());
            float fMax = 1.0f - Math.max(Math.min(jMax / 250.0f, 1.0f), 0.0f);
            if (fMax <= 0.0f) {
                ValueAnimator valueAnimator2 = this.fadeAnimator;
                if (valueAnimator2 != null) {
                    valueAnimator2.cancel();
                    this.fadeAnimator = null;
                }
                setAlpha(0.0f);
                return;
            }
            if (PhotoViewer.this.videoPlayer.isPlaying()) {
                if (this.fadeAnimator == null) {
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(fMax, 1.0f);
                    this.fadeAnimator = valueAnimatorOfFloat;
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PhotoViewer$FirstFrameView$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                            this.f$0.lambda$updateAlpha$3(valueAnimator3);
                        }
                    });
                    this.fadeAnimator.setDuration(jMax);
                    this.fadeAnimator.setInterpolator(this.fadeInterpolator);
                    this.fadeAnimator.start();
                    setAlpha(fMax);
                    return;
                }
                return;
            }
            ValueAnimator valueAnimator3 = this.fadeAnimator;
            if (valueAnimator3 != null) {
                valueAnimator3.cancel();
                this.fadeAnimator = null;
            }
            setAlpha(fMax);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateAlpha$3(ValueAnimator valueAnimator) {
            setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getThemedColor(int i) {
        Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
        if (resourcesProvider != null) {
            return resourcesProvider.getColor(i);
        }
        return Theme.getColor(i);
    }

    public boolean BLUR_RENDERNODE() {
        return !this.textureViewSkipRender && Build.VERSION.SDK_INT >= 31 && SharedConfig.useNewBlur && SharedConfig.getDevicePerformanceClass() >= 2 && !AndroidUtilities.makingGlobalBlurBitmap;
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x032e  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x033d  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x03a5  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x03d5  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0585  */
    /* JADX WARN: Removed duplicated region for block: B:242:0x0757  */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0797  */
    /* JADX WARN: Removed duplicated region for block: B:269:0x079a  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x07c0  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x081f  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x0842  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x086e  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x08b6  */
    /* JADX WARN: Removed duplicated region for block: B:348:0x08ba  */
    /* JADX WARN: Removed duplicated region for block: B:357:0x08f4  */
    /* JADX WARN: Removed duplicated region for block: B:360:0x08ff  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x0956  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x099b  */
    /* JADX WARN: Removed duplicated region for block: B:385:0x09b7  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x09be  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x09c4  */
    /* JADX WARN: Removed duplicated region for block: B:390:0x09c8  */
    /* JADX WARN: Removed duplicated region for block: B:403:0x09ed  */
    /* JADX WARN: Removed duplicated region for block: B:405:0x09f0  */
    /* JADX WARN: Removed duplicated region for block: B:407:0x09fe  */
    /* JADX WARN: Removed duplicated region for block: B:410:0x0a06  */
    /* JADX WARN: Removed duplicated region for block: B:419:0x0a32  */
    /* JADX WARN: Removed duplicated region for block: B:420:0x0a34  */
    /* JADX WARN: Removed duplicated region for block: B:421:0x0ae6  */
    /* JADX WARN: Removed duplicated region for block: B:424:0x0af2  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x0afc  */
    /* JADX WARN: Removed duplicated region for block: B:431:0x0b61  */
    /* JADX WARN: Removed duplicated region for block: B:476:0x0ce9  */
    /* JADX WARN: Removed duplicated region for block: B:478:0x0ced  */
    /* JADX WARN: Removed duplicated region for block: B:481:0x0cfc  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0246  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x028b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void drawCaptionBlur(android.graphics.Canvas r49, org.telegram.ui.Components.BlurringShader.StoryBlurDrawer r50, int r51, int r52, boolean r53, boolean r54, boolean r55) {
        /*
            Method dump skipped, instructions count: 3515
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.drawCaptionBlur(android.graphics.Canvas, org.telegram.ui.Components.BlurringShader$StoryBlurDrawer, int, int, boolean, boolean, boolean):void");
    }

    private void cancelStickerClippingMode() {
        if (this.sendPhotoType == 11 && this.cutOutBtn.isCancelState()) {
            this.cutOutBtn.setCutOutState(true);
            showEditStickerMode(true, true);
            this.stickerMakerView.disableClippingMode();
            this.containerView.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateBlur() {
        StickerMakerView stickerMakerView = this.stickerMakerView;
        if (stickerMakerView == null || !stickerMakerView.isThanosInProgress) {
            CaptionPhotoViewer captionPhotoViewer = this.captionEdit;
            if (captionPhotoViewer != null) {
                captionPhotoViewer.invalidateBlur();
            }
            CaptionPhotoViewer captionPhotoViewer2 = this.topCaptionEdit;
            if (captionPhotoViewer2 != null) {
                captionPhotoViewer2.invalidateBlur();
            }
            BlurButton blurButton = this.cutOutBtn;
            if (blurButton != null) {
                blurButton.invalidateBlur();
            }
            BlurButton blurButton2 = this.eraseBtn;
            if (blurButton2 != null) {
                blurButton2.invalidateBlur();
            }
            BlurButton blurButton3 = this.restoreBtn;
            if (blurButton3 != null) {
                blurButton3.invalidateBlur();
            }
            BlurButton blurButton4 = this.undoBtn;
            if (blurButton4 != null) {
                blurButton4.invalidateBlur();
            }
            BlurButton blurButton5 = this.outlineBtn;
            if (blurButton5 != null) {
                blurButton5.invalidateBlur();
            }
            VideoTimelinePlayView videoTimelinePlayView = this.videoTimelineView;
            if (videoTimelinePlayView != null) {
                videoTimelinePlayView.invalidateBlur();
            }
            FrameLayoutDrawer frameLayoutDrawer = this.containerView;
            if (frameLayoutDrawer != null) {
                frameLayoutDrawer.invalidate();
            }
        }
    }

    /* loaded from: classes5.dex */
    private class BlurButton extends StickerCutOutBtn {
        private boolean active;
        private final AnimatedFloat activeFloat;
        private final Path path;

        public BlurButton() {
            super(PhotoViewer.this.stickerMakerView, PhotoViewer.this.activityContext, PhotoViewer.this.resourcesProvider, PhotoViewer.this.blurManager);
            this.path = new Path();
            this.activeFloat = new AnimatedFloat(this, 0L, 420L, CubicBezierInterpolator.EASE_OUT_QUINT);
        }

        @Override // org.telegram.p023ui.Components.Paint.Views.StickerCutOutBtn, org.telegram.p023ui.Stories.recorder.ButtonWithCounterView, android.view.View
        protected void onDraw(Canvas canvas) {
            canvas.save();
            this.path.rewind();
            this.path.addRoundRect(this.bounds, AndroidUtilities.m1146dp(this.rad), AndroidUtilities.m1146dp(this.rad), Path.Direction.CW);
            canvas.clipPath(this.path);
            canvas.translate(-getX(), -getY());
            if (this == PhotoViewer.this.eraseBtn || this == PhotoViewer.this.restoreBtn) {
                canvas.translate(-PhotoViewer.this.btnLayout.getX(), -PhotoViewer.this.btnLayout.getY());
            }
            PhotoViewer.this.drawCaptionBlur(canvas, this.blurDrawer, -13948117, AndroidUtilities.DARK_STATUS_BAR_OVERLAY, false, true, false);
            float f = this.activeFloat.set(this.active);
            if (f > 0.0f) {
                canvas.drawColor(Theme.multAlpha(-1, f));
            }
            setTextColor(ColorUtils.blendARGB(-1, -16777216, f));
            canvas.restore();
            super.onDraw(canvas);
        }

        @Override // android.view.View
        public void onDrawForeground(Canvas canvas) {
            canvas.save();
            canvas.clipPath(this.path);
            super.onDrawForeground(canvas);
            canvas.restore();
        }

        public void setActive(boolean z, boolean z2) {
            this.active = z;
            if (!z2) {
                this.activeFloat.set(z, true);
            }
            invalidate();
        }

        public boolean isActive() {
            return this.active;
        }
    }

    private void applyTransformToOutline(Canvas canvas) {
        float fLerp;
        float fLerp2;
        float fLerp3;
        float fLerp4;
        float fLerp5;
        float f;
        MediaController.CropState cropState;
        if (this.imageMoveAnimation != null) {
            fLerp2 = AndroidUtilities.lerp(this.mirror, this.animateToMirror, this.animationValue);
            fLerp = AndroidUtilities.lerp(this.scale, this.animateToScale, this.animationValue);
            fLerp3 = AndroidUtilities.lerp(this.rotate, this.animateToRotate, this.animationValue);
            fLerp4 = AndroidUtilities.lerp(this.translationY, this.animateToY, this.animationValue);
            fLerp5 = AndroidUtilities.lerp(this.translationX, this.animateToX, this.animationValue);
        } else {
            fLerp = this.scale;
            fLerp2 = this.mirror;
            fLerp3 = this.rotate;
            fLerp4 = this.translationY;
            fLerp5 = this.translationX;
            if (this.animationStartTime != 0) {
                fLerp5 = this.animateToX;
                fLerp4 = this.animateToY;
                fLerp = this.animateToScale;
            }
        }
        int containerViewWidth = getContainerViewWidth();
        int containerViewHeight = getContainerViewHeight();
        canvas.translate(getAdditionX(this.currentEditMode), getAdditionY(this.currentEditMode));
        canvas.translate(fLerp5, fLerp4 + (this.currentEditMode != 3 ? this.currentPanTranslationY : 0.0f));
        canvas.scale(fLerp, fLerp);
        canvas.rotate(fLerp3);
        int bitmapWidth = this.centerImage.getBitmapWidth();
        int bitmapHeight = this.centerImage.getBitmapHeight();
        float f2 = containerViewWidth;
        float f3 = bitmapWidth;
        float f4 = f2 / f3;
        float f5 = containerViewHeight;
        float f6 = bitmapHeight;
        float f7 = f5 / f6;
        float fMin = Math.min(f4, f7);
        int orientation = this.cropTransform.getOrientation();
        if (orientation == 90 || orientation == 270) {
            bitmapHeight = bitmapWidth;
            bitmapWidth = bitmapHeight;
        }
        float cropPw = this.cropTransform.getCropPw();
        float cropPh = this.cropTransform.getCropPh();
        float f8 = bitmapWidth;
        float f9 = fLerp2;
        float f10 = bitmapHeight;
        float f11 = (int) ((cropPw + ((1.0f - cropPw) * 0.0f)) * f8);
        float f12 = f2 / f11;
        float f13 = (int) ((cropPh + ((1.0f - cropPh) * 0.0f)) * f10);
        if (f12 * f13 > f5) {
            f12 = f5 / f13;
        }
        float f14 = fLerp;
        if (this.sendPhotoType != 1 && ((this.currentEditMode != 1 || this.switchingToMode == 0) && this.editState.cropState != null)) {
            float f15 = f11 * f12;
            float f16 = f13 * f12;
            if (f4 * f6 > f5) {
                f4 = f7;
            }
            float f17 = f15 + ((((f3 * f4) / f14) - f15) * 0.0f);
            float f18 = f16 + ((((f6 * f4) / f14) - f16) * 0.0f);
            canvas.clipRect((-f17) / 2.0f, (-f18) / 2.0f, f17 / 2.0f, f18 / 2.0f);
        }
        if (this.sendPhotoType == 1 || this.cropTransform.hasViewTransform()) {
            TextureView textureView = this.videoTextureView;
            if (textureView != null) {
                MediaController.CropState cropState2 = this.editState.cropState;
                textureView.setScaleX((cropState2 == null || !cropState2.mirrored) ? 1.0f : -1.0f);
                FirstFrameView firstFrameView = this.firstFrameView;
                if (firstFrameView != null) {
                    firstFrameView.setScaleX(this.videoTextureView.getScaleX());
                }
            }
            MediaController.CropState cropState3 = this.editState.cropState;
            float f19 = cropState3 != null ? cropState3.cropScale : 1.0f;
            float f20 = f19 * ((f12 / fMin) / (((f19 - 1.0f) * 0.0f) + 1.0f));
            canvas.translate(this.cropTransform.getCropAreaX() * 1.0f, this.cropTransform.getCropAreaY() * 1.0f);
            canvas.scale(f20, f20);
            canvas.translate(this.cropTransform.getCropPx() * f8 * fMin * 1.0f, this.cropTransform.getCropPy() * f10 * fMin * 1.0f);
            float rotation = this.cropTransform.getRotation() + orientation;
            if (rotation > 180.0f) {
                rotation -= 360.0f;
            }
            canvas.rotate(rotation);
        }
        if (this.imagesArrLocals.isEmpty() || (cropState = this.editState.cropState) == null || !cropState.mirrored) {
            f = 1.0f;
        } else {
            f = 1.0f;
            canvas.scale(-1.0f, 1.0f);
        }
        if (f9 > 0.0f) {
            canvas.scale(f - (f9 * 2.0f), f);
            canvas.skew(0.0f, 4.0f * f9 * (f - f9) * 0.25f);
        }
    }

    private void applyTransformToMatrix(Matrix matrix) {
        float fLerp;
        float fLerp2;
        float fLerp3;
        float fLerp4;
        float fLerp5;
        MediaController.CropState cropState;
        if (this.imageMoveAnimation != null) {
            fLerp2 = AndroidUtilities.lerp(this.mirror, this.animateToMirror, this.animationValue);
            fLerp = AndroidUtilities.lerp(this.scale, this.animateToScale, this.animationValue);
            fLerp3 = AndroidUtilities.lerp(this.rotate, this.animateToRotate, this.animationValue);
            fLerp4 = AndroidUtilities.lerp(this.translationY, this.animateToY, this.animationValue);
            fLerp5 = AndroidUtilities.lerp(this.translationX, this.animateToX, this.animationValue);
        } else {
            fLerp = this.scale;
            fLerp2 = this.mirror;
            fLerp3 = this.rotate;
            fLerp4 = this.translationY;
            fLerp5 = this.translationX;
            if (this.animationStartTime != 0) {
                fLerp5 = this.animateToX;
                fLerp4 = this.animateToY;
                fLerp = this.animateToScale;
            }
        }
        int containerViewWidth = getContainerViewWidth();
        int containerViewHeight = getContainerViewHeight();
        matrix.preTranslate(fLerp5, fLerp4 + (this.currentEditMode != 3 ? this.currentPanTranslationY : 0.0f));
        matrix.preScale(fLerp, fLerp);
        matrix.preRotate(fLerp3);
        int bitmapWidth = this.centerImage.getBitmapWidth();
        int bitmapHeight = this.centerImage.getBitmapHeight();
        float f = containerViewWidth;
        float f2 = containerViewHeight;
        float fMin = Math.min(f / bitmapWidth, f2 / bitmapHeight);
        int orientation = this.cropTransform.getOrientation();
        if (orientation == 90 || orientation == 270) {
            bitmapHeight = bitmapWidth;
            bitmapWidth = bitmapHeight;
        }
        float cropPw = this.cropTransform.getCropPw();
        float cropPh = this.cropTransform.getCropPh();
        float f3 = bitmapWidth;
        float f4 = bitmapHeight;
        float f5 = f / ((int) ((cropPw + ((1.0f - cropPw) * 0.0f)) * f3));
        float f6 = (int) ((cropPh + ((1.0f - cropPh) * 0.0f)) * f4);
        if (f5 * f6 > f2) {
            f5 = f2 / f6;
        }
        if (this.sendPhotoType == 1 || this.cropTransform.hasViewTransform()) {
            TextureView textureView = this.videoTextureView;
            if (textureView != null) {
                MediaController.CropState cropState2 = this.editState.cropState;
                textureView.setScaleX((cropState2 == null || !cropState2.mirrored) ? 1.0f : -1.0f);
                FirstFrameView firstFrameView = this.firstFrameView;
                if (firstFrameView != null) {
                    firstFrameView.setScaleX(this.videoTextureView.getScaleX());
                }
            }
            MediaController.CropState cropState3 = this.editState.cropState;
            float f7 = cropState3 != null ? cropState3.cropScale : 1.0f;
            float f8 = f7 * ((f5 / fMin) / (((f7 - 1.0f) * 0.0f) + 1.0f));
            matrix.preTranslate(this.cropTransform.getCropAreaX() * 1.0f, this.cropTransform.getCropAreaY() * 1.0f);
            matrix.preScale(f8, f8);
            matrix.preTranslate(this.cropTransform.getCropPx() * f3 * fMin * 1.0f, this.cropTransform.getCropPy() * f4 * fMin * 1.0f);
            float rotation = this.cropTransform.getRotation() + orientation;
            if (rotation > 180.0f) {
                rotation -= 360.0f;
            }
            matrix.preRotate(rotation);
        }
        if (!this.imagesArrLocals.isEmpty() && (cropState = this.editState.cropState) != null && cropState.mirrored) {
            matrix.preScale(-1.0f, 1.0f);
        }
        if (fLerp2 > 0.0f) {
            matrix.preScale(1.0f - (2.0f * fLerp2), 1.0f);
            matrix.preSkew(0.0f, 4.0f * fLerp2 * (1.0f - fLerp2) * 0.25f);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public File getPhoto() {
        File pathToAttach;
        File pathToAttach2;
        TLRPC.WebPage webPage;
        MessageObject messageObject = this.currentMessageObject;
        boolean z = true;
        if (messageObject != null) {
            TLRPC.MessageMedia messageMedia = messageObject.messageOwner.media;
            if ((messageMedia instanceof TLRPC.TL_messageMediaWebPage) && (webPage = messageMedia.webpage) != null && webPage.document == null) {
                return FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(getFileLocation(this.currentIndex, null), true);
            }
            return FileLoader.getInstance(UserConfig.selectedAccount).getPathToMessage(this.currentMessageObject.messageOwner);
        }
        if (isCurrentAvatarSet()) {
            ArrayList arrayList = new ArrayList(((TLRPC.Photo) this.avatarsArr.get(this.switchingToIndex)).sizes);
            List.EL.sort(arrayList, new Comparator() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda151
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return Integer.compare(((TLRPC.PhotoSize) obj2).size, ((TLRPC.PhotoSize) obj).size);
                }
            });
            if (!arrayList.isEmpty() && arrayList.get(0) != null) {
                File pathToAttach3 = FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(((TLRPC.PhotoSize) arrayList.get(0)).location, true);
                if (pathToAttach3 != null) {
                    return pathToAttach3;
                }
            }
            if (!this.imagesArrLocationsVideo.isEmpty() && this.imagesArrLocationsVideo.get(0) != null) {
                File pathToAttach4 = FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(((ImageLocation) this.imagesArrLocationsVideo.get(0)).location, true);
                if (pathToAttach4 != null) {
                    return pathToAttach4;
                }
            }
            if (this.currentAvatarLocation != null && (pathToAttach2 = FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(this.currentAvatarLocation.location, true)) != null) {
                return pathToAttach2;
            }
            if (this.currentFileLocation == null || (pathToAttach = FileLoader.getInstance(UserConfig.selectedAccount).getPathToAttach(this.currentFileLocation.location, true)) == null) {
                return null;
            }
            return pathToAttach;
        }
        if (this.currentFileLocationVideo != null) {
            FileLoader fileLoader = FileLoader.getInstance(UserConfig.selectedAccount);
            TLRPC.FileLocation fileLocation = getFileLocation(this.currentFileLocationVideo);
            String fileLocationExt = getFileLocationExt(this.currentFileLocationVideo);
            if ((Objects.equals(getFileLocationExt(this.currentFileLocationVideo), "mp4") || this.avatarsDialogId == 0) && !this.isEvent) {
                z = false;
            }
            return fileLoader.getPathToAttach(fileLocation, fileLocationExt, z);
        }
        PageBlocksAdapter pageBlocksAdapter = this.pageBlocksAdapter;
        if (pageBlocksAdapter != null) {
            return pageBlocksAdapter.getFile(this.currentIndex);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0215  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void openAdsMenu() {
        /*
            Method dump skipped, instructions count: 705
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.openAdsMenu():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openAdsMenu$156(ItemOptions itemOptions, View view) {
        if (this.currentMessageObject == null) {
            return;
        }
        itemOptions.dismiss();
        BaseFragment baseFragment = this.parentFragment;
        if (baseFragment instanceof ChatActivity) {
            ((ChatActivity) baseFragment).logSponsoredClicked(this.currentMessageObject, false, true);
        }
        Browser.openUrl(this.activityContext, Uri.parse(this.currentMessageObject.sponsoredUrl), true, false, false, null, null, false, MessagesController.getInstance(this.currentAccount).sponsoredLinksInappAllow, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$openAdsMenu$157(Theme.ResourcesProvider resourcesProvider, View view) {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null) {
            return false;
        }
        if (!AndroidUtilities.addToClipboard(messageObject.sponsoredUrl)) {
            return true;
        }
        BulletinFactory.m1266of(Bulletin.BulletinWindow.make(this.activityContext), resourcesProvider).createCopyLinkBulletin().show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openAdsMenu$158(Theme.ResourcesProvider resourcesProvider, View view) {
        if (AndroidUtilities.addToClipboard(this.currentMessageObject.sponsoredInfo)) {
            BulletinFactory.m1266of(Bulletin.BulletinWindow.make(this.activityContext), resourcesProvider).createCopyBulletin(LocaleController.getString(C2369R.string.TextCopied)).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openAdsMenu$159(Theme.ResourcesProvider resourcesProvider, View view) {
        if (AndroidUtilities.addToClipboard(this.currentMessageObject.sponsoredAdditionalInfo)) {
            BulletinFactory.m1266of(Bulletin.BulletinWindow.make(this.activityContext), resourcesProvider).createCopyBulletin(LocaleController.getString(C2369R.string.TextCopied)).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openAdsMenu$161(int i, Theme.ResourcesProvider resourcesProvider) {
        if (UserConfig.getInstance(i).isPremium()) {
            BulletinFactory.m1266of(this.containerView, resourcesProvider).createAdReportedBulletin(LocaleController.getString(C2369R.string.AdHidden)).show();
            MessagesController.getInstance(i).disableAds(true);
            BaseFragment baseFragment = this.parentFragment;
            if (baseFragment instanceof ChatActivity) {
                ChatActivity chatActivity = (ChatActivity) baseFragment;
                chatActivity.removeFromSponsored(this.currentMessageObject);
                chatActivity.removeMessageWithThanos(this.currentMessageObject);
                return;
            }
            return;
        }
        new PremiumFeatureBottomSheet(this.parentFragment, 3, true).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openAdsMenu$162(Theme.ResourcesProvider resourcesProvider) {
        RevenueSharingAdsInfoBottomSheet.showAlert(this.activityContext, this.parentFragment, false, resourcesProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openAdsMenu$163(int i, Theme.ResourcesProvider resourcesProvider) {
        if (UserConfig.getInstance(i).isPremium()) {
            BulletinFactory.m1266of(this.containerView, resourcesProvider).createAdReportedBulletin(LocaleController.getString(C2369R.string.AdHidden)).show();
            MessagesController.getInstance(i).disableAds(true);
            BaseFragment baseFragment = this.parentFragment;
            if (baseFragment instanceof ChatActivity) {
                ChatActivity chatActivity = (ChatActivity) baseFragment;
                chatActivity.removeFromSponsored(this.currentMessageObject);
                chatActivity.removeMessageWithThanos(this.currentMessageObject);
                return;
            }
            return;
        }
        new PremiumFeatureBottomSheet(this.parentFragment, 3, true).show();
    }

    private static CharSequence sponsoredCaption(MessageObject messageObject, CharSequence charSequence) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (!TextUtils.isEmpty(messageObject.sponsoredTitle)) {
            spannableStringBuilder.append((CharSequence) messageObject.sponsoredTitle);
            spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold()), 0, spannableStringBuilder.length(), 33);
            spannableStringBuilder.setSpan(new LineHeightSpan() { // from class: org.telegram.ui.PhotoViewer.87
                @Override // android.text.style.LineHeightSpan
                public void chooseHeight(CharSequence charSequence2, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
                    fontMetricsInt.descent += AndroidUtilities.m1146dp(4.0f);
                    fontMetricsInt.ascent = fontMetricsInt.ascent;
                }
            }, 0, spannableStringBuilder.length(), 33);
            spannableStringBuilder.append((CharSequence) "\n");
        }
        spannableStringBuilder.append(charSequence);
        return spannableStringBuilder;
    }

    private void createAdButtonView() {
        if (this.adButtonView != null) {
            return;
        }
        FrameLayout frameLayout = new FrameLayout(this.activityContext);
        this.adButtonView = frameLayout;
        frameLayout.setBackground(Theme.createRadSelectorDrawable(620756991, 369098751, 8, 8));
        ScaleStateListAnimator.apply(this.adButtonView, 0.05f, 1.25f);
        TextView textView = new TextView(this.activityContext);
        this.adButtonTextView = textView;
        textView.setTextSize(1, 14.0f);
        this.adButtonTextView.setTextColor(-1);
        this.adButtonTextView.setTypeface(AndroidUtilities.bold());
        this.adButtonView.addView(this.adButtonTextView, LayoutHelper.createFrame(-2, -2, 17));
        this.adButtonView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda89
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws NumberFormatException {
                this.f$0.lambda$createAdButtonView$164(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createAdButtonView$164(View view) throws NumberFormatException {
        MessageObject messageObject = this.currentMessageObject;
        if (messageObject == null || !messageObject.isSponsored()) {
            return;
        }
        BaseFragment baseFragment = this.parentFragment;
        if (baseFragment instanceof ChatActivity) {
            ((ChatActivity) baseFragment).logSponsoredClicked(this.currentMessageObject, false, true);
        }
        closePhoto(true, false);
        String str = this.currentMessageObject.sponsoredUrl;
        if (str != null) {
            Context context = LaunchActivity.instance;
            if (context == null) {
                context = this.activityContext;
            }
            Browser.openUrl(context, Uri.parse(str), true, false, false, null, null, false, MessagesController.getInstance(this.currentAccount).sponsoredLinksInappAllow, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void chooseSpeed(float f, boolean z, boolean z2) {
        if (f != this.currentVideoSpeed) {
            this.currentVideoSpeed = f;
            if (this.currentMessageObject != null) {
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("playback_speed", 0);
                if (Math.abs(this.currentVideoSpeed - 1.0f) < 0.001f) {
                    sharedPreferences.edit().remove("speed" + this.currentMessageObject.getDialogId() + "_" + this.currentMessageObject.getId()).commit();
                } else {
                    sharedPreferences.edit().putFloat("speed" + this.currentMessageObject.getDialogId() + "_" + this.currentMessageObject.getId(), this.currentVideoSpeed).commit();
                }
            }
            VideoPlayer videoPlayer = this.videoPlayer;
            if (videoPlayer != null) {
                videoPlayer.setPlaybackSpeed(this.currentVideoSpeed);
            }
            PhotoViewerWebView photoViewerWebView = this.photoViewerWebView;
            if (photoViewerWebView != null) {
                photoViewerWebView.setPlaybackSpeed(this.currentVideoSpeed);
            }
        }
        setMenuItemIcon(true, z);
        if (z2) {
            this.videoItem.toggleSubMenu();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00a8  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00ce  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void toggleCaptionAbove() {
        /*
            Method dump skipped, instructions count: 300
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.toggleCaptionAbove():void");
    }

    public static float getSavedProgressFast(MessageObject messageObject) {
        int duration = (int) messageObject.getDuration();
        String fileNameFast = messageObject.isEmbedVideo() ? messageObject.messageOwner.media.webpage.url : messageObject.getFileNameFast();
        if (!TextUtils.isEmpty(fileNameFast) && duration >= 10) {
            SavedVideoPosition savedVideoPosition = (SavedVideoPosition) savedVideoPositions.get(fileNameFast);
            if (messageObject.forceSeekTo < 0.0f && savedVideoPosition != null) {
                float f = savedVideoPosition.position;
                if (f > 0.0f && f < 0.999f) {
                    return f;
                }
            }
        }
        return 0.0f;
    }

    public static float getSavedProgress(MessageObject messageObject) {
        int duration = (int) messageObject.getDuration();
        String fileNameFast = messageObject.isEmbedVideo() ? messageObject.messageOwner.media.webpage.url : messageObject.getFileNameFast();
        if (!TextUtils.isEmpty(fileNameFast)) {
            if (duration >= 10) {
                SavedVideoPosition savedVideoPosition = (SavedVideoPosition) savedVideoPositions.get(fileNameFast);
                if (messageObject.forceSeekTo < 0.0f && savedVideoPosition != null) {
                    float f = savedVideoPosition.position;
                    if (f > 0.0f && f < 0.999f) {
                        return f;
                    }
                }
            }
            if (duration >= 120 && messageObject.forceSeekTo < 0.0f) {
                float f2 = ApplicationLoader.applicationContext.getSharedPreferences("media_saved_pos", 0).getFloat(fileNameFast, -1.0f);
                if (f2 > 0.0f && f2 < 0.999f) {
                    return f2;
                }
            }
        }
        return 0.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.telegram.messenger.chromecast.ChromecastMediaVariations getCurrentChromecastMedia() {
        /*
            r7 = this;
            org.telegram.messenger.MessageObject r0 = r7.currentMessageObject
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            org.telegram.tgnet.TLRPC$Document r0 = r0.getDocument()
            org.telegram.messenger.MessageObject r2 = r7.currentMessageObject
            java.lang.String r2 = r2.getDocumentName()
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 == 0) goto L1c
            org.telegram.messenger.MessageObject r2 = r7.currentMessageObject
            java.lang.String r2 = r2.getFileName()
        L1c:
            org.telegram.messenger.MessageObject r3 = r7.currentMessageObject
            long r3 = r3.getDialogId()
            boolean r5 = org.telegram.messenger.DialogObject.isUserDialog(r3)
            if (r5 == 0) goto L41
            int r5 = org.telegram.messenger.UserConfig.selectedAccount
            org.telegram.messenger.MessagesController r5 = org.telegram.messenger.MessagesController.getInstance(r5)
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            org.telegram.tgnet.TLRPC$User r3 = r5.getUser(r3)
            if (r3 == 0) goto L55
            java.lang.String r4 = r3.first_name
            java.lang.String r3 = r3.last_name
            java.lang.String r3 = org.telegram.messenger.ContactsController.formatName(r4, r3)
            goto L56
        L41:
            int r5 = org.telegram.messenger.UserConfig.selectedAccount
            org.telegram.messenger.MessagesController r5 = org.telegram.messenger.MessagesController.getInstance(r5)
            long r3 = -r3
            java.lang.Long r3 = java.lang.Long.valueOf(r3)
            org.telegram.tgnet.TLRPC$Chat r3 = r5.getChat(r3)
            if (r3 == 0) goto L55
            java.lang.String r3 = r3.title
            goto L56
        L55:
            r3 = r1
        L56:
            org.telegram.messenger.MessageObject r4 = r7.currentMessageObject
            boolean r4 = r4.isPhoto()
            if (r4 == 0) goto Lbf
            org.telegram.messenger.MessageObject r0 = r7.currentMessageObject
            int r0 = r0.currentAccount
            org.telegram.messenger.FileLoader r0 = org.telegram.messenger.FileLoader.getInstance(r0)
            org.telegram.messenger.MessageObject r4 = r7.currentMessageObject
            org.telegram.tgnet.TLRPC$Message r4 = r4.messageOwner
            java.io.File r0 = r0.getPathToMessage(r4)
            if (r0 == 0) goto Lbe
            boolean r4 = r0.exists()
            if (r4 != 0) goto L77
            goto Lbe
        L77:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "file://"
            r1.append(r4)
            java.lang.String r0 = r0.getAbsolutePath()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            android.net.Uri r0 = android.net.Uri.parse(r0)
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "/photo_"
            r1.append(r4)
            org.telegram.messenger.MessageObject r4 = r7.currentMessageObject
            int r4 = r4.getId()
            r1.append(r4)
            java.lang.String r1 = r1.toString()
            java.lang.String r4 = "image/jpeg"
            org.telegram.messenger.chromecast.ChromecastMedia$Builder r0 = org.telegram.messenger.chromecast.ChromecastMedia.Builder.fromUri(r0, r1, r4)
            org.telegram.messenger.chromecast.ChromecastMedia$Builder r0 = r0.setTitle(r3)
            org.telegram.messenger.chromecast.ChromecastMedia$Builder r0 = r0.setSubtitle(r2)
            org.telegram.messenger.chromecast.ChromecastMedia r0 = r0.build()
            org.telegram.messenger.chromecast.ChromecastMediaVariations r0 = org.telegram.messenger.chromecast.ChromecastMediaVariations.m1210of(r0)
            return r0
        Lbe:
            return r1
        Lbf:
            org.telegram.ui.Components.VideoPlayer r4 = r7.videoPlayer
            if (r4 == 0) goto Le5
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            if (r0 == 0) goto Lcd
            long r5 = r0.f1579id
            goto Ld4
        Lcd:
            org.telegram.messenger.MessageObject r0 = r7.currentMessageObject
            int r0 = r0.getId()
            long r5 = (long) r0
        Ld4:
            r1.append(r5)
            java.lang.String r0 = ""
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            org.telegram.messenger.chromecast.ChromecastMediaVariations r0 = r4.getCurrentChromecastMedia(r0, r3, r2)
            return r0
        Le5:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PhotoViewer.getCurrentChromecastMedia():org.telegram.messenger.chromecast.ChromecastMediaVariations");
    }

    public void syncCastedPlayer() {
        if (isVisible()) {
            this.ignorePlayerUpdate = true;
            VideoPlayer videoPlayer = this.videoPlayer;
            if (videoPlayer != null) {
                videoPlayer.setMute(CastSync.isActive() || this.muteVideo);
            }
            if (this.videoPlayer != null && CastSync.isActive() && !CastSync.isUpdatePending()) {
                long position = CastSync.getPosition();
                if (position >= 0 && Math.abs(this.videoPlayer.getCurrentPosition() - position) > 1000) {
                    this.videoPlayer.seekTo(position);
                }
                if (CastSync.isPlaying()) {
                    this.videoPlayer.play();
                } else {
                    this.videoPlayer.pause();
                }
                if (this.activityContext != null && Math.abs(CastSync.getDeviceVolume() - CastSync.getVolume()) > 0.05f) {
                    AudioManager audioManager = (AudioManager) this.activityContext.getSystemService(MediaStreamTrack.AUDIO_TRACK_KIND);
                    int streamMaxVolume = audioManager.getStreamMaxVolume(3);
                    int streamMinVolume = (Build.VERSION.SDK_INT >= 28 ? audioManager.getStreamMinVolume(3) : 0) + ((int) ((streamMaxVolume - r5) * CastSync.getVolume()));
                    if (streamMinVolume != audioManager.getStreamVolume(3)) {
                        audioManager.setStreamVolume(3, streamMinVolume, 1);
                    }
                }
                chooseSpeed(CastSync.getSpeed(), true, false);
            }
            ChooseQualityLayout$QualityIcon chooseQualityLayout$QualityIcon = this.videoItemIcon;
            if (chooseQualityLayout$QualityIcon != null) {
                chooseQualityLayout$QualityIcon.setCasting(CastSync.isActive(), true);
            }
            this.ignorePlayerUpdate = false;
        }
    }

    public long getCurrentPosition() {
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer == null) {
            return -1L;
        }
        return videoPlayer.getCurrentPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pipInvalidateAvailability() {
        PipSource pipSource = this.pipSource;
        if (pipSource != null) {
            pipSource.invalidateAvailability();
        }
        if (PipVideoOverlay.getPipSource() != null) {
            PipVideoOverlay.getPipSource().invalidateAvailability();
        }
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public boolean pipIsAvailable() {
        ActionBarMenuSubItem actionBarMenuSubItem = this.pipItem;
        return actionBarMenuSubItem != null && actionBarMenuSubItem.isEnabled() && this.isPlaying;
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public Bitmap pipCreatePrimaryWindowViewBitmap() {
        TextureView textureView = this.videoTextureView;
        if (textureView != null) {
            return textureView.getBitmap();
        }
        if (!this.usedSurfaceView || Build.VERSION.SDK_INT < 24) {
            return null;
        }
        Bitmap bitmapCreateBitmap = Bitmaps.createBitmap(this.videoSurfaceView.getWidth(), this.videoSurfaceView.getHeight(), Bitmap.Config.ARGB_8888);
        AndroidUtilities.getBitmapFromSurface(this.videoSurfaceView, bitmapCreateBitmap);
        return bitmapCreateBitmap;
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public void pipRenderBackground(Canvas canvas) {
        canvas.drawColor(-16777216);
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public void pipRenderForeground(Canvas canvas) {
        this.textureViewSkipRender = true;
        this.windowView.draw(canvas);
        this.textureViewSkipRender = false;
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public View pipCreatePictureInPictureView() {
        TextureView textureView = new TextureView(this.parentActivity);
        this.pipTextureView = textureView;
        textureView.setOpaque(false);
        View view = this.pipPlaceholderView;
        if (view != null) {
            view.bringToFront();
        }
        return this.pipTextureView;
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public void pipHidePrimaryWindowView(Runnable runnable) {
        if (PipVideoOverlay.isVisible()) {
            PipVideoOverlay.dismiss(false);
        }
        this.pipFirstFrameCallback = runnable;
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer != null) {
            videoPlayer.setSurfaceView(null);
            this.videoPlayer.setTextureView(null);
            this.videoPlayer.play();
            this.videoPlayer.setTextureView(this.pipTextureView);
        }
        ((WindowManager) this.parentActivity.getSystemService("window")).removeView(this.windowView);
        this.windowViewSkipRender = true;
        this.windowView.invalidate();
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public Bitmap pipCreatePictureInPictureViewBitmap() {
        TextureView textureView = this.pipTextureView;
        if (textureView == null || !textureView.isAvailable()) {
            return null;
        }
        return this.pipTextureView.getBitmap();
    }

    @Override // org.telegram.messenger.pip.source.IPipSourceDelegate
    public void pipShowPrimaryWindowView(Runnable runnable) {
        this.pipFirstFrameCallback = runnable;
        this.windowViewSkipRender = false;
        if (this.windowView != null) {
            ((WindowManager) this.parentActivity.getSystemService("window")).addView(this.windowView, this.windowLayoutParams);
            this.windowView.invalidate();
        }
        VideoPlayer videoPlayer = this.videoPlayer;
        if (videoPlayer == null) {
            return;
        }
        videoPlayer.setSurfaceView(null);
        this.videoPlayer.setTextureView(null);
        this.videoPlayer.play();
        TextureView textureView = this.videoTextureView;
        if (textureView != null) {
            this.videoPlayer.setTextureView(textureView);
            return;
        }
        SurfaceView surfaceView = this.videoSurfaceView;
        if (surfaceView != null) {
            this.videoPlayer.setSurfaceView(surfaceView);
        }
    }

    private void showPhotoQualityHint(boolean z) {
        HintView2 hintView2 = this.compressPhotoHint;
        if (hintView2 != null) {
            hintView2.hide();
            this.compressPhotoHint = null;
        }
        if (this.activityContext == null) {
            return;
        }
        this.compressPhotoHint = new HintView2(this.activityContext, 3);
        SpannableStringBuilder spannableStringBuilderAppend = new SpannableStringBuilder("x ").append((CharSequence) LocaleController.getString(z ? C2369R.string.PhotoWillBeSentInHD : C2369R.string.PhotoWillBeSentInSD));
        spannableStringBuilderAppend.setSpan(new ColoredImageSpan(z ? C2369R.drawable.menu_quality_hd_filled : C2369R.drawable.menu_quality_sd_filled), 0, 1, 33);
        this.compressPhotoHint.setText(spannableStringBuilderAppend);
        this.containerView.addView(this.compressPhotoHint, LayoutHelper.createFrame(-1, 100.0f, 87, 0.0f, 0.0f, 0.0f, 48.0f));
        this.compressPhotoHint.setTranslationY(this.pickerView.getTranslationY());
        this.compressPhotoHint.setJointPx(0.0f, this.itemsLayout.getX() + this.compressItem.getX() + (this.compressItem.getWidth() / 2.0f));
        final HintView2 hintView22 = this.compressPhotoHint;
        hintView22.setOnHiddenListener(new Runnable() { // from class: org.telegram.ui.PhotoViewer$$ExternalSyntheticLambda116
            @Override // java.lang.Runnable
            public final void run() {
                AndroidUtilities.removeFromParent(hintView22);
            }
        });
        this.compressPhotoHint.setDuration(3500L);
        this.compressPhotoHint.show();
    }
}
