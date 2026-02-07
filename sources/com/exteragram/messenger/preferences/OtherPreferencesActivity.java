package com.exteragram.messenger.preferences;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import com.android.tools.p002r8.RecordTag;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.backup.PreferencesUtils;
import com.exteragram.messenger.badges.BadgesController;
import com.exteragram.messenger.components.SupporterBottomSheet;
import com.exteragram.messenger.export.p006ui.ExportActivity;
import com.exteragram.messenger.p003ai.p004ui.AbstractC0746x1d8a54ff;
import com.exteragram.messenger.plugins.Plugin;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.PluginsController$$ExternalSyntheticBackport1;
import com.exteragram.messenger.utils.network.BankingUtils;
import com.exteragram.messenger.utils.system.VibratorUtils;
import com.google.android.exoplayer2.util.Consumer;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LinkifyPort;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_account;
import p017j$.util.Collection;
import p017j$.util.function.Predicate$CC;

/* loaded from: classes.dex */
public class OtherPreferencesActivity extends BasePreferencesActivity {
    private List<BankingUtils.Donate> donates = new ArrayList();
    private List<BankingUtils.Donate> donates2 = new ArrayList();

    public enum OtherItem {
        CRASHLYTICS,
        ANALYTICS,
        EXPORT_SETTINGS,
        EXPORT_DATA,
        RESET_SETTINGS,
        DELETE_ACCOUNT,
        DONATE;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.LocalOther);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList<UItem> arrayList, UniversalAdapter universalAdapter) {
        Map mapM212m = PluginsController$$ExternalSyntheticBackport1.m212m(new Map.Entry[]{new AbstractMap.SimpleEntry("mastercard", new IconInfo(C2369R.drawable.mastercard_icon, Theme.isCurrentThemeDark() ? -1 : -16777216)), new AbstractMap.SimpleEntry("tonkeeper", new IconInfo(C2369R.drawable.ton_icon, Theme.isCurrentThemeDark() ? -14207411 : -15722977)), new AbstractMap.SimpleEntry("space", new IconInfo(C2369R.drawable.ton_space_icon, -13587978)), new AbstractMap.SimpleEntry("boosty", new IconInfo(C2369R.drawable.boosty_icon, Theme.isCurrentThemeDark() ? -1118482 : -14406868))});
        List<BankingUtils.Donate> donates = BankingUtils.getDonates();
        this.donates = donates;
        int i = 0;
        if (!donates.isEmpty()) {
            arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Support) + " (exteraGram)"));
            for (int i2 = 0; i2 < this.donates.size(); i2++) {
                BankingUtils.Donate donate = this.donates.get(i2);
                String lowerCase = donate.name().toLowerCase();
                IconInfo iconInfo = new IconInfo(C2369R.drawable.msg_payment_card, i);
                Iterator it = mapM212m.entrySet().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    Map.Entry entry = (Map.Entry) it.next();
                    if (lowerCase.contains((CharSequence) entry.getKey())) {
                        iconInfo = (IconInfo) entry.getValue();
                        break;
                    }
                }
                UItem searchable = UItem.asButton(OtherItem.DONATE.getId() + i2, donate.name()).setSearchable(this);
                if (iconInfo.iconColor == 0) {
                    searchable.setIcon(iconInfo.iconResId);
                } else {
                    searchable.setColorfulIcon(iconInfo.iconResId, iconInfo.iconColor);
                }
                arrayList.add(searchable);
            }
            arrayList.add(UItem.asShadow(AndroidUtilities.replaceSingleTag(LocaleController.getString(C2369R.string.GetBadgeInfo), new Runnable() { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$fillItems$0();
                }
            })));
        }
        List<BankingUtils.Donate> ayuDonates = BankingUtils.getAyuDonates();
        this.donates2 = ayuDonates;
        if (!ayuDonates.isEmpty()) {
            arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Support) + " (AyuGram)"));
            for (int i3 = 0; i3 < this.donates2.size(); i3++) {
                BankingUtils.Donate donate2 = this.donates2.get(i3);
                String lowerCase2 = donate2.name().toLowerCase();
                IconInfo iconInfo2 = new IconInfo(C2369R.drawable.msg_payment_card, i);
                Iterator it2 = mapM212m.entrySet().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    Map.Entry entry2 = (Map.Entry) it2.next();
                    if (lowerCase2.contains((CharSequence) entry2.getKey())) {
                        iconInfo2 = (IconInfo) entry2.getValue();
                        break;
                    }
                }
                UItem searchable2 = UItem.asButton(OtherItem.DONATE.getId() + this.donates.size() + i3, donate2.name()).setSearchable(this);
                if (iconInfo2.iconColor == 0) {
                    searchable2.setIcon(iconInfo2.iconResId);
                } else {
                    searchable2.setColorfulIcon(iconInfo2.iconResId, iconInfo2.iconColor);
                }
                arrayList.add(searchable2);
            }
            arrayList.add(UItem.asShadow(AndroidUtilities.replaceSingleTag(LocaleController.getString(C2369R.string.GetBadgeInfo), new Runnable() { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$fillItems$1();
                }
            })));
        }
        arrayList.add(UItem.asHeader("Google"));
        arrayList.add(UItem.asCheck(OtherItem.CRASHLYTICS.getId(), "Crashlytics", C2369R.drawable.msg_report).setChecked(ExteraConfig.useGoogleCrashlytics).setSearchable(this).setLinkAlias("crashlytics", this));
        arrayList.add(UItem.asCheck(OtherItem.ANALYTICS.getId(), "Analytics", C2369R.drawable.msg_data).setChecked(ExteraConfig.useGoogleAnalytics).setSearchable(this).setLinkAlias("analytics", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.AnalyticsInfo)));
        arrayList.add(UItem.asButton(OtherItem.EXPORT_SETTINGS.getId(), C2369R.drawable.msg_settings, LocaleController.getString(C2369R.string.ExportSettings)).setSearchable(this).setLinkAlias("exportSettings", this));
        int iCount = (int) Collection.EL.stream(PluginsController.getInstance().plugins.values()).filter(new Predicate() { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticLambda3
            public /* synthetic */ Predicate and(Predicate predicate) {
                return Predicate$CC.$default$and(this, predicate);
            }

            public /* synthetic */ Predicate negate() {
                return Predicate$CC.$default$negate(this);
            }

            /* renamed from: or */
            public /* synthetic */ Predicate m225or(Predicate predicate) {
                return Predicate$CC.$default$or(this, predicate);
            }

            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((Plugin) obj).isEnabled();
            }
        }).count();
        if (BadgesController.INSTANCE.isDeveloper() && iCount <= 1) {
            arrayList.add(UItem.asButton(OtherItem.EXPORT_DATA.getId(), C2369R.drawable.msg_archive, LocaleController.getString(C2369R.string.ExportData)).setSearchable(this).setLinkAlias("exportData", this));
        }
        arrayList.add(UItem.asButton(OtherItem.RESET_SETTINGS.getId(), C2369R.drawable.msg_reset, LocaleController.getString(C2369R.string.ResetSettings)).setSearchable(this).setLinkAlias("resetSettings", this));
        arrayList.add(UItem.asButton(OtherItem.DELETE_ACCOUNT.getId(), C2369R.drawable.msg_clearcache, LocaleController.getString(C2369R.string.DeleteAccount)).red().setSearchable(this).setLinkAlias("deleteAccount", this));
        arrayList.add(UItem.asShadow());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillItems$0() {
        SupporterBottomSheet.showAlert(this, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillItems$1() {
        SupporterBottomSheet.showAlert(this, null);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected boolean onLongClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        OtherItem otherItem = OtherItem.DONATE;
        if (i2 >= otherItem.getId() && i2 < otherItem.getId() + this.donates.size() + this.donates2.size()) {
            return handleDonateLongClick(uItem, view);
        }
        return super.onLongClick(uItem, view, i, f, f2);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        OtherItem otherItem = OtherItem.DONATE;
        if (i2 >= otherItem.getId() && i2 < otherItem.getId() + this.donates.size() + this.donates2.size()) {
            handleDonateClick(getDonate(i2));
        }
        if (i2 <= 0 || i2 >= otherItem.getId()) {
            return;
        }
        switch (C08912.f192x634c182a[OtherItem.values()[i2 - 1].ordinal()]) {
            case 1:
                toggleBooleanSettingAndRefresh("useGoogleCrashlytics", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticLambda4
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.useGoogleCrashlytics = ((Boolean) obj).booleanValue();
                    }
                });
                handleCrashlyticsClick();
                break;
            case 2:
                toggleBooleanSettingAndRefresh("useGoogleAnalytics", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticLambda5
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.useGoogleAnalytics = ((Boolean) obj).booleanValue();
                    }
                });
                handleAnalyticsClick();
                break;
            case 3:
                handleResetSettingsClick();
                break;
            case 4:
                handleDeleteAccountClick();
                break;
            case 5:
                PreferencesUtils.getInstance().exportSettings(this);
                break;
            case 6:
                presentFragment(new ExportActivity(null));
                break;
        }
    }

    /* renamed from: com.exteragram.messenger.preferences.OtherPreferencesActivity$2 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C08912 {

        /* renamed from: $SwitchMap$com$exteragram$messenger$preferences$OtherPreferencesActivity$OtherItem */
        static final /* synthetic */ int[] f192x634c182a;

        static {
            int[] iArr = new int[OtherItem.values().length];
            f192x634c182a = iArr;
            try {
                iArr[OtherItem.CRASHLYTICS.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f192x634c182a[OtherItem.ANALYTICS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f192x634c182a[OtherItem.RESET_SETTINGS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f192x634c182a[OtherItem.DELETE_ACCOUNT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f192x634c182a[OtherItem.EXPORT_SETTINGS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f192x634c182a[OtherItem.EXPORT_DATA.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    private void handleCrashlyticsClick() {
        FirebaseCrashlytics firebaseCrashlytics = ApplicationLoader.getFirebaseCrashlytics();
        if (firebaseCrashlytics != null) {
            firebaseCrashlytics.setCrashlyticsCollectionEnabled(ExteraConfig.useGoogleCrashlytics);
        }
    }

    private void handleAnalyticsClick() {
        FirebaseAnalytics firebaseAnalytics = ApplicationLoader.getFirebaseAnalytics();
        if (firebaseAnalytics != null) {
            firebaseAnalytics.setAnalyticsCollectionEnabled(ExteraConfig.useGoogleAnalytics);
            if (ExteraConfig.useGoogleAnalytics) {
                return;
            }
            firebaseAnalytics.resetAnalyticsData();
        }
    }

    private void handleResetSettingsClick() {
        AlertDialog alertDialogCreate = new AlertDialog.Builder(getParentActivity()).setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.ResetPreferencesInfo))).setTitle(LocaleController.getString(C2369R.string.ResetSettings)).setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null).setPositiveButton(LocaleController.getString(C2369R.string.Reset), new AlertDialog.OnButtonClickListener() { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticLambda8
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$handleResetSettingsClick$4(alertDialog, i);
            }
        }).create();
        showDialog(alertDialogCreate);
        TextView textView = (TextView) alertDialogCreate.getButton(-1);
        if (textView != null) {
            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleResetSettingsClick$4(AlertDialog alertDialog, int i) {
        PreferencesUtils.clearPreferences();
        this.parentLayout.rebuildAllFragmentViews(false, false);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.dialogFiltersUpdated, new Object[0]);
        LocaleController.getInstance().recreateFormatters();
        Theme.reloadAllResources(getParentActivity());
        BulletinFactory.m1267of(this).createErrorBulletin(LocaleController.getString(C2369R.string.ResetPreferences), getResourceProvider()).show();
    }

    private void handleDeleteAccountClick() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setMessage(LocaleController.getString(C2369R.string.TosDeclineDeleteAccount));
        builder.setTitle(LocaleController.getString(C2369R.string.DeleteAccount));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Deactivate), new AlertDialog.OnButtonClickListener() { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticLambda9
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                this.f$0.lambda$handleDeleteAccountClick$8(alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        final AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticLambda10
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f$0.lambda$handleDeleteAccountClick$9(alertDialogCreate, dialogInterface);
            }
        });
        showDialog(alertDialogCreate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDeleteAccountClick$8(AlertDialog alertDialog, int i) {
        final AlertDialog alertDialog2 = new AlertDialog(getParentActivity(), 3);
        alertDialog2.setCanCancel(false);
        Utilities.globalQueue.postRunnable(new Runnable() { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleDeleteAccountClick$7(alertDialog2);
            }
        }, 500L);
        alertDialog2.show();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDeleteAccountClick$7(final AlertDialog alertDialog) {
        TL_account.deleteAccount deleteaccount = new TL_account.deleteAccount();
        deleteaccount.reason = "ЭКСТЕРАГРАМ";
        getConnectionsManager().sendRequest(deleteaccount, new RequestDelegate() { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticLambda11
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$handleDeleteAccountClick$6(alertDialog, tLObject, tL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDeleteAccountClick$6(final AlertDialog alertDialog, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$handleDeleteAccountClick$5(alertDialog, tLObject, tL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDeleteAccountClick$5(AlertDialog alertDialog, TLObject tLObject, TLRPC.TL_error tL_error) {
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        if (tLObject instanceof TLRPC.TL_boolTrue) {
            getMessagesController().performLogout(0);
            return;
        }
        if (tL_error == null || tL_error.code != -1000) {
            String string = LocaleController.getString(C2369R.string.ErrorOccurred);
            if (tL_error != null) {
                string = string + "\n" + tL_error.text;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
            builder.setTitle(LocaleController.getString(C2369R.string.AppName));
            builder.setMessage(string);
            builder.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), null);
            builder.show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDeleteAccountClick$9(AlertDialog alertDialog, DialogInterface dialogInterface) {
        final TextView textView = (TextView) alertDialog.getButton(-1);
        textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        textView.setEnabled(false);
        final CharSequence text = textView.getText();
        new CountDownTimer(30000L, 100L) { // from class: com.exteragram.messenger.preferences.OtherPreferencesActivity.1
            @Override // android.os.CountDownTimer
            public void onTick(long j) {
                textView.setText(String.format(Locale.getDefault(), "%s • %d", text, Long.valueOf((j / 1000) + 1)));
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                textView.setText(text);
                textView.setEnabled(true);
            }
        }.start();
    }

    private void handleDonateClick(BankingUtils.Donate donate) {
        if (donate.name().toLowerCase().contains("ton")) {
            String str = "ton://transfer/" + donate.details() + "?text=" + String.valueOf(UserConfig.getInstance(this.currentAccount).getClientUserId());
            if (!Browser.isInternalUri(Uri.parse(str), new boolean[]{false})) {
                Browser.openUrl(getParentActivity(), str);
                return;
            } else {
                if (AndroidUtilities.addToClipboard(donate.details())) {
                    BulletinFactory.m1267of(this).createCopyBulletin(LocaleController.getString(C2369R.string.TextCopied)).show();
                    return;
                }
                return;
            }
        }
        if (LinkifyPort.WEB_URL.matcher(donate.details()).matches()) {
            Browser.openUrl(getParentActivity(), donate.details());
        } else if (AndroidUtilities.addToClipboard(donate.details())) {
            BulletinFactory.m1267of(this).createCopyBulletin(LocaleController.getString(C2369R.string.TextCopied)).show();
        }
    }

    private boolean handleDonateLongClick(UItem uItem, View view) {
        if (AndroidUtilities.addToClipboard(getDonate(uItem.f2017id).details())) {
            BulletinFactory.m1267of(this).createCopyBulletin(LocaleController.getString(C2369R.string.TextCopied)).show();
        }
        view.performHapticFeedback(VibratorUtils.getType(3), 1);
        return false;
    }

    private static final class IconInfo extends RecordTag {
        private final int iconColor;
        private final int iconResId;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof IconInfo)) {
                return false;
            }
            IconInfo iconInfo = (IconInfo) obj;
            return this.iconResId == iconInfo.iconResId && this.iconColor == iconInfo.iconColor;
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{Integer.valueOf(this.iconResId), Integer.valueOf(this.iconColor)};
        }

        private IconInfo(int i, int i2) {
            this.iconResId = i;
            this.iconColor = i2;
        }

        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        public final int hashCode() {
            return OtherPreferencesActivity$IconInfo$$ExternalSyntheticRecord0.m226m(this.iconResId, this.iconColor);
        }

        public final String toString() {
            return AbstractC0746x1d8a54ff.m185m($record$getFieldsAsObjects(), IconInfo.class, "iconResId;iconColor");
        }
    }

    private BankingUtils.Donate getDonate(int i) {
        int id = i - OtherItem.DONATE.getId();
        if (id < this.donates.size()) {
            return this.donates.get(id);
        }
        if (id - this.donates.size() < this.donates2.size()) {
            return this.donates2.get(id - this.donates.size());
        }
        return null;
    }
}
