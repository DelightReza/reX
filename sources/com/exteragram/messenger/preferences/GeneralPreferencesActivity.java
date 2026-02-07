package com.exteragram.messenger.preferences;

import android.view.View;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.utils.p011ui.PopupUtils;
import com.exteragram.messenger.utils.text.LocaleUtils;
import com.google.android.exoplayer2.util.Consumer;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.Components.Premium.PremiumFeatureBottomSheet;
import org.telegram.p023ui.Components.TranslateAlert2;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.Components.UniversalAdapter;
import org.telegram.p023ui.RestrictedLanguagesSelectActivity;
import p017j$.util.Collection;
import p017j$.util.Objects;
import p017j$.util.function.Function$CC;
import p017j$.util.function.Predicate$CC;
import p017j$.util.stream.Collectors;

/* loaded from: classes.dex */
public class GeneralPreferencesActivity extends BasePreferencesActivity {
    private static final CharSequence[] idOptions = {LocaleController.getString(C2369R.string.Hide), "Telegram API", "Bot API"};
    private static final CharSequence[] translationProviders = {"Telegram", "Google", "Yandex", "DeepL"};
    private static final CharSequence[] translationFormalities = {LocaleController.getString(C2369R.string.Default), LocaleController.getString(C2369R.string.TranslationFormalityLess), LocaleController.getString(C2369R.string.TranslationFormalityMore)};

    public enum GeneralItem {
        SHOW_TRANSLATE_BUTTON,
        SHOW_TRANSLATE_CHAT_BUTTON,
        TRANSLATION_PROVIDERS,
        TRANSLATION_FORMALITY,
        DO_NOT_TRANSLATE_LANGUAGES,
        DISABLE_NUMBER_ROUNDING,
        FORMAT_TIME_WITH_SECONDS,
        IN_APP_VIBRATION,
        FILTER_ZALGO,
        YANDEX_MAPS,
        DOWNLOAD_SPEED_BOOST,
        UPLOAD_SPEED_BOOST,
        HIDE_PHONE_NUMBER,
        SHOW_ID_AND_DC,
        ARCHIVE_ON_PULL,
        DISABLE_UNARCHIVE_SWIPE;

