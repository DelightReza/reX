package com.exteragram.messenger.icons.p007ui;

import android.R;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.icons.BaseIconPacks;
import com.exteragram.messenger.icons.IconManager;
import com.exteragram.messenger.icons.IconPack;
import com.exteragram.messenger.icons.p007ui.components.IconPackCell;
import com.exteragram.messenger.icons.p007ui.components.NewIconPackBottomSheet;
import com.exteragram.messenger.icons.p007ui.picker.IconPickerController;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.utils.p011ui.CanvasUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.ItemOptions;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.ScaleStateListAnimator;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;
import org.telegram.p023ui.LaunchActivity;
import p017j$.util.Collection;
import p017j$.util.function.Predicate$CC;

/* loaded from: classes3.dex */
public class IconPacksActivity extends BasePreferencesActivity implements NotificationCenter.NotificationCenterDelegate {
    private ImageView floatingButton;
    private boolean floatingHidden;
    private final AccelerateDecelerateInterpolator floatingInterpolator = new AccelerateDecelerateInterpolator();
    private int prevPosition;
    private int prevTop;
    private Runnable reorderRunnable;
    private boolean scrollUpdated;

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        NotificationCenter.getGlobalInstance().addObserver(this, NotificationCenter.iconPackUpdated);
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getGlobalInstance().removeObserver(this, NotificationCenter.iconPackUpdated);
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        UniversalRecyclerView universalRecyclerView;
        UniversalAdapter universalAdapter;
        if (i != NotificationCenter.iconPackUpdated || (universalRecyclerView = this.listView) == null || (universalAdapter = universalRecyclerView.adapter) == null) {
            return;
        }
        universalAdapter.update(true);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void checkInsets() {
        FrameLayout.LayoutParams layoutParams;
        ImageView imageView = this.floatingButton;
        if (imageView != null && (layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams()) != null) {
            layoutParams.bottomMargin = AndroidUtilities.m1146dp(14.0f) + this.windowInsetsStateHolder.getCurrentNavigationBarInset();
            this.floatingButton.setLayoutParams(layoutParams);
        }
        super.checkInsets();
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        View viewCreateView = super.createView(context);
        ImageView imageView = new ImageView(context);
        this.floatingButton = imageView;
        imageView.setVisibility(0);
        this.floatingButton.setScaleType(ImageView.ScaleType.CENTER);
        ScaleStateListAnimator.apply(this.floatingButton);
        this.floatingButton.setBackground(CanvasUtils.createFabBackground());
        this.floatingButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_actionIcon), PorterDuff.Mode.MULTIPLY));
        this.floatingButton.setImageResource(C2369R.drawable.msg_add);
        this.floatingButton.setContentDescription(LocaleController.getString(C2369R.string.NewIconPack));
        StateListAnimator stateListAnimator = new StateListAnimator();
        stateListAnimator.addState(new int[]{R.attr.state_pressed}, ObjectAnimator.ofFloat(this.floatingButton, "translationZ", AndroidUtilities.m1146dp(2.0f), AndroidUtilities.m1146dp(4.0f)).setDuration(200L));
        stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this.floatingButton, "translationZ", AndroidUtilities.m1146dp(4.0f), AndroidUtilities.m1146dp(2.0f)).setDuration(200L));
        this.floatingButton.setStateListAnimator(stateListAnimator);
        this.floatingButton.setOutlineProvider(new ViewOutlineProvider() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity.1
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                if (ExteraConfig.squareFab) {
                    outline.setRoundRect(0, 0, AndroidUtilities.m1146dp(56.0f), AndroidUtilities.m1146dp(56.0f), AndroidUtilities.m1146dp(16.0f));
                } else {
                    outline.setOval(0, 0, AndroidUtilities.m1146dp(56.0f), AndroidUtilities.m1146dp(56.0f));
                }
            }
        });
        if (viewCreateView instanceof FrameLayout) {
            FrameLayout frameLayout = (FrameLayout) viewCreateView;
            ImageView imageView2 = this.floatingButton;
            boolean z = LocaleController.isRTL;
            frameLayout.addView(imageView2, LayoutHelper.createFrame(56, 56.0f, (z ? 3 : 5) | 80, z ? 14.0f : 0.0f, 0.0f, z ? 0.0f : 14.0f, 14.0f));
        }
        this.floatingButton.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$0(view);
            }
        });
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView != null) {
            universalRecyclerView.allowReorder(true);
            this.listView.setReorderHandleOnly(true);
            this.listView.listenReorder(new Utilities.Callback2() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity$$ExternalSyntheticLambda4
                @Override // org.telegram.messenger.Utilities.Callback2
                public final void run(Object obj, Object obj2) {
                    this.f$0.updateConfigFromReorder(((Integer) obj).intValue(), (ArrayList) obj2);
                }
            });
            this.listView.setOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity.2
                @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    boolean z2;
                    if (IconPacksActivity.this.floatingButton.getVisibility() != 8) {
                        View childAt = recyclerView.getChildAt(0);
                        int top = childAt != null ? childAt.getTop() : 0;
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) ((BasePreferencesActivity) IconPacksActivity.this).listView.getLayoutManager();
                        if (IconPacksActivity.this.prevPosition == linearLayoutManager.findFirstVisibleItemPosition()) {
                            int i3 = IconPacksActivity.this.prevTop - top;
                            z2 = top < IconPacksActivity.this.prevTop;
                            if (Math.abs(i3) > 1) {
                            }
                            if (z && IconPacksActivity.this.scrollUpdated) {
                                IconPacksActivity.this.hideFloatingButton(z2);
                            }
                            IconPacksActivity.this.prevPosition = linearLayoutManager.findFirstVisibleItemPosition();
                            IconPacksActivity.this.prevTop = top;
                            IconPacksActivity.this.scrollUpdated = true;
                        }
                        z2 = linearLayoutManager.findFirstVisibleItemPosition() > IconPacksActivity.this.prevPosition;
                        z = true;
                        if (z) {
                            IconPacksActivity.this.hideFloatingButton(z2);
                        }
                        IconPacksActivity.this.prevPosition = linearLayoutManager.findFirstVisibleItemPosition();
                        IconPacksActivity.this.prevTop = top;
                        IconPacksActivity.this.scrollUpdated = true;
                    }
                }
            });
        }
        return viewCreateView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$0(View view) {
        new NewIconPackBottomSheet(this, getContext()).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void hideFloatingButton(boolean z) {
        if (this.floatingHidden == z) {
            return;
        }
        this.floatingHidden = z;
        ObjectAnimator duration = ObjectAnimator.ofFloat(this.floatingButton, "translationY", z ? AndroidUtilities.m1146dp(100.0f) : 0.0f).setDuration(300L);
        duration.setInterpolator(this.floatingInterpolator);
        this.floatingButton.setClickable(!z);
        duration.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateConfigFromReorder(int i, ArrayList arrayList) {
        UniversalAdapter universalAdapter;
        String str;
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i2 = 0;
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            Object obj2 = ((UItem) obj).object;
            if (obj2 instanceof IconPack) {
                arrayList2.add(((IconPack) obj2).getId());
            }
        }
        ArrayList arrayList3 = ExteraConfig.iconPacksLayout;
        int size2 = arrayList3.size();
        int i4 = 0;
        int i5 = 0;
        while (i5 < size2) {
            Object obj3 = arrayList3.get(i5);
            i5++;
            String str2 = (String) obj3;
            if (!str2.startsWith("base.") && getPackById(str2) != null) {
                i4++;
            }
        }
        boolean z = i4 > 1 && i == 0;
        if (z) {
            ArrayList arrayList4 = ExteraConfig.iconPacksLayout;
            int size3 = arrayList4.size();
            while (true) {
                if (i2 >= size3) {
                    str = null;
                    break;
                }
                Object obj4 = arrayList4.get(i2);
                i2++;
                str = (String) obj4;
                if (str.startsWith("base.")) {
                    break;
                }
            }
            ExteraConfig.iconPacksLayout.clear();
            ExteraConfig.iconPacksLayout.addAll(arrayList2);
            if (str != null) {
                ExteraConfig.iconPacksLayout.add(str);
            }
        } else {
            ExteraConfig.iconPacksHidden.clear();
            ExteraConfig.iconPacksHidden.addAll(arrayList2);
        }
        ExteraConfig.saveIconPacksLayout();
        if (z) {
            Runnable runnable = this.reorderRunnable;
            if (runnable != null) {
                AndroidUtilities.cancelRunOnUIThread(runnable);
            }
            Runnable runnable2 = new Runnable() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    IconManager.INSTANCE.initialize(true);
                }
            };
            this.reorderRunnable = runnable2;
            AndroidUtilities.runOnUIThread(runnable2, 500L);
        }
        UniversalRecyclerView universalRecyclerView = this.listView;
        if (universalRecyclerView == null || (universalAdapter = universalRecyclerView.adapter) == null) {
            return;
        }
        universalAdapter.update(true);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.IconPacks);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        boolean z;
        boolean z2;
        IconPack packById;
        IconPack packById2;
        Iterator it = IconManager.INSTANCE.getCustomPacks().iterator();
        boolean z3 = false;
        while (it.hasNext()) {
            String id = ((IconPack) it.next()).getId();
            if (!ExteraConfig.iconPacksLayout.contains(id) && !ExteraConfig.iconPacksHidden.contains(id)) {
                ExteraConfig.iconPacksHidden.add(id);
                z3 = true;
            }
        }
        if (z3) {
            ExteraConfig.saveIconPacksLayout();
        }
        String str = "base.default";
        String[] strArr = {"base.default", "base.solar", "base.remix"};
        ArrayList arrayList2 = ExteraConfig.iconPacksLayout;
        int size = arrayList2.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            Object obj = arrayList2.get(i);
            i++;
            String str2 = (String) obj;
            if (str2.startsWith("base.")) {
                str = str2;
                break;
            }
        }
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.BasePacks)));
        universalAdapter.whiteSectionStart();
        for (int i2 = 0; i2 < 3; i2++) {
            String str3 = strArr[i2];
            IconPack packById3 = getPackById(str3);
            if (packById3 != null) {
                UItem uItemAsIconPackCell = IconPackCell.Factory.asIconPackCell(packById3);
                uItemAsIconPackCell.checked = str3.equals(str);
                arrayList.add(uItemAsIconPackCell);
            }
        }
        universalAdapter.whiteSectionEnd();
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.BaseIconPackInfo)));
        ArrayList arrayList3 = ExteraConfig.iconPacksLayout;
        int size2 = arrayList3.size();
        int i3 = 0;
        while (true) {
            if (i3 >= size2) {
                z = false;
                break;
            }
            Object obj2 = arrayList3.get(i3);
            i3++;
            if (!((String) obj2).startsWith("base.")) {
                z = true;
                break;
            }
        }
        if (z) {
            arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.EnabledPacks)));
            universalAdapter.whiteSectionStart();
            ArrayList arrayList4 = ExteraConfig.iconPacksLayout;
            int size3 = arrayList4.size();
            int i4 = 0;
            int i5 = 0;
            while (i5 < size3) {
                Object obj3 = arrayList4.get(i5);
                i5++;
                String str4 = (String) obj3;
                if (!str4.startsWith("base.") && getPackById(str4) != null) {
                    i4++;
                }
            }
            if (i4 > 1) {
                universalAdapter.reorderSectionStart();
            }
            ArrayList arrayList5 = ExteraConfig.iconPacksLayout;
            int size4 = arrayList5.size();
            int i6 = 0;
            while (i6 < size4) {
                Object obj4 = arrayList5.get(i6);
                i6++;
                String str5 = (String) obj4;
                if (!str5.startsWith("base.") && (packById2 = getPackById(str5)) != null) {
                    arrayList.add(IconPackCell.Factory.asIconPackCell(packById2).setReordering(i4 > 1));
                }
            }
            if (i4 > 1) {
                universalAdapter.reorderSectionEnd();
            }
            universalAdapter.whiteSectionEnd();
        }
        ArrayList arrayList6 = ExteraConfig.iconPacksHidden;
        int size5 = arrayList6.size();
        int i7 = 0;
        while (true) {
            if (i7 >= size5) {
                z2 = false;
                break;
            }
            Object obj5 = arrayList6.get(i7);
            i7++;
            if (!((String) obj5).startsWith("base.")) {
                z2 = true;
                break;
            }
        }
        if (z2) {
            if (z) {
                arrayList.add(UItem.asShadow());
            }
            arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.AllPacks)));
            universalAdapter.whiteSectionStart();
            ArrayList arrayList7 = ExteraConfig.iconPacksHidden;
            int size6 = arrayList7.size();
            int i8 = 0;
            int i9 = 0;
            while (i9 < size6) {
                Object obj6 = arrayList7.get(i9);
                i9++;
                String str6 = (String) obj6;
                if (!str6.startsWith("base.") && getPackById(str6) != null) {
                    i8++;
                }
            }
            if (i8 > 1) {
                universalAdapter.reorderSectionStart();
            }
            ArrayList arrayList8 = ExteraConfig.iconPacksHidden;
            int size7 = arrayList8.size();
            int i10 = 0;
            while (i10 < size7) {
                Object obj7 = arrayList8.get(i10);
                i10++;
                String str7 = (String) obj7;
                if (!str7.startsWith("base.") && (packById = getPackById(str7)) != null) {
                    arrayList.add(IconPackCell.Factory.asIconPackCell(packById).setReordering(i8 > 1));
                }
            }
            if (i8 > 1) {
                universalAdapter.reorderSectionEnd();
            }
            universalAdapter.whiteSectionEnd();
        }
        if (z || z2) {
            arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.IconPacksHint)));
        }
    }

    private IconPack getPackById(String str) {
        if (str.startsWith("base.")) {
            return BaseIconPacks.INSTANCE.getBasePack(str);
        }
        return IconManager.INSTANCE.findPackById(str);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        Object obj = uItem.object;
        if (obj instanceof IconPack) {
            String id = ((IconPack) obj).getId();
            if (id.startsWith("base.")) {
                if (ExteraConfig.iconPacksLayout.contains(id)) {
                    return;
                }
                Collection.EL.removeIf(ExteraConfig.iconPacksLayout, new Predicate() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity$$ExternalSyntheticLambda5
                    public /* synthetic */ Predicate and(Predicate predicate) {
                        return Predicate$CC.$default$and(this, predicate);
                    }

                    public /* synthetic */ Predicate negate() {
                        return Predicate$CC.$default$negate(this);
                    }

                    /* renamed from: or */
                    public /* synthetic */ Predicate m208or(Predicate predicate) {
                        return Predicate$CC.$default$or(this, predicate);
                    }

                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        return ((String) obj2).startsWith("base.");
                    }
                });
                ExteraConfig.iconPacksLayout.add(id);
                ExteraConfig.saveIconPacksLayout();
                IconManager.INSTANCE.initialize(true);
                this.listView.adapter.update(true);
                return;
            }
            if (ExteraConfig.iconPacksLayout.contains(id)) {
                ExteraConfig.iconPacksLayout.remove(id);
                if (!ExteraConfig.iconPacksHidden.contains(id)) {
                    ExteraConfig.iconPacksHidden.add(0, id);
                }
            } else if (ExteraConfig.iconPacksHidden.contains(id)) {
                ExteraConfig.iconPacksHidden.remove(id);
                ExteraConfig.iconPacksLayout.add(0, id);
            }
            ExteraConfig.saveIconPacksLayout();
            IconManager.INSTANCE.initialize(true);
            this.listView.adapter.update(true);
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public void onResume() {
        super.onResume();
        this.listView.adapter.update(true);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected boolean onLongClick(UItem uItem, View view, int i, float f, float f2) {
        Object obj = uItem.object;
        if (obj instanceof IconPack) {
            final IconPack iconPack = (IconPack) obj;
            String id = iconPack.getId();
            if (id.startsWith("base.")) {
                return false;
            }
            ItemOptions.makeOptions(this, view).addIf(ExteraConfig.iconPacksLayout.contains(id), C2369R.drawable.msg_edit, LocaleController.getString(C2369R.string.Edit), new Runnable() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onLongClick$3(iconPack);
                }
            }).add(C2369R.drawable.msg_share, LocaleController.getString(C2369R.string.ShareFile), new Runnable() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onLongClick$6(iconPack);
                }
            }).add(C2369R.drawable.msg_delete, (CharSequence) LocaleController.getString(C2369R.string.Delete), true, new Runnable() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onLongClick$8(iconPack);
                }
            }).show();
            return true;
        }
        return super.onLongClick(uItem, view, i, f, f2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$3(IconPack iconPack) {
        ExteraConfig.editingIconPackId = iconPack.getId();
        ExteraConfig.editor.putString("editingIconPackId", iconPack.getId()).apply();
        IconPickerController.setActive((LaunchActivity) getParentActivity(), true);
        presentFragment(new IconPacksEditorActivity(iconPack));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$6(final IconPack iconPack) {
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onLongClick$5(iconPack);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$5(IconPack iconPack) {
        final File fileBundlePackBlocking = IconManager.INSTANCE.bundlePackBlocking(iconPack.getId());
        if (fileBundlePackBlocking != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onLongClick$4(fileBundlePackBlocking);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$4(File file) {
        if (getParentActivity() == null) {
            return;
        }
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("application/zip");
        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(getParentActivity(), ApplicationLoader.getApplicationId() + ".provider", file));
        intent.addFlags(1);
        getParentActivity().startActivity(Intent.createChooser(intent, LocaleController.getString(C2369R.string.ShareFile)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$8(final IconPack iconPack) {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(getParentActivity(), getResourceProvider()).setTitle(LocaleController.getString(C2369R.string.DeletePack)).setMessage(AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.DeletePackInfo, iconPack.getName()))).setPositiveButton(LocaleController.getString(C2369R.string.Delete), new AlertDialog.OnButtonClickListener() { // from class: com.exteragram.messenger.icons.ui.IconPacksActivity$$ExternalSyntheticLambda7
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                IconManager.INSTANCE.deletePack(iconPack.getId());
            }
        }).setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null).create();
        alertDialogCreate.show();
        TextView textView = (TextView) alertDialogCreate.getButton(-1);
        if (textView != null) {
            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
    }
}
