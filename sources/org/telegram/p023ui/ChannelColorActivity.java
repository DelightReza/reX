package org.telegram.p023ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.util.Consumer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChannelBoostsController;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ChatThemeController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.EmojiThemes;
import org.telegram.p023ui.ActionBar.INavigationLayout;
import org.telegram.p023ui.ActionBar.SimpleTextView;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.ActionBar.ThemeColors;
import org.telegram.p023ui.Cells.ChatMessageCell;
import org.telegram.p023ui.Cells.HeaderCell;
import org.telegram.p023ui.Cells.TextCell;
import org.telegram.p023ui.Cells.TextInfoPrivacyCell;
import org.telegram.p023ui.Cells.ThemePreviewMessagesCell;
import org.telegram.p023ui.Cells.ThemesHorizontalListCell;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.AnimatedFloat;
import org.telegram.p023ui.Components.Bulletin;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.ButtonBounce;
import org.telegram.p023ui.Components.ChatThemeBottomSheet;
import org.telegram.p023ui.Components.ColoredImageSpan;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.Easings;
import org.telegram.p023ui.Components.FlickerLoadingView;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.RLottieDrawable;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.Text;
import org.telegram.p023ui.Components.ThemeSmallPreviewView;
import org.telegram.p023ui.PeerColorActivity;
import org.telegram.p023ui.SelectAnimatedEmojiDialog;
import org.telegram.p023ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.p023ui.Stories.recorder.PreviewView;
import org.telegram.p023ui.ThemePreviewActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.ResultCallback;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_account;
import org.telegram.tgnet.p022tl.TL_stars;
import org.telegram.tgnet.p022tl.TL_stories;