        public int getId() {
            return ordinal() + 1;
        }
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    public String getTitle() {
        return LocaleController.getString(C2369R.string.General);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void fillItems(ArrayList arrayList, UniversalAdapter universalAdapter) {
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.TranslateMessages)));
        arrayList.add(UItem.asCheck(GeneralItem.SHOW_TRANSLATE_BUTTON.getId(), LocaleController.getString(C2369R.string.ShowTranslateButton)).setChecked(getContextTranslateValue()).setSearchable(this).setLinkAlias("showTranslateButton", this));
        arrayList.add(UItem.asCheck(GeneralItem.SHOW_TRANSLATE_CHAT_BUTTON.getId(), LocaleController.getString(C2369R.string.ShowTranslateChatButton)).setCheckBoxIcon(!getUserConfig().isPremium() ? C2369R.drawable.permission_locked : 0).setChecked(getChatTranslateValue()).setSearchable(this).setLinkAlias("showTranslateChatButton", this));
        arrayList.add(UItem.asButton(GeneralItem.TRANSLATION_PROVIDERS.getId(), LocaleController.getString(C2369R.string.TranslationProvider), translationProviders[ExteraConfig.translationProvider]).setSearchable(this).setLinkAlias("translationProvider", this));
        if (ExteraConfig.translationProvider == 3) {
            arrayList.add(UItem.asButton(GeneralItem.TRANSLATION_FORMALITY.getId(), LocaleController.getString(C2369R.string.TranslationFormality), translationFormalities[ExteraConfig.translationFormality]).setSearchable(this).setLinkAlias("translationFormality", this));
        }
        arrayList.add(UItem.asButton(GeneralItem.DO_NOT_TRANSLATE_LANGUAGES.getId(), LocaleController.getString(C2369R.string.DoNotTranslate), getDoNotTranslateValue()).setSearchable(this).setLinkAlias("doNotTranslateLanguages", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.TranslateMessagesInfo1)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.General)));
        arrayList.add(UItem.asCheck(GeneralItem.DISABLE_NUMBER_ROUNDING.getId(), LocaleController.getString(C2369R.string.DisableNumberRounding), "1.23K -> 1,234", false).setChecked(ExteraConfig.disableNumberRounding).setSearchable(this).setLinkAlias("disableNumberRounding", this));
        arrayList.add(UItem.asCheck(GeneralItem.FORMAT_TIME_WITH_SECONDS.getId(), LocaleController.getString(C2369R.string.FormatTimeWithSeconds), "12:34 -> 12:34:56", false).setChecked(ExteraConfig.formatTimeWithSeconds).setSearchable(this).setLinkAlias("formatTimeWithSeconds", this));
        arrayList.add(UItem.asCheck(GeneralItem.IN_APP_VIBRATION.getId(), LocaleController.getString(C2369R.string.InAppVibration)).setChecked(ExteraConfig.inAppVibration).setSearchable(this).setLinkAlias("inAppVibration", this));
        arrayList.add(UItem.asCheck(GeneralItem.FILTER_ZALGO.getId(), LocaleController.getString(C2369R.string.FilterZalgo)).setChecked(ExteraConfig.filterZalgo).setSearchable(this).setLinkAlias("filterZalgo", this));
        arrayList.add(UItem.asShadow(LocaleController.formatString(C2369R.string.FilterZalgoInfo, LocaleUtils.filter("Z̷͍͌ā̸̜l̸̞̂g̷͍̝o̶̩̓"))));
        if (ApplicationLoader.applicationLoaderInstance.allowToUseYandexMaps()) {
            arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Maps)));
            arrayList.add(UItem.asCheck(GeneralItem.YANDEX_MAPS.getId(), LocaleController.getString(C2369R.string.UseYandexMaps)).setChecked(ExteraConfig.useYandexMaps).setSearchable(this).setLinkAlias("useYandexMaps", this));
            arrayList.add(UItem.asShadow(LocaleUtils.formatWithHtmlURLs(LocaleUtils.fromHtml(LocaleController.getString(C2369R.string.TermsOfUseYandexMaps)))));
        }
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.DownloadSpeedBoost)));
        arrayList.add(UItem.asSlideView(GeneralItem.DOWNLOAD_SPEED_BOOST.getId(), new String[]{LocaleController.getString(C2369R.string.BlurOff), LocaleController.getString(C2369R.string.SpeedFast), LocaleController.getString(C2369R.string.Ultra)}, ExteraConfig.downloadSpeedBoost, new Utilities.Callback() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda0
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                this.f$0.lambda$fillItems$0((Integer) obj);
            }
        }).setLinkAlias("downloadSpeedBoost", this));
        arrayList.add(UItem.asCheck(GeneralItem.UPLOAD_SPEED_BOOST.getId(), LocaleController.getString(C2369R.string.UploadSpeedBoost)).setChecked(ExteraConfig.uploadSpeedBoost).setSearchable(this).setLinkAlias("uploadSpeedBoost", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.SpeedBoostInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.Profile)));
        arrayList.add(UItem.asCheck(GeneralItem.HIDE_PHONE_NUMBER.getId(), LocaleController.getString(C2369R.string.HidePhoneNumber)).setChecked(ExteraConfig.hidePhoneNumber).setSearchable(this).setLinkAlias("hidePhoneNumber", this));
        arrayList.add(UItem.asButton(GeneralItem.SHOW_ID_AND_DC.getId(), LocaleController.getString(C2369R.string.ShowIdAndDc), idOptions[ExteraConfig.showIdAndDc]).setSearchable(this).setLinkAlias("showIdAndDc", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.ShowIdAndDcInfo)));
        arrayList.add(UItem.asHeader(LocaleController.getString(C2369R.string.ArchivedChats)));
        arrayList.add(UItem.asCheck(GeneralItem.ARCHIVE_ON_PULL.getId(), LocaleController.getString(C2369R.string.ArchiveOnPull)).setChecked(ExteraConfig.archiveOnPull).setSearchable(this).setLinkAlias("archiveOnPull", this));
        arrayList.add(UItem.asCheck(GeneralItem.DISABLE_UNARCHIVE_SWIPE.getId(), LocaleController.getString(C2369R.string.DisableUnarchiveSwipe)).setChecked(ExteraConfig.disableUnarchiveSwipe).setSearchable(this).setLinkAlias("disableUnarchiveSwipe", this));
        arrayList.add(UItem.asShadow(LocaleController.getString(C2369R.string.DisableUnarchiveSwipeInfo)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$fillItems$0(Integer num) {
        int iIntValue = num.intValue();
        ExteraConfig.downloadSpeedBoost = iIntValue;
        changeIntSetting("downloadSpeedBoost", iIntValue);
    }

    @Override // com.exteragram.messenger.preferences.BasePreferencesActivity
    protected void onClick(UItem uItem, View view, int i, float f, float f2) {
        int i2 = uItem.f2017id;
        if (i2 <= 0 || i2 > GeneralItem.values().length) {
            return;
        }
        switch (C08881.f190x463f066a[GeneralItem.values()[uItem.f2017id - 1].ordinal()]) {
            case 1:
                toggleBooleanSettingAndRefresh("contextTranslateEnabled", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda1
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        this.f$0.lambda$onClick$1((Boolean) obj);
                    }
                });
                handleContextTranslateClick();
                break;
            case 2:
                handleChatTranslateClick(uItem);
                break;
            case 3:
                showListDialog(uItem, translationProviders, LocaleController.getString(C2369R.string.TranslationProvider), ExteraConfig.translationProvider, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda5
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$2(i3);
                    }
                });
                break;
            case 4:
                showListDialog(uItem, translationFormalities, LocaleController.getString(C2369R.string.TranslationFormality), ExteraConfig.translationFormality, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda6
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$3(i3);
                    }
                });
                break;
            case 5:
                presentFragment(new RestrictedLanguagesSelectActivity());
                break;
            case 6:
                toggleBooleanSettingAndRefresh("disableNumberRounding", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda7
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.disableNumberRounding = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 7:
                toggleBooleanSettingAndRefresh("formatTimeWithSeconds", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda8
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.formatTimeWithSeconds = ((Boolean) obj).booleanValue();
                    }
                });
                handleFormatTimeWithSecondsClick();
                break;
            case 8:
                toggleBooleanSettingAndRefresh("inAppVibration", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda9
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.inAppVibration = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 9:
                toggleBooleanSettingAndRefresh("filterZalgo", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda10
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.filterZalgo = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 10:
                toggleBooleanSettingAndRefresh("useYandexMaps", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda11
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.useYandexMaps = ((Boolean) obj).booleanValue();
                    }
                });
                ApplicationLoader.updateMapsProvider();
                break;
            case 11:
                toggleBooleanSettingAndRefresh("uploadSpeedBoost", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda12
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.uploadSpeedBoost = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 12:
                toggleBooleanSettingAndRefresh("hidePhoneNumber", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda13
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.hidePhoneNumber = ((Boolean) obj).booleanValue();
                    }
                });
                handleHidePhoneNumberClick();
                break;
            case 13:
                showListDialog(uItem, idOptions, LocaleController.getString(C2369R.string.ShowIdAndDc), ExteraConfig.showIdAndDc, new PopupUtils.OnItemClickListener() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda2
                    @Override // com.exteragram.messenger.utils.ui.PopupUtils.OnItemClickListener
                    public final void onClick(int i3) {
                        this.f$0.lambda$onClick$11(i3);
                    }
                });
                break;
            case 14:
                toggleBooleanSettingAndRefresh("archiveOnPull", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda3
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.archiveOnPull = ((Boolean) obj).booleanValue();
                    }
                });
                break;
            case 15:
                toggleBooleanSettingAndRefresh("disableUnarchiveSwipe", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda4
                    @Override // com.google.android.exoplayer2.util.Consumer
                    public final void accept(Object obj) {
                        ExteraConfig.disableUnarchiveSwipe = ((Boolean) obj).booleanValue();
                    }
                });
                break;
        }
    }

    /* renamed from: com.exteragram.messenger.preferences.GeneralPreferencesActivity$1 */
    /* loaded from: classes3.dex */
    static /* synthetic */ class C08881 {

        /* renamed from: $SwitchMap$com$exteragram$messenger$preferences$GeneralPreferencesActivity$GeneralItem */
        static final /* synthetic */ int[] f190x463f066a;

        static {
            int[] iArr = new int[GeneralItem.values().length];
            f190x463f066a = iArr;
            try {
                iArr[GeneralItem.SHOW_TRANSLATE_BUTTON.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f190x463f066a[GeneralItem.SHOW_TRANSLATE_CHAT_BUTTON.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f190x463f066a[GeneralItem.TRANSLATION_PROVIDERS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f190x463f066a[GeneralItem.TRANSLATION_FORMALITY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f190x463f066a[GeneralItem.DO_NOT_TRANSLATE_LANGUAGES.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f190x463f066a[GeneralItem.DISABLE_NUMBER_ROUNDING.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f190x463f066a[GeneralItem.FORMAT_TIME_WITH_SECONDS.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f190x463f066a[GeneralItem.IN_APP_VIBRATION.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f190x463f066a[GeneralItem.FILTER_ZALGO.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f190x463f066a[GeneralItem.YANDEX_MAPS.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f190x463f066a[GeneralItem.UPLOAD_SPEED_BOOST.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f190x463f066a[GeneralItem.HIDE_PHONE_NUMBER.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f190x463f066a[GeneralItem.SHOW_ID_AND_DC.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                f190x463f066a[GeneralItem.ARCHIVE_ON_PULL.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                f190x463f066a[GeneralItem.DISABLE_UNARCHIVE_SWIPE.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$1(Boolean bool) {
        getMessagesController().getTranslateController().setContextTranslateEnabled(bool.booleanValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$2(int i) {
        ExteraConfig.translationProvider = i;
        changeIntSetting("translationProvider", i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$3(int i) {
        ExteraConfig.translationFormality = i;
        changeIntSetting("translationFormality", i);
        this.parentLayout.rebuildFragments(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onClick$11(int i) {
        ExteraConfig.showIdAndDc = i;
        changeIntSetting("showIdAndDc", i);
        this.parentLayout.rebuildFragments(0);
    }

    private CharSequence getDoNotTranslateValue() {
        final boolean[] zArr = new boolean[1];
        return (CharSequence) Collection.EL.stream(RestrictedLanguagesSelectActivity.getRestrictedLanguages()).map(new Function() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda14
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return TranslateAlert2.capitalFirst(TranslateAlert2.languageName((String) obj, zArr));
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        }).filter(new Predicate() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda15
            public /* synthetic */ Predicate and(Predicate predicate) {
                return Predicate$CC.$default$and(this, predicate);
            }

            public /* synthetic */ Predicate negate() {
                return Predicate$CC.$default$negate(this);
            }

            /* renamed from: or */
            public /* synthetic */ Predicate m223or(Predicate predicate) {
                return Predicate$CC.$default$or(this, predicate);
            }

            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull((String) obj);
            }
        }).collect(Collectors.joining(", "));
    }

    private boolean getContextTranslateValue() {
        return getMessagesController().getTranslateController().isContextTranslateEnabled();
    }

    private void handleContextTranslateClick() {
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateSearchSettings, new Object[0]);
        this.parentLayout.rebuildFragments(0);
    }

    private boolean getChatTranslateValue() {
        return getMessagesController().getTranslateController().isChatTranslateEnabled();
    }

    private void handleChatTranslateClick(UItem uItem) {
        if (!uItem.checked && !getUserConfig().isPremium()) {
            showDialog(new PremiumFeatureBottomSheet(this, 13, false));
            return;
        }
        toggleBooleanSettingAndRefresh("chatTranslateEnabled", uItem, new Consumer() { // from class: com.exteragram.messenger.preferences.GeneralPreferencesActivity$$ExternalSyntheticLambda16
            @Override // com.google.android.exoplayer2.util.Consumer
            public final void accept(Object obj) {
                this.f$0.lambda$handleChatTranslateClick$15((Boolean) obj);
            }
        });
        NotificationCenter.getInstance(this.currentAccount).lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateSearchSettings, new Object[0]);
        this.parentLayout.rebuildFragments(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleChatTranslateClick$15(Boolean bool) {
        getMessagesController().getTranslateController().setChatTranslateEnabled(bool.booleanValue());
    }

    private void handleFormatTimeWithSecondsClick() {
        LocaleController.getInstance().recreateFormatters();
        this.parentLayout.rebuildFragments(0);
    }

    private void handleHidePhoneNumberClick() {
        getNotificationCenter().lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
        this.parentLayout.rebuildFragments(0);
    }
}
