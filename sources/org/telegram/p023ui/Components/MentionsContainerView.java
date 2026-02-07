package org.telegram.p023ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.SpannableString;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatValueHolder;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.google.android.exoplayer2.util.Consumer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Adapters.MentionsAdapter;
import org.telegram.p023ui.Adapters.PaddedListAdapter;
import org.telegram.p023ui.Business.QuickRepliesActivity;
import org.telegram.p023ui.Cells.MentionCell;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.blur3.drawable.BlurredBackgroundDrawable;
import org.telegram.p023ui.ContentPreviewViewer;
import org.telegram.p023ui.PhotoViewer;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.webrtc.MediaStreamTrack;

/* loaded from: classes3.dex */
public abstract class MentionsContainerView extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {
    private MentionsAdapter adapter;
    private int animationIndex;
    private BlurredBackgroundDrawable backgroundDrawable;
    WeakReference baseFragment;
    private PhotoViewer.PhotoViewerProvider botContextProvider;
    private ArrayList botContextResults;
    private final RectF clipBounds;
    private final Path clipPath;
    private Integer color;
    private float containerBottom;
    private float containerPadding;
    private float containerTop;
    private WeakReference delegate;
    private ExtendedGridLayoutManager gridLayoutManager;
    private float hideT;
    private boolean ignoreLayout;
    private LinearLayoutManager linearLayoutManager;
    private MentionsListView listView;
    private boolean listViewHiding;
    private float listViewPadding;
    private SpringAnimation listViewTranslationAnimator;
    private RecyclerListView.OnItemClickListener mentionsOnItemClickListener;
    private PaddedListAdapter paddedAdapter;
    private Paint paint;
    private Rect rect;
    private final Theme.ResourcesProvider resourcesProvider;
    private int scrollRangeUpdateTries;
    private boolean scrollToFirst;
    private boolean shouldLiftMentions;
    private boolean shown;
    private boolean switchLayoutManagerOnEnd;
    private Runnable updateVisibilityRunnable;

    public static /* synthetic */ void $r8$lambda$dd_L2UtPYzHyIxI7hKB0lrroym8(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
    }

    protected boolean canOpen() {
        return true;
    }

    protected boolean isStories() {
        return false;
    }

    protected void onAnimationScroll() {
    }

    protected void onClose() {
    }

    protected void onContextClick(TLRPC.BotInlineResult botInlineResult) {
    }

    protected void onContextSearch(boolean z) {
    }

    protected void onOpen() {
    }

    protected void onScrolled(boolean z, boolean z2) {
    }

