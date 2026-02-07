package org.telegram.p023ui.Components;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.URLSpan;
import android.util.Base64;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.util.Consumer;
import com.exteragram.messenger.ExteraConfig;
import com.exteragram.messenger.utils.system.VibratorUtils;
import com.radolyn.ayugram.AyuConstants;
import com.radolyn.ayugram.AyuState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.mvel2.DataTypes;
import org.mvel2.asm.Opcodes;
import org.mvel2.asm.signature.SignatureVisitor;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.AppGlobalConfig;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.OneUIUtilities;
import org.telegram.messenger.SecretChatHelper;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.SharedConfig;
import org.telegram.messenger.SvgHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.pip.utils.PipUtils;
import org.telegram.p023ui.ActionBar.ActionBarMenuItem;
import org.telegram.p023ui.ActionBar.ActionBarPopupWindow;
import org.telegram.p023ui.ActionBar.AlertDialog;
import org.telegram.p023ui.ActionBar.AlertDialogDecor;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.SimpleTextView;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.Business.TimezonesController;
import org.telegram.p023ui.CacheControlActivity;
import org.telegram.p023ui.Cells.AccountSelectCell;
import org.telegram.p023ui.Cells.CheckBoxCell;
import org.telegram.p023ui.Cells.RadioColorCell;
import org.telegram.p023ui.Cells.TextColorCell;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.Components.AlertsCreator;
import org.telegram.p023ui.Components.AnimatedTextView;
import org.telegram.p023ui.Components.Bulletin;
import org.telegram.p023ui.Components.Forum.ForumUtilities;
import org.telegram.p023ui.Components.LinkSpanDrawable;
import org.telegram.p023ui.Components.NumberPicker;
import org.telegram.p023ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p023ui.Components.voip.VoIPHelper;
import org.telegram.p023ui.LanguageSelectActivity;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.p023ui.NotificationsCustomSettingsActivity;
import org.telegram.p023ui.PhotoViewer;
import org.telegram.p023ui.PremiumPreviewFragment;
import org.telegram.p023ui.PrivacyControlActivity;
import org.telegram.p023ui.ProfileActivity;
import org.telegram.p023ui.Stars.StarGiftSheet;
import org.telegram.p023ui.Stars.StarsController;
import org.telegram.p023ui.Stars.StarsIntroActivity;
import org.telegram.p023ui.Stories.DarkThemeResourceProvider;
import org.telegram.p023ui.Stories.recorder.ButtonWithCounterView;
import org.telegram.p023ui.ThemePreviewActivity;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_account;
import org.telegram.tgnet.p022tl.TL_phone;
import org.telegram.tgnet.p022tl.TL_stars;
import p017j$.time.LocalDate;
import p017j$.time.YearMonth;
import p017j$.time.ZoneId;
import p017j$.time.ZoneOffset;

/* loaded from: classes6.dex */
public abstract class AlertsCreator {

    public interface AccountSelectDelegate {
        void didSelectAccount(int i);
    }

    public interface BlockDialogCallback {
        void run(boolean z, boolean z2);
    }

    public interface DatePickerDelegate {
        void didSelectDate(int i, int i2, int i3);
    }

    public interface ScheduleDatePickerDelegate {
        void didSelectDate(boolean z, int i, int i2);
    }

    public interface SoundFrequencyDelegate {
        void didSelectValues(int i, int i2);
    }

    public interface StatusUntilDatePickerDelegate {
        void didSelectDate(int i);
    }

    /* renamed from: $r8$lambda$0wbdN-_E-FsWwmt5_SxLLrc9vIo, reason: not valid java name */
    public static /* synthetic */ void m7591$r8$lambda$0wbdN_EFsWwmt5_SxLLrc9vIo(NumberPicker numberPicker, int i, int i2) {
    }

    public static /* synthetic */ void $r8$lambda$Hq5xQJssvd9l_d8xzFbBshNHewU(NumberPicker numberPicker, int i, int i2) {
    }

    public static /* synthetic */ void $r8$lambda$JiwJr3bR3ySTvE4ZdMp08xfUBy0() {
    }

    public static /* synthetic */ void $r8$lambda$mngfP3eB1O7qzEiRRbjMYngSF3w(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    public static /* synthetic */ void $r8$lambda$rFVIcafL96lJ1Lj3QsA2wBt_YbQ(AlertDialog alertDialog, int i) {
    }

    public static /* synthetic */ void $r8$lambda$tKLkyMP9famLGN2Ib107AG8efGc(TLObject tLObject, TLRPC.TL_error tL_error) {
    }

    public static Dialog createForgotPasscodeDialog(Context context) {
        return new AlertDialog.Builder(context).setTitle(LocaleController.getString(C2369R.string.ForgotPasscode)).setMessage(LocaleController.getString(C2369R.string.ForgotPasscodeInfo)).setPositiveButton(LocaleController.getString(C2369R.string.Close), null).create();
    }

    public static Dialog createLocationRequiredDialog(final Context context, boolean z) {
        return new AlertDialog.Builder(context).setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.PermissionNoLocationFriends))).setTopAnimation(C2369R.raw.permission_request_location, 72, false, Theme.getColor(Theme.key_dialogTopBackground)).setPositiveButton(LocaleController.getString(C2369R.string.PermissionOpenSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda132
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.m7654$r8$lambda$z9OV7Sej2fK9BMVVPq5XSk9LM(context, alertDialog, i);
            }
        }).setNegativeButton(LocaleController.getString(C2369R.string.ContactsPermissionAlertNotNow), null).create();
    }

    /* renamed from: $r8$lambda$z9OV7Sej2fK9BMVVPq5X-Sk-9LM, reason: not valid java name */
    public static /* synthetic */ void m7654$r8$lambda$z9OV7Sej2fK9BMVVPq5XSk9LM(Context context, AlertDialog alertDialog, int i) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
            context.startActivity(intent);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    public static Dialog createBackgroundActivityDialog(final Context context) {
        int i;
        AlertDialog.Builder title = new AlertDialog.Builder(context).setTitle(LocaleController.getString(C2369R.string.AllowBackgroundActivity));
        if (OneUIUtilities.isOneUI()) {
            i = Build.VERSION.SDK_INT >= 31 ? C2369R.string.AllowBackgroundActivityInfoOneUIAboveS : C2369R.string.AllowBackgroundActivityInfoOneUIBelowS;
        } else {
            i = C2369R.string.AllowBackgroundActivityInfo;
        }
        return title.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(i))).setTopAnimation(C2369R.raw.permission_request_apk, 72, false, Theme.getColor(Theme.key_dialogTopBackground)).setPositiveButton(LocaleController.getString(C2369R.string.PermissionOpenSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda7
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                AlertsCreator.$r8$lambda$AvuHCR1k6V5fBzOuStuj5qfZeNs(context, alertDialog, i2);
            }
        }).setNegativeButton(LocaleController.getString(C2369R.string.ContactsPermissionAlertNotNow), null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda8
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                SharedConfig.BackgroundActivityPrefs.increaseDismissedCount();
            }
        }).create();
    }

    public static /* synthetic */ void $r8$lambda$AvuHCR1k6V5fBzOuStuj5qfZeNs(Context context, AlertDialog alertDialog, int i) {
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
            context.startActivity(intent);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    public static Dialog createWebViewPermissionsRequestDialog(final Context context, Theme.ResourcesProvider resourcesProvider, String[] strArr, int i, String str, String str2, final Consumer consumer) {
        final boolean z;
        if (strArr == null || !(context instanceof Activity) || Build.VERSION.SDK_INT < 23) {
            z = false;
        } else {
            Activity activity = (Activity) context;
            for (String str3 : strArr) {
                if (activity.checkSelfPermission(str3) != 0 && activity.shouldShowRequestPermissionRationale(str3)) {
                    z = true;
                    break;
                }
            }
            z = false;
        }
        final AtomicBoolean atomicBoolean = new AtomicBoolean();
        AlertDialog.Builder topAnimation = new AlertDialog.Builder(context, resourcesProvider).setTopAnimation(i, 72, false, Theme.getColor(Theme.key_dialogTopBackground));
        if (z) {
            str = str2;
        }
        return topAnimation.setMessage(AndroidUtilities.replaceTags(str)).setPositiveButton(LocaleController.getString(z ? C2369R.string.PermissionOpenSettings : C2369R.string.BotWebViewRequestAllow), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda128
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                AlertsCreator.m7650$r8$lambda$tUfQ2PKxixHa2KrEl7AuAV6nLo(z, context, atomicBoolean, consumer, alertDialog, i2);
            }
        }).setNegativeButton(LocaleController.getString(C2369R.string.BotWebViewRequestDontAllow), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda129
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                AlertsCreator.$r8$lambda$HEvbBaVkS3tygf2fstRGhrdMj8k(atomicBoolean, consumer, alertDialog, i2);
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda130
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AlertsCreator.$r8$lambda$e_UUPM06Fsq_qCQnM0W1euYrafk(atomicBoolean, consumer, dialogInterface);
            }
        }).create();
    }

    /* renamed from: $r8$lambda$tUfQ2-PKxixHa2KrEl7AuAV6nLo, reason: not valid java name */
    public static /* synthetic */ void m7650$r8$lambda$tUfQ2PKxixHa2KrEl7AuAV6nLo(boolean z, Context context, AtomicBoolean atomicBoolean, Consumer consumer, AlertDialog alertDialog, int i) {
        if (z) {
            try {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
                context.startActivity(intent);
                return;
            } catch (Exception e) {
                FileLog.m1160e(e);
                return;
            }
        }
        atomicBoolean.set(true);
        consumer.accept(Boolean.TRUE);
    }

    public static /* synthetic */ void $r8$lambda$HEvbBaVkS3tygf2fstRGhrdMj8k(AtomicBoolean atomicBoolean, Consumer consumer, AlertDialog alertDialog, int i) {
        atomicBoolean.set(true);
        consumer.accept(Boolean.FALSE);
    }

    public static /* synthetic */ void $r8$lambda$e_UUPM06Fsq_qCQnM0W1euYrafk(AtomicBoolean atomicBoolean, Consumer consumer, DialogInterface dialogInterface) {
        if (atomicBoolean.get()) {
            return;
        }
        consumer.accept(Boolean.FALSE);
    }

    public static Dialog createApkRestrictedDialog(final Context context, Theme.ResourcesProvider resourcesProvider) {
        return new AlertDialog.Builder(context, resourcesProvider).setMessage(LocaleController.getString(C2369R.string.ApkRestricted)).setTopAnimation(C2369R.raw.permission_request_apk, 72, false, Theme.getColor(Theme.key_dialogTopBackground)).setPositiveButton(LocaleController.getString(C2369R.string.PermissionOpenSettings), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda3
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.m7595$r8$lambda$423SrzgdSx4Htk3AIZLaUXHAbI(context, alertDialog, i);
            }
        }).setNegativeButton(LocaleController.getString(C2369R.string.ContactsPermissionAlertNotNow), null).create();
    }

    /* renamed from: $r8$lambda$423SrzgdSx4Htk3AI-ZLaUXHAbI, reason: not valid java name */
    public static /* synthetic */ void m7595$r8$lambda$423SrzgdSx4Htk3AIZLaUXHAbI(Context context, AlertDialog alertDialog, int i) {
        try {
            context.startActivity(new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES", Uri.parse("package:" + context.getPackageName())));
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    public static /* synthetic */ void $r8$lambda$4f2RXDpSskM5bIoBqQs4mqEINPk(long j, int i, long j2) {
        Activity activity = AndroidUtilities.getActivity();
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        new StarsIntroActivity.StarsNeededSheet(activity, (PhotoViewer.getInstance().isVisible() || (safeLastFragment != null && safeLastFragment.hasShownSheet())) ? new DarkThemeResourceProvider() : safeLastFragment != null ? safeLastFragment.getResourceProvider() : null, j, 13, DialogObject.getShortName(i, j2), new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda14
            @Override // java.lang.Runnable
            public final void run() {
                AlertsCreator.$r8$lambda$JiwJr3bR3ySTvE4ZdMp08xfUBy0();
            }
        }, j2).show();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:323:0x0654  */
    /* JADX WARN: Removed duplicated region for block: B:378:0x0729  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static android.app.Dialog processError(final int r18, org.telegram.tgnet.TLRPC.TL_error r19, org.telegram.p023ui.ActionBar.BaseFragment r20, org.telegram.tgnet.TLObject r21, java.lang.Object... r22) {
        /*
            Method dump skipped, instructions count: 2518
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.AlertsCreator.processError(int, org.telegram.tgnet.TLRPC$TL_error, org.telegram.ui.ActionBar.BaseFragment, org.telegram.tgnet.TLObject, java.lang.Object[]):android.app.Dialog");
    }

    public static Toast showSimpleToast(BaseFragment baseFragment, String str) {
        Context parentActivity;
        if (str == null) {
            return null;
        }
        if (baseFragment != null && baseFragment.getParentActivity() != null) {
            parentActivity = baseFragment.getParentActivity();
        } else {
            parentActivity = ApplicationLoader.applicationContext;
        }
        Toast toastMakeText = Toast.makeText(parentActivity, str, 1);
        toastMakeText.show();
        return toastMakeText;
    }

    public static AlertDialog showUpdateAppAlert(final Context context, String str, boolean z) {
        if (context == null || str == null) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(LocaleController.getString(C2369R.string.AppName));
        builder.setMessage(str);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), null);
        if (z) {
            builder.setNegativeButton(LocaleController.getString(C2369R.string.UpdateApp), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda28
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    Browser.openUrl(context, BuildVars.GITHUB_APP_URL);
                }
            });
        }
        return builder.show();
    }

    public static AlertDialog.Builder createLanguageAlert(final LaunchActivity launchActivity, final TLRPC.TL_langPackLanguage tL_langPackLanguage) {
        String string;
        int iIndexOf;
        if (tL_langPackLanguage == null) {
            return null;
        }
        tL_langPackLanguage.lang_code = tL_langPackLanguage.lang_code.replace(SignatureVisitor.SUPER, '_').toLowerCase();
        tL_langPackLanguage.plural_code = tL_langPackLanguage.plural_code.replace(SignatureVisitor.SUPER, '_').toLowerCase();
        String str = tL_langPackLanguage.base_lang_code;
        if (str != null) {
            tL_langPackLanguage.base_lang_code = str.replace(SignatureVisitor.SUPER, '_').toLowerCase();
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(launchActivity);
        if (LocaleController.getInstance().getCurrentLocaleInfo().shortName.equals(tL_langPackLanguage.lang_code)) {
            builder.setTitle(LocaleController.getString(C2369R.string.Language));
            string = LocaleController.formatString("LanguageSame", C2369R.string.LanguageSame, tL_langPackLanguage.name);
            builder.setNegativeButton(LocaleController.getString(C2369R.string.f1459OK), null);
            builder.setNeutralButton(LocaleController.getString(C2369R.string.SETTINGS), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda106
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    launchActivity.lambda$runLinkRequest$107(new LanguageSelectActivity());
                }
            });
        } else if (tL_langPackLanguage.strings_count == 0) {
            builder.setTitle(LocaleController.getString(C2369R.string.LanguageUnknownTitle));
            string = LocaleController.formatString("LanguageUnknownCustomAlert", C2369R.string.LanguageUnknownCustomAlert, tL_langPackLanguage.name);
            builder.setNegativeButton(LocaleController.getString(C2369R.string.f1459OK), null);
        } else {
            builder.setTitle(LocaleController.getString(C2369R.string.LanguageTitle));
            if (tL_langPackLanguage.official) {
                string = LocaleController.formatString("LanguageAlert", C2369R.string.LanguageAlert, tL_langPackLanguage.name, Integer.valueOf((int) Math.ceil((tL_langPackLanguage.translated_count / tL_langPackLanguage.strings_count) * 100.0f)));
            } else {
                string = LocaleController.formatString("LanguageCustomAlert", C2369R.string.LanguageCustomAlert, tL_langPackLanguage.name, Integer.valueOf((int) Math.ceil((tL_langPackLanguage.translated_count / tL_langPackLanguage.strings_count) * 100.0f)));
            }
            builder.setPositiveButton(LocaleController.getString(C2369R.string.Change), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda107
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i) {
                    AlertsCreator.$r8$lambda$6ZqQMNFwAbwiSWlJWIeA75Lfd6A(tL_langPackLanguage, launchActivity, alertDialog, i);
                }
            });
            builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(AndroidUtilities.replaceTags(string));
        int iIndexOf2 = TextUtils.indexOf((CharSequence) spannableStringBuilder, '[');
        if (iIndexOf2 != -1) {
            int i = iIndexOf2 + 1;
            iIndexOf = TextUtils.indexOf((CharSequence) spannableStringBuilder, ']', i);
            if (iIndexOf != -1) {
                spannableStringBuilder.delete(iIndexOf, iIndexOf + 1);
                spannableStringBuilder.delete(iIndexOf2, i);
            }
        } else {
            iIndexOf = -1;
        }
        if (iIndexOf2 != -1 && iIndexOf != -1) {
            spannableStringBuilder.setSpan(new URLSpanNoUnderline(tL_langPackLanguage.translations_url) { // from class: org.telegram.ui.Components.AlertsCreator.1
                @Override // org.telegram.p023ui.Components.URLSpanNoUnderline, android.text.style.URLSpan, android.text.style.ClickableSpan
                public void onClick(View view) {
                    builder.getDismissRunnable().run();
                    super.onClick(view);
                }
            }, iIndexOf2, iIndexOf - 1, 33);
        }
        TextView textView = new TextView(launchActivity);
        textView.setText(spannableStringBuilder);
        textView.setTextSize(1, 16.0f);
        textView.setLinkTextColor(Theme.getColor(Theme.key_dialogTextLink));
        textView.setHighlightColor(Theme.getColor(Theme.key_dialogLinkSelection));
        textView.setPadding(AndroidUtilities.m1146dp(23.0f), 0, AndroidUtilities.m1146dp(23.0f), 0);
        textView.setMovementMethod(new AndroidUtilities.LinkMovementMethodMy());
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        builder.setView(textView);
        return builder;
    }

    public static /* synthetic */ void $r8$lambda$6ZqQMNFwAbwiSWlJWIeA75Lfd6A(TLRPC.TL_langPackLanguage tL_langPackLanguage, LaunchActivity launchActivity, AlertDialog alertDialog, int i) {
        String str;
        if (tL_langPackLanguage.official) {
            str = "remote_" + tL_langPackLanguage.lang_code;
        } else {
            str = "unofficial_" + tL_langPackLanguage.lang_code;
        }
        LocaleController.LocaleInfo languageFromDict = LocaleController.getInstance().getLanguageFromDict(str);
        if (languageFromDict == null) {
            languageFromDict = new LocaleController.LocaleInfo();
            languageFromDict.name = tL_langPackLanguage.native_name;
            languageFromDict.nameEnglish = tL_langPackLanguage.name;
            languageFromDict.shortName = tL_langPackLanguage.lang_code;
            languageFromDict.baseLangCode = tL_langPackLanguage.base_lang_code;
            languageFromDict.pluralLangCode = tL_langPackLanguage.plural_code;
            languageFromDict.isRtl = tL_langPackLanguage.rtl;
            if (tL_langPackLanguage.official) {
                languageFromDict.pathToFile = "remote";
            } else {
                languageFromDict.pathToFile = "unofficial";
            }
        }
        LocaleController.getInstance().applyLanguage(languageFromDict, true, false, false, true, UserConfig.selectedAccount, null);
        launchActivity.rebuildAllFragments(true);
    }

    public static boolean checkSlowMode(Context context, int i, long j, boolean z) {
        TLRPC.Chat chat;
        if (!DialogObject.isChatDialog(j) || (chat = MessagesController.getInstance(i).getChat(Long.valueOf(-j))) == null || !chat.slowmode_enabled || ChatObject.hasAdminRights(chat)) {
            return false;
        }
        if (!z) {
            TLRPC.ChatFull chatFull = MessagesController.getInstance(i).getChatFull(chat.f1571id);
            if (chatFull == null) {
                chatFull = MessagesStorage.getInstance(i).loadChatInfo(chat.f1571id, ChatObject.isChannel(chat), new CountDownLatch(1), false, false);
            }
            if (chatFull != null && chatFull.slowmode_next_send_date >= ConnectionsManager.getInstance(i).getCurrentTime()) {
                z = true;
            }
        }
        if (!z) {
            return false;
        }
        createSimpleAlert(context, chat.title, LocaleController.getString(C2369R.string.SlowmodeSendError)).show();
        return true;
    }

    public static AlertDialog.Builder createNoAccessAlert(Context context, String str, String str2, Theme.ResourcesProvider resourcesProvider) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(str);
        HashMap map = new HashMap();
        int i = Theme.key_dialogTopBackground;
        map.put("info1.**", Integer.valueOf(Theme.getColor(i, resourcesProvider)));
        map.put("info2.**", Integer.valueOf(Theme.getColor(i, resourcesProvider)));
        builder.setTopAnimation(C2369R.raw.not_available, 52, false, Theme.getColor(i, resourcesProvider), map);
        builder.setTopAnimationIsNew(true);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Close), null);
        builder.setMessage(str2);
        return builder;
    }

    public static AlertDialog.Builder createSimpleAlert(Context context, String str) {
        return createSimpleAlert(context, null, str);
    }

    public static AlertDialog.Builder createSimpleAlert(Context context, String str, String str2) {
        return createSimpleAlert(context, str, str2, null);
    }

    public static AlertDialog.Builder createSimpleAlert(Context context, String str, String str2, Theme.ResourcesProvider resourcesProvider) {
        return createSimpleAlert(context, str, str2, null, null, resourcesProvider);
    }

    public static AlertDialog.Builder createSimpleAlert(Context context, String str, String str2, String str3, final Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
        if (context == null || str2 == null) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
        if (str == null) {
            str = LocaleController.getString(C2369R.string.AppName);
        }
        builder.setTitle(str);
        builder.setMessage(str2);
        if (str3 == null) {
            builder.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), null);
            return builder;
        }
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        builder.setPositiveButton(str3, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda9
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.$r8$lambda$m_KswKeRAssHkiLhfDbnwtSAr5M(runnable, alertDialog, i);
            }
        });
        return builder;
    }

    public static /* synthetic */ void $r8$lambda$m_KswKeRAssHkiLhfDbnwtSAr5M(Runnable runnable, AlertDialog alertDialog, int i) {
        alertDialog.dismiss();
        if (runnable != null) {
            runnable.run();
        }
    }

    public static void createStoriesAlbumEnterNameForCreate(Context context, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider, MessagesStorage.StringCallback stringCallback) {
        createStoriesAlbumEnterName(context, baseFragment, LocaleController.getString(C2369R.string.StoriesAlbumCreateNew), LocaleController.getString(C2369R.string.StoriesAlbumAddHint), LocaleController.getString(C2369R.string.StoriesAlbumTitleInputHint), null, LocaleController.getString(C2369R.string.Create), resourcesProvider, stringCallback);
    }

    public static void createStoriesAlbumEnterNameForRename(Context context, BaseFragment baseFragment, String str, Theme.ResourcesProvider resourcesProvider, MessagesStorage.StringCallback stringCallback) {
        createStoriesAlbumEnterName(context, baseFragment, LocaleController.getString(C2369R.string.StoriesAlbumRename), LocaleController.getString(C2369R.string.StoriesAlbumRenameHint), LocaleController.getString(C2369R.string.StoriesAlbumTitleInputHint), str, LocaleController.getString(C2369R.string.Rename), resourcesProvider, stringCallback);
    }

    public static void createStoriesAlbumEnterName(Context context, final BaseFragment baseFragment, String str, String str2, String str3, String str4, String str5, final Theme.ResourcesProvider resourcesProvider, final MessagesStorage.StringCallback stringCallback) {
        final Activity activityFindActivity = AndroidUtilities.findActivity(context);
        final View currentFocus = activityFindActivity != null ? activityFindActivity.getCurrentFocus() : null;
        final AlertDialog[] alertDialogArr = new AlertDialog[1];
        AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
        builder.setTitle(str == null ? LocaleController.getString(C2369R.string.AppName) : str);
        builder.setMessage(str2);
        final EditTextCaption editTextCaption = new EditTextCaption(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.2
            AnimatedTextView.AnimatedTextDrawable limit;
            AnimatedColor limitColor = new AnimatedColor(this);
            private int limitCount;

            {
                AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = new AnimatedTextView.AnimatedTextDrawable(false, true, true);
                this.limit = animatedTextDrawable;
                animatedTextDrawable.setAnimationProperties(0.2f, 0L, 160L, CubicBezierInterpolator.EASE_OUT_QUINT);
                this.limit.setTextSize(AndroidUtilities.m1146dp(15.33f));
                this.limit.setCallback(this);
                this.limit.setGravity(5);
            }

            @Override // android.widget.TextView, android.view.View
            protected boolean verifyDrawable(Drawable drawable) {
                return drawable == this.limit || super.verifyDrawable(drawable);
            }

            @Override // org.telegram.p023ui.Components.EditTextEffects, android.widget.TextView
            protected void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                super.onTextChanged(charSequence, i, i2, i3);
                if (this.limit != null) {
                    this.limitCount = 12 - charSequence.length();
                    this.limit.cancelAnimation();
                    AnimatedTextView.AnimatedTextDrawable animatedTextDrawable = this.limit;
                    String str6 = "";
                    if (this.limitCount <= 4) {
                        str6 = "" + this.limitCount;
                    }
                    animatedTextDrawable.setText(str6);
                }
            }

            @Override // org.telegram.p023ui.Components.EditTextBoldCursor, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                super.dispatchDraw(canvas);
                this.limit.setTextColor(this.limitColor.set(Theme.getColor(this.limitCount < 0 ? Theme.key_text_RedRegular : Theme.key_dialogSearchHint, resourcesProvider)));
                this.limit.setBounds(getScrollX(), 0, getScrollX() + getWidth(), getHeight());
                this.limit.draw(canvas);
            }
        };
        editTextCaption.lineYFix = true;
        editTextCaption.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda135
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return AlertsCreator.m7632$r8$lambda$XmmyoHn6lHAr_950xOv40giscI(editTextCaption, stringCallback, alertDialogArr, currentFocus, textView, i, keyEvent);
            }
        });
        editTextCaption.setTextSize(1, 18.0f);
        editTextCaption.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
        editTextCaption.setHintColor(Theme.getColor(Theme.key_groupcreate_hintText, resourcesProvider));
        editTextCaption.setHintText(str3);
        editTextCaption.setFocusable(true);
        editTextCaption.setInputType(147457);
        editTextCaption.setLineColors(Theme.getColor(Theme.key_windowBackgroundWhiteInputField, resourcesProvider), Theme.getColor(Theme.key_windowBackgroundWhiteInputFieldActivated, resourcesProvider), Theme.getColor(Theme.key_text_RedRegular, resourcesProvider));
        editTextCaption.setImeOptions(6);
        editTextCaption.setBackgroundDrawable(null);
        editTextCaption.setPadding(0, AndroidUtilities.m1146dp(6.0f), 0, AndroidUtilities.m1146dp(6.0f));
        editTextCaption.setText(str4);
        editTextCaption.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Components.AlertsCreator.3
            boolean ignoreTextChange;

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (!this.ignoreTextChange && editable.length() > 12) {
                    this.ignoreTextChange = true;
                    editable.delete(12, editable.length());
                    AndroidUtilities.shakeView(editTextCaption);
                    try {
                        editTextCaption.performHapticFeedback(3, 2);
                    } catch (Exception unused) {
                    }
                    this.ignoreTextChange = false;
                }
            }
        });
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.addView(editTextCaption, LayoutHelper.createLinear(-1, -2, 24.0f, 0.0f, 24.0f, 10.0f));
        builder.makeCustomMaxHeight();
        builder.setView(linearLayout);
        builder.setWidth(AndroidUtilities.m1146dp(292.0f));
        builder.setPositiveButton(str5, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda136
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.$r8$lambda$4X5evEaly5Z5noomoLqLYltJee8(editTextCaption, stringCallback, alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda137
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialogArr[0] = builder.create();
        if (baseFragment != null) {
            AndroidUtilities.requestAdjustNothing(activityFindActivity, baseFragment.getClassGuid());
        }
        alertDialogArr[0].setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda138
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AlertsCreator.m7605$r8$lambda$C14bQF25GcuJmKEUteH82ngXs(editTextCaption, baseFragment, activityFindActivity, dialogInterface);
            }
        });
        alertDialogArr[0].setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda139
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                AlertsCreator.m7608$r8$lambda$DSoEFF3CSrWwNeXzgxe3UFG4A0(editTextCaption, dialogInterface);
            }
        });
        alertDialogArr[0].show();
        alertDialogArr[0].setDismissDialogByButtons(false);
        editTextCaption.setSelection(editTextCaption.getText().length());
    }

    /* renamed from: $r8$lambda$XmmyoHn6lHAr_950-xOv40giscI, reason: not valid java name */
    public static /* synthetic */ boolean m7632$r8$lambda$XmmyoHn6lHAr_950xOv40giscI(EditTextCaption editTextCaption, MessagesStorage.StringCallback stringCallback, AlertDialog[] alertDialogArr, View view, TextView textView, int i, KeyEvent keyEvent) {
        if (i != 6) {
            return false;
        }
        String string = editTextCaption.getText().toString();
        if (string.length() > 12) {
            AndroidUtilities.shakeView(editTextCaption);
            return true;
        }
        stringCallback.run(string);
        AlertDialog alertDialog = alertDialogArr[0];
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (view != null) {
            view.requestFocus();
        }
        return true;
    }

    public static /* synthetic */ void $r8$lambda$4X5evEaly5Z5noomoLqLYltJee8(EditTextCaption editTextCaption, MessagesStorage.StringCallback stringCallback, AlertDialog alertDialog, int i) {
        String strTrim = editTextCaption.getText().toString().trim();
        if (strTrim.length() > 12 || strTrim.isEmpty()) {
            AndroidUtilities.shakeView(editTextCaption);
        } else {
            stringCallback.run(strTrim);
            alertDialog.dismiss();
        }
    }

    /* renamed from: $r8$lambda$C14bQF25GcuJm-KEUteH82ng-Xs, reason: not valid java name */
    public static /* synthetic */ void m7605$r8$lambda$C14bQF25GcuJmKEUteH82ngXs(EditTextCaption editTextCaption, BaseFragment baseFragment, Activity activity, DialogInterface dialogInterface) {
        AndroidUtilities.hideKeyboard(editTextCaption);
        if (baseFragment != null) {
            AndroidUtilities.requestAdjustResize(activity, baseFragment.getClassGuid());
        }
    }

    /* renamed from: $r8$lambda$DSoEFF3CSrWwNeXzgxe3UFG4A-0, reason: not valid java name */
    public static /* synthetic */ void m7608$r8$lambda$DSoEFF3CSrWwNeXzgxe3UFG4A0(EditTextCaption editTextCaption, DialogInterface dialogInterface) {
        editTextCaption.requestFocus();
        AndroidUtilities.showKeyboard(editTextCaption);
    }

    public static Dialog showSimpleAlert(BaseFragment baseFragment, String str) {
        return showSimpleAlert(baseFragment, null, str);
    }

    public static Dialog showSimpleAlert(BaseFragment baseFragment, String str, String str2) {
        return showSimpleAlert(baseFragment, str, str2, null);
    }

    public static Dialog showSimpleAlert(BaseFragment baseFragment, String str, String str2, Theme.ResourcesProvider resourcesProvider) {
        if (baseFragment == null) {
            baseFragment = LaunchActivity.getSafeLastFragment();
        }
        if (str2 == null || baseFragment == null || baseFragment.getParentActivity() == null) {
            return null;
        }
        AlertDialog alertDialogCreate = createSimpleAlert(baseFragment.getParentActivity(), str, str2, resourcesProvider).create();
        baseFragment.showDialog(alertDialogCreate);
        return alertDialogCreate;
    }

    public static AlertDialog showSimpleConfirmAlert(BaseFragment baseFragment, String str, CharSequence charSequence, String str2, boolean z, final Runnable runnable) {
        TextView textView;
        AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
        builder.setTitle(str);
        builder.setMessage(charSequence);
        builder.setPositiveButton(str2, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda88
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.$r8$lambda$xAICqTdLslkEeeVJ8RNrduYOWBQ(runnable, alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        AlertDialog alertDialogCreate = builder.create();
        baseFragment.showDialog(alertDialogCreate);
        if (z && (textView = (TextView) alertDialogCreate.getButton(-1)) != null) {
            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
        return alertDialogCreate;
    }

    public static /* synthetic */ void $r8$lambda$xAICqTdLslkEeeVJ8RNrduYOWBQ(Runnable runnable, AlertDialog alertDialog, int i) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public static void showBlockReportSpamReplyAlert(final ChatActivity chatActivity, final MessageObject messageObject, long j, final Theme.ResourcesProvider resourcesProvider, final Runnable runnable) {
        if (chatActivity == null || chatActivity.getParentActivity() == null || messageObject == null) {
            return;
        }
        final AccountInstance accountInstance = chatActivity.getAccountInstance();
        TLRPC.User user = j > 0 ? accountInstance.getMessagesController().getUser(Long.valueOf(j)) : null;
        TLRPC.Chat chat = j < 0 ? accountInstance.getMessagesController().getChat(Long.valueOf(-j)) : null;
        if (user == null && chat == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(chatActivity.getParentActivity(), resourcesProvider);
        builder.setDimEnabled(runnable == null);
        builder.setOnPreDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda89
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AlertsCreator.$r8$lambda$2IFuh6MCJwGC3wMUj941yGpy2j8(runnable, dialogInterface);
            }
        });
        builder.setTitle(LocaleController.getString(C2369R.string.BlockUser));
        if (user != null) {
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("BlockUserReplyAlert", C2369R.string.BlockUserReplyAlert, UserObject.getFirstName(user))));
        } else {
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("BlockUserReplyAlert", C2369R.string.BlockUserReplyAlert, chat.title)));
        }
        LinearLayout linearLayout = new LinearLayout(chatActivity.getParentActivity());
        linearLayout.setOrientation(1);
        final CheckBoxCell[] checkBoxCellArr = {new CheckBoxCell(chatActivity.getParentActivity(), 1, resourcesProvider)};
        checkBoxCellArr[0].setBackgroundDrawable(Theme.getSelectorDrawable(false));
        checkBoxCellArr[0].setTag(0);
        checkBoxCellArr[0].setText(LocaleController.getString(C2369R.string.DeleteReportSpam), "", true, false);
        checkBoxCellArr[0].setPadding(LocaleController.isRTL ? AndroidUtilities.m1146dp(16.0f) : AndroidUtilities.m1146dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.m1146dp(8.0f) : AndroidUtilities.m1146dp(16.0f), 0);
        linearLayout.addView(checkBoxCellArr[0], LayoutHelper.createLinear(-1, -2));
        checkBoxCellArr[0].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda90
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                checkBoxCellArr[((Integer) view.getTag()).intValue()].setChecked(!r0[view.intValue()].isChecked(), true);
            }
        });
        builder.setView(linearLayout);
        final TLRPC.User user2 = user;
        final TLRPC.Chat chat2 = chat;
        builder.setPositiveButton(LocaleController.getString(C2369R.string.BlockAndDeleteReplies), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda91
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.$r8$lambda$TsRZukJMDin2Hqlq9XTOHIv7Hd4(user2, accountInstance, chatActivity, chat2, messageObject, checkBoxCellArr, resourcesProvider, alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        AlertDialog alertDialogCreate = builder.create();
        chatActivity.showDialog(alertDialogCreate);
        TextView textView = (TextView) alertDialogCreate.getButton(-1);
        if (textView != null) {
            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
    }

    public static /* synthetic */ void $r8$lambda$2IFuh6MCJwGC3wMUj941yGpy2j8(Runnable runnable, DialogInterface dialogInterface) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public static /* synthetic */ void $r8$lambda$TsRZukJMDin2Hqlq9XTOHIv7Hd4(TLRPC.User user, final AccountInstance accountInstance, ChatActivity chatActivity, TLRPC.Chat chat, MessageObject messageObject, CheckBoxCell[] checkBoxCellArr, Theme.ResourcesProvider resourcesProvider, AlertDialog alertDialog, int i) {
        UndoView undoView;
        if (user != null) {
            accountInstance.getMessagesStorage().deleteUserChatHistory(chatActivity.getDialogId(), user.f1734id);
        } else {
            accountInstance.getMessagesStorage().deleteUserChatHistory(chatActivity.getDialogId(), -chat.f1571id);
        }
        TLRPC.TL_contacts_blockFromReplies tL_contacts_blockFromReplies = new TLRPC.TL_contacts_blockFromReplies();
        tL_contacts_blockFromReplies.msg_id = messageObject.getId();
        tL_contacts_blockFromReplies.delete_message = true;
        tL_contacts_blockFromReplies.delete_history = true;
        if (checkBoxCellArr[0].isChecked()) {
            tL_contacts_blockFromReplies.report_spam = true;
            if (chatActivity.getParentActivity() != null && (undoView = chatActivity.getUndoView()) != null) {
                undoView.showWithAction(0L, 74, (Runnable) null);
            }
        }
        accountInstance.getConnectionsManager().sendRequest(tL_contacts_blockFromReplies, new RequestDelegate() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda140
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AlertsCreator.m7619$r8$lambda$LoeSYhrAecvAKcTVA4MO9MjDM(accountInstance, tLObject, tL_error);
            }
        });
    }

    /* renamed from: $r8$lambda$Lo-eSYhrAecvAKcTVA4MO9Mj-DM, reason: not valid java name */
    public static /* synthetic */ void m7619$r8$lambda$LoeSYhrAecvAKcTVA4MO9MjDM(AccountInstance accountInstance, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject instanceof TLRPC.Updates) {
            accountInstance.getMessagesController().processUpdates((TLRPC.Updates) tLObject, false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r5v0 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v4, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void showBlockReportSpamAlert(org.telegram.p023ui.ActionBar.BaseFragment r18, long r19, final org.telegram.tgnet.TLRPC.User r21, final org.telegram.tgnet.TLRPC.Chat r22, final org.telegram.tgnet.TLRPC.EncryptedChat r23, final boolean r24, org.telegram.tgnet.TLRPC.ChatFull r25, final org.telegram.messenger.MessagesStorage.IntCallback r26, org.telegram.ui.ActionBar.Theme.ResourcesProvider r27) {
        /*
            Method dump skipped, instructions count: 443
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.AlertsCreator.showBlockReportSpamAlert(org.telegram.ui.ActionBar.BaseFragment, long, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Chat, org.telegram.tgnet.TLRPC$EncryptedChat, boolean, org.telegram.tgnet.TLRPC$ChatFull, org.telegram.messenger.MessagesStorage$IntCallback, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    public static /* synthetic */ void $r8$lambda$stvsJ3bdnigphWMkQGbTolBy73M(TLRPC.User user, AccountInstance accountInstance, CheckBoxCell[] checkBoxCellArr, long j, TLRPC.Chat chat, TLRPC.EncryptedChat encryptedChat, boolean z, MessagesStorage.IntCallback intCallback, AlertDialog alertDialog, int i) {
        CheckBoxCell checkBoxCell;
        if (user != null) {
            accountInstance.getMessagesController().blockPeer(user.f1734id);
        }
        if (checkBoxCellArr == null || ((checkBoxCell = checkBoxCellArr[0]) != null && checkBoxCell.isChecked())) {
            accountInstance.getMessagesController().reportSpam(j, user, chat, encryptedChat, chat != null && z);
        }
        if (checkBoxCellArr == null || checkBoxCellArr[1].isChecked()) {
            if (chat == null || ChatObject.isNotInChat(chat)) {
                accountInstance.getMessagesController().deleteDialog(j, 0);
            } else {
                accountInstance.getMessagesController().deleteParticipantFromChat(-j, accountInstance.getMessagesController().getUser(Long.valueOf(accountInstance.getUserConfig().getClientUserId())));
            }
            intCallback.run(1);
            return;
        }
        intCallback.run(0);
    }

    public static void showCustomNotificationsDialog(BaseFragment baseFragment, long j, int i, int i2, ArrayList arrayList, ArrayList arrayList2, int i3, MessagesStorage.IntCallback intCallback) throws Resources.NotFoundException {
        showCustomNotificationsDialog(baseFragment, j, i, i2, arrayList, arrayList2, i3, intCallback, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v6, types: [android.view.ViewGroup] */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r5v7, types: [android.view.View, android.widget.TextView] */
    public static void showCustomNotificationsDialog(final BaseFragment baseFragment, long j, final int i, final int i2, final ArrayList arrayList, final ArrayList arrayList2, final int i3, final MessagesStorage.IntCallback intCallback, final MessagesStorage.IntCallback intCallback2) throws Resources.NotFoundException {
        int i4;
        Object obj;
        final long j2 = j;
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        ?? r13 = 0;
        final boolean zIsGlobalNotificationsEnabled = NotificationsController.getInstance(i3).isGlobalNotificationsEnabled(j2, false, false);
        ?? r14 = 1;
        String[] strArr = {LocaleController.getString(C2369R.string.NotificationsTurnOn), LocaleController.formatString("MuteFor", C2369R.string.MuteFor, LocaleController.formatPluralString("Hours", 1, new Object[0])), LocaleController.formatString("MuteFor", C2369R.string.MuteFor, LocaleController.formatPluralString("Days", 2, new Object[0])), (j2 == 0 && (baseFragment instanceof NotificationsCustomSettingsActivity)) ? null : LocaleController.getString(C2369R.string.NotificationsCustomize), LocaleController.getString(C2369R.string.NotificationsTurnOff)};
        int[] iArr = {C2369R.drawable.notifications_on, C2369R.drawable.notifications_mute1h, C2369R.drawable.notifications_mute2d, C2369R.drawable.notifications_settings, C2369R.drawable.notifications_off};
        LinearLayout linearLayout = new LinearLayout(baseFragment.getParentActivity());
        linearLayout.setOrientation(1);
        final AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity());
        int i5 = 0;
        Object obj2 = linearLayout;
        while (i5 < 5) {
            if (strArr[i5] == null) {
                obj = obj2;
                i4 = i5;
            } else {
                ?? textView = new TextView(baseFragment.getParentActivity());
                Drawable drawable = baseFragment.getParentActivity().getResources().getDrawable(iArr[i5]);
                if (i5 == 4) {
                    textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
                    drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_text_RedRegular), PorterDuff.Mode.MULTIPLY));
                } else {
                    textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                    drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogIcon), PorterDuff.Mode.MULTIPLY));
                }
                textView.setTextSize(r14, 16.0f);
                textView.setLines(r14);
                textView.setMaxLines(r14);
                textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                textView.setTag(Integer.valueOf(i5));
                textView.setBackgroundDrawable(Theme.getSelectorDrawable(r13));
                textView.setPadding(AndroidUtilities.m1146dp(24.0f), r13, AndroidUtilities.m1146dp(24.0f), r13);
                textView.setSingleLine(r14);
                textView.setGravity(19);
                textView.setCompoundDrawablePadding(AndroidUtilities.m1146dp(26.0f));
                textView.setText(strArr[i5]);
                obj2.addView(textView, LayoutHelper.createLinear(-1, 48, 51));
                i4 = i5;
                obj = obj2;
                textView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda158
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AlertsCreator.$r8$lambda$ye02GVKvn7xdODVr5SALBC3gipA(j2, i3, zIsGlobalNotificationsEnabled, i, intCallback2, i2, baseFragment, arrayList, arrayList2, intCallback, builder, view);
                    }
                });
            }
            i5 = i4 + 1;
            j2 = j;
            obj2 = obj;
            r13 = 0;
            r14 = 1;
        }
        builder.setTitle(LocaleController.getString(C2369R.string.Notifications));
        builder.setView(obj2);
        baseFragment.showDialog(builder.create());
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static /* synthetic */ void $r8$lambda$ye02GVKvn7xdODVr5SALBC3gipA(long r20, int r22, boolean r23, int r24, org.telegram.messenger.MessagesStorage.IntCallback r25, int r26, org.telegram.p023ui.ActionBar.BaseFragment r27, java.util.ArrayList r28, java.util.ArrayList r29, org.telegram.messenger.MessagesStorage.IntCallback r30, org.telegram.ui.ActionBar.AlertDialog.Builder r31, android.view.View r32) {
        /*
            Method dump skipped, instructions count: 298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.AlertsCreator.$r8$lambda$ye02GVKvn7xdODVr5SALBC3gipA(long, int, boolean, int, org.telegram.messenger.MessagesStorage$IntCallback, int, org.telegram.ui.ActionBar.BaseFragment, java.util.ArrayList, java.util.ArrayList, org.telegram.messenger.MessagesStorage$IntCallback, org.telegram.ui.ActionBar.AlertDialog$Builder, android.view.View):void");
    }

    public static AlertDialog showSecretLocationAlert(Context context, int i, final Runnable runnable, boolean z, Theme.ResourcesProvider resourcesProvider) {
        ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        int i2 = MessagesController.getInstance(i).availableMapProviders;
        if ((i2 & 1) != 0) {
            arrayList.add(LocaleController.getString(C2369R.string.MapPreviewProviderTelegram));
            arrayList2.add(0);
        }
        if ((i2 & 2) != 0) {
            arrayList.add(LocaleController.getString(C2369R.string.MapPreviewProviderGoogle));
            arrayList2.add(1);
        }
        if ((i2 & 4) != 0) {
            arrayList.add(LocaleController.getString(C2369R.string.MapPreviewProviderYandex));
            arrayList2.add(3);
        }
        arrayList.add(LocaleController.getString(C2369R.string.MapPreviewProviderNobody));
        arrayList2.add(2);
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
        builder.setTitle(LocaleController.getString(C2369R.string.MapPreviewProviderTitle));
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        builder.setView(linearLayout);
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            RadioColorCell radioColorCell = new RadioColorCell(context, resourcesProvider);
            radioColorCell.setPadding(AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i3));
            radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            radioColorCell.setTextAndValue((CharSequence) arrayList.get(i3), SharedConfig.mapPreviewType == ((Integer) arrayList2.get(i3)).intValue());
            radioColorCell.setBackground(Theme.createSelectorDrawable(Theme.getColor(Theme.key_listSelector), 2));
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda42
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertsCreator.m7614$r8$lambda$HjZQO2WRyhuMC6NpTsPN7RgfSE(arrayList2, runnable, builder, view);
                }
            });
        }
        if (!z) {
            builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        }
        AlertDialog alertDialogShow = builder.show();
        if (z) {
            alertDialogShow.setCanceledOnTouchOutside(false);
        }
        return alertDialogShow;
    }

    /* renamed from: $r8$lambda$HjZQO2W-RyhuMC6NpTsPN7RgfSE, reason: not valid java name */
    public static /* synthetic */ void m7614$r8$lambda$HjZQO2WRyhuMC6NpTsPN7RgfSE(ArrayList arrayList, Runnable runnable, AlertDialog.Builder builder, View view) {
        SharedConfig.setSecretMapPreviewType(((Integer) arrayList.get(((Integer) view.getTag()).intValue())).intValue());
        if (runnable != null) {
            runnable.run();
        }
        builder.getDismissRunnable().run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void updateDayPicker(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2, numberPicker2.getValue());
        calendar.set(1, numberPicker3.getValue());
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(calendar.getActualMaximum(5));
    }

    private static void checkPickerDate(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int i = 1;
        int i2 = calendar.get(1);
        int i3 = calendar.get(2);
        int i4 = calendar.get(5);
        numberPicker3.setMinValue(i2);
        int value = numberPicker3.getValue();
        numberPicker2.setMinValue(value == i2 ? i3 : 0);
        int value2 = numberPicker2.getValue();
        if (value == i2 && value2 == i3) {
            i = i4;
        }
        numberPicker.setMinValue(i);
    }

    public static void showOpenUrlAlert(BaseFragment baseFragment, String str, boolean z, boolean z2) {
        showOpenUrlAlert(baseFragment, str, z, true, z2, false, null, null);
    }

    public static void showOpenUrlAlert(BaseFragment baseFragment, String str, boolean z, boolean z2, Theme.ResourcesProvider resourcesProvider) {
        showOpenUrlAlert(baseFragment, str, z, true, z2, false, null, resourcesProvider);
    }

    public static void showOpenUrlAlert(BaseFragment baseFragment, String str, boolean z, boolean z2, boolean z3, Browser.Progress progress, Theme.ResourcesProvider resourcesProvider) {
        showOpenUrlAlert(baseFragment, str, z, z2, z3, false, progress, resourcesProvider);
    }

    public static void showOpenUrlAlert(final BaseFragment baseFragment, final String str, boolean z, final boolean z2, boolean z3, boolean z4, final Browser.Progress progress, final Theme.ResourcesProvider resourcesProvider) {
        String strReplaceHostname;
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        long inlineReturn = baseFragment instanceof ChatActivity ? ((ChatActivity) baseFragment).getInlineReturn() : 0L;
        String scheme = str == null ? null : Uri.parse(str).getScheme();
        if (Browser.isInternalUrl(str, null) || !z3 || "mailto".equalsIgnoreCase(scheme)) {
            Browser.openUrl(baseFragment.getParentActivity(), Uri.parse(str), inlineReturn == 0, z2, z4 && checkInternalBotApp(str), progress, null, false, true, false);
            return;
        }
        if (z) {
            try {
                Uri uri = Uri.parse(str);
                strReplaceHostname = Browser.replaceHostname(uri, Browser.IDN_toUnicode(uri.getHost()), null);
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        } else {
            strReplaceHostname = str;
        }
        final long j = inlineReturn;
        final Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Browser.openUrl(baseFragment.getParentActivity(), Uri.parse(str), j == 0, z2, progress);
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity(), resourcesProvider);
        builder.setTitle(LocaleController.getString(C2369R.string.OpenUrlTitle));
        final AlertDialog[] alertDialogArr = new AlertDialog[1];
        SpannableString spannableString = new SpannableString(strReplaceHostname);
        spannableString.setSpan(new URLSpan(strReplaceHostname) { // from class: org.telegram.ui.Components.AlertsCreator.4
            @Override // android.text.style.URLSpan, android.text.style.ClickableSpan
            public void onClick(View view) {
                runnable.run();
                AlertDialog alertDialog = alertDialogArr[0];
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        }, 0, spannableString.length(), 33);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(LocaleController.getString(C2369R.string.OpenUrlAlert2));
        int iIndexOf = spannableStringBuilder.toString().indexOf("%1$s");
        if (iIndexOf >= 0) {
            spannableStringBuilder.replace(iIndexOf, iIndexOf + 4, (CharSequence) spannableString);
        }
        builder.setMessage(spannableStringBuilder);
        builder.setMessageTextViewClickable(false);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Open), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda1
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                runnable.run();
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        builder.setNeutralButton(LocaleController.getString(C2369R.string.Copy), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.m7620$r8$lambda$N3pgYJnYRr4GeV8nY2zfdvCmc8(str, baseFragment, resourcesProvider, alertDialog, i);
            }
        });
        AlertDialog alertDialogCreate = builder.create();
        alertDialogArr[0] = alertDialogCreate;
        baseFragment.showDialog(alertDialogCreate);
    }

    /* renamed from: $r8$lambda$N3pgYJnYRr4GeV8n-Y2zfdvCmc8, reason: not valid java name */
    public static /* synthetic */ void m7620$r8$lambda$N3pgYJnYRr4GeV8nY2zfdvCmc8(String str, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider, AlertDialog alertDialog, int i) {
        try {
            AndroidUtilities.addToClipboard(str);
            if (BulletinFactory.canShowBulletin(baseFragment)) {
                BulletinFactory.m1267of(baseFragment).createCopyLinkBulletin(LocaleController.getString(C2369R.string.LinkCopied), resourcesProvider).show();
            }
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    private static boolean checkInternalBotApp(String str) {
        return Uri.parse(str).getPath().matches("^/\\w*/[^\\d]*(?:\\?startapp=.*?|)$");
    }

    public static AlertDialog createSupportAlert(final BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return null;
        }
        LinkSpanDrawable.LinksTextView linksTextView = new LinkSpanDrawable.LinksTextView(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
        SpannableString spannableString = new SpannableString(Html.fromHtml(LocaleController.getString(C2369R.string.AskAQuestionInfo).replace("\n", "<br>")));
        for (URLSpan uRLSpan : (URLSpan[]) spannableString.getSpans(0, spannableString.length(), URLSpan.class)) {
            int spanStart = spannableString.getSpanStart(uRLSpan);
            int spanEnd = spannableString.getSpanEnd(uRLSpan);
            spannableString.removeSpan(uRLSpan);
            spannableString.setSpan(new URLSpanNoUnderline(uRLSpan.getURL()) { // from class: org.telegram.ui.Components.AlertsCreator.5
                @Override // org.telegram.p023ui.Components.URLSpanNoUnderline, android.text.style.URLSpan, android.text.style.ClickableSpan
                public void onClick(View view) {
                    baseFragment.dismissCurrentDialog();
                    super.onClick(view);
                }
            }, spanStart, spanEnd, 0);
        }
        linksTextView.setText(spannableString);
        linksTextView.setTextSize(1, 16.0f);
        linksTextView.setLinkTextColor(Theme.getColor(Theme.key_dialogTextLink, resourcesProvider));
        linksTextView.setHighlightColor(Theme.getColor(Theme.key_dialogLinkSelection, resourcesProvider));
        linksTextView.setPadding(AndroidUtilities.m1146dp(23.0f), 0, AndroidUtilities.m1146dp(23.0f), 0);
        linksTextView.setMovementMethod(new AndroidUtilities.LinkMovementMethodMy());
        linksTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
        AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity(), resourcesProvider);
        builder.setView(linksTextView);
        builder.setTitle(LocaleController.getString(C2369R.string.AskAQuestion));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.AskButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda41
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.performAskAQuestion(baseFragment);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        return builder.create();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void performAskAQuestion(final org.telegram.p023ui.ActionBar.BaseFragment r11) {
        /*
            int r0 = r11.getCurrentAccount()
            android.content.SharedPreferences r1 = org.telegram.messenger.MessagesController.getMainSettings(r0)
            java.lang.String r2 = "support_id2"
            r3 = 0
            long r5 = org.telegram.messenger.AndroidUtilities.getPrefIntOrLong(r1, r2, r3)
            r2 = 0
            r7 = 0
            int r8 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r8 == 0) goto L58
            org.telegram.messenger.MessagesController r3 = org.telegram.messenger.MessagesController.getInstance(r0)
            java.lang.Long r4 = java.lang.Long.valueOf(r5)
            org.telegram.tgnet.TLRPC$User r3 = r3.getUser(r4)
            if (r3 != 0) goto L57
            java.lang.String r4 = "support_user"
            java.lang.String r4 = r1.getString(r4, r7)
            if (r4 == 0) goto L57
            byte[] r4 = android.util.Base64.decode(r4, r2)     // Catch: java.lang.Exception -> L4c
            if (r4 == 0) goto L57
            org.telegram.tgnet.SerializedData r3 = new org.telegram.tgnet.SerializedData     // Catch: java.lang.Exception -> L4c
            r3.<init>(r4)     // Catch: java.lang.Exception -> L4c
            int r4 = r3.readInt32(r2)     // Catch: java.lang.Exception -> L4c
            org.telegram.tgnet.TLRPC$User r4 = org.telegram.tgnet.TLRPC.User.TLdeserialize(r3, r4, r2)     // Catch: java.lang.Exception -> L4c
            if (r4 == 0) goto L4e
            long r5 = r4.f1734id     // Catch: java.lang.Exception -> L4c
            r8 = 333000(0x514c8, double:1.64524E-318)
            int r10 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r10 != 0) goto L4e
            r4 = r7
            goto L4e
        L4c:
            r3 = move-exception
            goto L53
        L4e:
            r3.cleanup()     // Catch: java.lang.Exception -> L4c
            r7 = r4
            goto L58
        L53:
            org.telegram.messenger.FileLog.m1160e(r3)
            goto L58
        L57:
            r7 = r3
        L58:
            if (r7 != 0) goto L7c
            org.telegram.ui.ActionBar.AlertDialog r3 = new org.telegram.ui.ActionBar.AlertDialog
            android.app.Activity r4 = r11.getParentActivity()
            r5 = 3
            r3.<init>(r4, r5)
            r3.setCanCancel(r2)
            r3.show()
            org.telegram.tgnet.TLRPC$TL_help_getSupport r2 = new org.telegram.tgnet.TLRPC$TL_help_getSupport
            r2.<init>()
            org.telegram.tgnet.ConnectionsManager r4 = org.telegram.tgnet.ConnectionsManager.getInstance(r0)
            org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda92 r5 = new org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda92
            r5.<init>()
            r4.sendRequest(r2, r5)
            goto L98
        L7c:
            org.telegram.messenger.MessagesController r0 = org.telegram.messenger.MessagesController.getInstance(r0)
            r1 = 1
            r0.putUser(r7, r1)
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.lang.String r1 = "user_id"
            long r2 = r7.f1734id
            r0.putLong(r1, r2)
            org.telegram.ui.ChatActivity r1 = new org.telegram.ui.ChatActivity
            r1.<init>(r0)
            r11.presentFragment(r1)
        L98:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.AlertsCreator.performAskAQuestion(org.telegram.ui.ActionBar.BaseFragment):void");
    }

    public static /* synthetic */ void $r8$lambda$WeaOSzu0dIRNAH1KDFakpay1eKo(final SharedPreferences sharedPreferences, final AlertDialog alertDialog, final int i, final BaseFragment baseFragment, TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tL_error == null) {
            final TLRPC.TL_help_support tL_help_support = (TLRPC.TL_help_support) tLObject;
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda141
                @Override // java.lang.Runnable
                public final void run() {
                    AlertsCreator.$r8$lambda$08iEEx2f71TBdxBEtMHrKckpq4Y(sharedPreferences, tL_help_support, alertDialog, i, baseFragment);
                }
            });
        } else {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda142
                @Override // java.lang.Runnable
                public final void run() {
                    AlertsCreator.$r8$lambda$3yCzB4uXrGUMAmj2esXjM3qfXio(alertDialog);
                }
            });
        }
    }

    public static /* synthetic */ void $r8$lambda$08iEEx2f71TBdxBEtMHrKckpq4Y(SharedPreferences sharedPreferences, TLRPC.TL_help_support tL_help_support, AlertDialog alertDialog, int i, BaseFragment baseFragment) {
        SharedPreferences.Editor editorEdit = sharedPreferences.edit();
        editorEdit.putLong("support_id2", tL_help_support.user.f1734id);
        SerializedData serializedData = new SerializedData();
        tL_help_support.user.serializeToStream(serializedData);
        editorEdit.putString("support_user", Base64.encodeToString(serializedData.toByteArray(), 0));
        editorEdit.apply();
        serializedData.cleanup();
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(tL_help_support.user);
        MessagesStorage.getInstance(i).putUsersAndChats(arrayList, null, true, true);
        MessagesController.getInstance(i).putUser(tL_help_support.user, false);
        Bundle bundle = new Bundle();
        bundle.putLong("user_id", tL_help_support.user.f1734id);
        baseFragment.presentFragment(new ChatActivity(bundle));
    }

    public static /* synthetic */ void $r8$lambda$3yCzB4uXrGUMAmj2esXjM3qfXio(AlertDialog alertDialog) {
        try {
            alertDialog.dismiss();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    public static void createImportDialogAlert(BaseFragment baseFragment, String str, String str2, TLRPC.User user, TLRPC.Chat chat, final Runnable runnable) {
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        if (chat == null && user == null) {
            return;
        }
        int currentAccount = baseFragment.getCurrentAccount();
        Activity parentActivity = baseFragment.getParentActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        long clientUserId = UserConfig.getInstance(currentAccount).getClientUserId();
        TextView textView = new TextView(parentActivity);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        textView.setTextSize(1, 16.0f);
        textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        FrameLayout frameLayout = new FrameLayout(parentActivity);
        builder.setView(frameLayout);
        AvatarDrawable avatarDrawable = new AvatarDrawable();
        avatarDrawable.setTextSize(AndroidUtilities.m1146dp(12.0f));
        BackupImageView backupImageView = new BackupImageView(parentActivity);
        backupImageView.setRoundRadius(ExteraConfig.getAvatarCorners(40.0f));
        frameLayout.addView(backupImageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, 22.0f, 5.0f, 22.0f, 0.0f));
        TextView textView2 = new TextView(parentActivity);
        textView2.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
        textView2.setTextSize(1, 20.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setLines(1);
        textView2.setMaxLines(1);
        textView2.setSingleLine(true);
        textView2.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        textView2.setEllipsize(TextUtils.TruncateAt.END);
        textView2.setText(LocaleController.getString(C2369R.string.ImportMessages));
        boolean z = LocaleController.isRTL;
        frameLayout.addView(textView2, LayoutHelper.createFrame(-1, -2.0f, (z ? 5 : 3) | 48, z ? 21 : 76, 11.0f, z ? 76 : 21, 0.0f));
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 57.0f, 24.0f, 9.0f));
        if (user != null) {
            if (UserObject.isReplyUser(user)) {
                avatarDrawable.setScaleSize(0.8f);
                avatarDrawable.setAvatarType(12);
                backupImageView.setImage((ImageLocation) null, (String) null, avatarDrawable, user);
            } else if (user.f1734id == clientUserId) {
                avatarDrawable.setScaleSize(0.8f);
                avatarDrawable.setAvatarType(1);
                backupImageView.setImage((ImageLocation) null, (String) null, avatarDrawable, user);
            } else {
                avatarDrawable.setScaleSize(1.0f);
                avatarDrawable.setInfo(currentAccount, user);
                backupImageView.setForUserOrChat(user, avatarDrawable);
            }
        } else {
            avatarDrawable.setInfo(currentAccount, chat);
            backupImageView.setForUserOrChat(chat, avatarDrawable);
        }
        textView.setText(AndroidUtilities.replaceTags(str2));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Import), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda205
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.$r8$lambda$VEEIVioxNbJxWWf52MaAO7e5fcA(runnable, alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        baseFragment.showDialog(builder.create());
    }

    public static /* synthetic */ void $r8$lambda$VEEIVioxNbJxWWf52MaAO7e5fcA(Runnable runnable, AlertDialog alertDialog, int i) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public static void createBotLaunchAlert(final BaseFragment baseFragment, final TLRPC.User user, final Runnable runnable, final Runnable runnable2) {
        final Context context = baseFragment.getContext();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LinkSpanDrawable.LinksTextView linksTextView = new LinkSpanDrawable.LinksTextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.6
            @Override // org.telegram.ui.Components.LinkSpanDrawable.LinksTextView, android.widget.TextView
            public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
                super.setText(Emoji.replaceEmoji(charSequence, getPaint().getFontMetricsInt(), false), bufferType);
            }
        };
        NotificationCenter.listenEmojiLoading(linksTextView);
        linksTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        linksTextView.setLinkTextColor(Theme.getColor(Theme.key_chat_messageLinkIn));
        linksTextView.setTextSize(1, 16.0f);
        linksTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        FrameLayout frameLayout = new FrameLayout(context);
        builder.setCustomViewOffset(6);
        builder.setView(frameLayout);
        AvatarDrawable avatarDrawable = new AvatarDrawable();
        avatarDrawable.setTextSize(AndroidUtilities.m1146dp(18.0f));
        BackupImageView backupImageView = new BackupImageView(context);
        backupImageView.setRoundRadius(AndroidUtilities.m1146dp(20.0f));
        frameLayout.addView(backupImageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, 22.0f, 5.0f, 22.0f, 0.0f));
        SimpleTextView simpleTextView = new SimpleTextView(context);
        simpleTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
        simpleTextView.setTextSize(20);
        simpleTextView.setTypeface(AndroidUtilities.bold());
        simpleTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        simpleTextView.setEllipsizeByGradient(true);
        simpleTextView.setText(user.first_name);
        if (user.scam) {
            simpleTextView.setRightDrawable(Theme.dialogs_scamDrawable);
        } else if (user.fake) {
            simpleTextView.setRightDrawable(Theme.dialogs_fakeDrawable);
        } else if (user.verified) {
            Drawable drawableMutate = context.getResources().getDrawable(C2369R.drawable.verified_area).mutate();
            int color = Theme.getColor(Theme.key_chats_verifiedBackground);
            PorterDuff.Mode mode = PorterDuff.Mode.MULTIPLY;
            drawableMutate.setColorFilter(new PorterDuffColorFilter(color, mode));
            Drawable drawableMutate2 = context.getResources().getDrawable(C2369R.drawable.verified_check).mutate();
            drawableMutate2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chats_verifiedCheck), mode));
            simpleTextView.setRightDrawable(new CombinedDrawable(drawableMutate, drawableMutate2));
        }
        TextView textView = new TextView(context);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlue));
        textView.setTextSize(1, 14.0f);
        textView.setLines(1);
        textView.setMaxLines(1);
        textView.setSingleLine(true);
        textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        textView.setEllipsize(TextUtils.TruncateAt.END);
        textView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda112
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.m7652$r8$lambda$x6WwnpzN_r7_9eWIeY5RZMaskk(user, baseFragment, builder, view);
            }
        });
        SpannableString spannableStringValueOf = SpannableString.valueOf(LocaleController.getString(C2369R.string.MoreAboutThisBot) + "  ");
        ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2369R.drawable.attach_arrow_right);
        coloredImageSpan.setTopOffset(1);
        coloredImageSpan.setSize(AndroidUtilities.m1146dp(10.0f));
        spannableStringValueOf.setSpan(coloredImageSpan, spannableStringValueOf.length() - 1, spannableStringValueOf.length(), 33);
        textView.setText(spannableStringValueOf);
        boolean z = LocaleController.isRTL;
        frameLayout.addView(simpleTextView, LayoutHelper.createFrame(-1, -2.0f, (z ? 5 : 3) | 48, z ? 21 : 76, 0.0f, z ? 76 : 21, 0.0f));
        boolean z2 = LocaleController.isRTL;
        frameLayout.addView(textView, LayoutHelper.createFrame(-1, -2.0f, (z2 ? 5 : 3) | 48, z2 ? 21 : 76, 24.0f, z2 ? 76 : 21, 0.0f));
        frameLayout.addView(linksTextView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 57.0f, 24.0f, 1.0f));
        if (UserObject.isReplyUser(user)) {
            avatarDrawable.setScaleSize(0.8f);
            avatarDrawable.setAvatarType(12);
            backupImageView.setImage((ImageLocation) null, (String) null, avatarDrawable, user);
        } else {
            avatarDrawable.setScaleSize(1.0f);
            avatarDrawable.setInfo(baseFragment.getCurrentAccount(), user);
            backupImageView.setForUserOrChat(user, avatarDrawable);
        }
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Start), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda113
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.$r8$lambda$YnqCk8rrMTZQI7tR99_85pMTKgU(runnable, alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        final AlertDialog alertDialogCreate = builder.create();
        baseFragment.showDialog(alertDialogCreate, false, new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda114
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AlertsCreator.m7637$r8$lambda$bLnB8D9iAW3SGxO7HWMRZ3Hks(runnable2, dialogInterface);
            }
        });
        linksTextView.setText(AndroidUtilities.replaceSingleTag(LocaleController.getString(C2369R.string.BotWebViewStartPermission2), new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda115
            @Override // java.lang.Runnable
            public final void run() {
                AlertsCreator.$r8$lambda$sntwN9as6dYP7naeRnDpClCn3xE(alertDialogCreate, context);
            }
        }));
    }

    /* renamed from: $r8$lambda$x6WwnpzN_r7_9eWIeY5-RZMaskk, reason: not valid java name */
    public static /* synthetic */ void m7652$r8$lambda$x6WwnpzN_r7_9eWIeY5RZMaskk(TLRPC.User user, BaseFragment baseFragment, AlertDialog.Builder builder, View view) {
        Bundle bundle = new Bundle();
        bundle.putLong("user_id", user.f1734id);
        if (baseFragment.getMessagesController().checkCanOpenChat(bundle, baseFragment)) {
            baseFragment.presentFragment(new ProfileActivity(bundle));
        }
        builder.getDismissRunnable().run();
    }

    public static /* synthetic */ void $r8$lambda$YnqCk8rrMTZQI7tR99_85pMTKgU(Runnable runnable, AlertDialog alertDialog, int i) {
        if (runnable != null) {
            runnable.run();
        }
    }

    /* renamed from: $r8$lambda$bLn-B8-D9iAW3SGxO7HWMRZ3Hks, reason: not valid java name */
    public static /* synthetic */ void m7637$r8$lambda$bLnB8D9iAW3SGxO7HWMRZ3Hks(Runnable runnable, DialogInterface dialogInterface) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public static /* synthetic */ void $r8$lambda$sntwN9as6dYP7naeRnDpClCn3xE(AlertDialog alertDialog, Context context) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        Browser.openUrl(context, LocaleController.getString(C2369R.string.BotWebViewStartPermissionLink));
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x01be  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0284  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0293  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void createBotLaunchAlert(final org.telegram.p023ui.ActionBar.BaseFragment r28, final java.util.concurrent.atomic.AtomicBoolean r29, final org.telegram.tgnet.TLRPC.User r30, final java.lang.Runnable r31) {
        /*
            Method dump skipped, instructions count: 725
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.AlertsCreator.createBotLaunchAlert(org.telegram.ui.ActionBar.BaseFragment, java.util.concurrent.atomic.AtomicBoolean, org.telegram.tgnet.TLRPC$User, java.lang.Runnable):void");
    }

    public static /* synthetic */ void $r8$lambda$IvvOL90qZrgrDGoKxXvuNSvA6Ss(TLRPC.User user, BaseFragment baseFragment, AlertDialog.Builder builder, View view) {
        Bundle bundle = new Bundle();
        bundle.putLong("user_id", user.f1734id);
        if (baseFragment.getMessagesController().checkCanOpenChat(bundle, baseFragment)) {
            baseFragment.presentFragment(new ProfileActivity(bundle));
        }
        builder.getDismissRunnable().run();
    }

    public static /* synthetic */ void $r8$lambda$B0Yqtzs3gZcPPXe0fAuVobIJwZE(AtomicBoolean atomicBoolean, View view) {
        atomicBoolean.set(!atomicBoolean.get());
        ((CheckBoxCell) view).setChecked(atomicBoolean.get(), true);
    }

    public static /* synthetic */ void $r8$lambda$RAZx2Urp18Ebqf4iLCvRwoud7Fc(AlertDialog alertDialog, Context context) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        Browser.openUrl(context, LocaleController.getString(C2369R.string.BotWebViewStartPermissionLink));
    }

    public static boolean ensurePaidMessagesMultiConfirmationTopicKeys(int i, ArrayList arrayList, int i2, Utilities.Callback callback) {
        HashSet hashSet = new HashSet();
        if (arrayList != null) {
            int size = arrayList.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList.get(i3);
                i3++;
                hashSet.add(Long.valueOf(((MessagesStorage.TopicKey) obj).dialogId));
            }
        }
        return ensurePaidMessagesMultiConfirmation(i, new ArrayList(hashSet), i2, callback);
    }

    public static boolean ensurePaidMessagesMultiConfirmation(final int i, final ArrayList arrayList, int i2, final Utilities.Callback callback) {
        if (callback == null) {
            return false;
        }
        if (arrayList == null || arrayList.isEmpty()) {
            callback.run(new HashMap());
            return false;
        }
        final HashMap map = new HashMap();
        int size = arrayList.size();
        long j = 0;
        int i3 = 0;
        boolean z = true;
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            Long l = (Long) obj;
            long jLongValue = l.longValue();
            long sendPaidMessagesStars = MessagesController.getInstance(i).getSendPaidMessagesStars(jLongValue);
            if (sendPaidMessagesStars <= 0 && jLongValue > 0) {
                sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(i).isUserContactBlocked(jLongValue));
            }
            map.put(l, Long.valueOf(sendPaidMessagesStars));
            j += sendPaidMessagesStars;
            StarsController.getInstance(i).sendingMessagesCount.put(l, Integer.valueOf(i2));
            if (sendPaidMessagesStars > 0) {
                i3++;
            }
            if (sendPaidMessagesStars > 0 && z) {
                if (MessagesController.getInstance(i).getMainSettings().getLong("ask_paid_message_" + jLongValue + "_price", 0L) < sendPaidMessagesStars) {
                    z = false;
                }
            }
        }
        final long jMax = j * Math.max(1, i2);
        if (z || jMax <= 0) {
            callback.run(map);
            return false;
        }
        final Activity activity = AndroidUtilities.getActivity();
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        final Theme.ResourcesProvider darkThemeResourceProvider = (PhotoViewer.getInstance().isVisible() || (safeLastFragment != null && safeLastFragment.hasShownSheet())) ? new DarkThemeResourceProvider() : safeLastFragment != null ? safeLastFragment.getResourceProvider() : null;
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessageMulti1", i3)));
        spannableStringBuilder.append((CharSequence) " ");
        spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessageMulti2", (int) jMax, LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessageMulti2Messages", Math.max(1, i3) * i2))));
        showAlertWithCheckbox(activity, LocaleController.getString(C2369R.string.MessageLockedStarsConfirmTitle), spannableStringBuilder, LocaleController.getString(C2369R.string.MessageLockedStarsConfirmMessageDontAsk), LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessagePay", i2), new Utilities.Callback() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda47
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj2) {
                AlertsCreator.m7628$r8$lambda$WICQmL3mk2m2tUE77gymTUZ4N8(i, arrayList, jMax, activity, darkThemeResourceProvider, callback, map, (Boolean) obj2);
            }
        }, darkThemeResourceProvider);
        return true;
    }

    /* renamed from: $r8$lambda$WICQmL3mk2m2tUE77gymT-UZ4N8, reason: not valid java name */
    public static /* synthetic */ void m7628$r8$lambda$WICQmL3mk2m2tUE77gymTUZ4N8(final int i, final ArrayList arrayList, final long j, final Activity activity, final Theme.ResourcesProvider resourcesProvider, final Utilities.Callback callback, final HashMap map, Boolean bool) {
        if (bool.booleanValue()) {
            SharedPreferences.Editor editorEdit = MessagesController.getInstance(i).getMainSettings().edit();
            int size = arrayList.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                Long l = (Long) obj;
                long jLongValue = l.longValue();
                long sendPaidMessagesStars = MessagesController.getInstance(i).getSendPaidMessagesStars(jLongValue);
                if (sendPaidMessagesStars <= 0 && jLongValue > 0) {
                    sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(i).isUserContactBlocked(jLongValue));
                }
                editorEdit.putLong("ask_paid_message_" + jLongValue + "_price", sendPaidMessagesStars);
                StarsController.getInstance(i).justAgreedToNotAskDialogs.put(l, Long.valueOf(System.currentTimeMillis()));
            }
            editorEdit.apply();
        }
        Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda110
            @Override // java.lang.Runnable
            public final void run() {
                AlertsCreator.m7616$r8$lambda$K1WaR_IX2adzH8H9xO3iPu18Qg(i, j, activity, arrayList, resourcesProvider, callback, map);
            }
        };
        if (!StarsController.getInstance(i).balanceAvailable()) {
            StarsController.getInstance(i).invalidateBalance(runnable);
        } else {
            runnable.run();
        }
    }

    /* renamed from: $r8$lambda$K1WaR_-IX2adzH8H9xO3iPu18Qg, reason: not valid java name */
    public static /* synthetic */ void m7616$r8$lambda$K1WaR_IX2adzH8H9xO3iPu18Qg(int i, long j, Activity activity, ArrayList arrayList, Theme.ResourcesProvider resourcesProvider, final Utilities.Callback callback, final HashMap map) {
        if (StarsController.getInstance(i).getBalance().amount >= j) {
            callback.run(map);
        } else {
            if (activity == null) {
                return;
            }
            long jLongValue = ((Long) arrayList.get(0)).longValue();
            new StarsIntroActivity.StarsNeededSheet(activity, resourcesProvider, j, 13, DialogObject.getShortName(i, jLongValue), new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda134
                @Override // java.lang.Runnable
                public final void run() {
                    callback.run(map);
                }
            }, jLongValue).show();
        }
    }

    public static boolean needsPaidMessageAlert(int i, long j) {
        long sendPaidMessagesStars = MessagesController.getInstance(i).getSendPaidMessagesStars(j);
        if (sendPaidMessagesStars <= 0 && j > 0) {
            sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(i).isUserContactBlocked(j));
        }
        if (sendPaidMessagesStars <= 0) {
            return false;
        }
        SharedPreferences mainSettings = MessagesController.getInstance(i).getMainSettings();
        StringBuilder sb = new StringBuilder();
        sb.append("ask_paid_message_");
        sb.append(j);
        sb.append("_price");
        return sendPaidMessagesStars > mainSettings.getLong(sb.toString(), 0L);
    }

    public static boolean ensurePaidMessageConfirmation(int i, long j, int i2, Utilities.Callback callback) {
        return ensurePaidMessageConfirmation(i, j, i2, callback, 0L);
    }

    public static boolean ensurePaidMessageConfirmation(final int i, final long j, int i2, final Utilities.Callback callback, long j2) {
        if (callback == null) {
            return false;
        }
        long sendPaidMessagesStars = MessagesController.getInstance(i).getSendPaidMessagesStars(j);
        if (sendPaidMessagesStars <= 0 && j > 0) {
            sendPaidMessagesStars = DialogObject.getMessagesStarsPrice(MessagesController.getInstance(i).isUserContactBlocked(j));
        }
        final long j3 = i2 * sendPaidMessagesStars;
        StarsController.getInstance(i).sendingMessagesCount.put(Long.valueOf(j), Integer.valueOf(i2));
        if (j3 <= 0 || j2 == j3) {
            callback.run(Long.valueOf(j3));
            return false;
        }
        final long j4 = sendPaidMessagesStars;
        showPayForMessageAlert(i, j, j4, i2, new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                AlertsCreator.$r8$lambda$Wil3gvLDr031jAT4m_MP71bcxsw(i, j3, j, callback, j4);
            }
        });
        return true;
    }

    public static /* synthetic */ void $r8$lambda$Wil3gvLDr031jAT4m_MP71bcxsw(final int i, final long j, final long j2, final Utilities.Callback callback, final long j3) {
        Runnable runnable = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda44
            @Override // java.lang.Runnable
            public final void run() {
                AlertsCreator.$r8$lambda$2xrbzzJZl8P2oXJP1Ar3j_6Shbc(i, j, j2, callback, j3);
            }
        };
        if (!StarsController.getInstance(i).balanceAvailable()) {
            StarsController.getInstance(i).invalidateBalance(runnable);
        } else {
            runnable.run();
        }
    }

    public static /* synthetic */ void $r8$lambda$2xrbzzJZl8P2oXJP1Ar3j_6Shbc(int i, long j, long j2, final Utilities.Callback callback, final long j3) {
        if (StarsController.getInstance(i).getBalance().amount < j) {
            Activity activity = AndroidUtilities.getActivity();
            BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
            Theme.ResourcesProvider darkThemeResourceProvider = (PhotoViewer.getInstance().isVisible() || (safeLastFragment != null && safeLastFragment.hasShownSheet())) ? new DarkThemeResourceProvider() : safeLastFragment != null ? safeLastFragment.getResourceProvider() : null;
            if (activity == null) {
                return;
            }
            new StarsIntroActivity.StarsNeededSheet(activity, darkThemeResourceProvider, j, 13, DialogObject.getShortName(i, j2), new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda116
                @Override // java.lang.Runnable
                public final void run() {
                    callback.run(Long.valueOf(j3));
                }
            }, j2).show();
            return;
        }
        callback.run(Long.valueOf(j3));
    }

    public static void showPayForMessageAlert(final int i, final long j, final long j2, int i2, final Runnable runnable) {
        TLRPC.Chat chat;
        if (runnable == null) {
            return;
        }
        if (j2 <= MessagesController.getInstance(i).getMainSettings().getLong("ask_paid_message_" + j + "_price", 0L)) {
            runnable.run();
            return;
        }
        Activity activity = AndroidUtilities.getActivity();
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        Theme.ResourcesProvider darkThemeResourceProvider = (PhotoViewer.getInstance().isVisible() || (safeLastFragment != null && safeLastFragment.hasShownSheet())) ? new DarkThemeResourceProvider() : safeLastFragment != null ? safeLastFragment.getResourceProvider() : null;
        String shortName = DialogObject.getShortName(i, j);
        if (ChatObject.isMonoForum(i, j)) {
            shortName = ForumUtilities.getMonoForumTitle(i, j, true);
        } else if (safeLastFragment instanceof ChatActivity) {
            ChatActivity chatActivity = (ChatActivity) safeLastFragment;
            if (chatActivity.isComments && chatActivity.getDialogId() == j && (chat = chatActivity.replyOriginalChat) != null) {
                shortName = DialogObject.getShortName(i, -chat.f1571id);
            }
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int i3 = (int) j2;
        spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessage1", i3, shortName)));
        spannableStringBuilder.append((CharSequence) " ");
        if (i2 == 1) {
            spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessage2One", i3)));
        } else {
            spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessage2Many1", (int) (i2 * j2))));
            spannableStringBuilder.append((CharSequence) " ");
            spannableStringBuilder.append((CharSequence) AndroidUtilities.replaceTags(LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessage2Many2", i2)));
        }
        showAlertWithCheckbox(activity, LocaleController.getString(C2369R.string.MessageLockedStarsConfirmTitle), spannableStringBuilder, LocaleController.getString(C2369R.string.MessageLockedStarsConfirmMessageDontAsk), LocaleController.formatPluralStringComma("MessageLockedStarsConfirmMessagePay", i2), new Utilities.Callback() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda56
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                AlertsCreator.$r8$lambda$LHlIXlSwPkJXNgsv2doRFubvMXY(i, j, j2, runnable, (Boolean) obj);
            }
        }, darkThemeResourceProvider);
    }

    public static /* synthetic */ void $r8$lambda$LHlIXlSwPkJXNgsv2doRFubvMXY(int i, long j, long j2, Runnable runnable, Boolean bool) {
        if (bool.booleanValue()) {
            MessagesController.getInstance(i).getMainSettings().edit().putLong("ask_paid_message_" + j + "_price", j2).apply();
            StarsController.getInstance(i).justAgreedToNotAskDialogs.put(Long.valueOf(j), Long.valueOf(System.currentTimeMillis()));
        }
        AndroidUtilities.runOnUIThread(runnable);
    }

    public static void showAlertWithCheckbox(Context context, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, final Utilities.Callback callback, Theme.ResourcesProvider resourcesProvider) {
        if (context == null) {
            callback.run(Boolean.FALSE);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
        final CheckBoxCell[] checkBoxCellArr = new CheckBoxCell[1];
        final boolean[] zArr = new boolean[1];
        TextView textView = new TextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.9
            @Override // android.widget.TextView
            public void setText(CharSequence charSequence5, TextView.BufferType bufferType) {
                super.setText(Emoji.replaceEmoji(charSequence5, getPaint().getFontMetricsInt(), false), bufferType);
            }
        };
        NotificationCenter.listenEmojiLoading(textView);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
        textView.setTextSize(1, 16.0f);
        textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        textView.setText(charSequence2);
        FrameLayout frameLayout = new FrameLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.10
            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                super.onMeasure(i, i2);
                if (checkBoxCellArr[0] != null) {
                    setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + checkBoxCellArr[0].getMeasuredHeight() + AndroidUtilities.m1146dp(7.0f));
                }
            }
        };
        builder.setCustomViewOffset(6);
        builder.setView(frameLayout);
        TextView textView2 = new TextView(context);
        textView2.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem, resourcesProvider));
        textView2.setTextSize(1, 20.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setLines(1);
        textView2.setMaxLines(1);
        textView2.setSingleLine(true);
        textView2.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        textView2.setEllipsize(TextUtils.TruncateAt.END);
        textView2.setText(charSequence);
        frameLayout.addView(textView2, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 8.0f, 24.0f, 0.0f));
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 48.0f, 24.0f, 6.0f));
        if (!TextUtils.isEmpty(charSequence3)) {
            CheckBoxCell checkBoxCell = new CheckBoxCell(context, 1, resourcesProvider);
            checkBoxCellArr[0] = checkBoxCell;
            checkBoxCell.setBackground(Theme.getSelectorDrawable(false));
            checkBoxCellArr[0].setMultiline(true);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) checkBoxCellArr[0].getCheckBoxView().getLayoutParams();
            layoutParams.topMargin = 0;
            layoutParams.gravity = (LocaleController.isRTL ? 5 : 3) | 16;
            checkBoxCellArr[0].getCheckBoxView().setLayoutParams(layoutParams);
            checkBoxCellArr[0].setText(charSequence3, "", false, false);
            checkBoxCellArr[0].setPadding(LocaleController.isRTL ? AndroidUtilities.m1146dp(16.0f) : AndroidUtilities.m1146dp(8.0f), AndroidUtilities.m1146dp(8.0f), LocaleController.isRTL ? AndroidUtilities.m1146dp(8.0f) : AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(8.0f));
            frameLayout.addView(checkBoxCellArr[0], LayoutHelper.createFrame(-1, -2, 83));
            checkBoxCellArr[0].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda102
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertsCreator.m7651$r8$lambda$vpjEuLJTj5gv_gU_C3nVy_8Xt0(zArr, view);
                }
            });
        }
        builder.setPositiveButton(charSequence4, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda103
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                callback.run(Boolean.valueOf(zArr[0]));
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setShowStarsBalance(true);
        alertDialogCreate.show();
    }

    /* renamed from: $r8$lambda$vpjEuLJTj5gv_gU_-C3nVy_8Xt0, reason: not valid java name */
    public static /* synthetic */ void m7651$r8$lambda$vpjEuLJTj5gv_gU_C3nVy_8Xt0(boolean[] zArr, View view) {
        boolean z = !zArr[0];
        zArr[0] = z;
        ((CheckBoxCell) view).setChecked(z, true);
    }

    public static void createClearOrDeleteDialogAlert(BaseFragment baseFragment, boolean z, TLRPC.Chat chat, TLRPC.User user, boolean z2, boolean z3, MessagesStorage.BooleanCallback booleanCallback) {
        createClearOrDeleteDialogAlert(baseFragment, z, false, false, chat, user, z2, false, z3, booleanCallback, null);
    }

    public static void createClearOrDeleteDialogAlert(BaseFragment baseFragment, boolean z, TLRPC.Chat chat, TLRPC.User user, boolean z2, boolean z3, boolean z4, MessagesStorage.BooleanCallback booleanCallback, Theme.ResourcesProvider resourcesProvider) {
        createClearOrDeleteDialogAlert(baseFragment, z, chat != null && chat.creator, false, chat, user, z2, z3, z4, booleanCallback, resourcesProvider);
    }

    /* JADX WARN: Removed duplicated region for block: B:168:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x03d2  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x03e9  */
    /* JADX WARN: Removed duplicated region for block: B:194:0x0428  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x05d6  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x05dd  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x066f  */
    /* JADX WARN: Removed duplicated region for block: B:279:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void createClearOrDeleteDialogAlert(final org.telegram.p023ui.ActionBar.BaseFragment r31, final boolean r32, final boolean r33, final boolean r34, final org.telegram.tgnet.TLRPC.Chat r35, final org.telegram.tgnet.TLRPC.User r36, final boolean r37, final boolean r38, final boolean r39, final org.telegram.messenger.MessagesStorage.BooleanCallback r40, final org.telegram.ui.ActionBar.Theme.ResourcesProvider r41) {
        /*
            Method dump skipped, instructions count: 1657
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.AlertsCreator.createClearOrDeleteDialogAlert(org.telegram.ui.ActionBar.BaseFragment, boolean, boolean, boolean, org.telegram.tgnet.TLRPC$Chat, org.telegram.tgnet.TLRPC$User, boolean, boolean, boolean, org.telegram.messenger.MessagesStorage$BooleanCallback, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    public static /* synthetic */ void $r8$lambda$9h3199I59VGryGcKOLAF_ufsiFQ(boolean[] zArr, View view) {
        boolean z = !zArr[0];
        zArr[0] = z;
        ((CheckBoxCell) view).setChecked(z, true);
    }

    public static /* synthetic */ void $r8$lambda$7Xajl0Dl5JcqQIqXyktR3uxz9YQ(boolean[] zArr, View view) {
        boolean z = !zArr[0];
        zArr[0] = z;
        ((CheckBoxCell) view).setChecked(z, true);
    }

    /* renamed from: $r8$lambda$_v2lXByN1-MKXRBMA9ickyWYQuE, reason: not valid java name */
    public static /* synthetic */ void m7635$r8$lambda$_v2lXByN1MKXRBMA9ickyWYQuE(boolean z, boolean z2, boolean z3, final TLRPC.User user, final BaseFragment baseFragment, final boolean z4, final boolean z5, final TLRPC.Chat chat, final boolean z6, final boolean z7, final MessagesStorage.BooleanCallback booleanCallback, final Theme.ResourcesProvider resourcesProvider, final boolean[] zArr, AlertDialog alertDialog, int i) {
        if (!z && !z2 && !z3) {
            if (UserObject.isUserSelf(user)) {
                createClearOrDeleteDialogAlert(baseFragment, z4, z5, true, chat, user, false, z6, z7, booleanCallback, resourcesProvider);
                return;
            } else if (user != null && zArr[0]) {
                MessagesStorage.getInstance(baseFragment.getCurrentAccount()).getMessagesCount(user.f1734id, new MessagesStorage.IntCallback() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda127
                    @Override // org.telegram.messenger.MessagesStorage.IntCallback
                    public final void run(int i2) {
                        AlertsCreator.$r8$lambda$PYRgoHgn9v3FWEnEoVIU0Gg5zhQ(baseFragment, z4, z5, chat, user, z6, z7, booleanCallback, resourcesProvider, zArr, i2);
                    }
                });
                return;
            }
        }
        if (booleanCallback != null) {
            booleanCallback.run(z2 || zArr[0]);
        }
    }

    public static /* synthetic */ void $r8$lambda$PYRgoHgn9v3FWEnEoVIU0Gg5zhQ(BaseFragment baseFragment, boolean z, boolean z2, TLRPC.Chat chat, TLRPC.User user, boolean z3, boolean z4, MessagesStorage.BooleanCallback booleanCallback, Theme.ResourcesProvider resourcesProvider, boolean[] zArr, int i) {
        if (i >= 50) {
            createClearOrDeleteDialogAlert(baseFragment, z, z2, true, chat, user, false, z3, z4, booleanCallback, resourcesProvider);
        } else if (booleanCallback != null) {
            booleanCallback.run(zArr[0]);
        }
    }

    public static void createClearOrDeleteDialogsAlert(BaseFragment baseFragment, boolean z, boolean z2, int i, int i2, boolean z3, final MessagesStorage.BooleanCallback booleanCallback, Theme.ResourcesProvider resourcesProvider) {
        CharSequence string;
        int currentAccount = baseFragment.getCurrentAccount();
        Activity parentActivity = baseFragment.getParentActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity, resourcesProvider);
        UserConfig.getInstance(currentAccount).getClientUserId();
        final CheckBoxCell[] checkBoxCellArr = new CheckBoxCell[1];
        final boolean[] zArr = new boolean[1];
        TextView textView = new TextView(parentActivity);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        textView.setTextSize(1, 16.0f);
        textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        FrameLayout frameLayout = new FrameLayout(parentActivity) { // from class: org.telegram.ui.Components.AlertsCreator.13
            @Override // android.widget.FrameLayout, android.view.View
            protected void onMeasure(int i3, int i4) {
                super.onMeasure(i3, i4);
                if (checkBoxCellArr[0] != null) {
                    setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() + checkBoxCellArr[0].getMeasuredHeight() + AndroidUtilities.m1146dp(7.0f));
                }
            }
        };
        builder.setCustomViewOffset(6);
        builder.setView(frameLayout);
        TextView textView2 = new TextView(parentActivity);
        textView2.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
        textView2.setTextSize(1, 20.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setLines(1);
        textView2.setMaxLines(1);
        textView2.setSingleLine(true);
        textView2.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        textView2.setEllipsize(TextUtils.TruncateAt.END);
        if (z2) {
            if (z3) {
                CheckBoxCell checkBoxCell = new CheckBoxCell(parentActivity, 1, resourcesProvider);
                checkBoxCellArr[0] = checkBoxCell;
                checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                checkBoxCellArr[0].setText(LocaleController.getString(C2369R.string.DeleteMessagesForBothSidesWherePossible), "", false, false);
                checkBoxCellArr[0].setPadding(LocaleController.isRTL ? AndroidUtilities.m1146dp(16.0f) : AndroidUtilities.m1146dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.m1146dp(8.0f) : AndroidUtilities.m1146dp(16.0f), 0);
                frameLayout.addView(checkBoxCellArr[0], LayoutHelper.createFrame(-1, 48.0f, 83, 0.0f, 0.0f, 0.0f, 0.0f));
                checkBoxCellArr[0].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda104
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AlertsCreator.m7611$r8$lambda$EsTNz06G0LY_lkaEdmX9UzMejk(zArr, view);
                    }
                });
            }
            textView2.setText(LocaleController.formatString("DeleteFewChatsTitle", C2369R.string.DeleteFewChatsTitle, LocaleController.formatPluralString("ChatsSelected", i2, new Object[0])));
            textView.setText(LocaleController.getString("AreYouSureDeleteFewChats", C2369R.string.AreYouSureDeleteFewChats));
        } else if (i != 0) {
            textView2.setText(LocaleController.formatString("ClearCacheFewChatsTitle", C2369R.string.ClearCacheFewChatsTitle, LocaleController.formatPluralString("ChatsSelectedClearCache", i2, new Object[0])));
            textView.setText(LocaleController.getString("AreYouSureClearHistoryCacheFewChats", C2369R.string.AreYouSureClearHistoryCacheFewChats));
        } else {
            textView2.setText(LocaleController.formatString("ClearFewChatsTitle", C2369R.string.ClearFewChatsTitle, LocaleController.formatPluralString("ChatsSelectedClear", i2, new Object[0])));
            textView.setText(LocaleController.getString("AreYouSureClearHistoryFewChats", C2369R.string.AreYouSureClearHistoryFewChats));
        }
        frameLayout.addView(textView2, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 11.0f, 24.0f, 0.0f));
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 57.0f, 24.0f, 1.0f));
        if (z2) {
            string = LocaleController.getString("Delete", C2369R.string.Delete);
        } else if (i != 0) {
            string = LocaleController.getString("ClearHistoryCache", C2369R.string.ClearHistoryCache);
        } else {
            string = LocaleController.getString("ClearHistory", C2369R.string.ClearHistory);
        }
        builder.setPositiveButton(string, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda105
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i3) {
                AlertsCreator.$r8$lambda$IBgOxCikOjdJ1y8Q_E1tOFcQ7GQ(booleanCallback, zArr, alertDialog, i3);
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", C2369R.string.Cancel), null);
        AlertDialog alertDialogCreate = builder.create();
        baseFragment.showDialog(alertDialogCreate);
        TextView textView3 = (TextView) alertDialogCreate.getButton(-1);
        if (textView3 != null) {
            textView3.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
    }

    /* renamed from: $r8$lambda$EsTNz0-6G0LY_lkaEdmX9UzMejk, reason: not valid java name */
    public static /* synthetic */ void m7611$r8$lambda$EsTNz06G0LY_lkaEdmX9UzMejk(boolean[] zArr, View view) {
        boolean z = !zArr[0];
        zArr[0] = z;
        ((CheckBoxCell) view).setChecked(z, true);
    }

    public static /* synthetic */ void $r8$lambda$IBgOxCikOjdJ1y8Q_E1tOFcQ7GQ(MessagesStorage.BooleanCallback booleanCallback, boolean[] zArr, AlertDialog alertDialog, int i) {
        if (booleanCallback != null) {
            booleanCallback.run(zArr[0]);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0197  */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v42 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void createClearDaysDialogAlert(org.telegram.p023ui.ActionBar.BaseFragment r26, int r27, org.telegram.tgnet.TLRPC.User r28, org.telegram.tgnet.TLRPC.Chat r29, boolean r30, final org.telegram.messenger.MessagesStorage.BooleanCallback r31, org.telegram.ui.ActionBar.Theme.ResourcesProvider r32) {
        /*
            Method dump skipped, instructions count: 608
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.AlertsCreator.createClearDaysDialogAlert(org.telegram.ui.ActionBar.BaseFragment, int, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Chat, boolean, org.telegram.messenger.MessagesStorage$BooleanCallback, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    public static /* synthetic */ void $r8$lambda$tbp4zkeaXrEelRhY3gqVvnjupxM(boolean[] zArr, View view) {
        boolean z = !zArr[0];
        zArr[0] = z;
        ((CheckBoxCell) view).setChecked(z, true);
    }

    public static void createCallDialogAlert(final BaseFragment baseFragment, final TLRPC.User user, final boolean z) {
        String string;
        String string2;
        if (baseFragment == null || baseFragment.getParentActivity() == null || user == null || UserObject.isDeleted(user) || UserConfig.getInstance(baseFragment.getCurrentAccount()).getClientUserId() == user.f1734id) {
            return;
        }
        baseFragment.getCurrentAccount();
        Activity parentActivity = baseFragment.getParentActivity();
        FrameLayout frameLayout = new FrameLayout(parentActivity);
        if (z) {
            string = LocaleController.getString(C2369R.string.VideoCallAlertTitle);
            string2 = LocaleController.formatString("VideoCallAlert", C2369R.string.VideoCallAlert, UserObject.getUserName(user));
        } else {
            string = LocaleController.getString(C2369R.string.CallAlertTitle);
            string2 = LocaleController.formatString("CallAlert", C2369R.string.CallAlert, UserObject.getUserName(user));
        }
        TextView textView = new TextView(parentActivity) { // from class: org.telegram.ui.Components.AlertsCreator.16
            @Override // android.widget.TextView
            public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
                super.setText(Emoji.replaceEmoji(charSequence, getPaint().getFontMetricsInt(), false), bufferType);
            }
        };
        NotificationCenter.listenEmojiLoading(textView);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        textView.setTextSize(1, 16.0f);
        textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        textView.setText(AndroidUtilities.replaceTags(string2));
        AvatarDrawable avatarDrawable = new AvatarDrawable();
        avatarDrawable.setTextSize(AndroidUtilities.m1146dp(12.0f));
        avatarDrawable.setScaleSize(1.0f);
        avatarDrawable.setInfo(baseFragment.getCurrentAccount(), user);
        BackupImageView backupImageView = new BackupImageView(parentActivity);
        backupImageView.setRoundRadius(ExteraConfig.getAvatarCorners(40.0f));
        backupImageView.setForUserOrChat(user, avatarDrawable);
        frameLayout.addView(backupImageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, 22.0f, 5.0f, 22.0f, 0.0f));
        TextView textView2 = new TextView(parentActivity);
        textView2.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
        textView2.setTextSize(1, 20.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setLines(1);
        textView2.setMaxLines(1);
        textView2.setSingleLine(true);
        textView2.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        textView2.setEllipsize(TextUtils.TruncateAt.END);
        textView2.setText(string);
        boolean z2 = LocaleController.isRTL;
        frameLayout.addView(textView2, LayoutHelper.createFrame(-1, -2.0f, (z2 ? 5 : 3) | 48, z2 ? 21 : 76, 11.0f, z2 ? 76 : 21, 0.0f));
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, 24.0f, 57.0f, 24.0f, 9.0f));
        baseFragment.showDialog(new AlertDialog.Builder(parentActivity).setView(frameLayout).setPositiveButton(LocaleController.getString(C2369R.string.Call), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda12
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.$r8$lambda$LiVkrQgm5Z2A2P7wWGQPIIe0zZw(baseFragment, user, z, alertDialog, i);
            }
        }).setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null).create());
    }

    public static /* synthetic */ void $r8$lambda$LiVkrQgm5Z2A2P7wWGQPIIe0zZw(BaseFragment baseFragment, TLRPC.User user, boolean z, AlertDialog alertDialog, int i) {
        TLRPC.UserFull userFull = baseFragment.getMessagesController().getUserFull(user.f1734id);
        VoIPHelper.startCall(user, z, userFull != null && userFull.video_calls_available, baseFragment.getParentActivity(), userFull, baseFragment.getAccountInstance(), true);
    }

    public static void createChangeBioAlert(String str, final long j, Context context, final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(LocaleController.getString(j > 0 ? C2369R.string.UserBio : C2369R.string.DescriptionPlaceholder));
        builder.setMessage(LocaleController.getString(j > 0 ? C2369R.string.VoipGroupBioEditAlertText : C2369R.string.DescriptionInfo));
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setClipChildren(false);
        if (j < 0) {
            long j2 = -j;
            if (MessagesController.getInstance(i).getChatFull(j2) == null) {
                MessagesController.getInstance(i).loadFullChat(j2, ConnectionsManager.generateClassGuid(), true);
            }
        }
        final NumberTextView numberTextView = new NumberTextView(context);
        final EditText editText = new EditText(context);
        int i2 = Theme.key_voipgroup_actionBarItems;
        editText.setTextColor(Theme.getColor(i2));
        editText.setHint(LocaleController.getString(j > 0 ? C2369R.string.UserBio : C2369R.string.DescriptionPlaceholder));
        editText.setTextSize(1, 16.0f);
        editText.setBackground(Theme.createEditTextDrawable(context, true));
        editText.setMaxLines(4);
        editText.setRawInputType(147457);
        editText.setImeOptions(6);
        final int i3 = j > 0 ? 70 : 255;
        editText.setFilters(new InputFilter[]{new CodepointsLengthInputFilter(i3) { // from class: org.telegram.ui.Components.AlertsCreator.17
            @Override // org.telegram.p023ui.Components.CodepointsLengthInputFilter, android.text.InputFilter
            public CharSequence filter(CharSequence charSequence, int i4, int i5, Spanned spanned, int i6, int i7) {
                CharSequence charSequenceFilter = super.filter(charSequence, i4, i5, spanned, i6, i7);
                if (charSequenceFilter != null && charSequence != null && charSequenceFilter.length() != charSequence.length()) {
                    VibratorUtils.vibrate();
                    AndroidUtilities.shakeView(numberTextView);
                }
                return charSequenceFilter;
            }
        }});
        numberTextView.setCenterAlign(true);
        numberTextView.setTextSize(15);
        numberTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
        numberTextView.setImportantForAccessibility(2);
        frameLayout.addView(numberTextView, LayoutHelper.createFrame(20, 20.0f, LocaleController.isRTL ? 3 : 5, 0.0f, 14.0f, 21.0f, 0.0f));
        editText.setPadding(LocaleController.isRTL ? AndroidUtilities.m1146dp(24.0f) : 0, AndroidUtilities.m1146dp(8.0f), LocaleController.isRTL ? 0 : AndroidUtilities.m1146dp(24.0f), AndroidUtilities.m1146dp(8.0f));
        editText.addTextChangedListener(new TextWatcher() { // from class: org.telegram.ui.Components.AlertsCreator.18
            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i4, int i5, int i6) {
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                int iCodePointCount = i3 - Character.codePointCount(editable, 0, editable.length());
                if (iCodePointCount < 30) {
                    NumberTextView numberTextView2 = numberTextView;
                    numberTextView2.setNumber(iCodePointCount, numberTextView2.getVisibility() == 0);
                    AndroidUtilities.updateViewVisibilityAnimated(numberTextView, true);
                    return;
                }
                AndroidUtilities.updateViewVisibilityAnimated(numberTextView, false);
            }
        });
        AndroidUtilities.updateViewVisibilityAnimated(numberTextView, false, 0.0f, false);
        editText.setText(str);
        editText.setSelection(editText.getText().toString().length());
        builder.setView(frameLayout);
        final AlertDialog.OnButtonClickListener onButtonClickListener = new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda159
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i4) {
                AlertsCreator.m7647$r8$lambda$m4Wu84YmfW0F5Yr3iPZo8V8d4M(j, i, editText, alertDialog, i4);
            }
        };
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Save), onButtonClickListener);
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        builder.setOnPreDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda160
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AndroidUtilities.hideKeyboard(editText);
            }
        });
        frameLayout.addView(editText, LayoutHelper.createFrame(-1, -2.0f, 0, 23.0f, 12.0f, 23.0f, 21.0f));
        editText.requestFocus();
        AndroidUtilities.showKeyboard(editText);
        final AlertDialog alertDialogCreate = builder.create();
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda161
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i4, KeyEvent keyEvent) {
                return AlertsCreator.$r8$lambda$6WdAWceshHHkD6IowyoI8HyNPmk(j, alertDialogCreate, onButtonClickListener, textView, i4, keyEvent);
            }
        });
        alertDialogCreate.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_dialogBackground));
        alertDialogCreate.show();
        alertDialogCreate.setTextColor(Theme.getColor(i2));
    }

    /* renamed from: $r8$lambda$m4Wu8-4YmfW0F5Yr3iPZo8V8d4M, reason: not valid java name */
    public static /* synthetic */ void m7647$r8$lambda$m4Wu84YmfW0F5Yr3iPZo8V8d4M(long j, int i, EditText editText, AlertDialog alertDialog, int i2) {
        if (j > 0) {
            TLRPC.UserFull userFull = MessagesController.getInstance(i).getUserFull(UserConfig.getInstance(i).getClientUserId());
            String strTrim = editText.getText().toString().replace("\n", " ").replaceAll(" +", " ").trim();
            if (userFull != null) {
                String str = userFull.about;
                if ((str != null ? str : "").equals(strTrim)) {
                    AndroidUtilities.hideKeyboard(editText);
                    alertDialog.dismiss();
                    return;
                } else {
                    userFull.about = strTrim;
                    NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.userInfoDidLoad, Long.valueOf(j), userFull);
                }
            }
            TL_account.updateProfile updateprofile = new TL_account.updateProfile();
            updateprofile.about = strTrim;
            updateprofile.flags |= 4;
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.showBulletin, 2, Long.valueOf(j));
            ConnectionsManager.getInstance(i).sendRequest(updateprofile, new RequestDelegate() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda206
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    AlertsCreator.$r8$lambda$tKLkyMP9famLGN2Ib107AG8efGc(tLObject, tL_error);
                }
            }, 2);
        } else {
            long j2 = -j;
            TLRPC.ChatFull chatFull = MessagesController.getInstance(i).getChatFull(j2);
            String string = editText.getText().toString();
            if (chatFull != null) {
                String str2 = chatFull.about;
                if ((str2 != null ? str2 : "").equals(string)) {
                    AndroidUtilities.hideKeyboard(editText);
                    alertDialog.dismiss();
                    return;
                } else {
                    chatFull.about = string;
                    NotificationCenter notificationCenter = NotificationCenter.getInstance(i);
                    int i3 = NotificationCenter.chatInfoDidLoad;
                    Boolean bool = Boolean.FALSE;
                    notificationCenter.lambda$postNotificationNameOnUIThread$1(i3, chatFull, 0, bool, bool);
                }
            }
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.showBulletin, 2, Long.valueOf(j));
            MessagesController.getInstance(i).updateChatAbout(j2, string, chatFull);
        }
        alertDialog.dismiss();
    }

    public static /* synthetic */ boolean $r8$lambda$6WdAWceshHHkD6IowyoI8HyNPmk(long j, AlertDialog alertDialog, AlertDialog.OnButtonClickListener onButtonClickListener, TextView textView, int i, KeyEvent keyEvent) {
        if ((i != 6 && (j <= 0 || keyEvent.getKeyCode() != 66)) || !alertDialog.isShowing()) {
            return false;
        }
        onButtonClickListener.onClick(alertDialog, 0);
        return true;
    }

    public static void createChangeNameAlert(final long j, Context context, final int i) {
        String str;
        String str2;
        final EditText editText;
        if (DialogObject.isUserDialog(j)) {
            TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(j));
            str = user.first_name;
            str2 = user.last_name;
        } else {
            str = MessagesController.getInstance(i).getChat(Long.valueOf(-j)).title;
            str2 = null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(LocaleController.getString(j > 0 ? C2369R.string.VoipEditName : C2369R.string.VoipEditTitle));
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        final EditText editText2 = new EditText(context);
        int i2 = Theme.key_voipgroup_actionBarItems;
        editText2.setTextColor(Theme.getColor(i2));
        editText2.setTextSize(1, 16.0f);
        editText2.setMaxLines(1);
        editText2.setLines(1);
        editText2.setSingleLine(true);
        editText2.setGravity(LocaleController.isRTL ? 5 : 3);
        editText2.setInputType(49152);
        editText2.setImeOptions(j > 0 ? 5 : 6);
        editText2.setHint(LocaleController.getString(j > 0 ? C2369R.string.FirstName : C2369R.string.VoipEditTitleHint));
        editText2.setBackground(Theme.createEditTextDrawable(context, true));
        editText2.setPadding(0, AndroidUtilities.m1146dp(8.0f), 0, AndroidUtilities.m1146dp(8.0f));
        editText2.requestFocus();
        if (j > 0) {
            editText = new EditText(context);
            editText.setTextColor(Theme.getColor(i2));
            editText.setTextSize(1, 16.0f);
            editText.setMaxLines(1);
            editText.setLines(1);
            editText.setSingleLine(true);
            editText.setGravity(LocaleController.isRTL ? 5 : 3);
            editText.setInputType(49152);
            editText.setImeOptions(6);
            editText.setHint(LocaleController.getString(C2369R.string.LastName));
            editText.setBackground(Theme.createEditTextDrawable(context, true));
            editText.setPadding(0, AndroidUtilities.m1146dp(8.0f), 0, AndroidUtilities.m1146dp(8.0f));
        } else {
            editText = null;
        }
        AndroidUtilities.showKeyboard(editText2);
        linearLayout.addView(editText2, LayoutHelper.createLinear(-1, -2, 0, 23, 12, 23, 21));
        if (editText != null) {
            linearLayout.addView(editText, LayoutHelper.createLinear(-1, -2, 0, 23, 12, 23, 21));
        }
        editText2.setText(str);
        editText2.setSelection(editText2.getText().toString().length());
        if (editText != null) {
            editText.setText(str2);
            editText.setSelection(editText.getText().toString().length());
        }
        builder.setView(linearLayout);
        final AlertDialog.OnButtonClickListener onButtonClickListener = new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda154
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i3) {
                AlertsCreator.$r8$lambda$dFj0JlQJGXNk9SNnEijFxggDuo0(editText2, j, i, editText, alertDialog, i3);
            }
        };
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Save), onButtonClickListener);
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        builder.setOnPreDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda155
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AlertsCreator.$r8$lambda$Uf4U4pJgdMNS1rFS8gR0KG0MEWs(editText2, editText, dialogInterface);
            }
        });
        final AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setBackgroundColor(Theme.getColor(Theme.key_voipgroup_dialogBackground));
        alertDialogCreate.show();
        alertDialogCreate.setTextColor(Theme.getColor(i2));
        TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda156
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i3, KeyEvent keyEvent) {
                return AlertsCreator.m7613$r8$lambda$GDj3dy8x7FioMqB7UMfCZUTeHk(alertDialogCreate, onButtonClickListener, textView, i3, keyEvent);
            }
        };
        if (editText != null) {
            editText.setOnEditorActionListener(onEditorActionListener);
        } else {
            editText2.setOnEditorActionListener(onEditorActionListener);
        }
    }

    public static /* synthetic */ void $r8$lambda$dFj0JlQJGXNk9SNnEijFxggDuo0(EditText editText, long j, int i, EditText editText2, AlertDialog alertDialog, int i2) {
        if (editText.getText() == null) {
            return;
        }
        if (j > 0) {
            TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(j));
            String string = editText.getText().toString();
            String string2 = editText2.getText().toString();
            String str = user.first_name;
            String str2 = user.last_name;
            if (str == null) {
                str = "";
            }
            if (str2 == null) {
                str2 = "";
            }
            if (str.equals(string) && str2.equals(string2)) {
                alertDialog.dismiss();
                return;
            }
            TL_account.updateProfile updateprofile = new TL_account.updateProfile();
            updateprofile.flags = 3;
            updateprofile.first_name = string;
            user.first_name = string;
            updateprofile.last_name = string2;
            user.last_name = string2;
            TLRPC.User user2 = MessagesController.getInstance(i).getUser(Long.valueOf(UserConfig.getInstance(i).getClientUserId()));
            if (user2 != null) {
                user2.first_name = updateprofile.first_name;
                user2.last_name = updateprofile.last_name;
            }
            UserConfig.getInstance(i).saveConfig(true);
            NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.mainUserInfoChanged, new Object[0]);
            NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_NAME));
            ConnectionsManager.getInstance(i).sendRequest(updateprofile, new RequestDelegate() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda198
                @Override // org.telegram.tgnet.RequestDelegate
                public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                    AlertsCreator.$r8$lambda$mngfP3eB1O7qzEiRRbjMYngSF3w(tLObject, tL_error);
                }
            });
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.showBulletin, 3, Long.valueOf(j));
        } else {
            long j2 = -j;
            TLRPC.Chat chat = MessagesController.getInstance(i).getChat(Long.valueOf(j2));
            String string3 = editText.getText().toString();
            String str3 = chat.title;
            if (str3 != null && str3.equals(string3)) {
                alertDialog.dismiss();
                return;
            }
            chat.title = string3;
            NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(NotificationCenter.updateInterfaces, Integer.valueOf(MessagesController.UPDATE_MASK_CHAT_NAME));
            MessagesController.getInstance(i).changeChatTitle(j2, string3);
            NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.showBulletin, 3, Long.valueOf(j));
        }
        alertDialog.dismiss();
    }

    public static /* synthetic */ void $r8$lambda$Uf4U4pJgdMNS1rFS8gR0KG0MEWs(EditText editText, EditText editText2, DialogInterface dialogInterface) {
        AndroidUtilities.hideKeyboard(editText);
        AndroidUtilities.hideKeyboard(editText2);
    }

    /* renamed from: $r8$lambda$GDj3dy8x7Fi-oMqB7UMfCZUTeHk, reason: not valid java name */
    public static /* synthetic */ boolean m7613$r8$lambda$GDj3dy8x7FioMqB7UMfCZUTeHk(AlertDialog alertDialog, AlertDialog.OnButtonClickListener onButtonClickListener, TextView textView, int i, KeyEvent keyEvent) {
        if ((i != 6 && keyEvent.getKeyCode() != 66) || !alertDialog.isShowing()) {
            return false;
        }
        onButtonClickListener.onClick(alertDialog, 0);
        return true;
    }

    public static void showChatWithAdmin(BaseFragment baseFragment, TLRPC.User user, String str, boolean z, int i) {
        if (baseFragment.getParentActivity() == null) {
            return;
        }
        BottomSheet.Builder builder = new BottomSheet.Builder(baseFragment.getParentActivity());
        builder.setTitle(LocaleController.getString(z ? C2369R.string.ChatWithAdminChannelTitle : C2369R.string.ChatWithAdminGroupTitle), true);
        LinearLayout linearLayout = new LinearLayout(baseFragment.getParentActivity());
        linearLayout.setOrientation(1);
        TextView textView = new TextView(baseFragment.getParentActivity());
        linearLayout.addView(textView, LayoutHelper.createLinear(-1, -1, 0, 21, 0, 21, 8));
        textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        textView.setTextSize(1, 16.0f);
        textView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("ChatWithAdminMessage", C2369R.string.ChatWithAdminMessage, str, LocaleController.formatDateAudio(i, false))));
        TextView textView2 = new TextView(baseFragment.getParentActivity());
        textView2.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView2.setGravity(17);
        textView2.setTextSize(1, 14.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setText(LocaleController.getString(C2369R.string.IUnderstand));
        textView2.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
        textView2.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(8.0f), Theme.getColor(Theme.key_featuredStickers_addButton), Theme.getColor(Theme.key_featuredStickers_addButtonPressed)));
        linearLayout.addView(textView2, LayoutHelper.createLinear(-1, 48, 0, 16, 12, 16, 8));
        builder.setCustomView(linearLayout);
        final BottomSheet bottomSheetShow = builder.show();
        textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda43
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                bottomSheetShow.lambda$new$0();
            }
        });
    }

    public static void createContactInviteDialog(final BaseFragment baseFragment, String str, String str2, final String str3) {
        AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity());
        builder.setTitle(LocaleController.getString(C2369R.string.ContactNotRegisteredTitle));
        builder.setMessage(LocaleController.formatString("ContactNotRegistered", C2369R.string.ContactNotRegistered, ContactsController.formatName(str, str2)));
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Invite), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda148
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.m7634$r8$lambda$_4DKcLBW55yLxNhLIOAFlLREyg(str3, baseFragment, alertDialog, i);
            }
        });
        baseFragment.showDialog(builder.create());
    }

    /* renamed from: $r8$lambda$_4DKcLBW55yLxNhL-IOAFlLREyg, reason: not valid java name */
    public static /* synthetic */ void m7634$r8$lambda$_4DKcLBW55yLxNhLIOAFlLREyg(String str, BaseFragment baseFragment, AlertDialog alertDialog, int i) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.fromParts("sms", str, null));
            intent.putExtra("sms_body", ContactsController.getInstance(baseFragment.getCurrentAccount()).getInviteText(1));
            baseFragment.getParentActivity().startActivityForResult(intent, 500);
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    public static ActionBarPopupWindow createSimplePopup(BaseFragment baseFragment, View view, View view2, float f, float f2) {
        if (baseFragment == null || view2 == null || view == null) {
            return null;
        }
        ActionBarPopupWindow actionBarPopupWindow = new ActionBarPopupWindow(view, -2, -2);
        actionBarPopupWindow.setPauseNotifications(true);
        actionBarPopupWindow.setDismissAnimationDuration(220);
        actionBarPopupWindow.setOutsideTouchable(true);
        actionBarPopupWindow.setClippingEnabled(true);
        actionBarPopupWindow.setAnimationStyle(C2369R.style.PopupContextAnimation);
        actionBarPopupWindow.setFocusable(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(1000.0f), TLObject.FLAG_31), View.MeasureSpec.makeMeasureSpec(AndroidUtilities.m1146dp(1000.0f), TLObject.FLAG_31));
        actionBarPopupWindow.setInputMethodMode(2);
        actionBarPopupWindow.getContentView().setFocusableInTouchMode(true);
        float x = 0.0f;
        View view3 = view2;
        float y = 0.0f;
        while (view3 != view2.getRootView()) {
            x += view3.getX();
            y += view3.getY();
            view3 = (View) view3.getParent();
            if (view3 == null) {
                break;
            }
        }
        actionBarPopupWindow.showAtLocation(view2.getRootView(), 0, (int) ((x + f) - (view.getMeasuredWidth() / 2.0f)), (int) ((y + f2) - (view.getMeasuredHeight() / 2.0f)));
        actionBarPopupWindow.dimBehind();
        return actionBarPopupWindow;
    }

    public static void checkRestrictedInviteUsers(final int i, final TLRPC.Chat chat, TLRPC.TL_messages_invitedUsers tL_messages_invitedUsers) {
        TLRPC.User user;
        if (tL_messages_invitedUsers == null || tL_messages_invitedUsers.missing_invitees.isEmpty() || chat == null) {
            return;
        }
        final ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = tL_messages_invitedUsers.missing_invitees;
        int size = arrayList4.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList4.get(i2);
            i2++;
            TLRPC.TL_missingInvitee tL_missingInvitee = (TLRPC.TL_missingInvitee) obj;
            if (tL_messages_invitedUsers.updates != null) {
                for (int i3 = 0; i3 < tL_messages_invitedUsers.updates.users.size(); i3++) {
                    user = tL_messages_invitedUsers.updates.users.get(i3);
                    if (user.f1734id == tL_missingInvitee.user_id) {
                        break;
                    }
                }
                user = null;
            } else {
                user = null;
            }
            if (user == null) {
                user = MessagesController.getInstance(i).getUser(Long.valueOf(tL_missingInvitee.user_id));
            }
            if (user != null) {
                arrayList.add(user);
                if (tL_missingInvitee.premium_required_for_pm) {
                    arrayList2.add(Long.valueOf(user.f1734id));
                }
                if (tL_missingInvitee.premium_would_allow_invite) {
                    arrayList3.add(Long.valueOf(user.f1734id));
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                AlertsCreator.$r8$lambda$DCgf9hO9e6WvYD3PmrMXrMAiPKY(i, chat, arrayList, arrayList2, arrayList3);
            }
        }, 200L);
    }

    public static /* synthetic */ void $r8$lambda$DCgf9hO9e6WvYD3PmrMXrMAiPKY(int i, TLRPC.Chat chat, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3) {
        BaseFragment lastFragment;
        if (!LaunchActivity.isActive || (lastFragment = LaunchActivity.getLastFragment()) == null || lastFragment.getParentActivity() == null) {
            return;
        }
        LimitReachedBottomSheet limitReachedBottomSheet = new LimitReachedBottomSheet(lastFragment, lastFragment.getParentActivity(), 11, i, null);
        limitReachedBottomSheet.setRestrictedUsers(chat, arrayList, arrayList2, arrayList3, null);
        limitReachedBottomSheet.show();
    }

    public static void createBlockDialogAlert(BaseFragment baseFragment, int i, boolean z, TLRPC.User user, final BlockDialogCallback blockDialogCallback) {
        String string;
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        if (i == 1 && user == null) {
            return;
        }
        Activity parentActivity = baseFragment.getParentActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        CheckBoxCell[] checkBoxCellArr = new CheckBoxCell[2];
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        builder.setView(linearLayout);
        if (i != 1) {
            builder.setTitle(LocaleController.formatString("BlockUserTitle", C2369R.string.BlockUserTitle, LocaleController.formatPluralString("UsersCountTitle", i, new Object[0])));
            string = LocaleController.getString(C2369R.string.BlockUsers);
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("BlockUsersMessage", C2369R.string.BlockUsersMessage, LocaleController.formatPluralString("UsersCount", i, new Object[0]))));
        } else {
            String name = ContactsController.formatName(user.first_name, user.last_name);
            builder.setTitle(LocaleController.formatString("BlockUserTitle", C2369R.string.BlockUserTitle, name));
            string = LocaleController.getString(C2369R.string.BlockUser);
            builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("BlockUserMessage", C2369R.string.BlockUserMessage, name)));
        }
        final boolean[] zArr = {true, true};
        for (final int i2 = 0; i2 < 2; i2++) {
            if (i2 != 0 || z) {
                CheckBoxCell checkBoxCell = new CheckBoxCell(parentActivity, 1);
                checkBoxCellArr[i2] = checkBoxCell;
                checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                if (i2 == 0) {
                    checkBoxCellArr[i2].setText(LocaleController.getString(C2369R.string.ReportSpamTitle), "", true, false);
                } else {
                    checkBoxCellArr[i2].setText(LocaleController.getString(i == 1 ? C2369R.string.DeleteThisChatBothSides : C2369R.string.DeleteTheseChatsBothSides), "", true, false);
                }
                checkBoxCellArr[i2].setPadding(LocaleController.isRTL ? AndroidUtilities.m1146dp(16.0f) : AndroidUtilities.m1146dp(8.0f), 0, LocaleController.isRTL ? AndroidUtilities.m1146dp(8.0f) : AndroidUtilities.m1146dp(16.0f), 0);
                linearLayout.addView(checkBoxCellArr[i2], LayoutHelper.createLinear(-1, 48));
                checkBoxCellArr[i2].setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda117
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AlertsCreator.$r8$lambda$NcVkjyGqcjiHcFX5s0kaSzdiGb8(zArr, i2, view);
                    }
                });
            }
        }
        builder.setPositiveButton(string, new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda118
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i3) {
                AlertsCreator.BlockDialogCallback blockDialogCallback2 = blockDialogCallback;
                boolean[] zArr2 = zArr;
                blockDialogCallback2.run(zArr2[0], zArr2[1]);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        AlertDialog alertDialogCreate = builder.create();
        baseFragment.showDialog(alertDialogCreate);
        TextView textView = (TextView) alertDialogCreate.getButton(-1);
        if (textView != null) {
            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
    }

    public static /* synthetic */ void $r8$lambda$NcVkjyGqcjiHcFX5s0kaSzdiGb8(boolean[] zArr, int i, View view) {
        boolean z = !zArr[i];
        zArr[i] = z;
        ((CheckBoxCell) view).setChecked(z, true);
    }

    public static BottomSheet createTimePickerDialog(Context context, String str, final int i, final int i2, final int i3, final Utilities.Callback callback) {
        if (context == null) {
            return null;
        }
        ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors();
        BottomSheet.Builder builder = new BottomSheet.Builder(context, false, null);
        builder.setApplyBottomPadding(false);
        final NumberPicker numberPicker = new NumberPicker(context) { // from class: org.telegram.ui.Components.AlertsCreator.21
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i4) {
                return LocaleController.formatPluralString("Hours", i4, new Object[0]);
            }
        };
        final LinearLayout linearLayout = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.22
            private Text ampmText;
            private boolean isAM;
            private final Text separatorText = new Text(":", 18.0f);

            @Override // android.view.ViewGroup, android.view.View
            protected void dispatchDraw(Canvas canvas) {
                int i4 = Theme.key_windowBackgroundWhiteBlackText;
                this.separatorText.draw(canvas, (getWidth() - this.separatorText.getCurrentWidth()) / 2.0f, getHeight() / 2.0f, Theme.getColor(i4), 1.0f);
                if (!LocaleController.is24HourFormat) {
                    boolean z = numberPicker.getValue() % 24 < 12;
                    if (this.isAM != z || this.ampmText == null) {
                        this.isAM = z;
                        this.ampmText = new Text(z ? "AM" : "PM", 18.0f);
                    }
                    this.ampmText.draw(canvas, (getWidth() / 2.0f) + AndroidUtilities.m1146dp(43.0f), (getHeight() / 2.0f) + AndroidUtilities.m1146dp(1.0f), Theme.getColor(i4), 1.0f);
                }
                super.dispatchDraw(canvas);
            }
        };
        linearLayout.setOrientation(0);
        linearLayout.setWeightSum(1.0f);
        numberPicker.setAllItemsCount(24);
        numberPicker.setItemCount(5);
        numberPicker.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker.setGravity(5);
        numberPicker.setTextOffset(-AndroidUtilities.m1146dp(12.0f));
        final NumberPicker numberPicker2 = new NumberPicker(context) { // from class: org.telegram.ui.Components.AlertsCreator.23
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i4) {
                return LocaleController.formatPluralString("Minutes", i4, new Object[0]);
            }
        };
        numberPicker2.setWrapSelectorWheel(true);
        numberPicker2.setAllItemsCount(60);
        numberPicker2.setItemCount(5);
        numberPicker2.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker2.setGravity(3);
        numberPicker2.setTextOffset(AndroidUtilities.m1146dp(12.0f));
        final Utilities.Callback callback2 = new Utilities.Callback() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda207
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                AlertsCreator.$r8$lambda$FBg0ha9BknK2iD5CDosaEE8gd40(i2, i3, numberPicker, numberPicker2, i, linearLayout, (Boolean) obj);
            }
        };
        linearLayout.addView(numberPicker, LayoutHelper.createLinear(0, 270, 0.5f));
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda208
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i4) {
                return AlertsCreator.$r8$lambda$CKSgp_q2qhFI8MlDTmmY1FiYYqc(i4);
            }
        });
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda209
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker3, int i4, int i5) {
                callback2.run(Boolean.TRUE);
            }
        });
        linearLayout.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.5f));
        numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda210
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i4) {
                return String.format("%02d", Integer.valueOf(i4));
            }
        });
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda211
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker3, int i4, int i5) {
                callback2.run(Boolean.TRUE);
            }
        });
        callback2.run(Boolean.FALSE);
        LinearLayout linearLayout2 = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.24
            boolean ignoreLayout = false;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i4, int i5) {
                this.ignoreLayout = true;
                Point point = AndroidUtilities.displaySize;
                int i6 = point.x > point.y ? 3 : 5;
                numberPicker.setItemCount(i6);
                numberPicker2.setItemCount(i6);
                numberPicker.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i6;
                numberPicker2.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i6;
                this.ignoreLayout = false;
                super.onMeasure(i4, i5);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        linearLayout2.setOrientation(1);
        FrameLayout frameLayout = new FrameLayout(context);
        TextView textView = new TextView(context);
        textView.setText(str);
        textView.setTextColor(scheduleDatePickerColors.textColor);
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda212
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.$r8$lambda$62RpUknQw_ftOFKzMPb6aLQQ80s(view, motionEvent);
            }
        });
        linearLayout2.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
        linearLayout2.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
        ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context, null);
        buttonWithCounterView.setText(LocaleController.getString(C2369R.string.Select), false);
        buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda213
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                bottomSheetArr[0].lambda$new$0();
            }
        });
        linearLayout2.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 0, 16, 12, 16, 12));
        builder.setCustomView(linearLayout2);
        BottomSheet bottomSheetShow = builder.show();
        bottomSheetShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda214
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                callback.run(Integer.valueOf((numberPicker.getValue() * 60) + numberPicker2.getValue()));
            }
        });
        bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
        bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
        BottomSheet bottomSheetCreate = builder.create();
        final BottomSheet[] bottomSheetArr = {bottomSheetCreate};
        return bottomSheetCreate;
    }

    public static /* synthetic */ void $r8$lambda$FBg0ha9BknK2iD5CDosaEE8gd40(int i, int i2, NumberPicker numberPicker, NumberPicker numberPicker2, int i3, LinearLayout linearLayout, Boolean bool) {
        int minValue;
        int value;
        int i4 = i % 60;
        int i5 = (i - i4) / 60;
        int i6 = i2 % 60;
        int i7 = (i2 - i6) / 60;
        if (i6 == 0 && i7 > 0) {
            i7--;
            i6 = 59;
        }
        if (bool.booleanValue()) {
            value = numberPicker.getValue();
            minValue = numberPicker2.getValue();
        } else {
            minValue = i3 % 60;
            value = (i3 - minValue) / 60;
            if (value == 24) {
                value--;
                minValue = 59;
            }
        }
        numberPicker.setMinValue(i5);
        numberPicker.setMaxValue(i7);
        if (value > i7) {
            numberPicker.setValue(i7);
            value = i7;
        } else if (value < i5) {
            numberPicker.setValue(i5);
            value = i5;
        }
        if (value <= i5) {
            numberPicker2.setMinValue(i4);
            numberPicker2.setMaxValue(i5 == i7 ? i6 : 59);
        } else if (value >= i7) {
            if (i5 != i7) {
                i4 = 0;
            }
            numberPicker2.setMinValue(i4);
            numberPicker2.setMaxValue(i6);
        } else if (i5 == i7) {
            numberPicker2.setMinValue(i4);
            numberPicker2.setMaxValue(i6);
        } else {
            numberPicker2.setMinValue(0);
            numberPicker2.setMaxValue(59);
        }
        if (minValue > numberPicker2.getMaxValue()) {
            minValue = numberPicker2.getMaxValue();
            numberPicker2.setValue(minValue);
        } else if (minValue < numberPicker2.getMinValue()) {
            minValue = numberPicker2.getMinValue();
            numberPicker2.setValue(minValue);
        }
        if (!bool.booleanValue()) {
            numberPicker.setValue(value);
            numberPicker2.setValue(minValue);
        }
        linearLayout.invalidate();
    }

    public static /* synthetic */ String $r8$lambda$CKSgp_q2qhFI8MlDTmmY1FiYYqc(int i) {
        boolean z = LocaleController.is24HourFormat;
        String str = String.format("%02d", Integer.valueOf((i % 12 != 0 || z) ? i % (z ? 24 : 12) : 12));
        return i >= 24 ? LocaleController.formatString(C2369R.string.BusinessHoursNextDayPicker, str) : str;
    }

    public static /* synthetic */ boolean $r8$lambda$62RpUknQw_ftOFKzMPb6aLQQ80s(View view, MotionEvent motionEvent) {
        return true;
    }

    public static AlertDialog.Builder createDatePickerDialog(Context context, int i, int i2, int i3, int i4, int i5, int i6, String str, final boolean z, final DatePickerDelegate datePickerDelegate) {
        if (context == null) {
            return null;
        }
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(0);
        linearLayout.setWeightSum(1.0f);
        final NumberPicker numberPicker = new NumberPicker(context);
        final NumberPicker numberPicker2 = new NumberPicker(context);
        final NumberPicker numberPicker3 = new NumberPicker(context);
        linearLayout.addView(numberPicker2, LayoutHelper.createLinear(0, -2, 0.3f));
        numberPicker2.setOnScrollListener(new NumberPicker.OnScrollListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda168
            @Override // org.telegram.ui.Components.NumberPicker.OnScrollListener
            public final void onScrollStateChange(NumberPicker numberPicker4, int i7) {
                AlertsCreator.m7594$r8$lambda$3hOYrmXOOXBQVBi_5NbOjIiBFk(z, numberPicker2, numberPicker, numberPicker3, numberPicker4, i7);
            }
        });
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(11);
        linearLayout.addView(numberPicker, LayoutHelper.createLinear(0, -2, 0.3f));
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda169
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i7) {
                return AlertsCreator.m7600$r8$lambda$7Lo5avyl9Gz_f_sGeFrmTZeBk(i7);
            }
        });
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda170
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker4, int i7, int i8) {
                AlertsCreator.updateDayPicker(numberPicker2, numberPicker, numberPicker3);
            }
        });
        numberPicker.setOnScrollListener(new NumberPicker.OnScrollListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda171
            @Override // org.telegram.ui.Components.NumberPicker.OnScrollListener
            public final void onScrollStateChange(NumberPicker numberPicker4, int i7) {
                AlertsCreator.$r8$lambda$FqbjHJTbrgWJDmPOi14kvi0QnXI(z, numberPicker2, numberPicker, numberPicker3, numberPicker4, i7);
            }
        });
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int i7 = calendar.get(1);
        numberPicker3.setMinValue(i + i7);
        numberPicker3.setMaxValue(i2 + i7);
        numberPicker3.setValue(i7 + i3);
        linearLayout.addView(numberPicker3, LayoutHelper.createLinear(0, -2, 0.4f));
        numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda172
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker4, int i8, int i9) {
                AlertsCreator.updateDayPicker(numberPicker2, numberPicker, numberPicker3);
            }
        });
        numberPicker3.setOnScrollListener(new NumberPicker.OnScrollListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda173
            @Override // org.telegram.ui.Components.NumberPicker.OnScrollListener
            public final void onScrollStateChange(NumberPicker numberPicker4, int i8) {
                AlertsCreator.$r8$lambda$kkvEN2WeO9lKGBpYbeXhv9zP1Ig(z, numberPicker2, numberPicker, numberPicker3, numberPicker4, i8);
            }
        });
        updateDayPicker(numberPicker2, numberPicker, numberPicker3);
        if (z) {
            checkPickerDate(numberPicker2, numberPicker, numberPicker3);
        }
        if (i4 != -1) {
            numberPicker2.setValue(i4);
            numberPicker.setValue(i5);
            numberPicker3.setValue(i6);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(str);
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Set), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda174
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i8) {
                AlertsCreator.m7598$r8$lambda$5Gz9fAxQcrgcZMQ3V8MhZb5UNs(z, numberPicker2, numberPicker, numberPicker3, datePickerDelegate, alertDialog, i8);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        return builder;
    }

    /* renamed from: $r8$lambda$3hOYrmXOOXBQ-VBi_5NbOjIiBFk, reason: not valid java name */
    public static /* synthetic */ void m7594$r8$lambda$3hOYrmXOOXBQVBi_5NbOjIiBFk(boolean z, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, NumberPicker numberPicker4, int i) {
        if (z && i == 0) {
            checkPickerDate(numberPicker, numberPicker2, numberPicker3);
        }
    }

    /* renamed from: $r8$lambda$7L-o5-avyl9Gz_f_sGeFrmTZeBk, reason: not valid java name */
    public static /* synthetic */ String m7600$r8$lambda$7Lo5avyl9Gz_f_sGeFrmTZeBk(int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, 1);
        calendar.set(2, i);
        return calendar.getDisplayName(2, 1, Locale.getDefault());
    }

    public static /* synthetic */ void $r8$lambda$FqbjHJTbrgWJDmPOi14kvi0QnXI(boolean z, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, NumberPicker numberPicker4, int i) {
        if (z && i == 0) {
            checkPickerDate(numberPicker, numberPicker2, numberPicker3);
        }
    }

    public static /* synthetic */ void $r8$lambda$kkvEN2WeO9lKGBpYbeXhv9zP1Ig(boolean z, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, NumberPicker numberPicker4, int i) {
        if (z && i == 0) {
            checkPickerDate(numberPicker, numberPicker2, numberPicker3);
        }
    }

    /* renamed from: $r8$lambda$5Gz-9fAxQcrgcZMQ3V8MhZb5UNs, reason: not valid java name */
    public static /* synthetic */ void m7598$r8$lambda$5Gz9fAxQcrgcZMQ3V8MhZb5UNs(boolean z, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, DatePickerDelegate datePickerDelegate, AlertDialog alertDialog, int i) {
        if (z) {
            checkPickerDate(numberPicker, numberPicker2, numberPicker3);
        }
        datePickerDelegate.didSelectDate(numberPicker3.getValue(), numberPicker2.getValue(), numberPicker.getValue());
    }

    public static boolean checkScheduleDate(TextView textView, TextView textView2, int i, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
        return checkScheduleDate(textView, textView2, 0L, 0L, i, numberPicker, numberPicker2, numberPicker3);
    }

    public static boolean checkScheduleDate(TextView textView, TextView textView2, long j, int i, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
        return checkScheduleDate(textView, textView2, 0L, j, i, numberPicker, numberPicker2, numberPicker3);
    }

    public static boolean checkScheduleDate(TextView textView, TextView textView2, long j, long j2, int i, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
        long j3;
        int i2;
        long timeInMillis;
        int i3;
        int days;
        int i4;
        boolean z;
        boolean z2;
        String pluralString;
        int value = numberPicker.getValue();
        int value2 = numberPicker2.getValue();
        int value3 = numberPicker3.getValue();
        Calendar calendar = Calendar.getInstance();
        long jCurrentTimeMillis = System.currentTimeMillis();
        calendar.setTimeInMillis(jCurrentTimeMillis);
        int i5 = calendar.get(1);
        calendar.get(6);
        if (j2 > 0) {
            i2 = i5;
            long j4 = j2 * 1000;
            j3 = jCurrentTimeMillis;
            calendar.setTimeInMillis(j3 + j4);
            calendar.set(11, 23);
            calendar.set(12, 59);
            calendar.set(13, 59);
            days = (int) TimeUnit.MILLISECONDS.toDays(j4);
            timeInMillis = calendar.getTimeInMillis();
            i3 = 23;
            i4 = 59;
        } else {
            j3 = jCurrentTimeMillis;
            i2 = i5;
            timeInMillis = j2;
            i3 = 0;
            days = 0;
            i4 = 0;
        }
        long millis = j > 0 ? TimeUnit.SECONDS.toMillis(j) : 60000L;
        long j5 = j3 + millis;
        calendar.setTimeInMillis(j5);
        int i6 = calendar.get(11);
        int i7 = calendar.get(12);
        long j6 = timeInMillis;
        calendar.setTimeInMillis(System.currentTimeMillis() + (value * 86400000));
        calendar.set(11, value2);
        calendar.set(12, value3);
        long timeInMillis2 = calendar.getTimeInMillis();
        calendar.setTimeInMillis(timeInMillis2);
        numberPicker.setMinValue(0);
        if (j6 > 0) {
            numberPicker.setMaxValue(days);
        }
        int value4 = numberPicker.getValue();
        numberPicker2.setMinValue(value4 == 0 ? i6 : 0);
        if (j6 > 0) {
            numberPicker2.setMaxValue(value4 == days ? i3 : 23);
        }
        int value5 = numberPicker2.getValue();
        numberPicker3.setMinValue((value4 == 0 && value5 == i6) ? i7 : 0);
        if (j6 > 0) {
            numberPicker3.setMaxValue((value4 == days && value5 == i3) ? i4 : 59);
        }
        int value6 = numberPicker3.getValue();
        if (timeInMillis2 <= j5) {
            calendar.setTimeInMillis(j5);
        } else if (j6 > 0 && timeInMillis2 > j6) {
            calendar.setTimeInMillis(j6);
        }
        int i8 = calendar.get(1);
        calendar.setTimeInMillis(System.currentTimeMillis() + (value4 * 86400000));
        calendar.set(11, value5);
        calendar.set(12, value6);
        long timeInMillis3 = calendar.getTimeInMillis();
        if (textView != null) {
            textView.setText(LocaleController.getInstance().getFormatterScheduleSend((value4 == 0 ? 0 : i2 == i8 ? 1 : 2) + (i * 3)).format(timeInMillis3));
        }
        if (textView2 != null) {
            int i9 = (int) ((timeInMillis3 - j3) / 1000);
            if (i9 > 86400) {
                z2 = false;
                pluralString = LocaleController.formatPluralString("DaysSchedule", Math.round(i9 / 86400.0f), new Object[0]);
            } else {
                z2 = false;
                z2 = false;
                z2 = false;
                if (i9 >= 3600) {
                    pluralString = LocaleController.formatPluralString("HoursSchedule", Math.round(i9 / 3600.0f), new Object[0]);
                } else if (i9 >= 60) {
                    pluralString = LocaleController.formatPluralString("MinutesSchedule", Math.round(i9 / 60.0f), new Object[0]);
                } else {
                    pluralString = LocaleController.formatPluralString("SecondsSchedule", i9, new Object[0]);
                }
            }
            if (textView2.getTag() != null) {
                int i10 = C2369R.string.VoipChannelScheduleInfo;
                z = true;
                Object[] objArr = new Object[1];
                objArr[z2 ? 1 : 0] = pluralString;
                textView2.setText(LocaleController.formatString("VoipChannelScheduleInfo", i10, objArr));
            } else {
                z = true;
                int i11 = C2369R.string.VoipGroupScheduleInfo;
                Object[] objArr2 = new Object[1];
                objArr2[z2 ? 1 : 0] = pluralString;
                textView2.setText(LocaleController.formatString("VoipGroupScheduleInfo", i11, objArr2));
            }
        } else {
            z = true;
            z2 = false;
        }
        return timeInMillis2 - j3 > millis ? z : z2;
    }

    public static class ScheduleDatePickerColors {
        public final int backgroundColor;
        public final int buttonBackgroundColor;
        public final int buttonBackgroundPressedColor;
        public final int buttonTextColor;
        public final int iconColor;
        public final int iconSelectorColor;
        public final int subMenuBackgroundColor;
        public final int subMenuSelectorColor;
        public final int subMenuTextColor;
        public final int textColor;

        private ScheduleDatePickerColors() {
            this((Theme.ResourcesProvider) null);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        public ScheduleDatePickerColors(Theme.ResourcesProvider resourcesProvider) {
            int i = Theme.key_dialogTextBlack;
            int colorOrDefault = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i) : Theme.getColor(i);
            int i2 = Theme.key_dialogBackground;
            int colorOrDefault2 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i2) : Theme.getColor(i2);
            int i3 = Theme.key_sheet_other;
            int colorOrDefault3 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i3) : Theme.getColor(i3);
            int i4 = Theme.key_player_actionBarSelector;
            int colorOrDefault4 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i4) : Theme.getColor(i4);
            int i5 = Theme.key_actionBarDefaultSubmenuItem;
            int colorOrDefault5 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i5) : Theme.getColor(i5);
            int i6 = Theme.key_actionBarDefaultSubmenuBackground;
            int colorOrDefault6 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i6) : Theme.getColor(i6);
            int i7 = Theme.key_listSelector;
            int colorOrDefault7 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i7) : Theme.getColor(i7);
            int i8 = Theme.key_featuredStickers_buttonText;
            int colorOrDefault8 = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i8) : Theme.getColor(i8);
            int i9 = Theme.key_featuredStickers_addButton;
            this(colorOrDefault, colorOrDefault2, colorOrDefault3, colorOrDefault4, colorOrDefault5, colorOrDefault6, colorOrDefault7, colorOrDefault8, resourcesProvider != null ? resourcesProvider.getColorOrDefault(i9) : Theme.getColor(i9), resourcesProvider != null ? resourcesProvider.getColorOrDefault(Theme.key_featuredStickers_addButtonPressed) : Theme.getColor(Theme.key_featuredStickers_addButtonPressed));
        }

        public ScheduleDatePickerColors(int i, int i2, int i3, int i4, int i5, int i6, int i7) {
            this(i, i2, i3, i4, i5, i6, i7, Theme.getColor(Theme.key_featuredStickers_buttonText), Theme.getColor(Theme.key_featuredStickers_addButton), Theme.getColor(Theme.key_featuredStickers_addButtonPressed));
        }

        public ScheduleDatePickerColors(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
            this.textColor = i;
            this.backgroundColor = i2;
            this.iconColor = i3;
            this.iconSelectorColor = i4;
            this.subMenuTextColor = i5;
            this.subMenuBackgroundColor = i6;
            this.subMenuSelectorColor = i7;
            this.buttonTextColor = i8;
            this.buttonBackgroundColor = i9;
            this.buttonBackgroundPressedColor = i10;
        }
    }

    public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, ScheduleDatePickerDelegate scheduleDatePickerDelegate) {
        return createScheduleDatePickerDialog(context, j, -1L, scheduleDatePickerDelegate, (Runnable) null);
    }

    public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Theme.ResourcesProvider resourcesProvider) {
        return createScheduleDatePickerDialog(context, j, -1L, 0, scheduleDatePickerDelegate, null, resourcesProvider);
    }

    public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, ScheduleDatePickerDelegate scheduleDatePickerDelegate, ScheduleDatePickerColors scheduleDatePickerColors) {
        return createScheduleDatePickerDialog(context, j, -1L, 0, scheduleDatePickerDelegate, null, scheduleDatePickerColors, null);
    }

    public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
        return createScheduleDatePickerDialog(context, j, -1L, 0, scheduleDatePickerDelegate, runnable, resourcesProvider);
    }

    public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, long j2, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Runnable runnable) {
        return createScheduleDatePickerDialog(context, j, j2, 0, scheduleDatePickerDelegate, runnable, new ScheduleDatePickerColors(), null);
    }

    public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, long j, long j2, int i, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
        return createScheduleDatePickerDialog(context, j, j2, i, scheduleDatePickerDelegate, runnable, new ScheduleDatePickerColors(resourcesProvider), resourcesProvider);
    }

    public static BottomSheet.Builder createScheduleDatePickerDialog(Context context, final long j, long j2, int i, final ScheduleDatePickerDelegate scheduleDatePickerDelegate, final Runnable runnable, final ScheduleDatePickerColors scheduleDatePickerColors, final Theme.ResourcesProvider resourcesProvider) {
        Context context2;
        FrameLayout frameLayout;
        FrameLayout frameLayout2;
        final int[] iArr;
        long j3;
        int i2;
        int i3;
        LinearLayout linearLayout;
        char c;
        final int[] iArr2;
        final String[] strArr;
        TLRPC.User user;
        TLRPC.UserStatus userStatus;
        if (context == null) {
            return null;
        }
        int[] iArr3 = {i};
        long clientUserId = UserConfig.getInstance(UserConfig.selectedAccount).getClientUserId();
        final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
        builder.setApplyBottomPadding(false);
        final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider);
        numberPicker.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker.setTextOffset(AndroidUtilities.m1146dp(10.0f));
        numberPicker.setItemCount(5);
        final NumberPicker numberPicker2 = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.25
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i4) {
                return LocaleController.formatPluralString("Hours", i4, new Object[0]);
            }
        };
        numberPicker2.setWrapSelectorWheel(true);
        numberPicker2.setAllItemsCount(24);
        numberPicker2.setItemCount(5);
        numberPicker2.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker2.setTextOffset(-AndroidUtilities.m1146dp(10.0f));
        final NumberPicker numberPicker3 = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.26
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i4) {
                return LocaleController.formatPluralString("Minutes", i4, new Object[0]);
            }
        };
        numberPicker3.setWrapSelectorWheel(true);
        numberPicker3.setAllItemsCount(60);
        numberPicker3.setItemCount(5);
        numberPicker3.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker3.setTextOffset(-AndroidUtilities.m1146dp(34.0f));
        FrameLayout frameLayout3 = new FrameLayout(context);
        LinearLayout linearLayout2 = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.27
            boolean ignoreLayout = false;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i4, int i5) {
                this.ignoreLayout = true;
                Point point = AndroidUtilities.displaySize;
                int i6 = point.x > point.y ? 3 : 5;
                numberPicker.setItemCount(i6);
                numberPicker2.setItemCount(i6);
                numberPicker3.setItemCount(i6);
                numberPicker.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i6;
                numberPicker2.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i6;
                numberPicker3.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i6;
                this.ignoreLayout = false;
                super.onMeasure(i4, i5);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        linearLayout2.setOrientation(1);
        frameLayout3.addView(linearLayout2, LayoutHelper.createFrame(-1, -1.0f));
        FrameLayout frameLayout4 = new FrameLayout(context);
        frameLayout3.addView(frameLayout4, LayoutHelper.createFrame(-1, 100.0f, 87, 0.0f, 0.0f, 0.0f, 120.0f));
        FrameLayout frameLayout5 = new FrameLayout(context);
        linearLayout2.addView(frameLayout5, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
        TextView textView = new TextView(context);
        if (j == clientUserId) {
            textView.setText(LocaleController.getString(C2369R.string.SetReminder));
        } else {
            textView.setText(LocaleController.getString(C2369R.string.ScheduleMessage));
        }
        textView.setTextColor(scheduleDatePickerColors.textColor);
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        frameLayout5.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda16
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.$r8$lambda$qarxAGdp_7MBwmbI_Sw_kt7LZ88(view, motionEvent);
            }
        });
        if (!DialogObject.isUserDialog(j) || j == clientUserId || (user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Long.valueOf(j))) == null || user.bot || (userStatus = user.status) == null || userStatus.expires <= 0) {
            context2 = context;
            frameLayout = frameLayout4;
            frameLayout2 = frameLayout3;
            iArr = iArr3;
            j3 = clientUserId;
            i2 = 1;
            i3 = 60;
            linearLayout = linearLayout2;
        } else {
            String firstName = UserObject.getFirstName(user);
            if (firstName.length() > 10) {
                firstName = firstName.substring(0, 10) + "…";
            }
            String str = firstName;
            frameLayout = frameLayout4;
            frameLayout2 = frameLayout3;
            iArr = iArr3;
            j3 = clientUserId;
            linearLayout = linearLayout2;
            i2 = 1;
            i3 = 60;
            final ActionBarMenuItem actionBarMenuItem = new ActionBarMenuItem(context, null, 0, scheduleDatePickerColors.iconColor, false, resourcesProvider);
            context2 = context;
            actionBarMenuItem.setLongClickEnabled(false);
            actionBarMenuItem.setSubMenuOpenSide(2);
            actionBarMenuItem.setIcon(C2369R.drawable.ic_ab_other);
            actionBarMenuItem.setBackgroundDrawable(Theme.createSelectorDrawable(scheduleDatePickerColors.iconSelectorColor, 1));
            frameLayout5.addView(actionBarMenuItem, LayoutHelper.createFrame(40, 40.0f, 53, 0.0f, 8.0f, 5.0f, 0.0f));
            actionBarMenuItem.addSubItem(1, LocaleController.formatString("ScheduleWhenOnline", C2369R.string.ScheduleWhenOnline, str));
            actionBarMenuItem.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda18
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertsCreator.m7630$r8$lambda$Wqu1IaeBGgr9YCAgHz_qnwbFUo(actionBarMenuItem, scheduleDatePickerColors, view);
                }
            });
            actionBarMenuItem.setDelegate(new ActionBarMenuItem.ActionBarMenuItemDelegate() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda19
                @Override // org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemDelegate
                public final void onItemClick(int i4) {
                    AlertsCreator.m7602$r8$lambda$8QbCOqMpnsgXna0j8SYfxRbI0o(scheduleDatePickerDelegate, builder, i4);
                }
            });
            actionBarMenuItem.setContentDescription(LocaleController.getString(C2369R.string.AccDescrMoreOptions));
        }
        LinearLayout linearLayout3 = new LinearLayout(context2);
        linearLayout3.setOrientation(0);
        linearLayout3.setWeightSum(1.0f);
        linearLayout.addView(linearLayout3, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
        final long jCurrentTimeMillis = System.currentTimeMillis();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(jCurrentTimeMillis);
        final int i4 = calendar.get(i2);
        final TextView textView2 = new TextView(context2) { // from class: org.telegram.ui.Components.AlertsCreator.28
            @Override // android.widget.TextView, android.view.View
            public CharSequence getAccessibilityClassName() {
                return Button.class.getName();
            }
        };
        linearLayout3.addView(numberPicker, LayoutHelper.createLinear(0, 270, 0.5f));
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(365);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda20
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i5) {
                return AlertsCreator.$r8$lambda$lci7xNoz8InrBP95KLwPNjJ9p80(jCurrentTimeMillis, calendar, i4, i5);
            }
        });
        final long j4 = j3;
        NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda21
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker4, int i5, int i6) {
                AlertsCreator.checkScheduleDate(textView2, null, j4 == j ? 1 : 0, numberPicker, numberPicker2, numberPicker3);
            }
        };
        NumberPicker numberPicker4 = numberPicker;
        numberPicker4.setOnValueChangedListener(onValueChangeListener);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(23);
        linearLayout3.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.2f));
        numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda22
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i5) {
                return String.format("%02d", Integer.valueOf(i5));
            }
        });
        numberPicker2.setOnValueChangedListener(onValueChangeListener);
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(59);
        numberPicker3.setValue(0);
        numberPicker3.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda23
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i5) {
                return String.format("%02d", Integer.valueOf(i5));
            }
        });
        linearLayout3.addView(numberPicker3, LayoutHelper.createLinear(0, 270, 0.3f));
        numberPicker3.setOnValueChangedListener(onValueChangeListener);
        if (j2 > 0 && j2 != 2147483646) {
            long j5 = j2 * 1000;
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            calendar.set(11, 0);
            int timeInMillis = (int) ((j5 - calendar.getTimeInMillis()) / 86400000);
            calendar.setTimeInMillis(j5);
            if (timeInMillis >= 0) {
                numberPicker3.setValue(calendar.get(12));
                numberPicker2.setValue(calendar.get(11));
                numberPicker4 = numberPicker4;
                numberPicker4.setValue(timeInMillis);
            } else {
                numberPicker4 = numberPicker4;
            }
        }
        final boolean[] zArr = {true};
        final NumberPicker numberPicker5 = numberPicker4;
        checkScheduleDate(textView2, null, j4 == j ? 1 : 0, numberPicker5, numberPicker2, numberPicker3);
        boolean zIsTestBackend = ConnectionsManager.getInstance(UserConfig.selectedAccount).isTestBackend();
        if (zIsTestBackend) {
            c = '\t';
            iArr2 = new int[]{0, i3, DataTypes.UNIT, 86400, 604800, 1209600, 2592000, 7862400, 15724800, 31536000};
        } else {
            c = '\t';
            iArr2 = new int[]{0, 86400, 604800, 1209600, 2592000, 7862400, 15724800, 31536000};
        }
        if (zIsTestBackend) {
            strArr = new String[10];
            strArr[0] = LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionNever);
            strArr[1] = "Every minute";
            strArr[2] = "Every 5 minutes";
            strArr[3] = LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionDaily);
            strArr[4] = LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionWeekly);
            strArr[5] = LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionBiweekly);
            strArr[6] = LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionMonthly);
            strArr[7] = LocaleController.getString(C2369R.string.MessageScheduledRepeatOption3Monthly);
            strArr[8] = LocaleController.getString(C2369R.string.MessageScheduledRepeatOption6Monthly);
            strArr[c] = LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionYearly);
        } else {
            strArr = new String[]{LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionNever), LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionDaily), LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionWeekly), LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionBiweekly), LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionMonthly), LocaleController.getString(C2369R.string.MessageScheduledRepeatOption3Monthly), LocaleController.getString(C2369R.string.MessageScheduledRepeatOption6Monthly), LocaleController.getString(C2369R.string.MessageScheduledRepeatOptionYearly)};
        }
        final FrameLayout frameLayout6 = new FrameLayout(context2);
        int i5 = scheduleDatePickerColors.textColor;
        int iBlendOver = Theme.blendOver(scheduleDatePickerColors.backgroundColor, Theme.multAlpha(i5, 0.075f));
        int iMultAlpha = Theme.multAlpha(scheduleDatePickerColors.textColor, 0.1f);
        final TextView textView3 = new TextView(context2);
        textView3.setTextSize(1, 13.0f);
        textView3.setTextColor(i5);
        textView3.setPadding(AndroidUtilities.m1146dp(12.0f), 0, AndroidUtilities.m1146dp(12.0f), 0);
        textView3.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(14.0f), iBlendOver, Theme.blendOver(iBlendOver, iMultAlpha)));
        textView3.setGravity(17);
        final int[] iArr4 = iArr;
        final Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda24
            @Override // java.lang.Runnable
            public final void run() {
                AlertsCreator.m7646$r8$lambda$mDu2w4peT2qIsBxcAzbS3FQShA(iArr2, iArr4, strArr, textView3);
            }
        };
        runnable2.run();
        frameLayout6.addView(textView3, LayoutHelper.createFrame(-2, 28.0f, 1, 32.0f, 4.0f, 32.0f, 5.0f));
        linearLayout.addView(frameLayout6, LayoutHelper.createLinear(-1, -2));
        final String[] strArr2 = strArr;
        textView2.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView2.setGravity(17);
        textView2.setTextColor(scheduleDatePickerColors.buttonTextColor);
        textView2.setTextSize(1, 14.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setBackground(Theme.AdaptiveRipple.filledRect(scheduleDatePickerColors.buttonBackgroundColor, 8.0f));
        linearLayout.addView(textView2, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda25
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.$r8$lambda$Lg0cCZPIUKg8rFYy9KxRle2YMyc(zArr, j4, j, numberPicker5, numberPicker2, numberPicker3, calendar, scheduleDatePickerDelegate, iArr, builder, view);
            }
        });
        builder.setCustomView(frameLayout2);
        final BottomSheet bottomSheetShow = builder.show();
        bottomSheetShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda26
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AlertsCreator.$r8$lambda$JRpu6QjZvBonkI2gc7mtFa3pWUo(runnable, zArr, dialogInterface);
            }
        });
        bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
        bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
        final int[] iArr5 = iArr2;
        final int[] iArr6 = iArr;
        final FrameLayout frameLayout7 = frameLayout;
        textView3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.m7592$r8$lambda$2N92La3GuWpGLLDZOfPp7HRLM0(frameLayout7, resourcesProvider, bottomSheetShow, frameLayout6, iArr5, strArr2, iArr6, runnable2, view);
            }
        });
        return builder;
    }

    public static /* synthetic */ boolean $r8$lambda$qarxAGdp_7MBwmbI_Sw_kt7LZ88(View view, MotionEvent motionEvent) {
        return true;
    }

    /* renamed from: $r8$lambda$Wqu1IaeBGgr9YCAgHz_q-nwbFUo, reason: not valid java name */
    public static /* synthetic */ void m7630$r8$lambda$Wqu1IaeBGgr9YCAgHz_qnwbFUo(ActionBarMenuItem actionBarMenuItem, ScheduleDatePickerColors scheduleDatePickerColors, View view) {
        actionBarMenuItem.toggleSubMenu();
        actionBarMenuItem.setPopupItemsColor(scheduleDatePickerColors.subMenuTextColor, false);
        actionBarMenuItem.setupPopupRadialSelectors(scheduleDatePickerColors.subMenuSelectorColor);
        actionBarMenuItem.redrawPopup(scheduleDatePickerColors.subMenuBackgroundColor);
    }

    /* renamed from: $r8$lambda$8QbCOqMpn-sgXna0j8SYfxRbI0o, reason: not valid java name */
    public static /* synthetic */ void m7602$r8$lambda$8QbCOqMpnsgXna0j8SYfxRbI0o(ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, int i) {
        if (i == 1) {
            scheduleDatePickerDelegate.didSelectDate(true, 2147483646, 0);
            builder.getDismissRunnable().run();
        }
    }

    public static /* synthetic */ String $r8$lambda$lci7xNoz8InrBP95KLwPNjJ9p80(long j, Calendar calendar, int i, int i2) {
        if (i2 == 0) {
            return LocaleController.getString(C2369R.string.MessageScheduleToday);
        }
        long j2 = j + (i2 * 86400000);
        calendar.setTimeInMillis(j2);
        int i3 = calendar.get(1);
        LocaleController.getInstance().getFormatterWeek().format(j2);
        if (i3 == i) {
            return LocaleController.getInstance().getFormatterWeek().format(j2) + ", " + LocaleController.getInstance().getFormatterScheduleDay().format(j2);
        }
        return LocaleController.getInstance().getFormatterScheduleYear().format(j2);
    }

    /* renamed from: $r8$lambda$m-Du2w4peT2qIsBxcAzbS3FQShA, reason: not valid java name */
    public static /* synthetic */ void m7646$r8$lambda$mDu2w4peT2qIsBxcAzbS3FQShA(int[] iArr, int[] iArr2, String[] strArr, TextView textView) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append((CharSequence) LocaleController.getString(C2369R.string.MessageScheduledRepeatOption));
        spannableStringBuilder.append((CharSequence) " ");
        int length = spannableStringBuilder.length();
        int i = 0;
        while (true) {
            if (i >= iArr.length) {
                break;
            }
            if (iArr2[0] == iArr[i]) {
                spannableStringBuilder.append((CharSequence) strArr[i]);
                spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.bold()), length, spannableStringBuilder.length(), 33);
                break;
            }
            i++;
        }
        spannableStringBuilder.append((CharSequence) " v");
        if (UserConfig.getInstance(UserConfig.selectedAccount).isPremium()) {
            ColoredImageSpan coloredImageSpan = new ColoredImageSpan(C2369R.drawable.arrows_select);
            coloredImageSpan.spaceScaleX = 0.7f;
            coloredImageSpan.translate(AndroidUtilities.m1146dp(-1.33f), AndroidUtilities.m1146dp(0.0f));
            coloredImageSpan.setAlpha(0.75f);
            spannableStringBuilder.setSpan(coloredImageSpan, spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 33);
        } else {
            ColoredImageSpan coloredImageSpan2 = new ColoredImageSpan(C2369R.drawable.mini_switch_lock);
            coloredImageSpan2.spaceScaleX = 0.7f;
            coloredImageSpan2.translate(AndroidUtilities.m1146dp(-1.33f), AndroidUtilities.m1146dp(0.0f));
            coloredImageSpan2.setAlpha(0.75f);
            spannableStringBuilder.setSpan(coloredImageSpan2, spannableStringBuilder.length() - 1, spannableStringBuilder.length(), 33);
        }
        textView.setText(spannableStringBuilder);
    }

    public static /* synthetic */ void $r8$lambda$Lg0cCZPIUKg8rFYy9KxRle2YMyc(boolean[] zArr, long j, long j2, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, Calendar calendar, ScheduleDatePickerDelegate scheduleDatePickerDelegate, int[] iArr, BottomSheet.Builder builder, View view) {
        zArr[0] = false;
        boolean zCheckScheduleDate = checkScheduleDate(null, null, j == j2 ? 1 : 0, numberPicker, numberPicker2, numberPicker3);
        calendar.setTimeInMillis(System.currentTimeMillis() + (numberPicker.getValue() * 86400000));
        calendar.set(11, numberPicker2.getValue());
        calendar.set(12, numberPicker3.getValue());
        if (zCheckScheduleDate) {
            calendar.set(13, 0);
        }
        scheduleDatePickerDelegate.didSelectDate(true, (int) (calendar.getTimeInMillis() / 1000), iArr[0]);
        builder.getDismissRunnable().run();
    }

    public static /* synthetic */ void $r8$lambda$JRpu6QjZvBonkI2gc7mtFa3pWUo(Runnable runnable, boolean[] zArr, DialogInterface dialogInterface) {
        if (runnable == null || !zArr[0]) {
            return;
        }
        runnable.run();
    }

    /* renamed from: $r8$lambda$2-N92La3GuWpGLLDZOfPp7HRLM0, reason: not valid java name */
    public static /* synthetic */ void m7592$r8$lambda$2N92La3GuWpGLLDZOfPp7HRLM0(FrameLayout frameLayout, Theme.ResourcesProvider resourcesProvider, BottomSheet bottomSheet, FrameLayout frameLayout2, int[] iArr, String[] strArr, final int[] iArr2, final Runnable runnable, View view) {
        if (!UserConfig.getInstance(UserConfig.selectedAccount).isPremium()) {
            BulletinFactory.m1266of(frameLayout, resourcesProvider).createSimpleBulletin(C2369R.raw.star_premium_2, AndroidUtilities.premiumText(LocaleController.getString(C2369R.string.MessageScheduledRepeatPremium), new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda39
                @Override // java.lang.Runnable
                public final void run() {
                    AlertsCreator.$r8$lambda$AZG8UCCPR_A3H_UyglKwqWiOH2Y();
                }
            })).show();
            return;
        }
        ItemOptions itemOptionsMakeOptions = ItemOptions.makeOptions(bottomSheet.container, resourcesProvider, frameLayout2);
        for (int i = 0; i < iArr.length; i++) {
            final int i2 = iArr[i];
            itemOptionsMakeOptions.add(strArr[i], new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda40
                @Override // java.lang.Runnable
                public final void run() {
                    AlertsCreator.m7639$r8$lambda$dJLxfhGFnm3l3RFituhNdfiXRg(iArr2, i2, runnable);
                }
            });
        }
        itemOptionsMakeOptions.setGravity(1);
        itemOptionsMakeOptions.show();
    }

    public static /* synthetic */ void $r8$lambda$AZG8UCCPR_A3H_UyglKwqWiOH2Y() {
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (safeLastFragment == null) {
            return;
        }
        BaseFragment.BottomSheetParams bottomSheetParams = new BaseFragment.BottomSheetParams();
        bottomSheetParams.transitionFromLeft = true;
        bottomSheetParams.allowNestedScroll = false;
        safeLastFragment.showAsSheet(new PremiumPreviewFragment("schedule_repeat"), bottomSheetParams);
    }

    /* renamed from: $r8$lambda$dJLxfh-GFnm3l3RFituhNdfiXRg, reason: not valid java name */
    public static /* synthetic */ void m7639$r8$lambda$dJLxfhGFnm3l3RFituhNdfiXRg(int[] iArr, int i, Runnable runnable) {
        iArr[0] = i;
        runnable.run();
    }

    public static BottomSheet.Builder createDatePickerDialog(Context context, String str, String str2, long j, final ScheduleDatePickerDelegate scheduleDatePickerDelegate) {
        if (context == null) {
            return null;
        }
        ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors();
        final BottomSheet.Builder builder = new BottomSheet.Builder(context, false);
        builder.setApplyBottomPadding(false);
        final NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker.setTextOffset(AndroidUtilities.m1146dp(10.0f));
        numberPicker.setItemCount(5);
        final NumberPicker numberPicker2 = new NumberPicker(context) { // from class: org.telegram.ui.Components.AlertsCreator.29
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i) {
                return LocaleController.formatPluralString("Hours", i, new Object[0]);
            }
        };
        numberPicker2.setItemCount(5);
        numberPicker2.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker2.setTextOffset(-AndroidUtilities.m1146dp(10.0f));
        final NumberPicker numberPicker3 = new NumberPicker(context) { // from class: org.telegram.ui.Components.AlertsCreator.30
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i) {
                return LocaleController.formatPluralString("Minutes", i, new Object[0]);
            }
        };
        numberPicker3.setItemCount(5);
        numberPicker3.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker3.setTextOffset(-AndroidUtilities.m1146dp(34.0f));
        LinearLayout linearLayout = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.31
            boolean ignoreLayout = false;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                this.ignoreLayout = true;
                Point point = AndroidUtilities.displaySize;
                int i3 = point.x > point.y ? 3 : 5;
                numberPicker.setItemCount(i3);
                numberPicker2.setItemCount(i3);
                numberPicker3.setItemCount(i3);
                numberPicker.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                numberPicker2.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                numberPicker3.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                this.ignoreLayout = false;
                super.onMeasure(i, i2);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        linearLayout.setOrientation(1);
        FrameLayout frameLayout = new FrameLayout(context);
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
        TextView textView = new TextView(context);
        textView.setText(str);
        textView.setTextColor(scheduleDatePickerColors.textColor);
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda199
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.$r8$lambda$j2E6yEmPzUFsa6vmMSpjWT9yOEA(view, motionEvent);
            }
        });
        LinearLayout linearLayout2 = new LinearLayout(context);
        linearLayout2.setOrientation(0);
        linearLayout2.setWeightSum(1.0f);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
        final long jCurrentTimeMillis = System.currentTimeMillis();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(jCurrentTimeMillis);
        final int i = calendar.get(1);
        TextView textView2 = new TextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.32
            @Override // android.widget.TextView, android.view.View
            public CharSequence getAccessibilityClassName() {
                return Button.class.getName();
            }
        };
        linearLayout2.addView(numberPicker, LayoutHelper.createLinear(0, 270, 0.5f));
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(365);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda200
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i2) {
                return AlertsCreator.m7601$r8$lambda$7q_tHPeK5tlMxW5IXj2m_vbFEA(jCurrentTimeMillis, calendar, i, i2);
            }
        });
        NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda201
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker4, int i2, int i3) {
                AlertsCreator.checkScheduleDate(null, null, 0, numberPicker, numberPicker2, numberPicker3);
            }
        };
        numberPicker.setOnValueChangedListener(onValueChangeListener);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(23);
        linearLayout2.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.2f));
        numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda202
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i2) {
                return String.format("%02d", Integer.valueOf(i2));
            }
        });
        numberPicker2.setOnValueChangedListener(onValueChangeListener);
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(59);
        numberPicker3.setValue(0);
        numberPicker3.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda203
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i2) {
                return String.format("%02d", Integer.valueOf(i2));
            }
        });
        linearLayout2.addView(numberPicker3, LayoutHelper.createLinear(0, 270, 0.3f));
        numberPicker3.setOnValueChangedListener(onValueChangeListener);
        if (j > 0 && j != 2147483646) {
            long j2 = 1000 * j;
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            calendar.set(11, 0);
            int timeInMillis = (int) ((j2 - calendar.getTimeInMillis()) / 86400000);
            calendar.setTimeInMillis(j2);
            if (timeInMillis >= 0) {
                numberPicker3.setValue(calendar.get(12));
                numberPicker2.setValue(calendar.get(11));
                numberPicker.setValue(timeInMillis);
            }
        }
        checkScheduleDate(null, null, 0, numberPicker, numberPicker2, numberPicker3);
        textView2.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView2.setGravity(17);
        textView2.setTextColor(scheduleDatePickerColors.buttonTextColor);
        textView2.setTextSize(1, 14.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(8.0f), scheduleDatePickerColors.buttonBackgroundColor, scheduleDatePickerColors.buttonBackgroundPressedColor));
        textView2.setText(str2);
        linearLayout.addView(textView2, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda204
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.$r8$lambda$Xwj6mm7fIuQWwiuGSQ8FosgRPRQ(numberPicker, numberPicker2, numberPicker3, calendar, scheduleDatePickerDelegate, builder, view);
            }
        });
        builder.setCustomView(linearLayout);
        BottomSheet bottomSheetShow = builder.show();
        bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
        bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
        return builder;
    }

    public static /* synthetic */ boolean $r8$lambda$j2E6yEmPzUFsa6vmMSpjWT9yOEA(View view, MotionEvent motionEvent) {
        return true;
    }

    /* renamed from: $r8$lambda$7q_tHPeK5-tlMxW5IXj2m_vbFEA, reason: not valid java name */
    public static /* synthetic */ String m7601$r8$lambda$7q_tHPeK5tlMxW5IXj2m_vbFEA(long j, Calendar calendar, int i, int i2) {
        if (i2 == 0) {
            return LocaleController.getString(C2369R.string.MessageScheduleToday);
        }
        long j2 = j + (i2 * 86400000);
        calendar.setTimeInMillis(j2);
        if (calendar.get(1) == i) {
            return LocaleController.getInstance().getFormatterScheduleDay().format(j2);
        }
        return LocaleController.getInstance().getFormatterScheduleYear().format(j2);
    }

    public static /* synthetic */ void $r8$lambda$Xwj6mm7fIuQWwiuGSQ8FosgRPRQ(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, Calendar calendar, ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, View view) {
        boolean zCheckScheduleDate = checkScheduleDate(null, null, 0, numberPicker, numberPicker2, numberPicker3);
        calendar.setTimeInMillis(System.currentTimeMillis() + (numberPicker.getValue() * 86400000));
        calendar.set(11, numberPicker2.getValue());
        calendar.set(12, numberPicker3.getValue());
        if (zCheckScheduleDate) {
            calendar.set(13, 0);
        }
        scheduleDatePickerDelegate.didSelectDate(true, (int) (calendar.getTimeInMillis() / 1000), 0);
        builder.getDismissRunnable().run();
    }

    public static BottomSheet.Builder createBirthdayPickerDialog(Context context, String str, String str2, TL_account.TL_birthday tL_birthday, final Utilities.Callback callback, Runnable runnable, boolean z, Theme.ResourcesProvider resourcesProvider) {
        float f;
        if (context == null) {
            return null;
        }
        final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
        builder.setApplyBottomPadding(false);
        final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider);
        numberPicker.setTextOffset(AndroidUtilities.m1146dp(10.0f));
        numberPicker.setItemCount(5);
        final NumberPicker numberPicker2 = new NumberPicker(context, resourcesProvider);
        numberPicker2.setItemCount(5);
        numberPicker2.setTextOffset(-AndroidUtilities.m1146dp(10.0f));
        final NumberPicker numberPicker3 = new NumberPicker(context, resourcesProvider);
        numberPicker3.setItemCount(5);
        numberPicker3.setTextOffset(-AndroidUtilities.m1146dp(24.0f));
        LinearLayout linearLayout = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.33
            boolean ignoreLayout = false;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                this.ignoreLayout = true;
                Point point = AndroidUtilities.displaySize;
                int i3 = point.x > point.y ? 3 : 5;
                numberPicker.setItemCount(i3);
                numberPicker2.setItemCount(i3);
                numberPicker3.setItemCount(i3);
                numberPicker.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                numberPicker2.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                numberPicker3.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                this.ignoreLayout = false;
                super.onMeasure(i, i2);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        linearLayout.setOrientation(1);
        FrameLayout frameLayout = new FrameLayout(context);
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
        TextView textView = new TextView(context);
        textView.setText(str);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda57
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.m7648$r8$lambda$nlzYECJozcYJbbow6DpJdaL5Nk(view, motionEvent);
            }
        });
        LinearLayout linearLayout2 = new LinearLayout(context);
        linearLayout2.setGravity(17);
        linearLayout2.setOrientation(0);
        linearLayout2.setWeightSum(1.0f);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(1) - 149;
        calendar.setTimeInMillis(System.currentTimeMillis());
        final int i2 = calendar.get(5);
        final int i3 = calendar.get(2);
        final int i4 = calendar.get(1);
        final int i5 = i4 + 1;
        final Runnable runnable2 = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda58
            @Override // java.lang.Runnable
            public final void run() {
                AlertsCreator.$r8$lambda$OTN8bpMcc6piPJiLZbADABfJ5ZE(numberPicker3, i5, numberPicker, numberPicker2, i4, i3, i2);
            }
        };
        System.currentTimeMillis();
        TextView textView2 = new TextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.34
            @Override // android.widget.TextView, android.view.View
            public CharSequence getAccessibilityClassName() {
                return Button.class.getName();
            }
        };
        linearLayout2.addView(numberPicker, LayoutHelper.createLinear(0, 270, 0.25f));
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(31);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda59
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i6) {
                return AlertsCreator.m7612$r8$lambda$FP4erwHwtiIB_mgZnKnGyszzwk(i6);
            }
        });
        NumberPicker.OnScrollListener onScrollListener = new NumberPicker.OnScrollListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda60
            @Override // org.telegram.ui.Components.NumberPicker.OnScrollListener
            public final void onScrollStateChange(NumberPicker numberPicker4, int i6) {
                AlertsCreator.$r8$lambda$7QfsfYr3ylwGqY7KyDu5zfSlvds(runnable2, numberPicker4, i6);
            }
        };
        numberPicker.setOnScrollListener(onScrollListener);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(11);
        numberPicker2.setWrapSelectorWheel(false);
        linearLayout2.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.5f));
        numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda61
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i6) {
                return AlertsCreator.$r8$lambda$l68JnNulMt8crv7i_zbJiQsCKPA(i6);
            }
        });
        numberPicker2.setOnScrollListener(onScrollListener);
        numberPicker3.setMinValue(i);
        numberPicker3.setMaxValue(i5);
        numberPicker3.setWrapSelectorWheel(false);
        numberPicker3.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda62
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i6) {
                return AlertsCreator.$r8$lambda$W_hO4iKwJn8IsVT6wYJtLysdfhk(i5, i6);
            }
        });
        linearLayout2.addView(numberPicker3, LayoutHelper.createLinear(0, 270, 0.25f));
        numberPicker3.setOnScrollListener(onScrollListener);
        if (tL_birthday != null) {
            numberPicker.setValue(tL_birthday.day);
            numberPicker2.setValue(tL_birthday.month - 1);
            if ((tL_birthday.flags & 1) != 0) {
                numberPicker3.setValue(tL_birthday.year);
            } else {
                numberPicker3.setValue(i5);
            }
        } else {
            numberPicker.setValue(calendar.get(5));
            numberPicker2.setValue(calendar.get(2));
            numberPicker3.setValue(i5);
        }
        runnable2.run();
        if (runnable != null) {
            FrameLayout frameLayout2 = new FrameLayout(context);
            final LinkSpanDrawable.LinksTextView linksTextView = new LinkSpanDrawable.LinksTextView(context);
            linksTextView.setPadding(AndroidUtilities.m1146dp(8.0f), 0, AndroidUtilities.m1146dp(8.0f), 0);
            linksTextView.setTextSize(1, 13.0f);
            linksTextView.setTextColor(Theme.getColor(Theme.key_dialogTextGray2, resourcesProvider));
            linksTextView.setLinkTextColor(Theme.getColor(Theme.key_chat_messageLinkIn, resourcesProvider));
            linksTextView.setGravity(17);
            frameLayout2.addView(linksTextView, LayoutHelper.createFrame(-2, -2, 17));
            linearLayout.addView(frameLayout2, LayoutHelper.createLinear(-1, -2));
            final int i6 = UserConfig.selectedAccount;
            final Runnable runnable3 = new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda63
                @Override // java.lang.Runnable
                public final void run() {
                    AlertsCreator.m7655$r8$lambda$zVYdrTWZwag8w0eG1USiinovsA(i6, linksTextView);
                }
            };
            runnable3.run();
            f = 8.0f;
            NotificationCenter.getInstance(i6).listen(frameLayout2, NotificationCenter.privacyRulesUpdated, new Utilities.Callback() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda64
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    runnable3.run();
                }
            });
            ContactsController.getInstance(i6).loadPrivacySettings();
        } else {
            f = 8.0f;
        }
        if (z) {
            ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context, false, resourcesProvider);
            buttonWithCounterView.setText(LocaleController.getString(C2369R.string.DateOfBirthHideYear), false);
            buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda65
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertsCreator.$r8$lambda$iDDdkPpRvKFFJxY3QwNAJxWBO5s(numberPicker3, i5, runnable2, view);
                }
            });
            linearLayout.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 4));
        }
        textView2.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView2.setGravity(17);
        textView2.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText, resourcesProvider));
        textView2.setTextSize(1, 14.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setText(str2);
        textView2.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(f), Theme.getColor(Theme.key_featuredStickers_addButton, resourcesProvider), Theme.getColor(Theme.key_featuredStickers_addButtonPressed, resourcesProvider)));
        linearLayout.addView(textView2, LayoutHelper.createLinear(-1, 48, 83, 16, z ? 0 : 15, 16, 16));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda66
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.m7623$r8$lambda$Qml0CX_hcUX9fOglMEK9Y8Ffc(numberPicker, numberPicker2, numberPicker3, i5, builder, callback, view);
            }
        });
        builder.setCustomView(linearLayout);
        return builder;
    }

    /* renamed from: $r8$lambda$nlzYECJozcYJbbow6DpJdaL-5Nk, reason: not valid java name */
    public static /* synthetic */ boolean m7648$r8$lambda$nlzYECJozcYJbbow6DpJdaL5Nk(View view, MotionEvent motionEvent) {
        return true;
    }

    public static /* synthetic */ void $r8$lambda$OTN8bpMcc6piPJiLZbADABfJ5ZE(NumberPicker numberPicker, int i, NumberPicker numberPicker2, NumberPicker numberPicker3, int i2, int i3, int i4) {
        if (numberPicker.getValue() == i) {
            numberPicker2.setMinValue(1);
            try {
                numberPicker2.setMaxValue(YearMonth.m612of(2024, numberPicker3.getValue() + 1).lengthOfMonth());
            } catch (Exception e) {
                FileLog.m1160e(e);
                numberPicker2.setMaxValue(31);
            }
            numberPicker3.setMinValue(0);
            numberPicker3.setMaxValue(11);
            return;
        }
        if (numberPicker.getValue() == i2) {
            numberPicker3.setMinValue(0);
            numberPicker3.setMaxValue(i3);
            if (numberPicker3.getValue() == i3) {
                numberPicker2.setMinValue(1);
                numberPicker2.setMaxValue(i4);
                return;
            }
            numberPicker2.setMinValue(1);
            try {
                numberPicker2.setMaxValue(YearMonth.m612of(numberPicker.getValue(), numberPicker3.getValue() + 1).lengthOfMonth());
                return;
            } catch (Exception e2) {
                FileLog.m1160e(e2);
                numberPicker2.setMaxValue(31);
                return;
            }
        }
        numberPicker2.setMinValue(1);
        try {
            numberPicker2.setMaxValue(YearMonth.m612of(numberPicker.getValue(), numberPicker3.getValue() + 1).lengthOfMonth());
        } catch (Exception e3) {
            FileLog.m1160e(e3);
            numberPicker2.setMaxValue(31);
        }
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(11);
    }

    /* renamed from: $r8$lambda$FP4e-rwHwtiIB_mgZnKnGyszzwk, reason: not valid java name */
    public static /* synthetic */ String m7612$r8$lambda$FP4erwHwtiIB_mgZnKnGyszzwk(int i) {
        return "" + i;
    }

    public static /* synthetic */ void $r8$lambda$7QfsfYr3ylwGqY7KyDu5zfSlvds(Runnable runnable, NumberPicker numberPicker, int i) {
        if (i == 0) {
            runnable.run();
        }
    }

    public static /* synthetic */ String $r8$lambda$l68JnNulMt8crv7i_zbJiQsCKPA(int i) {
        switch (i) {
            case 0:
                return LocaleController.getString(C2369R.string.January);
            case 1:
                return LocaleController.getString(C2369R.string.February);
            case 2:
                return LocaleController.getString(C2369R.string.March);
            case 3:
                return LocaleController.getString(C2369R.string.April);
            case 4:
                return LocaleController.getString(C2369R.string.May);
            case 5:
                return LocaleController.getString(C2369R.string.June);
            case 6:
                return LocaleController.getString(C2369R.string.July);
            case 7:
                return LocaleController.getString(C2369R.string.August);
            case 8:
                return LocaleController.getString(C2369R.string.September);
            case 9:
                return LocaleController.getString(C2369R.string.October);
            case 10:
                return LocaleController.getString(C2369R.string.November);
            default:
                return LocaleController.getString(C2369R.string.December);
        }
    }

    public static /* synthetic */ String $r8$lambda$W_hO4iKwJn8IsVT6wYJtLysdfhk(int i, int i2) {
        return i2 == i ? "—" : String.format("%02d", Integer.valueOf(i2));
    }

    /* renamed from: $r8$lambda$zVYdrTWZwag8w0eG-1USiinovsA, reason: not valid java name */
    public static /* synthetic */ void m7655$r8$lambda$zVYdrTWZwag8w0eG1USiinovsA(int i, LinkSpanDrawable.LinksTextView linksTextView) {
        final ArrayList<TLRPC.PrivacyRule> privacyRules = ContactsController.getInstance(i).getPrivacyRules(11);
        String string = LocaleController.getString(C2369R.string.EditProfileBirthdayInfoContacts);
        if (privacyRules != null && !privacyRules.isEmpty()) {
            int i2 = 0;
            while (true) {
                if (i2 >= privacyRules.size()) {
                    break;
                }
                if (privacyRules.get(i2) instanceof TLRPC.TL_privacyValueAllowContacts) {
                    string = LocaleController.getString(C2369R.string.EditProfileBirthdayInfoContacts);
                    break;
                }
                if ((privacyRules.get(i2) instanceof TLRPC.TL_privacyValueAllowAll) || (privacyRules.get(i2) instanceof TLRPC.TL_privacyValueDisallowAll)) {
                    string = LocaleController.getString(C2369R.string.EditProfileBirthdayInfo);
                }
                i2++;
            }
        }
        linksTextView.setText(AndroidUtilities.replaceArrows(AndroidUtilities.replaceSingleTag(string, new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda119
            @Override // java.lang.Runnable
            public final void run() {
                AlertsCreator.$r8$lambda$1qW3yIwiCFAV9bnJAUoueMrDlNY(privacyRules);
            }
        }), true, AndroidUtilities.m1146dp(2.6666667f), AndroidUtilities.m1146dp(0.66f)));
    }

    public static /* synthetic */ void $r8$lambda$1qW3yIwiCFAV9bnJAUoueMrDlNY(ArrayList arrayList) {
        BaseFragment lastFragment;
        if (arrayList == null || (lastFragment = LaunchActivity.getLastFragment()) == null) {
            return;
        }
        BaseFragment.BottomSheetParams bottomSheetParams = new BaseFragment.BottomSheetParams();
        bottomSheetParams.transitionFromLeft = true;
        bottomSheetParams.allowNestedScroll = false;
        lastFragment.showAsSheet(new PrivacyControlActivity(11), bottomSheetParams);
    }

    public static /* synthetic */ void $r8$lambda$iDDdkPpRvKFFJxY3QwNAJxWBO5s(NumberPicker numberPicker, int i, Runnable runnable, View view) {
        numberPicker.setValue(i);
        runnable.run();
    }

    /* renamed from: $r8$lambda$Qml0CX_hcUX9fOg-lMEK-9Y8Ffc, reason: not valid java name */
    public static /* synthetic */ void m7623$r8$lambda$Qml0CX_hcUX9fOglMEK9Y8Ffc(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, int i, BottomSheet.Builder builder, Utilities.Callback callback, View view) {
        TL_account.TL_birthday tL_birthday = new TL_account.TL_birthday();
        tL_birthday.day = numberPicker.getValue();
        tL_birthday.month = numberPicker2.getValue() + 1;
        if (numberPicker3.getValue() != i) {
            tL_birthday.flags |= 1;
            tL_birthday.year = numberPicker3.getValue();
        }
        builder.getDismissRunnable().run();
        callback.run(tL_birthday);
    }

    public static BottomSheet.Builder createStatusUntilDatePickerDialog(Context context, long j, final StatusUntilDatePickerDelegate statusUntilDatePickerDelegate) {
        LinearLayout linearLayout;
        float f;
        if (context == null) {
            return null;
        }
        ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors();
        final BottomSheet.Builder builder = new BottomSheet.Builder(context, false);
        builder.setApplyBottomPadding(false);
        final NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker.setTextOffset(AndroidUtilities.m1146dp(10.0f));
        numberPicker.setItemCount(5);
        final NumberPicker numberPicker2 = new NumberPicker(context) { // from class: org.telegram.ui.Components.AlertsCreator.35
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i) {
                return LocaleController.formatPluralString("Hours", i, new Object[0]);
            }
        };
        numberPicker2.setItemCount(5);
        numberPicker2.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker2.setTextOffset(-AndroidUtilities.m1146dp(10.0f));
        final NumberPicker numberPicker3 = new NumberPicker(context) { // from class: org.telegram.ui.Components.AlertsCreator.36
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i) {
                return LocaleController.formatPluralString("Minutes", i, new Object[0]);
            }
        };
        numberPicker3.setItemCount(5);
        numberPicker3.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker3.setTextOffset(-AndroidUtilities.m1146dp(34.0f));
        LinearLayout linearLayout2 = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.37
            boolean ignoreLayout = false;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                this.ignoreLayout = true;
                Point point = AndroidUtilities.displaySize;
                int i3 = point.x > point.y ? 3 : 5;
                numberPicker.setItemCount(i3);
                numberPicker2.setItemCount(i3);
                numberPicker3.setItemCount(i3);
                numberPicker.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                numberPicker2.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                numberPicker3.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                this.ignoreLayout = false;
                super.onMeasure(i, i2);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        linearLayout2.setOrientation(1);
        FrameLayout frameLayout = new FrameLayout(context);
        linearLayout2.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
        TextView textView = new TextView(context);
        textView.setText(LocaleController.getString(C2369R.string.SetEmojiStatusUntilTitle));
        textView.setTextColor(scheduleDatePickerColors.textColor);
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda192
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.m7625$r8$lambda$VrQcevBm_OpLHISrisoeg8boQ(view, motionEvent);
            }
        });
        LinearLayout linearLayout3 = new LinearLayout(context);
        linearLayout3.setOrientation(0);
        linearLayout3.setWeightSum(1.0f);
        linearLayout2.addView(linearLayout3, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
        final long jCurrentTimeMillis = System.currentTimeMillis();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(jCurrentTimeMillis);
        final int i = calendar.get(1);
        final int i2 = calendar.get(6);
        TextView textView2 = new TextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.38
            @Override // android.widget.TextView, android.view.View
            public CharSequence getAccessibilityClassName() {
                return Button.class.getName();
            }
        };
        linearLayout3.addView(numberPicker, LayoutHelper.createLinear(0, 270, 0.5f));
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(365);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda193
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i3) {
                return AlertsCreator.m7615$r8$lambda$ItOyzx0K9OI2bSgVnG7xXb0OAM(jCurrentTimeMillis, calendar, i, i2, i3);
            }
        });
        NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda194
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker4, int i3, int i4) {
                AlertsCreator.checkScheduleDate(null, null, 0, numberPicker, numberPicker2, numberPicker3);
            }
        };
        numberPicker.setOnValueChangedListener(onValueChangeListener);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(23);
        linearLayout3.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.2f));
        numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda195
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i3) {
                return String.format("%02d", Integer.valueOf(i3));
            }
        });
        numberPicker2.setOnValueChangedListener(onValueChangeListener);
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(59);
        numberPicker3.setValue(0);
        numberPicker3.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda196
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i3) {
                return String.format("%02d", Integer.valueOf(i3));
            }
        });
        linearLayout3.addView(numberPicker3, LayoutHelper.createLinear(0, 270, 0.3f));
        numberPicker3.setOnValueChangedListener(onValueChangeListener);
        if (j <= 0 || j == 2147483646) {
            linearLayout = linearLayout2;
            f = 34.0f;
        } else {
            long j2 = 1000 * j;
            linearLayout = linearLayout2;
            f = 34.0f;
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            calendar.set(11, 0);
            int timeInMillis = (int) ((j2 - calendar.getTimeInMillis()) / 86400000);
            calendar.setTimeInMillis(j2);
            if (timeInMillis >= 0) {
                numberPicker3.setValue(calendar.get(12));
                numberPicker2.setValue(calendar.get(11));
                numberPicker.setValue(timeInMillis);
            }
        }
        checkScheduleDate(null, null, 0, numberPicker, numberPicker2, numberPicker3);
        textView2.setPadding(AndroidUtilities.m1146dp(f), 0, AndroidUtilities.m1146dp(f), 0);
        textView2.setGravity(17);
        textView2.setTextColor(scheduleDatePickerColors.buttonTextColor);
        textView2.setTextSize(1, 14.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(8.0f), scheduleDatePickerColors.buttonBackgroundColor, scheduleDatePickerColors.buttonBackgroundPressedColor));
        textView2.setText(LocaleController.getString(C2369R.string.SetEmojiStatusUntilButton));
        LinearLayout linearLayout4 = linearLayout;
        linearLayout4.addView(textView2, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda197
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.m7653$r8$lambda$xOuMTyTeFbWDkzF5sZezAaH0j4(numberPicker, numberPicker2, numberPicker3, calendar, statusUntilDatePickerDelegate, builder, view);
            }
        });
        builder.setCustomView(linearLayout4);
        BottomSheet bottomSheetShow = builder.show();
        bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
        bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
        return builder;
    }

    /* renamed from: $r8$lambda$VrQ-cevBm_OpLHISr-isoeg8boQ, reason: not valid java name */
    public static /* synthetic */ boolean m7625$r8$lambda$VrQcevBm_OpLHISrisoeg8boQ(View view, MotionEvent motionEvent) {
        return true;
    }

    /* renamed from: $r8$lambda$ItOyzx0K9OI2bSg-VnG7xXb0OAM, reason: not valid java name */
    public static /* synthetic */ String m7615$r8$lambda$ItOyzx0K9OI2bSgVnG7xXb0OAM(long j, Calendar calendar, int i, int i2, int i3) {
        if (i3 == 0) {
            return LocaleController.getString(C2369R.string.MessageScheduleToday);
        }
        long j2 = j + (i3 * 86400000);
        calendar.setTimeInMillis(j2);
        int i4 = calendar.get(1);
        int i5 = calendar.get(6);
        if (i4 != i || i5 >= i2 + 7) {
            if (i4 == i) {
                return LocaleController.getInstance().getFormatterScheduleDay().format(j2);
            }
            return LocaleController.getInstance().getFormatterScheduleYear().format(j2);
        }
        return LocaleController.getInstance().getFormatterWeek().format(j2) + ", " + LocaleController.getInstance().getFormatterScheduleDay().format(j2);
    }

    /* renamed from: $r8$lambda$xOuMTyTeFbWDkzF5sZezA-aH0j4, reason: not valid java name */
    public static /* synthetic */ void m7653$r8$lambda$xOuMTyTeFbWDkzF5sZezAaH0j4(NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, Calendar calendar, StatusUntilDatePickerDelegate statusUntilDatePickerDelegate, BottomSheet.Builder builder, View view) {
        boolean zCheckScheduleDate = checkScheduleDate(null, null, 0, numberPicker, numberPicker2, numberPicker3);
        calendar.setTimeInMillis(System.currentTimeMillis() + (numberPicker.getValue() * 86400000));
        calendar.set(11, numberPicker2.getValue());
        calendar.set(12, numberPicker3.getValue());
        if (zCheckScheduleDate) {
            calendar.set(13, 0);
        }
        statusUntilDatePickerDelegate.didSelectDate((int) (calendar.getTimeInMillis() / 1000));
        builder.getDismissRunnable().run();
    }

    public static BottomSheet.Builder createAutoDeleteDatePickerDialog(Context context, int i, Theme.ResourcesProvider resourcesProvider, final ScheduleDatePickerDelegate scheduleDatePickerDelegate) {
        if (context == null) {
            return null;
        }
        ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors(resourcesProvider);
        final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
        builder.setApplyBottomPadding(false);
        final int[] iArr = {0, 1440, 2880, 4320, 5760, 7200, 8640, 10080, 20160, 30240, 44640, 89280, 133920, 178560, 223200, 267840, 525600};
        final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.39
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i2) {
                int i3 = iArr[i2];
                if (i3 == 0) {
                    return LocaleController.getString(C2369R.string.AutoDeleteNever);
                }
                if (i3 < 10080) {
                    return LocaleController.formatPluralString("Days", i3 / 1440, new Object[0]);
                }
                if (i3 < 44640) {
                    return LocaleController.formatPluralString("Weeks", i3 / 1440, new Object[0]);
                }
                if (i3 < 525600) {
                    return LocaleController.formatPluralString("Months", i3 / 10080, new Object[0]);
                }
                return LocaleController.formatPluralString("Years", ((i3 * 5) / 31) * 1440, new Object[0]);
            }
        };
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(16);
        numberPicker.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker.setValue(0);
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda120
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i2) {
                return AlertsCreator.m7645$r8$lambda$kmDsmaZnAGqhxtGZozb3FVhG50(iArr, i2);
            }
        });
        LinearLayout linearLayout = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.40
            boolean ignoreLayout = false;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i2, int i3) {
                this.ignoreLayout = true;
                Point point = AndroidUtilities.displaySize;
                int i4 = point.x > point.y ? 3 : 5;
                numberPicker.setItemCount(i4);
                numberPicker.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i4;
                this.ignoreLayout = false;
                super.onMeasure(i2, i3);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        boolean z = true;
        linearLayout.setOrientation(1);
        FrameLayout frameLayout = new FrameLayout(context);
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
        TextView textView = new TextView(context);
        textView.setText(LocaleController.getString(C2369R.string.AutoDeleteAfteTitle));
        textView.setTextColor(scheduleDatePickerColors.textColor);
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda121
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.$r8$lambda$XkguCgcVv26AxMRK93EN0PWW84k(view, motionEvent);
            }
        });
        LinearLayout linearLayout2 = new LinearLayout(context);
        linearLayout2.setOrientation(0);
        linearLayout2.setWeightSum(1.0f);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
        final AnimatedTextView animatedTextView = new AnimatedTextView(context, z, z, false) { // from class: org.telegram.ui.Components.AlertsCreator.41
            @Override // android.view.View
            public CharSequence getAccessibilityClassName() {
                return Button.class.getName();
            }
        };
        linearLayout2.addView(numberPicker, LayoutHelper.createLinear(0, 270, 1.0f));
        animatedTextView.setPadding(0, 0, 0, 0);
        animatedTextView.setGravity(17);
        animatedTextView.setTextColor(scheduleDatePickerColors.buttonTextColor);
        animatedTextView.setTextSize(AndroidUtilities.m1146dp(14.0f));
        animatedTextView.setTypeface(AndroidUtilities.bold());
        animatedTextView.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(8.0f), scheduleDatePickerColors.buttonBackgroundColor, scheduleDatePickerColors.buttonBackgroundPressedColor));
        linearLayout.addView(animatedTextView, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
        animatedTextView.setText(LocaleController.getString(C2369R.string.DisableAutoDeleteTimer));
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda122
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker2, int i2, int i3) {
                AlertsCreator.m7636$r8$lambda$aJTTkRfo7eEmoI5QW7RI8gvo1g(animatedTextView, numberPicker2, i2, i3);
            }
        });
        animatedTextView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda123
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.$r8$lambda$WlrQAiZDRhttTy5UFtJTkfaAzGg(iArr, numberPicker, scheduleDatePickerDelegate, builder, view);
            }
        });
        builder.setCustomView(linearLayout);
        BottomSheet bottomSheetShow = builder.show();
        bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
        bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
        return builder;
    }

    /* renamed from: $r8$lambda$kmDsmaZnAGqhxtGZozb3F-VhG50, reason: not valid java name */
    public static /* synthetic */ String m7645$r8$lambda$kmDsmaZnAGqhxtGZozb3FVhG50(int[] iArr, int i) {
        int i2 = iArr[i];
        if (i2 == 0) {
            return LocaleController.getString(C2369R.string.AutoDeleteNever);
        }
        if (i2 < 10080) {
            return LocaleController.formatPluralString("Days", i2 / 1440, new Object[0]);
        }
        if (i2 < 44640) {
            return LocaleController.formatPluralString("Weeks", i2 / 10080, new Object[0]);
        }
        if (i2 < 525600) {
            return LocaleController.formatPluralString("Months", i2 / 44640, new Object[0]);
        }
        return LocaleController.formatPluralString("Years", i2 / 525600, new Object[0]);
    }

    public static /* synthetic */ boolean $r8$lambda$XkguCgcVv26AxMRK93EN0PWW84k(View view, MotionEvent motionEvent) {
        return true;
    }

    /* renamed from: $r8$lambda$aJTTkRfo7eEmoI5QW7RI8gvo-1g, reason: not valid java name */
    public static /* synthetic */ void m7636$r8$lambda$aJTTkRfo7eEmoI5QW7RI8gvo1g(AnimatedTextView animatedTextView, NumberPicker numberPicker, int i, int i2) {
        try {
            if (i2 == 0) {
                animatedTextView.setText(LocaleController.getString(C2369R.string.DisableAutoDeleteTimer));
            } else {
                animatedTextView.setText(LocaleController.getString(C2369R.string.SetAutoDeleteTimer));
            }
        } catch (Exception unused) {
        }
    }

    public static /* synthetic */ void $r8$lambda$WlrQAiZDRhttTy5UFtJTkfaAzGg(int[] iArr, NumberPicker numberPicker, ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, View view) {
        scheduleDatePickerDelegate.didSelectDate(true, iArr[numberPicker.getValue()], 0);
        builder.getDismissRunnable().run();
    }

    public static BottomSheet.Builder createSoundFrequencyPickerDialog(Context context, int i, int i2, final SoundFrequencyDelegate soundFrequencyDelegate, Theme.ResourcesProvider resourcesProvider) {
        if (context == null) {
            return null;
        }
        ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors(resourcesProvider);
        final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
        builder.setApplyBottomPadding(false);
        final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.42
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i3) {
                return LocaleController.formatPluralString("Times", i3 + 1, new Object[0]);
            }
        };
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(10);
        numberPicker.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker.setValue(i - 1);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda186
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i3) {
                return LocaleController.formatPluralString("Times", i3 + 1, new Object[0]);
            }
        });
        final NumberPicker numberPicker2 = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.43
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i3) {
                return LocaleController.formatPluralString("Times", i3 + 1, new Object[0]);
            }
        };
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(10);
        numberPicker2.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker2.setValue((i2 / 60) - 1);
        numberPicker2.setWrapSelectorWheel(false);
        numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda187
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i3) {
                return LocaleController.formatPluralString("Minutes", i3 + 1, new Object[0]);
            }
        });
        final NumberPicker numberPicker3 = new NumberPicker(context, resourcesProvider);
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(0);
        numberPicker3.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker3.setValue(0);
        numberPicker3.setWrapSelectorWheel(false);
        numberPicker3.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda188
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i3) {
                return LocaleController.getString(C2369R.string.NotificationsFrequencyDivider);
            }
        });
        LinearLayout linearLayout = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.44
            boolean ignoreLayout = false;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i3, int i4) {
                this.ignoreLayout = true;
                Point point = AndroidUtilities.displaySize;
                int i5 = point.x > point.y ? 3 : 5;
                numberPicker.setItemCount(i5);
                numberPicker.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i5;
                numberPicker2.setItemCount(i5);
                numberPicker2.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i5;
                numberPicker3.setItemCount(i5);
                numberPicker3.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i5;
                this.ignoreLayout = false;
                super.onMeasure(i3, i4);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        linearLayout.setOrientation(1);
        FrameLayout frameLayout = new FrameLayout(context);
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
        TextView textView = new TextView(context);
        textView.setText(LocaleController.getString(C2369R.string.NotfificationsFrequencyTitle));
        textView.setTextColor(scheduleDatePickerColors.textColor);
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda189
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.$r8$lambda$ZRLUkKmrvfo2d37zNZVLY1N6BU8(view, motionEvent);
            }
        });
        LinearLayout linearLayout2 = new LinearLayout(context);
        linearLayout2.setOrientation(0);
        linearLayout2.setWeightSum(1.0f);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
        TextView textView2 = new TextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.45
            @Override // android.widget.TextView, android.view.View
            public CharSequence getAccessibilityClassName() {
                return Button.class.getName();
            }
        };
        linearLayout2.addView(numberPicker, LayoutHelper.createLinear(0, 270, 0.4f));
        linearLayout2.addView(numberPicker3, LayoutHelper.createLinear(0, -2, 0.2f, 16));
        linearLayout2.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.4f));
        textView2.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView2.setGravity(17);
        textView2.setTextColor(scheduleDatePickerColors.buttonTextColor);
        textView2.setTextSize(1, 14.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(8.0f), scheduleDatePickerColors.buttonBackgroundColor, scheduleDatePickerColors.buttonBackgroundPressedColor));
        textView2.setText(LocaleController.getString(C2369R.string.AutoDeleteConfirm));
        linearLayout.addView(textView2, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
        NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda190
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker4, int i3, int i4) {
                AlertsCreator.m7591$r8$lambda$0wbdN_EFsWwmt5_SxLLrc9vIo(numberPicker4, i3, i4);
            }
        };
        numberPicker.setOnValueChangedListener(onValueChangeListener);
        numberPicker2.setOnValueChangedListener(onValueChangeListener);
        textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda191
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.m7641$r8$lambda$flz86ppsO3f2XfKpeCy812Ywks(numberPicker, numberPicker2, soundFrequencyDelegate, builder, view);
            }
        });
        builder.setCustomView(linearLayout);
        BottomSheet bottomSheetShow = builder.show();
        bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
        bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
        return builder;
    }

    public static /* synthetic */ boolean $r8$lambda$ZRLUkKmrvfo2d37zNZVLY1N6BU8(View view, MotionEvent motionEvent) {
        return true;
    }

    /* renamed from: $r8$lambda$fl-z86ppsO3f2XfKpeCy812Ywks, reason: not valid java name */
    public static /* synthetic */ void m7641$r8$lambda$flz86ppsO3f2XfKpeCy812Ywks(NumberPicker numberPicker, NumberPicker numberPicker2, SoundFrequencyDelegate soundFrequencyDelegate, BottomSheet.Builder builder, View view) {
        soundFrequencyDelegate.didSelectValues(numberPicker.getValue() + 1, (numberPicker2.getValue() + 1) * 60);
        builder.getDismissRunnable().run();
    }

    public static BottomSheet.Builder createMuteForPickerDialog(Context context, Theme.ResourcesProvider resourcesProvider, final ScheduleDatePickerDelegate scheduleDatePickerDelegate) {
        if (context == null) {
            return null;
        }
        ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors(resourcesProvider);
        final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
        builder.setApplyBottomPadding(false);
        final int[] iArr = {30, 60, Opcodes.ISHL, Opcodes.GETFIELD, 480, 1440, 2880, 4320, 5760, 7200, 8640, 10080, 20160, 30240, 44640, 89280, 133920, 178560, 223200, 267840, 525600};
        final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.46
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i) {
                int i2 = iArr[i];
                if (i2 == 0) {
                    return LocaleController.getString(C2369R.string.MuteNever);
                }
                if (i2 < 60) {
                    return LocaleController.formatPluralString("Minutes", i2, new Object[0]);
                }
                if (i2 < 1440) {
                    return LocaleController.formatPluralString("Hours", i2 / 60, new Object[0]);
                }
                if (i2 < 10080) {
                    return LocaleController.formatPluralString("Days", i2 / 1440, new Object[0]);
                }
                if (i2 < 44640) {
                    return LocaleController.formatPluralString("Weeks", i2 / 10080, new Object[0]);
                }
                if (i2 < 525600) {
                    return LocaleController.formatPluralString("Months", i2 / 44640, new Object[0]);
                }
                return LocaleController.formatPluralString("Years", i2 / 525600, new Object[0]);
            }
        };
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(20);
        numberPicker.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker.setValue(0);
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda97
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i) {
                return AlertsCreator.$r8$lambda$osWcCg51XOXjPGjvKEewN0Ghw38(iArr, i);
            }
        });
        LinearLayout linearLayout = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.47
            boolean ignoreLayout = false;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                this.ignoreLayout = true;
                Point point = AndroidUtilities.displaySize;
                int i3 = point.x > point.y ? 3 : 5;
                numberPicker.setItemCount(i3);
                numberPicker.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                this.ignoreLayout = false;
                super.onMeasure(i, i2);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        linearLayout.setOrientation(1);
        FrameLayout frameLayout = new FrameLayout(context);
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
        TextView textView = new TextView(context);
        textView.setText(LocaleController.getString(C2369R.string.MuteForAlert));
        textView.setTextColor(scheduleDatePickerColors.textColor);
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda98
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.$r8$lambda$KlyYp47ymkg1I4twB7zijaL0cSA(view, motionEvent);
            }
        });
        LinearLayout linearLayout2 = new LinearLayout(context);
        linearLayout2.setOrientation(0);
        linearLayout2.setWeightSum(1.0f);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
        TextView textView2 = new TextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.48
            @Override // android.widget.TextView, android.view.View
            public CharSequence getAccessibilityClassName() {
                return Button.class.getName();
            }
        };
        linearLayout2.addView(numberPicker, LayoutHelper.createLinear(0, 270, 1.0f));
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda99
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker2, int i, int i2) {
                AlertsCreator.$r8$lambda$Hq5xQJssvd9l_d8xzFbBshNHewU(numberPicker2, i, i2);
            }
        });
        textView2.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView2.setGravity(17);
        textView2.setTextColor(scheduleDatePickerColors.buttonTextColor);
        textView2.setTextSize(1, 14.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(8.0f), scheduleDatePickerColors.buttonBackgroundColor, scheduleDatePickerColors.buttonBackgroundPressedColor));
        textView2.setText(LocaleController.getString(C2369R.string.AutoDeleteConfirm));
        linearLayout.addView(textView2, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda100
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.$r8$lambda$dEUSQb0hWKeTrwcJ0ybQiPGMzvA(iArr, numberPicker, scheduleDatePickerDelegate, builder, view);
            }
        });
        builder.setCustomView(linearLayout);
        BottomSheet bottomSheetShow = builder.show();
        bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
        bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
        return builder;
    }

    public static /* synthetic */ String $r8$lambda$osWcCg51XOXjPGjvKEewN0Ghw38(int[] iArr, int i) {
        int i2 = iArr[i];
        if (i2 == 0) {
            return LocaleController.getString(C2369R.string.MuteNever);
        }
        if (i2 < 60) {
            return LocaleController.formatPluralString("Minutes", i2, new Object[0]);
        }
        if (i2 < 1440) {
            return LocaleController.formatPluralString("Hours", i2 / 60, new Object[0]);
        }
        if (i2 < 10080) {
            return LocaleController.formatPluralString("Days", i2 / 1440, new Object[0]);
        }
        if (i2 < 44640) {
            return LocaleController.formatPluralString("Weeks", i2 / 10080, new Object[0]);
        }
        if (i2 < 525600) {
            return LocaleController.formatPluralString("Months", i2 / 44640, new Object[0]);
        }
        return LocaleController.formatPluralString("Years", i2 / 525600, new Object[0]);
    }

    public static /* synthetic */ boolean $r8$lambda$KlyYp47ymkg1I4twB7zijaL0cSA(View view, MotionEvent motionEvent) {
        return true;
    }

    public static /* synthetic */ void $r8$lambda$dEUSQb0hWKeTrwcJ0ybQiPGMzvA(int[] iArr, NumberPicker numberPicker, ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, View view) {
        scheduleDatePickerDelegate.didSelectDate(true, iArr[numberPicker.getValue()] * 60, 0);
        builder.getDismissRunnable().run();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkCalendarDate(long j, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        int i = 1;
        int i2 = calendar.get(1);
        int i3 = calendar.get(2);
        int i4 = calendar.get(5);
        calendar.setTimeInMillis(System.currentTimeMillis());
        int i5 = calendar.get(1);
        int i6 = calendar.get(2);
        int i7 = calendar.get(5);
        numberPicker3.setMaxValue(i5);
        numberPicker3.setMinValue(i2);
        int value = numberPicker3.getValue();
        numberPicker2.setMaxValue(value == i5 ? i6 : 11);
        numberPicker2.setMinValue(value == i2 ? i3 : 0);
        int value2 = numberPicker2.getValue();
        calendar.set(1, value);
        calendar.set(2, value2);
        int actualMaximum = calendar.getActualMaximum(5);
        if (value == i5 && value2 == i6) {
            actualMaximum = Math.min(i7, actualMaximum);
        }
        numberPicker.setMaxValue(actualMaximum);
        if (value == i2 && value2 == i3) {
            i = i4;
        }
        numberPicker.setMinValue(i);
    }

    public static BottomSheet.Builder createCalendarPickerDialog(Context context, final long j, final MessagesStorage.IntCallback intCallback, Theme.ResourcesProvider resourcesProvider) {
        if (context == null) {
            return null;
        }
        final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
        builder.setApplyBottomPadding(false);
        final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider);
        numberPicker.setTextOffset(AndroidUtilities.m1146dp(10.0f));
        numberPicker.setItemCount(5);
        final NumberPicker numberPicker2 = new NumberPicker(context, resourcesProvider);
        numberPicker2.setItemCount(5);
        numberPicker2.setTextOffset(-AndroidUtilities.m1146dp(10.0f));
        final NumberPicker numberPicker3 = new NumberPicker(context, resourcesProvider);
        numberPicker3.setItemCount(5);
        numberPicker3.setTextOffset(-AndroidUtilities.m1146dp(24.0f));
        LinearLayout linearLayout = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.49
            boolean ignoreLayout = false;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i, int i2) {
                this.ignoreLayout = true;
                Point point = AndroidUtilities.displaySize;
                int i3 = point.x > point.y ? 3 : 5;
                numberPicker.setItemCount(i3);
                numberPicker2.setItemCount(i3);
                numberPicker3.setItemCount(i3);
                numberPicker.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                numberPicker2.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                numberPicker3.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i3;
                this.ignoreLayout = false;
                super.onMeasure(i, i2);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        linearLayout.setOrientation(1);
        FrameLayout frameLayout = new FrameLayout(context);
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
        TextView textView = new TextView(context);
        textView.setText(LocaleController.getString(C2369R.string.ChooseDate));
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda50
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.$r8$lambda$qeZJL6Gu4XPFHQQBZRoJmqFi9Kc(view, motionEvent);
            }
        });
        LinearLayout linearLayout2 = new LinearLayout(context);
        linearLayout2.setOrientation(0);
        linearLayout2.setWeightSum(1.0f);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
        System.currentTimeMillis();
        TextView textView2 = new TextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.50
            @Override // android.widget.TextView, android.view.View
            public CharSequence getAccessibilityClassName() {
                return Button.class.getName();
            }
        };
        linearLayout2.addView(numberPicker, LayoutHelper.createLinear(0, 270, 0.25f));
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(31);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda51
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i) {
                return AlertsCreator.$r8$lambda$fMA3J3U7wYcTvHF4qHnb_Mk4BF4(i);
            }
        });
        NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda52
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker4, int i, int i2) {
                AlertsCreator.checkCalendarDate(j, numberPicker, numberPicker2, numberPicker3);
            }
        };
        numberPicker.setOnValueChangedListener(onValueChangeListener);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(11);
        numberPicker2.setWrapSelectorWheel(false);
        linearLayout2.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.5f));
        numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda53
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i) {
                return AlertsCreator.$r8$lambda$KQyI_Zvww1kyzfBNbEh_U89v2qo(i);
            }
        });
        numberPicker2.setOnValueChangedListener(onValueChangeListener);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        int i = calendar.get(1);
        calendar.setTimeInMillis(System.currentTimeMillis());
        int i2 = calendar.get(1);
        numberPicker3.setMinValue(i);
        numberPicker3.setMaxValue(i2);
        numberPicker3.setWrapSelectorWheel(false);
        numberPicker3.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda54
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i3) {
                return String.format("%02d", Integer.valueOf(i3));
            }
        });
        linearLayout2.addView(numberPicker3, LayoutHelper.createLinear(0, 270, 0.25f));
        numberPicker3.setOnValueChangedListener(onValueChangeListener);
        numberPicker.setValue(31);
        numberPicker2.setValue(12);
        numberPicker3.setValue(i2);
        checkCalendarDate(j, numberPicker, numberPicker2, numberPicker3);
        textView2.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView2.setGravity(17);
        textView2.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText, resourcesProvider));
        textView2.setTextSize(1, 14.0f);
        textView2.setTypeface(AndroidUtilities.bold());
        textView2.setText(LocaleController.getString(C2369R.string.JumpToDate));
        textView2.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(8.0f), Theme.getColor(Theme.key_featuredStickers_addButton, resourcesProvider), Theme.getColor(Theme.key_featuredStickers_addButtonPressed, resourcesProvider)));
        linearLayout.addView(textView2, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 16));
        textView2.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda55
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.$r8$lambda$f66B_DVKx0oylfBCdbOQsNMOdcU(j, numberPicker, numberPicker2, numberPicker3, calendar, intCallback, builder, view);
            }
        });
        builder.setCustomView(linearLayout);
        return builder;
    }

    public static /* synthetic */ boolean $r8$lambda$qeZJL6Gu4XPFHQQBZRoJmqFi9Kc(View view, MotionEvent motionEvent) {
        return true;
    }

    public static /* synthetic */ String $r8$lambda$fMA3J3U7wYcTvHF4qHnb_Mk4BF4(int i) {
        return "" + i;
    }

    public static /* synthetic */ String $r8$lambda$KQyI_Zvww1kyzfBNbEh_U89v2qo(int i) {
        switch (i) {
            case 0:
                return LocaleController.getString(C2369R.string.January);
            case 1:
                return LocaleController.getString(C2369R.string.February);
            case 2:
                return LocaleController.getString(C2369R.string.March);
            case 3:
                return LocaleController.getString(C2369R.string.April);
            case 4:
                return LocaleController.getString(C2369R.string.May);
            case 5:
                return LocaleController.getString(C2369R.string.June);
            case 6:
                return LocaleController.getString(C2369R.string.July);
            case 7:
                return LocaleController.getString(C2369R.string.August);
            case 8:
                return LocaleController.getString(C2369R.string.September);
            case 9:
                return LocaleController.getString(C2369R.string.October);
            case 10:
                return LocaleController.getString(C2369R.string.November);
            default:
                return LocaleController.getString(C2369R.string.December);
        }
    }

    public static /* synthetic */ void $r8$lambda$f66B_DVKx0oylfBCdbOQsNMOdcU(long j, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, Calendar calendar, MessagesStorage.IntCallback intCallback, BottomSheet.Builder builder, View view) {
        checkCalendarDate(j, numberPicker, numberPicker2, numberPicker3);
        calendar.set(1, numberPicker3.getValue());
        calendar.set(2, numberPicker2.getValue());
        calendar.set(5, numberPicker.getValue());
        calendar.set(12, 0);
        calendar.set(11, 0);
        calendar.set(13, 0);
        intCallback.run((int) (calendar.getTimeInMillis() / 1000));
        builder.getDismissRunnable().run();
    }

    public static BottomSheet createMuteAlert(final BaseFragment baseFragment, final long j, final long j2, final Theme.ResourcesProvider resourcesProvider) {
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return null;
        }
        BottomSheet.Builder builder = new BottomSheet.Builder(baseFragment.getParentActivity(), false, resourcesProvider);
        builder.setTitle(LocaleController.getString(C2369R.string.Notifications), true);
        builder.setItems(new CharSequence[]{LocaleController.formatString("MuteFor", C2369R.string.MuteFor, LocaleController.formatPluralString("Hours", 1, new Object[0])), LocaleController.formatString("MuteFor", C2369R.string.MuteFor, LocaleController.formatPluralString("Hours", 8, new Object[0])), LocaleController.formatString("MuteFor", C2369R.string.MuteFor, LocaleController.formatPluralString("Days", 2, new Object[0])), LocaleController.getString(C2369R.string.MuteDisable)}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda49
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                AlertsCreator.m7610$r8$lambda$EjV0XTv62X9unNyac19mMct39k(j, j2, baseFragment, resourcesProvider, dialogInterface, i);
            }
        });
        return builder.create();
    }

    /* renamed from: $r8$lambda$EjV0XTv62X9unNyac19mMc-t39k, reason: not valid java name */
    public static /* synthetic */ void m7610$r8$lambda$EjV0XTv62X9unNyac19mMct39k(long j, long j2, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider, DialogInterface dialogInterface, int i) {
        int i2 = i == 0 ? 0 : i == 1 ? 1 : i == 2 ? 2 : 3;
        NotificationsController.getInstance(UserConfig.selectedAccount).setDialogNotificationsSettings(j, j2, i2);
        if (BulletinFactory.canShowBulletin(baseFragment)) {
            BulletinFactory.createMuteBulletin(baseFragment, i2, 0, resourcesProvider).show();
        }
    }

    public static BottomSheet createMuteAlert(final BaseFragment baseFragment, final ArrayList arrayList, final int i, final Theme.ResourcesProvider resourcesProvider) {
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return null;
        }
        BottomSheet.Builder builder = new BottomSheet.Builder(baseFragment.getParentActivity(), false, resourcesProvider);
        builder.setTitle(LocaleController.getString(C2369R.string.Notifications), true);
        builder.setItems(new CharSequence[]{LocaleController.formatString("MuteFor", C2369R.string.MuteFor, LocaleController.formatPluralString("Hours", 1, new Object[0])), LocaleController.formatString("MuteFor", C2369R.string.MuteFor, LocaleController.formatPluralString("Hours", 8, new Object[0])), LocaleController.formatString("MuteFor", C2369R.string.MuteFor, LocaleController.formatPluralString("Days", 2, new Object[0])), LocaleController.getString(C2369R.string.MuteDisable)}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda101
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                AlertsCreator.$r8$lambda$krChoqO3jicd0wg4UEpoMvO4Jak(arrayList, i, baseFragment, resourcesProvider, dialogInterface, i2);
            }
        });
        return builder.create();
    }

    public static /* synthetic */ void $r8$lambda$krChoqO3jicd0wg4UEpoMvO4Jak(ArrayList arrayList, int i, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider, DialogInterface dialogInterface, int i2) {
        int i3 = i2 == 0 ? 0 : i2 == 1 ? 1 : i2 == 2 ? 2 : 3;
        if (arrayList != null) {
            for (int i4 = 0; i4 < arrayList.size(); i4++) {
                NotificationsController.getInstance(UserConfig.selectedAccount).setDialogNotificationsSettings(((Long) arrayList.get(i4)).longValue(), i, i3);
            }
        }
        if (BulletinFactory.canShowBulletin(baseFragment)) {
            BulletinFactory.createMuteBulletin(baseFragment, i3, 0, resourcesProvider).show();
        }
    }

    public static void createReportPhotoAlert(final int i, final Context context, final long j, final TLRPC.Photo photo, final Theme.ResourcesProvider resourcesProvider) {
        if (context == null || photo == null) {
            return;
        }
        final Utilities.Callback2 callback2 = new Utilities.Callback2() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda37
            @Override // org.telegram.messenger.Utilities.Callback2
            public final void run(Object obj, Object obj2) {
                AlertsCreator.m7644$r8$lambda$kCAJw4rWGPa7Q3kpe2Cq5WL0dk(i, j, photo, context, resourcesProvider, (Integer) obj, (String) obj2);
            }
        };
        BottomSheet.Builder builder = new BottomSheet.Builder(context, true, resourcesProvider);
        builder.setTitle(LocaleController.getString(C2369R.string.ReportProfilePhoto), true);
        final int[] iArr = {0, 6, 1, 2, 3, 4, 5, 100};
        builder.setItems(new CharSequence[]{LocaleController.getString(C2369R.string.ReportChatSpam), LocaleController.getString(C2369R.string.ReportChatFakeAccount), LocaleController.getString(C2369R.string.ReportChatViolence), LocaleController.getString(C2369R.string.ReportChatChild), LocaleController.getString(C2369R.string.ReportChatIllegalDrugs), LocaleController.getString(C2369R.string.ReportChatPersonalDetails), LocaleController.getString(C2369R.string.ReportChatPornography), LocaleController.getString(C2369R.string.ReportChatOther)}, new int[]{C2369R.drawable.msg_clearcache, C2369R.drawable.msg_report_fake, C2369R.drawable.msg_report_violence, C2369R.drawable.msg_block2, C2369R.drawable.msg_report_drugs, C2369R.drawable.msg_report_personal, C2369R.drawable.msg_report_xxx, C2369R.drawable.msg_report_other}, new DialogInterface.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda38
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i2) {
                AlertsCreator.$r8$lambda$B3b17nVFkRdafJEq1BVs6gISrH0(iArr, context, resourcesProvider, callback2, dialogInterface, i2);
            }
        });
        builder.show();
    }

    /* renamed from: $r8$lambda$kCAJw-4rWGPa7Q3kpe2Cq5WL0dk, reason: not valid java name */
    public static /* synthetic */ void m7644$r8$lambda$kCAJw4rWGPa7Q3kpe2Cq5WL0dk(int i, long j, TLRPC.Photo photo, Context context, Theme.ResourcesProvider resourcesProvider, Integer num, String str) {
        TL_account.reportProfilePhoto reportprofilephoto = new TL_account.reportProfilePhoto();
        reportprofilephoto.peer = MessagesController.getInstance(i).getInputPeer(j);
        TLRPC.TL_inputPhoto tL_inputPhoto = new TLRPC.TL_inputPhoto();
        tL_inputPhoto.f1595id = photo.f1603id;
        tL_inputPhoto.file_reference = photo.file_reference;
        tL_inputPhoto.access_hash = photo.access_hash;
        reportprofilephoto.photo_id = tL_inputPhoto;
        reportprofilephoto.message = "";
        if (num.intValue() == 0) {
            reportprofilephoto.reason = new TLRPC.TL_inputReportReasonSpam();
        } else if (num.intValue() == 1) {
            reportprofilephoto.reason = new TLRPC.TL_inputReportReasonViolence();
        } else if (num.intValue() == 2) {
            reportprofilephoto.reason = new TLRPC.TL_inputReportReasonChildAbuse();
        } else if (num.intValue() == 5) {
            reportprofilephoto.reason = new TLRPC.TL_inputReportReasonPornography();
        } else if (num.intValue() == 3) {
            reportprofilephoto.reason = new TLRPC.TL_inputReportReasonIllegalDrugs();
        } else if (num.intValue() == 4) {
            reportprofilephoto.reason = new TLRPC.TL_inputReportReasonPersonalDetails();
        }
        ConnectionsManager.getInstance(i).sendRequest(reportprofilephoto, null);
        BulletinFactory.m1266of(Bulletin.BulletinWindow.make(context), resourcesProvider).createReportSent(resourcesProvider).show();
    }

    public static /* synthetic */ void $r8$lambda$B3b17nVFkRdafJEq1BVs6gISrH0(int[] iArr, Context context, Theme.ResourcesProvider resourcesProvider, final Utilities.Callback2 callback2, DialogInterface dialogInterface, int i) {
        int i2 = iArr[i];
        if (i2 == 100) {
            new ReportAlert(context, i2, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.51
                @Override // org.telegram.p023ui.Components.ReportAlert
                protected void onSend(int i3, String str) {
                    callback2.run(Integer.valueOf(i3), str);
                }
            }.show();
        } else {
            callback2.run(Integer.valueOf(i2), "");
        }
    }

    private static String getFloodWaitString(String str) {
        String pluralString;
        int iIntValue = Utilities.parseInt((CharSequence) str).intValue();
        if (iIntValue < 60) {
            pluralString = LocaleController.formatPluralString("Seconds", iIntValue, new Object[0]);
        } else {
            pluralString = LocaleController.formatPluralString("Minutes", iIntValue / 60, new Object[0]);
        }
        return LocaleController.formatString("FloodWaitTime", C2369R.string.FloodWaitTime, pluralString);
    }

    public static void showFloodWaitAlert(String str, BaseFragment baseFragment) {
        String pluralString;
        if (str == null || !str.startsWith("FLOOD_WAIT") || baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        int iIntValue = Utilities.parseInt((CharSequence) str).intValue();
        if (iIntValue < 60) {
            pluralString = LocaleController.formatPluralString("Seconds", iIntValue, new Object[0]);
        } else {
            pluralString = LocaleController.formatPluralString("Minutes", iIntValue / 60, new Object[0]);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity());
        builder.setTitle(LocaleController.getString(C2369R.string.AppName));
        builder.setMessage(LocaleController.formatString("FloodWaitTime", C2369R.string.FloodWaitTime, pluralString));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), null);
        baseFragment.showDialog(builder.create(), true, null);
    }

    public static void showSendMediaAlert(int i, BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
        if (i == 0 || baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(baseFragment.getParentActivity(), resourcesProvider);
        builder.setTitle(LocaleController.getString(C2369R.string.UnableForward));
        if (i == 1) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedStickers));
        } else if (i == 2) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedMedia));
        } else if (i == 3) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedPolls));
        } else if (i == 4) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedStickersAll));
        } else if (i == 5) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedMediaAll));
        } else if (i == 6) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedPollsAll));
        } else if (i == 7) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedPrivacyVoiceMessages));
        } else if (i == 8) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedPrivacyVideoMessages));
        } else if (i == 9) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedVideoAll));
        } else if (i == 10) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedPhotoAll));
        } else if (i == 11) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedVideo));
        } else if (i == 12) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedPhoto));
        } else if (i == 13) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedVoiceAll));
        } else if (i == 14) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedVoice));
        } else if (i == 15) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedRoundAll));
        } else if (i == 16) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedRound));
        } else if (i == 17) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedDocumentsAll));
        } else if (i == 18) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedDocuments));
        } else if (i == 19) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedMusicAll));
        } else if (i == 20) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedMusic));
        } else if (i == 21) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedTodoAll));
        } else if (i == 22) {
            builder.setMessage(LocaleController.getString(C2369R.string.ErrorSendRestrictedTodo));
        }
        builder.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), null);
        baseFragment.showDialog(builder.create(), true, null);
    }

    public static void showAddUserAlert(String str, final BaseFragment baseFragment, boolean z, TLObject tLObject) {
        AlertDialog.Builder builder;
        if (str == null || baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        builder = new AlertDialog.Builder(baseFragment.getParentActivity());
        builder.setTitle(LocaleController.getString(C2369R.string.AppName));
        switch (str) {
            case "CHANNELS_ADMIN_LOCATED_TOO_MUCH":
                builder.setMessage(LocaleController.getString(C2369R.string.LocatedChannelsTooMuch));
                break;
            case "CHANNELS_ADMIN_PUBLIC_TOO_MUCH":
                builder.setMessage(LocaleController.getString(C2369R.string.PublicChannelsTooMuch));
                break;
            case "USERS_TOO_FEW":
                builder.setMessage(LocaleController.getString(C2369R.string.CreateGroupError));
                break;
            case "USER_BLOCKED":
            case "USER_BOT":
            case "USER_ID_INVALID":
                if (z) {
                    builder.setMessage(LocaleController.getString(C2369R.string.ChannelUserCantAdd));
                    break;
                } else {
                    builder.setMessage(LocaleController.getString(C2369R.string.GroupUserCantAdd));
                    break;
                }
            case "USER_RESTRICTED":
                builder.setMessage(LocaleController.getString(C2369R.string.UserRestricted));
                break;
            case "PEER_FLOOD":
                builder.setMessage(LocaleController.getString(C2369R.string.NobodyLikesSpam2));
                builder.setNegativeButton(LocaleController.getString(C2369R.string.MoreInfo), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda15
                    @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                    public final void onClick(AlertDialog alertDialog, int i) {
                        BaseFragment baseFragment2 = baseFragment;
                        MessagesController.getInstance(baseFragment2.getCurrentAccount()).openByUserName("spambot", baseFragment2, 1);
                    }
                });
                break;
            case "BOTS_TOO_MUCH":
                if (z) {
                    builder.setMessage(LocaleController.getString(C2369R.string.ChannelUserCantBot));
                    break;
                } else {
                    builder.setMessage(LocaleController.getString(C2369R.string.GroupUserCantBot));
                    break;
                }
            case "USER_KICKED":
            case "CHAT_ADMIN_BAN_REQUIRED":
                if (tLObject instanceof TLRPC.TL_channels_inviteToChannel) {
                    builder.setMessage(LocaleController.getString(C2369R.string.AddUserErrorBlacklisted));
                    break;
                } else {
                    builder.setMessage(LocaleController.getString(C2369R.string.AddAdminErrorBlacklisted));
                    break;
                }
            case "YOU_BLOCKED_USER":
                builder.setMessage(LocaleController.getString(C2369R.string.YouBlockedUser));
                break;
            case "USER_ADMIN_INVALID":
                builder.setMessage(LocaleController.getString(C2369R.string.AddBannedErrorAdmin));
                break;
            case "USERS_TOO_MUCH":
                if (z) {
                    builder.setMessage(LocaleController.getString(C2369R.string.ChannelUserAddLimit));
                    break;
                } else {
                    builder.setMessage(LocaleController.getString(C2369R.string.GroupUserAddLimit));
                    break;
                }
            case "ADMINS_TOO_MUCH":
                if (z) {
                    builder.setMessage(LocaleController.getString(C2369R.string.ChannelUserCantAdmin));
                    break;
                } else {
                    builder.setMessage(LocaleController.getString(C2369R.string.GroupUserCantAdmin));
                    break;
                }
            case "CHANNELS_TOO_MUCH":
                builder.setTitle(LocaleController.getString(C2369R.string.ChannelTooMuchTitle));
                if (tLObject instanceof TLRPC.TL_channels_createChannel) {
                    builder.setMessage(LocaleController.getString(C2369R.string.ChannelTooMuch));
                    break;
                } else {
                    builder.setMessage(LocaleController.getString(C2369R.string.ChannelTooMuchJoin));
                    break;
                }
            case "USER_CHANNELS_TOO_MUCH":
                builder.setTitle(LocaleController.getString(C2369R.string.ChannelTooMuchTitle));
                builder.setMessage(LocaleController.getString(C2369R.string.UserChannelTooMuchJoin));
                break;
            case "USER_NOT_MUTUAL_CONTACT":
                if (z) {
                    builder.setMessage(LocaleController.getString(C2369R.string.ChannelUserLeftError));
                    break;
                } else {
                    builder.setMessage(LocaleController.getString(C2369R.string.GroupUserLeftError));
                    break;
                }
            case "CHAT_ADMIN_INVITE_REQUIRED":
                builder.setMessage(LocaleController.getString(C2369R.string.AddAdminErrorNotAMember));
                break;
            case "USER_PRIVACY_RESTRICTED":
                if (z) {
                    builder.setMessage(LocaleController.getString(C2369R.string.InviteToChannelError));
                    break;
                } else {
                    builder.setMessage(LocaleController.getString(C2369R.string.InviteToGroupError));
                    break;
                }
            case "USER_ALREADY_PARTICIPANT":
                builder.setTitle(LocaleController.getString(C2369R.string.VoipGroupVoiceChat));
                builder.setMessage(LocaleController.getString(C2369R.string.VoipGroupInviteAlreadyParticipant));
                break;
            default:
                builder.setMessage(LocaleController.getString(C2369R.string.ErrorOccurred) + "\n" + str);
                break;
        }
        builder.setPositiveButton(LocaleController.getString(C2369R.string.f1459OK), null);
        baseFragment.showDialog(builder.create(), true, null);
    }

    public static Dialog createColorSelectDialog(Activity activity, long j, int i, int i2, Runnable runnable) {
        return createColorSelectDialog(activity, j, i, i2, runnable, null);
    }

    public static Dialog createColorSelectDialog(Activity activity, final long j, final long j2, final int i, final Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
        int i2;
        SharedPreferences notificationsSettings = MessagesController.getNotificationsSettings(UserConfig.selectedAccount);
        final String sharedPrefKey = NotificationsController.getSharedPrefKey(j, j2);
        if (j != 0) {
            if (notificationsSettings.contains("color_" + sharedPrefKey)) {
                i2 = notificationsSettings.getInt("color_" + sharedPrefKey, -16776961);
            } else if (DialogObject.isChatDialog(j)) {
                i2 = notificationsSettings.getInt("GroupLed", -16776961);
            } else {
                i2 = notificationsSettings.getInt("MessagesLed", -16776961);
            }
        } else if (i == 1) {
            i2 = notificationsSettings.getInt("MessagesLed", -16776961);
        } else if (i == 0) {
            i2 = notificationsSettings.getInt("GroupLed", -16776961);
        } else if (i == 3) {
            i2 = notificationsSettings.getInt("StoriesLed", -16776961);
        } else if (i == 5 || i == 4) {
            i2 = notificationsSettings.getInt("ReactionsLed", -16776961);
        } else {
            i2 = notificationsSettings.getInt("ChannelLed", -16776961);
        }
        final LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        String[] strArr = {LocaleController.getString(C2369R.string.ColorRed), LocaleController.getString(C2369R.string.ColorOrange), LocaleController.getString(C2369R.string.ColorYellow), LocaleController.getString(C2369R.string.ColorGreen), LocaleController.getString(C2369R.string.ColorCyan), LocaleController.getString(C2369R.string.ColorBlue), LocaleController.getString(C2369R.string.ColorViolet), LocaleController.getString(C2369R.string.ColorPink), LocaleController.getString(C2369R.string.ColorWhite)};
        final int[] iArr = {i2};
        for (int i3 = 0; i3 < 9; i3++) {
            RadioColorCell radioColorCell = new RadioColorCell(activity, resourcesProvider);
            radioColorCell.setPadding(AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i3));
            int i4 = TextColorCell.colors[i3];
            radioColorCell.setCheckColor(i4, i4);
            radioColorCell.setTextAndValue(strArr[i3], i2 == TextColorCell.colorsToSave[i3]);
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda163
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertsCreator.$r8$lambda$A3UAQX5oYjHyAApGsFxU0upX1GQ(linearLayout, iArr, view);
                }
            });
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, resourcesProvider);
        builder.setTitle(LocaleController.getString(C2369R.string.LedColor));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Set), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda164
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i5) {
                AlertsCreator.$r8$lambda$upBGjpwXcV9BmrUQAUyDR1JsMGE(j, sharedPrefKey, iArr, j2, i, runnable, alertDialog, i5);
            }
        });
        builder.setNeutralButton(LocaleController.getString(C2369R.string.LedDisabled), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda165
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i5) {
                AlertsCreator.m7596$r8$lambda$4jJb9GKTCjetjqhc4Q15OM9wg(j, i, runnable, alertDialog, i5);
            }
        });
        if (j != 0) {
            builder.setNegativeButton(LocaleController.getString(C2369R.string.Default), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda166
                @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
                public final void onClick(AlertDialog alertDialog, int i5) {
                    AlertsCreator.$r8$lambda$SmDZUdFCq5NbB6loulkryOhxCpw(sharedPrefKey, runnable, alertDialog, i5);
                }
            });
        }
        return builder.create();
    }

    public static /* synthetic */ void $r8$lambda$A3UAQX5oYjHyAApGsFxU0upX1GQ(LinearLayout linearLayout, int[] iArr, View view) {
        int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RadioColorCell radioColorCell = (RadioColorCell) linearLayout.getChildAt(i);
            radioColorCell.setChecked(radioColorCell == view, true);
        }
        iArr[0] = TextColorCell.colorsToSave[((Integer) view.getTag()).intValue()];
    }

    public static /* synthetic */ void $r8$lambda$upBGjpwXcV9BmrUQAUyDR1JsMGE(long j, String str, int[] iArr, long j2, int i, Runnable runnable, AlertDialog alertDialog, int i2) {
        SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
        if (j != 0) {
            editorEdit.putInt("color_" + str, iArr[0]);
            NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannel(j, j2);
        } else {
            if (i == 1) {
                editorEdit.putInt("MessagesLed", iArr[0]);
            } else if (i == 0) {
                editorEdit.putInt("GroupLed", iArr[0]);
            } else if (i == 3) {
                editorEdit.putInt("StoriesLed", iArr[0]);
            } else if (i == 5 || i == 4) {
                editorEdit.putInt("ReactionLed", iArr[0]);
            } else {
                editorEdit.putInt("ChannelLed", iArr[0]);
            }
            NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(i);
        }
        editorEdit.apply();
        if (runnable != null) {
            runnable.run();
        }
    }

    /* renamed from: $r8$lambda$4j--Jb9GKTCjetjqhc4Q15OM9wg, reason: not valid java name */
    public static /* synthetic */ void m7596$r8$lambda$4jJb9GKTCjetjqhc4Q15OM9wg(long j, int i, Runnable runnable, AlertDialog alertDialog, int i2) {
        SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
        if (j != 0) {
            editorEdit.putInt("color_" + j, 0);
        } else if (i == 1) {
            editorEdit.putInt("MessagesLed", 0);
        } else if (i == 0) {
            editorEdit.putInt("GroupLed", 0);
        } else if (i == 3) {
            editorEdit.putInt("StoriesLed", 0);
        } else if (i == 5 || i == 4) {
            editorEdit.putInt("ReactionsLed", 0);
        } else {
            editorEdit.putInt("ChannelLed", 0);
        }
        editorEdit.apply();
        if (runnable != null) {
            runnable.run();
        }
    }

    public static /* synthetic */ void $r8$lambda$SmDZUdFCq5NbB6loulkryOhxCpw(String str, Runnable runnable, AlertDialog alertDialog, int i) {
        SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
        editorEdit.remove("color_" + str);
        editorEdit.apply();
        if (runnable != null) {
            runnable.run();
        }
    }

    public static Dialog createVibrationSelectDialog(Activity activity, long j, long j2, boolean z, boolean z2, Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
        String str;
        if (j != 0) {
            str = "vibrate_" + j;
        } else {
            str = z ? "vibrate_group" : "vibrate_messages";
        }
        return createVibrationSelectDialog(activity, j, j2, str, runnable, resourcesProvider);
    }

    public static Dialog createVibrationSelectDialog(Activity activity, long j, long j2, String str, Runnable runnable) {
        return createVibrationSelectDialog(activity, j, j2, str, runnable, null);
    }

    public static Dialog createVibrationSelectDialog(Activity activity, final long j, final long j2, String str, final Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
        String[] strArr;
        final String str2 = str;
        SharedPreferences notificationsSettings = MessagesController.getNotificationsSettings(UserConfig.selectedAccount);
        final int[] iArr = new int[1];
        if (j != 0) {
            int i = notificationsSettings.getInt(str2, 0);
            iArr[0] = i;
            if (i == 3) {
                iArr[0] = 2;
            } else if (i == 2) {
                iArr[0] = 3;
            }
            strArr = new String[]{LocaleController.getString(C2369R.string.VibrationDefault), LocaleController.getString(C2369R.string.Short), LocaleController.getString(C2369R.string.Long), LocaleController.getString(C2369R.string.VibrationDisabled)};
        } else {
            int i2 = notificationsSettings.getInt(str2, 0);
            iArr[0] = i2;
            if (i2 == 0) {
                iArr[0] = 1;
            } else if (i2 == 1) {
                iArr[0] = 2;
            } else if (i2 == 2) {
                iArr[0] = 0;
            }
            strArr = new String[]{LocaleController.getString(C2369R.string.VibrationDisabled), LocaleController.getString(C2369R.string.VibrationDefault), LocaleController.getString(C2369R.string.Short), LocaleController.getString(C2369R.string.Long), LocaleController.getString(C2369R.string.OnlyIfSilent)};
        }
        String[] strArr2 = strArr;
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, resourcesProvider);
        int i3 = 0;
        while (i3 < strArr2.length) {
            RadioColorCell radioColorCell = new RadioColorCell(activity, resourcesProvider);
            radioColorCell.setPadding(AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i3));
            radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground, resourcesProvider), Theme.getColor(Theme.key_dialogRadioBackgroundChecked, resourcesProvider));
            radioColorCell.setTextAndValue(strArr2[i3], iArr[0] == i3);
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda167
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertsCreator.$r8$lambda$pYXuorzHo0j262nQV4Z5nvzJ0Qw(iArr, j, str2, j2, builder, runnable, view);
                }
            });
            i3++;
            str2 = str;
        }
        builder.setTitle(LocaleController.getString(C2369R.string.Vibrate));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Cancel), null);
        return builder.create();
    }

    public static /* synthetic */ void $r8$lambda$pYXuorzHo0j262nQV4Z5nvzJ0Qw(int[] iArr, long j, String str, long j2, AlertDialog.Builder builder, Runnable runnable, View view) {
        iArr[0] = ((Integer) view.getTag()).intValue();
        SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
        if (j != 0) {
            int i = iArr[0];
            if (i == 0) {
                editorEdit.putInt(str, 0);
            } else if (i == 1) {
                editorEdit.putInt(str, 1);
            } else if (i == 2) {
                editorEdit.putInt(str, 3);
            } else if (i == 3) {
                editorEdit.putInt(str, 2);
            }
            NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannel(j, j2);
        } else {
            int i2 = iArr[0];
            if (i2 == 0) {
                editorEdit.putInt(str, 2);
            } else if (i2 == 1) {
                editorEdit.putInt(str, 0);
            } else if (i2 == 2) {
                editorEdit.putInt(str, 1);
            } else if (i2 == 3) {
                editorEdit.putInt(str, 3);
            } else if (i2 == 4) {
                editorEdit.putInt(str, 4);
            }
            if (str.equals("vibrate_channel")) {
                NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(2);
            } else if (str.equals("vibrate_group")) {
                NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(0);
            } else if (str.equals("vibrate_react")) {
                NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(4);
            } else {
                NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(1);
            }
        }
        editorEdit.apply();
        builder.getDismissRunnable().run();
        if (runnable != null) {
            runnable.run();
        }
    }

    public static Dialog createLocationUpdateDialog(Activity activity, boolean z, TLRPC.User user, final MessagesStorage.IntCallback intCallback, Theme.ResourcesProvider resourcesProvider) {
        final int[] iArr = new int[1];
        String[] strArr = {LocaleController.getString(C2369R.string.SendLiveLocationFor15m), LocaleController.getString(C2369R.string.SendLiveLocationFor1h), LocaleController.getString(C2369R.string.SendLiveLocationFor8h), LocaleController.getString(C2369R.string.SendLiveLocationForever)};
        final LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(0, 0, 0, AndroidUtilities.m1146dp(4.0f));
        TextView textView = new TextView(activity);
        if (z) {
            textView.setText(LocaleController.getString(C2369R.string.LiveLocationAlertExpandMessage));
        } else if (user != null) {
            textView.setText(LocaleController.formatString(C2369R.string.LiveLocationAlertPrivate, UserObject.getFirstName(user)));
        } else {
            textView.setText(LocaleController.getString(C2369R.string.LiveLocationAlertGroup));
        }
        int i = Theme.key_dialogTextBlack;
        textView.setTextColor(resourcesProvider != null ? resourcesProvider.getColorOrDefault(i) : Theme.getColor(i));
        textView.setTextSize(1, 16.0f);
        textView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 24, z ? 4 : 0, 24, 8));
        int i2 = 0;
        while (i2 < 4) {
            RadioColorCell radioColorCell = new RadioColorCell(activity, resourcesProvider);
            radioColorCell.heightDp = 42;
            radioColorCell.setPadding(AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i2));
            int i3 = Theme.key_radioBackground;
            int colorOrDefault = resourcesProvider != null ? resourcesProvider.getColorOrDefault(i3) : Theme.getColor(i3);
            int i4 = Theme.key_dialogRadioBackgroundChecked;
            radioColorCell.setCheckColor(colorOrDefault, resourcesProvider != null ? resourcesProvider.getColorOrDefault(i4) : Theme.getColor(i4));
            radioColorCell.setTextAndValue(strArr[i2], iArr[0] == i2);
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda68
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertsCreator.$r8$lambda$NhQP2JAI_tTTWJMU7TPWPlC4sRM(iArr, linearLayout, view);
                }
            });
            i2++;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, resourcesProvider);
        if (z) {
            builder.setTitle(LocaleController.getString(C2369R.string.LiveLocationAlertExpandTitle));
        } else {
            builder.setTopImage(new ShareLocationDrawable(activity, 0), resourcesProvider != null ? resourcesProvider.getColorOrDefault(Theme.key_dialogTopBackground) : Theme.getColor(Theme.key_dialogTopBackground));
        }
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.ShareFile), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda69
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i5) {
                AlertsCreator.$r8$lambda$LZXJpsEjIA_2ztVLxI0dTU5rPFI(iArr, intCallback, alertDialog, i5);
            }
        });
        builder.setNeutralButton(LocaleController.getString(C2369R.string.Cancel), null);
        return builder.create();
    }

    public static /* synthetic */ void $r8$lambda$NhQP2JAI_tTTWJMU7TPWPlC4sRM(int[] iArr, LinearLayout linearLayout, View view) {
        iArr[0] = ((Integer) view.getTag()).intValue();
        int childCount = linearLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = linearLayout.getChildAt(i);
            if (childAt instanceof RadioColorCell) {
                ((RadioColorCell) childAt).setChecked(childAt == view, true);
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$LZXJpsEjIA_2ztVLxI0dTU5rPFI(int[] iArr, MessagesStorage.IntCallback intCallback, AlertDialog alertDialog, int i) {
        int i2 = iArr[0];
        intCallback.run(i2 == 0 ? 900 : i2 == 1 ? 3600 : i2 == 2 ? 28800 : ConnectionsManager.DEFAULT_DATACENTER_ID);
    }

    public static AlertDialog.Builder createBackgroundLocationPermissionDialog(final Activity activity, TLRPC.User user, final Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
        if (activity == null || Build.VERSION.SDK_INT < 29) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, resourcesProvider);
        String res = AndroidUtilities.readRes(Theme.getCurrentTheme().isDark() ? C2369R.raw.permission_map_dark : C2369R.raw.permission_map);
        String res2 = AndroidUtilities.readRes(Theme.getCurrentTheme().isDark() ? C2369R.raw.permission_pin_dark : C2369R.raw.permission_pin);
        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setClipToOutline(true);
        frameLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.AlertsCreator.52
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1146dp(6.0f), AndroidUtilities.m1146dp(6.0f));
            }
        });
        View view = new View(activity);
        view.setBackground(SvgHelper.getDrawable(res));
        frameLayout.addView(view, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, 0.0f));
        View view2 = new View(activity);
        view2.setBackground(SvgHelper.getDrawable(res2));
        frameLayout.addView(view2, LayoutHelper.createFrame(60, 82.0f, 17, 0.0f, 0.0f, 0.0f, 0.0f));
        BackupImageView backupImageView = new BackupImageView(activity);
        backupImageView.setRoundRadius(ExteraConfig.getAvatarCorners(52.0f));
        backupImageView.setForUserOrChat(user, new AvatarDrawable(user));
        frameLayout.addView(backupImageView, LayoutHelper.createFrame(52, 52.0f, 17, 0.0f, 0.0f, 0.0f, 11.0f));
        builder.setTopView(frameLayout);
        builder.setTopViewAspectRatio(0.37820512f);
        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.PermissionBackgroundLocation)));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Continue), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda108
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.$r8$lambda$AC52UMTGuCL8KjhhS74unA5Z1bs(activity, alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda109
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                runnable.run();
            }
        });
        return builder;
    }

    public static /* synthetic */ void $r8$lambda$AC52UMTGuCL8KjhhS74unA5Z1bs(Activity activity, AlertDialog alertDialog, int i) {
        if (activity.checkSelfPermission("android.permission.ACCESS_BACKGROUND_LOCATION") != 0) {
            activity.requestPermissions(new String[]{"android.permission.ACCESS_BACKGROUND_LOCATION"}, 30);
        }
    }

    public static AlertDialog.Builder createGigagroupConvertAlert(Activity activity, AlertDialog.OnButtonClickListener onButtonClickListener, AlertDialog.OnButtonClickListener onButtonClickListener2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        String res = AndroidUtilities.readRes(C2369R.raw.gigagroup);
        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setClipToOutline(true);
        frameLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.AlertsCreator.53
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1146dp(6.0f), AndroidUtilities.m1146dp(6.0f));
            }
        });
        View view = new View(activity);
        view.setBackground(new BitmapDrawable(SvgHelper.getBitmap(res, AndroidUtilities.m1146dp(320.0f), AndroidUtilities.m1146dp(127.17949f), false)));
        frameLayout.addView(view, LayoutHelper.createFrame(-1, -1.0f, 0, -1.0f, -1.0f, -1.0f, -1.0f));
        builder.setTopView(frameLayout);
        builder.setTopViewAspectRatio(0.3974359f);
        builder.setTitle(LocaleController.getString(C2369R.string.GigagroupAlertTitle));
        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.GigagroupAlertText)));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.GigagroupAlertLearnMore), onButtonClickListener);
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), onButtonClickListener2);
        return builder;
    }

    public static AlertDialog.Builder createDrawOverlayPermissionDialog(Activity activity, AlertDialog.OnButtonClickListener onButtonClickListener) {
        return createDrawOverlayPermissionDialog(activity, onButtonClickListener, false);
    }

    public static AlertDialog.Builder createDrawOverlayPermissionDialog(final Activity activity, AlertDialog.OnButtonClickListener onButtonClickListener, final boolean z) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        String res = AndroidUtilities.readRes(C2369R.raw.pip_video_request);
        FrameLayout frameLayout = new FrameLayout(activity);
        frameLayout.setBackground(new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{-14535089, -14527894}));
        frameLayout.setClipToOutline(true);
        frameLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.AlertsCreator.54
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1146dp(6.0f), AndroidUtilities.dpf2(6.0f));
            }
        });
        View view = new View(activity);
        view.setBackground(new BitmapDrawable(SvgHelper.getBitmap(res, AndroidUtilities.m1146dp(320.0f), AndroidUtilities.m1146dp(161.36752f), false)));
        frameLayout.addView(view, LayoutHelper.createFrame(-1, -1.0f, 0, -1.0f, -1.0f, -1.0f, -1.0f));
        builder.setTopView(frameLayout);
        builder.setTitle(LocaleController.getString(C2369R.string.PermissionDrawAboveOtherAppsTitle));
        builder.setMessage(LocaleController.getString(C2369R.string.PermissionDrawAboveOtherApps));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Enable), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda48
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.$r8$lambda$FfAOY7ZcuCwqhErTKT6XrNf7tdc(activity, z, alertDialog, i);
            }
        });
        builder.notDrawBackgroundOnTopView(true);
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), onButtonClickListener);
        builder.setTopViewAspectRatio(0.50427353f);
        return builder;
    }

    public static /* synthetic */ void $r8$lambda$FfAOY7ZcuCwqhErTKT6XrNf7tdc(Activity activity, boolean z, AlertDialog alertDialog, int i) {
        if (activity == null || Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (z && PipUtils.checkPermissions(activity) == -2) {
            try {
                activity.startActivity(new Intent("android.settings.PICTURE_IN_PICTURE_SETTINGS", Uri.parse("package:" + activity.getPackageName())));
                return;
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
        try {
            activity.startActivity(new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + activity.getPackageName())));
        } catch (Exception e2) {
            FileLog.m1160e(e2);
        }
    }

    public static AlertDialog.Builder createDrawOverlayGroupCallPermissionDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        String res = AndroidUtilities.readRes(C2369R.raw.pip_voice_request);
        final GroupCallPipButton groupCallPipButton = new GroupCallPipButton(context, 0, true);
        groupCallPipButton.setImportantForAccessibility(2);
        FrameLayout frameLayout = new FrameLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.55
            @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                groupCallPipButton.setTranslationY((getMeasuredHeight() * 0.28f) - (groupCallPipButton.getMeasuredWidth() / 2.0f));
                groupCallPipButton.setTranslationX((getMeasuredWidth() * 0.82f) - (groupCallPipButton.getMeasuredWidth() / 2.0f));
            }
        };
        frameLayout.setBackground(new GradientDrawable(GradientDrawable.Orientation.BL_TR, new int[]{-15128003, -15118002}));
        frameLayout.setClipToOutline(true);
        frameLayout.setOutlineProvider(new ViewOutlineProvider() { // from class: org.telegram.ui.Components.AlertsCreator.56
            @Override // android.view.ViewOutlineProvider
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight() + AndroidUtilities.m1146dp(6.0f), AndroidUtilities.dpf2(6.0f));
            }
        });
        View view = new View(context);
        view.setBackground(new BitmapDrawable(SvgHelper.getBitmap(res, AndroidUtilities.m1146dp(320.0f), AndroidUtilities.m1146dp(184.61539f), false)));
        frameLayout.addView(view, LayoutHelper.createFrame(-1, -1.0f, 0, -1.0f, -1.0f, -1.0f, -1.0f));
        frameLayout.addView(groupCallPipButton, LayoutHelper.createFrame(Opcodes.LNEG, 117.0f));
        builder.setTopView(frameLayout);
        builder.setTitle(LocaleController.getString(C2369R.string.PermissionDrawAboveOtherAppsGroupCallTitle));
        builder.setMessage(LocaleController.getString(C2369R.string.PermissionDrawAboveOtherAppsGroupCall));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Enable), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda34
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.$r8$lambda$F15UDzQxyWYREoI28WIuRg3SN9s(context, alertDialog, i);
            }
        });
        builder.notDrawBackgroundOnTopView(true);
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        builder.setTopViewAspectRatio(0.5769231f);
        return builder;
    }

    public static /* synthetic */ void $r8$lambda$F15UDzQxyWYREoI28WIuRg3SN9s(Context context, AlertDialog alertDialog, int i) {
        if (context != null) {
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION", Uri.parse("package:" + context.getPackageName()));
                    Activity activityFindActivity = AndroidUtilities.findActivity(context);
                    if (activityFindActivity instanceof LaunchActivity) {
                        activityFindActivity.startActivityForResult(intent, 105);
                    } else {
                        context.startActivity(intent);
                    }
                }
            } catch (Exception e) {
                FileLog.m1160e(e);
            }
        }
    }

    public static AlertDialog.Builder createContactsPermissionDialog(Activity activity, final MessagesStorage.IntCallback intCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTopAnimation(C2369R.raw.permission_request_contacts, 72, false, Theme.getColor(Theme.key_dialogTopBackground));
        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.ContactsPermissionAlert)));
        builder.setPositiveButton(LocaleController.getString(C2369R.string.ContactsPermissionAlertContinue), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda10
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                intCallback.run(1);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.ContactsPermissionAlertNotNow), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda11
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                intCallback.run(0);
            }
        });
        return builder;
    }

    public static Dialog createFreeSpaceDialog(final LaunchActivity launchActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(launchActivity);
        builder.setTitle(LocaleController.getString(C2369R.string.LowDiskSpaceTitle));
        builder.setMessage(LocaleController.getString(C2369R.string.LowDiskSpaceMessage2));
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.LowDiskSpaceButton), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda111
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                launchActivity.lambda$runLinkRequest$107(new CacheControlActivity());
            }
        });
        return builder.create();
    }

    public static Dialog createPrioritySelectDialog(Activity activity, long j, int i, int i2, Runnable runnable) {
        return createPrioritySelectDialog(activity, j, i, i2, runnable, null);
    }

    public static Dialog createPrioritySelectDialog(Activity activity, long j, final long j2, int i, final Runnable runnable, Theme.ResourcesProvider resourcesProvider) {
        String[] strArr;
        final long j3 = j;
        final int i2 = i;
        final SharedPreferences notificationsSettings = MessagesController.getNotificationsSettings(UserConfig.selectedAccount);
        final int[] iArr = new int[1];
        if (j3 != 0) {
            int i3 = notificationsSettings.getInt("priority_" + j3, 3);
            iArr[0] = i3;
            if (i3 == 3) {
                iArr[0] = 0;
            } else if (i3 == 4) {
                iArr[0] = 1;
            } else if (i3 == 5) {
                iArr[0] = 2;
            } else if (i3 == 0) {
                iArr[0] = 3;
            } else {
                iArr[0] = 4;
            }
            strArr = new String[]{LocaleController.getString(C2369R.string.NotificationsPrioritySettings), LocaleController.getString(C2369R.string.NotificationsPriorityLow), LocaleController.getString(C2369R.string.NotificationsPriorityMedium), LocaleController.getString(C2369R.string.NotificationsPriorityHigh), LocaleController.getString(C2369R.string.NotificationsPriorityUrgent)};
        } else {
            if (i2 == 1) {
                iArr[0] = notificationsSettings.getInt("priority_messages", 1);
            } else if (i2 == 0) {
                iArr[0] = notificationsSettings.getInt("priority_group", 1);
            } else if (i2 == 2) {
                iArr[0] = notificationsSettings.getInt("priority_channel", 1);
            } else if (i2 == 3) {
                iArr[0] = notificationsSettings.getInt("priority_stories", 1);
            } else if (i2 == 4 || i2 == 5) {
                iArr[0] = notificationsSettings.getInt("priority_react", 1);
            }
            int i4 = iArr[0];
            if (i4 == 4) {
                iArr[0] = 0;
            } else if (i4 == 5) {
                iArr[0] = 1;
            } else if (i4 == 0) {
                iArr[0] = 2;
            } else {
                iArr[0] = 3;
            }
            strArr = new String[]{LocaleController.getString(C2369R.string.NotificationsPriorityLow), LocaleController.getString(C2369R.string.NotificationsPriorityMedium), LocaleController.getString(C2369R.string.NotificationsPriorityHigh), LocaleController.getString(C2369R.string.NotificationsPriorityUrgent)};
        }
        String[] strArr2 = strArr;
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity, resourcesProvider);
        int i5 = 0;
        while (i5 < strArr2.length) {
            RadioColorCell radioColorCell = new RadioColorCell(activity, resourcesProvider);
            radioColorCell.setPadding(AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i5));
            radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground, resourcesProvider), Theme.getColor(Theme.key_dialogRadioBackgroundChecked, resourcesProvider));
            radioColorCell.setTextAndValue(strArr2[i5], iArr[0] == i5);
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda185
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertsCreator.$r8$lambda$l0eksVD1At5WO8KdN_BHvdyZ9Q8(iArr, j3, j2, i2, notificationsSettings, builder, runnable, view);
                }
            });
            i5++;
            j3 = j;
            i2 = i;
        }
        builder.setTitle(LocaleController.getString(C2369R.string.NotificationsImportance));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Cancel), null);
        return builder.create();
    }

    public static /* synthetic */ void $r8$lambda$l0eksVD1At5WO8KdN_BHvdyZ9Q8(int[] iArr, long j, long j2, int i, SharedPreferences sharedPreferences, AlertDialog.Builder builder, Runnable runnable, View view) {
        int i2 = 0;
        iArr[0] = ((Integer) view.getTag()).intValue();
        SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
        if (j != 0) {
            int i3 = iArr[0];
            if (i3 == 0) {
                i2 = 3;
            } else if (i3 == 1) {
                i2 = 4;
            } else if (i3 == 2) {
                i2 = 5;
            } else if (i3 != 3) {
                i2 = 1;
            }
            editorEdit.putInt("priority_" + j, i2);
            NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannel(j, j2);
        } else {
            int i4 = iArr[0];
            int i5 = i4 == 0 ? 4 : i4 == 1 ? 5 : i4 == 2 ? 0 : 1;
            if (i == 1) {
                editorEdit.putInt("priority_messages", i5);
                iArr[0] = sharedPreferences.getInt("priority_messages", 1);
            } else if (i == 0) {
                editorEdit.putInt("priority_group", i5);
                iArr[0] = sharedPreferences.getInt("priority_group", 1);
            } else if (i == 2) {
                editorEdit.putInt("priority_channel", i5);
                iArr[0] = sharedPreferences.getInt("priority_channel", 1);
            } else if (i == 3) {
                editorEdit.putInt("priority_stories", i5);
                iArr[0] = sharedPreferences.getInt("priority_stories", 1);
            } else if (i == 4 || i == 5) {
                editorEdit.putInt("priority_react", i5);
                iArr[0] = sharedPreferences.getInt("priority_react", 1);
            }
            NotificationsController.getInstance(UserConfig.selectedAccount).deleteNotificationChannelGlobal(i);
        }
        editorEdit.apply();
        builder.getDismissRunnable().run();
        if (runnable != null) {
            runnable.run();
        }
    }

    public static Dialog createPopupSelectDialog(Activity activity, final int i, final Runnable runnable) {
        SharedPreferences notificationsSettings = MessagesController.getNotificationsSettings(UserConfig.selectedAccount);
        final int[] iArr = new int[1];
        if (i == 1) {
            iArr[0] = notificationsSettings.getInt("popupAll", 0);
        } else if (i == 0) {
            iArr[0] = notificationsSettings.getInt("popupGroup", 0);
        } else {
            iArr[0] = notificationsSettings.getInt("popupChannel", 0);
        }
        String[] strArr = {LocaleController.getString(C2369R.string.NoPopup), LocaleController.getString(C2369R.string.OnlyWhenScreenOn), LocaleController.getString(C2369R.string.OnlyWhenScreenOff), LocaleController.getString(C2369R.string.AlwaysShowPopup)};
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        int i2 = 0;
        while (i2 < 4) {
            RadioColorCell radioColorCell = new RadioColorCell(activity);
            radioColorCell.setTag(Integer.valueOf(i2));
            radioColorCell.setPadding(AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f), 0);
            radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            radioColorCell.setTextAndValue(strArr[i2], iArr[0] == i2);
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda157
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertsCreator.m7626$r8$lambda$WiOabOG20DvodbCkzmF8kCsvXE(iArr, i, builder, runnable, view);
                }
            });
            i2++;
        }
        builder.setTitle(LocaleController.getString(C2369R.string.PopupNotification));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Cancel), null);
        return builder.create();
    }

    /* renamed from: $r8$lambda$W-iOabOG20DvodbCkzmF8kCsvXE, reason: not valid java name */
    public static /* synthetic */ void m7626$r8$lambda$WiOabOG20DvodbCkzmF8kCsvXE(int[] iArr, int i, AlertDialog.Builder builder, Runnable runnable, View view) {
        iArr[0] = ((Integer) view.getTag()).intValue();
        SharedPreferences.Editor editorEdit = MessagesController.getNotificationsSettings(UserConfig.selectedAccount).edit();
        if (i == 1) {
            editorEdit.putInt("popupAll", iArr[0]);
        } else if (i == 0) {
            editorEdit.putInt("popupGroup", iArr[0]);
        } else {
            editorEdit.putInt("popupChannel", iArr[0]);
        }
        editorEdit.apply();
        builder.getDismissRunnable().run();
        if (runnable != null) {
            runnable.run();
        }
    }

    public static Dialog createSingleChoiceDialog(Activity activity, String[] strArr, String str, int i, final DialogInterface.OnClickListener onClickListener) {
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        int i2 = 0;
        while (i2 < strArr.length) {
            RadioColorCell radioColorCell = new RadioColorCell(activity);
            radioColorCell.setPadding(AndroidUtilities.m1146dp(4.0f), 0, AndroidUtilities.m1146dp(4.0f), 0);
            radioColorCell.setTag(Integer.valueOf(i2));
            radioColorCell.setCheckColor(Theme.getColor(Theme.key_radioBackground), Theme.getColor(Theme.key_dialogRadioBackgroundChecked));
            radioColorCell.setTextAndValue(strArr[i2], i == i2);
            linearLayout.addView(radioColorCell);
            radioColorCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda162
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AlertsCreator.$r8$lambda$jX8TzGJiQ0Hh2MmbQtiGB9R7Vvs(builder, onClickListener, view);
                }
            });
            i2++;
        }
        builder.setTitle(str);
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Cancel), null);
        return builder.create();
    }

    public static /* synthetic */ void $r8$lambda$jX8TzGJiQ0Hh2MmbQtiGB9R7Vvs(AlertDialog.Builder builder, DialogInterface.OnClickListener onClickListener, View view) {
        int iIntValue = ((Integer) view.getTag()).intValue();
        builder.getDismissRunnable().run();
        onClickListener.onClick(null, iIntValue);
    }

    public static AlertDialog.Builder createTTLAlert(Context context, final TLRPC.EncryptedChat encryptedChat, Theme.ResourcesProvider resourcesProvider) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, resourcesProvider);
        builder.setTitle(LocaleController.getString(C2369R.string.MessageLifetime));
        final NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(20);
        int i = encryptedChat.ttl;
        if (i > 0 && i < 16) {
            numberPicker.setValue(i);
        } else if (i == 30) {
            numberPicker.setValue(16);
        } else if (i == 60) {
            numberPicker.setValue(17);
        } else if (i == 3600) {
            numberPicker.setValue(18);
        } else if (i == 86400) {
            numberPicker.setValue(19);
        } else if (i == 604800) {
            numberPicker.setValue(20);
        } else if (i == 0) {
            numberPicker.setValue(0);
        }
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda32
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i2) {
                return AlertsCreator.$r8$lambda$AtcFEFayX2Vp78G6MIN_peAsedM(i2);
            }
        });
        builder.setView(numberPicker);
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Done), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda33
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                AlertsCreator.$r8$lambda$VxqYuSumVBcByQD_gLZa3bTq7gw(encryptedChat, numberPicker, alertDialog, i2);
            }
        });
        return builder;
    }

    public static /* synthetic */ String $r8$lambda$AtcFEFayX2Vp78G6MIN_peAsedM(int i) {
        if (i == 0) {
            return LocaleController.getString(C2369R.string.ShortMessageLifetimeForever);
        }
        if (i >= 1 && i < 16) {
            return LocaleController.formatTTLString(i);
        }
        if (i == 16) {
            return LocaleController.formatTTLString(30);
        }
        if (i == 17) {
            return LocaleController.formatTTLString(60);
        }
        if (i == 18) {
            return LocaleController.formatTTLString(3600);
        }
        if (i == 19) {
            return LocaleController.formatTTLString(86400);
        }
        if (i == 20) {
            return LocaleController.formatTTLString(604800);
        }
        return "";
    }

    public static /* synthetic */ void $r8$lambda$VxqYuSumVBcByQD_gLZa3bTq7gw(TLRPC.EncryptedChat encryptedChat, NumberPicker numberPicker, AlertDialog alertDialog, int i) {
        int i2 = encryptedChat.ttl;
        int value = numberPicker.getValue();
        if (value >= 0 && value < 16) {
            encryptedChat.ttl = value;
        } else if (value == 16) {
            encryptedChat.ttl = 30;
        } else if (value == 17) {
            encryptedChat.ttl = 60;
        } else if (value == 18) {
            encryptedChat.ttl = 3600;
        } else if (value == 19) {
            encryptedChat.ttl = 86400;
        } else if (value == 20) {
            encryptedChat.ttl = 604800;
        }
        if (i2 != encryptedChat.ttl) {
            SecretChatHelper.getInstance(UserConfig.selectedAccount).sendTTLMessage(encryptedChat, null);
            MessagesStorage.getInstance(UserConfig.selectedAccount).updateEncryptedChatTTL(encryptedChat);
        }
    }

    public static AlertDialog createAccountSelectDialog(Activity activity, final AccountSelectDelegate accountSelectDelegate) {
        if (UserConfig.getActivatedAccountsCount() < 2) {
            return null;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final Runnable dismissRunnable = builder.getDismissRunnable();
        final AlertDialog[] alertDialogArr = new AlertDialog[1];
        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(1);
        for (int i = 0; i < 16; i++) {
            if (UserConfig.getInstance(i).getCurrentUser() != null) {
                AccountSelectCell accountSelectCell = new AccountSelectCell(activity, false);
                accountSelectCell.setAccount(i, false);
                accountSelectCell.setPadding(AndroidUtilities.m1146dp(14.0f), 0, AndroidUtilities.m1146dp(14.0f), 0);
                accountSelectCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                linearLayout.addView(accountSelectCell, LayoutHelper.createLinear(-1, 50));
                accountSelectCell.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda27
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AlertsCreator.$r8$lambda$mmWJnzuihkKOAan23EF_Ic7L1jc(alertDialogArr, dismissRunnable, accountSelectDelegate, view);
                    }
                });
            }
        }
        builder.setTitle(LocaleController.getString(C2369R.string.SelectAccount));
        builder.setView(linearLayout);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Cancel), null);
        AlertDialog alertDialogCreate = builder.create();
        alertDialogArr[0] = alertDialogCreate;
        return alertDialogCreate;
    }

    public static /* synthetic */ void $r8$lambda$mmWJnzuihkKOAan23EF_Ic7L1jc(AlertDialog[] alertDialogArr, Runnable runnable, AccountSelectDelegate accountSelectDelegate, View view) {
        AlertDialog alertDialog = alertDialogArr[0];
        if (alertDialog != null) {
            alertDialog.setOnDismissListener(null);
        }
        runnable.run();
        accountSelectDelegate.didSelectAccount(((AccountSelectCell) view).getAccountNumber());
    }

    /* JADX WARN: Removed duplicated region for block: B:129:0x029d  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02ff  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0309  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x032b  */
    /* JADX WARN: Removed duplicated region for block: B:243:0x057e  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0588 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:346:0x0711  */
    /* JADX WARN: Removed duplicated region for block: B:349:0x071e A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:355:0x072e  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x07b9  */
    /* JADX WARN: Removed duplicated region for block: B:375:0x07e0  */
    /* JADX WARN: Removed duplicated region for block: B:379:0x0809  */
    /* JADX WARN: Removed duplicated region for block: B:384:0x0830  */
    /* JADX WARN: Removed duplicated region for block: B:387:0x0848  */
    /* JADX WARN: Removed duplicated region for block: B:415:0x08f7  */
    /* JADX WARN: Removed duplicated region for block: B:426:0x0932  */
    /* JADX WARN: Removed duplicated region for block: B:451:0x0999  */
    /* JADX WARN: Removed duplicated region for block: B:452:0x09d0  */
    /* JADX WARN: Removed duplicated region for block: B:463:0x0a65  */
    /* JADX WARN: Removed duplicated region for block: B:466:0x0a77  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x02a7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:498:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void createDeleteMessagesAlert(final org.telegram.p023ui.ActionBar.BaseFragment r47, org.telegram.tgnet.TLRPC.User r48, final org.telegram.tgnet.TLRPC.Chat r49, final org.telegram.tgnet.TLRPC.EncryptedChat r50, final org.telegram.tgnet.TLRPC.ChatFull r51, final long r52, final org.telegram.messenger.MessageObject r54, final android.util.SparseArray[] r55, final org.telegram.messenger.MessageObject.GroupedMessages r56, final int r57, final int r58, org.telegram.tgnet.TLRPC.ChannelParticipant[] r59, final java.lang.Runnable r60, final java.lang.Runnable r61, final org.telegram.ui.ActionBar.Theme.ResourcesProvider r62) {
        /*
            Method dump skipped, instructions count: 2733
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.AlertsCreator.createDeleteMessagesAlert(org.telegram.ui.ActionBar.BaseFragment, org.telegram.tgnet.TLRPC$User, org.telegram.tgnet.TLRPC$Chat, org.telegram.tgnet.TLRPC$EncryptedChat, org.telegram.tgnet.TLRPC$ChatFull, long, org.telegram.messenger.MessageObject, android.util.SparseArray[], org.telegram.messenger.MessageObject$GroupedMessages, int, int, org.telegram.tgnet.TLRPC$ChannelParticipant[], java.lang.Runnable, java.lang.Runnable, org.telegram.ui.ActionBar.Theme$ResourcesProvider):void");
    }

    public static /* synthetic */ boolean $r8$lambda$icfEa4bzLrXV9Nn1ZYZltuxy5ts(MessageObject messageObject) {
        return messageObject.isSent() && messageObject.messageOwner.ayuDeleted;
    }

    public static /* synthetic */ void $r8$lambda$SbAeQR8z7xPUrUlG7D6KetppSyA(MessageObject.GroupedMessages groupedMessages, int i, BaseFragment baseFragment, int i2, int i3, MessageObject messageObject, AlertDialog alertDialog, int i4) {
        if (groupedMessages != null && !groupedMessages.messages.isEmpty()) {
            SendMessagesHelper.getInstance(i).editMessage(groupedMessages.messages.get(0), null, false, baseFragment, null, i2 + i3, i3);
        } else {
            SendMessagesHelper.getInstance(i).editMessage(messageObject, null, false, baseFragment, null, i2 + i3, i3);
        }
    }

    public static /* synthetic */ void $r8$lambda$WB0T4LCsyhHOT2nc8aYuVHfQQkI(long j, boolean z, int i, MessageObject messageObject, MessageObject.GroupedMessages groupedMessages, TLRPC.EncryptedChat encryptedChat, long j2, int i2, int i3, SparseArray[] sparseArrayArr, Runnable runnable, AlertDialog alertDialog, int i4) {
        ArrayList<Long> arrayList;
        TLRPC.Peer peer;
        long clientUserId = z ? UserConfig.getInstance(i).getClientUserId() : j;
        ArrayList<Long> arrayList2 = null;
        long j3 = 0;
        if (messageObject != null) {
            ArrayList<Integer> arrayList3 = new ArrayList<>();
            if (groupedMessages != null) {
                for (int i5 = 0; i5 < groupedMessages.messages.size(); i5++) {
                    MessageObject messageObject2 = groupedMessages.messages.get(i5);
                    arrayList3.add(Integer.valueOf(messageObject2.getId()));
                    if (encryptedChat != null && messageObject2.messageOwner.random_id != 0 && messageObject2.type != 10) {
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList<>();
                        }
                        arrayList2.add(Long.valueOf(messageObject2.messageOwner.random_id));
                    }
                }
            } else {
                arrayList3.add(Integer.valueOf(messageObject.getId()));
                if (encryptedChat != null && messageObject.messageOwner.random_id != 0 && messageObject.type != 10) {
                    arrayList2 = new ArrayList<>();
                    arrayList2.add(Long.valueOf(messageObject.messageOwner.random_id));
                }
            }
            MessagesController.getInstance(i).deleteMessages(arrayList3, arrayList2, encryptedChat, (j2 == 0 || (peer = messageObject.messageOwner.peer_id) == null || peer.chat_id != (-j2)) ? clientUserId : j2, i2, true, i3);
        } else {
            int i6 = 1;
            while (i6 >= 0) {
                ArrayList<Integer> arrayList4 = new ArrayList<>();
                for (int i7 = 0; i7 < sparseArrayArr[i6].size(); i7++) {
                    arrayList4.add(Integer.valueOf(sparseArrayArr[i6].keyAt(i7)));
                }
                if (encryptedChat != null) {
                    ArrayList<Long> arrayList5 = new ArrayList<>();
                    int i8 = 0;
                    while (i8 < sparseArrayArr[i6].size()) {
                        MessageObject messageObject3 = (MessageObject) sparseArrayArr[i6].valueAt(i8);
                        long j4 = j3;
                        long j5 = messageObject3.messageOwner.random_id;
                        if (j5 != j4 && messageObject3.type != 10) {
                            arrayList5.add(Long.valueOf(j5));
                        }
                        i8++;
                        j3 = j4;
                    }
                    arrayList = arrayList5;
                } else {
                    arrayList = null;
                }
                long j6 = j3;
                MessagesController.getInstance(i).deleteMessages(arrayList4, arrayList, encryptedChat, (i6 != 1 || j2 == j6) ? clientUserId : j2, i2, true, i3);
                sparseArrayArr[i6].clear();
                i6--;
                j3 = j6;
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public static /* synthetic */ void $r8$lambda$ySGyDC6ziTdW1IhG2Y9NOrYtUmk(Runnable runnable, DialogInterface dialogInterface) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public static /* synthetic */ TLObject $r8$lambda$OMg1_OfuUxXOZ3rsQcPi0Tz9rxg(int i, long j) {
        if (j > 0) {
            return MessagesController.getInstance(i).getUser(Long.valueOf(j));
        }
        return MessagesController.getInstance(i).getChat(Long.valueOf(-j));
    }

    /* renamed from: $r8$lambda$C_h9-meAn89lAQ1geSGeuXlkX2c, reason: not valid java name */
    public static /* synthetic */ boolean m7606$r8$lambda$C_h9meAn89lAQ1geSGeuXlkX2c(long j, TLObject tLObject) {
        if (tLObject instanceof TLRPC.User) {
            return ((TLRPC.User) tLObject).f1734id != j;
        }
        if (tLObject instanceof TLRPC.Chat) {
            return !ChatObject.hasAdminRights((TLRPC.Chat) tLObject);
        }
        return false;
    }

    public static /* synthetic */ void $r8$lambda$G7rUcEf5wlvuP3MRPHjSPbF9DR0(int[] iArr, int[] iArr2, int i, TLObject tLObject, TLRPC.ChannelParticipant[] channelParticipantArr, int i2, AlertDialog[] alertDialogArr, BaseFragment baseFragment, TLRPC.User user, TLRPC.Chat chat, TLRPC.EncryptedChat encryptedChat, TLRPC.ChatFull chatFull, long j, MessageObject messageObject, SparseArray[] sparseArrayArr, MessageObject.GroupedMessages groupedMessages, int i3, int i4, Runnable runnable, Runnable runnable2, Theme.ResourcesProvider resourcesProvider) {
        iArr[0] = iArr[0] + 1;
        iArr2[i] = 0;
        if (tLObject != null) {
            channelParticipantArr[i] = ((TLRPC.TL_channels_channelParticipant) tLObject).participant;
        }
        if (iArr[0] == i2) {
            try {
                alertDialogArr[0].dismiss();
            } catch (Throwable unused) {
            }
            alertDialogArr[0] = null;
            createDeleteMessagesAlert(baseFragment, user, chat, encryptedChat, chatFull, j, messageObject, sparseArrayArr, groupedMessages, i3, i4, channelParticipantArr, runnable, runnable2, resourcesProvider);
        }
    }

    /* renamed from: $r8$lambda$B1d0njum2ECOST0ys_18uMHj-o0, reason: not valid java name */
    public static /* synthetic */ void m7603$r8$lambda$B1d0njum2ECOST0ys_18uMHjo0(AlertDialog[] alertDialogArr, final int[] iArr, final int i, final Runnable runnable, BaseFragment baseFragment) {
        AlertDialog alertDialog = alertDialogArr[0];
        if (alertDialog == null) {
            return;
        }
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda144
            @Override // android.content.DialogInterface.OnCancelListener
            public final void onCancel(DialogInterface dialogInterface) {
                AlertsCreator.$r8$lambda$RfSJaBKFkqFx1JOg27Gjmcnh3Zk(iArr, i, runnable, dialogInterface);
            }
        });
        baseFragment.showDialog(alertDialogArr[0]);
    }

    public static /* synthetic */ void $r8$lambda$RfSJaBKFkqFx1JOg27Gjmcnh3Zk(int[] iArr, int i, Runnable runnable, DialogInterface dialogInterface) {
        for (int i2 : iArr) {
            if (i2 != 0) {
                ConnectionsManager.getInstance(i).cancelRequest(i2, true);
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public static /* synthetic */ void $r8$lambda$ak3hNMFD9r6ixNj2CJ4RaWXcL6c(boolean[] zArr, View view) {
        boolean z = !zArr[0];
        zArr[0] = z;
        ((CheckBoxCell) view).setChecked(z, true);
    }

    /* renamed from: $r8$lambda$di03UJra9PrBF-u8vDG48iOWKFQ, reason: not valid java name */
    public static /* synthetic */ void m7640$r8$lambda$di03UJra9PrBFu8vDG48iOWKFQ(boolean[] zArr, View view) {
        boolean z = !zArr[0];
        zArr[0] = z;
        ((CheckBoxCell) view).setChecked(z, true);
    }

    /* renamed from: $r8$lambda$Y5Wh4tsx2ME-xV6I7PfhURBPQf4, reason: not valid java name */
    public static /* synthetic */ boolean m7633$r8$lambda$Y5Wh4tsx2MExV6I7PfhURBPQf4(SparseArray sparseArray) {
        return sparseArray.size() > 0 && ((MessageObject) sparseArray.valueAt(0)).isSent() && !((MessageObject) sparseArray.valueAt(0)).messageOwner.ayuDeleted;
    }

    public static /* synthetic */ void $r8$lambda$lNxYtTPqlcpo8nahWz1xpxiZ3QQ(boolean[] zArr, View view) {
        boolean z = !zArr[0];
        zArr[0] = z;
        ((CheckBoxCell) view).setChecked(z, true);
    }

    public static /* synthetic */ void $r8$lambda$GE1CbMwKgkxxygOrkm6geyRqu00(boolean[] zArr, final long j, boolean z, final int i, MessageObject messageObject, MessageObject.GroupedMessages groupedMessages, TLRPC.EncryptedChat encryptedChat, long j2, int i2, boolean[] zArr2, int i3, SparseArray[] sparseArrayArr, Runnable runnable, AlertDialog alertDialog, int i4) {
        ArrayList<Long> arrayList;
        long j3;
        char c;
        ArrayList<Long> arrayList2;
        TLRPC.Peer peer;
        ArrayList<Long> arrayList3;
        long j4;
        char c2 = 0;
        if (zArr[0]) {
            AyuState.setHideSelection(true, 1);
        }
        long clientUserId = z ? UserConfig.getInstance(i).getClientUserId() : j;
        long j5 = 0;
        if (messageObject != null) {
            final ArrayList<Integer> arrayList4 = new ArrayList<>();
            if (groupedMessages != null) {
                int i5 = 0;
                ArrayList<Long> arrayList5 = null;
                while (i5 < groupedMessages.messages.size()) {
                    MessageObject messageObject2 = groupedMessages.messages.get(i5);
                    arrayList4.add(Integer.valueOf(messageObject2.getId()));
                    if (!zArr[c2]) {
                        AyuState.permitDeleteMessage(clientUserId, messageObject2.getId());
                    }
                    if (encryptedChat != null) {
                        arrayList3 = arrayList5;
                        if (messageObject2.messageOwner.random_id == j5 || messageObject2.type == 10) {
                            j4 = j5;
                        } else {
                            arrayList5 = arrayList3 == null ? new ArrayList<>() : arrayList3;
                            j4 = j5;
                            arrayList5.add(Long.valueOf(messageObject2.messageOwner.random_id));
                            i5++;
                            j5 = j4;
                            c2 = 0;
                        }
                    } else {
                        arrayList3 = arrayList5;
                        j4 = j5;
                    }
                    arrayList5 = arrayList3;
                    i5++;
                    j5 = j4;
                    c2 = 0;
                }
                j3 = j5;
                c = 0;
                arrayList2 = arrayList5;
            } else {
                j3 = 0;
                c = 0;
                arrayList4.add(Integer.valueOf(messageObject.getId()));
                if (!zArr[0]) {
                    AyuState.permitDeleteMessage(clientUserId, messageObject.getId());
                }
                if (encryptedChat == null || messageObject.messageOwner.random_id == 0 || messageObject.type == 10) {
                    arrayList2 = null;
                } else {
                    ArrayList<Long> arrayList6 = new ArrayList<>();
                    arrayList6.add(Long.valueOf(messageObject.messageOwner.random_id));
                    arrayList2 = arrayList6;
                }
            }
            long j6 = (j2 == j3 || (peer = messageObject.messageOwner.peer_id) == null || peer.chat_id != (-j2)) ? clientUserId : j2;
            final Long lValueOf = Long.valueOf(j6);
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda145
                @Override // java.lang.Runnable
                public final void run() {
                    NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(AyuConstants.MESSAGES_DELETED_NOTIFICATION, lValueOf, arrayList4);
                }
            });
            MessagesController.getInstance(i).deleteMessages(arrayList4, arrayList2, encryptedChat, j6, i2, zArr2[c], i3);
        } else {
            int i6 = 1;
            while (i6 >= 0) {
                final ArrayList<Integer> arrayList7 = new ArrayList<>();
                for (int i7 = 0; i7 < sparseArrayArr[i6].size(); i7++) {
                    arrayList7.add(Integer.valueOf(sparseArrayArr[i6].keyAt(i7)));
                    if (!zArr[0]) {
                        AyuState.permitDeleteMessage(j, sparseArrayArr[i6].keyAt(i7));
                    }
                }
                if (encryptedChat != null) {
                    ArrayList<Long> arrayList8 = new ArrayList<>();
                    int i8 = 0;
                    while (i8 < sparseArrayArr[i6].size()) {
                        MessageObject messageObject3 = (MessageObject) sparseArrayArr[i6].valueAt(i8);
                        long j7 = clientUserId;
                        long j8 = messageObject3.messageOwner.random_id;
                        if (j8 != 0 && messageObject3.type != 10) {
                            arrayList8.add(Long.valueOf(j8));
                        }
                        i8++;
                        clientUserId = j7;
                    }
                    arrayList = arrayList8;
                } else {
                    arrayList = null;
                }
                long j9 = clientUserId;
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda146
                    @Override // java.lang.Runnable
                    public final void run() {
                        NotificationCenter.getInstance(i).lambda$postNotificationNameOnUIThread$1(AyuConstants.MESSAGES_DELETED_NOTIFICATION, Long.valueOf(j), arrayList7);
                    }
                });
                MessagesController.getInstance(i).deleteMessages(arrayList7, arrayList, encryptedChat, (i6 != 1 || j2 == 0) ? j9 : j2, i2, zArr2[0], i3);
                sparseArrayArr[i6].clear();
                i6--;
                clientUserId = j9;
            }
        }
        if (runnable != null) {
            runnable.run();
        }
    }

    public static /* synthetic */ void $r8$lambda$QiwagM6qv9l8u2nCOLEXeZNqGHE(Runnable runnable, DialogInterface dialogInterface) {
        if (runnable != null) {
            runnable.run();
        }
    }

    public static void createThemeCreateDialog(final BaseFragment baseFragment, int i, final Theme.ThemeInfo themeInfo, final Theme.ThemeAccent themeAccent) {
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        Activity parentActivity = baseFragment.getParentActivity();
        final EditTextBoldCursor editTextBoldCursor = new EditTextBoldCursor(parentActivity);
        editTextBoldCursor.setBackground(null);
        editTextBoldCursor.setLineColors(Theme.getColor(Theme.key_dialogInputField), Theme.getColor(Theme.key_dialogInputFieldActivated), Theme.getColor(Theme.key_text_RedBold));
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setTitle(LocaleController.getString(C2369R.string.NewTheme));
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Create), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda93
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                AlertsCreator.$r8$lambda$rFVIcafL96lJ1Lj3QsA2wBt_YbQ(alertDialog, i2);
            }
        });
        LinearLayout linearLayout = new LinearLayout(parentActivity);
        linearLayout.setOrientation(1);
        builder.setView(linearLayout);
        TextView textView = new TextView(parentActivity);
        if (i != 0) {
            textView.setText(AndroidUtilities.replaceTags(LocaleController.getString(C2369R.string.EnterThemeNameEdit)));
        } else {
            textView.setText(LocaleController.getString(C2369R.string.EnterThemeName));
        }
        textView.setTextSize(1, 16.0f);
        textView.setPadding(AndroidUtilities.m1146dp(23.0f), AndroidUtilities.m1146dp(12.0f), AndroidUtilities.m1146dp(23.0f), AndroidUtilities.m1146dp(6.0f));
        int i2 = Theme.key_dialogTextBlack;
        textView.setTextColor(Theme.getColor(i2));
        linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2));
        editTextBoldCursor.setTextSize(1, 16.0f);
        editTextBoldCursor.setTextColor(Theme.getColor(i2));
        editTextBoldCursor.setMaxLines(1);
        editTextBoldCursor.setLines(1);
        editTextBoldCursor.setInputType(16385);
        editTextBoldCursor.setGravity(51);
        editTextBoldCursor.setSingleLine(true);
        editTextBoldCursor.setImeOptions(6);
        editTextBoldCursor.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        editTextBoldCursor.setCursorSize(AndroidUtilities.m1146dp(20.0f));
        editTextBoldCursor.setCursorWidth(1.5f);
        editTextBoldCursor.setPadding(0, AndroidUtilities.m1146dp(4.0f), 0, 0);
        linearLayout.addView(editTextBoldCursor, LayoutHelper.createLinear(-1, 36, 51, 24, 6, 24, 0));
        editTextBoldCursor.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda94
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView2, int i3, KeyEvent keyEvent) {
                return AlertsCreator.m7656$r8$lambda$zrjqH_CGo2jyQ2uE3b6xipC6T8(textView2, i3, keyEvent);
            }
        });
        editTextBoldCursor.setText(generateThemeName(themeAccent));
        editTextBoldCursor.setSelection(editTextBoldCursor.length());
        final AlertDialog alertDialogCreate = builder.create();
        alertDialogCreate.setOnShowListener(new DialogInterface.OnShowListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda95
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda147
                    @Override // java.lang.Runnable
                    public final void run() {
                        AlertsCreator.$r8$lambda$YQ7DPA_Tm2oAZnSzyiRAZcUXIFw(editTextBoldCursor);
                    }
                });
            }
        });
        baseFragment.showDialog(alertDialogCreate);
        editTextBoldCursor.requestFocus();
        alertDialogCreate.getButton(-1).setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda96
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) throws Throwable {
                AlertsCreator.$r8$lambda$_THr2S3f1kCro0JxJQC6RAotQGA(baseFragment, editTextBoldCursor, themeAccent, themeInfo, alertDialogCreate, view);
            }
        });
    }

    /* renamed from: $r8$lambda$zrjqH_CGo2j-yQ2uE3b6xipC6T8, reason: not valid java name */
    public static /* synthetic */ boolean m7656$r8$lambda$zrjqH_CGo2jyQ2uE3b6xipC6T8(TextView textView, int i, KeyEvent keyEvent) {
        AndroidUtilities.hideKeyboard(textView);
        return false;
    }

    public static /* synthetic */ void $r8$lambda$YQ7DPA_Tm2oAZnSzyiRAZcUXIFw(EditTextBoldCursor editTextBoldCursor) {
        editTextBoldCursor.requestFocus();
        AndroidUtilities.showKeyboard(editTextBoldCursor);
    }

    public static /* synthetic */ void $r8$lambda$_THr2S3f1kCro0JxJQC6RAotQGA(final BaseFragment baseFragment, final EditTextBoldCursor editTextBoldCursor, Theme.ThemeAccent themeAccent, Theme.ThemeInfo themeInfo, final AlertDialog alertDialog, View view) throws Throwable {
        if (baseFragment.getParentActivity() == null) {
            return;
        }
        if (editTextBoldCursor.length() == 0) {
            editTextBoldCursor.performHapticFeedback(3, 2);
            AndroidUtilities.shakeView(editTextBoldCursor);
            return;
        }
        if (baseFragment instanceof ThemePreviewActivity) {
            Theme.applyPreviousTheme();
            baseFragment.lambda$onBackPressed$371();
        }
        if (themeAccent != null) {
            themeInfo.setCurrentAccentId(themeAccent.f1786id);
            Theme.refreshThemeColors();
            Utilities.searchQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda143
                @Override // java.lang.Runnable
                public final void run() {
                    AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda184
                        @Override // java.lang.Runnable
                        public final void run() throws Throwable {
                            AlertsCreator.processCreate(editTextBoldCursor, alertDialog, baseFragment);
                        }
                    });
                }
            });
            return;
        }
        processCreate(editTextBoldCursor, alertDialog, baseFragment);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void processCreate(EditTextBoldCursor editTextBoldCursor, AlertDialog alertDialog, BaseFragment baseFragment) throws Throwable {
        if (baseFragment == null || baseFragment.getParentActivity() == null) {
            return;
        }
        AndroidUtilities.hideKeyboard(editTextBoldCursor);
        Theme.ThemeInfo themeInfoCreateNewTheme = Theme.createNewTheme(editTextBoldCursor.getText().toString());
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.themeListUpdated, new Object[0]);
        new ThemeEditorView().show(baseFragment.getParentActivity(), themeInfoCreateNewTheme);
        alertDialog.dismiss();
        SharedPreferences globalMainSettings = MessagesController.getGlobalMainSettings();
        if (globalMainSettings.getBoolean("themehint", false)) {
            return;
        }
        globalMainSettings.edit().putBoolean("themehint", true).apply();
        try {
            Toast.makeText(baseFragment.getParentActivity(), LocaleController.getString(C2369R.string.CreateNewThemeHelp), 1).show();
        } catch (Exception e) {
            FileLog.m1160e(e);
        }
    }

    private static String generateThemeName(Theme.ThemeAccent themeAccent) {
        int i;
        List listAsList = Arrays.asList("Ancient", "Antique", "Autumn", "Baby", "Barely", "Baroque", "Blazing", "Blushing", "Bohemian", "Bubbly", "Burning", "Buttered", "Classic", "Clear", "Cool", "Cosmic", "Cotton", "Cozy", "Crystal", "Dark", "Daring", "Darling", "Dawn", "Dazzling", "Deep", "Deepest", "Delicate", "Delightful", "Divine", "Double", "Downtown", "Dreamy", "Dusky", "Dusty", "Electric", "Enchanted", "Endless", "Evening", "Fantastic", "Flirty", "Forever", "Frigid", "Frosty", "Frozen", "Gentle", "Heavenly", "Hyper", "Icy", "Infinite", "Innocent", "Instant", "Luscious", "Lunar", "Lustrous", "Magic", "Majestic", "Mambo", "Midnight", "Millenium", "Morning", "Mystic", "Natural", "Neon", "Night", "Opaque", "Paradise", "Perfect", "Perky", "Polished", "Powerful", "Rich", "Royal", "Sheer", "Simply", "Sizzling", "Solar", "Sparkling", "Splendid", "Spicy", "Spring", "Stellar", "Sugared", "Summer", "Sunny", "Super", "Sweet", "Tender", "Tenacious", "Tidal", "Toasted", "Totally", "Tranquil", "Tropical", "True", "Twilight", "Twinkling", "Ultimate", "Ultra", "Velvety", "Vibrant", "Vintage", "Virtual", "Warm", "Warmest", "Whipped", "Wild", "Winsome");
        List listAsList2 = Arrays.asList("Ambrosia", "Attack", "Avalanche", "Blast", "Bliss", "Blossom", "Blush", "Burst", "Butter", "Candy", "Carnival", "Charm", "Chiffon", "Cloud", "Comet", "Delight", "Dream", "Dust", "Fantasy", "Flame", "Flash", "Fire", "Freeze", "Frost", "Glade", "Glaze", "Gleam", "Glimmer", "Glitter", "Glow", "Grande", "Haze", "Highlight", "Ice", "Illusion", "Intrigue", "Jewel", "Jubilee", "Kiss", "Lights", "Lollypop", "Love", "Luster", "Madness", "Matte", "Mirage", "Mist", "Moon", "Muse", "Myth", "Nectar", "Nova", "Parfait", "Passion", "Pop", "Rain", "Reflection", "Rhapsody", "Romance", "Satin", "Sensation", "Silk", "Shine", "Shadow", "Shimmer", "Sky", "Spice", "Star", "Sugar", "Sunrise", "Sunset", "Sun", "Twist", "Unbound", "Velvet", "Vibrant", "Waters", "Wine", "Wink", "Wonder", "Zone");
        HashMap map = new HashMap();
        map.put(9306112, "Berry");
        map.put(14598550, "Brandy");
        map.put(8391495, "Cherry");
        map.put(16744272, "Coral");
        map.put(14372985, "Cranberry");
        map.put(14423100, "Crimson");
        map.put(14725375, "Mauve");
        map.put(16761035, "Pink");
        map.put(16711680, "Red");
        map.put(16711807, "Rose");
        map.put(8406555, "Russet");
        map.put(16720896, "Scarlet");
        map.put(15856113, "Seashell");
        map.put(16724889, "Strawberry");
        map.put(16760576, "Amber");
        map.put(15438707, "Apricot");
        map.put(16508850, "Banana");
        map.put(10601738, "Citrus");
        map.put(11560192, "Ginger");
        map.put(16766720, "Gold");
        map.put(16640272, "Lemon");
        map.put(16753920, "Orange");
        map.put(16770484, "Peach");
        map.put(16739155, "Persimmon");
        map.put(14996514, "Sunflower");
        map.put(15893760, "Tangerine");
        map.put(16763004, "Topaz");
        map.put(16776960, "Yellow");
        map.put(3688720, "Clover");
        map.put(8628829, "Cucumber");
        map.put(5294200, "Emerald");
        map.put(11907932, "Olive");
        map.put(65280, "Green");
        map.put(43115, "Jade");
        map.put(2730887, "Jungle");
        map.put(12582656, "Lime");
        map.put(776785, "Malachite");
        map.put(10026904, "Mint");
        map.put(11394989, "Moss");
        map.put(3234721, "Azure");
        map.put(255, "Blue");
        map.put(18347, "Cobalt");
        map.put(5204422, "Indigo");
        map.put(96647, "Lagoon");
        map.put(7461346, "Aquamarine");
        map.put(1182351, "Ultramarine");
        map.put(128, "Navy");
        map.put(3101086, "Sapphire");
        map.put(7788522, "Sky");
        map.put(32896, "Teal");
        map.put(4251856, "Turquoise");
        map.put(10053324, "Amethyst");
        map.put(5046581, "Blackberry");
        map.put(6373457, "Eggplant");
        map.put(13148872, "Lilac");
        map.put(11894492, "Lavender");
        map.put(13421823, "Periwinkle");
        map.put(8663417, "Plum");
        map.put(6684825, "Purple");
        map.put(14204888, "Thistle");
        map.put(14315734, "Orchid");
        map.put(2361920, "Violet");
        map.put(4137225, "Bronze");
        map.put(3604994, "Chocolate");
        map.put(8077056, "Cinnamon");
        map.put(3153694, "Cocoa");
        map.put(7365973, "Coffee");
        map.put(7956873, "Rum");
        map.put(5113350, "Mahogany");
        map.put(7875865, "Mocha");
        map.put(12759680, "Sand");
        map.put(8924439, "Sienna");
        map.put(7864585, "Maple");
        map.put(15787660, "Khaki");
        map.put(12088115, "Copper");
        map.put(12144200, "Chestnut");
        map.put(15653316, "Almond");
        map.put(16776656, "Cream");
        map.put(12186367, "Diamond");
        map.put(11109127, "Honey");
        map.put(16777200, "Ivory");
        map.put(15392968, "Pearl");
        map.put(15725299, "Porcelain");
        map.put(13745832, "Vanilla");
        map.put(16777215, "White");
        map.put(8421504, "Gray");
        map.put(0, "Black");
        map.put(15266260, "Chrome");
        map.put(3556687, "Charcoal");
        map.put(789277, "Ebony");
        map.put(12632256, "Silver");
        map.put(16119285, "Smoke");
        map.put(2499381, "Steel");
        map.put(5220413, "Apple");
        map.put(8434628, "Glacier");
        map.put(16693933, "Melon");
        map.put(12929932, "Mulberry");
        map.put(11126466, "Opal");
        map.put(5547512, "Blue");
        Theme.ThemeAccent accent = themeAccent == null ? Theme.getCurrentTheme().getAccent(false) : themeAccent;
        if (accent == null || (i = accent.accentColor) == 0) {
            i = AndroidUtilities.calcDrawableColor(Theme.getCachedWallpaper())[0];
        }
        int iRed = Color.red(i);
        int iGreen = Color.green(i);
        int iBlue = Color.blue(i);
        String str = null;
        int i2 = ConnectionsManager.DEFAULT_DATACENTER_ID;
        for (Map.Entry entry : map.entrySet()) {
            Integer num = (Integer) entry.getKey();
            int iRed2 = Color.red(num.intValue());
            int i3 = (iRed + iRed2) / 2;
            int i4 = iRed - iRed2;
            int iGreen2 = iGreen - Color.green(num.intValue());
            int iBlue2 = iBlue - Color.blue(num.intValue());
            int i5 = ((((i3 + 512) * i4) * i4) >> 8) + (iGreen2 * 4 * iGreen2) + ((((767 - i3) * iBlue2) * iBlue2) >> 8);
            if (i5 < i2) {
                str = (String) entry.getValue();
                i2 = i5;
            }
        }
        if (Utilities.random.nextInt() % 2 == 0) {
            return ((String) listAsList.get(Utilities.random.nextInt(listAsList.size()))) + " " + str;
        }
        return str + " " + ((String) listAsList2.get(Utilities.random.nextInt(listAsList2.size())));
    }

    public static void showDeclineSuggestedPostDialog(BaseFragment baseFragment, long j, boolean z, final Utilities.Callback callback) {
        final Context context = baseFragment.getContext();
        AlertDialog.Builder builder = z ? new AlertDialogDecor.Builder(context) : new AlertDialog.Builder(context);
        builder.setTitle(LocaleController.getString(C2369R.string.SuggestedMessageDecline));
        builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.SuggestedMessageDeclineInfo, MessagesController.getInstance(UserConfig.selectedAccount).getPeerName(j))));
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setClipChildren(false);
        final EditText editText = new EditText(context);
        editText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        editText.setHint(LocaleController.getString(C2369R.string.SuggestedMessageDeclineReasonHint));
        editText.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        editText.setTextSize(1, 16.0f);
        editText.setBackground(Theme.createEditTextDrawable(context, true));
        editText.setMaxLines(4);
        editText.setRawInputType(147457);
        editText.setImeOptions(6);
        editText.setFilters(new InputFilter[]{new CodepointsLengthInputFilter(255) { // from class: org.telegram.ui.Components.AlertsCreator.57
            @Override // org.telegram.p023ui.Components.CodepointsLengthInputFilter, android.text.InputFilter
            public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
                Vibrator vibrator;
                CharSequence charSequenceFilter = super.filter(charSequence, i, i2, spanned, i3, i4);
                if (charSequenceFilter != null && charSequence != null && charSequenceFilter.length() != charSequence.length() && (vibrator = (Vibrator) context.getSystemService("vibrator")) != null) {
                    vibrator.vibrate(200L);
                }
                return charSequenceFilter;
            }
        }});
        editText.setPadding(LocaleController.isRTL ? AndroidUtilities.m1146dp(24.0f) : 0, AndroidUtilities.m1146dp(8.0f), LocaleController.isRTL ? 0 : AndroidUtilities.m1146dp(24.0f), AndroidUtilities.m1146dp(8.0f));
        editText.setSelection(editText.getText().toString().length());
        builder.setView(frameLayout);
        builder.setPositiveButton(LocaleController.getString(C2369R.string.Decline), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda124
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i) {
                AlertsCreator.$r8$lambda$5ELZ7aNDj2uuII0wjjUFErNqihU(callback, editText, alertDialog, i);
            }
        });
        builder.setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null);
        builder.setOnPreDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda125
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AndroidUtilities.hideKeyboard(editText);
            }
        });
        frameLayout.addView(editText, LayoutHelper.createFrame(-1, -2.0f, 0, 23.0f, 0.0f, 23.0f, 21.0f));
        editText.requestFocus();
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda126
            @Override // java.lang.Runnable
            public final void run() {
                AndroidUtilities.showKeyboard(editText);
            }
        }, 100L);
        AlertDialog alertDialogCreate = builder.create();
        baseFragment.showDialog(alertDialogCreate);
        TextView textView = (TextView) alertDialogCreate.getButton(-1);
        if (textView != null) {
            textView.setTextColor(Theme.getColor(Theme.key_text_RedBold));
        }
    }

    public static /* synthetic */ void $r8$lambda$5ELZ7aNDj2uuII0wjjUFErNqihU(Utilities.Callback callback, EditText editText, AlertDialog alertDialog, int i) {
        alertDialog.dismiss();
        if (callback != null) {
            callback.run(editText.getText().toString());
        }
    }

    public static BottomSheet.Builder createSuggestedMessageDatePickerDialog(Context context, long j, ScheduleDatePickerDelegate scheduleDatePickerDelegate, Theme.ResourcesProvider resourcesProvider, int i) {
        return createSuggestedMessageDatePickerDialog(context, j, scheduleDatePickerDelegate, null, new ScheduleDatePickerColors(), resourcesProvider, i);
    }

    public static BottomSheet.Builder createSuggestedMessageDatePickerDialog(Context context, long j, final ScheduleDatePickerDelegate scheduleDatePickerDelegate, final Runnable runnable, ScheduleDatePickerColors scheduleDatePickerColors, Theme.ResourcesProvider resourcesProvider, int i) {
        long j2;
        Calendar calendar;
        if (context == null) {
            return null;
        }
        final BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
        builder.setApplyBottomPadding(false);
        final NumberPicker numberPicker = new NumberPicker(context, resourcesProvider);
        numberPicker.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker.setTextOffset(AndroidUtilities.m1146dp(10.0f));
        numberPicker.setItemCount(5);
        final NumberPicker numberPicker2 = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.58
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i2) {
                return LocaleController.formatPluralString("Hours", i2, new Object[0]);
            }
        };
        numberPicker2.setWrapSelectorWheel(true);
        numberPicker2.setAllItemsCount(24);
        numberPicker2.setItemCount(5);
        numberPicker2.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker2.setTextOffset(-AndroidUtilities.m1146dp(10.0f));
        final NumberPicker numberPicker3 = new NumberPicker(context, resourcesProvider) { // from class: org.telegram.ui.Components.AlertsCreator.59
            @Override // org.telegram.p023ui.Components.NumberPicker
            protected CharSequence getContentDescription(int i2) {
                return LocaleController.formatPluralString("Minutes", i2, new Object[0]);
            }
        };
        numberPicker3.setWrapSelectorWheel(true);
        numberPicker3.setAllItemsCount(60);
        numberPicker3.setItemCount(5);
        numberPicker3.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker3.setTextOffset(-AndroidUtilities.m1146dp(34.0f));
        LinearLayout linearLayout = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.60
            boolean ignoreLayout = false;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i2, int i3) {
                this.ignoreLayout = true;
                Point point = AndroidUtilities.displaySize;
                int i4 = point.x > point.y ? 3 : 5;
                numberPicker.setItemCount(i4);
                numberPicker2.setItemCount(i4);
                numberPicker3.setItemCount(i4);
                numberPicker.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i4;
                numberPicker2.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i4;
                numberPicker3.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * i4;
                this.ignoreLayout = false;
                super.onMeasure(i2, i3);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        linearLayout.setOrientation(1);
        LinearLayout linearLayout2 = new LinearLayout(context);
        linearLayout2.setOrientation(1);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 22, 4));
        TextView textView = new TextView(context);
        textView.setText(LocaleController.getString(i == 1 ? C2369R.string.SuggestedPostAcceptTitle : C2369R.string.PostSuggestionsAddTime));
        textView.setTextColor(scheduleDatePickerColors.textColor);
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        linearLayout2.addView(textView, LayoutHelper.createLinear(-2, -2, 51, 0, 12, 0, 0));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda175
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.m7609$r8$lambda$E8kxbR6a2XNJyBIRi1p_nJkuas(view, motionEvent);
            }
        });
        TextView textView2 = new TextView(context);
        textView2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2, resourcesProvider));
        textView2.setTextSize(1, 14.0f);
        textView2.setText(LocaleController.getString(C2369R.string.PostSuggestionsAddTimeHint));
        linearLayout2.addView(textView2, LayoutHelper.createLinear(-2, -2, 51, 0, 2, 0, 0));
        textView2.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda176
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.$r8$lambda$q7040SYtffDkeglTQxTntBztBeU(view, motionEvent);
            }
        });
        LinearLayout linearLayout3 = new LinearLayout(context);
        linearLayout3.setOrientation(0);
        linearLayout3.setWeightSum(1.0f);
        linearLayout.addView(linearLayout3, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
        long jCurrentTimeMillis = System.currentTimeMillis();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(jCurrentTimeMillis);
        final int i2 = calendar2.get(1);
        AppGlobalConfig.ConfigTime configTime = MessagesController.getInstance(UserConfig.selectedAccount).config.starsSuggestedPostFutureMin;
        TimeUnit timeUnit = TimeUnit.SECONDS;
        final long j3 = configTime.get(timeUnit) * 2;
        final long j4 = MessagesController.getInstance(UserConfig.selectedAccount).config.starsSuggestedPostFutureMax.get(timeUnit) - 86400;
        final TextView textView3 = new TextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.61
            @Override // android.widget.TextView, android.view.View
            public CharSequence getAccessibilityClassName() {
                return Button.class.getName();
            }
        };
        linearLayout3.addView(numberPicker, LayoutHelper.createLinear(0, 270, 0.5f));
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(365);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda177
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i3) {
                return AlertsCreator.$r8$lambda$bwTf_VYkk9XCrxx69GquTe1oe90(i2, i3);
            }
        });
        final int i3 = i == 1 ? 5 : 3;
        NumberPicker.OnValueChangeListener onValueChangeListener = new NumberPicker.OnValueChangeListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda178
            @Override // org.telegram.ui.Components.NumberPicker.OnValueChangeListener
            public final void onValueChange(NumberPicker numberPicker4, int i4, int i5) {
                AlertsCreator.checkScheduleDate(textView3, null, j3, j4, i3, numberPicker, numberPicker2, numberPicker3);
            }
        };
        numberPicker.setOnValueChangedListener(onValueChangeListener);
        numberPicker2.setMinValue(0);
        numberPicker2.setMaxValue(23);
        linearLayout3.addView(numberPicker2, LayoutHelper.createLinear(0, 270, 0.2f));
        numberPicker2.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda179
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i4) {
                return String.format("%02d", Integer.valueOf(i4));
            }
        });
        numberPicker2.setOnValueChangedListener(onValueChangeListener);
        numberPicker3.setMinValue(0);
        numberPicker3.setMaxValue(59);
        numberPicker3.setValue(0);
        numberPicker3.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda180
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i4) {
                return String.format("%02d", Integer.valueOf(i4));
            }
        });
        linearLayout3.addView(numberPicker3, LayoutHelper.createLinear(0, 270, 0.3f));
        numberPicker3.setOnValueChangedListener(onValueChangeListener);
        if (j <= 0 || j == 2147483646) {
            j2 = j3;
            calendar = calendar2;
        } else {
            long j5 = 1000 * j;
            calendar = calendar2;
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            calendar.set(11, 0);
            int timeInMillis = (int) ((j5 - calendar.getTimeInMillis()) / 86400000);
            j2 = j3;
            calendar.setTimeInMillis(j5);
            if (timeInMillis >= 0) {
                numberPicker3.setValue(calendar.get(12));
                numberPicker2.setValue(calendar.get(11));
                numberPicker.setValue(timeInMillis);
            }
        }
        final boolean[] zArr = {true};
        final long j6 = j2;
        checkScheduleDate(textView3, null, j6, j4, i3, numberPicker, numberPicker2, numberPicker3);
        textView3.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView3.setGravity(17);
        textView3.setTextColor(scheduleDatePickerColors.buttonTextColor);
        textView3.setTextSize(1, 14.0f);
        textView3.setTypeface(AndroidUtilities.bold());
        textView3.setBackground(Theme.AdaptiveRipple.filledRect(scheduleDatePickerColors.buttonBackgroundColor, 8.0f));
        linearLayout.addView(textView3, LayoutHelper.createLinear(-1, 48, 83, 16, 15, 16, 4));
        final Calendar calendar3 = calendar;
        textView3.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda181
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.$r8$lambda$XVidzo7E4g5IyfozpwjSvTo0T14(zArr, j6, j4, i3, numberPicker, numberPicker2, numberPicker3, calendar3, scheduleDatePickerDelegate, builder, view);
            }
        });
        ScaleStateListAnimator.apply(textView3, 0.02f, 1.2f);
        TextView textView4 = new TextView(context) { // from class: org.telegram.ui.Components.AlertsCreator.62
            @Override // android.widget.TextView, android.view.View
            public CharSequence getAccessibilityClassName() {
                return Button.class.getName();
            }
        };
        textView4.setPadding(AndroidUtilities.m1146dp(34.0f), 0, AndroidUtilities.m1146dp(34.0f), 0);
        textView4.setGravity(17);
        textView4.setText(LocaleController.getString(i == 1 ? C2369R.string.MessageSuggestionPublishNow : C2369R.string.PostSuggestionsAnytime));
        textView4.setTextColor(scheduleDatePickerColors.buttonBackgroundColor);
        textView4.setTextSize(1, 14.0f);
        textView4.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.m1146dp(8.0f), Theme.getColor(Theme.key_windowBackgroundWhite), Theme.getColor(Theme.key_listSelector)));
        linearLayout.addView(textView4, LayoutHelper.createLinear(-1, 48, 83, 16, 0, 16, 16));
        textView4.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda182
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.$r8$lambda$F9fBaEsSs6C_eubtQdkJ_gMw0k0(zArr, scheduleDatePickerDelegate, builder, view);
            }
        });
        ScaleStateListAnimator.apply(textView4, 0.02f, 1.2f);
        builder.setCustomView(linearLayout);
        BottomSheet bottomSheetShow = builder.show();
        bottomSheetShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda183
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AlertsCreator.$r8$lambda$EUdhw3ZtM2KRErCJ6Du4_DiQUaQ(runnable, zArr, dialogInterface);
            }
        });
        bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
        bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
        return builder;
    }

    /* renamed from: $r8$lambda$E8kx-bR6a2XNJyBIRi1p_nJkuas, reason: not valid java name */
    public static /* synthetic */ boolean m7609$r8$lambda$E8kxbR6a2XNJyBIRi1p_nJkuas(View view, MotionEvent motionEvent) {
        return true;
    }

    public static /* synthetic */ boolean $r8$lambda$q7040SYtffDkeglTQxTntBztBeU(View view, MotionEvent motionEvent) {
        return true;
    }

    public static /* synthetic */ String $r8$lambda$bwTf_VYkk9XCrxx69GquTe1oe90(int i, int i2) {
        if (i2 == 0) {
            return LocaleController.getString(C2369R.string.MessageScheduleToday);
        }
        LocalDate localDatePlusDays = LocalDate.now().plusDays(i2);
        int year = localDatePlusDays.getYear();
        long epochMilli = localDatePlusDays.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
        if (year == i) {
            return LocaleController.getInstance().getFormatterWeek().format(epochMilli) + ", " + LocaleController.getInstance().getFormatterScheduleDay().format(epochMilli);
        }
        return LocaleController.getInstance().getFormatterScheduleYear().format(epochMilli);
    }

    public static /* synthetic */ void $r8$lambda$XVidzo7E4g5IyfozpwjSvTo0T14(boolean[] zArr, long j, long j2, int i, NumberPicker numberPicker, NumberPicker numberPicker2, NumberPicker numberPicker3, Calendar calendar, ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, View view) {
        zArr[0] = false;
        boolean zCheckScheduleDate = checkScheduleDate(null, null, j, j2, i, numberPicker, numberPicker2, numberPicker3);
        calendar.setTimeInMillis(LocalDate.now().plusDays(numberPicker.getValue()).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        calendar.set(11, numberPicker2.getValue());
        calendar.set(12, numberPicker3.getValue());
        if (zCheckScheduleDate) {
            calendar.set(13, 0);
        }
        scheduleDatePickerDelegate.didSelectDate(true, (int) (calendar.getTimeInMillis() / 1000), 0);
        builder.getDismissRunnable().run();
    }

    public static /* synthetic */ void $r8$lambda$F9fBaEsSs6C_eubtQdkJ_gMw0k0(boolean[] zArr, ScheduleDatePickerDelegate scheduleDatePickerDelegate, BottomSheet.Builder builder, View view) {
        zArr[0] = false;
        scheduleDatePickerDelegate.didSelectDate(true, -1, 0);
        builder.getDismissRunnable().run();
    }

    public static /* synthetic */ void $r8$lambda$EUdhw3ZtM2KRErCJ6Du4_DiQUaQ(Runnable runnable, boolean[] zArr, DialogInterface dialogInterface) {
        if (runnable == null || !zArr[0]) {
            return;
        }
        runnable.run();
    }

    public static void showCallsForbidden(Context context, final int i, final long j, final Theme.ResourcesProvider resourcesProvider) {
        BottomSheet.Builder builder = new BottomSheet.Builder(context, false, resourcesProvider);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.setPadding(AndroidUtilities.m1146dp(16.0f), 0, AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(8.0f));
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setClipChildren(false);
        frameLayout.setClipToPadding(false);
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, 92, 17, 0, 0, 0, 0));
        FrameLayout frameLayout2 = new FrameLayout(context);
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(C2369R.drawable.story_link);
        imageView.setScaleX(2.0f);
        imageView.setScaleY(2.0f);
        frameLayout2.addView(imageView, LayoutHelper.createFrame(-1, -1, 17));
        frameLayout2.setBackground(Theme.createCircleDrawable(AndroidUtilities.m1146dp(80.0f), Theme.getColor(Theme.key_featuredStickers_addButton, resourcesProvider)));
        frameLayout.addView(frameLayout2, LayoutHelper.createFrame(80, 80.0f, 1, 0.0f, 12.0f, 0.0f, 0.0f));
        TextView textView = new TextView(context);
        int i2 = Theme.key_windowBackgroundWhiteBlackText;
        textView.setTextColor(Theme.getColor(i2, resourcesProvider));
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        textView.setText(LocaleController.getString(C2369R.string.CallForbiddenInviteLinkTitle));
        textView.setGravity(17);
        linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 32.0f, 16.0f, 32.0f, 8.0f));
        TextView textView2 = new TextView(context);
        textView2.setTextColor(Theme.getColor(i2, resourcesProvider));
        textView2.setTextSize(1, 14.0f);
        textView2.setText(AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.CallForbiddenInviteLinkText, DialogObject.getName(i, j))));
        textView2.setGravity(17);
        linearLayout.addView(textView2, LayoutHelper.createLinear(-1, -2, 32.0f, 0.0f, 32.0f, 18.0f));
        final ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context, resourcesProvider);
        buttonWithCounterView.setText(LocaleController.getString(C2369R.string.CallForbiddenInviteLinkButton), false);
        linearLayout.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 0.0f, 0.0f, 0.0f, 0.0f));
        builder.setCustomView(linearLayout);
        final BottomSheet bottomSheetCreate = builder.create();
        buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda13
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AlertsCreator.$r8$lambda$oapwvxyW21rHqlzA3rB5ViOh4Z0(i, buttonWithCounterView, bottomSheetCreate, j, resourcesProvider, view);
            }
        });
        bottomSheetCreate.fixNavigationBar();
        bottomSheetCreate.show();
    }

    public static /* synthetic */ void $r8$lambda$oapwvxyW21rHqlzA3rB5ViOh4Z0(final int i, final ButtonWithCounterView buttonWithCounterView, final BottomSheet bottomSheet, final long j, final Theme.ResourcesProvider resourcesProvider, View view) {
        TL_phone.createConferenceCall createconferencecall = new TL_phone.createConferenceCall();
        createconferencecall.random_id = Utilities.random.nextInt();
        ConnectionsManager.getInstance(i).sendRequest(createconferencecall, new RequestDelegate() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda67
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda87
                    @Override // java.lang.Runnable
                    public final void run() {
                        AlertsCreator.m7638$r8$lambda$c5cUb5pJBOJseb19DP9EUlkpeQ(tLObject, i, buttonWithCounterView, bottomSheet, j, tL_error, resourcesProvider);
                    }
                });
            }
        });
    }

    /* renamed from: $r8$lambda$c5cUb5pJBOJseb19DP-9EUlkpeQ, reason: not valid java name */
    public static /* synthetic */ void m7638$r8$lambda$c5cUb5pJBOJseb19DP9EUlkpeQ(TLObject tLObject, final int i, ButtonWithCounterView buttonWithCounterView, BottomSheet bottomSheet, long j, TLRPC.TL_error tL_error, Theme.ResourcesProvider resourcesProvider) {
        if (tLObject instanceof TLRPC.Updates) {
            final TLRPC.Updates updates = (TLRPC.Updates) tLObject;
            MessagesController.getInstance(i).putUsers(updates.users, false);
            MessagesController.getInstance(i).putChats(updates.chats, false);
            ArrayList arrayListFindUpdates = MessagesController.findUpdates(updates, TLRPC.TL_updateGroupCall.class);
            int size = arrayListFindUpdates.size();
            TLRPC.GroupCall groupCall = null;
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayListFindUpdates.get(i2);
                i2++;
                groupCall = ((TLRPC.TL_updateGroupCall) obj).call;
            }
            Utilities.stageQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda133
                @Override // java.lang.Runnable
                public final void run() {
                    MessagesController.getInstance(i).processUpdates(updates, false);
                }
            });
            if (groupCall == null || LaunchActivity.instance == null) {
                buttonWithCounterView.setLoading(false);
                return;
            }
            bottomSheet.lambda$new$0();
            SendMessagesHelper.getInstance(i).sendMessage(SendMessagesHelper.SendMessageParams.m1190of(groupCall.invite_link, j));
            BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
            if (safeLastFragment != null) {
                if (safeLastFragment instanceof ChatActivity) {
                    ChatActivity chatActivity = (ChatActivity) safeLastFragment;
                    if (chatActivity.getDialogId() == j && chatActivity.getChatMode() == 0) {
                        return;
                    }
                }
                safeLastFragment.presentFragment(ChatActivity.m1258of(j));
                return;
            }
            return;
        }
        if (!(tLObject instanceof TL_phone.groupCall)) {
            if (tL_error != null) {
                BulletinFactory.m1266of(bottomSheet.topBulletinContainer, resourcesProvider).showForError(tL_error);
                return;
            }
            return;
        }
        TL_phone.groupCall groupcall = (TL_phone.groupCall) tLObject;
        MessagesController.getInstance(i).putUsers(groupcall.users, false);
        MessagesController.getInstance(i).putChats(groupcall.chats, false);
        if (LaunchActivity.instance == null) {
            buttonWithCounterView.setLoading(false);
            return;
        }
        TLRPC.TL_inputGroupCall tL_inputGroupCall = new TLRPC.TL_inputGroupCall();
        TLRPC.GroupCall groupCall2 = groupcall.call;
        tL_inputGroupCall.f1593id = groupCall2.f1586id;
        tL_inputGroupCall.access_hash = groupCall2.access_hash;
        bottomSheet.lambda$new$0();
        VoIPHelper.joinConference(LaunchActivity.instance, i, tL_inputGroupCall, false, groupcall.call, null);
        SendMessagesHelper.getInstance(i).sendMessage(SendMessagesHelper.SendMessageParams.m1190of(groupcall.call.invite_link, j));
    }

    public static void showGiftThemeApplyConfirm(Context context, Theme.ResourcesProvider resourcesProvider, int i, TL_stars.StarGift starGift, long j, final Runnable runnable) {
        TLObject userOrChat = MessagesController.getInstance(i).getUserOrChat(j);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        linearLayout.addView(new StarGiftSheet.GiftThemeReuseTopView(context, starGift, userOrChat), LayoutHelper.createLinear(-1, -2, 48, 0, -4, 0, 0));
        TextView textView = new TextView(context);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, resourcesProvider));
        textView.setTextSize(1, 16.0f);
        textView.setText(AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.GiftThemesSetInReuseInfo, DialogObject.getDialogTitle(userOrChat))));
        linearLayout.addView(textView, LayoutHelper.createLinear(-1, -2, 48, 24, 0, 24, 4));
        new AlertDialog.Builder(context, resourcesProvider).setView(linearLayout).setPositiveButton(LocaleController.getString(C2369R.string.GiftThemesSetInReuseConfirm), new AlertDialog.OnButtonClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda149
            @Override // org.telegram.ui.ActionBar.AlertDialog.OnButtonClickListener
            public final void onClick(AlertDialog alertDialog, int i2) {
                runnable.run();
            }
        }).setNegativeButton(LocaleController.getString(C2369R.string.Cancel), null).show();
    }

    public static BottomSheet createCustomPicker(Context context, String str, int i, final String[] strArr, final Utilities.Callback callback) {
        if (TimezonesController.getInstance(UserConfig.selectedAccount).getTimezones().isEmpty()) {
            return null;
        }
        ScheduleDatePickerColors scheduleDatePickerColors = new ScheduleDatePickerColors();
        BottomSheet.Builder builder = new BottomSheet.Builder(context, false, null);
        builder.setApplyBottomPadding(false);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(0);
        linearLayout.setWeightSum(1.0f);
        final NumberPicker numberPicker = new NumberPicker(context);
        numberPicker.setAllItemsCount(strArr.length);
        numberPicker.setItemCount(Math.min(strArr.length, 8));
        numberPicker.setTextColor(scheduleDatePickerColors.textColor);
        numberPicker.setGravity(17);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(strArr.length - 1);
        numberPicker.setValue(i);
        linearLayout.addView(numberPicker, LayoutHelper.createLinear(0, 432, 1.0f));
        numberPicker.setFormatter(new NumberPicker.Formatter() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda215
            @Override // org.telegram.ui.Components.NumberPicker.Formatter
            public final String format(int i2) {
                return AlertsCreator.$r8$lambda$GTHVQJQ0vucWWuZAEiEseUZE3uY(strArr, i2);
            }
        });
        LinearLayout linearLayout2 = new LinearLayout(context) { // from class: org.telegram.ui.Components.AlertsCreator.63
            boolean ignoreLayout = false;

            @Override // android.widget.LinearLayout, android.view.View
            protected void onMeasure(int i2, int i3) {
                this.ignoreLayout = true;
                numberPicker.getLayoutParams().height = AndroidUtilities.m1146dp(42.0f) * 8;
                this.ignoreLayout = false;
                super.onMeasure(i2, i3);
            }

            @Override // android.view.View, android.view.ViewParent
            public void requestLayout() {
                if (this.ignoreLayout) {
                    return;
                }
                super.requestLayout();
            }
        };
        linearLayout2.setOrientation(1);
        FrameLayout frameLayout = new FrameLayout(context);
        TextView textView = new TextView(context);
        textView.setText(str);
        textView.setTextColor(scheduleDatePickerColors.textColor);
        textView.setTextSize(1, 20.0f);
        textView.setTypeface(AndroidUtilities.bold());
        frameLayout.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 51, 0.0f, 12.0f, 0.0f, 0.0f));
        textView.setOnTouchListener(new View.OnTouchListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda216
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return AlertsCreator.$r8$lambda$7ckBqEALjwCHxGOZ3hYffbbxGrs(view, motionEvent);
            }
        });
        linearLayout2.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 51, 22, 0, 0, 4));
        linearLayout2.addView(linearLayout, LayoutHelper.createLinear(-1, -2, 1.0f, 0, 0, 12, 0, 12));
        ButtonWithCounterView buttonWithCounterView = new ButtonWithCounterView(context, null);
        buttonWithCounterView.setText(LocaleController.getString(C2369R.string.Select), false);
        buttonWithCounterView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda217
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                bottomSheetArr[0].lambda$new$0();
            }
        });
        linearLayout2.addView(buttonWithCounterView, LayoutHelper.createLinear(-1, 48, 0, 16, 12, 16, 12));
        builder.setCustomView(linearLayout2);
        BottomSheet bottomSheetShow = builder.show();
        bottomSheetShow.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: org.telegram.ui.Components.AlertsCreator$$ExternalSyntheticLambda218
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                callback.run(Integer.valueOf(numberPicker.getValue()));
            }
        });
        bottomSheetShow.setBackgroundColor(scheduleDatePickerColors.backgroundColor);
        bottomSheetShow.fixNavigationBar(scheduleDatePickerColors.backgroundColor);
        BottomSheet bottomSheetCreate = builder.create();
        final BottomSheet[] bottomSheetArr = {bottomSheetCreate};
        return bottomSheetCreate;
    }

    public static /* synthetic */ String $r8$lambda$GTHVQJQ0vucWWuZAEiEseUZE3uY(String[] strArr, int i) {
        return strArr[i];
    }

    public static /* synthetic */ boolean $r8$lambda$7ckBqEALjwCHxGOZ3hYffbbxGrs(View view, MotionEvent motionEvent) {
        return true;
    }
}
