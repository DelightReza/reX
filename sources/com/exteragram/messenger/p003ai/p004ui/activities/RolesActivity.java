package com.exteragram.messenger.p003ai.p004ui.activities;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import com.exteragram.messenger.p003ai.AiConfig;
import com.exteragram.messenger.p003ai.AiController;
import com.exteragram.messenger.p003ai.data.Role;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.ItemOptions;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.Components.UniversalRecyclerView;

/* loaded from: classes3.dex */
public class RolesActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {
    private UniversalRecyclerView listView;

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public boolean onFragmentCreate() {
        getNotificationCenter().addObserver(this, NotificationCenter.rolesUpdated);
        return super.onFragmentCreate();
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public void onFragmentDestroy() {
        getNotificationCenter().removeObserver(this, NotificationCenter.rolesUpdated);
        super.onFragmentDestroy();
    }

    @Override // org.telegram.messenger.NotificationCenter.NotificationCenterDelegate
    public void didReceivedNotification(int i, int i2, Object... objArr) {
        if (i == NotificationCenter.rolesUpdated) {
            this.listView.adapter.update(true);
        }
    }

    @Override // org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.actionBar.setBackButtonImage(C2369R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString(C2369R.string.Roles));
        this.actionBar.createMenu().addItem(0, C2369R.drawable.msg_add);
        this.actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() { // from class: com.exteragram.messenger.ai.ui.activities.RolesActivity.1
            @Override // org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick
            public void onItemClick(int i) {
                if (i == -1) {
                    RolesActivity.this.lambda$onBackPressed$371();
                } else if (i == 0) {
                    RolesActivity.this.presentFragment(new EditRoleActivity(null));
                }
            }
        });
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        UniversalRecyclerView universalRecyclerView = new UniversalRecyclerView(this, new Utilities.Callback2() { // from class: com.exteragram.messenger.ai.ui.activities.RolesActivity$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                this.f$0.fillItems((ArrayList) obj, (UniversalAdapter) obj2);
            }
        }, new Utilities.Callback5() { // from class: com.exteragram.messenger.ai.ui.activities.RolesActivity$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback5
            public final void run(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                this.f$0.onClick((UItem) obj, (View) obj2, ((Integer) obj3).intValue(), ((Float) obj4).floatValue(), ((Float) obj5).floatValue());
            }
        }, new Utilities.Callback5Return() { // from class: com.exteragram.messenger.ai.ui.activities.RolesActivity$$ExternalSyntheticLambda2
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
    public void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asTopView(LocaleController.getString(C2369R.string.RolesInfo), "exteraGramPlaceholders", "🎭"));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Suggestions)));
        for (Role role : AiController.getInstance().getSuggestedRoles()) {
            UItem checked = UItem.asRadioButton(0, role.getName(), role.getPrompt()).setChecked(role.isSelected());
            checked.object = role;
            arrayList.add(checked);
        }
        arrayList.add(UItem.asShadow(null));
        List<Role> roles = AiController.getInstance().getRoles();
        if (roles.isEmpty()) {
            return;
        }
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Roles)));
        for (Role role2 : roles) {
            UItem checked2 = UItem.asRadioButton(0, role2.getName(), role2.getPrompt()).setChecked(role2.isSelected());
            checked2.object = role2;
            arrayList.add(checked2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onClick(UItem uItem, View view, int i, float f, float f2) {
        Object obj = uItem.object;
        if (obj instanceof Role) {
            Role role = (Role) obj;
            if (role.isSelected()) {
                return;
            }
            AiConfig.setSelectedRole(role);
            this.listView.adapter.update(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onLongClick(UItem uItem, View view, int i, float f, float f2) {
        if (uItem == null) {
            return false;
        }
        Object obj = uItem.object;
        if (!(obj instanceof Role)) {
            return false;
        }
        final Role role = (Role) obj;
        if (role.isSuggestion()) {
            return false;
        }
        ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions(this, view);
        itemOptionsMakeOptions.add(C2369R.drawable.msg_edit, LocaleController.getString(C2369R.string.Edit), new Runnable() { // from class: com.exteragram.messenger.ai.ui.activities.RolesActivity$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onLongClick$0(role);
            }
        });
        itemOptionsMakeOptions.add(C2369R.drawable.msg_copy, LocaleController.getString(C2369R.string.Copy), new Runnable() { // from class: com.exteragram.messenger.ai.ui.activities.RolesActivity$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onLongClick$1(role);
            }
        });
        itemOptionsMakeOptions.add(C2369R.drawable.msg_delete, (CharSequence) LocaleController.getString(C2369R.string.Delete), true, new Runnable() { // from class: com.exteragram.messenger.ai.ui.activities.RolesActivity$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onLongClick$2(role);
            }
        });
        if (LocaleController.isRTL) {
            itemOptionsMakeOptions.setGravity(3);
        }
        itemOptionsMakeOptions.show();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$0(Role role) {
        presentFragment(new EditRoleActivity(role));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$1(Role role) {
        if (AndroidUtilities.addToClipboard(role.getName() + "\n" + role.getPrompt())) {
            return;
        }
        BulletinFactory.m1267of(this).createCopyBulletin(LocaleController.getString(C2369R.string.TextCopied)).show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLongClick$2(Role role) {
        boolean zIsSelected = role.isSelected();
        if (AiController.getInstance().removeRole(role)) {
            if (zIsSelected) {
                AiConfig.setSelectedRole((Role) AiController.getInstance().getSuggestedRoles().get(0));
            }
            this.listView.adapter.update(true);
        }
    }
}
