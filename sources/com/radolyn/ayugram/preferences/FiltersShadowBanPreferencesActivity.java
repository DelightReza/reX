package com.radolyn.ayugram.preferences;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.radolyn.ayugram.controllers.AyuFilterCacheController;
import com.radolyn.ayugram.preferences.utils.ShadowBanPopup;
import com.radolyn.ayugram.utils.filters.AyuFilterUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.OneUIUtilities;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.DialogsActivity;
import org.telegram.p023ui.TopicsFragment;
import p017j$.util.Collection;
import p017j$.util.stream.Collectors;

/* loaded from: classes4.dex */
public class FiltersShadowBanPreferencesActivity extends BasePreferencesActivity {
    private ActionBarMenuItem addItem;
    private final Map idToUser = new HashMap();
    private List shadowBanned;

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        this.shadowBanned = new ArrayList((Collection) Collection.EL.stream(AyuFilterUtils.getShadowBanList()).sorted().collect(Collectors.toList()));
        this.idToUser.clear();
        if (!this.shadowBanned.isEmpty()) {
            arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.RegexFiltersHeader)));
            for (int i = 0; i < this.shadowBanned.size(); i++) {
                Long l = (Long) this.shadowBanned.get(i);
                long jLongValue = l.longValue();
                int i2 = OneUIUtilities.ONE_UI_4_0 + i;
                this.idToUser.put(Integer.valueOf(i2), l);
                arrayList.add(UItem.asAyuPeerCell(i2, jLongValue, null, null));
            }
            return;
        }
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.RegexFiltersListEmpty)));
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        View viewCreateView = super.createView(context);
        ActionBarMenuItem actionBarMenuItemAddItem = this.actionBar.createMenu().addItem(0, C2369R.drawable.msg_add);
        this.addItem = actionBarMenuItemAddItem;
        actionBarMenuItemAddItem.setContentDescription(LocaleController.getString(C2369R.string.Add));
        this.addItem.setVisibility(0);
        this.addItem.setTag(null);
        this.addItem.setOnClickListener(new View.OnClickListener() { // from class: com.radolyn.ayugram.preferences.FiltersShadowBanPreferencesActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$createView$1(view);
            }
        });
        return viewCreateView;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createView$1(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("dialogsType", 4);
        bundle.putBoolean("onlySelect", true);
        bundle.putBoolean("canSelectTopics", false);
        bundle.putBoolean("allowSwitchAccount", true);
        bundle.putBoolean("checkCanWrite", false);
        DialogsActivity dialogsActivity = new DialogsActivity(bundle);
        dialogsActivity.setDelegate(new DialogsActivity.DialogsActivityDelegate() { // from class: com.radolyn.ayugram.preferences.FiltersShadowBanPreferencesActivity$$ExternalSyntheticLambda1
            @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
            public /* synthetic */ boolean canSelectStories() {
                return DialogsActivity.DialogsActivityDelegate.CC.$default$canSelectStories(this);
            }

            @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
            public final boolean didSelectDialogs(DialogsActivity dialogsActivity2, ArrayList arrayList, CharSequence charSequence, boolean z, boolean z2, int i, TopicsFragment topicsFragment) {
                return this.f$0.lambda$createView$0(dialogsActivity2, arrayList, charSequence, z, z2, i, topicsFragment);
            }

            @Override // org.telegram.ui.DialogsActivity.DialogsActivityDelegate
            public /* synthetic */ boolean didSelectStories(DialogsActivity dialogsActivity2) {
                return DialogsActivity.DialogsActivityDelegate.CC.$default$didSelectStories(this, dialogsActivity2);
            }
        });
        presentFragment(dialogsActivity);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$createView$0(DialogsActivity dialogsActivity, ArrayList arrayList, CharSequence charSequence, boolean z, boolean z2, int i, TopicsFragment topicsFragment) {
        if (arrayList != null && !arrayList.isEmpty()) {
            AyuFilterUtils.addShadowBan(((MessagesStorage.TopicKey) arrayList.get(0)).dialogId);
            AyuFilterCacheController.rebuildCache();
            dialogsActivity.lambda$onBackPressed$371();
            this.listView.adapter.update(true);
        }
        return true;
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2;
        Long l;
        if (uItem == null || (i2 = uItem.f2017id) < 40000 || (l = (Long) this.idToUser.get(Integer.valueOf(i2))) == null) {
            return;
        }
        ShadowBanPopup.show(this, view, f, f2, l.longValue());
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.FiltersShadowBan);
    }
}
