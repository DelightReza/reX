package org.telegram.p023ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.components.adapters.ListAdapterMD3;
import com.google.android.exoplayer2.util.Consumer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DocumentObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.utils.tlutils.AmountUtils$Amount;
import org.telegram.messenger.utils.tlutils.AmountUtils$Currency;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.SimpleTextView;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.ActionBar.ThemeDescription;
import org.telegram.p023ui.Cells.ChatMessageCell;
import org.telegram.p023ui.Cells.HeaderCell;
import org.telegram.p023ui.Cells.TextCell;
import org.telegram.p023ui.Cells.TextInfoPrivacyCell;
import org.telegram.p023ui.Cells.ThemePreviewMessagesCell;
import org.telegram.p023ui.Components.AnimatedColor;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.AnimatedEmojiSpan;
import org.telegram.p023ui.Components.AnimatedFloat;
import org.telegram.p023ui.Components.AvatarDrawable;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.Bulletin;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.ButtonBounce;
import org.telegram.p023ui.Components.ColoredImageSpan;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.Easings;
import org.telegram.p023ui.Components.FilledTabsView;
import org.telegram.p023ui.Components.FlickerLoadingView;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.LinkSpanDrawable;
import org.telegram.p023ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p023ui.Components.Premium.PremiumGradient;
import org.telegram.p023ui.Components.RLottieDrawable;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.p023ui.Components.SimpleThemeDescription;
import org.telegram.p023ui.Components.Text;
import org.telegram.p023ui.Components.TextHelper;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import org.telegram.p023ui.Components.ViewPagerFixed;
import org.telegram.p023ui.Gifts.GiftSheet;
import org.telegram.p023ui.Gifts.ResaleGiftsFragment;
import org.telegram.p023ui.SelectAnimatedEmojiDialog;
import org.telegram.p023ui.Stars.StarGiftPatterns;
import org.telegram.p023ui.Stars.StarGiftSheet;
import org.telegram.p023ui.Stars.StarsController;
import org.telegram.p023ui.Stars.StarsIntroActivity;
import org.telegram.p023ui.Stories.StoriesUtilities;
import org.telegram.p023ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_stars;

