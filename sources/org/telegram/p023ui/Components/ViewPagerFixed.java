package org.telegram.p023ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.SystemClock;
import android.text.TextPaint;
import android.transition.TransitionManager;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AnimationNotificationsLocker;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.ViewPagerFixed;
import org.telegram.p023ui.Stories.recorder.HintView2;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public class ViewPagerFixed extends FrameLayout {
    private static final Interpolator interpolator = new Interpolator() { // from class: org.telegram.ui.Components.ViewPagerFixed$$ExternalSyntheticLambda0
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return ViewPagerFixed.$r8$lambda$aoN7Ghc9HAnODMyle0hchIDeT6c(f);
        }
    };
    public Adapter adapter;
    private float additionalOffset;
    private boolean allowDisallowInterceptTouch;
    private boolean animatingForward;
    private boolean backAnimation;
    private float backProgress;
    public int currentPosition;
    public float currentProgress;
    private ValueAnimator manualScrolling;
    private int maximumVelocity;
    private boolean maybeStartTracking;
    protected int nextPosition;
    AnimationNotificationsLocker notificationsLocker;
    private Rect rect;
    private Theme.ResourcesProvider resourcesProvider;
    private boolean startedTracking;
    private int startedTrackingPointerId;
    private int startedTrackingX;
    private int startedTrackingY;
    private AnimatorSet tabsAnimation;
    private boolean tabsAnimationInProgress;
    TabsView tabsView;
    private final float touchSlop;
    ValueAnimator.AnimatorUpdateListener updateTabProgress;
    private VelocityTracker velocityTracker;
    protected View[] viewPages;
    private int[] viewTypes;
    protected SparseArray viewsByType;

    protected void addMoreTabs() {
    }

    protected boolean canScroll(MotionEvent motionEvent) {
        return true;
    }

    protected void invalidateBlur() {
    }

    protected void onBack() {
    }

    protected boolean onBackProgress(float f) {
        return false;
    }

    protected void onItemSelected(View view, View view2, int i, int i2) {
    }

    protected void onScrollEnd() {
    }

    public void onStartTracking() {
    }

    public void onTabAnimationUpdate(boolean z) {
    }

    protected void onTabPageSelected(int i) {
    }

    protected void onTabScrollEnd(int i) {
    }

    protected int tabMarginDp() {
        return 16;
    }

    public static /* synthetic */ float $r8$lambda$aoN7Ghc9HAnODMyle0hchIDeT6c(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2 * f2 * f2) + 1.0f;
    }

    public float getPositionAnimated() {
        float fClamp;
        View view = this.viewPages[0];
        if (view == null || view.getVisibility() != 0) {
            fClamp = 0.0f;
        } else {
            fClamp = (this.currentPosition * Utilities.clamp(1.0f - Math.abs(this.viewPages[0].getTranslationX() / getAvailableTranslationX()), 1.0f, 0.0f)) + 0.0f;
        }
        View view2 = this.viewPages[1];
        if (view2 == null || view2.getVisibility() != 0) {
            return fClamp;
        }
        return fClamp + (this.nextPosition * Utilities.clamp(1.0f - Math.abs(this.viewPages[1].getTranslationX() / getAvailableTranslationX()), 1.0f, 0.0f));
    }

    public float getCurrentPositionAlpha() {
        View view = this.viewPages[0];
        if (view == null || view.getVisibility() != 0) {
            return 0.0f;
        }
        return Utilities.clamp(1.0f - Math.abs(this.viewPages[0].getTranslationX() / getAvailableTranslationX()), 1.0f, 0.0f);
    }

    public int getNextPosition() {
        return this.nextPosition;
    }

    public float getNextPositionAlpha() {
        View view = this.viewPages[1];
        if (view == null || view.getVisibility() != 0) {
            return 0.0f;
        }
        return Utilities.clamp(1.0f - Math.abs(this.viewPages[1].getTranslationX() / getAvailableTranslationX()), 1.0f, 0.0f);
    }

    protected float getAvailableTranslationX() {
        return AndroidUtilities.displaySize.x;
    }

    protected boolean canScrollForward(MotionEvent motionEvent) {
        return canScroll(motionEvent);
    }

    public ViewPagerFixed(Context context) {
        this(context, null);
    }

    public ViewPagerFixed(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.currentProgress = 1.0f;
        this.viewsByType = new SparseArray();
        this.notificationsLocker = new AnimationNotificationsLocker();
        this.updateTabProgress = new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ViewPagerFixed.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (ViewPagerFixed.this.tabsAnimationInProgress) {
                    float fAbs = Math.abs(ViewPagerFixed.this.viewPages[0].getTranslationX()) / ViewPagerFixed.this.viewPages[0].getMeasuredWidth();
                    ViewPagerFixed viewPagerFixed = ViewPagerFixed.this;
                    float f = 1.0f - fAbs;
                    viewPagerFixed.currentProgress = f;
                    TabsView tabsView = viewPagerFixed.tabsView;
                    if (tabsView != null) {
                        tabsView.selectTab(viewPagerFixed.nextPosition, viewPagerFixed.currentPosition, f);
                    }
                }
                ViewPagerFixed.this.onTabAnimationUpdate(false);
            }
        };
        this.rect = new Rect();
        this.allowDisallowInterceptTouch = true;
        this.resourcesProvider = resourcesProvider;
        this.touchSlop = AndroidUtilities.getPixelsInCM(0.3f, true);
        this.maximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        this.viewTypes = new int[2];
        this.viewPages = new View[2];
        setClipChildren(true);
    }

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        this.viewTypes[0] = adapter.getItemViewType(this.currentPosition);
        this.viewPages[0] = adapter.createView(this.viewTypes[0]);
        if (this.viewPages[0] == null && this.currentPosition != 0) {
            this.currentPosition = 0;
            this.viewTypes[0] = adapter.getItemViewType(0);
            this.viewPages[0] = adapter.createView(this.viewTypes[0]);
        }
        adapter.bindView(this.viewPages[0], this.currentPosition, this.viewTypes[0]);
        addView(this.viewPages[0]);
        this.viewPages[0].setVisibility(0);
        fillTabs(false);
    }

    protected void onTabPageSelected(int i, boolean z) {
        onTabPageSelected(i);
    }

    public boolean isManualScrolling() {
        ValueAnimator valueAnimator = this.manualScrolling;
        return valueAnimator != null && valueAnimator.isRunning();
    }

    public boolean scrollToPosition(int i) {
        ValueAnimator valueAnimator;
        if (i == this.currentPosition || ((valueAnimator = this.manualScrolling) != null && this.nextPosition == i)) {
            return false;
        }
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.manualScrolling = null;
        }
        boolean z = this.currentPosition < i;
        this.animatingForward = z;
        this.nextPosition = i;
        updateViewForIndex(1);
        onTabPageSelected(i, z);
        View view = this.viewPages[0];
        int measuredWidth = view != null ? view.getMeasuredWidth() : 0;
        if (z) {
            setTranslationX(this.viewPages[1], measuredWidth);
        } else {
            setTranslationX(this.viewPages[1], -measuredWidth);
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.manualScrolling = valueAnimatorOfFloat;
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ViewPagerFixed$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                this.f$0.lambda$scrollToPosition$1(valueAnimator2);
            }
        });
        this.manualScrolling.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ViewPagerFixed.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ViewPagerFixed viewPagerFixed = ViewPagerFixed.this;
                if (viewPagerFixed.viewPages[1] != null) {
                    viewPagerFixed.swapViews();
                    ViewPagerFixed viewPagerFixed2 = ViewPagerFixed.this;
                    viewPagerFixed2.viewsByType.put(viewPagerFixed2.viewTypes[1], ViewPagerFixed.this.viewPages[1]);
                    ViewPagerFixed viewPagerFixed3 = ViewPagerFixed.this;
                    viewPagerFixed3.removeView(viewPagerFixed3.viewPages[1]);
                    ViewPagerFixed viewPagerFixed4 = ViewPagerFixed.this;
                    viewPagerFixed4.setTranslationX(viewPagerFixed4.viewPages[0], 0.0f);
                    ViewPagerFixed.this.viewPages[1] = null;
                }
                ViewPagerFixed.this.manualScrolling = null;
                ViewPagerFixed.this.onTabAnimationUpdate(true);
                TabsView tabsView = ViewPagerFixed.this.tabsView;
                if (tabsView != null) {
                    tabsView.listView.invalidate();
                    ViewPagerFixed.this.tabsView.listView.invalidateViews();
                    ViewPagerFixed.this.tabsView.invalidate();
                }
                ViewPagerFixed.this.onScrollEnd();
                ViewPagerFixed.this.notificationsLocker.unlock();
            }
        });
        this.manualScrolling.setDuration(540L);
        this.manualScrolling.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
        this.manualScrolling.start();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scrollToPosition$1(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        View view = this.viewPages[1];
        if (view == null) {
            return;
        }
        if (this.animatingForward) {
            setTranslationX(view, r0[0].getMeasuredWidth() * (1.0f - fFloatValue));
            setTranslationX(this.viewPages[0], (-r0.getMeasuredWidth()) * fFloatValue);
        } else {
            setTranslationX(view, (-r0[0].getMeasuredWidth()) * (1.0f - fFloatValue));
            setTranslationX(this.viewPages[0], r0.getMeasuredWidth() * fFloatValue);
        }
        this.currentProgress = fFloatValue;
        onTabAnimationUpdate(true);
        TabsView tabsView = this.tabsView;
        if (tabsView != null) {
            tabsView.listView.invalidate();
            this.tabsView.listView.invalidateViews();
            this.tabsView.invalidate();
        }
    }

    public TabsView createTabsView(boolean z, int i) {
        TabsView tabsView = new TabsView(getContext(), z, i, this.resourcesProvider) { // from class: org.telegram.ui.Components.ViewPagerFixed.3
            @Override // org.telegram.ui.Components.ViewPagerFixed.TabsView
            public void selectTab(int i2, int i3, float f) {
                super.selectTab(i2, i3, f);
                ViewPagerFixed.this.onTabPageSelected(f <= 0.5f ? i2 : i3, i2 < i3);
            }
        };
        this.tabsView = tabsView;
        tabsView.tabMarginDp = tabMarginDp();
        this.tabsView.setDelegate(new TabsView.TabsViewDelegate() { // from class: org.telegram.ui.Components.ViewPagerFixed.4
            @Override // org.telegram.ui.Components.ViewPagerFixed.TabsView.TabsViewDelegate
            public void onSamePageSelected() {
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.TabsView.TabsViewDelegate
            public void onPageSelected(int i2, boolean z2) {
                ViewPagerFixed.this.animatingForward = z2;
                ViewPagerFixed viewPagerFixed = ViewPagerFixed.this;
                viewPagerFixed.nextPosition = i2;
                viewPagerFixed.updateViewForIndex(1);
                ViewPagerFixed.this.onTabPageSelected(i2, z2);
                View view = ViewPagerFixed.this.viewPages[0];
                int measuredWidth = view != null ? view.getMeasuredWidth() : 0;
                ViewPagerFixed viewPagerFixed2 = ViewPagerFixed.this;
                View view2 = viewPagerFixed2.viewPages[1];
                if (view2 != null) {
                    if (z2) {
                        viewPagerFixed2.setTranslationX(view2, measuredWidth);
                    } else {
                        viewPagerFixed2.setTranslationX(view2, -measuredWidth);
                    }
                }
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.TabsView.TabsViewDelegate
            public boolean needsTab(int i2) {
                Adapter adapter = ViewPagerFixed.this.adapter;
                if (adapter == null) {
                    return true;
                }
                return adapter.needsTab(i2);
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.TabsView.TabsViewDelegate
            public void onPageScrolled(float f) {
                if (f == 1.0f) {
                    ViewPagerFixed viewPagerFixed = ViewPagerFixed.this;
                    if (viewPagerFixed.viewPages[1] != null) {
                        viewPagerFixed.swapViews();
                        ViewPagerFixed viewPagerFixed2 = ViewPagerFixed.this;
                        viewPagerFixed2.viewsByType.put(viewPagerFixed2.viewTypes[1], ViewPagerFixed.this.viewPages[1]);
                        ViewPagerFixed viewPagerFixed3 = ViewPagerFixed.this;
                        viewPagerFixed3.removeView(viewPagerFixed3.viewPages[1]);
                        ViewPagerFixed viewPagerFixed4 = ViewPagerFixed.this;
                        viewPagerFixed4.setTranslationX(viewPagerFixed4.viewPages[0], 0.0f);
                        ViewPagerFixed.this.viewPages[1] = null;
                    }
                    ViewPagerFixed viewPagerFixed5 = ViewPagerFixed.this;
                    viewPagerFixed5.onTabScrollEnd(viewPagerFixed5.currentPosition);
                    return;
                }
                ViewPagerFixed viewPagerFixed6 = ViewPagerFixed.this;
                if (viewPagerFixed6.viewPages[1] == null) {
                    return;
                }
                if (viewPagerFixed6.animatingForward) {
                    ViewPagerFixed viewPagerFixed7 = ViewPagerFixed.this;
                    viewPagerFixed7.setTranslationX(viewPagerFixed7.viewPages[1], r4[0].getMeasuredWidth() * (1.0f - f));
                    ViewPagerFixed viewPagerFixed8 = ViewPagerFixed.this;
                    viewPagerFixed8.setTranslationX(viewPagerFixed8.viewPages[0], (-r2.getMeasuredWidth()) * f);
                } else {
                    ViewPagerFixed viewPagerFixed9 = ViewPagerFixed.this;
                    viewPagerFixed9.setTranslationX(viewPagerFixed9.viewPages[1], (-r4[0].getMeasuredWidth()) * (1.0f - f));
                    ViewPagerFixed viewPagerFixed10 = ViewPagerFixed.this;
                    viewPagerFixed10.setTranslationX(viewPagerFixed10.viewPages[0], r2.getMeasuredWidth() * f);
                }
                ViewPagerFixed.this.onTabAnimationUpdate(false);
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.TabsView.TabsViewDelegate
            public boolean canPerformActions() {
                return (ViewPagerFixed.this.tabsAnimationInProgress || ViewPagerFixed.this.startedTracking) ? false : true;
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.TabsView.TabsViewDelegate
            public void invalidateBlur() {
                ViewPagerFixed.this.invalidateBlur();
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.TabsView.TabsViewDelegate
            public boolean canReorder(int i2) {
                Adapter adapter = ViewPagerFixed.this.adapter;
                if (adapter == null) {
                    return false;
                }
                return adapter.canReorder(i2);
            }

            @Override // org.telegram.ui.Components.ViewPagerFixed.TabsView.TabsViewDelegate
            public void applyReorder(ArrayList arrayList) {
                Adapter adapter = ViewPagerFixed.this.adapter;
                if (adapter == null) {
                    return;
                }
                adapter.applyReorder(arrayList);
            }
        });
        fillTabs(false);
        return this.tabsView;
    }

    protected ValueAnimator translateAnimator(final View view, final float f) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(view.getTranslationX(), f);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ViewPagerFixed.5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewPagerFixed.this.setTranslationX(view, ((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ViewPagerFixed.6
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ViewPagerFixed.this.setTranslationX(view, f);
            }
        });
        return valueAnimatorOfFloat;
    }

    protected void setTranslationX(View view, float f) {
        view.setTranslationX(f);
    }

    public boolean isTouch() {
        return this.startedTracking;
    }

    public void resetTouch() {
        if (this.startedTracking) {
            this.maybeStartTracking = true;
            this.startedTracking = false;
            setTranslationX(this.viewPages[0], 0.0f);
            View view = this.viewPages[1];
            if (view != null) {
                setTranslationX(view, this.animatingForward ? r2[0].getMeasuredWidth() : -r2[0].getMeasuredWidth());
            }
            this.nextPosition = 0;
            this.currentProgress = 1.0f;
            TabsView tabsView = this.tabsView;
            if (tabsView != null) {
                tabsView.selectTab(0, this.currentPosition, 1.0f);
            }
            onTabAnimationUpdate(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateViewForIndex(int i) {
        int i2 = i == 0 ? this.currentPosition : this.nextPosition;
        if (i2 < 0 || i2 >= this.adapter.getItemCount()) {
            return;
        }
        if (this.viewPages[i] == null) {
            this.viewTypes[i] = this.adapter.getItemViewType(i2);
            View viewCreateView = (View) this.viewsByType.get(this.viewTypes[i]);
            if (viewCreateView == null) {
                viewCreateView = this.adapter.createView(this.viewTypes[i]);
            } else {
                this.viewsByType.remove(this.viewTypes[i]);
            }
            if (viewCreateView.getParent() != null) {
                ((ViewGroup) viewCreateView.getParent()).removeView(viewCreateView);
            }
            addView(viewCreateView);
            this.viewPages[i] = viewCreateView;
            this.adapter.bindView(viewCreateView, i2, this.viewTypes[i]);
            this.viewPages[i].setVisibility(0);
            return;
        }
        if (this.viewTypes[i] == this.adapter.getItemViewType(i2)) {
            this.adapter.bindView(this.viewPages[i], i2, this.viewTypes[i]);
            this.viewPages[i].setVisibility(0);
            return;
        }
        this.viewsByType.put(this.viewTypes[i], this.viewPages[i]);
        this.viewPages[i].setVisibility(8);
        removeView(this.viewPages[i]);
        this.viewTypes[i] = this.adapter.getItemViewType(i2);
        View viewCreateView2 = (View) this.viewsByType.get(this.viewTypes[i]);
        if (viewCreateView2 == null) {
            viewCreateView2 = this.adapter.createView(this.viewTypes[i]);
        } else {
            this.viewsByType.remove(this.viewTypes[i]);
        }
        addView(viewCreateView2);
        this.viewPages[i] = viewCreateView2;
        viewCreateView2.setVisibility(0);
        Adapter adapter = this.adapter;
        adapter.bindView(this.viewPages[i], i2, adapter.getItemViewType(i2));
    }

    public void fillTabs(boolean z) {
        TabsView tabsView;
        if (this.adapter == null || (tabsView = this.tabsView) == null) {
            return;
        }
        tabsView.removeTabs();
        for (int i = 0; i < this.adapter.getItemCount(); i++) {
            if (this.adapter.needsTab(i)) {
                this.tabsView.addTab(this.adapter.getItemId(i), this.adapter.getItemTitle(i));
            }
        }
        addMoreTabs();
        if (z) {
            TransitionManager.beginDelayedTransition(this.tabsView.listView, TransitionExt.createSimpleTransition());
        }
        this.tabsView.finishAddingTabs();
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0010  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private boolean prepareForMoving(android.view.MotionEvent r7, boolean r8) {
        /*
            r6 = this;
            r0 = 0
            if (r8 != 0) goto L10
            int r1 = r6.currentPosition
            if (r1 != 0) goto L10
            r1 = 0
            r6.backProgress = r1
            boolean r1 = r6.onBackProgress(r1)
            if (r1 == 0) goto L22
        L10:
            r1 = 1
            if (r8 == 0) goto L1e
            int r2 = r6.currentPosition
            org.telegram.ui.Components.ViewPagerFixed$Adapter r3 = r6.adapter
            int r3 = r3.getItemCount()
            int r3 = r3 - r1
            if (r2 == r3) goto L22
        L1e:
            android.animation.ValueAnimator r2 = r6.manualScrolling
            if (r2 == 0) goto L23
        L22:
            return r0
        L23:
            boolean r2 = r6.canScroll(r7)
            if (r2 != 0) goto L2a
            return r0
        L2a:
            if (r8 == 0) goto L33
            boolean r2 = r6.canScrollForward(r7)
            if (r2 != 0) goto L33
            return r0
        L33:
            org.telegram.ui.Components.ViewPagerFixed$Adapter r2 = r6.adapter
            r3 = -1
            if (r2 == 0) goto L47
            int r4 = r6.currentPosition
            if (r8 == 0) goto L3e
            r5 = 1
            goto L3f
        L3e:
            r5 = -1
        L3f:
            int r4 = r4 + r5
            boolean r2 = r2.canScrollTo(r4)
            if (r2 != 0) goto L47
            return r0
        L47:
            android.view.ViewParent r2 = r6.getParent()
            r2.requestDisallowInterceptTouchEvent(r1)
            r6.maybeStartTracking = r0
            r6.startedTracking = r1
            r6.onStartTracking()
            float r7 = r7.getX()
            float r2 = r6.additionalOffset
            float r7 = r7 + r2
            int r7 = (int) r7
            r6.startedTrackingX = r7
            org.telegram.ui.Components.ViewPagerFixed$TabsView r7 = r6.tabsView
            if (r7 == 0) goto L66
            r7.setEnabled(r0)
        L66:
            org.telegram.messenger.AnimationNotificationsLocker r7 = r6.notificationsLocker
            r7.lock()
            r6.animatingForward = r8
            int r7 = r6.currentPosition
            if (r8 == 0) goto L72
            r3 = 1
        L72:
            int r7 = r7 + r3
            r6.nextPosition = r7
            r6.updateViewForIndex(r1)
            android.view.View[] r7 = r6.viewPages
            r2 = r7[r1]
            if (r2 == 0) goto L96
            if (r8 == 0) goto L8b
            r7 = r7[r0]
            int r7 = r7.getMeasuredWidth()
            float r7 = (float) r7
            r6.setTranslationX(r2, r7)
            goto L96
        L8b:
            r7 = r7[r0]
            int r7 = r7.getMeasuredWidth()
            int r7 = -r7
            float r7 = (float) r7
            r6.setTranslationX(r2, r7)
        L96:
            r6.onTabAnimationUpdate(r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.ViewPagerFixed.prepareForMoving(android.view.MotionEvent, boolean):boolean");
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        TabsView tabsView = this.tabsView;
        if (tabsView != null && tabsView.isAnimatingIndicator()) {
            return false;
        }
        if (checkTabsAnimationInProgress()) {
            return true;
        }
        onTouchEvent(motionEvent);
        return this.startedTracking;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (this.allowDisallowInterceptTouch && this.maybeStartTracking && !this.startedTracking) {
            onTouchEvent(null);
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return onTouchEventInternal(motionEvent);
    }

    /* JADX WARN: Code restructure failed: missing block: B:148:0x02a0, code lost:
    
        r6 = true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onTouchEventInternal(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instructions count: 1216
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.ViewPagerFixed.onTouchEventInternal(android.view.MotionEvent):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTouchEventInternal$2(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.backProgress = fFloatValue;
        onBackProgress(fFloatValue);
    }

    protected void swapViews() {
        View[] viewArr = this.viewPages;
        View view = viewArr[0];
        View view2 = viewArr[1];
        viewArr[0] = view2;
        viewArr[1] = view;
        int i = this.currentPosition;
        int i2 = this.nextPosition;
        this.currentPosition = i2;
        this.nextPosition = i;
        this.currentProgress = 1.0f - this.currentProgress;
        int[] iArr = this.viewTypes;
        int i3 = iArr[0];
        iArr[0] = iArr[1];
        iArr[1] = i3;
        onItemSelected(view2, view, i2, i);
    }

    public boolean checkTabsAnimationInProgress() {
        boolean z;
        if (!this.tabsAnimationInProgress) {
            return false;
        }
        if (this.backAnimation) {
            if (Math.abs(this.viewPages[0].getTranslationX()) < 1.0f) {
                setTranslationX(this.viewPages[0], 0.0f);
                View view = this.viewPages[1];
                if (view != null) {
                    setTranslationX(view, r0[0].getMeasuredWidth() * (this.animatingForward ? 1 : -1));
                }
                z = true;
            }
            z = false;
        } else {
            if (Math.abs(this.viewPages[1].getTranslationX()) < 1.0f) {
                setTranslationX(this.viewPages[0], r0.getMeasuredWidth() * (this.animatingForward ? -1 : 1));
                View view2 = this.viewPages[1];
                if (view2 != null) {
                    setTranslationX(view2, 0.0f);
                }
                z = true;
            }
            z = false;
        }
        onTabAnimationUpdate(true);
        if (z) {
            AnimatorSet animatorSet = this.tabsAnimation;
            if (animatorSet != null) {
                animatorSet.cancel();
                this.tabsAnimation = null;
            }
            this.tabsAnimationInProgress = false;
        }
        return this.tabsAnimationInProgress;
    }

    public static float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((f - 0.5f) * 0.47123894f);
    }

    public void setPosition(int i) {
        if (this.adapter == null) {
            this.currentPosition = i;
            onTabAnimationUpdate(false);
        }
        AnimatorSet animatorSet = this.tabsAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        View view = this.viewPages[1];
        if (view != null) {
            this.viewsByType.put(this.viewTypes[1], view);
            removeView(this.viewPages[1]);
            this.viewPages[1] = null;
        }
        int i2 = this.currentPosition;
        if (i2 != i) {
            this.currentPosition = i;
            this.nextPosition = 0;
            this.currentProgress = 1.0f;
            View view2 = this.viewPages[0];
            updateViewForIndex(0);
            onItemSelected(this.viewPages[0], view2, this.currentPosition, i2);
            setTranslationX(this.viewPages[0], 0.0f);
            TabsView tabsView = this.tabsView;
            if (tabsView != null) {
                tabsView.selectTab(this.currentPosition, this.nextPosition, this.currentProgress);
            }
            onTabAnimationUpdate(true);
        }
    }

    public void updateCurrent() {
        if (this.viewTypes[0] != this.adapter.getItemViewType(this.currentPosition)) {
            updateViewForIndex(0);
            View view = this.viewPages[1];
            if (view != null) {
                this.viewsByType.put(this.viewTypes[1], view);
                removeView(this.viewPages[1]);
                this.viewPages[1] = null;
            }
            setTranslationX(this.viewPages[0], 0.0f);
            onTabAnimationUpdate(true);
        }
    }

    public View[] getViewPages() {
        return this.viewPages;
    }

    public boolean isCurrentTabFirst() {
        return this.currentPosition == 0;
    }

    public void rebuild(boolean z) {
        onTouchEvent(null);
        if (!this.adapter.hasStableId()) {
            z = false;
        }
        AnimatorSet animatorSet = this.tabsAnimation;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.tabsAnimation = null;
        }
        View view = this.viewPages[1];
        if (view != null) {
            removeView(view);
            this.viewPages[1] = null;
        }
        View[] viewArr = this.viewPages;
        View view2 = viewArr[0];
        viewArr[1] = view2;
        int iIntValue = (view2 == null || view2.getTag() == null) ? 0 : ((Integer) this.viewPages[1].getTag()).intValue();
        if (this.adapter.getItemCount() == 0) {
            View view3 = this.viewPages[1];
            if (view3 != null) {
                removeView(view3);
                this.viewPages[1] = null;
            }
            View view4 = this.viewPages[0];
            if (view4 != null) {
                removeView(view4);
                this.viewPages[0] = null;
                return;
            }
            return;
        }
        if (this.currentPosition > this.adapter.getItemCount() - 1) {
            this.currentPosition = this.adapter.getItemCount() - 1;
        }
        if (this.currentPosition < 0) {
            this.currentPosition = 0;
        }
        this.viewTypes[0] = this.adapter.getItemViewType(this.currentPosition);
        this.viewPages[0] = this.adapter.createView(this.viewTypes[0]);
        this.adapter.bindView(this.viewPages[0], this.currentPosition, this.viewTypes[0]);
        addView(this.viewPages[0]);
        this.viewPages[0].setVisibility(0);
        if ((this.viewPages[0].getTag() == null ? 0 : ((Integer) this.viewPages[0].getTag()).intValue()) == iIntValue) {
            z = false;
        }
        if (z) {
            this.tabsView.saveFromValues();
        }
        fillTabs(z);
        if (z) {
            this.tabsAnimation = new AnimatorSet();
            View view5 = this.viewPages[1];
            if (view5 != null) {
                setTranslationX(view5, 0.0f);
            }
            View view6 = this.viewPages[0];
            if (view6 != null) {
                setTranslationX(view6, -getMeasuredWidth());
            }
            View view7 = this.viewPages[1];
            if (view7 != null) {
                this.tabsAnimation.playTogether(translateAnimator(view7, getMeasuredWidth()));
            }
            View view8 = this.viewPages[0];
            if (view8 != null) {
                this.tabsAnimation.playTogether(translateAnimator(view8, 0.0f));
            }
            onTabAnimationUpdate(true);
            this.tabsView.indicatorProgress2 = 0.0f;
            this.tabsView.listView.invalidateViews();
            this.tabsView.invalidate();
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ViewPagerFixed$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.f$0.lambda$rebuild$3(valueAnimator);
                }
            });
            this.tabsAnimation.playTogether(valueAnimatorOfFloat);
            this.tabsAnimation.setInterpolator(interpolator);
            this.tabsAnimation.setDuration(220L);
            this.tabsAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ViewPagerFixed.8
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ViewPagerFixed.this.tabsAnimation = null;
                    ViewPagerFixed viewPagerFixed = ViewPagerFixed.this;
                    View view9 = viewPagerFixed.viewPages[1];
                    if (view9 != null) {
                        viewPagerFixed.removeView(view9);
                        ViewPagerFixed.this.viewPages[1] = null;
                    }
                    ViewPagerFixed.this.tabsAnimationInProgress = false;
                    TabsView tabsView = ViewPagerFixed.this.tabsView;
                    if (tabsView != null) {
                        tabsView.setEnabled(true);
                        ViewPagerFixed.this.tabsView.animatingIndicator = false;
                        ViewPagerFixed.this.tabsView.indicatorProgress2 = 1.0f;
                        ViewPagerFixed.this.tabsView.listView.invalidateViews();
                        ViewPagerFixed.this.tabsView.invalidate();
                    }
                }
            });
            this.tabsView.setEnabled(false);
            this.tabsAnimationInProgress = true;
            this.tabsAnimation.start();
            return;
        }
        View view9 = this.viewPages[1];
        if (view9 != null) {
            removeView(view9);
            this.viewPages[1] = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$rebuild$3(ValueAnimator valueAnimator) {
        this.updateTabProgress.onAnimationUpdate(valueAnimator);
        this.tabsView.indicatorProgress2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.tabsView.listView.invalidateViews();
        this.tabsView.invalidate();
    }

    public void clearViews() {
        this.viewsByType.clear();
    }

    public static abstract class Adapter {
        public void applyReorder(ArrayList arrayList) {
        }

        public abstract void bindView(View view, int i, int i2);

        public boolean canReorder(int i) {
            return false;
        }

        public boolean canScrollTo(int i) {
            return true;
        }

        public abstract View createView(int i);

        public abstract int getItemCount();

        public int getItemId(int i) {
            return i;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public boolean hasStableId() {
            return false;
        }

        public boolean needsTab(int i) {
            return true;
        }

        public CharSequence getItemTitle(int i) {
            return "";
        }
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        if (i == 0) {
            return false;
        }
        if (!this.tabsAnimationInProgress && !this.startedTracking) {
            boolean z = i > 0;
            if ((!z && this.currentPosition == 0) || (z && this.currentPosition == this.adapter.getItemCount() - 1)) {
                return false;
            }
        }
        return true;
    }

    public View getCurrentView() {
        return this.viewPages[0];
    }

    public int getCurrentPosition() {
        return this.currentPosition;
    }

    public void cancelTouches() {
        float measuredWidth;
        int measuredWidth2;
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.computeCurrentVelocity(MediaDataController.MAX_STYLE_RUNS_COUNT, this.maximumVelocity);
        }
        if (this.startedTracking) {
            float x = this.viewPages[0].getX();
            this.tabsAnimation = new AnimatorSet();
            if (this.additionalOffset == 0.0f) {
                this.backAnimation = Math.abs(x) < ((float) this.viewPages[0].getMeasuredWidth()) / 3.0f && (Math.abs(0.0f) < 3500.0f || Math.abs(0.0f) < Math.abs(0.0f));
            } else if (Math.abs(0.0f) > 1500.0f) {
                this.backAnimation = false;
            } else if (this.animatingForward) {
                View view = this.viewPages[1];
                if (view != null) {
                    this.backAnimation = view.getX() > ((float) (this.viewPages[0].getMeasuredWidth() >> 1));
                } else {
                    this.backAnimation = false;
                }
            } else {
                this.backAnimation = this.viewPages[0].getX() < ((float) (this.viewPages[0].getMeasuredWidth() >> 1));
            }
            if (this.backAnimation) {
                measuredWidth = Math.abs(x);
                if (this.animatingForward) {
                    this.tabsAnimation.playTogether(translateAnimator(this.viewPages[0], 0.0f));
                    View view2 = this.viewPages[1];
                    if (view2 != null) {
                        this.tabsAnimation.playTogether(translateAnimator(view2, view2.getMeasuredWidth()));
                    }
                } else {
                    this.tabsAnimation.playTogether(translateAnimator(this.viewPages[0], 0.0f));
                    View view3 = this.viewPages[1];
                    if (view3 != null) {
                        this.tabsAnimation.playTogether(translateAnimator(view3, -view3.getMeasuredWidth()));
                    }
                }
            } else if (this.nextPosition >= 0) {
                measuredWidth = this.viewPages[0].getMeasuredWidth() - Math.abs(x);
                if (this.animatingForward) {
                    this.tabsAnimation.playTogether(translateAnimator(this.viewPages[0], -r5.getMeasuredWidth()));
                    View view4 = this.viewPages[1];
                    if (view4 != null) {
                        this.tabsAnimation.playTogether(translateAnimator(view4, 0.0f));
                    }
                } else {
                    this.tabsAnimation.playTogether(translateAnimator(this.viewPages[0], r5.getMeasuredWidth()));
                    View view5 = this.viewPages[1];
                    if (view5 != null) {
                        this.tabsAnimation.playTogether(translateAnimator(view5, 0.0f));
                    }
                }
            } else {
                measuredWidth = 0.0f;
            }
            if (this.nextPosition < 0) {
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.backProgress, this.backAnimation ? 0.0f : 1.0f);
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ViewPagerFixed$$ExternalSyntheticLambda4
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.f$0.lambda$cancelTouches$4(valueAnimator);
                    }
                });
                this.tabsAnimation.playTogether(valueAnimatorOfFloat);
            }
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat2.addUpdateListener(this.updateTabProgress);
            this.tabsAnimation.playTogether(valueAnimatorOfFloat2);
            this.tabsAnimation.setInterpolator(interpolator);
            int measuredWidth3 = getMeasuredWidth();
            float f = measuredWidth3 / 2;
            float fDistanceInfluenceForSnapDuration = f + (distanceInfluenceForSnapDuration(Math.min(1.0f, (measuredWidth * 1.0f) / measuredWidth3)) * f);
            float fAbs = Math.abs(0.0f);
            if (fAbs > 0.0f) {
                measuredWidth2 = Math.round(Math.abs(fDistanceInfluenceForSnapDuration / fAbs) * 1000.0f) * 4;
            } else {
                measuredWidth2 = (int) (((measuredWidth / getMeasuredWidth()) + 1.0f) * 100.0f);
            }
            this.tabsAnimation.setDuration(Math.max(150, Math.min(measuredWidth2, 600)));
            this.tabsAnimation.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ViewPagerFixed.9
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ViewPagerFixed.this.tabsAnimation = null;
                    ViewPagerFixed viewPagerFixed = ViewPagerFixed.this;
                    if (viewPagerFixed.nextPosition < 0) {
                        viewPagerFixed.onBack();
                    }
                    ViewPagerFixed viewPagerFixed2 = ViewPagerFixed.this;
                    if (viewPagerFixed2.viewPages[1] != null) {
                        if (!viewPagerFixed2.backAnimation) {
                            ViewPagerFixed.this.swapViews();
                        }
                        ViewPagerFixed viewPagerFixed3 = ViewPagerFixed.this;
                        viewPagerFixed3.viewsByType.put(viewPagerFixed3.viewTypes[1], ViewPagerFixed.this.viewPages[1]);
                        ViewPagerFixed viewPagerFixed4 = ViewPagerFixed.this;
                        viewPagerFixed4.removeView(viewPagerFixed4.viewPages[1]);
                        ViewPagerFixed.this.viewPages[1].setVisibility(8);
                        ViewPagerFixed.this.viewPages[1] = null;
                    }
                    ViewPagerFixed.this.tabsAnimationInProgress = false;
                    ViewPagerFixed.this.maybeStartTracking = false;
                    TabsView tabsView = ViewPagerFixed.this.tabsView;
                    if (tabsView != null) {
                        tabsView.setEnabled(true);
                    }
                    ViewPagerFixed.this.onTabAnimationUpdate(false);
                    ViewPagerFixed.this.onScrollEnd();
                    ViewPagerFixed.this.notificationsLocker.unlock();
                }
            });
            this.tabsAnimation.start();
            this.tabsAnimationInProgress = true;
            this.startedTracking = false;
            onTabAnimationUpdate(false);
        } else {
            this.maybeStartTracking = false;
            TabsView tabsView = this.tabsView;
            if (tabsView != null) {
                tabsView.setEnabled(true);
            }
        }
        VelocityTracker velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
            this.velocityTracker = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$cancelTouches$4(ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.backProgress = fFloatValue;
        onBackProgress(fFloatValue);
    }

    public static class TabsView extends FrameLayout {
        private int activeTextColorKey;
        public ListAdapter adapter;
        private int additionalTabWidth;
        private int allTabsWidth;
        private boolean animatingIndicator;
        private float animatingIndicatorProgress;
        private Runnable animationRunnable;
        private float animationTime;
        private int backgroundColorKey;
        private final Paint counterPaint;
        private int currentPosition;
        private TabsViewDelegate delegate;
        private final Paint deletePaint;
        private float editingAnimationProgress;
        private boolean editingForwardAnimation;
        private float editingStartAnimationProgress;
        private float hideProgress;
        private SparseIntArray idToPosition;
        private boolean ignoreLayout;
        private float indicatorProgress2;
        private CubicBezierInterpolator interpolator;
        private boolean invalidated;
        private boolean isEditing;
        private boolean isInHiddenMode;
        private DefaultItemAnimator itemAnimator;
        private ItemTouchHelper itemTouchHelper;
        private long lastAnimationTime;
        float lastDrawnIndicatorW;
        float lastDrawnIndicatorX;
        public LinearLayoutManager layoutManager;
        public RecyclerListView listView;
        private int manualScrollingToId;
        private int manualScrollingToPosition;
        private Utilities.Callback2Return onTabLongClick;
        private boolean orderChanged;
        private float overrideFromW;
        private float overrideFromX;
        private SparseIntArray positionToId;
        private SparseIntArray positionToWidth;
        private SparseIntArray positionToX;
        private Utilities.Callback2Return preTabClick;
        private int prevLayoutWidth;
        private int previousId;
        private int previousPosition;
        private boolean reordering;
        private Theme.ResourcesProvider resourcesProvider;
        private int scrollingToChild;
        private int selectedTabId;
        private int selectorColorKey;
        private GradientDrawable selectorDrawable;
        private final Paint selectorPaint;
        private int selectorType;
        private int tabLineColorKey;
        public int tabMarginDp;
        private final ArrayList tabs;
        ValueAnimator tabsAnimator;
        private final TextPaint textCounterPaint;
        private final TextPaint textPaint;
        private int unactiveTextColorKey;

        public interface TabsViewDelegate {
            void applyReorder(ArrayList arrayList);

            boolean canPerformActions();

            boolean canReorder(int i);

            void invalidateBlur();

            boolean needsTab(int i);

            void onPageScrolled(float f);

            void onPageSelected(int i, boolean z);

            void onSamePageSelected();
        }

        /* renamed from: $r8$lambda$-PK3hC1bmjMgE0D2nGi36N3pg8M, reason: not valid java name */
        public static /* synthetic */ void m12130$r8$lambda$PK3hC1bmjMgE0D2nGi36N3pg8M(TLObject tLObject, TLRPC.TL_error tL_error) {
        }

        public void setOnTabLongClick(Utilities.Callback2Return<Integer, View, Boolean> callback2Return) {
            this.onTabLongClick = callback2Return;
        }

        private static class Tab {
            public float alpha = 1.0f;
            public int counter;

            /* renamed from: id */
            public int f2019id;
            public CharSequence title;
            public int titleWidth;

            public Tab(int i, CharSequence charSequence) {
                this.f2019id = i;
                this.title = charSequence;
            }

            public int getWidth(boolean z, TextPaint textPaint) {
                int iCeil = (int) Math.ceil(HintView2.measureCorrectly(this.title, textPaint));
                this.titleWidth = iCeil;
                return Math.max(0, iCeil);
            }
        }

        public class TabView extends View {
            private int currentPosition;
            private Tab currentTab;
            private CharSequence currentText;
            private RectF rect;
            private boolean reordering;
            private final AnimatedFloat shakeAlpha;
            private Shaker shaker;
            private int tabWidth;
            private Text text;
            private int textOffsetX;

            public void setReordering(boolean z) {
                if (this.reordering == z) {
                    return;
                }
                this.reordering = z;
                invalidate();
            }

            public TabView(Context context) {
                super(context);
                this.rect = new RectF();
                this.shakeAlpha = new AnimatedFloat(this, 360L, CubicBezierInterpolator.EASE_OUT_QUINT);
            }

            public void setTab(Tab tab, int i) {
                this.currentTab = tab;
                this.currentPosition = i;
                setContentDescription(tab.title);
                setAlpha(tab.alpha);
                requestLayout();
            }

            @Override // android.view.View
            public int getId() {
                return this.currentTab.f2019id;
            }

            @Override // android.view.View
            protected void onMeasure(int i, int i2) {
                setMeasuredDimension(this.currentTab.getWidth(false, TabsView.this.textPaint) + AndroidUtilities.m1146dp(TabsView.this.tabMarginDp * 2) + TabsView.this.additionalTabWidth, View.MeasureSpec.getSize(i2));
            }

            @Override // android.view.View
            protected void onDraw(Canvas canvas) {
                int i;
                int i2;
                int i3;
                int i4;
                int i5;
                int i6;
                int i7;
                String str;
                int iCeil;
                int iM1146dp;
                float f;
                int iM1146dp2;
                float f2;
                String str2;
                int i8;
                float f3;
                int i9;
                Canvas canvas2 = canvas;
                canvas2.save();
                float f4 = this.shakeAlpha.set(this.reordering);
                if (f4 > 0.0f) {
                    if (this.shaker == null) {
                        this.shaker = new Shaker(this);
                    }
                    canvas2.translate(getWidth() / 2.0f, getHeight() / 2.0f);
                    this.shaker.concat(canvas2, f4);
                    canvas2.translate((-getWidth()) / 2.0f, (-getHeight()) / 2.0f);
                }
                if (this.currentTab.f2019id != Integer.MAX_VALUE && TabsView.this.editingAnimationProgress != 0.0f) {
                    canvas2.save();
                    float f5 = TabsView.this.editingAnimationProgress * (this.currentPosition % 2 == 0 ? 1.0f : -1.0f);
                    canvas2.translate(AndroidUtilities.m1146dp(0.66f) * f5, 0.0f);
                    canvas2.rotate(f5, getMeasuredWidth() / 2, getMeasuredHeight() / 2);
                }
                if (TabsView.this.manualScrollingToId != -1) {
                    i = TabsView.this.manualScrollingToId;
                    i2 = TabsView.this.selectedTabId;
                } else {
                    i = TabsView.this.selectedTabId;
                    i2 = TabsView.this.previousId;
                }
                int i10 = i;
                int i11 = i2;
                if (this.currentTab.f2019id == i10) {
                    i3 = TabsView.this.activeTextColorKey;
                    i4 = TabsView.this.unactiveTextColorKey;
                    i5 = Theme.key_chats_tabUnreadActiveBackground;
                    i6 = Theme.key_chats_tabUnreadUnactiveBackground;
                } else {
                    i3 = TabsView.this.unactiveTextColorKey;
                    i4 = TabsView.this.activeTextColorKey;
                    i5 = Theme.key_chats_tabUnreadUnactiveBackground;
                    i6 = Theme.key_chats_tabUnreadActiveBackground;
                }
                int i12 = i5;
                int i13 = i6;
                if (TabsView.this.selectorType == 9) {
                    TabsView.this.textPaint.setColor(Theme.getColor(TabsView.this.unactiveTextColorKey, TabsView.this.resourcesProvider));
                } else if ((TabsView.this.animatingIndicator || TabsView.this.manualScrollingToId != -1) && ((i7 = this.currentTab.f2019id) == i10 || i7 == i11)) {
                    TabsView.this.textPaint.setColor(ColorUtils.blendARGB(Theme.getColor(i4, TabsView.this.resourcesProvider), Theme.getColor(i3, TabsView.this.resourcesProvider), TabsView.this.animatingIndicatorProgress));
                } else {
                    TabsView.this.textPaint.setColor(Theme.getColor(i3, TabsView.this.resourcesProvider));
                }
                int i14 = this.currentTab.counter;
                if (i14 > 0) {
                    str = String.format("%d", Integer.valueOf(i14));
                    iCeil = (int) Math.ceil(TabsView.this.textCounterPaint.measureText(str));
                    iM1146dp = Math.max(AndroidUtilities.m1146dp(10.0f), iCeil) + AndroidUtilities.m1146dp(10.0f);
                } else {
                    str = null;
                    iCeil = 0;
                    iM1146dp = 0;
                }
                if (this.currentTab.f2019id == Integer.MAX_VALUE || (!TabsView.this.isEditing && TabsView.this.editingStartAnimationProgress == 0.0f)) {
                    f = 2.0f;
                } else {
                    f = 2.0f;
                    iM1146dp = (int) (iM1146dp + ((AndroidUtilities.m1146dp(20.0f) - iM1146dp) * TabsView.this.editingStartAnimationProgress));
                }
                int i15 = iM1146dp;
                int i16 = this.currentTab.titleWidth;
                if (i15 != 0) {
                    iM1146dp2 = AndroidUtilities.m1146dp((str != null ? 1.0f : TabsView.this.editingStartAnimationProgress) * 6.0f) + i15;
                } else {
                    iM1146dp2 = 0;
                }
                this.tabWidth = i16 + iM1146dp2;
                int measuredWidth = (getMeasuredWidth() - this.tabWidth) / 2;
                CharSequence charSequence = this.currentTab.title;
                if ((charSequence != null || this.currentText == null) && charSequence.equals(this.currentText)) {
                    f2 = 0.0f;
                } else {
                    Tab tab = this.currentTab;
                    f2 = 0.0f;
                    CharSequence charSequenceReplaceEmoji = Emoji.replaceEmoji(tab.title, TabsView.this.textPaint.getFontMetricsInt(), false);
                    tab.title = charSequenceReplaceEmoji;
                    this.currentText = charSequenceReplaceEmoji;
                    Text text = this.text;
                    if (text != null) {
                        text.detach();
                    }
                    this.text = new Text(this.currentText, TabsView.this.textPaint.getTextSize() / AndroidUtilities.density, TabsView.this.textPaint.getTypeface()).supportAnimatedEmojis(this);
                }
                Text text2 = this.text;
                if (text2 != null) {
                    str2 = str;
                    i8 = iCeil;
                    f3 = 1.0f;
                    text2.ellipsize(AndroidUtilities.m1146dp(400.0f)).draw(canvas2, measuredWidth + this.textOffsetX, getMeasuredHeight() / 2, TabsView.this.textPaint.getColor(), 1.0f);
                    canvas2 = canvas2;
                } else {
                    str2 = str;
                    i8 = iCeil;
                    f3 = 1.0f;
                }
                if (str2 != null || (this.currentTab.f2019id != Integer.MAX_VALUE && (TabsView.this.isEditing || TabsView.this.editingStartAnimationProgress != f2))) {
                    TabsView.this.textCounterPaint.setColor(Theme.getColor(TabsView.this.backgroundColorKey, TabsView.this.resourcesProvider));
                    if (Theme.hasThemeKey(i12) && Theme.hasThemeKey(i13)) {
                        int color = Theme.getColor(i12, TabsView.this.resourcesProvider);
                        if ((TabsView.this.animatingIndicator || TabsView.this.manualScrollingToPosition != -1) && ((i9 = this.currentTab.f2019id) == i10 || i9 == i11)) {
                            TabsView.this.counterPaint.setColor(ColorUtils.blendARGB(Theme.getColor(i13, TabsView.this.resourcesProvider), color, TabsView.this.animatingIndicatorProgress));
                        } else {
                            TabsView.this.counterPaint.setColor(color);
                        }
                    } else {
                        TabsView.this.counterPaint.setColor(TabsView.this.textPaint.getColor());
                    }
                    int iM1146dp3 = measuredWidth + this.currentTab.titleWidth + AndroidUtilities.m1146dp(6.0f);
                    int measuredHeight = (getMeasuredHeight() - AndroidUtilities.m1146dp(20.0f)) / 2;
                    if (this.currentTab.f2019id != Integer.MAX_VALUE && ((TabsView.this.isEditing || TabsView.this.editingStartAnimationProgress != f2) && str2 == null)) {
                        TabsView.this.counterPaint.setAlpha((int) (TabsView.this.editingStartAnimationProgress * 255.0f));
                    } else {
                        TabsView.this.counterPaint.setAlpha(255);
                    }
                    this.rect.set(iM1146dp3, measuredHeight, iM1146dp3 + i15, AndroidUtilities.m1146dp(20.0f) + measuredHeight);
                    RectF rectF = this.rect;
                    float f6 = AndroidUtilities.density;
                    canvas2.drawRoundRect(rectF, f6 * 11.5f, f6 * 11.5f, TabsView.this.counterPaint);
                    if (str2 != null) {
                        if (this.currentTab.f2019id != Integer.MAX_VALUE) {
                            TabsView.this.textCounterPaint.setAlpha((int) ((f3 - TabsView.this.editingStartAnimationProgress) * 255.0f));
                        }
                        RectF rectF2 = this.rect;
                        canvas2.drawText(str2, rectF2.left + ((rectF2.width() - i8) / f), measuredHeight + AndroidUtilities.m1146dp(14.5f), TabsView.this.textCounterPaint);
                    }
                    if (this.currentTab.f2019id != Integer.MAX_VALUE && (TabsView.this.isEditing || TabsView.this.editingStartAnimationProgress != f2)) {
                        TabsView.this.deletePaint.setColor(TabsView.this.textCounterPaint.getColor());
                        TabsView.this.deletePaint.setAlpha((int) (TabsView.this.editingStartAnimationProgress * 255.0f));
                        float fM1146dp = AndroidUtilities.m1146dp(3.0f);
                        canvas2.drawLine(this.rect.centerX() - fM1146dp, this.rect.centerY() - fM1146dp, this.rect.centerX() + fM1146dp, this.rect.centerY() + fM1146dp, TabsView.this.deletePaint);
                        canvas.drawLine(this.rect.centerX() - fM1146dp, this.rect.centerY() + fM1146dp, this.rect.centerX() + fM1146dp, this.rect.centerY() - fM1146dp, TabsView.this.deletePaint);
                    }
                }
                if (this.currentTab.f2019id != Integer.MAX_VALUE && TabsView.this.editingAnimationProgress != f2) {
                    canvas.restore();
                }
                canvas.restore();
            }

            @Override // android.view.View
            public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
                accessibilityNodeInfo.setSelected((this.currentTab == null || TabsView.this.selectedTabId == -1 || this.currentTab.f2019id != TabsView.this.selectedTabId) ? false : true);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public TabsView(Context context, boolean z, int i, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            this.indicatorProgress2 = 1.0f;
            TextPaint textPaint = new TextPaint(1);
            this.textPaint = textPaint;
            TextPaint textPaint2 = new TextPaint(1);
            this.textCounterPaint = textPaint2;
            TextPaint textPaint3 = new TextPaint(1);
            this.deletePaint = textPaint3;
            this.counterPaint = new Paint(1);
            this.tabs = new ArrayList();
            this.tabMarginDp = 16;
            this.selectedTabId = -1;
            this.manualScrollingToPosition = -1;
            this.manualScrollingToId = -1;
            this.scrollingToChild = -1;
            this.tabLineColorKey = Theme.key_profile_tabSelectedLine;
            this.activeTextColorKey = Theme.key_profile_tabSelectedText;
            this.unactiveTextColorKey = Theme.key_profile_tabText;
            this.selectorColorKey = Theme.key_profile_tabSelector;
            this.backgroundColorKey = Theme.key_actionBarDefault;
            this.interpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
            this.positionToId = new SparseIntArray(5);
            this.idToPosition = new SparseIntArray(5);
            this.positionToWidth = new SparseIntArray(5);
            this.positionToX = new SparseIntArray(5);
            this.animationRunnable = new Runnable() { // from class: org.telegram.ui.Components.ViewPagerFixed.TabsView.1
                @Override // java.lang.Runnable
                public void run() {
                    if (TabsView.this.animatingIndicator) {
                        long jElapsedRealtime = SystemClock.elapsedRealtime() - TabsView.this.lastAnimationTime;
                        if (jElapsedRealtime > 17) {
                            jElapsedRealtime = 17;
                        }
                        TabsView.this.animationTime += jElapsedRealtime / 200.0f;
                        TabsView tabsView = TabsView.this;
                        tabsView.setAnimationIdicatorProgress(tabsView.interpolator.getInterpolation(TabsView.this.animationTime));
                        if (TabsView.this.animationTime > 1.0f) {
                            TabsView.this.animationTime = 1.0f;
                        }
                        if (TabsView.this.animationTime < 1.0f) {
                            AndroidUtilities.runOnUIThread(TabsView.this.animationRunnable);
                            return;
                        }
                        TabsView.this.animatingIndicator = false;
                        TabsView.this.setEnabled(true);
                        if (TabsView.this.delegate != null) {
                            TabsView.this.delegate.onPageScrolled(1.0f);
                        }
                    }
                }
            };
            this.selectorPaint = new Paint(1);
            this.resourcesProvider = resourcesProvider;
            this.selectorType = i;
            textPaint2.setTextSize(AndroidUtilities.m1146dp(13.0f));
            textPaint2.setTypeface(AndroidUtilities.bold());
            textPaint.setTextSize(AndroidUtilities.m1146dp(i == 9 ? 14.0f : 15.0f));
            textPaint.setTypeface(AndroidUtilities.bold());
            textPaint3.setStyle(Paint.Style.STROKE);
            textPaint3.setStrokeCap(Paint.Cap.ROUND);
            textPaint3.setStrokeWidth(AndroidUtilities.m1146dp(1.5f));
            this.selectorDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, null);
            int i2 = ExteraConfig.tabStyle;
            float fDpf2 = AndroidUtilities.dpf2(i2 == 3 ? 10.0f : i2 == 4 ? 30.0f : 3.0f);
            int i3 = ExteraConfig.tabStyle;
            int i4 = 0;
            Object[] objArr = 0;
            if (i3 == 1 || i3 >= 3) {
                this.selectorDrawable.setCornerRadii(new float[]{fDpf2, fDpf2, fDpf2, fDpf2, fDpf2, fDpf2, fDpf2, fDpf2});
            } else {
                this.selectorDrawable.setCornerRadii(new float[]{fDpf2, fDpf2, fDpf2, fDpf2, 0.0f, 0.0f, 0.0f, 0.0f});
            }
            this.selectorDrawable.setColor(ColorUtils.setAlphaComponent(Theme.getColor(this.tabLineColorKey, resourcesProvider), ExteraConfig.tabStyle >= 3 ? 31 : 255));
            setHorizontalScrollBarEnabled(false);
            RecyclerListView recyclerListView = new RecyclerListView(context) { // from class: org.telegram.ui.Components.ViewPagerFixed.TabsView.2
                @Override // android.view.ViewGroup
                public void addView(View view, int i5, ViewGroup.LayoutParams layoutParams) {
                    super.addView(view, i5, layoutParams);
                    if (TabsView.this.isInHiddenMode) {
                        view.setScaleX(0.3f);
                        view.setScaleY(0.3f);
                        view.setAlpha(0.0f);
                    } else {
                        view.setScaleX(1.0f);
                        view.setScaleY(1.0f);
                        view.setAlpha(1.0f);
                    }
                }

                @Override // android.view.View
                public void setAlpha(float f) {
                    super.setAlpha(f);
                    TabsView.this.invalidate();
                }

                @Override // org.telegram.p023ui.Components.RecyclerListView
                protected boolean canHighlightChildAt(View view, float f, float f2) {
                    if (TabsView.this.isEditing) {
                        TabView tabView = (TabView) view;
                        float fM1146dp = AndroidUtilities.m1146dp(6.0f);
                        if (tabView.rect.left - fM1146dp < f && tabView.rect.right + fM1146dp > f) {
                            return false;
                        }
                    }
                    return super.canHighlightChildAt(view, f, f2);
                }
            };
            this.listView = recyclerListView;
            recyclerListView.setOverScrollMode(2);
            if (z) {
                this.listView.setItemAnimator(null);
            } else {
                ((DefaultItemAnimator) this.listView.getItemAnimator()).setDelayAnimations(false);
            }
            this.listView.setSelectorType(ExteraConfig.tabStyle >= 3 ? 100 : i);
            if (i < 3) {
                this.listView.setSelectorRadius(6);
            }
            this.listView.setSelectorDrawableColor(ExteraConfig.tabStyle >= 3 ? 0 : Theme.getColor(this.selectorColorKey, resourcesProvider));
            RecyclerListView recyclerListView2 = this.listView;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, i4, objArr == true ? 1 : 0) { // from class: org.telegram.ui.Components.ViewPagerFixed.TabsView.3
                @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
                public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i5) {
                    LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) { // from class: org.telegram.ui.Components.ViewPagerFixed.TabsView.3.1
                        @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
                        protected void onTargetFound(View view, RecyclerView.State state2, RecyclerView.SmoothScroller.Action action) {
                            int iCalculateDxToMakeVisible = calculateDxToMakeVisible(view, getHorizontalSnapPreference());
                            if (iCalculateDxToMakeVisible > 0 || (iCalculateDxToMakeVisible == 0 && view.getLeft() - AndroidUtilities.m1146dp(21.0f) < 0)) {
                                iCalculateDxToMakeVisible += AndroidUtilities.m1146dp(60.0f);
                            } else if (iCalculateDxToMakeVisible < 0 || (iCalculateDxToMakeVisible == 0 && view.getRight() + AndroidUtilities.m1146dp(21.0f) > TabsView.this.getMeasuredWidth())) {
                                iCalculateDxToMakeVisible -= AndroidUtilities.m1146dp(60.0f);
                            }
                            int iCalculateDyToMakeVisible = calculateDyToMakeVisible(view, getVerticalSnapPreference());
                            int iMax = Math.max(Opcodes.GETFIELD, calculateTimeForDeceleration((int) Math.sqrt((iCalculateDxToMakeVisible * iCalculateDxToMakeVisible) + (iCalculateDyToMakeVisible * iCalculateDyToMakeVisible))));
                            if (iMax > 0) {
                                action.update(-iCalculateDxToMakeVisible, -iCalculateDyToMakeVisible, iMax, this.mDecelerateInterpolator);
                            }
                        }
                    };
                    linearSmoothScroller.setTargetPosition(i5);
                    startSmoothScroll(linearSmoothScroller);
                }

                @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
                public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    super.onInitializeAccessibilityNodeInfo(recycler, state, accessibilityNodeInfoCompat);
                    if (TabsView.this.isInHiddenMode) {
                        accessibilityNodeInfoCompat.setVisibleToUser(false);
                    }
                }
            };
            this.layoutManager = linearLayoutManager;
            recyclerListView2.setLayoutManager(linearLayoutManager);
            this.listView.setPadding(AndroidUtilities.m1146dp(7.0f), 0, AndroidUtilities.m1146dp(7.0f), 0);
            this.listView.setClipToPadding(false);
            this.listView.setDrawSelectorBehind(true);
            ListAdapter listAdapter = new ListAdapter(context);
            this.adapter = listAdapter;
            listAdapter.setHasStableIds(z);
            this.listView.setAdapter(this.adapter);
            this.listView.setOnItemClickListener(new RecyclerListView.OnItemClickListenerExtended() { // from class: org.telegram.ui.Components.ViewPagerFixed$TabsView$$ExternalSyntheticLambda2
                @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
                public /* synthetic */ boolean hasDoubleTap(View view, int i5) {
                    return RecyclerListView.OnItemClickListenerExtended.CC.$default$hasDoubleTap(this, view, i5);
                }

                @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
                public /* synthetic */ void onDoubleTap(View view, int i5, float f, float f2) {
                    RecyclerListView.OnItemClickListenerExtended.CC.$default$onDoubleTap(this, view, i5, f, f2);
                }

                @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListenerExtended
                public final void onItemClick(View view, int i5, float f, float f2) {
                    this.f$0.lambda$new$0(view, i5, f, f2);
                }
            });
            this.listView.setOnItemLongClickListener(new RecyclerListView.OnItemLongClickListener() { // from class: org.telegram.ui.Components.ViewPagerFixed$TabsView$$ExternalSyntheticLambda3
                @Override // org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener
                public final boolean onItemClick(View view, int i5) {
                    return this.f$0.lambda$new$1(view, i5);
                }
            });
            this.listView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.Components.ViewPagerFixed.TabsView.4
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i5, int i6) {
                    TabsView.this.invalidate();
                }
            });
            if (i == 9) {
                addView(this.listView, LayoutHelper.createFrame(-2, -1, 1));
            } else {
                addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$new$0(View view, int i, float f, float f2) {
            TabsViewDelegate tabsViewDelegate;
            TabsViewDelegate tabsViewDelegate2 = this.delegate;
            if (tabsViewDelegate2 == null || tabsViewDelegate2.canPerformActions()) {
                TabView tabView = (TabView) view;
                if (i == this.currentPosition && (tabsViewDelegate = this.delegate) != null) {
                    tabsViewDelegate.onSamePageSelected();
                    return;
                }
                Utilities.Callback2Return callback2Return = this.preTabClick;
                if (callback2Return == null || !((Boolean) callback2Return.run(Integer.valueOf(tabView.currentTab.f2019id), Integer.valueOf(i))).booleanValue()) {
                    scrollToTab(tabView.currentTab.f2019id, i);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ boolean lambda$new$1(View view, int i) {
            Utilities.Callback2Return callback2Return = this.onTabLongClick;
            if (callback2Return == null) {
                return false;
            }
            return ((Boolean) callback2Return.run(Integer.valueOf(((TabView) view).currentTab.f2019id), view)).booleanValue();
        }

        public void setDelegate(TabsViewDelegate tabsViewDelegate) {
            this.delegate = tabsViewDelegate;
        }

        public void setPreTabClick(Utilities.Callback2Return<Integer, Integer, Boolean> callback2Return) {
            this.preTabClick = callback2Return;
        }

        public boolean isAnimatingIndicator() {
            return this.animatingIndicator;
        }

        public int getCurrentPosition() {
            return this.currentPosition;
        }

        public int getPreviousPosition() {
            return this.previousPosition;
        }

        public float getAnimatingIndicatorProgress() {
            return this.animatingIndicatorProgress;
        }

        public void scrollToTab(int i, int i2) {
            int i3 = this.currentPosition;
            boolean z = i3 < i2;
            this.scrollingToChild = -1;
            this.previousPosition = i3;
            this.previousId = this.selectedTabId;
            TabsViewDelegate tabsViewDelegate = this.delegate;
            if (tabsViewDelegate == null || tabsViewDelegate.needsTab(i2)) {
                this.currentPosition = i2;
                this.selectedTabId = i;
            }
            ValueAnimator valueAnimator = this.tabsAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            if (this.animatingIndicator) {
                this.animatingIndicator = false;
            }
            this.animationTime = 0.0f;
            this.animatingIndicatorProgress = 0.0f;
            this.animatingIndicator = true;
            setEnabled(false);
            TabsViewDelegate tabsViewDelegate2 = this.delegate;
            if (tabsViewDelegate2 != null) {
                tabsViewDelegate2.onPageSelected(i2, z);
            }
            scrollToChild(this.currentPosition);
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.tabsAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.ViewPagerFixed$TabsView$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    this.f$0.lambda$scrollToTab$2(valueAnimator2);
                }
            });
            this.tabsAnimator.setDuration(250L);
            this.tabsAnimator.setInterpolator(CubicBezierInterpolator.DEFAULT);
            this.tabsAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.ViewPagerFixed.TabsView.5
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    TabsView.this.animatingIndicator = false;
                    TabsView.this.setEnabled(true);
                    if (TabsView.this.delegate != null) {
                        TabsView.this.delegate.onPageScrolled(1.0f);
                    }
                    TabsView.this.invalidate();
                }
            });
            this.tabsAnimator.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$scrollToTab$2(ValueAnimator valueAnimator) {
            float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            setAnimationIdicatorProgress(fFloatValue);
            TabsViewDelegate tabsViewDelegate = this.delegate;
            if (tabsViewDelegate != null) {
                tabsViewDelegate.onPageScrolled(fFloatValue);
            }
        }

        public void setAnimationIdicatorProgress(float f) {
            this.animatingIndicatorProgress = f;
            this.listView.invalidateViews();
            invalidate();
            TabsViewDelegate tabsViewDelegate = this.delegate;
            if (tabsViewDelegate != null) {
                tabsViewDelegate.onPageScrolled(f);
            }
        }

        public Drawable getSelectorDrawable() {
            return this.selectorDrawable;
        }

        public RecyclerListView getTabsContainer() {
            return this.listView;
        }

        public int getNextPageId(boolean z) {
            return this.positionToId.get(this.currentPosition + (z ? 1 : -1), -1);
        }

        public int getPageIdByPosition(int i) {
            return this.positionToId.get(i, -1);
        }

        public void addTab(int i, CharSequence charSequence) {
            int size = this.tabs.size();
            if (size == 0 && this.selectedTabId == -1) {
                this.selectedTabId = i;
            }
            this.positionToId.put(size, i);
            this.idToPosition.put(i, size);
            int i2 = this.selectedTabId;
            if (i2 != -1 && i2 == i) {
                this.currentPosition = size;
            }
            Tab tab = new Tab(i, charSequence);
            this.allTabsWidth += tab.getWidth(true, this.textPaint) + AndroidUtilities.m1146dp(this.tabMarginDp * 2);
            this.tabs.add(tab);
        }

        public void removeTabs() {
            this.tabs.clear();
            this.positionToId.clear();
            this.idToPosition.clear();
            this.positionToWidth.clear();
            this.positionToX.clear();
            this.allTabsWidth = 0;
        }

        public void setReordering(final boolean z) {
            if (this.reordering == z) {
                return;
            }
            this.reordering = z;
            if (z && this.itemTouchHelper == null) {
                this.itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() { // from class: org.telegram.ui.Components.ViewPagerFixed.TabsView.6
                    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                    }

                    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                        if (TabsView.this.delegate.canReorder(viewHolder.getAdapterPosition())) {
                            return ItemTouchHelper.Callback.makeMovementFlags(12, 0);
                        }
                        return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
                    }

                    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                        int adapterPosition = viewHolder.getAdapterPosition();
                        int adapterPosition2 = viewHolder2.getAdapterPosition();
                        int i = 0;
                        if (!TabsView.this.delegate.canReorder(adapterPosition) || !TabsView.this.delegate.canReorder(adapterPosition2)) {
                            return false;
                        }
                        Utilities.swapItems(TabsView.this.tabs, adapterPosition, adapterPosition2);
                        TabsView.this.adapter.notifyItemMoved(adapterPosition, adapterPosition2);
                        ArrayList arrayList = new ArrayList();
                        ArrayList arrayList2 = TabsView.this.tabs;
                        int size = arrayList2.size();
                        while (i < size) {
                            Object obj = arrayList2.get(i);
                            i++;
                            arrayList.add(Integer.valueOf(((Tab) obj).f2019id));
                        }
                        TabsView.this.delegate.applyReorder(arrayList);
                        return true;
                    }

                    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
                    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z2) {
                        super.onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, z2);
                        TabsView.this.invalidate();
                    }
                });
            }
            if (this.reordering && this.itemAnimator == null) {
                DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator() { // from class: org.telegram.ui.Components.ViewPagerFixed.TabsView.7
                    @Override // androidx.recyclerview.widget.DefaultItemAnimator
                    protected void onMoveAnimationUpdate(RecyclerView.ViewHolder viewHolder) {
                        super.onMoveAnimationUpdate(viewHolder);
                        TabsView.this.invalidate();
                    }
                };
                this.itemAnimator = defaultItemAnimator;
                defaultItemAnimator.setSupportsChangeAnimations(false);
                this.itemAnimator.setDelayAnimations(false);
                this.itemAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                this.itemAnimator.setDurations(350L);
            }
            ItemTouchHelper itemTouchHelper = this.itemTouchHelper;
            if (itemTouchHelper != null) {
                itemTouchHelper.attachToRecyclerView(z ? this.listView : null);
            }
            this.listView.setItemAnimator(z ? this.itemAnimator : null);
            AndroidUtilities.forEachViews((RecyclerView) this.listView, new Consumer() { // from class: org.telegram.ui.Components.ViewPagerFixed$TabsView$$ExternalSyntheticLambda1
                @Override // com.google.android.exoplayer2.util.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$setReordering$3(z, (View) obj);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setReordering$3(boolean z, View view) {
            TabsViewDelegate tabsViewDelegate;
            int childAdapterPosition = this.listView.getChildAdapterPosition(view);
            if (view instanceof TabView) {
                ((TabView) view).setReordering(z && (tabsViewDelegate = this.delegate) != null && tabsViewDelegate.canReorder(childAdapterPosition));
            }
        }

        public void finishAddingTabs() {
            this.adapter.notifyDataSetChanged();
        }

        public int getCurrentTabId() {
            return this.selectedTabId;
        }

        public int getFirstTabId() {
            return this.positionToId.get(0, 0);
        }

        private void updateTabsWidths() {
            this.positionToX.clear();
            this.positionToWidth.clear();
            int iM1146dp = AndroidUtilities.m1146dp(7.0f);
            int size = this.tabs.size();
            for (int i = 0; i < size; i++) {
                int width = ((Tab) this.tabs.get(i)).getWidth(false, this.textPaint);
                this.positionToWidth.put(i, width);
                this.positionToX.put(i, (this.additionalTabWidth / 2) + iM1146dp);
                iM1146dp += width + AndroidUtilities.m1146dp(this.tabMarginDp * 2) + this.additionalTabWidth;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void saveFromValues() {
            this.overrideFromX = this.lastDrawnIndicatorX;
            this.overrideFromW = this.lastDrawnIndicatorW;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x0082  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0103  */
        @Override // android.view.ViewGroup
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        protected boolean drawChild(android.graphics.Canvas r20, android.view.View r21, long r22) {
            /*
                Method dump skipped, instructions count: 506
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.ViewPagerFixed.TabsView.drawChild(android.graphics.Canvas, android.view.View, long):boolean");
        }

        @Override // android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            if (!this.tabs.isEmpty()) {
                int size = (View.MeasureSpec.getSize(i) - AndroidUtilities.m1146dp(7.0f)) - AndroidUtilities.m1146dp(7.0f);
                int i3 = this.additionalTabWidth;
                if (this.tabs.size() == 1 || this.selectorType == 9) {
                    this.additionalTabWidth = 0;
                } else {
                    int i4 = this.allTabsWidth;
                    this.additionalTabWidth = i4 < size ? (size - i4) / this.tabs.size() : 0;
                }
                if (i3 != this.additionalTabWidth) {
                    this.ignoreLayout = true;
                    this.adapter.notifyDataSetChanged();
                    this.ignoreLayout = false;
                }
                updateTabsWidths();
                this.invalidated = false;
            }
            super.onMeasure(i, i2);
        }

        public void updateColors() {
            this.selectorDrawable.setColor(ColorUtils.setAlphaComponent(Theme.getColor(this.tabLineColorKey, this.resourcesProvider), ExteraConfig.tabStyle >= 3 ? 31 : 255));
            this.listView.invalidateViews();
            this.listView.invalidate();
            invalidate();
        }

        @Override // android.view.View, android.view.ViewParent
        public void requestLayout() {
            if (this.ignoreLayout) {
                return;
            }
            super.requestLayout();
        }

        private void scrollToChild(int i) {
            if (this.tabs.isEmpty() || this.scrollingToChild == i || i < 0 || i >= this.tabs.size()) {
                return;
            }
            this.scrollingToChild = i;
            this.listView.smoothScrollToPosition(i);
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            int i5 = i3 - i;
            if (this.prevLayoutWidth != i5) {
                this.prevLayoutWidth = i5;
                this.scrollingToChild = -1;
                if (this.animatingIndicator) {
                    AndroidUtilities.cancelRunOnUIThread(this.animationRunnable);
                    this.animatingIndicator = false;
                    setEnabled(true);
                    TabsViewDelegate tabsViewDelegate = this.delegate;
                    if (tabsViewDelegate != null) {
                        tabsViewDelegate.onPageScrolled(1.0f);
                    }
                }
            }
        }

        public void selectTab(int i, int i2, float f) {
            if (f < 0.0f) {
                f = 0.0f;
            } else if (f > 1.0f) {
                f = 1.0f;
            }
            this.currentPosition = i;
            this.selectedTabId = this.positionToId.get(i);
            if (f > 0.0f) {
                TabsViewDelegate tabsViewDelegate = this.delegate;
                if (tabsViewDelegate == null || tabsViewDelegate.needsTab(i2)) {
                    this.manualScrollingToPosition = i2;
                } else {
                    this.manualScrollingToPosition = i;
                }
                this.manualScrollingToId = this.positionToId.get(i2);
            } else {
                this.manualScrollingToPosition = -1;
                this.manualScrollingToId = -1;
            }
            this.animatingIndicatorProgress = f;
            this.listView.invalidateViews();
            invalidate();
            scrollToChild(i);
            if (f >= 1.0f) {
                this.manualScrollingToPosition = -1;
                this.manualScrollingToId = -1;
                this.currentPosition = i2;
                this.selectedTabId = this.positionToId.get(i2);
            }
            TabsViewDelegate tabsViewDelegate2 = this.delegate;
            if (tabsViewDelegate2 != null) {
                tabsViewDelegate2.invalidateBlur();
            }
        }

        public void selectTabWithId(int i, float f) {
            int i2 = this.idToPosition.get(i, -1);
            if (i2 < 0) {
                return;
            }
            if (f < 0.0f) {
                f = 0.0f;
            } else if (f > 1.0f) {
                f = 1.0f;
            }
            if (f > 0.0f) {
                this.manualScrollingToPosition = i2;
                this.manualScrollingToId = i;
            } else {
                this.manualScrollingToPosition = -1;
                this.manualScrollingToId = -1;
            }
            this.animatingIndicatorProgress = f;
            this.listView.invalidateViews();
            invalidate();
            scrollToChild(i2);
            if (f >= 1.0f) {
                this.manualScrollingToPosition = -1;
                this.manualScrollingToId = -1;
                this.currentPosition = i2;
                this.selectedTabId = i;
            }
        }

        public void setIsEditing(boolean z) {
            this.isEditing = z;
            this.editingForwardAnimation = true;
            this.listView.invalidateViews();
            invalidate();
            if (this.isEditing || !this.orderChanged) {
                return;
            }
            MessagesStorage.getInstance(UserConfig.selectedAccount).saveDialogFiltersOrder();
            TLRPC.TL_messages_updateDialogFiltersOrder tL_messages_updateDialogFiltersOrder = new TLRPC.TL_messages_updateDialogFiltersOrder();
            ArrayList<MessagesController.DialogFilter> arrayList = MessagesController.getInstance(UserConfig.selectedAccount).dialogFilters;
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                arrayList.get(i);
                tL_messages_updateDialogFiltersOrder.order.add(Integer.valueOf(arrayList.get(i).f1454id));
            }
            ConnectionsManager.getInstance(UserConfig.selectedAccount).sendRequest(tL_messages_updateDialogFiltersOrder, new RequestDelegate() { // from class: org.telegram.ui.Components.ViewPagerFixed$TabsView$$ExternalSyntheticLambda0
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    ViewPagerFixed.TabsView.m12130$r8$lambda$PK3hC1bmjMgE0D2nGi36N3pg8M(tLObject, tL_error);
                }
            });
            this.orderChanged = false;
        }

        private class ListAdapter extends RecyclerListView.SelectionAdapter {
            private Context mContext;

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemViewType(int i) {
                return 0;
            }

            @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
            public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
                return true;
            }

            public ListAdapter(Context context) {
                this.mContext = context;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public int getItemCount() {
                return TabsView.this.tabs.size();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public long getItemId(int i) {
                return ((Tab) TabsView.this.tabs.get(i)).f2019id;
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                return new RecyclerListView.Holder(TabsView.this.new TabView(this.mContext));
            }

            @Override // androidx.recyclerview.widget.RecyclerView.Adapter
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
                TabView tabView = (TabView) viewHolder.itemView;
                tabView.setTab((Tab) TabsView.this.tabs.get(i), i);
                tabView.setReordering(TabsView.this.reordering && TabsView.this.delegate != null && TabsView.this.delegate.canReorder(i));
            }
        }

        public void hide(boolean z, boolean z2) {
            this.isInHiddenMode = z;
            int i = 0;
            if (z2) {
                while (i < this.listView.getChildCount()) {
                    this.listView.getChildAt(i).animate().alpha(z ? 0.0f : 1.0f).scaleX(z ? 0.0f : 1.0f).scaleY(z ? 0.0f : 1.0f).setInterpolator(CubicBezierInterpolator.DEFAULT).setDuration(220L).start();
                    i++;
                }
            } else {
                while (i < this.listView.getChildCount()) {
                    View childAt = this.listView.getChildAt(i);
                    childAt.setScaleX(z ? 0.0f : 1.0f);
                    childAt.setScaleY(z ? 0.0f : 1.0f);
                    childAt.setAlpha(z ? 0.0f : 1.0f);
                    i++;
                }
                this.hideProgress = z ? 1.0f : 0.0f;
            }
            invalidate();
        }
    }

    private View findScrollingChild(ViewGroup viewGroup, float f, float f2) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.getVisibility() == 0) {
                childAt.getHitRect(this.rect);
                if (!this.rect.contains((int) f, (int) f2)) {
                    continue;
                } else {
                    if (childAt.canScrollHorizontally(-1)) {
                        return childAt;
                    }
                    if (childAt instanceof ViewGroup) {
                        Rect rect = this.rect;
                        View viewFindScrollingChild = findScrollingChild((ViewGroup) childAt, f - rect.left, f2 - rect.top);
                        if (viewFindScrollingChild != null) {
                            return viewFindScrollingChild;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return null;
    }

    public void drawForBlur(Canvas canvas) {
        RecyclerListView recyclerListViewFindRecyclerView;
        int i = 0;
        while (true) {
            View[] viewArr = this.viewPages;
            if (i >= viewArr.length) {
                return;
            }
            View view = viewArr[i];
            if (view != null && view.getVisibility() == 0 && (recyclerListViewFindRecyclerView = findRecyclerView(this.viewPages[i])) != null) {
                for (int i2 = 0; i2 < recyclerListViewFindRecyclerView.getChildCount(); i2++) {
                    View childAt = recyclerListViewFindRecyclerView.getChildAt(i2);
                    if (childAt.getY() < AndroidUtilities.m1146dp(203.0f) + AndroidUtilities.m1146dp(100.0f)) {
                        int iSave = canvas.save();
                        canvas.translate(this.viewPages[i].getX(), getY() + this.viewPages[i].getY() + recyclerListViewFindRecyclerView.getY() + childAt.getY());
                        childAt.draw(canvas);
                        canvas.restoreToCount(iSave);
                    }
                }
            }
            i++;
        }
    }

    private RecyclerListView findRecyclerView(View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof RecyclerListView) {
                return (RecyclerListView) childAt;
            }
            if (childAt instanceof ViewGroup) {
                findRecyclerView(childAt);
            }
        }
        return null;
    }

    public void setAllowDisallowInterceptTouch(boolean z) {
        this.allowDisallowInterceptTouch = z;
    }
}
