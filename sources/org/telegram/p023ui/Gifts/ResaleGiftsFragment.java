package org.telegram.p023ui.Gifts;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.p023ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p023ui.ActionBar.BackDrawable;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.INavigationLayout;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.BackupImageView;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.ColoredImageSpan;
import org.telegram.p023ui.Components.CubicBezierInterpolator;
import org.telegram.p023ui.Components.EditTextCaption;
import org.telegram.p023ui.Components.FireworksOverlay;
import org.telegram.p023ui.Components.FlickerLoadingView;
import org.telegram.p023ui.Components.ItemOptions;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.LinkSpanDrawable;
import org.telegram.p023ui.Components.RLottieDrawable;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.p023ui.Components.SizeNotifierFrameLayout;
import org.telegram.p023ui.Components.TypefaceSpan;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import org.telegram.p023ui.Gifts.GiftSheet;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.p023ui.Stars.StarGiftSheet;
import org.telegram.p023ui.Stars.StarsController;
import org.telegram.p023ui.Stars.StarsIntroActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_stars;

/* loaded from: classes6.dex */
public class ResaleGiftsFragment extends BaseFragment {
    private BackDrawable backDrawable;
    private Filter backdropButton;
    private TextView clearFiltersButton;
    private FrameLayout clearFiltersContainer;
    private Runnable closeParentSheet;
    private final long dialogId;
    private LargeEmptyView emptyView;
    private boolean emptyViewVisible;
    private HorizontalScrollView filterScrollView;
    private LinearLayout filtersContainer;
    private View filtersDivider;
    private FireworksOverlay fireworksOverlay;
    private final long gift_id;
    private final String gift_name;
    private final ResaleGiftsList list;
    private UniversalRecyclerView listView;
    private Filter modelButton;
    private Filter patternButton;
    private Filter sortButton;
    private boolean filtersShown = true;
    private boolean clearFiltersShown = true;

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onItemLongClick(UItem uItem, View view, int i, float f, float f2) {
        return false;
    }

