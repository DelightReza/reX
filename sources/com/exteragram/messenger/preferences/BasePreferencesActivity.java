package com.exteragram.messenger.preferences;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.preferences.utils.SettingsRegistry;
import com.exteragram.messenger.utils.p011ui.PopupUtils;
import com.exteragram.messenger.utils.system.VibratorUtils;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.ToIntFunction;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Cells.CheckBoxCell;
import org.telegram.p023ui.Cells.TextCell;
import org.telegram.p023ui.Components.Bulletin;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.ItemOptions;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.RecyclerListView;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import org.telegram.p023ui.Components.inset.WindowInsetsStateHolder;
import p017j$.util.Collection;

/* loaded from: classes.dex */
public abstract class BasePreferencesActivity extends BaseFragment {
    private AnimatorSet actionBarAnimator;
    private View actionBarBackground;
    protected LinearLayoutManager layoutManager;
    protected UniversalRecyclerView listView;
    private final int[] location = new int[2];
    protected final WindowInsetsStateHolder windowInsetsStateHolder = new WindowInsetsStateHolder(new Runnable() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity$$ExternalSyntheticLambda10
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.checkInsets();
        }
    });

    protected abstract void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter);

    public abstract String getTitle();

    protected boolean hasHeaderCell() {
        return false;
    }

    protected boolean hasWhiteActionBar() {
        return false;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean isSupportEdgeToEdge() {
        return true;
    }

    protected abstract void onClick(UItem uItem, View view, int i, float f, float f2);

    protected void checkInsets() {
        this.listView.setPadding(0, 0, 0, this.windowInsetsStateHolder.getCurrentNavigationBarInset());
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setBackButtonImage(C2369R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(false);
        this.actionBar.setTitle(getTitle());
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    BasePreferencesActivity.this.lambda$onBackPressed$371();
                }
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        ActionBar actionBar = this.actionBar;
        if (actionBar.menu == null) {
            actionBar.createMenu();
        }
        UniversalRecyclerView universalRecyclerView = new UniversalRecyclerView(this, new Utilities.Callback2() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity$$ExternalSyntheticLambda5
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, new Utilities.Callback5() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity$$ExternalSyntheticLambda6
            @Override // org.telegram.messenger.Utilities.Callback5
            public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                this.f$0.onClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
            }
        }, new Utilities.Callback5Return() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity$$ExternalSyntheticLambda7
            @Override // org.telegram.messenger.Utilities.Callback5Return
            public final Object run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                return Boolean.valueOf(this.f$0.onLongClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue()));
            }
        });
        this.listView = universalRecyclerView;
        universalRecyclerView.adapter.setUseSectionStyle(true);
        this.listView.setClipToPadding(false);
        UniversalRecyclerView universalRecyclerView2 = this.listView;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false);
        this.layoutManager = linearLayoutManager;
        universalRecyclerView2.setLayoutManager(linearLayoutManager);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        if (hasHeaderCell()) {
            this.actionBar.setBackground(null);
            ActionBar actionBar2 = this.actionBar;
            int i = Theme.key_windowBackgroundWhiteBlackText;
            actionBar2.setTitleColor(Theme.getColor(i));
            this.actionBar.setItemsColor(Theme.getColor(i), false);
            this.actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_listSelector), false);
            this.actionBar.setCastShadows(false);
            this.actionBar.setAddToContainer(false);
            this.actionBar.getTitleTextView().setAlpha(0.0f);
            View view = new View(context) { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity.2
                private final Paint paint = new Paint();

                @Override // android.view.View
                protected void onDraw(Canvas canvas) {
                    this.paint.setColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    int measuredHeight = getMeasuredHeight() - AndroidUtilities.m1146dp(3.0f);
                    canvas.drawRect(0.0f, 0.0f, getMeasuredWidth(), measuredHeight, this.paint);
                    ((BaseFragment) BasePreferencesActivity.this).parentLayout.drawHeaderShadow(canvas, measuredHeight);
                }
            };
            this.actionBarBackground = view;
            view.setAlpha(0.0f);
            frameLayout.addView(this.actionBarBackground, LayoutHelper.createFrame(-1, -2.0f));
            frameLayout.addView(this.actionBar, LayoutHelper.createFrame(-1, -2.0f));
            this.listView.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity.3
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i2, int i3) {
                    super.onScrolled(recyclerView, i2, i3);
                    BasePreferencesActivity.this.checkScroll(true);
                }
            });
            frameLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity$$ExternalSyntheticLambda8
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view2, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
                    this.f$0.lambda$createView$0(view2, i2, i3, i4, i5, i6, i7, i8, i9);
                }
            });
        }
        this.fragmentView = frameLayout;
        ViewCompat.setOnApplyWindowInsetsListener(frameLayout, new OnApplyWindowInsetsListener() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity$$ExternalSyntheticLambda9
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                return this.f$0.lambda$createView$1(view2, windowInsetsCompat);
            }
        });
        return this.fragmentView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        checkScroll(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ WindowInsetsCompat lambda$createView$1(View view, WindowInsetsCompat windowInsetsCompat) {
        this.windowInsetsStateHolder.setInsets(windowInsetsCompat);
        return WindowInsetsCompat.CONSUMED;
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        this.listView.adapter.update(false);
        Bulletin.addDelegate(this, new Bulletin.Delegate() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity.4
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
                return BasePreferencesActivity.this.windowInsetsStateHolder.getCurrentNavigationBarInset();
            }
        });
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onPause() {
        super.onPause();
        Bulletin.removeDelegate(this);
    }

    public void scrollToItem(int i) {
        final int iFindPositionByItemId = this.listView.findPositionByItemId(i);
        if (iFindPositionByItemId >= 0 && iFindPositionByItemId < this.listView.adapter.getItemCount()) {
            this.listView.highlightRow(new RecyclerListView.IntReturnCallback() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity$$ExternalSyntheticLambda2
                @Override // org.telegram.ui.Components.RecyclerListView.IntReturnCallback
                public final int run() {
                    return this.f$0.lambda$scrollToItem$2(iFindPositionByItemId);
                }
            });
        } else {
            SettingsRegistry.getInstance().onSettingNotFound(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ int lambda$scrollToItem$2(int i) {
        this.layoutManager.scrollToPositionWithOffset(i, AndroidUtilities.m1146dp(60.0f));
        return i;
    }

    protected int[] unBox(Collection<Integer> collection) {
        return Collection.EL.stream(collection).mapToInt(new ToIntFunction() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity$$ExternalSyntheticLambda3
            @Override // java.util.function.ToIntFunction
            public final int applyAsInt(Object obj) {
                return ((Integer) obj).intValue();
            }
        }).toArray();
    }

    protected void showListDialog(UItem uItem, CharSequence[] charSequenceArr, String str, int i, PopupUtils.OnItemClickListener onItemClickListener) {
        showListDialog(uItem, charSequenceArr, null, str, i, onItemClickListener);
    }

    protected void showListDialog(UItem uItem, CharSequence[] charSequenceArr, int[] iArr, String str, int i, PopupUtils.OnItemClickListener onItemClickListener) {
        showListDialog(uItem, charSequenceArr, iArr, str, i, onItemClickListener, iArr == null, true);
    }

    protected void showListDialog(final UItem uItem, final CharSequence[] charSequenceArr, int[] iArr, String str, final int i, final PopupUtils.OnItemClickListener onItemClickListener, boolean z, final boolean z2) {
        if (getParentActivity() == null) {
            return;
        }
        PopupUtils.showDialog(charSequenceArr, iArr, str, i, getContext(), new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity$$ExternalSyntheticLambda0
            @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
            public final void onClick(int i2) {
                this.f$0.lambda$showListDialog$3(z2, i, onItemClickListener, uItem, charSequenceArr, i2);
            }
        }, getResourceProvider(), z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showListDialog$3(boolean z, int i, PopupUtils.OnItemClickListener onItemClickListener, UItem uItem, CharSequence[] charSequenceArr, int i2) {
        if (z && i == i2) {
            return;
        }
        onItemClickListener.onClick(i2);
        View viewFindViewByItemId = this.listView.findViewByItemId(uItem.f2017id);
        if (viewFindViewByItemId instanceof TextCell) {
            ((TextCell) viewFindViewByItemId).setValue(charSequenceArr[i2], true);
        }
        this.listView.adapter.update(true);
    }

    protected void showRestartBulletin() {
        BulletinFactory.m1267of(this).createSimpleBulletin(C2369R.raw.info, LocaleController.getString(C2369R.string.RestartRequired), LocaleController.getString(C2369R.string.BotUnblock), new Runnable() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$showRestartBulletin$4();
            }
        }).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showRestartBulletin$4() {
        Context context = getContext();
        Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        Intent intentMakeRestartActivityTask = Intent.makeRestartActivityTask(launchIntentForPackage == null ? null : launchIntentForPackage.getComponent());
        intentMakeRestartActivityTask.setPackage(context.getPackageName());
        context.startActivity(intentMakeRestartActivityTask);
        Runtime.getRuntime().exit(0);
    }

    protected void toggleBooleanSettingAndRefresh(String str, UItem uItem, Consumer consumer) {
        toggleBooleanSettingAndRefresh(ExteraConfig.preferences, str, uItem, consumer);
    }

    protected void toggleBooleanSettingAndRefresh(SharedPreferences sharedPreferences, String str, UItem uItem, Consumer consumer) {
        boolean z = !uItem.checked;
        consumer.accept(Boolean.valueOf(z));
        sharedPreferences.edit().putBoolean(str, z).apply();
        uItem.setChecked(z);
        View viewFindViewByItemId = this.listView.findViewByItemId(uItem.f2017id);
        if (viewFindViewByItemId instanceof CheckBoxCell) {
            ((CheckBoxCell) viewFindViewByItemId).setChecked(z, true);
        }
        this.listView.adapter.update(true);
    }

    protected void changeIntSetting(String str, int i) {
        ExteraConfig.editor.putInt(str, i).apply();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean isLightStatusBar() {
        return !hasWhiteActionBar() ? super.isLightStatusBar() : ColorUtils.calculateLuminance(getThemedColor(Theme.key_windowBackgroundWhite)) > 0.699999988079071d;
    }

    protected boolean onLongClick(UItem uItem, View view, int i, float f, float f2) {
        final String firstSettingLink = SettingsRegistry.getInstance().getFirstSettingLink(getClass(), uItem);
        if (TextUtils.isEmpty(firstSettingLink)) {
            return false;
        }
        view.performHapticFeedback(VibratorUtils.getType(3), 1);
        ItemOptions.makeOptions(this, view).add(C2369R.drawable.msg_copy, LocaleController.getString(C2369R.string.CopyLink), new Runnable() { // from class: com.exteragram.messenger.preferences.BasePreferencesActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onLongClick$5(firstSettingLink);
            }
        }).show();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$5(String str) {
        if (AndroidUtilities.addToClipboard(str)) {
            BulletinFactory.m1267of(this).createCopyBulletin(LocaleController.getString(C2369R.string.LinkCopied)).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void checkScroll(boolean r10) {
        /*
            r9 = this;
            androidx.recyclerview.widget.LinearLayoutManager r0 = r9.layoutManager
            int r0 = r0.findFirstVisibleItemPosition()
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L3d
            org.telegram.ui.Components.UniversalRecyclerView r3 = r9.listView
            androidx.recyclerview.widget.RecyclerView$ViewHolder r0 = r3.findViewHolderForAdapterPosition(r0)
            if (r0 == 0) goto L3d
            android.view.View r0 = r0.itemView
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            android.view.View r0 = r0.getChildAt(r1)
            boolean r3 = r0 instanceof com.exteragram.messenger.preferences.components.HeaderSettingsCell
            if (r3 == 0) goto L3d
            com.exteragram.messenger.preferences.components.HeaderSettingsCell r0 = (com.exteragram.messenger.preferences.components.HeaderSettingsCell) r0
            android.widget.TextView r3 = r0.titleTextView
            int[] r4 = r9.location
            r3.getLocationOnScreen(r4)
            int[] r3 = r9.location
            r3 = r3[r2]
            android.widget.TextView r0 = r0.titleTextView
            int r0 = r0.getMeasuredHeight()
            int r3 = r3 + r0
            org.telegram.ui.ActionBar.ActionBar r0 = r9.actionBar
            int r0 = r0.getBottom()
            if (r3 >= r0) goto L3b
            goto L3d
        L3b:
            r0 = 0
            goto L3e
        L3d:
            r0 = 1
        L3e:
            android.view.View r3 = r9.actionBarBackground
            java.lang.Object r3 = r3.getTag()
            if (r3 != 0) goto L48
            r3 = 1
            goto L49
        L48:
            r3 = 0
        L49:
            if (r0 == r3) goto Lcb
            android.view.View r3 = r9.actionBarBackground
            r4 = 0
            if (r0 == 0) goto L52
            r5 = r4
            goto L56
        L52:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r2)
        L56:
            r3.setTag(r5)
            android.animation.AnimatorSet r3 = r9.actionBarAnimator
            if (r3 == 0) goto L62
            r3.cancel()
            r9.actionBarAnimator = r4
        L62:
            r3 = 0
            r4 = 1065353216(0x3f800000, float:1.0)
            if (r10 == 0) goto Lb3
            android.animation.AnimatorSet r10 = new android.animation.AnimatorSet
            r10.<init>()
            r9.actionBarAnimator = r10
            android.view.View r5 = r9.actionBarBackground
            android.util.Property r6 = android.view.View.ALPHA
            if (r0 == 0) goto L77
            r7 = 1065353216(0x3f800000, float:1.0)
            goto L78
        L77:
            r7 = 0
        L78:
            float[] r8 = new float[r2]
            r8[r1] = r7
            android.animation.ObjectAnimator r5 = android.animation.ObjectAnimator.ofFloat(r5, r6, r8)
            org.telegram.ui.ActionBar.ActionBar r7 = r9.actionBar
            org.telegram.ui.ActionBar.SimpleTextView r7 = r7.getTitleTextView()
            if (r0 == 0) goto L8a
            r3 = 1065353216(0x3f800000, float:1.0)
        L8a:
            float[] r0 = new float[r2]
            r0[r1] = r3
            android.animation.ObjectAnimator r0 = android.animation.ObjectAnimator.ofFloat(r7, r6, r0)
            r3 = 2
            android.animation.Animator[] r3 = new android.animation.Animator[r3]
            r3[r1] = r5
            r3[r2] = r0
            r10.playTogether(r3)
            android.animation.AnimatorSet r10 = r9.actionBarAnimator
            r0 = 250(0xfa, double:1.235E-321)
            r10.setDuration(r0)
            android.animation.AnimatorSet r10 = r9.actionBarAnimator
            com.exteragram.messenger.preferences.BasePreferencesActivity$5 r0 = new com.exteragram.messenger.preferences.BasePreferencesActivity$5
            r0.<init>()
            r10.addListener(r0)
            android.animation.AnimatorSet r10 = r9.actionBarAnimator
            r10.start()
            return
        Lb3:
            android.view.View r10 = r9.actionBarBackground
            if (r0 == 0) goto Lba
            r1 = 1065353216(0x3f800000, float:1.0)
            goto Lbb
        Lba:
            r1 = 0
        Lbb:
            r10.setAlpha(r1)
            org.telegram.ui.ActionBar.ActionBar r10 = r9.actionBar
            org.telegram.ui.ActionBar.SimpleTextView r10 = r10.getTitleTextView()
            if (r0 == 0) goto Lc8
            r3 = 1065353216(0x3f800000, float:1.0)
        Lc8:
            r10.setAlpha(r3)
        Lcb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.preferences.BasePreferencesActivity.checkScroll(boolean):void");
    }
}
