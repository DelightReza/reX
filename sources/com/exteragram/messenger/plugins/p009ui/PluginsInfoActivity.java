package com.exteragram.messenger.plugins.p009ui;

import android.content.SharedPreferences;
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.view.View;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.utils.text.LocaleUtils;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.browser.Browser;
import org.telegram.p023ui.Cells.TextCheckCell;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;

/* loaded from: classes.dex */
public class PluginsInfoActivity extends BasePreferencesActivity {

    public enum PreferenceItem {
        DEVELOPER_MODE,
        COMPACT_VIEW,
        SAFE_MODE,
        DOCUMENTATION,
        TRUSTED_PLUGINS;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.PluginsEngine);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList<UItem> arrayList, UniversalAdapter universalAdapter) {
        SpannableString spannableString;
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Settings)));
        arrayList.add(UItem.asCheck(PreferenceItem.DEVELOPER_MODE.getId(), LocaleController.getString(C2369R.string.PluginsDevMode), C2369R.drawable.msg_settings).setChecked(ExteraConfig.pluginsDevMode).setEnabled(ExteraConfig.pluginsEngine).setSearchable(this).setLinkAlias("pluginsDeveloperMode", this));
        arrayList.add(UItem.asCheck(PreferenceItem.COMPACT_VIEW.getId(), LocaleController.getString(C2369R.string.PluginsCompactView), C2369R.drawable.msg_topics).setChecked(ExteraConfig.pluginsCompactView).setEnabled(ExteraConfig.pluginsEngine).setSearchable(this).setLinkAlias("pluginsCompactView", this));
        arrayList.add(UItem.asCheck(PreferenceItem.SAFE_MODE.getId(), LocaleController.getString(C2369R.string.PluginsSafeMode), C2369R.drawable.msg2_secret).setChecked(ExteraConfig.pluginsSafeMode).setSearchable(this).setLinkAlias("pluginsSafeMode", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.PluginsSafeModeInfo2)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Links)));
        UItem linkAlias = UItem.asButton(PreferenceItem.DOCUMENTATION.getId(), LocaleController.getString(C2369R.string.PluginsDocumentation)).setSearchable(this).setLinkAlias("pluginsDocumentation", this);
        linkAlias.iconResId = C2369R.drawable.menu_intro;
        arrayList.add(linkAlias);
        UItem linkAlias2 = UItem.asButton(PreferenceItem.TRUSTED_PLUGINS.getId(), LocaleController.getString(C2369R.string.PluginsTrusted)).accent().setSearchable(this).setLinkAlias("trustedPlugins", this);
        linkAlias2.iconResId = C2369R.drawable.msg2_policy;
        arrayList.add(linkAlias2);
        String string = LocaleController.getString(C2369R.string.PluginsPoweredBy);
        if (Build.VERSION.SDK_INT >= 24) {
            spannableString = new SpannableString(Html.fromHtml(string, 0));
        } else {
            spannableString = new SpannableString(Html.fromHtml(string));
        }
        arrayList.add(UItem.asShadow(LocaleUtils.formatWithHtmlURLs(spannableString)));
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        if (i2 <= 0 || i2 > PreferenceItem.values().length) {
            return;
        }
        PreferenceItem preferenceItem = PreferenceItem.values()[uItem.f2017id - 1];
        if ((view instanceof TextCheckCell) && (ExteraConfig.pluginsEngine || preferenceItem == PreferenceItem.SAFE_MODE)) {
            int i3 = C08711.f186x73db1599[preferenceItem.ordinal()];
            if (i3 == 1) {
                toggleBooleanSettingAndRefresh("pluginsDevMode", uItem, new Consumer() { // from class: com.exteragram.messenger.plugins.ui.PluginsInfoActivity$$ExternalSyntheticLambda0
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$onClick$0((Boolean) obj);
                    }
                });
                return;
            }
            if (i3 == 2) {
                toggleBooleanSettingAndRefresh("pluginsCompactView", uItem, new Consumer() { // from class: com.exteragram.messenger.plugins.ui.PluginsInfoActivity$$ExternalSyntheticLambda1
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        PluginsInfoActivity.m1930$r8$lambda$pWKtYTyPbBtRw4e0nvUkinwVnM((Boolean) obj);
                    }
                });
                return;
            } else {
                if (i3 != 3) {
                    return;
                }
                final SharedPreferences sharedPreferences = PluginsController.getInstance().preferences;
                toggleBooleanSettingAndRefresh(sharedPreferences, "had_crash", uItem, new Consumer() { // from class: com.exteragram.messenger.plugins.ui.PluginsInfoActivity$$ExternalSyntheticLambda2
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        PluginsInfoActivity.$r8$lambda$W4VL9MhcoPOWziaGyIvwfRWHxNQ(sharedPreferences, (Boolean) obj);
                    }
                });
                return;
            }
        }
        PreferenceItem preferenceItem2 = PreferenceItem.DOCUMENTATION;
        if (preferenceItem == preferenceItem2 || preferenceItem == PreferenceItem.TRUSTED_PLUGINS) {
            Browser.openUrl(getParentActivity(), preferenceItem == preferenceItem2 ? "http://plugins.exteragram.app/" : "https://t.me/addlist/pPhOtEq00KhjYTc6");
        }
    }

    /* renamed from: com.exteragram.messenger.plugins.ui.PluginsInfoActivity$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C08711 {

        /* renamed from: $SwitchMap$com$exteragram$messenger$plugins$ui$PluginsInfoActivity$PreferenceItem */
        static final /* synthetic */ int[] f186x73db1599;

        static {
            int[] iArr = new int[PreferenceItem.values().length];
            f186x73db1599 = iArr;
            try {
                iArr[PreferenceItem.DEVELOPER_MODE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f186x73db1599[PreferenceItem.COMPACT_VIEW.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f186x73db1599[PreferenceItem.SAFE_MODE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$0(Boolean bool) {
        ExteraConfig.pluginsDevMode = bool.booleanValue();
        PluginsController.getInstance().checkDevServers();
        BulletinFactory bulletinFactoryM1267of = BulletinFactory.m1267of(this);
        boolean z = ExteraConfig.pluginsDevMode;
        bulletinFactoryM1267of.createSimpleBulletin(z ? C2369R.raw.contact_check : C2369R.raw.error, LocaleController.getString(z ? C2369R.string.PluginsDevServerLaunched : C2369R.string.PluginsDevServerStopped)).show();
    }

    /* renamed from: $r8$lambda$pWKtYTy-PbBtRw4e0nvUkinwVnM, reason: not valid java name */
    public static /* synthetic */ void m1930$r8$lambda$pWKtYTyPbBtRw4e0nvUkinwVnM(Boolean bool) {
        ExteraConfig.pluginsCompactView = bool.booleanValue();
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.reloadInterface, new Object[0]);
    }

    public static /* synthetic */ void $r8$lambda$W4VL9MhcoPOWziaGyIvwfRWHxNQ(SharedPreferences sharedPreferences, Boolean bool) {
        ExteraConfig.pluginsSafeMode = bool.booleanValue();
        if (bool.booleanValue()) {
            sharedPreferences.edit().putString("crashed_plugin_id", "manual!").apply();
        } else {
            sharedPreferences.edit().remove("crashed_plugin_id").apply();
        }
        PluginsController.getInstance().restart();
    }
}
