package com.exteragram.messenger.p003ai.p004ui.activities;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.exteragram.messenger.p003ai.AiConfig;
import com.exteragram.messenger.p003ai.AiController;
import com.exteragram.messenger.p003ai.p004ui.AiPreferencesHeaderCell;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.google.android.exoplayer2.util.Consumer;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;

/* loaded from: classes.dex */
public class AiPreferencesActivity extends BasePreferencesActivity {
    private AiPreferencesHeaderCell headerSettingsCell;

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected boolean hasHeaderCell() {
        return true;
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected boolean hasWhiteActionBar() {
        return true;
    }

    public enum PreferenceItem {
        ENDPOINT,
        ROLE,
        SAVE_HISTORY,
        RESPONSE_STREAMING,
        SHOW_RESPONSE_ONLY,
        INSERT_AS_QUOTE;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity, org.telegram.p023ui.ActionBar.BaseFragment
    public View createView(Context context) {
        this.headerSettingsCell = new AiPreferencesHeaderCell(context);
        return super.createView(context);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.AIChat);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asCustom(this.headerSettingsCell).setTransparent(true));
        arrayList.add(UItem.asShadow());
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.General)));
        arrayList.add(UItem.asButton(PreferenceItem.ENDPOINT.getId(), C2369R.drawable.msg_language, LocaleController.getString(C2369R.string.Services), getEndpointValue()).prioritizeTitleOverValue(true).setSearchable(this).setLinkAlias("aiServices", this));
        arrayList.add(UItem.asButton(PreferenceItem.ROLE.getId(), C2369R.drawable.msg_openprofile, LocaleController.getString(C2369R.string.Roles), AiConfig.getSelectedRole()).prioritizeTitleOverValue(true).setSearchable(this).setLinkAlias("aiRoles", this));
        arrayList.add(UItem.asCheck(PreferenceItem.SAVE_HISTORY.getId(), LocaleController.getString(C2369R.string.MessageHistory), C2369R.drawable.msg_discuss).setChecked(AiConfig.saveHistory).showDivider(false).setSearchable(this).setLinkAlias("saveAiHistory", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.HistoryInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.LocalOther)));
        arrayList.add(UItem.asCheck(PreferenceItem.RESPONSE_STREAMING.getId(), LocaleController.getString(C2369R.string.ResponseStreaming), LocaleController.getString(C2369R.string.ResponseStreamingInfo), true).setChecked(AiConfig.responseStreaming).setSearchable(this).setLinkAlias("responseStreaming", this));
        arrayList.add(UItem.asCheck(PreferenceItem.SHOW_RESPONSE_ONLY.getId(), LocaleController.getString(C2369R.string.ShowResponseOnly)).setChecked(AiConfig.showResponseOnly).setSearchable(this).setLinkAlias("showResponseOnly", this));
        arrayList.add(UItem.asCheck(PreferenceItem.INSERT_AS_QUOTE.getId(), LocaleController.getString(C2369R.string.InsertResponseAsQuote)).setChecked(AiConfig.insertAsQuote).showDivider(false).setSearchable(this).setLinkAlias("insertResponseAsQuote", this));
        arrayList.add(UItem.asShadow());
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        if (i2 <= 0 || i2 > PreferenceItem.values().length) {
            return;
        }
        switch (C07591.f146x6e22124e[PreferenceItem.values()[uItem.f2017id - 1].ordinal()]) {
            case 1:
                presentFragment(new ServicesActivity());
                break;
            case 2:
                presentFragment(new RolesActivity());
                break;
            case 3:
                toggleBooleanSettingAndRefresh(AiConfig.preferences, "saveHistory", uItem, new Consumer() { // from class: com.exteragram.messenger.ai.ui.activities.AiPreferencesActivity$$ExternalSyntheticLambda0
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        AiConfig.saveHistory = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 4:
                toggleBooleanSettingAndRefresh(AiConfig.preferences, "responseStreaming", uItem, new Consumer() { // from class: com.exteragram.messenger.ai.ui.activities.AiPreferencesActivity$$ExternalSyntheticLambda1
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        AiConfig.responseStreaming = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 5:
                toggleBooleanSettingAndRefresh(AiConfig.preferences, "showResponseOnly", uItem, new Consumer() { // from class: com.exteragram.messenger.ai.ui.activities.AiPreferencesActivity$$ExternalSyntheticLambda2
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        AiConfig.showResponseOnly = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 6:
                toggleBooleanSettingAndRefresh(AiConfig.preferences, "insertAsQuote", uItem, new Consumer() { // from class: com.exteragram.messenger.ai.ui.activities.AiPreferencesActivity$$ExternalSyntheticLambda3
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        AiConfig.insertAsQuote = ((Boolean) obj).booleanValue();
                    }
                });
                break;
        }
    }

    /* renamed from: com.exteragram.messenger.ai.ui.activities.AiPreferencesActivity$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C07591 {

        /* renamed from: $SwitchMap$com$exteragram$messenger$ai$ui$activities$AiPreferencesActivity$PreferenceItem */
        static final /* synthetic */ int[] f146x6e22124e;

        static {
            int[] iArr = new int[PreferenceItem.values().length];
            f146x6e22124e = iArr;
            try {
                iArr[PreferenceItem.ENDPOINT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f146x6e22124e[PreferenceItem.ROLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f146x6e22124e[PreferenceItem.SAVE_HISTORY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f146x6e22124e[PreferenceItem.RESPONSE_STREAMING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f146x6e22124e[PreferenceItem.SHOW_RESPONSE_ONLY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f146x6e22124e[PreferenceItem.INSERT_AS_QUOTE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private String getEndpointValue() {
        try {
            String host = new URL(AiController.getInstance().getSelected().getUrl()).getHost();
            if (!TextUtils.isEmpty(host) && AiController.canUseAI()) {
                return host.contains("generativelanguage.googleapis") ? "Gemini" : host;
            }
            return LocaleController.getString(C2369R.string.BlockedEmpty);
        } catch (MalformedURLException unused) {
            return LocaleController.getString(C2369R.string.BlockedEmpty);
        }
    }
}
