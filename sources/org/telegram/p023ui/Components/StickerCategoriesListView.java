package org.telegram.p023ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.telegram.SQLite.SQLiteDatabase;
import org.telegram.SQLite.SQLitePreparedStatement;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.CacheFetcher;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.StickerCategoriesListView;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes6.dex */
public abstract class StickerCategoriesListView extends RecyclerListView {
    private static EmojiGroupFetcher fetcher;
    public static CacheFetcher search;
    private Adapter adapter;
    private Paint backgroundPaint;
    private EmojiCategory[] categories;
    private boolean categoriesShouldShow;
    private ValueAnimator categoriesShownAnimator;
    private float categoriesShownT;
    private int categoriesType;
    private int dontOccupyWidth;
    public boolean isGlassDesign;
    public Integer layerNum;
    private LinearLayoutManager layoutManager;
    private AnimatedFloat leftBoundAlpha;
    private Drawable leftBoundDrawable;
    private Utilities.Callback onCategoryClick;
    private Utilities.Callback onScrollFully;
    private Utilities.Callback onScrollIntoOccupiedWidth;
    private View paddingView;
    private int paddingWidth;
    private final RectF rect1;
    private final RectF rect2;
    private final RectF rect3;
    private AnimatedFloat rightBoundAlpha;
    private Drawable rightBoundDrawable;
    private boolean scrolledFully;
    private boolean scrolledIntoOccupiedWidth;
    private AnimatedFloat selectedAlpha;
    private int selectedCategoryIndex;
    private AnimatedFloat selectedIndex;
    private Paint selectedPaint;
    private float shownButtonsAtStart;
    private static Set loadedIconsType = new HashSet();
    static int loadedCategoryIcons = 0;

    protected abstract boolean isTabIconsAnimationEnabled(boolean z);

    protected EmojiCategory[] preprocessCategories(EmojiCategory[] emojiCategoryArr) {
        return emojiCategoryArr;
    }

    static {
        fetcher = new EmojiGroupFetcher();
        search = new EmojiSearch();
    }