/* loaded from: classes5.dex */
public class PeerColorActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private static final float[] particles = {-18.0f, -24.66f, 24.0f, 0.4f, 5.33f, -53.0f, 28.0f, 0.38f, -4.0f, -86.0f, 19.0f, 0.18f, 31.0f, -30.0f, 21.0f, 0.35f, 12.0f, -3.0f, 24.0f, 0.18f, 30.0f, -73.0f, 19.0f, 0.3f, 43.0f, -101.0f, 16.0f, 0.1f, -50.0f, 1.33f, 20.0f, 0.22f, -58.0f, -33.0f, 24.0f, 0.22f, -35.0f, -62.0f, 25.0f, 0.22f, -59.0f, -88.0f, 19.0f, 0.18f, -86.0f, -61.0f, 19.0f, 0.1f, -90.0f, -14.33f, 19.66f, 0.18f};
    private FrameLayout actionBarContainer;
    private boolean applying;
    private boolean applyingName;
    private boolean applyingProfile;
    private ImageView backButton;
    private BaseFragment bulletinFragment;
    private View changeDayNightView;
    private ValueAnimator changeDayNightViewAnimator;
    private float changeDayNightViewProgress;
    private ColoredActionBar colorBar;
    private FrameLayout contentView;
    private final SparseIntArray currentColors = new SparseIntArray();
    private ImageView dayNightItem;
    private final long dialogId;
    private boolean forceDark;
    private final StarsController.GiftsList gifts;
    private final StarsController.GiftsList giftsWithPeerColor;
    private final boolean isChannel;
    private boolean isDark;
    public boolean loading;
    private final Theme.MessageDrawable msgInDrawable;
    private final Theme.MessageDrawable msgInDrawableSelected;
    public Page namePage;
    private Theme.ResourcesProvider parentResourcesProvider;
    public Page profilePage;
    private boolean startAtProfile;
    private RLottieDrawable sunDrawable;
    private FilledTabsView tabsView;
    private SimpleTextView titleView;
    private ViewPagerFixed viewPager;

    public Page getCurrentPage() {
        return this.viewPager.getCurrentPosition() == 0 ? this.profilePage : this.namePage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class Page extends FrameLayout {
        private int actionBarHeight;
        private ButtonWithCounterView button;
        private CharSequence buttonCollectible;
        private FrameLayout buttonContainer;
        private CharSequence buttonLocked;
        int buttonRow;
        private View buttonShadow;
        private CharSequence buttonUnlocked;
        int clearRow;
        int colorPickerRow;
        int giftsCount;
        int giftsEmptyRow;
        int giftsEndRow;
        int giftsHeaderRow;
        int giftsInfoRow;
        int giftsLoadingEndRow;
        int giftsLoadingStartRow;
        int giftsStartRow;
        int giftsTabsRow;
        int iconRow;
        private final HashMap index2gift;
        int info2Row;
        int infoRow;
        private GridLayoutManager layoutManager;
        private RecyclerView.Adapter listAdapter;
        private RecyclerListView listView;
        private ThemePreviewMessagesCell messagesCellPreview;
        private PeerColorGrid peerColorPicker;
        private ProfilePreview profilePreview;
        private ResaleGiftsFragment.ResaleGiftsList resaleGifts;
        int rowCount;
        private SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialog;
        private int selectedColor;
        private long selectedEmoji;
        private TLRPC.TL_emojiStatusCollectible selectedEmojiCollectible;
        private TLRPC.TL_peerColorCollectible selectedPeerCollectible;
        private TL_stars.TL_starGiftUnique selectedResaleGift;
        private TL_stars.StarGift selectedTabGift;
        private SetReplyIconCell setReplyIconCell;
        int shadowRow;
        private final ArrayList tabs;
        private final int type;
        final ArrayList uniqueGifts;

        /* JADX WARN: Removed duplicated region for block: B:11:0x003d  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0065  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void setupValues() {
            /*
                r6 = this;
                int r0 = r6.type
                r1 = 0
                r3 = 0
                if (r0 != 0) goto L6b
                org.telegram.ui.PeerColorActivity r0 = org.telegram.p023ui.PeerColorActivity.this
                long r4 = org.telegram.p023ui.PeerColorActivity.m15091$$Nest$fgetdialogId(r0)
                int r0 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
                if (r0 >= 0) goto L44
                org.telegram.ui.PeerColorActivity r0 = org.telegram.p023ui.PeerColorActivity.this
                org.telegram.messenger.MessagesController r0 = r0.getMessagesController()
                org.telegram.ui.PeerColorActivity r4 = org.telegram.p023ui.PeerColorActivity.this
                long r4 = org.telegram.p023ui.PeerColorActivity.m15091$$Nest$fgetdialogId(r4)
                long r4 = -r4
                java.lang.Long r4 = java.lang.Long.valueOf(r4)
                org.telegram.tgnet.TLRPC$Chat r0 = r0.getChat(r4)
                int r4 = org.telegram.messenger.ChatObject.getProfileColorId(r0)
                r6.selectedColor = r4
                long r4 = org.telegram.messenger.ChatObject.getProfileEmojiId(r0)
                r6.selectedEmoji = r4
                if (r0 == 0) goto L3d
                org.telegram.tgnet.TLRPC$EmojiStatus r0 = r0.emoji_status
                boolean r4 = r0 instanceof org.telegram.tgnet.TLRPC.TL_emojiStatusCollectible
                if (r4 == 0) goto L3d
                org.telegram.tgnet.TLRPC$TL_emojiStatusCollectible r0 = (org.telegram.tgnet.TLRPC.TL_emojiStatusCollectible) r0
                goto L3e
            L3d:
                r0 = r3
            L3e:
                r6.selectedEmojiCollectible = r0
                r6.selectedPeerCollectible = r3
                goto Lcb
            L44:
                org.telegram.ui.PeerColorActivity r0 = org.telegram.p023ui.PeerColorActivity.this
                org.telegram.messenger.UserConfig r0 = r0.getUserConfig()
                org.telegram.tgnet.TLRPC$User r0 = r0.getCurrentUser()
                int r4 = org.telegram.messenger.UserObject.getProfileColorId(r0)
                r6.selectedColor = r4
                long r4 = org.telegram.messenger.UserObject.getProfileEmojiId(r0)
                r6.selectedEmoji = r4
                if (r0 == 0) goto L65
                org.telegram.tgnet.TLRPC$EmojiStatus r0 = r0.emoji_status
                boolean r4 = r0 instanceof org.telegram.tgnet.TLRPC.TL_emojiStatusCollectible
                if (r4 == 0) goto L65
                org.telegram.tgnet.TLRPC$TL_emojiStatusCollectible r0 = (org.telegram.tgnet.TLRPC.TL_emojiStatusCollectible) r0
                goto L66
            L65:
                r0 = r3
            L66:
                r6.selectedEmojiCollectible = r0
                r6.selectedPeerCollectible = r3
                goto Lcb
            L6b:
                org.telegram.ui.PeerColorActivity r0 = org.telegram.p023ui.PeerColorActivity.this
                long r4 = org.telegram.p023ui.PeerColorActivity.m15091$$Nest$fgetdialogId(r0)
                int r0 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
                if (r0 >= 0) goto La6
                org.telegram.ui.PeerColorActivity r0 = org.telegram.p023ui.PeerColorActivity.this
                org.telegram.messenger.MessagesController r0 = r0.getMessagesController()
                org.telegram.ui.PeerColorActivity r4 = org.telegram.p023ui.PeerColorActivity.this
                long r4 = org.telegram.p023ui.PeerColorActivity.m15091$$Nest$fgetdialogId(r4)
                long r4 = -r4
                java.lang.Long r4 = java.lang.Long.valueOf(r4)
                org.telegram.tgnet.TLRPC$Chat r0 = r0.getChat(r4)
                int r4 = org.telegram.messenger.ChatObject.getColorId(r0)
                r6.selectedColor = r4
                long r4 = org.telegram.messenger.ChatObject.getEmojiId(r0)
                r6.selectedEmoji = r4
                r6.selectedEmojiCollectible = r3
                if (r0 == 0) goto La3
                org.telegram.tgnet.TLRPC$PeerColor r0 = r0.color
                boolean r4 = r0 instanceof org.telegram.tgnet.TLRPC.TL_peerColorCollectible
                if (r4 == 0) goto La3
                r3 = r0
                org.telegram.tgnet.TLRPC$TL_peerColorCollectible r3 = (org.telegram.tgnet.TLRPC.TL_peerColorCollectible) r3
            La3:
                r6.selectedPeerCollectible = r3
                goto Lcb
            La6:
                org.telegram.ui.PeerColorActivity r0 = org.telegram.p023ui.PeerColorActivity.this
                org.telegram.messenger.UserConfig r0 = r0.getUserConfig()
                org.telegram.tgnet.TLRPC$User r0 = r0.getCurrentUser()
                int r4 = org.telegram.messenger.UserObject.getColorId(r0)
                r6.selectedColor = r4
                long r4 = org.telegram.messenger.UserObject.getEmojiId(r0)
                r6.selectedEmoji = r4
                r6.selectedEmojiCollectible = r3
                if (r0 == 0) goto Lc9
                org.telegram.tgnet.TLRPC$PeerColor r0 = r0.color
                boolean r4 = r0 instanceof org.telegram.tgnet.TLRPC.TL_peerColorCollectible
                if (r4 == 0) goto Lc9
                r3 = r0
                org.telegram.tgnet.TLRPC$TL_peerColorCollectible r3 = (org.telegram.tgnet.TLRPC.TL_peerColorCollectible) r3
            Lc9:
                r6.selectedPeerCollectible = r3
            Lcb:
                org.telegram.tgnet.TLRPC$TL_emojiStatusCollectible r0 = r6.selectedEmojiCollectible
                if (r0 != 0) goto Ld5
                org.telegram.tgnet.TLRPC$TL_peerColorCollectible r0 = r6.selectedPeerCollectible
                if (r0 == 0) goto Ld4
                goto Ld5
            Ld4:
                return
            Ld5:
                r0 = -1
                r6.selectedColor = r0
                r6.selectedEmoji = r1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PeerColorActivity.Page.setupValues():void");
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x0162  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public Page(android.content.Context r18, final int r19) {
            /*
                Method dump skipped, instructions count: 551
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.PeerColorActivity.Page.<init>(org.telegram.ui.PeerColorActivity, android.content.Context, int):void");
        }

        /* renamed from: org.telegram.ui.PeerColorActivity$Page$4 */
        class C57344 extends ListAdapterMD3 {
            final /* synthetic */ Context val$context;
            final /* synthetic */ PeerColorActivity val$this$0;
            final /* synthetic */ int val$type;

            @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3
            public boolean isHeader(int i) {
                return false;
            }

            @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3
            public boolean isIgnoreLayoutParams(int i) {
                return (i == 6 || i == 3 || i == 1 || i == 2) ? false : true;
            }

            @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3
            public boolean shouldApplyBackground(int i) {
                return (i == 2 || i == 4) ? false : true;
            }

            @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3
            public boolean shouldIgnoreBackground(int i) {
                return i == 10 || i == 11 || i == 5;
            }

            C57344(PeerColorActivity peerColorActivity, Context context, int i) {
                this.val$this$0 = peerColorActivity;
                this.val$context = context;
                this.val$type = i;
            }

            @Override // com.exteragram.messenger.components.adapters.ListAdapterMD3
            public Theme.ResourcesProvider getResourcesProvider() {
                return ((BaseFragment) PeerColorActivity.this).resourceProvider;
            }

            @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
            public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
                return viewHolder.getItemViewType() == 3 || viewHolder.getItemViewType() == 6 || viewHolder.getItemViewType() == 8 || viewHolder.getItemViewType() == 12;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View view;
                View emptyView;
                View giftCell;
                switch (i) {
                    case 1:
                        Page page = Page.this;
                        PeerColorGrid peerColorGrid = new PeerColorGrid(Page.this.getContext(), this.val$type, ((BaseFragment) PeerColorActivity.this).currentAccount, ((BaseFragment) PeerColorActivity.this).resourceProvider);
                        page.peerColorPicker = peerColorGrid;
                        peerColorGrid.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                        peerColorGrid.setSelected(Page.this.selectedColor, false);
                        peerColorGrid.setOnColorClick(new Utilities.Callback() { // from class: org.telegram.ui.PeerColorActivity$Page$4$$ExternalSyntheticLambda2
                            @Override // org.telegram.messenger.Utilities.Callback
                            public final void run(Object obj) {
                                this.f$0.lambda$onCreateViewHolder$0((Integer) obj);
                            }
                        });
                        view = peerColorGrid;
                        emptyView = view;
                        break;
                    case 2:
                    default:
                        emptyView = new TextInfoPrivacyCell(Page.this.getContext(), PeerColorActivity.this.getResourceProvider());
                        break;
                    case 3:
                        Page page2 = Page.this;
                        Page page3 = Page.this;
                        SetReplyIconCell setReplyIconCell = page3.new SetReplyIconCell(page3.getContext());
                        page2.setReplyIconCell = setReplyIconCell;
                        setReplyIconCell.update(false);
                        view = setReplyIconCell;
                        emptyView = view;
                        break;
                    case 4:
                        View view2 = new View(Page.this.getContext()) { // from class: org.telegram.ui.PeerColorActivity.Page.4.2
                            @Override // android.view.View
                            protected void onMeasure(int i2, int i3) {
                                super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(16.0f), TLObject.FLAG_30));
                            }
                        };
                        view2.setBackground(Theme.getThemedDrawableByKey(Page.this.getContext(), C2369R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        emptyView = view2;
                        break;
                    case 5:
                        emptyView = new View(Page.this.getContext()) { // from class: org.telegram.ui.PeerColorActivity.Page.4.1
                            @Override // android.view.View
                            protected void onMeasure(int i2, int i3) {
                                super.onMeasure(i2, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(76.0f), TLObject.FLAG_30));
                            }
                        };
                        break;
                    case 6:
                        View textCell = new TextCell(Page.this.getContext(), PeerColorActivity.this.getResourceProvider());
                        textCell.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                        emptyView = textCell;
                        break;
                    case 7:
                        View headerCell = new HeaderCell(Page.this.getContext(), ((BaseFragment) PeerColorActivity.this).resourceProvider);
                        headerCell.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                        emptyView = headerCell;
                        break;
                    case 8:
                        giftCell = new GiftCell(Page.this.getContext(), false, ((BaseFragment) PeerColorActivity.this).resourceProvider);
                        emptyView = giftCell;
                        break;
                    case 9:
                        FlickerLoadingView flickerLoadingView = new FlickerLoadingView(this.val$context, ((BaseFragment) PeerColorActivity.this).resourceProvider);
                        flickerLoadingView.setIsSingleCell(true);
                        flickerLoadingView.setViewType(35);
                        emptyView = flickerLoadingView;
                        break;
                    case 10:
                        giftCell = new GiftSheet.Tabs(Page.this.getContext(), false, ((BaseFragment) PeerColorActivity.this).resourceProvider);
                        giftCell.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                        emptyView = giftCell;
                        break;
                    case 11:
                        Page page4 = Page.this;
                        emptyView = page4.new EmptyView(page4.getContext());
                        break;
                    case 12:
                        emptyView = new GiftSheet.GiftCell(Page.this.getContext(), ((BaseFragment) PeerColorActivity.this).currentAccount, ((BaseFragment) PeerColorActivity.this).resourceProvider);
                        break;
                }
                return new RecyclerListView.Holder(emptyView);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onCreateViewHolder$0(Integer num) {
                Page.this.selectedColor = num.intValue();
                Page.this.selectedEmojiCollectible = null;
                Page.this.selectedPeerCollectible = null;
                Page.this.selectedResaleGift = null;
                Page.this.updateProfilePreview(true);
                Page.this.updateMessages();
                Page.this.updateButton(true);
                if (Page.this.setReplyIconCell != null) {
                    Page.this.setReplyIconCell.invalidate();
                }
                Page page = PeerColorActivity.this.profilePage;
                if (page == null || page.profilePreview == null) {
                    return;
                }
                PeerColorActivity peerColorActivity = PeerColorActivity.this;
                if (peerColorActivity.namePage != null) {
                    peerColorActivity.profilePage.profilePreview.overrideAvatarColor(PeerColorActivity.this.namePage.selectedColor);
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                String string;
                boolean z = true;
                switch (getItemViewType(i)) {
                    case 1:
                        viewHolder.itemView.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                        ((PeerColorGrid) viewHolder.itemView).updateColors();
                        break;
                    case 2:
                        TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                        textInfoPrivacyCell.setFixedSize(0);
                        Page page = Page.this;
                        if (i == page.infoRow) {
                            if (this.val$type == 1) {
                                string = LocaleController.getString(PeerColorActivity.this.isChannel ? C2369R.string.ChannelColorHint : C2369R.string.UserColorHint);
                            } else {
                                string = LocaleController.getString(PeerColorActivity.this.isChannel ? C2369R.string.ChannelProfileHint : C2369R.string.UserProfileHint2);
                            }
                            final int i2 = this.val$type;
                            textInfoPrivacyCell.setText(AndroidUtilities.replaceArrows(AndroidUtilities.replaceSingleTag(string, new Runnable() { // from class: org.telegram.ui.PeerColorActivity$Page$4$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    this.f$0.lambda$onBindViewHolder$1(i2);
                                }
                            }), true));
                            textInfoPrivacyCell.setBackground(Theme.getThemedDrawableByKey(Page.this.getContext(), Page.this.clearRow >= 0 ? C2369R.drawable.greydivider : C2369R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                            break;
                        } else if (i == page.shadowRow) {
                            textInfoPrivacyCell.setText("");
                            textInfoPrivacyCell.setFixedSize(12);
                            textInfoPrivacyCell.setBackground(Theme.getThemedDrawableByKey(Page.this.getContext(), Page.this.giftsHeaderRow >= 0 ? C2369R.drawable.greydivider : C2369R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                            break;
                        } else if (i == page.giftsInfoRow) {
                            textInfoPrivacyCell.setText(LocaleController.getString(C2369R.string.UserProfileCollectibleInfo));
                            textInfoPrivacyCell.setBackground(Theme.getThemedDrawableByKey(Page.this.getContext(), C2369R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                            break;
                        }
                        break;
                    case 3:
                        ((SetReplyIconCell) viewHolder.itemView).updateColors();
                        break;
                    case 6:
                        TextCell textCell = (TextCell) viewHolder.itemView;
                        textCell.updateColors();
                        textCell.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                        textCell.updateColors();
                        Page page2 = Page.this;
                        if (i == page2.clearRow) {
                            textCell.setText(LocaleController.getString(PeerColorActivity.this.isChannel ? C2369R.string.ChannelProfileColorReset : C2369R.string.UserProfileColorReset), false);
                            break;
                        }
                        break;
                    case 7:
                        HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                        if (i == Page.this.giftsHeaderRow) {
                            headerCell.setText(LocaleController.getString(C2369R.string.UserProfileCollectibleHeader), false);
                        }
                        headerCell.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                        break;
                    case 8:
                        GiftCell giftCell = (GiftCell) viewHolder.itemView;
                        Page page3 = Page.this;
                        int i3 = i - page3.giftsStartRow;
                        if (i3 >= 0 && i3 < page3.uniqueGifts.size()) {
                            TL_stars.TL_starGiftUnique tL_starGiftUnique = (TL_stars.TL_starGiftUnique) Page.this.uniqueGifts.get(i3);
                            giftCell.set(i3, tL_starGiftUnique);
                            if ((Page.this.selectedEmojiCollectible == null || Page.this.selectedEmojiCollectible.collectible_id != tL_starGiftUnique.f1755id) && (Page.this.selectedPeerCollectible == null || Page.this.selectedPeerCollectible.collectible_id != tL_starGiftUnique.f1755id)) {
                                z = false;
                            }
                            giftCell.setSelected(z, false);
                            giftCell.card.invalidate();
                            break;
                        } else {
                            return;
                        }
                    case 10:
                        GiftSheet.Tabs tabs = (GiftSheet.Tabs) viewHolder.itemView;
                        Page.this.tabs.clear();
                        Page.this.index2gift.clear();
                        ArrayList arrayList = StarsController.getInstance(((BaseFragment) PeerColorActivity.this).currentAccount).sortedGifts;
                        Page.this.tabs.add(LocaleController.getString(C2369R.string.Gift2TabMine));
                        int size = 0;
                        for (int i4 = 0; i4 < arrayList.size(); i4++) {
                            TL_stars.StarGift starGift = (TL_stars.StarGift) arrayList.get(i4);
                            int i5 = this.val$type;
                            if ((i5 == 0 || (i5 == 1 && starGift.peer_color_available)) && starGift.availability_resale > 0) {
                                if (Page.this.selectedTabGift == starGift) {
                                    size = Page.this.tabs.size();
                                }
                                Page.this.index2gift.put(Integer.valueOf(Page.this.tabs.size()), starGift);
                                TextPaint textPaint = new TextPaint(1);
                                textPaint.setTextSize(AndroidUtilities.m1146dp(14.0f));
                                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("x ");
                                AnimatedEmojiSpan animatedEmojiSpan = new AnimatedEmojiSpan(starGift.getDocument(), textPaint.getFontMetricsInt());
                                animatedEmojiSpan.size = AndroidUtilities.m1146dp(14.0f);
                                spannableStringBuilder.setSpan(animatedEmojiSpan, 0, 1, 33);
                                spannableStringBuilder.append((CharSequence) starGift.title);
                                Page.this.tabs.add(spannableStringBuilder);
                            }
                        }
                        tabs.set(0, Page.this.tabs, size, new Utilities.Callback() { // from class: org.telegram.ui.PeerColorActivity$Page$4$$ExternalSyntheticLambda1
                            @Override // org.telegram.messenger.Utilities.Callback
                            public final void run(Object obj) {
                                this.f$0.lambda$onBindViewHolder$3((Integer) obj);
                            }
                        });
                        tabs.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                        tabs.updateColors();
                        break;
                    case 11:
                        ((EmptyView) viewHolder.itemView).updateColors();
                        break;
                    case 12:
                        GiftSheet.GiftCell giftCell2 = (GiftSheet.GiftCell) viewHolder.itemView;
                        Page page4 = Page.this;
                        int i6 = i - page4.giftsStartRow;
                        if (page4.resaleGifts != null && i6 >= 0 && i6 < Page.this.uniqueGifts.size()) {
                            TL_stars.TL_starGiftUnique tL_starGiftUnique2 = (TL_stars.TL_starGiftUnique) Page.this.uniqueGifts.get(i6);
                            giftCell2.setStarsGift(tL_starGiftUnique2, false, false, false, true);
                            if ((Page.this.selectedEmojiCollectible == null || Page.this.selectedEmojiCollectible.collectible_id != tL_starGiftUnique2.f1755id) && (Page.this.selectedPeerCollectible == null || Page.this.selectedPeerCollectible.collectible_id != tL_starGiftUnique2.f1755id)) {
                                z = false;
                            }
                            giftCell2.setSelected(z, false);
                            break;
                        } else {
                            return;
                        }
                        break;
                }
                updateRow(viewHolder, i);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onBindViewHolder$1(int i) {
                PeerColorActivity.this.viewPager.scrollToPosition(1 - i);
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onBindViewHolder$3(Integer num) {
                Page.this.selectedTabGift = num.intValue() == 0 ? null : (TL_stars.StarGift) Page.this.index2gift.get(num);
                if (Page.this.selectedTabGift == null) {
                    if (Page.this.resaleGifts != null) {
                        Page.this.resaleGifts.cancel();
                        Page.this.resaleGifts = null;
                    }
                } else if (Page.this.resaleGifts == null || Page.this.resaleGifts.gift_id != Page.this.selectedTabGift.f1755id) {
                    Page page = Page.this;
                    page.resaleGifts = new ResaleGiftsFragment.ResaleGiftsList(((BaseFragment) PeerColorActivity.this).currentAccount, Page.this.selectedTabGift.f1755id, new Utilities.Callback() { // from class: org.telegram.ui.PeerColorActivity$Page$4$$ExternalSyntheticLambda3
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.lambda$onBindViewHolder$2((Boolean) obj);
                        }
                    });
                    Page.this.resaleGifts.load();
                }
                Page.this.update();
                (PeerColorActivity.this.viewPager.getCurrentPosition() == 1 ? PeerColorActivity.this.profilePage : PeerColorActivity.this.namePage).update();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onBindViewHolder$2(Boolean bool) {
                Page.this.update();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
                super.onViewAttachedToWindow(viewHolder);
                boolean z = true;
                if (viewHolder.getItemViewType() == 8) {
                    GiftCell giftCell = (GiftCell) viewHolder.itemView;
                    int adapterPosition = viewHolder.getAdapterPosition();
                    Page page = Page.this;
                    int i = adapterPosition - page.giftsStartRow;
                    if (i < 0 || i >= page.uniqueGifts.size()) {
                        return;
                    }
                    TL_stars.TL_starGiftUnique tL_starGiftUnique = (TL_stars.TL_starGiftUnique) Page.this.uniqueGifts.get(i);
                    giftCell.set(i, tL_starGiftUnique);
                    if ((Page.this.selectedEmojiCollectible == null || Page.this.selectedEmojiCollectible.collectible_id != tL_starGiftUnique.f1755id) && (Page.this.selectedPeerCollectible == null || Page.this.selectedPeerCollectible.collectible_id != tL_starGiftUnique.f1755id)) {
                        z = false;
                    }
                    giftCell.setSelected(z, false);
                    return;
                }
                if (viewHolder.getItemViewType() == 12) {
                    GiftSheet.GiftCell giftCell2 = (GiftSheet.GiftCell) viewHolder.itemView;
                    int adapterPosition2 = viewHolder.getAdapterPosition();
                    Page page2 = Page.this;
                    int i2 = adapterPosition2 - page2.giftsStartRow;
                    if (page2.resaleGifts != null && i2 >= 0 && i2 < Page.this.uniqueGifts.size()) {
                        TL_stars.TL_starGiftUnique tL_starGiftUnique2 = (TL_stars.TL_starGiftUnique) Page.this.uniqueGifts.get(i2);
                        giftCell2.setStarsGift(tL_starGiftUnique2, false, false, false, true);
                        if ((Page.this.selectedEmojiCollectible == null || Page.this.selectedEmojiCollectible.collectible_id != tL_starGiftUnique2.f1755id) && (Page.this.selectedPeerCollectible == null || Page.this.selectedPeerCollectible.collectible_id != tL_starGiftUnique2.f1755id)) {
                            z = false;
                        }
                        giftCell2.setSelected(z, false);
                    }
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return Page.this.rowCount;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemViewType(int i) {
                Page page = Page.this;
                if (i != page.infoRow && i != page.giftsInfoRow && i != page.info2Row && i != page.shadowRow) {
                    if (i == page.colorPickerRow) {
                        return 1;
                    }
                    if (i == page.iconRow) {
                        return 3;
                    }
                    if (i == page.buttonRow) {
                        return 5;
                    }
                    if (i == page.clearRow) {
                        return 6;
                    }
                    if (i == page.giftsTabsRow) {
                        return 10;
                    }
                    if (i == page.giftsEmptyRow) {
                        return 11;
                    }
                    if (i == page.giftsHeaderRow) {
                        return 7;
                    }
                    if (i >= page.giftsStartRow && i < page.giftsEndRow) {
                        return page.selectedTabGift == null ? 8 : 12;
                    }
                    if (i >= page.giftsLoadingStartRow && i < page.giftsLoadingEndRow) {
                        return 9;
                    }
                    if (i == getItemCount() - 1) {
                        return 4;
                    }
                }
                return 2;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(int i, View view, int i2) {
            ProfilePreview profilePreview;
            Page page;
            if (view instanceof SetReplyIconCell) {
                showSelectStatusDialog((SetReplyIconCell) view);
                return;
            }
            if (i2 == this.clearRow) {
                this.selectedColor = -1;
                this.selectedEmoji = 0L;
                this.selectedEmojiCollectible = null;
                this.selectedPeerCollectible = null;
                this.selectedResaleGift = null;
                updateMessages();
                if (i == 0) {
                    PeerColorActivity.this.namePage.updateMessages();
                }
                SetReplyIconCell setReplyIconCell = this.setReplyIconCell;
                if (setReplyIconCell != null) {
                    setReplyIconCell.update(true);
                }
                updateProfilePreview(true);
                updateButton(true);
                PeerColorActivity peerColorActivity = PeerColorActivity.this;
                Page page2 = peerColorActivity.profilePage;
                if (page2 == null || (profilePreview = page2.profilePreview) == null || (page = peerColorActivity.namePage) == null) {
                    return;
                }
                profilePreview.overrideAvatarColor(page.selectedColor);
                return;
            }
            int i3 = this.giftsStartRow;
            if (i2 < i3 || i2 >= this.giftsEndRow) {
                return;
            }
            int i4 = i2 - i3;
            if (this.selectedTabGift == null) {
                if (i4 < 0 || i4 >= this.uniqueGifts.size()) {
                    return;
                }
                TL_stars.TL_starGiftUnique tL_starGiftUnique = (TL_stars.TL_starGiftUnique) this.uniqueGifts.get(i4);
                if (i == 1) {
                    TLRPC.PeerColor peerColor = tL_starGiftUnique.peer_color;
                    if (!(peerColor instanceof TLRPC.TL_peerColorCollectible)) {
                        return;
                    }
                    this.selectedEmoji = 0L;
                    this.selectedColor = -1;
                    this.selectedResaleGift = null;
                    this.selectedEmojiCollectible = null;
                    this.selectedPeerCollectible = (TLRPC.TL_peerColorCollectible) peerColor;
                } else {
                    this.selectedEmoji = 0L;
                    this.selectedColor = -1;
                    this.selectedResaleGift = null;
                    this.selectedEmojiCollectible = MessagesController.emojiStatusCollectibleFromGift(tL_starGiftUnique);
                    this.selectedPeerCollectible = null;
                }
                updateProfilePreview(true);
                updateMessages();
                updateButton(true);
                SetReplyIconCell setReplyIconCell2 = this.setReplyIconCell;
                if (setReplyIconCell2 != null) {
                    setReplyIconCell2.update(true);
                    return;
                }
                return;
            }
            if (this.resaleGifts == null || i4 < 0 || i4 >= this.uniqueGifts.size()) {
                return;
            }
            TL_stars.TL_starGiftUnique tL_starGiftUnique2 = (TL_stars.TL_starGiftUnique) this.uniqueGifts.get(i4);
            if (i == 1) {
                TLRPC.PeerColor peerColor2 = tL_starGiftUnique2.peer_color;
                if (!(peerColor2 instanceof TLRPC.TL_peerColorCollectible)) {
                    return;
                }
                this.selectedEmoji = 0L;
                this.selectedColor = -1;
                this.selectedEmojiCollectible = null;
                this.selectedPeerCollectible = (TLRPC.TL_peerColorCollectible) peerColor2;
            } else {
                this.selectedEmoji = 0L;
                this.selectedColor = -1;
                this.selectedEmojiCollectible = MessagesController.emojiStatusCollectibleFromGift(tL_starGiftUnique2);
                this.selectedPeerCollectible = null;
            }
            this.selectedResaleGift = tL_starGiftUnique2;
            updateProfilePreview(true);
            updateMessages();
            updateButton(true);
            SetReplyIconCell setReplyIconCell3 = this.setReplyIconCell;
            if (setReplyIconCell3 != null) {
                setReplyIconCell3.update(true);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$1(View view) {
            PeerColorActivity.this.buttonClick();
        }

        public class EmptyView extends LinearLayout {
            private final BackupImageView imageView;
            private final TextView subtitle;
            private final TextView title;

            public EmptyView(Context context) {
                super(context);
                setOrientation(1);
                setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                BackupImageView backupImageView = new BackupImageView(getContext());
                this.imageView = backupImageView;
                backupImageView.setImageDrawable(new RLottieDrawable(C2369R.raw.utyan_draw, "utyan_draw", AndroidUtilities.m1146dp(120.0f), AndroidUtilities.m1146dp(120.0f)));
                addView(backupImageView, LayoutHelper.createLinear(Opcodes.ISHL, Opcodes.ISHL, 1, 0, 6, 0, 0));
                LinkSpanDrawable.LinksTextView linksTextViewMakeLinkTextView = TextHelper.makeLinkTextView(getContext(), 14.0f, Theme.key_windowBackgroundWhiteGrayText, false, ((BaseFragment) PeerColorActivity.this).resourceProvider);
                this.title = linksTextViewMakeLinkTextView;
                linksTextViewMakeLinkTextView.setGravity(17);
                linksTextViewMakeLinkTextView.setText(LocaleController.getString(Page.this.type == 0 ? C2369R.string.Gift2PeerColorProfileEmptyTitle : C2369R.string.Gift2PeerColorReplyEmptyTitle));
                addView(linksTextViewMakeLinkTextView, LayoutHelper.createLinear(-1, -2, 1, 64, 8, 64, 8));
                LinkSpanDrawable.LinksTextView linksTextViewMakeLinkTextView2 = TextHelper.makeLinkTextView(getContext(), 14.0f, Theme.key_chat_messageLinkIn, false, ((BaseFragment) PeerColorActivity.this).resourceProvider);
                this.subtitle = linksTextViewMakeLinkTextView2;
                linksTextViewMakeLinkTextView2.setGravity(17);
                linksTextViewMakeLinkTextView2.setText(AndroidUtilities.replaceArrows(AndroidUtilities.replaceSingleTag(LocaleController.getString(C2369R.string.Gift2PeerColorEmptyButton), new Runnable() { // from class: org.telegram.ui.PeerColorActivity$Page$EmptyView$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$new$1();
                    }
                }), true, AndroidUtilities.m1146dp(2.6666667f), AndroidUtilities.m1146dp(1.33f), 1.0f));
                addView(linksTextViewMakeLinkTextView2, LayoutHelper.createLinear(-1, -2, 1, 32, 4, 32, 24));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$new$1() {
                GiftSheet.Tabs tabs = null;
                for (int i = 0; i < Page.this.listView.getChildCount(); i++) {
                    View childAt = Page.this.listView.getChildAt(i);
                    if (childAt instanceof GiftSheet.Tabs) {
                        tabs = (GiftSheet.Tabs) childAt;
                    }
                }
                if (tabs == null || Page.this.tabs.size() <= 1) {
                    return;
                }
                tabs.setSelected(1, true);
                Page page = Page.this;
                page.selectedTabGift = (TL_stars.StarGift) page.index2gift.get(1);
                if (Page.this.selectedTabGift == null) {
                    if (Page.this.resaleGifts != null) {
                        Page.this.resaleGifts.cancel();
                        Page.this.resaleGifts = null;
                    }
                } else if (Page.this.resaleGifts == null || Page.this.resaleGifts.gift_id != Page.this.selectedTabGift.f1755id) {
                    Page page2 = Page.this;
                    page2.resaleGifts = new ResaleGiftsFragment.ResaleGiftsList(((BaseFragment) PeerColorActivity.this).currentAccount, Page.this.selectedTabGift.f1755id, new Utilities.Callback() { // from class: org.telegram.ui.PeerColorActivity$Page$EmptyView$$ExternalSyntheticLambda1
                        @Override // org.telegram.messenger.Utilities.Callback
                        public final void run(Object obj) {
                            this.f$0.lambda$new$0((Boolean) obj);
                        }
                    });
                    Page.this.resaleGifts.load();
                }
                Page.this.update();
                (PeerColorActivity.this.viewPager.getCurrentPosition() == 1 ? PeerColorActivity.this.profilePage : PeerColorActivity.this.namePage).update();
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$new$0(Boolean bool) {
                Page.this.update();
            }

            public void updateColors() {
                setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                this.title.setTextColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhiteGrayText));
                TextView textView = this.subtitle;
                PeerColorActivity peerColorActivity = PeerColorActivity.this;
                int i = Theme.key_chat_messageLinkIn;
                textView.setTextColor(peerColorActivity.getThemedColor(i));
                this.subtitle.setLinkTextColor(PeerColorActivity.this.getThemedColor(i));
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            if (PeerColorActivity.this.getParentLayout() != null) {
                PeerColorActivity.this.getParentLayout().drawHeaderShadow(canvas, this.actionBarHeight);
            }
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            if (this.type == 1) {
                super.onMeasure(i, i2);
                this.actionBarHeight = this.messagesCellPreview.getMeasuredHeight() + ActionBar.getCurrentActionBarHeight() + AndroidUtilities.statusBarHeight;
                ((ViewGroup.MarginLayoutParams) this.messagesCellPreview.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight() + AndroidUtilities.statusBarHeight;
                ((ViewGroup.MarginLayoutParams) this.listView.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight() + AndroidUtilities.statusBarHeight;
                this.listView.setPadding(0, this.messagesCellPreview.getMeasuredHeight(), 0, 0);
            } else {
                this.actionBarHeight = AndroidUtilities.m1146dp(230.0f) + AndroidUtilities.statusBarHeight;
                ((ViewGroup.MarginLayoutParams) this.listView.getLayoutParams()).topMargin = this.actionBarHeight;
                ((ViewGroup.MarginLayoutParams) this.profilePreview.getLayoutParams()).height = this.actionBarHeight;
            }
            super.onMeasure(i, i2);
        }

        public boolean hasUnsavedChanged() {
            if (PeerColorActivity.this.isChannel) {
                TLRPC.Chat chat = PeerColorActivity.this.getMessagesController().getChat(Long.valueOf(-PeerColorActivity.this.dialogId));
                if (chat == null) {
                    return false;
                }
                if (this.type == 1) {
                    if (this.selectedColor == ChatObject.getColorId(chat) && this.selectedEmoji == ChatObject.getEmojiId(chat)) {
                        TLRPC.PeerColor peerColor = chat.color;
                        if (PeerColorActivity.m1313eq(peerColor instanceof TLRPC.TL_peerColorCollectible ? (TLRPC.TL_peerColorCollectible) peerColor : null, this.selectedPeerCollectible)) {
                            return false;
                        }
                    }
                    return true;
                }
                if (this.selectedColor == (chat.emoji_status instanceof TLRPC.TL_emojiStatusCollectible ? -1 : ChatObject.getProfileColorId(chat))) {
                    if (this.selectedEmoji == (chat.emoji_status instanceof TLRPC.TL_emojiStatusCollectible ? 0L : ChatObject.getOnlyProfileEmojiId(chat)) && PeerColorActivity.m1312eq(chat.emoji_status, this.selectedEmojiCollectible)) {
                        return false;
                    }
                }
                return true;
            }
            TLRPC.User currentUser = PeerColorActivity.this.getUserConfig().getCurrentUser();
            if (currentUser == null) {
                return false;
            }
            if (this.type == 1) {
                if (this.selectedColor == (currentUser.color instanceof TLRPC.TL_peerColorCollectible ? -1 : UserObject.getColorId(currentUser)) && this.selectedEmoji == UserObject.getEmojiId(currentUser)) {
                    TLRPC.PeerColor peerColor2 = currentUser.color;
                    if (PeerColorActivity.m1313eq(peerColor2 instanceof TLRPC.TL_peerColorCollectible ? (TLRPC.TL_peerColorCollectible) peerColor2 : null, this.selectedPeerCollectible)) {
                        return false;
                    }
                }
                return true;
            }
            if (this.selectedColor == (currentUser.emoji_status instanceof TLRPC.TL_emojiStatusCollectible ? -1 : UserObject.getProfileColorId(currentUser))) {
                if (this.selectedEmoji == (currentUser.emoji_status instanceof TLRPC.TL_emojiStatusCollectible ? 0L : UserObject.getOnlyProfileEmojiId(currentUser)) && PeerColorActivity.m1312eq(currentUser.emoji_status, this.selectedEmojiCollectible)) {
                    return false;
                }
            }
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateButtonY() {
            if (this.buttonContainer == null) {
                return;
            }
            int itemCount = this.listAdapter.getItemCount() - 1;
            boolean z = false;
            int measuredHeight = 0;
            for (int i = 0; i < this.listView.getChildCount(); i++) {
                View childAt = this.listView.getChildAt(i);
                int childAdapterPosition = this.listView.getChildAdapterPosition(childAt);
                if (childAdapterPosition != -1 && childAdapterPosition <= itemCount) {
                    measuredHeight = Math.max(measuredHeight, childAt.getTop());
                    if (childAdapterPosition == itemCount) {
                        z = true;
                    }
                }
            }
            if (!z) {
                measuredHeight = this.listView.getMeasuredHeight();
            }
            float fMax = Math.max(0, measuredHeight - (this.listView.getMeasuredHeight() - AndroidUtilities.m1146dp(76.66f)));
            int i2 = this.type;
            if (i2 == 0 || i2 == 1) {
                this.buttonShadow.animate().alpha(fMax > 0.0f ? 0.0f : 1.0f).start();
                fMax = 0.0f;
            }
            this.buttonContainer.setTranslationY(fMax);
        }

        private class SetReplyIconCell extends FrameLayout {
            private AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable imageDrawable;
            private Text offText;
            private TextView textView;

            public SetReplyIconCell(Context context) {
                super(context);
                setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                TextView textView = new TextView(context);
                this.textView = textView;
                textView.setTextSize(1, 16.0f);
                this.textView.setTextColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
                if (Page.this.type == 1) {
                    this.textView.setText(LocaleController.getString(PeerColorActivity.this.isChannel ? C2369R.string.ChannelReplyIcon : C2369R.string.UserReplyIcon));
                } else {
                    this.textView.setText(LocaleController.getString(PeerColorActivity.this.isChannel ? C2369R.string.ChannelProfileIcon : C2369R.string.UserProfileIcon));
                }
                addView(this.textView, LayoutHelper.createFrame(-1, -2.0f, 23, 20.0f, 0.0f, 20.0f, 0.0f));
                this.imageDrawable = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(this, false, AndroidUtilities.m1146dp(24.0f), 13);
            }

            public void updateColors() {
                setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                this.textView.setTextColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhiteBlackText));
            }

            public void update(boolean z) {
                if (Page.this.selectedEmoji != 0) {
                    this.imageDrawable.set(Page.this.selectedEmoji, z);
                    this.offText = null;
                } else {
                    this.imageDrawable.set((Drawable) null, z);
                    if (this.offText == null) {
                        this.offText = new Text(LocaleController.getString(PeerColorActivity.this.isChannel ? C2369R.string.ChannelReplyIconOff : C2369R.string.UserReplyIconOff), 16.0f);
                    }
                }
            }

            public void updateImageBounds() {
                this.imageDrawable.setBounds(LocaleController.isRTL ? AndroidUtilities.m1146dp(21.0f) : (getWidth() - this.imageDrawable.getIntrinsicWidth()) - AndroidUtilities.m1146dp(21.0f), (getHeight() - this.imageDrawable.getIntrinsicHeight()) / 2, LocaleController.isRTL ? AndroidUtilities.m1146dp(21.0f) + this.imageDrawable.getIntrinsicWidth() : getWidth() - AndroidUtilities.m1146dp(21.0f), (getHeight() + this.imageDrawable.getIntrinsicHeight()) / 2);
            }

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                updateImageBounds();
                this.imageDrawable.setColor(Integer.valueOf(getColor()));
                Text text = this.offText;
                if (text != null) {
                    text.draw(canvas, (getMeasuredWidth() - this.offText.getWidth()) - AndroidUtilities.m1146dp(19.0f), getMeasuredHeight() / 2.0f, PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhiteBlueText4), 1.0f);
                } else {
                    this.imageDrawable.draw(canvas);
                }
            }

            public int getColor() {
                MessagesController.PeerColor color;
                if (Page.this.selectedColor < 0) {
                    PeerColorActivity peerColorActivity = PeerColorActivity.this;
                    int i = Theme.key_actionBarDefault;
                    if (AndroidUtilities.computePerceivedBrightness(peerColorActivity.getThemedColor(i)) > 0.8f) {
                        return Theme.getColor(Theme.key_windowBackgroundWhiteBlueText, ((BaseFragment) PeerColorActivity.this).resourceProvider);
                    }
                    return AndroidUtilities.computePerceivedBrightness(PeerColorActivity.this.getThemedColor(i)) < 0.2f ? Theme.multAlpha(Theme.getColor(Theme.key_actionBarDefaultTitle, ((BaseFragment) PeerColorActivity.this).resourceProvider), 0.5f) : Theme.blendOver(Theme.getColor(Theme.key_windowBackgroundWhite, ((BaseFragment) PeerColorActivity.this).resourceProvider), Theme.multAlpha(PeerColorActivity.adaptProfileEmojiColor(Theme.getColor(i, ((BaseFragment) PeerColorActivity.this).resourceProvider)), 0.7f));
                }
                if (Page.this.selectedColor < 7) {
                    Page page = Page.this;
                    return PeerColorActivity.this.getThemedColor(Theme.keys_avatar_nameInMessage[page.selectedColor]);
                }
                MessagesController.PeerColors peerColors = Page.this.type == 1 ? MessagesController.getInstance(((BaseFragment) PeerColorActivity.this).currentAccount).peerColors : MessagesController.getInstance(((BaseFragment) PeerColorActivity.this).currentAccount).profilePeerColors;
                if (peerColors != null && (color = peerColors.getColor(Page.this.selectedColor)) != null) {
                    return color.getColor1();
                }
                return PeerColorActivity.this.getThemedColor(Theme.keys_avatar_nameInMessage[0]);
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

        public void showSelectStatusDialog(final SetReplyIconCell setReplyIconCell) {
            int iCenterX;
            int iM1146dp;
            if (this.selectAnimatedEmojiDialog != null || setReplyIconCell == null) {
                return;
            }
            final SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[] selectAnimatedEmojiDialogWindowArr = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow[1];
            int iMin = (int) Math.min(AndroidUtilities.m1146dp(330.0f), AndroidUtilities.displaySize.y * 0.75f);
            int iMin2 = (int) Math.min(AndroidUtilities.m1146dp(324.0f), AndroidUtilities.displaySize.x * 0.95f);
            AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = setReplyIconCell.imageDrawable;
            if (setReplyIconCell.imageDrawable != null) {
                setReplyIconCell.imageDrawable.play();
                setReplyIconCell.updateImageBounds();
                Rect rect = AndroidUtilities.rectTmp2;
                rect.set(setReplyIconCell.imageDrawable.getBounds());
                if (this.type == 1) {
                    iM1146dp = ((-rect.centerY()) + AndroidUtilities.m1146dp(12.0f)) - iMin;
                } else {
                    iM1146dp = (-(setReplyIconCell.getHeight() - rect.centerY())) - AndroidUtilities.m1146dp(16.0f);
                }
                iCenterX = rect.centerX() - (setReplyIconCell.getRight() - iMin2);
            } else {
                iCenterX = 0;
                iM1146dp = 0;
            }
            int i = iM1146dp;
            SelectAnimatedEmojiDialog selectAnimatedEmojiDialog = new SelectAnimatedEmojiDialog(PeerColorActivity.this, getContext(), true, Integer.valueOf(iCenterX), this.type == 1 ? 5 : 7, true, PeerColorActivity.this.getResourceProvider(), this.type == 1 ? 24 : 16, setReplyIconCell.getColor()) { // from class: org.telegram.ui.PeerColorActivity.Page.7
                @Override // org.telegram.p023ui.SelectAnimatedEmojiDialog
                protected float getScrimDrawableTranslationY() {
                    return 0.0f;
                }

                @Override // org.telegram.p023ui.SelectAnimatedEmojiDialog
                protected void onEmojiSelected(View view, Long l, TLRPC.Document document, TL_stars.TL_starGiftUnique tL_starGiftUnique, Integer num) {
                    if (tL_starGiftUnique != null) {
                        if (Page.this.type == 0) {
                            TLRPC.PeerColor peerColor = tL_starGiftUnique.peer_color;
                            if (!(peerColor instanceof TLRPC.TL_peerColorCollectible)) {
                                return;
                            }
                            Page.this.selectedPeerCollectible = (TLRPC.TL_peerColorCollectible) peerColor;
                            Page.this.selectedEmojiCollectible = null;
                        } else {
                            Page.this.selectedPeerCollectible = null;
                            Page.this.selectedEmojiCollectible = MessagesController.emojiStatusCollectibleFromGift(tL_starGiftUnique);
                        }
                        Page.this.selectedResaleGift = null;
                        Page.this.selectedColor = -1;
                    } else {
                        Page.this.selectedEmoji = l == null ? 0L : l.longValue();
                        Page.this.selectedEmojiCollectible = null;
                        Page.this.selectedPeerCollectible = null;
                        Page.this.selectedResaleGift = null;
                    }
                    SetReplyIconCell setReplyIconCell2 = setReplyIconCell;
                    if (setReplyIconCell2 != null) {
                        setReplyIconCell2.update(true);
                    }
                    Page.this.updateProfilePreview(true);
                    Page.this.updateMessages();
                    Page.this.updateButton(true);
                    if (selectAnimatedEmojiDialogWindowArr[0] != null) {
                        Page.this.selectAnimatedEmojiDialog = null;
                        selectAnimatedEmojiDialogWindowArr[0].dismiss();
                    }
                }
            };
            selectAnimatedEmojiDialog.useAccentForPlus = true;
            long j = this.selectedEmoji;
            selectAnimatedEmojiDialog.setSelected(j == 0 ? null : Long.valueOf(j));
            selectAnimatedEmojiDialog.setSaveState(3);
            selectAnimatedEmojiDialog.setScrimDrawable(swapAnimatedEmojiDrawable, setReplyIconCell);
            int i2 = -2;
            SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow selectAnimatedEmojiDialogWindow = new SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow(selectAnimatedEmojiDialog, i2, i2) { // from class: org.telegram.ui.PeerColorActivity.Page.8
                @Override // org.telegram.ui.SelectAnimatedEmojiDialog.SelectAnimatedEmojiDialogWindow, android.widget.PopupWindow
                public void dismiss() {
                    super.dismiss();
                    Page.this.selectAnimatedEmojiDialog = null;
                }
            };
            this.selectAnimatedEmojiDialog = selectAnimatedEmojiDialogWindow;
            selectAnimatedEmojiDialogWindowArr[0] = selectAnimatedEmojiDialogWindow;
            selectAnimatedEmojiDialogWindow.showAsDropDown(setReplyIconCell, 0, i, (LocaleController.isRTL ? 3 : 5) | 48);
            selectAnimatedEmojiDialogWindowArr[0].dimBehind();
        }

        public void checkResetColorButton() {
            int i;
            if (this.type != 0) {
                return;
            }
            int i2 = this.clearRow;
            updateRows();
            if (i2 >= 0 && this.clearRow < 0) {
                this.listAdapter.notifyItemRangeRemoved(i2, 2);
            } else {
                if (i2 >= 0 || (i = this.clearRow) < 0) {
                    return;
                }
                this.listAdapter.notifyItemRangeInserted(i, 2);
            }
        }

        public void updateSelectedGift() {
            TLRPC.TL_peerColorCollectible tL_peerColorCollectible;
            TLRPC.TL_peerColorCollectible tL_peerColorCollectible2;
            for (int i = 0; i < this.listView.getChildCount(); i++) {
                View childAt = this.listView.getChildAt(i);
                if (childAt instanceof GiftCell) {
                    GiftCell giftCell = (GiftCell) childAt;
                    TLRPC.TL_emojiStatusCollectible tL_emojiStatusCollectible = this.selectedEmojiCollectible;
                    giftCell.setSelected((tL_emojiStatusCollectible != null && tL_emojiStatusCollectible.collectible_id == giftCell.getGiftId()) || ((tL_peerColorCollectible2 = this.selectedPeerCollectible) != null && tL_peerColorCollectible2.collectible_id == giftCell.getGiftId()), true);
                } else if (childAt instanceof GiftSheet.GiftCell) {
                    GiftSheet.GiftCell giftCell2 = (GiftSheet.GiftCell) childAt;
                    TLRPC.TL_emojiStatusCollectible tL_emojiStatusCollectible2 = this.selectedEmojiCollectible;
                    giftCell2.setSelected((tL_emojiStatusCollectible2 != null && tL_emojiStatusCollectible2.collectible_id == giftCell2.getGiftId()) || ((tL_peerColorCollectible = this.selectedPeerCollectible) != null && tL_peerColorCollectible.collectible_id == giftCell2.getGiftId()), true);
                }
            }
        }

        private void updateRows() {
            this.clearRow = -1;
            this.shadowRow = -1;
            this.giftsHeaderRow = -1;
            this.giftsStartRow = -1;
            this.giftsLoadingStartRow = -1;
            this.giftsLoadingEndRow = -1;
            this.giftsEndRow = -1;
            this.giftsInfoRow = -1;
            this.giftsTabsRow = -1;
            this.giftsEmptyRow = -1;
            int i = 0;
            this.giftsCount = 0;
            this.uniqueGifts.clear();
            this.colorPickerRow = 0;
            int i2 = 1 + 1;
            this.iconRow = 1;
            int i3 = i2 + 1;
            this.rowCount = i3;
            this.infoRow = i2;
            int i4 = this.type;
            int i5 = 3;
            if (i4 == 0 && (this.selectedColor >= 0 || this.selectedEmojiCollectible != null || this.selectedPeerCollectible != null)) {
                this.clearRow = i3;
                this.rowCount = i2 + 3;
                this.shadowRow = i2 + 2;
            }
            PeerColorActivity peerColorActivity = PeerColorActivity.this;
            StarsController.GiftsList giftsList = i4 == 1 ? peerColorActivity.giftsWithPeerColor : peerColorActivity.gifts;
            int i6 = this.type;
            if ((i6 == 0 || i6 == 1) && giftsList != null) {
                int i7 = this.rowCount;
                this.rowCount = i7 + 1;
                this.giftsTabsRow = i7;
                TL_stars.StarGift starGift = this.selectedTabGift;
                if (starGift == null) {
                    while (i < giftsList.gifts.size()) {
                        TL_stars.StarGift starGift2 = ((TL_stars.SavedStarGift) giftsList.gifts.get(i)).gift;
                        if (starGift2 instanceof TL_stars.TL_starGiftUnique) {
                            this.uniqueGifts.add((TL_stars.TL_starGiftUnique) starGift2);
                        }
                        i++;
                    }
                    int i8 = this.rowCount;
                    this.giftsStartRow = i8;
                    this.rowCount = i8 + this.uniqueGifts.size();
                    this.giftsCount += this.uniqueGifts.size();
                    this.giftsEndRow = this.rowCount;
                    if (PeerColorActivity.this.gifts.loading || !PeerColorActivity.this.gifts.endReached) {
                        int i9 = this.rowCount;
                        this.giftsLoadingStartRow = i9;
                        int i10 = this.giftsCount;
                        int i11 = 3 - (i10 % 3);
                        if (i10 <= 0) {
                            i5 = 9;
                        } else if (i11 > 0) {
                            i5 = i11;
                        }
                        int i12 = i9 + i5;
                        this.rowCount = i12;
                        this.giftsCount = i10 + i5;
                        this.giftsLoadingEndRow = i12;
                    } else if (this.uniqueGifts.isEmpty()) {
                        int i13 = this.rowCount;
                        this.rowCount = i13 + 1;
                        this.giftsEmptyRow = i13;
                    }
                    if (seesLoading()) {
                        giftsList.load();
                    }
                } else if (starGift != null && this.resaleGifts != null) {
                    long clientUserId = UserConfig.getInstance(((BaseFragment) PeerColorActivity.this).currentAccount).getClientUserId();
                    while (i < this.resaleGifts.gifts.size()) {
                        TL_stars.TL_starGiftUnique tL_starGiftUnique = (TL_stars.TL_starGiftUnique) this.resaleGifts.gifts.get(i);
                        if (DialogObject.getPeerDialogId(tL_starGiftUnique.owner_id) != clientUserId && DialogObject.getPeerDialogId(tL_starGiftUnique.host_id) != clientUserId) {
                            this.uniqueGifts.add(tL_starGiftUnique);
                        }
                        i++;
                    }
                    int i14 = this.rowCount;
                    this.giftsStartRow = i14;
                    this.rowCount = i14 + this.uniqueGifts.size();
                    int size = this.giftsCount + this.uniqueGifts.size();
                    this.giftsCount = size;
                    int i15 = this.rowCount;
                    this.giftsEndRow = i15;
                    ResaleGiftsFragment.ResaleGiftsList resaleGiftsList = this.resaleGifts;
                    if (resaleGiftsList.loading || !resaleGiftsList.endReached) {
                        this.giftsLoadingStartRow = i15;
                        int i16 = 3 - (size % 3);
                        if (size <= 0) {
                            i5 = 9;
                        } else if (i16 > 0) {
                            i5 = i16;
                        }
                        int i17 = i15 + i5;
                        this.rowCount = i17;
                        this.giftsCount = size + i5;
                        this.giftsLoadingEndRow = i17;
                    }
                    if (resaleGiftsList != null && seesLoading()) {
                        this.resaleGifts.load();
                    }
                }
                int i18 = this.rowCount;
                this.rowCount = i18 + 1;
                this.giftsInfoRow = i18;
            }
            int i19 = this.rowCount;
            this.rowCount = i19 + 1;
            this.buttonRow = i19;
        }

        public boolean seesLoading() {
            if (this.listView == null) {
                return false;
            }
            for (int i = 0; i < this.listView.getChildCount(); i++) {
                if (this.listView.getChildAt(i) instanceof FlickerLoadingView) {
                    return true;
                }
            }
            return false;
        }

        public void updateButton(boolean z) {
            ButtonWithCounterView buttonWithCounterView = this.button;
            if (buttonWithCounterView == null) {
                return;
            }
            TL_stars.TL_starGiftUnique tL_starGiftUnique = this.selectedResaleGift;
            if (tL_starGiftUnique != null) {
                AmountUtils$Amount resellAmount = tL_starGiftUnique.getResellAmount(AmountUtils$Currency.STARS);
                if (tL_starGiftUnique.resale_ton_only) {
                    this.button.setText(StarsIntroActivity.replaceStars(true, (CharSequence) LocaleController.formatString(C2369R.string.ResellGiftBuyTON, tL_starGiftUnique.getResellAmount(AmountUtils$Currency.TON).asFormatString())), z);
                    this.button.setSubText(StarsIntroActivity.replaceStars(LocaleController.formatPluralStringComma("ResellGiftBuyEq", (int) resellAmount.asDecimal())), z);
                    return;
                } else {
                    this.button.setText(StarsIntroActivity.replaceStars(LocaleController.formatPluralStringComma("ResellGiftBuy", (int) resellAmount.asDecimal())), z);
                    this.button.setSubText(null, z);
                    return;
                }
            }
            buttonWithCounterView.setText((PeerColorActivity.this.getUserConfig().isPremium() || PeerColorActivity.this.isChannel) ? this.selectedEmojiCollectible != null ? this.buttonCollectible : this.buttonUnlocked : this.buttonLocked, z);
            this.button.setSubText(null, z);
        }

        public void updateProfilePreview(boolean z) {
            PeerColorGrid peerColorGrid = this.peerColorPicker;
            if (peerColorGrid != null) {
                peerColorGrid.setSelected(this.selectedColor, z);
            }
            ProfilePreview profilePreview = this.profilePreview;
            if (profilePreview != null) {
                TLRPC.TL_emojiStatusCollectible tL_emojiStatusCollectible = this.selectedEmojiCollectible;
                if (tL_emojiStatusCollectible != null) {
                    profilePreview.setStatusEmoji(tL_emojiStatusCollectible.document_id, true, z);
                    this.profilePreview.setColor(MessagesController.PeerColor.fromCollectible(this.selectedEmojiCollectible), z);
                    this.profilePreview.setEmoji(this.selectedEmojiCollectible.pattern_document_id, true, z);
                } else {
                    if (DialogObject.isEmojiStatusCollectible(PeerColorActivity.this.dialogId)) {
                        this.profilePreview.setStatusEmoji(0L, false, z);
                    } else {
                        this.profilePreview.setStatusEmoji(DialogObject.getEmojiStatusDocumentId(PeerColorActivity.this.dialogId), DialogObject.isEmojiStatusCollectible(PeerColorActivity.this.dialogId), z);
                    }
                    this.profilePreview.setColor(this.selectedColor, z);
                    this.profilePreview.setEmoji(this.selectedEmoji, false, z);
                }
            }
            if (this.type == 0 && PeerColorActivity.this.colorBar != null) {
                if (this.selectedEmojiCollectible == null) {
                    PeerColorActivity.this.colorBar.setColor(((BaseFragment) PeerColorActivity.this).currentAccount, this.selectedColor, z);
                } else {
                    PeerColorActivity.this.colorBar.setColor(MessagesController.PeerColor.fromCollectible(this.selectedEmojiCollectible), z);
                }
            }
            checkResetColorButton();
            updateSelectedGift();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateMessages() {
            MessageObject messageObject;
            ThemePreviewMessagesCell themePreviewMessagesCell = this.messagesCellPreview;
            if (themePreviewMessagesCell != null) {
                ChatMessageCell[] cells = themePreviewMessagesCell.getCells();
                for (int i = 0; i < cells.length; i++) {
                    ChatMessageCell chatMessageCell = cells[i];
                    if (chatMessageCell != null && (messageObject = chatMessageCell.getMessageObject()) != null) {
                        messageObject.notime = true;
                        PeerColorGrid peerColorGrid = this.peerColorPicker;
                        if (peerColorGrid != null) {
                            messageObject.overrideLinkColor = peerColorGrid.getColorId();
                        }
                        messageObject.overrideLinkEmoji = this.selectedEmoji;
                        messageObject.overrideLinkPeerColor = this.selectedPeerCollectible;
                        cells[i].setAvatar(messageObject);
                        cells[i].invalidate();
                    }
                }
            }
        }

        public void update() {
            updateRows();
            this.listAdapter.notifyDataSetChanged();
        }

        public void updateColors() {
            RecyclerListView recyclerListView = this.listView;
            PeerColorActivity peerColorActivity = PeerColorActivity.this;
            int i = Theme.key_windowBackgroundGray;
            recyclerListView.setBackgroundColor(peerColorActivity.getThemedColor(i));
            ButtonWithCounterView buttonWithCounterView = this.button;
            if (buttonWithCounterView != null) {
                buttonWithCounterView.updateColors();
            }
            ThemePreviewMessagesCell themePreviewMessagesCell = this.messagesCellPreview;
            if (themePreviewMessagesCell != null) {
                themePreviewMessagesCell.invalidate();
            }
            updateProfilePreview(true);
            this.buttonContainer.setBackgroundColor(PeerColorActivity.this.getThemedColor(i));
            this.buttonShadow.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_divider));
            AndroidUtilities.forEachViews((RecyclerView) this.listView, new Consumer() { // from class: org.telegram.ui.PeerColorActivity$Page$$ExternalSyntheticLambda2
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$updateColors$2((View) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$updateColors$2(View view) {
            if (view instanceof PeerColorGrid) {
                view.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                ((PeerColorGrid) view).updateColors();
                return;
            }
            if (view instanceof TextCell) {
                view.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                ((TextCell) view).updateColors();
                return;
            }
            if (view instanceof SetReplyIconCell) {
                view.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                ((SetReplyIconCell) view).updateColors();
                return;
            }
            if (view instanceof HeaderCell) {
                view.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                return;
            }
            if (view instanceof GiftCell) {
                ((GiftCell) view).card.invalidate();
                return;
            }
            if (view instanceof GiftSheet.Tabs) {
                view.setBackgroundColor(PeerColorActivity.this.getThemedColor(Theme.key_windowBackgroundWhite));
                ((GiftSheet.Tabs) view).updateColors();
            } else if (view instanceof EmptyView) {
                ((EmptyView) view).updateColors();
            }
        }

        public void premiumChanged() {
            updateButton(true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0090  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a5 A[LOOP:1: B:39:0x009f->B:41:0x00a5, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00bb  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateThemeColors() throws java.io.IOException {
        /*
            r9 = this;
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
            boolean r0 = r9.isDark
            if (r0 == 0) goto L69
            org.telegram.ui.ActionBar.Theme$ThemeInfo r0 = org.telegram.p023ui.ActionBar.Theme.getTheme(r5)
            goto L6d
        L69:
            org.telegram.ui.ActionBar.Theme$ThemeInfo r0 = org.telegram.p023ui.ActionBar.Theme.getTheme(r3)
        L6d:
            android.util.SparseIntArray r1 = r9.currentColors
            r1.clear()
            r1 = 1
            java.lang.String[] r3 = new java.lang.String[r1]
            java.lang.String r4 = r0.assetName
            r5 = 0
            if (r4 == 0) goto L7f
            android.util.SparseIntArray r4 = org.telegram.p023ui.ActionBar.Theme.getThemeFileValues(r5, r4, r3)
            goto L8a
        L7f:
            java.io.File r4 = new java.io.File
            java.lang.String r6 = r0.pathToFile
            r4.<init>(r6)
            android.util.SparseIntArray r4 = org.telegram.p023ui.ActionBar.Theme.getThemeFileValues(r4, r5, r3)
        L8a:
            int[] r5 = org.telegram.p023ui.ActionBar.Theme.getDefaultColors()
            if (r5 == 0) goto L9e
            r6 = 0
        L91:
            int r7 = r5.length
            if (r6 >= r7) goto L9e
            android.util.SparseIntArray r7 = r9.currentColors
            r8 = r5[r6]
            r7.put(r6, r8)
            int r6 = r6 + 1
            goto L91
        L9e:
            r5 = 0
        L9f:
            int r6 = r4.size()
            if (r5 >= r6) goto Lb5
            android.util.SparseIntArray r6 = r9.currentColors
            int r7 = r4.keyAt(r5)
            int r8 = r4.valueAt(r5)
            r6.put(r7, r8)
            int r5 = r5 + 1
            goto L9f
        Lb5:
            org.telegram.ui.ActionBar.Theme$ThemeAccent r5 = r0.getAccent(r2)
            if (r5 == 0) goto Lc0
            android.util.SparseIntArray r6 = r9.currentColors
            r5.fillAccentColors(r4, r6)
        Lc0:
            org.telegram.ui.PeerColorActivity$Page r4 = r9.namePage
            if (r4 == 0) goto Le2
            org.telegram.ui.Cells.ThemePreviewMessagesCell r4 = org.telegram.ui.PeerColorActivity.Page.m15109$$Nest$fgetmessagesCellPreview(r4)
            if (r4 == 0) goto Le2
            android.util.SparseIntArray r4 = r9.currentColors
            r3 = r3[r2]
            org.telegram.ui.ActionBar.Theme$BackgroundDrawableSettings r0 = org.telegram.p023ui.ActionBar.Theme.createBackgroundDrawable(r0, r4, r3, r2, r1)
            org.telegram.ui.PeerColorActivity$Page r1 = r9.namePage
            org.telegram.ui.Cells.ThemePreviewMessagesCell r1 = org.telegram.ui.PeerColorActivity.Page.m15109$$Nest$fgetmessagesCellPreview(r1)
            android.graphics.drawable.Drawable r2 = r0.themedWallpaper
            if (r2 == 0) goto Ldd
            goto Ldf
        Ldd:
            android.graphics.drawable.Drawable r2 = r0.wallpaper
        Ldf:
            r1.setOverrideBackground(r2)
        Le2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PeerColorActivity.updateThemeColors():void");
    }

    public PeerColorActivity(long j) {
        boolean zIsCurrentThemeDark = Theme.isCurrentThemeDark();
        this.isDark = zIsCurrentThemeDark;
        this.forceDark = zIsCurrentThemeDark;
        this.dialogId = j;
        this.isChannel = j != 0;
        if (j >= 0) {
            StarsController.getInstance(this.currentAccount).loadStarGifts();
            StarsController.GiftsList giftsList = new StarsController.GiftsList(this.currentAccount, j, false);
            this.gifts = giftsList;
            giftsList.forceTypeIncludeFlag(8, false);
            giftsList.load();
            StarsController.GiftsList giftsList2 = new StarsController.GiftsList(this.currentAccount, j, false);
            this.giftsWithPeerColor = giftsList2;
            giftsList2.forceTypeIncludeFlag(8, false);
            giftsList2.peer_color_available = true;
            giftsList2.load();
        } else {
            this.gifts = null;
            this.giftsWithPeerColor = null;
        }
        this.resourceProvider = new Theme.ResourcesProvider() { // from class: org.telegram.ui.PeerColorActivity.1
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

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public int getColor(int i) {
                int iIndexOfKey = PeerColorActivity.this.currentColors.indexOfKey(i);
                if (iIndexOfKey >= 0) {
                    return PeerColorActivity.this.currentColors.valueAt(iIndexOfKey);
                }
                if (PeerColorActivity.this.parentResourcesProvider != null) {
                    return PeerColorActivity.this.parentResourcesProvider.getColor(i);
                }
                return Theme.getColor(i);
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public Drawable getDrawable(String str) {
                if (str.equals("drawableMsgIn")) {
                    return PeerColorActivity.this.msgInDrawable;
                }
                if (str.equals("drawableMsgInSelected")) {
                    return PeerColorActivity.this.msgInDrawableSelected;
                }
                if (PeerColorActivity.this.parentResourcesProvider != null) {
                    return PeerColorActivity.this.parentResourcesProvider.getDrawable(str);
                }
                return Theme.getThemeDrawable(str);
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public Paint getPaint(String str) {
                return Theme.getThemePaint(str);
            }

            @Override // org.telegram.ui.ActionBar.Theme.ResourcesProvider
            public boolean isDark() {
                return PeerColorActivity.this.isDark;
            }
        };
        this.msgInDrawable = new Theme.MessageDrawable(0, false, false, this.resourceProvider);
        this.msgInDrawableSelected = new Theme.MessageDrawable(0, false, true, this.resourceProvider);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void setResourceProvider(Theme.ResourcesProvider resourcesProvider) {
        this.parentResourcesProvider = resourcesProvider;
    }

    public PeerColorActivity startOnProfile() {
        this.startAtProfile = true;
        return this;
    }

    public PeerColorActivity setOnApplied(BaseFragment baseFragment) {
        this.bulletinFragment = baseFragment;
        return this;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        getNotificationCenter().addObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        getNotificationCenter().addObserver(this, NotificationCenter.starUserGiftsLoaded);
        getNotificationCenter().addObserver(this, NotificationCenter.starGiftsLoaded);
        Bulletin.addDelegate(this, new Bulletin.Delegate() { // from class: org.telegram.ui.PeerColorActivity.2
            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ boolean allowLayoutChanges() {
                return Bulletin.Delegate.CC.$default$allowLayoutChanges(this);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ boolean bottomOffsetAnimated() {
                return Bulletin.Delegate.CC.$default$bottomOffsetAnimated(this);
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public boolean clipWithGradient(int i) {
                return true;
            }

            @Override // org.telegram.ui.Components.Bulletin.Delegate
            public /* synthetic */ int getTopOffset(int i) {
                return Bulletin.Delegate.CC.$default$getTopOffset(this, i);
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
                return AndroidUtilities.m1146dp(62.0f);
            }
        });
        getMediaDataController().loadReplyIcons();
        if (MessagesController.getInstance(this.currentAccount).peerColors == null && BuildVars.DEBUG_PRIVATE_VERSION) {
            MessagesController.getInstance(this.currentAccount).loadAppConfig(true);
        }
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.namePage = new Page(this, context, 1);
        this.profilePage = new Page(this, context, 0);
        this.actionBar.setCastShadows(false);
        this.actionBar.setVisibility(8);
        this.actionBar.setAllowOverlayTitle(false);
        FrameLayout frameLayout = new FrameLayout(context) { // from class: org.telegram.ui.PeerColorActivity.3
            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                if (PeerColorActivity.this.actionBarContainer != null) {
                    ((ViewGroup.MarginLayoutParams) PeerColorActivity.this.actionBarContainer.getLayoutParams()).height = ActionBar.getCurrentActionBarHeight();
                    ((ViewGroup.MarginLayoutParams) PeerColorActivity.this.actionBarContainer.getLayoutParams()).topMargin = AndroidUtilities.statusBarHeight;
                }
                super.onMeasure(i, i2);
            }
        };
        frameLayout.setFitsSystemWindows(true);
        this.colorBar = new ColoredActionBar(context, this.resourceProvider) { // from class: org.telegram.ui.PeerColorActivity.4
            private int lastBtnColor = 0;

            @Override // org.telegram.ui.PeerColorActivity.ColoredActionBar
            protected void onUpdateColor() {
                PeerColorActivity.this.updateLightStatusBar();
                updateActionBarButtonsColor();
                if (PeerColorActivity.this.tabsView != null) {
                    PeerColorActivity.this.tabsView.setBackgroundColor(getTabsViewBackgroundColor());
                }
            }

            public void updateActionBarButtonsColor() {
                int actionBarButtonColor = getActionBarButtonColor();
                if (this.lastBtnColor != actionBarButtonColor) {
                    if (PeerColorActivity.this.backButton != null) {
                        this.lastBtnColor = actionBarButtonColor;
                        PeerColorActivity.this.backButton.setColorFilter(new PorterDuffColorFilter(actionBarButtonColor, PorterDuff.Mode.SRC_IN));
                    }
                    if (PeerColorActivity.this.dayNightItem != null) {
                        this.lastBtnColor = actionBarButtonColor;
                        PeerColorActivity.this.dayNightItem.setColorFilter(new PorterDuffColorFilter(actionBarButtonColor, PorterDuff.Mode.SRC_IN));
                    }
                }
            }
        };
        this.profilePage.updateProfilePreview(false);
        frameLayout.addView(this.colorBar, LayoutHelper.createFrame(-1, -2, 55));
        ViewPagerFixed viewPagerFixed = new ViewPagerFixed(context) { // from class: org.telegram.ui.PeerColorActivity.5
            @Override // org.telegram.p023ui.Components.ViewPagerFixed
            public void onTabAnimationUpdate(boolean z) {
                PeerColorActivity.this.tabsView.setSelected(PeerColorActivity.this.viewPager.getPositionAnimated());
                PeerColorActivity.this.colorBar.setProgressToGradient(1.0f - PeerColorActivity.this.viewPager.getPositionAnimated());
            }
        };
        this.viewPager = viewPagerFixed;
        viewPagerFixed.setAdapter(new ViewPagerFixed.Adapter() { // from class: org.telegram.ui.PeerColorActivity.6
            @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
            public void bindView(View view, int i, int i2) {
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
            public int getItemCount() {
                return 2;
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
            public int getItemViewType(int i) {
                return i;
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.Adapter
            public View createView(int i) {
                if (i == 1) {
                    return PeerColorActivity.this.namePage;
                }
                if (i == 0) {
                    return PeerColorActivity.this.profilePage;
                }
                return null;
            }
        });
        frameLayout.addView(this.viewPager, LayoutHelper.createFrame(-1, -1, Opcodes.DNEG));
        FrameLayout frameLayout2 = new FrameLayout(context);
        this.actionBarContainer = frameLayout2;
        frameLayout.addView(frameLayout2, LayoutHelper.createFrame(-1, -2, 55));
        if (!this.isChannel) {
            FilledTabsView filledTabsView = new FilledTabsView(context);
            this.tabsView = filledTabsView;
            filledTabsView.setTabs(LocaleController.getString(this.isChannel ? C2369R.string.ChannelColorTabProfile : C2369R.string.UserColorTabProfile), LocaleController.getString(this.isChannel ? C2369R.string.ChannelColorTabName : C2369R.string.UserColorTabName));
            this.tabsView.onTabSelected(new Utilities.Callback() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda0
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$createView$0((Integer) obj);
                }
            });
            this.actionBarContainer.addView(this.tabsView, LayoutHelper.createFrame(-1, 40, 17));
        } else {
            SimpleTextView simpleTextView = new SimpleTextView(context);
            this.titleView = simpleTextView;
            simpleTextView.setText(LocaleController.getString(C2369R.string.ChannelColorTitle2));
            this.titleView.setEllipsizeByGradient(true);
            this.titleView.setTextSize(20);
            this.titleView.setTextColor(getThemedColor(Theme.key_actionBarDefaultTitle));
            this.titleView.setTypeface(AndroidUtilities.bold());
            this.actionBarContainer.addView(this.titleView, LayoutHelper.createFrame(-2, -2.0f, 19, 72.0f, 0.0f, 72.0f, 0.0f));
        }
        ColoredActionBar coloredActionBar = this.colorBar;
        if (coloredActionBar != null) {
            coloredActionBar.setProgressToGradient(1.0f);
            updateLightStatusBar();
        }
        ImageView imageView = new ImageView(context);
        this.backButton = imageView;
        ImageView.ScaleType scaleType = ImageView.ScaleType.CENTER;
        imageView.setScaleType(scaleType);
        ImageView imageView2 = this.backButton;
        int i = Theme.key_actionBarWhiteSelector;
        imageView2.setBackground(Theme.createSelectorDrawable(getThemedColor(i), 1));
        this.backButton.setImageResource(C2369R.drawable.ic_ab_back);
        ImageView imageView3 = this.backButton;
        PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
        imageView3.setColorFilter(new PorterDuffColorFilter(-1, mode));
        this.backButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$1(view);
            }
        });
        this.actionBarContainer.addView(this.backButton, LayoutHelper.createFrame(54, 54, 19));
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
        int color = Theme.getColor(Theme.key_chats_menuName);
        this.sunDrawable.setLayerColor("Sunny.**", color);
        this.sunDrawable.setLayerColor("Path 6.**", color);
        this.sunDrawable.setLayerColor("Path.**", color);
        this.sunDrawable.setLayerColor("Path 5.**", color);
        this.sunDrawable.commitApplyLayerColors();
        ImageView imageView4 = new ImageView(context);
        this.dayNightItem = imageView4;
        imageView4.setScaleType(scaleType);
        this.dayNightItem.setBackground(Theme.createSelectorDrawable(getThemedColor(i), 1));
        this.dayNightItem.setColorFilter(new PorterDuffColorFilter(-1, mode));
        this.dayNightItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$2(view);
            }
        });
        this.actionBarContainer.addView(this.dayNightItem, LayoutHelper.createFrame(54, 54, 21));
        this.dayNightItem.setImageDrawable(this.sunDrawable);
        this.colorBar.updateColors();
        this.contentView = frameLayout;
        this.fragmentView = frameLayout;
        return frameLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(Integer num) {
        ViewPagerFixed viewPagerFixed = this.viewPager;
        if (viewPagerFixed != null) {
            viewPagerFixed.scrollToPosition(num.intValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(View view) {
        if (onBackPressed()) {
            lambda$onBackPressed$371();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2(View view) {
        toggleTheme();
    }

    public boolean hasUnsavedChanged() {
        return this.namePage.hasUnsavedChanged() || this.profilePage.hasUnsavedChanged();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onBackPressed() {
        if (!this.isChannel && hasUnsavedChanged() && getUserConfig().isPremium()) {
            showUnsavedAlert();
            return false;
        }
        return super.onBackPressed();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean isSwipeBackEnabled(MotionEvent motionEvent) {
        if (!this.isChannel && hasUnsavedChanged() && getUserConfig().isPremium()) {
            return false;
        }
        return super.isSwipeBackEnabled(motionEvent);
    }

    private void showUnsavedAlert() {
        if (getVisibleDialog() != null) {
            return;
        }
        AlertDialog alertDialogCreate = new AlertDialog.Builder(getContext(), getResourceProvider()).setTitle(LocaleController.getString(this.isChannel ? C2369R.string.ChannelColorUnsaved : C2369R.string.UserColorUnsaved)).setMessage(LocaleController.getString(this.isChannel ? C2369R.string.ChannelColorUnsavedMessage : C2369R.string.UserColorUnsavedMessage)).setNegativeButton(LocaleController.getString(C2369R.string.Dismiss), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda3
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$showUnsavedAlert$3(alertDialog, i);
            }
        }).setPositiveButton(LocaleController.getString(C2369R.string.ApplyTheme), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda4
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$showUnsavedAlert$4(alertDialog, i);
            }
        }).create();
        showDialog(alertDialogCreate);
        ((TextView) alertDialogCreate.getButton(-2)).setTextColor(getThemedColor(Theme.key_text_RedBold));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnsavedAlert$3(AlertDialog alertDialog, int i) {
        lambda$onBackPressed$371();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showUnsavedAlert$4(AlertDialog alertDialog, int i) {
        buttonClick();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void buttonClick() {
        if (this.loading) {
            return;
        }
        if (this.isChannel) {
            lambda$onBackPressed$371();
        } else if (!getUserConfig().isPremium()) {
            showDialog(new PremiumFeatureBottomSheet(this, 23, true));
            return;
        }
        final Page page = this.viewPager.getCurrentPosition() == 1 ? this.namePage : this.profilePage;
        if (page.selectedResaleGift != null) {
            (this.viewPager.getCurrentPosition() == 1 ? this.profilePage : this.namePage).setupValues();
            this.loading = true;
            page.button.setLoading(true);
            buy(page.selectedResaleGift, new Utilities.Callback() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda6
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    this.f$0.lambda$buttonClick$5(page, (Boolean) obj);
                }
            });
            return;
        }
        Page page2 = this.viewPager.getCurrentPosition() == 1 ? this.profilePage : this.namePage;
        if (page2.selectedResaleGift != null) {
            page2.setupValues();
        }
        apply();
        lambda$onBackPressed$371();
        showBulletin();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buttonClick$5(Page page, Boolean bool) {
        this.loading = false;
        page.button.setLoading(false);
        if (bool.booleanValue()) {
            apply();
            lambda$onBackPressed$371();
            showBulletin();
        }
    }

    public void buy(final TL_stars.TL_starGiftUnique tL_starGiftUnique, final Utilities.Callback callback) {
        final long clientUserId = UserConfig.getInstance(this.currentAccount).getClientUserId();
        final AmountUtils$Currency amountUtils$Currency = tL_starGiftUnique.resale_ton_only ? AmountUtils$Currency.TON : AmountUtils$Currency.STARS;
        StarsController.getInstance(this.currentAccount, amountUtils$Currency).getResellingGiftForm(tL_starGiftUnique, clientUserId, new Utilities.Callback() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda9
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$buy$9(amountUtils$Currency, tL_starGiftUnique, clientUserId, callback, (TLRPC.TL_payments_paymentFormStarGift) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buy$9(AmountUtils$Currency amountUtils$Currency, final TL_stars.TL_starGiftUnique tL_starGiftUnique, final long j, final Utilities.Callback callback, TLRPC.TL_payments_paymentFormStarGift tL_payments_paymentFormStarGift) {
        if (tL_payments_paymentFormStarGift == null) {
            return;
        }
        final boolean[] zArr = new boolean[1];
        StarGiftSheet.ResaleBuyTransferAlert resaleBuyTransferAlert = new StarGiftSheet.ResaleBuyTransferAlert(getContext(), this.resourceProvider, tL_starGiftUnique, new StarGiftSheet.PaymentFormState(amountUtils$Currency, tL_payments_paymentFormStarGift), this.currentAccount, j, tL_starGiftUnique.title + " #" + LocaleController.formatNumber(tL_starGiftUnique.num, ','), new Utilities.Callback2() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda10
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.lambda$buy$7(zArr, tL_starGiftUnique, j, callback, (StarGiftSheet.PaymentFormState) obj, (Browser.Progress) obj2);
            }
        });
        resaleBuyTransferAlert.alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda11
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                PeerColorActivity.$r8$lambda$eYu3uCCt4FXW9oE3MDbVVpoyDLs(zArr, callback, dialogInterface);
            }
        });
        resaleBuyTransferAlert.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$buy$7(boolean[] zArr, TL_stars.TL_starGiftUnique tL_starGiftUnique, long j, final Utilities.Callback callback, StarGiftSheet.PaymentFormState paymentFormState, final Browser.Progress progress) {
        zArr[0] = true;
        progress.init();
        StarsController.getInstance(this.currentAccount, paymentFormState.currency).buyResellingGift(paymentFormState.form, tL_starGiftUnique, j, new Utilities.Callback2() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda12
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                PeerColorActivity.$r8$lambda$lShP0hmlQro1U0FxWJEJJ8rMQSc(progress, callback, (Boolean) obj, (String) obj2);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$lShP0hmlQro1U0FxWJEJJ8rMQSc(Browser.Progress progress, Utilities.Callback callback, Boolean bool, String str) {
        progress.end();
        if (callback != null) {
            callback.run(bool);
        }
    }

    public static /* synthetic */ void $r8$lambda$eYu3uCCt4FXW9oE3MDbVVpoyDLs(boolean[] zArr, Utilities.Callback callback, DialogInterface dialogInterface) {
        if (zArr[0] || callback == null) {
            return;
        }
        callback.run(Boolean.FALSE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01db  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x020d  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0273  */
    /* JADX WARN: Type inference failed for: r3v14, types: [org.telegram.tgnet.TLRPC$TL_emojiStatusEmpty] */
    /* JADX WARN: Type inference failed for: r3v15, types: [org.telegram.tgnet.TLRPC$EmojiStatus] */
    /* JADX WARN: Type inference failed for: r3v16, types: [org.telegram.tgnet.TLRPC$TL_inputEmojiStatusCollectible] */
    /* JADX WARN: Type inference failed for: r4v46, types: [org.telegram.messenger.MessagesController] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void apply() {
        /*
            Method dump skipped, instructions count: 685
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.PeerColorActivity.apply():void");
    }

    private void showBulletin() {
        if (this.bulletinFragment != null) {
            if (this.applyingName && (!this.applyingProfile || getCurrentPage() == this.namePage)) {
                if (this.namePage.selectedColor < 0) {
                    if (this.namePage.selectedPeerCollectible == null) {
                        return;
                    } else {
                        BulletinFactory.m1267of(this.bulletinFragment).createSimpleBulletin(PeerColorDrawable.from(this.namePage.selectedPeerCollectible), LocaleController.getString(this.isChannel ? C2369R.string.ChannelColorApplied : C2369R.string.UserColorApplied)).show();
                    }
                } else {
                    BulletinFactory.m1267of(this.bulletinFragment).createSimpleBulletin(PeerColorDrawable.from(this.currentAccount, this.namePage.selectedColor), LocaleController.getString(this.isChannel ? C2369R.string.ChannelColorApplied : C2369R.string.UserColorApplied)).show();
                }
            } else if (this.applyingProfile && (!this.applyingName || getCurrentPage() == this.profilePage)) {
                if (this.profilePage.selectedColor >= 0) {
                    BulletinFactory.m1267of(this.bulletinFragment).createSimpleBulletin(PeerColorDrawable.fromProfile(this.currentAccount, this.profilePage.selectedColor), LocaleController.getString(this.isChannel ? C2369R.string.ChannelProfileColorApplied : C2369R.string.UserProfileColorApplied)).show();
                } else if (this.profilePage.selectedEmoji != 0) {
                    BulletinFactory.m1267of(this.bulletinFragment).createStaticEmojiBulletin(AnimatedEmojiDrawable.findDocument(this.currentAccount, this.profilePage.selectedEmoji), LocaleController.getString(this.isChannel ? C2369R.string.ChannelProfileColorEmojiApplied : C2369R.string.UserProfileColorEmojiApplied)).show();
                } else {
                    BulletinFactory.m1267of(this.bulletinFragment).createSimpleBulletin(C2369R.raw.contact_check, LocaleController.getString(this.isChannel ? C2369R.string.ChannelProfileColorResetApplied : C2369R.string.UserProfileColorResetApplied)).show();
                }
            }
            this.bulletinFragment = null;
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentClosed() {
        super.onFragmentClosed();
        Bulletin.removeDelegate(this);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        getNotificationCenter().removeObserver(this, NotificationCenter.currentUserPremiumStatusChanged);
        getNotificationCenter().removeObserver(this, NotificationCenter.starUserGiftsLoaded);
        getNotificationCenter().removeObserver(this, NotificationCenter.starGiftsLoaded);
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public ArrayList getThemeDescriptions() {
        return SimpleThemeDescription.createThemeDescriptions(new ThemeDescription.ThemeDescriptionDelegate() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda5
            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public final void didSetColor() {
                this.f$0.updateColors();
            }

            @Override // org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate
            public /* synthetic */ void onAnimationProgress(float f) {
                ThemeDescription.ThemeDescriptionDelegate.CC.$default$onAnimationProgress(this, f);
            }
        }, Theme.key_windowBackgroundWhite, Theme.key_windowBackgroundWhiteBlackText, Theme.key_windowBackgroundWhiteGrayText2, Theme.key_listSelector, Theme.key_windowBackgroundGray, Theme.key_windowBackgroundWhiteGrayText4, Theme.key_text_RedRegular, Theme.key_windowBackgroundChecked, Theme.key_windowBackgroundCheckText, Theme.key_switchTrackBlue, Theme.key_switchTrackBlueChecked, Theme.key_switchTrackBlueThumb, Theme.key_switchTrackBlueThumbChecked);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateColors() {
        this.contentView.setBackgroundColor(getThemedColor(Theme.key_windowBackgroundGray));
        SimpleTextView simpleTextView = this.titleView;
        if (simpleTextView != null) {
            simpleTextView.setTextColor(getThemedColor(Theme.key_actionBarDefaultTitle));
        }
        this.namePage.updateColors();
        this.profilePage.updateColors();
        ColoredActionBar coloredActionBar = this.colorBar;
        if (coloredActionBar != null) {
            coloredActionBar.updateColors();
        }
        setNavigationBarColor(getNavigationBarColor());
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i2 != this.currentAccount) {
            return;
        }
        if (i == NotificationCenter.currentUserPremiumStatusChanged) {
            this.namePage.premiumChanged();
            this.profilePage.premiumChanged();
        } else if (i == NotificationCenter.starUserGiftsLoaded) {
            this.namePage.update();
            this.profilePage.update();
        } else if (i == NotificationCenter.starGiftsLoaded) {
            this.namePage.update();
            this.profilePage.update();
        }
    }

    public static class LevelLock extends Drawable {
        private final PremiumGradient.PremiumGradientTools gradientTools;
        private final Drawable lock;
        private final float lockScale;
        private final Theme.ResourcesProvider resourcesProvider;
        private final Text text;

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

        public LevelLock(Context context, int i, Theme.ResourcesProvider resourcesProvider) {
            this(context, false, i, resourcesProvider);
        }

        public LevelLock(Context context, boolean z, int i, Theme.ResourcesProvider resourcesProvider) {
            this.lockScale = 0.875f;
            this.resourcesProvider = resourcesProvider;
            this.text = new Text(LocaleController.formatPluralString(z ? "BoostLevelPlus" : "BoostLevel", i, new Object[0]), 12.0f, AndroidUtilities.bold());
            Drawable drawableMutate = context.getResources().getDrawable(C2369R.drawable.mini_switch_lock).mutate();
            this.lock = drawableMutate;
            drawableMutate.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_IN));
            this.gradientTools = new PremiumGradient.PremiumGradientTools(Theme.key_premiumGradient1, Theme.key_premiumGradient2, -1, -1, -1, resourcesProvider);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            int i = getBounds().left;
            int iCenterY = getBounds().centerY();
            RectF rectF = AndroidUtilities.rectTmp;
            float f = iCenterY;
            rectF.set(i, f - (getIntrinsicHeight() / 2.0f), getIntrinsicWidth() + i, (getIntrinsicHeight() / 2.0f) + f);
            this.gradientTools.gradientMatrix(rectF);
            canvas.drawRoundRect(rectF, AndroidUtilities.m1146dp(10.0f), AndroidUtilities.m1146dp(10.0f), this.gradientTools.paint);
            this.lock.setBounds(AndroidUtilities.m1146dp(3.33f) + i, (int) (f - ((this.lock.getIntrinsicHeight() * 0.875f) / 2.0f)), (int) (AndroidUtilities.m1146dp(3.33f) + i + (this.lock.getIntrinsicWidth() * 0.875f)), (int) (((this.lock.getIntrinsicHeight() * 0.875f) / 2.0f) + f));
            this.lock.draw(canvas);
            this.text.draw(canvas, i + AndroidUtilities.m1146dp(3.66f) + (this.lock.getIntrinsicWidth() * 0.875f), f, -1, 1.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return (int) (AndroidUtilities.m1146dp(9.66f) + (this.lock.getIntrinsicWidth() * 0.875f) + this.text.getWidth());
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return AndroidUtilities.m1146dp(18.33f);
        }
    }

    public static CharSequence withLevelLock(CharSequence charSequence, int i) {
        if (i <= 0) {
            return charSequence;
        }
        Context context = ApplicationLoader.applicationContext;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        spannableStringBuilder.append((CharSequence) "  L");
        ColoredImageSpan coloredImageSpan = new ColoredImageSpan(new LevelLock(context, i, null));
        coloredImageSpan.setTranslateY(AndroidUtilities.m1146dp(1.0f));
        spannableStringBuilder.setSpan(coloredImageSpan, spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 33);
        return spannableStringBuilder;
    }

    public static class ChangeNameColorCell extends View {
        private final Text buttonText;
        private PeerColorDrawable color1Drawable;
        private PeerColorDrawable color2Drawable;
        private final int currentAccount;
        private final Drawable drawable;
        private final boolean isChannelOrGroup;
        private final boolean isGroup;
        private LevelLock lock;
        private boolean needDivider;
        private final Theme.ResourcesProvider resourcesProvider;
        private Text userText;
        private final Paint userTextBackgroundPaint;
        private int userTextColorKey;

        public ChangeNameColorCell(int i, long j, Context context, Theme.ResourcesProvider resourcesProvider) {
            int i2;
            int i3;
            super(context);
            this.userTextBackgroundPaint = new Paint(1);
            this.userTextColorKey = -1;
            MessagesController messagesController = MessagesController.getInstance(i);
            TLRPC.Chat chat = messagesController.getChat(Long.valueOf(-j));
            this.currentAccount = i;
            boolean z = j < 0;
            this.isChannelOrGroup = z;
            boolean z2 = z && !ChatObject.isChannelAndNotMegaGroup(chat);
            this.isGroup = z2;
            this.resourcesProvider = resourcesProvider;
            Drawable drawableMutate = context.getResources().getDrawable(C2369R.drawable.menu_edit_appearance).mutate();
            this.drawable = drawableMutate;
            drawableMutate.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4, resourcesProvider), PorterDuff.Mode.SRC_IN));
            if (z) {
                i2 = z2 ? C2369R.string.ChangeGroupAppearance : C2369R.string.ChangeChannelNameColor2;
            } else {
                i2 = C2369R.string.ChangeUserNameColor;
            }
            CharSequence string = LocaleController.getString(i2);
            if (z && !z2 && MessagesController.getInstance(i).getMainSettings().getInt("boostingappearance", 0) < 3) {
                MessagesController.PeerColors peerColors = messagesController.peerColors;
                int i4 = ConnectionsManager.DEFAULT_DATACENTER_ID;
                if (peerColors != null) {
                    int iMin = Math.min(ConnectionsManager.DEFAULT_DATACENTER_ID, peerColors.maxLevel());
                    int iMax = Math.max(0, messagesController.peerColors.maxLevel());
                    int iMin2 = Math.min(iMin, messagesController.peerColors.minLevel());
                    int iMax2 = Math.max(iMax, messagesController.peerColors.minLevel());
                    i4 = iMin2;
                    i3 = iMax2;
                } else {
                    i3 = 0;
                }
                int iMin3 = Math.min(i4, messagesController.channelBgIconLevelMin);
                int iMin4 = Math.min(i3, messagesController.channelBgIconLevelMin);
                MessagesController.PeerColors peerColors2 = messagesController.profilePeerColors;
                if (peerColors2 != null) {
                    int iMin5 = Math.min(iMin3, peerColors2.maxLevel());
                    int iMax3 = Math.max(iMin4, messagesController.profilePeerColors.maxLevel());
                    iMin3 = Math.min(iMin5, messagesController.profilePeerColors.minLevel());
                    iMin4 = Math.max(iMax3, messagesController.profilePeerColors.minLevel());
                }
                int iMin6 = Math.min(iMin3, messagesController.channelProfileIconLevelMin);
                int iMax4 = Math.max(iMin4, messagesController.channelProfileIconLevelMin);
                int iMin7 = Math.min(iMin6, messagesController.channelEmojiStatusLevelMin);
                int iMax5 = Math.max(iMax4, messagesController.channelEmojiStatusLevelMin);
                int iMin8 = Math.min(iMin7, messagesController.channelWallpaperLevelMin);
                int iMax6 = Math.max(iMax5, messagesController.channelWallpaperLevelMin);
                int iMin9 = Math.min(iMin8, messagesController.channelCustomWallpaperLevelMin);
                int iMax7 = Math.max(iMax6, messagesController.channelCustomWallpaperLevelMin);
                int i5 = chat != null ? chat.level : 0;
                if (i5 < iMax7) {
                    this.lock = new LevelLock(context, true, Math.max(i5, iMin9), resourcesProvider);
                }
            }
            if (z && this.lock == null) {
                string = TextCell.applyNewSpan(string);
            }
            this.buttonText = new Text(string, 16.0f);
            updateColors();
        }

        public void updateColors() {
            int i;
            this.drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(this.isChannelOrGroup ? Theme.key_windowBackgroundWhiteGrayIcon : Theme.key_windowBackgroundWhiteBlueText4, this.resourcesProvider), PorterDuff.Mode.SRC_IN));
            this.buttonText.setColor(Theme.getColor(this.isChannelOrGroup ? Theme.key_windowBackgroundWhiteBlackText : Theme.key_windowBackgroundWhiteBlueText4, this.resourcesProvider));
            if (this.userText == null || this.userTextBackgroundPaint == null || (i = this.userTextColorKey) == -1) {
                return;
            }
            int color = Theme.getColor(i, this.resourcesProvider);
            this.userText.setColor(color);
            this.userTextBackgroundPaint.setColor(Theme.multAlpha(color, 0.1f));
        }

        public void set(TLRPC.Chat chat, boolean z) {
            int color;
            ArrayList arrayList;
            if (chat == null) {
                return;
            }
            this.needDivider = z;
            this.userText = new Text(Emoji.replaceEmoji(chat.title, Theme.chat_msgTextPaint.getFontMetricsInt(), false), 13.0f, AndroidUtilities.bold());
            PeerColorDrawable peerColorDrawable = this.color1Drawable;
            if (peerColorDrawable != null) {
                peerColorDrawable.setView(null);
            }
            TLRPC.EmojiStatus emojiStatus = chat.emoji_status;
            if (emojiStatus instanceof TLRPC.TL_emojiStatusCollectible) {
                this.color1Drawable = PeerColorDrawable.from((TLRPC.TL_emojiStatusCollectible) emojiStatus);
            } else {
                this.color1Drawable = ChatObject.getProfileColorId(chat) >= 0 ? PeerColorDrawable.fromProfile(this.currentAccount, ChatObject.getProfileColorId(chat)).setRadius(AndroidUtilities.m1146dp(11.0f)) : null;
            }
            PeerColorDrawable peerColorDrawable2 = this.color1Drawable;
            if (peerColorDrawable2 != null) {
                peerColorDrawable2.setView(this);
            }
            TLRPC.PeerColor peerColor = chat.color;
            if (peerColor instanceof TLRPC.TL_peerColorCollectible) {
                TLRPC.TL_peerColorCollectible tL_peerColorCollectible = (TLRPC.TL_peerColorCollectible) peerColor;
                Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
                boolean zIsDark = resourcesProvider != null ? resourcesProvider.isDark() : Theme.isCurrentThemeDark();
                int i = (!zIsDark || (tL_peerColorCollectible.flags & 1) == 0) ? tL_peerColorCollectible.accent_color : tL_peerColorCollectible.dark_accent_color;
                if (!zIsDark || (arrayList = tL_peerColorCollectible.dark_colors) == null) {
                    arrayList = tL_peerColorCollectible.colors;
                }
                int iIntValue = ((Integer) arrayList.get(0)).intValue() | (-16777216);
                int iIntValue2 = arrayList.size() >= 2 ? ((Integer) arrayList.get(1)).intValue() | (-16777216) : iIntValue;
                int iIntValue3 = arrayList.size() >= 3 ? ((Integer) arrayList.get(2)).intValue() | (-16777216) : iIntValue;
                this.userText.setColor(i);
                this.userTextBackgroundPaint.setColor(Theme.multAlpha(i, 0.1f));
                PeerColorDrawable radius = new PeerColorDrawable(iIntValue, iIntValue2, iIntValue3, tL_peerColorCollectible.gift_emoji_id).setRadius(AndroidUtilities.m1146dp(11.0f));
                this.color2Drawable = radius;
                radius.setView(this);
                return;
            }
            int colorId = ChatObject.getColorId(chat);
            if (colorId < 7) {
                int i2 = Theme.keys_avatar_nameInMessage[colorId];
                this.userTextColorKey = i2;
                color = Theme.getColor(i2, this.resourcesProvider);
            } else {
                MessagesController.PeerColors peerColors = MessagesController.getInstance(UserConfig.selectedAccount).peerColors;
                MessagesController.PeerColor color2 = peerColors != null ? peerColors.getColor(colorId) : null;
                if (color2 != null) {
                    this.userTextColorKey = -1;
                    color = color2.getColor1();
                } else {
                    int i3 = Theme.keys_avatar_nameInMessage[0];
                    this.userTextColorKey = i3;
                    color = Theme.getColor(i3, this.resourcesProvider);
                }
            }
            this.userText.setColor(color);
            this.userTextBackgroundPaint.setColor(Theme.multAlpha(color, 0.1f));
            PeerColorDrawable radius2 = PeerColorDrawable.from(this.currentAccount, colorId).setRadius(AndroidUtilities.m1146dp(11.0f));
            this.color2Drawable = radius2;
            if (radius2 != null) {
                radius2.setView(this);
            }
        }

        public void set(TLRPC.User user) {
            int color;
            ArrayList arrayList;
            if (user == null) {
                return;
            }
            String str = user.first_name;
            String strTrim = str == null ? "" : str.trim();
            int iIndexOf = strTrim.indexOf(" ");
            if (iIndexOf > 0) {
                strTrim = strTrim.substring(0, iIndexOf);
            }
            this.userText = new Text(Emoji.replaceEmoji(strTrim, Theme.chat_msgTextPaint.getFontMetricsInt(), false), 13.0f, AndroidUtilities.bold());
            PeerColorDrawable peerColorDrawable = this.color1Drawable;
            if (peerColorDrawable != null) {
                peerColorDrawable.setView(null);
            }
            TLRPC.EmojiStatus emojiStatus = user.emoji_status;
            if (emojiStatus instanceof TLRPC.TL_emojiStatusCollectible) {
                this.color1Drawable = PeerColorDrawable.from((TLRPC.TL_emojiStatusCollectible) emojiStatus);
            } else {
                this.color1Drawable = UserObject.getProfileColorId(user) >= 0 ? PeerColorDrawable.fromProfile(this.currentAccount, UserObject.getProfileColorId(user)).setRadius(AndroidUtilities.m1146dp(11.0f)) : null;
            }
            PeerColorDrawable peerColorDrawable2 = this.color1Drawable;
            if (peerColorDrawable2 != null) {
                peerColorDrawable2.setView(this);
            }
            TLRPC.PeerColor peerColor = user.color;
            if (peerColor instanceof TLRPC.TL_peerColorCollectible) {
                TLRPC.TL_peerColorCollectible tL_peerColorCollectible = (TLRPC.TL_peerColorCollectible) peerColor;
                Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
                boolean zIsDark = resourcesProvider != null ? resourcesProvider.isDark() : Theme.isCurrentThemeDark();
                int i = (!zIsDark || (tL_peerColorCollectible.flags & 1) == 0) ? tL_peerColorCollectible.accent_color : tL_peerColorCollectible.dark_accent_color;
                if (!zIsDark || (arrayList = tL_peerColorCollectible.dark_colors) == null) {
                    arrayList = tL_peerColorCollectible.colors;
                }
                int iIntValue = ((Integer) arrayList.get(0)).intValue() | (-16777216);
                int iIntValue2 = arrayList.size() >= 2 ? ((Integer) arrayList.get(1)).intValue() | (-16777216) : iIntValue;
                int iIntValue3 = arrayList.size() >= 3 ? ((Integer) arrayList.get(2)).intValue() | (-16777216) : iIntValue;
                this.userText.setColor(i);
                this.userTextBackgroundPaint.setColor(Theme.multAlpha(i, 0.1f));
                PeerColorDrawable radius = new PeerColorDrawable(iIntValue, iIntValue2, iIntValue3, tL_peerColorCollectible.gift_emoji_id).setRadius(AndroidUtilities.m1146dp(11.0f));
                this.color2Drawable = radius;
                radius.setView(this);
                return;
            }
            int colorId = UserObject.getColorId(user);
            if (colorId < 7) {
                int i2 = Theme.keys_avatar_nameInMessage[colorId];
                this.userTextColorKey = i2;
                color = Theme.getColor(i2, this.resourcesProvider);
            } else {
                MessagesController.PeerColors peerColors = MessagesController.getInstance(UserConfig.selectedAccount).peerColors;
                MessagesController.PeerColor color2 = peerColors != null ? peerColors.getColor(colorId) : null;
                if (color2 != null) {
                    this.userTextColorKey = -1;
                    color = color2.getColor1();
                } else {
                    int i3 = Theme.keys_avatar_nameInMessage[0];
                    this.userTextColorKey = i3;
                    color = Theme.getColor(i3, this.resourcesProvider);
                }
            }
            this.userText.setColor(color);
            this.userTextBackgroundPaint.setColor(Theme.multAlpha(color, 0.1f));
            this.color2Drawable = PeerColorDrawable.from(this.currentAccount, colorId).setRadius(AndroidUtilities.m1146dp(11.0f));
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(50.0f) + (this.needDivider ? 1 : 0), TLObject.FLAG_30));
        }

        private int rtl(int i) {
            return LocaleController.isRTL ? getMeasuredWidth() - i : i;
        }

        @Override // android.view.View
        protected void dispatchDraw(Canvas canvas) {
            this.drawable.setBounds(rtl(AndroidUtilities.m1146dp(64.0f) / 2) - (this.drawable.getIntrinsicWidth() / 2), (getMeasuredHeight() / 2) - (this.drawable.getIntrinsicHeight() / 2), rtl(AndroidUtilities.m1146dp(64.0f) / 2) + (this.drawable.getIntrinsicWidth() / 2), (getMeasuredHeight() / 2) + (this.drawable.getIntrinsicHeight() / 2));
            this.drawable.draw(canvas);
            Text text = this.buttonText;
            int measuredWidth = getMeasuredWidth() - AndroidUtilities.m1146dp(171.0f);
            text.ellipsize(measuredWidth - (this.lock != null ? r3.getIntrinsicWidth() + AndroidUtilities.m1146dp(8.0f) : 0));
            float measuredWidth2 = LocaleController.isRTL ? (getMeasuredWidth() - this.buttonText.getWidth()) - AndroidUtilities.m1146dp(71.0f) : AndroidUtilities.m1146dp(71.0f);
            this.buttonText.draw(canvas, measuredWidth2, getMeasuredHeight() / 2.0f);
            if (this.lock != null) {
                int width = (int) (measuredWidth2 + this.buttonText.getWidth() + AndroidUtilities.m1146dp(6.0f));
                this.lock.setBounds(width, 0, width, getHeight());
                this.lock.draw(canvas);
            }
            boolean z = this.isGroup;
            if (z && this.color2Drawable != null) {
                int iM1146dp = LocaleController.isRTL ? AndroidUtilities.m1146dp(58.0f) : getMeasuredWidth() - AndroidUtilities.m1146dp(24.0f);
                this.color2Drawable.setBounds(iM1146dp - AndroidUtilities.m1146dp(11.0f), (getMeasuredHeight() - AndroidUtilities.m1146dp(11.0f)) / 2, iM1146dp, (getMeasuredHeight() + AndroidUtilities.m1146dp(11.0f)) / 2);
                this.color2Drawable.stroke(AndroidUtilities.dpf2(3.0f), Theme.getColor(Theme.key_windowBackgroundWhite, this.resourcesProvider));
                this.color2Drawable.draw(canvas);
            } else if (this.color1Drawable != null && this.color2Drawable != null) {
                int iM1146dp2 = LocaleController.isRTL ? AndroidUtilities.m1146dp(58.0f) : getMeasuredWidth() - AndroidUtilities.m1146dp(24.0f);
                this.color2Drawable.setBounds(iM1146dp2 - AndroidUtilities.m1146dp(11.0f), (getMeasuredHeight() - AndroidUtilities.m1146dp(11.0f)) / 2, iM1146dp2, (getMeasuredHeight() + AndroidUtilities.m1146dp(11.0f)) / 2);
                PeerColorDrawable peerColorDrawable = this.color2Drawable;
                float fDpf2 = AndroidUtilities.dpf2(3.0f);
                int i = Theme.key_windowBackgroundWhite;
                peerColorDrawable.stroke(fDpf2, Theme.getColor(i, this.resourcesProvider));
                this.color2Drawable.draw(canvas);
                int iM1146dp3 = iM1146dp2 - AndroidUtilities.m1146dp(18.0f);
                this.color1Drawable.setBounds(iM1146dp3 - AndroidUtilities.m1146dp(11.0f), (getMeasuredHeight() - AndroidUtilities.m1146dp(11.0f)) / 2, iM1146dp3, (getMeasuredHeight() + AndroidUtilities.m1146dp(11.0f)) / 2);
                this.color1Drawable.stroke(AndroidUtilities.dpf2(3.0f), Theme.getColor(i, this.resourcesProvider));
                this.color1Drawable.draw(canvas);
            } else if (this.userText != null && !z) {
                float measuredWidth3 = (int) ((getMeasuredWidth() - AndroidUtilities.m1146dp(116.0f)) - Math.min(this.buttonText.getWidth() + (this.lock == null ? 0 : r3.getIntrinsicWidth() + AndroidUtilities.m1146dp(12.0f)), getMeasuredWidth() - AndroidUtilities.m1146dp(164.0f)));
                int iMin = (int) Math.min(this.userText.getWidth(), measuredWidth3);
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(LocaleController.isRTL ? AndroidUtilities.m1146dp(15.0f) : (getMeasuredWidth() - AndroidUtilities.m1146dp(33.0f)) - iMin, (getMeasuredHeight() - AndroidUtilities.m1146dp(22.0f)) / 2.0f, LocaleController.isRTL ? AndroidUtilities.m1146dp(33.0f) + iMin : getMeasuredWidth() - AndroidUtilities.m1146dp(15.0f), (getMeasuredHeight() + AndroidUtilities.m1146dp(22.0f)) / 2.0f);
                canvas.drawRoundRect(rectF, AndroidUtilities.m1146dp(12.0f), AndroidUtilities.m1146dp(12.0f), this.userTextBackgroundPaint);
                this.userText.ellipsize(measuredWidth3).draw(canvas, LocaleController.isRTL ? AndroidUtilities.m1146dp(24.0f) : (getMeasuredWidth() - AndroidUtilities.m1146dp(24.0f)) - iMin, getMeasuredHeight() / 2.0f);
            }
            if (this.needDivider) {
                Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
                Paint paint = resourcesProvider != null ? resourcesProvider.getPaint("paintDivider") : null;
                if (paint == null) {
                    paint = Theme.dividerPaint;
                }
                canvas.drawLine(LocaleController.isRTL ? 0.0f : AndroidUtilities.m1146dp(64.0f), getMeasuredHeight() - 1, getMeasuredWidth() - (LocaleController.isRTL ? AndroidUtilities.m1146dp(64.0f) : 0), getMeasuredHeight() - 1, paint);
            }
        }
    }

    /* loaded from: classes.dex */
    public static class PeerColorGrid extends View {
        private final Paint backgroundPaint;
        private ColorButton[] buttons;
        private final int currentAccount;
        private final Paint dividerPaint;
        private boolean lock;
        private boolean needDivider;
        private Utilities.Callback onColorClick;
        final int[] order;
        private int[] overrideColors;
        private ColorButton pressedButton;
        private final Theme.ResourcesProvider resourcesProvider;
        private int selectedColorId;
        private final int type;

        /* loaded from: classes5.dex */
        public class ColorButton {
            private final ButtonBounce bounce;
            private Paint closePaint;
            private Path closePath;
            private boolean hasClose;
            private boolean hasColor2;
            private boolean hasColor3;

            /* renamed from: id */
            public int f2039id;
            private Drawable lockDrawable;
            private boolean pressed;
            private boolean selected;
            private final AnimatedFloat selectedT;
            private final Paint paint1 = new Paint(1);
            private final Paint paint2 = new Paint(1);
            private final Paint paint3 = new Paint(1);
            private final Path circlePath = new Path();
            private final Path color2Path = new Path();
            private final RectF bounds = new RectF();
            public final RectF clickBounds = new RectF();

            public ColorButton() {
                this.bounce = new ButtonBounce(PeerColorGrid.this);
                this.selectedT = new AnimatedFloat(PeerColorGrid.this, 0L, 320L, CubicBezierInterpolator.EASE_OUT_QUINT);
            }

            public void set(int i) {
                this.hasColor3 = false;
                this.hasColor2 = false;
                this.paint1.setColor(i);
            }

            public void setClose(boolean z) {
                this.hasClose = z;
            }

            public void set(MessagesController.PeerColor peerColor) {
                if (peerColor == null) {
                    return;
                }
                boolean zIsCurrentThemeDark = PeerColorGrid.this.resourcesProvider == null ? Theme.isCurrentThemeDark() : PeerColorGrid.this.resourcesProvider.isDark();
                if (PeerColorGrid.this.type == 1) {
                    if (zIsCurrentThemeDark && peerColor.hasColor2() && !peerColor.hasColor3()) {
                        this.paint1.setColor(peerColor.getColor(1, PeerColorGrid.this.resourcesProvider));
                        this.paint2.setColor(peerColor.getColor(0, PeerColorGrid.this.resourcesProvider));
                    } else {
                        this.paint1.setColor(peerColor.getColor(0, PeerColorGrid.this.resourcesProvider));
                        this.paint2.setColor(peerColor.getColor(1, PeerColorGrid.this.resourcesProvider));
                    }
                    this.paint3.setColor(peerColor.getColor(2, PeerColorGrid.this.resourcesProvider));
                    this.hasColor2 = peerColor.hasColor2(zIsCurrentThemeDark);
                    this.hasColor3 = peerColor.hasColor3(zIsCurrentThemeDark);
                    return;
                }
                this.paint1.setColor(peerColor.getColor(0, PeerColorGrid.this.resourcesProvider));
                this.paint2.setColor(peerColor.hasColor6(zIsCurrentThemeDark) ? peerColor.getColor(1, PeerColorGrid.this.resourcesProvider) : peerColor.getColor(0, PeerColorGrid.this.resourcesProvider));
                this.hasColor2 = peerColor.hasColor6(zIsCurrentThemeDark);
                this.hasColor3 = false;
            }

            public void setSelected(boolean z, boolean z2) {
                this.selected = z;
                if (!z2) {
                    this.selectedT.set(z, true);
                }
                PeerColorGrid.this.invalidate();
            }

            public void layout(RectF rectF) {
                this.bounds.set(rectF);
            }

            public void layoutClickBounds(RectF rectF) {
                this.clickBounds.set(rectF);
            }

            protected void draw(Canvas canvas) throws Resources.NotFoundException {
                canvas.save();
                float scale = this.bounce.getScale(0.05f);
                canvas.scale(scale, scale, this.bounds.centerX(), this.bounds.centerY());
                canvas.save();
                this.circlePath.rewind();
                this.circlePath.addCircle(this.bounds.centerX(), this.bounds.centerY(), Math.min(this.bounds.height() / 2.0f, this.bounds.width() / 2.0f), Path.Direction.CW);
                canvas.clipPath(this.circlePath);
                canvas.drawPaint(this.paint1);
                if (this.hasColor2) {
                    this.color2Path.rewind();
                    Path path = this.color2Path;
                    RectF rectF = this.bounds;
                    path.moveTo(rectF.right, rectF.top);
                    Path path2 = this.color2Path;
                    RectF rectF2 = this.bounds;
                    path2.lineTo(rectF2.right, rectF2.bottom);
                    Path path3 = this.color2Path;
                    RectF rectF3 = this.bounds;
                    path3.lineTo(rectF3.left, rectF3.bottom);
                    this.color2Path.close();
                    canvas.drawPath(this.color2Path, this.paint2);
                }
                canvas.restore();
                if (this.hasColor3) {
                    canvas.save();
                    float fWidth = this.bounds.width() * 0.315f;
                    RectF rectF4 = AndroidUtilities.rectTmp;
                    float f = fWidth / 2.0f;
                    rectF4.set(this.bounds.centerX() - f, this.bounds.centerY() - f, this.bounds.centerX() + f, this.bounds.centerY() + f);
                    canvas.rotate(45.0f, this.bounds.centerX(), this.bounds.centerY());
                    canvas.drawRoundRect(rectF4, AndroidUtilities.m1146dp(2.33f), AndroidUtilities.m1146dp(2.33f), this.paint3);
                    canvas.restore();
                }
                float f2 = this.selectedT.set(this.selected);
                if (f2 > 0.0f) {
                    PeerColorGrid.this.backgroundPaint.setStrokeWidth(AndroidUtilities.dpf2(2.0f));
                    PeerColorGrid.this.backgroundPaint.setColor(Theme.getColor(Theme.key_windowBackgroundWhite, PeerColorGrid.this.resourcesProvider));
                    canvas.drawCircle(this.bounds.centerX(), this.bounds.centerY(), Math.min(this.bounds.height() / 2.0f, this.bounds.width() / 2.0f) + (PeerColorGrid.this.backgroundPaint.getStrokeWidth() * AndroidUtilities.lerp(0.5f, -2.0f, f2)), PeerColorGrid.this.backgroundPaint);
                }
                if (this.hasClose) {
                    if (PeerColorGrid.this.lock) {
                        if (this.lockDrawable == null) {
                            Drawable drawable = PeerColorGrid.this.getContext().getResources().getDrawable(C2369R.drawable.msg_mini_lock3);
                            this.lockDrawable = drawable;
                            drawable.setColorFilter(new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_IN));
                        }
                        this.lockDrawable.setBounds((int) (this.bounds.centerX() - ((this.lockDrawable.getIntrinsicWidth() / 2.0f) * 1.2f)), (int) (this.bounds.centerY() - ((this.lockDrawable.getIntrinsicHeight() / 2.0f) * 1.2f)), (int) (this.bounds.centerX() + ((this.lockDrawable.getIntrinsicWidth() / 2.0f) * 1.2f)), (int) (this.bounds.centerY() + ((this.lockDrawable.getIntrinsicHeight() / 2.0f) * 1.2f)));
                        this.lockDrawable.draw(canvas);
                    } else {
                        if (this.closePath == null) {
                            this.closePath = new Path();
                        }
                        if (this.closePaint == null) {
                            Paint paint = new Paint(1);
                            this.closePaint = paint;
                            paint.setColor(-1);
                            this.closePaint.setStyle(Paint.Style.STROKE);
                            this.closePaint.setStrokeCap(Paint.Cap.ROUND);
                        }
                        this.closePaint.setStrokeWidth(AndroidUtilities.m1146dp(2.0f));
                        this.closePath.rewind();
                        float fLerp = AndroidUtilities.lerp(AndroidUtilities.m1146dp(5.0f), AndroidUtilities.m1146dp(4.0f), f2);
                        this.closePath.moveTo(this.bounds.centerX() - fLerp, this.bounds.centerY() - fLerp);
                        this.closePath.lineTo(this.bounds.centerX() + fLerp, this.bounds.centerY() + fLerp);
                        this.closePath.moveTo(this.bounds.centerX() + fLerp, this.bounds.centerY() - fLerp);
                        this.closePath.lineTo(this.bounds.centerX() - fLerp, this.bounds.centerY() + fLerp);
                        canvas.drawPath(this.closePath, this.closePaint);
                    }
                }
                canvas.restore();
            }

            public void setPressed(boolean z) {
                ButtonBounce buttonBounce = this.bounce;
                this.pressed = z;
                buttonBounce.setPressed(z);
            }
        }

        public PeerColorGrid(Context context, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            Paint paint = new Paint(1);
            this.backgroundPaint = paint;
            paint.setStyle(Paint.Style.STROKE);
            this.order = new int[]{5, 3, 1, 0, 2, 4, 6, -1};
            this.dividerPaint = new Paint(1);
            this.needDivider = true;
            this.selectedColorId = 0;
            this.type = i;
            this.currentAccount = i2;
            this.resourcesProvider = resourcesProvider;
        }

        public void setOverrideColors(int[] iArr) {
            this.overrideColors = iArr;
        }

        public void setCloseAsLock(boolean z) {
            this.lock = z;
        }

        public void updateColors() {
            int i;
            if (this.buttons == null) {
                return;
            }
            MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
            MessagesController.PeerColors peerColors = this.type == 1 ? messagesController.peerColors : messagesController.profilePeerColors;
            int i2 = 0;
            while (true) {
                ColorButton[] colorButtonArr = this.buttons;
                if (i2 < colorButtonArr.length) {
                    int i3 = this.type;
                    if (i3 == 2) {
                        ColorButton colorButton = colorButtonArr[i2];
                        int i4 = this.order[i2];
                        colorButton.f2039id = i4;
                        colorButton.setClose(i4 < 0);
                        ColorButton colorButton2 = this.buttons[i2];
                        int i5 = this.order[i2];
                        if (i5 < 0) {
                            i = Theme.key_avatar_backgroundGray;
                        } else {
                            int[] iArr = Theme.keys_avatar_nameInMessage;
                            i = iArr[i5 % iArr.length];
                        }
                        colorButton2.set(Theme.getColor(i, this.resourcesProvider));
                    } else if (i2 < 7 && i3 == 1) {
                        ColorButton colorButton3 = colorButtonArr[i2];
                        int i6 = this.order[i2];
                        colorButton3.f2039id = i6;
                        colorButton3.set(Theme.getColor(Theme.keys_avatar_nameInMessage[i6], this.resourcesProvider));
                    } else if (i3 == 10) {
                        ColorButton colorButton4 = colorButtonArr[i2];
                        colorButton4.f2039id = i2;
                        colorButton4.setClose(false);
                        this.buttons[i2].set(this.overrideColors[i2]);
                    } else if (peerColors != null && i2 >= 0 && i2 < peerColors.colors.size()) {
                        this.buttons[i2].f2039id = peerColors.colors.get(i2).f1456id;
                        this.buttons[i2].set(peerColors.colors.get(i2));
                    }
                    i2++;
                } else {
                    invalidate();
                    return;
                }
            }
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            int i3;
            int size = View.MeasureSpec.getSize(i);
            MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
            MessagesController.PeerColors peerColors = this.type == 1 ? messagesController.peerColors : messagesController.profilePeerColors;
            int size2 = peerColors == null ? 0 : peerColors.colors.size();
            int i4 = this.type;
            int i5 = 8;
            int i6 = 2;
            if (i4 == 2 || i4 == 10) {
                size2 = 8;
            }
            if (i4 != 2 && i4 != 10 && i4 == 1) {
                i5 = 7;
            }
            float f = size;
            float f2 = i5;
            float f3 = i5 + 1;
            float fMin = Math.min(AndroidUtilities.m1146dp(54.0f), f / ((f3 * 0.28947f) + f2));
            float fMin2 = Math.min(0.28947f * fMin, AndroidUtilities.m1146dp(8.0f));
            float fMin3 = Math.min(0.31578946f * fMin, AndroidUtilities.m1146dp(11.33f));
            setMeasuredDimension(size, (int) (((size2 / i5) * fMin) + ((r15 + 1) * fMin3)));
            ColorButton[] colorButtonArr = this.buttons;
            if (colorButtonArr == null || colorButtonArr.length != size2) {
                this.buttons = new ColorButton[size2];
                int i7 = 0;
                while (i7 < size2) {
                    this.buttons[i7] = new ColorButton();
                    int i8 = this.type;
                    if (i8 == i6) {
                        ColorButton colorButton = this.buttons[i7];
                        int i9 = this.order[i7];
                        colorButton.f2039id = i9;
                        colorButton.setClose(i9 < 0);
                        ColorButton colorButton2 = this.buttons[i7];
                        int i10 = this.order[i7];
                        if (i10 < 0) {
                            i3 = Theme.key_avatar_backgroundGray;
                        } else {
                            int[] iArr = Theme.keys_avatar_nameInMessage;
                            i3 = iArr[i10 % iArr.length];
                        }
                        colorButton2.set(Theme.getColor(i3, this.resourcesProvider));
                    } else if (i8 == 10) {
                        ColorButton colorButton3 = this.buttons[i7];
                        colorButton3.f2039id = i7;
                        colorButton3.setClose(false);
                        this.buttons[i7].set(this.overrideColors[i7]);
                    } else if (peerColors != null && i7 >= 0 && i7 < peerColors.colors.size()) {
                        this.buttons[i7].f2039id = peerColors.colors.get(i7).f1456id;
                        this.buttons[i7].set(peerColors.colors.get(i7));
                    }
                    i7++;
                    i6 = 2;
                }
            }
            float f4 = ((f - ((f2 * fMin) + (f3 * fMin2))) / 2.0f) + fMin2;
            if (this.buttons != null) {
                float f5 = f4;
                float f6 = fMin3;
                for (int i11 = 0; i11 < this.buttons.length; i11++) {
                    RectF rectF = AndroidUtilities.rectTmp;
                    rectF.set(f5, f6, f5 + fMin, f6 + fMin);
                    this.buttons[i11].layout(rectF);
                    rectF.inset((-fMin2) / 2.0f, (-fMin3) / 2.0f);
                    this.buttons[i11].layoutClickBounds(rectF);
                    ColorButton colorButton4 = this.buttons[i11];
                    colorButton4.setSelected(colorButton4.f2039id == this.selectedColorId, false);
                    if (i11 % i5 == i5 - 1) {
                        f6 += fMin + fMin3;
                        f5 = f4;
                    } else {
                        f5 += fMin + fMin2;
                    }
                }
            }
        }

        public void setDivider(boolean z) {
            this.needDivider = z;
            invalidate();
        }

        @Override // android.view.View
        protected void dispatchDraw(Canvas canvas) throws Resources.NotFoundException {
            if (this.buttons != null) {
                int i = 0;
                while (true) {
                    ColorButton[] colorButtonArr = this.buttons;
                    if (i >= colorButtonArr.length) {
                        break;
                    }
                    colorButtonArr[i].draw(canvas);
                    i++;
                }
            }
            if (this.needDivider) {
                this.dividerPaint.setColor(Theme.getColor(Theme.key_divider, this.resourcesProvider));
                canvas.drawRect(AndroidUtilities.m1146dp(21.0f), getMeasuredHeight() - 1, getMeasuredWidth() - AndroidUtilities.m1146dp(21.0f), getMeasuredHeight(), this.dividerPaint);
            }
        }

        public void setSelected(int i, boolean z) {
            this.selectedColorId = i;
            if (this.buttons == null) {
                return;
            }
            int i2 = 0;
            while (true) {
                ColorButton[] colorButtonArr = this.buttons;
                if (i2 >= colorButtonArr.length) {
                    return;
                }
                ColorButton colorButton = colorButtonArr[i2];
                colorButton.setSelected(colorButton.f2039id == i, z);
                i2++;
            }
        }

        public int getColorId() {
            return this.selectedColorId;
        }

        public void setOnColorClick(Utilities.Callback<Integer> callback) {
            this.onColorClick = callback;
        }

        @Override // android.view.View
        public boolean dispatchTouchEvent(MotionEvent motionEvent) {
            ColorButton colorButton;
            ColorButton colorButton2;
            Utilities.Callback callback;
            Utilities.Callback callback2;
            if (this.buttons != null) {
                int i = 0;
                while (true) {
                    ColorButton[] colorButtonArr = this.buttons;
                    if (i >= colorButtonArr.length) {
                        break;
                    }
                    if (colorButtonArr[i].clickBounds.contains(motionEvent.getX(), motionEvent.getY())) {
                        colorButton = this.buttons[i];
                        break;
                    }
                    i++;
                }
            } else {
                colorButton = null;
            }
            if (motionEvent.getAction() == 0) {
                this.pressedButton = colorButton;
                if (colorButton != null) {
                    colorButton.setPressed(true);
                }
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            } else if (motionEvent.getAction() == 2) {
                ColorButton colorButton3 = this.pressedButton;
                if (colorButton3 != colorButton) {
                    if (colorButton3 != null) {
                        colorButton3.setPressed(false);
                    }
                    if (colorButton != null) {
                        colorButton.setPressed(true);
                    }
                    if (this.pressedButton != null && colorButton != null && (callback2 = this.onColorClick) != null) {
                        callback2.run(Integer.valueOf(colorButton.f2039id));
                    }
                    this.pressedButton = colorButton;
                }
            } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                if (motionEvent.getAction() == 1 && (colorButton2 = this.pressedButton) != null && (callback = this.onColorClick) != null) {
                    callback.run(Integer.valueOf(colorButton2.f2039id));
                }
                if (this.buttons != null) {
                    int i2 = 0;
                    while (true) {
                        ColorButton[] colorButtonArr2 = this.buttons;
                        if (i2 >= colorButtonArr2.length) {
                            break;
                        }
                        colorButtonArr2[i2].setPressed(false);
                        i2++;
                    }
                }
                this.pressedButton = null;
            }
            return true;
        }
    }

    public static class PeerColorSpan extends ReplacementSpan {
        public PeerColorDrawable drawable;
        private int size = AndroidUtilities.m1146dp(21.0f);

        public PeerColorSpan(boolean z, int i, int i2) {
            this.drawable = z ? PeerColorDrawable.fromProfile(i, i2) : PeerColorDrawable.from(i, i2);
        }

        public PeerColorSpan setSize(int i) {
            PeerColorDrawable peerColorDrawable = this.drawable;
            if (peerColorDrawable != null) {
                peerColorDrawable.setRadius(i / 2.0f);
                this.size = i;
            }
            return this;
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            return AndroidUtilities.m1146dp(3.0f) + this.size + AndroidUtilities.m1146dp(3.0f);
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
            PeerColorDrawable peerColorDrawable = this.drawable;
            if (peerColorDrawable != null) {
                int i6 = (i3 + i5) / 2;
                int i7 = this.size;
                peerColorDrawable.setBounds((int) (AndroidUtilities.m1146dp(3.0f) + f), i6 - this.size, (int) (f + AndroidUtilities.m1146dp(5.0f) + i7), i6 + i7);
                this.drawable.draw(canvas);
            }
        }
    }

    public static class PeerColorDrawable extends Drawable {
        private final Path clipCirclePath;
        private final Paint color1Paint;
        private final Paint color2Paint;
        private final Path color2Path;
        private final Paint color3Paint;
        private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable emoji;
        private final boolean hasColor3;
        private float radius = AndroidUtilities.dpf2(10.6665f);
        private Paint strokePaint;

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

        public static PeerColorDrawable from(TLRPC.TL_peerColorCollectible tL_peerColorCollectible) {
            ArrayList arrayList;
            if (!Theme.isCurrentThemeDark() || (arrayList = tL_peerColorCollectible.dark_colors) == null) {
                arrayList = tL_peerColorCollectible.colors;
            }
            if (arrayList == null || arrayList.isEmpty()) {
                return null;
            }
            int iIntValue = ((Integer) arrayList.get(0)).intValue() | (-16777216);
            return new PeerColorDrawable(iIntValue, arrayList.size() >= 2 ? ((Integer) arrayList.get(1)).intValue() | (-16777216) : iIntValue, arrayList.size() >= 3 ? ((Integer) arrayList.get(2)).intValue() | (-16777216) : iIntValue, tL_peerColorCollectible.gift_emoji_id);
        }

        public static PeerColorDrawable from(TLRPC.TL_emojiStatusCollectible tL_emojiStatusCollectible) {
            int i = tL_emojiStatusCollectible.center_color | (-16777216);
            return new PeerColorDrawable(i, i, i, tL_emojiStatusCollectible.document_id);
        }

        public static PeerColorDrawable from(int i, int i2) {
            if (i2 < 7) {
                return new PeerColorDrawable(Theme.getColor(Theme.keys_avatar_nameInMessage[i2]), Theme.getColor(Theme.keys_avatar_nameInMessage[i2]), Theme.getColor(Theme.keys_avatar_nameInMessage[i2]));
            }
            MessagesController.PeerColors peerColors = MessagesController.getInstance(i).peerColors;
            return from(peerColors == null ? null : peerColors.getColor(i2), false);
        }

        public static PeerColorDrawable fromProfile(int i, int i2) {
            MessagesController.PeerColors peerColors = MessagesController.getInstance(i).profilePeerColors;
            return from(peerColors == null ? null : peerColors.getColor(i2), true);
        }

        public static PeerColorDrawable from(MessagesController.PeerColor peerColor, boolean z) {
            if (peerColor == null) {
                return new PeerColorDrawable(0, 0, 0);
            }
            return new PeerColorDrawable(peerColor.getColor1(), (!z || peerColor.hasColor6(Theme.isCurrentThemeDark())) ? peerColor.getColor2() : peerColor.getColor1(), z ? peerColor.getColor1() : peerColor.getColor3());
        }

        public PeerColorDrawable setRadius(float f) {
            this.radius = f;
            initPath();
            return this;
        }

        public PeerColorDrawable stroke(float f, int i) {
            if (this.strokePaint == null) {
                Paint paint = new Paint(1);
                this.strokePaint = paint;
                paint.setStyle(Paint.Style.STROKE);
            }
            this.strokePaint.setStrokeWidth(f);
            this.strokePaint.setColor(i);
            return this;
        }

        public PeerColorDrawable(int i, int i2, int i3) {
            Paint paint = new Paint(1);
            this.color1Paint = paint;
            Paint paint2 = new Paint(1);
            this.color2Paint = paint2;
            Paint paint3 = new Paint(1);
            this.color3Paint = paint3;
            this.color2Path = new Path();
            this.clipCirclePath = new Path();
            this.hasColor3 = i3 != i;
            paint.setColor(i);
            paint2.setColor(i2);
            paint3.setColor(i3);
            this.emoji = null;
            initPath();
        }

        public PeerColorDrawable(int i, int i2, int i3, long j) {
            Paint paint = new Paint(1);
            this.color1Paint = paint;
            Paint paint2 = new Paint(1);
            this.color2Paint = paint2;
            Paint paint3 = new Paint(1);
            this.color3Paint = paint3;
            this.color2Path = new Path();
            this.clipCirclePath = new Path();
            this.hasColor3 = i3 != i;
            paint.setColor(i);
            paint2.setColor(i2);
            paint3.setColor(i3);
            initPath();
            AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(null, AndroidUtilities.m1146dp(14.0f));
            this.emoji = swapAnimatedEmojiDrawable;
            swapAnimatedEmojiDrawable.set(j, false);
        }

        public PeerColorDrawable setView(View view) {
            if (view == null) {
                AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.emoji;
                if (swapAnimatedEmojiDrawable != null) {
                    swapAnimatedEmojiDrawable.detach();
                    this.emoji.setParentView(null);
                }
                return this;
            }
            AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable2 = this.emoji;
            if (swapAnimatedEmojiDrawable2 != null) {
                swapAnimatedEmojiDrawable2.setParentView(view);
            }
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: org.telegram.ui.PeerColorActivity.PeerColorDrawable.1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view2) {
                    if (PeerColorDrawable.this.emoji != null) {
                        PeerColorDrawable.this.emoji.attach();
                    }
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view2) {
                    if (PeerColorDrawable.this.emoji != null) {
                        PeerColorDrawable.this.emoji.detach();
                    }
                }
            });
            return this;
        }

        private void initPath() {
            this.clipCirclePath.rewind();
            Path path = this.clipCirclePath;
            float f = this.radius;
            path.addCircle(f, f, f, Path.Direction.CW);
            this.color2Path.rewind();
            this.color2Path.moveTo(this.radius * 2.0f, 0.0f);
            Path path2 = this.color2Path;
            float f2 = this.radius;
            path2.lineTo(f2 * 2.0f, f2 * 2.0f);
            this.color2Path.lineTo(0.0f, this.radius * 2.0f);
            this.color2Path.close();
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.translate(getBounds().centerX() - this.radius, getBounds().centerY() - this.radius);
            Paint paint = this.strokePaint;
            if (paint != null) {
                float f = this.radius;
                canvas.drawCircle(f, f, f, paint);
            }
            canvas.clipPath(this.clipCirclePath);
            canvas.drawPaint(this.color1Paint);
            canvas.drawPath(this.color2Path, this.color2Paint);
            if (this.hasColor3) {
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(this.radius - AndroidUtilities.m1146dp(3.66f), this.radius - AndroidUtilities.m1146dp(3.66f), this.radius + AndroidUtilities.m1146dp(3.66f), this.radius + AndroidUtilities.m1146dp(3.66f));
                float f2 = this.radius;
                canvas.rotate(45.0f, f2, f2);
                canvas.drawRoundRect(rectF, AndroidUtilities.m1146dp(2.33f), AndroidUtilities.m1146dp(2.33f), this.color3Paint);
            }
            canvas.restore();
            if (this.emoji != null) {
                int iM1146dp = AndroidUtilities.m1146dp(14.0f) / 2;
                this.emoji.setBounds(getBounds().centerX() - iM1146dp, getBounds().centerY() - iM1146dp, getBounds().centerX() + iM1146dp, getBounds().centerY() + iM1146dp);
                this.emoji.draw(canvas);
            }
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return (int) (this.radius * 2.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return (int) (this.radius * 2.0f);
        }
    }

    public static class ColoredActionBar extends View {
        private RadialGradient backgroundGradient;
        private int backgroundGradientColor1;
        private int backgroundGradientColor2;
        private int backgroundGradientHeight;
        private int backgroundGradientWidth;
        private final Paint backgroundPaint;
        public int color1;
        private final AnimatedColor color1Animated;
        public int color2;
        private final AnimatedColor color2Animated;
        private int defaultColor;
        protected boolean ignoreMeasure;
        public boolean isDefault;
        private float progressToGradient;
        private final Theme.ResourcesProvider resourcesProvider;

        protected void onUpdateColor() {
        }

        public ColoredActionBar(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            this.progressToGradient = 0.0f;
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
            this.color1Animated = new AnimatedColor(this, 350L, cubicBezierInterpolator);
            this.color2Animated = new AnimatedColor(this, 350L, cubicBezierInterpolator);
            this.backgroundPaint = new Paint(1);
            this.resourcesProvider = resourcesProvider;
            this.defaultColor = Theme.getColor(Theme.key_actionBarDefault, resourcesProvider);
            setColor(-1, -1, false);
        }

        public void setColor(int i, int i2, boolean z) {
            MessagesController.PeerColors peerColors;
            MessagesController.PeerColor color = null;
            if (i2 >= 0 && i >= 0 && (peerColors = MessagesController.getInstance(i).profilePeerColors) != null) {
                color = peerColors.getColor(i2);
            }
            setColor(color, z);
        }

        public void setColor(MessagesController.PeerColor peerColor, boolean z) {
            this.isDefault = false;
            if (peerColor == null) {
                this.isDefault = true;
                int color = Theme.getColor(Theme.key_actionBarDefault, this.resourcesProvider);
                this.color2 = color;
                this.color1 = color;
            } else {
                Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
                boolean zIsDark = resourcesProvider != null ? resourcesProvider.isDark() : Theme.isCurrentThemeDark();
                this.color1 = peerColor.getBgColor1(zIsDark);
                this.color2 = peerColor.getBgColor2(zIsDark);
            }
            if (!z) {
                this.color1Animated.set(this.color1, true);
                this.color2Animated.set(this.color2, true);
            }
            invalidate();
        }

        public void setProgressToGradient(float f) {
            if (Math.abs(this.progressToGradient - f) > 0.001f) {
                this.progressToGradient = f;
                onUpdateColor();
                invalidate();
            }
        }

        @Override // android.view.View
        protected void dispatchDraw(Canvas canvas) {
            int i = this.color1Animated.set(this.color1);
            int i2 = this.color2Animated.set(this.color2);
            if (this.backgroundGradient == null || this.backgroundGradientColor1 != i || this.backgroundGradientColor2 != i2 || this.backgroundGradientWidth != getWidth() || this.backgroundGradientHeight != getHeight()) {
                this.backgroundGradientWidth = getWidth();
                this.backgroundGradientHeight = getHeight();
                int i3 = this.backgroundGradientWidth;
                int i4 = this.backgroundGradientHeight;
                float fDistance = AndroidUtilities.distance(0.0f, 0.0f, i3, i4) * 0.75f;
                this.backgroundGradientColor2 = i2;
                this.backgroundGradientColor1 = i;
                RadialGradient radialGradient = new RadialGradient(i3 / 2.0f, i4 * 0.4f, fDistance, new int[]{i2, i}, new float[]{0.0f, 1.0f}, Shader.TileMode.CLAMP);
                this.backgroundGradient = radialGradient;
                this.backgroundPaint.setShader(radialGradient);
                onUpdateColor();
            }
            if (this.progressToGradient < 1.0f) {
                canvas.drawColor(this.defaultColor);
            }
            float f = this.progressToGradient;
            if (f > 0.0f) {
                this.backgroundPaint.setAlpha((int) (f * 255.0f));
                canvas.drawRect(0.0f, 0.0f, getWidth(), getHeight(), this.backgroundPaint);
            }
        }

        @Override // android.view.View
        protected void onMeasure(int i, int i2) {
            if (!this.ignoreMeasure) {
                i2 = View.MeasureSpec.makeMeasureSpec(AndroidUtilities.statusBarHeight + AndroidUtilities.m1146dp(230.0f), TLObject.FLAG_30);
            }
            super.onMeasure(i, i2);
        }

        public void updateColors() {
            this.defaultColor = Theme.getColor(Theme.key_actionBarDefault, this.resourcesProvider);
            onUpdateColor();
            invalidate();
        }

        public int getColor() {
            return ColorUtils.blendARGB(Theme.getColor(Theme.key_actionBarDefault, this.resourcesProvider), ColorUtils.blendARGB(this.color1Animated.get(), this.color2Animated.get(), 0.75f), this.progressToGradient);
        }

        public int getActionBarButtonColor() {
            int i = Theme.key_actionBarDefaultIcon;
            return ColorUtils.blendARGB(Theme.getColor(i, this.resourcesProvider), this.isDefault ? Theme.getColor(i, this.resourcesProvider) : -1, this.progressToGradient);
        }

        public int getTabsViewBackgroundColor() {
            int iAdaptHSV;
            int iAdaptHSV2;
            int i = Theme.key_actionBarDefault;
            if (AndroidUtilities.computePerceivedBrightness(Theme.getColor(i, this.resourcesProvider)) > 0.721f) {
                iAdaptHSV = Theme.getColor(Theme.key_actionBarDefaultIcon, this.resourcesProvider);
            } else {
                iAdaptHSV = Theme.adaptHSV(Theme.getColor(i, this.resourcesProvider), 0.08f, -0.08f);
            }
            if (AndroidUtilities.computePerceivedBrightness(ColorUtils.blendARGB(this.color1Animated.get(), this.color2Animated.get(), 0.75f)) > 0.721f) {
                iAdaptHSV2 = Theme.getColor(Theme.key_windowBackgroundWhiteBlueIcon, this.resourcesProvider);
            } else {
                iAdaptHSV2 = Theme.adaptHSV(ColorUtils.blendARGB(this.color1Animated.get(), this.color2Animated.get(), 0.75f), 0.08f, -0.08f);
            }
            return ColorUtils.blendARGB(iAdaptHSV, iAdaptHSV2, this.progressToGradient);
        }
    }

    public static class ProfilePreview extends FrameLayout {
        protected final AvatarDrawable avatarDrawable;
        private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable botVerificationEmoji;
        private final int currentAccount;
        private final long dialogId;
        private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable emoji;
        private final AnimatedFloat emojiCollectible;
        protected final ImageReceiver imageReceiver;
        private final boolean isChannel;
        private boolean isEmojiCollectible;
        private boolean isForum;
        private MessagesController.PeerColor peerColor;
        private final RectF rectF;
        private final Theme.ResourcesProvider resourcesProvider;
        private final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable statusEmoji;
        private final StoriesUtilities.StoryGradientTools storyGradient;
        protected final SimpleTextView subtitleView;
        protected final SimpleTextView titleView;

        public ProfilePreview(Context context, int i, long j, Theme.ResourcesProvider resourcesProvider) {
            CharSequence userName;
            long botVerificationIcon;
            super(context);
            ImageReceiver imageReceiver = new ImageReceiver(this);
            this.imageReceiver = imageReceiver;
            AvatarDrawable avatarDrawable = new AvatarDrawable();
            this.avatarDrawable = avatarDrawable;
            this.emoji = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(this, false, AndroidUtilities.m1146dp(20.0f), 13);
            this.storyGradient = new StoriesUtilities.StoryGradientTools((View) this, false);
            this.emojiCollectible = new AnimatedFloat(this, 320L, CubicBezierInterpolator.EASE_OUT_QUINT);
            this.rectF = new RectF();
            this.currentAccount = i;
            this.dialogId = j;
            this.resourcesProvider = resourcesProvider;
            long emojiStatusDocumentId = 0;
            boolean z = j < 0;
            this.isChannel = z;
            SimpleTextView simpleTextView = new SimpleTextView(context) { // from class: org.telegram.ui.PeerColorActivity.ProfilePreview.1
                @Override // org.telegram.p023ui.ActionBar.SimpleTextView, android.view.View
                protected void onAttachedToWindow() {
                    super.onAttachedToWindow();
                    ProfilePreview.this.statusEmoji.attach();
                }

                @Override // org.telegram.p023ui.ActionBar.SimpleTextView, android.view.View
                protected void onDetachedFromWindow() {
                    super.onDetachedFromWindow();
                    ProfilePreview.this.statusEmoji.detach();
                }
            };
            this.titleView = simpleTextView;
            this.botVerificationEmoji = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(simpleTextView, AndroidUtilities.m1146dp(17.0f));
            this.statusEmoji = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(simpleTextView, AndroidUtilities.m1146dp(24.0f));
            simpleTextView.setLeftDrawableOutside(true);
            simpleTextView.setRightDrawableOutside(true);
            simpleTextView.setTextColor(-1);
            simpleTextView.setTextSize(20);
            simpleTextView.setTypeface(AndroidUtilities.bold());
            simpleTextView.setWidthWrapContent(true);
            addView(simpleTextView, LayoutHelper.createFrame(-2, -2.0f, 81, 16.0f, 0.0f, 16.0f, 40.33f));
            SimpleTextView simpleTextView2 = new SimpleTextView(context);
            this.subtitleView = simpleTextView2;
            simpleTextView2.setTextSize(14);
            simpleTextView2.setTextColor(-2130706433);
            simpleTextView2.setGravity(1);
            addView(simpleTextView2, LayoutHelper.createFrame(-2, -2.0f, 81, 16.0f, 0.0f, 16.0f, 20.66f));
            imageReceiver.setRoundRadius(AndroidUtilities.m1146dp(96.0f));
            if (z) {
                TLRPC.Chat chat = MessagesController.getInstance(i).getChat(Long.valueOf(-j));
                userName = chat == null ? "" : chat.title;
                avatarDrawable.setInfo(i, chat);
                imageReceiver.setForUserOrChat(chat, avatarDrawable);
                botVerificationIcon = DialogObject.getBotVerificationIcon(chat);
                if (chat != null) {
                    emojiStatusDocumentId = DialogObject.getEmojiStatusDocumentId(chat.emoji_status);
                }
            } else {
                TLRPC.User currentUser = UserConfig.getInstance(i).getCurrentUser();
                userName = UserObject.getUserName(currentUser);
                avatarDrawable.setInfo(i, currentUser);
                imageReceiver.setForUserOrChat(currentUser, avatarDrawable);
                botVerificationIcon = DialogObject.getBotVerificationIcon(currentUser);
                if (currentUser != null) {
                    emojiStatusDocumentId = DialogObject.getEmojiStatusDocumentId(currentUser.emoji_status);
                }
            }
            try {
                userName = Emoji.replaceEmoji(userName, null, false);
            } catch (Exception unused) {
            }
            this.titleView.setText(userName);
            this.botVerificationEmoji.set(botVerificationIcon, false);
            this.titleView.setLeftDrawable(this.botVerificationEmoji);
            this.statusEmoji.set(emojiStatusDocumentId, false);
            this.titleView.setRightDrawable(this.statusEmoji);
            if (this.isChannel) {
                long j2 = -j;
                TLRPC.Chat chat2 = MessagesController.getInstance(i).getChat(Long.valueOf(j2));
                TLRPC.ChatFull chatFull = MessagesController.getInstance(i).getChatFull(j2);
                if (chatFull != null && chatFull.participants_count > 0) {
                    if (!ChatObject.isChannelAndNotMegaGroup(chat2)) {
                        this.subtitleView.setText(LocaleController.formatPluralStringComma("Members", chatFull.participants_count));
                    } else {
                        this.subtitleView.setText(LocaleController.formatPluralStringComma("Subscribers", chatFull.participants_count));
                    }
                } else if (chat2 != null && chat2.participants_count > 0) {
                    if (!ChatObject.isChannelAndNotMegaGroup(chat2)) {
                        this.subtitleView.setText(LocaleController.formatPluralStringComma("Members", chat2.participants_count));
                    } else {
                        this.subtitleView.setText(LocaleController.formatPluralStringComma("Subscribers", chat2.participants_count));
                    }
                } else {
                    boolean zIsPublic = ChatObject.isPublic(chat2);
                    if (ChatObject.isChannelAndNotMegaGroup(chat2)) {
                        this.subtitleView.setText(LocaleController.getString(zIsPublic ? C2369R.string.ChannelPublic : C2369R.string.ChannelPrivate).toLowerCase());
                    } else {
                        this.subtitleView.setText(LocaleController.getString(zIsPublic ? C2369R.string.MegaPublic : C2369R.string.MegaPrivate).toLowerCase());
                    }
                }
            } else {
                this.subtitleView.setText(LocaleController.getString(C2369R.string.Online));
            }
            setWillNotDraw(false);
        }

        public void overrideAvatarColor(int i) {
            int themedColor;
            int themedColor2;
            if (i >= 14) {
                MessagesController messagesController = MessagesController.getInstance(UserConfig.selectedAccount);
                MessagesController.PeerColors peerColors = messagesController != null ? messagesController.peerColors : null;
                MessagesController.PeerColor color = peerColors != null ? peerColors.getColor(i) : null;
                if (color != null) {
                    int color1 = color.getColor1();
                    themedColor = getThemedColor(Theme.keys_avatar_background[AvatarDrawable.getPeerColorIndex(color1)]);
                    themedColor2 = getThemedColor(Theme.keys_avatar_background2[AvatarDrawable.getPeerColorIndex(color1)]);
                } else {
                    long j = i;
                    themedColor = getThemedColor(Theme.keys_avatar_background[AvatarDrawable.getColorIndex(j)]);
                    themedColor2 = getThemedColor(Theme.keys_avatar_background2[AvatarDrawable.getColorIndex(j)]);
                }
            } else {
                long j2 = i;
                themedColor = getThemedColor(Theme.keys_avatar_background[AvatarDrawable.getColorIndex(j2)]);
                themedColor2 = getThemedColor(Theme.keys_avatar_background2[AvatarDrawable.getColorIndex(j2)]);
            }
            this.avatarDrawable.setColor(themedColor, themedColor2);
            invalidate();
        }

        public void setForum(boolean z) {
            if (this.isForum != z) {
                invalidate();
            }
            this.isForum = z;
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onAttachedToWindow() {
            super.onAttachedToWindow();
            this.emoji.attach();
            this.imageReceiver.onAttachedToWindow();
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.emoji.detach();
            this.imageReceiver.onDetachedFromWindow();
        }

        private int getThemedColor(int i) {
            return Theme.getColor(i, this.resourcesProvider);
        }

        public void setColor(int i, boolean z) {
            MessagesController.PeerColors peerColors = MessagesController.getInstance(this.currentAccount).profilePeerColors;
            setColor(peerColors == null ? null : peerColors.getColor(i), z);
        }

        public void setColor(MessagesController.PeerColor peerColor, boolean z) {
            this.peerColor = peerColor;
            Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
            boolean zIsDark = resourcesProvider != null ? resourcesProvider.isDark() : Theme.isCurrentThemeDark();
            if (peerColor != null) {
                int i = peerColor.patternColor;
                if (i != 0) {
                    this.emoji.setColor(Integer.valueOf(i));
                } else {
                    this.emoji.setColor(Integer.valueOf(PeerColorActivity.adaptProfileEmojiColor(peerColor.getBgColor1(zIsDark))));
                }
                this.statusEmoji.setColor(Integer.valueOf(ColorUtils.blendARGB(peerColor.getStoryColor1(Theme.isCurrentThemeDark()), -1, 0.25f)));
                this.botVerificationEmoji.setColor(Integer.valueOf(ColorUtils.blendARGB(peerColor.getStoryColor1(Theme.isCurrentThemeDark()), -1, 0.25f)));
                int iBlendARGB = ColorUtils.blendARGB(peerColor.getStoryColor1(zIsDark), peerColor.getStoryColor2(zIsDark), 0.5f);
                int i2 = Theme.key_actionBarDefault;
                if (!Theme.hasHue(getThemedColor(i2))) {
                    this.subtitleView.setTextColor(iBlendARGB);
                } else {
                    this.subtitleView.setTextColor(Theme.changeColorAccent(getThemedColor(i2), iBlendARGB, getThemedColor(Theme.key_avatar_subtitleInProfileBlue), zIsDark, iBlendARGB));
                }
                this.titleView.setTextColor(-1);
            } else {
                int i3 = Theme.key_actionBarDefault;
                if (AndroidUtilities.computePerceivedBrightness(getThemedColor(i3)) > 0.8f) {
                    this.emoji.setColor(Integer.valueOf(getThemedColor(Theme.key_windowBackgroundWhiteBlueText)));
                } else if (AndroidUtilities.computePerceivedBrightness(getThemedColor(i3)) < 0.2f) {
                    this.emoji.setColor(Integer.valueOf(Theme.multAlpha(getThemedColor(Theme.key_actionBarDefaultTitle), 0.5f)));
                } else {
                    this.emoji.setColor(Integer.valueOf(PeerColorActivity.adaptProfileEmojiColor(getThemedColor(i3))));
                }
                AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.statusEmoji;
                int i4 = Theme.key_profile_verifiedBackground;
                swapAnimatedEmojiDrawable.setColor(Integer.valueOf(Theme.getColor(i4, this.resourcesProvider)));
                this.botVerificationEmoji.setColor(Integer.valueOf(Theme.getColor(i4, this.resourcesProvider)));
                this.subtitleView.setTextColor(getThemedColor(Theme.key_actionBarDefaultSubtitle));
                this.titleView.setTextColor(getThemedColor(Theme.key_actionBarDefaultTitle));
            }
            this.storyGradient.setColor(peerColor, z);
            invalidate();
        }

        public void setEmoji(long j, boolean z, boolean z2) {
            MessagesController.PeerColor peerColor;
            int i;
            if (j == 0) {
                this.emoji.set((Drawable) null, z2);
            } else {
                this.emoji.set(j, z2);
            }
            Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
            boolean zIsDark = resourcesProvider != null ? resourcesProvider.isDark() : Theme.isCurrentThemeDark();
            MessagesController.PeerColor peerColor2 = this.peerColor;
            if (peerColor2 != null) {
                int i2 = peerColor2.patternColor;
                if (i2 != 0) {
                    this.emoji.setColor(Integer.valueOf(i2));
                } else {
                    this.emoji.setColor(Integer.valueOf(PeerColorActivity.adaptProfileEmojiColor(peerColor2.getBgColor1(zIsDark))));
                }
            } else {
                int i3 = Theme.key_actionBarDefault;
                if (AndroidUtilities.computePerceivedBrightness(getThemedColor(i3)) > 0.8f) {
                    this.emoji.setColor(Integer.valueOf(getThemedColor(Theme.key_windowBackgroundWhiteBlueText)));
                } else if (AndroidUtilities.computePerceivedBrightness(getThemedColor(i3)) < 0.2f) {
                    this.emoji.setColor(Integer.valueOf(Theme.multAlpha(Theme.getColor(Theme.key_actionBarDefaultTitle), 0.5f)));
                } else {
                    this.emoji.setColor(Integer.valueOf(PeerColorActivity.adaptProfileEmojiColor(Theme.getColor(i3))));
                }
            }
            MessagesController.PeerColor peerColor3 = this.peerColor;
            if (peerColor3 != null) {
                AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = this.statusEmoji;
                int color = peerColor3.getColor(1, this.resourcesProvider);
                if (this.peerColor.hasColor6(zIsDark)) {
                    peerColor = this.peerColor;
                    i = 4;
                } else {
                    peerColor = this.peerColor;
                    i = 2;
                }
                swapAnimatedEmojiDrawable.setColor(Integer.valueOf(ColorUtils.blendARGB(color, peerColor.getColor(i, this.resourcesProvider), 0.5f)));
            } else {
                this.statusEmoji.setColor(Integer.valueOf(Theme.getColor(Theme.key_profile_verifiedBackground, this.resourcesProvider)));
            }
            this.isEmojiCollectible = z;
            if (!z2) {
                this.emojiCollectible.force(z);
            }
            invalidate();
        }

        public void setStatusEmoji(long j, boolean z, boolean z2) {
            this.statusEmoji.set(j, z2);
            this.statusEmoji.setParticles(z, z2);
            Theme.ResourcesProvider resourcesProvider = this.resourcesProvider;
            boolean zIsDark = resourcesProvider != null ? resourcesProvider.isDark() : Theme.isCurrentThemeDark();
            MessagesController.PeerColor peerColor = this.peerColor;
            if (peerColor != null) {
                this.statusEmoji.setColor(Integer.valueOf(ColorUtils.blendARGB(peerColor.getColor2(zIsDark), this.peerColor.hasColor6(zIsDark) ? this.peerColor.getColor5(zIsDark) : this.peerColor.getColor3(zIsDark), 0.5f)));
            } else {
                this.statusEmoji.setColor(Integer.valueOf(Theme.getColor(Theme.key_profile_verifiedBackground, this.resourcesProvider)));
            }
        }

        @Override // android.view.ViewGroup, android.view.View
        protected void dispatchDraw(Canvas canvas) {
            this.rectF.set((getWidth() - AndroidUtilities.m1146dp(86.0f)) / 2.0f, getHeight() - AndroidUtilities.m1146dp(168.0f), (getWidth() + AndroidUtilities.m1146dp(86.0f)) / 2.0f, getHeight() - AndroidUtilities.m1146dp(82.0f));
            this.imageReceiver.setRoundRadius(AndroidUtilities.m1146dp(this.isForum ? 18.0f : 54.0f));
            this.imageReceiver.setImageCoords(this.rectF);
            this.imageReceiver.draw(canvas);
            float fWidth = (this.rectF.width() / 2.0f) + AndroidUtilities.m1146dp(4.0f);
            float fM1146dp = AndroidUtilities.m1146dp(this.isForum ? 22.0f : 58.0f);
            canvas.drawRoundRect(this.rectF.centerX() - fWidth, this.rectF.centerY() - fWidth, this.rectF.centerX() + fWidth, this.rectF.centerY() + fWidth, fM1146dp, fM1146dp, this.storyGradient.getPaint(this.rectF));
            StarGiftPatterns.drawProfileAnimatedPattern(canvas, this.emoji, getWidth(), getHeight(), 1.0f, this.rectF, 1.0f);
            super.dispatchDraw(canvas);
        }
    }

    public static int adaptProfileEmojiColor(int i) {
        return Theme.adaptHSV(i, 0.5f, (AndroidUtilities.computePerceivedBrightness(i) > 0.2f ? 1 : (AndroidUtilities.computePerceivedBrightness(i) == 0.2f ? 0 : -1)) < 0 ? 0.28f : -0.28f);
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
        View view = new View(getContext()) { // from class: org.telegram.ui.PeerColorActivity.7
            @Override // android.view.View
            protected void onDraw(Canvas canvas2) {
                super.onDraw(canvas2);
                if (PeerColorActivity.this.isDark) {
                    if (PeerColorActivity.this.changeDayNightViewProgress > 0.0f) {
                        canvas.drawCircle(measuredWidth, measuredHeight, fMax * PeerColorActivity.this.changeDayNightViewProgress, paint);
                    }
                    canvas2.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, paint2);
                } else {
                    canvas2.drawCircle(measuredWidth, measuredHeight, fMax * (1.0f - PeerColorActivity.this.changeDayNightViewProgress), paint2);
                }
                canvas2.save();
                canvas2.translate(f, f2);
                PeerColorActivity.this.dayNightItem.draw(canvas2);
                canvas2.restore();
            }
        };
        this.changeDayNightView = view;
        view.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda7
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return PeerColorActivity.$r8$lambda$jN3uxIhJrKqUC9ox4vQUHnPvrw0(view2, motionEvent);
            }
        });
        this.changeDayNightViewProgress = 0.0f;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.changeDayNightViewAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.PeerColorActivity.8
            boolean changedNavigationBarColor = false;

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PeerColorActivity.this.changeDayNightViewProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                PeerColorActivity.this.changeDayNightView.invalidate();
                if (this.changedNavigationBarColor || PeerColorActivity.this.changeDayNightViewProgress <= 0.5f) {
                    return;
                }
                this.changedNavigationBarColor = true;
            }
        });
        this.changeDayNightViewAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.PeerColorActivity.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (PeerColorActivity.this.changeDayNightView != null) {
                    if (PeerColorActivity.this.changeDayNightView.getParent() != null) {
                        ((ViewGroup) PeerColorActivity.this.changeDayNightView.getParent()).removeView(PeerColorActivity.this.changeDayNightView);
                    }
                    PeerColorActivity.this.changeDayNightView = null;
                }
                PeerColorActivity.this.changeDayNightViewAnimator = null;
                super.onAnimationEnd(animator);
            }
        });
        this.changeDayNightViewAnimator.setDuration(400L);
        this.changeDayNightViewAnimator.setInterpolator(Easings.easeInOutQuad);
        this.changeDayNightViewAnimator.start();
        frameLayout.addView(this.changeDayNightView, new ViewGroup.LayoutParams(-1, -1));
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.PeerColorActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() throws IOException {
                this.f$0.lambda$toggleTheme$11();
            }
        });
    }

    public static /* synthetic */ boolean $r8$lambda$jN3uxIhJrKqUC9ox4vQUHnPvrw0(View view, MotionEvent motionEvent) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$toggleTheme$11() throws IOException {
        this.isDark = !this.isDark;
        updateThemeColors();
        setForceDark(this.isDark, true);
        updateColors();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean isLightStatusBar() {
        ColoredActionBar coloredActionBar = this.colorBar;
        if (coloredActionBar == null) {
            return super.isLightStatusBar();
        }
        return ColorUtils.calculateLuminance(coloredActionBar.getColor()) > 0.699999988079071d;
    }

    public void updateLightStatusBar() {
        if (getParentActivity() == null) {
            return;
        }
        AndroidUtilities.setLightStatusBar(getParentActivity().getWindow(), isLightStatusBar());
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
        ImageView imageView = this.dayNightItem;
        if (imageView != null) {
            imageView.invalidate();
        }
    }

    public static class GiftCell extends FrameLayout {
        public TL_stars.starGiftAttributeBackdrop backdrop;
        public final FrameLayout card;
        public final GiftSheet.CardBackground cardBackground;

        /* renamed from: id */
        public long f2038id;
        public final BackupImageView imageView;
        private TLRPC.Document lastDocument;
        private long lastDocumentId;
        public TL_stars.starGiftAttributePattern pattern;
        private final GiftSheet.Ribbon ribbon;

        public GiftCell(Context context, boolean z, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            FrameLayout frameLayout = new FrameLayout(context);
            this.card = frameLayout;
            GiftSheet.CardBackground cardBackground = new GiftSheet.CardBackground(frameLayout, resourcesProvider, false);
            this.cardBackground = cardBackground;
            frameLayout.setBackground(cardBackground);
            addView(frameLayout, LayoutHelper.createFrame(-1, -1, Opcodes.DNEG));
            ScaleStateListAnimator.apply(frameLayout, 0.025f, 1.25f);
            BackupImageView backupImageView = new BackupImageView(context);
            this.imageView = backupImageView;
            frameLayout.addView(backupImageView, LayoutHelper.createFrame(80, 80.0f, 17, 0.0f, 12.0f, 0.0f, 12.0f));
            if (z) {
                GiftSheet.Ribbon ribbon = new GiftSheet.Ribbon(context);
                this.ribbon = ribbon;
                addView(ribbon, LayoutHelper.createFrame(-2, -2.0f, 53, 0.0f, 2.0f, 1.0f, 0.0f));
                return;
            }
            this.ribbon = null;
        }

        public void set(int i, TL_stars.TL_starGiftUnique tL_starGiftUnique) {
            this.f2038id = tL_starGiftUnique.f1755id;
            boolean z = i % 3 == 1;
            setPadding(z ? AndroidUtilities.m1146dp(4.0f) : 0, 0, z ? AndroidUtilities.m1146dp(4.0f) : 0, 0);
            setSticker(tL_starGiftUnique.getDocument(), tL_starGiftUnique);
            this.backdrop = (TL_stars.starGiftAttributeBackdrop) StarsController.findAttribute(tL_starGiftUnique.attributes, TL_stars.starGiftAttributeBackdrop.class);
            this.pattern = (TL_stars.starGiftAttributePattern) StarsController.findAttribute(tL_starGiftUnique.attributes, TL_stars.starGiftAttributePattern.class);
            this.cardBackground.setBackdrop(this.backdrop);
            this.cardBackground.setPattern(this.pattern);
        }

        public void set(int i, TL_stars.SavedStarGift savedStarGift) {
            this.f2038id = savedStarGift.gift.f1755id;
            boolean z = i % 3 == 1;
            setPadding(z ? AndroidUtilities.m1146dp(4.0f) : 0, 0, z ? AndroidUtilities.m1146dp(4.0f) : 0, 0);
            setSticker(savedStarGift.gift.getDocument(), savedStarGift.gift);
            this.backdrop = (TL_stars.starGiftAttributeBackdrop) StarsController.findAttribute(savedStarGift.gift.attributes, TL_stars.starGiftAttributeBackdrop.class);
            this.pattern = (TL_stars.starGiftAttributePattern) StarsController.findAttribute(savedStarGift.gift.attributes, TL_stars.starGiftAttributePattern.class);
            this.cardBackground.setBackdrop(this.backdrop);
            this.cardBackground.setPattern(this.pattern);
            GiftSheet.Ribbon ribbon = this.ribbon;
            if (ribbon != null) {
                ribbon.setBackdrop(this.backdrop);
                this.ribbon.setText(9, "#" + LocaleController.formatNumber(savedStarGift.gift.num, ','), false);
            }
        }

        public long getGiftId() {
            return this.f2038id;
        }

        public void setSelected(boolean z, boolean z2) {
            this.cardBackground.setSelected(z, z2);
            float f = z ? 0.9f : 1.0f;
            if (z2) {
                this.imageView.animate().scaleX(f).scaleY(f).start();
                return;
            }
            this.imageView.animate().cancel();
            this.imageView.setScaleX(f);
            this.imageView.setScaleY(f);
        }

        private void setSticker(TLRPC.Document document, Object obj) {
            if (document == null) {
                this.imageView.clearImage();
                this.lastDocument = null;
                this.lastDocumentId = 0L;
            } else {
                if (this.lastDocument == document) {
                    return;
                }
                this.lastDocument = document;
                this.lastDocumentId = document.f1579id;
                TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, AndroidUtilities.m1146dp(100.0f));
                this.imageView.setImage(ImageLocation.getForDocument(document), "100_100", ImageLocation.getForDocument(closestPhotoSizeWithSize, document), "100_100", DocumentObject.getSvgThumb(document, Theme.key_windowBackgroundGray, 0.3f), obj);
            }
        }

        public static class Factory extends UItem.UItemFactory {
            static {
                UItem.UItemFactory.setup(new Factory());
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public GiftCell createView(Context context, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
                return new GiftCell(context, true, resourcesProvider);
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
                GiftCell giftCell = (GiftCell) view;
                giftCell.set(-1, (TL_stars.SavedStarGift) uItem.object);
                giftCell.setSelected(uItem.checked, false);
            }

            public static UItem asGiftCell(TL_stars.SavedStarGift savedStarGift) {
                UItem uItemOfFactory = UItem.ofFactory(Factory.class);
                uItemOfFactory.object = savedStarGift;
                return uItemOfFactory;
            }
        }
    }

    /* renamed from: eq */
    public static boolean m1312eq(TLRPC.EmojiStatus emojiStatus, TLRPC.TL_emojiStatusCollectible tL_emojiStatusCollectible) {
        boolean z = emojiStatus instanceof TLRPC.TL_emojiStatusCollectible;
        return (tL_emojiStatusCollectible != null) == z && tL_emojiStatusCollectible != null && z && ((TLRPC.TL_emojiStatusCollectible) emojiStatus).collectible_id == tL_emojiStatusCollectible.collectible_id;
    }

    /* renamed from: eq */
    public static boolean m1313eq(TLRPC.TL_peerColorCollectible tL_peerColorCollectible, TLRPC.TL_peerColorCollectible tL_peerColorCollectible2) {
        if (tL_peerColorCollectible == tL_peerColorCollectible2) {
            return true;
        }
        if (tL_peerColorCollectible == null && tL_peerColorCollectible2 == null) {
            return true;
        }
        return (tL_peerColorCollectible == null || tL_peerColorCollectible2 == null || tL_peerColorCollectible.collectible_id != tL_peerColorCollectible2.collectible_id) ? false : true;
    }
}