    public ResaleGiftsFragment(long j, String str, long j2, Theme.ResourcesProvider resourcesProvider) {
        this.dialogId = j;
        this.gift_name = str;
        this.gift_id = j2;
        this.resourceProvider = resourcesProvider;
        ResaleGiftsList resaleGiftsList = new ResaleGiftsList(this.currentAccount, j2, new Utilities.Callback() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.updateList(((Boolean) obj).booleanValue());
            }
        });
        this.list = resaleGiftsList;
        resaleGiftsList.load();
    }

    public ResaleGiftsFragment setCloseParentSheet(Runnable runnable) {
        this.closeParentSheet = runnable;
        return this;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(final Context context) {
        ActionBar actionBar = this.actionBar;
        BackDrawable backDrawable = new BackDrawable(false);
        this.backDrawable = backDrawable;
        actionBar.setBackButtonDrawable(backDrawable);
        this.backDrawable.setAnimationTime(240.0f);
        this.actionBar.setCastShadows(false);
        this.actionBar.setAddToContainer(false);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    ResaleGiftsFragment.this.lambda$onBackPressed$371();
                }
            }
        });
        this.actionBar.setTitle(this.gift_name);
        ActionBar actionBar2 = this.actionBar;
        int i = Theme.key_windowBackgroundWhite;
        actionBar2.setBackgroundColor(getThemedColor(i));
        ActionBar actionBar3 = this.actionBar;
        int i2 = Theme.key_windowBackgroundWhiteBlackText;
        actionBar3.setItemsColor(getThemedColor(i2), false);
        this.actionBar.setItemsColor(getThemedColor(i2), true);
        this.actionBar.setItemsBackgroundColor(getThemedColor(Theme.key_actionBarActionModeDefaultSelector), false);
        this.actionBar.setTitleColor(getThemedColor(i2));
        this.actionBar.setSubtitleColor(getThemedColor(Theme.key_windowBackgroundWhiteGrayText2));
        SizeNotifierFrameLayout sizeNotifierFrameLayout = new SizeNotifierFrameLayout(context) { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.2
            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i3, int i4) {
                ((FrameLayout.LayoutParams) ResaleGiftsFragment.this.filterScrollView.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight() + (((BaseFragment) ResaleGiftsFragment.this).actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0);
                ((FrameLayout.LayoutParams) ResaleGiftsFragment.this.filtersDivider.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight() + (((BaseFragment) ResaleGiftsFragment.this).actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) + AndroidUtilities.m1146dp(47.0f);
                ((FrameLayout.LayoutParams) ResaleGiftsFragment.this.listView.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight() + (((BaseFragment) ResaleGiftsFragment.this).actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0);
                ((FrameLayout.LayoutParams) ResaleGiftsFragment.this.emptyView.getLayoutParams()).topMargin = ActionBar.getCurrentActionBarHeight() + (((BaseFragment) ResaleGiftsFragment.this).actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0);
                super.onMeasure(i3, i4);
            }
        };
        int iBlendOver = Theme.blendOver(Theme.getColor(i, this.resourceProvider), Theme.multAlpha(Theme.getColor(i2, this.resourceProvider), 0.04f));
        sizeNotifierFrameLayout.setBackgroundColor(iBlendOver);
        this.fragmentView = sizeNotifierFrameLayout;
        final StarsIntroActivity.StarsBalanceView starsBalanceView = new StarsIntroActivity.StarsBalanceView(context, this.currentAccount, this.resourceProvider);
        ScaleStateListAnimator.apply(starsBalanceView);
        starsBalanceView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$0(starsBalanceView, view);
            }
        });
        this.actionBar.addView(starsBalanceView, LayoutHelper.createFrame(-2, -2.0f, 85, 0.0f, 0.0f, 4.0f, 0.0f));
        UniversalRecyclerView universalRecyclerView = new UniversalRecyclerView(this, new Utilities.Callback2() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda2
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, new Utilities.Callback5() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda3
            @Override // org.telegram.messenger.Utilities.Callback5
            public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                this.f$0.onItemClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
            }
        }, new Utilities.Callback5Return() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda4
            @Override // org.telegram.messenger.Utilities.Callback5Return
            public final Object run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                return Boolean.valueOf(this.f$0.onItemLongClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue()));
            }
        }) { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.3
            @Override // org.telegram.p023ui.Components.RecyclerListView
            public Integer getSelectorColor(int i3) {
                return 0;
            }
        };
        this.listView = universalRecyclerView;
        universalRecyclerView.adapter.setApplyBackground(false);
        this.listView.setSpanCount(3);
        this.listView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.4
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i3, int i4) {
                if (ResaleGiftsFragment.this.isLoadingVisible()) {
                    ResaleGiftsFragment.this.list.load();
                }
                ResaleGiftsFragment.this.filtersDivider.animate().alpha((ResaleGiftsFragment.this.filtersShown && ResaleGiftsFragment.this.listView.canScrollVertically(-1)) ? 1.0f : 0.0f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(320L).start();
            }
        });
        this.listView.setPadding(0, AndroidUtilities.m1146dp(45.0f), 0, AndroidUtilities.m1146dp(101.0f));
        this.listView.setClipToPadding(false);
        sizeNotifierFrameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f, Opcodes.DNEG, 7.33f, 0.0f, 7.33f, -45.0f));
        sizeNotifierFrameLayout.addView(this.actionBar);
        LargeEmptyView largeEmptyView = new LargeEmptyView(context, new View.OnClickListener() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$1(view);
            }
        }, this.resourceProvider);
        this.emptyView = largeEmptyView;
        this.emptyViewVisible = false;
        largeEmptyView.setAlpha(0.0f);
        this.emptyView.setScaleX(0.95f);
        this.emptyView.setScaleY(0.95f);
        this.emptyView.setVisibility(8);
        sizeNotifierFrameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f, Opcodes.DNEG, 0.0f, 0.0f, 0.0f, -45.0f));
        LinearLayout linearLayout = new LinearLayout(context);
        this.filtersContainer = linearLayout;
        linearLayout.setPadding(AndroidUtilities.m1146dp(11.0f), 0, AndroidUtilities.m1146dp(11.0f), 0);
        this.filtersContainer.setOrientation(0);
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(context);
        this.filterScrollView = horizontalScrollView;
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        this.filterScrollView.addView(this.filtersContainer);
        this.filterScrollView.setBackgroundColor(iBlendOver);
        sizeNotifierFrameLayout.addView(this.filterScrollView, LayoutHelper.createFrame(-1, 47, 55));
        View view = new View(context);
        this.filtersDivider = view;
        int i3 = Theme.key_divider;
        view.setBackgroundColor(getThemedColor(i3));
        this.filtersDivider.setAlpha(0.0f);
        sizeNotifierFrameLayout.addView(this.filtersDivider, LayoutHelper.createFrame(-1.0f, 2.0f / AndroidUtilities.density, 55));
        FrameLayout frameLayout = new FrameLayout(context);
        this.clearFiltersContainer = frameLayout;
        sizeNotifierFrameLayout.addView(frameLayout, LayoutHelper.createFrame(-1, 49, 87));
        this.clearFiltersButton = new TextView(context);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("x");
        spannableStringBuilder.setSpan(new ColoredImageSpan(C2369R.drawable.msg_clearcache), 0, 1, 33);
        spannableStringBuilder.append((CharSequence) " ").append((CharSequence) LocaleController.getString(C2369R.string.Gift2ResaleFiltersClear));
        this.clearFiltersButton.setText(spannableStringBuilder);
        TextView textView = this.clearFiltersButton;
        int i4 = Theme.key_featuredStickers_addButton;
        textView.setTextColor(getThemedColor(i4));
        this.clearFiltersButton.setTypeface(AndroidUtilities.bold());
        this.clearFiltersButton.setBackground(Theme.createSimpleSelectorRoundRectDrawable(0, getThemedColor(i), Theme.blendOver(getThemedColor(i), Theme.multAlpha(getThemedColor(i4), 0.1f))));
        this.clearFiltersButton.setGravity(17);
        this.clearFiltersButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.f$0.lambda$createView$2(view2);
            }
        });
        this.clearFiltersContainer.addView(this.clearFiltersButton, LayoutHelper.createFrame(-1, -1, Opcodes.DNEG));
        View view2 = new View(context);
        view2.setBackgroundColor(getThemedColor(i3));
        this.clearFiltersContainer.addView(view2, LayoutHelper.createFrame(-1.0f, 1.0f / AndroidUtilities.density, 55));
        setClearFiltersShown(false, false);
        Filter filter = new Filter(context, this.resourceProvider);
        this.sortButton = filter;
        filter.setSorting(this.list.getSorting());
        this.filtersContainer.addView(this.sortButton, LayoutHelper.createLinear(-2, -2, 16, 0, 0, 6, 0));
        this.sortButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$createView$6(view3);
            }
        });
        Filter filter2 = new Filter(context, this.resourceProvider);
        this.modelButton = filter2;
        filter2.setValue(LocaleController.getString(C2369R.string.Gift2AttributeModel));
        this.filtersContainer.addView(this.modelButton, LayoutHelper.createLinear(-2, -2, 16, 0, 0, 6, 0));
        this.modelButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$createView$12(context, view3);
            }
        });
        Filter filter3 = new Filter(context, this.resourceProvider);
        this.backdropButton = filter3;
        filter3.setValue(LocaleController.getString(C2369R.string.Gift2AttributeBackdrop));
        this.filtersContainer.addView(this.backdropButton, LayoutHelper.createLinear(-2, -2, 16, 0, 0, 6, 0));
        this.backdropButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda9
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$createView$18(context, view3);
            }
        });
        Filter filter4 = new Filter(context, this.resourceProvider);
        this.patternButton = filter4;
        filter4.setValue(LocaleController.getString(C2369R.string.Gift2AttributeSymbol));
        this.filtersContainer.addView(this.patternButton, LayoutHelper.createLinear(-2, -2, 16, 0, 0, 0, 0));
        this.patternButton.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda10
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.f$0.lambda$createView$24(context, view3);
            }
        });
        FireworksOverlay fireworksOverlay = new FireworksOverlay(getContext());
        this.fireworksOverlay = fireworksOverlay;
        sizeNotifierFrameLayout.addView(fireworksOverlay, LayoutHelper.createFrame(-1, -1.0f));
        setFiltersShown(false, false);
        return sizeNotifierFrameLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(StarsIntroActivity.StarsBalanceView starsBalanceView, View view) {
        if (starsBalanceView.lastBalance <= 0) {
            return;
        }
        presentFragment(new StarsIntroActivity());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(View view) {
        this.list.notSelectedBackdropAttributes.clear();
        this.list.notSelectedModelAttributes.clear();
        this.list.notSelectedPatternAttributes.clear();
        this.list.reload();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$2(View view) {
        this.list.notSelectedBackdropAttributes.clear();
        this.list.notSelectedModelAttributes.clear();
        this.list.notSelectedPatternAttributes.clear();
        this.list.reload();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$6(View view) {
        if (this.filtersShown) {
            ItemOptions.makeOptions(this, this.sortButton).add(C2369R.drawable.menu_sort_value, LocaleController.getString(ResaleGiftsList.Sorting.BY_PRICE.buttonStringResId), new Runnable() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createView$3();
                }
            }).add(C2369R.drawable.menu_sort_date, LocaleController.getString(ResaleGiftsList.Sorting.BY_DATE.buttonStringResId), new Runnable() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda27
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createView$4();
                }
            }).add(C2369R.drawable.menu_sort_number, LocaleController.getString(ResaleGiftsList.Sorting.BY_NUMBER.buttonStringResId), new Runnable() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$createView$5();
                }
            }).setDrawScrim(false).setOnTopOfScrim().translate(0.0f, AndroidUtilities.m1146dp(-8.0f)).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$3() {
        this.list.setSorting(ResaleGiftsList.Sorting.BY_PRICE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$4() {
        this.list.setSorting(ResaleGiftsList.Sorting.BY_DATE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$5() {
        this.list.setSorting(ResaleGiftsList.Sorting.BY_NUMBER);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$12(Context context, View view) {
        if (this.filtersShown && !this.list.modelAttributes.isEmpty()) {
            final ItemOptions itemOptionsNeedsFocus = ItemOptions.makeOptions((BaseFragment) this, (View) this.modelButton, false, true).setDrawScrim(false).setOnTopOfScrim().translate(0.0f, AndroidUtilities.m1146dp(-8.0f)).needsFocus();
            itemOptionsNeedsFocus.setOnDismiss(new Runnable() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    ResaleGiftsFragment.$r8$lambda$jJ0WQWOrcOGUFg3NoK16utP0fkY(itemOptionsNeedsFocus);
                }
            });
            final String[] strArr = {""};
            final ArrayList arrayList = new ArrayList(this.list.modelAttributes);
            Collections.sort(arrayList, new Comparator() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda17
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return this.f$0.lambda$createView$8((TL_stars.starGiftAttributeModel) obj, (TL_stars.starGiftAttributeModel) obj2);
                }
            });
            final UniversalRecyclerView universalRecyclerView = new UniversalRecyclerView(this, new Utilities.Callback2() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda18
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.lambda$createView$9(strArr, arrayList, (ArrayList) obj, (UniversalAdapter) obj2);
                }
            }, new Utilities.Callback5() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda19
                @Override // org.telegram.messenger.Utilities.Callback5
                public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    this.f$0.lambda$createView$10(itemOptionsNeedsFocus, (UItem) obj, (View) obj2, (Integer) obj3, (Float) obj4, (Float) obj5);
                }
            }, null) { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.5
                @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
                protected void onMeasure(int i, int i2) {
                    super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(Math.min((int) (AndroidUtilities.displaySize.y * 0.35f), View.MeasureSpec.getSize(i2)), View.MeasureSpec.getMode(i2)));
                }
            };
            universalRecyclerView.adapter.setApplyBackground(false);
            FrameLayout frameLayout = new FrameLayout(context);
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setImageResource(C2369R.drawable.smiles_inputsearch);
            imageView.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_actionBarDefaultSubmenuItemIcon), PorterDuff.Mode.SRC_IN));
            frameLayout.addView(imageView, LayoutHelper.createFrame(24, 24.0f, 19, 10.0f, 0.0f, 0.0f, 0.0f));
            EditTextCaption editTextCaption = new EditTextCaption(context, this.resourceProvider);
            editTextCaption.setTextSize(1, 16.0f);
            editTextCaption.setInputType(573441);
            editTextCaption.setRawInputType(573441);
            editTextCaption.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3, this.resourceProvider));
            editTextCaption.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourceProvider));
            editTextCaption.setCursorSize(AndroidUtilities.m1146dp(19.0f));
            editTextCaption.setCursorWidth(1.5f);
            editTextCaption.setHint(LocaleController.getString(C2369R.string.Gift2ResaleFiltersSearch));
            editTextCaption.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem, this.resourceProvider));
            editTextCaption.setBackground(null);
            frameLayout.addView(editTextCaption, LayoutHelper.createFrame(-1, -2.0f, 19, 43.0f, 0.0f, 8.0f, 0.0f));
            editTextCaption.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.6
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    strArr[0] = editable.toString();
                    universalRecyclerView.adapter.update(true);
                }
            });
            if (arrayList.size() > 8) {
                itemOptionsNeedsFocus.addView(frameLayout, LayoutHelper.createLinear(-1, 44));
                itemOptionsNeedsFocus.addGap();
            }
            if (!this.list.notSelectedModelAttributes.isEmpty()) {
                itemOptionsNeedsFocus.add(C2369R.drawable.msg_select, LocaleController.getString(C2369R.string.SelectAll), new Runnable() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda20
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$createView$11();
                    }
                });
            }
            itemOptionsNeedsFocus.addView(universalRecyclerView);
            itemOptionsNeedsFocus.show();
        }
    }

    public static /* synthetic */ void $r8$lambda$jJ0WQWOrcOGUFg3NoK16utP0fkY(ItemOptions itemOptions) {
        ActionBarPopupWindow actionBarPopupWindow = itemOptions.actionBarPopupWindow;
        if (actionBarPopupWindow != null) {
            AndroidUtilities.hideKeyboard(actionBarPopupWindow.getContentView());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$createView$8(TL_stars.starGiftAttributeModel stargiftattributemodel, TL_stars.starGiftAttributeModel stargiftattributemodel2) {
        Integer num = (Integer) this.list.modelAttributesCounter.get(Long.valueOf(stargiftattributemodel.document.f1579id));
        Integer num2 = (Integer) this.list.modelAttributesCounter.get(Long.valueOf(stargiftattributemodel2.document.f1579id));
        if (num == null) {
            return 1;
        }
        if (num2 == null) {
            return -1;
        }
        return num2.intValue() - num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$9(String[] strArr, ArrayList arrayList, ArrayList arrayList2, UniversalAdapter universalAdapter) {
        String lowerCase = strArr[0].toLowerCase();
        String strTranslitSafe = AndroidUtilities.translitSafe(lowerCase);
        boolean zIsEmpty = this.list.notSelectedModelAttributes.isEmpty();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TL_stars.starGiftAttributeModel stargiftattributemodel = (TL_stars.starGiftAttributeModel) obj;
            boolean zContains = this.list.notSelectedModelAttributes.contains(Long.valueOf(stargiftattributemodel.document.f1579id));
            boolean z = !zContains;
            if (!TextUtils.isEmpty(lowerCase) && !stargiftattributemodel.name.toLowerCase().startsWith(lowerCase) && !stargiftattributemodel.name.toLowerCase().startsWith(strTranslitSafe)) {
                if (!stargiftattributemodel.name.toLowerCase().contains(" " + lowerCase)) {
                    if (stargiftattributemodel.name.toLowerCase().contains(" " + strTranslitSafe)) {
                    }
                }
            }
            Integer num = (Integer) this.list.modelAttributesCounter.get(Long.valueOf(stargiftattributemodel.document.f1579id));
            UItem uItemAsModel = ModelItem.Factory.asModel(stargiftattributemodel, num == null ? 0 : num.intValue(), lowerCase);
            if (!TextUtils.isEmpty(lowerCase)) {
                z = (zIsEmpty || zContains) ? false : true;
            }
            arrayList2.add(uItemAsModel.setChecked(z));
        }
        if (arrayList2.isEmpty()) {
            arrayList2.add(EmptyView.Factory.asEmptyView(LocaleController.getString(C2369R.string.Gift2ResaleFiltersModelEmpty)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$10(ItemOptions itemOptions, UItem uItem, View view, Integer num, Float f, Float f2) {
        long j = ((TL_stars.starGiftAttributeModel) uItem.object).document.f1579id;
        if (!this.list.notSelectedModelAttributes.contains(Long.valueOf(j))) {
            if (this.list.notSelectedModelAttributes.isEmpty()) {
                ArrayList arrayList = this.list.modelAttributes;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    long j2 = ((TL_stars.starGiftAttributeModel) obj).document.f1579id;
                    if (j2 != j) {
                        this.list.notSelectedModelAttributes.add(Long.valueOf(j2));
                    }
                }
            } else {
                this.list.notSelectedModelAttributes.add(Long.valueOf(j));
            }
        } else {
            this.list.notSelectedModelAttributes.remove(Long.valueOf(j));
        }
        this.list.reload();
        itemOptions.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$11() {
        if (this.list.notSelectedModelAttributes.isEmpty()) {
            return;
        }
        this.list.notSelectedModelAttributes.clear();
        this.list.reload();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$18(Context context, View view) {
        if (this.filtersShown && !this.list.backdropAttributes.isEmpty()) {
            final ItemOptions itemOptionsNeedsFocus = ItemOptions.makeOptions((BaseFragment) this, (View) this.backdropButton, false, true).setDrawScrim(false).setOnTopOfScrim().translate(0.0f, AndroidUtilities.m1146dp(-8.0f)).needsFocus();
            itemOptionsNeedsFocus.setOnDismiss(new Runnable() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    ResaleGiftsFragment.m13429$r8$lambda$D93_wbZHqkNCsXvE1ZB9VZJVY(itemOptionsNeedsFocus);
                }
            });
            final String[] strArr = {""};
            final ArrayList arrayList = new ArrayList(this.list.backdropAttributes);
            Collections.sort(arrayList, new Comparator() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda22
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return this.f$0.lambda$createView$14((TL_stars.starGiftAttributeBackdrop) obj, (TL_stars.starGiftAttributeBackdrop) obj2);
                }
            });
            final UniversalRecyclerView universalRecyclerView = new UniversalRecyclerView(this, new Utilities.Callback2() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda23
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.lambda$createView$15(strArr, arrayList, (ArrayList) obj, (UniversalAdapter) obj2);
                }
            }, new Utilities.Callback5() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda24
                @Override // org.telegram.messenger.Utilities.Callback5
                public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    this.f$0.lambda$createView$16(itemOptionsNeedsFocus, (UItem) obj, (View) obj2, (Integer) obj3, (Float) obj4, (Float) obj5);
                }
            }, null) { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.7
                @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
                protected void onMeasure(int i, int i2) {
                    super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(Math.min((int) (AndroidUtilities.displaySize.y * 0.35f), View.MeasureSpec.getSize(i2)), View.MeasureSpec.getMode(i2)));
                }
            };
            universalRecyclerView.adapter.setApplyBackground(false);
            FrameLayout frameLayout = new FrameLayout(context);
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setImageResource(C2369R.drawable.smiles_inputsearch);
            imageView.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_actionBarDefaultSubmenuItemIcon), PorterDuff.Mode.SRC_IN));
            frameLayout.addView(imageView, LayoutHelper.createFrame(24, 24.0f, 19, 10.0f, 0.0f, 0.0f, 0.0f));
            EditTextCaption editTextCaption = new EditTextCaption(context, this.resourceProvider);
            editTextCaption.setTextSize(1, 16.0f);
            editTextCaption.setInputType(573441);
            editTextCaption.setRawInputType(573441);
            editTextCaption.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3, this.resourceProvider));
            editTextCaption.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourceProvider));
            editTextCaption.setCursorSize(AndroidUtilities.m1146dp(19.0f));
            editTextCaption.setCursorWidth(1.5f);
            editTextCaption.setHint(LocaleController.getString(C2369R.string.Gift2ResaleFiltersSearch));
            editTextCaption.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem, this.resourceProvider));
            editTextCaption.setBackground(null);
            frameLayout.addView(editTextCaption, LayoutHelper.createFrame(-1, -2.0f, 19, 43.0f, 0.0f, 8.0f, 0.0f));
            editTextCaption.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.8
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    strArr[0] = editable.toString();
                    universalRecyclerView.adapter.update(true);
                }
            });
            if (arrayList.size() > 8) {
                itemOptionsNeedsFocus.addView(frameLayout, LayoutHelper.createLinear(-1, 44));
                itemOptionsNeedsFocus.addGap();
            }
            if (!this.list.notSelectedBackdropAttributes.isEmpty()) {
                itemOptionsNeedsFocus.add(C2369R.drawable.msg_select, LocaleController.getString(C2369R.string.SelectAll), new Runnable() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda25
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$createView$17();
                    }
                });
            }
            itemOptionsNeedsFocus.addView(universalRecyclerView);
            itemOptionsNeedsFocus.show();
        }
    }

    /* renamed from: $r8$lambda$D9-3_wbZHqkNCsXvE1ZB-9VZJVY, reason: not valid java name */
    public static /* synthetic */ void m13429$r8$lambda$D93_wbZHqkNCsXvE1ZB9VZJVY(ItemOptions itemOptions) {
        ActionBarPopupWindow actionBarPopupWindow = itemOptions.actionBarPopupWindow;
        if (actionBarPopupWindow != null) {
            AndroidUtilities.hideKeyboard(actionBarPopupWindow.getContentView());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$createView$14(TL_stars.starGiftAttributeBackdrop stargiftattributebackdrop, TL_stars.starGiftAttributeBackdrop stargiftattributebackdrop2) {
        Integer num = (Integer) this.list.backdropAttributesCounter.get(Integer.valueOf(stargiftattributebackdrop.backdrop_id));
        Integer num2 = (Integer) this.list.backdropAttributesCounter.get(Integer.valueOf(stargiftattributebackdrop2.backdrop_id));
        if (num == null) {
            return 1;
        }
        if (num2 == null) {
            return -1;
        }
        return num2.intValue() - num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$15(String[] strArr, ArrayList arrayList, ArrayList arrayList2, UniversalAdapter universalAdapter) {
        String lowerCase = strArr[0].toLowerCase();
        String strTranslitSafe = AndroidUtilities.translitSafe(lowerCase);
        boolean zIsEmpty = this.list.notSelectedBackdropAttributes.isEmpty();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TL_stars.starGiftAttributeBackdrop stargiftattributebackdrop = (TL_stars.starGiftAttributeBackdrop) obj;
            boolean zContains = this.list.notSelectedBackdropAttributes.contains(Integer.valueOf(stargiftattributebackdrop.backdrop_id));
            boolean z = !zContains;
            if (!TextUtils.isEmpty(lowerCase) && !stargiftattributebackdrop.name.toLowerCase().startsWith(lowerCase) && !stargiftattributebackdrop.name.toLowerCase().startsWith(strTranslitSafe)) {
                if (!stargiftattributebackdrop.name.toLowerCase().contains(" " + lowerCase)) {
                    if (stargiftattributebackdrop.name.toLowerCase().contains(" " + strTranslitSafe)) {
                    }
                }
            }
            Integer num = (Integer) this.list.backdropAttributesCounter.get(Integer.valueOf(stargiftattributebackdrop.backdrop_id));
            UItem uItemAsBackdrop = BackdropItem.Factory.asBackdrop(stargiftattributebackdrop, num == null ? 0 : num.intValue(), lowerCase);
            if (!TextUtils.isEmpty(lowerCase)) {
                z = (zIsEmpty || zContains) ? false : true;
            }
            arrayList2.add(uItemAsBackdrop.setChecked(z));
        }
        if (arrayList2.isEmpty()) {
            arrayList2.add(EmptyView.Factory.asEmptyView(LocaleController.getString(C2369R.string.Gift2ResaleFiltersBackdropEmpty)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$16(ItemOptions itemOptions, UItem uItem, View view, Integer num, Float f, Float f2) {
        int i = ((TL_stars.starGiftAttributeBackdrop) uItem.object).backdrop_id;
        if (!this.list.notSelectedBackdropAttributes.contains(Integer.valueOf(i))) {
            if (this.list.notSelectedBackdropAttributes.isEmpty()) {
                ArrayList arrayList = this.list.backdropAttributes;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    int i3 = ((TL_stars.starGiftAttributeBackdrop) obj).backdrop_id;
                    if (i3 != i) {
                        this.list.notSelectedBackdropAttributes.add(Integer.valueOf(i3));
                    }
                }
            } else {
                this.list.notSelectedBackdropAttributes.add(Integer.valueOf(i));
            }
        } else {
            this.list.notSelectedBackdropAttributes.remove(Integer.valueOf(i));
        }
        this.list.reload();
        itemOptions.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$17() {
        if (this.list.notSelectedBackdropAttributes.isEmpty()) {
            return;
        }
        this.list.notSelectedBackdropAttributes.clear();
        this.list.reload();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$24(Context context, View view) {
        if (this.filtersShown && !this.list.patternAttributes.isEmpty()) {
            final ItemOptions itemOptionsNeedsFocus = ItemOptions.makeOptions((BaseFragment) this, (View) this.patternButton, false, true).setDrawScrim(false).setOnTopOfScrim().translate(0.0f, AndroidUtilities.m1146dp(-8.0f)).needsFocus();
            itemOptionsNeedsFocus.setOnDismiss(new Runnable() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    ResaleGiftsFragment.$r8$lambda$lpB95Wkl3iUfZjOTvXM5lXKkzfk(itemOptionsNeedsFocus);
                }
            });
            final String[] strArr = {""};
            final ArrayList arrayList = new ArrayList(this.list.patternAttributes);
            Collections.sort(arrayList, new Comparator() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda12
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return this.f$0.lambda$createView$20((TL_stars.starGiftAttributePattern) obj, (TL_stars.starGiftAttributePattern) obj2);
                }
            });
            final UniversalRecyclerView universalRecyclerView = new UniversalRecyclerView(this, new Utilities.Callback2() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda13
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.lambda$createView$21(strArr, arrayList, (ArrayList) obj, (UniversalAdapter) obj2);
                }
            }, new Utilities.Callback5() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda14
                @Override // org.telegram.messenger.Utilities.Callback5
                public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                    this.f$0.lambda$createView$22(itemOptionsNeedsFocus, (UItem) obj, (View) obj2, (Integer) obj3, (Float) obj4, (Float) obj5);
                }
            }, null) { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.9
                @Override // org.telegram.p023ui.Components.RecyclerListView, androidx.recyclerview.widget.RecyclerView, android.view.View
                protected void onMeasure(int i, int i2) {
                    super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(Math.min((int) (AndroidUtilities.displaySize.y * 0.35f), View.MeasureSpec.getSize(i2)), View.MeasureSpec.getMode(i2)));
                }
            };
            universalRecyclerView.adapter.setApplyBackground(false);
            FrameLayout frameLayout = new FrameLayout(context);
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setImageResource(C2369R.drawable.smiles_inputsearch);
            imageView.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_actionBarDefaultSubmenuItemIcon), PorterDuff.Mode.SRC_IN));
            frameLayout.addView(imageView, LayoutHelper.createFrame(24, 24.0f, 19, 10.0f, 0.0f, 0.0f, 0.0f));
            EditTextCaption editTextCaption = new EditTextCaption(context, this.resourceProvider);
            editTextCaption.setTextSize(1, 16.0f);
            editTextCaption.setInputType(573441);
            editTextCaption.setRawInputType(573441);
            editTextCaption.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3, this.resourceProvider));
            editTextCaption.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourceProvider));
            editTextCaption.setCursorSize(AndroidUtilities.m1146dp(19.0f));
            editTextCaption.setCursorWidth(1.5f);
            editTextCaption.setHint(LocaleController.getString(C2369R.string.Gift2ResaleFiltersSearch));
            editTextCaption.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem, this.resourceProvider));
            editTextCaption.setBackground(null);
            frameLayout.addView(editTextCaption, LayoutHelper.createFrame(-1, -2.0f, 19, 43.0f, 0.0f, 8.0f, 0.0f));
            editTextCaption.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.10
                @Override // android.text.TextWatcher
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                @Override // android.text.TextWatcher
                public void afterTextChanged(Editable editable) {
                    strArr[0] = editable.toString();
                    universalRecyclerView.adapter.update(true);
                }
            });
            if (arrayList.size() > 8) {
                itemOptionsNeedsFocus.addView(frameLayout, LayoutHelper.createLinear(-1, 44));
                itemOptionsNeedsFocus.addGap();
            }
            if (!this.list.notSelectedPatternAttributes.isEmpty()) {
                itemOptionsNeedsFocus.add(C2369R.drawable.msg_select, LocaleController.getString(C2369R.string.SelectAll), new Runnable() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda15
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$createView$23();
                    }
                });
            }
            itemOptionsNeedsFocus.addView(universalRecyclerView);
            itemOptionsNeedsFocus.show();
        }
    }

    public static /* synthetic */ void $r8$lambda$lpB95Wkl3iUfZjOTvXM5lXKkzfk(ItemOptions itemOptions) {
        ActionBarPopupWindow actionBarPopupWindow = itemOptions.actionBarPopupWindow;
        if (actionBarPopupWindow != null) {
            AndroidUtilities.hideKeyboard(actionBarPopupWindow.getContentView());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$createView$20(TL_stars.starGiftAttributePattern stargiftattributepattern, TL_stars.starGiftAttributePattern stargiftattributepattern2) {
        Integer num = (Integer) this.list.patternAttributesCounter.get(Long.valueOf(stargiftattributepattern.document.f1579id));
        Integer num2 = (Integer) this.list.patternAttributesCounter.get(Long.valueOf(stargiftattributepattern2.document.f1579id));
        if (num == null) {
            return 1;
        }
        if (num2 == null) {
            return -1;
        }
        return num2.intValue() - num.intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$21(String[] strArr, ArrayList arrayList, ArrayList arrayList2, UniversalAdapter universalAdapter) {
        String lowerCase = strArr[0].toLowerCase();
        String strTranslitSafe = AndroidUtilities.translitSafe(lowerCase);
        boolean zIsEmpty = this.list.notSelectedPatternAttributes.isEmpty();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TL_stars.starGiftAttributePattern stargiftattributepattern = (TL_stars.starGiftAttributePattern) obj;
            boolean zContains = this.list.notSelectedPatternAttributes.contains(Long.valueOf(stargiftattributepattern.document.f1579id));
            boolean z = !zContains;
            if (!TextUtils.isEmpty(lowerCase) && !stargiftattributepattern.name.toLowerCase().startsWith(lowerCase) && !stargiftattributepattern.name.toLowerCase().startsWith(strTranslitSafe)) {
                if (!stargiftattributepattern.name.toLowerCase().contains(" " + lowerCase)) {
                    if (stargiftattributepattern.name.toLowerCase().contains(" " + strTranslitSafe)) {
                    }
                }
            }
            Integer num = (Integer) this.list.patternAttributesCounter.get(Long.valueOf(stargiftattributepattern.document.f1579id));
            UItem uItemAsPattern = PatternItem.Factory.asPattern(stargiftattributepattern, num == null ? 0 : num.intValue(), lowerCase);
            if (!TextUtils.isEmpty(lowerCase)) {
                z = (zIsEmpty || zContains) ? false : true;
            }
            arrayList2.add(uItemAsPattern.setChecked(z));
        }
        if (arrayList2.isEmpty()) {
            arrayList2.add(EmptyView.Factory.asEmptyView(LocaleController.getString(C2369R.string.Gift2ResaleFiltersSymbolEmpty)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$22(ItemOptions itemOptions, UItem uItem, View view, Integer num, Float f, Float f2) {
        long j = ((TL_stars.starGiftAttributePattern) uItem.object).document.f1579id;
        if (!this.list.notSelectedPatternAttributes.contains(Long.valueOf(j))) {
            if (this.list.notSelectedPatternAttributes.isEmpty()) {
                ArrayList arrayList = this.list.patternAttributes;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    long j2 = ((TL_stars.starGiftAttributePattern) obj).document.f1579id;
                    if (j2 != j) {
                        this.list.notSelectedPatternAttributes.add(Long.valueOf(j2));
                    }
                }
            } else {
                this.list.notSelectedPatternAttributes.add(Long.valueOf(j));
            }
        } else {
            this.list.notSelectedPatternAttributes.remove(Long.valueOf(j));
        }
        this.list.reload();
        itemOptions.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$23() {
        if (this.list.notSelectedPatternAttributes.isEmpty()) {
            return;
        }
        this.list.notSelectedPatternAttributes.clear();
        this.list.reload();
    }

    private void setFiltersShown(final boolean z, boolean z2) {
        if (this.filtersShown == z) {
            return;
        }
        this.filtersShown = z;
        if (z2) {
            this.filterScrollView.setVisibility(0);
            ViewPropertyAnimator viewPropertyAnimatorAlpha = this.filterScrollView.animate().translationY(z ? 0.0f : -AndroidUtilities.m1146dp(45.0f)).alpha(z ? 1.0f : 0.0f);
            CubicBezierInterpolator cubicBezierInterpolator = CubicBezierInterpolator.EASE_OUT_QUINT;
            viewPropertyAnimatorAlpha.setInterpolator(cubicBezierInterpolator).setDuration(420L).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.11
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (z) {
                        return;
                    }
                    ResaleGiftsFragment.this.filterScrollView.setVisibility(8);
                }
            }).start();
            this.filtersDivider.animate().translationY(z ? 0.0f : -AndroidUtilities.m1146dp(45.0f)).setInterpolator(cubicBezierInterpolator).setDuration(420L).start();
            this.listView.animate().translationY(z ? 0.0f : -AndroidUtilities.m1146dp(39.0f)).setInterpolator(cubicBezierInterpolator).setDuration(420L).start();
            return;
        }
        this.filterScrollView.setVisibility(z ? 0 : 8);
        this.filterScrollView.setTranslationY(z ? 0.0f : -AndroidUtilities.m1146dp(45.0f));
        this.filterScrollView.setAlpha(z ? 1.0f : 0.0f);
        this.filtersDivider.setTranslationY(z ? 0.0f : -AndroidUtilities.m1146dp(45.0f));
        this.listView.setTranslationY(z ? 0.0f : -AndroidUtilities.m1146dp(39.0f));
    }

    private void setClearFiltersShown(boolean z, boolean z2) {
        if (this.clearFiltersShown == z) {
            return;
        }
        this.clearFiltersShown = z;
        if (z2) {
            this.clearFiltersContainer.animate().translationY(z ? 0.0f : AndroidUtilities.m1146dp(49.0f)).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(420L).start();
        } else {
            this.clearFiltersContainer.setTranslationY(z ? 0.0f : AndroidUtilities.m1146dp(49.0f));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateList(boolean z) {
        UniversalAdapter universalAdapter;
        if (this.list.getTotalCount() > 12) {
            setFiltersShown(true, true);
        }
        UniversalRecyclerView universalRecyclerView = this.listView;
        boolean z2 = false;
        if (universalRecyclerView != null && (universalAdapter = universalRecyclerView.adapter) != null) {
            universalAdapter.update(true);
            if (z) {
                this.listView.scrollToPosition(0);
            }
        }
        ActionBar actionBar = this.actionBar;
        if (actionBar != null) {
            actionBar.setTitle(this.gift_name);
            this.actionBar.setSubtitle(this.list.getTotalCount() <= 0 ? LocaleController.getString(C2369R.string.Gift2ResaleNoCount) : LocaleController.formatPluralStringComma("Gift2ResaleCount", this.list.getTotalCount()));
        }
        Filter filter = this.sortButton;
        if (filter != null) {
            filter.setSorting(this.list.getSorting());
        }
        if (this.modelButton != null) {
            int size = this.list.modelAttributes.size() - this.list.notSelectedModelAttributes.size();
            this.modelButton.setValue((size <= 0 || size == this.list.modelAttributes.size()) ? LocaleController.getString(C2369R.string.Gift2ResaleFilterModel) : LocaleController.formatPluralStringComma("Gift2ResaleFilterModels", size));
        }
        if (this.backdropButton != null) {
            int size2 = this.list.backdropAttributes.size() - this.list.notSelectedBackdropAttributes.size();
            this.backdropButton.setValue((size2 <= 0 || size2 == this.list.backdropAttributes.size()) ? LocaleController.getString(C2369R.string.Gift2ResaleFilterBackdrop) : LocaleController.formatPluralStringComma("Gift2ResaleFilterBackdrops", size2));
        }
        if (this.patternButton != null) {
            int size3 = this.list.patternAttributes.size() - this.list.notSelectedPatternAttributes.size();
            this.patternButton.setValue((size3 <= 0 || size3 == this.list.patternAttributes.size()) ? LocaleController.getString(C2369R.string.Gift2ResaleFilterSymbol) : LocaleController.formatPluralStringComma("Gift2ResaleFilterSymbols", size3));
        }
        if (isLoadingVisible()) {
            this.list.load();
        }
        ResaleGiftsList resaleGiftsList = this.list;
        if ((resaleGiftsList.loading || resaleGiftsList.getTotalCount() > 0) && (!this.list.notSelectedModelAttributes.isEmpty() || !this.list.notSelectedBackdropAttributes.isEmpty() || !this.list.notSelectedPatternAttributes.isEmpty())) {
            z2 = true;
        }
        setClearFiltersShown(z2, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        ArrayList arrayList2 = this.list.gifts;
        int size = arrayList2.size();
        boolean z = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            arrayList.add(GiftSheet.GiftCell.Factory.asStarGift(0, (TL_stars.TL_starGiftUnique) obj, false, false, false, true));
        }
        ResaleGiftsList resaleGiftsList = this.list;
        if (resaleGiftsList.loading || !resaleGiftsList.endReached) {
            arrayList.add(UItem.asFlicker(-1, 34).setSpanCount(1));
            arrayList.add(UItem.asFlicker(-2, 34).setSpanCount(1));
            arrayList.add(UItem.asFlicker(-3, 34).setSpanCount(1));
            if (this.list.gifts.isEmpty()) {
                arrayList.add(UItem.asFlicker(-4, 34).setSpanCount(1));
                arrayList.add(UItem.asFlicker(-5, 34).setSpanCount(1));
                arrayList.add(UItem.asFlicker(-6, 34).setSpanCount(1));
                arrayList.add(UItem.asFlicker(-7, 34).setSpanCount(1));
                arrayList.add(UItem.asFlicker(-8, 34).setSpanCount(1));
                arrayList.add(UItem.asFlicker(-9, 34).setSpanCount(1));
                arrayList.add(UItem.asFlicker(-10, 34).setSpanCount(1));
                arrayList.add(UItem.asFlicker(-11, 34).setSpanCount(1));
                arrayList.add(UItem.asFlicker(-12, 34).setSpanCount(1));
                arrayList.add(UItem.asFlicker(-13, 34).setSpanCount(1));
                arrayList.add(UItem.asFlicker(-14, 34).setSpanCount(1));
                arrayList.add(UItem.asFlicker(-15, 34).setSpanCount(1));
            }
        }
        if (arrayList.isEmpty() && !this.list.loading) {
            z = true;
        }
        updateEmptyView(z);
    }

    private void updateEmptyView(final boolean z) {
        if (this.emptyViewVisible == z) {
            return;
        }
        this.emptyViewVisible = z;
        this.emptyView.setVisibility(0);
        this.emptyView.animate().alpha(z ? 1.0f : 0.0f).scaleX(z ? 1.0f : 0.95f).scaleY(z ? 1.0f : 0.95f).setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT).setDuration(320L).setListener(new AnimatorListenerAdapter() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.12
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (z) {
                    return;
                }
                ResaleGiftsFragment.this.emptyView.setVisibility(8);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLoadingVisible() {
        for (int i = 0; i < this.listView.getChildCount(); i++) {
            if (this.listView.getChildAt(i) instanceof FlickerLoadingView) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onItemClick(UItem uItem, View view, int i, float f, float f2) {
        Object obj = uItem.object;
        if (obj instanceof TL_stars.TL_starGiftUnique) {
            TL_stars.TL_starGiftUnique tL_starGiftUnique = (TL_stars.TL_starGiftUnique) obj;
            StarGiftSheet starGiftSheet = new StarGiftSheet(getContext(), this.currentAccount, this.dialogId, this.resourceProvider);
            starGiftSheet.set(tL_starGiftUnique.slug, tL_starGiftUnique, this.list);
            starGiftSheet.setOnBoughtGift(new Utilities.Callback2() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$$ExternalSyntheticLambda29
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj2, Object obj3) {
                    this.f$0.lambda$onItemClick$25((TL_stars.TL_starGiftUnique) obj2, (Long) obj3);
                }
            });
            showDialog(starGiftSheet);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onItemClick$25(final TL_stars.TL_starGiftUnique tL_starGiftUnique, final Long l) {
        if (l.longValue() == UserConfig.getInstance(this.currentAccount).getClientUserId()) {
            this.list.gifts.remove(tL_starGiftUnique);
            updateList(false);
            if (l.longValue() == UserConfig.getInstance(this.currentAccount).getClientUserId()) {
                BulletinFactory.m1267of(this).createSimpleBulletin(tL_starGiftUnique.getDocument(), LocaleController.getString(C2369R.string.BoughtResoldGiftTitle), LocaleController.formatString(C2369R.string.BoughtResoldGiftText, tL_starGiftUnique.title + " #" + LocaleController.formatNumber(tL_starGiftUnique.num, ','))).hideAfterBottomSheet(false).show();
            } else {
                BulletinFactory.m1267of(this).createSimpleBulletin(tL_starGiftUnique.getDocument(), LocaleController.getString(C2369R.string.BoughtResoldGiftToTitle), LocaleController.formatString(C2369R.string.BoughtResoldGiftToText, DialogObject.getShortName(this.currentAccount, l.longValue()))).hideAfterBottomSheet(false).show();
            }
            this.fireworksOverlay.start(true);
            return;
        }
        Bundle bundle = new Bundle();
        if (l.longValue() >= 0) {
            bundle.putLong("user_id", l.longValue());
        } else {
            bundle.putLong("chat_id", -l.longValue());
        }
        ChatActivity chatActivity = new ChatActivity(bundle) { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.13
            private boolean shownToast = false;

            @Override // org.telegram.p023ui.ChatActivity, org.telegram.p023ui.ActionBar.BaseFragment
            public void onBecomeFullyVisible() throws Resources.NotFoundException {
                super.onBecomeFullyVisible();
                if (this.shownToast) {
                    return;
                }
                this.shownToast = true;
                BulletinFactory.m1267of(this).createSimpleBulletin(tL_starGiftUnique.getDocument(), LocaleController.getString(C2369R.string.BoughtResoldGiftToTitle), LocaleController.formatString(C2369R.string.BoughtResoldGiftToText, DialogObject.getShortName(this.currentAccount, l.longValue()))).hideAfterBottomSheet(false).show();
                FireworksOverlay fireworksOverlay = this.fireworksOverlay;
                if (fireworksOverlay != null) {
                    fireworksOverlay.start(true);
                }
            }
        };
        INavigationLayout iNavigationLayout = this.parentLayout;
        if (iNavigationLayout != null && iNavigationLayout.isSheet()) {
            lambda$onBackPressed$371();
            BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
            if (safeLastFragment != null) {
                safeLastFragment.presentFragment(chatActivity);
            }
        } else {
            presentFragment(chatActivity, true);
        }
        Runnable runnable = this.closeParentSheet;
        if (runnable != null) {
            runnable.run();
        }
    }

    public static class ResaleGiftsList implements StarsController.IGiftsList {
        private final int account;
        private long attributes_hash;
        public final long gift_id;
        private String last_offset;
        public boolean loading;
        private final Utilities.Callback onUpdate;
        private int totalCount;
        public final ArrayList gifts = new ArrayList();
        public final ArrayList modelAttributes = new ArrayList();
        public final ArrayList backdropAttributes = new ArrayList();
        public final ArrayList patternAttributes = new ArrayList();
        public final HashSet notSelectedModelAttributes = new HashSet();
        public final HashSet notSelectedBackdropAttributes = new HashSet();
        public final HashSet notSelectedPatternAttributes = new HashSet();
        public final HashMap modelAttributesCounter = new HashMap();
        public final HashMap backdropAttributesCounter = new HashMap();
        public final HashMap patternAttributesCounter = new HashMap();
        private Sorting sorting = Sorting.BY_PRICE;
        public boolean endReached = false;
        private int reqId = -1;

        @Override // org.telegram.ui.Stars.StarsController.IGiftsList
        public int findGiftToUpgrade(int i) {
            return -1;
        }

        @Override // org.telegram.ui.Stars.StarsController.IGiftsList
        public void notifyUpdate() {
        }

        public enum Sorting {
            BY_PRICE(C2369R.string.ResellGiftFilterSortPriceShort, C2369R.string.ResellGiftFilterSortPrice),
            BY_DATE(C2369R.string.ResellGiftFilterSortDateShort, C2369R.string.ResellGiftFilterSortDate),
            BY_NUMBER(C2369R.string.ResellGiftFilterSortNumberShort, C2369R.string.ResellGiftFilterSortNumber);

            public int buttonStringResId;
            public int shortResId;

            Sorting(int i, int i2) {
                this.shortResId = i;
                this.buttonStringResId = i2;
            }
        }

        public Sorting getSorting() {
            return this.sorting;
        }

        public void setSorting(Sorting sorting) {
            if (this.sorting != sorting) {
                this.sorting = sorting;
                reload();
            }
        }

        public ResaleGiftsList(int i, long j, Utilities.Callback callback) {
            this.account = i;
            this.gift_id = j;
            this.onUpdate = callback;
        }

        @Override // org.telegram.ui.Stars.StarsController.IGiftsList
        public void load() {
            load(false);
        }

        public void load(boolean z) {
            if (this.loading) {
                return;
            }
            if (z || !this.endReached) {
                this.loading = true;
                final TL_stars.getResaleStarGifts getresalestargifts = new TL_stars.getResaleStarGifts();
                getresalestargifts.gift_id = this.gift_id;
                String str = this.last_offset;
                if (str == null) {
                    str = "";
                }
                getresalestargifts.offset = str;
                getresalestargifts.limit = 15;
                Sorting sorting = this.sorting;
                int i = 0;
                if (sorting == Sorting.BY_NUMBER) {
                    getresalestargifts.sort_by_num = true;
                    getresalestargifts.sort_by_price = false;
                } else if (sorting == Sorting.BY_DATE) {
                    getresalestargifts.sort_by_num = false;
                    getresalestargifts.sort_by_price = false;
                } else if (sorting == Sorting.BY_PRICE) {
                    getresalestargifts.sort_by_num = false;
                    getresalestargifts.sort_by_price = true;
                }
                long j = this.attributes_hash;
                if (j != 0) {
                    getresalestargifts.flags = 1 | getresalestargifts.flags;
                    getresalestargifts.attributes_hash = j;
                } else if (this.modelAttributes.isEmpty() && this.backdropAttributes.isEmpty() && this.patternAttributes.isEmpty()) {
                    getresalestargifts.flags = 1 | getresalestargifts.flags;
                    getresalestargifts.attributes_hash = 0L;
                }
                if (!this.notSelectedModelAttributes.isEmpty() || !this.notSelectedBackdropAttributes.isEmpty() || !this.notSelectedPatternAttributes.isEmpty()) {
                    getresalestargifts.flags |= 8;
                    if (!this.notSelectedModelAttributes.isEmpty()) {
                        ArrayList arrayList = this.modelAttributes;
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            TL_stars.starGiftAttributeModel stargiftattributemodel = (TL_stars.starGiftAttributeModel) obj;
                            if (!this.notSelectedModelAttributes.contains(Long.valueOf(stargiftattributemodel.document.f1579id))) {
                                TL_stars.starGiftAttributeIdModel stargiftattributeidmodel = new TL_stars.starGiftAttributeIdModel();
                                stargiftattributeidmodel.document_id = stargiftattributemodel.document.f1579id;
                                getresalestargifts.attributes.add(stargiftattributeidmodel);
                            }
                        }
                    }
                    if (!this.notSelectedBackdropAttributes.isEmpty()) {
                        ArrayList arrayList2 = this.backdropAttributes;
                        int size2 = arrayList2.size();
                        int i3 = 0;
                        while (i3 < size2) {
                            Object obj2 = arrayList2.get(i3);
                            i3++;
                            TL_stars.starGiftAttributeBackdrop stargiftattributebackdrop = (TL_stars.starGiftAttributeBackdrop) obj2;
                            if (!this.notSelectedBackdropAttributes.contains(Integer.valueOf(stargiftattributebackdrop.backdrop_id))) {
                                TL_stars.starGiftAttributeIdBackdrop stargiftattributeidbackdrop = new TL_stars.starGiftAttributeIdBackdrop();
                                stargiftattributeidbackdrop.backdrop_id = stargiftattributebackdrop.backdrop_id;
                                getresalestargifts.attributes.add(stargiftattributeidbackdrop);
                            }
                        }
                    }
                    if (!this.notSelectedPatternAttributes.isEmpty()) {
                        ArrayList arrayList3 = this.patternAttributes;
                        int size3 = arrayList3.size();
                        while (i < size3) {
                            Object obj3 = arrayList3.get(i);
                            i++;
                            TL_stars.starGiftAttributePattern stargiftattributepattern = (TL_stars.starGiftAttributePattern) obj3;
                            if (!this.notSelectedPatternAttributes.contains(Long.valueOf(stargiftattributepattern.document.f1579id))) {
                                TL_stars.starGiftAttributeIdPattern stargiftattributeidpattern = new TL_stars.starGiftAttributeIdPattern();
                                stargiftattributeidpattern.document_id = stargiftattributepattern.document.f1579id;
                                getresalestargifts.attributes.add(stargiftattributeidpattern);
                            }
                        }
                    }
                }
                this.reqId = ConnectionsManager.getInstance(this.account).sendRequest(getresalestargifts, new RequestDelegate() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$ResaleGiftsList$$ExternalSyntheticLambda0
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$load$1(getresalestargifts, tLObject, tL_error);
                    }
                });
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$load$1(final TL_stars.getResaleStarGifts getresalestargifts, final TLObject tLObject, TLRPC.TL_error tL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment$ResaleGiftsList$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$load$0(tLObject, getresalestargifts);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$load$0(TLObject tLObject, TL_stars.getResaleStarGifts getresalestargifts) {
            boolean z;
            this.reqId = -1;
            if (tLObject instanceof TL_stars.resaleStarGifts) {
                TL_stars.resaleStarGifts resalestargifts = (TL_stars.resaleStarGifts) tLObject;
                int i = 0;
                MessagesController.getInstance(this.account).putUsers(resalestargifts.users, false);
                MessagesController.getInstance(this.account).putChats(resalestargifts.chats, false);
                this.totalCount = resalestargifts.count;
                boolean z2 = true;
                if (TextUtils.isEmpty(getresalestargifts.offset)) {
                    this.gifts.clear();
                    z = true;
                } else {
                    z = false;
                }
                ArrayList<TL_stars.StarGift> arrayList = resalestargifts.gifts;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    TL_stars.StarGift starGift = arrayList.get(i2);
                    i2++;
                    TL_stars.StarGift starGift2 = starGift;
                    if (starGift2 instanceof TL_stars.TL_starGiftUnique) {
                        this.gifts.add((TL_stars.TL_starGiftUnique) starGift2);
                    }
                }
                if (this.gifts.size() < this.totalCount && !TextUtils.isEmpty(resalestargifts.next_offset)) {
                    z2 = false;
                }
                this.endReached = z2;
                this.last_offset = resalestargifts.next_offset;
                this.loading = false;
                ArrayList<TL_stars.StarGiftAttribute> arrayList2 = resalestargifts.attributes;
                if (arrayList2 != null && !arrayList2.isEmpty()) {
                    this.modelAttributes.clear();
                    this.backdropAttributes.clear();
                    this.patternAttributes.clear();
                    this.modelAttributes.addAll(StarsController.findAttributes(resalestargifts.attributes, TL_stars.starGiftAttributeModel.class));
                    this.backdropAttributes.addAll(StarsController.findAttributes(resalestargifts.attributes, TL_stars.starGiftAttributeBackdrop.class));
                    this.patternAttributes.addAll(StarsController.findAttributes(resalestargifts.attributes, TL_stars.starGiftAttributePattern.class));
                    this.attributes_hash = resalestargifts.attributes_hash;
                }
                if (!resalestargifts.counters.isEmpty()) {
                    this.backdropAttributesCounter.clear();
                    this.patternAttributesCounter.clear();
                    this.modelAttributesCounter.clear();
                    ArrayList<TL_stars.starGiftAttributeCounter> arrayList3 = resalestargifts.counters;
                    int size2 = arrayList3.size();
                    while (i < size2) {
                        TL_stars.starGiftAttributeCounter stargiftattributecounter = arrayList3.get(i);
                        i++;
                        TL_stars.starGiftAttributeCounter stargiftattributecounter2 = stargiftattributecounter;
                        TL_stars.StarGiftAttributeId starGiftAttributeId = stargiftattributecounter2.attribute;
                        if (starGiftAttributeId instanceof TL_stars.starGiftAttributeIdBackdrop) {
                            this.backdropAttributesCounter.put(Integer.valueOf(starGiftAttributeId.backdrop_id), Integer.valueOf(stargiftattributecounter2.count));
                        } else if (starGiftAttributeId instanceof TL_stars.starGiftAttributeIdPattern) {
                            this.patternAttributesCounter.put(Long.valueOf(starGiftAttributeId.document_id), Integer.valueOf(stargiftattributecounter2.count));
                        } else if (starGiftAttributeId instanceof TL_stars.starGiftAttributeIdModel) {
                            this.modelAttributesCounter.put(Long.valueOf(starGiftAttributeId.document_id), Integer.valueOf(stargiftattributecounter2.count));
                        }
                    }
                }
                Utilities.Callback callback = this.onUpdate;
                if (callback != null) {
                    callback.run(Boolean.valueOf(z));
                }
            }
        }

        public void cancel() {
            if (this.reqId >= 0) {
                ConnectionsManager.getInstance(this.account).cancelRequest(this.reqId, true);
                this.reqId = -1;
            }
            this.loading = false;
        }

        public void reload() {
            cancel();
            this.last_offset = null;
            this.gifts.clear();
            load(true);
            Utilities.Callback callback = this.onUpdate;
            if (callback != null) {
                callback.run(Boolean.TRUE);
            }
        }

        @Override // org.telegram.ui.Stars.StarsController.IGiftsList
        public int getTotalCount() {
            return this.totalCount;
        }

        @Override // org.telegram.ui.Stars.StarsController.IGiftsList
        public int getLoadedCount() {
            return this.gifts.size();
        }

        @Override // org.telegram.ui.Stars.StarsController.IGiftsList
        public Object get(int i) {
            return this.gifts.get(i);
        }

        @Override // org.telegram.ui.Stars.StarsController.IGiftsList
        public int indexOf(Object obj) {
            return this.gifts.indexOf(obj);
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean isLightStatusBar() {
        if (getLastStoryViewer() != null && getLastStoryViewer().isShown()) {
            return false;
        }
        int color = Theme.getColor(Theme.key_windowBackgroundWhite);
        if (this.actionBar.isActionModeShowed()) {
            color = Theme.getColor(Theme.key_actionBarActionModeDefault);
        }
        return ColorUtils.calculateLuminance(color) > 0.699999988079071d;
    }

    public static class Filter extends TextView {
        private ColoredImageSpan span;

        public Filter(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            int color = Theme.getColor(Theme.key_actionBarActionModeDefaultIcon, resourcesProvider);
            setTextColor(color);
            setBackground(Theme.createRadSelectorDrawable(Theme.multAlpha(color, 0.08f), Theme.multAlpha(color, 0.15f), AndroidUtilities.m1146dp(13.0f), AndroidUtilities.m1146dp(13.0f)));
            setPadding(AndroidUtilities.m1146dp(11.0f), 0, AndroidUtilities.m1146dp(11.0f), 0);
            setGravity(17);
            setTypeface(AndroidUtilities.bold());
            ScaleStateListAnimator.apply(this);
            ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2369R.drawable.arrows_select);
            this.span = coloredImageSpan;
            coloredImageSpan.spaceScaleX = 0.8f;
            coloredImageSpan.translate(0.0f, AndroidUtilities.m1146dp(1.0f));
        }

        public void setSorting(ResaleGiftsList.Sorting sorting) {
            ColoredImageSpan coloredImageSpan;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("v ");
            if (sorting == ResaleGiftsList.Sorting.BY_DATE) {
                coloredImageSpan = new ColoredImageSpan(C2369R.drawable.mini_gift_sorting_date);
                spannableStringBuilder.setSpan(coloredImageSpan, 0, 1, 33);
                spannableStringBuilder.append((CharSequence) LocaleController.getString(C2369R.string.ResellGiftFilterSortDateShort));
            } else if (sorting == ResaleGiftsList.Sorting.BY_PRICE) {
                coloredImageSpan = new ColoredImageSpan(C2369R.drawable.mini_gift_sorting_price);
                spannableStringBuilder.setSpan(coloredImageSpan, 0, 1, 33);
                spannableStringBuilder.append((CharSequence) LocaleController.getString(C2369R.string.ResellGiftFilterSortPriceShort));
            } else if (sorting == ResaleGiftsList.Sorting.BY_NUMBER) {
                coloredImageSpan = new ColoredImageSpan(C2369R.drawable.mini_gift_sorting_num);
                spannableStringBuilder.setSpan(coloredImageSpan, 0, 1, 33);
                spannableStringBuilder.append((CharSequence) LocaleController.getString(C2369R.string.ResellGiftFilterSortNumberShort));
            } else {
                coloredImageSpan = null;
            }
            if (coloredImageSpan != null) {
                coloredImageSpan.translate(0.0f, AndroidUtilities.m1146dp(1.0f));
            }
            setText(spannableStringBuilder);
        }

        public void setValue(CharSequence charSequence) {
            SpannableStringBuilder spannableStringBuilderAppend = new SpannableStringBuilder(charSequence).append((CharSequence) " v");
            spannableStringBuilderAppend.setSpan(this.span, spannableStringBuilderAppend.length() - 1, spannableStringBuilderAppend.length(), 33);
            setText(spannableStringBuilderAppend);
        }

        @Override // android.widget.TextView, android.view.View
        protected void onMeasure(int i, int i2) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(26.0f), TLObject.FLAG_30));
        }
    }

    public static class EmptyView extends LinearLayout {
        private final BackupImageView imageView;
        private final TextView textView;

        public EmptyView(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            setOrientation(1);
            BackupImageView backupImageView = new BackupImageView(context);
            this.imageView = backupImageView;
            backupImageView.setImageDrawable(new RLottieDrawable(C2369R.raw.utyan_empty, "utyan_empty", AndroidUtilities.m1146dp(130.0f), AndroidUtilities.m1146dp(130.0f)));
            addView(backupImageView, LayoutHelper.createLinear(64, 64, 17, 0, 32, 0, 0));
            TextView textView = new TextView(context);
            this.textView = textView;
            textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3, resourcesProvider));
            textView.setTextSize(1, 14.0f);
            textView.setGravity(17);
            addView(textView, LayoutHelper.createLinear(-1, -2, 7, 12, 12, 12, 24));
        }

        public void set(CharSequence charSequence) {
            this.textView.setText(charSequence);
        }

        @Override // android.widget.LinearLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
                size = AndroidUtilities.m1146dp(250.0f);
            }
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), i2);
        }

        public static class Factory extends UItem.UItemFactory {
            static {
                UItem.UItemFactory.setup(new Factory());
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public EmptyView createView(Context context, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
                return new EmptyView(context, resourcesProvider);
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
                ((EmptyView) view).set(uItem.text);
            }

            public static UItem asEmptyView(CharSequence charSequence) {
                UItem uItemOfFactory = UItem.ofFactory(Factory.class);
                uItemOfFactory.text = charSequence;
                return uItemOfFactory;
            }
        }
    }

    public static class LargeEmptyView extends FrameLayout {
        private final TextView buttonView;
        private final BackupImageView imageView;
        private final LinearLayout layout;
        private final TextView subtitleView;
        private final TextView titleView;

        public LargeEmptyView(Context context, View.OnClickListener onClickListener, Theme.ResourcesProvider resourcesProvider) {
            super(context);
            LinearLayout linearLayout = new LinearLayout(context);
            this.layout = linearLayout;
            linearLayout.setOrientation(1);
            addView(linearLayout, LayoutHelper.createFrame(-1, -2, 23));
            BackupImageView backupImageView = new BackupImageView(context);
            this.imageView = backupImageView;
            backupImageView.setImageDrawable(new RLottieDrawable(C2369R.raw.utyan_empty, "utyan_empty", AndroidUtilities.m1146dp(130.0f), AndroidUtilities.m1146dp(130.0f)));
            linearLayout.addView(backupImageView, LayoutHelper.createLinear(Opcodes.IXOR, Opcodes.IXOR, 17));
            TextView textView = new TextView(context);
            this.titleView = textView;
            textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, resourcesProvider));
            textView.setTextSize(1, 17.0f);
            textView.setGravity(17);
            textView.setTypeface(AndroidUtilities.bold());
            textView.setText(LocaleController.getString(C2369R.string.Gift2ResaleFiltersEmptyTitle));
            linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, 17, 32, 12, 32, 9));
            LinkSpanDrawable.LinksTextView linksTextView = new LinkSpanDrawable.LinksTextView(context);
            this.subtitleView = linksTextView;
            linksTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3, resourcesProvider));
            linksTextView.setTextSize(1, 14.0f);
            linksTextView.setGravity(17);
            linksTextView.setText(LocaleController.getString(C2369R.string.Gift2ResaleFiltersEmptySubtitle));
            linksTextView.setMaxWidth(AndroidUtilities.m1146dp(200.0f));
            linearLayout.addView(linksTextView, LayoutHelper.createLinear(-2, -2, 17, 32, 0, 32, 12));
            TextView textView2 = new TextView(context);
            this.buttonView = textView2;
            int i = Theme.key_featuredStickers_addButton;
            textView2.setTextColor(Theme.getColor(i, resourcesProvider));
            textView2.setBackground(Theme.createRadSelectorDrawable(Theme.multAlpha(Theme.getColor(i, resourcesProvider), 0.1f), 6, 6));
            textView2.setGravity(17);
            textView2.setText(LocaleController.getString(C2369R.string.Gift2ResaleFiltersEmptyClear));
            textView2.setPadding(AndroidUtilities.m1146dp(13.0f), 0, AndroidUtilities.m1146dp(13.0f), 0);
            ScaleStateListAnimator.apply(textView2);
            linearLayout.addView(textView2, LayoutHelper.createLinear(-2, 27, 17, 32, 0, 32, 12));
            textView2.setOnClickListener(onClickListener);
        }
    }

    public static class ModelItem extends ActionBarMenuSubItem {
        private final int currentAccount;
        private AnimatedEmojiDrawable emojiDrawable;
        private long emojiDrawableId;

        public ModelItem(Context context, int i, Theme.ResourcesProvider resourcesProvider) {
            super(context, false, false, resourcesProvider);
            this.currentAccount = i;
            setPadding(AndroidUtilities.m1146dp(18.0f), 0, AndroidUtilities.m1146dp(18.0f), 0);
            setColors(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem, resourcesProvider), Theme.getColor(Theme.key_actionBarDefaultSubmenuItemIcon, resourcesProvider));
            setIconColor(-1);
            this.imageView.setTranslationX(AndroidUtilities.m1146dp(2.0f));
            this.imageView.setScaleX(1.2f);
            this.imageView.setScaleY(1.2f);
            makeCheckView(2);
            setBackground(null);
            this.imageView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.ModelItem.1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view) {
                    if (ModelItem.this.emojiDrawable != null) {
                        ModelItem.this.emojiDrawable.addView(ModelItem.this.imageView);
                    }
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view) {
                    if (ModelItem.this.emojiDrawable != null) {
                        ModelItem.this.emojiDrawable.removeView(ModelItem.this.imageView);
                    }
                }
            });
        }

        public void set(TL_stars.starGiftAttributeModel stargiftattributemodel, int i, String str, boolean z) {
            AnimatedEmojiDrawable animatedEmojiDrawable = this.emojiDrawable;
            if (animatedEmojiDrawable == null || this.emojiDrawableId != stargiftattributemodel.document.f1579id) {
                this.emojiDrawableId = stargiftattributemodel.document.f1579id;
                if (animatedEmojiDrawable != null) {
                    animatedEmojiDrawable.removeView(this.imageView);
                }
                this.emojiDrawable = new AnimatedEmojiDrawable(3, this.currentAccount, stargiftattributemodel.document) { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.ModelItem.2
                    @Override // android.graphics.drawable.Drawable
                    public int getIntrinsicHeight() {
                        return AndroidUtilities.m1146dp(24.0f);
                    }

                    @Override // android.graphics.drawable.Drawable
                    public int getIntrinsicWidth() {
                        return AndroidUtilities.m1146dp(24.0f);
                    }
                };
            }
            if (this.imageView.isAttachedToWindow()) {
                this.emojiDrawable.addView(this.imageView);
            }
            CharSequence charSequenceHighlightText = stargiftattributemodel.name;
            if (!TextUtils.isEmpty(str)) {
                charSequenceHighlightText = AndroidUtilities.highlightText(charSequenceHighlightText, str, this.resourcesProvider);
            }
            if (i > 0) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequenceHighlightText);
                spannableStringBuilder.append((CharSequence) "  ");
                int length = spannableStringBuilder.length();
                spannableStringBuilder.append((CharSequence) Integer.toString(i));
                spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold()), length, spannableStringBuilder.length(), 33);
                charSequenceHighlightText = spannableStringBuilder;
            }
            setTextAndIcon(charSequenceHighlightText, 0, this.emojiDrawable);
            setChecked(z);
        }

        @Override // org.telegram.p023ui.ActionBar.ActionBarMenuSubItem, android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
                size = AndroidUtilities.m1146dp(250.0f);
            }
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), i2);
        }

        public static class Factory extends UItem.UItemFactory {
            static {
                UItem.UItemFactory.setup(new Factory());
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public ModelItem createView(Context context, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
                return new ModelItem(context, i, resourcesProvider);
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
                ((ModelItem) view).set((TL_stars.starGiftAttributeModel) uItem.object, uItem.intValue, (String) uItem.text, uItem.checked);
            }

            public static UItem asModel(TL_stars.starGiftAttributeModel stargiftattributemodel, int i, String str) {
                UItem uItemOfFactory = UItem.ofFactory(Factory.class);
                uItemOfFactory.object = stargiftattributemodel;
                uItemOfFactory.text = str;
                uItemOfFactory.intValue = i;
                return uItemOfFactory;
            }
        }
    }

    public static class PatternItem extends ActionBarMenuSubItem {
        private final int currentAccount;
        private AnimatedEmojiDrawable emojiDrawable;
        private long emojiDrawableId;

        public PatternItem(Context context, int i, Theme.ResourcesProvider resourcesProvider) {
            super(context, false, false, resourcesProvider);
            this.currentAccount = i;
            setPadding(AndroidUtilities.m1146dp(18.0f), 0, AndroidUtilities.m1146dp(18.0f), 0);
            setColors(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem, resourcesProvider), Theme.getColor(Theme.key_actionBarDefaultSubmenuItemIcon, resourcesProvider));
            setIconColor(-1);
            this.imageView.setTranslationX(AndroidUtilities.m1146dp(2.0f));
            makeCheckView(2);
            setBackground(null);
            this.imageView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.PatternItem.1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view) {
                    if (PatternItem.this.emojiDrawable != null) {
                        PatternItem.this.emojiDrawable.addView(PatternItem.this.imageView);
                    }
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view) {
                    if (PatternItem.this.emojiDrawable != null) {
                        PatternItem.this.emojiDrawable.removeView(PatternItem.this.imageView);
                    }
                }
            });
        }

        public void set(TL_stars.starGiftAttributePattern stargiftattributepattern, int i, String str, boolean z) {
            AnimatedEmojiDrawable animatedEmojiDrawable = this.emojiDrawable;
            if (animatedEmojiDrawable == null || this.emojiDrawableId != stargiftattributepattern.document.f1579id) {
                this.emojiDrawableId = stargiftattributepattern.document.f1579id;
                if (animatedEmojiDrawable != null) {
                    animatedEmojiDrawable.removeView(this.imageView);
                }
                AnimatedEmojiDrawable animatedEmojiDrawable2 = new AnimatedEmojiDrawable(3, this.currentAccount, stargiftattributepattern.document) { // from class: org.telegram.ui.Gifts.ResaleGiftsFragment.PatternItem.2
                    @Override // android.graphics.drawable.Drawable
                    public int getIntrinsicHeight() {
                        return AndroidUtilities.m1146dp(24.0f);
                    }

                    @Override // android.graphics.drawable.Drawable
                    public int getIntrinsicWidth() {
                        return AndroidUtilities.m1146dp(24.0f);
                    }
                };
                this.emojiDrawable = animatedEmojiDrawable2;
                animatedEmojiDrawable2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem, this.resourcesProvider), PorterDuff.Mode.SRC_IN));
            }
            if (this.imageView.isAttachedToWindow()) {
                this.emojiDrawable.addView(this.imageView);
            }
            CharSequence charSequenceHighlightText = stargiftattributepattern.name;
            if (!TextUtils.isEmpty(str)) {
                charSequenceHighlightText = AndroidUtilities.highlightText(charSequenceHighlightText, str, this.resourcesProvider);
            }
            if (i > 0) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequenceHighlightText);
                spannableStringBuilder.append((CharSequence) "  ");
                int length = spannableStringBuilder.length();
                spannableStringBuilder.append((CharSequence) Integer.toString(i));
                spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold()), length, spannableStringBuilder.length(), 33);
                charSequenceHighlightText = spannableStringBuilder;
            }
            setTextAndIcon(charSequenceHighlightText, 0, this.emojiDrawable);
            setChecked(z);
        }

        @Override // org.telegram.p023ui.ActionBar.ActionBarMenuSubItem, android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
                size = AndroidUtilities.m1146dp(250.0f);
            }
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), i2);
        }

        public static class Factory extends UItem.UItemFactory {
            static {
                UItem.UItemFactory.setup(new Factory());
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public PatternItem createView(Context context, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
                return new PatternItem(context, i, resourcesProvider);
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
                ((PatternItem) view).set((TL_stars.starGiftAttributePattern) uItem.object, uItem.intValue, (String) uItem.text, uItem.checked);
            }

            public static UItem asPattern(TL_stars.starGiftAttributePattern stargiftattributepattern, int i, String str) {
                UItem uItemOfFactory = UItem.ofFactory(Factory.class);
                uItemOfFactory.object = stargiftattributepattern;
                uItemOfFactory.text = str;
                uItemOfFactory.intValue = i;
                return uItemOfFactory;
            }
        }
    }

    public static class BackdropItem extends ActionBarMenuSubItem {
        public BackdropItem(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context, false, false, resourcesProvider);
            setPadding(AndroidUtilities.m1146dp(18.0f), 0, AndroidUtilities.m1146dp(18.0f), 0);
            setColors(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem, resourcesProvider), Theme.getColor(Theme.key_actionBarDefaultSubmenuItemIcon, resourcesProvider));
            setIconColor(-1);
            this.imageView.setTranslationX(AndroidUtilities.m1146dp(2.0f));
            makeCheckView(2);
            setBackground(null);
        }

        public void set(TL_stars.starGiftAttributeBackdrop stargiftattributebackdrop, int i, String str, boolean z) {
            ShapeDrawable shapeDrawableCreateCircleDrawable = Theme.createCircleDrawable(AndroidUtilities.m1146dp(20.0f), stargiftattributebackdrop.center_color | (-16777216));
            CharSequence charSequenceHighlightText = stargiftattributebackdrop.name;
            if (!TextUtils.isEmpty(str)) {
                charSequenceHighlightText = AndroidUtilities.highlightText(charSequenceHighlightText, str, this.resourcesProvider);
            }
            if (i > 0) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequenceHighlightText);
                spannableStringBuilder.append((CharSequence) "  ");
                int length = spannableStringBuilder.length();
                spannableStringBuilder.append((CharSequence) Integer.toString(i));
                spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold()), length, spannableStringBuilder.length(), 33);
                charSequenceHighlightText = spannableStringBuilder;
            }
            setTextAndIcon(charSequenceHighlightText, 0, shapeDrawableCreateCircleDrawable);
            setChecked(z);
        }

        @Override // org.telegram.p023ui.ActionBar.ActionBarMenuSubItem, android.widget.FrameLayout, android.view.View
        protected void onMeasure(int i, int i2) {
            int size = View.MeasureSpec.getSize(i);
            if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE) {
                size = AndroidUtilities.m1146dp(250.0f);
            }
            super.onMeasure(View.MeasureSpec.makeMeasureSpec(size, TLObject.FLAG_30), i2);
        }

        public static class Factory extends UItem.UItemFactory {
            static {
                UItem.UItemFactory.setup(new Factory());
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public BackdropItem createView(Context context, int i, int i2, Theme.ResourcesProvider resourcesProvider) {
                return new BackdropItem(context, resourcesProvider);
            }

            @Override // org.telegram.ui.Components.UItem.UItemFactory
            public void bindView(View view, UItem uItem, boolean z, UniversalAdapter universalAdapter, UniversalRecyclerView universalRecyclerView) {
                ((BackdropItem) view).set((TL_stars.starGiftAttributeBackdrop) uItem.object, uItem.intValue, (String) uItem.text, uItem.checked);
            }

            public static UItem asBackdrop(TL_stars.starGiftAttributeBackdrop stargiftattributebackdrop, int i, String str) {
                UItem uItemOfFactory = UItem.ofFactory(Factory.class);
                uItemOfFactory.object = stargiftattributebackdrop;
                uItemOfFactory.text = str;
                uItemOfFactory.intValue = i;
                return uItemOfFactory;
            }
        }
    }
}