    public MentionsContainerView(Context context, long j, long j2, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.shouldLiftMentions = false;
        this.rect = new Rect();
        this.ignoreLayout = false;
        this.scrollToFirst = false;
        this.shown = false;
        this.updateVisibilityRunnable = new Runnable() { // from class: org.telegram.ui.Components.MentionsContainerView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        };
        this.animationIndex = -1;
        this.listViewHiding = false;
        this.hideT = 0.0f;
        this.switchLayoutManagerOnEnd = false;
        this.botContextProvider = new PhotoViewer.EmptyPhotoViewerProvider() { // from class: org.telegram.ui.Components.MentionsContainerView.5
            /* JADX WARN: Removed duplicated region for block: B:14:0x0044  */
            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct add '--show-bad-code' argument
            */
            public org.telegram.ui.PhotoViewer.PlaceProviderObject getPlaceForPhoto(org.telegram.messenger.MessageObject r4, org.telegram.tgnet.TLRPC.FileLocation r5, int r6, boolean r7, boolean r8) {
                /*
                    r3 = this;
                    r4 = 0
                    if (r6 < 0) goto L75
                    org.telegram.ui.Components.MentionsContainerView r5 = org.telegram.p023ui.Components.MentionsContainerView.this
                    java.util.ArrayList r5 = org.telegram.p023ui.Components.MentionsContainerView.m9934$$Nest$fgetbotContextResults(r5)
                    int r5 = r5.size()
                    if (r6 < r5) goto L10
                    goto L75
                L10:
                    org.telegram.ui.Components.MentionsContainerView r5 = org.telegram.p023ui.Components.MentionsContainerView.this
                    org.telegram.ui.Components.MentionsContainerView$MentionsListView r5 = r5.getListView()
                    int r5 = r5.getChildCount()
                    org.telegram.ui.Components.MentionsContainerView r7 = org.telegram.p023ui.Components.MentionsContainerView.this
                    java.util.ArrayList r7 = org.telegram.p023ui.Components.MentionsContainerView.m9934$$Nest$fgetbotContextResults(r7)
                    java.lang.Object r6 = r7.get(r6)
                    r7 = 0
                    r8 = 0
                L26:
                    if (r8 >= r5) goto L75
                    org.telegram.ui.Components.MentionsContainerView r0 = org.telegram.p023ui.Components.MentionsContainerView.this
                    org.telegram.ui.Components.MentionsContainerView$MentionsListView r0 = r0.getListView()
                    android.view.View r0 = r0.getChildAt(r8)
                    boolean r1 = r0 instanceof org.telegram.p023ui.Cells.ContextLinkCell
                    if (r1 == 0) goto L44
                    r1 = r0
                    org.telegram.ui.Cells.ContextLinkCell r1 = (org.telegram.p023ui.Cells.ContextLinkCell) r1
                    org.telegram.tgnet.TLRPC$BotInlineResult r2 = r1.getResult()
                    if (r2 != r6) goto L44
                    org.telegram.messenger.ImageReceiver r1 = r1.getPhotoImage()
                    goto L45
                L44:
                    r1 = r4
                L45:
                    if (r1 == 0) goto L72
                    r4 = 2
                    int[] r4 = new int[r4]
                    r0.getLocationInWindow(r4)
                    org.telegram.ui.PhotoViewer$PlaceProviderObject r5 = new org.telegram.ui.PhotoViewer$PlaceProviderObject
                    r5.<init>()
                    r6 = r4[r7]
                    r5.viewX = r6
                    r6 = 1
                    r4 = r4[r6]
                    r5.viewY = r4
                    org.telegram.ui.Components.MentionsContainerView r4 = org.telegram.p023ui.Components.MentionsContainerView.this
                    org.telegram.ui.Components.MentionsContainerView$MentionsListView r4 = r4.getListView()
                    r5.parentView = r4
                    r5.imageReceiver = r1
                    org.telegram.messenger.ImageReceiver$BitmapHolder r4 = r1.getBitmapSafe()
                    r5.thumb = r4
                    int[] r4 = r1.getRoundRadius(r6)
                    r5.radius = r4
                    return r5
                L72:
                    int r8 = r8 + 1
                    goto L26
                L75:
                    return r4
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.MentionsContainerView.C40185.getPlaceForPhoto(org.telegram.messenger.MessageObject, org.telegram.tgnet.TLRPC$FileLocation, int, boolean, boolean):org.telegram.ui.PhotoViewer$PlaceProviderObject");
            }

            @Override // org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider, org.telegram.ui.PhotoViewer.PhotoViewerProvider
            public void sendButtonPressed(int i, VideoEditedInfo videoEditedInfo, boolean z, int i2, int i3, boolean z2) {
                if (i < 0 || i >= MentionsContainerView.this.botContextResults.size() || MentionsContainerView.this.delegate == null || MentionsContainerView.this.delegate.get() == null) {
                    return;
                }
                ((Delegate) MentionsContainerView.this.delegate.get()).sendBotInlineResult((TLRPC.BotInlineResult) MentionsContainerView.this.botContextResults.get(i), z, i2);
            }
        };
        this.clipPath = new Path();
        this.clipBounds = new RectF();
        this.baseFragment = new WeakReference(baseFragment);
        this.resourcesProvider = resourcesProvider;
        setVisibility(8);
        setWillNotDraw(false);
        setClipToOutline(true);
        this.listViewPadding = (int) Math.min(AndroidUtilities.m1146dp(126.0f), AndroidUtilities.displaySize.y * 0.22f);
        this.listView = new MentionsListView(context, resourcesProvider);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context) { // from class: org.telegram.ui.Components.MentionsContainerView.1
            @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }

            @Override // androidx.recyclerview.widget.LinearLayoutManager
            public void setReverseLayout(boolean z) {
                super.setReverseLayout(z);
                MentionsContainerView.this.listView.setTranslationY((z ? -1 : 1) * AndroidUtilities.m1146dp(6.0f));
            }
        };
        this.linearLayoutManager = linearLayoutManager;
        linearLayoutManager.setOrientation(1);
        ExtendedGridLayoutManager extendedGridLayoutManager = new ExtendedGridLayoutManager(context, 100, false, false) { // from class: org.telegram.ui.Components.MentionsContainerView.2
            private Size size = new Size();

            @Override // org.telegram.p023ui.Components.ExtendedGridLayoutManager
            protected Size getSizeForItem(int i) {
                TLRPC.PhotoSize closestPhotoSizeWithSize;
                Size size = this.size;
                int i2 = 0;
                size.full = false;
                if (i == 0) {
                    size.width = getWidth();
                    this.size.height = MentionsContainerView.this.paddedAdapter.getPadding();
                    Size size2 = this.size;
                    size2.full = true;
                    return size2;
                }
                int i3 = i - 1;
                if (MentionsContainerView.this.adapter.getBotContextSwitch() == null && MentionsContainerView.this.adapter.getBotWebViewSwitch() == null) {
                    i = i3;
                }
                Size size3 = this.size;
                size3.width = 0.0f;
                size3.height = 0.0f;
                Object item = MentionsContainerView.this.adapter.getItem(i);
                if (item instanceof TLRPC.BotInlineResult) {
                    TLRPC.BotInlineResult botInlineResult = (TLRPC.BotInlineResult) item;
                    TLRPC.Document document = botInlineResult.document;
                    if (document != null) {
                        TLRPC.PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, 90);
                        Size size4 = this.size;
                        size4.width = closestPhotoSizeWithSize2 != null ? closestPhotoSizeWithSize2.f1605w : 100.0f;
                        size4.height = closestPhotoSizeWithSize2 != null ? closestPhotoSizeWithSize2.f1604h : 100.0f;
                        while (i2 < botInlineResult.document.attributes.size()) {
                            TLRPC.DocumentAttribute documentAttribute = botInlineResult.document.attributes.get(i2);
                            if ((documentAttribute instanceof TLRPC.TL_documentAttributeImageSize) || (documentAttribute instanceof TLRPC.TL_documentAttributeVideo)) {
                                Size size5 = this.size;
                                size5.width = documentAttribute.f1582w;
                                size5.height = documentAttribute.f1581h;
                                break;
                            }
                            i2++;
                        }
                    } else if (botInlineResult.content != null) {
                        while (i2 < botInlineResult.content.attributes.size()) {
                            TLRPC.DocumentAttribute documentAttribute2 = (TLRPC.DocumentAttribute) botInlineResult.content.attributes.get(i2);
                            if ((documentAttribute2 instanceof TLRPC.TL_documentAttributeImageSize) || (documentAttribute2 instanceof TLRPC.TL_documentAttributeVideo)) {
                                Size size6 = this.size;
                                size6.width = documentAttribute2.f1582w;
                                size6.height = documentAttribute2.f1581h;
                                break;
                            }
                            i2++;
                        }
                    } else if (botInlineResult.thumb != null) {
                        while (i2 < botInlineResult.thumb.attributes.size()) {
                            TLRPC.DocumentAttribute documentAttribute3 = (TLRPC.DocumentAttribute) botInlineResult.thumb.attributes.get(i2);
                            if ((documentAttribute3 instanceof TLRPC.TL_documentAttributeImageSize) || (documentAttribute3 instanceof TLRPC.TL_documentAttributeVideo)) {
                                Size size7 = this.size;
                                size7.width = documentAttribute3.f1582w;
                                size7.height = documentAttribute3.f1581h;
                                break;
                            }
                            i2++;
                        }
                    } else {
                        TLRPC.Photo photo = botInlineResult.photo;
                        if (photo != null && (closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(photo.sizes, AndroidUtilities.getPhotoSize())) != null) {
                            Size size8 = this.size;
                            size8.width = closestPhotoSizeWithSize.f1605w;
                            size8.height = closestPhotoSizeWithSize.f1604h;
                        }
                    }
                }
                return this.size;
            }

            @Override // org.telegram.p023ui.Components.ExtendedGridLayoutManager
            protected int getFlowItemCount() {
                if (MentionsContainerView.this.adapter.getBotContextSwitch() != null || MentionsContainerView.this.adapter.getBotWebViewSwitch() != null) {
                    return getItemCount() - 1;
                }
                return super.getFlowItemCount();
            }
        };
        this.gridLayoutManager = extendedGridLayoutManager;
        extendedGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() { // from class: org.telegram.ui.Components.MentionsContainerView.3
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public int getSpanSize(int i) {
                if (i == 0) {
                    return 100;
                }
                Object item = MentionsContainerView.this.adapter.getItem(i - 1);
                if (item instanceof TLRPC.TL_inlineBotSwitchPM) {
                    return 100;
                }
                if (item instanceof TLRPC.Document) {
                    return 20;
                }
                if (MentionsContainerView.this.adapter.getBotContextSwitch() != null || MentionsContainerView.this.adapter.getBotWebViewSwitch() != null) {
                    i--;
                }
                return MentionsContainerView.this.gridLayoutManager.getSpanSizeForItem(i);
            }
        });
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(150L);
        defaultItemAnimator.setMoveDuration(150L);
        defaultItemAnimator.setChangeDuration(150L);
        defaultItemAnimator.setRemoveDuration(150L);
        defaultItemAnimator.setTranslationInterpolator(CubicBezierInterpolator.DEFAULT);
        defaultItemAnimator.setDelayAnimations(false);
        this.listView.setItemAnimator(defaultItemAnimator);
        this.listView.setClipToPadding(false);
        this.listView.setLayoutManager(this.linearLayoutManager);
        MentionsAdapter mentionsAdapter = new MentionsAdapter(context, false, j, j2, new MentionsAdapter.MentionsAdapterDelegate() { // from class: org.telegram.ui.Components.MentionsContainerView.4
            @Override // org.telegram.ui.Adapters.MentionsAdapter.MentionsAdapterDelegate
            public void onItemCountUpdate(int i, int i2) {
                if (MentionsContainerView.this.listView.getLayoutManager() == MentionsContainerView.this.gridLayoutManager || !MentionsContainerView.this.shown) {
                    return;
                }
                AndroidUtilities.cancelRunOnUIThread(MentionsContainerView.this.updateVisibilityRunnable);
                Runnable runnable = MentionsContainerView.this.updateVisibilityRunnable;
                WeakReference weakReference = MentionsContainerView.this.baseFragment;
                AndroidUtilities.runOnUIThread(runnable, (weakReference == null || weakReference.get() == null || !((BaseFragment) MentionsContainerView.this.baseFragment.get()).getFragmentBeginToShow()) ? 100L : 0L);
            }

            @Override // org.telegram.ui.Adapters.MentionsAdapter.MentionsAdapterDelegate
            public void needChangePanelVisibility(boolean z) {
                if (MentionsContainerView.this.getNeededLayoutManager() != MentionsContainerView.this.getCurrentLayoutManager() && MentionsContainerView.this.canOpen()) {
                    if (MentionsContainerView.this.adapter.getLastItemCount() > 0) {
                        MentionsContainerView.this.switchLayoutManagerOnEnd = true;
                        MentionsContainerView.this.updateVisibility(false);
                        return;
                    }
                    MentionsContainerView.this.listView.setLayoutManager(MentionsContainerView.this.getNeededLayoutManager());
                }
                if (z && !MentionsContainerView.this.canOpen()) {
                    z = false;
                }
                MentionsContainerView.this.updateVisibility((!z || MentionsContainerView.this.adapter.getItemCountInternal() > 0) ? z : false);
            }

            @Override // org.telegram.ui.Adapters.MentionsAdapter.MentionsAdapterDelegate
            public void onContextSearch(boolean z) {
                MentionsContainerView.this.onContextSearch(z);
            }

            @Override // org.telegram.ui.Adapters.MentionsAdapter.MentionsAdapterDelegate
            public void onContextClick(TLRPC.BotInlineResult botInlineResult) {
                MentionsContainerView.this.onContextClick(botInlineResult);
            }
        }, resourcesProvider, isStories());
        this.adapter = mentionsAdapter;
        PaddedListAdapter paddedListAdapter = new PaddedListAdapter(mentionsAdapter);
        this.paddedAdapter = paddedListAdapter;
        this.listView.setAdapter(paddedListAdapter);
        this.listView.setTranslationY(AndroidUtilities.m1146dp(6.0f));
        addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        setReversed(false);
    }

    public MentionsListView getListView() {
        return this.listView;
    }

    public MentionsAdapter getAdapter() {
        return this.adapter;
    }

    public void setReversed(boolean z) {
        if (z != isReversed()) {
            this.scrollToFirst = true;
            this.linearLayoutManager.setReverseLayout(z);
            this.adapter.setIsReversed(z);
        }
    }

    public boolean isReversed() {
        RecyclerView.LayoutManager layoutManager = this.listView.getLayoutManager();
        LinearLayoutManager linearLayoutManager = this.linearLayoutManager;
        return layoutManager == linearLayoutManager && linearLayoutManager.getReverseLayout();
    }

    public LinearLayoutManager getCurrentLayoutManager() {
        RecyclerView.LayoutManager layoutManager = this.listView.getLayoutManager();
        LinearLayoutManager linearLayoutManager = this.linearLayoutManager;
        return layoutManager == linearLayoutManager ? linearLayoutManager : this.gridLayoutManager;
    }

    public LinearLayoutManager getNeededLayoutManager() {
        return ((this.adapter.isStickers() || this.adapter.isBotContext()) && this.adapter.isMediaLayout()) ? this.gridLayoutManager : this.linearLayoutManager;
    }

    public float clipBottom() {
        if (getVisibility() == 0 && !isReversed()) {
            return getMeasuredHeight() - this.containerTop;
        }
        return 0.0f;
    }

    public float clipTop() {
        if (getVisibility() == 0 && isReversed()) {
            return this.containerBottom;
        }
        return 0.0f;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        float fMin;
        BlurredBackgroundDrawable blurredBackgroundDrawable = this.backgroundDrawable;
        if (blurredBackgroundDrawable != null) {
            blurredBackgroundDrawable.draw(canvas);
            canvas.save();
            canvas.clipPath(this.clipPath);
            super.dispatchDraw(canvas);
            canvas.restore();
            return;
        }
        boolean zIsReversed = isReversed();
        this.containerPadding = AndroidUtilities.m1146dp(((this.adapter.isStickers() || this.adapter.isBotContext()) && this.adapter.isMediaLayout() && this.adapter.getBotContextSwitch() == null && this.adapter.getBotWebViewSwitch() == null ? 2 : 0) + 2);
        canvas.save();
        float fM1146dp = AndroidUtilities.m1146dp(6.0f);
        float f = this.containerTop;
        if (zIsReversed) {
            float fMin2 = Math.min(Math.max(0.0f, (this.paddedAdapter.paddingViewAttached ? r0.paddingView.getTop() : getHeight()) + this.listView.getTranslationY()) + this.containerPadding, (1.0f - this.hideT) * getHeight());
            Rect rect = this.rect;
            this.containerTop = 0.0f;
            int measuredWidth = getMeasuredWidth();
            this.containerBottom = fMin2;
            rect.set(0, (int) 0.0f, measuredWidth, (int) fMin2);
            fMin = Math.min(fM1146dp, Math.abs(getMeasuredHeight() - this.containerBottom));
            if (fMin > 0.0f) {
                canvas.clipRect(0, 0, getWidth(), getHeight());
                this.rect.top -= (int) fMin;
            }
        } else {
            if (this.listView.getLayoutManager() == this.gridLayoutManager) {
                this.containerPadding += AndroidUtilities.m1146dp(2.0f);
                fM1146dp += AndroidUtilities.m1146dp(2.0f);
            }
            float fMax = Math.max(0.0f, (this.paddedAdapter.paddingViewAttached ? r0.paddingView.getBottom() : 0) + this.listView.getTranslationY()) - this.containerPadding;
            this.containerTop = fMax;
            float fMax2 = Math.max(fMax, this.hideT * getHeight());
            Rect rect2 = this.rect;
            this.containerTop = fMax2;
            int measuredWidth2 = getMeasuredWidth();
            float measuredHeight = getMeasuredHeight();
            this.containerBottom = measuredHeight;
            rect2.set(0, (int) fMax2, measuredWidth2, (int) measuredHeight);
            fMin = Math.min(fM1146dp, Math.abs(this.containerTop));
            if (fMin > 0.0f) {
                canvas.clipRect(0, 0, getWidth(), getHeight());
                this.rect.bottom += (int) fMin;
            }
        }
        if (Math.abs(f - this.containerTop) > 0.1f) {
            onAnimationScroll();
        }
        if (this.paint == null) {
            Paint paint = new Paint(1);
            this.paint = paint;
            paint.setShadowLayer(AndroidUtilities.m1146dp(4.0f), 0.0f, 0.0f, 503316480);
        }
        Paint paint2 = this.paint;
        Integer num = this.color;
        paint2.setColor(num != null ? num.intValue() : getThemedColor(Theme.key_chat_messagePanelBackground));
        drawRoundRect(canvas, this.rect, fMin);
        canvas.clipRect(this.rect);
        super.dispatchDraw(canvas);
        canvas.restore();
    }

    public void drawRoundRect(Canvas canvas, Rect rect, float f) {
        RectF rectF = AndroidUtilities.rectTmp;
        rectF.set(rect);
        canvas.drawRoundRect(rectF, f, f, this.paint);
    }

    public void setOverrideColor(int i) {
        this.color = Integer.valueOf(i);
        invalidate();
    }

    public void setIgnoreLayout(boolean z) {
        this.ignoreLayout = z;
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.ignoreLayout) {
            return;
        }
        super.requestLayout();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        updateListViewTranslation(!this.shown, true);
    }

    public void updateVisibility(boolean z) {
        if (z) {
            boolean zIsReversed = isReversed();
            if (!this.shown) {
                this.scrollToFirst = true;
                RecyclerView.LayoutManager layoutManager = this.listView.getLayoutManager();
                LinearLayoutManager linearLayoutManager = this.linearLayoutManager;
                if (layoutManager == linearLayoutManager) {
                    linearLayoutManager.scrollToPositionWithOffset(0, zIsReversed ? -100000 : 100000);
                }
                if (getVisibility() == 8) {
                    this.hideT = 1.0f;
                    this.listView.setTranslationY(zIsReversed ? -(this.listViewPadding + AndroidUtilities.m1146dp(12.0f)) : r2.computeVerticalScrollOffset() + this.listViewPadding);
                }
            }
            setVisibility(0);
        } else {
            this.scrollToFirst = false;
        }
        this.shown = z;
        AndroidUtilities.cancelRunOnUIThread(this.updateVisibilityRunnable);
        SpringAnimation springAnimation = this.listViewTranslationAnimator;
        if (springAnimation != null) {
            springAnimation.cancel();
        }
        Runnable runnable = this.updateVisibilityRunnable;
        WeakReference weakReference = this.baseFragment;
        AndroidUtilities.runOnUIThread(runnable, (weakReference == null || weakReference.get() == null || !((BaseFragment) this.baseFragment.get()).getFragmentBeginToShow()) ? 100L : 0L);
        if (z) {
            onOpen();
        } else {
            onClose();
        }
    }

    public boolean isOpen() {
        return this.shown;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        checkListViewPadding();
        super.onMeasure(i, i2);
    }

    private void updateListViewTranslation(final boolean z, boolean z2) {
        float fM1146dp;
        int i;
        SpringAnimation springAnimation;
        if (this.listView == null || this.paddedAdapter == null) {
            this.scrollRangeUpdateTries = 0;
            return;
        }
        if (this.listViewHiding && (springAnimation = this.listViewTranslationAnimator) != null && springAnimation.isRunning() && z) {
            this.scrollRangeUpdateTries = 0;
            return;
        }
        boolean zIsReversed = isReversed();
        if (z) {
            fM1146dp = (-this.containerPadding) - AndroidUtilities.m1146dp(6.0f);
        } else {
            int iComputeVerticalScrollRange = this.listView.computeVerticalScrollRange();
            float padding = (iComputeVerticalScrollRange - this.paddedAdapter.getPadding()) + this.containerPadding;
            if (iComputeVerticalScrollRange <= 0 && this.adapter.getItemCountInternal() > 0 && (i = this.scrollRangeUpdateTries) < 3) {
                this.scrollRangeUpdateTries = i + 1;
                updateVisibility(true);
                return;
            }
            fM1146dp = padding;
        }
        this.scrollRangeUpdateTries = 0;
        float f = this.listViewPadding;
        float fMax = zIsReversed ? -Math.max(0.0f, f - fM1146dp) : Math.max(0.0f, f - fM1146dp) + (-f);
        if (z && !zIsReversed) {
            fMax += this.listView.computeVerticalScrollOffset();
        }
        final float f2 = fMax;
        SpringAnimation springAnimation2 = this.listViewTranslationAnimator;
        if (springAnimation2 != null) {
            springAnimation2.cancel();
        }
        Integer numValueOf = null;
        if (z2) {
            this.listViewHiding = z;
            final float translationY = this.listView.getTranslationY();
            final float f3 = this.hideT;
            final float f4 = z ? 1.0f : 0.0f;
            if (translationY == f2) {
                this.listViewTranslationAnimator = null;
                numValueOf = Integer.valueOf(z ? 8 : 0);
                if (this.switchLayoutManagerOnEnd && z) {
                    this.switchLayoutManagerOnEnd = false;
                    this.listView.setLayoutManager(getNeededLayoutManager());
                    this.shown = true;
                    updateVisibility(true);
                }
            } else {
                SpringAnimation spring = new SpringAnimation(new FloatValueHolder(translationY)).setSpring(new SpringForce(f2).setDampingRatio(1.0f).setStiffness(550.0f));
                this.listViewTranslationAnimator = spring;
                spring.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() { // from class: org.telegram.ui.Components.MentionsContainerView$$ExternalSyntheticLambda2
                    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationUpdateListener
                    public final void onAnimationUpdate(DynamicAnimation dynamicAnimation, float f5, float f6) {
                        this.f$0.lambda$updateListViewTranslation$1(f3, f4, translationY, f2, dynamicAnimation, f5, f6);
                    }
                });
                if (z) {
                    this.listViewTranslationAnimator.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: org.telegram.ui.Components.MentionsContainerView$$ExternalSyntheticLambda3
                        @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                        public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z3, float f5, float f6) {
                            this.f$0.lambda$updateListViewTranslation$2(z, dynamicAnimation, z3, f5, f6);
                        }
                    });
                }
                this.listViewTranslationAnimator.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: org.telegram.ui.Components.MentionsContainerView$$ExternalSyntheticLambda4
                    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
                    public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z3, float f5, float f6) {
                        MentionsContainerView.$r8$lambda$dd_L2UtPYzHyIxI7hKB0lrroym8(dynamicAnimation, z3, f5, f6);
                    }
                });
                this.listViewTranslationAnimator.start();
            }
        } else {
            this.hideT = z ? 1.0f : 0.0f;
            this.listView.setTranslationY(f2);
            if (z) {
                numValueOf = 8;
            }
        }
        if (numValueOf == null || getVisibility() == numValueOf.intValue()) {
            return;
        }
        setVisibility(numValueOf.intValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateListViewTranslation$1(float f, float f2, float f3, float f4, DynamicAnimation dynamicAnimation, float f5, float f6) {
        this.listView.setTranslationY(f5);
        onAnimationScroll();
        this.hideT = AndroidUtilities.lerp(f, f2, (f5 - f3) / (f4 - f3));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateListViewTranslation$2(boolean z, DynamicAnimation dynamicAnimation, boolean z2, float f, float f2) {
        if (z2) {
            return;
        }
        this.listViewTranslationAnimator = null;
        setVisibility(z ? 8 : 0);
        if (this.switchLayoutManagerOnEnd && z) {
            this.switchLayoutManagerOnEnd = false;
            this.listView.setLayoutManager(getNeededLayoutManager());
            this.shown = true;
            updateVisibility(true);
        }
    }

    public void setDialogId(long j) {
        this.adapter.setDialogId(j);
    }

    public void withDelegate(final Delegate delegate) {
        this.delegate = new WeakReference(delegate);
        MentionsListView listView = getListView();
        RecyclerListView.OnItemClickListener onItemClickListener = new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.MentionsContainerView$$ExternalSyntheticLambda5
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i) throws NumberFormatException {
                this.f$0.lambda$withDelegate$4(delegate, view, i);
            }
        };
        this.mentionsOnItemClickListener = onItemClickListener;
        listView.setOnItemClickListener(onItemClickListener);
        getListView().setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.MentionsContainerView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.lambda$withDelegate$5(view, motionEvent);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$withDelegate$4(Delegate delegate, View view, int i) throws NumberFormatException {
        WeakReference weakReference;
        Paint.FontMetricsInt fontMetrics;
        AnimatedEmojiSpan animatedEmojiSpan;
        if (i == 0 || getAdapter().isBannedInline() || (weakReference = this.delegate) == null || weakReference.get() == null) {
            return;
        }
        int i2 = i - 1;
        Object item = getAdapter().getItem(i2);
        int resultStartPosition = getAdapter().getResultStartPosition();
        int resultLength = getAdapter().getResultLength();
        String str = "";
        if (getAdapter().isLocalHashtagHint(i2)) {
            TLRPC.Chat currentChat = getAdapter().chat;
            if (currentChat == null && getAdapter().parentFragment != null) {
                currentChat = getAdapter().parentFragment.getCurrentChat();
            }
            StringBuilder sb = new StringBuilder();
            sb.append(getAdapter().getHashtagHint());
            if (currentChat != null) {
                str = "@" + ChatObject.getPublicUsername(currentChat);
            }
            sb.append(str);
            sb.append(" ");
            delegate.replaceText(resultStartPosition, resultLength, sb.toString(), false);
            return;
        }
        if (getAdapter().isGlobalHashtagHint(i2)) {
            delegate.replaceText(resultStartPosition, resultLength, getAdapter().getHashtagHint() + " ", false);
            return;
        }
        if (item instanceof TLRPC.TL_document) {
            TLRPC.TL_document tL_document = (TLRPC.TL_document) item;
            ((Delegate) this.delegate.get()).onStickerSelected(tL_document, MessageObject.findAnimatedEmojiEmoticon(tL_document), getAdapter().getItemParent(i2));
        } else if (item instanceof TLRPC.Chat) {
            String publicUsername = ChatObject.getPublicUsername((TLRPC.Chat) item);
            if (publicUsername != null) {
                Delegate delegate2 = (Delegate) this.delegate.get();
                StringBuilder sb2 = new StringBuilder();
                sb2.append("@");
                sb2.append(publicUsername);
                sb2.append(ExteraConfig.addCommaAfterMention ? ", " : " ");
                delegate2.replaceText(resultStartPosition, resultLength, sb2.toString(), false);
            }
        } else if (item instanceof TLRPC.User) {
            TLRPC.User user = (TLRPC.User) item;
            if (UserObject.getPublicUsername(user) != null) {
                Delegate delegate3 = (Delegate) this.delegate.get();
                StringBuilder sb3 = new StringBuilder();
                sb3.append("@");
                sb3.append(UserObject.getPublicUsername(user));
                sb3.append(ExteraConfig.addCommaAfterMention ? ", " : " ");
                delegate3.replaceText(resultStartPosition, resultLength, sb3.toString(), false);
            } else {
                String firstName = UserObject.getFirstName(user, false);
                StringBuilder sb4 = new StringBuilder();
                sb4.append(firstName);
                sb4.append(ExteraConfig.addCommaAfterMention ? ", " : " ");
                SpannableString spannableString = new SpannableString(sb4.toString());
                spannableString.setSpan(new URLSpanUserMention("" + user.f1734id, 3), 0, firstName.length() + 1, 33);
                ((Delegate) this.delegate.get()).replaceText(resultStartPosition, resultLength, spannableString, false);
            }
        } else if (item instanceof String) {
            ((Delegate) this.delegate.get()).replaceText(resultStartPosition, resultLength, item + " ", false);
        } else if (item instanceof MediaDataController.KeywordResult) {
            String str2 = ((MediaDataController.KeywordResult) item).emoji;
            ((Delegate) this.delegate.get()).addEmojiToRecent(str2);
            if (str2 != null && str2.startsWith("animated_")) {
                try {
                    try {
                        fontMetrics = ((Delegate) this.delegate.get()).getFontMetrics();
                    } catch (Exception e) {
                        FileLog.m1160e(e);
                        fontMetrics = null;
                    }
                    long j = Long.parseLong(str2.substring(9));
                    TLRPC.Document documentFindDocument = AnimatedEmojiDrawable.findDocument(UserConfig.selectedAccount, j);
                    SpannableString spannableString2 = new SpannableString(MessageObject.findAnimatedEmojiEmoticon(documentFindDocument));
                    if (documentFindDocument != null) {
                        animatedEmojiSpan = new AnimatedEmojiSpan(documentFindDocument, fontMetrics);
                    } else {
                        animatedEmojiSpan = new AnimatedEmojiSpan(j, fontMetrics);
                    }
                    spannableString2.setSpan(animatedEmojiSpan, 0, spannableString2.length(), 33);
                    ((Delegate) this.delegate.get()).replaceText(resultStartPosition, resultLength, spannableString2, false);
                } catch (Exception unused) {
                    ((Delegate) this.delegate.get()).replaceText(resultStartPosition, resultLength, str2, true);
                }
            } else {
                ((Delegate) this.delegate.get()).replaceText(resultStartPosition, resultLength, str2, true);
            }
            updateVisibility(false);
        }
        if (item instanceof TLRPC.BotInlineResult) {
            TLRPC.BotInlineResult botInlineResult = (TLRPC.BotInlineResult) item;
            if ((botInlineResult.type.equals("photo") && (botInlineResult.photo != null || botInlineResult.content != null)) || ((botInlineResult.type.equals("gif") && (botInlineResult.document != null || botInlineResult.content != null)) || (botInlineResult.type.equals(MediaStreamTrack.VIDEO_TRACK_KIND) && botInlineResult.document != null))) {
                ArrayList arrayList = new ArrayList(getAdapter().getSearchResultBotContext());
                this.botContextResults = arrayList;
                PhotoViewer photoViewer = PhotoViewer.getInstance();
                WeakReference weakReference2 = this.baseFragment;
                photoViewer.setParentActivity(weakReference2 != null ? (BaseFragment) weakReference2.get() : null, this.resourcesProvider);
                PhotoViewer.getInstance().openPhotoForSelect(arrayList, getAdapter().getItemPosition(i2), 3, false, this.botContextProvider, null);
                return;
            }
            ((Delegate) this.delegate.get()).sendBotInlineResult(botInlineResult, true, 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$withDelegate$5(View view, MotionEvent motionEvent) {
        return ContentPreviewViewer.getInstance().onTouch(motionEvent, getListView(), 0, this.mentionsOnItemClickListener, null, this.resourcesProvider);
    }

    public class MentionsListView extends RecyclerListView {
        private boolean isDragging;
        private boolean isScrolling;
        private int lastHeight;
        private int lastWidth;

        public MentionsListView(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context, resourcesProvider);
            setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.Components.MentionsContainerView.MentionsListView.1
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    MentionsListView.this.isScrolling = i != 0;
                    MentionsListView.this.isDragging = i == 1;
                }

                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    int iFindLastVisibleItemPosition;
                    if (MentionsListView.this.getLayoutManager() == MentionsContainerView.this.gridLayoutManager) {
                        iFindLastVisibleItemPosition = MentionsContainerView.this.gridLayoutManager.findLastVisibleItemPosition();
                    } else {
                        iFindLastVisibleItemPosition = MentionsContainerView.this.linearLayoutManager.findLastVisibleItemPosition();
                    }
                    if ((iFindLastVisibleItemPosition == -1 ? 0 : iFindLastVisibleItemPosition) > 0 && iFindLastVisibleItemPosition > MentionsContainerView.this.adapter.getLastItemCount() - 5) {
                        MentionsContainerView.this.adapter.searchForContextBotForNextOffset();
                    }
                    MentionsContainerView.this.onScrolled(!r2.canScrollVertically(-1), true ^ MentionsListView.this.canScrollVertically(1));
                    MentionsContainerView.this.checkBackgroundBounds();
                }
            });
            addItemDecoration(new RecyclerView.ItemDecoration() { // from class: org.telegram.ui.Components.MentionsContainerView.MentionsListView.2
                @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
                public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                    int childAdapterPosition;
                    rect.left = 0;
                    rect.right = 0;
                    rect.top = 0;
                    rect.bottom = 0;
                    if (recyclerView.getLayoutManager() != MentionsContainerView.this.gridLayoutManager || (childAdapterPosition = recyclerView.getChildAdapterPosition(view)) == 0 || MentionsContainerView.this.adapter.isStickers()) {
                        return;
                    }
                    if (MentionsContainerView.this.adapter.getBotContextSwitch() == null && MentionsContainerView.this.adapter.getBotWebViewSwitch() == null) {
                        rect.top = AndroidUtilities.m1146dp(2.0f);
                    } else {
                        if (childAdapterPosition == 0) {
                            return;
                        }
                        childAdapterPosition--;
                        if (!MentionsContainerView.this.gridLayoutManager.isFirstRow(childAdapterPosition)) {
                            rect.top = AndroidUtilities.m1146dp(2.0f);
                        }
                    }
                    rect.right = MentionsContainerView.this.gridLayoutManager.isLastInRow(childAdapterPosition) ? 0 : AndroidUtilities.m1146dp(2.0f);
                }
            });
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            MotionEvent motionEvent2;
            if (MentionsContainerView.this.linearLayoutManager.getReverseLayout()) {
                if (!this.isDragging && MentionsContainerView.this.paddedAdapter != null && MentionsContainerView.this.paddedAdapter.paddingView != null && MentionsContainerView.this.paddedAdapter.paddingViewAttached && motionEvent.getY() > MentionsContainerView.this.paddedAdapter.paddingView.getTop()) {
                    return false;
                }
            } else if (!this.isDragging && MentionsContainerView.this.paddedAdapter != null && MentionsContainerView.this.paddedAdapter.paddingView != null && MentionsContainerView.this.paddedAdapter.paddingViewAttached && motionEvent.getY() < MentionsContainerView.this.paddedAdapter.paddingView.getBottom()) {
                return false;
            }
            if (!this.isScrolling) {
                motionEvent2 = motionEvent;
                boolean z = ContentPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent2, MentionsContainerView.this.listView, 0, null, this.resourcesProvider);
                if ((!MentionsContainerView.this.adapter.isStickers() && motionEvent2.getAction() == 0) || motionEvent2.getAction() == 2) {
                    MentionsContainerView.this.adapter.doSomeStickersAction();
                }
                return !super.onInterceptTouchEvent(motionEvent2) || z;
            }
            motionEvent2 = motionEvent;
            if (!MentionsContainerView.this.adapter.isStickers()) {
                MentionsContainerView.this.adapter.doSomeStickersAction();
            } else {
                MentionsContainerView.this.adapter.doSomeStickersAction();
            }
            if (super.onInterceptTouchEvent(motionEvent2)) {
            }
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (MentionsContainerView.this.linearLayoutManager.getReverseLayout()) {
                if (!this.isDragging && MentionsContainerView.this.paddedAdapter != null && MentionsContainerView.this.paddedAdapter.paddingView != null && MentionsContainerView.this.paddedAdapter.paddingViewAttached && motionEvent.getY() > MentionsContainerView.this.paddedAdapter.paddingView.getTop()) {
                    return false;
                }
            } else if (!this.isDragging && MentionsContainerView.this.paddedAdapter != null && MentionsContainerView.this.paddedAdapter.paddingView != null && MentionsContainerView.this.paddedAdapter.paddingViewAttached && motionEvent.getY() < MentionsContainerView.this.paddedAdapter.paddingView.getBottom()) {
                return false;
            }
            return super.onTouchEvent(motionEvent);
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (MentionsContainerView.this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            int top;
            int i5 = i3 - i;
            int i6 = i4 - i2;
            boolean zIsReversed = MentionsContainerView.this.isReversed();
            LinearLayoutManager currentLayoutManager = MentionsContainerView.this.getCurrentLayoutManager();
            int iFindFirstVisibleItemPosition = zIsReversed ? currentLayoutManager.findFirstVisibleItemPosition() : currentLayoutManager.findLastVisibleItemPosition();
            View viewFindViewByPosition = currentLayoutManager.findViewByPosition(iFindFirstVisibleItemPosition);
            if (viewFindViewByPosition != null) {
                top = viewFindViewByPosition.getTop() - (zIsReversed ? 0 : this.lastHeight - i6);
            } else {
                top = 0;
            }
            super.onLayout(z, i, i2, i3, i4);
            if (MentionsContainerView.this.scrollToFirst) {
                MentionsContainerView.this.ignoreLayout = true;
                currentLayoutManager.scrollToPositionWithOffset(0, 100000);
                super.onLayout(false, i, i2, i3, i4);
                MentionsContainerView.this.ignoreLayout = false;
                MentionsContainerView.this.scrollToFirst = false;
            } else if (iFindFirstVisibleItemPosition != -1 && i5 == this.lastWidth && i6 - this.lastHeight != 0) {
                MentionsContainerView.this.ignoreLayout = true;
                currentLayoutManager.scrollToPositionWithOffset(iFindFirstVisibleItemPosition, top, false);
                super.onLayout(false, i, i2, i3, i4);
                MentionsContainerView.this.ignoreLayout = false;
            }
            this.lastHeight = i6;
            this.lastWidth = i5;
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView, android.view.View
        public void setTranslationY(float f) {
            super.setTranslationY(f);
            MentionsContainerView.this.invalidate();
            MentionsContainerView.this.checkBackgroundBounds();
        }

        @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
        protected void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i2);
            if (MentionsContainerView.this.paddedAdapter != null) {
                MentionsContainerView.this.paddedAdapter.setPadding(size);
            }
            MentionsContainerView.this.listViewPadding = (int) Math.min(AndroidUtilities.m1146dp(126.0f), AndroidUtilities.displaySize.y * 0.22f);
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(size + ((int) MentionsContainerView.this.listViewPadding), TLObject.FLAG_30));
        }

        @Override // androidx.recyclerview.widget.RecyclerView
        public void onScrolled(int i, int i2) {
            super.onScrolled(i, i2);
            MentionsContainerView.this.invalidate();
            MentionsContainerView.this.checkBackgroundBounds();
        }
    }

    private int getThemedColor(int i) {
        return Theme.getColor(i, this.resourcesProvider);
    }

    /* loaded from: classes6.dex */
    public interface Delegate {
        void addEmojiToRecent(String str);

        Paint.FontMetricsInt getFontMetrics();

        void onStickerSelected(TLRPC.TL_document tL_document, String str, Object obj);

        void replaceText(int i, int i2, CharSequence charSequence, boolean z);

        void sendBotInlineResult(TLRPC.BotInlineResult botInlineResult, boolean z, int i);

        /* renamed from: org.telegram.ui.Components.MentionsContainerView$Delegate$-CC, reason: invalid class name */
        public abstract /* synthetic */ class CC {
            public static void $default$onStickerSelected(Delegate delegate, TLRPC.TL_document tL_document, String str, Object obj) {
            }

            public static void $default$addEmojiToRecent(Delegate delegate, String str) {
            }

            public static void $default$sendBotInlineResult(Delegate delegate, TLRPC.BotInlineResult botInlineResult, boolean z, int i) {
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.emojiLoaded);
        if (getAdapter() != null) {
            getAdapter().onAttachedToWindow();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.emojiLoaded);
        if (getAdapter() != null) {
            getAdapter().onDestroy();
        }
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.emojiLoaded) {
            AndroidUtilities.forEachViews((RecyclerView) this.listView, new Consumer() { // from class: org.telegram.ui.Components.MentionsContainerView$$ExternalSyntheticLambda0
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    MentionsContainerView.$r8$lambda$P0RBUas03aNl8uv9eCnt3Tgs_hs((View) obj);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$P0RBUas03aNl8uv9eCnt3Tgs_hs(View view) {
        if (view instanceof MentionCell) {
            ((MentionCell) view).invalidateEmojis();
        } else if (view instanceof QuickRepliesActivity.QuickReplyView) {
            ((QuickRepliesActivity.QuickReplyView) view).invalidateEmojis();
        } else {
            view.invalidate();
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        checkBackgroundBounds();
    }

    public void setBackgroundDrawable(BlurredBackgroundDrawable blurredBackgroundDrawable) {
        this.backgroundDrawable = blurredBackgroundDrawable;
        blurredBackgroundDrawable.setRadius(AndroidUtilities.m1146dp(22.0f));
        this.backgroundDrawable.setPadding(AndroidUtilities.m1146dp(5.0f));
        checkListViewPadding();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkBackgroundBounds() {
        if (this.listView == null || this.linearLayoutManager == null) {
            return;
        }
        boolean zIsReversed = isReversed();
        this.containerPadding = 0.0f;
        if (zIsReversed) {
            float fMin = Math.min(Math.max(0.0f, (this.paddedAdapter.paddingViewAttached ? r0.paddingView.getTop() : getHeight()) + this.listView.getTranslationY()) + this.containerPadding, (1.0f - this.hideT) * getHeight());
            this.containerTop = 0.0f;
            this.containerBottom = fMin;
        } else {
            this.containerTop = Math.max(Math.max(0.0f, (this.paddedAdapter.paddingViewAttached ? r0.paddingView.getBottom() : 0) + this.listView.getTranslationY()) - this.containerPadding, this.hideT * getHeight());
            this.containerBottom = getMeasuredHeight();
        }
        BlurredBackgroundDrawable blurredBackgroundDrawable = this.backgroundDrawable;
        if (blurredBackgroundDrawable != null) {
            blurredBackgroundDrawable.setBounds(0, ((int) this.containerTop) - AndroidUtilities.m1146dp(5.0f), getMeasuredWidth(), ((int) this.containerBottom) + AndroidUtilities.m1146dp(5.0f));
            this.clipPath.rewind();
            this.clipBounds.set(this.backgroundDrawable.getPaddedBounds());
            if (isGif()) {
                this.clipBounds.inset(AndroidUtilities.m1146dp(2.0f), AndroidUtilities.m1146dp(2.0f));
                this.clipPath.addRoundRect(this.clipBounds, AndroidUtilities.m1146dp(20.0f), AndroidUtilities.m1146dp(20.0f), Path.Direction.CW);
            } else {
                this.clipPath.addRoundRect(this.clipBounds, AndroidUtilities.m1146dp(22.0f), AndroidUtilities.m1146dp(22.0f), Path.Direction.CW);
            }
            this.clipPath.close();
            invalidate();
        }
    }

    private boolean isGif() {
        MentionsAdapter mentionsAdapter;
        MentionsListView mentionsListView = this.listView;
        return (mentionsListView == null || this.gridLayoutManager == null || mentionsListView.getLayoutManager() != this.gridLayoutManager || (mentionsAdapter = this.adapter) == null || !mentionsAdapter.isBotContext()) ? false : true;
    }

    private void checkListViewPadding() {
        if (this.listView == null || this.linearLayoutManager == null) {
            return;
        }
        boolean zIsGif = isGif();
        if (this.backgroundDrawable == null) {
            this.listView.setPadding(0, 0, 0, 0);
        } else {
            this.listView.setPadding(AndroidUtilities.m1146dp(zIsGif ? 7.0f : 5.0f), zIsGif ? AndroidUtilities.m1146dp(2.0f) : 0, AndroidUtilities.m1146dp(zIsGif ? 7.0f : 5.0f), zIsGif ? AndroidUtilities.m1146dp(2.0f) : 0);
        }
    }
}