    public static void preload(final int i, int i2) {
        fetcher.fetch(i, Integer.valueOf(i2), new Utilities.Callback() { // from class: org.telegram.ui.Components.StickerCategoriesListView$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                StickerCategoriesListView.m11688$r8$lambda$ZDQ_VKiRHbBVs7puOYEMcaxZQ(i, (TLRPC.TL_messages_emojiGroups) obj);
            }
        });
    }

    /* renamed from: $r8$lambda$ZDQ_VKiRHbB-Vs7puOYEMcax-ZQ, reason: not valid java name */
    public static /* synthetic */ void m11688$r8$lambda$ZDQ_VKiRHbBVs7puOYEMcaxZQ(int i, TLRPC.TL_messages_emojiGroups tL_messages_emojiGroups) {
        ArrayList arrayList;
        if (tL_messages_emojiGroups == null || (arrayList = tL_messages_emojiGroups.groups) == null) {
            return;
        }
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            AnimatedEmojiDrawable.getDocumentFetcher(i).fetchDocument(((TLRPC.EmojiGroup) obj).icon_emoji_id, null);
        }
    }

    public StickerCategoriesListView(Context context, int i, Theme.ResourcesProvider resourcesProvider) {
        this(context, null, i, resourcesProvider);
    }

    public StickerCategoriesListView(Context context, final EmojiCategory[] emojiCategoryArr, int i, Theme.ResourcesProvider resourcesProvider) {
        super(context, resourcesProvider);
        this.shownButtonsAtStart = 6.5f;
        this.categories = null;
        CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
        this.leftBoundAlpha = new AnimatedFloat(this, 360L, cubicBezierInterpolator);
        this.rightBoundAlpha = new AnimatedFloat(this, 360L, cubicBezierInterpolator);
        this.selectedPaint = new Paint(1);
        this.selectedCategoryIndex = -1;
        this.categoriesShownT = 0.0f;
        this.categoriesShouldShow = true;
        this.selectedAlpha = new AnimatedFloat(this, 350L, cubicBezierInterpolator);
        this.selectedIndex = new AnimatedFloat(this, 350L, cubicBezierInterpolator);
        this.rect1 = new RectF();
        this.rect2 = new RectF();
        this.rect3 = new RectF();
        this.categoriesType = i;
        setPadding(0, 0, AndroidUtilities.m1146dp(2.0f), 0);
        Adapter adapter = new Adapter();
        this.adapter = adapter;
        setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        this.layoutManager = linearLayoutManager;
        setLayoutManager(linearLayoutManager);
        this.layoutManager.setOrientation(0);
        setSelectorRadius(AndroidUtilities.m1146dp(15.0f));
        setSelectorType(1);
        int i2 = Theme.key_listSelector;
        setSelectorDrawableColor(getThemedColor(i2));
        this.selectedPaint.setColor(getThemedColor(i2));
        setSelectorRadius(0);
        setSelectorType(100);
        setSelectorDrawableColor(0);
        setWillNotDraw(false);
        setOnItemClickListener(new RecyclerListView.OnItemClickListener() { // from class: org.telegram.ui.Components.StickerCategoriesListView$$ExternalSyntheticLambda1
            @Override // org.telegram.ui.Components.RecyclerListView.OnItemClickListener
            public final void onItemClick(View view, int i3) {
                this.f$0.lambda$new$1(view, i3);
            }
        });
        final long jCurrentTimeMillis = System.currentTimeMillis();
        fetcher.fetch(UserConfig.selectedAccount, Integer.valueOf(i), new Utilities.Callback() { // from class: org.telegram.ui.Components.StickerCategoriesListView$$ExternalSyntheticLambda2
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$new$3(emojiCategoryArr, jCurrentTimeMillis, (TLRPC.TL_messages_emojiGroups) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$3(final EmojiCategory[] emojiCategoryArr, final long j, final TLRPC.TL_messages_emojiGroups tL_messages_emojiGroups) {
        if (tL_messages_emojiGroups != null) {
            NotificationCenter.getInstance(UserConfig.selectedAccount).doOnIdle(new Runnable() { // from class: org.telegram.ui.Components.StickerCategoriesListView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$new$2(emojiCategoryArr, tL_messages_emojiGroups, j);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(EmojiCategory[] emojiCategoryArr, TLRPC.TL_messages_emojiGroups tL_messages_emojiGroups, long j) {
        this.categories = new EmojiCategory[(emojiCategoryArr == null ? 0 : emojiCategoryArr.length) + tL_messages_emojiGroups.groups.size()];
        int i = 0;
        if (emojiCategoryArr != null) {
            while (i < emojiCategoryArr.length) {
                this.categories[i] = emojiCategoryArr[i];
                i++;
            }
        }
        for (int i2 = 0; i2 < tL_messages_emojiGroups.groups.size(); i2++) {
            this.categories[i + i2] = EmojiCategory.remote((TLRPC.EmojiGroup) tL_messages_emojiGroups.groups.get(i2));
        }
        this.categories = preprocessCategories(this.categories);
        this.adapter.notifyDataSetChanged();
        setCategoriesShownT(0.0f);
        updateCategoriesShown(this.categoriesShouldShow, System.currentTimeMillis() - j > 16);
    }

    public void setShownButtonsAtStart(float f) {
        this.shownButtonsAtStart = f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onItemClick, reason: merged with bridge method [inline-methods] */
    public void lambda$new$1(int i, View view) {
        EmojiCategory[] emojiCategoryArr;
        if (i >= 1 && (emojiCategoryArr = this.categories) != null) {
            EmojiCategory emojiCategory = emojiCategoryArr[i - 1];
            int iM1146dp = AndroidUtilities.m1146dp(64.0f);
            if (getMeasuredWidth() - view.getRight() < iM1146dp) {
                smoothScrollBy(iM1146dp - (getMeasuredWidth() - view.getRight()), 0, CubicBezierInterpolator.EASE_OUT_QUINT);
            } else if (view.getLeft() < iM1146dp) {
                smoothScrollBy(-(iM1146dp - view.getLeft()), 0, CubicBezierInterpolator.EASE_OUT_QUINT);
            }
            Utilities.Callback callback = this.onCategoryClick;
            if (callback != null) {
                callback.run(emojiCategory);
            }
        }
    }

    private int getScrollToStartWidth() {
        if (getChildCount() <= 0) {
            return 0;
        }
        View childAt = getChildAt(0);
        if (childAt instanceof CategoryButton) {
            return this.paddingWidth + Math.max(0, (getChildAdapterPosition(childAt) - 1) * getHeight()) + (-childAt.getLeft());
        }
        return -childAt.getLeft();
    }

    public void scrollToStart() {
        smoothScrollBy(-getScrollToStartWidth(), 0, CubicBezierInterpolator.EASE_OUT_QUINT);
    }

    public void scrollToSelected() {
        final int iMax = ((-getScrollToStartWidth()) - Math.max(0, this.dontOccupyWidth)) + (this.selectedCategoryIndex * AndroidUtilities.m1146dp(34.0f));
        scrollBy(iMax, 0);
        post(new Runnable() { // from class: org.telegram.ui.Components.StickerCategoriesListView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$scrollToSelected$4(iMax);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$scrollToSelected$4(int i) {
        onScrolled(i, 0);
    }

    public void selectCategory(EmojiCategory emojiCategory) {
        int i;
        if (this.categories != null) {
            i = 0;
            while (true) {
                EmojiCategory[] emojiCategoryArr = this.categories;
                if (i >= emojiCategoryArr.length) {
                    break;
                } else if (emojiCategoryArr[i] == emojiCategory) {
                    break;
                } else {
                    i++;
                }
            }
        } else {
            i = -1;
        }
        selectCategory(i);
    }

    public void selectCategory(int i) {
        if (this.selectedCategoryIndex < 0 && i >= 0) {
            this.selectedIndex.set(i, true);
        }
        this.selectedCategoryIndex = i;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt instanceof CategoryButton) {
                ((CategoryButton) childAt).setSelected(this.selectedCategoryIndex == getChildAdapterPosition(childAt) - 1, true);
            }
        }
        invalidate();
    }

    public EmojiCategory getSelectedCategory() {
        int i;
        EmojiCategory[] emojiCategoryArr = this.categories;
        if (emojiCategoryArr == null || (i = this.selectedCategoryIndex) < 0 || i >= emojiCategoryArr.length) {
            return null;
        }
        return emojiCategoryArr[i];
    }

    public int getCategoryIndex() {
        return this.selectedCategoryIndex;
    }

    @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateCategoriesShown(this.categoriesShouldShow, false);
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        View view = this.paddingView;
        if (view != null) {
            view.requestLayout();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1, types: [int] */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v9 */
    public void updateCategoriesShown(boolean z, boolean z2) {
        this.categoriesShouldShow = z;
        ?? r5 = z;
        if (this.categories == null) {
            r5 = 0;
        }
        if (this.categoriesShownT == ((float) r5)) {
            return;
        }
        ValueAnimator valueAnimator = this.categoriesShownAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.categoriesShownAnimator = null;
        }
        if (z2) {
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.categoriesShownT, r5 != 0 ? 1.0f : 0.0f);
            this.categoriesShownAnimator = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.StickerCategoriesListView$$ExternalSyntheticLambda3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    this.f$0.lambda$updateCategoriesShown$5(valueAnimator2);
                }
            });
            this.categoriesShownAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.StickerCategoriesListView.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    StickerCategoriesListView stickerCategoriesListView = StickerCategoriesListView.this;
                    stickerCategoriesListView.setCategoriesShownT(((Float) stickerCategoriesListView.categoriesShownAnimator.getAnimatedValue()).floatValue());
                    StickerCategoriesListView.this.categoriesShownAnimator = null;
                }
            });
            this.categoriesShownAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
            this.categoriesShownAnimator.setDuration((this.categories == null ? 5 : r6.length) * 120);
            this.categoriesShownAnimator.start();
            return;
        }
        setCategoriesShownT(r5 != 0 ? 1.0f : 0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateCategoriesShown$5(ValueAnimator valueAnimator) {
        setCategoriesShownT(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCategoriesShownT(float f) {
        this.categoriesShownT = f;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof CategoryButton) {
                float fCascade = AndroidUtilities.cascade(f, (getChildCount() - 1) - getChildAdapterPosition(childAt), getChildCount() - 1, 3.0f);
                if (fCascade > 0.0f && childAt.getAlpha() <= 0.0f) {
                    ((CategoryButton) childAt).play(false);
                }
                childAt.setAlpha(fCascade);
                childAt.setScaleX(fCascade);
                childAt.setScaleY(fCascade);
            }
        }
        invalidate();
    }

    public boolean isCategoriesShown() {
        return this.categoriesShownT > 0.5f;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void onScrolled(int i, int i2) {
        boolean z;
        boolean z2;
        Utilities.Callback callback;
        super.onScrolled(i, i2);
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (childAt instanceof CategoryButton) {
                z = true;
            } else {
                z2 = childAt.getRight() <= this.dontOccupyWidth;
                z = false;
            }
        } else {
            z = false;
            z2 = false;
        }
        boolean z3 = this.scrolledIntoOccupiedWidth;
        if (z3 != z2) {
            this.scrolledIntoOccupiedWidth = z2;
            Utilities.Callback callback2 = this.onScrollIntoOccupiedWidth;
            if (callback2 != null) {
                callback2.run(Integer.valueOf(z2 ? Math.max(0, getScrollToStartWidth() - (this.paddingWidth - this.dontOccupyWidth)) : 0));
            }
            invalidate();
        } else if (z3 && (callback = this.onScrollIntoOccupiedWidth) != null) {
            callback.run(Integer.valueOf(Math.max(0, getScrollToStartWidth() - (this.paddingWidth - this.dontOccupyWidth))));
        }
        if (this.scrolledFully != z) {
            this.scrolledFully = z;
            Utilities.Callback callback3 = this.onScrollFully;
            if (callback3 != null) {
                callback3.run(Boolean.valueOf(z));
            }
            invalidate();
        }
    }

    public void setDontOccupyWidth(int i) {
        this.dontOccupyWidth = i;
    }

    public void setOnScrollIntoOccupiedWidth(Utilities.Callback<Integer> callback) {
        this.onScrollIntoOccupiedWidth = callback;
    }

    public void setOnScrollFully(Utilities.Callback<Boolean> callback) {
        this.onScrollFully = callback;
    }

    public void setOnCategoryClick(Utilities.Callback<EmojiCategory> callback) {
        this.onCategoryClick = callback;
    }

    public boolean isScrolledIntoOccupiedWidth() {
        return this.scrolledIntoOccupiedWidth;
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        if (this.backgroundPaint == null) {
            this.backgroundPaint = new Paint(1);
        }
        this.backgroundPaint.setColor(i);
        Drawable drawableMutate = getContext().getResources().getDrawable(C2369R.drawable.gradient_right).mutate();
        this.leftBoundDrawable = drawableMutate;
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        drawableMutate.setColorFilter(new PorterDuffColorFilter(i, mode));
        Drawable drawableMutate2 = getContext().getResources().getDrawable(C2369R.drawable.gradient_left).mutate();
        this.rightBoundDrawable = drawableMutate2;
        drawableMutate2.setColorFilter(new PorterDuffColorFilter(i, mode));
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0089  */
    @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void draw(android.graphics.Canvas r11) {
        /*
            r10 = this;
            android.graphics.Paint r0 = r10.backgroundPaint
            r1 = 1065353216(0x3f800000, float:1.0)
            r2 = 0
            if (r0 == 0) goto L89
            r0 = 2147483647(0x7fffffff, float:NaN)
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = 0
        Ld:
            int r5 = r10.getChildCount()
            if (r4 >= r5) goto L2e
            android.view.View r5 = r10.getChildAt(r4)
            boolean r6 = r5 instanceof org.telegram.ui.Components.StickerCategoriesListView.CategoryButton
            if (r6 == 0) goto L2b
            int r6 = r5.getLeft()
            int r0 = java.lang.Math.min(r0, r6)
            int r5 = r5.getRight()
            int r3 = java.lang.Math.max(r3, r5)
        L2b:
            int r4 = r4 + 1
            goto Ld
        L2e:
            if (r0 >= r3) goto L89
            float r0 = (float) r0
            int r4 = r10.getWidth()
            r5 = 1107296256(0x42000000, float:32.0)
            int r6 = org.telegram.messenger.AndroidUtilities.m1146dp(r5)
            int r4 = r4 + r6
            float r4 = (float) r4
            float r6 = r10.categoriesShownT
            float r6 = r1 - r6
            float r4 = r4 * r6
            float r0 = r0 + r4
            int r0 = (int) r0
            float r3 = (float) r3
            int r4 = r10.getWidth()
            int r5 = org.telegram.messenger.AndroidUtilities.m1146dp(r5)
            int r4 = r4 + r5
            float r4 = (float) r4
            float r5 = r10.categoriesShownT
            float r5 = r1 - r5
            float r4 = r4 * r5
            float r3 = r3 + r4
            int r3 = (int) r3
            float r5 = (float) r0
            float r7 = (float) r3
            int r0 = r10.getHeight()
            float r8 = (float) r0
            android.graphics.Paint r9 = r10.backgroundPaint
            r6 = 0
            r4 = r11
            r4.drawRect(r5, r6, r7, r8, r9)
            int r11 = r10.getWidth()
            if (r3 >= r11) goto L8a
            android.graphics.drawable.Drawable r11 = r10.leftBoundDrawable
            if (r11 == 0) goto L8a
            r0 = 255(0xff, float:3.57E-43)
            r11.setAlpha(r0)
            android.graphics.drawable.Drawable r11 = r10.leftBoundDrawable
            int r0 = r11.getIntrinsicWidth()
            int r0 = r0 + r3
            int r5 = r10.getHeight()
            r11.setBounds(r3, r2, r0, r5)
            android.graphics.drawable.Drawable r11 = r10.leftBoundDrawable
            r11.draw(r4)
            goto L8a
        L89:
            r4 = r11
        L8a:
            r10.drawSelectedHighlight(r4)
            super.draw(r4)
            android.graphics.drawable.Drawable r11 = r10.leftBoundDrawable
            if (r11 == 0) goto Lcd
            org.telegram.ui.Components.AnimatedFloat r0 = r10.leftBoundAlpha
            r3 = -1
            boolean r3 = r10.canScrollHorizontally(r3)
            if (r3 == 0) goto La2
            boolean r3 = r10.scrolledFully
            if (r3 == 0) goto La2
            goto La3
        La2:
            r1 = 0
        La3:
            float r0 = r0.set(r1)
            r1 = 1132396544(0x437f0000, float:255.0)
            float r1 = r1 * r0
            float r0 = r10.categoriesShownT
            float r1 = r1 * r0
            int r0 = (int) r1
            r11.setAlpha(r0)
            android.graphics.drawable.Drawable r11 = r10.leftBoundDrawable
            int r11 = r11.getAlpha()
            if (r11 <= 0) goto Lcd
            android.graphics.drawable.Drawable r11 = r10.leftBoundDrawable
            int r0 = r11.getIntrinsicWidth()
            int r1 = r10.getHeight()
            r11.setBounds(r2, r2, r0, r1)
            android.graphics.drawable.Drawable r11 = r10.leftBoundDrawable
            r11.draw(r4)
        Lcd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.StickerCategoriesListView.draw(android.graphics.Canvas):void");
    }

    private void drawSelectedHighlight(Canvas canvas) {
        float f = this.selectedAlpha.set(this.selectedCategoryIndex >= 0 ? 1.0f : 0.0f);
        int i = this.selectedCategoryIndex;
        float f2 = i >= 0 ? this.selectedIndex.set(i) : this.selectedIndex.get();
        if (f <= 0.0f) {
            return;
        }
        float f3 = f2 + 1.0f;
        double d = f3;
        int iMax = Math.max(1, (int) Math.floor(d));
        int iMax2 = Math.max(1, (int) Math.ceil(d));
        View view = null;
        View view2 = null;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            int childAdapterPosition = getChildAdapterPosition(childAt);
            if (childAdapterPosition == iMax) {
                view = childAt;
            }
            if (childAdapterPosition == iMax2) {
                view2 = childAt;
            }
            if (view != null && view2 != null) {
                break;
            }
        }
        int alpha = this.selectedPaint.getAlpha();
        this.selectedPaint.setAlpha((int) (alpha * f));
        if (view != null && view2 != null) {
            float f4 = iMax == iMax2 ? 0.5f : (f3 - iMax) / (iMax2 - iMax);
            getChildBounds(view, this.rect1);
            getChildBounds(view2, this.rect2);
            AndroidUtilities.lerp(this.rect1, this.rect2, f4, this.rect3);
            canvas.drawRoundRect(this.rect3, AndroidUtilities.m1146dp(15.0f), AndroidUtilities.m1146dp(15.0f), this.selectedPaint);
        }
        this.selectedPaint.setAlpha(alpha);
    }

    private void getChildBounds(View view, RectF rectF) {
        float right = (view.getRight() + view.getLeft()) / 2.0f;
        float bottom = (view.getBottom() + view.getTop()) / 2.0f;
        float width = ((view.getWidth() / 2.0f) - AndroidUtilities.m1146dp(1.0f)) * (view instanceof CategoryButton ? ((CategoryButton) view).getScale() : 1.0f);
        rectF.set(right - width, bottom - width, right + width, bottom + width);
    }

    @Override // org.telegram.p023ui.Components.RecyclerListView, android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            View viewFindChildViewUnder = findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if (!(viewFindChildViewUnder instanceof CategoryButton) || viewFindChildViewUnder.getAlpha() < 0.5f) {
                return false;
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private class Adapter extends RecyclerListView.SelectionAdapter {
        private int lastItemCount;

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            return i == 0 ? 0 : 1;
        }

        private Adapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View categoryButton;
            if (i == 0) {
                StickerCategoriesListView stickerCategoriesListView = StickerCategoriesListView.this;
                categoryButton = new View(StickerCategoriesListView.this.getContext()) { // from class: org.telegram.ui.Components.StickerCategoriesListView.Adapter.1
                    @Override // android.view.View
                    protected void onMeasure(int i2, int i3) {
                        int size = View.MeasureSpec.getSize(i2);
                        if (size <= 0) {
                            size = ((View) getParent()).getMeasuredWidth();
                        }
                        int size2 = View.MeasureSpec.getSize(i3) - AndroidUtilities.m1146dp(4.0f);
                        StickerCategoriesListView stickerCategoriesListView2 = StickerCategoriesListView.this;
                        int iMax = Math.max(stickerCategoriesListView2.dontOccupyWidth > 0 ? StickerCategoriesListView.this.dontOccupyWidth + AndroidUtilities.m1146dp(4.0f) : 0, (int) (size - Math.min(((Adapter.this.getItemCount() - 1) * size2) + AndroidUtilities.m1146dp(4.0f), StickerCategoriesListView.this.shownButtonsAtStart * size2)));
                        stickerCategoriesListView2.paddingWidth = iMax;
                        super.onMeasure(View.MeasureSpec.makeMeasureSpec(iMax, TLObject.FLAG_30), i3);
                    }
                };
                stickerCategoriesListView.paddingView = categoryButton;
            } else {
                StickerCategoriesListView stickerCategoriesListView2 = StickerCategoriesListView.this;
                categoryButton = stickerCategoriesListView2.new CategoryButton(stickerCategoriesListView2.getContext());
            }
            return new RecyclerListView.Holder(categoryButton);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder.getItemViewType() != 1 || StickerCategoriesListView.this.categories == null) {
                return;
            }
            int i2 = i - 1;
            EmojiCategory emojiCategory = StickerCategoriesListView.this.categories[i2];
            CategoryButton categoryButton = (CategoryButton) viewHolder.itemView;
            categoryButton.set(emojiCategory, i2, StickerCategoriesListView.this.selectedCategoryIndex == i2);
            categoryButton.setAlpha(StickerCategoriesListView.this.categoriesShownT);
            categoryButton.setScaleX(StickerCategoriesListView.this.categoriesShownT);
            categoryButton.setScaleY(StickerCategoriesListView.this.categoriesShownT);
            categoryButton.play(false);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewAttachedToWindow(RecyclerView.ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() == 1) {
                CategoryButton categoryButton = (CategoryButton) viewHolder.itemView;
                categoryButton.setSelected(StickerCategoriesListView.this.selectedCategoryIndex == viewHolder.getAdapterPosition() - 1, false);
                categoryButton.play(false);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            int length = (StickerCategoriesListView.this.categories == null ? 0 : StickerCategoriesListView.this.categories.length) + 1;
            if (length != this.lastItemCount) {
                if (StickerCategoriesListView.this.paddingView != null) {
                    StickerCategoriesListView.this.paddingView.requestLayout();
                }
                this.lastItemCount = length;
            }
            return length;
        }

        @Override // org.telegram.ui.Components.RecyclerListView.SelectionAdapter
        public boolean isEnabled(RecyclerView.ViewHolder viewHolder) {
            return viewHolder.getItemViewType() == 1;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getGlassIconColor(float f) {
        return ColorUtils.setAlphaComponent(Theme.getColor(Theme.key_glass_defaultIcon, this.resourcesProvider), (int) (f * 255.0f));
    }

    /* JADX INFO: Access modifiers changed from: private */
    class CategoryButton extends RLottieImageView {
        ValueAnimator backAnimator;
        private int imageColor;
        private int index;
        private long lastPlayed;
        ValueAnimator loadAnimator;
        float loadProgress;
        private boolean loaded;
        float pressedProgress;
        private ValueAnimator selectedAnimator;
        private float selectedT;

        public CategoryButton(Context context) {
            super(context);
            this.loaded = false;
            this.loadProgress = 1.0f;
            setImageColor(StickerCategoriesListView.this.isGlassDesign ? StickerCategoriesListView.this.getGlassIconColor(0.4f) : StickerCategoriesListView.this.getThemedColor(Theme.key_chat_emojiPanelIcon));
            setScaleType(ImageView.ScaleType.CENTER);
            setLayerNum(StickerCategoriesListView.this.layerNum);
        }

        public void set(EmojiCategory emojiCategory, int i, boolean z) {
            this.index = i;
            ValueAnimator valueAnimator = this.loadAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
                this.loadAnimator = null;
            }
            if (emojiCategory.remote) {
                setImageResource(0);
                clearAnimationDrawable();
                final boolean zIsTabIconsAnimationEnabled = StickerCategoriesListView.this.isTabIconsAnimationEnabled(true);
                this.loaded = false;
                this.loadProgress = 1.0f;
                AnimatedEmojiDrawable.getDocumentFetcher(UserConfig.selectedAccount).fetchDocument(emojiCategory.documentId, new AnimatedEmojiDrawable.ReceivedDocument() { // from class: org.telegram.ui.Components.StickerCategoriesListView$CategoryButton$$ExternalSyntheticLambda1
                    @Override // org.telegram.ui.Components.AnimatedEmojiDrawable.ReceivedDocument
                    public final void run(TLRPC.Document document) {
                        this.f$0.lambda$set$0(zIsTabIconsAnimationEnabled, document);
                    }
                });
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.StickerCategoriesListView$CategoryButton$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$set$1();
                    }
                }, 60L);
            } else if (emojiCategory.animated) {
                this.cached = false;
                setImageResource(0);
                setAnimation(emojiCategory.iconResId, 24, 24);
                playAnimation();
                this.loadProgress = 1.0f;
            } else {
                clearAnimationDrawable();
                setImageResource(emojiCategory.iconResId);
                this.loadProgress = 1.0f;
            }
            setSelected(z, false);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$set$0(boolean z, TLRPC.Document document) {
            setOnlyLastFrame(!z);
            setAnimation(document, 24, 24);
            playAnimation();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$set$1() {
            if (this.loaded) {
                return;
            }
            this.loadProgress = 0.0f;
        }

        @Override // org.telegram.p023ui.Components.RLottieImageView
        protected void onLoaded() {
            this.loaded = true;
            if (this.loadProgress < 1.0f) {
                ValueAnimator valueAnimator = this.loadAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    this.loadAnimator = null;
                }
                ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.loadProgress, 1.0f);
                this.loadAnimator = valueAnimatorOfFloat;
                valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.StickerCategoriesListView$CategoryButton$$ExternalSyntheticLambda4
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        this.f$0.lambda$onLoaded$2(valueAnimator2);
                    }
                });
                this.loadAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.StickerCategoriesListView.CategoryButton.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        CategoryButton categoryButton = CategoryButton.this;
                        categoryButton.loadProgress = 1.0f;
                        categoryButton.invalidate();
                        CategoryButton.this.loadAnimator = null;
                    }
                });
                this.loadAnimator.setDuration(320L);
                this.loadAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                this.loadAnimator.start();
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLoaded$2(ValueAnimator valueAnimator) {
            this.loadProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            invalidate();
        }

        public void setSelected(boolean z, boolean z2) {
            if (Math.abs(this.selectedT - (z ? 1.0f : 0.0f)) > 0.01f) {
                ValueAnimator valueAnimator = this.selectedAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    this.selectedAnimator = null;
                }
                if (z2) {
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.selectedT, z ? 1.0f : 0.0f);
                    this.selectedAnimator = valueAnimatorOfFloat;
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.StickerCategoriesListView$CategoryButton$$ExternalSyntheticLambda3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            this.f$0.lambda$setSelected$3(valueAnimator2);
                        }
                    });
                    this.selectedAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.StickerCategoriesListView.CategoryButton.2
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            CategoryButton categoryButton = CategoryButton.this;
                            categoryButton.updateSelectedT(((Float) categoryButton.selectedAnimator.getAnimatedValue()).floatValue());
                            CategoryButton.this.selectedAnimator = null;
                        }
                    });
                    this.selectedAnimator.setDuration(350L);
                    this.selectedAnimator.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                    this.selectedAnimator.start();
                    return;
                }
                updateSelectedT(z ? 1.0f : 0.0f);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSelected$3(ValueAnimator valueAnimator) {
            updateSelectedT(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateSelectedT(float f) {
            this.selectedT = f;
            StickerCategoriesListView stickerCategoriesListView = StickerCategoriesListView.this;
            if (stickerCategoriesListView.isGlassDesign) {
                setImageColor(stickerCategoriesListView.getGlassIconColor(AndroidUtilities.lerp(0.4f, 0.8f, f)));
            } else {
                setImageColor(ColorUtils.blendARGB(stickerCategoriesListView.getThemedColor(Theme.key_chat_emojiPanelIcon), StickerCategoriesListView.this.getThemedColor(Theme.key_chat_emojiPanelIconSelected), this.selectedT));
            }
            invalidate();
        }

        public void setImageColor(int i) {
            if (this.imageColor != i) {
                this.imageColor = i;
                setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.MULTIPLY));
            }
        }

        @Override // android.view.View
        public void draw(Canvas canvas) {
            updatePressedProgress();
            float scale = getScale();
            if (scale != 1.0f) {
                canvas.save();
                canvas.scale(scale, scale, getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f);
            }
            super.draw(canvas);
            if (scale != 1.0f) {
                canvas.restore();
            }
        }

        @Override // android.widget.ImageView, android.view.View
        protected void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i2);
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(size - AndroidUtilities.m1146dp(4.0f), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30));
        }

        public void play(boolean z) {
            if (System.currentTimeMillis() - this.lastPlayed > 250 || z) {
                this.lastPlayed = System.currentTimeMillis();
                RLottieDrawable animatedDrawable = getAnimatedDrawable();
                if (animatedDrawable == null && getImageReceiver() != null) {
                    animatedDrawable = getImageReceiver().getLottieAnimation();
                }
                if (animatedDrawable != null) {
                    animatedDrawable.stop();
                    animatedDrawable.setCurrentFrame(0);
                    animatedDrawable.restart(true);
                } else if (animatedDrawable == null) {
                    setProgress(0.0f);
                    playAnimation();
                }
            }
        }

        public void updatePressedProgress() {
            if (isPressed()) {
                float f = this.pressedProgress;
                if (f != 1.0f) {
                    this.pressedProgress = Utilities.clamp(f + ((1000.0f / AndroidUtilities.screenRefreshRate) / 100.0f), 1.0f, 0.0f);
                    invalidate();
                    StickerCategoriesListView.this.invalidate();
                }
            }
        }

        public float getScale() {
            return (((1.0f - this.pressedProgress) * 0.15f) + 0.85f) * this.loadProgress;
        }

        @Override // android.view.View
        public void setPressed(boolean z) {
            ValueAnimator valueAnimator;
            if (isPressed() != z) {
                super.setPressed(z);
                invalidate();
                StickerCategoriesListView.this.invalidate();
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
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: org.telegram.ui.Components.StickerCategoriesListView$CategoryButton$$ExternalSyntheticLambda0
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                            this.f$0.lambda$setPressed$4(valueAnimator2);
                        }
                    });
                    this.backAnimator.addListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Components.StickerCategoriesListView.CategoryButton.3
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            super.onAnimationEnd(animator);
                            CategoryButton.this.backAnimator = null;
                        }
                    });
                    this.backAnimator.setInterpolator(new OvershootInterpolator(3.0f));
                    this.backAnimator.setDuration(350L);
                    this.backAnimator.start();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setPressed$4(ValueAnimator valueAnimator) {
            this.pressedProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            invalidate();
        }
    }

    public static class EmojiCategory {
        public boolean animated;
        public long documentId;
        public String emojis;
        public boolean greeting;
        public int iconResId;
        public boolean premium;
        public boolean remote;
        public String title;

        public static EmojiCategory remote(TLRPC.EmojiGroup emojiGroup) {
            EmojiCategory emojiCategory = new EmojiCategory();
            emojiCategory.remote = true;
            emojiCategory.documentId = emojiGroup.icon_emoji_id;
            if (emojiGroup instanceof TLRPC.TL_emojiGroupPremium) {
                emojiCategory.emojis = "premium";
                emojiCategory.premium = true;
            } else {
                emojiCategory.emojis = TextUtils.concat((CharSequence[]) emojiGroup.emoticons.toArray(new String[0])).toString();
            }
            emojiCategory.greeting = emojiGroup instanceof TLRPC.TL_emojiGroupGreeting;
            emojiCategory.title = emojiGroup.title;
            return emojiCategory;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class EmojiGroupFetcher extends CacheFetcher {
        private EmojiGroupFetcher() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.telegram.messenger.CacheFetcher
        public void getRemote(int i, Integer num, long j, final Utilities.Callback4 callback4) {
            TLRPC.TL_messages_getEmojiGroups tL_messages_getEmojiGroups;
            if (num.intValue() == 1) {
                TLRPC.TL_messages_getEmojiStatusGroups tL_messages_getEmojiStatusGroups = new TLRPC.TL_messages_getEmojiStatusGroups();
                tL_messages_getEmojiStatusGroups.hash = (int) j;
                tL_messages_getEmojiGroups = tL_messages_getEmojiStatusGroups;
            } else if (num.intValue() == 2) {
                TLRPC.TL_messages_getEmojiProfilePhotoGroups tL_messages_getEmojiProfilePhotoGroups = new TLRPC.TL_messages_getEmojiProfilePhotoGroups();
                tL_messages_getEmojiProfilePhotoGroups.hash = (int) j;
                tL_messages_getEmojiGroups = tL_messages_getEmojiProfilePhotoGroups;
            } else if (num.intValue() == 3) {
                TLRPC.TL_messages_getEmojiStickerGroups tL_messages_getEmojiStickerGroups = new TLRPC.TL_messages_getEmojiStickerGroups();
                tL_messages_getEmojiStickerGroups.hash = (int) j;
                tL_messages_getEmojiGroups = tL_messages_getEmojiStickerGroups;
            } else {
                TLRPC.TL_messages_getEmojiGroups tL_messages_getEmojiGroups2 = new TLRPC.TL_messages_getEmojiGroups();
                tL_messages_getEmojiGroups2.hash = (int) j;
                tL_messages_getEmojiGroups = tL_messages_getEmojiGroups2;
            }
            ConnectionsManager.getInstance(i).sendRequest(tL_messages_getEmojiGroups, new RequestDelegate() { // from class: org.telegram.ui.Components.StickerCategoriesListView$EmojiGroupFetcher$$ExternalSyntheticLambda1
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    StickerCategoriesListView.EmojiGroupFetcher.$r8$lambda$hoVrwIB0C7JCXIxZyF899CAsQ48(callback4, tLObject, tL_error);
                }
            });
        }

        public static /* synthetic */ void $r8$lambda$hoVrwIB0C7JCXIxZyF899CAsQ48(Utilities.Callback4 callback4, TLObject tLObject, TLRPC.TL_error tL_error) {
            if (tLObject instanceof TLRPC.TL_messages_emojiGroupsNotModified) {
                Boolean bool = Boolean.TRUE;
                callback4.run(bool, null, 0L, bool);
            } else if (!(tLObject instanceof TLRPC.TL_messages_emojiGroups)) {
                callback4.run(Boolean.FALSE, null, 0L, Boolean.TRUE);
            } else {
                callback4.run(Boolean.FALSE, (TLRPC.TL_messages_emojiGroups) tLObject, Long.valueOf(r3.hash), Boolean.TRUE);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.telegram.messenger.CacheFetcher
        public void getLocal(final int i, final Integer num, final Utilities.Callback2 callback2) {
            MessagesStorage.getInstance(i).getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.ui.Components.StickerCategoriesListView$EmojiGroupFetcher$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() throws Throwable {
                    StickerCategoriesListView.EmojiGroupFetcher.$r8$lambda$d9VgXuQKrOQ1qkFLkDr7Mpark10(i, num, callback2);
                }
            });
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:37:0x006a  */
        /* JADX WARN: Removed duplicated region for block: B:48:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public static /* synthetic */ void $r8$lambda$d9VgXuQKrOQ1qkFLkDr7Mpark10(int r6, java.lang.Integer r7, org.telegram.messenger.Utilities.Callback2 r8) throws java.lang.Throwable {
            /*
                r0 = 0
                java.lang.Long r0 = java.lang.Long.valueOf(r0)
                r1 = 0
                org.telegram.messenger.MessagesStorage r6 = org.telegram.messenger.MessagesStorage.getInstance(r6)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
                org.telegram.SQLite.SQLiteDatabase r6 = r6.getDatabase()     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
                if (r6 == 0) goto L5c
                java.lang.String r2 = "SELECT data FROM emoji_groups WHERE type = ?"
                r3 = 1
                java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
                r5 = 0
                r4[r5] = r7     // Catch: java.lang.Throwable -> L56 java.lang.Exception -> L59
                org.telegram.SQLite.SQLiteCursor r6 = r6.queryFinalized(r2, r4)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
                boolean r7 = r6.next()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
                if (r7 == 0) goto L3a
                org.telegram.tgnet.NativeByteBuffer r7 = r6.byteBufferValue(r5)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
                if (r7 == 0) goto L3a
                int r2 = r7.readInt32(r5)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
                org.telegram.tgnet.TLRPC$messages_EmojiGroups r2 = org.telegram.tgnet.TLRPC.messages_EmojiGroups.TLdeserialize(r7, r2, r3)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
                r7.reuse()     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
                goto L3b
            L35:
                r7 = move-exception
                r1 = r6
                goto L6e
            L38:
                r7 = move-exception
                goto L62
            L3a:
                r2 = r1
            L3b:
                boolean r7 = r2 instanceof org.telegram.tgnet.TLRPC.TL_messages_emojiGroups     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
                if (r7 != 0) goto L43
                r8.run(r0, r1)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
                goto L4f
            L43:
                org.telegram.tgnet.TLRPC$TL_messages_emojiGroups r2 = (org.telegram.tgnet.TLRPC.TL_messages_emojiGroups) r2     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
                int r7 = r2.hash     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
                long r3 = (long) r7     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
                java.lang.Long r7 = java.lang.Long.valueOf(r3)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
                r8.run(r7, r2)     // Catch: java.lang.Throwable -> L35 java.lang.Exception -> L38
            L4f:
                r1 = r6
                goto L5c
            L51:
                r7 = move-exception
                goto L6e
            L53:
                r7 = move-exception
            L54:
                r6 = r1
                goto L62
            L56:
                r6 = move-exception
                r7 = r6
                goto L6e
            L59:
                r6 = move-exception
                r7 = r6
                goto L54
            L5c:
                if (r1 == 0) goto L6d
                r1.dispose()
                return
            L62:
                org.telegram.messenger.FileLog.m1160e(r7)     // Catch: java.lang.Throwable -> L35
                r8.run(r0, r1)     // Catch: java.lang.Throwable -> L35
                if (r6 == 0) goto L6d
                r6.dispose()
            L6d:
                return
            L6e:
                if (r1 == 0) goto L73
                r1.dispose()
            L73:
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.StickerCategoriesListView.EmojiGroupFetcher.$r8$lambda$d9VgXuQKrOQ1qkFLkDr7Mpark10(int, java.lang.Integer, org.telegram.messenger.Utilities$Callback2):void");
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.telegram.messenger.CacheFetcher
        public void setLocal(final int i, final Integer num, final TLRPC.TL_messages_emojiGroups tL_messages_emojiGroups, long j) {
            MessagesStorage.getInstance(i).getStorageQueue().postRunnable(new Runnable() { // from class: org.telegram.ui.Components.StickerCategoriesListView$EmojiGroupFetcher$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    StickerCategoriesListView.EmojiGroupFetcher.$r8$lambda$ItTxN5yQgcyJ0ciauXDJGTpsOXk(i, tL_messages_emojiGroups, num);
                }
            });
        }

        public static /* synthetic */ void $r8$lambda$ItTxN5yQgcyJ0ciauXDJGTpsOXk(int i, TLRPC.TL_messages_emojiGroups tL_messages_emojiGroups, Integer num) {
            try {
                SQLiteDatabase database = MessagesStorage.getInstance(i).getDatabase();
                if (database != null) {
                    if (tL_messages_emojiGroups == null) {
                        database.executeFast("DELETE FROM emoji_groups WHERE type = " + num).stepThis().dispose();
                        return;
                    }
                    SQLitePreparedStatement sQLitePreparedStatementExecuteFast = database.executeFast("REPLACE INTO emoji_groups VALUES(?, ?)");
                    sQLitePreparedStatementExecuteFast.requery();
                    NativeByteBuffer nativeByteBuffer = new NativeByteBuffer(tL_messages_emojiGroups.getObjectSize());
                    tL_messages_emojiGroups.serializeToStream(nativeByteBuffer);
                    sQLitePreparedStatementExecuteFast.bindInteger(1, num.intValue());
                    sQLitePreparedStatementExecuteFast.bindByteBuffer(2, nativeByteBuffer);
                    sQLitePreparedStatementExecuteFast.step();
                    nativeByteBuffer.reuse();
                    sQLitePreparedStatementExecuteFast.dispose();
                }
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class EmojiSearch extends CacheFetcher {
        private EmojiSearch() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // org.telegram.messenger.CacheFetcher
        public void getRemote(int i, String str, long j, final Utilities.Callback4 callback4) {
            TLRPC.TL_messages_searchCustomEmoji tL_messages_searchCustomEmoji = new TLRPC.TL_messages_searchCustomEmoji();
            tL_messages_searchCustomEmoji.emoticon = str;
            tL_messages_searchCustomEmoji.hash = j;
            ConnectionsManager.getInstance(i).sendRequest(tL_messages_searchCustomEmoji, new RequestDelegate() { // from class: org.telegram.ui.Components.StickerCategoriesListView$EmojiSearch$$ExternalSyntheticLambda0
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    StickerCategoriesListView.EmojiSearch.m11706$r8$lambda$S8qTPEYrUlxmW8suBrCSOYi_S0(callback4, tLObject, tL_error);
                }
            });
        }

        /* renamed from: $r8$lambda$S8qTPEYrUlxmW8suBr-CSOYi_S0, reason: not valid java name */
        public static /* synthetic */ void m11706$r8$lambda$S8qTPEYrUlxmW8suBrCSOYi_S0(Utilities.Callback4 callback4, TLObject tLObject, TLRPC.TL_error tL_error) {
            if (tLObject instanceof TLRPC.TL_emojiListNotModified) {
                Boolean bool = Boolean.TRUE;
                callback4.run(bool, null, 0L, bool);
            } else if (!(tLObject instanceof TLRPC.TL_emojiList)) {
                callback4.run(Boolean.FALSE, null, 0L, Boolean.TRUE);
            } else {
                TLRPC.TL_emojiList tL_emojiList = (TLRPC.TL_emojiList) tLObject;
                callback4.run(Boolean.FALSE, tL_emojiList, Long.valueOf(tL_emojiList.hash), Boolean.TRUE);
            }
        }
    }
}