/* loaded from: classes5.dex */
public class ChannelColorActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    protected Adapter adapter;
    public Drawable backgroundDrawable;
    public TL_stories.TL_premium_boostsStatus boostsStatus;
    private BaseFragment bulletinFragment;
    protected ButtonWithCounterView button;
    protected FrameLayout buttonContainer;
    private View changeDayNightView;
    private ValueAnimator changeDayNightViewAnimator;
    private float changeDayNightViewProgress;
    private final SparseIntArray currentColors;
    public int currentLevel;
    public int currentProfileColor;
    public long currentProfileEmoji;
    public int currentReplyColor;
    public long currentReplyEmoji;
    public TLRPC.EmojiStatus currentStatusEmoji;
    public TLRPC.WallPaper currentWallpaper;
    private ActionBarMenuItem dayNightItem;
    public final long dialogId;
    private final Paint dividerPaint;
    private boolean forceDark;
    public TLRPC.WallPaper galleryWallpaper;
    private boolean isDark;
    protected boolean isGroup;
    protected GridLayoutManager layoutManager;
    protected RecyclerListView listView;
    private SpannableStringBuilder lock;
    protected int messagesPreviewRow;
    private final Theme.MessageDrawable msgInDrawable;
    private final Theme.MessageDrawable msgInDrawableSelected;
    private final Drawable msgOutCheckReadDrawable;
    private final Theme.MessageDrawable msgOutDrawable;
    private final Theme.MessageDrawable msgOutDrawableSelected;
    private final Drawable msgOutHalfCheckDrawable;
    protected int packEmojiHintRow;
    protected int packEmojiRow;
    protected int packStickerHintRow;
    protected int packStickerRow;
    private Theme.ResourcesProvider parentResourcesProvider;
    protected int profileColorGridRow;
    protected int profileEmojiRow;
    protected int profileHintRow;
    protected int profilePreviewRow;
    protected int removeProfileColorRow;
    protected int removeProfileColorShadowRow;
    protected int replyColorListRow;
    protected int replyEmojiRow;
    protected int replyHintRow;
    protected int rowsCount;
    private SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialog;
    public int selectedProfileColor;
    public long selectedProfileEmoji;
    public int selectedReplyColor;
    public long selectedReplyEmoji;
    public TLRPC.EmojiStatus selectedStatusEmoji;
    public TLRPC.WallPaper selectedWallpaper;
    protected int statusEmojiRow;
    protected int statusHintRow;
    private RLottieDrawable sunDrawable;
    protected int wallpaperHintRow;
    protected int wallpaperRow;
    protected int wallpaperThemesRow;

    protected int getEmojiPackInfoStrRes() {
        return 0;
    }

    protected int getEmojiPackStrRes() {
        return 0;
    }

    protected int getEmojiStickersLevelMin() {
        return 0;
    }

    protected int getMessagePreviewType() {
        return 3;
    }

    protected int getStickerPackInfoStrRes() {
        return 0;
    }

    protected int getStickerPackStrRes() {
        return 0;
    }

    protected boolean isForum() {
        return false;
    }

    protected boolean needBoostInfoSection() {
        return false;
    }

    protected void openBoostDialog(int i) {
    }

    public int minLevelRequired() {
        int iMax = 0;
        if (this.currentReplyColor != this.selectedReplyColor) {
            MessagesController.PeerColors peerColors = getMessagesController().peerColors;
            MessagesController.PeerColor color = peerColors == null ? null : peerColors.getColor(this.selectedReplyColor);
            if (color != null) {
                iMax = Math.max(0, color.getLvl(this.isGroup));
            }
        }
        if (this.currentReplyEmoji != this.selectedReplyEmoji) {
            iMax = Math.max(iMax, getMessagesController().channelBgIconLevelMin);
        }
        if (this.currentProfileColor != this.selectedProfileColor) {
            MessagesController.PeerColors peerColors2 = getMessagesController().profilePeerColors;
            MessagesController.PeerColor color2 = peerColors2 != null ? peerColors2.getColor(this.selectedProfileColor) : null;
            if (color2 != null) {
                iMax = Math.max(iMax, color2.getLvl(this.isGroup));
            }
        }
        if (this.currentProfileEmoji != this.selectedProfileEmoji) {
            iMax = Math.max(iMax, getProfileIconLevelMin());
        }
        if (!DialogObject.emojiStatusesEqual(this.currentStatusEmoji, this.selectedStatusEmoji)) {
            iMax = Math.max(iMax, getEmojiStatusLevelMin());
        }
        return !ChatThemeController.wallpaperEquals(this.currentWallpaper, this.selectedWallpaper) ? Math.max(iMax, getWallpaperLevelMin()) : iMax;
    }

    protected int getProfileIconLevelMin() {
        return getMessagesController().channelProfileIconLevelMin;
    }

    protected int getCustomWallpaperLevelMin() {
        return getMessagesController().channelCustomWallpaperLevelMin;
    }

    protected int getWallpaperLevelMin() {
        return getMessagesController().channelWallpaperLevelMin;
    }

    protected int getEmojiStatusLevelMin() {
        return getMessagesController().channelEmojiStatusLevelMin;
    }

    public void updateButton(boolean z) {
        if (this.button == null || this.boostsStatus == null) {
            return;
        }
        int iMinLevelRequired = minLevelRequired();
        if (this.currentLevel >= iMinLevelRequired) {
            this.button.setSubText(null, z);
            return;
        }
        if (this.lock == null) {
            this.lock = new SpannableStringBuilder("l");
            ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2369R.drawable.mini_switch_lock);
            coloredImageSpan.setTopOffset(1);
            this.lock.setSpan(coloredImageSpan, 0, 1, 33);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) this.lock).append((CharSequence) LocaleController.formatPluralString("BoostLevelRequired", iMinLevelRequired, new Object[0]));
        this.button.setSubText(spannableStringBuilder, z);
    }

    public class ThemeDelegate implements Theme.ResourcesProvider {
        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ void applyServiceShaderMatrix(int i, int i2, float f, float f2) {
            Theme.applyServiceShaderMatrix(i, i2, f, f2);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ ColorFilter getAnimatedEmojiColorFilter() {
            return Theme.chat_animatedEmojiTextColorFilter;
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ int getColorOrDefault(int i) {
            return getColor(i);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ int getCurrentColor(int i) {
            return getColor(i);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ boolean hasGradientService() {
            return Theme.ResourcesProvider.CC.$default$hasGradientService(this);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public /* synthetic */ void setAnimatedColor(int i, int i2) {
            Theme.ResourcesProvider.CC.$default$setAnimatedColor(this, i, i2);
        }

        public ThemeDelegate() {
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public int getColor(int i) {
            int iIndexOfKey = ChannelColorActivity.this.currentColors.indexOfKey(i);
            if (iIndexOfKey >= 0) {
                return ChannelColorActivity.this.currentColors.valueAt(iIndexOfKey);
            }
            if (ChannelColorActivity.this.parentResourcesProvider != null) {
                return ChannelColorActivity.this.parentResourcesProvider.getColor(i);
            }
            return Theme.getColor(i);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public Drawable getDrawable(String str) {
            if (str.equals("drawableMsgIn")) {
                return ChannelColorActivity.this.msgInDrawable;
            }
            if (str.equals("drawableMsgInSelected")) {
                return ChannelColorActivity.this.msgInDrawableSelected;
            }
            if (str.equals("drawableMsgOut")) {
                return ChannelColorActivity.this.msgOutDrawable;
            }
            if (str.equals("drawableMsgOutSelected")) {
                return ChannelColorActivity.this.msgOutDrawableSelected;
            }
            if (str.equals("drawableMsgOutCheckRead")) {
                ChannelColorActivity.this.msgOutCheckReadDrawable.setColorFilter(getColor(Theme.key_chat_outSentCheckRead), PorterDuff.Mode.MULTIPLY);
                return ChannelColorActivity.this.msgOutCheckReadDrawable;
            }
            if (str.equals("drawableMsgOutHalfCheck")) {
                ChannelColorActivity.this.msgOutHalfCheckDrawable.setColorFilter(getColor(Theme.key_chat_outSentCheckRead), PorterDuff.Mode.MULTIPLY);
                return ChannelColorActivity.this.msgOutHalfCheckDrawable;
            }
            if (ChannelColorActivity.this.parentResourcesProvider != null) {
                return ChannelColorActivity.this.parentResourcesProvider.getDrawable(str);
            }
            return Theme.getThemeDrawable(str);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public Paint getPaint(String str) {
            if (str.equals("paintDivider")) {
                return ChannelColorActivity.this.dividerPaint;
            }
            return Theme.getThemePaint(str);
        }

        @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
        public boolean isDark() {
            return ChannelColorActivity.this.isDark;
        }

        public void toggle() throws IOException {
            ChannelColorActivity.this.isDark = !r0.isDark;
            ChannelColorActivity.this.updateThemeColors();
            ChannelColorActivity.this.updateColors();
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        getMediaDataController().loadRestrictedStatusEmojis();
        getNotificationCenter().addObserver(this, NotificationCenter.boostByChannelCreated);
        getNotificationCenter().addObserver(this, NotificationCenter.chatWasBoostedByUser);
        getNotificationCenter().addObserver(this, NotificationCenter.dialogDeleted);
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        getNotificationCenter().removeObserver(this, NotificationCenter.boostByChannelCreated);
        getNotificationCenter().removeObserver(this, NotificationCenter.chatWasBoostedByUser);
        getNotificationCenter().removeObserver(this, NotificationCenter.dialogDeleted);
        Bulletin.removeDelegate(this);
    }

    public ChannelColorActivity(long j) {
        boolean zIsCurrentThemeDark = Theme.isCurrentThemeDark();
        this.isDark = zIsCurrentThemeDark;
        this.rowsCount = 0;
        this.forceDark = zIsCurrentThemeDark;
        this.currentColors = new SparseIntArray();
        Paint paint = new Paint(1);
        this.dividerPaint = paint;
        paint.setStrokeWidth(1.0f);
        paint.setColor(Theme.getColor(Theme.key_divider, this.resourceProvider));
        this.msgOutCheckReadDrawable = ContextCompat.getDrawable(ApplicationLoader.applicationContext, C2369R.drawable.msg_check_s).mutate();
        this.msgOutHalfCheckDrawable = ContextCompat.getDrawable(ApplicationLoader.applicationContext, C2369R.drawable.msg_halfcheck).mutate();
        this.dialogId = j;
        final TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(-j));
        if (chat != null) {
            this.currentLevel = chat.level;
        }
        MessagesController.getInstance(this.currentAccount).getBoostsController().getBoostsStats(j, new Consumer() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda4
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$new$0(chat, (TL_stories.TL_premium_boostsStatus) obj);
            }
        });
        this.resourceProvider = new ThemeDelegate();
        this.msgInDrawable = new Theme.MessageDrawable(0, false, false, this.resourceProvider);
        this.msgInDrawableSelected = new Theme.MessageDrawable(0, false, true, this.resourceProvider);
        this.msgOutDrawable = new Theme.MessageDrawable(0, true, false, this.resourceProvider);
        this.msgOutDrawableSelected = new Theme.MessageDrawable(0, true, true, this.resourceProvider);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(TLRPC.Chat chat, TL_stories.TL_premium_boostsStatus tL_premium_boostsStatus) {
        this.boostsStatus = tL_premium_boostsStatus;
        if (tL_premium_boostsStatus != null) {
            int i = tL_premium_boostsStatus.level;
            this.currentLevel = i;
            if (chat != null) {
                chat.flags |= 1024;
                chat.level = i;
            }
        }
        updateButton(true);
        ButtonWithCounterView buttonWithCounterView = this.button;
        if (buttonWithCounterView != null) {
            buttonWithCounterView.setLoading(false);
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void setResourceProvider(Theme.ResourcesProvider resourcesProvider) {
        this.parentResourcesProvider = resourcesProvider;
    }

    protected void createListView() {
        this.listView = new RecyclerListView(getContext(), this.resourceProvider);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(-this.dialogId));
        if (chat != null) {
            int colorId = ChatObject.getColorId(chat);
            this.selectedReplyColor = colorId;
            this.currentReplyColor = colorId;
            long emojiId = ChatObject.getEmojiId(chat);
            this.selectedReplyEmoji = emojiId;
            this.currentReplyEmoji = emojiId;
            int profileColorId = ChatObject.getProfileColorId(chat);
            this.selectedProfileColor = profileColorId;
            this.currentProfileColor = profileColorId;
            long profileEmojiId = ChatObject.getProfileEmojiId(chat);
            this.selectedProfileEmoji = profileEmojiId;
            this.currentProfileEmoji = profileEmojiId;
            TLRPC.EmojiStatus emojiStatus = chat.emoji_status;
            this.selectedStatusEmoji = emojiStatus;
            this.currentStatusEmoji = emojiStatus;
        }
        final TLRPC.ChatFull chatFull = getMessagesController().getChatFull(-this.dialogId);
        if (chatFull != null) {
            TLRPC.WallPaper wallPaper = chatFull.wallpaper;
            this.selectedWallpaper = wallPaper;
            this.currentWallpaper = wallPaper;
            if (ChatThemeController.isNotEmoticonWallpaper(wallPaper)) {
                this.galleryWallpaper = this.currentWallpaper;
            }
        }
        this.actionBar.setBackButtonImage(C2369R.drawable.ic_ab_back);
        this.actionBar.setTitle(LocaleController.getString(C2369R.string.ChannelColorTitle2));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.ChannelColorActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i != -1) {
                    if (i == 1) {
                        ChannelColorActivity.this.toggleTheme();
                    }
                } else {
                    ChannelColorActivity channelColorActivity = ChannelColorActivity.this;
                    if (channelColorActivity.currentLevel >= channelColorActivity.minLevelRequired() && ChannelColorActivity.this.hasUnsavedChanged()) {
                        ChannelColorActivity.this.showUnsavedAlert();
                    } else {
                        ChannelColorActivity.this.lambda$onBackPressed$371();
                    }
                }
            }
        });
        RLottieDrawable rLottieDrawable = new RLottieDrawable(C2369R.raw.sun, "" + C2369R.raw.sun, AndroidUtilities.m1146dp(28.0f), AndroidUtilities.m1146dp(28.0f), true, null);
        this.sunDrawable = rLottieDrawable;
        rLottieDrawable.setPlayInDirectionOfCustomEndFrame(true);
        if (!this.isDark) {
            this.sunDrawable.setCustomEndFrame(0);
            this.sunDrawable.setCurrentFrame(0);
        } else {
            this.sunDrawable.setCurrentFrame(35);
            this.sunDrawable.setCustomEndFrame(36);
        }
        this.sunDrawable.beginApplyLayerColors();
        int color = Theme.getColor(Theme.key_chats_menuName, this.resourceProvider);
        this.sunDrawable.setLayerColor("Sunny.**", color);
        this.sunDrawable.setLayerColor("Path 6.**", color);
        this.sunDrawable.setLayerColor("Path.**", color);
        this.sunDrawable.setLayerColor("Path 5.**", color);
        this.dayNightItem = this.actionBar.createMenu().addItem(1, this.sunDrawable);
        FrameLayout frameLayout = new FrameLayout(context);
        updateRows();
        createListView();
        RecyclerListView recyclerListView = this.listView;
        Adapter adapter = new Adapter();
        this.adapter = adapter;
        recyclerListView.setAdapter(adapter);
        this.layoutManager = new GridLayoutManager(context, 3);
        this.listView.setLayoutManager(new LinearLayoutManager(context));
        RecyclerListView recyclerListView2 = this.listView;
        int i = Theme.key_windowBackgroundGray;
        recyclerListView2.setBackgroundColor(getThemedColor(i));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f, Opcodes.DNEG, 0.0f, 0.0f, 0.0f, 68.0f));
        this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i2) {
                this.f$0.lambda$createView$4(chatFull, view, i2);
            }
        });
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setDurations(350L);
        defaultItemAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        defaultItemAnimator.setDelayAnimations(false);
        defaultItemAnimator.setSupportsChangeAnimations(false);
        this.listView.setItemAnimator(defaultItemAnimator);
        ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context, this.resourceProvider);
        this.button = buttonWithCounterView;
        buttonWithCounterView.setText(LocaleController.getString(C2369R.string.ApplyChanges), false);
        this.button.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$5(view);
            }
        });
        updateButton(false);
        FrameLayout frameLayout2 = new FrameLayout(context);
        this.buttonContainer = frameLayout2;
        frameLayout2.setBackgroundColor(getThemedColor(i));
        this.buttonContainer.addView(this.button, LayoutHelper.createFrame(-1, 48.0f, 80, 10.0f, 10.0f, 10.0f, 10.0f));
        frameLayout.addView(this.buttonContainer, LayoutHelper.createFrame(-1, 68, 80));
        Bulletin.addDelegate(this, new Bulletin.Delegate() { // from class: org.telegram.ui.ChannelColorActivity.3
            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ boolean allowLayoutChanges() {
                return Bulletin.Delegate.CC.$default$allowLayoutChanges(this);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ boolean bottomOffsetAnimated() {
                return Bulletin.Delegate.CC.$default$bottomOffsetAnimated(this);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ boolean clipWithGradient(int i2) {
                return Bulletin.Delegate.CC.$default$clipWithGradient(this, i2);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ int getTopOffset(int i2) {
                return Bulletin.Delegate.CC.$default$getTopOffset(this, i2);
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
            public int getBottomOffset(int i2) {
                return ChannelColorActivity.this.buttonContainer.getMeasuredHeight();
            }
        });
        this.fragmentView = frameLayout;
        return frameLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$4(TLRPC.ChatFull chatFull, final View view, final int i) {
        long emojiStatusDocumentId = 0;
        if (view instanceof EmojiCell) {
            if (i == this.packStickerRow) {
                if (chatFull == null) {
                    return;
                }
                GroupStickersActivity groupStickersActivity = new GroupStickersActivity(-this.dialogId);
                groupStickersActivity.setInfo(chatFull);
                presentFragment(groupStickersActivity);
                return;
            }
            if (i == this.replyEmojiRow) {
                emojiStatusDocumentId = this.selectedReplyEmoji;
            } else if (i == this.profileEmojiRow) {
                emojiStatusDocumentId = this.selectedProfileEmoji;
            } else if (i == this.statusEmojiRow) {
                TLRPC.EmojiStatus emojiStatus = this.selectedStatusEmoji;
                if (emojiStatus instanceof TLRPC.TL_emojiStatusCollectible) {
                    emojiStatusDocumentId = ((TLRPC.TL_emojiStatusCollectible) emojiStatus).collectible_id;
                } else {
                    emojiStatusDocumentId = DialogObject.getEmojiStatusDocumentId(emojiStatus);
                }
            }
            if (i == this.packEmojiRow) {
                int emojiStickersLevelMin = getEmojiStickersLevelMin();
                TL_stories.TL_premium_boostsStatus tL_premium_boostsStatus = this.boostsStatus;
                if (tL_premium_boostsStatus != null && tL_premium_boostsStatus.level < emojiStickersLevelMin) {
                    openBoostDialog(29);
                    return;
                }
                GroupStickersActivity groupStickersActivity2 = new GroupStickersActivity(-this.dialogId, true);
                groupStickersActivity2.setInfo(chatFull);
                presentFragment(groupStickersActivity2);
                return;
            }
            EmojiCell emojiCell = (EmojiCell) view;
            showSelectStatusDialog(emojiCell, emojiStatusDocumentId, i == this.statusEmojiRow, new Utilities.Callback3() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda12
                @Override // org.telegram.messenger.Utilities.Callback3
                public final void run(Object obj, Object obj2, Object obj3) throws IOException {
                    this.f$0.lambda$createView$1(i, view, (Long) obj, (Integer) obj2, (TL_stars.TL_starGiftUnique) obj3);
                }
            }, this.selectedStatusEmoji instanceof TLRPC.TL_emojiStatusCollectible ? Theme.getColor(Theme.key_windowBackgroundWhiteBlueIcon, this.resourceProvider) : emojiCell.getColor());
            return;
        }
        if (i == this.removeProfileColorRow) {
            this.selectedProfileColor = -1;
            this.selectedProfileEmoji = 0L;
            if (this.selectedStatusEmoji instanceof TLRPC.TL_emojiStatusCollectible) {
                this.selectedStatusEmoji = null;
            }
            updateProfilePreview(true);
            updateButton(true);
            updateRows();
            return;
        }
        if (i == this.wallpaperRow) {
            ChatThemeBottomSheet.openGalleryForBackground(getParentActivity(), this, this.dialogId, this.resourceProvider, new Utilities.Callback() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda13
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) throws IOException {
                    this.f$0.lambda$createView$3((TLRPC.WallPaper) obj);
                }
            }, new ThemePreviewActivity.DayNightSwitchDelegate() { // from class: org.telegram.ui.ChannelColorActivity.2
                @Override // org.telegram.ui.ThemePreviewActivity.DayNightSwitchDelegate
                public boolean supportsAnimation() {
                    return false;
                }

                @Override // org.telegram.ui.ThemePreviewActivity.DayNightSwitchDelegate
                public boolean isDark() {
                    return ((BaseFragment) ChannelColorActivity.this).resourceProvider != null ? ((BaseFragment) ChannelColorActivity.this).resourceProvider.isDark() : Theme.isCurrentThemeDark();
                }

                @Override // org.telegram.ui.ThemePreviewActivity.DayNightSwitchDelegate
                public void switchDayNight(boolean z) throws IOException {
                    if (((BaseFragment) ChannelColorActivity.this).resourceProvider instanceof ThemeDelegate) {
                        ((ThemeDelegate) ((BaseFragment) ChannelColorActivity.this).resourceProvider).toggle();
                    }
                    ChannelColorActivity.this.setForceDark(isDark(), false);
                    ChannelColorActivity.this.updateColors();
                }
            }, this.boostsStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(int i, View view, Long l, Integer num, TL_stars.TL_starGiftUnique tL_starGiftUnique) throws IOException {
        if (i == this.replyEmojiRow) {
            this.selectedReplyEmoji = l.longValue();
            updateMessagesPreview(true);
        } else if (i == this.profileEmojiRow) {
            this.selectedProfileEmoji = l.longValue();
            updateProfilePreview(true);
        } else if (i == this.statusEmojiRow) {
            if (l.longValue() == 0) {
                this.selectedStatusEmoji = null;
            } else if (tL_starGiftUnique != null) {
                TLRPC.TL_emojiStatusCollectible tL_emojiStatusCollectibleEmojiStatusCollectibleFromGift = MessagesController.emojiStatusCollectibleFromGift(tL_starGiftUnique);
                if (num != null) {
                    tL_emojiStatusCollectibleEmojiStatusCollectibleFromGift.flags |= 1;
                    tL_emojiStatusCollectibleEmojiStatusCollectibleFromGift.until = num.intValue();
                }
                this.selectedStatusEmoji = tL_emojiStatusCollectibleEmojiStatusCollectibleFromGift;
                this.selectedProfileColor = -1;
                this.selectedProfileEmoji = 0L;
            } else {
                TLRPC.TL_emojiStatus tL_emojiStatus = new TLRPC.TL_emojiStatus();
                tL_emojiStatus.document_id = l.longValue();
                if (num != null) {
                    tL_emojiStatus.flags |= 1;
                    tL_emojiStatus.until = num.intValue();
                }
                this.selectedStatusEmoji = tL_emojiStatus;
            }
            updateProfilePreview(true);
        }
        updateButton(true);
        ((EmojiCell) view).setEmoji(l.longValue(), tL_starGiftUnique != null, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$3(TLRPC.WallPaper wallPaper) throws IOException {
        this.currentWallpaper = wallPaper;
        this.selectedWallpaper = wallPaper;
        this.galleryWallpaper = wallPaper;
        updateButton(false);
        updateMessagesPreview(false);
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$createView$2();
            }
        }, 350L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2() {
        BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.done, LocaleController.getString(C2369R.string.ChannelWallpaperUpdated)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$5(View view) {
        buttonClick();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onBackPressed() {
        if (this.currentLevel >= minLevelRequired() && hasUnsavedChanged()) {
            showUnsavedAlert();
            return false;
        }
        return super.onBackPressed();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean isSwipeBackEnabled(MotionEvent motionEvent) {
        return !hasUnsavedChanged() || this.currentLevel < minLevelRequired();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r15v2 */
    private void buttonClick() {
        boolean z;
        ?? r15;
        if (this.boostsStatus == null || this.button.isLoading()) {
            return;
        }
        if (this.currentLevel < minLevelRequired()) {
            this.button.setLoading(true);
            showLimit();
            return;
        }
        final int[] iArr = {0};
        final int[] iArr2 = {0};
        final boolean[] zArr = {false};
        final Utilities.Callback callback = new Utilities.Callback() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda7
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$buttonClick$7(zArr, iArr2, iArr, (TLRPC.TL_error) obj);
            }
        };
        TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(-this.dialogId));
        if (chat == null) {
            FileLog.m1158e("channel is null in ChannelColorAcitivity");
            BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.error, LocaleController.getString(C2369R.string.UnknownError)).show();
            return;
        }
        this.button.setLoading(true);
        if (this.currentReplyColor == this.selectedReplyColor && this.currentReplyEmoji == this.selectedReplyEmoji) {
            z = true;
            r15 = 0;
        } else {
            TLRPC.TL_channels_updateColor tL_channels_updateColor = new TLRPC.TL_channels_updateColor();
            tL_channels_updateColor.channel = getMessagesController().getInputChannel(-this.dialogId);
            tL_channels_updateColor.for_profile = false;
            if (chat.color == null) {
                chat.color = new TLRPC.TL_peerColor();
                chat.flags2 |= 128;
            }
            int i = tL_channels_updateColor.flags;
            tL_channels_updateColor.flags = i | 4;
            int i2 = this.selectedReplyColor;
            tL_channels_updateColor.color = i2;
            TLRPC.PeerColor peerColor = chat.color;
            int i3 = peerColor.flags;
            int i4 = i3 | 1;
            peerColor.flags = i4;
            peerColor.color = i2;
            z = true;
            r15 = 0;
            long j = this.selectedReplyEmoji;
            if (j != 0) {
                tL_channels_updateColor.flags = i | 5;
                tL_channels_updateColor.background_emoji_id = j;
                peerColor.flags = i3 | 3;
                peerColor.background_emoji_id = j;
            } else {
                peerColor.flags = i4 & (-3);
                peerColor.background_emoji_id = 0L;
            }
            iArr[0] = iArr[0] + 1;
            getConnectionsManager().sendRequest(tL_channels_updateColor, new RequestDelegate() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda8
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$buttonClick$8(callback, tLObject, tL_error);
                }
            });
        }
        if (this.currentProfileColor != this.selectedProfileColor || this.currentProfileEmoji != this.selectedProfileEmoji) {
            TLRPC.TL_channels_updateColor tL_channels_updateColor2 = new TLRPC.TL_channels_updateColor();
            tL_channels_updateColor2.channel = getMessagesController().getInputChannel(-this.dialogId);
            tL_channels_updateColor2.for_profile = z;
            if (chat.profile_color == null) {
                chat.profile_color = new TLRPC.TL_peerColor();
                chat.flags2 |= 256;
            }
            int i5 = this.selectedProfileColor;
            if (i5 >= 0) {
                tL_channels_updateColor2.flags |= 4;
                tL_channels_updateColor2.color = i5;
                TLRPC.PeerColor peerColor2 = chat.profile_color;
                peerColor2.flags |= 1;
                peerColor2.color = i5;
            } else {
                chat.profile_color.flags &= -2;
            }
            long j2 = this.selectedProfileEmoji;
            if (j2 != 0) {
                tL_channels_updateColor2.flags |= 1;
                tL_channels_updateColor2.background_emoji_id = j2;
                TLRPC.PeerColor peerColor3 = chat.profile_color;
                peerColor3.flags |= 2;
                peerColor3.background_emoji_id = j2;
            } else {
                TLRPC.PeerColor peerColor4 = chat.profile_color;
                peerColor4.flags &= -3;
                peerColor4.background_emoji_id = 0L;
            }
            iArr[r15] = iArr[r15] + 1;
            getConnectionsManager().sendRequest(tL_channels_updateColor2, new RequestDelegate() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda9
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$buttonClick$9(callback, tLObject, tL_error);
                }
            });
        }
        if (!ChatThemeController.wallpaperEquals(this.currentWallpaper, this.selectedWallpaper)) {
            TLRPC.TL_messages_setChatWallPaper tL_messages_setChatWallPaper = new TLRPC.TL_messages_setChatWallPaper();
            tL_messages_setChatWallPaper.peer = getMessagesController().getInputPeer(this.dialogId);
            TLRPC.WallPaper wallPaper = this.selectedWallpaper;
            if (wallPaper != null) {
                if (!TextUtils.isEmpty(ChatThemeController.getWallpaperEmoticon(wallPaper))) {
                    tL_messages_setChatWallPaper.flags |= 1;
                    TLRPC.TL_inputWallPaperNoFile tL_inputWallPaperNoFile = new TLRPC.TL_inputWallPaperNoFile();
                    tL_messages_setChatWallPaper.wallpaper = tL_inputWallPaperNoFile;
                    tL_inputWallPaperNoFile.f1653id = 0L;
                    tL_messages_setChatWallPaper.flags |= 4;
                    TLRPC.TL_wallPaperSettings tL_wallPaperSettings = new TLRPC.TL_wallPaperSettings();
                    tL_messages_setChatWallPaper.settings = tL_wallPaperSettings;
                    tL_wallPaperSettings.flags |= 128;
                    tL_wallPaperSettings.emoticon = ChatThemeController.getWallpaperEmoticon(this.selectedWallpaper);
                } else {
                    tL_messages_setChatWallPaper.flags |= 1;
                    TLRPC.WallPaper wallPaper2 = this.selectedWallpaper;
                    if (wallPaper2 instanceof TLRPC.TL_wallPaper) {
                        TLRPC.TL_inputWallPaper tL_inputWallPaper = new TLRPC.TL_inputWallPaper();
                        TLRPC.WallPaper wallPaper3 = this.selectedWallpaper;
                        tL_inputWallPaper.f1652id = wallPaper3.f1742id;
                        tL_inputWallPaper.access_hash = wallPaper3.access_hash;
                        tL_messages_setChatWallPaper.wallpaper = tL_inputWallPaper;
                    } else if (wallPaper2 instanceof TLRPC.TL_wallPaperNoFile) {
                        TLRPC.TL_inputWallPaperNoFile tL_inputWallPaperNoFile2 = new TLRPC.TL_inputWallPaperNoFile();
                        tL_inputWallPaperNoFile2.f1653id = this.selectedWallpaper.f1742id;
                        tL_messages_setChatWallPaper.wallpaper = tL_inputWallPaperNoFile2;
                    }
                }
            }
            iArr[r15] = iArr[r15] + 1;
            getConnectionsManager().sendRequest(tL_messages_setChatWallPaper, new RequestDelegate() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda10
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$buttonClick$10(callback, tLObject, tL_error);
                }
            });
            TLRPC.ChatFull chatFull = getMessagesController().getChatFull(-this.dialogId);
            ChatThemeController.getInstance(this.currentAccount).saveChatWallpaper(this.dialogId, this.selectedWallpaper);
            if (chatFull != null) {
                TLRPC.WallPaper wallPaper4 = this.selectedWallpaper;
                if (wallPaper4 == null) {
                    chatFull.flags2 &= -129;
                    chatFull.wallpaper = null;
                } else {
                    chatFull.flags2 |= 128;
                    chatFull.wallpaper = wallPaper4;
                }
                getMessagesController().putChatFull(chatFull);
                NotificationCenter notificationCenter = getNotificationCenter();
                int i6 = NotificationCenter.chatInfoDidLoad;
                Integer numValueOf = Integer.valueOf((int) r15);
                Object[] objArr = new Object[4];
                objArr[r15] = chatFull;
                objArr[1] = numValueOf;
                Boolean bool = Boolean.FALSE;
                objArr[2] = bool;
                objArr[3] = bool;
                notificationCenter.lambda$postNotificationNameOnUIThread$1(i6, objArr);
            }
        }
        if (!DialogObject.emojiStatusesEqual(this.currentStatusEmoji, this.selectedStatusEmoji)) {
            TLRPC.TL_channels_updateEmojiStatus tL_channels_updateEmojiStatus = new TLRPC.TL_channels_updateEmojiStatus();
            tL_channels_updateEmojiStatus.channel = getMessagesController().getInputChannel(-this.dialogId);
            TLRPC.EmojiStatus emojiStatus = this.selectedStatusEmoji;
            if (emojiStatus == null || (emojiStatus instanceof TLRPC.TL_emojiStatusEmpty)) {
                tL_channels_updateEmojiStatus.emoji_status = new TLRPC.TL_emojiStatusEmpty();
                chat.emoji_status = new TLRPC.TL_emojiStatusEmpty();
                chat.flags2 &= -513;
            } else if (emojiStatus instanceof TLRPC.TL_emojiStatusCollectible) {
                TLRPC.TL_emojiStatusCollectible tL_emojiStatusCollectible = (TLRPC.TL_emojiStatusCollectible) emojiStatus;
                TLRPC.TL_inputEmojiStatusCollectible tL_inputEmojiStatusCollectible = new TLRPC.TL_inputEmojiStatusCollectible();
                tL_inputEmojiStatusCollectible.collectible_id = tL_emojiStatusCollectible.collectible_id;
                tL_inputEmojiStatusCollectible.flags = tL_emojiStatusCollectible.flags;
                tL_inputEmojiStatusCollectible.until = tL_emojiStatusCollectible.until;
                tL_channels_updateEmojiStatus.emoji_status = tL_inputEmojiStatusCollectible;
                chat.emoji_status = this.selectedStatusEmoji;
                chat.flags |= 512;
            } else {
                tL_channels_updateEmojiStatus.emoji_status = emojiStatus;
                chat.emoji_status = emojiStatus;
                chat.flags |= 512;
            }
            getMessagesController().updateEmojiStatusUntilUpdate(this.dialogId, this.selectedStatusEmoji);
            iArr[r15] = iArr[r15] + 1;
            getConnectionsManager().sendRequest(tL_channels_updateEmojiStatus, new RequestDelegate() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda11
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    this.f$0.lambda$buttonClick$11(callback, tLObject, tL_error);
                }
            });
        }
        if (iArr[r15] == 0) {
            lambda$onBackPressed$371();
            this.button.setLoading(r15);
            return;
        }
        getMessagesController().putChat(chat, r15);
        NotificationCenter notificationCenter2 = getNotificationCenter();
        int i7 = NotificationCenter.updateInterfaces;
        Object[] objArr2 = new Object[1];
        objArr2[r15] = Integer.valueOf(MessagesController.UPDATE_MASK_EMOJI_STATUS);
        notificationCenter2.lambda$postNotificationNameOnUIThread$1(i7, objArr2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buttonClick$7(final boolean[] zArr, final int[] iArr, final int[] iArr2, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda16
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$buttonClick$6(zArr, iArr, iArr2, tL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buttonClick$6(boolean[] zArr, int[] iArr, int[] iArr2, TLRPC.TL_error tL_error) {
        int i;
        if (zArr[0] || (i = iArr[0]) >= iArr2[0]) {
            return;
        }
        if (tL_error != null) {
            zArr[0] = true;
            if ("BOOSTS_REQUIRED".equals(tL_error.text)) {
                showLimit();
                return;
            } else {
                this.button.setLoading(false);
                BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.error, LocaleController.formatString(C2369R.string.UnknownErrorCode, tL_error.text)).show();
                return;
            }
        }
        int i2 = i + 1;
        iArr[0] = i2;
        if (i2 == iArr2[0]) {
            lambda$onBackPressed$371();
            showBulletin();
            this.button.setLoading(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buttonClick$8(Utilities.Callback callback, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject instanceof TLRPC.Updates) {
            getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
        }
        if (callback != null) {
            callback.run(tL_error);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buttonClick$9(Utilities.Callback callback, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject instanceof TLRPC.Updates) {
            getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
        }
        if (callback != null) {
            callback.run(tL_error);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buttonClick$10(Utilities.Callback callback, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject instanceof TLRPC.Updates) {
            getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
        }
        if (callback != null) {
            callback.run(tL_error);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buttonClick$11(Utilities.Callback callback, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject instanceof TLRPC.Updates) {
            getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
        }
        if (callback != null) {
            callback.run(tL_error);
        }
    }

    private void showLimit() {
        getMessagesController().getBoostsController().userCanBoostChannel(this.dialogId, this.boostsStatus, new Consumer() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda15
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$showLimit$13((ChannelBoostsController.CanApplyBoost) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$showLimit$13(org.telegram.messenger.ChannelBoostsController.CanApplyBoost r13) {
        /*
            Method dump skipped, instructions count: 269
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.ChannelColorActivity.lambda$showLimit$13(org.telegram.messenger.ChannelBoostsController$CanApplyBoost):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showLimit$12(TLRPC.Chat chat) {
        presentFragment(StatisticActivity.create(chat));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUnsavedAlert() {
        if (getVisibleDialog() != null) {
            return;
        }
        AlertDialog alertDialogCreate = new AlertDialog.Builder(getContext(), getResourceProvider()).setTitle(LocaleController.getString(C2369R.string.ChannelColorUnsaved)).setMessage(LocaleController.getString(C2369R.string.ChannelColorUnsavedMessage)).setNegativeButton(LocaleController.getString(C2369R.string.Dismiss), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$showUnsavedAlert$14(alertDialog, i);
            }
        }).setPositiveButton(LocaleController.getString(C2369R.string.ApplyTheme), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda3
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$showUnsavedAlert$15(alertDialog, i);
            }
        }).create();
        showDialog(alertDialogCreate);
        ((TextView) alertDialogCreate.getButton(-2)).setTextColor(getThemedColor(Theme.key_text_RedBold));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnsavedAlert$14(AlertDialog alertDialog, int i) {
        lambda$onBackPressed$371();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnsavedAlert$15(AlertDialog alertDialog, int i) {
        buttonClick();
    }

    public void showSelectStatusDialog(EmojiCell emojiCell, long j, boolean z, final Utilities.Callback3 callback3, int i) {
        int iCenterX;
        int iM1146dp;
        if (this.selectAnimatedEmojiDialog != null || emojiCell == null) {
            return;
        }
        final SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] selectAnimatedEmojiDialogWindowArr = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[1];
        boolean z2 = ((float) (emojiCell.getTop() + emojiCell.getHeight())) > ((float) this.listView.getMeasuredHeight()) / 2.0f;
        int iMin = (int) Math.min(AndroidUtilities.m1146dp(330.0f), AndroidUtilities.displaySize.y * 0.75f);
        int iMin2 = (int) Math.min(AndroidUtilities.m1146dp(324.0f), AndroidUtilities.displaySize.x * 0.95f);
        emojiCell.imageDrawable.removeOldDrawable();
        AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = emojiCell.imageDrawable;
        if (emojiCell.imageDrawable != null) {
            emojiCell.imageDrawable.play();
            emojiCell.updateImageBounds();
            Rect rect = AndroidUtilities.rectTmp2;
            rect.set(emojiCell.imageDrawable.getBounds());
            if (z2) {
                iM1146dp = ((-rect.centerY()) + AndroidUtilities.m1146dp(12.0f)) - iMin;
            } else {
                iM1146dp = (-(emojiCell.getHeight() - rect.centerY())) - AndroidUtilities.m1146dp(16.0f);
            }
            iCenterX = rect.centerX() - (AndroidUtilities.displaySize.x - iMin2);
        } else {
            iCenterX = 0;
            iM1146dp = 0;
        }
        int i2 = iM1146dp;
        SelectAnimatedEmojiDialog selectAnimatedEmojiDialog = new SelectAnimatedEmojiDialog(this, getContext(), true, Integer.valueOf(iCenterX), z ? z2 ? 10 : 9 : z2 ? 5 : 7, true, getResourceProvider(), z2 ? 24 : 16, i) { // from class: org.telegram.ui.ChannelColorActivity.5
            @Override // org.telegram.p023ui.SelectAnimatedEmojiDialog
            protected float getScrimDrawableTranslationY() {
                return 0.0f;
            }

            @Override // org.telegram.p023ui.SelectAnimatedEmojiDialog
            protected void onEmojiSelected(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
                Utilities.Callback3 callback32 = callback3;
                if (callback32 != null) {
                    callback32.run(Long.valueOf(l == null ? 0L : l.longValue()), num, tL_starGiftUnique);
                }
                if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                    ChannelColorActivity.this.selectAnimatedEmojiDialog = null;
                    selectAnimatedEmojiDialogWindowArr[0].dismiss();
                }
            }

            @Override // org.telegram.p023ui.SelectAnimatedEmojiDialog
            public long getDialogId() {
                return ChannelColorActivity.this.dialogId;
            }
        };
        selectAnimatedEmojiDialog.useAccentForPlus = true;
        selectAnimatedEmojiDialog.setSelected(j == 0 ? null : Long.valueOf(j));
        selectAnimatedEmojiDialog.setSaveState(3);
        selectAnimatedEmojiDialog.setScrimDrawable(swapAnimatedEmojiDrawable, emojiCell);
        int i3 = -2;
        SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialogWindow = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow(selectAnimatedEmojiDialog, i3, i3) { // from class: org.telegram.ui.ChannelColorActivity.6
            @Override // org.telegram.ui.SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow, android.widget.PopupWindow
            public void dismiss() {
                super.dismiss();
                ChannelColorActivity.this.selectAnimatedEmojiDialog = null;
            }
        };
        this.selectAnimatedEmojiDialog = selectAnimatedEmojiDialogWindow;
        selectAnimatedEmojiDialogWindowArr[0] = selectAnimatedEmojiDialogWindow;
        selectAnimatedEmojiDialogWindow.showAsDropDown(emojiCell, 0, i2, 53);
        selectAnimatedEmojiDialogWindowArr[0].dimBehind();
    }

    protected void updateRows() {
        Adapter adapter;
        Adapter adapter2;
        this.messagesPreviewRow = 0;
        int i = 1 + 1;
        this.replyColorListRow = 1;
        this.replyEmojiRow = i;
        this.replyHintRow = i + 1;
        this.wallpaperThemesRow = i + 2;
        this.wallpaperRow = i + 3;
        this.wallpaperHintRow = i + 4;
        this.profilePreviewRow = i + 5;
        this.profileColorGridRow = i + 6;
        int i2 = i + 8;
        this.rowsCount = i2;
        this.profileEmojiRow = i + 7;
        if (this.selectedProfileEmoji != 0 || this.selectedProfileColor >= 0 || (this.selectedStatusEmoji instanceof TLRPC.TL_emojiStatusCollectible)) {
            boolean z = this.removeProfileColorRow >= 0;
            this.rowsCount = i + 9;
            this.removeProfileColorRow = i2;
            if (!z && (adapter = this.adapter) != null) {
                adapter.notifyItemInserted(i2);
                this.adapter.notifyItemChanged(this.profileEmojiRow);
            }
        } else {
            int i3 = this.removeProfileColorRow;
            this.removeProfileColorRow = -1;
            if (i3 >= 0 && (adapter2 = this.adapter) != null) {
                adapter2.notifyItemRemoved(i3);
                this.adapter.notifyItemChanged(this.profileEmojiRow);
            }
        }
        int i4 = this.rowsCount;
        this.profileHintRow = i4;
        this.statusEmojiRow = i4 + 1;
        this.rowsCount = i4 + 3;
        this.statusHintRow = i4 + 2;
    }

    protected int getProfileInfoStrRes() {
        return C2369R.string.ChannelProfileInfo;
    }

    protected int getEmojiStatusStrRes() {
        return C2369R.string.ChannelEmojiStatus;
    }

    protected int getEmojiStatusInfoStrRes() {
        return C2369R.string.ChannelEmojiStatusInfo;
    }

    protected int getWallpaperStrRes() {
        return C2369R.string.ChannelWallpaper;
    }

    protected int getWallpaper2InfoStrRes() {
        return C2369R.string.ChannelWallpaper2Info;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getThemeChooserEmoticon() {
        String wallpaperEmoticon = ChatThemeController.getWallpaperEmoticon(this.selectedWallpaper);
        return (wallpaperEmoticon == null && this.selectedWallpaper == null && this.galleryWallpaper != null) ? "❌" : wallpaperEmoticon;
    }

    protected class Adapter extends RecyclerListView.SelectionAdapter {
        protected Adapter() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) throws IOException {
            FrameLayout textInfoPrivacyCell;
            if (i == 0) {
                Context context = ChannelColorActivity.this.getContext();
                INavigationLayout iNavigationLayout = ((BaseFragment) ChannelColorActivity.this).parentLayout;
                int messagePreviewType = ChannelColorActivity.this.getMessagePreviewType();
                ChannelColorActivity channelColorActivity = ChannelColorActivity.this;
                ThemePreviewMessagesCell themePreviewMessagesCell = new ThemePreviewMessagesCell(context, iNavigationLayout, messagePreviewType, channelColorActivity.dialogId, ((BaseFragment) channelColorActivity).resourceProvider);
                themePreviewMessagesCell.customAnimation = true;
                themePreviewMessagesCell.setImportantForAccessibility(4);
                ChannelColorActivity channelColorActivity2 = ChannelColorActivity.this;
                themePreviewMessagesCell.fragment = channelColorActivity2;
                Drawable drawable = channelColorActivity2.backgroundDrawable;
                int i2 = ((BaseFragment) channelColorActivity2).currentAccount;
                ChannelColorActivity channelColorActivity3 = ChannelColorActivity.this;
                Drawable backgroundDrawable = PreviewView.getBackgroundDrawable(drawable, i2, channelColorActivity3.selectedWallpaper, channelColorActivity3.isDark);
                channelColorActivity2.backgroundDrawable = backgroundDrawable;
                themePreviewMessagesCell.setOverrideBackground(backgroundDrawable);
                textInfoPrivacyCell = themePreviewMessagesCell;
            } else if (i == 2) {
                ThemeChooser themeChooser = new ThemeChooser(ChannelColorActivity.this.getContext(), false, ((BaseFragment) ChannelColorActivity.this).currentAccount, ((BaseFragment) ChannelColorActivity.this).resourceProvider);
                themeChooser.setWithRemovedStub(true);
                themeChooser.setSelectedEmoticon(ChannelColorActivity.this.getThemeChooserEmoticon(), false);
                themeChooser.setGalleryWallpaper(ChannelColorActivity.this.galleryWallpaper);
                themeChooser.setOnEmoticonSelected(new Utilities.Callback() { // from class: org.telegram.ui.ChannelColorActivity$Adapter$$ExternalSyntheticLambda0
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) throws IOException {
                        this.f$0.lambda$onCreateViewHolder$0((String) obj);
                    }
                });
                themeChooser.setBackgroundColor(ChannelColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                textInfoPrivacyCell = themeChooser;
            } else if (i == 5) {
                FrameLayout textCell = new TextCell(ChannelColorActivity.this.getContext(), ChannelColorActivity.this.getResourceProvider());
                textCell.setBackgroundColor(ChannelColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                textInfoPrivacyCell = textCell;
            } else if (i == 6) {
                FrameLayout emojiCell = new EmojiCell(ChannelColorActivity.this.getContext(), ((BaseFragment) ChannelColorActivity.this).resourceProvider);
                emojiCell.setBackgroundColor(ChannelColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                textInfoPrivacyCell = emojiCell;
            } else if (i == 3) {
                final PeerColorPicker peerColorPicker = new PeerColorPicker(ChannelColorActivity.this.getContext(), ((BaseFragment) ChannelColorActivity.this).currentAccount, ((BaseFragment) ChannelColorActivity.this).resourceProvider);
                peerColorPicker.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.ChannelColorActivity$Adapter$$ExternalSyntheticLambda1
                    @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                    public final void onItemClick(View view, int i3) throws IOException {
                        this.f$0.lambda$onCreateViewHolder$1(peerColorPicker, view, i3);
                    }
                });
                peerColorPicker.setBackgroundColor(ChannelColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                textInfoPrivacyCell = peerColorPicker;
            } else if (i == 4) {
                PeerColorActivity.PeerColorGrid peerColorGrid = new PeerColorActivity.PeerColorGrid(ChannelColorActivity.this.getContext(), 0, ((BaseFragment) ChannelColorActivity.this).currentAccount, ((BaseFragment) ChannelColorActivity.this).resourceProvider);
                peerColorGrid.setDivider(false);
                peerColorGrid.setOnColorClick(new Utilities.Callback() { // from class: org.telegram.ui.ChannelColorActivity$Adapter$$ExternalSyntheticLambda2
                    @Override // org.telegram.messenger.Utilities.Callback
                    public final void run(Object obj) {
                        this.f$0.lambda$onCreateViewHolder$2((Integer) obj);
                    }
                });
                peerColorGrid.setBackgroundColor(ChannelColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                textInfoPrivacyCell = peerColorGrid;
            } else if (i == 1) {
                ChannelColorActivity channelColorActivity4 = ChannelColorActivity.this;
                textInfoPrivacyCell = channelColorActivity4.new ProfilePreview(channelColorActivity4.getContext());
            } else if (i == 8) {
                FrameLayout headerCell = new HeaderCell(ChannelColorActivity.this.getContext(), ((BaseFragment) ChannelColorActivity.this).resourceProvider);
                headerCell.setBackgroundColor(ChannelColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                textInfoPrivacyCell = headerCell;
            } else if (i == 9) {
                textInfoPrivacyCell = new PeerColorActivity.GiftCell(ChannelColorActivity.this.getContext(), false, ((BaseFragment) ChannelColorActivity.this).resourceProvider);
            } else if (i == 10) {
                FlickerLoadingView flickerLoadingView = new FlickerLoadingView(ChannelColorActivity.this.getContext(), ((BaseFragment) ChannelColorActivity.this).resourceProvider);
                flickerLoadingView.setIsSingleCell(true);
                flickerLoadingView.setViewType(35);
                textInfoPrivacyCell = flickerLoadingView;
            } else {
                textInfoPrivacyCell = new TextInfoPrivacyCell(ChannelColorActivity.this.getContext());
            }
            return new RecyclerListView.Holder(textInfoPrivacyCell);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$0(String str) throws IOException {
            if (str == null) {
                ChannelColorActivity channelColorActivity = ChannelColorActivity.this;
                channelColorActivity.selectedWallpaper = channelColorActivity.galleryWallpaper;
            } else if (str.equals("❌")) {
                ChannelColorActivity.this.selectedWallpaper = null;
            } else {
                ChannelColorActivity.this.selectedWallpaper = new TLRPC.TL_wallPaperNoFile();
                TLRPC.WallPaper wallPaper = ChannelColorActivity.this.selectedWallpaper;
                wallPaper.f1742id = 0L;
                wallPaper.flags |= 4;
                wallPaper.settings = new TLRPC.TL_wallPaperSettings();
                ChannelColorActivity.this.selectedWallpaper.settings.emoticon = str;
            }
            ChannelColorActivity.this.updateButton(true);
            ChannelColorActivity.this.updateMessagesPreview(true);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$1(PeerColorPicker peerColorPicker, View view, int i) throws IOException {
            ChannelColorActivity.this.selectedReplyColor = peerColorPicker.toColorId(i);
            ChannelColorActivity.this.updateButton(true);
            ChannelColorActivity.this.updateMessagesPreview(true);
            ChannelColorActivity.this.updateProfilePreview(true);
            if (view.getLeft() < peerColorPicker.listView.getPaddingLeft() + AndroidUtilities.m1146dp(24.0f)) {
                RecyclerListView recyclerListView = peerColorPicker.listView;
                recyclerListView.smoothScrollBy(-((recyclerListView.getPaddingLeft() + AndroidUtilities.m1146dp(48.0f)) - view.getLeft()), 0);
            } else if (view.getLeft() + view.getWidth() > (peerColorPicker.listView.getMeasuredWidth() - peerColorPicker.listView.getPaddingRight()) - AndroidUtilities.m1146dp(24.0f)) {
                peerColorPicker.listView.smoothScrollBy((view.getLeft() + view.getWidth()) - ((peerColorPicker.listView.getMeasuredWidth() - peerColorPicker.listView.getPaddingRight()) - AndroidUtilities.m1146dp(48.0f)), 0);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateViewHolder$2(Integer num) {
            ChannelColorActivity.this.selectedProfileColor = num.intValue();
            ChannelColorActivity channelColorActivity = ChannelColorActivity.this;
            if (channelColorActivity.selectedStatusEmoji instanceof TLRPC.TL_emojiStatusCollectible) {
                channelColorActivity.selectedStatusEmoji = null;
            }
            channelColorActivity.updateButton(true);
            ChannelColorActivity.this.updateProfilePreview(true);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            TLRPC.StickerSet stickerSet;
            TLRPC.StickerSet stickerSet2;
            int itemViewType = viewHolder.getItemViewType();
            if (itemViewType == 1) {
                ProfilePreview profilePreview = (ProfilePreview) viewHolder.itemView;
                profilePreview.backgroundView.setColor(((BaseFragment) ChannelColorActivity.this).currentAccount, ChannelColorActivity.this.selectedProfileColor, false);
                profilePreview.profileView.setColor(ChannelColorActivity.this.selectedProfileColor, false);
                profilePreview.profileView.setEmoji(ChannelColorActivity.this.selectedProfileEmoji, false, false);
                profilePreview.profileView.setForum(ChannelColorActivity.this.isForum());
                profilePreview.profileView.setStatusEmoji(DialogObject.getEmojiStatusDocumentId(ChannelColorActivity.this.selectedStatusEmoji), false, false);
                profilePreview.profileView.overrideAvatarColor(ChannelColorActivity.this.selectedReplyColor);
                return;
            }
            if (itemViewType == 3) {
                ((PeerColorPicker) viewHolder.itemView).setSelected(ChannelColorActivity.this.selectedReplyColor, false);
                return;
            }
            if (itemViewType == 4) {
                ((PeerColorActivity.PeerColorGrid) viewHolder.itemView).setSelected(ChannelColorActivity.this.selectedProfileColor, false);
                return;
            }
            if (itemViewType == 5) {
                TextCell textCell = (TextCell) viewHolder.itemView;
                ChannelColorActivity channelColorActivity = ChannelColorActivity.this;
                if (i == channelColorActivity.removeProfileColorRow) {
                    textCell.setText(LocaleController.getString(C2369R.string.ChannelProfileColorReset), false);
                    return;
                }
                textCell.setText(LocaleController.getString(channelColorActivity.getWallpaperStrRes()), false);
                ChannelColorActivity channelColorActivity2 = ChannelColorActivity.this;
                if (channelColorActivity2.currentLevel < channelColorActivity2.getCustomWallpaperLevelMin()) {
                    textCell.setLockLevel(false, ChannelColorActivity.this.getCustomWallpaperLevelMin());
                    return;
                } else {
                    textCell.setLockLevel(false, 0);
                    return;
                }
            }
            if (itemViewType != 6) {
                if (itemViewType != 7) {
                    return;
                }
                TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                textInfoPrivacyCell.setFixedSize(0);
                ChannelColorActivity channelColorActivity3 = ChannelColorActivity.this;
                if (i == channelColorActivity3.replyHintRow) {
                    textInfoPrivacyCell.setText(LocaleController.getString(C2369R.string.ChannelReplyInfo));
                } else if (i == channelColorActivity3.wallpaperHintRow) {
                    textInfoPrivacyCell.setText(LocaleController.getString(channelColorActivity3.getWallpaper2InfoStrRes()));
                } else if (i == channelColorActivity3.profileHintRow) {
                    textInfoPrivacyCell.setText(LocaleController.getString(channelColorActivity3.getProfileInfoStrRes()));
                } else if (i == channelColorActivity3.statusHintRow) {
                    textInfoPrivacyCell.setText(LocaleController.getString(channelColorActivity3.getEmojiStatusInfoStrRes()));
                } else if (i == channelColorActivity3.packEmojiHintRow) {
                    textInfoPrivacyCell.setText(LocaleController.getString(channelColorActivity3.getEmojiPackInfoStrRes()));
                } else if (i == channelColorActivity3.packStickerHintRow) {
                    textInfoPrivacyCell.setText(LocaleController.getString(channelColorActivity3.getStickerPackInfoStrRes()));
                } else if (i == channelColorActivity3.removeProfileColorShadowRow) {
                    textInfoPrivacyCell.setText("");
                    textInfoPrivacyCell.setFixedSize(12);
                }
                Context context = ChannelColorActivity.this.getContext();
                ChannelColorActivity channelColorActivity4 = ChannelColorActivity.this;
                textInfoPrivacyCell.setBackground(Theme.getThemedDrawableByKey(context, i == channelColorActivity4.statusHintRow ? C2369R.drawable.greydivider_bottom : C2369R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow, ((BaseFragment) channelColorActivity4).resourceProvider));
                return;
            }
            EmojiCell emojiCell = (EmojiCell) viewHolder.itemView;
            emojiCell.setDivider(false);
            ChannelColorActivity channelColorActivity5 = ChannelColorActivity.this;
            if (i == channelColorActivity5.replyEmojiRow) {
                emojiCell.setAdaptiveEmojiColor(((BaseFragment) channelColorActivity5).currentAccount, ChannelColorActivity.this.selectedReplyColor, true);
                emojiCell.setText(LocaleController.getString(C2369R.string.ChannelReplyLogo));
                ChannelColorActivity channelColorActivity6 = ChannelColorActivity.this;
                if (channelColorActivity6.currentLevel < channelColorActivity6.getMessagesController().channelBgIconLevelMin) {
                    emojiCell.setLockLevel(ChannelColorActivity.this.getMessagesController().channelBgIconLevelMin);
                } else {
                    emojiCell.setLockLevel(0);
                }
                emojiCell.setEmoji(ChannelColorActivity.this.selectedReplyEmoji, false, false);
                return;
            }
            if (i == channelColorActivity5.profileEmojiRow) {
                emojiCell.setAdaptiveEmojiColor(((BaseFragment) channelColorActivity5).currentAccount, ChannelColorActivity.this.selectedProfileColor, false);
                emojiCell.setText(LocaleController.getString(C2369R.string.ChannelProfileLogo));
                emojiCell.setDivider(ChannelColorActivity.this.removeProfileColorRow >= 0);
                ChannelColorActivity channelColorActivity7 = ChannelColorActivity.this;
                if (channelColorActivity7.currentLevel < channelColorActivity7.getProfileIconLevelMin()) {
                    emojiCell.setLockLevel(ChannelColorActivity.this.getProfileIconLevelMin());
                } else {
                    emojiCell.setLockLevel(0);
                }
                emojiCell.setEmoji(ChannelColorActivity.this.selectedProfileEmoji, false, false);
                return;
            }
            if (i == channelColorActivity5.statusEmojiRow) {
                emojiCell.setAdaptiveEmojiColor(((BaseFragment) channelColorActivity5).currentAccount, ChannelColorActivity.this.selectedProfileColor, false);
                emojiCell.setText(LocaleController.getString(ChannelColorActivity.this.getEmojiStatusStrRes()));
                ChannelColorActivity channelColorActivity8 = ChannelColorActivity.this;
                if (channelColorActivity8.currentLevel < channelColorActivity8.getEmojiStatusLevelMin()) {
                    emojiCell.setLockLevel(ChannelColorActivity.this.getEmojiStatusLevelMin());
                } else {
                    emojiCell.setLockLevel(0);
                }
                emojiCell.setEmoji(DialogObject.getEmojiStatusDocumentId(ChannelColorActivity.this.selectedStatusEmoji), DialogObject.isEmojiStatusCollectible(ChannelColorActivity.this.selectedStatusEmoji), false);
                return;
            }
            if (i == channelColorActivity5.packEmojiRow) {
                emojiCell.setAdaptiveEmojiColor(((BaseFragment) channelColorActivity5).currentAccount, ChannelColorActivity.this.selectedProfileColor, false);
                emojiCell.setText(LocaleController.getString(ChannelColorActivity.this.getEmojiPackStrRes()));
                ChannelColorActivity channelColorActivity9 = ChannelColorActivity.this;
                if (channelColorActivity9.currentLevel < channelColorActivity9.getEmojiStickersLevelMin()) {
                    emojiCell.setLockLevel(ChannelColorActivity.this.getEmojiStickersLevelMin());
                } else {
                    emojiCell.setLockLevel(0);
                }
                TLRPC.ChatFull chatFull = ChannelColorActivity.this.getMessagesController().getChatFull(-ChannelColorActivity.this.dialogId);
                if (chatFull != null && (stickerSet2 = chatFull.emojiset) != null) {
                    emojiCell.setEmoji(ChannelColorActivity.this.getEmojiSetThumbId(stickerSet2), false, false);
                    return;
                } else {
                    emojiCell.setEmoji(0L, false, false);
                    return;
                }
            }
            if (i == channelColorActivity5.packStickerRow) {
                emojiCell.setText(LocaleController.getString(channelColorActivity5.getStickerPackStrRes()));
                emojiCell.setLockLevel(0);
                TLRPC.ChatFull chatFull2 = ChannelColorActivity.this.getMessagesController().getChatFull(-ChannelColorActivity.this.dialogId);
                if (chatFull2 != null && (stickerSet = chatFull2.stickerset) != null) {
                    emojiCell.setEmoji(ChannelColorActivity.this.getEmojiSetThumb(stickerSet), false, false);
                } else {
                    emojiCell.setEmoji(0L, false, false);
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
            View view = viewHolder.itemView;
            if (view instanceof ProfilePreview) {
                ProfilePreview profilePreview = (ProfilePreview) view;
                ChannelColorActivity channelColorActivity = ChannelColorActivity.this;
                TLRPC.EmojiStatus emojiStatus = channelColorActivity.selectedStatusEmoji;
                if (emojiStatus instanceof TLRPC.TL_emojiStatusCollectible) {
                    profilePreview.profileView.setColor(MessagesController.PeerColor.fromCollectible(emojiStatus), false);
                    profilePreview.profileView.setEmoji(((TLRPC.TL_emojiStatusCollectible) ChannelColorActivity.this.selectedStatusEmoji).pattern_document_id, true, false);
                } else {
                    profilePreview.profileView.setColor(channelColorActivity.selectedProfileColor, false);
                    profilePreview.profileView.setEmoji(ChannelColorActivity.this.selectedProfileEmoji, false, false);
                }
                profilePreview.profileView.setStatusEmoji(DialogObject.getEmojiStatusDocumentId(ChannelColorActivity.this.selectedStatusEmoji), DialogObject.isEmojiStatusCollectible(ChannelColorActivity.this.selectedStatusEmoji), false);
                profilePreview.profileView.setForum(ChannelColorActivity.this.isForum());
                profilePreview.profileView.overrideAvatarColor(ChannelColorActivity.this.selectedReplyColor);
            } else if (view instanceof ThemePreviewMessagesCell) {
                ((ThemePreviewMessagesCell) view).setOverrideBackground(ChannelColorActivity.this.backgroundDrawable);
            } else {
                ChannelColorActivity.this.updateColors(view);
            }
            super.onViewAttachedToWindow(viewHolder);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            ChannelColorActivity channelColorActivity = ChannelColorActivity.this;
            if (i == channelColorActivity.messagesPreviewRow) {
                return 0;
            }
            if (i == channelColorActivity.wallpaperThemesRow) {
                return 2;
            }
            if (i == channelColorActivity.profilePreviewRow) {
                return 1;
            }
            if (i == channelColorActivity.replyColorListRow) {
                return 3;
            }
            if (i == channelColorActivity.profileColorGridRow) {
                return 4;
            }
            if (i == channelColorActivity.replyEmojiRow || i == channelColorActivity.profileEmojiRow || i == channelColorActivity.statusEmojiRow || i == channelColorActivity.packEmojiRow || i == channelColorActivity.packStickerRow) {
                return 6;
            }
            return (i == channelColorActivity.wallpaperRow || i == channelColorActivity.removeProfileColorRow) ? 5 : 7;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return ChannelColorActivity.this.rowsCount;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return itemViewType == 5 || itemViewType == 6;
        }
    }

    public void updateMessagesPreview(boolean z) throws IOException {
        MessageObject messageObject;
        View viewFindChildAt = findChildAt(this.messagesPreviewRow);
        View viewFindChildAt2 = findChildAt(this.replyColorListRow);
        View viewFindChildAt3 = findChildAt(this.replyEmojiRow);
        View viewFindChildAt4 = findChildAt(this.wallpaperThemesRow);
        if (viewFindChildAt instanceof ThemePreviewMessagesCell) {
            ThemePreviewMessagesCell themePreviewMessagesCell = (ThemePreviewMessagesCell) viewFindChildAt;
            ChatMessageCell[] cells = themePreviewMessagesCell.getCells();
            for (int i = 0; i < cells.length; i++) {
                ChatMessageCell chatMessageCell = cells[i];
                if (chatMessageCell != null && (messageObject = chatMessageCell.getMessageObject()) != null) {
                    messageObject.overrideLinkColor = this.selectedReplyColor;
                    messageObject.overrideLinkEmoji = this.selectedReplyEmoji;
                    cells[i].setAvatar(messageObject);
                    cells[i].invalidate();
                }
            }
            Drawable backgroundDrawable = PreviewView.getBackgroundDrawable(this.backgroundDrawable, this.currentAccount, this.selectedWallpaper, this.isDark);
            this.backgroundDrawable = backgroundDrawable;
            themePreviewMessagesCell.setOverrideBackground(backgroundDrawable);
        }
        if (viewFindChildAt2 instanceof PeerColorActivity.PeerColorGrid) {
            ((PeerColorActivity.PeerColorGrid) viewFindChildAt2).setSelected(this.selectedReplyColor, z);
        } else if (viewFindChildAt2 instanceof PeerColorPicker) {
            ((PeerColorPicker) viewFindChildAt2).setSelected(this.selectedReplyColor, z);
        }
        if (viewFindChildAt3 instanceof EmojiCell) {
            EmojiCell emojiCell = (EmojiCell) viewFindChildAt3;
            emojiCell.setAdaptiveEmojiColor(this.currentAccount, this.selectedReplyColor, true);
            emojiCell.setEmoji(this.selectedReplyEmoji, false, z);
        }
        if (viewFindChildAt4 instanceof ThemeChooser) {
            ThemeChooser themeChooser = (ThemeChooser) viewFindChildAt4;
            themeChooser.setSelectedEmoticon(getThemeChooserEmoticon(), z);
            themeChooser.setGalleryWallpaper(this.galleryWallpaper);
        }
    }

    public void updateProfilePreview(boolean z) {
        TLRPC.StickerSet stickerSet;
        TLRPC.StickerSet stickerSet2;
        View viewFindChildAt = findChildAt(this.profilePreviewRow);
        View viewFindChildAt2 = findChildAt(this.profileColorGridRow);
        View viewFindChildAt3 = findChildAt(this.profileEmojiRow);
        View viewFindChildAt4 = findChildAt(this.statusEmojiRow);
        View viewFindChildAt5 = findChildAt(this.packEmojiRow);
        View viewFindChildAt6 = findChildAt(this.packStickerRow);
        if (viewFindChildAt instanceof ProfilePreview) {
            TLRPC.EmojiStatus emojiStatus = this.selectedStatusEmoji;
            if (emojiStatus instanceof TLRPC.TL_emojiStatusCollectible) {
                ProfilePreview profilePreview = (ProfilePreview) viewFindChildAt;
                profilePreview.setColor(MessagesController.PeerColor.fromCollectible(emojiStatus), z);
                profilePreview.setEmoji(((TLRPC.TL_emojiStatusCollectible) this.selectedStatusEmoji).pattern_document_id, true, z);
            } else {
                ProfilePreview profilePreview2 = (ProfilePreview) viewFindChildAt;
                profilePreview2.setColor(this.selectedProfileColor, z);
                profilePreview2.setEmoji(this.selectedProfileEmoji, false, z);
            }
            ProfilePreview profilePreview3 = (ProfilePreview) viewFindChildAt;
            profilePreview3.setEmojiStatus(this.selectedStatusEmoji, z);
            profilePreview3.profileView.overrideAvatarColor(this.selectedReplyColor);
        }
        if (viewFindChildAt2 instanceof PeerColorActivity.PeerColorGrid) {
            ((PeerColorActivity.PeerColorGrid) viewFindChildAt2).setSelected(this.selectedProfileColor, z);
        } else if (viewFindChildAt2 instanceof PeerColorPicker) {
            ((PeerColorPicker) viewFindChildAt2).setSelected(this.selectedReplyColor, z);
        }
        if (viewFindChildAt3 instanceof EmojiCell) {
            EmojiCell emojiCell = (EmojiCell) viewFindChildAt3;
            emojiCell.setAdaptiveEmojiColor(this.currentAccount, this.selectedProfileColor, false);
            emojiCell.setEmoji(this.selectedProfileEmoji, false, z);
        }
        if (viewFindChildAt4 instanceof EmojiCell) {
            TLRPC.EmojiStatus emojiStatus2 = this.selectedStatusEmoji;
            if (emojiStatus2 instanceof TLRPC.TL_emojiStatusCollectible) {
                ((EmojiCell) viewFindChildAt4).setAdaptiveEmojiColor(MessagesController.PeerColor.fromCollectible(emojiStatus2));
            } else {
                ((EmojiCell) viewFindChildAt4).setAdaptiveEmojiColor(this.currentAccount, this.selectedProfileColor, false);
            }
            ((EmojiCell) viewFindChildAt4).setEmoji(DialogObject.getEmojiStatusDocumentId(this.selectedStatusEmoji), DialogObject.isEmojiStatusCollectible(this.selectedStatusEmoji), z);
        }
        if (viewFindChildAt5 instanceof EmojiCell) {
            EmojiCell emojiCell2 = (EmojiCell) viewFindChildAt5;
            emojiCell2.setAdaptiveEmojiColor(this.currentAccount, this.selectedProfileColor, false);
            TLRPC.ChatFull chatFull = getMessagesController().getChatFull(-this.dialogId);
            if (chatFull != null && (stickerSet2 = chatFull.emojiset) != null) {
                emojiCell2.setEmoji(getEmojiSetThumbId(stickerSet2), false, false);
            } else {
                emojiCell2.setEmoji(0L, false, false);
            }
        }
        if (viewFindChildAt6 instanceof EmojiCell) {
            TLRPC.ChatFull chatFull2 = getMessagesController().getChatFull(-this.dialogId);
            if (chatFull2 != null && (stickerSet = chatFull2.stickerset) != null) {
                ((EmojiCell) viewFindChildAt6).setEmoji(getEmojiSetThumb(stickerSet), false, false);
            } else {
                ((EmojiCell) viewFindChildAt6).setEmoji(0L, false, false);
            }
        }
        updateRows();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getEmojiSetThumbId(TLRPC.StickerSet stickerSet) {
        if (stickerSet == null) {
            return 0L;
        }
        long j = stickerSet.thumb_document_id;
        if (j == 0) {
            TLRPC.TL_messages_stickerSet groupStickerSetById = getMediaDataController().getGroupStickerSetById(stickerSet);
            if (!groupStickerSetById.documents.isEmpty()) {
                return ((TLRPC.Document) groupStickerSetById.documents.get(0)).f1579id;
            }
        }
        return j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public TLRPC.Document getEmojiSetThumb(TLRPC.StickerSet stickerSet) {
        if (stickerSet != null && stickerSet.thumb_document_id == 0) {
            TLRPC.TL_messages_stickerSet groupStickerSetById = getMediaDataController().getGroupStickerSetById(stickerSet);
            if (!groupStickerSetById.documents.isEmpty()) {
                return (TLRPC.Document) groupStickerSetById.documents.get(0);
            }
        }
        return null;
    }

    public View findChildAt(int i) {
        for (int i2 = 0; i2 < this.listView.getChildCount(); i2++) {
            View childAt = this.listView.getChildAt(i2);
            if (this.listView.getChildAdapterPosition(childAt) == i) {
                return childAt;
            }
        }
        return null;
    }

    protected class ProfilePreview extends FrameLayout {
        public final PeerColorActivity.ColoredActionBar backgroundView;
        public LinearLayout infoLayout;
        public final PeerColorActivity.ProfilePreview profileView;
        public TextView textInfo1;
        public TextView textInfo2;
        public SimpleTextView title;

        public void setTitleSize() {
            float f;
            boolean z = getResources().getConfiguration().orientation == 2;
            this.title.setTextSize((AndroidUtilities.isTablet() || !z) ? 20 : 18);
            SimpleTextView simpleTextView = this.title;
            if (AndroidUtilities.isTablet()) {
                f = -2.0f;
            } else {
                f = z ? 4 : 0;
            }
            simpleTextView.setTranslationY(AndroidUtilities.m1146dp(f));
        }

        public ProfilePreview(Context context) {
            super(context);
            PeerColorActivity.ColoredActionBar coloredActionBar = new PeerColorActivity.ColoredActionBar(getContext(), ((BaseFragment) ChannelColorActivity.this).resourceProvider);
            this.backgroundView = coloredActionBar;
            coloredActionBar.setProgressToGradient(1.0f);
            coloredActionBar.ignoreMeasure = true;
            addView(coloredActionBar, LayoutHelper.createFrame(-1, ChannelColorActivity.this.isGroup ? 320 : 260, Opcodes.DNEG));
            PeerColorActivity.ProfilePreview profilePreview = new PeerColorActivity.ProfilePreview(getContext(), ((BaseFragment) ChannelColorActivity.this).currentAccount, ChannelColorActivity.this.dialogId, ((BaseFragment) ChannelColorActivity.this).resourceProvider) { // from class: org.telegram.ui.ChannelColorActivity.ProfilePreview.1
                @Override // org.telegram.ui.PeerColorActivity.ProfilePreview
                public void setColor(int i, boolean z) {
                    super.setColor(i, z);
                    ProfilePreview profilePreview2 = ProfilePreview.this;
                    TextView textView = profilePreview2.textInfo1;
                    if (textView != null) {
                        textView.setTextColor(profilePreview2.profileView.subtitleView.getTextColor());
                    }
                }
            };
            this.profileView = profilePreview;
            addView(profilePreview, LayoutHelper.createFrame(-1, 230.0f, 80, 0.0f, 0.0f, 0.0f, ChannelColorActivity.this.isGroup ? 24.0f : 0.0f));
            if (ChannelColorActivity.this.needBoostInfoSection()) {
                SimpleTextView simpleTextView = new SimpleTextView(getContext());
                this.title = simpleTextView;
                simpleTextView.setGravity(19);
                this.title.setTextColor(ChannelColorActivity.this.getThemedColor(Theme.key_actionBarDefaultTitle));
                this.title.setTypeface(AndroidUtilities.bold());
                this.title.setText(LocaleController.getString(C2369R.string.ChangeChannelNameColor2));
                this.title.setAlpha(0.0f);
                setTitleSize();
                addView(this.title, LayoutHelper.createFrame(-1, -2.0f, 80, 72.0f, 0.0f, 0.0f, 16.0f));
                LinearLayout linearLayout = new LinearLayout(context);
                this.infoLayout = linearLayout;
                linearLayout.setOrientation(0);
                this.infoLayout.setBackground(Theme.createSelectorWithBackgroundDrawable(Theme.multAlpha(-16777216, 0.065f), -16777216));
                this.infoLayout.setGravity(17);
                this.infoLayout.setPadding(AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(4.0f));
                TextView textView = new TextView(context);
                this.textInfo1 = textView;
                textView.setTextSize(1, 12.0f);
                this.textInfo1.setTextColor(profilePreview.subtitleView.getTextColor());
                TextView textView2 = new TextView(context);
                this.textInfo2 = textView2;
                textView2.setTextSize(1, 12.0f);
                this.textInfo2.setTextColor(-1);
                TextView textView3 = this.textInfo1;
                TL_stories.TL_premium_boostsStatus tL_premium_boostsStatus = ChannelColorActivity.this.boostsStatus;
                textView3.setText(AndroidUtilities.replaceTags(LocaleController.formatPluralString("BoostingGroupBoostCount", tL_premium_boostsStatus != null ? tL_premium_boostsStatus.boosts : 0, new Object[0])));
                this.textInfo2.setText(LocaleController.getString(C2369R.string.BoostingGroupBoostWhatAreBoosts));
                this.infoLayout.addView(this.textInfo1);
                this.infoLayout.addView(this.textInfo2, LayoutHelper.createLinear(-2, -2, 3.0f, 0.0f, 0.0f, 0.0f));
                addView(this.infoLayout, LayoutHelper.createFrame(-1, -2, 80));
            }
        }

        public void setColor(int i, boolean z) {
            this.profileView.setColor(i, z);
            this.backgroundView.setColor(((BaseFragment) ChannelColorActivity.this).currentAccount, i, z);
        }

        public void setColor(MessagesController.PeerColor peerColor, boolean z) {
            this.profileView.setColor(peerColor, z);
            this.backgroundView.setColor(peerColor, z);
        }

        public void setEmoji(long j, boolean z, boolean z2) {
            this.profileView.setEmoji(j, z, z2);
        }

        public void setEmojiStatus(TLRPC.EmojiStatus emojiStatus, boolean z) {
            this.profileView.setStatusEmoji(DialogObject.getEmojiStatusDocumentId(emojiStatus), false, z);
        }
    }

    private static class EmojiCell extends FrameLayout {
        private int color;
        private AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable imageDrawable;
        private boolean needDivider;
        private Text offText;
        private Theme.ResourcesProvider resourcesProvider;
        private SimpleTextView textView;

        public EmojiCell(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            this.needDivider = false;
            this.resourcesProvider = resourcesProvider;
            setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite, resourcesProvider));
            SimpleTextView simpleTextView = new SimpleTextView(context);
            this.textView = simpleTextView;
            simpleTextView.setTextSize(16);
            this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, resourcesProvider));
            addView(this.textView, LayoutHelper.createFrame(-1, -2.0f, 23, 23.0f, 0.0f, 48.0f, 0.0f));
            this.imageDrawable = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(this, false, AndroidUtilities.m1146dp(24.0f), 13);
        }

        public void setDivider(boolean z) {
            this.needDivider = z;
            setWillNotDraw(!z);
        }

        public void setLockLevel(int i) {
            if (i <= 0) {
                this.textView.setRightDrawable((Drawable) null);
            } else {
                this.textView.setRightDrawable(new PeerColorActivity.LevelLock(getContext(), i, this.resourcesProvider));
                this.textView.setDrawablePadding(AndroidUtilities.m1146dp(6.0f));
            }
        }

        public void setAdaptiveEmojiColor(int i, int i2, boolean z) {
            if (i2 < 0) {
                setAdaptiveEmojiColor(null);
            } else if (i2 < 7) {
                this.color = Theme.getColor(Theme.keys_avatar_nameInMessage[i2], this.resourcesProvider);
            } else {
                MessagesController messagesController = MessagesController.getInstance(i);
                MessagesController.PeerColors peerColors = z ? messagesController.peerColors : messagesController.profilePeerColors;
                setAdaptiveEmojiColor(peerColors != null ? peerColors.getColor(i2) : null);
            }
            invalidate();
        }

        public void setAdaptiveEmojiColor(MessagesController.PeerColor peerColor) {
            if (peerColor == null) {
                int i = Theme.key_actionBarDefault;
                if (AndroidUtilities.computePerceivedBrightness(Theme.getColor(i, this.resourcesProvider)) > 0.8f) {
                    this.color = Theme.getColor(Theme.key_windowBackgroundWhiteBlueText, this.resourcesProvider);
                    return;
                } else if (AndroidUtilities.computePerceivedBrightness(Theme.getColor(i, this.resourcesProvider)) < 0.2f) {
                    this.color = Theme.multAlpha(Theme.getColor(Theme.key_actionBarDefaultTitle, this.resourcesProvider), 0.5f);
                    return;
                } else {
                    this.color = Theme.blendOver(Theme.getColor(Theme.key_windowBackgroundWhite, this.resourcesProvider), Theme.multAlpha(PeerColorActivity.adaptProfileEmojiColor(Theme.getColor(i, this.resourcesProvider)), 0.7f));
                    return;
                }
            }
            this.color = peerColor.getColor(0, this.resourcesProvider);
        }

        public void setText(CharSequence charSequence) {
            this.textView.setText(charSequence);
        }

        public void setEmoji(long j, boolean z, boolean z2) {
            if (j == 0) {
                this.imageDrawable.set((Drawable) null, z2);
                if (this.offText == null) {
                    this.offText = new Text(LocaleController.getString(C2369R.string.ChannelReplyIconOff), 16.0f);
                }
            } else {
                this.imageDrawable.set(j, z2);
                this.offText = null;
            }
            this.imageDrawable.setParticles(z, z2);
        }

        public void setEmoji(TLRPC.Document document, boolean z, boolean z2) {
            if (document == null) {
                this.imageDrawable.set((Drawable) null, z2);
                if (this.offText == null) {
                    this.offText = new Text(LocaleController.getString(C2369R.string.ChannelReplyIconOff), 16.0f);
                }
            } else {
                this.imageDrawable.set(document, z2);
                this.offText = null;
            }
            this.imageDrawable.setParticles(z, z2);
        }

        public void updateColors() {
            this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourcesProvider));
        }

        public void updateImageBounds() {
            this.imageDrawable.setBounds((getWidth() - this.imageDrawable.getIntrinsicWidth()) - AndroidUtilities.m1146dp(21.0f), (getHeight() - this.imageDrawable.getIntrinsicHeight()) / 2, getWidth() - AndroidUtilities.m1146dp(21.0f), (getHeight() + this.imageDrawable.getIntrinsicHeight()) / 2);
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            Canvas canvas2;
            super.dispatchDraw(canvas);
            updateImageBounds();
            this.imageDrawable.setColor(Integer.valueOf(this.color));
            Text text = this.offText;
            if (text != null) {
                canvas2 = canvas;
                text.draw(canvas2, (getMeasuredWidth() - this.offText.getWidth()) - AndroidUtilities.m1146dp(19.0f), getMeasuredHeight() / 2.0f, Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4, this.resourcesProvider), 1.0f);
            } else {
                canvas2 = canvas;
                this.imageDrawable.draw(canvas2);
            }
            if (this.needDivider) {
                Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
                Paint paint = resourcesProvider != null ? resourcesProvider.getPaint("paintDivider") : Theme.dividerPaint;
                if (paint != null) {
                    canvas2.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.m1146dp(23.0f), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.m1146dp(23.0f) : 0), getMeasuredHeight() - 1, paint);
                }
            }
        }

        public int getColor() {
            return this.color;
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(50.0f), TLObject.FLAG_30));
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.imageDrawable.detach();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.imageDrawable.attach();
        }
    }

    public static class ThemeChooser extends FrameLayout {
        private final RecyclerListView.SelectionAdapter adapter;
        private final int currentAccount;
        private String currentEmoticon;
        private boolean dataLoaded;
        private TLRPC.WallPaper fallbackWallpaper;
        public final List items;
        private final RecyclerListView listView;
        private final HashMap loadingThemes;
        private final HashMap loadingWallpapers;
        private Utilities.Callback onEmoticonSelected;
        private FlickerLoadingView progressView;
        private final Theme.ResourcesProvider resourcesProvider;
        private boolean withRemovedStub;

        public void setWithRemovedStub(boolean z) {
            this.withRemovedStub = z;
        }

        public void setOnEmoticonSelected(Utilities.Callback<String> callback) {
            this.onEmoticonSelected = callback;
        }

        public void setSelectedEmoticon(String str, boolean z) {
            this.currentEmoticon = str;
            int i = -1;
            for (int i2 = 0; i2 < this.items.size(); i2++) {
                ChatThemeBottomSheet.ChatThemeItem chatThemeItem = (ChatThemeBottomSheet.ChatThemeItem) this.items.get(i2);
                boolean z2 = TextUtils.equals(this.currentEmoticon, chatThemeItem.getEmoticon()) || (TextUtils.isEmpty(str) && chatThemeItem.chatTheme.showAsDefaultStub);
                chatThemeItem.isSelected = z2;
                if (z2) {
                    i = i2;
                }
            }
            if (i >= 0 && !z && (this.listView.getLayoutManager() instanceof LinearLayoutManager)) {
                ((LinearLayoutManager) this.listView.getLayoutManager()).scrollToPositionWithOffset(i, (AndroidUtilities.displaySize.x - AndroidUtilities.m1146dp(83.0f)) / 2);
            }
            updateSelected();
        }

        public void setGalleryWallpaper(TLRPC.WallPaper wallPaper) {
            this.fallbackWallpaper = wallPaper;
            AndroidUtilities.forEachViews((RecyclerView) this.listView, new Consumer() { // from class: org.telegram.ui.ChannelColorActivity$ThemeChooser$$ExternalSyntheticLambda1
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$setGalleryWallpaper$0((View) obj);
                }
            });
            if (this.fallbackWallpaper != null) {
                if ((this.items.isEmpty() || ((ChatThemeBottomSheet.ChatThemeItem) this.items.get(0)).chatTheme.showAsDefaultStub) && this.withRemovedStub) {
                    this.items.add(0, new ChatThemeBottomSheet.ChatThemeItem(EmojiThemes.createChatThemesRemoved(this.currentAccount)));
                    this.adapter.notifyDataSetChanged();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setGalleryWallpaper$0(View view) {
            if (view instanceof ThemeSmallPreviewView) {
                ThemeSmallPreviewView themeSmallPreviewView = (ThemeSmallPreviewView) view;
                themeSmallPreviewView.setFallbackWallpaper(themeSmallPreviewView.chatThemeItem.chatTheme.showAsRemovedStub ? null : this.fallbackWallpaper);
            }
        }

        private void updateSelected() {
            int childAdapterPosition;
            for (int i = 0; i < this.listView.getChildCount(); i++) {
                View childAt = this.listView.getChildAt(i);
                if ((childAt instanceof ThemeSmallPreviewView) && (childAdapterPosition = this.listView.getChildAdapterPosition(childAt)) >= 0 && childAdapterPosition < this.items.size()) {
                    ((ThemeSmallPreviewView) childAt).setSelected(((ChatThemeBottomSheet.ChatThemeItem) this.items.get(childAdapterPosition)).isSelected, true);
                }
            }
        }

        public boolean isDark() {
            Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
            return resourcesProvider != null ? resourcesProvider.isDark() : Theme.isCurrentThemeDark();
        }

        public ThemeChooser(Context context, final boolean z, final int i, final Theme.ResourcesProvider resourcesProvider) {
            super(context);
            this.items = new ArrayList();
            this.loadingThemes = new HashMap();
            this.loadingWallpapers = new HashMap();
            this.currentAccount = i;
            this.resourcesProvider = resourcesProvider;
            if (!z) {
                FlickerLoadingView flickerLoadingView = new FlickerLoadingView(getContext(), resourcesProvider);
                this.progressView = flickerLoadingView;
                flickerLoadingView.setViewType(14);
                this.progressView.setVisibility(0);
                addView(this.progressView, LayoutHelper.createFrame(-1, 104.0f, 8388611, 16.0f, 13.0f, 16.0f, 6.0f));
            }
            RecyclerListView recyclerListView = new RecyclerListView(context, resourcesProvider) { // from class: org.telegram.ui.ChannelColorActivity.ThemeChooser.1
                @Override // org.telegram.p023ui.Components.RecyclerListView
                public Integer getSelectorColor(int i2) {
                    return 0;
                }
            };
            this.listView = recyclerListView;
            recyclerListView.setClipToPadding(false);
            recyclerListView.setPadding(AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(13.0f), AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(z ? 13.0f : 6.0f));
            if (z) {
                recyclerListView.setHasFixedSize(false);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.ChannelColorActivity.ThemeChooser.2
                    @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
                    public int getSpanSize(int i2) {
                        return 1;
                    }
                });
                recyclerListView.setLayoutManager(gridLayoutManager);
            } else {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(0);
                recyclerListView.setLayoutManager(linearLayoutManager);
                recyclerListView.setAlpha(0.0f);
            }
            RecyclerListView.SelectionAdapter selectionAdapter = new RecyclerListView.SelectionAdapter() { // from class: org.telegram.ui.ChannelColorActivity.ThemeChooser.3
                @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
                public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
                    return true;
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
                    return new RecyclerListView.Holder(new ThemeSmallPreviewView(viewGroup.getContext(), i, resourcesProvider, z ? 4 : 3) { // from class: org.telegram.ui.ChannelColorActivity.ThemeChooser.3.1
                        @Override // org.telegram.p023ui.Components.ThemeSmallPreviewView
                        protected String noThemeString() {
                            return LocaleController.getString(C2369R.string.ChannelNoWallpaper);
                        }

                        @Override // org.telegram.p023ui.Components.ThemeSmallPreviewView
                        protected int noThemeStringTextSize() {
                            if (z) {
                                return super.noThemeStringTextSize();
                            }
                            return 13;
                        }
                    });
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
                    ThemeSmallPreviewView themeSmallPreviewView = (ThemeSmallPreviewView) viewHolder.itemView;
                    Theme.ThemeInfo themeInfo = ((ChatThemeBottomSheet.ChatThemeItem) ThemeChooser.this.items.get(i2)).chatTheme.getThemeInfo(((ChatThemeBottomSheet.ChatThemeItem) ThemeChooser.this.items.get(i2)).themeIndex);
                    if (themeInfo != null && themeInfo.pathToFile != null && !themeInfo.previewParsed && new File(themeInfo.pathToFile).exists()) {
                        ThemeChooser.this.parseTheme(themeInfo);
                    }
                    ChatThemeBottomSheet.ChatThemeItem chatThemeItem = (ChatThemeBottomSheet.ChatThemeItem) ThemeChooser.this.items.get(i2);
                    themeSmallPreviewView.setEnabled(true);
                    themeSmallPreviewView.setBackgroundColor(Theme.getColor(Theme.key_dialogBackgroundGray));
                    themeSmallPreviewView.setItem(chatThemeItem, false);
                    themeSmallPreviewView.setSelected(chatThemeItem.isSelected, false);
                    themeSmallPreviewView.setFallbackWallpaper(chatThemeItem.chatTheme.showAsRemovedStub ? null : ThemeChooser.this.fallbackWallpaper);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
                    int adapterPosition = viewHolder.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= ThemeChooser.this.items.size()) {
                        return;
                    }
                    ChatThemeBottomSheet.ChatThemeItem chatThemeItem = (ChatThemeBottomSheet.ChatThemeItem) ThemeChooser.this.items.get(adapterPosition);
                    ((ThemeSmallPreviewView) viewHolder.itemView).setSelected(chatThemeItem.isSelected, false);
                    ((ThemeSmallPreviewView) viewHolder.itemView).setFallbackWallpaper(chatThemeItem.chatTheme.showAsRemovedStub ? null : ThemeChooser.this.fallbackWallpaper);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemCount() {
                    return ThemeChooser.this.items.size();
                }
            };
            this.adapter = selectionAdapter;
            recyclerListView.setAdapter(selectionAdapter);
            addView(recyclerListView, LayoutHelper.createFrame(-1, z ? -1.0f : 130.0f));
            recyclerListView.setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.ChannelColorActivity$ThemeChooser$$ExternalSyntheticLambda2
                @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
                public final void onItemClick(View view, int i2) {
                    this.f$0.lambda$new$1(z, view, i2);
                }
            });
            ChatThemeController chatThemeController = ChatThemeController.getInstance(i);
            chatThemeController.preloadAllWallpaperThumbs(true);
            chatThemeController.preloadAllWallpaperThumbs(false);
            chatThemeController.preloadAllWallpaperImages(true);
            chatThemeController.preloadAllWallpaperImages(false);
            chatThemeController.requestAllChatThemes(new C30064(i), true);
            updateState(false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(boolean z, View view, int i) {
            if (i < 0 || i >= this.items.size()) {
                return;
            }
            ChatThemeBottomSheet.ChatThemeItem chatThemeItem = (ChatThemeBottomSheet.ChatThemeItem) this.items.get(i);
            if (!z) {
                setSelectedEmoticon(chatThemeItem.getEmoticon(), true);
                if (view.getLeft() < this.listView.getPaddingLeft() + AndroidUtilities.m1146dp(24.0f)) {
                    RecyclerListView recyclerListView = this.listView;
                    recyclerListView.smoothScrollBy(-((recyclerListView.getPaddingLeft() + AndroidUtilities.m1146dp(48.0f)) - view.getLeft()), 0);
                } else if (view.getLeft() + view.getWidth() > (this.listView.getMeasuredWidth() - this.listView.getPaddingRight()) - AndroidUtilities.m1146dp(24.0f)) {
                    this.listView.smoothScrollBy((view.getLeft() + view.getWidth()) - ((this.listView.getMeasuredWidth() - this.listView.getPaddingRight()) - AndroidUtilities.m1146dp(48.0f)), 0);
                }
            }
            Utilities.Callback callback = this.onEmoticonSelected;
            if (callback != null) {
                callback.run(chatThemeItem.getEmoticon());
            }
        }

        /* renamed from: org.telegram.ui.ChannelColorActivity$ThemeChooser$4 */
        class C30064 implements ResultCallback {
            final /* synthetic */ int val$currentAccount;

            C30064(int i) {
                this.val$currentAccount = i;
            }

            @Override // org.telegram.tgnet.ResultCallback
            public void onComplete(final List list) {
                NotificationCenter.getInstance(this.val$currentAccount).doOnIdle(new Runnable() { // from class: org.telegram.ui.ChannelColorActivity$ThemeChooser$4$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onComplete$0(list);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onComplete$0(List list) {
                ThemeChooser.this.onDataLoaded(list);
            }

            @Override // org.telegram.tgnet.ResultCallback
            public void onError(TLRPC.TL_error tL_error) {
                Toast.makeText(ThemeChooser.this.getContext(), tL_error.text, 0).show();
            }
        }

        public void updateColors() {
            boolean zIsDark = isDark();
            for (int i = 0; i < this.items.size(); i++) {
                ((ChatThemeBottomSheet.ChatThemeItem) this.items.get(i)).themeIndex = zIsDark ? 1 : 0;
            }
            AndroidUtilities.forEachViews((RecyclerView) this.listView, new Consumer() { // from class: org.telegram.ui.ChannelColorActivity$ThemeChooser$$ExternalSyntheticLambda0
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$updateColors$2((View) obj);
                }
            });
            this.adapter.notifyDataSetChanged();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateColors$2(View view) {
            ((ThemeSmallPreviewView) view).setBackgroundColor(Theme.getColor(Theme.key_dialogBackgroundGray, this.resourcesProvider));
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Multi-variable type inference failed */
        public void onDataLoaded(List list) {
            if (list == null || list.isEmpty()) {
                return;
            }
            this.dataLoaded = true;
            this.items.clear();
            this.items.add(0, new ChatThemeBottomSheet.ChatThemeItem((EmojiThemes) list.get(0)));
            if (this.fallbackWallpaper != null && this.withRemovedStub) {
                this.items.add(0, new ChatThemeBottomSheet.ChatThemeItem(EmojiThemes.createChatThemesRemoved(this.currentAccount)));
            }
            Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
            int iIsDark = resourcesProvider != null ? resourcesProvider.isDark() : Theme.isCurrentThemeDark();
            for (int i = 1; i < list.size(); i++) {
                EmojiThemes emojiThemes = (EmojiThemes) list.get(i);
                ChatThemeBottomSheet.ChatThemeItem chatThemeItem = new ChatThemeBottomSheet.ChatThemeItem(emojiThemes);
                emojiThemes.loadPreviewColors(this.currentAccount);
                chatThemeItem.themeIndex = iIsDark;
                this.items.add(chatThemeItem);
            }
            int i2 = -1;
            for (int i3 = 0; i3 < this.items.size(); i3++) {
                ChatThemeBottomSheet.ChatThemeItem chatThemeItem2 = (ChatThemeBottomSheet.ChatThemeItem) this.items.get(i3);
                boolean z = TextUtils.equals(this.currentEmoticon, chatThemeItem2.getEmoticon()) || (TextUtils.isEmpty(this.currentEmoticon) && chatThemeItem2.chatTheme.showAsDefaultStub);
                chatThemeItem2.isSelected = z;
                if (z) {
                    i2 = i3;
                }
            }
            RecyclerListView.SelectionAdapter selectionAdapter = this.adapter;
            if (selectionAdapter != null) {
                selectionAdapter.notifyDataSetChanged();
            }
            this.listView.animate().alpha(1.0f).setDuration(150L).start();
            updateState(true);
            if (i2 < 0 || !(this.listView.getLayoutManager() instanceof LinearLayoutManager)) {
                return;
            }
            ((LinearLayoutManager) this.listView.getLayoutManager()).scrollToPositionWithOffset(i2, (AndroidUtilities.displaySize.x - AndroidUtilities.m1146dp(83.0f)) / 2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean parseTheme(final Theme.ThemeInfo themeInfo) {
            int iStringKeyToInt;
            int iIntValue;
            String[] strArrSplit;
            if (themeInfo == null || themeInfo.pathToFile == null) {
                return false;
            }
            boolean z = true;
            try {
                FileInputStream fileInputStream = new FileInputStream(new File(themeInfo.pathToFile));
                int i = 0;
                boolean z2 = false;
                while (true) {
                    try {
                        int i2 = fileInputStream.read(ThemesHorizontalListCell.bytes);
                        if (i2 == -1) {
                            break;
                        }
                        int i3 = i;
                        int i4 = 0;
                        int i5 = 0;
                        while (true) {
                            if (i4 >= i2) {
                                break;
                            }
                            byte[] bArr = ThemesHorizontalListCell.bytes;
                            if (bArr[i4] == 10) {
                                int i6 = i4 - i5;
                                int i7 = i6 + 1;
                                String str = new String(bArr, i5, i6, "UTF-8");
                                if (str.startsWith("WLS=")) {
                                    String strSubstring = str.substring(4);
                                    Uri uri = Uri.parse(strSubstring);
                                    themeInfo.slug = uri.getQueryParameter("slug");
                                    themeInfo.pathToWallpaper = new File(ApplicationLoader.getFilesDirFixed(), Utilities.MD5(strSubstring) + ".wp").getAbsolutePath();
                                    String queryParameter = uri.getQueryParameter("mode");
                                    if (queryParameter != null && (strArrSplit = queryParameter.toLowerCase().split(" ")) != null && strArrSplit.length > 0) {
                                        int i8 = 0;
                                        while (true) {
                                            if (i8 >= strArrSplit.length) {
                                                break;
                                            }
                                            if ("blur".equals(strArrSplit[i8])) {
                                                themeInfo.isBlured = z;
                                                break;
                                            }
                                            i8++;
                                        }
                                    }
                                    if (!TextUtils.isEmpty(uri.getQueryParameter("pattern"))) {
                                        try {
                                            String queryParameter2 = uri.getQueryParameter("bg_color");
                                            if (!TextUtils.isEmpty(queryParameter2)) {
                                                themeInfo.patternBgColor = Integer.parseInt(queryParameter2.substring(0, 6), 16) | (-16777216);
                                                if (queryParameter2.length() >= 13 && AndroidUtilities.isValidWallChar(queryParameter2.charAt(6))) {
                                                    themeInfo.patternBgGradientColor1 = Integer.parseInt(queryParameter2.substring(7, 13), 16) | (-16777216);
                                                }
                                                if (queryParameter2.length() >= 20 && AndroidUtilities.isValidWallChar(queryParameter2.charAt(13))) {
                                                    themeInfo.patternBgGradientColor2 = Integer.parseInt(queryParameter2.substring(14, 20), 16) | (-16777216);
                                                }
                                                if (queryParameter2.length() == 27 && AndroidUtilities.isValidWallChar(queryParameter2.charAt(20))) {
                                                    themeInfo.patternBgGradientColor3 = Integer.parseInt(queryParameter2.substring(21), 16) | (-16777216);
                                                }
                                            }
                                        } catch (Exception unused) {
                                        }
                                        try {
                                            String queryParameter3 = uri.getQueryParameter("rotation");
                                            if (!TextUtils.isEmpty(queryParameter3)) {
                                                themeInfo.patternBgGradientRotation = Utilities.parseInt((CharSequence) queryParameter3).intValue();
                                            }
                                        } catch (Exception unused2) {
                                        }
                                        String queryParameter4 = uri.getQueryParameter("intensity");
                                        if (!TextUtils.isEmpty(queryParameter4)) {
                                            themeInfo.patternIntensity = Utilities.parseInt((CharSequence) queryParameter4).intValue();
                                        }
                                        if (themeInfo.patternIntensity == 0) {
                                            themeInfo.patternIntensity = 50;
                                        }
                                    }
                                } else {
                                    if (str.startsWith("WPS")) {
                                        themeInfo.previewWallpaperOffset = i7 + i3;
                                        z2 = true;
                                        break;
                                    }
                                    int iIndexOf = str.indexOf(61);
                                    if (iIndexOf != -1 && ((iStringKeyToInt = ThemeColors.stringKeyToInt(str.substring(0, iIndexOf))) == Theme.key_chat_inBubble || iStringKeyToInt == Theme.key_chat_outBubble || iStringKeyToInt == Theme.key_chat_wallpaper || iStringKeyToInt == Theme.key_chat_wallpaper_gradient_to1 || iStringKeyToInt == Theme.key_chat_wallpaper_gradient_to2 || iStringKeyToInt == Theme.key_chat_wallpaper_gradient_to3)) {
                                        String strSubstring2 = str.substring(iIndexOf + 1);
                                        if (strSubstring2.length() > 0 && strSubstring2.charAt(0) == '#') {
                                            try {
                                                iIntValue = Color.parseColor(strSubstring2);
                                            } catch (Exception unused3) {
                                                iIntValue = Utilities.parseInt((CharSequence) strSubstring2).intValue();
                                            }
                                        } else {
                                            iIntValue = Utilities.parseInt((CharSequence) strSubstring2).intValue();
                                        }
                                        if (iStringKeyToInt == Theme.key_chat_inBubble) {
                                            themeInfo.setPreviewInColor(iIntValue);
                                        } else if (iStringKeyToInt == Theme.key_chat_outBubble) {
                                            themeInfo.setPreviewOutColor(iIntValue);
                                        } else if (iStringKeyToInt == Theme.key_chat_wallpaper) {
                                            themeInfo.setPreviewBackgroundColor(iIntValue);
                                        } else if (iStringKeyToInt == Theme.key_chat_wallpaper_gradient_to1) {
                                            themeInfo.previewBackgroundGradientColor1 = iIntValue;
                                        } else if (iStringKeyToInt == Theme.key_chat_wallpaper_gradient_to2) {
                                            themeInfo.previewBackgroundGradientColor2 = iIntValue;
                                        } else if (iStringKeyToInt == Theme.key_chat_wallpaper_gradient_to3) {
                                            themeInfo.previewBackgroundGradientColor3 = iIntValue;
                                        }
                                    }
                                }
                                i5 += i7;
                                i3 += i7;
                            }
                            i4++;
                            z = true;
                        }
                        if (z2 || i == i3) {
                            break;
                        }
                        fileInputStream.getChannel().position(i3);
                        i = i3;
                        z = true;
                    } finally {
                    }
                }
                fileInputStream.close();
            } catch (Throwable th) {
                FileLog.m1160e(th);
            }
            if (themeInfo.pathToWallpaper != null && !themeInfo.badWallpaper && !new File(themeInfo.pathToWallpaper).exists()) {
                if (this.loadingWallpapers.containsKey(themeInfo)) {
                    return false;
                }
                this.loadingWallpapers.put(themeInfo, themeInfo.slug);
                TL_account.getWallPaper getwallpaper = new TL_account.getWallPaper();
                TLRPC.TL_inputWallPaperSlug tL_inputWallPaperSlug = new TLRPC.TL_inputWallPaperSlug();
                tL_inputWallPaperSlug.slug = themeInfo.slug;
                getwallpaper.wallpaper = tL_inputWallPaperSlug;
                ConnectionsManager.getInstance(themeInfo.account).sendRequest(getwallpaper, new RequestDelegate() { // from class: org.telegram.ui.ChannelColorActivity$ThemeChooser$$ExternalSyntheticLambda3
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$parseTheme$4(themeInfo, tLObject, tL_error);
                    }
                });
                return false;
            }
            themeInfo.previewParsed = true;
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$parseTheme$4(final Theme.ThemeInfo themeInfo, final TLObject tLObject, TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.ChannelColorActivity$ThemeChooser$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$parseTheme$3(tLObject, themeInfo);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$parseTheme$3(TLObject tLObject, Theme.ThemeInfo themeInfo) {
            if (tLObject instanceof TLRPC.TL_wallPaper) {
                TLRPC.WallPaper wallPaper = (TLRPC.WallPaper) tLObject;
                String attachFileName = FileLoader.getAttachFileName(wallPaper.document);
                if (this.loadingThemes.containsKey(attachFileName)) {
                    return;
                }
                this.loadingThemes.put(attachFileName, themeInfo);
                FileLoader.getInstance(themeInfo.account).loadFile(wallPaper.document, wallPaper, 1, 1);
                return;
            }
            themeInfo.badWallpaper = true;
        }

        private void updateState(boolean z) {
            if (!this.dataLoaded) {
                AndroidUtilities.updateViewVisibilityAnimated(this.progressView, true, 1.0f, true, z);
            } else {
                AndroidUtilities.updateViewVisibilityAnimated(this.progressView, false, 1.0f, true, z);
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), i2);
        }
    }

    public ChannelColorActivity setOnApplied(BaseFragment baseFragment) {
        this.bulletinFragment = baseFragment;
        return this;
    }

    private void showBulletin() {
        BaseFragment baseFragment = this.bulletinFragment;
        if (baseFragment != null) {
            if (baseFragment instanceof ChatEditActivity) {
                ((ChatEditActivity) baseFragment).updateColorCell();
            }
            BulletinFactory.m1267of(this.bulletinFragment).createSimpleBulletin(C2369R.raw.contact_check, LocaleController.getString(this.isGroup ? C2369R.string.GroupAppearanceUpdated : C2369R.string.ChannelAppearanceUpdated)).show();
            this.bulletinFragment = null;
        }
    }

    public void updateColors() {
        this.actionBar.setBackgroundColor(getThemedColor(Theme.key_actionBarDefault));
        this.actionBar.setTitleColor(getThemedColor(Theme.key_actionBarDefaultTitle));
        this.actionBar.setItemsColor(getThemedColor(Theme.key_actionBarDefaultIcon), false);
        this.actionBar.setItemsBackgroundColor(getThemedColor(Theme.key_actionBarDefaultSelector), false);
        RecyclerListView recyclerListView = this.listView;
        int i = Theme.key_windowBackgroundGray;
        recyclerListView.setBackgroundColor(getThemedColor(i));
        this.adapter.notifyDataSetChanged();
        AndroidUtilities.forEachViews((RecyclerView) this.listView, new Consumer() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda6
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                this.f$0.updateColors((View) obj);
            }
        });
        this.buttonContainer.setBackgroundColor(getThemedColor(i));
        this.button.updateColors();
        setNavigationBarColor(getNavigationBarColor());
    }

    public boolean hasUnsavedChanged() {
        return (this.currentReplyColor == this.selectedReplyColor && this.currentReplyEmoji == this.selectedReplyEmoji && this.currentProfileColor == this.selectedProfileColor && this.currentProfileEmoji == this.selectedProfileEmoji && DialogObject.emojiStatusesEqual(this.currentStatusEmoji, this.selectedStatusEmoji) && ChatThemeController.wallpaperEquals(this.currentWallpaper, this.selectedWallpaper)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateColors(View view) {
        if (view instanceof TextInfoPrivacyCell) {
            ((TextInfoPrivacyCell) view).setBackground(Theme.getThemedDrawableByKey(getContext(), this.listView.getChildAdapterPosition(view) == this.statusHintRow ? C2369R.drawable.greydivider_bottom : C2369R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow, this.resourceProvider));
            return;
        }
        view.setBackgroundColor(getThemedColor(Theme.key_windowBackgroundWhite));
        if (view instanceof EmojiCell) {
            ((EmojiCell) view).updateColors();
            return;
        }
        if (view instanceof TextCell) {
            ((TextCell) view).updateColors();
        } else if (view instanceof PeerColorPicker) {
            ((PeerColorPicker) view).updateColors();
        } else if (view instanceof ThemeChooser) {
            ((ThemeChooser) view).updateColors();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class PeerColorPicker extends FrameLayout {
        public final RecyclerListView.SelectionAdapter adapter;
        private final int currentAccount;
        public final LinearLayoutManager layoutManager;
        public final RecyclerListView listView;
        private final Theme.ResourcesProvider resourcesProvider;
        private int selectedPosition;

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (getParent() != null) {
                ViewParent parent = getParent();
                boolean z = true;
                if (!canScrollHorizontally(-1) && !canScrollHorizontally(1)) {
                    z = false;
                }
                parent.requestDisallowInterceptTouchEvent(z);
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        public PeerColorPicker(final Context context, final int i, final Theme.ResourcesProvider resourcesProvider) {
            super(context);
            this.currentAccount = i;
            this.resourcesProvider = resourcesProvider;
            RecyclerListView recyclerListView = new RecyclerListView(context, resourcesProvider) { // from class: org.telegram.ui.ChannelColorActivity.PeerColorPicker.1
                @Override // org.telegram.p023ui.Components.RecyclerListView
                public Integer getSelectorColor(int i2) {
                    return 0;
                }
            };
            this.listView = recyclerListView;
            recyclerListView.setPadding(AndroidUtilities.m1146dp(6.0f), AndroidUtilities.m1146dp(5.0f), AndroidUtilities.m1146dp(6.0f), 0);
            recyclerListView.setClipToPadding(false);
            RecyclerListView.SelectionAdapter selectionAdapter = new RecyclerListView.SelectionAdapter() { // from class: org.telegram.ui.ChannelColorActivity.PeerColorPicker.2
                @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
                public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
                    return true;
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
                    return new RecyclerListView.Holder(PeerColorPicker.this.new ColorCell(context));
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
                    ColorCell colorCell = (ColorCell) viewHolder.itemView;
                    colorCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite, resourcesProvider));
                    colorCell.setSelected(i2 == PeerColorPicker.this.selectedPosition, false);
                    MessagesController.PeerColors peerColors = MessagesController.getInstance(i).peerColors;
                    if (peerColors == null || i2 < 0 || i2 >= peerColors.colors.size()) {
                        return;
                    }
                    colorCell.set(peerColors.colors.get(i2));
                }

                @Override // androidx.recyclerview.widget.RecyclerView.Adapter
                public int getItemCount() {
                    MessagesController.PeerColors peerColors = MessagesController.getInstance(i).peerColors;
                    if (peerColors == null) {
                        return 0;
                    }
                    return peerColors.colors.size();
                }
            };
            this.adapter = selectionAdapter;
            recyclerListView.setAdapter(selectionAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            this.layoutManager = linearLayoutManager;
            linearLayoutManager.setOrientation(0);
            recyclerListView.setLayoutManager(linearLayoutManager);
            addView(recyclerListView, LayoutHelper.createFrame(-1, -1.0f));
        }

        public void setSelected(int i, boolean z) {
            setSelectedPosition(toPosition(i), z);
        }

        public void setSelectedPosition(int i, final boolean z) {
            if (i != this.selectedPosition) {
                this.selectedPosition = i;
                if (!z) {
                    this.layoutManager.scrollToPositionWithOffset(i, (AndroidUtilities.displaySize.x - AndroidUtilities.m1146dp(56.0f)) / 2);
                }
                AndroidUtilities.forEachViews((RecyclerView) this.listView, new Consumer() { // from class: org.telegram.ui.ChannelColorActivity$PeerColorPicker$$ExternalSyntheticLambda1
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$setSelectedPosition$0(z, (View) obj);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSelectedPosition$0(boolean z, View view) {
            ((ColorCell) view).setSelected(this.listView.getChildAdapterPosition(view) == this.selectedPosition, z);
        }

        public int toPosition(int i) {
            MessagesController.PeerColors peerColors = MessagesController.getInstance(this.currentAccount).peerColors;
            if (peerColors == null) {
                return 0;
            }
            for (int i2 = 0; i2 < peerColors.colors.size(); i2++) {
                if (peerColors.colors.get(i2).f1456id == i) {
                    return i2;
                }
            }
            return 0;
        }

        public void updateColors() {
            final MessagesController.PeerColors peerColors = MessagesController.getInstance(this.currentAccount).peerColors;
            AndroidUtilities.forEachViews((RecyclerView) this.listView, new Consumer() { // from class: org.telegram.ui.ChannelColorActivity$PeerColorPicker$$ExternalSyntheticLambda0
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$updateColors$1(peerColors, (View) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateColors$1(MessagesController.PeerColors peerColors, View view) {
            if (view instanceof ColorCell) {
                ColorCell colorCell = (ColorCell) view;
                colorCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite, this.resourcesProvider));
                int childAdapterPosition = this.listView.getChildAdapterPosition(view);
                if (peerColors == null || childAdapterPosition < 0 || childAdapterPosition >= peerColors.colors.size()) {
                    return;
                }
                colorCell.set(peerColors.colors.get(childAdapterPosition));
            }
        }

        public int toColorId(int i) {
            MessagesController.PeerColors peerColors = MessagesController.getInstance(this.currentAccount).peerColors;
            if (peerColors == null || i < 0 || i >= peerColors.colors.size()) {
                return 0;
            }
            return peerColors.colors.get(i).f1456id;
        }

        private class ColorCell extends View {
            private final Paint backgroundPaint;
            private final ButtonBounce bounce;
            private final Path circlePath;
            private final Path color2Path;
            private boolean hasColor2;
            private boolean hasColor3;
            private final Paint paint1;
            private final Paint paint2;
            private final Paint paint3;
            private boolean selected;
            private final AnimatedFloat selectedT;

            public ColorCell(Context context) {
                super(context);
                Paint paint = new Paint(1);
                this.backgroundPaint = paint;
                this.paint1 = new Paint(1);
                this.paint2 = new Paint(1);
                this.paint3 = new Paint(1);
                this.circlePath = new Path();
                this.color2Path = new Path();
                this.bounce = new ButtonBounce(this);
                this.selectedT = new AnimatedFloat(this, 0L, 320L, CubicBezierInterpolator.EASE_OUT_QUINT);
                paint.setStyle(Paint.Style.STROKE);
            }

            @Override // android.view.View
            public void setBackgroundColor(int i) {
                this.backgroundPaint.setColor(i);
            }

            public void set(MessagesController.PeerColor peerColor) {
                if ((PeerColorPicker.this.resourcesProvider != null ? PeerColorPicker.this.resourcesProvider.isDark() : Theme.isCurrentThemeDark()) && peerColor.hasColor2() && !peerColor.hasColor3()) {
                    this.paint1.setColor(peerColor.getColor(1, PeerColorPicker.this.resourcesProvider));
                    this.paint2.setColor(peerColor.getColor(0, PeerColorPicker.this.resourcesProvider));
                } else {
                    this.paint1.setColor(peerColor.getColor(0, PeerColorPicker.this.resourcesProvider));
                    this.paint2.setColor(peerColor.getColor(1, PeerColorPicker.this.resourcesProvider));
                }
                this.paint3.setColor(peerColor.getColor(2, PeerColorPicker.this.resourcesProvider));
                this.hasColor2 = peerColor.hasColor2();
                this.hasColor3 = peerColor.hasColor3();
            }

            public void setSelected(boolean z, boolean z2) {
                this.selected = z;
                if (!z2) {
                    this.selectedT.set(z, true);
                }
                invalidate();
            }

            @Override // android.view.View
            protected void onMeasure(int i, int i2) {
                setMeasuredDimension(AndroidUtilities.m1146dp(56.0f), AndroidUtilities.m1146dp(56.0f));
                this.circlePath.rewind();
                this.circlePath.addCircle(getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f, AndroidUtilities.m1146dp(20.0f), Path.Direction.CW);
                this.color2Path.rewind();
                this.color2Path.moveTo(getMeasuredWidth(), 0.0f);
                this.color2Path.lineTo(getMeasuredWidth(), getMeasuredHeight());
                this.color2Path.lineTo(0.0f, getMeasuredHeight());
                this.color2Path.close();
            }

            @Override // android.view.View
            protected void dispatchDraw(Canvas canvas) {
                canvas.save();
                float scale = this.bounce.getScale(0.05f);
                canvas.scale(scale, scale, getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f);
                canvas.save();
                canvas.clipPath(this.circlePath);
                canvas.drawPaint(this.paint1);
                if (this.hasColor2) {
                    canvas.drawPath(this.color2Path, this.paint2);
                }
                canvas.restore();
                if (this.hasColor3) {
                    canvas.save();
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set((getMeasuredWidth() - AndroidUtilities.m1146dp(12.4f)) / 2.0f, (getMeasuredHeight() - AndroidUtilities.m1146dp(12.4f)) / 2.0f, (getMeasuredWidth() + AndroidUtilities.m1146dp(12.4f)) / 2.0f, (getMeasuredHeight() + AndroidUtilities.m1146dp(12.4f)) / 2.0f);
                    canvas.rotate(45.0f, getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f);
                    canvas.drawRoundRect(rectF, AndroidUtilities.m1146dp(2.33f), AndroidUtilities.m1146dp(2.33f), this.paint3);
                    canvas.restore();
                }
                float f = this.selectedT.set(this.selected);
                if (f > 0.0f) {
                    this.backgroundPaint.setStrokeWidth(AndroidUtilities.dpf2(2.0f));
                    canvas.drawCircle(getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f, AndroidUtilities.lerp(AndroidUtilities.m1146dp(20.0f) + (this.backgroundPaint.getStrokeWidth() * 0.5f), AndroidUtilities.m1146dp(20.0f) - (this.backgroundPaint.getStrokeWidth() * 2.0f), f), this.backgroundPaint);
                }
                canvas.restore();
            }

            @Override // android.view.View
            public void setPressed(boolean z) {
                super.setPressed(z);
                this.bounce.setPressed(z);
            }
        }
    }

    public void toggleTheme() {
        FrameLayout frameLayout = (FrameLayout) getParentActivity().getWindow().getDecorView();
        final Bitmap bitmapCreateBitmap = Bitmap.createBitmap(frameLayout.getWidth(), frameLayout.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmapCreateBitmap);
        this.dayNightItem.setAlpha(0.0f);
        frameLayout.draw(canvas);
        this.dayNightItem.setAlpha(1.0f);
        final Paint paint = new Paint(1);
        paint.setColor(-16777216);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        final Paint paint2 = new Paint(1);
        paint2.setFilterBitmap(true);
        int[] iArr = new int[2];
        this.dayNightItem.getLocationInWindow(iArr);
        final float f = iArr[0];
        final float f2 = iArr[1];
        final float measuredWidth = f + (this.dayNightItem.getMeasuredWidth() / 2.0f);
        final float measuredHeight = f2 + (this.dayNightItem.getMeasuredHeight() / 2.0f);
        final float fMax = Math.max(bitmapCreateBitmap.getHeight(), bitmapCreateBitmap.getWidth()) + AndroidUtilities.navigationBarHeight;
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        paint2.setShader(new BitmapShader(bitmapCreateBitmap, tileMode, tileMode));
        View view = new View(getContext()) { // from class: org.telegram.ui.ChannelColorActivity.7
            @Override // android.view.View
            protected void onDraw(Canvas canvas2) {
                super.onDraw(canvas2);
                if (ChannelColorActivity.this.isDark) {
                    if (ChannelColorActivity.this.changeDayNightViewProgress > 0.0f) {
                        canvas.drawCircle(measuredWidth, measuredHeight, fMax * ChannelColorActivity.this.changeDayNightViewProgress, paint);
                    }
                    canvas2.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint2);
                } else {
                    canvas2.drawCircle(measuredWidth, measuredHeight, fMax * (1.0f - ChannelColorActivity.this.changeDayNightViewProgress), paint2);
                }
                canvas2.save();
                canvas2.translate(f, f2);
                ChannelColorActivity.this.dayNightItem.draw(canvas2);
                canvas2.restore();
            }
        };
        this.changeDayNightView = view;
        view.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda17
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return ChannelColorActivity.$r8$lambda$hmVvo36VO0QvCdUVJUydBvkeXWs(view2, motionEvent);
            }
        });
        this.changeDayNightViewProgress = 0.0f;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.changeDayNightViewAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.ChannelColorActivity.8
            boolean changedNavigationBarColor = false;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ChannelColorActivity.this.changeDayNightViewProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ChannelColorActivity.this.changeDayNightView.invalidate();
                if (this.changedNavigationBarColor || ChannelColorActivity.this.changeDayNightViewProgress <= 0.5f) {
                    return;
                }
                this.changedNavigationBarColor = true;
            }
        });
        this.changeDayNightViewAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.ChannelColorActivity.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (ChannelColorActivity.this.changeDayNightView != null) {
                    if (ChannelColorActivity.this.changeDayNightView.getParent() != null) {
                        ((ViewGroup) ChannelColorActivity.this.changeDayNightView.getParent()).removeView(ChannelColorActivity.this.changeDayNightView);
                    }
                    ChannelColorActivity.this.changeDayNightView = null;
                }
                ChannelColorActivity.this.changeDayNightViewAnimator = null;
                super.onAnimationEnd(animator);
            }
        });
        this.changeDayNightViewAnimator.setDuration(400L);
        this.changeDayNightViewAnimator.setInterpolator(Easings.easeInOutQuad);
        this.changeDayNightViewAnimator.start();
        frameLayout.addView(this.changeDayNightView, new ViewGroup.LayoutParams(-1, -1));
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda18
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                this.f$0.lambda$toggleTheme$17();
            }
        });
    }

    public static /* synthetic */ boolean $r8$lambda$hmVvo36VO0QvCdUVJUydBvkeXWs(View view, MotionEvent motionEvent) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toggleTheme$17() throws IOException {
        Theme.ResourcesProvider resourcesProvider = this.resourceProvider;
        if (resourcesProvider instanceof ThemeDelegate) {
            ((ThemeDelegate) resourcesProvider).toggle();
        } else {
            this.isDark = !this.isDark;
            updateThemeColors();
        }
        setForceDark(this.isDark, true);
        updateColors();
    }

    public void setForceDark(boolean z, boolean z2) {
        if (this.forceDark == z) {
            return;
        }
        this.forceDark = z;
        if (z2) {
            RLottieDrawable rLottieDrawable = this.sunDrawable;
            rLottieDrawable.setCustomEndFrame(z ? rLottieDrawable.getFramesCount() : 0);
            RLottieDrawable rLottieDrawable2 = this.sunDrawable;
            if (rLottieDrawable2 != null) {
                rLottieDrawable2.start();
                return;
            }
            return;
        }
        int framesCount = z ? this.sunDrawable.getFramesCount() - 1 : 0;
        this.sunDrawable.setCurrentFrame(framesCount, false, true);
        this.sunDrawable.setCustomEndFrame(framesCount);
        ActionBarMenuItem actionBarMenuItem = this.dayNightItem;
        if (actionBarMenuItem != null) {
            actionBarMenuItem.invalidate();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateThemeColors() throws java.io.IOException {
        /*
            r7 = this;
            android.content.Context r0 = org.telegram.messenger.ApplicationLoader.applicationContext
            java.lang.String r1 = "themeconfig"
            r2 = 0
            android.content.SharedPreferences r0 = r0.getSharedPreferences(r1, r2)
            java.lang.String r1 = "lastDayTheme"
            java.lang.String r3 = "Blue"
            java.lang.String r1 = r0.getString(r1, r3)
            org.telegram.ui.ActionBar.Theme$ThemeInfo r4 = org.telegram.p023ui.ActionBar.Theme.getTheme(r1)
            if (r4 == 0) goto L21
            org.telegram.ui.ActionBar.Theme$ThemeInfo r4 = org.telegram.p023ui.ActionBar.Theme.getTheme(r1)
            boolean r4 = r4.isDark()
            if (r4 == 0) goto L22
        L21:
            r1 = r3
        L22:
            java.lang.String r4 = "lastDarkTheme"
            java.lang.String r5 = "Dark Blue"
            java.lang.String r0 = r0.getString(r4, r5)
            org.telegram.ui.ActionBar.Theme$ThemeInfo r4 = org.telegram.p023ui.ActionBar.Theme.getTheme(r0)
            if (r4 == 0) goto L3a
            org.telegram.ui.ActionBar.Theme$ThemeInfo r4 = org.telegram.p023ui.ActionBar.Theme.getTheme(r0)
            boolean r4 = r4.isDark()
            if (r4 != 0) goto L3b
        L3a:
            r0 = r5
        L3b:
            org.telegram.ui.ActionBar.Theme$ThemeInfo r4 = org.telegram.p023ui.ActionBar.Theme.getActiveTheme()
            boolean r6 = r1.equals(r0)
            if (r6 == 0) goto L5e
            boolean r4 = r4.isDark()
            if (r4 != 0) goto L5c
            boolean r4 = r1.equals(r5)
            if (r4 != 0) goto L5c
            java.lang.String r4 = "Night"
            boolean r4 = r1.equals(r4)
            if (r4 == 0) goto L5a
            goto L5c
        L5a:
            r3 = r1
            goto L60
        L5c:
            r5 = r0
            goto L60
        L5e:
            r5 = r0
            goto L5a
        L60:
            boolean r0 = r7.isDark
            if (r0 == 0) goto L69
            org.telegram.ui.ActionBar.Theme$ThemeInfo r0 = org.telegram.p023ui.ActionBar.Theme.getTheme(r5)
            goto L6d
        L69:
            org.telegram.ui.ActionBar.Theme$ThemeInfo r0 = org.telegram.p023ui.ActionBar.Theme.getTheme(r3)
        L6d:
            android.util.SparseIntArray r1 = r7.currentColors
            r1.clear()
            r1 = 1
            java.lang.String[] r1 = new java.lang.String[r1]
            java.lang.String r3 = r0.assetName
            r4 = 0
            if (r3 == 0) goto L7f
            android.util.SparseIntArray r1 = org.telegram.p023ui.ActionBar.Theme.getThemeFileValues(r4, r3, r1)
            goto L8a
        L7f:
            java.io.File r3 = new java.io.File
            java.lang.String r5 = r0.pathToFile
            r3.<init>(r5)
            android.util.SparseIntArray r1 = org.telegram.p023ui.ActionBar.Theme.getThemeFileValues(r3, r4, r1)
        L8a:
            int[] r3 = org.telegram.p023ui.ActionBar.Theme.getDefaultColors()
            if (r3 == 0) goto L9e
            r4 = 0
        L91:
            int r5 = r3.length
            if (r4 >= r5) goto L9e
            android.util.SparseIntArray r5 = r7.currentColors
            r6 = r3[r4]
            r5.put(r4, r6)
            int r4 = r4 + 1
            goto L91
        L9e:
            if (r1 == 0) goto Lc2
            r3 = 0
        La1:
            int r4 = r1.size()
            if (r3 >= r4) goto Lb7
            android.util.SparseIntArray r4 = r7.currentColors
            int r5 = r1.keyAt(r3)
            int r6 = r1.valueAt(r3)
            r4.put(r5, r6)
            int r3 = r3 + 1
            goto La1
        Lb7:
            org.telegram.ui.ActionBar.Theme$ThemeAccent r0 = r0.getAccent(r2)
            if (r0 == 0) goto Lc2
            android.util.SparseIntArray r2 = r7.currentColors
            r0.fillAccentColors(r1, r2)
        Lc2:
            android.graphics.Paint r0 = r7.dividerPaint
            int r1 = org.telegram.p023ui.ActionBar.Theme.key_divider
            org.telegram.ui.ActionBar.Theme$ResourcesProvider r2 = r7.resourceProvider
            int r1 = org.telegram.p023ui.ActionBar.Theme.getColor(r1, r2)
            r0.setColor(r1)
            android.graphics.drawable.Drawable r0 = r7.backgroundDrawable
            int r1 = r7.currentAccount
            org.telegram.tgnet.TLRPC$WallPaper r2 = r7.selectedWallpaper
            boolean r3 = r7.isDark
            android.graphics.drawable.Drawable r0 = org.telegram.p023ui.Stories.recorder.PreviewView.getBackgroundDrawable(r0, r1, r2, r3)
            r7.backgroundDrawable = r0
            int r0 = r7.messagesPreviewRow
            android.view.View r0 = r7.findChildAt(r0)
            boolean r1 = r0 instanceof org.telegram.p023ui.Cells.ThemePreviewMessagesCell
            if (r1 == 0) goto Lee
            org.telegram.ui.Cells.ThemePreviewMessagesCell r0 = (org.telegram.p023ui.Cells.ThemePreviewMessagesCell) r0
            android.graphics.drawable.Drawable r1 = r7.backgroundDrawable
            r0.setOverrideBackground(r1)
        Lee:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.ChannelColorActivity.updateThemeColors():void");
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.chatWasBoostedByUser) {
            if (this.dialogId == ((Long) objArr[2]).longValue()) {
                updateBoostsAndLevels((TL_stories.TL_premium_boostsStatus) objArr[0]);
                return;
            }
            return;
        }
        if (i == NotificationCenter.boostByChannelCreated) {
            if (((Boolean) objArr[1]).booleanValue()) {
                return;
            }
            getMessagesController().getBoostsController().getBoostsStats(this.dialogId, new Consumer() { // from class: org.telegram.ui.ChannelColorActivity$$ExternalSyntheticLambda5
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.updateBoostsAndLevels((TL_stories.TL_premium_boostsStatus) obj);
                }
            });
        } else if (i == NotificationCenter.dialogDeleted) {
            if (this.dialogId == ((Long) objArr[0]).longValue()) {
                INavigationLayout iNavigationLayout = this.parentLayout;
                if (iNavigationLayout != null && iNavigationLayout.getLastFragment() == this) {
                    lambda$onBackPressed$371();
                } else {
                    removeSelfFromStack();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBoostsAndLevels(TL_stories.TL_premium_boostsStatus tL_premium_boostsStatus) {
        if (tL_premium_boostsStatus != null) {
            TLRPC.Chat chat = getMessagesController().getChat(Long.valueOf(-this.dialogId));
            this.boostsStatus = tL_premium_boostsStatus;
            int i = tL_premium_boostsStatus.level;
            this.currentLevel = i;
            if (chat != null) {
                chat.level = i;
            }
            Adapter adapter = this.adapter;
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            updateButton(true);
        }
    }
}
