package org.telegram.p023ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.util.LongSparseArray;
import android.util.Property;
import android.util.StateSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScrollerCustom;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.components.TranslateBeforeSendWrapper;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.exteragram.messenger.utils.AppUtils;
import com.exteragram.messenger.utils.p011ui.CanvasUtils;
import com.exteragram.messenger.utils.system.VibratorUtils;
import com.exteragram.messenger.utils.text.LocaleUtils;
import com.exteragram.messenger.utils.text.TranslatorUtils;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.AyuState;
import com.radolyn.ayugram.controllers.AyuGhostController;
import com.radolyn.ayugram.p015ui.AlertUtils;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AnimationNotificationsLocker;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BirthdayController;
import org.telegram.messenger.BotWebViewVibrationEffect;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.FilesMigrationService;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LiteMode;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.NotificationsSettingsFacade;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.XiaomiUtilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.ActionBarMenu;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.p023ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BackDrawable;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.INavigationLayout;
import org.telegram.p023ui.ActionBar.MenuDrawable;
import org.telegram.p023ui.ActionBar.SimpleTextView;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Adapters.DialogsAdapter;
import org.telegram.p023ui.Adapters.DialogsSearchAdapter;
import org.telegram.p023ui.Adapters.FiltersView;
import org.telegram.p023ui.Cells.ActiveGiftAuctionsHintCell;
import org.telegram.p023ui.Cells.BaseCell;
import org.telegram.p023ui.Cells.DialogCell;
import org.telegram.p023ui.Cells.DialogsHintCell;
import org.telegram.p023ui.Cells.DrawerProfileCell;
import org.telegram.p023ui.Cells.GraySectionCell;
import org.telegram.p023ui.Cells.HeaderCell;
import org.telegram.p023ui.Cells.ProfileSearchCell;
import org.telegram.p023ui.Cells.RequestPeerRequirementsCell;
import org.telegram.p023ui.Cells.TextCell;
import org.telegram.p023ui.Cells.UnconfirmedAuthHintCell;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.Components.AlertsCreator;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.AnimationProperties;
import org.telegram.p023ui.Components.ArchiveHelp;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.BlurredRecyclerView;
import org.telegram.p023ui.Components.Bulletin;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.ChatActivityEnterView;
import org.telegram.p023ui.Components.ChatAvatarContainer;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.DialogsItemAnimator;
import org.telegram.p023ui.Components.FilterTabsView;
import org.telegram.p023ui.Components.FiltersListBottomSheet;
import org.telegram.p023ui.Components.FlickerLoadingView;
import org.telegram.p023ui.Components.FloatingDebug.FloatingDebugController;
import org.telegram.p023ui.Components.FloatingDebug.FloatingDebugProvider;
import org.telegram.p023ui.Components.FolderBottomSheet;
import org.telegram.p023ui.Components.FolderDrawable;
import org.telegram.p023ui.Components.ForegroundColorSpanThemable;
import org.telegram.p023ui.Components.Forum.ForumUtilities;
import org.telegram.p023ui.Components.FragmentContextView;
import org.telegram.p023ui.Components.ImageUpdater;
import org.telegram.p023ui.Components.ItemOptions;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.MediaActivity;
import org.telegram.p023ui.Components.NumberTextView;
import org.telegram.p023ui.Components.PacmanAnimation;
import org.telegram.p023ui.Components.PermissionRequest;
import org.telegram.p023ui.Components.PopupSwipeBackLayout;
import org.telegram.p023ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p023ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p023ui.Components.Premium.boosts.UserSelectorBottomSheet;
import org.telegram.p023ui.Components.ProxyDrawable;
import org.telegram.p023ui.Components.PullForegroundDrawable;
import org.telegram.p023ui.Components.RLottieDrawable;
import org.telegram.p023ui.Components.RLottieImageView;
import org.telegram.p023ui.Components.RadialProgress2;
import org.telegram.p023ui.Components.RadialProgressView;
import org.telegram.p023ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p023ui.Components.RecyclerAnimationScrollHelper;
import org.telegram.p023ui.Components.RecyclerItemsEnterAnimator;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.SearchViewPager;
import org.telegram.p023ui.Components.SharedMediaLayout;
import org.telegram.p023ui.Components.SizeNotifierFrameLayout;
import org.telegram.p023ui.Components.UndoView;
import org.telegram.p023ui.Components.ViewPagerFixed;
import org.telegram.p023ui.Components.inset.WindowInsetsStateHolder;
import org.telegram.p023ui.DialogsActivity;
import org.telegram.p023ui.FilterCreateActivity;
import org.telegram.p023ui.FilteredSearchView;
import org.telegram.p023ui.Gifts.GiftSheet;
import org.telegram.p023ui.GroupCreateFinalActivity;
import org.telegram.p023ui.PhotoViewer;
import org.telegram.p023ui.SelectAnimatedEmojiDialog;
import org.telegram.p023ui.Stars.StarGiftSheet;
import org.telegram.p023ui.Stars.StarsController;
import org.telegram.p023ui.Stories.DialogStoriesCell;
import org.telegram.p023ui.Stories.StealthModeAlert;
import org.telegram.p023ui.Stories.StoriesController;
import org.telegram.p023ui.Stories.StoriesListPlaceProvider;
import org.telegram.p023ui.Stories.UserListPoller;
import org.telegram.p023ui.Stories.recorder.HintView2;
import org.telegram.p023ui.Stories.recorder.StoryRecorder;
import org.telegram.p023ui.bots.BotWebViewSheet;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_account;
import org.telegram.tgnet.p022tl.TL_chatlists;
import org.telegram.tgnet.p022tl.TL_stars;
import org.telegram.tgnet.p022tl.TL_stories;

/* loaded from: classes.dex */
public class DialogsActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate, FloatingDebugProvider {
    public static boolean[] dialogsLoaded = new boolean[16];
    private static final Interpolator interpolator = new Interpolator() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda46
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return DialogsActivity.$r8$lambda$1MKEGXpjH9HrtwN1ZClzWIcweWE(f);
        }
    };
    public static float viewOffset = 0.0f;
    private final String ACTION_MODE_SEARCH_DIALOGS_TAG;
    public final Property SCROLL_Y;
    public final Property SEARCH_TRANSLATION_Y;
    private ValueAnimator actionBarColorAnimator;
    private final Paint actionBarDefaultPaint;
    private String actionBarDefaultTitle;
    private int actionModeAdditionalHeight;
    private boolean actionModeFullyShowed;
    private final ArrayList actionModeViews;
    private float activeGiftAuctionsHintCellProgress;
    private boolean activeGiftAuctionsHintCellVisible;
    private ActionBarMenuSubItem addToFolderItem;
    private String addToGroupAlertString;
    private float additionalFloatingTranslation;
    private float additionalFloatingTranslation2;
    private float additionalOffset;
    private boolean afterSignup;
    public boolean allowBots;
    public boolean allowChannels;
    private boolean allowGlobalSearch;
    public boolean allowGroups;
    public boolean allowLegacyGroups;
    public boolean allowMegagroups;
    private boolean allowMoving;
    private boolean allowSwipeDuringCurrentTouch;
    private boolean allowSwitchAccount;
    public boolean allowUsers;
    private boolean animateToHasStories;
    private DrawerProfileCell.AnimatedStatusView animatedStatusView;
    private boolean animatingForward;
    private ActionBarMenuItem archive2Item;
    private ActionBarMenuSubItem archiveItem;
    private boolean askAboutContacts;
    private boolean askingForPermissions;
    private UnconfirmedAuthHintCell authHintCell;
    private boolean authHintCellAnimating;
    private ValueAnimator authHintCellAnimator;
    private float authHintCellProgress;
    private boolean authHintCellVisible;
    private TLRPC.FileLocation avatar;
    private TLRPC.FileLocation avatarBig;
    private ChatAvatarContainer avatarContainer;
    private int avatarUploadingRequest;
    private boolean backAnimation;
    private BackDrawable backDrawable;
    private ActionBarMenuSubItem blockItem;
    private View blurredView;
    private ArrayList botShareDialogs;
    private Long cacheSize;
    private int canClearCacheCount;
    private boolean canDeletePsaSelected;
    private int canMuteCount;
    private int canPinCount;
    private int canReadCount;
    private int canReportSpamCount;
    private boolean canSelectTopics;
    private boolean canShowFilterTabsView;
    private boolean canShowHiddenArchive;
    private boolean canShowStoryHint;
    private int canUnarchiveCount;
    private int canUnmuteCount;
    private boolean cantSendToChannels;
    private boolean checkCanWrite;
    private boolean checkPermission;
    private boolean checkingImportDialog;
    private ActionBarMenuSubItem clearItem;
    private boolean closeFragment;
    private boolean closeSearchFieldOnHide;
    private ChatActivityEnterView commentView;
    private final boolean commentViewAnimated;
    private AnimatorSet commentViewAnimator;
    private View commentViewBg;
    private boolean commentViewIgnoreTopUpdate;
    private int commentViewPreviousTop;
    private float contactsAlpha;
    private ValueAnimator contactsAlphaAnimator;
    private int currentConnectionState;
    View databaseMigrationHint;
    private int debugLastUpdateAction;
    private DialogsActivityDelegate delegate;
    private ActionBarMenuItem deleteItem;
    private Long deviceSize;
    public DialogStoriesCell dialogStoriesCell;
    public boolean dialogStoriesCellVisible;
    private DialogsHintCell dialogsHintCell;
    private boolean dialogsHintCellVisible;
    private boolean dialogsListFrozen;
    private boolean disableActionBarScrolling;
    private ActionBarMenuItem doneItem;
    private AnimatorSet doneItemAnimator;
    private ActionBarMenuItem downloadsItem;
    private boolean downloadsItemVisible;
    private ItemOptions filterOptions;
    private float filterTabsMoveFrom;
    private float filterTabsProgress;
    private FilterTabsView filterTabsView;
    private boolean filterTabsViewIsVisible;
    private ValueAnimator filtersTabAnimator;
    private FiltersView filtersView;
    private boolean fixScrollYAfterArchiveOpened;
    private RadialProgressView floating2ProgressView;
    private RLottieImageView floatingButton;
    private RLottieImageView floatingButton2;
    private FrameLayout floatingButton2Container;
    private FrameLayout floatingButtonContainer;
    private float floatingButtonHideProgress;
    private float floatingButtonPanOffset;
    private float floatingButtonTranslation;
    private boolean floatingForceVisible;
    private boolean floatingHidden;
    private final AccelerateDecelerateInterpolator floatingInterpolator;
    private AnimatorSet floatingProgressAnimator;
    private boolean floatingProgressVisible;
    private int folderId;
    private int forumCount;
    public long forwardOriginalChannel;
    private int fragmentContextTopPadding;
    private FragmentContextView fragmentContextView;
    private FragmentContextView fragmentLocationContextView;
    private ArrayList frozenDialogsList;
    private AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable ghostStatusDrawable;
    private boolean hasInvoice;
    public boolean hasOnlySlefStories;
    private int hasPoll;
    public boolean hasStories;
    private ImageUpdater imageUpdater;
    private int initialDialogsType;
    private String initialSearchString;
    private int initialSearchType;
    private boolean invalidateScrollY;
    boolean isDrawerTransition;
    private boolean isFirstTab;
    private boolean isNextButton;
    public boolean isQuote;
    public boolean isReplyTo;
    boolean isSlideBackTransition;
    private int lastMeasuredTopPadding;
    private int maximumVelocity;
    private boolean maybeStartTracking;
    private MenuDrawable menuDrawable;
    private int messagesCount;
    private final ArrayList movingDialogFilters;
    private DialogCell movingView;
    private boolean movingWas;
    private ActionBarMenuItem muteItem;
    private AnimationNotificationsLocker notificationsLocker;
    public boolean notify;
    private boolean onlySelect;
    private MessagesStorage.TopicKey openedDialogId;
    private ActionBarMenuItem optionsItem;
    private int otherwiseReloginDays;
    private PacmanAnimation pacmanAnimation;
    private final Paint paint;
    float panTranslationY;
    private RLottieDrawable passcodeDrawable;
    private ActionBarMenuItem passcodeItem;
    private boolean passcodeItemVisible;
    private AlertDialog permissionDialog;
    private ActionBarMenuSubItem pin2Item;
    private ActionBarMenuItem pinItem;
    private Drawable premiumStar;
    private int prevPosition;
    private int prevTop;
    private float progressToActionMode;
    public float progressToDialogStoriesCell;
    public float progressToShowStories;
    private ProxyDrawable proxyDrawable;
    private ActionBarMenuItem proxyItem;
    private boolean proxyItemVisible;
    private ActionBarMenuSubItem readItem;
    private final RectF rect;
    private ActionBarMenuSubItem removeFromFolderItem;
    public long replyMessageAuthor;
    private long requestPeerBotId;
    private TLRPC.RequestPeerType requestPeerType;
    public boolean resetDelegate;
    private boolean rightFragmentTransitionInProgress;
    private boolean rightFragmentTransitionIsOpen;
    public RightSlidingDialogContainer rightSlidingDialogContainer;
    public int scheduleDate;
    private float scrollAdditionalOffset;
    private boolean scrollBarVisible;
    private boolean scrollUpdated;
    private float scrollYOffset;
    private boolean scrollingManually;
    private float searchAnimationProgress;
    private boolean searchAnimationTabsDelayedCrossfade;
    private AnimatorSet searchAnimator;
    private long searchDialogId;
    private boolean searchFiltersWasShowed;
    private boolean searchIsShowed;
    private ActionBarMenuItem searchItem;
    private TLObject searchObject;
    private String searchString;
    private ViewPagerFixed.TabsView searchTabsView;
    private SearchViewPager searchViewPager;
    private int searchViewPagerIndex;
    float searchViewPagerTranslationY;
    private boolean searchWas;
    private boolean searchWasFullyShowed;
    private boolean searching;
    private String selectAlertString;
    private String selectAlertStringGroup;
    private SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialog;
    private ArrayList selectedDialogs;
    private NumberTextView selectedDialogsCountTextView;
    private ActionBarPopupWindow sendPopupWindow;
    private SharedMediaLayout.SharedMediaPreloader sharedMediaPreloader;
    private int shiftDp;
    private boolean showSetPasswordConfirm;
    private String showingSuggestion;
    private RecyclerView sideMenu;
    final int slideAmplitudeDp;
    ValueAnimator slideBackTransitionAnimator;
    boolean slideFragmentLite;
    float slideFragmentProgress;
    private DialogCell slidingView;
    private boolean slowedReloadAfterDialogClick;
    private AnimatorSet speedAnimator;
    private ActionBarMenuItem speedItem;
    private long startArchivePullingTime;
    private boolean startedTracking;
    private AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable statusDrawable;
    private Long statusDrawableGiftId;
    private Bulletin storiesBulletin;
    public boolean storiesEnabled;
    private float storiesOverscroll;
    private boolean storiesOverscrollCalled;
    ValueAnimator storiesVisibilityAnimator;
    ValueAnimator storiesVisibilityAnimator2;
    private float storiesYOffset;
    private HintView2 storyHint;
    private boolean storyHintShown;
    private HintView2 storyPremiumHint;
    private ActionBarMenuItem switchItem;
    private Animator tabsAlphaAnimator;
    private AnimatorSet tabsAnimation;
    private boolean tabsAnimationInProgress;
    private float tabsYOffset;
    private final TextPaint textPaint;
    private Bulletin topBulletin;
    private int topPadding;
    private UndoView[] undoView;
    private int undoViewIndex;
    private FrameLayout updateLayout;
    private AnimatorSet updateLayoutAnimator;
    private RadialProgress2 updateLayoutIcon;
    private boolean updatePullAfterScroll;
    private TextView updateTextView;
    private Bulletin uploadingAvatarBulletin;
    private ViewPage[] viewPages;
    private boolean waitingForScrollFinished;
    private boolean wasDrawn;
    public boolean whiteActionBar;
    private final WindowInsetsStateHolder windowInsetsStateHolder;
    private ChatActivityEnterView.SendButton writeButton;

    /* renamed from: -$$Nest$fgetactiveGiftAuctionsHintCell, reason: not valid java name */
    static /* bridge */ /* synthetic */ ActiveGiftAuctionsHintCell m12877$$Nest$fgetactiveGiftAuctionsHintCell(DialogsActivity dialogsActivity) {
        dialogsActivity.getClass();
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCommentView() {
    }

    public boolean shouldShowNextButton(DialogsActivity dialogsActivity, ArrayList arrayList, CharSequence charSequence, boolean z) {
        return false;
    }

    public MessagesStorage.TopicKey getOpenedDialogId() {
        return this.openedDialogId;
    }

    public class ViewPage extends FrameLayout {
        public boolean animateStoriesView;
        private DialogsAdapter animationSupportDialogsAdapter;
        private RecyclerListView animationSupportListView;
        private int archivePullViewState;
        private DialogsAdapter dialogsAdapter;
        private DialogsItemAnimator dialogsItemAnimator;
        private int dialogsType;
        private boolean isLocked;
        private ItemTouchHelper itemTouchhelper;
        private int lastItemsCount;
        private LinearLayoutManager layoutManager;
        public DialogsRecyclerView listView;
        public int pageAdditionalOffset;
        private FlickerLoadingView progressView;
        private PullForegroundDrawable pullForegroundDrawable;
        private RecyclerItemsEnterAnimator recyclerItemsEnterAnimator;
        Runnable saveScrollPositionRunnable;
        private RecyclerAnimationScrollHelper scrollHelper;
        public RecyclerListViewScroller scroller;
        private int selectedType;
        private SwipeController swipeController;
        Runnable updateListRunnable;
        boolean updating;

        public ViewPage(Context context) {
            super(context);
            this.saveScrollPositionRunnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$ViewPage$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$0();
                }
            };
            this.updateListRunnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$ViewPage$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$1();
                }
            };
        }

        public boolean isDefaultDialogType() {
            int i = this.dialogsType;
            return i == 0 || i == 7 || i == 8;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0() {
            DialogsRecyclerView dialogsRecyclerView = this.listView;
            if (dialogsRecyclerView == null || dialogsRecyclerView.getScrollState() != 0 || this.listView.getChildCount() <= 0 || this.listView.getLayoutManager() == null) {
                return;
            }
            int i = 1;
            boolean z = this.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive() && this.archivePullViewState == 2;
            float f = DialogsActivity.this.scrollYOffset;
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.listView.getLayoutManager();
            View view = null;
            int top = ConnectionsManager.DEFAULT_DATACENTER_ID;
            int i2 = -1;
            for (int i3 = 0; i3 < this.listView.getChildCount(); i3++) {
                DialogsRecyclerView dialogsRecyclerView2 = this.listView;
                int childAdapterPosition = dialogsRecyclerView2.getChildAdapterPosition(dialogsRecyclerView2.getChildAt(i3));
                View childAt = this.listView.getChildAt(i3);
                if (childAdapterPosition != -1 && childAt != null && childAt.getTop() < top) {
                    top = childAt.getTop();
                    i2 = childAdapterPosition;
                    view = childAt;
                }
            }
            if (view != null) {
                float top2 = view.getTop() - this.listView.getPaddingTop();
                if (DialogsActivity.this.hasStories) {
                    f = 0.0f;
                }
                if (this.listView.getScrollState() != 1) {
                    if (z && i2 == 0 && ((this.listView.getPaddingTop() - view.getTop()) - view.getMeasuredHeight()) + f < 0.0f) {
                        top2 = f;
                    } else {
                        i = i2;
                    }
                    linearLayoutManager.scrollToPositionWithOffset(i, (int) top2);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1() {
            this.dialogsAdapter.updateList(this.saveScrollPositionRunnable);
            DialogsActivity.this.invalidateScrollY = true;
            DialogsRecyclerView dialogsRecyclerView = this.listView;
            dialogsRecyclerView.updateDialogsOnNextDraw = true;
            this.updating = false;
            dialogsRecyclerView.invalidate();
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.listView.getLayoutParams();
            if (this.animateStoriesView) {
                layoutParams.bottomMargin = -AndroidUtilities.m1146dp(85.0f);
            } else {
                layoutParams.bottomMargin = 0;
            }
            super.onMeasure(i, i2);
        }

        public void updateList(boolean z) {
            if (((BaseFragment) DialogsActivity.this).isPaused) {
                return;
            }
            if (z) {
                AndroidUtilities.cancelRunOnUIThread(this.updateListRunnable);
                this.listView.setItemAnimator(this.dialogsItemAnimator);
                this.updateListRunnable.run();
            } else {
                if (this.updating) {
                    return;
                }
                this.updating = true;
                if (!this.dialogsItemAnimator.isRunning()) {
                    this.listView.setItemAnimator(null);
                }
                AndroidUtilities.runOnUIThread(this.updateListRunnable, 36L);
            }
        }
    }

    public static /* synthetic */ float $r8$lambda$1MKEGXpjH9HrtwN1ZClzWIcweWE(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2 * f2 * f2) + 1.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class ContentView extends SizeNotifierFrameLayout {
        private final Paint actionBarSearchPaint;
        private Rect blurBounds;
        private int inputFieldHeight;
        private int[] pos;
        private int startedTrackingPointerId;
        private int startedTrackingX;
        private int startedTrackingY;
        private VelocityTracker velocityTracker;
        private boolean wasPortrait;
        private final Paint windowBackgroundPaint;

        @Override // android.view.View
        public boolean hasOverlappingRendering() {
            return false;
        }

        @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayout
        protected boolean invalidateOptimized() {
            return true;
        }

        public ContentView(Context context) {
            super(context);
            this.actionBarSearchPaint = new Paint(1);
            this.windowBackgroundPaint = new Paint();
            this.pos = new int[2];
            this.blurBounds = new Rect();
            this.needBlur = true;
            this.blurBehindViews.add(this);
        }

        private boolean prepareForMoving(MotionEvent motionEvent, boolean z) {
            int nextPageId = DialogsActivity.this.filterTabsView.getNextPageId(z);
            if (nextPageId < 0) {
                return false;
            }
            getParent().requestDisallowInterceptTouchEvent(true);
            DialogsActivity.this.maybeStartTracking = false;
            DialogsActivity.this.startedTracking = true;
            this.startedTrackingX = (int) (motionEvent.getX() + DialogsActivity.this.additionalOffset);
            ((BaseFragment) DialogsActivity.this).actionBar.setEnabled(false);
            DialogsActivity.this.filterTabsView.setEnabled(false);
            DialogsActivity.this.viewPages[1].selectedType = nextPageId;
            DialogsActivity.this.viewPages[1].setVisibility(0);
            DialogsActivity.this.animatingForward = z;
            DialogsActivity.this.showScrollbars(false);
            DialogsActivity.this.switchToCurrentSelectedMode(true);
            if (z) {
                DialogsActivity.this.viewPages[1].setTranslationX(DialogsActivity.this.viewPages[0].getMeasuredWidth());
            } else {
                DialogsActivity.this.viewPages[1].setTranslationX(-DialogsActivity.this.viewPages[0].getMeasuredWidth());
            }
            return true;
        }

        @Override // android.view.View
        public void setPadding(int i, int i2, int i3, int i4) {
            DialogsActivity.this.fragmentContextTopPadding = i2;
            DialogsActivity.this.updateTopPadding();
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x00a9  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean checkTabsAnimationInProgress() {
            /*
                r7 = this;
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                boolean r0 = org.telegram.p023ui.DialogsActivity.m12978$$Nest$fgettabsAnimationInProgress(r0)
                r1 = 0
                if (r0 == 0) goto Lc4
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                boolean r0 = org.telegram.p023ui.DialogsActivity.m12892$$Nest$fgetbackAnimation(r0)
                r2 = -1
                r3 = 0
                r4 = 1065353216(0x3f800000, float:1.0)
                r5 = 1
                if (r0 == 0) goto L59
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p023ui.DialogsActivity.m12988$$Nest$fgetviewPages(r0)
                r0 = r0[r1]
                float r0 = r0.getTranslationX()
                float r0 = java.lang.Math.abs(r0)
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 >= 0) goto Lbd
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p023ui.DialogsActivity.m12988$$Nest$fgetviewPages(r0)
                r0 = r0[r1]
                r0.setTranslationX(r3)
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p023ui.DialogsActivity.m12988$$Nest$fgetviewPages(r0)
                r0 = r0[r5]
                org.telegram.ui.DialogsActivity r3 = org.telegram.p023ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r3 = org.telegram.p023ui.DialogsActivity.m12988$$Nest$fgetviewPages(r3)
                r3 = r3[r1]
                int r3 = r3.getMeasuredWidth()
                org.telegram.ui.DialogsActivity r4 = org.telegram.p023ui.DialogsActivity.this
                boolean r4 = org.telegram.p023ui.DialogsActivity.m12884$$Nest$fgetanimatingForward(r4)
                if (r4 == 0) goto L52
                r2 = 1
            L52:
                int r3 = r3 * r2
                float r2 = (float) r3
                r0.setTranslationX(r2)
                goto L9c
            L59:
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p023ui.DialogsActivity.m12988$$Nest$fgetviewPages(r0)
                r0 = r0[r5]
                float r0 = r0.getTranslationX()
                float r0 = java.lang.Math.abs(r0)
                int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
                if (r0 >= 0) goto Lbd
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p023ui.DialogsActivity.m12988$$Nest$fgetviewPages(r0)
                r0 = r0[r1]
                org.telegram.ui.DialogsActivity r4 = org.telegram.p023ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r4 = org.telegram.p023ui.DialogsActivity.m12988$$Nest$fgetviewPages(r4)
                r4 = r4[r1]
                int r4 = r4.getMeasuredWidth()
                org.telegram.ui.DialogsActivity r6 = org.telegram.p023ui.DialogsActivity.this
                boolean r6 = org.telegram.p023ui.DialogsActivity.m12884$$Nest$fgetanimatingForward(r6)
                if (r6 == 0) goto L8a
                goto L8b
            L8a:
                r2 = 1
            L8b:
                int r4 = r4 * r2
                float r2 = (float) r4
                r0.setTranslationX(r2)
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                org.telegram.ui.DialogsActivity$ViewPage[] r0 = org.telegram.p023ui.DialogsActivity.m12988$$Nest$fgetviewPages(r0)
                r0 = r0[r5]
                r0.setTranslationX(r3)
            L9c:
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                org.telegram.p023ui.DialogsActivity.m13085$$Nest$mshowScrollbars(r0, r5)
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                android.animation.AnimatorSet r0 = org.telegram.p023ui.DialogsActivity.m12977$$Nest$fgettabsAnimation(r0)
                if (r0 == 0) goto Lb8
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                android.animation.AnimatorSet r0 = org.telegram.p023ui.DialogsActivity.m12977$$Nest$fgettabsAnimation(r0)
                r0.cancel()
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                r2 = 0
                org.telegram.p023ui.DialogsActivity.m13051$$Nest$fputtabsAnimation(r0, r2)
            Lb8:
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                org.telegram.p023ui.DialogsActivity.m13052$$Nest$fputtabsAnimationInProgress(r0, r1)
            Lbd:
                org.telegram.ui.DialogsActivity r0 = org.telegram.p023ui.DialogsActivity.this
                boolean r0 = org.telegram.p023ui.DialogsActivity.m12978$$Nest$fgettabsAnimationInProgress(r0)
                return r0
            Lc4:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.DialogsActivity.ContentView.checkTabsAnimationInProgress():boolean");
        }

        public int getActionBarFullHeight() {
            float f = 0.0f;
            float height = ((BaseFragment) DialogsActivity.this).actionBar.getHeight() + (((DialogsActivity.this.filterTabsView == null || DialogsActivity.this.filterTabsView.getVisibility() == 8) ? 0.0f : DialogsActivity.this.filterTabsView.getMeasuredHeight() - ((1.0f - DialogsActivity.this.filterTabsProgress) * DialogsActivity.this.filterTabsView.getMeasuredHeight())) * (1.0f - DialogsActivity.this.searchAnimationProgress)) + (((DialogsActivity.this.searchTabsView == null || DialogsActivity.this.searchTabsView.getVisibility() == 8) ? 0.0f : DialogsActivity.this.searchTabsView.getMeasuredHeight()) * DialogsActivity.this.searchAnimationProgress);
            RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment()) {
                f = DialogsActivity.this.rightSlidingDialogContainer.openedProgress;
            }
            if (DialogsActivity.this.hasStories) {
                height += AndroidUtilities.m1146dp(81.0f) * (1.0f - DialogsActivity.this.searchAnimationProgress) * (1.0f - f) * (1.0f - DialogsActivity.this.progressToActionMode);
            }
            return (int) (height + DialogsActivity.this.storiesOverscroll);
        }

        public int getActionBarTop() {
            float f = DialogsActivity.this.scrollYOffset;
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (dialogsActivity.hasStories) {
                RightSlidingDialogContainer rightSlidingDialogContainer = dialogsActivity.rightSlidingDialogContainer;
                f *= (1.0f - DialogsActivity.this.progressToActionMode) * (1.0f - ((rightSlidingDialogContainer == null || !rightSlidingDialogContainer.hasFragment()) ? 0.0f : DialogsActivity.this.rightSlidingDialogContainer.openedProgress));
            }
            return (int) ((-getY()) + (f * (1.0f - DialogsActivity.this.searchAnimationProgress)));
        }

        @Override // android.view.ViewGroup
        protected boolean drawChild(Canvas canvas, View view, long j) {
            if ((view == DialogsActivity.this.fragmentContextView && DialogsActivity.this.fragmentContextView.isCallStyle()) || view == DialogsActivity.this.blurredView) {
                return true;
            }
            if (SizeNotifierFrameLayout.drawingBlur) {
                return super.drawChild(canvas, view, j);
            }
            if (view != DialogsActivity.this.viewPages[0] && ((DialogsActivity.this.viewPages.length <= 1 || view != DialogsActivity.this.viewPages[1]) && view != DialogsActivity.this.fragmentContextView && view != DialogsActivity.this.fragmentLocationContextView && view != DialogsActivity.this.dialogsHintCell && view != DialogsActivity.this.authHintCell)) {
                DialogsActivity.m12877$$Nest$fgetactiveGiftAuctionsHintCell(DialogsActivity.this);
                if (view != null) {
                    if (view == ((BaseFragment) DialogsActivity.this).actionBar && DialogsActivity.this.slideFragmentProgress != 1.0f) {
                        canvas.save();
                        DialogsActivity dialogsActivity = DialogsActivity.this;
                        if (dialogsActivity.slideFragmentLite) {
                            canvas.translate((dialogsActivity.isDrawerTransition ? 1 : -1) * dialogsActivity.getSlideAmplitude() * (1.0f - DialogsActivity.this.slideFragmentProgress), 0.0f);
                        } else {
                            float f = 1.0f - ((1.0f - dialogsActivity.slideFragmentProgress) * 0.05f);
                            canvas.translate(dialogsActivity.getSlideAmplitude() * (1.0f - DialogsActivity.this.slideFragmentProgress), 0.0f);
                            if (DialogsActivity.this.allowToScale()) {
                                canvas.scale(f, f, DialogsActivity.this.isDrawerTransition ? getMeasuredWidth() : 0.0f, (((BaseFragment) DialogsActivity.this).actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) + (ActionBar.getCurrentActionBarHeight() / 2.0f));
                            }
                        }
                        boolean zDrawChild = super.drawChild(canvas, view, j);
                        canvas.restore();
                        return zDrawChild;
                    }
                    return super.drawChild(canvas, view, j);
                }
            }
            canvas.save();
            canvas.clipRect(0.0f, (-getY()) + getActionBarTop() + getActionBarFullHeight(), getMeasuredWidth(), getMeasuredHeight());
            DialogsActivity dialogsActivity2 = DialogsActivity.this;
            float f2 = dialogsActivity2.slideFragmentProgress;
            if (f2 != 1.0f) {
                if (dialogsActivity2.slideFragmentLite) {
                    canvas.translate((dialogsActivity2.isDrawerTransition ? 1 : -1) * dialogsActivity2.getSlideAmplitude() * (1.0f - DialogsActivity.this.slideFragmentProgress), 0.0f);
                } else {
                    float f3 = 1.0f - ((1.0f - f2) * 0.05f);
                    canvas.translate(dialogsActivity2.getSlideAmplitude() * (1.0f - DialogsActivity.this.slideFragmentProgress), 0.0f);
                    if (DialogsActivity.this.allowToScale()) {
                        canvas.scale(f3, f3, DialogsActivity.this.isDrawerTransition ? getMeasuredWidth() : 0.0f, (-getY()) + DialogsActivity.this.scrollYOffset + getActionBarFullHeight());
                    }
                }
            }
            boolean zDrawChild2 = super.drawChild(canvas, view, j);
            canvas.restore();
            return zDrawChild2;
        }

        /* JADX WARN: Removed duplicated region for block: B:180:0x05a7  */
        /* JADX WARN: Removed duplicated region for block: B:20:0x005f  */
        @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected void dispatchDraw(android.graphics.Canvas r20) {
            /*
                Method dump skipped, instructions count: 2017
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.DialogsActivity.ContentView.dispatchDraw(android.graphics.Canvas):void");
        }

        /* JADX WARN: Removed duplicated region for block: B:108:0x0294  */
        @Override // android.widget.FrameLayout, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected void onMeasure(int r22, int r23) {
            /*
                Method dump skipped, instructions count: 1046
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.DialogsActivity.ContentView.onMeasure(int, int):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMeasure$0() {
            if (DialogsActivity.this.selectAnimatedEmojiDialog != null) {
                DialogsActivity.this.selectAnimatedEmojiDialog.dismiss();
                DialogsActivity.this.selectAnimatedEmojiDialog = null;
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:120:0x0223  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00a1  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x00be  */
        /* JADX WARN: Removed duplicated region for block: B:49:0x00d2  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0106  */
        @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected void onLayout(boolean r17, int r18, int r19, int r20, int r21) {
            /*
                Method dump skipped, instructions count: 671
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.DialogsActivity.ContentView.onLayout(boolean, int, int, int, int):void");
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if ((actionMasked == 1 || actionMasked == 3) && ((BaseFragment) DialogsActivity.this).actionBar.isActionModeShowed()) {
                DialogsActivity.this.allowMoving = true;
            }
            return checkTabsAnimationInProgress() || (DialogsActivity.this.filterTabsView != null && DialogsActivity.this.filterTabsView.isAnimatingIndicator()) || onTouchEvent(motionEvent);
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public void requestDisallowInterceptTouchEvent(boolean z) {
            if (DialogsActivity.this.maybeStartTracking && !DialogsActivity.this.startedTracking) {
                onTouchEvent(null);
            }
            super.requestDisallowInterceptTouchEvent(z);
        }

        @Override // android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            float xVelocity;
            float yVelocity;
            float measuredWidth;
            int measuredWidth2;
            if (((BaseFragment) DialogsActivity.this).parentLayout == null || DialogsActivity.this.filterTabsView == null || DialogsActivity.this.filterTabsView.isEditing() || DialogsActivity.this.searching || DialogsActivity.this.rightSlidingDialogContainer.hasFragment() || ((BaseFragment) DialogsActivity.this).parentLayout.checkTransitionAnimation() || ((BaseFragment) DialogsActivity.this).parentLayout.isInPreviewMode() || ((BaseFragment) DialogsActivity.this).parentLayout.isPreviewOpenAnimationInProgress() || ((((BaseFragment) DialogsActivity.this).parentLayout.getDrawerLayoutContainer() != null && ((BaseFragment) DialogsActivity.this).parentLayout.getDrawerLayoutContainer().isDrawerOpened()) || !((motionEvent == null || DialogsActivity.this.startedTracking || motionEvent.getY() > getActionBarTop() + getActionBarFullHeight()) && (DialogsActivity.this.initialDialogsType == 3 || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 5 || (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 2 && DialogsActivity.this.viewPages[0] != null && (DialogsActivity.this.viewPages[0].dialogsAdapter.getDialogsType() == 7 || DialogsActivity.this.viewPages[0].dialogsAdapter.getDialogsType() == 8)))))) {
                return false;
            }
            if (motionEvent != null) {
                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                }
                this.velocityTracker.addMovement(motionEvent);
            }
            if (motionEvent != null && motionEvent.getAction() == 0 && checkTabsAnimationInProgress()) {
                DialogsActivity.this.startedTracking = true;
                this.startedTrackingPointerId = motionEvent.getPointerId(0);
                this.startedTrackingX = (int) motionEvent.getX();
                if (((BaseFragment) DialogsActivity.this).parentLayout.getDrawerLayoutContainer() != null) {
                    ((BaseFragment) DialogsActivity.this).parentLayout.getDrawerLayoutContainer().setAllowOpenDrawerBySwipe(false);
                }
                if (DialogsActivity.this.animatingForward) {
                    if (this.startedTrackingX < DialogsActivity.this.viewPages[0].getMeasuredWidth() + DialogsActivity.this.viewPages[0].getTranslationX()) {
                        DialogsActivity dialogsActivity = DialogsActivity.this;
                        dialogsActivity.additionalOffset = dialogsActivity.viewPages[0].getTranslationX();
                    } else {
                        ViewPage viewPage = DialogsActivity.this.viewPages[0];
                        DialogsActivity.this.viewPages[0] = DialogsActivity.this.viewPages[1];
                        DialogsActivity.this.viewPages[1] = viewPage;
                        DialogsActivity.this.animatingForward = false;
                        DialogsActivity dialogsActivity2 = DialogsActivity.this;
                        dialogsActivity2.additionalOffset = dialogsActivity2.viewPages[0].getTranslationX();
                        DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[0].selectedType, 1.0f);
                        DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[1].selectedType, DialogsActivity.this.additionalOffset / DialogsActivity.this.viewPages[0].getMeasuredWidth());
                        DialogsActivity.this.switchToCurrentSelectedMode(true);
                        DialogsActivity.this.viewPages[0].dialogsAdapter.resume();
                        DialogsActivity.this.viewPages[1].dialogsAdapter.pause();
                    }
                } else if (this.startedTrackingX < DialogsActivity.this.viewPages[1].getMeasuredWidth() + DialogsActivity.this.viewPages[1].getTranslationX()) {
                    ViewPage viewPage2 = DialogsActivity.this.viewPages[0];
                    DialogsActivity.this.viewPages[0] = DialogsActivity.this.viewPages[1];
                    DialogsActivity.this.viewPages[1] = viewPage2;
                    DialogsActivity.this.animatingForward = true;
                    DialogsActivity dialogsActivity3 = DialogsActivity.this;
                    dialogsActivity3.additionalOffset = dialogsActivity3.viewPages[0].getTranslationX();
                    DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[0].selectedType, 1.0f);
                    DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[1].selectedType, (-DialogsActivity.this.additionalOffset) / DialogsActivity.this.viewPages[0].getMeasuredWidth());
                    DialogsActivity.this.switchToCurrentSelectedMode(true);
                    DialogsActivity.this.viewPages[0].dialogsAdapter.resume();
                    DialogsActivity.this.viewPages[1].dialogsAdapter.pause();
                } else {
                    DialogsActivity dialogsActivity4 = DialogsActivity.this;
                    dialogsActivity4.additionalOffset = dialogsActivity4.viewPages[0].getTranslationX();
                }
                DialogsActivity.this.tabsAnimation.removeAllListeners();
                DialogsActivity.this.tabsAnimation.cancel();
                DialogsActivity.this.tabsAnimationInProgress = false;
            } else if (motionEvent != null && motionEvent.getAction() == 0) {
                DialogsActivity.this.additionalOffset = 0.0f;
            }
            if (motionEvent != null && motionEvent.getAction() == 0 && !DialogsActivity.this.startedTracking && !DialogsActivity.this.maybeStartTracking && DialogsActivity.this.filterTabsView.getVisibility() == 0) {
                this.startedTrackingPointerId = motionEvent.getPointerId(0);
                DialogsActivity.this.maybeStartTracking = true;
                this.startedTrackingX = (int) motionEvent.getX();
                this.startedTrackingY = (int) motionEvent.getY();
                this.velocityTracker.clear();
            } else if (motionEvent != null && motionEvent.getAction() == 2 && motionEvent.getPointerId(0) == this.startedTrackingPointerId) {
                int x = (int) ((motionEvent.getX() - this.startedTrackingX) + DialogsActivity.this.additionalOffset);
                int iAbs = Math.abs(((int) motionEvent.getY()) - this.startedTrackingY);
                if (DialogsActivity.this.startedTracking && ((DialogsActivity.this.animatingForward && x > 0) || (!DialogsActivity.this.animatingForward && x < 0))) {
                    if (!prepareForMoving(motionEvent, x < 0)) {
                        DialogsActivity.this.maybeStartTracking = true;
                        DialogsActivity.this.startedTracking = false;
                        DialogsActivity.this.viewPages[0].setTranslationX(0.0f);
                        DialogsActivity.this.viewPages[1].setTranslationX(DialogsActivity.this.animatingForward ? DialogsActivity.this.viewPages[0].getMeasuredWidth() : -DialogsActivity.this.viewPages[0].getMeasuredWidth());
                        DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[1].selectedType, 0.0f);
                    }
                }
                if (DialogsActivity.this.maybeStartTracking && !DialogsActivity.this.startedTracking) {
                    float pixelsInCM = AndroidUtilities.getPixelsInCM(0.3f, true);
                    int x2 = (int) (motionEvent.getX() - this.startedTrackingX);
                    if (Math.abs(x2) >= pixelsInCM && Math.abs(x2) > iAbs) {
                        prepareForMoving(motionEvent, x < 0);
                    }
                } else if (DialogsActivity.this.startedTracking) {
                    DialogsActivity.this.viewPages[0].setTranslationX(x);
                    if (DialogsActivity.this.animatingForward) {
                        DialogsActivity.this.viewPages[1].setTranslationX(DialogsActivity.this.viewPages[0].getMeasuredWidth() + x);
                    } else {
                        DialogsActivity.this.viewPages[1].setTranslationX(x - DialogsActivity.this.viewPages[0].getMeasuredWidth());
                    }
                    float fAbs = Math.abs(x) / DialogsActivity.this.viewPages[0].getMeasuredWidth();
                    if (DialogsActivity.this.viewPages[1].isLocked && fAbs > 0.3f) {
                        dispatchTouchEvent(MotionEvent.obtain(0L, 0L, 3, 0.0f, 0.0f, 0));
                        DialogsActivity.this.filterTabsView.shakeLock(DialogsActivity.this.viewPages[1].selectedType);
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$ContentView$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$onTouchEvent$1();
                            }
                        }, 200L);
                        return false;
                    }
                    DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[1].selectedType, fAbs);
                }
            } else if (motionEvent == null || (motionEvent.getPointerId(0) == this.startedTrackingPointerId && (motionEvent.getAction() == 3 || motionEvent.getAction() == 1 || motionEvent.getAction() == 6))) {
                this.velocityTracker.computeCurrentVelocity(MediaDataController.MAX_STYLE_RUNS_COUNT, DialogsActivity.this.maximumVelocity);
                if (motionEvent == null || motionEvent.getAction() == 3) {
                    xVelocity = 0.0f;
                    yVelocity = 0.0f;
                } else {
                    xVelocity = this.velocityTracker.getXVelocity();
                    yVelocity = this.velocityTracker.getYVelocity();
                    if (!DialogsActivity.this.startedTracking && Math.abs(xVelocity) >= AppUtils.getSwipeVelocity() && Math.abs(xVelocity) > Math.abs(yVelocity)) {
                        prepareForMoving(motionEvent, xVelocity < 0.0f);
                    }
                }
                if (DialogsActivity.this.startedTracking) {
                    float x3 = DialogsActivity.this.viewPages[0].getX();
                    DialogsActivity.this.tabsAnimation = new AnimatorSet();
                    if (DialogsActivity.this.viewPages[1].isLocked) {
                        DialogsActivity.this.backAnimation = true;
                    } else if (DialogsActivity.this.additionalOffset == 0.0f) {
                        DialogsActivity.this.backAnimation = Math.abs(x3) < ((float) DialogsActivity.this.viewPages[0].getMeasuredWidth()) / 3.0f && (Math.abs(xVelocity) < ((float) AppUtils.getSwipeVelocity()) || Math.abs(xVelocity) < Math.abs(yVelocity));
                    } else if (Math.abs(xVelocity) > 1500.0f) {
                        DialogsActivity dialogsActivity5 = DialogsActivity.this;
                        dialogsActivity5.backAnimation = !dialogsActivity5.animatingForward ? xVelocity >= 0.0f : xVelocity <= 0.0f;
                    } else if (DialogsActivity.this.animatingForward) {
                        DialogsActivity dialogsActivity6 = DialogsActivity.this;
                        dialogsActivity6.backAnimation = dialogsActivity6.viewPages[1].getX() > ((float) (DialogsActivity.this.viewPages[0].getMeasuredWidth() >> 1));
                    } else {
                        DialogsActivity dialogsActivity7 = DialogsActivity.this;
                        dialogsActivity7.backAnimation = dialogsActivity7.viewPages[0].getX() < ((float) (DialogsActivity.this.viewPages[0].getMeasuredWidth() >> 1));
                    }
                    boolean z = DialogsActivity.this.backAnimation;
                    Property property = View.TRANSLATION_X;
                    if (z) {
                        measuredWidth = Math.abs(x3);
                        if (DialogsActivity.this.animatingForward) {
                            DialogsActivity.this.tabsAnimation.playTogether(ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[0], (Property<ViewPage, Float>) property, 0.0f), ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[1], (Property<ViewPage, Float>) property, DialogsActivity.this.viewPages[1].getMeasuredWidth()));
                        } else {
                            DialogsActivity.this.tabsAnimation.playTogether(ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[0], (Property<ViewPage, Float>) property, 0.0f), ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[1], (Property<ViewPage, Float>) property, -DialogsActivity.this.viewPages[1].getMeasuredWidth()));
                        }
                    } else {
                        measuredWidth = DialogsActivity.this.viewPages[0].getMeasuredWidth() - Math.abs(x3);
                        if (DialogsActivity.this.animatingForward) {
                            DialogsActivity.this.tabsAnimation.playTogether(ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[0], (Property<ViewPage, Float>) property, -DialogsActivity.this.viewPages[0].getMeasuredWidth()), ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[1], (Property<ViewPage, Float>) property, 0.0f));
                        } else {
                            DialogsActivity.this.tabsAnimation.playTogether(ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[0], (Property<ViewPage, Float>) property, DialogsActivity.this.viewPages[0].getMeasuredWidth()), ObjectAnimator.ofFloat(DialogsActivity.this.viewPages[1], (Property<ViewPage, Float>) property, 0.0f));
                        }
                    }
                    DialogsActivity.this.tabsAnimation.setInterpolator(DialogsActivity.interpolator);
                    int measuredWidth3 = getMeasuredWidth();
                    float f = measuredWidth3 / 2;
                    float fDistanceInfluenceForSnapDuration = f + (AndroidUtilities.distanceInfluenceForSnapDuration(Math.min(1.0f, measuredWidth / measuredWidth3)) * f);
                    float fAbs2 = Math.abs(xVelocity);
                    if (fAbs2 > 0.0f) {
                        measuredWidth2 = Math.round(Math.abs(fDistanceInfluenceForSnapDuration / fAbs2) * 1000.0f) * 4;
                    } else {
                        measuredWidth2 = (int) (((measuredWidth / getMeasuredWidth()) + 1.0f) * 100.0f);
                    }
                    DialogsActivity.this.tabsAnimation.setDuration(Math.max(150, Math.min(measuredWidth2, 600)));
                    DialogsActivity.this.tabsAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.ContentView.1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            DialogsActivity.this.tabsAnimation = null;
                            if (!DialogsActivity.this.backAnimation) {
                                ViewPage viewPage3 = DialogsActivity.this.viewPages[0];
                                DialogsActivity.this.viewPages[0] = DialogsActivity.this.viewPages[1];
                                DialogsActivity.this.viewPages[1] = viewPage3;
                                DialogsActivity.this.filterTabsView.selectTabWithId(DialogsActivity.this.viewPages[0].selectedType, 1.0f);
                                DialogsActivity.this.updateCounters(false);
                                DialogsActivity.this.viewPages[0].dialogsAdapter.resume();
                                DialogsActivity.this.viewPages[1].dialogsAdapter.pause();
                            }
                            DialogsActivity dialogsActivity8 = DialogsActivity.this;
                            dialogsActivity8.isFirstTab = dialogsActivity8.viewPages[0].selectedType == DialogsActivity.this.filterTabsView.getFirstTabId();
                            DialogsActivity.this.updateDrawerSwipeEnabled();
                            DialogsActivity.this.viewPages[1].setVisibility(8);
                            DialogsActivity.this.showScrollbars(true);
                            DialogsActivity.this.tabsAnimationInProgress = false;
                            DialogsActivity.this.maybeStartTracking = false;
                            ((BaseFragment) DialogsActivity.this).actionBar.setEnabled(true);
                            DialogsActivity.this.filterTabsView.setEnabled(true);
                            DialogsActivity dialogsActivity9 = DialogsActivity.this;
                            dialogsActivity9.checkListLoad(dialogsActivity9.viewPages[0]);
                        }
                    });
                    DialogsActivity.this.tabsAnimation.start();
                    DialogsActivity.this.tabsAnimationInProgress = true;
                    DialogsActivity.this.startedTracking = false;
                } else {
                    DialogsActivity dialogsActivity8 = DialogsActivity.this;
                    dialogsActivity8.isFirstTab = dialogsActivity8.viewPages[0].selectedType == DialogsActivity.this.filterTabsView.getFirstTabId();
                    DialogsActivity.this.updateDrawerSwipeEnabled();
                    DialogsActivity.this.maybeStartTracking = false;
                    ((BaseFragment) DialogsActivity.this).actionBar.setEnabled(true);
                    DialogsActivity.this.filterTabsView.setEnabled(true);
                }
                VelocityTracker velocityTracker = this.velocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    this.velocityTracker = null;
                }
            }
            return DialogsActivity.this.startedTracking;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTouchEvent$1() {
            DialogsActivity.this.showDialog(new LimitReachedBottomSheet(DialogsActivity.this, getContext(), 3, ((BaseFragment) DialogsActivity.this).currentAccount, null));
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayout
        protected void drawList(Canvas canvas, boolean z, ArrayList arrayList) {
            if (DialogsActivity.this.searchIsShowed) {
                if (DialogsActivity.this.searchViewPager == null || DialogsActivity.this.searchViewPager.getVisibility() != 0) {
                    return;
                }
                DialogsActivity.this.searchViewPager.drawForBlur(canvas);
                return;
            }
            for (int i = 0; i < DialogsActivity.this.viewPages.length; i++) {
                if (DialogsActivity.this.viewPages[i] != null && DialogsActivity.this.viewPages[i].getVisibility() == 0) {
                    for (int i2 = 0; i2 < DialogsActivity.this.viewPages[i].listView.getChildCount(); i2++) {
                        View childAt = DialogsActivity.this.viewPages[i].listView.getChildAt(i2);
                        if (childAt instanceof BaseCell) {
                            ((BaseCell) childAt).setCaching(z, false);
                        }
                    }
                }
            }
            for (int i3 = 0; i3 < DialogsActivity.this.viewPages.length; i3++) {
                if (DialogsActivity.this.viewPages[i3] != null && DialogsActivity.this.viewPages[i3].getVisibility() == 0) {
                    for (int i4 = 0; i4 < DialogsActivity.this.viewPages[i3].listView.getChildCount(); i4++) {
                        View childAt2 = DialogsActivity.this.viewPages[i3].listView.getChildAt(i4);
                        float y = childAt2.getY();
                        int iM1146dp = DialogsActivity.this.viewPages[i3].listView.blurTopPadding + AndroidUtilities.m1146dp(100.0f) + ((DialogsActivity.this.authHintCell == null || DialogsActivity.this.authHintCell.getVisibility() != 0) ? 0 : AndroidUtilities.m1146dp(200.0f));
                        DialogsActivity.m12877$$Nest$fgetactiveGiftAuctionsHintCell(DialogsActivity.this);
                        if (y < iM1146dp) {
                            int iSave = canvas.save();
                            canvas.translate(DialogsActivity.this.viewPages[i3].getX(), DialogsActivity.this.viewPages[i3].getY() + DialogsActivity.this.viewPages[i3].listView.getY());
                            if (childAt2 instanceof DialogCell) {
                                DialogCell dialogCell = (DialogCell) childAt2;
                                if (!dialogCell.isFolderCell() || !SharedConfig.archiveHidden) {
                                    canvas.translate(dialogCell.getX(), dialogCell.getY());
                                    dialogCell.setCaching(z, true);
                                    dialogCell.drawCached(canvas);
                                    if (arrayList != null) {
                                        arrayList.add(dialogCell);
                                    }
                                }
                            } else {
                                canvas.translate(childAt2.getX(), childAt2.getY());
                                childAt2.draw(canvas);
                                if (arrayList != null && (childAt2 instanceof SizeNotifierFrameLayout.IViewWithInvalidateCallback)) {
                                    arrayList.add((SizeNotifierFrameLayout.IViewWithInvalidateCallback) childAt2);
                                }
                            }
                            canvas.restoreToCount(iSave);
                        }
                    }
                }
            }
        }

        @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            if (DialogsActivity.this.statusDrawable != null) {
                DialogsActivity.this.statusDrawable.attach();
            }
            if (DialogsActivity.this.ghostStatusDrawable != null) {
                DialogsActivity.this.ghostStatusDrawable.attach();
            }
        }

        @Override // org.telegram.p023ui.Components.SizeNotifierFrameLayout, android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            if (DialogsActivity.this.statusDrawable != null) {
                DialogsActivity.this.statusDrawable.detach();
            }
            if (DialogsActivity.this.ghostStatusDrawable != null) {
                DialogsActivity.this.ghostStatusDrawable.detach();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTopPadding() {
        SearchViewPager searchViewPager;
        this.topPadding = this.fragmentContextTopPadding;
        updateContextViewPosition();
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer != null) {
            rightSlidingDialogContainer.setFragmentViewPadding(this.topPadding);
        }
        if (this.whiteActionBar && (searchViewPager = this.searchViewPager) != null) {
            searchViewPager.setTranslationY((this.topPadding - this.lastMeasuredTopPadding) + this.searchViewPagerTranslationY);
        } else {
            this.fragmentView.requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateStoriesViewAlpha(float f) {
        float f2;
        float f3;
        this.dialogStoriesCell.setAlpha((1.0f - this.progressToActionMode) * f * this.progressToDialogStoriesCell * (1.0f - Utilities.clamp(this.searchAnimationProgress / 0.5f, 1.0f, 0.0f)));
        if (this.hasStories || this.animateToHasStories) {
            float fClamp = Utilities.clamp((-this.scrollYOffset) / AndroidUtilities.m1146dp(81.0f), 1.0f, 0.0f);
            if (this.progressToActionMode == 1.0f) {
                fClamp = 1.0f;
            }
            float fClamp2 = Utilities.clamp(fClamp / 0.5f, 1.0f, 0.0f);
            this.dialogStoriesCell.setClipTop(0);
            if (!this.hasStories && this.animateToHasStories) {
                this.dialogStoriesCell.setTranslationY((-AndroidUtilities.m1146dp(81.0f)) - AndroidUtilities.m1146dp(8.0f));
                this.dialogStoriesCell.setProgressToCollapse(1.0f);
                f3 = this.progressToDialogStoriesCell;
            } else {
                this.dialogStoriesCell.setTranslationY(((this.scrollYOffset + this.storiesYOffset) + (this.storiesOverscroll / 2.0f)) - AndroidUtilities.m1146dp(8.0f));
                this.dialogStoriesCell.setProgressToCollapse(fClamp, !this.rightSlidingDialogContainer.hasFragment());
                if (!this.animateToHasStories) {
                    f3 = this.progressToDialogStoriesCell;
                } else {
                    f2 = 1.0f - fClamp2;
                    this.actionBar.setTranslationY(0.0f);
                }
            }
            f2 = 1.0f - f3;
            this.actionBar.setTranslationY(0.0f);
        } else {
            if (this.hasOnlySlefStories) {
                this.dialogStoriesCell.setTranslationY(((-AndroidUtilities.m1146dp(81.0f)) + this.scrollYOffset) - AndroidUtilities.m1146dp(8.0f));
                this.dialogStoriesCell.setProgressToCollapse(1.0f);
                DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
                dialogStoriesCell.setClipTop((int) (AndroidUtilities.statusBarHeight - dialogStoriesCell.getY()));
            }
            f2 = 1.0f - this.progressToDialogStoriesCell;
            this.actionBar.setTranslationY(this.scrollYOffset);
        }
        if (f2 != 1.0f) {
            this.actionBar.getTitlesContainer().setPivotY(AndroidUtilities.statusBarHeight + (ActionBar.getCurrentActionBarHeight() / 2.0f));
            this.actionBar.getTitlesContainer().setPivotX(AndroidUtilities.m1146dp(72.0f));
            float f4 = (0.2f * f2) + 0.8f;
            this.actionBar.getTitlesContainer().setScaleY(f4);
            this.actionBar.getTitlesContainer().setScaleX(f4);
            this.actionBar.getTitlesContainer().setAlpha(f2 * (1.0f - this.progressToActionMode));
            return;
        }
        this.actionBar.getTitlesContainer().setScaleY(1.0f);
        this.actionBar.getTitlesContainer().setScaleY(1.0f);
        this.actionBar.getTitlesContainer().setScaleX(1.0f);
        this.actionBar.getTitlesContainer().setAlpha(1.0f - this.progressToActionMode);
    }

    public class DialogsRecyclerView extends BlurredRecyclerView implements StoriesListPlaceProvider.ClippedView {
        public int additionalPadding;
        float animateFromSelectorPosition;
        boolean animateSwitchingSelector;
        private RecyclerListView animationSupportListView;
        LongSparseArray animationSupportViewsByDialogId;
        private int appliedPaddingTop;
        private boolean firstLayout;
        private boolean ignoreLayout;
        float lastDrawSelectorY;
        private int lastListPadding;
        private int lastTop;
        Paint paint;
        private final ViewPage parentPage;
        UserListPoller poller;
        RectF rectF;
        private float rightFragmentOpenedProgress;
        private Paint selectorPaint;
        float selectorPositionProgress;
        public boolean updateDialogsOnNextDraw;

        @Override // org.telegram.p023ui.Components.RecyclerListView
        protected boolean updateEmptyViewAnimated() {
            return true;
        }

        public DialogsRecyclerView(Context context, ViewPage viewPage) {
            super(context);
            this.firstLayout = true;
            this.paint = new Paint();
            this.rectF = new RectF();
            this.selectorPositionProgress = 1.0f;
            this.parentPage = viewPage;
            this.additionalClipBottom = AndroidUtilities.m1146dp(200.0f);
        }

        public void prepareSelectorForAnimation() {
            this.selectorPositionProgress = 0.0f;
            this.animateFromSelectorPosition = this.lastDrawSelectorY;
            this.animateSwitchingSelector = this.rightFragmentOpenedProgress != 0.0f;
        }

        public void setViewsOffset(float f) {
            View viewFindViewByPosition;
            DialogsActivity.viewOffset = f;
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).setTranslationY(f);
            }
            if (this.selectorPosition != -1 && (viewFindViewByPosition = getLayoutManager().findViewByPosition(this.selectorPosition)) != null) {
                this.selectorRect.set(viewFindViewByPosition.getLeft(), (int) (viewFindViewByPosition.getTop() + f), viewFindViewByPosition.getRight(), (int) (viewFindViewByPosition.getBottom() + f));
                this.selectorDrawable.setBounds(this.selectorRect);
            }
            invalidate();
        }

        public float getViewOffset() {
            return DialogsActivity.viewOffset;
        }

        @Override // android.view.ViewGroup
        public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
            super.addView(view, i, layoutParams);
            view.setTranslationY(DialogsActivity.viewOffset);
            view.setTranslationX(0.0f);
            view.setAlpha(1.0f);
        }

        @Override // android.view.ViewGroup, android.view.ViewManager
        public void removeView(View view) {
            super.removeView(view);
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            view.setAlpha(1.0f);
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
        public void onDraw(Canvas canvas) {
            if (this.parentPage.pullForegroundDrawable != null && DialogsActivity.viewOffset != 0.0f) {
                int paddingTop = getPaddingTop();
                if (paddingTop != 0) {
                    canvas.save();
                    canvas.translate(0.0f, paddingTop);
                }
                this.parentPage.pullForegroundDrawable.drawOverScroll(canvas);
                if (paddingTop != 0) {
                    canvas.restore();
                }
            }
            super.onDraw(canvas);
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x02b5  */
        /* JADX WARN: Removed duplicated region for block: B:104:0x02f2  */
        /* JADX WARN: Removed duplicated region for block: B:91:0x029e  */
        /* JADX WARN: Removed duplicated region for block: B:94:0x02a3 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:98:0x02ae  */
        @Override // org.telegram.p023ui.Components.BlurredRecyclerView, org.telegram.p023ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected void dispatchDraw(android.graphics.Canvas r22) {
            /*
                Method dump skipped, instructions count: 1197
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.DialogsActivity.DialogsRecyclerView.dispatchDraw(android.graphics.Canvas):void");
        }

        private boolean drawMovingViewsOverlayed() {
            return getItemAnimator() != null && getItemAnimator().isRunning();
        }

        @Override // org.telegram.p023ui.Components.BlurredRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean drawChild(Canvas canvas, View view, long j) {
            if (drawMovingViewsOverlayed() && (view instanceof DialogCell) && ((DialogCell) view).isMoving()) {
                return true;
            }
            return super.drawChild(canvas, view, j);
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView
        public void setAdapter(RecyclerView.Adapter adapter) {
            super.setAdapter(adapter);
            this.firstLayout = true;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r7v20, types: [android.animation.AnimatorSet, kotlin.jvm.internal.Intrinsics] */
        /* JADX WARN: Type inference failed for: r7v21, types: [void] */
        @Override // org.telegram.p023ui.Components.BlurredRecyclerView, org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
        protected void onMeasure(int i, int i2) {
            String str;
            int iFindFirstVisibleItemPosition = this.parentPage.layoutManager.findFirstVisibleItemPosition();
            if (iFindFirstVisibleItemPosition != -1 && this.parentPage.itemTouchhelper.isIdle() && !this.parentPage.layoutManager.hasPendingScrollPosition() && this.parentPage.listView.getScrollState() != 1) {
                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.parentPage.listView.findViewHolderForAdapterPosition(iFindFirstVisibleItemPosition);
                if (viewHolderFindViewHolderForAdapterPosition != null) {
                    int top = viewHolderFindViewHolderForAdapterPosition.itemView.getTop();
                    if (this.parentPage.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive() && this.parentPage.archivePullViewState == 2) {
                        iFindFirstVisibleItemPosition = Math.max(1, iFindFirstVisibleItemPosition);
                    }
                    this.ignoreLayout = true;
                    this.parentPage.layoutManager.scrollToPositionWithOffset(iFindFirstVisibleItemPosition, (int) ((top - this.lastListPadding) + DialogsActivity.this.scrollAdditionalOffset + this.parentPage.pageAdditionalOffset));
                    this.ignoreLayout = false;
                }
            } else if (iFindFirstVisibleItemPosition == -1 && this.firstLayout) {
                this.parentPage.layoutManager.scrollToPositionWithOffset((this.parentPage.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive()) ? 1 : 0, (int) DialogsActivity.this.scrollYOffset);
            }
            if (!DialogsActivity.this.onlySelect || DialogsActivity.this.initialDialogsType == 3) {
                this.ignoreLayout = true;
                DialogsActivity dialogsActivity = DialogsActivity.this;
                int currentActionBarHeight = (dialogsActivity.hasStories || (dialogsActivity.filterTabsView != null && DialogsActivity.this.filterTabsView.getVisibility() == 0)) ? ActionBar.getCurrentActionBarHeight() + (((BaseFragment) DialogsActivity.this).actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) : ((BaseFragment) DialogsActivity.this).inPreviewMode ? AndroidUtilities.statusBarHeight : 0;
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                int iM1146dp = currentActionBarHeight;
                if (dialogsActivity2.hasStories) {
                    iM1146dp = currentActionBarHeight;
                    if (!dialogsActivity2.actionModeFullyShowed) {
                        iM1146dp = currentActionBarHeight + AndroidUtilities.m1146dp(81.0f);
                    }
                }
                this.additionalPadding = 0;
                int i3 = iM1146dp;
                if (DialogsActivity.this.authHintCell != null) {
                    i3 = iM1146dp;
                    if (DialogsActivity.this.authHintCellProgress != 0.0f) {
                        i3 = iM1146dp;
                        if (!DialogsActivity.this.authHintCellAnimating) {
                            int measuredHeight = iM1146dp + DialogsActivity.this.authHintCell.getMeasuredHeight();
                            this.additionalPadding += DialogsActivity.this.authHintCell.getMeasuredHeight();
                            i3 = measuredHeight;
                        }
                    }
                }
                DialogsActivity.m12877$$Nest$fgetactiveGiftAuctionsHintCell(DialogsActivity.this);
                int currentNavigationBarInset = DialogsActivity.this.windowInsetsStateHolder.getCurrentNavigationBarInset();
                if (i3 != getPaddingTop() || currentNavigationBarInset != getPaddingBottom()) {
                    setTopGlowOffset(i3);
                    setPadding(0, i3, 0, currentNavigationBarInset);
                    if (DialogsActivity.this.hasStories) {
                        this.parentPage.progressView.setPaddingTop(i3 - AndroidUtilities.m1146dp(81.0f));
                    } else {
                        this.parentPage.progressView.setPaddingTop(i3);
                    }
                    for (int i4 = 0; i4 < getChildCount(); i4++) {
                        if (getChildAt(i4) instanceof DialogsAdapter.LastEmptyView) {
                            getChildAt(i4).requestLayout();
                        }
                    }
                }
                this.ignoreLayout = false;
                str = i3;
            } else {
                str = 0;
            }
            if (this.firstLayout && DialogsActivity.this.getMessagesController().dialogsLoaded) {
                if (this.parentPage.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive()) {
                    this.ignoreLayout = true;
                    ((LinearLayoutManager) getLayoutManager()).scrollToPositionWithOffset(1, (int) DialogsActivity.this.scrollYOffset);
                    this.ignoreLayout = false;
                }
                this.firstLayout = false;
            }
            super.onMeasure(i, i2);
            if (DialogsActivity.this.onlySelect || this.appliedPaddingTop == str || DialogsActivity.this.viewPages == null || DialogsActivity.this.viewPages.length <= 1 || DialogsActivity.this.startedTracking) {
                return;
            }
            if ((DialogsActivity.this.tabsAnimation != null && DialogsActivity.this.tabsAnimation.checkNotNullParameter(str, str) != 0) || DialogsActivity.this.tabsAnimationInProgress || DialogsActivity.this.filterTabsView == null) {
                return;
            }
            DialogsActivity.this.filterTabsView.isAnimatingIndicator();
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            this.lastListPadding = getPaddingTop();
            this.lastTop = i2;
            DialogsActivity.this.scrollAdditionalOffset = 0.0f;
            this.parentPage.pageAdditionalOffset = 0;
        }

        @Override // org.telegram.p023ui.Components.BlurredRecyclerView, org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void toggleArchiveHidden(boolean z, DialogCell dialogCell) {
            SharedConfig.toggleArchiveHidden();
            UndoView undoView = DialogsActivity.this.getUndoView();
            if (SharedConfig.archiveHidden) {
                if (dialogCell != null) {
                    DialogsActivity.this.disableActionBarScrolling = true;
                    DialogsActivity.this.waitingForScrollFinished = true;
                    int measuredHeight = dialogCell.getMeasuredHeight() + (dialogCell.getTop() - getPaddingTop());
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    if (dialogsActivity.hasStories && !dialogsActivity.dialogStoriesCell.isExpanded()) {
                        DialogsActivity.this.fixScrollYAfterArchiveOpened = true;
                        measuredHeight += AndroidUtilities.m1146dp(81.0f);
                    }
                    smoothScrollBy(0, measuredHeight, CubicBezierInterpolator.EASE_OUT);
                    if (z) {
                        DialogsActivity.this.updatePullAfterScroll = true;
                    } else {
                        updatePullState();
                    }
                }
                undoView.showWithAction(0L, 6, null, null);
                return;
            }
            undoView.showWithAction(0L, 7, null, null);
            updatePullState();
            if (!z || dialogCell == null) {
                return;
            }
            dialogCell.resetPinnedArchiveState();
            dialogCell.invalidate();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updatePullState() {
            this.parentPage.archivePullViewState = SharedConfig.archiveHidden ? 2 : 0;
            if (this.parentPage.pullForegroundDrawable != null) {
                this.parentPage.pullForegroundDrawable.setWillDraw(this.parentPage.archivePullViewState != 0);
            }
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (this.fastScrollAnimationRunning || DialogsActivity.this.waitingForScrollFinished || DialogsActivity.this.rightFragmentTransitionInProgress) {
                return false;
            }
            int action = motionEvent.getAction();
            if (action == 0) {
                setOverScrollMode(0);
            }
            if ((action == 1 || action == 3) && !this.parentPage.itemTouchhelper.isIdle() && this.parentPage.swipeController.swipingFolder) {
                this.parentPage.swipeController.swipeFolderBack = true;
                if (this.parentPage.itemTouchhelper.checkHorizontalSwipe(null, 4) != 0 && this.parentPage.swipeController.currentItemViewHolder != null) {
                    View view = this.parentPage.swipeController.currentItemViewHolder.itemView;
                    if (view instanceof DialogCell) {
                        DialogCell dialogCell = (DialogCell) view;
                        long dialogId = dialogCell.getDialogId();
                        if (DialogObject.isFolderDialogId(dialogId)) {
                            toggleArchiveHidden(false, dialogCell);
                        } else {
                            TLRPC.Dialog dialog = (TLRPC.Dialog) DialogsActivity.this.getMessagesController().dialogs_dict.get(dialogId);
                            if (dialog != null) {
                                if (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) != 1) {
                                    if (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) != 3) {
                                        if (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) != 0) {
                                            if (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 4) {
                                                ArrayList arrayList = new ArrayList();
                                                arrayList.add(Long.valueOf(dialogId));
                                                DialogsActivity.this.performSelectedDialogsAction(arrayList, 102, true, false);
                                            }
                                        } else {
                                            ArrayList arrayList2 = new ArrayList();
                                            arrayList2.add(Long.valueOf(dialogId));
                                            DialogsActivity.this.canPinCount = !DialogsActivity.this.isDialogPinned(dialog) ? 1 : 0;
                                            DialogsActivity.this.performSelectedDialogsAction(arrayList2, 100, true, false);
                                        }
                                    } else if (!DialogsActivity.this.getMessagesController().isDialogMuted(dialogId, 0L)) {
                                        NotificationsController.getInstance(UserConfig.selectedAccount).setDialogNotificationsSettings(dialogId, 0L, 3);
                                        if (BulletinFactory.canShowBulletin(DialogsActivity.this)) {
                                            BulletinFactory.createMuteBulletin(DialogsActivity.this, 3).show();
                                        }
                                    } else {
                                        ArrayList arrayList3 = new ArrayList();
                                        arrayList3.add(Long.valueOf(dialogId));
                                        DialogsActivity dialogsActivity = DialogsActivity.this;
                                        dialogsActivity.canMuteCount = !MessagesController.getInstance(((BaseFragment) dialogsActivity).currentAccount).isDialogMuted(dialogId, 0L) ? 1 : 0;
                                        DialogsActivity dialogsActivity2 = DialogsActivity.this;
                                        dialogsActivity2.canUnmuteCount = dialogsActivity2.canMuteCount > 0 ? 0 : 1;
                                        DialogsActivity.this.performSelectedDialogsAction(arrayList3, 104, true, false);
                                    }
                                } else {
                                    ArrayList arrayList4 = new ArrayList();
                                    arrayList4.add(Long.valueOf(dialogId));
                                    DialogsActivity.this.canReadCount = (dialog.unread_count > 0 || dialog.unread_mark) ? 1 : 0;
                                    DialogsActivity.this.performSelectedDialogsAction(arrayList4, 101, true, false);
                                }
                            }
                        }
                    }
                }
            }
            boolean zOnTouchEvent = super.onTouchEvent(motionEvent);
            if (this.parentPage.dialogsType == 0 && ((action == 1 || action == 3) && this.parentPage.archivePullViewState == 2 && DialogsActivity.this.hasHiddenArchive() && ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition() == 0)) {
                int paddingTop = getPaddingTop();
                DialogCell dialogCellFindArchiveDialogCell = DialogsActivity.this.findArchiveDialogCell(this.parentPage);
                if (dialogCellFindArchiveDialogCell != null) {
                    int iM1146dp = (int) (AndroidUtilities.m1146dp(SharedConfig.useThreeLinesLayout ? 78.0f : 72.0f) * 0.85f);
                    int top = (dialogCellFindArchiveDialogCell.getTop() - paddingTop) + dialogCellFindArchiveDialogCell.getMeasuredHeight();
                    long jCurrentTimeMillis = System.currentTimeMillis() - DialogsActivity.this.startArchivePullingTime;
                    if (top < iM1146dp || jCurrentTimeMillis < 200) {
                        DialogsActivity.this.disableActionBarScrolling = true;
                        smoothScrollBy(0, top, CubicBezierInterpolator.EASE_OUT_QUINT);
                        this.parentPage.archivePullViewState = 2;
                    } else if (this.parentPage.archivePullViewState != 1) {
                        if (getViewOffset() == 0.0f) {
                            DialogsActivity.this.disableActionBarScrolling = true;
                            smoothScrollBy(0, dialogCellFindArchiveDialogCell.getTop() - paddingTop, CubicBezierInterpolator.EASE_OUT_QUINT);
                        }
                        if (!DialogsActivity.this.canShowHiddenArchive) {
                            DialogsActivity.this.canShowHiddenArchive = true;
                            try {
                                performHapticFeedback(3, 2);
                            } catch (Exception unused) {
                            }
                            if (this.parentPage.pullForegroundDrawable != null) {
                                this.parentPage.pullForegroundDrawable.colorize(true);
                            }
                        }
                        dialogCellFindArchiveDialogCell.startOutAnimation();
                        this.parentPage.archivePullViewState = 1;
                        if (ExteraConfig.archiveOnPull) {
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$DialogsRecyclerView$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$onTouchEvent$0();
                                }
                            }, 150L);
                        }
                        if (AndroidUtilities.isAccessibilityScreenReaderEnabled()) {
                            AndroidUtilities.makeAccessibilityAnnouncement(LocaleController.getString(C2369R.string.AccDescrArchivedChatsShown));
                        }
                    }
                    if (getViewOffset() != 0.0f) {
                        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(getViewOffset(), 0.0f);
                        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$DialogsRecyclerView$$ExternalSyntheticLambda1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                this.f$0.lambda$onTouchEvent$1(valueAnimator);
                            }
                        });
                        valueAnimatorOfFloat.setDuration(Math.max(100L, (long) (350.0f - ((getViewOffset() / PullForegroundDrawable.getMaxOverscroll()) * 120.0f))));
                        valueAnimatorOfFloat.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                        setScrollEnabled(false);
                        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.DialogsRecyclerView.1
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                super.onAnimationEnd(animator);
                                DialogsRecyclerView.this.setScrollEnabled(true);
                            }
                        });
                        valueAnimatorOfFloat.start();
                    }
                }
            }
            return zOnTouchEvent;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTouchEvent$0() {
            Bundle bundle = new Bundle();
            bundle.putInt("folderId", 1);
            bundle.putBoolean("onlySelect", DialogsActivity.this.onlySelect);
            DialogsActivity dialogsActivity = new DialogsActivity(bundle);
            dialogsActivity.setDelegate(DialogsActivity.this.delegate);
            DialogsActivity dialogsActivity2 = DialogsActivity.this;
            dialogsActivity2.presentFragment(dialogsActivity, dialogsActivity2.onlySelect);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onTouchEvent$1(ValueAnimator valueAnimator) {
            setViewsOffset(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (this.fastScrollAnimationRunning || DialogsActivity.this.waitingForScrollFinished || this.parentPage.dialogsItemAnimator.isRunning()) {
                return false;
            }
            if (motionEvent.getAction() == 0) {
                DialogsActivity.this.allowSwipeDuringCurrentTouch = !((BaseFragment) r0).actionBar.isActionModeShowed();
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView
        protected boolean allowSelectChildAtPosition(View view) {
            return !(view instanceof HeaderCell) || view.isClickable();
        }

        public void setOpenRightFragmentProgress(float f) {
            this.rightFragmentOpenedProgress = f;
            invalidate();
        }

        public void setAnimationSupportView(RecyclerListView recyclerListView, float f, boolean z, boolean z2) {
            RecyclerListView recyclerListView2 = recyclerListView == null ? this.animationSupportListView : this;
            if (recyclerListView2 == null) {
                this.animationSupportListView = recyclerListView;
                return;
            }
            DialogCell dialogCell = null;
            DialogCell dialogCell2 = null;
            int top = ConnectionsManager.DEFAULT_DATACENTER_ID;
            for (int i = 0; i < recyclerListView2.getChildCount(); i++) {
                View childAt = recyclerListView2.getChildAt(i);
                if (childAt instanceof DialogCell) {
                    DialogCell dialogCell3 = (DialogCell) childAt;
                    if (dialogCell3.getDialogId() == DialogsActivity.this.rightSlidingDialogContainer.getCurrentFragmetDialogId()) {
                        dialogCell = dialogCell3;
                    }
                    if (childAt.getTop() >= 0 && dialogCell3.getDialogId() != 0 && childAt.getTop() < top) {
                        top = dialogCell3.getTop();
                        dialogCell2 = dialogCell3;
                    }
                }
            }
            if (dialogCell == null || getAdapter().getItemCount() * AndroidUtilities.m1146dp(70.0f) <= getMeasuredHeight() || dialogCell2.getTop() - getPaddingTop() <= (getMeasuredHeight() - getPaddingTop()) / 2.0f) {
                dialogCell = dialogCell2;
            }
            this.animationSupportListView = recyclerListView;
            if (dialogCell != null) {
                if (recyclerListView != null) {
                    recyclerListView.setPadding(getPaddingLeft(), this.topPadding, getPaddingLeft(), getPaddingBottom());
                    DialogsAdapter dialogsAdapter = (DialogsAdapter) recyclerListView.getAdapter();
                    int iFindDialogPosition = dialogsAdapter.findDialogPosition(dialogCell.getDialogId());
                    int top2 = (int) ((dialogCell.getTop() - recyclerListView2.getPaddingTop()) + f);
                    if (iFindDialogPosition >= 0) {
                        boolean z3 = this.parentPage.dialogsType == 0 && this.parentPage.archivePullViewState == 2 && DialogsActivity.this.hasHiddenArchive();
                        DialogsActivity dialogsActivity = DialogsActivity.this;
                        ((LinearLayoutManager) recyclerListView.getLayoutManager()).scrollToPositionWithOffset(iFindDialogPosition, dialogsAdapter.fixScrollGap(this, iFindDialogPosition, top2, z3, dialogsActivity.hasStories, dialogsActivity.canShowFilterTabsView, z));
                    }
                }
                int iFindDialogPosition2 = ((DialogsAdapter) getAdapter()).findDialogPosition(dialogCell.getDialogId());
                int top3 = dialogCell.getTop() - getPaddingTop();
                if (z2 && DialogsActivity.this.hasStories) {
                    top3 += AndroidUtilities.m1146dp(81.0f);
                }
                if (iFindDialogPosition2 >= 0) {
                    ((LinearLayoutManager) getLayoutManager()).scrollToPositionWithOffset(iFindDialogPosition2, top3);
                }
            }
        }

        @Override // org.telegram.ui.Stories.StoriesListPlaceProvider.ClippedView
        public void updateClip(int[] iArr) {
            int paddingTop = (int) (getPaddingTop() + DialogsActivity.this.scrollYOffset);
            iArr[0] = paddingTop;
            iArr[1] = paddingTop + getMeasuredHeight();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public StoriesController getStoriesController() {
        return getMessagesController().getStoriesController();
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SwipeController extends ItemTouchHelper.Callback {
        private RecyclerView.ViewHolder currentItemViewHolder;
        private ViewPage parentPage;
        private boolean swipeFolderBack;
        private boolean swipingFolder;

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
            return 0.45f;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public float getSwipeVelocityThreshold(float f) {
            return Float.MAX_VALUE;
        }

        public SwipeController(ViewPage viewPage) {
            this.parentPage = viewPage;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            TLRPC.Dialog dialog;
            if (!DialogsActivity.this.waitingForDialogsAnimationEnd(this.parentPage) && ((((BaseFragment) DialogsActivity.this).parentLayout == null || !((BaseFragment) DialogsActivity.this).parentLayout.isInPreviewMode()) && !DialogsActivity.this.rightSlidingDialogContainer.hasFragment())) {
                if (this.swipingFolder && this.swipeFolderBack) {
                    View view = viewHolder.itemView;
                    if (view instanceof DialogCell) {
                        ((DialogCell) view).swipeCanceled = true;
                    }
                    this.swipingFolder = false;
                    return 0;
                }
                if (!DialogsActivity.this.onlySelect && this.parentPage.isDefaultDialogType() && DialogsActivity.this.slidingView == null) {
                    View view2 = viewHolder.itemView;
                    if (view2 instanceof DialogCell) {
                        DialogCell dialogCell = (DialogCell) view2;
                        long dialogId = dialogCell.getDialogId();
                        MessagesController.DialogFilter dialogFilter = null;
                        if (((BaseFragment) DialogsActivity.this).actionBar.isActionModeShowed(null)) {
                            TLRPC.Dialog dialog2 = (TLRPC.Dialog) DialogsActivity.this.getMessagesController().dialogs_dict.get(dialogId);
                            if (!DialogsActivity.this.allowMoving || dialog2 == null || !DialogsActivity.this.isDialogPinned(dialog2) || DialogObject.isFolderDialogId(dialogId)) {
                                return 0;
                            }
                            DialogsActivity.this.movingView = (DialogCell) viewHolder.itemView;
                            DialogsActivity.this.movingView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                            this.swipeFolderBack = false;
                            return ItemTouchHelper.Callback.makeMovementFlags(3, 0);
                        }
                        int dialogsType = DialogsActivity.this.initialDialogsType;
                        try {
                            dialogsType = this.parentPage.dialogsAdapter.getDialogsType();
                        } catch (Exception unused) {
                        }
                        if ((DialogsActivity.this.filterTabsView == null || DialogsActivity.this.filterTabsView.getVisibility() != 0 || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) != 5) && DialogsActivity.this.allowSwipeDuringCurrentTouch && (((dialogId != DialogsActivity.this.getUserConfig().clientUserId && dialogId != 777000 && dialogsType != 7 && dialogsType != 8) || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) != 2) && (!DialogsActivity.this.getMessagesController().isPromoDialog(dialogId, false) || DialogsActivity.this.getMessagesController().promoDialogType == MessagesController.PROMO_TYPE_PSA))) {
                            if (DialogsActivity.this.folderId != 0 && ExteraConfig.disableUnarchiveSwipe) {
                                return 0;
                            }
                            boolean z = DialogsActivity.this.folderId == 0 && (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 3 || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 1 || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 0 || SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 4) && !DialogsActivity.this.rightSlidingDialogContainer.hasFragment();
                            if (SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 1) {
                                if (DialogsActivity.this.viewPages[0].dialogsType == 7 || DialogsActivity.this.viewPages[0].dialogsType == 8) {
                                    dialogFilter = DialogsActivity.this.getMessagesController().selectedDialogFilter[DialogsActivity.this.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
                                }
                                if (dialogFilter != null && (dialogFilter.flags & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ) != 0 && (dialog = (TLRPC.Dialog) DialogsActivity.this.getMessagesController().dialogs_dict.get(dialogId)) != null && !dialogFilter.alwaysShow(((BaseFragment) DialogsActivity.this).currentAccount, dialog) && (dialog.unread_count > 0 || dialog.unread_mark)) {
                                    z = false;
                                }
                            }
                            this.swipeFolderBack = false;
                            this.swipingFolder = (z && !DialogObject.isFolderDialogId(dialogCell.getDialogId())) || (SharedConfig.archiveHidden && DialogObject.isFolderDialogId(dialogCell.getDialogId()));
                            dialogCell.setSliding(true);
                            return ItemTouchHelper.Callback.makeMovementFlags(0, 4);
                        }
                    }
                }
            }
            return 0;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            View view = viewHolder2.itemView;
            if (!(view instanceof DialogCell)) {
                return false;
            }
            long dialogId = ((DialogCell) view).getDialogId();
            TLRPC.Dialog dialog = (TLRPC.Dialog) DialogsActivity.this.getMessagesController().dialogs_dict.get(dialogId);
            if (dialog == null || !DialogsActivity.this.isDialogPinned(dialog) || DialogObject.isFolderDialogId(dialogId)) {
                return false;
            }
            int adapterPosition = viewHolder.getAdapterPosition();
            int adapterPosition2 = viewHolder2.getAdapterPosition();
            if (this.parentPage.listView.getItemAnimator() == null) {
                ViewPage viewPage = this.parentPage;
                viewPage.listView.setItemAnimator(viewPage.dialogsItemAnimator);
            }
            this.parentPage.dialogsAdapter.moveDialogs(this.parentPage.listView, adapterPosition, adapterPosition2);
            if (DialogsActivity.this.viewPages[0].dialogsType == 7 || DialogsActivity.this.viewPages[0].dialogsType == 8) {
                MessagesController.DialogFilter dialogFilter = DialogsActivity.this.getMessagesController().selectedDialogFilter[DialogsActivity.this.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
                if (!DialogsActivity.this.movingDialogFilters.contains(dialogFilter)) {
                    DialogsActivity.this.movingDialogFilters.add(dialogFilter);
                }
            } else {
                DialogsActivity.this.movingWas = true;
            }
            return true;
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public int convertToAbsoluteDirection(int i, int i2) {
            if (this.swipeFolderBack) {
                return 0;
            }
            return super.convertToAbsoluteDirection(i, i2);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                DialogCell dialogCell = (DialogCell) viewHolder.itemView;
                long dialogId = dialogCell.getDialogId();
                if (DialogObject.isFolderDialogId(dialogId)) {
                    this.parentPage.listView.toggleArchiveHidden(false, dialogCell);
                    return;
                }
                final TLRPC.Dialog dialog = (TLRPC.Dialog) DialogsActivity.this.getMessagesController().dialogs_dict.get(dialogId);
                if (dialog == null) {
                    return;
                }
                if (!DialogsActivity.this.getMessagesController().isPromoDialog(dialogId, false) && DialogsActivity.this.folderId == 0 && SharedConfig.getChatSwipeAction(((BaseFragment) DialogsActivity.this).currentAccount) == 1) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(Long.valueOf(dialogId));
                    DialogsActivity.this.canReadCount = (dialog.unread_count > 0 || dialog.unread_mark) ? 1 : 0;
                    DialogsActivity.this.performSelectedDialogsAction(arrayList, 101, true, false);
                    return;
                }
                DialogsActivity.this.slidingView = dialogCell;
                final int adapterPosition = viewHolder.getAdapterPosition();
                final int itemCount = this.parentPage.dialogsAdapter.getItemCount();
                Runnable runnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$SwipeController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onSwiped$3(dialog, itemCount, adapterPosition);
                    }
                };
                DialogsActivity.this.setDialogsListFrozen(true);
                if (Utilities.random.nextInt(MediaDataController.MAX_STYLE_RUNS_COUNT) == 1) {
                    if (DialogsActivity.this.pacmanAnimation == null) {
                        DialogsActivity.this.pacmanAnimation = new PacmanAnimation(this.parentPage.listView);
                    }
                    DialogsActivity.this.pacmanAnimation.setFinishRunnable(runnable);
                    DialogsActivity.this.pacmanAnimation.start();
                    return;
                }
                runnable.run();
                return;
            }
            DialogsActivity.this.slidingView = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSwiped$3(final TLRPC.Dialog dialog, int i, int i2) {
            if (DialogsActivity.this.frozenDialogsList == null) {
                return;
            }
            DialogsActivity.this.frozenDialogsList.remove(dialog);
            final int i3 = dialog.pinnedNum;
            DialogsActivity.this.slidingView = null;
            this.parentPage.listView.invalidate();
            int iFindLastVisibleItemPosition = this.parentPage.layoutManager.findLastVisibleItemPosition();
            if (iFindLastVisibleItemPosition == i - 1) {
                this.parentPage.layoutManager.findViewByPosition(iFindLastVisibleItemPosition).requestLayout();
            }
            if (DialogsActivity.this.getMessagesController().isPromoDialog(dialog.f1577id, false)) {
                DialogsActivity.this.getMessagesController().hidePromoDialog();
                this.parentPage.dialogsItemAnimator.prepareForRemove();
                this.parentPage.updateList(true);
                return;
            }
            int iAddDialogToFolder = DialogsActivity.this.getMessagesController().addDialogToFolder(dialog.f1577id, DialogsActivity.this.folderId == 0 ? 1 : 0, -1, 0L);
            if (iAddDialogToFolder != 2 || i2 != 0) {
                this.parentPage.dialogsItemAnimator.prepareForRemove();
                this.parentPage.updateList(true);
            }
            if (DialogsActivity.this.folderId == 0) {
                if (iAddDialogToFolder == 2) {
                    if (SharedConfig.archiveHidden) {
                        SharedConfig.toggleArchiveHidden();
                    }
                    this.parentPage.dialogsItemAnimator.prepareForRemove();
                    if (i2 == 0) {
                        DialogsActivity.this.setDialogsListFrozen(true);
                        this.parentPage.updateList(true);
                        DialogsActivity.this.checkAnimationFinished();
                    } else {
                        this.parentPage.updateList(true);
                        if (!SharedConfig.archiveHidden && this.parentPage.layoutManager.findFirstVisibleItemPosition() == 0) {
                            DialogsActivity.this.disableActionBarScrolling = true;
                            this.parentPage.listView.smoothScrollBy(0, -AndroidUtilities.m1146dp(SharedConfig.useThreeLinesLayout ? 78.0f : 72.0f));
                        }
                    }
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    DialogsActivity.this.frozenDialogsList.add(0, (TLRPC.Dialog) dialogsActivity.getDialogsArray(((BaseFragment) dialogsActivity).currentAccount, this.parentPage.dialogsType, DialogsActivity.this.folderId, false).get(0));
                    this.parentPage.updateList(true);
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$SwipeController$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onSwiped$0();
                        }
                    }, 300L);
                } else if (iAddDialogToFolder == 1) {
                    RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = this.parentPage.listView.findViewHolderForAdapterPosition(0);
                    if (viewHolderFindViewHolderForAdapterPosition != null) {
                        View view = viewHolderFindViewHolderForAdapterPosition.itemView;
                        if (view instanceof DialogCell) {
                            DialogCell dialogCell = (DialogCell) view;
                            dialogCell.checkCurrentDialogIndex(true);
                            dialogCell.animateArchiveAvatar();
                        }
                    }
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$SwipeController$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onSwiped$1();
                        }
                    }, 300L);
                }
                SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
                boolean z = globalMainSettings.getBoolean("archivehint_l", false) || SharedConfig.archiveHidden;
                if (!z) {
                    globalMainSettings.edit().putBoolean("archivehint_l", true).apply();
                }
                UndoView undoView = DialogsActivity.this.getUndoView();
                if (undoView != null) {
                    undoView.showWithAction(dialog.f1577id, z ? 2 : 3, null, new Runnable() { // from class: org.telegram.ui.DialogsActivity$SwipeController$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onSwiped$2(dialog, i3);
                        }
                    });
                }
            }
            if (DialogsActivity.this.folderId == 0 || !DialogsActivity.this.frozenDialogsList.isEmpty()) {
                return;
            }
            this.parentPage.listView.setEmptyView(null);
            this.parentPage.progressView.setVisibility(4);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSwiped$0() {
            DialogsActivity.this.setDialogsListFrozen(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSwiped$1() {
            DialogsActivity.this.setDialogsListFrozen(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSwiped$2(TLRPC.Dialog dialog, int i) {
            DialogsActivity.this.dialogsListFrozen = true;
            DialogsActivity.this.getMessagesController().addDialogToFolder(dialog.f1577id, 0, i, 0L);
            DialogsActivity.this.dialogsListFrozen = false;
            ArrayList<TLRPC.Dialog> dialogs = DialogsActivity.this.getMessagesController().getDialogs(0);
            int iIndexOf = dialogs.indexOf(dialog);
            if (iIndexOf >= 0) {
                ArrayList<TLRPC.Dialog> dialogs2 = DialogsActivity.this.getMessagesController().getDialogs(1);
                if (!dialogs2.isEmpty() || iIndexOf != 1) {
                    DialogsActivity.this.setDialogsListFrozen(true);
                    this.parentPage.dialogsItemAnimator.prepareForRemove();
                    this.parentPage.updateList(true);
                    DialogsActivity.this.checkAnimationFinished();
                }
                if (dialogs2.isEmpty()) {
                    dialogs.remove(0);
                    if (iIndexOf == 1) {
                        DialogsActivity.this.setDialogsListFrozen(true);
                        this.parentPage.updateList(true);
                        DialogsActivity.this.checkAnimationFinished();
                        return;
                    } else {
                        if (!DialogsActivity.this.frozenDialogsList.isEmpty()) {
                            DialogsActivity.this.frozenDialogsList.remove(0);
                        }
                        this.parentPage.dialogsItemAnimator.prepareForRemove();
                        this.parentPage.updateList(true);
                        return;
                    }
                }
                return;
            }
            this.parentPage.updateList(false);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                this.parentPage.listView.hideSelector(false);
            }
            this.currentItemViewHolder = viewHolder;
            if (viewHolder != null) {
                View view = viewHolder.itemView;
                if (view instanceof DialogCell) {
                    ((DialogCell) view).swipeCanceled = false;
                }
            }
            super.onSelectedChanged(viewHolder, i);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public long getAnimationDuration(RecyclerView recyclerView, int i, float f, float f2) {
            if (i == 4) {
                return 200L;
            }
            if (i == 8 && DialogsActivity.this.movingView != null) {
                final DialogCell dialogCell = DialogsActivity.this.movingView;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$SwipeController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        dialogCell.setBackgroundDrawable(null);
                    }
                }, this.parentPage.dialogsItemAnimator.getMoveDuration());
                DialogsActivity.this.movingView = null;
            }
            return super.getAnimationDuration(recyclerView, i, f, f2);
        }

        @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
        public float getSwipeEscapeVelocity(float f) {
            return AppUtils.getSwipeVelocity();
        }
    }

    public interface DialogsActivityDelegate {
        boolean canSelectStories();

        boolean didSelectDialogs(DialogsActivity dialogsActivity, ArrayList arrayList, CharSequence charSequence, boolean z, boolean z2, int i, TopicsFragment topicsFragment);

        boolean didSelectStories(DialogsActivity dialogsActivity);

        /* renamed from: org.telegram.ui.DialogsActivity$DialogsActivityDelegate$-CC, reason: invalid class name */
        /* loaded from: classes5.dex */
        public abstract /* synthetic */ class CC {
            public static boolean $default$canSelectStories(DialogsActivityDelegate dialogsActivityDelegate) {
                return false;
            }

            public static boolean $default$didSelectStories(DialogsActivityDelegate dialogsActivityDelegate, DialogsActivity dialogsActivity) {
                return false;
            }
        }
    }

    public DialogsActivity(Bundle bundle) {
        super(bundle);
        this.initialSearchType = -1;
        this.ACTION_MODE_SEARCH_DIALOGS_TAG = "search_dialogs_action_mode";
        this.isFirstTab = true;
        this.allowGlobalSearch = true;
        this.hasStories = false;
        this.hasOnlySlefStories = false;
        this.animateToHasStories = false;
        this.invalidateScrollY = true;
        this.contactsAlpha = 1.0f;
        this.undoView = new UndoView[2];
        this.movingDialogFilters = new ArrayList();
        this.actionBarDefaultPaint = new Paint();
        this.actionModeViews = new ArrayList();
        this.commentViewAnimated = false;
        this.rect = new RectF();
        this.paint = new Paint(1);
        this.textPaint = new TextPaint(1);
        this.askAboutContacts = true;
        this.floatingInterpolator = new AccelerateDecelerateInterpolator();
        this.checkPermission = true;
        this.resetDelegate = true;
        this.openedDialogId = new MessagesStorage.TopicKey();
        this.selectedDialogs = new ArrayList();
        this.notify = true;
        this.notificationsLocker = new AnimationNotificationsLocker();
        this.debugLastUpdateAction = -1;
        this.SCROLL_Y = new AnimationProperties.FloatProperty("animationValue") { // from class: org.telegram.ui.DialogsActivity.1
            @Override // org.telegram.ui.Components.AnimationProperties.FloatProperty
            public void setValue(DialogsActivity dialogsActivity, float f) {
                dialogsActivity.setScrollY(f);
            }

            @Override // android.util.Property
            public Float get(DialogsActivity dialogsActivity) {
                return Float.valueOf(DialogsActivity.this.scrollYOffset);
            }
        };
        this.SEARCH_TRANSLATION_Y = new AnimationProperties.FloatProperty("viewPagerTranslation") { // from class: org.telegram.ui.DialogsActivity.2
            @Override // org.telegram.ui.Components.AnimationProperties.FloatProperty
            public void setValue(View view, float f) {
                DialogsActivity dialogsActivity = DialogsActivity.this;
                dialogsActivity.searchViewPagerTranslationY = f;
                view.setTranslationY(dialogsActivity.panTranslationY + f);
            }

            @Override // android.util.Property
            public Float get(View view) {
                return Float.valueOf(DialogsActivity.this.searchViewPagerTranslationY);
            }
        };
        this.windowInsetsStateHolder = new WindowInsetsStateHolder(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda22
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.checkInsets();
            }
        });
        this.shiftDp = -4;
        this.commentViewPreviousTop = -1;
        this.commentViewIgnoreTopUpdate = false;
        this.scrollBarVisible = true;
        this.storiesEnabled = true;
        this.isNextButton = false;
        this.slideFragmentProgress = 1.0f;
        this.slideAmplitudeDp = Opcodes.ISHL;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        getMessagesController().getBlockedPeersFull();
        Bundle bundle = this.arguments;
        if (bundle != null) {
            this.onlySelect = bundle.getBoolean("onlySelect", false);
            this.canSelectTopics = this.arguments.getBoolean("canSelectTopics", false);
            this.cantSendToChannels = this.arguments.getBoolean("cantSendToChannels", false);
            this.initialDialogsType = this.arguments.getInt("dialogsType", 0);
            this.isQuote = this.arguments.getBoolean("quote", false);
            this.isReplyTo = this.arguments.getBoolean("reply_to", false);
            this.replyMessageAuthor = this.arguments.getLong("reply_to_author", 0L);
            this.forwardOriginalChannel = this.arguments.getLong("forward_into_channel", 0L);
            this.selectAlertString = this.arguments.getString("selectAlertString");
            this.selectAlertStringGroup = this.arguments.getString("selectAlertStringGroup");
            this.addToGroupAlertString = this.arguments.getString("addToGroupAlertString");
            this.allowSwitchAccount = this.arguments.getBoolean("allowSwitchAccount");
            this.checkCanWrite = this.arguments.getBoolean("checkCanWrite", true);
            this.afterSignup = this.arguments.getBoolean("afterSignup", false);
            this.folderId = this.arguments.getInt("folderId", 0);
            this.resetDelegate = this.arguments.getBoolean("resetDelegate", true);
            this.messagesCount = this.arguments.getInt("messagesCount", 0);
            this.hasPoll = this.arguments.getInt("hasPoll", 0);
            this.hasInvoice = this.arguments.getBoolean("hasInvoice", false);
            this.showSetPasswordConfirm = this.arguments.getBoolean("showSetPasswordConfirm", this.showSetPasswordConfirm);
            this.otherwiseReloginDays = this.arguments.getInt("otherwiseRelogin");
            this.allowGroups = this.arguments.getBoolean("allowGroups", true);
            this.allowMegagroups = this.arguments.getBoolean("allowMegagroups", true);
            this.allowLegacyGroups = this.arguments.getBoolean("allowLegacyGroups", true);
            this.allowChannels = this.arguments.getBoolean("allowChannels", true);
            this.allowUsers = this.arguments.getBoolean("allowUsers", true);
            this.allowBots = this.arguments.getBoolean("allowBots", true);
            this.closeFragment = this.arguments.getBoolean("closeFragment", true);
            this.allowGlobalSearch = this.arguments.getBoolean("allowGlobalSearch", true);
            byte[] byteArray = this.arguments.getByteArray("requestPeerType");
            if (byteArray != null) {
                try {
                    SerializedData serializedData = new SerializedData(byteArray);
                    this.requestPeerType = TLRPC.RequestPeerType.TLdeserialize(serializedData, serializedData.readInt32(true), true);
                    serializedData.cleanup();
                } catch (Exception unused) {
                }
            }
            this.requestPeerBotId = this.arguments.getLong("requestPeerBotId", 0L);
        }
        if (this.initialDialogsType == 0) {
            this.askAboutContacts = MessagesController.getGlobalNotificationsSettings().getBoolean("askAboutContacts", true);
            SharedConfig.loadProxyList();
        }
        if (this.searchString == null) {
            this.currentConnectionState = getConnectionsManager().getConnectionState();
            getNotificationCenter().addObserver(this, NotificationCenter.dialogsNeedReload);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
            if (!this.onlySelect) {
                NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.closeSearchByActiveAction);
                NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.proxySettingsChanged);
                getNotificationCenter().addObserver(this, NotificationCenter.filterSettingsUpdated);
                getNotificationCenter().addObserver(this, NotificationCenter.dialogFiltersUpdated);
                getNotificationCenter().addObserver(this, NotificationCenter.dialogsUnreadCounterChanged);
            }
            getNotificationCenter().addObserver(this, NotificationCenter.updateInterfaces);
            getNotificationCenter().addObserver(this, NotificationCenter.encryptedChatUpdated);
            getNotificationCenter().addObserver(this, NotificationCenter.contactsDidLoad);
            getNotificationCenter().addObserver(this, NotificationCenter.appDidLogout);
            getNotificationCenter().addObserver(this, NotificationCenter.openedChatChanged);
            getNotificationCenter().addObserver(this, NotificationCenter.notificationsSettingsUpdated);
            getNotificationCenter().addObserver(this, NotificationCenter.messageReceivedByAck);
            getNotificationCenter().addObserver(this, NotificationCenter.messageReceivedByServer);
            getNotificationCenter().addObserver(this, NotificationCenter.messageSendError);
            getNotificationCenter().addObserver(this, NotificationCenter.needReloadRecentDialogsSearch);
            getNotificationCenter().addObserver(this, NotificationCenter.replyMessagesDidLoad);
            getNotificationCenter().addObserver(this, NotificationCenter.topicsDidLoaded);
            getNotificationCenter().addObserver(this, NotificationCenter.reloadHints);
            getNotificationCenter().addObserver(this, NotificationCenter.didUpdateConnectionState);
            getNotificationCenter().addObserver(this, NotificationCenter.onDownloadingFilesChanged);
            getNotificationCenter().addObserver(this, NotificationCenter.needDeleteDialog);
            getNotificationCenter().addObserver(this, NotificationCenter.folderBecomeEmpty);
            getNotificationCenter().addObserver(this, NotificationCenter.newSuggestionsAvailable);
            getNotificationCenter().addObserver(this, NotificationCenter.fileLoaded);
            getNotificationCenter().addObserver(this, NotificationCenter.fileLoadFailed);
            getNotificationCenter().addObserver(this, NotificationCenter.fileLoadProgressChanged);
            getNotificationCenter().addObserver(this, NotificationCenter.dialogsUnreadReactionsCounterChanged);
            getNotificationCenter().addObserver(this, NotificationCenter.forceImportContactsStart);
            getNotificationCenter().addObserver(this, NotificationCenter.userEmojiStatusUpdated);
            getNotificationCenter().addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.didSetPasscode);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.appUpdateAvailable);
            NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.appUpdateLoading);
        }
        getNotificationCenter().addObserver(this, NotificationCenter.messagesDeleted);
        getNotificationCenter().addObserver(this, NotificationCenter.onDatabaseMigration);
        getNotificationCenter().addObserver(this, NotificationCenter.onDatabaseOpened);
        getNotificationCenter().addObserver(this, NotificationCenter.didClearDatabase);
        getNotificationCenter().addObserver(this, NotificationCenter.onDatabaseReset);
        getNotificationCenter().addObserver(this, NotificationCenter.storiesUpdated);
        getNotificationCenter().addObserver(this, NotificationCenter.storiesEnabledUpdate);
        getNotificationCenter().addObserver(this, NotificationCenter.unconfirmedAuthUpdate);
        getNotificationCenter().addObserver(this, NotificationCenter.premiumPromoUpdated);
        if (this.initialDialogsType == 0) {
            getNotificationCenter().addObserver(this, NotificationCenter.chatlistFolderUpdate);
            getNotificationCenter().addObserver(this, NotificationCenter.dialogTranslate);
        }
        getNotificationCenter().addObserver(this, NotificationCenter.starBalanceUpdated);
        getNotificationCenter().addObserver(this, NotificationCenter.starSubscriptionsLoaded);
        getNotificationCenter().addObserver(this, NotificationCenter.appConfigUpdated);
        getNotificationCenter().addObserver(this, NotificationCenter.activeAuctionsUpdated);
        loadDialogs(getAccountInstance());
        getMessagesController().getStoriesController().loadAllStories();
        getMessagesController().loadPinnedDialogs(this.folderId, 0L, null);
        if (this.databaseMigrationHint != null && !getMessagesStorage().isDatabaseMigrationInProgress()) {
            View view = this.databaseMigrationHint;
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            this.databaseMigrationHint = null;
        }
        if (isArchive()) {
            getMessagesController().getStoriesController().loadHiddenStories();
        } else {
            getMessagesController().getStoriesController().loadStories();
        }
        getContactsController().loadGlobalPrivacySetting();
        if (getMessagesController().savedViewAsChats) {
            getMessagesController().getSavedMessagesController().preloadDialogs(true);
        }
        BirthdayController.getInstance(this.currentAccount).check();
        return true;
    }

    public static void loadDialogs(final AccountInstance accountInstance) {
        int currentAccount = accountInstance.getCurrentAccount();
        if (dialogsLoaded[currentAccount]) {
            return;
        }
        MessagesController messagesController = accountInstance.getMessagesController();
        messagesController.loadGlobalNotificationsSettings();
        messagesController.loadDialogs(0, 0, 100, true);
        messagesController.loadHintDialogs();
        messagesController.loadUserInfo(accountInstance.getUserConfig().getCurrentUser(), false, 0);
        accountInstance.getContactsController().checkInviteText();
        accountInstance.getMediaDataController().checkAllMedia(false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                accountInstance.getDownloadController().loadDownloadingFiles();
            }
        }, 200L);
        Iterator<String> it = messagesController.diceEmojies.iterator();
        while (it.hasNext()) {
            accountInstance.getMediaDataController().loadStickersByEmojiOrName(it.next(), true, true);
        }
        dialogsLoaded[currentAccount] = true;
    }

    public void updateStatus(TLRPC.User user, boolean z) {
        if (this.statusDrawable == null || this.actionBar == null) {
            return;
        }
        Long emojiStatusDocumentId = UserObject.getEmojiStatusDocumentId(user);
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = null;
        this.statusDrawableGiftId = null;
        if (emojiStatusDocumentId != null) {
            boolean z2 = user.emoji_status instanceof TLRPC.TL_emojiStatusCollectible;
            this.statusDrawable.set(emojiStatusDocumentId.longValue(), z);
            this.statusDrawable.setParticles(z2, z);
            if (z2) {
                this.statusDrawableGiftId = Long.valueOf(((TLRPC.TL_emojiStatusCollectible) user.emoji_status).collectible_id);
            }
            this.actionBar.setRightDrawableOnClick(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda71
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$updateStatus$2(view);
                }
            });
            SelectAnimatedEmojiDialog.preload(this.currentAccount);
        } else if (user != null && MessagesController.getInstance(this.currentAccount).isPremiumUser(user)) {
            if (this.premiumStar == null) {
                this.premiumStar = getContext().getResources().getDrawable(C2369R.drawable.msg_premium_liststar).mutate();
                this.premiumStar = new AnimatedEmojiDrawable.WrapSizeDrawable(this.premiumStar, AndroidUtilities.m1146dp(18.0f), AndroidUtilities.m1146dp(18.0f)) { // from class: org.telegram.ui.DialogsActivity.3
                    @Override // org.telegram.ui.Components.AnimatedEmojiDrawable.WrapSizeDrawable, android.graphics.drawable.Drawable
                    public void draw(Canvas canvas) {
                        canvas.save();
                        canvas.translate(AndroidUtilities.m1146dp(-2.0f), AndroidUtilities.m1146dp(1.0f));
                        super.draw(canvas);
                        canvas.restore();
                    }
                };
            }
            this.premiumStar.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_profile_verifiedBackground), PorterDuff.Mode.MULTIPLY));
            this.statusDrawable.set(this.premiumStar, z);
            this.statusDrawable.setParticles(false, z);
            this.actionBar.setRightDrawableOnClick(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda72
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$updateStatus$3(view);
                }
            });
            SelectAnimatedEmojiDialog.preload(this.currentAccount);
        } else {
            this.statusDrawable.set((Drawable) null, z);
            this.statusDrawable.setParticles(false, z);
            this.actionBar.setRightDrawableOnClick(null);
        }
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = this.statusDrawable;
        int i = Theme.key_profile_verifiedBackground;
        swapAnimatedEmojiDrawable2.setColor(Integer.valueOf(Theme.getColor(i)));
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable3 = this.ghostStatusDrawable;
        if (swapAnimatedEmojiDrawable3 != null) {
            swapAnimatedEmojiDrawable3.setColor(Integer.valueOf(Theme.getColor(Theme.key_actionBarDefaultArchivedTitle)));
        }
        DrawerProfileCell.AnimatedStatusView animatedStatusView = this.animatedStatusView;
        if (animatedStatusView != null) {
            animatedStatusView.setColor(Theme.getColor(i));
        }
        SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialogWindow = this.selectAnimatedEmojiDialog;
        if (selectAnimatedEmojiDialogWindow == null || !(selectAnimatedEmojiDialogWindow.getContentView() instanceof SelectAnimatedEmojiDialog)) {
            return;
        }
        SimpleTextView titleTextView = this.actionBar.getTitleTextView();
        SelectAnimatedEmojiDialog selectAnimatedEmojiDialog = (SelectAnimatedEmojiDialog) this.selectAnimatedEmojiDialog.getContentView();
        if (titleTextView != null) {
            Drawable rightDrawable = titleTextView.getRightDrawable();
            AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable4 = this.statusDrawable;
            if (rightDrawable == swapAnimatedEmojiDrawable4) {
                swapAnimatedEmojiDrawable = swapAnimatedEmojiDrawable4;
            }
        }
        selectAnimatedEmojiDialog.setScrimDrawable(swapAnimatedEmojiDrawable, titleTextView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateStatus$2(View view) {
        DialogStoriesCell dialogStoriesCell;
        if (this.dialogStoriesCellVisible && (dialogStoriesCell = this.dialogStoriesCell) != null && !dialogStoriesCell.isExpanded()) {
            scrollToTop(true, true);
        } else {
            showSelectStatusDialog();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateStatus$3(View view) {
        DialogStoriesCell dialogStoriesCell;
        if (this.dialogStoriesCellVisible && (dialogStoriesCell = this.dialogStoriesCell) != null && !dialogStoriesCell.isExpanded()) {
            scrollToTop(true, true);
        } else {
            showSelectStatusDialog();
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        Bulletin.removeDelegate(this);
        if (this.searchString == null) {
            getNotificationCenter().removeObserver(this, NotificationCenter.dialogsNeedReload);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
            if (!this.onlySelect) {
                NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.closeSearchByActiveAction);
                NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.proxySettingsChanged);
                getNotificationCenter().removeObserver(this, NotificationCenter.filterSettingsUpdated);
                getNotificationCenter().removeObserver(this, NotificationCenter.dialogFiltersUpdated);
                getNotificationCenter().removeObserver(this, NotificationCenter.dialogsUnreadCounterChanged);
            }
            getNotificationCenter().removeObserver(this, NotificationCenter.updateInterfaces);
            getNotificationCenter().removeObserver(this, NotificationCenter.encryptedChatUpdated);
            getNotificationCenter().removeObserver(this, NotificationCenter.contactsDidLoad);
            getNotificationCenter().removeObserver(this, NotificationCenter.appDidLogout);
            getNotificationCenter().removeObserver(this, NotificationCenter.openedChatChanged);
            getNotificationCenter().removeObserver(this, NotificationCenter.notificationsSettingsUpdated);
            getNotificationCenter().removeObserver(this, NotificationCenter.messageReceivedByAck);
            getNotificationCenter().removeObserver(this, NotificationCenter.messageReceivedByServer);
            getNotificationCenter().removeObserver(this, NotificationCenter.messageSendError);
            getNotificationCenter().removeObserver(this, NotificationCenter.needReloadRecentDialogsSearch);
            getNotificationCenter().removeObserver(this, NotificationCenter.replyMessagesDidLoad);
            getNotificationCenter().removeObserver(this, NotificationCenter.topicsDidLoaded);
            getNotificationCenter().removeObserver(this, NotificationCenter.reloadHints);
            getNotificationCenter().removeObserver(this, NotificationCenter.didUpdateConnectionState);
            getNotificationCenter().removeObserver(this, NotificationCenter.onDownloadingFilesChanged);
            getNotificationCenter().removeObserver(this, NotificationCenter.needDeleteDialog);
            getNotificationCenter().removeObserver(this, NotificationCenter.folderBecomeEmpty);
            getNotificationCenter().removeObserver(this, NotificationCenter.newSuggestionsAvailable);
            getNotificationCenter().removeObserver(this, NotificationCenter.fileLoaded);
            getNotificationCenter().removeObserver(this, NotificationCenter.fileLoadFailed);
            getNotificationCenter().removeObserver(this, NotificationCenter.fileLoadProgressChanged);
            getNotificationCenter().removeObserver(this, NotificationCenter.dialogsUnreadReactionsCounterChanged);
            getNotificationCenter().removeObserver(this, NotificationCenter.forceImportContactsStart);
            getNotificationCenter().removeObserver(this, NotificationCenter.userEmojiStatusUpdated);
            getNotificationCenter().removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.didSetPasscode);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.appUpdateAvailable);
            NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.appUpdateLoading);
        }
        getNotificationCenter().removeObserver(this, NotificationCenter.messagesDeleted);
        getNotificationCenter().removeObserver(this, NotificationCenter.onDatabaseMigration);
        getNotificationCenter().removeObserver(this, NotificationCenter.onDatabaseOpened);
        getNotificationCenter().removeObserver(this, NotificationCenter.didClearDatabase);
        getNotificationCenter().removeObserver(this, NotificationCenter.onDatabaseReset);
        getNotificationCenter().removeObserver(this, NotificationCenter.storiesUpdated);
        getNotificationCenter().removeObserver(this, NotificationCenter.storiesEnabledUpdate);
        getNotificationCenter().removeObserver(this, NotificationCenter.unconfirmedAuthUpdate);
        getNotificationCenter().removeObserver(this, NotificationCenter.premiumPromoUpdated);
        if (this.initialDialogsType == 0) {
            getNotificationCenter().removeObserver(this, NotificationCenter.chatlistFolderUpdate);
            getNotificationCenter().removeObserver(this, NotificationCenter.dialogTranslate);
        }
        getNotificationCenter().removeObserver(this, NotificationCenter.starBalanceUpdated);
        getNotificationCenter().removeObserver(this, NotificationCenter.starSubscriptionsLoaded);
        getNotificationCenter().removeObserver(this, NotificationCenter.appConfigUpdated);
        getNotificationCenter().removeObserver(this, NotificationCenter.activeAuctionsUpdated);
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.onDestroy();
        }
        UndoView undoView = this.undoView[0];
        if (undoView != null) {
            undoView.hide(true, 0);
        }
        this.notificationsLocker.unlock();
        this.delegate = null;
        SuggestClearDatabaseBottomSheet.dismissDialog();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean dismissDialogOnPause(Dialog dialog) {
        return !(dialog instanceof BotWebViewSheet) && super.dismissDialogOnPause(dialog);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public ActionBar createActionBar(Context context) {
        ActionBar actionBar = new ActionBar(context) { // from class: org.telegram.ui.DialogsActivity.4
            @Override // org.telegram.p023ui.ActionBar.ActionBar, android.view.View
            public void setTranslationY(float f) {
                View view;
                if (f != getTranslationY() && (view = DialogsActivity.this.fragmentView) != null) {
                    view.invalidate();
                }
                super.setTranslationY(f);
            }

            @Override // org.telegram.p023ui.ActionBar.ActionBar
            protected boolean shouldClipChild(View view) {
                return super.shouldClipChild(view) || view == DialogsActivity.this.doneItem;
            }

            @Override // org.telegram.p023ui.ActionBar.ActionBar, android.view.ViewGroup
            protected boolean drawChild(Canvas canvas, View view, long j) {
                if (!((BaseFragment) DialogsActivity.this).inPreviewMode || DialogsActivity.this.avatarContainer == null || view == DialogsActivity.this.avatarContainer) {
                    return super.drawChild(canvas, view, j);
                }
                return false;
            }

            @Override // org.telegram.p023ui.ActionBar.ActionBar
            public void setTitleOverlayText(String str, int i, Runnable runnable) {
                super.setTitleOverlayText(str, i, runnable);
                if (DialogsActivity.this.selectAnimatedEmojiDialog != null && (DialogsActivity.this.selectAnimatedEmojiDialog.getContentView() instanceof SelectAnimatedEmojiDialog)) {
                    SimpleTextView titleTextView = getTitleTextView();
                    ((SelectAnimatedEmojiDialog) DialogsActivity.this.selectAnimatedEmojiDialog.getContentView()).setScrimDrawable((titleTextView == null || titleTextView.getRightDrawable() != DialogsActivity.this.statusDrawable) ? null : DialogsActivity.this.statusDrawable, titleTextView);
                }
                DialogStoriesCell dialogStoriesCell = DialogsActivity.this.dialogStoriesCell;
                if (dialogStoriesCell != null) {
                    dialogStoriesCell.setTitleOverlayText(str, i);
                }
            }

            @Override // org.telegram.p023ui.ActionBar.ActionBar
            protected boolean onSearchChangedIgnoreTitles() {
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                return rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment();
            }

            @Override // org.telegram.p023ui.ActionBar.ActionBar
            public void onSearchFieldVisibilityChanged(boolean z) {
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment()) {
                    getBackButton().animate().alpha(z ? 1.0f : 0.0f).start();
                }
                super.onSearchFieldVisibilityChanged(z);
            }
        };
        actionBar.setUseContainerForTitles();
        actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_actionBarDefaultSelector), false);
        actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefaultSelector), true);
        actionBar.setItemsColor(Theme.getColor(Theme.key_actionBarDefaultIcon), false);
        actionBar.setItemsColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon), true);
        if (!this.inPreviewMode && (!AndroidUtilities.isTablet() || this.folderId == 0 || isArchive())) {
            return actionBar;
        }
        actionBar.setOccupyStatusBar(false);
        return actionBar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Drawable getStatusDrawable() {
        if (AyuConfig.displayGhostStatus && getAyuGhostController().isGhostModeActive()) {
            return this.ghostStatusDrawable;
        }
        return this.statusDrawable;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean isSupportEdgeToEdge() {
        return this.initialDialogsType == 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkInsets() {
        DialogsRecyclerView dialogsRecyclerView;
        FrameLayout.LayoutParams layoutParams;
        FrameLayout.LayoutParams layoutParams2;
        FrameLayout.LayoutParams layoutParams3;
        FrameLayout.LayoutParams layoutParams4;
        FrameLayout.LayoutParams layoutParams5;
        int currentNavigationBarInset = this.windowInsetsStateHolder.getCurrentNavigationBarInset();
        FrameLayout frameLayout = this.floatingButtonContainer;
        if (frameLayout != null && (layoutParams5 = (FrameLayout.LayoutParams) frameLayout.getLayoutParams()) != null) {
            layoutParams5.bottomMargin = AndroidUtilities.m1146dp(14.0f) + currentNavigationBarInset;
            this.floatingButtonContainer.setLayoutParams(layoutParams5);
        }
        FrameLayout frameLayout2 = this.floatingButton2Container;
        if (frameLayout2 != null && (layoutParams4 = (FrameLayout.LayoutParams) frameLayout2.getLayoutParams()) != null) {
            layoutParams4.bottomMargin = AndroidUtilities.m1146dp(82.0f) + currentNavigationBarInset;
            this.floatingButton2Container.setLayoutParams(layoutParams4);
        }
        FrameLayout frameLayout3 = this.updateLayout;
        if (frameLayout3 != null && (layoutParams3 = (FrameLayout.LayoutParams) frameLayout3.getLayoutParams()) != null) {
            layoutParams3.bottomMargin = AndroidUtilities.m1146dp(8.0f) + currentNavigationBarInset;
            this.updateLayout.setLayoutParams(layoutParams3);
        }
        UndoView[] undoViewArr = this.undoView;
        if (undoViewArr != null) {
            for (UndoView undoView : undoViewArr) {
                if (undoView != null) {
                    undoView.setBottomInset(currentNavigationBarInset);
                    FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) undoView.getLayoutParams();
                    if (layoutParams6 != null && layoutParams6.bottomMargin != AndroidUtilities.m1146dp(8.0f) + currentNavigationBarInset) {
                        layoutParams6.bottomMargin = AndroidUtilities.m1146dp(8.0f) + currentNavigationBarInset;
                        undoView.setLayoutParams(layoutParams6);
                    }
                }
            }
        }
        HintView2 hintView2 = this.storyHint;
        if (hintView2 != null && (layoutParams2 = (FrameLayout.LayoutParams) hintView2.getLayoutParams()) != null) {
            layoutParams2.bottomMargin = currentNavigationBarInset;
            this.storyHint.setLayoutParams(layoutParams2);
        }
        HintView2 hintView22 = this.storyPremiumHint;
        if (hintView22 != null && (layoutParams = (FrameLayout.LayoutParams) hintView22.getLayoutParams()) != null) {
            layoutParams.bottomMargin = AndroidUtilities.m1146dp(56.0f) + currentNavigationBarInset;
            this.storyPremiumHint.setLayoutParams(layoutParams);
        }
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager != null) {
            searchViewPager.setPadding(0, 0, 0, currentNavigationBarInset);
        }
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr != null) {
            for (ViewPage viewPage : viewPageArr) {
                if (viewPage != null && (dialogsRecyclerView = viewPage.listView) != null) {
                    dialogsRecyclerView.requestLayout();
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0b80  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x0bb0  */
    /* JADX WARN: Removed duplicated region for block: B:273:0x0bc1  */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0bca  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0bd4  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0be1  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0cdf  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x0d3b  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x0d80  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0d83  */
    /* JADX WARN: Removed duplicated region for block: B:307:0x0d90  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0e0f  */
    /* JADX WARN: Removed duplicated region for block: B:316:0x0e1b  */
    /* JADX WARN: Removed duplicated region for block: B:324:0x0e46  */
    /* JADX WARN: Type inference failed for: r0v173, types: [org.telegram.ui.Components.RecyclerListView, org.telegram.ui.DialogsActivity$DialogsRecyclerView] */
    /* JADX WARN: Type inference failed for: r0v5, types: [org.telegram.ui.ActionBar.ActionBarMenu] */
    /* JADX WARN: Type inference failed for: r0v72, types: [org.telegram.ui.ActionBar.ActionBar] */
    /* JADX WARN: Type inference failed for: r2v12, types: [org.telegram.ui.ActionBar.ActionBar] */
    /* JADX WARN: Type inference failed for: r2v17, types: [org.telegram.ui.ActionBar.ActionBar] */
    /* JADX WARN: Type inference failed for: r2v25, types: [org.telegram.ui.ActionBar.ActionBar] */
    /* JADX WARN: Type inference failed for: r6v20, types: [org.telegram.ui.Components.RecyclerListView, org.telegram.ui.DialogsActivity$DialogsRecyclerView] */
    /* JADX WARN: Type inference failed for: r6v22, types: [org.telegram.ui.Components.RecyclerListView, org.telegram.ui.DialogsActivity$DialogsRecyclerView] */
    /* JADX WARN: Type inference failed for: r6v27, types: [org.telegram.ui.Components.RecyclerListView, org.telegram.ui.DialogsActivity$DialogsRecyclerView] */
    /* JADX WARN: Type inference failed for: r6v28, types: [org.telegram.ui.Components.RecyclerListView, org.telegram.ui.DialogsActivity$DialogsRecyclerView] */
    /* JADX WARN: Type inference failed for: r6v30, types: [androidx.recyclerview.widget.LinearLayoutManager] */
    /* JADX WARN: Type inference failed for: r7v36, types: [android.graphics.drawable.BitmapDrawable] */
    /* JADX WARN: Type inference failed for: r9v0 */
    /* JADX WARN: Type inference failed for: r9v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v20 */
    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.view.View createView(android.content.Context r42) throws android.content.res.Resources.NotFoundException {
        /*
            Method dump skipped, instructions count: 3710
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.createView(android.content.Context):android.view.View");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$5(View view) {
        this.filterTabsView.setIsEditing(false);
        showDoneItem(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$6(View view) {
        getContactsController().loadGlobalPrivacySetting();
        this.optionsItem.toggleSubMenu();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$7() {
        if (this.initialDialogsType != 10) {
            hideFloatingButton(false);
        }
        if (this.hasOnlySlefStories && getStoriesController().hasOnlySelfStories()) {
            this.dialogStoriesCell.openSelfStories();
        } else {
            scrollToTop(true, true);
        }
    }

    /* renamed from: org.telegram.ui.DialogsActivity$6 */
    class C50406 extends FilterTabsView {
        C50406(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context, resourcesProvider);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            getParent().requestDisallowInterceptTouchEvent(true);
            DialogsActivity.this.maybeStartTracking = false;
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            if (getTranslationY() != f) {
                super.setTranslationY(f);
                DialogsActivity.this.updateContextViewPosition();
                View view = DialogsActivity.this.fragmentView;
                if (view != null) {
                    view.invalidate();
                }
            }
        }

        @Override // org.telegram.p023ui.Components.FilterTabsView
        protected void onDefaultTabMoved() {
            if (DialogsActivity.this.getMessagesController().premiumFeaturesBlocked()) {
                return;
            }
            try {
                performHapticFeedback(VibratorUtils.getType(3), 1);
            } catch (Exception unused) {
            }
            DialogsActivity dialogsActivity = DialogsActivity.this;
            dialogsActivity.topBulletin = BulletinFactory.m1267of(dialogsActivity).createSimpleBulletin(C2369R.raw.filter_reorder, AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.LimitReachedReorderFolder, LocaleController.getString(C2369R.string.FilterAllChats))), LocaleController.getString(C2369R.string.PremiumMore), 5000, new Runnable() { // from class: org.telegram.ui.DialogsActivity$6$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onDefaultTabMoved$0();
                }
            }).show(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDefaultTabMoved$0() {
            DialogsActivity.this.showDialog(new PremiumFeatureBottomSheet(DialogsActivity.this, 9, true));
            DialogsActivity.this.filterTabsView.setIsEditing(false);
            DialogsActivity.this.showDoneItem(false);
        }
    }

    /* renamed from: org.telegram.ui.DialogsActivity$7 */
    class C50447 implements FilterTabsView.FilterTabsViewDelegate {
        private int lastTitleType = ExteraConfig.tabIcons;
        final /* synthetic */ Context val$context;

        C50447(Context context) {
            this.val$context = context;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: showDeleteAlert, reason: merged with bridge method [inline-methods] */
        public void lambda$didSelectTab$6(final MessagesController.DialogFilter dialogFilter) {
            if (dialogFilter.isChatlist()) {
                FolderBottomSheet.showForDeletion(DialogsActivity.this, dialogFilter.f1454id, null);
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(DialogsActivity.this.getParentActivity());
            builder.setTitle(LocaleController.getString(C2369R.string.FilterDelete));
            builder.setMessage(LocaleController.getString(C2369R.string.FilterDeleteAlert));
            builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
            builder.setPositiveButton(LocaleController.getString(C2369R.string.Delete), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$7$$ExternalSyntheticLambda6
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$showDeleteAlert$0(dialogFilter, alertDialog, i);
                }
            });
            AlertDialog alertDialogCreate = builder.create();
            DialogsActivity.this.showDialog(alertDialogCreate);
            TextView textView = (TextView) alertDialogCreate.getButton(-1);
            if (textView != null) {
                textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$showDeleteAlert$0(MessagesController.DialogFilter dialogFilter, AlertDialog alertDialog, int i) {
            TLRPC.TL_messages_updateDialogFilter tL_messages_updateDialogFilter = new TLRPC.TL_messages_updateDialogFilter();
            tL_messages_updateDialogFilter.f1697id = dialogFilter.f1454id;
            DialogsActivity.this.getConnectionsManager().sendRequest(tL_messages_updateDialogFilter, null);
            DialogsActivity.this.getMessagesController().removeFilter(dialogFilter);
            DialogsActivity.this.getMessagesStorage().deleteDialogFilter(dialogFilter);
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onSamePageSelected() {
            DialogsActivity.this.scrollToTop(true, false);
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onPageReorder(int i, int i2) {
            for (ViewPage viewPage : DialogsActivity.this.viewPages) {
                if (viewPage.selectedType == i) {
                    viewPage.selectedType = i2;
                } else if (viewPage.selectedType == i2) {
                    viewPage.selectedType = i;
                }
            }
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onPageSelected(FilterTabsView.Tab tab, boolean z) {
            int i;
            if (DialogsActivity.this.viewPages[0].selectedType == tab.f1871id) {
                return;
            }
            if (tab.isLocked) {
                DialogsActivity.this.filterTabsView.shakeLock(tab.f1871id);
                DialogsActivity dialogsActivity = DialogsActivity.this;
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                dialogsActivity.showDialog(new LimitReachedBottomSheet(dialogsActivity2, this.val$context, 3, ((BaseFragment) dialogsActivity2).currentAccount, null));
                return;
            }
            ArrayList<MessagesController.DialogFilter> dialogFilters = DialogsActivity.this.getMessagesController().getDialogFilters();
            if (tab.isDefault || ((i = tab.f1871id) >= 0 && i < dialogFilters.size())) {
                DialogsActivity dialogsActivity3 = DialogsActivity.this;
                dialogsActivity3.isFirstTab = tab.f1871id == dialogsActivity3.filterTabsView.getFirstTabId();
                DialogsActivity.this.updateDrawerSwipeEnabled();
                DialogsActivity.this.viewPages[1].selectedType = tab.f1871id;
                DialogsActivity.this.viewPages[1].setVisibility(0);
                DialogsActivity.this.viewPages[1].setTranslationX(DialogsActivity.this.viewPages[0].getMeasuredWidth());
                DialogsActivity.this.showScrollbars(false);
                DialogsActivity.this.switchToCurrentSelectedMode(true);
                DialogsActivity.this.animatingForward = z;
            }
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public boolean canPerformActions() {
            return !DialogsActivity.this.searching;
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onPageScrolled(float f) {
            if (f != 1.0f || DialogsActivity.this.viewPages[1].getVisibility() == 0 || DialogsActivity.this.searching) {
                if (DialogsActivity.this.animatingForward) {
                    DialogsActivity.this.viewPages[0].setTranslationX((-f) * DialogsActivity.this.viewPages[0].getMeasuredWidth());
                    DialogsActivity.this.viewPages[1].setTranslationX(DialogsActivity.this.viewPages[0].getMeasuredWidth() - (f * DialogsActivity.this.viewPages[0].getMeasuredWidth()));
                } else {
                    DialogsActivity.this.viewPages[0].setTranslationX(DialogsActivity.this.viewPages[0].getMeasuredWidth() * f);
                    DialogsActivity.this.viewPages[1].setTranslationX((f * DialogsActivity.this.viewPages[0].getMeasuredWidth()) - DialogsActivity.this.viewPages[0].getMeasuredWidth());
                }
                if (f == 1.0f) {
                    ViewPage viewPage = DialogsActivity.this.viewPages[0];
                    DialogsActivity.this.viewPages[0] = DialogsActivity.this.viewPages[1];
                    DialogsActivity.this.viewPages[1] = viewPage;
                    DialogsActivity.this.viewPages[1].setVisibility(8);
                    DialogsActivity.this.showScrollbars(true);
                    DialogsActivity.this.updateCounters(false);
                    DialogsActivity.this.filterTabsView.stopAnimatingIndicator();
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    dialogsActivity.checkListLoad(dialogsActivity.viewPages[0]);
                    DialogsActivity.this.viewPages[0].dialogsAdapter.resume();
                    DialogsActivity.this.viewPages[1].dialogsAdapter.pause();
                }
            }
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public int getTabCounter(int i) {
            if (DialogsActivity.this.initialDialogsType != 3 && ExteraConfig.tabCounter) {
                if (i == DialogsActivity.this.filterTabsView.getDefaultTabId()) {
                    return DialogsActivity.this.getMessagesStorage().getMainUnreadCount();
                }
                ArrayList<MessagesController.DialogFilter> dialogFilters = DialogsActivity.this.getMessagesController().getDialogFilters();
                if (i >= 0 && i < dialogFilters.size()) {
                    return DialogsActivity.this.getMessagesController().getDialogFilters().get(i).unreadCount;
                }
            }
            return 0;
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x005b  */
        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public boolean didSelectTab(org.telegram.ui.Components.FilterTabsView.TabView r19, boolean r20) {
            /*
                Method dump skipped, instructions count: 633
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.C50447.didSelectTab(org.telegram.ui.Components.FilterTabsView$TabView, boolean):boolean");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didSelectTab$1() {
            DialogsActivity.this.resetScroll();
            DialogsActivity.this.filterTabsView.setIsEditing(true);
            DialogsActivity.this.showDoneItem(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didSelectTab$2(boolean z, MessagesController.DialogFilter dialogFilter) {
            DialogsActivity.this.presentFragment(z ? new FiltersSetupActivity() : new FilterCreateActivity(dialogFilter));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didSelectTab$3(ArrayList arrayList, boolean z) {
            int i = 0;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                TLRPC.Dialog dialog = (TLRPC.Dialog) arrayList.get(i2);
                if (dialog != null) {
                    DialogsActivity.this.getNotificationsController().setDialogNotificationsSettings(dialog.f1577id, 0L, z ? 3 : 4);
                    i++;
                }
            }
            BulletinFactory.createMuteBulletin(DialogsActivity.this, z, i, (Theme.ResourcesProvider) null).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didSelectTab$4(ArrayList arrayList) {
            DialogsActivity.this.markDialogsAsRead(arrayList);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didSelectTab$5(boolean[] zArr, MessagesController.DialogFilter dialogFilter) {
            if (zArr[0]) {
                DialogsActivity.this.presentFragment(new FilterChatlistActivity(dialogFilter, null));
            } else {
                FilterCreateActivity.FilterInvitesBottomSheet.show(DialogsActivity.this, dialogFilter, null);
            }
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public boolean isTabMenuVisible() {
            return DialogsActivity.this.filterOptions != null && DialogsActivity.this.filterOptions.isShown();
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onDeletePressed(int i) {
            lambda$didSelectTab$6(DialogsActivity.this.getMessagesController().getDialogFilters().get(i));
        }

        @Override // org.telegram.ui.Components.FilterTabsView.FilterTabsViewDelegate
        public void onTabSelected(FilterTabsView.Tab tab, boolean z, boolean z2) {
            if (ExteraConfig.tabIcons != 2) {
                if (this.lastTitleType == 2) {
                    ((BaseFragment) DialogsActivity.this).actionBar.setTitle(LocaleUtils.getActionBarTitle(), DialogsActivity.this.getStatusDrawable());
                    this.lastTitleType = ExteraConfig.tabIcons;
                    return;
                }
                return;
            }
            if (DialogsActivity.this.selectedDialogs.isEmpty()) {
                if (z2) {
                    ((BaseFragment) DialogsActivity.this).actionBar.setTitleAnimatedX(tab.isDefault ? DialogsActivity.this.actionBarDefaultTitle : tab.realTitle, (tab.isDefault || AyuConfig.displayGhostStatus) ? DialogsActivity.this.getStatusDrawable() : null, z, 350);
                } else {
                    ((BaseFragment) DialogsActivity.this).actionBar.setTitle(tab.isDefault ? DialogsActivity.this.actionBarDefaultTitle : tab.realTitle, (tab.isDefault || AyuConfig.displayGhostStatus) ? DialogsActivity.this.getStatusDrawable() : null);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.DialogsActivity$8 */
    class C50458 extends ActionBar.ActionBarMenuOnItemClick {
        C50458() {
        }

        @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
        public void onItemClick(int i) {
            if ((i == 201 || i == 200 || i == 202 || i == 203) && DialogsActivity.this.searchViewPager != null) {
                DialogsActivity.this.searchViewPager.onActionBarItemClick(i);
                return;
            }
            if (i == -1) {
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment()) {
                    if (((BaseFragment) DialogsActivity.this).actionBar.isActionModeShowed()) {
                        if (DialogsActivity.this.searchViewPager != null && DialogsActivity.this.searchViewPager.getVisibility() == 0 && DialogsActivity.this.searchViewPager.actionModeShowing()) {
                            DialogsActivity.this.searchViewPager.hideActionMode();
                            return;
                        } else {
                            DialogsActivity.this.hideActionMode(true);
                            return;
                        }
                    }
                    DialogsActivity.this.rightSlidingDialogContainer.lambda$presentFragment$1();
                    if (DialogsActivity.this.searchViewPager != null) {
                        DialogsActivity.this.searchViewPager.updateTabs();
                        return;
                    }
                    return;
                }
                if (DialogsActivity.this.filterTabsView == null || !DialogsActivity.this.filterTabsView.isEditing()) {
                    if (((BaseFragment) DialogsActivity.this).actionBar.isActionModeShowed()) {
                        if (DialogsActivity.this.searchViewPager != null && DialogsActivity.this.searchViewPager.getVisibility() == 0 && DialogsActivity.this.searchViewPager.actionModeShowing()) {
                            DialogsActivity.this.searchViewPager.hideActionMode();
                            return;
                        } else {
                            DialogsActivity.this.hideActionMode(true);
                            return;
                        }
                    }
                    if (!DialogsActivity.this.onlySelect && DialogsActivity.this.folderId == 0) {
                        if (((BaseFragment) DialogsActivity.this).parentLayout == null || ((BaseFragment) DialogsActivity.this).parentLayout.getDrawerLayoutContainer() == null) {
                            return;
                        }
                        ((BaseFragment) DialogsActivity.this).parentLayout.getDrawerLayoutContainer().openDrawer(false);
                        return;
                    }
                    DialogsActivity.this.lambda$onBackPressed$371();
                    return;
                }
                DialogsActivity.this.filterTabsView.setIsEditing(false);
                DialogsActivity.this.showDoneItem(false);
                return;
            }
            if (i == 1) {
                if (DialogsActivity.this.getParentActivity() == null) {
                    return;
                }
                SharedConfig.appLocked = true;
                SharedConfig.saveConfig();
                int[] iArr = new int[2];
                DialogsActivity.this.passcodeItem.getLocationInWindow(iArr);
                ((LaunchActivity) DialogsActivity.this.getParentActivity()).showPasscodeActivity(false, true, iArr[0] + (DialogsActivity.this.passcodeItem.getMeasuredWidth() / 2), iArr[1] + (DialogsActivity.this.passcodeItem.getMeasuredHeight() / 2), new Runnable() { // from class: org.telegram.ui.DialogsActivity$8$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onItemClick$0();
                    }
                }, new Runnable() { // from class: org.telegram.ui.DialogsActivity$8$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onItemClick$1();
                    }
                });
                DialogsActivity.this.getNotificationsController().showNotifications();
                DialogsActivity.this.updatePasscodeButton();
                return;
            }
            if (i == 2) {
                DialogsActivity.this.presentFragment(new ProxyListActivity());
                return;
            }
            if (i == 3) {
                DialogsActivity.this.showSearch(true, true, true);
                ((BaseFragment) DialogsActivity.this).actionBar.openSearchField(true);
                return;
            }
            if (i == 5) {
                DialogsActivity.this.presentFragment(new ArchiveSettingsActivity());
                return;
            }
            if (i == 6) {
                DialogsActivity.this.showArchiveHelp();
                return;
            }
            if (i >= 10 && i < 26) {
                if (DialogsActivity.this.getParentActivity() == null) {
                    return;
                }
                DialogsActivityDelegate dialogsActivityDelegate = DialogsActivity.this.delegate;
                LaunchActivity launchActivity = (LaunchActivity) DialogsActivity.this.getParentActivity();
                launchActivity.switchToAccount(i - 10, true);
                DialogsActivity dialogsActivity = new DialogsActivity(((BaseFragment) DialogsActivity.this).arguments);
                dialogsActivity.setDelegate(dialogsActivityDelegate);
                launchActivity.presentFragment(dialogsActivity, false, true);
                return;
            }
            if (i == 109) {
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                FiltersListBottomSheet filtersListBottomSheet = new FiltersListBottomSheet(dialogsActivity2, dialogsActivity2.selectedDialogs);
                filtersListBottomSheet.setDelegate(new FiltersListBottomSheet.FiltersListBottomSheetDelegate() { // from class: org.telegram.ui.DialogsActivity$8$$ExternalSyntheticLambda2
                    @Override // org.telegram.ui.Components.FiltersListBottomSheet.FiltersListBottomSheetDelegate
                    public final void didSelectFilter(MessagesController.DialogFilter dialogFilter, boolean z) {
                        this.f$0.lambda$onItemClick$2(dialogFilter, z);
                    }
                });
                DialogsActivity.this.showDialog(filtersListBottomSheet);
                return;
            }
            if (i != 110) {
                if (i == 100 || i == 101 || i == 102 || i == 103 || i == 104 || i == 105 || i == 106 || i == 107 || i == 108) {
                    DialogsActivity dialogsActivity3 = DialogsActivity.this;
                    dialogsActivity3.performSelectedDialogsAction(dialogsActivity3.selectedDialogs, i, true, false);
                    return;
                }
                return;
            }
            MessagesController.DialogFilter dialogFilter = DialogsActivity.this.getMessagesController().getDialogFilters().get(DialogsActivity.this.viewPages[0].selectedType);
            DialogsActivity dialogsActivity4 = DialogsActivity.this;
            ArrayList dialogsCount = FiltersListBottomSheet.getDialogsCount(dialogsActivity4, dialogFilter, dialogsActivity4.selectedDialogs, false, false);
            if ((dialogFilter != null ? dialogFilter.neverShow.size() : 0) + dialogsCount.size() > 100) {
                DialogsActivity dialogsActivity5 = DialogsActivity.this;
                dialogsActivity5.showDialog(AlertsCreator.createSimpleAlert(dialogsActivity5.getParentActivity(), LocaleController.getString(C2369R.string.FilterAddToAlertFullTitle), LocaleController.getString(C2369R.string.FilterAddToAlertFullText)).create());
                return;
            }
            if (!dialogsCount.isEmpty()) {
                dialogFilter.neverShow.addAll(dialogsCount);
                for (int i2 = 0; i2 < dialogsCount.size(); i2++) {
                    Long l = (Long) dialogsCount.get(i2);
                    dialogFilter.alwaysShow.remove(l);
                    dialogFilter.pinnedDialogs.delete(l.longValue());
                }
                if (dialogFilter.isChatlist()) {
                    dialogFilter.neverShow.clear();
                }
                FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, false, false, DialogsActivity.this, null);
            }
            long jLongValue = dialogsCount.size() == 1 ? ((Long) dialogsCount.get(0)).longValue() : 0L;
            UndoView undoView = DialogsActivity.this.getUndoView();
            if (undoView != null) {
                undoView.showWithAction(jLongValue, 21, Integer.valueOf(dialogsCount.size()), dialogFilter, (Runnable) null, (Runnable) null);
            }
            DialogsActivity.this.hideActionMode(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$0() {
            DialogsActivity.this.passcodeItem.setAlpha(1.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$1() {
            DialogsActivity.this.passcodeItem.setAlpha(0.0f);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onItemClick$2(MessagesController.DialogFilter dialogFilter, boolean z) {
            C50458 c50458;
            boolean z2;
            ArrayList arrayList;
            ArrayList arrayList2;
            DialogsActivity dialogsActivity = DialogsActivity.this;
            ArrayList dialogsCount = FiltersListBottomSheet.getDialogsCount(dialogsActivity, dialogFilter, dialogsActivity.selectedDialogs, true, false);
            if (!z) {
                int size = (dialogFilter != null ? dialogFilter.alwaysShow.size() : 0) + dialogsCount.size();
                if ((size > DialogsActivity.this.getMessagesController().dialogFiltersChatsLimitDefault && !DialogsActivity.this.getUserConfig().isPremium()) || size > DialogsActivity.this.getMessagesController().dialogFiltersChatsLimitPremium) {
                    DialogsActivity dialogsActivity2 = DialogsActivity.this;
                    DialogsActivity dialogsActivity3 = DialogsActivity.this;
                    dialogsActivity2.showDialog(new LimitReachedBottomSheet(dialogsActivity3, dialogsActivity3.fragmentView.getContext(), 4, ((BaseFragment) DialogsActivity.this).currentAccount, null));
                    return;
                }
            }
            if (dialogFilter != null) {
                if (z) {
                    for (int i = 0; i < DialogsActivity.this.selectedDialogs.size(); i++) {
                        dialogFilter.neverShow.add((Long) DialogsActivity.this.selectedDialogs.get(i));
                        dialogFilter.alwaysShow.remove(DialogsActivity.this.selectedDialogs.get(i));
                    }
                    FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, true, false, DialogsActivity.this, null);
                    long jLongValue = DialogsActivity.this.selectedDialogs.size() == 1 ? ((Long) DialogsActivity.this.selectedDialogs.get(0)).longValue() : 0L;
                    UndoView undoView = DialogsActivity.this.getUndoView();
                    if (undoView != null) {
                        undoView.showWithAction(jLongValue, 21, Integer.valueOf(DialogsActivity.this.selectedDialogs.size()), dialogFilter, (Runnable) null, (Runnable) null);
                    }
                    c50458 = this;
                    z2 = true;
                } else {
                    if (dialogsCount.isEmpty()) {
                        arrayList = dialogsCount;
                        z2 = true;
                    } else {
                        for (int i2 = 0; i2 < dialogsCount.size(); i2++) {
                            dialogFilter.neverShow.remove(dialogsCount.get(i2));
                        }
                        dialogFilter.alwaysShow.addAll(dialogsCount);
                        arrayList = dialogsCount;
                        z2 = true;
                        FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, true, false, DialogsActivity.this, null);
                    }
                    if (arrayList.size() == z2) {
                        arrayList2 = arrayList;
                        jLongValue = ((Long) arrayList2.get(0)).longValue();
                    } else {
                        arrayList2 = arrayList;
                    }
                    c50458 = this;
                    long j = jLongValue;
                    UndoView undoView2 = DialogsActivity.this.getUndoView();
                    if (undoView2 != null) {
                        undoView2.showWithAction(j, 20, Integer.valueOf(arrayList2.size()), dialogFilter, (Runnable) null, (Runnable) null);
                    }
                }
            } else {
                c50458 = this;
                z2 = true;
                DialogsActivity.this.presentFragment(new FilterCreateActivity(null, dialogsCount));
            }
            DialogsActivity.this.hideActionMode(z2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ WindowInsetsCompat lambda$createView$8(View view, WindowInsetsCompat windowInsetsCompat) {
        this.windowInsetsStateHolder.setInsets(windowInsetsCompat);
        return WindowInsetsCompat.CONSUMED;
    }

    /* renamed from: org.telegram.ui.DialogsActivity$11 */
    class C498811 extends LinearLayoutManager {
        private boolean fixOffset;
        boolean lastDragging;
        ValueAnimator storiesOverscrollAnimator;
        final /* synthetic */ ViewPage val$viewPage;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C498811(Context context, ViewPage viewPage) {
            super(context);
            this.val$viewPage = viewPage;
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        protected int firstPosition() {
            return (this.val$viewPage.dialogsType == 0 && DialogsActivity.this.hasHiddenArchive() && this.val$viewPage.archivePullViewState == 2) ? 1 : 0;
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public void scrollToPositionWithOffset(int i, int i2) {
            if (this.fixOffset) {
                i2 -= this.val$viewPage.listView.getPaddingTop();
            }
            super.scrollToPositionWithOffset(i, i2);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.ItemTouchHelper.ViewDropHandler
        public void prepareForDrop(View view, View view2, int i, int i2) {
            this.fixOffset = true;
            super.prepareForDrop(view, view2, i, i2);
            this.fixOffset = false;
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
            if (DialogsActivity.this.hasHiddenArchive() && i == 1) {
                super.smoothScrollToPosition(recyclerView, state, i);
                return;
            }
            LinearSmoothScrollerCustom linearSmoothScrollerCustom = new LinearSmoothScrollerCustom(recyclerView.getContext(), 0);
            linearSmoothScrollerCustom.setTargetPosition(i);
            startSmoothScroll(linearSmoothScrollerCustom);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onScrollStateChanged(int i) {
            super.onScrollStateChanged(i);
            ValueAnimator valueAnimator = this.storiesOverscrollAnimator;
            if (valueAnimator != null) {
                valueAnimator.removeAllListeners();
                this.storiesOverscrollAnimator.cancel();
            }
            if (this.val$viewPage.listView.getScrollState() != 1) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(DialogsActivity.this.storiesOverscroll, 0.0f);
                this.storiesOverscrollAnimator = valueAnimatorOfFloat;
                final ViewPage viewPage = this.val$viewPage;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$11$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$onScrollStateChanged$0(viewPage, valueAnimator2);
                    }
                });
                this.storiesOverscrollAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.11.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        C498811 c498811 = C498811.this;
                        DialogsActivity.this.setStoriesOvercroll(c498811.val$viewPage, 0.0f);
                    }
                });
                this.storiesOverscrollAnimator.setDuration(200L);
                this.storiesOverscrollAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
                this.storiesOverscrollAnimator.start();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onScrollStateChanged$0(ViewPage viewPage, ValueAnimator valueAnimator) {
            DialogsActivity.this.setStoriesOvercroll(viewPage, ((Float) valueAnimator.getAnimatedValue()).floatValue());
        }

        /* JADX WARN: Removed duplicated region for block: B:55:0x00cb  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x00cf  */
        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public int scrollVerticallyBy(int r21, androidx.recyclerview.widget.RecyclerView.Recycler r22, androidx.recyclerview.widget.RecyclerView.State r23) {
            /*
                Method dump skipped, instructions count: 1146
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.C498811.scrollVerticallyBy(int, androidx.recyclerview.widget.RecyclerView$Recycler, androidx.recyclerview.widget.RecyclerView$State):int");
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            boolean z = BuildVars.DEBUG_VERSION;
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                FileLog.m1160e(e);
                final ViewPage viewPage = this.val$viewPage;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$11$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        viewPage.dialogsAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$9(ViewPage viewPage, View view, int i, float f, float f2) {
        if (view instanceof GraySectionCell) {
            return;
        }
        boolean z = view instanceof DialogCell;
        if (z) {
            DialogCell dialogCell = (DialogCell) view;
            if (dialogCell.isBlocked()) {
                showPremiumBlockedToast(view, dialogCell.getDialogId());
                return;
            }
        }
        if (clickSelectsDialog()) {
            onItemLongClick(viewPage.listView, view, i, 0.0f, 0.0f, viewPage.dialogsType, viewPage.dialogsAdapter);
            return;
        }
        int i2 = this.initialDialogsType;
        if (i2 == 15 && (view instanceof TextCell)) {
            viewPage.dialogsAdapter.onCreateGroupForThisClick();
            return;
        }
        if ((i2 == 11 || i2 == 13) && i == 1) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("forImport", true);
            bundle.putLongArray("result", new long[]{getUserConfig().getClientUserId()});
            bundle.putInt("chatType", 4);
            String string = this.arguments.getString("importTitle");
            if (string != null) {
                bundle.putString("title", string);
            }
            GroupCreateFinalActivity groupCreateFinalActivity = new GroupCreateFinalActivity(bundle);
            groupCreateFinalActivity.setDelegate(new GroupCreateFinalActivity.GroupCreateFinalActivityDelegate() { // from class: org.telegram.ui.DialogsActivity.12
                @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
                public void didFailChatCreation() {
                }

                @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
                public void didStartChatCreation() {
                }

                @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
                public void didFinishChatCreation(GroupCreateFinalActivity groupCreateFinalActivity2, long j) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(MessagesStorage.TopicKey.m1182of(-j, 0L));
                    DialogsActivityDelegate dialogsActivityDelegate = DialogsActivity.this.delegate;
                    if (DialogsActivity.this.closeFragment) {
                        DialogsActivity.this.removeSelfFromStack();
                    }
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    dialogsActivityDelegate.didSelectDialogs(dialogsActivity, arrayList, null, true, dialogsActivity.notify, dialogsActivity.scheduleDate, null);
                }
            });
            presentFragment(groupCreateFinalActivity);
            return;
        }
        if ((view instanceof DialogsHintCell) && (viewPage.dialogsType == 7 || viewPage.dialogsType == 8)) {
            TL_chatlists.TL_chatlists_chatlistUpdates chatlistUpdate = viewPage.dialogsAdapter.getChatlistUpdate();
            if (chatlistUpdate != null) {
                MessagesController.DialogFilter dialogFilter = getMessagesController().selectedDialogFilter[viewPage.dialogsType - 7];
                if (dialogFilter != null) {
                    showDialog(new FolderBottomSheet(this, dialogFilter.f1454id, chatlistUpdate));
                    return;
                }
                return;
            }
        } else if (z && !this.actionBar.isActionModeShowed() && !this.rightSlidingDialogContainer.hasFragment()) {
            DialogCell dialogCell2 = (DialogCell) view;
            AndroidUtilities.rectTmp.set(dialogCell2.avatarImage.getImageX(), dialogCell2.avatarImage.getImageY(), dialogCell2.avatarImage.getImageX2(), dialogCell2.avatarImage.getImageY2());
        }
        onItemClick(view, i, viewPage.dialogsAdapter, f, f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$10(View view, int i) {
        this.filtersView.cancelClickRunnables(true);
        addSearchFilter(this.filtersView.getFilterAt(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$11(View view) throws IOException {
        INavigationLayout iNavigationLayout = this.parentLayout;
        if (iNavigationLayout != null && iNavigationLayout.isInPreviewMode()) {
            finishPreviewFragment();
            return;
        }
        if (MessagesController.getInstance(this.currentAccount).isFrozen()) {
            AccountFrozenAlert.show(this.currentAccount);
        } else if (!this.storiesEnabled) {
            openStoriesRecorder();
        } else {
            openWriteContacts();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$12(View view) throws IOException {
        INavigationLayout iNavigationLayout = this.parentLayout;
        if (iNavigationLayout != null && iNavigationLayout.isInPreviewMode()) {
            finishPreviewFragment();
            return;
        }
        if (this.initialDialogsType == 10) {
            if (this.delegate == null || this.selectedDialogs.isEmpty()) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < this.selectedDialogs.size(); i++) {
                arrayList.add(MessagesStorage.TopicKey.m1182of(((Long) this.selectedDialogs.get(i)).longValue(), 0L));
            }
            this.delegate.didSelectDialogs(this, arrayList, null, false, this.notify, this.scheduleDate, null);
            return;
        }
        if (this.floatingButton.getVisibility() != 0) {
            return;
        }
        if (MessagesController.getInstance(this.currentAccount).isFrozen()) {
            AccountFrozenAlert.show(this.currentAccount);
        } else if (!this.storiesEnabled) {
            openWriteContacts();
        } else {
            openStoriesRecorder();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$13() {
        MessagesController.getInstance(this.currentAccount).getMainSettings().edit().putBoolean("storyhint", false).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$14(Long l) {
        this.cacheSize = l;
        updateDialogsHint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$15(Long l, Long l2) {
        this.deviceSize = l;
        updateDialogsHint();
    }

    /* renamed from: org.telegram.ui.DialogsActivity$21 */
    /* loaded from: classes5.dex */
    class C499921 implements ChatActivityEnterView.ChatActivityEnterViewDelegate {
        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void bottomPanelTranslationYChanged(float f) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ boolean checkCanRemoveRestrictionsByBoosts() {
            return ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$checkCanRemoveRestrictionsByBoosts(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void didPressAttachButton() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ void didPressSuggestionButton() {
            ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$didPressSuggestionButton(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ int getContentViewHeight() {
            return ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$getContentViewHeight(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ TLRPC.Peer getDefaultSendAs() {
            return ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$getDefaultSendAs(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ ChatActivity.ReplyQuote getReplyQuote() {
            return ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$getReplyQuote(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ TL_stories.StoryItem getReplyToStory() {
            return ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$getReplyToStory(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ TLRPC.TL_channels_sendAsPeers getSendAsPeers() {
            return ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$getSendAsPeers(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ boolean hasForwardingMessages() {
            return ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$hasForwardingMessages(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ boolean hasScheduledMessages() {
            return ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$hasScheduledMessages(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public boolean isVideoRecordingPaused() {
            return false;
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ int measureKeyboardHeight() {
            return ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$measureKeyboardHeight(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needChangeVideoPreviewState(int i, float f) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needSendTyping() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needShowMediaBanHint() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needStartRecordAudio(int i) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void needStartRecordVideo(int i, boolean z, int i2, int i3, int i4, long j, long j2) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onAttachButtonHidden() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onAttachButtonShow() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onAudioVideoInterfaceUpdated() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ void onContextMenuClose() {
            ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$onContextMenuClose(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ void onContextMenuOpen() {
            ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$onContextMenuOpen(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ void onEditTextScroll() {
            ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$onEditTextScroll(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ void onKeyboardRequested() {
            ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$onKeyboardRequested(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onMessageEditEnd(boolean z) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onPreAudioVideoRecord() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onSendLongClick() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onStickersExpandedChange() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onStickersTab(boolean z) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onSwitchRecordMode(boolean z) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onTextSelectionChanged(int i, int i2) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onTextSpansChanged(CharSequence charSequence) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ void onTrendingStickersShowed(boolean z) {
            ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$onTrendingStickersShowed(this, z);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onUpdateSlowModeButton(View view, boolean z, CharSequence charSequence) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onWindowSizeChanged(int i) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ boolean onceVoiceAvailable() {
            return ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$onceVoiceAvailable(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ void openScheduledMessages() {
            ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$openScheduledMessages(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ void prepareMessageSending() {
            ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$prepareMessageSending(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ void scrollToSendingMessage() {
            ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$scrollToSendingMessage(this);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public /* synthetic */ boolean setDefaultSendAs(long j, long j2) {
            return ChatActivityEnterView.ChatActivityEnterViewDelegate.CC.$default$setDefaultSendAs(this, j, j2);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void setFrontface(boolean z) {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void toggleVideoRecordingPause() {
        }

        C499921() {
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onMessageSend(CharSequence charSequence, boolean z, int i, int i2, long j) {
            if (DialogsActivity.this.delegate == null || DialogsActivity.this.selectedDialogs.isEmpty()) {
                return;
            }
            ArrayList arrayList = new ArrayList();
            for (int i3 = 0; i3 < DialogsActivity.this.selectedDialogs.size(); i3++) {
                arrayList.add(MessagesStorage.TopicKey.m1182of(((Long) DialogsActivity.this.selectedDialogs.get(i3)).longValue(), 0L));
            }
            DialogsActivity.this.delegate.didSelectDialogs(DialogsActivity.this, arrayList, charSequence, false, z, i, null);
        }

        @Override // org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate
        public void onTextChanged(CharSequence charSequence, boolean z, boolean z2) {
            final DialogsActivity dialogsActivity = DialogsActivity.this;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$21$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    dialogsActivity.updateSelectedCount();
                }
            }, 100L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$16(View view) {
        if (this.delegate == null || this.selectedDialogs.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.selectedDialogs.size(); i++) {
            arrayList.add(MessagesStorage.TopicKey.m1182of(((Long) this.selectedDialogs.get(i)).longValue(), 0L));
        }
        this.delegate.didSelectDialogs(this, arrayList, this.commentView.getFieldText(), false, this.notify, this.scheduleDate, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createView$17(View view) {
        if (this.isNextButton) {
            return false;
        }
        onSendLongClick(this.writeButton);
        return true;
    }

    /* renamed from: org.telegram.ui.DialogsActivity$23 */
    class C500123 extends DialogStoriesCell {
        C500123(Context context, BaseFragment baseFragment, int i, int i2) {
            super(context, baseFragment, i, i2);
        }

        /* JADX WARN: Removed duplicated region for block: B:101:0x02d9  */
        /* JADX WARN: Removed duplicated region for block: B:107:0x02fc  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x01e6  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x01ef  */
        /* JADX WARN: Removed duplicated region for block: B:57:0x01f1  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x0207  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x0209  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x021d  */
        /* JADX WARN: Removed duplicated region for block: B:65:0x021f  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0228  */
        /* JADX WARN: Removed duplicated region for block: B:69:0x022b  */
        /* JADX WARN: Removed duplicated region for block: B:74:0x0240  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x0268  */
        /* JADX WARN: Removed duplicated region for block: B:87:0x028f  */
        /* JADX WARN: Removed duplicated region for block: B:95:0x02b3  */
        @Override // org.telegram.p023ui.Stories.DialogStoriesCell
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void onUserLongPressed(final android.view.View r32, final long r33) {
            /*
                Method dump skipped, instructions count: 836
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.C500123.onUserLongPressed(android.view.View, long):void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$0() {
            DialogsActivity.this.dialogStoriesCell.openStoryRecorder();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$1() {
            Bundle bundle = new Bundle();
            bundle.putLong("dialog_id", UserConfig.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getClientUserId());
            bundle.putInt(PluginsConstants.Settings.TYPE, 1);
            bundle.putInt("start_from", 9);
            DialogsActivity.this.presentFragment(new MediaActivity(bundle, null));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$2() {
            Bundle bundle = new Bundle();
            bundle.putLong("dialog_id", UserConfig.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getClientUserId());
            bundle.putInt(PluginsConstants.Settings.TYPE, 1);
            DialogsActivity.this.presentFragment(new MediaActivity(bundle, null));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$3(long j) {
            DialogsActivity.this.dialogStoriesCell.openStoryRecorder(j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$4(long j) {
            DialogsActivity.this.presentFragment(ChatActivity.m1258of(j));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$5(long j) {
            DialogsActivity.this.presentFragment(ProfileActivity.m1314of(j));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$6(long j) {
            DialogsActivity.this.presentFragment(ChatActivity.m1258of(j));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$7(String str, long j, TLRPC.User user) {
            MessagesController.getNotificationsSettings(((BaseFragment) DialogsActivity.this).currentAccount).edit().putBoolean(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + str, false).apply();
            DialogsActivity.this.getNotificationsController().updateServerNotificationsSettings(j, 0L);
            String strTrim = user == null ? "" : user.first_name.trim();
            int iIndexOf = strTrim.indexOf(" ");
            if (iIndexOf > 0) {
                strTrim = strTrim.substring(0, iIndexOf);
            }
            BulletinFactory.m1267of(DialogsActivity.this).createUsersBulletin(Arrays.asList(user), AndroidUtilities.replaceTags(LocaleController.formatString("NotificationsStoryMutedHint", C2369R.string.NotificationsStoryMutedHint, strTrim))).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$8(String str, long j, TLRPC.User user) {
            MessagesController.getNotificationsSettings(((BaseFragment) DialogsActivity.this).currentAccount).edit().putBoolean(NotificationsSettingsFacade.PROPERTY_STORIES_NOTIFY + str, true).apply();
            DialogsActivity.this.getNotificationsController().updateServerNotificationsSettings(j, 0L);
            String strTrim = user == null ? "" : user.first_name.trim();
            int iIndexOf = strTrim.indexOf(" ");
            if (iIndexOf > 0) {
                strTrim = strTrim.substring(0, iIndexOf);
            }
            BulletinFactory.m1267of(DialogsActivity.this).createUsersBulletin(Arrays.asList(user), AndroidUtilities.replaceTags(LocaleController.formatString("NotificationsStoryUnmutedHint", C2369R.string.NotificationsStoryUnmutedHint, strTrim))).show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$10(final View view) {
            TL_stories.TL_storiesStealthMode stealthMode = MessagesController.getInstance(UserConfig.selectedAccount).getStoriesController().getStealthMode();
            if (stealthMode != null && ConnectionsManager.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getCurrentTime() < stealthMode.active_until_date) {
                if (view instanceof DialogStoriesCell.StoryCell) {
                    DialogsActivity.this.dialogStoriesCell.openStoryForCell((DialogStoriesCell.StoryCell) view);
                }
            } else {
                StealthModeAlert stealthModeAlert = new StealthModeAlert(getContext(), 0.0f, 1, ((BaseFragment) DialogsActivity.this).resourceProvider);
                stealthModeAlert.setListener(new StealthModeAlert.Listener() { // from class: org.telegram.ui.DialogsActivity$23$$ExternalSyntheticLambda15
                    @Override // org.telegram.ui.Stories.StealthModeAlert.Listener
                    public final void onButtonClicked(boolean z) {
                        this.f$0.lambda$onUserLongPressed$9(view, z);
                    }
                });
                DialogsActivity.this.showDialog(stealthModeAlert);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$9(View view, boolean z) {
            if (view instanceof DialogStoriesCell.StoryCell) {
                DialogsActivity.this.dialogStoriesCell.openStoryForCell((DialogStoriesCell.StoryCell) view);
                if (z) {
                    AndroidUtilities.runOnUIThread(new DialogsActivity$23$$ExternalSyntheticLambda16(), 500L);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$12(final View view) {
            StealthModeAlert stealthModeAlert = new StealthModeAlert(getContext(), 0.0f, 1, ((BaseFragment) DialogsActivity.this).resourceProvider);
            stealthModeAlert.setListener(new StealthModeAlert.Listener() { // from class: org.telegram.ui.DialogsActivity$23$$ExternalSyntheticLambda14
                @Override // org.telegram.ui.Stories.StealthModeAlert.Listener
                public final void onButtonClicked(boolean z) {
                    this.f$0.lambda$onUserLongPressed$11(view, z);
                }
            });
            DialogsActivity.this.showDialog(stealthModeAlert);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$11(View view, boolean z) {
            if (view instanceof DialogStoriesCell.StoryCell) {
                DialogsActivity.this.dialogStoriesCell.openStoryForCell((DialogStoriesCell.StoryCell) view);
                if (z) {
                    AndroidUtilities.runOnUIThread(new DialogsActivity$23$$ExternalSyntheticLambda16(), 500L);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$13(long j) {
            DialogsActivity.this.toggleArciveForStory(j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$14(long j) {
            DialogsActivity.this.toggleArciveForStory(j);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onUserLongPressed$15(long j) {
            MediaDataController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).removePeer(j);
            DialogsActivity.this.getMessagesController().getStoriesController().toggleHidden(j, true, false, true);
        }

        @Override // org.telegram.p023ui.Stories.DialogStoriesCell
        /* renamed from: onMiniListClicked */
        public void lambda$new$0() {
            DialogsActivity dialogsActivity = DialogsActivity.this;
            if (dialogsActivity.hasOnlySlefStories && dialogsActivity.getStoriesController().hasOnlySelfStories()) {
                DialogsActivity.this.dialogStoriesCell.openSelfStories();
            } else {
                DialogsActivity.this.scrollToTop(true, true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$18(View view) {
        if (SharedConfig.isAppUpdateAvailable()) {
            ApplicationLoader.applicationLoaderInstance.openApkInstall(getParentActivity(), SharedConfig.pendingAppUpdate.document);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$19(View view) {
        finishPreviewFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setStoriesOvercroll(ViewPage viewPage, float f) {
        if (this.storiesOverscroll == f) {
            return;
        }
        this.storiesOverscroll = f;
        if (f == 0.0f) {
            this.storiesOverscrollCalled = false;
        }
        this.dialogStoriesCell.setOverscoll(f);
        viewPage.listView.setViewsOffset(f);
        viewPage.listView.setOverScrollMode(f != 0.0f ? 2 : 0);
        this.fragmentView.invalidate();
        if (f <= AndroidUtilities.m1146dp(90.0f) || this.storiesOverscrollCalled) {
            return;
        }
        this.storiesOverscrollCalled = true;
        getOrCreateStoryViewer().doOnAnimationReady(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda107
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setStoriesOvercroll$20();
            }
        });
        this.dialogStoriesCell.openOverscrollSelectedStory();
        try {
            this.dialogStoriesCell.performHapticFeedback(3);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setStoriesOvercroll$20() {
        this.fragmentView.dispatchTouchEvent(AndroidUtilities.emptyMotionEvent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toggleArciveForStory(final long j) {
        final boolean z = !isArchive();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda132
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$toggleArciveForStory$23(j, z);
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toggleArciveForStory$23(final long j, final boolean z) {
        String name;
        TLRPC.Chat chat;
        getMessagesController().getStoriesController().toggleHidden(j, z, false, true);
        BulletinFactory.UndoObject undoObject = new BulletinFactory.UndoObject();
        undoObject.onUndo = new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda143
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$toggleArciveForStory$21(j, z);
            }
        };
        undoObject.onAction = new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda144
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$toggleArciveForStory$22(j, z);
            }
        };
        if (j >= 0) {
            TLRPC.User user = getMessagesController().getUser(Long.valueOf(j));
            name = ContactsController.formatName(user.first_name, null, 15);
            chat = user;
        } else {
            TLRPC.Chat chat2 = getMessagesController().getChat(Long.valueOf(-j));
            name = chat2.title;
            chat = chat2;
        }
        this.storiesBulletin = BulletinFactory.global().createUsersBulletin(Collections.singletonList(chat), isArchive() ? AndroidUtilities.replaceTags(LocaleController.formatString("StoriesMovedToDialogs", C2369R.string.StoriesMovedToDialogs, name)) : AndroidUtilities.replaceTags(LocaleController.formatString("StoriesMovedToContacts", C2369R.string.StoriesMovedToContacts, ContactsController.formatName(name, null, 15))), null, undoObject).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toggleArciveForStory$21(long j, boolean z) {
        getMessagesController().getStoriesController().toggleHidden(j, !z, false, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toggleArciveForStory$22(long j, boolean z) {
        getMessagesController().getStoriesController().toggleHidden(j, z, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkAutoscrollToStories(ViewPage viewPage) {
        FilterTabsView filterTabsView;
        if ((!this.hasStories && ((filterTabsView = this.filterTabsView) == null || filterTabsView.getVisibility() != 0)) || this.rightSlidingDialogContainer.hasFragment()) {
            return false;
        }
        int i = (int) (-this.scrollYOffset);
        int maxScrollYOffset = getMaxScrollYOffset();
        if (i == 0 || i == maxScrollYOffset) {
            return false;
        }
        if (i < maxScrollYOffset / 2) {
            if (!viewPage.listView.canScrollVertically(-1)) {
                return false;
            }
            viewPage.scroller.smoothScrollBy(-i);
            return true;
        }
        if (!viewPage.listView.canScrollVertically(1)) {
            return false;
        }
        viewPage.scroller.smoothScrollBy(maxScrollYOffset - i);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public float getActionBarMoveFrom(boolean z) {
        float fM1146dp = this.hasStories ? 0.0f + AndroidUtilities.m1146dp(81.0f) : 0.0f;
        if (z) {
            fM1146dp += AndroidUtilities.m1146dp(48.0f);
        }
        DialogsHintCell dialogsHintCell = this.dialogsHintCell;
        if (dialogsHintCell != null && dialogsHintCell.getVisibility() == 0) {
            fM1146dp += this.dialogsHintCell.getMeasuredHeight();
        }
        return (this.authHintCell == null || !this.authHintCellVisible) ? fM1146dp : fM1146dp + r3.getMeasuredHeight();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getMaxScrollYOffset() {
        if (this.hasStories) {
            return AndroidUtilities.m1146dp(81.0f);
        }
        return ActionBar.getCurrentActionBarHeight();
    }

    private boolean isCacheHintVisible() {
        if (this.cacheSize != null && this.deviceSize != null) {
            if (r0.longValue() / this.deviceSize.longValue() < 0.3f) {
                clearCacheHintVisible();
                return false;
            }
            if (System.currentTimeMillis() > MessagesController.getGlobalMainSettings().getLong("cache_hint_showafter", 0L)) {
                return true;
            }
        }
        return false;
    }

    private void resetCacheHintVisible() {
        SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
        long j = globalMainSettings.getLong("cache_hint_period", 604800000L);
        if (j <= 604800000) {
            j = 2592000000L;
        }
        globalMainSettings.edit().putLong("cache_hint_showafter", System.currentTimeMillis() + j).putLong("cache_hint_period", j).apply();
    }

    private void clearCacheHintVisible() {
        MessagesController.getGlobalMainSettings().edit().remove("cache_hint_showafter").remove("cache_hint_period").apply();
    }

    public void showSelectStatusDialog() {
        int iCenterX;
        int i;
        if (this.selectAnimatedEmojiDialog != null || SharedConfig.appLocked || ((this.hasStories && !this.dialogStoriesCell.isExpanded()) || getStatusDrawable() == this.ghostStatusDrawable)) {
            return;
        }
        final SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] selectAnimatedEmojiDialogWindowArr = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[1];
        TLRPC.User currentUser = UserConfig.getInstance(UserConfig.selectedAccount).getCurrentUser();
        SimpleTextView titleTextView = this.actionBar.getTitleTextView();
        if (titleTextView == null || titleTextView.getRightDrawable() == null) {
            iCenterX = 0;
            i = 0;
        } else {
            this.statusDrawable.play();
            this.statusDrawable.getDrawable();
            Rect rect = AndroidUtilities.rectTmp2;
            rect.set(titleTextView.getRightDrawable().getBounds());
            rect.offset((int) titleTextView.getX(), (int) titleTextView.getY());
            int iM1146dp = (-(this.actionBar.getHeight() - rect.centerY())) - AndroidUtilities.m1146dp(16.0f);
            iCenterX = rect.centerX() - AndroidUtilities.m1146dp(16.0f);
            DrawerProfileCell.AnimatedStatusView animatedStatusView = this.animatedStatusView;
            if (animatedStatusView != null) {
                animatedStatusView.translate(rect.centerX(), rect.centerY());
            }
            i = iM1146dp;
        }
        SelectAnimatedEmojiDialog selectAnimatedEmojiDialog = new SelectAnimatedEmojiDialog(this, getContext(), true, Integer.valueOf(iCenterX), 0, getResourceProvider()) { // from class: org.telegram.ui.DialogsActivity.27
            @Override // org.telegram.p023ui.SelectAnimatedEmojiDialog
            protected boolean willApplyEmoji(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
                return tL_starGiftUnique == null || StarsController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).findUserStarGift(tL_starGiftUnique.f1755id) == null || MessagesController.getGlobalMainSettings().getInt("statusgiftpage", 0) >= 2;
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // org.telegram.p023ui.SelectAnimatedEmojiDialog
            protected void onEmojiSelected(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
                TLRPC.TL_emojiStatus tL_emojiStatus;
                TLRPC.EmojiStatus tL_emojiStatusEmpty;
                if (l == null) {
                    tL_emojiStatusEmpty = new TLRPC.TL_emojiStatusEmpty();
                } else {
                    if (tL_starGiftUnique != null) {
                        TL_stars.SavedStarGift savedStarGiftFindUserStarGift = StarsController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).findUserStarGift(tL_starGiftUnique.f1755id);
                        if (savedStarGiftFindUserStarGift != null && MessagesController.getGlobalMainSettings().getInt("statusgiftpage", 0) < 2) {
                            MessagesController.getGlobalMainSettings().edit().putInt("statusgiftpage", MessagesController.getGlobalMainSettings().getInt("statusgiftpage", 0) + 1).apply();
                            new StarGiftSheet(getContext(), ((BaseFragment) DialogsActivity.this).currentAccount, UserConfig.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getClientUserId(), ((BaseFragment) DialogsActivity.this).resourceProvider).set(savedStarGiftFindUserStarGift, (StarsController.IGiftsList) null).setupWearPage().show();
                            if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                                DialogsActivity.this.selectAnimatedEmojiDialog = null;
                                selectAnimatedEmojiDialogWindowArr[0].dismiss();
                                return;
                            }
                            return;
                        }
                        TLRPC.TL_inputEmojiStatusCollectible tL_inputEmojiStatusCollectible = new TLRPC.TL_inputEmojiStatusCollectible();
                        tL_inputEmojiStatusCollectible.collectible_id = tL_starGiftUnique.f1755id;
                        tL_emojiStatus = tL_inputEmojiStatusCollectible;
                        if (num != null) {
                            tL_inputEmojiStatusCollectible.flags |= 1;
                            tL_inputEmojiStatusCollectible.until = num.intValue();
                            tL_emojiStatus = tL_inputEmojiStatusCollectible;
                        }
                    } else {
                        TLRPC.TL_emojiStatus tL_emojiStatus2 = new TLRPC.TL_emojiStatus();
                        tL_emojiStatus2.document_id = l.longValue();
                        tL_emojiStatus = tL_emojiStatus2;
                        if (num != null) {
                            tL_emojiStatus2.flags |= 1;
                            tL_emojiStatus2.until = num.intValue();
                            tL_emojiStatus = tL_emojiStatus2;
                        }
                    }
                    tL_emojiStatusEmpty = tL_emojiStatus;
                }
                DialogsActivity.this.getMessagesController().updateEmojiStatus(tL_emojiStatusEmpty, tL_starGiftUnique);
                if (l != null) {
                    DialogsActivity.this.animatedStatusView.animateChange(ReactionsLayoutInBubble.VisibleReaction.fromCustomEmoji(l));
                }
                if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                    DialogsActivity.this.selectAnimatedEmojiDialog = null;
                    selectAnimatedEmojiDialogWindowArr[0].dismiss();
                }
            }
        };
        if (currentUser != null && DialogObject.getEmojiStatusUntil(currentUser.emoji_status) > 0) {
            selectAnimatedEmojiDialog.setExpireDateHint(DialogObject.getEmojiStatusUntil(currentUser.emoji_status));
        }
        Long l = this.statusDrawableGiftId;
        if (l != null) {
            selectAnimatedEmojiDialog.setSelected(l);
        } else {
            selectAnimatedEmojiDialog.setSelected(this.statusDrawable.getDrawable() instanceof AnimatedEmojiDrawable ? Long.valueOf(((AnimatedEmojiDrawable) this.statusDrawable.getDrawable()).getDocumentId()) : null);
        }
        selectAnimatedEmojiDialog.setSaveState(1);
        selectAnimatedEmojiDialog.setScrimDrawable(this.statusDrawable, titleTextView);
        int i2 = -2;
        SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialogWindow = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow(selectAnimatedEmojiDialog, i2, i2) { // from class: org.telegram.ui.DialogsActivity.28
            @Override // org.telegram.ui.SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow, android.widget.PopupWindow
            public void dismiss() {
                super.dismiss();
                DialogsActivity.this.selectAnimatedEmojiDialog = null;
            }
        };
        this.selectAnimatedEmojiDialog = selectAnimatedEmojiDialogWindow;
        selectAnimatedEmojiDialogWindowArr[0] = selectAnimatedEmojiDialogWindow;
        selectAnimatedEmojiDialogWindow.showAsDropDown(this.actionBar, AndroidUtilities.m1146dp(16.0f), i, 48);
        selectAnimatedEmojiDialogWindowArr[0].dimBehind();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showPremiumBlockedToast(View view, long j) {
        String userName;
        Bulletin bulletinCreateSimpleBulletin;
        int i = -this.shiftDp;
        this.shiftDp = i;
        AndroidUtilities.shakeViewSpring(view, i);
        BotWebViewVibrationEffect.APP_ERROR.vibrate();
        if (j < 0) {
            userName = "";
        } else {
            userName = UserObject.getUserName(MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(j)));
        }
        if (getMessagesController().premiumFeaturesBlocked()) {
            bulletinCreateSimpleBulletin = BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.star_premium_2, AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.UserBlockedNonPremium, userName)));
        } else {
            bulletinCreateSimpleBulletin = BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.star_premium_2, AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.UserBlockedNonPremium, userName)), LocaleController.getString(C2369R.string.UserBlockedNonPremiumButton), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda131
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showPremiumBlockedToast$24();
                }
            });
        }
        bulletinCreateSimpleBulletin.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showPremiumBlockedToast$24() {
        if (LaunchActivity.getLastFragment() != null) {
            presentFragment(new PremiumPreviewFragment("noncontacts"));
        }
    }

    private void updateAuthHintCellVisibility(final boolean z) {
        final int top;
        if (this.authHintCellVisible != z) {
            this.authHintCellVisible = z;
            if (this.authHintCell == null) {
                return;
            }
            ValueAnimator valueAnimator = this.authHintCellAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.authHintCellAnimator = null;
            }
            if (z) {
                this.authHintCell.setVisibility(0);
            }
            this.authHintCell.setAlpha(1.0f);
            this.viewPages[0].listView.requestLayout();
            this.fragmentView.requestLayout();
            this.notificationsLocker.lock();
            this.authHintCellAnimating = true;
            final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.authHintCellProgress, z ? 1.0f : 0.0f);
            final int iFindFirstVisibleItemPosition = this.viewPages[0].layoutManager.findFirstVisibleItemPosition();
            if (iFindFirstVisibleItemPosition != -1) {
                top = (z ? 0 : -this.authHintCell.getMeasuredHeight()) + this.viewPages[0].layoutManager.findViewByPosition(iFindFirstVisibleItemPosition).getTop();
            } else {
                top = 0;
            }
            AndroidUtilities.doOnLayout(this.fragmentView, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda67
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$updateAuthHintCellVisibility$27(z, iFindFirstVisibleItemPosition, top, valueAnimatorOfFloat);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAuthHintCellVisibility$27(final boolean z, int i, int i2, ValueAnimator valueAnimator) {
        View viewFindViewByPosition;
        final float measuredHeight = this.authHintCell.getMeasuredHeight();
        if (!z && (viewFindViewByPosition = this.viewPages[0].layoutManager.findViewByPosition(i)) != null) {
            measuredHeight += i2 - viewFindViewByPosition.getTop();
        }
        this.viewPages[0].listView.setTranslationY(this.authHintCellProgress * measuredHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda108
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$updateAuthHintCellVisibility$26(measuredHeight, valueAnimator2);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.29
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                DialogsActivity.this.notificationsLocker.unlock();
                DialogsActivity.this.authHintCellAnimating = false;
                DialogsActivity.this.authHintCellProgress = z ? 1.0f : 0.0f;
                View view = DialogsActivity.this.fragmentView;
                if (view != null) {
                    view.requestLayout();
                }
                DialogsActivity.this.viewPages[0].listView.requestLayout();
                DialogsActivity.this.viewPages[0].listView.setTranslationY(0.0f);
                if (z) {
                    return;
                }
                DialogsActivity.this.authHintCell.setVisibility(8);
            }
        });
        valueAnimator.setDuration(250L);
        valueAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
        valueAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateAuthHintCellVisibility$26(float f, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.authHintCellProgress = fFloatValue;
        this.viewPages[0].listView.setTranslationY(f * fFloatValue);
        updateContextViewPosition();
    }

    private void updateActiveGiftAuctionsHintCellVisibility(boolean z) {
        if (this.activeGiftAuctionsHintCellVisible != z) {
            this.activeGiftAuctionsHintCellVisible = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0493  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0446  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateDialogsHint() {
        /*
            Method dump skipped, instructions count: 1196
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.updateDialogsHint():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$30(View view) {
        AccountFrozenAlert.show(getContext(), this.currentAccount, getResourceProvider());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$31(View view) {
        PasskeysActivity.showLearnSheet(getContext(), this.currentAccount, this.resourceProvider, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$32(View view) {
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "SETUP_PASSKEY");
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(200L);
        TransitionManager.beginDelayedTransition((ViewGroup) this.dialogsHintCell.getParent(), changeBounds);
        updateDialogsHint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$35(TLRPC.TL_pendingSuggestion tL_pendingSuggestion, View view) {
        Browser.openUrl(getContext(), tL_pendingSuggestion.url);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$36(TLRPC.TL_pendingSuggestion tL_pendingSuggestion, View view) {
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, tL_pendingSuggestion.suggestion);
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(200L);
        TransitionManager.beginDelayedTransition((ViewGroup) this.dialogsHintCell.getParent(), changeBounds);
        updateDialogsHint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$40(BirthdayController.BirthdayState birthdayState, View view) {
        if (birthdayState != null && birthdayState.today.size() == 1) {
            showDialog(new GiftSheet(getContext(), this.currentAccount, birthdayState.today.get(0).f1734id, null, null).setBirthday());
        } else {
            UserSelectorBottomSheet.open(0L, birthdayState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$41(View view) {
        BirthdayController.getInstance(this.currentAccount).hide();
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "BIRTHDAY_CONTACTS_TODAY");
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(200L);
        TransitionManager.beginDelayedTransition((ViewGroup) this.dialogsHintCell.getParent(), changeBounds);
        updateDialogsHint();
        BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.gift, LocaleController.getString(C2369R.string.BoostingPremiumChristmasToast), 4).setDuration(5000).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$46(View view) {
        showDialog(AlertsCreator.createBirthdayPickerDialog(getContext(), LocaleController.getString(C2369R.string.EditProfileBirthdayTitle), LocaleController.getString(C2369R.string.EditProfileBirthdayButton), null, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda64
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$updateDialogsHint$44((TL_account.TL_birthday) obj);
            }
        }, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda65
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDialogsHint$45();
            }
        }, false, getResourceProvider()).create());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$44(TL_account.TL_birthday tL_birthday) {
        TL_account.updateBirthday updatebirthday = new TL_account.updateBirthday();
        updatebirthday.flags |= 1;
        updatebirthday.birthday = tL_birthday;
        final TLRPC.UserFull userFull = getMessagesController().getUserFull(getUserConfig().getClientUserId());
        final TL_account.TL_birthday tL_birthday2 = userFull != null ? userFull.birthday : null;
        if (userFull != null) {
            userFull.flags2 |= 32;
            userFull.birthday = tL_birthday;
        }
        getMessagesController().invalidateContentSettings();
        getConnectionsManager().sendRequest(updatebirthday, new RequestDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda103
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$updateDialogsHint$43(userFull, tL_birthday2, tLObject, tL_error);
            }
        }, 1024);
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "BIRTHDAY_SETUP");
        updateDialogsHint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$43(final TLRPC.UserFull userFull, final TL_account.TL_birthday tL_birthday, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda114
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDialogsHint$42(tLObject, userFull, tL_birthday, tL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$42(TLObject tLObject, TLRPC.UserFull userFull, TL_account.TL_birthday tL_birthday, TLRPC.TL_error tL_error) {
        String str;
        if (tLObject instanceof TLRPC.TL_boolTrue) {
            BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.gift, LocaleController.getString(C2369R.string.PrivacyBirthdaySetDone), LocaleController.getString(C2369R.string.PrivacyBirthdaySetDoneInfo)).setDuration(5000).show();
            return;
        }
        if (userFull != null) {
            if (tL_birthday == null) {
                userFull.flags2 &= -33;
            } else {
                userFull.flags2 |= 32;
            }
            userFull.birthday = tL_birthday;
            getMessagesStorage().updateUserInfo(userFull, false);
        }
        if (tL_error != null && (str = tL_error.text) != null && str.startsWith("FLOOD_WAIT_")) {
            if (getContext() != null) {
                showDialog(new AlertDialog.Builder(getContext(), this.resourceProvider).setTitle(LocaleController.getString(C2369R.string.PrivacyBirthdayTooOftenTitle)).setMessage(LocaleController.getString(C2369R.string.PrivacyBirthdayTooOftenMessage)).setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), null).create());
                return;
            }
            return;
        }
        BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.error, LocaleController.getString(C2369R.string.UnknownError)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$45() {
        BaseFragment.BottomSheetParams bottomSheetParams = new BaseFragment.BottomSheetParams();
        bottomSheetParams.transitionFromLeft = true;
        bottomSheetParams.allowNestedScroll = false;
        showAsSheet(new PrivacyControlActivity(11), bottomSheetParams);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$48(View view) {
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "BIRTHDAY_SETUP");
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(200L);
        TransitionManager.beginDelayedTransition((ViewGroup) this.dialogsHintCell.getParent(), changeBounds);
        updateDialogsHint();
        BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.chats_infotip, LocaleController.getString(C2369R.string.BirthdaySetupLater), LocaleController.getString(C2369R.string.Settings), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda59
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDialogsHint$47();
            }
        }).setDuration(5000).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$47() {
        presentFragment(new UserInfoActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$56(View view) {
        presentFragment(new CacheControlActivity());
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda94
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDialogsHint$55();
            }
        }, 250L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$55() {
        resetCacheHintVisible();
        updateDialogsHint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$57(View view) {
        openSetAvatar();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$58(View view) {
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "USERPIC_SETUP");
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(200L);
        TransitionManager.beginDelayedTransition((ViewGroup) this.dialogsHintCell.getParent(), changeBounds);
        updateDialogsHint();
    }

    public static /* synthetic */ void $r8$lambda$0bky1Hgr6kz2UGC5fTajKURck9Y(String str, View view) {
        ApplicationLoader applicationLoader = ApplicationLoader.applicationLoaderInstance;
        if (applicationLoader != null) {
            applicationLoader.onSuggestionClick(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$61(final String str, View view) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda73
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$updateDialogsHint$60(str);
            }
        }, 250L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateDialogsHint$60(String str) {
        MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, str);
        updateDialogsHint();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createGroupForThis() {
        long[] jArr;
        final AlertDialog alertDialog = new AlertDialog(getContext(), 3);
        TLRPC.RequestPeerType requestPeerType = this.requestPeerType;
        if (requestPeerType instanceof TLRPC.TL_requestPeerTypeBroadcast) {
            Bundle bundle = new Bundle();
            bundle.putInt("step", 0);
            Boolean bool = this.requestPeerType.has_username;
            if (bool != null) {
                bundle.putBoolean("forcePublic", bool.booleanValue());
            }
            final ChannelCreateActivity channelCreateActivity = new ChannelCreateActivity(bundle);
            channelCreateActivity.setOnFinishListener(new Utilities.Callback2() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda121
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.lambda$createGroupForThis$71(channelCreateActivity, alertDialog, (BaseFragment) obj, (Long) obj2);
                }
            });
            presentFragment(channelCreateActivity);
            return;
        }
        if (requestPeerType instanceof TLRPC.TL_requestPeerTypeChat) {
            Bundle bundle2 = new Bundle();
            Boolean bool2 = this.requestPeerType.bot_participant;
            if (bool2 != null && bool2.booleanValue()) {
                jArr = new long[]{getUserConfig().getClientUserId(), this.requestPeerBotId};
            } else {
                jArr = new long[]{getUserConfig().getClientUserId()};
            }
            bundle2.putLongArray("result", jArr);
            Boolean bool3 = this.requestPeerType.forum;
            bundle2.putInt("chatType", (bool3 == null || !bool3.booleanValue()) ? 4 : 5);
            bundle2.putBoolean("canToggleTopics", false);
            GroupCreateFinalActivity groupCreateFinalActivity = new GroupCreateFinalActivity(bundle2);
            groupCreateFinalActivity.setDelegate(new C500931(alertDialog));
            presentFragment(groupCreateFinalActivity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGroupForThis$71(final ChannelCreateActivity channelCreateActivity, final AlertDialog alertDialog, final BaseFragment baseFragment, final Long l) {
        Utilities.doCallbacks(new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda134
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$createGroupForThis$63(l, channelCreateActivity, baseFragment, (Runnable) obj);
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda135
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$createGroupForThis$65(alertDialog, l, (Runnable) obj);
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda136
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$createGroupForThis$67(l, (Runnable) obj);
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda137
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$createGroupForThis$69(l, (Runnable) obj);
            }
        }, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda138
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$createGroupForThis$70(alertDialog, l, channelCreateActivity, baseFragment, (Runnable) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGroupForThis$63(Long l, final ChannelCreateActivity channelCreateActivity, final BaseFragment baseFragment, Runnable runnable) {
        showSendToBotAlert(getMessagesController().getChat(l), runnable, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda145
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createGroupForThis$62(channelCreateActivity, baseFragment);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGroupForThis$62(ChannelCreateActivity channelCreateActivity, BaseFragment baseFragment) {
        removeSelfFromStack();
        channelCreateActivity.removeSelfFromStack();
        baseFragment.lambda$onBackPressed$371();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGroupForThis$65(AlertDialog alertDialog, Long l, final Runnable runnable) {
        alertDialog.showDelayed(150L);
        Boolean bool = this.requestPeerType.bot_participant;
        if (bool != null && bool.booleanValue()) {
            getMessagesController().addUserToChat(l.longValue(), getMessagesController().getUser(Long.valueOf(this.requestPeerBotId)), 0, null, this, false, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda146
                @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                public final boolean run(TLRPC.TL_error tL_error) {
                    return DialogsActivity.$r8$lambda$1593kncsC4YC8Y7q2mZghqofMu8(runnable, tL_error);
                }
            });
        } else {
            runnable.run();
        }
    }

    public static /* synthetic */ boolean $r8$lambda$1593kncsC4YC8Y7q2mZghqofMu8(Runnable runnable, TLRPC.TL_error tL_error) {
        runnable.run();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGroupForThis$67(Long l, final Runnable runnable) {
        if (this.requestPeerType.bot_admin_rights != null) {
            TLRPC.User user = getMessagesController().getUser(Long.valueOf(this.requestPeerBotId));
            MessagesController messagesController = getMessagesController();
            long jLongValue = l.longValue();
            TLRPC.RequestPeerType requestPeerType = this.requestPeerType;
            TLRPC.TL_chatAdminRights tL_chatAdminRights = requestPeerType.bot_admin_rights;
            Boolean bool = requestPeerType.bot_participant;
            messagesController.setUserAdminRole(jLongValue, user, tL_chatAdminRights, null, false, this, bool == null || !bool.booleanValue(), true, null, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda142
                @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                public final boolean run(TLRPC.TL_error tL_error) {
                    return DialogsActivity.$r8$lambda$LB8vGV5K82fdDxXh07r5iZFHyuM(runnable, tL_error);
                }
            });
            return;
        }
        runnable.run();
    }

    public static /* synthetic */ boolean $r8$lambda$LB8vGV5K82fdDxXh07r5iZFHyuM(Runnable runnable, TLRPC.TL_error tL_error) {
        runnable.run();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGroupForThis$69(Long l, final Runnable runnable) {
        if (this.requestPeerType.user_admin_rights != null) {
            getMessagesController().setUserAdminRole(l.longValue(), getAccountInstance().getUserConfig().getCurrentUser(), ChatRightsEditActivity.rightsOR(getMessagesController().getChat(l).admin_rights, this.requestPeerType.user_admin_rights), null, true, this, false, true, null, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda141
                @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                public final boolean run(TLRPC.TL_error tL_error) {
                    return DialogsActivity.$r8$lambda$I0aadp357GEH7qiBBn5TAv8TEsI(runnable, tL_error);
                }
            });
        } else {
            runnable.run();
        }
    }

    public static /* synthetic */ boolean $r8$lambda$I0aadp357GEH7qiBBn5TAv8TEsI(Runnable runnable, TLRPC.TL_error tL_error) {
        runnable.run();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createGroupForThis$70(AlertDialog alertDialog, Long l, ChannelCreateActivity channelCreateActivity, BaseFragment baseFragment, Runnable runnable) {
        alertDialog.dismiss();
        getMessagesController().loadChannelParticipants(l);
        DialogsActivityDelegate dialogsActivityDelegate = this.delegate;
        removeSelfFromStack();
        channelCreateActivity.removeSelfFromStack();
        baseFragment.lambda$onBackPressed$371();
        if (dialogsActivityDelegate != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(MessagesStorage.TopicKey.m1182of(-l.longValue(), 0L));
            dialogsActivityDelegate.didSelectDialogs(this, arrayList, null, false, this.notify, this.scheduleDate, null);
        }
    }

    /* renamed from: org.telegram.ui.DialogsActivity$31 */
    /* loaded from: classes5.dex */
    class C500931 implements GroupCreateFinalActivity.GroupCreateFinalActivityDelegate {
        final /* synthetic */ AlertDialog val$progress;

        @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
        public void didFailChatCreation() {
        }

        @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
        public void didStartChatCreation() {
        }

        C500931(AlertDialog alertDialog) {
            this.val$progress = alertDialog;
        }

        @Override // org.telegram.ui.GroupCreateFinalActivity.GroupCreateFinalActivityDelegate
        public void didFinishChatCreation(GroupCreateFinalActivity groupCreateFinalActivity, final long j) {
            final BaseFragment[] baseFragmentArr = {groupCreateFinalActivity, null};
            Utilities.Callback callback = new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$31$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$1(j, baseFragmentArr, (Runnable) obj);
                }
            };
            Utilities.Callback callback2 = new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$31$$ExternalSyntheticLambda1
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$3(j, baseFragmentArr, (Runnable) obj);
                }
            };
            final AlertDialog alertDialog = this.val$progress;
            Utilities.Callback callback3 = new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$31$$ExternalSyntheticLambda2
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$5(alertDialog, j, (Runnable) obj);
                }
            };
            Utilities.Callback callback4 = new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$31$$ExternalSyntheticLambda3
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$7(j, (Runnable) obj);
                }
            };
            Utilities.Callback callback5 = new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$31$$ExternalSyntheticLambda4
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$9(j, (Runnable) obj);
                }
            };
            final AlertDialog alertDialog2 = this.val$progress;
            Utilities.doCallbacks(callback, callback2, callback3, callback4, callback5, new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$31$$ExternalSyntheticLambda5
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$didFinishChatCreation$10(alertDialog2, j, baseFragmentArr, (Runnable) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didFinishChatCreation$1(long j, BaseFragment[] baseFragmentArr, final Runnable runnable) {
            if (DialogsActivity.this.requestPeerType.has_username != null && DialogsActivity.this.requestPeerType.has_username.booleanValue()) {
                Bundle bundle = new Bundle();
                bundle.putInt("step", 1);
                bundle.putLong("chat_id", j);
                bundle.putBoolean("forcePublic", DialogsActivity.this.requestPeerType.has_username.booleanValue());
                ChannelCreateActivity channelCreateActivity = new ChannelCreateActivity(bundle);
                channelCreateActivity.setOnFinishListener(new Utilities.Callback2() { // from class: org.telegram.ui.DialogsActivity$31$$ExternalSyntheticLambda9
                    @Override // org.telegram.messenger.Utilities.Callback2
                    public final void run(Object obj, Object obj2) {
                        runnable.run();
                    }
                });
                DialogsActivity.this.presentFragment(channelCreateActivity);
                baseFragmentArr[1] = channelCreateActivity;
                return;
            }
            runnable.run();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didFinishChatCreation$3(long j, final BaseFragment[] baseFragmentArr, Runnable runnable) {
            DialogsActivity.this.showSendToBotAlert(DialogsActivity.this.getMessagesController().getChat(Long.valueOf(j)), runnable, new Runnable() { // from class: org.telegram.ui.DialogsActivity$31$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didFinishChatCreation$2(baseFragmentArr);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didFinishChatCreation$2(BaseFragment[] baseFragmentArr) {
            DialogsActivity.this.removeSelfFromStack();
            if (baseFragmentArr[1] != null) {
                baseFragmentArr[0].removeSelfFromStack();
                baseFragmentArr[1].lambda$onBackPressed$371();
            } else {
                baseFragmentArr[0].lambda$onBackPressed$371();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didFinishChatCreation$5(AlertDialog alertDialog, long j, final Runnable runnable) {
            alertDialog.showDelayed(150L);
            if (DialogsActivity.this.requestPeerType.bot_participant != null && DialogsActivity.this.requestPeerType.bot_participant.booleanValue()) {
                DialogsActivity.this.getMessagesController().addUserToChat(j, DialogsActivity.this.getMessagesController().getUser(Long.valueOf(DialogsActivity.this.requestPeerBotId)), 0, null, DialogsActivity.this, false, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$31$$ExternalSyntheticLambda6
                    @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                    public final boolean run(TLRPC.TL_error tL_error) {
                        return DialogsActivity.C500931.$r8$lambda$jcNVK0ojmq68TTJNAtQanLhUOjM(runnable, tL_error);
                    }
                });
            } else {
                runnable.run();
            }
        }

        public static /* synthetic */ boolean $r8$lambda$jcNVK0ojmq68TTJNAtQanLhUOjM(Runnable runnable, TLRPC.TL_error tL_error) {
            runnable.run();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didFinishChatCreation$7(long j, final Runnable runnable) {
            if (DialogsActivity.this.requestPeerType.bot_admin_rights != null) {
                TLRPC.User user = DialogsActivity.this.getMessagesController().getUser(Long.valueOf(DialogsActivity.this.requestPeerBotId));
                MessagesController messagesController = DialogsActivity.this.getMessagesController();
                TLRPC.TL_chatAdminRights tL_chatAdminRights = DialogsActivity.this.requestPeerType.bot_admin_rights;
                DialogsActivity dialogsActivity = DialogsActivity.this;
                messagesController.setUserAdminRole(j, user, tL_chatAdminRights, null, false, dialogsActivity, dialogsActivity.requestPeerType.bot_participant == null || !DialogsActivity.this.requestPeerType.bot_participant.booleanValue(), true, null, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$31$$ExternalSyntheticLambda7
                    @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                    public final boolean run(TLRPC.TL_error tL_error) {
                        return DialogsActivity.C500931.$r8$lambda$qs_vYfswriaGw2Wbk2SGZ1PMuxE(runnable, tL_error);
                    }
                });
                return;
            }
            runnable.run();
        }

        public static /* synthetic */ boolean $r8$lambda$qs_vYfswriaGw2Wbk2SGZ1PMuxE(Runnable runnable, TLRPC.TL_error tL_error) {
            runnable.run();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didFinishChatCreation$9(long j, final Runnable runnable) {
            if (DialogsActivity.this.requestPeerType.user_admin_rights != null) {
                DialogsActivity.this.getMessagesController().setUserAdminRole(j, DialogsActivity.this.getAccountInstance().getUserConfig().getCurrentUser(), ChatRightsEditActivity.rightsOR(DialogsActivity.this.getMessagesController().getChat(Long.valueOf(j)).admin_rights, DialogsActivity.this.requestPeerType.user_admin_rights), null, false, DialogsActivity.this, false, true, null, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$31$$ExternalSyntheticLambda8
                    @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                    public final boolean run(TLRPC.TL_error tL_error) {
                        return DialogsActivity.C500931.$r8$lambda$IvsBfdM4utRqhtgDeUSk5_yOtYw(runnable, tL_error);
                    }
                });
            } else {
                runnable.run();
            }
        }

        public static /* synthetic */ boolean $r8$lambda$IvsBfdM4utRqhtgDeUSk5_yOtYw(Runnable runnable, TLRPC.TL_error tL_error) {
            runnable.run();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didFinishChatCreation$10(AlertDialog alertDialog, long j, BaseFragment[] baseFragmentArr, Runnable runnable) {
            alertDialog.dismiss();
            DialogsActivity.this.getMessagesController().loadChannelParticipants(Long.valueOf(j));
            DialogsActivityDelegate dialogsActivityDelegate = DialogsActivity.this.delegate;
            DialogsActivity.this.removeSelfFromStack();
            if (baseFragmentArr[1] != null) {
                baseFragmentArr[0].removeSelfFromStack();
                baseFragmentArr[1].lambda$onBackPressed$371();
            } else {
                baseFragmentArr[0].lambda$onBackPressed$371();
            }
            if (dialogsActivityDelegate != null) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(MessagesStorage.TopicKey.m1182of(-j, 0L));
                DialogsActivity dialogsActivity = DialogsActivity.this;
                dialogsActivityDelegate.didSelectDialogs(dialogsActivity, arrayList, null, false, dialogsActivity.notify, dialogsActivity.scheduleDate, null);
            }
        }
    }

    private void updateAppUpdateViews(boolean z) {
        if (this.updateLayout == null) {
            return;
        }
        if (SharedConfig.isAppUpdateAvailable() ? getFileLoader().getPathToAttach(SharedConfig.pendingAppUpdate.document, true).exists() : false) {
            if (this.updateLayout.getTag() != null) {
                return;
            }
            AnimatorSet animatorSet = this.updateLayoutAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            this.updateLayout.setVisibility(0);
            this.updateLayout.setTag(1);
            if (z) {
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.updateLayoutAnimator = animatorSet2;
                animatorSet2.setDuration(180L);
                this.updateLayoutAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT);
                this.updateLayoutAnimator.playTogether(ObjectAnimator.ofFloat(this.updateLayout, (Property<FrameLayout, Float>) View.TRANSLATION_Y, 0.0f));
                this.updateLayoutAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.32
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        DialogsActivity.this.updateLayoutAnimator = null;
                    }
                });
                this.updateLayoutAnimator.start();
                return;
            }
            this.updateLayout.setTranslationY(0.0f);
            return;
        }
        if (this.updateLayout.getTag() == null) {
            return;
        }
        this.updateLayout.setTag(null);
        if (z) {
            AnimatorSet animatorSet3 = new AnimatorSet();
            this.updateLayoutAnimator = animatorSet3;
            animatorSet3.setDuration(180L);
            this.updateLayoutAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT);
            this.updateLayoutAnimator.playTogether(ObjectAnimator.ofFloat(this.updateLayout, (Property<FrameLayout, Float>) View.TRANSLATION_Y, AndroidUtilities.m1146dp(56.0f)));
            this.updateLayoutAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.33
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (DialogsActivity.this.updateLayout.getTag() == null) {
                        DialogsActivity.this.updateLayout.setVisibility(4);
                    }
                    DialogsActivity.this.updateLayoutAnimator = null;
                }
            });
            this.updateLayoutAnimator.start();
            return;
        }
        this.updateLayout.setTranslationY(AndroidUtilities.m1146dp(56.0f));
        this.updateLayout.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateContextViewPosition() {
        float f;
        RightSlidingDialogContainer rightSlidingDialogContainer;
        FilterTabsView filterTabsView = this.filterTabsView;
        float fM1146dp = 0.0f;
        float measuredHeight = (filterTabsView == null || filterTabsView.getVisibility() == 8) ? 0.0f : this.filterTabsView.getMeasuredHeight();
        ViewPagerFixed.TabsView tabsView = this.searchTabsView;
        float measuredHeight2 = (tabsView == null || tabsView.getVisibility() == 8) ? 0.0f : this.searchTabsView.getMeasuredHeight();
        float fM1146dp2 = this.hasStories ? AndroidUtilities.m1146dp(81.0f) : 0.0f;
        if (this.hasStories) {
            float f2 = this.scrollYOffset;
            float f3 = this.searchAnimationProgress;
            f = (f2 * (1.0f - f3)) + (fM1146dp2 * (1.0f - f3)) + (measuredHeight * (1.0f - f3)) + (measuredHeight2 * f3) + this.tabsYOffset;
        } else {
            float f4 = this.scrollYOffset;
            float f5 = this.searchAnimationProgress;
            f = f4 + (measuredHeight * (1.0f - f5)) + (measuredHeight2 * f5) + this.tabsYOffset;
        }
        float measuredHeight3 = f + this.storiesOverscroll;
        FrameLayout frameLayout = this.updateLayout;
        if (frameLayout != null && frameLayout.getVisibility() == 0 && (rightSlidingDialogContainer = this.rightSlidingDialogContainer) != null && rightSlidingDialogContainer.hasFragment()) {
            this.updateLayout.setTranslationY(AndroidUtilities.m1146dp(56.0f) * this.rightSlidingDialogContainer.openedProgress);
            this.updateLayout.setAlpha(1.0f - this.rightSlidingDialogContainer.openedProgress);
        }
        DialogsHintCell dialogsHintCell = this.dialogsHintCell;
        if (dialogsHintCell != null && dialogsHintCell.getVisibility() == 0) {
            RightSlidingDialogContainer rightSlidingDialogContainer2 = this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer2 != null && rightSlidingDialogContainer2.hasFragment()) {
                measuredHeight3 -= this.dialogsHintCell.getMeasuredHeight() * this.rightSlidingDialogContainer.openedProgress;
            }
            this.dialogsHintCell.setTranslationY(measuredHeight3);
            measuredHeight3 += this.dialogsHintCell.getMeasuredHeight() * (1.0f - this.searchAnimationProgress);
        }
        UnconfirmedAuthHintCell unconfirmedAuthHintCell = this.authHintCell;
        if (unconfirmedAuthHintCell != null && unconfirmedAuthHintCell.getVisibility() == 0) {
            RightSlidingDialogContainer rightSlidingDialogContainer3 = this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer3 != null && rightSlidingDialogContainer3.hasFragment()) {
                measuredHeight3 -= this.authHintCell.getMeasuredHeight() * this.rightSlidingDialogContainer.openedProgress;
            }
            float measuredHeight4 = this.authHintCell.getMeasuredHeight() * (1.0f - this.authHintCellProgress);
            this.authHintCell.setTranslationY((-measuredHeight4) + measuredHeight3);
            measuredHeight3 += this.authHintCell.getMeasuredHeight() - measuredHeight4;
        }
        if (this.fragmentContextView != null) {
            FragmentContextView fragmentContextView = this.fragmentLocationContextView;
            float fM1146dp3 = (fragmentContextView == null || fragmentContextView.getVisibility() != 0) ? 0.0f : AndroidUtilities.m1146dp(36.0f) + 0.0f;
            FragmentContextView fragmentContextView2 = this.fragmentContextView;
            fragmentContextView2.setTranslationY(fM1146dp3 + fragmentContextView2.getTopPadding() + measuredHeight3);
        }
        if (this.fragmentLocationContextView != null) {
            FragmentContextView fragmentContextView3 = this.fragmentContextView;
            if (fragmentContextView3 != null && fragmentContextView3.getVisibility() == 0) {
                fM1146dp = 0.0f + AndroidUtilities.m1146dp(this.fragmentContextView.getStyleHeight()) + this.fragmentContextView.getTopPadding();
            }
            FragmentContextView fragmentContextView4 = this.fragmentLocationContextView;
            fragmentContextView4.setTranslationY(fM1146dp + fragmentContextView4.getTopPadding() + measuredHeight3);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:58:0x009d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateFiltersView(boolean r11, java.util.ArrayList r12, java.util.ArrayList r13, boolean r14, boolean r15) {
        /*
            r10 = this;
            boolean r0 = r10.searchIsShowed
            if (r0 == 0) goto Lc1
            boolean r0 = r10.onlySelect
            if (r0 != 0) goto Lc1
            org.telegram.ui.Components.SearchViewPager r0 = r10.searchViewPager
            if (r0 != 0) goto Le
            goto Lc1
        Le:
            java.util.ArrayList r0 = r0.getCurrentSearchFilters()
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
        L18:
            int r7 = r0.size()
            r8 = 1
            if (r2 >= r7) goto L56
            java.lang.Object r7 = r0.get(r2)
            org.telegram.ui.Adapters.FiltersView$MediaFilterData r7 = (org.telegram.ui.Adapters.FiltersView.MediaFilterData) r7
            boolean r7 = r7.isMedia()
            if (r7 == 0) goto L2d
            r4 = 1
            goto L53
        L2d:
            java.lang.Object r7 = r0.get(r2)
            org.telegram.ui.Adapters.FiltersView$MediaFilterData r7 = (org.telegram.ui.Adapters.FiltersView.MediaFilterData) r7
            int r7 = r7.filterType
            r9 = 4
            if (r7 != r9) goto L3a
            r5 = 1
            goto L53
        L3a:
            java.lang.Object r7 = r0.get(r2)
            org.telegram.ui.Adapters.FiltersView$MediaFilterData r7 = (org.telegram.ui.Adapters.FiltersView.MediaFilterData) r7
            int r7 = r7.filterType
            r9 = 6
            if (r7 != r9) goto L47
            r6 = 1
            goto L53
        L47:
            java.lang.Object r7 = r0.get(r2)
            org.telegram.ui.Adapters.FiltersView$MediaFilterData r7 = (org.telegram.ui.Adapters.FiltersView.MediaFilterData) r7
            int r7 = r7.filterType
            r9 = 7
            if (r7 != r9) goto L53
            r3 = 1
        L53:
            int r2 = r2 + 1
            goto L18
        L56:
            if (r3 == 0) goto L59
            r14 = 0
        L59:
            if (r12 == 0) goto L61
            boolean r0 = r12.isEmpty()
            if (r0 == 0) goto L6b
        L61:
            if (r13 == 0) goto L69
            boolean r0 = r13.isEmpty()
            if (r0 == 0) goto L6b
        L69:
            if (r14 == 0) goto L6d
        L6b:
            r0 = 1
            goto L6e
        L6d:
            r0 = 0
        L6e:
            r2 = 0
            if (r4 != 0) goto L76
            if (r0 != 0) goto L76
            if (r11 == 0) goto L76
            goto L9d
        L76:
            if (r0 == 0) goto L9d
            if (r12 == 0) goto L83
            boolean r11 = r12.isEmpty()
            if (r11 != 0) goto L83
            if (r5 != 0) goto L83
            goto L84
        L83:
            r12 = r2
        L84:
            if (r13 == 0) goto L8f
            boolean r11 = r13.isEmpty()
            if (r11 != 0) goto L8f
            if (r6 != 0) goto L8f
            goto L90
        L8f:
            r13 = r2
        L90:
            if (r12 != 0) goto L96
            if (r13 != 0) goto L96
            if (r14 == 0) goto L9d
        L96:
            org.telegram.ui.Adapters.FiltersView r11 = r10.filtersView
            r11.setUsersAndDates(r12, r13, r14)
            r11 = 1
            goto L9e
        L9d:
            r11 = 0
        L9e:
            if (r11 != 0) goto La5
            org.telegram.ui.Adapters.FiltersView r12 = r10.filtersView
            r12.setUsersAndDates(r2, r2, r1)
        La5:
            if (r15 != 0) goto Lb0
            org.telegram.ui.Adapters.FiltersView r12 = r10.filtersView
            androidx.recyclerview.widget.RecyclerView$Adapter r12 = r12.getAdapter()
            r12.notifyDataSetChanged()
        Lb0:
            org.telegram.ui.Components.ViewPagerFixed$TabsView r12 = r10.searchTabsView
            if (r12 == 0) goto Lb7
            r12.hide(r11, r8)
        Lb7:
            org.telegram.ui.Adapters.FiltersView r12 = r10.filtersView
            r12.setEnabled(r11)
            org.telegram.ui.Adapters.FiltersView r11 = r10.filtersView
            r11.setVisibility(r1)
        Lc1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.updateFiltersView(boolean, java.util.ArrayList, java.util.ArrayList, boolean, boolean):void");
    }

    private void addSearchFilter(FiltersView.MediaFilterData mediaFilterData) {
        SearchViewPager searchViewPager;
        if (!this.searchIsShowed || (searchViewPager = this.searchViewPager) == null) {
            return;
        }
        ArrayList<FiltersView.MediaFilterData> currentSearchFilters = searchViewPager.getCurrentSearchFilters();
        if (!currentSearchFilters.isEmpty()) {
            for (int i = 0; i < currentSearchFilters.size(); i++) {
                if (mediaFilterData.isSameType(currentSearchFilters.get(i))) {
                    return;
                }
            }
        }
        currentSearchFilters.add(mediaFilterData);
        this.actionBar.setSearchFilter(mediaFilterData);
        this.actionBar.setSearchFieldText("");
        updateFiltersView(true, null, null, false, true);
    }

    public void updateSpeedItem(boolean z) {
        boolean z2;
        if (this.speedItem == null) {
            return;
        }
        ArrayList<MessageObject> arrayList = getDownloadController().downloadingFiles;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                z2 = false;
                break;
            }
            MessageObject messageObject = arrayList.get(i);
            i++;
            MessageObject messageObject2 = messageObject;
            if (messageObject2.getDocument() != null && messageObject2.getDocument().size >= 157286400) {
                z2 = true;
                break;
            }
        }
        ArrayList<MessageObject> arrayList2 = getDownloadController().recentDownloadingFiles;
        int size2 = arrayList2.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size2) {
                break;
            }
            MessageObject messageObject3 = arrayList2.get(i2);
            i2++;
            MessageObject messageObject4 = messageObject3;
            if (messageObject4.getDocument() != null && messageObject4.getDocument().size >= 157286400) {
                z2 = true;
                break;
            }
        }
        final boolean z3 = !getUserConfig().isPremium() && !getMessagesController().premiumFeaturesBlocked() && z2 && z;
        if (z3 != (this.speedItem.getTag() != null)) {
            this.speedItem.setTag(z3 ? Boolean.TRUE : null);
            this.speedItem.setClickable(z3);
            AnimatorSet animatorSet = this.speedAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.setDuration(180L);
            animatorSet2.playTogether(ObjectAnimator.ofFloat(this.speedItem, (Property<ActionBarMenuItem, Float>) View.ALPHA, z3 ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.speedItem, (Property<ActionBarMenuItem, Float>) View.SCALE_X, z3 ? 1.0f : 0.5f), ObjectAnimator.ofFloat(this.speedItem, (Property<ActionBarMenuItem, Float>) View.SCALE_Y, z3 ? 1.0f : 0.5f));
            animatorSet2.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.34
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) DialogsActivity.this.speedItem.getIconView().getDrawable();
                    if (z3) {
                        animatedVectorDrawable.start();
                        if (SharedConfig.getDevicePerformanceClass() != 0) {
                            TLRPC.TL_help_premiumPromo premiumPromo = MediaDataController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getPremiumPromo();
                            String strFeatureTypeToServerString = PremiumPreviewFragment.featureTypeToServerString(2);
                            if (premiumPromo != null) {
                                int i3 = 0;
                                while (true) {
                                    if (i3 >= premiumPromo.video_sections.size()) {
                                        i3 = -1;
                                        break;
                                    } else if (((String) premiumPromo.video_sections.get(i3)).equals(strFeatureTypeToServerString)) {
                                        break;
                                    } else {
                                        i3++;
                                    }
                                }
                                if (i3 != -1) {
                                    FileLoader.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).loadFile((TLRPC.Document) premiumPromo.videos.get(i3), premiumPromo, 3, 0);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (Build.VERSION.SDK_INT >= 23) {
                        animatedVectorDrawable.reset();
                    } else {
                        animatedVectorDrawable.setVisible(false, true);
                    }
                }
            });
            animatorSet2.start();
            this.speedAnimator = animatorSet2;
        }
    }

    private void createActionMode(String str) {
        if (this.actionBar.actionModeIsExist(str)) {
            return;
        }
        ActionBarMenu actionBarMenuCreateActionMode = this.actionBar.createActionMode(false, str);
        actionBarMenuCreateActionMode.setBackgroundColor(0);
        actionBarMenuCreateActionMode.drawBlur = false;
        NumberTextView numberTextView = new NumberTextView(actionBarMenuCreateActionMode.getContext());
        this.selectedDialogsCountTextView = numberTextView;
        numberTextView.setTextSize(18);
        this.selectedDialogsCountTextView.setTypeface(AndroidUtilities.bold());
        this.selectedDialogsCountTextView.setTextColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon));
        actionBarMenuCreateActionMode.addView(this.selectedDialogsCountTextView, LayoutHelper.createLinear(0, -1, 1.0f, 72, 0, 0, 0));
        this.selectedDialogsCountTextView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda139
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return DialogsActivity.$r8$lambda$s_Awv7EV_CX_teb6CCHilDCr9K0(view, motionEvent);
            }
        });
        this.pinItem = actionBarMenuCreateActionMode.addItemWithWidth(100, C2369R.drawable.msg_pin, AndroidUtilities.m1146dp(54.0f));
        this.muteItem = actionBarMenuCreateActionMode.addItemWithWidth(104, C2369R.drawable.msg_mute, AndroidUtilities.m1146dp(54.0f));
        this.archive2Item = actionBarMenuCreateActionMode.addItemWithWidth(107, C2369R.drawable.msg_archive, AndroidUtilities.m1146dp(54.0f));
        this.deleteItem = actionBarMenuCreateActionMode.addItemWithWidth(102, C2369R.drawable.msg_delete, AndroidUtilities.m1146dp(54.0f), LocaleController.getString(C2369R.string.Delete));
        ActionBarMenuItem actionBarMenuItemAddItemWithWidth = actionBarMenuCreateActionMode.addItemWithWidth(0, C2369R.drawable.ic_ab_other, AndroidUtilities.m1146dp(54.0f), LocaleController.getString(C2369R.string.AccDescrMoreOptions));
        this.archiveItem = actionBarMenuItemAddItemWithWidth.addSubItem(105, C2369R.drawable.msg_archive, LocaleController.getString(C2369R.string.Archive));
        this.pin2Item = actionBarMenuItemAddItemWithWidth.addSubItem(108, C2369R.drawable.msg_pin, LocaleController.getString(C2369R.string.DialogPin));
        this.addToFolderItem = actionBarMenuItemAddItemWithWidth.addSubItem(109, C2369R.drawable.msg_addfolder, LocaleController.getString(C2369R.string.FilterAddTo));
        this.removeFromFolderItem = actionBarMenuItemAddItemWithWidth.addSubItem(110, C2369R.drawable.msg_removefolder, LocaleController.getString(C2369R.string.FilterRemoveFrom));
        this.readItem = actionBarMenuItemAddItemWithWidth.addSubItem(101, C2369R.drawable.msg_markread, LocaleController.getString(C2369R.string.MarkAsRead));
        this.clearItem = actionBarMenuItemAddItemWithWidth.addSubItem(103, C2369R.drawable.msg_clear, LocaleController.getString(C2369R.string.ClearHistory));
        this.blockItem = actionBarMenuItemAddItemWithWidth.addSubItem(106, C2369R.drawable.msg_block, LocaleController.getString(C2369R.string.BlockUser));
        this.muteItem.setOnLongClickListener(new View.OnLongClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda140
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return this.f$0.lambda$createActionMode$73(view);
            }
        });
        this.actionModeViews.add(this.pinItem);
        this.actionModeViews.add(this.archive2Item);
        this.actionModeViews.add(this.muteItem);
        this.actionModeViews.add(this.deleteItem);
        this.actionModeViews.add(actionBarMenuItemAddItemWithWidth);
        updateCounters(false);
    }

    public static /* synthetic */ boolean $r8$lambda$s_Awv7EV_CX_teb6CCHilDCr9K0(View view, MotionEvent motionEvent) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createActionMode$73(View view) {
        performSelectedDialogsAction(this.selectedDialogs, 104, true, true);
        return true;
    }

    public void closeSearching() {
        ActionBar actionBar = this.actionBar;
        if (actionBar == null || !actionBar.isSearchFieldVisible()) {
            return;
        }
        this.actionBar.closeSearchField();
        this.searchIsShowed = false;
        updateFilterTabs(true, true);
    }

    public void scrollToFolder(int i) {
        if (this.filterTabsView == null) {
            updateFilterTabs(true, true);
            if (this.filterTabsView == null) {
                return;
            }
        }
        int tabsCount = this.filterTabsView.getTabsCount() - 1;
        ArrayList<MessagesController.DialogFilter> dialogFilters = getMessagesController().getDialogFilters();
        int i2 = 0;
        while (true) {
            if (i2 >= dialogFilters.size()) {
                break;
            }
            if (dialogFilters.get(i2).f1454id == i) {
                tabsCount = i2;
                break;
            }
            i2++;
        }
        FilterTabsView.Tab tab = this.filterTabsView.getTab(tabsCount);
        if (tab != null) {
            this.filterTabsView.scrollToTab(tab, tabsCount);
        } else {
            this.filterTabsView.selectLastTab();
        }
    }

    public void switchToCurrentSelectedMode(boolean z) {
        ViewPage[] viewPageArr;
        int i = 0;
        int i2 = 0;
        while (true) {
            viewPageArr = this.viewPages;
            if (i2 >= viewPageArr.length) {
                break;
            }
            viewPageArr[i2].listView.stopScroll();
            i2++;
        }
        if (viewPageArr[z ? 1 : 0].selectedType < 0 || this.viewPages[z ? 1 : 0].selectedType >= getMessagesController().getDialogFilters().size()) {
            return;
        }
        MessagesController.DialogFilter dialogFilter = getMessagesController().getDialogFilters().get(this.viewPages[z ? 1 : 0].selectedType);
        if (dialogFilter.isDefault()) {
            this.viewPages[z ? 1 : 0].dialogsType = this.initialDialogsType;
            this.viewPages[z ? 1 : 0].listView.updatePullState();
        } else {
            if (this.viewPages[!z ? 1 : 0].dialogsType == 7) {
                this.viewPages[z ? 1 : 0].dialogsType = 8;
            } else {
                this.viewPages[z ? 1 : 0].dialogsType = 7;
            }
            this.viewPages[z ? 1 : 0].listView.setScrollEnabled(true);
            getMessagesController().selectDialogFilter(dialogFilter, this.viewPages[z ? 1 : 0].dialogsType == 8 ? 1 : 0);
        }
        this.viewPages[1].isLocked = dialogFilter.locked;
        this.viewPages[z ? 1 : 0].dialogsAdapter.setDialogsType(this.viewPages[z ? 1 : 0].dialogsType);
        LinearLayoutManager linearLayoutManager = this.viewPages[z ? 1 : 0].layoutManager;
        if (this.viewPages[z ? 1 : 0].dialogsType == 0 && hasHiddenArchive() && this.viewPages[z ? 1 : 0].archivePullViewState == 2) {
            i = 1;
        }
        linearLayoutManager.scrollToPositionWithOffset(i, (int) this.scrollYOffset);
        checkListLoad(this.viewPages[z ? 1 : 0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showScrollbars(boolean z) {
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr == null || this.scrollBarVisible == z) {
            return;
        }
        this.scrollBarVisible = z;
        for (ViewPage viewPage : viewPageArr) {
            if (z) {
                viewPage.listView.setScrollbarFadingEnabled(false);
            }
            viewPage.listView.setVerticalScrollBarEnabled(z);
            if (z) {
                viewPage.listView.setScrollbarFadingEnabled(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x018a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateFilterTabs(boolean r23, boolean r24) {
        /*
            Method dump skipped, instructions count: 770
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.updateFilterTabs(boolean, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDrawerSwipeEnabled() {
        RightSlidingDialogContainer rightSlidingDialogContainer;
        INavigationLayout iNavigationLayout = this.parentLayout;
        if (iNavigationLayout == null || iNavigationLayout.getDrawerLayoutContainer() == null) {
            return;
        }
        this.parentLayout.getDrawerLayoutContainer().setAllowOpenDrawerBySwipe(((this.isFirstTab && SharedConfig.getChatSwipeAction(this.currentAccount) == 5) || SharedConfig.getChatSwipeAction(this.currentAccount) != 5) && !this.searchIsShowed && ((rightSlidingDialogContainer = this.rightSlidingDialogContainer) == null || !rightSlidingDialogContainer.hasFragment()));
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    protected void onPanTranslationUpdate(float f) {
        if (this.viewPages == null) {
            return;
        }
        this.panTranslationY = f;
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        int i = 0;
        if (chatActivityEnterView != null && chatActivityEnterView.isPopupShowing()) {
            this.fragmentView.setTranslationY(f);
            ViewPage[] viewPageArr = this.viewPages;
            int length = viewPageArr.length;
            while (i < length) {
                viewPageArr[i].setTranslationY(0.0f);
                i++;
            }
            if (!this.onlySelect) {
                this.actionBar.setTranslationY(0.0f);
                Bulletin bulletin = this.topBulletin;
                if (bulletin != null) {
                    bulletin.updatePosition();
                }
            }
            SearchViewPager searchViewPager = this.searchViewPager;
            if (searchViewPager != null) {
                searchViewPager.setTranslationY(this.searchViewPagerTranslationY);
                return;
            }
            return;
        }
        ViewPage[] viewPageArr2 = this.viewPages;
        int length2 = viewPageArr2.length;
        while (i < length2) {
            viewPageArr2[i].setTranslationY(f);
            i++;
        }
        if (!this.onlySelect) {
            this.actionBar.setTranslationY(f);
            Bulletin bulletin2 = this.topBulletin;
            if (bulletin2 != null) {
                bulletin2.updatePosition();
            }
        }
        SearchViewPager searchViewPager2 = this.searchViewPager;
        if (searchViewPager2 != null) {
            searchViewPager2.setTranslationY(this.panTranslationY + this.searchViewPagerTranslationY);
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    /* renamed from: finishFragment */
    public void lambda$onBackPressed$371() {
        super.lambda$onBackPressed$371();
        ItemOptions itemOptions = this.filterOptions;
        if (itemOptions != null) {
            itemOptions.dismiss();
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onResume() {
        final DialogsActivity dialogsActivity;
        ViewPage viewPage;
        int dialogsType;
        int i;
        View view;
        super.onResume();
        DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
        if (dialogStoriesCell != null) {
            dialogStoriesCell.onResume();
        }
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer != null) {
            rightSlidingDialogContainer.onResume();
        }
        if (!this.parentLayout.isInPreviewMode() && (view = this.blurredView) != null && view.getVisibility() == 0) {
            this.blurredView.setVisibility(8);
            this.blurredView.setBackground(null);
        }
        updateDrawerSwipeEnabled();
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr != null) {
            for (ViewPage viewPage2 : viewPageArr) {
                viewPage2.dialogsAdapter.notifyDataSetChanged();
            }
        }
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.onResume();
        }
        if (!this.onlySelect && this.folderId == 0) {
            getMediaDataController().checkStickers(4);
        }
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager != null) {
            searchViewPager.onResume();
        }
        boolean z = this.afterSignup || getUserConfig().unacceptedTermsOfService == null;
        NotificationManager notificationManager = (NotificationManager) getContext().getSystemService("notification");
        if (z && this.folderId == 0 && this.checkPermission && !this.onlySelect && (i = Build.VERSION.SDK_INT) >= 23) {
            final Activity parentActivity = getParentActivity();
            if (parentActivity != null) {
                this.checkPermission = false;
                final boolean z2 = parentActivity.checkSelfPermission("android.permission.READ_CONTACTS") != 0;
                final boolean z3 = (i <= 28 || BuildVars.NO_SCOPED_STORAGE) && parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0;
                final boolean z4 = i >= 33 && parentActivity.checkSelfPermission("android.permission.POST_NOTIFICATIONS") != 0;
                dialogsActivity = this;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onResume$77(z4, z2, z3, parentActivity);
                    }
                }, (dialogsActivity.afterSignup && (z2 || z4)) ? 4000L : 0L);
            } else {
                dialogsActivity = this;
            }
        } else {
            dialogsActivity = this;
            if (!dialogsActivity.onlySelect && dialogsActivity.folderId == 0 && XiaomiUtilities.isMIUI() && !XiaomiUtilities.isCustomPermissionGranted(XiaomiUtilities.OP_SHOW_WHEN_LOCKED)) {
                if (getParentActivity() == null || MessagesController.getGlobalNotificationsSettings().getBoolean("askedAboutMiuiLockscreen", false)) {
                    return;
                } else {
                    showDialog(new AlertDialog.Builder(getParentActivity()).setTopAnimation(C2369R.raw.permission_request_apk, 72, false, Theme.getColor(Theme.key_dialogTopBackground)).setMessage(LocaleController.getString(C2369R.string.PermissionXiaomiLockscreen)).setPositiveButton(LocaleController.getString(C2369R.string.PermissionOpenSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda18
                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                        public final void onClick(AlertDialog alertDialog, int i2) {
                            this.f$0.lambda$onResume$78(alertDialog, i2);
                        }
                    }).setNegativeButton(LocaleController.getString(C2369R.string.ContactsPermissionAlertNotNow), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda19
                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                        public final void onClick(AlertDialog alertDialog, int i2) {
                            MessagesController.getGlobalNotificationsSettings().edit().putBoolean("askedAboutMiuiLockscreen", true).apply();
                        }
                    }).create());
                }
            } else if (dialogsActivity.folderId == 0 && Build.VERSION.SDK_INT >= 34 && !notificationManager.canUseFullScreenIntent()) {
                if (getParentActivity() == null || MessagesController.getGlobalNotificationsSettings().getBoolean("askedAboutFSILockscreen", false)) {
                    return;
                } else {
                    showDialog(new AlertDialog.Builder(getParentActivity()).setTopAnimation(C2369R.raw.permission_request_apk, 72, false, Theme.getColor(Theme.key_dialogTopBackground)).setMessage(LocaleController.getString(C2369R.string.PermissionFSILockscreen)).setPositiveButton(LocaleController.getString(C2369R.string.PermissionOpenSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda20
                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                        public final void onClick(AlertDialog alertDialog, int i2) {
                            this.f$0.lambda$onResume$80(alertDialog, i2);
                        }
                    }).setNegativeButton(LocaleController.getString(C2369R.string.ContactsPermissionAlertNotNow), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda21
                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                        public final void onClick(AlertDialog alertDialog, int i2) {
                            MessagesController.getGlobalNotificationsSettings().edit().putBoolean("askedAboutFSILockscreen", true).commit();
                        }
                    }).create());
                }
            }
        }
        showFiltersHint();
        if (dialogsActivity.viewPages != null) {
            int i2 = 0;
            while (true) {
                ViewPage[] viewPageArr2 = dialogsActivity.viewPages;
                if (i2 >= viewPageArr2.length) {
                    break;
                }
                if (viewPageArr2[i2].dialogsType == 0 && dialogsActivity.viewPages[i2].archivePullViewState == 2 && dialogsActivity.viewPages[i2].layoutManager.findFirstVisibleItemPosition() == 0 && hasHiddenArchive()) {
                    dialogsActivity.viewPages[i2].layoutManager.scrollToPositionWithOffset(1, (int) dialogsActivity.scrollYOffset);
                }
                if (i2 == 0) {
                    dialogsActivity.viewPages[i2].dialogsAdapter.resume();
                } else {
                    dialogsActivity.viewPages[i2].dialogsAdapter.pause();
                }
                i2++;
            }
        }
        showNextSupportedSuggestion();
        Bulletin.addDelegate(this, new Bulletin.Delegate() { // from class: org.telegram.ui.DialogsActivity.35
            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ boolean allowLayoutChanges() {
                return Bulletin.Delegate.CC.$default$allowLayoutChanges(this);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ boolean bottomOffsetAnimated() {
                return Bulletin.Delegate.CC.$default$bottomOffsetAnimated(this);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ boolean clipWithGradient(int i3) {
                return Bulletin.Delegate.CC.$default$clipWithGradient(this, i3);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ void onHide(Bulletin bulletin) {
                Bulletin.Delegate.CC.$default$onHide(this, bulletin);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public int getBottomOffset(int i3) {
                return DialogsActivity.this.windowInsetsStateHolder.getCurrentNavigationBarInset();
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public void onBottomOffsetChange(float f) {
                if (DialogsActivity.this.undoView[0] == null || DialogsActivity.this.undoView[0].getVisibility() != 0) {
                    DialogsActivity.this.additionalFloatingTranslation = f - DialogsActivity.this.windowInsetsStateHolder.getCurrentNavigationBarInset();
                    if (DialogsActivity.this.additionalFloatingTranslation < 0.0f) {
                        DialogsActivity.this.additionalFloatingTranslation = 0.0f;
                    }
                    if (DialogsActivity.this.floatingHidden) {
                        return;
                    }
                    DialogsActivity.this.updateFloatingButtonOffset();
                }
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public void onShow(Bulletin bulletin) {
                if (DialogsActivity.this.undoView[0] == null || DialogsActivity.this.undoView[0].getVisibility() != 0) {
                    return;
                }
                DialogsActivity.this.undoView[0].hide(true, 2);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public int getTopOffset(int i3) {
                int collapsedProgress = 0;
                int measuredHeight = (((BaseFragment) DialogsActivity.this).actionBar != null ? ((BaseFragment) DialogsActivity.this).actionBar.getMeasuredHeight() : 0) + ((DialogsActivity.this.filterTabsView == null || DialogsActivity.this.filterTabsView.getVisibility() != 0) ? 0 : DialogsActivity.this.filterTabsView.getMeasuredHeight()) + ((DialogsActivity.this.fragmentContextView == null || !DialogsActivity.this.fragmentContextView.isCallTypeVisible()) ? 0 : AndroidUtilities.m1146dp(DialogsActivity.this.fragmentContextView.getStyleHeight())) + ((DialogsActivity.this.dialogsHintCell == null || DialogsActivity.this.dialogsHintCell.getVisibility() != 0) ? 0 : DialogsActivity.this.dialogsHintCell.getHeight()) + ((DialogsActivity.this.authHintCell == null || !DialogsActivity.this.authHintCellVisible) ? 0 : DialogsActivity.this.authHintCell.getHeight());
                DialogsActivity.m12877$$Nest$fgetactiveGiftAuctionsHintCell(DialogsActivity.this);
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                DialogStoriesCell dialogStoriesCell2 = dialogsActivity2.dialogStoriesCell;
                if (dialogStoriesCell2 != null && dialogsActivity2.dialogStoriesCellVisible) {
                    collapsedProgress = (int) ((1.0f - dialogStoriesCell2.getCollapsedProgress()) * AndroidUtilities.m1146dp(81.0f));
                }
                return measuredHeight + collapsedProgress;
            }
        });
        if (dialogsActivity.searchIsShowed) {
            AndroidUtilities.requestAdjustResize(getParentActivity(), dialogsActivity.classGuid);
        }
        updateVisibleRows(0, false);
        updateProxyButton(false, true);
        updateStoriesVisibility(false);
        checkSuggestClearDatabase();
        if (dialogsActivity.filterTabsView != null && (viewPage = dialogsActivity.viewPages[0]) != null && viewPage.dialogsAdapter != null && ((dialogsType = dialogsActivity.viewPages[0].dialogsAdapter.getDialogsType()) == 7 || dialogsType == 8)) {
            MessagesController.DialogFilter dialogFilter = getMessagesController().selectedDialogFilter[dialogsType != 7 ? (char) 1 : (char) 0];
            if (dialogFilter != null) {
                dialogsActivity.filterTabsView.selectTabWithStableId(dialogFilter.localId);
            }
        }
        if (AyuConfig.sawFirstLaunchAlert) {
            return;
        }
        AlertUtils.showFirstLaunchAlert(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$77(boolean z, boolean z2, boolean z3, final Activity activity) {
        if (getParentActivity() == null) {
            return;
        }
        this.afterSignup = false;
        if (z || z2 || z3) {
            this.askingForPermissions = true;
            if (z && NotificationPermissionDialog.shouldAsk(activity)) {
                PermissionRequest.requestPermission("android.permission.POST_NOTIFICATIONS", new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda69
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$onResume$75(activity, (Boolean) obj);
                    }
                });
                return;
            }
            if (z2 && this.askAboutContacts && getUserConfig().syncContacts && activity.shouldShowRequestPermissionRationale("android.permission.READ_CONTACTS")) {
                AlertDialog alertDialogCreate = AlertsCreator.createContactsPermissionDialog(activity, new MessagesStorage.IntCallback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda70
                    @Override // org.telegram.messenger.MessagesStorage.IntCallback
                    public final void run(int i) {
                        this.f$0.lambda$onResume$76(i);
                    }
                }).create();
                this.permissionDialog = alertDialogCreate;
                showDialog(alertDialogCreate);
            } else {
                if (z3 && activity.shouldShowRequestPermissionRationale("android.permission.WRITE_EXTERNAL_STORAGE")) {
                    if (activity instanceof BasePermissionsActivity) {
                        AlertDialog alertDialogCreatePermissionErrorAlert = ((BasePermissionsActivity) activity).createPermissionErrorAlert(C2369R.raw.permission_request_folder, LocaleController.getString(C2369R.string.PermissionStorageWithHint));
                        this.permissionDialog = alertDialogCreatePermissionErrorAlert;
                        showDialog(alertDialogCreatePermissionErrorAlert);
                        return;
                    }
                    return;
                }
                askForPermissons(true);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$75(final Activity activity, Boolean bool) {
        if (bool.booleanValue()) {
            return;
        }
        showDialog(new NotificationPermissionDialog(activity, !PermissionRequest.canAskPermission("android.permission.POST_NOTIFICATIONS"), new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda105
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                DialogsActivity.m12846$r8$lambda$7_w6AxxtajPigEUrDRBUffjaa4(activity, (Boolean) obj);
            }
        }));
    }

    /* renamed from: $r8$lambda$7_w6A-xxtajPigEUrDRBUffjaa4, reason: not valid java name */
    public static /* synthetic */ void m12846$r8$lambda$7_w6AxxtajPigEUrDRBUffjaa4(Activity activity, Boolean bool) {
        if (bool.booleanValue()) {
            if (!PermissionRequest.canAskPermission("android.permission.POST_NOTIFICATIONS")) {
                PermissionRequest.showPermissionSettings("android.permission.POST_NOTIFICATIONS");
            } else {
                activity.requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$76(int i) {
        this.askAboutContacts = i != 0;
        MessagesController.getGlobalNotificationsSettings().edit().putBoolean("askAboutContacts", this.askAboutContacts).apply();
        askForPermissons(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$78(AlertDialog alertDialog, int i) {
        Intent permissionManagerIntent = XiaomiUtilities.getPermissionManagerIntent();
        if (permissionManagerIntent != null) {
            try {
                try {
                    getParentActivity().startActivity(permissionManagerIntent);
                } catch (Exception unused) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
                    getParentActivity().startActivity(intent);
                }
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onResume$80(AlertDialog alertDialog, int i) {
        Intent intent = new Intent("android.settings.MANAGE_APP_USE_FULL_SCREEN_INTENT");
        intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
        try {
            getParentActivity().startActivity(intent);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean presentFragment(BaseFragment baseFragment) {
        ViewPage[] viewPageArr;
        boolean zPresentFragment = super.presentFragment(baseFragment);
        if (zPresentFragment && (viewPageArr = this.viewPages) != null) {
            for (ViewPage viewPage : viewPageArr) {
                viewPage.dialogsAdapter.pause();
            }
        }
        HintView2 hintView2 = this.storyHint;
        if (hintView2 != null) {
            hintView2.hide();
        }
        HintView2 hintView22 = this.storyPremiumHint;
        if (hintView22 != null) {
            hintView22.hide();
        }
        Bulletin.hideVisible();
        return zPresentFragment;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        Bulletin bulletin = this.storiesBulletin;
        if (bulletin != null) {
            bulletin.hide();
            this.storiesBulletin = null;
        }
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer != null) {
            rightSlidingDialogContainer.onPause();
        }
        ItemOptions itemOptions = this.filterOptions;
        if (itemOptions != null) {
            itemOptions.dismiss();
        }
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        if (chatActivityEnterView != null) {
            chatActivityEnterView.onPause();
        }
        UndoView undoView = this.undoView[0];
        if (undoView != null) {
            undoView.hide(true, 0);
        }
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr != null) {
            for (ViewPage viewPage : viewPageArr) {
                viewPage.dialogsAdapter.pause();
            }
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onBackPressed() {
        if (closeSheet()) {
            return false;
        }
        if (this.rightSlidingDialogContainer.hasFragment()) {
            if (this.rightSlidingDialogContainer.getFragment().onBackPressed()) {
                this.rightSlidingDialogContainer.lambda$presentFragment$1();
                SearchViewPager searchViewPager = this.searchViewPager;
                if (searchViewPager != null) {
                    searchViewPager.updateTabs();
                }
            }
            return false;
        }
        ItemOptions itemOptions = this.filterOptions;
        if (itemOptions != null) {
            itemOptions.dismiss();
            this.filterOptions = null;
            return false;
        }
        FilterTabsView filterTabsView = this.filterTabsView;
        if (filterTabsView != null && filterTabsView.isEditing()) {
            this.filterTabsView.setIsEditing(false);
            showDoneItem(false);
            return false;
        }
        ActionBar actionBar = this.actionBar;
        if (actionBar != null && actionBar.isActionModeShowed()) {
            SearchViewPager searchViewPager2 = this.searchViewPager;
            if (searchViewPager2 != null && searchViewPager2.getVisibility() == 0) {
                this.searchViewPager.hideActionMode();
                hideActionMode(true);
            } else {
                hideActionMode(true);
            }
            return false;
        }
        FilterTabsView filterTabsView2 = this.filterTabsView;
        if (filterTabsView2 != null && filterTabsView2.getVisibility() == 0 && !this.tabsAnimationInProgress && !this.filterTabsView.isAnimatingIndicator() && !this.startedTracking && !this.filterTabsView.isFirstTabSelected()) {
            this.filterTabsView.selectFirstTab();
            return false;
        }
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        if (chatActivityEnterView != null && chatActivityEnterView.isPopupShowing()) {
            this.commentView.hidePopup(true);
            return false;
        }
        if (this.dialogStoriesCell.isFullExpanded() && this.dialogStoriesCell.scrollToFirst()) {
            return false;
        }
        return super.onBackPressed();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onBecomeFullyHidden() {
        View view;
        FilterTabsView filterTabsView;
        if (this.closeSearchFieldOnHide) {
            ActionBar actionBar = this.actionBar;
            if (actionBar != null) {
                actionBar.closeSearchField();
            }
            TLObject tLObject = this.searchObject;
            if (tLObject != null) {
                SearchViewPager searchViewPager = this.searchViewPager;
                if (searchViewPager != null) {
                    searchViewPager.dialogsSearchAdapter.putRecentSearch(this.searchDialogId, tLObject);
                }
                this.searchObject = null;
            }
            this.closeSearchFieldOnHide = false;
        }
        if (!this.hasStories && (filterTabsView = this.filterTabsView) != null && filterTabsView.getVisibility() == 0 && this.filterTabsViewIsVisible) {
            int i = (int) (-this.scrollYOffset);
            int currentActionBarHeight = ActionBar.getCurrentActionBarHeight();
            if (i != 0 && i != currentActionBarHeight) {
                if (i < currentActionBarHeight / 2) {
                    setScrollY(0.0f);
                } else if (this.viewPages[0].listView.canScrollVertically(1)) {
                    setScrollY(-currentActionBarHeight);
                }
            }
        }
        UndoView undoView = this.undoView[0];
        if (undoView != null) {
            undoView.hide(true, 0);
        }
        if (!isInPreviewMode() && (view = this.blurredView) != null && view.getVisibility() == 0) {
            this.blurredView.setVisibility(8);
            this.blurredView.setBackground(null);
        }
        super.onBecomeFullyHidden();
        this.canShowStoryHint = true;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onBecomeFullyVisible() {
        HintView2 hintView2;
        super.onBecomeFullyVisible();
        if (isArchive()) {
            SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
            boolean z = globalMainSettings.getBoolean("archivehint", true);
            boolean zIsEmpty = getDialogsArray(this.currentAccount, this.initialDialogsType, this.folderId, false).isEmpty();
            if (z && zIsEmpty) {
                MessagesController.getGlobalMainSettings().edit().putBoolean("archivehint", false).commit();
                z = false;
            }
            if (z) {
                globalMainSettings.edit().putBoolean("archivehint", false).commit();
                showArchiveHelp();
            }
            ActionBarMenuItem actionBarMenuItem = this.optionsItem;
            if (actionBarMenuItem != null) {
                if (zIsEmpty) {
                    actionBarMenuItem.hideSubItem(6);
                } else {
                    actionBarMenuItem.showSubItem(6);
                }
            }
        }
        updateFloatingButtonOffset();
        if (this.canShowStoryHint && !this.storyHintShown && (hintView2 = this.storyHint) != null && this.storiesEnabled) {
            this.storyHintShown = true;
            this.canShowStoryHint = false;
            hintView2.show();
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda28
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.createSearchViewPager();
            }
        }, 200L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showArchiveHelp() {
        getContactsController().loadGlobalPrivacySetting();
        BottomSheet bottomSheetShow = new BottomSheet.Builder(getContext(), false, getResourceProvider()).setCustomView(new ArchiveHelp(getContext(), this.currentAccount, getResourceProvider(), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda83
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showArchiveHelp$83(bottomSheetArr);
            }
        }, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda84
            @Override // java.lang.Runnable
            public final void run() {
                DialogsActivity.$r8$lambda$fMKa9135M6narG86kvaEd46jMYs(bottomSheetArr);
            }
        }), 49).show();
        final BottomSheet[] bottomSheetArr = {bottomSheetShow};
        bottomSheetShow.fixNavigationBar(Theme.getColor(Theme.key_dialogBackground));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showArchiveHelp$83(BottomSheet[] bottomSheetArr) {
        BottomSheet bottomSheet = bottomSheetArr[0];
        if (bottomSheet != null) {
            bottomSheet.lambda$new$0();
            bottomSheetArr[0] = null;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda106
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showArchiveHelp$82();
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showArchiveHelp$82() {
        presentFragment(new ArchiveSettingsActivity());
    }

    public static /* synthetic */ void $r8$lambda$fMKa9135M6narG86kvaEd46jMYs(BottomSheet[] bottomSheetArr) {
        BottomSheet bottomSheet = bottomSheetArr[0];
        if (bottomSheet != null) {
            bottomSheet.lambda$new$0();
            bottomSheetArr[0] = null;
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void setInPreviewMode(boolean z) {
        ActionBarMenuItem actionBarMenuItem;
        super.setInPreviewMode(z);
        if (!z && this.avatarContainer != null) {
            this.actionBar.setBackground(null);
            ((ViewGroup.MarginLayoutParams) this.actionBar.getLayoutParams()).topMargin = 0;
            this.actionBar.removeView(this.avatarContainer);
            this.avatarContainer = null;
            updateFilterTabs(false, false);
            this.floatingButton.setVisibility(0);
            ContentView contentView = (ContentView) this.fragmentView;
            FragmentContextView fragmentContextView = this.fragmentContextView;
            if (fragmentContextView != null) {
                contentView.addView(fragmentContextView);
            }
            FragmentContextView fragmentContextView2 = this.fragmentLocationContextView;
            if (fragmentContextView2 != null) {
                contentView.addView(fragmentContextView2);
            }
        }
        DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
        if (dialogStoriesCell != null) {
            if (this.dialogStoriesCellVisible && !z) {
                dialogStoriesCell.setVisibility(0);
            } else {
                dialogStoriesCell.setVisibility(8);
            }
        }
        FrameLayout frameLayout = this.floatingButtonContainer;
        if (frameLayout != null) {
            frameLayout.setVisibility(((!this.onlySelect || this.initialDialogsType == 10) && this.folderId == 0 && !z) ? 0 : 8);
        }
        FrameLayout frameLayout2 = this.floatingButton2Container;
        if (frameLayout2 != null) {
            frameLayout2.setVisibility(((this.onlySelect && this.initialDialogsType != 10) || this.folderId != 0 || !this.storiesEnabled || ((actionBarMenuItem = this.searchItem) != null && actionBarMenuItem.isSearchFieldVisible()) || z) ? 8 : 0);
        }
        updateDialogsHint();
    }

    public boolean addOrRemoveSelectedDialog(long j, View view) {
        if (this.onlySelect && getMessagesController().isForum(j)) {
            return false;
        }
        if (this.selectedDialogs.contains(Long.valueOf(j))) {
            this.selectedDialogs.remove(Long.valueOf(j));
            if (view instanceof DialogCell) {
                ((DialogCell) view).setChecked(false, true);
            } else if (view instanceof ProfileSearchCell) {
                ((ProfileSearchCell) view).setChecked(false, true);
            }
            return false;
        }
        this.selectedDialogs.add(Long.valueOf(j));
        if (view instanceof DialogCell) {
            ((DialogCell) view).setChecked(true, true);
        } else if (view instanceof ProfileSearchCell) {
            ((ProfileSearchCell) view).setChecked(true, true);
        }
        return true;
    }

    public void search(String str, boolean z) {
        showSearch(true, false, z);
        this.actionBar.openSearchField(str, false);
    }

    public void showSearch(boolean z, boolean z2, boolean z3) {
        showSearch(z, z2, z3, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0099  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void showSearch(final boolean r18, boolean r19, boolean r20, boolean r21) {
        /*
            Method dump skipped, instructions count: 1298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.showSearch(boolean, boolean, boolean, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSearch$85(ValueAnimator valueAnimator) {
        setSearchAnimationProgress(((Float) valueAnimator.getAnimatedValue()).floatValue(), false);
    }

    public boolean onlyDialogsAdapter() {
        int totalDialogsCount = getMessagesController().getTotalDialogsCount();
        if (this.onlySelect) {
            return true;
        }
        return totalDialogsCount <= 10 && !this.hasStories;
    }

    private void updateFilterTabsVisibility(boolean z) {
        if (this.fragmentView == null) {
            return;
        }
        if (this.isPaused || this.databaseMigrationHint != null) {
            z = false;
        }
        if (this.searchIsShowed) {
            ValueAnimator valueAnimator = this.filtersTabAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            boolean z2 = this.canShowFilterTabsView;
            this.filterTabsViewIsVisible = z2;
            this.filterTabsProgress = z2 ? 1.0f : 0.0f;
            return;
        }
        final boolean z3 = this.canShowFilterTabsView;
        if (this.filterTabsViewIsVisible != z3) {
            ValueAnimator valueAnimator2 = this.filtersTabAnimator;
            if (valueAnimator2 != null) {
                valueAnimator2.cancel();
            }
            this.filterTabsViewIsVisible = z3;
            if (z) {
                if (z3) {
                    if (this.filterTabsView.getVisibility() != 0) {
                        this.filterTabsView.setVisibility(0);
                    }
                    this.filtersTabAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                } else {
                    this.filtersTabAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
                }
                this.filterTabsMoveFrom = getActionBarMoveFrom(true);
                final float f = this.scrollYOffset;
                this.filtersTabAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.38
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        DialogsActivity.this.filtersTabAnimator = null;
                        DialogsActivity.this.scrollAdditionalOffset = 0.0f;
                        if (!z3) {
                            DialogsActivity.this.filterTabsView.setVisibility(8);
                        }
                        View view = DialogsActivity.this.fragmentView;
                        if (view != null) {
                            view.requestLayout();
                        }
                        DialogsActivity.this.notificationsLocker.unlock();
                    }
                });
                this.filtersTabAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda68
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                        this.f$0.lambda$updateFilterTabsVisibility$86(z3, f, valueAnimator3);
                    }
                });
                this.filtersTabAnimator.setDuration(220L);
                this.filtersTabAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
                this.notificationsLocker.lock();
                this.filtersTabAnimator.start();
                this.fragmentView.requestLayout();
                return;
            }
            this.filterTabsProgress = z3 ? 1.0f : 0.0f;
            this.filterTabsView.setVisibility(z3 ? 0 : 8);
            View view = this.fragmentView;
            if (view != null) {
                view.invalidate();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateFilterTabsVisibility$86(boolean z, float f, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.filterTabsProgress = fFloatValue;
        if (!z && !this.hasStories) {
            setScrollY(f * fFloatValue);
        }
        View view = this.fragmentView;
        if (view != null) {
            view.invalidate();
        }
    }

    public void setSearchAnimationProgress(float f, boolean z) {
        this.searchAnimationProgress = f;
        boolean z2 = true;
        if (this.whiteActionBar) {
            int color = Theme.getColor(this.folderId != 0 ? Theme.key_actionBarDefaultArchivedIcon : Theme.key_actionBarDefaultIcon);
            ActionBar actionBar = this.actionBar;
            int i = Theme.key_actionBarActionModeDefaultIcon;
            actionBar.setItemsColor(ColorUtils.blendARGB(color, Theme.getColor(i), this.searchAnimationProgress), false);
            this.actionBar.setItemsColor(ColorUtils.blendARGB(Theme.getColor(i), Theme.getColor(i), this.searchAnimationProgress), true);
            this.actionBar.setItemsBackgroundColor(ColorUtils.blendARGB(Theme.getColor(this.folderId != 0 ? Theme.key_actionBarDefaultArchivedSelector : Theme.key_actionBarDefaultSelector), Theme.getColor(Theme.key_actionBarActionModeDefaultSelector), this.searchAnimationProgress), false);
        }
        View view = this.fragmentView;
        if (view != null) {
            view.invalidate();
        }
        DialogsHintCell dialogsHintCell = this.dialogsHintCell;
        if (dialogsHintCell != null) {
            dialogsHintCell.setAlpha(1.0f - f);
            if (this.dialogsHintCellVisible) {
                if (this.dialogsHintCell.getAlpha() == 0.0f) {
                    this.dialogsHintCell.setVisibility(4);
                } else {
                    this.dialogsHintCell.setVisibility(0);
                    ViewParent parent = this.dialogsHintCell.getParent();
                    if (parent != null) {
                        parent.requestLayout();
                    }
                }
            }
        }
        UnconfirmedAuthHintCell unconfirmedAuthHintCell = this.authHintCell;
        if (unconfirmedAuthHintCell != null) {
            unconfirmedAuthHintCell.setAlpha(1.0f - f);
            if (this.authHintCellVisible) {
                if (this.authHintCell.getAlpha() == 0.0f) {
                    this.authHintCell.setVisibility(4);
                } else {
                    this.authHintCell.setVisibility(0);
                }
            }
        }
        if (SharedConfig.getDevicePerformanceClass() != 0 && LiteMode.isEnabled(32768)) {
            z2 = false;
        }
        if (z) {
            ViewPage viewPage = this.viewPages[0];
            if (viewPage != null) {
                if (f < 1.0f) {
                    viewPage.setVisibility(0);
                }
                this.viewPages[0].setAlpha(1.0f - f);
                if (!z2) {
                    float f2 = (0.1f * f) + 0.9f;
                    this.viewPages[0].setScaleX(f2);
                    this.viewPages[0].setScaleY(f2);
                }
            }
            RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer != null) {
                if (f >= 1.0f) {
                    rightSlidingDialogContainer.setVisibility(8);
                } else {
                    rightSlidingDialogContainer.setVisibility(0);
                    this.rightSlidingDialogContainer.setAlpha(1.0f - f);
                }
            }
            SearchViewPager searchViewPager = this.searchViewPager;
            if (searchViewPager != null) {
                searchViewPager.setAlpha(f);
                if (!z2) {
                    float f3 = ((1.0f - f) * 0.05f) + 1.0f;
                    this.searchViewPager.setScaleX(f3);
                    this.searchViewPager.setScaleY(f3);
                }
            }
            ActionBarMenuItem actionBarMenuItem = this.passcodeItem;
            if (actionBarMenuItem != null) {
                actionBarMenuItem.getIconView().setAlpha(1.0f - f);
            }
            ActionBarMenuItem actionBarMenuItem2 = this.downloadsItem;
            if (actionBarMenuItem2 != null) {
                actionBarMenuItem2.setAlpha(1.0f - f);
            }
            FilterTabsView filterTabsView = this.filterTabsView;
            if (filterTabsView != null && filterTabsView.getVisibility() == 0) {
                this.filterTabsView.getTabsContainer().setAlpha(1.0f - f);
            }
        }
        updateContextViewPosition();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void findAndUpdateCheckBox(long j, boolean z) {
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr == null) {
            return;
        }
        for (ViewPage viewPage : viewPageArr) {
            int childCount = viewPage.listView.getChildCount();
            int i = 0;
            while (true) {
                if (i < childCount) {
                    View childAt = viewPage.listView.getChildAt(i);
                    if (childAt instanceof DialogCell) {
                        DialogCell dialogCell = (DialogCell) childAt;
                        if (dialogCell.getDialogId() == j) {
                            dialogCell.setChecked(z, true);
                            break;
                        }
                    }
                    i++;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkListLoad(ViewPage viewPage) {
        checkListLoad(viewPage, viewPage.layoutManager.findFirstVisibleItemPosition(), viewPage.layoutManager.findLastVisibleItemPosition());
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x00bc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void checkListLoad(org.telegram.ui.DialogsActivity.ViewPage r13, int r14, int r15) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.checkListLoad(org.telegram.ui.DialogsActivity$ViewPage, int, int):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkListLoad$87(boolean z, boolean z2, boolean z3, boolean z4) {
        if (z) {
            getMessagesController().loadDialogs(this.folderId, -1, 100, z2);
        }
        if (z3) {
            getMessagesController().loadDialogs(1, -1, 100, z4);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0375  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x037c  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:220:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x03bf  */
    /* JADX WARN: Removed duplicated region for block: B:252:0x044c  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x0463  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x04a8  */
    /* JADX WARN: Removed duplicated region for block: B:270:0x04cb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void onItemClick(android.view.View r22, int r23, androidx.recyclerview.widget.RecyclerView.Adapter r24, float r25, float r26) {
        /*
            Method dump skipped, instructions count: 1561
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.onItemClick(android.view.View, int, androidx.recyclerview.widget.RecyclerView$Adapter, float, float):void");
    }

    public static ChatActivity highlightFoundQuote(ChatActivity chatActivity, MessageObject messageObject) {
        CharSequence charSequence;
        if (messageObject != null && messageObject.hasHighlightedWords()) {
            try {
                if (!TextUtils.isEmpty(messageObject.caption)) {
                    charSequence = messageObject.caption;
                } else {
                    charSequence = messageObject.messageText;
                }
                CharSequence charSequenceHighlightText = AndroidUtilities.highlightText(charSequence, messageObject.highlightedWords, (Theme.ResourcesProvider) null);
                if (charSequenceHighlightText instanceof SpannableStringBuilder) {
                    SpannableStringBuilder spannableStringBuilder = (SpannableStringBuilder) charSequenceHighlightText;
                    ForegroundColorSpanThemable[] foregroundColorSpanThemableArr = (ForegroundColorSpanThemable[]) spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), ForegroundColorSpanThemable.class);
                    if (foregroundColorSpanThemableArr.length > 0) {
                        int spanStart = spannableStringBuilder.getSpanStart(foregroundColorSpanThemableArr[0]);
                        int spanEnd = spannableStringBuilder.getSpanEnd(foregroundColorSpanThemableArr[0]);
                        for (int i = 1; i < foregroundColorSpanThemableArr.length; i++) {
                            int spanStart2 = spannableStringBuilder.getSpanStart(foregroundColorSpanThemableArr[i]);
                            int spanStart3 = spannableStringBuilder.getSpanStart(foregroundColorSpanThemableArr[i]);
                            if (spanStart2 != spanEnd) {
                                if (spanStart2 > spanEnd) {
                                    for (int i2 = spanEnd; i2 <= spanStart2; i2++) {
                                        if (!Character.isWhitespace(spannableStringBuilder.charAt(i2))) {
                                            break;
                                        }
                                    }
                                }
                            }
                            spanEnd = spanStart3;
                        }
                        chatActivity.setHighlightQuote(messageObject.getRealId(), charSequence.subSequence(spanStart, spanEnd).toString(), spanStart);
                        return chatActivity;
                    }
                }
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
        return chatActivity;
    }

    public void setOpenedDialogId(long j, long j2) {
        MessagesStorage.TopicKey topicKey = this.openedDialogId;
        topicKey.dialogId = j;
        topicKey.topicId = j2;
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr == null) {
            return;
        }
        for (ViewPage viewPage : viewPageArr) {
            if (viewPage.isDefaultDialogType() && AndroidUtilities.isTablet()) {
                viewPage.dialogsAdapter.setOpenedDialogId(this.openedDialogId.dialogId);
            }
        }
        updateVisibleRows(MessagesController.UPDATE_MASK_SELECT_DIALOG);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onItemLongClick(RecyclerListView recyclerListView, View view, int i, float f, float f2, int i2, RecyclerView.Adapter adapter) {
        DialogsSearchAdapter dialogsSearchAdapter;
        DialogsSearchAdapter dialogsSearchAdapter2;
        final long jMakeEncryptedDialogId;
        if (getParentActivity() != null && !(view instanceof DialogsHintCell)) {
            if (adapter.getItemViewType(i) == 21) {
                return false;
            }
            if (!this.actionBar.isActionModeShowed() && !AndroidUtilities.isTablet() && !this.onlySelect && (view instanceof DialogCell)) {
                DialogCell dialogCell = (DialogCell) view;
                if (!getMessagesController().isForum(dialogCell.getDialogId()) && !this.rightSlidingDialogContainer.hasFragment() && dialogCell.isPointInsideAvatar(f, f2)) {
                    return showChatPreview(dialogCell);
                }
            }
            RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment()) {
                return false;
            }
            SearchViewPager searchViewPager = this.searchViewPager;
            if (searchViewPager != null && adapter == (dialogsSearchAdapter2 = searchViewPager.dialogsSearchAdapter)) {
                Object item = dialogsSearchAdapter2.getItem(i);
                if (!this.searchViewPager.dialogsSearchAdapter.isSearchWas()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                    builder.setTitle(LocaleController.getString(C2369R.string.ClearSearchSingleAlertTitle));
                    if (item instanceof TLRPC.Chat) {
                        TLRPC.Chat chat = (TLRPC.Chat) item;
                        if (chat.monoforum) {
                            builder.setMessage(LocaleController.formatString("ClearSearchSingleChatAlertText", C2369R.string.ClearSearchSingleChatAlertText, ForumUtilities.getMonoForumTitle(this.currentAccount, chat)));
                        } else {
                            builder.setMessage(LocaleController.formatString("ClearSearchSingleChatAlertText", C2369R.string.ClearSearchSingleChatAlertText, chat.title));
                        }
                        jMakeEncryptedDialogId = -chat.f1571id;
                    } else if (item instanceof TLRPC.User) {
                        TLRPC.User user = (TLRPC.User) item;
                        if (user.f1734id == getUserConfig().clientUserId) {
                            builder.setMessage(LocaleController.formatString("ClearSearchSingleChatAlertText", C2369R.string.ClearSearchSingleChatAlertText, LocaleController.getString(C2369R.string.SavedMessages)));
                        } else {
                            builder.setMessage(LocaleController.formatString("ClearSearchSingleUserAlertText", C2369R.string.ClearSearchSingleUserAlertText, ContactsController.formatName(user.first_name, user.last_name)));
                        }
                        jMakeEncryptedDialogId = user.f1734id;
                    } else {
                        if (!(item instanceof TLRPC.EncryptedChat)) {
                            return false;
                        }
                        TLRPC.User user2 = getMessagesController().getUser(Long.valueOf(((TLRPC.EncryptedChat) item).user_id));
                        builder.setMessage(LocaleController.formatString("ClearSearchSingleUserAlertText", C2369R.string.ClearSearchSingleUserAlertText, ContactsController.formatName(user2.first_name, user2.last_name)));
                        jMakeEncryptedDialogId = DialogObject.makeEncryptedDialogId(r9.f1583id);
                    }
                    builder.setPositiveButton(LocaleController.getString(C2369R.string.ClearSearchRemove), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda117
                        @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                        public final void onClick(AlertDialog alertDialog, int i3) {
                            this.f$0.lambda$onItemLongClick$88(jMakeEncryptedDialogId, alertDialog, i3);
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
                    AlertDialog alertDialogCreate = builder.create();
                    showDialog(alertDialogCreate);
                    TextView textView = (TextView) alertDialogCreate.getButton(-1);
                    if (textView != null) {
                        textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                    }
                    return true;
                }
            }
            SearchViewPager searchViewPager2 = this.searchViewPager;
            if (searchViewPager2 != null && adapter == (dialogsSearchAdapter = searchViewPager2.dialogsSearchAdapter)) {
                if (this.onlySelect) {
                    onItemClick(view, i, adapter, f, f2);
                    return false;
                }
                long dialogId = (!(view instanceof ProfileSearchCell) || dialogsSearchAdapter.isGlobalSearch(i)) ? 0L : ((ProfileSearchCell) view).getDialogId();
                if (dialogId == 0) {
                    return false;
                }
                showOrUpdateActionMode(dialogId, view);
                return true;
            }
            TLObject item2 = ((DialogsAdapter) adapter).getItem(i);
            if (item2 instanceof TLRPC.Dialog) {
                TLRPC.Dialog dialog = (TLRPC.Dialog) item2;
                if (this.onlySelect) {
                    if ((this.initialDialogsType != 3 && !clickSelectsDialog()) || !validateSlowModeDialog(dialog.f1577id)) {
                        return false;
                    }
                    if (this.initialDialogsType == 1 && clickSelectsDialog() && this.canSelectTopics && getMessagesController().isForum(dialog.f1577id)) {
                        Bundle bundle = new Bundle();
                        bundle.putLong("chat_id", -dialog.f1577id);
                        bundle.putBoolean("for_select", true);
                        bundle.putBoolean("forward_to", true);
                        bundle.putBoolean("bot_share_to", this.initialDialogsType == 1);
                        bundle.putBoolean("quote", this.isQuote);
                        bundle.putBoolean("reply_to", this.isReplyTo);
                        TopicsFragment topicsFragment = new TopicsFragment(bundle);
                        topicsFragment.setForwardFromDialogFragment(this);
                        presentFragment(topicsFragment);
                        return false;
                    }
                    addOrRemoveSelectedDialog(dialog.f1577id, view);
                    updateSelectedCount();
                } else {
                    if (dialog instanceof TLRPC.TL_dialogFolder) {
                        onArchiveLongPress(view);
                        return false;
                    }
                    if (this.actionBar.isActionModeShowed() && isDialogPinned(dialog)) {
                        return false;
                    }
                    showOrUpdateActionMode(dialog.f1577id, view);
                }
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onItemLongClick$88(long j, AlertDialog alertDialog, int i) {
        this.searchViewPager.dialogsSearchAdapter.removeRecentSearch(j);
    }

    private void onArchiveLongPress(View view) {
        try {
            view.performHapticFeedback(0, 2);
        } catch (Exception unused) {
        }
        BottomSheet.Builder builder = new BottomSheet.Builder(getParentActivity());
        boolean z = getMessagesStorage().getArchiveUnreadCount() != 0;
        builder.setItems(new CharSequence[]{z ? LocaleController.getString(C2369R.string.MarkAllAsRead) : null, LocaleController.getString(SharedConfig.archiveHidden ? C2369R.string.PinInTheList : C2369R.string.HideAboveTheList)}, new int[]{z ? C2369R.drawable.msg_markread : 0, SharedConfig.archiveHidden ? C2369R.drawable.chats_pin : C2369R.drawable.chats_unpin}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda104
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                this.f$0.lambda$onArchiveLongPress$89(dialogInterface, i);
            }
        });
        showDialog(builder.create());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onArchiveLongPress$89(DialogInterface dialogInterface, int i) {
        ViewPage[] viewPageArr;
        if (i == 0) {
            getMessagesStorage().readAllDialogs(1);
            return;
        }
        if (i != 1 || (viewPageArr = this.viewPages) == null) {
            return;
        }
        for (ViewPage viewPage : viewPageArr) {
            if (viewPage.dialogsType == 0 && viewPage.getVisibility() == 0) {
                viewPage.listView.toggleArchiveHidden(true, findArchiveDialogCell(viewPage));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public DialogCell findArchiveDialogCell(ViewPage viewPage) {
        DialogsRecyclerView dialogsRecyclerView = viewPage.listView;
        for (int i = 0; i < dialogsRecyclerView.getChildCount(); i++) {
            View childAt = dialogsRecyclerView.getChildAt(i);
            if (childAt instanceof DialogCell) {
                DialogCell dialogCell = (DialogCell) childAt;
                if (dialogCell.isFolderCell()) {
                    return dialogCell;
                }
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r14v13, types: [android.view.ViewGroup] */
    /* JADX WARN: Type inference failed for: r14v14 */
    /* JADX WARN: Type inference failed for: r14v16 */
    /* JADX WARN: Type inference failed for: r29v0, types: [org.telegram.ui.ActionBar.BaseFragment, org.telegram.ui.DialogsActivity] */
    /* JADX WARN: Type inference failed for: r7v15, types: [android.view.ViewGroup] */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [boolean] */
    public boolean showChatPreview(final DialogCell dialogCell) {
        long j;
        TLRPC.Chat chat;
        boolean z;
        int i;
        ViewGroup viewGroup;
        MessagesController.DialogFilter dialogFilter;
        boolean[] zArr;
        long j2;
        ?? r8;
        final long j3;
        boolean z2;
        int i2;
        int i3;
        int i4;
        int size;
        int i5;
        ScrollView scrollView;
        ActionBarMenuSubItem actionBarMenuSubItem;
        ?? r14;
        boolean z3;
        ScrollView scrollView2;
        LinearLayout linearLayout;
        boolean z4 = false;
        if (dialogCell.isDialogFolder()) {
            if (dialogCell.getCurrentDialogFolderId() == 1) {
                onArchiveLongPress(dialogCell);
            }
            return false;
        }
        final long dialogId = dialogCell.getDialogId();
        Bundle bundle = new Bundle();
        int messageId = dialogCell.getMessageId();
        if (DialogObject.isEncryptedDialog(dialogId)) {
            return false;
        }
        if (DialogObject.isUserDialog(dialogId)) {
            bundle.putLong("user_id", dialogId);
        } else {
            if (messageId == 0 || (chat = getMessagesController().getChat(Long.valueOf(-dialogId))) == null || chat.migrated_to == null) {
                j = dialogId;
            } else {
                bundle.putLong("migrated_to", dialogId);
                j = -chat.migrated_to.channel_id;
            }
            bundle.putLong("chat_id", -j);
        }
        if (messageId != 0) {
            bundle.putInt("message_id", messageId);
        }
        final ArrayList arrayList = new ArrayList();
        arrayList.add(Long.valueOf(dialogId));
        boolean z5 = getMessagesController().filtersEnabled && getMessagesController().dialogFiltersLoaded && getMessagesController().dialogFilters != null && getMessagesController().dialogFilters.size() > 0;
        final ActionBarPopupWindow.ActionBarPopupWindowLayout[] actionBarPopupWindowLayoutArr = new ActionBarPopupWindow.ActionBarPopupWindowLayout[1];
        if (z5) {
            LinearLayout linearLayout2 = new LinearLayout(getParentActivity());
            linearLayout2.setOrientation(1);
            ScrollView scrollView3 = new ScrollView(getParentActivity()) { // from class: org.telegram.ui.DialogsActivity.40
                @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.View
                protected void onMeasure(int i6, int i7) {
                    super.onMeasure(i6, View.MeasureSpec.makeMeasureSpec((int) Math.min(View.MeasureSpec.getSize(i7), Math.min(AndroidUtilities.displaySize.y * 0.35f, AndroidUtilities.m1146dp(400.0f))), View.MeasureSpec.getMode(i7)));
                }
            };
            LinearLayout linearLayout3 = new LinearLayout(getParentActivity());
            linearLayout3.setOrientation(1);
            scrollView3.addView(linearLayout3);
            int size2 = getMessagesController().dialogFilters.size();
            int i6 = 0;
            ActionBarMenuSubItem actionBarMenuSubItem2 = null;
            while (i6 < size2) {
                final MessagesController.DialogFilter dialogFilter2 = getMessagesController().dialogFilters.get(i6);
                if (dialogFilter2.isDefault()) {
                    i5 = i6;
                    scrollView = scrollView3;
                } else {
                    final boolean zIncludesDialog = dialogFilter2.includesDialog(AccountInstance.getInstance(this.currentAccount), dialogId);
                    i5 = i6;
                    scrollView = scrollView3;
                    final ArrayList dialogsCount = FiltersListBottomSheet.getDialogsCount(this, dialogFilter2, arrayList, true, z4);
                    if (zIncludesDialog || dialogFilter2.alwaysShow.size() + dialogsCount.size() <= 100) {
                        actionBarMenuSubItem = new ActionBarMenuSubItem((Context) getParentActivity(), 2, false, false, (Theme.ResourcesProvider) null);
                        actionBarMenuSubItem.setChecked(zIncludesDialog);
                        LinearLayout linearLayout4 = linearLayout2;
                        Spannable spannableReplaceAnimatedEmoji = MessageObject.replaceAnimatedEmoji(Emoji.replaceEmoji(dialogFilter2.name, actionBarMenuSubItem.getTextView().getPaint().getFontMetricsInt(), false), dialogFilter2.entities, actionBarMenuSubItem.getTextView().getPaint().getFontMetricsInt());
                        actionBarMenuSubItem.setEmojiCacheType(dialogFilter2.title_noanimate ? 26 : 0);
                        final long j4 = dialogId;
                        actionBarMenuSubItem.setTextAndIcon(spannableReplaceAnimatedEmoji, 0, new FolderDrawable(getContext(), C2369R.drawable.msg_folders, dialogFilter2.color));
                        actionBarMenuSubItem.getTextView().setEmojiColor(Theme.getColor(Theme.key_featuredStickers_addButton, this.resourceProvider));
                        actionBarMenuSubItem.setMinimumWidth(Opcodes.IF_ICMPNE);
                        r14 = linearLayout3;
                        z3 = z5;
                        scrollView2 = scrollView;
                        linearLayout = linearLayout4;
                        dialogId = j4;
                        actionBarMenuSubItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda85
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                this.f$0.lambda$showChatPreview$90(zIncludesDialog, dialogsCount, dialogFilter2, j4, view);
                            }
                        });
                        r14.addView(actionBarMenuSubItem);
                    }
                    i6 = i5 + 1;
                    linearLayout2 = linearLayout;
                    scrollView3 = scrollView2;
                    linearLayout3 = r14;
                    z5 = z3;
                    actionBarMenuSubItem2 = actionBarMenuSubItem;
                    z4 = false;
                }
                linearLayout = linearLayout2;
                z3 = z5;
                actionBarMenuSubItem = actionBarMenuSubItem2;
                scrollView2 = scrollView;
                r14 = linearLayout3;
                i6 = i5 + 1;
                linearLayout2 = linearLayout;
                scrollView3 = scrollView2;
                linearLayout3 = r14;
                z5 = z3;
                actionBarMenuSubItem2 = actionBarMenuSubItem;
                z4 = false;
            }
            ?? r7 = linearLayout2;
            ScrollView scrollView4 = scrollView3;
            LinearLayout linearLayout5 = linearLayout3;
            z = z5;
            i = Opcodes.IF_ICMPNE;
            if (actionBarMenuSubItem2 != null) {
                actionBarMenuSubItem2.updateSelectorBackground(false, true);
            }
            if (linearLayout5.getChildCount() <= 0) {
                viewGroup = r7;
                z = false;
            } else {
                ActionBarPopupWindow.GapView gapView = new ActionBarPopupWindow.GapView(getParentActivity(), getResourceProvider(), Theme.key_actionBarDefaultSubmenuSeparator);
                gapView.setTag(C2369R.id.fit_width_tag, 1);
                ActionBarMenuSubItem actionBarMenuSubItem3 = new ActionBarMenuSubItem(getParentActivity(), true, false);
                actionBarMenuSubItem3.setTextAndIcon(LocaleController.getString(C2369R.string.Back), C2369R.drawable.ic_ab_back);
                actionBarMenuSubItem3.setMinimumWidth(Opcodes.IF_ICMPNE);
                actionBarMenuSubItem3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda86
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        DialogsActivity.m12863$r8$lambda$ifNCam95n8kXJagrJuoAgqZXnM(actionBarPopupWindowLayoutArr, view);
                    }
                });
                r7.addView(actionBarMenuSubItem3);
                r7.addView(gapView, LayoutHelper.createLinear(-1, 8));
                r7.addView(scrollView4);
                viewGroup = r7;
            }
        } else {
            z = z5;
            i = Opcodes.IF_ICMPNE;
            viewGroup = null;
        }
        int i7 = z ? 3 : 2;
        final WeakReference[] weakReferenceArr = {new WeakReference(null)};
        ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(getParentActivity(), C2369R.drawable.popup_fixed_alert2, getResourceProvider(), i7);
        actionBarPopupWindowLayoutArr[0] = actionBarPopupWindowLayout;
        if (z) {
            final int[] iArr = {actionBarPopupWindowLayout.addViewToSwipeBack(viewGroup)};
            ActionBarMenuSubItem actionBarMenuSubItem4 = new ActionBarMenuSubItem(getParentActivity(), true, false);
            actionBarMenuSubItem4.setTextAndIcon(LocaleController.getString(C2369R.string.FilterAddTo), C2369R.drawable.msg_addfolder);
            actionBarMenuSubItem4.setMinimumWidth(i);
            actionBarMenuSubItem4.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda87
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    actionBarPopupWindowLayoutArr[0].getSwipeBack().openForeground(iArr[0]);
                }
            });
            actionBarPopupWindowLayoutArr[0].addView(actionBarMenuSubItem4);
            actionBarPopupWindowLayoutArr[0].getSwipeBack().setOnHeightUpdateListener(new PopupSwipeBackLayout.IntCallback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda88
                @Override // org.telegram.ui.Components.PopupSwipeBackLayout.IntCallback
                public final void run(int i8) {
                    DialogsActivity.$r8$lambda$qwTLBci6cmhEV_gLZBWFnG4uwN4(weakReferenceArr, i8);
                }
            });
        }
        ActionBarMenuSubItem actionBarMenuSubItem5 = new ActionBarMenuSubItem(getParentActivity(), !z, false);
        if (dialogCell.getHasUnread()) {
            actionBarMenuSubItem5.setTextAndIcon(LocaleController.getString(C2369R.string.MarkAsRead), C2369R.drawable.msg_markread);
        } else {
            actionBarMenuSubItem5.setTextAndIcon(LocaleController.getString(C2369R.string.MarkAsUnread), C2369R.drawable.msg_markunread);
        }
        actionBarMenuSubItem5.setMinimumWidth(i);
        actionBarMenuSubItem5.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda89
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showChatPreview$94(dialogCell, dialogId, view);
            }
        });
        actionBarPopupWindowLayoutArr[0].addView(actionBarMenuSubItem5);
        boolean[] zArr2 = {true};
        final TLRPC.Dialog dialog = (TLRPC.Dialog) getMessagesController().dialogs_dict.get(dialogId);
        boolean z6 = (this.viewPages[0].dialogsType == 7 || this.viewPages[0].dialogsType == 8) && (!this.actionBar.isActionModeShowed() || this.actionBar.isActionModeShowed(null));
        if (z6) {
            dialogFilter = getMessagesController().selectedDialogFilter[this.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
        } else {
            dialogFilter = null;
        }
        if (isDialogPinned(dialog)) {
            zArr = zArr2;
            j2 = dialogId;
            r8 = 0;
        } else {
            ArrayList<TLRPC.Dialog> dialogs = getMessagesController().getDialogs(this.folderId);
            int size3 = dialogs.size();
            int i8 = 0;
            int i9 = 0;
            int i10 = 0;
            while (true) {
                if (i8 >= size3) {
                    zArr = zArr2;
                    j2 = dialogId;
                    break;
                }
                TLRPC.Dialog dialog2 = dialogs.get(i8);
                zArr = zArr2;
                if (dialog2 instanceof TLRPC.TL_dialogFolder) {
                    j2 = dialogId;
                } else if (isDialogPinned(dialog2)) {
                    j2 = dialogId;
                    if (DialogObject.isEncryptedDialog(dialog2.f1577id)) {
                        i10++;
                    } else {
                        i9++;
                    }
                } else {
                    j2 = dialogId;
                    if (!getMessagesController().isPromoDialog(dialog2.f1577id, false)) {
                        break;
                    }
                }
                i8++;
                zArr2 = zArr;
                dialogId = j2;
            }
            if (dialog == null || isDialogPinned(dialog)) {
                i2 = 0;
                i3 = 0;
                i4 = 0;
            } else {
                boolean zIsEncryptedDialog = DialogObject.isEncryptedDialog(j2);
                int i11 = !zIsEncryptedDialog ? 1 : 0;
                if (dialogFilter == null || !dialogFilter.alwaysShow.contains(Long.valueOf(j2))) {
                    i4 = i11;
                    i3 = zIsEncryptedDialog ? 1 : 0;
                    i2 = 0;
                } else {
                    i4 = i11;
                    i3 = zIsEncryptedDialog ? 1 : 0;
                    i2 = 1;
                }
            }
            if (z6 && dialogFilter != null) {
                size = 100 - dialogFilter.alwaysShow.size();
            } else if (this.folderId != 0 || dialogFilter != null) {
                if (getUserConfig().isPremium()) {
                    size = getMessagesController().maxFolderPinnedDialogsCountPremium;
                } else {
                    size = getMessagesController().maxFolderPinnedDialogsCountDefault;
                }
            } else if (getUserConfig().isPremium()) {
                size = getMessagesController().maxPinnedDialogsCountPremium;
            } else {
                size = getMessagesController().maxPinnedDialogsCountDefault;
            }
            r8 = 0;
            zArr[0] = i3 + i10 <= size && (i4 + i9) - i2 <= size;
        }
        if (zArr[r8]) {
            ActionBarMenuSubItem actionBarMenuSubItem6 = new ActionBarMenuSubItem(getParentActivity(), r8, r8);
            if (isDialogPinned(dialog)) {
                actionBarMenuSubItem6.setTextAndIcon(LocaleController.getString(C2369R.string.UnpinMessage), C2369R.drawable.msg_unpin);
            } else {
                actionBarMenuSubItem6.setTextAndIcon(LocaleController.getString(C2369R.string.PinMessage), C2369R.drawable.msg_pin);
            }
            actionBarMenuSubItem6.setMinimumWidth(Opcodes.IF_ICMPNE);
            final MessagesController.DialogFilter dialogFilter3 = dialogFilter;
            j3 = j2;
            actionBarMenuSubItem6.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda90
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showChatPreview$96(dialogFilter3, dialog, j3, view);
                }
            });
            actionBarPopupWindowLayoutArr[0].addView(actionBarMenuSubItem6);
        } else {
            j3 = j2;
        }
        if (DialogObject.isUserDialog(j3) && UserObject.isUserSelf(getMessagesController().getUser(Long.valueOf(j3)))) {
            z2 = false;
        } else {
            ActionBarMenuSubItem actionBarMenuSubItem7 = new ActionBarMenuSubItem(getParentActivity(), false, false);
            if (!getMessagesController().isDialogMuted(j3, 0L)) {
                actionBarMenuSubItem7.setTextAndIcon(LocaleController.getString(C2369R.string.Mute), C2369R.drawable.msg_mute);
            } else {
                actionBarMenuSubItem7.setTextAndIcon(LocaleController.getString(C2369R.string.Unmute), C2369R.drawable.msg_unmute);
            }
            actionBarMenuSubItem7.setMinimumWidth(Opcodes.IF_ICMPNE);
            actionBarMenuSubItem7.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda91
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$showChatPreview$97(j3, view);
                }
            });
            z2 = false;
            actionBarPopupWindowLayoutArr[0].addView(actionBarMenuSubItem7);
        }
        ActionBarMenuSubItem actionBarMenuSubItem8 = new ActionBarMenuSubItem(getParentActivity(), z2, true);
        actionBarMenuSubItem8.setIconColor(getThemedColor(Theme.key_text_RedRegular));
        int i12 = Theme.key_text_RedBold;
        actionBarMenuSubItem8.setTextColor(getThemedColor(i12));
        actionBarMenuSubItem8.setSelectorColor(Theme.multAlpha(getThemedColor(i12), 0.12f));
        actionBarMenuSubItem8.setTextAndIcon(LocaleController.getString(C2369R.string.Delete), C2369R.drawable.msg_delete);
        actionBarMenuSubItem8.setMinimumWidth(Opcodes.IF_ICMPNE);
        actionBarMenuSubItem8.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda92
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$showChatPreview$98(arrayList, view);
            }
        });
        actionBarPopupWindowLayoutArr[0].addView(actionBarMenuSubItem8);
        if (!getMessagesController().checkCanOpenChat(bundle, this)) {
            return false;
        }
        if (this.searchString != null) {
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
        }
        prepareBlurBitmap();
        this.parentLayout.setHighlightActionButtons(true);
        WeakReference weakReference = new WeakReference(new ChatActivity(bundle));
        weakReferenceArr[0] = weakReference;
        Point point = AndroidUtilities.displaySize;
        if (point.x > point.y) {
            presentFragmentAsPreview((BaseFragment) weakReference.get());
            return true;
        }
        presentFragmentAsPreviewWithMenu((BaseFragment) weakReference.get(), actionBarPopupWindowLayoutArr[0]);
        WeakReference weakReference2 = weakReferenceArr[0];
        if (weakReference2 == null || weakReference2.get() == null) {
            return true;
        }
        ((ChatActivity) weakReferenceArr[0].get()).allowExpandPreviewByClick = true;
        try {
            ((ChatActivity) weakReferenceArr[0].get()).getAvatarContainer().getAvatarImageView().performAccessibilityAction(64, null);
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChatPreview$90(boolean z, ArrayList arrayList, MessagesController.DialogFilter dialogFilter, long j, View view) {
        if (!z) {
            if (!arrayList.isEmpty()) {
                for (int i = 0; i < arrayList.size(); i++) {
                    dialogFilter.neverShow.remove(arrayList.get(i));
                }
                dialogFilter.alwaysShow.addAll(arrayList);
                FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, true, false, this, null);
            }
            getUndoView().showWithAction(j, 20, Integer.valueOf(arrayList.size()), dialogFilter, (Runnable) null, (Runnable) null);
        } else {
            dialogFilter.alwaysShow.remove(Long.valueOf(j));
            dialogFilter.neverShow.add(Long.valueOf(j));
            FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, true, false, this, null);
            getUndoView().showWithAction(j, 21, Integer.valueOf(arrayList.size()), dialogFilter, (Runnable) null, (Runnable) null);
        }
        hideActionMode(true);
        finishPreviewFragment();
    }

    /* renamed from: $r8$lambda$ifNCam95n8kXJag-rJuoAgqZXnM, reason: not valid java name */
    public static /* synthetic */ void m12863$r8$lambda$ifNCam95n8kXJagrJuoAgqZXnM(ActionBarPopupWindow.ActionBarPopupWindowLayout[] actionBarPopupWindowLayoutArr, View view) {
        ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = actionBarPopupWindowLayoutArr[0];
        if (actionBarPopupWindowLayout != null) {
            actionBarPopupWindowLayout.getSwipeBack().closeForeground();
        }
    }

    public static /* synthetic */ void $r8$lambda$qwTLBci6cmhEV_gLZBWFnG4uwN4(WeakReference[] weakReferenceArr, int i) {
        WeakReference weakReference = weakReferenceArr[0];
        if (weakReference == null || weakReference.get() == null || ((ChatActivity) weakReferenceArr[0].get()).getFragmentView() == null || !((ChatActivity) weakReferenceArr[0].get()).isInPreviewMode()) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = ((ChatActivity) weakReferenceArr[0].get()).getFragmentView().getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = AndroidUtilities.m1146dp(48.0f) + i;
            ((ChatActivity) weakReferenceArr[0].get()).getFragmentView().setLayoutParams(layoutParams);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChatPreview$94(DialogCell dialogCell, long j, View view) {
        if (dialogCell.getHasUnread()) {
            markAsRead(j);
        } else {
            markAsUnread(j);
        }
        finishPreviewFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChatPreview$96(final MessagesController.DialogFilter dialogFilter, final TLRPC.Dialog dialog, final long j, View view) {
        finishPreviewFragment();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda95
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showChatPreview$95(dialogFilter, dialog, j);
            }
        }, 100L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChatPreview$95(MessagesController.DialogFilter dialogFilter, TLRPC.Dialog dialog, long j) {
        int i;
        MessagesController.DialogFilter dialogFilter2;
        DialogsActivity dialogsActivity;
        int iMin = ConnectionsManager.DEFAULT_DATACENTER_ID;
        if (dialogFilter == null || !isDialogPinned(dialog)) {
            i = ConnectionsManager.DEFAULT_DATACENTER_ID;
        } else {
            int size = dialogFilter.pinnedDialogs.size();
            for (int i2 = 0; i2 < size; i2++) {
                iMin = Math.min(iMin, dialogFilter.pinnedDialogs.valueAt(i2));
            }
            i = iMin - this.canPinCount;
        }
        TLRPC.EncryptedChat encryptedChat = DialogObject.isEncryptedDialog(j) ? getMessagesController().getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(j))) : null;
        UndoView undoView = getUndoView();
        if (undoView == null) {
            return;
        }
        if (!isDialogPinned(dialog)) {
            pinDialog(j, true, dialogFilter, i, true);
            dialogFilter2 = dialogFilter;
            undoView.showWithAction(0L, 78, (Object) 1, (Object) 1600, (Runnable) null, (Runnable) null);
            if (dialogFilter2 != null) {
                if (encryptedChat != null) {
                    if (!dialogFilter2.alwaysShow.contains(Long.valueOf(encryptedChat.user_id))) {
                        dialogFilter2.alwaysShow.add(Long.valueOf(encryptedChat.user_id));
                    }
                } else if (!dialogFilter2.alwaysShow.contains(Long.valueOf(j))) {
                    dialogFilter2.alwaysShow.add(Long.valueOf(j));
                }
            }
        } else {
            pinDialog(j, false, dialogFilter, i, true);
            dialogFilter2 = dialogFilter;
            undoView.showWithAction(0L, 79, (Object) 1, (Object) 1600, (Runnable) null, (Runnable) null);
        }
        if (dialogFilter2 != null) {
            FilterCreateActivity.saveFilterToServer(dialogFilter2, dialogFilter2.flags, dialogFilter2.name, dialogFilter2.entities, dialogFilter2.title_noanimate, dialogFilter2.color, dialogFilter2.emoticon, dialogFilter2.alwaysShow, dialogFilter2.neverShow, dialogFilter2.pinnedDialogs, false, false, true, true, false, this, null);
            dialogsActivity = this;
        } else {
            dialogsActivity = this;
        }
        dialogsActivity.getMessagesController().reorderPinnedDialogs(dialogsActivity.folderId, null, 0L);
        dialogsActivity.updateCounters(true);
        ViewPage[] viewPageArr = dialogsActivity.viewPages;
        if (viewPageArr != null) {
            for (ViewPage viewPage : viewPageArr) {
                viewPage.dialogsAdapter.onReorderStateChanged(false);
            }
        }
        dialogsActivity.updateVisibleRows(MessagesController.UPDATE_MASK_REORDER | MessagesController.UPDATE_MASK_CHECK);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChatPreview$97(long j, View view) {
        boolean zIsDialogMuted = getMessagesController().isDialogMuted(j, 0L);
        if (!zIsDialogMuted) {
            getNotificationsController().setDialogNotificationsSettings(j, 0L, 3);
        } else {
            getNotificationsController().setDialogNotificationsSettings(j, 0L, 4);
        }
        BulletinFactory.createMuteBulletin(this, !zIsDialogMuted, null).show();
        finishPreviewFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showChatPreview$98(ArrayList arrayList, View view) {
        performSelectedDialogsAction(arrayList, 102, false, false);
        finishPreviewFragment();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFloatingButtonOffset() {
        FrameLayout frameLayout = this.floatingButtonContainer;
        if (frameLayout != null) {
            frameLayout.setTranslationY((this.floatingButtonTranslation - this.floatingButtonPanOffset) - (Math.max(this.additionalFloatingTranslation, this.additionalFloatingTranslation2) * (1.0f - this.floatingButtonHideProgress)));
            HintView2 hintView2 = this.storyHint;
            if (hintView2 != null) {
                hintView2.setTranslationY(this.floatingButtonContainer.getTranslationY());
            }
        }
        FrameLayout frameLayout2 = this.floatingButton2Container;
        if (frameLayout2 != null) {
            frameLayout2.setTranslationY(((this.floatingButtonTranslation - this.floatingButtonPanOffset) - (Math.max(this.additionalFloatingTranslation, this.additionalFloatingTranslation2) * (1.0f - this.floatingButtonHideProgress))) + (AndroidUtilities.m1146dp(48.0f) * this.floatingButtonHideProgress));
        }
    }

    private void updateStoriesPosting() {
        HintView2 hintView2;
        ActionBarMenuItem actionBarMenuItem;
        boolean zStoriesEnabled = getMessagesController().storiesEnabled();
        if (this.storiesEnabled != zStoriesEnabled) {
            FrameLayout frameLayout = this.floatingButton2Container;
            if (frameLayout != null) {
                frameLayout.setVisibility(((!this.onlySelect || this.initialDialogsType == 10) && this.folderId == 0 && zStoriesEnabled && ((actionBarMenuItem = this.searchItem) == null || !actionBarMenuItem.isSearchFieldVisible()) && !isInPreviewMode()) ? 0 : 8);
            }
            updateFloatingButtonOffset();
            if (!this.storiesEnabled && zStoriesEnabled && (hintView2 = this.storyHint) != null) {
                hintView2.show();
            }
            this.storiesEnabled = zStoriesEnabled;
        }
        RLottieImageView rLottieImageView = this.floatingButton;
        if (rLottieImageView == null || this.floatingButtonContainer == null) {
            return;
        }
        if (this.initialDialogsType == 10) {
            rLottieImageView.setImageResource(C2369R.drawable.floating_check);
            this.floatingButtonContainer.setContentDescription(LocaleController.getString(C2369R.string.Done));
        } else if (zStoriesEnabled) {
            rLottieImageView.setAnimation(C2369R.raw.write_contacts_fab_icon_camera, 56, 56);
            this.floatingButtonContainer.setContentDescription(LocaleController.getString(C2369R.string.AccDescrCaptureStory));
        } else {
            rLottieImageView.setAnimation(C2369R.raw.write_contacts_fab_icon, 52, 52);
            this.floatingButtonContainer.setContentDescription(LocaleController.getString(C2369R.string.NewMessageTitle));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasHiddenArchive() {
        return !this.onlySelect && this.initialDialogsType == 0 && this.folderId == 0 && getMessagesController().hasHiddenArchive();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean waitingForDialogsAnimationEnd(ViewPage viewPage) {
        return viewPage.dialogsItemAnimator.isRunning();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkAnimationFinished() {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda66
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$checkAnimationFinished$99();
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkAnimationFinished$99() {
        setDialogsListFrozen(false);
        updateDialogIndices();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScrollY(float f) {
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr != null) {
            int i = 0;
            int paddingTop = viewPageArr[0].listView.getPaddingTop() + ((int) f);
            while (true) {
                ViewPage[] viewPageArr2 = this.viewPages;
                if (i >= viewPageArr2.length) {
                    break;
                }
                viewPageArr2[i].listView.setTopGlowOffset(paddingTop);
                i++;
            }
        }
        if (this.fragmentView == null || f == this.scrollYOffset) {
            return;
        }
        this.scrollYOffset = f;
        Bulletin bulletin = this.topBulletin;
        if (bulletin != null) {
            bulletin.updatePosition();
        }
        DrawerProfileCell.AnimatedStatusView animatedStatusView = this.animatedStatusView;
        if (animatedStatusView != null) {
            animatedStatusView.translateY2((int) f);
            this.animatedStatusView.setAlpha(1.0f - ((-f) / ActionBar.getCurrentActionBarHeight()));
        }
        this.fragmentView.invalidate();
    }

    private void prepareBlurBitmap() {
        if (this.blurredView == null) {
            return;
        }
        int measuredWidth = (int) (this.fragmentView.getMeasuredWidth() / 6.0f);
        int measuredHeight = (int) (this.fragmentView.getMeasuredHeight() / 6.0f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        canvas.scale(0.16666667f, 0.16666667f);
        this.fragmentView.draw(canvas);
        Utilities.stackBlurBitmap(bitmapCreateBitmap, Math.max(7, Math.max(measuredWidth, measuredHeight) / Opcodes.GETFIELD));
        this.blurredView.setBackground(new BitmapDrawable(bitmapCreateBitmap));
        this.blurredView.setAlpha(0.0f);
        this.blurredView.setVisibility(0);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onTransitionAnimationProgress(boolean z, float f) {
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment()) {
            this.rightSlidingDialogContainer.getFragment().onTransitionAnimationProgress(z, f);
            return;
        }
        View view = this.blurredView;
        if (view == null || view.getVisibility() != 0) {
            return;
        }
        if (z) {
            this.blurredView.setAlpha(1.0f - f);
        } else {
            this.blurredView.setAlpha(f);
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        View view;
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if (rightSlidingDialogContainer != null && rightSlidingDialogContainer.hasFragment()) {
            this.rightSlidingDialogContainer.getFragment().onTransitionAnimationEnd(z, z2);
            return;
        }
        if (z && (view = this.blurredView) != null && view.getVisibility() == 0) {
            this.blurredView.setVisibility(8);
            this.blurredView.setBackground(null);
        }
        if (z && this.afterSignup) {
            try {
                this.fragmentView.performHapticFeedback(3, 2);
            } catch (Exception unused) {
            }
            if (getParentActivity() instanceof LaunchActivity) {
                ((LaunchActivity) getParentActivity()).getFireworksOverlay().start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetScroll() {
        if (this.scrollYOffset == 0.0f || this.hasStories) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ObjectAnimator.ofFloat(this, (Property<DialogsActivity, Float>) this.SCROLL_Y, 0.0f));
        animatorSet.setInterpolator(CubicBezierInterpolator.DEFAULT);
        animatorSet.setDuration(250L);
        animatorSet.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideActionMode(boolean z) {
        final float fMax;
        boolean z2;
        this.actionBar.hideActionMode();
        if (this.menuDrawable != null) {
            this.actionBar.setBackButtonContentDescription(LocaleController.getString(C2369R.string.AccDescrOpenMenu));
        }
        this.selectedDialogs.clear();
        MenuDrawable menuDrawable = this.menuDrawable;
        if (menuDrawable != null) {
            menuDrawable.setRotation(0.0f, true);
        } else {
            BackDrawable backDrawable = this.backDrawable;
            if (backDrawable != null) {
                backDrawable.setRotation(0.0f, true);
            }
        }
        FilterTabsView filterTabsView = this.filterTabsView;
        if (filterTabsView != null) {
            filterTabsView.animateColorsTo(Theme.key_actionBarTabLine, Theme.key_actionBarTabActiveText, Theme.key_actionBarTabUnactiveText, Theme.key_actionBarTabSelector, Theme.key_actionBarDefault);
        }
        ValueAnimator valueAnimator = this.actionBarColorAnimator;
        Object obj = null;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.actionBarColorAnimator = null;
        }
        if (this.progressToActionMode == 0.0f) {
            return;
        }
        if (this.hasStories) {
            setScrollY(-getMaxScrollYOffset());
            int i = 0;
            while (true) {
                ViewPage[] viewPageArr = this.viewPages;
                if (i >= viewPageArr.length) {
                    break;
                }
                ViewPage viewPage = viewPageArr[i];
                if (viewPage != null) {
                    viewPage.listView.cancelClickRunnables(true);
                }
                i++;
            }
            fMax = Math.max(0.0f, AndroidUtilities.m1146dp(81.0f) + this.scrollYOffset);
        } else {
            fMax = 0.0f;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.progressToActionMode, 0.0f);
        this.actionBarColorAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda24
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$hideActionMode$100(fMax, valueAnimator2);
            }
        });
        this.actionBarColorAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.41
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                DialogsActivity.this.actionBarColorAnimator = null;
                DialogsActivity.this.actionModeFullyShowed = false;
                DialogsActivity dialogsActivity = DialogsActivity.this;
                if (dialogsActivity.hasStories) {
                    dialogsActivity.invalidateScrollY = true;
                    DialogsActivity.this.fixScrollYAfterArchiveOpened = true;
                    DialogsActivity.this.fragmentView.invalidate();
                    DialogsActivity.this.scrollAdditionalOffset = -(AndroidUtilities.m1146dp(81.0f) - fMax);
                    DialogsActivity.this.viewPages[0].setTranslationY(0.0f);
                    for (int i2 = 0; i2 < DialogsActivity.this.viewPages.length; i2++) {
                        if (DialogsActivity.this.viewPages[i2] != null) {
                            DialogsActivity.this.viewPages[i2].listView.requestLayout();
                        }
                    }
                    DialogsActivity.this.fragmentView.requestLayout();
                }
            }
        });
        this.actionBarColorAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
        this.actionBarColorAnimator.setDuration(200L);
        this.actionBarColorAnimator.start();
        this.allowMoving = false;
        if (!this.movingDialogFilters.isEmpty()) {
            int size = this.movingDialogFilters.size();
            int i2 = 0;
            while (i2 < size) {
                MessagesController.DialogFilter dialogFilter = (MessagesController.DialogFilter) this.movingDialogFilters.get(i2);
                FilterCreateActivity.saveFilterToServer(dialogFilter, dialogFilter.flags, dialogFilter.name, dialogFilter.entities, dialogFilter.title_noanimate, dialogFilter.color, dialogFilter.emoticon, dialogFilter.alwaysShow, dialogFilter.neverShow, dialogFilter.pinnedDialogs, false, false, true, true, false, this, null);
                i2++;
                size = size;
                obj = null;
            }
            this.movingDialogFilters.clear();
        }
        if (this.movingWas) {
            getMessagesController().reorderPinnedDialogs(this.folderId, null, 0L);
            z2 = false;
            this.movingWas = false;
        } else {
            z2 = false;
        }
        updateCounters(true);
        ViewPage[] viewPageArr2 = this.viewPages;
        if (viewPageArr2 != null) {
            for (ViewPage viewPage2 : viewPageArr2) {
                viewPage2.dialogsAdapter.onReorderStateChanged(z2);
            }
        }
        updateVisibleRows(MessagesController.UPDATE_MASK_REORDER | MessagesController.UPDATE_MASK_CHECK | (z ? MessagesController.UPDATE_MASK_CHAT : 0));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideActionMode$100(float f, ValueAnimator valueAnimator) {
        if (this.hasStories) {
            this.viewPages[0].setTranslationY(f * (1.0f - this.progressToActionMode));
        }
        this.progressToActionMode = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        for (int i = 0; i < this.actionBar.getChildCount(); i++) {
            if (this.actionBar.getChildAt(i).getVisibility() == 0 && this.actionBar.getChildAt(i) != this.actionBar.getActionMode() && this.actionBar.getChildAt(i) != this.actionBar.getBackButton()) {
                this.actionBar.getChildAt(i).setAlpha(1.0f - this.progressToActionMode);
            }
        }
        View view = this.fragmentView;
        if (view != null) {
            view.invalidate();
        }
    }

    private int getPinnedCount() {
        ArrayList<TLRPC.Dialog> dialogsArray;
        if ((this.viewPages[0].dialogsType == 7 || this.viewPages[0].dialogsType == 8) && (!this.actionBar.isActionModeShowed() || this.actionBar.isActionModeShowed(null))) {
            dialogsArray = getDialogsArray(this.currentAccount, this.viewPages[0].dialogsType, this.folderId, this.dialogsListFrozen);
        } else {
            dialogsArray = getMessagesController().getDialogs(this.folderId);
        }
        int size = dialogsArray.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            TLRPC.Dialog dialog = dialogsArray.get(i2);
            if (!(dialog instanceof TLRPC.TL_dialogFolder)) {
                if (!isDialogPinned(dialog)) {
                    if (!getMessagesController().isPromoDialog(dialog.f1577id, false)) {
                        break;
                    }
                } else {
                    i++;
                }
            }
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDialogPinned(TLRPC.Dialog dialog) {
        if (dialog == null) {
            return false;
        }
        MessagesController.DialogFilter dialogFilter = null;
        if ((this.viewPages[0].dialogsType == 7 || this.viewPages[0].dialogsType == 8) && (!this.actionBar.isActionModeShowed() || this.actionBar.isActionModeShowed(null))) {
            dialogFilter = getMessagesController().selectedDialogFilter[this.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
        }
        if (dialogFilter != null) {
            return dialogFilter.pinnedDialogs.indexOfKey(dialog.f1577id) >= 0;
        }
        return dialog.pinned;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void performSelectedDialogsAction(ArrayList arrayList, int i, boolean z, boolean z2) {
        performSelectedDialogsAction(arrayList, i, z, z2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:178:0x02f9 A[LOOP:0: B:177:0x02f7->B:178:0x02f9, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:319:0x05a7  */
    /* JADX WARN: Removed duplicated region for block: B:321:0x05aa  */
    /* JADX WARN: Removed duplicated region for block: B:328:0x05bc  */
    /* JADX WARN: Removed duplicated region for block: B:329:0x05be  */
    /* JADX WARN: Removed duplicated region for block: B:330:0x05ee  */
    /* JADX WARN: Removed duplicated region for block: B:336:0x0608  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x060b  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x0624  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void performSelectedDialogsAction(final java.util.ArrayList r33, int r34, boolean r35, boolean r36, java.util.HashSet r37) {
        /*
            Method dump skipped, instructions count: 1738
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.performSelectedDialogsAction(java.util.ArrayList, int, boolean, boolean, java.util.HashSet):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSelectedDialogsAction$101(ArrayList arrayList) {
        getMessagesController().addDialogToFolder(arrayList, this.folderId == 0 ? 0 : 1, -1, null, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSelectedDialogsAction$103(ArrayList arrayList, final int i, final HashSet hashSet, final boolean z) {
        int i2;
        if (arrayList.isEmpty()) {
            return;
        }
        final ArrayList arrayList2 = new ArrayList(arrayList);
        UndoView undoView = getUndoView();
        if (undoView != null) {
            int i3 = i == 102 ? 27 : 26;
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda120
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$performSelectedDialogsAction$102(i, arrayList2, z, hashSet);
                }
            };
            i2 = i;
            undoView.showWithAction(arrayList2, i3, (Object) null, (Object) null, runnable, (Runnable) null);
        } else {
            i2 = i;
        }
        hideActionMode(i2 == 103);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSelectedDialogsAction$102(int i, ArrayList arrayList, boolean z, HashSet hashSet) {
        if (i == 102) {
            getMessagesController().setDialogsInTransaction(true);
            performSelectedDialogsAction(arrayList, i, false, false, z ? hashSet : null);
            getMessagesController().setDialogsInTransaction(false);
            getMessagesController().checkIfFolderEmpty(this.folderId);
            if (this.folderId == 0 || getDialogsArray(this.currentAccount, this.viewPages[0].dialogsType, this.folderId, false).size() != 0) {
                return;
            }
            this.viewPages[0].listView.setEmptyView(null);
            this.viewPages[0].progressView.setVisibility(4);
            lambda$onBackPressed$371();
            return;
        }
        performSelectedDialogsAction(arrayList, i, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSelectedDialogsAction$104(ArrayList arrayList, boolean z, boolean z2) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Long l = (Long) arrayList.get(i);
            long jLongValue = l.longValue();
            if (z) {
                getMessagesController().reportSpam(jLongValue, getMessagesController().getUser(l), null, null, false);
            }
            if (z2) {
                getMessagesController().deleteDialog(jLongValue, 0, true);
            }
            getMessagesController().blockPeer(jLongValue);
        }
        hideActionMode(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSelectedDialogsAction$105(AlertDialog alertDialog, int i) {
        getMessagesController().hidePromoDialog();
        hideActionMode(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSelectedDialogsAction$107(final int i, TLRPC.Chat chat, final long j, final boolean z, final boolean z2) {
        final TLRPC.Chat chat2;
        int i2;
        ArrayList arrayList;
        hideActionMode(false);
        if (i == 103 && ChatObject.isChannel(chat)) {
            chat2 = chat;
            if (!chat2.megagroup || ChatObject.isPublic(chat2)) {
                getMessagesController().deleteDialog(j, 2, z2);
                return;
            }
        } else {
            chat2 = chat;
        }
        if (i == 102 && this.folderId != 0 && getDialogsArray(this.currentAccount, this.viewPages[0].dialogsType, this.folderId, false).size() == 1) {
            this.viewPages[0].progressView.setVisibility(4);
        }
        this.debugLastUpdateAction = 3;
        int i3 = -1;
        if (i == 102) {
            setDialogsListFrozen(true);
            if (this.frozenDialogsList != null) {
                int i4 = 0;
                while (i4 < this.frozenDialogsList.size()) {
                    if (((TLRPC.Dialog) this.frozenDialogsList.get(i4)).f1577id == j) {
                        break;
                    } else {
                        i4++;
                    }
                }
                i4 = -1;
                checkAnimationFinished();
                i2 = i4;
            } else {
                i4 = -1;
                checkAnimationFinished();
                i2 = i4;
            }
        } else {
            i2 = -1;
        }
        UndoView undoView = getUndoView();
        if (undoView != null) {
            undoView.showWithAction(j, i == 103 ? 0 : 1, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda116
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$performSelectedDialogsAction$106(i, j, chat2, z, z2);
                }
            });
        }
        ArrayList arrayList2 = new ArrayList(getDialogsArray(this.currentAccount, this.viewPages[0].dialogsType, this.folderId, false));
        int i5 = 0;
        while (true) {
            if (i5 >= arrayList2.size()) {
                break;
            }
            if (((TLRPC.Dialog) arrayList2.get(i5)).f1577id == j) {
                i3 = i5;
                break;
            }
            i5++;
        }
        if (i == 102) {
            if (i2 >= 0 && i3 < 0 && (arrayList = this.frozenDialogsList) != null) {
                arrayList.remove(i2);
                this.viewPages[0].dialogsItemAnimator.prepareForRemove();
                this.viewPages[0].updateList(true);
                return;
            }
            setDialogsListFrozen(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSelectedDialogsAction$108(DialogInterface dialogInterface) {
        hideActionMode(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performSelectedDialogsAction$109(DialogInterface dialogInterface) {
        hideActionMode(true);
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0091  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void markAsRead(long r17) {
        /*
            Method dump skipped, instructions count: 228
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.markAsRead(long):void");
    }

    private void markAsUnread(long j) {
        getMessagesController().markDialogAsUnread(j, null, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void markDialogsAsRead(ArrayList arrayList) {
        this.debugLastUpdateAction = 2;
        setDialogsListFrozen(true);
        checkAnimationFinished();
        for (int i = 0; i < arrayList.size(); i++) {
            long j = ((TLRPC.Dialog) arrayList.get(i)).f1577id;
            TLRPC.Dialog dialog = (TLRPC.Dialog) arrayList.get(i);
            if (getMessagesController().isForum(j) || getMessagesController().isMonoForumWithManageRights(j)) {
                getMessagesController().markAllTopicsAsRead(j);
            }
            getMessagesController().markMentionsAsRead(j, 0L);
            MessagesController messagesController = getMessagesController();
            int i2 = dialog.top_message;
            messagesController.markDialogAsRead(j, i2, i2, dialog.last_message_date, false, 0L, 0, true, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: performDeleteOrClearDialogAction, reason: merged with bridge method [inline-methods] */
    public void lambda$performSelectedDialogsAction$106(int i, long j, TLRPC.Chat chat, boolean z, boolean z2) {
        AyuState.setAllowDeleteDialogs(true, 1);
        if (i == 103) {
            getMessagesController().deleteDialog(j, 1, z2);
            return;
        }
        if (chat != null) {
            if (ChatObject.isNotInChat(chat)) {
                getMessagesController().deleteDialog(j, 0, z2);
            } else {
                getMessagesController().deleteParticipantFromChat(-j, getMessagesController().getUser(Long.valueOf(getUserConfig().getClientUserId())), (TLRPC.Chat) null, z2, false);
            }
        } else {
            getMessagesController().deleteDialog(j, 0, z2);
            if (z && z2) {
                getMessagesController().blockPeer(j);
            }
        }
        if (AndroidUtilities.isTablet()) {
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, Long.valueOf(j));
        }
        getMessagesController().checkIfFolderEmpty(this.folderId);
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x013b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void pinDialog(long r14, boolean r16, org.telegram.messenger.MessagesController.DialogFilter r17, int r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 322
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.pinDialog(long, boolean, org.telegram.messenger.MessagesController$DialogFilter, int, boolean):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$pinDialog$110() {
        setDialogsListFrozen(false);
    }

    public void scrollToTop(boolean z, boolean z2) {
        int i = (this.viewPages[0].dialogsType == 0 && hasHiddenArchive() && this.viewPages[0].archivePullViewState == 2) ? 1 : 0;
        int i2 = (!this.hasStories || z2 || this.dialogStoriesCell.isExpanded()) ? 0 : -AndroidUtilities.m1146dp(81.0f);
        if (z) {
            this.viewPages[0].scrollHelper.setScrollDirection(1);
            this.viewPages[0].scrollHelper.scrollToPosition(i, i2, false, true);
            resetScroll();
        } else {
            this.viewPages[0].layoutManager.scrollToPositionWithOffset(i, i2);
            resetScroll();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCounters(boolean z) {
        int i;
        FilterTabsView filterTabsView;
        long j;
        TLRPC.User user;
        this.canDeletePsaSelected = false;
        this.canUnarchiveCount = 0;
        this.canUnmuteCount = 0;
        this.canMuteCount = 0;
        this.canPinCount = 0;
        this.canReadCount = 0;
        this.forumCount = 0;
        this.canClearCacheCount = 0;
        this.canReportSpamCount = 0;
        if (z) {
            return;
        }
        int size = this.selectedDialogs.size();
        long clientUserId = getUserConfig().getClientUserId();
        SharedPreferences notificationsSettings = getNotificationsSettings();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        while (i2 < size) {
            TLRPC.Dialog dialog = (TLRPC.Dialog) getMessagesController().dialogs_dict.get(((Long) this.selectedDialogs.get(i2)).longValue());
            if (dialog == null) {
                j = clientUserId;
            } else {
                long j2 = dialog.f1577id;
                boolean zIsDialogPinned = isDialogPinned(dialog);
                boolean z2 = dialog.unread_count != 0 || dialog.unread_mark;
                if (getMessagesController().isForum(j2)) {
                    this.forumCount++;
                }
                j = clientUserId;
                if (getMessagesController().isDialogMuted(j2, 0L)) {
                    this.canUnmuteCount++;
                } else {
                    this.canMuteCount++;
                }
                if (z2) {
                    this.canReadCount++;
                }
                if (this.folderId == 1 || dialog.folder_id == 1) {
                    this.canUnarchiveCount++;
                } else if (j2 != j && j2 != 777000 && !getMessagesController().isPromoDialog(j2, false)) {
                    i5++;
                }
                if (!DialogObject.isUserDialog(j2) || j2 == j || j2 == UserObject.VERIFY || MessagesController.isSupportUser(getMessagesController().getUser(Long.valueOf(j2)))) {
                    i7++;
                } else {
                    if (notificationsSettings.getBoolean("dialog_bar_report" + j2, true)) {
                        this.canReportSpamCount++;
                    }
                }
                if (DialogObject.isChannel(dialog)) {
                    TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(-j2));
                    if (getMessagesController().isPromoDialog(dialog.f1577id, true)) {
                        this.canClearCacheCount++;
                        if (getMessagesController().promoDialogType == MessagesController.PROMO_TYPE_PSA) {
                            i3++;
                            this.canDeletePsaSelected = true;
                        }
                    } else {
                        if (zIsDialogPinned) {
                            i6++;
                        } else {
                            this.canPinCount++;
                        }
                        if (chat == null || !chat.megagroup || ChatObject.isPublic(chat)) {
                            this.canClearCacheCount++;
                        }
                        i3++;
                    }
                } else {
                    boolean zIsChatDialog = DialogObject.isChatDialog(dialog.f1577id);
                    if (zIsChatDialog) {
                        getMessagesController().getChat(Long.valueOf(-dialog.f1577id));
                    }
                    if (DialogObject.isEncryptedDialog(dialog.f1577id)) {
                        TLRPC.EncryptedChat encryptedChat = getMessagesController().getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(dialog.f1577id)));
                        if (encryptedChat != null) {
                            user = getMessagesController().getUser(Long.valueOf(encryptedChat.user_id));
                        } else {
                            user = new TLRPC.TL_userEmpty();
                        }
                    } else {
                        user = (zIsChatDialog || !DialogObject.isUserDialog(dialog.f1577id)) ? null : getMessagesController().getUser(Long.valueOf(dialog.f1577id));
                    }
                    if (user != null && user.bot) {
                        MessagesController.isSupportUser(user);
                    }
                    if (zIsDialogPinned) {
                        i6++;
                    } else {
                        this.canPinCount++;
                    }
                }
                i4++;
                i3++;
            }
            i2++;
            clientUserId = j;
        }
        ActionBarMenuItem actionBarMenuItem = this.deleteItem;
        if (actionBarMenuItem != null) {
            if (i3 != size) {
                actionBarMenuItem.setVisibility(8);
            } else {
                actionBarMenuItem.setVisibility(0);
            }
        }
        ActionBarMenuSubItem actionBarMenuSubItem = this.clearItem;
        if (actionBarMenuSubItem != null) {
            int i8 = this.canClearCacheCount;
            if ((i8 != 0 && i8 != size) || (i4 != 0 && i4 != size)) {
                actionBarMenuSubItem.setVisibility(8);
            } else {
                actionBarMenuSubItem.setVisibility(0);
                if (this.canClearCacheCount != 0) {
                    this.clearItem.setText(LocaleController.getString(C2369R.string.ClearHistoryCache));
                } else {
                    this.clearItem.setText(LocaleController.getString(C2369R.string.ClearHistory));
                }
            }
        }
        ActionBarMenuSubItem actionBarMenuSubItem2 = this.archiveItem;
        if (actionBarMenuSubItem2 != null && this.archive2Item != null) {
            if (this.canUnarchiveCount != 0) {
                String string = LocaleController.getString(C2369R.string.Unarchive);
                this.archiveItem.setTextAndIcon(string, C2369R.drawable.msg_unarchive);
                this.archive2Item.setIcon(C2369R.drawable.msg_unarchive);
                this.archive2Item.setContentDescription(string);
                FilterTabsView filterTabsView2 = this.filterTabsView;
                if (filterTabsView2 != null && filterTabsView2.getVisibility() == 0) {
                    this.archive2Item.setVisibility(0);
                    this.archiveItem.setVisibility(8);
                } else {
                    this.archiveItem.setVisibility(0);
                    this.archive2Item.setVisibility(8);
                }
            } else if (i5 != 0) {
                String string2 = LocaleController.getString(C2369R.string.Archive);
                this.archiveItem.setTextAndIcon(string2, C2369R.drawable.msg_archive);
                this.archive2Item.setIcon(C2369R.drawable.msg_archive);
                this.archive2Item.setContentDescription(string2);
                FilterTabsView filterTabsView3 = this.filterTabsView;
                if (filterTabsView3 != null && filterTabsView3.getVisibility() == 0) {
                    this.archive2Item.setVisibility(0);
                    this.archiveItem.setVisibility(8);
                } else {
                    this.archiveItem.setVisibility(0);
                    this.archive2Item.setVisibility(8);
                }
            } else {
                actionBarMenuSubItem2.setVisibility(8);
                this.archive2Item.setVisibility(8);
            }
        }
        ActionBarMenuItem actionBarMenuItem2 = this.pinItem;
        if (actionBarMenuItem2 == null || this.pin2Item == null) {
            i = 0;
        } else if (this.canPinCount + i6 != size) {
            actionBarMenuItem2.setVisibility(8);
            this.pin2Item.setVisibility(8);
            i = 0;
        } else {
            FilterTabsView filterTabsView4 = this.filterTabsView;
            if (filterTabsView4 != null && filterTabsView4.getVisibility() == 0) {
                i = 0;
                this.pin2Item.setVisibility(0);
                this.pinItem.setVisibility(8);
            } else {
                i = 0;
                this.pinItem.setVisibility(0);
                this.pin2Item.setVisibility(8);
            }
        }
        ActionBarMenuSubItem actionBarMenuSubItem3 = this.blockItem;
        if (actionBarMenuSubItem3 != null) {
            if (i7 != 0) {
                actionBarMenuSubItem3.setVisibility(8);
            } else {
                actionBarMenuSubItem3.setVisibility(i);
            }
        }
        if (this.removeFromFolderItem != null) {
            FilterTabsView filterTabsView5 = this.filterTabsView;
            boolean z3 = filterTabsView5 == null || filterTabsView5.getVisibility() != 0 || this.filterTabsView.currentTabIsDefault();
            if (!z3) {
                try {
                    z3 = size >= getDialogsArray(this.currentAccount, this.viewPages[0].dialogsAdapter.getDialogsType(), this.folderId, this.dialogsListFrozen).size();
                } catch (Exception unused) {
                }
            }
            if (z3) {
                this.removeFromFolderItem.setVisibility(8);
            } else {
                this.removeFromFolderItem.setVisibility(0);
            }
        }
        if (this.addToFolderItem != null) {
            if (this.folderId == 1 || ((filterTabsView = this.filterTabsView) != null && filterTabsView.getVisibility() == 0 && this.filterTabsView.currentTabIsDefault() && !FiltersListBottomSheet.getCanAddDialogFilters(this, this.selectedDialogs).isEmpty())) {
                this.addToFolderItem.setVisibility(0);
            } else {
                this.addToFolderItem.setVisibility(8);
            }
        }
        ActionBarMenuItem actionBarMenuItem3 = this.muteItem;
        if (actionBarMenuItem3 != null) {
            if (this.canUnmuteCount != 0) {
                actionBarMenuItem3.setIcon(C2369R.drawable.msg_unmute);
                this.muteItem.setContentDescription(LocaleController.getString(C2369R.string.ChatsUnmute));
            } else {
                actionBarMenuItem3.setIcon(C2369R.drawable.msg_mute);
                this.muteItem.setContentDescription(LocaleController.getString(C2369R.string.ChatsMute));
            }
        }
        ActionBarMenuSubItem actionBarMenuSubItem4 = this.readItem;
        if (actionBarMenuSubItem4 != null) {
            if (this.canReadCount != 0) {
                actionBarMenuSubItem4.setTextAndIcon(LocaleController.getString(C2369R.string.MarkAsRead), C2369R.drawable.msg_markread);
                this.readItem.setVisibility(0);
            } else if (this.forumCount == 0) {
                actionBarMenuSubItem4.setTextAndIcon(LocaleController.getString(C2369R.string.MarkAsUnread), C2369R.drawable.msg_markunread);
                this.readItem.setVisibility(0);
            } else {
                actionBarMenuSubItem4.setVisibility(8);
            }
        }
        ActionBarMenuItem actionBarMenuItem4 = this.pinItem;
        if (actionBarMenuItem4 == null || this.pin2Item == null) {
            return;
        }
        if (this.canPinCount != 0) {
            actionBarMenuItem4.setIcon(C2369R.drawable.msg_pin);
            this.pinItem.setContentDescription(LocaleController.getString(C2369R.string.PinToTop));
            this.pin2Item.setText(LocaleController.getString(C2369R.string.DialogPin));
        } else {
            actionBarMenuItem4.setIcon(C2369R.drawable.msg_unpin);
            this.pinItem.setContentDescription(LocaleController.getString(C2369R.string.UnpinFromTop));
            this.pin2Item.setText(LocaleController.getString(C2369R.string.DialogUnpin));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean validateSlowModeDialog(long j) {
        TLRPC.Chat chat;
        ChatActivityEnterView chatActivityEnterView;
        if ((this.messagesCount <= 1 && ((chatActivityEnterView = this.commentView) == null || chatActivityEnterView.getVisibility() != 0 || TextUtils.isEmpty(this.commentView.getFieldText()))) || !DialogObject.isChatDialog(j) || (chat = getMessagesController().getChat(Long.valueOf(-j))) == null || ChatObject.hasAdminRights(chat) || !chat.slowmode_enabled) {
            return true;
        }
        AlertsCreator.showSimpleAlert(this, LocaleController.getString(C2369R.string.Slowmode), LocaleController.getString(C2369R.string.SlowmodeSendError));
        return false;
    }

    private void showOrUpdateActionMode(long j, View view) {
        addOrRemoveSelectedDialog(j, view);
        boolean z = true;
        if (this.actionBar.isActionModeShowed()) {
            if (this.selectedDialogs.isEmpty()) {
                hideActionMode(true);
                return;
            }
        } else {
            if (this.searchIsShowed) {
                createActionMode("search_dialogs_action_mode");
                if (this.actionBar.getBackButton().getDrawable() instanceof MenuDrawable) {
                    this.actionBar.setBackButtonDrawable(new BackDrawable(false));
                }
            } else {
                createActionMode(null);
            }
            AndroidUtilities.hideKeyboard(this.fragmentView.findFocus());
            this.actionBar.setActionModeOverrideColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            this.actionBar.showActionMode();
            if (!this.hasStories) {
                resetScroll();
            }
            if (this.menuDrawable != null) {
                this.actionBar.setBackButtonContentDescription(LocaleController.getString(C2369R.string.AccDescrGoBack));
            }
            if (getPinnedCount() > 1) {
                ViewPage[] viewPageArr = this.viewPages;
                if (viewPageArr != null) {
                    for (ViewPage viewPage : viewPageArr) {
                        viewPage.dialogsAdapter.onReorderStateChanged(true);
                    }
                }
                updateVisibleRows(MessagesController.UPDATE_MASK_REORDER);
            }
            if (!this.searchIsShowed) {
                AnimatorSet animatorSet = new AnimatorSet();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < this.actionModeViews.size(); i++) {
                    View view2 = (View) this.actionModeViews.get(i);
                    view2.setPivotY(ActionBar.getCurrentActionBarHeight() / 2);
                    AndroidUtilities.clearDrawableAnimation(view2);
                    arrayList.add(ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.SCALE_Y, 0.1f, 1.0f));
                }
                animatorSet.playTogether(arrayList);
                animatorSet.setDuration(200L);
                animatorSet.start();
            }
            ValueAnimator valueAnimator = this.actionBarColorAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            this.actionBarColorAnimator = ValueAnimator.ofFloat(this.progressToActionMode, 1.0f);
            final float f = 0.0f;
            if (this.hasStories) {
                int i2 = 0;
                while (true) {
                    ViewPage[] viewPageArr2 = this.viewPages;
                    if (i2 >= viewPageArr2.length) {
                        break;
                    }
                    ViewPage viewPage2 = viewPageArr2[i2];
                    if (viewPage2 != null) {
                        viewPage2.listView.cancelClickRunnables(true);
                    }
                    i2++;
                }
                float fMax = Math.max(0.0f, AndroidUtilities.m1146dp(81.0f) + this.scrollYOffset);
                if (fMax != 0.0f) {
                    this.actionModeAdditionalHeight = (int) fMax;
                    this.fragmentView.requestLayout();
                }
                f = fMax;
            }
            this.actionBarColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda122
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    this.f$0.lambda$showOrUpdateActionMode$111(f, valueAnimator2);
                }
            });
            this.actionBarColorAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.42
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    DialogsActivity.this.actionBarColorAnimator = null;
                    DialogsActivity.this.actionModeAdditionalHeight = 0;
                    DialogsActivity.this.actionModeFullyShowed = true;
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    if (dialogsActivity.hasStories) {
                        dialogsActivity.scrollAdditionalOffset = AndroidUtilities.m1146dp(81.0f) - f;
                        DialogsActivity.this.viewPages[0].setTranslationY(0.0f);
                        for (int i3 = 0; i3 < DialogsActivity.this.viewPages.length; i3++) {
                            if (DialogsActivity.this.viewPages[i3] != null) {
                                DialogsActivity.this.viewPages[i3].listView.requestLayout();
                            }
                        }
                        DialogsActivity.this.dialogStoriesCell.setProgressToCollapse(1.0f, false);
                        DialogsActivity.this.fragmentView.requestLayout();
                    }
                }
            });
            this.actionBarColorAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.actionBarColorAnimator.setDuration(200L);
            this.actionBarColorAnimator.start();
            FilterTabsView filterTabsView = this.filterTabsView;
            if (filterTabsView != null) {
                filterTabsView.animateColorsTo(Theme.key_profile_tabSelectedLine, Theme.key_profile_tabSelectedText, Theme.key_profile_tabText, Theme.key_profile_tabSelector, Theme.key_actionBarActionModeDefault);
            }
            MenuDrawable menuDrawable = this.menuDrawable;
            if (menuDrawable != null) {
                menuDrawable.setRotateToBack(false);
                this.menuDrawable.setRotation(1.0f, true);
            } else {
                BackDrawable backDrawable = this.backDrawable;
                if (backDrawable != null) {
                    backDrawable.setRotation(1.0f, true);
                }
            }
            z = false;
        }
        updateCounters(false);
        this.selectedDialogsCountTextView.setNumber(this.selectedDialogs.size(), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showOrUpdateActionMode$111(float f, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.progressToActionMode = fFloatValue;
        if (this.hasStories) {
            this.viewPages[0].setTranslationY((-f) * fFloatValue);
        }
        for (int i = 0; i < this.actionBar.getChildCount(); i++) {
            if (this.actionBar.getChildAt(i).getVisibility() == 0 && this.actionBar.getChildAt(i) != this.actionBar.getActionMode() && this.actionBar.getChildAt(i) != this.actionBar.getBackButton()) {
                this.actionBar.getChildAt(i).setAlpha(1.0f - this.progressToActionMode);
            }
        }
        View view = this.fragmentView;
        if (view != null) {
            view.invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeSearch() {
        if (AndroidUtilities.isTablet()) {
            ActionBar actionBar = this.actionBar;
            if (actionBar != null) {
                actionBar.closeSearchField();
            }
            TLObject tLObject = this.searchObject;
            if (tLObject != null) {
                SearchViewPager searchViewPager = this.searchViewPager;
                if (searchViewPager != null) {
                    searchViewPager.dialogsSearchAdapter.putRecentSearch(this.searchDialogId, tLObject);
                }
                this.searchObject = null;
                return;
            }
            return;
        }
        this.closeSearchFieldOnHide = true;
    }

    protected RecyclerListView getListView() {
        return this.viewPages[0].listView;
    }

    protected RecyclerListView getSearchListView() {
        createSearchViewPager();
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager != null) {
            return searchViewPager.searchListView;
        }
        return null;
    }

    public void createUndoView() {
        Context context;
        if (this.undoView[0] == null && (context = getContext()) != null) {
            for (int i = 0; i < 2; i++) {
                this.undoView[i] = new C502243(context);
                ContentView contentView = (ContentView) this.fragmentView;
                UndoView undoView = this.undoView[i];
                int i2 = this.undoViewIndex + 1;
                this.undoViewIndex = i2;
                contentView.addView(undoView, i2, LayoutHelper.createFrame(-1, -2.0f, 83, 8.0f, 0.0f, 8.0f, 8.0f));
            }
            checkInsets();
        }
    }

    /* renamed from: org.telegram.ui.DialogsActivity$43 */
    /* loaded from: classes5.dex */
    class C502243 extends UndoView {
        C502243(Context context) {
            super(context);
        }

        @Override // android.view.View
        public void setTranslationY(float f) {
            super.setTranslationY(f);
            if (this == DialogsActivity.this.undoView[0]) {
                if (DialogsActivity.this.undoView[1] == null || DialogsActivity.this.undoView[1].getVisibility() != 0) {
                    DialogsActivity.this.additionalFloatingTranslation = (getMeasuredHeight() + AndroidUtilities.m1146dp(8.0f)) - f;
                    if (DialogsActivity.this.additionalFloatingTranslation < 0.0f) {
                        DialogsActivity.this.additionalFloatingTranslation = 0.0f;
                    }
                    if (DialogsActivity.this.floatingHidden) {
                        return;
                    }
                    DialogsActivity.this.updateFloatingButtonOffset();
                }
            }
        }

        @Override // org.telegram.p023ui.Components.UndoView
        protected boolean canUndo() {
            for (int i = 0; i < DialogsActivity.this.viewPages.length; i++) {
                if (DialogsActivity.this.viewPages[i].dialogsItemAnimator.isRunning()) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.telegram.p023ui.Components.UndoView
        protected void onRemoveDialogAction(long j, int i) {
            if (i == 1 || i == 27) {
                DialogsActivity.this.debugLastUpdateAction = 1;
                DialogsActivity.this.setDialogsListFrozen(true);
                if (DialogsActivity.this.frozenDialogsList != null) {
                    final int i2 = 0;
                    while (true) {
                        if (i2 >= DialogsActivity.this.frozenDialogsList.size()) {
                            i2 = -1;
                            break;
                        } else if (((TLRPC.Dialog) DialogsActivity.this.frozenDialogsList.get(i2)).f1577id == j) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (i2 >= 0) {
                        final TLRPC.Dialog dialog = (TLRPC.Dialog) DialogsActivity.this.frozenDialogsList.remove(i2);
                        DialogsActivity.this.viewPages[0].dialogsAdapter.notifyDataSetChanged();
                        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$43$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$onRemoveDialogAction$0(i2, dialog);
                            }
                        });
                    } else {
                        DialogsActivity.this.setDialogsListFrozen(false);
                    }
                }
                DialogsActivity.this.checkAnimationFinished();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onRemoveDialogAction$0(int i, TLRPC.Dialog dialog) {
            if (DialogsActivity.this.frozenDialogsList == null || i < 0 || i >= DialogsActivity.this.frozenDialogsList.size()) {
                return;
            }
            DialogsActivity.this.frozenDialogsList.add(i, dialog);
            DialogsActivity.this.viewPages[0].updateList(true);
        }
    }

    public UndoView getUndoView() {
        createUndoView();
        UndoView undoView = this.undoView[0];
        if (undoView != null && undoView.getVisibility() == 0) {
            UndoView[] undoViewArr = this.undoView;
            UndoView undoView2 = undoViewArr[0];
            undoViewArr[0] = undoViewArr[1];
            undoViewArr[1] = undoView2;
            undoView2.hide(true, 2);
            ContentView contentView = (ContentView) this.fragmentView;
            contentView.removeView(this.undoView[0]);
            contentView.addView(this.undoView[0]);
        }
        return this.undoView[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateProxyButton(boolean z, boolean z2) {
        boolean z3;
        ActionBarMenuItem actionBarMenuItem;
        if (this.proxyDrawable != null) {
            ActionBarMenuItem actionBarMenuItem2 = this.doneItem;
            if (actionBarMenuItem2 == null || actionBarMenuItem2.getVisibility() != 0) {
                int i = 0;
                while (true) {
                    if (i >= getDownloadController().downloadingFiles.size()) {
                        z3 = false;
                        break;
                    } else {
                        if (getFileLoader().isLoadingFile(getDownloadController().downloadingFiles.get(i).getFileName())) {
                            z3 = true;
                            break;
                        }
                        i++;
                    }
                }
                if (!this.searching && (getDownloadController().hasUnviewedDownloads() || z3 || (this.downloadsItem.getVisibility() == 0 && this.downloadsItem.getAlpha() == 1.0f && !z2))) {
                    this.downloadsItemVisible = true;
                    this.downloadsItem.setVisibility(0);
                } else {
                    this.downloadsItem.setVisibility(8);
                    this.downloadsItemVisible = false;
                }
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                String string = sharedPreferences.getString("proxy_ip", "");
                boolean z4 = sharedPreferences.getBoolean("proxy_enabled", false);
                if ((!this.downloadsItemVisible && !this.searching && z4 && !TextUtils.isEmpty(string)) || (getMessagesController().blockedCountry && !SharedConfig.proxyList.isEmpty())) {
                    if (!this.actionBar.isSearchFieldVisible() && ((actionBarMenuItem = this.doneItem) == null || actionBarMenuItem.getVisibility() != 0)) {
                        this.proxyItem.setVisibility(0);
                    }
                    this.proxyItemVisible = true;
                    ProxyDrawable proxyDrawable = this.proxyDrawable;
                    int i2 = this.currentConnectionState;
                    proxyDrawable.setConnected(z4, i2 == 3 || i2 == 5, z);
                    return;
                }
                this.proxyItemVisible = false;
                this.proxyItem.setVisibility(8);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDoneItem(final boolean z) {
        if (this.doneItem == null) {
            return;
        }
        AnimatorSet animatorSet = this.doneItemAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.doneItemAnimator = null;
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.doneItemAnimator = animatorSet2;
        animatorSet2.setDuration(180L);
        if (z) {
            this.doneItem.setVisibility(0);
        } else {
            this.doneItem.setSelected(false);
            Drawable background = this.doneItem.getBackground();
            if (background != null) {
                background.setState(StateSet.NOTHING);
                background.jumpToCurrentState();
            }
            ActionBarMenuItem actionBarMenuItem = this.searchItem;
            if (actionBarMenuItem != null) {
                actionBarMenuItem.setVisibility(0);
            }
            ActionBarMenuItem actionBarMenuItem2 = this.proxyItem;
            if (actionBarMenuItem2 != null && this.proxyItemVisible) {
                actionBarMenuItem2.setVisibility(0);
            }
            ActionBarMenuItem actionBarMenuItem3 = this.passcodeItem;
            if (actionBarMenuItem3 != null && this.passcodeItemVisible) {
                actionBarMenuItem3.setVisibility(0);
            }
            ActionBarMenuItem actionBarMenuItem4 = this.downloadsItem;
            if (actionBarMenuItem4 != null && this.downloadsItemVisible) {
                actionBarMenuItem4.setVisibility(0);
            }
        }
        ArrayList arrayList = new ArrayList();
        ActionBarMenuItem actionBarMenuItem5 = this.doneItem;
        Property property = View.ALPHA;
        arrayList.add(ObjectAnimator.ofFloat(actionBarMenuItem5, (Property<ActionBarMenuItem, Float>) property, z ? 1.0f : 0.0f));
        if (this.proxyItemVisible) {
            arrayList.add(ObjectAnimator.ofFloat(this.proxyItem, (Property<ActionBarMenuItem, Float>) property, z ? 0.0f : 1.0f));
        }
        if (this.passcodeItemVisible) {
            arrayList.add(ObjectAnimator.ofFloat(this.passcodeItem, (Property<ActionBarMenuItem, Float>) property, z ? 0.0f : 1.0f));
        }
        arrayList.add(ObjectAnimator.ofFloat(this.searchItem, (Property<ActionBarMenuItem, Float>) property, z ? 0.0f : 1.0f));
        this.doneItemAnimator.playTogether(arrayList);
        this.doneItemAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.44
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                DialogsActivity.this.doneItemAnimator = null;
                if (z) {
                    if (DialogsActivity.this.searchItem != null) {
                        DialogsActivity.this.searchItem.setVisibility(4);
                    }
                    if (DialogsActivity.this.proxyItem != null && DialogsActivity.this.proxyItemVisible) {
                        DialogsActivity.this.proxyItem.setVisibility(4);
                    }
                    if (DialogsActivity.this.passcodeItem != null && DialogsActivity.this.passcodeItemVisible) {
                        DialogsActivity.this.passcodeItem.setVisibility(4);
                    }
                    if (DialogsActivity.this.downloadsItem == null || !DialogsActivity.this.downloadsItemVisible) {
                        return;
                    }
                    DialogsActivity.this.downloadsItem.setVisibility(4);
                    return;
                }
                if (DialogsActivity.this.doneItem != null) {
                    DialogsActivity.this.doneItem.setVisibility(8);
                }
            }
        });
        this.doneItemAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSelectedCount() {
        if (this.commentView != null) {
            if (this.selectedDialogs.isEmpty()) {
                if (this.initialDialogsType == 3 && this.selectAlertString == null) {
                    this.actionBar.setTitle(LocaleController.getString(C2369R.string.ForwardTo));
                } else {
                    this.actionBar.setTitle(LocaleController.getString(C2369R.string.SelectChat));
                }
                if (this.commentView.getTag() != null) {
                    this.commentView.hidePopup(false);
                    this.commentView.closeKeyboard();
                    AnimatorSet animatorSet = this.commentViewAnimator;
                    if (animatorSet != null) {
                        animatorSet.cancel();
                    }
                    this.commentViewAnimator = new AnimatorSet();
                    this.commentView.setTranslationY(0.0f);
                    this.commentViewAnimator.playTogether(ObjectAnimator.ofFloat(this.commentView, (Property<ChatActivityEnterView, Float>) View.TRANSLATION_Y, r11.getMeasuredHeight()), ObjectAnimator.ofFloat(this.writeButton, (Property<ChatActivityEnterView.SendButton, Float>) View.SCALE_X, 0.2f), ObjectAnimator.ofFloat(this.writeButton, (Property<ChatActivityEnterView.SendButton, Float>) View.SCALE_Y, 0.2f), ObjectAnimator.ofFloat(this.writeButton, (Property<ChatActivityEnterView.SendButton, Float>) View.ALPHA, 0.0f));
                    this.commentViewAnimator.setDuration(180L);
                    this.commentViewAnimator.setInterpolator(new DecelerateInterpolator());
                    this.commentViewAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.45
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            DialogsActivity.this.commentView.setVisibility(8);
                            DialogsActivity.this.writeButton.setVisibility(8);
                        }
                    });
                    this.commentViewAnimator.start();
                    this.commentView.setTag(null);
                    this.fragmentView.requestLayout();
                }
            } else {
                if (this.commentView.getTag() == null) {
                    this.commentView.setFieldText("");
                    AnimatorSet animatorSet2 = this.commentViewAnimator;
                    if (animatorSet2 != null) {
                        animatorSet2.cancel();
                    }
                    this.commentView.setVisibility(0);
                    this.writeButton.setVisibility(0);
                    AnimatorSet animatorSet3 = new AnimatorSet();
                    this.commentViewAnimator = animatorSet3;
                    animatorSet3.playTogether(ObjectAnimator.ofFloat(this.commentView, (Property<ChatActivityEnterView, Float>) View.TRANSLATION_Y, r6.getMeasuredHeight(), 0.0f), ObjectAnimator.ofFloat(this.writeButton, (Property<ChatActivityEnterView.SendButton, Float>) View.SCALE_X, 1.0f), ObjectAnimator.ofFloat(this.writeButton, (Property<ChatActivityEnterView.SendButton, Float>) View.SCALE_Y, 1.0f), ObjectAnimator.ofFloat(this.writeButton, (Property<ChatActivityEnterView.SendButton, Float>) View.ALPHA, 1.0f));
                    this.commentViewAnimator.setDuration(180L);
                    this.commentViewAnimator.setInterpolator(new DecelerateInterpolator());
                    this.commentViewAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.46
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            DialogsActivity.this.commentView.setTag(2);
                            DialogsActivity.this.commentView.requestLayout();
                        }
                    });
                    this.commentViewAnimator.start();
                    this.commentView.setTag(1);
                }
                this.writeButton.setCount(Math.max(1, this.selectedDialogs.size()), true);
                int i = this.messagesCount + (!TextUtils.isEmpty(this.commentView.getFieldText()) ? 1 : 0);
                ArrayList arrayList = this.selectedDialogs;
                int size = arrayList.size();
                long j = 0;
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    long jLongValue = ((Long) obj).longValue();
                    long sendPaidMessagesStars = getMessagesController().getSendPaidMessagesStars(jLongValue);
                    if (sendPaidMessagesStars <= 0 && jLongValue > 0) {
                        sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(getMessagesController().isUserContactBlocked(jLongValue));
                    }
                    j += sendPaidMessagesStars;
                }
                this.writeButton.setStarsPrice(j, i);
                this.commentView.updateSendButtonPaid();
                this.actionBar.setTitle(LocaleController.formatPluralString("Recipient", this.selectedDialogs.size(), new Object[0]));
            }
        } else if (this.initialDialogsType == 10) {
            hideFloatingButton(this.selectedDialogs.isEmpty());
        }
        ArrayList arrayList2 = this.selectedDialogs;
        ChatActivityEnterView chatActivityEnterView = this.commentView;
        boolean zShouldShowNextButton = shouldShowNextButton(this, arrayList2, chatActivityEnterView != null ? chatActivityEnterView.getFieldText() : "", false);
        this.isNextButton = zShouldShowNextButton;
        this.writeButton.setResourceId(zShouldShowNextButton ? C2369R.drawable.msg_arrow_forward : C2369R.drawable.attach_send);
    }

    private void askForPermissons(boolean z) {
        final Activity parentActivity = getParentActivity();
        if (parentActivity == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (this.folderId == 0 && Build.VERSION.SDK_INT >= 33 && NotificationPermissionDialog.shouldAsk(parentActivity)) {
            if (z) {
                showDialog(new NotificationPermissionDialog(parentActivity, !PermissionRequest.canAskPermission("android.permission.POST_NOTIFICATIONS"), new Utilities.Callback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda25
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        DialogsActivity.$r8$lambda$kZ_BWIoWW0iAoUMTdkebMp2tgpk(parentActivity, (Boolean) obj);
                    }
                }));
                return;
            }
            arrayList.add("android.permission.POST_NOTIFICATIONS");
        }
        if (getUserConfig().syncContacts && this.askAboutContacts && parentActivity.checkSelfPermission("android.permission.READ_CONTACTS") != 0) {
            if (z) {
                AlertDialog alertDialogCreate = AlertsCreator.createContactsPermissionDialog(parentActivity, new MessagesStorage.IntCallback() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda26
                    @Override // org.telegram.messenger.MessagesStorage.IntCallback
                    public final void run(int i) {
                        this.f$0.lambda$askForPermissons$113(i);
                    }
                }).create();
                this.permissionDialog = alertDialogCreate;
                showDialog(alertDialogCreate);
                return;
            } else {
                arrayList.add("android.permission.READ_CONTACTS");
                arrayList.add("android.permission.WRITE_CONTACTS");
                arrayList.add("android.permission.GET_ACCOUNTS");
            }
        }
        int i = Build.VERSION.SDK_INT;
        if (i >= 33) {
            if (parentActivity.checkSelfPermission("android.permission.READ_MEDIA_IMAGES") != 0) {
                arrayList.add("android.permission.READ_MEDIA_IMAGES");
            }
            if (parentActivity.checkSelfPermission("android.permission.READ_MEDIA_VIDEO") != 0) {
                arrayList.add("android.permission.READ_MEDIA_VIDEO");
            }
            if (parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
            }
        } else if ((i <= 28 || BuildVars.NO_SCOPED_STORAGE) && parentActivity.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
            arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
        }
        if (arrayList.isEmpty()) {
            if (this.askingForPermissions) {
                this.askingForPermissions = false;
                showFiltersHint();
                return;
            }
            return;
        }
        try {
            parentActivity.requestPermissions((String[]) arrayList.toArray(new String[0]), 1);
        } catch (Exception unused) {
        }
    }

    public static /* synthetic */ void $r8$lambda$kZ_BWIoWW0iAoUMTdkebMp2tgpk(Activity activity, Boolean bool) {
        if (bool.booleanValue()) {
            if (!PermissionRequest.canAskPermission("android.permission.POST_NOTIFICATIONS")) {
                PermissionRequest.showPermissionSettings("android.permission.POST_NOTIFICATIONS");
            } else {
                activity.requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, 1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$askForPermissons$113(int i) {
        this.askAboutContacts = i != 0;
        MessagesController.getGlobalNotificationsSettings().edit().putBoolean("askAboutContacts", this.askAboutContacts).apply();
        askForPermissons(false);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    protected void onDialogDismiss(Dialog dialog) {
        AlertDialog alertDialog;
        super.onDialogDismiss(dialog);
        if (this.folderId != 0 || (alertDialog = this.permissionDialog) == null || dialog != alertDialog || getParentActivity() == null) {
            return;
        }
        askForPermissons(false);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onConfigurationChanged(Configuration configuration) {
        FrameLayout frameLayout;
        super.onConfigurationChanged(configuration);
        ItemOptions itemOptions = this.filterOptions;
        if (itemOptions != null) {
            itemOptions.dismiss();
        }
        if (this.onlySelect || (frameLayout = this.floatingButtonContainer) == null) {
            return;
        }
        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: org.telegram.ui.DialogsActivity.47
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public void onGlobalLayout() {
                DialogsActivity dialogsActivity = DialogsActivity.this;
                dialogsActivity.floatingButtonTranslation = dialogsActivity.floatingHidden ? AndroidUtilities.m1146dp(100.0f) + DialogsActivity.this.windowInsetsStateHolder.getCurrentNavigationBarInset() : 0.0f;
                DialogsActivity.this.updateFloatingButtonOffset();
                DialogsActivity.this.floatingButtonContainer.setClickable(!DialogsActivity.this.floatingHidden);
                if (DialogsActivity.this.floatingButtonContainer != null) {
                    DialogsActivity.this.floatingButtonContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        FilesMigrationService.FilesMigrationBottomSheet filesMigrationBottomSheet;
        if (i != 1) {
            if (i == 4) {
                for (int i2 : iArr) {
                    if (i2 != 0) {
                        return;
                    }
                }
                if (Build.VERSION.SDK_INT < 30 || (filesMigrationBottomSheet = FilesMigrationService.filesMigrationBottomSheet) == null) {
                    return;
                }
                filesMigrationBottomSheet.migrateOldFolder();
                return;
            }
            return;
        }
        for (int i3 = 0; i3 < strArr.length; i3++) {
            if (iArr.length > i3) {
                String str = strArr[i3];
                str.getClass();
                switch (str) {
                    case "android.permission.POST_NOTIFICATIONS":
                        if (iArr[i3] == 0) {
                            NotificationsController.getInstance(this.currentAccount).showNotifications();
                            break;
                        } else {
                            NotificationPermissionDialog.askLater();
                            break;
                        }
                    case "android.permission.WRITE_EXTERNAL_STORAGE":
                        if (iArr[i3] == 0) {
                            ImageLoader.getInstance().checkMediaPaths();
                            break;
                        } else {
                            break;
                        }
                    case "android.permission.READ_CONTACTS":
                        if (iArr[i3] == 0) {
                            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda27
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$onRequestPermissionsResultFragment$114();
                                }
                            });
                            getContactsController().forceImportContacts();
                            break;
                        } else {
                            SharedPreferences.Editor editorEdit = MessagesController.getGlobalNotificationsSettings().edit();
                            this.askAboutContacts = false;
                            editorEdit.putBoolean("askAboutContacts", false).apply();
                            break;
                        }
                }
            }
        }
        if (this.askingForPermissions) {
            this.askingForPermissions = false;
            showFiltersHint();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onRequestPermissionsResultFragment$114() {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.forceImportContactsStart, new Object[0]);
    }

    private void reloadViewPageDialogs(ViewPage viewPage, boolean z) {
        int i;
        int i2;
        if (viewPage.getVisibility() != 0) {
            return;
        }
        int currentCount = viewPage.dialogsAdapter.getCurrentCount();
        if (viewPage.dialogsType == 0 && hasHiddenArchive() && viewPage.listView.getChildCount() == 0 && viewPage.archivePullViewState == 2) {
            ((LinearLayoutManager) viewPage.listView.getLayoutManager()).scrollToPositionWithOffset(1, (int) this.scrollYOffset);
        }
        if (viewPage.dialogsAdapter.isDataSetChanged() || z) {
            viewPage.dialogsAdapter.updateHasHints();
            int itemCount = viewPage.dialogsAdapter.getItemCount();
            if (itemCount == 1 && currentCount == 1 && viewPage.dialogsAdapter.getItemViewType(0) == 5) {
                viewPage.updateList(true);
            } else {
                viewPage.updateList(false);
                if (itemCount > currentCount && (i = this.initialDialogsType) != 11 && i != 12 && i != 13) {
                    viewPage.recyclerItemsEnterAnimator.showItemsAnimated(currentCount);
                }
            }
        } else {
            updateVisibleRows(MessagesController.UPDATE_MASK_NEW_MESSAGE);
            if (viewPage.dialogsAdapter.getItemCount() > currentCount && (i2 = this.initialDialogsType) != 11 && i2 != 12 && i2 != 13) {
                viewPage.recyclerItemsEnterAnimator.showItemsAnimated(currentCount);
            }
        }
        try {
            viewPage.listView.setEmptyView(this.folderId == 0 ? viewPage.progressView : null);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        checkListLoad(viewPage);
    }

    public void setPanTranslationOffset(float f) {
        this.floatingButtonPanOffset = f;
        updateFloatingButtonOffset();
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, final Object... objArr) {
        MessagesController.DialogFilter dialogFilter;
        final boolean zBooleanValue;
        final boolean zBooleanValue2;
        DialogsSearchAdapter dialogsSearchAdapter;
        DialogsSearchAdapter dialogsSearchAdapter2;
        MessagesController.DialogFilter dialogFilter2;
        char c = 1;
        int i3 = 0;
        if (i == NotificationCenter.dialogsNeedReload) {
            ViewPage[] viewPageArr = this.viewPages;
            if (viewPageArr == null || this.dialogsListFrozen) {
                return;
            }
            for (final ViewPage viewPage : viewPageArr) {
                if (this.viewPages[0].dialogsType == 7 || this.viewPages[0].dialogsType == 8) {
                    dialogFilter2 = getMessagesController().selectedDialogFilter[this.viewPages[0].dialogsType == 8 ? (char) 1 : (char) 0];
                } else {
                    dialogFilter2 = null;
                }
                boolean z = (dialogFilter2 == null || (dialogFilter2.flags & MessagesController.DIALOG_FILTER_FLAG_EXCLUDE_READ) == 0) ? false : true;
                if (this.slowedReloadAfterDialogClick && z) {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda29
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$didReceivedNotification$115(viewPage, objArr);
                        }
                    }, 160L);
                } else {
                    reloadViewPageDialogs(viewPage, objArr.length > 0);
                }
            }
            FilterTabsView filterTabsView = this.filterTabsView;
            if (filterTabsView != null && filterTabsView.getVisibility() == 0) {
                this.filterTabsView.checkTabsCounter();
            }
            this.slowedReloadAfterDialogClick = false;
            return;
        }
        if (i == NotificationCenter.topicsDidLoaded) {
            updateVisibleRows(0);
            return;
        }
        if (i == NotificationCenter.dialogsUnreadCounterChanged) {
            FilterTabsView filterTabsView2 = this.filterTabsView;
            if (filterTabsView2 == null || filterTabsView2.getVisibility() != 0) {
                return;
            }
            FilterTabsView filterTabsView3 = this.filterTabsView;
            filterTabsView3.notifyTabCounterChanged(filterTabsView3.getDefaultTabId());
            return;
        }
        if (i == NotificationCenter.dialogsUnreadReactionsCounterChanged) {
            updateVisibleRows(0);
            return;
        }
        if (i == NotificationCenter.emojiLoaded) {
            if (this.viewPages != null) {
                int i4 = 0;
                while (true) {
                    ViewPage[] viewPageArr2 = this.viewPages;
                    if (i4 >= viewPageArr2.length) {
                        break;
                    }
                    DialogsRecyclerView dialogsRecyclerView = viewPageArr2[i4].listView;
                    if (dialogsRecyclerView != null) {
                        for (int i5 = 0; i5 < dialogsRecyclerView.getChildCount(); i5++) {
                            View childAt = dialogsRecyclerView.getChildAt(i5);
                            if (childAt != null) {
                                childAt.invalidate();
                            }
                        }
                    }
                    i4++;
                }
            }
            FilterTabsView filterTabsView4 = this.filterTabsView;
            if (filterTabsView4 != null) {
                filterTabsView4.getTabsContainer().invalidateViews();
                return;
            }
            return;
        }
        if (i == NotificationCenter.closeSearchByActiveAction) {
            ActionBar actionBar = this.actionBar;
            if (actionBar != null) {
                actionBar.closeSearchField();
                return;
            }
            return;
        }
        if (i == NotificationCenter.proxySettingsChanged) {
            updateProxyButton(false, false);
            return;
        }
        if (i == NotificationCenter.updateInterfaces) {
            Integer num = (Integer) objArr[0];
            updateVisibleRows(num.intValue());
            FilterTabsView filterTabsView5 = this.filterTabsView;
            if (filterTabsView5 != null && filterTabsView5.getVisibility() == 0 && (num.intValue() & MessagesController.UPDATE_MASK_READ_DIALOG_MESSAGE) != 0) {
                this.filterTabsView.checkTabsCounter();
            }
            ViewPage[] viewPageArr3 = this.viewPages;
            if (viewPageArr3 != null) {
                int length = viewPageArr3.length;
                while (i3 < length) {
                    ViewPage viewPage2 = viewPageArr3[i3];
                    if ((num.intValue() & MessagesController.UPDATE_MASK_STATUS) != 0) {
                        viewPage2.dialogsAdapter.sortOnlineContacts(true);
                    }
                    i3++;
                }
            }
            updateStatus(UserConfig.getInstance(i2).getCurrentUser(), true);
            return;
        }
        if (i == NotificationCenter.appDidLogout) {
            dialogsLoaded[this.currentAccount] = false;
            return;
        }
        if (i == NotificationCenter.encryptedChatUpdated) {
            updateVisibleRows(0);
            return;
        }
        if (i == NotificationCenter.contactsDidLoad) {
            if (this.viewPages == null || this.dialogsListFrozen) {
                return;
            }
            boolean z2 = this.floatingProgressVisible;
            setFloatingProgressVisible(false, true);
            for (ViewPage viewPage3 : this.viewPages) {
                viewPage3.dialogsAdapter.setForceUpdatingContacts(false);
            }
            if (z2) {
                setContactsAlpha(0.0f);
                animateContactsAlpha(1.0f);
            }
            boolean z3 = false;
            for (ViewPage viewPage4 : this.viewPages) {
                if (!viewPage4.isDefaultDialogType() || getMessagesController().getAllFoldersDialogsCount() > 10) {
                    z3 = true;
                } else {
                    viewPage4.dialogsAdapter.notifyDataSetChanged();
                }
            }
            if (z3) {
                updateVisibleRows(0);
                return;
            }
            return;
        }
        char c2 = 2;
        if (i == NotificationCenter.openedChatChanged) {
            ViewPage[] viewPageArr4 = this.viewPages;
            if (viewPageArr4 == null) {
                return;
            }
            int length2 = viewPageArr4.length;
            int i6 = 0;
            while (i6 < length2) {
                ViewPage viewPage5 = viewPageArr4[i6];
                if (viewPage5.isDefaultDialogType() && AndroidUtilities.isTablet()) {
                    boolean zBooleanValue3 = ((Boolean) objArr[c2]).booleanValue();
                    long jLongValue = ((Long) objArr[i3]).longValue();
                    long jLongValue2 = ((Long) objArr[c]).longValue();
                    if (zBooleanValue3) {
                        MessagesStorage.TopicKey topicKey = this.openedDialogId;
                        if (jLongValue == topicKey.dialogId && jLongValue2 == topicKey.topicId) {
                            topicKey.dialogId = 0L;
                            topicKey.topicId = 0L;
                        }
                    } else {
                        MessagesStorage.TopicKey topicKey2 = this.openedDialogId;
                        topicKey2.dialogId = jLongValue;
                        topicKey2.topicId = jLongValue2;
                    }
                    viewPage5.dialogsAdapter.setOpenedDialogId(this.openedDialogId.dialogId);
                }
                i6++;
                c = 1;
                i3 = 0;
                c2 = 2;
            }
            updateVisibleRows(MessagesController.UPDATE_MASK_SELECT_DIALOG);
            return;
        }
        if (i == NotificationCenter.notificationsSettingsUpdated) {
            updateVisibleRows(0);
            return;
        }
        if (i == NotificationCenter.messageReceivedByAck || i == NotificationCenter.messageReceivedByServer || i == NotificationCenter.messageSendError) {
            updateVisibleRows(MessagesController.UPDATE_MASK_SEND_STATE);
            return;
        }
        if (i == NotificationCenter.didSetPasscode) {
            updatePasscodeButton();
            return;
        }
        if (i == NotificationCenter.needReloadRecentDialogsSearch) {
            SearchViewPager searchViewPager = this.searchViewPager;
            if (searchViewPager == null || (dialogsSearchAdapter2 = searchViewPager.dialogsSearchAdapter) == null) {
                return;
            }
            dialogsSearchAdapter2.loadRecentSearch();
            return;
        }
        if (i == NotificationCenter.replyMessagesDidLoad) {
            updateVisibleRows(MessagesController.UPDATE_MASK_MESSAGE_TEXT);
            return;
        }
        if (i == NotificationCenter.reloadHints) {
            SearchViewPager searchViewPager2 = this.searchViewPager;
            if (searchViewPager2 == null || (dialogsSearchAdapter = searchViewPager2.dialogsSearchAdapter) == null) {
                return;
            }
            dialogsSearchAdapter.notifyDataSetChanged();
            return;
        }
        if (i == NotificationCenter.didUpdateConnectionState) {
            int connectionState = AccountInstance.getInstance(i2).getConnectionsManager().getConnectionState();
            if (this.currentConnectionState != connectionState) {
                this.currentConnectionState = connectionState;
                updateProxyButton(true, false);
                return;
            }
            return;
        }
        if (i == NotificationCenter.onDownloadingFilesChanged) {
            updateProxyButton(true, false);
            SearchViewPager searchViewPager3 = this.searchViewPager;
            if (searchViewPager3 != null) {
                updateSpeedItem(searchViewPager3.getCurrentPosition() == 2);
                return;
            }
            return;
        }
        if (i == NotificationCenter.needDeleteDialog) {
            if (this.fragmentView == null || this.isPaused) {
                return;
            }
            final long jLongValue3 = ((Long) objArr[0]).longValue();
            final TLRPC.User user = (TLRPC.User) objArr[1];
            final TLRPC.Chat chat = (TLRPC.Chat) objArr[2];
            if (user != null && user.bot) {
                zBooleanValue2 = ((Boolean) objArr[3]).booleanValue();
                zBooleanValue = false;
            } else {
                zBooleanValue = ((Boolean) objArr[3]).booleanValue();
                zBooleanValue2 = false;
            }
            Runnable runnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didReceivedNotification$116(chat, jLongValue3, zBooleanValue, user, zBooleanValue2);
                }
            };
            createUndoView();
            if (this.undoView[0] != null) {
                if (!ChatObject.isForum(chat)) {
                    UndoView undoView = getUndoView();
                    if (undoView != null) {
                        undoView.showWithAction(jLongValue3, 1, runnable);
                        return;
                    }
                    return;
                }
                runnable.run();
                return;
            }
            runnable.run();
            return;
        }
        if (i == NotificationCenter.folderBecomeEmpty) {
            int iIntValue = ((Integer) objArr[0]).intValue();
            int i7 = this.folderId;
            if (i7 != iIntValue || i7 == 0) {
                return;
            }
            lambda$onBackPressed$371();
            return;
        }
        if (i == NotificationCenter.dialogFiltersUpdated) {
            updateFilterTabs(true, true);
            return;
        }
        if (i == NotificationCenter.filterSettingsUpdated) {
            showFiltersHint();
            return;
        }
        if (i == NotificationCenter.newSuggestionsAvailable) {
            showNextSupportedSuggestion();
            updateDialogsHint();
            checkEmailConfig();
            return;
        }
        if (i == NotificationCenter.forceImportContactsStart) {
            boolean z4 = true;
            setFloatingProgressVisible(true, true);
            ViewPage[] viewPageArr5 = this.viewPages;
            if (viewPageArr5 != null) {
                int length3 = viewPageArr5.length;
                int i8 = 0;
                while (i8 < length3) {
                    ViewPage viewPage6 = viewPageArr5[i8];
                    viewPage6.dialogsAdapter.setForceShowEmptyCell(false);
                    viewPage6.dialogsAdapter.setForceUpdatingContacts(z4);
                    viewPage6.dialogsAdapter.notifyDataSetChanged();
                    i8++;
                    z4 = true;
                }
                return;
            }
            return;
        }
        if (i == NotificationCenter.messagesDeleted) {
            if (!this.searchIsShowed || this.searchViewPager == null) {
                return;
            }
            this.searchViewPager.messagesDeleted(((Long) objArr[1]).longValue(), (ArrayList) objArr[0]);
            return;
        }
        if (i == NotificationCenter.didClearDatabase) {
            ViewPage[] viewPageArr6 = this.viewPages;
            if (viewPageArr6 != null) {
                for (ViewPage viewPage7 : viewPageArr6) {
                    viewPage7.dialogsAdapter.didDatabaseCleared();
                }
            }
            SuggestClearDatabaseBottomSheet.dismissDialog();
            return;
        }
        if (i == NotificationCenter.appUpdateAvailable || i == NotificationCenter.appUpdateLoading) {
            updateMenuButton(true);
            return;
        }
        if (i == NotificationCenter.fileLoaded || i == NotificationCenter.fileLoadFailed || i == NotificationCenter.fileLoadProgressChanged) {
            String str = (String) objArr[0];
            if (SharedConfig.isAppUpdateAvailable() && FileLoader.getAttachFileName(SharedConfig.pendingAppUpdate.document).equals(str)) {
                updateMenuButton(true);
                return;
            }
            return;
        }
        if (i == NotificationCenter.onDatabaseMigration) {
            boolean zBooleanValue4 = ((Boolean) objArr[0]).booleanValue();
            if (this.fragmentView != null) {
                if (zBooleanValue4) {
                    if (this.databaseMigrationHint == null) {
                        DatabaseMigrationHint databaseMigrationHint = new DatabaseMigrationHint(this.fragmentView.getContext(), this.currentAccount);
                        this.databaseMigrationHint = databaseMigrationHint;
                        databaseMigrationHint.setAlpha(0.0f);
                        ((ContentView) this.fragmentView).addView(this.databaseMigrationHint);
                        this.databaseMigrationHint.animate().alpha(1.0f).setDuration(300L).setStartDelay(1000L).start();
                    }
                    this.databaseMigrationHint.setTag(1);
                    return;
                }
                View view = this.databaseMigrationHint;
                if (view == null || view.getTag() == null) {
                    return;
                }
                final View view2 = this.databaseMigrationHint;
                view2.animate().setListener(null).cancel();
                view2.animate().setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.48
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (view2.getParent() != null) {
                            ((ViewGroup) view2.getParent()).removeView(view2);
                        }
                        DialogsActivity.this.databaseMigrationHint = null;
                    }
                }).alpha(0.0f).setStartDelay(0L).setDuration(150L).start();
                this.databaseMigrationHint.setTag(null);
                return;
            }
            return;
        }
        if (i == NotificationCenter.onDatabaseOpened) {
            checkSuggestClearDatabase();
            return;
        }
        if (i == NotificationCenter.userEmojiStatusUpdated) {
            updateStatus((TLRPC.User) objArr[0], true);
            return;
        }
        if (i == NotificationCenter.currentUserPremiumStatusChanged) {
            ActionBar actionBar2 = this.actionBar;
            String actionBarTitle = LocaleUtils.getActionBarTitle();
            this.actionBarDefaultTitle = actionBarTitle;
            actionBar2.setTitle(actionBarTitle, getStatusDrawable());
            updateStatus(UserConfig.getInstance(i2).getCurrentUser(), true);
            updateStoriesPosting();
            return;
        }
        if (i == NotificationCenter.onDatabaseReset) {
            dialogsLoaded[this.currentAccount] = false;
            loadDialogs(getAccountInstance());
            getMessagesController().loadPinnedDialogs(this.folderId, 0L, null);
            return;
        }
        if (i == NotificationCenter.chatlistFolderUpdate) {
            int iIntValue2 = ((Integer) objArr[0]).intValue();
            if (this.viewPages == null) {
                return;
            }
            int i9 = 0;
            while (true) {
                ViewPage[] viewPageArr7 = this.viewPages;
                if (i9 >= viewPageArr7.length) {
                    return;
                }
                ViewPage viewPage8 = viewPageArr7[i9];
                if (viewPage8 != null && ((viewPage8.dialogsType == 7 || viewPage8.dialogsType == 8) && (dialogFilter = getMessagesController().selectedDialogFilter[viewPage8.dialogsType - 7]) != null && iIntValue2 == dialogFilter.f1454id)) {
                    viewPage8.updateList(true);
                    return;
                }
                i9++;
            }
        } else if (i == NotificationCenter.dialogTranslate) {
            long jLongValue4 = ((Long) objArr[0]).longValue();
            if (this.viewPages == null) {
                return;
            }
            int i10 = 0;
            while (true) {
                ViewPage[] viewPageArr8 = this.viewPages;
                if (i10 >= viewPageArr8.length) {
                    return;
                }
                ViewPage viewPage9 = viewPageArr8[i10];
                if (viewPage9.listView != null) {
                    int i11 = 0;
                    while (true) {
                        if (i11 < viewPage9.listView.getChildCount()) {
                            View childAt2 = viewPage9.listView.getChildAt(i11);
                            if (childAt2 instanceof DialogCell) {
                                DialogCell dialogCell = (DialogCell) childAt2;
                                if (jLongValue4 == dialogCell.getDialogId()) {
                                    dialogCell.buildLayout();
                                    break;
                                }
                            }
                            i11++;
                        }
                    }
                }
                i10++;
            }
        } else {
            if (i == NotificationCenter.storiesUpdated) {
                updateStoriesVisibility(this.wasDrawn);
                updateVisibleRows(0);
                return;
            }
            if (i == NotificationCenter.storiesEnabledUpdate) {
                updateStoriesPosting();
                return;
            }
            if (i == NotificationCenter.unconfirmedAuthUpdate) {
                updateDialogsHint();
                return;
            }
            if (i == NotificationCenter.premiumPromoUpdated) {
                updateDialogsHint();
                return;
            }
            if (i == NotificationCenter.starBalanceUpdated || i == NotificationCenter.starSubscriptionsLoaded) {
                updateDialogsHint();
            } else if (i == NotificationCenter.appConfigUpdated) {
                updateDialogsHint();
            } else if (i == NotificationCenter.activeAuctionsUpdated) {
                updateDialogsHint();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$115(ViewPage viewPage, Object[] objArr) {
        reloadViewPageDialogs(viewPage, objArr.length > 0);
        FilterTabsView filterTabsView = this.filterTabsView;
        if (filterTabsView == null || filterTabsView.getVisibility() != 0) {
            return;
        }
        this.filterTabsView.checkTabsCounter();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didReceivedNotification$116(TLRPC.Chat chat, long j, boolean z, TLRPC.User user, boolean z2) {
        if (chat != null) {
            if (ChatObject.isNotInChat(chat)) {
                getMessagesController().deleteDialog(j, 0, z);
            } else {
                getMessagesController().deleteParticipantFromChat(-j, getMessagesController().getUser(Long.valueOf(getUserConfig().getClientUserId())), (TLRPC.Chat) null, z, z);
            }
        } else {
            getMessagesController().deleteDialog(j, 0, z);
            if (user != null && user.bot && z2) {
                getMessagesController().blockPeer(user.f1734id);
            }
        }
        getMessagesController().checkIfFolderEmpty(this.folderId);
    }

    private void checkSuggestClearDatabase() {
        if (getMessagesStorage().showClearDatabaseAlert) {
            getMessagesStorage().showClearDatabaseAlert = false;
            SuggestClearDatabaseBottomSheet.show(this);
        }
    }

    private void updateMenuButton(boolean z) {
        int i;
        if (this.menuDrawable == null) {
            return;
        }
        if (ApplicationLoader.applicationLoaderInstance.isCustomUpdate()) {
            if (ApplicationLoader.applicationLoaderInstance.getUpdate() != null) {
                if (ApplicationLoader.applicationLoaderInstance.isDownloadingUpdate()) {
                    i = MenuDrawable.TYPE_UDPATE_DOWNLOADING;
                    fFloatValue = ApplicationLoader.applicationLoaderInstance.getDownloadingUpdateProgress();
                } else {
                    i = MenuDrawable.TYPE_UDPATE_AVAILABLE;
                }
            } else {
                i = MenuDrawable.TYPE_DEFAULT;
            }
        } else if (SharedConfig.isAppUpdateAvailable()) {
            String attachFileName = FileLoader.getAttachFileName(SharedConfig.pendingAppUpdate.document);
            if (getFileLoader().isLoadingFile(attachFileName)) {
                int i2 = MenuDrawable.TYPE_UDPATE_DOWNLOADING;
                Float fileProgress = ImageLoader.getInstance().getFileProgress(attachFileName);
                fFloatValue = fileProgress != null ? fileProgress.floatValue() : 0.0f;
                i = i2;
            } else {
                i = MenuDrawable.TYPE_UDPATE_AVAILABLE;
            }
        } else {
            i = MenuDrawable.TYPE_DEFAULT;
        }
        updateAppUpdateViews(z);
        this.menuDrawable.setType(i, z);
        this.menuDrawable.setUpdateDownloadProgress(fFloatValue, z);
    }

    private void showNextSupportedSuggestion() {
        if (this.showingSuggestion != null) {
            return;
        }
        for (String str : getMessagesController().pendingSuggestions) {
            if (showSuggestion(str)) {
                this.showingSuggestion = str;
                return;
            }
        }
    }

    private void onSuggestionDismiss() {
        if (this.showingSuggestion == null) {
            return;
        }
        getMessagesController().removeSuggestion(0L, this.showingSuggestion);
        this.showingSuggestion = null;
        showNextSupportedSuggestion();
    }

    private boolean showSuggestion(String str) {
        if (!"AUTOARCHIVE_POPULAR".equals(str)) {
            return false;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setTitle(LocaleController.getString(C2369R.string.HideNewChatsAlertTitle));
        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.HideNewChatsAlertText)));
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.GoToSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda81
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$showSuggestion$117(alertDialog, i);
            }
        });
        showDialog(builder.create(), new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda82
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$showSuggestion$118(dialogInterface);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSuggestion$117(AlertDialog alertDialog, int i) {
        presentFragment(new PrivacySettingsActivity());
        AndroidUtilities.scrollToFragmentRow(this.parentLayout, "newChatsRow");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showSuggestion$118(DialogInterface dialogInterface) {
        onSuggestionDismiss();
    }

    private void showFiltersHint() {
        if (this.askingForPermissions || !getMessagesController().dialogFiltersLoaded || !getMessagesController().showFiltersTooltip || this.filterTabsView == null || !getMessagesController().getDialogFilters().isEmpty() || this.isPaused || !getUserConfig().filtersLoaded || this.inPreviewMode) {
            return;
        }
        SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
        if (globalMainSettings.getBoolean("filterhint", false)) {
            return;
        }
        globalMainSettings.edit().putBoolean("filterhint", true).apply();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda60
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showFiltersHint$120();
            }
        }, 1000L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFiltersHint$120() {
        UndoView undoView = getUndoView();
        if (undoView != null) {
            undoView.showWithAction(0L, 15, null, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda109
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$showFiltersHint$119();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showFiltersHint$119() {
        presentFragment(new FiltersSetupActivity());
    }

    private void setDialogsListFrozen(boolean z, boolean z2) {
        if (this.viewPages == null || this.dialogsListFrozen == z) {
            return;
        }
        if (z) {
            this.frozenDialogsList = new ArrayList(getDialogsArray(this.currentAccount, this.viewPages[0].dialogsType, this.folderId, false));
        } else {
            this.frozenDialogsList = null;
        }
        this.dialogsListFrozen = z;
        this.viewPages[0].dialogsAdapter.setDialogsListFrozen(z);
        if (z || !z2) {
            return;
        }
        if (this.viewPages[0].listView.isComputingLayout()) {
            this.viewPages[0].listView.post(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda58
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setDialogsListFrozen$121();
                }
            });
        } else {
            this.viewPages[0].dialogsAdapter.notifyDataSetChanged();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setDialogsListFrozen$121() {
        this.viewPages[0].dialogsAdapter.notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDialogsListFrozen(boolean z) {
        setDialogsListFrozen(z, true);
    }

    /* loaded from: classes5.dex */
    public class DialogsHeader extends TLRPC.Dialog {
        public int headerType;

        public DialogsHeader(int i) {
            this.headerType = i;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:346:0x02b7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:353:0x0277 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.ArrayList getDialogsArray(int r8, int r9, int r10, boolean r11) {
        /*
            Method dump skipped, instructions count: 956
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.getDialogsArray(int, int, int, boolean):java.util.ArrayList");
    }

    private boolean meetRequestPeerRequirements(TLRPC.User user) {
        TLRPC.TL_requestPeerTypeUser tL_requestPeerTypeUser = (TLRPC.TL_requestPeerTypeUser) this.requestPeerType;
        if (user == null || UserObject.isReplyUser(user) || UserObject.isDeleted(user)) {
            return false;
        }
        Boolean bool = tL_requestPeerTypeUser.bot;
        if (bool != null && bool.booleanValue() != user.bot) {
            return false;
        }
        Boolean bool2 = tL_requestPeerTypeUser.premium;
        return bool2 == null || bool2.booleanValue() == user.premium;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean meetRequestPeerRequirements(org.telegram.tgnet.TLRPC.User r6, org.telegram.tgnet.TLRPC.Chat r7) {
        /*
            r5 = this;
            r0 = 0
            if (r7 == 0) goto L94
            boolean r1 = org.telegram.messenger.ChatObject.isChannelAndNotMegaGroup(r7)
            org.telegram.tgnet.TLRPC$RequestPeerType r2 = r5.requestPeerType
            boolean r3 = r2 instanceof org.telegram.tgnet.TLRPC.TL_requestPeerTypeBroadcast
            if (r1 != r3) goto L94
            java.lang.Boolean r1 = r2.creator
            if (r1 == 0) goto L1b
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L1b
            boolean r1 = r7.creator
            if (r1 == 0) goto L94
        L1b:
            org.telegram.tgnet.TLRPC$RequestPeerType r1 = r5.requestPeerType
            java.lang.Boolean r1 = r1.bot_participant
            if (r1 == 0) goto L37
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L37
            org.telegram.messenger.MessagesController r1 = r5.getMessagesController()
            boolean r1 = r1.isInChatCached(r7, r6)
            if (r1 != 0) goto L37
            boolean r1 = org.telegram.messenger.ChatObject.canAddBotsToChat(r7)
            if (r1 == 0) goto L94
        L37:
            org.telegram.tgnet.TLRPC$RequestPeerType r1 = r5.requestPeerType
            java.lang.Boolean r1 = r1.has_username
            r2 = 1
            if (r1 == 0) goto L4d
            boolean r1 = r1.booleanValue()
            java.lang.String r3 = org.telegram.messenger.ChatObject.getPublicUsername(r7)
            if (r3 == 0) goto L4a
            r3 = 1
            goto L4b
        L4a:
            r3 = 0
        L4b:
            if (r1 != r3) goto L94
        L4d:
            org.telegram.tgnet.TLRPC$RequestPeerType r1 = r5.requestPeerType
            java.lang.Boolean r1 = r1.forum
            if (r1 == 0) goto L5d
            boolean r1 = r1.booleanValue()
            boolean r3 = org.telegram.messenger.ChatObject.isForum(r7)
            if (r1 != r3) goto L94
        L5d:
            org.telegram.tgnet.TLRPC$RequestPeerType r1 = r5.requestPeerType
            org.telegram.tgnet.TLRPC$TL_chatAdminRights r1 = r1.user_admin_rights
            if (r1 == 0) goto L79
            org.telegram.messenger.MessagesController r1 = r5.getMessagesController()
            org.telegram.messenger.UserConfig r3 = r5.getUserConfig()
            org.telegram.tgnet.TLRPC$User r3 = r3.getCurrentUser()
            org.telegram.tgnet.TLRPC$RequestPeerType r4 = r5.requestPeerType
            org.telegram.tgnet.TLRPC$TL_chatAdminRights r4 = r4.user_admin_rights
            boolean r1 = r1.matchesAdminRights(r7, r3, r4)
            if (r1 == 0) goto L94
        L79:
            org.telegram.tgnet.TLRPC$RequestPeerType r1 = r5.requestPeerType
            org.telegram.tgnet.TLRPC$TL_chatAdminRights r1 = r1.bot_admin_rights
            if (r1 == 0) goto L93
            org.telegram.messenger.MessagesController r1 = r5.getMessagesController()
            org.telegram.tgnet.TLRPC$RequestPeerType r3 = r5.requestPeerType
            org.telegram.tgnet.TLRPC$TL_chatAdminRights r3 = r3.bot_admin_rights
            boolean r6 = r1.matchesAdminRights(r7, r6, r3)
            if (r6 != 0) goto L93
            boolean r6 = org.telegram.messenger.ChatObject.canAddAdmins(r7)
            if (r6 == 0) goto L94
        L93:
            return r2
        L94:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.meetRequestPeerRequirements(org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Chat):boolean");
    }

    public void setSideMenu(RecyclerView recyclerView) {
        this.sideMenu = recyclerView;
        int i = Theme.key_chats_menuBackground;
        recyclerView.setBackgroundColor(Theme.getColor(i));
        this.sideMenu.setGlowColor(Theme.getColor(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePasscodeButton() {
        if (this.passcodeItem == null) {
            return;
        }
        if (SharedConfig.passcodeHash.length() != 0 && !this.searching) {
            ActionBarMenuItem actionBarMenuItem = this.doneItem;
            if (actionBarMenuItem == null || actionBarMenuItem.getVisibility() != 0) {
                this.passcodeItem.setVisibility(0);
            }
            this.passcodeItem.setIcon(this.passcodeDrawable);
            this.passcodeItemVisible = true;
            return;
        }
        this.passcodeItem.setVisibility(8);
        this.passcodeItemVisible = false;
    }

    private void setFloatingProgressVisible(final boolean z, boolean z2) {
        if (this.floatingButton2 == null || this.floating2ProgressView == null) {
            return;
        }
        if (z2) {
            if (z == this.floatingProgressVisible) {
                return;
            }
            AnimatorSet animatorSet = this.floatingProgressAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            this.floatingProgressVisible = z;
            AnimatorSet animatorSet2 = new AnimatorSet();
            this.floatingProgressAnimator = animatorSet2;
            RLottieImageView rLottieImageView = this.floatingButton2;
            Property property = View.ALPHA;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(rLottieImageView, (Property<RLottieImageView, Float>) property, z ? 0.0f : 1.0f);
            RLottieImageView rLottieImageView2 = this.floatingButton2;
            Property property2 = View.SCALE_X;
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(rLottieImageView2, (Property<RLottieImageView, Float>) property2, z ? 0.1f : 1.0f);
            RLottieImageView rLottieImageView3 = this.floatingButton2;
            Property property3 = View.SCALE_Y;
            animatorSet2.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, ObjectAnimator.ofFloat(rLottieImageView3, (Property<RLottieImageView, Float>) property3, z ? 0.1f : 1.0f), ObjectAnimator.ofFloat(this.floating2ProgressView, (Property<RadialProgressView, Float>) property, z ? 1.0f : 0.0f), ObjectAnimator.ofFloat(this.floating2ProgressView, (Property<RadialProgressView, Float>) property2, z ? 1.0f : 0.1f), ObjectAnimator.ofFloat(this.floating2ProgressView, (Property<RadialProgressView, Float>) property3, z ? 1.0f : 0.1f));
            this.floatingProgressAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.49
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    DialogsActivity.this.floating2ProgressView.setVisibility(0);
                    DialogsActivity.this.floatingButton2.setVisibility(0);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (animator == DialogsActivity.this.floatingProgressAnimator) {
                        if (z) {
                            if (DialogsActivity.this.floatingButton2 != null) {
                                DialogsActivity.this.floatingButton2.setVisibility(8);
                            }
                        } else if (DialogsActivity.this.floatingButton2 != null) {
                            DialogsActivity.this.floating2ProgressView.setVisibility(8);
                        }
                        DialogsActivity.this.floatingProgressAnimator = null;
                    }
                }
            });
            this.floatingProgressAnimator.setDuration(150L);
            this.floatingProgressAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.floatingProgressAnimator.start();
            return;
        }
        AnimatorSet animatorSet3 = this.floatingProgressAnimator;
        if (animatorSet3 != null) {
            animatorSet3.cancel();
        }
        this.floatingProgressVisible = z;
        if (z) {
            this.floatingButton2.setAlpha(0.0f);
            this.floatingButton2.setScaleX(0.1f);
            this.floatingButton2.setScaleY(0.1f);
            this.floatingButton2.setVisibility(8);
            this.floating2ProgressView.setAlpha(1.0f);
            this.floating2ProgressView.setScaleX(1.0f);
            this.floating2ProgressView.setScaleY(1.0f);
            this.floating2ProgressView.setVisibility(0);
            return;
        }
        this.floatingButton2.setAlpha(1.0f);
        this.floatingButton2.setScaleX(1.0f);
        this.floatingButton2.setScaleY(1.0f);
        this.floatingButton2.setVisibility(0);
        this.floating2ProgressView.setAlpha(0.0f);
        this.floating2ProgressView.setScaleX(0.1f);
        this.floating2ProgressView.setScaleY(0.1f);
        this.floating2ProgressView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideFloatingButton(boolean z) {
        if (this.rightSlidingDialogContainer.hasFragment()) {
            z = true;
        }
        if (this.floatingHidden != z) {
            if (z && this.floatingForceVisible) {
                return;
            }
            this.floatingHidden = z;
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.floatingButtonHideProgress, this.floatingHidden ? 1.0f : 0.0f);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda93
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$hideFloatingButton$122(valueAnimator);
                }
            });
            animatorSet.playTogether(valueAnimatorOfFloat);
            animatorSet.setDuration(300L);
            animatorSet.setInterpolator(this.floatingInterpolator);
            this.floatingButtonContainer.setClickable(!z);
            animatorSet.start();
            if (z) {
                HintView2 hintView2 = this.storyHint;
                if (hintView2 != null) {
                    hintView2.hide();
                }
                HintView2 hintView22 = this.storyPremiumHint;
                if (hintView22 != null) {
                    hintView22.hide();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$hideFloatingButton$122(ValueAnimator valueAnimator) {
        this.floatingButtonHideProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.floatingButtonTranslation = (AndroidUtilities.m1146dp(100.0f) + this.windowInsetsStateHolder.getCurrentNavigationBarInset()) * this.floatingButtonHideProgress;
        updateFloatingButtonOffset();
    }

    public void animateContactsAlpha(float f) {
        ValueAnimator valueAnimator = this.contactsAlphaAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator duration = ValueAnimator.ofFloat(this.contactsAlpha, f).setDuration(250L);
        this.contactsAlphaAnimator = duration;
        duration.setInterpolator(CubicBezierInterpolator.DEFAULT);
        this.contactsAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda63
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$animateContactsAlpha$123(valueAnimator2);
            }
        });
        this.contactsAlphaAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$animateContactsAlpha$123(ValueAnimator valueAnimator) {
        setContactsAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public void setContactsAlpha(float f) {
        this.contactsAlpha = f;
        for (ViewPage viewPage : this.viewPages) {
            DialogsRecyclerView dialogsRecyclerView = viewPage.listView;
            for (int i = 0; i < dialogsRecyclerView.getChildCount(); i++) {
                View childAt = dialogsRecyclerView.getChildAt(i);
                if (childAt != null && dialogsRecyclerView.getChildAdapterPosition(childAt) >= viewPage.dialogsAdapter.getDialogsCount() + 1) {
                    childAt.setAlpha(f);
                }
            }
        }
    }

    public void setScrollDisabled(boolean z) {
        for (ViewPage viewPage : this.viewPages) {
            ((LinearLayoutManager) viewPage.listView.getLayoutManager()).setScrollDisabled(z);
        }
    }

    private void updateDialogIndices() {
        ViewPage[] viewPageArr = this.viewPages;
        if (viewPageArr == null) {
            return;
        }
        for (ViewPage viewPage : viewPageArr) {
            if (viewPage.getVisibility() == 0 && !viewPage.dialogsAdapter.getDialogsListIsFrozen()) {
                viewPage.updateList(false);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateVisibleRows(int i) {
        updateVisibleRows(i, true);
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x013b A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00f5  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x012b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void updateVisibleRows(int r17, boolean r18) {
        /*
            Method dump skipped, instructions count: 324
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.updateVisibleRows(int, boolean):void");
    }

    public void setDelegate(DialogsActivityDelegate dialogsActivityDelegate) {
        this.delegate = dialogsActivityDelegate;
    }

    public void setSearchString(String str) {
        this.searchString = str;
    }

    public void setInitialSearchString(String str) {
        this.initialSearchString = str;
    }

    public boolean isMainDialogList() {
        return this.delegate == null && this.searchString == null;
    }

    public boolean isArchive() {
        return this.folderId == 1;
    }

    public void setInitialSearchType(int i) {
        this.initialSearchType = i;
    }

    private boolean checkCanWrite(long j) {
        int i;
        int i2 = this.initialDialogsType;
        if (i2 != 15 && i2 != 16 && this.addToGroupAlertString == null && this.checkCanWrite) {
            if (DialogObject.isChatDialog(j)) {
                long j2 = -j;
                TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(j2));
                if (ChatObject.isChannel(chat) && !chat.megagroup && (this.cantSendToChannels || !ChatObject.isCanWriteToChannel(j2, this.currentAccount) || (i = this.hasPoll) == 2 || i == 3)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
                    builder.setTitle(LocaleController.getString(C2369R.string.SendMessageTitle));
                    int i3 = this.hasPoll;
                    if (i3 == 3) {
                        builder.setMessage(LocaleController.getString(C2369R.string.TodoCantForward));
                    } else if (i3 == 2) {
                        builder.setMessage(LocaleController.getString(C2369R.string.PublicPollCantForward));
                    } else {
                        builder.setMessage(LocaleController.getString(C2369R.string.ChannelCantSendMessage));
                    }
                    builder.setNegativeButton(LocaleController.getString(C2369R.string.f1459OK), null);
                    showDialog(builder.create());
                    return false;
                }
            } else if (DialogObject.isEncryptedDialog(j) && (this.hasPoll != 0 || this.hasInvoice)) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getParentActivity());
                builder2.setTitle(LocaleController.getString(C2369R.string.SendMessageTitle));
                int i4 = this.hasPoll;
                if (i4 == 3) {
                    builder2.setMessage(LocaleController.getString(C2369R.string.TodoCantForwardSecretChat));
                } else if (i4 != 0) {
                    builder2.setMessage(LocaleController.getString(C2369R.string.PollCantForwardSecretChat));
                } else {
                    builder2.setMessage(LocaleController.getString(C2369R.string.InvoiceCantForwardSecretChat));
                }
                builder2.setNegativeButton(LocaleController.getString(C2369R.string.f1459OK), null);
                showDialog(builder2.create());
                return false;
            }
        }
        return true;
    }

    public void didSelectResult(long j, long j2, boolean z, boolean z2) {
        didSelectResult(j, j2, z, z2, null);
    }

    public void didSelectResult(final long j, final long j2, boolean z, final boolean z2, final TopicsFragment topicsFragment) {
        final TLRPC.Chat chat;
        final TLRPC.User user;
        String string;
        String stringSimple;
        String string2;
        TLRPC.TL_forumTopic tL_forumTopicFindTopic;
        if (checkCanWrite(j)) {
            int i = this.initialDialogsType;
            if (i == 11 || i == 12 || i == 13) {
                if (this.checkingImportDialog) {
                    return;
                }
                if (DialogObject.isUserDialog(j)) {
                    TLRPC.User user2 = getMessagesController().getUser(Long.valueOf(j));
                    if (!user2.mutual_contact) {
                        UndoView undoView = getUndoView();
                        if (undoView != null) {
                            undoView.showWithAction(j, 45, (Runnable) null);
                            return;
                        }
                        return;
                    }
                    user = user2;
                    chat = null;
                } else {
                    TLRPC.Chat chat2 = getMessagesController().getChat(Long.valueOf(-j));
                    if (!ChatObject.hasAdminRights(chat2) || !ChatObject.canChangeChatInfo(chat2)) {
                        UndoView undoView2 = getUndoView();
                        if (undoView2 != null) {
                            undoView2.showWithAction(j, 46, (Runnable) null);
                            return;
                        }
                        return;
                    }
                    chat = chat2;
                    user = null;
                }
                final AlertDialog alertDialog = new AlertDialog(getParentActivity(), 3);
                final TLRPC.TL_messages_checkHistoryImportPeer tL_messages_checkHistoryImportPeer = new TLRPC.TL_messages_checkHistoryImportPeer();
                tL_messages_checkHistoryImportPeer.peer = getMessagesController().getInputPeer(j);
                getConnectionsManager().sendRequest(tL_messages_checkHistoryImportPeer, new RequestDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda110
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$didSelectResult$126(alertDialog, user, chat, j, z2, tL_messages_checkHistoryImportPeer, tLObject, tL_error);
                    }
                });
                try {
                    alertDialog.showDelayed(300L);
                    return;
                } catch (Exception unused) {
                    return;
                }
            }
            if (!z || ((this.selectAlertString == null || this.selectAlertStringGroup == null) && this.addToGroupAlertString == null)) {
                if (i == 15) {
                    final Runnable runnable = new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda112
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$didSelectResult$128(j, j2, z2, topicsFragment);
                        }
                    };
                    Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda113
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$didSelectResult$130(j, runnable);
                        }
                    };
                    if (j < 0) {
                        showSendToBotAlert(getMessagesController().getChat(Long.valueOf(-j)), runnable2, (Runnable) null);
                        return;
                    } else {
                        showSendToBotAlert(getMessagesController().getUser(Long.valueOf(j)), runnable2, (Runnable) null);
                        return;
                    }
                }
                if (this.delegate != null) {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add(MessagesStorage.TopicKey.m1182of(j, j2));
                    if (this.delegate.didSelectDialogs(this, arrayList, null, z2, this.notify, this.scheduleDate, topicsFragment) && this.resetDelegate) {
                        this.delegate = null;
                        return;
                    }
                    return;
                }
                lambda$onBackPressed$371();
                return;
            }
            if (getParentActivity() == null) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
            if (DialogObject.isEncryptedDialog(j)) {
                TLRPC.User user3 = getMessagesController().getUser(Long.valueOf(getMessagesController().getEncryptedChat(Integer.valueOf(DialogObject.getEncryptedChatId(j))).user_id));
                if (user3 == null) {
                    return;
                }
                string = LocaleController.getString(C2369R.string.SendMessageTitle);
                stringSimple = LocaleController.formatStringSimple(this.selectAlertString, UserObject.getUserName(user3));
                string2 = LocaleController.getString(C2369R.string.Send);
            } else if (!DialogObject.isUserDialog(j)) {
                TLRPC.Chat chat3 = getMessagesController().getChat(Long.valueOf(-j));
                if (chat3 == null) {
                    return;
                }
                String str = chat3.title;
                if (j2 != 0 && (tL_forumTopicFindTopic = getMessagesController().getTopicsController().findTopic(chat3.f1571id, j2)) != null) {
                    str = ((Object) str) + " " + tL_forumTopicFindTopic.title;
                }
                if (this.addToGroupAlertString != null) {
                    string = LocaleController.getString(C2369R.string.AddToTheGroupAlertTitle);
                    stringSimple = LocaleController.formatStringSimple(this.addToGroupAlertString, str);
                    string2 = LocaleController.getString(C2369R.string.Add);
                } else {
                    string = LocaleController.getString(C2369R.string.SendMessageTitle);
                    stringSimple = LocaleController.formatStringSimple(this.selectAlertStringGroup, str);
                    string2 = LocaleController.getString(C2369R.string.Send);
                }
            } else if (j == getUserConfig().getClientUserId()) {
                string = LocaleController.getString(C2369R.string.SendMessageTitle);
                stringSimple = LocaleController.formatStringSimple(this.selectAlertStringGroup, LocaleController.getString(C2369R.string.SavedMessages));
                string2 = LocaleController.getString(C2369R.string.Send);
            } else {
                TLRPC.User user4 = getMessagesController().getUser(Long.valueOf(j));
                if (user4 == null || this.selectAlertString == null) {
                    return;
                }
                string = LocaleController.getString(C2369R.string.SendMessageTitle);
                stringSimple = LocaleController.formatStringSimple(this.selectAlertString, UserObject.getUserName(user4));
                string2 = LocaleController.getString(C2369R.string.Send);
            }
            String str2 = string;
            String str3 = string2;
            builder.setTitle(str2);
            builder.setMessage(AndroidUtilities.replaceTags(stringSimple));
            builder.setPositiveButton(str3, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda111
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog2, int i2) {
                    this.f$0.lambda$didSelectResult$127(j, j2, topicsFragment, alertDialog2, i2);
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
            AlertDialog alertDialogCreate = builder.create();
            if (showDialog(alertDialogCreate) == null) {
                alertDialogCreate.show();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didSelectResult$126(final AlertDialog alertDialog, final TLRPC.User user, final TLRPC.Chat chat, final long j, final boolean z, final TLRPC.TL_messages_checkHistoryImportPeer tL_messages_checkHistoryImportPeer, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda126
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$didSelectResult$125(alertDialog, tLObject, user, chat, j, z, tL_error, tL_messages_checkHistoryImportPeer);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didSelectResult$125(AlertDialog alertDialog, TLObject tLObject, TLRPC.User user, TLRPC.Chat chat, final long j, final boolean z, TLRPC.TL_error tL_error, TLRPC.TL_messages_checkHistoryImportPeer tL_messages_checkHistoryImportPeer) {
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        this.checkingImportDialog = false;
        if (tLObject != null) {
            AlertsCreator.createImportDialogAlert(this, this.arguments.getString("importTitle"), ((TLRPC.TL_messages_checkedHistoryImportPeer) tLObject).confirm_text, user, chat, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda133
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didSelectResult$124(j, z);
                }
            });
        } else {
            AlertsCreator.processError(this.currentAccount, tL_error, this, tL_messages_checkHistoryImportPeer, new Object[0]);
            getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.historyImportProgressChanged, Long.valueOf(j), tL_messages_checkHistoryImportPeer, tL_error);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didSelectResult$124(long j, boolean z) {
        setDialogsListFrozen(true);
        ArrayList arrayList = new ArrayList();
        arrayList.add(MessagesStorage.TopicKey.m1182of(j, 0L));
        this.delegate.didSelectDialogs(this, arrayList, null, z, this.notify, this.scheduleDate, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didSelectResult$127(long j, long j2, TopicsFragment topicsFragment, AlertDialog alertDialog, int i) {
        didSelectResult(j, j2, false, false, topicsFragment);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didSelectResult$128(long j, long j2, boolean z, TopicsFragment topicsFragment) {
        if (this.delegate != null) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(MessagesStorage.TopicKey.m1182of(j, j2));
            this.delegate.didSelectDialogs(this, arrayList, null, z, this.notify, this.scheduleDate, topicsFragment);
            if (this.resetDelegate) {
                this.delegate = null;
                return;
            }
            return;
        }
        lambda$onBackPressed$371();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$didSelectResult$130(long j, final Runnable runnable) {
        if (this.requestPeerType.bot_admin_rights != null) {
            getMessagesController().setUserAdminRole(-j, getMessagesController().getUser(Long.valueOf(this.requestPeerBotId)), this.requestPeerType.bot_admin_rights, null, false, this, true, true, null, runnable, new MessagesController.ErrorDelegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda125
                @Override // org.telegram.messenger.MessagesController.ErrorDelegate
                public final boolean run(TLRPC.TL_error tL_error) {
                    return DialogsActivity.$r8$lambda$rcQyQ3FmYtEQZWSquZwdMGXjjUk(runnable, tL_error);
                }
            });
        } else {
            runnable.run();
        }
    }

    public static /* synthetic */ boolean $r8$lambda$rcQyQ3FmYtEQZWSquZwdMGXjjUk(Runnable runnable, TLRPC.TL_error tL_error) {
        runnable.run();
        return true;
    }

    private void showSendToBotAlert(TLRPC.User user, final Runnable runnable, final Runnable runnable2) {
        TLRPC.User user2 = getMessagesController().getUser(Long.valueOf(this.requestPeerBotId));
        showDialog(new AlertDialog.Builder(getContext()).setTitle(LocaleController.formatString(C2369R.string.AreYouSureSendChatToBotTitle, UserObject.getFirstName(user), UserObject.getFirstName(user2))).setMessage(TextUtils.concat(AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.AreYouSureSendChatToBotMessage, UserObject.getFirstName(user), UserObject.getFirstName(user2))))).setPositiveButton(LocaleController.formatString("Send", C2369R.string.Send, new Object[0]), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda127
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                runnable.run();
            }
        }).setNegativeButton(LocaleController.formatString("Cancel", C2369R.string.Cancel, new Object[0]), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda128
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                DialogsActivity.$r8$lambda$yXxLSWLW_hqCFeaKr_w4bEBlHno(runnable2, alertDialog, i);
            }
        }).create());
    }

    public static /* synthetic */ void $r8$lambda$yXxLSWLW_hqCFeaKr_w4bEBlHno(Runnable runnable, AlertDialog alertDialog, int i) {
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSendToBotAlert(TLRPC.Chat chat, final Runnable runnable, final Runnable runnable2) {
        CharSequence charSequenceConcat;
        String string;
        TLRPC.User user = getMessagesController().getUser(Long.valueOf(this.requestPeerBotId));
        boolean zIsChannelAndNotMegaGroup = ChatObject.isChannelAndNotMegaGroup(chat);
        AlertDialog.Builder title = new AlertDialog.Builder(getContext()).setTitle(LocaleController.formatString(C2369R.string.AreYouSureSendChatToBotTitle, chat.title, UserObject.getFirstName(user)));
        SpannableStringBuilder spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.AreYouSureSendChatToBotMessage, chat.title, UserObject.getFirstName(user)));
        Boolean bool = this.requestPeerType.bot_participant;
        if ((bool != null && bool.booleanValue() && !getMessagesController().isInChatCached(chat, user)) || this.requestPeerType.bot_admin_rights != null) {
            if (this.requestPeerType.bot_admin_rights == null) {
                string = LocaleController.formatString(C2369R.string.AreYouSureSendChatToBotAdd, UserObject.getFirstName(user), chat.title);
            } else {
                string = LocaleController.formatString(C2369R.string.AreYouSureSendChatToBotAddRights, UserObject.getFirstName(user), chat.title, RequestPeerRequirementsCell.rightsToString(this.requestPeerType.bot_admin_rights, zIsChannelAndNotMegaGroup));
            }
            charSequenceConcat = TextUtils.concat("\n\n", AndroidUtilities.replaceTags(string));
        } else {
            charSequenceConcat = "";
        }
        showDialog(title.setMessage(TextUtils.concat(spannableStringBuilderReplaceTags, charSequenceConcat)).setPositiveButton(LocaleController.formatString("Send", C2369R.string.Send, new Object[0]), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda123
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                runnable.run();
            }
        }).setNegativeButton(LocaleController.formatString("Cancel", C2369R.string.Cancel, new Object[0]), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda124
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                DialogsActivity.$r8$lambda$b3usuVl4lTQc380QY0n7aol0ckg(runnable2, alertDialog, i);
            }
        }).create());
    }

    public static /* synthetic */ void $r8$lambda$b3usuVl4lTQc380QY0n7aol0ckg(Runnable runnable, AlertDialog alertDialog, int i) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public RLottieImageView getFloatingButton() {
        return this.floatingButton;
    }

    private void onSendLongClick(View view) {
        DialogsActivity dialogsActivity;
        final Activity parentActivity = getParentActivity();
        final Theme.ResourcesProvider resourceProvider = getResourceProvider();
        if (parentActivity == null) {
            return;
        }
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        ActionBarPopupWindow.ActionBarPopupWindowLayout actionBarPopupWindowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(parentActivity, resourceProvider);
        final boolean z = false;
        actionBarPopupWindowLayout.setAnimationEnabled(false);
        actionBarPopupWindowLayout.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.DialogsActivity.50
            private final Rect popupRect = new Rect();

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() != 0 || DialogsActivity.this.sendPopupWindow == null || !DialogsActivity.this.sendPopupWindow.isShowing()) {
                    return false;
                }
                view2.getHitRect(this.popupRect);
                if (this.popupRect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                    return false;
                }
                DialogsActivity.this.sendPopupWindow.dismiss();
                return false;
            }
        });
        actionBarPopupWindowLayout.setDispatchKeyEventListener(new ActionBarPopupWindow.OnDispatchKeyEventListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda55
            @Override // org.telegram.ui.ActionBar.ActionBarPopupWindow.OnDispatchKeyEventListener
            public final void onDispatchKeyEvent(KeyEvent keyEvent) {
                this.f$0.lambda$onSendLongClick$135(keyEvent);
            }
        });
        actionBarPopupWindowLayout.setShownFromBottom(false);
        actionBarPopupWindowLayout.setupRadialSelectors(getThemedColor(Theme.key_dialogButtonSelector));
        if (this.commentView.getFieldText() == null || this.commentView.getFieldText().length() == 0) {
            dialogsActivity = this;
        } else {
            dialogsActivity = this;
            actionBarPopupWindowLayout.addView((View) new TranslateBeforeSendWrapper(getContext(), true, false, resourceProvider) { // from class: org.telegram.ui.DialogsActivity.51
                @Override // com.exteragram.messenger.components.TranslateBeforeSendWrapper
                protected void onClick() {
                    if (DialogsActivity.this.sendPopupWindow != null && DialogsActivity.this.sendPopupWindow.isShowing()) {
                        DialogsActivity.this.sendPopupWindow.dismiss();
                    }
                    TranslatorUtils.translate(DialogsActivity.this.commentView.getFieldText(), new TranslatorUtils.TranslateCallback() { // from class: org.telegram.ui.DialogsActivity.51.1
                        @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                        public /* synthetic */ void onReqId(int i) {
                            TranslatorUtils.TranslateCallback.CC.$default$onReqId(this, i);
                        }

                        @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                        public /* synthetic */ void onSuccess(TLObject tLObject, TLRPC.TL_error tL_error) {
                            TranslatorUtils.TranslateCallback.CC.$default$onSuccess(this, tLObject, tL_error);
                        }

                        @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                        public void onSuccess(String str) {
                            DialogsActivity.this.commentView.setFieldText(str);
                            DialogsActivity.this.commentView.setSelection(str.length());
                        }

                        @Override // com.exteragram.messenger.utils.text.TranslatorUtils.TranslateCallback
                        public void onFailed() {
                            BulletinFactory.m1267of(DialogsActivity.this).createErrorBulletin(LocaleController.getString(C2369R.string.TranslationFailedAlert2)).show();
                        }
                    });
                }
            }, LayoutHelper.createLinear(-1, 48));
        }
        boolean z2 = true;
        for (int i = 0; i < dialogsActivity.selectedDialogs.size(); i++) {
            long jLongValue = ((Long) dialogsActivity.selectedDialogs.get(i)).longValue();
            if (DialogObject.isEncryptedDialog(jLongValue)) {
                z2 = false;
            }
            TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(-jLongValue));
            if (chat != null && !ChatObject.canWriteToChat(chat)) {
                z2 = false;
            }
        }
        ActionBarMenuSubItem actionBarMenuSubItem = new ActionBarMenuSubItem(parentActivity, dialogsActivity.commentView.getFieldText() == null || dialogsActivity.commentView.getFieldText().length() == 0, !z2, resourceProvider);
        actionBarMenuSubItem.setTextAndIcon(AyuGhostController.getInstance(dialogsActivity.currentAccount).getSendWithoutSoundString(), AyuGhostController.getInstance(dialogsActivity.currentAccount).getSendWithoutSoundIcon());
        actionBarMenuSubItem.setMinimumWidth(AndroidUtilities.m1146dp(196.0f));
        actionBarPopupWindowLayout.addView((View) actionBarMenuSubItem, LayoutHelper.createLinear(-1, 48));
        actionBarMenuSubItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda56
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$onSendLongClick$136(view2);
            }
        });
        if (z2) {
            ActionBarMenuSubItem actionBarMenuSubItem2 = new ActionBarMenuSubItem((Context) parentActivity, false, true, resourceProvider);
            actionBarMenuSubItem2.setTextAndIcon(LocaleController.getString(C2369R.string.ScheduleMessage), C2369R.drawable.msg_calendar2);
            actionBarMenuSubItem2.setMinimumWidth(AndroidUtilities.m1146dp(196.0f));
            actionBarPopupWindowLayout.addView((View) actionBarMenuSubItem2, LayoutHelper.createLinear(-1, 48));
            actionBarMenuSubItem2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda57
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    this.f$0.lambda$onSendLongClick$137(parentActivity, z, resourceProvider, view2);
                }
            });
        }
        linearLayout.addView(actionBarPopupWindowLayout, LayoutHelper.createLinear(-1, -2));
        ActionBarPopupWindow actionBarPopupWindow = new ActionBarPopupWindow(linearLayout, -2, -2);
        dialogsActivity.sendPopupWindow = actionBarPopupWindow;
        actionBarPopupWindow.setAnimationEnabled(false);
        dialogsActivity.sendPopupWindow.setAnimationStyle(C2369R.style.PopupContextAnimation2);
        dialogsActivity.sendPopupWindow.setOutsideTouchable(true);
        dialogsActivity.sendPopupWindow.setClippingEnabled(true);
        dialogsActivity.sendPopupWindow.setInputMethodMode(2);
        dialogsActivity.sendPopupWindow.setSoftInputMode(0);
        dialogsActivity.sendPopupWindow.getContentView().setFocusableInTouchMode(true);
        SharedConfig.removeScheduledOrNoSoundHint();
        linearLayout.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(1000.0f), TLObject.FLAG_31), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(1000.0f), TLObject.FLAG_31));
        dialogsActivity.sendPopupWindow.setFocusable(true);
        int[] iArr = new int[2];
        view.getLocationInWindow(iArr);
        dialogsActivity.sendPopupWindow.showAtLocation(view, 51, ((iArr[0] + view.getMeasuredWidth()) - linearLayout.getMeasuredWidth()) + AndroidUtilities.m1146dp(8.0f), (iArr[1] - linearLayout.getMeasuredHeight()) - AndroidUtilities.m1146dp(2.0f));
        dialogsActivity.sendPopupWindow.dimBehind();
        try {
            view.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSendLongClick$135(KeyEvent keyEvent) {
        ActionBarPopupWindow actionBarPopupWindow;
        if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && (actionBarPopupWindow = this.sendPopupWindow) != null && actionBarPopupWindow.isShowing()) {
            this.sendPopupWindow.dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSendLongClick$136(View view) {
        ActionBarPopupWindow actionBarPopupWindow = this.sendPopupWindow;
        if (actionBarPopupWindow != null && actionBarPopupWindow.isShowing()) {
            this.sendPopupWindow.dismiss();
        }
        this.notify = false;
        if (this.delegate == null || this.selectedDialogs.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < this.selectedDialogs.size(); i++) {
            arrayList.add(MessagesStorage.TopicKey.m1182of(((Long) this.selectedDialogs.get(i)).longValue(), 0L));
        }
        this.delegate.didSelectDialogs(this, arrayList, this.commentView.getFieldText(), false, this.notify, this.scheduleDate, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSendLongClick$137(Activity activity, boolean z, Theme.ResourcesProvider resourcesProvider, View view) {
        ActionBarPopupWindow actionBarPopupWindow = this.sendPopupWindow;
        if (actionBarPopupWindow != null && actionBarPopupWindow.isShowing()) {
            this.sendPopupWindow.dismiss();
        }
        AlertsCreator.createScheduleDatePickerDialog(activity, z ? getUserConfig().getClientUserId() : -1L, new AlertsCreator.ScheduleDatePickerDelegate() { // from class: org.telegram.ui.DialogsActivity.52
            @Override // org.telegram.ui.Components.AlertsCreator.ScheduleDatePickerDelegate
            public void didSelectDate(boolean z2, int i, int i2) {
                DialogsActivity dialogsActivity = DialogsActivity.this;
                dialogsActivity.scheduleDate = i;
                if (dialogsActivity.delegate == null || DialogsActivity.this.selectedDialogs.isEmpty()) {
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (int i3 = 0; i3 < DialogsActivity.this.selectedDialogs.size(); i3++) {
                    arrayList.add(MessagesStorage.TopicKey.m1182of(((Long) DialogsActivity.this.selectedDialogs.get(i3)).longValue(), 0L));
                }
                DialogsActivityDelegate dialogsActivityDelegate = DialogsActivity.this.delegate;
                DialogsActivity dialogsActivity2 = DialogsActivity.this;
                dialogsActivityDelegate.didSelectDialogs(dialogsActivity2, arrayList, dialogsActivity2.commentView.getFieldText(), false, z2, i, null);
            }
        }, resourcesProvider);
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x082b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x03ed  */
    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.ArrayList getThemeDescriptions() {
        /*
            Method dump skipped, instructions count: 6411
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.getThemeDescriptions():java.util.ArrayList");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:105:0x004b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0020  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$getThemeDescriptions$138() {
        /*
            Method dump skipped, instructions count: 426
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.DialogsActivity.lambda$getThemeDescriptions$138():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getThemeDescriptions$139() {
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager != null) {
            ActionBarMenu actionMode = searchViewPager.getActionMode();
            if (actionMode != null) {
                actionMode.setBackgroundColor(getThemedColor(Theme.key_actionBarActionModeDefault));
            }
            ActionBarMenuItem speedItem = this.searchViewPager.getSpeedItem();
            if (speedItem != null) {
                speedItem.getIconView().setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_actionBarActionModeDefaultIcon), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getThemeDescriptions$140() {
        this.speedItem.getIconView().setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon), PorterDuff.Mode.SRC_IN));
        this.speedItem.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_actionBarActionModeDefaultSelector)));
    }

    private void updateFloatingButtonColor() {
        if (getParentActivity() == null) {
            return;
        }
        if (this.floatingButtonContainer != null) {
            this.floatingButtonContainer.setBackground(CanvasUtils.createFabBackground());
        }
        FrameLayout frameLayout = this.floatingButton2Container;
        if (frameLayout != null) {
            frameLayout.setBackground(CanvasUtils.createFabBackground(40, getThemedColor(Theme.key_chats_actionBackground), getThemedColor(Theme.key_chats_actionPressedBackground)));
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    protected Animator getCustomSlideTransition(boolean z, boolean z2, float f) {
        if (z2) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.slideFragmentProgress, 1.0f);
            this.slideBackTransitionAnimator = valueAnimatorOfFloat;
            return valueAnimatorOfFloat;
        }
        int iMax = getLayoutContainer() != null ? (int) (Math.max((int) ((200.0f / getLayoutContainer().getMeasuredWidth()) * f), 80) * 1.2f) : 150;
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(this.slideFragmentProgress, 1.0f);
        this.slideBackTransitionAnimator = valueAnimatorOfFloat2;
        valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda23
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$getCustomSlideTransition$141(valueAnimator);
            }
        });
        this.slideBackTransitionAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT);
        this.slideBackTransitionAnimator.setDuration(iMax);
        this.slideBackTransitionAnimator.start();
        return this.slideBackTransitionAnimator;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getCustomSlideTransition$141(ValueAnimator valueAnimator) {
        setSlideTransitionProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void prepareFragmentToSlide(boolean z, boolean z2) {
        if (!z && z2) {
            this.isSlideBackTransition = true;
            setFragmentIsSliding(true);
        } else {
            this.slideBackTransitionAnimator = null;
            this.isSlideBackTransition = false;
            setFragmentIsSliding(false);
            setSlideTransitionProgress(1.0f);
        }
    }

    private void setFragmentIsSliding(boolean z) {
        ViewPage viewPage;
        if (SharedConfig.getDevicePerformanceClass() == 0 || !LiteMode.isEnabled(32768)) {
            return;
        }
        if (z) {
            ViewPage[] viewPageArr = this.viewPages;
            if (viewPageArr != null && (viewPage = viewPageArr[0]) != null) {
                viewPage.setLayerType(2, null);
                this.viewPages[0].setClipChildren(false);
                this.viewPages[0].setClipToPadding(false);
                this.viewPages[0].listView.setClipChildren(false);
            }
            ActionBar actionBar = this.actionBar;
            if (actionBar != null) {
                actionBar.setLayerType(2, null);
            }
            FilterTabsView filterTabsView = this.filterTabsView;
            if (filterTabsView != null) {
                filterTabsView.getListView().setLayerType(2, null);
            }
            View view = this.fragmentView;
            if (view != null) {
                ((ViewGroup) view).setClipChildren(false);
                this.fragmentView.requestLayout();
                return;
            }
            return;
        }
        ViewPage[] viewPageArr2 = this.viewPages;
        if (viewPageArr2 != null) {
            for (ViewPage viewPage2 : viewPageArr2) {
                if (viewPage2 != null) {
                    viewPage2.setLayerType(0, null);
                    viewPage2.setClipChildren(true);
                    viewPage2.setClipToPadding(true);
                    viewPage2.listView.setClipChildren(true);
                }
            }
        }
        ActionBar actionBar2 = this.actionBar;
        if (actionBar2 != null) {
            actionBar2.setLayerType(0, null);
        }
        FilterTabsView filterTabsView2 = this.filterTabsView;
        if (filterTabsView2 != null) {
            filterTabsView2.getListView().setLayerType(0, null);
        }
        DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
        if (dialogStoriesCell != null) {
            dialogStoriesCell.setLayerType(0, null);
        }
        View view2 = this.fragmentView;
        if (view2 != null) {
            ((ViewGroup) view2).setClipChildren(true);
            this.fragmentView.requestLayout();
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onSlideProgress(boolean z, float f) {
        if ((SharedConfig.getDevicePerformanceClass() > 0 || BuildVars.DEBUG_PRIVATE_VERSION) && this.isSlideBackTransition && this.slideBackTransitionAnimator == null) {
            setSlideTransitionProgress(f);
        }
    }

    private void setSlideTransitionProgress(float f) {
        if ((SharedConfig.getDevicePerformanceClass() > 0 || BuildVars.DEBUG_PRIVATE_VERSION) && this.slideFragmentProgress != f) {
            this.slideFragmentLite = SharedConfig.getDevicePerformanceClass() == 0 || !LiteMode.isEnabled(32768);
            this.slideFragmentProgress = f;
            View view = this.fragmentView;
            if (view != null) {
                view.invalidate();
            }
            if (this.slideFragmentLite) {
                FilterTabsView filterTabsView = this.filterTabsView;
                if (filterTabsView != null) {
                    filterTabsView.getListView().setTranslationX((this.isDrawerTransition ? 1 : -1) * getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
                    this.filterTabsView.invalidate();
                }
                DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
                if (dialogStoriesCell != null) {
                    dialogStoriesCell.setTranslationX((this.isDrawerTransition ? 1 : -1) * getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
                }
                FrameLayout frameLayout = this.floatingButtonContainer;
                if (frameLayout != null) {
                    frameLayout.setTranslationX((this.isDrawerTransition ? 1 : -1) * getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
                }
                FrameLayout frameLayout2 = this.floatingButton2Container;
                if (frameLayout2 != null) {
                    frameLayout2.setTranslationX((this.isDrawerTransition ? 1 : -1) * getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
                }
                FrameLayout frameLayout3 = this.updateLayout;
                if (frameLayout3 != null) {
                    frameLayout3.setTranslationX((this.isDrawerTransition ? 1 : -1) * getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
                }
                RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
                if (rightSlidingDialogContainer == null || rightSlidingDialogContainer.getFragmentView() == null || this.rightFragmentTransitionInProgress) {
                    return;
                }
                this.rightSlidingDialogContainer.getFragmentView().setTranslationX((this.isDrawerTransition ? 1 : -1) * getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
                return;
            }
            float f2 = 1.0f - ((1.0f - this.slideFragmentProgress) * 0.05f);
            if (this.filterTabsView != null) {
                if (allowToScale()) {
                    this.filterTabsView.getListView().setScaleX(f2);
                    this.filterTabsView.getListView().setScaleY(f2);
                    this.filterTabsView.getListView().setPivotX(this.isDrawerTransition ? this.filterTabsView.getMeasuredWidth() : 0.0f);
                    this.filterTabsView.getListView().setPivotY(0.0f);
                }
                this.filterTabsView.getListView().setTranslationX(getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
                this.filterTabsView.invalidate();
            }
            if (this.dialogStoriesCell != null) {
                if (allowToScale()) {
                    this.dialogStoriesCell.setScaleX(f2);
                    this.dialogStoriesCell.setScaleY(f2);
                    this.dialogStoriesCell.setPivotX(this.isDrawerTransition ? r1.getMeasuredWidth() : 0.0f);
                    this.dialogStoriesCell.setPivotY(0.0f);
                }
                this.dialogStoriesCell.setTranslationX(getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
            }
            if (this.floatingButtonContainer != null) {
                if (allowToScale()) {
                    this.floatingButtonContainer.setScaleX(f2);
                    this.floatingButtonContainer.setScaleY(f2);
                    this.floatingButtonContainer.setPivotX(this.isDrawerTransition ? r1.getMeasuredWidth() : 0.0f);
                    this.floatingButtonContainer.setPivotY(0.0f);
                }
                this.floatingButtonContainer.setTranslationX(getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
            }
            if (this.floatingButton2Container != null) {
                if (allowToScale()) {
                    this.floatingButton2Container.setScaleX(f2);
                    this.floatingButton2Container.setScaleY(f2);
                    this.floatingButton2Container.setPivotX(this.isDrawerTransition ? r1.getMeasuredWidth() : 0.0f);
                    this.floatingButton2Container.setPivotY(0.0f);
                }
                this.floatingButton2Container.setTranslationX(getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
            }
            FrameLayout frameLayout4 = this.updateLayout;
            if (frameLayout4 != null) {
                frameLayout4.setTranslationX(getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
            }
            RightSlidingDialogContainer rightSlidingDialogContainer2 = this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer2 == null || rightSlidingDialogContainer2.getFragmentView() == null) {
                return;
            }
            if (!this.rightFragmentTransitionInProgress) {
                if (allowToScale()) {
                    this.rightSlidingDialogContainer.getFragmentView().setScaleX(f2);
                    this.rightSlidingDialogContainer.getFragmentView().setScaleY(f2);
                }
                this.rightSlidingDialogContainer.getFragmentView().setTranslationX(getSlideAmplitude() * (1.0f - this.slideFragmentProgress));
            }
            this.rightSlidingDialogContainer.getFragmentView().setPivotX(this.isDrawerTransition ? this.rightSlidingDialogContainer.getMeasuredWidth() : 0.0f);
            this.rightSlidingDialogContainer.getFragmentView().setPivotY(0.0f);
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public INavigationLayout.BackButtonState getBackButtonState() {
        return (isArchive() || this.rightSlidingDialogContainer.isOpenned) ? INavigationLayout.BackButtonState.BACK : INavigationLayout.BackButtonState.MENU;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void setProgressToDrawerOpened(float f) {
        if (SharedConfig.getDevicePerformanceClass() <= 0 || this.isSlideBackTransition) {
            return;
        }
        boolean z = false;
        boolean z2 = f > 0.0f;
        if (this.searchIsShowed) {
            f = 0.0f;
        } else {
            z = z2;
        }
        if (z != this.isDrawerTransition) {
            this.isDrawerTransition = z;
            setFragmentIsSliding(z);
            View view = this.fragmentView;
            if (view != null) {
                view.requestLayout();
            }
        }
        setSlideTransitionProgress(1.0f - f);
    }

    public void setShowSearch(String str, int i) {
        int positionForType;
        if (!this.searching) {
            this.initialSearchType = i;
            this.actionBar.openSearchField(str, false);
            return;
        }
        if (!this.searchItem.getSearchField().getText().toString().equals(str)) {
            this.searchItem.getSearchField().setText(str);
        }
        SearchViewPager searchViewPager = this.searchViewPager;
        if (searchViewPager == null || (positionForType = searchViewPager.getPositionForType(i)) < 0 || this.searchViewPager.getTabsView().getCurrentTabId() == positionForType) {
            return;
        }
        this.searchViewPager.getTabsView().scrollToTab(positionForType, positionForType);
    }

    public ActionBarMenuItem getSearchItem() {
        return this.searchItem;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean isLightStatusBar() {
        RightSlidingDialogContainer rightSlidingDialogContainer;
        if (!this.searching && (rightSlidingDialogContainer = this.rightSlidingDialogContainer) != null && rightSlidingDialogContainer.getFragment() != null) {
            return this.rightSlidingDialogContainer.getFragment().isLightStatusBar();
        }
        int color = Theme.getColor((this.searching && this.whiteActionBar) ? Theme.key_windowBackgroundWhite : this.folderId == 0 ? Theme.key_actionBarDefault : Theme.key_actionBarDefaultArchived);
        ActionBar actionBar = this.actionBar;
        if (actionBar != null && actionBar.isActionModeShowed()) {
            color = Theme.getColor(Theme.key_actionBarActionModeDefault);
        }
        return ColorUtils.calculateLuminance(color) > 0.699999988079071d;
    }

    @Override // org.telegram.p023ui.Components.FloatingDebug.FloatingDebugProvider
    public List onGetDebugItems() {
        return Arrays.asList(new FloatingDebugController.DebugItem(LocaleController.getString(C2369R.string.DebugDialogsActivity)), new FloatingDebugController.DebugItem(LocaleController.getString(C2369R.string.ClearLocalDatabase), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda129
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onGetDebugItems$142();
            }
        }), new FloatingDebugController.DebugItem(LocaleController.getString(C2369R.string.DebugClearSendMessageAsPeers), new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda130
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onGetDebugItems$143();
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onGetDebugItems$142() {
        getMessagesStorage().clearLocalDatabase();
        Toast.makeText(getContext(), LocaleController.getString(C2369R.string.DebugClearLocalDatabaseSuccess), 0).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onGetDebugItems$143() {
        getMessagesController().clearSendAsPeers();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean isSwipeBackEnabled(MotionEvent motionEvent) {
        return this.initialDialogsType != 3 || this.viewPages[0].selectedType == this.filterTabsView.getFirstTabId();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean closeLastFragment() {
        if (this.rightSlidingDialogContainer.hasFragment()) {
            this.rightSlidingDialogContainer.lambda$presentFragment$1();
            SearchViewPager searchViewPager = this.searchViewPager;
            if (searchViewPager == null) {
                return true;
            }
            searchViewPager.updateTabs();
            return true;
        }
        return super.closeLastFragment();
    }

    public boolean getAllowGlobalSearch() {
        return this.allowGlobalSearch;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean canBeginSlide() {
        FilterTabsView filterTabsView;
        if (this.rightSlidingDialogContainer.hasFragment()) {
            return false;
        }
        if (this.initialDialogsType == 3 && (filterTabsView = this.filterTabsView) != null && filterTabsView.getVisibility() == 0) {
            return this.filterTabsView.isFirstTab();
        }
        return true;
    }

    public int getSlideAmplitude() {
        if (this.isDrawerTransition && ExteraConfig.immersiveDrawerAnimation) {
            return this.parentLayout.getDrawerLayoutContainer().getDrawerWidth();
        }
        int iM1146dp = AndroidUtilities.m1146dp(this.slideFragmentLite ? 120.0f : 20.0f);
        int i = 1;
        if (!this.slideFragmentLite && !this.isDrawerTransition) {
            i = -1;
        }
        return iM1146dp * i;
    }

    public boolean allowToScale() {
        return (this.isDrawerTransition && ExteraConfig.immersiveDrawerAnimation) ? false : true;
    }

    public void updateStoriesVisibility(boolean z) {
        final boolean z2;
        if (this.dialogStoriesCell == null || this.storiesVisibilityAnimator != null) {
            return;
        }
        RightSlidingDialogContainer rightSlidingDialogContainer = this.rightSlidingDialogContainer;
        if ((rightSlidingDialogContainer == null || !rightSlidingDialogContainer.hasFragment()) && !this.searchIsShowed) {
            ActionBar actionBar = this.actionBar;
            if ((actionBar == null || !actionBar.isActionModeShowed()) && !this.onlySelect) {
                int i = 0;
                if (StoryRecorder.isVisible() || (getLastStoryViewer() != null && getLastStoryViewer().isFullyVisible())) {
                    z = false;
                }
                boolean zHasOnlySelfStories = !isArchive() && getStoriesController().hasOnlySelfStories();
                if (isArchive()) {
                    z2 = !getStoriesController().getHiddenList().isEmpty();
                } else {
                    z2 = !zHasOnlySelfStories && getStoriesController().hasStories();
                    zHasOnlySelfStories = getStoriesController().hasOnlySelfStories();
                }
                this.hasOnlySlefStories = zHasOnlySelfStories;
                boolean z3 = this.dialogStoriesCellVisible;
                boolean z4 = zHasOnlySelfStories || z2;
                this.dialogStoriesCellVisible = z4;
                if (z2 || z4) {
                    this.dialogStoriesCell.updateItems(z, z4 != z3);
                }
                boolean z5 = this.dialogStoriesCellVisible;
                int i2 = 8;
                if (z5 != z3) {
                    if (z) {
                        ValueAnimator valueAnimator = this.storiesVisibilityAnimator2;
                        if (valueAnimator != null) {
                            valueAnimator.cancel();
                        }
                        if (this.dialogStoriesCellVisible && !isInPreviewMode()) {
                            this.dialogStoriesCell.setVisibility(0);
                        }
                        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.progressToDialogStoriesCell, this.dialogStoriesCellVisible ? 1.0f : 0.0f);
                        this.storiesVisibilityAnimator2 = valueAnimatorOfFloat;
                        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.DialogsActivity.53
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                                DialogsActivity.this.progressToDialogStoriesCell = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                                View view = DialogsActivity.this.fragmentView;
                                if (view != null) {
                                    view.invalidate();
                                }
                            }
                        });
                        this.storiesVisibilityAnimator2.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.54
                            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                            public void onAnimationEnd(Animator animator) {
                                DialogsActivity dialogsActivity = DialogsActivity.this;
                                boolean z6 = dialogsActivity.dialogStoriesCellVisible;
                                dialogsActivity.progressToDialogStoriesCell = z6 ? 1.0f : 0.0f;
                                if (!z6) {
                                    dialogsActivity.dialogStoriesCell.setVisibility(8);
                                }
                                View view = DialogsActivity.this.fragmentView;
                                if (view != null) {
                                    view.invalidate();
                                }
                            }
                        });
                        this.storiesVisibilityAnimator2.setDuration(200L);
                        this.storiesVisibilityAnimator2.setInterpolator(CubicBezierInterpolator.DEFAULT);
                        this.storiesVisibilityAnimator2.start();
                    } else {
                        this.dialogStoriesCell.setVisibility((!z5 || isInPreviewMode()) ? 8 : 0);
                        this.progressToDialogStoriesCell = this.dialogStoriesCellVisible ? 1.0f : 0.0f;
                        View view = this.fragmentView;
                        if (view != null) {
                            view.invalidate();
                        }
                    }
                }
                if (z2 == this.animateToHasStories) {
                    return;
                }
                this.animateToHasStories = z2;
                if (z2) {
                    this.dialogStoriesCell.setProgressToCollapse(1.0f, false);
                }
                if (z && !isInPreviewMode()) {
                    this.dialogStoriesCell.setVisibility(0);
                    float f = -this.scrollYOffset;
                    float maxScrollYOffset = z2 ? 0.0f : getMaxScrollYOffset();
                    ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
                    this.storiesVisibilityAnimator = valueAnimatorOfFloat2;
                    valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(f, z2, maxScrollYOffset) { // from class: org.telegram.ui.DialogsActivity.55
                        int currentValue;
                        final /* synthetic */ float val$fromScrollY;
                        final /* synthetic */ boolean val$newVisibility;
                        final /* synthetic */ float val$toScrollY;

                        {
                            this.val$fromScrollY = f;
                            this.val$newVisibility = z2;
                            this.val$toScrollY = maxScrollYOffset;
                            this.currentValue = (int) f;
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            DialogsActivity.this.progressToShowStories = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                            if (!this.val$newVisibility) {
                                DialogsActivity dialogsActivity = DialogsActivity.this;
                                dialogsActivity.progressToShowStories = 1.0f - dialogsActivity.progressToShowStories;
                            }
                            int iLerp = (int) AndroidUtilities.lerp(this.val$fromScrollY, this.val$toScrollY, ((Float) valueAnimator2.getAnimatedValue()).floatValue());
                            int i3 = iLerp - this.currentValue;
                            this.currentValue = iLerp;
                            DialogsActivity.this.viewPages[0].listView.scrollBy(0, i3);
                            View view2 = DialogsActivity.this.fragmentView;
                            if (view2 != null) {
                                view2.invalidate();
                            }
                        }
                    });
                    this.storiesVisibilityAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.DialogsActivity.56
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            DialogsActivity dialogsActivity = DialogsActivity.this;
                            dialogsActivity.storiesVisibilityAnimator = null;
                            boolean z6 = z2;
                            dialogsActivity.hasStories = z6;
                            if (!z6 && !dialogsActivity.hasOnlySlefStories) {
                                dialogsActivity.dialogStoriesCell.setVisibility(8);
                            }
                            if (!z2) {
                                DialogsActivity.this.setScrollY(0.0f);
                                DialogsActivity.this.scrollAdditionalOffset = AndroidUtilities.m1146dp(81.0f);
                            } else {
                                DialogsActivity.this.scrollAdditionalOffset = -AndroidUtilities.m1146dp(81.0f);
                                DialogsActivity.this.setScrollY(-r3.getMaxScrollYOffset());
                            }
                            for (int i3 = 0; i3 < DialogsActivity.this.viewPages.length; i3++) {
                                if (DialogsActivity.this.viewPages[i3] != null) {
                                    DialogsActivity.this.viewPages[i3].listView.requestLayout();
                                }
                            }
                            View view2 = DialogsActivity.this.fragmentView;
                            if (view2 != null) {
                                view2.requestLayout();
                            }
                        }
                    });
                    this.storiesVisibilityAnimator.setDuration(200L);
                    this.storiesVisibilityAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
                    this.storiesVisibilityAnimator.start();
                    return;
                }
                this.progressToShowStories = z2 ? 1.0f : 0.0f;
                this.hasStories = z2;
                DialogStoriesCell dialogStoriesCell = this.dialogStoriesCell;
                if ((z2 || this.hasOnlySlefStories) && !isInPreviewMode()) {
                    i2 = 0;
                }
                dialogStoriesCell.setVisibility(i2);
                if (!z2) {
                    setScrollY(0.0f);
                } else {
                    this.scrollAdditionalOffset = -AndroidUtilities.m1146dp(81.0f);
                    setScrollY(-getMaxScrollYOffset());
                }
                while (true) {
                    ViewPage[] viewPageArr = this.viewPages;
                    if (i >= viewPageArr.length) {
                        break;
                    }
                    ViewPage viewPage = viewPageArr[i];
                    if (viewPage != null) {
                        viewPage.listView.requestLayout();
                    }
                    i++;
                }
                View view2 = this.fragmentView;
                if (view2 != null) {
                    view2.requestLayout();
                    this.fragmentView.invalidate();
                }
            }
        }
    }

    public void createSearchViewPager() {
        int i;
        SearchViewPager searchViewPager = this.searchViewPager;
        if ((searchViewPager != null && searchViewPager.getParent() == this.fragmentView) || this.fragmentView == null || getContext() == null) {
            return;
        }
        if (this.searchString != null) {
            i = 2;
        } else {
            i = !this.onlySelect ? 1 : 0;
        }
        SearchViewPager searchViewPager2 = new SearchViewPager(getContext(), this, i, this.initialDialogsType, this.folderId, new SearchViewPager.ChatPreviewDelegate() { // from class: org.telegram.ui.DialogsActivity.57
            @Override // org.telegram.ui.Components.SearchViewPager.ChatPreviewDelegate
            public void startChatPreview(RecyclerListView recyclerListView, DialogCell dialogCell) {
                DialogsActivity.this.showChatPreview(dialogCell);
            }

            @Override // org.telegram.ui.Components.SearchViewPager.ChatPreviewDelegate
            public void move(float f) {
                Point point = AndroidUtilities.displaySize;
                if (point.x > point.y) {
                    DialogsActivity.this.movePreviewFragment(f);
                }
            }

            @Override // org.telegram.ui.Components.SearchViewPager.ChatPreviewDelegate
            public void finish() {
                Point point = AndroidUtilities.displaySize;
                if (point.x > point.y) {
                    DialogsActivity.this.finishPreviewFragment();
                }
            }
        }) { // from class: org.telegram.ui.DialogsActivity.58
            @Override // org.telegram.p023ui.Components.ViewPagerFixed
            protected boolean onBackProgress(float f) {
                return false;
            }

            @Override // org.telegram.p023ui.Components.ViewPagerFixed
            protected void onTabPageSelected(int i2) {
                DialogsActivity.this.updateSpeedItem(i2 == 2);
            }

            @Override // org.telegram.p023ui.Components.SearchViewPager
            protected boolean includeDownloads() {
                RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
                return rightSlidingDialogContainer == null || !rightSlidingDialogContainer.hasFragment();
            }
        };
        this.searchViewPager = searchViewPager2;
        ((ContentView) this.fragmentView).addView(searchViewPager2, this.searchViewPagerIndex);
        this.searchViewPager.dialogsSearchAdapter.setDelegate(new C503959());
        this.searchViewPager.channelsSearchListView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda75
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ boolean hasDoubleTap(View view, int i2) {
                return RecyclerListView.OnItemClickListenerExtended.CC.$default$hasDoubleTap(this, view, i2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ void onDoubleTap(View view, int i2, float f, float f2) {
                RecyclerListView.OnItemClickListenerExtended.CC.$default$onDoubleTap(this, view, i2, f, f2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i2, float f, float f2) {
                this.f$0.lambda$createSearchViewPager$144(view, i2, f, f2);
            }
        });
        this.searchViewPager.botsSearchListView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda76
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ boolean hasDoubleTap(View view, int i2) {
                return RecyclerListView.OnItemClickListenerExtended.CC.$default$hasDoubleTap(this, view, i2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ void onDoubleTap(View view, int i2, float f, float f2) {
                RecyclerListView.OnItemClickListenerExtended.CC.$default$onDoubleTap(this, view, i2, f, f2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i2, float f, float f2) {
                this.f$0.lambda$createSearchViewPager$145(view, i2, f, f2);
            }
        });
        this.searchViewPager.hashtagSearchListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda77
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i2) {
                this.f$0.lambda$createSearchViewPager$146(view, i2);
            }
        });
        this.searchViewPager.botsSearchListView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda78
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener
            public final boolean onItemClick(View view, int i2) {
                return this.f$0.lambda$createSearchViewPager$148(view, i2);
            }
        });
        this.searchViewPager.searchListView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda79
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ boolean hasDoubleTap(View view, int i2) {
                return RecyclerListView.OnItemClickListenerExtended.CC.$default$hasDoubleTap(this, view, i2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public /* synthetic */ void onDoubleTap(View view, int i2, float f, float f2) {
                RecyclerListView.OnItemClickListenerExtended.CC.$default$onDoubleTap(this, view, i2, f, f2);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
            public final void onItemClick(View view, int i2, float f, float f2) {
                this.f$0.lambda$createSearchViewPager$149(view, i2, f, f2);
            }
        });
        this.searchViewPager.searchListView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListenerExtended() { // from class: org.telegram.ui.DialogsActivity.60
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
            public boolean onItemClick(View view, int i2, float f, float f2) {
                if (view instanceof ProfileSearchCell) {
                    ProfileSearchCell profileSearchCell = (ProfileSearchCell) view;
                    if (profileSearchCell.isBlocked()) {
                        DialogsActivity.this.showPremiumBlockedToast(view, profileSearchCell.getDialogId());
                        return true;
                    }
                }
                DialogsActivity dialogsActivity = DialogsActivity.this;
                return dialogsActivity.onItemLongClick(dialogsActivity.searchViewPager.searchListView, view, i2, f, f2, -1, DialogsActivity.this.searchViewPager.dialogsSearchAdapter);
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
            public void onMove(float f, float f2) {
                Point point = AndroidUtilities.displaySize;
                if (point.x > point.y) {
                    DialogsActivity.this.movePreviewFragment(f2);
                }
            }

            @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListenerExtended
            public void onLongClickRelease() {
                Point point = AndroidUtilities.displaySize;
                if (point.x > point.y) {
                    DialogsActivity.this.finishPreviewFragment();
                }
            }
        });
        this.searchViewPager.setFilteredSearchViewDelegate(new FilteredSearchView.Delegate() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda80
            @Override // org.telegram.ui.FilteredSearchView.Delegate
            public final void updateFiltersView(boolean z, ArrayList arrayList, ArrayList arrayList2, boolean z2) {
                this.f$0.lambda$createSearchViewPager$150(z, arrayList, arrayList2, z2);
            }
        });
        this.searchViewPager.setVisibility(8);
    }

    /* renamed from: org.telegram.ui.DialogsActivity$59 */
    class C503959 implements DialogsSearchAdapter.DialogsSearchAdapterDelegate {
        C503959() {
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void searchStateChanged(boolean z, boolean z2) {
            if (DialogsActivity.this.searchViewPager.emptyView.getVisibility() == 0) {
                z2 = true;
            }
            if (DialogsActivity.this.searching && DialogsActivity.this.searchWas && DialogsActivity.this.searchViewPager.emptyView != null) {
                if (z || DialogsActivity.this.searchViewPager.dialogsSearchAdapter.getItemCount() != 0) {
                    DialogsActivity.this.searchViewPager.emptyView.showProgress(true, z2);
                } else {
                    DialogsActivity.this.searchViewPager.emptyView.showProgress(false, z2);
                }
            }
            if (z && DialogsActivity.this.searchViewPager.dialogsSearchAdapter.getItemCount() == 0) {
                DialogsActivity.this.searchViewPager.cancelEnterAnimation();
            }
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void didPressedBlockedDialog(View view, long j) {
            DialogsActivity.this.showPremiumBlockedToast(view, j);
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void didPressedOnSubDialog(long j) {
            if (DialogsActivity.this.onlySelect) {
                if (DialogsActivity.this.validateSlowModeDialog(j)) {
                    if (!DialogsActivity.this.selectedDialogs.isEmpty()) {
                        DialogsActivity.this.findAndUpdateCheckBox(j, DialogsActivity.this.addOrRemoveSelectedDialog(j, null));
                        DialogsActivity.this.updateSelectedCount();
                        ((BaseFragment) DialogsActivity.this).actionBar.closeSearchField();
                        return;
                    }
                    DialogsActivity.this.didSelectResult(j, 0L, true, false);
                    return;
                }
                return;
            }
            Bundle bundle = new Bundle();
            if (DialogObject.isUserDialog(j)) {
                bundle.putLong("user_id", j);
            } else {
                bundle.putLong("chat_id", -j);
            }
            DialogsActivity.this.closeSearch();
            if (AndroidUtilities.isTablet() && DialogsActivity.this.viewPages != null) {
                for (int i = 0; i < DialogsActivity.this.viewPages.length; i++) {
                    DialogsAdapter dialogsAdapter = DialogsActivity.this.viewPages[i].dialogsAdapter;
                    DialogsActivity.this.openedDialogId.dialogId = j;
                    dialogsAdapter.setOpenedDialogId(j);
                }
                DialogsActivity.this.updateVisibleRows(MessagesController.UPDATE_MASK_SELECT_DIALOG);
            }
            if (DialogsActivity.this.searchString != null) {
                if (DialogsActivity.this.getMessagesController().checkCanOpenChat(bundle, DialogsActivity.this)) {
                    DialogsActivity.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.closeChats, new Object[0]);
                    DialogsActivity.this.presentFragment(new ChatActivity(bundle));
                    return;
                }
                return;
            }
            if (DialogsActivity.this.getMessagesController().checkCanOpenChat(bundle, DialogsActivity.this)) {
                DialogsActivity.this.presentFragment(new ChatActivity(bundle));
            }
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void needRemoveHint(final long j) {
            TLRPC.User user;
            if (DialogsActivity.this.getParentActivity() == null || (user = DialogsActivity.this.getMessagesController().getUser(Long.valueOf(j))) == null) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(DialogsActivity.this.getParentActivity());
            builder.setTitle(LocaleController.getString(C2369R.string.ChatHintsDeleteAlertTitle));
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("ChatHintsDeleteAlert", C2369R.string.ChatHintsDeleteAlert, ContactsController.formatName(user.first_name, user.last_name))));
            builder.setPositiveButton(LocaleController.getString(C2369R.string.StickersRemove), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$59$$ExternalSyntheticLambda2
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    this.f$0.lambda$needRemoveHint$0(j, alertDialog, i);
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
            AlertDialog alertDialogCreate = builder.create();
            DialogsActivity.this.showDialog(alertDialogCreate);
            TextView textView = (TextView) alertDialogCreate.getButton(-1);
            if (textView != null) {
                textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$needRemoveHint$0(long j, AlertDialog alertDialog, int i) {
            DialogsActivity.this.getMediaDataController().removePeer(j);
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void needClearList() {
            AlertDialog.Builder builder = new AlertDialog.Builder(DialogsActivity.this.getParentActivity());
            if (DialogsActivity.this.searchViewPager.dialogsSearchAdapter.isSearchWas() && DialogsActivity.this.searchViewPager.dialogsSearchAdapter.isRecentSearchDisplayed()) {
                builder.setTitle(LocaleController.getString(C2369R.string.ClearSearchAlertPartialTitle));
                builder.setMessage(LocaleController.formatPluralString("ClearSearchAlertPartial", DialogsActivity.this.searchViewPager.dialogsSearchAdapter.getRecentResultsCount(), new Object[0]));
                builder.setPositiveButton(LocaleController.getString(C2369R.string.Clear), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$59$$ExternalSyntheticLambda0
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i) {
                        this.f$0.lambda$needClearList$1(alertDialog, i);
                    }
                });
            } else {
                builder.setTitle(LocaleController.getString(C2369R.string.ClearSearchAlertTitle));
                builder.setMessage(LocaleController.getString(C2369R.string.ClearSearchAlert));
                builder.setPositiveButton(LocaleController.getString(C2369R.string.ClearButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$59$$ExternalSyntheticLambda1
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i) {
                        this.f$0.lambda$needClearList$2(alertDialog, i);
                    }
                });
            }
            builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
            AlertDialog alertDialogCreate = builder.create();
            DialogsActivity.this.showDialog(alertDialogCreate);
            TextView textView = (TextView) alertDialogCreate.getButton(-1);
            if (textView != null) {
                textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$needClearList$1(AlertDialog alertDialog, int i) {
            DialogsActivity.this.searchViewPager.dialogsSearchAdapter.clearRecentSearch();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$needClearList$2(AlertDialog alertDialog, int i) {
            if (DialogsActivity.this.searchViewPager.dialogsSearchAdapter.isRecentSearchDisplayed()) {
                DialogsActivity.this.searchViewPager.dialogsSearchAdapter.clearRecentSearch();
            } else {
                DialogsActivity.this.searchViewPager.dialogsSearchAdapter.clearRecentHashtags();
            }
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public void runResultsEnterAnimation() {
            if (DialogsActivity.this.searchViewPager != null) {
                DialogsActivity.this.searchViewPager.runResultsEnterAnimation();
            }
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public boolean isSelected(long j) {
            return DialogsActivity.this.selectedDialogs.contains(Long.valueOf(j));
        }

        @Override // org.telegram.ui.Adapters.DialogsSearchAdapter.DialogsSearchAdapterDelegate
        public long getSearchForumDialogId() {
            RightSlidingDialogContainer rightSlidingDialogContainer = DialogsActivity.this.rightSlidingDialogContainer;
            if (rightSlidingDialogContainer == null || !(rightSlidingDialogContainer.getFragment() instanceof TopicsFragment)) {
                return 0L;
            }
            return ((TopicsFragment) DialogsActivity.this.rightSlidingDialogContainer.getFragment()).getDialogId();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSearchViewPager$144(View view, int i, float f, float f2) {
        Object object = this.searchViewPager.channelsSearchAdapter.getObject(i);
        if (object instanceof TLRPC.Chat) {
            Bundle bundle = new Bundle();
            bundle.putLong("chat_id", ((TLRPC.Chat) object).f1571id);
            ChatActivity chatActivity = new ChatActivity(bundle);
            chatActivity.setNextChannels(this.searchViewPager.channelsSearchAdapter.getNextChannels(i));
            presentFragment(chatActivity);
            return;
        }
        if (object instanceof MessageObject) {
            MessageObject messageObject = (MessageObject) object;
            Bundle bundle2 = new Bundle();
            if (messageObject.getDialogId() >= 0) {
                bundle2.putLong("user_id", messageObject.getDialogId());
            } else {
                bundle2.putLong("chat_id", -messageObject.getDialogId());
            }
            bundle2.putInt("message_id", messageObject.getId());
            presentFragment(highlightFoundQuote(new ChatActivity(bundle2), messageObject));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSearchViewPager$145(View view, int i, float f, float f2) {
        Object object = this.searchViewPager.botsSearchAdapter.getObject(i);
        if (object instanceof TLRPC.User) {
            presentFragment(ProfileActivity.m1314of(((TLRPC.User) object).f1734id));
            return;
        }
        if (object instanceof MessageObject) {
            MessageObject messageObject = (MessageObject) object;
            Bundle bundle = new Bundle();
            if (messageObject.getDialogId() >= 0) {
                bundle.putLong("user_id", messageObject.getDialogId());
            } else {
                bundle.putLong("chat_id", -messageObject.getDialogId());
            }
            bundle.putInt("message_id", messageObject.getId());
            presentFragment(highlightFoundQuote(new ChatActivity(bundle), messageObject));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSearchViewPager$146(View view, int i) {
        Object obj = this.searchViewPager.hashtagSearchAdapter.getItem(i).object;
        if (obj instanceof MessageObject) {
            MessageObject messageObject = (MessageObject) obj;
            Bundle bundle = new Bundle();
            if (messageObject.getDialogId() >= 0) {
                bundle.putLong("user_id", messageObject.getDialogId());
            } else {
                bundle.putLong("chat_id", -messageObject.getDialogId());
            }
            bundle.putInt("message_id", messageObject.getId());
            presentFragment(highlightFoundQuote(new ChatActivity(bundle), messageObject));
            return;
        }
        if (obj instanceof StoriesController.SearchStoriesList) {
            StoriesController.SearchStoriesList searchStoriesList = (StoriesController.SearchStoriesList) obj;
            Bundle bundle2 = new Bundle();
            bundle2.putInt(PluginsConstants.Settings.TYPE, 3);
            bundle2.putString("hashtag", searchStoriesList.query);
            bundle2.putInt("storiesCount", searchStoriesList.getCount());
            presentFragment(new MediaActivity(bundle2, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createSearchViewPager$148(View view, int i) {
        Object topPeerObject = this.searchViewPager.botsSearchAdapter.getTopPeerObject(i);
        if (topPeerObject instanceof TLRPC.User) {
            final TLRPC.User user = (TLRPC.User) topPeerObject;
            new AlertDialog.Builder(getContext(), this.resourceProvider).setTitle(LocaleController.getString(C2369R.string.AppsClearSearch)).setMessage(LocaleController.formatString(C2369R.string.AppsClearSearchAlert, "\"" + UserObject.getUserName(user) + "\"")).setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null).setPositiveButton(LocaleController.getString(C2369R.string.Remove), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda119
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i2) {
                    this.f$0.lambda$createSearchViewPager$147(user, alertDialog, i2);
                }
            }).makeRed(-1).show();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSearchViewPager$147(TLRPC.User user, AlertDialog alertDialog, int i) {
        getMediaDataController().removeWebapp(user.f1734id);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSearchViewPager$149(View view, int i, float f, float f2) {
        Object item = this.searchViewPager.dialogsSearchAdapter.getItem(i);
        if (item instanceof TLRPC.TL_sponsoredPeer) {
            TLRPC.TL_sponsoredPeer tL_sponsoredPeer = (TLRPC.TL_sponsoredPeer) item;
            presentFragment(ChatActivity.m1258of(DialogObject.getPeerDialogId(tL_sponsoredPeer.peer)));
            this.searchViewPager.dialogsSearchAdapter.clickedSponsoredPeer(tL_sponsoredPeer);
            return;
        }
        if (view instanceof ProfileSearchCell) {
            ProfileSearchCell profileSearchCell = (ProfileSearchCell) view;
            if (profileSearchCell.isBlocked()) {
                showPremiumBlockedToast(view, profileSearchCell.getDialogId());
                return;
            }
        }
        if (this.initialDialogsType == 10) {
            SearchViewPager searchViewPager = this.searchViewPager;
            onItemLongClick(searchViewPager.searchListView, view, i, f, f2, -1, searchViewPager.dialogsSearchAdapter);
        } else {
            onItemClick(view, i, this.searchViewPager.dialogsSearchAdapter, f, f2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createSearchViewPager$150(boolean z, ArrayList arrayList, ArrayList arrayList2, boolean z2) {
        updateFiltersView(z, arrayList, arrayList2, z2, true);
    }

    public boolean clickSelectsDialog() {
        return this.initialDialogsType == 10;
    }

    public void openSetAvatar() {
        try {
            ((RLottieDrawable) ((AvatarDrawable) this.dialogsHintCell.imageView.getImageReceiver().getStaticThumb()).getCustomIcon()).restart(true);
        } catch (Exception unused) {
        }
        if (this.imageUpdater == null) {
            ImageUpdater imageUpdater = new ImageUpdater(true, 0, true);
            this.imageUpdater = imageUpdater;
            imageUpdater.setOpenWithFrontfaceCamera(true);
            ImageUpdater imageUpdater2 = this.imageUpdater;
            imageUpdater2.parentFragment = this;
            imageUpdater2.setDelegate(new C504261());
            getMediaDataController().checkFeaturedStickers();
            getMessagesController().loadSuggestedFilters();
            getMessagesController().loadUserInfo(getUserConfig().getCurrentUser(), true, this.classGuid);
        }
        TLRPC.User user = MessagesController.getInstance(this.currentAccount).getUser(Long.valueOf(UserConfig.getInstance(this.currentAccount).getClientUserId()));
        if (user == null) {
            user = UserConfig.getInstance(this.currentAccount).getCurrentUser();
        }
        if (user == null) {
            return;
        }
        this.imageUpdater.updateColors();
        ImageUpdater imageUpdater3 = this.imageUpdater;
        TLRPC.UserProfilePhoto userProfilePhoto = user.photo;
        imageUpdater3.openMenu((userProfilePhoto == null || userProfilePhoto.photo_big == null || (userProfilePhoto instanceof TLRPC.TL_userProfilePhotoEmpty)) ? false : true, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda61
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$openSetAvatar$151();
            }
        }, new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda62
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                this.f$0.lambda$openSetAvatar$152(dialogInterface);
            }
        }, 0);
    }

    /* renamed from: org.telegram.ui.DialogsActivity$61 */
    /* loaded from: classes5.dex */
    class C504261 implements ImageUpdater.ImageUpdaterDelegate {
        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ boolean canFinishFragment() {
            return ImageUpdater.ImageUpdaterDelegate.CC.$default$canFinishFragment(this);
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ void didUploadFailed() {
            ImageUpdater.ImageUpdaterDelegate.CC.$default$didUploadFailed(this);
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public /* synthetic */ String getInitialSearchString() {
            return ImageUpdater.ImageUpdaterDelegate.CC.$default$getInitialSearchString(this);
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public boolean supportsBulletin() {
            return true;
        }

        C504261() {
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public void didStartUpload(boolean z, boolean z2) {
            if (DialogsActivity.this.uploadingAvatarBulletin != null) {
                DialogsActivity.this.uploadingAvatarBulletin.hide();
                DialogsActivity.this.uploadingAvatarBulletin = null;
            }
            Bulletin.ProgressLayout progressLayout = new Bulletin.ProgressLayout(DialogsActivity.this.getContext(), ((BaseFragment) DialogsActivity.this).resourceProvider);
            if (z) {
                progressLayout.imageView.setImageBitmap(DialogsActivity.this.imageUpdater.getPreviewBitmap());
            } else {
                progressLayout.imageView.setImageBitmap(PhotoViewer.getInstance().centerImage.getBitmap());
            }
            progressLayout.setButton(new Bulletin.UndoButton(DialogsActivity.this.getContext(), true, ((BaseFragment) DialogsActivity.this).resourceProvider).setText(LocaleController.getString(C2369R.string.ViewAction)).setUndoAction(new Runnable() { // from class: org.telegram.ui.DialogsActivity$61$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.openAvatarInProfile();
                }
            }));
            progressLayout.getButton().setVisibility(8);
            progressLayout.textView.setText(LocaleController.getString(z2 ? C2369R.string.YourProfileVideoUploading : C2369R.string.YourProfilePhotoUploading), true);
            DialogsActivity dialogsActivity = DialogsActivity.this;
            dialogsActivity.uploadingAvatarBulletin = BulletinFactory.m1267of(dialogsActivity).create(progressLayout, -1);
            DialogsActivity.this.uploadingAvatarBulletin.hideAfterBottomSheet = false;
            DialogsActivity.this.uploadingAvatarBulletin.setCanHide(false);
            DialogsActivity.this.uploadingAvatarBulletin.skipShowAnimation();
            DialogsActivity.this.uploadingAvatarBulletin.show();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void openAvatarInProfile() {
            Bundle bundle = new Bundle();
            bundle.putLong("user_id", UserConfig.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getClientUserId());
            bundle.putBoolean("my_profile", true);
            DialogsActivity.this.presentFragment(new ProfileActivity(bundle, null));
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public PhotoViewer.PlaceProviderObject getCloseIntoObject() {
            if (DialogsActivity.this.uploadingAvatarBulletin == null) {
                return null;
            }
            Bulletin.ProgressLayout progressLayout = (Bulletin.ProgressLayout) DialogsActivity.this.uploadingAvatarBulletin.getLayout();
            PhotoViewer.PlaceProviderObject placeProviderObject = new PhotoViewer.PlaceProviderObject();
            int[] iArr = new int[2];
            progressLayout.imageView.getLocationInWindow(iArr);
            placeProviderObject.viewX = iArr[0];
            placeProviderObject.viewY = iArr[1];
            placeProviderObject.parentView = DialogsActivity.this.fragmentView;
            ImageReceiver imageReceiver = progressLayout.imageView.getImageReceiver();
            placeProviderObject.imageReceiver = imageReceiver;
            placeProviderObject.thumb = imageReceiver.getBitmapSafe();
            placeProviderObject.clipBottomAddition = 0;
            placeProviderObject.radius = placeProviderObject.imageReceiver.getRoundRadius();
            placeProviderObject.scale = progressLayout.imageView.getScaleX();
            return placeProviderObject;
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public void onUploadProgressChanged(float f) {
            if (DialogsActivity.this.uploadingAvatarBulletin != null) {
                ((Bulletin.ProgressLayout) DialogsActivity.this.uploadingAvatarBulletin.getLayout()).setProgress(f * 0.9f);
            }
        }

        @Override // org.telegram.ui.Components.ImageUpdater.ImageUpdaterDelegate
        public void didUploadPhoto(final TLRPC.InputFile inputFile, final TLRPC.InputFile inputFile2, final double d, final String str, final TLRPC.PhotoSize photoSize, final TLRPC.PhotoSize photoSize2, final boolean z, final TLRPC.VideoSize videoSize) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$61$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didUploadPhoto$2(inputFile, inputFile2, videoSize, d, str, z, photoSize2, photoSize);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didUploadPhoto$1(final String str, final boolean z, final TLObject tLObject, final TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.DialogsActivity$61$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$didUploadPhoto$0(tL_error, tLObject, str, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didUploadPhoto$0(TLRPC.TL_error tL_error, TLObject tLObject, String str, boolean z) {
            if (tL_error == null) {
                TLRPC.User user = DialogsActivity.this.getMessagesController().getUser(Long.valueOf(DialogsActivity.this.getUserConfig().getClientUserId()));
                if (user == null) {
                    user = DialogsActivity.this.getUserConfig().getCurrentUser();
                    if (user == null) {
                        return;
                    } else {
                        DialogsActivity.this.getMessagesController().putUser(user, false);
                    }
                } else {
                    DialogsActivity.this.getUserConfig().setCurrentUser(user);
                }
                TLRPC.TL_photos_photo tL_photos_photo = (TLRPC.TL_photos_photo) tLObject;
                ArrayList arrayList = tL_photos_photo.photo.sizes;
                TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, 150);
                TLRPC.PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(arrayList, 800);
                TLRPC.VideoSize closestVideoSizeWithSize = tL_photos_photo.photo.video_sizes.isEmpty() ? null : FileLoader.getClosestVideoSizeWithSize(tL_photos_photo.photo.video_sizes, MediaDataController.MAX_STYLE_RUNS_COUNT);
                TLRPC.TL_userProfilePhoto tL_userProfilePhoto = new TLRPC.TL_userProfilePhoto();
                user.photo = tL_userProfilePhoto;
                tL_userProfilePhoto.photo_id = tL_photos_photo.photo.f1603id;
                if (closestPhotoSizeWithSize != null) {
                    tL_userProfilePhoto.photo_small = closestPhotoSizeWithSize.location;
                }
                if (closestPhotoSizeWithSize2 != null) {
                    tL_userProfilePhoto.photo_big = closestPhotoSizeWithSize2.location;
                }
                if (closestPhotoSizeWithSize != null && DialogsActivity.this.avatar != null) {
                    FileLoader.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getPathToAttach(DialogsActivity.this.avatar, true).renameTo(FileLoader.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getPathToAttach(closestPhotoSizeWithSize, true));
                    ImageLoader.getInstance().replaceImageInCache(DialogsActivity.this.avatar.volume_id + "_" + DialogsActivity.this.avatar.local_id + "@50_50", closestPhotoSizeWithSize.location.volume_id + "_" + closestPhotoSizeWithSize.location.local_id + "@50_50", ImageLocation.getForUserOrChat(user, 1), false);
                }
                if (closestVideoSizeWithSize != null && str != null) {
                    new File(str).renameTo(FileLoader.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getPathToAttach(closestVideoSizeWithSize, "mp4", true));
                } else if (closestPhotoSizeWithSize2 != null && DialogsActivity.this.avatarBig != null) {
                    FileLoader.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getPathToAttach(DialogsActivity.this.avatarBig, true).renameTo(FileLoader.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).getPathToAttach(closestPhotoSizeWithSize2, true));
                }
                DialogsActivity.this.getMessagesController().getDialogPhotos(user.f1734id).addPhotoAtStart(tL_photos_photo.photo);
                ArrayList arrayList2 = new ArrayList();
                arrayList2.add(user);
                DialogsActivity.this.getMessagesStorage().putUsersAndChats(arrayList2, null, false, true);
                TLRPC.UserFull userFull = DialogsActivity.this.getMessagesController().getUserFull(DialogsActivity.this.getUserConfig().getClientUserId());
                if (userFull != null) {
                    userFull.profile_photo = tL_photos_photo.photo;
                    DialogsActivity.this.getMessagesStorage().updateUserInfo(userFull, false);
                }
            }
            DialogsActivity.this.avatar = null;
            DialogsActivity.this.avatarBig = null;
            DialogsActivity.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_ALL));
            DialogsActivity.this.getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
            DialogsActivity.this.getUserConfig().saveConfig(true);
            MessagesController.getInstance(((BaseFragment) DialogsActivity.this).currentAccount).removeSuggestion(0L, "USERPIC_SETUP");
            TransitionManager.beginDelayedTransition((ViewGroup) DialogsActivity.this.dialogsHintCell.getParent(), new ChangeBounds().setDuration(200L));
            DialogsActivity.this.updateDialogsHint();
            if (DialogsActivity.this.uploadingAvatarBulletin != null) {
                Bulletin.ProgressLayout progressLayout = (Bulletin.ProgressLayout) DialogsActivity.this.uploadingAvatarBulletin.getLayout();
                progressLayout.textView.setText(LocaleController.getString(z ? C2369R.string.YourProfileVideoDone : C2369R.string.YourProfilePhotoDone), true);
                progressLayout.setProgress(1.0f);
                Bulletin.Button button = progressLayout.getButton();
                button.setScaleX(0.6f);
                button.setScaleY(0.6f);
                button.setAlpha(0.0f);
                button.setVisibility(0);
                button.animate().scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(360L).start();
                DialogsActivity.this.uploadingAvatarBulletin.setDuration(5000);
                DialogsActivity.this.uploadingAvatarBulletin.setCanHide(false);
                DialogsActivity.this.uploadingAvatarBulletin.setCanHide(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$didUploadPhoto$2(TLRPC.InputFile inputFile, TLRPC.InputFile inputFile2, TLRPC.VideoSize videoSize, double d, final String str, final boolean z, TLRPC.PhotoSize photoSize, TLRPC.PhotoSize photoSize2) {
            if (inputFile != null || inputFile2 != null || videoSize != null) {
                if (DialogsActivity.this.avatar == null) {
                    return;
                }
                TLRPC.TL_photos_uploadProfilePhoto tL_photos_uploadProfilePhoto = new TLRPC.TL_photos_uploadProfilePhoto();
                if (inputFile != null) {
                    tL_photos_uploadProfilePhoto.file = inputFile;
                    tL_photos_uploadProfilePhoto.flags |= 1;
                }
                if (inputFile2 != null) {
                    tL_photos_uploadProfilePhoto.video = inputFile2;
                    int i = tL_photos_uploadProfilePhoto.flags;
                    tL_photos_uploadProfilePhoto.video_start_ts = d;
                    tL_photos_uploadProfilePhoto.flags = i | 6;
                }
                if (videoSize != null) {
                    tL_photos_uploadProfilePhoto.video_emoji_markup = videoSize;
                    tL_photos_uploadProfilePhoto.flags |= 16;
                }
                DialogsActivity dialogsActivity = DialogsActivity.this;
                dialogsActivity.avatarUploadingRequest = dialogsActivity.getConnectionsManager().sendRequest(tL_photos_uploadProfilePhoto, new RequestDelegate() { // from class: org.telegram.ui.DialogsActivity$61$$ExternalSyntheticLambda2
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$didUploadPhoto$1(str, z, tLObject, tL_error);
                    }
                });
            } else {
                DialogsActivity.this.avatar = photoSize.location;
                DialogsActivity.this.avatarBig = photoSize2.location;
            }
            ((BaseFragment) DialogsActivity.this).actionBar.createMenu().requestLayout();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openSetAvatar$151() {
        MessagesController.getInstance(this.currentAccount).deleteUserPhoto(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openSetAvatar$152(DialogInterface dialogInterface) {
        if (this.imageUpdater.isUploadingImage()) {
            MessagesController.getInstance(this.currentAccount).removeSuggestion(0L, "USERPIC_SETUP");
            TransitionManager.beginDelayedTransition((ViewGroup) this.dialogsHintCell.getParent(), new ChangeBounds().setDuration(200L));
            updateDialogsHint();
        }
    }

    private void openWriteContacts() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("destroyAfterSelect", true);
        presentFragment(new ContactsActivity(bundle));
    }

    private void openStoriesRecorder() throws IOException {
        if (!this.storiesEnabled) {
            HintView2 hintView2 = this.storyPremiumHint;
            if (hintView2 != null) {
                if (hintView2.shown()) {
                    return;
                } else {
                    AndroidUtilities.removeFromParent(this.storyPremiumHint);
                }
            }
            HintView2 bgColor = new HintView2(getContext(), 2).setRounding(8.0f).setDuration(8000L).setCloseButton(true).setMultilineText(true).setMaxWidthPx(AndroidUtilities.displaySize.x - AndroidUtilities.m1146dp(148.0f)).setText(AndroidUtilities.replaceSingleTag(LocaleController.getString("StoriesPremiumHint2").replace('\n', ' '), Theme.key_undo_cancelColor, 0, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda54
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$openStoriesRecorder$153();
                }
            })).setJoint(1.0f, -40.0f).setBgColor(getThemedColor(Theme.key_undo_background));
            this.storyPremiumHint = bgColor;
            ((ViewGroup) this.fragmentView).addView(bgColor, LayoutHelper.createFrame(-1, 240.0f, 87, 12.0f, 0.0f, 72.0f, 56.0f));
            this.storyPremiumHint.show();
            return;
        }
        HintView2 hintView22 = this.storyHint;
        if (hintView22 != null) {
            hintView22.hide();
        }
        StoriesController.StoryLimit storyLimitCheckStoryLimit = MessagesController.getInstance(this.currentAccount).getStoriesController().checkStoryLimit();
        if (storyLimitCheckStoryLimit != null && storyLimitCheckStoryLimit.active(this.currentAccount, 1)) {
            showDialog(new LimitReachedBottomSheet(this, getContext(), storyLimitCheckStoryLimit.getLimitReachedType(), this.currentAccount, null));
        } else {
            StoryRecorder.getInstance(getParentActivity(), this.currentAccount).closeToWhenSent(new StoryRecorder.ClosingViewProvider() { // from class: org.telegram.ui.DialogsActivity.62
                @Override // org.telegram.ui.Stories.recorder.StoryRecorder.ClosingViewProvider
                public void preLayout(long j, final Runnable runnable) {
                    DialogsActivity dialogsActivity = DialogsActivity.this;
                    if (dialogsActivity.dialogStoriesCell != null) {
                        dialogsActivity.scrollToTop(false, true);
                        DialogsActivity.this.invalidateScrollY = true;
                        DialogsActivity.this.fragmentView.invalidate();
                        if (j == 0 || j == DialogsActivity.this.getUserConfig().getClientUserId()) {
                            DialogsActivity.this.dialogStoriesCell.scrollToFirstCell();
                        } else {
                            DialogsActivity.this.dialogStoriesCell.scrollTo(j);
                        }
                        DialogsActivity.this.viewPages[0].listView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: org.telegram.ui.DialogsActivity.62.1
                            @Override // android.view.ViewTreeObserver.OnPreDrawListener
                            public boolean onPreDraw() {
                                DialogsActivity.this.viewPages[0].listView.getViewTreeObserver().removeOnPreDrawListener(this);
                                AndroidUtilities.runOnUIThread(runnable, 100L);
                                return false;
                            }
                        });
                        return;
                    }
                    runnable.run();
                }

                @Override // org.telegram.ui.Stories.recorder.StoryRecorder.ClosingViewProvider
                public StoryRecorder.SourceView getView(long j) {
                    DialogStoriesCell dialogStoriesCell = DialogsActivity.this.dialogStoriesCell;
                    return StoryRecorder.SourceView.fromStoryCell(dialogStoriesCell != null ? dialogStoriesCell.findStoryCell(j) : null);
                }
            }).open(StoryRecorder.SourceView.fromFloatingButton(this.floatingButtonContainer), true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$openStoriesRecorder$153() {
        HintView2 hintView2 = this.storyPremiumHint;
        if (hintView2 != null) {
            hintView2.hide();
        }
        presentFragment(new PremiumPreviewFragment("stories"));
    }

    private void checkEmailConfig() {
        int iCheckEmailSuggestion = getMessagesController().checkEmailSuggestion();
        if (iCheckEmailSuggestion != 0) {
            presentFragment(new LoginActivity().changeEmail(new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda52
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$checkEmailConfig$154();
                }
            }, new Runnable() { // from class: org.telegram.ui.DialogsActivity$$ExternalSyntheticLambda53
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$checkEmailConfig$155();
                }
            }, iCheckEmailSuggestion == 2));
            getMessagesController().markEmailSuggestionAsShown();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkEmailConfig$154() {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourceProvider);
        lottieLayout.setAnimation(C2369R.raw.email_check_inbox, new String[0]);
        lottieLayout.textView.setText(LocaleController.getString(C2369R.string.YourLoginEmailChangedSuccess));
        Bulletin.make(this, lottieLayout, 2750).show();
        try {
            this.fragmentView.performHapticFeedback(3, 2);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkEmailConfig$155() {
        getMessagesController().removeSuggestion(0L, "SETUP_LOGIN_EMAIL");
    }
}
