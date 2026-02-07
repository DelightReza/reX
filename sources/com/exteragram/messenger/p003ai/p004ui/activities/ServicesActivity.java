package com.exteragram.messenger.p003ai.p004ui.activities;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import com.exteragram.messenger.p003ai.AiConfig;
import com.exteragram.messenger.p003ai.AiController;
import com.exteragram.messenger.p003ai.data.Service;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.CombinedDrawable;
import org.telegram.p023ui.Components.ItemOptions;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;

/* loaded from: classes3.dex */
public class ServicesActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private UniversalRecyclerView listView;

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        getNotificationCenter().addObserver(this, NotificationCenter.servicesUpdated);
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        getNotificationCenter().removeObserver(this, NotificationCenter.servicesUpdated);
        super.onFragmentDestroy();
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.servicesUpdated) {
            this.listView.adapter.update(true);
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setBackButtonImage(C2369R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString(C2369R.string.Services));
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: com.exteragram.messenger.ai.ui.activities.ServicesActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    ServicesActivity.this.lambda$onBackPressed$371();
                }
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        UniversalRecyclerView universalRecyclerView = new UniversalRecyclerView(this, new Utilities.Callback2() { // from class: com.exteragram.messenger.ai.ui.activities.ServicesActivity$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) throws Resources.NotFoundException {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, new Utilities.Callback5() { // from class: com.exteragram.messenger.ai.ui.activities.ServicesActivity$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback5
            public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                this.f$0.onClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
            }
        }, new Utilities.Callback5Return() { // from class: com.exteragram.messenger.ai.ui.activities.ServicesActivity$$ExternalSyntheticLambda2
            @Override // org.telegram.messenger.Utilities.Callback5Return
            public final Object run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                return Boolean.valueOf(this.f$0.onLongClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue()));
            }
        });
        this.listView = universalRecyclerView;
        universalRecyclerView.adapter.setUseSectionStyle(true);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.fragmentView = frameLayout;
        return frameLayout;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) throws Resources.NotFoundException {
        arrayList.add(UItem.asTopView(LocaleController.getString(C2369R.string.ServicesInfo), "exteraGramPlaceholders", "🔑"));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Services)));
        for (Service service : AiController.getInstance().getAll()) {
            UItem checked = UItem.asRadioButton(0, service.getModel(), service.getUrl()).setChecked(service.isSelected());
            checked.object = service;
            arrayList.add(checked);
        }
        Drawable drawable = getContext().getResources().getDrawable(C2369R.drawable.poll_add_circle);
        Drawable drawable2 = getContext().getResources().getDrawable(C2369R.drawable.poll_add_plus);
        int themedColor = getThemedColor(Theme.key_switchTrackChecked);
        PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
        drawable.setColorFilter(new PorterDuffColorFilter(themedColor, mode));
        drawable2.setColorFilter(new PorterDuffColorFilter(getThemedColor(Theme.key_checkboxCheck), mode));
        UItem uItemAccent = UItem.asButton(1, new CombinedDrawable(drawable, drawable2) { // from class: com.exteragram.messenger.ai.ui.activities.ServicesActivity.2
            @Override // org.telegram.p023ui.Components.CombinedDrawable, android.graphics.drawable.Drawable
            public void setColorFilter(ColorFilter colorFilter) {
            }

            {
                this.translateX = AndroidUtilities.m1146dp(2.0f);
            }
        }, LocaleController.getString(C2369R.string.NewService)).accent();
        uItemAccent.pad = 61;
        arrayList.add(uItemAccent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClick(UItem uItem, View view, int i, float f, float f2) {
        if (uItem.f2017id == 0) {
            Object obj = uItem.object;
            if (obj instanceof Service) {
                Service service = (Service) obj;
                if (!service.isSelected()) {
                    AiConfig.setSelectedServices(service);
                    this.listView.adapter.update(true);
                    return;
                }
            }
        }
        if (uItem.f2017id == 1) {
            presentFragment(new EditServiceActivity());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onLongClick(UItem uItem, View view, int i, float f, float f2) {
        if (uItem == null) {
            return false;
        }
        Object obj = uItem.object;
        if (!(obj instanceof Service)) {
            return false;
        }
        final Service service = (Service) obj;
        ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions(this, view);
        itemOptionsMakeOptions.add(C2369R.drawable.msg_edit, LocaleController.getString(C2369R.string.Edit), new Runnable() { // from class: com.exteragram.messenger.ai.ui.activities.ServicesActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onLongClick$0(service);
            }
        });
        itemOptionsMakeOptions.add(C2369R.drawable.msg_copy, LocaleController.getString(C2369R.string.Copy), new Runnable() { // from class: com.exteragram.messenger.ai.ui.activities.ServicesActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onLongClick$1(service);
            }
        });
        itemOptionsMakeOptions.add(C2369R.drawable.msg_delete, (CharSequence) LocaleController.getString(C2369R.string.Delete), true, new Runnable() { // from class: com.exteragram.messenger.ai.ui.activities.ServicesActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onLongClick$2(service);
            }
        });
        if (LocaleController.isRTL) {
            itemOptionsMakeOptions.setGravity(3);
        }
        itemOptionsMakeOptions.show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$0(Service service) {
        presentFragment(new EditServiceActivity(service));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$1(Service service) {
        if (AndroidUtilities.addToClipboard(service.getUrl() + "\n" + service.getModel() + "\n" + service.getKey())) {
            return;
        }
        BulletinFactory.m1267of(this).createCopyBulletin(LocaleController.getString(C2369R.string.TextCopied)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$2(Service service) {
        if (AiController.getInstance().removeService(service)) {
            if (service.isSelected()) {
                AiConfig.clearSelectedService();
                if (!AiController.getInstance().isServicesEmpty()) {
                    AiConfig.setSelectedServices((Service) AiController.getInstance().getAll().get(0));
                }
            }
            if (AiController.getInstance().isServicesEmpty()) {
                lambda$onBackPressed$371();
            } else {
                this.listView.adapter.update(true);
            }
        }
    }
}
